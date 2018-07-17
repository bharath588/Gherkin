/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import pscBDD.fileSharing.FileSharingPage;
import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.reports.ReportsPage;
import pscBDD.userVerification.UserVerificationPage;

import bdd_lib.Web;

import com.aventstack.extentreports.Status;

import bdd_reporter.Reporter;
import bdd_core.framework.Globals;




import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author rvpndy
 *
 */
public class ReportStepDefinitions {
	
	public static String featureName=null;
	public static Scenario scenario=null;
	public static Integer currentIteration = null;

	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	ForgotPasswordPage forgotPwdPage;
	FileSharingPage fileSharingPage;
	ReportsPage reportsPage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
		featureName=scenario.getId().split(";")[0];
		Globals.scenarioName=scenario.getName();
		Reporter.initializeModule(featureName);
		ReportStepDefinitions.scenario =scenario;
		System.out.println(scenario.getId());
		currentIteration=Integer.valueOf(scenario.getId().split(";")[3])-1;
		}
		catch(Exception e){
			currentIteration=1;
		}
	}

	@After
	public void after() throws Exception{
		Reporter.finalizeTCReport();
	}
	
	@Given("^User has a shareable report in My Reports$")
	public void user_has_a_shareable_report_in_My_Reports() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.initializeReportForTC(currentIteration, Globals.scenarioName, "SWEB-17122");
	    reportsPage = new ReportsPage();
	    reportsPage.get();
	    if(reportsPage.isShareOrReshareAvailable()){
	    	Reporter.logEvent(Status.PASS, "User has a shareable report in My Reports", 
	    			"User has a shareable report in My Reports", true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "User has a shareable report in My Reports", 
	    			"User does not have a shareable report in My Reports", true);
	    }
	    
	}
	
	@When("^User clicks the 'Share' or 'Reshare' link$")
	public void user_clicks_the_Share_or_Reshare_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.clickOnShareOrReShare();
	}
	
	@Then("^Report Sharing modal box opens$")
	public void report_Sharing_modal_box_opens() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		if(reportsPage.isFileSharingModel()){
			Reporter.logEvent(Status.PASS, "Report Sharing modal box opens", 
					"Report Sharing modal box opens", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Report Sharing modal box opens", 
					"Report Sharing modal box does not open", true);
		}
	}
	
	@Given("^User has access to File Sharing$")
	public void user_has_access_to_File_Sharing() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(currentIteration, Globals.scenarioName, "SWEB-17122");
	    reportsPage = new ReportsPage();
	    if(reportsPage.isFileSharingUser()){
	    	Reporter.logEvent(Status.PASS, "User has access to File Sharing module", 
	    			"User has access to File Sharing module", true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "User has access to File Sharing module", 
	    			"User does not have access to File Sharing module", true);
	    }
	}

	@When("^User selects a folder from the Folder dropdown$")
	public void user_selects_a_folder_from_the_Folder_dropdown() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.selectAFolder();
	}

	@Then("^The Subfolder dropdown should populate with the folder's subfolders' names$")
	public void the_Subfolder_dropdown_should_populate_with_the_folder_s_subfolders_names() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
	    if(reportsPage.hasSubFolders()){
	    	Reporter.logEvent(Status.PASS, "Sub folder drop down is populated with values", 
	    			"Sub folder drop down is populated with values", true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "Sub folder drop down is populated with values", 
	    			"Sub folder drop down is not populated with values", true);
	    }
	    
	}

	@Then("^the dropdown should display the default option 'Select subfolder'$")
	public void the_dropdown_should_display_the_default_option_Select_subfolder() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		if(reportsPage.getSubFolderDefaultOption().equalsIgnoreCase("Select subfolder")){
			Reporter.logEvent(Status.PASS, 
					"Sub folder drop down displays default option Select subfolder", 
					"Sub folder drop down displays default option Select subfolder", true);
		}
		else{
			Reporter.logEvent(Status.PASS, 
					"Sub folder drop down displays default option Select subfolder", 
					"Sub folder drop down does not display default option Select subfolder", true);
		}
	}

	@Then("^No subfolders should populate in the Subfolder dropdown$")
	public void no_subfolders_should_populate_in_the_Subfolder_dropdown() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.setFolderName("Conversions");
		if(!reportsPage.hasSubFolders()){
			Reporter.logEvent(Status.PASS, "No subfolders", "No subfolders", true);
		}
	}

	@Then("^the dropdown box should appear disabled$")
	public void the_dropdown_box_should_appear_disabled() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   Reporter.logEvent(Status.INFO, "the dropdown box should appear disabled", 
			   "Not yet implemented", true);
	}
	
	@When("^has made a selection for the subfolder field$")
	public void has_made_a_selection_for_the_subfolder_field() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.selectFolderWithSubFolder();
	}

	@When("^has made a selection for the Category field$")
	public void has_made_a_selection_for_the_Category_field() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    reportsPage = new ReportsPage();
	    reportsPage.selectAcategory();
	}

	@When("^has made a selection for the Expiration Date field$")
	public void has_made_a_selection_for_the_Expiration_Date_field() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.selectExpirationDate();
	}

	@When("^has selected the Confirmation Box$")
	public void has_selected_the_Confirmation_Box() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.clickOnConfirmationCheckBox();
	}

	@When("^User clicks \"([^\"]*)\"$")
	public void user_clicks(String save) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reportsPage = new ReportsPage();
		reportsPage.clickOnSave();
		
	}

	@Then("^The selected report should appear in the selected subfolder$")
	public void the_selected_report_should_appear_in_the_selected_subfolder() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Web.getDriver().switchTo().defaultContent();
		homePage = new HomePage();
		homePage.switchPlan(ReportsPage.planNumberValue);
	    fileSharingPage = new FileSharingPage();
	    //Web.clickOnElement(fileSharingPage, "File Sharing Tab");
	    Web.actionsClickOnElement(Web.returnElement(fileSharingPage, "File Sharing Tab"));
	    
	    Web.waitForElement(Web.returnElement(fileSharingPage, "File Sharing Frame"));
	    Web.FrameSwitchONandOFF(true, Web.returnElement(fileSharingPage, "File Sharing Frame"));
	    Web.nextGenDriver.waitForAngular();
	    Thread.sleep(10000);
	    fileSharingPage.openFolder(ReportsPage.folderNameValue);
	    fileSharingPage.openSubFolder(ReportsPage.subfolderNameValue);
	    if(fileSharingPage.isDocumentAvailable(ReportsPage.fileNameValue)){
	    	Reporter.logEvent(Status.PASS, "Shared report appears in correct folder", 
	    			"Shared report appears in correct folder", true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "Shared report appears in correct folder", 
	    			"Shared report does not appear in correct folder", true);
	    }
	}

	@Then("^the content of the file should match the selected report$")
	public void the_content_of_the_file_should_match_the_selected_report() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.logEvent(Status.INFO, "the content of the file should match the selected report",
	    		"Cannot verify this automatically", true);
	}
	
	@When("^user clicks on \"([^\"]*)\" under Reports tab$")
	public void user_clicks_on_under_Reports_tab(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   ReportsPage reportPage = new ReportsPage();
	   reportPage.get();
	   
	}
	@Then("^the Reports catalog link is displayed next to the Search entry text box$")
	public void the_Reports_catalog_link_is_displayed_next_to_the_Search_entry_text_box() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		if(Web.isWebElementDisplayed(new ReportsPage(), "ReportsCatalogLink", true)){
			   Reporter.logEvent(Status.PASS, "Reports catalog link displays next to the Search entry text box", 
		    			"Reports catalog link displays next to the Search entry text box", true);
		   }
		   else{
			   Reporter.logEvent(Status.FAIL, "Reports catalog link displays next to the Search entry text box", 
		    			"Reports catalog link does not displays next to the Search entry text box", true);
		   }    
	}
	@Given("^user is on the \"([^\"]*)\" page$")
    public void user_is_on_the_standard_reports_page(String arg1) throws Throwable {
        new ReportsPage().get();
    }
	@When("^user clicks Reports Catalog Link$")
    public void user_clicks_reports_catalog_link() throws Throwable {
        if(Web.isWebElementDisplayed(new ReportsPage(), "ReportsCatalogLink", true))
        	Web.clickOnElement(new ReportsPage(), "ReportsCatalogLink");
    }
	@Then("^link downloads the Reports Catalog excel file$")
	public void link_downloads_the_Reports_Catalog_excel_file() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		new ReportsPage().downloadReportCatlogFile();
	   
	}

}
