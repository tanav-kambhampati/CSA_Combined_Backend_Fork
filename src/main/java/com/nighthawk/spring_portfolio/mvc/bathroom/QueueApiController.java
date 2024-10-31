package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/api/queue")
public class QueueApiController {

    @Autowired
    private QueueJPARepository repository;

    // DTO class for queue entries
    @Getter
    public static class QueueDto {
        private String teacherName;
        private int queuePositions;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addToQueue(@RequestBody QueueDto queueDto) {
        // Check if a queue entry for the teacher already exists
        Optional<Queue> existingQueue = repository.findByTeacherName(queueDto.getTeacherName());
        if (existingQueue.isPresent()) {
            return new ResponseEntity<>("A queue for " + queueDto.getTeacherName() + " already exists.", HttpStatus.CONFLICT);
        }
        
        Queue queue = new Queue(queueDto.getTeacherName(), queueDto.getQueuePositions());
        repository.save(queue);
        return new ResponseEntity<>("Student added to " + queueDto.getTeacherName() + "'s queue at position " + queueDto.getQueuePositions(), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> removeFromQueue(@RequestParam Long id) {
        Optional<Queue> queueEntry = repository.findById(id);
        if (queueEntry.isPresent()) {
            repository.delete(queueEntry.get());
            return new ResponseEntity<>("Removed student from queue", HttpStatus.OK);
        }
        return new ResponseEntity<>("Queue entry not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Queue>> getAllQueues() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
