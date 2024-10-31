package com.nighthawk.spring_portfolio.mvc.bathroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/queue")
public class QueueApiController {
    
    @Autowired
    private QueueJPARepository repository;

    // Add student to a teacher's queue
    @PostMapping("/add/{studentId}/{teacherName}")
    public ResponseEntity<Object> addToQueue(
            @PathVariable Long studentId,
            @PathVariable String teacherName,
            @RequestParam String studentName) {
        
        // Get current maximum position for this teacher
        Integer maxPosition = repository.findMaxPositionForTeacher(teacherName);
        int newPosition = (maxPosition != null) ? maxPosition + 1 : 1;

        // Get or create student queue entry
        Queue queue = repository.findByStudentId(studentId)
            .orElse(new Queue(studentId, studentName, new HashMap<>()));

        // Add or update position in teacher's queue
        queue.getQueuePositions().put(teacherName, newPosition);
        repository.save(queue);

        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("studentName", studentName);
        response.put("teacherQueue", Map.of(teacherName, newPosition));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Remove student from a teacher's queue
    @DeleteMapping("/remove/{studentId}/{teacherName}")
    public ResponseEntity<Object> removeFromQueue(
            @PathVariable Long studentId,
            @PathVariable String teacherName) {
        
        Optional<Queue> queueOpt = repository.findByStudentId(studentId);
        
        if (queueOpt.isPresent()) {
            Queue queue = queueOpt.get();
            Map<String, Integer> positions = queue.getQueuePositions();
            
            if (positions.containsKey(teacherName)) {
                int removedPosition = positions.get(teacherName);
                positions.remove(teacherName);
                
                // If no more queues, remove the entire entry
                if (positions.isEmpty()) {
                    repository.delete(queue);
                } else {
                    repository.save(queue);
                }
                
                // Update other students' positions for this teacher
                List<Queue> allQueues = repository.findAll();
                for (Queue q : allQueues) {
                    Map<String, Integer> pos = q.getQueuePositions();
                    if (pos.containsKey(teacherName) && pos.get(teacherName) > removedPosition) {
                        pos.put(teacherName, pos.get(teacherName) - 1);
                        repository.save(q);
                    }
                }
                
                return new ResponseEntity<>(Map.of("removed", Map.of(
                    "studentId", studentId,
                    "teacherName", teacherName
                )), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Student not found in teacher's queue"), HttpStatus.NOT_FOUND);
    }

    // Get current queue for a specific teacher
    @GetMapping("/teacher/{teacherName}")
    public ResponseEntity<List<Map<String, Object>>> getTeacherQueue(@PathVariable String teacherName) {
        List<Queue> allQueues = repository.findAll();
        List<Map<String, Object>> teacherQueue = new ArrayList<>();
        
        for (Queue q : allQueues) {
            if (q.getQueuePositions().containsKey(teacherName)) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("studentId", q.getStudentId());
                entry.put("studentName", q.getStudentName());
                entry.put("position", q.getQueuePositions().get(teacherName));
                teacherQueue.add(entry);
            }
        }
        
        // Sort by position
        teacherQueue.sort((a, b) -> 
            ((Integer)a.get("position")).compareTo((Integer)b.get("position")));
            
        return new ResponseEntity<>(teacherQueue, HttpStatus.OK);
    }

    // Get all queues for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Object> getStudentQueues(@PathVariable Long studentId) {
        Optional<Queue> queueOpt = repository.findByStudentId(studentId);
        
        if (queueOpt.isPresent()) {
            Queue queue = queueOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("studentId", queue.getStudentId());
            response.put("studentName", queue.getStudentName());
            response.put("queues", queue.getQueuePositions());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Student not found"), HttpStatus.NOT_FOUND);
    }
}