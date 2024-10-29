package com.nighthawk.spring_portfolio.mvc.mortevision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentApiController {

    @Autowired
    private AssignmentJpaRepository repository;

    // GET queue for a specific assignment
    @GetMapping("/getQueue/{id}")
    public ResponseEntity<Queue> getQueue(@PathVariable long id) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            return new ResponseEntity<>(assignment.getAssignmentQueue(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/initQueue/{id}")
    public ResponseEntity<Assignment> initQueue(@PathVariable long id, @RequestBody List<String> people) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.initQueue(people);
            repository.save(assignment);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/addQueue/{id}")
    public ResponseEntity<Assignment> addQueue(@PathVariable long id, @RequestBody String person) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.addQueue(person);
            repository.save(assignment);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/removeQueue/{id}")
    public ResponseEntity<Assignment> removeQueue(@PathVariable long id, @RequestBody String person) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.removeQueue(person);
            repository.save(assignment);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/doneQueue/{id}")
    public ResponseEntity<Assignment> doneQueue(@PathVariable long id, @RequestBody String person) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.doneQueue(person);
            repository.save(assignment);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/resetQueue/{id}")
    public ResponseEntity<Assignment> resetQueue(@PathVariable long id) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.resetQueue();
            repository.save(assignment);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET all assignments
    @GetMapping("/")
    public ResponseEntity<List<Assignment>> getAssignments() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // CREATE a new assignment
    @PostMapping("/create")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        repository.save(assignment);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    // DELETE an assignment by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAssignment(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable long id, @RequestBody Assignment updatedAssignment) {
        Optional<Assignment> optional = repository.findById(id);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
        
            if (updatedAssignment.getName() != null) {
                assignment.setName(updatedAssignment.getName());
            }
            if (updatedAssignment.getStartDate() != null) {
                assignment.setStartDate(updatedAssignment.getStartDate());
            }
            if (updatedAssignment.getDueDate() != null) {
                assignment.setDueDate(updatedAssignment.getDueDate());
            }
            if (updatedAssignment.getRubric() != null) {
                assignment.setRubric(updatedAssignment.getRubric());
            }
            if (updatedAssignment.getPoints() != 0) {
                assignment.setPoints(updatedAssignment.getPoints());
            }
            repository.save(assignment);  // Save the updated entity
            return new ResponseEntity<>(assignment, HttpStatus.OK);  // Return the updated assignment
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Assignment not found
    }

}
