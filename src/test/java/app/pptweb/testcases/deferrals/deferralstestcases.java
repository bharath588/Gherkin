package app.pptweb.testcases.deferrals;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.Reporter;
import lib.Reporter.Status;
import pageobjects.general.*;
import pageobjects.login.*;
import pageobjects.deferrals.Deferrals;
import pageobjects.landingpage.LandingPage;
import core.framework.Globals;
import lib.Stock;

public class deferralstestcases {
  
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		lib.Web.webdriver = lib.Web.getWebDriver(Stock.globalParam.get("BROWSER"));
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		System.out.println("Begin Debug with manual TC  :" + Globals.GC_MANUAL_TC_NAME);
		//this.testData = Stock.getTestData(this.getClass().getName(), testCase.getName());
		this.testData = Stock.getTestData(this.getClass().getName(), Globals.GC_MANUAL_TC_NAME);
		System.out.println(this.testData);
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
			/*if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
				 leftmenu = new LeftNavigationBar(homePage);			
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}*/
			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			leftmenu = new LeftNavigationBar(accountPage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try { lib.Web.waitForElement(deferrals,"Table Header Contribution"); }
			catch (Exception e) {	e.printStackTrace(); }	
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			if(deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS, "Verify After-tax contribution page", "After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify After-tax contribution page", "After-tax Contributions page is not displayed", true);
			if(deferrals.click_Select_Your_Contribution_Rate())
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
			
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.myContributions_Confirmation_Page();
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
	    finally { }
	    try { Reporter.finalizeTCReport(); }
	    catch (Exception e1) { e1.printStackTrace(); } 
	}
	
		
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_004_Bonus_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_004_Bonus_Select_Another_Contribution_Rate");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
				 leftmenu = new LeftNavigationBar(homePage);			
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try { lib.Web.waitForElement(deferrals,"Table Header Contribution"); }
			catch (Exception e) {	e.printStackTrace(); }	
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			if(deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS, "Verify Bonus contribution page", "Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Bonus contribution page", "Bonus Contributions page is not displayed", true);
			
			if(deferrals.click_Select_Your_Contribution_Rate())
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
			
