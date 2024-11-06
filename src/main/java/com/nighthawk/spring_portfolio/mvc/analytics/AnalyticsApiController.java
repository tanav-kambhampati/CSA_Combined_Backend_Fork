package com.nighthawk.spring_portfolio.mvc.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;  // Add this line for Collections
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:8080")  // Enable CORS for the frontend URL
public class AnalyticsApiController {

    @Autowired
    private AnalyticsJpaRepository repository;

    // Get all analytics records
    @GetMapping("/")
    public ResponseEntity<List<Analytics>> getAllAnalytics() {
        List<Analytics> analyticsList = repository.findAll();
        if (analyticsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }
        return new ResponseEntity<>(analyticsList, HttpStatus.OK); // Return found records
    }
    
    @GetMapping("/assignments")
    public List<Analytics> getAssignments() {
        List<Analytics> uniqueAssignments = new ArrayList<>();
        for (Analytics a : Analytics.init()) {
            boolean exists = uniqueAssignments.stream().anyMatch(existing -> existing.getAssignmentId() == a.getAssignmentId());
            if (!exists) {
                uniqueAssignments.add(a);
            }
        }
        return uniqueAssignments;

    }

    @GetMapping("/assignment/{assignment_id}/grades")
    @CrossOrigin(origins = "http://localhost:8080")  // Enable CORS for this method
    public ResponseEntity<GradeStatistics> getGradesByAssignment(@PathVariable int assignment_id) {
        List<Analytics> analyticsList = repository.findByAssignmentId(assignment_id);  // Use the correct method
        if (analyticsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No records found
        }

        // Extract grades from the analytics list and store them in an array
        List<Integer> gradesList = new ArrayList<>();
        for (Analytics analytics : analyticsList) {
            gradesList.add(analytics.getGrade());  // Assuming `getGrade()` is the method to retrieve the grade
        }

        // Convert list to array
        int[] gradesArray = gradesList.stream().mapToInt(i -> i).toArray();

        // Calculate statistics
        double mean = calculateMean(gradesArray);
        double stdDev = calculateStandardDeviation(gradesArray, mean);
        double median = calculateMedian(gradesList);
        double q1 = calculateQuartile(gradesList, 25);
        double q3 = calculateQuartile(gradesList, 75);

        // Create response object
        GradeStatistics stats = new GradeStatistics(gradesArray, mean, stdDev, median, q1, q3);

        return new ResponseEntity<>(stats, HttpStatus.OK);  // Return statistics
    }

    // Helper method to calculate mean
    private double calculateMean(int[] grades) {
        double sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    // Helper method to calculate standard deviation
    private double calculateStandardDeviation(int[] grades, double mean) {
        double sum = 0;
        for (int grade : grades) {
            sum += Math.pow(grade - mean, 2);
        }
        return Math.sqrt(sum / grades.length);
    }

    // Helper method to calculate median
    private double calculateMedian(List<Integer> gradesList) {
        Collections.sort(gradesList);
        int size = gradesList.size();
        if (size % 2 == 0) {
            return (gradesList.get(size / 2 - 1) + gradesList.get(size / 2)) / 2.0;
        } else {
            return gradesList.get(size / 2);
        }
    }

    // Helper method to calculate quartiles
    private double calculateQuartile(List<Integer> gradesList, int percentile) {
        Collections.sort(gradesList);
        int index = (int) Math.ceil(percentile / 100.0 * gradesList.size()) - 1;
        return gradesList.get(Math.max(index, 0));
    }

    // GradeStatistics class to hold all data
    public static class GradeStatistics {
        private int[] grades;
        private double mean;
        private double standardDeviation;
        private double median;
        private double q1;
        private double q3;

        public GradeStatistics(int[] grades, double mean, double standardDeviation, double median, double q1, double q3) {
            this.grades = grades;
            this.mean = mean;
            this.standardDeviation = standardDeviation;
            this.median = median;
            this.q1 = q1;
            this.q3 = q3;
        }

        // Getters for JSON response
        public int[] getGrades() {
            return grades;
        }

        public double getMean() {
            return mean;
        }

        public double getStandardDeviation() {
            return standardDeviation;
        }

        public double getMedian() {
            return median;
        }

        public double getQ1() {
            return q1;
        }

        public double getQ3() {
            return q3;
        }
    }
}
