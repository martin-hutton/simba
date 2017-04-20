package com.uk.mycompany.services.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.domain.ProfileDetails;
import com.uk.mycompany.domain.enumeration.Gender;
import com.uk.mycompany.resources.UserResource;
import com.uk.mycompany.shared.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;

/**
 * Created by mahutton on 12/04/2017.
 */
public final class JsonHandler {

    final static ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(JsonHandler.class);

    public static Set<Devise> transformDeviseFromJson(final String json) {

        final Set<Devise> deviseSet = getUsersFromData(json);

        for (final Devise devise : deviseSet) {
            final UserResource userResource = new UserResource(devise.getProfileDetails().getUsername());
            final String response = userResource.get();

            //TODO: Put in correct status code handling in filter
            if (response != "404") {
                getDeviseDetails(response, devise);
            }
        }
        return deviseSet;
    }

    private static void getDeviseDetails(final String data, final Devise devise) {
        try {
            JsonNode jsonNode = objectMapper.readValue(data, JsonNode.class);

            //TODO: Should be replaced with Builder pattern
            devise.getProfileDetails().setForename(jsonNode.get("forename").asText());
            devise.getProfileDetails().setSurname(jsonNode.get("surname").asText());
            devise.getProfileDetails().setGender(Gender.valueOf(jsonNode.get("gender").asText().toUpperCase()));
            devise.getProfileDetails().setEmailAddress(jsonNode.get("emailAddress").asText());
            devise.getProfileDetails().setReasonAttending(jsonNode.get("bio").get("hereTo").asText());

            Set<String> skillsSet = new HashSet<>();
            JsonNode skillsNode = jsonNode.get("bio").get("askMeAbout");

            //TODO:Update method to just add to set in single line rather than loop through
            if (skillsNode.isArray())
                for (final JsonNode node : skillsNode) {
                    skillsSet.add(node.asText().trim());
                }

            devise.setSkills(skillsSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Devise> getUsersFromData(final String data) {
        final Set<Devise> deviseSet = new HashSet<>();
        final Set<ProfileDetails> profileDetailsSet = new HashSet<>();

        try {
            final JsonNode jsonNode = objectMapper.readValue(data, JsonNode.class);

            if (jsonNode.isArray())
                for (final JsonNode node : jsonNode) {
                    final ProfileDetails profileDetails = new ProfileDetails();
                    profileDetails.setUsername(node.get("username").asText());
                    profileDetailsSet.add(profileDetails);
                    final Map<String, String> locationCheckinMap = new HashMap<>();

                    for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
                        final JsonNode dayNode = node.path(StringUtils.formatStringToCamelCase(dayOfWeek.toString()));
                        final Iterator<Map.Entry<String, JsonNode>> nodeIterator = dayNode.fields();

                        while (nodeIterator.hasNext()) {
                            final Map.Entry<String, JsonNode> currentIter = nodeIterator.next();
                            final Optional<JsonNode> checkedInNode = Optional.ofNullable(dayNode.findValue("runningCount"));

                            if (checkedInNode.isPresent()) {
                                locationCheckinMap.put(dayOfWeek.toString(), currentIter.getKey());
                                break;
                            }
                        }
                    }
                    final Devise devise = new Devise();
                    devise.setProfileDetails(profileDetails);
                    devise.setWeekCheckInStatus(locationCheckinMap);
                    deviseSet.add(devise);
                }

        } catch (IOException e) {
            logger.error(e);
        }
        return deviseSet;
    }
}