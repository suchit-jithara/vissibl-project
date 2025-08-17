package com.suchit.DemoProject.service;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
@Service
public class CSVService {

    public byte[] generateCsvFromHtml(String html) throws Exception {
        StringBuilder sb = new StringBuilder();

        Document doc = Jsoup.parse(html);
        Element table = doc.selectFirst("table");

        if (table == null) {
            throw new IllegalArgumentException("No <table> found in HTML");
        }

        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements cells = row.select("th, td");

            for (int i = 0; i < cells.size(); i++) {
                String text = cells.get(i).text();

                // Escape commas, quotes, and newlines for CSV safety
                if (text.contains(",") || text.contains("\"") || text.contains("\n")) {
                    text = "\"" + text.replace("\"", "\"\"") + "\"";
                }

                sb.append(text);
                if (i < cells.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }

        // Convert to bytes with UTF-8 encoding
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}
