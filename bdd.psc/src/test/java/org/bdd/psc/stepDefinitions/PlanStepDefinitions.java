

package org.bdd.psc.stepDefinitions;

import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import pscBDD.planPage.InvestmentsPerformancePage;
import pscBDD.planPage.LoanInformationPage;
import pscBDD.planPage.PlanPage;
import pscBDD.planPage.PlanProvisionsPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import cucumber.runtime.Runtime;

/**
 * @author rvpndy
 *
 */
public class PlanStepDefinitions {
	
	private static final Class String = null;
	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;
	
	LoginPage login;
	UserVerificationPage userVerPage;
	HomePage homePage;
	PlanPage planPage;
	PlanProvisionsPage provisionsPage;
	InvestmentsPerformancePage investmentPage;
	LoanInformationPage loanInfoPage;
	private static HashMap<Integer,String> buttonPageName=new HashMap<Integer,String>();
	
	@Before
	public void before(cucumber.api.Scenario scenario) {
	    featureName=scenario.getId().split(";")[0];
	    scenarioName=scenario.getName();
	    System.out.println(scenario.getId());
	    provisionsPage=new PlanProvisionsPage();
	    investmentPage=new InvestmentsPerformancePage();
	     loanInfoPage=new LoanInformationPage();
	    Reporter.initializeModule(featureName);
	}
	
	@After
	public void after() throws Exception{
		
		
		if (Globals.exception != null ) {
			
			 Reporter.logEvent(Status.FAIL, "Excpetion ", " "+Globals.exception, true);
		} 
		else if(Globals.assertionerror != null)
		{
			 Reporter.logEvent(Status.FAIL, "assertionerror ", " "+Globals.assertionerror, true);
		}
		Reporter.finalizeTCReport();
	}
	
	@Given("^user is on AF bundled nextgen home page$")
	public void user_is_on_AF_bundled_nextgen_home_page() throws Throwable {
	   try{
		   //Reporter.initializeReportForTC(1,scenarioName);
		   Reporter.logEvent(Status.PASS, "User is on AF bundled nextGen home page", 
				   "User is on AF bundled nextGen home page", true);
	   }
	   catch(Exception e){
		   e.printStackTrace();
	   }
	}

	@When("^user clicks on Plan menu$")
	public void user_clicks_on_Plan_menu() throws Throwable {
		Reporter.logEvent(Status.INFO, "user clicks on Plan menu", 
				   "user clicks on Plan menu", true);
	}

	@Then("^Investment and Performance is renamed to Investment Results$")
	public void investment_and_Performance_is_renamed_to_Investment_Results() throws Throwable {
		planPage = new PlanPage(Web.getDriver());
	    if(planPage.isInvestmentResultsDisplays())
	    	Reporter.logEvent(Status.PASS, "Investment and Performance is renamed to Investment Results", 
	    			"Investment and Performance is renamed to Investment Results", true);
	    login = new LoginPage(Web.getDriver());
		login.logout();
	}

	@When("^user clicks on Investment Results under Plan menu$")
	public void user_clicks_on_Investment_Results_under_Plan_menu() throws Throwable {
	    Reporter.logEvent(Status.INFO, "user clicks on Investment Results under Plan menu", 
	    		"user clicks on Investment Results under Plan menu", true);
	}

