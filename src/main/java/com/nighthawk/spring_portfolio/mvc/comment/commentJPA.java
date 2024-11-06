package com.nighthawk.spring_portfolio.mvc.comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; 

public interface commentJPA extends JpaRepository<comment, Long> {
    List<comment> findByAuthor(String author); // Method to find announcement by author

    List<comment> findByAssignment(String assignment);

    List<comment> findAllByOrderByTimestampDesc();
}
