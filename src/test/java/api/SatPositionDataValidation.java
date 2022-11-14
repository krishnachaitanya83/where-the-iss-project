package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.pages.PageObject;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;



public class SatPositionDataValidation extends PageObject {

    public final Logger logger = LoggerFactory.getLogger(SatDataValidation.class);
    public static final String BASE_URL = "https://api.wheretheiss.at/v1";
    public static final String URI = "/satellites/";
    Properties prop = APIUtils.readPropertiesFile("config.properties");

    public SatPositionDataValidation() throws IOException {
    }


    //This method validates the satellite details are received.
    public void request_RetrieveSatellitePositionInfo(String id){
        RestAssured.given().when().baseUri(BASE_URL).get(URI+id).then().log().all().
                assertThat().
                statusCode(200).
                body("id", Matchers.equalTo(Integer.parseInt(prop.getProperty("id")))).
                body("name", Matchers.equalTo(prop.getProperty("name")));
    }


    //This method validates the satellite details are received and the response code is 200
    public void response_ValidResponseForTheSatellitePositionInfo(String id){
        RestAssured.given().when().baseUri(BASE_URL).get(URI+id).then().assertThat().statusCode(200);
    }


    //This method validates the satellite information for the current timestamp
    public void response_Info_SatellitePositionVelocityEtc(String id){
        RestAssured.given().when().baseUri(BASE_URL).get(URI+id).then().assertThat().
                statusCode(200).
                body("velocity", Matchers.anything()).
                body("timestamp", Matchers.anything()).
                body("units", Matchers.containsString(prop.getProperty("units")));
    }


    //This method validates the satellite information for the user provided timestamp
    public void response_DetailsBasedOnTheTimeStamp_StepDef(String timeStamp) {

        String currentTimeStamp = Long.toString(APIUtils.getCurrentTimestamp());
        RestAssured.given().queryParam("timestamp", timeStamp).when().baseUri(BASE_URL).
                get(URI + prop.getProperty("id")).then().assertThat().
                statusCode(200);

        Response response = RestAssured.given().header("timestamp", timeStamp).
                when().baseUri(BASE_URL).get(URI + prop.getProperty("id"));
        int timestamp = APIUtils.getJsonIntegerData(response, "timestamp") / 100;
        Assert.assertEquals("The time stamp is", timestamp, Integer.parseInt(currentTimeStamp));
    }

    //This method validates the satellite information is received based on the user provided units
    public void response_UserProvidesTheUnitsParameter(String providedUnits){
        RestAssured.given().queryParam("units",providedUnits).when().baseUri(BASE_URL).
                get(URI+prop.getProperty("id")).then().assertThat().
                statusCode(200);
    }

    //This method validates the satellite information based is received and validates on the user provided units
    public void response_UserProvidesTheUnitsParameter(String providedUnits , String expectedUnits){
                RestAssured.given().queryParam("units",providedUnits).when().
                        baseUri(BASE_URL).get(URI+prop.getProperty("id")).then().log().all().assertThat().
                statusCode(200).body("units", Matchers.equalTo(expectedUnits));
    }


    //This method validates the satellite information based on multiple timestamps
    public void request_GETRequestForSatellitePosition_TimeStamp(String timeStamp) {
        ValidatableResponse response = RestAssured.given().queryParam("timestamps", timeStamp).
                when().baseUri(BASE_URL).get(URI + prop.getProperty("id") + "/positions").then().log().all();

        ArrayList<Map<String, ?>> jsonArrayList = response.extract().jsonPath().get();

        for (Map<String, ?> r : jsonArrayList) {
            Assert.assertTrue("name is displayed", r.containsKey("name"));
            Assert.assertTrue("id is displayed", r.containsKey("id"));
            Assert.assertTrue("velocity is displayed", r.containsKey("velocity"));
            Assert.assertTrue("timestamp is displayed", r.containsKey("timestamp"));
            Assert.assertTrue("units is displayed", r.containsKey("units"));
        }
    }

