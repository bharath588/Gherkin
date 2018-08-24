/**
 * 
 */
package app.psc.testcases.reports;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.homepage.HomePage;
import pageobjects.reports.ReportsPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import framework.util.CommonLib;

/**
 * @author rvpndy
 *
 */
public class ReportsTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;

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
				.getName(), testCase.getName());
	}

	@Test(dataProvider = "setData")
	public void TC01_01_SIT_PSC_Reports_02_TC017_ViewReport_Parameters(int itr, 
			Map<String, String> testData){
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME, "DDTC-2264");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify reports parameters", false);
			HomePage homePage = new HomePage();
			homePage.logoutPSC(homePage.isDifferentUser());
			ReportsPage rptPage = new ReportsPage();
			rptPage.get();
			if(rptPage.isReportOrderForm()){
				Reporter.logEvent(Status.PASS, "Verify report order screen Elements", "Report order form elements are proper", false);
				
			}
			else{
				Reporter.logEvent(Status.FAIL, "Verify report order screen Elements", "Report order form elements are not loaded", true);
			}
		}
		catch(Exception e){
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page/login page/Reports Page "
							+ "could not be verified.",
							errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void TC02_01_PSCNextGen_Reports_Plan_review_PDF_PPTX(int itr, Map<String,String>testData){
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME, "DDTC-8283");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify reports parameters", false);
			if(CommonLib.insertTxnCode(Stock.getTestQuery("insertTxnCode"),			
					Stock.GetParameterValue("username"), 
					Stock.GetParameterValue("tabTxnCode"),
					Stock.GetParameterValue("uscsId"))&&
					CommonLib.insertTxnCode(Stock.getTestQuery("insertTxnCode"), 
							Stock.GetParameterValue("username"), 
							Stock.GetParameterValue("txnCode"),							
							Stock.GetParameterValue("uscsId")))
			{
				HomePage homePage = new HomePage();
				homePage.logoutPSC(homePage.isDifferentUser());
				ReportsPage rptPage = new ReportsPage();
				rptPage.get();
				if(rptPage.isReportOrderFormByTxnCodeSearch(Stock.GetParameterValue("txnCode"))){
					Reporter.logEvent(Status.PASS, "Open report order page", 
							"Report order page i"
							+ "s open. Ordering report...", true);
					rptPage.orderReport();
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Open report order page", 
							"Report order page did not open", true);
				}
				
			}
			
			
		}
		catch(Exception e){
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page/login page/Reports Page "
							+ "could not be verified.",
							errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
	}
	
	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().close();
		Web.getDriver().quit();
		Web.removeWebDriverInstance();
	}
	


}
