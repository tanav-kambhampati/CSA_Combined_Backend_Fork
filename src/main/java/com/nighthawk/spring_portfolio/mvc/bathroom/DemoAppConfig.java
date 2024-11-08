package com.nighthawk.spring_portfolio.mvc.bathroom;

import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUser;
import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DemoAppConfig {

    @Bean
    public CommandLineRunner initData(DemoUserRepository demoUserRepository) {
        return args -> {
            demoUserRepository.save(new DemoUser(1L, 123456L, "Alice", LocalDate.of(2024,11,4), LocalTime.of(9, 0), LocalTime.of(9, 7)));
            demoUserRepository.save(new DemoUser(2L, 234567L, "Cinder", LocalDate.of(2024,11,4), LocalTime.of(10, 0), LocalTime.of(10, 16)));
            demoUserRepository.save(new DemoUser(3L, 345678L, "Leo", LocalDate.of(2024,11,4), LocalTime.of(10, 20), LocalTime.of(10, 29)));
            demoUserRepository.save(new DemoUser(4L, 123456L, "Alice", LocalDate.of(2024,11,4), LocalTime.of(11, 0), LocalTime.of(11, 9)));
            demoUserRepository.save(new DemoUser(5L, 345678L, "Leo", LocalDate.of(2024,11,4), LocalTime.of(23, 5), LocalTime.of(23, 13)));
            demoUserRepository.save(new DemoUser(6L, 456789L, "Boss", LocalDate.of(2024,11,4), LocalTime.of(13, 0), LocalTime.of(13, 11)));
          
            demoUserRepository.save(new DemoUser(8L, 234567L, "Cinder", LocalDate.of(2024,11,5), LocalTime.of(8, 0), LocalTime.of(8, 12)));
            demoUserRepository.save(new DemoUser(9L, 123456L, "Alice", LocalDate.of(2024,11,5), LocalTime.of(11, 0), LocalTime.of(11, 13)));
            demoUserRepository.save(new DemoUser(10L, 345678L, "Leo", LocalDate.of(2024,11,5), LocalTime.of(12, 10), LocalTime.of(12, 18)));
            demoUserRepository.save(new DemoUser(11L, 456789L, "Boss", LocalDate.of(2024,11,5), LocalTime.of(13, 10), LocalTime.of(13, 22)));
            demoUserRepository.save(new DemoUser(12L, 345678L, "Leo", LocalDate.of(2024,11,5), LocalTime.of(18, 20), LocalTime.of(18, 27)));

            demoUserRepository.save(new DemoUser(13L, 123456L, "Alice", LocalDate.of(2024,11,6), LocalTime.of(11, 45), LocalTime.of(11, 59)));
            demoUserRepository.save(new DemoUser(14L, 345678L, "Leo", LocalDate.of(2024,11,6), LocalTime.of(12, 10), LocalTime.of(12, 14)));
            demoUserRepository.save(new DemoUser(15L, 234567L, "Cinder", LocalDate.of(2024,11,6), LocalTime.of(9, 30), LocalTime.of(9, 42)));
            demoUserRepository.save(new DemoUser(16L, 456789L, "Boss", LocalDate.of(2024,11,6), LocalTime.of(14, 20), LocalTime.of(14, 33)));
            demoUserRepository.save(new DemoUser(0L, 345678L, "Leo", LocalDate.of(2024,11,6), LocalTime.of(18, 8), LocalTime.of(18, 15)));

            demoUserRepository.save(new DemoUser(17L, 123456L, "Alice", LocalDate.of(2024,11,7), LocalTime.of(9, 0), LocalTime.of(9, 11)));
            demoUserRepository.save(new DemoUser(18L, 234567L, "Cinder", LocalDate.of(2024,11,7), LocalTime.of(10, 0), LocalTime.of(10, 16)));
            demoUserRepository.save(new DemoUser(19L, 345678L, "Leo", LocalDate.of(2024,11,7), LocalTime.of(10, 20), LocalTime.of(10, 33)));
            demoUserRepository.save(new DemoUser(20L, 123456L, "Alice", LocalDate.of(2024,11,7), LocalTime.of(11, 0), LocalTime.of(11, 9)));
            demoUserRepository.save(new DemoUser(21L, 345678L, "Leo", LocalDate.of(2024,11,7), LocalTime.of(23, 5), LocalTime.of(23, 11)));
            demoUserRepository.save(new DemoUser(22L, 456789L, "Boss", LocalDate.of(2024,11,7), LocalTime.of(13, 0), LocalTime.of(13, 12)));

            demoUserRepository.save(new DemoUser(23L, 234567L, "Cinder", LocalDate.of(2024,11,8), LocalTime.of(10, 45), LocalTime.of(10, 52)));
            demoUserRepository.save(new DemoUser(24L, 345678L, "Leo", LocalDate.of(2024,11,8), LocalTime.of(10, 22), LocalTime.of(10, 29)));
            demoUserRepository.save(new DemoUser(25L, 456789L, "Boss", LocalDate.of(2024,11,8), LocalTime.of(13, 3), LocalTime.of(13, 11)));
            demoUserRepository.save(new DemoUser(26L, 123456L, "Alice", LocalDate.of(2024,11,8), LocalTime.of(14, 6), LocalTime.of(14, 20)));
        
        };
    }
}
