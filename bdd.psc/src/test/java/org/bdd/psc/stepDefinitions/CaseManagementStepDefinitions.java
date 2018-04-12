/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import com.aventstack.extentreports.Status;

import org.bdd.psc.pageobjects.CaseManagement;
import reporter.Reporter;
import core.framework.Globals;
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
	

}
