package com.uk.mycompany.spreadsheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.uk.mycompany.domain.RecordDetails;

public class ProfilesSpreadSheet {
    private static final Logger logger = LogManager.getLogger(WeeklyCheckInSpreadsheet.class);

    public static String FILENAME = "interests.xls";

    private Workbook workbook;

    public ProfilesSpreadSheet() {
        workbook = null;
    }

    /**
     * Create workbook object from user records provided.
     *
     * @param records User records used for workbook creation.
     */
    public void createSpreadSheet(List<RecordDetails> records) {
        logger.info("Starting workbook creation, this may take a while...");
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        wb.setSheetName(0, "Profiles");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Gender");
        row.createCell(2).setCellValue("Email Address");
        row.createCell(3).setCellValue("Here To");
        row.createCell(4).setCellValue("Interests");
        sheet.createFreezePane(0, 1); // Freeze top row

        for (RecordDetails r : records) {
            StringBuilder name = new StringBuilder();
            name.append(r.getForename());
            name.append(" ");
            name.append(r.getSurname());

            row = sheet.createRow(row.getRowNum() + 1);
            row.createCell(0).setCellValue(name.toString());
            row.createCell(1).setCellValue(r.getGender());
            row.createCell(2).setCellValue(r.getEmailAddress());
            row.createCell(3).setCellValue(r.getReasonAttending());
            int i = 4;
            for (String s : r.getInterests()) {
                row.createCell(i++).setCellValue(s);
            }
        }

        // Auto-size the first three columns
        for (int i = 0; i <= 3; i++)
            sheet.autoSizeColumn(i);

        this.workbook = wb;
        logger.info("Workbook created successfully!");
    }

    /**
     * Write workbook out to file.
     *
     * @return Whether file was written successfully or not.
     */
    public boolean writeToFile() {
        if (workbook != null) {
            logger.info("Writing workbook '" + FILENAME + "' to file...");
            try {
                FileOutputStream out = new FileOutputStream(FILENAME);
                workbook.write(out);
                out.close();
                return true;
            } catch (IOException e) {
                logger.error(e);
                return false;
            }
        } else {
            logger.error("Cannot write workbook to file. Workbook must be created first.");
            return false;
        }
    }

}
