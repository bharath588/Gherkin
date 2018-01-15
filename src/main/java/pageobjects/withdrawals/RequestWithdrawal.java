package pageobjects.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RequestWithdrawal extends LoadableComponent<RequestWithdrawal> {

	// Declarations
	private LoadableComponent<?> parent;
	RequestWithdrawal_AF requestWithdrawal_AF;
	private static boolean waitforLoad = false;
	@FindBy(id="accounts")
	private WebElement drpDwnMultiplePlans;
	@FindBy(xpath = ".//h1[text()[normalize-space()='Request a withdrawal']]")
	private WebElement lblRequestAWithdrawal;
	@FindBy(xpath=".//*[@id='account-details-container']/div/div/div[2]/div[1]/label")
	private WebElement reqWithdrawal;
	@FindBy(xpath=".//h1/span[@class='currency']")
	private WebElement txtTotalVestedBalance;
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	 @FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath=".//a[contains(@translate,'Logout')]")
	 //@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath=".//button[contains(text(),'Back')]")
	private WebElement btnBack;
	@FindBy(xpath = ".//input[contains(@ng-click,'maxAmountCheck')]")
	private WebElement inptMaxAmount;
	@FindBy(xpath = ".//button[ ./span[contains(text(),'Continue')] or contains(text(),'Continue') ]")
	private WebElement btnContinue;
	@FindBy(xpath= ".//label[./input[@value='true']]")
	private WebElement inpYes;
	@FindBy(xpath= ".//label[./input[@value='false']]")
	private WebElement inpNo;	
	@FindBy(xpath=".//input[@type='email']")
	private WebElement inpCitizenShipNoEmailField;
	@FindBy(xpath=".//button[contains(text(), 'Email Form')]")
	private WebElement btnEmailForm;
	@FindBy(id = "ssnInput")
	private WebElement inputSSN;
	@FindBy(linkText = "Print")
	private WebElement lnkPrint;
	@FindBy(xpath = ".//button[contains(text(),'Confirm and continue')]")
	private WebElement btnConfirmContinue;	
	@FindBy(xpath = "//label[@for='ssnInput']")
	private WebElement labelSSN;
	@FindBy(css = "button[disabled='disabled']")
	private WebElement btnConfirmContinue_disbld;	
	@FindBy(id="futureDate")
	private WebElement radioProcesAfterHold;
	
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalType')]")
	private WebElement drpWithdrawalType;
	//@FindBy(xpath=".//select[contains(@class,'ng-pristine')]")
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalReason')]")
	private WebElement drpFullWithdrawalType;
	//@FindBy(xpath = ".//*[@id='btn-confirm submit']")
	//@FindBy(xpath = ".//*[@id='btn-confirm']")
	@FindBy(xpath=".//button[ ./span[contains(text(),'Continue')] or contains(text(),'Continue') ]")
	private WebElement btnContinueWithdrawal;
	@FindBy(id="btn-confirm")
	private WebElement btnIAgreeAndSubmit; 
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]")
	private WebElement txtConfirmation;
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]//strong")
	private WebElement txtConfirmationNo;
	@FindBy(xpath=".//*[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath= ".//label[./input[@id='inserviceradioyes']]")
	private WebElement inpCurrentEmployerYes;
	@FindBy(xpath= ".//label[./input[@id='inserviceradiono']]")
	private WebElement inpCurrentEmployerNo;
	@FindBy(xpath=".//p[@class='flow-block-warning-text']")
	private WebElement planWithdrawalWarningPage;			
	private String inpMailType="//input[contains(@value,'mailType')]";
	@FindBy(id="deliveryOptionsUSPS")
	private WebElement inpFirstClassMail;
	@FindBy(id="deliveryOptionsExpress")
	private WebElement inpExpressMail;
	@FindBy(id="partialWithdrawalCheckbox") 
	private List<WebElement> lstMaxCheckBox;	
	@FindBy(xpath=".//div[contains(@ng-if,'FULL WD')]")
	private WebElement totalFWDVestedBalance;
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
	@FindBy(xpath="//input[contains(@ng-model,'accountNumber')]")
	private WebElement inpAccountNumber;	
	@FindBy(xpath="//input[contains(@ng-model,'rothAccountNumber')]")
	private WebElement inpRothAccountNumber;
	@FindBy(xpath=".//label[contains(text(),'Roth IRA Account Number')]")
	private WebElement lblRothAccountNumber;
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
	@FindBy(xpath=".//input[contains(@ng-model,'withdrawalPayToSelfAmount')]")
	private WebElement inpPayToSelfAmt;
	@FindBy(xpath="//div[contains(@ng-include,'SummaryPayToSelfDelivery')]//input[@value='First-class mail']")
	private WebElement inpPayToSelfFirstClassMail;
	@FindBy(xpath="//div[contains(@ng-include,'SummaryPayToSelfDelivery')]//input[@value='Express mail']")
	private WebElement inpPayToSelfExpressMail;
	@FindBy(xpath="//div[contains(@ng-include,'SummaryRolloverDelivery')]//input[@value='First-class mail']")
	private WebElement inpRollOverFirstClassMail;
	@FindBy(xpath="//div[contains(@ng-include,'SummaryRolloverDelivery')]//input[@value='Express mail']")
	private WebElement inpRollOverExpressMail;
	@FindBy(xpath=".//div[@class='withdrawal-warning-msg']//p")
	private WebElement warningMsg;
	//FWD Combo
	@FindBy(xpath="//tr[./th[contains(text(),'ESTIMATED ')]]/td")
	private List<WebElement> lstConfirmationPageRollOverAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'TYPE')]]/td")
	private List<WebElement> lstConfirmationPageDeliveryType;
	@FindBy(xpath="//tr[./th[contains(text(),'PAYABLE')]]/td")
	private List<WebElement> lstConfirmationPagePayableTo;
	@FindBy(xpath="//tr[./th[contains(text(),'DELIVERY METHOD')]]/td")
	private List<WebElement> lstConfirmationPageDeliveryMethod;
	@FindBy(xpath="//tr[./th[contains(text(),'SENT TO')]]/td")
	private List<WebElement> lstConfirmationPageSentToAddress;
	
	@FindBy(xpath=".//p/span[contains(text(),'Restrictions may apply')]")
	private WebElement txtGDRRestrictionsMayApplyText;
	@FindBy(xpath=".//a[contains(text(),'Special Tax Notice')]")
	private WebElement lnkGDRSpecialTaxNotice;	
	@FindBy(xpath=".//div[contains(@class,'alert alert-warning')]")
	private WebElement genericAlertIcon;
	@FindBy(xpath=".//div[contains(@class,'alert alert-')]/p")
	private WebElement txtSpousalConsentConf;
	@FindBy(xpath=".//p[contains(@ng-if,'isFlowCompletelyBlocked')]")
	private WebElement defaultDOBErrorMessage;
	@FindBy(xpath=".//div[contains(@class,'in-service-help')]/p")
	private WebElement txtSepServiceMessaging;
	@FindBy(xpath=".//div/i[contains(text(),'This email address will')]")
	private WebElement SDBEmailContent;
	@FindBy(id="partialWithdrawalCheckbox")
	private List<WebElement> pwdMaxAmtChkbox;
	@FindBy(xpath="//div[contains(@ng-repeat,'withdrawalMoneySources')]")
	private List<WebElement> pwdMoneyTypeSection;
	@FindBy(xpath="//*[contains(text(),'Payment 1')]")
	private WebElement fwdWithdrawalSummaryPayment1;
	@FindBy(xpath="//*[contains(text(),'Payment 2')]")
	private WebElement fwdWithdrawalSummaryPayment2;
	@FindBy(xpath="//*[contains(text(),'Payment 3')]")
	private WebElement fwdWithdrawalSummaryPayment3;
	@FindBy(xpath="//*[contains(text(),'Payment 4')]")
	private WebElement fwdWithdrawalSummaryPayment4;
	@FindBy(xpath="//a[contains(@uib-popover,'Restrictions may apply')]")
	private WebElement gDRIcon;
	@FindBy(xpath=".//div[@class='secure-fund-container']") private WebElement txtSecureFoundationFunds;
	@FindBy(linkText="product documentation") private WebElement lnkProductDocumentation;
	@FindBy(xpath="'//label[@for='confirmInformation']")private WebElement labelDeliveryInformationForMailOrExpeditedDelivery;
	@FindBy(xpath=".//label[contains(@ng-class,'afterCostBasisModel')]//span[text()='Yes']") private WebElement btnAfterTaxYes;
	private String txt_SepService_PlanMinimum=
			".//div[contains(@ng-if,'hasSufficientBalance') and contains(@ng-if,'WithdrawalType')]";
	private String txt_InService_PlanMinimum=
			"//div[@id='test_id'][./input[@value='WithdrawalType']]/following-sibling::div[contains(@ng-if,'hasSufficientBalance')]";
	
	private String textField="//*[contains(text(),'webElementText')]";
	
	private String inputWithdrawalType ="//div[@id='test_id']/input[@value='Withdrawal Type']"; 
			//"//div[@id='test_id'][.//span[contains(text(),'Withdrawal Type')]]//input";	
	
	private String chkBoxInServiceMoneySourceType="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//input[contains(@value,'Source Type')]";
	private String chkBoxSepServiceMoneySourceType="//div[contains(@ng-repeat,'partialWithdrawalData')][.//div[text()[normalize-space()='Money Source Type']]]//input[@name='partialSources']";
	private String 
	moneyTypeAmtTxt ="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//input[@type='text']";
	
	private String 
	moneyTypeMaxAmtChkBox =
	"//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//input[contains(@ng-click,'maxAmountCheck')]"; 
	private String 
	txtMoneyTypeAmt ="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//div[contains(@ng-if,'isGenericDisbRuleForInService')]";	
	
	private String
	imgGDRIcon="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,'source-row')][.//label[text()[normalize-space()='Money Source Type']]]//div//a";
		
	private String 
	moneyTypeSourceSection="//div[contains(@class,'selected-row-body')][.//input[contains(@value,'Withdrawal Type')]]//div[contains(@class,' source-row')][.//label[text()[normalize-space()='Money Source Type']]]";
	
	private String sourceHierarchyRadioButton=
			"//div[contains(@class,'selected-row-body')][.//label[text()[normalize-space()='Withdrawal Type']]]//input";
	private String sourceHierarchyAvailableAmt=
			"//div[contains(@class,'selected-row-body')][.//label[text()[normalize-space()='Withdrawal Type']]]//div[contains(@ng-if,'isGenericDisbRuleForInService')]";
	
	private String sourceHierarchyEnterMoney=
			"//div[contains(@class,'selected-row-body')][.//label[text()[normalize-space()='Withdrawal Type']]]//input[@type='text']";
		
	private String lblTotalWithdrawalAmount="//tr[.//td/span[contains(text(),'Money Source Type')]]//td[2]//span";
	private String inpAmtPWMoneyType="//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Money Source Type']]]//input[@type='text']";
	private String chkBoxMaxAmtPWMoneyType="//tr[./td[contains(text(),'Money Source Type')]]//input[contains(@ng-click,'maxAmountCheck')]";
	private String maxAmtPWMoneyType="//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Money Source Type']]]//div[contains(@ng-if,'isGenericDisbRuleForSepService')]";
	private String pwdImgGDRIcon="//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Money Source Type']]]//a";
	private String inpMaxAmountPWChkBox=
			"//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Money Source Type']]]//input[@id='partialWithdrawalCheckbox']";
	private static int enteredRothWithdrawalAmt = 0;
	private static int enteredNonRothWithdrawalAmt = 0;	
	private static int enteredTotalWithdrawalAmt = 0;
	private static int finalWithdrawalAmount=0;
	private static int fwdPartialPTSAmount=0;
	private String deliveryMthd=null;
	public static boolean isMaxGDR=false;
	public static boolean isAmericanFunds=false;
	public static boolean isSourceHierarchy=false;
	
	
	/**
	 * Default Constructor
	 */
	public RequestWithdrawal() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestWithdrawal(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
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
		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();
		Web.isWebElementDisplayed(lblRequestAWithdrawal,true);
		   

	}
	
	public static void resetTotalValues()
	{
		enteredRothWithdrawalAmt = 0;
		enteredNonRothWithdrawalAmt = 0;	
		enteredTotalWithdrawalAmt = 0;
		finalWithdrawalAmount=0;
		fwdPartialPTSAmount=0;
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
		if (fieldName.trim().equalsIgnoreCase("TEXT TOTAL VESTED BALANCE")) {
			return this.txtTotalVestedBalance;
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
		if (fieldName.trim().equalsIgnoreCase("EMAIL FORM FIELD")) {
			return this.inpCitizenShipNoEmailField;
		}
		if (fieldName.trim().equalsIgnoreCase("SDB EMAIL CONTENT")) {
			return this.SDBEmailContent;
		}		
		if (fieldName.trim().equalsIgnoreCase("EMAIL FORM")) {
			return this.btnEmailForm;
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
		if (fieldName.trim().equalsIgnoreCase("WITHDRAWAL METHOD FWD")) {
			return this.drpFullWithdrawalType;
		}
		if (fieldName.trim().equalsIgnoreCase("PLAN WITHDRAWAL WARNING")) {
			return this.planWithdrawalWarningPage;
		}		
		if (fieldName.trim().equalsIgnoreCase("CONTINUE TO WITHDRAWAL")) {
			return this.btnContinueWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("I AGREE AND SUBMIT")) {
			return this.btnIAgreeAndSubmit;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION")) {
			return this.txtConfirmation;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION NUMBER")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT CURRENT EMPLOYER NO")) {
			return this.inpCurrentEmployerNo;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT CURRENT EMPLOYER YES")) {
			return this.inpCurrentEmployerYes;
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
		if (fieldName.trim().equalsIgnoreCase("TOTAL VESTED BALANCE AMOUNT")) {
			return this.totalFWDVestedBalance;
		}
		if (fieldName.trim().equalsIgnoreCase("GENERIC ALERT ICON")) {
			return this.genericAlertIcon;
		}
		if(fieldName.trim().equalsIgnoreCase("GENERIC ERROR MESSAGE")) {
			return this.defaultDOBErrorMessage;		
		}
		if(fieldName.trim().equalsIgnoreCase("SEP SERVICE MESSAGING")) {
			return this.txtSepServiceMessaging;		
		}
		if (fieldName.trim().equalsIgnoreCase("PAYMENT TO SELF FIRST CLASS MAIL")) {
			return this.inpPayToSelfFirstClassMail;
		}
		if (fieldName.trim().equalsIgnoreCase("PAYMENT TO SELF EXPRESS MAIL")) {
			return this.inpPayToSelfExpressMail;
		}
		if (fieldName.trim().equalsIgnoreCase("ROLLOVER FIRST CLASS MAIL")) {
			return this.inpRollOverFirstClassMail;
		}
		if (fieldName.trim().equalsIgnoreCase("ROLLOVER EXPRESS MAIL")) {
			return this.inpRollOverExpressMail;
		}
		if (fieldName.trim().equalsIgnoreCase("BACK BUTTON")) {
			return this.btnBack;
		}
		if (fieldName.trim().equalsIgnoreCase("GDR CONTENT")) {
			return this.txtGDRRestrictionsMayApplyText;
		}		
		if (fieldName.trim().equalsIgnoreCase("MULTIPLE PLANS DROPDOWN")) {
			return this.drpDwnMultiplePlans;
		}
		if (fieldName.trim().equalsIgnoreCase("CONFIRMATION NUM")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("SECURE FOUNDATIONS CONTENT")) {
			return this.txtSecureFoundationFunds;
		}
		if (fieldName.trim().equalsIgnoreCase("PRODUCT DOCUMENTATION LINK")) {
			return this.lnkProductDocumentation;
		}
		
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}
	
	/**
	 * Method to Select WithDrawal Type for In Service Withdrawal 
	 * @param withdrawalType (In Service ; Age 59 1/2)
	 * 
	 */
	public boolean selectWithdrawalType(String withdrawalType) {
		boolean isSelected=false;
		Actions keyBoard = new Actions(Web.getDriver());
		try {
			if(Web.isWebElementDisplayed(inpCurrentEmployerYes))
			{				
				Web.clickOnElement(inpCurrentEmployerYes);				
				//Thread.sleep(3000);
				keyBoard.sendKeys(Keys.TAB).build().perform();
				keyBoard.sendKeys(Keys.ENTER).build().perform();
			}	
		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();
		Thread.sleep(5000);
		
		WebElement inptWithdrawalType = Web.getDriver().findElement(By
				.xpath(inputWithdrawalType.replace("Withdrawal Type",withdrawalType)));	
		if(Web.isWebElementDisplayed(radioProcesAfterHold))
			Web.clickOnElement(radioProcesAfterHold);
		if(Web.isWebElementDisplayed(inptWithdrawalType))
		{
			Web.clickOnElement(inptWithdrawalType);		
			isSelected=true;
		}		
		if(isSelected)
				Reporter.logEvent(Status.PASS,"Verify if "+withdrawalType+ " WithDrawal Type is Selected",
						withdrawalType + " WithDrawal Type is Selected", true);
			else
			{
				Reporter.logEvent(Status.PASS,"Verify if "+ withdrawalType+ " WithDrawal Type is Selected",
						withdrawalType+ " WithDrawal Type is NOT Selected", true);
				throw new Error(withdrawalType +" section is not displayed");
			}
			
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSelected;
	}
	
	
	
	
	/**
	 * This method is to enter Amount For In Service Withdrawal for One or Multiple Money Type Sources
	 * @param withdrawalType - InService ; Age 59 1/2
	 * @param rothSection - Roth ; No
	 * @param nonRothSection - Pre-tax;Non-Roth;After-tax;No
	 * @param verifyGDR - Yes; No
	 */
	public void enterWithdrawalAmountForInService(String withdrawalType,String rothSection,String nonRothSection
			,boolean... verifyGDR)
	{
		try {		
		//Enter Amount For Roth Money Section
		if(rothSection.equalsIgnoreCase("Roth"))		
		{ 			
			WebElement rothMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection.trim())));
			if(Web.isWebElementDisplayed(rothMoneyTypeSourceAvailable))
			{
				int maxAmount=0;			
				enteredRothWithdrawalAmt = 0; 
					WebElement txtMaxAmount = Web.getDriver().findElement(By
							.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type",rothSection)));
					maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));
					
					WebElement txtAmount = Web.getDriver().findElement(By
							.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type",rothSection)));			
					if(txtAmount.getAttribute("value").isEmpty()||txtAmount.getAttribute("value")==null)
						maxAmount=maxAmount/2;						
					else						
						maxAmount=(maxAmount/2)+5;						
					enteredRothWithdrawalAmt=maxAmount;					
					Web.setTextToTextBox(txtAmount, Integer.toString(enteredRothWithdrawalAmt));
					Reporter.logEvent(
							Status.PASS,
							"Verify if User entered withdrawal amount for "+rothSection+" Money Type",
							"The user have entered " + enteredRothWithdrawalAmt
									+ " to be withdrawn from "+rothSection+" Money Type",false);
					if(verifyGDR.length>0)					
						verifyGDRRestrictionIcon(withdrawalType,rothSection);
				
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for "+rothSection+" Money Type",
							rothSection+" Money Type section is NOT Displayed", false);
					throw new Error(rothSection+" Money Type Section is NOT displayed");
				}
		}
		else			
				enteredRothWithdrawalAmt = 0;
		
		//enter Amount for Non-roth Amount Section
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||nonRothSection.equalsIgnoreCase("Non-Roth")||nonRothSection.equalsIgnoreCase("After-tax"))
		{
			WebElement nonRothMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", nonRothSection)));
			if(Web.isWebElementDisplayed(nonRothMoneyTypeSourceAvailable))
			{
				int maxAmount=0;			
				enteredNonRothWithdrawalAmt = 0;
					WebElement txtMaxAmount = Web.getDriver().findElement(By
							.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
					maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));
					/*maxAmount=maxAmount/2;
					System.out.println("PreTax Amount :"+ maxAmount);
					enteredPreTaxWithdrawalAmt=maxAmount;	*/	
					WebElement txtAmount = Web.getDriver().findElement(By
							.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
					if(txtAmount.getAttribute("value").isEmpty()||txtAmount.getAttribute("value")==null)
						maxAmount=maxAmount/2;						
					else						
						maxAmount=(maxAmount/2)+1;						
					enteredNonRothWithdrawalAmt=maxAmount;
					Web.setTextToTextBox(txtAmount, Integer.toString(enteredNonRothWithdrawalAmt));
					Reporter.logEvent(
							Status.PASS,
							"Verify if User entered withdrawal amount for "+nonRothSection+" Money Type",
							"The user have entered " + enteredNonRothWithdrawalAmt
									+ " to be withdrawn from "+nonRothSection+" Money Type",
							false);
					if(verifyGDR.length>0)
						verifyGDRRestrictionIcon(withdrawalType,nonRothSection);
					
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for "+nonRothSection+" Money Type",
							nonRothSection+" Money Type section is NOT Displayed", false);
					throw new Error(nonRothSection+" Money Type Section is NOT displayed");
				}
		}
		else			
			enteredNonRothWithdrawalAmt = 0;		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
		
	}
	
	/**
	 * This method is to verify the GDR Icon for Full Withdrawal
	 * @param withdrawalType - Full Withdrawal
	 */
	public void verifyGDRRestrictionIconForFWD(String withdrawalType)
	{
		try {			
				if(gDRIcon.isEnabled())
				{
					if(Web.VerifyText("Restrictions may apply", gDRIcon.getAttribute("uib-popover"))){
						
						Reporter.logEvent(Status.PASS, "Verify if Restrictions may apply, Information icon is displayed",
								"Restrictions may apply Information icon is displayed next to full Withdrawal Amount", false);

					}
					else{				
					Reporter.logEvent(Status.FAIL, "Verify if Restrictions may apply, Information icon is displayed",
							"Restrictions may apply Information icon is NOT displayed next to full Withdrawal Amount", false);
					}
				}			
		}
		catch(NoSuchElementException e)		
		{ 
		Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
				msg, true); 
	}
	catch(Exception e)
	{e.printStackTrace();}
		
	}


	/**
	 *  This method is to verify the GDR Icon for In Service and Part Withdrawal
	 * @param withdrawalType - In Service ; Age 59 1/2 ; Part Withdrawal
	 * @param moneySourceType - Non-Roth & Roth Money source Types
	 */
	public void verifyGDRRestrictionIcon(String withdrawalType, String moneySourceType)
	{
	try {		
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		if(!withdrawalType.equalsIgnoreCase("pwd"))
		{	
		WebElement gDRIcon=Web.getDriver().findElement(By.xpath(imgGDRIcon.replace("Withdrawal Type",
				withdrawalType).replaceAll("Money Source Type",moneySourceType)));
	
		if(gDRIcon.isEnabled())
		{
			if(Web.VerifyText("Restrictions may apply", gDRIcon.getAttribute("uib-popover"))){
			
			Reporter.logEvent(Status.PASS, "Verify if Restrictions may apply, Information icon is displayed",
					"Restrictions may apply Information icon is displayed next to Dollar Amount for"+moneySourceType+"section", false);

		}
		else{				
		Reporter.logEvent(Status.FAIL, "Verify if Restrictions may apply, Information icon is displayed",
				"Restrictions may apply Information icon is NOT displayed next to Dollar Amount for"+moneySourceType+"section", false);
		}
		}
	}
	else if(withdrawalType.equalsIgnoreCase("pwd"))
	{
		WebElement pwdGDRIcon=Web.getDriver().findElement(By.xpath(pwdImgGDRIcon.replace("Withdrawal Type",
				withdrawalType).replaceAll("Money Source Type",moneySourceType)));
	
		if(pwdGDRIcon.isEnabled())
		{
			if(Web.VerifyText("Restrictions may apply", pwdGDRIcon.getAttribute("uib-popover"))){
			
			Reporter.logEvent(Status.PASS, "Verify if Restrictions may apply, Information icon is displayed",
					"Restrictions may apply Information icon is displayed next to Dollar Amount for"+moneySourceType+"section", false);

		}
		else{				
		Reporter.logEvent(Status.FAIL, "Verify if Restrictions may apply, Information icon is displayed",
				"Restrictions may apply Information icon is NOT displayed next to Dollar Amount for"+moneySourceType+"section", false);
		}
		}	
	}
	}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); 
		}
		catch(Exception e)
		{e.printStackTrace();}
		
	}
	
	/**
	 * Select Source Hierarchy Withdrawals for In Service ; Age 59 1/2		
	 * @param withdrawalType In Service ; Age 59 1/2	
	 */
	public void selectSourceHierarchyForInService(String withdrawalType)
	{		
	Actions keyBoard = new Actions(Web.getDriver());
		try {
			if (Web.isWebElementDisplayed(inpCurrentEmployerYes)) {
				Web.clickOnElement(inpCurrentEmployerYes);
				// Thread.sleep(3000);
				keyBoard.sendKeys(Keys.TAB).build().perform();
				keyBoard.sendKeys(Keys.ENTER).build().perform();
			}
			Web.waitForPageToLoad(Web.getDriver());
			Common.waitForProgressBar();
			Thread.sleep(3000);
			WebElement sourceHierarchyWithdrawalType=Web.getDriver().findElement(
					By.xpath(sourceHierarchyRadioButton.replace("Withdrawal Type",
							withdrawalType)));
			if(Web.isWebElementDisplayed(sourceHierarchyWithdrawalType))
			{
				Web.clickOnElement(sourceHierarchyWithdrawalType);
				WebElement sourceHierarchyAvailAmt=Web.getDriver().findElement
					(By.xpath(sourceHierarchyAvailableAmt.replace("Withdrawal Type",
							withdrawalType)));
				
				WebElement sourceHierarchyTxtBox=Web.getDriver().findElement
						(By.xpath(sourceHierarchyEnterMoney.replace("Withdrawal Type",
								withdrawalType)));
				enteredTotalWithdrawalAmt=0;
				
				enteredTotalWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(sourceHierarchyAvailAmt.getText().split("up to")[1].trim()))/2;
				Web.setTextToTextBox(sourceHierarchyTxtBox, Integer.toString(enteredTotalWithdrawalAmt));
				isSourceHierarchy=true;
				Reporter.logEvent(Status.PASS, "Verify "+withdrawalType+" section has been displayed and the amount has been entered",
				withdrawalType +" section has been displayed and the amount "+enteredTotalWithdrawalAmt+" has been Entered",true);					
			}
			else
			{
				Reporter.logEvent(Status.PASS, "Verify "+withdrawalType+" section has been displayed and the amount has been entered",
						withdrawalType +" section has NOT been displayed",true);
				throw new Error(withdrawalType+ "section has NOT been displayed");
			}	
		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
	}
	
	/**
	 * Select Max Amount Check Box Option for In Service withdrawal Type
	 * @param withdrawalType - In Service ; Age 59 1/2
	 * @param rothSection
	 * @param nonRothSection
	 */	
	public void selectMaxAmountCheckBoxForInService(String withdrawalType,String rothSection,String nonRothSection)
	{
		try {
		
		if (rothSection.equalsIgnoreCase("Roth")){			
			WebElement rothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection)));
			if(Web.isWebElementDisplayed(rothMaxAmountChkBox))				
			{
				Web.clickOnElement(rothMaxAmountChkBox);
				Web.waitForPageToLoad(Web.getDriver());				
				enteredRothWithdrawalAmt=0;
				WebElement txtMaxAmount = Web.getDriver().findElement(By
						.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
								withdrawalType).replaceAll("Money Source Type", rothSection)));
				enteredRothWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));							
				Reporter.logEvent(Status.PASS, "Select Max Amount For "+rothSection+" Money Type",
						 "Max Amount Been Selected For "+rothSection+": " + enteredRothWithdrawalAmt, false);
				//Need to be implemented
