package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueueJPARepository extends JpaRepository<Queue, Long> {
    Optional<Queue> findByTeacherName(String teacherName);
    List<Queue> findByPositionGreaterThan(int position);
    List<Queue> findAllByOrderByPositionAsc();
    
    @Query("SELECT MAX(q.position) FROM Queue q")
    Integer findMaxPosition();
}