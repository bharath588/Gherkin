package app.pptweb.testcases.sanity;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.deferrals.Deferrals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.HealthCareCosts;
import pageobjects.liat.HowDoICompare;
import pageobjects.liat.RetirementIncome;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.userregistration.AccountLookup;
import pageobjects.userregistration.AccountSetup;
import core.framework.Globals;

public class sanityTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;

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
	@Test(dataProvider = "setData")
	public void SF01_TC02_Verify_login_Successfully_into_unregistered_Device(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, core.framework.Globals.GC_MANUAL_TC_NAME+"_"+Common.getSponser());
			String verificationCode = "";
			
			TwoStepVerification twoStepVerification = new TwoStepVerification(new LoginPage());
			twoStepVerification.get();
			
			twoStepVerification.setPageMandatory(true);	//Two step verification page is expected to load
			twoStepVerification.get();
			
			Reporter.logEvent(Status.PASS, "Navigate to 'Two step verification (2 of 3)' page", 
					"Navigation succeeded", true);
			
			// TODO Add code to verify text displayed on Two step verification page
			
			// Verify options 'Text me', 'Call me', 'Email Me' and 'Already have a code?' exists
			Web.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "TEXT ME:");
			Web.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "CALL ME:");
			Web.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "EMAIL:");
			
			if (Web.isWebElementDisplayed(twoStepVerification, "Already have a code?")) {
				Reporter.logEvent(Status.PASS, "Verify 'Already have a code?' link is displayed", 
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify 'Already have a code?' link is displayed", 
						"Link is not displayed", false);
			}
			
			//Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock.GetParameterValue("deliveryOption"));
			
			//Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim().equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = twoStepVerification.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("deliveryOption").trim().equalsIgnoreCase("EMAIL")) {
					verificationCode = twoStepVerification.getVerificationCode(false);
				} else {
					if (twoStepVerification.isActivationCodeGenerated(lib.Stock.GetParameterValue("deliveryOption"))) {
						Reporter.logEvent(Status.PASS, "Verify activation code is generated", "Activation code is successfully generated", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify activation code is generated", "Activation code is not generated", false);
					}
					return;
				}
			}
			
			if (verificationCode.trim().length() == 0) {
				Reporter.logEvent(Status.FAIL, "Fetch verification code.", "Verification code not generated", false);
				return;
			}
			
			//Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true, 
					Boolean.parseBoolean(lib.Stock.GetParameterValue("rememberDevice")));
			
			//Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage(twoStepVerification);
			//landingPage.dismissPopUps(true, true);
			
			//Verify if landing page is displayed - Landing page is loaded if Logout link is displayed.
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS, "Verify landing page is displayed", 
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify landing page is displayed", 
						"Landing page is not displayed", true);
			}
			
			//Logout if opted
			landingPage.logout(true);
					
		}
		catch(Exception e)
	    {
	        e.printStackTrace();
	        Globals.exception = e;
	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
	    }catch(AssertionError ae)
	    {
	        ae.printStackTrace();
	        Globals.assertionerror = ae;
	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
	    }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
		}
		
	@Test(dataProvider = "setData")
	public void SF04_TC01_AccountLookup_PositiveFlow(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser());
			String hdrBlockText;
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
					false);

			// Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			if (hdrBlockText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account setup Header block text",
						"Header text block is not displayed on the page", true);
			} else {
				Web.VerifyText(
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
	public void RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify if we are able to navigate to Plan Savings Tab
			if (retirement.isFieldDisplayed("Plan Savings"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Plan Savings tab",
						"Plan Savings tab is displayed and able to click on it",
						false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Savings tab",
						"Plan Savings tab is not displayed", true);
        Web.clickOnElement(retirement,"Plan Savings");
			// verify if Contribution rate slider is present
			if (retirement.verifyIfSliderPresent("Contribution rate slider"))
				Reporter.logEvent(Status.PASS,
						"Verify Contribution rate slider",
						"Contribution rate slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Contribution rate slider",
						"Contribution rate slider not displayed", true);
			// verify if Retirement age slider is present
			if (retirement.verifyIfSliderPresent("Retirement age slider"))
				Reporter.logEvent(Status.PASS, "Verify Retirement age slider",
						"Retirement age slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Retirement age slider",
						"Retirement age slider not displayed", true);

			// verify if Investment mix slider is present
			/*if (retirement.verifyIfSliderPresent("Investment mix slider"))
				Reporter.logEvent(Status.PASS, "Verify Investment mix slider",
						"Investment mix slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Investment mix slider",
						"Investment mix slider not displayed", true);*/

			if (lib.Stock.GetParameterValue("Do It Myself").trim()
					.equalsIgnoreCase("Y")) {
				Web.clickOnElement(retirement, "Do It Myself");
				if (retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", true);
			} else {
				if (!retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider displayed", true);
			}
			// verify if we are able to navigate to Help Me Do It Tab
			if (lib.Stock.GetParameterValue("Help Me Do It").trim()
					.equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Able to navigate to Help Me Do It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Not Able to navigate to Help Me Do It tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Help Me DO it tab is not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Help Me Do It tab is displayed", true);
			}

			// verify if we are able to navigate to Do It For Me Tab
			if (lib.Stock.GetParameterValue("Do It For Me").trim()
					.equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Able to navigate to Do It For Me It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Not Able to navigate to Do It For Me tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Do It For Me tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Do It For Me tab is displayed", true);
			}

			// verify if we are able to navigate to Do It Myself Tab
			if (lib.Stock.GetParameterValue("Do It Myself").trim()
					.equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Able to navigate to Do It Myself tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Not Able to navigate to Do It Myself tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Do It Myself tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Do It Myself tab is displayed", true);
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
	public void RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Social Security Tab");

				// Verify if Social Security Tab is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Social Security Administration"))
					Reporter.logEvent(Status.PASS,
							"Verify if Social Security Page is Loaded",
							"Social Security page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Social Security Page is Loaded",
							"Social Security page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
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
	public void RIP_TC006_To_Verify_RetirementIncomeTab_Other_Assets(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Other Assets Tab");

				// Verifying if Other Assets page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Add an Account"))
					Reporter.logEvent(Status.PASS,
							"Verify if Other Assets Page is Loaded",
							"Other Assets page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Other AssetsPage is Loaded",
							"Other Assets page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
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
	public void RIP_TC007_To_Verify_RetirementIncomeTab_Income_Gap(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verify if Retirement Income Page is loaded
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Income Gap Tab");

				// Verify if Income Gap Page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Catch up Contributions"))
					Reporter.logEvent(Status.PASS,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);

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
	public void RIP_TC008_To_verify_Retirement_Income_tab_percent_of_my_goal_section(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtMonthlyIncome = retirement
					.verifyPercentOfMyGoalSection("Monthly Income");
			if (expectedtxtMonthlyIncome
					.contains("My goal for monthly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for monthly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for monthly retirement income ",
						"Percent of my goal section not displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtYearlyIncome = retirement
					.verifyPercentOfMyGoalSection("Yearly Income");
			if (expectedtxtYearlyIncome
					.contains("My goal for yearly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section not displayed", true);
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
	public void HCC_TC009_To_verify_Health_care_costs(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			float projectedIncome;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();

			projectedIncome = retirementIncome.getProjectedIncome();
			Web.clickOnElement(healthCareCost, "Health-care costs");
			healthCareCost.verifyAttainedAgeSlide();
			healthCareCost.verifyPieChart();
			healthCareCost.verifyHealthCostFromUI(projectedIncome);
			healthCareCost.verifyPersonalizeBtn();
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
	public void HDIC_TC010_To_verify_How_do_I_compare_tab(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare hdic = new HowDoICompare(homePage);
			hdic.get();
			hdic.verifyViewDetails();
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
	public void Beneficiary_TC001_Married_with_Spouse_One_beneficiary_new_address_Sanity(int itr, Map<String, String> testdata){
	
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
			//			leftmenu = new LeftNavigationBar(homePage);			
			//		else {
			//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			//			leftmenu = new LeftNavigationBar(accountPage);
			//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();


			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);

			//			// add a beneficiary
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//verify beneficiary name

			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name bot matching", true);

			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation bot matching", true);

			//verify beneficiary Relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship bot matching", true);

			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN bot matching", true);

			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB bot matching", true);

			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address bot matching", true);
			}

//			beneficiary.verifyBeneficiaryDetailsFromDB(Stock.GetParameterValue("Participant ssn"));


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
			//delete beneficiary from Database
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			try {
				beneficiary.deleteBeneficiariesFromDB(Stock.GetParameterValue("Participant ssn"), Stock.GetParameterValue("Participant first name")+"%");
				
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
	public void UnMarried_Multiple_Individual_beneficiary(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		

		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);


			//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
			//			leftmenu = new LeftNavigationBar(homePage);			
			//		else {
			//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			//			leftmenu = new LeftNavigationBar(accountPage);
			//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();


			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);

			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			//verify beneficiary name
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name bot matching", true);

			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation bot matching", true);

			//verify beneficiary relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship bot matching", true);

			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN bot matching", true);

			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB bot matching", true);

			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address bot matching", true);
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
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(Stock.GetParameterValue("Participant ssn"), Stock.GetParameterValue("Participant first name")+"%");
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
	/**
	 * The following script After Tax Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_001_After-tax_Select
	 * another contribution rate_Sanity
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__AfterTax(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			if (deferrals.click_Select_Your_Contribution_Rate())
				Reporter.logEvent(Status.PASS,
						"Verify accuracy of My Contribution Rate",
						"My Contribution Rate value is matching", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify accuracy of My Contribution Rate",
						"My Contribution Rate value is not matching", true);
			lib.Web.clickOnElement(deferrals, "Continue button");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.myContributions_Confirmation_Page();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
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
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_012_Regular_BEFORE-Select Another contribution
	 * rate_Sanity 2.SIT_PPTWEB_Deferral_016_Regular_SPLIT- Select Another
	 * contributiont rate_Sanity
	 * 3.SIT_PPTWEB_Deferral_010_Regular_BEFORE-Maximize to the company match
	 * 4.SIT_PPTWEB_Deferral_011_Regular_BEFORE-Maximize to the IRS limit
	 * 5.SIT_PPTWEB_Deferral_013_Regular_ROTH-Maximize to the company match
	 * 6.SIT_PPTWEB_Deferral_014_Regular_ROTH-Maximize to the IRS limit
	 * 7.SIT_PPTWEB_Deferral_015_Regular_ROTH-Select Another contribution rate
	 * 8.SIT_PPTWEB_Deferral_017_Regular_SPLIT-Maximize to the company match
	 * 9.SIT_PPTWEB_Deferral_018_Regular_SPLIT-Maximize to the IRS limit
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__Regular(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Participant ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else{
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();

			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);

			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to company match"))
				deferrals.click_MaximizeToCompanyMatch();
			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit();
			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			if (!lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock
						.GetParameterValue("Add_auto_increase_type"));

			deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "",
					true);
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
