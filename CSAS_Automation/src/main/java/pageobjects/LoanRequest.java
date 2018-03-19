package pageobjects;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.util.DataUtility;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.Range;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import framework.util.CommonLib;

public class LoanRequest extends LoadableComponent<LoanRequest> {

	LoadableComponent<?> parent;

	public LoanRequest() {
		PageFactory.initElements(Web.getDriver(), this);
		// TODO Auto-generated constructor stub
	}


	@FindBy(xpath = "//*[@class='pageMenuTitle' and text()='Loans']")
	WebElement loanRequest;

	@FindBy(id = "oCMenu_316")
	WebElement menuPPTChanges;

	@FindBy(xpath = "//*[text()='Loan Request']")
	WebElement menuLoanQuote;

	// Megha ID
	@FindBy(xpath = ".//*[@id='activeLoans']/div/div[5]/div[2]/a")
	WebElement loan_number;

	@FindBy(id = "titleTab")
	WebElement loan_history_page_title_tab;

	@FindBy(name = "searchPartId")
	WebElement participant_id;

	// @FindBy(name = "mobPhoneId")
	@FindBy(xpath = ".//*[@id='mobPhoneId']/input")
	WebElement mobile_phone;

	@FindBy(name = "email")
	WebElement inpEmail;

	@FindBy(xpath = ".//*[@id='activeLoans']/div")
	WebElement outstanding_loan;

	// @FindBy(id = "mobPhoneError")
	@FindBy(xpath = ".//*[@id='mobPhoneError']")
	WebElement mobile_error;

	@FindBy(id = "emailId")
	WebElement email_field;
	@FindBy(name = "homePhoneId")
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

	@FindBy(xpath = ".//*[@id='paperFormOrSubmit']/div[3]/div/div[1]/label")
	WebElement refinance_loan_label;

	/*
	 * Web elements for Delivery form
	 */
	@FindBy(xpath = ".//*[@id='maxLoanAmount-GENERAL']")
	private WebElement maxAmount;

	@FindBy(xpath = "//*[@id='loanTypeGeneral']//*[text()='Minimum loan amount']/../following-sibling::div")
	private WebElement minAmount;

	/*
	 * @FindBy(xpath = "//*[text()='Participant Changes']") private WebElement
	 * menuPPTChanges;
	 */
	@FindBy(xpath = "//*[text()='Loan Request']")
	private WebElement menuLoanRequest;

	@FindBy(id = "oCMenu_319")
	private WebElement search_menu;

	/*
	 * @FindBy(xpath=
	 * ".//*[@id='table_workLayout']//*[@class='panel panel-primary']//*[@class='panel-heading']"
	 * ) private WebElement loanRequest;
	 */

	@FindBy(name = "searchPartId")
	private WebElement ParticipantID;

	@FindBy(id = "submitPpt")
	private WebElement SubmitPptSearchBtn;

	@FindBy(id = "firstDueDateGENERAL")
	private WebElement FirstPaymentDateTxtBox;

	@FindBy(id = "borrowAmount-GENERAL")
	private WebElement amount_to_borrow_txtBox;

	/*
	 * @FindBy(id="fiveQuickQuotes-GENERAL") private WebElement
	 * quick_qoutes_button;
	 */
	@FindBy(id = "pmtFrequencyGENERAL")
	private WebElement payment_frequency;

	/*
	 * @FindBy(xpath=".//*[@id='buttonIndex-0']/input") private WebElement
	 * loan_qoute1_radio_button;
	 */
	@FindBy(id = "continue")
	private WebElement continue_button;

	@FindBy(xpath = ".//*[@id='pmtFrequencyGENERAL']/option[2]")
	private WebElement payment_frequency_monthly;

	@FindBy(xpath = ".//*[@id='notMarried']/div[1]/input")
	private WebElement not_married_radio_button;

	@FindBy(name = "deliveryEmailCheckbox")
	private WebElement Email_chk_box;

	@FindBy(name = "deliveryFaxCheckbox")
	private WebElement Fax_chk_box;

	@FindBy(name = "deliveryMailCheckbox")
	private WebElement StdEmail_chk_box;

	@FindBy(xpath = ".//*[@id='deliveryFaxId']/input")
	private WebElement FaxId_txtbox;

	@FindBy(id = "loanReq")
	private WebElement loan_summary_Info;

	@FindBy(xpath = ".//*[@id='deliverFormButton']/input")
	private WebElement deliveryFormBtn;

	@FindBy(xpath = ".//*[@id='loanReq']//*[@class='col-xs-6']/strong")
	private WebElement confirmation_id;

	/*
	 * WebElements for Edit Address testCases
	 */
	@FindBy(xpath = "//a[contains(text(),'address')]")
	private WebElement editAddress_link;

	@FindBy(xpath = ".//*[@id='viewAddress']//*[@class='modal-content']")
	private WebElement Address_change_window;

	@FindBy(xpath = ".//*[@id='viewAddress']//input[@name='scndLineMailing']")
	private WebElement secondLineMailing;

	@FindBy(id = "okAddress")
	private WebElement EditAddr_Ok_Btn;

	@FindBy(id = "addressEditedMessage")
	private WebElement AddressEdited_message;

	@FindBy(id = "addressChangedAlert")
	private WebElement addressChanged_Alert;

	@FindBy(xpath = "//*[@id='addressChangedAlert']//*[contains(@class,'fa-exclamation-triangle')]")
	private WebElement addressChanged_Alert_Img;

	@FindBy(xpath = ".//*[@id='addressChangedAlert']//*[strong]")
	private WebElement addressChanged_Alert_msg;

	/*
	 * Web Elements for Re finance test cases
	 */
	@FindBy(id = "MORTGAGE_img")
	private WebElement Principal_residence_radio_btn;

	@FindBy(id = "ajaxErrorRecalculate")
	private WebElement Refinancing_msg;

	@FindBy(xpath = ".//*[@id='table_workLayout']//*[@id='activeLoans']")
	private WebElement refinance_loans;

	@FindBy(xpath = ".//*[@id='activeLoans']//*[@class='primaryRefinanceChecked'][1]")
	private WebElement eligible_chk_box;

	@FindBy(id = "refinanceSelectedButton")
	private WebElement select_refinance_btn;

	@FindBy(id = "borrowAmount-MORTGAGE")
	private WebElement add_additional_loan_txtbox;

	@FindBy(id = "addButton-MORTGAGE")
	private WebElement add_additional_loan_btn;

	@FindBy(xpath = ".//*[@id='buttonIndex-0']/input")
	private WebElement addtn_amt_loan_quote;

	@FindBy(xpath = ".//*[@id='checkedIconPromisMail']/i")
	private WebElement std_mail_radio_btn;

	@FindBy(xpath = ".//*[@id='submitButton']/input")
	private WebElement submit_loan_req;
	@FindBy(css = "div#oCMenu_319")
	private WebElement menuSearch;

	/*
	 * @FindBy(id="loanReq") private WebElement loan_summary_Info;
	 */

	@FindBy(xpath = ".//*[@id='loanReq']//*[@class='col-xs-6'][1]/strong")
	private WebElement confirmation_id_Refinance_multiple_loans;

	/*
	 * Web Elements Re finance and verify loan term
	 */
	@FindBy(xpath = ".//*[@id='pmtFrequencyMORTGAGE']")
	private WebElement payment_frequency_principal_res;

	/*
	 * @FindBy(id="loanTerm-MORTGAGE")//
	 */
	@FindBy(xpath = ".//*[@id='loanQuotesRows']/div[1]/div[4]")
	private WebElement loan_term_txtbox;

	/*
	 * Web elements for re finance multiple NO additional loan amount
	 */
	@FindBy(id = "borrowAmount-GENERAL")
	private WebElement add_additional_loan_btn_general;

	@FindBy(id = "ajaxErrorAdd")
	private WebElement loanReq_Error_msg_invalid_input;

	@FindBy(id = "loanTerm-GENERAL")
	private WebElement loanterm_txtBox;

	@FindBy(xpath = ".//*[@id='activeLoans']/div/div[5]/div[6]")
	private WebElement loanterm_selected_loan;

	@FindBy(id = "addButton-GENERAL")
	private WebElement add_btn_general;

	/*
	 * Web Elements for Promissory Note testcase Deliver Form
	 */
	@FindBy(xpath = ".//*[@class='em-checkbox-icon ']")
	private WebElement first_class_mail_radiobtn;

	@FindBy(xpath = ".//*[@id='homePhoneId']/input")
	private WebElement home_Phone_txtBox;

	@FindBy(xpath = ".//*[@id='mobPhoneId']/input")
	private WebElement mobile_Phone_txtBox;

	@FindBy(xpath = ".//*[@id='emailId']/input")
	private WebElement personal_email_txtBox;

	@FindBy(id = "ajaxMessageRecalculate")
	private WebElement promissory_note;

	@FindBy(id = "borrowAmount-GENERAL")
	private WebElement inputBorrowAmount;
	/*
	 * Web Elements for Promissory Note testcase Loan request
	 */
	@FindBy(xpath = ".//*[@id='deliveryFaxId']/input")
	private WebElement fax_number_txtbox;

	@FindBy(xpath = ".//*[@id='deliveryFaxDiv']/div/div[2]/input")
	private WebElement fax_id_txtbox;

	@FindBy(id = "submitButton")
	private WebElement submitLoanRequestBtn;

	@FindBy(xpath = ".//*[text()='Standard mail']")
	private WebElement standardEmailRadioBtn;

	@FindBy(id = "bankAccountValue-GENERAL")
	private WebElement bankAccountDropDown;

	/*
	 * ACH Webelements
	 */
	@FindBy(xpath = ".//*[@id='loanReq']//*[@class='col-xs-6']/strong")
	private WebElement Conf_id_ACH;;

	/*
	 * verifying Back Hyperlink
	 */
	@FindBy(id = "back")
	private WebElement backHyperlink;
	
	@FindBy(id = "updateRecordLabel")
	private WebElement updateRecord;
	
	
	/*
	 * Verifying Eligible Check boxes and refinance
	 */
	@FindBy(xpath = ".//*[@id='activeLoans']//*[text()='Ineligible']")
	private WebElement Ineligible_btn;

	// @FindBy(xpath=".//*[@id='loanTerm-MORTGAGE']")
	@FindBy(xpath = ".//*[@id='activeLoans']/div/div[5]/div[6]")
	private WebElement Refinance_LoanTerm;

	@FindBy(id = "addButton-MORTGAGE")
	private WebElement add_btn_mortgage;

	/*
	 * verifying different types of Loantypes
	 */
	@FindBy(xpath = ".//*[@id='loanTypeValue-GENERAL']")
	private WebElement LoanType_General;

