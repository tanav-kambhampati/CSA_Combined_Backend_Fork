package com.nighthawk.spring_portfolio.mvc.comment;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Comment")
public class CommentApiController {

    private final CommentJPA CommentJPA;

    public CommentApiController(CommentJPA CommentJPA) {
        this.CommentJPA = CommentJPA;
    }

    // Create Example
    @PostMapping("/create") 
    public ResponseEntity<Comment> createComment( @RequestParam String assignment, @RequestParam String text, @RequestParam String author) {
        Comment newComment = new Comment(assignment, text, author);
        Comment savedComment = CommentJPA.save(newComment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    // Read: Get all comments
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = CommentJPA.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Read: Get a single comment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return CommentJPA.findById(id)
                .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // Read: Get comments by assignment
    @GetMapping("/by-assignment")
    public ResponseEntity<List<Comment>> getCommentsByAssignment(@RequestParam String assignment) {
        List<Comment> comments = CommentJPA.findByAssignment(assignment);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no comments found
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);  // Return 200 with the list of comments
    }
}
