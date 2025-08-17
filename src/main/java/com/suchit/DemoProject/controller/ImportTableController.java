package com.suchit.DemoProject.controller;

import com.suchit.DemoProject.service.CSVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/table")
public class ImportTableController {

    private CSVService csvService;

    public ImportTableController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/uploadcsv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            String html = csvService.convertCsvToHtml(file);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
