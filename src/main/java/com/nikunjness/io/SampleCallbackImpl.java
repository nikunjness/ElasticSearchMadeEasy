package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;


public class SampleCallbackImpl implements Callback<JsonNode> {


    @Override
    public void completed(HttpResponse<JsonNode> response) {
        System.out.println(response.getStatus());
        System.out.println(response.getStatusText());
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }





}

