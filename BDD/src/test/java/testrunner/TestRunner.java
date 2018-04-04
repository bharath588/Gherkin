/**
 * 
 */
package testrunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author rvpndy
 *
 */
@CucumberOptions(
		features="feature",
		glue={"stepDefinitions"},
		/*format = { "json", "json:target/cucumber.json" },strict = false,*/
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-17543"})
public class TestRunner extends AbstractTestNGCucumberTests {

}


/*import org.junit.runner.RunWith;
import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "json", "json:target/cucumber.json" }, features = { "target/generated-test-sources/" },strict = true)
public class TestRunner {

}
 */
