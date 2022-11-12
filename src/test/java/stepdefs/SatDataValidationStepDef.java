package stepdefs;

import io.cucumber.java.en.And;
import page.SatDataValidation;


public class SatDataValidationStepDef {

    SatDataValidation satDataValidation;

    @And("^user perform GET request to retrieve satellites information$")
    public void list_Of_Satellites_StepDef(){
        satDataValidation.list_Of_Satellites();
    }

    @And("list of satellites are retrieved")
    public String response_The_ListOfAvailableSatellites_StepDef() {
        return satDataValidation.response_The_ListOfAvailableSatellites();
    }

    @And("response code should be {string}")
    public void response_responseCodeShouldBe_StepDef(String responseCode) {
        satDataValidation.response_responseCodeShouldBe(responseCode);    }
}
