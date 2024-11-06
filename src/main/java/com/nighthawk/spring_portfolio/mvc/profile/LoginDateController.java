package com.nighthawk.spring_portfolio.mvc.profile;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LoginDateController {

    @Autowired
    private ProfileJpaRepository profileJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProfileApiController.class);

    @PostMapping("/login")
    public String loginUser(@RequestBody Profile profile) {
        // Retrieve the profile from the database using email
        Optional<Profile> userProfileOptional = profileJpaRepository.findByEmail(profile.getEmail());

        if (userProfileOptional.isPresent()) {
            // Get the Profile object from Optional
            Profile userProfile = userProfileOptional.get();

            // Set the login date
            userProfile.setDate(LocalDate.now());

            // Save updated profile to repository
            profileJpaRepository.save(userProfile);

            return "Login successful for " + userProfile.getName() + " on " + userProfile.getDate();
        }
        return "Login failed";
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getProfile(@PathVariable String email) {
        try {
            Optional<Profile> userProfileOptional = profileJpaRepository.findByEmail(email);

            if (userProfileOptional.isPresent()) {
                Profile userProfile = userProfileOptional.get();
                return ResponseEntity.ok(userProfile);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching profile for email {}: {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the profile.");
        }
    }
}
