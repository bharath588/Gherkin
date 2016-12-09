
package pageobjects.userregistration;

import lib.Reporter;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;






import org.testng.Assert;

import appUtils.Common;
import core.framework.Globals;
import pageobjects.login.LoginPage;

public class AccountLookup extends LoadableComponent<AccountLookup> {

	//Declarations
	private LoadableComponent<?> parent;
	
	//Objects and locators declaration
	@FindBy(xpath=".//*[text()[normalize-space()='Registration account lookup']]/..") private WebElement lblAccLookupHeaderTextBlock;
	@FindBy(linkText="I do not have a PIN") private WebElement tabIDoNotHaveaPIN;
	@FindBy(linkText="I have a PIN") private WebElement tabIHaveaPIN;
	@FindBy(linkText="I have a group account password") private WebElement tabIHaveaGroupAccPassword;
	@FindBy(id="ssnInput") private WebElement txtSSN;
	@FindBy(id="zipCodeInput") private WebElement txtZipCode;
	@FindBy(id="lastNameInput") private WebElement txtLastName;
	@FindBy(id="dateOfBirth") private WebElement txtDateOfBirth;//dateOfBirthInput
	@FindBy(id="streetAddressInput") private WebElement txtStreetAddress;
	//@FindBy(id="pinInput") private WebElement txtPINInput;
	@FindBy(xpath=".//*[@id='registrationContent']/.//*[@id='pinInput']") private WebElement txtPINInput;
//	@FindBy(xpath=".//*[@id='submit' and @value='Continue']") private WebElement btnContinue;
	@FindBy(xpath=".//*[@id='registrationContent']/.//*[normalize-space()='CONTINUE' and @id='submit']") private WebElement btnContinue;
	@FindBy(xpath=".//*[@id='noPin']/a") private WebElement btnContinueOnGroupAccPasswordTab;
	@FindBy(xpath=".//ng-message[@class='form-validation-rule']//span[contains(text(),'Social Security Number')]") private WebElement lblSSNErrMsg;
	@FindBy(xpath="//ng-message[@class='form-validation-rule']//span[(contains(text(),'ZIP Code') or contains(text(),'Zip Code'))]") private WebElement lblZipCodeErrMsg;
	@FindBy(xpath="//ng-message[@class='form-validation-rule']//span[contains(text(),'Last Name')]") private WebElement lblLastNameErrMsg;
	@FindBy(xpath="//ng-message[@class='form-validation-rule']//span[contains(text(),'Date') or contains(text(),'date')]") private WebElement lblDOBErrMsg;
	@FindBy(xpath=".//ng-message[@class='form-validation-rule']//span[contains(text(),'Street Address')]]") private WebElement lblStrAddrErrMsg;
	@FindBy(xpath=".//ng-message[@class='form-validation-rule']//span[contains(text(),'PIN')]") private WebElement lblPINErrMsg;
	@FindBy(xpath=".//*[@class='form-group has-error ng-scope'][1] | .//*[@class='form-group has-error'][1]") private WebElement lblMainErrMsg;
	@FindBy(xpath = ".//div[@class='container']/span[@ng-if='accuLogoLoaded']/img")
	private WebElement lblSponser;
	@FindBy(xpath=".//*[@id='passwordInput' and @name='password']") private WebElement txtPassword;
	/** Empty args constructor
	 * 
	 */
	public AccountLookup() {
		this.parent = new LoginPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public AccountLookup(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblAccLookupHeaderTextBlock),"Account LookUp Page is Not Loaded");
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
		Web.waitForPageToLoad(Web.webdriver);
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	I DO NOT HAVE A PIN - [Tab]
	 * 	I HAVE A PIN - [Tab]
	 * 	I HAVE A GROUP ACCOUNT PASSWORD - [Tab]
	 * 	SOICAL SECURITY NUMBER or SSN - [TextBox]
	 * 	ZIP CODE - [TextBox]
	 * 	LAST NAME - [TextBox]
	 * 	DATE OF BIRTH - [TextBox]
	 * 	STREET ADDRESS - [TextBox]
	 * 	PIN - [TextBox]
	 * 	CONTINUE - [Button]
	 * 	ERR_SOICAL SECURITY NUMBER or ERR_SSN - [Error Message]
	 * 	ERR_ZIP CODE - [Error Message]
	 * 	ERR_LAST NAME - [Error Message]
	 * 	ERR_DATE OF BIRTH - [Error Message]
	 * 	ERR_STREET ADDRESS - [Error Message]
	 * 	ERR_PIN - [Error Message]
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	private WebElement getWebElement(String fieldName) {
		//I do not have a PIN
		if (fieldName.trim().equalsIgnoreCase("I DO NOT HAVE A PIN")) {
			return this.tabIDoNotHaveaPIN;
		}
		
		//I do not have a PIN
		if (fieldName.trim().equalsIgnoreCase("I HAVE A PIN")) {
			return this.tabIHaveaPIN;
		}
		
		//I have a group account password
		if (fieldName.trim().equalsIgnoreCase("I HAVE A GROUP ACCOUNT PASSWORD")) {
			return this.tabIHaveaGroupAccPassword;
		}
		
		//Social Security Number
		if (fieldName.trim().equalsIgnoreCase("SOCIAL SECURITY NUMBER") || 
				fieldName.trim().equalsIgnoreCase("SSN")) {
			return this.txtSSN;
		}
		
		//ZIP CODE
		if (fieldName.trim().equalsIgnoreCase("ZIP CODE")) {
			return this.txtZipCode;
		}
		
		//LAST NAME
		if (fieldName.trim().equalsIgnoreCase("LAST NAME")) {
			return this.txtLastName;
		}
		
		//DATE OF BIRTH
		if (fieldName.trim().equalsIgnoreCase("DATE OF BIRTH")) {
			return this.txtDateOfBirth;
		}
		
		//STREET ADDRESS
		if (fieldName.trim().equalsIgnoreCase("STREET ADDRESS")) {
			return this.txtStreetAddress;
		}
		
		//PIN
		if (fieldName.trim().equalsIgnoreCase("PIN")) {
			return this.txtPINInput;
		}
		
		//ERR_SOCIAL SECURITY NUMBER
		if (fieldName.trim().equalsIgnoreCase("ERR_SOCIAL SECURITY NUMBER")) {
			return this.lblSSNErrMsg;
		}
		
		//ERR_ZIP CODE
		if (fieldName.trim().equalsIgnoreCase("ERR_ZIP CODE")) {
			return this.lblZipCodeErrMsg;
		}
		
		//ERR_LAST NAME
		if (fieldName.trim().equalsIgnoreCase("ERR_LAST NAME")) {
			return this.lblLastNameErrMsg;
		}
		
		//ERR_DATE OF BIRTH
		if (fieldName.trim().equalsIgnoreCase("ERR_DATE OF BIRTH")) {
			return this.lblDOBErrMsg;
		}
		
		//ERR_STREET ADDRESS
		if (fieldName.trim().equalsIgnoreCase("ERR_STREET ADDRESS")) {
			return this.lblStrAddrErrMsg;
		}
		
		//ERR_PIN
		if (fieldName.trim().equalsIgnoreCase("ERR_PIN")) {
			return this.lblPINErrMsg;
		}
		
		//Continue button
		if (fieldName.trim().equalsIgnoreCase("CONTINUE")) {
			if (this.getActiveTabName().equalsIgnoreCase("I have a group account password")) {
				//Continue on "I have a group account password" Tab
				return this.btnContinueOnGroupAccPasswordTab;
			} else {
				return this.btnContinue;
			}
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		return null;
	}
	
	/**Method to set details under 'I do not have a PIN' tab and click onContinue
	 * 
	 * @param socialSecurityNumber - Social Security Number
	 * @param zipCode - Zip-code
	 * @param lastName - Participant last name
	 * @param dateOfBirth - Participant date of birth
	 * @param streetAddress - street address
	 */
	public void registerWithoutPIN(String socialSecurityNumber,
									String zipCode,
									String lastName,
									String dateOfBirth,
									String streetAddress) {
				
		this.tabIDoNotHaveaPIN.click();
		
		this.txtSSN.sendKeys(socialSecurityNumber);
		this.txtZipCode.sendKeys(zipCode);
		this.txtLastName.sendKeys(lastName);
		this.txtDateOfBirth.sendKeys(dateOfBirth);
		this.txtStreetAddress.sendKeys(streetAddress);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.btnContinue.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
	}
	
	/**Method to set details under <b>I have a PIN</b> tab and click on <b>Continue</b> button
	 * 
	 * @param socialSecurityNumber - Social Security Number
	 * @param verificatinPIN - PIN which participant already have
	 * @throws InterruptedException 
	 */
	public void registrationWithPIN(String socialSecurityNumber, String verificatinPIN) throws InterruptedException {
		this.tabIHaveaPIN.click();
//		try {
//			common.waitForElement(txtSSN);
//		} catch (Exception e1) {
//		}
		Thread.sleep(5000);
		this.txtSSN.sendKeys(socialSecurityNumber);
		this.txtPINInput.sendKeys(verificatinPIN);
		
		Thread.sleep(5000);
		this.btnContinue.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	
	/**Method to set details under <b>Group Account </b> tab and click on <b>Continue</b> button
	 * 
	 * @param : NA
	 */
	
	public void registerWithGroupAccPassword() {
		this.tabIHaveaGroupAccPassword.click();
		this.btnContinueOnGroupAccPasswordTab.click();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** Method to return text displayed in Account look up header block
	 * 
	 * @return <b>Header block text</b> if element present and text is displayed.
	 *	<b>Empty string</b> if no text is displayed.
	 *	<b>null</b> if header block element is not displayed.
	 */
	public String getAccountLookupHeaderBlockText() {
		
		boolean isHeaderPresent = Web.isWebElementDisplayed(this.lblAccLookupHeaderTextBlock);
		String headerText = "";
		
		if (isHeaderPresent) {
			headerText = this.lblAccLookupHeaderTextBlock.getText().trim();
		} else {
			return null;
		}
		
		return headerText;
	}

	/** Method to return name of current open tab
	 * 
	 * @return <b>String - Tab Name</b> if successful in identifying active tab. 
	 * <b>Empty string</b> if failed to identify active tab.
	 */
	public String getActiveTabName() {
		String activeTab = "";
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// Do Nothing
		}

		if (Web.getParent(this.tabIDoNotHaveaPIN).getAttribute("class").toUpperCase().contains("ACTIVE")) {
			activeTab = "I do not have a PIN";
		} else if (Web.getParent(this.tabIHaveaPIN).getAttribute("class").toUpperCase().contains("ACTIVE")) {
			activeTab = "I have a PIN";
		} else if (Web.getParent(this.tabIHaveaGroupAccPassword).getAttribute("class").toUpperCase().contains("ACTIVE")) {
			activeTab = "I have a group account password";
		} else {
			activeTab = "";
		}
		
		return activeTab;
	}

	/** <pre> Method to navigate to the specified tab
	 * Tabs:
	 * 	I have a PIN
	 * 	I do not have a PIN
	 * 	I have a group account Password</pre>
	 * 
	 * @param tabName - String
	 * @return <b>true</b> if successful. <b>false</b> otherwise.
	 */
	public boolean navigateToTab(String tabName) {
		boolean isSuccess = false;
		
		if (tabName.trim().equalsIgnoreCase("I do not have a PIN")) {
			this.tabIDoNotHaveaPIN.click();
		} else if(tabName.trim().equalsIgnoreCase("I have a PIN")) {
			this.tabIHaveaPIN.click();
		} else if(tabName.trim().equalsIgnoreCase("I have a group account password")) {
			this.tabIHaveaGroupAccPassword.click();
		} else {
			return false;
		}
		
		if (this.getActiveTabName().equalsIgnoreCase(tabName.trim())) {
			isSuccess = true;
		}
		
		return isSuccess;
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

	/** <pre> Method to set value to text box and,
	 * returns the value as accepted and displayed by the text field after entering.
	 * returns empty string if unable to find the element or set value to element.</pre>
	 * 
	 * @param fieldName - Name of text box as displayed on page
	 * @param valueToSet - Text value to be set in the text box
	 * @return <b>String</b> - <b>Value</b> attribute of the text box after setting specified text.
	 */
	public String setTextToFields(String fieldName, CharSequence... valueToSet) {
		WebElement element = this.getWebElement(fieldName);
		return Web.setTextToTextBox(element, valueToSet);
	}
	
	/** Method to click on specified field element.
	 * 
	 * @param fieldName - Name of field on which click action is to be performed
	 * @return <b>true</b> if successful. <b>false</b> otherwise.
	 */
	public boolean clickOnFields(String fieldName) {
		WebElement element = this.getWebElement(fieldName);
		return Web.clickOnElement(element);
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
			Web.waitForPageToLoad(Web.webdriver);
			Web.waitForElement(element);
			if (Web.isWebElementDisplayed(element)) {
				errMsg = element.getText();
			} else {
				errMsg = "";
			}
		}
		
		return errMsg;
	}
	
	public String getMainErrorMsg() {
		String errMsg = "";
		
			if (Web.isWebElementDisplayed(this.lblMainErrMsg)) {
				errMsg = this.lblMainErrMsg.getText();
			}
		
		
		return errMsg;
	} 
}
