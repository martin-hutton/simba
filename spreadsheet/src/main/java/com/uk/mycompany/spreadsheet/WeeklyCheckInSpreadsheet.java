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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Optional;

/**
 * Created by mahutton on 08/04/2017.
 */
public class WeeklyCheckInSpreadsheet {

    private static final int NO_UPPERCASE_CHAR_FOUND = -1;

    private static final int FIRST_COLUMN = 0;

    private static final int SECOND_ROW = 1;

    private static final Logger logger = LogManager.getLogger(WeeklyCheckInSpreadsheet.class);

    private final String dataSource;

    private Workbook workbook = new HSSFWorkbook();

    private FileOutputStream fileOutputStream = new FileOutputStream("AIE.xls");

    final ObjectMapper objectMapper = new ObjectMapper();

    //TODO: Need a better package name
    //TODO: Should this be inside a Factory pattern for the different types of spreadsheets that can be created
    // could then be with a public method to write to disk
    public WeeklyCheckInSpreadsheet(final String datasource) throws FileNotFoundException {
        this.dataSource = datasource;
        this.createSheet();
    }

    private void createSheet() {
        try {
            final Sheet sheet = workbook.createSheet();
            workbook.setSheetName(0, "Weely Checkin");
            createColumnHeaders(sheet);

            final JsonNode jsonNode = objectMapper.readValue(dataSource, JsonNode.class);
            final int column = FIRST_COLUMN;
            int row = SECOND_ROW;

            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {

                    final String formattedUsername = formatUsername(node.get("username").asText());
                    insertStringToCell(sheet, row, column, formattedUsername);

                    for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
                        final JsonNode dayNode = node.path(StringUtils.formatStringToCamelCase(dayOfWeek.toString()));
                        final Optional<JsonNode> checkedInNode = Optional.ofNullable(dayNode.findValue("runningCount"));

                        if (checkedInNode.isPresent()) {
                            sheet.getRow(row).createCell(dayOfWeek.getValue()).setCellValue("Yes");
                        }
                    }
                    row++;
                }

            }
            this.workbook = workbook;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToDirectory() {
        try {
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO:Create generic method to insert data into cell
    private void insertStringToCell(final Sheet sheet, final int row, final int column, final String text) {
        sheet.createRow(row).createCell(column).setCellValue(text);
    }

    private void createColumnHeaders(final Sheet sheet) {

        final Row spreadsheetRow = sheet.createRow(0);

        for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
            spreadsheetRow.createCell(dayOfWeek.getValue()).setCellValue(StringUtils.formatStringToCamelCase(dayOfWeek.toString()));
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
