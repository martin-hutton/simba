package com.uk.mycompany.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uk.mycompany.client.RESTClient;
import com.uk.mycompany.domain.RecordDetails;
import com.uk.mycompany.resources.AnalyticsWeekCurrentResource;
import com.uk.mycompany.shared.constants.SimbaConstants;
import com.uk.mycompany.spreadsheet.ProfilesSpreadSheet;

@RestController
public class ProfilesController {
    private static final Logger logger = LogManager.getLogger(WeekCheckedInController.class);

    @RequestMapping(value = "/checkin/profiles", method = RequestMethod.GET)
    public String checkinWeekAnalytics(@RequestParam(value = "location", defaultValue = "") final String location) {

        // Get dataset with users data
        AnalyticsWeekCurrentResource analyticsWeekCurrentResource = new AnalyticsWeekCurrentResource();
        final String usersResponse = analyticsWeekCurrentResource.get();
        final List<RecordDetails> records = new ArrayList<>();

        // Create list of user records
        for (String username : this.getUsersFromData(usersResponse)) {
            RecordDetails record = new RecordDetails();
            record.setUsername(username);

            final String recordResponse = RESTClient.get(SimbaConstants.USER_SERVICE_ENDPOINT, SimbaConstants.USER_DATA_RESOURCE + username);
            this.fillDetailsFromData(record, recordResponse);

            records.add(record);
        }

        ProfilesSpreadSheet iss = new ProfilesSpreadSheet();
        iss.createSpreadSheet(records);
        iss.writeToFile();

        StringBuilder output = new StringBuilder();
        output.append("<html>\n");
        output.append("Spreadsheet generated successfully.<br>\n");
        output.append("<b>Users Processed:</b> ");
        output.append(records.size());
        output.append("<br>\n");

        return output.toString(); //"Interests spreadsheet generated!";
    }

    /**
     * Get set of users from JSON data provided by endpoint.
     *
     * @param data Data produced by RESTClient.
     * @return Set of users existing in the current week.
     */
    private Set<String> getUsersFromData(String data) {
        Set<String> users = new HashSet<>();

        try {
            JsonNode jsonNode = new ObjectMapper().readValue(data, JsonNode.class);

            if (jsonNode.isArray())
                for (final JsonNode node : jsonNode) {
                    final String username = node.get("username").asText();
                    users.add(username);
                }
        } catch (IOException e) {
            logger.error(e);
        }

        return users;
    }

    /**
     * Fill details known about user from JSON data provided by user service endpoint.
     *
     * @param record User record to be updated.
     * @param data Data produced by RESTClient.
     * @return User record.
     */
    private RecordDetails fillDetailsFromData(RecordDetails record, String data) {
        try {
            JsonNode node = new ObjectMapper().readValue(data, JsonNode.class); // Set node as root
            node = node.get("details"); // Change node to be at 'details'

            record.setForename(node.get("forename").asText());
            record.setSurname(node.get("surname").asText());
            record.setGender(node.get("gender").asText());
            record.setEmailAddress(node.get("emailAddress").asText());
            record.setReasonAttending(node.get("bio").get("hereTo").asText());

            List<String> interests = new ArrayList<>();
            JsonNode interestsNode = node.get("bio").get("askMeAbout");
            if (interestsNode.isArray())
                for (final JsonNode n : interestsNode) {
                    interests.add(n.asText().trim());
                }
            record.setInterests(interests);
        } catch (IOException e) {
            logger.error(e);
        }

        return record;
    }
}
