package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@CucumberOptions(
        features = "src/main/test/resources/features",
        glue = {"src/main/java/StepDef"},
        plugin = {
                "pretty", "html:target/cucumber-reports.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
