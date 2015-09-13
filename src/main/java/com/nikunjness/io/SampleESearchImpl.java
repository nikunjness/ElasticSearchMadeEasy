package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


public class SampleESearchImpl implements ESearch {

    @Override
    public void onSuccess(HttpResponse<JsonNode> response) {
        //Do your stuff here
    }
}

