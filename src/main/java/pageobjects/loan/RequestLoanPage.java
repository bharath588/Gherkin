package pageobjects.loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
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

public class RequestLoanPage extends LoadableComponent<RequestLoanPage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = "//h1[text()[normalize-space()='Loans']]")
	private WebElement lblRequestALoan;
	@FindBy(xpath = "//button[@id='reqGenPurposeLoan']")
	private WebElement inputLonatypeGeneral;
	@FindBy(id = "reqPrimaryResLoan")
	private WebElement inputLonatypeMortgage;
	@FindBy(xpath = "//button[contains(text(),'Continue')]")
	private WebElement btnContinue;
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(id = "loanQuoteAmount")
	private WebElement inputLoanAmount;
	@FindBy(xpath = ".//input[@name='loan_term']")
	private WebElement inputLoanTerm;
	@FindBy(xpath = ".//input[@value='Calculate Payment Information']")
	private WebElement btnCaluculatePaymentInfo;
	@FindBy(xpath = ".//p[2]//.")
	private WebElement txtLoanType;
	@FindBy(xpath = "//td[@align='left']//table//h2[@class='section508']//strong")
	private WebElement txtLoanRequestEstimate;
	@FindBy(xpath = "//tr[@class='even']//td//font")
	private WebElement txtLoanTerm;
	@FindBy(xpath = "//input[@value='Continue Loan Request']")
	private WebElement btnContinueLoanRequest;
	@FindBy(xpath = "//input[@name='home_area_code']")
	private WebElement inputHomeAreaCode;
	@FindBy(xpath = "//input[@name='home_prefix']")
	private WebElement inputHomePrefix;
	@FindBy(xpath = "//input[@name='home_suffix']")
	private WebElement inputHomeSuffix;
	@FindBy(xpath = "//p[1]/strong[1]")
	private WebElement txtVerifyAllInfo;
	@FindBy(xpath = "//p[1]/strong[2]")
	private WebElement txtOnceYouClick;
	@FindBy(xpath = "//button[contains(text(),'I agree & submit')]")
	private WebElement btnIAgreeAndSubmit;
	@FindBy(id = "checkLoanProvision")
	private WebElement inputIAccept;
	@FindBy(xpath = "//p[1]/span[2]")
	private WebElement txtConfirmation;
	@FindBy(xpath = "html/body/p[1]/span[2]/span")
	private WebElement txtConfirmationNo;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;
	@FindBy(xpath = "//li[@class='ng-scope']//a[text()[normalize-space()='Request a loan']]")
	private WebElement lnkRequestLoan;
	@FindBy(xpath = "//li[@class='ng-scope']//a[text()[normalize-space()='Loans summary']]")
	private WebElement lnkLoanSummary;
	@FindBy(id = "legacyFeatureIframe")
	private WebElement iFrame;
	@FindBy(xpath = "//button[text()[normalize-space()='Request a new loan']]")
	private WebElement btnRequestNewLoan;
	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement btnUpdate;
	@FindBy(id = "proActiveNotificationScreen")
	private WebElement hdrProactiveNotificationScreen;
	@FindBy(id = "loan-total")
	private WebElement txtLoanTotal;
	@FindBy(xpath = "//h1[text()[normalize-space()='Loan review']]")
	private WebElement lblLoanReview;
	@FindBy(id = "currency-maximum")
	private WebElement txtLoanMaximum;
	@FindBy(xpath = "//td[@class='loans-summary-allowed']//span")
	private WebElement txtMaximumNoOfLoans;
	
	private String loanQuote="//*[contains(text(),'webElementText')]";
	private String loanTerm="//table[@id='quoteTable']//tr[./td//span[contains(text(),'Repayment Term')]]//input";
	/**
	 * Default Constructor
	 */
	public RequestLoanPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestLoanPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestALoan, true),"Request A Loan Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo=null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		else{
		
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
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblRequestALoan),"Requuest Loan page is not loadeded\n");
		} else {
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}

	}

	@Override
	protected void load() {
		this.parent.get();

		((LeftNavigationBar) this.parent).clickNavigationLink("Request a loan");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		lib.Web.isWebElementDisplayed(lblRequestALoan,true);
		

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
		if (fieldName.trim().equalsIgnoreCase("REQUEST A LOAN")) {
			return this.lblRequestALoan;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT LOAN TYPE")) {
			return this.txtLoanType;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT LOAN REQUEST ESTIMATE")) {
			return this.txtLoanRequestEstimate;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT LOAN TERM")) {
			return this.txtLoanTerm;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE LOAN REQUEST")) {
			return this.btnContinueLoanRequest;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT HOME AREA CODE")) {
			return this.inputHomeAreaCode;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT HOME PREFIX")) {
			return this.inputHomePrefix;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT HOME SUFFIX")) {
			return this.inputHomeSuffix;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT VERIFY ALL INFO")) {
			return this.txtVerifyAllInfo;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT ONCE YOU CLICK")) {
			return this.txtOnceYouClick;
		}
		if (fieldName.trim().equalsIgnoreCase("I AGREE AND SUBMIT")) {
			return this.btnIAgreeAndSubmit;
		}
		if (fieldName.trim().equalsIgnoreCase("CHECKBOX I ACCEPT")) {
			return this.inputIAccept;
		}
		if (fieldName.trim().equalsIgnoreCase("Text Confirmation")) {
			return this.txtConfirmation;
		}
		if (fieldName.trim().equalsIgnoreCase("Text Confirmation Number")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Request A New Loan")) {
			return this.btnRequestNewLoan;
		}
		if (fieldName.trim().equalsIgnoreCase("LOAN TYPE GENERAL")) {
			return this.inputLonatypeGeneral;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Update")) {
			return this.btnUpdate;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("ProActive Notification Screen")) {
			return this.hdrProactiveNotificationScreen;
		}
		if (fieldName.trim().equalsIgnoreCase("Header Loan Review")) {
			return this.lblLoanReview;
		}
		if (fieldName.trim().equalsIgnoreCase("LOAN TOTAL")) {
			return this.txtLoanTotal;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * Method to Select Loan Type and Click Continue
	 * 
	 * @param loanType
	 * 
	 */
	public void selectLoneType(String loanType) {
	
		if (loanType.equalsIgnoreCase("GENERAL")) {
					
			if (Web.isWebElementDisplayed(inputLonatypeGeneral,true)) {
				inputLonatypeGeneral.click();
			} else {
				
				throw new Error("Loan type radio button 'General' is not displayed");
			}
			
		} else if (loanType.equalsIgnoreCase("MORTGAGE")) {
			if (Web.isWebElementDisplayed(inputLonatypeMortgage, true)) {
				this.inputLonatypeMortgage.click();
			} else {
				
				throw new Error("Loan type radio button 'Mortgage' is not displayed");
			}
		}

		//this.btnContinue.click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
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
	
		/**
		 * Method to Enter Loan Amount,Loan Term and Click on Calculate Payment Information
		 * 
		 * @param loanAmount
		 * @param loanTerm
		 * 
		 */
		public void EnterLoanAmtAndTerm(String loanAmount,String loanTerm) {
		
			Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
			Web.clickOnElement(btnContinue);
			Common.waitForProgressBar();
			 WebElement inpLoanTerm= Web.getDriver().findElement(By.xpath(this.loanTerm.replace("Repayment Term", loanTerm)));
			Web.clickOnElement(inpLoanTerm);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public boolean isTextFieldDisplayed(String fieldName) {
			boolean isTextDisplayed=false;
			 WebElement txtLoanQuote= Web.getDriver().findElement(By.xpath(loanQuote.replace("webElementText", fieldName)));
		
			isTextDisplayed = Web.isWebElementDisplayed(txtLoanQuote, true);
			if (isTextDisplayed) {
				lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
						+ "  is Displayed", fieldName + " is Displayed",
						false);

			} else {
				lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
						+ " is Displayed",
						fieldName + " is Not Displayed", false);
			}
		
return isTextDisplayed;
		}
		
		/**
		 * <pre>
		 * Method to Verify the Page Header is Displayed or Not
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyPageHeaderIsDisplayed(String fieldName) {
			
			WebElement webelement= getWebElement(fieldName);
		 String pageHeader=webelement.getText().toString().trim();
			if (Web.isWebElementDisplayed(webelement)) {
				lib.Reporter.logEvent(Status.PASS, "Verify "+pageHeader 
						+ " Page  is Displayed", pageHeader 
						+ " Page  is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify "+pageHeader 
						+ " Page  is Displayed", pageHeader 
						+ " Page  is Not Displayed",true);
				throw new Error(fieldName+" is not displayed");
			}
		

		}
		
		/**
		 * Method to get Maximum Loan Amount
		 * 
		 * @return loanAmount
		 */
		public String getMaximumLoanAmount() {
		 String loanAmount=" ";
			Web.waitForElement(txtLoanMaximum);
			try{
				if(txtLoanMaximum.isDisplayed())
					loanAmount=txtLoanMaximum.getText().trim().toString();
					
			} catch (NoSuchElementException e) {
				
				lib.Reporter.logEvent(Status.FAIL, "get Loan Maximum Amount",  
						 "Loan Maximum Amount is Not Displayed",true);
			}
			return loanAmount;
		}
		/**
		 * Method to get Maximum No.Of Loans Allowed
		 * 
		 * @return maximumNoOfLoans
		 */
		public String getMaximumLoansAllowed() {
		 String maximumNoOfLoans=" ";
			Web.waitForElement(txtMaximumNoOfLoans);
			try{
				if(txtMaximumNoOfLoans.isDisplayed())
					maximumNoOfLoans=txtMaximumNoOfLoans.getText().split("Up to")[1].trim().toString();
					
			} catch (NoSuchElementException e) {
				
				lib.Reporter.logEvent(Status.FAIL, "get Maximum No Of Loans Allowed",  
						 " Maximum No Of Loans Allowed is Not Displayed",true);
			}
			return maximumNoOfLoans;
		}
}
