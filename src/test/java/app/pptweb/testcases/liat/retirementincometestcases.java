package app.pptweb.testcases.liat;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.liat.RetirementIncome;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import app.common.Reporter;
import app.common.common;
import app.common.Reporter.Status;
import core.framework.Globals;
import core.utils.Stock;

public class retirementincometestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		common.webdriver = common.getWebDriver(Stock.globalParam.get("BROWSER"));
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
	public void RIP_TC003_To_verify_Retirement_Income_tab(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "RIP_TC003_To_verify_Retirement_Income_tab");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			
			Reporter.logEvent(Status.INFO, "Navigate to Retirement Incomep page.", "Retirement Income page is displayed", true);
			//verify if paycheck rainbow line is displayed
			if(retirement.isFieldDisplayed("Paycheck Rainbow"))
				Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice", "Paycheck Rainbow slice is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice", "Paycheck Rainbow slice is not displayed", true);
			
			//verify if user is able to click on view details button
			if(retirement.verifyViewDetailsLink())
				Reporter.logEvent(Status.PASS, "Verify View Details Button", "Clicked on View Details buton", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify View Details Button", "Could not click on view details button", true);
			
			//verify if names under view details are displayed on paycheck view
			String paycheckSliceDetails = retirement.getPaycheckSliceDetails();
			
			if(!(retirement.getContributionValueFromViewDetails("My Current Savings").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("My Current Savings").equalsIgnoreCase("$0.0") )){
				if(paycheckSliceDetails.contains("My Savings"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'My Current Savings'", "Paycheck Rainbow slice is displaying 'My Savings'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'My Current Savings'", "Paycheck Rainbow slice is not displaying 'My Savings'", true);
			}
				
			if(!(retirement.getContributionValueFromViewDetails("My Future Savings").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("My Future Savings").equalsIgnoreCase("$0.0"))){
				if(paycheckSliceDetails.contains("My Savings"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'My Future Savings'", "Paycheck Rainbow slice is displaying 'My Savings'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'My Current Savings'", "Paycheck Rainbow slice is not displaying 'My Savings'", true);
			}
				
			if(!(retirement.getContributionValueFromViewDetails("Employer Past Contribution").equalsIgnoreCase("N/A") ||  retirement.getContributionValueFromViewDetails("Employer Past Contribution").equalsIgnoreCase("$0.0"))){
				if(paycheckSliceDetails.contains("Employer Contributions"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'Employer Past Contribution'", "Paycheck Rainbow slice is displaying 'Employer Contribution'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'Employer Past Contribution'", "Paycheck Rainbow slice is not displaying 'Employer Contribution'", true);
			}
			
//			if(!(retirement.getContributionValueFromViewDetails("Employer Future Contribution").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("My Current Savings").equalsIgnoreCase("$0.0"))){
//				if(paycheckSliceDetails.contains("Employer Contributions"))
//					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'Employer Future Contribution'", "Paycheck Rainbow slice is displaying 'Employer Contribution'", false);
//				else
//					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'Employer Future Contribution'", "Paycheck Rainbow slice is not displaying 'Employer Contribution'", true);
//			}
			
			
			if(!(retirement.getContributionValueFromViewDetails("Social Security").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("Social Security").equalsIgnoreCase("$0.0"))){
				if(paycheckSliceDetails.contains("Social Security"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'Social Security'", "Paycheck Rainbow slice is displaying 'Social Security'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'Social Security'", "Paycheck Rainbow slice is not displaying 'Social Security'", true);
			}
			
			if(!(retirement.getContributionValueFromViewDetails("Other Assets").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("Other Assets").equalsIgnoreCase("$0.00"))){
				if(paycheckSliceDetails.contains("Other Assets"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'Other Assets'", "Paycheck Rainbow slice is displaying 'Other Assets'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'Other Assets'", "Paycheck Rainbow slice is not displaying 'Other Assets'", true);
			}
			
			if(!(retirement.getContributionValueFromViewDetails("Income Gap").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("Income Gap").equalsIgnoreCase("$0.0"))){
				if(paycheckSliceDetails.contains("Income Gap"))
					Reporter.logEvent(Status.PASS, "Check Paycheck rainbow slice for 'Income Gap'", "Paycheck Rainbow slice is displaying 'Income Gap'", false);
				else
					Reporter.logEvent(Status.FAIL, "Check Paycheck rainbow slice for 'Income Gap'", "Paycheck Rainbow slice is not displaying 'Income Gap'", true);
			}
			
			//close view details
			if(retirement.verifyViewDetailsCloseLink())
				Reporter.logEvent(Status.PASS, "Check close button for view details", "Able to click close buton", false);
			else
				Reporter.logEvent(Status.FAIL, "Check close button for view details", "Not able to click clos button", true);
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
}
}
	
	@Test(dataProvider = "setData")
	public void RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings(int itr, Map<String, String> testdata){
		
		Stock.globalTestdata = testdata;
//		Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			
			Reporter.logEvent(Status.INFO, "Navigate to Retirement Incomep page.", "Retirement Income page is displayed", true);
			
			//verify if we are able to navigate to Plan Savings Tab
			if(retirement.isFieldDisplayed("Plan Savings"))
				Reporter.logEvent(Status.PASS, "Verify Plan Savings tab", "Plan Savings tab is displayed and able to click on it", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Savings tab", "Plan Savings tab is not displayed", true);
			
			//verify if Contribution rate slider is present
			if(retirement.verifyIfSliderPresent("Contribution rate slider"))
				Reporter.logEvent(Status.PASS, "Verify Contribution rate slider", "Contribution rate slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Contribution rate slider", "Contribution rate slider not displayed", true);
			//verify if Retirement age slider is present
			if(retirement.verifyIfSliderPresent("Retirement age slider"))
				Reporter.logEvent(Status.PASS, "Verify Retirement age slider", "Retirement age slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Retirement age slider", "Retirement age slider not displayed", true);
			
			//verify if Investment mix slider is present
//			if(retirement.verifyIfSliderPresent("Investment mix slider"))
//				Reporter.logEvent(Status.PASS, "Verify Investment mix slider", "Investment mix slider displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Investment mix slider", "Investment mix slider not displayed", true);
			
			
			
			if (testdata.get("Do It Myself").trim().equalsIgnoreCase("Y")) {
				common.clickOnElement(retirement, "Do It Myself");
				if(retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS, "Verify Investment mix slider", "Investment mix slider displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Investment mix slider", "Investment mix slider not displayed", true);
			}
			else {
				if(!retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS, "Verify Investment mix slider", "Investment mix slider not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Investment mix slider", "Investment mix slider displayed", true);
			}
			
			
			
			
			
			//verify if we are able to navigate to Help Me Do It Tab
			if (testdata.get("Help Me Do It").trim().equalsIgnoreCase("Y")) {
				if(common.clickOnElement(retirement,"Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab", "Able to navigate to Help Me Do It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab", "Not Able to navigate to Help Me Do It tab", true);
			}
			else  {
				if(!common.clickOnElement(retirement,"Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab", "Help Me DO it tab is not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab", "Help Me Do It tab is displayed", true);
			}
			
			//verify if we are able to navigate to Do It For Me Tab
			if (testdata.get("Do It For Me").trim().equalsIgnoreCase("Y")) {
				if(common.clickOnElement(retirement,"Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab", "Able to navigate to Do It For Me It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab", "Not Able to navigate to Do It For Me tab", true);
			}
			else  {
				if(!common.clickOnElement(retirement,"Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab", "Do It For Me tab is not displayed", false);
				else
				
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab", "Do It For Me tab is displayed", true);
			}
			
			//verify if we are able to navigate to Do It Myself Tab
			if (testdata.get("Do It Myself").trim().equalsIgnoreCase("Y")) {
				if(common.clickOnElement(retirement,"Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab", "Able to navigate to Do It Myself tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab", "Not Able to navigate to Do It Myself tab", true);
			}
			else {
				if(!common.clickOnElement(retirement,"Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab", "Do It Myself tab is not displayed", false);
				else
				
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab", "Do It Myself tab is displayed", true);
			}
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
}
	}

//	@Test(dataProvider = "setData")
//	public void RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
//		Globals.GBL_CurrentIterationNumber = itr;
//		try {
//			Reporter.initializeReportForTC("RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try{
//			
//		}
//	}
}