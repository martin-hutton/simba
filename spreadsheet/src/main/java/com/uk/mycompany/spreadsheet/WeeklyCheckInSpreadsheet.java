package com.uk.mycompany.spreadsheet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.shared.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Created by mahutton on 08/04/2017.
 */
public class WeeklyCheckInSpreadsheet {

    private static final int NO_UPPERCASE_CHAR_FOUND = -1;

    private static final int FIRST_COLUMN = 0;

    private static final int SECOND_ROW = 0;

    private static final Logger logger = LogManager.getLogger(WeeklyCheckInSpreadsheet.class);

    private final String dataSource;

    private Workbook workbook;

    private FileOutputStream out;

    //TODO: Need a better package name
    //TODO: Should this be inside a Factory pattern for the different types of spreadsheets that can be created
    // could then be with a public method to write to disk
    public WeeklyCheckInSpreadsheet(final String datasource) {
        this.dataSource = datasource;
        this.createSpreadSheet();
    }

    private void createSpreadSheet() {


        try {

            out = new FileOutputStream("workbook.xls");
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();
            wb.setSheetName(0, "test");
            createColumnHeaders(sheet);

            final ObjectMapper objectMapper = new ObjectMapper();

            final JsonNode jsonNode = objectMapper.readValue(dataSource, JsonNode.class);

            if (jsonNode.isArray()) {

                StreamSupport.stream(jsonNode.spliterator(), false).forEach(

                        node -> {
                            final int column = FIRST_COLUMN;
                            int row = SECOND_ROW;
                            final String formattedUsername = formatUsername(node.get("username").asText());

                            row++;
                            insertStringToCell(sheet, row, column, formattedUsername);

                            for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
                                final JsonNode dayNode = node.path(StringUtils.formatStringToCamelCase(dayOfWeek.toString()));
                                final Optional<JsonNode> checkedInNode = Optional.ofNullable(dayNode.findValue("runningCount"));

                                if (checkedInNode.isPresent()) {
                                    sheet.getRow(row).createCell(dayOfWeek.getValue()).setCellValue("Yes");
                                }
                            }


                        }
                );

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

    private void insertStringToCell(final Sheet sheet, final int row, final int column, final String text) {
        sheet.createRow(row).createCell(column).setCellValue(text);
    }

    private void createColumnHeaders(final Sheet sheet) {

        final Row spreadsheetRow = sheet.createRow(0);

        for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
            spreadsheetRow.createCell(dayOfWeek.getValue()).setCellValue(dayOfWeek.toString().substring(0, 1) + dayOfWeek.toString().substring(1, dayOfWeek.toString().length()).toLowerCase());
        }
    }

    private int findFirstUppercaseCharacter(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private String formatUsername(final String username) {

        int charIndex = findFirstUppercaseCharacter(username);

        StringBuilder usernameStringBuilder = new StringBuilder(username);

        if (NO_UPPERCASE_CHAR_FOUND != charIndex) {
            usernameStringBuilder.insert(charIndex, " ");
        }

        return usernameStringBuilder.toString();
    }
}
