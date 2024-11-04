package com.nighthawk.spring_portfolio.mvc.seedtracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * The SeedJpaRepository interface is automatically implemented by Spring Data JPA at runtime.
 * It uses the Java Persistence API (JPA) to map, store, update, and retrieve data from relational databases.
 * 
 * This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations.
 * Through the JPA, the developer can store and retrieve Seed objects to and from the database.
 */
public interface SeedJpaRepository extends JpaRepository<Seed, Long> {

    /**
     * Query methods defined by Spring Data JPA naming conventions.
     * Spring Data JPA will automatically generate a query using the method name.
     */
    List<Seed> findByStudentId(Long studentId); // Change Long to String
    //List<Seed> findAllByOrderByCreatedDateDesc(); // Ensure this method corresponds with a createdDate field if used
    List<Seed> findByCommentContainingIgnoreCase(String keyword);

    /**
     * Custom JPA query using the @Query annotation.
     * This allows for more complex queries that can't be expressed through the method name.
     * The query will retrieve all Seed entries where the comment or student ID matches the given term.
     * The 'nativeQuery = true' parameter indicates that the query is a native SQL query, not a JPQL query.
     */
    @Query(
        value = "SELECT * FROM Seed s WHERE s.comment LIKE %:term% OR CAST(s.student_id AS CHAR) LIKE %:term%",
        nativeQuery = true
    )
    List<Seed> findByCommentOrStudentIdNative(@Param("term") String term);
}


