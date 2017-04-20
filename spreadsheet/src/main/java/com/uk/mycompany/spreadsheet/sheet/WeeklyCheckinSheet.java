package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.shared.constants.SimbaConstants;
import com.uk.mycompany.shared.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mahutton on 08/04/2017.
 */
public class WeeklyCheckinSheet extends AbstractAIESheet {

    private static final int ROW_NUMBER_FREEZE = 1;

    private static final int COLUMN_NUMBER_FREEZE = 1;

    private static final Logger logger = LogManager.getLogger(WeeklyCheckinSheet.class);

    //TODO: Need a better package name
    //TODO: Autosize columns, Days will be easy but for names will need to find the longest name and autosize based on that
    public WeeklyCheckinSheet(final Set<Devise> datasource) {
        super(datasource, SimbaConstants.CHECKIN_SHEET_NAME);
        generateSheet();
    }

    @Override
    void generateSheet() {

        int rowNumber = 1;

        final List<String> columnHeaders = Arrays.stream(DayOfWeek.values()).map(Enum::name).map(s -> StringUtils.formatStringToCamelCase(s)).collect(Collectors.toList());
        final int coulmnHeaderOffset = 1;
        createColumnHeaders(columnHeaders, coulmnHeaderOffset);
        freezePane(COLUMN_NUMBER_FREEZE, ROW_NUMBER_FREEZE);

        for (final Devise devise : datasource) {
            createFullnameColumn(rowNumber, devise);
            createCheckinEntry(rowNumber, devise);
            rowNumber++;
        }
        autoSizeColumns(columnHeaders);
    }

    private void createCheckinEntry(final int rowNumber, final Devise devise) {

        for (final Map.Entry<String, String> checkinEntry : devise.getWeekCheckInStatus().entrySet()) {
            if (checkinEntry.getValue() != null || checkinEntry.getValue() != "") {
                final int columnNumber = DayOfWeek.valueOf(checkinEntry.getKey()).getValue();
                createCellValue(rowNumber, columnNumber, checkinEntry.getValue());
            }
        }
    }

    private void createFullnameColumn(final int rowNumber, final Devise devise) {
        final String fullname = StringUtils.createFullname(devise.getProfileDetails().getForename(), devise.getProfileDetails().getSurname());
        int columnNumber = 0;

        createCellValue(rowNumber, columnNumber, fullname);
    }
}
