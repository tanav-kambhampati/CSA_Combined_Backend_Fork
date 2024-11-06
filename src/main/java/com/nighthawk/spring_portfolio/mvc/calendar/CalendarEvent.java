package com.nighthawk.spring_portfolio.mvc.calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

/**
 * CalendarEvent is a POJO, Plain Old Java Object.
 * --- @Data is Lombok annotation for @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
 * --- @AllArgsConstructor is Lombok annotation for a constructor with all arguments
 * --- @NoArgsConstructor is Lombok annotation for a constructor with no arguments
 * --- @Entity annotation is used to mark the class as a persistent Java class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CalendarEvent {

    /** automatic unique identifier for CalendarEvent record */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** CalendarEvent attributes */
    @NotEmpty
    @Size(min = 3, max = 100, message = "Event type should be between 3 and 100 characters")
    private String type; // lecture, meeting, workshop

    @NotEmpty
    @Size(min = 3, max = 100, message = "Lecturer name should be between 3 and 100 characters")
    private String lecturer;

    @NotEmpty
    @Size(min = 3, max = 200, message = "Event link should be between 3 and 200 characters")
    private String eventLink;

    /** Optional JSON field to store additional details */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalDetails = new HashMap<>();

    @NotNull
    private LocalDate date;

    /** Custom constructor for CalendarEvent when building a new CalendarEvent object from an API call */
    public CalendarEvent(String type, String lecturer, String eventLink) {
        this.type = type;
        this.lecturer = lecturer;
        this.eventLink = eventLink;
    }

    /** Static method to initialize an array list of CalendarEvent objects */
    public static List<CalendarEvent> init() {
        List<CalendarEvent> events = new ArrayList<>();
        events.add(new CalendarEvent("lecture", "Dr. Smith", "http://example.com/lecture"));
        events.add(new CalendarEvent("meeting", "Mr. Johnson", "http://example.com/meeting"));
        return events;
    }

    /** Static method to print CalendarEvent objects from a list */
    public static void main(String[] args) {
        List<CalendarEvent> events = init();
        for (CalendarEvent event : events) {
            System.out.println(event);
        }
    }
}