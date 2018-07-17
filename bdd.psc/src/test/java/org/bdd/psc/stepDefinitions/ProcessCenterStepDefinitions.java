package org.bdd.psc.stepDefinitions;

import gherkin.formatter.model.Scenario;

import java.util.List;



import pscBDD.login.LoginPage;
import pscBDD.processCenter.uploadPayrollFilePage;
import bdd_core.framework.Globals;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProcessCenterStepDefinitions {
	
	public static String featureName=null;
	public static Scenario scenario=null;
	uploadPayrollFilePage uploadPayRollFile; 
	

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
			featureName=scenario.getId().split(";")[0];
			Globals.scenarioName=scenario.getName();
			System.out.println(scenario.getId());		
			Reporter.initializeModule(featureName);
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[scenario.getId().split(";").length-1])-1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
			uploadPayRollFile=new uploadPayrollFilePage();
		}
		catch(Exception e){
			try{
				Globals.currentIteration = Integer.valueOf(scenario.getId().split(";")[4])-1;
			}
			catch(Exception e1){
				Globals.currentIteration=1;
			}
		}
	}

	@After
	public void after() throws Exception{
		Reporter.finalizeTCReport();
	}
	
	 @Given("^navigated to Process center/Transmit your payroll file/Upload payroll file$")
	    public void navigated_to_process_centertransmit_your_payroll_fileupload_payroll_file() throws Throwable {
	        uploadPayRollFile.get();
	    }
	 @Given("^has a file with a file name and format as one of the authorized file names listed$")
	    public void has_a_file_with_a_file_name_and_format_as_one_of_the_authorized_file_names_listed() throws Throwable {
		 if(!uploadPayRollFile.checkUserHasFileToUpload())
		 Reporter.logEvent(Status.INFO, "user don't have file to upload",
					"user don't have file to upload", true);
	    }
	 @Given("^has a file with a file name and format that does NOT match one of the authorized file names listed$")
	    public void has_a_file_with_a_file_name_and_format_that_does_not_match_one_of_the_authorized_file_names_listed() throws Throwable {
		 if(!uploadPayRollFile.checkUserHasInvalidFileToUpload())
			 Reporter.logEvent(Status.INFO, "user don't have invalid file to upload",
						"user don't have invalid file to upload", true);
	    }
	 @When("^user uploads an Employee data file$")
	    public void user_uploads_an_employee_data_file() throws Throwable {
	        uploadPayRollFile.uploadFile(uploadPayRollFile.filePath+uploadPayRollFile.fileNameToUpload);
	    }
	 @When("^user uploads an invalid Employee data file$")
	    public void user_uploads_an_invalid_employee_data_file() throws Throwable {
	        uploadPayRollFile.uploadFile(uploadPayRollFile.filePath+uploadPayRollFile.invalidFileNameToUpload);
	    }

	@Then("^confirmation message should appear: \"([^\"]*)\"$")
	public void confirmation_message_should_appear_successfully_uploaded(
			String message) throws Throwable {
		if (uploadPayRollFile.verifyFileUploadMessage(message)) {
			Reporter.logEvent(Status.PASS,
					"confirmation message should appear: " + message,
					"confirmation message displays: " + message, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"confirmation message should appear: " + message,
					"confirmation message don't displays: " + message, true);
		}
	}

	@Then("^the file name should match the name of the file that was uploaded$")
	public void the_file_name_should_match_the_name_of_the_file_that_was_uploaded()
			throws Throwable {
		if (uploadPayRollFile.verifyUploadedFileName()) {
			Reporter.logEvent(Status.PASS,
					"file name should match the name of the file uploaded",
					"file name matched with the name of the file uploaded",
					true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"file name should match the name of the file uploaded",
					"file name don't match with the name of the file uploaded",
					true);
		}
	}
	@Then("^the upload should not proceed$")
    public void the_upload_should_not_proceed() throws Throwable {
		if (uploadPayRollFile.verifyFileUploadErrorMessage()) {
			Reporter.logEvent(Status.PASS,
					"the file upload should not proceed",
					"file upload  not allowed",
					true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"the file upload should not proceed",
					"file upload allowed",
					true);
		}
    }

	@Then("^a message should be displayed: \"([^\"]*)\"$")
	public void a_message_should_be_displayed_errorMessage(String errorMessage)
			throws Throwable {
		if (uploadPayRollFile.verifyFileUploadErrorMessage(errorMessage)) {
			Reporter.logEvent(Status.PASS,
					"error message displays: "+errorMessage,
					"error message displays: "+errorMessage,
					true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"error message displays: "+errorMessage,
					"error message don't displays: "+errorMessage,
					true);
		}
		

	}
	 

	

}
