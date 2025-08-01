package com.example.collegepredictor.service;

import com.example.collegepredictor.config.ExcelDataLoader;
import com.example.collegepredictor.model.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.example.collegepredictor.dto.BranchDto;

@Service
public class CollegeService {

    @Autowired
    private ExcelDataLoader excelDataLoader;

    public List<College> predictColleges(int rank, String gender, String category, List<String> branches) {
        List<College> allColleges = excelDataLoader.getColleges();

        int eligibleRank = rank - 500;
        List<String> branchFilter = (branches == null) ? List.of() : branches;
        boolean noBranchFilter = branchFilter.isEmpty();

        return allColleges.stream()
                .filter(college -> noBranchFilter || branchFilter.contains(college.getBranchCode()))
                .filter(college -> {
                    int cutoffRank = getCutoffRank(college, gender, category);
                    return eligibleRank <= cutoffRank;
                })
                .sorted(Comparator.comparingInt(college -> getCutoffRank(college, gender, category)))
                .collect(Collectors.toList());
    }

    private int getCutoffRank(College college, String gender, String category) {
        if (category == null || gender == null) {
            return Integer.MAX_VALUE;
        }

        switch (category.toUpperCase()) {
            case "OC":
                return "MALE".equalsIgnoreCase(gender) ? college.getOcBoys() : college.getOcGirls();
            case "BC-A":
                return "MALE".equalsIgnoreCase(gender) ? college.getBcaBoys() : college.getBcaGirls();
            case "BC-B":
                return "MALE".equalsIgnoreCase(gender) ? college.getBcbBoys() : college.getBcbGirls();
            case "BC-C":
                return "MALE".equalsIgnoreCase(gender) ? college.getBccBoys() : college.getBccGirls();
            case "BC-D":
                return "MALE".equalsIgnoreCase(gender) ? college.getBcdBoys() : college.getBcdGirls();
            case "BC-E":
                return "MALE".equalsIgnoreCase(gender) ? college.getBceBoys() : college.getBceGirls();
            case "SC":
                return "MALE".equalsIgnoreCase(gender) ? college.getScBoys() : college.getScGirls();
            case "ST":
                return "MALE".equalsIgnoreCase(gender) ? college.getStBoys() : college.getStGirls();
            case "EWS":
                return "MALE".equalsIgnoreCase(gender) ? college.getEwsBoys() : college.getEwsGirls();
            default:
                return Integer.MAX_VALUE;
        }
    }

    public List<BranchDto> getAllBranchesWithNames() {
        return excelDataLoader.getColleges().stream()
                .collect(Collectors.toMap(
                        College::getBranchCode,
                        College::getBranchName,
                        (name1, name2) -> name1))
                .entrySet().stream()
                .map(e -> new BranchDto(e.getKey(), e.getValue()))
                .sorted((a, b) -> a.code.compareTo(b.code))
                .collect(Collectors.toList());
    }
}