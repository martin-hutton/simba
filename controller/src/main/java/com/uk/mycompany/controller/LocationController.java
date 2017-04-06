package com.uk.mycompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.resources.RecordResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;

/**
 * Created by mahutton on 24/03/2017.
 */
@RestController
public class LocationController {

    private static final Logger logger = LogManager.getLogger(LocationController.class);

    @RequestMapping(value = "/checkin/locations/today", method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "location", defaultValue = "") final String location) throws Exception {

        logger.error("Configuration File Defined To Be :: " + System.getProperty("log4j.configurationFile"));
        logger.error("Class Path Defined To Be :: " + System.getProperty("java.class.path"));

        RecordResource recordResource = new RecordResource();

        FileOutputStream out = new FileOutputStream("workbook.xls");
        Workbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();
        wb.setSheetName(0, "test");
        HSSFCell cell = (HSSFCell) s.createRow(6).createCell(1);
        cell.setCellValue("Test!");
        wb.write(out);
        out.close();

        final ObjectMapper objectMapper = new ObjectMapper();

//        /objectMapper

        //TODO: Logging doesn't write to log file but does get created

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