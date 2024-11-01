package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BathroomQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String teacherName;
    private String peopleQueue;

    // Custom constructor
    public BathroomQueue(String teacherName) {
        this.teacherName = teacherName;
        this.peopleQueue = "";
    }
    
    public void addStudent(String studentName) {
        if (this.peopleQueue == null || this.peopleQueue.isEmpty()) {
            this.peopleQueue = studentName;
        } else {
            this.peopleQueue += "," + studentName;
        }
    }
    public void removeStudent(String studentName) {
        if (this.peopleQueue != null) {
            this.peopleQueue = Arrays.stream(this.peopleQueue.split(","))
                .filter(s -> !s.equals(studentName))
                .collect(Collectors.joining(","));
        }
    }
    

    public static BathroomQueue[] init() {
        ArrayList<BathroomQueue> queues = new ArrayList<>();
        queues.add(new BathroomQueue("Mortensen"));
        queues.add(new BathroomQueue("Campillo"));
        queues.add(new BathroomQueue("Jenkins"));
        queues.add(new BathroomQueue("Bernabeo"));
        return queues.toArray(new BathroomQueue[0]);
    }
}