package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public abstract class AbstractAIESheet implements AIESheet {

    final int FIRST_ROW = 0;

    Workbook workbook = WorkbookSingleton.getWorkbook();

    Set<Devise> datasource;

    Sheet sheet;


    AbstractAIESheet(final Set<Devise> datasource, final String sheetName) {
        this.datasource = datasource;
        this.sheet = workbook.createSheet(sheetName);
    }

    public abstract void generateSheet();

    void freezePane(final int columnSplit, final int rowSplit) {
        sheet.createFreezePane(columnSplit, rowSplit);
    }

    void createCellValue(final int rowNumber, final int columnNumber, final String cellValue) {

        Row row = sheet.getRow(rowNumber);

        if (null == row) {
            row = sheet.createRow(rowNumber);
        }
        row.createCell(columnNumber).setCellValue(cellValue);
    }

    void createColumnHeaders(final List<String> headers, final int offSet) {

        final Row spreadsheetRow = sheet.createRow(FIRST_ROW);

        for (final String header : headers) {
            final int columnNumber = headers.indexOf(header);
            spreadsheetRow.createCell(columnNumber + offSet).setCellValue(header);
        }
    }

    void autoSizeColumns(final List<String> headers) {
        for (final String header : headers) {
            final int columnNumber = headers.indexOf(header);
            sheet.autoSizeColumn(columnNumber);
        }
    }
}
