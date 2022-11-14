package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.pages.PageObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;


public class SatCoordinateDataValidation extends PageObject {

    public final Logger logger = LoggerFactory.getLogger(SatDataValidation.class);
    public static final String BASE_URL = "https://api.wheretheiss.at/v1";
    public static final String URI = "/coordinates/";
    Properties prop = APIUtils.readPropertiesFile("config.properties");

    public SatCoordinateDataValidation() throws IOException {
    }

    public void request_GETRequestToRetrieveSatCoordinateData(String latLongData){
        Response response = RestAssured.given().when().baseUri(BASE_URL).get(URI + latLongData);
        response.prettyPrint();
    }

    public void response_GettingTheCoordinatesDataInJASONFormat(){
        Response response = RestAssured.given().when().baseUri(BASE_URL).get(URI + prop.getProperty("latandlong"));
        System.out.println("The response type is ====>>>>>>>>> " +response.getContentType());
        String responseType = response.getContentType();
        Assert.assertTrue(responseType.equalsIgnoreCase("application/json"));
    }

    public void response_DataIsProperlyGettingDisplayed(String latLongData) {
        ValidatableResponse response = RestAssured.given().
                when().baseUri(BASE_URL).get(URI + latLongData).then().log().all().
                assertThat().statusCode(200);

        LinkedHashMap<String, ?> jsonMap = response.extract().jsonPath().get();
            Assert.assertTrue("latitude is displayed", jsonMap.containsKey("latitude"));
            Assert.assertTrue("longitude is displayed", jsonMap.containsKey("longitude"));
            Assert.assertTrue("timezone is displayed", jsonMap.containsKey("timezone_id"));
            Assert.assertTrue("offset is displayed", jsonMap.containsKey("offset"));
            Assert.assertTrue("country code is displayed", jsonMap.containsKey("country_code"));
            Assert.assertTrue("map_url is displayed", jsonMap.containsKey("map_url"));

    }

    public void response_DataDisplayedAsProvidedLatLongDetails(String latitude, String longitude, String timeZoneId, String countryCode, String latLongData) {
        ValidatableResponse response = RestAssured.given().
                when().baseUri(BASE_URL).get(URI + latLongData).then().log().all().
                assertThat().statusCode(200);

        LinkedHashMap<String, ?> jsonMap = response.extract().jsonPath().get();
            Assert.assertEquals("latitude is displayed", jsonMap.get("latitude"), latitude);
            Assert.assertEquals("longitude is displayed", jsonMap.get("longitude"),longitude);
            Assert.assertEquals("timezone is displayed", jsonMap.get("timezone_id"),timeZoneId);
            Assert.assertEquals("offset is displayed", jsonMap.get("country_code"), countryCode);
    }

    public void response_theUserIsGettingTheResponseOnDetails(String responseCode, String latLongData){

    }
}