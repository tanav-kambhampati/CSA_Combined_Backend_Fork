package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    @Autowired
    private AdminJPARepository repository;

    @Getter
    public static class AdminDto {
        private String studentEmail;
        private String timeIn;
        private double averageDuration;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> timeInOut(@RequestBody AdminDto adminDto) {
        Optional<Admin> student = repository.findByStudentEmail(adminDto.getStudentEmail());
        if (student.isPresent()) {
            student.get().setTimeIn(adminDto.getTimeIn());
            student.get().setAverageDuration(adminDto.getAverageDuration());
            repository.save(student.get());
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<Admin> getAll() {
        return repository.findAll();
    }


}
