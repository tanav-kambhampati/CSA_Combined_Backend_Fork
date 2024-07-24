package com.nighthawk.spring_portfolio.mvc.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface AnnouncementJPA extends JpaRepository<Announcement, Long> {
    Announcement findByAuthor(String author); // Method to find announcement by author

    List<Announcement> findByTags(String tags); // Example method to find announcements by tags

    List<Announcement> findAllByOrderByTimestampDesc();
}
