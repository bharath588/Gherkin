package pageobjects;

import java.util.concurrent.TimeUnit;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import framework.util.CommonLib;

public class LoanRequest extends LoadableComponent<LoanRequest> {
	
	LoadableComponent<?> parent;
	
	
	public LoanRequest()
	{
		PageFactory.initElements(Web.getDriver(), this);
		// TODO Auto-generated constructor stub
	}

	Actions action = new Actions(Web.getDriver());
	
	@FindBy(xpath = "//*[@class='pageMenuTitle' and text()='Loans']")
	 WebElement loanRequest;

	@FindBy(id = "oCMenu_316")
	 WebElement menuPPTChanges;	
	
	@FindBy(xpath = "//*[text()='Loan Request']")
	 WebElement menuLoanQuote;
	
	//Megha ID 
	@FindBy(xpath = ".//*[@id='activeLoans']/div/div[5]/div[2]/a")
	WebElement loan_number;
	
	@FindBy(id = "titleTab")
	WebElement loan_history_page_title_tab;	

	@FindBy(name="searchPartId")
	WebElement participant_id;
	
	
	@FindBy(name = "mobPhoneId")
	WebElement mobile_phone;
	
	@FindBy(name="email")
	WebElement inpEmail;
	
	@FindBy(xpath = ".//*[@id='activeLoans']/div")
	WebElement outstanding_loan;
	
	@FindBy(id = "mobPhoneError")
	WebElement mobile_error;
	
	@FindBy(id= "emailId")
	WebElement email_field;
	@FindBy(name="homePhoneId")
	WebElement home_phone;
	
	
	@FindBy(id = "paymentmethodCode-GENERAL")
	WebElement payment_method;

	
	@FindBy(id = "vestPercentValue-GENERAL-0")
	WebElement vesting1;
	
	@FindBy(id = "vestPercentError-GENERAL-0")
	WebElement vesting_error;	
	
	@FindBy(xpath = ".//*[@id='activeLoans']/div/div[10]/div[1]/input")
	WebElement eligible_checkbox;
	
	@FindBy(id = "refinanceSelectedButton")
	WebElement refinance_selected_button;
	
	@FindBy(id = "borrowAmount-GENERAL")
	WebElement amount_to_borrow;
	
	@FindBy(xpath = ".//*[@id='availableToBorrowAmount-GENERAL']")
	WebElement available_to_borrow;
	
	@FindBy(id = "fiveQuickQuotes-GENERAL")
	WebElement quick_qoutes_button;
	
	@FindBy(id = "addButton-GENERAL")
	WebElement add_button;
	
	@FindBy(xpath = ".//*[@id='buttonIndex-0']/input")
	WebElement loan_qoute1_radio_button;
	
	@FindBy(id = "editAddress")
	WebElement edit_address_link;
	
	@FindBy(xpath = ".//*[@id='viewAddress']/div/div/div[2]/div[1]/div[2]/input")
	WebElement input_address;
	
	@FindBy(id = "okAddress")
	WebElement ok_button;
	
	@FindBy(xpath = ".//*[@id='recalculateButton-GENERAL']")
	WebElement recalculate_button;
	
	@FindBy(xpath = ".//*[@id='loanTerm-GENERAL']")
	WebElement loan_term;
	
	@FindBy(xpath = ".//*[@id='buttonIndex-0']/div/i")
	WebElement selected_loan_quote;
	
	@FindBy(xpath = "//input[@id='continue']")
	WebElement continue_button;
	
	@FindBy(xpath = ".//*[@id='paperFormOrSubmit']/div[3]/div/div[1]/label")
	WebElement refinance_loan_label;
	
	
	@Override
	protected void isLoaded() throws Error {
		if( CommonLib.checkForPpt())
		Assert.assertTrue(Web.isWebElementDisplayed(loanRequest));
		else{
			Assert.assertTrue(false);
		}
		
	}

