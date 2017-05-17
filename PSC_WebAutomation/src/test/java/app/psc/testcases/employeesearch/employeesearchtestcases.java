package app.psc.testcases.employeesearch;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.homepage.HomePage;
import pageobjects.jumppage.JumpPage;
import pageobjects.login.LoginPage;
import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.DB;
import lib.Stock;
import lib.Web;
import core.framework.Globals;
import framework.util.CommonLib;

public class employeesearchtestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public EmployeeSearch employeesearch;
	String actualErrorMessage = "";
	ResultSet resultset;
	HomePage homePage;
	CommonLib commonLib;
	LoginPage loginPage;
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
	 * This test case verifies the search results for valid SSN input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC001_Search_Employee_For_Valid_SSN(int itr,
			Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid SSN",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}			
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
	 * This test case verifies the search results for invalid SSN input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC002_Search_Employee_For_Invalid_SSN(int itr,
			Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {

				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid SSN",
						"Search results are displayed correctly", false);
				Thread.sleep(2000);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid SSN",
						"Search results are not displayed correctly", true);
			}
			Reporter.finalizeTCReport();
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
	/**
	 * This test case verifies the search results for valid Name input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC003_Search_Employee_For_Valid_Name_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid SSN",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}
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
	 * This test case verifies the search results are not displayed for invalid Name input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC004_Search_Employee_For_Invalid_Name_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {

				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid Name",
						"Search results are displayed correctly", false);
				Thread.sleep(2000);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid Name",
						"Search results are not displayed correctly", false);
			}
			Reporter.finalizeTCReport();
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
	 * This test case verifies the search results for valid EmployeeID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC005_Search_Employee_For_Valid_EmployeeID_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid Name",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid Name",
						"Search results are not displayed correctly", true);
			}
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
	 * This test case verifies the search results are not displayed for invalid Employee ID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC006_Search_Employee_For_Invalid_EmployeeID_Input(int itr,
			Map<String, String> testdata) {		
		try {	
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are displayed correctly", false);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are not displayed correctly", true);
			}
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
	 * This test case verifies the search results are not displayed for plans with divisions
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC07_Verify_employee_details_displayed_for_plans_with_divisions(
			int itr, Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for plans having divisions", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithDivisions"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.navigateToEmployeeTab();
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

	/*
	 * ---- Regression Test cases starts from here -------
	 * =========================================================================
	 * ====
	 */
	/**
	 * This testcase validates the search results for valid EmployeeID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC08_Verify_employee_details_displayed_for_valid_participant_id(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for ParticipantID input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("ParticipantId"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid Name",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid Name",
						"Search results are not displayed correctly", true);
			}
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
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}/**
	 * This testcase validates the search results for invalid EmployeeID input
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void TC09_Verify_employee_details_displayed_for_invalid_participant_id(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Negative  flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("ParticipantId"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are displayed correctly", false);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are not displayed correctly", true);
			}
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
	 * This testcase validates the search results for SSN Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC010_Verify_the_Search_results_for_SSN_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
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
	 * This testcase validates the search results for Name Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC011_Verify_the_Search_results_for_Name_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
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
	 * This testcase validates the search results for EmployeeID Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC012_Verify_the_Search_results_for_EmployeeID_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
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
 * This testcase validates the search results for participant ID Doesn't contain duplicate value
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC013_Verify_the_Search_results_for_pptID_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for pptID Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("Participant ID"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
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
 * This testcase validates the search results for plans with Division Doesn't contain duplicate value
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC014_Verify_the_Search_results_for_Plans_with_Div_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for plans with Division Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();	
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithDivisions"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
			employeesearch.navigateToEmployeeTab();
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
	 * <pre>This testcase validates the search results for Plans that are having single division</pre>
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC015_Check_For_Plans_With_one_division(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Plans that are having only one division", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithSingleDivision"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.dismissErrorBox();
			employeesearch.navigateToEmployeeTab();
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
 * <pre>This testcase validates the search results for Plans that are having multiple division</pre>
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC016_Check_For_Plans_With_multiple_divisions(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Plans that are having multiple division", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithMorethanSingleDivision"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.dismissErrorBox();
			employeesearch.navigateToEmployeeTab();
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
	 * <pre>This testcase validates general employment informations</pre>
	 * @param itr
	 * @param testdata
	 */
		@Test(dataProvider = "setData")
		public void TC017_Employment_Info_Verification(int itr,
				Map<String, String> testdata) {		
			try {
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
				Reporter.logEvent(Status.INFO, "Testcase Description",
						"This testcase validates general employment informations", false);
				employeesearch = new EmployeeSearch().get();
				CommonLib.switchToDefaultPlan();
				employeesearch.navigateToEmployeeTab();
				employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());//need to fetch employee from database
				employeesearch.verifyEmploymentInfoANDLabels();
				employeesearch.updateEmploymentInfoModalWindow();
				
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
		 * <pre>This testcase validates general contact informations</pre>
		 * @param itr
		 * @param testdata
		 */
			@Test(dataProvider = "setData")
			public void TC018_Employee_Contact_Info_Verification(int itr,
					Map<String, String> testdata) {		
				try {
					Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
					Reporter.logEvent(Status.INFO, "Testcase Description",
							"This testcase validates general employment informations", false);
					employeesearch = new EmployeeSearch().get();
					CommonLib.switchToDefaultPlan();
					employeesearch.navigateToEmployeeTab();
					employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());
					employeesearch.contactInFoSectionValidation();
					employeesearch.contactInFoLabelValidation();
					employeesearch.contactInFoValidationModalWindow();
					employeesearch.validateContactInfoWithDB();
					
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
			 * <pre>This testcase validates beneficiary details with DB</pre>
			 * @param itr
			 * @param testdata
			 */
				@Test(dataProvider = "setData")
				public void TC019_Verify_Beneficiary_Info(int itr,
						Map<String, String> testdata) {		
					try {
						Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
						Reporter.logEvent(Status.INFO, "Testcase Description",
								"This testcase validates general employment informations", false);
						employeesearch = new EmployeeSearch().get();
						CommonLib.switchToDefaultPlan();
						employeesearch.navigateToEmployeeTab();
						employeesearch.str1 = employeesearch.getBeneficiaryEmployeeSSN();
						employeesearch.searchEmployeeBySSN(employeesearch.str1[0]);
						employeesearch.navigateToEmployeeOverViewPage();
						employeesearch.navigateBeneficiaryPage();
						employeesearch.validateBeneficiaryWithDB();
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
				 * <pre>This testcase validates beneficiary details with DB</pre>
				 * @param itr
				 * @param testdata
				 */
					@Test(dataProvider = "setData")
					public void TC020_Recently_Viewed_Employee(int itr,
							Map<String, String> testdata) {		
						try {
							Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
							Reporter.logEvent(Status.INFO, "Testcase Description",
									"This testcase validates general employment informations", false);
							employeesearch = new EmployeeSearch().get();
							CommonLib.switchToDefaultPlan();
							employeesearch.navigateToEmployeeTab();
							employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());
							employeesearch.navigateToEmployeeOverViewPage();
							employeesearch.Verify_Recently_Viewed_Employee();
							
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
					 * <pre>This testcase to update salary details and validate with DB</pre>
					 * @param itr
					 * @param testdata
					 */
						@Test(dataProvider = "setData")
						public void TC021_Salary_View_And_Update(int itr,
								Map<String, String> testdata) {		
							try {
								Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
								Reporter.logEvent(Status.INFO, "Testcase Description",
										"This testcase validates general employment informations", false);
								employeesearch = new EmployeeSearch().get();
								homePage = new HomePage();
								homePage.searchPlanWithIdOrName(employeesearch.getSSNAndGaIdForSalaryEmp()[0]);
								employeesearch.navigateToEmployeeTab();
								employeesearch.searchEmployeeBySSN(employeesearch.individual[2]);
								employeesearch.navigateToEmployeeOverViewPage();
								employeesearch.navigateToEmpDetailPage();
								employeesearch.verifySalarySection();
								employeesearch.updateSalaryinfo();
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
						 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
						 * @param itr
						 * @param testdata
						 */
							@Test(dataProvider = "setData")
							public void TC022_Paycheck_contribution_View(int itr,
									Map<String, String> testdata) {		
								try {
									Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
									Reporter.logEvent(Status.INFO, "Testcase Description",
											"This testcase validates general employment informations", false);
									employeesearch = new EmployeeSearch().get();
									employeesearch.searchEmployeeBySSNAllPlans(employeesearch.ssnOfPayCheckContribution());
									employeesearch.navigateToEmployeeOverViewPage();
									employeesearch.navigateToEmpDetailPage();
									employeesearch.verify_Paycheck_ContributionSection();
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
							 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
							 * @param itr
							 * @param testdata
							 */
								@Test(dataProvider = "setData")
								public void TC023_EnrolmmentAndEligibility(int itr,
										Map<String, String> testdata) {		
									try {
										Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
										Reporter.logEvent(Status.INFO, "Testcase Description",
												"This testcase validates general employment informations", false);
										employeesearch = new EmployeeSearch().get();
										employeesearch.SSNOfEmployeeWithTermDate();
										employeesearch.searchEmployeeBySSNAllPlans(employeesearch.terminatedEmp[0]);
										employeesearch.navigateToEmployeeOverViewPage();
										employeesearch.navigateToEmpDetailPage();
										employeesearch.beforeRehiring();
										employeesearch.rehireEmployee();
										employeesearch.AfterRehiring();
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
								 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
								 * @param itr
								 * @param testdata
								 */
									@Test(dataProvider = "setData")
									public void TC024_Verify_Employee_Overview_Screen_Elements(int itr,
											Map<String, String> testdata) {		
										try {
											Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
											Reporter.logEvent(Status.INFO, "Testcase Description",
													"This testcase validates general employment informations", false);
											employeesearch = new EmployeeSearch().get();
											homePage = new HomePage();
											homePage.searchPlanWithIdOrName(employeesearch.findPlanForUser(Stock.getTestQuery("queryTofindPlansForNextGen"),
													Stock.GetParameterValue("username")));
											employeesearch.navigateToEmployeeTab();
											employeesearch.searchEmployeeByEmployeeId("");
											employeesearch.navigateToEmployeeOverViewPage();
											employeesearch.verifyOverviewScreenElements();
											
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
									 * <pre>This testcase to verify Employee overview plan list view section</pre>
									 * @param itr
									 * @param testdata
									 */
										@Test(dataProvider = "setData")
										public void TC025_Verify_Employee_Overview_PlanList_Balance_Section(int itr,
												Map<String, String> testdata) {		
											try {
												Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
												Reporter.logEvent(Status.INFO, "Testcase Description",
														"This testcase validates general employment informations", false);
												employeesearch = new EmployeeSearch().get();
												employeesearch.searchEmployeeBySSNAllPlans(employeesearch.getMultiPlanEmployee());
												employeesearch.navigateToEmployeeOverViewPage();
												employeesearch.verifyColumnsOfPlanListsection();
												employeesearch.verifySelectedPlanHeaderAndHighlightedPlan();
												employeesearch.verifyTotalBalance();
												if(employeesearch.verifyViewPageForAPlanHavingBalance())
												{
													employeesearch.verifyInvestmentSection();
													employeesearch.verifyCalendarIcon();
													employeesearch.returnToEmployeeOverview();
												}else
												{
													Reporter.logEvent(Status.INFO, "Check if balance is displayed for any of the plan for employee enrolled with more than one plan.", "Balance is not displayed for any of the plan", true);
												}
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
 * <pre>This test case verifies employee basic information on overview page and update few fields and verifies the changes.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC026_Verify_Employee_Basic_Info(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"This testcase validates general employment informations", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.getQNTTypeIndividuals();
		Thread.sleep(3000);
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(0));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(1));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(2));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.verifyBasicInfoModalWindow();
		employeesearch.editBasicInfoAndSave();
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}


/**
 * <pre>This test case verifies employee subset section and subset history section for employee who are assigned with plans with subset
 * and employee with no subset section.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC027_Verify_Subset_History_Section(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"This testcase validates Subset section of employee.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.getEmployeeForUser(
				Stock.getTestQuery("getEmployeeWithSubSetSection"),
				Stock.GetParameterValue("username"));
		String ssn = "";
		ssn = employeesearch.getEmployeeSSNFromResultSet(resultset);
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		if(employeesearch.isSubSetSectionDisplayed())
		{
			Reporter.logEvent(Status.PASS,"Check Employee subset information section is displayed.", "Employee subset information is displayed.", false);
			employeesearch.verifySubSetColumnHeaders();
			employeesearch.verifySubsetHistorySection();
			employeesearch.checkSubSetHistDataIsDisplayed();
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Check Employee subset information section is displayed.", "Employee subset information is not displayed.", true);
		}
		resultset = employeesearch.getEmployeeForUser(
				Stock.getTestQuery("getEmployeeWithoutSubSetSection"),
				Stock.GetParameterValue("username"));
		ssn = employeesearch.getEmployeeSSNFromResultSet(resultset);
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		if(employeesearch.isSubSetSectionDisplayed())
			Reporter.logEvent(Status.FAIL,"Check Employee subset information section is not displayed.", "Employee subset information is displayed.", true);
		else
			Reporter.logEvent(Status.PASS,"Check Employee subset information section is not displayed.", "Employee subset information is not displayed.", false);
		
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}


/**
 * <pre>This testcase validates employee switch functionality through recently viewed features.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC028_Recently_Viewed_Switch_Participant_Reg(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"This testcase validates employee switch functionality through recently viewed features.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectPlanForUser(
				Stock.getTestQuery("queryTofindPlansForNextGen"),
				Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.navigateToEmployeeTab();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.switchToRecentlyViewedEmp();
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}


/**
 * <pre>This testcase validates employee enrollment and eligibility edit and save,edit and cancel features.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC029_Edit_Enrollment_And_Eligibility(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objective of this test case is to edit Enrollment and eligibility section and update and save and edit"
		+ "Enrollment and eligibility section,update and cancel to make sure save/cancel works as expected.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectEmployeesForUser(Stock.getTestQuery("getEmployeeWithTermDate"),
				Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		employeesearch.editEnrollmentAndEligibilityAndSave();
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}


/**
 * <pre>This testcase validate Account balance section for a Participant.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC30_Account_balance_Tab(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objective of this test case is to validate Account balance section for a Participant.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectEmployeesForUser(Stock.getTestQuery("getPartWithAccountBalance"),
				Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.validateAccountBalanceScreen_0())
		{
			employeesearch.validateAccountBalanceScreen_1();
			employeesearch.ChangeDateAndVerifyData();
		}
		else
		{
			Reporter.logEvent(Status.INFO,"Check for participanct with account balance.", "No particiapnt is found"
					+ " with account balance.", true);
		}
		
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}

/**
 * <pre>This testcase validates 'Data set error must be displayed if access to participant Web is disable via PSC'
 *  and this test case also validates Employee Web button is not displayed if PSCPAE txn_code is not associated with logged in user.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC31_Disable_PAE_then_renable_PAE(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objective of this test case is to validate<br>1.Data set error must be displayed if access to participant Web is disable via PSC."
		+ "<br>2.Employee Web button is displayed based on respective transaction code 'PSCPAE'.", false);
		employeesearch = new EmployeeSearch().get();
		if(employeesearch.disablePAEURL())
		{
			employeesearch.searchEmployeeByName("");
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.verifyDataSetErrorForDisabledPAEURL();
			employeesearch.enablePAEURL();
		}
		if(employeesearch.validateEmployeeWebButton())
		{
			Reporter.logEvent(Status.PASS, "Validate Employee Web bottin is displayed before deleting 'PSCPAE' txn_code for all user classes, logged in user is associated with.", "Employee web button is"
					+ " displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Validate Employee Web bottin is displayed before deleting 'PSCPAE' txn_code for all user classes, logged in user is associated with.", "Employee web button is"
					+ " not displayed.", true);
		}
		employeesearch.deletePSCPAETxnCodeFromDB();
		employeesearch.logoutFromApplication();
		LoginPage loginPage = new LoginPage();
		JumpPage jumpPage = new JumpPage();
		loginPage.submitLoginCredentials(
				new String[]{Stock.GetParameterValue("username"),Stock.GetParameterValue("password")});
		jumpPage.ClickOnJumpPageURL();
		employeesearch.navigateToEmployeeTab();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.validateEmployeeWebButton())
		{
			Reporter.logEvent(Status.FAIL, "Execute the query '"+Stock.getTestQuery("deletePSCPAETxnCode")[1]+"'.", "Employee web button is"
					+ " displayed.", true);
		}
		else
		{
			Reporter.logEvent(Status.PASS, "Execute the query '"+Stock.getTestQuery("deletePSCPAETxnCode")[1]+"'.", "Employee web button is"
					+ " not displayed.", false);
		}
		employeesearch.insertPSCPAETxnCode();
		
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}



/**
 * <pre>This test case validates the menu items for TRSFlex plan and for Non-TRSFlex plan.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC32_Supress_Menu_Items_for_TRS_Flex(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objective of this test case is to validate menu items 'Process Center and Compliance' must be Supressed for TRSFlex.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getTRXFlexPlan"), Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		if(employeesearch.validateComplianceAndProCenterTabForTRSFlex())
			Reporter.logEvent(Status.FAIL,"Search for TRS-Flex plan and navigate to plan page.Observe the"
					+ " Process center and compliance menu tabs are supressed.", "Process center and compliance menu tabs are"
							+ " not supressed.", true);
		else
			Reporter.logEvent(Status.PASS,"Search for TRS-Flex plan and navigate to plan page.Observe the"
					+ " Process center and compliance menu tabs are supressed.", "Process center and compliance menu tabs are"
							+ " supressed.", false);
		employeesearch.validationsForTRSFlexPlan();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getNonTRXFlexPlan"), Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		if(employeesearch.validateComplianceAndProCenterTabForTRSFlex())
			Reporter.logEvent(Status.PASS,"Search for Non TRS-Flex plan and navigate to plan page.Observe the"
					+ " Process center and compliance menu tabs are displayed.", "Process center and compliance menu tabs are"
							+ " displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Search for Non TRS-Flex plan and navigate to plan page.Observe the"
					+ " Process center and compliance menu tabs are displayed.", "Process center and compliance menu tabs are"
							+ " not displayed.", true);
		employeesearch.validationsForNonTRSFlexPlan();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getTRXFlexPlan"), Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();//add TRS-Flex plan employee to recently viewed list 
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getNonTRXFlexPlan"), Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();//add non TRS-Flex plan employee to recently viewed list
		employeesearch.switchEmployeeAndValidateTopMenu();
		
		
		
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
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}
	
	
/**
 * <pre>This testcase validates Account detail and employee detail section's elements for TRSFlex and non TRSFlex plans.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC33_Suppress_Account_Detail_elements_TRS_NonTRS(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objective of this test case is to validate account detail and employee detail elements for TRSFlex and non TRSFlex plans.", false);
		employeesearch = new EmployeeSearch().get();	
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getTRXFlexPlan"), 
				Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.searchEmployeeByName("");
		//TRSFlex plan employee added to recently viewed employee
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateAccountDetailSectionsTRSFlexPlan();
		employeesearch.validateEmployeeDetailSectionsTRSFlexPlan();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getNonTRXFlexPlan"), 
				Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.searchEmployeeByName("");
		//NonTRSFlex plan employee added to recently viewed employee
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateAccountDetailSectionsNonTRSFlexPlan();
		employeesearch.validateEmployeeDetailSectionsNonTRSFlexPlan();
		employeesearch.validateEmpDetailAccDetail_SwitchEmploye();
	
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * <pre>1.This test case validates the Investments sub section inside Account Detail tab(Columns).<br>
 	2.This test case validates order of investment information and max number of investment records on overview page.<br>
 	3.This test case validates the adding new allocation.<br>
 	4.This test case validates the 'Add/Change Allocations button' enabled/disabled based on transaction codes assigned to user.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC34_SIT_PSC_Accountdetail_Allocations_Reg(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"The objectives of this test case is as below:"
		+ "1.This test case validates the Investments sub section inside Account Detail tab(Columns)."
		+"2.This test case validates order of investment information and max number of investment records on overview page(if max number records exist)."
		+"3.This test case validates the adding new allocation."
		+"4.This test case validates the 'Add/Change Allocations button' enabled/disabled based on transaction codes assigned to user.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectEmployeesForUser(Stock.getTestQuery("getInvetmentAllocationEmp"),
				Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.validateInvestmentsSectionElements())
		{
			employeesearch.validateInvestmentsPercentOrderAndMaxRecords();
			if(employeesearch.validateAddAllocationBtnIfUserHasAccess())
			{
				employeesearch.addAllocation();
			}
			employeesearch.validateNoAllocation();
		}
		employeesearch.deleteAdd_ChangeAllocationTxn_Code();
		employeesearch.logoutFromApplication();
		LoginPage loginPage = new LoginPage();
		JumpPage jumpPage = new JumpPage();
		loginPage.submitLoginCredentials(
				new String[]{Stock.GetParameterValue("username"),Stock.GetParameterValue("password")});
		jumpPage.ClickOnJumpPageURL();
		employeesearch.navigateToEmployeeTab();
		resultset = employeesearch.selectEmployeesForUser(Stock.getTestQuery("getInvetmentAllocationEmp"),
				Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateAdd_ChangeAllocIfNoAccess();
		employeesearch.insertESCCPATxnCode();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}	


/**
 * <pre></pre>
 * @Objective This test case validates the Statement tab and its filter functionality.
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC35_SIT_PSC_Statements_Tab(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objectives of this test case is as below:"
				+ "1.Validate the Statement tab UI validations.2)Sorting of Statement records.3)Filter of statements based on "
				+ "selected category.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("empSSN"));
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.navigateToStatementTab())
		{
			employeesearch.validateStatementTabHeaderAndDateSorting();
			employeesearch.validateStatementFilter();
		}
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}

/**
 * <pre></pre>
 * @Objective This test case validates the Enrollment & Eligibility section is not displayed for NQ plans.
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC36_SIT_PSC_Employee_Overview_DB_plans_Enrollment_Elligibility_Not_displayed(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objectives of this test case is to validate"
				+ " Enrollment & Eligibility section is not displayed for NQ plans.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("GetNQPlans"),Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.navigateToEmployeeTab();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		if(!employeesearch.isEnrollmentEligiDisplayed())
			Reporter.logEvent(Status.PASS, "Validate Enrollment and Eligibility section"
					+ " is not displayed for NQ plans.", "Enrollment and Eligibility section is not displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate Enrollment and Eligibility section"
					+ " is not displayed for NQ plans.", "Enrollment and Eligibility section is not displayed.", true);
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("GetDBPlans"),Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.navigateToEmployeeTab();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		if(!employeesearch.isEnrollmentEligiDisplayed())
			Reporter.logEvent(Status.PASS, "Validate Enrollment and Eligibility section"
					+ " is not displayed for DB plans.", "Enrollment and Eligibility section is not displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate Enrollment and Eligibility section"
					+ " is not displayed for DB plans.", "Enrollment and Eligibility section is not displayed.", true);
		employeesearch.logoutFromApplication();
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}

/**
 * <pre></pre>
 * @Objective This test case validates the new fields in Employment Information section on Employee overview page.
 * @param itr
 * @param testdata
 */
/*@Test(dataProvider = "setData")
public void TC37_SIT_PSC_Employment_Information_new_fileds(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates"
				+ " new fileds on Employment information section and validates their values against DB.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = DB.executeQuery(Stock.getTestQuery("getEmploymentPPT")[0],Stock.getTestQuery("getEmploymentPPT")[1],""+
				"K_"+Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateNewEmploymentFileds();
		
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}*/

/**
 * <pre>This test case validates the tool-tip elements in recently viewed section.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC37_SIT_PSC_Recentlyviewed_MouseOver(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objectives of this test case is to validate"
				+ " Tool-tip elements in recently viewed employee section.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.recentlyViwedMouseHoverElementsValidation();
		employeesearch.logoutFromApplication();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}

/**
 * <pre></pre>
 * @Objective This test case validates the Fees detail on Employee overview page.
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC38_SIT_PSC_Accountdetail_Fees_Reg(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objectives of this test case is to validate"
				+ "Fees section elements on employee overview page.", false);
		employeesearch = new EmployeeSearch().get();
		/*resultset = DB.executeQuery(Stock.getTestQuery("getPastThreeMonthFeesData")[0],
				Stock.getTestQuery("getPastThreeMonthFeesData")[1],""
				+"K_"+Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);*/
		String ssn = Stock.GetParameterValue("SSN");
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.validatePastThreeMonthFees())
		{
			employeesearch.validateMaxNumberOfFeesRecords();
			if(employeesearch.validateFeesModalWindow())
			employeesearch.updateDateRengeAndValidate();
		}
		resultset = DB.executeQuery(Stock.getTestQuery("getNoPastThreeMonthFees")[0],
				Stock.getTestQuery("getNoPastThreeMonthFees")[1],"K_"+Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateNoPastThreeMonthDataMessage();
		employeesearch.logoutFromApplication();
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to verify Employee Vesting information
 *box displayed under Account detail tab with Vesting info.
 *Note: This Test case deals with validation of elements inside vesting section.It does not validate data specific to a participant.</pre>
 * @Objective This test case validates the Fees detail on Employee overview page.
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC39_SIT_PSC_Accountdetail_Vesting_Reg(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to validate"
				+ " Vesting section with vesting info under account detail tab.", false);
		employeesearch = new EmployeeSearch().get();
		String ssn = Stock.GetParameterValue("SSN");
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateVestingSection_1();
		employeesearch.validateVestingSection_2();
		employeesearch.validateVestingModalWindowSection_1();
		employeesearch.validateVestingModalWindowSection_2();
		employeesearch.logoutFromApplication();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to verify Employee Loan information
 *box displayed under Account detail tab.
 *Note: This Test case deals with validation of elements inside Loan section
 *and navigation of loan pages when links are clicked.</pre>
 * @Objective This test case validates the Loan detail on Employee overview page.
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC40_SIT_PSC_Accountdetail_Loans_Reg(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to validate"
				+ " Loan section info under account detail tab.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchPPTWithLoan();
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateLoanSection_1();
		employeesearch.compareLoanDataWithDB();
		employeesearch.validateLoanEffDateOrder();
		employeesearch.validateLoanDetailPage_1();
		employeesearch.validateLoanDetailPage_2();
		employeesearch.validateEmpLoanAccountInformation();
		employeesearch.validateDaysLateAndPayRemainingField();
		employeesearch.logoutFromApplication();
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to validate Employee YTD contributions sum displayed on YTD column,
 * Paycheck contributions elements displayed in Paycheck contributions box under Account detail tab.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC41_SIT_PSC_Accountdetail_Paycheck_Contribution_YTD(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to validate Employee "
				+"Paycheck contributions screen elements and valiate contribution type record on history window.", false);
		employeesearch = new EmployeeSearch().get();
		/*employeesearch.selectEmployeesForUser(Stock.getTestQuery("PaycheckContriSSNWithYTD"),
				Stock.GetParameterValue("username"));*/
		String ssn = Stock.GetParameterValue("SSN");
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToAccountDetailPage();
		employeesearch.validatePaycheckContTitle();
		employeesearch.validatePaycheckContTabs();
		employeesearch.validateYTDTabDataWithDB();
		employeesearch.validateTransactionHistory();
		employeesearch.validateMoreButtonPage();
		employeesearch.validateNoDataScenarioPayChkContribution();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to validate page navigation of Paycheck contribution history window.</pre>
 * @param itr
 * @param testdata
 * @Date 11th-May-2017
 */
@Test(dataProvider = "setData")
public void TC42_Paycheck_Contribution_History(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate page navigation of Paycheck contribution history window.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeBySSN("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		employeesearch.validateNavigationWhenClosingHisWindow();
		employeesearch.logoutFromApplication();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to validate remember employee search selection using browser cookie.</pre>
 * @param itr
 * @param testdata
 * @Date 11th-May-2017
 */
@Test(dataProvider = "setData")
public void TC43_SIT_PSC_User_Remember_Search_Employee_Selection(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate page navigation of Paycheck contribution history window.", false);
		employeesearch = new EmployeeSearch().get();
		//employeesearch.navigateToEmployeeTab();
		employeesearch.validateDefaultEmpSearchOption();
		employeesearch.validateRememberOfEmpSearchOption();
		employeesearch.validateRememberOfEmpSearchOptionWhenPlanSwitch();
		employeesearch.logoutFromApplication();
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to validate fields with DB for enrollment and eligibility section.</pre>
 * @param itr
 * @param testdata
 * @Date 11th-May-2017
 */
@Test(dataProvider = "setData")
public void TC_44_Enroll_And_Eligibility_Fields_Type_Validations(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is"
				+ " to validate fields with DB for enrollment and eligibility section.", false);
		String ssn="";
		employeesearch = new EmployeeSearch().get();
		resultset = DB.executeQuery(Stock.getTestQuery("getPPtWithEnrollAndEligDetails")[0],
				Stock.getTestQuery("getPPtWithEnrollAndEligDetails")[1],"K_"+Stock.GetParameterValue("username"));
		while(resultset.next()){
		ssn = resultset.getString("SSN");break;}
		System.out.println("SSN is:"+ssn);
		Map<String,String> dbMap =employeesearch.getEnrollAndEligDataFromDB(ssn);
		employeesearch.searchEmployeeBySSNAllPlans(ssn);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		Map<String,String> uiMap = employeesearch.getEnrollAndEligDataFromUI();
		if(dbMap.equals(uiMap))
			Reporter.logEvent(Status.PASS,"Compare UI data from DB for enroll and eligibility section.",""
					+"DB data:"+dbMap+" and UI data:"+uiMap, false);
		else
			Reporter.logEvent(Status.FAIL,"Compare UI data from DB for enroll and eligibility section.",""
					+"DB data:"+dbMap+" and UI data:"+uiMap, true);
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}



/**
 * @author smykjn
 * <pre>The objective of this test case is to validate Add employee functionality for Autosite plan
 * with participant enrolled into managed account.</pre>
 * @param itr
 * @param testdata
 * @Date 11th-May-2017
 */
@Test(dataProvider = "setData")
public void TC_45_SIT_PSC_Add_Employee_Autosite_Plan_Display_Angular_Page(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate Add employee functionality for Autosite plan.", false);
		employeesearch = new EmployeeSearch().get();
		/*resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getAutoSuitePlan"),""
				+Stock.GetParameterValue("username"));*/
		String planNumber = Stock.GetParameterValue("PlanNumber");
		employeesearch.selectPlanFromResultset(planNumber);
		employeesearch.navigateToAddEmpPage();
		if(employeesearch.fillSSNForAddNewEmp())
		{
			employeesearch.fillNewEmpBasicInfoInvalid();
			if(employeesearch.fillNewEmpBasicInfoValid())
			{
				employeesearch.fillEligibilityInfoNegativeFlow();
				if(employeesearch.fillEligibilityInfoPositiveFlow())
				{
					if(employeesearch.enterIncomeData())
					{
						if(employeesearch.addNewEmploymentInfo())
						{
							employeesearch.addSubSetInfo();
						}
						employeesearch.addManageAccountDetailsWithManageAccntEnroll();
					}
				}
			}
		}
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
}
}


/**
 * @author smykjn
 * <pre>The objective of this test case is to validate Add employee functionality for Autosite plan
 * without participant enrollment into managed account.</pre>
 * @param itr
 * @param testdata
 * @Date 11th-May-2017
 */
@Test(dataProvider = "setData")
public void TC_46_SIT_PSC_Add_Employee_Autosite_Plan_Display_Angular_Page(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate Add employee functionality for Autosite plan.", false);
		employeesearch = new EmployeeSearch().get();
		/*resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getAutoSuitePlan"),""
				+Stock.GetParameterValue("username"));*/
		String planNumber = Stock.GetParameterValue("PlanNumber");
		employeesearch.selectPlanFromResultset(planNumber);
		employeesearch.navigateToAddEmpPage();
		if(employeesearch.fillSSNForAddNewEmp())
		{
			employeesearch.fillNewEmpBasicInfoInvalid();
			if(employeesearch.fillNewEmpBasicInfoValid())
			{
				employeesearch.fillEligibilityInfoNegativeFlow();
				if(employeesearch.fillEligibilityInfoPositiveFlow())
				{
					if(employeesearch.enterIncomeData())
					{
						if(employeesearch.addNewEmploymentInfo())
						{
							employeesearch.addSubSetInfo();
						}
						employeesearch.addManageAccountDetailsWithOutManageAccntEnroll();
					}
				}
			}
		}
		
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
	Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
} finally {
		try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
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
