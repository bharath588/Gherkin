package core.framework;

import java.util.HashMap;

import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import core.utils.Stock;

public class TestListener implements ITestListener, IConfigurationListener2, ISuiteListener, IInvokedMethodListener {

	
	
	public void onStart(ISuite suite) {

	}

	
	public void onStart(ITestContext test) {
	}

	
	public void onTestStart(ITestResult result) {
	}

	
	public void onTestSuccess(ITestResult result) {
		
	}

	
	public void onTestFailure(ITestResult result) {
		
	}

	
	public void onTestSkipped(ITestResult result) {
		
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	
	public void onFinish(ITestContext context) {
	}

	
	public void onConfigurationSuccess(ITestResult result) {
	}

	
	public void onConfigurationFailure(ITestResult result) {
		
	}

	
	public void onConfigurationSkip(ITestResult result) {
	}

	
	public void beforeConfiguration(ITestResult result) {
	}

	
	public void onFinish(ISuite suite) {
		  	  
	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test
	@SuppressWarnings("unchecked")
	
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.getTestMethod().isTest()) {
			HashMap<String, String> globalTestData = (HashMap<String, String>) testResult.getParameters()[1];
			Stock.globalTestdata = globalTestData;
		}
	}

	
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}	
}