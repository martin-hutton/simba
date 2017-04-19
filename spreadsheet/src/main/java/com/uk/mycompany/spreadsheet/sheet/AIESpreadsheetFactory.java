package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.spreadsheet.AIESpreadsheet;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;

import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class AIESpreadsheetFactory {

    private AIESpreadsheetFactory() {
    }

    ;

    public static AIESpreadsheet getSpreadsheet(final Set<SheetType> sheetTypeSet, final Set<Devise> datasource) {

        final AIESpreadsheet aieSpreadsheet = AIESpreadsheet.getInstance();

        for (final SheetType sheetType : sheetTypeSet) {

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
