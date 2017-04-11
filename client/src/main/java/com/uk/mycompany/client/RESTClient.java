package com.uk.mycompany.client;

import com.uk.mycompany.shared.constants.SimbaConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mahutton on 07/03/2017.
 */

public class RESTClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String applicationJson = SimbaConstants.JSON_CONTENT_TYPE;

    //TODO: Needs real implementation
    private final String token = String.format("token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE0ODg0NTA5MjB9.YjCKsXoJ5tDU6s7SzIb-htiD6K1jsj7ePMwM5aCw2Yo");

    public String get(final String endPoint, final String target) {

        //TODO: Look into genetrics to see if can use for http methods

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        HttpEntity<String> response = restTemplate.exchange(endPoint + target, HttpMethod.GET, httpEntity,String.class);

        return response.getBody();

//        return client.post(site + target)
//                .header(HttpHeaders.CONTENT_TYPE, applicationJson)
//                .header(HttpHeaders.AUTHORIZATION, token)
//                .useCaches(false)
//                .body(requestBody)
//                .asJsonObject();
    }
}