package app.pptweb.testcases.NPDI;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.userregistration.AccountLookup;
import pageobjects.userregistration.AccountSetup;
import pageobjects.userregistration.Registration;
import appUtils.Common;
import appUtils.ExecuteQuery;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class NPDILoadTesting {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private static HashMap<String, String> testDataFromDB = null;
	public static String SSN = null;
	LoginPage login;
	String tcName;
	static String printTestData="";
	
	@BeforeClass
	public void InitTest() throws Exception {
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
	public void Load_Testing(int itr, Map<String, String> testdata) {

		try {
			
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Common.getSponser()
							+ "_"
							+ Stock.getConfigParam("BROWSER"));

			Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();
LandingPage homePage = new LandingPage();
			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration = new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			Enrollment enroll = new Enrollment(objAuth);
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on
			// Register
			// link
			
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

		SSN=Stock.GetParameterValue("SSN");
			
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("ga_PlanId"),
					Stock.GetParameterValue("codeDeliveryOption"));
			Web.waitForElement(registration, "Header Registration");
			if (Web.isWebElementDisplayed(registration, "Header Registration")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}

			
			registration.providePersonalInformation(Stock.GetParameterValue("FIRST NAME"), Stock.GetParameterValue("MIDDLE NAME"), Stock.GetParameterValue("LAST NAME"),
					Stock.GetParameterValue("dob"), Stock.GetParameterValue("GEN"), Stock.GetParameterValue("SSN"), Stock.GetParameterValue("Marital status"));
			
				registration.provideEmploymentInformation(Stock.GetParameterValue("annual income"), Stock.GetParameterValue("date of hire"));
			
				
			
			registration.provideMailingAddress(Stock.GetParameterValue("ADDRESS"),"", Stock.GetParameterValue("CITY"),
					Stock.GetParameterValue("STATE"), Stock.GetParameterValue("ZIP"), "UNITED STATES");

			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			
			
			// Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("Mail"));
			Thread.sleep(7000);
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("phone"));
			
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "LOAD");
			
			
			
			
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("Re-enter password"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			Web.waitForElement(accSetup,"MOBILE PHONE NUMBER");
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("phone"));
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(10000);

			Web.waitForElement(objAuth, "Header Enhanced Security");
			if (Web.isWebElementDisplayed(objAuth, "Header Enhanced Security")) {

				Reporter.logEvent(Status.PASS,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is not displayed", true);
			}
			
			
			// Step 12
			objAuth.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			String verificationCode = "74196385";
			
			objAuth.submitVerificationCode(verificationCode, false, false);
			Web.clickOnElement(objAuth, "SIGN IN");
			
			
			Web.clickOnElement(objAuth, "CONTINUE TO MY ACCOUNT");
			Thread.sleep(5000);
			homePage.dismissPopUps(true, true);
			Web.waitForElement(homePage, "Log out");
			
				Web.clickOnElement(homePage, "LOGOUT");
			
		}

		catch (Exception e) {
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
				ExecuteQuery.UnRegisterParticipant(SSN);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void precondition() {
		try {
			String[] sqlQuery;
			sqlQuery = Stock.getTestQuery("unlockParticipants");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1],
					Stock.GetParameterValue("SSN"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
