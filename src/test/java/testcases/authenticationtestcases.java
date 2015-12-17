package testcases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import core.framework.Globals;
import core.utils.Reporter;
import core.utils.Stock;
import core.utils.common;
import core.utils.Reporter.Status;

public class authenticationtestcases {
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
	public void SF01_TC01_SendActivationCodeThroughLoginFlow(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC01_SendActivationCodeThroughLoginFlow");
			boolean isDisplayed = false;
			LoginPage loginPage = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(loginPage);
			LandingPage landingPage = new LandingPage(twoStepVerification);
			
			//Navigate to verification code options selection page
			twoStepVerification.setPageMandatory(true);
			twoStepVerification.get();
			isDisplayed = common.isWebElementDisplayed(twoStepVerification, "Choose delivery method");
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
			isDisplayed = common.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Text Me:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Text Me:' is available", "'Text Me:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Text Me:' is available", "'Text Me:' option not available", false);
			}
			
			//Validate drop down box option 'Call Me:' is displayed
			isDisplayed = common.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Call Me:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Call Me:' is available", "'Call Me:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Call Me:' is available", "'Call Me:' option not available", false);
			}
			
			//Validate drop down box option 'Email:' is displayed
			isDisplayed = common.verifyDropDownOptionExists(twoStepVerification, "Choose delivery method", "Email:");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify drop down box option 'Email:' is available", "'Email:' option available", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify drop down box option 'Email:' is available", "'Email:' option not available", false);
			}
			
			//Verify 'Already have a code?' link is displayed
			isDisplayed = common.isWebElementDisplayed(twoStepVerification, "Already have a code?");
			if (isDisplayed) {
				Reporter.logEvent(Status.PASS, "Verify that 'Aready have a code?' link is displayed", 
						"Link displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify that 'Aready have a code?' link is displayed", 
						"Link not displayed", true);
			}
			
			//Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(testdata.get("codeDeliveryOption"));
//			Reporter.logEvent(Status.INFO, "Select code delivery option and click continue", "Done", true);
			Reporter.logEvent(Status.INFO, "Select code delivery option and click continue", 
					"Done", true);
			
			//Get verification code from database
			String verificationCode = "";
			if(testdata.get("codeDeliveryOption").trim().equalsIgnoreCase("Already_Have_Code")) {
				verificationCode = twoStepVerification.getVerificationCode(true);
			} else {
				if (testdata.get("codeDeliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = twoStepVerification.getVerificationCode(false);
				} else {
					if (twoStepVerification.isActivationCodeGenerated(testdata.get("codeDeliveryOption"))) {
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
				isDisplayed = common.isWebElementDisplayed(landingPage, "Log out") && common.isWebElementDisplayed(landingPage, "Retirement income");
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
	public void SF04_TC01_SendActivationCode_ForgotPasswordFlow(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, "SF04_TC01_SendActivationCode_ForgotPasswordFlow");
			String actLoginHelptxt = "Enter the information below to recover your username. You will have the option to change your password.";
			String expLoginHelptxt;
			boolean isMatching;
			boolean verificationResult;
			String verificationCode;
			
			LoginPage objLogin = new LoginPage();
			ForgotPassword objForgotPsw = new ForgotPassword(objLogin).get();
			TwoStepVerification objAuth = new TwoStepVerification(objLogin);
			//LandingPage objland = new LandingPage(objAuth);	
			
			
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
			isMatching = common.VerifyText(expLoginHelptxt, actLoginHelptxt, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Forgot Password header Text Verification", "Header text verification was successful", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Forgot Password header Text Verification", "Header text verification was un-successful actual text: " + actLoginHelptxt + "     Expected Text: "+ expLoginHelptxt, false);
			}
			
			//Step 4 - Enter corresponding details for following fields and click Continue button. - User is redirected to Login help (2 of 3) page
			
			objForgotPsw.enterForgotPasswordDetails(testdata.get("SSN"), 
					testdata.get("zipCode"), 
					testdata.get("LastName"), 
					testdata.get("DOB"), 
					testdata.get("StreetAddress"));
				
			//Step 5 - Click on "Already have a code?" link
			objAuth.selectCodeDeliveryOption(testdata.get("codeDeliveryOption"));
			
			//Step 6 and 7 - Enter verification code into "PLEASE ENTER VERIFICATION CODE" text box and click on "Continue" button
			if (testdata.get("codeDeliveryOption").equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = objAuth.getVerificationCode(true);
			} else {
				if (testdata.get("codeDeliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = objAuth.getVerificationCode(false);
				} else {
					if (objAuth.isActivationCodeGenerated(testdata.get("codeDeliveryOption"))) {
						Reporter.logEvent(Status.PASS, "Verify activation code is generated", "Activation code is successfully generated", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify activation code is generated", "Activation code is not generated", false);
					}
					return;
				}
			}
			
			objAuth.submitVerificationCode(verificationCode, false, false);
			
			//Step 8 - Click the "I need help with my password too" link and enter new password and verify if the user is successful in setting the new psw
			objForgotPsw.helpResetMyPassword(testdata.get("password"), testdata.get("reEnterPassword"));
			
			//verify if the user is on the landing page
			/*try {
				WebActions.waitForElement(objland, "HOME");
			} catch (Exception e) {
				Reporter.logEvent(Status.FAIL, "wait for home page", "page timed out befor entering the home page", false);
				e.printStackTrace();
			}
			
			if (WebActions.isWebElementDisplayed(objland, "HOME")) {
				Reporter.logEvent(Status.PASS, "Navigate to home page", "User was successful in navigation to the home page", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Navigate to home page", "User was UN-successful in navigation to the home page", true);
			}*/
			
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
