package com.uk.mycompany.spreadsheet.workbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by mahutton on 18/04/2017.
 */
public class WorkbookSingleton {

    private static Workbook workbook;

    private WorkbookSingleton() {
    }

    ;

    public static Workbook getWorkbook() {
        if (null == workbook) {
            workbook = new HSSFWorkbook();
        }

        return workbook;
    }
}
