package com.uk.mycompany.spreadsheet.sheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.domain.Devise;
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
import java.util.Map;
import java.util.Set;

/**
 * Created by mahutton on 08/04/2017.
 */
public class WeeklyCheckinSheet extends AbstractAIESheet {

    private static final int NO_UPPERCASE_CHAR_FOUND = -1;

    private static final int FIRST_COLUMN = 0;

    private static final int SECOND_ROW = 1;

    private static final Logger logger = LogManager.getLogger(WeeklyCheckinSheet.class);

    final ObjectMapper objectMapper = new ObjectMapper();

    private Workbook workbook = new HSSFWorkbook();

    //TODO: Need a better package name
    //TODO: Should this be inside a Factory pattern for the different types of spreadsheets that can be created
    // could then be with a public method to write to disk
    //TODO: Autosize columns, Days will be easy but for names will need to find the longest name and autosize based on that
    public WeeklyCheckinSheet(final Set<Devise> datasource) {
        super(datasource);
        generateSheet();
    }

    @Override
    void generateSheet() {

        try {
            FileOutputStream out = new FileOutputStream("AIE.xls");

            Sheet sheet = workbook.createSheet();
            workbook.setSheetName(0, "Weekly Checkin");
            createColumnHeaders(sheet);
            int rowNumber = 1;

            for (final Devise devise : datasource) {

                final StringBuilder name = new StringBuilder();
                name.append(devise.getProfileDetails().getForename());
                name.append(" ");
                name.append(devise.getProfileDetails().getSurname());

//                    sheet.createRow(rowNumber).createCell(0).setCellValue(name.toString());
                Row currentRow = sheet.createRow(rowNumber);
                currentRow.createCell(0).setCellValue(name.toString());

                for (final Map.Entry<String, String> checkinEntry : devise.getWeekCheckInStatus().entrySet()) {

                    if (checkinEntry.getValue() != null || checkinEntry.getValue() != "") {
                        currentRow.createCell(DayOfWeek.valueOf(checkinEntry.getKey()).getValue()).setCellValue(checkinEntry.getValue());
                    }
                }
                rowNumber++;
            }

            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            final Sheet sheet = workbook.createSheet();
//            workbook.setSheetName(0, "Weely Checkin");
//            createColumnHeaders(sheet);
//
//            final JsonNode jsonNode = objectMapper.readValue(dataSource, JsonNode.class);
//            final int column = FIRST_COLUMN;
//            int row = SECOND_ROW;
//
//            if (jsonNode.isArray()) {
//                for (JsonNode node : jsonNode) {
//
//                    final String formattedUsername = formatUsername(node.get("username").asText());
//                    insertStringToCell(sheet, row, column, formattedUsername);
//
//                    for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
//                        final JsonNode dayNode = node.path(StringUtils.formatStringToCamelCase(dayOfWeek.toString()));
//                        final Optional<JsonNode> checkedInNode = Optional.ofNullable(dayNode.findValue("runningCount"));
//
//                        if (checkedInNode.isPresent()) {
//                            sheet.getRow(row).createCell(dayOfWeek.getValue()).setCellValue("Yes");
//                        }
//                    }
//                    row++;
//                }
//
//            }
//            this.workbook = workbook;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void writeToDirectory() {
//        try {
////            workbook.write(fileOutputStream);
////            fileOutputStream.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }

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
