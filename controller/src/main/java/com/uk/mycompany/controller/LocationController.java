package com.uk.mycompany.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mahutton on 24/03/2017.
 */
@RestController
public class LocationController {

    private static final Logger logger = LogManager.getLogger(LocationController.class);


    @RequestMapping(value = "/checkin/locations/today", method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "location", defaultValue = "") final String location) throws Exception {

//        logger.error("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
//        logger.error("Class Path Defined To Be :: "+System.getProperty("java.class.path"));
//
//        RecordResource recordResource = new RecordResource();
//
//        FileOutputStream out = new FileOutputStream("workbook.xls");
//        Workbook wb = new HSSFWorkbook();
//        Sheet s = wb.createSheet();
//        wb.setSheetName(0, "\u0422\u0435\u0441\u0442\u043E\u0432\u0430\u044F " +
//                "\u0421\u0442\u0440\u0430\u043D\u0438\u0447\u043A\u0430" );
//        s.createRow(12);
//        wb.write(out);
//        out.close();
//
//        logger.trace("Logging works!");
//        logger.error("Logging works!");


        //TODO: Tidy up depenancies in POMs
//
//		//TODO: Add dependancy injection framework
//
//		//TODO: Add testing and mocking stuff
//
//        //TODO: Add logging
//
//        //TODO: See if can use generics for RESTClient/Resources
//
//        //TODO: Use ObjectMapper to return a custom JSON response
//        //ObjectMapper objectMapper
//
//        return recordResource.get();

        return "Simba!";
    }
}