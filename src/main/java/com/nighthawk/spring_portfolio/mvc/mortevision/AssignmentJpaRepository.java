package com.nighthawk.spring_portfolio.mvc.mortevision;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentJpaRepository extends JpaRepository<Assignment, Long> {
    // Additional query methods can be added here if needed
}
