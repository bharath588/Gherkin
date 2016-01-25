package app.csas.testcases.participanthomepage;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_participanthomepage {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	ParticipantHome participantHomeObj;
	boolean isPageDisplayed;

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
		Globals.GBL_SuiteName = this.getClass().getName();
	}
<<<<<<< Upstream, based on origin/master
	
	/*
	 * Verify Employee status as either active or terminated
	 * 
	 * @Author:Ranjan
	 */

	@Test(dataProvider = "setData")
	public void Validate_Employment_Status_As_Active_Or_Terminated(int itr,
			Map<String, String> testdata) {

		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));

			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(Stock
					.GetParameterValue("ppt_id"),participantHomeObj.getPPTIdfield());

			// Step3:Search for the HireDate & Termination Date based on plan
			participantHomeObj.verify_HireDate_TermDate(Stock
					.GetParameterValue("ppt_id"));
			;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Verify page instance on ppt home page
	 * 
	 * @Author:Ranjan
	 */

	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Page_Instance(int itr,
			Map<String, String> testdata) {

		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));

			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(Stock
					.GetParameterValue("ppt_id"),participantHomeObj.getPPTIdfield());

			// Step3:Verify page instance against database..
			participantHomeObj.verify_Page_Instance();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
<<<<<<< Upstream, based on origin/master
<<<<<<< Upstream, based on origin/master
	
	

	/*
	 * Validate Personal Data on PPT home page
	 * 
	 * @Author:Ranjan
	 */

	@Test(dataProvider = "setData")
	public void Validate_Personal_Data_On_PPT_Home_Page(int itr,
			Map<String, String> testdata) {

		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));

			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(Stock
					.GetParameterValue("ssn"),participantHomeObj.getSSNfield());

			// Step3:Validate Personal Data on PPT home page..
			participantHomeObj.validate_Personal_Data_On_PPT_Home(Stock
					.GetParameterValue("ssn"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
		
=======
	public void Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN(int itr, Map<String, String> testdata) {
=======
	
=======
		
>>>>>>> 1084f0e PPTHomePage Commit for Merging Ranjans code
	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN(int itr, 
			                            Map<String, String> testdata) {
>>>>>>> 2c4ef67 Implemented Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
<<<<<<< Upstream, based on origin/master
>>>>>>> 0619d8f Commiting CommonLib initial

=======
			
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			if(Web.appLoginStatus==false){				
				Reporter.logEvent(Status.PASS,
						"Check if the CSAS Log in page open",
						"CSAS log in page launhced successfully", true);
				participantHomeObj.submitLoginCredentials(
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password"));
			}
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID(Stock
					.GetParameterValue("ppt_id"));
			
			// Step3: Verify Mail existing PIN and Order Temp PIN message
			participantHomeObj.verifyPIN_ExistingOrTemp(Stock.GetParameterValue("pin_type"));
			
>>>>>>> 2c4ef67 Implemented Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@AfterSuite
	public void cleanUpSession() {
		Web.QuitDriver();		
	}

}
