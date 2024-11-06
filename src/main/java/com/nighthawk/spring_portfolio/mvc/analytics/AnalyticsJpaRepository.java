package com.nighthawk.spring_portfolio.mvc.analytics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsJpaRepository extends JpaRepository<Analytics, Long> {
    List<Analytics> findByAssignmentId(int assignment_id);  // Use camelCase method matching the field name
}

