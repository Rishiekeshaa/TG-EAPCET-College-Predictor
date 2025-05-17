package com.example.collegepredictor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "colleges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class College {
    @Id
    private String instCode;
    private String instituteName;
    private String place;
    private String distCode;
    private String education;
    private String collegeType;
    private String yearOfEstab;
    private String branchCode;
    private String branchName;
    private int ocBoys;
    private int ocGirls;
    private int bcaBoys;
    private int bcaGirls;
    private int bcbBoys;
    private int bcbGirls;
    private int bccBoys;
    private int bccGirls;
    private int bcdBoys;
    private int bcdGirls;
    private int bceBoys;
    private int bceGirls;
    private int scBoys;
    private int scGirls;
    private int stBoys;
    private int stGirls;
    private int ewsBoys;
    private int ewsGirls;
    private double tuitionFee;
    private String affiliatedTo;
} 