package app.pptweb.testcases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import app.pptweb.common.Reporter;
import app.pptweb.common.common;
import app.pptweb.common.Reporter.Status;
import app.pptweb.pageobjects.Deferrals;
import app.pptweb.pageobjects.LeftNavigationBar;
import app.pptweb.pageobjects.MyAccountsPage;
import app.pptweb.pageobjects.landingpage.LandingPage;
import app.pptweb.pageobjects.login.LoginPage;
import app.pptweb.pageobjects.login.TwoStepVerification;
import core.framework.Globals;
import core.utils.Stock;

public class deferralstestcases {
  
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
	public void SIT_PPTWEB_Deferral_001_AfterTax_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_001_AfterTax_Select_Another_Contribution_Rate");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			leftmenu = new LeftNavigationBar(accountPage);
			
//			if(homePage.getNoOfPlansFromDB() <= 2){			
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}			
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try {
				common.waitForElement(deferrals,"Table Header Contribution");
			} catch (Exception e) {			
				e.printStackTrace();
			}
			
			//Navigating to  My Contributions Page
			if(common.isWebElementDisplayed(deferrals, "My Contributions"))
			{
				Reporter.logEvent(Status.PASS, "Navigate to My Contributions Page", 
						"Navigation succeeded", true);
				
			//Clicking Add or Edit button for After Tax Contribution
				deferrals.addOrEditAfterTaxContribution();
			
				
			} else {
				Reporter.logEvent(Status.FAIL, "Navigate to My Contributions Page", 
						"My Contributions page is not loaded", true);
			}
		}
		catch(Exception e)
	    {
	        e.printStackTrace();
	        Globals.exception = e;
	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
	    }catch(AssertionError ae)
	    {
	        ae.printStackTrace();
	        Globals.assertionerror = ae;
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
	public void SIT_PPTWEB_Deferral_015_Regular_BEFORE_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_015_Regular_BEFORE_Select_Another_Contribution_Rate");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			leftmenu = new LeftNavigationBar(accountPage);
			
//			if(homePage.getNoOfPlansFromDB() <= 2){			
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}			
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try {
				common.waitForElement(deferrals,"Table Header Contribution");
			} catch (Exception e) {			
				e.printStackTrace();
			}
			
			//Navigating to  My Contributions Page
					if(common.isWebElementDisplayed(deferrals, "My Contributions"))
					{
						Reporter.logEvent(Status.PASS, "Navigate to My Contributions Page", 
								"Navigation succeeded", true);
						
						//Clicking Add or Edit button for Before Tax Contribution
						deferrals.addOrEditBeforeTaxContribution();
					
						
					} else {
						Reporter.logEvent(Status.FAIL, "Navigate to My Contributions Page", 
								"My Contributions page is not loaded", true);
					}
		
		}
		catch(Exception e)
	    {
	        e.printStackTrace();
	        Globals.exception = e;
	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
	    }catch(AssertionError ae)
	    {
	        ae.printStackTrace();
	        Globals.assertionerror = ae;
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
	public void SIT_PPTWEB_Deferral_019_Regular_SPLIT_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_019_Regular_SPLIT_Select_Another_Contribution_Rate");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			leftmenu = new LeftNavigationBar(accountPage);
			
//			if(homePage.getNoOfPlansFromDB() <= 2){			
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}			
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try {
				common.waitForElement(deferrals,"Table Header Contribution");
			} catch (Exception e) {			
				e.printStackTrace();
			}
			
			//Navigating to  My Contributions Page
					if(common.isWebElementDisplayed(deferrals, "My Contributions"))
					{
						Reporter.logEvent(Status.PASS, "Navigate to My Contributions Page", 
								"Navigation succeeded", true);
						
						//Clicking Add or Edit button for Before Tax Contribution
						deferrals.addOrEditBeforeTaxSplitContribution();
					
						
					} else {
						Reporter.logEvent(Status.FAIL, "Navigate to My Contributions Page", 
								"My Contributions page is not loaded", true);
					}
		
		}
		catch(Exception e)
	    {
	        e.printStackTrace();
	        Globals.exception = e;
	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
	    }catch(AssertionError ae)
	    {
	        ae.printStackTrace();
	        Globals.assertionerror = ae;
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
}
