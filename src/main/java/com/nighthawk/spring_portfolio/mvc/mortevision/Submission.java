package com.nighthawk.spring_portfolio.mvc.mortevision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

import java.util.List;
import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long SubmissiontId;

    private String user;

    private String submission_link;

    private String submission_time;

    private String assignment_name;

    private List<String> comments;


    public void addComment(String comment){
        this.comments.add(comment);
    }
    
}
