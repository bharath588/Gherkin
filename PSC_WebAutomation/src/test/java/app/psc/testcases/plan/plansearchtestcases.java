package app.psc.testcases.plan;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;

public class plansearchtestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	HomePage homePage;


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

	/**This test case aims to verify that user is redirected to plan summary page when plan is entered in the Search
	 * plans text on home page.
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC01_search_plan_for_valid_plan(int itr, Map<String,String> testDat)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"Verify the user is redirected to respective plan page when the plan number is entered");
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This testcase verifies that user is redirected to respective plan page when plan number is entered",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			Thread.sleep(3000);
			if(homePage.searchPlan())
			{
				Reporter.logEvent(Status.PASS, "Verify user is redirected to entered plan summary page", "User is redirected to entered plan summary page", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify user is redirected to entered plan summary page", "User is not redirected to entered plan summary page", true);
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


	/**This test case aims to verify that user is redirected to plan summary page when plan is entered in the Search
	 * plans text on home page.
=======
	
	
	/**This test case aims to verify that user is prompted with an error message when user hits
	 *  search for plan with plan field blank.
>>>>>>> 742279f708fa8c86da87f93f3dc630b0096c0f0f
	 * @param itr
	 * @param testData
	 */
	@Test(dataProvider="setData")
	public void TC02_Validate_error_message_when_plan_field_is_left_blank(int itr, Map<String,String> testData)
	{
		try
		{

			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"Verify the user is prompted with error message when the plan field is left blank.");
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This testcase verifies that user is prompted with error message when the plan field is left blank.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if(homePage.searchPlan() && homePage.verifyErrorText())
			{
				Reporter.logEvent(Status.PASS, "Verify user is promted with error message if plan number is empty", 
						"User is promted with error message if plan number is empty", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify user is promted with error message if plan number is empty", 
						"User is not promted with error message if plan number is empty", true);
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
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of error message",
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
		@Test(dataProvider="setData")
		public void TC03_Verify_planDetails_Populated(int itr, Map<String,String> testData)
		{
			try
			{
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
						"Verify that plan number and name are populated when user enters few text of the related plan numbers");
				Reporter.logEvent(Status.INFO, "Testcase Description", 
						"This testcase verifies that plan number and name are populated when user enters few text of the related plan numbers",
						false);
				homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
				homePage.enterPartialPlanNumber();
				if(homePage.verifyAutocompletePlanSuggestion())
				{
					Reporter.logEvent(Status.PASS, 
							"Verify that plan number and name are populated when user enters few text of the related plan numbers",
							"Plan number and name are populated when user enters few text of the related plan numbers", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL, 
							"Verify that plan number and name are populated when user enters few text of the related plan numbers", 
							"Plan number and name are not populated when user enters few text of the related plan numbers", true);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Globals.exception = e;
				String exceptionMessage = e.getMessage();
				Reporter.logEvent(Status.FAIL, 
						"A run time exception occured and and plan details are not populated when user type in few characters of a plan.",
						exceptionMessage, true);
			}
			catch(Error ae)
			{
				ae.printStackTrace();
				Globals.error = ae;
				String errorMsg = ae.getMessage();
				Reporter.logEvent(Status.FAIL, 
						"Assertion error occured and plan details are not populated when user type in few characters of a plan",
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
	
	/**This test case aims to verify that user is prompted with an error message when invalid plan is searched.
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC03_Validate_Error_Message_When_Invalid_Plan_Number_Entered(int itr, Map<String,String> testDat)
	{
		try
		{
			
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"Verify the user is prompted with error message when invalid plan number is searched.");
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This testcase verifies that user is prompted with error message when invalid plan number is searched.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if(!homePage.searchPlan() && homePage.verifyErrorText())
			{
				Reporter.logEvent(Status.PASS, "Verify user is promted with error message if plan number is invalid.", 
						"User is promted with error message if plan number is invalid.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify user is promted with error message if plan number is invalid.", 
						"User is not promted with error message if plan number is invalid.", true);
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
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of error message",
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
	
	
	/**This test case aims to verify that user is navigated to plan page when plan name is entered and searched on Home page.
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider="setData")
	public void TC04_Validate_Plan_Page_When_PlanName_Entered(int itr, Map<String,String> testDat)
	{
		try
		{
			
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"Verify the user is navigated to respective plan page when plan Name is entered in plan search box.");
			Reporter.logEvent(Status.INFO, "Testcase Description", 
					"This testcase verifies that user is navigated to respective plan page when plan Name is entered in plan search box.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if(homePage.searchPlanWithName())
			{
				Reporter.logEvent(Status.PASS, "Verify user is Navigated to respective plan page if plan name is entered and searched.", 
						"User is Navigated to repspective plan page.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify user is Navigated to respective plan page if plan name is entered and searched.", 
						"User is not Navigated to repspective plan page.", true);
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
			Reporter.logEvent(Status.FAIL, "Assertion error occured during checking of error message",
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
