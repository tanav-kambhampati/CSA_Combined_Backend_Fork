package com.nighthawk.spring_portfolio.mvc.assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:8085")
public class AssignmentsController {
    private static final Logger logger = LoggerFactory.getLogger(AssignmentsController.class);

    @Autowired
    private AssignmentJpaRepository assignmentRepo;

    @Autowired
    private SubmissionJPA submissionRepo;

    @PostMapping("/create") 
    public ResponseEntity<?> createAssignment(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String description,
            @RequestParam Double points,
            @RequestParam String dueDate
            ) {
        try {
            Assignment newAssignment = new Assignment(name, type, description, points, dueDate);
            Assignment savedAssignment = assignmentRepo.save(newAssignment);
            logger.info("Created new assignment: {}", savedAssignment.getName());
            return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating assignment", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create assignment: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssignments() {
    try {
        List<Assignment> assignments = assignmentRepo.findAll();
        System.out.println("Found " + assignments.size() + " assignments");
        for (Assignment a : assignments) {
            System.out.println("Assignment: " + a.getId() + " - " + a.getName());
        }
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error: " + e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PostMapping("/edit/{name}")
    public ResponseEntity<?> editAssignment(
            @PathVariable String name,
            @RequestBody String body) {
        try {
            Assignment assignment = assignmentRepo.findByName(name);
            if (assignment != null) {
                assignment.setName(name);
                assignmentRepo.save(assignment);
                logger.info("Updated assignment: {}", name);
                return new ResponseEntity<>(assignment, HttpStatus.OK);
            }
            logger.warn("Assignment not found for editing: {}", name);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Assignment not found: " + name);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error editing assignment", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to edit assignment: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity<?> deleteAssignment(@PathVariable String name) {
        try {
            Assignment assignment = assignmentRepo.findByName(name);
            if (assignment != null) {
                assignmentRepo.delete(assignment);
                logger.info("Deleted assignment: {}", name);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Assignment deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            logger.warn("Assignment not found for deletion: {}", name);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Assignment not found: " + name);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting assignment", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete assignment: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/submit/{assignmentId}")
    public ResponseEntity<?> submitAssignment(
            @PathVariable Long assignmentId,
            @RequestParam String content) {
        try {
            Assignment assignment = assignmentRepo.findById(assignmentId)
                    .orElseThrow(() -> new RuntimeException("Assignment not found"));
            
            Submission submission = new Submission(assignment, content);
            Submission savedSubmission = submissionRepo.save(submission);
            logger.info("Created submission for assignment: {}", assignmentId);
            return new ResponseEntity<>(savedSubmission, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error submitting assignment", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to submit assignment: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{assignmentId}/submissions")
    public ResponseEntity<?> getSubmissions(@PathVariable Long assignmentId) {
        try {
            List<Submission> submissions = submissionRepo.findByAssignmentId(assignmentId);
            logger.info("Retrieved {} submissions for assignment: {}", submissions.size(), assignmentId);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving submissions", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch submissions: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/debug") 
    public ResponseEntity<?> debugAssignments() {
        try {
            List<Assignment> assignments = assignmentRepo.findAll();
            // Create a simple map with just the essential data
            List<Map<String, String>> simple = new ArrayList<>();
            for (Assignment a : assignments) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(a.getId()));
                map.put("name", a.getName());
                simple.add(map);
            }
            return new ResponseEntity<>(simple, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); 
            return new ResponseEntity<>("Error: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // NOTE: I used gen. AI to create exceptions for each of my functions. 
    // SECOND NOTE: There are still some bugs I am working on :/
}