    //This method validates each timestamp data received when user provides more than one timestamp separated by comma
    public void response_UserIsGettingRespOnTimestamp(String timeStamp) {
        String[] arrayOfTimeStamp = timeStamp.split(",");
        List<String> listOfTimeStamp = Arrays.asList(arrayOfTimeStamp);
        ArrayList<String> arrayListTimeStamp = new ArrayList<>(listOfTimeStamp);

        for (String i : arrayListTimeStamp) {
            ValidatableResponse response = RestAssured.given().queryParam("timestamps", timeStamp).
                    when().baseUri(BASE_URL).get(URI + prop.getProperty("id") + "/positions").then().log().all().assertThat().
                    statusCode(200);
            ArrayList<Map<String, ?>> jsonArrayList = response.extract().jsonPath().get();

            for (Map<String, ?> r : jsonArrayList) {
                if (i == r.get("timestamp"))
                    logger.info("The details are properly displayed based on the timestamp provided");
                break;
            }
        }

    }


    //This method validates the satellite information based on multiple timestamps which are more than 10.
    public void response_ForAllTheProvidedAreReceived(String timeStamps) {

        String[] arrayOfTimeStamp = timeStamps.split(",");
        List<String> listOfTimeStamp = Arrays.asList(arrayOfTimeStamp);
        ArrayList<String> arrayListTimeStamp = new ArrayList<>(listOfTimeStamp);

        int userProvidedTimestampSize = arrayListTimeStamp.size();

        ValidatableResponse response = RestAssured.given().queryParam("timestamps", timeStamps).
                when().baseUri(BASE_URL).get(URI + prop.getProperty("id") + "/positions").then().log().all().assertThat().
                statusCode(200);
        ArrayList<Map<String, ?>> jsonArrayList = response.extract().jsonPath().get();

        int responseTimestampSize = jsonArrayList.size();
        Assert.assertEquals("The user is getting the response for all the time stamps below 10",
                userProvidedTimestampSize, responseTimestampSize);

    }


    //This method validates the satellite information based on the user provided unit value.
    public void response_GettingTheUserProvidedUnitValue(String unitValue, String timeStamps){

        ValidatableResponse response = RestAssured.given().
                queryParam("timestamps", timeStamps).
                queryParam("units", unitValue).
                when().baseUri(BASE_URL).get(URI + prop.getProperty("id") + "/positions").then().log().all().assertThat().
                statusCode(200);
        ArrayList<Map<String, ?>> jsonArrayList = response.extract().jsonPath().get();
        for(Map<String, ?> r : jsonArrayList){
            Assert.assertEquals("name is displayed", r.get("units"), unitValue);

        }

    }


    //This method validates the user is getting the default unit value on providing the wrong unit
    public void response_GettingDefaultUnitValueOnProvidingInvalid(String unitValue, String timeStamps){
        ValidatableResponse response = RestAssured.given().
                queryParam("timestamps", timeStamps).
                queryParam("units", unitValue).
                when().baseUri(BASE_URL).get(URI + prop.getProperty("id") + "/positions").then().log().all().assertThat().
                statusCode(200);
        ArrayList<Map<String, ?>> jsonArrayList = response.extract().jsonPath().get();
        for(Map<String, ?> r : jsonArrayList){
            Assert.assertEquals("name is displayed", r.get("units"), "kilometers");

        }
    }

    //This method validates the user is receiving the response on providing the invalid timestamp
    public void request_RequestToRetrieveSatellitePositionInvalidTimestamp(String timeStamps){
        ValidatableResponse response = RestAssured.given().queryParam("timestamps",timeStamps).
                when().baseUri(BASE_URL).get(URI+prop.getProperty("id")+"/positions").then().log().all();

    }


    //This method validates the user is receiving the error message in response on providing the invalid timestamp
    public void response_GettingTheDetailsBasedOnInvalidTimeStamp(String timeStamps){
        ValidatableResponse response = RestAssured.given().queryParam("timestamps",timeStamps).
                when().baseUri(BASE_URL).get(URI+prop.getProperty("id")+"/positions").then().log().all();

        LinkedHashMap<String, ?> jsonMap = response.extract().jsonPath().get();
        Assert.assertTrue("error is displayed", jsonMap.containsKey("error"));
        Assert.assertTrue("status is displayed", jsonMap.containsKey("status"));
    }


    //This method validates the user is receiving the 400 status code in response on providing the invalid timestamp
    public void response_CodeReceivedForTheTimestamp(String responseCode, String timeStamps){
        ValidatableResponse response = RestAssured.given().queryParam("timestamps",timeStamps).
                when().baseUri(BASE_URL).get(URI+prop.getProperty("id")+"/positions").then().log().all().assertThat().
                statusCode(400).body("status", Matchers.equalTo(Integer.parseInt(responseCode)));

    }
}