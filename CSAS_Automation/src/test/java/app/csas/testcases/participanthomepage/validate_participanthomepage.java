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
	
	@BeforeClass
	public void ReportInit(){
		Globals.GBL_SuiteName = this.getClass().getName();
	}
	
	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
				.getName(), Globals.GC_MANUAL_TC_NAME);	
	}

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	TESTCASE:			Validate_Employment_Status_As_Active_Or_Terminated
    DESCRIPTION:	    Verify Employee status as either active or terminated 
    PARAMETERS: 		int itr, Map<String, String> testdata
    RETURNS:		    VOID	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Ranjan     Date : 25-01-16      
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void Validate_Employment_Status_As_Active_Or_Terminated(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			if (Web.appLoginStatus == false) {
				Reporter.logEvent(Status.PASS,
						"Check if the CSAS Log in page open",
						"CSAS log in page launhced successfully", true);
				
				participantHomeObj.submitLoginCredentials(
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password"));
			}			
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(
					Stock.GetParameterValue("ppt_id"),
					Web.returnElement(participantHomeObj,"PPT_ID"));

			// Step3:Search for the HireDate & Termination Date based on plan
			participantHomeObj.verify_HireDate_TermDate(
					Stock.GetParameterValue("ppt_id"),
					Stock.GetParameterValue("emp_Status"));

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
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	TESTCASE:			Validate_PPT_Home_Page_Instance
	DESCRIPTION:	    Verify page instance on ppt home page
	PARAMETERS: 		int itr, Map<String, String> testdata
	RETURNS:		    VOID	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Ranjan     Date : 25-01-16      
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Page_Instance(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			if (Web.appLoginStatus == false) {
				Reporter.logEvent(Status.PASS,
						"Check if the CSAS Log in page open",
						"CSAS log in page launhced successfully", true);
				participantHomeObj.submitLoginCredentials(
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password"));
			}			
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(
					Stock.GetParameterValue("ppt_id"),Web.returnElement(participantHomeObj,"PPT_ID"));

			// Step3:Verify page instance against database..
			participantHomeObj.verify_Page_Instance();

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
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	TESTCASE:			Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN
	DESCRIPTION:	    Validate Mail existing PIN and ORder Temp PIN Message Details
	PARAMETERS: 		int itr, Map<String, String> testdata
	RETURNS:		    VOID	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 25-01-16      
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN(int itr,
			Map<String, String> testdata) throws Throwable {
		String pptID = Globals.GC_EMPTY;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			if (Web.appLoginStatus == false) {
				Reporter.logEvent(Status.PASS,
						"Check if the CSAS Log in page open",
						"CSAS log in page launhced successfully", true);
				participantHomeObj.submitLoginCredentials(
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password"));
			}
			
			// Step2:Search with PPT ID..
			if(Stock.GetParameterValue("searchUser").equalsIgnoreCase("TRUE")){
				pptID = participantHomeObj.getPPTID(Stock.GetParameterValue("web_reg_status"));
	
				participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(pptID,Web.returnElement(participantHomeObj,"PPT_ID"));
			}
			
			// Step3: Verify Mail existing PIN and Order Temp PIN message
			participantHomeObj.verifyPIN_ExistingOrTemp();

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
/**  ------------------------------------------------------------------------------------------------------------------------------------------------------------
TESTCASE:			Validate_Registration_Status_On_PPT_Home_Page
DESCRIPTION:	    Validate Registration status on PPT home page
PARAMETERS: 		int itr, Map<String, String> testdata
RETURNS:		    VOID	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Ranjan     Date : 02-02-16      
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void Validate_Registration_Status_On_PPT_Home_Page(int itr,
		Map<String, String> testdata) {
	String pptID = Globals.GC_EMPTY;
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();
		if (Web.appLoginStatus == false) {
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));
		}
		// Step2:Search with PPT ID..
					if(Stock.GetParameterValue("searchUser").equalsIgnoreCase("TRUE")){
						pptID = participantHomeObj.getPPTID(Stock.GetParameterValue("reg_status"));
			
						participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(pptID,Web.returnElement(participantHomeObj,"PPT_ID"));
					}
	/*	// Step2:Search with PPT ID..
		participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(Stock.GetParameterValue("ppt_id"),
				                                Web.returnElement(participantHomeObj,"PPT_ID"));*/

		// Step3: Verify Registration status
		participantHomeObj.verify_Registration_Status(Stock
				.GetParameterValue("reg_status"));
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

/**  ------------------------------------------------------------------------------------------------------------------------------------------------------------
TESTCASE:			Validate_Managed_Account_Status_On_PPT_Home_Page
DESCRIPTION:	    Validate Managed Account status on PPT home page
PARAMETERS: 		int itr, Map<String, String> testdata
RETURNS:		    VOID	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Ranjan     Date : 02-02-16      
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void Validate_Managed_Account_Status_On_PPT_Home_Page(int itr,
		Map<String, String> testdata) {
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();
		if (Web.appLoginStatus == false) {
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));
		}
		// Step2: Verify Managed Account status
		participantHomeObj.verify_Managed_Account_Status(Stock
				.GetParameterValue("managed_acc_status"));
		
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

/**  ------------------------------------------------------------------------------------------------------------------------------------------------------------
TESTCASE:			validate_Personal_Data_On_PPT_Home
DESCRIPTION:	    Validate Personal data on PPT home page
PARAMETERS: 		int itr, Map<String, String> testdata
RETURNS:		    VOID	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Ranjan     Date : 09-02-16      
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	@Test(dataProvider = "setData")
	public void validate_Personal_Data_On_PPT_Home(int itr,
		Map<String, String> testdata) {
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();
		if (Web.appLoginStatus == false) {
			Reporter.logEvent(Status.PASS,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			participantHomeObj.submitLoginCredentials(
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));
		}
		// Step2:Search with SSN..
		participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(Stock.GetParameterValue("ssn"),
				                                Web.returnElement(participantHomeObj,"SSN"));
		// Step3: Verify Registration status
		participantHomeObj.validate_Personal_Data_On_PPT_Home(Stock
				.GetParameterValue("ssn"));
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

	/**
	 * Method to cleanup all active session
	 * 
	 * @author rnjbdn
	 */
	@AfterSuite
	public void cleanUpSession() {
		Web.webdriver.close();
		Web.webdriver.quit();
	}

}
