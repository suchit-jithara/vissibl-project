package com.suchit.DemoProject.controller;

import com.suchit.DemoProject.service.ExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/table")
public class ImportTableController {

    private final ExcelService excelService;

    public ImportTableController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/xlsx/{id}")
    public ResponseEntity<byte[]> exportHtmlAsExcel(@PathVariable("id") Long id) {
        try {
            // Fetch content from DB using ID — replace with your repo logic
            String htmlFromDb = getHtmlById(id);

            byte[] excelBytes = excelService.generateExcelFromHtml(htmlFromDb);

            System.out.println("done");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exported-data.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);
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
