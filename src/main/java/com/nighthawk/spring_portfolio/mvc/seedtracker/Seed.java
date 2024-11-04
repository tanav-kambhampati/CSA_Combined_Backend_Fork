package com.nighthawk.spring_portfolio.mvc.seedtracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Seed is a POJO, Plain Old Java Object.
 * --- @Data is Lombok annotation for @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
 * --- @AllArgsConstructor is Lombok annotation for a constructor with all arguments
 * --- @NoArgsConstructor is Lombok annotation for a constructor with no arguments
 * --- @Entity annotation is used to mark the class as a persistent Java class.
 */
@Data
@NoArgsConstructor
@Entity
public class Seed {

    /** automatic unique identifier for Seed record
     * --- Id annotation is used to specify the identifier property of the entity.
     * --- GeneratedValue annotation is used to specify the primary key generation strategy to use.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** studentId is a unique identifier for each student
     * --- Column annotation specifies that this field is mapped to a column.
     */
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
    @NotNull
    private Double grade;

    public Seed(Long studentId, String comment, Double grade) {
        this.studentId = studentId;
        this.comment = comment;
        this.grade = grade;
    }
    
    /** Static method to initialize an array of Seed objects for testing
     * @return Seed[], an array of Seed objects
     */
    public static Seed[] init() {
        return new Seed[] {
            new Seed(1L, "Good work on the project.", 85.5),
            new Seed(2L, "Needs improvement in certain areas.", 70.0),
            new Seed(3L, "Excellent performance!", 95.0),
            new Seed(4L, "Incomplete assignment.", 50.0),
            new Seed(5L, "Average performance.", 75.0)
        };
    }

    /** Static method to print Seed objects from an array
     * @param args, not used
     */
    public static void main(String[] args) {
        // Obtain Seed from initializer
        Seed[] seeds = init();

        // Iterate using "enhanced for loop"
        for (Seed seed : seeds) {
            System.out.println(seed);  // print object
        }
    }
}

