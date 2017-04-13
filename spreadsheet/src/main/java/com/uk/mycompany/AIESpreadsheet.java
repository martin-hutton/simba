package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class AIESpreadsheet{

    private Set<AIESheet> sheets;

    private Workbook workbook = new HSSFWorkbook();

    private FileOutputStream fileOutputStream = new FileOutputStream("AIE.xls");

    public AIESpreadsheet() throws FileNotFoundException {
    }
}