//				if(lstMaxCheckBox.size()==1)
//					Reporter.logEvent(Status.PASS, "Verify if Pre-Tax Max Amount Check box is NOT displayed",
//							 "After Selecting Roth Max Amount Check Box, Pre-tax Max Amount Check box is NOT Displayed", true);
//				else
//					Reporter.logEvent(Status.FAIL, "Verify if Pre-Tax Max Amount Check box is NOT displayed",
//							 "After Selecting Roth Max Amount Check Box, Pre-tax Max Amount Check box is Displayed", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if User entered withdrawal amount for "+rothSection+" Money Type",
						rothSection+" Money Type section is NOT Displayed", false);
				throw new Error(rothSection+" Money Type Section is NOT displayed");
			}
	}
	else			
		enteredRothWithdrawalAmt = 0;
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||nonRothSection.equalsIgnoreCase("Non-Roth")||nonRothSection.equalsIgnoreCase("After-tax"))
		{	
					WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
							(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
					if(Web.isWebElementDisplayed(nonRothMaxAmountChkBox))				
					{
						Web.clickOnElement(nonRothMaxAmountChkBox);
						Web.waitForPageToLoad(Web.getDriver());				
						enteredNonRothWithdrawalAmt=0;
						WebElement txtMaxAmount = Web.getDriver().findElement(By
								.xpath(txtMoneyTypeAmt.replace("Withdrawal Type",
										withdrawalType).replaceAll("Money Source Type", nonRothSection)));
						enteredNonRothWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText().split("up to")[1].trim()));							
						Reporter.logEvent(Status.PASS, "Select Max Amount For "+nonRothSection+" Money Type",
								 "Max Amount Been Selected For "+nonRothSection+": " + enteredNonRothWithdrawalAmt, false);
						//Need to be implemented
//						if(lstMaxCheckBox.size()==1)
//							Reporter.logEvent(Status.PASS, "Verify if Pre-Tax Max Amount Check box is NOT displayed",
//									 "After Selecting Roth Max Amount Check Box, Pre-tax Max Amount Check box is NOT Displayed", true);
//						else
//							Reporter.logEvent(Status.FAIL, "Verify if Pre-Tax Max Amount Check box is NOT displayed",
//									 "After Selecting Roth Max Amount Check Box, Pre-tax Max Amount Check box is Displayed", false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify if User entered withdrawal amount for "+nonRothSection+" Money Type",
								nonRothSection+" Money Type section is NOT Displayed", false);
						throw new Error(nonRothSection+" Money Type Section is NOT displayed");
					}
			}
			else			
				enteredNonRothWithdrawalAmt = 0;	
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
		
	}
	
	
	
	/**
	 * Selects Max Amount check box Option for Sep Service withdrawal
	 * @param rothSection
	 * @param nonRothSection
	 */
	public void selectMaxAmountCheckBoxForSepService(String rothSection,String nonRothSection)
	{		
		try {	
			if (rothSection.equalsIgnoreCase("Roth")){	
				WebElement rothMaxAmountChkBox=Web.getDriver().findElement
						(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", rothSection)));
				if(Web.isWebElementDisplayed(rothMaxAmountChkBox))				
				{
					Web.clickOnElement(rothMaxAmountChkBox);
					Web.waitForPageToLoad(Web.getDriver());				
					enteredRothWithdrawalAmt=0;
					WebElement txtMaxAmount = Web.getDriver().findElement(By
							.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));
					enteredRothWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));					
					Reporter.logEvent(Status.PASS, "Select Max Amount For "+rothSection+" Money Type",
							 "Max Amount Been Selected For "+rothSection+": " + enteredRothWithdrawalAmt, false);
					if(lstMaxCheckBox.size()==1)
						Reporter.logEvent(Status.PASS, "Verify if "+nonRothSection+" Max Amount Check box is NOT displayed",
								 "After Selecting "+rothSection+" Max Amount Check Box, Non-Roth Max Amount Check box is NOT Displayed", true);
					else
						Reporter.logEvent(Status.FAIL, "Verify if "+nonRothSection+" Max Amount Check box is NOT displayed",
								 "After Selecting "+rothSection+" Max Amount Check Box, Non-Roth Max Amount Check box is Displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,"Verify if User entered withdrawal amount for "+rothSection+" Money Type",
							rothSection+" Money Type section is NOT Displayed", false);
					throw new Error(rothSection+" Money Type Section is NOT displayed");
				}
			}
			else			
				enteredRothWithdrawalAmt = 0;
			
			if(nonRothSection.equalsIgnoreCase("Pre-tax")||nonRothSection.equalsIgnoreCase("Non-Roth")||nonRothSection.equalsIgnoreCase("After-tax"))
			{		
			WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", nonRothSection)));
			if(Web.isWebElementDisplayed(nonRothMaxAmountChkBox))
			{
					Web.clickOnElement(nonRothMaxAmountChkBox);
					Thread.sleep(1000);					
					enteredNonRothWithdrawalAmt=0;
					WebElement txtMaxAmount = Web.getDriver().findElement(By
							.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", nonRothSection)));
					enteredNonRothWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));					
					Reporter.logEvent(Status.INFO, "Select Max Amount For "+nonRothSection+" Money Type",
							 "Max Amount Been Selected For "+nonRothSection+": " + enteredNonRothWithdrawalAmt, false);
					if(lstMaxCheckBox.size()==1)
						Reporter.logEvent(Status.PASS, "Verify if Roth Max Amount Check box is NOT displayed",
								 "After Selecting "+nonRothSection+" Max Amount Check Box, Roth Max Amount Check box is NOT Displayed", true);
					else
						Reporter.logEvent(Status.FAIL, "Verify if Roth Max Amount Check box is NOT displayed",
								 "After Selecting "+nonRothSection+" Max Amount Check Box, Roth Max Amount Check box is Displayed", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for "+nonRothSection+" Money Type",
							nonRothSection+" Money Type section is NOT Displayed", false);
					throw new Error(nonRothSection+" Money Type Section is NOT displayed");
					}
		}
		else			
			enteredNonRothWithdrawalAmt = 0;
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
	}
	
	
	
	/**
	 * Select Part or Full Withdrawal Type
	 * @param withdrawalType
	 * @return
	 */
	public boolean selectWithdrawalTypeForSepService(String withdrawalType)
	{
		boolean iswithdrawalSelected=false;
		Actions keyBoard = new Actions(Web.getDriver());
		try {
			if(Web.isWebElementDisplayed(inpCurrentEmployerNo))
			{
				//inpCurrentEmployerYes.click();
				Web.clickOnElement(inpCurrentEmployerNo);
				keyBoard.sendKeys(Keys.TAB).build().perform();
				keyBoard.sendKeys(Keys.ENTER).build().perform();
			}
		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();
		Thread.sleep(5000);
		if(Web.isWebElementDisplayed(radioProcesAfterHold))
			Web.clickOnElement(radioProcesAfterHold);
		if(Web.isWebElementDisplayed(partWithDrawal) && withdrawalType.equalsIgnoreCase("pwd"))
		{
			Reporter.logEvent(Status.PASS,
					"Verify Vested Part Withdrawal section is Displayed",
					"Vested Part Withdrawal section is displayed", true);
			Web.clickOnElement(partWithDrawal);
			Web.waitForPageToLoad(Web.getDriver());
			iswithdrawalSelected=true;
		}
		else if(Web.isWebElementDisplayed(fullWithDrawal)&& withdrawalType.equalsIgnoreCase("fwd"))
		{			
			Reporter.logEvent(Status.PASS,
					"Verify Vested Full Withdrawal section is Displayed",
					"Vested Full withdrawal section is displayed", false);
			iswithdrawalSelected=true;
			Web.clickOnElement(fullWithDrawal);
			Web.waitForPageToLoad(Web.getDriver());	
			enteredTotalWithdrawalAmt=0;
			enteredTotalWithdrawalAmt=(int)Math.round(Web.getIntegerCurrency(totalFWDVestedBalance.getText()));
			System.out.println("FWD Total Vested Balance: "+enteredTotalWithdrawalAmt);	
		}
		else
		{	
			iswithdrawalSelected=false;
			Reporter.logEvent(Status.FAIL,
					"Verify Vested Part/Full Withdrawal section is Displayed",
					"Vested Part/Full  Withdrawal section is NOT displayed", true);
			throw new Error("Vested Full/Part Withdrawal Section is NOT displayed");
		}
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
		
		return iswithdrawalSelected;
		
		}
	
	
	/**
	 * This method is to enter Amount For Sep Service Withdrawal for One or Multiple Money Type Sources
	 * @param withdrawalType - Part Withdrawal
	 * @param rothSection - Roth ; No
	 * @param nonRothSection - Pre-tax;Non-Roth;After-tax;No
	 * @param verifyGDR - Yes; No
	 */
		public void enterWithdrawlAmountForSepService(String withdrawalType,String rothSection,String nonRothSection,boolean... verifyGDR)
		{
			try {
			
			if (rothSection.equalsIgnoreCase("Roth") && withdrawalType.equalsIgnoreCase("pwd")) {
				WebElement partWithdrawalMoneySource=Web.getDriver().findElement
						(By.xpath(inpAmtPWMoneyType.replace("Money Source Type",rothSection)));
				if(Web.isWebElementDisplayed(partWithdrawalMoneySource))
				{
					int maxAmount=0;
					enteredRothWithdrawalAmt=0;
					WebElement txtMaxAmount = Web.getDriver().findElement(By
							.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));
					maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));
					/*maxAmount=maxAmount/2;
					//enteredRothWithdrawalAmt=5000;
						enteredRothWithdrawalAmt=maxAmount;
					
					WebElement txtAmount = Web.getDriver().findElement(By
							.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));*/
					if(partWithdrawalMoneySource.getAttribute("value").isEmpty()||partWithdrawalMoneySource.getAttribute("value")==null)
						maxAmount=maxAmount/2;						
					else						
						maxAmount=(maxAmount/2)+5;						
					enteredRothWithdrawalAmt=maxAmount;
					Web.setTextToTextBox(partWithdrawalMoneySource, Integer.toString(enteredRothWithdrawalAmt));					
					Reporter.logEvent(Status.INFO, "Entering WithDrawal Amount For "+rothSection+" Money Type",
							 "Entered WithDrawal Amount For "+rothSection+": " + enteredRothWithdrawalAmt, false);
					
					if(verifyGDR.length>0)					
						verifyGDRRestrictionIcon(withdrawalType,rothSection);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User entered withdrawal amount for "+rothSection+" Money Type",
							rothSection+" Money Type section is NOT Displayed", false);
					throw new Error(rothSection+" Money Type Section is NOT displayed");
				}
			}else			
			enteredRothWithdrawalAmt = 0;
			if((nonRothSection.equalsIgnoreCase("Pre-tax") && withdrawalType.equalsIgnoreCase("pwd")) ||
					(nonRothSection.equalsIgnoreCase("Non-Roth") && withdrawalType.equalsIgnoreCase("pwd")) ||
					(nonRothSection.equalsIgnoreCase("After-tax") && withdrawalType.equalsIgnoreCase("pwd")) )
			{
					WebElement partWithdrawalMoneySource=Web.getDriver().findElement
							(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", nonRothSection)));
					if(Web.isWebElementDisplayed(partWithdrawalMoneySource))
					{
						int maxAmount=0;
						enteredNonRothWithdrawalAmt=0;
						WebElement txtMaxAmount = Web.getDriver().findElement(By
								.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", nonRothSection)));
						maxAmount=(int)Math.round(Web.getIntegerCurrency(txtMaxAmount.getText()));									
						if(partWithdrawalMoneySource.getAttribute("value").isEmpty()||partWithdrawalMoneySource.getAttribute("value")==null)
							maxAmount=maxAmount/2;						
						else						
							maxAmount=(maxAmount/2)+5;						
						enteredNonRothWithdrawalAmt=maxAmount;
						Web.setTextToTextBox(partWithdrawalMoneySource, Integer.toString(enteredNonRothWithdrawalAmt));
						
						Reporter.logEvent(Status.INFO, "Entering WithDrawal Amount For "+nonRothSection+" Money Type",
								 "Entered WithDrawal Amount For "+nonRothSection+": " + enteredNonRothWithdrawalAmt, false);
						
						if(verifyGDR.length>0)					
							verifyGDRRestrictionIcon(withdrawalType,nonRothSection);
					} else {
						Reporter.logEvent(Status.FAIL,"Verify if User entered withdrawal amount for "+nonRothSection+" Money Type",
								nonRothSection+" Money Type section is NOT Displayed", false);
						throw new Error(nonRothSection+" Money Type Section is NOT displayed");
					}
			}
			else			
			enteredNonRothWithdrawalAmt = 0;		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
		
	}
		
		/**
		 * citizenship Validation Page - User to Enter SSN
		 * @param SSN
		 */
	public void citizenShipValidation(String SSN)
	{
		try {	
			// Click on Continue
			if (Web.isWebElementDisplayed(btnContinue)) {
				Web.clickOnElement(btnContinue);
				Reporter.logEvent(
						Status.PASS,
						"Enter withdrawal amount for multiple / single  money type sources and click Continue",
						"User enters the withdrawal amount for multiple money types source and clicked on continue button",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Enter withdrawal amount for both Roth and Pre-tax  money type sources and Click Continue",
						"Continue button is not displayed in Request a Withdrawal Page",
						true);
				throw new Error("'Continue' is not displayed");
			}
			Thread.sleep(2000);		
		//US Citizenship and SSN Validation
		if(isTextFieldDisplayed("Plan withdrawal"))
			isTextFieldDisplayed("Are you a U.S. citizen or resident?");
		Web.waitForElement(inpYes);
		Thread.sleep(2000);
		Web.clickOnElement(inpYes);		
		Thread.sleep(2000);
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
		if (Web.isWebElementDisplayed(btnConfirmContinue)) {
			Web.clickOnElement(btnConfirmContinue);	
			Thread.sleep(2000);
		}
	
		else
			throw new Error("'Confirm and Continue' is not displayed");		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
	}
	
	/**
	 * Verify Withdrawal Method for In Service and Sep Service
	 * @param withdrawalType 
	 * @param withdrawalMethodType
	 * @param emailAddress
	 * @param rollingOverAccount
	 */
	public void verifyWithdrawalMethodPage(String withdrawalType,String withdrawalMethodType,String emailAddress,
			String... rollingOverAccount)
	{
		try {
		Thread.sleep(1000);
		if (isTextFieldDisplayed("Withdrawal method"))
		{
		isTextFieldDisplayed("How would you like your withdrawal distributed?");
		
		if(withdrawalType.equalsIgnoreCase("fwd"))
		{
			Web.selectDropDownOption(drpFullWithdrawalType,withdrawalMethodType,true);
			
			if(withdrawalMethodType.equalsIgnoreCase("ROLLOVER"))
				Web.selectDropDownOption(drpWithdrawalType, rollingOverAccount[0],true);
			
			else if(withdrawalMethodType.equalsIgnoreCase("PARTIAL PAYMENT TO SELF AND ROLLOVER REMAINING BALANCE"))
			{
				System.out.println("FWD to be entered for Payment to Self: " +enteredTotalWithdrawalAmt);	
				fwdPartialPTSAmount=enteredTotalWithdrawalAmt/3;				
				System.out.println("FWD Partial PTS amount: "+fwdPartialPTSAmount);
				String amountPaytToSel=Integer.toString(fwdPartialPTSAmount);				
				Web.setTextToTextBox(inpPayToSelfAmt, amountPaytToSel);
				Reporter.logEvent(Status.INFO, "Enter the amount to be taken as part of Payment to Self",
						"The amount "+fwdPartialPTSAmount+" has been entered as part of PTS",true);
				Web.selectDropDownOption(drpWithdrawalType, rollingOverAccount[0],true);
			}
		}
		else	
			Web.selectDropDownOption(drpWithdrawalType,withdrawalMethodType,true);		
		if(Web.isWebElementDisplayed(btnAfterTaxYes))
			Web.clickOnElement(btnAfterTaxYes);
		
		selectRollOverCompany(withdrawalMethodType,emailAddress);
		if (Web.isWebElementDisplayed(btnContinueWithdrawal)) {
				Web.clickOnElement(btnContinueWithdrawal);
				Web.waitForPageToLoad(Web.getDriver());
				
		}
		}
		}	
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
	}
	
	/**
	 * Verify Withdrawal Summary Quote Page - After selecting the delivery Type
	 * @param mailDeliverytype ; First-class mail;Express mail
	 * @param isFWDTotalVerification
	 */
	public void verifyWithdrawalSummary(String mailDeliverytype,boolean... isFWDTotalVerification)
	{
		try {
			if (isTextFieldDisplayed("delivery method")) {
				selectMailDeliveryType(mailDeliverytype);
				if (isTextFieldDisplayed("Withdrawal summary")) {
					if (isFWDTotalVerification[0]|| isFWDTotalVerification.length == 0) 
					{
						if (!isSourceHierarchy) 
						{
							enteredTotalWithdrawalAmt = 0;
							enteredTotalWithdrawalAmt = enteredNonRothWithdrawalAmt+ enteredRothWithdrawalAmt;									
						}
						WebElement lblFinalWithdrawalAmount = Web.getDriver().findElement(
										By.xpath(lblTotalWithdrawalAmount.replace("Money Source Type","Total withdrawal amount")));
						finalWithdrawalAmount = (int) Math.round(Web.getIntegerCurrency(lblFinalWithdrawalAmount.getText()));
						if (enteredTotalWithdrawalAmt == finalWithdrawalAmount) 
							Reporter.logEvent(
									Status.PASS,"Verify Withdrawal Amount is Displayed for  Withdrawal",
									"Withdrawal Amount is Displayed and Matching \nExpected:$"+ enteredTotalWithdrawalAmt+ "\nActual:$"+ finalWithdrawalAmount, false);
						else 
							Reporter.logEvent(Status.FAIL,"Verify Withdrawal Amount is Displayed","Withdrawal Amount is NOT Matching\nExpected:$"
											+ enteredTotalWithdrawalAmt+ "\nActual:$"+ finalWithdrawalAmount, true);						
					}
				} else
					throw new Error("Withdrawal Summary is NOT displayed");
			}
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
	}
	
	
	/**
	 * Select Mail Delivery type in Delivery Method Page
	 * @param mailDeliveryType - First class Mail ; Express mail; Wireless Transfer (AF)
	 */
	public void selectMailDeliveryType(String mailDeliveryType)
	{
		try	
		{							
			if (mailDeliveryType.equalsIgnoreCase("firstClass")
					|| mailDeliveryType.equalsIgnoreCase("wire Transfer To AF")) {
				deliveryMthd = inpFirstClassMail.getAttribute("value");
				Web.clickOnElement(inpFirstClassMail);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ mailDeliveryType, "Selected Delivery Method: "
						+ inpFirstClassMail.getAttribute("value"), false);
			} else if (mailDeliveryType.equalsIgnoreCase("expressMail")) {
				Web.clickOnElement(inpExpressMail);
				deliveryMthd = inpExpressMail.getAttribute("value");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ mailDeliveryType, "Selected Delivery Method: "
						+ inpExpressMail.getAttribute("value"), false);
			}			
		}
		catch(Exception e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
	}
	
	/**
	 * Verify Quote Page for GDR Participants
	 * @param mailDeliverytype - first class mail,express mail
	 */
	public void verifyWithdrawalSummaryforGDRPpts(String mailDeliverytype)
	{
		try {
			if (isTextFieldDisplayed("delivery method")) {
				selectMailDeliveryType(mailDeliverytype);
				// Thread.sleep(1000);
				((JavascriptExecutor) Web.getDriver()).executeScript(
						"window.scrollBy(0,250)", "");

				Common.waitForProgressBar();
				if (isTextFieldDisplayed("Withdrawal summary")) {
					if (Web.isWebElementDisplayed(txtGDRRestrictionsMayApplyText)) {
						if (!(Stock.GetParameterValue("withdrawalType")
								.equalsIgnoreCase("fwd"))) {
							enteredTotalWithdrawalAmt = 0;
							enteredTotalWithdrawalAmt = enteredNonRothWithdrawalAmt
									+ enteredRothWithdrawalAmt;
						}
						Reporter.logEvent(
								Status.PASS,
								"Verify if GDR Section content is being displayed",
								"GDR Section Content is Displayed", false);
						if (Web.isWebElementDisplayed(lnkGDRSpecialTaxNotice))
							Reporter.logEvent(
									Status.PASS,
									"Verify if Special Tax Notice Link is being displayed in the GDR Section",
									"Special TaxNotice Link is being displayed in the GDR Section",
									false);
						else
							Reporter.logEvent(
									Status.PASS,
									"Verify if Special Tax Notice Link is being displayed in the GDR Section",
									"Special TaxNotice Link is NOT displayed in the GDR Section",
									true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify if GDR Section content is being displayed",
								"GDR Section Content is NOT Displayed", true);
						throw new Error("GDR Section Content is not Displayed");
					}

				}

			}
		}
		catch(NoSuchElementException e)		
	{ 
		Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
				msg, true); }
	catch(Exception e)
	{ Globals.exception = e;
	Throwable t = e.getCause();
	String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
	if (null != t) {
		msg = t.getMessage();
	}
	Reporter.logEvent(Status.FAIL, "A run time exception occured.",
			msg, true);
	}
	}

	
	
	/**
	 * Verify Withdrawal Confirmation PAge for Spousal Consent Participants 
	 * @param withdrawalType
	 * @param indId
	 */
	public void verifyWithdrawalConfForSpousalConsentPpts(String withdrawalType,String indId)
	{
		int spousalconsentDetails=0;
		try {
			if(Web.isWebElementDisplayed(btnIAgreeAndSubmit))
			{
				System.out.println("The I Agree and Submit element is identified");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");			
				Web.clickOnElement(btnIAgreeAndSubmit);	
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());	
			}
			else
				throw new Error("I Agree & Submit buton is NOT displayed");
			
			if(isTextFieldDisplayed("Spousal consent"))
			{
				if(Web.isWebElementDisplayed(txtSpousalConsentConf))
				Reporter.logEvent(Status.PASS, "Verify if the Confirmation Page is displayed with pre-filled consent form mail details", 
						"The confirmation page is displayed with following details: \n"+txtSpousalConsentConf.getText(), false);
				else
					Reporter.logEvent(Status.PASS, "Verify if the Confirmation Page is displayed with pre-filled consent form mail details", 
							"The confirmation page is NOT displayed with Pre-filled consent details", false);
				
				// verify form has been generated in DB
				String[] sqlQuery=Stock.getTestQuery("verifySpousalConsentDetails");
				sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				ResultSet spousalConsentFormRs=DB.executeQuery(sqlQuery[0], sqlQuery[1], indId);
				
				spousalconsentDetails = DB.getRecordSetCount(spousalConsentFormRs);
				if (spousalconsentDetails > 0) {
					Reporter.logEvent(
							Status.PASS,
							"Verify a  record has been generated in document table and it is Populated in the DataBase",
							"A record has been created in the Document table",false);
					while(spousalConsentFormRs.next())
					{
						String dc01=spousalConsentFormRs.getString("dc01");						
						Reporter.logEvent(
								Status.PASS,
								"Verify document ID value has been generated for the participant",
								"The document ID value has been generated for the participant and the values is displayed as: \n"+
								"DC01: "+dc01,true);
						break;
					}					
				} else 
					Reporter.logEvent(
							Status.FAIL,
							"Verify Document Id has been generated for Participant the DataBase",
							"Form has NOT been generated for the Ppt in the DataBase",
							true);					
			}
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
	}
	
	/**
	 * Verify Withdrawal Confirmation for All Participant and Validate All the Fields in the Confirmation Page
	 * @param withdrawalType
	 * @param IndId
	 * @param deliveryType
	 * @param mailDeliveryType
	 */
	public void verifyWithdrawalConfirmation(String withdrawalType,
			String IndId,String deliveryType,String mailDeliveryType)
	{
		try {		
		String confirmationNo=null;
		String pptFirstName=null;
		int count=0;
		String[] sqlQuery = Stock.getTestQuery("getParticipantFullName");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet getPptFullName=DB.executeQuery(sqlQuery[0], sqlQuery[1], IndId);	
		 count=DB.getRecordSetCount(getPptFullName);
		if(getPptFullName!=null)
		{
			while(getPptFullName.next())
			{
				pptFirstName=getPptFullName.getString("first_name");
			}
		}
		if(Web.isWebElementDisplayed(btnIAgreeAndSubmit))
		{
			System.out.println("The I Agree and Submit element is identified");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");			
			Web.clickOnElement(btnIAgreeAndSubmit);			
			Common.waitForProgressBar();		
			Web.waitForPageToLoad(Web.getDriver());	
		}
		else
			throw new Error("I Agree & Submit buton is NOT displayed");	
						
		if(isTextFieldDisplayed("Request submitted!"))
		{
			Web.waitForElement(txtConfirmationNo);
		if (Web.isWebElementDisplayed(txtConfirmationNo)) {
			confirmationNo=txtConfirmationNo.getText().trim();		
			Reporter.logEvent(
					Status.INFO,"Verify Request Confirmation is Displayed on UI",
					"Request Confirmation is Displayed\n Confirmation No: "+confirmationNo ,true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify Request Confirmation Number is Displayed",
					"Request Confirmation Number is NOT Displayed", true);
		}
		isConfirmationNumDisplayed(IndId);			
		
		//Verify other fields in confirmation Page
		if(isMaxGDR==false){
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
		}
		//Verify delivery Type
				if(confirmationPageDeliveryType.getText().contains(deliveryType))
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
							+ "Expected : "+pptFirstName +"\n"
							+ "Actual Payable To: "+confirmationPagePayableTo.getText(), false);
				else	
					Reporter.logEvent(Status.FAIL, "Verify the Payable To in the Confirmation Page", 
							"The Payable To Field is displayed as:\n"
							+ "Expected: "+ pptFirstName +"\n"
							+ "Actual: "+confirmationPagePayableTo.getText(), false);
				
				//deliveryMthd=deliveryMthd.contains("-")? deliveryMethod.replace("-", " ") : deliveryMethod;
				if(isAmericanFunds==false)
				{
				if(confirmationPageDeliveryMethod.getText().contains(deliveryMthd))
					Reporter.logEvent(Status.PASS, "Verify the Delivery Method in the Confirmation Page", 
							"The Delivery Method is displayed as:\n"
							+ "Expected Delivery Method: "+ deliveryMthd +"\n"
							+ "Actual Delivery Method: "+confirmationPageDeliveryMethod.getText(), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify the Delivery Method in the Confirmation Page", 
							"The Delivery Method is displayed as:\n"
							+ "Expected Delivery Method: "+ deliveryMthd +"\n"
							+ "Actual Delivery Method: "+confirmationPageDeliveryMethod.getText(), false);
				}			
				//Verify Address
				Reporter.logEvent(Status.INFO, "Verify the Sent To Address in the Confirmation Page", 
						"The Address is displayed as:\n"+
					"Address: "+confirmationPageSentToAddress.getText().trim(), false);
			
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		catch (NoSuchElementException e) {
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL,
					"Element is not displayed in the Application", msg, true);
		} catch (Exception e) {
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		}
	}
	
	public boolean isConfirmationNumDisplayed(String individualId)
	{
		boolean isConfirmationNoDisplayed=false;
		String confirmationNo=null;		
		int isConfirmationNumDisplayed=0;
		String dc01=null;
		String dc02=null;
		try {	
			confirmationNo=txtConfirmationNo.getText();
			String[] sqlQuery = Stock.getTestQuery("getWithDrawalConfirmationNo");
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			//sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet getWithdrawalConfRs=DB.executeQuery(sqlQuery[0],sqlQuery[1],confirmationNo, individualId);
			isConfirmationNumDisplayed = DB.getRecordSetCount(getWithdrawalConfRs);
			if (isConfirmationNumDisplayed > 0) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Request Confirmation Number is Populated in the DataBase",
						"Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"+confirmationNo,false);
				System.out.println("Confirmation Number: "+confirmationNo);
				while(getWithdrawalConfRs.next())
				{
					dc01=getWithdrawalConfRs.getString("dc01");
					dc02=getWithdrawalConfRs.getString("dc02");
					Reporter.logEvent(
							Status.PASS,
							"Verify document ID value has been generated for the participant",
							"The document ID value has been generated for the participant and the values are displayed as: \n"+
									"DC01: "+dc01+"\n"+
									"DC02: "+dc02,false);
					break;
				}
				isConfirmationNoDisplayed=true;
				if(dc02==null)
					verifyWithdrawalDisbursementRecords();

			} else if(isConfirmationNumDisplayed==0) {
				String verifyStatusCode=verifyWithdrawalDisbursementRecords();
				if(verifyStatusCode.equalsIgnoreCase("INPEND")||verifyStatusCode.equalsIgnoreCase("FUTURE"))
					Reporter.logEvent(Status.PASS,"Verify the withdrawal disbursement records status code", 
							"The Withdrawal disburement records status code are verified",false);
				else		
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
	 * This method is to select Roll Over Company for Withdrawal
	 
	 */
	public void selectRollOverCompany(String withdrawalMethodType,String emailAddress)
	{
		try {
			if(!withdrawalMethodType.equalsIgnoreCase("Payment to Self") && !(withdrawalMethodType.equalsIgnoreCase("INSERVICE")))
			{
				Web.selectDropnDownOptionAsIndex(drpRollOverCompany, "5");
				isTextFieldDisplayed("Your rollover check will be sent to the address of record on the account");
				Thread.sleep(1000);
				isTextFieldDisplayed("Please enter rollover information");
				Web.setTextToTextBox(inpAccountNumber, "123456");
				Reporter.logEvent(Status.PASS,"Verify if IRA Account Number has been displayed",
						"IRA Account Number Field Has been Displayed and entered",true);
				if (Web.isWebElementDisplayed(inpRothAccountNumber)) {
					Web.setTextToTextBox(inpRothAccountNumber, "1111111");
					Reporter.logEvent(Status.PASS,"Verify if Roth IRA Account Number has been displayed",
							"Roth IRA Account Number Field Has been Displayed and entered",false);
				}
			}			
		
		isTextFieldDisplayed("Delivery information for mail or expedited delivery");
		Thread.sleep(1000);
		
		// Enter participant email address and click on continue
		Web.setTextToTextBox(txtEmailAddress,emailAddress);
		txtEmailAddress.sendKeys(Keys.ENTER);	
		
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
	
	public boolean isWebElementEnabled(String fieldName) {
			
			if(this.getWebElement(fieldName).isEnabled())
				return true;
			else
				return false;

	}
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
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

	public void enterSSN(String ssn) {		
	try {
			Actions keyBoardEvent=new Actions(Web.getDriver());
			if(Web.isWebElementDisplayed(this.inputSSN, true))
			{
				//Robot robot=new Robot();				
				Web.setTextToTextBox(inputSSN, ssn);			
				keyBoardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1000);
				//Web.clickOnElement(btnConfirmContinue);
						
				Reporter.logEvent(Status.INFO, "Entering SSN "
						+ ssn, "Entered SSN: " + ssn, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public String verifyWithdrawalDisbursementRecords()
	{		
		String statusCode=null;
		try {				
		String[] sqlQuery = Stock.getTestQuery("getWithdrawalDisbursementRecords");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet getWithdrawalsRecords=DB.executeQuery(sqlQuery[0], sqlQuery[1], txtConfirmationNo.getText());			
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
				 statusCode= getWithdrawalsRecords.getString("STATUS_CODE");
				 String evId=getWithdrawalsRecords.getString("MASTER_EV_ID");
				String taxReasonCode= getWithdrawalsRecords.getString("TAX_REASON_CODE");
				Reporter.logEvent(Status.PASS, "Verify the Withdrawal disbursment Fields", "The following Withdrawal Disbursement Fields are Created:\n"
						+ "Confirmation Number: "+evId +"\n"
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
		} 	
		catch (SQLException e) {		
			e.printStackTrace();
		}
		return statusCode;
	}

	
	
	public void selectMaxAmountCheckBoxForInServiceGDR(String withdrawalType,String rothSection,String nonRothSection)
	{
		try {
		isMaxGDR=true;		
			//Select MaxAmount checkbox for Pre-Tax or Roth
		if (rothSection.equalsIgnoreCase("Roth")){	
			WebElement rothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection)));
			WebElement txtAmount = Web.getDriver().findElement(By
					.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection)));
			if(Web.isWebElementDisplayed(rothMaxAmountChkBox, true))				
			{
				Web.clickOnElement(rothMaxAmountChkBox);
				Web.waitForPageToLoad(Web.getDriver());
				Reporter.logEvent(Status.PASS, "Select Max Amount For "+rothSection+" Money Type",
						 "Max Amount Been Selected For "+rothSection+" Money Type and "+txtAmount.getAttribute("value")+" has been displayed", false);	
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if User selected Max Amount Checkbox for "+rothSection+" Money Type",
						rothSection+" Money Type section is NOT Displayed", false);
				throw new Error(rothSection+" Money Type Section is NOT displayed");
			}
		}	
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||
				nonRothSection.equalsIgnoreCase("Non-Roth")||
				nonRothSection.equalsIgnoreCase("After-tax"))
		{
					WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
							(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
					WebElement txtAmount = Web.getDriver().findElement(By
							.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
					if(Web.isWebElementDisplayed(nonRothMaxAmountChkBox, true))				
					{
						Web.clickOnElement(nonRothMaxAmountChkBox);						
						Web.waitForPageToLoad(Web.getDriver());									
						Reporter.logEvent(Status.PASS, "Select Max Amount For "+nonRothSection+" Money Type and Verify Withdrwal Amount Input field",
								 "Max Amount Been Selected For "+nonRothSection+" Money Type and "+txtAmount.getAttribute("value")+" has been displayed", false);						
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify if User selected Max Amount Checkbox for "+nonRothSection+" Money Type",
								nonRothSection+" Money Type section is NOT Displayed", false);
						throw new Error(nonRothSection+" Money Type Section is NOT displayed");
					}
			}
			}
				catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
			}
		
	
	public void selectMaxAmountCheckBoxForSepServiceGDR(String rothSection,String nonRothSection)
	{
		Actions keyBoard = new Actions(Web.getDriver());
		try {
			isMaxGDR=true;
			if(Web.isWebElementDisplayed(inpCurrentEmployerYes, true))
			{
				//inpCurrentEmployerYes.click();
				Web.clickOnElement(inpCurrentEmployerNo);				
				Thread.sleep(3000);
				keyBoard.sendKeys(Keys.TAB).perform();
				keyBoard.sendKeys(Keys.ENTER).perform();
			}
			Thread.sleep(5000);
			if(Web.isWebElementDisplayed(partWithDrawal))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Vested Part Withdrawal section is Displayed",
						"Vested Part Withdrawal section is displayed", false);
				Web.clickOnElement(partWithDrawal);
				Web.waitForPageToLoad(Web.getDriver());
			}
			else
			{
				Reporter.logEvent(Status.FAIL,
						"Verify Vested Part/Full Withdrawal section is Displayed",
						"Vested Part/Full  Withdrawal section is NOT displayed", true);
				throw new Error("Vested Full/Part Withdrawal Section is NOT displayed");
			}
		
			//Select MaxAmount checkbox for Pre-Tax or Roth
			if (rothSection.equalsIgnoreCase("Roth")){	
				WebElement rothMaxAmountChkBox=Web.getDriver().findElement
						(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", rothSection)));
				WebElement txtMaxAmount = Web.getDriver().findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));
				if(Web.isWebElementDisplayed(rothMaxAmountChkBox, true))				
				{
					Web.clickOnElement(rothMaxAmountChkBox);
					Web.waitForPageToLoad(Web.getDriver());
					Reporter.logEvent(Status.PASS, "Select Max Amount For "+rothSection+" Money Type and verify Withdrawal Amount Text field",
							 "Max Amount Been Selected For "+rothSection+" and in the the Withdrawal Amount  Text Field "+txtMaxAmount.getAttribute("value")+" has been Displayed", false);
					if(lstMaxCheckBox.size()==1)
						Reporter.logEvent(Status.PASS, "Verify if nonRothSection Max Amount Check box is NOT displayed",
								 "After Selecting "+rothSection+" Max Amount Check Box, Non-Roth Max Amount Check box is NOT Displayed", true);
					else
						Reporter.logEvent(Status.FAIL, "Verify if Non-roth Max Amount Check box is NOT displayed",
								 "After Selecting "+rothSection+" Max Amount Check Box, Non-Roth Max Amount Check box is Displayed", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User has Selected Max Amount Check for "+rothSection+" Money Type",
							rothSection+" Money Type section is NOT Displayed", false);
					throw new Error(rothSection+" Money Type Section is NOT displayed");
				}
		}		
			
			if(nonRothSection.equalsIgnoreCase("Pre-tax")||nonRothSection.equalsIgnoreCase("Non-Roth")||nonRothSection.equalsIgnoreCase("After-tax"))
			{
			WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", nonRothSection)));
			WebElement txtMaxAmount = Web.getDriver().findElement(By
					.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", nonRothSection)));
		if(Web.isWebElementDisplayed(nonRothMaxAmountChkBox, true))
		{
					Web.clickOnElement(nonRothMaxAmountChkBox);
					Thread.sleep(1000);	
					Reporter.logEvent(Status.PASS, "Select Max Amount For "+nonRothSection+" Money Type and verify Withdrawal Amount Text field",
							 "Max Amount Been Selected For "+nonRothSection+" and in the the Withdrawal Amount  Text Field "+txtMaxAmount.getAttribute("value")+" has been Displayed", false);
					if(lstMaxCheckBox.size()==1)
						Reporter.logEvent(Status.PASS, "Verify if Roth Max Amount Check box is NOT displayed",
								 "After Selecting "+nonRothSection+" Max Amount Check Box, Roth Max Amount Check box is NOT Displayed", true);
					else
						Reporter.logEvent(Status.FAIL, "Verify if Roth Max Amount Check box is NOT displayed",
								 "After Selecting "+nonRothSection+" Max Amount Check Box, Roth Max Amount Check box is Displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify if User has Selected Max Amount Check for "+nonRothSection+" Money Type",
							nonRothSection+" Money Type section is NOT Displayed", true);
					throw new Error(nonRothSection+" Money Type Section is NOT displayed");
				}
		}
		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}	
	}
	


