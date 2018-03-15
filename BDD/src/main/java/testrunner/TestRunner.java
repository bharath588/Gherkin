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
		/*format = { "json:target/cucumber.json", "html:target/reports" },features = { "target/generated-test-sources" }, strict = false*/
		glue={"stepDefinitions"},
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-17122"})
public class TestRunner extends AbstractTestNGCucumberTests {

}
