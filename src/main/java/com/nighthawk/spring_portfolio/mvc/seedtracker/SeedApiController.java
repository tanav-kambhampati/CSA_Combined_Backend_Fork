package com.nighthawk.spring_portfolio.mvc.seedtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seeds")
public class SeedApiController {

    @Autowired
    private SeedJpaRepository SeedJpaRepository; // Change variable name to camelCase

    @PostMapping("/")
    public Seed addSeed(@RequestBody Seed seed) {
        return SeedJpaRepository.save(seed);
    }

    @GetMapping("/{studentId}")
    public List<Seed> getSeedsByStudentId(@PathVariable Long studentId) { // Change Long to String
        return SeedJpaRepository.findByStudentId(studentId); // Match the type with your repository
    }

    @PutMapping("/{id}")
    public Seed updateSeed(@PathVariable Long id, @RequestBody Seed updatedSeed) {
        return SeedJpaRepository.findById(id)
                .map(seed -> {
                    seed.setComment(updatedSeed.getComment());
                    seed.setGrade(updatedSeed.getGrade()); // Change from setSeedCount() to setGrade()
                    return SeedJpaRepository.save(seed);
                })
                .orElseThrow(() -> new RuntimeException("Seed not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteSeed(@PathVariable Long id) {
        SeedJpaRepository.deleteById(id);
    }
}