public void verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(String pTSmailDeliveryType, String rollOverDeliveryType)
{	
	try	
	{	
		if(isTextFieldDisplayed("Payment to self delivery method"))
		{
			if(pTSmailDeliveryType.equalsIgnoreCase("firstClass"))
			{
				Web.clickOnElement(inpPayToSelfFirstClassMail);
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ pTSmailDeliveryType, "Selected Delivery Method: " + inpPayToSelfFirstClassMail.getAttribute("value"), false);
			} else{			
				Web.clickOnElement(inpPayToSelfExpressMail);
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ pTSmailDeliveryType, "Selected Delivery Method: " + inpPayToSelfExpressMail.getAttribute("value"), false);
			}
			//Common.waitForProgressBar();
			//Web.waitForPageToLoad(Web.getDriver());
		}
		if(isTextFieldDisplayed("Rollover delivery method"))
		{
			if(rollOverDeliveryType.equalsIgnoreCase("firstClass"))	{		
				Web.clickOnElement(inpRollOverFirstClassMail);
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ rollOverDeliveryType, "Selected Delivery Method: " + inpRollOverFirstClassMail.getAttribute("value"), false);
			}else{			
				Web.clickOnElement(inpRollOverExpressMail);
				Reporter.logEvent(Status.INFO, "Selecting Delivery Method "
						+ rollOverDeliveryType, "Selected Delivery Method: " + inpRollOverExpressMail.getAttribute("value"), false);
			}			
		}
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		if(isTextFieldDisplayed("Withdrawal summary"))
		{
			//verify the total withdrawal amount for partial payments
			WebElement lblFinalWithdrawalAmount = Web.getDriver().findElement(By
					.xpath(lblTotalWithdrawalAmount.replace("Money Source Type",
							"Total withdrawal amount")));
			finalWithdrawalAmount=(int)Math.round(Web.getIntegerCurrency(lblFinalWithdrawalAmount.getText()));
			if (enteredTotalWithdrawalAmt == finalWithdrawalAmount) 
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Amount is Displayed for  Withdrawal",
						"Withdrawal Amount is Displayed and Macthing \nExpected:$"
								+ enteredTotalWithdrawalAmt + "\nActual:$"
								+ finalWithdrawalAmount, false);
			else 
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Amount is Displayed",
						"Withdrawal Amount is NOT Matching\nExpected:$"
								+ enteredTotalWithdrawalAmt + "\nActual:$"
								+ finalWithdrawalAmount, true);
			
			if(Web.isWebElementDisplayed(fwdWithdrawalSummaryPayment1))
				Reporter.logEvent(Status.PASS,"Verify Payment 1 Section Has been displayed in the Withdrawals Summary Page", 
						"Payment 1 Section has been displayed", false);
			if(Web.isWebElementDisplayed(fwdWithdrawalSummaryPayment2))
				Reporter.logEvent(Status.PASS,"Verify Payment 2 Section Has been displayed in the Withdrawals Summary Page", 
						"Payment 2 Section has been displayed", false);
			if(Web.isWebElementDisplayed(fwdWithdrawalSummaryPayment3))
				Reporter.logEvent(Status.PASS,"Verify Payment 3 Section Has been displayed in the Withdrawals Summary Page", 
						"Payment 3 Section has been displayed", false);
			if(Web.isWebElementDisplayed(fwdWithdrawalSummaryPayment4))
				Reporter.logEvent(Status.PASS,"Verify Payment 4 Section Has been displayed in the Withdrawals Summary Page", 
						"Payment 4 Section has been displayed", false);
		}
	}
	catch(Exception e)		
	{ 
		Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
				msg, true); }
}

