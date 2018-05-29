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
			requestLoan.clickOnRequestANewLoan();
			
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
			requestLoan.clickOnRequestANewLoan();
			
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
			requestLoan.clickOnRequestANewLoan();
			
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
			requestLoan.clickOnRequestANewLoan();
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
	/**
	 * This Test Case to verify Request A Loan Page When participant's death date updated in DB
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27926_Loan_exceptions_BR_484_participant_deceased(
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
			 * Pre Condition -Login with a ppt who is eligible for requesting a loan and whose 
			 * death_date <> NULL in INDIVIDUAL table.
			 * If death_date or death_notification_date is NOT NULL in INDIVIDUAL table, 
			 * then it reflects participant is Deceased.
			 */
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, -190);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			Common.updateDeathDateInDB(date);
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_484();
			
			

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
				Common.updateDeathDateInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case to verify Request A Loan Page When participant is having restriction code in art_argmt Table
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27969_Loan_exceptions_BR_486_Participant_has_PartAgrmt_restriction(
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
			 * Pre Condition - Login with a ppt who is having restriction code in PART_AGRMT table.
			 * 
			 */
			
			
			Common.updateRestrictionCodeInDB(Stock.GetParameterValue("Restc_Code"));
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_486();
			
			

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
				Common.updateRestrictionCodeInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	/**
	 * This Test Case to verify Request A Loan Page When participant group has disbursement hold
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27988_Loan_exceptions_BR_467_Participant_group_has_disbursement_hold(
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
			 * and whose participant's group has disbursement hold.
			 * 
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
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_467();
			
			

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
				Common.updateRestrictionCodeInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case to verify Request A Loan Page When participant's plan does not allow loan
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27989_Loan_exceptions_BR_468_Participant_plan_doesnot_allow_Loan(
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
			 * and whose participant's plan does not allow loan
			 * setup plan. LOAN_ADMIN_IND = N
			 * 
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
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Based on your plan provisions, you are not eligible to take a loan." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_468();
			
			

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
				Common.updateRestrictionCodeInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		

	}
	
	/**
	 * This Test Case to verify Request A Loan Page When  participant having pending distribution
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27991_Loan_exceptions_BR_478_participant_having_pending_distribution(
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
			 * Pre Condition - Login with a ppt who is eligible for requesting a loan and participant 
			 * having Pending Distribution or if existing distribution has an effective date = current system date
			 * 
			 */
			
		
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			

			requestLoan.clickOnRequestANewLoan();

			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			if(requestLoan.getPendingLoans(Stock.GetParameterValue("username")))
				Reporter.logEvent(Status.PASS,
					"Pre requisite : Verify Participant is Having Pending Loan",
					"Participant is Having Pending Loan Request", false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Pre requisite : Verify Participant is Having Pending Loan",
					"Participant is Not Having Pending Loan Request", false);
		
			
			/**
			 *  Step 1 to 4
			 *  Launch Browser and enter URL: Login page should be displayed for NextGEn
			 *  Enter Valid credentials and login - Participant should be redirected to home page
			 *  Navigate to 'My Accounts' page - Ppt should be redirected to 'My Accounts' page
			 *  Click on 'Request a Loan' button - Participant should be navigated to 'Loan Reasons' page
			 *  
			 *  
			 */
			
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Due to a pending transaction, you are unable to request a loan."
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_478();
			
			

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
				Common.updateRestrictionCodeInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	/**
	 * This Test Case to verify Request A Loan Page When  participant having pending distribution
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27992_Loan_exceptions_BR_487_participant_having_pending_distribution(
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
			 * Pre Condition - Login with a ppt who is eligible for requesting a loan and participant 
			 * having Pending Distribution or if existing distribution has an effective date = current system date
			 * 
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
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
		
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Due to a pending transaction, you are unable to request a loan."
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_478();
			
			

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
				Common.updateRestrictionCodeInDB(null);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	/**
	 * This Test Case to verify Request A Loan Page When having periodic payments.
	 * 
	 * @param itr
	 * @param testdata
	 */
	//TODO
	@Test(dataProvider = "setData")
	public void DDTC_28011_Loan_exceptions_BR_476_participant_having_periodic_payments(
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
			 * Pre Condition - Login with a ppt having periodic payments.
			 * 
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
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
		
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Due to a pending transaction, you are unable to request a loan."
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_478();
			
			

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
			
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * This Test Case to verify Request A Loan Page When Loans are not allowed for the Group
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_28037_Loan_exceptions_BR_510_Loans_are_not_allowed_for_Group(
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
			 * Pre Condition - Login with a  participant whose group does not allow loan request.
			 * 
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
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
		
			requestLoan.get();
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *in a modal window: "Based on your plan's loan provisions, you are not eligible to take a loan."
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_510();
			
			

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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}

	/**
	 * This Test Case to verify Request A Loan Page When Loans allowed only for Hardship Reason
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_28067_Loan_exceptions_BR_473_Loans_allowed_only_for_Hardship_Reason(
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
			 * Pre Condition - Login with a ppt whose plan will only allow loans for reason of Hardship.
			 * HardshipInd=y
			 * 
			 */
			Common.updateLoanHardshipIndicators("Y", Stock.GetParameterValue("ga_id"));
		
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *Please call Customer Service for assistance." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_473();
			
			

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
				Common.updateLoanHardshipIndicators("N", Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	/**
	 * This Test Case to verify Request A Loan Page When participant having foreign address
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_28065_Loan_exceptions_BR_488_participant_having_foreign_address(
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
			 * Pre Condition - Login with a ppt who is having foreign address
			 * 
			 * 
			 */
			Common.updateForeignAddressInDB();
		
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *Please call Customer Service for assistance." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_488();
			
			

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
			
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	/**
	 * This Test Case to verify Request A Loan Page with ppt whose employment is terminated
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_28041_Loan_exceptions_BR_485_employment_terminated(
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
			 * Pre Condition - Login with a terminated ppt under a plan allowing loans.
			 * 
			 * 
			 */
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, -190);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			Common.updateTerminatedEmployeeLoanIndicator("N", Stock.GetParameterValue("ga_id"));
			Common.updateTerminationDateInDB(date, Stock.GetParameterValue("gc_id"));
		
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *Please call Customer Service for assistance." 
			 *at the top of the page and should not be allowed proceed with loan request.
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_485();
			
			/**
			 * Step 6 - Set the CHGR --> CHANGE GROUP LOAN STRUCTURE --> Set 'Loans to terminated employees' as 'Yes'.
			 */
			Common.updateTerminatedEmployeeLoanIndicator("Y", Stock.GetParameterValue("ga_id"));
			/**
			 * Step 7 - Perform steps 1, 2 & 3
			 */
			
			requestLoan.get();
			Common.waitForProgressBar();
			/**
			 * Step 8 - Click on 'Request a Loan' button.
			 * Participant should be navigated to 'Loan Reasons' page.
			 */
			requestLoan.clickOnRequestANewLoan();
			/**
			 * Step 9 - Verify the 'Loan Reasons' page
			 * Participant able to initiate loan with no issues
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			if(requestLoan.getPendingLoans(Stock.GetParameterValue("username")))
				Reporter.logEvent(Status.PASS,
					"Verify Participant is Able to Submit Loan Request",
					"Participant is Able to Submit Loan Request", false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Participant is Able to Submit Loan Request",
					"Participant is Not Able to Submit Loan Request", false);
			
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
				Common.updateTerminationDateInDB(null, Stock.GetParameterValue("gc_id"));
				Common.updateTerminatedEmployeeLoanIndicator("N", Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * This Test Case to verify Request A Loan Page when 
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_28040_Loan_exceptions_BR_474_Participant_does_not_meet_contribution_History_Criteria(
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
			 * Pre Condition - Login with a participant who does not meet the contribution history criteria.
			 * 
			 * 
			 */
			
			Common.updateContributionHistoryDays("30", Stock.GetParameterValue("ga_id"));
		
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
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 5 
			 *Participant should be displayed with a hard stop message
			 *"Loan Quotes and Processing cannot occur because there were no recent Contributions found." 
			 *at the top of the page and should not be allowed proceed with loan request
			 *Also below buttons should be disabled:
			 *Request a General Purpose Loan
			 *Request a Principal Residence Loan
			 */
			requestLoan.verifyPPTRequestLoanPageWithBR_474();
			
			
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
				Common.updateContributionHistoryDays(null, Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
}