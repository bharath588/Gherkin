package app.psc.testcases.employeesearch;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
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
	 *//*
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
		}*/
	
		
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
										"This testcase updates the salary information of an employee.", false);
								employeesearch = new EmployeeSearch().get();
								homePage = new HomePage();
								String planId = employeesearch.getSSNAndGaIdForSalaryEmp()[0];
								if(Stock.getConfigParam("DataType").equals("NonApple"))
								homePage.searchPlanWithIdOrName(planId);
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
											"This testcase validates Paycheck contribution section of an employee.", false);
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
							 * <pre></pre>
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
 * <pre>This test case verifies employee basic information on 
 * overview page and update few fields and verifies the changes.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC026_Verify_Employee_Basic_Info(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"This test case verifies employee basic information on "
		+ "overview page and update few fields and verifies the changes", false);
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
		String ssn = employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		employeesearch =	new EmployeeSearch();
		employeesearch.editEnrollmentAndEligibilityAndSave();
		Map<String,String> dbMap =employeesearch.getEnrollAndEligDataFromDB(ssn);
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
		"The objective of this test case is to validate menu items"
		+ " 'Process Center and Compliance' must be Supressed for TRSFlex.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getTRXFlexPlan"), 
				Stock.GetParameterValue("username"));
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
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getNonTRXFlexPlan"), 
				Stock.GetParameterValue("username"));
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
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getTRXFlexPlan"),
				Stock.GetParameterValue("username"));
		employeesearch.selectPlanFromResultset(resultset);
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();//add TRS-Flex plan employee to recently viewed list 
		resultset = employeesearch.selectPlanForUser(Stock.getTestQuery("getNonTRXFlexPlan"),
				Stock.GetParameterValue("username"));
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
		if(Stock.GetParameterValue("DataType").equals("NonApple"))
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
		homePage.navigateToHomePage();
		
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
 * <pre>.</pre>
 * @param itr
 * @param testdata
 * @Date 7-July-2017
 */
