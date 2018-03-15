package stepDefination;


import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;

import com.aventstack.extentreports.Status;






import pageobjects.login.Login;
import reporter.Reporter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Regression_Steps {
	public static String featureName=null;
public static String scenarioName=null;
public static Scenario scenario=null;
	
@Before
public void before(cucumber.api.Scenario scenario) {
    featureName=scenario.getId().split(";")[0];
    scenarioName=scenario.getName();
    System.out.println(scenario.getId());
    Reporter.initializeModule(featureName);
}
@After
public void after() {
    Login.webDriver.close();
}
	
	
	@Given("^Open firefox and start ppt web application$")
	public void open_firefox_and_start_ppt_web_application() throws Throwable {
		//Scenario scenario=new extent;
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@"+scenario.getName().toString());
		
		Reporter.initializeReportForTC(1,scenarioName);
		Login login =new Login();
	    login.openBrowserAndNavigateToApplication();
	}

	/*@When("^I enter valid username and password$")
	public void i_enter_valid_username_and_password() throws Throwable {
	    System.out.println("Enter UserName and Password");
	    Reporter.logEvent(Status.PASS, "Verify User is Logged in", "user is logged in", true);
	}*/

	@When("^Click on Request a Withdrawal link$")
	public void click_on_Request_a_Withdrawal_link() throws Throwable {
		System.out.println("Click on withdrawal Link");
	}

	@Then("^User should be navigated to Withdrawals Page successfully$")
	public void user_should_be_navigated_to_Withdrawals_Page_successfully() throws Throwable {
		System.out.println("Page Landed successfully");
	}
	
	
	
}
