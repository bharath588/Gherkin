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
import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
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
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.payroll.PayrollCalendar;
import pageobjects.planinformation.PlanForms;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import pageobjects.transactionhistory.TransactionHistory;
import pageobjects.withdrawals.RequestWithdrawal;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ProdvalidationTestCases_Login_LeftNav_Investments {

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
								"CopyRight Informatio is Not Same on the Login Page\nExpected:"+Stock.GetParameterValue("copyright info")+"\nActual:"+copyRightInfo,
								false);
			}
			if (!Common.getSponser().equalsIgnoreCase("Apple")) {
				login.verifyWebElementDisplayed("image participant Savings rates");
				login.verifyWebElementDisplayed("image participant Rollover options");
				/*login.verifyWebElementDisplayed("image participant Browser Support");*/
				login.verifyWebElementDisplayed("IMAGE Protect your personal information");
			}
			login.verifyWebElementDisplayed(/* "System Requirements and Security" */"System Requirements");
			login.verifyWebElementDisplayed("Privacy");
			login.verifyWebElementDisplayed(/* "Terms and Conditions" */"Terms");
			login.verifyWebElementDisplayed(/* "Business Continuity Plan" */"Business Continuity");
			login.verifyWebElementDisplayed(/* "Market Timing and Excessive Trading Policies" */"Market Timing and Excessive Trading");
			login.verifyWebElementDisplayed("BrokerCheck Notification");
			login.verifyWebElementDisplayed("FINRA Investor Education");
			login.verifyLinkIsNotBroken("System Requirements");
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
			
			homePage.dismissPopUps(true, true);
			
			Thread.sleep(4000);

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
			homePage.dismissPopUps(true, true);
		
			Thread.sleep(4000);

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
			if(Web.isWebElementDisplayed(homePage, "Button Close", true)){
				Web.clickOnElement(homePage, "Button Close");
			}
			
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
						/*for (String winHandle : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				Web.waitForPageToLoad(Web.getDriver());
			}*/
			//Thread.sleep(5000);
			brokerage.verifyBrokerageTableDisplayed();

			brokerage.verifyBrokerageTableDataDisplayed("Provider Name");
			brokerage.verifyBrokerageTableDataDisplayed("Enroll image");
			brokerage.verifyBrokerageTableDataDisplayed("Transfer into sda link");
			brokerage.verifyBrokerageTableDataDisplayed("Transfer from sda link");
			brokerage.verifyBrokerageTableDataDisplayed("PDF image");
			Web.getDriver().switchTo().defaultContent();
			/*Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);*/
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
			/* Web.clickOnElement(investment, "LOGOUT");
			Web.waitForElement(investment, "Login");*/
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
			landingPage.dismissPopUps(true, true);
			// Logout if opted
			//Web.clickOnElement(landingPage, "LOGOUT");
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
	
	@Test(dataProvider = "setData")
	public void DDTC_30083_FE_HMDI_Access_Online_Advice(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
			//Step 5
			investment.clickChangeMyInvestmentButton();
		
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
		
			//Step 6 to 9
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
					investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 10
			investment.verifyWebElementDisplayed("Access Online Advice");			
			investment.isTextFieldDisplayed("Online investment advice can help you select investments that align with your retirement objectives.");
			//Step 11
			Web.clickOnElement(investment,"Access Online Advice");
			/*Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");*/
			Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(10000);
			    	  /* Web.waitForElement(investment, "Button Accept");
			    	   if(Web.isWebEementEnabled(investment, "Button Accept"))
			    		   Web.clickOnElement(investment, "Button Accept");
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(5000);*/
			    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Advisory Services - Welcome")){
			    	  
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Advisory Services Page'opened in New Window",
									"'Advisory Services Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Advisory Services Page'opened in New Window ",
									"'Advisory Services Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
	           if(Web.isWebEementEnabled(investment, "Button Refresh",true))
		           Web.clickOnElement(investment, "Button Refresh");
	           Web.getDriver().switchTo().defaultContent();
	           Web.waitForPageToLoad(Web.getDriver());
	           Common.waitForProgressBar();
	        
		 
		}catch (Exception e) {
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void DDTC_30104_FE_DIM_Access_Online_Guidance(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
			//Step 5
			investment.clickChangeMyInvestmentButton();
		
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			/*investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");*/
		
			//Step 6 to 9
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 10
			investment.verifyWebElementDisplayed("Access Online Guidance");			
			investment.isTextFieldDisplayed("Online Guidance can help you get pointed in the right direction.");
			//Step 11
			Web.clickOnElement(investment,"Access Online Guidance");
			/*Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");*/
			Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(10000);
			    	  /* Web.waitForElement(investment, "Button Accept");
			    	   if(Web.isWebEementEnabled(investment, "Button Accept"))
			    		   Web.clickOnElement(investment, "Button Accept");
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(5000);*/
			    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Advisory Services - Welcome")){
			    	  
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Advisory Services Page'opened in New Window",
									"'Advisory Services Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Advisory Services Page'opened in New Window ",
									"'Advisory Services Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow);
	           if(Web.isWebElementDisplayed(investment, "Button Refresh",true))
		           Web.clickOnElement(investment, "Button Refresh");
		         
	           Web.getDriver().switchTo().defaultContent();
	           Web.waitForPageToLoad(Web.getDriver());
	           Common.waitForProgressBar();
	        
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

}
