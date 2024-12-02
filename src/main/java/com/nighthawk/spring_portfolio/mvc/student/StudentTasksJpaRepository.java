package com.nighthawk.spring_portfolio.mvc.student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentTasksJpaRepository extends JpaRepository<StudentTasks, Long> {
    StudentTasks findByName(String name);
}
