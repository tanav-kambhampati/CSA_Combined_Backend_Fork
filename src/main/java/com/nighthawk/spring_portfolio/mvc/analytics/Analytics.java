package com.nighthawk.spring_portfolio.mvc.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private String assignmentName;

    @Column(name = "assignment_id")
    private int assignmentId;

    private int points;
    private int grade;
    private String teacherComments;

    public static Analytics[] init() {
        return new Analytics[] {
            new Analytics(null, "John Doe", "Integrals", 1, 100, 85, "Good effort"),
            new Analytics(null, "Jane Smith", "Integrals", 1, 100, 92, "Excellent work"),
            new Analytics(null, "Tom Brown", "Integrals", 1, 100, 78, "Needs improvement"),
            new Analytics(null, "Lucy White", "Integrals", 1, 100, 88, "Well done"),
            new Analytics(null, "Henry Green", "Integrals", 1, 100, 95, "Outstanding"),
            new Analytics(null, "Emily Black", "Derivatives", 2, 100, 82, "Good, but can improve"),
            new Analytics(null, "Oliver Blue", "Derivatives", 2, 100, 89, "Great work"),
            new Analytics(null, "Sophia Red", "Derivatives", 2, 100, 91, "Excellent performance"),
            new Analytics(null, "Liam Purple", "Derivatives", 2, 100, 86, "Solid effort"),
            new Analytics(null, "Mia Yellow", "Derivatives", 2, 100, 90, "Great job")
        };
    }
}
