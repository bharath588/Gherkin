package app.pptweb.testcases.prodvalidations;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.general.RateOfReturnPage;
import pageobjects.investment.Brokerage;
import pageobjects.investment.InvestmentLineup;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.landingpage.LegacyLandingPage;
import pageobjects.liat.ProfilePage;
import pageobjects.loan.RequestLonePage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.payroll.PayrollCalendar;
import pageobjects.planinformation.PlanForms;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import pageobjects.transactionhistory.TransactionHistory;
import core.framework.Globals;

public class prodvalidationtestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;

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
				.getName(), Globals.GC_MANUAL_TC_NAME);

	}

	@Test(dataProvider = "setData")
	public void SF01_TC01_Verify_Custom_site_prelogin_data(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
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
								"CopyRight Informatio is Same on the Login Page",
								false);
			}
			if (!Common.getSponser().equalsIgnoreCase("Apple")) {
				login.verifyWebElementDisplayed("image participant Savings rates");
				login.verifyWebElementDisplayed("image participant Rollover options");
				login.verifyWebElementDisplayed("image participant Browser Support");
			}
			login.verifyWebElementDisplayed("System Requirements and Security");
			login.verifyWebElementDisplayed("Privacy");
			login.verifyWebElementDisplayed("Terms and Conditions");
			login.verifyWebElementDisplayed("Business Continuity Plan");
			login.verifyWebElementDisplayed("Market Timing and Excessive Trading Policies");
			login.verifyWebElementDisplayed("Broker Check Notification");
			login.verifyLinkIsNotBroken("System Requirements and Security");
			login.verifyLinkIsNotBroken("Privacy");
			login.verifyLinkIsNotBroken("Terms and Conditions");
			login.verifyLinkIsNotBroken("Business Continuity Plan");
			login.verifyLinkIsNotBroken("Market Timing and Excessive Trading Policies");
			boolean windowFound = false;
			String parentWindow = Web.webdriver.getWindowHandle();
			Web.clickOnElement(login, "Broker Check Notification");
			Set<String> handles = Web.webdriver.getWindowHandles();
			for (String windowHandle : handles) {

				if (!windowHandle.equals(parentWindow)) {
					if (Web.webdriver.switchTo().window(windowHandle)
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
								Status.FAIL,
								"Verifying Broker Check Notification is Opened in New Window",
								"Broker Check Notification is Not Opened in New Window",
								true);
			}

			Web.webdriver.close();
			Web.webdriver.switchTo().window(parentWindow);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
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
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
			login.get();

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
								"Customer Support Information is Same not on the Login Page",
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
						"Contact Us Information is Same not on the Login Page",
						true);
			}

			Web.clickOnElement(login, "dismiss");
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

			isContactNoMatching = login.isValidContactUsInfo(Stock
					.GetParameterValue("ExpectedContactNo_Post_login"));
			if (isContactNoMatching) {
				lib.Reporter.logEvent(Status.PASS,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is Same on the Home Page",
						false);

			} else {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is Same not on the Home Page",
						false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
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
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			boolean isDisplayed = false;

			isDisplayed = profilePage.validateUserProfileInfo();
			if (isDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify User Profile Info is Displayed",
						"User Profile Info is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify User Profile Info is Displayed",
						"User Profile Info is not Proper", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
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
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
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
			lblDisplayed = Web.isWebElementDisplayed(myAccountPage, "Graph",
					true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify Graph is Displayed",
						"Graph is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify Graph is Displayed",
						"Graph is NOT visible", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC016_Verify_LeftNavigation_Tab_Request_A_Lone_link(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			LeftNavigationBar lftBar = new LeftNavigationBar(myAccountPage);
			RequestLonePage requestLone = new RequestLonePage(lftBar);
			requestLone.get();

			boolean lblDisplayed = false;

			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"Request a loan", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Lone Page is Displayed",
						"Request A Lone Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Lone Page is Displayed",
						"Request A Lone Page is NOT visible", true);
			}
			requestLone.selectLoneType(Stock.GetParameterValue("loanType"));
			Web.webdriver.switchTo().frame("legacyFeatureIframe");
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
			Web.webdriver.switchTo().defaultContent();
			requestLone.EnterLoanAmtAndTerm("$10,000", "12");
			Web.webdriver.switchTo().frame("legacyFeatureIframe");
			lblDisplayed = Web
					.VerifyPartialText(
							"YOUR\nLOAN \nREQUEST/PAYMENT \nESTIMATE",
							requestLone
									.getWebElementText("TEXT LOAN REQUEST ESTIMATE"),
							true);
			if (lblDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Loan requset Estimate is Displayed",
						"Loan Request Estimate is Displayed \n Expected:YOUR\nLOAN \nREQUEST/PAYMENT \nESTIMATE \nActual:"
								+ requestLone
										.getWebElementText("TEXT LOAN REQUEST ESTIMATE"),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan requset Estimate is Displayed",
						"Loan Request Estimate is Not Displayed \n Expected:YOUR\nLOAN \nREQUEST/PAYMENT \nESTIMATE \nActual:"
								+ requestLone
										.getWebElementText("TEXT LOAN REQUEST ESTIMATE"),
						true);
			}
			lblDisplayed = Web.VerifyPartialText("Loan Term = 12 Months",
					requestLone.getWebElementText("TEXT LOAN TERM"), true);
			if (lblDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Loan Term is Displayed",
						"Loan Term is Displayed \n Expected:Loan Term = 12 Months \nActual:"
								+ requestLone
										.getWebElementText("TEXT LOAN TERM"),
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan Term is Displayed",
						"Loan Term is Not Displayed \n Expected:Loan Term = 12 Months \nActual:"
								+ requestLone
										.getWebElementText("TEXT LOAN TERM"),
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
			requestLone
					.isTextFieldDisplayed("Please review the above contact information and make any necessary changes before you continue.");
			Web.setTextToTextBox("INPUT HOME AREA CODE", requestLone, "123");
			Web.setTextToTextBox("INPUT HOME PREFIX", requestLone, "456");
			Web.setTextToTextBox("INPUT HOME SUFFIX", requestLone, "7890");
			Web.clickOnElement(requestLone, "CONTINUE LOAN REQUEST");
			lblDisplayed = Web
					.VerifyText(Stock.GetParameterValue("textVerifyAllInfo")
							.toString().trim(), requestLone
							.getWebElementText("TEXT VERIFY ALL INFO"), true);

			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Text PLEASE VERIFY ALL INFO..... is Displayed",
						"PLEASE VERIFY ALL INFO..... Text is Dispalyed", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Text PLEASE VERIFY ALL INFO..... is Displayed",
						"PLEASE VERIFY ALL INFO..... Text is Not Dispalyed Expected:"
								+ Stock.GetParameterValue("textVerifyAllInfo")
										.toString().trim()
								+ "\nActual:"
								+ requestLone
										.getWebElementText("TEXT VERIFY ALL INFO"),
						true);
			}
			lblDisplayed = Web.VerifyText(
					Stock.GetParameterValue("textOnceYouClick").toString()
							.trim(),
					requestLone.getWebElementText("TEXT ONCE YOU CLICK"), true);

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
								+ Stock.GetParameterValue("textOnceYouClick")
										.toString().trim()
								+ "\nActual:"
								+ requestLone
										.getWebElementText("TEXT ONCE YOU CLICK"),
						false);
			}

			requestLone.isTextFieldDisplayed("Loan Information");
			requestLone.isTextFieldDisplayed("Loan Amount");
			requestLone.isTextFieldDisplayed("Total Interest Charge");
			requestLone
					.isTextFieldDisplayed("Total Principal and Interest Amount");
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
			requestLone
					.isTextFieldDisplayed("I have read and agree to the Plan");
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
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC018_Verify_ROR_link(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
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

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
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
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LegacyLandingPage homePage = new LegacyLandingPage(mfaPage);

			homePage.get();

			boolean lblDisplayed = false;

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
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Balance_validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Balance balance = new Balance(leftmenu);

			balance.get();
			balance.navigateToTab("Balance");
			balance.verifyTableDisplayed("Balance by Money Table");
			balance.verifytableHeaderNotEmpty("Balance by Money Table Header");
			balance.verifyTableDataDisplayed("Balance by Money Table");

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

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Transaction_History_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			TransactionHistory transaction = new TransactionHistory(leftmenu);

			transaction.get();
			// transaction.clickConfirmationNumber();
			transaction.verifyTableDisplayed("Transaction Filter Option Table");
			transaction.clickConfirmationNumber();

			transaction
					.verifyTableDisplayed("Transaction History Contr Summary Table");
			transaction
					.verifytableHeaderNotEmpty("Transaction History Contr Summary Table Header");
			transaction
					.verifyTableDataDisplayed("Transaction History Contr Summary Table");

			transaction
					.verifyTableDisplayed("Transaction History Contr Detail Table");
			transaction
					.verifytableHeaderNotEmpty("Transaction History Contr Detail Table Header");
			transaction
					.verifyTableDataDisplayed("Transaction History Contr Detail Table");

			transaction.verifyReferenceNumber();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Statement_and_Documents_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			StatementsAndDocuments statements = new StatementsAndDocuments(
					leftmenu);

			statements.get();
			statements.navigateToTab("Stmts On Demand Tab");
			statements.verifyTableDisplayed("Statements Summary Table");
			statements
					.verifytableHeaderNotEmpty("Statements Summary Table Header");
			statements.verifyTableDataDisplayed("Statements Summary Table");

			statements.verifyTableDisplayed("Stmts By Money Type Table");
			statements
					.verifytableHeaderNotEmpty("Stmts By Money Type Table Header");
			statements.verifyTableDataDisplayed("Stmts By Money Type Table");

			statements.verifyTableDisplayed("Stmts by Fund Detail Table");
			statements
					.verifytableHeaderNotEmpty("Stmts by Fund Detail Table Header");
			statements.verifyTableDataDisplayed("Stmts by Fund Detail Table");

			statements.clickOnStatementFromTable("Money Type Table");
			statements.switchToWindow();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Manage_My_Investment_Flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);

			investment.get();
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Rebalance Currnet Balance");
			Web.clickOnElement(investment, "Continue button1");
			investment.rebalanceInvestment(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"),
					Stock.GetParameterValue("investment_percent"));
			DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			String date = dateFormat.format(cal.getTime());
			investment.verifyRebalanceInvestmentDetails(
					Stock.GetParameterValue("Frequency_Period"),
					Stock.GetParameterValue("Setup_date"), date,
					Stock.GetParameterValue("investment_percent"));
			Web.clickOnElement(investment, "Cancel button");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Payroll_Calendar_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PayrollCalendar payroll = new PayrollCalendar(leftmenu);

			payroll.get();
			payroll.verifyDataIsDiaplyed();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Investment_lineup_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			InvestmentLineup investment = new InvestmentLineup(leftmenu);

			investment.get();
			investment.viewProspectus();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Brokerage_validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Brokerage brokerage = new Brokerage(leftmenu);

			brokerage.get();

			if (Web.isWebElementDisplayed(brokerage, "Brokerage Table")) {
				Reporter.logEvent(Status.PASS,
						"Verify brokerage table is displayed",
						"Table displayed", true);

				brokerage.verifyBrokerageDataDisplayed("Provider Name");
				brokerage.verifyBrokerageDataDisplayed("Enroll image");
				brokerage
						.verifyBrokerageDataDisplayed("Transfer into sda link");
				brokerage
						.verifyBrokerageDataDisplayed("Transfer from sda link");
				brokerage.verifyBrokerageDataDisplayed("PDF image");
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify brokerage table is displayed",
						"Table is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void PlanForms_Validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PlanForms planforms = new PlanForms(leftmenu);

			planforms.get();
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void FundToFundTransfer(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);

			investment.get();
			investment.clickChangeMyInvestmentButton();
			investment
					.choseInvestmentOption("Change Current Balance Investment");
			Web.clickOnElement(investment, "Continue button1");
			investment.navigateToTab("View By Asset Class Tab");
			investment.verifyIfGraphDisplayed("Current Assets Balance Graph");
			investment.verifyIfGraphDisplayed("Post Transfer Balance Graph");
			investment.fundToFundTransfer(
					Stock.GetParameterValue("From_Percent"),
					Stock.GetParameterValue("To_Percent"));
			investment.ReviewFundToFundTransfer(
					Stock.GetParameterValue("From_Percent"),
					Stock.GetParameterValue("To_Percent"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Dollar_cost_average_flow(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);

			investment.get();
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Dollar Cost");
			Web.clickOnElement(investment, "Continue button1");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Edit_Beneficiary(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.",
					"Beneficiary page is displayed", true);
			beneficiary.clickOnBeneficiaryFromTable(null, "Primary");

			if (!beneficiary.ifElementDisabled("First name"))
				Reporter.logEvent(Status.PASS,
						"verify First name text box is enabled",
						"First name text box is enabled", true);
			else
				Reporter.logEvent(Status.FAIL,
						"verify First name text box is enabled",
						"First name text box not enabled", true);
			if (!beneficiary.ifElementDisabled("Middle name"))
				Reporter.logEvent(Status.PASS,
						"verify Middle name text box is enabled",
						"Middle name text box is enabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Middle name text box is enabled",
						"Middle name text box not enabled", true);
			if (!beneficiary.ifElementDisabled("Last name"))
				Reporter.logEvent(Status.PASS,
						"verify Last name text box is enabled",
						"Last name text box is enabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Last name text box is enabled",
						"Last name text box not enabled", true);
			if (!beneficiary.ifElementDisabled("DOB"))
				Reporter.logEvent(Status.PASS,
						"verify DOB text box is enabled",
						"DOB text box is enabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"verify DOB text box is enabled",
						"DOB text box not enabled", true);
			if (!beneficiary.ifElementDisabled("Suffix"))
				Reporter.logEvent(Status.PASS,
						"verify Suffix text box is enabled",
						"Suffix text box is enabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Suffix text box is enabled",
						"Suffix text box not enabled", true);
			if (!beneficiary.ifElementDisabled("SSN"))
				Reporter.logEvent(Status.PASS,
						"verify SSN text box is enabled",
						"SSN text box is enabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"verify SSN text box is enabled",
						"SSN text box not enabled", true);

			lib.Web.clickOnElement(beneficiary, "Cancel button");
			if (lib.Web.isWebElementDisplayed(beneficiary, "MyBeneficiaries"))
				Reporter.logEvent(Status.PASS,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(dataProvider = "setData")
	public void Edit_MyContribution(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferral = new Deferrals(leftmenu);

			deferral.get();
			if (deferral.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferral.click_MaximizeToCompanyMatch();
			deferral.select_ContributionType("Before");
			lib.Web.clickOnElement(deferral, "Continue button");
			if (deferral.verifyMyContributions("6", "Before-tax", "Standard"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Before contribution percent for Standard deferral",
						"Before contribution percent matching", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Before contribution percent for Standar deferral",
						"Before contribution percent matching", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {

		}

		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
