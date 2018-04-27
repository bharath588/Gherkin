	package app.pptweb.testcases.withdrawals;
	
	import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.withdrawals.RequestWithdrawal;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
	
	public class withdrawalstestcases {
	
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
		public void Withdrawals_Age59_And_Half_WithdrawalMethods(
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
	
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_InService_Age59_And_Half_Max_Amount(int itr,
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
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.selectMaxAmountCheckBoxForInService(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	
		@Test(dataProvider = "setData")
		public void Withdrawals_In_Service_WithdrawalMethods(int itr,
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
	
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.selectMaxAmountCheckBoxForSepService(Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void WithDrawals_SepService_PWD_WithdrawalMethods(int itr,
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		@Test(dataProvider = "setData")
		public void WithDrawals_SepService_FWD_Payment_To_Self(int itr,
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}	
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_Combo_PTS_With_RollQual_RollIRA(int itr,
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
				requestWithdrawal.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));
				requestWithdrawal.verify_FWD_PartialPayments_WithdrawalConfirmation(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));
	
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
					RequestWithdrawal.resetTotalValues();				
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		 
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Sep_Service_FWD_RollOver(int itr,
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
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));			
				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
			
		@Test(dataProvider = "setData")
		public void Withdrawals_Age59_And_Half_RollQual_GDR(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
	
				//Update GDR Status	
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
				
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
						
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		
		@Test(dataProvider = "setData")
		public void Withdrawals_InService_MaxAmount_GDR(
				int itr, Map<String, String> testdata) {
	
			try {
				boolean isLabelDisplayed = false;			
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
				
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
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.selectMaxAmountCheckBoxForInServiceGDR(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.isMaxGDR=false;
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	
		}
	
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_MaxAmount_GDR(
				int itr, Map<String, String> testdata) {
	
			try {
				boolean isLabelDisplayed = false;			
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),
						"PART WD");
				
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
			
				requestWithdrawal.selectMaxAmountCheckBoxForSepServiceGDR(Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.isMaxGDR=false;
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_InService_CitizenShip_Validation_Non_US(int itr,
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
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
					requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));			
									
				Web.waitForElement(requestWithdrawal, "CONTINUE");
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")){
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Reporter.logEvent(
							Status.PASS,
							"Enter withdrawal amount for multiple / single  money type sources and click Continue",
							"User enters the withdrawal amount for multiple money types source and clicked on continue button",
							false);
				}
				else{
					Reporter.logEvent(
							Status.FAIL,
							"Enter withdrawal amount for both Roth / Pre-tax  money type sources and Click Continue",
							"Continue button is not displayed in Request a Withdrawal Page",
							true);
					throw new Error("'Continue' is not displayed");
				}
				
				
				Web.waitForElement(requestWithdrawal, "NO");
				Thread.sleep(2000);
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");			
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");			
				lblDisplayed = Web.clickOnElement(requestWithdrawal, "NO");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				//Common.waitForProgressBar();
				if(Web.isWebElementDisplayed(requestWithdrawal, "GENERIC ALERT ICON"))
					Reporter.logEvent(Status.INFO, "Verify Alert Message has been displayed if the participants selects \"No\"",
							"The Alert Message has been displayed \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"), false);
				requestWithdrawal.isTextFieldDisplayed("Your paperwork will be emailed to:");	
				Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal, Stock.GetParameterValue("emailAddress"));
				Web.clickOnElement(requestWithdrawal, "EMAIL FORM");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if(Web.isWebElementDisplayed(requestWithdrawal, "PLAN WITHDRAWAL WARNING", true))
					Reporter.logEvent(Status.INFO, "Verify confirmation page has been displayed", "The confirmation page has been displayed: \n"
							+requestWithdrawal.getWebElementText("PLAN WITHDRAWAL WARNING"), true);		
				
	
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
		public void Withdrawal_SepService_CitizenShip_Validation_Non_US(int itr,
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
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
						
				Web.waitForElement(requestWithdrawal, "CONTINUE");
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")){
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Reporter.logEvent(
							Status.PASS,
							"Enter withdrawal amount for multiple / single  money type sources and click Continue",
							"User enters the withdrawal amount for multiple money types source and clicked on continue button",
							false);
				}
				else{
					Reporter.logEvent(
							Status.FAIL,
							"Enter withdrawal amount for both Roth / Pre-tax  money type sources and Click Continue",
							"Continue button is not displayed in Request a Withdrawal Page",
							true);
					throw new Error("'Continue' is not displayed");
				}
				Web.waitForElement(requestWithdrawal, "NO");
				Thread.sleep(2000);
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");			
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");			
				lblDisplayed = Web.clickOnElement(requestWithdrawal, "NO");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				//Common.waitForProgressBar();
				if(Web.isWebElementDisplayed(requestWithdrawal, "GENERIC ALERT ICON"))
					Reporter.logEvent(Status.INFO, "Verify Alert Message has been displayed if the participants selects \"No\"",
							"The Alert Message has been displayed \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"), false);
				requestWithdrawal.isTextFieldDisplayed("Your paperwork will be emailed to:");		
				Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal, Stock.GetParameterValue("emailAddress"));
				Web.clickOnElement(requestWithdrawal, "EMAIL FORM");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if(Web.isWebElementDisplayed(requestWithdrawal, "PLAN WITHDRAWAL WARNING", true))
					Reporter.logEvent(Status.INFO, "Verify confirmation page has been displayed", "The confirmation page has been displayed: \n"
							+requestWithdrawal.getWebElementText("PLAN WITHDRAWAL WARNING"), true);	
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
		public void Withdrawal_SepService_PWD_Multiple_Money_Sources_GDR(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status	
				
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),"PART WD");
				
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
			
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Age59_And_Half_Roll_IRA_GDR(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_Multiple_Money_Sources(int itr,
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_MaxAvail_SepService_PartialWithdrawal_Single_Money_Source(int itr,
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.verifyMaxAmount_SingleMoneySource_Pwd();			
	
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_Messaging(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			int rowUpdated=0;
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				String expectedMsg="If you are not finding the option you are looking for, please contact a retirement specialist at 1-800-338-4015.";
				String actualErrMsg=requestWithdrawal.getWebElementText("SEP SERVICE MESSAGING");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify the Message is displayed under the withdrawal reasons available",
							"The following message is been displayed under the Withdrawal Reasons \n" +
					actualErrMsg, true);
				else
					Reporter.logEvent(Status.PASS, "Verify the Message is displayed under the withdrawal reasons available",
							"The following message is been displayed under the Withdrawal Reasons \n" +
					actualErrMsg, true);			
	
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
		public void Withdrawal_Participant_With_Default_DOB(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			int rowUpdated=0;
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				// Set Data for default DOB
				String[] updateQueryForDefaultDob=Stock.getTestQuery("getDefaultDOBPpt");	
				updateQueryForDefaultDob[0]= Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
	
				rowUpdated=DB.executeUpdate(updateQueryForDefaultDob[0], updateQueryForDefaultDob[1],"Y",Stock.GetParameterValue("SSN"));
				if(rowUpdated>0)
				{
					System.out.println("DOB Date has been set for the ppt");
					Reporter.logEvent(Status.PASS, "Verify DefaultDOB has been set the for the Participant", 
							"Default DOB has been set for Participant in the Database", false);
				}
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				//String expectedMsg="Your request cannot be completed on-line. Please call a Participant Services Representative at 1-800-338-4015 to complete your request.";
				String expectedMsg=Stock.GetParameterValue("expectedMsg");
				String actualErrMsg=requestWithdrawal.getWebElementText("GENERIC ERROR MESSAGE");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify if the Participant with default DOB, should not be allowed to withdraw the amount",
							"The Participant with DOB is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant with default DOB, should not be allowed to withdraw the amount",
							"The Participant with Default DOB is allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, true);				
	
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
					//default DOB Data is Revereted Back
					String[] updateQueryForDefaultDob=Stock.getTestQuery("getDefaultDOBPpt");
					updateQueryForDefaultDob[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					rowUpdated=DB.executeUpdate(updateQueryForDefaultDob[0], updateQueryForDefaultDob[1],null,Stock.GetParameterValue("SSN"));
					if(rowUpdated>0)
					{
						System.out.println("Default DOB Date has been revereted back for the ppt");				
					}
					
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_Participant_With_ForeignAddress(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			int rowUpdated=0;
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				// Set Data for PPT with Foreign Address
				String[] updateQueryForForeignAddress=Stock.getTestQuery("getPptWithForeignAddress");
				updateQueryForForeignAddress[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				rowUpdated=DB.executeUpdate(updateQueryForForeignAddress[0], updateQueryForForeignAddress[1],"CA",Stock.GetParameterValue("ind_ID"));
				if(rowUpdated>0)
				{
					System.out.println("Ppt address has been updated to Foreign Address");
					Reporter.logEvent(Status.PASS, "Verify Foreign Address has been set the for the Participant", 
							"Foreign Addres has been set for Participant in the Database", false);
				}
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				String expectedMsg=Stock.GetParameterValue("expectedMsg");
				String actualErrMsg=requestWithdrawal.getWebElementText("GENERIC ERROR MESSAGE");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify if the Participant with Foreign Address, should not be allowed to withdraw the amount",
							"The Participant with Foreign Address is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant with Foreign Address, should not be allowed to withdraw the amount",
							"The Participant with Foreign Address is allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, true);				
	
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
					//default Foreign Address Data is Reverted Back
					String[] updateQueryForForeignAddress=Stock.getTestQuery("getPptWithForeignAddress");
					updateQueryForForeignAddress[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					rowUpdated=DB.executeUpdate(updateQueryForForeignAddress[0], updateQueryForForeignAddress[1],"US",Stock.GetParameterValue("ind_ID"));
					if(rowUpdated>0)				
					System.out.println("Ppt address has been reverted back to US Address");
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		
		@Test(dataProvider = "setData")
		public void Withdrawal_Participant_With_SDB_Account(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;	
			Actions keyBoard = new Actions(Web.getDriver());
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
				requestWithdrawal.get();
				Thread.sleep(1000);
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
				if(Web.isWebElementDisplayed(requestWithdrawal,"YES"))
				{
						//inpCurrentEmployerYes.click();
						Web.clickOnElement(requestWithdrawal,"NO");				
						//Thread.sleep(3000);
						keyBoard.sendKeys(Keys.TAB).perform();
						keyBoard.sendKeys(Keys.ENTER).perform();
						
				}
				Web.waitForPageToLoad(Web.getDriver());
				Common.waitForProgressBar();
				String expectedMsg="Your plan has a Self-Directed Brokerage (SDB) account. A form must be sent to you and returned for processing. This transaction will not be submitted until we receive this form back from you in good order.";
				String actualErrMsg=requestWithdrawal.getWebElementText("GENERIC ALERT ICON");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify if the Participant with SDB Account, should not be allowed to withdraw the amount",
							"The Participant with SDB Account is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant with SDB Account, should not be allowed to withdraw the amount",
							"The Participant with SDB Account is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				Thread.sleep(1000);
				requestWithdrawal.isTextFieldDisplayed("Your paperwork will be emailed to:");
				//Verify Invalid Email Address
				Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal,"test123");
				Reporter.logEvent(Status.INFO, "Enter Invalid email Address", "Entered Invalid email address: test123", false);
				if(!requestWithdrawal.isWebElementEnabled("EMAIL FORM"))
					Reporter.logEvent(Status.PASS, "Verify after entering invalid Email Addres, Email-form button is Disabled",
							"The Email-Form button is disabled after entering Invalid Email Address",false);
				else
					Reporter.logEvent(Status.FAIL, "Verify after entering invalid Email Addres, Email-form button is Disabled",
							"The Email-Form button is NOT disabled after entering Invalid Email Address",false);
				//Verify Text displayed below email id field
				String exptdMsg="This email address will only be used for this form. To make a permanent change, please go to your profile page by clicking on your name at the top right of your screen.";
				String actualMsg=requestWithdrawal.getWebElementText("SDB EMAIL CONTENT");
				if(Web.VerifyText(exptdMsg, actualMsg, true))
					Reporter.logEvent(Status.PASS, "Verify the message text displayed right below the email address field.",
							"The following message text has been displayed \n"+actualMsg,false);								
				else
					Reporter.logEvent(Status.FAIL, "Verify the message text displayed right below the email address field.",
							"The following message text has been displayed \n"+actualMsg,false);
				//Enter Valid Email Address							
				Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal, Stock.GetParameterValue("emailAddress"));
				Web.clickOnElement(requestWithdrawal, "EMAIL FORM");
				Web.waitForPageToLoad(Web.getDriver());
				Common.waitForProgressBar();
				if(Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON",true))
						Reporter.logEvent(Status.PASS, "Verify the confirmation page has been displayed",
								"The confirmation page has been displayed with the following content \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"),true);
				else
					Reporter.logEvent(Status.FAIL, "Verify the confirmation page has been displayed",
							"The confirmation page has been displayed with the inapropriate content \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"),true);
					
				requestWithdrawal.isTextFieldDisplayed("A form has been emailed");
				requestWithdrawal.isTextFieldDisplayed("If you have additional questions");
				
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
		public void Withdrawal_InService_AGE_59_And_Half_Source_Hierarchy_Remove_Max_Available(
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
	
				// Verify if Withdrawal Page is displayed
				isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true);
				if (isLabelDisplayed)
					Reporter.logEvent(Status.INFO,"Verify  if Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is displayed successfully",true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed","Request A Withdrawal Page is NOT displayed successfully",
							true);
				requestWithdrawal.selectSourceHierarchyForInService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.isSourceHierarchy=false;
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	
		}
		
		
		@Test(dataProvider = "setData")
		public void Withdrawal_Ppt_With_File_Restrictions(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			int rowUpdated=0;
			String[] updateQueryForFileRestriction=Stock.getTestQuery("updatePptFileRestrictionStatus");
			
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				// Set Data for PPT with Foreign Address
				updateQueryForFileRestriction[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));		
				rowUpdated=DB.executeUpdate(updateQueryForFileRestriction[0], updateQueryForFileRestriction[1],"FILE",Stock.GetParameterValue("ind_ID"));
				if(rowUpdated>0)
				{
					System.out.println("Ppt  has been updated to File Restriction");
					Reporter.logEvent(Status.PASS, "Verify participant has been set the for the File Restriction Status", 
							"File Restriction Status has been set for Participant in the Database", false);
				}
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				//String expectedMsg="Your request cannot be completed on-line. Please call a Participant Services Representative at 1-800-338-4015 to complete your request.";
				String expectedMsg=Stock.GetParameterValue("expectedMsg");
				String actualErrMsg=requestWithdrawal.getWebElementText("GENERIC ERROR MESSAGE");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify if the Participant with File restriction, should not be allowed to withdraw the amount",
							"The Participant with File restrictions is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant with File restriction, should not be allowed to withdraw the amount",
							"The Participant with File restriction is allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, true);				
	
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
					// File Restriction Data is Reverted Back
					updateQueryForFileRestriction[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					rowUpdated=DB.executeUpdate(updateQueryForFileRestriction[0], updateQueryForFileRestriction[1],null,Stock.GetParameterValue("ind_ID"));
					if(rowUpdated>0)				
					System.out.println("Ppt file restriction has been reverted back");
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_Ppt_With_TakeOver_Account(int itr,
				Map<String, String> testdata) {
			boolean lblDisplayed = false;
			int rowUpdated=0;
			String[] updateQueryForTakeOverAccount=Stock.getTestQuery("updatePptTakeOverAccount");
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				// Set Data for PPT with Foreign Address
				updateQueryForTakeOverAccount[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));			
				rowUpdated=DB.executeUpdate(updateQueryForTakeOverAccount[0], updateQueryForTakeOverAccount[1],"T",Stock.GetParameterValue("ind_ID"));
				if(rowUpdated>0)
				{
					System.out.println("Ppt  has been updated to Take Over Account");
					Reporter.logEvent(Status.PASS, "Verify participant has been set the for the Take Over Account", 
							"Take Over Account has been set for Participant in the Database", false);
				}
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
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
				//String expectedMsg="Your request cannot be completed on-line. Please call a Participant Services Representative at 1-800-338-4015 to complete your request.";
				String expectedMsg=Stock.GetParameterValue("expectedMsg");
				String actualErrMsg=requestWithdrawal.getWebElementText("GENERIC ERROR MESSAGE");
				if(expectedMsg.equalsIgnoreCase(actualErrMsg))
					Reporter.logEvent(Status.PASS, "Verify if the Participant with Take Over Account (Death or Alternate Payee), should not be allowed to withdraw the amount",
							"The Participant with Take Over Account is NOT allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant with Take Over Account (Death or Alternate Payee), should not be allowed to withdraw the amount",
							"The Participant with Take Over Account is allowed to withdraw amount and the following message is been displayed \n" +
					actualErrMsg, true);				
	
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
					// File Restriction Data is Reverted Back
					updateQueryForTakeOverAccount[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					rowUpdated=DB.executeUpdate(updateQueryForTakeOverAccount[0], updateQueryForTakeOverAccount[1],"N",Stock.GetParameterValue("ind_ID"));
					if(rowUpdated>0)				
					System.out.println("Ppt with Take Over Account has been reverted back");
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_SpousalConsent_Above_5K_Threshold_Single(int itr,
				Map<String, String> testdata) {
			int spousalRowUpdated=0;
			int maritalStatusUpdated=0;
			String[] updateMaritalStatus=Stock.getTestQuery("updatePptMaritalStatus");
			String[] updateQuery=Stock.getTestQuery("getSpousalConsentPpt");
			String gcId[]=Stock.GetParameterValue("planId").split("-");
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Get Data for Spousal Consent		
				updateMaritalStatus[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				maritalStatusUpdated=DB.executeUpdate(updateMaritalStatus[0], updateMaritalStatus[1],"S",Stock.GetParameterValue("ind_ID"));
				spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"Y","N",gcId[0]);
				
				if(spousalRowUpdated>0 && maritalStatusUpdated >0)
				{
				System.out.println("Spousal consent Data is ready");
				Reporter.logEvent(Status.PASS, "Verify Spousal Consent Data is Set Up for the participant",
						"Spousal Consent Data is Set up for this participant and Participant Marital Status is Single", false);
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
							
				if(requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType")))		
					requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));	
				int totalVestedBalance=(int)Math.round(Web.getIntegerCurrency(requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE")));
				if(totalVestedBalance>5000)
					Reporter.logEvent(Status.PASS, "Verify if the Participant is having Above $5K Balance",
							"The Participant Balance is above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant is having Above $5K Balance", "The Participant Balance is NOT above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
				{
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Common.waitForProgressBar();
				}
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
	
				if (!Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON"))			
					Reporter.logEvent(Status.PASS,
							"Verify Spousal Consent Banner is NOT Displayed For Participant Who is Single",
							"In the Request A Withdrawal Page Spousal Consent Banner is NOT Displayed", false);
				 else {
					Reporter.logEvent(Status.FAIL,
							"Verify Spousal Consent Banner is NOT Displayed For Participant Who is Single",
							"In the Request A Withdrawal Page Spousal Consent Banner is  Displayed", false);
					throw new Error("Spousal Consent is Displayed in Withdrawals Page");
				}
				
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();			
				Thread.sleep(2000);
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());
				
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Spousal Consent Data is Set Up", "Spousal Consent Data is NOT Set up for this participant", false);
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
					updateMaritalStatus[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					maritalStatusUpdated=DB.executeUpdate(updateMaritalStatus[0], updateMaritalStatus[1], "M",Stock.GetParameterValue("ind_ID"));				
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"N","N",gcId[0]);
					
					if(spousalRowUpdated>0 && maritalStatusUpdated >0)
						System.out.println("Spousal consent Data is reverted back");
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		@Test(dataProvider = "setData")
		public void Withdrawal_SpousalConsent_Below_5K_Threshold_InService(int itr,
				Map<String, String> testdata) {
			int spousalRowUpdated=0;
			String[] updateQuery=Stock.getTestQuery("getSpousalConsentPpt");
			String gcId[]=Stock.GetParameterValue("planId").split("-");
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Get Data for SPousal Consent
				updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"Y","N",gcId[0]);
				if(spousalRowUpdated>0)
				{
					System.out.println("Spousal consent Data is ready");
				Reporter.logEvent(Status.INFO, "Verify Spousal Consent Data is Set Up for the participant",
						"Spousal Consent Data is Set up for this participant whose Balance is Below $5K", false);
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
			
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				int totalVestedBalance=(int)Math.round(Web.getIntegerCurrency(requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE")));
				if(totalVestedBalance<5000)
					Reporter.logEvent(Status.PASS, "Verify if the Participant is having Below $5K Balance and if the Spousal Consent Value is set, participant will go through normal Withdrawals flow",
							"The Participant Balance is below $5k and he wil go through normal withdrawals work flow and the available balance is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant is having Below $5K Balance", "The Participant Balance is NOT below $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
					Web.clickOnElement(requestWithdrawal, "CONTINUE");			
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				
				Thread.sleep(1000);
				Common.waitForProgressBar();
				if (!Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON"))			
					Reporter.logEvent(Status.PASS,
							"Verify Spousal Consent Banner is NOT Displayed For Participant whose Balance is Below $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is NOT Displayed", false);
				 else {
					Reporter.logEvent(Status.FAIL,
							"Verify Spousal Consent Banner is NOT Displayed For Participant Balance is Below $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is Displayed", false);
					throw new Error("Spousal Consent is Displayed in Withdrawals Page");
				}
			
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());
				
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Spousal Consent Data is Set Up", "Spousal Consent Data is NOT Set up for this participant", false);
				
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
					//update the participant status from Spousal Consent
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"N","N",gcId[0]);
					if(spousalRowUpdated>0)
						System.out.println("Spousal consent Data is reverted back");
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SpousalConsent_Above_5K_Threshold_InService(int itr,
				Map<String, String> testdata) {
				int spousalRowUpdated=0;
				String[] updateQuery=Stock.getTestQuery("getSpousalConsentPpt");
				String gcId[]=Stock.GetParameterValue("planId").split("-");
				try {
					Reporter.initializeReportForTC(itr,
							Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
									+ Stock.getConfigParam("BROWSER"));
					lib.Reporter.logEvent(Status.INFO,
							"Test Data used for this Test Case:", printTestData(),
							false);
					//Get Data for Spousal Consent
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"Y","N",gcId[0]);	
				if(spousalRowUpdated>0)
				{
					System.out.println("Spousal consent Data is ready");
				Reporter.logEvent(Status.PASS, "Verify Spousal Consent Data is Set Up for the participant", 
						"Spousal Consent Data is Set up for this participant whose balance is above $5K", false);
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				int totalVestedBalance=(int)Math.round(Web.getIntegerCurrency(requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE")));
				if(totalVestedBalance>5000)
					Reporter.logEvent(Status.PASS, "Verify if the Participant is having Above $5K Balance",
							"The Participant Balance is above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant is having Above $5K Balance", "The Participant Balance is NOT above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
							
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
					Web.clickOnElement(requestWithdrawal, "CONTINUE");			
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");	
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				Thread.sleep(1000);
				if (Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON"))			
					Reporter.logEvent(Status.PASS,
							"Verify Spousal Consent Banner is  Displayed For Participant whose balance is above $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is  Displayed", false);
				 else {
					Reporter.logEvent(Status.FAIL,
							"Verify Spousal Consent Banner is NOT Displayed For Participant Whose balance is above $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is NOT Displayed", false);
					throw new Error("Spousal Consent is NOT Displayed in Withdrawals Page");
				}
			
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());
				
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);			
				requestWithdrawal.verifyWithdrawalConfForSpousalConsentPpts(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"));
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Spousal Consent Data is Set Up", "Spousal Consent Data is NOT Set up for this participant", false);	
				
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
					//update the participant status from Spousal Consent	
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"N","N",gcId[0]);
					if(spousalRowUpdated>0)
						System.out.println("Spousal consent Data is reverted back");
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();				
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_SpousalConsent_Below_5K_Threshold_SepService(int itr,
				Map<String, String> testdata) {
			int spousalRowUpdated=0;
			String[] updateQuery=Stock.getTestQuery("getSpousalConsentPpt");
			String gcId[]=Stock.GetParameterValue("planId").split("-");		
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Get Data for SPousal Consent
				updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"Y","N",gcId[0]);		
				if(spousalRowUpdated>0)
				{
					System.out.println("Spousal Consent Data is ready");
				Reporter.logEvent(Status.PASS, "Verify Spousal Consent Data is Set Up for the participant",
						"Spousal Consent Data is Set up for this participant whose balance is below $5K", false);
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
							
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));				
				int totalVestedBalance=(int)Math.round(Web.getIntegerCurrency(requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE")));
				if(totalVestedBalance<5000)
					Reporter.logEvent(Status.PASS, "Verify if the Participant is having Below $5K Balance",
							"The Participant Balance is Below $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant is having Below $5K Balance", 
							"The Participant Balance is NOT below $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
					Web.clickOnElement(requestWithdrawal, "CONTINUE");			
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				Thread.sleep(1000);
				if (!Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON"))			
					Reporter.logEvent(Status.PASS,
							"Verify Spousal Consent Banner is NOT Displayed For Participant whose balance is below $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is NOT Displayed", false);
				 else {
					Reporter.logEvent(Status.FAIL,
							"Verify Spousal Consent Banner is NOT Displayed For Participant whose balance is below $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is  Displayed", false);
					throw new Error("Spousal Consent is Displayed in Withdrawals Page");
				}
			
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());
				
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Spousal Consent Data is Set Up", "Spousal Consent Data is NOT Set up for this participant", false);
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
					//update the participant status from Spousal Consent				
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"N","N",gcId[0]);
					if(spousalRowUpdated>0)
						System.out.println("Spousal consent Data is reverted back");
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawal_SpousalConsent_Above_5K_Threshold_SepService(int itr,
				Map<String, String> testdata) {
			int spousalRowUpdated=0;
			String[] updateQuery=Stock.getTestQuery("getSpousalConsentPpt");
			String gcId[]=Stock.GetParameterValue("planId").split("-");		
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Get Data for Spousal Consent
				updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) 
						+ "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"Y","N",gcId[0]);
				if(spousalRowUpdated>0)
				{
					System.out.println("Spousal consent Data is ready");
					Reporter.logEvent(Status.PASS, "Verify Spousal Consent Data is Set Up for the participant",
							"Spousal Consent Data is Set up for this participant and Participant whose balance is above $5K", false);
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
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType")
						,Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));	
				 int totalVestedBalance=(int)Math.round(Web.getIntegerCurrency(requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE")));
				if(totalVestedBalance>5000)
					Reporter.logEvent(Status.PASS, "Verify if the Participant is having Above $5K Balance", "The Participant Balance is above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the Participant is having Above $5K Balance", "The Participant Balance is NOT above $5k which is: "					
							+requestWithdrawal.getWebElementText("TEXT TOTAL VESTED BALANCE"),false);
				
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
					Web.clickOnElement(requestWithdrawal, "CONTINUE");			
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				Thread.sleep(1000);
				if (Web.isWebElementDisplayed(requestWithdrawal,"GENERIC ALERT ICON"))			
					Reporter.logEvent(Status.PASS,
							"Verify Spousal Consent Banner is Displayed For Participant whose balance is above $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is  Displayed", false);
				 else {
					Reporter.logEvent(Status.FAIL,
							"Verify Spousal Consent Banner is NOT Displayed For Participant whose balance is above $5K",
							"In the Request A Withdrawal Page Spousal Consent Banner is NOT Displayed", false);
					throw new Error("Spousal Consent is NOT Displayed in Withdrawals Page");
				}			
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());	
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);			
				requestWithdrawal.verifyWithdrawalConfForSpousalConsentPpts(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"));
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Spousal Consent Data is Set Up", "Spousal Consent Data is NOT Set up for this participant", false);	
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
					//update the participant status from Spousal Consent
					updateQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName")) 
							+ "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
					spousalRowUpdated=DB.executeUpdate(updateQuery[0], updateQuery[1],"N","N",gcId[0]);
					if(spousalRowUpdated>0)
						System.out.println("Spousal consent Data is reverted back");
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Combos_Single_Money_Source(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
				/*requestWithdrawal.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));
				//Needed
				requestWithdrawal.verify_FWD_PartialPayments_WithdrawalConfirmation(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));*/
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_RetirementReason_SepService_PWD(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed for the participant with RETIREMENT Reason", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed for the participant with RETIREMENT Reason", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_RetirementReason_SepService_FWD(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		@Test(dataProvider = "setData")
		public void Withdrawals_RetirementReason_SepService_Combo_FWD(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccount"));
				requestWithdrawal.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));
				requestWithdrawal.verify_FWD_PartialPayments_WithdrawalConfirmation(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("PTSDeliveryMethod"),
						Stock.GetParameterValue("rollOverDeliveryMethod"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Age_59AndHalf_Roll_IRA_With_GDR(int itr,
				Map<String, String> testdata) {			
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
								
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_PartWD_RollQUAL_withGDR_Pretax(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
							
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));		
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);	
				if(!Web.isWebElementDisplayed(requestWithdrawal, "GDR CONTENT"))
					Reporter.logEvent(Status.INFO, "Verify for this participant, Withdrawal Summary page is Displayed",
							"Withdrawal Summary Page is displayed instead of GDR content",false);		
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_Pwd_Roll_IRA_No_GDR(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);	
				if(!Web.isWebElementDisplayed(requestWithdrawal, "GDR CONTENT"))
					Reporter.logEvent(Status.INFO, "Verify for this participant, Withdrawal Summary page is Displayed",
							"Withdrawal Summary Page is displayed instead of GDR content",false);
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_SepService_PWD_Roll_IRA_With_GDR(int itr,
				Map<String, String> testdata) {			
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_PartWD_PTS_With_GDR_Pretax(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_PartWD_PTS_With_GDR_Pretax_BTK1(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				//requestWithdrawal.verifyMsgIfMaxAmountEntered_BothMoneySourceType();
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Age59AndHalf_PTS_No_GDR_Roth(int itr,
				Map<String, String> testdata) {			
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
					
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);	
				if(!Web.isWebElementDisplayed(requestWithdrawal, "GDR CONTENT"))
					Reporter.logEvent(Status.INFO, "Verify for this participant, Withdrawal Summary page is Displayed",
							"Withdrawal Summary Page is displayed instead of GDR content",false);
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_FWD_GDR_On_BTK(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"),Stock.GetParameterValue("GDR_DSMDCode"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}			
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.verifyGDRRestrictionIconForFWD(Stock.GetParameterValue("withdrawalType"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				//requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),false);	
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_InService_PTS_withGDR(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);
				//Update GDR Status		
				Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
						Stock.GetParameterValue("GDR_TaxReasonCode"),
						Stock.GetParameterValue("GDR_SDMTCode"),
						Stock.GetParameterValue("GDR_SEQNBR"));
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
	
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"),true);
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),
						Stock.GetParameterValue("withdrawalMethod"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummaryforGDRPpts(Stock.GetParameterValue("deliveryMethod"));			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					//Update GDR Status		
					Common.updateGDRStatus(Stock.GetParameterValue("planId"),Stock.GetParameterValue("GDR_DSRSCode"),
							"ROLL QUAL",
							Stock.GetParameterValue("GDR_SDMTCode"),
							Stock.GetParameterValue("GDR_SEQNBR"));
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Age59AndHalf_To_Pwd(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//Age 59 1/2 scenario
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyEmpQueIsDisplayedOrInServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom"));
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));			
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_InService_To_Fwd(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//In Service scenario
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);
				
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyEmpQueIsDisplayedOrInServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom"));
				
				//FWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));			
				/*requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));*/				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),false);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Age_59AndHalf_MaxAmount_To_Age59AndHalf_Withdrawal(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);		
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//Age 59 1/2 Max Amount
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.selectMaxAmountCheckBoxForInService(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);
				
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueIsDisplayedOrInServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))						
				requestWithdrawal.verifyEnteredInServiceAmount_MaxAmount(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));
				
						
				//Age 59 1/2 Withdrawal
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeTo"));		
				requestWithdrawal.unSelectWithdrawalType_InService(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("isRothAvailFrom"));
				requestWithdrawal.unSelectWithdrawalType_InService(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("isNonRothAvailFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Fwd_Combo_To_Pwd_RollOver(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//FWD Combo
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				//requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"),Stock.GetParameterValue("isNonRothAvail"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccountFrom"));
				requestWithdrawal.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(Stock.GetParameterValue("PTSDeliveryMethodFrom"),
						Stock.GetParameterValue("rollOverDeliveryMethodFrom"));			
				
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom"));
								
				//PWD Roll Qual
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("withdrawalMethodTo"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
				
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Fwd_RollOver_To_Pwd_RollOver(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//FWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				/*requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));	*/		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccountFrom"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),false);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom"));
				
				//pwd scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"),
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
				
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Age59AndHalf_To_Age59AndHalf_WithChange(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//Age 59 1/2 Max Amount
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);
				
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueIsDisplayedOrInServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))						
					requestWithdrawal.verifyEnteredInServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),
							Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));
				
						
				//Age 59 1/2 Withdrawal
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeTo"));
				/*requestWithdrawal.unSelectWithdrawalType_InService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));*/
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_InService_To_InService_WithoutChange(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//In Service
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));				
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);
				
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueIsDisplayedOrInServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))						
				requestWithdrawal.verifyEnteredInServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("isRothAvailFrom"),Stock.GetParameterValue("isNonRothAvailFrom"));
				
						
				//In Service Without Change
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeTo"));
				/*requestWithdrawal.unSelectWithdrawalType_InService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));*/
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_PWD_RollOver_To_FWD_RollOver(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))
					
					requestWithdrawal.verifyEnteredSepServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				
				//fwd scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccountTo"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),false);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
				
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_PWD_PTS_To_FWD_Combo(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))
					requestWithdrawal.verifyEnteredSepServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				
				//fwd scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"),
						Stock.GetParameterValue("emailAddress"),Stock.GetParameterValue("rollingOverAccountTo"));	
				requestWithdrawal.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(Stock.GetParameterValue("PTSDeliveryMethodTo"),
						Stock.GetParameterValue("rollOverDeliveryMethodTo"));	
				requestWithdrawal.verify_FWD_PartialPayments_WithdrawalConfirmation(Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("PTSDeliveryMethodTo"),
						Stock.GetParameterValue("rollOverDeliveryMethodTo"));
				
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_PWD_MaxAmount_To_PWD(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.selectMaxAmountCheckBoxForSepService(Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))
					requestWithdrawal.verifyEnteredSepServicePWD_MaxAmount(Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				
				
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.unSelectWithdrawalType_SepService(Stock.GetParameterValue("isRothAvailFrom"));
				requestWithdrawal.unSelectWithdrawalType_SepService(Stock.GetParameterValue("isNonRothAvailFrom"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));			
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("withdrawalMethodTo"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_PWD_To_PWD_WithChange(int itr,
				Map<String, String> testdata) {
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
				
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))		
					myAccountPage.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
				
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))
					requestWithdrawal.verifyEnteredSepServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
							Stock.GetParameterValue("isNonRothAvailFrom"));
				
				//fwd scenarios add
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				/*requestWithdrawal.unSelectWithdrawalType_SepService(Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));*/
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));			
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("withdrawalMethodTo"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_PWD_To_PWD_With_No_Change(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//PWD Scenario
				requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));		
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);			
							
				//Caching Scenario
				requestWithdrawal.clickOnBackButton();	
				requestWithdrawal.verifyWithdrawalMethodCachingPage(Stock.GetParameterValue("withdrawalTypeFrom"),
						Stock.GetParameterValue("withdrawalMethodFrom"));	
				requestWithdrawal.clickOnBackButton();
				requestWithdrawal.verifyPlanWithdrawalCachingPage();
				requestWithdrawal.clickOnBackButton();
				if(!requestWithdrawal.verifyEmpQueDisplayedOrSepServiceSelected(Stock.GetParameterValue("withdrawalTypeFrom")))
					requestWithdrawal.verifyEnteredSepServiceAmount(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				/*//Mosin
				else
					if (Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE",true)) 
						Web.clickOnElement(requestWithdrawal, "CONTINUE");*/
					
				
				
				/*requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.unSelectWithdrawalType_SepService(Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));	
				requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));*/
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));			
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),
						Stock.GetParameterValue("withdrawalMethodTo"), Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);			
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_Caching_Multiple_Plans(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				//Age 59 1/2 scenario
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeFrom"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isRothAvailFrom"), 
						Stock.GetParameterValue("isNonRothAvailFrom"));
				//requestWithdrawal.enterWithdrawalAmountForAfterTax(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("isAfterTaxAvailFrom"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeFrom"),Stock.GetParameterValue("withdrawalMethodFrom"), 
						Stock.GetParameterValue("emailAddress"));			
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodFrom"),true);	
					
				//Navigate to Second Plan
				Web.selectDropDownOption(requestWithdrawal,"MULTIPLE PLANS DROPDOWN",
						Stock.GetParameterValue("secondPlanName"));	
				Common.waitForProgressBar();
				requestWithdrawal.get();
							
				//Age 59 1/2
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalTypeTo"));
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("isRothAvailTo"), 
						Stock.GetParameterValue("isNonRothAvailTo"));			
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("withdrawalMethodTo"), 
						Stock.GetParameterValue("emailAddress"));		
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethodTo"),true);					
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalTypeTo"),Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("withdrawalMethodTo"),Stock.GetParameterValue("deliveryMethodTo"));
								
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void Withdrawals_InServ_PWD_FWD_CurrentBalance_Less_Than_Plan_Minimum(int itr,
				Map<String, String> testdata) {
			try {
				Reporter.initializeReportForTC(itr,
						Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
								+ Stock.getConfigParam("BROWSER"));
				lib.Reporter.logEvent(Status.INFO,
						"Test Data used for this Test Case:", printTestData(),
						false);	
				
				String[] updateQuery = Stock.getTestQuery("updatePlanMinimum_Amount");
				updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));			
				int planMinUpdate=DB.executeUpdate(updateQuery[0],updateQuery[1],
						"1000000", Stock.GetParameterValue("dsrsCode"),Stock.GetParameterValue("planId"));
				if(planMinUpdate >0)
					Reporter.logEvent(Status.PASS, "Verify if the plan minimum balance is updated", 
							"The Plan minimum is updated for the "
				+Stock.GetParameterValue("withdrawalType")+" withdrawal Type", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if the plan minimum balance is updated", 
							"The Plan minimum is NOT updated for the "
				+Stock.GetParameterValue("withdrawalType")+" withdrawal Type", false);
				
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
				requestWithdrawal.verifyPlanMinErrorMessage(Stock.GetParameterValue("withdrawalType"));
													
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
					String[] updateQuery = Stock.getTestQuery("updatePlanMinimum_Amount");
					updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));			
					int planMinUpdate=DB.executeUpdate(updateQuery[0],updateQuery[1],
							"0", Stock.GetParameterValue("dsrsCode"),Stock.GetParameterValue("planId"));
					if(planMinUpdate >0)
						System.out.println("The Plan minimum is revereted back");
					else
						System.out.println("The Plan minimum is NOT revereted back");
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Test(dataProvider = "setData")
		public void DDTC_26778_SECUREFOUNDATIONS_PPTWEB_Withdrawal_Set_FundType_To_G_SFI(int itr,
				Map<String, String> testdata) {
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
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true))
				{
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
	
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				
			if(Web.isWebElementDisplayed(requestWithdrawal,"SECURE FOUNDATIONS CONTENT"))
			{
				Reporter.logEvent(Status.PASS,"Verify if the SecureFoundations Content has been displayed in the Request a Withdrawal Home Page",
							"The Secure Foundations content has been displayed \n"
				+requestWithdrawal.getWebElementText("SECURE FOUNDATIONS CONTENT"),false);
				Web.clickOnElement(requestWithdrawal, "PRODUCT DOCUMENTATION LINK");
				String getParentWindow = Web.getDriver().getWindowHandle();
				for (String windowHandles : Web.getDriver().getWindowHandles()) 
				{
					Web.getDriver().switchTo().window(windowHandles); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
					Web.waitForPageToLoad(Web.getDriver());
				}
				System.out.println(Web.getDriver().getCurrentUrl());
				if(Web.getDriver().getCurrentUrl().equalsIgnoreCase(Stock.GetParameterValue("product disclosure Url")))
					Reporter.logEvent(Status.PASS, "Verify the secure foundation content and click on Product documentation link",
								"On clicking the Product Documentation link, system displays the "+Web.getDriver().getTitle()+" Page and the URL of the page is \n"
					+Web.getDriver().getCurrentUrl(), false);
				else
					Reporter.logEvent(Status.PASS, "Verify the secure foundation content and click on Product documentation link",
							"On clicking the Product Documentation link, system does not display Product disclosure content", true);			
					Web.getDriver().close();
					Web.getDriver().switchTo().window(getParentWindow);
					Web.getDriver().switchTo().defaultContent();
					System.out.println(Web.getDriver().getCurrentUrl());
				}else
				Reporter.logEvent(Status.PASS,"Verify if the SecureFoundations Content has been displayed in the Request a Withdrawal Home Page",
						"The Secure Foundations content has NOT been displayed",false);
				
				requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
				
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));
				requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
	}
