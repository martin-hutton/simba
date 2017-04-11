package com.uk.mycompany.controller;

import com.uk.mycompany.resources.RecordResource;
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

        logger.error("Configuration File Defined To Be :: " + System.getProperty("log4j.configurationFile"));
        logger.error("Class Path Defined To Be :: " + System.getProperty("java.class.path"));

        RecordResource recordResource = new RecordResource();

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