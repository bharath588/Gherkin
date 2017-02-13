package app.psc.testcases.guidedpayroll;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
//import lib.Reporter.Status;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.guidedpayroll.GuidedPayrollPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;



public class guidedpayrolltestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	AccountVerificationPage accountverification;
	LoginPage login;
	UserVerificationPage userverification;
	HomePage home;
	GuidedPayrollPage guidedpayroll;
	String actualErrorMessage;
	String expectedErrorMessage;
	private String tcName = Globals.GC_EMPTY;

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
		this.testData = Stock.getTestData(this.getClass().getName(), testCase.getName());
	}

	@BeforeMethod
	public void getTCName(Method tc) {
		tcName = tc.getName();
	}

	@Test(dataProvider = "setData")
	public void TC001_Verify_Contributiontotal_for_Invalid_User_input(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, tcName);
			guidedpayroll = new GuidedPayrollPage().get();	
			guidedpayroll.navigateToProcessCenter();
			actualErrorMessage = guidedpayroll.validateContributionTotal(testdata.get("amount"));
			expectedErrorMessage = testdata.get("ExpectedErrorMessage");
			if (expectedErrorMessage.trim().equalsIgnoreCase(actualErrorMessage)) {
				Reporter.logEvent(Status.PASS, "Check proper error message is displayed for invalid input",
						"Proper error message is displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Check proper error message is displayed for invalid input",
						"Proper error message is not displayed", true);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", "Assertion Failed!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC002_Compare_Contributiontotal_Displayed(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, tcName);
			boolean isEqual = false;
			guidedpayroll = new GuidedPayrollPage().get();
			guidedpayroll.navigateToProcessCenter();
			Reporter.logEvent(Status.INFO, "Check if the user is landed on Process center page",
					"The user has landed on guided payroll page", true);
			guidedpayroll.createContributionProcessingforSingleMoneySource(testdata.get("date"),
					testdata.get("amount"));
			isEqual = guidedpayroll.compareContributionTotal(testdata.get("amount"));
			if (isEqual) {
				Reporter.logEvent(Status.PASS, "Check if the expected contribution total same as entered one",
						"The expected cont total equals to the input", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Check if the expected contribution total same as entered one",
						"The expected cont total does not equals to the input", true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", "Assertion Failed!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	}

	@Test(dataProvider = "setData")
	public void TC003_Create_Contribution_For_Single_Money_Source(int itr, Map<String, String> testdata) {		
		@SuppressWarnings("unused")
		String RefNumGenerated;
		try {
			Reporter.initializeReportForTC(itr, tcName);
			guidedpayroll = new GuidedPayrollPage().get();
			guidedpayroll.navigateToProcessCenter();
			RefNumGenerated = guidedpayroll.fillUpdateEmployeeContribution(testdata.get("firstAmount"),
					testdata.get("secondAmount"), testdata.get("date"), testdata.get("contributionamount"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", "Assertion Failed!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@AfterClass
	public void cleanupSessions() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}
}
