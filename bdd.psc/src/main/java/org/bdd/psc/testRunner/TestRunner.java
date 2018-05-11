package org.bdd.psc.testRunner;
/**
 * 
 */

/*import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

  *//**
  * @author rvpndy
  *
  *//*
@CucumberOptions(
		features="features",
		format = { "json", "json:target/cucumber.json" },features = { "target/generated-test-sources/cucumber" }, strict = true,
		glue={"org.bdd.psc.stepDefinitions"},
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-18139"})
public class TestRunner extends AbstractTestNGCucumberTests {

}*/
   

/**
 * 
 */

import java.io.File;

import org.testng.annotations.AfterClass;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author rvpndy
 *
 */
@CucumberOptions(
		features="features",
		glue={"org.bdd.psc.stepDefinitions"},
		//format = { "json", "json:target/cucumber.json" },strict = false,
		//plugin = {"pretty","html:target/cucumber-html-report"} 
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"},
		monochrome = true
		,tags = {"@SWEB-18139"})
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Windows 7");
        Reporter.setTestRunnerOutput("BDD Tests");
    }

}
