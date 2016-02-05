package core.framework;

import java.util.HashMap;

import lib.Stock;
import lib.Web;

import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IConfigurationListener2, ISuiteListener, IInvokedMethodListener {

	
	public void onStart(ISuite suite) {
		try{
			Stock.getParam(Globals.GC_TESTCONFIGLOC+
			Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void onStart(ITestContext test) {	
		try{	
			if(!Web.webdriver.getWindowHandle().isEmpty()){
				System.out.println("WebDriver not null");
			}
		}catch(Exception e){	
			System.out.println("Initiating WebDriver");
			Web.webdriver = Web.getWebDriver(Stock.globalParam.get("BROWSER"));
			Web.appLoginStatus = false; 
		}		
		Globals.GC_MANUAL_TC_NAME = test.getName();		
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
		Web.webdriver.quit();  	  
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