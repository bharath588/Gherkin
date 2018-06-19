/**
 * 
 */
package org.bdd.psc.stepDefinitions;


import pscBDD.PlanExpress.PlanExpressPage;
import pscBDD.login.LoginPage;
import pscBDD.partnerlinkHomePage.PartnerlinkHomePage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.DataTable;
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
public class PartnerlinkHomeStepDefs {
	public static String featureName = null;
	public static Scenario scenario = null;
	PartnerlinkHomePage plHomePage;
	PlanExpressPage pePage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			plHomePage = new PartnerlinkHomePage();
			pePage = new PlanExpressPage();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			Reporter.initializeModule(featureName);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}

	@Then("^user is on PartnerLink home page$")
	public void user_is_on_PartnerLink_home_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new PartnerlinkHomePage(new LoginPage(), false, new String[] {
				Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password") }).get();
	}

	@Given("^user is on the PartnerLink Home page of accuCode when user login with correct username and password$")
	public void user_is_on_the_PartnerLink_Home_page_of_accuCode_when_user_login_with_correct_username_and_password(
			DataTable accAndCreds) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		if (!accAndCreds.equals(Globals.creds)) {
			Globals.creds = accAndCreds;
			LoginPage.setURL(Web.rawValues(Globals.creds).get(0)
					.get("accuCode"));
			if (Web.isWebElementDisplayed(Web.returnWebElement(new LoginPage(),
					"Logout")))
				Web.clickOnElement(Web.returnWebElement(new LoginPage(),
						"Logout"));
			new PartnerlinkHomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		} else {
			new PartnerlinkHomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		}

	}

	@When("^user selects Implementation / Plan Express from the main nav$")
	public void user_selects_Implementation_Plan_Express_from_the_main_nav()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (plHomePage.isPlanExpressPage()) {
			Reporter.logEvent(Status.PASS, "User is on plan Express page",
					"User is on plan Express page", true);
		} else {
			Reporter.logEvent(Status.PASS, "User is not on plan Express page",
					"User is not on plan Express page", true);
		}

	}

	@Then("^user is presented Welcome to PlanExpress page$")
	public void user_is_presented_Welcome_to_PlanExpress_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (Web.isWebElementDisplayed(plHomePage, "WELCOME TO PLAN EXPRESS",
				true)) {
			Reporter.logEvent(Status.PASS,
					"User is presented with welcome to plan express page",
					"User is presented with welcome to plan express page", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					"User is not presented with welcome to plan express page",
					"User is not presented with welcome to plan express page",
					true);
		}

	}

	@Given("^user has logged into PlanExpress page$")
	public void user_has_logged_into_PlanExpress_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		pePage.get();

	}

	@When("^user selects (\\d+) Plan Pipeline Data$")
	public void user_selects_Plan_Pipeline_Data(int linkNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnPlanPipelineData();
		Reporter.logEvent(Status.INFO, "user clicks on 1 Plan Pipeline Data",
				"user clicks on 1 Plan Pipeline Data", true);

	}

	@Then("^user is presented PlanExpress Add a New Plan page (\\d+)$")
	public void user_is_presented_PlanExpress_Add_a_New_Plan_page(
			int addPlanPageNumber) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (pePage.isAddPlanPage()) {
			Reporter.logEvent(Status.PASS, "User is presented add plan page",
					"User is presented add plan page number"
							+ addPlanPageNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "User is presented add plan page",
					"User is not presented add plan page number"
							+ addPlanPageNumber, true);
		}

	}

	@Given("^user is on PlanExpress Add a New Plan page (\\d+)$")
	public void user_is_on_PlanExpress_Add_a_New_Plan_page(int addPlanPageNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.get();
		pePage.clickOnPlanPipelineData();
		if (pePage.isAddPlanPage()) {
			Reporter.logEvent(Status.PASS, "User is on add plan page",
					"User is on add plan page number" + addPlanPageNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "User is on add plan page",
					"User is not on add plan page number" + addPlanPageNumber,
					true);
		}

	}

	@When("^user selects partner as PartnerLink$")
	public void user_selects_partner_as_PartnerLink(DataTable partnerName)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.selectPartner(Web.rawValues(partnerName).get(0).get("partner"));
		Reporter.logEvent(Status.INFO, "Select a partner", "Select a partner:"
				+ Web.rawValues(partnerName).get(0).get("partner"), true);

	}

	@When("^enters a plan name$")
	public void enters_a_plan_name(DataTable planName) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.enterPlanName(Web.rawValues(planName).get(0).get("planName"));
		Reporter.logEvent(Status.INFO, "Enter plan name", "Enter plan name:"
				+ Web.rawValues(planName).get(0).get("planName"), true);
	}

	@When("^chooses internal Compliance service$")
	public void chooses_internal_Compliance_service(DataTable internalcompliance)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.selectInternalCompliance(Web.rawValues(internalcompliance)
				.get(0).get("internalCompliance"));
		Reporter.logEvent(
				Status.INFO,
				"Chooses internal Compliance service",
				"Chooses internal Compliance service:"
						+ Web.rawValues(internalcompliance).get(0)
								.get("internalCompliance"), true);
	}

	@When("^clicks on Save & Continue$")
	public void clicks_on_Save_Continue() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickSaveAndContinueOnAddPlanPage1();
		Reporter.logEvent(Status.INFO, "Clicks on Save & Continue",
				"Clicks on Save & Continue", true);
	}

	@Given("^user is on PlanExpress Add a New Plan page two$")
	public void user_is_on_PlanExpress_Add_a_New_Plan_page_two()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.isAddPlanPage2();
	}

	@When("^user enters required data fields$")
	public void user_enters_required_data_fields() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.fillAddressOnAddPlanPage2("Sample Company", "Addr1", "addr2",
				"Denver", "Colorado", "80014", "United States");
	}

	@When("^enters a plan name in range for the PartnerLink$")
	public void enters_a_plan_name_in_range_for_the_PartnerLink(String query)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.fillPlanNumber(query);
	}

	@When("^selects Save & Return$")
	public void selects_Save_Return() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnSaveAndReturn();
		Reporter.logEvent(Status.INFO,
				"Clicked on Save and return on add plan page 2",
				"Clicked on Save and return on add plan page 2", true);
	}

	@Then("^user is presented Implementation Checklist page$")
	public void user_is_presented_Implementation_Checklist_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (pePage.isImplementationCheckList())
			Reporter.logEvent(Status.PASS,
					"user is presented Implementation Checklist page",
					"user is presented Implementation Checklist page", true);
		Reporter.logEvent(Status.FAIL,
				"user is presented Implementation Checklist page",
				"user is not presented Implementation Checklist page", true);
	}

	@When("^selects option (\\d+) Complete Plan Data$")
	public void selects_option_Complete_Plan_Data(int optionNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnCompletePlanData();
		Reporter.logEvent(Status.INFO, "Clicks on 2 Complete Plan Data",
				"Clicks on 2 Complete Plan Data", false);
	}

	@When("^selects a \"([^\"]*)\" to complete from list provided$")
	public void selects_a_to_complete_from_list_provided(String ga_ID)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Globals.planNumber=ga_ID;
		pePage.clickOnPlanLink(ga_ID);
		Reporter.logEvent(Status.INFO,
				"Clicks on Plan link from list provided",
				"Clicks on Plan link from list provided", false);
	}

	@When("^on page (\\d+): Enrollment Kits, answers Should a beneficiary form be included in the enrollment books\\?: as \"([^\"]*)\"$")
	public void on_page_Enrollment_Kits_answers_Should_a_beneficiary_form_be_included_in_the_enrollment_books_as(
			int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOn3600EnrollmentKits();
		Reporter.logEvent(Status.INFO, "Clicks on 3600: Enrollment Kits",
				"Clicks on 3600: Enrollment Kits", true);
		pePage.selectYBeneficiaryForm();
		Reporter.logEvent(
				Status.INFO,
				"Answer Y is selected for question \"Should an beneficiary form be included in the enrollment books?\"",
				"Answer Y is selected for question \"Should an beneficiary form be included in the enrollment books?\"",
				false);

	}

	@When("^selects Create forms and update recordkeeping system from Implementation Checklist for that plan$")
	public void selects_Create_forms_and_update_recordkeeping_system_from_Implementation_Checklist_for_that_plan()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnCreateFormsAndUpdate();
		Reporter.logEvent(
				Status.INFO,
				"selects Create forms and update recordkeeping system from Implementation Checklist for that plan",
				"selects Create forms and update recordkeeping system from Implementation Checklist for that plan",
				false);
		if(pePage.isSuccessMessage())
			Reporter.logEvent(Status.PASS, "New plan data is created", "New plan data is created", false);
		else
			Reporter.logEvent(Status.FAIL, "New plan data is created", "New plan data is not created", true);
	}
	
	@Then("^created plan data can be verified with sql below$")
	public void created_plan_data_can_be_verified_with_sql_below(String query) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    pePage.verifyDBValues(query, Globals.planNumber);
	}
	

}
