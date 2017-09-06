
package pageobjects.userregistration;

import java.util.List;

import lib.Reporter;
import lib.Web;

import com.aventstack.extentreports.*;
import com.sun.org.apache.bcel.internal.generic.Select;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;






import org.testng.Assert;

import appUtils.Common;
import core.framework.Globals;
import pageobjects.login.LoginPage;

public class Registration extends LoadableComponent<Registration> {

	//Declarations
	private LoadableComponent<?> parent;
	
	//Objects and locators declaration
	
	@FindBy(xpath=".//*[text()[normalize-space()='Registration']]") private WebElement lblRegistrationHeader;
	
	//Provide personal information
	@FindBy(id="firstNameInput") private WebElement txtfirstName;
	@FindBy(id="middleNameInput") private WebElement txtMiddleName;
	@FindBy(id="lastNameInput") private WebElement txtLastname;
	@FindBy(id="dateOfBirth") private WebElement txtDob;
	@FindBy(id="genderInput") private WebElement selGender;
    @FindBy(id="ssnInput") private WebElement txtSSN;
	@FindBy(id="maritalStatusInput") private WebElement selMaritalStatus;
	
	//Empoyment information
	@FindBy(id="currentAnnualIncomeInput") private WebElement inpAnnualIncome;
	@FindBy(id="dateOfHire") private WebElement inpDateOfHire;
	
	//Provide mailing address
	@FindBy(id="addressLine1Input") private WebElement inpAddressLine1;
	@FindBy(id="addressLine2Input") private WebElement inpAddressLine2;
	@FindBy(id="cityInput") private WebElement inpCity;
	@FindBy(id="stateInput") private WebElement drpState;
	@FindBy(id="zipcodeInput") private WebElement inpZipcode;
	@FindBy(id="countryInput") private WebElement drpCountry;
	@FindBy(xpath="//button[text()='CONTINUE']") private WebElement btnSubmit;
	@FindBy(xpath="//label[@for='dateOfHire']") private WebElement lblDateOfHire;
	@FindBy(xpath="//header[./h2[contains(text(),'Provide mailing address')]]//following-sibling::div[@class='row']") private WebElement panelProvideMailAddr;
	@FindBy(xpath="//label[contains(text(),'Current Annual Income')]//following-sibling::ng-messages/ng-message/span") private WebElement Err_inpAnnualIncome;
	@FindBy(xpath="//label[contains(text(),'Date Of Hire')]//following-sibling::ng-messages/ng-message") private WebElement Err_inpDateOfHire;
	@FindBy(xpath="//label[contains(text(),'Address Line 1')]//following-sibling::ng-messages/ng-message") private WebElement Err_inpAddress1;
	@FindBy(xpath="//label[contains(text(),'Address Line 2')]//following-sibling::ng-messages/ng-message") private WebElement Err_inpAddress2;
	@FindBy(xpath="//label[contains(text(),'City')]//following-sibling::ng-messages/ng-message") private WebElement Err_inpCity;
	private String textField="//*[contains(text(),'webElementText')]";
	
	/** Empty args constructor
	 * 
	 */
	
