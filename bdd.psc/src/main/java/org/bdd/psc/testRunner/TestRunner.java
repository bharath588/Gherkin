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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

/**
 * @author rvpndy
 *
 */
@CucumberOptions(
		features="features",
		glue={"org.bdd.psc.stepDefinitions"},
		//format = { "json", "json:target/cucumber.json" },strict = false,
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		//plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"},
		monochrome = true
		,tags = {"@SWEB-14906"})
public class TestRunner {
	
	   private TestNGCucumberRunner testNGCucumberRunner;

	    @BeforeClass(alwaysRun = true)
	    public void setUpClass() throws Exception {
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	    }

	    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	    public void feature(CucumberFeatureWrapper cucumberFeature) {
	    	try{
	        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }

	    /**
	     * @return returns two dimensional array of {@link CucumberFeatureWrapper} objects.
	     */
	    @DataProvider
	    public Object[][] features() {
	        return testNGCucumberRunner.provideFeatures();
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() throws Exception {
	        testNGCucumberRunner.finish();
	    }
	
	/*@AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Windows 7");
        Reporter.setTestRunnerOutput("BDD Tests");
    }*/
	   

}
