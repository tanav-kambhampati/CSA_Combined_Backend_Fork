package com.nighthawk.spring_portfolio.mvc.synergy;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.spring_portfolio.mvc.assignments.Assignment;
import com.nighthawk.spring_portfolio.mvc.assignments.AssignmentJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;

import jakarta.validation.Valid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/synergy")
public class SynergyApiController {
    @Autowired
    private GradeJpaRepository gradeRepository;

    @Autowired
    private GradeRequestJpaRepository gradeRequestRepository;

    @Autowired
    private AssignmentJpaRepository assignmentRepository;

    @Autowired
    private PersonJpaRepository personRepository;

    @PostMapping("/update-all-grades")
    public String updateAllGrades(@RequestParam Map<String, String> grades) {
        for (String key : grades.keySet()) {
            String[] ids = key.replace("grades[", "").replace("]", "").split("\\[");
            Long assignmentId = Long.parseLong(ids[0]);
            Long studentId = Long.parseLong(ids[1]);
            String gradeString = grades.get(key);

            if (isNumeric(gradeString)) {
                Double gradeValue = Double.parseDouble(grades.get(key));
                Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
                Person student = personRepository.findById(studentId).orElse(null);
                
                Grade grade = gradeRepository.findByAssignmentAndStudent(assignment, student);
                if (grade == null) {
                    grade = new Grade();
                    grade.setAssignment(assignment);
                    grade.setStudent(student);
                }
                grade.setGrade(gradeValue);
                gradeRepository.save(grade);
            }
        }

        return "redirect:/mvc/synergy/gradebook";
    }
    
    @PostMapping("/create-grade-request")
    public String createGradeRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestBody GradeRequestDTO requestData) {
        String email = userDetails.getUsername();
        Person grader = personRepository.findByEmail(email);
        if (grader == null) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You must be a logged in user to do this"
            );
        }

        Person student = personRepository.findById(requestData.studentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + requestData.studentId));
        Assignment assignment = assignmentRepository.findById(requestData.assignmentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid assignment ID: " + requestData.assignmentId));
        
        GradeRequest gradeRequest = new GradeRequest(assignment, student, grader, requestData.explanation, requestData.gradeSuggestion);
        gradeRequestRepository.save(gradeRequest);

        return "{'message': 'Successfully created the grade request'}";
    }

    @PostMapping("/accept-request")
    public String acceptRequest(@Valid @RequestBody RequestIdDTO body) {
        if (body == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body");
        }

        GradeRequest request = gradeRequestRepository.findById((long) body.getRequestId()).orElse(null);
        if (request == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Grade request not found"
            );
        }
        else if (request.isAccepted()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Grade request was already accepted before"
            );
        }

        Assignment assignment = request.getAssignment();
        Person student = request.getStudent();
        Grade grade = gradeRepository.findByAssignmentAndStudent(assignment, request.getStudent());
        if (grade == null) {
            grade = new Grade();
            grade.setAssignment(assignment);
            grade.setStudent(student);
        }
        grade.setGrade(request.getGradeSuggestion());
        gradeRepository.save(grade);

        request.accept();
        gradeRequestRepository.save(request);

        return "{'message': 'Successfully accepted the grade request'}";
    }

    @PostMapping("/reject-request")
    public String rejectRequest(@RequestBody RequestIdDTO body) {
        if (body == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body");
        }
    
        GradeRequest request = gradeRequestRepository.findById((long) body.getRequestId()).orElse(null);
        if (request == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Grade request not found"
            );
        }
        else if (request.isAccepted()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Grade request was already accepted before"
            );
        }

        request.reject();
        gradeRequestRepository.save(request);

        return "{'message': 'Successfully rejected the grade request'}";
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}