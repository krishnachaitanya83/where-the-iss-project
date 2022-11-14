import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/resources/features",
    //    tags = "@SatellitePosition",
        dryRun = false,
        glue = {"stepdefs"}
)
public class RunTest {
}