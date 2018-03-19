package pageobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;


import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import framework.util.CommonLib;
import generallib.General;

public class OnlineEnrollment extends LoadableComponent<OnlineEnrollment> {
	LoadableComponent<?> parent;
	ResultSet queryResultSet;
	public static String SSN = "";
	public static String MobileNumber = "";
	public static String DBName = "";
	ParticipantHome participantHomeObj;

	public OnlineEnrollment() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@FindBy(id = "oCMenu_318")
	private WebElement menuAddtlResources;

	@FindBy(id = "oCMenu_342")
	private WebElement createNewAcct;

	@FindBy(id = "table_Online Enrollment")
	private WebElement onlineEnrollment;

	@FindBy(id = "planNumberLabel")
	private WebElement planNumberLabel;

	@FindBy(id = "planNumberLabel")
	private WebElement ssnSearchedLabel;

	@FindBy(id = "planNumber")
	private WebElement planNumberTextbox;

	@FindBy(id = "planEnrollmentCode")
	private WebElement planEnrollmentCodeTextbox;

	@FindBy(id = "ssnSearched")
	private WebElement ssnSearchedTextbox;

	@FindBy(id = "validateSsnGaIdPin")
	private WebElement submitBtn;

	@FindBy(id = "createAccountEnroll")
	private WebElement planFoundLabel;

	/**
	 * Elements for validating CITY field
	 */
	@FindBy(id = "createAccountEnroll")
	private WebElement createAccountEnrollTable;

	@FindBy(id = "firstName")
	private WebElement firstNameTextbox;

	@FindBy(id = "lastName")
	private WebElement lastNameTextbox;

	@FindBy(id = "datOfBirth")
	private WebElement DateOfBirthTextbox;

	@FindBy(id = "gender")
	private WebElement genderRadioBtn;

	@FindBy(id = "married")
	private WebElement marriedRadioBtn;

	@FindBy(id = "annualIncome")
	private WebElement annualIncomeTextBox;

	@FindBy(id = "dateOfHire")
	private WebElement dateOfHireTextBox;

	@FindBy(id = "addressLine1")
	private WebElement addressLine1;

	@FindBy(id = "city")
	private WebElement cityTextbox;

	@FindBy(id = "cityError")
	private WebElement cityError;

	@FindBy(id = "state")
	private WebElement stateTextbox;
	
	@FindBy(xpath =".//select[@id='state']")
	private WebElement stateDropDown;

	@FindBy(id = "stateError")
	private WebElement stateError;

	@FindBy(id = "zipCode")
	private WebElement zipCodeTextBox;

	@FindBy(id = "country")
	private WebElement countryTextbox;

	@FindBy(id = "personalEmail")
	private WebElement personalEmailTextBox;

	@FindBy(id = "personalEmailError")
	private WebElement personalEmailError;

	@FindBy(id = "homePhone")
	private WebElement homePhoneTextBox;

	@FindBy(id = "homePhoneError")
	private WebElement homePhoneError;

	@FindBy(id = "mobilePhone")
	private WebElement mobilePhoneTextBox;

	@FindBy(id = "mobilePhoneError")
	private WebElement mobilePhoneError;

	@FindBy(id = "workPhone")
	private WebElement workPhoneTextBox;

	@FindBy(id = "workPhoneError")
	private WebElement workPhoneError;

	@FindBy(id = "internationalPhoneCountry")
	private WebElement internationalPhoneDropDown;

	@FindBy(id = "internationalPhoneNumber")
	private WebElement internationalPhoneTextBox;

	@FindBy(id = "internationalPhoneNumberError")
	private WebElement internationalPhoneNumberError;

	@FindBy(id = "continueToCreateAccount")
	private WebElement contToCreateNewAccountBtn;

	@FindBy(id = "addErrorDiv")
	private WebElement errorDiv;

	@FindBy(id = "addErrorMessages")
	private WebElement errorMsg;

	@FindBy(id = "preConfirmEnroll")
	private WebElement preConfirmEnroll;

	@FindBy(id = "back")
	private WebElement backBtn;

	@FindBy(id = "addErrorMessages")
	private WebElement addErrorMessages;

	/**
	 * PreConfirmation Page Elements
	 */

	@FindBy(id = "preConfirmFirst")
	private WebElement preConfirmFirstName;

	@FindBy(id = "preConfirmLast")
	private WebElement preConfirmLastName;

	@FindBy(id = "preConfirmDOB")
	private WebElement preConfirmDOB;

	@FindBy(id = "preConfirmSSN")
	private WebElement preConfirmSSN;

	@FindBy(id = "preConfirmGender")
	private WebElement preConfirmGender;

	@FindBy(id = "preConfirmMaritalStatus")
	private WebElement preConfirmMaritalStatus;

	@FindBy(id = "preAnnualIncome")
	private WebElement preConfirmAnnualIncome;

	@FindBy(id = "preDateOfHire")
	private WebElement preConfirmDOH;

	@FindBy(id = "preConfirmAddLineOne")
	private WebElement preConfirmAddrLine1;

	@FindBy(id = "addErrorMessages")
	private WebElement preConfirmAddrLine2;

	@FindBy(id = "preConfirmCity")
	private WebElement preConfirmCity;

	@FindBy(id = "preConfirmState")
	private WebElement preConfirmState;

	@FindBy(id = "preConfirmZip")
	private WebElement preConfirmZipCode;

	@FindBy(id = "preConfirmCountry")
	private WebElement preConfirmCountry;

	@FindBy(id = "preConfirmEmail")
	private WebElement preConfirmPersonalEmail;

	@FindBy(id = "preConfirmMobPhone")
	private WebElement preConfirmMobile;

	@FindBy(id = "preConfirmWorkPhone")
	private WebElement preConfirmWork;

	@FindBy(id = "preConfirmHomePhone")
	private WebElement preConfirmHome;

	@FindBy(id = "preConfirmIntlPhone")
	private WebElement preConfirmIntlPhone;

	@FindBy(xpath = ".//*[@id='createAccountEnroll']//input[@value='Clear']")
	private WebElement clearButton;

	@FindBy(id = "noClear")
	private WebElement noClear;

	@FindBy(id = "yesClear")
	private WebElement yesClear;

	@FindBy(id = "addressLine2")
	private WebElement addressLine2;

	@FindBy(id = "addressLine1Error")
	private WebElement addressLine1Error;

	@FindBy(id = "addressLine2Error")
	private WebElement addressLine2Error;

	@FindBy(id = "preConfirmAddLineOne")
	private WebElement preConfirmAddLineOne;

	@FindBy(id = "preConfirmAddLine2")
	private WebElement preConfirmAddLineTwo;

	@FindBy(id = "dateOfHireError")
	private WebElement dateOfHireError;

	@FindBy(id = "annualIncomeError")
	private WebElement annualIncomeError;

	@FindBy(id = "datOfBirthError")
	private WebElement datOfBirthError;

	@FindBy(id = "createNewAccount")
	private WebElement createAccount;

	@FindBy(id = "planNumberError")
	private WebElement planNumberError;

	@FindBy(id = "planEnrollmentCodeError")
	private WebElement planEnrollmentCodeError;

	@FindBy(id = "ssnSearchedError")
	private WebElement ssnSearchedError;

	@FindBy(xpath = ".//*[contains(@data-value,'Select Division')]")
	private WebElement defaultDivision;

	@FindBy(xpath = "//div[@class='selectize-input items full has-options has-items']")
	private WebElement divisionDropDown;

	@FindBy(xpath = "//div[@class='selectize-control single']")
	private WebElement div;

	@FindBy(xpath = "//div[./select[@id='division']]//div[@class='option'][1]")
	private WebElement selectItem;

	@FindBy(xpath = "//div[./select[@id='division']]//div[@class='option'][1]")
	private WebElement selectItem1;

	@FindBy(xpath = ".//button[@class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all']")
	private WebElement doneButton;

	@FindBy(id = "divisionError")
	private WebElement divisionError;

	@FindBy(id = "preDivision")
	private WebElement preDivision;

	@FindBy(id = "countryError")
	private WebElement countryError;

	@FindBy(xpath = ".//*[@id='reviewInfoSectionDiv']/div[2]/div")
	private WebElement reviewInfoSectionMsg;

	@FindBy(id = "ssn")
	private WebElement ssnEnrollPage;

	@FindBy(id = "enrollmenterrorMessage")
	private WebElement EnrollErrorMessage;

	@FindBy(xpath = ".//*[@id='enrollmenterrorMessage']//li")
	private WebElement EnrollErrorMessageText;

	@FindBy(id = "firstNameError")
	private WebElement firstNameError;

	@FindBy(id = "lastNameError")
	private WebElement lastNameError;

	@FindBy(xpath = "//div[@class='option']")
	private List<WebElement> options;

	@FindBy(xpath = ".//div[@class='panel panel-primary']//span[contains(text(),'Your account has been created')]")
	private WebElement confirmationNumber;

	@FindBy(xpath = ".//div[contains(text(),'Corporate')]")
	private WebElement optionOne;

	@FindBy(xpath = ".//input[@id='gender']/parent::*/following-sibling::div[1]")
	private List<WebElement> gender;
	
	@FindBy(xpath = ".//div[@id='reviewInfoSectionDiv']/child::div[2]")
	private WebElement accountNotCreatedMsg;
	
	@FindBy(css = "div#oCMenu_319")
	private WebElement menuSearch;
	
