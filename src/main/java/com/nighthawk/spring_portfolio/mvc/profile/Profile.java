package com.nighthawk.spring_portfolio.mvc.profile;

import java.time.LocalDate;
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

    @Column
    private String email;
    private String name;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private String date;

    public LocalDate getDate() {
        return LocalDate.parse(this.date);
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public Profile(String name, String email, String password, String date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.date = date;
    }

    public static Profile[] init() {
        ArrayList<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Thomas Edison", "toby@gmail.com", "123toby", "2001-01-02"));
        profiles.add(new Profile("Alexander Graham Bell", "lexb@gmail.com", "123lex", "2001-01-18"));
        profiles.add(new Profile("Nikola Tesla", "niko@gmail.com", "123niko", "2001-01-18"));
        return profiles.toArray(new Profile[0]);
    }
}
