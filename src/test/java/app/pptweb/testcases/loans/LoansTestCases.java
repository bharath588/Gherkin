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
	static String printTestData="";
	static String userName=null;
	static String confirmationNumber="";

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
		printTestData="";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
		}
	 return printTestData;
	}
	
	/**
	 * This Test Case to verify General Purpose Loan Repayment Page
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27488_General_Purpose_Loan_Repayment_Term_Page_Verification(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();

			//Step 4
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt =requestLoan.getMaximumLoanAmount();
			if(!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
			//Step 5
			String expectedNoofLoans =requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans =requestLoan.getMaximumLoansAllowedforPlan(Stock.GetParameterValue("ga_id"));
			if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
			//Step 6
			//TODO
			//Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			//Step 8
			requestLoan.verifyLoansDisclaimer();
			//Step 9
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
			//Step 10
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			//Step 11
			requestLoan.EnterLoanAmount(Stock.getConfigParam("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan.verifyWebElementIsDisplayed("Link How Is This Calculated");
			//Step 12
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			//Step 13
			requestLoan.isTextFieldDisplayed("Select from a repayment term option below or");
			
			//Step 14
			requestLoan.isTextFieldDisplayed("enter your own term");
			requestLoan.verifyWebElementIsDisplayed("Link Enter Your Own Term");
			//Step 15
			requestLoan.isTextFieldDisplayed("Select");
			requestLoan.isTextFieldDisplayed(" bi-weekly");
			requestLoan.isTextFieldDisplayed("Repayment Term");
			requestLoan.verifyDefaultRepamentTerm();
			//Step 16
			//TODO
			//Step 17
			//TODO
			//Step 18
			requestLoan.EnterLoanAmount("200");
			requestLoan.verifyWebElementIsDisplayed("BUTTON UPDATE");
			//Step 19
			Web.clickOnElement(requestLoan,"BUTTON UPDATE");
			
			
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
	 * This Test Case to verify General Purpose Loan My Loan Page
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27491_General_Purpose_Loan_My_Loan_Page_Verification(int itr, Map<String, String> testdata) {

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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt =requestLoan.getMaximumLoanAmount();
			if(!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			String expectedNoofLoans =requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans =requestLoan.getMaximumLoansAllowedforPlan(Stock.GetParameterValue("ga_id"));
			if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
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
			 * Step 8 - Verify the disclaimer displayed at the bottom of the page on loans landing page
			 * Validate the disclaimer displayed at the bottom of the page 
			 * is displayed per latest design reqs on loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();
			
			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing pag
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
			String interestRate =requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType"));
			
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
			requestLoan.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			
			/**
			 * Step 11 - Enter amount in entry box 
			 * Validate only proper values (numeric/decimal)are accepted into this field
			 * Validate 'Continue' button is enabled only when the min and max amt values are met else
			 *  display the min,max amt error in red text if the criteria is not met.
			 */
			requestLoan.EnterLoanAmount(Stock.getConfigParam("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan.verifyWebElementIsDisplayed("Link How Is This Calculated");
			
			/**
			 * Step 12 - Hit 'Continue' button
			 * Validate that Repayment Term options section 
			 * should be displayed below the 'How much would you like to borrow' section
			 */
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			
			/**
			 * Step 13 - Verify the Repayment Term options/values section verbiage
			 * Verbiage should be "Select from a repayment term option below or enter your own term"
			 */
			requestLoan.isTextFieldDisplayed("Select from a repayment term option below or");
			
			/**
			 * Step 14 - Verify "enter your own loan term" at the end of the verbiage
			 * It must be a link & text should be in blue color
			 */
			
			requestLoan.isTextFieldDisplayed("enter your own term");
			requestLoan.verifyWebElementIsDisplayed("Link Enter Your Own Term");
			
			/**
			 * Step 15 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT
			 * ii) <Repayment Frequency Value>(Dynamic value)
			 * iii) REPAYMENT TERM
			 * Five 'Repayment Term' options should be offered to the participant
			 *  with below tenures by default in ascending order
			 *  12 months
			 *  24 months
			 *  36 months
			 *  48 months
			 *  60 months
			 */
			
			requestLoan.isTextFieldDisplayed("Select");
			requestLoan.isTextFieldDisplayed(" bi-weekly");
			requestLoan.isTextFieldDisplayed("Repayment Term");
			requestLoan.verifyDefaultRepamentTerm();
			
			/**
			 * Step 16 - Verify the 2nd column header in the repayment term table
			 * It should be <Repayment Frequency Value>(Dynamic value) asper the plan set up.
			 * It can be weekly, Bi-Weekly, Monthly, Semi-Monthly or Quarterly
			 */
			//TODO
			/**
			 * Step 17 - Verify the Repayment Term options/values or the per pay period amount for each tenure/REPAYMENT TERM
			 * Validate per pay period amount values are displayed correct as per the loan amount entered for each REPAYMENT TERM. 
			 */
			//TODO
			/**
			 * Step 18 -Verify SELECT column for each loan term row 
			 * Each loan term row should have the radio button
			 */
			requestLoan.verifySelectColumnForLoanTerm();
			
			/**
			 * Step 19 - Verify the Repayment term options or number of radio button selections ppt can select
			 * Participant must be able to select only ONE Repayment term option or radio button at an instance
			 * Step 20 - Select Repayment Term option say for e.g. 24 months by clicking on the respective radio button present the Repayment Term row
			 * Validate that upon term selection Delivery options & 'My Loan Summary section should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			/**
			 * Step 21 -Verify the header of the 'Delivery Options' section 
			 * It should be "How would you like your check to be delivered?"
			 */
			
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			
			/**
			 * Step 22 and 23
			 * Verify the 'Delivery Options' offered for ppt with ACH set up
			 * It should be
			 * i) Regular mail
			 * Delivery up to 5 business days
			 * Free
			 * ii) Expedited mail
			 * Delivery up to 2 business days
			 * $25
			 * iii) Electronically transfer funds directly to my bank account (ACH)
			 * Delivery up to 3 business days
			 * $30.00
			 */
			requestLoan.verifyMailDeliveryOptions();
			
			/**
			 * Step 24 - Verify the selection of the  Delivery method' by default once the radio button is selected
			 * By default 'Regular mail' delivery method should be pre-selected
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			
			/**
			 * Step 25 - Verify the 'Continue' button at the bottom of the page
			 * It must be present & enabled
			 */
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			
			/**
			 * Step 26 - It must be present & enabled
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			
			requestLoan.isTextFieldDisplayed("INTEREST RATE");
			requestLoan.isTextFieldDisplayed("FEES*");
			requestLoan.isTextFieldDisplayed("CHECK TOTAL");
			requestLoan.isTextFieldDisplayed("LOAN TOTAL");
			
			/**
			 * Step27 - Verify the INTEREST RATE
			 * It should be the same value  which was displayed in the 'Request a new loan' page.
			 */
			
			if(Web.VerifyText(interestRate, requestLoan.getInterestRateFromLoanSummaryTable())){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify INTEREST RATE is Matching",
						"INTEREST RATE is Matching in Loan summary Table\nINTEREST RATE:"+interestRate,	
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"VerifyINTEREST RATE is Matching",
						"INTEREST RATE is Not Matching in Loan summary Table\nExpected:"+interestRate+"\nActual:"+requestLoan.getInterestRateFromLoanSummaryTable(),	
						true);
			}
			
			/**
			 * Step 28 - Hover the mouse on the 'INTEREST RATE' value - not feasible
			 */
		
			/**
			 *Step 29 - Verify the FEES* value - It must be the sum of 'Origination Fee', Stamp Tax & etc if any
			 *
			 */
			requestLoan.verifyOriginationFeeDisplayed();
			
			/**
			 * Step 30 - Hover the mouse on the 'FEES' value - Not Feasible
			 */
			
			/**
			 * Step 31 - Verify for the Maintenance Fee value in 'FEES' tool tip -  Not Feasible
			 */
			
			/**
			 * Step 32 - Verify the CHECK TOTAL value - It must have the value of the loan amount requested minus the FEES
			 * CHECK TOTAL = LOAN AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmount();
			/**
			 * Step 33 - Verify the LOAN TOTAL value - It must have the value of Loan Amount requested.
			 * LOAN TOTAL = CHECK TOTAL + FEES 
			 */
			
			requestLoan.verifyLoanTotalAmount();
			
			/**
			 * Step 34 - Verify the verbiage at the bottom of the page informing about the Maintenance Fee
			 * Verbiage should be present at the bottom of the page above the 'Back' button
			 * Verbiage is "* Excludes quarterly $7.50 maintenance fee."
			 */
			requestLoan.isTextFieldDisplayed("* Excludes quarterly $7.50 maintenance fee.");
			
			/**
			 * Step 35 - Change the Repayment Term option say for e.g. 36 months by c
			 * licking on the respective radio button present the Repayment Term row.
			 * 
			 * Validate that upon term selection change Delivery options & 
			 * 'My Loan Summary section should be displayed  - N/A
			 */
			
			
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
	 * This Test Case to verify General Purpose Loan Repayment Page
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27500_General_Purpose_Loan_Summary_Page_Verification(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();

			//Step 4
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt =requestLoan.getMaximumLoanAmount();
			if(!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
			//Step 5
			String expectedNoofLoans =requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans =requestLoan.getMaximumLoansAllowedforPlan(Stock.GetParameterValue("ga_id"));
			if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
			//Step 6
			//TODO
			//Step 7
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			//Step 8
			requestLoan.verifyLoansDisclaimer();
			//Step 9
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
			//Step 10
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			//Step 11
			requestLoan.EnterLoanAmount(Stock.getConfigParam("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan.verifyWebElementIsDisplayed("Link How Is This Calculated");
			//Step 12
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			//Step 13
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
			
			if(Web.VerifyPartialText(Stock.GetParameterValue("LoanTotal"), requestLoan.getWebElementText("LOAN TOTAL"), true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Matching in Loan summary Table\nLoan Total:"+requestLoan.getWebElementText("LOAN TOTAL"),	
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Not Matching in Loan summary Table\nExpected:"+Stock.GetParameterValue("LoanTotal")+"\nActual:"+requestLoan.getWebElementText("LOAN TOTAL"),	
						true);
			}
			
			//Step 14
			requestLoan.clickContinueButton();
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");
			
			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			//Step 15
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			//Step 16 to 21
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
			requestLoan.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
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
			//Step 22 and 23
			requestLoan.verifyLoanProvisionLink();
			//Step 24
			requestLoan.verifyWebElementIsDisplayed("LOAN ACKNOWLEDGEMENT WINDOW");
			
			//Step 25
			if ( Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
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
	 * This Test Case to verify General Purpose Loan What's Next page
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27501_General_Purpose_Loan_Confirmation_Whats_Next_Page_Verification(int itr, Map<String, String> testdata) {

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
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt =requestLoan.getMaximumLoanAmount();
			if(!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
			/**
			 * Step 5 - Verify the Number of active loans in this plan below summary section
			 * Validate the number of active loans in the select plan/ppt displayed correct.
			 */
			String expectedNoofLoans =requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans =requestLoan.getMaximumLoansAllowedforPlan(Stock.GetParameterValue("ga_id"));
			if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
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
			 * Step 8 - Verify the disclaimer displayed at the bottom of the page on loans landing page
			 * Validate the disclaimer displayed at the bottom of the page 
			 * is displayed per latest design reqs on loans landing page
			 */
			requestLoan.verifyLoansDisclaimer();
			
			/**
			 * Step 9 - Click on'Request a new loan' button on the loans landing pag
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
			String interestRate =requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType"));
			
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
			requestLoan.isTextFieldDisplayed("How much would you like to borrow?");
			requestLoan.verifyLoanMinimumIsDisplayed();
			requestLoan.verifyLoanMinimumErrorMessageIsDisplayed("10");
			
			/**
			 * Step 11 - Enter amount in entry box 
			 * Validate only proper values (numeric/decimal)are accepted into this field
			 * Validate 'Continue' button is enabled only when the min and max amt values are met else
			 *  display the min,max amt error in red text if the criteria is not met.
			 */
			requestLoan.EnterLoanAmount(Stock.getConfigParam("loanAmount"));
			requestLoan.verifyWebElementIsDisplayed("BUTTON CONTINUE");
			requestLoan.verifyWebElementIsDisplayed("BUTTON BACK");
			requestLoan.verifyWebElementIsDisplayed("Link How Is This Calculated");
			
			/**
			 * Step 12 - Hit 'Continue' button
			 * Validate that Repayment Term options section 
			 * should be displayed below the 'How much would you like to borrow' section
			 */
			
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			requestLoan.verifyWebElementIsDisplayed("Repayment Term Table");
			
			/**
			 * Step 13 - Opt for a desired REPAYMENT TERM by selecting the radio button
			 * Verbiage should be "Select from a repayment term option below or enter your own term"
			 */
		
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			requestLoan.isTextFieldDisplayed("My loan summary");
		
			
			
			/**
			 * Step 14 - Click on 'Continue' button
			 * Confirm Information page should be displayed
			 */
			requestLoan.clickContinueButton();
			
			requestLoan.verifyWebElementIsDisplayed("ProActive Notification Screen");
			
			requestLoan.isTextFieldDisplayed("Sign up for updates on your loan process");
			/**
			 * Step 15 -In 'Confirm Information' page, verify the email address, phone number and click 'Continue' button
			 * In 'Confirm Information' page, verify the email address, phone number and click 'Continue' button
			 */
			requestLoan.verifyWebElementIsDisplayed("EmailId Input Field");
			requestLoan.verifyWebElementIsDisplayed("Phone No Input Field");
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.verifyPageHeaderIsDisplayed("Header Loan Review");
			
			/**
			 * Step 16 - Verify the Loan Summary page and click on 'I agree & Submit' page
			 * User should be displayed with the Confirmation page
			 */
			
			
			if ( Web.isWebElementDisplayed(requestLoan, "I AGREE AND SUBMIT",
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
			 * Step 17 - Verify the page sections
			 * It should have 'Loan request received' and 'Confirmation' sections
			 */
			requestLoan.isTextFieldDisplayed("Loan request received");
			requestLoan.isTextFieldDisplayed("Loan Details");
			
			/**
			 * Step 18 - Verify the disclaimer message below the 'Loan request received' section
			 * It should be 'Your loan request has been received is being processed. 
			 * Depending on your preference, you will receive an email and/or text with your loan's status.
			 * 
			 */
			
			requestLoan.verifyLoanConfirmationDisclaimer();
			
			/**
			 * Step 19 - Verify the information under the 'Loan request received' section
			 * It should have below information:
			 * 1 Loan request received
			 * 2 Approval from your employer
			 * 3. Processing
			 * 4. Check sent by <selected delivery option>
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