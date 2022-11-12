package page;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.pages.PageObject;
import org.hamcrest.Matchers;
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
    Properties prop = APIUtils.readPropertiesFile("config.properties");

    public SatDataValidation() throws IOException {
    }

    public void list_Of_Satellites() {
        String data = APIUtils.getResponseData("/satellites");
        logger.info(data);
    }

    public String response_The_ListOfAvailableSatellites() {
        RestAssured.given().when().baseUri(BASE_URL).get(URI).then().log().all().
                assertThat().
                statusCode(200);
//                body("id", Matchers.equalTo(prop.getProperty("id"))).
//                body("name", Matchers.equalTo(prop.getProperty("name")));

        Response response = APIUtils.getResponse("/satellites");
        ArrayList<Integer> id = response.getBody().jsonPath().get("id");
        String satId = id.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return satId;
    }

    public void response_responseCodeShouldBe(String responseCode) {
        APIUtils.assertGetRequest("/satellites", responseCode);
    }
}
