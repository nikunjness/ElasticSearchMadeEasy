package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;


public class ESME {

    private final String SEPARATOR = "/";

    private Callback<JsonNode> eSearch;
    private String app;
    private String baseURL;
    private String username;
    private String password;

    public ESME(final String app, final String host, final String username, final String password, Callback<JsonNode> eSearch) throws UnirestException {
        this.app = app;
        this.baseURL = host + SEPARATOR + app;
        if (Unirest.head(baseURL).basicAuth(username, password).asJson().getStatus() != 200)
            throw new RuntimeException("Invalid app name");
        this.username = username;
        this.password = password;
        this.eSearch = eSearch;
    }

    public void index(String type, String index, String jsonDoc) throws UnirestException {
        Unirest.put(baseURL + SEPARATOR + type + SEPARATOR + index).basicAuth(username, password).
                body(jsonDoc).asJsonAsync(eSearch);

    }

    public void autoIndex(String type, String jsonDoc) throws UnirestException {
        //HttpResponse<JsonNode> response =
                Unirest.post(baseURL + SEPARATOR + type + SEPARATOR).basicAuth(username, password).
                body(jsonDoc)
                .asJsonAsync(eSearch);
    }

    public void searchDocument(String type, String index) throws UnirestException {
        //HttpResponse<JsonNode> response =
        Unirest.get(baseURL + SEPARATOR + type + SEPARATOR + index).basicAuth(username, password).asJsonAsync(eSearch);

    }

    public void deleteDocument(String type, String index) throws UnirestException {
        //HttpResponse<JsonNode> response =
                Unirest.delete(baseURL + SEPARATOR + type + SEPARATOR + index).basicAuth(username, password).asJsonAsync(eSearch);
    }

    public void updateDocument(String type, String index, String jsonDoc) throws UnirestException {
        //HttpResponse<JsonNode> response =
                Unirest.put(baseURL + SEPARATOR + type + SEPARATOR + index).basicAuth(username, password).body(jsonDoc).asJsonAsync(eSearch);
    }

    public void search(String json, String type) throws UnirestException {
        //HttpResponse<JsonNode> response =
                Unirest.get(baseURL + SEPARATOR + type + "/_search").basicAuth(username, password).header("", json).asJsonAsync(eSearch);
    }

    public void searchUri(String term) throws UnirestException {
        //HttpResponse<JsonNode> response =
        Unirest.get(baseURL + SEPARATOR + "_search?q=" + term).basicAuth(username, password).asJsonAsync(eSearch);
    }


}

