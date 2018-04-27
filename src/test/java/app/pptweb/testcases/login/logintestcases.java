package app.pptweb.testcases.login;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import appUtils.TestDataFromDB;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class logintestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private static HashMap<String, String> testDataFromDB = null;
	LoginPage login;
	String tcName;
	static String printTestData="";
	static String url = null;
	static String accuCode = null;
	String[] sqlQuery=null;
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

	public void prepareLoginTestData(String quesryNmae,String... queryParam) {
		try {
			testDataFromDB = TestDataFromDB.getParticipantDataFromDB(
					quesryNmae, queryParam);
			//TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void prepareLoginTestData() {
		try {
			testDataFromDB = TestDataFromDB.getParticipantDataFromDB(
					"getRegisteredUser", Stock.GetParameterValue("ga_PlanId"));
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
	public void SF01_TC01_Verify_invalid_Userid_and_Password(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));

			login = new LoginPage();

			login.get();
			login.submitLoginCredentials(
					lib.Stock.GetParameterValue("username"),
					lib.Stock.GetParameterValue("password"));

			String errMsg = "";
			errMsg = login.isValidCredentials();

			if (errMsg.trim().isEmpty()) {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check error message displayed on submiting invalid credentials",
								"No error message is displayed after submiting login credentials\nExpected error message on clicking on Sign In button",
								true);

				return;
			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check error message displayed on submiting invalid credentials",
								"An error message is displayed as expected",
								true);
			}

			Web.VerifyText(lib.Stock.GetParameterValue("ExpectedErrorMessage"),
					errMsg, true);

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
	public void SF01_TC02_Verify_login_Successfully_into_unregistered_Device(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			String verificationCode = "";
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
														// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			// TODO Add code to verify text displayed on Two step verification
			// page

			// Verify options 'Text me', 'Call me', 'Email Me' and 'Already have
			// a code?' exists
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "TEXT ME:",true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "CALL ME:",true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "EMAIL:",true);

			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) {
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			} else {
				if (lib.Stock.GetParameterValue("deliveryOption").trim()
						.equalsIgnoreCase("EMAIL")) {
					verificationCode = twoStepVerification
							.getVerificationCode(false);
				} else {
					if (twoStepVerification.isActivationCodeGenerated(lib.Stock
							.GetParameterValue("deliveryOption"))) {
						Reporter.logEvent(Status.PASS,
								"Verify activation code is generated",
								"Activation code is successfully generated",
								false);
					} else {
						Reporter.logEvent(Status.FAIL,
								"Verify activation code is generated",
								"Activation code is not generated", false);
					}
					return;
				}
			}

			if (verificationCode.trim().length() == 0) {
				Reporter.logEvent(Status.FAIL, "Fetch verification code.",
						"Verification code not generated", false);
				return;
			}

			// Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			if(Web.isWebElementDisplayed(twoStepVerification, "CONTINUE CONTACT INFO PAGE", true))
			  Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage(twoStepVerification);
			// landingPage.dismissPopUps(true, true);

			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
			landingPage.get();
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}

			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	public void SF01_TC02_Verify_login_Successfully_into_unregistered_Device_Email(
			int itr, Map<String, String> testdata) {
	SF01_TC02_Verify_login_Successfully_into_unregistered_Device(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Login_CallCenter_timings(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			LoginPage login = new LoginPage();
			login.get();
			
			if (Stock.globalTestdata.get(Thread.currentThread().getId()).containsKey("ACCUCODE"))

			{
				if (Stock.GetParameterValue("AccuCode") != null)
					accuCode = Stock.GetParameterValue("AccuCode");
				else
					accuCode = Common.GC_DEFAULT_SPONSER;
				url = Stock.getConfigParam(
						"AppURL" + "_" + Stock.getConfigParam("TEST_ENV")).replace(
						"Empower", accuCode);

				 Web.getDriver().get(url);
				 Web.getDriver().navigate().refresh();
				 Web.waitForPageToLoad(Web.getDriver());
				 if(Web.getDriver().getCurrentUrl().contains(accuCode))
					 Reporter.logEvent(Status.PASS,
							 "Verify Login page for '" + Common.getSponser()
								+ "' is loaded.",
								"Login page for '" + Common.getSponser()
								+ "' is loaded.",
								true);

				 else	
					 Reporter.logEvent(Status.FAIL,
							 "Verify Login page for '" + Common.getSponser()
								+ "' is loaded.",
								"Login page for '" + Common.getSponser()
								+ "' is loaded.",
								true);


			}
			
			 
			String customerSupportInfo = "";
			boolean isTextMatching = false;
			
			customerSupportInfo = login.isValidCustomerSupportInfo();

			if (customerSupportInfo.trim().isEmpty()) {
				Reporter.logEvent(Status.FAIL,
						"Check Customer Support Information on the Login Page is Displayed",
						"No Customer Support Information on the Login Page",
						true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check Customer Support Information on the Login Page is Displayed",
								"Customer Support Information on the Login Page is displayed ",
								true);
			}

			isTextMatching = Web
					.VerifyText(
							Stock.GetParameterValue("Expected_CallCenterTimings_Pre_Login"),
							customerSupportInfo, true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check Customer Support Information on the Login Page is Same",
								"Customer Support Information is Same on the Login Page\nExpected:"+Stock.GetParameterValue("Expected_CallCenterTimings_Pre_Login")+"\nActual:"+customerSupportInfo,
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information is not same on the Login Page\nExpected:"
										+ Stock.GetParameterValue("Expected_CallCenterTimings_Pre_Login")
										+ "\nActual:" + customerSupportInfo,
								false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
				url = Stock.getConfigParam(
						"AppURL" + "_" + Stock.getConfigParam("TEST_ENV"));

				 Web.getDriver().get(url);
				 Web.getDriver().navigate().refresh();
				 Web.waitForPageToLoad(Web.getDriver());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void userName_Login_Sanity(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			String verificationCode = "";
			boolean isMatching=false;
			login = new LoginPage();

			login.get();
			login.submitLoginCredentials(
					lib.Stock.GetParameterValue("inValidUserName"),
					lib.Stock.GetParameterValue("password"));

			String errMsg = "";
			errMsg = login.isValidCredentials();

			if (errMsg.trim().isEmpty()) {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check error message displayed on submiting invalid credentials",
								"No error message is displayed after submiting login credentials\nExpected error message on clicking on Sign In button",
								true);

				return;
			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check error message displayed on submiting invalid credentials",
								"An error message is displayed as expected",
								true);
			}

			isMatching=Web.VerifyText(Stock.GetParameterValue("ExpectedErrorMessage"),
					errMsg, true);
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.INFO,
						"Check error message displayed on submiting invalid credentials",
						"An error message is displayed as expected\nExpected:"+Stock.GetParameterValue("ExpectedErrorMessage")+"\nActual:"+errMsg,
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.INFO,
						"Check error message displayed on submiting invalid credentials",
						"An error message is Not displayed as expected \nExpected:"+Stock.GetParameterValue("ExpectedErrorMessage")+"\nActual:"+errMsg,
						true);
			}
			TwoStepVerification twoStepVerification = new TwoStepVerification(login);
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
														// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);
			//Step 3
			
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("TEXT CONFIRM IDENTITY").toString().trim(),
					Stock.GetParameterValue("textConfirmIdentity"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.INFO,
						"Check Confirm Your Identity ... Text is Dispalyed",
						"Confirm Your Identity ... Text is Dispalyed\nExpected:"+Stock.GetParameterValue("textConfirmIdentity")+"\nActual:"+twoStepVerification.getWebElementText("TEXT CONFIRM IDENTITY").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.INFO,
						"Check Confirm Your Identity ... Text is Dispalyed",
						"Confirm Your Identity ... Text is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textConfirmIdentity")+"\nActual:"+twoStepVerification.getWebElementText("TEXT CONFIRM IDENTITY").trim(),
						true);
			}
			//Step 4
			// Verify options 'Text me', 'Call me', 'Email Me' and 'Already have
			// a code?' exists
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "TEXT ME:",true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "CALL ME:",true);
			Web.verifyDropDownOptionExists(twoStepVerification,
					"CHOOSE DELIVERY METHOD", "EMAIL:",true);

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
								.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
									.equalsIgnoreCase("EMAIL")) {
								verificationCode = twoStepVerification
										.getVerificationCode(false);
			}
			if (verificationCode.trim().length() == 0) {
				Reporter.logEvent(Status.FAIL, "Fetch verification code.",
						"Verification code not generated", false);
				return;
			}
			//Step 5
			// Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));

			
			LandingPage landingPage = new LandingPage(twoStepVerification);
			
			//Step 6
			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
			if(Web.isWebElementDisplayed(landingPage, "CONTINUE", true)){
				Web.clickOnElement(landingPage, "CONTINUE");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			}
			landingPage.get();
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}

			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);


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
	public void Pre_Login_Page_footer_links_check(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter
			.logEvent(
					Status.INFO,
					"Test Data used for this Test Case:",
					printTestData(),
					false);
			LoginPage login = new LoginPage();
			login.get();

			String copyRightInfo = "";
			boolean isLogoDisplayed = false;
			boolean isBannerDisplayed = false;
			boolean isTextMatching = false;
			isLogoDisplayed = login.isSponsorLogoDisplayed(Stock
					.GetParameterValue("logoName"));
			if (isLogoDisplayed) {
				lib.Reporter.logEvent(Status.PASS,
						"Sponsor Logo Displayed on the Login Page",
						"Sponsor Logo is Same on the Login Page", true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Sponsor Logo Displayed on the Login Page",
								"Sponsor Logo is not Displayed on the Login Page",
								true);
			}
			isBannerDisplayed = login.isSponsorBannerDisplayed();
			if (isBannerDisplayed) {
				lib.Reporter.logEvent(Status.PASS,
						"Sponsor Banner Displayed on the Login Page",
						"Sponsor Banner is Same on the Login Page", false);

			} else {
				lib.Reporter.logEvent(Status.FAIL,
						"Sponsor Banner Displayed on the Login Page",
						"Sponsor Banner is not Displayed on the Login Page",
						false);
			}
			copyRightInfo = login.getWebElementText("copyright info");
			isTextMatching = Web.VerifyText(
					Stock.GetParameterValue("copyright info"), copyRightInfo,
					true);

			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check CopyRight Information on the Login Page",
								"CopyRight Informatio is Same on the Login Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check CopyRight Information on the Login Page",
								"CopyRight Informatio is Not Same on the Login Page",
								false);
			}
			if (!Common.getSponser().equalsIgnoreCase("Apple")) {
				login.verifyWebElementDisplayed("image participant Savings rates");
				login.verifyWebElementDisplayed("image participant Rollover options");
				login.verifyWebElementDisplayed("image participant Browser Support");
			}
			//Step 2
			
			login.verifyWebElementDisplayed(/* "System Requirements and Security" */"Requirements and Security");
			login.verifyWebElementDisplayed("Privacy");
			login.verifyWebElementDisplayed(/* "Terms and Conditions" */"Terms");
			login.verifyWebElementDisplayed(/* "Business Continuity Plan" */"Business Continuity");
			login.verifyWebElementDisplayed(/* "Market Timing and Excessive Trading Policies" */"Market Timing and Excessive Trading");
			login.verifyWebElementDisplayed("BrokerCheck Notification");
			login.verifyWebElementDisplayed("FINRA Investor Education");
			login.verifyLinkIsNotBroken("Requirements and Security");
			login.clickDismissLink();
			login.verifyLinkIsNotBroken("Privacy");
			login.clickDismissLink();
			login.verifyLinkIsNotBroken("Terms");
			login.clickDismissLink();
			login.verifyLinkIsNotBroken("Business Continuity");
			login.clickDismissLink();
			login.verifyLinkIsNotBroken("Market Timing and Excessive Trading");
			login.clickDismissLink();
			login.verifyLinkIsNotBroken("FINRA Investor Education");
			login.clickDismissLink();
			//Step 3
			login.verifyWebElementDisplayed("FACEBOOK");
			login.verifyWebElementDisplayed("TWITTER");
			login.verifyWebElementDisplayed("INSTAGRAM");
			login.verifyWebElementDisplayed("LINKEDIN");
			login.verifyWebElementDisplayed("YOUTUBE");
			boolean windowFound = false;
			String parentWindow = Web.getDriver().getWindowHandle();
			Web.clickOnElement(login, "BrokerCheck Notification");
			Thread.sleep(7000);
			Set<String> handles = Web.getDriver().getWindowHandles();
			for (String windowHandle : handles) {

				if (!windowHandle.equals(parentWindow)) {
					if (Web.getDriver().switchTo().window(windowHandle)
							.getTitle().contains("BrokerCheck")|| Web.getDriver().switchTo().window(windowHandle)
							.getTitle().contains("FINRA")) {
						windowFound = true;
						break;
					}
				}
			}
			if (windowFound) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verifying Broker Check Notification is Opened in New Window",
								"Broker Check Notification is Opened in New Window",
								true);
				Web.getDriver().close();
				Web.getDriver().switchTo().window(parentWindow);
				Web.getDriver().navigate().refresh();

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verifying Broker Check Notification is Opened in New Window",
								"Broker Check Notification is Not Opened Properly in New Window",
								true);
				
			}

			
			
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			login.clickDismissLink();
			String customerSupportInfo = "";
			boolean isContactNoMatching = false;
			customerSupportInfo = login.isValidCustomerSupportInfo();

			if (customerSupportInfo.trim().isEmpty()) {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Customer Support Information on the Login Page",
						"No Customer Support Information on the Login Page",
						true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information on the Login Page is displayed ",
								true);
			}

			isTextMatching = Web
					.VerifyText(
							Stock.GetParameterValue("ExpectedCustomerSupportInfo_Pre_Login"),
							customerSupportInfo, true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information is Same on the Login Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check Customer Support Information on the Login Page",
								"Customer Support Information is not same on the Login Page\nExpected:"
										+ Stock.GetParameterValue("ExpectedCustomerSupportInfo_Pre_Login")
										+ "\nActual:" + customerSupportInfo,
								false);
			}
			isContactNoMatching = login.isValidContactUsInfo(Stock
					.GetParameterValue("ExpectedContactNo_Pre_login"));
			if (isContactNoMatching) {
				lib.Reporter.logEvent(Status.PASS,
						"Check Contact Us Information on the Login Page",
						"Contact Us Information is Same on the Login Page",
						true);

			} else {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Contact Us Information on the Login Page",
						"Contact Us Information is not same on the Login Page",
						true);
			}
			List<String> telePhoneNo = login.getPreLoginTelePhoneNo(Stock
					.GetParameterValue("Sponsor"));
			Web.clickOnElement(login, "dismiss");
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			//Step 6
			login.verifyWebElementDisplayed(/* "System Requirements and Security" */"Requirements and Security");
			login.verifyWebElementDisplayed("Privacy");
			login.verifyWebElementDisplayed(/* "Terms and Conditions" */"Terms");
			login.verifyWebElementDisplayed(/* "Business Continuity Plan" */"Business Continuity");
			login.verifyWebElementDisplayed(/* "Market Timing and Excessive Trading Policies" */"Market Timing and Excessive Trading");
			//login.verifyWebElementDisplayed("BrokerCheck Notification");
			login.verifyWebElementDisplayed("FINRA Investor Education");
			login.verifyLinkIsNotBroken("Requirements and Security");
			login.verifyLinkIsNotBroken("Privacy");
			login.verifyLinkIsNotBroken("Terms");
			login.verifyLinkIsNotBroken("Business Continuity");
			login.verifyLinkIsNotBroken("Market Timing and Excessive Trading");
			login.verifyLinkIsNotBroken("FINRA Investor Education");
			
			login.verifyWebElementDisplayed("FACEBOOK");
			login.verifyWebElementDisplayed("TWITTER");
			login.verifyWebElementDisplayed("INSTAGRAM");
			login.verifyWebElementDisplayed("LINKEDIN");
			login.verifyWebElementDisplayed("YOUTUBE");
			
			customerSupportInfo = login.isValidCustomerSupportInfo();

			if (customerSupportInfo.trim().isEmpty()) {
				lib.Reporter.logEvent(Status.FAIL,
						"Check Customer Support Information on the Home Page",
						"No Customer Support Information on the Home Page",
						true);

			} else {
				lib.Reporter
						.logEvent(
								Status.INFO,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information on the Home Page is displayed ",
								true);
			}
			isTextMatching = Web.VerifyText(
					Stock.GetParameterValue(
							"ExpectedCustomerSupportInfo_Post_Login")
							.toString().trim(), customerSupportInfo, true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information is Same on the Home Page",
								false);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Check Customer Support Information on the Home Page",
								"Customer Support Information is not Same on the Home Page. Expected/ "
										+ Stock.GetParameterValue("ExpectedCustomerSupportInfo_Post_Login")
										+ " Actual/ " + customerSupportInfo,
								false);
			}

			isContactNoMatching = login
					.isValidContactUsInfoPostLogin(telePhoneNo);
			if (isContactNoMatching) {
				lib.Reporter.logEvent(Status.PASS,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is Same on the Home Page",
						false);

			} else {
				lib.Reporter.logEvent(Status.INFO,
						"Check Contact Us Information on the Home Page",
						"Contact Us Information is not Same on the Home Page",
						false);
			}
			// Logout if opted
						homePage.logout(true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
			    Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Login_005_with_out_eDelivery_as_Electronic_Greater_than_180days(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			String verificationCode = "";
			Boolean isMatching=false;
			Common.updateEDeliveryMthodInDB();
			
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
			//Step 1&2											// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			//Step 3
			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) 
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			
 		   // Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			
			//Step 4
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").toString().trim(),
					Stock.GetParameterValue("textContactInfoHdr"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PERSONAL EMAIL", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "DROP DOWN COUNTRY", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PHONE NUMBER", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT COUNTRY CODE", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country Code Is Displayed",
						"Country Code Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country Code Is Displayed",
						"Country Code Is Not Displayed",true);
			}
           Web.setTextToTextBox("INPUT PERSONAL EMAIL", twoStepVerification, Stock.GetParameterValue("Email"));
           Web.selectDropDownOption(twoStepVerification, "DROP DOWN COUNTRY", Stock.GetParameterValue("Country"), true);
           Web.setTextToTextBox("INPUT PHONE NUMBER", twoStepVerification, Stock.GetParameterValue("phoneNo"));
           //Step5
          Web.getDriver().switchTo().defaultContent();
         // twoStepVerification.clickContinueInContactInfoPage();
           Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
         /*Actions keyBoard = new Actions(Web.getDriver());
         keyBoard.sendKeys(Keys.TAB).build().perform();
         twoStepVerification.clickOnSaveButton();*/
           if(Web.isWebEementEnabled(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN",true))
             Web.clickOnElement(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN");
         
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
         
			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage();
			landingPage.dismissPopUps(true, true);
			
			Web.waitForElement(landingPage, "Log out");
			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//Step 6 verify the part_comm_preferences table, where delivery method is displayed with 'ELECTRONIC'
			
			ResultSet participantID = null;
			sqlQuery=Stock.getTestQuery("getDeliveryMethodForParticipant");
			participantID =DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
			if (DB.getRecordSetCount(participantID) > 0) {
				try {
					participantID.first();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			}
			isMatching=Web.VerifyText(Stock.GetParameterValue("deliveryMethod"),
					participantID.getString("delivery_method"));
			if (isMatching) {
				Reporter.logEvent(Status.PASS,
						"Verify The Delivery Method in Data Base",
						"Delivery Method is displayed As Expected In Data Base \nExpexted:"+Stock.GetParameterValue("deliveryMethod")+"\nActual:"+participantID.getString("delivery_method"), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify The Delivery Method in Data Base",
						"Delivery Method is not displayed As Expected In Data Base \nExpexted:"+Stock.GetParameterValue("deliveryMethod")+"\nActual:"+participantID.getString("delivery_method"), false);
			}
			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	public void Login_004_eDelivery_with_two_plans_with_legacy_and_next_gen_Greater_than_180days(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			String verificationCode = "";
			Boolean isMatching=false;
			Common.updateEDeliveryMthodInDB();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
			//Step 1&2											// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			//Step 3
			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) 
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			
 		   // Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			
			//Step 4
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").toString().trim(),
					Stock.GetParameterValue("textContactInfoHdr"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PERSONAL EMAIL", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "DROP DOWN COUNTRY", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PHONE NUMBER", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT COUNTRY CODE", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country Code Is Displayed",
						"Country Code Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country Code Is Displayed",
						"Country Code Is Not Displayed",true);
			}
           Web.setTextToTextBox("INPUT PERSONAL EMAIL", twoStepVerification, Stock.GetParameterValue("Email"));
           Web.selectDropDownOption(twoStepVerification, "DROP DOWN COUNTRY", Stock.GetParameterValue("Country"), true);
           Web.setTextToTextBox("INPUT PHONE NUMBER", twoStepVerification, Stock.GetParameterValue("phoneNo"));
           //Step5
           Web.getDriver().switchTo().defaultContent();
           Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
           Thread.sleep(2000);
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
           Web.waitForElement(twoStepVerification,"Label Communication Preference");
           Web.waitForElement(twoStepVerification, "EMAIL DROP DOWN");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Label Communication Preference", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Not Displayed",true);
			}
           //Step 6
           Common.isTextFieldDisplayed("For mail delivery, uncheck the e-delivery option.");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT I PREFER", true);
      
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "CHECKBOX PLAN NAME", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Check Box with plan Name' Is Displayed",
						"Check Box with plan Name' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Check Box with plan Name' Is Displayed",
						"'Check Box with plan Name' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "EMAIL DROP DOWN", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Email DropDown' Is Displayed",
						"Email DropDown' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Email DropDown' Is Displayed",
						"'Email DropDown' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT AGREEMENT FROM", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"AGREEMENT FROM' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"'AGREEMENT FROM' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Button Save", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'SAVE Button' Is Displayed",
						"SAVE Button' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'SAVE Button' Is Displayed",
						"'SAVE Button' Is Not Displayed",true);
			}
           	// Step 7
           
           if(twoStepVerification.getNoofPlans()>1){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'CheckBoxes With Diffrent Plan Names' Are Displayed",
						"'CheckBoxes With Diffrent Plan Names' Are Displayed\n No.Of Plans:"+twoStepVerification.getNoofPlans(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'CheckBoxes With Diffrent Plan Names' Are Displayed",
						"'CheckBoxes With Diffrent Plan Names' Are Not DisplayedNo.Of Plans:"+twoStepVerification.getNoofPlans(),
						true);
			}
           
           //Stp 8
           String planName =Common.getPlanName(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("plan_id"));
           twoStepVerification.selectEdelivery(planName);
         
                  
           twoStepVerification.clickOnSaveButton();
         if(Web.isWebElementDisplayed(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN",true))
           Web.clickOnElement(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN");
        
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
           LandingPage landingPage = new LandingPage();
           Common.waitForProgressBar();
           //Step 9
			// Dismiss pop ups if displayed
			
			 landingPage.dismissPopUps(true, true);

			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
			
			Web.waitForElement(landingPage, "Log out");
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//Step 10 verify the part_comm_preferences table, where delivery method is displayed with 'ELECTRONIC'
			
			ResultSet participantID = null;
			sqlQuery=Stock.getTestQuery("getDeliveryMethodForParticipantWithMutiplePlans");
			participantID =DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
			
			List<String>deliveryMethodsList = new LinkedList<>();
						for(int i=1;;i++)
			{
				try{
				
					deliveryMethodsList.add(Stock.GetParameterValue("deliveryMethod"+i));
				}catch(Error e){
					break;
				}
				
			}
			String actualDelivryMethod = null;
			int counter = 0;
			while(participantID.next())
			{
				actualDelivryMethod = participantID.getString("DELIVERY_METHOD");
				if(actualDelivryMethod.equalsIgnoreCase(deliveryMethodsList.get(counter))){
					Reporter.logEvent(Status.PASS,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is not displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				}
				counter++;
				if(counter==deliveryMethodsList.size()){
					break;
				}
			}
			
			
			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
			Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	public void Login_003_eDelivery_with_two_plans_Greater_than_180days(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			String verificationCode = "";
			Boolean isMatching=false;
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			Common.updateEDeliveryMthodInDB();
			
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
			//Step 1&2											// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			//Step 3
			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) 
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			
 		   // Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			
			//Step 4
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").toString().trim(),
					Stock.GetParameterValue("textContactInfoHdr"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PERSONAL EMAIL", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "DROP DOWN COUNTRY", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PHONE NUMBER", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT COUNTRY CODE", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country Code Is Displayed",
						"Country Code Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country Code Is Displayed",
						"Country Code Is Not Displayed",true);
			}
           Web.setTextToTextBox("INPUT PERSONAL EMAIL", twoStepVerification, Stock.GetParameterValue("Email"));
           Web.selectDropDownOption(twoStepVerification, "DROP DOWN COUNTRY", Stock.GetParameterValue("Country"), true);
           Web.setTextToTextBox("INPUT PHONE NUMBER", twoStepVerification, Stock.GetParameterValue("phoneNo"));
           //Step5
           Web.getDriver().switchTo().defaultContent();
           Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
           Thread.sleep(6000);
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
           Web.waitForElement(twoStepVerification,"Label Communication Preference");
           Web.waitForElement(twoStepVerification, "EMAIL DROP DOWN");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Label Communication Preference", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Not Displayed",true);
			}
           //Step 6
           Common.isTextFieldDisplayed("For mail delivery, uncheck the e-delivery option.");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT I PREFER", true);
      
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "CHECKBOX PLAN NAME", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Check Box with plan Name' Is Displayed",
						"Check Box with plan Name' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Check Box with plan Name' Is Displayed",
						"'Check Box with plan Name' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "EMAIL DROP DOWN", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Email DropDown' Is Displayed",
						"Email DropDown' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Email DropDown' Is Displayed",
						"'Email DropDown' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT AGREEMENT FROM", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"AGREEMENT FROM' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"'AGREEMENT FROM' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Button Save", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'SAVE Button' Is Displayed",
						"SAVE Button' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'SAVE Button' Is Displayed",
						"'SAVE Button' Is Not Displayed",true);
			}
           	// Step 7
           
           if(twoStepVerification.getNoofPlans()>1){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'CheckBoxes With Diffrent Plan Names' Are Displayed",
						"'CheckBoxes With Diffrent Plan Names' Are Displayed\n No.Of Plans:"+twoStepVerification.getNoofPlans(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'CheckBoxes With Diffrent Plan Names' Are Displayed",
						"'CheckBoxes With Diffrent Plan Names' Are Not DisplayedNo.Of Plans:"+twoStepVerification.getNoofPlans(),
						true);
			}
           //Stp 8
           String planName =Common.getPlanName(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("plan_id"));
           twoStepVerification.selectEdelivery(planName);
         
          
           
           twoStepVerification.clickOnSaveButton();
           if(Web.isWebElementDisplayed(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN", true)){
         
           Web.clickOnElement(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN");
           }
           Common.waitForProgressBar();
           //Step 9
			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage();
			Common.waitForProgressBar();
			 landingPage.dismissPopUps(true, true);

			// Verify if landing page is displayed - Landing page is loaded if
			// Logout link is displayed.
		
			
			Web.waitForElement(landingPage, "Log out");
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//Step 10 verify the part_comm_preferences table, where delivery method is displayed with 'ELECTRONIC'
			
			ResultSet participantID = null;
			String[] sqlQuery=Stock.getTestQuery("getDeliveryMethodForParticipantWithMutiplePlans");
			participantID =DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
			
			List<String>deliveryMethodsList = new LinkedList<>();
						for(int i=1;;i++)
			{
				try{
				
					deliveryMethodsList.add(Stock.GetParameterValue("deliveryMethod"+i));
				}catch(Error e){
					break;
				}
				
			}
			String actualDelivryMethod = null;
			int counter = 0;
			while(participantID.next())
			{
				actualDelivryMethod = participantID.getString("DELIVERY_METHOD");
				if(actualDelivryMethod.equalsIgnoreCase(deliveryMethodsList.get(counter))){
					Reporter.logEvent(Status.PASS,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is not displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				}
				counter++;
				if(counter==deliveryMethodsList.size()){
					break;
				}
			}
			
			
			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	public void Login_002_eDelivery_as_Mail_Greater_than_180days(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			String verificationCode = "";
			Boolean isMatching=false;
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			Common.updateEDeliveryMthodInDB();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
			//Step 1&2											// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			//Step 3
			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) 
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			
 		   // Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			
			//Step 4
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").toString().trim(),
					Stock.GetParameterValue("textContactInfoHdr"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PERSONAL EMAIL", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "DROP DOWN COUNTRY", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PHONE NUMBER", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT COUNTRY CODE", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country Code Is Displayed",
						"Country Code Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country Code Is Displayed",
						"Country Code Is Not Displayed",true);
			}
           Web.setTextToTextBox("INPUT PERSONAL EMAIL", twoStepVerification, Stock.GetParameterValue("Email"));
           Web.selectDropDownOption(twoStepVerification, "DROP DOWN COUNTRY", Stock.GetParameterValue("Country"), true);
           Web.setTextToTextBox("INPUT PHONE NUMBER", twoStepVerification, Stock.GetParameterValue("phoneNo"));
           //Step5
           Web.getDriver().switchTo().defaultContent();
           Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
           Thread.sleep(6000);
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
           Web.waitForElement(twoStepVerification,"Label Communication Preference");
           Web.waitForElement(twoStepVerification, "EMAIL DROP DOWN");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Label Communication Preference", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Not Displayed",true);
			}
           //Step 6
           Common.isTextFieldDisplayed("For mail delivery, uncheck the e-delivery option.");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT I PREFER", true);
      
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "CHECKBOX PLAN NAME", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Check Box with plan Name' Is Displayed",
						"Check Box with plan Name' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Check Box with plan Name' Is Displayed",
						"'Check Box with plan Name' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "EMAIL DROP DOWN", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Email DropDown' Is Displayed",
						"Email DropDown' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Email DropDown' Is Displayed",
						"'Email DropDown' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT AGREEMENT FROM", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"AGREEMENT FROM' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"'AGREEMENT FROM' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Button Save", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'SAVE Button' Is Displayed",
						"SAVE Button' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'SAVE Button' Is Displayed",
						"'SAVE Button' Is Not Displayed",true);
			}
           	// Step 7
           
           twoStepVerification.clickOnSaveButton();
           if(Web.isWebElementDisplayed(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN", true)){
         
           Web.clickOnElement(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN");
           }
        
           Common.waitForProgressBar();
           //Step 9
			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage();
			Common.waitForProgressBar();
			 landingPage.dismissPopUps(true, true);
			 
			Web.waitForElement(landingPage, "Log out");
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//Step 8 verify the part_comm_preferences table, where delivery method is displayed with 'MAIL'
			
			ResultSet participantID = null;
			sqlQuery=Stock.getTestQuery("getDeliveryMethodForParticipantWithMutiplePlans");
			participantID =DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
			
			List<String>deliveryMethodsList = new LinkedList<>();
						for(int i=1;;i++)
			{
				try{
				
					deliveryMethodsList.add(Stock.GetParameterValue("deliveryMethod"+i));
				}catch(Error e){
					break;
				}
				
			}
			String actualDelivryMethod = null;
			int counter = 0;
			while(participantID.next())
			{
				actualDelivryMethod = participantID.getString("DELIVERY_METHOD");
				if(actualDelivryMethod.equalsIgnoreCase(deliveryMethodsList.get(counter))){
					Reporter.logEvent(Status.PASS,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is not displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				}
				counter++;
				if(counter==deliveryMethodsList.size()){
					break;
				}
			}
			
			
			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	public void Login_001_eDelivery_as_Electronic_Greater_than_180days(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					core.framework.Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId()) + "_"
							+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			String verificationCode = "";
			Boolean isMatching=false;
			prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			Common.updateEDeliveryMthodInDB();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					new LoginPage());
			twoStepVerification.get();

			twoStepVerification.setPageMandatory(true); // Two step verification
														// page is expected to
			//Step 1&2											// load
			twoStepVerification.get();

			Reporter.logEvent(Status.PASS,
					"Navigate to 'Two step verification (2 of 3)' page",
					"Navigation succeeded", true);

			//Step 3
			if (Web.isWebElementDisplayed(twoStepVerification,
					"Already have a code?")) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Already have a code?' link is displayed",
						"Link is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'Already have a code?' link is displayed",
						"Link is not displayed", false);
			}

			// Select code delivery option and click continue
			twoStepVerification.selectCodeDeliveryOption(lib.Stock
					.GetParameterValue("deliveryOption"));

			// Get verification code
			if (lib.Stock.GetParameterValue("deliveryOption").trim()
					.equalsIgnoreCase("ALREADY_HAVE_CODE")) 
				verificationCode = twoStepVerification
						.getVerificationCode(true);
			
 		   // Submit verification code
			Thread.sleep(5000);
			twoStepVerification.submitVerificationCode(verificationCode, true,
					Boolean.parseBoolean(lib.Stock
							.GetParameterValue("rememberDevice")));
			
			//Step 4
			isMatching=Web.VerifyText(twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").toString().trim(),
					Stock.GetParameterValue("textContactInfoHdr"));
			if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Check Confirm Your Contact Info Page is Dispalyed",
						"Confirm Your Contact Info Page Header is Dispalyed As Expected\nExpected:"+Stock.GetParameterValue("textContactInfoHdr")+"\nActual:"+twoStepVerification.getWebElementText("CONTACT INFORMATION HEADER").trim(),
						true);
			}
			isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PERSONAL EMAIL", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Personal Email Address Input Field Is Displayed",
						"Personal Email Address Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "DROP DOWN COUNTRY", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country List Drop Down Is Displayed",
						"Country List Drop Down Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "INPUT PHONE NUMBER", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Phone Number Input Field Is Displayed",
						"Phone Number Input Field Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT COUNTRY CODE", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Country Code Is Displayed",
						"Country Code Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Country Code Is Displayed",
						"Country Code Is Not Displayed",true);
			}
           Web.setTextToTextBox("INPUT PERSONAL EMAIL", twoStepVerification, Stock.GetParameterValue("Email"));
           Web.selectDropDownOption(twoStepVerification, "DROP DOWN COUNTRY", Stock.GetParameterValue("Country"), true);
           Web.setTextToTextBox("INPUT PHONE NUMBER", twoStepVerification, Stock.GetParameterValue("phoneNo"));
           //Step5
           Web.getDriver().switchTo().defaultContent();
           Web.clickOnElement(twoStepVerification, "CONTINUE CONTACT INFO PAGE");
           Thread.sleep(6000);
           Common.waitForProgressBar();
           Web.waitForPageToLoad(Web.getDriver());
           Web.waitForElement(twoStepVerification,"Label Communication Preference");
           Web.waitForElement(twoStepVerification, "EMAIL DROP DOWN"); 
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Label Communication Preference", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify Change Communaction Preferences Page Is Displayed",
						"Change Communaction Preferences Page Is Not Displayed",true);
			}
           //Step 6
           Common.isTextFieldDisplayed("For mail delivery, uncheck the e-delivery option.");
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT I PREFER", true);
      
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'E-delivery' Label Is Displayed",
						"'E-delivery' Label Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "CHECKBOX PLAN NAME", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Check Box with plan Name' Is Displayed",
						"Check Box with plan Name' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Check Box with plan Name' Is Displayed",
						"'Check Box with plan Name' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "EMAIL DROP DOWN", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'Email DropDown' Is Displayed",
						"Email DropDown' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'Email DropDown' Is Displayed",
						"'Email DropDown' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "TEXT AGREEMENT FROM", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"AGREEMENT FROM' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'AGREEMENT FROM' Is Displayed",
						"'AGREEMENT FROM' Is Not Displayed",true);
			}
           isMatching= Web.isWebElementDisplayed(twoStepVerification, "Button Save", true);
           
           if(isMatching){
				lib.Reporter
				.logEvent(
						Status.PASS,
						"Verify 'SAVE Button' Is Displayed",
						"SAVE Button' Is Displayed",
						false);
			}
			else{
				lib.Reporter
				.logEvent(
						Status.FAIL,
						"Verify 'SAVE Button' Is Displayed",
						"'SAVE Button' Is Not Displayed",true);
			}
           	// Step 7
           
         
           String planName =Common.getPlanName(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("plan_id"));
           twoStepVerification.selectEdelivery(planName);
         
           
           twoStepVerification.clickOnSaveButton();
           if(Web.isWebElementDisplayed(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN", true)){
         
           Web.clickOnElement(twoStepVerification, "BUTTON CONTINUE TO NEXTGEN");
           }
           
           Common.waitForProgressBar();
           //Step 9
			// Dismiss pop ups if displayed
			LandingPage landingPage = new LandingPage();
			Common.waitForProgressBar();
			 landingPage.dismissPopUps(true, true);
			Web.waitForElement(landingPage, "Log out");
			if (Web.isWebElementDisplayed(landingPage, "Log out")) {
				Reporter.logEvent(Status.PASS,
						"Verify landing page is displayed",
						"Landing page is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify landing page is displayed",
						"Landing page is not displayed", true);
			}
			//Step 10 verify the part_comm_preferences table, where delivery method is displayed with 'ELECTRONIC'
			
			ResultSet participantID = null;
			sqlQuery=Stock.getTestQuery("getDeliveryMethodForParticipantWithMutiplePlans");
			participantID =DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
			
			List<String>deliveryMethodsList = new LinkedList<>();
						for(int i=1;;i++)
			{
				try{
				
					deliveryMethodsList.add(Stock.GetParameterValue("deliveryMethod"+i));
				}catch(Error e){
					break;
				}
				
			}
			String actualDelivryMethod = null;
			int counter = 0;
			while(participantID.next())
			{
				actualDelivryMethod = participantID.getString("DELIVERY_METHOD");
				if(actualDelivryMethod.equalsIgnoreCase(deliveryMethodsList.get(counter))){
					Reporter.logEvent(Status.PASS,
							"Verify The Delivery Method in Data Base",
							"Delivery Method is displayed As Expected In Data Base \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify The Delivery Method in Data Base",
							"Landing page is not displayed \nExpexted:"+deliveryMethodsList.get(counter)+"\nActual:"+actualDelivryMethod, false);
				}
				counter++;
				if(counter==deliveryMethodsList.size()){
					break;
				}
			}
			
			
			// Logout if opted
			if(Web.isWebEementEnabled(landingPage, "Link Dismiss", true))
				Web.clickOnElement(landingPage, "Link Dismiss");
			landingPage.logout(true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