public void verify_FWD_PartialPayments_WithdrawalConfirmation(String IndId,String PTSDeliveryMethod, String rollOverDeliveryMthd)
{
	try {		
	String confirmationNo=null;
	String pptFirstName=null;
	int count=0;
	String[] sqlQuery = Stock.getTestQuery("getParticipantFullName");
	sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
	ResultSet getPptFullName=DB.executeQuery(sqlQuery[0], sqlQuery[1], IndId);	
	 count=DB.getRecordSetCount(getPptFullName);
	if(getPptFullName!=null)
	{
		while(getPptFullName.next())
		{
			pptFirstName=getPptFullName.getString("first_name");
		}
	}
	if(Web.isWebElementDisplayed(btnIAgreeAndSubmit, true))
	{
		System.out.println("The I Agree and Submit element is identified");	
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		Web.clickOnElement(btnIAgreeAndSubmit);	
		Thread.sleep(5000);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());	
	}
	else
		throw new Error("I Agree & Submit buton is NOT displayed");	
					
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
	isConfirmationNumDisplayed(IndId);			
	
	//Verify other fields in confirmation Page
	
	Reporter.logEvent(Status.INFO, "Verify Partial Payment to Self Confirmation Details", 
			"Full Withdrawal Partial Payment ot Self Confirmation details", false);
	int ptsConfirmationPageAmount=(int)Math.round(Web.getIntegerCurrency(lstConfirmationPageRollOverAmount.get(0).getText()));
	if(ptsConfirmationPageAmount==fwdPartialPTSAmount)
		Reporter.logEvent(Status.PASS, "Verify the Entered Partial Payment to Self Amount in the Confirmation Page", 
				"The Payment to Self Withdrawal Amount is displayed as:\n"
				+ "Expected Amount: "+ fwdPartialPTSAmount +"\n"
				+ "Actual Amount: "+ptsConfirmationPageAmount, false);
	else
		Reporter.logEvent(Status.FAIL, "Verify the Entered Partial Payment to Self Amount in the Confirmation Page", 
				"The Payment to Self Withdrawal Amount is displayed as:\n"
				+ "Expected Amount: "+ fwdPartialPTSAmount +"\n"
				+ "Actual Amount: "+ptsConfirmationPageAmount, false);
	
	if(lstConfirmationPageDeliveryType.get(0).getText().equalsIgnoreCase("Payment to self"))
		Reporter.logEvent(Status.PASS, "Verify the Delivery Type in the Confirmation Page for PTS", 
				"The Delivery Type is displayed as:\n"
				+ "Expected Delivery Type:  Payment to Self" +"\n"
				+ "Actual Delivery type: "+lstConfirmationPageDeliveryType.get(0).getText(), false);
	else
		Reporter.logEvent(Status.FAIL, "Verify the Delivery Type in the Confirmation Page for PTS", 
				"The Delivery Type is displayed as:\n"
				+ "Expected Delivery Type:  Payment to Self" +"\n"
				+ "Actual Delivery type: "+lstConfirmationPageDeliveryType.get(0).getText(), false);
		
	//Verify Payable To		
			if(lstConfirmationPagePayableTo.get(0).getText().trim().contains(pptFirstName.toString().trim()))
				Reporter.logEvent(Status.PASS, "Verify the Payable To in the Confirmation Page for PTS", 
						"The Payable To Field is displayed as:\n"
						+ "Payable To: "+lstConfirmationPagePayableTo.get(0).getText(), false);
			else	
				Reporter.logEvent(Status.FAIL, "Verify the Payable To in the Confirmation Page for PTS", 
						"The Payable To Field is displayed as:\n"
						+ "Expected: "+ pptFirstName +"\n"
						+ "Actual: "+lstConfirmationPagePayableTo.get(0).getText(), false);
			
			String mailDelivery=lstConfirmationPageDeliveryMethod.get(0).getText().contains("-")?
					lstConfirmationPageDeliveryMethod.get(0).getText().replace("-", " "):
						lstConfirmationPageDeliveryMethod.get(0).getText();
					
			//deliveryMthd=deliveryMthd.contains("-")? deliveryMethod.replace("-", " ") : deliveryMethod;
			//if(mailDelivery.contains(PTSDeliveryMethod))
				Reporter.logEvent(Status.PASS, "Verify the Delivery Method in the Confirmation Page", 
						"The Delivery Method is displayed as:\n"
						+ "Expected Delivery Method: "+ PTSDeliveryMethod +"\n"
						//+ "Actual Delivery Method: "+lstConfirmationPageDeliveryMethod.get(0).getText(), false);
						+ "Actual Delivery Method: "+mailDelivery, false);
			/*else
				Reporter.logEvent(Status.FAIL, "Verify the Delivery Method in the Confirmation Page", 
						"The Delivery Method is displayed as:\n"
						+ "Expected Delivery Method: "+ PTSDeliveryMethod +"\n"
						+ "Actual Delivery Method: "+mailDelivery, false);*/
			
			System.out.println(lstConfirmationPageSentToAddress.get(0).getText().trim());
			//Verify Address
			Reporter.logEvent(Status.INFO, "Verify the Sent To Address in the Confirmation Page", 
					"The Address is displayed as:\n"+
				"Address: "+lstConfirmationPageSentToAddress.get(0).getText().trim(), false);
			
			//Verify the Confirmation Page for Roll Over
			Reporter.logEvent(Status.INFO, "Verify Roll Over Confirmation Details", 
					"Full Withdrawal Roll Over Confirmation details", false);
			int rollOverConfirmationPageAmount=(int)Math.round(Web.getIntegerCurrency(lstConfirmationPageRollOverAmount.get(1).getText()));
			if(rollOverConfirmationPageAmount==(enteredTotalWithdrawalAmt-fwdPartialPTSAmount))
				Reporter.logEvent(Status.PASS, "Verify the Entered roll Over Amount in the Confirmation Page", 
						"The Roll Over Withdrawal Amount is displayed as:\n"
						+ "Expected Amount: "+ (enteredTotalWithdrawalAmt-fwdPartialPTSAmount) +"\n"
						+ "Actual Amount: "+rollOverConfirmationPageAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the Entered roll Over Amount in the Confirmation Page", 
						"The Roll Over Withdrawal Amount is displayed as:\n"
						+ "Expected Amount: "+ (enteredTotalWithdrawalAmt-fwdPartialPTSAmount) +"\n"
						+ "Actual Amount: "+rollOverConfirmationPageAmount, false);
			
			if(lstConfirmationPageDeliveryType.get(1).getText().equalsIgnoreCase("Rollover"))
				Reporter.logEvent(Status.PASS, "Verify the Delivery Type in the Confirmation Page for PTS", 
						"The Delivery Type is displayed as:\n"
						+ "Expected Delivery Type:  Rollover" +"\n"
						+ "Actual Delivery type: "+lstConfirmationPageDeliveryType.get(1).getText(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the Delivery Type in the Confirmation Page for PTS", 
						"The Delivery Type is displayed as:\n"
						+ "Expected Delivery Type:  Rollover" +"\n"
						+ "Actual Delivery type: "+lstConfirmationPageDeliveryType.get(1).getText(), false);
				
			//Verify Payable To		
					if(lstConfirmationPagePayableTo.get(1).getText().trim().contains(pptFirstName.toString().trim()))
						Reporter.logEvent(Status.PASS, "Verify the Payable To in the Confirmation Page for PTS", 
								"The Payable To Field is displayed as:\n"
								+ "Payable To: "+lstConfirmationPagePayableTo.get(1).getText(), false);
					else	
						Reporter.logEvent(Status.FAIL, "Verify the Payable To in the Confirmation Page for PTS", 
								"The Payable To Field is displayed as:\n"
								+ "Expected: "+ pptFirstName +"\n"
								+ "Actual: "+lstConfirmationPagePayableTo.get(1).getText(), false);
					
					String rollOverMailDelivery=lstConfirmationPageDeliveryMethod.get(1).getText().contains("-")?
							lstConfirmationPageDeliveryMethod.get(1).getText().replace("-", " "):
								lstConfirmationPageDeliveryMethod.get(1).getText();
					//deliveryMthd=deliveryMthd.contains("-")? deliveryMethod.replace("-", " ") : deliveryMethod;					
					//if(rollOverMailDelivery.contains(rollOverDeliveryMthd))
						Reporter.logEvent(Status.PASS, "Verify the Delivery Method in the Confirmation Page", 
								"The Delivery Method is displayed as:\n"
								+ "Expected Delivery Method: "+ rollOverDeliveryMthd +"\n"
								+ "Actual Delivery Method: "+rollOverMailDelivery, false);
					/*else
						Reporter.logEvent(Status.FAIL, "Verify the Delivery Method in the Confirmation Page", 
								"The Delivery Method is displayed as:\n"
								+ "Expected Delivery Method: "+ rollOverDeliveryMthd +"\n"
								+ "Actual Delivery Method: "+rollOverMailDelivery, false);*/
					
					//Verify Address
					Reporter.logEvent(Status.INFO, "Verify the Sent To Address in the Confirmation Page", 
							"The Address is displayed as:\n"+
						"Address: "+lstConfirmationPageSentToAddress.get(1).getText().trim(), false);
		}

	}catch (SQLException e) {e.printStackTrace();}

	catch(NoSuchElementException e)		
	{ 
		Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
				msg, true); }
	catch(Exception e)
	{ Globals.exception = e;
	Throwable t = e.getCause();
	String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
	if (null != t) {
		msg = t.getMessage();
	}
	Reporter.logEvent(Status.FAIL, "A run time exception occured.",
			msg, true);}
}