	@Override
	protected void load() {
		try {
		// TODO Auto-generated method stub
		this.parent = new ParticipantHome().get();	
	
		new ParticipantHome().search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
				Stock.GetParameterValue("PPT_ID"));
		
		Web.mouseHover(menuPPTChanges);
		if (Web.isWebElementDisplayed(menuLoanQuote)) {
			Web.clickOnElement(menuLoanQuote);	
			
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
			
			if (Web.isWebElementDisplayed(loanRequest, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Loan Request page displayed or not",
						"Loan Quote page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Loan Request page displayed or not",
						"Loan Quote didn't get displayed successfully", true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan Request Link on Participant Changes tab displayed or not",
					" Loan Request Link on Participant Changes tab didn't get displayed successfully",
					true);
		}	
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	/* 
	 * @Author : Megha
	 * @Date :
	 * @Manaul Test cases 
	 */

	public void validating_loan_number_link() {				
			if(Web.isWebElementDisplayed(loan_number, true)){
			Web.clickOnElement(loan_number);
			Reporter.logEvent(Status.INFO,"Clicking on the loan number link","Clicking on loan number link takes us to next page", false);
			if (Web.isWebElementDisplayed(loan_history_page_title_tab, true)) {
				Reporter.logEvent(Status.PASS,
						"Checking if LOAN INFO/HISTORY page is displayed on clicking loan number link",
						"LOAN INFO/HISTORY page is displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Checking if LOAN INFO/HISTORY page is displayed on clicking loan number link",
						"LOAN INFO/HISTORY page is not displayed", true);
			}}
	else{
		Reporter.logEvent(Status.FAIL,
				"Loan Number is not  displayed for this participant",
				"Loan Number is not  displayed for this participant", true);
		
	}
	}
	
	
	public void validating_mobile_phone() {
			Web.waitForElement(mobile_phone);
			verify_ErrorMssage_Email_InputBox("ahghhjhjh", "Enter a valid mobile phone number.", "Checking for character values in mobile number field");
      		verify_ErrorMssage_Email_InputBox("a12$%ghsd90", "Enter a valid mobile phone number.", "Checking for alpha numeric character values in mobile number field");
      		verify_ErrorMssage_Email_InputBox("123564852635", "Enter a valid mobile phone number.", "Checking for more than 10 characters values in mobile number field");
			verify_ErrorMssage_Email_InputBox(Stock.GetParameterValue("Mobile Phone"), "", "Checking for valid phone number value in mobile number field");
}
	
	private void verify_ErrorMssage_Email_InputBox(String sValue, String sErrorMsg,String sMsg){		
		Web.setTextToTextBox(mobile_phone,sValue);
		action.sendKeys(Keys.TAB).build().perform();
		Web.waitForElement(mobile_error);
		String error4 = mobile_error.getText();
		if (validateErrorMessage(error4, sErrorMsg)) {
			Reporter.logEvent(Status.PASS,
					sMsg,
					"Error message is not displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Error message expected :"+sErrorMsg
					,
					" But Actual was : " + error4, false);

		}

	}

	public boolean validateErrorMessage(String actual, String sExpText) {
		boolean areSame = false;

		if (actual == null && sExpText == null) {
			areSame = true;
			return areSame;
		}
		/*
		 * else if ((actual != null && sExpText == null ) || (actual == null &&
		 * sExpText != null )) { areSame = false; return areSame; }
		 */
		else if (actual.isEmpty() && sExpText.isEmpty()) {
			areSame = true;
			return areSame;
		}

		if (sExpText.equalsIgnoreCase(actual)) {
			Reporter.logEvent(Status.PASS, "Error message should be displayed",
					sExpText, false);
			areSame = true;
			return areSame;
		} else {
			Reporter.logEvent(Status.FAIL, "Error message expected :"
					+ sExpText, " But Actual was : " + actual, false);
		}
		return areSame;
	}
	
	

	public void validating_payment_method_noneditable() {
	

//			Web.waitForElement(partId);
//			Web.setTextToTextBox(partId,
//					Stock.GetParameterValue("participantId"));
//			Web.waitForElement(submit);
//			Web.clickOnElement(submit);
//			Web.waitForElement(participant_changes);
//			Web.clickOnElement(participant_changes);
//			Web.waitForElement(loan_request);
//			Web.clickOnElement(loan_request);
			Web.waitForElement(payment_method);

			String tag_name = payment_method.getTagName();
			System.out.println("the tag name is :" + tag_name);
			if (tag_name != "input") {
				Reporter.logEvent(
						Status.PASS,
						"Verifying if the payment method is a non-editable field",
						"Payment method is a non-editable field", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying if the payment method is a non-editable field",
						"Payment method is a editable field", false);
			}
	

	}
	

	public void validating_vesting_field() {
			Web.waitForElement(vesting1);
			Web.clickOnElement(vesting1);
			Web.setTextToTextBox(vesting1, "aajhgdj");
			Web.waitForElement(vesting_error);
			String error1 = vesting_error.getText();
			System.out.println("on entering alphabets in vesting field:"
					+ error1);
			Reporter.logEvent(Status.INFO,"Checking the vesting field for alphabets","On entering alphabets in the vesting field:"+error1,false );
			Web.setTextToTextBox(vesting1, "*&^%");
			Web.waitForElement(vesting_error);
			String error2 = vesting_error.getText();
			System.out
					.println("on entering spectial characters in vesting field:"
							+ error2);
			Reporter.logEvent(Status.INFO,"Checking the vesting field for special characters","On entering special characters in the vesting field:"+error2,false );
			Web.setTextToTextBox(vesting1, Stock.GetParameterValue("Vesting"));
			Reporter.logEvent(Status.INFO,"Entering a valid number in the vesting field","A valid number is added to the vesting field",false);
			if (vesting_error.isDisplayed()) {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying if the vesting field is accepting only numerical value",
						"Vesting field is not accepting only numerical value",
						false);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Verifying if the vesting field is accepting only numerical value",
						"Vesting field is accepting only numerical value",
						false);
			}
		 
	}
	
	
	public void validating_recalculate_buttton() {
	     	Web.waitForElement(eligible_checkbox);
			Web.clickOnElement(eligible_checkbox);
			Web.waitForElement(refinance_selected_button);
			Web.clickOnElement(refinance_selected_button);
			Web.setTextToTextBox(amount_to_borrow,
					Stock.GetParameterValue("Amount to Borrow"));
			String amount1 = available_to_borrow.getText();
			Reporter.logEvent(Status.INFO,
					"Changing the value in the amount to borrow text box",
					"Amount has been changed in the amount to borrow text box",
					false);
			Web.waitForElement(add_button);
			Web.clickOnElement(add_button);
			Web.waitForElement(loan_qoute1_radio_button);
			Web.clickOnElement(loan_qoute1_radio_button);
			Web.waitForElement(recalculate_button);
			Web.clickOnElement(recalculate_button);
			Reporter.logEvent(Status.INFO,"clicking on recalculate button","Recalculate button is clicked", false);	
			Web.waitForElement(amount_to_borrow);
			String amount2 = "$" + amount_to_borrow.getAttribute("value");
			

			if (amount1.equalsIgnoreCase(amount2)) {
				Reporter.logEvent(Status.PASS,
						"Verifying recalculate button functionality",
						"Recalculate button functionality successfull", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying recalculate button functionality",
						"Recalculate button functionality not successfull",
						false);
			}
		
	
		Web.clickOnElement(eligible_checkbox);
	}
	

