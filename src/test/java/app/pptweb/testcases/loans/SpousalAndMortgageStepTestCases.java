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
			requestLoan.verifyLoanDetailsSectionConfirmationPageForLoanRefinance();			
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
}