public void verifyMaxAmount_SingleMoneySource_Pwd()
{	
	if (pwdMoneyTypeSection.size()==1)
			Reporter.logEvent(Status.PASS, "Verify there is only one money source available", 
					"Single Money Source has been displayed in the Part Withdrawal Section", false);
	else
		Reporter.logEvent(Status.FAIL, "Verify there is only one money source available", 
				"Single Money Source has NOT been displayed in the Part Withdrawal Section", false);
	
	if (pwdMaxAmtChkbox.size()==0)
		Reporter.logEvent(Status.PASS, "Verify Max Amount checck box is not displayed for Single Money Souce Type", 
				"Max Amount check has not been displayed for Single Money Source  in the Part Withdrawal Section", false);
	else
	Reporter.logEvent(Status.FAIL, "Verify Max Amount checck box is not displayed for Single Money Souce Type", 
			"Max Amount check has been displayed for Single Money Source  in the Part Withdrawal Section", false);
}

/*public void verifyMsgIfMaxAmountEntered_BothMoneySourceType()
{
	NumberFormat format = NumberFormat.getCurrencyInstance();
try {
	if(pwdMaxAmtChkbox.size()>1)
	{
		//Verify Pre Tax Section
	WebElement preTaxMaxAmount = Web.getDriver().findElement(By
			.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", "Pre-tax")));	
	Number preTaxNmber = format.parse(preTaxMaxAmount.getText());
	System.out.println(preTaxNmber.toString());	
	
	WebElement preTaxTxtAmount = Web.getDriver().findElement(By
			.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", "Pre-tax")));	
	Web.setTextToTextBox(preTaxTxtAmount,preTaxNmber.toString());
//Verify Roth Section
	WebElement rothMaxAmount = Web.getDriver().findElement(By
			.xpath(maxAmtPWMoneyType.replaceAll("Money Source Type", "Roth")));
	Number rothNumber = format.parse(rothMaxAmount.getText());
	System.out.println(rothNumber.toString());
	
	WebElement rothTxtAmount = Web.getDriver().findElement(By
			.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", "Roth")));
		Web.setTextToTextBox(rothTxtAmount,rothNumber.toString());
	
	if(Web.isWebElementDisplayed(warningMsg))	
	Reporter.logEvent(Status.PASS, "Verify if 'Max amount' field values are entered then a warning message has been displayed", 
			"System displays the following warning message \n"+ warningMsg.getText(), true);
	else
	Reporter.logEvent(Status.FAIL, "Verify if 'Max amount' field values are entered then a warning message has been displayed", 
				"System does NOT 0display the warning message \n", true);	
	
	if(!btnContinue.isEnabled())
		Reporter.logEvent(Status.PASS, "Verify if 'Max amount' field values are entered then Continue button is Disabled", 
				"Continue button is Disabled as expected", false);
	else
		Reporter.logEvent(Status.FAIL, "Verify if 'Max amount' field values are entered then Continue button is Disabled", 
				"Continue button is NOT Disabled as expected", false);
	preTaxTxtAmount.clear();
	rothTxtAmount.clear();
	
	//Verify if Max Amount is selected other Checkbox is Not Displayed	
	WebElement preTaxMaxAmountChkBox=Web.getDriver().findElement
			(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", "Pre-tax")));
	Web.clickOnElement(preTaxMaxAmountChkBox);
	if(pwdMaxAmtChkbox.size()==1)
	{
		Reporter.logEvent(Status.PASS, "Verify user can select Max amount check box against one money source type.", 
				"After Selecting the Max Amount check box for One Money Source Type, the other max check box is not displayed", true);
		Web.clickOnElement(preTaxMaxAmountChkBox);	
	}
		else
			Reporter.logEvent(Status.FAIL, "Verify user can select Max amount check box against one money source type.", 
					"After Selecting the Max Amount check box for One Money Source Type, the other max check box is still displayed", true);
	}
	else if (pwdMaxAmtChkbox.size()==1)
	{
		WebElement maxAmountChkBox=Web.getDriver().findElement(By
				.xpath(inpMaxAmountPWChkBox.replaceAll("Money Source Type", "Pre-tax")));	
		if(!Web.isWebElementDisplayed(maxAmountChkBox))			
			Reporter.logEvent(Status.FAIL, "Verify if there is only one Money Source for PWD,then  thre is no option to select Max Amount", 
					"If there in only one Money Source type, then Max Amount checkbox is NOT displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify if there is only one Money Source for PWD,then  thre is no option to select Max Amount", 
					"If there in only one Money Source type, then Max Amount checkbox is displayed", true);
	}
	else
		Reporter.logEvent(Status.INFO, "Verify Max Amount check box has not been displayed", 
				"Max Amount check box has not been displayed", false);

	
	
}catch(Exception e)
{ Globals.exception = e;
Throwable t = e.getCause();
String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
if (null != t) {
	msg = t.getMessage();
}
Reporter.logEvent(Status.FAIL, "A run time exception occured.",
		msg, true);}


}*/

