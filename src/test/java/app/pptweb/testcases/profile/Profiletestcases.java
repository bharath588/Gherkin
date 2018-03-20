package app.pptweb.testcases.profile;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.liat.ProfilePage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.userregistration.AccountLookup;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class Profiletestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	String sExpectedErrorMsg;
	String actualErrorMsg;
	static boolean userloggedin;

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

	/**
	 * The following script
	 * 
	 * Covered Manual Test Cases: 1.DDTC-5956 Profile- Change Password another
	 * contribution rate_Sanity
	 **/

	@Test(dataProvider = "setData")
	public void Profile_Change_Password(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			//homePage.get();
			//homePage.clickOnProfile();

			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			// Step 5

			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");

			// Step 6
			try {
				Web.waitForElement(profilePage, "CHANGE PASSWORD BUTTON");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			profilePage.verifyWebElementDisplayed("CHANGE PASSWORD BUTTON");

			// Step 7
			try {
				Web.clickOnElement(profilePage, "CHANGE PASSWORD BUTTON");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Step 8
			try {
				Web.waitForElement(profilePage, "CHANGE PASSWORD TEXT");
			} catch (Exception e) {

				e.printStackTrace();
			}
			profilePage.isTextFieldDisplayed("Change password");

			// Step 9
			profilePage.verifyWebElementDisplayed("SAVE CHANGES");

			// Step 10
			profilePage.verifyWebElementDisplayed("CANCEL");
			try {
				Web.clickOnElement(profilePage, "CANCEL");
			} catch (Exception e) {

				e.printStackTrace();
			}
			profilePage.isTextFieldDisplayed("Profile");

			// Step 11
			try {
				Web.waitForElement(profilePage, "CHANGE PASSWORD BUTTON");
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				Web.clickOnElement(profilePage, "CHANGE PASSWORD BUTTON");
			} catch (Exception e) {

				e.printStackTrace();
			}

			profilePage.verifyWebElementEnabled("CURRENT PASSWORD");
			profilePage.verifyWebElementEnabled("NEW PASSWORD");
			profilePage.verifyWebElementEnabled("REENTER PASSWORD");

			// Step 12

			profilePage.isTextFieldDisplayed("Enter current password");

			// Step 13
			Web.setTextToTextBox("CURRENT PASSWORD", profilePage,
					Stock.GetParameterValue("currentPassword"));
			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("newPassword"));
			Web.setTextToTextBox("REENTER PASSWORD", profilePage,
					Stock.GetParameterValue("reEnterPassword"));

			Web.clickOnElement(profilePage, "SAVE CHANGES");
			Web.waitForPageToLoad(Web.getDriver());

			try {
				Web.waitForElement(profilePage, "ERROR CURRENT PASSWORD");
			} catch (Exception e) {

				e.printStackTrace();
			}
			profilePage.getElementText("ERROR CURRENT PASSWORD");

			profilePage
					.isTextFieldDisplayed("Error: The entries you provided do not match our records. Please try again.");

			// Step 14
			profilePage.isTextFieldDisplayed("Create new password");

			// Step 15
			profilePage.isTextFieldDisplayed("Re-enter new password");

			// Step 16 and 17
			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("wrongNewPassword"));
			profilePage.validateMsg("Must be 8 - 16 characters", "Error", true);
			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("wrongNewPassword2"));
			profilePage.validateMsg("Must be 8 - 16 characters", "Error", true);

			// Step 18
			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("userName"));
			profilePage.validateMsg("Must not match the username", "Error",
					true);

			// Step 19
			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("newPassword"));

			profilePage.validateMsg("Valid password", "Success", true);

			// Step 20

			Web.setTextToTextBox("NEW PASSWORD", profilePage, "a");

			profilePage.validateMsg("Lowercase letter", "Error", false);

			Web.setTextToTextBox("NEW PASSWORD", profilePage, "A");
			profilePage.validateMsg("Uppercase letter", "Error", false);

			Web.setTextToTextBox("NEW PASSWORD", profilePage, "@");
			profilePage.validateMsg("Special character", "Error", false);

			Web.setTextToTextBox("NEW PASSWORD", profilePage, "aA@");
			profilePage.validateMsg("Must include 2 of these 3:", "Error",
					false);

			// Step 21 - Step has been changed and it is same as Step 19

			// Step 22

			Web.clickOnElement(profilePage, "CANCEL");
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(profilePage, "CHANGE PASSWORD BUTTON");
			JavascriptExecutor jse = (JavascriptExecutor)Web.getDriver();
			jse.executeScript("window.scrollBy(0,-150)", "");
			jse = (JavascriptExecutor)Web.getDriver();
			jse.executeScript("window.scrollBy(0,150)", "");
			Web.clickOnElement(profilePage, "CHANGE PASSWORD BUTTON");

			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("newPassword"));
			Web.setTextToTextBox("REENTER PASSWORD", profilePage,
					Stock.GetParameterValue("reEnterPassword"));
			profilePage.validateMsg("Passwords must match", "Error", false);

			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("newPassword"));
			Web.setTextToTextBox("REENTER PASSWORD", profilePage, "aaaa");
			profilePage.validateMsg("Passwords must match", "Error", true);

			// Step 23
			Web.clickOnElement(profilePage, "CANCEL");
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(profilePage, "CHANGE PASSWORD BUTTON");
			Web.clickOnElement(profilePage, "CHANGE PASSWORD BUTTON");

			profilePage.validatePasswordBox("NEW PASSWORD");
			profilePage.validatePasswordBox("CURRENT PASSWORD");
			profilePage.validatePasswordBox("REENTER PASSWORD");

			Web.setTextToTextBox("CURRENT PASSWORD", profilePage, "Smart");
			profilePage.validatePasswordBox("NEW PASSWORD");

			Web.setTextToTextBox("NEW PASSWORD", profilePage,
					Stock.GetParameterValue("newPassword"));
			profilePage.validatePasswordBox("CURRENT PASSWORD");

			Web.setTextToTextBox("REENTER PASSWORD", profilePage,
					Stock.GetParameterValue("reEnterPassword"));
			profilePage.validatePasswordBox("REENTER PASSWORD");
			Web.clickOnElement(profilePage, "CANCEL");
			
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
	public void Profile_Change_Username(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			//homePage.get();
			//homePage.clickOnProfile();

			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			// Step 5
			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");

			// Step 6
			try {
				Web.waitForElement(profilePage, "CHANGE USERNAME BUTTON");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			profilePage.verifyWebElementDisplayed("CHANGE USERNAME BUTTON");

			// Step 7
			try {
				Web.clickOnElement(profilePage, "CHANGE USERNAME BUTTON");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Step 8

			profilePage.isTextFieldDisplayed("Change username");
			profilePage.isTextFieldDisplayed("Username");

			// Step 9

			profilePage
					.validateUsernameBox(Stock.GetParameterValue("userName"));
			profilePage.verifyWebElementDisplayed("SAVE CHANGES");
			profilePage.verifyWebElementDisplayed("CANCEL");

			// Step 10

			profilePage.validateMsg("Valid Username", "Success", true);

			// Step 11

			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			profilePage.validateMsg("Username is required", "Error", true);

			// Step 12
			Web.setTextToTextBox("CURRENT USERNAME", profilePage, "=");
			profilePage.validateMsg("Must be at least 6 characters", "Error",
					true);
			profilePage.validateMsg("Must include at least 3 letters", "Error",
					true);
			profilePage.validateMsg("Must include at least 1 number", "Error",
					true);
			profilePage.validateMsg("Invalid username", "Error", true);

			// Step 13
			Web.setTextToTextBox("CURRENT USERNAME", profilePage, "=a b");
			profilePage.validateMsg("Must be at least 6 characters", "Error",
					true);
			profilePage.validateMsg("Must include at least 3 letters", "Error",
					true);
			profilePage.validateMsg("Must include at least 1 number", "Error",
					true);
			profilePage.validateMsg("Invalid username", "Error", true);
			profilePage.validateMsg("Username may not include spaces", "Error",
					true);

			// Step 14 and 16
			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Stock.GetParameterValue("validUsername"));
			profilePage.validateMsg("Valid Username", "Success", true);

			// Step 15

			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Keys.chord(Stock.GetParameterValue("validUsername")),
					Keys.BACK_SPACE);
			profilePage.validateMsg("Must include at least 1 number", "Error",
					true);

			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Keys.chord(Stock.GetParameterValue("validUsername")),
					Keys.BACK_SPACE, Keys.BACK_SPACE);
			profilePage.validateMsg("Must include at least 1 number", "Error",
					true);
			profilePage.validateMsg("Must be at least 6 characters", "Error",
					true);

			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Keys.chord(Stock.GetParameterValue("validUsername")),
					Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
					Keys.BACK_SPACE, Keys.BACK_SPACE);
			profilePage.validateMsg("Must be at least 6 characters", "Error",
					true);
			profilePage.validateMsg("Must include at least 3 letters", "Error",
					true);
			profilePage.validateMsg("Must include at least 1 number", "Error",
					true);

			// Step 17 and 18
			Web.setTextToTextBox("CURRENT USERNAME", profilePage, "abc");
			profilePage.validateMsg("Must be at least 6 characters", "Error",
					true);

			// Step 19
			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("queryName"));

			ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],
					Stock.GetParameterValue("userName"));
			rs.first();
			String ssn = rs.getString("SSN");

			Web.setTextToTextBox("CURRENT USERNAME", profilePage, ssn);
			profilePage.validateMsg("Must include at least 3 letters", "Error",
					true);

			// Step 21

			Web.setTextToTextBox("CURRENT USERNAME", profilePage, "417473329a@");
			profilePage.validateMsg("Must include at least 3 letters", "Error",
					true);

			// Steo 20
			Web.setTextToTextBox("CURRENT USERNAME", profilePage,
					Stock.GetParameterValue("exsistingUsername"));
			Web.clickOnElement(profilePage, "SAVE CHANGES");

			Web.waitForElement(profilePage, "EXSISTING USER ERROR");
			profilePage.getElementText("EXSISTING USER ERROR");

			profilePage.isTextFieldDisplayed("Username already in use.");
			Web.clickOnElement(profilePage, "CANCEL");
			
			JavascriptExecutor jse = (JavascriptExecutor)Web.getDriver();
			jse.executeScript("window.scrollBy(0,-550)", "");
			
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
	public void Profile_View_UsernameAndPassword(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			if (Common.verifyLoggedInUserIsSame())
				Reporter.logEvent(Status.PASS,
						" Verify 'Liat' Page is displayed",
						"User name is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						" Verify 'Liat' Page is displayed",
						"User name is not displayed", true);

			Web.getDriver().get(Stock.GetParameterValue("url"));

			ProfilePage profilePage = new ProfilePage(homePage);

			// Step 5
			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");
			profilePage.isTextFieldDisplayed("Username and password");
			// Step 6
			String sPassword = profilePage
					.getElementText("PASSWORD PROFILE PAGE");
			String sUsername = profilePage
					.getElementText("USERNAME PROFILE PAGE");
			System.out.println("User name and Pssword" + sPassword + sUsername);
			if (sUsername.equals(Stock.GetParameterValue("userName"))) {
				Reporter.logEvent(Status.PASS,
						" Verify 'Profile Page' Page is displayed",
						"User name is matching", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						" Verify 'Profile Page' Page is displayed",
						"User name is not matching", true);
			}

			if (sPassword.length() == 8
					&& !StringUtils.isAlphanumeric(sPassword)) {
				Reporter.logEvent(Status.PASS,
						" Verify 'Profile Page' Page is displayed",
						"Password is masked and it's length is matching", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						" Verify 'Profile Page' Page is displayed",
						"Password is not matching", true);
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
	public void Profile_Change_ContactInformation(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			//homePage.get();

			// Step 2

			

			//homePage.clickOnProfile();

			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			/*if (Common.verifyLoggedInUserIsSame())
				Reporter.logEvent(Status.PASS,
						" Verify 'Liat' Page is displayed",
						"User name is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						" Verify 'Liat' Page is displayed",
						"User name is not displayed", true);*/

			// Step 3 & Step 4
			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");
			profilePage.isTextFieldDisplayed("Personal contact information");

			// Step 5
			profilePage.validatePersonalContactInformation();

			// Step 6
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(profilePage, "CHANGE CONTACT INFORMATION BUTTON");
			Web.waitForPageToLoad(Web.getDriver());
			Web.clickOnElement(profilePage, "CHANGE CONTACT INFORMATION BUTTON");
			Web.setTextToTextBox("PERSONAL EMAIL ADDRESS TEXTBOX", profilePage,
					"");
			
			profilePage.isTextFieldDisplayed("Email is required");
			Web.clickOnElement(profilePage, "PERSONAL PHONE NUMBER");
			Web.setTextToTextBox("PERSONAL EMAIL ADDRESS TEXTBOX", profilePage,
					"a.aa@gw..com");
			
			profilePage
					.isTextFieldDisplayed("Please enter a valid email address.");

			// Step 8
			Web.setTextToTextBox("PERSONAL EMAIL ADDRESS TEXTBOX", profilePage,
					"a.aa@gwg.com");
			Web.setTextToTextBox("PERSONAL PHONE NUMBER", profilePage, "");
			
			Web.clickOnElement(profilePage, "SAVE CHANGES");
			Web.waitForPageToLoad(Web.getDriver());
		
			profilePage
					.isTextFieldDisplayed("At least one phone number is required");

			// Step 9 and 10 - Mobile TC

			// Step 11
			Web.clickOnElement(profilePage, "CANCEL");
			Web.waitForPageToLoad(Web.getDriver());
			
			profilePage.validatePersonalContactInformation();
			
			Web.waitForPageToLoad(Web.getDriver());
			
			// Step 12 and 7
			
			Web.waitForElement(profilePage, "CHANGE CONTACT INFORMATION BUTTON");
			Web.waitForPageToLoad(Web.getDriver());
			Web.clickOnElement(profilePage, "CHANGE CONTACT INFORMATION BUTTON");
			Web.setTextToTextBox("PERSONAL EMAIL ADDRESS TEXTBOX", profilePage,
					"");
			Web.setTextToTextBox("PERSONAL EMAIL ADDRESS TEXTBOX", profilePage,
					"a84`6@a'a.bbb.cbc.com");
			Web.setTextToTextBox("PERSONAL PHONE NUMBER", profilePage,
					"7878787878");
			
			Web.clickOnElement(profilePage, "SAVE CHANGES");
			Thread.sleep(5000);//It takes time to change the values in DB and reflect it in UI
			profilePage.validatePersonalContactInformation();

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
	public void Profile_Change_Address(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			/*if (Common.verifyLoggedInUserIsSame())
				Reporter.logEvent(Status.PASS,
						" Verify 'Liat' Page is displayed",
						"User name is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						" Verify 'Liat' Page is displayed",
						"User name is not displayed", true);*/
			// Step 5

			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");
			profilePage.isTextFieldDisplayed("Home mailing address");
			// Step 6
			Web.clickOnElement(profilePage, "CHANGE HOME ADDRESS");
			profilePage.isTextFieldDisplayed("Change Home Address");

			profilePage.verifyWebElementDisplayed("CHANGE HOME ADDRESS LINE1");
			profilePage.verifyWebElementDisplayed("CHANGE HOME ADDRESS LINE2");
			profilePage.verifyWebElementDisplayed("CHANGE HOME ADDRESS CITY");
			profilePage.verifyWebElementDisplayed("CHANGE HOME ADDRESS STATE");
			profilePage.verifyWebElementDisplayed("CHANGE HOME ADDRESS ZIP");
			profilePage
					.verifyWebElementDisplayed("CHANGE HOME ADDRESS COUNTRY");
			Web.clickOnElement(profilePage, "CANCEL");
			
			
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
	public void Profile_View_ContactInformation(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ProfilePage profilePage = new ProfilePage(homePage);
			profilePage.get();
			
			// Step 4

			Web.waitForPageToLoad(Web.getDriver());
			profilePage.isTextFieldDisplayed("Profile");
			profilePage.isTextFieldDisplayed("Personal contact information");

			// Step 5 to 6
			profilePage
					.verifyWebElementDisplayed("PERSONAL CONTACT INFORMATION");
			profilePage
					.verifyWebElementDisplayed("LABEL PERSONAL EMAIL ADDRESS");
			profilePage.verifyWebElementDisplayed("LABEL MOBILE PHONE NUMBER");

			profilePage.validatePersonalContactInformation();
			
			// STep 7 to 12
			//Update work phone and email to NULL
			
			String[] WorkEmailToNull = Stock.getTestQuery(Stock
					.GetParameterValue("updateWorkEmailNull"));
			DB.executeQuery(WorkEmailToNull[0],
					WorkEmailToNull[1], Stock.GetParameterValue("userName"));
			
			String[] WorkPhoneToNull = Stock.getTestQuery(Stock
					.GetParameterValue("updateWorkPhoneNumNull"));
			DB.executeQuery(WorkPhoneToNull[0],
					WorkPhoneToNull[1], Stock.GetParameterValue("userName"));
			Web.getDriver().navigate().refresh();
			Web.waitForPageToLoad(Web.getDriver());
			profilePage.validateWorkContactInformation();
			
			//Update work phone to NULL and set valid email id
			
			String[] WorkEmailId = Stock.getTestQuery(Stock
					.GetParameterValue("updateWorkEmail"));
			DB.executeQuery(WorkEmailId[0],
					WorkEmailId[1], Stock.GetParameterValue("userName"));
			Web.getDriver().navigate().refresh();
			Web.waitForPageToLoad(Web.getDriver());
			profilePage.validateWorkContactInformation();
			profilePage.verifyWebElementDisplayed("HOME ADDRESS ON WORK INFORMATION");
			
			//Update work email to NULL and set valid phone number
			
			DB.executeQuery(WorkEmailToNull[0],
					WorkEmailToNull[1], Stock.GetParameterValue("userName"));
			String[] WorkPhoneNum = Stock.getTestQuery(Stock
					.GetParameterValue("updateWorkPhoneNum"));
			DB.executeQuery(WorkPhoneNum[0],
					WorkPhoneNum[1], Stock.GetParameterValue("userName"));
			Web.getDriver().navigate().refresh();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			profilePage.validateWorkContactInformation();
			profilePage.verifyWebElementDisplayed("HOME ADDRESS ON WORK INFORMATION");
			
			//Update valid work email address and phone number  
			
			DB.executeQuery(WorkEmailId[0],
					WorkEmailId[1], Stock.GetParameterValue("userName"));
			Web.getDriver().navigate().refresh();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			profilePage.validateWorkContactInformation();
			profilePage.verifyWebElementDisplayed("HOME ADDRESS ON WORK INFORMATION");
			
			
			//Step 13
			profilePage.verifyWebElementDisplayed("HOME ADDRESS LABEL PROFILE PAGE");
			profilePage.validateHomeMailingAddress();
			Web.clickOnElement(profilePage, "LOG OUT");
			
			
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
	public void Profile_ForceChangePassword(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			// Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LocalDate mydateStr = LocalDate.now().minusDays(185);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
			String sDate = mydateStr.format(formatter);
			
			System.out.println(sDate);
			
			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("updatePwdChangeDate"));

			DB.executeQuery(sqlQuery[0],
					sqlQuery[1], sDate,Stock.GetParameterValue("userName"));
			DB.executeUpdate(sqlQuery[0], "commit");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			mfaPage.get();
			Web.clickOnElement(mfaPage, "ALREADY HAVE A CODE?");
			mfaPage.submitVerificationCode("74196385", true, false);
			ForgotPassword objForgotPsw = new ForgotPassword();
			
			
			
		
			objForgotPsw.verifyWebElementDisplayed("CURRENT PASSWORD");
			objForgotPsw.verifyWebElementDisplayed("NEW PASSWORD");
			objForgotPsw.verifyWebElementDisplayed("REENTER PASSWORD");
			
			
			Web.setTextToTextBox("CURRENT PASSWORD", objForgotPsw,
					Stock.GetParameterValue("currentPassword"));
			Web.setTextToTextBox("NEW PASSWORD", objForgotPsw,
					Stock.GetParameterValue("newPassword"));
			Web.setTextToTextBox("REENTER PASSWORD", objForgotPsw,
					Stock.GetParameterValue("reEnterPassword"));
			//Web.waitForElement(objForgotPsw, "SAVE CHANGES");
			Web.clickOnElement(objForgotPsw, "SAVE CHANGES");
			LandingPage homePage = new LandingPage();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(5000);
			if(Web.isWebElementDisplayed(homePage, "HOME"))
			{
				Reporter.logEvent(Status.PASS, "Verify Home Page is displayed", "Home Label is displayed", false);

			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify Home Page is not displayed", "Home Label is not displayed", true);
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
			ForgotPassword objForget = new ForgotPassword();
			try {
				String[] sqlQuery = Stock.getTestQuery(Stock
						.GetParameterValue("updatePwd"));

				DB.executeQuery(sqlQuery[0],
						sqlQuery[1], Stock.GetParameterValue("encryptedCode"),Stock.GetParameterValue("userName"));
				DB.executeUpdate(sqlQuery[0], "commit");
				System.out.println("Password is changed back to normal");
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Change_OR_update_Password_Reg(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			String headerText;
			String activeTab;
			String actualValue;
			String actualErrMsg, expectedErrMsg;

			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);

			// Steps
			accLookup.get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.",
					"Account lookup page is displayed", true);

			// Step 3 - Verify verbiage on the top of the page
			headerText = accLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account Lookup Header block text",
						"Header text block is not displayed on the page", false);
			} else {
				Web.VerifyText(
						"Lets look up your account Enter the information below to set up your account.",
						headerText, true);
			}

			// Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = accLookup.getActiveTabName();
			if (activeTab.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(Status.PASS,
						"Verify default tab opened is 'I do not have a PIN'",
						"Tab 'I do not have a PIN' is open by default", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify default tab opened is 'I do not have a PIN",
						"Tab 'I do not have a PIN' is not open by default",
						false);
			}
			System.out.println("Done");
			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("getUserInfo"));

			ResultSet rs_Number = DB.executeQuery(sqlQuery[0],
					sqlQuery[1], Stock.GetParameterValue("userName"));
			
			rs_Number.first();
			String sFirstLine = rs_Number.getString("first_line_mailing");
			StringBuilder sFirst = new StringBuilder();
			System.out.println(sFirstLine);
			
			char[] c = sFirstLine.toCharArray();
			
			if(Character.isAlphabetic(c[0]))
			{
				Reporter.logEvent(Status.FAIL, "User not valid", "User doesn't have Numeric portion of street address or P.O. box", true);
			}
			else
			{
				for(int i=0;i<c.length;i++)
				{
					
					if(Character.isDigit(c[i]))
					{
						sFirst.append(c[i]);
					}
					else if(Character.isWhitespace(c[i]))
					{
						break;
					}
					else
					{
						break;
					}
				}
				System.out.println(sFirst);
			}
			
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
			
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
