package com.jm.batch.service;

import com.jm.common.entity.Contractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParserService {
        private static final Logger LOG = LoggerFactory.getLogger(ExcelParserService.class);
    public List<Contractor> parse(String filePath) throws IOException {
        File file = new File(filePath);

        // Validate file exists
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        // Validate file extension
        if (!filePath.endsWith(".xlsx") && !filePath.endsWith(".xls")) {
            throw new IOException("Unsupported file type. Only .xlsx and .xls files are supported. Provided: " + filePath);
        }

        // Validate file size (POI may fail on empty files)
        if (file.length() == 0) {
            throw new IOException("File is empty: " + filePath);
        }

        LOG.info("Parsing Excel file: {} (size: {} bytes)", filePath, file.length());

        List<Contractor> contractors = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Contractor c = new Contractor();
                c.setCnum(row.getCell(0).getStringCellValue());
                c.setFirstName(row.getCell(1).getStringCellValue());
                c.setLastName(row.getCell(2).getStringCellValue());
                c.setDateOfJoining(row.getCell(3).getLocalDateTimeCellValue().toLocalDate());
                c.setPersonalEmailId(row.getCell(4).getStringCellValue());
                c.setEmpType(row.getCell(5).getStringCellValue());
                c.setManagerEmail(row.getCell(6).getStringCellValue());
                c.setCity(row.getCell(7).getStringCellValue());
                c.setCampus(row.getCell(8).getStringCellValue());
                c.setBuilding(row.getCell(9).getStringCellValue());
                c.setFloor(row.getCell(10).getStringCellValue());
                c.setEndDate(row.getCell(11).getLocalDateTimeCellValue().toLocalDate());
                c.setBusinessUnit(row.getCell(12).getStringCellValue());
                c.setAgencyName(row.getCell(13).getStringCellValue());
                contractors.add(c);
            }
        } catch (Exception e) {
            String errorMsg = String.format(
                    "Failed to parse Excel file '%s': %s. Root cause: %s",
                    filePath, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "Unknown"
            );
            LOG.error(errorMsg, e);
            throw new IOException(errorMsg, e);
        }

        return contractors;
    }
}

