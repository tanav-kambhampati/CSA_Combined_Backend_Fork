package com.nighthawk.spring_portfolio.mvc.profile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;
    private String name;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private String date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(name = "time_in")
    private LocalTime timeIn; // Store login time only (hour and minute)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(name = "time_out")
    private LocalTime timeOut; // Store logout time only (hour and minute)

    public LocalDate getDate() {
        return LocalDate.parse(this.date);
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    // Calculate the duration between timeIn and timeOut
    public Duration getTimeDuration() {
        if (timeIn != null && timeOut != null) {
            return Duration.between(timeIn, timeOut);
        }
        return Duration.ZERO;
    }

    // Get duration in human-readable format
    public String getTimeDurationReadable() {
        return TimeDurationUtil.getReadableDuration(timeIn, timeOut);
    }

    // Get duration in minutes
    public long getTimeDurationMinutes() {
        return TimeDurationUtil.getDurationInMinutes(timeIn, timeOut);
    }

    // Constructor updated to include timeIn and timeOut
    public Profile(String name, String email, String password, String date, LocalTime timeIn, LocalTime timeOut) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public static Profile[] init() {
        ArrayList<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Thomas Edison", "toby@gmail.com", "123toby", "2001-01-02"));
        profiles.add(new Profile("Alexander Graham Bell", "lexb@gmail.com", "123lex", "2001-01-18"));
        profiles.add(new Profile("Nikola Tesla", "niko@gmail.com", "123niko", "2001-01-18"));
        profiles.add(new Profile("Tara Sehdave", "tarasehdave@gmail.com", "123tara", "2001-01-18"));    
        return profiles.toArray(new Profile[0]);
    }

    public static void main(String[] args) {
        Profile[] profiles = Profile.init();
        for (Profile profile : profiles) {
            System.out.println("Name: " + profile.getName());
            System.out.println("Time In: " + profile.getTimeIn());
            System.out.println("Time Out: " + profile.getTimeOut());
            System.out.println("Duration (Readable): " + profile.getTimeDurationReadable());
            System.out.println("Duration (Minutes): " + profile.getTimeDurationMinutes() + " minutes");
            System.out.println();
        }
    }
}
