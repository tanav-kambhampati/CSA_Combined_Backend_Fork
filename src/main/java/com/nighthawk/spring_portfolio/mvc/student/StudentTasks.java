package com.nighthawk.spring_portfolio.mvc.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;
    private ArrayList<String> tasks;
    private ArrayList<String> completed;
    private String project;

    public StudentTasks (String name, ArrayList<String> tasks, ArrayList<String> completed, String project) {
        this.name = name;
        this.tasks = tasks;
        this.completed = completed;
        this.project = project;
    }
}
