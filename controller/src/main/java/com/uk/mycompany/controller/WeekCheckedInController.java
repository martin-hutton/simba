package com.uk.mycompany.controller;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.resources.AnalyticsWeekCurrentResource;
import com.uk.mycompany.services.handler.JsonHandler;
import com.uk.mycompany.spreadsheet.workbook.AIESpreadsheet;
import com.uk.mycompany.spreadsheet.factory.AIESpreadsheetFactory;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by mahutton on 06/04/2017.
 */
@RestController
public class WeekCheckedInController {

    private static final Logger logger = LogManager.getLogger(WeekCheckedInController.class);

    @RequestMapping(value = "/checkin/week", method = RequestMethod.GET)
    public String checkinWeekAnalytics(@RequestParam(value = "location", defaultValue = "") final String location) throws FileNotFoundException {

        AnalyticsWeekCurrentResource analyticsWeekCurrentResource = new AnalyticsWeekCurrentResource();

        final String response = analyticsWeekCurrentResource.get();

        final Set<Devise> deviseSet = JsonHandler.transformDeviseFromJson(response);

        //TODO: Fix!
        for (Iterator<Devise> iterator = deviseSet.iterator(); iterator.hasNext(); ) {
            Devise s = iterator.next();
            if (s.getProfileDetails().getUsername().contains("KwasiAmponsa")) {
                iterator.remove();
            }
        }

        final List<SheetType> sheetTypeList = Arrays.asList(SheetType.CHECKIN, SheetType.PROFILE);

        final AIESpreadsheet spreadsheet = AIESpreadsheetFactory.getSpreadsheet(sheetTypeList, deviseSet);

        spreadsheet.writeToDirectory();

        return "Spreadsheet successfully written to directory!";
    }
}
