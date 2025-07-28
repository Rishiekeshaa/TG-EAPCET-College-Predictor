package com.example.collegepredictor.repository;

import com.example.collegepredictor.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CollegeRepository extends JpaRepository<College, String> {

    @Query("SELECT c FROM College c WHERE " +
            "(:branchCode IS NULL OR c.branchCode = :branchCode) AND " +
            "(:category IS NULL OR (" +
            "(:gender = 'Male' AND (" +
            "(:category = 'OC' AND c.ocBoys >= :rank) OR " +
            "(:category = 'BC-A' AND c.bcaBoys >= :rank) OR " +
            "(:category = 'BC-B' AND c.bcbBoys >= :rank) OR " +
            "(:category = 'BC-C' AND c.bccBoys >= :rank) OR " +
            "(:category = 'BC-D' AND c.bcdBoys >= :rank) OR " +
            "(:category = 'BC-E' AND c.bceBoys >= :rank) OR " +
            "(:category = 'SC' AND c.scBoys >= :rank) OR " +
            "(:category = 'ST' AND c.stBoys >= :rank) OR " +
            "(:category = 'EWS' AND c.ewsBoys >= :rank)" +
            ")) OR " +
            "(:gender = 'Female' AND (" +
            "(:category = 'OC' AND c.ocGirls >= :rank) OR " +
            "(:category = 'BC-A' AND c.bcaGirls >= :rank) OR " +
            "(:category = 'BC-B' AND c.bcbGirls >= :rank) OR " +
            "(:category = 'BC-C' AND c.bccGirls >= :rank) OR " +
            "(:category = 'BC-D' AND c.bcdGirls >= :rank) OR " +
            "(:category = 'BC-E' AND c.bceGirls >= :rank) OR " +
            "(:category = 'SC' AND c.scGirls >= :rank) OR " +
            "(:category = 'ST' AND c.stGirls >= :rank) OR " +
            "(:category = 'EWS' AND c.ewsGirls >= :rank)" +
            "))))")
    List<College> findCollegesByCriteria(
            @Param("branchCode") String branchCode,
            @Param("category") String category,
            @Param("gender") String gender,
            @Param("rank") int rank
    );
}