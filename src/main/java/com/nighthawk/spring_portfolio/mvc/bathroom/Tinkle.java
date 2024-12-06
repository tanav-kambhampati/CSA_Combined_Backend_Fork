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
public class Tinkle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String studentEmail;
    private String timeIn;
    private double averageDuration;

    public Tinkle(String studentEmail, String timeIn, double averageDuration)
    {
        this.studentEmail = studentEmail;
        this.timeIn = timeIn;
        this.averageDuration = averageDuration;
    }

    public void addTimeIn(String timeIn)
    {
        if (this.timeIn == null || this.timeIn.isEmpty())
        {
            this.timeIn = timeIn;
        }
        else 
        {
            this.timeIn += "," + timeIn;
        }

    }

    public void addAverageDuration(double averageDuration)
    {
        if (this.averageDuration == 0.0)
        {
            this.averageDuration = averageDuration;
        }
        else 
        {
            this.averageDuration += averageDuration;
        }
    }

    public static Tinkle[] init()
    {
        ArrayList<Tinkle> users = new ArrayList<>();
        users.add(new Tinkle("toby@gmail.com", "", 0.0));
        users.add(new Tinkle("lexb@gmail.com", "", 0.0));
        users.add(new Tinkle("niko@gmail.com", "", 0.0));
        users.add(new Tinkle("madam@gmail.com", "", 0.0));
        users.add(new Tinkle("hop@gmail.com", "", 0.0));
        users.add(new Tinkle("jm1021@gmail.com", "", 0.0));
        users.add(new Tinkle("tarasehdave@gmail.com", "", 0.0));
        return users.toArray(new Tinkle[0]);
    }
}