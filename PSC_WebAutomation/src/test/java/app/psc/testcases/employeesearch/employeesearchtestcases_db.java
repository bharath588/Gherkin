package app.psc.testcases.employeesearch;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import lib.DB;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.employeesearch.EmployeeSearch;
import core.framework.Globals;

public class employeesearchtestcases_db {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	EmployeeSearch employeesearch;
	String actualErrorMessage = "";
	ResultSet resultset;

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
				.getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@Test(dataProvider = "setData")
	public void TC017_Verify_the_dropdown_options_And_Screen_elements(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.navigateToEmployeeTab();
			employeesearch.verifyScreenElements();
			if (employeesearch.compareDropdownOptions()) {
				Reporter.logEvent(Status.PASS,
						"Check if dropdown options are present as expected",
						"All dropdown options are displayed as expected", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if dropdown options are present as expected",
						"Dropdown options are not displayed", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC018_Verify_the_sorting_in_search_results(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("");
			employeesearch.verifySortingofColumns("FirstName");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC019_Verify_the_Pagination_on_search_results_page(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("1");
			if (employeesearch.verifyPaginationforSearchResults()) {
				Reporter.logEvent(Status.PASS,
						"Verify the pagination of search rtesults page",
						"The pagination is displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify the pagination of search rtesults page",
						"The pagination is displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC020_Verify_the_Order_of_search_results(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("1");
			employeesearch.verifyColumnHeaders();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC021_Verify_Search_results_are_displayed_as_Hyperlinks(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("1");
			if (employeesearch.verifySearchResultsasLinks()) {
				Reporter.logEvent(Status.PASS,
						"Check if the search results are displayed as links",
						"The results are displayed as links", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if the search results are displayed as links",
						"The results are not displayed as links", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC022_Verify_if_the_clicking_on_a_searchresults_redirects_to_employee_overview_page(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("1");
			if (employeesearch.verifyEmployeeredirectOverviewPage()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if the user is redirected to Employee Overview page",
						"The user is redirected to employee overview page",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if the user is redirected to Employee Overview page",
						"The user is not redirected to employee overview page",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC023_Verify_the_limit_of_search_results_on_searchpage(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("1");
			if (employeesearch.verifyLimitofsearchResults()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if the limit of search results is 1000",
						"The limit of search results displayed is less than or equal to 1000",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if the limit of search results is 1000",
						"The limit of search results displayed is not less than or equal to 1000",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC024_Verify_Filter_Functionality_Search_Results_Page(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN("0");
			employeesearch.switchToFrame();
			if (Web.isWebElementDisplayed(employeesearch, "FILTER")) {
				Reporter.logEvent(
						Status.PASS,
						"Check if the filter option is displayed before doing search",
						"The filter option is not displayed", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if the filter option is displayed before doing search",
						"The filter option is displayed before search", true);
			}
			employeesearch.switchToDefaultContent();
			employeesearch.verifyFilterFunctionality(Stock
					.GetParameterValue("FilterText"));
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC025_Verify_search_results_for_ssn_with_extension(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch();
			employeesearch.logoutFromApplication();
			DB.executeUpdate(
					Stock.getTestQuery("updateDefaultPlanQuery")[0],
					Stock.getTestQuery("updateDefaultPlanQuery")[1],
					employeesearch.selectDefaultPlan(
							Stock.getTestQuery("selectDefaultPlanQuery"),
							Stock.GetParameterValue("username")),
					"K_" + Stock.GetParameterValue("username"));

			String ssn = employeesearch.getSSNwithExtn(
					Stock.getTestQuery("selectSSNwithExt"),
					employeesearch.selectDefaultPlan(
							Stock.getTestQuery("selectDefaultPlanQuery"),
							Stock.GetParameterValue("username")));
			employeesearch.get();
			employeesearch.searchEmployeeBySSN(ssn);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if search results with EXT are displayed for valid SSN",
						"Search results with EXT are displayed correctly", true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results with EXT are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC026_Verify_search_results_are_not_displayed_for_employess_with_err_code(
			int itr, Map<String, String> testdata) {
		String planNumber = "";
		String employeeId = "";
		ResultSet tempResultset;
		boolean planExist = true;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			
			employeesearch = new EmployeeSearch();
			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlansForErrCode"),
					Stock.GetParameterValue("username"));
			planNumber = employeesearch.selectPlanFromResultset(resultset);

			if (DB.getRecordSetCount(resultset) < 1) {
				Reporter.logEvent(
						Status.INFO,
						"Check if there are any plan exist for the specific user",
						"There are no such plans exist for Err code", false);
				planExist = false;
			}
			tempResultset = DB.executeQuery(
					Stock.getTestQuery("getEmployeeIDErr")[0],
					Stock.getTestQuery("getEmployeeIDErr")[1], planNumber);

			if (tempResultset.next()) {
				employeeId = tempResultset.getString("ID");
			}

			employeesearch.get();
			employeesearch.searchByParticipantID(employeeId);
			if (planExist)
				if (!employeesearch.isSearchResultsDisplayed()) {
					Reporter.logEvent(
							Status.PASS,
							"Check if search results are displayed for Employees with Err code",
							"Search results are displayed correctly", false);
					Thread.sleep(2000);
					actualErrorMessage = employeesearch
							.getErrorMessageTextforInvalidSearch();
					if (Web.VerifyText(
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
							"Check if search results are displayed for Employees with Err code",
							"Search results are not displayed correctly", true);
				}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC027_Verify_termed_employeedetails_are_displayed(int itr,
			Map<String, String> testdata) {
		Stock.globalTestdata = testdata;
		String employeeId = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch();
			resultset = DB.executeQuery(
					Stock.getTestQuery("getTermedEmployees")[0],
					Stock.getTestQuery("getTermedEmployees")[1],
					"K_" + Stock.GetParameterValue("username"));

			if (resultset.next()) {
				employeeId = resultset.getString("ID");
			}

			employeesearch.get();
			employeesearch.searchByParticipantID(employeeId);
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
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC028_Verify_search_results_are_displayed_for_termed_employees(
			int itr, Map<String, String> testdata) {
		String planNumber = "";
		ResultSet tempResultSet;
		Stock.globalTestdata = testdata;
		String employeeId = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch().get();

			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanForTermedEmployees"),
					Stock.GetParameterValue("username"));
			planNumber = employeesearch.selectPlanFromResultset(resultset);
			if (DB.getRecordSetCount(resultset) < 1) {
				Reporter.logEvent(
						Status.INFO,
						"Check if there are any plan exist for the specific user",
						"There are no such plans exist for termed employees",
						false);
			}
			tempResultSet = DB.executeQuery(
					Stock.getTestQuery("getEmpIdforTermedEmployee")[0],
					Stock.getTestQuery("getEmpIdforTermedEmployee")[1],
					planNumber);

			if (tempResultSet.next()) {
				employeeId = tempResultSet.getString("IND_ID");
			}
			employeesearch.get();
			employeesearch.searchByParticipantID(employeeId);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid SSN",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}
			employeesearch.logoutFromApplication();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void TC029_Verify_ssn_masking_for_external_users(int itr,
			Map<String, String> testdata) {
		String planNumber = Globals.GC_EMPTY;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			employeesearch = new EmployeeSearch();
			employeesearch.logoutFromApplication();
			DB.executeUpdate(Stock.getTestQuery("queryChangeUserCategory")[0],
					Stock.getTestQuery("queryChangeUserCategory")[1], "K_"
							+ Stock.GetParameterValue("username"));

			resultset = DB.executeQuery(
					Stock.getTestQuery("selectPlanForSSNMasking")[0],
					Stock.getTestQuery("selectPlanForSSNMasking")[1], "K_"
							+ Stock.GetParameterValue("username"));
			if (resultset.next()) {
				planNumber = resultset.getString("GA_ID");
			}

			DB.executeUpdate(Stock.getTestQuery("updateDefaultPlanQuery")[0],
					Stock.getTestQuery("updateDefaultPlanQuery")[1],
					planNumber, "K_" + Stock.GetParameterValue("username"));

			employeesearch.get();
			employeesearch.verifySSNmasking();
			employeesearch.logoutFromApplication();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Assertion Error!!", true);
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
		Web.webdriver.close();
		Web.webdriver.quit();
	}

}
