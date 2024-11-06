package com.nighthawk.spring_portfolio.mvc.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.announcement.Announcement;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/comment")
public class commentApiController {
    
    private final commentJPA commentJPA;

    public commentApiController(commentJPA commentJPA) {
        this.commentJPA = commentJPA;
    }

    // Create Example
    @PostMapping("/create") 
    public ResponseEntity<comment> createComment( @RequestParam String assignment, @RequestParam String text, @RequestParam String author) {
        
        comment newComment = new comment(assignment, text, author);
        comment savedComment = commentJPA.save(newComment);
        
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam String author) {
        try {
            List<comment> comments = commentJPA.findByAuthor(author);
            
            // Check if any comments were found
            if (comments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no comments found
            }

            // Get the first comment in the list and delete it
            comment comment = comments.get(0);
            commentJPA.delete(comment);
            return new ResponseEntity<>(HttpStatus.OK);  // Return 200 on successful deletion

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 on error
        }
    }



}
