package app.pptweb.testcases.liat;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.liat.HealthCareCosts;
import pageobjects.liat.HowDoICompare;
import pageobjects.liat.RetirementIncome;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import core.framework.Globals;

public class liattestcases {
 
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
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
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@BeforeMethod
    public void getTCName(Method tc) {
           tcName = tc.getName();       
           lib.Web.webdriver = Web.getWebDriver(Stock.getConfigParam("BROWSER"));
    }
	
	
	@Test(dataProvider = "setData")
	public void RIP_TC003_To_verify_Retirement_Income_tab(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
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
			
			if(!(retirement.getContributionValueFromViewDetails("Income Gap").equalsIgnoreCase("N/A") || retirement.getContributionValueFromViewDetails("Income Gap").equalsIgnoreCase("$0.00"))){
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
		}
	@Test(dataProvider = "setData")
	public void RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
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
			
			
			
			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				Web.clickOnElement(retirement, "Do It Myself");
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
			if (lib.Stock.GetParameterValue("Help Me Do It").trim().equalsIgnoreCase("Y")) {
				if(Web.clickOnElement(retirement,"Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab", "Able to navigate to Help Me Do It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab", "Not Able to navigate to Help Me Do It tab", true);
			}
			else  {
				if(!Web.clickOnElement(retirement,"Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab", "Help Me DO it tab is not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab", "Help Me Do It tab is displayed", true);
			}
			
			//verify if we are able to navigate to Do It For Me Tab
			if (lib.Stock.GetParameterValue("Do It For Me").trim().equalsIgnoreCase("Y")) {
				if(Web.clickOnElement(retirement,"Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab", "Able to navigate to Do It For Me It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab", "Not Able to navigate to Do It For Me tab", true);
			}
			else  {
				if(!Web.clickOnElement(retirement,"Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab", "Do It For Me tab is not displayed", false);
				else
				
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab", "Do It For Me tab is displayed", true);
			}
			
			//verify if we are able to navigate to Do It Myself Tab
			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				if(Web.clickOnElement(retirement,"Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab", "Able to navigate to Do It Myself tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab", "Not Able to navigate to Do It Myself tab", true);
			}
			else {
				if(!Web.clickOnElement(retirement,"Do It Myself"))
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login=new LoginPage();
			TwoStepVerification twoStepVerification=new TwoStepVerification(login);
			LandingPage landing= new LandingPage(twoStepVerification);			
			RetirementIncome retirementIncome=new RetirementIncome(landing);
			retirementIncome.get();			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				
			}
			//Verifying if Retirement Income Page is loaded.
			if(Web.isWebElementDisplayed(retirementIncome, "Estimated Retirement Income"))
			{
			Reporter.logEvent(Status.PASS, "Navigate to 'Retirement Income Page", 
					"Navigation succeeded", true);
			Web.clickOnElement(retirementIncome, "Social Security Tab");		
			
			//Verify if Social Security Tab is loaded
			if(Web.isWebElementDisplayed(retirementIncome, "Social Security Administration"))			
					Reporter.logEvent(Status.PASS, "Verify if Social Security Page is Loaded", "Social Security page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify if Social Security Page is Loaded", "Social Security page is not loaded", true);									
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Navigate to 'Retirement Income Page", 
						"Retirement Page is not loaded", true);
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void RIP_TC006_To_Verify_RetirementIncomeTab_Other_Assets(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login=new LoginPage();
			TwoStepVerification twoStepVerification=new TwoStepVerification(login);
			LandingPage landing= new LandingPage(twoStepVerification);			
			RetirementIncome retirementIncome=new RetirementIncome(landing);
			retirementIncome.get();	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				
			}			
			//Verifying if Retirement Income Page is loaded.
			if(Web.isWebElementDisplayed(retirementIncome, "Estimated Retirement Income"))
			{
			Reporter.logEvent(Status.PASS, "Navigate to 'Retirement Income Page", 
					"Navigation succeeded", true);			
			Web.clickOnElement(retirementIncome, "Other Assets Tab");
			
			//Verifying if Other Assets page is loaded 
			if(Web.isWebElementDisplayed(retirementIncome, "Add an Account"))				
					Reporter.logEvent(Status.PASS, "Verify if Other Assets Page is Loaded", "Other Assets page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify if Other AssetsPage is Loaded", "Other Assets page is not loaded", true);				
			}else {					
				Reporter.logEvent(Status.FAIL, "Navigate to 'Retirement Income Page", 
						"Retirement Page is not loaded", true);
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void RIP_TC007_To_Verify_RetirementIncomeTab_Income_Gap(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login=new LoginPage();
			TwoStepVerification twoStepVerification=new TwoStepVerification(login);
			LandingPage landing= new LandingPage(twoStepVerification);			
			RetirementIncome retirementIncome=new RetirementIncome(landing);
			retirementIncome.get();		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				
			}
			//Verify if Retirement Income Page is loaded
			if(Web.isWebElementDisplayed(retirementIncome, "Estimated Retirement Income"))
			{
			Reporter.logEvent(Status.PASS, "Navigate to 'Retirement Income Page", 
					"Navigation succeeded", true);	
			Web.clickOnElement(retirementIncome, "Income Gap Tab");

			//Verify if Income Gap Page is loaded
			if(Web.isWebElementDisplayed(retirementIncome, "Catch up Contributions"))					
					Reporter.logEvent(Status.PASS, "Verify if Income Gap Page is Loaded", "Income Gap page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify if Income Gap Page is Loaded", "Income Gap page is not loaded", true);						
			}else {
				Reporter.logEvent(Status.FAIL, "Navigate to 'Retirement Income Page", 
						"Retirement Page is not loaded", true);
				
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void RIP_TC008_To_verify_Retirement_Income_tab_percent_of_my_goal_section(int itr, Map<String, String> testdata){

		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			
			Reporter.logEvent(Status.INFO, "Navigate to Retirement Incomep page.", "Retirement Income page is displayed", true);
			
			//verify  Percent of my goal section for monthly retirement income
			String expectedtxtMonthlyIncome = retirement.verifyPercentOfMyGoalSection("Monthly Income");
			if(expectedtxtMonthlyIncome.contains("My goal for monthly retirement income") )
				Reporter.logEvent(Status.PASS, "Check Percent of my goal section for monthly retirement income", "Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Check Percent of my goal section for monthly retirement income ", "Percent of my goal section not displayed", true);
			
			//verify  Percent of my goal section for monthly retirement income
			String expectedtxtYearlyIncome = retirement.verifyPercentOfMyGoalSection("Yearly Income");
			if(expectedtxtYearlyIncome.contains("My goal for yearly retirement income"))
				Reporter.logEvent(Status.PASS, "Check Percent of my goal section for yearly retirement income", "Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Check Percent of my goal section for yearly retirement income", "Percent of my goal section not displayed", true);
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void HCC_TC009_To_verify_Health_care_costs(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			float projectedIncome;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();
			
			projectedIncome = retirementIncome.getProjectedIncome();
			Web.clickOnElement(healthCareCost, "Health-care costs");
			healthCareCost.verifyAttainedAgeSlide();
			healthCareCost.verifyPieChart();
			healthCareCost.verifyHealthCostFromUI(projectedIncome);
			healthCareCost.verifyPersonalizeBtn();
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	@Test(dataProvider = "setData")
	public void HDIC_TC010_To_verify_How_do_I_compare_tab(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare hdic = new HowDoICompare(homePage);
			hdic.get();
			hdic.verifyViewDetails();
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
                        
        }
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
		
	@AfterSuite
	public void cleanupSessions() {
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();
	}
	}

