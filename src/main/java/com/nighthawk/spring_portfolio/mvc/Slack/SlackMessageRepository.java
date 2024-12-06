package com.nighthawk.spring_portfolio.Slack;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface SlackMessageRepository extends JpaRepository<SlackMessage, LocalDateTime> {}
