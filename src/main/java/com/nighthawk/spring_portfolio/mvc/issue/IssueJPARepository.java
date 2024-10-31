package com.nighthawk.spring_portfolio.mvc.issue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueJPARepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByIssueAndBathroom(String issue, String bathroom);
}
