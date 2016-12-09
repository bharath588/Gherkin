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
		for (Map.Entry<String, String> entry : Stock.globalTestdata.entrySet()) {
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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));
			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
						
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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));

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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));


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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));

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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));

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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
			
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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));

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
			requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
			requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"));			
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock
					.GetParameterValue("userName"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));

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
			
			if(Web.isWebElementDisplayed(requestWithdrawal, "VESTED PART WITHDRAWAL"))
				Reporter.logEvent(Status.PASS,
						"Verify Vested Part Withdrawal section is Displayed",
						"Vested Part Withdrawal section is displayed", true);
			else 
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Vested Part Withdrawal section is Displayed",
						"Vested Part Withdrawal section is NOT displayed", true);
				throw new Error("Part Withdrawal Section is NOT displayed");
			}

			if(Web.isWebElementDisplayed(requestWithdrawal, "VESTED FULL WITHDRAWAL"))
				Reporter.logEvent(Status.PASS,
						"Verify Vested Full Withdrawal section is Displayed",
						"Vested Full withdrawal section is displayed", true);
			else 
				Reporter.logEvent(Status.FAIL,
						"Verify Vested Full Withdrawal section is Displayed",
						"Vested Full Withdrawal section is NOT displayed", true);

			// Select Part withdrawal
			Web.clickOnElement(requestWithdrawal, "VESTED FULL WITHDRAWAL");
						
			if (Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")) {
				Web.clickOnElement(requestWithdrawal, "CONTINUE");
			} else {
				throw new Error("'Continue' is not displayed");
			}

			Thread.sleep(2000);
			Web.waitForElement(requestWithdrawal, "YES");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Plan withdrawal");

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Citizenship Confirmation Page is Displayed",
						"Citizenship Confirmation Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Citizenship Confirmation Page is Displayed",
						"Citizenship Confirmation Page is Not Displayed", true);
			}

			requestWithdrawal
					.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			// Click on Yes
			lblDisplayed = Web.clickOnElement(requestWithdrawal, "YES");
			Thread.sleep(3000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Please enter your Social Security number.");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Social Security number Field is Displayed.",
						"Social Security number Field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Social Security number Field is Displayed",
						"Social Security number Field is Not Displayed", true);
			}
			// Enter SSN
			requestWithdrawal.enterSSN(Stock.GetParameterValue("SSN"));
			Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Withdrawal method");

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Not Displayed", true);
			}

			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");

			// Select Payment to Self Withdrawal Distribution
			boolean isOptionSelected=false;
			isOptionSelected=Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD",
					Stock.GetParameterValue("withdrawalMethod"));
			if (isOptionSelected)
				Reporter.logEvent(
						Status.PASS,
						"Verify if User selects "+Stock.GetParameterValue("withdrawalMethod")+" withdrawal distribution option",
						"User selects "+Stock.GetParameterValue("withdrawalMethod")+" withdrawal distribution option",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if User selects "+Stock.GetParameterValue("withdrawalMethod")+" withdrawal distribution option",
						"User did NOT select "+Stock.GetParameterValue("withdrawalMethod")+" withdrawal distribution option",
						true);

			Thread.sleep(1000);

			// Verify if Confirm your contact information section is displayed
			requestWithdrawal
					.isTextFieldDisplayed("Confirm your contact information");

			// Enter participant email address and click on continue
			Web.setTextToTextBox("EMAIL ADDRESS", requestWithdrawal,
					Stock.GetParameterValue("emailAddress"));

			Web.clickOnElement(requestWithdrawal, "CONTINUE TO WITHDRAWAL");
			Thread.sleep(4000);

			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("delivery method");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Not Displayed", true);
			}
			// Select delivery method
			requestWithdrawal.selectDeliveryMethod(Stock
					.GetParameterValue("deliveryMethod"));
			Thread.sleep(10000);
			Web.waitForElement(requestWithdrawal, "I AGREE AND SUBMIT");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Withdrawal summary");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Not Displayed", true);
			}		
			Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");
			Thread.sleep(8000);
			Web.waitForElement(requestWithdrawal, "TEXT CONFIRMATION NUMBER");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Request submitted!");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Request Submission Page is Displayed",
						"Request Submission Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Submission Page is Displayed",
						"Request Submission is Not Displayed", true);
			}			

			if (Web.isWebElementDisplayed(requestWithdrawal,
					"TEXT CONFIRMATION NUMBER", true)) {
				Reporter.logEvent(
						Status.INFO,
						"Verify Request Confirmation is Displayed on UI",
						"Request Confirmation is Displayed\n ConfirmationNO:"
								+ requestWithdrawal.getWebElementText(
										"TEXT CONFIRMATION NUMBER").trim(),
						false);

				String txtConfirmationNo = requestWithdrawal.getWebElementText(
						"TEXT CONFIRMATION NUMBER").trim();

				String dbName = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"));

				int isconfirmationDisplayed = DB
						.getRecordSetCount(DB.executeQuery(
								dbName
										+ "DB_"
										+ Common.checkEnv(Stock
												.getConfigParam("TEST_ENV")),
								Stock.getTestQuery("getWithDrawalConfirmationNo")[1],
								txtConfirmationNo, Stock
										.GetParameterValue("ind_ID")));

				if (isconfirmationDisplayed > 0) {
					Reporter.logEvent(
							Status.PASS,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"
									+ requestWithdrawal.getWebElementText(
											"TEXT CONFIRMATION NUMBER").trim(),
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation Number is NOT Populated in the DataBase",
							true);
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Confirmation Number is Displayed",
						"Request Confirmation Number is Not Displayed", true);
			}
			// Verify Other Fields in Withdrawals Confirmation Page
			requestWithdrawal.verifyWithdrawalConfirmationPage(
					enteredTotalWithdrawalAmt,
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("lblUserName"),
					Stock.GetParameterValue("deliveryMethod"),
					Stock.GetParameterValue("confirmationTxtAddress"));

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
