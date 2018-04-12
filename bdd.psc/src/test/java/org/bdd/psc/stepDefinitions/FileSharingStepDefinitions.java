/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import com.aventstack.extentreports.Status;

import gherkin.formatter.model.Scenario;

import org.bdd.psc.pageobjects.FileSharingPage;
import org.bdd.psc.pageobjects.ForgotPasswordPage;
import org.bdd.psc.pageobjects.HomePage;
import org.bdd.psc.pageobjects.JumpPage;
import org.bdd.psc.pageobjects.LoginPage;
import org.bdd.psc.pageobjects.UserVerificationPage;

import reporter.Reporter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
	
	@When("^User \"([^\"]*)\" the header row checkbox$")
    public void user_something_the_header_row_checkbox(String strArg1) throws Throwable {
		new FileSharingPage().selectOrDeselectTheHeaderRowCheckBox(strArg1);
        
    }
	@Given("^The header row checkbox is \"([^\"]*)\"$")
	public void the_header_row_checkbox_is_something(String strArg1) throws Throwable {
		new FileSharingPage().selectOrDeselectTheHeaderRowCheckBox(strArg1);
	}
	@Then("^The header-row checkbox should be \"([^\"]*)\"$")
	public void the_headerrow_checkbox_should_be_something(String strArg1) throws Throwable {
		if(new FileSharingPage().isSelectedHeaderRowCheckBox(strArg1)){
			Reporter.logEvent(Status.PASS, "The header-row checkbox should be "+strArg1, 
					"The header-row checkbox is "+strArg1, true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "The header-row checkbox should be "+strArg1, 
					"The header-row checkbox is not "+strArg1, true);
		} 
	}

	@Then("^All checkboxes on screen should be \"([^\"]*)\"$")
	public void all_checkboxes_on_screen_should_be_something(String strArg1) throws Throwable {
		if(new FileSharingPage().isAllCheckBoxSelectedOrDeselected(strArg1)){
			Reporter.logEvent(Status.PASS, "All checkboxes on screen should be "+strArg1, 
					"All checkboxes on screen "+strArg1, true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "All checkboxes on screen should be "+strArg1, 
					"All checkboxes on screen does not "+strArg1, true);
		}
	}
	@Then("^The checkboxes on other page should be \"([^\"]*)\"$")
	public void the_checkboxes_on_other_pages_should_be_something(String strArg1)throws Throwable {
		new FileSharingPage().navigateToNextPage();
		if (new FileSharingPage().isAllCheckBoxSelectedOrDeselected(strArg1)) {
			Reporter.logEvent(Status.PASS,
					"All checkboxes on other page should be " + strArg1,
					"All checkboxes on other page are " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"All checkboxes on other page should be " + strArg1,
					"All checkboxes on other page are not " + strArg1, true);
		}
	}
	@When("^User navigates to another page$")
	public void user_navigates_to_another_page() throws Throwable {
		 new FileSharingPage().navigateToNextPage();
	}
	@When("^User selects a checkbox that is NOT the header row checkbox$")
    public void user_selects_a_checkbox_that_is_not_the_header_row_checkbox() throws Throwable {
		new FileSharingPage().selectOneOrMoreNonHeaderRowCheckBox(1);
    }
	@Given("^User has selected one or more checkboxes on a page$")
    public void user_has_selected_one_or_more_checkboxes_on_a_page() throws Throwable {
		new FileSharingPage().selectOneOrMoreNonHeaderRowCheckBox(3);
    }
	@Then("^The Button Row becomes \"([^\"]*)\"$")
    public void the_button_row_becomes_something(String strArg1) throws Throwable {
		if (new FileSharingPage().isButtonRowVisible(strArg1)) {
			Reporter.logEvent(Status.PASS,
					"The Button Row should be " + strArg1,
					"The Button Row becomes " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"The Button Row should be " + strArg1,
					"The Button Row is not  " + strArg1, true);
		}
    }
	@Then("^Selected checkboxes should be \"([^\"]*)\"$")
    public void selected_checkboxes_should_be_something(String strArg1) throws Throwable {
        if(new FileSharingPage().isAllCheckBoxSelectedOrDeselected(strArg1)){
        	Reporter.logEvent(Status.PASS,"Selected checkboxes should be " + strArg1,"Selected checkboxes are " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,"Selected checkboxes should be " + strArg1,"Selected checkboxes are not  " + strArg1, true);
        }
    }
	
	@When("^User unselects all selected checkboxes$")
    public void user_unselects_all_selected_checkboxes() throws Throwable {
		new FileSharingPage().deselectOneOrMoreNonHeaderRowCheckBox();
    }
	@When("^All row-level checkboxes are selected$")
    public void all_rowlevel_checkboxes_are_selected() throws Throwable {
		new FileSharingPage().selectOneOrMoreNonHeaderRowCheckBox();
    }
	@When("^At least one row-level checkbox is not selected$")
    public void at_least_one_rowlevel_checkbox_is_not_selected() throws Throwable {
		new FileSharingPage().selectOneOrMoreNonHeaderRowCheckBox();
    }


}
