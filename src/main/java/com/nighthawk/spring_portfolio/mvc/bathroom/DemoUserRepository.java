package com.nighthawk.spring_portfolio.mvc.bathroom;

import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DemoUserRepository extends JpaRepository<DemoUser, Long> {
    List<DemoUser> findByUserName(String userName);
    List<DemoUser> findByUserId(Long userId);
}
