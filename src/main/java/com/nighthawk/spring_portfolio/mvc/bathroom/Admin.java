package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.ArrayList;

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

    public static Admin[] init()
    {
        ArrayList<Admin> users = new ArrayList<>();
        users.add(new Admin("toby@gmail.com", "", 0.0));
        users.add(new Admin("lexb@gmail.com", "", 0.0));
        users.add(new Admin("niko@gmail.com", "", 0.0));
        users.add(new Admin("madam@gmail.com", "", 0.0));
        users.add(new Admin("hop@gmail.com", "", 0.0));
        users.add(new Admin("jm1021@gmail.com", "", 0.0));
        users.add(new Admin("tarasehdave@gmail.com", "", 0.0));
        return users.toArray(new Admin[0]);
    }
}