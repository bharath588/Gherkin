package org.bdd.psc.testRunner;
/**
 * 
 *//*
package testrunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

*//**
 * @author rvpndy
 *
 *//*
@CucumberOptions(
		features="feature",
		format = { "json", "json:target/cucumber.json" },features = { "target/generated-test-sources" }, strict = true,
		glue={"stepDefinitions"},
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-17122"})
public class TestRunner extends AbstractTestNGCucumberTests {

}
*/

/**
 * 
 */

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author rvpndy
 *
 */
@CucumberOptions(
		features="features",
		glue={"org.bdd.psc.stepDefinitions"},
		/*format = { "json", "json:target/cucumber.json" },strict = false,*/
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-17181"})
public class TestRunner extends AbstractTestNGCucumberTests {

}


/*import org.junit.runner.RunWith;
import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "json", "json:target/cucumber.json" }, features = { "target/generated-test-sources/" },strict = true)
public class TestRunner {

}
 */