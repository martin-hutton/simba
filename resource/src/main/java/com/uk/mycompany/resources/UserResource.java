package com.uk.mycompany.resources;

import com.uk.mycompany.shared.constants.SimbaConstants;

/**
 * Created by mahutton on 12/04/2017.
 */
public class UserResource extends AbstractResource {
    public UserResource(String userValue) {
        super(SimbaConstants.USER_SERVICE_ENDPOINT + "/" + SimbaConstants.USER_RESOURCE + userValue + "/" + SimbaConstants.DETAILS_PATH);
    }
}
