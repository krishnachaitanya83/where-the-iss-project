package page;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class APIUtils {
    private final Headers headers =  new Headers();
    private static Response response;

    public static final String BASE_URL = "https://api.wheretheiss.at/v1";


    public static int getResponseCode(String endPoint){
        response = RestAssured.given().baseUri(BASE_URL).get(endPoint);
        return response.getStatusCode();
    }

    public static String getResponseData(String uri){
        response = RestAssured.given().baseUri(BASE_URL).get(uri);
        return response.getBody().asPrettyString();
    }

    public static Response getResponse(String uri){
        response = RestAssured.given().baseUri(BASE_URL).get(uri);
        return response;
    }
    public static void assertGetRequest(String uri, String statusCode) {
        RestAssured.given().when().baseUri(BASE_URL).baseUri(uri)
                .then().statusCode(Integer.parseInt(statusCode));
    }




    public static void assertGetRequestStringField(String uri, String key, String expectedValue) {
        ResponseBody body = RestAssured.given().when().baseUri(BASE_URL).
                get(uri).getBody();
        String data = body.asString();
        JsonPath path = body.jsonPath();
        ArrayList<Integer> value = path.get(key);
        Assert.assertTrue(value.contains(expectedValue));
    }


    public static int getIDFromResponse(String endPoint){
        ResponseBody body = RestAssured.given().when().baseUri(BASE_URL).
                get(endPoint).getBody();
        JsonPath path = body.jsonPath();
        ArrayList<Integer> value = path.get("id");
        return value.get(0);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException{
        FileInputStream fis = null;
        Properties prop = null;
        try{
            fis = new FileInputStream((fileName));
            prop = new Properties();
            prop.load(fis);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return prop;
    }


    public static long getCurrentTimestamp(){
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())/100;
    }

    public static int getJsonIntegerData(Response response, String field){
        ResponseBody body = response.getBody();
        String bodyStringValue = body.asString();
        Assert.assertTrue(bodyStringValue.contains(field));
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(field);

    }

    public static Date convertTimeStampToDate(int timeStamp){
        Instant instant = Instant.ofEpochSecond( timeStamp );
        return Date.from( instant );
    }

    public static Date getCurrentDate(){
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
        return time.getTime();
    }



}
