package app.pptweb.testcases.login;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import appUtils.ExecuteQuery;
import appUtils.TestDataFromDB;
import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.userregistration.AccountLookup;
import pageobjects.userregistration.AccountSetup;
import pageobjects.userregistration.Registration;
import core.framework.Globals;

public class registrationtestcases {

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

	public void prepareLoginTestData() {
		try {
			testDataFromDB = TestDataFromDB
					.getParticipantDataFromDB("getUnRegisteredUser",
							Stock.GetParameterValue("ga_PlanId"));
			//TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void prepareLoginTestData(String quesryNmae, String... queryParam) {
		try {
			testDataFromDB = TestDataFromDB.getParticipantDataFromDB(quesryNmae,
					queryParam);
			//TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
	public void SF01_TC01_User_has_PIN(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
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

			// Navigate to tab 'I have a PIN'
			if (accLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to \"I have a PIN\" tab",
						"Navigation successful", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to \"I have a PIN\" tab",
						"Navigation failed", true);
			}

			// Step 5 - Verify that SSN and PIN text boxes are displayed
			if (accLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN field verification",
						"SSN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN field verification",
						"SSN field is NOT visible", false);
			}

			// Verify PIN text box is displayed
			if (accLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification",
						"PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification",
						"PIN field is NOT visible", false);
			}

			// Step 6 - Enter Alpha-numeric SSN and move the cursor out
			accLookup.setTextToFields("Social Security Number", "ab12CD34e");
			accLookup.clickOnFields("PIN");
			accLookup.clickOnFields("CONTINUE");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getSSNFieldErrorMsgs();
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message 'Social Security Number must be numeric.' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyPartialText("Social Security Number must be numeric.",actualErrMsg,
						 true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 7 - Enter SSN less than 9 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "1252");
			accLookup.clickOnFields("PIN");
			accLookup.clickOnFields("CONTINUE");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 8 - Enter SSN more than 9 digits
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			actualValue = accLookup.setTextToFields("Social Security Number",
					"12345678956");
			if (actualValue.trim().equals("123-45-6789")) {
				Reporter.logEvent(Status.PASS,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ actualValue, false);
			} else if (actualValue.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ actualValue, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted",
						false);
			}

