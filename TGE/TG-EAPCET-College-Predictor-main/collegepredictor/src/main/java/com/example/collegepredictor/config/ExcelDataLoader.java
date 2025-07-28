package com.example.collegepredictor.config;

import com.example.collegepredictor.model.College;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelDataLoader.class);
    private static final String EXCEL_FILE_PATH = "static/college_data.xlsx";
    private List<College> colleges = new ArrayList<>();

    @PostConstruct
    public void loadExcelData() throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource(EXCEL_FILE_PATH);
            if (!resource.exists()) {
                throw new FileNotFoundException("Excel file not found: " + EXCEL_FILE_PATH);
            }

            try (InputStream is = resource.getInputStream();
                 Workbook workbook = new XSSFWorkbook(is)) {

                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    throw new IllegalStateException("Excel file has no sheets");
                }

                logger.info("Processing {} rows", sheet.getLastRowNum());

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Skip header row

                    try {
                        College college = new College();
                        college.setInstCode(getStringValue(row.getCell(0)));
                        college.setInstituteName(getStringValue(row.getCell(1)));
                        college.setPlace(getStringValue(row.getCell(2)));
                        college.setDistCode(getStringValue(row.getCell(3)));
                        college.setEducation(getStringValue(row.getCell(4)));
                        college.setCollegeType(getStringValue(row.getCell(5)));
                        college.setYearOfEstab(getStringValue(row.getCell(6)));
                        college.setBranchCode(getStringValue(row.getCell(7)));
                        college.setBranchName(getStringValue(row.getCell(8)));
                        college.setOcBoys(getIntValue(row.getCell(9)));
                        college.setOcGirls(getIntValue(row.getCell(10)));
                        college.setBcaBoys(getIntValue(row.getCell(11)));
                        college.setBcaGirls(getIntValue(row.getCell(12)));
                        college.setBcbBoys(getIntValue(row.getCell(13)));
                        college.setBcbGirls(getIntValue(row.getCell(14)));
                        college.setBccBoys(getIntValue(row.getCell(15)));
                        college.setBccGirls(getIntValue(row.getCell(16)));
                        college.setBcdBoys(getIntValue(row.getCell(17)));
                        college.setBcdGirls(getIntValue(row.getCell(18)));
                        college.setBceBoys(getIntValue(row.getCell(19)));
                        college.setBceGirls(getIntValue(row.getCell(20)));
                        college.setScBoys(getIntValue(row.getCell(21)));
                        college.setScGirls(getIntValue(row.getCell(22)));
                        college.setStBoys(getIntValue(row.getCell(23)));
                        college.setStGirls(getIntValue(row.getCell(24)));
                        college.setEwsBoys(getIntValue(row.getCell(25)));
                        college.setEwsGirls(getIntValue(row.getCell(26)));
                        college.setTuitionFee(getDoubleValue(row.getCell(27)));
                        college.setAffiliatedTo(getStringValue(row.getCell(28)));

                        colleges.add(college);
                    } catch (Exception e) {
                        logger.error("Error processing row {}: {}", row.getRowNum(), e.getMessage());
                    }
                }

                logger.info("Successfully loaded {} colleges", colleges.size());
            }
        } catch (FileNotFoundException e) {
            logger.error("Excel file not found: {}", e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("Failed to load Excel file: {}", e.getMessage());
            throw new IOException("Failed to load Excel file: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while loading Excel file: {}", e.getMessage());
            throw new IllegalStateException("Failed to load Excel data: " + e.getMessage());
        }
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf((int) cell.getNumericCellValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private int getIntValue(Cell cell) {
        if (cell == null) return 0;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    String value = cell.getStringCellValue().trim();
                    return value.isEmpty() ? 0 : Integer.parseInt(value);
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private double getDoubleValue(Cell cell) {
        if (cell == null) return 0.0;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    String value = cell.getStringCellValue().trim();
                    return value.isEmpty() ? 0.0 : Double.parseDouble(value);
                default:
                    return 0.0;
            }
        } catch (Exception e) {
            return 0.0;
        }
    }

    public List<College> getColleges() {
        return colleges;
    }
}