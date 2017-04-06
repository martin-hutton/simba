package com.uk.mycompany.resources;

import com.uk.mycompany.client.RESTClient;

import java.util.List;

/**
 * Created by mahutton on 09/03/2017.
 */

public class AbstractResource {

    private final String target;

    protected final RESTClient client = new RESTClient();

    public AbstractResource(final String target) {
        this.target = target;
    };

    public List<String> get() {
        return client.get(target);
    }
}
