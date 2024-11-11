package com.nighthawk.spring_portfolio.mvc.seedtracker;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long studentId;

    // New field for student's name
    @NotEmpty
    private String name;

    @NotEmpty
    private String comment;

    @DecimalMin("0.0")
    private Double grade;

    // Constructor with required fields (excluding studentId and id, as they're auto-generated)
    public Seed(String name, String comment, Double grade) {
        this.name = name;
        this.comment = comment;
        this.grade = grade;
    }

    // Static method to create initial data
    public static List<Seed> createInitialData() {
        List<Seed> seeds = new ArrayList<>();
        seeds.add(new Seed("John Doe", "Good work on the project.", 85.5));
        seeds.add(new Seed("Jane Smith", "Needs improvement in certain areas.", 70.0));
        seeds.add(new Seed("Emily Jones", "Excellent performance!", 95.0));
        seeds.add(new Seed("Chris Brown", "Incomplete assignment.", 50.0));
        seeds.add(new Seed("Alex Green", "Average performance.", 75.0));
        return seeds;
    }

    public static List<Seed> init() {
        return createInitialData();
    }

    public static void main(String[] args) {
        List<Seed> seeds = init();
        for (Seed seed : seeds) {
            System.out.println(seed);
        }
    }
}