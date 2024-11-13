package com.nighthawk.spring_portfolio.mvc.seedtracker;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:4100")
@RestController
@RequestMapping("/api/seeds")
public class SeedApiController {

    @Autowired
    private SeedJpaRepository seedJpaRepository;

    // Add a new seed entry
    @PostMapping("/")
    public Seed addSeed(@RequestBody Seed seed) {
        if (seed.getStudentId() == null) {
            Long maxStudentId = seedJpaRepository.findMaxStudentId().orElse(0L);
            seed.setStudentId(maxStudentId + 1);
        }
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
                    seed.setName(updatedSeed.getName());
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
