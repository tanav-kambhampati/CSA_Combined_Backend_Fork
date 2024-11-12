package com.nighthawk.spring_portfolio.mvc.seedtracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seeds")
public class SeedApiController {

    @Autowired
    private SeedJpaRepository seedJpaRepository;

    // Add a new seed entry
    @PostMapping("/")
    public Seed addSeed(@RequestBody Seed seed) {
        return seedJpaRepository.save(seed);
    }

    // Retrieve seeds by studentId
    @GetMapping("/{studentId}")
    public List<Seed> getSeedsByStudentId(@PathVariable Long studentId) {
        return seedJpaRepository.findByStudentId(studentId);
    }

    // Update a seed entry by id
    @PutMapping("/{id}")
    public Seed updateSeed(@PathVariable Long id, @RequestBody Seed updatedSeed) {
        return seedJpaRepository.findById(id)
                .map(seed -> {
                    seed.setName(updatedSeed.getName()); // Set the name field
                    seed.setComment(updatedSeed.getComment());
                    seed.setGrade(updatedSeed.getGrade());
                    return seedJpaRepository.save(seed);
                })
                .orElseThrow(() -> new RuntimeException("Seed not found with id " + id));
    }

    // Delete a seed entry by id
    @DeleteMapping("/{id}")
    public void deleteSeed(@PathVariable Long id) {
        seedJpaRepository.deleteById(id);
    }

    @GetMapping("/")
    public List<Seed> getAllSeeds() {
        return seedJpaRepository.findAll();
    }
}