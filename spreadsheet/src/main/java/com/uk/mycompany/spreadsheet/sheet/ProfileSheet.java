package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.domain.ProfileDetails;
import com.uk.mycompany.shared.constants.SimbaConstants;
import com.uk.mycompany.shared.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class ProfileSheet extends AbstractAIESheet {

    private static final int ROW_NUMBER_FREEZE = 1;

    private static final int COLUMN_NUMBER_FREEZE = 1;

    private static final Logger logger = LogManager.getLogger(ProfileSheet.class);

    //TODO: Set contructor back to default once sheet is tested and working correctly
    public ProfileSheet(final Set<Devise> datasource) {
        super(datasource, SimbaConstants.PROFILES_SHEET_NAME);
        generateSheet();
    }

    @Override
    public void generateSheet() {

        final List<String> headers = Arrays.asList("Name", "Gender", "E-mail Address", "Here To", "Interests");
        final int columnHeaderOffset = 0;
        createColumnHeaders(headers, columnHeaderOffset);
        freezePane(COLUMN_NUMBER_FREEZE, ROW_NUMBER_FREEZE);

        int rowNumber = 1;

        for (final Devise devise : datasource) {
            createDeviseProfileEntries(rowNumber++, devise);
        }

        autoSizeColumns(headers);
        logger.info("Workbook created successfully!");
    }

    private void createDeviseProfileEntries(int rowNumber, final Devise devise) {

        final ProfileDetails currentDeviseProfile = devise.getProfileDetails();
        final String name = StringUtils.createFullname(currentDeviseProfile.getForename(), currentDeviseProfile.getSurname());
        int columnNumber = 0;

        createCellValue(rowNumber, columnNumber++, name);
        createCellValue(rowNumber, columnNumber++, currentDeviseProfile.getGender().toString());
        createCellValue(rowNumber, columnNumber++, currentDeviseProfile.getEmailAddress());
        createCellValue(rowNumber, columnNumber++, currentDeviseProfile.getReasonAttending());

        final String formattedInterestList = String.join(", ", devise.getSkills());

        createCellValue(rowNumber, columnNumber, formattedInterestList);
    }
}
