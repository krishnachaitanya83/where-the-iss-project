package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import page.SatDataValidation;
import page.SatPositionDataValidation;

import java.util.List;


public class SatPositionDataValidationStepDef {


    @Steps
    SatDataValidationStepDef satDataValidationStepDef;
    SatPositionDataValidation satPositionDataValidation;



    @Given("user perform GET request to retrieve satellite position based on satellite id")
    public void request_RetrieveSatellitePosition_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satPositionDataValidation.request_RetrieveSatellitePositionInfo(id);
    }


    @And("the user get a valid response for the satellite position info")
    public void response_ValidResponseForTheSatellitePositionInfo_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satPositionDataValidation.response_ValidResponseForTheSatellitePositionInfo(id);
    }

    @Then("the user is getting all the information about the satellite position, velocity and other details")
    public void response_Info_SatellitePositionVelocityEtc_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satPositionDataValidation.response_Info_SatellitePositionVelocityEtc(id);
    }

    @Then("And the user is getting the details only for the current timestamp on providing other {string}")
    public void response_DetailsBasedOnTheTimeStamp_StepDef(String timeStamp) {
        satPositionDataValidation.response_DetailsBasedOnTheTimeStamp_StepDef(timeStamp);
    }

    @When("the user provides the units parameter in {string}")
    public void response_UserProvidesTheUnitsParameter_StepDef(String providedUnits) {
        satPositionDataValidation.response_UserProvidesTheUnitsParameter(providedUnits);

    }

    @Then("the user should get the response in the {string} based on the unit given as {string}")
    public void response_UserGetTheResponseInTheBasedOnUnits_StepDef(String expectedUnits, String providedUnits) {
        satPositionDataValidation.response_UserProvidesTheUnitsParameter(expectedUnits,providedUnits);
    }

    @Given("user perform GET request to retrieve satellite position based on provided {string}")
    public void request_GETRequestForSatellitePosition_TimeStamp_StepDef(String timeStamp) {
        satPositionDataValidation.request_GETRequestForSatellitePosition_TimeStamp(timeStamp);
    }

    @Then("the user is getting the details based on the provided {string}")
    public void response_UserIsGettingRespOnTimestamp_StepDef(String timeStamp) {
        satPositionDataValidation.response_UserIsGettingRespOnTimestamp(timeStamp);
    }

    @And("the response for all the provided {string} are received properly")
    public void response_ForAllTheProvidedAreReceived_StepDef(String timeStamps) {

        satPositionDataValidation.response_ForAllTheProvidedAreReceived(timeStamps);
    }

    @And("the user is getting the unit value in the {string} for all the {string}")
    public void response_GettingTheUserProvidedUnitValue_StepDef(String unitValue, String timeStamps) {
        satPositionDataValidation.response_GettingTheUserProvidedUnitValue(unitValue, timeStamps);
    }

    @And("the user is getting the unit value as default on providing invalid {string} for all the {string}")
    public void response_GettingDefaultUnitValueOnProvidingInvalid_StepDef(String unitValue, String timeStamps) {
        satPositionDataValidation.response_GettingDefaultUnitValueOnProvidingInvalid(unitValue, timeStamps);
    }
}
