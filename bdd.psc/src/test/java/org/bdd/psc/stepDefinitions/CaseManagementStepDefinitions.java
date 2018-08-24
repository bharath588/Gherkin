/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import pscBDD.casemanagement.CaseManagement;
import bdd_core.framework.Globals;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;




import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.Scenario;

/**
 * @author rvpndy
 *
 */
public class CaseManagementStepDefinitions {
	public static String featureName=null;
	public static Scenario scenario=null;
	CaseManagement caseManagement;
	
	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
			caseManagement = new CaseManagement();
			featureName=scenario.getId().split(";")[0];
			Globals.scenarioName=scenario.getName();
			System.out.println(scenario.getId());
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[3])-1;
			Reporter.initializeModule(featureName);
		}
		catch(Exception e){
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after() throws Exception{
		Reporter.finalizeTCReport();
	}
	
	@When("^user clicks on 'Case Management' option under the Plan Menu$")
	public void user_clicks_on_Case_Management_option_under_the_Plan_Menu() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		caseManagement.get();
	}
	
	@When("^user clicks on 'View all case history' button$")
	public void user_clicks_on_View_all_case_history_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		caseManagement.clickOnViewAllCaseHistroy();
		Reporter.logEvent(Status.INFO, "Click on 'View all case history' button",
				"Click on 'View all case history' button", true);
	}
	
	@When("^user clicks on 'Excel' link$")
	public void user_clicks_on_Excel_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		caseManagement.clickOnExcelLink();
		Reporter.logEvent(Status.INFO,"Click on excel link", "Click on excel link", true);
		Reporter.logEvent(Status.INFO,"Excel is downloaded", "Excel is downloaded", true);
	}
	
	@Then("^the Excel file columns should include \"([^\"]*)\"$")
	public void the_Excel_file_columns_should_include(String csvColNames) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println(csvColNames);
		String[] colNames = csvColNames.split(",");
		for(String colName : colNames)
			System.out.println(colName);
		Thread.sleep(5000);
		if(caseManagement.isColumnExist(colNames)){
			Reporter.logEvent(Status.PASS, "Newly added columns exist in the the downloaded excel", 
					"Newly added columns exist in the the downloaded excel", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Newly added columns exist in the the downloaded excel", 
					"Newly added columns do not exist in the the downloaded excel", true);
		}
		
	}

	@Then("^the \"([^\"]*)\" and \"([^\"]*)\" fields should be displayed correctly for \"([^\"]*)\"$")
	public void verify_partid_empid_for_the_caseNumber(String partid,
			String empid, String casenumber) throws Throwable {
		if (caseManagement.verify_Part_ID_Emp_ID_forCaseNumber(partid.trim(),
				empid.trim(), casenumber.trim())) {
			Reporter.logEvent(Status.PASS,
					"the Part ID and Emp_ID fields displays correctly for Case_Number",
					"the Part ID and Emp_ID fields displays correctly for Case_Number",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Part ID and Emp_ID fields displays correctly for Case_Number",
					"the Part ID and Emp_ID fields aren't displays correctly for Case_Number",
					true);
		}

	}
	@When("^user selects a legend link in 'Participant by category' with related to \"([^\"]*)\"$")
    public void user_selects_a_legend_link_in_participant_by_category_with_related_to_caseNo(String casenumber) throws Throwable {
		 caseManagement.clickOnParticipantByCategoryLink(casenumber);
		 Reporter.logEvent(Status.INFO,
					"click on legend link in Participant by category",
					"click on legend link in Participant by category", true);
	    }
	
	 @Then("^the \"([^\"]*)\" and \"([^\"]*)\" fields should be displayed correctly for \"([^\"]*)\" in the pop-up$")
	    public void verify_partid_empid_for_the_caseNumber_in_the_popup(String partid, String empid, String casenumber) throws Throwable {
		 if (caseManagement.verify_Part_ID_Emp_ID_forCaseNumberInThePopUp(partid.trim(),
					empid.trim(), casenumber.trim())) {
				Reporter.logEvent(Status.PASS,
						"the Part ID and Emp_ID fields displays correctly for Case_Number in the popup",
						"the Part ID and Emp_ID fields displays correctly for Case_Number in the popup",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"the Part ID and Emp_ID fields displays correctly for Case_Number in the popup",
						"the Part ID and Emp_ID fields aren't displays correctly for Case_Number in the popup",
						true);
			}
	    }
	 @When("^user selects the 'New Case' option$")
	    public void user_selects_the_new_case_option() throws Throwable {
		 caseManagement.clickCreateNewCase();
		 Reporter.logEvent(Status.INFO,
					"click on create new case",
					"click on create new case", true);
	    }
String caseType="";
	@When("^user selects \"([^\"]*)\" in case type and searches for a Participant using \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_selects_case_type_and_searches_for_a_participant_using_partid_and_empid(
			String caseType, String empid, String partid) throws Throwable {
		this.caseType=caseType;

	}

	@Then("^the \"([^\"]*)\" and \"([^\"]*)\" fields should be displayed correctly for the \"([^\"]*)\" in search results$")
	public void verify_partid_empid_for_the_caseNumber_in_the_NewCase_popup_in_search_results(
			String partid, String empid, String planno) throws Throwable {
		if (caseManagement.selectTheCaseTypeInNewCaseAndVerify(partid.trim(),
				empid.trim(), planno.trim(),caseType)) {
			Reporter.logEvent(Status.PASS,
					"the Part ID and Emp_ID fields displays correctly for search result",
					"the Part ID and Emp_ID fields displays correctly for search result",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"the Part ID and Emp_ID fields displays correctly for search result",
					"the Part ID and Emp_ID fields aren't displays correctly for search result",
					true);
		}

	}
	@When("^user select \"([^\"]*)\"$")
	    public void user_select_caseNumber(String casenumber) throws Throwable {
		caseManagement.selectTheCaseNumber(casenumber);
	    }
	@Then("^'Case details' page is displayed$")
    public void case_details_page_is_displayed() throws Throwable {
		if (caseManagement.verifyBreadcrumb("Case Details")) {
			Reporter.logEvent(Status.PASS,
					"Case Details page displays",
					"Case Details page displays",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Case Details page  displays",
					"Case Details page isn't displays",
					true);
		}

    }
	
	@Then("^the \"([^\"]*)\" should be displayed$")
    public void the_label_should_be_displayed(String label) throws Throwable {
		if (caseManagement.verifyTheLabelUnderCaseDetails(label)) {
			Reporter.logEvent(Status.PASS,label+" displays in case details",label+" displays in case details",true);
		} else {
			Reporter.logEvent(Status.FAIL,label+" displays in case details",label+" is not displays in case details",true);
		}
    }



	 

}
