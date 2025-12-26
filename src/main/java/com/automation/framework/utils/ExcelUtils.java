package com.automation.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    public static List<Map<String, String>> getTestData(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> testData = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Map<String, String> data = new HashMap<>();
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                Cell headerCell = headerRow.getCell(j);
                Cell dataCell = row.getCell(j);
                String header = headerCell.getStringCellValue();
                String value = dataCell.getStringCellValue();
                data.put(header, value);
            }
            testData.add(data);
        }
        workbook.close();
        fis.close();
        return testData;
    }
}