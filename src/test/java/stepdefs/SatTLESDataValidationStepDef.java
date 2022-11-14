package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import api.SatTLESDataValidation;


public class SatTLESDataValidationStepDef {

    @Steps
    SatDataValidationStepDef satDataValidationStepDef;
    SatTLESDataValidation satTLESDataValidation;


    @Given("user perform GET request to retrieve satellite TLES data")
    public void request_ToRetrieveSatelliteTLESData_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satTLESDataValidation.request_ToRetrieveSatelliteTLESData(id);
    }

    @Then("the user is getting the TLES data in JASON format by default")
    public void response_GettingTheTLESDataInJASONFormat_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satTLESDataValidation.response_GettingTheTLESDataInJASONFormat(id);
    }

    @And("the user is getting the TLES data in TEXT format by selecting the format as TEXT")
    public void response_GettingTheTLESDataInTEXTFormat_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satTLESDataValidation.response_GettingTheTLESDataInTEXTFormat(id);
    }

    @And("the TLES data is properly getting displayed in JSON format")
    public void response_TLESDataIsProperlyGettingDisplayedJSON_StepDef() {
        String id = satDataValidationStepDef.response_The_ListOfAvailableSatellites_StepDef();
        satTLESDataValidation.response_TLESDataIsProperlyGettingDisplayedJSON(id);
    }

}