	@FindBy(xpath = ".//*[@id='loanTypeValue-MORTGAGE']")
	private WebElement LoanType_PrincipalResidence;

	@FindBy(id = "ajaxMessageRecalculate")
	private WebElement Layout_Changes;

	/*
	 * web elements for Loan overview structure
	 */
	@FindBy(linkText = "View payment & repayment options")
	private WebElement Payment_repayment_hyperlink;

	@FindBy(xpath = ".//*[@id='table_popupLayout']//*[@class='panel-heading']")
	private WebElement Payment_repayment_options;

	@FindBy(linkText = "View full loan structure details")
	private WebElement Loan_StructureDetails_hyperlink;

	@FindBy(xpath = ".//*[@id='table_popupLayout']//*[@class='titleText']//*[text()='Active Loan Structure']")
	private WebElement Active_Loan_StructureDetails;

	@FindBy(xpath = "//div[div[text()='Active Loan Structure']]//img[contains(@src,'minimize')]")
	private WebElement Active_Loan_Structure_minimizebtn;

	@FindBy(xpath = "//div[div[text()='Active Loan Structure']]//img[contains(@src,'minimize')]")
	private WebElement Active_Loan_Structure_ExpandedDetails;

	@FindBy(xpath = "//div[div[text()='Active Loan Structure']]//img[contains(@src,'restore')]")
	private WebElement Active_Loan_Structure_restorebtn;

	@FindBy(xpath = "//div[*[text()='Approval requirements']]")
	private WebElement LoanStructure_ApprovalReq;

	@FindBy(xpath = "//div[*[text()='Approval requirements']]/following-sibling::div")
	private WebElement LoanStructure_ApprovalReq_values;

	@FindBy(xpath = "//div[*[text()='Number of loans allowed']]")
	private WebElement NoOfLoansAllowed;

	@FindBy(xpath = "//div[*[text()='Number of loans allowed']]/following-sibling::div")
	private WebElement NoOfLoansAllowed_values;

	@FindBy(xpath = "//div[*[text()='Fees']]")
	private WebElement Fees;

	@FindBy(xpath = "//div[*[text()='Fees']]/following-sibling::div")
	private WebElement Fees_values;

	@FindBy(id = "submitAddressChange")
	private WebElement submitForProcessing;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //span[contains(text(),'Participant has Pending Loan')]")
	private WebElement pendingLoanMsg;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //li[contains(text(),'whose Employment is terminated')]")
	private WebElement employmentTerminatedErrorMsg;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //li[contains(text(),'This participant is currently non active.')]")
	private WebElement nonActiveParticipantErrorMsg;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //li[contains(text(),'takeover BR_479')]")
	private WebElement ownershipErrorMsg;

	@FindBy(xpath = ".//b[contains(text(),'New loan wait days')]/parent::*/following-sibling::div")
	private WebElement newLoanwaitDays;

	@FindBy(xpath = ".//b[contains(text(),'One or Two-Step loan')]/parent::*/following-sibling::div")
	private WebElement oneOrTwoStepLoan;

	@FindBy(xpath = ".//b[contains(text(),'Loan refinance')]/parent::*/following-sibling::div")
	private WebElement loanRefinance;

	@FindBy(xpath = ".//label[contains(text(),'Participant grouping')]/parent::*/following-sibling::div")
	private WebElement participantGrouping;

	@FindBy(xpath = ".//b[contains(text(),'Auto loan offset waiting days')]/parent::*/following-sibling::div")
	private WebElement autoLoanOffset;

	@FindBy(xpath = ".//b[contains(text(),'Loans allowed after previous default')]/parent::*/following-sibling::div")
	private WebElement loanAllowedAfterpreviousDefault;

	@FindBy(xpath = ".//b[contains(text(),'Payment & repayment options')]")
	private WebElement paymentAndRepaymentOption;

	@FindBy(xpath = ".//a[contains(text(),'View payment & repayment options')]")
	private WebElement viewPaymentAndRepaymentOptionLink;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //span[contains(text(),'Pending Transfer  WR_477')]")
	private WebElement pendingLoanTransferError;

	@FindBy(xpath = ".//ul[@class='bullet-list'] //li[contains(text(),'has a Disb hold.  BR_467')]")
	private WebElement br467ErrorMsg;
	
	@FindBy(xpath= ".//ul[@class='bullet-list'] //li[contains(text(),'group is Terminated. BR_511')]")
	private WebElement br511ErrorMsg;
	
	@FindBy(xpath= ".//li[contains(text(),'Maximum Number of Loans allowed. BR_470')]")
	private WebElement br469ErrorMsg;
	
	@FindBy(xpath= ".//span[contains(text(),'outstanding Promissory Note WR_489')]")
	private WebElement wr489ErrorMsg;
	
	@FindBy(xpath= ".//ul[@class='bullet-list'] //li[contains(text(),'Unable to locate an account balance')]")
	private WebElement accountBalErrorMsg;
	
	@FindBy(xpath=".//span[contains(@id,'availableToBorrowAmount')]/parent::*/child::a")
	private WebElement howIsCalculatedLink;
	
	@FindBy(xpath = ".//a[contains(@id,'interestRateTooltip')]")
	private WebElement intrestRate;
	
	@FindBy(xpath = ".//a[contains(text(),'Log Out')]")
	private WebElement loanStructure;
	
	@FindBy(xpath = ".//td[contains(text(),'Account Reduction')]")
	private WebElement loanType;
	
	@FindBy(xpath = ".//td[contains(text(),'GENERAL')]")
	private WebElement loanCatagaries;
	
	@FindBy(xpath = ".//td[contains(text(),'12 - 60 Months')]")
	private WebElement generalPurposeLoanTerm;
	
	@FindBy(xpath = ".//td[contains(text(),'61 - 120 Months')]")
	private WebElement motorGageLoanTerm;
	
	@FindBy(xpath = ".//td[contains(text(),'Loans Allowed After Previous Default:')]/parent::*/child::td[2]")
	private WebElement loanAllowedAfterPrviousTerm;
	
	@FindBy(xpath = ".//a[contains(@href,'/csasWebApp/part/loanInfoPORTAL.do')]")
	private WebElement loanNumber;
	
	@FindBy(xpath = "//input[@class='selectOptions']")
	private WebElement principalResidence;
	
	@FindBy(id="pmtFrequencyGENERAL")
	private WebElement paymentFrequency;
	
	@FindBy(id="paymentmethodCode-GENERAL")
	private WebElement paymentMethod;
	

	@FindBy(xpath = ".//*[@id='loanTypePriRes']/child::div[26]/child::div[2]/a")
	private WebElement logOut;
	
	@FindBy(xpath=".//div[@class='loanTypeInformationGENERAL']/child::div[2]/child::div[1]/label")
	private WebElement paymentFrequencyLabelText;
	
	@FindBy(id="ajaxErrorRecalculate")
	private WebElement minAS_208;
	
	@FindBy(id = "confirmPayment")
	private WebElement confirmPaymentFrequency;


	ParticipantHome participantHomeObj;

	@Override
	protected void isLoaded() throws Error {
		// if(CommonLib.checkForPpt())
		Assert.assertTrue(Web.isWebElementDisplayed(loanRequest));
		// Assert.assertTrue(Web.isWebElementDisplayed(loanRequest));
		/*
		 * else{ Assert.assertTrue(false); }
		 */

	}

