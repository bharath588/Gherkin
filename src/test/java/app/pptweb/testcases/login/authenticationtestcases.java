package app.pptweb.testcases.login;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import core.framework.Globals;

public class authenticationtestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;

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
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@Test(dataProvider = "setData")
	public void SF01_TC01_SendActivationCodeThroughLoginFlow(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, core.framework.Globals.GC_MANUAL_TC_NAME+"_"+Common.getSponser());
			boolean isDisplayed = false;
			LoginPage loginPage = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(loginPage);
			LandingPage landingPage = new LandingPage(twoStepVerification);

			//Navigate to verification code options selection page
			twoStepVerification.setPageMandatory(true);
			twoStepVerification.get();
			isDisplayed = Web.isWebElementDisplayed(twoStepVerification, "Choose delivery method");
			if (isDisplayed) {
				Reporter.logEvent(Status.INFO, "Navigate to verification code delivery option selection page", 
						"Navigation succeeded", true);
				Reporter.logEvent(Status.PASS, "Verify drop down box 'Choose delivery method' is displayed", "Drop down box is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Navigate to verification code delivery option selection page", 
						"Navigation failed", true);
				return;
			}

			//Validate drop down box option 'Text Me:' is displayed
			isDisplayed = Web.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Text Me:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Text Me:' is available", "'Text Me:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Text Me:' is available", "'Text Me:' option not available", false);
			}

			//Validate drop down box option 'Call Me:' is displayed
			isDisplayed = Web.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Call Me:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Call Me:' is available", "'Call Me:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Call Me:' is available", "'Call Me:' option not available", false);
			}

			//Validate drop down box option 'Email:' is displayed
			isDisplayed = Web.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Email:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Email:' is available", "'Email:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Email:' is available", "'Email:' option not available", false);
			}

			//Verify 'Already have a code?' link is displayed
			isDisplayed = Web.isWebElementDisplayed(twoStepVerification, "Already have a code?");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify that 'Aready have a code?' link is displayed", 
						"Link displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify that 'Aready have a code?' link is displayed", 
						"Link not displayed", true);
			}

			//Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock.GetParameterValue("codeDeliveryOption"));
			Reporter.logEvent(Status.INFO, "Select code delivery option and click continue","Done", true);

			//Get verification code from database
			String verificationCode = "";
			if(lib.Stock.GetParameterValue("codeDeliveryOption").trim().equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = twoStepVerification.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("codeDeliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = twoStepVerification.getVerificationCode(false);
				} else {
					if (twoStepVerification.isActivationCodeGenerated(lib.Stock.GetParameterValue("codeDeliveryOption"))) {
						Reporter.logEvent(Status.PASS, "Verify activation code is generated", "Activation code is successfully generated", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify activation code is generated", "Activation code is not generated", false);
					}
					return;
				}
			}

			if (verificationCode.trim().length() == 0) {
				Reporter.logEvent(Status.FAIL, "Fetch verification code", "No code generated.", false);
			} else {
				//Enter valid verification code and click on 'Sign In' button
				twoStepVerification.submitVerificationCode(verificationCode, true, false);

				//Verify user is on landing page (Check label 'Retirement income' is displayed on the page)
				isDisplayed = Web.isWebElementDisplayed(landingPage, "Log out") && Web.isWebElementDisplayed(landingPage, "Retirement income");
				if (isDisplayed) {
					Reporter.logEvent(Status.PASS, "Verify user is on landing page", "user is on landing page", true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify user is on landing page", "user is not on landing page", true);
				}
			}

		}catch(Exception e)
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
		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF04_TC01_SendActivationCode_ForgotPasswordFlow(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, core.framework.Globals.GC_MANUAL_TC_NAME+"_"+Common.getSponser());
			String actLoginHelptxt = "Enter the information below to recover your username. You will have the option to change your password.";
			String expLoginHelptxt;
			boolean isMatching;
			boolean verificationResult;
			String verificationCode;

			LoginPage objLogin = new LoginPage();
			ForgotPassword objForgotPsw = new ForgotPassword(objLogin).get();
			TwoStepVerification objAuth = new TwoStepVerification(objLogin);

			Reporter.logEvent(Status.INFO, "Navigate to forgot password page.", "forgot password page is displayed", true);

			//Step 3 - Verify following verbiage is displayed "Enter the information below to recover your username. You will have the option to change your password." 
			//		 
			//		Also,verify following fields are displayed along with the respective labels
			//		Social Security,Zip Code,Last Name,Date of Birth, and Street Address

			verificationResult = objForgotPsw.validateFieldNames();
			if (verificationResult) {
				Reporter.logEvent(Status.PASS, "Forgot Password Text fields label validation ", "text field name validation was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Forgot Password Text fields label validation ", "text field name validation was un-successful", false);
			}


			expLoginHelptxt = objForgotPsw.isLoginHelptxtDisplayed();
			isMatching = Web.VerifyText(expLoginHelptxt, actLoginHelptxt, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Forgot Password header Text Verification", "Header text verification was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Forgot Password header Text Verification", "Header text verification was un-successful actual text: " + actLoginHelptxt + "     Expected Text: "+ expLoginHelptxt, false);
			}

			//Step 4 - Enter corresponding details for following fields and click Continue button. - User is redirected to Login help (2 of 3) page

			objForgotPsw.enterForgotPasswordDetails(lib.Stock.GetParameterValue("SSN"), 
					lib.Stock.GetParameterValue("ZIPCODE"), 
					lib.Stock.GetParameterValue("LASTNAME"), 
					lib.Stock.GetParameterValue("DOB"), 
					lib.Stock.GetParameterValue("STREETADDRESS"));

			//Step 5 - Click on "Already have a code?" link
			objAuth.selectCodeDeliveryOption(lib.Stock.GetParameterValue("codeDeliveryOption"));

			//Step 6 and 7 - Enter verification code into "PLEASE ENTER VERIFICATION CODE" text box and click on "Continue" button
			if (lib.Stock.GetParameterValue("codeDeliveryOption").equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = objAuth.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("codeDeliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				} else {
					if (objAuth.isActivationCodeGenerated(lib.Stock.GetParameterValue("codeDeliveryOption"))) {
						Reporter.logEvent(Status.PASS, "Verify activation code is generated", "Activation code is successfully generated", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify activation code is generated", "Activation code is not generated", false);
					}
					return;
				}
			}

			objAuth.submitVerificationCode(verificationCode, false, false);

			//Step 8 - Click the "I need help with my password too" link and enter new password and verify if the user is successful in setting the new psw
			objForgotPsw.helpResetMyPassword(lib.Stock.GetParameterValue("PASSWORD"), lib.Stock.GetParameterValue("REENTERPASSWORD"));

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

		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF04_TC05_ForgotPassword_DidntReceiveActivationCode(int itr, Map<String, String> testdata){

		String actLoginHelptxt = "Enter the information below to recover your username. You will have the option to change your password.";
		String expLoginHelptxt;
		boolean isMatching;
		boolean eleDisplayed;
		try{
			Reporter.initializeReportForTC(itr, core.framework.Globals.GC_MANUAL_TC_NAME+"_"+Common.getSponser());
			LoginPage objLogin = new LoginPage();
			ForgotPassword objForgotPsw = new ForgotPassword(objLogin).get();
			TwoStepVerification objAuth = new TwoStepVerification(objLogin);

			Reporter.logEvent(Status.INFO, "Navigate to forgot password page.", "forgot password page is displayed", true);
			//------------------------------------------------------------------------------------------------------------------------
			//Step 3 - Verify following verbiage is displayed "Enter the information below to recover your username. You will have the option to change your password." 
			//		 
			//		Also,verify following fields are displayed along with the respective labels
			//		Social Security,Zip Code,Last Name,Date of Birth, and Street Address

			expLoginHelptxt = objForgotPsw.isLoginHelptxtDisplayed();
			isMatching = lib.Web.VerifyText(expLoginHelptxt, actLoginHelptxt, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Forgot Password header Text Verification", "Header text verification was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Forgot Password header Text Verification", "Header text verification was un-successful actual text: " + actLoginHelptxt + "     Expected Text: "+ expLoginHelptxt, false);
			}


			objForgotPsw.enterForgotPasswordDetails(Stock.GetParameterValue("SSN"), Stock.GetParameterValue("zipCode"), Stock.GetParameterValue("lastName"), Stock.GetParameterValue("DOB"), Stock.GetParameterValue("streetAddress"));

			//------------------------------------------------------------------------------------------------------------------------
			//Step 4 - Verify that below the page titile "Verify your identity" the following verbiage appears: "We need to confirm your identity to ensure your accounts are secure. 
			//We do this by sending a temporary Verification Code to one of the phone numbers or email addresses you provided us in the past."

			//need to implement (application is not navigating to this page)
			//------------------------------------------------------------------------------------------------------------------------


			//step 5 - Verify that the drop down selections has option for requesting the code via the following 3 options:text me,call me,		email 
			//already covered in an other test case.

			//------------------------------------------------------------------------------------------------------------------------
			//step 6 - There is a link for "Already have a code?" and clicking it takes user to the activation code entry page
			objAuth.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			if (lib.Web.isWebElementDisplayed(objAuth, "VERIFICATION CODE")) {
				Reporter.logEvent(Status.PASS, "Already Have a code", "User navigated to the Verification code page", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Already Have a code", "User did not navigate to the Verification code page", true);
			}

			lib.Web.clickOnElement(objAuth, "DIDN'T RECEIVE THE CODE");

			//------------------------------------------------------------------------------------------------------------------------
			//step 7 - There is a link for "Didn't receive the code?"  and clicking it takes user back to the page for requesting the code
			eleDisplayed = lib.Web.isWebElementDisplayed(objAuth, "CHOOSE DELIVERY METHOD");
			if (eleDisplayed) {
				Reporter.logEvent(Status.PASS, "Didnt receive code verification", "after clicking on didnt receive code, the user navigated to choose delivery method page", true);
			} else {
				Reporter.logEvent(Status.PASS, "Didnt receive code verification", "after clicking on didnt receive code, the user did not navigate to choose delivery method page", true);
			}
			//------------------------------------------------------------------------------------------------------------------------

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

		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}



	}


	@AfterSuite
	public void cleanupSessions() {
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();
	}
}
