package com.nighthawk.spring_portfolio.mvc.seedtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/seeds")
public class SeedApiController {

    @Autowired
    private SeedJpaRepository seedJpaRepository; // Ensure this is your repository for Seed data

    private SeedRole seedRole = new SeedRole(); // Initialize seedRole for role management

    // POST method to add a new seed
    @PostMapping("/")
    public Seed addSeed(@RequestBody Seed seed) {
        return seedJpaRepository.save(seed);
    }

    // GET method to retrieve seeds by student ID
    @GetMapping("/{studentId}")
    public List<Seed> getSeedsByStudentId(@PathVariable Long studentId) {
        return seedJpaRepository.findByStudentId(studentId);
    }

    // PUT method to update an existing seed
    @PutMapping("/{id}")
    public Seed updateSeed(@PathVariable Long id, @RequestBody Seed updatedSeed) {
        return seedJpaRepository.findById(id)
                .map(seed -> {
                    seed.setComment(updatedSeed.getComment());
                    seed.setGrade(updatedSeed.getGrade()); // Make sure your model has setGrade()
                    return seedJpaRepository.save(seed);
                })
                .orElseThrow(() -> new RuntimeException("Seed not found with id " + id));
    }

    // DELETE method to delete a seed by its ID
    @DeleteMapping("/{id}")
    public void deleteSeed(@PathVariable Long id) {
        seedJpaRepository.deleteById(id);
    }
    
    // PUT method to assign a role to a user
    @PutMapping("/roles/assign")
    public String assignRoleToUser(@RequestParam Long studentId, @RequestParam String roleName) {
        Seed seed = seedJpaRepository.findByStudentId(studentId);
        if (seed != null) {
            PersonRole role = new PersonRole(roleName); // Create a new role
            seed.getRoles().add(role); // Add the role to the user's roles
            seedJpaRepository.save(seed); // Save the updated user
            return "Role '" + roleName + "' has been assigned to user with student ID " + studentId;
        } else {
            return "User not found with student ID: " + studentId;
        }
    }

    // GET method to retrieve all roles
    @GetMapping("/roles")
    public Set<PersonRole> getRoles() {
        return seedRole.getRoles();
    }
}
