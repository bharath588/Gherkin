package app.csas.testcases.loanquotepage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LoanInfo;
import pageobjects.LoanQuote;
import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_loanquotepage {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	LoanQuote loanQuotePO;
	boolean isPageDisplayed;
	String ga_id = null ;

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
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE:	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Souvik     Date : 05-04-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * @param <br>
	 *        CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Message_for_Selected_Loan_and_Request_Loan_Quote(
			int itr, Map<String, String> testdata) {
		loanQuotePO = new LoanQuote();
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			if (itr == 1) {
				participantHomeObj = new ParticipantHome().get();
			//	ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("PPT_ID")) ;
			//	participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
			//			Stock.GetParameterValue("PPT_ID"));
				participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
						Stock.GetParameterValue("PPT_ID"));
				// Step2:Navigating to Loan Quote
				loanQuotePO.get();
				loanQuotePO.SelectLoanType(Stock.GetParameterValue("LoanType"));
			}

			// Step3: Validate Selected and Existing Loan Message
			if (loanQuotePO.VerifyMsg_for_Selected_and_Existing_Loan(Stock
					.GetParameterValue("MsgType"))) {
				Reporter.logEvent(Status.PASS, "Validate if "
						+ Stock.GetParameterValue("MsgType").toUpperCase()
						+ " loan type message is displayed correctly", Stock
						.GetParameterValue("MsgType").toUpperCase()
						+ " loan type message is displayed successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate if "
						+ Stock.GetParameterValue("MsgType").toUpperCase()
						+ " loan type message is displayed correctly", Stock
						.GetParameterValue("MsgType").toUpperCase()
						+ " loan type message is displayed inaccurate", true);
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
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE:	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Souvik     Date : 05-04-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Selected_Loan_Type_Details(int itr,
			Map<String, String> testdata) {
		loanQuotePO = new LoanQuote();
		String infoType = Stock.GetParameterValue("InformationType");
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application
			if (itr == 1) {
				participantHomeObj = new ParticipantHome().get();
			//	ga_id = participantHomeObj.getSSN_or_pptID_EmpSts(Stock.GetParameterValue("PPT_ID")) ;
			//	participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
			//			Stock.GetParameterValue("PPT_ID"));
				participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
						Stock.GetParameterValue("PPT_ID"));
				// Step2:Navigating to Loan Quote
				loanQuotePO.get();
				loanQuotePO.SelectLoanType(Stock.GetParameterValue("LoanType"));
				if (!Web.isWebElementDisplayed(loanQuotePO,
						"BTN_CALC_AVAIL_LOAN_AMT", true)) {
					Reporter.logEvent(Status.FAIL,
							"Navigation to Loan Quote page unsuccessfull",
							"Loan Quote page didn't get displayed", true);
				} else {
					Reporter.logEvent(Status.INFO,
							"Navigation to Loan Quote page is successfull",
							"Loan Quote page is displayed as expected", true);
				}
			}
			// Step3: Validate Selected Loan Type - Plan Max/Min Loan amount,
			// Interest Rate if numeric and Max loans allowed
			if (loanQuotePO.Verify_Details_for_Selected_Loan(infoType)) {
				Reporter.logEvent(Status.PASS, "Validate " + infoType
						+ " details", infoType
						+ " details successfully validated", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate " + infoType
						+ " details", infoType + " details validation failed",
						true);
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
