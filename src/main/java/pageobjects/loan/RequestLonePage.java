package pageobjects.loan;

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

public class RequestLonePage extends LoadableComponent<RequestLonePage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//h1[text()[normalize-space()='Request a loan']]")
	private WebElement lblRequestALoan;
	@FindBy(xpath = ".//*[@id='genPurp']")
	private WebElement inputLonatypeGeneral;
	@FindBy(xpath = ".//*[@id='mortPurp']")
	private WebElement inputLonatypeMortgage;
	@FindBy(id = "loanInitiation")
	private WebElement btnContinue;
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(id = "loan_amount")
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
	@FindBy(id = "CommitLoanRequest")
	private WebElement btnIAccept;
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

	private String loanQuote="//*[contains(text(),'webElementText')]";
	
	/**
	 * Default Constructor
	 */
	public RequestLonePage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestLonePage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
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
			Assert.assertTrue(false,"Logging in with new User");
		}

	}

	@Override
	protected void load() {
		this.parent.get();

		((LeftNavigationBar) this.parent).clickNavigationLink("Request a loan");
		
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
		if (fieldName.trim().equalsIgnoreCase("I ACCEPT")) {
			return this.btnIAccept;
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
		if (fieldName.trim().equalsIgnoreCase("LOAN TYPE GENERAL")) {
			return this.inputLonatypeGeneral;
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
		Web.waitForElement(iFrame);
		if(Web.isWebElementDisplayed(iFrame, true))
		Web.webdriver.switchTo().frame("legacyFeatureIframe");
		
		if (!Web.isWebElementDisplayed(inputLonatypeGeneral)) {
			Web.webdriver.navigate().refresh();
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Web.webdriver.switchTo().defaultContent();
			Web.waitForElement(iFrame);
			if(Web.isWebElementDisplayed(iFrame, true))
			Web.webdriver.switchTo().frame("legacyFeatureIframe");
		}
		if (loanType.equalsIgnoreCase("GENERAL")) {
					
			if (Web.isWebElementDisplayed(inputLonatypeGeneral)) {
				inputLonatypeGeneral.click();
			} else {
				Web.webdriver.switchTo().defaultContent();
				throw new Error("Loan type radio button 'General' is not displayed");
			}
			
		} else if (loanType.equalsIgnoreCase("MORTGAGE")) {
			if (Web.isWebElementDisplayed(inputLonatypeMortgage)) {
				this.inputLonatypeMortgage.click();
			} else {
				Web.webdriver.switchTo().defaultContent();
				throw new Error("Loan type radio button 'Mortgage' is not displayed");
			}
		}

		this.btnContinue.click();
		Web.webdriver.switchTo().defaultContent();
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
			Web.webdriver.switchTo().frame("legacyFeatureIframe");
			Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
			Web.setTextToTextBox(this.inputLoanTerm, loanTerm);
			this.btnCaluculatePaymentInfo.click();
			Web.webdriver.switchTo().defaultContent();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public boolean isTextFieldDisplayed(String fieldName) {
			boolean isTextDisplayed=false;
			 WebElement txtLoanQuote= Web.webdriver.findElement(By.xpath(loanQuote.replace("webElementText", fieldName)));
		
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
}
