package app.pptweb.testcases.loans;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
public class LoansTestCases {
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
	 * This Test Case to verify General Purpose Loan Repayment Page
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27488_General_Purpose_Loan_Repayment_Term_Page_Verification(
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
			RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU rvmd_reversal;
			rvmd_reversal = new RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU();
			// General.setDatabaseInstance(Stock.GetParameterValue("LOGON_DATABASE"));
			/*rvmd_reversal.setServiceParameters(Stock.GetParameterValue("SSN"));
			rvmd_reversal.runService();
			rvmd_reversal.validateOutput();
			rvmd_reversal.validateInDatabase(Stock.GetParameterValue("SSN"));*/
			// Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			// Step 4
			requestLoan.verifyLoansLandingPage();

			// Step 5
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			// Step 6
			// TODO
			// Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));
			// Step 8
			requestLoan.verifyLoansDisclaimer();
			// Step 9
			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			// Step 10
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));

			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			// Step 11
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan
					.verifyWebElementIsDisplayed("Link How Is This Calculated");
			// Step 12
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			// Step 13
			requestLoan
					.isTextFieldDisplayed("Select from a repayment term option below or");

			// Step 14
			requestLoan.isTextFieldDisplayed("enter your own term");
			requestLoan.verifyWebElementIsDisplayed("Link Enter Your Own Term");
			// Step 15
			requestLoan.isTextFieldDisplayed("Select");
			requestLoan.isTextFieldDisplayed(" bi-weekly");
			requestLoan.isTextFieldDisplayed("Repayment Term");
			requestLoan.verifyDefaultRepamentTerm();
			// Step 16
			// TODO
			// Step 17
			// TODO
			// Step 18
			requestLoan.EnterLoanAmount("200");
			requestLoan.verifyWebElementIsDisplayed("BUTTON UPDATE");
			// Step 19
			Web.clickOnElement(requestLoan, "BUTTON UPDATE");

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
	 * This Test Case to verify General Purpose Loan My Loan Page
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27491_General_Purpose_Loan_My_Loan_Page_Verification(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Verify the disclaimer displayed at the bottom of the
			 * page on loans landing page Validate the disclaimer displayed at
			 * the bottom of the page is displayed per latest design reqs on
			 * loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();

			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));

			/**
			 * Step 10 - Select 'Request a General purpose loan' button PPT is
			 * navigated to General purpose loan page/ How much would you like
			 * to borrow page with the max and min amount balance for General
			 * purpose loan and a 'Enter amount' text box to enter valid amt
			 * values with a 'Continue' and 'Back' buttons 'How is this
			 * calculated?' link displaying the calculation in the hover help
			 * from design team
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");

			/**
			 * Step 11 - Enter amount in entry box Validate only proper values
			 * (numeric/decimal)are accepted into this field Validate 'Continue'
			 * button is enabled only when the min and max amt values are met
			 * else display the min,max amt error in red text if the criteria is
			 * not met.
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan
					.verifyWebElementIsDisplayed("Link How Is This Calculated");

			/**
			 * Step 12 - Hit 'Continue' button Validate that Repayment Term
			 * options section should be displayed below the 'How much would you
			 * like to borrow' section
			 */

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 13 - Verify the Repayment Term options/values section
			 * verbiage Verbiage should be
			 * "Select from a repayment term option below or enter your own term"
			 */
			requestLoan
					.isTextFieldDisplayed("Select from a repayment term option below or");

			/**
			 * Step 14 - Verify "enter your own loan term" at the end of the
			 * verbiage It must be a link & text should be in blue color
			 */

			requestLoan.isTextFieldDisplayed("enter your own term");
			requestLoan.verifyWebElementIsDisplayed("Link Enter Your Own Term");

			/**
			 * Step 15 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers i) SELECT ii)
			 * <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Five 'Repayment Term' options should be offered to the
			 * participant with below tenures by default in ascending order 12
			 * months 24 months 36 months 48 months 60 months
			 */

			requestLoan.isTextFieldDisplayed("Select");
			requestLoan.isTextFieldDisplayed(" bi-weekly");
			requestLoan.isTextFieldDisplayed("Repayment Term");
			requestLoan.verifyDefaultRepamentTerm();

			/**
			 * Step 16 - Verify the 2nd column header in the repayment term
			 * table It should be <Repayment Frequency Value>(Dynamic value)
			 * asper the plan set up. It can be weekly, Bi-Weekly, Monthly,
			 * Semi-Monthly or Quarterly
			 */
			// TODO
			/**
			 * Step 17 - Verify the Repayment Term options/values or the per pay
			 * period amount for each tenure/REPAYMENT TERM Validate per pay
			 * period amount values are displayed correct as per the loan amount
			 * entered for each REPAYMENT TERM.
			 */
			// TODO
			/**
			 * Step 18 -Verify SELECT column for each loan term row Each loan
			 * term row should have the radio button
			 */
			requestLoan.verifySelectColumnForLoanTerm();

			/**
			 * Step 19 - Verify the Repayment term options or number of radio
			 * button selections ppt can select Participant must be able to
			 * select only ONE Repayment term option or radio button at an
			 * instance Step 20 - Select Repayment Term option say for e.g. 24
			 * months by clicking on the respective radio button present the
			 * Repayment Term row Validate that upon term selection Delivery
			 * options & 'My Loan Summary section should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("My loan summary");

			/**
			 * Step 21 -Verify the header of the 'Delivery Options' section It
			 * should be "How would you like your check to be delivered?"
			 */

			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");

			/**
			 * Step 22 and 23 Verify the 'Delivery Options' offered for ppt with
			 * ACH set up It should be i) Regular mail Delivery up to 5 business
			 * days Free ii) Expedited mail Delivery up to 2 business days $25
			 * iii) Electronically transfer funds directly to my bank account
			 * (ACH) Delivery up to 3 business days $30.00
			 */
			requestLoan.verifyMailDeliveryOptionsWithACH();

			/**
			 * Step 24 - Verify the selection of the Delivery method' by default
			 * once the radio button is selected By default 'Regular mail'
			 * delivery method should be pre-selected
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();

			/**
			 * Step 25 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			/**
			 * Step 26 - It must be present & enabled It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */

			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step27 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */
			requestLoan.verifyInterestRateInLoanSummaryTable();
			
			/**
			 * Step 28 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 29 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 30 - Hover the mouse on the 'FEES' value 
			 */
		
			/**
			 * Step 31 - Verify for the Maintenance Fee value in 'FEES' tool tip
			 * 
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 32 - Verify the CHECK TOTAL value - It must have the value
			 * of the loan amount requested minus the FEES CHECK TOTAL = LOAN
			 * AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmount();
			/**
			 * Step 33 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();

			/**
			 * Step 34 - Verify the verbiage at the bottom of the page informing
			 * about the Maintenance Fee Verbiage should be present at the
			 * bottom of the page above the 'Back' button Verbiage is
			 * "* Excludes quarterly $7.50 maintenance fee."
			 */
			requestLoan
					.isTextFieldDisplayed("* Excludes quarterly $7.50 maintenance fee.");

			/**
			 * Step 35 - Change the Repayment Term option say for e.g. 36 months
			 * by c licking on the respective radio button present the Repayment
			 * Term row.
			 * 
			 * Validate that upon term selection change Delivery options & 'My
			 * Loan Summary section should be displayed - N/A
			 */

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
	 * This Test Case to verify General Purpose Loan Repayment Page
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27500_General_Purpose_Loan_Summary_Page_Verification(
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

			// Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			// Step 4
			requestLoan.verifyLoansLandingPage();

			// Step 5
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			// Step 6
			// TODO
			// Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));
			// Step 8
			requestLoan.verifyLoansDisclaimer();
			// Step 9
			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			// Step 10
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));

			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			// Step 11
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan
					.verifyWebElementIsDisplayed("Link How Is This Calculated");
			// Step 12
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			// Step 13
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");

			
			requestLoan.verifyLoanTotalAmount();

			// Step 14
			requestLoan.clickContinueButton();
			
			requestLoan
					.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan
					.isTextFieldDisplayed("Sign up for updates on your loan process");
			// Step 15
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			// Step 16 to 21
			requestLoan.verifyLoanSummarypage();
			// Step 22 and 23
			requestLoan.verifyLoanProvisionLink();
			// Step 24
			requestLoan
					.verifyWebElementIsDisplayed("LOAN ACKNOWLEDGEMENT WINDOW");

			// Step 25
			if (Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
					true)) {
				Reporter.logEvent(Status.PASS,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Not Displayed", true);
			}

		}

		catch (Exception e) {
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
	 * This Test Case to verify General Purpose Loan What's Next page
	 * 
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27501_General_Purpose_Loan_Confirmation_Whats_Next_Page_Verification(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */

			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));

			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Verify the disclaimer displayed at the bottom of the
			 * page on loans landing page Validate the disclaimer displayed at
			 * the bottom of the page is displayed per latest design reqs on
			 * loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();

			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));
			
			

			/**
			 * Step 10 - Select 'Request a General purpose loan' button PPT is
			 * navigated to General purpose loan page/ How much would you like
			 * to borrow page with the max and min amount balance for General
			 * purpose loan and a 'Enter amount' text box to enter valid amt
			 * values with a 'Continue' and 'Back' buttons 'How is this
			 * calculated?' link displaying the calculation in the hover help
			 * from design team
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");

			/**
			 * Step 11 - Enter amount in entry box Validate only proper values
			 * (numeric/decimal)are accepted into this field Validate 'Continue'
			 * button is enabled only when the min and max amt values are met
			 * else display the min,max amt error in red text if the criteria is
			 * not met.
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan
					.verifyWebElementIsDisplayed("Link How Is This Calculated");

			/**
			 * Step 12 - Hit 'Continue' button Validate that Repayment Term
			 * options section should be displayed below the 'How much would you
			 * like to borrow' section
			 */

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 13 - Opt for a desired REPAYMENT TERM by selecting the radio
			 * button Verbiage should be
			 * "Select from a repayment term option below or enter your own term"
			 */

			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			

			/**
			 * Step 14 - Click on 'Continue' button Confirm Information page
			 * should be displayed
			 */
			requestLoan.clickContinueButton();

			requestLoan
					.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan
					.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 15 -In 'Confirm Information' page, verify the email address,
			 * phone number and click 'Continue' button In 'Confirm Information'
			 * page, verify the email address, phone number and click 'Continue'
			 * button
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 16 - Verify the Loan Summary page and click on 'I agree &
			 * Submit' page User should be displayed with the Confirmation page
			 */

			if (Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
					true)) {
				Reporter.logEvent(Status.PASS,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Not Displayed", true);
			}

			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			/**
			 * Step 17 - Verify the page sections It should have 'Loan request
			 * received' and 'Confirmation' sections
			 */
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");

			/**
			 * Step 18 - Verify the disclaimer message below the 'Loan request
			 * received' section It should be 'Your loan request has been
			 * received is being processed. Depending on your preference, you
			 * will receive an email and/or text with your loan's status.
			 * 
			 */

			requestLoan.verifyLoanConfirmationDisclaimer();

			/**
			 * Step 19 to 26 - Verify the information under the 'Loan request
			 * received' section It should have below information: 1 Loan
			 * request received 2 Approval from your employer 3. Processing 4.
			 * Check sent by <selected delivery option>
			 * 
			 */
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();

			/**
			 * Step 27 - N/A FOR OTHER SECTIONS MOCKUP IS NOT UPDATED.THIS TEST
			 * CASE WILL BE UPDATED ONCE THE MOCKUP SCREEN IS AVAILABLE
			 * 
			 */

			/**
			 * Step 28 - Verify the 'Print' hyperlink It must be present at the
			 * top right corner of the page
			 */
			requestLoan.verifyWebElementIsDisplayed("PRINT LINK");

			/**
			 * Step 29 - Click on 'Print' hyperlink - partially Automatable
			 * 'Confirmation & What's next' page should be opened in a new modal
			 * window in PDF format & user should be able to save it Step 30 -
			 * Verify the content of the PDF - N/A It should be same exactly as
			 * the 'Confirmation & What's next' page
			 * 
			 */

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
	 * This Test Case to verify General Purpose Loan Page After Submitting the
	 * Loan Request
	 * 
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27555_General_Purpose_Loan_Page_verification_after_submitting_Loan_Request(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a General purpose loan' button PPT is
			 * navigated to General purpose loan page/ How much would you like
			 * to borrow page with the max and min amount balance for General
			 * purpose loan and a 'Enter amount' text box to enter valid amt
			 * values with a 'Continue' and 'Back' buttons
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			

			/**
			 * Step 10 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 11 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			/**
			 * Step - 12 Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed It should be "How
			 * would you like your check to be delivered?
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			
			
			/**
			 * Step 13 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * NOTE: ACH set up for the participant can be checked in part_banking table
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 14 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			/**
			 * Step 15 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			/**
			 * Step 16 - Verify the 'My loan summary' section It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */

			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value 
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value 
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 21 -Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 * $9950 = $10000 - $50
			 */
			requestLoan.verifyCheckTotalAmount();
			
			
			/**
			 * Step 22 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();
			/**
			 * Step 23 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 24 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 25 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 26 - Click 'I agree & submit' after confirming the loan summary page info
			 * Participant should be displayed with the Loan Confirmation page
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 27 - Now, click on the 'Loans' in left nav of the page
			 * Loans page should be displayed
			 */
			requestLoan.get();

			/**
			 * Step 28 - NOTE: Before verifying the below step ensure that 
			 * loan is processed using EASY or the DDRV process so that the changes are reflected in UI.
			 * Process to do will be updated in the same TC  -N/A
			 * 
			 */
			/**
			 * Step 29 - Verify the Loans page
			 * AVAILABLE TO BORROW should be $15000
			 * Number of active loans in this plan should be 1
			 * Number of active loans in all plans should be 1 - N/A
			 */
			/**
			 * Step 30 - Verify for the new section ACTIVE LOANS in the LOANS page after the loan request is submitted
			 * ACTIVE LOANS section should consist below columns
			 * LOAN NUMBER = 1
			 * PAYMENT AMOUNT =
			 * OUTSTANDING BALANCE = Loan Amount i.e. $10000
			 * MATURITY DATE = CURRENT DATE + 48 months(refer STEP 11)
			 * REMAINING TERM = 48 - N/A
			 */
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
	 * This Test Case to Validate Enter Amount Field
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27594_Loan_Enter_Amount_field_validation(
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
			 * Step 1 to 2 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 3 - Click on 'Request a General Purpose loan' button 
			 * PPT is navigated to  'How much would you like to borrow' page
			 */
			String maxLoanAmount=Float.toString(Web.getIntegerCurrency(requestLoan.getMaximumLoanAmount())+10);
			requestLoan.clickOnRequestANewLoan();
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));

			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 4 - Enter amount lesser than the 'Minimum amount required to borrow a loan.
			 * Ex: If min is $1000
			 * enter the amount as 500 
			 * Display the error message in red below the field 
			 * 'Minimum loan amount is $1000' and 'Continue' button should remain in disabled status
			 */
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			/**
			 * Step 5 - Enter amount greater than the loan amount available to borrow
			 * Ex: If max is $25000
			 * enter the amount as 26000 
			 * Display the error message in red below the field 'Maximum loan amount is $25000'
			 * and 'Continue' button should remain in disabled status
			 */
			
			requestLoan.verifyLoanMaximumErrorMessageIsDisplayed(maxLoanAmount);
			
			/**
			 * Step 6 - Enter a valid amount which is greater than loan min & lesser than available to borrow
			 * Ex: If min is $1000 & max is $25000
			 * enter the amount as $14000 
			 * It should be accepeted & 'Continue' button should be enabled.
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 7 - Now, delete/clear the value in the ENTER AMOUNT field
			 * 'Continue' button should be disabled
			 */
			
			requestLoan.EnterLoanAmount("");
		
			//Web.clickOnElement(requestLoan, "INPUT AMOUNT FIELD");
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
			Reporter.logEvent(Status.INFO,
					"Clear the Amount from the Enter Amount Field",
					"Enter Amount Input Field is Empty and Continue button should be disabled",
					true);
			requestLoan.verifyContinueButtonIsEnabled(false);
			
			/**
			 *Step 8 - Try to enter special characters or the alphabets
			 * ENTER AMOUNT field should not accept the values
			 */
			requestLoan.EnterLoanAmount("@#$");
			if(requestLoan.getWebElementValueAttribute("INPUT AMOUNT FIELD").isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Amount Field is not Accepting Special Characters",
						"Loan Amount Field is not Accepting Special Characters",
						true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Amount Field is not Accepting Special Characters",
						"Loan Amount Field is Accepting Special Characters",
						true);
			/**
			 * Step 9 - Enter a decimal value 123.55
			 * It should be accepted & 'Continue' button should be enabled
			 */
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount")+".23");
			requestLoan.verifyContinueButtonIsEnabled(true);
			/**
			 * Step 10 - Click on 'Continue' button
			 * Repayment Term table should be populated for the entered amount
			 */
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			/**
			 * Step 11 - Now, again modify/change the amount to $5000
			 * Verify that 'Continue' button label should be changed to 'Update'
			 */
			requestLoan.EnterLoanAmount("1500");
			Web.waitForElement(requestLoan,"BUTTON UPDATE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON UPDATE");
			
			
		}

		catch (Exception e) {
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
	 * This Test Case to verify General Purpose Loan via Expedited Mail Delivery
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27611_General_Purpose_Loan_Request_via_Expedited_mail_delivery_method(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();
			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 10 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 11 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			/**
			 * Step - 12 Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed It should be "How
			 * would you like your check to be delivered?
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			
			
			/**
			 * Step 13 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * NOTE: ACH set up for the participant can be checked in part_banking table
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 14 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			/**
			 * Step 15 - Select the Delivery method 'Expedited mail'
			 * Delivery method selection should be switched to 'Expedited mail'.
			 */
			requestLoan.clickExpeditedMailDeliveryOption();
			
			/**
			 * Step 16 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			/**
			 * Step 17 - Verify the 'My loan summary' section It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step18 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			/**
			 * Step 19 - Hover the mouse on the 'INTEREST RATE' value 
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 20 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 21 - Hover the mouse on the 'FEES' value
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 22 -Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 * $9950 = $10000 - $50
			 */
			requestLoan.verifyCheckTotalAmount();
			
			
			/**
			 * Step 23 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();
			/**
			 * Step 24 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 25 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 26 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 27 - Click 'I agree & submit' after confirming the loan summary page info
			 * Participant should be displayed with the Loan Confirmation page
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 28 - Verify the 'Loan confirmation' page
			 * It should have
			 * i) Confirmation Number
			 * ii) Loan request Received date & time.
			 * iii) Delivery method selection.
			 * iv) Loan Details
			 */
			requestLoan.verifyLoanRequestRecievedSectionforExpeditedMail();
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
	 * This Test Case to verify General Purpose Loan via Regular Mail Delivery
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_27613_General_Purpose_Loan_Request_via_Regular_mail_delivery_method(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */
			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 10 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 11 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			/**
			 * Step - 12 Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed It should be "How
			 * would you like your check to be delivered?
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			
			
			/**
			 * Step 13 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * NOTE: ACH set up for the participant can be checked in part_banking table
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 14 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			
			/**
			 * Step 15 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			/**
			 * Step 16 - Verify the 'My loan summary' section It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */

			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value 
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 21 -Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 * $9950 = $10000 - $50
			 */
			requestLoan.verifyCheckTotalAmount();
			
			
			/**
			 * Step 22 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();
			/**
			 * Step 23 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 24 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 25 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 26 - Click 'I agree & submit' after confirming the loan summary page info
			 * Participant should be displayed with the Loan Confirmation page
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 27 - Verify the 'Loan confirmation' page
			 * It should have
			 * i) Confirmation Number
			 * ii) Loan request Received date & time.
			 * iii) Delivery method selection.
			 * iv) Loan Details
			 */
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
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
	 * This Test Case to verify General Purpose Loan via ACH Mail Delivery
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27630_General_Purpose_Loan_Request_via_ACH_delivery_method(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 10 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 11 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			/**
			 * Step - 12 Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed It should be "How
			 * would you like your check to be delivered?
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			
			
			/**
			 * Step 13 - Verify the 'Delivery Options' offered for ppt
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 * iii)  Electronically transfer funds directly to my bank account (ACH) $30
			 * Delivery up to 3 business days
			 */
			requestLoan.verifyMailDeliveryOptionsWithACH();
			
			/**
			 * Step 14 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			/**
			 * Step 15 - Select the Delivery method 'Expedited mail'
			 * Delivery method selection should be switched to 'Expedited mail'.
			 */
			requestLoan.clickElectronicallyTransferFundsACHOption();
			/**
			 * Step 16 - Verify that bank account drop down is displayed 
			 * As expected
			 */
			Web.waitForElement(requestLoan,"ACH ACCOUNT DROP DOWN");
			requestLoan.verifyWebElementIsDisplayed("ACH ACCOUNT DROP DOWN");
			/**
			 * Step 17 - Click on the the drop down
			 * All the associated Pre-noted ACH bank accounts of the participant should be displayed.
			 * Use below query to find the bank accounts associated
			 * Select * from part_banking where IND_ID='<ppt ind_id>' and PRENOTE_STATUS='PRO'
			 * 
			 */
			requestLoan.verifyACHAccountNumbersinDropDown(Stock.GetParameterValue("SSN"));
			
			/**
			 * Step 18 - Select the bank account from drop down
			 * Bank account should selected.(Loan amount should be transferred to this account via ACH)
			 */
			requestLoan.selectACHAccountNumber();
			
			 /** Step 19 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			/**
			 * Step 20 - Verify the 'My loan summary' section It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */

			requestLoan.verifyMyLoanSummarySection();
			
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			/**
			 * Step 21 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			/**
			 * Step 22 - Hover the mouse on the 'INTEREST RATE' value -
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 23 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 24 - Hover the mouse on the 'FEES' value 
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 25 -Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 * $9950 = $10000 - $50
			 */
			requestLoan.verifyCheckTotalAmount();
			
			
			/**
			 * Step 26 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();
			/**
			 * Step 27 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 28 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 29 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 30 - Click 'I agree & submit' after confirming the loan summary page info
			 * Participant should be displayed with the Loan Confirmation page
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 31 - Verify the 'Loan confirmation' page
			 * It should have
			 * i) Confirmation Number
			 * ii) Loan request Received date & time.
			 * iii) Delivery method selection.
			 * iv) Loan Details
			 */
			requestLoan.verifyLoanRequestRecievedSectionforACHDelivery();
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
	 * This Test Case to verify General Purpose Loan Request with recent address change
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27628_PreFilled_form_Delivery_for_General_Purpose_Loan_Request_by_participant_with_recent_address_change(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * Loans page is displayed with a banner containing below message
			 * A recent address change has been detected. Processing can continue 
			 * on 05/09/17 or first business day after unless form is returned with 
			 * address change section completed and notarized.
			 * Date value in the above verbiage:<MM/DD/YYYY(change of address date + 14 days)
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyAddressChangeBanner();
			/**
			 * Step 4 - Verify the participant should have 2 options  to proceed with loan request
			 * Participant should be offered with the below  2 options to proceed with loan request
			 * i) Submit for Processing
			 * Request can be future dated to the end of the hold period due to the address change.
			 *  Processing can continue on 05/13/17 or first business day after.
			 *  ii) Deliver Prefilled Form
			 *  Participant can get the form notarized.
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			/**
			 * Step 5- Select the radio button 'Deliver Pre-filled form'
			 * If this option is selected loan is processed after
			 *  the form is signed & submitted by the particpant
			 * 
			 */
			Web.clickOnElement(requestLoan, "INPUT EMAIL A FORM");
			/**
			 * Step - 6
			 * Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * PPT is navigated to What type of loan would you like? page / loan structure page with 
			 * all the correct values set for the test ppt
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * All the values are pulled correct from back end tables
			 * for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.verifyLoanRequestTypePage();
			
			String interestRate = requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType"));

			/**
			 * Step 7 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 8 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 9 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			
			/**
			 * Step 10 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			/**
			 * Step 11 - Verify the 'My Loan Summary' is as expected & click on 'Continue'
			 *  with Delivery method option selected as 'First-class mail'
			 *  Participant should be navigated to Address page
			 */
			requestLoan.verifyMyLoanSummarySection();
			
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 12 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Participant should be navigated to Address page
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 13 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 14 - Click 'I agree & submit' 
			 * Loan Confirmation page should be displayed with a message
			 * "A pre-filled form has been sent to your <email address>."
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed(" Your loan request has been received and a pre-filled form will be emailed to you shortly. "
					+ "To complete the process, you must sign and return the form to the address provided.");
			requestLoan.isTextFieldDisplayed("Print, sign, get notarized and return the pre-filled form that will be emailed to you at the end of this process");
			/**
			 * Step 15 - Verify the Pre-Filled form received
			 * It should have all the details selected/entered in the UI. - N/A
			 */
			
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
	 * This Test Case to verify General Purpose Loan Request with recent address change
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27627_Future_Dated_General_Purpose_Loan_Request_by_participant_with_recent_address_change(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * Loans page is displayed with a banner containing below message
			 * A recent address change has been detected. Processing can continue 
			 * on 05/09/17 or first business day after unless form is returned with 
			 * address change section completed and notarized.
			 * Date value in the above verbiage:<MM/DD/YYYY(change of address date + 14 days)
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyAddressChangeBanner();
			/**
			 * Step 4 - Verify the participant should have 2 options  to proceed with loan request
			 * Participant should be offered with the below  2 options to proceed with loan request
			 * i) Submit for Processing
			 * Request can be future dated to the end of the hold period due to the address change.
			 *  Processing can continue on 05/13/17 or first business day after.
			 *  ii) Deliver Prefilled Form
			 *  Participant can get the form notarized.
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			/**
			 * Step 5- Select the radio button 'Deliver Pre-filled form'
			 * If this option is selected loan is processed after
			 *  the form is signed & submitted by the particpant
			 * 
			 */
			Web.clickOnElement(requestLoan, "INPUT PROCESS LOAN AFTER HOLD");
			/**
			 * Step - 6
			 * Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * PPT is navigated to What type of loan would you like? page / loan structure page with 
			 * all the correct values set for the test ppt
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * All the values are pulled correct from back end tables
			 * for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.verifyLoanRequestTypePage();
			String interestRate = requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType"));

			/**
			 * Step 7 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 8 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 9 - Select Repayment Term option say for e.g. 48 months by
			 * clicking on the respective radio button present the Repayment
			 * Term row. Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			
			/**
			 * Step 10 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			/**
			 * Step 11 - Verify the 'My Loan Summary' is as expected & click on 'Continue'
			 *  with Delivery method option selected as 'First-class mail'
			 *  Participant should be navigated to Address page
			 */
			requestLoan.verifyMyLoanSummarySection();
			
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 12 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Participant should be navigated to Address page
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 13 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 14 - Click 'I agree & submit' 
			 * Loan Confirmation page should be displayed with confirmation number & other details.
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.verifyLoanRequestRecievedSectionForFutureDatedLoan();
			
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
	 * This Test Case to verify General Purpose Loan via ACH Mail Delivery
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27686_General_Purpose_Loan_Request_via_ACH_Non_Prenoted_Account_delivery_method(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing
			 * page Loan landing page displays the the highest amount available
			 * to borrow. It could be general purpose or primary residence.
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing
			 * pag PPT is navigated to What type of loan would you like? page /
			 * loan structure page with all the correct values set for the test
			 * ppt Loan purpose Maximum loan Minimum loan Repayment term
			 * Documentation required Interest rate Repayment Fees #Loans
			 * allowed Waiting period All the values are pulled correct from
			 * back end tables for available loan structures (Primary Residence,
			 * General purpose) buttons
			 */

			requestLoan.clickOnRequestANewLoan();

			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a General purpose loan' button
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 10 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");

			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 *Select Repayment Term option say for e.g. 36 
			 *months by clicking on the respective radio button present 
			 *the Repayment Term row. 
			 *Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			/**
			 * Step - 12 Verify the header of the 'Delivery Options' section
			 * It should be "How
			 * would you like your check to be delivered?
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			
			
			/**
			 * Step 13 - Verify the 'Delivery Options' offered for ppt
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 * iii)  Electronically transfer funds directly to my bank account (ACH) $30
			 * Delivery up to 3 business days
			 */
			requestLoan.verifyMailDeliveryOptionsWithACH();
			
			/**
			 * Step 14 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			/**
			 * Step 15 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & disable since any of the bank accounts are selected
			 */
			requestLoan.verifyContinueButtonIsEnabled(false);
		
			/**
			 * Step 16 - To proceed select any of the available ACH bank account(if any) &
			 *  click 'Continue' button.Verify the 'My loan summary' section
			 *  It must have the below details iin the form of a table
			 *  INTEREST RATE
			 *  FEES*
			 *  CHECK TOTAL
			 *  LOAN TOTAL
			 */
			requestLoan.clickElectronicallyTransferFundsACHOption();
			Web.waitForElement(requestLoan,"ACH ACCOUNT DROP DOWN");
			requestLoan.verifyWebElementIsDisplayed("ACH ACCOUNT DROP DOWN");
			
			
			requestLoan.selectACHAccountNumber();
			
			
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step 17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value 
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value 
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 21 -Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 * $9950 = $10000 - $50
			 */
			requestLoan.verifyCheckTotalAmount();
			
			
			/**
			 * Step 22 - Verify the LOAN TOTAL value - It must have the value of
			 * Loan Amount requested. LOAN TOTAL = CHECK TOTAL + FEES
			 */

			requestLoan.verifyLoanTotalAmount();
			/**
			 * Step 23 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 24 -Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");

			/**
			 * Step 25 - Verify the Loan Summary page 
			 * It should contain all the details of the loan, payment info, Fees, Delivery info
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 26 - Click 'I agree & submit' after confirming the loan summary page info
			 * Participant should be displayed with the Loan Confirmation page
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 27 - Verify the 'Loan confirmation' page
			 * It should have
			 * i) Confirmation Number
			 * ii) Loan request Received date & time.
			 * iii) Delivery method selection.
			 * iv) Loan Details
			 */
			requestLoan.verifyLoanRequestRecievedSectionforACHDelivery();
			
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
	 * This Test Case to verify General Purpose Loan quote page
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_29034_Loans_quote_page_validation(
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
			 * Step 1 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 2 - Navigate to Loans reason page & Select 'Request a General purpose loan' button 
			 * PPT is navigated to Loans quote page.
			 */
		
			requestLoan.clickOnRequestANewLoan();
		
			String interestRate = requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType"));

			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 3- PPT is navigated to Loans quote page.
			 * The section 'Select from a Repayment term...' 
			 * is loaded by showing options of repayment term associated with radio button selection.
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 4- Select the repayment radio button
			 * Upon repayment term selection the delivery section 
			 * and loan summary section was displayed with contents in a tabular format.
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 5 -Verify the heading above the delivery method box
			 * The heading above the delivery method box should be changed to :
			 * How would you like your funds to be delivered?
			 * Note: the word "check" is being replaced by "funds"
			 */
			
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
		
			/**
			 * Step 6 - Verify my loan summary section
			 * All the below details mentioned in loan summary page:
			 * a. Interest rate
			 * b. Fees
			 * c. Check Total
			 * d. Loan total
			 */

			requestLoan.verifyMyLoanSummarySection();

			/**
			 * Step 7 -Verify interest rate tooltip details.- Partially Automated
			 */

			requestLoan.verifyInterestRateInLoanSummaryTable();

			
			requestLoan.verifyOriginationFeeDisplayed();

		
			
			
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
	 * This Test Case to verify Back Button in General Purpose Loan flow
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_28043_Verify_Loans_Back_button_GP_loans_flow(
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
			 * Step 1  Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 2 - Verify contents displayed on loans landing page and compare this with CSAS and Legacy page
			 * Loans landing page displays amount available to borrow and summary section. It could be general purpose
			 *  or primary residence. Similar value is observed with CSAS and Legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 3 - Select 'Request a new loan' button on the loans landing page
			 * Loan type selection page with header 'What type of loan would you like?' was displayed
			 */

			requestLoan.clickOnRequestANewLoan();
			/**
			 * Step 4 -Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * PPT is navigated to What type of loan would you like? page / 
			 * loan structure page with all the correct values set for the test PPT:
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * All the values are pulled correct from back end tables
			 * for available loan structures (Primary Residence, General purpose) buttons  
			 */
			requestLoan.verifyLoanRequestTypePage();
			String interestRate = requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType"));

			
			/**
			 * Step 5 - Verify 'Back' button in loans reason (2nd) page
			 * 	Verified. 'Back' button is enabled
			 */
			requestLoan.verifyBackButtonIsEnabled(true);
			
			
			/**
			 * Step 6 - Click on 'Back' button
			 * PPT is navigated back to Loans landing page and displays all the correct information
			 */
			
			requestLoan.clickOnBackButton();
			Common.waitForProgressBar();
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			 String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loans Landing Page is displayed",
						"Loans Landing Page is displayed ", true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loans Landing Page is displayed",
						"Loans Landing Page is displayed ", true);
			
			/**
			 * Step 7 - Navigate to Loans quote page
			 * i.e. Loans landing page --> Loans reason page --> Loans quote page
			 * Loans quote / 'How much would you like to borrow?' 
			 * page was loaded along with amount field, continue button and 'back' button enabled
			 */
			
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyBackButtonIsEnabled(true);
			requestLoan.verifyContinueButtonIsEnabled(false);
			/**
			 * Step 8 - Click on 'Back' button
			 * PPT is navigated back to loans reason page.
			 */
			requestLoan.clickOnBackButton();
			Common.waitForProgressBar();
			if (Web.isWebElementDisplayed(requestLoan, "LOAN TYPE GENERAL", true))
				Reporter.logEvent(Status.PASS,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);
			/** 
			 * Step 9 - Choose loan type as 'General purpose' loan
			 *  and click on 'Request a General purpose loan' button
			 *  'How much would you like to borrow?'is loaded
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			/**
			 * Step 10 - Enter the amount & click 'Continue' button
			 * Repayment Term section in table format is loaded with 
			 * all required information along with radio button to choose
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			requestLoan.verifyDefaultRepamentTerm();
			
			/**
			 *Step 11 - Click on 'Back' button
			 *PPT is navigated back to loans reason page
			 */
			requestLoan.clickOnBackButton();
			Common.waitForProgressBar();
			if (Web.isWebElementDisplayed(requestLoan, "LOAN TYPE GENERAL", true))
				Reporter.logEvent(Status.PASS,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);
			/**
			 * Step - 12 Re-visit the 'How much would you like to borrow?' page
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			/**
			 * Step 13 - Select repayment term
			 * Delivery options & My Loan Summary section gets loaded in the loan quote page
			 * 
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			
			/**
			 * Step 14 - Click on 'Back' button 
			 * PPT is navigated back to loans reason page.
			 * 
			 */
			requestLoan.clickOnBackButton();
			Common.waitForProgressBar();
			if (Web.isWebElementDisplayed(requestLoan, "LOAN TYPE GENERAL", true))
				Reporter.logEvent(Status.PASS,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loans Reason Page is displayed",
						"Loans Reason Page is displayed ", true);
			
			/**
			 * Step 15 -Verify loans quote page display
			 */
			/**
			 * Step 16 -Again enter all the info for field amount available,
			 *  select repayment term and then delivery method and click on continue
			 *  Loans Address page was displayed
			 */
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Common.waitForProgressBar();
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			requestLoan.clickContinueButton();
			
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			/**
			 * Step 17 - Verify the 'Back' button
			 * Back button is enabled and clickable
			 */
			requestLoan.verifyBackButtonIsEnabled(true);
			/**
			 * Step 18 - Click on 'Back' button
			 * Participant should be navigated back to Loans quote page.
			 * All the previously entered data are available to verify
			 */
			requestLoan.clickOnBackButton();
			requestLoan
			.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan
			.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.verifyRegularMailSelectedAsDefault();
			/**
			 * Step 19 -Navigate to 'Loan Summary' page & verify the 'Back' button
			 *Back button is available and enabled
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			requestLoan.verifyBackButtonIsEnabled(true);

			/**
			 * Step 20 - click on back button
			 * PPT is navigated back to address page by showing default address and default page display. 
			 */
			requestLoan.clickOnBackButton();
			
			Common.waitForProgressBar();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");
			
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
	 * This Test Case to verify General Purpose Loan when Proacted
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27682_General_Purpose_Loan_ProActive_Notification_Opted(
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
			 * Step 1 to 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * PPT is navigated to What type of loan would you like? page 
			 * / loan structure page with all the correct values set for the test ppt
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * All the values are pulled correct from back end tables
			 * for available loan structures (Primary Residence, General purpose) buttons 
			 * 
			 */
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			

			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));
			
			/**
			 * Step 5 -Click on 'Request a General Purpose loan' button.
			 *  Participant should be navigated to 'How much would you like to borrow?' page.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("How much would you like to borrow?");
			
			/**
			 * Step 6 - Enter amount in entry box within the amount available
			 * to borrow($25000) and click on 'Continue'. Request loan for
			 * $10000 Validate that Repayment Term options section should be
			 * displayed below the 'How much would you like to borrow' section
			 * 
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");

			/**
			 * Step 7 - Select Repayment Term option say for e.g. 24 
			 *months by clicking on the respective radio button present 
			 *the Repayment Term row. 
			 *Validate that upon term selection Delivery options &
			 * 'My Loan Summary section should be displayed
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			
			
			/**
			 * Step 8 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be 
			 * i) First-class mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 9 - Verify the 'My Loan Summary' is as expected & 
			 * click on 'Continue' with Delivery method option selected as 'First-class mail
			 * By default 'First-class mail' delivery method should be pre-selected
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			requestLoan.verifyMyLoanSummarySection();
			
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 10 -In the Address page, select the check box for both the fields
			 * i) Update me by email at:
			 * ii) Update me by text message at:
			 * Loan Summary page should be displayed
			 */
			/**
			 * Step 11 - Duplicate
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			
			/**
			 * Step 12 - Click on 'Continue' button after performing either STEP 10 or 11
			 * Participant should be navigated to Loan Summary page
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			/**
			 * Step 13 - Verify the Loan Summary page 
			 * Validate all the information is correct as per the entries in the previous pages
			 */
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 15 - Click 'I agree & submit' 
			 * Loan Confirmation page should be displayed.
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			
			String confirmationNo=requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			/**
			 * Step 15 - After submit verify the EVENT_CONTACT_INFO table 
			 * use query:select * from EVENT_CONTACT_INFO order by EV_ID desc
			 * Event should be populated in the table with the  preferred/selected
			 *  notification in step 12 or 13 details
			 */
			String expected="EMAIL";
					
			String actual=Common.getLoanNotificationType(confirmationNo);
			
			if(expected.equalsIgnoreCase(actual))
				Reporter.logEvent(Status.PASS,
						"Verify Notification Type is Updated in DB",
						"Notification Type is updated in DB as Expected\nNotificationType:"+expected, false);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Notification Type is Updated in DB",
						"Notification Type is Not updated in DB as Expected\nExpected:"+expected+"\nActual:"+actual,false);
				
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
	 * This Test Case to verify Loan Reason Page
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_28964_Loans_Reason_page_validation(
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
			 * Step 1 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 2 -Click on 'Request a loan' button in loans landing page
			 * Loans reasons page was displayed
			 */
			/**
			 * Step 3 - Select 'Request a new loan' button on the loans landing page
			 * 
			 * Loan type selection page with header 'What type of loan would you like?' was displayed
			 * 
			 * Step 2 and 3 are same
			 */
		
			requestLoan.clickOnRequestANewLoan();

			requestLoan.isTextFieldDisplayed("What type of loan would you like to request?");
			/**
			 * Step 4 - Verify all correct values are displayed for the PPT 
			 * in the loan type selection page for available loan structures such as GR , PR:
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 */
			requestLoan.verifyLoanRequestTypePage();
			
			
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
	 * This Test Case to verify Principle Residence Loan Confirmation Page
	 * 
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_28010_Loans_Screen_6_PR_loan_confirmation_page(
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
			 * Step 1 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();

			/**
			 * Step 2 - Verify contents displayed on loans landing page and
			 *  compare this with CSAS and Legacy page.
			 *  Loans landing page displays amount available to borrow and summary section.
			 *   It could be general purpose or primary residence. Similar value is observed with CSAS and Legacy PW
			 *  
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 3 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 4 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 5 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 6 - Verify the disclaimer displayed at the bottom of the
			 * page on loans landing page Validate the disclaimer displayed at
			 * the bottom of the page is displayed per latest design reqs on
			 * loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();

			/**
			 * Step 7 - Click on'Request a new loan' button on the loans landing
			 * Loan type selection page with header 'What type of loan would you like?' was displayed
			 */

			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.isTextFieldDisplayed("What type of loan would you like to request?");
			/**
			 * Step 8 Verify all correct values are displayed for the PPT in the loan type 
			 * selection page for available loan structures such as GR , PR:
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * Verified with CSAS, Legacy PW and backend tables.
			 *  correct values are getting displayed for the said headers along with 2 clickable buttons as 
			 *  a. Request a general purpose loan
			 *  b. Request a primary resident loan
			 */
			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));
			
			

			/**
			 * Step 10 - Select 'Request a primary residence loan' button
			 * PPT is navigated to Primary Residence loan requirements with the following 
			 * This type of loan is only for purchasing or building your principal residence; not renovating
			 * or refinancing your current home.
			 * At the end of this process, a pre-filled form will be emailed to you. 
			 * You must sign and return to us at the address provided, with the following documentation,
			 *  to complete this loan request.
			 *  and with billeted documents and 
			 *  the following verbiage at the bottom of the page
			 *  I certify this request is for my principal residence and will 
			 *  provide documentation upon request as
			 *  detailed in the loan policy.
			 *  with 'continue' and 'back' buttons
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan
					.isTextFieldDisplayed("Principal residence loan requirements");
			requestLoan.verifyPrincipalResidenceLoanDisclaimer();
			requestLoan.clickContinueButton();
			

			/**
			 * Step 11 - Enter amount in entry box Validate only proper values
			 * (numeric/decimal)are accepted into this field Validate 'Continue'
			 * button is enabled only when the min and max amt values are met
			 * else display the min,max amt error in red text if the criteria is
			 * not met.
			 */
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			
			requestLoan
					.verifyWebElementIsDisplayed("Link How Is This Calculated");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();

			/**
			 * Step 12 - Select the repayment radio button
			 * Upon repayment term selection the delivery section and loan summary
			 *  section was displayed with contents in a tabular format.
			 */

		
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			requestLoan.verifyDefaultRepamentTerm();

			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan
					.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.verifyMyLoanSummarySection();
			requestLoan.verifyLoanTotalAmount();
			requestLoan.setCheckTotal(requestLoan
					.getCheckTotalFromSummaryTable());
			
			/**
			 * Step 13 - Verify the loan summary values, choose delivery radio button and click on continue button
			 * The address page was displayed with a header "Please confirm your address"
			 */

			requestLoan.verifyRegularMailSelectedAsDefault();

			requestLoan.clickContinueButton();

			requestLoan
					.verifyWebElementIsDisplayed("ProActive Notification Screen");

			requestLoan
					.isTextFieldDisplayed("Sign up for updates on your loan process");
			
			

			/**
			 * Step 14 - Verify the contact information/pls confirm your address page and click on continue.
			 * Loan summary page was displayed
			 * 
			 */
			requestLoan
			.isTextFieldDisplayed("Sign up for updates on your loan process");
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 15 -Verify Loan summary page with all the correct information/ values selected in all the previous
			 *  pages with loan provision link and click on "I agree & submit" buttons
			 *  Loan request received'/ confirmation page was displayed with all the steps in progress as below:
			 *  1. Print, sign and return the pre-filled form that will be emailed you at the end of the process
			 *  2. Request form received
			 *  3. Form Review Complete
			 *  4. Check sent by expedited mail
			 */
			requestLoan.verifyLoanSummarypage();
		
			if (Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
					true)) {
				Reporter.logEvent(Status.PASS,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Not Displayed", true);
			}
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			/**
			 * Step 16 - 25- Verify the 1) 'Print, sign and return the pre-filled form that will 
			 * be emailed you at the end of the process' section timestamp
			 * Conf no. should be displayed 
			 * timestamp format is displayed as MM/DD/YYYY
			 */

			
			requestLoan.verifyPrincipalResidenceLoanRequestRecievedSectionForRegularMail();
			/**
			 * Step 26 - Verify 'Loan provisions' section content -N/A
			 */
			
			/**
			 * Step 27 - Verify the loan provision hyperlink status -N/A
			 * 
			 */

			/**
			 * Step 28 - Verify presence of back button
			 * Verified. Back button is not required in confirmation page.
			 */
			requestLoan.verifyBackButtonIsDisplayed(false);
			
			/**
			 * Step 29 - Validate the print button on all the screens including 'Loan provisions' modal
			 * Able to print functionality works with no issues for all the loans pages selected
			 * 
			 */
			requestLoan.verifyWebElementIsDisplayed("PRINT LINK");
			/**
			 * Step 30 -Validate tooltips on all the applicable pages
			 * Tool tip functions correct per design/requirements     -N/A
			 */
			

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
	 * This Test Case to verify Principal Residence Loan Repayment Page
	 * 
	 * @param itr
	 * @param testdata
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27502_Principal_Residence_Loan_Repayment_Term_Page_Verification(
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
			 * Step 1  - 3 Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();


			/**
			 * Step 4 - Verify contents displayed on loans landing page and
			 *  compare this with CSAS and Legacy page.
			 *  Loans landing page displays amount available to borrow and summary section.
			 *   It could be general purpose or primary residence. Similar value is observed with CSAS and Legacy PW
			 *  
			 */
			requestLoan.verifyLoansLandingPage();

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6 Verify the Number of active loans in all plans below
			 * Summary section Validate all the the number of loans available in
			 * multiple plans for the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan
			 * eligible for a loan
			 */

			// TODO

			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate the number of loans available for the plan is displayed
			 * correct with the number of loans allowed for each loan structure
			 * is also displayed if the plan is setup for
			 */

			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));

			/**
			 * Step 8 - Verify the disclaimer displayed at the bottom of the
			 * page on loans landing page Validate the disclaimer displayed at
			 * the bottom of the page is displayed per latest design reqs on
			 * loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();

			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing
			 * Loan type selection page with header 'What type of loan would you like?' was displayed
			 *  Verify all correct values are displayed for the PPT in the loan type 
			 * selection page for available loan structures such as GR , PR:
			 * Loan purpose
			 * Maximum loan
			 * Minimum loan
			 * Repayment term
			 * Documentation required
			 * Interest rate
			 * Repayment
			 * Fees
			 * #Loans allowed
			 * Waiting period
			 * Verified with CSAS, Legacy PW and backend tables.
			 *  correct values are getting displayed for the said headers along with 2 clickable buttons as 
			 *  a. Request a general purpose loan
			 *  b. Request a primary resident loan
			 */

			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.isTextFieldDisplayed("What type of loan would you like to request?");
			
			requestLoan.verifyLoanRequestTypePage();
			
			requestLoan.setInterestRate(requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType")));
			
			/**
			 * Step 10 - Select 'Request a Principal Residence loan' button
			 * Ppt must be navigated to 'Principal Residence Loan Requirements' page
			 * 
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));

			requestLoan
			.isTextFieldDisplayed("Principal residence loan requirements");
		
			
			/**
			 * Step 11 -Verify the 'Principal Residence Loan Requirements' page.
			 * Page should have below content:
			 * Principal residence loan requirements(IN BOLD)
			 * This type of loan is only for purchasing or building your principal residence;
			 *  not renovating or refinancing you current home.(IN BOLD)
			 *  At the end of this process, a pre-filled form will be emailed to you.
			 *   you must be sign and return to the address provided,along with the following documentation,
			 *    to complete this loan request.
			 *    Documentation type 1(TBD)
			 *    Documentation type 2(TBD)
			 *    Documentation type 3(TBD)
			 *    'Back' & 'Continue' buttons should be enabled.
			 * 
			 */
			requestLoan.verifyPrincipalResidenceLoanDisclaimer();
			/**
			 * Step 12,13;
			 * PPT must be navigated to  
			 * How much would you like to borrow page with
			 *  the max and min amount balance for Principal Residence loan and a 'Enter amount' 
			 *  text box to enter valid amt values with a 'Continue' and 'Back' buttons
			 *  'How is this calculated?' link displaying the calculation in the hover help from design team.
			 */
			requestLoan.clickContinueButton();
			
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			requestLoan
			.verifyWebElementIsDisplayed("Link How Is This Calculated");
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			
			/**
			 * Step 14 - Hit 'Continue' button 
			 * Validate that Repayment Term options section
			 *  should be displayed below the 'How much would you like to borrow' section.
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			/**
			 * Step 15 -Verify the Repayment Term options/values section verbiage.
			 * Verbiage should be "Select from a repayment term option below or enter your own term"
			 */
			requestLoan
					.isTextFieldDisplayed("Select from a repayment term option below or");

			/**
			 * Step 16 - Verify "enter your own loan term" at the end of the verbiage.
			 * It must be a link & text should be in blue color.
			 */
			requestLoan.isTextFieldDisplayed("enter your own term");
			requestLoan.verifyWebElementIsDisplayed("Link Enter Your Own Term");
			/**
			 * Step 17-19 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT
			 * ii) <Repayment Frequency Value>(Dynamic value)
			 * iii) REPAYMENT TERM
			 * Five 'Repayment Term' options should be offered to the participant with 
			 * below tenures by default in ascending order
			 * 12 months
			 * 24 months
			 * 36 months
			 * 48 months
			 * 60 months
			 * 
			 */
			requestLoan.isTextFieldDisplayed("Select");
			requestLoan.isTextFieldDisplayed(" bi-weekly");
			requestLoan.isTextFieldDisplayed("Repayment Term");
			requestLoan.verifySelectColumnForPrincipalResidenceLoanTerm();
			requestLoan.verifyDefaultRepamentTermForPrinciplaResidence();
			/**
			 * Step 20 - Now re-enter/change the amount in the 'ENTER AMOUNT' field.
			 * Button label should change form 'Continue' to 'Update
			 */
			requestLoan.EnterLoanAmount("200");
			requestLoan.verifyWebElementIsDisplayed("BUTTON UPDATE");
			/**
			 * Step 21 - Now, click on 'UPDATE' button
			 */
			Web.clickOnElement(requestLoan, "BUTTON UPDATE");
			//Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			
			

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
	 * This Test Case to verify Happy Path Primary/Principal Residence Loans
	 * @param itr
	 * @param testdata
	 * author: bbndsh
	 */
	
	@Test(dataProvider = "setData")
	public void DDTC_27416_Happy_Path_Initiate_Primary_Residence_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 *Navigate to My Accounts -> Loans & Withdrawals -> Loans 
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page 
			 * Loan landing page displays the the highest amount available to borrow.
			 * It could be general purpose or 
			 * primary residence. compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			
			
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6
			 * Verify the Number of active loans in all plans below Summary section
			 * Validate all the the number of loans available in multiple plans for 
			 * the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan eligible for a loan
			 */
			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate  the number of loans available for the plan is displayed 
			 * correct with the number of loans allowed 
			 * for each loan structure is also displayed if the plan is setup for
			 */
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 8 - Verify the disclaimer displayed at the bottom of the page on loans landing page
			 * Validate the disclaimer displayed at the bottom of the page 
			 * is displayed per latest design reqs on loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();
			
			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestTypePage();
						
			/**
			 * Step 10 - Select 'Request a Primary loan' button PPT is navigated to Primary Residence loan page/
			 * This type of loan is only for purchasing or building your principal residence;
			 * not renovating or refinancing your current home. 
			 * At the end of this process, a pre-filled form will be emailed to you.
			 * You must sign and return to us at the address provided, with the following documentation, 
			 * to complete this loan request. and with billeted documents and the following verbiage at the bottom of the page 
			 * I certify this request is for my principal residence and will provide documentation upon request as 
			 * detailed in the loan policy. with 'continue' and 'back' buttons
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");
			
			requestLoan.verifyPrincipalResidenceLoanDisclaimer();
			
			/**
			 * Step 11 - Select 'Continue' button
			 * PPT is navigated to  Primary Residence loan requirements page
			 * This type of loan is only for purchasing or building your principal residence;
			 * not renovating or refinancing your current home.
			 * At the end of this process, a pre-filled for will be emailed to you.
			 * You must be sign and return to the address provided to complete this loan request.
			 * I certify this request is for my principal residence and will provide documentation upon request as detailed in the loan policy.
			 * and 'I certify & Continue' and 'Back' button
			 * 
			 */
			
			requestLoan.clickContinueButton();
			
			/**
			 * Step 12 - Enter amount in entry box 
			 * Validate only proper values (numeric/decimal)are accepted into this field
			 * Validate 'Continue' button is enabled only when the min and max amt values are met else
			 *  display the min,max amt error in red text if the criteria is not met.
			 */
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));			
			requestLoan.verifyWebElementIsDisplayed("Link How Is This Calculated");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/** 
			 * Step 13 - Verify the repayment term options displayed for the amt enter in previous step
			 * validate  repayment values are displayed correct per the loan amount entered.
			 * if diff amt is entered then recalculate and re-render the different loan term table.
			 * compare the values and term with csas
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			requestLoan.verifyDefaultRepyamentTermPrincipalResidence();
			
			/**
			 * Step 14 - select the repayment radio button
			 * PPT able to select or custom enter the loan term and continue with no issues.
			 * validate the sort order for the terms displayed
			 * Note: Upon term selection show the delivery and loan summary table 
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 15 - verify How would you like your check to be delivered? page
			 * PPT able to select one of the options from the following 
			 * 'First clsas mail'- Free ,
			 * 'Expedite mail' - $ 25, 
			 * 'Electronically transfer funds directly to my bank account (ACH' - $ 30 
			 * Note: mail option is always Defaulted to First-class mail
			 * Only show the available ACH accounts only if the ACH option is selected
			 */
			
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.verifyMailDeliveryOptionsWithACH();
			
			
			/**
			 * Step 16 - Select First class mail
			 * PPT is navigated to My loan summary page 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			

			/**
			 * Step 17 - Verify My loan summary page
			 * Validated that the interest rate, fees, check total and loan total details are displayed correct 
			 * per the selections ppt has made in the above steps with a continue and back buttons
			 * Note: Interest rate and fees should have tooltips explaining details.
			 * however compare the values to CSAS/ISIS forms
			 */
			requestLoan.verifyMyLoanSummarySection();
			
			requestLoan.validateToolTipForInterestRate();
			requestLoan.validateToolTipForFee();
			/**
			 * Step 18 - Select Continue button on the mailing options page
			 * PPT is navigated to 
			 */
			requestLoan.clickContinueButton();
			
			/**
			 * Step 19 - Verify the contact information/pls confirm your address page
			 * PPT correct primary address is displayed with no issues 
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			
			
			/**
			 * Step 20 - Verify 'Keep me updated on my loans status'section
			 * PPT able to select the following check boxes
			 * 'Update me by email at:
			 * 'Update me by text message at:
			 * 'and enter valid email address and or ph numbers US and international options with no issues
			 */
			
			
			
			/**
			 * Step 21 - Enter valid info and hit continue button
			 * PPT is navigated to Loan summary page with all the correct information/
			 * values selected in all the previous pages with loan provision link 
			 * and I agree & submit and back buttons
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanSummarypage();
			
			
			/**
			 * Step 22 - Select 'I agree & submit' button
			 * PPT is navigated to 'Loan request received page with all the steps in progress
			 */
			
			if (Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
					true)) {
				Reporter.logEvent(Status.PASS,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree and Submit' Button is Displayed",
						" 'I Agree and Submit' Button is Not Displayed", true);
			}
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			
			/**
			 * Step 23 - Validate the confirmation section
			 * PPT is displayed with all the correct info entered and term and fee calculation from the previous pages 
			 */
			
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			requestLoan.verifyPrincipalResidenceLoanRequestRecievedSectionForRegularMail();
			
			/**
			 * Step 24 - Validate the pending loans screen
			 * PPT able to see the above initiated loan as pending on the loans landing page
			 */
			
			//TODO
			
			/**
			 * Step 25 - Validate back buttons on all the loan pages
			 */
			
			//TODO
			
			/**
			 * Step 26 - Validate caching on all loan screens
			 */
			
			//TODO
			
			/**
			 * Step 27 - validate the print button on all the screens including 'Loan provisions' modal
			 * Able to print functionality works with no issues for all the loans pages selected
			 */
			
			requestLoan.verifyWebElementIsDisplayed("PRINT LINK");
			
			/**
			 * Step 28 - Validate tootips on all the applicable pages
			 * Tool tip functions correct per design/requirements
			 */
			
			//TODO
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	 * Test Case- DDTC-27610
	 * Loans - Princiapal Residence Loan request
	 * Regular mail Delivery Method
	 * @author bbndsh
	 */

	
	@Test(dataProvider = "setData")
	public void DDTC_27610_Principal_Residence_Loan_Request_through_Regular_mail_delivery_method(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 *Navigate to My Accounts -> Loans & Withdrawals -> Loans 
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page 
			 * Loan landing page displays the the highest amount available to borrow.
			 * It could be general purpose or 
			 * primary residence. compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6
			 * Verify the Number of active loans in all plans below Summary section
			 * Validate all the the number of loans available in multiple plans for 
			 * the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan eligible for a loan
			 */
			
			//TODO
			
			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate  the number of loans available for the plan is displayed 
			 * correct with the number of loans allowed 
			 * for each loan structure is also displayed if the plan is setup for
			 */
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			
			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestTypePage();
			
			/**
			 * Step 9 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	
			
			/**
			 * Step 10 - Select 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 11 - Enter amount in entry box 
			 * Click on 'Continue' button
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));				
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/**
			 * Step 12 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 13 - Verify the header of the 'Delivery Options' section 
			 * It should be "How would you like your *check* to be delivered?"
			 */
			
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("How would you like your funds to be delivered?");
			
			/**
			 * Step 14 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be i) Regular mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 15 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'Regular mail' delivery method should be pre-selected 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();

			/**
			 * Step 16 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 17 to Step 23 -Verify 'My Loan Summary' section  
			 * 
			 * verify How would you like your check to be delivered? page
			 * It should be i) Regular mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 * 
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			requestLoan.verifyMyLoanSummarySection();
			
			/**
			 * Step 24 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
		
			requestLoan.clickContinueButton();
			
			/**
			 * Step 25 - Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 26 - Verify My loan summary page
			 * It should contain all the details of the loan, payment info, Fees, 
			 * Delivery info which was provided/opted in the earlier screens by the participant
			 */
			
			requestLoan.verifyLoanSummarypage();
			/**
			 * Step 18 to Step 26 - 
			 */			
						
			/**
			 * Step 27 - Click 'I agree & submit' button
			 * Participant should be able to submit the loan request successfully 
			 * Loan Confirmation page should be displayed.
			 */
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			/**
			 * Step 28 - Verify Loan Confirmation Page
			 * A pre-filled form has been sent to your email address <email Id> 
			 */
			
			requestLoan.verifyPrincipalResidenceLoanRequestRecievedSectionForRegularMail();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_27612_Principal_Residence_Loan_Request_via_Expedited_mail_delivery_method(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 *Navigate to My Accounts -> Loans & Withdrawals -> Loans 
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page 
			 * Loan landing page displays the the highest amount available to borrow.
			 * It could be general purpose or  primary residence. 
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6
			 * Verify the Number of active loans in all plans below Summary section
			 * Validate all the the number of loans available in multiple plans for 
			 * the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan eligible for a loan
			 */
			
			//TODO
			
			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Validate  the number of loans available for the plan is displayed 
			 * correct with the number of loans allowed 
			 * for each loan structure is also displayed if the plan is setup for
			 */
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			
			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestTypePage();

			/**
			 * Step 9 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	
			
			/**
			 * Step 10 - Click 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 11 - Enter amount in entry box 
			 * Click on 'Continue' button
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/**
			 * Step 12 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			
			
			/**
			 * Step 13 - Verify the header of the 'Delivery Options' section 
			 * It should be "How would you like your *check* to be delivered?"
			 */
			
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			
			/**
			 * Step 14 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be i) Regular mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 15 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'Regular mail' delivery method should be pre-selected 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();

			/**
			 * Step - 16
			 * Select the Delivery Method as 'Expedited mail'
			 */
			
			requestLoan.clickExpeditedMailDeliveryOption();
			/**
			 * Step 17 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 18 to Step 24 -Verify 'My Loan Summary' section  
			 *  
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySection();
						
			
			/**
			 * Step 25 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
		
			requestLoan.clickContinueButton();
			
			/**
			 * Step 26 - Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 27 - Verify My loan summary page
			 * It should contain all the details of the loan, payment info, Fees, 
			 * Delivery info which was provided/opted in the earlier screens by the participant
			 */
			
			requestLoan.verifyLoanSummarypage();
			
									
			/**
			 * Step 28 - Click 'I agree & submit' button
			 * Participant should be able to submit the loan request successfully 
			 * Loan Confirmation page should be displayed.
			 */
			
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			/**
			 * Step 29 - Verify Loan Confirmation Page
			 * A pre-filled form has been sent to your email address <email Id> 
			 */
			
			requestLoan.verifyLoanRequestRecievedSectionforExpeditedMail();
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_27626_Loans_Pre_Filled_form_Delivery_for_Principal_Residence_Loan_Request_by_participant_with_recent_address_change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 * Navigate to My Accounts -> Loans & Withdrawals -> Loans 
			 * Loans page must be displayed with a banner containing below message
			 * A recent address change has been detected. Processing can continue on 05/09/17 or first business day after unless form is returned with address change section completed and notarized.
			 * Date value in the above verbiage: <MM/DD/YYYY(change of address date + 14 days)
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			Web.clickOnElement(requestLoan, "Button Request A New Loan");
			Common.waitForProgressBar();
			
			
			/**
			 * Step 4 - Verify the participant should have 2 options  to proceed with loan request
			 * Participant should be offered with the below  2 options to proceed with loan request
			 * i) Submit for Processing: Request can be future dated to the end of the hold period due to the address change.
			 * Processing can continue on + 14 days or first business day after.
			 * ii) Deliver Prefilled Form: Participant can get the form notarized.

			 */
			requestLoan.verifyLoanReasons();
			
			/**
			 * Step 5 - Select the radio button 'Deliver Pre-filled form 
			 * If this option is selected loan must be processed after the form is signed & 
			 * submitted by the participant 
			 */
			requestLoan.clickLoanReasonOptionPreFilled();
			
			/**
			 * Step 6 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 */
						
			
			/**
			 * Step 7 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	
			
			/**
			 * Step 8 - Click 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 9 - Enter amount in entry box 
			 * Click on 'Continue' button
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/**
			 * Step 10 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 11 - Verify the header of the 'Delivery Options' section 
			 * It should be i) First-class mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 */
						
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 12 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'Regular mail' delivery method should be pre-selected 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.verifyWebElementIsDisplayed("Button Continue");
			
			/**
			 * Step 12 -Verify 'My Loan Summary' section  
			 *  click on 'Continue' with Delivery method option selected as 'First-class mail'
			 * 
			 */
			
			requestLoan.verifyMyLoanSummarySection();
			requestLoan.clickContinueButton();
			
			
			/**
			 * Step 13 - Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 14 - Verify My loan summary page
			 * All the information must be correct as per the entries/selections in the previous pages.
			 */
			
			requestLoan.verifyLoanSummarypage();
			
									
			/**
			 * Step 15 - Click 'I agree & submit' button 
			 * Loan Confirmation page should be displayed with a message
			 * "A pre-filled form has been sent to your <email address>."
			 */
			
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			/**
			 * Step 16 - Verify Pre-Filled form received
			 * It should have all the values selected in the UI. 
			 */
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_27625_Loans_Future_Dated_Principal_Residence_Loan_Request_by_participant_with_recent_address_change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 * Navigate to My Accounts -> Loans & Withdrawals -> Loans 
			 * Loans page must be displayed with a banner containing below message
			 * A recent address change has been detected. Processing can continue on 05/09/17 or first business day after unless form is returned with address change section completed and notarized.
			 * Date value in the above verbiage: <MM/DD/YYYY(change of address date + 14 days)
			 * 
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			
			/**
			 * Step 4 - Verify the participant should have 2 options  to proceed with loan request
			 * Participant should be offered with the below  2 options to proceed with loan request
			 * i) Submit for Processing: Request can be future dated to the end of the hold period due to the address change.
			 * Processing can continue on + 14 days or first business day after.
			 * ii) Deliver Prefilled Form: Participant can get the form notarized.

			 */
			requestLoan.verifyLoanReasons();
			
			/**
			 * Step 5 - Select the radio button Future Dated 
			 * If this option is selected loan must be processed after the form is signed & 
			 * submitted by the participant 
			 */
			requestLoan.clickLoanReasonOptionFutureDate();
			
			/**
			 * Step 6 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 */
			requestLoan.verifyLoansLandingPage();			
			
			/**
			 * Step 7 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	
			
			/**
			 * Step 8 - Click 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 9 - Enter amount in entry box 
			 * Click on 'Continue' button
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/**
			 * Step 10 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 11 - Verify the header of the 'Delivery Options' section 
			 * It should be i) First-class mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 */
						
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
						
			
			/**
			 * Step 12 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'Regular mail' delivery method should be pre-selected 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			
			/**
			 * Step 12 -Verify 'My Loan Summary' section  
			 *  click on 'Continue' with Delivery method option selected as 'First-class mail'
			 * 
			 */
		
			requestLoan.verifyMyLoanSummarySection();
			
			/**
			 * Step 13 - Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */
			
			requestLoan.isTextFieldDisplayed("My loan summary");
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 14 - Verify My loan summary page
			 * All the information must be correct as per the entries/selections in the previous pages.
			 */
			
			requestLoan.verifyLoanSummarypage();
			
									
			/**
			 * Step 15 - Click 'I agree & submit' button 
			 * Loan Confirmation page should be displayed with a message
			 * "A pre-filled form has been sent to your <email address>."
			 */
			
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			/**
			 * Step 16 - Verify Pre-Filled form received
			 * It should have all the values selected in the UI. 
			 */
			
			requestLoan.verifyLoanRequestRecievedSectionForFutureDatedLoan();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_27629_Loans_Principal_Residence_Loan_Request_via_ACH_delivery_method(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 * Navigate to My Accounts -> Loans & Withdrawals -> Loans			
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			
			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 * Loan landing page must be displayed with the  highest amount available to borrow.
			 * It could be general purpose or primary residence.
			 * For reference compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			/**
			 * Step 6
			 * Verify the Number of active loans in all plans below Summary section
			 * Validate all the the number of loans available in multiple plans for 
			 * the selected test ppt are displayed correct
			 * This option is displayed only when ppt have more than one plan eligible for a loan
			 */
			
			//TODO
			
			/**
			 * Step 7 - Verify Number of loans allowed below Summary section
			 * Number of loans available for the plan ismust be displayed correctly with
			 * the number of loans allowed for each loan structure is also displayed.
			 */
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			
			/**
			 * Step 8 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));

			/**
			 * Step 9 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	

			/**
			 * Step 10 - Click 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 11 - Enter amount in entry box 
			 * Click on 'Continue' button
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			/**
			 * Step 12 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 13 - Verify the header of the 'Delivery Options' section 
			 * It should be "How would you like your *check* to be delivered?"
			 */
			
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			
			/**
			 * Step 14 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 * It should be i) Regular mail Delivery up to 5 business days Free
			 * ii) Expedited mail Delivery up to 5 business days $25
			 * iii)  Electronically transfer funds directly to my bank account (ACH) $30 Delivery up to 3 business days
			 */
			requestLoan.verifyMailDeliveryOptionsWithACH();
			
			/**
			 * Step 15 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'First-class mail' delivery method should be pre-selected 
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();

			/**
			 * Step 16 - Select the Delivery Method as 'Electronically transfer funds directly to my bank account (ACH)'
			 * Delivery method selection should be switched to 'Electronically transfer funds directly to my bank account (ACH)'
			 * Continue button should not be displayed yet.
			 */
			
			requestLoan.clickElectronicallyTransferFundsACHOption();
			//TODO click on continue and verify error message
			requestLoan.verifyContinueButtonIsEnabled(false);
			
			/**
			 * Step 17 - Verify that bank account drop down is displayed
			 * As expected 
			 */
			/**
			 * Step 18 - Click on the Drop down
			 * All the associated pre-noted ACH bank accounts of the participant should be displayed.
			 */
			
			requestLoan.verifyACHAccountNumbersinDropDown(Stock.GetParameterValue("SSN"));

			/**
			 * Step 19 - Select bank account from drop down
			 * Bank account should selected.(Loan amount should be transferred to this account via ACH)
			 */
			
			requestLoan.selectACHAccountNumber();
			
			/**
			 * Step 20 - Verify the Continue button at the bottom of the page
			 * It must be present & enabled
			 */
			
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 21 - Verify 'My loann summary' section
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			
			requestLoan.verifyMyLoanSummarySection();
			
			/**
			 * Step 22 - Verify Interest Rate
			 * It should be same value as on 'Request a loan' page
			 */
		
			requestLoan.verifyInterestRateInLoanSummaryTable();
			
			/**
			 * Step 23 - Hover the mouse on the 'INTEREST RATE' value
			 * Tool tip should be displayed explaining the details for INTEREST RATE having below components
			 * Rate Type: Fixed
			 */
			requestLoan.validateToolTipForInterestRate();
			/**
			 * Step 24 - Verify the FEES* value
			 * It must be the sum of 'Origination Fee'(Assumption $25), Stamp Tax(Assumption $25) and ACH Delivery Fee($30) which is $80
			 */
			 
			requestLoan.getOriginationFeeFromLoanSummaryTable();
			
			/**
			 * Step 25 - Hover the mouse on the 'FEES' value
			 * Tool tip should be displayed explaining the details of the different Fee components.
			 * Note: If there is only one fee, tool tip should not be displayed
			 */
			requestLoan.validateToolTipForFee();
			/**
			 * Step 26 - Verify the CHECK TOTAL value
			 * It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 */
			
			requestLoan.verifyCheckTotalAmount();
			
			/**
			 * Step 27 - Verify the LOAN TOTAL value
			 * It must have the value of Loan Amount requested.
			 */
			
			requestLoan.verifyLoanTotalAmount();
			
			/**
			 * Step 28 - Click on 'Continue' button
			 * Address confirmation page should be displayed
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 29 - Verify the Mailing address, email address, phone number and hit 'Continue' button
			 * Loan Summary page should be displayed
			 */

			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 30 - Verify My loan summary page
			 * It should contain all the details of the loan, payment info, Fees, 
			 * Delivery info which was provided/opted in the earlier screens by the participant
			 */
			
			requestLoan.verifyLoanSummarypage();
			
									
			/**
			 * Step 31 - Click 'I agree & submit' button
			 * Participant should be able to submit the loan request successfully 
			 * Loan Confirmation page should be displayed.
			 */
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPageHeaderIsDisplayed("Loan Confirmation");
			
			/**
			 * Step 32 - Verify Loan Confirmation Page
			 * Print, sign, and return the pre-filled form that will be emailed to you at the end of this process. 
			 */
			
			requestLoan.verifyPrincipalResidenceLoanRequestRecievedSectionForACH();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	@SuppressWarnings("static-access")
	@Test(dataProvider = "setData")
	public void DDTC_27884_Loans_Principal_Residence_Loan_Summary_page_verification(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 * Navigate to My Accounts -> Loans & Withdrawals -> Loans			
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 * Loan landing page must be displayed with the  highest amount available to borrow. 
			 * It could be general purpose or primary residence. 
			 * compare this with CSAS and legacy PW
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Number of active loans in the selected plan/ppt must be  displayed correctly.
			 * Compare to CSAS and ISIS forms
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("gaId"));
			
			/**
			 * Step 6 to 7 - Verify the Number of active loans in all plans below Summary section
			 * Verify Number of loans allowed below Summary section
			 * Number of loans available in multiple plans for the selected test ppt are displayed correctly.
			 * Number of loans available for the plan must be displayed correctly with the number of loans allowed for each loan structure.
			 */
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("gaId"));
			
			/**
			 * Step 8 - Verify the disclaimer displayed at the bottom of the page on loans landing page
			 * Disclaimer displayed at the bottom of the page must be displayed  per latest design reqs on loans landing page
			 */
			//TODO
			
			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			

			/**
			 * Step 10 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	

			/**
			 * Step 11 - Verify the 'Principal Residence Loan Requirements' page.
			 * Principal residence loan requirements(IN BOLD)
			 * This type of loan is only for purchasing or building your principal residence; 
			 * not renovating or refinancing you current home.(IN BOLD)	
			 * At the end of this process, a pre-filled form will be emailed to you. you must be sign and return to the address provided,
			 * along with the following documentation, to complete this loan request.
			 * 'Back' & 'Continue' buttons should be enabled.
			 */		
			//TODO
			
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPrincipalResidenceLoanDisclaimer();
			/**
			 * Step 12 - Click on 'Continue' button. 
			 * Ppt must be navigated to 'How much would you like to borrow' page.
			*/
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 13 - Enter amount in entry box
			 * Validate only proper values  (numeric/decimal)are accepted into this field
			 * Validate 'Continue' button is enabled only when the min and max amt values are met 
			 * else display the min,max amt error in red text if the criteria is not met.
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 14 - Hit 'Continue' button 
			 * Repayment Term options section should be displayed below the 'How much would you like to borrow' section.
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			/**
			 * Step 15 - Opt for a desired REPAYMENT TERM by selecting the radio button
			 * Once the radio button is selected Delivery options & Loan summary sections must be displayed
			 */
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.verifyMailDeliveryOptionsWithACH();
			requestLoan.verifyMyLoanSummarySection();
			
			
			/**
			 * Step 16 - Click on 'Continue' button 
			 * Confirm Information page should be displayed
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * Step 17 - In 'Confirm Information' page, verify the email address, phone number 
			 * click 'Continue' button
			 * User should be navigated to 'Loan Summary' page.
			 * Title of the page should be Loan Review
			 */
			
			requestLoan.verifyCheckBoxesInAddressPage();
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());						
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 18 - Verify My loan summary page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 * LOAN ACKNOWLEDGEMENT
			 * All the section header names should be in BOLD
			 */
			/**
			 * Step 27 - Verify 'I agree & submit' button
			 * It must be enabled
			 */
			
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 19 - Verify the LOAN DETAILS section		
			 * PLAN: verify it from Plan tabel taking gc_id of ppt as reference
			 * LOAN TYPE: General Purpose/Principal residence
			 * TERM: Chosen in Repayment Table
			 * MATURITY DATE: dd-mmm-yyyy<Requested date + term date> in Repayment term page
			 * INTEREST RATE: value displayed in Request a loan page
			 * APR: Should be the Annual Percentage Rate
			 * CHECK AMOUNT: FEES
			 * LOAN AMOUNT: CHECK AMOUNT + FEES
			 * TOTAL INTEREST AMOUNT
			 * TOTAL PRINCIPAL AND INTEREST AMOUNT: Loan AMOUNT + TOTAL INTEREST
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPage();
			
			//TODO
			
			/**
			 * Step 20 - Verify 'Payment Information' section
			 * FIRST PAYMENT DUE:
			 * LAST PAYMENT DUE:
			 * NUMBER OF PAYMENTS:
			 * PAYMENT AMOUNT:
			 * PAYMENT METHOD: Will be PAYROLL or CHECK as per the plan setup
			 * PAYMENT FREQUENCY
			 */
			//TODO
			
			/**
			 * Step 21 - Verify the 'Fees & Taxes' section
			 * ORIGINATION FEE:
			 * STAMP TAX:
			 * MAINTENANCE FEE: 
			 */
					
			/**
			 * Step 22 - Verify the 'Delivery Information' section
			 * DELIVERY METHOD:
			 * MAILING ADDRESS: 
			 */
			//TODO
			
			/**
			 * Step 23 - Verify the 'LOAN PROVISIONS' 
			 * Loan provisions – The following language should show:
			 * Please review your plan’s loan provisions before continuing with your request.
			 * <loan provisions> should be a hyper link to the provisions modal.
			 */
			//TODO
			
			/**
			 * Step 24 - Verify the 'LOAN PROVISIONS' link
			 * It should display the provisions in a scrollable modal window with 'OK' button.
			 */
			//TODO
			
			/**
			 * Step 25 - Click on 'Ok' button
			 * Modal window should get closed & 'Loan Summary' page should be displayed.
			 */
			//TODO
			
			/**
			 * Step 26 - Verify the Loan Acknowledgement section
			 * Loan acknowledgement should be displayed in a scrolling window when clicked on the LOAN ACKNOWLEDGEMENT hyperlink.
			 */
			//TODO
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
	
	@SuppressWarnings("static-access")
	@Test(dataProvider = "setData")
	public void DDTC_27661_Loans_ProActive_Notification_Opted_Principal_Residence_Loan_Request(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			/**
			 * Step 1 to 3
			 * Launch Browser and enter URL: 
			 * Enter Valid credentials and login
			 * Navigate to My Accounts -> Loans & Withdrawals -> Loans			
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			/**
			 * Step 4 - Click on'Request a new loan' button on the loans landing page
			 * PPT is navigated to What type of loan would you like? page / loan structure
			 *  page with all the correct values set for the test ppt
			 *  Loan purpose
			 *  Maximum loan
			 *  Minimum loan
			 *  Repayment term
			 *  Documentation required
			 *  Interest rate
			 *  Repayment
			 *  Fees
			 *  #Loans allowed
			 *  Waiting period
			 *  All the values are pulled correct from back end tables
			 *  for available loan structures (Primary Residence, General purpose) buttons 
			 */
			
			requestLoan.clickOnRequestANewLoan();
			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			

			/**
			 * Step 5 - Select 'Request a Princiapl Residence loan' button PPT is navigated to Primary Residence loan page/
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.			
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("Principal residence loan requirements");	

			/**
			 * Step 6 - Click 'Continue' button
			 * Participant should be navigated to next page where 
			 * 'How much would you like to borrow' page is displayed			 * 
			 */		
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.isTextFieldDisplayed("How much would you like to borrow");
			
			/**
			 * Step 7 - Enter amount in entry box
			 * Click on 'Continue' button 
			 * Repayment Term options section should be displayed below the 'How much would you like to borrow' section.

			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - select the repayment option button
			 * By clicking on the respective radio button present the Repayment Term row
			 *  Upon term selection show the loan summary section should be displayed 
			 */
			
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the header of the 'Delivery Options' section 
			 * It should be "How would you like your *check* to be delivered?"
			 */
			
			requestLoan.verifyMailDeliveryOptionsWithACH();
			
			/**
			 * Step 10 - Verify the 'My Loan Summary' is as expected 
			 * click on 'Continue'
			 * By default 'First-class mail' delivery method should be pre-selected 
			 * Participant should be navigated to Address page
			 */
			
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.verifyMyLoanSummarySection();
			requestLoan.clickContinueButton();

			/**
			 * Step 11 - In the Address page, select the check box for both the fields
			 * i) Update me by email at:
			 * ii) Update me by text message at:
			 * As expected check boxes should get selected.
			 * After the loan request submit, participant should get notification
			 * regarding the loan request submitted to Email inbox & SMS to mobile number.
			 * 
			 * Step 12 - select the check box for any one of the below field
			 * i) Update me by email at:
			 * ii) Update me by text message at:
			 */
			
			requestLoan.verifyCheckBoxesInAddressPage();
			
			/**
			 * Step 13 - Click on 'Continue' 
			 * Participant should be navigated to Loan Summary page
			 */
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
						
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 14 - Verify My loan summary page
			 * All the information must be correct as per the entries/selections in the previous pages. 
			 */
			
			requestLoan.verifyLoanSummarypage();
			
									
			/**
			 * Step 15 - Click 'I agree & submit' button
			 * Participant should be able to submit the loan request successfully 
			 * Loan Confirmation page should be displayed.
			 */
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			requestLoan.verifyPrincipalResidenceLoanRequestRecievedSectionForRegularMail();
			
			/**
			 * Step 16 - After submit verify the EVENT_CONTACT_INFO table
			 * use query: select * from EVENT_CONTACT_INFO order by EV_ID desc
			 * Event should be populated in the table with the  preferred/selected notification in step 12/13 details. 
			 */
			
			//TODO
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
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
}