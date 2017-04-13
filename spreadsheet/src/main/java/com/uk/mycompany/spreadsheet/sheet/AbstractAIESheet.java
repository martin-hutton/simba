package com.uk.mycompany.spreadsheet.sheet;

import com.uk.mycompany.domain.Devise;

import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public abstract class AbstractAIESheet implements AIESheet {

    Set<Devise> datasource;

    AbstractAIESheet(final Set<Devise> datasource) {
        this.datasource = datasource;
    }

    abstract void generateSheet();
}