	@Then("^Investment Results page is loaded$")
	public void investment_Results_page_is_loaded() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Reporter.logEvent(Status.INFO, "Investment Results page is loaded", 
	    		"Investment Results page is loaded", true);
	}

	@Then("^breadcrumb displays Investment Results$")
	public void breadcrumb_displays_Investment_Results() throws Throwable {
		planPage = new PlanPage(Web.getDriver());
		if(planPage.isInvestmentResultsBreadcrumb()){
			Reporter.logEvent(Status.PASS, "breadcrumb displays Investment Results",
					"breadcrumb displays Investment Results", true);
		}
		
	}
	
	@When("^user selects the \"([^\"]*)\" option under the Plan Menu$")
	public void user_selects_the_option_under_the_Plan_Menu(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		planPage = new PlanPage(Web.getDriver());
		planPage.isInvestmentResultsDisplays();
		if(planPage.isInvestmentResultsBreadcrumb()){
			Reporter.logEvent(Status.PASS, "breadcrumb displays Investment Results",
					"breadcrumb displays Investment Results", true);
		}
	}
	
	@Then("^the tab label Asset Allocation Model should be \"([^\"]*)\"$")
	public void the_tab_label_Asset_Allocation_Model_should_be(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		planPage = new PlanPage(Web.getDriver());
		if(planPage.correctLabelForAssetAlocation(arg1)){
			Reporter.logEvent(Status.PASS, "Asset allocation tab is:"+arg1, "Asset allocation tab is:"+arg1, true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Asset allocation tab is:"+arg1, "Asset allocation tab is not:"+arg1, true);
		}
	    
	}
	
	@When("^user click to \"([^\"]*)\"$")
	public void user_click_to_button(@Delimiter(",") List<String> buttonName)
			throws Throwable {
		int keyVal=0;
		if (!(buttonPageName.isEmpty()))
			buttonPageName.clear();
		for (String name : buttonName) {
			try {
				String pageNameHeaderText = provisionsPage.gettingButtonPageName(name);
				if(pageNameHeaderText!=null){
					buttonPageName.put(++keyVal, pageNameHeaderText.trim().toLowerCase());
				}
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	@Then("^the \"([^\"]*)\" page should be displayed$")
	public void the_something_page_should_be_displayed(
			@Delimiter(",") List<String> buttonPageFromFeatureFile) throws Throwable {
		try {
			for(String page:buttonPageFromFeatureFile){
				if(buttonPageName.containsValue(page.trim().toLowerCase())){
					Reporter.logEvent(Status.PASS, page+" page should displays",page+" is displays", true);
				}else{
					
					Reporter.logEvent(Status.FAIL,page+" page should displays", page+" isn't displays", true);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Given("^user is on the Plan Provisions page of \"([^\"]*)\"$")
    public void user_is_on_the_plan_provisions_page_of_planNo(String planno) throws Throwable {
       new HomePage().switchPlan(planno);
       Web.nextGenDriver.waitForAngular();
       provisionsPage.get();
    }
	@When("^user is on \"([^\"]*)\" page$")
    public void user_is_on_investments_page(String investments) throws Throwable {
       investmentPage.get();
       Web.nextGenDriver.waitForAngular();
    }
	@Then("^the \"([^\"]*)\" tab should be displayed$")
    public void the_fixedrate_tab_should_be_displayed(String fixedrate) throws Throwable {
		if(investmentPage.isCorrectTabName(fixedrate)){
			Reporter.logEvent(Status.PASS, "The "+fixedrate+" tab should displays", "The "+fixedrate+" tab is displays", true);
		}else{
			
			Reporter.logEvent(Status.FAIL, "The "+fixedrate+" tab should displays", "The "+fixedrate+" tab isn't displays", true);
		}
        
    }
	@When("^user is on \"([^\"]*)\" tab$")
    public void user_is_on_something_tab(String fixedrate) throws Throwable {
		
    }
	@Then("^the \"([^\"]*)\" label should be displayed$")
    public void the_interestRate_label_should_be_displayed(String interestRate) throws Throwable {
		if(investmentPage.isCorrectIntrestRateLableName(interestRate)){
			Reporter.logEvent(Status.PASS, "The "+interestRate+" label should displays", "The "+interestRate+" tab is displays", true);
		}else{
			
			Reporter.logEvent(Status.FAIL, "The "+interestRate+" label should displays", "The "+interestRate+" tab isn't displays", true);
		}
    }
	
	@When("^user clicks Loan Information button on Plan Provision page$")
    public void user_clicks_loan_information_button_on_plan_provision_page() throws Throwable {
		loanInfoPage.get();
    }
	
	@Then("^the Loan Information page should be displayed$")
    public void the_loan_information_page_should_be_displayed() throws Throwable {
		if(loanInfoPage.isLoanInformationPage()){
			Reporter.logEvent(Status.PASS, "Loan Information page should displays", "Loan Information page is displays", true);
		}else{		
			Reporter.logEvent(Status.FAIL, "Loan Information page should displays", "Loan Information page isn't displays", true);
		}
    }
	@Then("^the selected \"([^\"]*)\" should be displayed on the page$")
    public void the_selected_planno_should_be_displayed_on_the_page(String planno) throws Throwable {
		if(homePage.getPlanNumberFromPageHeader().equals(planno.trim())){
			Reporter.logEvent(Status.PASS, "selected plan number should displays", "selected plan number is displays", true);
		}else{		
			Reporter.logEvent(Status.FAIL, "selected plan number should displays", "selected plan number isn't displays", true);
		}
    }
	
	@Then("^the expected_text label should displays$")
    public void the_expectedtext_label_should_displays(DataTable dt) throws Throwable {
       List<Map<String,String>> input=Web.rawValues(dt);
       List<String> labelInput=new ArrayList<String>();
       for(int i=0;i<input.size();i++)
    	   labelInput.add(input.get(i).get("expected_text"));
        if(loanInfoPage.isLabelDisplaysInLoanInformationPage(labelInput)){
			Reporter.logEvent(Status.PASS, "selected plan number should displays", "selected plan number is displays", true);
		}else{		
			Reporter.logEvent(Status.FAIL, "selected plan number should displays", "selected plan number isn't displays", true);
		}
    }
	String maxLoanValue="";
	@When("^in DB max_loans_allowed column of table GRP_LOAN_TERM is Null for \"([^\"]*)\"$")
	public void in_db_maxloansallowed_column_of_table_grploanterm_is_null_for_planNo(
			String planno, String query) throws Throwable {
		maxLoanValue=loanInfoPage.get_max_loans_allowed(query,planno);

	}
	@When("^in DB max_loans_allowed column of table GRP_LOAN_TERM is not Null for \"([^\"]*)\"$")
    public void in_db_maxloansallowed_column_of_table_grploanterm_is_not_null_for_planNo(String planno, String query) throws Throwable {
		maxLoanValue=loanInfoPage.get_max_loans_allowed(query,planno);
    }


	@Then("^the Restriction on number of loans allowed should display \"([^\"]*)\"$")
	public void the_restriction_on_number_of_loans_allowed_should_display_NA(
			String expValue) throws Throwable {
		if (loanInfoPage.verifyRestrictionNumberOfLoansAllowedValue(expValue)) {
			Reporter.logEvent(Status.PASS,
					"Restriction on number of loans allowed displays N/A",
					"Restriction on number of loans allowed displays N/A", true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Restriction on number of loans allowed displays N/A",
					"Restriction on number of loans allowed don't displays N/A",
					true);
		}
	}

	@Then("^the Restriction on number of loans allowed should display the corresponding value in DB$")
	public void the_restriction_on_number_of_loans_allowed_should_display_the_corresponding_value_in_db()
			throws Throwable {
		if (loanInfoPage.verifyRestrictionNumberOfLoansAllowedValue(maxLoanValue)) {
			Reporter.logEvent(Status.PASS,
					"Restriction on number of loans allowed displays the value in DB",
					"Restriction on number of loans allowed displays the value in DB", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Restriction on number of loans allowed displays the value in DB",
					"Restriction on number of loans allowed DON'T displays the value in DB", true);
		}
	}
	@When("^user clicks Plan, Plan Provisions$")
    public void user_clicks_plan_plan_provisions() throws Throwable {
		provisionsPage.get();
    }
	@Then("^the Plan Provision page is displayed$")
    public void the_plan_provision_page_is_displayed() throws Throwable {
		if(provisionsPage.isPlanProvisionPage()){
			Reporter.logEvent(Status.PASS, " Plan Provision page displays"," Plan Provision page displays", true);
		}else{		
			Reporter.logEvent(Status.FAIL, " Plan Provision page displays"," Plan Provision page isn't displays", true);
		}
    }
	
	@When("^user clicks Loans button$")
    public void user_clicks_loans_button() throws Throwable {
		provisionsPage.clickOnLoanButton();
    }

	@Then("^Loan provision page is displayed$")
	public void loan_provision_page_is_displayed() throws Throwable {
		if (loanInfoPage.isLoanInformationPage()) {
			Reporter.logEvent(Status.PASS, " Loan provision page displays",
					" Loan provision page displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, " Loan provision page displays",
					" Loan provision page isn't displays", true);
		}
	}

	@Then("^The \"([^\"]*)\" value is displayed on header$")
	public void the_LoanRequiredPriorToHardship_value_is_displays(
			String value) throws Throwable {
		if (loanInfoPage.verifyLoanRequiredPriorToHardshipValue(value)) {
			Reporter.logEvent(Status.PASS, " Loans required prior to hardship value displays",
					" Loans required prior to hardship value displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, " Loans required prior to hardship value displays",
					" Loans required prior to hardship value is't displays", true);
		}
	}
	@Then("^The \"([^\"]*)\" value is displayed$")
	public void the_LoanRestrictionHardshipReasons_value_is_displays(
			String value) throws Throwable {
		if (loanInfoPage.verifyLoanRestrictionHardshipReasonsValue(value)) {
			Reporter.logEvent(Status.PASS, " Loan restrictions to hardship reasons value displays",
					" Loan restrictions to hardship reasons value displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, " Loan restrictions to hardship reasons value displays",
					" Loan restrictions to hardship reasons value is't displays", true);
		}
	}




}

