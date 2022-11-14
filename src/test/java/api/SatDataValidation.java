package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;


public class SatDataValidation extends PageObject {


    public final Logger logger = LoggerFactory.getLogger(SatDataValidation.class);
    public static final String BASE_URL = "https://api.wheretheiss.at/v1";
    public static final String URI = "/satellites";


    //This method is to get the satellite data from the API.
    public void list_Of_Satellites() {
        String data = APIUtils.getResponseData("/satellites");
        logger.info(data);
    }


    //This method is to get the satellite data from the API and it will return the Satellite ID
    // to be consumed by other methods as parameter
    public String response_The_ListOfAvailableSatellites() {
        RestAssured.given().when().baseUri(BASE_URL).get(URI).then().log().all().
                assertThat().
                statusCode(200);
        Response response = APIUtils.getResponse("/satellites");
        ArrayList<Integer> id = response.getBody().jsonPath().get("id");
        String satId = id.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return satId;
    }

    //This method validates the response code that returns the satellites details
    public void response_responseCodeShouldBe(String responseCode) {
        APIUtils.assertGetRequest("/satellites", responseCode);
    }
}
