package com.nikunjness.io;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.*;
import org.mockito.Matchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by nikunj on 26/8/15.
 */
public class ESMETest extends AbstractElasticsearchIntegrationTest {

    private static ESME esme;
    private static String host = "192.168.0.103";
    private static int port = 9200;
    private static String testIndex = "customer";
    private static String username = "admin";
    private static String password = "admin_pw";
    private SampleCallbackImpl eSearch;
    private List<String> records = new ArrayList<String>();


    @Before
    public void setup() throws Exception {
        fillRecords();
        Client client = this.getClient();
        for (int i = 0; i < records.size(); i++) {
            IndexResponse response = client.prepareIndex(testIndex, "restaurant", String.valueOf(i + 1))
                    .setSource(records.get(i))
                    .execute()
                    .actionGet();
        }
        eSearch = mock(SampleCallbackImpl.class);
        esme = new ESME("customer", "http://" + host + ":" + port, username, password, eSearch);
    }

    private void fillRecords() throws InterruptedException {
        records.add("{\"address\": {\"building\": \"1007\", \"coord\": [-73.856077, 40.848447], \"street\": \"Morris Park Ave\", \"zipcode\": \"10462\"}, \"borough\": \"Bronx\", \"cuisine\": \"Bakery\", \"grades\": [{\"date\": {\"$date\": 1393804800000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 2}, {\"date\": {\"$date\": 1378857600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 6}, {\"date\": {\"$date\": 1358985600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 10}, {\"date\": {\"$date\": 1322006400000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 9}, {\"date\": {\"$date\": 1299715200000}, \"grade\": \"B\", \"score\": 14}], \"name\": \"Morris Park Bake Shop\", \"restaurant_id\": \"30075445\"}");
        records.add("{\"address\": {\"building\": \"469\", \"coord\": [-73.961704, 40.662942], \"street\": \"Flatbush Avenue\", \"zipcode\": \"11225\"}, \"borough\": \"Brooklyn\", \"cuisine\": \"Hamburgers\", \"grades\": [{\"date\": {\"$date\": 1419897600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 8}, {\"date\": {\"$date\": 1404172800000}, \"grade\": \"B\", \"score\": 23}, {\"date\": {\"$date\": 1367280000000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}, {\"date\": {\"$date\": 1336435200000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}], \"name\": \"Wendy'S\", \"restaurant_id\": \"30112340\"}");
        records.add("{\"address\": {\"building\": \"351\", \"coord\": [-73.98513559999999, 40.7676919], \"street\": \"West   57 Street\", \"zipcode\": \"10019\"}, \"borough\": \"Manhattan\", \"cuisine\": \"Irish\", \"grades\": [{\"date\": {\"$date\": 1409961600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 2}, {\"date\": {\"$date\": 1374451200000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 11}, {\"date\": {\"$date\": 1343692800000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}, {\"date\": {\"$date\": 1325116800000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}], \"name\": \"Dj Reynolds Pub And Restaurant\", \"restaurant_id\": \"30191841\"}");
        records.add("{\"address\": {\"building\": \"2780\", \"coord\": [-73.98241999999999, 40.579505], \"street\": \"Stillwell Avenue\", \"zipcode\": \"11224\"}, \"borough\": \"Brooklyn\", \"cuisine\": \"American \", \"grades\": [{\"date\": {\"$date\": 1402358400000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 5}, {\"date\": {\"$date\": 1370390400000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 7}, {\"date\": {\"$date\": 1334275200000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}, {\"date\": {\"$date\": 1318377600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 12}], \"name\": \"Riviera Caterer\", \"restaurant_id\": \"40356018\"}");
        records.add("{\"address\": {\"building\": \"97-22\", \"coord\": [-73.8601152, 40.7311739], \"street\": \"63 Road\", \"zipcode\": \"11374\"}, \"borough\": \"Queens\", \"cuisine\": \"Jewish/Kosher\", \"grades\": [{\"date\": {\"$date\": 1416787200000}, \"grade\": \"Z\", \"score\": 20}, {\"date\": {\"$date\": 1358380800000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 13}, {\"date\": {\"$date\": 1343865600000}, \"grade\": \"AbstractElasticsearchIntegrationTest\", \"score\": 13}, {\"date\": {\"$date\": 1323907200000}, \"grade\": \"B\", \"score\": 25}], \"name\": \"Tov Kosher Kitchen\", \"restaurant_id\": \"40356068\"}");
        Thread.sleep(1000);
    }

    @Test
    public void testIndex() throws UnirestException, InterruptedException {
        esme.index("Bike", "1", "{\"name\":\"Dhoom\",\"price\":\"10 Lacs\"}");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }

    @Test
    public void testAutoIndex() throws UnirestException, InterruptedException {
        esme.autoIndex("car", "{\"name\":\"Bently\",\"price\":\"10 Lacs\"}");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }

    @Test
    public void testSearchDocument() throws UnirestException, InterruptedException {
        esme.searchDocument("restaurant", "2");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }


    @Test
    public void testDeleteDocument() throws UnirestException, InterruptedException {
        esme.deleteDocument("restaurant", "2");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }

    @Test
    public void testUpdateDocument() throws UnirestException, InterruptedException {
        esme.updateDocument("restaurant", "1", "{\"name\":\"Jassi Da Dhaba\"}");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }

    @Test
    public void testSearch() throws UnirestException, InterruptedException {
        esme.search("{\"query\":{\"term\":{\"name\":\"riviera\"}}}", "restaurant");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }

    @Test
    public void testSearchURI() throws UnirestException, InterruptedException {
        esme.searchUri("name:Riviera");
        Thread.sleep(1000);
        verify(eSearch).completed(Matchers.any(HttpResponse.class));
    }


}