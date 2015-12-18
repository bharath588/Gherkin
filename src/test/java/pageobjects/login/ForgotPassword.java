package pageobjects.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.utils.Reporter;
import core.utils.common;
import core.utils.Reporter.Status;

public class ForgotPassword extends LoadableComponent<ForgotPassword> {

	private LoadableComponent<?> parent;
	private boolean iselementDisplayed;
	
	@FindBy(xpath=".//*[contains(text(),'Login Help')]") private WebElement lblLoginHelp;
	@FindBy(xpath=".//*[contains(text(),'Enter the information below to recover your username')]") private WebElement lblLoginHelpTxr;
	@FindBy(xpath=".//*[@for='ssnInput']") private WebElement lblSSN;
	@FindBy(xpath=".//*[@for='zipCodeInput']") private WebElement lblZipCode;
	@FindBy(xpath=".//*[@for='lastNameInput']") private WebElement lblLastName;
	@FindBy(xpath=".//*[@for='dateOfBirthInput']") private WebElement lblDateofBirth;
	@FindBy(xpath=".//*[@for='streetAddressInput']") private WebElement lblStreetAddress;
	//@FindBy(xpath=".//*[@id='submit']") private WebElement btnContinue;
	@FindBy(xpath=".//*[@id='submit' and @value='Continue']") private WebElement btnContinue;
	@FindBy(xpath=".//*[@id='ssnInput']") private WebElement txtSSN;
	@FindBy(xpath=".//*[@id='zipCodeInput']") private WebElement txtZipCode;
	@FindBy(xpath=".//*[@id='lastNameInput']") private WebElement txtLastName;
	@FindBy(xpath=".//*[@id='dateOfBirthInput']") private WebElement txtDateofBirth;
	@FindBy(xpath=".//*[@id='streetAddressInput']") private WebElement txtStreetAddress;
	@FindBy(xpath=".//*[@class='form-group has-error ng-scope']") private WebElement lblErrorMessage;
	@FindBy(xpath=".//*[@id='passwordReset']/a") private WebElement lnkNeedHelpWithPassword;
	@FindBy(linkText="Return to login") private WebElement lnkReturnToLogin;
	@FindBy(xpath=".//*[text()[normalize-space()='Recover your account']]") private WebElement lblRecoverYourAccount;
	@FindBy(xpath=".//*[@id='passwordInput' and @name='password']") private WebElement txtPassword;
	@FindBy(xpath=".//*[@id='confirmPasswordInput']") private WebElement txtReEnterPassword;
	@FindBy(xpath=".//*[@id='accountRecoveryContinue']") private WebElement btnContinueAccRecovery;
	
	/** Empty args constructor
	 * 
	 */
	public ForgotPassword(){
		this.parent = new LoginPage();
		PageFactory.initElements(common.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public ForgotPassword(LoadableComponent<?> parent){
		this.parent = parent;
		PageFactory.initElements(common.webdriver, this);
	}
	
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(common.isWebElementDisplayed(lblLoginHelp));
//		iselementDisplayed = WebActions.isWebElementDisplayed(lblLoginHelp);
//		if (!iselementDisplayed) {
//			throw new AssertionError("Forgot Password Page is not loaded.");
//		}
		
	}

	@Override
	protected void load() {
		LoginPage objLogin = (LoginPage) this.parent;
		objLogin.get();
		objLogin.clickForgotPassword();
		
	}

	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	I NEED HELP WITH MY PASSWORD TOO - [Link]
	 * 	RETURN TO LOGIN - [Link]
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//CHOOSE DELIVERY METHOD
		if (fieldName.trim().equalsIgnoreCase("I NEED HELP WITH MY PASSWORD TOO")) {
			return this.lnkNeedHelpWithPassword;
		}
		
		if (fieldName.trim().equalsIgnoreCase("RETURN TO LOGIN")) {
			return this.lnkReturnToLogin;
		}
		return null;
	
	}
	
		
	
