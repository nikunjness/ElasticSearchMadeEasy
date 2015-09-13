package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class ESME {
    private ESearch eSearch;
    private String app;
    private String baseURL;
    private String username;
    private String password;

    public ESME(final String app, final String host, final String username, final String password, ESearch eSearch) throws UnirestException {
        this.app = app;
        this.baseURL = host + "/" + app;
        if (Unirest.head(baseURL).basicAuth(username, password).asJson().getStatus() != 200)
            throw new RuntimeException("Invalid app name");
        this.username = username;
        this.password = password;
        this.eSearch = eSearch;

    }

    public void index(String type, String index, String jsonDoc) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.put(baseURL + "/" + type + "/" + index).basicAuth(username, password).
                body(jsonDoc)
                .asJson();
        eSearch.onSuccess(response);

    }

    public void autoIndex(String type, String jsonDoc) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(baseURL + "/" + type + "/").basicAuth(username, password).
                body(jsonDoc)
                .asJson();
        eSearch.onSuccess(response);
    }

    public void searchDocument(String type, String index) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(baseURL + "/" + type + "/" + index).basicAuth(username, password).asJson();
        eSearch.onSuccess(response);

    }

    public void deleteDocument(String type, String index) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.delete(baseURL + "/" + type + "/" + index).basicAuth(username, password).asJson();
        eSearch.onSuccess(response);
    }

    public void updateDocument(String type, String index, String jsonDoc) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.put(baseURL + "/" + type + "/" + index).basicAuth(username, password).body(jsonDoc).asJson();
    }

    public void search(String json, String type) throws UnirestException{
        HttpResponse<JsonNode> response = Unirest.get(baseURL + "/" + type + "/_search").basicAuth(username, password).header("", json).asJson();
    }

    public void searchUri(String term) throws UnirestException{
        HttpResponse<JsonNode> response = Unirest.get(baseURL + "/" + "_search?q=" + term).basicAuth(username, password).asJson();
        eSearch.onSuccess(response);
    }


}

