/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import gherkin.formatter.model.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

import pscBDD.fileSharing.FileSharingPage;
import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.reports.ReportsPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
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
	ReportsPage reportsPage;
	

	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	ForgotPasswordPage forgotPwdPage;
	FileSharingPage fileSharePage;
	int noOfCheckboxToSelect;
	private static String rootFolderName;
	String subFolderName;
	private String openedPopupName;
	private String destinationFolderName;
	

	@Before
	public void before(cucumber.api.Scenario scenario) {
		featureName=scenario.getId().split(";")[0];
		scenarioName=scenario.getName();
		System.out.println(scenario.getId());

		Reporter.initializeModule(featureName);
		fileSharePage=new FileSharingPage();
		noOfCheckboxToSelect=0;
		openedPopupName=null;
	}

	@After
	public void after() throws Exception{
		
		if (Globals.exception != null ) {
			
			 Reporter.logEvent(Status.FAIL, "Excpetion ", " "+Globals.exception, true);
		} 
		else if(Globals.assertionerror != null)
		{
			 Reporter.logEvent(Status.FAIL, "assertionerror ", " "+Globals.assertionerror, true);
		}
		
		Reporter.finalizeTCReport();
	}
	
	 @Given("^user is on the File sharing page of accuCode when user login with correct username and password$")
	    public void user_is_on_the_file_sharing_page_of_accucode_when_user_login_with_correct_username_and_password(DataTable accAndCreds) throws Throwable {
		 Reporter.initializeReportForTC(Globals.currentIteration,Globals.scenarioName);
		 if (!accAndCreds.equals(Globals.creds)) {
				Globals.creds = accAndCreds;
				LoginPage.setURL(Web.rawValues(Globals.creds).get(0).get("accuCode"));
				new FileSharingPage().get();
		 }
		 else
			 new FileSharingPage().get();
		 
	    }
	
	@Then("^user is on filesharing page$")
	public void user_is_on_filesharing_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.logEvent(Status.PASS, "User is on File Sharing landing page", "User is on File Sharing landing page", true);
	}
	
	@Given("^the user has files within a folder$")
	public void the_user_has_files_within_a_folder() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		if(!(fileSharePage.countOfAllCheckBox()>0)){
			Reporter.logEvent(Status.INFO, "the user has files within a folder", "No file found", true);
		}
	    
	}
	
	@When("^User \"([^\"]*)\" the header row checkbox$")
    public void user_something_the_header_row_checkbox(String strArg1) throws Throwable {
		fileSharePage.selectOrDeselectTheHeaderRowCheckBox(strArg1);
        
    }
	@Given("^The header row checkbox is \"([^\"]*)\"$")
	public void the_header_row_checkbox_is_something(String strArg1) throws Throwable {
		fileSharePage.selectOrDeselectTheHeaderRowCheckBox(strArg1);
	}
	@Then("^The header-row checkbox should be \"([^\"]*)\"$")
	public void the_headerrow_checkbox_should_be_something(String strArg1) throws Throwable {
		if(fileSharePage.isSelectedHeaderRowCheckBox(strArg1)){
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
		if(fileSharePage.isAllCheckBoxSelectedOrDeselected(strArg1)){
			Reporter.logEvent(Status.PASS, "All checkboxes on screen should be "+strArg1, 
					"All checkboxes on screen "+strArg1, true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "All checkboxes on screen should be "+strArg1, 
					"All checkboxes on screen doesn't "+strArg1, true);
		}
	}
	
	 @Given("^All checkboxes on the screen should be \"([^\"]*)\"$")
	    public void all_checkboxes_on_screen_should_be_selected(String strArg1) throws Throwable {
	        fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(fileSharePage.countOfAllCheckBox());
	    }
	
	@Then("^The checkboxes on other page should be \"([^\"]*)\"$")
	public void the_checkboxes_on_other_pages_should_be_something(String strArg1)throws Throwable {
		fileSharePage.navigateToNextPage();
		if (fileSharePage.isAllCheckBoxSelectedOrDeselected(strArg1)) {
			Reporter.logEvent(Status.PASS,
					"All checkboxes on other page should be " + strArg1,
					"All checkboxes on other page are " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"All checkboxes on other page should be " + strArg1,
					"All checkboxes on other page are not " + strArg1, true);
		}
		fileSharePage.navigateToPreviousPage();
	}
	@When("^User navigates to another page$")
	public void user_navigates_to_another_page() throws Throwable {
		 fileSharePage.navigateToNextPage();
	}
	@When("^User selects a checkbox that is NOT the header row checkbox$")
    public void user_selects_a_checkbox_that_is_not_the_header_row_checkbox() throws Throwable {
		fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(1);
    }
	@Given("^User has selected one or more checkboxes on a page$")
    public void user_has_selected_one_or_more_checkboxes_on_a_page() throws Throwable {
		fileSharePage.navigateToPreviousPage();
		fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(2);
    }
	@Then("^The Button Row becomes \"([^\"]*)\"$")
    public void the_button_row_becomes_something(String strArg1) throws Throwable {
		if (fileSharePage.isButtonRowVisible(strArg1)) {
			Reporter.logEvent(Status.PASS,
					"The Button Row should be " + strArg1,
					"The Button Row becomes " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"The Button Row should be " + strArg1,
					"The Button Row is not  " + strArg1, true);
		}
    }
	@Then("^Button row should be \"([^\"]*)\"$")
	public void button_row_should_be_something(String strArg1) throws Throwable {
		if (fileSharePage.isButtonRowVisible(strArg1)) {
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
        if(fileSharePage.isAllCheckBoxSelectedOrDeselected(strArg1)){
        	Reporter.logEvent(Status.PASS,"Selected checkboxes should be " + strArg1,"Selected checkboxes are " + strArg1, true);
		} else {
			Reporter.logEvent(Status.FAIL,"Selected checkboxes should be " + strArg1,"Selected checkboxes are not  " + strArg1, true);
        }
    }
	
	@When("^User unselects all selected checkboxes$")
    public void user_unselects_all_selected_checkboxes() throws Throwable {
		fileSharePage.deselectOneOrMoreNonHeaderRowCheckBox();
    }
	@When("^All row-level checkboxes are selected$")
    public void all_rowlevel_checkboxes_are_selected() throws Throwable {
		fileSharePage.selectOneOrMoreNonHeaderRowCheckBox();
    }
	@When("^At least one row-level checkbox is not selected$")
    public void at_least_one_rowlevel_checkbox_is_not_selected() throws Throwable {
		noOfCheckboxToSelect=fileSharePage.countOfAllCheckBox()-1;
		fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(noOfCheckboxToSelect);
		fileSharePage.deselectOneOrMoreNonHeaderRowCheckBox(1);
    }
	@Given("^The Button Row is \"([^\"]*)\"$")
    public void the_button_row_is_something(String strArg1) throws Throwable {
        if(!(fileSharePage.isButtonRowVisible(strArg1)))
        	fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(1);
    }
	@When("^User clicks a \"([^\"]*)\"$")
    public void user_clicks_a_something(String strArg1) throws Throwable {
		fileSharePage.clickOnButton(strArg1);
    }

    @Then("^The corresponding \"([^\"]*)\" should appear$")
    public void the_corresponding_something_should_appear(String modalbox) throws Throwable {
    	if(fileSharePage.isModalBoxPopupDisplayedOrNot(modalbox)){
        	Reporter.logEvent(Status.PASS,"The "+modalbox+" should displays ","The "+modalbox+" is displayed" , true);
		}
    	else {
			Reporter.logEvent(Status.FAIL,"The "+modalbox+" should displays" ,"The "+modalbox+" doesn't displayed", true);
        }
    	fileSharePage.closeModalBoxPopup(modalbox);
    	
    }
    @Given("^user has opened a \"([^\"]*)\"$")
    public void user_has_opened_a_modal_box(String modalbox) throws Throwable {
		
			if (!(fileSharePage.isButtonRowVisible("visible"))) {
				fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(1);
				noOfCheckboxToSelect = 1;
			} else
				noOfCheckboxToSelect =fileSharePage.countOfAllCheckBox()-fileSharePage.countOfNonSelectedCheckbox();
			System.out.println("---------");
			fileSharePage.clickOnButton(modalbox);

		
    }
    @When("^User cancels actions on the \"([^\"]*)\"$")
    public void user_cancels_actions_on_the_something(String modalbox) throws Throwable {
    	Thread.sleep(3000);
       fileSharePage.cancelModalBoxPopup(modalbox);
    }
    @When("^User closes the \"([^\"]*)\"$")
    public void user_closes_the_something(String modalbox) throws Throwable {
    	Thread.sleep(3000);
    	fileSharePage.closeModalBoxPopup(modalbox);
    }
    @Then("^User returns to the previously selected folder$")
    public void user_returns_to_the_previously_selected_folder() throws Throwable {
        if(fileSharePage.isButtonRowVisible("visible")){
        	Reporter.logEvent(Status.PASS,"User should returns to the previously selected folder","User returns to the previously selected folder" , true);
		}
    	else {
			Reporter.logEvent(Status.FAIL,"User should returns to the previously selected folder" ,"User doesn't returns to the previously selected folder", true);
        }
        
    }
    
	@Then("^Previously selected checkboxes should remain checked$")
	public void previously_selected_checkboxes_should_remain_checked()
			throws Throwable {
		int remainSelected = fileSharePage.countOfAllCheckBox()-fileSharePage.countOfNonSelectedCheckbox();
		if (noOfCheckboxToSelect == remainSelected) {
			Reporter.logEvent(Status.PASS,
					"Previously selected checkboxes should remain checked",
					"Previously selected checkboxes remain checked", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Previously selected checkboxes should remain checked",
					"Previously selected checkboxes doesn't remain checked",
					true);
		}

	}
	
	@Given("^User at root-level folder$")
    public void user_at_rootlevel_folder(DataTable rootFolder) throws Throwable {
		List<String> rfolder = rootFolder.asList(String.class);
		rootFolderName=rfolder.get(1);
		Thread.sleep(5000);
		fileSharePage.openFolder(rootFolderName);
    }
	@Given("^User at sub-level folder$")
	public void user_at_sublevel_folder(DataTable rootFolder) throws Throwable {
		 List<String> sfolder = rootFolder.asList(String.class);
			String fname=sfolder.get(1);			
			fileSharePage.openSubFolder(fname);
    }
	
	@When("^user clicks the \"([^\"]*)\" button on the button Row$")
    public void user_clicks_the_something_button_on_the_button_row(String strArg1) throws Throwable {
       fileSharePage.clickOnButton(strArg1);
       openedPopupName=strArg1;
    }
	
	/*@Then("^The previously selected root folder should be the default$")
    public void the_previously_selected_root_folder_should_be_the_default() throws Throwable {
		if(rootFolderName.equalsIgnoreCase(fileSharePage.getDropDownSelectedText("Parent Folder"))){
			Reporter.logEvent(Status.PASS,
					"The previously selected root folder should be the default",
					"The previously selected root folder is the default one", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"The previously selected root folder should be the default",
					"The previously selected root folder isn't the default one",
					true);
		}
		
    }*/
	
	@Then("^User's root folders should populate in Parent Folder Name dropdown$")
    public void the_subfolders_parent_folder_should_be_the_default() throws Throwable {
		if(rootFolderName.equalsIgnoreCase(fileSharePage.getDropDownSelectedText("Parent Folder"))){
			Reporter.logEvent(Status.PASS,
					"The subfolder's parent folder should be the default",
					"The subfolder's parent folder is the default one", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"The subfolder's parent folder should be the default",
					"The subfolder's parent folder isn't the default one",
					true);
		}
		fileSharePage.closeModalBoxPopup(openedPopupName);
		Thread.sleep(4000);
		
    }
	
	@When("^User clicks on the subfolder dropdown box$")
    public void user_clicks_on_the_subfolder_dropdown_box() throws Throwable {
		
    }
	@Then("^The option \"([^\"]*)\" should be the default in the Subfolder Name dropdown$")
	public void the_option_something_should_be_the_default_in_the_subfolder_name_dropdown(String strArg1) throws Throwable {
		if((fileSharePage.getDropDownSelectedText("Sub Folder")).contains(strArg1)){
			Reporter.logEvent(Status.PASS,
					"The option "+strArg1+" should be the default in the Subfolder Name dropdown",
					"The option "+strArg1+" is the default in the Subfolder Name dropdown", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"The option "+strArg1+" should be the default in the Subfolder Name dropdown",
					"The option "+strArg1+" isn't the default in the Subfolder Name dropdown",
					true);
		}   
	}
	
	@Then("^User's subfolders should populate in the Subfolder Name dropdown$")
    public void users_subfolders_should_populate_in_the_subfolder_name_dropdown() throws Throwable {
		if(fileSharePage.isUserAllSubfoldersDisplayedInTheSubfolderNameDropdown(subFolderName)){
			Reporter.logEvent(Status.PASS,
					"User's all subfolders should populate in the Subfolder Name dropdown",
					"User's all subfolders populated in the Subfolder Name dropdown", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"User's all subfolders should populate in the Subfolder Name dropdown",
					"User's all subfolders does not populated in the Subfolder Name dropdown",
					true);
		}
		fileSharePage.closeModalBoxPopup(openedPopupName);
    }
	
	 @Given("^user has to select files to move from \"([^\"]*)\" with \"([^\"]*)\"$")
	    public void user_has_selected_files_to_move_from_something_with_something(String fromfoldername, String fromfoldervalue) throws Throwable {
		 try{
			 fileSharePage.closeModalBoxPopup("Move");
			 Thread.sleep(2000);
		 }
		 catch(Exception e){
			 Reporter.logEvent(Status.INFO, "No opened popup available", "No opened popup available", true);
		 } 
		 if(fromfoldername.trim().equalsIgnoreCase("parent"))
			 fileSharePage.openFolder(fromfoldervalue);
		 if(fromfoldername.trim().equalsIgnoreCase("sub"))
			 fileSharePage.openSubFolder(fromfoldervalue);
	    }
	 
	 @Given("^clicked on \"([^\"]*)\" button$")
	    public void clicked_on_button(String strArg1) throws Throwable {
		 fileSharePage.clickOnButton(strArg1);
		 openedPopupName=strArg1;
	    }
	 
	 @When("^User selects a \"([^\"]*)\" folder with the \"([^\"]*)\" in dropdown box$")
	    public void user_selects_a_something_folder_with_the_something_in_dropdown_box(String tofoldername, String tofoldervalue) throws Throwable {
	        fileSharePage.selectDropDownValue(tofoldername, tofoldervalue);
	    }
	 @When("^User checks the Confirmation checkbox on the Move Files Modal Box$")
	    public void user_checks_the_confirmation_checkbox_on_the_move_files_modal_box() throws Throwable {
	        fileSharePage.checkTheCheckboxInMoveFileModalBox();
	    }
	 
	 @Then("^User does not select the \"([^\"]*)\" in \"([^\"]*)\" folder$")
	    public void user_does_not_select_the_something_in_something_folder(String fromfoldervalue, String tofoldername) throws Throwable {
		   String DroDownText=fileSharePage.getDropDownSelectedText(tofoldername);
		 if(!(fromfoldervalue.equalsIgnoreCase(DroDownText))){
				Reporter.logEvent(Status.PASS,
						"User does not select the same"+tofoldername+" folder",
						"User does not selected the same"+tofoldername+" folder", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"User does not select the same"+tofoldername+" folder",
						"User select the same"+tofoldername+" folder",
						true);
			}
	    }
	 
	 @Then("^The Move Files Button Label becomes \"([^\"]*)\"$")
	    public void the_move_files_button_label_becomes_enabled(String strArg1) throws Throwable {
		 if(fileSharePage.isMoveFileButtonEnableOrDisabled(strArg1)){
				Reporter.logEvent(Status.PASS,
						"The Move Files Button Label should be enabled",
						"The Move Files Button Label becomes enabled", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"The Move Files Button Label should be enabled",
						"The Move Files Button Label isn't enabled",
						true);
			}
	    }
	 
	 @When("^User deselects the Confirmation checkbox on the Move Files Modal Box$")
	    public void user_deselects_the_confirmation_checkbox_on_the_move_files_modal_box() throws Throwable {
	        fileSharePage.deSelectTheCheckboxInMoveFileModalBox();
	    }
	 

	    @Given("^user has selected the Confirmation checkbox$")
	    public void user_has_selected_the_confirmation_checkbox() throws Throwable {
	    	fileSharePage.checkTheCheckboxInMoveFileModalBox();
	    }
	    
	    @Given("^user has made a change to the \"([^\"]*)\" folder name dropdown box with \"([^\"]*)\"$")
	    public void user_has_made_a_change_to_the_something_folder_name_dropdown_box_with_something(String foldername, String values) throws Throwable {
	    	fileSharePage.selectDropDownValue(foldername, values);
	    }
	    @When("^User selects either the default \"([^\"]*)\" folder in the destination dropbox with \"([^\"]*)\"$")
	    public void user_selects_either_the_default_something_folder_in_the_destination_dropbox_with_something(String foldername, String values) throws Throwable {
	    	fileSharePage.selectDropDownValue(foldername, values);
	    	Thread.sleep(3000);
	    }
	    
	    @Given("^user has selected files$")
	    public void user_has_selected_files(DataTable fileToSelect) throws Throwable {
	    	 try{
				 fileSharePage.closeModalBoxPopup("Move");
				 Thread.sleep(2000);
			 }
			 catch(Exception e){
				 Reporter.logEvent(Status.INFO, "No opened popup available", "No opened popup available", true);
			 } 
	    	//List<String> sfolder = fileToSelect.asList(String.class);
	    	List<Map<String, String>> data = fileToSelect.asMaps(String.class,String.class);
	    	
	    	String fileName=data.get(0).get("parentOrSubFolder");//sfolder.get(1);
			String folderName=data.get(0).get("root folder");//sfolder.get(2);
			if(fileName.trim().equalsIgnoreCase("parent")){
				rootFolderName=folderName;
				 fileSharePage.openFolder(folderName);
			}
				
			 if(fileName.trim().equalsIgnoreCase("sub")){
				 subFolderName=folderName;
				 fileSharePage.openSubFolder(folderName);
			 }
				 
	        
	    }
	    
	@Then("^the count of selected files should match the count that appears in the Move Files modal box$")
	public void the_count_of_selected_files_should_match_the_count_that_appears_in_the_move_files_modal_box()
			throws Throwable {
		if (fileSharePage.isFileCountSameAsManyFileSelected()) {
			Reporter.logEvent(
					Status.PASS,
					"count of selected files should match the count in the Move Files modal box",
					"count of selected files matches the count in the Move Files modal box",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"count of selected files should match the count in the Move Files modal box",
					"count of selected files doesn't match the count in the Move Files modal box",
					true);
		}
	}
	    
	@When("^User clicks Move Files on the modal box$")
	public void user_clicks_move_files_on_the_modal_box() throws Throwable {
		fileSharePage.clickOnMoveFiles();

	}
	@Then("^User has no subfolders in the previously selected root-level folder$")
	public void user_has_no_subfolders_in_the_previously_selected_rootlevel_folder()
			throws Throwable {
		int i=fileSharePage.getTheCountOfDropDown("sub");
		String text=fileSharePage.getDropDownSelectedText("sub");
		System.out.println(i+text);
		if (fileSharePage.getTheCountOfDropDown("sub") == 1
				&& fileSharePage.getDropDownSelectedText("sub")
						.contains("None")) {
			
			Reporter.logEvent(
					Status.PASS,
					"User has no subfolders in the previously selected root-level folder",
					"User has no subfolders in the previously selected root-level folder",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"User has no subfolders in the previously selected root-level folder",
					"User has subfolders in the previously selected root-level folder",
					true);

		}
    }

	@When("^user select the destination folder in dropdown which is not same as selected folder$")
	public void user_select_the_destination_folder_in_dropdown_which_is_not_same_as_selected_folder(DataTable fileToSelect) throws Throwable {
		//List<String> sfolder = fileToSelect.asList(String.class);
		List<Map<String, String>> data = fileToSelect.asMaps(String.class,String.class);
    	String fileName=data.get(0).get("parentOrSubFolder");
		String folderName=data.get(0).get("root folder");
		if(fileName.trim().equalsIgnoreCase("parent")){
			destinationFolderName=folderName;
		    fileSharePage.selectDropDownValue(fileName, folderName);
		}			
		if(fileName.trim().equalsIgnoreCase("sub")){
			destinationFolderName=folderName;
			fileSharePage.selectDropDownValue(fileName, folderName);
		 }
	}
	
	@Then("^User is navigated to the newly selected folder$")
	public void user_is_navigated_to_the_newly_selected_folder()throws Throwable {
		Web.nextGenDriver.waitForAngular();
		Thread.sleep(5000);
		fileSharePage.openFolder(destinationFolderName);
		Web.nextGenDriver.waitForAngular();
	}
	@Then("^Previously selected files should appear in newly selected folder$")
	public void previously_selected_files_should_appear_newly_selected_folder()throws Throwable {
		fileSharePage.openFolder(destinationFolderName);
		Web.nextGenDriver.waitForAngular();
		if (fileSharePage.isFileExistAfterMoving()) {
			Reporter.logEvent(
					Status.PASS,
					"Previously selected files should appear in newly selected folder",
					"Previously selected files present in newly selected folder",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Previously selected files should appear in newly selected folder",
					"Previously selected files not present in newly selected folder",
					true);
		}
		
	}
	@Then("^Previously selected files should no longer appear in former folder$")
	public void previously_selected_files_should_no_longer_appear_in_former_folder()throws Throwable {
		fileSharePage.openFolder(rootFolderName);
		Web.nextGenDriver.waitForAngular();
		if (!fileSharePage.isFileExistInOldFolderAfterMoving()) {
			Reporter.logEvent(
					Status.PASS,
					"Previously selected files should no longer appear in former folder",
					"Previously selected files no longer appear in former folder",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Previously selected files should no longer appear in former folder",
					"Previously selected files present in former folder",
					true);
		}
		
	}
	@Given("^User has selected one or more file checkboxes on a page$")
    public void user_has_selected_one_or_more_file_checkboxes_on_a_page() throws Throwable {
		fileSharePage.navigateToPreviousPage();
		fileSharePage.deselectOneOrMoreNonHeaderRowCheckBox();
		fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(1);
		
    }

	
	
	@When("^user selects a file$")
    public void user_selects_a_file() throws Throwable {
        fileSharePage.selectOneOrMoreNonHeaderRowCheckBox(1);
    }
		
	@And("^clicks the \"([^\"]*)\" button that appears$")
    public void clicks_the_something_button_that_appears(String strArg1) throws Throwable {
		Web.nextGenDriver.waitForAngular();
		fileSharePage.clickOnButton(strArg1);
	       openedPopupName=strArg1;
    }
    @Then("^the Move Files modal box should appear$")
    public void the_move_files_modal_box_should_appear() throws Throwable {
    	Web.nextGenDriver.waitForAngular();
        if(fileSharePage.isModalBoxPopupDisplayedOrNot("Move File(s)"))
        {
        	Reporter.logEvent(Status.PASS,"The Move Files modal box should appear",
					"The Move Files modal box is appeared",true);
        }
        else
        {
        	Reporter.logEvent(Status.FAIL,"The Move Files modal box should appear",
					"The Move Files modal box is appeared",true);
        }
        
    }

    @And("^user has upload and delete permissions and access to the Vault folder$")
    public void user_has_upload_and_delete_permissions_and_access_to_the_vault_folder() throws Throwable {
        //throw new PendingException();
    }
      
    @And("^the folder destination dropdown box should have the \"([^\"]*)\" option$")
    public void the_folder_destination_dropdown_box_should_have_the_something_option(String strArg1) throws Throwable {
    	if(fileSharePage.valueExistInselectDropDown("Sub",strArg1))
        {
        	Reporter.logEvent(Status.PASS,"The folder destination dropdown box should have the Vault Option",
					"The folder destination dropdown box  Vault Option is Presented",true);
        }
        else
        {
        	Reporter.logEvent(Status.FAIL,"The folder destination dropdown box should have the Vault Option",
					"The folder destination dropdown box  Vault Option is Not Presented",true);
        }
    	
    	fileSharePage.closeModalBoxPopup("Move File(s)");
    	
    	System.out.println(fileSharePage.getCountOfAllFiles());
    	
    	fileSharePage.displayAllFileNamesInAuditorFolder();
 
    	fileSharePage.checkFileNamesAreHavingGivenExtension("txt");
    	//fileSharePage.checkFileNamesAreHavingGivenExtension("png");
    	//fileSharePage.checkFileNamesAreHavingGivenExtension("pdf");
    	
    	fileSharePage.deselectOneOrMoreNonHeaderRowCheckBox();
    	List< String > FileNames1 = new ArrayList<>();
    	FileNames1.add("image.jpg");
    	fileSharePage.checkFilesAreExistInAuditorFolder(FileNames1);
    	fileSharePage.selectCheckBoxRelatedFileNames(FileNames1);
    	
    	Web.nextGenDriver.waitForAngular();
		fileSharePage.clickOnButton("Move");
	       openedPopupName="Move";
    	fileSharePage.selectDropDownValue("Sub", "Vault");
    	fileSharePage.checkTheCheckboxInMoveFileModalBox();
    	fileSharePage.clickOnMoveFiles();
    	
    	
    	fileSharePage.checkFilesAreExistInVaultFolder(FileNames1);
    	fileSharePage.checkFilesAreDoesNotExistInAuditorFolder(FileNames1);
    }
	
    
    @Given("^user has test.txt, test.png, and test.pdf in the Auditor folder$")
    public void user_has_testtxt_testpng_and_testpdf_in_the_auditor_folder() throws Throwable {
       
    	/*fileSharePage.checkFileNamesAreHavingGivenExtension("txt");
    	fileSharePage.checkFileNamesAreHavingGivenExtension("png");
    	fileSharePage.checkFileNamesAreHavingGivenExtension("pdf");*/
    	
    	// throw new PendingException();
    }

    @When("^user moves (.+) to Vault$")
    public void user_moves_to_vault(String filegroup) throws Throwable {
        
    	/*fileSharePage.deselectOneOrMoreNonHeaderRowCheckBox();
    	List< String > FileNames1 = new ArrayList<>();
    	FileNames1.add("image.jpg");
    	fileSharePage.checkFilesAreExistInAuditorFolder(FileNames1);
    	fileSharePage.selectCheckBoxRelatedFileNames(FileNames1);
    	
    	Web.nextGenDriver.waitForAngular();
		fileSharePage.clickOnButton("Move");
	       openedPopupName="Move";
    	fileSharePage.selectDropDownValue("Sub", "Vault");
    	fileSharePage.checkTheCheckboxInMoveFileModalBox();
    	fileSharePage.clickOnMoveFiles();
    	*/
    	///throw new PendingException();
    }

    @Then("^the moved (.+) should appear in list of files$")
    public void the_moved_should_appear_in_list_of_files(String filegroup) throws Throwable {
        // new PendingException();
    	
    	//fileSharePage.checkFilesAreExistInVaultFolder(FileNames1);
    }

    @And("^the moved (.+) should not be in the Auditor folder$")
    public void the_moved_should_not_be_in_the_auditor_folder(String filegroup) throws Throwable {
        //throw new PendingException();
    	
    	//fileSharePage.checkFilesAreDoesNotExistInAuditorFolder(FileNames1);
    }
	  
    @And("^User has selected a plan$")
    public void user_has_selected_a_plan(DataTable plandata ) throws Throwable {
       // throw new PendingException();
      	Web.getDriver().switchTo().defaultContent();
        String plan=Web.rawValues(plandata).get(0).get("plan");
        Globals.planNumber = plan;
		homePage = new HomePage();
		homePage.switchPlan(plan);
		//fileSharePage.navigateToFileSharingTab();
    }
    
   /* @And("^User has access to File Sharing$")
    public void user_has_access_to_file_sharing() throws Throwable {
        //throw new PendingException();
    }*/
    
    @When("^User navigates to the File Sharing tab$")
    public void user_navigates_to_the_file_sharing_tab() throws Throwable {
    	fileSharePage.navigateToFileSharingTab();
    }
    
    @Then("^User should see the \"([^\"]*)\" link$")
    public void user_should_see_the_something_link(String strArg1) throws Throwable {
        fileSharePage.isManageFolderNotificationsButtonVisible();
       
    }
    
   /* @SWEB-16663
    * Scenario: Verify when the Plus Button is present
    * Given User has access to a folder with subfolders
    * When The User opens the Notifications Manager
    * Then There should be a Plus Button next to the folder
    */
    
    @Given("^User has access to a folder with subfolders$")
    public void user_has_access_to_a_folder_with_subfolders() throws Throwable {
        //throw new PendingException();
       	    fileSharePage.isSubFoldersPresent();
    }
    @When("^The User opens the Notifications Manager$")
    public void the_user_opens_the_notifications_manager() throws Throwable {
        //throw new PendingException();
        fileSharePage.openFolderNotificationManager();
     }
    @Then("^There should be a Plus Button next to the folder$")
    public void there_should_be_a_plus_button_next_to_the_folder() throws Throwable {
    	fileSharePage.isPlusButtonPresentNexttoFolder();
    }
    
   /* @SWEB-16663
    * Scenario: Verify when the Plus Button converts to a Minus Button    
    * Given User has access to a folder with subfolders
    * And User had opened the Notifications Manager
    * When The User clicks on the Plus Button next to the folder     
    * Then The Plus Button should convert into a Minus Button      
    * And the Nested Subfolders section for that folder should expand      
    * And other Nested Subfolder sections should minimize      
    * And other Minus Buttons should convert into Plus Buttons 
    * */
    
    @And("^User had opened the Notifications Manager$")
    public void user_had_opened_the_notifications_manager() throws Throwable {
    	fileSharePage.openFolderNotificationManager();
    }
    
    @When("^The User clicks on the Plus Button next to the folder$")
    public void the_user_clicks_on_the_plus_button_next_to_the_folder() throws Throwable {
    	fileSharePage.clickOnPlusButtonNexttoFolder();
    }
    
    @Then("^The Plus Button should convert into a Minus Button$")
    public void the_plus_button_should_convert_into_a_minus_button() throws Throwable {
       fileSharePage.checkPlusButtonConvertedToMinus();
    }
    @And("^the Nested Subfolders section for that folder should expand$")
    public void the_nested_subfolders_section_for_that_folder_should_expand() throws Throwable {
        //throw new PendingException();
    }
    @And("^other Nested Subfolder sections should minimize$")
    public void other_nested_subfolder_sections_should_minimize() throws Throwable {
    	fileSharePage.otherNestedFolderSectionIsMinimised();
    }
    @And("^other Minus Buttons should convert into Plus Buttons$")
    public void other_minus_buttons_should_convert_into_plus_buttons() throws Throwable {
       // throw new PendingException();
    }
    
   /* @SWEB-16663 
    * Scenario: Verify when the Minus Button converts to a Plus Button    
    * Given User has access to a folder with subfolders      
    * And there is a Minus Button on the Notification Manager     
    * When The User clicks on the Minus Button        
    * Then The Minus Button should convert into a Plus Button      
    * And the Nested Subfolders section for that folder should minimize
    * */
    
    @And("^there is a Minus Button on the Notification Manager$")
    public void there_is_a_minus_button_on_the_notification_manager() throws Throwable {
    	fileSharePage.openFolderNotificationManager();
    	
    }
    @When("^The User clicks on the Minus Button$")
    public void the_user_clicks_on_the_minus_button() throws Throwable {
    	fileSharePage.clickOnMinusButton();
    }
    @Then("^The Minus Button should convert into a Plus Button$")
    public void the_minus_button_should_convert_into_a_plus_button() throws Throwable {
    	fileSharePage.checkMinusButtonConvertedToPlus();
    }
    @And("^the Nested Subfolders section for that folder should minimize$")
    public void the_nested_subfolders_section_for_that_folder_should_minimize() throws Throwable {
        //throw new PendingException();
    }
    
    /*@SWEB-16663
     * Scenario: Verify changes to preferences in the Notification Manager are preserved    
     * Given User has made a change to their subfolder notification preferences      
     * And User has minimized the Nested Subfolders     
     * When User re-opens the Nested Subfolders     
     * Then User's changes should be preserved
     */    
    
    @Given("^User has made a change to their subfolder notification preferences$")
    public void user_has_made_a_change_to_their_subfolder_notification_preferences() throws Throwable {
    	fileSharePage.isSubFoldersPresent();
    	fileSharePage.openFolderNotificationManager();
    	fileSharePage.clickOnPlusButtonNexttoFolder();
    	fileSharePage.changePrefereanceBySelectingSubFoldersCheckBox();
    }
    
    @And("^User has minimized the Nested Subfolders$")
    public void user_has_minimized_the_nested_subfolders() throws Throwable {
        //throw new PendingException();
        fileSharePage.minimizeSubfolderSection();
    }
    @When("^User reopens the Nested Subfolders$")
    public void user_reopens_the_nested_subfolders() throws Throwable {
    	fileSharePage.reopenPreviouslyMinimizedNotificationFolder();
    }
    
    @Then("^User's changes should be preserved$")
    public void users_changes_should_be_preserved() throws Throwable {
      
        if(fileSharePage.isCheckChangesIsPreserved())
			Reporter.logEvent(Status.PASS, "User has made a change in subfolder notification preferences are preserved",
				" ", true);
		else
			Reporter.logEvent(Status.FAIL, "User has not made any change in subfolder notification preferences are not preserved",
					" ", true);
    }
    
    @When("^User closes the Notifications Manager without clicking 'Update'$")
    public void user_closes_the_notifications_manager_without_clicking_update() throws Throwable {
    	fileSharePage.closeFolderNotificationsModal();
    }
    
    @Then("^User's changes should be NOT be preserved$")
    public void users_changes_should_be_not_be_preserved() throws Throwable {
    	fileSharePage.openFolderNotificationManager();
    	fileSharePage.reopenPreviouslyMinimizedNotificationFolder();
    	if(!fileSharePage.isCheckChangesIsPreserved())
			Reporter.logEvent(Status.PASS, "User made changes in subfolder notification preferences are not preserved",
				" ", true);
		else
			Reporter.logEvent(Status.FAIL, "User made changes in subfolder notification preferences are preserved",
					" ", true);
    }
    
    
    @Given("^User has expanded a folder's nested subfolders$")
    public void user_has_expanded_a_folders_nested_subfolders() throws Throwable {
    	fileSharePage.isSubFoldersPresent();
    	fileSharePage.openFolderNotificationManager();
    	fileSharePage.clickOnPlusButtonNexttoFolder();
    }
    @When("^User selects the 'Select all subfolders' option$")
    public void user_selects_the_select_all_subfolders_option() throws Throwable {
       // throw new PendingException();
    	fileSharePage.selectAllFolderSubFoldersOptions();
    	JavascriptExecutor javascript = (JavascriptExecutor) Web.getDriver();
    	Boolean b2 = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
    }
    @Then("^All subfolder options for that folder should be selected$")
    public void all_subfolder_options_for_that_folder_should_be_selected() throws Throwable {
    	fileSharePage.checkAllSubFoldersOptionsisChecked();
    }
    
    
    @When("^User deselects the 'Select all subfolders' option$")
    public void user_deselects_the_select_all_subfolders_option() throws Throwable {
    	fileSharePage.selectAllFolderSubFoldersOptions();
    	fileSharePage.deSelectAllFolderSubFoldersOptions();
    }
    @Then("^All subfolder options for that folder should be deselected$")
    public void all_subfolder_options_for_that_folder_should_be_deselected() throws Throwable {
    	fileSharePage.checkAllSubFoldersOptionsisChecked();
    }
    
    @And("^User has selected 'Select all subfolders'$")
    public void user_has_selected_select_all_subfolders() throws Throwable {
    	fileSharePage.selectAllFolderSubFoldersOptions();
    }
    
    @When("^User deselects a selected subfolder$")
    public void user_deselects_a_selected_subfolder() throws Throwable {
    	fileSharePage.deSelectOneSubFolder();
    }
    
    @Then("^The 'Select all subfolders' option should be deselected$")
    public void the_select_all_subfolders_option_should_be_deselected() throws Throwable {
        if(!fileSharePage.checkSelectAllFolderOptionIsSelected())
        {
        	Reporter.logEvent(Status.PASS, "Select all subfolders option should be deselected",
					"Select all subfolders option is deselected", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Select all subfolders option should be deselected",
					"Select all subfolders option is not deselected", false);
		}
    }
    
    @When("^User manually selects all subfolders$")
    public void user_manually_selects_all_subfolders() throws Throwable {
    	fileSharePage.selectAllFoldersIndividuavally();
    }
    @But("^does not select 'Select all subfolders'$")
    public void does_not_select_select_all_subfolders() throws Throwable {
    	// fileSharePage.checkSelectAllFolderOptionIsSelected();
    }
    
    @Then("^The 'Select all subfolders' option should be selected$")
    public void the_select_all_subfolders_option_should_be_selected() throws Throwable {
    	if(fileSharePage.checkSelectAllFolderOptionIsSelected())
        {
        	Reporter.logEvent(Status.PASS, "Select all subfolders option should be selected",
					"Select all subfolders option is selected", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Select all subfolders option should be selected",
					"Select all subfolders option is not selected", false);
		}
    }
    
    @Given("^User has access to folders and subfolders in the Notifications Manager$")
    public void user_has_access_to_folders_and_subfolders_in_the_notifications_manager() throws Throwable {
    	fileSharePage.isSubFoldersPresent();
    	fileSharePage.openFolderNotificationManager();
    }
    @When("^User clicks 'Select all' button$")
    public void user_clicks_select_all_button() throws Throwable {
    	fileSharePage.clickOnSelectAllButton();
    }
    @Then("^All folders and subfolders within the Notifications Manager are selected$")
    public void all_folders_and_subfolders_within_the_notifications_manager_are_selected() throws Throwable {
    	fileSharePage.checkAllFolderCheckBoxesAreChecked();
    }
}