	/** Check if text in the forgot password page has displayed the header text on the page
	 * 
	 * @param NA
	 * @return <b>String value</b> if specified field is displayed with some text. <b>Null</b> otherwise.
	 */
	public String isLoginHelptxtDisplayed(){
		iselementDisplayed = common.isWebElementDisplayed(lblLoginHelpTxr);
		if (iselementDisplayed) {
			return this.lblLoginHelpTxr.getText();
		}else{
			return "";
		}
	}
	
	
	/** Verify the label of each of the text box that appears on the Login Help page
	 * 
	 * @param NA
	 * @return <b>true</b> if all the labels are displayed as expected. <b>false</b> otherwise.
	 */
	public boolean validateFieldNames(){
		iselementDisplayed = true;
		if (common.isWebElementDisplayed(this.lblSSN)) {
			if (!this.lblSSN.getText().equalsIgnoreCase("SOCIAL SECURITY NUMBER")) {
				iselementDisplayed = false;
			}
			
		} 
		
		if (common.isWebElementDisplayed(this.lblZipCode)) {
			if (!this.lblZipCode.getText().equalsIgnoreCase("ZIP CODE")) {
				iselementDisplayed = false;
			}
		} 
		
		if (common.isWebElementDisplayed(this.lblLastName)) {
			if (!this.lblLastName.getText().equalsIgnoreCase("LAST NAME")) {
				iselementDisplayed = false;
			}
		} 
		
		if (common.isWebElementDisplayed(this.lblDateofBirth)) {
			if (!this.lblDateofBirth.getText().equalsIgnoreCase("DATE OF BIRTH")) {
				iselementDisplayed = false;
			}
		}
		
		if (common.isWebElementDisplayed(this.lblStreetAddress)) {
			if (!this.lblStreetAddress.getText().equalsIgnoreCase("NUMERIC PORTION OF STREET ADDRESS OR P.O. BOX")) {
				iselementDisplayed = false;
			}
		} 
		
		return iselementDisplayed;
	}
	
	/**Method to set details for forgot Password and click onContinue
	 * 
	 * @param socialSecurityNumber - Social Security Number
	 * @param zipCode - Zip-code
	 * @param lastName - Participant last name
	 * @param dateOfBirth - Participant date of birth
	 * @param streetAddress - street address
	 */
	public void enterForgotPasswordDetails(String socialSecurityNumber,
									String zipCode,
									String lastName,
									String dateOfBirth,
									String streetAddress) {
				
		this.txtSSN.sendKeys(socialSecurityNumber);
		this.txtZipCode.sendKeys(zipCode);
		this.txtLastName.sendKeys(lastName);
		this.txtDateofBirth.sendKeys(dateOfBirth);
		this.txtStreetAddress.sendKeys(streetAddress);
		
		this.btnContinue.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
	}
	
	/**Method to capture error message generated by the user after entering invalid data in forgot Password page
	 * 
	 * @param NA
	 * @return <b>Error Message String</b> if error message is displayed. <b>null</b> otherwise.
	 */
	
	public String getMainErrorMsg() {
		String errMsg = "";
		
			if (common.isWebElementDisplayed(this.lblErrorMessage)) {
				errMsg = this.lblErrorMessage.getText();
			}
		
		
		return errMsg;
	} 
	
	/**Method to re-enter the new password during the forgot password flow
	 * @param password- Password from the datasheet
	 * @param reEnterPassword - password re-Entered
	 */
	
	public void helpResetMyPassword(String password,String reEnterPassword) {
			
			
			this.lnkNeedHelpWithPassword.click();
			try {
				common.waitForElement(this.lblRecoverYourAccount);
			} catch (Exception e) {
				//do nothing
			}
			
			Reporter.logEvent(Status.INFO, "Password Recovery Page", "User has successfully navigated to the Password Recovery Page", true);
			this.txtPassword.sendKeys(password);
			this.txtReEnterPassword.sendKeys(reEnterPassword);
			this.btnContinueAccRecovery.click();
			
			//verify the outcome of the above operation
			
			
			
		
		
		
	} 
	
}
