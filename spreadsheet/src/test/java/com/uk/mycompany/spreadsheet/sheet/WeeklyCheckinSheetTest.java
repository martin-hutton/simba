package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.services.handler.JsonHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mahutton on 20/04/2017.
 */
public class WeeklyCheckinSheetTest {

    WeeklyCheckinSheet weeklyCheckinSheet;

    Set<Devise> deviseSet;

    @Before
    public void setUp() throws IOException {

        final Path path = Paths.get("src/test/resources/checkin.json");
        final String json = Files.lines(path).collect(Collectors.joining());
        deviseSet = JsonHandler.transformDeviseFromJson(json);
    }

    @Test
    @Ignore
    public void spreadsheetGeneration() {
        weeklyCheckinSheet = new WeeklyCheckinSheet(deviseSet);
    }
}
