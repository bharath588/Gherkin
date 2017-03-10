package app.csas.testcases.loaninfopage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LoanInfo;
import pageobjects.ParticipantHome;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class validate_loaninfopage {
 
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	ParticipantHome participantHomeObj;
	LoanInfo loanInfopage_Obj ;
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
	 * <pre>Validate loan status details..</pre>
	 * @throws Exception 
	 */
	public void validateLoanStatusDetails(int itr,
			Map<String, String> testdata) throws Exception{
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();

			//Step2: Get PPT ID and expected data for loan info validation
			loanInfo_List = loanInfopage_Obj.getPPTIDAndExpDataForLoanInfo(Stock.GetParameterValue("expecteddata_loaninfo")) ;
			if (loanInfo_List.size() < 1) {
				throw new AssertionError("Sql query doesnot provide any data") ;
			}
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",loanInfo_List.get(0),loanInfo_List.get(1));
			
			participantHomeObj.selectSpecificPlaINPPTHome(loanInfo_List.get(1)) ;
			
			// Step3: Verify Loan info page
			loanInfopage_Obj = new LoanInfo().get() ;
			//Step4:Verify Total Outstanding balance
			loanInfopage_Obj.verify_Loan_Status_Details(Stock.GetParameterValue("loanstatuswebelement"),loanInfo_List.get(2),Stock.GetParameterValue("expecteddata_loaninfo"));
			
	}
	
	
	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_LoanStatus_Details
	 *DESCRIPTION:	Validate Loan status section on Loan info page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_LoanStatus_TotalOutstandingBal(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			validateLoanStatusDetails(itr, testdata);
			
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
	
	@Test(dataProvider = "setData")
	public void Validate_LoanStatus_PaymentFrequency(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			validateLoanStatusDetails(itr, testdata);
			
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
	
	@Test(dataProvider = "setData")
	public void Validate_LoanStatus_PaymentFrequencyMethod(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			validateLoanStatusDetails(itr, testdata);
			
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
	
	@Test(dataProvider = "setData")
	public void Validate_LoanStatus_DefaultIndicator(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			validateLoanStatusDetails(itr, testdata);
			
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
	 * <pre>Method to validate payment history</pre>
	 * @throws Exception 
	 */
	public void validatePaymentHistory(int itr,Map<String, String> testdata) throws Exception{
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();

			//Step2: Get PPT ID and expected data for loan info validation
			loanInfo_List = loanInfopage_Obj.getPPTIDAndExpDataForLoanInfo(Stock.GetParameterValue("expecteddata_loaninfo")) ;
			if (loanInfo_List.size() < 1) {
				throw new AssertionError("Sql query doesnot provide any data") ;
			}
			
			participantHomeObj.selectSpecificPlaINPPTHome(loanInfo_List.get(1)) ;
			
			// Step2:Search with PPT ID..
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",loanInfo_List.get(0),loanInfo_List.get(1));
			
			participantHomeObj.selectSpecificPlaINPPTHome(loanInfo_List.get(1)) ;
			
			// Step3: Verify Loan info page
			loanInfopage_Obj = new LoanInfo().get() ;
			//Step4:Verify Total Outstanding balance
			loanInfopage_Obj.verify_LoanHistory_PAID_Or_UNPAID(Stock.GetParameterValue("PAYMENTSTATUS"));
			
	}
	
	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 *TESTCASE:	Validate_LoanStatus_Details
	 *DESCRIPTION:	Validate Loan status section on Loan info page  
	 *RETURNS:	VOID	
	 *REVISION HISTORY: 
	 *--------------------------------------------------------------------
	 *Author:Ranjan     Date : 27-02-16    
	 *--------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_PaymentHistory_Paid(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			validatePaymentHistory(itr, testdata);
			
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
	
	@Test(dataProvider = "setData")
	public void Validate_PaymentHistory_UnPaid(int itr,
			Map<String, String> testdata) {
		ArrayList<String> loanInfo_List ;
		loanInfopage_Obj = new LoanInfo() ;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			validatePaymentHistory(itr, testdata);
			
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
		Web.removeWebDriverInstance();
	}

}
