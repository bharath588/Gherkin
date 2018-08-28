package app.psc.testcases.accountverification;

import java.lang.reflect.Method;
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
import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;
import framework.util.CommonLib;

public class accountverificationtest_plan {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	EmployeeSearch employeesearch;
	String actualErrorMessage = "";
	AccountVerificationPage accountverificationpage;
	LoginPage loginpage;
	String errorMessage;
	UserVerificationPage userverification;

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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC012_User access to New plan or Plans</u></b>
	 * 
	 * Application: <b>PSC</b>
	 * Functionality: <b>Login</b>
	 * Sub-functionality: <b>Give access to new plan number</b>
	 * Test Type: <b>Positive validation</b>
	 * 
	 * <b>Description:</b>
	 * This test case verifies that the user has to give the plan number to which it has got access recently when logs into the application
	 * 
	 * <pre>
	 * This test case is validated in three iterations
	 * </pre>
	 * 
	 * <pre>
	 * <b>Pre-Conditions:</b> The user should have got access to a new plan
	 * <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 * <b>txtPassword-</b> Password required to login 
	 *  <b>assignedPlanNumber -</b> Input for plan number
	 *  <b>chkPLuser -</b> Flag to check for PL user validations
	 * assigned to the user(Valid / Invalid)
	 * 
	 * @author krsbhr
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC012_02_Verify_User_Access_New_Plan_For_Valid_Input(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Verify if the plan number field is displayed when a new plan is added for an existing user"
							+ ":" + "Positive Sceanrio", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			accountverificationpage = new AccountVerificationPage();
			loginpage = new LoginPage();
			userverification = new UserVerificationPage();
			accountverificationpage.addPlanNumber(
					Stock.getTestQuery("addPlanNumberQuery"),
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("addPlanNumber"));
			accountverificationpage.logoutFromApplication();
			accountverificationpage.get();
			
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
							Stock.GetParameterValue("UserSecondaryAns") });
			Web.clickOnElement(accountverificationpage, "CANCEL");
			Thread.sleep(2000);
			if (Web.isWebElementDisplayed(loginpage, "LOGIN FRAME")) {
				Reporter.logEvent(
						Status.PASS,
						"Check if user is navigated to splash screen when it clicks cancel",
						"The user is navigated to splash screen when cancel is clicked",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if user is navigated to splash screen when it clicks cancel",
						"The user is not navigated to splash screen when cancel is clicked",
						true);
			}

			Reporter.logEvent(Status.INFO, "Cancel button check",
					"The cancel button event is checked", true);

			// input when the user clicks next button
			accountverificationpage.get();
			accountverificationpage.planNumberInput(Stock
					.GetParameterValue("PlanNumber"));
			errorMessage = accountverificationpage.getErrorMessageText();
			accountverificationpage.actualErrorValidationRptPositiveFlow(
					errorMessage, "Add New Plan");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
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
	 * Testcase: <b><u>SIT_PSC_Login_01_TC012_User access to New plan or Plans</u></b>
	 * 
	 * Application: <b>PSC</b>
	 * Functionality: <b>Login</b>
	 * Sub-functionality: <b>Give access to new plan number</b>
	 * Test Type: <b>Negative validation</b>
	 * 
	 * <b>Description:</b>
	 * This test case verifies that the user has to give the plan number to which it has got access recently when logs into the application
	 * 
	 * <pre>
	 * This test case is validated in three iterations
	 * </pre>
	 * 
	 * <pre>
	 * <b>Pre-Conditions:</b> The user should have got access to a new plan
	 * <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 * <b>txtPassword-</b> Password required to login 
	 *  <b>assignedPlanNumber -</b> Input for plan number
	 *  <b>expectedErrorMessage -</b> Expected error message to be displayed
	 * assigned to the user(Valid / Invalid)
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC012_01_Verify_User_Access_New_Plan_For_Invalid_Input(int itr,
			Map<String, String> testdata) {
		// Stock.globalTestdata = testdata;
		try {
			userverification = new UserVerificationPage();
			accountverificationpage = new AccountVerificationPage();
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"Verify if the plan "
							+ "r field is displayed when a new plan is added for an existing user"
							+ ":" + "Negative Sceanrio", false);
			Reporter.logEvent(Status.INFO,
					"Test Data used for iteration" + itr,
					CommonLib.getIterationDataAsString(testdata), false);
			accountverificationpage.addPlanNumber(
					Stock.getTestQuery("addPlanNumberQuery"),
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("PlanNumber"));//addPlanNumber
			accountverificationpage.logoutFromApplication();
			accountverificationpage.get();
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
							Stock.GetParameterValue("UserSecondaryAns") });
			accountverificationpage.planNumberInput(Stock
					.GetParameterValue("PlanNumber"));
			actualErrorMessage = accountverificationpage.getErrorMessageText();
			Web.clickOnElement(userverification, "ERRORMSG");
			Web.clickOnElement(userverification, "DISMISS");
			Thread.sleep(1000);
			accountverificationpage.actualErrorValidationRptNegativeFlow(
					actualErrorMessage,
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
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@AfterSuite
	public void cleanUpSession() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}

}
