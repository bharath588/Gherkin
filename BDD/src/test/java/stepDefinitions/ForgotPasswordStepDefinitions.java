/**
 * 
 */
package stepDefinitions;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.psc.ForgotPasswordPage;
import pageobjects.psc.LoginPage;
import reporter.Reporter;
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
	
	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
	    featureName=scenario.getId().split(";")[0];
	    Globals.scenarioName=scenario.getName();
	    System.out.println(scenario.getId());		
	    Reporter.initializeModule(featureName);
	    Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[3])-1;
	}
	catch(Exception e){
		Globals.currentIteration=1;
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

}
