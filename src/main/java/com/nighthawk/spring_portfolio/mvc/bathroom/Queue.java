package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.ArrayList;

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
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String teacherName;
    private Integer queuePositions;

    // Custom constructor
    public Queue(String teacherName, Integer queuePositions) {
        this.teacherName = teacherName;
        this.queuePositions = queuePositions;
    }

    public static Queue[] init() {
        ArrayList<Queue> queues = new ArrayList<>();
        queues.add(new Queue("Mortensen", 1));
        queues.add(new Queue("Mortensen", 2));
        queues.add(new Queue("Mortensen", 3));
        queues.add(new Queue("Campillo", 1));
        queues.add(new Queue("Campillo", 2));
        queues.add(new Queue("Campillo", 3));
        return queues.toArray(new Queue[0]);
    }
}