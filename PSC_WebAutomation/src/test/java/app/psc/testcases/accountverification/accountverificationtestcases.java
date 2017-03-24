package app.psc.testcases.accountverification;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;


@SuppressWarnings("unused")
public class accountverificationtestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;	
	private AccountVerificationPage accountverification;
	private LoginPage login;
	private UserVerificationPage userverification;
	private HomePage home;

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
	 * <pre>
	 * Testcase: <b><u>SIT_PSC_Login_01_TC008_Login flow- New PSC User</u></b>
	 * 
	 * Application: <b>PSC</b>
	 * Functionality: <b>Login</b>
	 * Sub-functionality: <b>New PSC User Login Flow</b>
	 * Test Type: <b>Negative validation</b>
	 * 
	 * <b>Description:</b>
	 * This test case verifies the different validations for a plan number input
	 * 
	 * <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 * <b>txtPassword-</b> Password required to login 
	 *  <b>PlanNumber -</b> Input for plan number
	 * <b>chkusername -</b> Flag  to check for username validation
	 * <b>firstDrpdwnAns -</b> Answer for the first dropdown 
	 * <b>secondDrpdwnAns -</b> Answer for the second dropdown 
	 * <b>thirdDrpdwnAns -</b> Answer for the third dropdown
	 * <b>Username - </b> Input for registering username
	 * </pre>
	 */
	@Test(dataProvider = "setData")
	public void TC008_00_Verify_new_user_flow_negative(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the New User Flow End to End"+":"
			+"Negative Sceanrio", false);
			accountverification = new AccountVerificationPage();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			String recentPlan = accountverification.recreateNewUser(
					Stock.getTestQuery("updateAttributesQuery"),
					Stock.getTestQuery("deleteSecurityAnsQuery"),
					Stock.getTestQuery("deleteFriendlyUsernameQuery"),
					Stock.getTestQuery("getRecentPlanQuery"),
					"K_" + Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification = new UserVerificationPage();
			String actualErrorMessage = "";
			String emailAddress = accountverification.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			int userAccessTosites = accountverification.getUseraccessForSites(
					Stock.getTestQuery("getNumberofSiteAccess"),
					Stock.GetParameterValue("username"));
			
			// Reset password page
			accountverification.resetPassword(userAccessTosites
					  						  ,emailAddress
					  						  ,recentPlan);
			
			actualErrorMessage = accountverification.getErrorMessageText();
			if (!actualErrorMessage.trim().isEmpty()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if error message is displayed for invalid input",
						"Error message is displayed for invalid input", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}catch (Error ae) {
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
	 * Application: <b>PSC</b>
	 * Functionality: <b>Login</b>
	 * Sub-functionality: <b>New PSC User Login Flow</b>
	 * Test Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b>
	 * This test case verifies the new user flow
	 * 
	 * <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 * <b>txtPassword-</b> Password required to login 
	 *  <b>PlanNumber -</b> Input for plan number
	 * <b>chkusername -</b> Flag  to check for username validation
	 * <b>firstDrpdwnAns -</b> Answer for the first dropdown 
	 * <b>secondDrpdwnAns -</b> Answer for the second dropdown 
	 * <b>thirdDrpdwnAns -</b> Answer for the third dropdown
	 * <b>Username - </b> Input for registering username
	 * </pre>
	 * @throws Exception 
	 */
	@Test(dataProvider = "setData")
	public void TC008_00_Verify_new_user_flow_positive(int itr,
			Map<String, String> testdata) throws Exception {
		int totalPlan;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the New User Flow End to End"+":"
			+"Positive Sceanrio", false);
			accountverification = new AccountVerificationPage();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			totalPlan = accountverification.getNumberOfplans(
					Stock.getTestQuery("getNumberOfplansQuery"),
					"K_" + Stock.GetParameterValue("username"));
			String recentPlan = accountverification.recreateNewUser(
					Stock.getTestQuery("updateAttributesQuery"),
					Stock.getTestQuery("deleteSecurityAnsQuery"),
					Stock.getTestQuery("deleteFriendlyUsernameQuery"),
					Stock.getTestQuery("getRecentPlanQuery"),
					"K_" + Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification = new UserVerificationPage();
			String actualErrorMessage = "";
			String emailAddress = accountverification.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			int userAccessTosites = accountverification.getUseraccessForSites(
					Stock.getTestQuery("getNumberofSiteAccess"),
					Stock.GetParameterValue("username"));
			// Reset password page
			accountverification.resetPassword(userAccessTosites
					  ,emailAddress
					  ,recentPlan);

			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage
																	 ,"New User Flow");
			
			// The user enters question for the security answers
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));
			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage
					                                  ,"New User Flow Security Answers");
			
			if (userAccessTosites == 1 && totalPlan > 1) {
				if ((Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))) {
					Web.setTextToTextBox("SEARCH DEFAULT PLAN", recentPlan);
					Web.clickOnElement(accountverification, "BTNNEXT");
				}
				if ((Web.isWebElementDisplayed(accountverification,
						"DEFAULT PLAN DROPDOWN"))) {
					accountverification.selectUnlockedPlan();
					Web.clickOnElement(accountverification, "BTNNEXT");
				}
			}
			accountverification.registerUsername(Stock
					.GetParameterValue("regUserName"));
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage
													,"New User Flow- Register User Name");

			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			accountverification.logoutFromApplication();
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
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
	 * Application: <b>PSC</b>
	 * Functionality: <b>Login</b>
	 * Sub-functionality: <b>New PSC User Login Flow</b>
	 * Test Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b>
	 * This test case verifies the different validations for answering security questions
	 * 
	 * <pre>
	 * This test case is validated in three iterations
	 * </pre>
	 * 
	 * <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 * <b>txtPassword-</b> Password required to login <b>firstDrpdwnAns -</b> Answer
	 * for the first dropdown <b>secondDrpdwnAns -</b> Answer for the second
	 * dropdown <b>thirdDrpdwnAns -</b> Answer for the third fropdown <b>PlanNumber
	 * -</b> Input for plan number dropdown in security question
	 * <b>chkCancelButtonClick-</b> To check if cancel button validation performed
	 * <b>Number of iterations = </b> 3
	 */
	@Test(dataProvider = "setData")
	public void TC008_01_Verify_Login_Flow_Security_Answer_Invalid_Input_New_PSC_User(
			int itr, Map<String, String> testdata) throws SQLException,
			Exception {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the New User Flow End to End"+":"+"Security answers validation"
			+"Negative Sceanrio", false);
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			String recentPlan = accountverification.recreateNewUser(
					Stock.getTestQuery("updateAttributesQuery"),
					Stock.getTestQuery("deleteSecurityAnsQuery"),
					Stock.getTestQuery("deleteFriendlyUsernameQuery"),
					Stock.getTestQuery("getRecentPlanQuery"),
					"K_" + Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification = new UserVerificationPage();
			String actualErrorMessage = "";
			String emailAddress = accountverification.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			int userAccessTosites = accountverification.getUseraccessForSites(
					Stock.getTestQuery("getNumberofSiteAccess"),
					Stock.GetParameterValue("username"));
			// Reset password page
			accountverification.resetPassword(userAccessTosites
					  ,emailAddress
					  ,recentPlan);
			// The user enters question for the security answers
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));
			actualErrorMessage = accountverification.getErrorMessageText();
			Web.clickOnElement(userverification, "ERRORMSG");
			Web.clickOnElement(userverification, "DISMISS");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				ThrowException.Report(TYPE.INTERRUPTED,
						"Exception at thread sleep");
			}

			accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
			        Stock.GetParameterValue("expectedErrorMessage"));
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
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
	 * <pre>Verify the new user flow when the security questions are kept same</pre>
	 * @param itr
	 * @param testdata
	 * @throws SQLException
	 * @throws Exception
	 */
	@Test(dataProvider = "setData")
	public void TC008_02_Verify_Login_Flow_Security_Answer_Select_Security_Questions_Same(
			int itr, Map<String, String> testdata) throws SQLException,
			Exception {
		String actualErrorMessage = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the New User Flow End to End"+":"+"Security answers validation when the questions are same"
			+"Negative Sceanrio", false);
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			String recentPlan = accountverification.recreateNewUser(
					Stock.getTestQuery("updateAttributesQuery"),
					Stock.getTestQuery("deleteSecurityAnsQuery"),
					Stock.getTestQuery("deleteFriendlyUsernameQuery"),
					Stock.getTestQuery("getRecentPlanQuery"),
					"K_" + Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification = new UserVerificationPage();
			String emailAddress = accountverification.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			int userAccessTosites = accountverification.getUseraccessForSites(
					Stock.getTestQuery("getNumberofSiteAccess"),
					Stock.GetParameterValue("username"));
			// Reset password page
			accountverification.resetPassword(userAccessTosites
					  ,emailAddress
					  ,recentPlan);

			// Step-2 : selecting same security questions
			accountverification.selectSameSecurityQuestions();
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));
			actualErrorMessage = accountverification.getErrorMessageText();

			accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
			        Stock.GetParameterValue("expectedErrorMessage"));
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
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
	 * <pre>Validate the New User Flow End to End For register username input invalid</pre>
	 * @param itr
	 * @param testdata
	 * @throws SQLException
	 * @throws Exception
	 */
	@Test(dataProvider = "setData")
	public void TC008_03_Verify_New_User_loginFlow_register_username_invalid_input(
			int itr, Map<String, String> testdata) throws SQLException,
			Exception {
		int totalPlan;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Validate the New User Flow End to End"+":"+"For register username input"
			+"Negative Scenario", false);
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			totalPlan = accountverification.getNumberOfplans(
					Stock.getTestQuery("getNumberOfplansQuery"),
					"K_" + Stock.GetParameterValue("username"));
			String recentPlan = accountverification.recreateNewUser(
					Stock.getTestQuery("updateAttributesQuery"),
					Stock.getTestQuery("deleteSecurityAnsQuery"),
					Stock.getTestQuery("deleteFriendlyUsernameQuery"),
					Stock.getTestQuery("getRecentPlanQuery"),
					"K_" + Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification = new UserVerificationPage();
			String actualErrorMessage = "";
			String emailAddress = accountverification.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			int userAccessTosites = accountverification.getUseraccessForSites(
					Stock.getTestQuery("getNumberofSiteAccess"),
					Stock.GetParameterValue("username"));
			// Reset password page
			accountverification.resetPassword(userAccessTosites
					  ,emailAddress
					  ,recentPlan);

			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage
					                                                 ,"New User Flow");
			// The user enters question for the security answers
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));
			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage
					                                                 ,"New User Flow Security Answers");
			if (userAccessTosites == 1 && totalPlan > 1) {
				if ((Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))) {
					Web.setTextToTextBox("SEARCH DEFAULT PLAN", recentPlan);
					Web.clickOnElement(accountverification, "BTNNEXT");
				}
				if ((Web.isWebElementDisplayed(accountverification,
						"DEFAULT PLAN DROPDOWN"))) {
					accountverification.selectUnlockedPlan();
					Web.clickOnElement(accountverification, "BTNNEXT");
				}
			}
			accountverification.registerUsername(Stock.GetParameterValue("regUserName"));
			actualErrorMessage = accountverification.getErrorMessageText();
			Web.clickOnElement(userverification, "ERRORMSG");
			Web.clickOnElement(userverification, "DISMISS");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
					        Stock.GetParameterValue("expectedErrorMessage"));
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			accountverification.logoutFromApplication();
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
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
		lib.Web.getDriver().close();
		lib.Web.getDriver().quit();
	}

}
