package com.uk.mycompany.spreadsheet.factory;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.spreadsheet.sheet.ProfileSheet;
import com.uk.mycompany.spreadsheet.sheet.WeeklyCheckinSheet;
import com.uk.mycompany.spreadsheet.workbook.AIESpreadsheet;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;

import java.util.List;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class AIESpreadsheetFactory {

    private AIESpreadsheetFactory() {
    }

    ;

    public static AIESpreadsheet getSpreadsheet(final List<SheetType> sheetTypesheetTypeList, final Set<Devise> datasource) {

        final AIESpreadsheet aieSpreadsheet = AIESpreadsheet.getInstance();

        for (final SheetType sheetType : sheetTypesheetTypeList) {

            switch (sheetType) {
                case CHECKIN:
                    new WeeklyCheckinSheet(datasource);
                    break;
                case PROFILE:
                    new ProfileSheet(datasource);
                    break;
                default:
                    throw new IllegalArgumentException("Not a valid sheet type");
            }
        }
        return aieSpreadsheet;
    }
}
