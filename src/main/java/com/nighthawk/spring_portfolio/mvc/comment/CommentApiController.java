package com.nighthawk.spring_portfolio.mvc.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam String author) {
        try {
            List<Comment> Comments = CommentJPA.findByAuthor(author);
            
            // Check if any Comments were found
            if (Comments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no Comments found
            }

            // Get the first Comment in the list and delete it
            Comment Comment = Comments.get(0);
            CommentJPA.delete(Comment);
            return new ResponseEntity<>(HttpStatus.OK);  // Return 200 on successful deletion

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 on error
        }
    }



}
