package app.psc.testcases.plan;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import plan.PlanPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import framework.util.CommonLib;

/**
 * @author smykjn
 */
public class plantestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	HomePage homePage;
	PlanPage planPage;
	ResultSet queryResultSet; 
	EmployeeSearch employeesearch;
	AccountVerificationPage accountverification;
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
			String planNumber = Stock.GetParameterValue("planNumber");
			String actMaxNumberPlanallowed="";
			if(homePage.searchPlanWithIdOrName(planNumber))
				Reporter.logEvent(Status.INFO,"Switch to plan-"+planNumber,
						"User switches to plan-"+planNumber, false);
			if(homePage.navigateToProvidedPage("Plan","Overview","Plan provisions"))
				Reporter.logEvent(Status.PASS,"Naivgate to Plan provision page.",
						"Plan provision page is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,"Naivgate to Plan provision page.",
						"Plan provision page is not displayed.", true);
			planPage.validateGeneralInfoOnPlanProvision(planNumber);
			planPage.validateLoaninformationSectionOnPlanProvision(planNumber);
			
			
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
					"This test case aims to verify search newly created PPT and validate first name and Last name "
					+ "is displayed properly under contact information section",
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

	
	/**<pre>This test case aims to verify complex user cannot be edited/updated through SAW.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC20_Complex_User(int itr, Map<String,String> testDat)
	{
		String complexUserAssignedId="";
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case aims to verify complex user cannot be edited/updated through SAW.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			if(homePage.navigateToProvidedPage("Plan","Administration","Username security management"))
				Reporter.logEvent(Status.PASS,"Navigate to Plan-->Administration-->User security management page.", ""
						+ "user is navigated to user security management page.", false);
			else
				Reporter.logEvent(Status.FAIL,"Navigate to Plan-->Administration-->User security management page.", ""
						+ "user is not navigated to user security management page.", true);
			queryResultSet = DB.executeQuery(Stock.getTestQuery("getComplexUser")[0],
					Stock.getTestQuery("getComplexUser")[1]);
			while(queryResultSet.next()){
				complexUserAssignedId = queryResultSet.getString("LOGON_ID").replace("K_","").trim();
				break;
			}
			if(planPage.searchUser(new String[]{complexUserAssignedId},
					Web.returnElement(planPage,"ASSIGNED_USERID")))
				Reporter.logEvent(Status.PASS,"Search for a complex user with assigned user id-"+complexUserAssignedId,""
						+ "user is found.", false);
			else
				Reporter.logEvent(Status.FAIL,"Search for a complex user with assigned user id-"+complexUserAssignedId,""
						+ "user is not found.", true);
			
			if(!planPage.canBeEdited())
				Reporter.logEvent(Status.PASS,"Validate N/A is displayed for complex user against action column.",""
						+ "N/A is displayed.",false);
			else
				Reporter.logEvent(Status.FAIL,"Validate N/A is displayed for complex user against action column.",""
						+ "N/A is not displayed.",true);
			
			//code to remove complexity of user
			if(planPage.searchUser(new String[]{Stock.GetParameterValue("NonComplexUserId")},
					Web.returnElement(planPage,"ASSIGNED_USERID")))
				Reporter.logEvent(Status.PASS,"Search for a non complex user with assigned user id-"+Stock.GetParameterValue("NonComplexUserId"),""
						+ "user is found.", false);
			else
				Reporter.logEvent(Status.FAIL,"Search for a non complex user with assigned user id-"+Stock.GetParameterValue("NonComplexUserId"),""
						+ "user is not found.", true);
			
			if(planPage.canBeEdited()){
				if(planPage.validateActionDropDownOption("View / Edit"))
					Reporter.logEvent(Status.PASS,"Validate non complex user can be updated through SAW.",""
							+ "User can be updated through SAW.", false);
				else
					Reporter.logEvent(Status.FAIL,"Validate non complex user can be updated through SAW.",""
							+ "User can not be updated through SAW.", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,"Validate non complex user can be updated through SAW.",""
						+ "Action drop down is not displayed hence user can not be updated.", true);
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
	
	
	/**<pre>This test case aims to validate the search functionality of a user on SAW page.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC23_Find_Users_by_Name(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case aims to validate the search functionality of a user on SAW page.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			String lastname=Stock.GetParameterValue("LastName");
			String firstname=Stock.GetParameterValue("FirstName");
			if(Stock.GetParameterValue("Flow").equals("Positive")){
				homePage.navigateToProvidedPage("Plan","Administration","Username security management");
				if(planPage.searchUser(new String[]{lastname},
						Web.returnElement(planPage,"USER_LAST_NAME_INPUT")))
					Reporter.logEvent(Status.PASS,"Search a user with filter last name-"+lastname,""
							+ "User is found.",false);
				else
					Reporter.logEvent(Status.FAIL,"Search a user with filter last name-"+lastname,""
							+ "User is not found.",true);
				
				if(planPage.searchUser(new String[]{lastname,firstname},
						Web.returnElement(planPage,"USER_LAST_NAME_INPUT"),
						Web.returnElement(planPage,"USER_FIRST_NAME_INPUT")))
					Reporter.logEvent(Status.PASS,"Search a user with filter last name-"+lastname+" and first name-"+firstname,""
							+ "User is found.",false);
				else
					Reporter.logEvent(Status.FAIL,"Search a user with filter last name-"+lastname+" and first name-"+firstname,""
							+ "User is not found.",true);
			}else{
				homePage.navigateToProvidedPage("Plan","Administration","Username security management");
				if(!planPage.searchUser(new String[]{lastname,firstname},
						Web.returnElement(planPage,"USER_LAST_NAME_INPUT"),
						Web.returnElement(planPage,"USER_FIRST_NAME_INPUT")))
				{	
					Reporter.logEvent(Status.PASS,
							"Search a user with invalid filters\nlast name-"+lastname+" and first name-"+firstname,""
							+ "User is not found.",false);
					String actErrorMsg = 
							Web.returnElement(planPage,"NO_SEARCH_RESULTS_TEXT_ELEMENT").getText().trim() ;
					if(CommonLib.VerifyText(Stock.GetParameterValue("ExpectedErrorMessage"),
							actErrorMsg, true))
						Reporter.logEvent(Status.PASS,"Validate proper validation message is displayed.",
								"Validation message is displayed properly as below.\n"+actErrorMsg, false);
					else
						Reporter.logEvent(Status.FAIL,"Validate if proper validation message is displayed.",
								"Validation message is not displayed properly.Actual message is displayed as below-\n"+actErrorMsg, true);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,"Search a user with invalid filters as below\nlast name-"+lastname+" and first name-"+firstname,""
							+ "User is found.",true);
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
	
	

	/**<pre>This test case validates the basic screen elements of Charts page for Investment & Performance section.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC24_Investment_Charts_Page(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case validates the basic screen elements of Charts page for Investment & Performance section.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			employeesearch = new EmployeeSearch();
			employeesearch.searchPlan(Stock.GetParameterValue("planNumber"));
			homePage.navigateToProvidedPage("Plan","Investments & Performance","");
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateChartsPageScreenElements();
			planPage.validateChartsPageBasicFeatures();
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
	
	
	/**<pre>This test case validates the basic screen elements of Charts page for Investment & Performance section.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC25_Investment_Document_Page(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case validates the basic screen elements of"
					+ " Document page under Investment & Performance.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			employeesearch = new EmployeeSearch();
			employeesearch.searchPlan(Stock.GetParameterValue("planNumber"));
			homePage.navigateToProvidedPage("Plan","Investments & Performance","");
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateDocumentPageScreenElements();
			
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


	/**<pre>This test case validates the basic screen elements of Charts page for Investment & Performance section.</pre>
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC26_Terminate_User_ID(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This test case validates the basic screen elements of"
					+ " Document page under Investment & Performance.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			employeesearch = new EmployeeSearch();
			accountverification = new AccountVerificationPage();
			homePage.navigateToProvidedPage("Plan","Administration","Username security management");
			String mailID = Stock.GetParameterValue("Email ID");
			String assignedUsername = Stock.GetParameterValue("Assigned User Name");
			planPage.searchUser(new String[]{mailID,assignedUsername},
					Web.returnElement(planPage,"Email Search Input"),
					Web.returnElement(planPage,"ASSIGNED_USERID"));
			planPage.terminateUserFromUserSecurityManagement();
			if(Stock.GetParameterValue("Terminate").equalsIgnoreCase("Yes")){
				try{
					planPage.validateDBDetailsForTerminatedUser();
					homePage.logoutPSC();
					new LoginPage().submitLoginCredentials(new String[]{
							Stock.GetParameterValue("Assigned User Name"),
							Stock.GetParameterValue("password")});
					String expError = Stock.GetParameterValue("ExpErrorMsg");
					CommonLib.switchToFrame(Web.returnElement(new LoginPage(),"LOGIN FRAME"));
					String actError = Web.returnElement(new LoginPage(),"PreLoginErrorMsg").getText();
					if(Web.VerifyText(expError, actError, true))
						Reporter.logEvent(Status.PASS,"Login with terminated user and validate below error message.\n"+expError,""
								+ "Proper error message is displayed."+actError, false);
					else
						Reporter.logEvent(Status.FAIL,"Login with terminated user and validate below error message.\n"+expError,""
								+ "Proper error message is not displayed."+actError, true);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					DB.executeUpdate(Stock.getTestQuery("updateTermDateToNull")[0],
							Stock.getTestQuery("updateTermDateToNull")[1],
							"K_"+Stock.GetParameterValue("Assigned User Name"));
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
