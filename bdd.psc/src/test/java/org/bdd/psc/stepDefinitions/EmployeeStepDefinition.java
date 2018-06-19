/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.util.Arrays;
import java.util.List;






import pscBDD.employee.EmployeePages;
import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import bdd_core.framework.Globals;
import bdd_reporter.Reporter;
import cucumber.api.DataTable;
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
public class EmployeeStepDefinition {
	public static String featureName = null;
	public static Scenario scenario = null;
	HomePage homePage;
	EmployeePages employeePage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			System.out.println(scenario.getId());
			homePage = new HomePage();
			employeePage = new EmployeePages();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			System.out.println("Current iteration is: "
					+ Globals.currentIteration);
			Reporter.initializeModule(featureName);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}

	@Given("^user is on Employee Search page of \"([^\"]*)\" when login with correct \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_is_on_Employee_Search_page_of_when_login_with_correct_and(
			String accuCode, String username, String password) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		List<List<String>> accuWithCreds = Arrays.asList(
				Arrays.asList("accuCode", "username", "password"),
				Arrays.asList(accuCode, username, password));
		DataTable dataTable = DataTable.create(accuWithCreds);
		Globals.creds = dataTable;
		LoginPage.setURL(accuCode);
		employeePage.get();

	}

	@When("^user selects search employee by \"([^\"]*)\"$")
	public void user_selects_search_employee_by(String searchBy)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.setSearchBy(searchBy);
	}

	@When("^enters \"([^\"]*)\"$")
	public void enters(String searchText) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.enterSearchText(searchText);
	}

	@When("^hits Go to search$")
	public void hits_Go_to_search() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.clickOnSearchButton();
	}

	@When("^clicks on last name link$")
	public void clicks_on_last_name_link() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.openEmployeeOverviewPage();
	}

	@When("^clicks on View button for a \"([^\"]*)\"$")
	public void clicks_on_View_button_for_a(String planNumber) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.clickOnViewButtonOfPlan(planNumber);
		EmployeePages.planNumber = planNumber;
	}

	@When("^selects Return to employee overview$")
	public void selects_Return_to_employee_overview() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		employeePage.returnToEmployeeOverviewPage();
	}

	@Then("^data under \"([^\"]*)\" reflects data for the plan displayed in header on employee overview page$")
	public void data_under_reflects_data_for_the_plan_displayed_in_header_on_employee_overview_page(
			@Delimiter(",") List<String> labels) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
		employeePage.clickOnTopLevelTabsAndVerifyPlanHeader(labels);

	}

}
