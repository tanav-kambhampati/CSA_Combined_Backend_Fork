package com.nighthawk.spring_portfolio.mvc.profile;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByEmail(String email); // This returns an Optional<Profile>
    Profile findByEmailAndPassword(String email, String password);  // Corrected method name
    @Query(
            value = "SELECT * FROM Profile p WHERE p.name LIKE ?1 OR p.email LIKE ?1",
            nativeQuery = true)
    List<Profile> findByLikeTermNative(String term);
}