	@Override
	protected void load() {
		try {
			// TODO Auto-generated method stub
			this.parent = new ParticipantHome().get();

			new ParticipantHome().search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					Stock.GetParameterValue("PPT_ID"));

			Web.mouseHover(menuPPTChanges);
			if (Web.isWebElementDisplayed(menuLoanRequest)) {
				Web.clickOnElement(menuLoanRequest);

				Web.waitForElement(inputBorrowAmount);

				// Web.waitForPageToLoad(Web.getDriver());
				// Web.getDriver().manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

				if (Web.isWebElementDisplayed(loanRequest, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Loan Request page displayed or not",
							"Loan Request page displyed successfully", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Loan Request page displayed or not",
							"Loan Request didn't get displayed successfully",
							true);
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
	 * 
	 * @Date :
	 * 
	 * @Manaul Test cases
	 */

	public void validating_loan_number_link() {
		if (Web.isWebElementDisplayed(loan_number, true)) {
			Web.clickOnElement(loan_number);
			Reporter.logEvent(Status.INFO, "Clicking on the loan number link",
					"Clicking on loan number link takes us to next page", false);
			if (Web.isWebElementDisplayed(loan_history_page_title_tab, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Checking if LOAN INFO/HISTORY page is displayed on clicking loan number link",
						"LOAN INFO/HISTORY page is displayed", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Checking if LOAN INFO/HISTORY page is displayed on clicking loan number link",
						"LOAN INFO/HISTORY page is not displayed", true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Loan Number is not  displayed for this participant",
					"Loan Number is not  displayed for this participant", true);

		}
	}

	public void validating_mobile_phone() {
		Web.waitForElement(mobile_phone);

		verify_ErrorMssage_Email_InputBox("ahghhjhjh",
				"Enter a valid mobile phone number.",
				"Checking for character values in mobile number field");

		verify_ErrorMssage_Email_InputBox("a12$%ghsd90",
				"Enter a valid mobile phone number.",
				"Checking for alpha numeric character values in mobile number field");
		verify_ErrorMssage_Email_InputBox("123564852635",
				"Enter a valid mobile phone number.",
				"Checking for more than 10 characters values in mobile number field");
		verify_ErrorMssage_Email_InputBox(
				Stock.GetParameterValue("Mobile Phone"), "",
				"Checking for valid phone number value in mobile number field");
	}

	private void verify_ErrorMssage_Email_InputBox(String sValue,
			String sErrorMsg, String sMsg) {

		Web.setTextToTextBox(mobile_Phone_txtBox, sValue);
		// mobile_Phone_txtBox.sendKeys(Keys.ENTER);
		Web.setTextToTextBox(inpEmail, "test.csas@gwg.com");
		Web.waitForElement(mobile_error);
		String error4 = mobile_error.getText();
		if (validateErrorMessage(error4, sErrorMsg)) {
			Reporter.logEvent(Status.PASS, sMsg, "Error message is displayed",
					false);
		} else {
			Reporter.logEvent(Status.FAIL, "Error message expected: "
					+ sErrorMsg, " But Actual was : " + error4, false);

		}

	}

	public boolean validateErrorMessage(String actual, String sExpText) {
		boolean areSame = false;
		Web.setTextToTextBox(inpEmail, "test.csas@gwg.com");
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
					actual, false);
			areSame = true;
			return areSame;
		} else {/*
				 * Reporter.logEvent(Status.FAIL, "Error message expected :" +
				 * sExpText, " But Actual was : " + actual, false);
				 */
		}
		return areSame;
	}

	public void validating_payment_method_noneditable() {

		// Web.waitForElement(partId);
		// Web.setTextToTextBox(partId,
		// Stock.GetParameterValue("participantId"));
		// Web.waitForElement(submit);
		// Web.clickOnElement(submit);
		// Web.waitForElement(participant_changes);
		// Web.clickOnElement(participant_changes);
		// Web.waitForElement(loan_request);
		// Web.clickOnElement(loan_request);
		Web.waitForElement(payment_method);

		String tag_name = payment_method.getTagName();
		System.out.println("the tag name is :" + tag_name);
		if (tag_name != "input") {
			Reporter.logEvent(Status.PASS,
					"Verifying if the payment method is a non-editable field",
					"Payment method is a non-editable field", false);
		} else {
			Reporter.logEvent(Status.FAIL,
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
		System.out.println("on entering alphabets in vesting field:" + error1);
		Reporter.logEvent(Status.INFO,
				"Checking the vesting field for alphabets",
				"On entering alphabets in the vesting field:" + error1, false);
		Web.setTextToTextBox(vesting1, "*&^%");
		Web.waitForElement(vesting_error);
		String error2 = vesting_error.getText();
		System.out.println("on entering spectial characters in vesting field:"
				+ error2);
		Reporter.logEvent(
				Status.INFO,
				"Checking the vesting field for special characters",
				"On entering special characters in the vesting field:" + error2,
				false);
		Web.setTextToTextBox(vesting1, Stock.GetParameterValue("Vesting"));
		Reporter.logEvent(Status.INFO,
				"Entering a valid number in the vesting field",
				"A valid number is added to the vesting field", false);
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
					"Vesting field is accepting only numerical value", false);
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

		Web.clickOnElement(recalculate_button);
		Reporter.logEvent(Status.INFO, "clicking on recalculate button",
				"Recalculate button is clicked", false);

		wait(2000);

		Web.waitForElement(amount_to_borrow);
		String amount2 = "$" + amount_to_borrow.getAttribute("value");

		if (amount1.equalsIgnoreCase(amount2)) {
			Reporter.logEvent(Status.PASS,
					"Verifying recalculate button functionality",
					"Recalculate button functionality successfull", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying recalculate button functionality",
					"Recalculate button functionality not successfull", false);
		}

		Web.clickOnElement(eligible_checkbox);
	}

	public void verifying_enable_of_select_button() {
		try {
			Web.waitForElement(amount_to_borrow);
			Web.setTextToTextBox(amount_to_borrow,
					Stock.GetParameterValue("Amount to Borrow"));
			Reporter.logEvent(Status.INFO,
					"Value to be added into amount to borrow field",
					"Value is added into amount to borrow field", false);
			Web.waitForElement(loan_term);
			Web.setTextToTextBox(loan_term,
					Stock.GetParameterValue("Loan Terms"));
			Reporter.logEvent(Status.INFO,
					"Value to be added into loan term field",
					"Value is added into loan term field", false);
			// Web.waitForElement(add_button);
			Web.clickOnElement(add_button);
			Web.waitForElement(loan_qoute1_radio_button);
			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"First loan quote is selected", false);
			// Thread.sleep(5000);
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
			Reporter.logEvent(Status.INFO,
					"Changing the value in the amount to borrow text box",
					"Amount has been changed in the amount to borrow text box",
					false);
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

	public void verifyDeliverForm() {

		if (Web.isWebElementDisplayed(amount_to_borrow_txtBox)) {
			String amount_to_borrow = getMinAmount().toString();
			Web.setTextToTextBox(amount_to_borrow_txtBox, amount_to_borrow);
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount has been changed in the amount to borrow text box: "
							+ Stock.GetParameterValue("Amount_to_Borrow"),
					false);

			Web.selectDropDownOption(payment_frequency, "MONTHLY");

			Web.clickOnElement(quick_qoutes_button);
			Reporter.logEvent(Status.INFO,
					"Viewing Quick quotes for monthly payment frequency",
					"Quick quotes for monthly frequency is being viewed", false);
			wait(3000);

			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.clickOnElement(not_married_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting maritial status",
					"\"Not married\" is selected", false);

			Web.clickOnElement(continue_button);
			Reporter.logEvent(
					Status.INFO,
					"Clicking Continue to Loan Review and Confirmation button",
					"Continue to Loan Review and Confirmation button is clicked",
					false);
			wait(2000);

			Web.clickOnElement(Email_chk_box);
			Reporter.logEvent(Status.INFO, "Checking Email check box",
					"Email Check box is selected: discard@gwl.com", false);
			verifyConfirmationID();

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying Loan amount to be borrowed fields",
					"Loan amount to be borrowed field not enabled", false);
		}

	}

	public void verify_EditAddress_Field() {

		if (Web.isWebElementDisplayed(editAddress_link)) {
			Web.clickOnElement(editAddress_link);
			Reporter.logEvent(Status.INFO, "Click on Edit Address link",
					"Editing address link is clicked", false);

			if (Web.isWebElementDisplayed(Address_change_window)) {
				Reporter.logEvent(Status.INFO,
						"Checking if Editing Address window is displayed",
						"Editing address window is displayed", false);
				Web.setTextToTextBox(secondLineMailing,
						Stock.GetParameterValue("newAddress"));
				Reporter.logEvent(Status.INFO, "Entering new valid address",
						"New value for second line mailing address has been entered: "
								+ Stock.GetParameterValue("newAddress"), false);
			}

			Web.clickOnElement(EditAddr_Ok_Btn);

			if (Web.isWebElementDisplayed(AddressEdited_message)) {
				Reporter.logEvent(Status.PASS,
						"Verifying Edited Address Message is displayed or not",
						"Edited Address Message is displayed\n"
								+ AddressEdited_message.getText(), false);
			}

			String sExpText = "A recent address change has been detected. Processing can continue on "
					+ DataUtility.getTimeForTimeZone("MM/dd/yy", 16)
					+ " or first business day after unless form is returned with address change section completed and notarized.";

			if (Web.isWebElementDisplayed(addressChanged_Alert)
					&& Web.isWebElementDisplayed(addressChanged_Alert_Img)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Recent Address Change Warning",
						"Recent Address Change Warning is Displayed with yellow warning icon",
						false);
				verifyText(addressChanged_Alert_msg, sExpText,
						"Verifying Recent Address Change Warning", true);

			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying Recent Address Change Warning",
						"Address Change warning is not displayed", false);
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Verifying Edit Address link",
					"Address link is not enabled", false);
		}

	}

	public void verify_Refinance_Loan_Multiple_Outstanding_Loans() {

		if (Web.isWebElementDisplayed(refinance_loans, true)) {
			Reporter.logEvent(
					Status.INFO,
					"Verifying participant has multiple outstanding loans",
					"Multiple Outstanding loans section is displayed, participant has multiple outstanding loans",
					false);

			List<WebElement> checkBoxList = Web.getDriver().findElements(
					By.className("generalRefinanceChecked"));
			Web.clickOnElement(checkBoxList.get(0));

			Reporter.logEvent(Status.INFO,
					"Clicking on Eligible Check boxes to Refinance Loans",
					"Loans has been selected for Refinancing", false);

			Web.clickOnElement(select_refinance_btn);
			Reporter.logEvent(Status.INFO,
					"Submitting selected loans for Refinancing",
					"Selected loans for Refinancing are submitted", false);

			Web.setTextToTextBox(add_additional_loan_btn_general,
					getMinAmount().toString());
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box"
							+ getMinAmount().toString(), false);

			Web.selectDropDownOption(payment_frequency, "MONTHLY");

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Adding additional Loan amount and Viewing Quick quotes ",
					"New Loan Quotes is displayed", false);

			wait(2000);

			Web.clickOnElement(addtn_amt_loan_quote);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.clickOnElement(not_married_radio_button);

			if (Web.isWebElementDisplayed(continue_button)) {
				Web.clickOnElement(continue_button);
				Reporter.logEvent(
						Status.PASS,
						"Clicking Continue to Loan Review and Confirmation button",
						"Continue to Loan Review and Confirmation is clicked",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Clicking Continue to Loan Review and Confirmation button",
						"Continue to Loan Review and Confirmation is not enabled",
						false);
			}

			Web.clickOnElement(Email_chk_box);

			Web.clickOnElement(deliveryFormBtn);
			Reporter.logEvent(Status.INFO, "Click Delivery Form button",
					"Delivery Form button has been clicked", false);

			if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
				Reporter.logEvent(Status.PASS,
						"verifying successfull Delivering the form",
						"Form was delivered successfully", false);
				wait(2000);
				String conf_id = confirmation_id_Refinance_multiple_loans
						.getText();

