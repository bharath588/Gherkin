package app.pptweb.testcases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import app.pptweb.common.Reporter;
import app.pptweb.common.common;
import app.pptweb.common.Reporter.Status;
import app.pptweb.pageobjects.login.LoginPage;
import app.pptweb.pageobjects.userregistration.AccountLookup;
import app.pptweb.pageobjects.userregistration.AccountSetup;
import core.framework.Globals;

import core.utils.Stock;


public class registrationtestcases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		common.webdriver = common.getWebDriver(Stock.globalParam.get("BROWSER"));
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getName(), testCase.getName());
	}
	
	@BeforeMethod
    public void getTCName(Method tc) {
           tcName = tc.getName();       
    }
	
	
	@Test(dataProvider = "setData")
	public void SF01_TC01_User_has_PIN(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC01_User_has_PIN");
			String headerText;
			String activeTab;
			String actualValue;
			String actualErrMsg, expectedErrMsg;
			
			LoginPage loginPage = new LoginPage();
			AccountLookup accLookup = new AccountLookup(loginPage);
			
			//Steps
			accLookup.get();
			
			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.", "Account lookup page is displayed", true);
			
			//Step 3 - Verify verbiage on the top of the page
			headerText = accLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL, "Verify Account Lookup Header block text", "Header text block is not displayed on the page", false);
			} else {
				common.VerifyText("Let’s look up your account\nEnter the information below to set up your account.", headerText, true);
			}
			
			//Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = accLookup.getActiveTabName();
			if (activeTab.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(Status.PASS, "Verify default tab opened is 'I do not have a PIN'", "Tab 'I do not have a PIN' is open by default", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify default tab opened is 'I do not have a PIN", "Tab 'I do not have a PIN' is not open by default", false);
			}
			
			//Navigate to tab 'I have a PIN'
			if (accLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS, "Navigate to \"I have a PIN\" tab", "Navigation successful", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Navigate to \"I have a PIN\" tab", "Navigation failed", true);
			}
			
			//Step 5 - Verify that SSN and PIN text boxes are displayed
			if (accLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN field verification", "SSN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN field verification", "SSN field is NOT visible", false);	
			}
			
			//Verify PIN text box is displayed
			if (accLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification", "PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification", "PIN field is NOT visible", false);	
			}
			
			//Step 6 - Enter Alpha-numeric SSN and move the cursor out
			accLookup.setTextToFields("Social Security Number", "ab12CD34e");
			accLookup.clickOnFields("PIN");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,"Social Security Number must be numeric.",true)) {
					Reporter.logEvent(Status.PASS, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 7 - Enter SSN less than 9 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "1252");
			accLookup.clickOnFields("PIN");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 8 - Enter SSN more than 9 digits
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			actualValue = accLookup.setTextToFields("Social Security Number", "12345678956");
			if (actualValue.trim().equals("123456789")) {
				Reporter.logEvent(Status.PASS, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: " + actualValue, false);
			} else if (actualValue.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: " + actualValue, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted", false);
			}
			
			//Step 9 - Enter Alpha-numeric PIN and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "ab12");
			accLookup.clickOnFields("Social Security Number");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be numeric";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message 'PIN must be numeric.' is displayed", 
						"No error message displayed for PIN.", false);
			} else {
				//Common.VerifyText(actualErrMsg,"PIN must be numeric",true);
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 10 - Enter PIN less than 4 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "12");
			accLookup.clickOnFields("Social Security Number");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be four digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for PIN number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 11 - Enter PIN more than 4 digits
			actualValue = accLookup.setTextToFields("PIN", "123456");
			if (actualValue.trim().equals("1234")) {
				Reporter.logEvent(Status.PASS, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: " + actualValue, false);
			} else if (actualValue.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: " + actualValue, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: Unable to set/No Value accepted", false);
			}
			
			//Step 12 - Leave SSN blank and enter valid PIN and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "1234");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 13 - Enter valid SSN and leave PIN blank and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "123456789");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for PIN number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			
			//Step 14 - Leave both SSN and PIN blank and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (common.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
		
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
}
	}
	
	
	@Test(dataProvider = "setData")
	public void SF01_TC02_User_does_not_have_PIN(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC02_User_does_not_have_PIN");
			boolean blnIsElePresent;
			String txtActErrMsg;
			String headerText;
			String activeTab;
			String expectedErrMsg;
			//String txtDateFormate;

			LoginPage objloginPage = new LoginPage();
			AccountLookup objAccountLookup = new AccountLookup(objloginPage).get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.","Account lookup page is displayed", true);

			// Step 3 - Verify verbiage on the top of the page
			headerText = objAccountLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account Lookup Header block text","Header text block is not displayed on the page", false);
			} else {
				common.VerifyText("Let’s look up your account\nEnter the information below to set up your account.",headerText, false);
			}

			// Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = objAccountLookup.getActiveTabName();
			if (activeTab.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(Status.PASS,"Verify default tab opened is 'I do not have a PIN'",
						"Tab 'I do not have a PIN' is open by default", false);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify default tab opened is 'I do not have a PIN",
						"Tab 'I do not have a PIN' is not open by default", false);
			}

			// Step 5 - Verify that the following fields are shown along with text
			// entry boxes for values:
			// Social Security number (defaults to placeholder text that displays as
			// (___-__-____)
			// Zip Code
			// Last Name
			// Date of Birth (defaults to show greyed out format of MM DD YYYY)
			// Street Address

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Social Security Number");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'","Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'","Social Security Number field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Zip Code");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Zip Code Field Displayed'","Zip Code field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Zip Code Field Displayed'","Zip Code field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Last Name");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Last Name Field Displayed'","Last Name field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Last Name Field Displayed'","Last Name field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Date of Birth");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Date of Birth Field Displayed'","Date of Birth field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Date of Birth Field Displayed'","Date of Birth field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Street Address");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Street Address Field Displayed'","Street Address field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Street Address Field Displayed'","Street Address field is NOT Displayed", false);
			}

			// Step 7 - In the social security field, enter alphanumeric characters
			// and move the cursor out of the field.

			objAccountLookup.setTextToFields("Social Security Number", "ab12CD34e");
			//objAccountLookup.setTextToFields("Social Security Number", Keys.TAB);
			objAccountLookup.clickOnFields("ZIP Code");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Social Security Number");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"Verify error message 'Social Security Number must be numeric.' is displayed",
						"No error message displayed for Social Security number.",false);
			} else {
				if (common.VerifyText(txtActErrMsg,"Social Security Number must be numeric.",true)) {
					Reporter.logEvent(Status.PASS, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}
			
			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 8 - Enter SSN less than 9 digits and move the cursor out
			objAccountLookup.setTextToFields("Social Security Number", "1252");
			objAccountLookup.clickOnFields("Zip Code");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '"+ expectedErrMsg + "' is displayed",
						"No error message displayed for Social Security number.",false);
			} else {
				if (common.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 9 - Enter SSN more than 9 digits
			txtActErrMsg = objAccountLookup.setTextToFields("Social Security Number", "12345678956");
			if (txtActErrMsg.trim().equals("123456789")) {
				Reporter.logEvent(Status.PASS,"Verify SSN field accepts only 9 digits","Value set: 12345678956\nValue accepted: " + txtActErrMsg,
						false);
			} else if (txtActErrMsg.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL,"Verify SSN field accepts only 9 digits","Value set: 12345678956\nValue accepted: " + txtActErrMsg,
						false);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted",false);
			}
			
			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 10 - In the zip code field, enter alphanumeric characters and
			// move the cursor out of the field..

			objAccountLookup.setTextToFields("ZIP CODE", "abc23");
			objAccountLookup.clickOnFields("Last Name");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("ZIP CODE");
			expectedErrMsg = "Please enter valid ZIP Code.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"ZIP CODE error Verification for alpha numberic char",
						"Error message 'Please enter valid ZIP Code.' is not displayed",false);
			} else {
				if (common.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}
			
			/*objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// step 11 and 12 are UI validations that cannot be done
			// Step 13 - Verfiy that the placeholder text for the three boxes in the
			// Date of Birth field should be "__/__/____"
			objAccountLookup.setTextToFields("DATE OF BIRTH","01221947");
			objAccountLookup.clickOnFields("Street Address");
			txtDateFormate = WebActions.getWebElementAttribute(objAccountLookup, "DATE OF BIRTH", "value");
			if (txtDateFormate.equalsIgnoreCase("01/22/1947")) {
				Reporter.logEvent(Status.PASS,"Date of Birth type validation",
						"After entering Date of Birth value the entered value correctly is formated into __/__/____",false);
			} else {
				Reporter.logEvent(Status.FAIL,"Date of Birth type validation",
						"After entering Date of Birth value the entered value is not formated into __/__/____",true);
			}*/
			
			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 14 - Verify that only numeric characters are allowed to be
			// entered in the Date of Birth text entry box and no alpha character
			// should be allowed in the fields.

			objAccountLookup.setTextToFields("Date of Birth", "123123aa");
			objAccountLookup.clickOnFields("Street Address");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Date of Birth");
			expectedErrMsg = "Date of Birth must be numeric.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"Date of Birth error Verification for alpha numberic char",
						"Verify error message 'Date of Birth must be numeric.' is displayed",false);
			} else {
				if (common.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}
			
			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// step 15 cannot be automated
			// Step 16 -Verify text entry box for the Street Address and that the
			// Street Address field allows for text entry of just the house number
			objAccountLookup.setTextToFields("Street Address", "23");
			objAccountLookup.clickOnFields("Social Security Number");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.PASS,"Street Address error Verification",
						"No error was displayed for Street address for entering only house number",false);
			} else {
				Reporter.logEvent(Status.FAIL,"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number\nActual Message: " + txtActErrMsg,false);
			}
			
			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 16 -Verify text entry box for the Street Address and that the
			// Street Address field allows for text entry of just the house number
			// and Street name
			objAccountLookup.setTextToFields("Street Address", "23 BOURKE STREET");
			objAccountLookup.clickOnFields("Social Security Number");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.PASS,"Street Address error Verification",
						"No error was displayed for Street address for entering house number and street name",false);
			} else {
				Reporter.logEvent(Status.FAIL,"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number and street name\nActual Message: " + txtActErrMsg,false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the page
			if (objAccountLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS,"Verify tab opened is 'I have a PIN'","The user navigated to 'I have a PIN' tab", true);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify tab opened is 'I do not have a PIN"," The user did not navigate to 'I have a PIN'", false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the page and
			// verify if the SSN and PIN field is displayed
			if (objAccountLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'","Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'","Social Security Number field is NOT Displayed", false);
			}
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification","PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification","PIN field is NOT visible", false);
			}

			// Step 18 - Click on the "I do not have a PIN" - The user is taken back
			// to registering without a pin
			objAccountLookup.navigateToTab("I do not have a PIN");
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.FAIL, "PIN field verification","PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.PASS, "PIN field verification","PIN field is NOT visible", false);
			}
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
}
	}
	
	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithAndWithoutPINTabs(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC03_NegativeFlow_WithAndWithoutPINTabs");
			String ActErrMessage;
			LoginPage loginPage = new LoginPage();
			AccountLookup accLookup = new AccountLookup(loginPage);
			
			//Steps
			//Step 2 - Navigate to Account lookup page by clicking on Register link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page", "Navigation succeeded", true);
			
			//Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(testdata.get("TabName"));
			
			//Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim().equalsIgnoreCase(testdata.get("TabName"))) {
				Reporter.logEvent(Status.PASS, "Verify user is on \"" + testdata.get("TabName") + "\" tab", 
						"User is on \"" + testdata.get("TabName") + "\" tab", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify user is on \"" + testdata.get("TabName") + "\" tab", 
						"User is not on \"" + testdata.get("TabName") + "\" tab", true);
			}
			
			//Step 5 - Enter a valid participant details and click continue
			if (testdata.get("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("SSN", testdata.get("SSN"));
				//Enter PIN
				accLookup.setTextToFields("PIN", testdata.get("PIN"));
				
			} else if (testdata.get("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("Social Security Number", testdata.get("SSN"));
				//Enter Zip Code
				accLookup.setTextToFields("Zip Code", testdata.get("ZipCode"));
				//Enter Last Name
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println(testdata.get("LastName"));
				accLookup.setTextToFields("Last Name", testdata.get("LastName"));
				//Enter Date of Birth
				accLookup.setTextToFields("Date of Birth", testdata.get("DOB"));
				//Enter Street Address
				accLookup.setTextToFields("Street Address", testdata.get("StreetAddress"));
			}
			
			//Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(Status.INFO, "Enter participant details and click on Continue button.", 
					"Submitted participant details and clicked on Continue button", true);
			
			//Verify error message displayed
			ActErrMessage = accLookup.getMainErrorMsg();
			if (ActErrMessage.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message displayed", "No error message is displayed on the page", true);
			} else {
				if (common.VerifyText(testdata.get("ExpectedErrorMsg"), ActErrMessage, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message after submitting invalid details", "Expected error message is displayed.\nExpected: " + testdata.get("ExpectedErrorMsg"), true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message after submitting invalid details", 
							"Expected error message is not displayed.\nExpected: " + testdata.get("ExpectedErrorMsg") + "\n"
							+ ActErrMessage, true);
				}
			}
			
			//Switch to other tab - to help continuation for other iterations if any
			if (testdata.get("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				accLookup.navigateToTab("I do not have a PIN");
			} else {
				accLookup.navigateToTab("I have a PIN");
			}
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
}
	
	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithAndWithoutPIN(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC05_UserLock_WithAndWithoutPIN");
			String activeTab;
			String actErrMsg;
			boolean blnTempVar;
			String expNoPinUsrLckmsgOne = "Error: The entries you provided do not match our records. Please try again. You have 2 attempt(s) remaining.";
			String expNoPinUsrLockmsgTwo = "Error: The entries you provided do not match our records. Please try again. You have 1 attempt(s) remaining.";
			String expNoPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";
			String expWithPinUsrLckmsgOne = "The passcode you entered is invalid. Please re-enter your passcode. You have 2 attempt(s) left.";
			String expWithPinUsrLckmsgTwo = "The passcode you entered is invalid. Please re-enter your passcode. You have 1 attempt(s) left.";
			String expWithPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";
			
			LoginPage objloginPage = new LoginPage();
			AccountLookup objAccountLookup = new AccountLookup(objloginPage).get();		
			
			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.", "Account lookup page is displayed", false);
			
			objAccountLookup.clickOnFields(testdata.get("TabName"));
			
			
					
					
					
			if (testdata.get("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				
				//Verify if the user is on the 'I have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
		        if (activeTab.trim().equalsIgnoreCase("I have a PIN")) {
		            Reporter.logEvent(Status.PASS, "Verify Verification", "The user navigated to 'I have a PIN' tab", false);
		        } else {
		            Reporter.logEvent(Status.FAIL, "Verify Verification", " The user did not navigate to 'I have a PIN'", false);
		        }
				
		        //verify the first error message that the user gets on entering invalid pin
				objAccountLookup.registrationWithPIN(testdata.get("SSN"), "4231");
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = common.VerifyText(actErrMsg,expWithPinUsrLckmsgOne,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
					
				}
				
				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");
				
				//verify the second error message that the user gets on entering invalid pin
				objAccountLookup.registrationWithPIN(testdata.get("SSN"), "4231");
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = common.VerifyText(actErrMsg,expWithPinUsrLckmsgTwo,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
				}
				
				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");
				
				//verify the final error message that the user gets on entering invalid pin,where the user is locked
				objAccountLookup.registrationWithPIN(testdata.get("SSN"), "4231");
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify user Lock Message'" + actErrMsg , 
							"user profile lock msg has not been displayed. Please check your data", false);
				} else {
					if (actErrMsg.trim().toUpperCase().startsWith(expWithPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(Status.PASS, "Verify user Lock Message'" + actErrMsg , 
								"User has locked his profile sucessfully after 3 unsuccessful tries.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify user Lock Message'" + actErrMsg , 
								"User profile lock message after 3 unsuccessful login attempts was not verified correctly", true);
					}
				}
				
					
			} else if (testdata.get("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {
				
				//Verify if the user is on the 'I do not have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
		        if (activeTab.trim().equalsIgnoreCase("I do not have a PIN")) {
		            Reporter.logEvent(Status.PASS, "Verify Verification", "The user navigated to 'I do not have a PIN' tab", false);
		        } else {
		            Reporter.logEvent(Status.FAIL, "Verify Verification", " The user did not navigate to 'I do not have a PIN'", false);
		        }
		        
		      //verify the first error message that the user gets on entering invalid pin
				objAccountLookup.registerWithoutPIN(testdata.get("SSN"), 
						testdata.get("ZipCode"),  
						testdata.get("LastName"), 
						testdata.get("DOB"), 
						testdata.get("StreetAddress"));
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = common.VerifyText(actErrMsg,expNoPinUsrLckmsgOne,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
					
				}
				
				//avoid staleElementException
				
				objAccountLookup.clickOnFields("I have a PIN");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//Do nothing
				}
				objAccountLookup.clickOnFields("I do not have a PIN");
				
				//verify the second error message that the user gets on entering invalid pin
				objAccountLookup.registerWithoutPIN(testdata.get("SSN"), 
						testdata.get("ZipCode"),  
						testdata.get("LastName"), 
						testdata.get("DOB"), 
						testdata.get("StreetAddress"));
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = common.VerifyText(actErrMsg,expNoPinUsrLockmsgTwo,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
				}
				
				objAccountLookup.clickOnFields("I have a PIN");
				objAccountLookup.clickOnFields("I do not have a PIN");
				
				//verify the final error message that the user gets on entering invalid pin,where the user is locked
				objAccountLookup.registerWithoutPIN(testdata.get("SSN"), 
						testdata.get("ZipCode"),  
						testdata.get("LastName"), 
						testdata.get("DOB"), 
						testdata.get("StreetAddress"));
				
				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock down'" + actErrMsg , 
							"user profile lock msg has not been displayed. Please check your data", false);
				} else {
					if (actErrMsg.trim().toUpperCase().startsWith(expNoPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock down'" + actErrMsg , 
								"User has locked his profile sucessfully after 3 unsuccessful tries.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock down'" + actErrMsg , 
								"User has NOT locked his profile sucessfully after 3 unsuccessful tries.", false);
					}
				}
			}
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void SF03_TC01_AccountSetup(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF03_TC01_AccountSetup");
			LoginPage objloginPage = new LoginPage();
			AccountLookup objAccountLookup = new AccountLookup(objloginPage);
			AccountSetup objAccountSetup = new AccountSetup(objAccountLookup).get();
			
			if (testdata.get("FunctionalityType").trim().equalsIgnoreCase("contactinformation")) {
				objAccountSetup.validateContactInformationUI(testdata.get("SSN"));
			} else {
				objAccountSetup.validateUserNameAndPasswordUI();
			}
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void SF04_TC01_AccountLookup_PositiveFlow(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF04_TC01_AccountLookup_PositiveFlow");
			String hdrBlockText;
			LoginPage loginPage = new LoginPage();
			AccountLookup accLookup = new AccountLookup(loginPage);
			AccountSetup accSetup = new AccountSetup(accLookup);
			
			//Steps
			//Step 2 - Navigate to Account lookup page by clicking on Register link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page", "Navigation succeeded", true);
			
			//Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(testdata.get("TabName"));
			
			//Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim().equalsIgnoreCase(testdata.get("TabName"))) {
				Reporter.logEvent(Status.PASS, "Verify user is on \"" + testdata.get("TabName") + "\" tab", 
						"User is on \"" + testdata.get("TabName") + "\" tab", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify user is on \"" + testdata.get("TabName") + "\" tab", 
						"User is not on \"" + testdata.get("TabName") + "\" tab", true);
			}
			
			//Step 5 - Enter a valid participant details and click continue
			if (testdata.get("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("SSN", testdata.get("SSN"));
				//Enter PIN
				accLookup.setTextToFields("PIN", testdata.get("PIN"));
				
			} else if (testdata.get("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("Social Security Number", testdata.get("SSN"));
				//Enter Zip Code
				accLookup.setTextToFields("Zip Code", testdata.get("ZipCode"));
				//Enter Last Name
				accLookup.setTextToFields("Last Name", testdata.get("LastName"));
				//Enter Date of Birth
				accLookup.setTextToFields("Date of Birth", testdata.get("DOB"));
				//Enter Street Address
				accLookup.setTextToFields("Street Address", testdata.get("StreetAddress"));
			}
			
			//Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(Status.INFO, "Enter participant details and click on Continue button.", 
					"Submitted participant details and clicked on Continue button", false);
			
			//Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			if (hdrBlockText == null) {
				Reporter.logEvent(Status.FAIL, "Verify Account setup Header block text", "Header text block is not displayed on the page", true);
			} else {
				common.VerifyText("We found you!\nTo continue, provide your contact information and create a username and password.", hdrBlockText, true);
				Reporter.logEvent(Status.PASS, "Navigate to Account setup page", "user successfully navigated to the Account Setup page", true);
			}
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	
	
	@AfterClass
	public void cleanupSessions() {
		common.webdriver.close();
		common.webdriver.quit();
	}
}
