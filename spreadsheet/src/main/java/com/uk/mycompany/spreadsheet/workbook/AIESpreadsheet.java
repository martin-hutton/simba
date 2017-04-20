package com.uk.mycompany.spreadsheet.workbook;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mahutton on 12/04/2017.
 */
public class AIESpreadsheet {

    private static AIESpreadsheet aieSpreadsheet;

    private Workbook workbook = WorkbookSingleton.getWorkbook();

    private FileOutputStream fileOutputStream = new FileOutputStream("AIE.xls");

    private AIESpreadsheet() throws FileNotFoundException {

    }

    public static AIESpreadsheet getInstance() {
        if (null == aieSpreadsheet) {
            try {
                aieSpreadsheet = new AIESpreadsheet();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return aieSpreadsheet;
    }

    public void writeToDirectory() {
        try {
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
