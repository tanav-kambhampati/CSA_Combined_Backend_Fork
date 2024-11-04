package com.nighthawk.spring_portfolio.mvc.mortevision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TasksApiController {

    @Autowired
    private TasksJpaRepository repository;

    // GET all tasks
    @GetMapping("/")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // GET a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
        Optional<Tasks> task = repository.findById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // CREATE a new task
    @PostMapping("/create")
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks task) {
        repository.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // UPDATE a task
    @PutMapping("/update/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable Long id, @RequestBody Tasks updatedTask) {
        Optional<Tasks> optional = repository.findById(id);
        if (optional.isPresent()) {
            Tasks task = optional.get();
            if (updatedTask.getTaskName() != null) task.setTaskName(updatedTask.getTaskName());
            if (updatedTask.getTime() != null) task.setTime(updatedTask.getTime());
            if (updatedTask.getStatus() != null) task.setStatus(updatedTask.getStatus());
            if (updatedTask.getDescription() != null) task.setDescription(updatedTask.getDescription());
            repository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE a task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