			// Step 9 - Enter Alpha-numeric PIN and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "ab12");
			accLookup.clickOnFields("Social Security Number");
			accLookup.clickOnFields("CONTINUE");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be numeric";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message 'PIN must be numeric.' is displayed",
						"No error message displayed for PIN.", false);
			} else {

				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 10 - Enter PIN less than 4 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "12");
			accLookup.clickOnFields("Social Security Number");
			accLookup.clickOnFields("CONTINUE");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be between 4-16 digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '"
						+ expectedErrMsg + "' is displayed",
						"No error message displayed for PIN number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 11 - Enter PIN more than 4 digits--Not Valid
			/*
			 * actualValue = accLookup.setTextToFields("PIN", "123456"); if
			 * (actualValue.trim().equals("1234")) {
			 * Reporter.logEvent(Status.PASS,
			 * "Verify PIN field accepts only 4 digits",
			 * "Value set: 123456\nValue accepted: " + actualValue, false); }
			 * else if (actualValue.trim().length() > 0) {
			 * Reporter.logEvent(Status.FAIL,
			 * "Verify PIN field accepts only 4 digits",
			 * "Value set: 123456\nValue accepted: " + actualValue, false); }
			 * else { Reporter.logEvent(Status.FAIL,
			 * "Verify PIN field accepts only 4 digits",
			 * "Value set: 123456\nValue accepted: Unable to set/No Value accepted"
			 * , false); }
			 */

			// Step 12 - Leave SSN blank and enter valid PIN and click on
			// Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "1234");
			accLookup.clickOnFields("Continue");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 13 - Enter valid SSN and leave PIN blank and click on
			// Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "123456789");
			accLookup.clickOnFields("Continue");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '"
						+ expectedErrMsg + "' is displayed",
						"No error message displayed for PIN number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Step 14 - Leave both SSN and PIN blank and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.clickOnFields("Continue");
			Thread.sleep(4000);
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}

			// Switch to other tab - to help continuation for other iterations
			// if any
			accLookup.navigateToTab("I do not have a PIN");

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
	public void SF01_TC02_User_does_not_have_PIN(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			boolean blnIsElePresent;
			String txtActErrMsg;
			String headerText;
			String activeTab;
			String expectedErrMsg;
			LoginPage objloginPage = new LoginPage();

			AccountLookup objAccountLookup = new AccountLookup(objloginPage)
					.get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.",
					"Account lookup page is displayed", true);

			// Step 3 - Verify verbiage on the top of the page
			headerText = objAccountLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account Lookup Header block text",
						"Header text block is not displayed on the page", false);
			} else {
				Web.VerifyText(
						"Lets look up your account\nEnter the information below to set up your account.",
						headerText, false);
			}

			// Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = objAccountLookup.getActiveTabName();
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

			// Step 5 - Verify that the following fields are shown along
			// with text
			// entry boxes for values:
			// Social Security number (defaults to placeholder text that
			// displays as
			// (___-__-____)
			// Zip Code
			// Last Name
			// Date of Birth (defaults to show greyed out format of MM DD
			// YYYY)
			// Street Address

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Social Security Number");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'",
						"Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'",
						"Social Security Number field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Zip Code");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Zip Code Field Displayed'",
						"Zip Code field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Zip Code Field Displayed'",
						"Zip Code field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Last Name");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Last Name Field Displayed'",
						"Last Name field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Last Name Field Displayed'",
						"Last Name field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Date of Birth");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS,
						"Date of Birth Field Displayed'",
						"Date of Birth field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Date of Birth Field Displayed'",
						"Date of Birth field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Street Address");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS,
						"Street Address Field Displayed'",
						"Street Address field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Street Address Field Displayed'",
						"Street Address field is NOT Displayed", false);
			}

			// Step 7 - In the social security field, enter alphanumeric
			// characters
			// and move the cursor out of the field.
			Actions keyBoard = new Actions(Web.getDriver());

			objAccountLookup.setTextToFields("Social Security Number",
					"ab12CD34e");
			Web.clickOnElement(objAccountLookup, "ZIP CODE");
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup.getSSNFieldErrorMsgs();
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message 'Social Security Number must be numeric.' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyPartialText("Social Security Number must be numeric.",txtActErrMsg,
						 true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 8 - Enter SSN less than 9 digits and move the cursor out
			objAccountLookup.setTextToFields("Social Security Number", "1252");
			Web.clickOnElement(objAccountLookup, "ZIP CODE");
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup
					.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 9 - Enter SSN more than 9 digits
			txtActErrMsg = objAccountLookup.setTextToFields(
					"Social Security Number", "12345678956");
			if (txtActErrMsg.trim().equals("123-45-6789")) {
				Reporter.logEvent(Status.PASS,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ txtActErrMsg, false);
			} else if (txtActErrMsg.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ txtActErrMsg, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted",
						false);
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 10 - In the zip code field, enter alphanumeric
			// characters and
			// move the cursor out of the field..
/*
 * this assertion has been removed due to functionality cahnged as zip code field is accepting alphanumeric
 */
			/*objAccountLookup.setTextToFields("ZIP CODE", "abc23");
			Web.clickOnElement(objAccountLookup, "Social Security Number");
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("ZIP CODE");
			expectedErrMsg = "Please enter valid ZIP Code.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"ZIP CODE error Verification for alpha numberic char",
						"Error message 'Please enter valid ZIP Code.' is not displayed",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}
*/
			/*
			 * // step 11 and 12 are UI validations that cannot be done // Step
			 * 13 - Verfiy that the placeholder text for the three boxes in the
			 * // Date of Birth field should be "__/__/____"
			 */

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 14 - Verify that only numeric characters are allowed to
			// be
			// entered in the Date of Birth text entry box and no alpha
			// character
			// should be allowed in the fields.

			objAccountLookup.setTextToFields("Date of Birth", "123123aa");
			Web.clickOnElement(objAccountLookup, "Social Security Number");
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Date of Birth");
			expectedErrMsg = "Date of birth must follow MM/DD/YYYY format.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Date of Birth error Verification for alpha numberic char",
						"Verify error message 'Please enter a valid date.' is displayed",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// step 15 cannot be automated
			// Step 16 -Verify text entry box for the Street Address and
			// that the
			// Street Address field allows for text entry of just the house
			// number
			objAccountLookup.setTextToFields("Street Address", "23");
			Thread.sleep(2000);
			objAccountLookup.clickOnFields("Social Security Number");
			Thread.sleep(2000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.PASS,
						"Street Address error Verification",
						"No error was displayed for Street address for entering only house number",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number\nActual Message: "
								+ txtActErrMsg, false);
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 16 -Verify text entry box for the Street Address and
			// that the
			// Street Address field allows for text entry of just the house
			// number
			// and Street name
			objAccountLookup.setTextToFields("Street Address",
					"23 BOURKE STREET");
			Thread.sleep(2000);
			objAccountLookup.clickOnFields("Social Security Number");
			Thread.sleep(2000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.PASS,
						"Street Address error Verification",
						"No error was displayed for Street address for entering house number and street name",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number and street name\nActual Message: "
								+ txtActErrMsg, false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the
			// page
			if (objAccountLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS,
						"Verify tab opened is 'I have a PIN'",
						"The user navigated to 'I have a PIN' tab", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify tab opened is 'I do not have a PIN",
						" The user did not navigate to 'I have a PIN'", false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the
			// page and
			// verify if the SSN and PIN field is displayed
			if (objAccountLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'",
						"Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'",
						"Social Security Number field is NOT Displayed", false);
			}
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification",
						"PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification",
						"PIN field is NOT visible", false);
			}

			// Step 18 - Click on the "I do not have a PIN" - The user is
			// taken back
			// to registering without a pin
			objAccountLookup.navigateToTab("I do not have a PIN");
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.FAIL, "PIN field verification",
						"PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.PASS, "PIN field verification",
						"PIN field is NOT visible", false);
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

	@Test(dataProvider = "setData")
	public void SF01_TC02_User_does_not_have_PIN_Apple(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) +"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			boolean blnIsElePresent;
			String txtActErrMsg;
			String headerText;
			String activeTab;
			String expectedErrMsg;

			Web.getDriver()
					.get("https://proj2.retirementpartner.com/participant-web-services/ws/appleRegLanding.do?accu=Apple&token=M0hFuIOjV3nCoTYKOPFd7G5vfErJkEVbKBpNzxGjRgXhYK5dLOh488J7lakZ2TS82NgzgDT5iAdnWkKOXptXxSlYo%2Bf0bRuGaiTU9SqGTio%3D");
			AccountLookup objAccountLookup = new AccountLookup();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.",
					"Account lookup page is displayed", true);

			// Step 3 - Verify verbiage on the top of the page
			headerText = objAccountLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account Lookup Header block text",
						"Header text block is not displayed on the page", false);
			} else {
				Web.VerifyText(
						"Lets look up your account\nEnter the information below to set up your account.",
						headerText, false);
			}

			// Step 4 - Verify default tab opened is "I do not have a PIN"
			/*
			 * Cant execute this Step FOr Apple Sponser
			 */

			// Step 5 - Verify that the following fields are shown along
			// with text
			// entry boxes for values:
			// Social Security number (defaults to placeholder text that
			// displays as
			// (___-__-____)
			// Zip Code
			// Last Name
			// Date of Birth (defaults to show greyed out format of MM DD
			// YYYY)
			// Street Address

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Social Security Number");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'",
						"Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'",
						"Social Security Number field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Zip Code");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Zip Code Field Displayed'",
						"Zip Code field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Zip Code Field Displayed'",
						"Zip Code field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Last Name");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Last Name Field Displayed'",
						"Last Name field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Last Name Field Displayed'",
						"Last Name field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Date of Birth");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS,
						"Date of Birth Field Displayed'",
						"Date of Birth field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Date of Birth Field Displayed'",
						"Date of Birth field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup
					.isFieldDisplayed("Street Address");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS,
						"Street Address Field Displayed'",
						"Street Address field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Street Address Field Displayed'",
						"Street Address field is NOT Displayed", false);
			}

			// Step 7 - In the social security field, enter alphanumeric
			// characters
			// and move the cursor out of the field.
			Actions keyBoard = new Actions(Web.getDriver());
			objAccountLookup.setTextToFields("Social Security Number",
					"ab12CD34e");
			keyBoard.sendKeys(Keys.TAB).perform();
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup
					.getFieldErrorMsg("Social Security Number");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message 'Social Security Number must be numeric.' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg,
						"Social Security Number must be numeric.", true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message 'Social Security Number must be numeric.' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			Web.getDriver().navigate().refresh();

			// Step 8 - Enter SSN less than 9 digits and move the cursor out
			objAccountLookup.setTextToFields("Social Security Number", "1252");
			keyBoard.sendKeys(Keys.TAB).perform();
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup
					.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Social Security number.",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			Web.getDriver().navigate().refresh();

			// Step 9 - Enter SSN more than 9 digits
			txtActErrMsg = objAccountLookup.setTextToFields(
					"Social Security Number", "12345678956");
			if (txtActErrMsg.trim().equals("123456789")) {
				Reporter.logEvent(Status.PASS,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ txtActErrMsg, false);
			} else if (txtActErrMsg.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: "
								+ txtActErrMsg, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted",
						false);
			}

			Web.getDriver().navigate().refresh();
			// Step 10 - In the zip code field, enter alphanumeric
			// characters and
			// move the cursor out of the field..

			objAccountLookup.setTextToFields("ZIP CODE", "abc23");
			keyBoard.sendKeys(Keys.TAB).perform();
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("ZIP CODE");
			expectedErrMsg = "Please enter valid ZIP Code.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"ZIP CODE error Verification for alpha numberic char",
						"Error message 'Please enter valid ZIP Code.' is not displayed",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}
			Web.getDriver().navigate().refresh();
			/*
			 * // step 11 and 12 are UI validations that cannot be done // Step
			 * 13 - Verfiy that the placeholder text for the three boxes in the
			 * // Date of Birth field should be "__/__/____"
			 */

			// Step 14 - Verify that only numeric characters are allowed to
			// be
			// entered in the Date of Birth text entry box and no alpha
			// character
			// should be allowed in the fields.

			objAccountLookup.setTextToFields("Date of Birth", "123123aa");
			keyBoard.sendKeys(Keys.TAB).perform();
			Thread.sleep(6000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Date of Birth");
			expectedErrMsg = "Please enter a valid date.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Date of Birth error Verification for alpha numberic char",
						"Verify error message 'Date of Birth must be numeric.' is displayed",
						false);
			} else {
				if (Web.VerifyText(txtActErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ txtActErrMsg, false);
				}
			}

			Web.getDriver().navigate().refresh();

			// step 15 cannot be automated
			// Step 16 -Verify text entry box for the Street Address and
			// that the
			// Street Address field allows for text entry of just the house
			// number
			objAccountLookup.setTextToFields("Street Address", "23");
			Thread.sleep(2000);
			objAccountLookup.clickOnFields("Social Security Number");
			Thread.sleep(2000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.PASS,
						"Street Address error Verification",
						"No error was displayed for Street address for entering only house number",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number\nActual Message: "
								+ txtActErrMsg, false);
			}

			Web.getDriver().navigate().refresh();
			// Step 16 -Verify text entry box for the Street Address and
			// that the
			// Street Address field allows for text entry of just the house
			// number
			// and Street name
			objAccountLookup.setTextToFields("Street Address",
					"23 BOURKE STREET");
			Thread.sleep(2000);
			objAccountLookup.clickOnFields("Social Security Number");
			Thread.sleep(2000);
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.PASS,
						"Street Address error Verification",
						"No error was displayed for Street address for entering house number and street name",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number and street name\nActual Message: "
								+ txtActErrMsg, false);
			}
			Web.getDriver().navigate().refresh();
			// Step 17 - Click on the "I have a PIN" tab at the top of the
			// page

			// Step 17 - Click on the "I have a PIN" tab at the top of the
			// page and
			// verify if the SSN and PIN field is displayed

			// Step 18 - Click on the "I do not have a PIN" - The user is
			// taken back
			/*
			 * These Steps are not automatable for Apple Sponser
			 */
			// to registering without a pin

			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.FAIL, "PIN field verification",
						"PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.PASS, "PIN field verification",
						"PIN field is NOT visible", false);
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

	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithAndWithoutPINTabs(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			precondition();
			String ActErrMessage;
			LoginPage loginPage = new LoginPage();
			AccountLookup accLookup = new AccountLookup(loginPage);

			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			// Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is on \"" + Stock.GetParameterValue("TabName")
								+ "\" tab", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is not on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						true);
			}

			// Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {
				// Enter SSN
				accLookup
						.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				// Enter PIN
				accLookup
						.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I do not have a PIN")) {
				// Enter SSN
				accLookup.setTextToFields("Social Security Number",
						Stock.GetParameterValue("SSN"));
				// Enter Zip Code
				accLookup.setTextToFields("Zip Code",
						Stock.GetParameterValue("ZipCode"));
				// Enter Last Name
				accLookup.setTextToFields("Last Name",
						Stock.GetParameterValue("LastName"));
				// Enter Date of Birth
				accLookup.setTextToFields("Date of Birth",
						Stock.GetParameterValue("DOB"));
				// Enter Street Address
				accLookup.setTextToFields("Street Address",
						Stock.GetParameterValue("StreetAddress"));
			}

			// Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					true);
			Thread.sleep(4000);

			// Verify error message displayed
			ActErrMessage = accLookup.getMainErrorMsg();
			if (ActErrMessage.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message displayed",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(Stock.GetParameterValue("ExpectedErrorMsg"),
						ActErrMessage, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message after submitting invalid details",
							"Expected error message is displayed.\nExpected: "
									+ Stock.GetParameterValue("ExpectedErrorMsg")
									+ "\nActual:" + ActErrMessage, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message after submitting invalid details",
							"Expected error message is not displayed.\nExpected: "
									+ Stock.GetParameterValue("ExpectedErrorMsg")
									+ "\n" + ActErrMessage, true);
				}
			}

			// Switch to other tab - to help continuation for other iterations
			// if any
			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {
				accLookup.navigateToTab("I do not have a PIN");
			} else {
				accLookup.navigateToTab("I have a PIN");
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
			// throw ae;
		} finally {

			try {
				String[] sqlQuery;
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1],
						Stock.GetParameterValue("SSN"));

			} catch (Exception e) {
				Reporter.logEvent(Status.FAIL, "A run time exception occured.",
						e.getCause().getMessage(), true);
			}
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithoutPINTab(int itr,
			Map<String, String> testdata) {
		SF01_TC03_NegativeFlow_WithAndWithoutPINTabs(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithPINTab(int itr,
			Map<String, String> testdata) {
		SF01_TC03_NegativeFlow_WithAndWithoutPINTabs(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithoutPINTab_Apple(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) +"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			String ActErrMessage;
			Web.getDriver()
					.get("https://proj2.retirementpartner.com/participant-web-services/ws/appleRegLanding.do?accu=Apple&token=M0hFuIOjV3nCoTYKOPFd7G5vfErJkEVbKBpNzxGjRgXhYK5dLOh488J7lakZ2TS82NgzgDT5iAdnWkKOXptXxSlYo%2Bf0bRuGaiTU9SqGTio%3D");
			AccountLookup accLookup = new AccountLookup();
			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link

			/*
			 * N/A
			 */
			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			/*
			 * N/A
			 */

			// Step 4 - Verify user is on opted tab
			/*
			 * N/A
			 */

			// Step 5 - Enter a valid participant details and click continue

			// Enter SSN
			accLookup.setTextToFields("Social Security Number",
					Stock.GetParameterValue("SSN"));
			// Enter Zip Code
			accLookup.setTextToFields("Zip Code",
					Stock.GetParameterValue("ZipCode"));
			// Enter Last Name
			accLookup.setTextToFields("Last Name",
					Stock.GetParameterValue("LastName"));
			// Enter Date of Birth
			accLookup.setTextToFields("Date of Birth",
					Stock.GetParameterValue("DOB"));
			// Enter Street Address
			accLookup.setTextToFields("Street Address",
					Stock.GetParameterValue("StreetAddress"));

			// Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					true);

			// Verify error message displayed
			ActErrMessage = accLookup.getMainErrorMsg();
			if (ActErrMessage.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message displayed",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(Stock.GetParameterValue("ExpectedErrorMsg"),
						ActErrMessage, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message after submitting invalid details",
							"Expected error message is displayed.\nExpected: "
									+ Stock.GetParameterValue("ExpectedErrorMsg"),
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message after submitting invalid details",
							"Expected error message is not displayed.\nExpected: "
									+ Stock.GetParameterValue("ExpectedErrorMsg")
									+ "\n" + ActErrMessage, true);
				}
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
			// throw ae;
		} finally {

			try {
				String[] sqlQuery;
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1],
						Stock.GetParameterValue("SSN"));
				DB.executeUpdate(sqlQuery[0], "Commit;");

			} catch (Exception e) {
				Reporter.logEvent(Status.FAIL, "A run time exception occured.",
						e.getCause().getMessage(), true);
			}
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithoutPIN_Apple(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) +"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			String actErrMsg;
			boolean blnTempVar;
			String expNoPinUsrLckmsgOne = "Error: The entries you provided do not match our records. Please try again. You have 2 attempt(s) remaining.";
			String expNoPinUsrLockmsgTwo = "Error: The entries you provided do not match our records. Please try again. You have 1 attempt(s) remaining.";
			String expNoPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";

			Web.getDriver()
					.get("https://proj2.retirementpartner.com/participant-web-services/ws/appleRegLanding.do?accu=Apple&token=M0hFuIOjV3nCoTYKOPFd7G5vfErJkEVbKBpNzxGjRgXhYK5dLOh488J7lakZ2TS82NgzgDT5iAdnWkKOXptXxSlYo%2Bf0bRuGaiTU9SqGTio%3D");
			AccountLookup objAccountLookup = new AccountLookup();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.",
					"Account lookup page is displayed", false);

			// verify the first error message that the user gets on entering
			// invalid pin
			objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"),
					Stock.GetParameterValue("ZIP_CODE"),
					Stock.GetParameterValue("LAST_NAME"),
					Stock.GetParameterValue("BIRTH_DATE"),
					Stock.GetParameterValue("FIRST_LINE_MAILING"));

			actErrMsg = objAccountLookup.getMainErrorMsg();
			if (actErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message for User Lock - try 1'"
								+ actErrMsg,
						"No error message displayed on account lookup page.",
						false);
			} else {
				blnTempVar = Web.VerifyText(actErrMsg, expNoPinUsrLckmsgOne,
						true);
				if (blnTempVar) {
					Reporter.logEvent(Status.PASS,
							"Verify error message for User Lock - try 1'"
									+ actErrMsg,
							"Error message displayed as Expected.", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 1'"
									+ actErrMsg,
							"Error message did not match to the Expected error message.",
							true);
				}

			}

			// avoid staleElementException

			Web.getDriver().navigate().refresh();

			// verify the second error message that the user gets on
			// entering invalid pin
			objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"),
					Stock.GetParameterValue("ZipCode"),
					Stock.GetParameterValue("LastName"),
					Stock.GetParameterValue("DOB"),
					Stock.GetParameterValue("StreetAddress"));

			actErrMsg = objAccountLookup.getMainErrorMsg();
			if (actErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message for User Lock - try 2'"
								+ actErrMsg,
						"No error message displayed on account lookup page.",
						false);
			} else {
				blnTempVar = Web.VerifyText(actErrMsg, expNoPinUsrLockmsgTwo,
						true);
				if (blnTempVar) {
					Reporter.logEvent(Status.PASS,
							"Verify error message for User Lock - try 2'"
									+ actErrMsg,
							"Error message displayed as Expected.", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 2'"
									+ actErrMsg,
							"Error message did not match to the Expected error message.",
							true);
				}
			}

			Web.getDriver().navigate().refresh();

			// verify the final error message that the user gets on entering
			// invalid pin,where the user is locked
			objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"),
					Stock.GetParameterValue("ZipCode"),
					Stock.GetParameterValue("LastName"),
					Stock.GetParameterValue("DOB"),
					Stock.GetParameterValue("StreetAddress"));

			actErrMsg = objAccountLookup.getMainErrorMsg();
			if (actErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message for User Lock down'" + actErrMsg,
						"user profile lock msg has not been displayed. Please check your data",
						false);
			} else {
				if (actErrMsg.trim().toUpperCase()
						.startsWith(expNoPinUsrLockmsg.trim().toUpperCase())) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message for User Lock down'"
									+ actErrMsg,
							"User has locked his profile sucessfully after 3 unsuccessful tries.",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock down'"
									+ actErrMsg,
							"User has NOT locked his profile sucessfully after 3 unsuccessful tries.",
							false);
				}
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
			String[] sqlQuery;
			try {
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1],
						Stock.GetParameterValue("SSN"));
				DB.executeUpdate(sqlQuery[0], "Commit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithAndWithoutPIN(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			String activeTab;
			String actErrMsg;
			boolean blnTempVar;
			String expNoPinUsrLckmsgOne = "Error: The entries you provided do not match our records. Please try again. You have 2 attempt(s) remaining.";
			String expNoPinUsrLockmsgTwo = "Error: The entries you provided do not match our records. Please try again. You have 1 attempt(s) remaining.";
			String expNoPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";
			String expWithPinUsrLckmsgOne = "The passcode you entered is invalid. Please re-enter your passcode. You have 2 attempt(s) left.";
			String expWithPinUsrLckmsgTwo = "The passcode you entered is invalid. Please re-enter your passcode. You have 1 attempt(s) left.";
			String expWithPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";
			precondition();
			LoginPage objloginPage = new LoginPage();
			AccountLookup objAccountLookup = new AccountLookup(objloginPage)
					.get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.",
					"Account lookup page is displayed", false);

			objAccountLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {

				// Verify if the user is on the 'I have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
				if (activeTab.trim().equalsIgnoreCase("I have a PIN")) {
					Reporter.logEvent(Status.PASS, "Verify Verification",
							"The user navigated to 'I have a PIN' tab", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Verification",
							" The user did not navigate to 'I have a PIN'",
							false);
				}

				// verify the first error message that the user gets on entering
				// invalid pin
				objAccountLookup.registrationWithPIN(
						Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 1'"
									+ actErrMsg,
							"No error message displayed on account lookup page.",
							false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,
							expWithPinUsrLckmsgOne, true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS,
								"Verify error message for User Lock - try 1'"
										+ actErrMsg,
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify error message for User Lock - try 1'"
										+ actErrMsg,
								"Error message did not match to the Expected error message.",
								true);
					}

				}

				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");

				// verify the second error message that the user gets on
				// entering invalid pin
				objAccountLookup.registrationWithPIN(
						Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 2'"
									+ actErrMsg,
							"No error message displayed on account lookup page.",
							false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,
							expWithPinUsrLckmsgTwo, true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS,
								"Verify error message for User Lock - try 2'"
										+ actErrMsg,
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify error message for User Lock - try 2'"
										+ actErrMsg,
								"Error message did not match to the Expected error message.",
								true);
					}
				}

				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");

				// verify the final error message that the user gets on entering
				// invalid pin,where the user is locked
				objAccountLookup.registrationWithPIN(
						Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify user Lock Message'" + actErrMsg,
							"user profile lock msg has not been displayed. Please check your data",
							false);
				} else {
					if (actErrMsg
							.trim()
							.toUpperCase()
							.startsWith(
									expWithPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(
								Status.PASS,
								"Verify user Lock Message'" + actErrMsg,
								"User has locked his profile sucessfully after 3 unsuccessful tries.",
								true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify user Lock Message'" + actErrMsg,
								"User profile lock message after 3 unsuccessful login attempts was not verified correctly",
								true);
					}
				}

			} else if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I do not have a PIN")) {

				// Verify if the user is on the 'I do not have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
				if (activeTab.trim().equalsIgnoreCase("I do not have a PIN")) {
					Reporter.logEvent(Status.PASS, "Verify Verification",
							"The user navigated to 'I do not have a PIN' tab",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Verification",
							" The user did not navigate to 'I do not have a PIN'",
							false);
				}

				// verify the first error message that the user gets on entering
				// invalid pin
				objAccountLookup.registerWithoutPIN(
						Stock.GetParameterValue("SSN"),
						Stock.GetParameterValue("ZipCode"),
						Stock.GetParameterValue("LastName"),
						Stock.GetParameterValue("DOB"),
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 1'"
									+ actErrMsg,
							"No error message displayed on account lookup page.",
							false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,
							expNoPinUsrLckmsgOne, true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS,
								"Verify error message for User Lock - try 1'"
										+ actErrMsg,
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify error message for User Lock - try 1'"
										+ actErrMsg,
								"Error message did not match to the Expected error message.",
								true);
					}

				}

				// avoid staleElementException

				objAccountLookup.clickOnFields("I have a PIN");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Do nothing
				}
				objAccountLookup.clickOnFields("I do not have a PIN");

				// verify the second error message that the user gets on
				// entering invalid pin
				objAccountLookup.registerWithoutPIN(
						Stock.GetParameterValue("SSN"),
						Stock.GetParameterValue("ZipCode"),
						Stock.GetParameterValue("LastName"),
						Stock.GetParameterValue("DOB"),
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock - try 2'"
									+ actErrMsg,
							"No error message displayed on account lookup page.",
							false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,
							expNoPinUsrLockmsgTwo, true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS,
								"Verify error message for User Lock - try 2'"
										+ actErrMsg,
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify error message for User Lock - try 2'"
										+ actErrMsg,
								"Error message did not match to the Expected error message.",
								true);
					}
				}

				objAccountLookup.clickOnFields("I have a PIN");
				objAccountLookup.clickOnFields("I do not have a PIN");

				// verify the final error message that the user gets on entering
				// invalid pin,where the user is locked
				objAccountLookup.registerWithoutPIN(
						Stock.GetParameterValue("SSN"),
						Stock.GetParameterValue("ZipCode"),
						Stock.GetParameterValue("LastName"),
						Stock.GetParameterValue("DOB"),
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message for User Lock down'"
									+ actErrMsg,
							"user profile lock msg has not been displayed. Please check your data",
							false);
				} else {
					if (actErrMsg
							.trim()
							.toUpperCase()
							.startsWith(expNoPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(
								Status.PASS,
								"Verify error message for User Lock down'"
										+ actErrMsg,
								"User has locked his profile sucessfully after 3 unsuccessful tries.",
								true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify error message for User Lock down'"
										+ actErrMsg,
								"User has NOT locked his profile sucessfully after 3 unsuccessful tries.",
								false);
					}
				}
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
				String[] sqlQuery;
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1],
						Stock.GetParameterValue("SSN"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithPIN(int itr,
			Map<String, String> testdata) {
		SF01_TC05_UserLock_WithAndWithoutPIN(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithOutPIN(int itr,
			Map<String, String> testdata) {
		SF01_TC05_UserLock_WithAndWithoutPIN(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SF03_TC01_AccountSetup(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));

			prepareLoginTestData(Stock.GetParameterValue("queryName"),
					Stock.GetParameterValue("ga_PlanId"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			if (Common.getSponser().equalsIgnoreCase("Apple")) {
				Web.getDriver()
						.get("https://proj2.retirementpartner.com/participant-web-services/ws/appleRegLanding.do?accu=Apple&token=M0hFuIOjV3nCoTYKOPFd7G5vfErJkEVbKBpNzxGjRgXhYK5dLOh488J7lakZ2TS82NgzgDT5iAdnWkKOXptXxSlYo%2Bf0bRuGaiTU9SqGTio%3D");
				AccountLookup objAccountLookup = new AccountLookup();
				AccountSetup objAccountSetup = new AccountSetup(
						objAccountLookup).get();

				if (Stock.GetParameterValue("FunctionalityType").trim()
						.equalsIgnoreCase("contactinformation")) {
					objAccountSetup.validateContactInformationUI(Stock
							.GetParameterValue("SSN"));
				} else {
					objAccountSetup.validateUserNameAndPasswordUI();
				}

			} else {
				LoginPage objloginPage = new LoginPage();
				AccountLookup objAccountLookup = new AccountLookup(objloginPage);
				AccountSetup objAccountSetup = new AccountSetup(
						objAccountLookup).get();

				if (Stock.GetParameterValue("FunctionalityType").trim()
						.equalsIgnoreCase("contactinformation")) {
					objAccountSetup.validateContactInformationUI(Stock
							.GetParameterValue("SSN"));
				} else {
					objAccountSetup.validateUserNameAndPasswordUI();
				}
				objloginPage.get();
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
	public void SF04_TC01_AccountLookup_PositiveFlow(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"),
					Stock.GetParameterValue("ga_PlanId"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN = Stock.GetParameterValue("SSN");
			String hdrBlockText;
			boolean isMatching=false;
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			AccountSetup accSetup = new AccountSetup(accLookup);

			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			// Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is on \"" + Stock.GetParameterValue("TabName")
								+ "\" tab", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is not on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						true);
			}

			// Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {
				// Enter SSN
				accLookup
						.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				// Enter PIN
				accLookup
						.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I do not have a PIN")) {
				// Enter SSN
				accLookup.setTextToFields("Social Security Number",
						Stock.GetParameterValue("SSN"));
				// Enter Zip Code
				accLookup.setTextToFields("Zip Code",
						Stock.GetParameterValue("ZIP_CODE"));
				// Enter Last Name
				accLookup.setTextToFields("Last Name",
						Stock.GetParameterValue("LAST_NAME"));
				// Enter Date of Birth
				accLookup.setTextToFields("Date of Birth",
						Stock.GetParameterValue("BIRTH_DATE"));
				// Enter Street Address
				accLookup.setTextToFields("Street Address",
						Stock.GetParameterValue("FIRST_LINE_MAILING").split(" ")[0]);
			}

			// Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					false);

			Thread.sleep(5000);
			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			if (hdrBlockText == null) {
		
				hdrBlockText = accSetup.getAccountSetupHeaderText();
				isMatching = Web
						.VerifyText(
								"Create username and password",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Create username and password' header is displayed",
							"User successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Create username and password' header is displayed",
							"'Create username and password' header is Not displayed", true);
				}
			} else {
				isMatching = Web
						.VerifyText(
								"We found you!\nVerification codes for enhanced security will be sent to the email address or phone number you provide below.",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'We found you!' header is displayed",
							"user successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'We found you!' header is displayed",
							"We found you! Header is not displayed", true);
				}
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
				ExecuteQuery.UnRegisterParticipant(SSN);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void AccountSetup_WithPin_Validate_ContactInfo(int itr, Map<String, String> testdata) {
		SF03_TC01_AccountSetup(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void AccountSetup_WithPin_Validate_UserNameInfo(int itr, Map<String, String> testdata) {
		SF03_TC01_AccountSetup(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AccountSetup_WithOutPin_Validate_ContactInfo(int itr, Map<String, String> testdata) {
		SF03_TC01_AccountSetup(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void AccountSetup_WithOutPin_Validate_UserNameInfo(int itr, Map<String, String> testdata) {
		SF03_TC01_AccountSetup(itr, testdata);
	}
		
	@Test(dataProvider = "setData")
	public void AccountLookup_PositiveFlow_WithPIN(int itr,
			Map<String, String> testdata) {
		SF04_TC01_AccountLookup_PositiveFlow(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void AccountLookup_PositiveFlow_WithOutPIN(int itr,
			Map<String, String> testdata) {
		SF04_TC01_AccountLookup_PositiveFlow(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SF04_TC01_AccountLookup_PositiveFlow_Apple(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) +"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			String hdrBlockText;
			Web.getDriver()
					.get("https://proj2.retirementpartner.com/participant-web-services/ws/appleRegLanding.do?accu=Apple&token=M0hFuIOjV3nCoTYKOPFd7G5vfErJkEVbKBpNzxGjRgXhYK5dLOh488J7lakZ2TS82NgzgDT5iAdnWkKOXptXxSlYo%2Bf0bRuGaiTU9SqGTio%3D");
			AccountLookup accLookup = new AccountLookup();
			AccountSetup accSetup = new AccountSetup(accLookup);

			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accSetup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			/*
			 * N/A
			 */

			// Step 4 - Verify user is on opted tab
			/*
			 * N/A
			 */

			// Step 5 - Enter a valid participant details and click continue

			// Enter SSN
			accLookup.setTextToFields("Social Security Number",
					Stock.GetParameterValue("SSN"));
			// Enter Zip Code
			accLookup.setTextToFields("Zip Code",
					Stock.GetParameterValue("ZipCode"));
			// Enter Last Name
			accLookup.setTextToFields("Last Name",
					Stock.GetParameterValue("LastName"));
			// Enter Date of Birth
			accLookup.setTextToFields("Date of Birth",
					Stock.GetParameterValue("DOB"));
			// Enter Street Address
			accLookup.setTextToFields("Street Address",
					Stock.GetParameterValue("StreetAddress"));

			// Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					false);

			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			boolean isMatching =false;
			if (hdrBlockText == null) {
				
				hdrBlockText = accSetup.getAccountSetupHeaderText();
				isMatching = Web
						.VerifyText(
								"Create username and password",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Create username and password' header is displayed",
							"User successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Create username and password' header is displayed",
							"'Create username and password' header is Not displayed", true);
				} 
			}
				else {
				Web.VerifyPartialText(
						/* "We found you!\nTo continue, provide your contact information and create a username and password." */"Create username and password",
						hdrBlockText, true);
				Reporter.logEvent(
						Status.PASS,
						"Navigate to Account setup page",
						"user successfully navigated to the Account Setup page",
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
	public void SSIT_PPT_Reg_MFA_ON_TC01_5Point_registration(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			prepareLoginTestData(Stock.GetParameterValue("queryName"),
					Stock.GetParameterValue("ga_PlanId"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN = Stock.GetParameterValue("SSN");
			String hdrBlockText;
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			AccountSetup accSetup = new AccountSetup(accLookup);
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			LandingPage homePage = new LandingPage(objAuth);
			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			boolean isMatching = false;
			String verificationCode = null;
			boolean isDisplayed = false;
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			// Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is on \"" + Stock.GetParameterValue("TabName")
								+ "\" tab", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is not on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						true);
			}

			// Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {
				// Enter SSN
				accLookup
						.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				// Enter PIN
				accLookup
						.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I do not have a PIN")) {
				// Enter SSN
				accLookup.setTextToFields("Social Security Number",
						Stock.GetParameterValue("SSN"));
				// Enter Zip Code
				accLookup.setTextToFields("Zip Code",
						Stock.GetParameterValue("ZIP_CODE"));
				// Enter Last Name
				accLookup.setTextToFields("Last Name",
						Stock.GetParameterValue("LAST_NAME"));
				// Enter Date of Birth
				accLookup.setTextToFields("Date of Birth",
						Stock.GetParameterValue("BIRTH_DATE"));
				// Enter Street Address
				accLookup.setTextToFields("Street Address",
						Stock.GetParameterValue("FIRST_LINE_MAILING"));

			}

			// Click on Continue button
			accLookup.clickOnFields("Continue");

			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					false);

			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderText();
			if (hdrBlockText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account setup Header block text",
						"Header text block is not displayed on the page", true);
			} else {
				isMatching = Web.VerifyText("Create username and password",
						hdrBlockText, true);

				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Create username and password' header is displayed",
							"Create username and password Header is displayed",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Create username and password' header is displayed",
							"Create username and password Header is not displayed",
							true);
				}
			}

			// Verify 'Username' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "USERNAME")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Username' text field is displayed",
						"'Username' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Username' text field is displayed",
						"'Username' is not displayed", false);
			}

			// Verify 'Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Password' text field is displayed",
						"'Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Password' text field is displayed",
						"'Password' is not displayed", false);
			}

			// Verify 'Re-Enter Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "RE-ENTER PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' is not displayed", false);
			}
			
           /* String email=accSetup.getWebElementText("EMAIL ADDRESS");
            String phoneNo=accSetup.getWebElementText("MOBILE PHONE NUMBER");
           
            if(email.isEmpty()||!email.contains("@")||!email.contains(".com")){
            	Web.setTextToTextBox("EMAIL ADDRESS", accSetup, "discard@gwl.com");
            }
            if(phoneNo.isEmpty()||phoneNo.length()<10){
            	Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup, "9999999999");
            }*/
			Web.setTextToTextBox("USERNAME", accSetup,
					Stock.GetParameterValue("SSN") + "ABC");
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");
			Thread.sleep(10000);
			objAuth.selectCodeDeliveryOption(Stock
					.GetParameterValue("codeDeliveryOption"));
			if (lib.Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			objAuth.submitVerificationCode(verificationCode, false, false);

			Web.clickOnElement(objAuth, "CONTINUE TO MY ACCOUNT");
			Thread.sleep(5000);
			homePage.dismissPopUps(true, true);
			Web.waitForElement(homePage, "Log out");
			isDisplayed = Web.isWebElementDisplayed(homePage, "Log out")&& Web.isWebElementDisplayed(homePage, "Retirement income");
					if (isDisplayed) {
						Reporter.logEvent(Status.PASS,
								"Verify user is on landing page",
								"user is navigated to landing page", true);
						Web.clickOnElement(homePage, "LOGOUT");
					} else if (Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")) {
						Reporter.logEvent(Status.PASS,
								"Verify user is on landing page",
								"user is navigated to MyACCOUNTS page", true);
						Web.clickOnElement(homePage, "LOGOUT");
					} else {
						Reporter.logEvent(Status.FAIL,
								"Verify user is on landing page",
								"user is not navigated to Landing page", true);
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
				ExecuteQuery.UnRegisterParticipant(SSN);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Registration_MFA_ON_5Point_registration(int itr,
			Map<String, String> testdata) {
		SSIT_PPT_Reg_MFA_ON_TC01_5Point_registration(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Registration_MFA_ON_2Point_registration(int itr,
			Map<String, String> testdata) {
		SSIT_PPT_Reg_MFA_ON_TC01_5Point_registration(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Registration_MFA_OFF_5Point_registration(int itr,
			Map<String, String> testdata) {
		SSIT_PPT_Reg_MFA_OFF_TC01_5Point_registration(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Registration_MFA_OFF_2Point_registration(int itr,
			Map<String, String> testdata) {
		SSIT_PPT_Reg_MFA_OFF_TC01_5Point_registration(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void SSIT_PPT_Reg_MFA_OFF_TC01_5Point_registration(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"),
					Stock.GetParameterValue("ga_PlanId"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN = Stock.GetParameterValue("SSN");
			String hdrBlockText;
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			AccountSetup accSetup = new AccountSetup(accLookup);
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			LandingPage homePage = new LandingPage(objAuth);
			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			boolean isMatching = false;
			String verificationCode = null;
			boolean isDisplayed = false;
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			// Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is on \"" + Stock.GetParameterValue("TabName")
								+ "\" tab", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is not on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						true);
			}

			// Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I have a PIN")) {
				// Enter SSN
				accLookup
						.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				// Enter PIN
				accLookup
						.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim()
					.equalsIgnoreCase("I do not have a PIN")) {
				// Enter SSN
				accLookup.setTextToFields("Social Security Number",
						Stock.GetParameterValue("SSN"));
				// Enter Zip Code
				accLookup.setTextToFields("Zip Code",
						Stock.GetParameterValue("ZIP_CODE"));
				// Enter Last Name
				accLookup.setTextToFields("Last Name",
						Stock.GetParameterValue("LAST_NAME"));
				// Enter Date of Birth
				accLookup.setTextToFields("Date of Birth",
						Stock.GetParameterValue("BIRTH_DATE"));
				// Enter Street Address
				accLookup.setTextToFields("Street Address",
						Stock.GetParameterValue("FIRST_LINE_MAILING"));

			}

			// Click on Continue button
			accLookup.clickOnFields("Continue");

			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					false);
			Thread.sleep(5000);
			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			
			if (hdrBlockText == null) {
				
				hdrBlockText = accSetup.getAccountSetupHeaderText();
				isMatching = Web
						.VerifyText(
								"Create username and password",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Create username and password' header is displayed",
							"User successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Create username and password' header is displayed",
							"'Create username and password' header is Not displayed", true);
				} 
			} else {
				isMatching = Web
						.VerifyText(
								"We found you!\nVerification codes for enhanced security will be sent to the email address or phone number you provide below.",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'We found you!' header is displayed",
							"user successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'We found you!' header is displayed",
							"We found you! Header is not displayed", true);
				}
			}

			// Verify Provide contact information Header
			hdrBlockText = accSetup.getAccountSetupContactInfoHeaderText();
			isMatching = Web.VerifyText("Provide contact information",
					hdrBlockText, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Contact Information' header is displayed",
						"Provide contact information Header is displayed",
						false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Contact Information' header is displayed",
						"Provide contact information Header is not displayed",
						false);
			}

			// Verify 'Email Address' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "EMAIL ADDRESS")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Email Address' text field is displayed",
						"'Email Address' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Email Address' text field is displayed",
						"'Email Address' is not displayed", false);
			}

			// Verify 'Mobile Phone Number' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "MOBILE PHONE NUMBER")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Mobile Phone Number' text field is displayed",
						"'Email Address' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Mobile Phone Number' text field is displayed",
						"'Email Address' is not displayed", false);
			}
			// Verify 'Username' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "USERNAME")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Username' text field is displayed",
						"'Username' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Username' text field is displayed",
						"'Username' is not displayed", false);
			}

			// Verify 'Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Password' text field is displayed",
						"'Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Password' text field is displayed",
						"'Password' is not displayed", false);
			}

			// Verify 'Re-Enter Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "RE-ENTER PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' is not displayed", false);
			}
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup,
					Stock.GetParameterValue("SSN") + "ABC");
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(10000);
			objAuth.selectCodeDeliveryOption(Stock
					.GetParameterValue("codeDeliveryOption"));
			if (lib.Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			objAuth.submitVerificationCode(verificationCode, false, false);

			Web.clickOnElement(objAuth, "CONTINUE TO MY ACCOUNT");
			Thread.sleep(5000);
			homePage.dismissPopUps(true, true);
			Web.waitForElement(homePage, "Log out");
			isDisplayed = Web.isWebElementDisplayed(homePage, "Log out")
					&& Web.isWebElementDisplayed(homePage, "Retirement income");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify user is on landing page",
						"user is navigated to landing page", true);
				Web.clickOnElement(homePage, "LOGOUT");
			} else if (Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")) {
				Reporter.logEvent(Status.PASS,
						"Verify user is on landing page",
						"user is navigated to MyACCOUNTS page", true);
				Web.clickOnElement(homePage, "LOGOUT");
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify user is on landing page",
						"user is not navigated to Landing page", true);
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
				ExecuteQuery.UnRegisterParticipant(SSN);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void NPDIRegistration(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"),
					Stock.GetParameterValue("ga_PlanId"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN = Stock.GetParameterValue("SSN");
			String hdrBlockText;
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration rigistration =new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup(accLookup);
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			LandingPage homePage = new LandingPage(objAuth);
			// Steps
			// Step 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			boolean isMatching = false;
			String verificationCode = null;
			boolean isDisplayed = false;
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			// Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is on \"" + Stock.GetParameterValue("TabName")
								+ "\" tab", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						"User is not on \""
								+ Stock.GetParameterValue("TabName") + "\" tab",
						true);
			}

			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),  Stock.GetParameterValue("planEnrollmentCode"));
			// Click on Continue button
			accLookup.clickOnFields("Continue");

			Reporter.logEvent(
					Status.INFO,
					"Enter participant details and click on Continue button.",
					"Submitted participant details and clicked on Continue button",
					false);
			Thread.sleep(5000);
			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			
			if (hdrBlockText == null) {
				
				hdrBlockText = accSetup.getAccountSetupHeaderText();
				isMatching = Web
						.VerifyText(
								"Create username and password",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Create username and password' header is displayed",
							"User successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Create username and password' header is displayed",
							"'Create username and password' header is Not displayed", true);
				} 
			} else {
				isMatching = Web
						.VerifyText(
								"We found you!\nVerification codes for enhanced security will be sent to the email address or phone number you provide below.",
								hdrBlockText, true);
				if (isMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'We found you!' header is displayed",
							"user successfully navigated to the Account Setup page",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'We found you!' header is displayed",
							"We found you! Header is not displayed", true);
				}
			}

			// Verify Provide contact information Header
			hdrBlockText = accSetup.getAccountSetupContactInfoHeaderText();
			isMatching = Web.VerifyText("Provide contact information",
					hdrBlockText, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Contact Information' header is displayed",
						"Provide contact information Header is displayed",
						false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Contact Information' header is displayed",
						"Provide contact information Header is not displayed",
						false);
			}

			// Verify 'Email Address' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "EMAIL ADDRESS")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Email Address' text field is displayed",
						"'Email Address' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Email Address' text field is displayed",
						"'Email Address' is not displayed", false);
			}

			// Verify 'Mobile Phone Number' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "MOBILE PHONE NUMBER")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Mobile Phone Number' text field is displayed",
						"'Email Address' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Mobile Phone Number' text field is displayed",
						"'Email Address' is not displayed", false);
			}
			// Verify 'Username' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "USERNAME")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Username' text field is displayed",
						"'Username' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Username' text field is displayed",
						"'Username' is not displayed", false);
			}

			// Verify 'Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Password' text field is displayed",
						"'Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Password' text field is displayed",
						"'Password' is not displayed", false);
			}

			// Verify 'Re-Enter Password' field is displayed
			if (Web.isWebElementDisplayed(accSetup, "RE-ENTER PASSWORD")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' field is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Re-Enter Password' text field is displayed",
						"'Re-Enter Password' is not displayed", false);
			}
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup,
					Stock.GetParameterValue("SSN") + "ABC");
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(5000);
			objAuth.selectCodeDeliveryOption(Stock
					.GetParameterValue("codeDeliveryOption"));
			if (lib.Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			objAuth.submitVerificationCode(verificationCode, false, false);

			Web.clickOnElement(objAuth, "CONTINUE TO MY ACCOUNT");
			Thread.sleep(5000);
			Web.waitForElement(homePage, "Log out");
			isDisplayed = Web.isWebElementDisplayed(homePage, "Log out")
					&& Web.isWebElementDisplayed(homePage, "Retirement income");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify user is on landing page",
						"user is navigated to landing page", true);
				Web.clickOnElement(homePage, "LOGOUT");
			} else if (Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")) {
				Reporter.logEvent(Status.PASS,
						"Verify user is on landing page",
						"user is navigated to MyACCOUNTS page", true);
				Web.clickOnElement(homePage, "LOGOUT");
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify user is on landing page",
						"user is not navigated to Landing page", true);
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
				ExecuteQuery.UnRegisterParticipant(SSN);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void NPDI_001_validation_for_Ihaveaplanenrollment_code_page(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
		
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 - Account verification page should be displayed with 3 tabs
			//1. I do not have a pin
			//2. i have a pin
			//3. I have a plan  enrollment code.
			if(accLookup.isFieldDisplayed("I DO NOT HAVE A PIN")){
				Reporter.logEvent(
					Status.PASS,
					"Verify 'I do not have a pin' tab is displayed",
					"'I do not have a pin' tab is displayed",
					true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'I do not have a pin' tab is displayed",
					"'I do not have a pin' tab is not displayed", true);
		} 
			if(accLookup.isFieldDisplayed("I HAVE A PIN")){
				Reporter.logEvent(
					Status.PASS,
					"Verify 'I HAVE A PIN' tab is displayed",
					"'I HAVE A PIN' tab is displayed",
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'I HAVE A PIN' tab is displayed",
					"'I HAVE A PIN' tab is not displayed", false);
		} 
			if(accLookup.isFieldDisplayed("I HAVE A Plan Enrollment Code")){
				Reporter.logEvent(
					Status.PASS,
					"Verify 'I HAVE A Plan Enrollment Code' tab is displayed",
					"'I HAVE A Plan Enrollment Code' tab is displayed",
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'I HAVE A Plan Enrollment Code' tab is displayed",
					"'I HAVE A Plan Enrollment Code' tab is not displayed", false);
		} 

			
			if (accLookup.getActiveTabName().trim()
					.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is on \" I do not have a PIN \" tab",
						"User is on \" I do not have a PIN \" tab", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is on \" I do not have a PIN \" tab",
						"User is on \" I do not have a PIN \" tab", false);
					
			}
			//Step 4
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.isLabelDisplayed("Group Id / Plan Number");
			accLookup.isLabelDisplayed("Plan Enrollment Code");
			if(Web.isWebElementDisplayed(accLookup, "Group Id/Plan Number")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Group Id/Plan Number' input Field is displayed",
						"'Group Id/Plan Number' input Field is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Group Id/Plan Number' input Field is displayed",
						"'Group Id/Plan Number' input Field is not displayed", true);
			} 
			
			if(Web.isWebElementDisplayed(accLookup, "Plan Enrollment Code")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Plan Enrollment Code' input Field is displayed",
						"'Plan Enrollment Code' input Field is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Plan Enrollment Code' input Field is displayed",
						"'Plan Enrollment Code' input Field is not displayed", true);
			} 
			//Step 5
			accLookup.clickOnFields("Group Id/Plan Number");
			accLookup.clickOnFields("CONTINUE");
			String actualErrMsg = accLookup.getFieldErrorMsg("Group Id/Plan Number");
			String expectedErrMsg = "Group Id is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Group Id/Plan Number",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}
			//step 6
			accLookup.clickOnFields("Plan Enrollment Code");
			accLookup.clickOnFields("CONTINUE");
			actualErrMsg = accLookup.getFieldErrorMsg("Plan Enrollment Code");
		    expectedErrMsg = "Plan Enrollment Code is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(
						Status.FAIL,
						"Verify error message '" + expectedErrMsg
								+ "' is displayed",
						"No error message displayed for Plan Enrollment Code",
						false);
			} else {
				if (Web.VerifyText(actualErrMsg, expectedErrMsg, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '"
							+ expectedErrMsg + "' is displayed",
							"Error message displayed is not matching.\nActual message: "
									+ actualErrMsg, false);
				}
			}
			
			//Step 7
			accLookup.registerWithPlanEnrollmentCode("12345-01","3333");
			actualErrMsg=accLookup.getMainErrorMsg();
			expectedErrMsg="Please enter a valid Group ID / Plan Number and Plan Enrollment Code.";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message after submitting invalid details",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message after submitting invalid details",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message after submitting invalid details",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}

			//Step 8
			
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
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
	
	@Test(dataProvider = "setData")
	public void NPDI_004_Validation_for_Registration_Page_Mailing_Address(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN=generateRandomSSN(9);
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			AccountSetup accSetup =new AccountSetup();
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
														Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}
			
			//Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			//Step 5
			registration.verifyInLineValidationsForAddressLine1();
			//Step 6
			registration.verifyInLineValidationsForAddressLine2();
			//Step 7
			registration.verifyInLineValidationsForCityField();
			//Step 8
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			registration.providePersonalInformation("ABC", "XYZ", "ABC", "12/12/1998", "Male",SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015");
			registration.provideMailingAddress("123ABC", "QASD", "Aptos", "California", "95001", "United States");
			//Step 9
			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if(Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
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
	
	@Test(dataProvider = "setData")
	public void NPDI_003_Validation_for_Registration_Page_Employment_formation(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
														Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}
			
			//Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			//Step 5
			registration.isLabelDisplayed("Current Annual Income");
			if(Web.VerifyText(registration.getWebElementText("LABEL DATE OF HIRE"),"DATE OF HIRE MM/DD/YYYY")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Label 'DATE OF HIRE MM/DD/YYYY' is displayed",
						"Label 'DATE OF HIRE MM/DD/YYYY' is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Label 'DATE OF HIRE MM/DD/YYYY' is displayed",
						"Label 'DATE OF HIRE MM/DD/YYYY' is not displayed", true);
			}
			//Step 6
			registration.validateCurrentAnnualIncomeField();
			registration.validateDateOfHireField();
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
	@Test(dataProvider = "setData")
	public void NPDI_005_Validation_For_Registration_Page_Profile_Settings_Page(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN=generateRandomSSN(9);
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			AccountSetup accSetup =new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
														Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}
			
			//Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			//Step 5 & 6
			
			registration.providePersonalInformation("ABC", "XYZ", "ABC", "12/12/1998", "Male",SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015");
			registration.provideMailingAddress("123ABC", "QASD", "Aptos", "California", "95001", "United States");
			
			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if(Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			//Step 7
			accSetup.validateProfileSettinsPage();
			//Step 8
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup,
					SSN+ "ABC");
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(10000);

			Web.waitForElement(objAuth, "Header Enhanced Security");
			if(Web.isWebElementDisplayed(objAuth, "Header Enhanced Security")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is not displayed", true);
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
	@Test(dataProvider = "setData")
	public void NPDI_006_Validation_For_Enhanced_Security_Pages(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			SSN=generateRandomSSN(9);
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			AccountSetup accSetup =new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
														Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}
			
			//Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			//Step 5
			
			registration.providePersonalInformation("ABC", "XYZ", "ABC", "12/12/1998", "Male",SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015");
			registration.provideMailingAddress("123ABC", "QASD", "Aptos", "California", "95001", "United States");
			
			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if(Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			//Step 6
			accSetup.validateProfileSettinsPage();
			//Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup,
					SSN+ "ABC");
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			// Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(10000);

			Web.waitForElement(objAuth, "Header Enhanced Security");
			if(Web.isWebElementDisplayed(objAuth, "Header Enhanced Security")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Enhanced Security' Page is displayed",
						"'Enhanced Security' Page is not displayed", true);
			}
			//Step 8
			Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
					+ "phone number or email address listed for your account.");
			//Step 9
			Common.isLabelDisplayed("Where should we send your code?");
			
			if(Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is not displayed", true);
			}
			//Step 10
			String[] deliveryMethodOptions={"Choose delivery method","Text me: ***-***-9999","Call me: ***-***-9999","Email: *******@gwl.com"};
			objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
			//Step 11
			Web.clickOnElement(objAuth,"BUTTON SEND ME A CODE");
			Web.clickOnElement(objAuth,"BUTTON SEND ME A CODE");
			Thread.sleep(4000);
			Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
			//Step 12
			objAuth.selectCodeDeliveryOption(Stock.GetParameterValue("codeDeliveryOption"),true);
			String verificationCode="";
				if (Stock.GetParameterValue("codeDeliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				} 
			//Step 13
			objAuth.submitVerificationCode(verificationCode, false, false);
Web.clickOnElement(objAuth, "SIGN IN");

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
	
	@Test(dataProvider = "setData")
	public void NPDI_002_Validation_RegistrationPage_Provide_Personal_Information(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())  + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration =new Registration(accLookup);
			
			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on Register
			// link
			accLookup.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3 
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode( Stock.GetParameterValue("planNumber"),
														Stock.GetParameterValue("planEnrollmentCode"));
			Web.waitForElement(registration, "Header Registration");
			if(Web.isWebElementDisplayed(registration, "Header Registration")){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Registration' Page is displayed",
						"'Registration' Page is not displayed", true);
			}
			
			//Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			//Step 5
			registration.isLabelDisplayed("First");
			registration.isLabelDisplayed("Middle");
			registration.isLabelDisplayed("Last");
			if(Web.VerifyText(registration.getWebElementText("LABEL DATE OF BIRTH"),"DATE OF BIRTH MM/DD/YYYY")){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Label 'DATE OF BIRTH MM/DD/YYYY' is displayed",
						"Label 'DATE OF BIRTH MM/DD/YYYY' is displayed",
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Label 'DATE OF BIRTH MM/DD/YYYY' is displayed",
						"Label 'DATE OF BIRTH MM/DD/YYYY' is not displayed", true);
			}	
			registration.isLabelDisplayed("Gender");
			registration.isLabelDisplayed("Social Security Number");
			registration.isLabelDisplayed("Marital Status");
			registration.isInputFieldDisplayed("FIRST NAME");
			registration.isInputFieldDisplayed("MIDDLE NAME");
			registration.isInputFieldDisplayed("LAST NAME");
			registration.isInputFieldDisplayed("DATE OF BIRTH");
			registration.isInputFieldDisplayed("GENDER");
			registration.isInputFieldDisplayed("SSN");
			registration.isInputFieldDisplayed("MARITAL STATUS");
			
			//Step 6
			registration.verifyInLineValidationsForFirstName();
			//Step 7
			registration.verifyInLineValidationsForMiddleName();
			//Step 8
			registration.verifyInLineValidationsForLastName();
			//Step 9
			registration.verifyInLineValidationsForDateOfBirthField();
			//Step 10
			registration.verifyInLineValidationsForGenderField();
			//Step 11
			registration.verifyInLineValidationsForSSN();
			//Step 12
			registration.verifyInLineValidationsForMaritalStatusField();
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
	@Test(dataProvider = "setData")
	public void NPDI_007_Register_participant_doesnot_exists_inEASY_system(
			int itr, Map<String, String> testdata) {

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
			SSN = generateRandomSSN(9);
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

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
			registration.setPasswordForPlan();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3
			
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015","Corporate");
			//registration.provideEmploymentInformation("12000", "12/12/2015");
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");

			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if (Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")) {

				Reporter.logEvent(Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			// Step 6
			accSetup.validateProfileSettinsPage();
			// Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABC");
			
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
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
			// Step 8
			Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
					+ "phone number or email address listed for your account.");
			// Step 9
			Common.isLabelDisplayed("Where should we send your code?");

			if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

				Reporter.logEvent(
						Status.PASS,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
						true);
			}
			// Step 10
			String[] deliveryMethodOptions = { "Choose delivery method",
					"Text me: ***-***-9999", "Call me: ***-***-9999",
					"Email: *******@gwl.com" };
			objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
			// Step 11
			
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Thread.sleep(4000);
			Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
			// Step 12
			objAuth.selectCodeDeliveryOption(
					Stock.GetParameterValue("codeDeliveryOption"), true);
			String verificationCode = "";
			if (Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			// Step 13
			objAuth.submitVerificationCode(verificationCode, false, false);
			//Web.clickOnElement(objAuth, "ALREADY HAVE A CODE?");
			//objAuth.submitVerificationCode("74196385", false, false);
			Web.clickOnElement(objAuth, "SIGN IN");

			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("queryName"));

			ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
			rs.first();
			
			String sDbSSN = rs.getString("SSN");
			String sDbGroupId = rs.getString("GA_ID");
			String sDbEmail = rs.getString("EMAIL_ADDRESS");
			
			if(sDbSSN.equalsIgnoreCase(SSN))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"SSN is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"SSN is not matching", false);
			}
			if(sDbGroupId.equalsIgnoreCase(Stock.GetParameterValue("planNumber")))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"Plan Number is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"Plan Number is not matching", false);
			}
			if(sDbEmail.equalsIgnoreCase(Stock.GetParameterValue("EmailId")))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"EmailId is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"EmailId is not matching", false);
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
	
	@Test(dataProvider = "setData")
	public void NPDI_008_Register_a_ppt_exists_in_Easy_DB(int itr, Map<String, String> testdata) {

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
			if(itr==1)
			{
				SSN = generateRandomSSN(9);
			}
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration = new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);

			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on
			// Register
			// link
			accLookup.get();

			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3
			registration.setPasswordForPlan();
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015",Stock.GetParameterValue("divisionName"));
			
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");

			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if (Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")) {

				Reporter.logEvent(Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			// Step 6
			accSetup.validateProfileSettinsPage();
			// Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			if(itr==2)
			{
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABCD");
			}
			else
			{
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABC");
			}
			
			
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
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
			// Step 8
			Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
					+ "phone number or email address listed for your account.");
			// Step 9
			Common.isLabelDisplayed("Where should we send your code?");

			if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

				Reporter.logEvent(
						Status.PASS,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
						true);
			}
			// Step 10
			String[] deliveryMethodOptions = { "Choose delivery method",
					"Text me: ***-***-9999", "Call me: ***-***-9999",
					"Email: *******@gwl.com" };
			objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
			// Step 11
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Thread.sleep(4000);
			Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
			// Step 12
			objAuth.selectCodeDeliveryOption(
					Stock.GetParameterValue("codeDeliveryOption"), true);
			String verificationCode = "";
			if (Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			// Step 13
			objAuth.submitVerificationCode(verificationCode, false, false);
			Web.clickOnElement(objAuth, "SIGN IN");
			
			
			
			if(itr==2)
			{
				
				
				String[] sqlQuery = Stock.getTestQuery(Stock
						.GetParameterValue("queryName"));

				//ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				int i=0;
				while(rs.next())
				{
					
					++i;
					
				}
				
				
				if(i==2)
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"2 plans are associated with the user", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"Plans mismatch", false);
				}
				
			}
			else
			{
				String[] sqlQuery = Stock.getTestQuery(Stock
						.GetParameterValue("queryName"));

				ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				rs.first();
				String sDbSSN = rs.getString("SSN");
				String sDbGroupId = rs.getString("GA_ID");
				String sDbEmail = rs.getString("EMAIL_ADDRESS");
				
				if(sDbSSN.equalsIgnoreCase(SSN))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"SSN is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"SSN is not matching", false);
				}
				if(sDbGroupId.equalsIgnoreCase(Stock.GetParameterValue("planNumber")))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"Plan Number is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"Plan Number is not matching", false);
				}
				if(sDbEmail.equalsIgnoreCase(Stock.GetParameterValue("EmailId")))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"EmailId is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"EmailId is not matching", false);
				}
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
	@Test(dataProvider = "setData")
	public void NPDI_009_Register_a_ppt_exists_in_Easy_DB(int itr, Map<String, String> testdata) {

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
			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("queryName"));

			ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],
					Stock.GetParameterValue("planNumber"));
			rs.first();
			
			SSN = rs.getString("SSN");
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration = new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);

			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on
			// Register
			// link
			accLookup.get();

			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3
			registration.setPasswordForPlan();
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015",
					"CLM");
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(registration, "LABEL WE FOUND YOU!");
			registration.isLabelDisplayed("We found you!");
			//Thread.sleep(5000);
			
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
	@Test(dataProvider = "setData")
	public void NPDI_010_Register_a_ppt_exists_in_Easy_DB(int itr, Map<String, String> testdata) {

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
			if(itr==1)
			{
				SSN = generateRandomSSN(9);
			}
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration = new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);

			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on
			// Register
			// link
			accLookup.get();

			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3
			registration.setPasswordForPlan();
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015",Stock.GetParameterValue("divisionName"));
			
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");

			if(itr==1)
			{
				Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
				if (Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")) {

					Reporter.logEvent(Status.PASS,
							"Verify 'Profile Settings' Page is displayed",
							"'Profile Settings' Page is displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Profile Settings' Page is displayed",
							"'Profile Settings' Page is not displayed", true);
				}
				// Step 6
				accSetup.validateProfileSettinsPage();
				// Step 7
				Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
						Stock.GetParameterValue("EmailId"));
				Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
						Stock.GetParameterValue("MOBILEPHONENUMBER"));
				
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABC");
				
				
				
				Web.setTextToTextBox("PASSWORD", accSetup,
						Stock.GetParameterValue("PASSWORD"));
				Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
						Stock.GetParameterValue("REENTERPASSWORD"));
				Reporter.logEvent(
						Status.INFO,
						"Enter  details and click on Register button.",
						"Submitted participant details and clicked on Register button",
						true);
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
				// Step 8
				Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
						+ "phone number or email address listed for your account.");
				// Step 9
				Common.isLabelDisplayed("Where should we send your code?");

				if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

					Reporter.logEvent(
							Status.PASS,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
							true);
				}
				// Step 10
				String[] deliveryMethodOptions = { "Choose delivery method",
						"Text me: ***-***-9999", "Call me: ***-***-9999",
						"Email: *******@gwl.com" };
				objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
				// Step 11
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Thread.sleep(4000);
				Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
				// Step 12
				objAuth.selectCodeDeliveryOption(
						Stock.GetParameterValue("codeDeliveryOption"), true);
				String verificationCode = "";
				if (Stock.GetParameterValue("codeDeliveryOption").trim()
						.equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				}
				// Step 13
				objAuth.submitVerificationCode(verificationCode, false, false);
				Web.clickOnElement(objAuth, "SIGN IN");
			}
			else
			{
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
				// Step 8
				Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
						+ "phone number or email address listed for your account.");
				// Step 9
				Common.isLabelDisplayed("Where should we send your code?");

				if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

					Reporter.logEvent(
							Status.PASS,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
							true);
				}
				// Step 10
				String[] deliveryMethodOptions = { "Choose delivery method",
						"Text me: ***-***-9999", "Call me: ***-***-9999",
						"Email: *******@gwl.com" };
				objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
				// Step 11
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Thread.sleep(4000);
				Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
				// Step 12
				objAuth.selectCodeDeliveryOption(
						Stock.GetParameterValue("codeDeliveryOption"), true);
				String verificationCode = "";
				if (Stock.GetParameterValue("codeDeliveryOption").trim()
						.equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				}
				// Step 13
				objAuth.submitVerificationCode(verificationCode, false, false);
				Web.clickOnElement(objAuth, "SIGN IN");
				Thread.sleep(4000);
				Web.waitForPageToLoad(Web.getDriver());
				registration.isLabelDisplayed("Link accounts");
				registration.isLabelDisplayed("We found an existing account for you. Please confirm the information below and click \"Continue\" to link your accounts.");
				
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
	@Test(dataProvider = "setData")
	public void NPDI_011_Register_participant_doesnot_exists_inEASY_system(
			int itr, Map<String, String> testdata) {

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
			SSN = generateRandomSSN(9);
			
			String[] sqlQuery = Stock.getTestQuery(Stock
					.GetParameterValue("queryGetRegisteredUser"));

			ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],Stock.GetParameterValue("planNumber"));
			rs.first();
			String sExistingUsername = rs.getString("USERNAME");
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

			AccountLookup accLookup = new AccountLookup(loginPage);
			Registration registration = new Registration(accLookup);
			AccountSetup accSetup = new AccountSetup();
			TwoStepVerification objAuth = new TwoStepVerification(accSetup);

			// Steps
			// Step 1 & 2 - Navigate to Account lookup page by clicking on
			// Register
			// link
			accLookup.get();

			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page",
					"Navigation succeeded", true);

			// Step 3
			registration.setPasswordForPlan();
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			registration.provideEmploymentInformation("12000", "12/12/2015",
					Stock.GetParameterValue("divisionName"));
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");

			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if (Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")) {

				Reporter.logEvent(Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			// Step 6
			accSetup.validateProfileSettinsPage();
			// Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			Web.setTextToTextBox("USERNAME", accSetup, sExistingUsername);
			
			
			
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			//Web.clickOnElement(accSetup, "REGISTER");

			Thread.sleep(10000);
			Common.isErrorMessageDisplayed("Username already in use.");
			Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABC");
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
			Thread.sleep(10000);
			//Web.waitForElement(objAuth, "Header Enhanced Security");
			if (Web.isWebElementDisplayed(objAuth, "Header Enhanced Security")) {
				Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
						+ "phone number or email address listed for your account.");
				// Step 9
				Common.isLabelDisplayed("Where should we send your code?");

				if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

					Reporter.logEvent(
							Status.PASS,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
							"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
							true);
				}
				// Step 10
				String[] deliveryMethodOptions = { "Choose delivery method",
						"Text me: ***-***-9999", "Call me: ***-***-9999",
						"Email: *******@gwl.com" };
				objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
				// Step 11
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
				Thread.sleep(4000);
				Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
				// Step 12
				objAuth.selectCodeDeliveryOption(
						Stock.GetParameterValue("codeDeliveryOption"), true);
				String verificationCode = "";
				if (Stock.GetParameterValue("codeDeliveryOption").trim()
						.equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				}
				// Step 13
				objAuth.submitVerificationCode(verificationCode, false, false);
				Web.clickOnElement(objAuth, "SIGN IN");
				
			} 
			// Step 8
			

			String getNewUser[] = Stock.getTestQuery(Stock
					.GetParameterValue("queryGetNewUserInformation"));

			ResultSet rsGetInfo = DB.executeQuery(getNewUser[0], getNewUser[1],SSN);
			rsGetInfo.first();
			
			String sDbSSN = rsGetInfo.getString("SSN");
			String sDbGroupId = rsGetInfo.getString("GA_ID");
			String sDbEmail = rsGetInfo.getString("EMAIL_ADDRESS");
			
			if(sDbSSN.equalsIgnoreCase(SSN))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"SSN is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"SSN is not matching", false);
			}
			if(sDbGroupId.equalsIgnoreCase(Stock.GetParameterValue("planNumber")))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"Plan Number is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"Plan Number is not matching", false);
			}
			if(sDbEmail.equalsIgnoreCase(Stock.GetParameterValue("EmailId")))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Database values",
						"EmailId is matching", false);
			}else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Database values",
						"EmailId is not matching", false);
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
	@Test(dataProvider = "setData")
	public void NPDI_013_Register_a_participant_End_to_End_flow(int itr, Map<String, String> testdata) {

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
			if(itr==1)
			{
				SSN = generateRandomSSN(9);
			}
			
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage loginPage = new LoginPage();

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

			// Step 3
			registration.setPasswordForPlan();
			
			accLookup.navigateToTab("I have a plan enrollment code");
			accLookup.registerWithPlanEnrollmentCode(
					Stock.GetParameterValue("planNumber"),
					Stock.GetParameterValue("planEnrollmentCode"));
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

			// Step 4
			registration.isLabelDisplayed("Provide personal information");
			registration.isLabelDisplayed("Employment information");
			registration.isLabelDisplayed("Provide mailing address");
			// Step 5

			registration.providePersonalInformation("ABC", "XYZ", "ABC",
					"12/12/1998", "Male", SSN, "Married");
			if(itr==2)
				registration.provideEmploymentInformation("12000", "12/12/2015");
			else
				registration.provideEmploymentInformation("12000", "12/12/2015",Stock.GetParameterValue("divisionName"));
			
			registration.provideMailingAddress("123ABC", "QASD", "Aptos",
					"California", "95001", "United States");

			Web.waitForElement(accSetup, "HEADER PROFILE SETTINGS");
			if (Web.isWebElementDisplayed(accSetup, "HEADER PROFILE SETTINGS")) {

				Reporter.logEvent(Status.PASS,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Profile Settings' Page is displayed",
						"'Profile Settings' Page is not displayed", true);
			}
			// Step 6
			accSetup.validateProfileSettinsPage();
			// Step 7
			Web.setTextToTextBox("EMAIL ADDRESS", accSetup,
					Stock.GetParameterValue("EmailId"));
			Web.setTextToTextBox("MOBILE PHONE NUMBER", accSetup,
					Stock.GetParameterValue("MOBILEPHONENUMBER"));
			if(itr==2)
			{
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABCD");
			}
			else
			{
				Web.setTextToTextBox("USERNAME", accSetup, SSN + "ABC");
			}
			
			
			Web.setTextToTextBox("PASSWORD", accSetup,
					Stock.GetParameterValue("PASSWORD"));
			Web.setTextToTextBox("RE-ENTER PASSWORD", accSetup,
					Stock.GetParameterValue("REENTERPASSWORD"));
			Reporter.logEvent(
					Status.INFO,
					"Enter  details and click on Register button.",
					"Submitted participant details and clicked on Register button",
					true);
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
			// Step 8
			Common.isTextFieldDisplayed("To confirm your identity, we will send a verification code to the "
					+ "phone number or email address listed for your account.");
			// Step 9
			Common.isLabelDisplayed("Where should we send your code?");

			if (Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD")) {

				Reporter.logEvent(
						Status.PASS,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is displayed", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'CHOOSE DELIVERY METHOD' Drop Down is displayed",
						"'CHOOSE DELIVERY METHOD' Drop Down is not displayed",
						true);
			}
			// Step 10
			String[] deliveryMethodOptions = { "Choose delivery method",
					"Text me: ***-***-9999", "Call me: ***-***-9999",
					"Email: *******@gwl.com" };
			objAuth.verifyDeliveryMethodAvailableOptions(deliveryMethodOptions);
			// Step 11
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Web.clickOnElement(objAuth, "BUTTON SEND ME A CODE");
			Thread.sleep(4000);
			Common.isErrorMessageDisplayed("Please select a delivery option from the dropdown");
			// Step 12
			objAuth.selectCodeDeliveryOption(
					Stock.GetParameterValue("codeDeliveryOption"), true);
			String verificationCode = "";
			if (Stock.GetParameterValue("codeDeliveryOption").trim()
					.equalsIgnoreCase("EMAIL")) {
				verificationCode = objAuth.getVerificationCode(false);
			}
			// Step 13
			objAuth.submitVerificationCode(verificationCode, false, false);
			Web.clickOnElement(objAuth, "SIGN IN");
			
			if(itr==2)
			{
				
				
				String[] sqlQuery = Stock.getTestQuery(Stock
						.GetParameterValue("queryName"));

				//ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				int i=0;
				while(rs.next())
				{
					
					++i;
					
				}
				
				
				if(i==2)
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"2 plans are associated with the user", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"Plans mismatch", false);
				}
				
			
				
				enroll.selectCustomizeEnroll();
				//Thread.sleep(5000);
				enroll.verifyPriorPlanContributionsPage();
				
				Web.waitForElement(enroll,"Prior Plan No");
				Web.clickOnElement(enroll,"Prior Plan No");
				Web.waitForPageToLoad(Web.getDriver());
				
				Web.waitForElement(enroll,"Prior Plan Continue");
				Web.clickOnElement(enroll,"Prior Plan Continue");
				Web.waitForPageToLoad(Web.getDriver());
				
				Deferrals defr = new Deferrals();
				defr.click_Select_Your_Contribution_Rate();
				defr.select_ContributionType("Split");
				
				
				Web.waitForElement(defr,"Button Confirm And Continue");
				Web.clickOnElement(defr,"Button Confirm And Continue"); 
				
				
				Web.waitForPageToLoad(Web.getDriver());
				/*Web.waitForElement(enroll, "Enrollment Continue");
				Web.clickOnElement(enroll,"Enrollment Continue");
				Web.waitForPageToLoad(Web.getDriver());
				
				Web.waitForElement(enroll, "My Contributions Confirm");
				Web.clickOnElement(enroll,"My Contributions Confirm");
				Web.waitForPageToLoad(Web.getDriver());*/
				
				Web.waitForElement(enroll, "Do it for me");
				Web.clickOnElement(enroll,"Do it for me");
				Web.waitForPageToLoad(Web.getDriver());
				
				
				JavascriptExecutor jse = (JavascriptExecutor)Web.getDriver();
				jse.executeScript("window.scrollBy(0,450)", "");
				Web.waitForElement(enroll, "Retirement Continue");
				Web.clickOnElement(enroll, "Retirement Continue");
				Web.waitForPageToLoad(Web.getDriver());
				
				Web.waitForElement(enroll, "Button I Agree Enroll Now");
				Web.clickOnElement(enroll,"Button I Agree Enroll Now");
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(20000);
				
				//enroll.isTextFieldDisplayed("Congratulations!");
				
				/*Web.waitForElement(enroll, "Add Beneficiaries");
				Web.clickOnElement(enroll,"Add Beneficiaries");
				Web.waitForPageToLoad(Web.getDriver());*/
				jse.executeScript("window.scrollBy(0,450)", "");
				Web.waitForElement(enroll, "View My Account Link");
				Web.clickOnElement(enroll, "View My Account Link");
				Web.waitForPageToLoad(Web.getDriver());
				
				/*Web.waitForElement(enroll, "Account Overview");
				Web.clickOnElement(enroll,"Account Overview");
				Web.waitForPageToLoad(Web.getDriver());*/
				
				Web.waitForElement(enroll,"Read Balance");
				String sBalance = enroll.getElementText("Read Balance");
				
				
				if(sBalance.equalsIgnoreCase("$0.00"))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Account overview Page",
							"Balance is matching", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Account overview Page",
							"Balance is not matching", true);
				}
				
				Web.waitForElement(enroll, "LOG OUT");
				Web.clickOnElement(enroll,"LOG OUT");
				
			}
			else
			{
				String[] sqlQuery = Stock.getTestQuery(Stock
						.GetParameterValue("queryName"));

				ResultSet rs = DB.executeQuery(sqlQuery[0], sqlQuery[1],SSN);
				rs.first();
				String sDbSSN = rs.getString("SSN");
				String sDbGroupId = rs.getString("GA_ID");
				String sDbEmail = rs.getString("EMAIL_ADDRESS");
				
				if(sDbSSN.equalsIgnoreCase(SSN))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"SSN is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"SSN is not matching", false);
				}
				if(sDbGroupId.equalsIgnoreCase(Stock.GetParameterValue("planNumber")))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"Plan Number is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"Plan Number is not matching", false);
				}
				if(sDbEmail.equalsIgnoreCase(Stock.GetParameterValue("EmailId")))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Database values",
							"EmailId is matching", false);
				}else
				{
					Reporter.logEvent(Status.FAIL,
							"Verify Database values",
							"EmailId is not matching", false);
				}
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

	@AfterSuite
	public void cleanupSessions() {
		lib.Web.getDriver().close();
		lib.Web.getDriver().quit();
	}
	public String  generateRandomSSN(int length) {
		Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}
}
