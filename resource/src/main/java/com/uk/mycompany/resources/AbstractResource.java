package com.uk.mycompany.resources;

import com.uk.mycompany.client.RESTClient;

import java.util.List;

/**
 * Created by mahutton on 09/03/2017.
 */

public class AbstractResource {

    private final String target;
    private final String endPoint;

    protected final RESTClient client = new RESTClient();

    public AbstractResource(final String endPoint, final String target) {
        this.endPoint = endPoint;
        this.target = target;
    };

    public String get() {
        return client.get(endPoint, target);
    }
}
