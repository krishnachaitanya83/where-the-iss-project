package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import api.SatCoordinateDataValidation;


public class SatCoordinatesDataValidationStepDef {

    @Steps
    SatDataValidationStepDef satDataValidationStepDef;
    SatCoordinateDataValidation satCoordinateDataValidation;

    @Given("user perform GET request to retrieve satellite coordinate data with {string}")
    public void request_GETRequestToRetrieveSatCoordinateData_StepDef(String latLongData ) {
        satCoordinateDataValidation.request_GETRequestToRetrieveSatCoordinateData(latLongData);
    }

    @Then("the user is getting the coordinates data in JASON format by default")
    public void response_GettingTheCoordinatesDataInJASONFormat_StepDef() {
        satCoordinateDataValidation.response_GettingTheCoordinatesDataInJASONFormat();
    }

    @And("the contents in the data is properly getting displayed as per the {string} provided")
    public void response_DataIsProperlyGettingDisplayed_StepDef(String latLongData) {
        satCoordinateDataValidation.response_DataIsProperlyGettingDisplayed(latLongData);
    }

    @And("the {string},{string},{string} and {string} data is getting displayed based on {string}")
    public void response_DataDisplayedAsProvidedLatLongDetails_StepDef(String latitude, String longitude, String timeZoneId, String countryCode, String latLongData) {
        satCoordinateDataValidation.response_DataDisplayedAsProvidedLatLongDetails(latitude, longitude, timeZoneId, countryCode, latLongData);
    }

    @And("the user is getting the {string} based on {string}")
    public void response_theUserIsGettingTheResponseOnDetails_StepDef(String responseCode, String latLongData) {
        satCoordinateDataValidation.response_theUserIsGettingTheResponseOnDetails(responseCode, latLongData);
    }

}
