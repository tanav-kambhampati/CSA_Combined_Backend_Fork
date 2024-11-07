package com.nighthawk.spring_portfolio.mvc.seedtracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

/**
 * Seed is a POJO, Plain Old Java Object.
 * --- @Data is Lombok annotation for @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
 * --- @AllArgsConstructor is Lombok annotation for a constructor with all arguments
 * --- @NoArgsConstructor is Lombok annotation for a constructor with no arguments
 * --- @Entity annotation is used to mark the class as a persistent Java class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seed {

    /** Automatic unique identifier for Seed record
     * --- Id annotation is used to specify the identifier property of the entity.
     * --- GeneratedValue annotation is used to specify the primary key generation strategy to use.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** studentId is a unique identifier for each student
     * --- Column annotation specifies that this field is mapped to a column and is auto-generated.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long studentId;

    /** comment is a field for storing feedback or notes related to the student
     * --- Basic string attribute for storing comments.
     */
    @NotEmpty
    private String comment;

    /** grade is the student's grade for a given assignment or project
     * --- Represented as a double to allow fractional grades.
     */
    @DecimalMin("0.0") // Ensures the grade is not less than 0.0
    private Double grade;

    // Constructor with required fields (excluding studentId, as it's auto-generated)
    public Seed(String comment, Double grade) {
        this.comment = comment;
        this.grade = grade;
    }

    // Static method to create initial data
    public static List<Seed> createInitialData() {
        List<Seed> seeds = new ArrayList<>();
        seeds.add(new Seed("Good work on the project.", 85.5));
        seeds.add(new Seed("Needs improvement in certain areas.", 70.0));
        seeds.add(new Seed("Excellent performance!", 95.0));
        seeds.add(new Seed("Incomplete assignment.", 50.0));
        seeds.add(new Seed("Average performance.", 75.0));
        return seeds;
    }

    // Static method to initialize the data
    public static List<Seed> init() {
        return createInitialData();
    }

    /** Main method for testing Seed objects initialization
     * @param args, not used
     */
    public static void main(String[] args) {
        // Obtain Seed from initializer
        List<Seed> seeds = init();

        // Iterate using "enhanced for loop"
        for (Seed seed : seeds) {
            System.out.println(seed);  // print object
        }
    }
}


