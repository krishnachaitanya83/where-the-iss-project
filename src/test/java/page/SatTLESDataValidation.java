package page;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.pages.PageObject;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


public class SatTLESDataValidation extends PageObject {

    public final Logger logger = LoggerFactory.getLogger(SatDataValidation.class);



    public static final String BASE_URL = "https://api.wheretheiss.at/v1";
    public static final String URI = "/satellites/";
    Properties prop = APIUtils.readPropertiesFile("config.properties");

    public SatTLESDataValidation() throws IOException {
    }


    public void request_ToRetrieveSatelliteTLESData(String id){
        RestAssured.given().when().baseUri(BASE_URL).get(URI+id+"/tles").then().log().all().
                assertThat().
                statusCode(200);
    }

    public void response_GettingTheTLESDataInJASONFormat(String id){
        Response response1 = RestAssured.given().when().baseUri(BASE_URL).get(URI+id+"/tles");
        System.out.println("The response type is ====>>>>>>>>> " +response1.getContentType());
        String responseType = response1.getContentType();
        Assert.assertTrue(responseType.equalsIgnoreCase("application/json"));
    }

    public void response_GettingTheTLESDataInTEXTFormat(String id){
        Response response1 = RestAssured.given().queryParam("format",prop.getProperty("format_text")).
                when().baseUri(BASE_URL).get(URI+id+"/tles");
        String responseType = response1.getContentType();
        Assert.assertTrue(responseType.equalsIgnoreCase("text/plain"));

    }


    public void response_TLESDataIsProperlyGettingDisplayedJSON(String id){
        ValidatableResponse response = RestAssured.given().queryParam("format",prop.getProperty("format_json")).
                when().baseUri(BASE_URL).get(URI+id+"/tles").then().log().all().
                assertThat().statusCode(200);

        LinkedHashMap<String, ?> jsonMap = response.extract().jsonPath().get();
            Assert.assertTrue("requested timestamp is displayed", jsonMap.containsKey("requested_timestamp"));
            Assert.assertTrue("tle timestamp is displayed", jsonMap.containsKey("tle_timestamp"));
            Assert.assertTrue("id is displayed", jsonMap.containsKey("id"));
            Assert.assertTrue("name is displayed", jsonMap.containsKey("name"));
            Assert.assertTrue("line1 is displayed", jsonMap.containsKey("line1"));
            Assert.assertTrue("line2 is displayed", jsonMap.containsKey("line2"));
    }
}