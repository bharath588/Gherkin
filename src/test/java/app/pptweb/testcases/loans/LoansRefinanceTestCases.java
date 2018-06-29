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

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;


public class LoansRefinanceTestCases {

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
	 * This Test Case to verify Loan Refinance Option offered to the participant
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31571_LoansRefinance_Verifying_Loan_Refinance_Option_Is_Offered_To_The_Participant(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 - 7
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			/**
			 * Step 8
			 */
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			
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
	 * This Test Case to verify Loan Refinance Option offered to the participant
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31582_LoansRefinance_Verifying_Loan_Refinance_Option_Is_Offered_To_The_Participant_For_General_Purpose_Flow_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanTypeGenPur"));
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			/**
			 * Step 8
			 */			
			requestLoan.clickOnBackButton();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.selectLoneType(Stock.GetParameterValue("loanTypePriRes"));
			if(!requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to principal residence Loan requirements page on selecting Princiapl Residence Loan", 
						"Principal residence Loan requirements page is Displayed on selecting Principal residence Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated toprincipal residence Loan requirements page on selecting Princiapl Residence Loan", 
						"Principal residence Loan requirements page is Displayed on selecting Principal residence Loan is not Displayed", true);				
			}
			
			
			
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
	 * This Test Case to verify Loan Refinance Option offered to the participant
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31584_LoansRefinance_Verifying_Loan_Refinance_Option_Is_Offered_To_The_Participant_For_Principal_Residence_Loan_Flow_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			
			requestLoan.verifyMaximumLoansForLoanStructure(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanTypePriRes"));
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting Principal Residence Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting Principal Residence Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting Principal Residence Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			/**
			 * Step 8
			 */			
			requestLoan.clickOnBackButton();
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.selectLoneType(Stock.GetParameterValue("loanTypeGenPur"));
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Quotes Page on selecting General Purpose Loan", 
						"Loan Quotes page is not Displayed on selecting General Purpose Loan", true);
			}else{
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Quotes page on selecting General Purpose Loan", 
						"Loan Quotes page is Displayed on selecting General Purpose Loan", false);				
			}
			
			
			
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
	 * This Test Case to verify Loan Refinance Option offered to the participant
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31627_LoansRefinance_Verify_The_Loan_Review_Page_For_Loan_Refinance_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/** 
			 * Step 10 - Verify the CHECK TOTAL value
			 * It must have the value of the Refinance amount requested minus the FEES
			 * CHECK TOTAL = REFINANCE AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmountForRefinance();
			
			/**
			 * Step 11 - Verify the LOAN TOTAL value
			 * LOAN TOTAL = CHECK TOTAL + FEES + TOTAL OUTSTANDING LOAN BALANCE of Active Loan(s) selected for refinance
			 */
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 12 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 15 - In Loan Review page, verify the Loan Details section values
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			/**
			 * Step 16 - In Loan Review page, verify the Payment Information section values.
			 */
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			/**
			 * Step 17 - Verify the 'Fees & Taxes' section
			 */
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			/**
			 * Step 18 - Verify the 'Delivery Information' section
			 */
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 19 to 21 - Verify the 'LOAN PROVISIONS'  
			 */
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 22 - verify Loan Acknowledgement link
			 */
			// No Loan Acknowledgement Link
			
			/**
			 * Step 23 - verify 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			
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
	 * This Test Case to verify Loan Refinance Option offered to the participant
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31628_LoansRefinance_Verify_The_Confirmation_Page_For_General_Purpose_Loan_Refinance_Request(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/** 
			 * Step 10 - Verify the CHECK TOTAL value
			 * It must have the value of the Refinance amount requested minus the FEES
			 * CHECK TOTAL = REFINANCE AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmountForRefinance();
			
			/**
			 * Step 11 - Verify the LOAN TOTAL value
			 * LOAN TOTAL = CHECK TOTAL + FEES + TOTAL OUTSTANDING LOAN BALANCE of Active Loan(s) selected for refinance
			 */
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 12 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 15 - In Loan Review page, verify the Loan Details section values
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			/**
			 * Step 16 - In Loan Review page, verify the Payment Information section values.
			 */
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			/**
			 * Step 17 - Verify the 'Fees & Taxes' section
			 */
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			/**
			 * Step 18 - Verify the 'Delivery Information' section
			 */
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 19 to 21 - Verify the 'LOAN PROVISIONS'  
			 */
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 22 - verify Loan Acknowledgement link
			 */
			// No Loan Acknowledgement Link
			
			/**
			 * Step 23 - verify 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31629_LoansRefinance_Verify_The_Confirmation_Page_For_Principal_Residence_Loan_Refinance_Request(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/** 
			 * Step 10 - Verify the CHECK TOTAL value
			 * It must have the value of the Refinance amount requested minus the FEES
			 * CHECK TOTAL = REFINANCE AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmountForRefinance();
			
			/**
			 * Step 11 - Verify the LOAN TOTAL value
			 * LOAN TOTAL = CHECK TOTAL + FEES + TOTAL OUTSTANDING LOAN BALANCE of Active Loan(s) selected for refinance
			 */
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 12 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 15 - In Loan Review page, verify the Loan Details section values
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			/**
			 * Step 16 - In Loan Review page, verify the Payment Information section values.
			 */
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			/**
			 * Step 17 - Verify the 'Fees & Taxes' section
			 */
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			/**
			 * Step 18 - Verify the 'Delivery Information' section
			 */
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 19 to 21 - Verify the 'LOAN PROVISIONS'  
			 */
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 22 - verify Loan Acknowledgement link
			 */
			// No Loan Acknowledgement Link
			
			/**
			 * Step 23 - verify 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31638_Request_New_General_Purpose_Loan_When_Participant_Has_Option_To_Either_Request_New_Loan_Refinance_Existing_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRequestANewLoan();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/** 
			 * Step 10 - Verify the CHECK TOTAL value
			 * It must have the value of the Refinance amount requested minus the FEES
			 * CHECK TOTAL = REFINANCE AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmountForRefinance();
			
			/**
			 * Step 11 - Verify the LOAN TOTAL value
			 * LOAN TOTAL = CHECK TOTAL + FEES + TOTAL OUTSTANDING LOAN BALANCE of Active Loan(s) selected for refinance
			 */
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 12 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 15 - In Loan Review page, verify the Loan Details section values
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			/**
			 * Step 16 - In Loan Review page, verify the Payment Information section values.
			 */
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			/**
			 * Step 17 - Verify the 'Fees & Taxes' section
			 */
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			/**
			 * Step 18 - Verify the 'Delivery Information' section
			 */
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 19 to 21 - Verify the 'LOAN PROVISIONS'  
			 */
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 22 - verify Loan Acknowledgement link
			 */
			// No Loan Acknowledgement Link
			
			/**
			 * Step 23 - verify 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31641_Request_New_Principal_Residence_Loan_When_Participant_Has_Option_To_Either_Request_New_Loan_Refinance_Existing_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRequestANewLoan();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/** 
			 * Step 10 - Verify the CHECK TOTAL value
			 * It must have the value of the Refinance amount requested minus the FEES
			 * CHECK TOTAL = REFINANCE AMOUNT REQUESTED - FEES
			 */
			requestLoan.verifyCheckTotalAmountForRefinance();
			
			/**
			 * Step 11 - Verify the LOAN TOTAL value
			 * LOAN TOTAL = CHECK TOTAL + FEES + TOTAL OUTSTANDING LOAN BALANCE of Active Loan(s) selected for refinance
			 */
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 12 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			/**
			 * Step 15 - In Loan Review page, verify the Loan Details section values
			 */
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			/**
			 * Step 16 - In Loan Review page, verify the Payment Information section values.
			 */
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			/**
			 * Step 17 - Verify the 'Fees & Taxes' section
			 */
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			/**
			 * Step 18 - Verify the 'Delivery Information' section
			 */
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 19 to 21 - Verify the 'LOAN PROVISIONS'  
			 */
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 22 - verify Loan Acknowledgement link
			 */
			// No Loan Acknowledgement Link
			
			/**
			 * Step 23 - verify 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31643_Refinance_Active_General_Purpose_Loan_Try_Requesting_Another_General_Purpose_Loan_Before_Existing_Refinance_Request_Not_Complete(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31644_Verify_When_Participant_Has_Exhausted_The_Available_Loans_Does_Not_Have_Loans_Eligible_For_Refinance(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31588_Verifying_Loan_Refinance_Page_ForGeneral_Purpose_Loan_Flow_When_Participant_HasNot_Exhausted_All_Loans(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4 - verify and validate details displayed on Loans Landing Page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify "Number of active loans in this plan" below Summary section
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("gaId"));
			
			/**
			 * Step 6 - Click on 'request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7 - Click on 'Request a General Purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			/**
			 * Step 8 & 9 - Verify the Loan Refinance page
			 * Page should have header
			 * "Do you want to request a new loan or add amount to existing loan?" in BOLD with below options
			 * New loan
			 * Refinance existing loan
			 * 
			 * By default 'New loan' option must be selected & 'Continue' button should be enabled.
			 */
			requestLoan.verifyLoanRefinancePage();
			
			/**
			 * Step 10 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 11 - Verify the 'Continue' button, should not be displayed
			 */
			requestLoan.verifyContinueButtonIsEnabled(false);
			
			/**
			 * Step 12 - Verify the Active Loan details section
			 * It should have header "_Select the loan or loans you would like to refinance._"
			 */
			//TODO
			
			/**
			 * Step 13 - Verify the table headers and its values
			 */
			//TODO
			
			/**
			 * Step 14 - Select the checkboxes
			 * 'Continue' button should be presented to the user to proceed with the Refinance flow.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			
			/**
			 * Step 15 - Verify the multiple selection of the checkboxes
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31688_Verify_Enter_Your_Own_Term_Functionality_AS_212_Exception_For_Refinance_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Verify "Number of active loans in this plan" below Summary section
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_Id"));
			
			/**
			 * Step 3 - Click on 'request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 4 - Click on 'Request a General Purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			/**
			 * Step 5 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 6
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			/**
			 * Step 8
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 9
			 */
			requestLoan.clickOnEnterYourOwnLoanTerm(Stock.GetParameterValue("newLoanTermValid"));
			
			/**
			 * Step 10
			 */
			requestLoan.verifyRefinanceRepaymentTermAfterAddingNewTerm(Stock.GetParameterValue("newLoanTermValid"));
			
			/**
			 * Step 11 
			 */
			requestLoan.clickOnEnterYourOwnLoanTerm(Stock.GetParameterValue("newLoanTermInvalid"));
			
			/**
			 * Step 12
			 */
			requestLoan.verifyAS_212ErrorMessage();
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31672_Enter_Amount_Field_Validation_AS_205_AS_211_Exceptions_For_Loan_Refinance(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			
			/**
			 * Step 3 - Click on 'Request a General Purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			/**
			 * Step 4 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 5
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.verifyUpdateButtonIsEnabled(true);
			
			/**
			 * Step 6 - Enter Amount that is sum of outstanding balance of active loan selected for refinance
			 *  and Refinance amount entered is less than plan minimum.
			 */
			
			/*requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmountLesserThanMin"));
			requestLoan.verifyErrorMsgForInvalidLoanAmount();
			requestLoan.verifyUpdateButtonIsEnabled(false);
			*/
			/**
			 * Step 7 - Enter Refinance Amount which is less than the Fee(Origination Fee+any of the Delivery Fee)
			 */
			requestLoan.verifyErrorMsgForInvalidLoanAmount(requestLoan.getOriginationFee());
			requestLoan.verifyUpdateButtonIsEnabled(false);
			
			/**
			 * Step 8 - Enter amount more than Maximum Amount
			 */
			requestLoan.verifyErrorMsgForInvalidLoanAmount(Stock.GetParameterValue("loanAmountMoreThanMax"));
			requestLoan.verifyUpdateButtonIsEnabled(false);
			
			/**
			 * Step 9 - Enter Valid Amount
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyUpdateButtonIsEnabled(true);
			
			/**
			 * Step 10 - Clear the Loan Amount field
			 */
			requestLoan.clearInputLoanAmountField();
			requestLoan.verifyUpdateButtonIsEnabled(true);
			
			/**
			 * Step 11 - Enter Special Characters
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmountSpecialCharacters"));
			
			/**
			 * Step 12 - Enter Deciaml Value
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmountDecimalValue"));
			requestLoan.verifyUpdateButtonIsEnabled(true);
			
			/**
			 * Step 13 - Click on Continue Button
			 */
			requestLoan.clickUpdateButton();
			Web.waitForElement(requestLoan, "Repayment Term Table");
			
			/**
			 * Step 14 - Change Loan Amount Value
			 */
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.verifyUpdateButtonIsEnabled(true);
			
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
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31619_Refinance_An_Active_General_Purpose_Loan_With_Participant_Having_Option_o_Either_Request_A_New_Loan_Or_Refinancing_Existing_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 * Click 'Continue' button
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 11 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 12 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 13 - Verify the General Purpose Loan Refinance request confirmation page
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
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_31588_Verifying_Loan_Refinance_Page_For_General_Purpose_Loan_Flow_And_Participant_Has_Not_Exhausted_All_Loans(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4 - Verify Loans Landing Page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify Number of Active Loans in this plan
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Verify Loan Refinancing Page
			 */
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			requestLoan.verifyLoanRefinancePage();
			
			/**
			 * Step 9 - Verify the selection & 'Continue' button
			 * By default 'New loan' option must be selected & 'Continue' button should be enabled. 
			 */
			requestLoan.verifyNewLoanRefinanceisSelected();
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 10 - Select the radio button 'Refinance existing loan'
			 * Participant should be  displayed with all the currently active loans detail in tabular format.
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 11 - Verify Continue button
			 */
			//TODO
			
			/**
			 * Step 12 to 13 - Verify the Active Loan details section
			 * It should display
			 * SELECT
			 * OUTSTANDING BALANCE
			 * PAYMENT AMOUNT
			 * MATURITY DATE
			 * LONG TERM REMAINING
			 */
			System.out.println(StringUtils.capitalize("SLECT"));
			System.out.println(StringUtils.capitalize("OUTSTANDING BAL"));
			requestLoan.verifyActiveLoansDetailsSectionForRefinance(Stock.GetParameterValue("ActiveLoansHeaders"));
			
			/**
			 * Step 14 - Select the check boxes
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 15 - Verify the multiple selection of the checkboxes
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

	
	@Test(dataProvider = "setData")
	public void DDTC_32448_Loan_Refinance_Pre_Filled_form_Delivery_for_General_Purpose_Loan_Refinance_Request_by_Participant_With_Recent_Address_Change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
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
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			
			/**
			 * Step 5
			 */
			requestLoan.clickLoanReasonOptionPreFilled();
			
			/**
			 * Step 6 to 7 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * Click on 'Request a General Purpose loan' button.
			 */
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 8
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 9 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Select Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 11 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 12 - Verify the 'My Loan Summary' is as expected
			 * click on 'Continue' with Delivery method option selected as 'First-class mail'
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 11 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 12 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 13 - Verify the General Purpose Loan Refinance request confirmation page
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
	
	@Test(dataProvider = "setData")
	public void DDTC_32423_Loan_Refinance_Future_Dated_General_Purpose_Loan_Refinance_Request_by_Participant_With_Recent_Address_Change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
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
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			
			/**
			 * Step 5
			 */
			requestLoan.clickLoanReasonOptionFutureDate();
			
			/**
			 * Step 6 to 7 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * Click on 'Request a General Purpose loan' button.
			 */
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 8
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 9 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Select Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 11 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 12 - Verify the 'My Loan Summary' is as expected
			 * click on 'Continue' with Delivery method option selected as 'First-class mail'
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Click Continue button
			 */
			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 11 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 12 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 13 - Verify the General Purpose Loan Refinance request confirmation page
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31595_Loan_Refinance_Verify_Loan_Quotes_Page_General_Purpose_Loan_Refinance_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - verify number of active loans in this plan
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 3 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 4 - Click on 'Request a general purpose loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 5 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 6 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/**
			 * Step 7 - Verify the Available to Borrow amount
			 * It must be 50% of the vested balance minus the highest outstanding loan balance in last 12 months.
			 */
			//TODO
			
			/**
			 * Step 8 - Verify the section beside the 'Available to Borrow'
			 * Label should be: ENTER AMOUNT TO ADD TO YOUR CURRENT LOAN
			 */
			requestLoan.isTextFieldDisplayed("ENTER AMOUNT TO ADD TO YOUR CURRENT LOAN");
			
			
			/**
			 * Step 9 - Verify that current active loan balance is displayed
			 */
			requestLoan.verifyCurrentActiveLoanBalanceIsDisplayed();
			
			/**
			 * Step 10 - In step 6, if two or more active loans(s) are selected for refinancing
			 */
			//TODO
			
			/**
			 * Step 11 - Hover the mouse on 'Loan outstanding balance'
			 */
			requestLoan.validateToolTipForLoanOutstandingBalance();
			
			/**
			 * Step 12 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 13 - Verify the Repayment Term options/values section table
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 14 - Click on 'Enter Your own loan Term' and verify the functionality 
			 */
			requestLoan.clickOnEnterYourOwnLoanTerm(Stock.GetParameterValue("newLoanTermValid"));
			requestLoan.verifyRefinanceRepaymentTermAfterAddingNewTerm(Stock.GetParameterValue("newLoanTermValid"));
			
			/**
			 * Step 15 - Select Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 16 - Verify the 'My Loan Summary' 
			 * Step 22 & 23 [Verify Check Total and Loan total]
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/**
			 * Step 17 - Verify Interest Rate
			 */
			requestLoan.verifyInterestRateInLoanSummaryTable();
			
			/**
			 * Step 18 - Validate tooltip for Interest rate
			 */
			requestLoan.validateToolTipForInterestRate();
			
			/**
			 * Step 19 - Verify FEES value
			 */
			requestLoan.verifyOriginationFeeDisplayed();
			
			/**
			 * Step 20 to 21 - Validate tool tip for Fee
			 */
			requestLoan.validateToolTipForFee();
						
			/**
			 * Step 24 - Verify the verbiage at the bottom of the page informing about the Maintenance Fee
			 */
	//		requestLoan.isTextFieldDisplayed("* Excludes quarterly $7.50 maintenance fee.");
			
			/**
			 * Step 25 - Change the Repayment Term option say for e.g. 24 months by
			 * clicking on the respective radio button present in the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("newLoanTermValid"));
			Web.waitForPageToLoad(Web.getDriver());
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31668_Verify_Loans_Landing_Page_After_Loan_Refinance_Request_Is_Completed(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 3 - Verify the Loans landing page for amount available to borrow & no. of active loans.
			 */
			requestLoan.verifyLoansLandingPage();
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 4 - Verify the Total outstanding balance of the one active loan after refinance .
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.selectRefinanceExistingLoanButton();
			requestLoan.verifyOutstandingBalance();
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31589_Verifying_Loan_Refinance_Page_GP_Loan_Flow_And_Participant_Has_Exhausted_All_The_Loans(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4 to 5 - Verify the Loans landing page for amount available to borrow & no. of active loans.
			 */
			requestLoan.verifyLoansLandingPage();
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7 - Click on 'Request a General purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			/**
			 * Step 8 - verify Loan refinance page
			 */
			requestLoan.verifyLoanRefinancePage();
			
			/**
			 * Step 9 - Verify the informational message at the top of the  active loan details table
			 */
			requestLoan.verifyInformationalMessageForExhaustedloans();
			
			/**
			 * Step 10 - Verify the 'Continue' and back buttons
			 */
			requestLoan.verifyBackButtonIsEnabled(true);
			
			/**
			 * Step 11 & 12 - Verify the Active Loan details section
			 */
			requestLoan.verifyActiveLoansDetailsHeaderSectionForRefinance();
			
			/**
			 * Step 13 - Select the checkboxes 
			 * Step 14 - Verify the multiple selection of the checkboxes
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31631_Refinance_An_Active_General_Purpose_Loan_With_Participant_Having_Option_To_Refinance_Existing_Loan_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a General purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			/**
			 * Step 4 - Verify the informational message at the top of the  active loan details table
			 */
			requestLoan.verifyInformationalMessageForExhaustedloans();
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			/**
			 * Step 9 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 11 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 12 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31630_Refinance_Multiple_Active_General_Purpose_Loan_With_Participant_Having_Option_To__Either_Request_A_NewLoan_Or_Refinance_Existing_Loan_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Click on 'Request a new Loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a General purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			requestLoan.verifyLoanRefinancePage();
			
			/**
			 * Step 4 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 5 - Choose one or more active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 9 - Verify the LOAN TOTAL value in Loan Summary
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 10 - Click on 'Continue' button with delivery method selected as 'Expedited  mail'
			 */			
			requestLoan.clickExpeditedMailDeliveryOption();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 11 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 12 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 13 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 14 - Verify the General Purpose Loan Refinance request confirmation page
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31632_Refinance_Multiple_Active_General_Purpose_Loan_With_Participant_Having_Option_To_Refinance_Existing_Loan_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a General purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 5 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 6 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyDefaultRepamentTermForLoanRefinance();
			
			/**
			 * Step 8 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			/**
			 * Step 9 - Click on 'Continue' button with delivery method selected as 'Regular mail'
			 */			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 11 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 12 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31590_Verifying_Loan_Refinance_Page_For_Principal_Residence_Loan_Flow_Participant_Has_Not_Exhausted_All_Loans(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7 - Click on 'Request a Princiapal Residence Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Click on 'Continue' button
			 */
			requestLoan.clickContinueButton();
			
			/**
			 * Step 9 - Verify the Loan Refinance page
			 */
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			requestLoan.verifyLoanRefinancePage();			
			
			/**
			 * Step 10 - Verify the selection & 'Continue' button
			 */
			requestLoan.verifyNewLoanRefinanceisSelected();
			requestLoan.verifyContinueButtonIsEnabled(true);
			
			/**
			 * Step 11 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 12 - Verify the 'Continue' button
			 */
			requestLoan.verifyContinueButtonIsDisplayed(false);
			
			/**
			 * Step 13 - Verify the Active Loan details section
			 */
			requestLoan.verifyActiveLoansDetailsSectionForRefinance(Stock.GetParameterValue("ActiveLoansHeaders"));
			
			/**
			 * Step 14 - Verify the table headers and its values
			 */
			requestLoan.verifyActiveLoansDetailsHeaderSectionForRefinance();
			
			/**
			 * Step 15 - Select the Checkbox(es)
			 * 'Continue' button should be presented to the user to proceed with the Refinance flow.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			
			/**
			 * Step 16 - Verify the multiple selection of the checkboxes
			 * Participant should be able to select multiple check-boxes & proceed to Refinance flow.
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31591_Verifying_Loan_Refinance_Page_For_Principal_Residence_Loan_Flow_Participant_Has_Exhausted_All_Loans(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 6 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			
			/**
			 * Step 7 - Click on 'Request a Princiapal Residence Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Click on 'Continue' button
			 */
			requestLoan.clickContinueButton();
			
			/**
			 * Step 9 - Verify the Loan Refinance page
			 */
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			requestLoan.verifyLoanRefinancePage();			
			
			/**
			 * Step 10 - Verify the informational message at the top of the  active loan details table
			 */
			requestLoan.verifyInformationalMessageForExhaustedloans();
			
			/**
			 * Step 11 - Verify the 'back' & 'Continue' button
			 */
			requestLoan.verifyBackButtonIsDisplayed(true);
			requestLoan.verifyContinueButtonIsDisplayed(false);
			
			/**
			 * Step 12 - Verify the Active Loan details section
			 */
			requestLoan.verifyActiveLoansDetailsSectionForRefinancePR();
			
			/**
			 * Step 13 - Verify the table headers and its values
			 */
			requestLoan.verifyActiveLoansDetailsHeaderSectionForRefinance();
			
			/**
			 * Step 14 - Select the Checkbox(es)
			 * 'Continue' button should be presented to the user to proceed with the Refinance flow.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			
			/**
			 * Step 15 - Verify the multiple selection of the checkboxes
			 * Participant should be able to select multiple check-boxes & proceed to Refinance flow.
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31618_Loan_Refinance_Verify_Loan_Quotes_Page_Princiapal_Residence_Loan_Refinance_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - verify number of active loans in this plan
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 3 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 4 - Click on 'Request a general purpose loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 5 - Click on 'Continue' button
			 */
			requestLoan.clickContinueButton();
			
			/**
			 * Step 6 - Select the radio button 'Refinance existing loan'
			 */
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 7 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/**
			 * Step 8 - Verify the Available to Borrow amount
			 * It must be 50% of the vested balance minus the highest outstanding loan balance in last 12 months.
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 9 - Verify the section beside the 'Available to Borrow'
			 * Label should be: ENTER AMOUNT TO ADD TO YOUR CURRENT LOAN
			 */
			requestLoan.isTextFieldDisplayed("ENTER AMOUNT TO ADD TO YOUR CURRENT LOAN");
						
			/**
			 * Step 10 - Verify that current active loan balance is displayed
			 */
			requestLoan.verifyCurrentActiveLoanBalanceIsDisplayed();
			
			/**
			 * Step 11 - In step 6, if two or more active loans(s) are selected for refinancing
			 */
			//TODO
			
			/**
			 * Step 12 - Hover the mouse on 'Loan outstanding balance'
			 */
			requestLoan.validateToolTipForLoanOutstandingBalance();
			
			/**
			 * Step 13 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 14 - Verify the Repayment Term options/values section table
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 15 - Click on 'Enter Your own loan Term' and verify the functionality 
			 */
			requestLoan.clickOnEnterYourOwnLoanTerm(Stock.GetParameterValue("newLoanTermValid"));
			requestLoan.verifyRefinanceRepaymentTermAfterAddingNewTerm(Stock.GetParameterValue("newLoanTermValid"));
			
			/**
			 * Step 16 - Select any Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 17 - Verify the 'My Loan Summary' 
			 * Step 24 & 24 [Verify Check Total and Loan total]
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			
			/**
			 * Step 18 - Verify Interest Rate
			 */
			requestLoan.verifyInterestRateInLoanSummaryTable();
			
			/**
			 * Step 19 - Validate tooltip for Interest rate
			 */
			requestLoan.validateToolTipForInterestRate();
			
			/**
			 * Step 20 - Verify FEES* value
			 */
			requestLoan.verifyOriginationFeeDisplayed();
			
			/**
			 * Step 21 to 22 - Hover the mouse on the 'FEES' value
			 * Validate tool tip for Fee
			 */
			requestLoan.validateToolTipForFee();
						
			/**
			 * Step 25 - Verify the verbiage at the bottom of the page informing about the Maintenance Fee
			 */
	//		requestLoan.isTextFieldDisplayed("* Excludes quarterly $7.50 maintenance fee.");
			
			/**
			 * Step 26 - Change the Repayment Term option say for e.g. 24 months by
			 * clicking on the respective radio button present in the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("newLoanTermValid"));
			Web.waitForPageToLoad(Web.getDriver());
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31633_Refinance_An_Active_Principal_Residence_Loan_With_Participant_Having_Option_To_Either_Request_A_New_Loan_Or_Refinancing_Existing_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a Principal Residence Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.clickContinueButton();
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			
			/**
			 * Step 5 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 6 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 * Participant should be navigated to Loan Quotes page
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 7 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 9 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 10 - Verify the 'My loan summary' section information
			 * It must have the below details iin the form of a table
			 * INTEREST RATE
			 * FEES*
			 * CHECK TOTAL
			 * LOAN TOTAL
			 * Click 'Continue' button
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyRegularMailSelectedAsDefault();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 12 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 13 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 14 - Verify the General Purpose Loan Refinance request confirmation page
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31634_Refinance_Multiple_Active_Principal_Residence_Loans_With_Participant_Having_Option_To_Either_Request_A_NewLoan_Or_Refinance_Existing_Loan(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Click on 'Request a new Loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a Principal Residence Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.clickContinueButton();
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			requestLoan.verifyLoanRefinancePage();
			
			/**
			 * Step 5 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 6 - Choose one or more active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 7 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 9 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 10 - Verify the LOAN TOTAL value in Loan Summary
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 11 - Click on 'Continue' button with delivery method selected as 'Expedited  mail'
			 */			
			requestLoan.clickExpeditedMailDeliveryOption();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 12 - Click Continue button
			 *//*
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			*/
			/**
			 * Step 13 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 14 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 15 - Verify the Principal Residence Loan Refinance request confirmation page
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_31635_Refinance_An_Active_Principal_Residence_Loan_With_Participant_Having_Option_To_Refinance_Existing_Loan_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a General purpose Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.clickContinueButton();
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4 - Click on 'Continue' button.
			 */
			
			
			/**
			 * Step 5 - Verify the informational message at the top of the  active loan details table
			 */
			requestLoan.verifyInformationalMessageForExhaustedloans();
			
			/**
			 * Step 6 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 7 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 9 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			/**
			 * Step 10 - Click on 'Continue' button with delivery method selected as 'ACH'
			 */			
			requestLoan.clickElectronicallyTransferFundsACHOption();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 11 - Click Continue button
			 *//*
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());*/
			
			/**
			 * Step 12 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
	//		requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 13 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 14 - Verify the Principal Residence Loan Refinance request confirmation page
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
	
	@Test(dataProvider = "setData")
	public void DDTC_31636_Refinance_Multiple_Active_Principal_Residence_Loans_With_Participant_Having_Option_To_Refinance_Existing_Loan_Only(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
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
			 * Step 2 - Click on 'Request a new loan'
			 */
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 3 - Click on 'Request a Princiapl Residence Loan'
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 4 - Click on 'Continue' button.
			 */
			
			
			/**
			 * Step 5 - Select the radio button 'Refinance existing loan'
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			
			/**
			 * Step 6 - Choose one active loan Checkbox for refinance and click on 'Continue' button.
			 */
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			
			/**
			 * Step 7 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Verify the Repayment Term options/values section table
			 * It should have 3 columns with below headers 
			 * i) SELECT ii) <Repayment Frequency Value>(Dynamic value) iii) REPAYMENT TERM
			 * Loan 'Repayment Term' options should be offered to the participant
			 */
			requestLoan.verifyRefinanceRepaymentTerm(Stock.GetParameterValue("repaymentTerm"));
			
			/**
			 * Step 9 - Select any of the Repayment term available
			 * Delivery Method options & 'My loan summary' sections should be displayed
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 10 - Verify the LOAN TOTAL value in Loan Summary
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.verifyCheckTotalAmountForRefinance();
			requestLoan.verifyLoanTotalAmountLoanRefinance();
			
			/**
			 * Step 10 - Click on 'Continue' button with delivery method selected as 'ACH'
			 *//*
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			*/
			/**
			 * Step 11 - Click Continue button with delivery method selected as 'Expedited Mail'
			 */
			requestLoan.clickExpeditedMailDeliveryOption();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 12 - Click on 'Continue' button.
			 */
			requestLoan.clickContinueButton();
			
			/**
			 * Step 13 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
//			requestLoan.verifyLoanProvisionLink();
			
			/**
			 * Step 14 - In Loan Review page, click on 'I agree and submit'
			 * Participant should be able to submit the General Purpose Loan Refinance request successfully & 
			 * Loan Confirmation page should be displayed
			 */
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 15 - Verify the Principal Residence Loan Refinance request confirmation page
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
	
	@Test(dataProvider = "setData")
	public void DDTC_32466_Loan_Refinance_Future_Dated_Principal_Resedence_Loan_Refinance_Request_by_Participant_With_Recent_Address_Change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
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
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			
			/**
			 * Step 5
			 */
			requestLoan.clickLoanReasonOptionFutureDate();
			
			/**
			 * Step 6 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * 
			 */
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			/**
			 * Step 7 - Click on 'Request a Principal Residence loan' button.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 8 - Click on 'Continue' button.
			 */
			requestLoan.clickContinueButton();
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			/**
			 * Step 9 - Select the active loan(s) for refinance & click on Continue button
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 11 - Select Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 12 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 13 - Verify the 'My Loan Summary' is as expected
			 * click on 'Continue' with Delivery method option selected as 'First-class mail'
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 14 - Verify the address, e-mail address & phone number
			 * Click Continue button
			 */
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());	
			
			/**
			 * Step 15 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 16 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_32467_Loan_Refinance_Pre_Filled_form_Delivery_for_Principal_Residence_Loan_Refinance_Request_by_Participant_With_Recent_Address_Change(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
			 * and login Navigate to My Accounts -> Loans & Withdrawals -> Loans
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
			 */
			requestLoan.verifyAddressChangeSectionInLaonPage();
			
			/**
			 * Step 5
			 */
			requestLoan.clickLoanReasonOptionPreFilled();
			
			/**
			 * Step 6 - Verify and validate details displayed on Loans landing page and click on 'Request a new loan'
			 * 
			 */
			requestLoan.verifyLoanRequestTypePage();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			
			/**
			 * Step 7 - Select 'Request a Principal Residence loan' button.
			 * Ppt must be  navigated to 'Principal Residence Loan Requirements' page.
			 * Step 8 - Click on 'Continue' button.
			 */
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.waitForPageToLoad(Web.getDriver());
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			if(requestLoan.verifyLoanRefinancingPageLoad()){
				Reporter.logEvent(Status.PASS, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is Displayed on selecting General Purpose Loan", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verifying participant navigated to Loan Refinance Page on selecting General Purpose Loan", 
						"Loan Refinance Page with option to Refinance existing loan is not Displayed", true);				
			}
			
			
			/**
			 * Step 9 - Select the active loan(s) for refinance & click on Continue button
			 */
			requestLoan.selectRefinanceExistingLoanButton();
			requestLoan.selectActiveLoanCheckBox(Integer.valueOf(Stock.GetParameterValue("NoOfLoansToBeChecked")));
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 10 - Enter the refinance amount in Enter Amount field & click 'Continue' button.
			 * Repayment Term table should be generated for the 
			 * Outstanding loan amount of current active loan(s)selected + Refinance amount entered
			 */
			Thread.sleep(3000);
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));	
			requestLoan.clickUpdateButton();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 11 - Select Repayment Term option say for e.g. 36 months
			 * by clicking on the respective radio button present the Repayment Term row.
			 */
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			/**
			 * Step 12 - Verify the 'Delivery Options' offered for ppt without ACH set up
			 */
			requestLoan.verifyMailDeliveryOptionsWithoutACH();
			
			/**
			 * Step 13 - Verify the 'My Loan Summary' is as expected
			 * click on 'Continue' with Delivery method option selected as 'First-class mail'
			 */
			requestLoan.verifyMyLoanSummarySectionForLoanRefinance();
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			/**
			 * Step 14 - Click Continue button
			 */
			
			requestLoan.clickContinueButton();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			/**
			 * Step 15 - Verify the Loan Review page
			 * LOAN DETAILS
			 * PAYMENT INFORMATION
			 * FEES & TAXES
			 * DELIVERY INFORMATION
			 * LOAN PROVISIONS
			 */
			requestLoan.verifyLoanSummarypage();
			
			
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();
			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();
			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			
			
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			/**
			 * Step 16 - Click 'I agree & submit' button
			 */
			Web.isWebEementEnabled(requestLoan, "I AGREE AND SUBMIT", true);
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			/**
			 * Step 17 - Verify the General Purpose Loan Refinance request confirmation page
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_27811_Loans_Frequency_Restriction_Participant_Restricted_With_2_Loan_Requests_Annually(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
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
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify the "Number of active loans in this plan" below Summary section
			 * Step 6 - Verify "Number of loans allowed"
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 7 - Click on 'Request a new loan"
			 * Participant should not be allowed to borrow another loan.
			 * Message indicating "Only 2 loans are allowed annually and you have already 2 active loans" should be displayed.
			 */
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyFrequencyRestrictionMsg();
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_27814_Loans_Frequency_Restriction_Participant_Restricted_With_2_Loan_Requests_Per_Calendar_Year(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
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
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify the "Number of active loans in this plan" below Summary section
			 * Step 6 - Verify "Number of loans allowed"
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 7 - Click on 'Request a new loan"
			 * Participant should not be allowed to borrow another loan.
			 * Message indicating "Only 2 loans are allowed annually and you have already 2 active loans" should be displayed.
			 */
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyFrequencyRestrictionMsg();
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_27815_Loans_Frequency_Restriction_Participant_Restricted_With_2_Loan_Requests_Per_Plan_Year(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * Step 1 to 3  Launch Browser and enter URL: Enter Valid credentials
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
			 * Step 4 - Verify and validate details displayed on Loans landing page
			 */
			requestLoan.verifyLoansLandingPage();
			
			/**
			 * Step 5 - Verify the "Number of active loans in this plan" below Summary section
			 * Step 6 - Verify "Number of loans allowed"
			 */
			requestLoan.verifyActiveLoansForparticipant(Stock.GetParameterValue("ga_id"));
			
			/**
			 * Step 7 - Click on 'Request a new loan"
			 * Participant should not be allowed to borrow another loan.
			 * Message indicating "Only 2 loans are allowed annually and you have already 2 active loans" should be displayed.
			 */
			requestLoan.clickOnRequestANewLoan();
			
			requestLoan.verifyFrequencyRestrictionMsg();
			
			
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