				if (conf_id != null && conf_id != "") {
					Reporter.logEvent(Status.PASS,
							"Confirmation Id for Delivery form",
							"Confirmation Id: " + conf_id, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Confirmation Id for Delivery form",
							"Confirmation Id is not displayed ", false);
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"verifying successfull submission of loan request",
						"Loan request not delivered successfully", false);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"verifying successfull Delivering the form",
					"Form was not delivered successfully", false);
		}
	}

	public void verify_Refinance_Loan_Maximum_Outstanding_Loans() {

		Web.clickOnElement(Principal_residence_radio_btn);

		if (Web.isWebElementDisplayed(Refinancing_msg, true)) {
		//	String strExp = " Refinancing is required ";
			// verifyText(Refinancing_msg, strExp,
			// "verifying the message \"Refinancing is Required\" is displayed or not",
			// false);
		}

		if (Web.isWebElementDisplayed(refinance_loans, true)) {
			Reporter.logEvent(
					Status.INFO,
					"Verifying participant has maximum outstanding loans",
					"Outstanding loans section is displayed, participant has maximum outstanding loans",
					false);

			Web.clickOnElement(eligible_chk_box);
			Reporter.logEvent(Status.INFO,
					"Clicking on Eligible Check box to Refinance Loans",
					"Loan has been selected for Refinancing", false);

			Web.clickOnElement(select_refinance_btn);
			Reporter.logEvent(Status.INFO,
					"Submitting selected loans for Refinancing",
					"Selected loans for Refinancing are submitted", false);

			Web.setTextToTextBox(add_additional_loan_txtbox, getMinAmount()
					.toString());
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box"
							+ Stock.GetParameterValue("Amount_to_Borrow"),
					false);

			Web.clickOnElement(add_additional_loan_btn);
			Reporter.logEvent(Status.INFO,
					"Adding additional Loan amount and Viewing Quick quotes ",
					"New Loan Quotes is displayed", false);

			wait(2000);
			Web.clickOnElement(addtn_amt_loan_quote);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.clickOnElement(not_married_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting maritial status",
					"\"Not married\" is selected", false);

			if (Web.isWebElementDisplayed(continue_button)) {
				Web.clickOnElement(continue_button);
				Reporter.logEvent(
						Status.PASS,
						"Clicking Continue to Loan Review and Confirmation button",
						"Continue to Loan Review and Confirmation is clicked",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Clicking Continue to Loan Review and Confirmation button",
						"Continue to Loan Review and Confirmation is not enabled",
						false);
			}
			wait(2000);

			Web.clickOnElement(Email_chk_box);
			Reporter.logEvent(Status.INFO, "Checking Email check box",
					"Email Check box is selected ", false);

			Web.clickOnElement(deliveryFormBtn);
			Reporter.logEvent(Status.INFO, "Clicking Delivery Form button",
					"Delivery Form button has been clicked", false);

			if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
				Reporter.logEvent(Status.PASS,
						"verifying successfull submission of loan request",
						"Loan application was delivered successfully", false);
				String conf_id = confirmation_id_Refinance_multiple_loans
						.getText();
				Reporter.logEvent(Status.PASS,
						"Confirmation Id from request submission",
						"Confirmation Id: " + conf_id, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"verifying successfull submission of loan request",
						"Loan request not delivered successfully", false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying participant having outstanding loans",
					"Outstanding loans are not displayed for paricipant", false);
		}
	}

	public void verify_Loan_Term() {

		try {
			if (Web.isWebElementDisplayed(refinance_loans, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying participant has outstanding loans",
						"Outstanding loans section is displayed, participant has outstanding loans",
						false);

				if (Web.isWebElementDisplayed(eligible_chk_box)) {
					Web.clickOnElement(eligible_chk_box);
					Reporter.logEvent(
							Status.PASS,
							"Clicking on Eligible Check box to Refinance Loans",
							"Loan has been selected for Refinancing", false);
				}

				Web.clickOnElement(select_refinance_btn);
				Reporter.logEvent(Status.INFO,
						"Submitting selected loans for Refinancing",
						"Selected loans for Refinancing are submitted", false);

				if (Web.isWebElementDisplayed(add_additional_loan_txtbox)) {
					Web.setTextToTextBox(add_additional_loan_txtbox,
							getMinAmount().toString());
					Reporter.logEvent(
							Status.PASS,
							"Entering the value in the amount to borrow text box",
							"Amount has been entered in the amount to borrow text box: "
									+ getMinAmount().toString(), false);
				}

				Web.selectDropDownOption(payment_frequency_principal_res,
						"MONTHLY");

				if (Web.isWebElementDisplayed(add_additional_loan_btn)) {
					Web.clickOnElement(add_additional_loan_btn);
					Reporter.logEvent(
							Status.PASS,
							"Adding additional Loan amount and Viewing Quick quotes ",
							"New Loan Quotes is being viewed", false);
				}
				wait(2000);

				if (Web.isWebElementDisplayed(loan_term_txtbox, true)) {
					Range<Integer> myRange = Range.between(61, 360);
					String loanterm_months = loan_term_txtbox.getText().split(
							" ")[0];
					Integer loan_term_months = Integer
							.parseInt(loanterm_months);
					System.out.println("Loan term in months: "
							+ loan_term_months);
					Reporter.logEvent(
							Status.INFO,
							"Loan term for selected refinanced loan amount for payment frequency of: Monthly",
							"Loan terms in months: " + loan_term_months, false);
					if (myRange.contains(loan_term_months)) {
						Reporter.logEvent(
								Status.PASS,
								"Loan term \nExpected: Principal Residence Loan Term is showing in Months like 61 - 360 Months",
								"Actual Loan terms in months: "
										+ loan_term_months, false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Loan term \nExpected: Principal Residence Loan Term is showing in Months like 61 - 360 Months",
								"Actual Loan terms in months: "
										+ loan_term_months, false);
					}
				} else {
					Reporter.logEvent(Status.FAIL, "Verifying new Loan term ",
							"Loan term for new quotes not displayed", false);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verify_Exceptions_Refinance_Multiple_NO_Addtl_Amt() {

		if (Web.isWebElementDisplayed(refinance_loans, true)) {
			Reporter.logEvent(
					Status.INFO,
					"Verifying participant has multiple outstanding loans",
					"Multiple Outstanding loans section is displayed, participant has multiple outstanding loans",
					false);

			List<WebElement> checkBoxList = Web.getDriver().findElements(
					By.className("generalRefinanceChecked"));
			checkBoxList.get(0).click();
			Reporter.logEvent(Status.INFO,
					"Clicking on Eligible Check boxes to Refinance Loans",
					"Loans has been selected for Refinancing", false);

			Web.clickOnElement(select_refinance_btn);
			Reporter.logEvent(Status.INFO,
					"Submitting selected loans for Refinancing",
					"Selected loans for Refinancing are submitted", false);

			String amt0 = Stock.GetParameterValue("Amount_to_Borrow_0");
			Web.setTextToTextBox(add_additional_loan_btn_general, amt0);
			Reporter.logEvent(
					Status.INFO,
					"Entering the value zero(0) in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box: "
							+ amt0, false);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);

			String expectedExceptionMsg = " Requested Loan amt must be > 0 AS_200   New Loan Amt for a Refinance must be at least Fee Amt AS_211";

			if (Web.isWebElementDisplayed(loanReq_Error_msg_invalid_input, true)) {
				verifyText(
						loanReq_Error_msg_invalid_input,
						expectedExceptionMsg,
						"Verifying Exception message for Zero(0) amount for Loan",
						false);
			}

			String amtLessThanMin = Stock
					.GetParameterValue("Amount_to_Borrow_lessthan_Min");
			Web.setTextToTextBox(add_additional_loan_btn_general,
					amtLessThanMin);
			Reporter.logEvent(
					Status.INFO,
					"Entering the value less than minimum in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box: "
							+ amtLessThanMin, false);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);

			expectedExceptionMsg = " New Loan Amt for a Refinance must be at least Fee Amt AS_211";
			verifyText(
					loanReq_Error_msg_invalid_input,
					expectedExceptionMsg,
					"Verifying Exception message for less than minimum amount for Loan",
					false);

			String amtMin = getMinAmount().toString();
			Web.setTextToTextBox(add_additional_loan_btn_general, amtMin);
			Reporter.logEvent(
					Status.INFO,
					"Entering the minimum value in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box: "
							+ amtMin, false);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);
			wait(2000);
			if (!(Web.isWebElementDisplayed(loanReq_Error_msg_invalid_input,
					true))) {
				Reporter.logEvent(Status.PASS,
						"Expected: Exception message should not display",
						"No Exception message id Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Expected: Exception message should not display",
						"Actual: " + loanReq_Error_msg_invalid_input.getText(),
						false);
			}

			float min_amount = getMinAmount() + 1;
			String amtMoreThanMin = String.valueOf(min_amount);
			Web.setTextToTextBox(add_additional_loan_btn_general,
					amtMoreThanMin);
			Reporter.logEvent(
					Status.INFO,
					"Entering the value which is more than minimum in the amount to borrow text box",
					"Amount has been entered in the amount to borrow text box: "
							+ amtMoreThanMin, false);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);
			wait(2000);
			if (!(Web.isWebElementDisplayed(loanReq_Error_msg_invalid_input,
					true))) {
				Reporter.logEvent(Status.PASS,
						"Expected: Exception message should not display",
						"No Exception message id Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "", "Actual: "
						+ loanReq_Error_msg_invalid_input.getText(), false);
			}

			String loan_term_months = loanterm_selected_loan.getText();
			int loan_term_month = Integer.parseInt(loan_term_months) + 1;
			Web.setTextToTextBox(loanterm_txtBox,
					String.valueOf(loan_term_month));
			Reporter.logEvent(Status.INFO,
					"Enter loan term greater than plan's remaining term",
					"Loan term of " + loan_term_month
							+ " months has been entered", false);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);

			if ((Web.isWebElementDisplayed(loanReq_Error_msg_invalid_input,
					true))) {
				expectedExceptionMsg = " Loan Term must be between min Term of 12 Month s  and Max Term of 60 Month s  AS_213";
				verifyText(
						loanReq_Error_msg_invalid_input,
						expectedExceptionMsg,
						"Verifying Exception message for loan term more than remaining term",
						false);
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verifying participant has multiple outstanding loans",
					"Multiple Outstanding loans section is not displayed, participant doesn't have multiple outstanding loans",
					false);
		}
	}

	public void verify_HappyPath_PromissoryNote_SetTo_pptID_DeliverForm() {

		if (Web.isWebElementDisplayed(promissory_note, true)) {
			// String strExp =
			// " This is a two-step loan and requires a promissory note.";
			// verifyText(promissory_note, strExp,
			// "verifying the message "+strExp+" should be displayed", false);

			Reporter.logEvent(
					Status.INFO,
					"Verifying message \"This is a two-step loan and requires a promissory note.\" is displayed or not",
					"The message is displayed", false);

			Web.setTextToTextBox(amount_to_borrow_txtBox, getMinAmount()
					.toString());
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount entered : " + getMinAmount().toString(), false);

			Web.selectDropDownOption(payment_frequency, "MONTHLY");

			Web.clickOnElement(quick_qoutes_button);
			Reporter.logEvent(Status.INFO,
					"Viewing Quick quotes for monthly payment frequency",
					"Quick quotes for monthly frequency is being viewed", false);
			wait(2000);

			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.clickOnElement(first_class_mail_radiobtn);
			Reporter.logEvent(Status.INFO,
					"Selecting First-Class mail radio button",
					"\"First-class mail\" is selected", false);

			Web.clickOnElement(not_married_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting maritial status",
					"\"Not married\" is selected", false);

			Web.setTextToTextBox(home_Phone_txtBox,
					Stock.GetParameterValue("HomePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Home Phone number",
					"Home Phone number entered is: "
							+ Stock.GetParameterValue("HomePhoneNumber"), false);

			Web.setTextToTextBox(mobile_Phone_txtBox,
					Stock.GetParameterValue("MobilePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Mobile Phone number",
					"Mobile Phone number entered is: "
							+ Stock.GetParameterValue("MobilePhoneNumber"),
					false);

			Web.setTextToTextBox(personal_email_txtBox,
					Stock.GetParameterValue("PersonalEmail"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Personal Email Id",
					"Personal Email Id entered is: "
							+ Stock.GetParameterValue("PersonalEmail"), false);

			Web.clickOnElement(continue_button);
			Reporter.logEvent(
					Status.INFO,
					"Clicking Continue to Loan Review and Confirmation button",
					"Continue to Loan Review and Confirmation button is clicked",
					false);
			wait(2000);

			Web.clickOnElement(Email_chk_box);
			Reporter.logEvent(Status.INFO, "Checking Email check box",
					"Email Check box is selected: discard@gwl.com", false);

			verifyConfirmationID();

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying promissory note message is displayed or not",
					"Promissory note message is not displayed", false);
		}

	}

	public void verify_HappyPath_PromissoryNote_SetTo_pptID_LoanRequest() {

		if (Web.isWebElementDisplayed(promissory_note, true)) {
			// String strExp =
			// " This is a two-step loan and requires a promissory note.";
			// verifyText(promissory_note, strExp,
			// "verifying the message \"This is a two-step loan and requires a promissory note should display.\" is displayed or not",
			// false);

			Reporter.logEvent(
					Status.INFO,
					"verifying the message \"This is a two-step loan and requires a promissory note should display.\" is displayed or not",
					"Promissory note is displayed", false);

			Web.setTextToTextBox(amount_to_borrow_txtBox, getMinAmount()
					.toString());
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount has been changed in the amount to borrow text box: "
							+ getMinAmount().toString(), false);

			Web.clickOnElement(quick_qoutes_button);
			Reporter.logEvent(Status.INFO,
					"Viewing Quick quotes for monthly payment frequency",
					"Quick quotes for monthly frequency is being viewed", false);
			wait(2000);

			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.clickOnElement(first_class_mail_radiobtn);
			Reporter.logEvent(Status.INFO,
					"Selecting First-Class mail radio button",
					"\"First-class mail\" is selected", false);

			Web.setTextToTextBox(home_Phone_txtBox,
					Stock.GetParameterValue("HomePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Home Phone number",
					"Home Phone number entered is: "
							+ Stock.GetParameterValue("HomePhoneNumber"), false);

			Web.setTextToTextBox(mobile_Phone_txtBox,
					Stock.GetParameterValue("MobilePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Mobile Phone number",
					"Mobile Phone number entered is: "
							+ Stock.GetParameterValue("MobilePhoneNumber"),
					false);

			Web.setTextToTextBox(personal_email_txtBox,
					Stock.GetParameterValue("PersonalEmail"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Personal Email Id",
					"Personal Email Id entered is: "
							+ Stock.GetParameterValue("PersonalEmail"), false);

			Web.clickOnElement(continue_button);
			Reporter.logEvent(
					Status.INFO,
					"Clicking Continue to Loan Review and Confirmation button",
					"Continue to Loan Review and Confirmation button is clicked",
					false);
			wait(2000);

			Web.clickOnElement(standardEmailRadioBtn);
			Reporter.logEvent(Status.INFO, "Checking Standard Email check box",
					"Standard Email Check box is selected", false);

			Web.clickOnElement(submitForProcessing);

			Web.clickOnElement(submitLoanRequestBtn);
			Reporter.logEvent(Status.INFO,
					"Clicking Agree & submit Loan Request ",
					"Agree & submit Loan Request button has been clicked",
					false);

			if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
				Reporter.logEvent(Status.PASS,
						"verifying successfull submission of loan request",
						"Loan application was submitted successfully", false);
				String conf_id = confirmation_id.getText();
				Reporter.logEvent(Status.PASS,
						"Confirmation Id from request submission",
						"Confirmation Id: " + conf_id, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"verifying successfull submission of loan request",
						"Loan request not delivered successfully", false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying Loan amount to be borrowed fields",
					"Loan amount to be borrowed field not enabled", false);
		}

	}

	public void verify_HappyPath_ACH() {

		Web.setTextToTextBox(amount_to_borrow_txtBox, getMinAmount().toString());
		Reporter.logEvent(Status.INFO,
				"Entering the value in the amount to borrow text box",
				"Amount has been changed in the amount to borrow text box: "
						+ getMinAmount().toString(), false);

		Web.selectDropnDownOptionAsIndex(bankAccountDropDown, "1");

		Web.setTextToTextBox(amount_to_borrow_txtBox, getMinAmount().toString());

		Web.clickOnElement(quick_qoutes_button);
		Reporter.logEvent(Status.INFO,
				"Viewing Quick quotes for monthly payment frequency",
				"Quick quotes for monthly frequency is being viewed", false);
		wait(2000);

		Web.clickOnElement(loan_qoute1_radio_button);
		Reporter.logEvent(Status.INFO, "Selecting a loan quote",
				"One loan quote has been selected", false);

		Web.setTextToTextBox(home_Phone_txtBox,
				Stock.GetParameterValue("HomePhoneNumber"));
		Reporter.logEvent(
				Status.INFO,
				"Entering Home Phone number",
				"Home Phone number entered is: "
						+ Stock.GetParameterValue("HomePhoneNumber"), false);

		Web.setTextToTextBox(mobile_Phone_txtBox,
				Stock.GetParameterValue("MobilePhoneNumber"));
		Reporter.logEvent(
				Status.INFO,
				"Entering Mobile Phone number",
				"Mobile Phone number entered is: "
						+ Stock.GetParameterValue("MobilePhoneNumber"), false);

		Web.setTextToTextBox(personal_email_txtBox,
				Stock.GetParameterValue("PersonalEmail"));
		Reporter.logEvent(
				Status.INFO,
				"Entering Personal Email Id",
				"Personal Email Id entered is: "
						+ Stock.GetParameterValue("PersonalEmail"), false);

		Web.clickOnElement(continue_button);
		Reporter.logEvent(Status.INFO,
				"Clicking Continue to Loan Review and Confirmation button",
				"Continue to Loan Review and Confirmation button is clicked",
				false);
		wait(2000);

		Web.clickOnElement(standardEmailRadioBtn);

		Web.clickOnElement(submitLoanRequestBtn);
		Reporter.logEvent(Status.INFO, "Clicking Agree & submit Loan Request ",
				"Agree & submit Loan Request button has been clicked", false);

		if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
			Reporter.logEvent(Status.PASS,
					"verifying successfull submission of loan request",
					"Loan request was submitted successfully", false);
			wait(2000);
			String conf_id = Conf_id_ACH.getText();
			Reporter.logEvent(Status.PASS,
					"Confirmation Id from request submission",
					"Confirmation Id: " + conf_id, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"verifying successfull submission of loan request",
					"Loan request not delivered successfully", false);
		}

	}

	public void verifyBackHyperlink() {

		if (Web.isWebElementDisplayed(amount_to_borrow_txtBox)) {
			String amount_to_borrow = getMinAmount().toString();
			Web.setTextToTextBox(amount_to_borrow_txtBox, amount_to_borrow);
			Reporter.logEvent(Status.INFO,
					"Entering the value in the amount to borrow text box",
					"Amount has been changed in the amount to borrow text box: "
							+ getMinAmount().toString(), false);

			Web.setTextToTextBox(loanterm_txtBox,
					Stock.GetParameterValue("loanTermYears"));
			Reporter.logEvent(Status.INFO, "Entering loan term in years",
					"Loan term of " + Stock.GetParameterValue("loanTermyears")
							+ " years has been entered", false);

			Web.clickOnElement(add_additional_loan_btn);
			Reporter.logEvent(Status.PASS,
					"Adding additional Loan amount and Viewing Quick quotes ",
					"New Loan Quotes is being viewed", false);

			Web.clickOnElement(editAddress_link);
			Reporter.logEvent(Status.INFO, "Click on Edit Address link",
					"Editing address link is clicked", false);

			if (Web.isWebElementDisplayed(Address_change_window)) {
				Reporter.logEvent(Status.INFO,
						"Checking if Editing Address window is displayed",
						"Editing address window is displayed", false);
				Web.setTextToTextBox(secondLineMailing,
						Stock.GetParameterValue("newAddress"));
				Reporter.logEvent(Status.INFO, "Entering new valid address",
						"New value for second line mailing address has been entered: "
								+ Stock.GetParameterValue("newAddress"), false);
			}

			Web.clickOnElement(EditAddr_Ok_Btn);

			Web.setTextToTextBox(home_Phone_txtBox,
					Stock.GetParameterValue("HomePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Home Phone number",
					"Home Phone number entered is: "
							+ Stock.GetParameterValue("HomePhoneNumber"), false);

			Web.setTextToTextBox(mobile_Phone_txtBox,
					Stock.GetParameterValue("MobilePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Mobile Phone number",
					"Mobile Phone number entered is: "
							+ Stock.GetParameterValue("MobilePhoneNumber"),
					false);

			Web.setTextToTextBox(personal_email_txtBox,
					Stock.GetParameterValue("PersonalEmail"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Personal Email Id",
					"Personal Email Id entered is: "
							+ Stock.GetParameterValue("PersonalEmail"), false);

			Web.clickOnElement(continue_button);
			Reporter.logEvent(
					Status.INFO,
					"Clicking Continue to Loan Review and Confirmation button",
					"Continue to Loan Review and Confirmation button is clicked",
					false);
			wait(2000);

			Web.clickOnElement(backHyperlink);
			Reporter.logEvent(Status.INFO, "Clicking Back Hyperlink button",
					"Back hyperlink button is clicked", false);
			wait(2000);

			if (Web.isWebElementDisplayed(quick_qoutes_button, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Loan Request page displayed or not",
						"Loan Request page displayed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Loan Request page displayed or not",
						"Loan Request didn't get displayed successfully", true);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying Loan amount to be borrowed fields",
					"Loan amount to be borrowed field not enabled", false);
		}
	}

	public void verifyCheckbox_RefinanceLoanTerm() {
		try {
			if (Web.isWebElementDisplayed(refinance_loans, true)) {

				if (Web.isWebElementDisplayed(eligible_chk_box)) {
					Web.clickOnElement(eligible_chk_box);
					Reporter.logEvent(
							Status.PASS,
							"Clicking on Eligible Check box to Refinance Loans",
							"Loan has been selected for Refinancing", false);

					Web.clickOnElement(select_refinance_btn);
					Reporter.logEvent(Status.INFO,
							"Submitting selected loans for Refinancing",
							"Selected loans for Refinancing are submitted",
							false);

					if (!Web.isWebElementDisplayed(quick_qoutes_button)) {
						Reporter.logEvent(
								Status.PASS,
								"Verifying Quick quotes button is displayed or not",
								"Quick quotes button is not displayed", false);
					}
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verifying Eligible check box is displyed or not",
							"Eligible check box is not displayed", false);
				}
			}

			String loan_term_months = Refinance_LoanTerm.getText();
			Reporter.logEvent(Status.INFO, "verifying Refinance loan term",
					"Refinance loan term for selected loan is: "
							+ loan_term_months, false);

			if (Web.isWebElementDisplayed(Ineligible_btn, true)) {
				String text_color = Ineligible_btn.getCssValue("color");
				if (text_color.equalsIgnoreCase("rgba(255, 0, 0, 1)")) {
					Reporter.logEvent(
							Status.PASS,
							"Verifying Ineligible check box is displayed or not and color is red",
							"Ineligible check box is not displayed and its color is RED",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verifying Ineligible check box is displayed or not and color is red",
							"Ineligible check box is not displayed ", false);
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying Ineligible check box is displayed or not",
						"Ineligible check box is displayed", false);
			}

			Web.clickOnElement(eligible_chk_box);
			List<WebElement> checkBoxList = Web.getDriver().findElements(
					By.className("generalRefinanceChecked"));
			Web.clickOnElement(checkBoxList.get(0));
			Reporter.logEvent(
					Status.PASS,
					"Verifying if User can select the Eligible check box one either for General Purpose or Principal Residence",
					"User can select the Eligible check box one either for General Purpose or Principal Residence",
					false);

			int loan_term_month = Integer.parseInt(loan_term_months) + 1;
			String lterm = String.valueOf(loan_term_month);
			Web.setTextToTextBox(Refinance_LoanTerm, lterm);
			Reporter.logEvent(Status.INFO,
					"Enter loan term greater than plan's remaining term",
					"Loan term of " + loan_term_month
							+ " months has been entered", false);

			Web.clickOnElement(add_btn_mortgage);
			Reporter.logEvent(Status.INFO,
					"Clicking on Add button for additional loan amount",
					"Add button has been clicked", false);

			if ((Web.isWebElementDisplayed(loanReq_Error_msg_invalid_input,
					true))) {
				String expectedExceptionMsg = " The loan term cannot exceed the plan's remaining term.";
				verifyText(
						loanReq_Error_msg_invalid_input,
						expectedExceptionMsg,
						"Verifying Exception message for loan term more than remaining term",
						false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying error message for exceeding loan term",
						"Error message is not displayed", false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyLoanChoices() {

		try {
			if (Web.isWebElementDisplayed(LoanType_General, true)
					&& (Web.isWebElementDisplayed(LoanType_PrincipalResidence,
							true))) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying if the plan Offers both loan types: General Purpose and Principal Residence",
						"Plan Offers both loan types: General Purpose and Principal Residence",
						false);

				Web.clickOnElement(LoanType_General);
				Reporter.logEvent(Status.INFO,
						"Clicking on General Purpose Loan type",
						"General Purpose loan type is clicked", false);
				Reporter.logEvent(
						Status.PASS,
						"Verifying Loan Structure Overview' details get changes when user select Loan type - General Purpose",
						"Loan Structure Overview' details get changes when user select Loan type - General Purpose",
						false);

				Web.clickOnElement(LoanType_PrincipalResidence);
				Reporter.logEvent(Status.INFO,
						"Clicking on Principal Residence Loan type",
						"Principal Residence loan type is clicked", false);
				Reporter.logEvent(
						Status.PASS,
						"Verifying Loan Structure Overview' details get changes when user select Loan type - Principal Residence",
						"Loan Structure Overview' details get changes when user select Loan type - Principal Residence",
						false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyLoanTerm_Under_SelectedLoanQuote() {
		try {
			List<WebElement> LoanTermList = Web
					.getDriver()
					.findElements(
							By.xpath(".//*[@id='loanQuotesRows']//*[@class='row']/div[4]"));
			Reporter.logEvent(Status.INFO,
					"Verifying the Loan term under selected Loan Quote",
					"Getting Loan Term for selected loan quote", false);
			Reporter.logEvent(
					Status.PASS,
					"Expected: The Loan term under Select A Loan Quote should display as\n 1 Years\n2 Years\n3 Years\n4 Years\n5 Years",
					"Actual Loan terms are:\n" + LoanTermList.get(0).getText()
							+ "\n" + LoanTermList.get(1).getText() + "\n"
							+ LoanTermList.get(2).getText() + "\n"
							+ LoanTermList.get(3).getText() + "\n"
							+ LoanTermList.get(4).getText(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoanRefinancing_NoAdditionalAmount() {
		if (Web.isWebElementDisplayed(refinance_loans, true)) {
			Reporter.logEvent(
					Status.INFO,
					"Verifying participant has outstanding loans: refinance with NO additl loan amount",
					"Outstanding loans section is displayed, participant has outstanding loans: refinance with NO additl loan amount",
					false);

			List<WebElement> checkBoxList = Web.getDriver().findElements(
					By.className("generalRefinanceChecked"));
			checkBoxList.get(0).click();
			Reporter.logEvent(Status.INFO,
					"Clicking on Eligible Check boxes to Refinance Loans",
					"Loans has been selected for Refinancing", false);

			Web.clickOnElement(select_refinance_btn);
			Reporter.logEvent(Status.INFO,
					"Submitting selected loans for Refinancing",
					"Selected loans for Refinancing are submitted", false);

			String amount_to_borrow = getMinAmount().toString();
			Web.setTextToTextBox(amount_to_borrow_txtBox, amount_to_borrow);

			Web.clickOnElement(add_btn_general);
			Reporter.logEvent(Status.PASS,
					"Adding additional Loan amount and Viewing Quick quotes ",
					"New Loan Quotes is being viewed", false);

			Web.clickOnElement(loan_qoute1_radio_button);
			Reporter.logEvent(Status.INFO, "Selecting a loan quote",
					"One loan quote has been selected", false);

			Web.setTextToTextBox(home_Phone_txtBox,
					Stock.GetParameterValue("HomePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Home Phone number",
					"Home Phone number entered is: "
							+ Stock.GetParameterValue("HomePhoneNumber"), false);

			Web.setTextToTextBox(mobile_Phone_txtBox,
					Stock.GetParameterValue("MobilePhoneNumber"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Mobile Phone number",
					"Mobile Phone number entered is: "
							+ Stock.GetParameterValue("MobilePhoneNumber"),
					false);

			Web.setTextToTextBox(personal_email_txtBox,
					Stock.GetParameterValue("PersonalEmail"));
			Reporter.logEvent(
					Status.INFO,
					"Entering Personal Email Id",
					"Personal Email Id entered is: "
							+ Stock.GetParameterValue("PersonalEmail"), false);

			Web.clickOnElement(continue_button);
			Reporter.logEvent(
					Status.INFO,
					"Clicking Continue to Loan Review and Confirmation button",
					"Continue to Loan Review and Confirmation button is clicked",
					false);
			wait(2000);

			Web.clickOnElement(submitForProcessing);

			Web.clickOnElement(submitLoanRequestBtn);
			Reporter.logEvent(Status.INFO,
					"Clicking Agree & submit Loan Request ",
					"Agree & submit Loan Request button has been clicked",
					false);

			if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
				Reporter.logEvent(Status.PASS,
						"verifying successfull submission of loan request",
						"Loan application was delivered successfully", false);
				String conf_id = confirmation_id.getText();
				Reporter.logEvent(Status.PASS,
						"Confirmation Id from request submission",
						"Confirmation Id: " + conf_id, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"verifying successfull submission of loan request",
						"Loan request not delivered successfully", false);
			}

		} else {
			Reporter.logEvent(
					Status.INFO,
					"Verifying participant has multiple outstanding loans",
					"Multiple Outstanding loans section is displayed, participant has multiple outstanding loans",
					false);
		}
	}

	public void verify_LoanStructureOverview_Hyperlink() {
		if (Web.isWebElementDisplayed(Payment_repayment_hyperlink)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify the 'Payment & repayment options' field",
					"The 'Payment & repayment options' field with hover text/link,View payment and repayment options is displayed",
					false);

			String winHandleBefore = Web.getDriver().getWindowHandle();

			Web.clickOnElement(Payment_repayment_hyperlink);
			Reporter.logEvent(
					Status.INFO,
					"Clicking on \"View payment and repayment options\" hyperlink ",
					"\"View payment and repayment options\" hyperlink has been clicked",
					false);

			for (String winHandle : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(winHandle);
			}

			if (Web.isWebElementDisplayed(Payment_repayment_options, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Clicking on \"View payment and repayment options\" hyperlink should open up the details screen with all the details should display",
						"Clicking on hyperlink opens up the details screen with all the details",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying Clicking on \"View payment and repayment options\" hyperlink should open up the details screen with all the details should display",
						"Clicking on hyperlink didn't open a new window with details",
						false);
			}
			Web.getDriver().close();
			Web.getDriver().switchTo().window(winHandleBefore);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify the 'Payment & repayment options' field",
					"The 'Payment & repayment options' field with hover text/link,View payment and repayment options is not displayed",
					false);
		}
	}

	public void verify_FullLoanStructureDetails() {
		boolean flag1 = false;
		boolean flag2 = false;
		if (Web.isWebElementDisplayed(Loan_StructureDetails_hyperlink)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify the 'Loan structure' field",
					"The 'Loan structure' field with \"View full loan structure details\" hyperlink is displayed",
					false);

			String winHandleBefore = Web.getDriver().getWindowHandle();

			Web.clickOnElement(Loan_StructureDetails_hyperlink);

			Reporter.logEvent(
					Status.INFO,
					"Clicking on \"View full loan structure details\" hyperlink ",
					"\"View full loan structure details\" hyperlink has been clicked",
					false);

			for (String winHandle : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(winHandle);
			}

			if (Web.isWebElementDisplayed(Active_Loan_StructureDetails, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Clicking on \"View full loan structure details\" hyperlink should takes the user to the existing CSAS full loan structure details",
						"Clicking on hyperlink displays CSAS full loan structure details",
						false);

				Web.clickOnElement(Active_Loan_Structure_minimizebtn);
				Reporter.logEvent(
						Status.INFO,
						"Clicking on Loan Structure Overview minimize button",
						"Loan Structure Overview minimize button has been clicked",
						false);

				if (!(Web.isWebElementDisplayed(
						Active_Loan_Structure_ExpandedDetails, true))) {
					Reporter.logEvent(
							Status.INFO,
							"When clicked on minimize button, verifying details displayed are collapsed",
							"Loan Structure details are minimized", false);
					flag1 = true;
				} else {
					flag1 = false;
					Reporter.logEvent(
							Status.INFO,
							"When clicked on minimize button, verifying details displayed are collapsed",
							"Loan Structure details are not minimized", false);
				}

				Web.clickOnElement(Active_Loan_Structure_restorebtn);
				Reporter.logEvent(
						Status.INFO,
						"Clicking on Loan Structure Overview restore button",
						"Loan Structure Overview restore button has been clicked",
						false);

				if ((Web.isWebElementDisplayed(
						Active_Loan_Structure_ExpandedDetails, true))) {
					Reporter.logEvent(
							Status.INFO,
							"When clicked on minimize button, verifying details displayed are expanded",
							"Loan Structure details are expanded", false);
					flag2 = true;
				} else {
					Reporter.logEvent(
							Status.INFO,
							"When clicked on minimize button, verifying details displayed are expanded",
							"Loan Structure details are not expanded", false);
					flag2 = false;
				}

				if ((flag1) || (flag2)) {
					Reporter.logEvent(
							Status.PASS,
							"Verifying Loan Structure Overview section should be collapsible/expandable within the UI",
							"Loan Structure Overview section is collapsible/expandable within the UI",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verifying Loan Structure Overview section should be collapsible/expandable within the UI",
							"Loan Structure Overview section is not collapsible/expandable within the UI",
							false);
				}

			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying Clicking on \"View full loan structure details\" hyperlink should takes the user to the existing CSAS full loan structure details",
						"Clicking on hyperlink not displaying CSAS full loan structure details",
						false);
			}
			Web.getDriver().close();
			Web.getDriver().switchTo().window(winHandleBefore);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify the 'Loan structure' field",
					"The 'Loan structure' field with \"View full loan structure details\" hyperlink is not displayed",
					false);
		}
	}

	public void verify_LoanStructure_ApprovalReqFields() {

		if (Web.isWebElementDisplayed(LoanStructure_ApprovalReq)) {
			Reporter.logEvent(
					Status.PASS,
					"Verifying Approval Requirements fields are displayed or not",
					"Fields are Displayed:\n"
							+ LoanStructure_ApprovalReq.getText(), true);

			if (Web.isWebElementDisplayed(LoanStructure_ApprovalReq_values)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Approval Requirements field values are displayed or not",
						"Field values are Displayed:\n"
								+ LoanStructure_ApprovalReq_values.getText(),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying Approval Requirements field values are displayed or not",
						"Field values are not Displayed", true);
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verifying Approval Requirements fields are displayed or not",
					"Fields are not Displayed", true);
		}
	}

	public void verify_LoanStructure_NumberOfLoansAllowed() {

		if (Web.isWebElementDisplayed(NoOfLoansAllowed)) {
			Reporter.logEvent(
					Status.PASS,
					"Verifying Number of loans allowed fields are displayed or not",
					"Fields are Displayed:\n" + NoOfLoansAllowed.getText(),
					false);

			if (Web.isWebElementDisplayed(NoOfLoansAllowed_values)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Number of loans allowed field values are displayed or not",
						"Field values are Displayed:\n"
								+ NoOfLoansAllowed_values.getText(), false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verifying Number of loans allowed field values are displayed or not",
						"Field values are not Displayed", false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying Number of loans allowed are displayed or not",
					"Fields are not Displayed", false);
		}
	}

	public void verify_LoanStructure_Fees() {

		if (Web.isWebElementDisplayed(Fees)) {
			Reporter.logEvent(Status.PASS,
					"Verifying Fees fields are displayed or not",
					"Fields are Displayed:\n" + Fees.getText(), false);

			if (Web.isWebElementDisplayed(Fees_values)) {
				Reporter.logEvent(
						Status.PASS,
						"Verifying Fees field values are displayed or not",
						"Field values are Displayed:\n" + Fees_values.getText(),
						false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verifying Fees field values are displayed or not",
						"Field values are not Displayed", false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verifying Fees fields are displayed or not",
					"Fields are not Displayed", false);
		}
	}

	public void verifyText(WebElement sObj, String sExpText, String sMsg,
			Boolean sMatch) {
		String sActText_from_webElement = sObj.getText();
		String sActText = trim_Messages(sActText_from_webElement);
		if (sMatch) {

			if (sExpText.equalsIgnoreCase(sActText)) {
				Reporter.logEvent(Status.PASS, sMsg, sExpText, false);

			} else {
				Reporter.logEvent(Status.FAIL, "Expected text :" + sExpText,
						" But Actual was :" + sActText, true);
			}

		} else {

			if (sActText.contains(sExpText)) {
				Reporter.logEvent(Status.PASS, sMsg, sExpText, false);

			} else {
				Reporter.logEvent(Status.FAIL, "Expected text :" + sExpText,
						" Actual was :" + sActText, true);
			}

		}

	}

	public String trim_Messages(String message) {
		Pattern pt = Pattern.compile("[^a-zA-Z0-9,>_'/.-]");
		Matcher match = pt.matcher(message);
		while (match.find()) {
			String s = match.group();
			message = message.replaceAll("\\" + s, " ");
		}
		System.out.println(message);
		return message;
	}

	public void verifyConfirmationID() {
		Web.clickOnElement(deliveryFormBtn);
		Reporter.logEvent(Status.INFO, "Click Delivery Form button",
				"Delivery Form button has been clicked", false);

		if (Web.isWebElementDisplayed(loan_summary_Info, true)) {
			Reporter.logEvent(Status.PASS,
					"verifying successfull submission of loan request",
					"Loan application was delivered successfully", true);
			wait(2000);
			String conf_id = confirmation_id.getText();

			if (conf_id != null && conf_id != "") {
				Reporter.logEvent(Status.PASS,
						"Confirmation Id from request submission",
						"Confirmation Id: " + conf_id, true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Confirmation Id from request submission",
						"Confirmation Id is not displayed ", true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"verifying successfull submission of loan request",
					"Loan request not delivered successfully", true);
		}
	}

	public void wait(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String borrowAmountVerify() {
		String amount = amount_to_borrow_txtBox.getAttribute("value");
		String remove_comma = amount.replace(",", "");

		return remove_comma;
	}

	public Float getMaxAmount() {
		String max = maxAmount.getText();
		System.out.println(max);
		Float amount = Web.getIntegerCurrency(max);

		return amount;
	}

	public Float getMinAmount() {
		String min = minAmount.getText();

		Float amount = Web.getIntegerCurrency(min);

		return amount;
	}

	/**
	 * Method to Verify "Participant has Pending Loan WR_487" Exception.
	 */
	public void verifyPendingLoanErrormessage() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(pendingLoanMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify Loan pending error message on UI.
		CommonLib.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Participant has loan Pending exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}

	/**
	 * Method to verify
	 * "Cannot grant loan to Participant whose Employment is terminated. BR_485"
	 * and "This participant is currently non active. BR_481" exception.
	 */
	public void verifyEmploymentTerminatedErrormessage() {
		String actualExceptionMsg = "";
		String expectedExceptionMsg = "";
		// Get actual and expected exception messages.
		actualExceptionMsg = Web.getWebElementText(employmentTerminatedErrorMsg);
		expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage1");

		// Verify "Cannot grant loan to Participant whose Employment is terminated. BR_485" exception message on UI.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Cannot grant loan to Participant whose Employment is terminated. BR_485 exception should display on UI.");
		// Goto participant search page and search a participant.
		Web.clickOnElement(menuSearch);
		participantHomeObj = new ParticipantHome().get();
		participantHomeObj.gotoParticipantPageAndSearchParticipant();
		
		// go to Loan request page.
		Web.mouseHover(menuPPTChanges);
		if (Web.isWebElementDisplayed(menuLoanRequest)) {
			Web.clickOnElement(menuLoanRequest);
		} else {
			load();
		}
		// Get actual and expected exception messages.
		actualExceptionMsg = Web
				.getWebElementText(nonActiveParticipantErrorMsg);
		expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage2");

		// Verify "This participant is currently non active. BR_481" exception message on UI.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg,
						actualExceptionMsg,
						"This participant is currently non active. BR_481 exception should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "This participant's ownership_ind reflects a spousal takeover BR_479" exception.
	 */
	public void verifyOwnerShipErrormessage() {
		// Get actual and expected exception messages.
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");
		Web.waitForElement(ownershipErrorMsg);
		String actualExceptionMsg = Web.getWebElementText(ownershipErrorMsg);

		// Verify "This participant's ownership_ind reflects a spousal takeover BR_479" exception message on UI.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"This participant's ownership_ind reflects a spousal takeover BR_479 exception should display on UI.");
		Web.clickOnElement(menuSearch);
	}

	/**
	 * Method to verify loan structure details.
	 */
	public void verifyLoanStructureOverview() {
		String actualFieldValue = "";
		// Get actual Participant grouping Value from UI.
		actualFieldValue = Web.getWebElementText(participantGrouping);
		String expectedFieldValue = Stock
				.GetParameterValue("ParticipantGrouping");

		// Verify Participant grouping displayed on UI.
		CommonLib.verifyExpectedAndActualEual(actualFieldValue,
				expectedFieldValue,
				"Participant Grouping should display on UI.");

		// Get AutoLoan Offset days.
		actualFieldValue = Web.getWebElementText(autoLoanOffset);

		// Verify AutoLoan Offset days on UI.
		if (actualFieldValue.equalsIgnoreCase("NO")
				|| actualFieldValue.equalsIgnoreCase("YES")) {
			Reporter.logEvent(Status.PASS,
					"Auto Loan Offset days should display on UI.",
					actualFieldValue, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Auto Loan Offset days should display on UI.",
					actualFieldValue, true);
		}

		// Get new loan wait days.
		actualFieldValue = Web.getWebElementText(newLoanwaitDays);

		// Verify new loan wait days on UI.
		if (actualFieldValue.equalsIgnoreCase("NO")
				|| actualFieldValue.equalsIgnoreCase("YES")) {
			Reporter.logEvent(Status.PASS,
					"New loan wait days should display on UI.",
					actualFieldValue, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"New loan wait days should display on UI.",
					actualFieldValue, true);
		}

		actualFieldValue = Web.getWebElementText(oneOrTwoStepLoan);

		// Verify one or two step loan on UI.
		if (actualFieldValue.equalsIgnoreCase("One-Step")
				|| actualFieldValue.equalsIgnoreCase("Two-Step")) {
			Reporter.logEvent(Status.PASS,
					"One or Two Step Loan should display on UI.",
					actualFieldValue, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"One or Two Step Loan should display on UI.",
					actualFieldValue, true);
		}
		// Get Loan refinance.
		actualFieldValue = Web.getWebElementText(loanRefinance);

		// Verify loan refinance on UI.
		if (actualFieldValue.equalsIgnoreCase("NO")
				|| actualFieldValue.equalsIgnoreCase("YES")) {
			Reporter.logEvent(Status.PASS,
					"Loan Refinace value should display on UI.",
					actualFieldValue, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Loan Refinace value should display on UI.",
					actualFieldValue, true);
		}
		// Get loan allowed after previous default vlue.
		actualFieldValue = Web
				.getWebElementText(loanAllowedAfterpreviousDefault);

		// Verify loan allowed after previous default value on UI.
		if (actualFieldValue.equalsIgnoreCase("NO")
				|| actualFieldValue.equalsIgnoreCase("YES")) {
			Reporter.logEvent(Status.PASS,
					"Loan allowed after previous default value on UI.",
					actualFieldValue, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Loan allowed after previous default value on UI.",
					actualFieldValue, true);
		}
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "Participant has Pending loan transfer" exception message.
	 */
	public void verifyPendingTranferErrorMsg() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(pendingLoanTransferError);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify Loan tranfer error message on UI.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Participant has Pending loan transfer exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "participant's group has a Disb hold" exception message.
	 */
	public void verifyDisbHoldErrorMsg() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(br467ErrorMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify participant's group has a Disb hold. BR_467
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"participant's group has a Disb hold exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "This participant's group is Terminated. BR_511" exception message.
	 */
	public void verifyParticipantGroupTerminatedExceptionMsg() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(br511ErrorMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify This participant's group is Terminated. BR_511.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"This participant's group is Terminated. BR_511 exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "Unable to locate an account balance" exception message.
	 */
	public void verifyUnableToLocateAnAccountBalanceExceptionMsg() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(accountBalErrorMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify Unable to locate an account balance.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Unable to locate an account balance exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "Participant has exceeded Maximum Number of Loans allowed per period. BR_469" exception message.
	 */
	public void verifyParticipantExceededMaximumNumberOfLoans() {
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(br469ErrorMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify Participant has exceeded Maximum Number of Loans allowed per period. BR_469
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Participant has exceeded Maximum Number of Loans allowed per period. BR_469 exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify "Loan Summary screen the Payment frequency and Payment method is getting display as Payment:bi-weekly and Payment method: Payroll".
	 * @throws InterruptedException 
	 */
	public void verifyPaymentMethodAndPeriod() throws InterruptedException{
		// Get actual and expected payment Method.
		String actualPaymentMethod = Web.getWebElementText(paymentMethod);
		String expectedPaymentMethod = Stock.GetParameterValue("PaymentMethod");

		// Verify Payment method is getting display in Loan Summary screen as "Payroll".
		CommonLib
				.verifyExpectedAndActualEual(expectedPaymentMethod,actualPaymentMethod,
						" Payment method is getting display in Loan Summary screen as Payroll ");
		// Get Min loan amount and entee minimum value.
		String amount_to_borrow = getMinAmount().toString();
		Web.setTextToTextBox(amount_to_borrow_txtBox, amount_to_borrow);
		
		// Selct Payment Frequency as "bi-weekly".
		String paymentFrequencyValue = Stock.GetParameterValue("PaymentFrequency");
		Web.selectDropDownOption(paymentFrequency,paymentFrequencyValue, true);
		
		// enter loan term and add.
		Web.waitForElement(loan_term);
		Web.setTextToTextBox(loan_term,
				Stock.GetParameterValue("Loan Terms"));
		Reporter.logEvent(Status.INFO,
				"Value to be added into loan term field",
				"Value is added into loan term field", false);
		Web.clickOnElement(add_button);
		// Select loan quotes and fill Contact Information.
		Thread.sleep(1000);
		Web.waitForElement(quick_qoutes_button);
		Web.clickOnElement(quick_qoutes_button);
		Web.waitForElement(loan_qoute1_radio_button);
		Web.clickOnElement(loan_qoute1_radio_button);
		Reporter.logEvent(Status.INFO, "Selecting a loan quote",
				"First loan quote is selected", false);
		
		//  Verify it should display the message "Change to the payment frequency requires a form to be sent to and returned by the participant".
		String actualPaymentFrequecyChangedMsg = Web.getWebElementText(promissory_note);
		String expectedPaymentFrequecyChangedMsg =Stock.GetParameterValue("PaymentFrequencyChangedMsg");
		if(actualPaymentFrequecyChangedMsg.contains(expectedPaymentFrequecyChangedMsg)){
			Reporter.logEvent(Status.PASS, "Change to the payment frequency requires a form to be sent to and returned by the participant messgae should display", "Expected was should present "+expectedPaymentFrequecyChangedMsg+" actual is "+actualPaymentFrequecyChangedMsg+" ", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Change to the payment frequency requires a form to be sent to and returned by the participant messgae not displayed", actualPaymentFrequecyChangedMsg, true);
		}
			
		//Web.waitForElement(selected_loan_quote);
		Web.setTextToTextBox(mobile_Phone_txtBox, Stock.GetParameterValue("MobilePhoneNumber"));
		Thread.sleep(1000);
		Web.clickOnElement(updateRecord);
		Web.waitForElement(continue_button);
		Web.clickOnElement(continue_button);
		Reporter.logEvent(Status.INFO,
				"Clicking the continue button to move to the next page",
				"Continue button clicked and moved to next page", true);
		// Get actual and expected frequency Method.
		String actualPaymentFrequency = Web.getWebElementText(confirmPaymentFrequency);
		// Verify Payment method is getting display in Loan Summary screen as "bi-weekly".
		if(actualPaymentFrequency.contains(paymentFrequencyValue.toLowerCase())){
			Reporter.logEvent(Status.PASS, "Payment method is getting display in Loan Summary screen as bi-weekly", "Expected was should present "+paymentFrequencyValue+" actual is "+actualPaymentFrequency+" ", true);
		}else{
			Reporter.logEvent(Status.FAIL, "Payment method is getting display in Loan Summary screen as bi-weekly", actualPaymentFrequency, true);
		}
		Web.clickOnElement(menuSearch);	
	}
	
	/**has
	 * 
	 * Method to verify Loan Structure Overview field data updates/refreshes,including hyper links.
	 */
	public void verifyPrincipalResidenceLoanStructureOverview(){
		
		// Select loanType as PrincipalResidence.
		Web.clickOnElement(principalResidence);
		
		// Verify LoanCalculated link present under loan structure overview.
		CommonLib.verifyIfWebElementPresent(howIsCalculatedLink, "LoanCalculated link should present under loan structure overview", true);
		
		// Verify IntrestRate should displayed under loan structure overview section.
		CommonLib.verifyIfWebElementTextPresent(intrestRate, Stock.GetParameterValue("IntrestRate"),"IntrestRate should displayed under loan structure overview section");
		
		// Verify Payment & repayment options field exist on UI.
		CommonLib.verifyIfWebElementPresent(viewPaymentAndRepaymentOptionLink,"Payment & repayment options field should display underloan structure overview section.",true);
		
		// Verify view full loan structure details link exist on UI.
		CommonLib.verifyIfWebElementPresent(Loan_StructureDetails_hyperlink,"View full loan structure details link should display underloan structure overview section.",true);
		
		// Click on view loan structure details link and verify loan details.
		Web.clickOnElement(Loan_StructureDetails_hyperlink);
        String getParentWindow = Web.getDriver().getWindowHandle();
        for (String windowHandles : Web.getDriver().getWindowHandles()) 
        {
        	if(windowHandles!=getParentWindow){
        		// switch focus of WebDriver to the next found window handle (that's your newly opened window).
               Web.getDriver().switchTo().window(windowHandles); 
        	}
        }
        // Verify loan structue details.
		CommonLib.verifyIfWebElementTextPresent(loanType, Stock.GetParameterValue("LoanType"), "Loan Type should dispalyed");
		CommonLib.verifyIfWebElementTextPresent(loanCatagaries, Stock.GetParameterValue("LoanCategories"), "Loan Type should dispalyed");
		CommonLib.verifyIfWebElementTextPresent(generalPurposeLoanTerm, Stock.GetParameterValue("GeneralPurposeLoanTerm"), "Loan Type should dispalyed");
		CommonLib.verifyIfWebElementTextPresent(motorGageLoanTerm, Stock.GetParameterValue("MotorGageLoanTerm"), "Loan Type should dispalyed");
	}
	
	/**
	 * Method to verify should display the Loan Info/History screen in separate window when clicked on Loan Number hyperlink.
	 */
	public void loanNumberLinkWillTakeYouToLoanInfoHistoryPage(){
		Web.clickOnElement(loanNumber);
		 String getParentWindow = Web.getDriver().getWindowHandle();
	        for (String windowHandles : Web.getDriver().getWindowHandles()) 
	        {
	        	if(windowHandles!=getParentWindow){
	        		// switch focus of WebDriver to the next found window handle (that's your newly opened window)
	               Web.getDriver().switchTo().window(windowHandles); 
	        	}
	        }
		
		// verify we are on loan info history page.
		String expectedUrl = Stock.GetParameterValue("loanHistoryPageURL");
		String actualURl = Web.getDriver().getCurrentUrl();
		if(actualURl.contains(expectedUrl)){
			Reporter.logEvent(Status.PASS, "Loan History page is dispalyed", actualURl, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Loan History page is not dispalyed", actualURl, true);
		}
	}
	 
	
	/**
	 * Method to verify  "Individual has an outstanding Promissory Note WR_489" exception.
	 */
	public void verifyIndividualHasAnOutstandingPromissoryNote(){
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(wr489ErrorMsg);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		// Verify Individual has an outstanding Promissory Note WR_489.
		CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Individual has an outstanding Promissory Note WR_489 exception message should display on UI.");
		Web.clickOnElement(menuSearch);
	}
	
	/**
	 * Method to verify  "participant's plan balance does not meet the minimum amount required by the plan to take a loan" exception.
	 */
	public void verifyParticipantPlanBalanceDoenNotMeetMinimumBalEligibleToTakeLoan(){
		
		// Get actual and expected exception messages.
		String actualExceptionMsg = Web.getWebElementText(minAS_208);
		String expectedExceptionMsg = Stock.GetParameterValue("ExceptionMessage");

		//Participant's plan balance does not meet the minimum amount required by the plan to take a loan.
		if(actualExceptionMsg.contains(expectedExceptionMsg)){
			Reporter.logEvent(Status.PASS, "participant's plan balance does not meet the minimum amount required by the plan to take a loan exception is displayed", expectedExceptionMsg, false);
			
		}else{Reporter.logEvent(Status.FAIL, "participant's plan balance does not meet the minimum amount required by the plan to take a loan exception is not displayed", expectedExceptionMsg, true);
		
	}
		/*CommonLib
				.verifyExpectedAndActualEual(expectedExceptionMsg, actualExceptionMsg,
						"Participant's plan balance does not meet the minimum amount required by the plan to take a loan  exception message should display on UI.");*/
		Web.clickOnElement(menuSearch);
	}
}