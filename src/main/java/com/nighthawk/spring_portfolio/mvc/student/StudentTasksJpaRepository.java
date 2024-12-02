package com.nighthawk.spring_portfolio.mvc.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTasksJpaRepository extends JpaRepository<StudentTasks, Long> {
    StudentTasks findByUsername(String username);
}
