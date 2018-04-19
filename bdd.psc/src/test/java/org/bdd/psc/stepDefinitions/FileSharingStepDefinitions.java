/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.util.List;

import lib.Web;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import gherkin.formatter.model.Scenario;

import org.bdd.psc.pageobjects.FileSharingPage;
import org.bdd.psc.pageobjects.ForgotPasswordPage;
import org.bdd.psc.pageobjects.HomePage;
import org.bdd.psc.pageobjects.JumpPage;
import org.bdd.psc.pageobjects.LoginPage;
import org.bdd.psc.pageobjects.UserVerificationPage;

import reporter.Reporter;
import cucumber.api.DataTable;
import cucumber.api.Delimiter;
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
	FileSharingPage fileSharePage;
	int noOfCheckboxToSelect;
	private static String rootFolderName;
	String subFolderName;
	private String openedPopupName;
	

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
       fileSharePage.cancelModalBoxPopup(modalbox);
    }
    @When("^User closes the \"([^\"]*)\"$")
    public void user_closes_the_something(String modalbox) throws Throwable {
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
	    	List<String> sfolder = fileToSelect.asList(String.class);
	    	String fileName=sfolder.get(1);
			String folderName=sfolder.get(2);
			if(fileName.trim().equalsIgnoreCase("parent"))
				 fileSharePage.openFolder(folderName);
			 if(fileName.trim().equalsIgnoreCase("sub"))
				 fileSharePage.openSubFolder(folderName);
	        
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





    
   


}
