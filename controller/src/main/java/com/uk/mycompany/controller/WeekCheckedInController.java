package com.uk.mycompany.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.resources.AnalyticsWeekCurrentResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;

/**
 * Created by mahutton on 06/04/2017.
 */
@RestController
public class WeekCheckedInController {

    private static final Logger logger = LogManager.getLogger(WeekCheckedInController.class);

    @RequestMapping(value = "/checkin/week", method = RequestMethod.GET)
    public String checkinWeekAnalytics(@RequestParam(value = "location", defaultValue = "") final String location) {

        int row = 0;
        final int column = 0;

        try {

            FileOutputStream out = new FileOutputStream("workbook.xls");
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();
            wb.setSheetName(0, "test");
//            createColumnHeaders(s);

            int dayOfWeekColumn = 0;

            Row spreadsheetRow = sheet.createRow(0);

            for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
                spreadsheetRow.createCell(dayOfWeekColumn++).setCellValue(dayOfWeek.toString());
            }

            AnalyticsWeekCurrentResource analyticsWeekCurrentResource = new AnalyticsWeekCurrentResource();

            String test = analyticsWeekCurrentResource.get();

            ObjectMapper objectMapper = new ObjectMapper();

            final JsonNode jsonNode = objectMapper.readValue(test, JsonNode.class);

            if (jsonNode.isArray()) {
                for (final JsonNode node : jsonNode) {

                    final String username = node.get("username").asText();

                    row++;
                    sheet.createRow(row).createCell(column).setCellValue(username);

                    logger.trace("Writing " + username + " to row: " + row + " column: " + column);


                }
            }

            wb.write(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "test!";
    }

    private void createColumnHeaders(final Sheet sheet) {

        final int rowNumber = 0;
        int column = 0;

        for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {

            final Row row = sheet.createRow(rowNumber);

            row.createCell(column++).setCellValue(dayOfWeek.toString());
        }
    }
}
