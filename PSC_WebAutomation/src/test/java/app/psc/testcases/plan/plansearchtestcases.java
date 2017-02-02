package app.psc.testcases.plan;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

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
}
