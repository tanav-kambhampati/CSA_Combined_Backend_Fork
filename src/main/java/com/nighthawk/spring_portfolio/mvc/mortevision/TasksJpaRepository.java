package com.nighthawk.spring_portfolio.mvc.mortevision;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksJpaRepository extends JpaRepository<Tasks, Long> {
    // Additional query methods can be added here if needed
}