	Actions action = new Actions(Web.getDriver());

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(planFoundLabel));
	}

	@Override
	protected void load() {
		try {
			this.parent = new ParticipantHome().get();

			Web.mouseHover(menuAddtlResources);

			if (Web.isWebElementDisplayed(menuAddtlResources)) {
				Web.clickOnElement(createNewAcct);

				Web.waitForElement(planNumberLabel);

				if (Web.isWebElementDisplayed(onlineEnrollment, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Create Account page displayed or not",
							"Create Account page displyed successfully", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Create Account page displayed or not",
							"Create Account didn't get displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create New Account Link on Addtl Resources tab displayed or not",
						"Create New Account Link on Addtl Resources tab didn't get displayed successfully",
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to fill valid data in create account page.
	 */
	public void enterValidInputDetails() {
		// fill the valid data in GropuId,PEC and SSN fields.
		Web.setTextToTextBox(planNumberTextbox,
				Stock.GetParameterValue("GrpId"));
		Web.setTextToTextBox(planEnrollmentCodeTextbox,
				Stock.GetParameterValue("PEC"));
		long randomNumber = CommonLib.getRandomNumber(9);
		SSN = Long.toString(randomNumber);
		Web.setTextToTextBox(ssnSearchedTextbox, SSN);
		ssnSearchedTextbox.sendKeys(Keys.TAB);
		Web.clickOnElement(submitBtn);
		Web.waitForElement(onlineEnrollment);

		if (Web.isWebElementDisplayed(onlineEnrollment, true)) {
			Reporter.logEvent(Status.PASS,
					"Online Enrollment page should be display",
					"Online Enrollment page is displyed successfully", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Online Enrollment page should be display",
					"Online Enrollment page didn't get displayed successfully", true);
		}
	}

	private String getDropDownElement(WebElement dropDownElement) {
		Select ele = new Select(dropDownElement);
		return ele.getFirstSelectedOption().getText();
	}

	public void validateIndicativeCity() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox, "");
				Web.selectDropnDownOptionAsIndex(stateTextbox, "1");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(errorDiv, true)
						|| Web.isWebElementDisplayed(cityError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message \"City is required\" is displayed or not when no value enetred in city field ",
							"Error message displayed successfully with value: "
									+ cityError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message \"City is required\" is displayed or not when no value entered in city field",
							"Error message is not displayed", true);
				}

				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				cityTextbox.clear();
				Web.clickOnElement(stateTextbox);
				if (Web.isWebElementDisplayed(cityError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message \"City is required\" is displayed or not when city field is left blank ",
							"Error message displayed successfully with value: "
									+ cityError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message \"City is required\" is displayed or not when city field is left blank ",
							"Error message is not displayed", true);
				}

				if (cityTextbox.getAttribute("maxlength")
						.equalsIgnoreCase("20")) {
					Reporter.logEvent(Status.PASS,
							"Check if max Limit data entry is 20 bytes",
							"Max limit of Data entry is 20 bytes", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if max Limit data entry is 20 bytes",
							"Max limit of Data entry is not 20 bytes", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateIndicativePersonalEmail() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));

				if (personalEmailTextBox.getAttribute("maxlength")
						.equalsIgnoreCase("40")) {
					Reporter.logEvent(Status.PASS,
							"Check if max Limit data entry is 40 bytes",
							"Max limit of Data entry is 40 bytes", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if max Limit data entry is 40 bytes",
							"Max limit of Data entry is not 40 bytes", true);
				}

				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL INVALID1"));
				personalEmailTextBox.sendKeys(Keys.TAB);
				if (Web.isWebElementDisplayed(personalEmailError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message is displayed or not when data entered is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID1"),
							"Error message displayed successfully with value: "
									+ personalEmailError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message is displayed or not when data eneterd is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID1"),
							"Error message is not displayed", true);
				}
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL INVALID2"));
				personalEmailTextBox.sendKeys(Keys.TAB);
				if (Web.isWebElementDisplayed(personalEmailError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID2"),
							"Error message displayed successfully with value: "
									+ personalEmailError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID2"),
							"Error message is not displayed", true);
				}
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				personalEmailTextBox.sendKeys(Keys.TAB);
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Pre-Confirmation Enroll page is displayed or not on valid personal email: "
									+ Stock.GetParameterValue("PERSONAL EMAIL"),
							"Pre-Confirmation Enroll page displayed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Pre-Confirmation Enroll page is displayed or not on valid personal email: "
									+ Stock.GetParameterValue("PERSONAL EMAIL"),
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateIndicativeMobilePhone() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking mobile field is optional by checking if Pre-Confirmation Enroll page is displayed or not on not entering mobile phone details",
							"Pre-Confirmation Enroll page displayed successfully: Mobile phone field is Optional",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking mobile field is optional by checking if Pre-Confirmation Enroll page is displayed or not on not entering mobile phone details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
				Web.clickOnElement(backBtn);
				if (mobilePhoneTextBox.getAttribute("maxlength")
						.equalsIgnoreCase("10")) {
					Reporter.logEvent(Status.PASS,
							"Check if max Limit data entry is 10 numbers",
							"Max limit of Data entry is 10 numbers", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if max Limit data entry is 10 numbers",
							"Max limit of Data entry is not 10 numbers", true);
				}

				Web.setTextToTextBox(mobilePhoneTextBox,
						Stock.GetParameterValue("MOBILE PHONE INVALID"));
				mobilePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(homePhoneTextBox);
				if (Web.isWebElementDisplayed(mobilePhoneError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("MOBILE PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ mobilePhoneError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("MOBILE PHONE INVALID"),
							"Error message is not displayed", true);
				}

				Web.setTextToTextBox(mobilePhoneTextBox,
						Stock.GetParameterValue("MOBILE PHONE"));
				mobilePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(homePhoneTextBox);

				String regex = "\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}";
				if (mobilePhoneTextBox.getAttribute("value").matches(regex)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if mobile phone number is in the format (###)###-####",
							"Mobile phone number is in the format (###)###-#### with value: "
									+ mobilePhoneTextBox.getAttribute("value"),
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if mobile phone number is in the format (###)###-####",
							"Mobile phone number is not in the format (###)###-#### with value: "
									+ mobilePhoneTextBox.getAttribute("value"),
							true);
				}
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering mobile phone details",
							"Pre-Confirmation Enroll page displayed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering mobile phone details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateIndicativeHomePhone() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking mobile field is optional by checking if Pre-Confirmation Enroll page is displayed or not on not entering Home phone details",
							"Pre-Confirmation Enroll page displayed successfully: Home phone field is Optional",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking mobile field is optional by checking if Pre-Confirmation Enroll page is displayed or not on not entering Home phone details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
				Web.clickOnElement(backBtn);
				if (mobilePhoneTextBox.getAttribute("maxlength")
						.equalsIgnoreCase("10")) {
					Reporter.logEvent(Status.PASS,
							"Check if max Limit data entry is 10 numbers",
							"Max limit of Data entry is 10 numbers", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if max Limit data entry is 10 numbers",
							"Max limit of Data entry is not 10 numbers", true);
				}

				Web.setTextToTextBox(homePhoneTextBox,
						Stock.GetParameterValue("HOME PHONE INVALID"));
				homePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(mobilePhoneTextBox);
				if (Web.isWebElementDisplayed(homePhoneError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("HOME PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ homePhoneError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("HOME PHONE INVALID"),
							"Error message is not displayed", true);
				}

				Web.setTextToTextBox(homePhoneTextBox,
						Stock.GetParameterValue("HOME PHONE"));
				homePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(mobilePhoneTextBox);

				String regex = "\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}";
				if (homePhoneTextBox.getAttribute("value").matches(regex)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if home phone number is in the format (###)###-####",
							"Mobile phone number is in the format (###)###-#### with value: "
									+ homePhoneTextBox.getAttribute("value"),
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if home phone number is in the format (###)###-####",
							"Mobile phone number is not in the format (###)###-#### with value: "
									+ homePhoneTextBox.getAttribute("value"),
							true);
				}
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering home phone details",
							"Pre-Confirmation Enroll page displayed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering home phone details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateIndicative_ValidationErrorMessage()
			throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(addErrorMessages, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if error message is displayed when user DO NOT enter any field in Personal Information field and clicked Continue To Create New Account button",
							"Error message is displayed when user DO NOT enter any field in Personal Information field and clicked Continue To Create New Account button"
									+ addErrorMessages.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking if error message is displayed when user DO NOT enter any field in Personal Information field and clicked Continue To Create New Account button",
							"Error message is not displayed when user DO NOT enter any field in Personal Information field and clicked Continue To Create New Account button",
							true);
				}
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page displayed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateIndicativeData_InlineValidation()
			throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));

				Web.clickOnElement(mobilePhoneTextBox);
				Web.clickOnElement(homePhoneTextBox);
				Web.clickOnElement(workPhoneTextBox);
				Web.clickOnElement(internationalPhoneTextBox);
				Web.clickOnElement(contToCreateNewAccountBtn);

				if (Web.isWebElementDisplayed(addErrorMessages, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking Error messages when no phone details are entered",
							"Error message is displayed successfully"
									+ addErrorMessages.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Error messages when no phone details are entered",
							"Error message is not displayed successfully"
									+ addErrorMessages.getText(), true);
				}

				Web.setTextToTextBox(mobilePhoneTextBox,
						Stock.GetParameterValue("MOBILE PHONE INVALID"));
				mobilePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(homePhoneTextBox);
				if (Web.isWebElementDisplayed(mobilePhoneError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("MOBILE PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ mobilePhoneError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("MOBILE PHONE INVALID"),
							"Error message is not displayed", true);
				}
				mobilePhoneTextBox.clear();

				Web.setTextToTextBox(homePhoneTextBox,
						Stock.GetParameterValue("HOME PHONE INVALID"));
				homePhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(mobilePhoneTextBox);
				if (Web.isWebElementDisplayed(homePhoneError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("HOME PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ homePhoneError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("HOME PHONE INVALID"),
							"Error message is not displayed", true);
				}
				homePhoneTextBox.clear();

				Web.setTextToTextBox(workPhoneTextBox,
						Stock.GetParameterValue("WORK PHONE INVALID"));
				workPhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(internationalPhoneTextBox);
				if (Web.isWebElementDisplayed(workPhoneError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message is displayed or not when data entered is: "
									+ Stock.GetParameterValue("WORK PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ workPhoneError.getText(), true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Error message  is displayed or not",
							"Error message is not displayed", true);
				}
				workPhoneTextBox.clear();

				Web.selectDropnDownOptionAsIndex(internationalPhoneDropDown,
						"2");
				Web.setTextToTextBox(internationalPhoneTextBox,
						Stock.GetParameterValue("INTERNATIONAL PHONE INVALID"));
				internationalPhoneTextBox.sendKeys(Keys.TAB);
				// Web.clickOnElement(mobilePhoneTextBox);
				if (Web.isWebElementDisplayed(internationalPhoneNumberError,
						true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message is displayed or not when data entered is: "
									+ Stock.GetParameterValue("INTERNATIONAL PHONE INVALID"),
							"Error message displayed successfully with value: "
									+ internationalPhoneNumberError.getText(),
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message  is displayed or not when data entered is: "
									+ Stock.GetParameterValue("INTERNATIONAL PHONE INVALID"),
							"Error message is not displayed", true);
				}
				internationalPhoneTextBox.clear();

			}
		}
	}

	public void validateConfirmationPage() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page displayed successfully with the details:"
									+ "\nFirst Name: "
									+ preConfirmFirstName.getText()
									+ "\nLast Name: "
									+ preConfirmLastName.getText()
									+ "\nDOB: "
									+ preConfirmDOB.getText()
									+ "\nSSN: "
									+ preConfirmSSN.getText()
									+ "\nGender: "
									+ preConfirmGender.getText()
									+ "\nMarital Status: "
									+ preConfirmMaritalStatus.getText()
									+ "\nAddress Line1: "
									+ preConfirmAddrLine1.getText()
									+ "\nAddress Line2: "
									+ preConfirmAddrLine2.getText()
									+ "\nCity: "
									+ preConfirmCity.getText()
									+ "\nState: "
									+ preConfirmState.getText()
									+ "\nZip Code: "
									+ preConfirmZipCode.getText()
									+ "\nCountry: "
									+ preConfirmCountry.getText()
									+ "\nPersonal Email: "
									+ preConfirmPersonalEmail.getText()
									+ "\nMobile Phone: "
									+ preConfirmMobile.getText()
									+ "\nHome Phone: "
									+ preConfirmHome.getText()
									+ "\nWork Phone: "
									+ preConfirmWork.getText()
									+ "\nInternational Phone: "
									+ preConfirmIntlPhone.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateClearButton() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Reporter.logEvent(
						Status.INFO,
						"Entering all the details in Personal Information section",
						"All the details in Personal Information section are entered",
						true);
				Web.clickOnElement(clearButton);
				if (Web.isWebElementDisplayed(yesClear, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if pop up box with message\"You have selected to clear all personal information data. \"Do you wish to continue? With Yes and No button\" is displayed or not",
							"Pop up message box is displayed", true);
					Web.clickOnElement(noClear);
					Reporter.logEvent(Status.INFO, "Clicking on \"No\" button",
							"\"No\" button has been clicked", false);
					if (Web.isWebElementDisplayed(contToCreateNewAccountBtn,
							true)) {
						Reporter.logEvent(
								Status.PASS,
								"Verifying page has returned to data entry page with \"Continue to Create Account\" button",
								"Page has returned to data entry page with \"Continue to Create Account\" button",
								true);

						Web.clickOnElement(clearButton);
						Web.clickOnElement(yesClear);
						Reporter.logEvent(Status.INFO,
								"Clicking on \"Yes\" button",
								"\"Yes\" button has been clicked", false);
						if (firstNameTextbox.getText().equalsIgnoreCase("")
								&& lastNameTextbox.getText().equalsIgnoreCase(
										"")) {
							Reporter.logEvent(
									Status.PASS,
									"Checking all indicative data are cleared or not",
									"All indicative data are cleared"
											+ "\nFirst Name: "
											+ firstNameTextbox.getText()
											+ "\nLast Name: "
											+ lastNameTextbox.getText()
											+ "\nDOB: "
											+ DateOfBirthTextbox.getText()
											+ "\nAddress Line1: "
											+ addressLine1.getText()
											+ "\nCity: "
											+ cityTextbox.getText()
											+ "\nState: "
											+ getDropDownElement(stateTextbox)
											+ "\nZip Code: "
											+ zipCodeTextBox.getText()
											+ "\nCountry: "
											+ getDropDownElement(countryTextbox)
											+ "\nPersonal Email: "
											+ personalEmailTextBox.getText(),
									false);
						} else {
							Reporter.logEvent(
									Status.PASS,
									"Checking all indicative data are cleared or not",
									"All indicative data are not cleared",
									false);
						}

					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verifying page has returned to data entry page with \"Continue to Create Account\" button",
								"Page has not returned to data entry page with \"Continue to Create Account\" button",
								true);
					}

				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if pop up box with message\"You have selected to clear all personal information data. \"Do you wish to continue? With Yes and No button\" is displayed or not",
							"Pop up message box is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void validateErrorMsgPersonalPhoneNumber()
			throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {
			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				fillTheFieldesinOnlineEnrollmentForm();

				// enter invalid data into personal email field and validate
				// error message.
				personalEmailTextBox.clear();
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL INVALID1"));
				personalEmailTextBox.sendKeys(Keys.TAB);
				if (Web.isWebElementDisplayed(personalEmailError, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Error message is displayed or not when data entered is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID1"),
							"Error message displayed successfully with value: "
									+ personalEmailError.getText(), true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Error message is displayed or not when data eneterd is: "
									+ Stock.GetParameterValue("PERSONAL EMAIL INVALID1"),
							"Error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	/**
	 * Method to verify AddressLine and AddressLine2
	 * 
	 * @throws InterruptedException
	 */
	public void validateErrorMsgAddressLine1AndAddressLine2()
			throws InterruptedException {
		try {
			if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

				if (Web.isWebElementDisplayed(planFoundLabel, true)) {
					fillTheFieldesinOnlineEnrollmentForm();

					// Verify addressLine1 is mandatory field.
					addressLine1.clear();
					addressLine1.sendKeys(Keys.TAB);
					CommonLib
							.verifyIfWebElementPresent(
									addressLine1Error,
									"check if Error message is displayed or not when data not entered: ",
									true);

					// Verify addressLine1 and addressLine2 values should not be
					// match.
					String addressLine1andLine2 = Stock
							.GetParameterValue("ADDR LINE1 INVALID1");
					String addressLine2Value = Stock
							.GetParameterValue("ADDR LINE2");
					Web.setTextToTextBox(addressLine1, addressLine1andLine2);
					Web.setTextToTextBox(addressLine2, addressLine1andLine2);
					addressLine2.sendKeys(Keys.TAB);
					CommonLib.verifyIfWebElementPresent(addressLine2Error,
							"check if Error message is displayed or not when same data entered is: "
									+ addressLine1andLine2, true);

					// click on continuetocreate button.
					addressLine2.clear();
					Web.setTextToTextBox(addressLine2, addressLine2Value);
					addressLine2.sendKeys(Keys.TAB);
					addressLine2Error.click();
					Web.clickOnElement(contToCreateNewAccountBtn);

					// Verify AddressLine1 and AddressLine2 fields length should
					// not be exceed more than 35Bytes.
					CommonLib.verifyIfWebElementTextPresent(
							preConfirmAddLineOne,
							Stock.GetParameterValue("ADDR LINE1 EXPECTED"),"ADDR LINE1 field value should match with expected text length.");
					CommonLib.verifyIfWebElementTextPresent(
							preConfirmAddLineTwo,
							Stock.GetParameterValue("ADDR LINE2 EXPECTED"),"ADDR LINE2 field value should match with expected text length.");
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Create Account page is displayed or not",
							"create Account Enrollment didn't get displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"Create Account page didn't get displayed successfully",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to verify Date of hire field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void validateErrorMsgDateOfHire() throws InterruptedException {
		try {
			if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

				if (Web.isWebElementDisplayed(planFoundLabel, true)) {
					fillTheFieldesinOnlineEnrollmentForm();

					// Verify Date of hire is mandatory field.
					dateOfHireTextBox.clear();
					Web.clickOnElement(doneButton);
					CommonLib
							.verifyIfWebElementPresent(
									dateOfHireError,
									"check if Error message is displayed or not when data not entered to the field: ",
									true);

					// Verify invalid date format for the field Date of hire
					Web.setTextToTextBox(dateOfHireTextBox,
							Stock.GetParameterValue("DOHInvalid1"));
					Web.clickOnElement(doneButton);
					CommonLib.verifyIfWebElementPresent(dateOfHireError,
							"check if Error message is displayed or not when data entered is: "
									+ Stock.GetParameterValue("DOHInvalid1"),
							true);

					// verify future date verification for the field Date of
					// hire.
					Web.setTextToTextBox(dateOfHireTextBox,
							Stock.GetParameterValue("DOHFutureDate"));
					Web.clickOnElement(doneButton);
					dateOfHireError.click();
					CommonLib.verifyIfWebElementTextPresent(dateOfHireError,
							Stock.GetParameterValue("DOHFutureDateErrorMsg"),"DateOfHire field error should display on UI.");
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Create Account page is displayed or not",
							"create Account Enrollment didn't get displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"Create Account page didn't get displayed successfully",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to verify end to end testing with alphanumeric GroupID.
	 * 
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	public void verifyEndToEndTestingWithoutAlphaNumaricOfGId()
			throws InterruptedException, SQLException {
		fillTheFieldesinOnlineEnrollmentForm();
		Web.clickOnElement(contToCreateNewAccountBtn);
		MobileNumber = preConfirmMobile.getText();
		Web.waitForElement(createAccount);
		Web.clickOnElement(createAccount);

		// Connect to the respective data base get enrollment details.
		Web.waitForElement(confirmationNumber);
		String confirmationNumberText = confirmationNumber.getText();
		String confirmationNumber = confirmationNumberText.substring(59);
		if (confirmationNumber != null) {
			Reporter.logEvent(Status.PASS, "Enrollment should be successful",
					confirmationNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enrollment should be successful",
					"Enrollment not success " + confirmationNumber, true);
		}

		HashMap<String, String> personelDetails = getPersonelDetails(SSN, null);
		String IndID = personelDetails.get("IndId");
		HashMap<String, String> addressList = getAddressDetails(IndID, null);

		HashMap<String, String> contactAndAddressList = getContactAndEmploymentDetails(
				IndID, true, null);
		String confirmationNumberFromDB = getConfirmationNumber(
				confirmationNumber, null);

		// Verify UI enrollment confirmation number with database value.
		CommonLib.verifyExpectedAndActualEual(confirmationNumber,
				confirmationNumberFromDB,
				"Expected EvenID should be exist on database");

		// Verify data base populated enrolled participant personal,address
		// employment details.
		verifyPersonelDetails(personelDetails);
		verifyAddressDetails(addressList);
		verifyEmailAndEmploymentDetails(contactAndAddressList, true);
	}

	/**
	 * Method to verify end to end testing with numeric GroupID.
	 * 
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	public void verifyEndToEndTestingWithAlphaNumaricOfGId()
			throws InterruptedException, SQLException {

		// Enter GrpId with AlphaNumaric value.
		fillTheDataInGrpIdAndPECAndSSN(
				Stock.GetParameterValue("GrpIdWithAlphaNumaric"),
				Stock.GetParameterValue("PEC"));

		fillTheFieldesinOnlineEnrollmentForm();
		Web.clickOnElement(contToCreateNewAccountBtn);
		MobileNumber = preConfirmMobile.getText();
		Web.waitForElement(createAccount);
		Web.clickOnElement(createAccount);

		// Connect to the respective data base get enrollment details.
		Web.waitForElement(confirmationNumber);
		String confirmationNumberText = confirmationNumber.getText();
		String confirmationNumber = confirmationNumberText.substring(59);
		if (confirmationNumber != null) {
			Reporter.logEvent(Status.PASS, "Enrollment should be successful",
					confirmationNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enrollment should be successful",
					"Enrollment not success " + confirmationNumber, true);
		}
		String DB = Stock.GetParameterValue("DB");
		HashMap<String, String> personelDetails = getPersonelDetails(SSN, DB);
		String IndID = personelDetails.get("IndId");
		System.out.println("personel details: " + personelDetails);
		HashMap<String, String> addressList = getAddressDetails(IndID, DB);

		HashMap<String, String> contactAndAddressList = getContactAndEmploymentDetails(
				IndID, false, DB);
		String confirmationNumberFromDB = getConfirmationNumber(
				confirmationNumber, DB);

		// Verify UI enrollment confirmation number with database value.
		CommonLib.verifyExpectedAndActualEual(confirmationNumber,
				confirmationNumberFromDB,
				"Expected EvenID should be exist on database");

		// Verify data base populated enrolled participant personal,address
		// employment details.
		verifyPersonelDetails(personelDetails);
		verifyAddressDetails(addressList);
		verifyEmailAndEmploymentDetails(contactAndAddressList, false);

	}

	/**
	 * Method to verify Date of hire field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void validateDivisionField() {
		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {

				// Verify division dropdown default value.
				Web.waitForElement(defaultDivision);
				CommonLib.verifyIfWebElementPresent(defaultDivision,
						"Default division present", true);

				// Verify Division field error message.
				Web.clickOnElement(divisionDropDown);
				addressLine1.click();
				CommonLib.verifyIfWebElementTextPresent(divisionError,
						Stock.GetParameterValue("DivisionError"),"Division Field Error Should Display On UI.");

			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	/**
	 * Method to verifydivision field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void verifyDivisionFieldValueOnConfirmationPage()
			throws InterruptedException {
		fillTheFieldesinOnlineEnrollmentForm();
		Web.clickOnElement(contToCreateNewAccountBtn);

		// Verify division field value on confirmation page.
		CommonLib.verifyIfWebElementTextPresent(preDivision,
				Stock.GetParameterValue("DivisionValue"),"Expected pre division value should displayed on UI.");

	}

	/**
	 * Method to verifydivision field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void verifyDivisionFieldHasMoreThanOneValue()
			throws InterruptedException {
		try {
			// Click on dropdown and get options.
			Web.clickOnElement(divisionDropDown);
			Boolean b = options.isEmpty();
			List<String> optionsText = new ArrayList<String>();
			if (b == false) {
				for (int i = 0; i < options.size(); i++) {
					String optionValue = options.get(i).getAttribute(
							"data-value");
					optionsText.add(optionValue);
				}
				if (optionsText.size() > 1) {
					System.out.println(optionsText);
					Reporter.logEvent(Status.PASS,
							"Check if division field has multiple options ",
							"division field has multiple options", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if division field has multiple options ",
							"division field has multiple options", true);
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if division field has multiple options ",
						"division field has multiple options", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Method to verifydivision field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void verifyNoDivisionDisplayedOnConfirmationPage()
			throws InterruptedException {

		// Verify division field not displayed on online enrollment page.
		Boolean isDivisionExist = divisionDropDown.isDisplayed();
		if (isDivisionExist) {
			Reporter.logEvent(
					Status.FAIL,
					"Division field should not displayed in online enrollment.",
					"division field is displayed: " + divisionDropDown, true);
		} else {
			Reporter.logEvent(
					Status.PASS,
					"Division field should not displayed in online enrollment.",
					"division field not displayed: " + divisionDropDown, false);
		}

		// Verify on confirmation page division should not displayed.
		Boolean isDivisionValueExist = Web.isWebElementDisplayed(preDivision,
				false);
		if (isDivisionValueExist) {
			Reporter.logEvent(
					Status.PASS,
					"Division field should not displayed in online enrollment.",
					"division field not displayed: " + divisionDropDown, false);
		} else {
			Reporter.logEvent(
					Status.PASS,
					"Division field should not displayed in online enrollment.",
					"division field not displayed: " + divisionDropDown, false);
			fillTheFieldesinOnlineEnrollmentForm();
		}
	}

	public void verifyEnableSelectionOfDivision() throws InterruptedException,
			SQLException {
		// Verify division field not displayed on online enrollment page.
		Boolean isDivisionExist = Web.isWebElementDisplayed(divisionDropDown,
				false);
		// Boolean isDivisionExist = divisionDropDown.isDisplayed();
		if (isDivisionExist) {
			Reporter.logEvent(
					Status.FAIL,
					"Division field  should not be displayed on a online enrollment page.",
					"Division field is displayed", true);
		} else {
			Reporter.logEvent(
					Status.PASS,
					"Division field  should not be displayed on a online enrollment page.",
					"division field not displayed", false);
		}

		// Enter group id where division information is available.
		Web.mouseHover(menuAddtlResources);
		if (Web.isWebElementDisplayed(menuAddtlResources)) {
			Web.clickOnElement(createNewAcct);
			Web.waitForElement(planNumberLabel);
			if (Web.isWebElementDisplayed(onlineEnrollment, true)) {
				// Enter GrpId with AlphaNumaric value.
				fillTheDataInGrpIdAndPECAndSSN(
						Stock.GetParameterValue("GrpIdWithDivision"),
						Stock.GetParameterValue("PEC"));

				// Verify division field displayed on online enrollment page.
				isDivisionExist = divisionDropDown.isDisplayed();
				if (isDivisionExist) {
					Reporter.logEvent(
							Status.PASS,
							"Check if division field not displayed on a online enrollment page or not",
							"division field is displayed", true);
					verifyDivisions();
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if division field not displayed on a online enrollment page or not",
							"division field not displayed", false);
				}

			}
		}
	}

	public void fillTheDataInGrpIdAndPECAndSSN(String grpID, String PEC) {
		Web.setTextToTextBox(planNumberTextbox, grpID);
		Web.setTextToTextBox(planEnrollmentCodeTextbox, PEC);
		long randomNumber = CommonLib.getRandomNumber(9);
		SSN = Long.toString(randomNumber);
		Web.setTextToTextBox(ssnSearchedTextbox, SSN);
		ssnSearchedTextbox.sendKeys(Keys.TAB);
		Web.clickOnElement(submitBtn);
		Web.waitForElement(planNumberLabel);
		Web.waitForElement(onlineEnrollment);

		if (Web.isWebElementDisplayed(onlineEnrollment, true)) {
			Reporter.logEvent(Status.PASS,
					"Check if Online Enrollment page displayed or not",
					"Online Enrollment page displyed successfully", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Online Enrollment page displayed or not",
					"Online Enrollment didn't get displayed successfully", true);
		}

	}

	/**
	 * Method to get enabled division names from data base.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, String>> getEnabledDivisionDetails()
			throws SQLException {
		ResultSet res = DB.executeQuery(
				Stock.getTestQuery("getEnabledDivisionDetails")[0],
				Stock.getTestQuery("getEnabledDivisionDetails")[1]);
		ArrayList<HashMap<String, String>> enabledDivisionDetails = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> divisionData = new HashMap<String, String>();
		String basis = "";
		String name = "";
		String value = "";

		if (res != null) {
			while (res.next()) {
				basis = res.getString("BASIS");
				divisionData.put("Basis", basis);
				name = res.getString("NAME");
				divisionData.put("Name", name);
				value = res.getString("VALUE");
				divisionData.put("Value", value);
				enabledDivisionDetails.add(divisionData);
				divisionData.remove(0);
			}
		}

		return enabledDivisionDetails;

	}

	public void verifyDivisions() throws SQLException {
		// Get division data from data base.
		ArrayList<HashMap<String, String>> divisions = getEnabledDivisionDetails();

		// Check if division field is displayed on UI or not.
		Web.clickOnElement(divisionDropDown);
		Boolean b = options.isEmpty();
		if (b == false) {
			// Verify UI division text with database value.
			for (int i = 0; i < options.size(); i++) {
				HashMap<String, String> map = divisions.get(i);
				String divisionText = options.get(i).getText();
				if (divisionText.contains(map.get("Basis"))
						|| divisionText.contains(map.get("Name"))
						|| divisionText.contains(map.get("Value"))) {
					Reporter.logEvent(Status.PASS,
							"Dvision field drop down value in UI should matched with data base value.  "
									+ divisionText, divisionText, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Dvision field drop down value in UI should matched with data base value.  "
									+ divisionText, divisionText, true);
				}
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Dvision field should have options "
					+ divisionDropDown, "Division field is empty :"
					+ divisionDropDown, true);
		}
	}

	public void verifyGenderButton() throws InterruptedException {
		// fill the all the data except gender and click on continue button.
		fillTheFieldesinOnlineEnrollmentForm();;
		Web.clickOnElement(contToCreateNewAccountBtn);
		
		// Verify Gender field is mandatory field.
		CommonLib.verifyExpectedAndActualEual(
				Stock.GetParameterValue("ERROR MESSAGE"), errorMsg.getText(),
				"Expected gender error messge should be displayed in UI");

		// Verify displayed Female/Male on a page.
			for (int i = 0; i < gender.size(); i++) {
				if (i == 0) {
					CommonLib.verifyExpectedAndActualEual("Female",Web.getWebElementText(gender.get(0)),"Expected gendershould be displayed in UI");

				} else if (i == 1) {
					CommonLib.verifyExpectedAndActualEual("Male", Web.getWebElementText(gender.get(1)),
							"Expected gendershould be displayed in UI");

			}
		}

	}

	public void verifyAnnualIncomeField() throws InterruptedException {
		// Click on annual income field and should not enter data verify error
		// message.
		Web.setTextToTextBox(annualIncomeTextBox, Keys.TAB);
		Web.clickOnElement(annualIncomeError);

		// Verify error message is displayed.
		CommonLib
				.verifyExpectedAndActualEual(
						Stock.GetParameterValue("DefaultErrorMessage"),Web.getWebElementText(annualIncomeError),
						"Expected annual income field error messge should be displayed in UI");

		// Verify if you enter alpha numeric it will display error message.
		// Verify error message is displayed.
		Web.setTextToTextBox(annualIncomeTextBox,
				Stock.GetParameterValue("AlphaNumaricAnnualIncome"));
		//Web.setTextToTextBox(annualIncomeTextBox, Keys.TAB);
		Web.clickOnElement(annualIncomeError);
		CommonLib
				.verifyExpectedAndActualEual(
						Stock.GetParameterValue("InvalidDataErrorMessage"),
						Web.getWebElementText(annualIncomeError),
						"Expected annual income field error messge should be displayed in UI");

		// Verify if you enter 100 to text box it will add decimal.
		Web.setTextToTextBox(annualIncomeTextBox,"100");
		//Web.setTextToTextBox(annualIncomeTextBox, Keys.TAB);
		CommonLib
				.verifyFieldValue(annualIncomeTextBox, "$100.00",
						"Actual Annual income field value should be match with expected.");

		// Verify if you enter 100 to text box it will add decimal.
		annualIncomeTextBox.clear();
		Web.setTextToTextBox(annualIncomeTextBox,"1000");
		//Web.setTextToTextBox(annualIncomeTextBox, Keys.TAB);
		CommonLib
				.verifyFieldValue(annualIncomeTextBox, "$1,000.00",
						"Actual Annual income field value should be match with expected.");
	}

	public void verifyMaritalStatusField() throws InterruptedException {
		fillTheFieldesinOnlineEnrollmentForm();
		Web.clickOnElement(contToCreateNewAccountBtn);

		// Verify error message is displayed.
		CommonLib
				.verifyExpectedAndActualEual(
						Stock.GetParameterValue("MaritalStatusErrorMessage"),Web.getWebElementText(errorMsg),
						"Expected marital status error messge should be displayed in UI");

	}

	/**
	 * Method to verifydivision field validation.
	 * 
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	public void verifyEndToEndTestingWithDivisionField()
			throws InterruptedException, SQLException {
		// Fill all the details in online enrollment form and enroll a
		// participant.

		fillTheFieldesinOnlineEnrollmentForm();
		Web.clickOnElement(contToCreateNewAccountBtn);
		MobileNumber = preConfirmMobile.getText();
		Web.waitForElement(createAccount);
		Web.clickOnElement(createAccount);

		// Connect to the respective data base get enrollment details.
		Web.waitForElement(confirmationNumber);
		String confirmationNumberText = confirmationNumber.getText();
		String confirmationNumber = confirmationNumberText.substring(59);
		if (confirmationNumber != null) {
			Reporter.logEvent(Status.PASS, "Enrollment should be successful",
					confirmationNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enrollment should be successful",
					"Enrollment not success " + confirmationNumber, true);
		}

		HashMap<String, String> personelDetails = getPersonelDetails(SSN, null);
		String IndID = personelDetails.get("IndId");
		System.out.println("personel details: " + personelDetails);
		HashMap<String, String> addressList = getAddressDetails(IndID, null);

		HashMap<String, String> contactAndAddressList = getContactAndEmploymentDetails(
				IndID, true, null);
		String confirmationNumberFromDB = getConfirmationNumber(
				confirmationNumber, null);

		// Verify UI enrollment confirmation number with database value.
		CommonLib.verifyExpectedAndActualEual(confirmationNumber,
				confirmationNumberFromDB,
				"Expected EvenID should be exist on database");

		// Verify data base populated enrolled participant personal,address
		// employment details.
		verifyPersonelDetails(personelDetails);
		verifyAddressDetails(addressList);
		verifyEmailAndEmploymentDetails(contactAndAddressList, true);
		
		// Go to search page and verify enrolled participant details.

		menuSearch.click();
		participantHomeObj = new ParticipantHome().get();
		participantHomeObj.verifyEnrolledParticipantDetails(IndID,SSN);
		
		
	}
	public void verifyPersonelDetails(HashMap<String, String> hm) {
		try {
			// Verify personelDetail
			CommonLib.verifyExpectedAndActualEual(
					Stock.GetParameterValue("FIRST NAME"), hm.get("FirstName"),
					"Expected FirstName should exist on data base.");
			CommonLib.verifyExpectedAndActualEual(
					Stock.GetParameterValue("LAST NAME"), hm.get("LastName"),
					"Expected LastName should exist on data base.");

			// Verify enrolled participant date with database populated value.
			Boolean b = CommonLib.compareDB_Date_With_Web_Date(
					hm.get("BirthDate"), Stock.GetParameterValue("DOB"));
			if (b) {
				Reporter.logEvent(Status.PASS,
						"Date Of Birth should exist on data base.",
						hm.get("BirthDate"), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Date Of Birth should exist on data base.",
						"Date Of Birth does not matched with data base value:"
								+ hm.get("BirthDate"), true);
			}

			CommonLib.verifyExpectedAndActualEual("M", hm.get("SEX"),
					"Expected Gender should exist on data base.");
			CommonLib.verifyExpectedAndActualEual("S", hm.get("MaritalStatus"),
					"Expected MaritalStatus should exist on data base.");
			String actualMobilenumber = hm.get("MobileNumber");
			if (MobileNumber.contains(actualMobilenumber)) {
				Reporter.logEvent(Status.PASS,
						"Registred mobile number should exist on data base",
						"Registred mobile number matched with data base populated number: "
								+ MobileNumber, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Registred mobile number should exist on data base",
						"Registred mobile number does not matched with data base populated number:"
								+ MobileNumber, true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyAddressDetails(HashMap<String, String> hm) {
		// Verify Address Details
		CommonLib.verifyExpectedAndActualEual(
				Stock.GetParameterValue("ADDR LINE1"), hm.get("FirstLine"),
				"Expected Address should exist on data base.");
		CommonLib.verifyExpectedAndActualEual(Stock.GetParameterValue("CITY"),
				hm.get("City"), "Expected City should exist on data base.");
		CommonLib.verifyExpectedAndActualEual(
				Stock.GetParameterValue("ZIP CODE"), hm.get("ZipCode"),
				"Expected ZipCode should exist on data base.");
		CommonLib.verifyExpectedAndActualEual("CO", hm.get("State"),
				"Expected State should exist on data base.");
		CommonLib.verifyExpectedAndActualEual("US", hm.get("Country"),
				"Expected Country should exist on data base.");
	}

	public void verifyEmailAndEmploymentDetails(HashMap<String, String> hm,
			boolean isDivision) {
		try {
			CommonLib.verifyExpectedAndActualEual(
					Stock.GetParameterValue("PERSONAL EMAIL"),
					hm.get("EmailAddress"),
					"Expected Email Address should exist on data base.");
			CommonLib.compareDB_Date_With_Web_Date(hm.get("HireDate"),
					Stock.GetParameterValue("DOH"));
			if (isDivision) {
				CommonLib.verifyExpectedAndActualEual("D",
						hm.get("DivisionCode"),
						"Expected Division Code should exist on data base.");
				CommonLib.verifyExpectedAndActualEual("5",
						hm.get("DivisionValue"),
						"Expected Division Value should exist on data base.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getConfirmationNumber(String EventId, String isDatabaseName)
			throws SQLException {
		if (isDatabaseName == null) {
			DBName = Stock.getTestQuery("getEventDetails")[0];
		} else {
			DBName = isDatabaseName;
		}
		ResultSet res = DB.executeQuery(DBName,
				Stock.getTestQuery("getEventDetails")[1], EventId);
		String confirmationNumber = "";
		if (res != null) {
			while (res.next()) {
				confirmationNumber = res.getString("ID");

			}
		}

		return confirmationNumber;

	}

	public HashMap<String, String> getPersonelDetails(
			String confirmationNumber, String isDatabaseName)
			throws SQLException {
		HashMap<String, String> personelList = new HashMap<String, String>();
		if (isDatabaseName == null) {
			DBName = Stock.getTestQuery("getParticipantPersonelDetails")[0];
		} else {
			DBName = isDatabaseName;
		}

		ResultSet res = DB.executeQuery(DBName,
				Stock.getTestQuery("getParticipantPersonelDetails")[1],
				confirmationNumber);

		if (res != null) {

			while (res.next()) {
				String Ind_ID = res.getString("ID");
				personelList.put("IndId", Ind_ID);
				String firstName = res.getString("FIRST_NAME");
				personelList.put("FirstName", firstName);
				String lastName = res.getString("LAST_NAME");
				personelList.put("LastName", lastName);
				String birthDate = res.getString("BIRTH_DATE");
				personelList.put("BirthDate", birthDate);
				String sex = res.getString("SEX");
				personelList.put("SEX", sex);
				String maritalStatus = res.getString("MARITAL_STATUS");
				personelList.put("MaritalStatus", maritalStatus);
				String mobileNumber = res.getString("MOBILE_PHONE_NBR");
				personelList.put("MobileNumber", mobileNumber);
			}
		}
		return personelList;

	}

	public HashMap<String, String> getAddressDetails(String IndId,
			String isDatabaseName) throws SQLException {
		HashMap<String, String> addressList = new HashMap<String, String>();
		if (isDatabaseName == null) {
			DBName = Stock.getTestQuery("getParticipantAddressDetails")[0];
		} else {
			DBName = isDatabaseName;
		}

		ResultSet res = DB.executeQuery(DBName,
				Stock.getTestQuery("getParticipantAddressDetails")[1], IndId);

		if (res != null) {
			while (res.next()) {
				String firstLine = res.getString("FIRST_LINE_MAILING");
				addressList.put("FirstLine", firstLine);
				String city = res.getString("CITY");
				addressList.put("City", city);
				String zipCode = res.getString("ZIP_CODE");
				addressList.put("ZipCode", zipCode);
				String state = res.getString("STATE_CODE");
				addressList.put("State", state);
				String country = res.getString("COUNTRY");
				addressList.put("Country", country);

			}
		}
		return addressList;

	}

	public HashMap<String, String> getContactAndEmploymentDetails(String IndId,
			boolean isDivisionRequired, String isDatabaseName)
			throws SQLException {
		HashMap<String, String> contactAndEmploymentList = new HashMap<String, String>();
		if (isDatabaseName == null) {
			DBName = Stock.getTestQuery("getEmailDetails")[0];
		} else {
			DBName = isDatabaseName;
		}
		ResultSet emailDetails = null;
		emailDetails = DB.executeQuery(DBName,
				Stock.getTestQuery("getEmailDetails")[1], IndId);

		if (emailDetails != null) {
			while (emailDetails.next()) {
				String emailAddress = emailDetails.getString("EMAIL_ADDRESS");
				contactAndEmploymentList.put("EmailAddress", emailAddress);
			}
		}
		if (isDivisionRequired) {
			ResultSet divisionDetails = DB.executeQuery(
					Stock.getTestQuery("getDivisionDetails")[0],
					Stock.getTestQuery("getDivisionDetails")[1], IndId);

			if (divisionDetails != null) {
				while (divisionDetails.next()) {
					String divisionCode = divisionDetails
							.getString("GCS_BASIS");
					contactAndEmploymentList.put("DivisionCode", divisionCode);
					String divisionValue = divisionDetails
							.getString("GCS_VALUE");
					contactAndEmploymentList
							.put("DivisionValue", divisionValue);
						
				}
			}
		}

		ResultSet res = DB.executeQuery(DBName,
				Stock.getTestQuery("getEmploymentDetails")[1], IndId);

		if (res != null) {
			while (res.next()) {
				String hireDate = res.getString("HIRE_DATE");
				contactAndEmploymentList.put("HireDate", hireDate);

			}
		}
		return contactAndEmploymentList;
	}

	/**
	 * Method to fill all the details in online enrollment form.
	 * 
	 * @throws InterruptedException
	 */
	private void fillTheFieldesinOnlineEnrollmentForm() throws InterruptedException {

		try {
			// Fill personal information fields
			Web.setTextToTextBox(firstNameTextbox,
					Stock.GetParameterValue("FIRST NAME"));
			Web.setTextToTextBox(lastNameTextbox,
					Stock.GetParameterValue("LAST NAME"));
			Web.setTextToTextBox(DateOfBirthTextbox,
					Stock.GetParameterValue("DOB"));
			DateOfBirthTextbox.sendKeys(Keys.TAB);
			String gender = Stock.GetParameterValue("Gender");
			if (gender.equalsIgnoreCase("M")) {
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(2000);
				genderRadioBtns.get(1).click();
			}else if (gender.equalsIgnoreCase("F")) {
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(2000);
				genderRadioBtns.get(0).click();
			}
			String maritialStatus = Stock.GetParameterValue("MaritialStatus");
			if (maritialStatus.equalsIgnoreCase("M")) {
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(2000);
				maritalStatusRadioBtns.get(0).click();
			}
			else if (maritialStatus.equalsIgnoreCase("S")) {
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(2000);
				maritalStatusRadioBtns.get(1).click();
			}

			// Fill employment information
			Web.setTextToTextBox(annualIncomeTextBox,
					Stock.GetParameterValue("ANNUAl INCOME"));
			Web.setTextToTextBox(dateOfHireTextBox,
					Stock.GetParameterValue("DOH"));
			Web.clickOnElement(doneButton);

			// Check if division field displayed are not.
			Boolean isexist = Web.isWebElementDisplayed(divisionDropDown, true);

			if (isexist) {
				// Select division from division drop down.
				Web.clickOnElement(divisionDropDown);
				Actions a = new Actions(Web.getDriver());
				a.moveToElement(selectItem).click().build().perform();
			}

			// Fill Mailing address
			Web.setTextToTextBox(addressLine1,
					Stock.GetParameterValue("ADDR LINE1"));
			Web.setTextToTextBox(addressLine2,
					Stock.GetParameterValue("ADDR LINE2"));
			Web.setTextToTextBox(cityTextbox, Stock.GetParameterValue("CITY"));
			String state= Stock.GetParameterValue("STATE");
			Web.selectDropDownOption(stateDropDown,state, true);
			Web.setTextToTextBox(zipCodeTextBox,
					Stock.GetParameterValue("ZIP CODE"));

			// Fill Contact Information.
			Web.setTextToTextBox(personalEmailTextBox,
					Stock.GetParameterValue("PERSONAL EMAIL"));
			Web.setTextToTextBox(mobilePhoneTextBox,
					Stock.GetParameterValue("MOBILE PHONE"));
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to verify error message validation for GrpId,PEC and SSN.
	 * 
	 * @throws InterruptedException
	 */
	public void verifyValidIndicativeDateFor_GrpId_PEC_SSN()
			throws InterruptedException {
		try {
			// Construct GrpId,PEC and SSN values.
			String GrpID = Stock.GetParameterValue("GrpId");
			String PlanEnrollCode = Stock.GetParameterValue("PEC");
			String SSN = Stock.GetParameterValue("SSN");

			// Check if Submit button is enabled or not.
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter Invalid data in GrpId field verify error message and
			// whether submit button is enabled or not.
			Web.setTextToTextBox(planNumberTextbox,
					Stock.GetParameterValue("InvalidGrpId"));
			planNumberTextbox.sendKeys(Keys.TAB);
			CommonLib.verifyIfWebElementTextPresent(planNumberError,
					Stock.GetParameterValue("GrpIdErrorMsg"),"Plan Number field error message should displayed in UI.");
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter Invalid data in PEC field verify error message and whether
			// submit button is enabled or not.
			Web.setTextToTextBox(planEnrollmentCodeTextbox,
					Stock.GetParameterValue("InvalidPEC"));
			planEnrollmentCodeTextbox.sendKeys(Keys.TAB);
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter Invalid data in SSN field verify error message and whether
			// submit button is enabled or not.
			Web.setTextToTextBox(ssnSearchedTextbox,
					Stock.GetParameterValue("InvalidSSN"));
			ssnSearchedTextbox.sendKeys(Keys.TAB);
			ssnSearchedError.click();
			CommonLib.verifyIfWebElementTextPresent(ssnSearchedError,
					Stock.GetParameterValue("SSNErrorMsg"),"SSN Field Error should display on  UI");
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter the value in EnrollmentCode and SSN Fields and validate
			// error Message for GroupID Field.
			Web.setTextToTextBox(planEnrollmentCodeTextbox, PlanEnrollCode);
			Web.setTextToTextBox(ssnSearchedTextbox, SSN);
			planNumberTextbox.sendKeys(Keys.ENTER);
			planNumberTextbox.sendKeys(Keys.TAB);
			CommonLib
					.verifyIfWebElementPresent(
							planNumberError,
							"Check if error message is displayed when we did not entered any data for the field :PlanNumber",
							true);

			// Check if Submit button is enabled or not.
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter the value in PlanNumber Number and SSN Fields and validate
			// error Message for PEC Field.
			clearAllFields();
			Web.setTextToTextBox(planNumberTextbox, GrpID);
			Web.setTextToTextBox(ssnSearchedTextbox, SSN);
			planEnrollmentCodeTextbox.sendKeys(Keys.TAB);
			CommonLib
					.verifyIfWebElementPresent(
							planEnrollmentCodeError,
							"Check if error message is displayed when we did not entered any data for the field :PEC ",
							true);

			// Check if Submit button is enabled or not.
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Enter the value in PlanNumber Number and PEC Fields and validate
			// error Message for SSN Field.
			clearAllFields();
			Web.setTextToTextBox(planNumberTextbox, GrpID);
			Web.setTextToTextBox(planEnrollmentCodeTextbox, PlanEnrollCode);
			ssnSearchedTextbox.sendKeys(Keys.TAB);
			CommonLib
					.verifyIfWebElementPresent(
							ssnSearchedError,
							"Check if error message is displayed when we did not entered any data for the field :SSN  ",
							true);

			// Check if Submit button is enabled or not.
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Verify SSn field Should accept value with dashes.
			clearAllFields();
			Web.setTextToTextBox(planNumberTextbox, GrpID);
			Web.setTextToTextBox(planEnrollmentCodeTextbox, PlanEnrollCode);
			Web.setTextToTextBox(ssnSearchedTextbox,
					Stock.GetParameterValue("SSNwithDashes"));
			ssnSearchedTextbox.sendKeys(Keys.TAB);
			Web.clickOnElement(submitBtn);

			// Check if Submit button is enabled or not.
			CommonLib.verifyIsButtonEnabledOrNot(submitBtn, true);

			// Verify GrpId,PEC,SSN values in online enrollment page under
			// Create Account session.
			CommonLib.verifyFieldValue(planNumberTextbox, "194391-01",
					"Actual GrpId should match with expected value.");
			CommonLib.verifyFieldValue(planEnrollmentCodeTextbox,
					PlanEnrollCode,
					"Actual PEC should match with expected value.");
			CommonLib.verifyFieldValue(ssnSearchedTextbox, SSN,
					"Actual SSN should match with expected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method clear the data in all field which are create accout page.
	 */
	private void clearAllFields() {
		planNumberTextbox.clear();
		planEnrollmentCodeTextbox.clear();
		ssnSearchedTextbox.clear();
	}

	/**
	 * Method to verifydivision field validation.
	 * 
	 * @throws InterruptedException
	 */
	/*
	 * public void verifyEndtoEndTestingWithDivisionOnDataBase() throws
	 * InterruptedException{ try{ // fill all the details and verify division
	 * value in confirmation page. verifyDivisionFieldValueOnConfirmationPage();
	 * 
	 * //
	 * 
	 * } catch(Exception e){ e.printStackTrace(); }finally{ try{
	 * Reporter.finalizeTCReport(); }catch(Exception e){ e.printStackTrace(); }
	 * }
	 * 
	 * }
	 */
	public void validateIndicativeData_Country() throws SQLException {

		String rv_low_val = null;
		String rv_meaning = null;

		Select sel = new Select(countryTextbox);

		if (countryTextbox.isDisplayed()) {
			Reporter.logEvent(Status.PASS,
					"Check if Country element is Drop down",
					"Country element is a drop down", true);
			if (sel.getFirstSelectedOption().getText()
					.equalsIgnoreCase("US - United States")) {
				Reporter.logEvent(Status.PASS,
						"Check default value of country field",
						"Default value in drop down is: " + "\nExpected: "
								+ "US - United States" + "\nActual: "
								+ sel.getFirstSelectedOption().getText(), true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check default value of country field",
						"Default value in drop down is: " + "\nExpected: "
								+ "US - United States" + "\nActual: "
								+ sel.getFirstSelectedOption().getText(), true);
			}

			queryResultSet = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForRV_DOMAIN")[1]);
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				if (queryResultSet.next()) {
					rv_low_val = queryResultSet.getString("RV_LOW_VALUE");
					rv_meaning = queryResultSet.getString("RV_MEANING");
					Reporter.logEvent(Status.PASS,
							"Getting RV_LOW_VALUE from Database: D_ISIS",
							"RV_LOW_VALUE: " + rv_low_val + "\nRV_MEANING: "
									+ rv_meaning, true);
				}

				Web.selectDropDownOption(countryTextbox, rv_low_val + " - "
						+ rv_meaning);
				Reporter.logEvent(Status.INFO,
						"Entering RV_LOW_VALUE in Country text box",
						"Value entered is: "
								+ sel.getFirstSelectedOption().getText(), false);

			} else {
				Reporter.logEvent(Status.FAIL,
						"Getting RV_LOW_VALUE from Database",
						"No records found in Database", false);
			}

			Web.selectDropnDownOptionAsIndex(countryTextbox, "0");
			countryTextbox.sendKeys(Keys.TAB);
			if (Web.isWebElementDisplayed(countryError, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Checking if user deselects or leaves country Country error msg is displayed or not",
						"Error message is displayed when user doesn't enter any value in country field: "
								+ countryError.getText(), true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Checking if user deselects or leaves country Country error msg is displayed or not",
						"Error message is not displayed when user doesn't enter any value in country field: "
								+ countryError.getText(), true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Country element is Drop down",
					"Country element is not a drop down", true);
		}
	}

	public void validateIndicativeData_State() throws SQLException {

		String rv_low_val = null;
		String rv_meaning = null;

		Select sel = new Select(stateTextbox);

		if (stateTextbox.isDisplayed()) {
			Reporter.logEvent(Status.PASS,
					"Check if State element is Drop down",
					"State element is a drop down", true);
			if (sel.getFirstSelectedOption().getText().equalsIgnoreCase("")) {
				Reporter.logEvent(Status.PASS,
						"Check default value of State field",
						"Default value in State field is null: "
								+ "\nExpected: " + "" + "\nActual: "
								+ sel.getFirstSelectedOption().getText(), true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check default value of State field",
						"Default value in State field is: " + "\nExpected: "
								+ "" + "\nActual: "
								+ sel.getFirstSelectedOption().getText(), true);
			}

			queryResultSet = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForRV_DOMAINState")[1]);
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				if (queryResultSet.next()) {
					rv_low_val = queryResultSet.getString("RV_LOW_VALUE");
					rv_meaning = queryResultSet.getString("RV_MEANING");
					Reporter.logEvent(Status.PASS,
							"Getting RV_LOW_VALUE from Database: D_ISIS",
							"RV_LOW_VALUE: " + rv_low_val + "\nRV_MEANING: "
									+ rv_meaning, true);
				}

				Web.selectDropDownOption(stateTextbox, rv_low_val + " - "
						+ rv_meaning);
				Reporter.logEvent(Status.INFO,
						"Entering Value in State text box",
						"Value entered is: "
								+ sel.getFirstSelectedOption().getText(), false);

			} else {
				Reporter.logEvent(Status.FAIL,
						"Getting RV_LOW_VALUE from Database",
						"No records found in Database", false);
			}

			Web.selectDropnDownOptionAsIndex(stateTextbox, "0");
			stateTextbox.sendKeys(Keys.TAB);
			if (Web.isWebElementDisplayed(stateError, true)) {
				Reporter.logEvent(
						Status.PASS,
						"Checking if State is not selected, error msg is displayed or not",
						"Error message is displayed when State is not selected: "
								+ stateError.getText(), true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Checking if user deselects or leaves State, error msg is displayed or not",
						"Error message is not displayed when user doesn't enter any value in State field: "
								+ stateError.getText(), true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if State element is Drop down",
					"State element is not a drop down", true);
		}
	}

	public void validateIndicativeData_ZipCode() throws SQLException {

	}

	public void confirmationPage_InformationalMsg() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Reporter.logEvent(
						Status.INFO,
						"Entering all the details in Personal Information section",
						"All the details in Personal Information section are entered",
						true);
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page displayed successfully with the details:"
									+ "\nFirst Name: "
									+ preConfirmFirstName.getText()
									+ "\nLast Name: "
									+ preConfirmLastName.getText()
									+ "\nDOB: "
									+ preConfirmDOB.getText()
									+ "\nSSN: "
									+ preConfirmSSN.getText()
									+ "\nGender: "
									+ preConfirmGender.getText()
									+ "\nMarital Status: "
									+ preConfirmMaritalStatus.getText()
									+ "\nAddress Line1: "
									+ preConfirmAddrLine1.getText()
									+ "\nAddress Line2: "
									+ preConfirmAddrLine2.getText()
									+ "\nCity: "
									+ preConfirmCity.getText()
									+ "\nState: "
									+ preConfirmState.getText()
									+ "\nZip Code: "
									+ preConfirmZipCode.getText()
									+ "\nCountry: "
									+ preConfirmCountry.getText()
									+ "\nPersonal Email: "
									+ preConfirmPersonalEmail.getText()
									+ "\nMobile Phone: "
									+ preConfirmMobile.getText()
									+ "\nHome Phone: "
									+ preConfirmHome.getText()
									+ "\nWork Phone: "
									+ preConfirmWork.getText()
									+ "\nInternational Phone: "
									+ preConfirmIntlPhone.getText(), true);

					if (Web.isWebElementDisplayed(reviewInfoSectionMsg, true)) {
						Reporter.logEvent(
								Status.PASS,
								"Verifying the informational message at the top of the page",
								"Informational message is displayed: \n"
										+ reviewInfoSectionMsg.getText(), true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verifying the informational message at the top of the page",
								"Informational message is not displayed", true);
					}
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void confirmationPage_BackButton() throws InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Reporter.logEvent(
						Status.INFO,
						"Entering all the details in Personal Information section",
						"All the details in Personal Information section are entered",
						true);
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page displayed successfully with the details:"
									+ "\nFirst Name: "
									+ preConfirmFirstName.getText()
									+ "\nLast Name: "
									+ preConfirmLastName.getText()
									+ "\nDOB: "
									+ preConfirmDOB.getText()
									+ "\nSSN: "
									+ preConfirmSSN.getText()
									+ "\nGender: "
									+ preConfirmGender.getText()
									+ "\nMarital Status: "
									+ preConfirmMaritalStatus.getText()
									+ "\nAddress Line1: "
									+ preConfirmAddrLine1.getText()
									+ "\nAddress Line2: "
									+ preConfirmAddrLine2.getText()
									+ "\nCity: "
									+ preConfirmCity.getText()
									+ "\nState: "
									+ preConfirmState.getText()
									+ "\nZip Code: "
									+ preConfirmZipCode.getText()
									+ "\nCountry: "
									+ preConfirmCountry.getText()
									+ "\nPersonal Email: "
									+ preConfirmPersonalEmail.getText()
									+ "\nMobile Phone: "
									+ preConfirmMobile.getText()
									+ "\nHome Phone: "
									+ preConfirmHome.getText()
									+ "\nWork Phone: "
									+ preConfirmWork.getText()
									+ "\nInternational Phone: "
									+ preConfirmIntlPhone.getText(), true);

					if (Web.isWebElementDisplayed(backBtn, false)) {
						Reporter.logEvent(Status.PASS,
								"verifying Back button is displayed or not",
								"Back button is displayed and enabled", true);
						Web.clickOnElement(backBtn);
						Reporter.logEvent(Status.INFO,
								"Clicking on Back button",
								"Back button is clicked", false);

						if (Web.isWebElementDisplayed(createAccountEnrollTable,
								true)) {
							Reporter.logEvent(
									Status.PASS,
									"Cheking if control has returned the page to data entry ",
									"Control has returned the page to data entry enabled",
									true);
						} else {
							Reporter.logEvent(
									Status.FAIL,
									"Cheking if control has returned the page to data entry ",
									"Control has not returned the page to data entry enabled",
									true);
						}
						Web.setTextToTextBox(personalEmailTextBox,
								Stock.GetParameterValue("NEW EMAIL"));
						Reporter.logEvent(
								Status.INFO,
								"Changing Data entered in the page",
								"Changing the personal email\n:"
										+ "New data entered is: "
										+ Stock.GetParameterValue("NEW EMAIL"),
								true);
						Web.clickOnElement(contToCreateNewAccountBtn);

						Reporter.logEvent(
								Status.PASS,
								"Checking if Pre-Confirmation Enroll page is displayed with new changes made on previous page",
								"Pre-Confirmation Enroll page is displayed with new changes made on previous page"
										+ "New Personal Email: "
										+ preConfirmPersonalEmail.getText(),
								true);
					} else {
						Reporter.logEvent(Status.FAIL,
								"verifying Back button is displayed or not",
								"Back button is not displayed ", true);
					}
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	public void confirmationPage_CreateNewAccount() throws SQLException,
			InterruptedException {

		if (Web.isWebElementDisplayed(createAccountEnrollTable, true)) {

			if (Web.isWebElementDisplayed(planFoundLabel, true)) {
				Web.setTextToTextBox(firstNameTextbox,
						Stock.GetParameterValue("FIRST NAME"));
				Web.setTextToTextBox(lastNameTextbox,
						Stock.GetParameterValue("LAST NAME"));
				Web.setTextToTextBox(DateOfBirthTextbox,
						Stock.GetParameterValue("DOB"));
				DateOfBirthTextbox.sendKeys(Keys.TAB);
				List<WebElement> genderRadioBtns = Web.getDriver()
						.findElements(By.id("gender"));
				Thread.sleep(3000);
				genderRadioBtns.get(1).click();
				List<WebElement> maritalStatusRadioBtns = Web.getDriver()
						.findElements(By.id("married"));
				Thread.sleep(3000);
				maritalStatusRadioBtns.get(1).click();
				Web.setTextToTextBox(annualIncomeTextBox,
						Stock.GetParameterValue("ANNUAl INCOME"));
				Web.setTextToTextBox(dateOfHireTextBox,
						Stock.GetParameterValue("DOH"));
				dateOfHireTextBox.sendKeys(Keys.TAB);
				Web.setTextToTextBox(addressLine1,
						Stock.GetParameterValue("ADDR LINE1"));
				Web.setTextToTextBox(cityTextbox,
						Stock.GetParameterValue("CITY"));
				Web.selectDropnDownOptionAsIndex(stateTextbox, "2");
				Web.setTextToTextBox(zipCodeTextBox,
						Stock.GetParameterValue("ZIP CODE"));
				Web.setTextToTextBox(personalEmailTextBox,
						Stock.GetParameterValue("PERSONAL EMAIL"));
				Reporter.logEvent(
						Status.INFO,
						"Entering all the details in Personal Information section",
						"All the details in Personal Information section are entered",
						true);
				Web.clickOnElement(contToCreateNewAccountBtn);
				if (Web.isWebElementDisplayed(preConfirmEnroll, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Checking if Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page displayed successfully with the details:"
									+ "\nFirst Name: "
									+ preConfirmFirstName.getText()
									+ "\nLast Name: "
									+ preConfirmLastName.getText()
									+ "\nDOB: "
									+ preConfirmDOB.getText()
									+ "\nSSN: "
									+ preConfirmSSN.getText()
									+ "\nGender: "
									+ preConfirmGender.getText()
									+ "\nMarital Status: "
									+ preConfirmMaritalStatus.getText()
									+ "\nAddress Line1: "
									+ preConfirmAddrLine1.getText()
									+ "\nAddress Line2: "
									+ preConfirmAddrLine2.getText()
									+ "\nCity: "
									+ preConfirmCity.getText()
									+ "\nState: "
									+ preConfirmState.getText()
									+ "\nZip Code: "
									+ preConfirmZipCode.getText()
									+ "\nCountry: "
									+ preConfirmCountry.getText()
									+ "\nPersonal Email: "
									+ preConfirmPersonalEmail.getText()
									+ "\nMobile Phone: "
									+ preConfirmMobile.getText()
									+ "\nHome Phone: "
									+ preConfirmHome.getText()
									+ "\nWork Phone: "
									+ preConfirmWork.getText()
									+ "\nInternational Phone: "
									+ preConfirmIntlPhone.getText(), true);

					if (Web.isWebElementDisplayed(reviewInfoSectionMsg, true)) {
						Reporter.logEvent(
								Status.PASS,
								"Verifying the informational message at the top of the page",
								"Informational message is displayed: \n"
										+ reviewInfoSectionMsg.getText(), true);

						Web.clickOnElement(createAccount);
						Reporter.logEvent(Status.INFO,
								"Clicking on Create New Account Button",
								"Create New Account Button is clicked", false);

						/**
						 * 
						 */
					} else {
						Reporter.logEvent(
								Status.PASS,
								"Verifying the informational message at the top of the page",
								"Informational message is not displayed", true);
					}
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Checking Pre-Confirmation Enroll page is displayed or not on entering all the valid details",
							"Pre-Confirmation Enroll page not displayed successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Create Account page is displayed or not",
						"create Account Enrollment didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Create Account page is displayed or not",
					"Create Account page didn't get displayed successfully",
					true);
		}
	}

	/*
	 * Module: ONLINE ENROLLMENT Author: VIRAJ
	 */

	public void Enter_gaId(String Ga_id) {
		Web.setTextToTextBox(planNumberTextbox, Ga_id);
		Reporter.logEvent(Status.INFO, "Plan number entered is :", Ga_id, true);

	}

	public void Enter_Pec(String pec) {
		Web.setTextToTextBox(planEnrollmentCodeTextbox, pec);
		Reporter.logEvent(Status.INFO, "PEC value entered is :", pec, true);

	}

	public void Enter_ssn(String ssn) {
		Web.setTextToTextBox(ssnSearchedTextbox, ssn);
		Reporter.logEvent(Status.INFO, "ssn value entered is :", ssn, true);

	}

	public void Validate_invalid_ssn() {
		Web.clickOnElement(ssnSearchedLabel);

		if (Web.isWebElementDisplayed(ssnSearchedError, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify ssn error message for invalid ssn",
					"error message displayed for ssn "
							+ EnrollErrorMessageText.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify ssn error message for invalid ssn",
					"error message NOT displayed", true);
		}

	}

	public void submit() {
		Web.clickOnElement(submitBtn);
		if (Web.isWebElementDisplayed(planFoundLabel, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify navigation to Enrollment/info page",
					"Page is loaded", true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify navigation to Enrollment/info page",
					"Page NOT loaded due to invalid data", true);
		}

	}

	public void Validate_existing_ssn() {

		if (Web.isWebElementDisplayed(EnrollErrorMessage, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify ssn error message for existing ssn",
					"error message displayed."
							+ EnrollErrorMessageText.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify ssn error message forexisting  ssn",
					"error message NOT displayed", true);
		}

	}

	public void validate_ga_pec_Error() {
		if (Web.isWebElementDisplayed(EnrollErrorMessage, true)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify consolidated GAID/PEC error message for invalid data",
					"error message displayed."
							+ EnrollErrorMessageText.getText(), true);
		}
		//
		else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify consolidated GAID/PEC error message for invalid data",
					"error message NOT displayed", true);
		}

	}

	public void validate_invalid_PlanNum_Error() {
		if (Web.isWebElementDisplayed(planNumberError, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify plan num error message displayed for invalid data",
					"error message displayed." + planNumberError.getText(),
					true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify plan num error message displayed for invalid data",
					"error message NOT displayed", true);
		}

	}

	public void air_click() {
		Web.clickOnElement(planNumberLabel);
	}

	public void air_click_main() {
		Web.clickOnElement(planFoundLabel);
	}

	public void last_Name_Field() {
		Web.clickOnElement(lastNameTextbox);
		air_click_main();
		if (Web.isWebElementDisplayed(lastNameError, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify last name error displayed for invalid data",
					"error message displayed." + lastNameError.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify last name error displayed for invalid data",
					"error message NOT displayed", true);
		}

		Web.setTextToTextBox(lastNameTextbox, "*1&%^$");
		air_click_main();

		if (Web.isWebElementDisplayed(lastNameError, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify last name error displayed for invalid data",
					"error message displayed." + lastNameError.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify last name error displayed for invalid data",
					"error message NOT displayed", true);
		}

	}

	public void first_Name_Field() {
		Web.clickOnElement(firstNameTextbox);
		air_click_main();
		if (Web.isWebElementDisplayed(firstNameError, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify first name error displayed for invalid data",
					"error message displayed." + lastNameError.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify first name error displayed for invalid data",
					"error message NOT displayed", true);
		}

		Web.setTextToTextBox(firstNameTextbox, "*1&%^$");

		air_click_main();

		if (Web.isWebElementDisplayed(firstNameError, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify first name error displayed for invalid data",
					"error message displayed." + firstNameError.getText(), true);
		}
		//
		else {
			Reporter.logEvent(Status.FAIL,
					"Verify first name error displayed for invalid data",
					"error message NOT displayed", true);
		}

	}

	public String Validate_Ssn_Enroll() {
		String ssnReadOnly = ssnEnrollPage.getAttribute("readonly");

		if (ssnReadOnly.isEmpty()) {
			Reporter.logEvent(Status.FAIL, "Verify ssn is non editable",
					"SSn is  editable field it should not be editable", true);
		}

		else {
			Reporter.logEvent(Status.PASS, "Verify ssn is non editable",
					"SSn is non editable field", true);
		}
		String ssn = ssnEnrollPage.getText();
		return ssn;
	}

	/**
	 * Method to verify Date of birth field validation.
	 * 
	 * @throws InterruptedException
	 */
	public void validateErrorMsgDateOfBirth() throws InterruptedException {
		// Verify Date of hire is mandatory field.
		Web.setTextToTextBox(DateOfBirthTextbox, Keys.TAB);
		Web.clickOnElement(datOfBirthError);
		CommonLib.verifyIfWebElementPresent(datOfBirthError,"DateOfBirth field error message should displayed on UI.",true);

		// Verify invalid date format for the field Date of hire
		Web.setTextToTextBox(DateOfBirthTextbox,
				Stock.GetParameterValue("DOBInvalid1"));
		CommonLib.verifyIfWebElementPresent(datOfBirthError,
				"DateOfBirth field error message should displayed on UI for entering of invalid date of birth.", true);

		// verify future date verification for the field Date of
		// hire.
		Web.setTextToTextBox(DateOfBirthTextbox,Stock.GetParameterValue("DOBFutureDate"));
		Web.clickOnElement(doneButton);
		boolean b = isAlertPresent();
		if(b){
		Web.getDriver().switchTo().alert().accept();
		}
		Web.clickOnElement(datOfBirthError);
		CommonLib.verifyIfWebElementTextPresent(datOfBirthError,Stock.GetParameterValue("DOBFutureDateErrorMsg"),"DateOfBirth Field error message should present on UI.");

	}
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        Web.getDriver().switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	}   // 
	/**
	 * Verify create new account functionality
	 * @throws InterruptedException 
	 */
	public void verifyCreateAccountFunctionality() throws InterruptedException{
		fillTheFieldesinOnlineEnrollmentForm();
		
		// Click on continuetoCreate account button and verify account not created messgae.
		Web.clickOnElement(contToCreateNewAccountBtn);
		CommonLib.verifyIfWebElementTextPresent(accountNotCreatedMsg, Stock.GetParameterValue("AccountYetToCreated"), "Confirmation page account notcreated message should display.");
		
		// Click on createAccount button and verify Confirmation message.
		Web.clickOnElement(createAccount);
		String expectedConfirmationMsg = Stock.GetParameterValue("CONFIRMATION TEXT");
		String actualConfirmationMsg = Web.getWebElementText(confirmationNumber);
		if(actualConfirmationMsg.contains(expectedConfirmationMsg)){
			Reporter.logEvent(Status.PASS, "Confirmation number should displayed on UI.",expectedConfirmationMsg , false);
		}else{
			Reporter.logEvent(Status.FAIL,"Confirmation number should displayed on UI." ,"Expected was :"+expectedConfirmationMsg + " \\n Actual was :"+ actualConfirmationMsg,true);
		}
		
		 
	 }
}
