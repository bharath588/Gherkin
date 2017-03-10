package app.csas.testcases.moneytypebalance;

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

import pageobjects.MoneyTypeBalance;
import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_moneytypebalance {
 
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	ParticipantHome participantHomeObj;
	MoneyTypeBalance moneyTypeBal_Obj ;
	boolean isPageDisplayed;

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
		.getName(), testCase.getName());

	}
	
	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_Active_Allocation_Percentage
	 *DESCRIPTION:	Validate Active allocation percentage on Money Type Balance page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Active_Allocation_Percentage(int itr,
			Map<String, String> testdata) {
		moneyTypeBal_Obj = new MoneyTypeBalance() ;
		String ga_id = null ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);
			String plan_No = participantHomeObj.getCheckedPlanOnPPTHome("Plan_No") ;
			
			// Step3: Verify Loan info page..
			moneyTypeBal_Obj = new MoneyTypeBalance().get() ;
			
			//Step4:Verify Asset allocation /Investment options Percentage..
			moneyTypeBal_Obj.verify_Investment_Option_Percentage(Stock.GetParameterValue("ppt_id"),plan_No) ;
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
	 *TESTCASE:	Validate_Variable_Investment_Money_Type_Link
	 *DESCRIPTION:	Validate Money type link under VI sections  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Variable_Investment_Money_Type_Link(int itr,
			Map<String, String> testdata) {
		moneyTypeBal_Obj = new MoneyTypeBalance() ;
		String ga_id = "" ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);
			String plan_No = participantHomeObj.getCheckedPlanOnPPTHome("Plan_No") ;
			
			// Step3: Verify Loan info page..
			moneyTypeBal_Obj = new MoneyTypeBalance().get() ;
			
			//Step4:Verify Asset allocation /Investment options Percentage..
			moneyTypeBal_Obj.verify_VI_MoneyType_Link(Stock.GetParameterValue("ppt_id"),plan_No) ;
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
	 *TESTCASE:	Verify_Variable_Invstment_Balance
	 *DESCRIPTION:	Validate Variable balance on Money Type Balance page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Verify_Variable_Invstment_Balance(int itr,
			Map<String, String> testdata) {
		moneyTypeBal_Obj = new MoneyTypeBalance() ;
		String ga_id = null ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);
			String plan_No = participantHomeObj.getCheckedPlanOnPPTHome("Plan_No") ;
			
			// Step3: Verify Loan info page..
			moneyTypeBal_Obj = new MoneyTypeBalance().get() ;
			
			//Step4:Verify Variable investment balance..
			moneyTypeBal_Obj.verify_VariableInvestment_Balane(Stock.GetParameterValue("ppt_id")) ;
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
	 *TESTCASE:	Verify_Fixed_Invstment_Balance
	 *DESCRIPTION:	Validate Fixed balance on Money Type Balance page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Verify_Fixed_Invstment_Balance(int itr,
			Map<String, String> testdata) {
		moneyTypeBal_Obj = new MoneyTypeBalance() ;
		String ga_id = null ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);
			
			// Step3: Verify Loan info page..
			moneyTypeBal_Obj = new MoneyTypeBalance().get() ;
			
			//Step4:Verify Fixed investment balance..
			moneyTypeBal_Obj.verify_FixedInvestment_Balane(Stock.GetParameterValue("ppt_id")) ;
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
	 *TESTCASE:	Verify_Money_Type_Totals
	 *DESCRIPTION:	Validate Money Type total balances on Money Type Balance page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Verify_Money_Type_Totals(int itr,
			Map<String, String> testdata) {
		moneyTypeBal_Obj = new MoneyTypeBalance() ;
		String ga_id = null ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("ppt_id")) ;
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",Stock.GetParameterValue("ppt_id"),ga_id);
			
			// Step3: Verify Loan info page..
			moneyTypeBal_Obj = new MoneyTypeBalance().get() ;
			
			//Step4:Verify Fixed investment balance..
			moneyTypeBal_Obj.verify_MoneyTypeTotalsBalance(Stock.GetParameterValue("ppt_id")) ;
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
		Web.getDriver().close();
		Web.getDriver().quit();
	}

}
