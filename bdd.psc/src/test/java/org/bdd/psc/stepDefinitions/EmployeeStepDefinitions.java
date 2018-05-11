/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import gherkin.formatter.model.Scenario;
import lib.CommonLib;
import lib.Web;

import org.bdd.psc.pageobjects.EmployeePage;
import org.bdd.psc.pageobjects.HomePage;
import org.bdd.psc.pageobjects.JumpPage;
import org.bdd.psc.pageobjects.LoginPage;
import org.bdd.psc.pageobjects.UserVerificationPage;

import reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author rvpndy
 *
 */
public class EmployeeStepDefinitions {

	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;
	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	EmployeePage empPage;
	CommonLib commonLib;
	//WebDriver webDriver;


	@Before()
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
	

	
	@Given("^I am on Employee Search Page$")
	public void navigateToEmployeeSearchPage() throws Exception{
		Reporter.initializeReportForTC(1,scenarioName);
		empPage = new EmployeePage(Web.getDriver());
		empPage.get();
	}

	@When("^I select SSN from search filter drop down down$")
	public void selectSSNINFilterSelect(){
		Reporter.logEvent(Status.INFO, "Select SSN from filter box", "Select SSN from filter box", false);
	}

	@And("^I enter correct SSN number in search text box$")
	public void enterSSNInTextBox(DataTable ssn){
		Reporter.logEvent(Status.INFO, "Enter SSN in textbox", "Enter SSN in textbox", false);
	}

	@And("^I click on Go button$")
	public void clickOnGoButtonToFetchResult(){
		empPage.searchEmployeeBySSN();
	}

	@Then("^Employee Search result displays$")
	public void isSearchResultDisplay(){
		if(empPage.isSearchResultDisplayed()){
			Reporter.logEvent(Status.PASS, "Search result displays", 
					"Search result displays", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Search result displays", 
					"Search result do not display", true);
		}

	}

	@Given("^I am on employee search result page$")
	public void employeeSearchResult() throws Exception{
		Reporter.initializeReportForTC(1,scenarioName);
		empPage = new EmployeePage(Web.getDriver());
		//isSearchResultDisplay();
	}

	@When("^I click on employee name link$")
	public void clickOnEmployeeLink(){
		empPage = new EmployeePage(Web.getDriver());
		if(empPage.verifyEmployeeRedirectOverviewPage()){
		Reporter.logEvent(Status.PASS, "User is navigated to employee overview page", 
				"User is navigated to employee overview page", true);
		}
	}

	@Given("^I am on Employee overview page$")
	public void openEmployeeOverViewPage() throws Exception{
		Reporter.initializeReportForTC(1,scenarioName);
		Reporter.logEvent(Status.INFO, "User is on employee overview page", 
				"User is on employee overview page", true);
	}

	@When("^I click on Employee detail tab$")
	public void openEmployeeDetailPage(){
		empPage = new EmployeePage(Web.getDriver());
		empPage.clickOnEmployeeDetailTab();
		Reporter.logEvent(Status.INFO, "User is on employee detail page", 
				"User is on employee detail page", true);
	}

	@And("^I click on Edit button of Basic information$")
	public void clickEditInfoLink(){
		empPage.clickOnEditLink();
		Reporter.logEvent(Status.INFO, "User is on employee detail edit page", 
				"User is on employee detail edit page", true);
	}

	@And("^I update the last name$")
	public void updateLastName(){
		empPage.updateLastNameInInfo();
	}
	
	@And("^I update the first name$")
	public void updateFirstName(){
		empPage.updateFirstNameInInfo();
	}
	
	@And("^I update the middle name$")
	public void updateMidName(){
		empPage.updateMidNameInInfo();
	}

	@And("^I click on Save button$")
	public void saveUpdate(){
		empPage.saveUpdatedInfo();
	}

	@Then("^Updated information is saved$")
	public void verifySaveUpdate(){

	}

	@And("^I can verify the updated information in contact information section$")
	public void verifyInfoChanges(){
		if(empPage.verifyChangesInInfo()){
			Reporter.logEvent(Status.PASS, "Information is updated properly", 
					"Information is updated properly", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Information is updated properly", 
					"Information is not updated properly", true);
		}
	}
	
	@Given("^I am on Home Page$")
	public void i_am_on_Home_Page() throws Throwable {
		Reporter.initializeReportForTC(1,scenarioName);
		HomePage homePage = new HomePage(Web.getDriver());
		homePage.get();
	    
	}

	@When("^I click on Employee menu$")
	public void i_click_on_Employee_menu() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   Reporter.logEvent(Status.INFO, "Navigating to employee search page", 
			   "Navigating to employee search page", false);
	}

	@Then("^I should be navigated to Employee Search page$")
	public void i_should_be_navigated_to_Employee_Search_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		empPage = new EmployeePage(Web.getDriver());
		empPage.get();
	}

	@Then("^Employee overview page opens$")
	public void employee_overview_page_opens() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}





}
