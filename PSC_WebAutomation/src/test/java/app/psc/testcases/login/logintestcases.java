package app.psc.testcases.login;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.fileSharing.FileSharing;
import pageobjects.homepage.HomePage;
import pageobjects.jumppage.JumpPage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;
import framework.util.CommonLib;

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
		this.testData = Stock.getTestData(this.getClass().getPackage()
				.getName(), testCase.getName());
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
	public void TC017_01_Internal_Employee_External_Client_Login(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Validate the internal employee vs External client Login scenario",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			// Step-1 : Login with internal/external employee credentials
			login = new LoginPage().get();
			//below step added by swtcht
			 if (Web.isWebElementDisplayed(login, "LOGIN FRAME"))
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			// Thread.sleep(10000);
			Web.waitForPageToLoad(Web.getDriver());
			// Step-2 : Check if the user is on the login page
			if ((!Web.isWebElementDisplayed(login, "LOGIN FRAME"))) {
				Reporter.logEvent(Status.PASS,
						"Check if the user is on the login page",
						"User is successfully logged in", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if the user is on the login page",
						"User login failed ", true);
			}
			 new JumpPage().logoutPSCFromJumpage();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	public void TC006_01_Verify_Force_Login(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Validate A random user when enters any URL and do force login,it should be navigated to planEmpower site",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage();
			boolean isUsernameFieldDisplayed;
			boolean isGWRSLogoDisplayed;
			// Step-1 Check for force login true and if the user stays on the
			// same
			// page-GWRS

			Web.getDriver().get(Stock.GetParameterValue("ForceLoginTrueURL"));
			isUsernameFieldDisplayed = Web.isWebElementDisplayed(login,
					"FORCELOGIN USERNAME");

			if (isUsernameFieldDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Check if user name field displayed",
						"Username field displayed successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if user name field displayed",
						"Expected Username field didnt display", true);
			}

			// Step-2 :check for forcelogin false.The user should be navigated
			// to
			// Empower site and verify modal window

			Web.getDriver().get(Stock.GetParameterValue("ForceLoginFalseURL"));
			isGWRSLogoDisplayed = Web
					.isWebElementDisplayed(login, "GWRS IMAGE");

			if (isGWRSLogoDisplayed) {
				Reporter.logEvent(Status.PASS, "Check if GWRS Logo displayed",
						"GWRS Logo is displayed as expected", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Check if GWRS Logo displayed",
						"GWRS Logo is not displayed ", true);
			}
			Thread.sleep(7000);
			if ((Web.isWebElementDisplayed(login, "LOGIN FRAME"))) {
				Reporter.logEvent(Status.PASS,
						"Check if the Login Frame is displayed",
						"Login frame is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if the Login Frame is displayed",
						"Login frame is not displayed", true);
			}

			// Step-3:Check for forcelogin with dummyvalues
			Web.getDriver().get(Stock.GetParameterValue("ForceLoginDummyURL"));
			isGWRSLogoDisplayed = Web
					.isWebElementDisplayed(login, "GWRS IMAGE");

			if (isGWRSLogoDisplayed) {
				Reporter.logEvent(Status.PASS, "Check if GWRS Logo displayed",
						"GWRS Logo is displayed as expected", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Check if GWRS Logo displayed",
						"GWRS Logo is not displayed ", true);
			}
			Thread.sleep(7000);
			if ((Web.isWebElementDisplayed(login, "LOGIN FRAME"))) {
				Reporter.logEvent(Status.PASS,
						"Check if the Login Frame is displayed",
						"Login frame is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if the Login Frame is displayed",
						"Login frame is not displayed", true);
			}
			Web.getDriver().get(
					Stock.getConfigParam("AppURL" + "_"
							+ Stock.getConfigParam("TEST_ENV")));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	public void TC002_01_Verify_Existing_User_Error_Messages_PreLogin(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the pre-login error messages", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username").trim(),
					Stock.GetParameterValue("password").trim() });
			Thread.sleep(4000);
			if (itr==3){
			login.verifyErrorforRespectiveLogin(Stock
						.GetParameterValue("errorMessages").trim() +"\n"+ Stock
						.GetParameterValue("errorMessages1").trim());
			}
			login.verifyErrorforRespectiveLogin(Stock
					.GetParameterValue("errorMessages"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	public void TC004_01_Verify_Header_and_Footer_Links_PreLogin(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the pre login Header and footer links", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			login.checkHeaderLinkPreLogin();
			Thread.sleep(3000);
			login.checkFooterLinkPreLogin();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	public void TC004_02_Verify_Header_and_Footer_Links_PostLogin(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the post login Header and footer links", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();

			List<String> preLoginFooterList = login.getPreLoginFooterLinkList();
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Thread.sleep(3000);
			accountverification.jumpPagedisplayed();
			home.checkFooterLinkPostLogin(preLoginFooterList);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC005_01_Verify_User_Account_Locking_For_Max_Invalid_Attempt(
			int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Verify user account is getting locked if user enters wrong password for three times",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			login = new LoginPage();
			login.updateInvalidLogonAttempt(
					Stock.getTestQuery("queryToSetInvalidLoginCount"),
					Stock.GetParameterValue("username"));
			login.get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			login.verifyErrorforRespectiveLogin(Stock
					.GetParameterValue("errorMessages"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of account locking for wrong credential.",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Login page header could not be verified.",
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
	 * Testcase: <b><u>SIT_PSC_Sitenavigation_01_TC001_Menutabs</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validate all menu.</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify if all the expected Menu options are displayed on Home page
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password
	 */
	@Test(dataProvider = "setData")
	public void TC001_01_SIT_PSC_Sitenavigation_Menutabs(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"verify if all the expected Menu and sub menu options all displayed on Home page.",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Thread.sleep(3000);
			accountverification.jumpPagedisplayed();
			home.verifyHomePageMenuTabs();
			home.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC004_Terminate User</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validating inactive user
	 * Login</b> Test Type: <b>Negative flow</b>
	 * 
	 * <b>Description:</b> To verify user is prompted with error message if try to login
	 * with terminated user credentials
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password <b>txtUserVeriEmail -</b> User Verification Email
	 * <b>txtUserVeriAns -</b> User Verification Security Answer
	 * <b>termDate -</b> Termination date of the employee
	 * 
	 * @author rvpndy (08-FEB-2017)
	 */

	@Test(dataProvider = "setData")
	public void TC006_01_Verify_Error_Message_For_Terminated_User(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Verify user is prompted with an error message if user tries to login with terminated user",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			login = new LoginPage();
			login.updateInvalidLogonAttempt(
					Stock.getTestQuery("queryToResetInvalidLoginCount"),
					Stock.GetParameterValue("username"));
			login.updateTermDateForUser(
					Stock.getTestQuery("queryToTerminateUser"),
					Stock.GetParameterValue("termDate"),
					Stock.GetParameterValue("username"));
			login.get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			if (itr == 1) {
				login.verifyErrorforRespectiveLogin(Stock
						.GetParameterValue("errorMessages"));
			} else {
				UserVerificationPage userverification = new UserVerificationPage();
				userverification.performVerification(new String[] {
						Stock.GetParameterValue("UserVeriEmail"),
						Stock.GetParameterValue("UserSecondaryAns") });
				new HomePage().get();
				Reporter.logEvent(Status.PASS, "Check if home page is loaded",
						"Home page is loaded", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of login flow of terminated user.",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
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
	 * Testcase: <b><u>SIT_PSC_Sitenavigation_SubMenutabs</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validate all Sub menu options on home page.</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify if all the expected sub menu options are displayed on Home page
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password <b>menuname -</b>valid menuname
	 */
	@Test(dataProvider = "setData")
	public void TC001_02_SIT_PSC_Sitenavigation_SubMenutabs(int itr,
			Map<String, String> testdata) {
		try {
			
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"verify if all the expected sub menu options all displayed on Home page.",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Thread.sleep(3000);
			home.verifySubMenuAndOptions(Stock.GetParameterValue("menuname"));
			Web.waitForPageToLoad(Web.getDriver());
			home.logoutPSC();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	 * Testcase: <b><u>Display correct date and time of my last login</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Verifying equality of last login date values
	 * from app and DB</b> Test Type: <b>Psotive flow</b>
	 * 
	 * <b>Description:</b> To verify equality of last login date time value is equal to 
	 * date time value stored in database
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password <b>txtUserVeriEmail -</b> User Verification Email
	 * <b>txtUserVeriAns -</b> User Verification Security Answer
	 * <b>MENU_FEATURE_CODE -</b> Browser Info
	 * <b>APPLICATION</b> Application name in menu activity table.
	 * @author rvpndy (21-FEB-2017)
	 */

	@Test(dataProvider = "setData")
	public void TC007_01_display_correct_date_and_time_of_my_last_login(
			int itr, Map<String, String> testData) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify last login displayed on home page is correct",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			HomePage homePage = new HomePage();
			String date = homePage.getLastLoginDate();
			login = new LoginPage().get();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if (homePage.verifyLastLoginDateEquality(date)) {
				Reporter.logEvent(
						Status.PASS,
						"Compare last login date and time from application and database ",
						"Time displayed on application home page is equal to time stored in database",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Compare last login date and time values from application and database ",
						"Time displayed on application home page is not equal to time stored in database",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of last login time equality between app and db.",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
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
	 * Testcase: <b><u>Verify_Jump_Page_Not_Displayed_When_User_Have_Access_To_Plan_In_Singlesite</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validate jump page not to display if user has access to plans only in single site.</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify Jump page not to display
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password <b>menuname -</b>valid menuname
	 */
	@Test(dataProvider = "setData")
	public void TC001_09_Verify_Jump_Page_Not_Displayed_When_AcessToPlanInSingleSite(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"verify if jump page is not displayed after login when user has acess to plan only in single site.",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			userverification = new UserVerificationPage();
			userverification.performVerification(new String[] {
					Stock.GetParameterValue("UserVeriEmail"),
					Stock.GetParameterValue("UserVeriEmail") });
			Web.waitForPageToLoad(Web.getDriver());
			home.isJumpPageDisplayed();
			home.jumpPageVerificationWhenPlanAccessInSingleSite();
			Web.waitForPageToLoad(Web.getDriver());

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC008_01_Verify_Equality_Of_SessionID_From_Browser_And_DB(
			int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Verify session ID from browser cookie is equal to session ID from database",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			HomePage homePage = new HomePage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			// Thread.sleep(2000);
			if (homePage.validateCookieValue())
				Reporter.logEvent(
						Status.PASS,
						"Verify session ID from browser cookie is equal to session ID from database",
						"Sessions IDs are verified positively for equality",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify session ID from browser cookie is equal to session ID from database",
						"Sessions IDs are not equal", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of equality of session IDs from app and db.",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
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
	 * Testcase: <b><u>Verify_Jump_Page_Displayed_When_User_Has_Access_To_Plan_In_Allsites</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validate jumpage is displayed if user has access to plans available in all sites(NextGen,Heritage,Putnam).</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To verify if jump page is displayed if user has plans available in all sites.
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password.
	 */
	@Test(dataProvider = "setData")
	public void TC002_Verify_Jump_Page_Displayed_When_AcessToPlansInAllSites(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"verify if jump page is displayed when user has access to plans in all sites.",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage().get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			home = new HomePage();
			accountverification = new AccountVerificationPage();
			userverification = new UserVerificationPage();
			Thread.sleep(2000);
			userverification.performVerification(new String[] {
					Stock.GetParameterValue("UserVeriEmail"),
					Stock.GetParameterValue("UserVeriEmail") });
			Web.waitForPageToLoad(Web.getDriver());
			home.isJumpPageDisplayed();
			home.jumpPageVerificationWhenPlanAccessInAllSite();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC009_01_Verify_To_Do_List_Site_Navigator(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-1319");
			Reporter.logEvent(
					Status.INFO,
					"Testcase description",
					"Verify clicking on To do(from all options) redirects users to same page",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			HomePage homePage = new HomePage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if (homePage.verifyToDoSiteNavigation(Stock
					.GetParameterValue("BUTTONNAME"))) {
				Reporter.logEvent(Status.PASS,
						"Check user is properly navigated",
						"User is properly navigated", false);
			} else
				Reporter.logEvent(Status.FAIL,
						"Check user is properly navigated",
						"User is not properly navigated", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of site naviagation",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
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
	 * Testcase: <b><u>UI_Validations_On_JumpPage</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validate UI of JumpPage(Links,Search Box,Messages).</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> To validate UI of JumpPage
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password.
	 */
	@Test(dataProvider = "setData")
	public void TC003_UI_Validations_On_JumpPage(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"To verify UI of JumpPage(Links,SearchBox,Messages)", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			/*
			 * JumpPage jp = new JumpPage(new UserVerificationPage(), true, new
			 * String[]
			 * {Stock.GetParameterValue("UserVeriEmail"),Stock.GetParameterValue
			 * ("UserSecondaryAns")}).get();
			 */
			JumpPage jp = new JumpPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Web.waitForPageToLoad(Web.getDriver());
			jp.jumpPageUIValidation();
			//jp.jumpPageSearchPlanBoxValidation();
			jp.logoutPSCFromJumpage();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC010_01_Verify_Restyled_Button_Home_Page_For_Sentence_Case(
			int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-1353");
			Reporter.logEvent(Status.INFO, "Testcase description",
					"Verify restyled buttons on home page for sentence case",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			HomePage homePage = new HomePage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if (homePage.verifyButtonTextForSentenceCase()) {
				Reporter.logEvent(Status.PASS,
						"Check button text is in sentence case",
						"Button text is in sentence case", false);
			} else
				Reporter.logEvent(Status.FAIL,
						"Check button text is in sentence case",
						"Button text is not in sentence case", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured during verification of restyled button on home page.",
					e.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
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
	 * Testcase: <b><u>Verify_PLUser_Login_With_No_Plna_Access</u></b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Verify PL user login with no plan access.</b> Test Type: <b>Positive flow</b>
	 * 
	 * <b>Description:</b> Verify PL user login with no plan access
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid Username <b>password
	 * -</b> Valid Password.
	 */
	@Test(dataProvider = "setData")
	public void TC004_Verify_PLUser_Login_With_No_Plna_Access(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify PL User Login with no Plan access", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			HomePage homePage = new HomePage(new LoginPage(), false,
					new String[] { Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Reporter.logEvent(Status.INFO, "Login Status", "Login is done",
					true);
			Web.getDriver().switchTo().frame("framea");
			homePage.isPlanListDisplayed();
			Web.getDriver().switchTo().defaultContent();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC05_01_SIT_PSC_HomePage_01_TC001_frame_on_Dashboard_Filesharing_and_Reports_pages(
			int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Resize iframe height based on content from Angular app",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			FileSharing fileSharing = new FileSharing();
			fileSharing.get();
			if (fileSharing.verifyPaginationOnFileSharingPage()) {
				Reporter.logEvent(Status.PASS,
						"Check pagination on file sharing page",
						"Pagination on File Sharing page is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check pagination on file sharing page",
						"Pagination on File Sharing page is not displayed",
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
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case validates the various tabs enablity based on respective
	 * transaction codes
	 * 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC01_SIT_PSC_HomePage_Transaction_Security(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This test case validates the various tabs enability based on respective transaction codes",
					false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			login = new LoginPage();
			JumpPage jumpPage = new JumpPage();
			HomePage homePage = new HomePage(new LoginPage(), false,
					new String[] { Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage.validateHomeScreenSectionAfterLogin();
			
			if (login.checkPlanTabTxnCodes()) {
				homePage.searchPlanWithIdOrName(Stock.GetParameterValue("PlanNo"));
				homePage.validatePlanSubmenuBasedOnTxnCodes();
				homePage.validateOtherHomePageSecutiryTXNCodes();
				login.deletePlanMenuTxnCodes1();
				homePage.logoutPSC();
				login.submitLoginCredentials(new String[] {
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password") });
				jumpPage.ClickOnJumpPageURL();
				homePage.validateHomePageScreenAfterDeletingTxnCodes1();
				login.insertPlanMenuTxnCodes();
				login.deletePlanMenuTxnCodes2();
				homePage.logoutPSC();
				Thread.sleep(2000);
				login.submitLoginCredentials(new String[] {
						Stock.GetParameterValue("username"),
						Stock.GetParameterValue("password") });
				jumpPage.ClickOnJumpPageURL();
				homePage.validateOverviewAndPlanDashboardAfterDeletingTxnCodes();
				login.insertPSOVPGandPSOVMNTxnCodes();
				homePage.logoutPSC();

			} else {
				Reporter.logEvent(Status.FAIL,
						"Validate if all Txn codes 'PSOVPG','PSWVRS','PSPROV','PSVSCH'"
								+ " are assigned to user.",
								"All txn codes are not assigned.please assign all txn codes"
										+ " to execute test case.", false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
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
	 * SIT_PSC_Login_01_TC020_Unsuccessful_login_attempts
	 * </b>
	 * 
	 * Application: <b>PSC</b> Functionality: <b>Validating invalid login attempts and account locking</b> 
	 * Test Type: <b>Negative validation</b>
	 * 
	 * <b>Description:</b> Verify that user account is locked out for 24 hours after 3 invalid tries
	 * 
	 * <u><b>Test data:</b></u> <b>username -</b> Valid user name <b>password
	 * -</b> Valid password and Invalid Password
	 * @param itr iteration
	 * @param testData testData
	 */
	@Test(dataProvider = "setData")
	public void TC011_01_SIT_PSC_Login_01_TC020_Unsuccessful_login_attempts(
			int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify user account is getting locked for 24 hours "
							+ "if user enters wrong password for 4 times",
							false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testData), false);
			login = new LoginPage();
			login.updateInvalidLogonAttempt(
					Stock.getTestQuery("queryToSetInvalidLoginCount"),
					Stock.GetParameterValue("username"));
			login.get();
			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("incorrectPassword") });
			login.verifyErrorforRespectiveLogin(Stock
					.GetParameterValue("errorMessages"));
			if (login.isInvalidLoginDpDateUpdated()) {
				Reporter.logEvent(
						Status.PASS,
						"Update invalid login DP date column in DB to yesterday's date",
						"Invalid login DP date column is updated", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Update invalid login DP date column in DB to yesterday's date",
						"Invalid login DP date column is not updated", false);
			}

			login.submitLoginCredentials(new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") });
			WebElement userVerificationEmailbox = Web.returnElement(
					new UserVerificationPage(), "EMAIL ADDRESS");
			if (Web.isWebElementDisplayed(userVerificationEmailbox, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is able to login after 24 hours of locking out period",
						"user is able to login after 24 hours of locking out period",
						false);
			} else {
				WebElement homeLogo = Web.returnElement(new HomePage(),
						"Home_Page_Logo");
				if (Web.isWebElementDisplayed(homeLogo, true))
					Reporter.logEvent(
							Status.PASS,
							"Verify user is able to login after 24 hours of locking out period",
							"user is able to login after 24 hours of locking out period",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify user is able to login after 24 hours of locking out period",
							"user is unable to login after 24 hours of locking out period",
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
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * @Test(dataProvider = "setData") public void
	 */
	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().close();
		Web.getDriver().quit();
		Web.removeWebDriverInstance();
	}
}
