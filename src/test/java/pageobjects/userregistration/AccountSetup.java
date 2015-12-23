package pageobjects.userregistration;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.login.LoginPage;


public class AccountSetup extends LoadableComponent<AccountSetup>{

	//Declarations
	LoadableComponent<?> parent;
	
	//Object Declarations
	@FindBy(xpath=".//*[text()[normalize-space()='We found you!']]/..") private WebElement hrdSetUpYourAccount;
	//@FindBy(xpath=".//*[text()[normalize-space()='Account setup']]/..") private WebElement hrdSetUpYourAccount;
	@FindBy(xpath=".//*[@id='registration-form']/h2[1]") private WebElement lblContactInformation;
	@FindBy(xpath=".//*[@id='registration-form']/h2[2]") private WebElement lblUsernameAndPassword;
	@FindBy(id="emailInput") private WebElement txtEmail;
	@FindBy(id="phoneNumberInput") private WebElement txtPhone;
	//@FindBy(id="usernameInput") private WebElement txtUserName;
	@FindBy(xpath=".//*[@id='usernameInput' and @name='username']") private WebElement txtUserName;
	//@FindBy(id="passwordInput") private WebElement txtPassword;
	@FindBy(xpath=".//*[@id='passwordInput'  and @name='password']") private WebElement txtPassword;
	@FindBy(id="confirmPasswordInput") private WebElement txtConfirmPassword;
	@FindBy(id="submit") private WebElement btnRegister;
	@FindBy(xpath=" .//*[contains(@for,'email') and ./ng-message]") private WebElement lblEmailErrMsg;
	@FindBy(xpath=" .//*[contains(@for,'phoneNumber') and ./ng-message]") private WebElement lblPhoneErrMsg;
	@FindBy(xpath=".//*[contains(@for,'username') and ./ng-message]") private WebElement lblUserNameErrMsg;
//	@FindBy(xpath=".//*[@id='registration-form']/div[4]/span[1]") private WebElement lblUserNameErrMsg;
	@FindBy(xpath=".//*[contains(@for,'password') and ./ng-message]") private WebElement lblPasswordErrMsg;
//	@FindBy(xpath=".//*[@id='registration-form']/div[5]/span[1]") private WebElement lblPasswordErrMsg;
	@FindBy(xpath=".//*[contains(@for,'confirmPassword') and ./ng-message]") private WebElement lblConfirmPwdErrMsg;
	@FindBy(xpath=".//p[text()[normalize-space()='Valid Username']]") private WebElement lblValidUsername;
	@FindBy(xpath=".//p[text()[normalize-space()='Valid password']]") private WebElement lblStrongPassword;
	
	/** Empty args constructor
	 * 
	 */
	public AccountSetup() {
		this.parent = new LoginPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public AccountSetup(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(hrdSetUpYourAccount));
	}
	
