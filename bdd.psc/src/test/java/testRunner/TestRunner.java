package testRunner;


import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.framework.TestListener;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

  /**
  * @author rvpndy
  *
  */
@CucumberOptions(
		format = { "json", "json:target/cucumber.json" },
		features = { "target/generated-test-sources/cucumber" }, strict = true,
		glue={"org.bdd.psc.stepDefinitions"})
public class TestRunner {
	
	 private TestNGCucumberRunner testNGCucumberRunner;
	 private TestListener testListener;
	 private ISuite suite;
	 private IInvokedMethod method;
	 private ITestResult testResult;

	    @BeforeClass(alwaysRun = true)
	    public void setUpClass() throws Exception {
	    	testListener = new TestListener();
	        testListener.onStart(suite);
	        testListener.beforeInvocation(method, testResult);        
	        
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	        
	    }

	    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	    public void feature(CucumberFeatureWrapper cucumberFeature) {
	        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	    }
	    
	    @DataProvider
	    public Object[][] features() {
	        return testNGCucumberRunner.provideFeatures();
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() throws Exception {
	    	//testListener.onFinish(suite);
	        testNGCucumberRunner.finish();
	    }

}