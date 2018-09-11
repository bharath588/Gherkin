
package org.bdd.psc.stepDefinitions;

import gherkin.formatter.model.Scenario;

import java.util.List;

import org.testng.Assert;

import pscBDD.employee.EmployeePage;
import pscBDD.employee.EmployeePages;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.planPage.PlanProvisionsPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_lib.CommonLib;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;
//import com.gargoylesoftware.htmlunit.javascript.host.media.webkitMediaStream;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.PendingException;
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
	EmployeePages empPages;
	String planNumber=null;
	PlanProvisionsPage provisionPage;


	@Before()
	public void before(cucumber.api.Scenario scenario) {
		
			featureName=scenario.getId().split(";")[0];
			scenarioName=scenario.getName();
			System.out.println(scenario.getId());
			Reporter.initializeModule(featureName);
			empPages=new EmployeePages();
			provisionPage=new PlanProvisionsPage();
			homePage=new HomePage();
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
	@Given("^User is on Employee Search Page$")
	public void userNavigateToEmployeeSearchPage() throws Exception{
		//Reporter.initializeReportForTC(1,scenarioName);
		//empPage = new EmployeePage(Web.getDriver());
		empPages.get();
	}
	@When("^user clicks Employee$")
    public void user_clicks_on_employee() throws Throwable {
        
    }
	
	@And("^User search and selects the plan \"([^\"]*)\" with Vesting Service level as \"([^\"]*)\" in Plan Provisions page$")
	public void user_search_and_selects_the_plan_with_vesting_service_level_in_plan_provisions_page(
			String planno, String vestinglevel) throws Throwable {
		homePage.switchPlan(planno);
		provisionPage.get();
		if(!provisionPage.verifyVestingServiceLevel(vestinglevel)){
			Reporter.logEvent(Status.FAIL,
					"Vesting Service level should set with: " + vestinglevel,
					"Vesting Service level isn't set with: " + vestinglevel,
					true);
		Assert.assertTrue(false);
		}
			
	}

	@When("^User is landing on Employee Search Page$")
	public void user_is_landing_on_Employee_SearchPage() throws Exception {
		// Reporter.initializeReportForTC(1,scenarioName);
		//empPage = new EmployeePage(Web.getDriver());
		empPages.get();
	}

	@When("^User search employee by \"([^\"]*)\"  and select an employee with \"([^\"]*)\"$")
	public void user_search_employee_by_dropdown_value_and_select_an_employee(
			String searchBy, String searchVal) throws Throwable {
		empPages.searchEmployeeBy(searchBy, searchVal);
		empPages.clickOnFirstNameLinkInSearchResult();
	}

	@When("^user search for an employee using \"([^\"]*)\" having loan_type loans with \"([^\"]*)\"$")
	public void user_search_for_an_employee_using_ind_id_having_loantype_loans_with_something(
			String indid, String searchBy) throws Throwable {
		empPages.searchEmployeeBy(searchBy, indid);
	}


	@Then("^employee detail page is displayed$")
	public void when_employee_detail_page_is_displayed() throws Throwable {
		if(empPages.isEmployeeDetailsPage()){
			Reporter.logEvent(Status.PASS, "employee detail page displays", 
					"employee detail page displays", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "employee detail page displays", 
					"employee detail page don't displays", true);
		}
	}
	@When("^user clicks on Vesting link$")
    public void user_clicks_on_vesting_link() throws Throwable {
        empPages.clickOnVestingLink();
    }

	@Then("^a new window opens with Vesting historical data$")
	public void a_new_window_opens_with_vesting_historical_data()
			throws Throwable {
		if(empPages.verifyVestingWindow()){
			Reporter.logEvent(Status.PASS, "new window opens with Vesting historical data", 
					"new window opens with Vesting historical data", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "new window opens with Vesting historical data", 
					"new window don't opens with Vesting historical data", true);
		}

	}

	@Then("^information displayed on Vesting section should be \"([^\"]*)\"$")
	public void information_displayed_on_vesting_section_should_be_message(
			String inputMessage) throws Throwable {
		if(empPages.verifyVestingSectionMessage(inputMessage)){
			Reporter.logEvent(Status.PASS, "information displays on Vesting section: "+inputMessage, 
					"information displays on Vesting section: "+inputMessage, true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "information displays on Vesting section: "+inputMessage, 
					inputMessage+" :don't displays on Vesting section", true);
		}

	}

	@Then("^Vesting information should be available under Vesting section$")
	public void vesting_information_should_be_available_under_vesting_section()
			throws Throwable {
		if(empPages.verifyVestingSectionDisplays()){
			Reporter.logEvent(Status.PASS, "Vesting information displays under Vesting section", 
					"Vesting information displays under Vesting section", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Vesting information displays under Vesting section", 
					"Vesting information don't displays under Vesting section", true);
		}

	}
	 @Then("^Vesting section should be suppressed$")
	    public void vesting_section_should_be_suppressed() throws Throwable {
		 if(!empPages.verifyVestingSectionDisplays()){
				Reporter.logEvent(Status.PASS, "Vesting section should be suppressed", 
						"Vesting section is suppressed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Vesting section should be suppressed", 
						"Vesting section don't suppressed", true);
			}

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
	@And("^user click on Edit button of Employment information$")
	public void clickEditEmpInfoLink(){
		empPages.clickOnEmpInfoEditLink();
		Reporter.logEvent(Status.INFO, "User is on employment information page", 
				"User is on employment information page", true);
	}
	@And("^user click on Details link under Employment history$")
	public void click_Details_Link_Under_EmploymentHistory(){
		empPages.clickOnDetailsLinkUnderEmploymentHistory();
		Reporter.logEvent(Status.INFO, "User is on employment history section", 
				"User is on employment history section", true);
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

	@When("^user switched to \"([^\"]*)\" and navigate to deferral page$")
	public void user_switched_to_planNo_and_navigate_to_deferral_page(String planno) throws Throwable {
		if (planno == null ||planno.equals(""))
			planno = planNumber;
		new HomePage().switchPlan(planno);
		empPages.get();
		empPages.openDeferralDetailsPage();

	}
	@When("^user switched to \"([^\"]*)\" and navigate to Deferral Contribution screen for Future Dated Ongoing Deferrals$")
    public void user_switched_to_planno_and_navigate_to_deferral_contribution_screen_for_future_dated_ongoing_deferrals(String planno) throws Throwable {
		if (planno == null ||planno.equals(""))
			planno = planNumber;
		new HomePage().switchPlan(planno);
		empPages.get();
		empPages.openDeferralDetailsPage();
    }
	
	
	@When("^user enters a \"([^\"]*)\" contribution for a \"([^\"]*)\"$")
    public void user_enters_a_futuredate_contribution_for_a_deferraltype(String futuredate, String deferraltype) throws Throwable {
		empPages.clickOngoingEdit();
		empPages.enterFutureDateContributionAndSave(deferraltype, futuredate);
    }
	@When("^user enters a contribution for a \"([^\"]*)\"$")
    public void user_enters_a_contribution_for_a_deferraltype(String deferraltype) throws Throwable {
		empPages.clickOngoingEdit();
		empPages.enterFutureDateContributionAndSave(deferraltype);
    }
	@When("^user enters a contribution for a \"([^\"]*)\" of the given plan$")
	    public void user_enters_a_contribution_for_a_deferraltype_of_the_given_plan(String deferraltype) throws Throwable {
		empPages.clickOngoingEdit();
		empPages.enterContributionAndSave(deferraltype);
	    }
	 @When("^user clicks continue on the deferral contribution screen$")
	    public void user_clicks_continue_on_the_deferral_contribution_screen() throws Throwable {
	        
	    }

	@Then("^the effective date listed on the deferral review page for a \"([^\"]*)\" is the \"([^\"]*)\" the user entered$")
	public void the_effective_date_listed_on_the_deferral_review_page_for_a_deferral_is_the_futuredate_the_user_entered(
			String deferraltype, String futuredate) throws Throwable {
		if (empPages.isEffectiveDateSameAsFutureDate(deferraltype, futuredate)) {
			Reporter.logEvent(Status.PASS,
					"the effective date "+futuredate+" should be listed on the deferral review page",
					"the effective date "+futuredate+" is listed on the deferral review page",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the effective date "+futuredate+" should be listed on the deferral review page",
					"the effective date "+futuredate+" isn't listed on the deferral review page",
					true);
		}
	}
	
	@Then("^the effective date listed on the deferral review page is the current date for \"([^\"]*)\"$")
	public void the_effective_date_listed_on_the_deferral_review_page_is_the_current_date_for_deferraltype(
			String deferraltype) throws Throwable {
		if (empPages.isEffectiveDateSameAsFutureDate(deferraltype)) {
			Reporter.logEvent(
					Status.PASS,
					"the effective date should be listed on the deferral review page is the current date",
					"the effective date is listed on the deferral review page is the current date",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the effective date should be listed on the deferral review page is the current date",
					"the effective date listed on the deferral review page isn't the current date",
					true);
		}
	}
	
	@Given("^plan is set up with \"([^\"]*)\"$")
    public void plan_is_set_up_with_something(String sdsvsubcode, String query) throws Throwable {
		planNumber=empPages.getPlanNumber(query, sdsvsubcode);
        
    }
	@Given("^plan is set up with \"([^\"]*)\" in GA_Service table$")
    public void plan_is_set_up_with_sdsvsubcode_in_gaService_table(String sdsvsubcode, String query) throws Throwable {
		planNumber=empPages.getPlanNumber(query, sdsvsubcode);
        
    }

	@When("^participant has an ongoing deferral for the \"([^\"]*)\"$")
	public void participant_has_an_ongoing_deferral(String deferraltype) throws Throwable {
		empPages.checkAndAddOngoingDeferral(deferraltype);
			

	}
	@When("^user adds a scheduled automatic increase$")
	    public void user_adds_a_scheduled_automatic_increase() throws Throwable {
	        empPages.clickScheduleAutomaticIncreaseEdit();
	    }
	
	@Then("^the dropdown list should populate with list of dates including weekend dates for \"([^\"]*)\"$")
    public void the_dropdown_list_should_populate_with_list_of_dates_including_weekend_dates_for_something(String deferraltype) throws Throwable {
        empPages.isAllDateAvialableInSelectTargetPayRoll();
    }
	
	@Then("^user should be able to add a schedule for the \"([^\"]*)\"$")
    public void user_should_be_able_to_add_a_schedule_for_the_deferraltype(String deferraltype) throws Throwable {
        
    }

	
	@Then("^the effective date listed on the deferral review page for a \"([^\"]*)\" is the next month date$")
    public void the_effective_date_listed_on_the_deferral_review_page_for_a_something_is_the_first_date_of_the_following_month(String deferraltype) throws Throwable {
		if (empPages.isTheDateWithinNextMonth(deferraltype)) {
			System.out.println("PASS");
		}
    }
	@Then("^user should see a \"([^\"]*)\" datepicker for \"([^\"]*)\"$")
    public void user_should_see_a_something_datepicker_for_something(String deferraltype, String calendarType) throws Throwable {
		if (empPages.isCalendarType(deferraltype, calendarType)) {
			Reporter.logEvent(Status.PASS, calendarType
					+ " datepicker displays for the user", calendarType
					+ " datepicker displays for the user", true);
		} else {
			Reporter.logEvent(Status.FAIL, calendarType
					+ " datepicker displays for the user", calendarType
					+ " datepicker isn't displays for the user", true);
		}
    }
	
	@Given("^User search and selects plan \"([^\"]*)\"$")
	public void userSelectPlan(String planNo) throws Exception{
		new HomePage().switchPlan(planNo);
	}

	@When("^clicks on the search result to open employee detail page$")
    public void clicks_on_the_search_result_to_open_employee_detail_page() throws Throwable {
		empPages.clickOnFirstNameLinkInSearchResult();
    }
	@When("^clicks on View button on Loan Section$")
    public void clicks_on_view_button_on_loan_section() throws Throwable {
        
    }

	/*@Then("^Loan details page is displayed$")
	public void loan_detail_page_is_displayed() throws Throwable {
		if(empPages.isEmployeeDetailsPage()){
			Reporter.logEvent(Status.PASS, "employee detail page displays", 
					"employee detail page displays", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "employee detail page displays", 
					"employee detail page don't displays", true);
		}
	}*/
	@Then("^Plan Analytics are suppressed$")
    public void plan_analytics_are_suppressed() throws Throwable {
		if(!provisionPage.isPlanAnalyticsSectionDiplays()){
			Reporter.logEvent(Status.PASS, "Plan Analytics are suppressed", 
					"Plan Analytics are suppressed", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Plan Analytics are suppressed", 
					"Plan Analytics aren't suppressed", true);
		}
    }

	@Then("^Employee account balance and view account history button is suppressed for \"([^\"]*)\" plan$")
	public void employee_account_balance_is_suppressed_for_hsa_plan(
			String hsaPlan) throws Throwable {
		if (empPages.verifyAccountBalanceSuppressed(hsaPlan) && empPages.verifyViewButtonSuppressed()) {
			Reporter.logEvent(
					Status.PASS,
					"Employee account balance and view account history button are suppressed",
					"Employee account balance and view account history button are suppressed",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Employee account balance and view account history button are suppressed",
					"Employee account balance and view account history button aren't suppressed",
					true);
		}
	}
	

	@Then("^the Employee Information section should not include \"([^\"]*)\"$")
	public void the_employee_information_section_should_not_include_label(
			@Delimiter(",") List<String> labels) throws Throwable {
		if (empPages.verifyEmploymentInformationLabel(labels, false)) {
			Reporter.logEvent(
					Status.PASS,
					"the Employee Information section do not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Employee Information section not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Employee Information section do not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Employee Information section include all or one of the label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		}
	}

	@Then("^the Employee Information section should include \"([^\"]*)\"$")
	public void the_employee_information_section_should_include_label(
			@Delimiter(",") List<String> labels) throws Throwable {
		if (empPages.verifyEmploymentInformationLabel(labels, true)) {
			Reporter.logEvent(
					Status.PASS,
					"the Employee Information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Employee Information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Employee Information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Employee Information section don't include all or one of the label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		}
	}

	@Then("^the Details and Update employment information section should not include \"([^\"]*)\"$")
	public void the_details_and_update_employment_information_section_should_not_include_label(
			@Delimiter(",") List<String> labels) throws Throwable {
		if (empPages.verifyEmploymentInformationLabel(labels, false)
				&& empPages.verifyUpdateEmploymentInformationLabel(labels,
						false)) {
			Reporter.logEvent(
					Status.PASS,
					"the Details and Update employment information section do not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Details and Update employment information section not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Details and Update employment information section do not include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Details and Update employment information section include all or one of the label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		}
	}
	
	
	@Then("^the Details and Update employment information section should include \"([^\"]*)\"$")
	public void the_details_and_update_employment_information_section_should_include_label(
			@Delimiter(",") List<String> labels) throws Throwable {
		if (empPages.verifyEmploymentInformationLabel(labels, true)
				&& empPages
						.verifyUpdateEmploymentInformationLabel(labels, true)) {
			Reporter.logEvent(
					Status.PASS,
					"the Details and Update employment information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Details and Update employment information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Details and Update employment information section include label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					"the Details and Update employment information section don't include all or one of the label: "
							+ "Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code",
					true);
		}
	}

	
	
		@When("^\"([^\"]*)\" has retrived \"([^\"]*)\" from \"([^\"]*)\" and selected from the top right drop-down list$")
	    public void something_has_retrived_something_from_something_and_selected_from_the_top_right_dropdown_list(String user, String plan, String db) throws Throwable {
			empPages.retrivePlanFromDBAndSerach(user,plan,db);
	    }

		/* @Then("^\"([^\"]*)\" related \"([^\"]*)\" should display $")	
	    public void something_related_something_should_display(String user, String menuitems) throws Throwable {
	       // throw new PendingException();
	    }*/
	
		 @Then("^\"([^\"]*)\" related \"([^\"]*)\" should display$")
		    public void something_related_something_should_display(String user, String menuitems) throws Throwable {
			 empPages.verifyMenuItems(menuitems);
			 System.out.println(menuitems);
		    }

	 	
	 	
	 	
	 	
		@Given("^a mandatory minimum deferral amount is in place$")
	    public void a_mandatory_minimum_deferral_amount_is_in_place() throws Throwable {
	        throw new PendingException();
	    }
	
	    @Given("^a \"([^\"]*)\" amount is in place for a \"([^\"]*)\"$")
	    public void a_something_amount_is_in_place_for_a_something(String strArg1, String strArg2) throws Throwable {
	        throw new PendingException();
	    }
	
	    @When("^user enters a contribution amount lower than the minimum$")
	    public void user_enters_a_contribution_amount_lower_than_the_minimum() throws Throwable {
	        throw new PendingException();
	    }
	
	    @When("^user enters a contribution dollar amount lower than the minimum$")
	    public void user_enters_a_contribution_dollar_amount_lower_than_the_minimum() throws Throwable {
	        throw new PendingException();
	    }
	
	    @When("^user enters a contribution percentage lower than the minimum $")
	    public void user_enters_a_contribution_percentage_lower_than_the_minimum() throws Throwable {
	        throw new PendingException();
	    }
	
	    @When("^user clicks 'Edit' next to 'Stop all deferrals'$")
	    public void user_clicks_edit_next_to_stop_all_deferrals() throws Throwable {
	        throw new PendingException();
	    }
	
	    @Then("^an error message should appear$")
	    public void an_error_message_should_appear() throws Throwable {
	        throw new PendingException();
	    }
	
	    @Then("^an error message should appear indicating the user's dollar amount is less than the plan minimum$")
	    public void an_error_message_should_appear_indicating_the_users_dollar_amount_is_less_than_the_plan_minimum() throws Throwable {
	        throw new PendingException();
	    }
	
	    @Then("^an error message should appear indicating that the user's percentage value is less than the plan minimum$")
	    public void an_error_message_should_appear_indicating_that_the_users_percentage_value_is_less_than_the_plan_minimum() throws Throwable {
	        throw new PendingException();
	    }
	
	    @Then("^the deferral contribution rate is set to $")
	    public void the_deferral_contribution_rate_is_set_to() throws Throwable {
	        throw new PendingException();
	    }
	
	    @And("^the contribution should not be processed$")
	    public void the_contribution_should_not_be_processed() throws Throwable {
	        throw new PendingException();
	    }
	
	    @And("^clicks 'confirm' on the following screen$")
	    public void clicks_confirm_on_the_following_screen() throws Throwable {
	        throw new PendingException();
	    }

	   
}
