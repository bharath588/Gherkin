/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.util.List;

import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.login.LoginPage;
import bdd_core.framework.Globals;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.Scenario;

/**
 * @author rvpndy
 *
 */
public class ForgotPasswordStepDefinitions {
	public static String featureName=null;

	public static Scenario scenario=null;
	ForgotPasswordPage forgotPwdPage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
			featureName=scenario.getId().split(";")[0];
			Globals.scenarioName=scenario.getName();
			System.out.println(scenario.getId());		
			Reporter.initializeModule(featureName);
			forgotPwdPage = new ForgotPasswordPage();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[scenario.getId().split(";").length-1])-1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
		}
		catch(Exception e){
			try{
				Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[4])-1;
			}
			catch(Exception e1){
				Globals.currentIteration=1;
			}
		}
	}

	@After
	public void after() throws Exception{
		Reporter.finalizeTCReport();
	}

	@Given("^user is on the Forgot Password page$")
	public void user_is_on_the_Forgot_Password_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration,Globals.scenarioName);
		new LoginPage().get();
		ForgotPasswordPage forgotPwdPage = new ForgotPasswordPage();
		forgotPwdPage.get();

	}
	@Given("^user is on the Forgot Password page of \"([^\"]*)\"$")
	public void user_is_on_the_Forgot_Password_page_of(String accucode) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration,Globals.scenarioName);
		LoginPage.setURL(accucode);
		new LoginPage().get();
		ForgotPasswordPage forgotPwdPage = new ForgotPasswordPage();
		forgotPwdPage.get();
	}
	@When("^user clicks on Terms and Conditions link displayed in the footer on Forgot password page$")
	public void user_clicks_on_Terms_and_Conditions_link_displayed_in_the_footer_on_Forgot_password_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ForgotPasswordPage forgotPwdPage = new ForgotPasswordPage();
		forgotPwdPage.clickOnTnCLink();
	}

	@Then("^the Terms and Conditions link should not be displayed in the footer system tray on Forgot password page$")
	public void the_Terms_and_Conditions_link_should_not_be_displayed_in_the_footer_system_tray_on_Forgot_password_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ForgotPasswordPage forgotPwdPage = new ForgotPasswordPage();
		if(forgotPwdPage.isTnCDisplayedInSysTray()){
			Reporter.logEvent(Status.PASS, "Terms and Conditions link does not display in the footer "
					+ "system tray on Forgot password page", "Terms and Conditions link does not "
							+ "display in the footer system tray on Forgot password page", true);
		}
		else{
			Reporter.logEvent(Status.PASS, "Terms and Conditions link does not display in the footer "
					+ "system tray on Forgot password page", "Terms and Conditions link displays in "
							+ "the footer system tray on Forgot password page", true);
		}
	}

	@Then("^new \"([^\"]*)\" are displayed in the footer at forgot password page$")
	public void new_are_displayed_in_the_footer_at_forgot_password_page(@Delimiter(",") List<String> links) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		int count = 0;
		//String[] newLinks = links.split(",");
		for(String link : links){
			if(forgotPwdPage.isLinkDisplayed(link))
				count++;
		}
		if(count==links.size()){
			Reporter.logEvent(Status.PASS, "New links display at footer of Forgot password page", 
					"New links display at footer of Forgot password page", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "New links display at footer of Forgot password page", 
					"New links do not display at footer of Forgot password page", true);
		}
	}

	@Then("^clicking close on pop up returns to forgot password page$")
	public void clicking_close_on_pop_up_returns_to_forgot_password_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if(HomeStepDefinitions.forgotPwdPageVisits==3){
			Reporter.logEvent(Status.PASS, "clicking close on pop up returns to forgot password page", 
					"clicking close on pop up returns to forgot password page", true);
			HomeStepDefinitions.forgotPwdPageVisits = 0;
		}
		else{
			Reporter.logEvent(Status.FAIL, "clicking close on pop up returns to forgot password page", 
					"clicking close on pop up does not return to forgot password page", true);
			HomeStepDefinitions.forgotPwdPageVisits = 0;
		}
	}

}
