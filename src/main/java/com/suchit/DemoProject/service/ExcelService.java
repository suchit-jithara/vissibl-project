package com.suchit.DemoProject.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ExcelService {

    public byte[] generateExcelFromHtml(String html) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Exported Data");

        Document doc = Jsoup.parse(html);
        Element table = doc.selectFirst("table");

        if (table == null) {
            throw new IllegalArgumentException("No <table> found in HTML");
        }

        Elements rows = table.select("tr");
        int rowNum = 0;

        for (Element row : rows) {
            Row excelRow = sheet.createRow(rowNum++);
            Elements cells = row.select("th, td");
            int colNum = 0;
            for (Element cell : cells) {
                Cell excelCell = excelRow.createCell(colNum++);
                excelCell.setCellValue(cell.text());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
