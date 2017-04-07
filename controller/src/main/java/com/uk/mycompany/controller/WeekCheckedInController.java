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
            createColumnHeaders(sheet);


            AnalyticsWeekCurrentResource analyticsWeekCurrentResource = new AnalyticsWeekCurrentResource();

            String test = analyticsWeekCurrentResource.get();

            ObjectMapper objectMapper = new ObjectMapper();

            final JsonNode jsonNode = objectMapper.readValue(test, JsonNode.class);

            if (jsonNode.isArray()) {
                for (final JsonNode node : jsonNode) {

                    final String username = node.get("username").asText();

                    int i = findFirstUppercase(username);

                    StringBuilder username1 = new StringBuilder(username);

                    if (-1 == i) {
                        username1.insert(i, " ");
                    }

                    row++;
                    sheet.createRow(row).createCell(column).setCellValue(username1.toString());

                    logger.trace("Writing " + username1.toString() + " to row: " + row + " column: " + column);

                    final String dayOfWeekString;

                    for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
                        JsonNode dayNode = node.path(dayOfWeek.toString().substring(0, 1) + dayOfWeek.toString().substring(1, dayOfWeek.toString().length()).toLowerCase());

                        JsonNode checkedInNode = dayNode.findValue("runningCount");

                        if (null != checkedInNode) {
                            sheet.getRow(row).createCell(dayOfWeek.getValue()).setCellValue("Yes");
                        }

                        int h = 0;
                    }

                    //List<JsonNode> test1 = dayOfWeekNode.findValues("runningCount");

                    int a = 0;

//                    dayOfWeekNode.get
//
//                    if (dayOfWeekNode.isObject() ) {
//
//
//
//                        for (JsonNode jsonNode1 :   )
//
//                    }


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

        final Row spreadsheetRow = sheet.createRow(0);

        for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
            spreadsheetRow.createCell(dayOfWeek.getValue()).setCellValue(dayOfWeek.toString().substring(0, 1) + dayOfWeek.toString().substring(1, dayOfWeek.toString().length()).toLowerCase());
        }
    }

    private int findFirstUppercase(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
