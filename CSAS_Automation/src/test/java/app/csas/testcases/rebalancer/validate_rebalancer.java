package app.csas.testcases.rebalancer;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.framework.Globals;
import pageobjects.ParticipantHome;
import pageobjects.Rebalancer;

public class validate_rebalancer {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	
	String tcName;
	Rebalancer rebalancer;
	ParticipantHome participantHomeObj;

	@BeforeClass
	public void ReportInit() {
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
		.getName(), testCase.getName());
	}
	
	public void validate_Rebalancer(int itr,
			Map<String, String> testdata) throws Exception{
		participantHomeObj = new ParticipantHome().get();

		// Step2: Searching for a PPT using PPT ID
		if (Stock.GetParameterValue("isChckBx").equalsIgnoreCase("No")) {
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					Stock.GetParameterValue("PPT_ID"));
		} else {
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					Stock.GetParameterValue("PPT_ID"),Stock.GetParameterValue("ga_id"));
		}
		
		// Step3: Launching Rebalancer
		rebalancer = new Rebalancer().get();
		rebalancer.checkexistingTranfersAndCancel();

		// Step4: Setting value for Rebalancer
		String invtOpt = rebalancer.setRebalancingVal(Stock.GetParameterValue("isChckBx"));

		if (Stock.GetParameterValue("isChckBx").equalsIgnoreCase("Yes")) {
			rebalancer.verify_RedirectFtrCntr(invtOpt);
		}
		// Step5: Submitting Rebalancer
		rebalancer.submitRebalancing();
		
		// Step6: Validating Rebalancer
		rebalancer.validate_Rebalancing_Complete();
	}
	
	/**
	 * <pre>Validate rebalancer</pre>
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void Perform_and_validate_Rebalancer_transferRestriction(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1: Launch and logged into CSAS application.
			validate_Rebalancer(itr, testdata);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Perform_and_validate_Rebalancer_Checkbox(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1: Launch and logged into CSAS application.
			validate_Rebalancer(itr, testdata);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * <pre>
	 * Method to cleanup all active session
	 * </pre>
	 * 
	 * @author rnjbdn
	 */
	@AfterSuite
	public void cleanUpSession() {
		Web.getDriver().close();
		Web.getDriver().quit();
		Web.removeWebDriverInstance();
	}

}
