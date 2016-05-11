package app.psc.testcases.login;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;


public class logintestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;

	AccountVerificationPage accountverification;
	LoginPage login;
	UserVerificationPage userverification;
	HomePage home;

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

	/**
	 * <pre>
	 * Testcase: <b><u>SIT_PSC_Login_01_TC017_Internal and External User</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Login</b> Sub-functionality:
	 * <b>Internal vs External Employee</b> Test Type: <b>Positive
	 * validation</b>
	 * 
	 * <b>Description:</b> Check if the internal emplyee and an external client
	 * is able to login to the application
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> username for the
	 * internal/external user <b>password -</b> password for the
	 * internal/external user <b>Number of iterations = </b> 2
	 * 
	 * @author krsbhr(14-SEP-2015)
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC017_01_Internal_Employee_External_Client_Login(int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the internal employee vs External client Login scenario", false);
			// Step-1 : Login with internal/external employee credentials
			login = new LoginPage().get();
		
			login.submitLoginCredentials(new String[]{Stock.GetParameterValue("username"), Stock.GetParameterValue("password")});
			Thread.sleep(10000);
			// Step-2 : Check if the user is on the login page
			if ((!Web.isWebElementDisplayed(login, "LOGIN FRAME"))) {
				Reporter.logEvent(Status.PASS, "Check if the user is on the login page",
						"User is successfully logged in", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Check if the user is on the login page", "User login failed ", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);	
		} catch (Error ae) {
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC006_Gwrs- Forcelogin</u></b>
	 * </pre>
	 * 
	 * Application: <b>PSC</b>
	 * <nl>
	 * Functionality: <b>Login</b>< Sub-functionality: <b>Force Login</b> Test
	 * Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b> A random user when enters any URL and do force
	 * login,it should be navigated to planEmpower site
	 * 
	 * <u><b>Test data:</b></u> <b>ForceLoginTrueURL -</b> URL for forcelogin
	 * true <b>ForceLoginFalseURL -</b> URL for forcelogin fasle
	 * <b>ForceLoginDummyURL -</b> URL for forcelogin with dummy value <b>Number
	 * of iterations = </b> 1
	 * 
	 * @author krsbhr(14-SEP-2015)
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC006_01_Verify_Force_Login(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate A random user when enters any URL and do force login,it should be navigated to planEmpower site", false);
			login = new LoginPage();
			boolean isUsernameFieldDisplayed;
			boolean isGWRSLogoDisplayed;
			// Step-1 Check for force login true and if the user stays on the
			// same
			// page-GWRS

			Web.webdriver.get(Stock.GetParameterValue("ForceLoginTrueURL"));
			isUsernameFieldDisplayed = Web.isWebElementDisplayed(login, "FORCELOGIN USERNAME");
			
			if(isUsernameFieldDisplayed){
				Reporter.logEvent(Status.PASS, "Check if user name field displayed",
						"Username field displayed successfully", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if user name field displayed",
						"Expected Username field didnt display", true);
			}

			// Step-2 :check for forcelogin false.The user should be navigated
			// to
			// Empower site and verify modal window

			Web.webdriver.get(Stock.GetParameterValue("ForceLoginFalseURL"));
			isGWRSLogoDisplayed = Web.isWebElementDisplayed(login, "GWRS IMAGE");
			
			if(isGWRSLogoDisplayed){
				Reporter.logEvent(Status.PASS, "Check if GWRS Logo displayed",
						"GWRS Logo is displayed as expected", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if GWRS Logo displayed",
						"GWRS Logo is not displayed ", true);
			}
			Thread.sleep(7000);
			if((Web.isWebElementDisplayed(login, "LOGIN FRAME"))){
				Reporter.logEvent(Status.PASS, "Check if the Login Frame is displayed",
						"Login frame is displayed", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the Login Frame is displayed",
						"Login frame is not displayed",true);
			}


			// Step-3:Check for forcelogin with dummyvalues
			Web.webdriver.get(Stock.GetParameterValue("ForceLoginDummyURL"));
			isGWRSLogoDisplayed = Web.isWebElementDisplayed(login, "GWRS IMAGE");

			if(isGWRSLogoDisplayed){
				Reporter.logEvent(Status.PASS, "Check if GWRS Logo displayed",
						"GWRS Logo is displayed as expected", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if GWRS Logo displayed",
						"GWRS Logo is not displayed ", true);
			}
			Thread.sleep(7000);
			if((Web.isWebElementDisplayed(login, "LOGIN FRAME"))){
				Reporter.logEvent(Status.PASS, "Check if the Login Frame is displayed",
						"Login frame is displayed", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the Login Frame is displayed",
						"Login frame is not displayed",true);
			}		
			Web.webdriver.get(Stock.getConfigParam("AppURL"+"_"+Stock.getConfigParam("TEST_ENV")));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (Error ae) {
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC008_Login flow- New PSC User</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Login</b> Sub-functionality:
	 * <b>Check Error messages</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify if the respective error messages display
	 * for invalid login
	 * 
	 * @author svkdtt (08-SEP-2015)
	 *
	 */

	@Test(dataProvider = "setData")
	public void TC002_01_Verify_Existing_User_Error_Messages_PreLogin(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the pre-login error messages", false);
			login = new LoginPage().get();
			
		login.submitLoginCredentials(new String[]{Stock.GetParameterValue("username"), Stock.GetParameterValue("password")});
			login.verifyErrorforRespectiveLogin(Stock.GetParameterValue("errorMessages"));			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (Error ae) {
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
	 * SIT_PSC_Login_01_TC014_Footer links
	 * </ul>
	 * </b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validating Header and Footer
	 * link</b> Test Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b> Verify that all header and footer links redirects to
	 * the correct page and can be navigated to home page
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid user name <b>password
	 * -</b> Valid password
	 * 
	 * @author svkdtt(17-AUG-2015)
	 * 
	 */

	@Test(dataProvider = "setData")
	public void TC004_01_Verify_Header_and_Footer_Links_PreLogin(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the pre login Header and footer links", false);
			login = new LoginPage().get();
			login.checkHeaderLinkPreLogin();
			Thread.sleep(1000);
			login.checkFooterLinkPreLogin();			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (Error ae) {
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC014_Footer links</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validating Footer Links Post
	 * Login</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify if the respective footer links opens into a
	 * modal window post login
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password <b>txtUserVeriEmail -</b> User Verification Email
	 * <b>txtUserVeriAns -</b> User Verification Security Answer
	 * 
	 * @author svkdtt (17-AUG-2015)
	 */
	@Test(dataProvider = "setData")
	public void TC004_02_Verify_Header_and_Footer_Links_PostLogin(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the post login Header and footer links", false);
			login = new LoginPage().get();
			
			List<String> preLoginFooterList = login.getPreLoginFooterLinkList();
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			userverification = new UserVerificationPage().get();
			userverification.performVerification(
					new String[] { (userverification.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(), Stock.GetParameterValue("UserSecondaryAns") });
			Thread.sleep(3000);
			accountverification.jumpPagedisplayed();
			home.checkFooterLinkPostLogin(preLoginFooterList);			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (Error ae) {
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

	@AfterSuite
	public void DriverQuite() {
//		Web.webdriver.close();
//		Web.webdriver.quit();
	}
}