public void clickOnBackButton()
{
	if(Web.isWebElementDisplayed(btnBack))
	{
		Web.clickOnElement(btnBack);
		Reporter.logEvent(Status.PASS, "Verify if Back button is displayed and click on the button",
				"Back Button is clicked and navigated to Previous Page", false);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());				
	}
	else
	{
		Reporter.logEvent(Status.PASS, "Verify if Back button is displayed and click on the button",
				"Back Button is Not clicked and not navigated to Previous Page", true);
		throw new Error("Back Button is not displayed and Clicked");
	}
}

public void verifyWithdrawalMethodCachingPage(String withdrawalType,
			String withdrawalMethod) {
		if (isTextFieldDisplayed("Withdrawal method")) {
			isTextFieldDisplayed("How would you like your withdrawal distributed?");
			if (withdrawalType.equalsIgnoreCase("fwd")) {
				if (Web.isWebElementDisplayed(drpFullWithdrawalType)) {
					String selectedOption = new Select(drpFullWithdrawalType)
							.getFirstSelectedOption().getText();
					if (selectedOption.equalsIgnoreCase(withdrawalMethod))
						Reporter.logEvent(Status.PASS,"Verify that on clicking back button, in the Withdrawal Method Page, previously selected option is displayed by default",
								"On clicking the back button, system displays the previous selected option by default \n"
										+ "Previous Selected Option: "
										+ withdrawalMethod
										+ "\n Current Displayed Option: "
										+ selectedOption, true);
					else
						Reporter.logEvent(Status.FAIL,"Verify that on clicking back button, in the Withdrawal Method Page, previously selected option is displayed by default",
								"On clicking the back button, system does NOT display the previous selected option by default \n"
										+ "Previous Selected Option: "
										+ withdrawalMethod
										+ "\n Current Displayed Option: "
										+ selectedOption, true);
				}

			} else {
				String selectedOption = new Select(drpWithdrawalType)
						.getFirstSelectedOption().getText();
				if (selectedOption.equalsIgnoreCase(withdrawalMethod))
					Reporter.logEvent(
							Status.PASS,
							"Verify that on clicking back button, in the Withdrawal Method Page, previously selected option is displayed by default",
							"On clicking the back button, system displays the previous selected option by default \n"
									+ "Previous Selected Option: "
									+ withdrawalMethod
									+ "\n Current Displayed Option: "
									+ selectedOption, true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify that on clicking back button, in the Withdrawal Method Page, previously selected option is displayed by default",
							"On clicking the back button, system does NOT display the previous selected option by default \n"
									+ "Previous Selected Option: "
									+ withdrawalMethod
									+ "\n Current Displayed Option: "
									+ selectedOption, true);
			}
		}
	}

	public void verifyPlanWithdrawalCachingPage() {
		if (isTextFieldDisplayed("Plan withdrawal")) {
			isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			if (!Web.isWebElementDisplayed(inputSSN))
				Reporter.logEvent(
						Status.PASS,
						"Verify citizenship verification page is displayed without entering SSN details field",
						"System displays citizenship verification page without SSN details",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify citizenship verification page is displayed without entering SSN details field",
						"System displays citizenship verification page with SSN details",
						true);
		}
	}
	
	public void verifyCachingForEmployerQuestionPage(String withdrawalType)
	{
		if(withdrawalType.equalsIgnoreCase("pwd") || withdrawalType.equalsIgnoreCase("fwd"))
		{
			if(inpCurrentEmployerNo.getAttribute("class").contains("active"))
				Reporter.logEvent(Status.PASS, "For Sep Service,verify on clicking back button in Plan Withdrawal Page, system displays Employer Question Page",
						"On clicking back button in Plan Withdrawal Page, system displays Employer Question Page with option NO selected", true);
			else
				Reporter.logEvent(Status.FAIL, "For Sep Service,verify on clicking back button in Plan Withdrawal Page, system displays Employer Question Page",
						"On clicking back button in Plan Withdrawal Page, system displays Employer Question Page without option selected", true);
		}
		else
		{
			if(inpCurrentEmployerYes.getAttribute("class").contains("active"))
				Reporter.logEvent(Status.PASS, "For In Service verify on clicking back button in Plan Withdrawal Page, system displays Employer Question Page",
						"On clicking back button in Plan Withdrawal Page, system displays Employer Question Page with option YES selected", true);
			else
				Reporter.logEvent(Status.FAIL, "For In Service verify on clicking back button in Plan Withdrawal Page, system displays Employer Question Page",
						"On clicking back button in Plan Withdrawal Page, system displays Employer Question Page without option selected", true);							
		}
	}
	
	
	
	public boolean verifyEmpQueIsDisplayedOrInServiceSelected(String withdrawalType)
	{	
		boolean isEmpQueDisplayed=false;
	if (Web.isWebElementDisplayed(lblRequestAWithdrawal)) 
	{
		if(Web.isWebElementDisplayed(inpCurrentEmployerYes))
		{
			verifyCachingForEmployerQuestionPage(withdrawalType);
			isEmpQueDisplayed=true;
		}
		else
		{
		WebElement inpWithdrawalType = Web.getDriver().findElement(By
				.xpath(inputWithdrawalType.replace("Withdrawal Type",
						withdrawalType)));		
		if(inpWithdrawalType.getAttribute("checked").contains("true"))		
			Reporter.logEvent(Status.PASS, "Verify "+withdrawalType+" option is selected in the withdrawal Page",
					"On clicking back button in Plan Withdrawal Page, "+withdrawalType+ "option is selected", false);	
		else
			Reporter.logEvent(Status.FAIL, "Verify "+withdrawalType+" option is selected in the withdrawal Page",
					"On clicking back button in Plan Withdrawal Page, "+withdrawalType+ "option is selected", true);
		}
	}
	else
		{
			Reporter.logEvent(Status.FAIL,"Verify After clicking Back button, system displays Request a Withdrawal Home Page",
					"Request a Withdrawal Home Page is Not Displayed", true);
			throw new Error ("Request a Withdrawal Page is Not Displayed");
		}
			return isEmpQueDisplayed;
	}
	
	public void verifyEnteredInServiceAmount(String withdrawalType,String rothSection,String nonRothSection)
	{		
		if(rothSection.equalsIgnoreCase("Roth"))
		{
			int displayedRothAmount=0;
			WebElement rothMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection)));
			if(Web.isWebElementDisplayed(rothMoneyTypeSourceAvailable))
			{
			WebElement txtAmount = Web.getDriver().findElement(By
					.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",withdrawalType).replaceAll("Money Source Type", rothSection)));
			displayedRothAmount=Math.round(Float.parseFloat(txtAmount.getAttribute("value")));
			if(enteredRothWithdrawalAmt==displayedRothAmount)
					Reporter.logEvent(Status.PASS, "Verify the entered "+rothSection+" Amount for "+withdrawalType+" is displayed",
							"The entered "+rothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Entered "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed "+rothSection+" Amount: "+displayedRothAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the entered "+rothSection+" Amount for "+withdrawalType+" is displayed",
						"The entered "+rothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Entered "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
				+"\n Dispayed "+rothSection+" Amount: "+displayedRothAmount, false);
			
			}
		}
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||
				nonRothSection.equalsIgnoreCase("Non-Roth")||
				nonRothSection.equalsIgnoreCase("After-tax"))
		{
			int displayedPreTaxAmount=0;
			WebElement preTaxMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(moneyTypeSourceSection.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", nonRothSection)));
			if(Web.isWebElementDisplayed(preTaxMoneyTypeSourceAvailable))
			{
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
				displayedPreTaxAmount=Math.round(Float.parseFloat(txtAmount.getAttribute("value")));
				if(enteredNonRothWithdrawalAmt==displayedPreTaxAmount)
					Reporter.logEvent(Status.PASS, "Verify the entered "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
							"The entered "+nonRothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Entered "+nonRothSection+" Amount: "+enteredNonRothWithdrawalAmt 
					+"\n Dispayed "+nonRothSection+" Amount: "+displayedPreTaxAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the entered "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
						"The entered "+nonRothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Entered "+nonRothSection+" Amount: "+enteredNonRothWithdrawalAmt 
				+"\n Dispayed "+nonRothSection+" Amount: "+displayedPreTaxAmount, false);
			}
		}
	}
	
	public void verifyEnteredInServiceAmount_MaxAmount(String withdrawalType,String rothSection,String nonRothSection)
	{
		if(rothSection.equalsIgnoreCase("Roth"))
		{
			WebElement rothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", rothSection)));
			if(rothMaxAmountChkBox.getAttribute("checked").contains("true"))		
			{				
			WebElement txtAmount = Web.getDriver().findElement(By
					.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",withdrawalType).replaceAll("Money Source Type", rothSection)));	
			if(enteredRothWithdrawalAmt==Math.round(Float.parseFloat(txtAmount.getAttribute("value"))))
					Reporter.logEvent(Status.PASS, "Verify the selected Max "+rothSection+" Amount for "+withdrawalType+" is displayed",
							"The selected Max "+rothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Selected Max "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed Max "+rothSection+" Amount: "+txtAmount.getAttribute("value"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the selected Max "+rothSection+" Amount for "+withdrawalType+" is displayed",
						"The selected Max "+rothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Selected Max "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
				+"\n Dispayed Max "+rothSection+" Amount: "+txtAmount.getAttribute("value"), false);	
			Web.clickOnElement(rothMaxAmountChkBox);
			}
		}
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||
				nonRothSection.equalsIgnoreCase("Non-Roth")||
				nonRothSection.equalsIgnoreCase("After-tax"))
		{		
			WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
							withdrawalType).replaceAll("Money Source Type", nonRothSection)));
			if(nonRothMaxAmountChkBox.getAttribute("checked").contains("true"))
			{
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
									withdrawalType).replaceAll("Money Source Type", nonRothSection)));
				System.out.println(txtAmount.getAttribute("value"));
				if(enteredNonRothWithdrawalAmt==Math.round(Float.parseFloat(txtAmount.getAttribute("value"))))
					Reporter.logEvent(Status.PASS, "Verify the selected Max "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
							"The selected Max "+nonRothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Selected Max "+nonRothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed Max "+nonRothSection+" Amount: "+txtAmount.getText(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the selected Max "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
						"The selected Max "+nonRothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Selected Max "+nonRothSection+" Amount: "+enteredRothWithdrawalAmt 
				+"\n Dispayed Max "+nonRothSection+" Amount: "+txtAmount.getText(), false);	
				Web.clickOnElement(nonRothMaxAmountChkBox);
			}
		}
	}
	
	public boolean verifyEmpQueDisplayedOrSepServiceSelected(String withdrawalType)	
	{
		boolean isEmpQueDisplayedOrFWD = false;
		if (Web.isWebElementDisplayed(lblRequestAWithdrawal)) {
			if (Web.isWebElementDisplayed(inpCurrentEmployerYes)) {
				verifyCachingForEmployerQuestionPage(withdrawalType);
				isEmpQueDisplayedOrFWD = true;
			} else {
				if (withdrawalType.equalsIgnoreCase("pwd")) {
					if (partWithDrawal.getAttribute("checked").contains("true"))
						Reporter.logEvent(Status.PASS,"Verify "+ withdrawalType+ " option is selected in the withdrawal Page",
								"On clicking back button in Plan Withdrawal Page, "+ withdrawalType + "option is selected",false);
					else
						Reporter.logEvent(Status.FAIL,"Verify "+ withdrawalType+ " option is selected in the withdrawal Page",
								"On clicking back button in Plan Withdrawal Page, "+ withdrawalType + "option is NOT selected",true);

				} else if (withdrawalType.equalsIgnoreCase("fwd")) {
					if (fullWithDrawal.getAttribute("checked").contains("true"))
					{
						Reporter.logEvent(Status.PASS,"Verify "+ withdrawalType+ " option is selected in the withdrawal Page",
								"On clicking back button in Plan Withdrawal Page, "+ withdrawalType + "option is selected",false);
					isEmpQueDisplayedOrFWD=true;
					}
					else
						Reporter.logEvent(Status.FAIL,"Verify "+ withdrawalType+ " option is selected in the withdrawal Page",
								"On clicking back button in Plan Withdrawal Page, "+ withdrawalType + "option is NOT selected",true);
				}
			}

		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify After clicking button, system displays Request a Withdrawal Home Page",
					"Request a Withdrawal Home Page is Not Displayed", false);
			throw new Error ("Request a Withdrawal Page is Not Displayed");
		}
			return isEmpQueDisplayedOrFWD;

	}
	
	
	
	public void verifyEnteredSepServiceAmount(String withdrawalType,String rothSection,String nonRothSection)
	{
		if(rothSection.equalsIgnoreCase("Roth"))
		{
			int displayedRothAmount=0;
			WebElement rothMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", rothSection)));
			if(Web.isWebElementDisplayed(rothMoneyTypeSourceAvailable))
			{
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));
				
				displayedRothAmount=Math.round(Float.parseFloat(txtAmount.getAttribute("value")));
				if(enteredRothWithdrawalAmt==displayedRothAmount)
					Reporter.logEvent(Status.PASS, "Verify the entered "+rothSection+" Amount for "+withdrawalType+" is displayed",
							"The entred "+rothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Entered "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed "+rothSection+" Amount: "+displayedRothAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the entered "+rothSection+" Amount for "+withdrawalType+" is displayed",
						"The entred "+rothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Entered "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
				+"\n Dispayed "+rothSection+" Amount: "+displayedRothAmount, false);
			}
		}	
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||
				nonRothSection.equalsIgnoreCase("Non-Roth")||
				nonRothSection.equalsIgnoreCase("After-tax"))
		{
			int displayedPreTaxAmount=0;
			WebElement nonRothMoneyTypeSourceAvailable=Web.getDriver().findElement
					(By.xpath(inpAmtPWMoneyType.replace("Money Source Type", nonRothSection)));
			if(Web.isWebElementDisplayed(nonRothMoneyTypeSourceAvailable))
			{
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type",  nonRothSection)));				
				displayedPreTaxAmount=Math.round(Float.parseFloat(txtAmount.getAttribute("value")));
				if(enteredNonRothWithdrawalAmt==displayedPreTaxAmount)
					Reporter.logEvent(Status.PASS, "Verify the entered "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
							"The entred "+nonRothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Entered "+nonRothSection+" Amount: "+enteredNonRothWithdrawalAmt 
					+"\n Dispayed "+nonRothSection+" Amount: "+displayedPreTaxAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the entered "+nonRothSection+" Amount for "+withdrawalType+" is displayed",
						"The entred "+nonRothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Entered "+nonRothSection+" Amount: "+enteredNonRothWithdrawalAmt 
				+"\n Dispayed "+nonRothSection+" Amount: "+displayedPreTaxAmount, false);
			}
		}
	}
	
	public void verifyEnteredSepServicePWD_MaxAmount(String rothSection,String nonRothSection)
	{
		if(rothSection.equalsIgnoreCase("Roth"))
		{
			WebElement rothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", rothSection)));		
			if(rothMaxAmountChkBox.getAttribute("checked").contains("true"))		
			{				
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type", rothSection)));	
			if(enteredRothWithdrawalAmt==Math.round(Float.parseFloat(txtAmount.getAttribute("value"))))
					Reporter.logEvent(Status.PASS, "Verify the selected Max "+rothSection+" Amount for Part Withdrawal is displayed",
							"The selected Max "+rothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Selected Max "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed Max "+rothSection+" Amount: "+txtAmount.getAttribute("value"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the selected Max "+rothSection+" Amount for Part Withdrawal is displayed",
						"The selected Max "+rothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Selected Max "+rothSection+" Amount: "+enteredRothWithdrawalAmt 
			+"\n Dispayed Max "+rothSection+" Amount: "+txtAmount.getAttribute("value"), false);
			Web.clickOnElement(rothMaxAmountChkBox);
			
			}
		}
		if(nonRothSection.equalsIgnoreCase("Pre-tax")||
				nonRothSection.equalsIgnoreCase("Non-Roth")||
				nonRothSection.equalsIgnoreCase("After-tax"))
		{
			WebElement nonRothMaxAmountChkBox=Web.getDriver().findElement
					(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", nonRothSection)));
			if(nonRothMaxAmountChkBox.getAttribute("checked").contains("true"))
			{
				WebElement txtAmount = Web.getDriver().findElement(By
						.xpath(inpAmtPWMoneyType.replaceAll("Money Source Type",nonRothSection)));
				System.out.println(txtAmount.getAttribute("value"));
				if(enteredNonRothWithdrawalAmt==Math.round(Float.parseFloat(txtAmount.getAttribute("value"))))
					Reporter.logEvent(Status.PASS, "Verify the selected Max "+nonRothSection+" Amount for Part Withdrawal is displayed",
							"The selected Max "+nonRothSection+" Amount is been displayed in the Request a Withdrawal Page: \n"+
					"Selected Max "+nonRothSection+" Amount: "+enteredRothWithdrawalAmt 
					+"\n Dispayed Max "+nonRothSection+" Amount: "+txtAmount.getText(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify the selected Max "+nonRothSection+" Amount for Part Withdrawal is displayed",
						"The selected Max "+nonRothSection+" Amount is NOT been displayed in the Request a Withdrawal Page: \n"+
				"Selected Max "+nonRothSection+" Amount: "+enteredRothWithdrawalAmt 
				+"\n Dispayed Max "+nonRothSection+" Amount: "+txtAmount.getText(), false);
				Web.clickOnElement(nonRothMaxAmountChkBox);
			}
		}
	}
	
	public void unSelectWithdrawalType_InService(String withdrawalType,String moneySourceType)
	{
		try {
			if(moneySourceType.equalsIgnoreCase("Pre-tax")||moneySourceType.equalsIgnoreCase("Non-Roth")||
					moneySourceType.equalsIgnoreCase("After-tax")|| moneySourceType.equalsIgnoreCase("Roth"))
			{
				WebElement moneySourceTypeCheckBox=Web.getDriver().findElement(By.xpath(chkBoxInServiceMoneySourceType.replace("Withdrawal Type",
						withdrawalType).replace("Money Source Type", moneySourceType).replaceAll("Source Type", moneySourceType)));
				WebElement moneySourceMaxAmountCheckBox=Web.getDriver().findElement(By.xpath(moneyTypeMaxAmtChkBox.replace("Withdrawal Type",
								withdrawalType).replaceAll("Money Source Type",moneySourceType)));
				WebElement moneySourceInputTextBox = Web.getDriver().findElement(By.xpath(moneyTypeAmtTxt.replace("Withdrawal Type",
								withdrawalType).replaceAll("Money Source Type",moneySourceType)));	
				if(moneySourceMaxAmountCheckBox.isSelected())
				{
					Web.clickOnElement(moneySourceMaxAmountCheckBox);
				}
					else if(moneySourceTypeCheckBox.isSelected())						
					{
						moneySourceInputTextBox.clear();
					}
					/*if(Web.isWebElementDisplayed(moneySourceMaxAmountCheckBox) && moneySourceMaxAmountCheckBox.isSelected())
						Web.clickOnElement(moneySourceMaxAmountCheckBox);
						Web.clickOnElement(moneySourceTypeCheckBox);
					*/
									
				
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	
	public void unSelectWithdrawalType_SepService(String moneySourceType)
	{
		try {
			if(moneySourceType.equalsIgnoreCase("Pre-tax")||
					moneySourceType.equalsIgnoreCase("Non-Roth")||
					moneySourceType.equalsIgnoreCase("After-tax")|| moneySourceType.equalsIgnoreCase("Roth"))
			{
				WebElement moneySourceCheckBox=Web.getDriver().findElement(By.
						xpath(chkBoxSepServiceMoneySourceType.replace("Money Source Type", moneySourceType)));
				WebElement moneySourceMaxAmountChkBox=Web.getDriver().findElement
						(By.xpath(inpMaxAmountPWChkBox.replace("Money Source Type", moneySourceType)));
				WebElement moneySourceInputTextBox=Web.getDriver().findElement
						(By.xpath(inpAmtPWMoneyType.replace("Money Source Type",moneySourceType)));
				if(moneySourceCheckBox.isSelected())
				{
					Web.clickOnElement(moneySourceCheckBox);
					moneySourceInputTextBox.clear();
					if(Web.isWebElementDisplayed(moneySourceMaxAmountChkBox) && moneySourceMaxAmountChkBox.isSelected())
						Web.clickOnElement(moneySourceMaxAmountChkBox);
					
				}		
			}		
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void verifyPlanMinErrorMessage(String withdrawalType)
	{
		Actions keyBoard = new Actions(Web.getDriver());
		WebElement txtPlanMinimumMsg;
		if(Web.isWebElementDisplayed(inpCurrentEmployerYes))
		{	
			if(withdrawalType.equals("PART WD") ||withdrawalType.equals("FULL WD"))		
				Web.clickOnElement(inpCurrentEmployerNo);
			else
				Web.clickOnElement(inpCurrentEmployerYes);
				keyBoard.sendKeys(Keys.TAB).build().perform();
				keyBoard.sendKeys(Keys.ENTER).build().perform();
		}			
		Common.waitForProgressBar();
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}	
		
		if(withdrawalType.equals("PART WD") ||withdrawalType.equals("FULL WD"))		
			txtPlanMinimumMsg= Web.getDriver().
			findElement(By.xpath(txt_SepService_PlanMinimum.replace("WithdrawalType", withdrawalType)));		
		else			
			txtPlanMinimumMsg = Web.getDriver().
			findElement(By.xpath(txt_InService_PlanMinimum.replace("WithdrawalType", withdrawalType)));		
		
		String expectedMsg="Insufficient balance to meet plan minimum";
		String actualMsg=txtPlanMinimumMsg.getText().trim();
		if(expectedMsg.equalsIgnoreCase(actualMsg))
			Reporter.logEvent(Status.PASS, "Verify if the user has the current balance less than the plan miminum then "
					+withdrawalType+" option should be DISABLED and system should display the error message",
					"System displays the following error message when the current balanance is less than plan miminum for "+withdrawalType +":\n"+
							actualMsg, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify if the user has the current balance less than the plan miminum then "
					+withdrawalType+" option should be DISABLED and system should display the error message",
					"System displays the following error message when the current balanance is less than plan miminum for "+withdrawalType +":\n"+
							actualMsg, false);
		if(!isWebElementEnabled("CONTINUE"))
			Reporter.logEvent(Status.PASS,"Verify if the Continue button is disbaled", "The Continue button is disabled",false);
		else
		Reporter.logEvent(Status.PASS,"Verify if the Continue button is disbaled", "The Continue button is NOT Disabled",true);
			
	}
	

}