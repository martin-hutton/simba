package com.uk.mycompany.resources;

import com.uk.mycompany.shared.constants.SimbaConstants;

/**
 * Created by mahutton on 29/03/2017.
 */
public class LocationsTodayResource extends AbstractResource {
    public LocationsTodayResource() {
        super(SimbaConstants.RECORD_RESOURCE + SimbaConstants.LOCATIONS_RESOURCE + SimbaConstants.TODAY_RESOURCE);
    }
}
