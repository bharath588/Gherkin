package app.psc.testcases.plan;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import plan.PlanPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

/**
 * @author smykjn
 */
public class plantestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	HomePage homePage;
	PlanPage planPage;
	ResultSet queryResultSet; 
	EmployeeSearch employeesearch;
	@BeforeClass
	public void ReportInit()
	{
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception{
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception{
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), testCase.getName());

	}

	/**<pre>This test case aims to verify plan provisions page against DB.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC16_Plan_Provisions_page(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case aims to verify plan provisions page against DB",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of plan summary page",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**<pre>This test case aims to verify search newly created PPT and validate first name and Last name
	 * is displayed properly under contact information section.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC17_Add_PPT_And_SearchForPPT(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case aims to verify plan provisions page against DB",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			String ssn="";
			//homePage.searchPlanWithIdOrName(Stock.GetParameterValue("PlanNumber"));
			employeesearch = new EmployeeSearch();
			employeesearch.navigateToAddEmpPage();
			if(employeesearch.fillSSNForAddNewEmp())
			{
				ssn = EmployeeSearch.newEmpSSN;
				if(employeesearch.fillNewEmpBasicInfoValid())
				{
					if(employeesearch.fillEligibilityInfoPositiveFlow())
					{
						if(employeesearch.enterIncomeData())
						{
							if(employeesearch.addNewEmploymentInfo())
							{
								employeesearch.addSubSetInfo();
							}
							employeesearch.addManageAccountDetailsWithOutManageAccntEnroll();
							homePage.navigateToHomePage();
							homePage.navigateToProvidedPage("Employees","Search employee");
							employeesearch.searchEmployeeBySSNAllPlans(ssn);
							if (employeesearch.isSearchResultsDisplayed()) {
								Reporter.logEvent(Status.PASS,
										"Check if search results are displayed for ssn:"+ssn,
										"Search results are displayed correctly", false);
							} else {
								Reporter.logEvent(Status.FAIL,
										"Check if search results are displayed for valid SSN",
										"Search results are not displayed correctly", true);
							}	
							employeesearch.navigateToEmployeeOverViewPage();
							employeesearch.navigateToEmpDetailPage();
							employeesearch.validateContactFstNameLstName(ssn);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of plan summary page",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**<pre>This test case aims to verify Unit/Share values pages under Investment & options plan menu option.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC18_SIT_PSC_Investment_UnitShare_values_page(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case aims to verify Unit/Share values pages under Investment & options plan menu option.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock.GetParameterValue("planNumber"));
			planPage.navigateToInvestmentAndPerformance();
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateUnitShareFromToField();
			planPage.validateUnitShareValuesheaders();
			planPage.validateWatermarkText();
			planPage.validatePrintFunctionality();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of plan summary page",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}
