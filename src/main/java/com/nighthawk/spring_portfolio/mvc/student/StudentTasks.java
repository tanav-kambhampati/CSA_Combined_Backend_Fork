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

    @Column(unique = true)
    private String username;

    private ArrayList<String> tasks;

    public StudentTasks(String username, ArrayList<String> tasks) {
        this.username = username;
        this.tasks = tasks;
    }
}