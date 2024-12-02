package com.nighthawk.spring_portfolio.mvc.student;

import java.util.ArrayList;
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
@RequestMapping("/api/students")
public class StudentApiController {
    
    @Autowired
    private StudentJPARepository studentJPARepository;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Student>> getAllStudents() {
        return ResponseEntity.ok(studentJPARepository.findAll());
    }

    @Getter
    public static class CriteriaDto {
        private String name;
        private String course;
        private int trimester;
        private int period; 
    }

    @PostMapping("/find")
    public ResponseEntity<Student> getStudentByCriteria(
            @RequestBody CriteriaDto criteriaDto) {
        
        List<Student> students = studentJPARepository.findByNameCourseTrimesterPeriod(criteriaDto.getName(), criteriaDto.getCourse(), criteriaDto.getTrimester(), criteriaDto.getPeriod());
        
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students.get(0));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Optional<Student> existingStudents = studentJPARepository.findByUsername(student.getUsername());
            if (!existingStudents.isEmpty()) {
                throw new RuntimeException("A student with this GitHub ID already exists.");
            }
            Student createdStudent = studentJPARepository.save(student);
            return ResponseEntity.ok(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteStudentByUsername(@RequestParam String username) {
        Optional<Student> student = studentJPARepository.findByUsername(username);
        
        if (student.isPresent()) {
            studentJPARepository.deleteById(student.get().getId());  // Delete student by ID
            return ResponseEntity.ok("Student with username '" + username + "' has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Student with username '" + username + "' not found.");
        }
    }

    @Getter
    public static class TeamDto {
        private String course;
        private int trimester;
        private int period; 
        private int table;
    }

    @PostMapping("/find-team")
    public ResponseEntity<Iterable<Student>> getTeamByCriteria(
            @RequestBody TeamDto teamDto) {
        
        List<Student> students = studentJPARepository.findTeam(teamDto.getCourse(), teamDto.getTrimester(), teamDto.getPeriod(), teamDto.getTable());
        
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }


    @Getter 
    public static class StudentDto {
        private String username;
        private ArrayList<String> tasks;
    }


    @PostMapping("/update-tasks")
    public ResponseEntity<Student> updateTasks(@RequestBody StudentDto studentDto) {
        String username =  studentDto.getUsername();
        ArrayList<String> tasks = studentDto.getTasks();


        Optional<Student> student = studentJPARepository.findByUsername(username);

        if (student.isPresent()) {
            Student student1 = student.get();
            ArrayList<String> newTasks = student1.getTasks();
            
            for (String task: tasks) {
                newTasks.add(task);
            }
            
            student1.setTasks(newTasks);
            studentJPARepository.save(student1);
            return ResponseEntity.ok(student1);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
@Getter
public static class TasksDto {
    private String username;
    private String task;
}

@PostMapping("/complete-task")
public ResponseEntity<String> completeTask(@RequestBody TasksDto tasksDto) {
    Optional<Student> optionalStudent = studentJPARepository.findByUsername(tasksDto.getUsername());
    String task = tasksDto.getTask();

    if (optionalStudent.isPresent()) {
        Student student = optionalStudent.get();
        if (student.getCompleted() == null) {
            student.setCompleted(new ArrayList<>()); 
        }

        if (student.getTasks().contains(task)) {
            student.getTasks().remove(task);
            student.getCompleted().add(task + " - Completed");
            studentJPARepository.save(student);
            return ResponseEntity.ok("Task marked as completed.");
        } else {
            return ResponseEntity.badRequest().body("Task not found in the student's task list.");
        }
    } else {
        return ResponseEntity.status(404).body("Student not found.");
    }
}

    @Getter 
    public static class PeriodDto {
        private String course;
        private int trimester;
        private int period;
    }

    @PostMapping("/find-period")
    public ResponseEntity<Iterable<Student>> getPeriodByTrimester(
        @RequestBody PeriodDto periodDto) {
            
        List<Student> students = studentJPARepository.findPeriod(periodDto.getCourse(), periodDto.getTrimester(), periodDto.getPeriod());

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }


}