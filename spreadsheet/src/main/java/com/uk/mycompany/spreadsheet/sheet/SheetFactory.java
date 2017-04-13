package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;

import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class SheetFactory {

    private SheetFactory() {
    }

    ;

    public static AIESheet getSheet(final SheetType shapeType, final Set<Devise> datasource) {

        switch (shapeType) {
            case CHECKIN:
//                return new WeeklyCheckinSheet(datasource);

            case PROFILE:
                return new ProfileSheet(datasource);

            default:
                throw new IllegalArgumentException("Not a valid sheet type");
        }
    }
}
