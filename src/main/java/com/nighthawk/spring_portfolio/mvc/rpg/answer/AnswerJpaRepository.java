package com.nighthawk.spring_portfolio.mvc.rpg.answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long question);
    List<Answer> findByUserId(Integer userid);

    @Query("SELECT NEW com.nighthawk.spring_portfolio.mvc.rpg.answer.LeaderboardDto(u.id, SUM(a.chatScore)) FROM Answer a JOIN User u ON a.id = u.id GROUP BY u.id ORDER BY SUM(a.chatScore) DESC")
    List<LeaderboardDto> findTop10UsersByTotalScore();
}
