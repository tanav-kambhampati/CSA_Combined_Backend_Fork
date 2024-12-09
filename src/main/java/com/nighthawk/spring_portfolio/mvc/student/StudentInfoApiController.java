package com.nighthawk.spring_portfolio.mvc.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/api/students")
public class StudentInfoApiController {
    
    @Autowired
    private StudentInfoJPARepository studentJPARepository;

    @GetMapping("/all")
    public ResponseEntity<Iterable<StudentInfo>> getAllStudents() {
        return ResponseEntity.ok(studentJPARepository.findAll());
    }

    @Getter
    public static class CriteriaDto {
        private String username;
        private String course;
        private int trimester;
        private int period; 
    }

    @PostMapping("/find")
    public ResponseEntity<StudentInfo> getStudentByCriteria(
            @RequestBody CriteriaDto criteriaDto) {
        
        List<StudentInfo> students = studentJPARepository.findByUsernameCourseTrimesterPeriod(criteriaDto.getUsername(), criteriaDto.getCourse(), criteriaDto.getTrimester(), criteriaDto.getPeriod());
        
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students.get(0));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<StudentInfo> createStudent(@RequestBody StudentInfo student) {
        try {
            Optional<StudentInfo> existingStudents = studentJPARepository.findByUsername(student.getUsername());
            if (!existingStudents.isEmpty()) {
                throw new RuntimeException("A student with this GitHub ID already exists.");
            }
            StudentInfo createdStudent = studentJPARepository.save(student);
            return ResponseEntity.ok(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteStudentByUsername(@RequestParam String username) {
        Optional<StudentInfo> student = studentJPARepository.findByUsername(username);
        
        if (student.isPresent()) {
            studentJPARepository.deleteById(student.get().getId());  // Delete student by ID
            return ResponseEntity.ok("Student with username '" + username + "' has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Student with username '" + username + "' not found.");
        }
    }


    @Getter 
    public static class StudentDto {
        private String username;
        private ArrayList<String> tasks;
    }


    @PostMapping("/update-tasks")
    public ResponseEntity<StudentInfo> updateTasks(@RequestBody StudentDto studentDto) {
        String username =  studentDto.getUsername();
        ArrayList<String> tasks = studentDto.getTasks();


        Optional<StudentInfo> student = studentJPARepository.findByUsername(username);

        if (student.isPresent()) {
            StudentInfo student1 = student.get();
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
    public static class PeriodDto {
        private String course;
        private int trimester;
        private int period;
    }

    @PostMapping("/find-period")
    public ResponseEntity<Iterable<StudentInfo>> getPeriodByTrimester(
        @RequestBody PeriodDto periodDto) {
            
        List<StudentInfo> students = studentJPARepository.findPeriod(periodDto.getCourse(), periodDto.getTrimester(), periodDto.getPeriod());

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }


}