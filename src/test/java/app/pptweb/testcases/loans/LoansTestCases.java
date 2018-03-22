package app.pptweb.testcases.loans;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.general.LeftNavigationBar;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.RetirementIncome;
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			// Step 5
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
			// Step 6
			// TODO
			// Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));
			// Step 8
			requestLoan.verifyLoansDisclaimer();
			// Step 9
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
			String interestRate = requestLoan
					.getInterestRateFromRequestLoanPage(Stock
							.GetParameterValue("loanType"));

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

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step27 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(interestRate,
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ interestRate, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ interestRate
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

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
			 * Step 30 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

			/**
			 * Step 31 - Verify for the Maintenance Fee value in 'FEES' tool tip
			 * - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			// Step 5
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
			// Step 6
			// TODO
			// Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock
					.GetParameterValue("ga_id"));
			// Step 8
			requestLoan.verifyLoansDisclaimer();
			// Step 9
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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

			/*if (Web.VerifyPartialText(Stock.GetParameterValue("LoanTotal"),
					requestLoan.getWebElementText("LOAN TOTAL"), true)) {

				Reporter.logEvent(Status.PASS,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Matching in Loan summary Table\nLoan Total:"
								+ requestLoan.getWebElementText("LOAN TOTAL"),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Not Matching in Loan summary Table\nExpected:"
								+ Stock.GetParameterValue("LoanTotal")
								+ "\nActual:"
								+ requestLoan.getWebElementText("LOAN TOTAL"),
						true);
			}*/
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
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			// Step 16 to 21
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			 * Step 15 - Select the Delivery method 'Expedited mail'
			 * Delivery method selection should be switched to 'Expedited mail'.
			 */
			Web.clickOnElement(requestLoan, "EXPEDITED MAIL RADIO BUTTON");
			/**
			 * Step 16 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			/**
			 * Step 17 - Verify the 'My loan summary' section It must have the below
			 * details iin the form of a table INTEREST RATE FEES* CHECK TOTAL
			 * LOAN TOTAL
			 */

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step18 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			/**
			 * Step 19 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 20 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 21 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			Web.clickOnElement(requestLoan, "ACH DELIVERY RADIO BUTTON");
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

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step 21 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			/**
			 * Step 22 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 23 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 24 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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

			
			Web.clickOnElement(requestLoan, "Button Request A New Loan");
			
			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			
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
			
			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");
			
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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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

			
			Web.clickOnElement(requestLoan, "Button Request A New Loan");
			
			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			
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
			
			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");
			
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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
	@SuppressWarnings("static-access")
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			/**
			 * Step 5 - Verify the Number of active loans in this plan below
			 * summary section Validate the number of active loans in the select
			 * plan/ppt displayed correct.
			 */
			String expectedNoofLoans = requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans = requestLoan
					.getMaximumLoansAllowedforPlan(Stock
							.GetParameterValue("ga_id"));
			if (expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans + "\nActual No.Of Loans:"
								+ ActualNoofLoans, false);

			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"
								+ expectedNoofLoans
								+ "\nActual No.Of Loans:"
								+ ActualNoofLoans, true);
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

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			Web.clickOnElement(requestLoan, "ACH DELIVERY RADIO BUTTON");
			Web.waitForElement(requestLoan,"ACH ACCOUNT DROP DOWN");
			requestLoan.verifyWebElementIsDisplayed("ACH ACCOUNT DROP DOWN");
			
			
			requestLoan.selectACHAccountNumber();
			
			
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step 17 - Verify the INTEREST RATE It should be the same value
			 * which was displayed in the 'Request a new loan' page.
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			/**
			 * Step 18 - Hover the mouse on the 'INTEREST RATE' value - not
			 * feasible
			 */

			/**
			 * Step 19 - Verify the FEES* value - It must be the sum of
			 * 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();

			/**
			 * Step 20 - Hover the mouse on the 'FEES' value - Not Feasible
			 */

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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
	@SuppressWarnings("static-access")
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
		
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

		
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

			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");

			/**
			 * Step 7 -Verify interest rate tooltip details.- Partially Automated
			 */

			if (Web.VerifyText(requestLoan.getInterestRate(),
					requestLoan.getInterestRateFromLoanSummaryTable())) {

				Reporter.logEvent(Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"
								+ requestLoan.getInterestRate(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"
								+ requestLoan.getInterestRate()
								+ "\nActual:"
								+ requestLoan
										.getInterestRateFromLoanSummaryTable(),
						true);
			}

			
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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt = requestLoan.getMaximumLoanAmount();
			if (!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"
								+ loanAmt, true);

			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"
								+ loanAmt, true);

			
			/**
			 * Step 3 - Select 'Request a new loan' button on the loans landing page
			 * Loan type selection page with header 'What type of loan would you like?' was displayed
			 */

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
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
			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			 loanAmt = requestLoan.getMaximumLoanAmount();
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
			
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			
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
	@SuppressWarnings("static-access")
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
			

			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
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
			
			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");
		
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
			requestLoan.isTextFieldDisplayed("Loan Details");
			requestLoan.isTextFieldDisplayed("PLAN:");
			requestLoan.isTextFieldDisplayed("LOAN TYPE:");
			requestLoan.isTextFieldDisplayed("TERM:");
			requestLoan.isTextFieldDisplayed("MATURITY DATE:");
			requestLoan.isTextFieldDisplayed("INTEREST RATE:");
			requestLoan.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLoan.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLoan.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLoan.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLoan
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLoan.isTextFieldDisplayed("Payment Information");
			requestLoan.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLoan.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLoan.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLoan.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLoan.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLoan.isTextFieldDisplayed("Fees and Taxes");
			requestLoan.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLoan.isTextFieldDisplayed("Delivery Information");
			requestLoan.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLoan.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLoan.isTextFieldDisplayed("Loan Provisions");
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
		
			Web.clickOnElement(requestLoan, "Button Request A New Loan");

			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");

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
			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			requestLoan.isTextFieldDisplayed("Waiting period");
			requestLoan.verifyLoneTypeisDisplayed("GENERAL PURPOSE");
			requestLoan.verifyLoneTypeisDisplayed("MORTAGAGE");
			
			
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
}