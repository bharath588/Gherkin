package org.bdd.psc.testRunner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

/**
 * @author rvpndy
 *
 */
@CucumberOptions(
		features="features",
		glue={"org.bdd.psc.stepDefinitions"},
		//format = { "json", "json:target/cucumber.json" },strict = false, //for behavePro		
		plugin = {"pretty","html:target/cucumber-html-report"} ,
		monochrome = true
		,tags = {"@SWEB-18868"})


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
	   

}
