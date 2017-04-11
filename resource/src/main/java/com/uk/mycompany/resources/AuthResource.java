package com.uk.mycompany.resources;

import com.uk.mycompany.shared.constants.SimbaConstants;

/**
 * Created by mahutton on 09/03/2017.
 */

public class AuthResource extends AbstractResource {

    public AuthResource() {
        super(SimbaConstants.CHECK_IN_SERVICE_ENDPOINT, SimbaConstants.AUTH_RESOURCE);
    }
}
