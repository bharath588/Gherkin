package pageobjects.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
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
	//@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalReason')]")
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalType')]")
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
	private String inputWithdrawalType ="//div[@id='test_id']/input[@value='Withdrawal Type']"; 
			//"//div[@id='test_id'][.//span[contains(text(),'Withdrawal Type')]]//input";			
	private String inpMailType="//input[contains(@value,'mailType')]";
	@FindBy(id="deliveryOptionsUSPS")
	private WebElement inpFirstClassMail;
	@FindBy(id="deliveryOptionsExpress")
	private WebElement inpExpressMail;
	private String 
	moneyTypeAmtTxt ="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//input[@type='text']";
	
	private String 
	moneyTypeMaxAmtChkBox =
	"//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//input[@type='checkbox']"; 
	private String 
	txtMoneyTypeAmt ="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//div[contains(@ng-if,'isGenericDisbRuleForInService')]";	
	
	private String 
	moneyTypeSourceSection="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,' source-row')][.//label[text()[normalize-space()='Money Source Type']]]";
	
	
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
	@FindBy(xpath=".//input[contains(@ng-model,'email')]")
	private WebElement txtEmailAddress;
	@FindBy(xpath=".//div[contains(@ng-bind,'address.firstLineMailing')]")
	private WebElement txtInpAddress1;
	@FindBy(xpath=".//span[contains(@ng-bind,'address.city')]")
	private WebElement txtInpCity;
	@FindBy(xpath=".//span[contains(@ng-bind,'address.state')]")
	private WebElement txtState;
	@FindBy(xpath=".//span[contains(@ng-bind,'address.postalCode')]")
	private WebElement txtPostalCode;
	@FindBy(xpath=".//*[@id='fullWithdrawal']")
	private WebElement fullWithDrawal;
	@FindBy(xpath=".//*[@id='partialWithdrawal']")
	private WebElement partWithDrawal;
	@FindBy(xpath=".//label[contains(text(),'part of my vested balance')]")
	private WebElement lblPartWithdrawal;
	@FindBy(xpath="//div[@ng-show='wtCtrl.showBothAccountNumberFields()']//input[contains(@ng-model,'accountNumber')]")
	private WebElement inpAccountNumber;	
	@FindBy(xpath="//div[@ng-show='wtCtrl.showBothAccountNumberFields()']//input[contains(@ng-model,'rothAccountNumber')]")
	private WebElement inpRothAccountNumber;
	@FindBy(xpath=".//label[contains(text(),'Roth IRA Account Number')]")
	private WebElement lblRothAccountNumber;
	private String lblTotalWithdrawalAmount="//tr[.//td/span[contains(text(),'Money Source Type')]]//td[2]//span";
	private String inpAmtPWMoneyType="//div[contains(@ng-repeat,'partialWithdrawalData')][.//div[text()[normalize-space()='Money Source Type']]]//input[@type='text']";
	private String chkBoxMaxAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[contains(@ng-click,'maxAmountCheck')]";
	private String maxAmtPWMoneyType="//div[contains(@ng-repeat,'partialWithdrawalData')][.//div[text()[normalize-space()='Money Source Type']]]//div[contains(@ng-if,'isGenericDisbRuleForSepService')]";
	@FindBy(xpath="//tr[./th[contains(text(),'ESTIMATED ')]]/td")
	private WebElement confirmationPageRollOverAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'TYPE')]]/td")
	private WebElement confirmationPageDeliveryType;
	@FindBy(xpath="//tr[./th[contains(text(),'Payable')]]/td")
	private WebElement confirmationPagePayableTo;
	@FindBy(xpath="//tr[./th[contains(text(),'DELIVERY METHOD')]]/td")
	private WebElement confirmationPageDeliveryMethod;
	@FindBy(xpath="//tr[./th[contains(text(),'Sent to')]]/td")
	private WebElement confirmationPageSentToAddress;
	
	private static int enteredRothWithdrawalAmt = 0;
	private static int enteredPreTaxWithdrawalAmt = 0;
	private static int enteredTotalWithdrawalAmt = 0;
	private static int finalWithdrawalAmount=0;
	private String deliveryMthd=null;
	
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
			Assert.assertTrue(false,"");
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
		Common.waitForProgressBar();
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
	
	public void selectWithdrawalTypeForInService(String withdrawalType,String rothSection,String preTaxSection)
	{
		try {
		boolean isWithdrawalTypeSelected =false;
		isWithdrawalTypeSelected=selectWithdrawalType(withdrawalType);
		if (isWithdrawalTypeSelected) {
			Reporter.logEvent(
					Status.PASS,"Verify if "+ withdrawalType+ " WithDrawal Type is Selected",
					withdrawalType + " WithDrawal Type is Selected", true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify if "
							+ Stock.GetParameterValue("withdrawalType")
							+ " WithDrawal Type is Selected",
					Stock.GetParameterValue("withdrawalType")
							+ " WithDrawal Type is NOT Selected", true);
		}
		//Enter Amount For Roth Money Section
		if(rothSection.equalsIgnoreCase("Yes"))
		{
			WebElement rothMoneyTypeSourceAvailable=Web.webdriver.findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", "Roth")));
			if(Web.isWebElementDisplayed(rothMoneyTypeSourceAvailable, true))
			{
				int maxAmount=0;			
				enteredRothWithdrawalAmt = 0;
					WebElement txtMaxAmount = Web.webdriver.findElement(By
							.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", "Roth")));
					maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));
					maxAmount=maxAmount-1;	
					enteredRothWithdrawalAmt=maxAmount;
			//	enteredRothWithdrawalAmt = 2000;
					WebElement txtAmount = Web.webdriver.findElement(By
							.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", "Roth")));
					Web.setTextToTextBox(txtAmount, Integer.toString(enteredRothWithdrawalAmt));
					Reporter.logEvent(
							Status.PASS,
							"Verify if User entered withdrawal amount for Roth Money Type",
							"The user have entered " + enteredRothWithdrawalAmt
									+ " to be withdrawn from roth Money Type",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for Roth Money Type",
							"Roth Money Type section is NOT Displayed", false);
					throw new Error("Roth Money Type Section is NOT displayed");
				}
		}
		else			
				enteredRothWithdrawalAmt = 0;
		
		//enter Amount for Pre-tax Amount Section
		if(preTaxSection.equalsIgnoreCase("Yes"))
		{
			WebElement preTaxMoneyTypeSourceAvailable=Web.webdriver.findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", "Pre-tax")));
			if(Web.isWebElementDisplayed(preTaxMoneyTypeSourceAvailable, true))
			{
				int maxAmount=0;			
				enteredPreTaxWithdrawalAmt = 0;
					WebElement txtMaxAmount = Web.webdriver.findElement(By
							.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", "Pre-tax")));
					maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));
					maxAmount=maxAmount-1;	
					enteredPreTaxWithdrawalAmt=maxAmount;
			//	enteredPreTaxWithdrawalAmt = 2000;
					WebElement txtAmount = Web.webdriver.findElement(By
							.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", "Pre-tax")));
					Web.setTextToTextBox(txtAmount, Integer.toString(enteredPreTaxWithdrawalAmt));
					Reporter.logEvent(
							Status.PASS,
							"Verify if User entered withdrawal amount for Pre Tax Money Type",
							"The user have entered " + enteredPreTaxWithdrawalAmt
									+ " to be withdrawn from Pre Tax Money Type",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for Pre Tax Money Type",
							"Pre Tax Money Type section is NOT Displayed", false);
					throw new Error("Pre Tax Money Type Section is NOT displayed");
				}
		}
		else			
			enteredPreTaxWithdrawalAmt = 0;
		
		// Click on Continue
		if (Web.isWebElementDisplayed(btnContinue, true)) {
			Web.clickOnElement(btnContinue);
			Reporter.logEvent(
					Status.PASS,
					"Select "+ withdrawalType+ " option, and enter withdrawal amount for multiple / single  money type sources and click Continue",
					"User enters the withdrawal amount for multiple money types source and clicked on continue button",
					false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Select "+ withdrawalType+ " option, and enter withdrawal amount for both Roth and Pre-tax  money type sources and Click Continue",
					"Continue button is not displayed in Request a Withdrawal Page",
					true);
			throw new Error("'Continue' is not displayed");
		}
		Thread.sleep(2000);			
		}
		catch(Exception e){
		e.printStackTrace();}	
		
	}
	
	public void selectWithdrawalTypeForSepService(String withdrawalType, String rothSection,String preTaxSection)
	{
		try {			
		if(Web.isWebElementDisplayed(partWithDrawal) && withdrawalType.equalsIgnoreCase("pwd"))
		{
			Reporter.logEvent(Status.PASS,
					"Verify Vested Part Withdrawal section is Displayed",
					"Vested Part Withdrawal section is displayed", false);
			partWithDrawal.click();
		}
		else if(Web.isWebElementDisplayed(fullWithDrawal)&& withdrawalType.equalsIgnoreCase("fwd"))
		{
			Reporter.logEvent(Status.PASS,
					"Verify Vested Full Withdrawal section is Displayed",
					"Vested Full withdrawal section is displayed", false);
			fullWithDrawal.click();
		}
		else
		{
			Reporter.logEvent(Status.FAIL,
					"Verify Vested Part/Full Withdrawal section is Displayed",
					"Vested Part/Full  Withdrawal section is NOT displayed", true);
			throw new Error("Vested Full/Part Withdrawal Section is NOT displayed");
		}
			
		Thread.sleep(2000);	
		//enter Amount for Part Withdrawal
		if (rothSection.equalsIgnoreCase("Yes")) {
			WebElement partWithdrawalMoneySourceAvailable=Web.webdriver.findElement
					(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", "Roth")));
			if(Web.isWebElementDisplayed(partWithdrawalMoneySourceAvailable, true))
			{
				int maxAmount=0;
				enteredRothWithdrawalAmt=0;
				WebElement txtMaxAmount = Web.webdriver.findElement(By
						.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", "Roth")));
				maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));
				maxAmount=maxAmount-1;
				enteredRothWithdrawalAmt=maxAmount;
				
				WebElement txtAmount = Web.webdriver.findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", "Roth")));
				Web.setTextToTextBox(txtAmount, Integer.toString(enteredRothWithdrawalAmt));
				
				Reporter.logEvent(Status.INFO, "Entering WithDrawal Amount For Roth Money Type",
						 "Entered WithDrawal Amount For Roth: " + enteredRothWithdrawalAmt, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if User entered withdrawal amount for Roth Money Type",
						"Roth Money Type section is NOT Displayed", false);
				throw new Error("Roth Money Type Section is NOT displayed");
			}
	}
	else			
		enteredRothWithdrawalAmt = 0;
		
	 if(preTaxSection.equalsIgnoreCase("Yes"))
	{
			WebElement partWithdrawalMoneySourceAvailable=Web.webdriver.findElement
					(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", "Pre-tax")));
			if(Web.isWebElementDisplayed(partWithdrawalMoneySourceAvailable, true))
			{
				int maxAmount=0;
				enteredPreTaxWithdrawalAmt=0;
				WebElement txtMaxAmount = Web.webdriver.findElement(By
						.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", "Pre-tax")));
				maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));
				maxAmount=maxAmount-1;
				enteredPreTaxWithdrawalAmt=maxAmount;
				
				WebElement txtAmount = Web.webdriver.findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", "Pre-tax")));
				Web.setTextToTextBox(txtAmount, Integer.toString(enteredPreTaxWithdrawalAmt));
				
				Reporter.logEvent(Status.INFO, "Entering WithDrawal Amount For Pre-Tax Money Type",
						 "Entered WithDrawal Amount For Pre-Tax: " + enteredPreTaxWithdrawalAmt, false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if User entered withdrawal amount for Pre Tax Money Type",
						"Pre Tax Money Type section is NOT Displayed", false);
				throw new Error("Pre Tax Money Type Section is NOT displayed");
			}
	}
	else			
	enteredPreTaxWithdrawalAmt = 0;	
		// Click on Continue
	 
	 if (Web.isWebElementDisplayed(btnContinue, true)) {
		 Web.clickOnElement(btnContinue);
		 Reporter.logEvent(Status.PASS,
					"Verify if user enters withdrawal amount for multiple / single  money type sources and click Continue button",
					"User enters the withdrawal amount for multiple money types source and clicked on continue button",
					false);
		} else {
			 Reporter.logEvent(Status.FAIL,"Verify if user enters withdrawal amount for multiple / single  money type sources and click Continue button",
						"User enters the withdrawal amount for multiple money types source and clicked on continue button",
						false);
			throw new Error("'Continue' is not displayed");
		}
		Thread.sleep(2000);			
		}
		catch(Exception e){
		e.printStackTrace();}	
	}
	
	public void citizenShipValidation(String SSN)
	{
		try {	
		//US Citizenship and SSN Validation
		if(isTextFieldDisplayed("Plan withdrawal"))
			isTextFieldDisplayed("Are you a U.S. citizen or resident?");	
		Web.clickOnElement(inpYes);				
		Thread.sleep(3000);
		if (isTextFieldDisplayed("Please enter your Social Security number.")) {
			enterSSN(SSN);
			Reporter.logEvent(
					Status.PASS,
					"Verify if user enters Social Security number.",
					"User enters Social Security number "+ SSN+ " in the field which is displayed", true);
		} else
			Reporter.logEvent(
					Status.FAIL,
					"Verify if user enters Social Security number.",
					"User enters Social Security number "+ SSN+ " in the field which is displayed", true);

		// Click on Confirm and Continue
		Web.clickOnElement(btnConfirmContinue);
		Thread.sleep(4000);
		}
		catch(Exception e){
		e.printStackTrace();}	
	}
	
	public void verifyWithdrawalMethodPage(String withdrawalMethodType,String emailAddress)
	{
		try {
		if (isTextFieldDisplayed("Withdrawal method"))
		{
		isTextFieldDisplayed("How would you like your withdrawal distributed?");
		if (Web.selectDropDownOption(drpWithdrawalType,withdrawalMethodType,true))
		Reporter.logEvent(Status.PASS,"Verify if User selects "+withdrawalMethodType+" withdrawal distribution option",
						"User selects "+withdrawalMethodType+" withdrawal distribution option",true);
		else
		Reporter.logEvent(Status.FAIL,"Verify if User selects "+withdrawalMethodType+" withdrawal distribution option",
						"User did NOT select "+withdrawalMethodType+" withdrawal distribution option",true);
			Thread.sleep(1000);
		
			if(!withdrawalMethodType.equalsIgnoreCase("Payment to Self"))
			{
			String mailingAddressLine1=null;
			String state=null;
			String city=null;
			String postalCode=null;			

			Web.isWebElementDisplayed(drpRollOverCompany, true);
			Web.selectDropnDownOptionAsIndex(drpRollOverCompany, "5");		
			Thread.sleep(1000);
			mailingAddressLine1=txtInpAddress1.getText();
			state=txtState.getText();
			city=txtInpCity.getText();
			postalCode=txtPostalCode.getText().split("-")[0];
			
			//Setting text box
			Web.waitForElement(inpAccountNumber);
			Web.setTextToTextBox(inpAccountNumber, "123456");
			Web.setTextToTextBox(txtAddressLine1, mailingAddressLine1);
			Web.setTextToTextBox(txtCity, city);
			Web.selectDropDownOption(drpStateCode, state);
			Web.setTextToTextBox(txtZIPCode, postalCode);	
			}
		// Verify if Confirm your contact information section is displayed
		isTextFieldDisplayed("Confirm your contact information");
		Thread.sleep(2000);
		// Enter participant email address and click on continue
		Web.setTextToTextBox(txtEmailAddress,emailAddress);
		Web.clickOnElement(btnContinueWithdrawal);
		Thread.sleep(4000);
		}		
	}
		catch(Exception e)
		{ e.printStackTrace();}
	}
	
	public void verifyWithdrawalSummary(String mailDeliverytype)
	{
		try
		{
			if (isTextFieldDisplayed("delivery method"))
			{				
				if(mailDeliverytype.equalsIgnoreCase("firstClass"))
				{
					inpFirstClassMail.click();
					deliveryMthd=inpFirstClassMail.getAttribute("value");
				}
				else if(mailDeliverytype.equalsIgnoreCase("expressMail"))
				{
					inpExpressMail.click();
					deliveryMthd=inpExpressMail.getAttribute("value");
				}
				Thread.sleep(2000);
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ mailDeliverytype, "Selected Delivery Method: " + inpFirstClassMail.getAttribute("value"), false);
				Thread.sleep(10000);
				if(isTextFieldDisplayed("Withdrawal summary"))
				{
					enteredTotalWithdrawalAmt = 0;
					enteredTotalWithdrawalAmt = enteredPreTaxWithdrawalAmt
							+ enteredRothWithdrawalAmt;
					WebElement lblFinalWithdrawalAmount = Web.webdriver.findElement(By
							.xpath(lblTotalWithdrawalAmount.replace("Money Source Type",
									"Total withdrawal amount")));
					finalWithdrawalAmount=(int)Math.round(Web.getIntegerCurrency(lblFinalWithdrawalAmount.getText()));
					if (enteredTotalWithdrawalAmt == finalWithdrawalAmount) {
						Reporter.logEvent(Status.PASS,
								"Verify Withdrawal Amount is Displayed",
								"Withdrawal Amount is Displayed and Macthing \nExpected:$"
										+ enteredTotalWithdrawalAmt + "\nActual:$"
										+ finalWithdrawalAmount, false);
					} else {
						Reporter.logEvent(Status.FAIL,
								"Verify Withdrawal Amount is Displayed",
								"Withdrawal Amount is NOT Matching\nExpected:$"
										+ enteredTotalWithdrawalAmt + "\nActual:$"
										+ finalWithdrawalAmount, true);
					}
					btnContinueWithdrawal.click();
					Thread.sleep(8000);	
					
				}
				//check for GDR section or throw error and stop execution
				else
					System.out.println("GDR Section needs to be implemented");
				
			}			
			
		}
		catch(Exception e)
		{ e.printStackTrace();}
	}
	
	public void verifyWithdrawalConfirmation(String withdrawalType,String userName,String IndId,String deliveryType,String mailDeliveryType)
	{
		try {		
		String confirmationNo=null;
		String pptFirstName=null;		
		String dbName= Common.getParticipantDBName(userName);
		ResultSet getPptFullName=DB.executeQuery(dbName+"DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getParticipantFullName")[1],
				IndId);
		if(getPptFullName!=null)
		{
			while(getPptFullName.next())
			{
				pptFirstName=getPptFullName.getString("first_name");
			}
		}	
		if(isTextFieldDisplayed("Request submitted!"))
		{
		Web.waitForElement(txtConfirmationNo);
		if (Web.isWebElementDisplayed(txtConfirmationNo, true)) {
			confirmationNo=txtConfirmationNo.getText().trim();		
			Reporter.logEvent(
					Status.INFO,"Verify Request Confirmation is Displayed on UI",
					"Request Confirmation is Displayed\n Confirmation No: "+confirmationNo ,false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify Request Confirmation Number is Displayed",
					"Request Confirmation Number is NOT Displayed", true);
		}
		//verify confirmation no displayed in db in document table
		if(withdrawalType.equalsIgnoreCase("AGE 59 1/2") || withdrawalType.equalsIgnoreCase("IN SERVICE WITHDRAWAL"))
		{
			isConfirmationNumDisplayed(userName,IndId);
		}
		else
		{
		//verify disbursement records in db
		verifyWithdrawalDisbursementRecords();
		}
		
		//Verify other fields in confirmation Page
		int confirmationPageAmount= (int)Math.round(Web.getIntegerCurrency(confirmationPageRollOverAmount.getText()));
		System.out.println("A: "+confirmationPageAmount);
		System.out.println("E: "+enteredTotalWithdrawalAmt);
		if(confirmationPageAmount == enteredTotalWithdrawalAmt)
			Reporter.logEvent(Status.PASS, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ enteredTotalWithdrawalAmt +"\n"
					+ "Actual Amount: "+confirmationPageAmount, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ enteredTotalWithdrawalAmt +"\n"
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
				if(confirmationPagePayableTo.getText().toString().trim().contains(pptFirstName.toString().trim()))
					Reporter.logEvent(Status.PASS, "Verify the Payable To in the Confirmation Page", 
							"The Payable To Field is displayed as:\n"
							+ "Payable To: "+confirmationPagePayableTo.getText(), false);
				else	
					Reporter.logEvent(Status.FAIL, "Verify the Payable To in the Confirmation Page", 
							"The Payable To Field is displayed as:\n"
							+ "Expected: "+ pptFirstName +"\n"
							+ "Actual: "+confirmationPagePayableTo.getText(), false);
				
				//deliveryMthd=deliveryMthd.contains("-")? deliveryMethod.replace("-", " ") : deliveryMethod;
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
				Reporter.logEvent(Status.INFO, "Verify the Sent To Address in the Confirmation Page", 
						"The Address is displayed as:\n"+
					"Address: "+confirmationPageSentToAddress.getText().trim(), false);
			}
		}catch (SQLException e) {
					
					e.printStackTrace();
				}	

		catch(Exception e)
		{ e.printStackTrace();}
	}
	
	
	
	public boolean isConfirmationNumDisplayed(String userName,String individualId)
	{
		boolean isConfirmationNoDisplayed=false;
		String confirmationNo=null;
		String dbName=null;
		int isConfirmationNumDisplayed=0;
		try {		
		dbName=Common.getParticipantDBName(userName);
		confirmationNo=txtConfirmationNo.getText();
		isConfirmationNumDisplayed = DB.getRecordSetCount(DB.executeQuery(dbName+ "DB_"+ Common.checkEnv(Stock.getConfigParam("TEST_ENV")),
				Stock.getTestQuery("getWithDrawalConfirmationNo")[1],
				confirmationNo, individualId));
	if (isConfirmationNumDisplayed > 0) {
		Reporter.logEvent(
				Status.PASS,
				"Verify Request Confirmation Number is Populated in the DataBase",
				"Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"+confirmationNo,false);
		isConfirmationNoDisplayed=true;
	} else {
		Reporter.logEvent(
				Status.FAIL,
				"Verify Request Confirmation Number is Populated in the DataBase",
				"Request Confirmation Number is NOT Populated in the DataBase",
				true);
		isConfirmationNoDisplayed=false;
	}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	return isConfirmationNoDisplayed;
	
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
	 * This method is to validate if Pre-tax or Roth Money Type Source section is displayed for the In-Service withdrawal type
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
	 * This method is to validate if Pre-tax or Roth Money Type Source section is displayed for the 
	 * Sept -Service Part Withdrawal Type
	 * @param withdrawalType
	 * @param moneyType
	 * @return
	 */
	public boolean isPartWithdrawalMoneyTypeSourceAvailable(String moneyType)
	{
	WebElement partWithdrawalMoneySourceAvailable=Web.webdriver.findElement
					(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", moneyType)));
			if(partWithdrawalMoneySourceAvailable.isDisplayed())			
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
		maxAmount=maxAmount-1;
		
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
				.xpath(lblTotalWithdrawalAmount.replace("Money Source Type",
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
		try {
		//Verify total Withdrawal Amount
		int confirmationPageAmount= (int)Math.round(Web.getIntegerCurrency(confirmationPageRollOverAmount.getText()));
		System.out.println("A: "+confirmationPageAmount);
		System.out.println("E: "+enteredTotalWithdrawalAmt);
		if(confirmationPageAmount == enteredTotalWithdrawalAmt)
			Reporter.logEvent(Status.PASS, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ enteredTotalWithdrawalAmt +"\n"
					+ "Actual Amount: "+confirmationPageAmount, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Total Withdrawal Amount in the Confirmation Page", 
					"The Total Withdrawal Amount is displayed as:\n"
					+ "Expected Amount: "+ enteredTotalWithdrawalAmt +"\n"
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
		String pptFullName=null;
		String dbName= Common.getParticipantDBName(Stock
					.GetParameterValue("userName"));
		ResultSet getPptFullName=DB.executeQuery(dbName+"DB_"+ 
				Common.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getParticipantFullName")[1],
				Stock.GetParameterValue("ind_ID"));
		if(getPptFullName!=null)
			{
				while(getPptFullName.next())
				{
					pptFullName=getPptFullName.getString("mailing_name_1");
				}
			}
	
		if(confirmationPagePayableTo.getText().toString().trim().contains(pptFullName.toString().trim()))
			Reporter.logEvent(Status.PASS, "Verify the Payable To in the Confirmation Page", 
					"The Payable To Field is displayed as:\n"
					+ "Expected: "+ pptFullName +"\n"
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
	} catch (SQLException e) {
			
			e.printStackTrace();
		}	
				
	}
	
	public void verifyWithdrawalDisbursementRecords()
	{
		try {
			String dbName = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"));
			ResultSet getWithdrawalsRecords=DB.executeQuery(dbName+"DB_"+ 
					Common.checkEnv(Stock.getConfigParam("TEST_ENV")),
					Stock.getTestQuery("getWithdrawalDisbursementRecords")[1],
					txtConfirmationNo.getText());
		if(getWithdrawalsRecords!=null)
		{			
		int isDisbursementRecordsDisplayed = DB.getRecordSetCount(getWithdrawalsRecords);
		if(isDisbursementRecordsDisplayed>0)
		{
			while(getWithdrawalsRecords.next())
			{
				String disbType = getWithdrawalsRecords.getString("DISB_TYPE");
				String dsmdCode= getWithdrawalsRecords.getString("DSMD_CODE");
				String dsrsCode= getWithdrawalsRecords.getString("DSRS_CODE");
				String statusCode= getWithdrawalsRecords.getString("STATUS_CODE");
				String taxReasonCode= getWithdrawalsRecords.getString("TAX_REASON_CODE");
				Reporter.logEvent(Status.PASS, "Verify the Withdrawal disbursment Fields", "The following Withdrawal Disbursement Fields are Created:\n"
						+ "Disb Type: "+disbType +"\n"
						+ "DSMD Code: "+dsmdCode +"\n"
						+ "DSRS Code: "+dsrsCode +"\n"
						+ "Status Code: "+statusCode +"\n"
						+ "Tax Reason Code: "+taxReasonCode +"\n", false);
				break;
				
			}
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Withdrawals Disbursement Records are displayed" , 
					"Withdrawal Disbursement Records are not available", false);		
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Withdrawals Disbursement Records are displayed" ,
					"Withdrawal Disbursement Records are not available", false);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
}
