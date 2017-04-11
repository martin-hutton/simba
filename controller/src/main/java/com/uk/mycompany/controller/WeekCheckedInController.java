package com.uk.mycompany.controller;

import com.uk.mycompany.resources.AnalyticsWeekCurrentResource;
import com.uk.mycompany.spreadsheet.WeeklyCheckInSpreadsheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mahutton on 06/04/2017.
 */
@RestController
public class WeekCheckedInController {

    private static final Logger logger = LogManager.getLogger(WeekCheckedInController.class);

    @RequestMapping(value = "/checkin/week", method = RequestMethod.GET)
    public String checkinWeekAnalytics(@RequestParam(value = "location", defaultValue = "") final String location) {

        AnalyticsWeekCurrentResource analyticsWeekCurrentResource = new AnalyticsWeekCurrentResource();

        final String response = analyticsWeekCurrentResource.get();

        WeeklyCheckInSpreadsheet weeklyCheckInSpreadsheet = new WeeklyCheckInSpreadsheet(response);

        weeklyCheckInSpreadsheet.writeToDirectory();

        return "test!";
    }
}
