package app.csas.testcases.participanthomepage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
	Map<String,String> sqlQueryRes = new HashMap<String,String>();
	String ga_id = null ;
	@BeforeClass
	public void ReportInit(){		
		Reporter.initializeModule(this.getClass().getName());
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

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_Employment_Status_As_Active_Or_Terminated
	 *DESCRIPTION:	Verify Employee status as either active or terminated 
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 25-01-16      
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,Participant ID,Employment Status</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Employment_Status_As_Active_Or_Terminated(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			
			Reporter.logEvent(
					Status.PASS,
					"Check if Employment status for plan number : ",
					"Employment status for plan number : ", true);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);

			// Step3:Search for the HireDate & Termination Date based on plan
			participantHomeObj.verify_Employment_Status(
					Stock.GetParameterValue("ppt_id"),
					Stock.GetParameterValue("emp_Status"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_PPT_HomePage_Instance
	 *DESCRIPTION:	Verify Database Instance on PPT home page 
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 25-01-16      
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,Participant ID,DB Instance</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_PPT_HomePage_Instance(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);

			// Step3:Verify page instance against database..
			participantHomeObj.verify_Page_Instance();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
/**
 * -------------------------------------------------------------------
 * <pre>
 *TESTCASE:     Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN
 *DESCRIPTION:  Validate Mail existing PIN and ORder Temp PIN Message Details
 *RETURNS:      VOID    
 *REVISION HISTORY: 
 *--------------------------------------------------------------------
 *Author:Souvik    Date : 25-01-16      
 *--------------------------------------------------------------------
 * </pre>
 * @param <br>CSAS Credential,Participant ID</br>
 */	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Order_Mail_PIN_and_Temp_PIN(int itr,
			Map<String, String> testdata) throws Throwable {
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			// Step2:Querying for ID and GA_ID and performing search with ID  
			if(Web.webdriver.getWindowHandles().size()==1){
			//	sqlQueryRes = participantHomeObj.getSSN_or_pptID(Stock.
			//			      GetParameterValue("web_reg_status"),"ID","GA_ID");
				sqlQueryRes = participantHomeObj.getSSN_or_pptID(Stock.GetParameterValue("web_reg_status"),true,"ID","GA_ID");
				participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
						                                              sqlQueryRes.get("ID"),
						                                              sqlQueryRes.get("GA_ID"));
			}
			// Step3: Verify Mail existing PIN and Order Temp PIN message
			participantHomeObj.verifyPIN_ExistingOrTemp();
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

 
	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_Registration_Status_On_PPT_Home_Page
	 *DESCRIPTION:	Validate Registration status on PPT home page
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author: RANJAN     Date : 09-02-16      
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,Participant ID,Registration Status</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Registration_Status_On_PPT_Home_Page(int itr,
			Map<String, String> testdata) {

		sqlQueryRes = new HashMap<String,String>();
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		// Step1:Launch and logged into CSAS application..
		participantHomeObj = new ParticipantHome().get();
		
		// Step2:Search with PPT ID..		
		sqlQueryRes = participantHomeObj.getSSN_or_pptID(Stock.GetParameterValue("reg_status"),"ID","GA_ID");
		participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",sqlQueryRes.get("ID"),sqlQueryRes.get("GA_ID"));

		// Step3: Verify Registration status
		participantHomeObj.verify_Registration_Status(Stock
				.GetParameterValue("reg_status"));
	} catch (Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				"Exception Occured", true);
	} catch (Error ae) {
        ae.printStackTrace();
        Globals.error = ae;
        String errorMsg = ae.getMessage();
        Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                        errorMsg, true);
	} finally {
		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}	

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_Managed_Account_Status_On_PPT_Home_Page
	 *DESCRIPTION:	 Validate Managed Account status on PPT home page
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author: RANJAN     Date : 02-02-16      
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,Participant ID,Managed Acc Status</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Managed_Account_Status_On_PPT_Home_Page(int itr,
			Map<String, String> testdata) {
		ArrayList<String> pptID_ManagaedAccSts_List_DB;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			//Step2:Search with PPTID
			pptID_ManagaedAccSts_List_DB = participantHomeObj.getPPTIDAndManagedAccSts(Stock.GetParameterValue("managed_acc_status"));
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(pptID_ManagaedAccSts_List_DB.get(0)) ;
			
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",pptID_ManagaedAccSts_List_DB.get(0),ga_id);
			// Step3: Verify Managed Account status
			if (Stock.GetParameterValue("managed_acc_status").equalsIgnoreCase("Plan Not Offered")) {
				participantHomeObj.verify_Managed_Account_Status(Stock
						.GetParameterValue("managed_acc_status"),pptID_ManagaedAccSts_List_DB.get(0));
			}else{
			participantHomeObj.verify_Managed_Account_Status(Stock
					.GetParameterValue("managed_acc_status"),pptID_ManagaedAccSts_List_DB.get(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_Personal_Data_On_PPT_Home
	 *DESCRIPTION:	Validate Personal data on PPT home page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 09-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,Participant ID,SSN</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Personal_Data_On_PPT_Home(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			// Step2:Search with SSN..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN(
					"SSN",Stock.GetParameterValue("ssn"));
			// Step3: Verify Registration status
			participantHomeObj.verify_Personal_Data(Stock
					.GetParameterValue("ssn"));
		} catch (Exception e) {
			e.printStackTrace();	
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_PDI_Status_On_PPT_Home
	 *DESCRIPTION:	Validate
	 * PDI status on PPT home page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 19-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential,PDI Status</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_PDI_Status_On_PPT_Home(int itr,
			Map<String, String> testdata) {
		String pptID = Globals.GC_EMPTY;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			// Step2:Search with SSN..
			pptID = participantHomeObj.getPPTIDForPDIStatus(Stock
					.GetParameterValue("pdi_status"));
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(pptID) ;
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",pptID,ga_id);

			// Step3: Verify PDI status
			participantHomeObj.verify_PDI_Status(Stock
					.GetParameterValue("pdi_status"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_PPT_Home_Account_Balance
	 *DESCRIPTION:	Validate PPT account balance on PPT home page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 25-05-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_PPT_Home_Account_Balance(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);

			// Step3: Verify Account balance
			participantHomeObj.verifyAccountBal_On_PPTHome();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
            ae.printStackTrace();
            Globals.error = ae;
            String errorMsg = ae.getMessage();
            Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
                            errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	/**
	 * <pre>Method to cleanup all active session </pre>
	 * 
	 * @author rnjbdn
	 */
	@AfterSuite
	public void cleanUpSession() {
		Web.webdriver.close();
		Web.webdriver.quit();
	}

}
