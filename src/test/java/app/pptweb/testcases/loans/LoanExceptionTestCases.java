package app.pptweb.testcases.loans;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.loan.RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU;
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

@SuppressWarnings("unused")
public class LoanExceptionTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public Map<String, String> mapInvestmentOptionsReviewPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsConfirmPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptions = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsRebalance = new HashMap<String, String>();
	public List<String> ConfirmationNos = new ArrayList<String>();
	LoginPage login;
	String tcName;
	static String printTestData = "";
	static String userName = null;
	static String confirmationNumber = "";

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

	private String printTestData() throws Exception {
		printTestData = "";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(
				Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}

	/**
	 * This Test Case to verify Request A Loan Page When PPT is Having Mail Hold Date
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27660_Loan_exceptions_Participant_with_MailHold(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * Pre Condition - Login with a ppt having MAIL_HOLD_DATE in ADDRESS table
			 * 
			 */
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, -190);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			Common.updateMailHoldDateInDB(date);
			/**
			 *  Step 1 to 4
			 *  Launch Browser and enter URL: Login page should be displayed for NextGEn
			 *  Enter Valid credentials and login - Participant should be redirected to home page
			 *  Navigate to 'My Accounts' page - Ppt should be redirected to 'My Accounts' page
			 *  Click on 'Request a Loan' button - Participant should be navigated to 'Loan Reasons' page
			 *  
			 *  
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 5 
			 * Verify the 'Loan Reasons' page 
			 * Participant should be displayed with a hard stop message,
			 *  a message should appear as "Due to a problem with your mailing address, 
			 *  you are unable to request a loan at this time. 
			 *  Please contact a participant services representative to complete this transaction."
			 *  Also below buttons should be disabled:
			 *  Request a General Purpose Loan
			 *  Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithMailHold();

			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Common.updateMailHoldDateInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case to verify Request A Loan Page ownership_ind reflects a spousal takeover
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27908_Loan_exceptions_BR_479_spousal_takeover(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * Pre Condition - Login with a ppt who is eligible for requesting a loan 
			 * and whose ownership_ind reflects a spousal takeover
			 * If ownership_ind is T, then it reflects Spousal takeove
			 * 
			 */
			
			Common.updateOwnership_IndInDB("T");
			/**
			 *  Step 1 to 4
			 *  Launch Browser and enter URL: Login page should be displayed for NextGEn
			 *  Enter Valid credentials and login - Participant should be redirected to home page
			 *  Navigate to 'My Accounts' page - Ppt should be redirected to 'My Accounts' page
			 *  Click on 'Request a Loan' button - Participant should be navigated to 'Loan Reasons' page
			 *  
			 *  
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 5 
			 * Verify the 'Loan Reasons' page 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_479();

			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Common.updateOwnership_IndInDB("N");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This Test Case to verify Request A Loan Page When PPT ownership_ind reflects a QDRO
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27909_Loan_exceptions_BR_480_QDRO(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * Pre Condition - Login with a ppt who is eligible for requesting a loan and 
			 * whose ownership_ind reflects a QDRO
			 * If ownership_ind is Q, then it reflects QDRO
			 * 
			 */
			
			Common.updateOwnership_IndInDB("Q");
			/**
			 *  Step 1 to 4
			 *  Launch Browser and enter URL: Login page should be displayed for NextGEn
			 *  Enter Valid credentials and login - Participant should be redirected to home page
			 *  Navigate to 'My Accounts' page - Ppt should be redirected to 'My Accounts' page
			 *  Click on 'Request a Loan' button - Participant should be navigated to 'Loan Reasons' page
			 *  
			 *  
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 5 
			 * Verify the 'Loan Reasons' page 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_480();

			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Common.updateOwnership_IndInDB("N");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	/**
	 * This Test Case to verify Request A Loan Page When participant is currently non active
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27919_Loan_exceptions_BR_481_participant_currently_non_active(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * Pre Condition - LLogin with a ppt who is eligible for requesting a loan and 
			 * whose STATUS_CODE='T' in PART_AGRMT table.
			 * If STATUS_CODE is T in PART_AGRMT table, then it reflects participant is Non-Active.
			 */
			
		
			/**
			 *  Step 1 to 4
			 *  Launch Browser and enter URL: Login page should be displayed for NextGEn
			 *  Enter Valid credentials and login - Participant should be redirected to home page
			 *  Navigate to 'My Accounts' page - Ppt should be redirected to 'My Accounts' page
			 *  Click on 'Request a Loan' button - Participant should be navigated to 'Loan Reasons' page
			 *  
			 *  
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			Common.updateStatusCodeInDB("T");
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_481();
			Web.clickOnElement(requestLoan, "LOGOUT");
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Common.updateStatusCodeInDB("A");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
}