			deferrals.select_ContributionType_Standard("After");
			deferrals.add_Auto_Increase("Bonus Add Auto Increase");
			deferrals.myContributions_Confirmation_Page();			
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
		finally { }
	    try { Reporter.finalizeTCReport(); }
	    catch (Exception e1) { e1.printStackTrace(); } 
	}
	
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_013_Regular_BEFORE_Maximize_To_Company_Match(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_013_Regular_BEFORE_Maximize_To_Company_Match");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);	
			
			if(homePage.getNoOfPlansFromDB(testdata.get("Participant ssn")) <= 2)
				 leftmenu = new LeftNavigationBar(homePage);
			else{
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			try {
				lib.Web.waitForElement(deferrals,"Table Header Contribution");
			} catch (Exception e) {			
				e.printStackTrace();
			}
			
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			if(deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
			
			if(testdata.get("Standard_Contribution").equalsIgnoreCase("Maximize to company match"))
				deferrals.click_MaximizeToCompanyMatch();
			if(testdata.get("Standard_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit("Standard");
			if(testdata.get("Standard_Contribution").equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();
			
			
			deferrals.select_ContributionType_Standard(testdata.get("Contribution_type"));
			deferrals.add_Auto_Increase(testdata.get("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();			
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
		
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
		finally { }
	    try { Reporter.finalizeTCReport(); }
	    catch (Exception e1) { e1.printStackTrace(); } 
	}
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_014_Regular_BEFORE_Maximize_IRS_Limit(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_014_Regular_BEFORE_Maximize_IRS_Limit");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			deferrals.click_Maximize_IRS_Limit("Standard");	
//			deferrals.select_ContributionType_Standard("Before");
//			deferrals.add_Auto_Increase("Before Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_015_Regular_BEFORE_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_015_Regular_BEFORE_Select_Another_Contribution_Rate");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			
//			if(deferrals.click_Select_Your_Contribution_Rate())
//				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
//			
//			deferrals.select_ContributionType_Standard("Before");
//			deferrals.add_Auto_Increase("Before Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_016_Regular_ROTH_Maximize_To_Company_Match(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_016_Regular_ROTH_Maximize_To_Company_Match");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);		
//			
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2){			
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}			
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();
//			
//			try {
//				common.waitForElement(deferrals,"Table Header Contribution");
//			} catch (Exception e) {			
//				e.printStackTrace();
//			}
//			
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			
//			deferrals.click_MaximizeToCompanyMatch();
//			deferrals.select_ContributionType_Standard("Roth");
//			deferrals.add_Auto_Increase("Roth Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//		}
//		catch(Exception e)
//        {
//            e.printStackTrace();
//            Globals.exception = e;
//            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
//        }
//		catch(Error ae)
//        {
//                        ae.printStackTrace();
//                        Globals.error = ae;
//                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//                        //throw ae;
//        }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
	
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_017_Regular_ROTH_Maximize_IRS_Limit(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_017_Regular_ROTH_Maximize_IRS_Limit");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			deferrals.click_Maximize_IRS_Limit("Standard");	
//			deferrals.select_ContributionType_Standard("Roth");
//			deferrals.add_Auto_Increase("Roth Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_018_Regular_ROTH_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_018_Regular_ROTH_Select_Another_Contribution_Rate");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			
//			if(deferrals.click_Select_Your_Contribution_Rate())
//				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
//			
//			deferrals.select_ContributionType_Standard("Roth");
//			deferrals.add_Auto_Increase("Roth Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	

//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_019_Regular_SPLIT_Select_Another_Contribution_Rate(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_019_Regular_SPLIT_Select_Another_Contribution_Rate");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			if(deferrals.click_Select_Your_Contribution_Rate())
//				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
//			
//			deferrals.select_ContributionType_Standard("Split");
//			deferrals.add_Auto_Increase("Before Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}

	
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_020_Regular_SPLIT_Maximize_To_Company_Match(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_020_Regular_SPLIT_Maximize_To_Company_Match");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);		
//			
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2){			
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}			
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();
//			
//			try {
//				common.waitForElement(deferrals,"Table Header Contribution");
//			} catch (Exception e) {			
//				e.printStackTrace();
//			}
//			
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			
//			deferrals.click_MaximizeToCompanyMatch();
//			deferrals.select_ContributionType_Standard("Split");
//			deferrals.add_Auto_Increase("Before Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//		}
//		catch(Exception e)
//        {
//            e.printStackTrace();
//            Globals.exception = e;
//            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
//        }
//		catch(Error ae)
//        {
//                        ae.printStackTrace();
//                        Globals.error = ae;
//                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//                        //throw ae;
//        }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
//	@Test(dataProvider = "setData")
//	public void SIT_PPTWEB_Deferral_021_Regular_SPLIT_Maximize_IRS_Limit(int itr, Map<String, String> testdata){
//		Stock.globalTestdata = testdata;
////      Globals.GBL_CurrentIterationNumber = itr;
//		try{
//			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_021_Regular_SPLIT_Maximize_IRS_Limit");
//			LeftNavigationBar leftmenu;
//			LoginPage login = new LoginPage();
//			TwoStepVerification mfaPage = new TwoStepVerification(login);
//			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
//				 leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
//			Deferrals deferrals = new Deferrals(leftmenu);
//			deferrals.get();		
//			try { common.waitForElement(deferrals,"Table Header Contribution"); }
//			catch (Exception e) {	e.printStackTrace(); }	
//			if(common.isWebElementDisplayed(deferrals, "Table Header Contribution"))
//				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
//			
//			if(deferrals.clickAddEditButton("Standard Add"))
//				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
//			deferrals.click_Maximize_IRS_Limit("Standard");	
//			deferrals.select_ContributionType_Standard("Split");
//			deferrals.add_Auto_Increase("Before Add Auto Increase");
//			deferrals.myContributions_Confirmation_Page();			
//		}
//		catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        Globals.exception = e;
//	        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
//	    }catch(AssertionError ae)
//	    {
//	        ae.printStackTrace();
//	        Globals.assertionerror = ae;
//	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
//	        //throw ae;
//	    }
//		finally { }
//	    try { Reporter.finalizeTCReport(); }
//	    catch (Exception e1) { e1.printStackTrace(); } 
//	}
	
	
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_009_Catch_up_Maximize_to_the_IRS_limit(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_009_Catch_up_Maximize_to_the_IRS_limit up_Maximize to the IRS limit");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if(homePage.getNoOfPlansFromDB(testdata.get("Particicpant_ssn")) <= 2)			
				 leftmenu = new LeftNavigationBar(homePage);			
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();		
			try { lib.Web.waitForElement(deferrals,"Table Header Contribution"); }
			catch (Exception e) {	e.printStackTrace(); }	
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			if(deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS, "Verify Catch up contribution page", "Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Catch up contribution page", "Catch up Contributions page is not displayed", true);
			
			if(testdata.get("Catchup_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit("Catch Up");
			if(testdata.get("Catchup_Contribution").equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();
				
			deferrals.select_ContributionType_Standard(testdata.get("Contribution_type"));
			if(testdata.get("Catchup_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.catchup_maximize_to_irs();
			else
				deferrals.add_Auto_Increase(testdata.get("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();		
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
		finally { }
	    try { Reporter.finalizeTCReport(); }
	    catch (Exception e1) { e1.printStackTrace(); } 
	}
	
	
	@AfterClass
	public void cleanupSessions() {
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();
	}
}
