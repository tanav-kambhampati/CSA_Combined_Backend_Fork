package com.nighthawk.spring_portfolio.mvc.profile;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/api/profile")
public class ProfileApiController {

    @Autowired
    private ProfileJpaRepository profileJpaRepository;

    @Getter
    public static class ProfileDto {
        private String email;
        private String password;
        private String name;
        private String date;
    }

    // Endpoint to create a new profile
    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody ProfileDto profileDto) {
        try {
            Profile profile = new Profile(
                profileDto.getName(),
                profileDto.getEmail(),
                profileDto.getPassword(),
                profileDto.getDate()
            );
            profileJpaRepository.save(profile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating profile");
        }
    }

    // Endpoint to authenticate (login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ProfileDto profileDto) {
        Optional<Profile> profile = Optional.ofNullable(profileJpaRepository.findByEmailAndPassword(
                profileDto.getEmail(), profileDto.getPassword()
        ));
        if (profile.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Endpoint to retrieve all profiles
    @GetMapping("/all")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileJpaRepository.findAll();
        return ResponseEntity.ok(profiles);
    }
}
