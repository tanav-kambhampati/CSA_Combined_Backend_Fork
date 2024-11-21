package com.nighthawk.spring_portfolio.mvc.bathroom;

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
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String studentEmail;
    private String timeIn;
    private double averageDuration;

    public Admin(String studentEmail, String timeIn, double averageDuration)
    {
        this.studentEmail = studentEmail;
        this.timeIn = timeIn;
        this.averageDuration = averageDuration;
    }
}