package org.bdd.psc.stepDefinitions;

import com.aventstack.extentreports.Status;

import pscBDD.accountVerification.accountVerification;
import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class AccountVerificationStepDefinitions {

	public static String featureName = null;
	public static Scenario scenario = null;
	LoginPage login;
	UserVerificationPage userVerPage;
	accountVerification accountverify;
	
	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			login = new LoginPage();
			accountverify = new accountVerification();
			userVerPage= new UserVerificationPage();
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			Reporter.initializeModule(featureName);
			LoginStepDefinitions.scenario = scenario;
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}
	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}
	
	
	@And("^user is on the Account Verification page of accuCode when user login with correct username and password$")
    public void user_is_on_the_account_verification_page_of_accucode_when_user_login_with_correct_username_and_password(DataTable accAndCreds) 
    		throws Throwable {
		if (!accAndCreds.equals(Globals.creds)) {
			Globals.creds = accAndCreds;
			LoginPage.setURL(Web.rawValues(Globals.creds).get(0)
					.get("accuCode"));
			if (Web.isWebElementDisplayed(Web.returnWebElement(new LoginPage(),
					"Logout")))
				Web.actionsClickOnElement(Web.returnWebElement(new LoginPage(),"Logout"));
			new accountVerification(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		} else {
			new accountVerification(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		}
		
		
    }
		
	
	@Then("^three security questions are presented during Account verification$")
	public void three_security_questions_are_presented_during_account_verification()
			throws Throwable {
		if (accountverify.verifySecurityQuestions()) {
			Reporter.logEvent(
					Status.PASS,
					"Validate Security questions displayed in account verififcation page",
					"Security questions displayed in account verififcation page",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validate Security questions displayed in account verififcation page",
					"Security questions displayed in account verififcation page",
					true);
		}
	}
	 
	 
	@Given("^user answers three security questions$")
	public void user_answers_three_security_questions() throws Throwable {
		userVerPage.answerSecurityQuestions();
	}
	
}
