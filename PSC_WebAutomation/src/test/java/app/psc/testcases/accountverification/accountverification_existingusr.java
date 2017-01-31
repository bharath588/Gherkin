package app.psc.testcases.accountverification;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import lib.DB;
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

public class accountverification_existingusr {
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
	 * <pre>Verify the expired password scenario for invalid user input</pre>
	 * @param itr
	 * @param testdata
	 * @throws SQLException
	 * @throws Exception
	 */
	
	@Test(dataProvider = "setData")
	public void TC004_01_Verify_Changepasword_When_Password_Expired_Negative_Scenario(
			int itr, Map<String, String> testdata) throws SQLException,
			Exception {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the expired password scenario for negative user input", false);
			userverification = new UserVerificationPage();
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			String actualErrorMessage;
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			if (itr == 1) {
				userverification.performVerification(new String[] {
						(userverification.getEmailAddressOfuser(
								Stock.getTestQuery("getEmailaddressQuery"),
								Stock.GetParameterValue("username"))).trim(),
						Stock.GetParameterValue("UserSecondaryAns") });
			}
			accountverification.resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));			
			actualErrorMessage = accountverification.getErrorMessageText();			

			if (Web.isWebElementDisplayed(accountverification, "ERRORMSG")) {				
				Web.clickOnElement(accountverification, "ERRORMSG");
				Web.clickOnElement(accountverification, "DISMISS");
			}
			accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
			        Stock.GetParameterValue("expectedErrorMessage"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
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
	 * <pre>Verify the expired password scenario for valid user input</pre>
	 * @param itr
	 * @param testdata
	 * @throws Exception
	 */
	@Test(dataProvider = "setData")
	public void TC004_02_Verify_Changepasword_When_Password_Expired_Positive_Flow(
			int itr, Map<String, String> testdata) throws Exception {
		String actualErrorMessage = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the expired password scenario for valid user input", false);			
			userverification = new UserVerificationPage();
			accountverification = new AccountVerificationPage();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
					Stock.GetParameterValue("UserSecondaryAns") });
			if (Web.isWebElementDisplayed(accountverification, "ERRORMSG")) {
				Web.clickOnElement(accountverification, "ERRORMSG");
				Web.clickOnElement(accountverification, "DISMISS");
			}
			Thread.sleep(2000);
			accountverification.resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));

			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));

			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage,"Changed Password");
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}catch (Error ae) {
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC019_Default plan access</u></b>
	 *  
	 *  Application: <b>PSC</b>
	 *  Functionality: <b>Login</b>
	 *  Sub-functionality: <b>Default Planprompt</b>
	 *  Test Type: <b>Positive validation</b>
	 *  
	 *  <b>Description:</b>
	 *             A random user will be required to go through default plan prompting if default ga_id = NULL
	 *  
	 *  <u><b>Test data:</b></u>
	 *  <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 *  <b>txtPassword-</b> Password required to login 
	 *  <b>PlanNumber -</b> Input for plan number
	 *  <b>setDefaultplanNullQuery  -</b> Query to set default plan id to NULL
	 * <b>getPlanNUmberQuery -</b> Query to fetch the total number of plans for the user 
	 * 
	 * <b>Number of iterations -</b> 3
	 * @author krsbhr
	 * 
	 */

	@Test(dataProvider = "setData")
	public void TC019_Verify_Default_Plan_Prompting(int itr,
			Map<String, String> testdata) throws SQLException, Exception {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the Default plan prompting sceanrio for an user according to the number of plans"
					+ "The user has access to", false);
			int PlanCount = 0;
			String[] setDeafultPlanNullQuery;
			accountverification = new AccountVerificationPage();
			userverification = new UserVerificationPage();
			accountverification.logoutFromApplication();
			setDeafultPlanNullQuery = Stock
					.getTestQuery("setDefaultPlanNullQuery");
			DB.executeUpdate(setDeafultPlanNullQuery[0],
					setDeafultPlanNullQuery[1],
					"K_" + Stock.GetParameterValue("username"));

			PlanCount = accountverification.getNumberOfplans(
					Stock.getTestQuery("getNumberOfplansQuery"),
					"K_" + Stock.GetParameterValue("username"));
			System.out.println("The number of plans the user has:" + PlanCount);
			userverification.get();
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
					Stock.GetParameterValue("UserSecondaryAns") });
			if (PlanCount > 25) {

				if ((Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if search box displayed for default plan prompting displayed for user having more than 25 plans",
							"Search box is displayed for users having more than 25 plans",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if search box displayed for default plan prompting displayed for user having more than 25 plans",
							"Search box is not displayed for users having more than 25 plans",
							true);
				}
			}
			if (PlanCount > 1 && PlanCount < 25) {
				if ((Web.isWebElementDisplayed(accountverification,
						"DEFAULT PLAN DROPDOWN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if dropdown box is displayed for the number of plans less than 25",
							"The dropdown box is displayed for the user having plan number 1 to 25",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if dropdown box is displayed for the number of plans less than 25",
							"The dropdown box is not displayed for user having plan number 1 to 25",
							true);
				}
			}
			if (PlanCount == 1) {
				accountverification.get();
				if ((!Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))
						&& !(Web.isWebElementDisplayed(accountverification,
								"DEFAULT PLAN DROPDOWN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if default plan prompt page is displayed for user having one plan",
							"The system doesnot prompt user to enter default plan if it is having only one plan",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if default plan prompt page is displayed for user having one plan",
							"Default plan page is displayed for the users having one plan",
							true);
				}
			}
			Web.clickOnElement(accountverification, "BTNNEXT");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
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
 * Testcase: <b><u>SIT_PSC_Login_01_TC018_Existing User_Security questions validation</u></b>
 *  
 *  Application: <b>PSC</b>
 *  Functionality: <b>Login</b>
 *  Sub-functionality: <b>Security Questions</b>
 *  Test Type: <b>Positive validation</b>
 *  
 *  <b>Description:</b>
 *  When the security questions are deleted for the user should be asked to re-enter them 
 *  
 *  <u><b>Test data:</b></u>
 *   <b>txtUserName       -</b> Valid user name
 *             <b>txtPassword               -</b> Valid password
 *             <b>deleteSecurityQuesQuery -</b> Query to delete security questions
 *  <b>firstDrpdwnAns -</b> Answer for the first dropdown 
 *  <b>secondDrpdwnAns -</b> Answer for the second dropdown 
 *  <b>thirdDrpdwnAns -</b> Answer for the third fropdown
 * <b>Number of iterations = </b> 1
 * @author krsbhr
 * 
 */
	@Test(dataProvider = "setData")
	public void TC018_Security_Question_Validation_Existing_User(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates if the user is navigated to security answers page when the security answers are"
					+ "deleted in database", false);
			accountverification = new AccountVerificationPage();
			String[] queryDeleteSecurityAnswers;
			accountverification.logoutFromApplication();
			// Step-1 : Delete the security questions for the user from DB
			queryDeleteSecurityAnswers = Stock
					.getTestQuery("deleteSecurityAnsQuery");

			DB.executeUpdate(queryDeleteSecurityAnswers[0],
					queryDeleteSecurityAnswers[1],
					"K_" + Stock.GetParameterValue("username"));

			// Step-2 : Check if the user is prompted to answer security
			// questions
			accountverification.get();

			if ((Web.isWebElementDisplayed(accountverification,
					"SECURITY ANSWER"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify if the user is prompted to security question on deletion",
						"The user is prompted for security question", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if the user is prompted to security question on deletion",
						"The security questions are not displayed", true);
			}

			// Step-2 : Enter all the answers and click next
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));

			accountverification.actualErrorValidationRptPositiveFlow(accountverification.getErrorMessageText(),"Security Questions");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		}  finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().quit();
	}

}
