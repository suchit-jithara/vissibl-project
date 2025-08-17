package com.suchit.DemoProject.controller;

import com.suchit.DemoProject.service.ExcelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/table")
public class ImportTableController {

    private final ExcelService excelService;

    public ImportTableController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/uploadxlsx")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            String html = excelService.convertExcelToHtml(file);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Excel file: " + e.getMessage());
        }
    }
}
