package com.uk.mycompany.spreadsheet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.stream.StreamSupport;

/**
 * Created by mahutton on 08/04/2017.
 */
public class WeeklyCheckInSpreadsheet {

    private static final Logger logger = LogManager.getLogger(WeeklyCheckInSpreadsheet.class);

    private final String dataSource;

    private Workbook workbook;

    private FileOutputStream out;

    //TODO: Need a better package name
    //TODO: Should this be inside a Factory pattern for the different types of spreadsheets that can be created
    // could then be with a public method to write to disk
    public  WeeklyCheckInSpreadsheet(final String datasource) {
        this.dataSource = datasource;
        this.createSpreadSheet();
    }

    private void createSpreadSheet() {

        int row = 0;
        final int column = 0;

        try {

            out = new FileOutputStream("workbook.xls");
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();
            wb.setSheetName(0, "test");
            createColumnHeaders(sheet);

            final ObjectMapper objectMapper = new ObjectMapper();

            final JsonNode jsonNode = objectMapper.readValue(dataSource, JsonNode.class);

            if (jsonNode.isArray()) {

//                long a = StreamSupport.stream(jsonNode.spliterator(), false).forEach();

                for (final JsonNode node : jsonNode) {

                    final String username = node.get("username").asText();

                    int i = findFirstUppercase(username);

                    StringBuilder username1 = new StringBuilder(username);

                    if (-1 != i) {
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
                    }
                }
            }

            this.workbook = wb;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToDirectory() {
        try {
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
