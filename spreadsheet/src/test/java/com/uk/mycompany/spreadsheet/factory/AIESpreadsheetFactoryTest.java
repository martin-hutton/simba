package com.uk.mycompany.spreadsheet.factory;

import com.uk.mycompany.domain.Devise;
import com.uk.mycompany.services.handler.JsonHandler;
import com.uk.mycompany.spreadsheet.sheet.enumeration.SheetType;
import com.uk.mycompany.spreadsheet.workbook.AIESpreadsheet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mahutton on 20/04/2017.
 */
public class AIESpreadsheetFactoryTest {

    Set<Devise> deviseSet;

    @Before
    public void setUp() throws IOException {
        final Path path = Paths.get("src/test/resources/checkin.json");
        final String json = Files.lines(path).collect(Collectors.joining());
//        deviseSet = JsonHandler.transformDeviseFromJson(json);
    }

    @Test
    @Ignore
    public void testCorrectSheetsReturned() {
        List<SheetType> sheetTypeList = Arrays.asList(SheetType.CHECKIN, SheetType.PROFILE);
        AIESpreadsheet spreadsheet = AIESpreadsheetFactory.getSpreadsheet(sheetTypeList, deviseSet);

    }
}
