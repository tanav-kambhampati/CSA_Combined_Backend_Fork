package com.nighthawk.spring_portfolio.mvc.synergy;


import com.vladmihalcea.hibernate.type.json.JsonType;

import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName ="assignment", converter = JsonType.class)
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min=5, max=50, message="Assignment Name (5 to 50 chars)")
    private String name;

    private String details;

    @OneToMany(mappedBy="assignment")
    private List<Grade> grades;

    @NotNull
    private Double points;

    public Assignment(String name, String details, Double points) {
        this.name = name;
        this.details = details;
        this.points = points;
    }

    // Test data 
    public static Assignment[] init() {
        return new Assignment[] {
            new Assignment("Dance Unit", "Be a little skibidi and do some dancing.", 1.0),
            new Assignment("Sprint 1", "How's the website coming along?", 1.0),
            new Assignment("Sprint 2", "Please make a decent team teach", 1.0),
            new Assignment("Sprint 3", "Bro we just need some N@TM submissions that are presentable", 1.0),
        };
    }
}
