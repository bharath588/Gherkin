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

import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;

public class SpousalAndMortgageStepTestCases {

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
	 * @author bbndsh
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_33236_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_MORTGAGE_Step_In_TDL(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDB"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPage();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDB"));
			
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
	public void DDTC_33237_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_MORTGAGE_Step_Not_Present_In_TDL(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDB"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33249_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_MORTGAGE_Step_Present_In_TDL_Mortgage_PaperWork_Review_Indicator_N(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDB"));
			requestLoan.setMortgagePaperWorkIndicator(Stock.GetParameterValue("Mortgage Paperwork Indicator"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDB"));
			
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
	public void DDTC_33252_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Spousal_MORTGAGE_Steps_In_TDL_QJSA_False_SpousalConsent_True(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			//QJSA = FALSE & Spousal Consent = TRUE
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33258_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Spousal_MORTGAGE_Steps_In_TDL_QJSA_True(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			requestLoan.setMortgagePaperWorkIndicator(Stock.GetParameterValue("Mortgage Paperwork Indicator"), Stock.GetParameterValue("ga_id"));
			
			//QJSA = TRUE & Spousal Consent = TRUE
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33260_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Spousal_MORTGAGE_Steps_In_TDL_QJSA_True(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			requestLoan.setMortgagePaperWorkIndicator(Stock.GetParameterValue("Mortgage Paperwork Indicator"), Stock.GetParameterValue("ga_id"));
			
			//QJSA = TRUE & Spousal Consent = TRUE
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33261_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_MORTGAGE_Steps_In_TDL_QJSA_False_Requiring_Spousal_Consent(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			//QJSA = TRUE & Spousal Consent = TRUE
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33262_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_SPOUSAL_Steps_In_TDL_QJSA_False_Requiring_Spousal_Consent(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			//QJSA = TRUE & Spousal Consent = TRUE
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33263_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_SPOUSAL_Steps_In_TDL_QJSA_False_Spousal_Consent_Not_Required(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			//TODO
			//Spousal Money Type does not exists in loan amount
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33264_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_SPOUSAL_Steps_In_TDL_QJSA_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			//TODO
			//Spousal Money Type does not exists in loan amount
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
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
	public void DDTC_33299_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_SPOUSAL_Mortgage_Steps_NOT_In_TDL_QJSA_False_Spousal_Consent_Required(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33265_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_No_SPOUSAL_Mortgage_Steps_NOT_In_TDL_QJSA_False_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33266_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_No_SPOUSAL_Mortgage_Steps_NOT_In_TDL_QJSA_False_Spousal_Consent_NotRequired(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33292_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_No_SPOUSAL_Mortgage_Steps_NOT_In_TDL_QJSA_False_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
						
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33293_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_OnlyMortgage_Steps_In_TDL_QJSA_False_Spousal_Consent_NotRequired(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
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
	public void DDTC_33294_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_Mortgage_Steps_In_TDL_QJSA_False_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			
			
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
	public void DDTC_33295_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_Mortgage_Steps_In_TDL_Both_QJSA_And_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			
			
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
	public void DDTC_33296_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Only_Spousal_And_Mortgage_Steps_In_TDL_QJSA_False_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			
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
	public void DDTC_33297_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Spousal_And_Mortgage_Steps_In_TDL_QJSA_False_Spousal_Consent_NotRequired(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			
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
	public void DDTC_33298_Spousal_And_Mortgage_Request_Principal_Residence_Loan_With_Spousal_And_Mortgage_Steps_In_TDL_QJSA_And_Spousal_Consent_False(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr,Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan = new RequestLoanPage(leftmenu);
			requestLoan.get();
			
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.setMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			requestLoan.setQJSAandSpousalConsentInDatabase(Stock.GetParameterValue("QJSA_Ind"), Stock.GetParameterValue("Spousal_Consent_Ind"),Stock.GetParameterValue("gc_id"));
			requestLoan.setRULE_IDInDatabase(Stock.GetParameterValue("rule_id"), Stock.GetParameterValue("ga_id"));
			//TODO
			
			requestLoan.clickOnRequestANewLoan();
			requestLoan.setOriginationFee(Stock.GetParameterValue("loanTypeForFee"));
			requestLoan.setInterestRate(requestLoan.getInterestRateFromRequestLoanPage(Stock.GetParameterValue("loanType")));
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.clickContinueButton();
			
			requestLoan.EnterLoanAmount(Stock.GetParameterValue("loanAmount"));
			requestLoan.clickContinueButton();
			Common.waitForProgressBar();
			
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			requestLoan.setpaymentAmount(Stock.GetParameterValue("loanTerm"));
			
			requestLoan.clickContinueButton();
			
			try{
				requestLoan.isTextFieldDisplayed("ProActive Notification Screen");
				requestLoan.clickContinueButton();
			}catch(Exception e){
				lib.Reporter.logEvent(Status.INFO, "Verifying Contact Verification page is displayed", 
							"Contact Verification Page not displayed/ProActive Notification is not Enabled", false);
			}
			
			requestLoan.verifyLoanSummarypage();
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
			requestLoan.verifyPaymentInformationSectionInConfirmationPage();			
			requestLoan.verifyFeesAndTaxesSectionInConfirmationPage();
			requestLoan.verifyDeliveryInformationSectionInConfirmationPage();
			
			requestLoan.clickOnIAgreeAndSubmit();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			requestLoan.verifyLoanRequestRecievedSectionForRegularMail();
			
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBMort"));
			requestLoan.ResetMortgageOrSpousalStepInDatabase(Stock.GetParameterValue("StepToBeSetInDBSpoc"));
			
			
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