@Test(dataProvider = "setData")
public void TC_47_Search_Emp_Locked_Plan(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate Add employee functionality for Autosite plan.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN"),false);
		employeesearch.validateSSNForLockedPlan();
		
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
 * <pre>This test case validates employee info input fields in the process of adding new employee.</pre>
 * @param itr
 * @param testdata
 * @Date 5-July-2017
 */
@Test(dataProvider = "setData")
public void TC_51_Add_New_Employee_View_Page(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate employee info input fields in the process of adding new employee", false);
		employeesearch = new EmployeeSearch().get();
		String ga_id = "";
		resultset = DB.executeQuery(Stock.getTestQuery("IPBaddNewEmployeePreCondition")[0],
				Stock.getTestQuery("IPBaddNewEmployeePreCondition")[1],"K_"+Stock.GetParameterValue("username"));
		while(resultset.next()){
			ga_id = resultset.getString("ID");
			break;
		}
		homePage = new HomePage();
		homePage.searchPlanWithIdOrName(ga_id);
		employeesearch.navigateToAddEmpPage();
		employeesearch.validateEmpInfoLabelsWhileaddingNewEmp();
		homePage.navigateToHomePage();
		homePage.logoutPSC();
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
 * <pre>This test case validates that user is taken to PPT Web for each plan employee is assigned with.</pre>
 * @param itr
 * @param testdata
 * @Date 5-July-2017
 */
@Test(dataProvider = "setData")
public void TC_50_SSN_Multiple_Plan_PAE(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate that user is taken to PPT Web for each plan employee is assigned with if"
				+ " plan is set up properly.", false);
		employeesearch = new EmployeeSearch().get();
		homePage = new HomePage();
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN"));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.verifyAccountBalScreenByMoneySourceAndInvestments();
		employeesearch.verifyEmployeeWebButtonFunctionality();
		homePage.navigateToHomePage();
		homePage.logoutPSC();
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
 * <pre>This test case validates message when there is no record in work_recently_viewed_part table.</pre>
 * @param itr
 * @param testdata
 * @Date 5-July-2017
 */
@Test(dataProvider = "setData")
public void TC_56_Info_Message_New_User(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate message when there is no record in work_recently_viewed_part table.", false);
		resultset = DB.executeQuery(Stock.getTestQuery("DeleteRecentlyViewedRecords")[0],
				Stock.getTestQuery("DeleteRecentlyViewedRecords")[1],"K_"+Stock.GetParameterValue("username"));
		employeesearch = new EmployeeSearch().get();
		employeesearch.validateRecentlyViewedMsgWhenNoData();
		homePage = new HomePage();
		homePage.navigateToHomePage();
		homePage.logoutPSC();
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
 * <pre>This test case validates Employee active asset allocation screen.</pre>
 * @param itr
 * @param testdata
 * @Date 7-July-2017
 */
@Test(dataProvider = "setData")
public void TC_54_Employee_Overview_With_Active_Models(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate asset allocation screen and total field.", false);
		employeesearch = new EmployeeSearch().get();
		homePage = new HomePage();
		resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithActiveAlloModel")[0],
			Stock.getTestQuery("getPlanWithActiveAlloModel")[1],"K_"+Stock.GetParameterValue("username"));
			String plan = employeesearch.selectPlanFromResultset(resultset);
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNforActiveAllocation")[0],
				Stock.getTestQuery("getSSNforActiveAllocation")[1],plan);
			//employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN"));
			employeesearch.selectEmployeeFromResultSet(resultset);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToAccountDetailPage();
			if(employeesearch.navigateToInvestmentTab()){
				Reporter.logEvent(Status.PASS,"Navigate to investment tab.","Investment tab is displayed.", false);
				employeesearch.validateAssetAllocationPage();
				employeesearch.addAllocationAndVerify();
			}
			else{
				Reporter.logEvent(Status.FAIL,"Navigate to investment tab.","Investment tab is not displayed.", true);
			}
			homePage.logoutPSC();
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
 * <pre>This test case validates employee ability to add/change allocation based on txn codes assigned/removed.</pre>
 * @param itr
 * @param testdata
 * @Date 7-July-2017
 */
@Test(dataProvider = "setData")
public void TC_53_Participant_Investments_Tab(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validate employee ability to add/change allocation based on txn codes assigned/removed.", false);
		boolean ispresent = false;
		resultset = DB.executeQuery(Stock.getTestQuery("checkESCPALTxnCode")[0],
				Stock.getTestQuery("checkESCPALTxnCode")[1],""+
				"K_"+Stock.GetParameterValue("username"));
		if(CommonLib.isTxnCodesPresent(resultset,Stock.GetParameterValue("TXN_CODE"))){
			Reporter.logEvent(Status.INFO,"Check if user is assigned with ESCPAL txn code.","User is assigned with ESCPAL"
					+ " txn code.", false);ispresent=true;}
		else{
			Reporter.logEvent(Status.WARNING,"Check if user is assigned with ESCPAL txn code.","User is not assigned with ESCPAL"
					+ " txn code.please insert ESCPAL txn code to process with this test case.", false);
			ispresent =false;
			CommonLib.insertTxnCode(Stock.GetParameterValue("TXN_CODE"),Stock.GetParameterValue("uscsId"));
			resultset = DB.executeQuery(Stock.getTestQuery("checkESCPALTxnCode")[0],
					Stock.getTestQuery("checkESCPALTxnCode")[1],""+
							"K_"+Stock.GetParameterValue("username"));
			if(CommonLib.isTxnCodesPresent(resultset,Stock.GetParameterValue("TXN_CODE").split(","))){
				Reporter.logEvent(Status.INFO,"insert txn code and Check again if user is assigned with ESCPAL txn code.","User is assigned with ESCPAL"
						+ " txn code.", false);ispresent=true;
			}else{
				ispresent=false;
				Reporter.logEvent(Status.WARNING,"insert txn code and Check again if user is assigned with ESCPAL txn code.",
						"User is not assigned with ESCPAL txn code.", false);
			}
		}
		if(ispresent){
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		if(employeesearch.navigateToInvestmentTab())
			Reporter.logEvent(Status.PASS,"Verify investment tab is displayed if user is"
					+ " assigned with ESCPAL code.","Investment tab is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Verify investment tab is displayed if user is"
					+ " assigned with ESCPAL code.","Investment tab is not displayed.", true);
		}
		employeesearch.deleteESCPALAndESCCSDTxnCodes();
		homePage = new HomePage();
		homePage.logoutPSC();
		employeesearch.get();
		employeesearch.searchEmployeeByName("");
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToInvestmentTab();
		employeesearch.verifyAddChangeAlocBtn();
		employeesearch.insertESCCPAandESCCSDTxnCodes();
		homePage.logoutPSC();
		employeesearch.get();
		resultset = DB.executeQuery(Stock.getTestQuery("getSSNForSelfDirectedAllocation")[0],
				Stock.getTestQuery("getSSNForSelfDirectedAllocation")[1],Stock.GetParameterValue("PlanNumber"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToInvestmentTab();
		employeesearch.addChangeAllocBtnForSelfDirectedPlan();
		employeesearch.navigateToAllocPage();
		employeesearch.addAllocationAndVerify();
		homePage.logoutPSC();
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
 * <pre>This test case validates employee Transaction History page.</pre>
 * @param itr
 * @param testdata
 * @Date 10-July-2017
 */
@Test(dataProvider = "setData")
public void TC_48_AccountDetail_Transacion_History_Reg(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validateemployee Transaction History page.", false);
		employeesearch = new EmployeeSearch().get();
		resultset = DB.executeQuery(Stock.getTestQuery("getPPTWithTransactionHistory")[0],
				Stock.getTestQuery("getPPTWithTransactionHistory")[1],"K_"+Stock.GetParameterValue("username"));
		employeesearch.selectEmployeeFromResultSet(resultset);
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToTxnHistoryTab();
		String expMsg = Stock.GetParameterValue("ExpPastThreeMonthsTxnMsg");
		String actMsg =  Web.returnElement(employeesearch,"TXN_HISTORY_PAST_THREE_MONTH_MSG").getText().trim();
		if(Web.VerifyText(expMsg,actMsg,true))
			Reporter.logEvent(Status.PASS,"Validate below message is displayed under transation history"
					+ " section\n"+expMsg,"Below message is displayed:\n"+actMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below message is displayed under transation history"
					+ " section\n"+expMsg,"Below message is displayed:\n"+actMsg, true);
		employeesearch.validateTxnHistPageScreenElements();
		employeesearch.dateRangeValidationTxnHist();
		employeesearch.validateConfirmationNbrWindow();
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN_NoPastThreeMonth"));
		employeesearch.navigateToEmployeeOverViewPage();
		CommonLib.switchToFrame(Web.returnElement(employeesearch,"FRAME"));
		String expNoDataMsg = Stock.GetParameterValue("ExpMsg_NoPastThreeMonth");
		String actNoDataMsg = 
				Web.returnElement(employeesearch,"NO_DATA_TXN_HIST_Click_HERE_LINK").findElement(By.xpath("./..")).getText().trim();
		System.out.println("Actual No Data message:"+actNoDataMsg);
		if(actNoDataMsg.contains(expNoDataMsg)) 
			Reporter.logEvent(Status.PASS,"Validate below message for employee having no"
					+ " past three month transactions.\n"+actNoDataMsg,"Below message is displayed for ppt not having"
							+ " past three month transaction.\n"+actNoDataMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below message for employee having no"
					+ " past three month transactions.\n"+actNoDataMsg,"Below message is displayed for ppt not having"
							+ " past three month transaction.\n"+actNoDataMsg, true);
		employeesearch.clickHereOpensModalWindow();
		
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
 * <pre>This test case validates external loan page elements and data.</pre>
 * @param itr
 * @param testdata
 * @Date 10-July-2017
 */
@Test(dataProvider = "setData")
public void TC_55_Loan_Summary_Table_External_Loan(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to "
				+ "validateemployee Transaction History page.", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN"));
		employeesearch.navigateToEmployeeOverViewPage();	
		if(employeesearch.isMoreDisplayedAndExtLoanHeaders()){
		employeesearch.validateExtLoanDetailPage_1();
		employeesearch.validateExtLoanDetailPage_2();}
		Web.getDriver().switchTo().defaultContent();
		employeesearch.searchPlan(Stock.GetParameterValue("GA_ID"));
		employeesearch.searchByParticipantID(Stock.GetParameterValue("PPTId_NO_Loan_Amt"));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.noExtLoanDataValidation();
		employeesearch.returnToEmpOvwPage();
		employeesearch.searchByParticipantID(Stock.GetParameterValue("PPTId_NO_Loan"));
		employeesearch.navigateToEmployeeOverViewPage();
		CommonLib.switchToFrame(Web.returnElement(employeesearch,"FRAME"));
		String text = Web.returnElement(employeesearch,"NO LOANS PAYOUT ID").getText();
		if(Web.returnElement(employeesearch,"NO LOANS PAYOUT ID").isDisplayed())
			Reporter.logEvent(Status.PASS,"Search for ppt with no loan payout data and check"
					+ " on overview page loan data block is suppressed and below message is dislayed:\n"+text,""
							+ "Loan data block is suppressed and below message is displayed.\n"+text,false);
		else
			Reporter.logEvent(Status.FAIL,"Search for ppt with no loan payout data and check"
					+ " on overview page loan data block is suppressed.","Loan data block is not suppressed.",true);
		employeesearch.searchEmployeeBySSNAllPlans(Stock.GetParameterValue("SSN"));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.dateFieldValidatnOnLoanPage();
		employeesearch.returnToEmpOvwPage();
		employeesearch.searchByParticipantID(Stock.GetParameterValue("PPTId_NoLoanRepayments"));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.validateIfNoLoanRepaymenet();
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
 * <pre>This testcase will cover UI changes and work flow for Active employee.</pre>
 * @param itr
 * @param testdata
 * @Date 10-July-2017
 */
@Test(dataProvider = "setData")
public void TC_56_PSC_Employee_Information_NonZeroDeferral_Terminated_GreaterThan_18Month(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to validate"
				+ " employment angular page and hire date and term date validations.", false);
		String planNumber="";
		String dateToRevertBack="";
		String ssn = "";
		String hireDate="";
		String termDate="";
		String termDateModified = "";
		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithEligiRuleSet")[0],
					Stock.getTestQuery("getPlanWithEligiRuleSet")[1],"K_"+Stock.GetParameterValue("username"));
			
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				break;
			}
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNTermedGrtrThan18Months")[0],
					Stock.getTestQuery("getSSNTermedGrtrThan18Months")[1],planNumber);
			
			while(resultset.next()){
				ssn = resultset.getString("SSN");
				hireDate = resultset.getString("HIRE_DATE");
				termDate = resultset.getString("EMP_TERMDATE");
				termDateModified = resultset.getString("EMP_TERMDATE_MODIFIEDDATE");
				break;
			}
			Date dbTermDate = CommonLib.getDateInDateFormatFromDateString("MM/dd/yyyy", termDate);
			
			if(dbTermDate.after(CommonLib.getSysDateWithTimeZone("MST"))){
				dateToRevertBack = CommonLib.getDateStringInDateFormat("dd-MMM-yy", dbTermDate);
				System.out.println("TermDate from DB :"+dateToRevertBack);
				String modifyDate = CommonLib.getDate("dd-MMM-yy", -60);
				System.out.println("TermDate modified by -60 days :"+modifyDate);
				DB.executeUpdate(Stock.getTestQuery("updateEmploymentTableTermDate")[0],
						Stock.getTestQuery("updateEmploymentTableTermDate")[1],modifyDate,ssn);
			}
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchEmployeeBySSN(ssn);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.employmentInfoForTermedEmp(termDateModified);
				employeesearch.ediTTermDateValidation(hireDate,termDate);
				Web.clickOnElement(Web.returnElement(employeesearch, "CANCEL_BUTTON"));
				employeesearch.validateRehireFeature();
				employeesearch.validateBreadCrumb("Employment information");
				employeesearch.validateBasicEmploymentElements(planNumber, ssn);
				employeesearch.validateEmploymentHistoryLabels();
				employeesearch.updateReHireDateValidation(termDate);
				employeesearch.fillRehireDetailFormAndCheckForCnfMsg(termDate);
			}
			
			if(dbTermDate.after(CommonLib.getSysDateWithTimeZone("MST"))){
				DB.executeUpdate(Stock.getTestQuery("updateEmploymentTableTermDate")[0],
						Stock.getTestQuery("updateEmploymentTableTermDate")[1],dateToRevertBack,ssn);
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
 * <pre>This testcase covers hire date and term date validations for ppt whose termdate+18months<current date.</pre>
 * @param itr
 * @param testdata
 * @Date 10-July-2017
 */
@Test(dataProvider = "setData")
public void TC_57_PSC_Employee_Information_NonZeroDeferral_TerminatedLessThen_18Month
(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The objective of this test case is to fill employment"
				+ " details,save and validate for event id generated.", false);
		String planNumber="";
		String dateToRevertBack="";
		String ssn = "";
		String hireDate="";
		String termDate="";
		String termDateModified = "";
		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithEligiRuleSet")[0],
					Stock.getTestQuery("getPlanWithEligiRuleSet")[1],"K_"+Stock.GetParameterValue("username"));
			
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				break;
			}
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNTermedLessThan18Months")[0],
					Stock.getTestQuery("getSSNTermedLessThan18Months")[1],planNumber);
			
			while(resultset.next()){
				ssn = resultset.getString("SSN");
				hireDate = resultset.getString("HIRE_DATE");
				termDate = resultset.getString("EMP_TERMDATE");
				termDateModified = resultset.getString("EMP_TERMDATE_MODIFIEDDATE");
				break;
			}
			Date dbTermDate = CommonLib.getDateInDateFormatFromDateString("MM/dd/yyyy", termDate);
			
			if(dbTermDate.after(CommonLib.getSysDateWithTimeZone("MST"))){
				dateToRevertBack = CommonLib.getDateStringInDateFormat("dd-MMM-yy", dbTermDate);
				System.out.println("TermDate from DB :"+dateToRevertBack);
				String modifyDate = CommonLib.getDate("dd-MMM-yy", -740);
				System.out.println("TermDate modified by -560 days :"+modifyDate);
				DB.executeUpdate(Stock.getTestQuery("updateEmploymentTableTermDate")[0],
						Stock.getTestQuery("updateEmploymentTableTermDate")[1],modifyDate,ssn);
			}
			employeesearch = new EmployeeSearch().get();
			if(itr==1){
				employeesearch.searchPlan(planNumber);
				employeesearch.searchEmployeeBySSN(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.navigateToEmpDetailPage();
				if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
					employeesearch.employmentInfoForTermedEmp(termDateModified);
					employeesearch.validateRehireFeature();
					employeesearch.validateBreadCrumb("Employment information");
					employeesearch.validateBasicEmploymentElements(planNumber, ssn);
					employeesearch.validateEmploymentHistoryLabels();
					employeesearch.updateReHireDateValidation(termDate);
					employeesearch.fillRehireDetailFormAndCheckForCnfMsg(termDate);
				}
			}
			if(itr==2){
				employeesearch.searchPlan(planNumber);
				employeesearch.searchEmployeeBySSN(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.navigateToEmpDetailPage();
				if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
					employeesearch.employmentInfoForTermedEmp(termDateModified);
					employeesearch.navigateToEmpRehirePage();
					employeesearch.fillEmploymentDetails();
				}
			}
			
			if(dbTermDate.after(CommonLib.getSysDateWithTimeZone("MST"))){
				DB.executeUpdate(Stock.getTestQuery("updateEmploymentTableTermDate")[0],
						Stock.getTestQuery("updateEmploymentTableTermDate")[1],dateToRevertBack,ssn);
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
 * @param itr
 * @param testdata
 * @Date 10-July-2017
 */
@Test(dataProvider = "setData")
public void TC_58_PSC_Employee_Information_RehireTerminate_NonZeroDeferral_Active(int itr,Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates employment positive flow"
				+ " with some hire and term date validations.", false);
		String planNumber="";
		String ssn = "";
		String hireDate="";
		String termDate="";
		
		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithEligiRuleSet")[0],
					Stock.getTestQuery("getPlanWithEligiRuleSet")[1],"K_"+Stock.GetParameterValue("username"));
			
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				break;
			}
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNWithNullTermDate")[0],
					Stock.getTestQuery("getSSNWithNullTermDate")[1],planNumber);
			
			while(resultset.next()){
				ssn = resultset.getString("SSN");
				hireDate = resultset.getString("HIRE_DATE");
				termDate = resultset.getString("EMP_TERMDATE");
				break;
			}
			//Date dbTermDate = CommonLib.getDateInDateFormatFromDateString("MM/dd/yyyy", termDate);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchPlan(planNumber);
				employeesearch.searchEmployeeBySSN(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.navigateToEmpDetailPage();
				if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
					employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
					employeesearch.validateBreadCrumb("Employment information");
					employeesearch.validateBasicEmploymentElements(planNumber, ssn);
					employeesearch.validateEmploymentHistoryLabels();
					employeesearch.hireTermDateCalendarValidation();
					employeesearch.compareTerminationReason();
					employeesearch.fillEmploymentDetails();
					employeesearch.compareEmploymentData(ssn);
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
 * <pre>This test case aims to change hire info for active participant.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
@Test(dataProvider = "setData")
public void TC_60_PSC_Employee_Information_Active_ChageHireInfo(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case aims to change hire info for active participant", false);
		String planNumber="";
		String ssn = "";
		String hireDate="";
		String termDate="";

		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithEligiRuleSet")[0],
					Stock.getTestQuery("getPlanWithEligiRuleSet")[1],"K_"+Stock.GetParameterValue("username"));

			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				break;
			}
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNWithNullTermDate")[0],
					Stock.getTestQuery("getSSNWithNullTermDate")[1],planNumber);

			while(resultset.next()){
				ssn = resultset.getString("SSN");
				hireDate = resultset.getString("HIRE_DATE");
				termDate = resultset.getString("EMP_TERMDATE");
				break;
			}
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchEmployeeBySSN(ssn);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
				employeesearch.employmentFieldValidation();
				employeesearch.fillEmploymentDetails();
				employeesearch.returnToEmployeeOverview();
				employeesearch.searchEmployeeBySSN(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
				WebElement msgEle1 = Web.returnElement(employeesearch,"UPDATE_EMP_MSG_FOR_TEMRED_1");
				WebElement msgEle2 = Web.returnElement(employeesearch,"UPDATE_EMP_MSG_FOR_TEMRED_2");
				String msg = msgEle1.getText().trim()+" "+ msgEle2.getText().trim();
				if(Web.isWebElementDisplayed(msgEle1,false)&&Web.isWebElementDisplayed(msgEle2,false))
					Reporter.logEvent(Status.PASS,"validate below message is displayed for termed employee:\n"
							+ "'This employee is currently in a terminated employment status. Please choose an option below to continue.'",
							"Below message is displayed:\n"+msg,false);
				else
					Reporter.logEvent(Status.FAIL,"validate below message is displayed for termed employee:\n"
							+ "'This employee is currently in a terminated employment status. Please choose an option below to continue.'",
							"Below message is displayed:\n"+msg,true);
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
 * <pre>This test case covers different scenario for Hire and terminate features for Zero deferral plan.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
@Test(dataProvider = "setData")
public void TC_59_PSC_Employee_Information_RehireTerminate_ZeroDeferral_Scenario1(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates Edit termination button is"
				+ " not displayed if plan is set for zero deferral with 0 days set for zero deferral rule.", false);
		String planNumber="";
		String ssn = "";

		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanWithEligiRuleAndZeroDeferral")[0],
					Stock.getTestQuery("getPlanWithEligiRuleAndZeroDeferral")[1],"K_"+Stock.GetParameterValue("username"));

			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				break;
			}
			
			resultset = DB.executeQuery(Stock.getTestQuery("getSSNWithNullTermDate")[0],
					Stock.getTestQuery("getSSNWithNullTermDate")[1],planNumber);
			

			while(resultset.next()){
				ssn = resultset.getString("SSN");
				break;
			}
			Date currentDate = new Date();
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchEmployeeBySSN(ssn);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
				employeesearch.terminateEmpWithCurrentDate(currentDate);
				Web.clickOnElement(Web.returnElement(employeesearch,"RETURN_TO_EMPLOYEE_PAGE_BTN"));
				employeesearch.searchEmployeeBySSN(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.zeroDeferralValidationScenario_1();
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
 * <pre>This test case covers different scenario for Hire and terminate features for Zero deferral plan.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
@Test(dataProvider = "setData")
public void TC_59_PSC_Employee_Information_RehireTerminate_ZeroDeferral_Scenario2(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates Edit termination button is"
				+ " not displayed when termDate+zero_after_x_days<currentdate", false);
		String planNumber="";
		String indid = "";
		String zero_after_x_days="";

		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[0],
					Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[1],"K_"+Stock.GetParameterValue("username"));
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				zero_after_x_days = resultset.getString("ZERO_AFTER_X_DAYS");
				break;
			}
			
			resultset = DB.executeQuery(Stock.getTestQuery("Xdays+TermDateLessThnSysdtWithZeroDeferral")[0],
					Stock.getTestQuery("Xdays+TermDateLessThnSysdtWithZeroDeferral")[1],planNumber,zero_after_x_days);
		
			while(resultset.next()){
				indid=resultset.getString("IND_ID");
			}
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchByParticipantID(indid);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.zeroDeferralValidationScenario_1();
				employeesearch.employmentsectionandDetailLinkValidation();
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
 * <pre>This test case covers different scenario for Hire and terminate features for Zero deferral plan.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
