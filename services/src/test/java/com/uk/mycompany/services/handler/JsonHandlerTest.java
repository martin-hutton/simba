package com.uk.mycompany.services.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.resources.UserResource;
import com.uk.mycompany.shared.utils.StringUtils;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mahutton on 12/04/2017.
 */
public final class JsonHandlerTest {

    private static String CHECKIN_STRING;
    private static String PROFILE_DETAILS_STRING;
    private static JsonNode CHECKIN_JSON_NODE;
    private static JsonNode PROFILE_DETAILS_JSON_NODE;
    final static ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws IOException {
        CHECKIN_STRING = Files.lines(Paths.get("src/test/resources/checkin.json")).collect(Collectors.joining());
        PROFILE_DETAILS_STRING = Files.lines(Paths.get("src/test/resources/profile_details.json")).collect(Collectors.joining());
        CHECKIN_JSON_NODE = objectMapper.readValue(CHECKIN_STRING, JsonNode.class);
        PROFILE_DETAILS_JSON_NODE = objectMapper.readValue(PROFILE_DETAILS_STRING, JsonNode.class);

    }

    @Test
    public void checkProfileDetails(@Mocked final UserResource userResourceMock) throws ParseException {

        new Expectations() {{
            userResourceMock.get();
            result = PROFILE_DETAILS_STRING;
        }};
        final Set<Devise> deviseSet = JsonHandler.transformDeviseFromJson(CHECKIN_STRING);

        for (final Devise devise : deviseSet) {
            Assert.assertEquals(PROFILE_DETAILS_JSON_NODE.get("forename").asText(), devise.getProfileDetails().getForename());
            Assert.assertEquals(PROFILE_DETAILS_JSON_NODE.get("surname").asText(), devise.getProfileDetails().getSurname());
            Assert.assertEquals(PROFILE_DETAILS_JSON_NODE.get("gender").asText(), devise.getProfileDetails().getGender().name().toLowerCase());
            Assert.assertEquals(PROFILE_DETAILS_JSON_NODE.get("emailAddress").asText(), devise.getProfileDetails().getEmailAddress());
            Assert.assertEquals(PROFILE_DETAILS_JSON_NODE.get("bio").get("hereTo").asText(), devise.getProfileDetails().getReasonAttending());
        }
    }

    @Test
    public void checkSkills(@Mocked final UserResource userResourceMock) throws ParseException {

        new Expectations() {{
            userResourceMock.get();
            result = PROFILE_DETAILS_STRING;
        }};

        Set<String> skillsSet = new HashSet<>();
        JsonNode skillsNode = PROFILE_DETAILS_JSON_NODE.get("bio").get("askMeAbout");

        if (skillsNode.isArray())
            for (final JsonNode node : skillsNode) {
                skillsSet.add(node.asText().trim());
            }

        final Set<Devise> deviseSet = JsonHandler.transformDeviseFromJson(CHECKIN_STRING);

        for (final Devise devise : deviseSet) {
            Assert.assertEquals(skillsSet, devise.getSkills());
        }
    }

    @Test
    public void checkCheckinDetails() {
        final Set<Devise> deviseSet = JsonHandler.transformDeviseFromJson(CHECKIN_STRING);
        final Map<String, String> expectedCheckinMap = getCheckInMap();

        for (final Devise devise : deviseSet) {
            Assert.assertEquals(expectedCheckinMap, devise.getWeekCheckInStatus());
        }
    }

    private final Map<String, String> getCheckInMap() {

        final Map<String, String> locationCheckinMap = new HashMap<>();

        if (CHECKIN_JSON_NODE.isArray())
            for (final JsonNode node : CHECKIN_JSON_NODE) {
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
            }

        return locationCheckinMap;
    }

}