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
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);

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
	public void withDrawal_SepService_PWD_Roll_IRA(int itr,
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
			if(Web.isWebElementDisplayed(myAccountPage,"PLAN NAME", true)) { 
				 myAccountPage.clickPlanNameByGAID("385030-01"); 
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

			int enteredAmount = requestWithdrawal
					.getMaxAmountForPWMoneyTypeSource(Stock
							.GetParameterValue("moneyTypeSourceRoth"));
			// Sect Part withdrawal
			Web.clickOnElement(requestWithdrawal, "VESTED PART WITHDRAWAL");
			// Enter Amount for Part Withdrawal
			requestWithdrawal.enterAmountforPartWthdrawalMoneyTypeSource(Stock
					.GetParameterValue("moneyTypeSourceRoth"), Integer
					.toString(requestWithdrawal
							.getMaxAmountForPWMoneyTypeSource("Roth")));
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
						"Citizenship Confirmation Page is Displayed", false);
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
						"Withdrawal Method Page is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Not Displayed", true);
			}
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			// Select withdrawal method
			Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD",
					Stock.GetParameterValue("withdrawalMethod"));
			Web.waitForElement(requestWithdrawal, "ROLLOVER COMPANY");
			// Select RollOver Company
			requestWithdrawal.selectRollOverCompany(Stock
					.GetParameterValue("rollOverCompany"));
			requestWithdrawal.enterAddressDetailsForRollOverCompany(
					Stock.GetParameterValue("accountNo"),
					Stock.GetParameterValue("address1"),
					Stock.GetParameterValue("city"),
					Stock.GetParameterValue("stateCode"),
					Stock.GetParameterValue("zipCode"));
			Web.clickOnElement(requestWithdrawal, "CONTINUE TO WITHDRAWAL");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Delivery method");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Not Displayed", true);
			}
			// Select delivary method
			requestWithdrawal.selectDelivaryMethod(Stock
					.GetParameterValue("deliveryMethod"));
			Thread.sleep(5000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Withdrawal summary");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Not Displayed", true);
			}
			int withdrwalAmount = requestWithdrawal
					.getFinalWithdrawalAmountForMoneyTypeSource("Total withdrawal amount");
			if (enteredAmount == withdrwalAmount) {

				Reporter.logEvent(Status.INFO,
						"Verify Withdrawal Amount is Displayed Properly",
						"Withdrawal Amount is Displayed Properly\nExpected:$"
								+ enteredAmount + "\nActual:$"
								+ withdrwalAmount, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Amount is Displayed Properly",
						"Withdrawal Amount is Not Displayed Properly\nExpected:$"
								+ enteredAmount + "\nActual:$"
								+ withdrwalAmount, true);
			}
			Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");
			Thread.sleep(3000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Request submitted!");
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request Submission Page is Displayed",
						"Request Submission Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Submission Page is Displayed",
						"Request Submission is Not Displayed", true);
			}
			lblDisplayed = Web.VerifyPartialText("Your confirmation number is",
					requestWithdrawal.getWebElementText("TEXT CONFIRMATION"),
					true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Not Displayed", true);
			}

			/*
			 * if (Web.isWebElementDisplayed(requestWithdrawal,
			 * "TEXT CONFIRMATION NUMBER", true)) { lblDisplayed
			 * =Web.VerifyText(
			 * requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"
			 * ),DB.executeQuery(dbName, query, queryParameterValues)); if
			 * (lblDisplayed) { Reporter.logEvent( Status.PASS,
			 * "Verify Request Confirmation Number is in Number Format",
			 * "Request Confirmation is in Number Format and \n Confirmation Number is:"
			 * +
			 * requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"),
			 * false); } else { Reporter.logEvent( Status.FAIL,
			 * "Verify Request Confirmation Number is in Number Format",
			 * "Request Confirmation Number is Not in Number Format" +
			 * requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER"),
			 * true); } } else { Reporter.logEvent(Status.FAIL,
			 * "Verify Request Confirmation Number is Displayed",
			 * "Request Confirmation Number is Not Displayed", true); }
			 */
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
