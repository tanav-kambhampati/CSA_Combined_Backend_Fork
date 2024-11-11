package com.nighthawk.spring_portfolio.mvc.seedtracker;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeedJpaRepository extends JpaRepository<Seed, Long> {

    // Finds all seeds associated with a specific studentId
    List<Seed> findByStudentId(Long studentId);

    // Finds seeds where the comment contains the given keyword, case-insensitive
    List<Seed> findByCommentContainingIgnoreCase(String keyword);

    // Finds seeds by exact name match
    List<Seed> findByName(String name);

    // Finds seeds where the name contains the given keyword, case-insensitive
    List<Seed> findByNameContainingIgnoreCase(String keyword);

    // Custom query to find seeds where the comment, student ID, or name matches the search term
    @Query(
        value = "SELECT * FROM Seed s WHERE s.comment LIKE %:term% OR CAST(s.student_id AS CHAR) LIKE %:term% OR s.name LIKE %:term%",
        nativeQuery = true
    )
    List<Seed> findByCommentOrStudentIdOrNameNative(@Param("term") String term);
}

