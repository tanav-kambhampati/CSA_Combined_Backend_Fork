package com.nighthawk.spring_portfolio.mvc.comments;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;

    @Column(unique=false)
    private Long assignmentNumber;

    private String text;
    private String timestamp;

    // Define a formatter for the timestamp
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor with necessary fields
    public Comment(Long assignmentNumber, String text) {
        this.assignmentNumber = assignmentNumber;
        this.text = text;
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    // Static method to create initial data
    public static List<Comment> createInitialData() {
        List<Comment> comments = new ArrayList<>();

        // Create comments with formatted timestamp
        comments.add(new Comment(1L, "This is a test comment"));

        return comments;
    }

    // Static method to initialize the data
    public static List<Comment> init() {
        return createInitialData();
    }
}
