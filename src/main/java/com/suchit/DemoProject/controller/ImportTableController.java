package com.suchit.DemoProject.controller;

import com.suchit.DemoProject.service.CSVService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/table")
public class ImportTableController {

    private final CSVService csvService;

    public ImportTableController(CSVService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/csv/{id}")
    public ResponseEntity<byte[]> exportHtmlAsCsv(@PathVariable("id") Long id) {
        try {
            String htmlFromDb = getHtmlById(id); // your repo logic
            byte[] csvBytes = csvService.generateCsvFromHtml(htmlFromDb);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exported-data.csv")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(csvBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Dummy data — replace with your actual DB fetching logic
    private String getHtmlById(Long id) {
        return """
                <table>
                    <tr><th>Name</th><th>Email</th></tr>
                    <tr><td>Suchit</td><td>suchit@example.com</td></tr>
                    <tr><td>Jay</td><td>jay@example.com</td></tr>
                </table>
                """;
    }
}
