package com.nighthawk.spring_portfolio.mvc.mortevision;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nighthawk.spring_portfolio.mvc.announcement.Announcement;

public interface AssignmentJpaRepository extends JpaRepository<Assignment, Long> {
    // Additional query methods can be added here if needed
    Optional<Assignment> findByassignmentId(Long assignmentId); 
    Assignment findByName(String Name); 
    List<Assignment> findAllByOrderByNameAsc();
}
