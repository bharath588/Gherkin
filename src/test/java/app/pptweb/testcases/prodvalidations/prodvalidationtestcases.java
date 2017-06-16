package app.pptweb.testcases.prodvalidations;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.balance.Balance;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.deferrals.Deferrals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.general.RateOfReturnPage;
import pageobjects.investment.Brokerage;
import pageobjects.investment.InvestmentLineup;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.landingpage.LegacyLandingPage;
import pageobjects.liat.HealthCareCosts;
import pageobjects.liat.HowDoICompare;
import pageobjects.liat.ProfilePage;
import pageobjects.liat.RetirementIncome;
import pageobjects.loan.RequestLonePage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.payroll.PayrollCalendar;
import pageobjects.planinformation.PlanForms;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import pageobjects.transactionhistory.TransactionHistory;
import pageobjects.withdrawals.RequestWithdrawal;
import appUtils.Common;
import core.framework.Globals;

public class prodvalidationtestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	static String printTestData="";

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

	@Test(dataProvider = "setData")
	public void SF01_TC01_Verify_Custom_site_prelogin_data(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter
			.logEvent(
					Status.INFO,
					"Test Data used for this Test Case:",
					printTestData(),
					false);
			LoginPage login = new LoginPage();
			login.get();

			String copyRightInfo = "";
			boolean isLogoDisplayed = false;
			boolean isBannerDisplayed = false;
			boolean isTextMatching = false;
			isLogoDisplayed = login.isSponsorLogoDisplayed(Stock
					.GetParameterValue("logoName"));
			if (isLogoDisplayed) {
				lib.Reporter.logEvent(Status.PASS,
						"Sponsor Logo Displayed on the Login Page",
						"Sponsor Logo is Same on the Login Page", true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Sponsor Logo Displayed on the Login Page",
								"Sponsor Logo is not Displayed on the Login Page",
								true);
			}
			isBannerDisplayed = login.isSponsorBannerDisplayed();
			if (isBannerDisplayed) {
				lib.Reporter.logEvent(Status.PASS,
						"Sponsor Banner Displayed on the Login Page",
						"Sponsor Banner is Same on the Login Page", false);

			} else {
				lib.Reporter.logEvent(Status.FAIL,
						"Sponsor Banner Displayed on the Login Page",
						"Sponsor Banner is not Displayed on the Login Page",
						false);
			}
			copyRightInfo = login.getWebElementText("copyright info");
			isTextMatching = Web.VerifyText(
					Stock.GetParameterValue("copyright info"), copyRightInfo,
					true);

			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check CopyRight Information on the Login Page",
								"CopyRight Informatio is Same on the Login Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check CopyRight Information on the Login Page",
								"CopyRight Informatio is Not Same on the Login Page",
								false);
			}
			if (!Common.getSponser().equalsIgnoreCase("Apple")) {
				login.verifyWebElementDisplayed("image participant Savings rates");
				login.verifyWebElementDisplayed("image participant Rollover options");
				login.verifyWebElementDisplayed("image participant Browser Support");
			}
			login.verifyWebElementDisplayed(/* "System Requirements and Security" */"Requirements and Security");
			login.verifyWebElementDisplayed("Privacy");
			login.verifyWebElementDisplayed(/* "Terms and Conditions" */"Terms");
			login.verifyWebElementDisplayed(/* "Business Continuity Plan" */"Business Continuity");
			login.verifyWebElementDisplayed(/* "Market Timing and Excessive Trading Policies" */"Market Timing and Excessive Trading");
			login.verifyWebElementDisplayed("BrokerCheck Notification");
			login.verifyWebElementDisplayed("FINRA Investor Education");
			login.verifyLinkIsNotBroken("Requirements and Security");
			login.verifyLinkIsNotBroken("Privacy");
			login.verifyLinkIsNotBroken("Terms");
			login.verifyLinkIsNotBroken("Business Continuity");
			login.verifyLinkIsNotBroken("Market Timing and Excessive Trading");
			login.verifyLinkIsNotBroken("FINRA Investor Education");
			boolean windowFound = false;
			String parentWindow = Web.getDriver().getWindowHandle();
			Web.clickOnElement(login, "BrokerCheck Notification");
			Thread.sleep(7000);
			Set<String> handles = Web.getDriver().getWindowHandles();
			for (String windowHandle : handles) {

				if (!windowHandle.equals(parentWindow)) {
					if (Web.getDriver().switchTo().window(windowHandle)
							.getTitle().contains("BrokerCheck")) {
						windowFound = true;
						break;
					}
				}
			}
			if (windowFound) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verifying Broker Check Notification is Opened in New Window",
								"Broker Check Notification is Opened in New Window",
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Verifying Broker Check Notification is Opened in New Window",
								"Broker Check Notification is Not Opened Properly in New Window",
								true);
			}

			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
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

		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			    Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC03_Verify_Custom_site_prelogin_postlogin_data(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			login.get();
			if(Web.isWebElementDisplayed(login, "DISMISS"))
				Web.clickOnElement(login, "DISMISS");
			String customerSupportInfo = "";
			boolean isTextMatching = false;
			boolean isContactNoMatching = false;
			customerSupportInfo = login.isValidCustomerSupportInfo();

			if (customerSupportInfo.trim().isEmpty()) {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Customer Support Information on the Login Page",
						"No Customer Support Information on the Login Page",
						true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information on the Login Page is displayed ",
								true);
			}

			isTextMatching = Web
					.VerifyText(
							Stock.GetParameterValue("ExpectedCustomerSupportInfo_Pre_Login"),
							customerSupportInfo, true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information is Same on the Login Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information is not same on the Login Page\nExpected:"
										+ Stock.GetParameterValue("ExpectedCustomerSupportInfo_Pre_Login")
										+ "\nActual:" + customerSupportInfo,
								false);
			}
			isContactNoMatching = login.isValidContactUsInfo(Stock
					.GetParameterValue("ExpectedContactNo_Pre_login"));
			if (isContactNoMatching) {
				lib.Reporter.logEvent(Status.PASS,
						"Check Contact Us Information on the Login Page",
						"Contact Us Information is Same on the Login Page",
						true);

			} else {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Contact Us Information on the Login Page",
						"Contact Us Information is not same on the Login Page",
						true);
			}
			List<String> telePhoneNo = login.getPreLoginTelePhoneNo(Stock
					.GetParameterValue("Sponsor"));
			Web.clickOnElement(login, "dismiss");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();

			customerSupportInfo = login.isValidCustomerSupportInfo();

			if (customerSupportInfo.trim().isEmpty()) {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Customer Support Information on the Home Page",
						"No Customer Support Information on the Home Page",
						true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information on the Home Page is displayed ",
								true);
			}
			isTextMatching = Web.VerifyText(
					Stock.GetParameterValue(
							"ExpectedCustomerSupportInfo_Post_Login")
							.toString().trim(), customerSupportInfo, true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information is Same on the Home Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information is not Same on the Home Page. Expected/ "
										+ Stock.GetParameterValue("ExpectedCustomerSupportInfo_Post_Login")
										+ " Actual/ " + customerSupportInfo,
								false);
			}

			isContactNoMatching = login
					.isValidContactUsInfoPostLogin(telePhoneNo);
			if (isContactNoMatching) {
				lib.Reporter.logEvent(Status.PASS,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is Same on the Home Page",
						false);

			} else {
				lib.Reporter.logEvent(Status.INFO,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is not Same on the Home Page",
						false);
			}

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
	public void SF01_TC04_Verify_Participant_Profile_Page_Data(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			boolean isDisplayed = false;

			isDisplayed = profilePage.validateUserProfileInfo();
			if (isDisplayed) {
				Reporter.logEvent(
						Status.INFO,
						"Verify All Of the Fields in User Profile is Displayed",
						" Info in User Profile Page is Displayed", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify All Of the Fields in User Profile is Displayed",
						"Info User Profile page is not Proper", false);
			}
			Web.clickOnElement(profilePage, "HOME");
			Common.waitForProgressBar();
			/*Web.getDriver().navigate().back();
			Web.getDriver().navigate().refresh();
			if(Stock.getConfigParam("BROWSER").equalsIgnoreCase("CHROME")){
			Web.waitForElement(profilePage, "LOG OUT");
			homePage.dismissPopUps(false, true);
			//}
			Web.waitForElement(profilePage, "LOG OUT");
			if(Stock.getConfigParam("BROWSER").equalsIgnoreCase("FIREFOX"))
			Web.clickOnElement(profilePage, "LOG OUT");*/
			Web.waitForElement(profilePage, "LOG OUT");
			Web.clickOnElement(profilePage, "LOG OUT");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
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
	public void SF01_TC05_Verify_RPS_PSAP_Setup_Dummy_TestPlan_link(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			myAccountPage.clickPlanNameByGAID(Stock
					.GetParameterValue("groupId"));
			boolean lblDisplayed = false;

			lblDisplayed = Web.isWebElementDisplayed(myAccountPage,
					"Account Overview", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Account Overview Lable verification",
						"Account Overview is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Account Overview Lable verification",
						"Account Overview is NOT visible", false);
			}
			lblDisplayed = Web.VerifyPartialText(
					Stock.GetParameterValue("groupId"),
					Web.getDriver().getCurrentUrl(), false);
			if (lblDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Account Overview Page is Loaded with the same plan",
						"Account Overview page is loaded with same plan", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Account Overview Page is Loaded with the same plan",
						"Account Overview page is not loaded with same plan",
						true);
			}
			lblDisplayed = Web.isWebElementDisplayed(myAccountPage, "Graph",
					true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify Graph is Displayed",
						"Graph is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify Graph is Displayed",
						"Graph is NOT visible", true);
			}
			Web.clickOnElement(myAccountPage, "LOGOUT");
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
	public void SF01_TC016_Verify_LeftNavigation_Tab_Request_A_Loan_link(
			int itr, Map<String, String> testdata) {

		try {
			
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			/*	MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			if(Stock.getConfigParam("TEST_ENV").contains("QA")){
				 myAccountPage.get();
				 if(Web.isWebElementDisplayed(myAccountPage,
				  "PLAN NAME", true)) { myAccountPage.clickPlanNameByGAID("332125-01"); 
				
				  }
				}*/
			LeftNavigationBar lftBar = new LeftNavigationBar(homePage);
			RequestLonePage requestLone = new RequestLonePage(lftBar);
			requestLone.get();
			String parentWindow = Web.getDriver().getWindowHandle();
			for (String winHandle : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				Web.waitForPageToLoad(Web.getDriver());
			}

			boolean lblDisplayed = false;
//			int confirmationNumber = 0;
            Thread.sleep(5000);
			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"Request a loan", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Loan Page is Displayed",
						"Request A Loan Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Loan Page is Displayed",
						"Request A Loan Page is NOT visible", true);
			}
		
			
			requestLone.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.getDriver().switchTo().frame("legacyFeatureIframe");
			lblDisplayed = Web
					.VerifyPartialText(
							"Selected Loan Type:\nGeneral Purpose (Available for any purpose.)",
							requestLone.getWebElementText("TEXT LOAN TYPE"),
							true);
			if (lblDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Selected Loan Type is Displayed",
						"Selected Loan Type"
								+ Stock.GetParameterValue("loanType")
								+ " is visible", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Selected Loan Type is Displayed",
						"Selected Loan Type"
								+ Stock.GetParameterValue("loanType")
								+ " is Not visible", true);
			}
			Web.getDriver().switchTo().defaultContent();
			if(Stock.getConfigParam("TEST_ENV").toUpperCase().startsWith("PROJ")){
				requestLone.EnterLoanAmtAndTerm("$100", "12");
			}
			else
			requestLone.EnterLoanAmtAndTerm("$1000", "12");
			
			Web.getDriver().switchTo().frame("legacyFeatureIframe");
			lblDisplayed = Web.VerifyPartialText("Loan Term = 12 Months",
					requestLone.getWebElementText("TEXT LOAN TERM"), true);
			if (lblDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Loan Term is Displayed",
						"Loan Term is Displayed \n Expected:Loan Term = 12 Months \nActual:"
								+ requestLone.getWebElementText("TEXT LOAN TERM"),
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan Term is Displayed",
						"Loan Term is Not Displayed \n Expected:Loan Term = 12 Months \nActual:"
								+ requestLone.getWebElementText("TEXT LOAN TERM"),
						false);
			}
			requestLone.isTextFieldDisplayed("Loan Origination Fee:");
			requestLone.isTextFieldDisplayed("Check Amount:");
			requestLone.isTextFieldDisplayed("Loan Amount:");
			requestLone.isTextFieldDisplayed("Interest Rate:");
			requestLone.isTextFieldDisplayed("Annual Percentage Rate (APR):");
			requestLone.isTextFieldDisplayed("Payment Frequency:");
			requestLone.isTextFieldDisplayed("Payment Method:");
			requestLone.isTextFieldDisplayed("Payment Amount:");
			Web.clickOnElement(requestLone, "CONTINUE LOAN REQUEST");
			Web.getDriver().switchTo().defaultContent();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(10000);
			Web.getDriver().switchTo().frame("legacyFeatureIframe");
			lblDisplayed = requestLone
					.isTextFieldDisplayed("MAILING AND CONTACT INFORMATION:");

			if (lblDisplayed) {
				Reporter.logEvent(
						Status.INFO,
						"Verify MAILING AND CONTACT INFORMATION Page is Displayed",
						"MAILING AND CONTACT INFORMATION Page is Dispalyed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify MAILING AND CONTACT INFORMATION Page is Displayed",
						"MAILING AND CONTACT INFORMATION Page is Not Dispalyed",
						true);
			}
			requestLone.isTextFieldDisplayed("Home Phone:");
			requestLone.isTextFieldDisplayed("Mobile Phone:");
			requestLone.isTextFieldDisplayed("Work Phone/Ext:");
			requestLone.isTextFieldDisplayed("Email Address:");
			requestLone.isTextFieldDisplayed("Please review the above contact information and make any necessary changes before you continue.");
			Web.setTextToTextBox("INPUT HOME AREA CODE", requestLone, "123");
			Web.setTextToTextBox("INPUT HOME PREFIX", requestLone, "456");
			Web.setTextToTextBox("INPUT HOME SUFFIX", requestLone, "7890");
			Web.clickOnElement(requestLone, "CONTINUE LOAN REQUEST");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(requestLone, "CHECKBOX I ACCEPT");
			lblDisplayed = Web.VerifyText(
							"PLEASE VERIFY ALL INFORMATION AND CAREFULLY READ ALL TERMS OF THE LOAN PROMISSORY NOTE AND THE PLAN'S LOAN PROVISIONS BEFORE CLICKING \"I ACCEPT\".",
							requestLone.getWebElementText("TEXT VERIFY ALL INFO").trim(),
							true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Text PLEASE VERIFY ALL INFO..... is Displayed",
						"PLEASE VERIFY ALL INFO..... Text is Dispalyed", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Text PLEASE VERIFY ALL INFO..... is Displayed",
						"PLEASE VERIFY ALL INFO..... Text is Not Dispalyed Expected:"
								+ "PLEASE VERIFY ALL INFORMATION AND CAREFULLY READ ALL TERMS OF THE LOAN PROMISSORY NOTE AND THE PLAN'S LOAN PROVISIONS BEFORE CLICKING \"I ACCEPT\"."
								+ "\nActual:"
								+ requestLone.getWebElementText("TEXT VERIFY ALL INFO"),
						true);
			}
			lblDisplayed = Web
					.VerifyText(
							"Once you click \"I Accept\", you will initiate the loan described below and you are acknowledging that you accept the terms of the Loan Promissory Note and the Plan's Loan Provisions.",
							requestLone.getWebElementText("TEXT ONCE YOU CLICK"),
							true);

			if (lblDisplayed) {
				Reporter.logEvent(
						Status.INFO,
						"Verify Text ONCE YOU CLICK I ACCEPT..... is Displayed",
						"ONCE YOU CLICK I ACCEPT..... Text is Dispalyed", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Text ONCE YOU CLICK I ACCEPT..... is Displayed",
						"ONCE YOU CLICK I ACCEPT..... Text is Not Dispalyed Expected:"
								+ "Once you click \"I Accept\", you will initiate the loan described below and you are acknowledging that you accept the terms of the Loan Promissory Note and the Plan's Loan Provisions."
								+ "\nActual:"
								+ requestLone.getWebElementText("TEXT ONCE YOU CLICK"),
						false);
			}

			requestLone.isTextFieldDisplayed("Loan Information");
			requestLone.isTextFieldDisplayed("Loan Amount");
			requestLone.isTextFieldDisplayed("Total Interest Charge");
			requestLone.isTextFieldDisplayed("Total Principal and Interest Amount");
			requestLone.isTextFieldDisplayed("Interest Rate ");
			requestLone.isTextFieldDisplayed("Loan Type");
			requestLone.isTextFieldDisplayed("Loan Term");
			requestLone.isTextFieldDisplayed("Maturity Date");
			requestLone.isTextFieldDisplayed("Annual Percentage Rate (APR)");
			requestLone.isTextFieldDisplayed("Loan Payment Information");
			requestLone.isTextFieldDisplayed("First Payment Date");
			requestLone.isTextFieldDisplayed("Last Payment Date");
			requestLone.isTextFieldDisplayed("Number of Payments");
			requestLone.isTextFieldDisplayed("Payment Amount");
			requestLone.isTextFieldDisplayed("Payment Method");
			requestLone.isTextFieldDisplayed("Payment Frequency");
			requestLone.isTextFieldDisplayed("Loan Fees or Taxes");
			requestLone.isTextFieldDisplayed("Loan Origination Fee");
			requestLone.isTextFieldDisplayed("Loan Maintenance Fee");
			requestLone.isTextFieldDisplayed("Documentary Stamp Tax");
			requestLone.isTextFieldDisplayed("Express Mail Fee");
			requestLone.isTextFieldDisplayed("Plan Bank ACH Charge");
			requestLone.isTextFieldDisplayed("Loan Delivery Information.");
			requestLone.isTextFieldDisplayed("Check Amount");
			requestLone.isTextFieldDisplayed("Name");
			requestLone.isTextFieldDisplayed("Address");
			requestLone.isTextFieldDisplayed("City");
			requestLone.isTextFieldDisplayed("State");
			requestLone.isTextFieldDisplayed("Zip");
			requestLone.isTextFieldDisplayed("Country");
			requestLone.isTextFieldDisplayed("Express Mail Service");
			requestLone.isTextFieldDisplayed("I have read and agree to the Plan");
			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"CHECKBOX I ACCEPT", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify I Accept CheckBox is Displayed",
						"I ACCEPT CheckBox is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify I Accept CheckBox is Displayed",
						"I ACCEPT CheckBox is Not Displayed", false);
			}
			Web.clickOnElement(requestLone, "CHECKBOX I ACCEPT");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			lblDisplayed = Web.isWebElementDisplayed(requestLone, "I ACCEPT",
					true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify I Accept Button is Displayed",
						"I ACCEPT Button is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify I Accept Button is Displayed",
						"I ACCEPT Button is Not Displayed", false);
			}
			if (Stock.GetParameterValue("submitRequest").equalsIgnoreCase("YES")) {
				Web.clickOnElement(requestLone, "I ACCEPT");
				Thread.sleep(3000);
				lblDisplayed = Web.VerifyPartialText(
						"Your confirmation number is",
						requestLone.getWebElementText("TEXT CONFIRMATION"),
						true);
				if (lblDisplayed) {
					Reporter.logEvent(Status.INFO,
							"Verify RequestLoan Confirmation is Displayed",
							"RequestLoan Confirmation is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify RequestLoan Confirmation is Displayed",
							"RequestLoan Confirmation is Not Displayed", true);
				}
				if (Web.isWebElementDisplayed(requestLone,
						"TEXT CONFIRMATION NUMBER", true)) {
					lblDisplayed = Common.verifyStringIsInNumberFormat(requestLone.getWebElementText("TEXT CONFIRMATION NUMBER"));
					if (lblDisplayed) {
						Reporter.logEvent(
								Status.PASS,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation is in Number Format and \n Confirmation Number is:"
										+ requestLone.getWebElementText("TEXT CONFIRMATION NUMBER"),
								false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify Request Confirmation Number is Number Format",
								"Request Confirmation Number is  Not in Number Format"
										+ requestLone.getWebElementText("TEXT CONFIRMATION NUMBER"),
								true);
					}
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation Number is Displayed",
							"Request Confirmation Number is Not Displayed",
							true);
				}
			}
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
			Web.getDriver().switchTo().defaultContent();
			/*Web.clickOnElement(requestLone, "LOGOUT");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(login, "SIGN IN");*/
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
	public void SF01_TC018_Verify_ROR_link(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RateOfReturnPage ROR = new RateOfReturnPage(lftNavBar);
			ROR.get();
			boolean isVerified = false;
			isVerified = ROR.verifyDataInRateOfReturnPage();
			if (isVerified) {
				Reporter.logEvent(
						Status.PASS,
						" Verify Rate Of Return Page is Displayed with Proper Data",
						"Rate Of Return Page is Displayed with Proper Data",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						" Verify Rate Of Return Page is Displayed with Proper Data",
						"Rate Of Return Page is Not Displayed with Proper Data",
						true);
			}
			Web.getDriver().switchTo().defaultContent();
			//Web.clickOnElement(ROR, "LOGOUT");
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
	public void SF01_TC19_Verify_Legacy_Home_Page(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LegacyLandingPage homePage = new LegacyLandingPage(mfaPage);

			homePage.get();

			boolean lblDisplayed = false;
			
			//Thread.sleep(6000);
			/*Web.getDriver().navigate().refresh();
			Thread.sleep(6000);*/
			lblDisplayed = Web.isWebElementDisplayed(homePage, "USER NAME",
					true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO, "LEGACY HOME PAGE VERIFICATION",
						"User Name is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL, "LEGACY HOME PAGE VERIFICATION",
						"User Name is NOT visible", true);
			}
			lblDisplayed = Web.isWebElementDisplayed(homePage, "MY ACCOUNT",
					true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify My Accounts Link is Displayed",
						"My Accounts Link is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify My Accounts Link is Displayed",
						"My Accounts Link is Not Displayed", false);
			}
			lblDisplayed = Web.isWebElementDisplayed(homePage, "INVESTMENTS",
					true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify INVESTMENTS Link is Displayed",
						"INVESTMENTS Link is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify INVESTMENTS Link is Displayed",
						"INVESTMENTS Link is Not Displayed", false);
			}
			//Web.clickOnElement(homePage, "LOGOUT");
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
	public void Balance_validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			Balance balance = new Balance(leftmenu);
			balance.get();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(5000);
			
				if(!Web.isWebElementDisplayed(balance, "Balance",true)){
					Common.handlePageToLoad("Balance");
				}
				
			balance.navigateToTab("Balance");
			// not seeing this table in PRod data
			/*
			 * balance.verifyTableDisplayed("Balance by Money Table");
			 * balance.verifytableHeaderNotEmpty
			 * ("Balance by Money Table Header");
			 * balance.verifyTableDataDisplayed("Balance by Money Table");
			 */

			balance.verifyTableDisplayed("Balance by Investment Table");
			balance.verifytableHeaderNotEmpty("Balance by Investment Table Header");
			balance.verifyTableDataDisplayed("Balance by Investment Table");
			balance.verifyGraphDisplayed("High chart graph");

			balance.navigateToTab("Balance over time");
			balance.verifyTableDisplayed("Balance History Table");
			balance.verifytableHeaderNotEmpty("Balance History Table Header");
			balance.verifyTableDataDisplayed("Balance History Table");
			balance.verifyGraphDisplayed("Line graph");

			balance.navigateToTab("Balance comparison");
			balance.verifyTableDisplayed("Balance Comparison Table");
			balance.verifytableHeaderNotEmpty("Balance Comparison Table Header");
			balance.verifyTableDataDisplayed("Balance Comparison Table");
			balance.verifyGraphDisplayed("Balance Comparison Graph");
		//	Web.clickOnElement(balance, "LOGOUT");
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
	public void Transaction_History_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			TransactionHistory transaction = new TransactionHistory(leftmenu);

			transaction.get();
			Thread.sleep(5000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Common.switchToLegacyFutureFrame()){
				if(!Web.isWebElementDisplayed(transaction, "DropDown Frequency",true)){
					Common.handlePageToLoad("Transaction history");
				}
				}
				else
					Common.handlePageToLoad("Transaction history");
				
			transaction.selectDateFrequency("Three Years");
			Thread.sleep(5000);
			// transaction.clickConfirmationNumber();
			transaction.verifyTableDisplayed("Transaction Filter Option Table");
			transaction.clickConfirmationNumber();

			transaction.verifyTableDisplayed("Transaction History Contr Summary Table");
			transaction.verifytableHeaderNotEmpty("Transaction History Contr Summary Table Header");
			transaction.verifyTableDataDisplayed("Transaction History Contr Summary Table");

			transaction.verifyTableDisplayed("Transaction History Contr Detail Table");
			transaction.verifytableHeaderNotEmpty("Transaction History Contr Detail Table Header");
			transaction.verifyTableDataDisplayed("Transaction History Contr Detail Table");

			transaction.verifyReferenceNumber();
		//	 Web.clickOnElement(transaction, "LOGOUT");
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
	public void Statement_and_Documents_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			StatementsAndDocuments statements = new StatementsAndDocuments(
					leftmenu);

			statements.get();
			Thread.sleep(5000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			String parentWindow = Web.getDriver().getWindowHandle();
			for (String winHandle : Web.getDriver().getWindowHandles()) {
	       Web.getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
	       Web.waitForPageToLoad(Web.getDriver());
       }
			String parentWindow1 = Web.getDriver().getWindowHandle();
			/*if(Common.switchToLegacyFutureFrame()){
			if(!Web.isWebElementDisplayed(statements, "Stmts On Demand Tab",true)){
				Common.handlePageToLoad("Statements and documents");
			}
			}
			else
				Common.handlePageToLoad("Statements and documents");*/
					
			statements.navigateToTab("Stmts On Demand Tab");
			statements.selectDateFrequency("Two Years");
			Thread.sleep(5000);
			statements.verifyTableDisplayed("Account at a Glance Table");
			statements.verifytableHeaderNotEmpty("Account at a Glance Table Header");
			statements.verifyTableDataDisplayed("Account at a Glance Table");

			statements.verifyTableDisplayed("Activity by Contribution Source Table");
			statements.verifytableHeaderNotEmpty("Activity by Contribution Source Table Header");
			statements.verifyTableDataDisplayed("Activity by Contribution Source Table");

			statements.verifyTableDisplayed("Activity by Investment Option Table");
			statements.verifytableHeaderNotEmpty("Activity by Investment Option Table Header");
			statements.verifyTableDataDisplayed("Activity by Investment Option Table");

			statements.clickOnStatementFromTable("Activity by Contribution Source");
			statements.verifyTableDisplayed("Transaction Details Table");
			statements.verifytableHeaderNotEmpty("Transaction Details Table Header");
			statements. verifyTableDataDisplayed("Transaction Details Table");
			//closing child window
	   		 Web.getDriver().close();
	          //cntrl to parent window1
	      	  Web.getDriver().switchTo().window(parentWindow1);
	      	  //close parent window 1
	      	 Web.getDriver().close();
	      	 //cntrl to parent window
	      	 Web.getDriver().switchTo().window(parentWindow);
	      	Web.getDriver().switchTo().defaultContent();
			//statements.switchToWindow();

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
	public void Manage_My_Investment_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);

			investment.get();
			//Thread.sleep(5000);
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Rebalance Currnet Balance");
			Web.clickOnElement(investment, "Continue button1");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.rebalanceInvestment(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"),
					Stock.GetParameterValue("investment_percent"));
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			String date = dateFormat.format(cal.getTime()).toUpperCase();
			investment.verifyRebalanceInvestmentDetails(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"), date,
					Stock.GetParameterValue("investment_percent"));
			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				investment.verifyRebalanceInvestmentConfirmationDetails();
				investment.cancelTransfer("Rebalance Currnet Balance");
			}
			Web.getDriver().switchTo().defaultContent();
			 //Web.clickOnElement(investment, "LOGOUT");
			
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
	public void Payroll_Calendar_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PayrollCalendar payroll = new PayrollCalendar(leftmenu);
			payroll.get();
			
			//Thread.sleep(5000);
			payroll.verifyDataIsDiaplyed();
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
	public void Investment_lineup_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			InvestmentLineup investment = new InvestmentLineup(leftmenu);
			investment.get();
			
				if(!Web.isWebElementDisplayed(investment, "Options tab",true)){
					Common.handlePageToLoad("Investment lineup");
				}
				
			//Thread.sleep(5000);
			investment.viewProspectus();
		    Web.getDriver().switchTo().defaultContent();
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
	public void Edit_MyContribution(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferral = new Deferrals(leftmenu);
			deferral.get();
			//Thread.sleep(5000);
			if (deferral.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferral.click_Select_Your_Contribution_Rate();
			deferral.select_ContributionType("Before");
			lib.Web.clickOnElement(deferral, "Continue button");
			Thread.sleep(3000);
			if (deferral.verifyMyContributions(
					deferral.contrbution_rate, "Before Tax",
					"Standard"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Before contribution percent for Standard deferral",
						"Before contribution percent matching", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Before contribution percent for Standar deferral",
						"Before contribution percent matching", true);
			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				deferral.myContributions_Confirmation_Page();
				lib.Web.clickOnElement(deferral, "MyContribution Button");
			}
			Web.getDriver().switchTo().defaultContent();
			//lib.Web.clickOnElement(deferral, "LOG OUT");
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
	public void Brokerage_validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Brokerage brokerage = new Brokerage(leftmenu);
			brokerage.get();
			String parentWindow = Web.getDriver().getWindowHandle();
						for (String winHandle : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				Web.waitForPageToLoad(Web.getDriver());
			}
			//Thread.sleep(5000);
			brokerage.verifyBrokerageTableDisplayed();

			brokerage.verifyBrokerageTableDataDisplayed("Provider Name");
			brokerage.verifyBrokerageTableDataDisplayed("Enroll image");
			brokerage.verifyBrokerageTableDataDisplayed("Transfer into sda link");
			brokerage.verifyBrokerageTableDataDisplayed("Transfer from sda link");
			brokerage.verifyBrokerageTableDataDisplayed("PDF image");
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
			Web.getDriver().switchTo().defaultContent();
			if(Stock.getConfigParam("TEST_ENV").toUpperCase().startsWith("PROJ")){
				Web.clickOnElement(brokerage, "LOGOUT");
				Web.waitForElement(brokerage, "LOGIN");
			}
			

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
	public void PlanForms_Validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			leftmenu = new LeftNavigationBar(homePage);
			PlanForms planforms = new PlanForms(leftmenu);
			planforms.get();
			//Thread.sleep(7000);
			
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			planforms.clickOnForm(null);
			if (planforms.verifyPlanFormIsOpened())
				Reporter.logEvent(Status.PASS, "Verify Plan Form is opened",
						"Plan form is opened ", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Form is opened",
						" Plan form is not opened", true);

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
	public void FundToFundTransfer(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			Thread.sleep(5000);
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Current Balance Investment");
			Web.clickOnElement(investment, "Continue button1");
			Thread.sleep(5000);
						
			if(Stock.getConfigParam("TEST_ENV").toUpperCase().startsWith("PROJ")){
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.getDriver().switchTo().frame("legacyFeatureIframe");
				Web.clickOnElement(investment,"F2F MTG1");
				Thread.sleep(2000);
				Web.clickOnElement(investment,"Submit button Rebalancer");
				
				Web.getDriver().switchTo().defaultContent();
			}
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.navigateToTab("View By Asset Class Tab");
			investment.verifyIfGraphDisplayed("Current Assets Balance Graph");
			investment.verifyIfGraphDisplayed("Post Transfer Balance Graph");
			investment.fundToFundTransfer(
					Stock.GetParameterValue("From_Percent"),
					Stock.GetParameterValue("To_Percent"));
			investment.ReviewFundToFundTransfer(
					Stock.GetParameterValue("From_Percent"),
					Stock.GetParameterValue("To_Percent"));

			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes"))
				investment.cancelTransfer("F2F");
			Web.getDriver().switchTo().defaultContent();
			 //Web.clickOnElement(investment, "LOGOUT");
			// Web.waitForElement(investment, "Login");
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
	public void Dollar_Cost_Average_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			Thread.sleep(5000);
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Dollar Cost");
			Web.clickOnElement(investment, "Continue button1");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.dollarCostAverageFlow(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"),
					Stock.GetParameterValue("percent"),
					Stock.GetParameterValue("amount"));
			 //Web.clickOnElement(investment, "LOGOUT");
			 //Thread.sleep(5000);
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
	public void Edit_Beneficiary(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			beneficiary.get();
			//Thread.sleep(5000);
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.",
					"Beneficiary page is displayed", true);
			beneficiary.clickOnBeneficiaryFromTable(null, "Primary");

			// if (!beneficiary.ifElementDisabled("First name"))
			// Reporter.logEvent(Status.PASS,
			// "verify First name text box is enabled",
			// "First name text box is enabled", true);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify First name text box is enabled",
			// "First name text box not enabled", true);
			// if (!beneficiary.ifElementDisabled("Middle name"))
			// Reporter.logEvent(Status.PASS,
			// "verify Middle name text box is enabled",
			// "Middle name text box is enabled", false);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify Middle name text box is enabled",
			// "Middle name text box not enabled", true);
			// if (!beneficiary.ifElementDisabled("Last name"))
			// Reporter.logEvent(Status.PASS,
			// "verify Last name text box is enabled",
			// "Last name text box is enabled", false);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify Last name text box is enabled",
			// "Last name text box not enabled", true);
			// if (!beneficiary.ifElementDisabled("DOB"))
			// Reporter.logEvent(Status.PASS,
			// "verify DOB text box is enabled",
			// "DOB text box is enabled", false);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify DOB text box is enabled",
			// "DOB text box not enabled", true);
			// if (!beneficiary.ifElementDisabled("Suffix"))
			// Reporter.logEvent(Status.PASS,
			// "verify Suffix text box is enabled",
			// "Suffix text box is enabled", false);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify Suffix text box is enabled",
			// "Suffix text box not enabled", true);
			// if (!beneficiary.ifElementDisabled("SSN"))
			// Reporter.logEvent(Status.PASS,
			// "verify SSN text box is enabled",
			// "SSN text box is enabled", false);
			// else
			// Reporter.logEvent(Status.FAIL,
			// "verify SSN text box is enabled",
			// "SSN text box not enabled", true);
			beneficiary.enterBeneficiaryDetails();
			lib.Web.clickOnElement(beneficiary, "Save button");
			if (lib.Web.isWebElementDisplayed(beneficiary, "MyBeneficiaries"))
				Reporter.logEvent(Status.PASS,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is not displayed", true);
			if (beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button",
						"Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button",
						"Confirm and Continue button is not displayed", true);

			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				// click on continue and confirm button
				if (Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
					Reporter.logEvent(Status.PASS,
							"Confirm and Continue button",
							"Clicked confirm and continue button", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Confirm and Continue button",
							"Could not Click confirm and continue button", true);
				// verify beneficiary name

				if (beneficiary.verifyBeneficiaryDetails("Name"))
					Reporter.logEvent(Status.PASS, "verify beneficiary name",
							"beneficiary name is matching", true);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary name",
							"beneficiary name bot matching", true);

				// verify beneficiary allocation percentage
				if (beneficiary.verifyBeneficiaryDetails("Allocation"))
					Reporter.logEvent(Status.PASS,
							"verify beneficiary Allocation",
							"beneficiary Allocation is matching", false);
				else
					Reporter.logEvent(Status.FAIL,
							"verify beneficiary Allocation",
							"beneficiary Allocation bot matching", true);

				// verify beneficiary Relationship
				if (beneficiary.verifyBeneficiaryDetails("Relationship"))
					Reporter.logEvent(Status.PASS,
							"verify beneficiary Relationship",
							"beneficiary Relationship is matching", false);
				else
					Reporter.logEvent(Status.FAIL,
							"verify beneficiary Relationship",
							"beneficiary Relationship bot matching", true);

				// verify beneficiary ssn
				if (beneficiary.verifyBeneficiaryDetails("SSN"))
					Reporter.logEvent(Status.PASS, "verify beneficiary SSN",
							"beneficiary SSN is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary SSN",
							"beneficiary SSN bot matching", true);

				// verify beneficiary DOB
				if (beneficiary.verifyBeneficiaryDetails("DOB"))
					Reporter.logEvent(Status.PASS, "verify beneficiary DOB",
							"beneficiary DOB is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary DOB",
							"beneficiary DOB bot matching", true);
			}

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
	public void SF01_TC019_Verify_Request_A_Withdrawal_link(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			boolean lblDisplayed = false;
			requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,"I AGREE AND SUBMIT", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify I Agree and Submit Button is Displayed",
						"I Agree and Submit Button is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify I Agree and Submit Button is Displayed",
						"I Agree and Submit Button is Not Displayed", false);
			}
			if (Stock.GetParameterValue("submitRequest").equalsIgnoreCase("YES")) {
				Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");				
				Common.waitForProgressBar();
				Thread.sleep(3000);
				lblDisplayed = requestWithdrawal.isTextFieldDisplayed("Request submitted!");
				if (lblDisplayed) {
					Reporter.logEvent(Status.INFO,
							"Verify Withdrawals Confirmation Page is Displayed",
							"Withdrawals Confirmation Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Withdrawals Confirmation Page is Displayed",
							"Withdrawals Confirmation is Not Displayed", true);
				}
				lblDisplayed = Web.VerifyPartialText(
						"Your confirmation number is", requestWithdrawal.getWebElementText("TEXT CONFIRMATION"), true);
				if (lblDisplayed) {
					Reporter.logEvent(Status.INFO,
							"Verify Request Confirmation is Displayed",
							"Request Confirmation is Displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation is Displayed",
							"Request Confirmation is Not Displayed", true);
				}

				if (Web.isWebElementDisplayed(requestWithdrawal,
						"TEXT CONFIRMATION NUMBER", true)) {
					lblDisplayed = Common.verifyStringIsInNumberFormat(requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"));
					if (lblDisplayed) {
						Reporter.logEvent(
								Status.PASS,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation is in Number Format and \n Confirmation Number is:"
										+ requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"),
								false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation Number is Not in Number Format"
										+ requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"),
								true);
					}
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation Number is Displayed",
							"Request Confirmation Number is Not Displayed",
							true);
				}
			}
			Web.getDriver().switchTo().defaultContent();
		//	Web.clickOnElement(requestWithdrawal, "LOG OUT");
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
	public void SF04_TC01_SendActivationCode_ForgotPasswordFlow(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			String actLoginHelptxt = "Enter the information below to recover your username. You will have the option to change your password.";
			String expLoginHelptxt;
			boolean isMatching;
			boolean verificationResult;
			String verificationCode;

			LoginPage objLogin = new LoginPage();
			ForgotPassword objForgotPsw = new ForgotPassword(objLogin).get();
			TwoStepVerification objAuth = new TwoStepVerification(objLogin);

			Reporter.logEvent(Status.INFO, "Navigate to forgot password page.",
					"forgot password page is displayed", true);

			// Step 3 - Verify following verbiage is displayed
			// "Enter the information below to recover your username. You will have the option to change your password."
			//
			// Also,verify following fields are displayed along with the
			// respective labels
			// Social Security,Zip Code,Last Name,Date of Birth, and Street
			// Address

			verificationResult = objForgotPsw.validateFieldNames();
			if (verificationResult) {
				Reporter.logEvent(Status.PASS,
						"Forgot Password Text fields label validation ",
						"text field name validation was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Forgot Password Text fields label validation ",
						"text field name validation was un-successful", false);
			}

			expLoginHelptxt = objForgotPsw.isLoginHelptxtDisplayed();
			isMatching = Web.VerifyText(expLoginHelptxt, actLoginHelptxt, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS,
						"Forgot Password header Text Verification",
						"Header text verification was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Forgot Password header Text Verification",
						"Header text verification was un-successful actual text: "
								+ actLoginHelptxt + "     Expected Text: "
								+ expLoginHelptxt, false);
			}

			// Step 4 - Enter corresponding details for following fields and
			// click Continue button. - User is redirected to Login help (2 of
			// 3) page

			objForgotPsw.enterForgotPasswordDetails(
					lib.Stock.GetParameterValue("SSN"),
					lib.Stock.GetParameterValue("ZIPCODE"),
					lib.Stock.GetParameterValue("LASTNAME"),
					lib.Stock.GetParameterValue("DOB"),
					lib.Stock.GetParameterValue("STREETADDRESS"));

			// Step 5 - Click on "Already have a code?" link
			objAuth.selectCodeDeliveryOption(lib.Stock.GetParameterValue("codeDeliveryOption"));

			// Step 6 and 7 - Enter verification code into
			// "PLEASE ENTER VERIFICATION CODE" text box and click on "Continue"
			// button
			if (lib.Stock.GetParameterValue("codeDeliveryOption")
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = objAuth.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("codeDeliveryOption").trim()
						.equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				} else {
					if (objAuth.isActivationCodeGenerated(lib.Stock
							.GetParameterValue("codeDeliveryOption"))) {
						Reporter.logEvent(Status.PASS,
								"Verify activation code is generated",
								"Activation code is successfully generated",
								false);
					} else {
						Reporter.logEvent(Status.FAIL,
								"Verify activation code is generated",
								"Activation code is not generated", false);
					}
					return;
				}
			}

			objAuth.submitVerificationCode(verificationCode, false, false);

			// Step 8 - Click the "I need help with my password too" link and
			// enter new password and verify if the user is successful in
			// setting the new psw
			objForgotPsw.helpResetMyPassword(
					lib.Stock.GetParameterValue("PASSWORD"),
					lib.Stock.GetParameterValue("REENTERPASSWORD"));

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
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",ae.getMessage(), true);

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
	public void SF01_TC02_Verify_login_Successfully_into_unregistered_Device(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			String verificationCode = "";

			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
														// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			// TODO Add code to verify text displayed on Two step verification
			// page

			// Verify options 'Text me', 'Call me', 'Email Me' and 'Already have
			// a code?' exists
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "TEXT ME:", true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "CALL ME:", true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "EMAIL:", true);

			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim().equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = twoStepVerification.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("deliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = twoStepVerification.getVerificationCode(false);
				} else {
					if (twoStepVerification.isActivationCodeGenerated(lib.Stock.GetParameterValue("deliveryOption"))) {
						Reporter.logEvent(Status.PASS,
								"Verify activation code is generated",
								"Activation code is successfully generated",
								false);
					} else {
						Reporter.logEvent(Status.FAIL,
								"Verify activation code is generated",
								"Activation code is not generated", false);
					}
					return;
				}
			}

			if (verificationCode.trim().length() == 0) {
				Reporter.logEvent(Status.FAIL, "Fetch verification code.",
						"Verification code not generated", false);
				return;
			}

			// Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock.GetParameterValue("rememberDevice")));

			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage(twoStepVerification);
			landingPage.dismissPopUps(true, true);
			landingPage.dismissPopUps(true, true);
			Thread.sleep(4000);
			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
			if (Web.isWebElementDisplayed(landingPage, "LOGOUT")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//landingPage.dismissPopUps(true, true);
			// Logout if opted
			Web.clickOnElement(landingPage, "LOGOUT");
			Common.waitForProgressBar();
			 Web.waitForPageToLoad(Web.getDriver());
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
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
	public void RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify if we are able to navigate to Plan Savings Tab
			if (retirement.isFieldDisplayed("Plan Savings"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Plan Savings tab",
						"Plan Savings tab is displayed and able to click on it",
						false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Savings tab",
						"Plan Savings tab is not displayed", true);
        Web.clickOnElement(retirement,"Plan Savings");
			// verify if Contribution rate slider is present
			if (retirement.verifyIfSliderPresent("Contribution rate slider"))
				Reporter.logEvent(Status.PASS,
						"Verify Contribution rate slider",
						"Contribution rate slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Contribution rate slider",
						"Contribution rate slider not displayed", true);
			// verify if Retirement age slider is present
			if (retirement.verifyIfSliderPresent("Retirement age slider"))
				Reporter.logEvent(Status.PASS, "Verify Retirement age slider",
						"Retirement age slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Retirement age slider",
						"Retirement age slider not displayed", true);

			// verify if Investment mix slider is present
			/*if (retirement.verifyIfSliderPresent("Investment mix slider"))
				Reporter.logEvent(Status.PASS, "Verify Investment mix slider",
						"Investment mix slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Investment mix slider",
						"Investment mix slider not displayed", true);*/

			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				Web.clickOnElement(retirement, "Do It Myself");
				if (retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", true);
			} else {
				if (!retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider displayed", true);
			}
			// verify if we are able to navigate to Help Me Do It Tab
			if (lib.Stock.GetParameterValue("Help Me Do It").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Able to navigate to Help Me Do It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Not Able to navigate to Help Me Do It tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Help Me DO it tab is not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Help Me Do It tab is displayed", true);
			}

			// verify if we are able to navigate to Do It For Me Tab
			if (lib.Stock.GetParameterValue("Do It For Me").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Able to navigate to Do It For Me It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Not Able to navigate to Do It For Me tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Do It For Me tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Do It For Me tab is displayed", true);
			}

			// verify if we are able to navigate to Do It Myself Tab
			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Able to navigate to Do It Myself tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Not Able to navigate to Do It Myself tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Do It Myself tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Do It Myself tab is displayed", true);
			}
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
	public void RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Social Security Tab");

				// Verify if Social Security Tab is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Social Security Administration"))
					Reporter.logEvent(Status.PASS,
							"Verify if Social Security Page is Loaded",
							"Social Security page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Social Security Page is Loaded",
							"Social Security page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
			}

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
	public void RIP_TC006_To_Verify_RetirementIncomeTab_Other_Assets(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Other Assets Tab");

				// Verifying if Other Assets page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Add an Account"))
					Reporter.logEvent(Status.PASS,
							"Verify if Other Assets Page is Loaded",
							"Other Assets page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Other AssetsPage is Loaded",
							"Other Assets page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
			}

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
	public void RIP_TC007_To_Verify_RetirementIncomeTab_Income_Gap(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verify if Retirement Income Page is loaded
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Income Gap Tab");

				// Verify if Income Gap Page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Catch up Contributions"))
					Reporter.logEvent(Status.PASS,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);

			}
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
	public void RIP_TC008_To_verify_Retirement_Income_tab_percent_of_my_goal_section(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtMonthlyIncome = retirement.verifyPercentOfMyGoalSection("Monthly Income");
			if (expectedtxtMonthlyIncome.contains("My goal for monthly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for monthly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for monthly retirement income ",
						"Percent of my goal section not displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtYearlyIncome = retirement.verifyPercentOfMyGoalSection("Yearly Income");
			if (expectedtxtYearlyIncome.contains("My goal for yearly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section not displayed", true);
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
	public void HCC_TC009_To_verify_Health_care_costs(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			float projectedIncome;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();

			projectedIncome = retirementIncome.getProjectedIncome();
			Web.clickOnElement(healthCareCost, "Health-care costs");
			healthCareCost.verifyAttainedAgeSlide();
			healthCareCost.verifyPieChart();
			healthCareCost.verifyHealthCostFromUI(projectedIncome);
			healthCareCost.verifyPersonalizeBtn();
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
	public void HDIC_TC010_To_verify_How_do_I_compare_tab(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare hdic = new HowDoICompare(homePage);
			hdic.get();
			hdic.verifyViewDetails();
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
	public void LIAT_To_verify_PayCheckView_Displayed(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			lblDisplayed = Web.isWebElementDisplayed(retirement,
					"Paycheck Rainbow", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is displayed",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is Not displayed",
						true);

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
	public void LIAT_To_verify_PayCheckView_Displayed_With_ColorBar(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			lblDisplayed = Web.isWebElementDisplayed(retirement,
					"Paycheck Rainbow", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is displayed",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is Not displayed",
						true);
			Web.waitForElement(retirement,"VIEW DETAILS");
			Web.clickOnElement(retirement,"VIEW DETAILS");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("My current savings");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("My future savings");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Employer past contribution");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Employer future contribution");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Social Security");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Other assets");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Income gap");
			retirement.verifyValueInColorBarForNonZeroValue("My savings");
			retirement.verifyValueInColorBarForNonZeroValue("Employer contributions");
			retirement.verifyValueInColorBarForNonZeroValue("Social Security");
			retirement.verifyValueInColorBarForNonZeroValue("Other assets");
			retirement.verifyValueInColorBarForNonZeroValue("Income gap");
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
	public void LIAT_To_verify_HowDoICompare_Labels_Circles(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare howdoicompare = new HowDoICompare(homePage);
			howdoicompare.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to How Do I Compare page.",
					"How Do I Compare page is displayed", true);

			
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label MyPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'My Peers' Label in How Do I Compare Page",
						"'My Peers' Label is Displayed in How Do I Compare Page",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'My Peers' Label in How Do I Compare Page",
						"'My Peers' Label is Not Displayed in How Do I Compare Page",
						true);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label TopPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Top Peers' Label in How Do I Compare Page",
						"'Top Peers' Label is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Top Peers' Label in How Do I Compare Page",
						"'Top Peers' Label is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label Me", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Me' Label in How Do I Compare Page",
						"'Me' Label is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Me' Label in How Do I Compare Page",
						"'Me' Label is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal MyPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'My Peers Goal Section' in How Do I Compare Page",
						"'My Peers Goal Circle' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'My Peers Goal Section' in How Do I Compare Page",
						"'Goal Circle For My Peers' is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal TopPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Top Peers Goal Section' in How Do I Compare Page",
						"'Goal Circle For Top Peers' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Top Peers Goal Section' in How Do I Compare Page",
						"'Top Peers Goal Circle' is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal Me", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Goal Section For Me' in How Do I Compare Page",
						"'Goal Circle For Me' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Goal Section For Me' in How Do I Compare Page",
						"'Goal Circle For Me' is Not Displayed in How Do I Compare Page",
						false);

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
	public void LIAT_To_verify_LIS_Score_Gets_Changed_as_we_Change_Dropdown_Values(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare howdoicompare = new HowDoICompare(homePage);
			howdoicompare.get();
			boolean lblDisplayed = false;
			List<String> DefaultContributionValues=new ArrayList<String>();
			List<String> DefaultBalanceValues= new ArrayList<String>();
			List<String> DefaultContributionValues1=new ArrayList<String>();
			List<String> DefaultBalanceValues1= new ArrayList<String>();
			List<String> ContributionRateValuesAfetrChange= new ArrayList<String>();
			List<String> BalanceValuesAfterChange=new ArrayList<String>();
			Reporter.logEvent(Status.INFO,
					"Navigate to How Do I Compare page.",
					"How Do I Compare page is displayed", true);
			DefaultContributionValues=howdoicompare.GetValuesofContributionRate();
			DefaultBalanceValues=howdoicompare.GetValuesofBalance();
			DefaultContributionValues1=DefaultBalanceValues;
			DefaultBalanceValues1=DefaultBalanceValues;
            Web.selectDropDownOption(howdoicompare, "DropDown Age", "50-59", true);
            ContributionRateValuesAfetrChange=howdoicompare.GetValuesofContributionRate();
            BalanceValuesAfterChange=howdoicompare.GetValuesofBalance();
			for(int i=0;i< DefaultContributionValues.size()-1;i++){
				if(!(DefaultContributionValues1.get(i).equalsIgnoreCase(ContributionRateValuesAfetrChange.get(i)))){
					lblDisplayed=true;
					break;
				}
			}
				
				if(lblDisplayed){
					Reporter.logEvent(Status.PASS,
							"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Age Option",
							"'Contribution Rate' Changed in How Do I Compare Page",
							true);
				}
				else{
					Reporter.logEvent(
							Status.FAIL,
							"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Age Option",
							"'Contribution Rate' is Not Changed in How Do I Compare Page",
							true);
				}
				
				for(int i=0;i< DefaultBalanceValues.size()-1;i++){
					if(!DefaultBalanceValues1.get(i).equalsIgnoreCase(BalanceValuesAfterChange.get(i))){
						lblDisplayed=true;
						break;
					}
				}
					
					if(lblDisplayed){
						Reporter.logEvent(Status.PASS,
								"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Age Option",
								"'Balance' Changed in How Do I Compare Page",
								false);
					}
					else{
						Reporter.logEvent(
								Status.FAIL,
								"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Age Option",
								"'Balance' is Not Changed in How Do I Compare Page",
								false);
					}
					 Web.selectDropDownOption(howdoicompare, "DropDown Salary", "$75K - Less than $100K", true);
			            ContributionRateValuesAfetrChange=howdoicompare.GetValuesofContributionRate();
			            BalanceValuesAfterChange=howdoicompare.GetValuesofBalance();
						for(int i=0;i< DefaultContributionValues.size()-1;i++){
							if(!DefaultContributionValues1.get(i).equalsIgnoreCase(ContributionRateValuesAfetrChange.get(i))){
								lblDisplayed=true;
								break;
							}
						}
							
							if(lblDisplayed){
								Reporter.logEvent(Status.PASS,
										"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Salary Option",
										"'Contribution Rate' Changed in How Do I Compare Page",
										true);
							}
							else{
								Reporter.logEvent(
										Status.FAIL,
										"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Salary Option",
										"'Contribution Rate' is Not Changed in How Do I Compare Page",
										true);
							}
							
							for(int i=0;i< DefaultBalanceValues.size()-1;i++){
								if(!DefaultBalanceValues1.get(i).equalsIgnoreCase(BalanceValuesAfterChange.get(i))){
									lblDisplayed=true;
									break;
								}
							}
								
								if(lblDisplayed){
									Reporter.logEvent(Status.PASS,
											"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Salary Option",
											"'Balance' Changed in How Do I Compare Page",
											false);
								}
								else{
									Reporter.logEvent(
											Status.FAIL,
											"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Salary Option",
											"'Balance' is Not Changed in How Do I Compare Page",
											false);
								}
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
	public void LIAT_To_verify_ContributionRate_Slider_For_Suspended_And_Terminated_Users(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblEnabled = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			if(Stock.GetParameterValue("userType").equalsIgnoreCase("Terminated")){
				lblEnabled = retirement.verifyIfSliderEnabled("Contribution rate slider");
			if (!lblEnabled)

				Reporter.logEvent(Status.PASS,
						"Verify Contribution Rate Slider in Retirement Income Page",
						"Contribution Rate Slider in Retirement Income Page is Not Enabled",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Contribution Rate Slider in Retirement Income Page",
						"Contribution Rate Slider in Retirement Income Page is Enabled",
						true);
			}
			if(Stock.GetParameterValue("userType").equalsIgnoreCase("Suspended")){
				lblEnabled = retirement.verifyIfSliderEnabled("Contribution rate slider");
				if (lblEnabled)

					Reporter.logEvent(Status.PASS,
							"Verify Contribution Rate Slider in Retirement Income Page",
							"Contribution Rate Slider in Retirement Income Page is Enabled",
							true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Contribution Rate Slider in Retirement Income Page",
							"Contribution Rate Slider in Retirement Income Page is Not Enabled",
							true);
				retirement.enterContributionRate("15");
				Reporter.logEvent(Status.INFO,
						"Enter Contribution Rate in Retirement Income Page",
						"Contribution Rate is changed in Contribution Rate Slider",
						true);
				lblEnabled = retirement.verifyIfSliderEnabled("Review Changes");
				if (!lblEnabled)

					Reporter.logEvent(Status.PASS,
							"Verify 'Review Changes' Button in Retirement Income Page",
							"'Review Changes' Button in Retirement Income Page is Not Enabled",
							true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Review Changes' Button in Retirement Income Page",
							"'Review Changes' Button in Retirement Income Page is Enabled",
							true);
				
				}
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
	public void Verify_Health_care_costs_helpful_links(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			//RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();
			
			Web.clickOnElement(healthCareCost, "Personalize Button");
			Reporter.logEvent(Status.INFO,"Verify 'Personalize Button' is clicked","Clicked on 'Personalize Button'", true);
			
			if(Web.isWebElementDisplayed(healthCareCost, "Your full report(PDF)", true)){
				Reporter.logEvent(Status.PASS,"Verify 'Your full report(PDF)' link is displayed","'Your full report(PDF)' link is displayed", false);
				Web.clickOnElement(healthCareCost, "Your full report(PDF)");
				Reporter.logEvent(Status.INFO,"Verify 'Your full report(PDF)' link is clicked","clicked on 'Your full report(PDF)' link", false);
				String parentWindow = Web.getDriver().getWindowHandle();
				Set<String> handles = Web.getDriver().getWindowHandles();

				for (String windowHandle : handles) {

					if (!windowHandle.equals(parentWindow)) {
						Web.getDriver().switchTo().window(windowHandle);
						break;
					}
				}
				// closing child window
				Web.getDriver().close(); 
				//Switching to main window
				Web.getDriver().switchTo().window(parentWindow);
			}
			else
				Reporter.logEvent(Status.FAIL,"Verify 'Your full report(PDF)' link is displayed","'Your full report(PDF)' link is displayed", true);
			
			healthCareCost.verifyMedicareLink();
			
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Throwable t = e.getCause();
            String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured",ae.getMessage() , true);                    
                        
        }
		finally { 
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	@Test(dataProvider = "setData")
	public void Verify_Tour_Link(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			
			if(Web.isWebElementDisplayed(retirement, "TOUR Button", true)){
				Web.clickOnElement(retirement, "TOUR Button");
				Reporter.logEvent(Status.INFO,"Verify TOUR button is clicked","clicked on TOUR button", false);
				retirement.verifyTourModals("Your retirement account experience");
				Web.clickOnElement(retirement, "Next Button");
				retirement.verifyTourModals("Progress toward your goal");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Your estimated monthly retirement income");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Income sources");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Compare your options");
				Web.clickOnElement(retirement, "Finish Button");
				if (Web.isWebElementDisplayed(retirement, "Cancel Goal setup", true)) {
					Web.clickOnElement(retirement, "Cancel Goal setup");
				}
			}
			else
				Reporter.logEvent(Status.FAIL,"Verify TOUR button is displayed","Tour button is displayed", false);
			//Web.clickOnElement(retirement, "LOG OUT");
			
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Throwable t = e.getCause();
            String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
            Reporter.logEvent(Status.FAIL, "A run time exception occured.",msg, true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured",ae.getMessage(), true);                    
                        
        }
		finally { 
			try { 
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void Manage_My_Investment_Flow_New(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);

			investment.get();
			//Thread.sleep(5000);
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Rebalance Current Balance");
			Web.clickOnElement(investment, "Continue button1");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			String[] percentage={"50","50"};
			investment.rebalanceInvestment_New(2,percentage);
			/*DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			String date = dateFormat.format(cal.getTime()).toUpperCase();
			investment.verifyRebalanceInvestmentDetails(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"), date,
					Stock.GetParameterValue("investment_percent"));
			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				investment.verifyRebalanceInvestmentConfirmationDetails();
				investment.cancelTransfer("Rebalance Currnet Balance");
			}*/
			Web.getDriver().switchTo().defaultContent();
			 Web.clickOnElement(investment, "LOGOUT");
			 Common.waitForProgressBar();
			 Web.waitForPageToLoad(Web.getDriver());
			
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
