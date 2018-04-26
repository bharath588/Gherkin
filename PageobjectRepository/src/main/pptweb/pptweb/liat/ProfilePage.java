package pptweb.liat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pptweb.appUtils.Common;
import pptweb.landingpage.LandingPage;
import core.framework.Globals;

public class ProfilePage extends LoadableComponent<ProfilePage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//*[text()='Profile']")
	private WebElement lblProfile;
	@FindBy(xpath = "//*[@id='content-container']//h1")
	private WebElement hrdUserName;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//h2")
	private WebElement hrdPersonalInfo;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//div[1]//strong")
	private WebElement lblPersonalEmailAdd;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//div[2]//strong")
	private WebElement lblMobileNo;
	@FindBy(xpath = ".//*[@id='personalEmailAddress']")
	private WebElement txtPersonalEmailAdd;
	@FindBy(xpath = ".//*[@id='mobilePhoneNumber']")
	private WebElement txtMobileNo;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//h2")
	private WebElement hrdHomeMaililngAdd;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//div[2]//strong")
	private WebElement lblHomeAdd;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//div[2]//div")
	private WebElement txtHomeAdd;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//h2")
	private WebElement hrdUserNamePassword;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[1]//strong")
	private WebElement lblUsername;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[2]//strong")
	private WebElement lblPassword;
	@FindBy(xpath = ".//div[contains(@class,'profile-username')]")
	private WebElement txtUsername;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[2]//div[1]")
	private WebElement txtPassword;

	// Mosin
	@FindBy(id = "updatePassWordId")
	private WebElement lnkChangePassword;

	@FindBy(id = "updateUsernameId")
	private WebElement lnkChangeUsername;

	@FindBy(xpath = ".//*[text()[normalize-space()='Change password']]")
	private WebElement lblChangePassword;

	@FindBy(xpath = "//div[contains(text(),'Change username')]")
	private WebElement lblChangeUsername;

	@FindBy(xpath = "//label[following-sibling::input[@id='usernameId']]")
	private WebElement lblCurrentUsername;

	@FindBy(xpath = "//span[contains(text(),'Username already in use.')]")
	private WebElement lblUserError;

	@FindBy(xpath = "//label[following-sibling::input[@id='passwordId']]")
	private WebElement lblCurrentPassword;
	@FindBy(xpath = "//label[following-sibling::input[@id='newPasswordId']]")
	private WebElement lblNewPassword;
	@FindBy(xpath = "//label[following-sibling::input[@id='confirmPasswordId']]")
	private WebElement lblReEnterNewPassword;

	@FindBy(id = "usernameId")
	private WebElement txtCurrentUsername;
	@FindBy(id = "passwordId")
	private WebElement txtCurrentPassword;
	@FindBy(id = "newPasswordId")
	private WebElement txtNewPassword;
	@FindBy(id = "confirmPasswordId")
	private WebElement txtReEnterPassword;

	@FindBy(xpath = ".//*[text()[normalize-space()='Save Changes']]")
	private WebElement lnkSave;
	@FindBy(xpath = ".//*[text()[normalize-space()='Cancel']]")
	private WebElement lnkCancel;

	@FindBy(xpath = "//div[@class='form-group has-error']/span[@class='help-block']")
	private WebElement lblErrorCurrentPassword;

	@FindBy(xpath = "//a[text()='Home']")
	private WebElement lblHome;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;

	// Personal Contact Information

	@FindBy(id = "updateContactInfoId")
	private WebElement lnkChangeContact;

	@FindBy(id = "emailId")
	private WebElement txtPersonalEmail;
	@FindBy(id = "phoneNumberIdD")
	private WebElement txtPersonalPhoneNum;
	@FindBy(xpath = "//p[@class='success']")
	private List<WebElement> lstsuccessMsg;
	@FindBy(xpath = "//ng-message[@class='form-validation-rule' or contains(text(),'Username is required')]")
	private List<WebElement> lstErrorMsg;
	public static String userFromDatasheet = null;
	private String textField = "//*[contains(text(),'webElementText')]";
	private List<WebElement> lstErrorMessages;

	// Username and Password - Profile page
	@FindBy(xpath = "//div[@class='col-sm-4 profile-username']")
	private WebElement lblUsernameProfilePage;

	@FindBy(xpath = "//strong[contains(text(),'Password')]/following-sibling::div[@class='col-sm-4']")
	private WebElement lblPasswordProfilePage;

	// Home mailing address

	@FindBy(id = "updateHomeAddressId")
	private WebElement lnkChangeHomeAddress;

	@FindBy(xpath = "//div[@class='profile-information home-information']/div/strong")
	private WebElement lblHomeAddress;

	@FindBy(xpath = "//div[@class='profile-information home-information']/div/div/*")
	private List<WebElement> lstHomeAddressProfilePage;

	@FindBy(xpath = "//label[following-sibling::input[@id='stline1Id']]")
	private WebElement lblAddressLine1;

	@FindBy(xpath = "//label[following-sibling::input[@id='stline2Id']]")
	private WebElement lblAddressLine2;

	@FindBy(xpath = "//label[@class='control-label'][following-sibling::input[@id='cityId']]")
	private WebElement lblAddressCity;

	@FindBy(xpath = "//label[./following-sibling::div[./select[@id='state']]]")
	private WebElement lblAddressState;

	@FindBy(xpath = "//label[following-sibling::input[@id='zipcodeId']]")
	private WebElement lblAddressZip;

	@FindBy(xpath = "//label[./following-sibling::div[./select[@id='countryId']]]")
	private WebElement lblAddressCountry;

	// Work contact information

	@FindBy(xpath = "//div[@class='profile-information work-information']")
	private WebElement lblWorkContactInformation;

	@FindBy(id = "workEmailAddress")
	private WebElement txtWorkEmailAddress;

	@FindBy(id = "workPhoneNumber")
	private WebElement txtWorkPhoneNumber;

	@FindBy(xpath = "//div[@class='profile-information home-information'][preceding-sibling::div[@class='profile-information work-information']]//h2")
	private WebElement lblHomeMailingAddressOnWorkContact;

	/*
	 * List<WebElement> lstErrorMsg = Web .getDriver() .findElements( By.xpath(
	 * "//ng-message[@class='form-validation-rule' or contains(text(),'Username is required')]"
	 * ));
	 */

	/**
	 * Default Constructor
	 */
	public ProfilePage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */

	public ProfilePage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	// label[following-sibling::input[@id='passwordId']]
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblProfile, true),
				"Profile Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		// String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {

			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
						+ strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String userLogedIn = this.lblProfile.getText();
		/*
		 * String sponser = this.lblSponser.getAttribute("Alt"); if
		 * (sponser.isEmpty()) { sponser = Common.GC_DEFAULT_SPONSER; }
		 */
		if (Web.isWebElementDisplayed(this.lblProfile)) {
			// Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblProfile, true));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false, "Login Page is not loaded\n");
		}

	}

	@Override
	protected void load() {
		this.parent.get();

		((LandingPage) this.parent).dismissPopUps(false, false);
		this.lblUserName.click();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());

	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	LOG OUT Or LOGOUT - [Link]
	 * 	HOME - [Link]
	 * 	MY ACCOUNTS - [Link]
	 * 	RETIREMENT INCOME - [Label]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		if (fieldName.trim().equalsIgnoreCase("HOME")) {
			return this.lblHome;
		}

		// Profile
		if (fieldName.trim().equalsIgnoreCase("PROFILE")) {
			return this.lblProfile;
		}

		// Personal Contact Info
		if (fieldName.trim().equalsIgnoreCase("PERSONAL CONTACT INFORMATION")) {
			return this.hrdPersonalInfo;
		}
		// UserName
		if (fieldName.trim().equalsIgnoreCase("LABEL USER NAME")) {
			return this.hrdUserName;
		}

		// PERSONAL EMAIL ADDRESS
		if (fieldName.trim().equalsIgnoreCase("LABEL PERSONAL EMAIL ADDRESS")) {
			return this.lblPersonalEmailAdd;
		}
		// MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("LABEL MOBILE PHONE NUMBER")) {
			return this.lblMobileNo;
		}
		// TEXT PERSONAL EMAIL ADDRESS
		if (fieldName.trim().equalsIgnoreCase("PERSONAL EMAIL ADDRESS")) {
			return this.txtPersonalEmailAdd;
		}
		// TEXT MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("MOBILE PHONE NUMBER")) {
			return this.txtMobileNo;
		}
		// HOME MAILING ADDRESS
		if (fieldName.trim().equalsIgnoreCase("HOME MAILING ADDRESS")) {
			return this.hrdHomeMaililngAdd;
		}
		// HOME ADDRESS
		if (fieldName.trim().equalsIgnoreCase("LABEL HOME ADDRESS")) {
			return this.lblHomeAdd;
		}
		// TEXT HOME ADDRESS
		if (fieldName.trim().equalsIgnoreCase("HOME ADDRESS")) {
			return this.txtHomeAdd;
		}
		// USER NAME AND PASSWORD
		if (fieldName.trim().equalsIgnoreCase("USER NAME AND PASSWORD")) {
			return this.hrdUserNamePassword;
		}
		// LABEL USER NAME
		if (fieldName.trim().equalsIgnoreCase("LABEL USER NAME")) {
			return this.lblUsername;
		}
		// PASSWORD
		if (fieldName.trim().equalsIgnoreCase("LABEL PASSWORD")) {
			return this.lblPassword;
		}
		// TEXT USER NAME
		if (fieldName.trim().equalsIgnoreCase("USER NAME")) {
			return this.txtUsername;
		}
		// TEXT PASSWORD
		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}
		// Mosin
		// Change Password Buttton
		if (fieldName.trim().equalsIgnoreCase("CHANGE PASSWORD BUTTON")) {
			return this.lnkChangePassword;
		}
		// Change Username Buttton
		if (fieldName.trim().equalsIgnoreCase("CHANGE USERNAME BUTTON")) {
			return this.lnkChangeUsername;
		}
		// Change Password
		if (fieldName.trim().equalsIgnoreCase("CHANGE PASSWORD TEXT")) {
			return this.lblChangePassword;
		}

		// Change Username
		if (fieldName.trim().equalsIgnoreCase("CHANGE USERNAME TEXT")) {
			return this.lblChangeUsername;
		}
		// SAVE CHANGES LINK
		if (fieldName.trim().equalsIgnoreCase("SAVE CHANGES")) {
			return this.lnkSave;
		}
		// CANCEL LINK
		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
			return this.lnkCancel;
		}
		if (fieldName.trim().equalsIgnoreCase("CURRENT PASSWORD")) {
			return this.txtCurrentPassword;
		}
		if (fieldName.trim().equalsIgnoreCase("CURRENT USERNAME")) {
			return this.txtCurrentUsername;
		}
		// SAVE CHANGES LINK
		if (fieldName.trim().equalsIgnoreCase("NEW PASSWORD")) {
			return this.txtNewPassword;
		}
		// CANCEL LINK
		if (fieldName.trim().equalsIgnoreCase("REENTER PASSWORD")) {
			return this.txtReEnterPassword;
		}
		// ERROR CURRENT PASSWORD
		if (fieldName.trim().equalsIgnoreCase("ERROR CURRENT PASSWORD")) {
			return this.lblErrorCurrentPassword;
		}
		// CURRENT PASSWORD TEXT
		if (fieldName.trim().equalsIgnoreCase("CURRENT PASSWORD TEXT")) {
			return this.lblCurrentPassword;
		}
		if (fieldName.trim().equalsIgnoreCase("CURRENT USERNAME TEXT")) {
			return this.lblCurrentUsername;
		}
		// NEW PASSWORD TEXT
		if (fieldName.trim().equalsIgnoreCase("NEW PASSWORD TEXT")) {
			return this.lblNewPassword;
		}
		// REENTER PASSWORD TEXT
		if (fieldName.trim().equalsIgnoreCase("REENTER PASSWORD TEXT")) {
			return this.lblReEnterNewPassword;
		}
		if (fieldName.trim().equalsIgnoreCase("EXSISTING USER ERROR")) {
			return this.lblUserError;
		}
		if (fieldName.trim().equalsIgnoreCase("PERSONAL EMAIL ADDRESS TEXTBOX")) {
			return this.txtPersonalEmail;
		}
		if (fieldName.trim().equalsIgnoreCase("PERSONAL PHONE NUMBER")) {
			return this.txtPersonalPhoneNum;
		}

		if (fieldName.trim().equalsIgnoreCase("USERNAME PROFILE PAGE")) {
			return this.lblUsernameProfilePage;
		}
		if (fieldName.trim().equalsIgnoreCase("PASSWORD PROFILE PAGE")) {
			return this.lblPasswordProfilePage;
		}

		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS")) {
			return this.lnkChangeHomeAddress;
		}

		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS LINE1")) {
			return this.lblAddressLine1;
		}
		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS LINE2")) {
			return this.lblAddressLine2;
		}
		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS CITY")) {
			return this.lblAddressCity;
		}
		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS STATE")) {
			return this.lblAddressState;
		}
		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS ZIP")) {
			return this.lblAddressZip;
		}
		if (fieldName.trim().equalsIgnoreCase("CHANGE HOME ADDRESS COUNTRY")) {
			return this.lblAddressCountry;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"HOME ADDRESS ON WORK INFORMATION")) {
			return this.lblHomeMailingAddressOnWorkContact;
		}

		if (fieldName.trim()
				.equalsIgnoreCase("HOME ADDRESS LABEL PROFILE PAGE")) {
			return this.lblHomeAddress;
		}

		if (fieldName.trim().equalsIgnoreCase(
				"CHANGE CONTACT INFORMATION BUTTON")) {
			return this.lnkChangeContact;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed
	 * @throws InterruptedException
	 */

	/**
	 * Below method validates if the given password text box is masked or not
	 * 
	 * @param ele
	 */
	public void validatePasswordBox(String ele) {
		WebElement nEle = getWebElement(ele);
		String type = nEle.getAttribute("type");
		if (type.equalsIgnoreCase("password")) {
			Reporter.logEvent(Status.PASS,
					" Verify 'Change Password' Page is displayed",
					"Password box is masked", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Change Password' Page is displayed",
					"Password box is not masked", true);
		}

	}

	/**
	 * Below method validates if the users information like Personal phone
	 * number and email address matches the DB
	 * 
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	public void validatePersonalContactInformation()
			throws InterruptedException, SQLException {
		String[] sqlQueryNumber = Stock.getTestQuery(Stock
				.GetParameterValue("getPhoneNum"));
		String[] sqlQueryEmail = Stock.getTestQuery(Stock
				.GetParameterValue("getEmail"));

		ResultSet rs_Number = DB.executeQuery(sqlQueryNumber[0],
				sqlQueryNumber[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		String sMobileAreaCode = rs_Number.getString("mobile_phone_area_code");
		String sMobileNbr = rs_Number.getString("mobile_phone_nbr");
		String sPhoneNum = "(" + sMobileAreaCode + ")" + " " + sMobileNbr;

		ResultSet rs_Email = DB.executeQuery(sqlQueryEmail[0],
				sqlQueryEmail[1], Stock.GetParameterValue("userName"));
		rs_Email.first();
		String sEmail = rs_Email.getString("email_address");

		System.out.println("From DataBase: " + sEmail + " and " + sPhoneNum);

		if (txtPersonalEmailAdd.getText().equalsIgnoreCase(sEmail)) {
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Personal contact EmailId is matching", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Personal contact EmailId is not matching", true);
		}
		if (txtMobileNo.getText().equalsIgnoreCase(sPhoneNum)) {
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Personal contact Phone Number is matching", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Personal contact Phone Number is not matching", true);
		}
		Thread.sleep(3000);
		lnkChangeContact.click();
		Thread.sleep(2000);
		// System.out.println("Email" + txtPersonalEmail.getAttribute("value"));
		// System.out.println("Phone" +
		// txtPersonalPhoneNum.getAttribute("value"));

		if (txtPersonalEmail.getAttribute("value").equalsIgnoreCase(sEmail)) {
			Reporter.logEvent(
					Status.PASS,
					" Verify 'Change Personal Contact Information' Page is displayed",
					"Personal contact EmailId is matching", false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					" Verify 'Change Personal Contact Information' Page is displayed",
					"Personal contact EmailId is not matching", true);
		}
		if (txtPersonalPhoneNum.getAttribute("value").equalsIgnoreCase(
				sPhoneNum)) {
			Reporter.logEvent(
					Status.PASS,
					" Verify 'Change Personal Contact Information' Page is displayed",
					"Personal contact Phone number matching", false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					" Verify 'Change Personal Contact Information' Page is displayed",
					"Personal contact Phone Number is not matching", true);
		}
		lnkCancel.click();
	}

	public void validateWorkContactInformation() throws SQLException {
		String[] sqlQueryNumber = Stock.getTestQuery(Stock
				.GetParameterValue("getWorkPhoneNum"));
		String[] sqlQueryEmail = Stock.getTestQuery(Stock
				.GetParameterValue("getWorkEmail"));

		ResultSet rs_Number = DB.executeQuery(sqlQueryNumber[0],
				sqlQueryNumber[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		String sMobileAreaCode = rs_Number.getString("work_phone_area_code");
		String sMobileNbr = rs_Number.getString("work_phone_nbr");

		ResultSet rs_Email = DB.executeQuery(sqlQueryEmail[0],
				sqlQueryEmail[1], Stock.GetParameterValue("userName"));
		rs_Email.first();

		String sEmail = "";
		boolean bEmail = true;

		try {
			sEmail = rs_Email.getString("email_address");
		} catch (SQLException e) {
			bEmail = false;
		}
		System.out.println("From DataBase Email: " + sEmail);
		System.out.println("From DB Phone:" + sMobileAreaCode + " "
				+ sMobileNbr);

		if (bEmail == false && sMobileAreaCode == null) {
			try {
				if (!lblWorkContactInformation.isDisplayed()) {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							"Work contact information is not displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							" Verify 'Profile' Page is displayed",
							"Work contact information is displayed", true);
				}
			} catch (NoSuchElementException e) {
				Reporter.logEvent(Status.PASS,
						" Verify 'Profile' Page is displayed",
						"Work contact information is not displayed", false);
			}

		} else {
			if (bEmail == true) {
				if (txtWorkEmailAddress.getText().equalsIgnoreCase(sEmail)) {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							"Work Email Address is matching", false);
				} else {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							" Work Email Address is not matching with DB",
							false);
				}
			} else {
				if (txtWorkEmailAddress.getText().isEmpty()) {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							"Work Email Address is null", false);

				} else {
					Reporter.logEvent(Status.FAIL,
							" Verify 'Profile' Page is displayed",
							"Work Email Address is not null", true);
				}
			}

			if (sMobileAreaCode == null && sMobileNbr == null) {

				if (txtWorkPhoneNumber.getText().isEmpty()) {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							"Work Phone number is null", false);

				} else {
					Reporter.logEvent(Status.FAIL,
							" Verify 'Profile' Page is displayed",
							"Work Phone number is not null", false);
				}
			} else {
				String sPhoneNum = "(" + sMobileAreaCode + ")" + " "
						+ sMobileNbr;
				if (txtWorkPhoneNumber.getText().equalsIgnoreCase(sPhoneNum)) {
					Reporter.logEvent(Status.PASS,
							" Verify 'Profile' Page is displayed",
							"Work Phone number matching", false);

				} else {
					Reporter.logEvent(Status.FAIL,
							" Verify 'Profile' Page is displayed",
							"Work Phone Number is not matching", true);
				}
			}
		}

	}
	/**
	 * This method is used to validate if the Home Mailing address is displayed as per the values listed in DB 
	 * @throws SQLException
	 */
	//@SuppressWarnings("null")
	public void validateHomeMailingAddress() throws SQLException {
		String sFirstLine, sSecondLine;
		//List<String> sAddress = new ArrayList<String>();
		// String sText = getElementText("HOME ADDRESS PROFILE PAGE");
		String[] sqlQueryHomeAddress = Stock.getTestQuery(Stock
				.GetParameterValue("getHomeAddress"));

		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		sFirstLine = rs_Number.getString("first_line_mailing");
		sSecondLine = rs_Number.getString("scnd_line_mailing");
		
		String sCity = rs_Number.getString("city");
		String sState = rs_Number.getString("state_code");
		String sZip = rs_Number.getString("zip_code");
		String sCountry = rs_Number.getString("country");

		String sFullAddress = sCity + "  " + sState + "  " + sZip + " ";
		System.out.println("From DB" + sFullAddress);
		System.out.println("Second Line from DB"+sSecondLine);
		System.out.println("Second line from UI"+lstHomeAddressProfilePage.get(1).getText()+lstHomeAddressProfilePage.get(1).getText().isEmpty());
		
		if (sFirstLine.equalsIgnoreCase(lstHomeAddressProfilePage.get(0).getText()))

		{
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sFirstLine matches the DB values", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sFirstLine doesnot matches the DB values", true);
		}
		if ( (sSecondLine==null && lstHomeAddressProfilePage.get(1).getText().isEmpty()) || sSecondLine.equalsIgnoreCase(lstHomeAddressProfilePage.get(1).getText()))

		{
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sSecondLine matches the DB values", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sFirstLine doesnot matches the DB values", true);
		}
		if (sFullAddress.trim().equalsIgnoreCase(lstHomeAddressProfilePage.get(2).getText()))

		{
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Home mailing sFullAddress address matches the DB values", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Home mailing sFullAddress address doesnot matches the DB values", true);
		}
		if (sCountry.equalsIgnoreCase(lstHomeAddressProfilePage.get(4).getText()))

		{
			Reporter.logEvent(Status.PASS,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sCountry matches the DB values", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Profile' Page is displayed",
					"Home mailing address sCountry doesnot matches the DB values", true);
		}

	}

	/**
	 * Below method validates whether the given username matches with the
	 * username after moving to profile page
	 * 
	 * @param name
	 */
	public void validateUsernameBox(String name) {
		System.out.println("Value" + txtCurrentUsername.getAttribute("value"));
		if (txtCurrentUsername.getAttribute("value").equals(name)) {
			Reporter.logEvent(Status.PASS,
					" Verify 'Username' Page is displayed",
					"Username is matching", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Username' Page is displayed",
					"Username is not matching", true);
		}
	}

	/**
	 * Below method validates if the error msg is displayed in UI or not
	 * 
	 * @param sMsg
	 * @param type
	 * @param bStatus
	 * @throws InterruptedException
	 */
	public void validateMsg(String sMsg, String type, boolean bStatus)
			throws InterruptedException {
		Thread.sleep(1000);
		/*
		 * List<WebElement> lstErrorMsg = Web.getDriver().findElements(
		 * By.xpath("//ng-message[@class='form-validation-rule']"));
		 */

		if (type.equalsIgnoreCase("Success")) {
			lstErrorMessages = lstsuccessMsg;
		} else {
			lstErrorMessages = lstErrorMsg;
		}

		/*
		 * for(int i=0;i<lstErrorMessages.size(); i++) {
		 * System.out.println(lstErrorMessages.get(i).getText()); }
		 */

		boolean status = false;

		if (bStatus == true) {
			for (int i = 0; i < lstErrorMessages.size(); i++) {
				if (sMsg.trim()
						.equals(lstErrorMessages.get(i).getText().trim())) {

					Reporter.logEvent(Status.PASS,
							" Verify 'Change Password' Page is displayed",
							"Error Message " + sMsg.trim() + " is displayed",
							false);
					status = true;
					break;
				}
			}
		} else {

			if (!lstErrorMessages.contains(sMsg)) {

				status = true;
				Reporter.logEvent(Status.PASS,
						" Verify 'Change Password' Page is displayed",
						"Error Message " + sMsg.trim() + " is not displayed",
						false);

			} else {
				status = true;
				Reporter.logEvent(Status.FAIL,
						" Verify 'Change Password' Page is displayed",
						"Error Message " + sMsg.trim() + " is displayed", true);
			}

		}
		if (status == false) {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Change Password' Page is displayed",
					"Error Message " + sMsg.trim() + " is not displayed", true);
		}
	}

	/*
	 * public void validateSuccess(String sValid) throws InterruptedException {
	 * Thread.sleep(3000);
	 * 
	 * boolean status = false;
	 * 
	 * for (int i = 0; i < lstsuccessMsg.size(); i++) { if
	 * (sValid.trim().equals(lstsuccessMsg.get(i).getText().trim())) {
	 * Reporter.logEvent(Status.PASS,
	 * " Verify 'Change Password' Page is displayed", "Success Message " +
	 * sValid.trim() + " is displayed", false); status = true; break; } } if
	 * (status == false) { Reporter.logEvent(Status.FAIL,
	 * " erify 'Change Password' Page is displayed", "Error Message " +
	 * sValid.trim() + " is not displayed", true); } }
	 */
	/*
	 * Validates profile page information
	 */
	public boolean validateUserProfileInfo() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.lblProfile);
		boolean isTextMatching = false;
		if (isElementPresent) {

			Reporter.logEvent(Status.PASS,
					" Verify User 'Profile' Page is displayed",
					"User 'Profile' Page is displayed", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify User 'Profile' Page is displayed",
					"User 'Profile' Page is not displayed", true);
		}
		String userName = this.hrdUserName.getText().replaceAll("\\s+", " ")
				.trim();
		isTextMatching = Web.VerifyText(userFromDatasheet, userName, true);

		if (isTextMatching) {
			Reporter.logEvent(
					Status.PASS,
					"Verify 'User Name In Profile Page'  is displayed",
					"USer Name is displayed\nExpected:"
							+ userFromDatasheet.trim() + "\nActual:" + userName,
					false);

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify 'User Name In Profile Page'  is displayed",
					"USer Name is not Same\nExpected:"
							+ userFromDatasheet.trim() + "\nActual:" + userName,
					false);
		}

		verifyWebElementDisplayed("PERSONAL CONTACT INFORMATION");
		verifyWebElementDisplayed("LABEL PERSONAL EMAIL ADDRESS");
		verifyWebElementDisplayed("LABEL MOBILE PHONE NUMBER");
		verifyWebElementText("PERSONAL EMAIL ADDRESS");
		verifyWebElementText("MOBILE PHONE NUMBER");
		verifyWebElementDisplayed("HOME MAILING ADDRESS");
		verifyWebElementDisplayed("LABEL HOME ADDRESS");
		verifyWebElementText("HOME ADDRESS");
		verifyWebElementDisplayed("USER NAME AND PASSWORD");
		verifyWebElementDisplayed("LABEL USER NAME");
		verifyWebElementDisplayed("LABEL PASSWORD");
		verifyWebElementText("USER NAME");
		verifyWebElementText("PASSWORD");

		return isTextMatching;
	}

	public void verifyWebElementDisplayed(String fieldName) {

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName), true)) {

			Reporter.logEvent(Status.PASS, "Verify \'" + fieldName
					+ "\' is displayed", fieldName + " is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL, "Verify \'" + fieldName
					+ "\' is displayed", fieldName + " is not displayed", false);
		}

	}

	// Mosin
	// Below method is used to verify if the element is enabled or not
	public void verifyWebElementEnabled(String fieldName) {

		if (this.getWebElement(fieldName).isEnabled()) {

			Reporter.logEvent(Status.PASS, "Verify \'" + fieldName
					+ "\' is enabled", fieldName + " is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL, "Verify \'" + fieldName
					+ "\' is enabled", fieldName + " is not Same", false);
		}

	}

	// Mosin
	// Below method is used to get the text of any element which is passed as
	// parameter
	public String getElementText(String fieldName) {
		return Web.getWebElementText(this.getWebElement(fieldName)).trim();
	}

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return boolean - Displayed
	 */
	public void verifyWebElementText(String fieldName) {

		String getText = "";

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

			getText = this.getWebElement(fieldName).getText().trim();
			if (!getText.isEmpty()) {

				Reporter.logEvent(Status.PASS, "Verify Text for \'" + fieldName
						+ "\' is displayed", "Text for " + fieldName
						+ " is displayed", false);

			} else {
				Reporter.logEvent(Status.FAIL, "Verify Text for \'" + fieldName
						+ "\' is displayed", "Text for" + fieldName
						+ " is not Same", false);
			}
		}

	}

	// Below method is used to validate if the given text is currently displayed
	// on the UI or not
	public boolean isTextFieldDisplayed(String fieldName) throws InterruptedException {
		Thread.sleep(2000);
		boolean isTextDisplayed = false;
		try {
			WebElement txtField = Web.getDriver().findElement(
					By.xpath(textField.replace("webElementText", fieldName)));

			Web.waitForElement(txtField);
			isTextDisplayed = Web.isWebElementDisplayed(txtField, true);

			if (isTextDisplayed)
				lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field "
						+ fieldName + "  is Displayed", "TEXT Field '"
						+ fieldName + "' is Displayed", false);

		} catch (NoSuchElementException e) {
			lib.Reporter.logEvent(Status.FAIL, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '" + fieldName
					+ "' is Not Displayed", false);
			isTextDisplayed = false;
		}

		return isTextDisplayed;
	}
}
