package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

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
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
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
int enteredAmount=requestWithdrawal
.getMaxAmountForPWMoneyTypeSource("Roth");
			Web.clickOnElement(requestWithdrawal, "VESTED PART WITHDRAWAL");
			requestWithdrawal.enterAmountforPartWthdrawalMoneyTypeSource(
					"Roth", Integer.toString(requestWithdrawal
							.getMaxAmountForPWMoneyTypeSource("Roth")));
			if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")){
				Web.clickOnElement(requestWithdrawal, "CONTINUE");
			}
			else{
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

			requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			lblDisplayed = Web.clickOnElement(requestWithdrawal, "YES");
			Thread.sleep(3000);
			lblDisplayed = requestWithdrawal.isTextFieldDisplayed("Please enter your Social Security number.");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Social Security number Field is Displayed.",
						"Social Security number Field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Social Security number Field is Displayed",
						"Social Security number Field is Not Displayed", true);
			}

			requestWithdrawal.enterSSN(Stock.GetParameterValue("SSN"));
			Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal.isTextFieldDisplayed("Withdrawal method");

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Not Displayed", true);
			}
			requestWithdrawal.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD",
					Stock.GetParameterValue("withdrawalMethod"));
			Web.waitForElement(requestWithdrawal, "ROLLOVER COMPANY");
			requestWithdrawal.selectRollOverCompany(Stock.GetParameterValue("rollOverCompany"));
			requestWithdrawal.enterAddressDetailsForRollOverCompany(Stock.GetParameterValue("accountNo"),Stock.GetParameterValue("address1"), Stock.GetParameterValue("city"), Stock.GetParameterValue("stateCode"), Stock.GetParameterValue("zipCode"));
			Web.clickOnElement(requestWithdrawal, "CONTINUE TO WITHDRAWAL");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal.isTextFieldDisplayed("Delivery method");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Not Displayed", true);
			}
			requestWithdrawal.selectDelivaryMethod(Stock.GetParameterValue("deliveryMethod"));
			Thread.sleep(5000);
			lblDisplayed = requestWithdrawal.isTextFieldDisplayed("Withdrawal summary");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Not Displayed", true);
			}
			int withdrwalAmount=requestWithdrawal.getFinalWithdrawalAmountForMoneyTypeSource(Stock.GetParameterValue("moneyTypeSource"));
			if(enteredAmount==withdrwalAmount){
				
				Reporter.logEvent(Status.INFO,
						"Verify Withdrawal Amount is Displayed Properly",
						"Withdrawal Amount is Displayed Properly\nExpected:$"+enteredAmount+"\nActual:$"+withdrwalAmount, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Amount is Displayed Properly",
						"Withdrawal Amount is Not Displayed Properly\nExpected:$"+enteredAmount+"\nActual:$"+withdrwalAmount, true);
			}
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