@Test(dataProvider = "setData")
public void TC_59_PSC_Employee_Information_RehireTerminate_ZeroDeferral_Scenario3(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates Edit termination button is"
				+ " not displayed when termDate+zero_after_x_days=currentdate", false);
		String planNumber="";
		String indId = "";
		String zero_after_x_days="";
		String ssn="";
		boolean isIndIdEmpty=false;
		employeesearch = new EmployeeSearch().get();
		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[0],
					Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[1],"K_"+Stock.GetParameterValue("username"));
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				zero_after_x_days = resultset.getString("ZERO_AFTER_X_DAYS");
				break;
			}
			
			
				resultset = DB.executeQuery(Stock.getTestQuery("Xdays+TermDateEqulsSysdtWithZeroDeferral")[0],
						Stock.getTestQuery("Xdays+TermDateEqulsSysdtWithZeroDeferral")[1],planNumber,zero_after_x_days);
				while(resultset.next()){
					indId=resultset.getString("IND_ID").trim();
				}
			
				if(indId.isEmpty()){
					isIndIdEmpty = true;
					resultset = DB.executeQuery(Stock.getTestQuery("getSSNWithNullTermDate")[0],
							Stock.getTestQuery("getSSNWithNullTermDate")[1],planNumber);
					while(resultset.next()){
						ssn=resultset.getString("SSN");
						break;
					}
					String termDateString = 
							employeesearch.getDateWithReferenceToAnyDate(new Date(),-Integer.parseInt(zero_after_x_days),"MM/dd/yyyy");
					employeesearch.searchEmployeeBySSNAllPlans(ssn);
					employeesearch.navigateToEmployeeOverViewPage();
					employeesearch.navigateToEmpDetailPage();
					employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
					employeesearch.terminateEmpWithCurrentDate(
							CommonLib.getDateInDateFormatFromDateString("MM/dd/yyyy", termDateString));
					resultset = DB.executeQuery(Stock.getTestQuery("Xdays+TermDateEqulsSysdtWithZeroDeferral")[0],
							Stock.getTestQuery("Xdays+TermDateEqulsSysdtWithZeroDeferral")[1],planNumber,zero_after_x_days);
					while(resultset.next()){
						indId=resultset.getString("IND_ID");
					}
				}
			
			Web.getDriver().switchTo().defaultContent();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchByParticipantID(indId);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.zeroDeferralValidationScenario_1();
				employeesearch.employmentsectionandDetailLinkValidation();
			}
			if(isIndIdEmpty){
				employeesearch.rehireEmp(CommonLib.getDateStringInDateFormat("MM/dd/yyyy", new Date()), ssn);
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
 * <pre>This test case covers different scenario for Hire and terminate features for Zero deferral plan.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
@Test(dataProvider = "setData")
public void TC_59_PSC_Employee_Information_RehireTerminate_ZeroDeferral_Scenario4(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","This test case validates Edit termination button is"
				+ " not displayed when termDate+zero_after_x_days>currentdate", false);
		String planNumber="";
		String indId = "";
		String zero_after_x_days="";
		String ssn="";
		employeesearch = new EmployeeSearch().get();
		resultset = DB.executeQuery(Stock.getTestQuery("chkEmploymentAndEditTxnCode")[0],
				Stock.getTestQuery("chkEmploymentAndEditTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(resultset)==2){
			resultset = DB.executeQuery(Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[0],
					Stock.getTestQuery("getPlanAndZeroDeferralDaysRecord")[1],"K_"+Stock.GetParameterValue("username"));
			while(resultset.next()){
				planNumber = resultset.getString("GA_ID");
				zero_after_x_days = resultset.getString("ZERO_AFTER_X_DAYS");
				break;
			}
			
				resultset = DB.executeQuery(Stock.getTestQuery("Xdays+TermDateGrtrThnSysdtWithNonZeroDeferral")[0],
						Stock.getTestQuery("Xdays+TermDateGrtrThnSysdtWithNonZeroDeferral")[1],planNumber,zero_after_x_days);
				while(resultset.next()){
					indId=resultset.getString("IND_ID");
				}
			if(indId.isEmpty()){
				resultset = DB.executeQuery(Stock.getTestQuery("getSSNWithNullTermDate")[0],
						Stock.getTestQuery("getSSNWithNullTermDate")[1],planNumber);
				while(resultset.next()){
					ssn=resultset.getString("SSN");
				}
				String termDateString = 
				employeesearch.getDateWithReferenceToAnyDate(new Date(),Integer.parseInt(zero_after_x_days),"MM/dd/yyyy");
				employeesearch.searchEmployeeBySSNAllPlans(ssn);
				employeesearch.navigateToEmployeeOverViewPage();
				employeesearch.navigateToEmpDetailPage();
				employeesearch.navigateToHirePageWhenEmpActiveNoTermDate();
				employeesearch.terminateEmpWithCurrentDate(
						CommonLib.getDateInDateFormatFromDateString("MM/dd/yyyy", termDateString));
				resultset = DB.executeQuery(Stock.getTestQuery("Xdays+TermDateGrtrThnSysdtWithNonZeroDeferral")[0],
						Stock.getTestQuery("Xdays+TermDateGrtrThnSysdtWithNonZeroDeferral")[1],planNumber,zero_after_x_days);
				while(resultset.next()){
					indId=resultset.getString("IND_ID");
				}
				
			}
			Web.getDriver().switchTo().defaultContent();
			employeesearch.searchPlan(planNumber);
			employeesearch.searchByParticipantID(indId);
			employeesearch.navigateToEmployeeOverViewPage();
			employeesearch.navigateToEmpDetailPage();
			if(employeesearch.employmentInfoSectionAndEditLinkValidation()){
				employeesearch.zeroDeferralValidationScenario_1();
				employeesearch.employmentsectionandDetailLinkValidation();
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
 * <pre>This test case covers different scenario for Hire and terminate features for Zero deferral plan.</pre>
 * @author smykjn
 * @param itr
 * @param testdata
 * @Date 31-July-2017
 */
/*@Test(dataProvider = "setData")
public void TC_61_SIT_PSC_Search_Employee_based_on_User_access(int itr,Map<String, String> testdata) {		
	try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description","The purpose of this test case"+
				"is to verify Employee Search functionality based on User category.", false);
		resultset = DB.executeQuery(Stock.getTestQuery("getNumberOfPlansQuery")[0],Stock.getTestQuery("getNumberOfPlansQuery")[1],
				"K_"+Stock.GetParameterValue("username"));
		int numberOfPlans = DB.getRecordSetCount(resultset);
		String ssn="";
		String planNumber="";
		System.out.println("Number of plans:"+numberOfPlans);
		if(resultset.next()){
			planNumber = resultset.getString("GA_ID");
			}
		resultset = DB.executeQuery(Stock.getTestQuery("queryToFindSSNforAplan")[0],
				Stock.getTestQuery("queryToFindSSNforAplan")[1],planNumber);
		while(resultset.next()){
			ssn =  resultset.getString("SSN");
			break;
		}
		employeesearch = new EmployeeSearch().get();
		if(numberOfPlans==1){
			employeesearch.searchEmployeeBySSN(ssn);
		}else{
			employeesearch.searchPlan(planNumber);
			employeesearch.searchEmployeeBySSN(ssn);
		}
		if(Web.returnElements(employeesearch, "EmpLastNameLink").get(0).isDisplayed())
			Reporter.logEvent(Status.PASS,"Login as PSC user with only 1 plan and search for an"
					+ " employee within that plan.","Employee is found.", false);
		else
			Reporter.logEvent(Status.FAIL,"Login as PSC user with only 1 plan and search for an"
					+ " employee within that plan.","Employee is not found.", true);
		
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


















	

	@AfterSuite
	public void cleanUpSession() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}

}