	public Registration() {
		this.parent = new LoginPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	
	public Registration(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblRegistrationHeader),"Registration page is Not Loaded");
		/*String sponser = this.lblSponser.getAttribute("Alt");
		if(sponser.isEmpty())
		{
			sponser=Common.GC_DEFAULT_SPONSER;
		}
		if (!Common.isCurrentSponser(sponser)) {
			Assert.assertTrue(Web.isWebElementDisplayed(txtPassword,true));

		} */
	}

	@Override
	protected void load() {
		
		this.parent.get();
		((LoginPage) this.parent).clickRegister();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
	}
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	private WebElement getWebElement(String fieldName) {
		//I do not have a PIN
		if (fieldName.trim().equalsIgnoreCase("Header Registration")) {
			return this.lblRegistrationHeader;
		}	
		else if(fieldName.trim().equalsIgnoreCase("LABEL DATE OF HIRE")) {
			return this.lblDateOfHire;
			}
		
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		return null;
	}
	
	
	/**Method to set details under 'Registration -Provide personal information
	 * 
	 */
	
	public void providePersonalInformation (String firstName,
												String middleName,
												String lastName,
												String dob,
												String gender,
												String sSN,
												String maritalStatus){
				
		Web.setTextToTextBox(txtfirstName, firstName);
		Web.setTextToTextBox(txtMiddleName, middleName);
		Web.setTextToTextBox(txtLastname, lastName);
		Web.setTextToTextBox(txtDob, dob);
		Web.selectDropDownOption(selGender, gender);
		Web.setTextToTextBox(txtSSN, sSN);
		Web.selectDropDownOption(selMaritalStatus, maritalStatus);

	}
	
	/**Method to set details under 'Registration -Employment information
	 * 
	 */
	
	public void provideEmploymentInformation(String annualIncome,
												String dateOfHire){
		
		Web.setTextToTextBox(inpAnnualIncome, annualIncome);
		Web.setTextToTextBox(inpDateOfHire, dateOfHire);
		
	}
	
	/**Method to set details under 'Registration -Provide mailing address
	 * 
	 */
	
	public void provideMailingAddress(String addressLine1,
										String addressLine2,
										String city,
										String state,
										String zip,
										String country){
		
		Web.setTextToTextBox(inpAddressLine1, addressLine1);
		Web.setTextToTextBox(inpAddressLine2, addressLine2);
		Web.setTextToTextBox(inpCity, city);
		Web.selectDropDownOption(drpState, state);
		Web.setTextToTextBox(inpZipcode, zip);
		Web.selectDropDownOption(drpCountry, country);
		Web.getDriver().switchTo().defaultContent();
		Web.clickOnElement(btnSubmit);
				
	}
	
	/**
	 * Method to verify Label is Displayed
	 * @param fieldName
	 * @return
	 */
	public boolean isLabelDisplayed(String labelName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", labelName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify " + labelName
					+ "   Label is Displayed", "'"+labelName + "' Label is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "Verify " + labelName
					+ "   Label is Displayed", "'"+labelName + "' Label is Not Displayed", false);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
		
	
	/**
	 * <pre>
	 * Method to get the text of an webElement
	 * Returns string webElement is displayed
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementText(String fieldName) {
		String getText = "";

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

			getText = this.getWebElement(fieldName).getText().trim();

		}

		return getText;

	}
	/** Check if specified field is displayed on the page
	 * 
	 * @param fieldName
	 * @return <b>true</b> if specified field is displayed. <b>false</b> otherwise.
	 */
	public boolean isFieldDisplayed(String fieldName) {
		WebElement element = this.getWebElement(fieldName);
		
		if (element == null) {
			return false;
		} else {
			return Web.isWebElementDisplayed(element);
		}
	}

	/**
	 * Method to verify inline validations for Current Annual Income Input Field
	 * 
	 */
	public void validateCurrentAnnualIncomeField() {
	Web.clickOnElement(inpAnnualIncome);
	Web.clickOnElement(inpDateOfHire);
	String actualErrMsg=Err_inpAnnualIncome.getText().trim();
	String expectedErrMsg="Annual income is required.";
	
	if (actualErrMsg.length() == 0) {
		Reporter.logEvent(Status.FAIL,
				"Verify error message is displayed for Current Annual Income",
				"No error message is displayed on the page", true);
	} else {
		if (Web.VerifyText(expectedErrMsg,
				actualErrMsg, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message is displayed for Current Annual Income",
					"Expected error message is displayed.\nExpected: "
							+ expectedErrMsg
							+ "\nActual:" + actualErrMsg, true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message is displayed for Current Annual Income",
					"Expected error message is not displayed.\nExpected: "
							+expectedErrMsg
							+ "\n" + actualErrMsg, true);
		}
	}
			Web.setTextToTextBox(inpAnnualIncome, "ABC");
			if(Web.VerifyText("$0.00", inpAnnualIncome.getAttribute("value"))){
		
			Reporter.logEvent(Status.PASS, "Verify 'Current Annual Income' Field accepts Only Numerics" , 
					"'Current Annual Income' Field Accepts Only Numerics\nCurrent Annual Income:"+inpAnnualIncome.getAttribute("value"),
					true);

			}
			else
			{
			Reporter.logEvent(Status.FAIL,"Verify 'Current Annual Income' Field accepts Only Numerics" , 
					"'Current Annual Income' Field Accepts Alpha Numerics\nCurrent Annual Income:"+inpAnnualIncome.getAttribute("value"),
					true);
			
			}
	
			Web.setTextToTextBox(inpAnnualIncome, "0");
			if(Web.VerifyPartialText("$", inpAnnualIncome.getAttribute("value"),false)){
		
			Reporter.logEvent(Status.PASS, "Verify '$' is appended for 'Current Annual Income' Field Value" , 
					"'$' is appended for 'Current Annual Income' Field Value\nCurrent Annual Income:"+inpAnnualIncome.getAttribute("value"),
					true);

			}
			else
			{
			Reporter.logEvent(Status.FAIL,"Verify '$' is appended for 'Current Annual Income' Field Value" , 
					"'$' is not appended for 'Current Annual Income' Field Value\nCurrent Annual Income:"+inpAnnualIncome.getAttribute("value"),
					true);
			
			}
	
		}
	
	/**
	 * Method to verify inline validations for Date Of Hire Input Field
	 * 
	 */
	public void validateDateOfHireField() {
	Web.clickOnElement(inpDateOfHire);
	Web.clickOnElement(inpAnnualIncome);
	String actualErrMsg=Err_inpDateOfHire.getText().trim();
	String expectedErrMsg="Date of hire is required in the following format MM/DD/YYYY. ";
	
	if (actualErrMsg.length() == 0) {
		Reporter.logEvent(Status.FAIL,
				"Verify error message is displayed for Date Of Hire",
				"No error message is displayed on the page", true);
	} else {
		if (Web.VerifyText(expectedErrMsg,
				actualErrMsg, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message is displayed for Date Of Hire",
					"Expected error message is displayed.\nExpected: "
							+ expectedErrMsg
							+ "\nActual:" + actualErrMsg, true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message is displayed for Date Of Hire",
					"Expected error message is not displayed.\nExpected: "
							+expectedErrMsg
							+ "\n" + actualErrMsg, true);
		}
	}
			Web.setTextToTextBox(inpDateOfHire, "13/12/1999");
			if(Web.VerifyText("12/12/1999", inpDateOfHire.getAttribute("value"))){
		
			Reporter.logEvent(Status.PASS, "Verify 'Date Of Hire' Field Accepts Only MM/DD/YYYY Format" , 
					"'Date Of Hire' Field Accepts Only MM/DD/YYYY Format",
					true);

			}
			else
			{
			Reporter.logEvent(Status.FAIL,"Verify 'Date Of Hire' Field Accepts Only MM/DD/YYYY Format" , 
					"'Date Of Hire' Field Accepts other Date Formats also"+inpDateOfHire.getAttribute("value"),
					true);
			
			}
	
			
		}
	
	/**
	 * Method to verify inline validations for AddressLine1 Input Field
	 * 
	 */
	public void verifyInLineValidationsForAddressLine1() {
		Actions keyBord=new Actions(Web.getDriver());
	Web.setTextToTextBox(inpAddressLine1, "123ABC");
	Web.clickOnElement(inpAddressLine1);
	keyBord.sendKeys(Keys.TAB).build().perform();
	//Web.clickOnElement(inpAddressLine2);
	if(!Web.isWebElementDisplayed(Err_inpAddress1)){
		Reporter.logEvent(
				Status.PASS,
				"Verify 'Address Line1' Field Accepts Alpha Numeric",
				"'Address Line1' Field Accepts Alpha Numeric", false);
	} else {
		Reporter.logEvent(
				Status.FAIL,
				"Verify 'Address Line1' Field Accepts Alpha Numeric",
				"'Address Line1' Field Accepts is not Accepting Alpha Numeric", true);
	}
	
	Web.setTextToTextBox(inpAddressLine1, "@");
	Web.clickOnElement(inpAddressLine1);
	Web.clickOnElement(inpCity);
	String actualErrMsg=Err_inpAddress1.getText().trim();
	String expectedErrMsg="Invalid Street Address.";
	
	if (actualErrMsg.length() == 0) {
		Reporter.logEvent(Status.FAIL,
				"Verify error message is displayed after entering Special Character for Address Line1",
				"No error message is displayed on the page", true);
	} else {
		if (Web.VerifyText(expectedErrMsg,
				actualErrMsg, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message is displayed after entering Special Character for Address Line1",
					"Expected error message is displayed.\nExpected: "
							+ expectedErrMsg
							+ "\nActual:" + actualErrMsg, true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message is displayed after entering Special Character for Address Line1",
					"Expected error message is not displayed.\nExpected: "
							+expectedErrMsg
							+ "\n" + actualErrMsg, true);
		}
	}
			Web.setTextToTextBox(inpAddressLine1, "ABC");
			Web.clickOnElement(inpAddressLine2);
			actualErrMsg=Err_inpAddress1.getText().trim();
			expectedErrMsg="Invalid Street Address.";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message is displayed after entering Alpahabates to Address Line1",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message is displayed after entering Alpahabates to Address Line1",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message is displayed after entering Alpahabates to Address Line1",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}
	
			Web.setTextToTextBox(inpAddressLine1, "");
			Web.clickOnElement(inpAddressLine2);
			actualErrMsg=Err_inpAddress1.getText().trim();
			expectedErrMsg="Address line 1 is required.";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message is displayed when Address Line1 is empty",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message is displayed when Address Line1 is empty",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message is displayed when Address Line1 is empty",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}
		}
	
	/**
	 * Method to verify inline validations for AddressLine2 Input Field
	 * 
	 */
	public void verifyInLineValidationsForAddressLine2() {
	Web.setTextToTextBox(inpAddressLine2, "123ABC");
	Web.clickOnElement(inpAddressLine1);
	if(!Web.isWebElementDisplayed(Err_inpAddress2)){
		Reporter.logEvent(
				Status.PASS,
				"Verify 'Address Line2' Field Accepts Alpha Numeric",
				"'Address Line2' Field Accepts Alpha Numeric", false);
	} else {
		Reporter.logEvent(
				Status.FAIL,
				"Verify 'Address Line2' Field Accepts Alpha Numeric",
				"'Address Line2' Field Accepts is not Accepting Alpha Numeric", true);
	}
	
	Web.setTextToTextBox(inpAddressLine2, "@");
	Web.clickOnElement(inpAddressLine1);
	Web.clickOnElement(inpCity);
	String actualErrMsg=Err_inpAddress2.getText().trim();
	String expectedErrMsg="Invalid Street Address";
	
	if (actualErrMsg.length() == 0) {
		Reporter.logEvent(Status.FAIL,
				"Verify error message is displayed after entering Special Character for Address Line2",
				"No error message is displayed on the page", true);
	} else {
		if (Web.VerifyText(expectedErrMsg,
				actualErrMsg, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message is displayed after entering Special Character for Address Line2",
					"Expected error message is displayed.\nExpected: "
							+ expectedErrMsg
							+ "\nActual:" + actualErrMsg, true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message is displayed after entering Special Character for Address Line2",
					"Expected error message is not displayed.\nExpected: "
							+expectedErrMsg
							+ "\n" + actualErrMsg, true);
		}
	}
			Web.setTextToTextBox(inpAddressLine1, "ABC1");
			Web.setTextToTextBox(inpAddressLine2, "ABC1");
			Web.clickOnElement(inpCity);
			actualErrMsg=Err_inpAddress2.getText().trim();
			expectedErrMsg="Address line 2 must not match Address line 1";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message is displayed when same address is given for Address Line1 and Address Line2",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message is displayed when same address is given for Address Line1 and Address Line2",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message is displayed when same address is given for Address Line1 and Address Line2",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}
	
		/*	Web.setTextToTextBox(inpAddressLine1, "");
			Web.clickOnElement(inpAddressLine2);
			actualErrMsg=Err_inpAddress2.getText().trim();
			expectedErrMsg="Invalid Street Address.";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message is displayed when Address Line1 is empty",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message is displayed when Address Line1 is empty",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message is displayed when Address Line1 is empty",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}*/
		}
	/**
	 * Method to verify inline validations for City Input Field
	 * 
	 */
	public void verifyInLineValidationsForCityField() {
	Web.setTextToTextBox(inpCity, "123ABC@");
	Web.clickOnElement(inpAddressLine2);
	
	String actualErrMsg=Err_inpCity.getText().trim();
	String expectedErrMsg="Invalid special characters.";
	
	if (actualErrMsg.length() == 0) {
		Reporter.logEvent(Status.FAIL,
				"Verify error message is displayed after entering Numeric/Special Character for City Field",
				"No error message is displayed on the page", true);
	} else {
		if (Web.VerifyText(expectedErrMsg,
				actualErrMsg, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message is displayed after entering Numeric/Special Character for City Field",
					"Expected error message is displayed.\nExpected: "
							+ expectedErrMsg
							+ "\nActual:" + actualErrMsg, true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message is displayed after entering Numeric/Special Character for City Field",
					"Expected error message is not displayed.\nExpected: "
							+expectedErrMsg
							+ "\n" + actualErrMsg, true);
		}
	}
			
			Web.setTextToTextBox(inpCity, "");
			Web.clickOnElement(inpAddressLine2);
			actualErrMsg=Err_inpCity.getText().trim();
			expectedErrMsg="City is required.";
			
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,
						"Verify error message is displayed when City Field is empty",
						"No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(expectedErrMsg,
						actualErrMsg, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message is displayed when City Field is empty",
							"Expected error message is displayed.\nExpected: "
									+ expectedErrMsg
									+ "\nActual:" + actualErrMsg, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify error message is displayed when City Field is empty",
							"Expected error message is not displayed.\nExpected: "
									+expectedErrMsg
									+ "\n" + actualErrMsg, true);
				}
			}
		}
}
