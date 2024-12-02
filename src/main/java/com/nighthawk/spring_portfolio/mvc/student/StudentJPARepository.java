package com.nighthawk.spring_portfolio.mvc.student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface StudentJPARepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByName(String name);
    @Query(
        value = "SELECT * FROM students WHERE course = :course AND trimester = :trimester AND period = :period AND table_number = :table",
        nativeQuery = true
    )
    List<Student> findTeam(
        @Param("course") String course, 
        @Param("trimester") int trimester, 
        @Param("period") int period,
        @Param("table") int table
    );

    @Query(
        value = "SELECT * FROM students WHERE course = :course AND trimester = :trimester AND period = :period",
        nativeQuery = true
    )
    List<Student> findPeriod(
        @Param("course") String course, 
        @Param("trimester") int trimester, 
        @Param("period") int period
    );



    @Query(
        value = "SELECT * FROM students WHERE name = :name AND course = :course AND trimester = :trimester AND period = :period",
        nativeQuery = true
    )
    List<Student> findByNameCourseTrimesterPeriod(
        @Param("name") String name, 
        @Param("course") String course, 
        @Param("trimester") int trimester, 
        @Param("period") int period
    );
}
