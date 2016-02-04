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
import lib.Web;
import lib.Reporter.Status;
import pageobjects.general.*;
import pageobjects.login.*;
import pageobjects.deferrals.Deferrals;
import pageobjects.deferrals.PriorPlanContributions;
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
           lib.Web.webdriver = Web.getWebDriver(Stock.globalParam.get("BROWSER"));
    }

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__AfterTax(int itr, Map<String, String> testdata){

		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//				leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
			
			
			leftmenu = new LeftNavigationBar(homePage);
			
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
	public void SIT_PPTWEB_Deferral__Bonus(int itr, Map<String, String> testdata){
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//				leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
			
			leftmenu = new LeftNavigationBar(homePage);
			
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

			deferrals.select_ContributionType("After");
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
	public void SIT_PPTWEB_Deferral__Regular(int itr, Map<String, String> testdata){

		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);	

//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Participant ssn")) <= 2)
//				leftmenu = new LeftNavigationBar(homePage);
//			else{
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
			
			leftmenu = new LeftNavigationBar(homePage);
			
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

			if(lib.Stock.GetParameterValue("Standard_Contribution").equalsIgnoreCase("Maximize to company match"))
				deferrals.click_MaximizeToCompanyMatch();
			if(lib.Stock.GetParameterValue("Standard_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit("Standard");
			if(lib.Stock.GetParameterValue("Standard_Contribution").equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();


			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			if(!lib.Stock.GetParameterValue("Standard_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock.GetParameterValue("Add_auto_increase_type"));
			
			deferrals.myContributions_Confirmation_Page();			
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", true);
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

	



	


	

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Catch_up(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
		//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//				leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
			leftmenu = new LeftNavigationBar(homePage);
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

			if(lib.Stock.GetParameterValue("Catchup_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit("Catch Up");
			if(lib.Stock.GetParameterValue("Catchup_Contribution").equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			
			if(!lib.Stock.GetParameterValue("Catchup_Contribution").equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock.GetParameterValue("Add_auto_increase_type"));
			
			deferrals.myContributions_Confirmation_Page();	
			
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
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
	public void SIT_PPTWEB_Deferral_006_Previous_Contributions_Participant_hired_in_prior_year(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
		//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
				leftmenu = new LeftNavigationBar(homePage);			
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			PriorPlanContributions priorContributions = new PriorPlanContributions(leftmenu);
			priorContributions.get();

			if(priorContributions.verifyParticipantsHiredInPriorYear())
				Reporter.logEvent(Status.PASS, "Verify if Participant was hired in previous year", "Participant hired in previous year", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Participant was hired in previous year", "Participant not hired in previous year", true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
		}
		catch(AssertionError ae)
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


	/**
	 * Thee following scripts test for the prior contribution details of the participants that are in the following category
	 * 1. Participant in the first year of joining the plan or first year of the employment
	 * 2. Participant in the first year of catchup contribution
	 * 3. Participant who is not in the first year of employment
	 * 
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_008_Prior Plan Contributions_Participant not in first year of employment
	 * 2.SIT_PPTWEB_Deferral_007_Prior Plan Contributions_Catchup - participant within first year of employment
	 * 3.SIT_PPTWEB_Deferral_006_Previous Contributions_Participant hired in current year
	 */

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//				leftmenu = new LeftNavigationBar(homePage);			
//			else {
//				MyAccountsPage accountPage = new MyAccountsPage(homePage);
//				leftmenu = new LeftNavigationBar(accountPage);
//			}
			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(leftmenu);

			//logic to verify the prior plan contribution functionality with 3 combination of manual test cases listed above in comments
			if (lib.Stock.GetParameterValue("Period_of_Participation").equalsIgnoreCase("Not_In_First_Year")) {

				if (lib.Web.isWebElementDisplayed(leftmenu,"Prior plan contributions" )) {
					Reporter.logEvent(Status.FAIL, "Prior plan contributions link visibility", 
							"participant is unable to find the Prior plan contributions link since the participant is not in his first year of employment", true);
				} else {
					Reporter.logEvent(Status.PASS, "Prior plan contributions link visibility", 
							"participant is able to find the Prior plan contributions link even though the participant is not in his first year of employment. Please check the test data", true);
				}
			} else {
				//Navigate to the prior plan contribution
				priorContributions.get();
				//perform a initial check on the prior plan contribution
				priorContributions.verifyPriorPlanContributionsPage();
				//enter the year to date and catchup contribution value
				priorContributions.enterContributionValue(lib.Stock.GetParameterValue("yearToDateContribution"), lib.Stock.GetParameterValue("catchupContribution"));

				if (priorContributions.verifyConfirmationDetails(lib.Stock.GetParameterValue("yearToDateContribution"), "Year To Date")) {
					Reporter.logEvent(Status.PASS, "Verify Year to Date Confirmation Detials verification", 
							"Test data for Year to Date value matched with test data on the confirmation page", true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Year to Date Confirmation Detials verification", 
							"Test data for Year to Date value DID NOT matched with test data on the confirmation page", true);
				}
				//verify the values for the year to date and catchup contribution value on the confirmtion page
				if (priorContributions.verifyConfirmationDetails(lib.Stock.GetParameterValue("catchupContribution"), "Catch up")) {
					Reporter.logEvent(Status.PASS, "Verify catchup Contribution Confirmation Detials verification", 
							"Test data for catchup Contribution value matched with test data on the confirmation page", true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify catchup Contribution Confirmation Detials verification", 
							"Test data for catchup Contribution value DID NOT matched with test data on the confirmation page", true);
				}				
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
		}
		catch(AssertionError ae)
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
	public void SIT_PPTWEB_Deferral_016_Multiple_Create_contribution_rate_for_multiple_deferral_type_split_contribution(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
		//      Globals.GBL_CurrentIterationNumber = itr;
		try{
			Reporter.initializeReportForTC(itr, "SIT_PPTWEB_Deferral_009_Catch_up_Maximize_to_the_IRS_limit up_Maximize to the IRS limit");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
				leftmenu = new LeftNavigationBar(homePage);			
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();



		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
		}
		catch(AssertionError ae)
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
