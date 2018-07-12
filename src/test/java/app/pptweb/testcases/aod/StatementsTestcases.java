package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.balance.Balance;
import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.ProfilePage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class StatementsTestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	static String printTestData = "";

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
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}
	
	@Test(dataProvider = "setData")
	public void AOD_Statements_DDTC_28696_TC_01_Verify_Statements_card_present_on_Account_overview_page(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.changeDeliveryOption(Stock.GetParameterValue("SSN"), "MAIL");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			//AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateStatementCard();
			myAccountOverview.verifyStmtsareVisible();
			myAccountOverview.verifyShowMoreLink(false);
			myAccountOverview.verifyDeliveryOption("You are set to receive all documents by mail. Sign up for e-delivery and benefit from:");
			myAccountOverview.clickOnEdelivery(false);
			
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
	public void AOD_Statements_DDTC_28697_TC_02_Test_to_Verify_functionality_of_Show_more_link_present_on_Statements_card(
			int itr, Map<String, String> testdata) {

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
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateStatementCard();
			myAccountOverview.verifyShowMoreLink(true);
			StatementsAndDocuments stmt = new StatementsAndDocuments();
			if(Web.isWebElementDisplayed(stmt, "Stmts On Demand Tab", true))
				Reporter.logEvent(Status.PASS,
						"Verify Statement and Documents page is loaded",
						"Statement and Documents page is loaded upon clicking Show more link from Statment Card", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Statement and Documents page is loaded",
						"Statement and Documents page is not loaded upon clicking Show more link from Statment Card", true);
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
	public void AOD_Statements_DDTC_28698_TC_03_Test_to_Verify_message_displayed_on_statements_card_when_participant_does_not_have_any_statements(
			int itr, Map<String, String> testdata) {

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
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateStatementCard();
			myAccountOverview.verifyNonStements();
			
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
	public void AOD_Statements_DDTC_28703_TC_04_Test_to_Verify_functionality_of_e_delivery_button_present_on_Statements_card(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.changeDeliveryOption(Stock.GetParameterValue("SSN"), "MAIL");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			Web.getDriver().navigate().refresh();
			myAccountOverview.validateStatementCard();
			myAccountOverview.clickOnEdelivery(true);
			if(Web.isWebElementDisplayed(mfaPage, "Label Communication Preference", true))
				Reporter.logEvent(Status.PASS,
						"Verify Change communication preference page is loaded",
						"Change communication preference page is loaded", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Change communication preference page is loaded",
						"Change communication preference page is not loaded", true);
			mfaPage.verifySaveButton();
			mfaPage.clickOnSaveButton();
			ProfilePage profiles = new ProfilePage();
			
			profiles.clickOnHomeButton();
			
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
	public void AOD_Statements_DDTC_31381_TC_04_Verify_functionality_of_e_delivery_button_when_personal_e_mail_id_is_not_present_for_ppt(
			int itr, Map<String, String> testdata) {

		try {
			String gcId[] = Stock.GetParameterValue("planId").split("-");
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.changeDeliveryOption(Stock.GetParameterValue("SSN"), "MAIL");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			
			myAccountOverview.validateStatementCard();
			myAccountOverview.clickOnEdelivery(true);
			if(Web.isWebElementDisplayed(mfaPage, "Label Communication Preference", true))
				Reporter.logEvent(Status.PASS,
						"Verify Change communication preference page is loaded",
						"Change communication preference page is loaded", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Change communication preference page is loaded",
						"Change communication preference page is not loaded", true);
			
			mfaPage.selectEdelivery(gcId[0]);
			mfaPage.verifySaveButton();
			mfaPage.clickOnSaveButton();
			ProfilePage profiles = new ProfilePage();
			profiles.verifyDeliveryMethod("E-delivery");
			profiles.clickOnHomeButton();
			sLeftNav.get();
			myAccountOverview.verifyDeliveryOption("You are set to receive all documents by e-delivery. With e-delivery you will benefit from:");
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
				AccountOverview myAccountOverview=new AccountOverview();
				myAccountOverview.changeDeliveryOption(Stock.GetParameterValue("SSN"),"ELECTRONIC");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Statements_DDTC_28728_TC_05_Verify_message_displayed_when_participant_participant_has_already_opted_in_for_e_delivery(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.changeDeliveryOption(Stock.GetParameterValue("SSN"),"ELECTRONIC");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			Web.getDriver().navigate().refresh();
			myAccountOverview.validateStatementCard();
			myAccountOverview.verifyDeliveryOption("You are set to receive all documents by e-delivery. With e-delivery you will benefit from:");
			
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
	public void AOD_Statements_DDTC_28730_TC_06_Test_to_Verify_functionality_of_link_present_on_Statements_card(
			int itr, Map<String, String> testdata) {

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
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateStatementCard();
			myAccountOverview.verifyStmtsareVisible();
			myAccountOverview.clickOnFirstStmts();
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
