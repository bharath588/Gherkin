package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import core.framework.Globals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.withdrawals.RequestWithdrawal;

public class withdrawalstestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	static String printTestData = "";
	static int enteredRothWithdrawalAmt = 0;
	static int enteredPreTaxWithdrawalAmt = 0;
	int enteredTotalWithdrawalAmt = 0;

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

	private String printTestData() throws Exception {
		printTestData = "";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}

	@Test(dataProvider = "setData")
	public void Withdrawals_TC01_In_Service_Age59_And_Half_Payment_To_Self(
			int itr, Map<String, String> testdata) {

		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			// Land on Request a Withdrawal Page
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);

			// Verify if Withdrawal Page is displayed
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify  if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);
						
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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Withdrawals_TC03_In_Service_Age59_And_Half_Roll_Qual(int itr,
			Map<String, String> testdata) {

		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			// Land on Request a Withdrawal Page
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);

			// Verify if Withdrawal Page is displayed

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify  if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Withdrawals_TC04_In_Service_Age59_And_Half_Roll_Over_To_Traditional_IRA(
			int itr, Map<String, String> testdata) {

		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			// Land on Request a Withdrawal Page
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);

			// Verify if Withdrawal Page is displayed
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify  if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);


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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_TC05_In_Service_Age59_And_Half_Roll_Over_To_External_Roth_IRA(
			int itr, Map<String, String> testdata) {

		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			// Land on Request a Withdrawal Page
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);

			// Verify if Withdrawal Page is displayed
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify  if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			
			requestWithdrawal.selectWithdrawalTypeForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	

	@Test(dataProvider = "setData")
	public void WithDrawals_TC02_SepService_PWD_Roll_Over_To_Traditional_IRA(
			int itr, Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void WithDrawals_TC07_SepService_PWD_Roll_Over_To_External_Roth_IRA(
			int itr, Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void WithDrawals_TC08_SepService_PWD_Roll_Qual(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void WithDrawals_TC06_SepService_PWD_Payment_to_Self(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void WithDrawals_TC09_SepService_FWD_Payment_To_Self(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),false);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Withdrawals_SepService_Combo_PTS_And_Roll_IRA(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),false);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_Sep_Service_FWD_RollOver_Roll_Qual(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),false);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void Withdrawals_SepService_RollOver_Roll_Over_To_Traditional_IRA(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),false);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void Withdrawals_SepService_PWD_Max_Amount_PTS(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			requestWithdrawal.selectMaxAmountCheckBoxForWithdrawalType(Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isPreTaxAvail"));				
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"),true);
			

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

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
}
