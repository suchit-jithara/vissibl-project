package com.suchit.DemoProject.service;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class CSVService {

    public String convertCsvToHtml(MultipartFile file) throws Exception {
        StringBuilder htmlBuilder = new StringBuilder();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            htmlBuilder.setLength(0);
            List<String[]> rows = reader.readAll();

            htmlBuilder.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");

            for (int i = 0; i < rows.size(); i++) {
                htmlBuilder.append("<tr>");
                for (String cell : rows.get(i)) {
                    if (i == 0) {
                        htmlBuilder.append("<th style='padding: 8px; background-color: #f2f2f2;'>")
                                .append(escapeHtml(cell))
                                .append("</th>");
                    } else {
                        htmlBuilder.append("<td style='padding: 8px;'>")
                                .append(escapeHtml(cell))
                                .append("</td>");
                    }
                }
                htmlBuilder.append("</tr>");
            }

            htmlBuilder.append("</table>");
        }

        return htmlBuilder.toString();
    }

    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
