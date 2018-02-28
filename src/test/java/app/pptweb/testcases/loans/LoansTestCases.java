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
			requestLoan.verifyMaximumLoansForLoan(Stock.GetParameterValue("ga_id"));
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
			requestLoan.verifyMaximumLoansForLoan(Stock.GetParameterValue("ga_id"));
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
			requestLoan.verifySelectColumnForLoanTerm();
			//Step 19 and 20
			requestLoan.selectLoanTerm(Stock.GetParameterValue("loanTerm"));
			Web.waitForElement(requestLoan, "BUTTON CONTINUE");
			requestLoan.isTextFieldDisplayed("My loan summary");
			//Step 21
			requestLoan.isTextFieldDisplayed("How would you like your funds delivered?");
			//Step 22
			
			
			
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
			requestLoan.verifyMaximumLoansForLoan(Stock.GetParameterValue("ga_id"));
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
}