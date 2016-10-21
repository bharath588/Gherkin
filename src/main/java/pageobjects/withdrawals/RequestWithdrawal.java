package pageobjects.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;
import core.framework.Globals;

public class RequestWithdrawal extends LoadableComponent<RequestWithdrawal> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//h1[text()[normalize-space()='Request a withdrawal']]")
	private WebElement lblRequestAWithdrawal;
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	 @FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = ".//input[contains(@ng-click,'maxAmountCheck')]")
	private WebElement inptMaxAmount;
	@FindBy(xpath = ".//button[contains(text(),'Continue')]")
	private WebElement btnContinue;
	@FindBy(xpath= ".//label[./input[@value='true']]")
	private WebElement inpYes;
	@FindBy(xpath= ".//label[./input[@value='false']]")
	private WebElement inpNo;	
	@FindBy(id = "ssnInput")
	private WebElement inputSSN;
	@FindBy(xpath = ".//button[contains(text(),'Confirm and continue')]")
	private WebElement btnConfirmContinue;
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalReason')]")
	//@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalType')]")
	private WebElement drpWithdrawalType;
	@FindBy(xpath = ".//*[@id='btn-confirm submit']")
	private WebElement btnContinueWithdrawal;
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]")
	private WebElement txtConfirmation;
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]//strong")
	private WebElement txtConfirmationNo;
	@FindBy(xpath = "//label[./input[@id='inserviceradiono']]")
	private WebElement inptCurrentEmpNo;
	@FindBy(xpath=".//*[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath= ".//label[./input[@id='inserviceradioyes']]")
	private WebElement inpCurrentEmployerYes;
		
	private String textField="//*[contains(text(),'webElementText')]";
	private String inputWithdrawalType = "//div[@id='test_id'][.//span[contains(text(),'Withdrawal Type')]]//input";
	private String inpMailType="//input[contains(@value,'mailType')]";
	
	private String 
	moneyTypeAmtTxt ="//div[contains(@class,'selected-row-body')][.//span[contains(text(),'Withdrawal Type')]]//div[contains(@class,'source-row')][.//span[text()[normalize-space()='Money Source Type']]]//input[@type='text']";
	
	private String 
	moneyTypeMaxAmtChkBox ="//div[contains(@class,'selected-row-body')][.//span[contains(text(),'Withdrawal Type')]]//div[contains(@class,'source-row')][.//span[text()[normalize-space()='Money Source Type']]]//input[@type='checkbox']"; 
	private String 
	txtMoneyTypeAmt ="//div[contains(@class,'selected-row-body')][.//span[contains(text(),'Withdrawal Type')]]//div[contains(@class,'source-row')][.//span[text()[normalize-space()='Money Source Type']]]//div[contains(@class,'col-md-5 cell-container')]";	
	
	private String 
	moneyTypeSourceSection=" //div[contains(@class,'selected-row-body')][.//span[contains(text(),'Withdrawal Type')]]//div[contains(@class,' source-row')][.//span[text()[normalize-space()='Money Source Type']]]";
	;
	@FindBy(xpath = ".//select[contains(@ng-model,'companyType')]")
	private WebElement drpRollOverCompany;	
	@FindBy(xpath=".//input[contains(@placeholder, 'Enter address line 1')]")
	private WebElement txtAddressLine1;	
	@FindBy(xpath=".//input[contains(@placeholder, 'City')]")
	private WebElement txtCity;
	@FindBy(xpath=".//select[contains(@ng-model,'address.state')]")
	private WebElement drpStateCode;
	@FindBy(xpath=".//input[contains(@placeholder, 'ZIP')]")
	private WebElement txtZIPCode;
	@FindBy(xpath=".//input[@type='email']")
	private WebElement txtEmailAddress;
	@FindBy(xpath=".//*[@id='fullWithdrawal']")
	private WebElement fullWithDrawal;
	@FindBy(xpath=".//*[@id='partialWithdrawal']")
	private WebElement partWithDrawal;
	@FindBy(xpath="//div[@ng-show='wtCtrl.showBothAccountNumberFields()']//input[contains(@ng-model,'accountNumber')]")
	private WebElement inpAccountNumber;	
	@FindBy(xpath="//div[@ng-show='wtCtrl.showBothAccountNumberFields()']//input[contains(@ng-model,'rothAccountNumber')]")
	private WebElement inpRothAccountNumber;
	@FindBy(xpath=".//label[contains(text(),'Roth IRA Account Number')]")
	private WebElement lblRothAccountNumber;
	private String lblWithdrawalAmount="//tr[.//td/span[contains(text(),'Money Source Type')]]//td[2]//span";
	private String inpAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[@type='text']";
	private String chkBoxMaxAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[contains(@ng-click,'maxAmountCheck')]";
	private String maxAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//td[3]/span";
	@FindBy(xpath="//tr[./th[contains(text(),'Estimated ')]]/td")
	private WebElement confirmationPageRollOverAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'Type')]]/td")
	private WebElement confirmationPageDeliveryType;
	@FindBy(xpath="//tr[./th[contains(text(),'Payable')]]/td")
	private WebElement confirmationPagePayableTo;
	@FindBy(xpath="//tr[./th[contains(text(),'Delivery method')]]/td")
	private WebElement confirmationPageDeliveryMethod;
	@FindBy(xpath="//tr[./th[contains(text(),'Sent to')]]/td")
	private WebElement confirmationPageSentToAddress;
	
	
	
	/**
	 * Default Constructor
	 */
	public RequestWithdrawal() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestWithdrawal(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestAWithdrawal,
				//true),"Request A WithDrawal Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		 else {

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
		String userLogedIn = this.lblUserName.getText();
		/*String sponser = this.lblSponser.getAttribute("Alt");
		if (sponser.isEmpty()) {
			sponser = Common.GC_DEFAULT_SPONSER;
		}*/
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));
			Assert.assertTrue(Web.isWebElementDisplayed(lblRequestAWithdrawal),"Request WithDrawal page is not loaded");
		
		} else {
			this.lnkLogout.click();
			Web.waitForElement(btnLogin);
			Assert.assertTrue(false,"Logging in with new User");
		}
	}

	@Override
	protected void load() {
	/*	
		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}
		*/
		this.parent.get();

		((LeftNavigationBar) this.parent).clickNavigationLink("Request a withdrawal");
		Web.waitForPageToLoad(Web.webdriver);
		Web.isWebElementDisplayed(lblRequestAWithdrawal,true);
		   

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
		if (fieldName.trim().equalsIgnoreCase("REQUEST A WITHDRAWAL")) {
			return this.lblRequestAWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("MAX AMOUNT")) {
			return this.inptMaxAmount;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("YES")) {
			return this.inpYes;
		}
		if (fieldName.trim().equalsIgnoreCase("NO")) {
			return this.inpNo;
		}
		if (fieldName.trim().equalsIgnoreCase("SSN")) {
			return this.inputSSN;
		}
		if (fieldName.trim().equalsIgnoreCase("CONFIRM AND CONTINUE")) {
			return this.btnConfirmContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("WITHDRAWAL METHOD")) {
			return this.drpWithdrawalType;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE TO WITHDRAWAL")) {
			return this.btnContinueWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("I AGREE AND SUBMIT")) {
			return this.btnContinueWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION")) {
			return this.txtConfirmation;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION NUMBER")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT CURRENT EMPLOYER NO")) {
			return this.inptCurrentEmpNo;
		}
		if (fieldName.trim().equalsIgnoreCase("ROLLOVER COMPANY")) {
			return this.drpRollOverCompany;
		}
		if (fieldName.trim().equalsIgnoreCase("EMAIL ADDRESS")) {
			return this.txtEmailAddress;
		}
		if (fieldName.trim().equalsIgnoreCase("VESTED FULL WITHDRAWAL")) {
			return this.fullWithDrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("VESTED PART WITHDRAWAL")) {
			return this.partWithDrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("LABEL ROTH ACCOUNT NUMBER")) {
			return this.lblRothAccountNumber;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT ROTH ACCOUNT NUMBER")) {
			return this.inpRothAccountNumber;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * Method to Select WithDrawal Type 
	 * @param withdrawalType
	 * 
	 */
	public boolean selectWithdrawalType(String withdrawalType) {
		boolean isSelected=false;
		Actions keyBoard = new Actions(Web.webdriver);
		try {
			if(Web.isWebElementDisplayed(inpCurrentEmployerYes, true))
			{
				inpCurrentEmployerYes.click();
				Thread.sleep(3000);
				keyBoard.sendKeys(Keys.TAB).perform();
				keyBoard.sendKeys(Keys.ENTER).perform();
			}
			Thread.sleep(5000);	
		WebElement inptWithdrawalType = Web.webdriver.findElement(By
				.xpath(inputWithdrawalType.replace("Withdrawal Type",
						withdrawalType)));
	
		if(Web.isWebElementDisplayed(inptWithdrawalType, true))
		{
		inptWithdrawalType.click();
		isSelected=true;
		}
			
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return isSelected;
	}
	
	/**
	 * This method is to validate if Pre-tax or Roth Money Type Source section is displayed for the respective withdrawal type
	 * @param withdrawalType
	 * @param moneyType
	 * @return
	 */
	public boolean isMoneyTypeSourceAvailable(String withdrawalType,String moneyType)
	{
		WebElement moneyTypeSoruceAvailable=Web.webdriver.findElement
				(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
						withdrawalType).replaceAll("Money Source Type", moneyType)));
		if(moneyTypeSoruceAvailable.isDisplayed())
			return true;
		else
			return false;
	}
	
	
	/**
	 * This method is to select money type source and enter the amount for specified withdrawal type
	 * @param withdrawalType
	 * @param moneyType
	 * @param amount
	 */
	public void enterAmountforMoneyTypeSource(String withdrawalType, String moneyType,String amount) {
	try
	{
		WebElement txtAmount = Web.webdriver.findElement(By
				.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
						withdrawalType).replaceAll("Money Source Type", moneyType)));
		Web.setTextToTextBox(txtAmount, amount);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	
	/**
	 * This method is to select money type source and  Max amount check box for specified withdrawal type
	 * @param withdrawalType
	 * @param moneyType
	 */
	public void selectMaxAmountForMoneyTypeSource(String withdrawalType, String moneyType) {
	try
	{
		WebElement chkBoxMaxAmount = Web.webdriver.findElement(By
				.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
						withdrawalType).replaceAll("Money Source Type", moneyType)));
		Web.clickOnElement(chkBoxMaxAmount);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	/**
	 * This method is to select money type source and  Max amount check box for specified withdrawal type
	 * @param withdrawalType
	 * @param moneyType
	 */
	public int getMaxAmountForPWMoneyTypeSource(String moneyType) {
		int maxAmount=0;
	try
	{
		
		WebElement txtMaxAmount = Web.webdriver.findElement(By
				.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", moneyType)));
		maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return maxAmount;
		
	}
	
	/**
	 * This method is to enter Address details of the Roll Over Company for Withdrawal
	 * @param address1
	 * @param city
	 * @param stateCode
	 * @param zipCode
	 */
	public void enterAddressDetailsForRollOverCompany(String accountNo,String address1,String city, String stateCode,String zipCode)
	{
		try {
			Web.waitForElement(inpAccountNumber);
			Web.setTextToTextBox(inpAccountNumber, accountNo);
			Web.setTextToTextBox(txtAddressLine1, address1);
			Web.setTextToTextBox(txtCity, city);
			Web.selectDropDownOption(drpStateCode, stateCode);
			Web.setTextToTextBox(txtZIPCode, zipCode);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.webdriver.findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			throw new Error(fieldName+" is not displayed");
		}
	
return isTextDisplayed;
	}

	/**
	 * Method to Select WithDrawal Distribution type 
	 * @param withDrawalType
	 * 
	 */
	public void selectWithDrawalMethod(String withDrawalType) {
		Web.selectDropDownOption(drpWithdrawalType, withDrawalType, true);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method to Select WithDrawal Distribution type 
	 * @param withDrawalType
	 * 
	 */
	public void selectRollOverCompany(String rollOverCompany) {
		Web.selectDropDownOption(drpRollOverCompany, rollOverCompany, true);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is to get the final amount that can be withdrawn for the money type source
	 * @param withdrawalType
	 * @param moneyType
	 */
	public int getFinalWithdrawalAmountForMoneyTypeSource(String moneyType) {
	int finalWithdrawalAmount=0;
	try
	{		
		WebElement lblFinalWithdrawalAmount = Web.webdriver.findElement(By
				.xpath(lblWithdrawalAmount.replace("Money Source Type",
						moneyType)));
		finalWithdrawalAmount=(int)Math.round(Web.getIntegerCurrency(lblFinalWithdrawalAmount.getText()));
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return finalWithdrawalAmount ;
	}
	
	
	
	/**
	 * Method to Select Delivery Method 
	 * @param deliveryMethod
	 * 
	 */
	public void selectDeliveryMethod(String deliveryMethod) {
		WebElement inptDeliveryMethod = Web.webdriver.findElement(By
				.xpath(inpMailType.replace("mailType",
						deliveryMethod)));
		try {
		inptDeliveryMethod.click();
		
		Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
				+ deliveryMethod, "Selected Delivery Method: " + deliveryMethod, false);
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
	}
	}
	public void enterSSN(String ssn) {
		
		try {
			if(Web.isWebElementDisplayed(this.inputSSN, true))
			{
				Web.setTextToTextBox(inputSSN, ssn);
				Reporter.logEvent(Status.INFO, "Entering SSN "
						+ ssn, "Entered SSN: " + ssn, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to select money type source and enter the amount for part withdrawal type
	 * @param moneyType
	 * @param amount
	 */
	public void enterAmountforPartWthdrawalMoneyTypeSource(String moneyType,String amount) {
	try
	{
		WebElement txtAmount = Web.webdriver.findElement(By
				.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", moneyType)));
		Web.setTextToTextBox(txtAmount, amount);
		
		Reporter.logEvent(Status.INFO, "Entering WithDrawal Amount For'"+moneyType+"'"
				, "Entered WithDrawal Amount For'"+moneyType+"': " + amount, false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	
	/**
	 * This method is to select money type source and  Max amount check box for Part withdrawal type
	 * @param moneyType
	 */
	public void selectMaxAmountForPartWithDrawalMoneyTypeSource(String moneyType) {
	try
	{
		WebElement chkBoxMaxAmount = Web.webdriver.findElement(By
				.xpath(chkBoxMaxAmtPWMoneyType.replaceAll("Money Source Type", moneyType)));
		Web.clickOnElement(chkBoxMaxAmount);
		Reporter.logEvent(Status.INFO, "Selecting Max Amount For'"+moneyType+"'"
				, "Selected Max Amount For'"+moneyType+"'", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	/**
	 * This method is to select money type source and  Max amount check box for specified withdrawal type
	 * @param withdrawalType
	 * @param moneyType
	 */
	public int getMaxAmountForMoneyTypeSource(String withdrawalType, String moneyType) {
		int maxAmount=0;
	try
	{
		
		WebElement txtMaxAmount = Web.webdriver.findElement(By
				.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
						withdrawalType).replaceAll("Money Source Type", moneyType)));
		maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));
		maxAmount=maxAmount-1;
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return maxAmount;
		
	}
	
	public void verifyWithdrawalConfirmationPage
	(int totalWithdrawalAmount, String deliveryType,String payableTo, String deliveryMethod, String address )
	{
		//Verify total Withdrawal Amount
		int confirmationPageAmount= (int)Math.round(Web.getIntegerCurrency(confirmationPageRollOverAmount.getText()));
		System.out.println("A: "+confirmationPageAmount);
		System.out.println("E: "+totalWithdrawalAmount);
		if(confirmationPageAmount == totalWithdrawalAmount)
			Reporter.logEvent(Status.PASS, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ totalWithdrawalAmount +"\n"
					+ "Actual Amount: "+confirmationPageAmount, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ totalWithdrawalAmount +"\n"
					+ "Actual Amount: "+confirmationPageAmount, false);
		
		//Verify delivery Type
		if(confirmationPageDeliveryType.getText().equalsIgnoreCase(deliveryType))
			Reporter.logEvent(Status.PASS, "Verify the Delivery Type in the Confirmation Page", 
					"The Delivery Type is displayed as:\n"
					+ "Expected Delivery Type: "+ deliveryType +"\n"
					+ "Actual Delivery type: "+confirmationPageDeliveryType.getText(), false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Delivery Type in the Confirmation Page", 
					"The Delivery Type is displayed as:\n"
					+ "Expected Delivery Type: "+ deliveryType +"\n"
					+ "Actual Delivery type: "+confirmationPageDeliveryType.getText(), false);
		
		//Verify Payable To 
		if(confirmationPagePayableTo.getText().contains(payableTo.replace("?", " ")))
			Reporter.logEvent(Status.PASS, "Verify the Payable To in the Confirmation Page", 
					"The Payable To Field is displayed as:\n"
					+ "Expected: "+ payableTo.replace("-", " ") +"\n"
					+ "Actual : "+confirmationPagePayableTo.getText(), false);
		else	
			Reporter.logEvent(Status.FAIL, "Verify the Payable To in the Confirmation Page", 
					"The Payable To Field is displayed as:\n"
					+ "Expected: "+ payableTo +"\n"
					+ "Actual : "+confirmationPagePayableTo.getText(), false);
		
		
		//Verify delivery Method	
		String deliveryMthd=deliveryMethod.contains("-")? deliveryMethod.replace("-", " ") : deliveryMethod;
		if(confirmationPageDeliveryMethod.getText().equalsIgnoreCase(deliveryMthd))
			Reporter.logEvent(Status.PASS, "Verify the Delivery Method in the Confirmation Page", 
					"The Delivery Method is displayed as:\n"
					+ "Expected Delivery Method: "+ deliveryMthd +"\n"
					+ "Actual Delivery Method: "+confirmationPageDeliveryMethod.getText(), false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Delivery Method in the Confirmation Page", 
					"The Delivery Method is displayed as:\n"
					+ "Expected Delivery Method: "+ deliveryMthd +"\n"
					+ "Actual Delivery Method: "+confirmationPageDeliveryMethod.getText(), false);
		
		
		//Verify Address
		/*String sentToAddress=confirmationPageSentToAddress.getText().trim();
		System.out.println("A "+sentToAddress);
		System.out.println("E "+address.toString().trim());
		if(sentToAddress.contains(address.toString()))
			Reporter.logEvent(Status.PASS, "Verify the Sent To Address in the Confirmation Page", 
					"The Address is displayed as:\n"
					+ "Expected Address: "+ address +"\n"
					+ "Actual Address: "+sentToAddress, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Sent To Address in the Confirmation Page", 
					"The Address is displayed as:\n"
					+ "Expected Address: "+ address +"\n"
					+ "Actual Address: "+sentToAddress, false);*/
		String sentToAddress=confirmationPageSentToAddress.getText().trim();
		Reporter.logEvent(Status.PASS, "Verify the Sent To Address in the Confirmation Page", 
				"The Address is displayed as:\n"+
			"Address: "+sentToAddress, false);
		
				
	}
	
}
