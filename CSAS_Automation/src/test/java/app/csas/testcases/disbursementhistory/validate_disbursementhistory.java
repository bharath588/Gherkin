package app.csas.testcases.disbursementhistory;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

import pageobjects.DisbursementInfoOrHistory;
import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_disbursementhistory {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	DisbursementInfoOrHistory disbursementHistoryObj;
	boolean isPageDisplayed;
	String ga_id = null;

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
	
	/**
	 * <pre>Method to verify disbursement history details</pre>
	 * @throws Exception 
	 */
	public void verifyDIHDetails(int itr, Map<String, String> testdata) throws Exception {
		ArrayList<String> disbursementHistory_List;
		participantHomeObj = new ParticipantHome().get();

		// Step2:Search participant using SSN
		disbursementHistoryObj = new DisbursementInfoOrHistory();
		disbursementHistory_List = disbursementHistoryObj
				.getDisbursementHistoryDtls_DB();
		participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("SSN",
				disbursementHistory_List.get(0));

		// Step3:Navigate to Disbursement Info/History page
		disbursementHistoryObj.get();

		// Step4: Search DIB Info with effdate..
		disbursementHistoryObj.clickOnEventID(
				disbursementHistory_List.get(2),
				disbursementHistory_List.get(1));

		// Step5: Click on specific tab on Disbursement History page based
		// on test case
		if (!Stock.GetParameterValue("testcase").equalsIgnoreCase(
				"PaymentHistory")) {
			disbursementHistoryObj.clickOnTABOnDHPage(Stock
					.GetParameterValue("testcase"));
		}
		switch (Stock.GetParameterValue("testcase")) {
		case "VESTING":
			//Verify vesting details
			disbursementHistoryObj.verifyVestingDetails();
			break;
		case "PROCESSINGDETAILS":
			break;
		default:
			// Validate Payment History details..
			disbursementHistoryObj
					.validatePaymentHisDtls(disbursementHistory_List);
			break;
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE:	Verify_Disbursement_History_Details
	 * DESCRIPTION:	Validate Loan status section on Loan info page  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Ranjan     Date : 27-02-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *        CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Verify_DIH_PaymentHistory(int itr, Map<String, String> testdata) {
		ArrayList<String> disbursementHistory_List;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			
			verifyDIHDetails(itr, testdata);

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
	public void Verify_DIH_Vesting(int itr, Map<String, String> testdata) {
		ArrayList<String> disbursementHistory_List;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			verifyDIHDetails(itr, testdata);

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
	public void Verify_DIH_ProcessingDetails(int itr, Map<String, String> testdata) {
		ArrayList<String> disbursementHistory_List;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			verifyDIHDetails(itr, testdata);

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
	}

}
