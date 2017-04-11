package com.uk.mycompany.resources;


import com.uk.mycompany.shared.constants.SimbaConstants;

/**
 * Created by mahutton on 07/03/2017.
 */

public class RecordResource extends AbstractResource {

    public RecordResource() {
        super(SimbaConstants.CHECK_IN_SERVICE_ENDPOINT, SimbaConstants.RECORD_RESOURCE);
    }
}