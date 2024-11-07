package com.nighthawk.spring_portfolio.mvc.comment;

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
    private Long id;

    @Column(unique=false)
    private String assignment;
    private String text;
    private String author;
    private String timestamp;

    // Define a formatter for the timestamp
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor with necessary fields
    public Comment(String text, String author, String assigment) {
        this.assignment = assigment;
        this.text = text;
        this.author = author;
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    // Static method to create initial data
    public static List<Comment> createInitialData() {
        List<Comment> Comments = new ArrayList<>();

        // Create Comments with formatted timestamp
        Comments.add(new Comment("Reading Log", "This is a test Comment", "Kayden"));

        return Comments;
    }

    // Static method to initialize the data
    public static List<Comment> init() {
        return createInitialData();
    }
}
