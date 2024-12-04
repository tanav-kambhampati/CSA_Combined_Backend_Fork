package com.nighthawk.spring_portfolio.mvc.mortevision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assignmentId;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private String rubric;
    private double points;

    @Convert(converter = QueueConverter.class)
    private Queue assignmentQueue = new Queue();

    public void resetQueue() {
        assignmentQueue.reset();
    }

    public void initQueue(List<String> people) {
        assignmentQueue.getHaventGone().addAll(people);
    }

    public void addQueue(String person) {
        assignmentQueue.getHaventGone().remove(person);
        assignmentQueue.getQueue().add(person);
    }

    public void removeQueue(String person) {
        assignmentQueue.getQueue().remove(person);
        assignmentQueue.getHaventGone().add(person);
    }

    public void doneQueue(String person) {
        assignmentQueue.getQueue().remove(person);
        assignmentQueue.getDone().add(person);
    }

    public String getFront() {
        return assignmentQueue.getFront();
    }

    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", rubric='" + rubric + '\'' +
                ", points=" + points +
                '}';
    }

    public static Assignment createAssignment(String name, String startDate, String dueDate, String rubric, double points) {
        Assignment assign = new Assignment();
        assign.setName(name); // setting name
        

        // setting the start/due dates
        try {
            Date date_start = new SimpleDateFormat("MM-dd-yyyy").parse(startDate); // start date
            assign.setStartDate(date_start);
            Date date_due = new SimpleDateFormat("MM-dd-yyyy").parse(dueDate); // start date
            assign.setDueDate(date_due);
        } catch (Exception e) {
            // handle exception
        }

        assign.setRubric(rubric);
        assign.setPoints(points);

        assign.setAssignmentQueue(new Queue());
    
        return assign;
    }

    public static Assignment[] init() {
        ArrayList<Assignment> assignments = new ArrayList<>();
        assignments.add(createAssignment("Math Homework", "10-01-2024", "10-15-2024", "Solve all problems in chapter 3", 100));
        assignments.add(createAssignment("Science Project", "10-05-2024", "10-20-2024", "Create a model of a cell", 200));
        assignments.add(createAssignment("History Essay", "10-10-2024", "10-25-2024", "Write about the industrial revolution", 150));
        return assignments.toArray(new Assignment[0]);
    }

    /** Static method to print Person objects from an array
     * @param args, not used
     */
    public static void main(String[] args) {
        // obtain Person from initializer
        Assignment assignments[] = init();

        // iterate using "enhanced for loop"
        for( Assignment assignment : assignments) {
            System.out.println(assignment);  // print object
        }
    }
}
