package com.nighthawk.spring_portfolio.mvc.bathroom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 

public interface AdminJPARepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByStudentEmail(String studentEmail);
}
