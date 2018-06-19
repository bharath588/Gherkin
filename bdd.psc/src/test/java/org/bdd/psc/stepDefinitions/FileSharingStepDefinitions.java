/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import gherkin.formatter.model.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * @author rvpndy
 *
 */
public class FileSharingStepDefinitions {
	
	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;

	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	ForgotPasswordPage forgotPwdPage;

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
	
	@Then("^user is on filesharing page$")
	public void user_is_on_filesharing_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.logEvent(Status.PASS, "User is on File Sharing landing page", "User is on File Sharing landing page", true);
	}
	
	@Given("^the user has files within a folder$")
	public void the_user_has_files_within_a_folder() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.initializeReportForTC(1, scenarioName, 
	    		"verify appropriate checkboxes are selected");
	    
	}

}
