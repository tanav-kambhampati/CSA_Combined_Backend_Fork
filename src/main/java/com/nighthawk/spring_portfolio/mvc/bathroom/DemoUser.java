package com.nighthawk.spring_portfolio.mvc.bathroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Import @Table from jakarta.persistence
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.DayOfWeek;

@Entity
//@Table(name = "app_user")  // Use a different table name
public class DemoUser {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated unique primary key
    // private Long id;           // Primary key field
    private Long id;           // Manually managed primary key

    private Long userId;    // Custom user ID (can have duplicates)
    private String userName;
    private LocalDate loginDate;

    @Column(name = "day_of_week") // Maps to the exact column name in the database
    private String dayOfWeek;
    
    private LocalTime loginTime;
    private LocalTime logoutTime;

    @Transient
    private Long duration;

    // Constructors, getters, and setters
    public DemoUser() { }

    public DemoUser(Long id, Long userId, String userName, LocalDate loginDate, LocalTime loginTime, LocalTime logoutTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        // this.loginDate = loginDate;
        setLoginDate(loginDate); // Set loginDate to automatically set dayOfWeek
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }  // Ensure the id is set manually

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public LocalDate getLoginDate() { return loginDate; }
    public void setLoginDate(LocalDate loginDate) {
        this.loginDate = loginDate;
        this.dayOfWeek = loginDate != null ? loginDate.getDayOfWeek().toString() : "N/A"; // Automatically set dayOfWeek
    }
    // public void setLoginDate(LocalDate loginDate) { this.loginDate = loginDate; }
    public String getDayOfWeek() { return dayOfWeek; }
    public LocalTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalTime loginTime) { this.loginTime = loginTime; }
    public LocalTime getLogoutTime() { return logoutTime; }
    public void setLogoutTime(LocalTime logoutTime) { this.logoutTime = logoutTime; }

    public Long getDuration() {
        if (loginTime != null && logoutTime != null) {
            return Duration.between(loginTime, logoutTime).toMinutes();
        }
        return null;
    }

    public Duration getSessionDuration() {
        return Duration.between(loginTime, logoutTime);
    }


}