	@Override
	protected void load() {
		AccountLookup accLookup = (AccountLookup) this.parent;
		
		this.parent.get();
		
		if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I DO NOT HAVE A PIN")) {
			System.out.println(Stock.GetParameterValue("SSN"));
			System.out.println(Stock.GetParameterValue("zipCode"));
			System.out.println(Stock.GetParameterValue("LastName"));
			System.out.println(Stock.GetParameterValue("DOB"));
			System.out.println(Stock.GetParameterValue("StreetAddress"));
			
			accLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"), 
					Stock.GetParameterValue("zipCode"),
					Stock.GetParameterValue("LastName"),
					Stock.GetParameterValue("DOB"),
					Stock.GetParameterValue("StreetAddress"));
		}
		else {
			try {
				accLookup.registrationWithPIN(Stock.GetParameterValue("SSN"),
						Stock.GetParameterValue("PIN"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	EMAIL ADDRESS - [TextBox]
	 * 	MOBILE PHONE NUMBER - [TextBox]
	 * 	USERNAME - [TextBox]
	 * 	PASSWORD - [TextBox]
	 * 	RE-ENTER PASSWORD - [TextBox]
	 * 	ERR_EMAIL ADDRESS - [Error Message]
	 * 	ERR_MOBILE PHONE NUMBER - [Error Message]
	 * 	ERR_USERNAME - [Error Message]
	 * 	ERR_PASSWORD - [Error Message]
	 * 	ERR_RE-ENTER PASSWORD - [Error Message]
	 * 	REGISTER - [Button]
	 *</pre>
	 * 
	 * @param fieldName
	 * @return WebElement
	 */
	private WebElement getWebElement(String fieldName) {
		//Email Address
		if (fieldName.trim().equalsIgnoreCase("EMAIL ADDRESS")) {
			return this.txtEmail;
		}
		
		//ERR_EMAIL ADDRESS
		if (fieldName.trim().equalsIgnoreCase("ERR_EMAIL ADDRESS")) {
			return this.lblEmailErrMsg;
		}
		
		//MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("MOBILE PHONE NUMBER")) {
			return this.txtPhone;
		}
		
		//ERR_MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("ERR_MOBILE PHONE NUMBER")) {
			return this.lblPhoneErrMsg;
		}
		
		//USERNAME
		if (fieldName.trim().equalsIgnoreCase("USERNAME")) {
			return this.txtUserName;
		}
		
		//ERR_USERNAME
		if (fieldName.trim().equalsIgnoreCase("ERR_USERNAME")) {
			return this.lblUserNameErrMsg;
		}
		
		//PASSWORD
		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}
		
		//ERR_PASSWORD
		if (fieldName.trim().equalsIgnoreCase("ERR_PASSWORD")) {
			return this.lblPasswordErrMsg;
		}
		
		//RE-ENTER PASSWORD
		if (fieldName.trim().equalsIgnoreCase("RE-ENTER PASSWORD")) {
			return this.txtConfirmPassword;
		}
		
		//ERR_RE-ENTER PASSWORD
		if (fieldName.trim().equalsIgnoreCase("ERR_RE-ENTER PASSWORD")) {
			return this.lblConfirmPwdErrMsg;
		}
		
		//REGISTER
		if (fieldName.trim().equalsIgnoreCase("REGISTER")) {
			return this.btnRegister;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		
		return null;
	}

	/**<pre> Method to return error message displayed for the corresponding field.
	 * Error message for fields:
	 * 	SOCIAL SECURITY NUMBER
	 * 	ZIP CODE
	 * 	LAST NAME
	 * 	DATE OF BIRTH
	 * 	STREET ADDRESS
	 * 	PIN
	 * 
	 * Returns empty string if no error message is displayed.</pre>
	 * 
	 * @param fieldName
	 * @return <b>Error Msg</b> if displayed. <b>Emptry string</b> otherwise.
	 */
	public String getFieldErrorMsg(String fieldName) {
		WebElement element = this.getWebElement("ERR_" + fieldName);
		String errMsg;
		
		if (element == null) {
			errMsg = "";
		} else {
			if (Web.isWebElementDisplayed(element)) {
				errMsg = element.getText();
			} else {
				errMsg = "";
			}
		}
		
		return errMsg;
	}
	
	/** Method to return text displayed in Account setup header block
	 * 
	 * @return <b>Header block text</b> if element present and text is displayed.
	 *	<b>Empty string</b> if no text is displayed.
	 *	<b>null</b> if header block element is not displayed.
	 * @throws InterruptedException 
	 */
	public String getAccountSetupHeaderBlockText() throws InterruptedException {
		
//		try {
//			common.waitForElement(this.hrdSetUpYourAccount);
//		} catch (Exception e) {
//		}
		
		Thread.sleep(5000);
	
		boolean isHeaderPresent = Web.isWebElementDisplayed(this.hrdSetUpYourAccount);
		String headerText = "";
		
		if (isHeaderPresent) {
			headerText = this.hrdSetUpYourAccount.getText().trim();
		} else {
			return null;
		}
		
		return headerText;
	}
	
	/** Method to submit account setup details
	 * 
	 * @param emailAddress
	 * @param phoneNumber
	 * @param userName
	 * @param password
	 * @param reEnterPassword
	 */
	public void submitAccountSetupDetails (String emailAddress,
											String phoneNumber,
											String userName,
											String password,
											String reEnterPassword) {
		this.txtEmail.sendKeys(emailAddress);
		this.txtPhone.sendKeys(phoneNumber);
		this.txtUserName.sendKeys(userName);
		this.txtPassword.sendKeys(password);
		this.txtConfirmPassword.sendKeys(reEnterPassword);
		this.btnRegister.click();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** Method to perform UI validation on Contact Information section of Account setup page (We found you!)
	 * 
	 * @param validSSN - A valid SSN to verify if email address and mobile phone numbers are pre-populated
	 * @throws Exception 
	 */
	public void validateContactInformationUI(String validSSN) throws Exception {
		String actualErrorText = "";
		String[] sqlQuery;
		String expEmailAddress, expMobileNumberAreaCode, expMobileNumber;
		String actEmailAddress, actMobileNumber;
		boolean isMatching = false;
		ResultSet recordSet;
		
		//Capture screenshot at the beginning of the page
		Reporter.logEvent(Status.INFO, "", "", true);
		
		//Verify "Contact Information" header is displayed
		isMatching = Web.VerifyText("Provide contact information", this.lblContactInformation.getText(), true);
		if (isMatching) {
			Reporter.logEvent(Status.PASS, "Verify 'Contact Information' header is displayed", "Header is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Contact Information' header is displayed", "Header is not displayed", false);
		}
		
		//Verify 'Email Address' field is displayed
		if (Web.isWebElementDisplayed(this.txtEmail)) {
			Reporter.logEvent(Status.PASS, "Verify 'Email Address' text field is displayed", "'Email Address' field is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Email Address' text field is displayed", "'Email Address' is not displayed", false);
		}
		
		//Verify 'Mobile Phone Number' field is displayed
		if (Web.isWebElementDisplayed(this.txtPhone)) {
			Reporter.logEvent(Status.PASS, "Verify 'Mobile Phone Number' text field is displayed", "'Email Address' field is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Mobile Phone Number' text field is displayed", "'Email Address' is not displayed", false);
		}
		
		//Validate if Email Address and Mobile Phone Number are pre-populated when information already exists
		// TODO Update below code to fetch correct query and parameters
		sqlQuery = Stock.getTestQuery("fetchContactInformation");
		//queryParameters = new String[] {"'" + validSSN + "'"};
		recordSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], validSSN);
		
		try {
			
			recordSet.first();
			expEmailAddress = recordSet.getString("EMAIL_ADDRESS");
			expMobileNumber = recordSet.getString("MOBILE_PHONE_NBR");
			expMobileNumberAreaCode = recordSet.getString("MOBILE_PHONE_AREA_CODE");
			
			//if the returned results fo the query is null convert the result into empty string			
			expEmailAddress = (expEmailAddress==null)? "":expEmailAddress;
			expMobileNumber = (expMobileNumber==null)? "":expMobileNumber;
			expMobileNumberAreaCode = (expMobileNumberAreaCode==null)? "":expMobileNumberAreaCode;
			
			actEmailAddress = this.txtEmail.getAttribute("value");
			actMobileNumber = this.txtPhone.getAttribute("value");
			
			
			//Validate pre-populated email address
			isMatching = Web.VerifyText(expEmailAddress, actEmailAddress, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Verify pre-populated Email Address", 
						"Expected: " + expEmailAddress + "\nActual: " + actEmailAddress, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify pre-populated Email Address", 
						"Expected: " + expEmailAddress + "\nActual: " + actEmailAddress, false);
			}
			
			//Validate pre-populated mobile phone number
			if (expMobileNumber.trim().length() > 0) {
				isMatching = Web.VerifyText("(" + expMobileNumberAreaCode + ") " + expMobileNumber,
						actMobileNumber, true);
			} else {
				isMatching = Web.VerifyText("", actMobileNumber, true);
			}
			
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Verify pre-populated Mobile Phone Number", 
						"Expected: " + "(" + expMobileNumberAreaCode + ") " + expMobileNumber +
						"\nActual: " + actMobileNumber, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify pre-populated Mobile Phone Number", 
						"Expected: " + "(" + expMobileNumberAreaCode + ") " + expMobileNumber +
						"\nActual: " + actMobileNumber, false);
			}
			
			
		} catch (SQLException e) {
			Reporter.logEvent(Status.FAIL, "Exception occured while fetching contact details from DB", 
					e.getMessage(), false);
		} finally {
			try {
				recordSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Leave Email Address field empty and then move the cursor out of the field - Email Address is required error message is required
		this.txtEmail.clear();
		this.txtEmail.sendKeys(Keys.TAB);
		if (Web.isWebElementDisplayed(this.lblEmailErrMsg)) {
			actualErrorText = this.lblEmailErrMsg.getText();
			isMatching = Web.VerifyText("Email is required", actualErrorText, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Verify error message is displayed when email field is left blank.", 
						"Expected: Email is required\nActual: " + actualErrorText, true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify error message is displayed when email field is left blank.", 
						"Expected: Email is required\nActual: " + actualErrorText, true);
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Verify error message is displayed when email field is left blank.", 
					"No error message is displayed for 'Email Address' field", true);
		}
		
		//Enter invalid email address and verify for the error message
		this.txtEmail.clear();
		this.txtEmail.sendKeys("Test@test");
		this.txtEmail.sendKeys(Keys.TAB);
		if (Web.isWebElementDisplayed(this.lblEmailErrMsg)) {
			actualErrorText = "";
			actualErrorText = this.lblEmailErrMsg.getText();
			isMatching = Web.VerifyText("Please enter a valid email address", actualErrorText, true);
			if (isMatching) {
				Reporter.logEvent(Status.PASS, "Verify error message is displayed when invalid email address is entered.", 
						"Expected: Please enter a valid email address\nActual: " + actualErrorText, true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify error message is displayed when invalid email address is entered.", 
						"Expected: Please enter a valid email address\nActual: " + actualErrorText, true);
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Verify error message is displayed when invalid email address is entered.", 
					"No error message is displayed for 'Email Address' field", true);
		}
		
		//Enter valid email address and verify for no error message
		this.txtEmail.clear();
		this.txtEmail.sendKeys("Test@test.com");
		this.txtEmail.sendKeys(Keys.TAB);
		if (Web.isWebElementDisplayed(this.lblEmailErrMsg)) {
			actualErrorText = "";
			actualErrorText = this.lblEmailErrMsg.getText();
			Reporter.logEvent(Status.FAIL, "Verify no error message is displayed when valid email address is entered.", 
						"Expected: No error message is displayed\nActual: " + actualErrorText, true);
		} else {
			Reporter.logEvent(Status.PASS, "Verify no error message is displayed when valid email address is entered.", 
					"No error message is displayed for 'Email Address' field", true);
		}
	}
	
	public void validateUserNameAndPasswordUI() {
		boolean isMatching = false;
		String actErrorText;
		
		//Capture screenshot at the beginning of the page
		Reporter.logEvent(Status.INFO, "", "", true);
		
		//Verify "Username and Password" header is displayed
		isMatching = Web.VerifyText("Username and Password", this.lblUsernameAndPassword.getText(), true);
		if (isMatching) {
			Reporter.logEvent(Status.PASS, "Verify 'Username and Password' header is displayed", "Header is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Username and Password' header is displayed", "Header is not displayed", false);
		}
		
		//Verify 'Username' field is displayed
		if (Web.isWebElementDisplayed(this.txtUserName)) {
			Reporter.logEvent(Status.PASS, "Verify 'Username' text field is displayed", "'Username' field is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Username' text field is displayed", "'Username' is not displayed", false);
		}
		
		//Verify 'Password' field is displayed
		if (Web.isWebElementDisplayed(this.txtPassword)) {
			Reporter.logEvent(Status.PASS, "Verify 'Password' text field is displayed", "'Password' field is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Password' text field is displayed", "'Password' is not displayed", false);
		}
		
		//Verify 'Re-Enter Password' field is displayed
		if (Web.isWebElementDisplayed(this.txtConfirmPassword)) {
			Reporter.logEvent(Status.PASS, "Verify 'Re-Enter Password' text field is displayed", "'Re-Enter Password' field is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Re-Enter Password' text field is displayed", "'Re-Enter Password' is not displayed", false);
		}
		
		//Enter a letter 'a' in Username and verify inline error messages
		this.txtUserName.clear();
		this.txtUserName.sendKeys("a");
		actErrorText = this.lblUserNameErrMsg.getText();
		//Verify 'At least three letters' error message is displayed
		if (actErrorText.contains("At least three letters")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least three letters\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least three letters\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least one number' error message is displayed
		if (actErrorText.contains("At least one number")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least six characters' error message is displayed
		if (actErrorText.contains("At least six characters")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least six characters\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Username and verify error message", 
					"Expected: At least six characters\nActual: " + actErrorText, true);
		}
		
		//Clear the Username field and move the cursor out and verify error message displayed
		this.txtUserName.clear();
		this.txtPassword.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
				
//		actErrorText = this.lblUserNameErrMsg.getText();
//		isMatching = Common.VerifyText("Username is required", actErrorText, true);
//		if (isMatching) {
//			Reporter.logEvent(Status.PASS, "Clear Username field and verify error message", 
//					"Expected: Username is required\nActual: " + actErrorText, true);
//		} else {
//			Reporter.logEvent(Status.FAIL, "Clear Username field and verify error message", 
//					"Expected: Username is required\nActual: " + actErrorText, true);
//		}
		
		//-----------------------------------------------------------------------------------------------------
		actErrorText = this.lblUserNameErrMsg.getText();
		if (actErrorText.contains("Username is required")) {
			Reporter.logEvent(Status.PASS, "clear username and verify error message", 
					"Expected: Username is required \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear username and verify error message", 
					"Expected: Username is required\nActual: " + actErrorText, true);
		}
		
		
		if (actErrorText.contains("At least three letters")) {
			Reporter.logEvent(Status.PASS, "clear username and verify error message", 
					"Expected: At least three letters \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear username and verify error message", 
					"Expected: At least three letters\nActual: " + actErrorText, true);
		}
		
		if (actErrorText.contains("At least one number")) {
			Reporter.logEvent(Status.PASS, "clear username and verify error message", 
					"Expected: At least one number \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear username and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		}
		
		if (actErrorText.contains("At least six characters")) {
			Reporter.logEvent(Status.PASS, "clear username and verify error message", 
					"Expected: At least six characters \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear username and verify error message", 
					"Expected: At least six characters\nActual: " + actErrorText, true);
		}
		
		if (actErrorText.contains("Invalid username")) {
			Reporter.logEvent(Status.PASS, "clear username and verify error message", 
					"Expected: Invalid username \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear username and verify error message", 
					"Expected: Invalid username\nActual: " + actErrorText, true);
		}
		//------------------------------------------------------------------------------------------------------------
		
		//Enter valid Username and verify no error message is displayed. And 'Valid Username' messge is displayed
		this.txtUserName.clear();
		this.txtUserName.sendKeys("8253256ABC");
		if(Web.isWebElementDisplayed(this.lblValidUsername)) {
			Reporter.logEvent(Status.PASS, "Enter valid username and verify displayed message", 
					"Expected: Valid Username\nActual: Valid Username", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter valid username and verify displayed message", 
					"Expected: Valid Username\nActual: Message not displayed", true);
		}
		
		//Delete the last letter from the username and verify error message displayed
		this.txtUserName.sendKeys(Keys.BACK_SPACE);
		actErrorText = "";
		actErrorText = this.lblUserNameErrMsg.getText();
		isMatching = Web.VerifyText("At least three letters", actErrorText, true);
		if (isMatching) {
			Reporter.logEvent(Status.PASS, "Delete last letter from valid username (8253256ABC) and verify error displayed", 
					"Expected: At least three letters\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Delete last letter from valid username (8253256ABC) and verify error displayed", 
					"Expected: At least three letters\nActual: " + actErrorText, true);
		}
		
		//--------------------Password field validation-------------------------
		//Enter a letter 'a' in Password and verify in-line error messages
		this.txtPassword.clear();
		this.txtPassword.sendKeys("a");
		actErrorText = this.lblPasswordErrMsg.getText();
		//Verify 'At least one number' error message is displayed
		if (actErrorText.contains("At least one number")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least one uppercase letter' error message is displayed
		if (actErrorText.contains("At least one uppercase letter")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one uppercase letter\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one uppercase letter\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least one special character' error message is displayed
		if (actErrorText.contains("At least one special character")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one special character\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least one special character\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least eight characters' error message is displayed
		if (actErrorText.contains("At least eight characters")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least eight characters\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Password and verify error message", 
					"Expected: At least eight characters\nActual: " + actErrorText, true);
		}
		
		//Clear the Password field and move the cursor out and verify error message displayed
		this.txtPassword.clear();
		this.txtConfirmPassword.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		
		/*actErrorText = this.lblPasswordErrMsg.getText();
		isMatching = Common.VerifyText("Password is required", actErrorText, true);
		if (isMatching) {
			Reporter.logEvent(Status.PASS, "Clear Password field and verify error message", 
					"Expected: Password is required\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Clear Password field and verify error message", 
					"Expected: Password is required\nActual: " + actErrorText, true);
		}*/
		
		//--------------------------------------------
		
		
		actErrorText = this.lblPasswordErrMsg.getText();
		//Verify 'At least one number' error message is displayed
		if (actErrorText.contains("Password is required")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected: Password is required \nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear Password and verify error message", 
					"Expected: Password is required\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least one uppercase letter' error message is displayed
		if (actErrorText.contains("At least one number")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected:At least one number\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear Password and verify error message", 
					"Expected: At least one number\nActual: " + actErrorText, true);
		}
		
		if (actErrorText.contains("At least one lowercase letter")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected: At least one lowercase letter\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear  Password and verify error message", 
					"Expected: At least one lowercase letter\nActual: " + actErrorText, true);
		}
		
		if (actErrorText.contains("At least one uppercase letter")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected: At least one uppercase letter\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear  Password and verify error message", 
					"Expected: At least one uppercase letter\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least one special character' error message is displayed
		if (actErrorText.contains("At least one special character")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected: At least one special character\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear Password and verify error message", 
					"Expected: At least one special character\nActual: " + actErrorText, true);
		}
		
		//Verify 'At least eight characters' error message is displayed
		if (actErrorText.contains("At least eight characters")) {
			Reporter.logEvent(Status.PASS, "clear Password and verify error message", 
					"Expected: At least eight characters\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "clear  Password and verify error message", 
					"Expected: At least eight characters\nActual: " + actErrorText, true);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//--------------------------------------------
		
		//Enter valid Password and verify no error message is displayed and 'Strong Password' message is displayed
		this.txtPassword.clear();
		
		this.txtPassword.sendKeys("Test@1234");
		if(Web.isWebElementDisplayed(this.lblStrongPassword)) {
			Reporter.logEvent(Status.PASS, "Enter valid Password and verify displayed message", 
					"Expected: Strong Password\nActual: Strong Password", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter valid Password and verify displayed message", 
					"Expected: Strong Password\nActual: Message not displayed", true);
		}
		
		//-------------Re-Enter Password field validation------------------
		//Enter a letter 'a' in Re-Enter Password field and verify in-line error messages
		this.txtConfirmPassword.clear();
		this.txtConfirmPassword.sendKeys("a");
		this.txtPassword.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		actErrorText = this.lblConfirmPwdErrMsg.getText();
		//Verify 'Passwords must match' error message is displayed
		if (actErrorText.contains("Passwords must match")) {
			Reporter.logEvent(Status.PASS, "Enter letter 'a' in Re-Enter Password field and verify error message", 
					"Expected: Passwords must match\nActual: " + actErrorText, true);
		} else {
			Reporter.logEvent(Status.FAIL, "Enter letter 'a' in Re-Enter Password field and verify error message", 
					"Expected: Passwords must match\nActual: " + actErrorText, true);
		}
		
		//Enter valid Password and verify no error message is displayed
		this.txtConfirmPassword.clear();
		this.txtConfirmPassword.sendKeys("Test@1234");
		this.txtPassword.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		
		if(Web.isWebElementDisplayed(this.lblConfirmPwdErrMsg)) {
			Reporter.logEvent(Status.FAIL, "Enter valid Password and verify no error message is displayed", 
					"Expected: No message displayed\nActual: " + this.lblConfirmPwdErrMsg.getText(), true);
		} else {
			Reporter.logEvent(Status.PASS, "Enter valid Password and verify no error message is displayed", 
					"Expected: No message displayed\nActual: No message displayed", true);
		}
	}
}
