package com.nighthawk.spring_portfolio.mvc.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.Entity;

import java.util.List;

public interface UserAssignmentsJpaRepository extends JpaRepository<UserAssignments, Long> {
    //void save(Integer userid, Integer assignmentid);
    List<UserAssignments> findAll();

    @Query("SELECT new com.nighthawk.spring_portfolio.mvc.Grades.UserAssignmentsSummary(p.name, a.assignmentName, ua.grade) " +
           "FROM UserAssignments ua " +
           "JOIN Person p ON ua.userid = p.id " +
           "JOIN Assignment a ON ua.assignmentid = a.id " +
           "WHERE p.id = :userid") // Connecting tables together using SQL
    List<UserAssignmentsSummary> findUserAssignmentsSummaries(@Param("userid") int userid); // Userid used in order to find the list of user assignments
}
