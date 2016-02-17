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


	
	/**
	 * Thee following scripts Add Other deferral type
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_009_Other_Select another contribution rate
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Other(int itr, Map<String, String> testdata){
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
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			if(deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS, "Verify Other contribution page", "Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Other contribution page", "Other Contributions page is not displayed", true);
			if(deferrals.click_Select_Your_Contribution_Rate())
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution Rate", "My Contribution Rate value is not matching", true);
			
			deferrals.select_ContributionType(Stock.GetParameterValue("Contribution_type"));
			deferrals.add_Auto_Increase(Stock.GetParameterValue("Add_auto_increase_type"));
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
	
	/**
	 * Thee following script checks if the participant is available for Bonus type deferral
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_003_Bonus_Participant not eligible for Bonus type contribution
	 */
	@Test(dataProvider = "setData")
	public void Participant_not_elegible_for_Bonus_type_contribution(int itr, Map<String, String> testdata){
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
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			if(deferrals.check_if_participant_eligible_for_deferral_type("Bonus"))
				Reporter.logEvent(Status.PASS, "Check if Participant eligible for bonus type contribution", "Participant not eligible for bonus type contribution", false);
			else
				Reporter.logEvent(Status.FAIL, "Check if Participant eligible for bonus type contribution", "Participant eligible for bonus type contribution", true);
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
	
	/**
	 * The participant selects the Maximize me always option for Standard deferral type
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_020_Regular_SPLIT- Change of Maximized with Catchup to Maximize me always 
	 */
	@Test(dataProvider = "setData")
	public void Regular_SPLIT_Maximize_me_always(int itr, Map<String, String> testdata){
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
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			if(deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
			
			deferrals.click_Maximize_IRS_Limit("Standard");
			
			if(lib.Web.isWebElementDisplayed(deferrals, "Maximize Checkbox"))
				Reporter.logEvent(Status.PASS, "Check if Maximize me always check box is present", "Maximize me always check box is present", false);
			else
				Reporter.logEvent(Status.FAIL, "Check if Maximize me always check box is present", "Maximize me always check box is not present", true);
			
			deferrals.regular_maximize_me_always("Yes");
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
	
	/**
	 * The following script adds or edits all the deferral typed and then confirm it
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_020_Multiple_Create contribution rate for multiple deferral type, split contribution 
	 * 2.SIT_PPTWEB_Deferral_021_Multiple_Change contribution rate for multiple deferral type, split contributions
	 */
	@Test(dataProvider = "setData")
	public void Multiple_deferral_split_contribution(int itr, Map<String, String> testdata){
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
			if(lib.Web.isWebElementDisplayed(deferrals, "Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
			
			//Creating or editing Standard deferral
			if(Stock.GetParameterValue("CreateOrEdit").equalsIgnoreCase("Create")){
				if(deferrals.clickAddEditButton("Standard Add"))
					Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
			}
			else{
				if(deferrals.clickAddEditButton("Standard Edit"))
					Reporter.logEvent(Status.PASS, "Verify Standard contribution page", "Standard Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Standard contribution page", "Standard Contributions page is not displayed", true);
			}
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("Split");
			lib.Web.clickOnElement(deferrals, "Continue button");
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Split_Tax_roth"), "ROTH deferral", "Standard"))
				Reporter.logEvent(Status.PASS, "Verify Roth contribution percent for Standar deferral", "Roth contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Roth contribution percent for Standar deferral", "Roth contribution percent not matching", true);
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Split_Tax_before"), "Before-tax", "Standard"))
				Reporter.logEvent(Status.PASS, "Verify before contribution percent for Standar deferral", "before contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify before contribution percent for Standar deferral", "before contribution percent not matching", true);
			
			//Create or edit Catch up deferrals
			if(Stock.GetParameterValue("CreateOrEdit").equalsIgnoreCase("Create")){
				if(deferrals.clickAddEditButton("Catch Up Add"))
					Reporter.logEvent(Status.PASS, "Verify Catch up contribution page", "Standard Catch up page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Catch up contribution page", "Standard Catch up page is not displayed", true);
			}
			else{
				if(deferrals.clickAddEditButton("Catch Up Edit"))
					Reporter.logEvent(Status.PASS, "Verify Catch up contribution page", "Standard Catch up page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Catch up contribution page", "Standard Catch up page is not displayed", true);
			}
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("Split");
			lib.Web.clickOnElement(deferrals, "Continue button");
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Split_Tax_roth"), "Age Catch-Up Roth", "Catchup"))
				Reporter.logEvent(Status.PASS, "Verify Roth contribution percent for Catchup deferral", "Roth contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Roth contribution percent for Catchup deferral", "Roth contribution percent not matching", true);
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Split_Tax_before"), "Age catch-up before tax", "Catchup"))
				Reporter.logEvent(Status.PASS, "Verify before contribution percent for Catchup deferral", "before contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify before contribution percent for Catchup deferral", "before contribution percent not matching", true);
			
			//Create or edit after tax
			if(Stock.GetParameterValue("CreateOrEdit").equalsIgnoreCase("Create")){
				if(deferrals.clickAddEditButton("After Tax Add"))
					Reporter.logEvent(Status.PASS, "Verify After Tax contribution page", "After Tax page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify After Tax contribution page", "After Tax page is not displayed", true);
			}
			else{
				if(deferrals.clickAddEditButton("After Tax Edit"))
					Reporter.logEvent(Status.PASS, "Verify After Tax contribution page", "After Tax page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify After Tax contribution page", "After Tax page is not displayed", true);
			}
			deferrals.click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(deferrals, "Continue button");
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Contribution Rate"), "AFTRTX", "Aftertax"))
				Reporter.logEvent(Status.PASS, "Verify after tax contribution percent for AfterTax deferral", "after tax contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify after tax contribution percent for AfterTax deferral", "after tax contribution percent not matching", true);
			
			//Create or edit Bonus deferrals
			if(Stock.GetParameterValue("CreateOrEdit").equalsIgnoreCase("Create")){
				if(deferrals.clickAddEditButton("Bonus Add"))
					Reporter.logEvent(Status.PASS, "Verify Bonus contribution page", "Bonus page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Bonus contribution page", "Bonus page is not displayed", true);
			}
			else{
				if(deferrals.clickAddEditButton("Bonus Edit"))
					Reporter.logEvent(Status.PASS, "Verify Bonus contribution page", "Bonus page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Bonus contribution page", "Bonus page is not displayed", true);
			}
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("After");
			lib.Web.clickOnElement(deferrals, "Continue button");
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Contribution Rate"), "After Tax Bonus ", "Bonus"))
				Reporter.logEvent(Status.PASS, "Verify after tax contribution percent for Bonus deferral", "after tax contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify after tax contribution percent for Bonus deferral", "after tax contribution percent not matching", true);
			
			//create or edit other deferral
			if(Stock.GetParameterValue("CreateOrEdit").equalsIgnoreCase("Create")){
				if(deferrals.clickAddEditButton("Other Add"))
					Reporter.logEvent(Status.PASS, "Verify Other contribution page", "Other page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Other contribution page", "Other page is not displayed", true);
			}
			else{
				if(deferrals.clickAddEditButton("Other Edit"))
					Reporter.logEvent(Status.PASS, "Verify Other contribution page", "Other page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Other contribution page", "Other page is not displayed", true);
			}
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("Split");
			lib.Web.clickOnElement(deferrals, "Continue button");
			if(deferrals.verifyMyContributions(Stock.GetParameterValue("Contribution Rate"), "AFTRTXVR", "Other"))
				Reporter.logEvent(Status.PASS, "Verify after tax vr contribution percent for Other deferral", "Roth contribution percent matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify after tax vr contribution percent for Other deferral", "Roth contribution percent not matching", true);
			
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch UP Add Auto Increase");
			deferrals.add_Auto_Increase("Bonus Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add Auto Increase");
			
			lib.Web.clickOnElement(deferrals, "Confirm button");
			
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Split_Tax_before"), "Before-tax", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Split_Tax_roth"), "Roth deferral", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Split_Tax_before"), "Age catch-up before tax", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Split_Tax_roth"), " Age Catch-Up Roth", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Contribution Rate"), "AFTRTX", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Contribution Rate"), "After Tax Bonus", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			deferrals.verifyContributionDetails(Stock.GetParameterValue("Contribution Rate"), "AFTRTXVR", Stock.GetParameterValue("Auto Increase Contribution Percent"), Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
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
	public void SIT_PPTWEB_Deferral_003_View_only_Standard_with_changes_allowed_deferral(
		int itr, Map<String, String> testdata) {
		Stock.globalTestdata = testdata;
		// Globals.GBL_CurrentIterationNumber = itr;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
	
			deferrals.View_only_Standard_with_changes(Stock
					.GetParameterValue("Contribution Rate"));
	
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	
		}
	}
	
    @Test(dataProvider = "setData")
    public void SIT_PPTWEB_Deferral_002_View_only_After_tax_with_no_split_contributions(int itr, Map<String, String> testdata){
           Stock.globalTestdata = testdata;
           //      Globals.GBL_CurrentIterationNumber = itr;
           try{
                  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
                  LeftNavigationBar leftmenu;
                  LoginPage login = new LoginPage();
                  TwoStepVerification mfaPage = new TwoStepVerification(login);
                  LandingPage homePage = new LandingPage(mfaPage);
//                if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)                     
//                      leftmenu = new LeftNavigationBar(homePage);                   
//                else {
//                      MyAccountsPage accountPage = new MyAccountsPage(homePage);
//                      leftmenu = new LeftNavigationBar(accountPage);
//                }
                  leftmenu = new LeftNavigationBar(homePage);
                  Deferrals deferrals = new Deferrals(leftmenu);
                  deferrals.get();           
                  if(deferrals.checkAftertaxOptionNotdisplayed())
                  {
                        Reporter.logEvent(Status.FAIL, "Check if after tax option is displayed in contributions page",
                                      "The after tax option is displayed", true);
                  }
                  else
                  {
                        Reporter.logEvent(Status.PASS, "Check if after tax option is displayed in contributions page",
                                      "The after tax option is not displayed", false);
                  }
                  
                  
                  if(lib.Web.isWebElementDisplayed(deferrals, "Edit Btn Aftertax"))
                  {
                        Reporter.logEvent(Status.FAIL, "Check if edit button is displayed for after tax with no split contributions",
                                      "The edit button is displayed for after tax with no split contributions", true);
                  }
                  else
                  {
                        Reporter.logEvent(Status.PASS, "Check if edit button is displayed for after tax with no split contributions",
                                      "The edit button is not displayed for after tax with no split contributions", true);
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
           try { Reporter.finalizeTCReport(); }
           catch (Exception e1) { e1.printStackTrace(); } 
           
    }
    }


	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_001_View_only_Catchup_with_split_contributions(int itr, Map<String, String> testdata) {
		Stock.globalTestdata = testdata;
		// Globals.GBL_CurrentIterationNumber = itr;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
 
			deferrals.Catchup_with_split_contributions(Stock
					.GetParameterValue("Contribution Rate"));

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
		try { Reporter.finalizeTCReport(); }
		catch (Exception e1) { e1.printStackTrace(); } 
		
	}
	}
	 			

	@AfterClass
	public void cleanupSessions() {
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();
	}
}
