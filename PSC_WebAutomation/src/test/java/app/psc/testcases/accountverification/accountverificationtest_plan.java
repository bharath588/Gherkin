package app.psc.testcases.accountverification;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Reporter.Status;
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
				.getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@Test(dataProvider = "setData")
	public void TC012_02_Verify_User_Access_New_Plan_For_Valid_Input(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			accountverificationpage = new AccountVerificationPage();
			loginpage = new LoginPage();
			userverification = new UserVerificationPage();
			accountverificationpage.addPlanNumber(
					Stock.getTestQuery("addPlanNumberQuery"),
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("addPlanNumber"));
			accountverificationpage.get();
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
					Stock.GetParameterValue("UserSecondaryAns") });
			Web.clickOnElement(accountverificationpage, "CANCEL");
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
			accountverificationpage.actualErrorValidationRptPositiveFlow(errorMessage,"Add New Plan");
					
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
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
	public void TC012_01_Verify_User_Access_New_Plan_For_Invalid_Input(int itr,
			Map<String, String> testdata) {
		Stock.globalTestdata = testdata;
		try {
			userverification = new UserVerificationPage();
			accountverificationpage = new AccountVerificationPage();
			Reporter.initializeReportForTC(itr,
					"TC012_01_Verify_User_Access_New_Plan_For_Invalid_Input");
			accountverificationpage.addPlanNumber(
					Stock.getTestQuery("addPlanNumberQuery"),
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("addPlanNumber"));
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
			accountverificationpage.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
			        Stock.GetParameterValue("expectedErrorMessage"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
		}
		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@AfterSuite
	public void cleanUpSession() {
		Web.webdriver.close();
		Web.webdriver.quit();
	}

}
