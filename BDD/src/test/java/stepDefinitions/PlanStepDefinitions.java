/**
 * 
 */
package stepDefinitions;

import lib.Web;

import com.aventstack.extentreports.Status;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageobjects.psc.HomePage;
import pageobjects.psc.LoginPage;
import pageobjects.psc.PlanPage;
import pageobjects.psc.UserVerificationPage;
import reporter.Reporter;
import gherkin.formatter.model.Scenario;

/**
 * @author rvpndy
 *
 */
public class PlanStepDefinitions {
	
	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;
	
	LoginPage login;
	UserVerificationPage userVerPage;
	HomePage homePage;
	PlanPage planPage;
	
	@Before
	public void before(cucumber.api.Scenario scenario) {
	    featureName=scenario.getId().split(";")[0];
	    scenarioName=scenario.getName();
	    System.out.println(scenario.getId());
		
	    Reporter.initializeModule(featureName);
	}
	
	@After
	public void after() throws Exception{
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

}
