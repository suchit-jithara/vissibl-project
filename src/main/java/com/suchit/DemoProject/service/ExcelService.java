package com.suchit.DemoProject.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ExcelService {

    public String convertExcelToHtml(MultipartFile file) throws Exception {
        StringBuilder html = new StringBuilder();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // You can loop over multiple sheets if needed
            html.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");

            for (Row row : sheet) {
                html.append("<tr>");
                for (Cell cell : row) {
                    String cellValue = getCellValue(cell);

                    if (row.getRowNum() == 0) {
                        html.append("<th style='padding: 8px; background-color: #f2f2f2;'>")
                                .append(escapeHtml(cellValue))
                                .append("</th>");
                    } else {
                        html.append("<td style='padding: 8px;'>")
                                .append(escapeHtml(cellValue))
                                .append("</td>");
                    }
                }
                html.append("</tr>");
            }

            html.append("</table>");
        }
        return html.toString();
    }

    private String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell) ?
                    cell.getDateCellValue().toString() :
                    String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private String escapeHtml(String input) {
        return input == null ? "" : input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
