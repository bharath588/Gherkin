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
	@FindBy(xpath = ".//select[contains(@ng-model,'withDrawalType')]")
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
	@FindBy(xpath="//div[contains(@ng-show,'showAccountNumberField')]//input[contains(@ng-model,'accountNumber')]")
	private WebElement inpAccountNumber;	
	private String lblWithdrawalAmount="//tr[.//td/span[contains(text(),'Money Source Type')]]//td[2]//span";
	private String inpAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[@type='text']";
	private String chkBoxMaxAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[contains(@ng-click,'maxAmountCheck')]";
	private String maxAmtPWMoneyType="//tr[./td[contains(text(),'Pre-tax')]]//td[3]/span";
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
			}
			Thread.sleep(3000);
			keyBoard.sendKeys(Keys.TAB).perform();
			keyBoard.sendKeys(Keys.ENTER).perform();
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
			lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
					+ "  is Displayed", fieldName + " is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
					+ " is Displayed",
					fieldName + " is Not Displayed", false);
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
	public void selectDelivaryMethod(String deliveryMethod) {
		WebElement inptDeliveryMethod = Web.webdriver.findElement(By
				.xpath(inpMailType.replace("mailType",
						deliveryMethod)));
		inptDeliveryMethod.click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void enterSSN(String ssn) {
		
		try {
			if(Web.isWebElementDisplayed(this.inputSSN, true))
			{
				Web.setTextToTextBox(inputSSN, ssn);
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
		maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1]));
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return maxAmount;
		
	}
	
}