	public void verifying_enable_of_select_button() {
		try {
			Web.waitForElement(amount_to_borrow);
			Web.setTextToTextBox(amount_to_borrow,
					Stock.GetParameterValue("Amount to Borrow"));
			Reporter.logEvent(Status.INFO,"Value to be added into amount to borrow field","Value is added into amount to borrow field",false );
			Web.waitForElement(loan_term);
			Web.setTextToTextBox(loan_term,
					Stock.GetParameterValue("Loan Terms"));
			Reporter.logEvent(Status.INFO,"Value to be added into loan term field","Value is added into loan term field",false );
			//Web.waitForElement(add_button);
			Web.clickOnElement(add_button);
			Web.waitForElement(loan_qoute1_radio_button);
			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO,"Selecting a loan quote","First loan quote is selected",false);
			//Thread.sleep(5000);
		    Web.waitForElement(selected_loan_quote);
		    
			if (selected_loan_quote.isDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Verifying if the green tick appeared",
						"Green tick appeared for the selected loan quote",
						false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying if the green tick appeared",
						"Green tick appeared for the selected loan quote",
						false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void refinance() {
		try {

			Web.waitForElement(eligible_checkbox);
		
			Web.clickOnElement(eligible_checkbox);
			Web.waitForElement(refinance_selected_button);
			Web.clickOnElement(refinance_selected_button);		
			Web.waitForElement(amount_to_borrow);
		
			Web.setTextToTextBox(amount_to_borrow,
					Stock.GetParameterValue("Amount to Borrow"));
			Reporter.logEvent(Status.INFO,"Changing the value in the amount to borrow text box","Amount has been changed in the amount to borrow text box",false);
			Web.waitForElement(add_button);
			Web.clickOnElement(add_button);
			
			Web.waitForElement(loan_qoute1_radio_button);
			Web.clickOnElement(loan_qoute1_radio_button);
			Web.waitForElement(edit_address_link);
			Web.clickOnElement(edit_address_link);
		
			Web.waitForElement(input_address);
			
			Web.setTextToTextBox(input_address,
					Stock.GetParameterValue("address"));
			Reporter.logEvent(
					Status.INFO,
					"Editing address using the edit address link",
					"Address is edited using the edit address link"
							+ Stock.GetParameterValue("address"), false);
			// ok_button.click();
			Web.clickOnElement(ok_button);
			Thread.sleep(5000);
		
			Web.waitForElement(mobile_phone);
			
			Web.setTextToTextBox(mobile_phone,
					Stock.GetParameterValue("Mobile Phone"));
			Thread.sleep(5000);
			Reporter.logEvent(
					Status.INFO,
					"Editing mobile number",
					"Mobile number is edited"
							+ Stock.GetParameterValue("Mobile Phone"), false);
			Web.clickOnElement(continue_button);
			Thread.sleep(5000);
			Reporter.logEvent(Status.INFO,
					"Clicking the continue button to move to the next page",
					"Continue button clicked and moved to next page", false);
			
			Web.waitForElement(refinance_loan_label);
			if (Web.isWebElementDisplayed(refinance_loan_label, true)) {
				Reporter.logEvent(Status.PASS,
						"Verify if participant has loan refinancing",
						"Participant has loan refinancing", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify if participant has loan refinancing",
						"Participant does not have loan refinancing", false);
			}

			Web.isWebElementDisplayed(outstanding_loan);

		
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
}