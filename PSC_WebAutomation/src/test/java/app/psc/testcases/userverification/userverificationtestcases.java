package app.psc.testcases.userverification;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;

public class userverificationtestcases {
	LoginPage login;
	UserVerificationPage userverification;
	HomePage home;
	boolean checkReLoginStats = false;
	private LinkedHashMap<Integer, Map<String, String>> testData = null;	

/*	@BeforeTest
	@Parameters({"browser"})
	public void getBrowser(String browser)
	{
		Web.getDriver() = Web.getWebDriver(browser);
	}*/
	@BeforeClass
	public void ReportInit(){		
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
	 * <pre>
	 * Testcase: <b>
	 * <ul>
	 * SIT_PSC_Login_01_TC007_Login flow- New Browser
	 * </ul>
	 * </b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Login New Browser</b> Test
	 * Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b> Verify the user is able to navigate with valid email
	 * and secondary answer when he logs in with new browser.
	 * 
	 * <u><b>Test data:</b></u> <b>txtUserName -</b> Valid user name
	 * <b>txtPassword -</b> Valid password <b>UserVeriEmail -</b> user
	 * verification email <b>UserSecondaryAns -</b> secondary answer
	 *
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC007_01_Verify_Login_Flow_Existing_User_NewBrowser_Valid_Input(int itr, Map<String, String> testdata) {
		String actualErrorMessage = "";
		login = new LoginPage();
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			userverification = new UserVerificationPage().get();
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the user is able to navigate with valid email"
				   +"and secondary answer when he logs in with new browser", false);	
			Thread.sleep(3000);
			
			//userverification.get();
			Web.clickOnElement(userverification, "CANCEL");
			// Verify if the user id displayed same as logged in id
			
			if(Web.isWebElementDisplayed(login, "LOGIN FRAME",true)){
				Reporter.logEvent(Status.PASS, "Check if user is navigated to splash screen when it clicks cancel",
						"The user is navigated to splash screen when cancel is clicked", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if user is navigated to splash screen when it clicks cancel",
						"The user is not navigated to splash screen when cancel is clicked", true);
			}
			userverification = new UserVerificationPage().get();
			userverification.verifyUserIdDisplayed(Stock.GetParameterValue("username"));
			// Enter Valid user secondary answer and email
			userverification.performVerification(
					new String[] { Stock.GetParameterValue("UserVeriEmail"), Stock.GetParameterValue("UserSecondaryAns") });

			actualErrorMessage = userverification.getErrorMessageText();
			
			if(actualErrorMessage.trim().isEmpty()){
				Reporter.logEvent(Status.PASS,"Check if the the user is able to proceed with the valid input in user verification page",
						"The user is able to proceed with valid inputs:" + "User Verification Email"
								+ Stock.GetParameterValue("UserVeriEmail") + "Secondary Answer" + Stock.GetParameterValue("UserSecondaryAns"), false);
			}else{
				Reporter.logEvent(Status.FAIL,"Check if the the user is able to proceed with the valid input in user verification page",
						"The user is not able to proceed with valid inputs:" + "User Verification Email"
								+ Stock.GetParameterValue("UserVeriEmail") + "Secondary Answer" + Stock.GetParameterValue("UserSecondaryAns"), true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}  catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * Testcase: <b>
	 * <ul>
	 * SIT_PSC_Login_01_TC007_Login flow- New Browser
	 * </ul>
	 * </b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Login New Browser</b> Test
	 * Type: <b>Negative validation</b>
	 * 
	 * <b>Description:</b> Verify the user is able to navigate with valid email
	 * and secondary answer when he logs in with new browser.
	 * 
	 * <u><b>Test data:</b></u> <b>txtUserName -</b> Valid user name
	 * <b>txtPassword -</b> Valid password <b>UserVeriEmail -</b> user
	 * verification email <b>UserSecondaryAns -</b> secondary answer
	 * <b>expectedErrorMessage -</b> error massage for invalid input
	 * 
	 * @author krsbhr(21-SEP-2015)
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC007_02_Verify_Login_Flow_Existing_User_New_Browser_Invalid_input(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			String actualErrorMessage = "";
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the user is not able to navigate with invalid email"
	 +"and secondary answer when he logs in with new browser", false);
			userverification = new UserVerificationPage().get();
			
			userverification.performVerification(
					new String[] { Stock.GetParameterValue("UserVeriEmail"),
							       Stock.GetParameterValue("UserSecondaryAns") });
			actualErrorMessage = userverification.getErrorMessageText();

			if (!actualErrorMessage.trim().isEmpty()) {
				
				Reporter.logEvent(Status.PASS, "Check if user is able to proceed with invalid input",
						          "Error message is displayed for invalid input", false);
				// Verify the displayed error message
				if((Web.VerifyText(actualErrorMessage, Stock.GetParameterValue("expectedErrorMessage")))){
					Reporter.logEvent(Status.PASS, "Check if appropriate error mrssage is displayed for invalid input",
							"Appropriate error message is displayed as expected "+actualErrorMessage, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Check if appropriate error mrssage is displayed for invalid input",
							"Appropriate error message is not displayed"
									+ "Expected :" + Stock.GetParameterValue("expectedErrorMessage") + "Actual" + actualErrorMessage, true);
				}
			} else {
				Reporter.logEvent(Status.FAIL, "Check if user is able to proceed with invalid input",
						"Error message is not displayed for invalid input", true);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}  catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC007_03_Verify_Login_Flow_Existing_User_withing_same_browser_session(int itr,Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the user is navigated directly to the home while re-login within same session", false);									
			
			userverification = new UserVerificationPage();
			home = new HomePage();			
			if(!checkReLoginStats && !Web.getDriver().getWindowHandle().isEmpty() 
					              &&  Web.getDriver().getCurrentUrl().contains("http")){
				//If already in Jump Page
				if(Web.isWebElementDisplayed(home,"JUMP_PAGE_NXTGEN_LINK",false)){
					Web.clickOnElement(home,"JUMP_PAGE_NXTGEN_LINK");
					Web.waitForElement(home,"LOGOUT_LINK");
					Web.clickOnElement(home,"LOGOUT_LINK");
				}
				
				//If already in Home Page
				if(Web.isWebElementDisplayed(home,"LOGOUT_LINK",false)){
					Web.clickOnElement(home,"LOGOUT_LINK");
				}				
				checkReLoginStats = true;
			}else{
				checkReLoginStats = true;
			}			
						
			if(itr==1){
				userverification.get();
				if(userverification.getSecurityQuestion().equalsIgnoreCase("car"))
					userverification.performVerification(
						        new String[] {Stock.GetParameterValue("UserVeriEmail"),
								              Stock.GetParameterValue("dreamCar")});
				else if(userverification.getSecurityQuestion().equalsIgnoreCase("drink"))
					userverification.performVerification(
					        new String[] {Stock.GetParameterValue("UserVeriEmail"),
							              Stock.GetParameterValue("favDrink")});
				else
					userverification.performVerification(
					        new String[] {Stock.GetParameterValue("UserVeriEmail"),
							              Stock.GetParameterValue("spouseMidName")});
				home.validate_if_homepage_loaded(Stock.GetParameterValue("ifSingleSiteUser"));
			}
			if(itr==2){
				home = new HomePage(new LoginPage(),false,new String[] {
									Stock.GetParameterValue("username"),
									Stock.GetParameterValue("password") }).get();
			}
			home.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}  catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Verify_TUDS_table_for_Magic_login(int itr,Map<String,String> testData)
	{
		try{
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
				"Verify TUDS table for Magic login", false);									
		
		HomePage homePage = new HomePage(new LoginPage(), false, new String[] {
			Stock.GetParameterValue("username"),
			Stock.GetParameterValue("password") }).get();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error ae)
		{
			
		}
		finally{
			try{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().quit();
	}

}
