package com.nighthawk.spring_portfolio.mvc.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AnalyticsInitializer implements CommandLineRunner {

    private final AnalyticsJpaRepository analyticsRepository;

    @Autowired
    public AnalyticsInitializer(AnalyticsJpaRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public void run(String... args) {
        if (analyticsRepository.count() == 0) {
            Analytics[] initialData = Analytics.init();  // This should work now
            analyticsRepository.saveAll(Arrays.asList(initialData));
            System.out.println("Initialized Analytics data in the database.");
        } else {
            System.out.println("Analytics data already exists in the database.");
        }
    }
}
