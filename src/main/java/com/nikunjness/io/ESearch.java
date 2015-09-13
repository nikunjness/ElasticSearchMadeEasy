package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;

public interface ESearch {

    public void onSuccess(HttpResponse<JsonNode> response);
}
