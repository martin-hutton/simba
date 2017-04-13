package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.domain.ProfileDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class ProfileSheet extends AbstractAIESheet {

    private static final Logger logger = LogManager.getLogger(ProfileSheet.class);

    private Workbook workbook = new HSSFWorkbook();

    //TODO: Set contructor back to default once sheet is tested and working correctly
    public ProfileSheet(final Set<Devise> datasource) {
        super(datasource);
        generateSheet();
    }

    @Override
    void generateSheet() {

        logger.info("Starting workbook creation, this may take a while...");

        try {
            FileOutputStream out = new FileOutputStream("AIE1.xls");

            Sheet sheet = workbook.createSheet();
            workbook.setSheetName(0, "Profiles");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Name");
            row.createCell(1).setCellValue("Gender");
            row.createCell(2).setCellValue("Email Address");
            row.createCell(3).setCellValue("Here To");
            row.createCell(4).setCellValue("Interests");
            sheet.createFreezePane(0, 1); // Freeze top row

            for (Devise devise : datasource) {

                final ProfileDetails currentDeviseProfile = devise.getProfileDetails();

                final StringBuilder name = new StringBuilder();
                name.append(currentDeviseProfile.getForename());
                name.append(" ");
                name.append(currentDeviseProfile.getSurname());

                row = sheet.createRow(row.getRowNum() + 1);
                row.createCell(0).setCellValue(name.toString());
                row.createCell(1).setCellValue(currentDeviseProfile.getGender().toString());
                row.createCell(2).setCellValue(currentDeviseProfile.getEmailAddress());
                row.createCell(3).setCellValue(currentDeviseProfile.getReasonAttending());
                int i = 4;
                for (String interest : devise.getInterests()) {
                    row.createCell(i++).setCellValue(interest);
                }
            }

            // Auto-size the first three columns
            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            out.close();

            logger.info("Workbook created successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
