package com.example.collegepredictor.controller;

import com.example.collegepredictor.model.College;
import com.example.collegepredictor.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/api/branches")
    public List<String> getBranches() {
        return collegeService.getAllBranches();
    }

    @PostMapping("/api/predict")
    public List<College> predictColleges(
            @RequestParam int rank,
            @RequestParam String gender,
            @RequestParam String category,
            @RequestParam String phase,
            @RequestParam(required = false) List<String> branches) {
        return collegeService.predictColleges(rank, gender, category, phase, branches);
    }
}