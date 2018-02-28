package pageobjects.loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import oracle.sql.ARRAY;

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
	@FindBy(xpath = "//button[contains(text(),'Back')]")
	private WebElement btnBack;
	@FindBy(xpath = "//a[contains(text(),'OK')]")
	private WebElement btnOK;
	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement btnupdate;
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
	@FindBy(xpath = "//td[@data-header='LOAN TOTAL']")
	private WebElement txtLoanTotal;
	@FindBy(xpath = "//h1[text()[normalize-space()='Loan review']]")
	private WebElement lblLoanReview;
	@FindBy(xpath = "//h1[text()[normalize-space()='How much would you like to borrow?']]")
	private WebElement hdrLoanPage;
	@FindBy(id = "currency-maximum")
	private WebElement txtLoanMaximum;
	@FindBy(xpath = "//td[@class='loans-summary-allowed']//span")
	private WebElement txtMaximumNoOfLoans;
	@FindBy(xpath = "//span[contains(@ng-if,'GenPurLoans')]//li")
	private WebElement txtMaximumNoOfLoansGeneral;
	@FindBy(xpath = "//span[contains(@ng-if,'PrimaryResLoans')]//li")
	private WebElement txtMaximumNoOfLoansPrincipal;
	
	@FindBy(xpath = "//div[@id='loanRequestMarketingInfoWrapper']//div[@class='loan-top-margin']/p")
	private WebElement txtloanDisclaimer1;
	@FindBy(xpath = "//div[@id='loanRequestMarketingInfoWrapper']//div[@class='loan-top-margin']/ul/li[1]")
	private WebElement txtloanDisclaimer2;
	@FindBy(xpath = "//div[@id='loanRequestMarketingInfoWrapper']//div[@class='loan-top-margin']/ul/li[2]")
	private WebElement txtloanDisclaimer3;
	@FindBy(xpath = "//a[text()[normalize-space()='How is this calculated?']]")
	private WebElement lnkHowIsThisCalculated;
	
	@FindBy(xpath = "//div[@id='min-loan-amount']/p")
	private WebElement txtLoanMinimum;
	
	@FindBy(xpath = "//div[@class='error-block']")
	private WebElement txtErrorMsg;
	@FindBy(id = "quoteTable")
	private WebElement tableRepaymentTerm;
	@FindBy(xpath = "//a[contains(text(),'enter your own term')]")
	private WebElement lnkEnterOwnTerm;
	@FindBy(id = "emailId")
	private WebElement inpEmail;
	@FindBy(id = "phoneNumberId")
	private WebElement inpPhoneNo;
	@FindBy(xpath = "//a[contains(text(),'loan provisions')]")
	private WebElement lnkLoanProvision;
	@FindBy(xpath = "//div[@class='modal-title' and text()=' Loan provisions']")
	private WebElement modalHeaderLoanProvision;
	@FindBy(id = "txn-ofc-comply")
	private WebElement loanAckWindow;
	@FindBy(id = "first-class-mail-radio-selection") private WebElement inpRegularMail;
	@FindBy(id = "express-mail-radio-selection") private WebElement inpExpressMail;
	@FindBy(id = "ach-delivery-radio-selection") private WebElement inpACHDelivery;
	
	private String loanQuote="//*[contains(text(),'webElementText')]";
	
	private String strmaxloan="//span[contains(@ng-if,'LoanTypeLoans')]//li";
	private String strRepaymentTerm="//table[@id='quoteTable']/tbody/tr[rownum]/td//span";
	
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
		if (fieldName.trim().equalsIgnoreCase("BUTTON UPDATE")) {
			return this.btnUpdate;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON BACK")) {
			return this.btnBack;
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
		if (fieldName.trim().equalsIgnoreCase("GENERAL PURPOSE")) {
			return this.inputLonatypeGeneral;
		}
		if (fieldName.trim().equalsIgnoreCase("MORTAGAGE")) {
			return this.inputLonatypeMortgage;
		}
		if (fieldName.trim().equalsIgnoreCase("Link How Is This Calculated")) {
			return this.lnkHowIsThisCalculated;
		}
		if (fieldName.trim().equalsIgnoreCase("Repayment Term Table")) {
			return this.tableRepaymentTerm;
		}
		if (fieldName.trim().equalsIgnoreCase("Link Enter Your Own Term")) {
			return this.lnkEnterOwnTerm;
		}
		if (fieldName.trim().equalsIgnoreCase("EmailId Input Field")) {
			return this.inpEmail;
		}
		if (fieldName.trim().equalsIgnoreCase("Phone No Input Field")) {
			return this.inpPhoneNo;
		}
		if (fieldName.trim().equalsIgnoreCase("LINK LOAN PROVISION")) {
			return this.lnkLoanProvision;
		}
		if (fieldName.trim().equalsIgnoreCase("MODAL CONTENT LOAN PROVISIONS")) {
			return this.modalHeaderLoanProvision;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON OK")) {
			return this.btnOK;
		}
		if (fieldName.trim().equalsIgnoreCase("LOAN ACKNOWLEDGEMENT WINDOW")) {
			return this.loanAckWindow;
		}
		if (fieldName.trim().equalsIgnoreCase("REGULAR MAIL RADIO BUTTON")) {
			return this.inpRegularMail;
		}
		if (fieldName.trim().equalsIgnoreCase("EXPEDITED MAIL RADIO BUTTON")) {
			return this.inpExpressMail;
		}
		if (fieldName.trim().equalsIgnoreCase("ACH DELIVERY RADIO BUTTON")) {
			return this.inpACHDelivery;
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

		Web.waitForElement(hdrLoanPage);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Method to Verify Request Loan Button is Displayed
	 * 
	 * @param fieldName
	 * 
	 */
	public void verifyLoneTypeisDisplayed(String fieldName) {
	
			WebElement element=	getWebElement(fieldName);
					
			if (element.isDisplayed()) {
				lib.Reporter.logEvent(Status.PASS, "Verify Reqest Loan Buttton is Displayed",  
						"Reqest"+fieldName+"Buttton is Displayed",false);
			} else {
				lib.Reporter.logEvent(Status.FAIL, "Verify Reqest Loan Buttton is Displayed",  
						"Reqest"+fieldName+"Buttton is not Displayed",true);
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
		
		/**
		 * Method to Select Loan Term
		* @param loanTerm
		 * 
		 */
		public void selectLoanTerm(String loanTerm) {
		
			Common.waitForProgressBar();
			 WebElement inpLoanTerm= Web.getDriver().findElement(By.xpath(this.loanTerm.replace("Repayment Term", loanTerm)));
			 Web.clickOnElement(inpLoanTerm);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Method to Enter Loan Amount
		 * 
		 * @param loanAmount
		 * 
		 */
		public void EnterLoanAmount(String loanAmount) {
		
			Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
		
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
		 * Method to get Maximum No.Of Loans Allowed from Application
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
		
		/**
		 * Method to get Maximum Loans Allowed for the plan
		 * @param ga_Id 150049
		* @return maxLoansAllowed
		 * @throws SQLException
		 */
		public  String getMaximumLoansAllowedforPlan(String ga_Id) throws SQLException {

			// query to get the no of Loans
			String[] sqlQuery = null;
			ResultSet loans= null;
			String maxLoansAllowed="";

			try {
				sqlQuery = Stock.getTestQuery("getMaxLoansAllowed");
			} catch (Exception e) {
				e.printStackTrace();
			}

			loans = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
	try{
			if (DB.getRecordSetCount(loans) > 0) {
				loans.first();
	  			maxLoansAllowed= loans.getString("max_loans_allowed");
				} 
		}catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query get PlanName:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
	  			
			return maxLoansAllowed;
		}
		/**
		 * Method to get Maximum Loans Allowed for the plan
		 * @param ga_Id 150049
		* @return 
		 * @throws SQLException
		 */
		public  Map<String,String> getMaximumLoansAllowedforLoanReason(String ga_Id) throws SQLException {

			// query to get the no of Loans
			String[] sqlQuery = null;
			ResultSet loans= null;
			 Map<String, String> mapMaxLoansAllowed = new HashMap<String, String>();

			try {
				sqlQuery = Stock.getTestQuery("getMaxLoansAllowedforLoanReason");
			} catch (Exception e) {
				e.printStackTrace();
			}

			loans = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
	try{
			if (DB.getRecordSetCount(loans) > 0) {
				
				while(loans.next()){
					mapMaxLoansAllowed.put(loans.getString("loan_reason_code"), loans.getString("max_loans_allowed"));
				} 
			}
		}catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query get PlanName:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
	  			
			return mapMaxLoansAllowed;
		}
		/**
		 * <pre>
		 * Method to Verify the No.of Loans Displayed for Loan Reason Code is Same or Not
		 *
		 * </pre>
		 * @throws SQLException 
		 * 
		 *
		 */
		public void verifyMaximumLoansForLoan(String ga_id) throws SQLException {
			WebElement txtLoanQuote = null;
			Map<String,String> maxloansActual;
			Map<String,String> maxloansExpected = new HashMap<String,String>();
			maxloansActual=getMaximumLoansAllowedforLoanReason(ga_id);
			Set<String> keys = maxloansActual.keySet();
		 	try{
			for(String key: keys){
	          if(key.equalsIgnoreCase("GENERAL")){
	        	  txtLoanQuote= Web.getDriver().findElement(By.xpath(strmaxloan.replace("LoanType", "GenPur")));
	          }
	          else if(key.equalsIgnoreCase("MORTGAGE")){
	        	  txtLoanQuote= Web.getDriver().findElement(By.xpath(strmaxloan.replace("LoanType", "PrimaryRes")));
	          }
	          maxloansExpected.put(key, txtLoanQuote.getText());
	          
		 	}
		 	}catch(NoSuchElementException e){
		 		e.printStackTrace();
		 	}
		 	if(maxloansExpected.equals(maxloansActual))Reporter.logEvent(Status.PASS,
					"Verify  Maximum Loan Amount for each Loan Structure is displayed",
					"Maximum Loan Amount for each Loan Structure is Matching "+maxloansActual, true);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify  Maximum Loan Amount for each Loan Structure is displayed",
					"Maximum Loan Amount for  Loan Structure is not Matching\nExpected:"+maxloansExpected+"\nActual:"+maxloansActual, true);
	
		}
		
		/**
		 * <pre>
		 * Method to Verify Loans Disclaimer
		 *
		 * </pre>
		 */
		public void verifyLoansDisclaimer() throws SQLException {

		if (txtloanDisclaimer1.getText().toString().trim()
				.equals(Stock.GetParameterValue("Disclaimer1")))
			Reporter.logEvent(Status.PASS,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is displayed"
							+ txtloanDisclaimer1.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected:"
							+ Stock.GetParameterValue("Disclaimer1")
							+ "\nActual:"
							+ txtloanDisclaimer1.getText().toString().trim(),
					true);

		if (txtloanDisclaimer2.getText().toString().trim()
				.equals(Stock.GetParameterValue("Disclaimer2")))
			Reporter.logEvent(Status.PASS,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is displayed"
							+ txtloanDisclaimer2.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected:"
							+ Stock.GetParameterValue("Disclaimer2")
							+ "\nActual:"
							+ txtloanDisclaimer2.getText().toString().trim(),
					true);

		if (txtloanDisclaimer3.getText().toString().trim()
				.equals(Stock.GetParameterValue("Disclaimer3")))
			Reporter.logEvent(Status.PASS,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is displayed"
							+ txtloanDisclaimer3.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected:"
							+ Stock.GetParameterValue("Disclaimer3")
							+ "\nActual:"
							+ txtloanDisclaimer3.getText().toString().trim(),
					true);

	}
		
		/**
		 * <pre>
		 * Method to Verify theLoan Minimum Amount is Displayed or Not
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyLoanMinimumIsDisplayed() {
			
		
			if (Web.isWebElementDisplayed(txtLoanMinimum)) {
				lib.Reporter.logEvent(Status.PASS, "Verify Minimum Loan Amount is Displayed" 
						, "Minimum Loan Amount is Displayed"+txtLoanMinimum.getText().toString().trim(),
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify Minimum Loan Amount is Displayed" 
						, "Minimum Loan Amount is Displayed"+txtLoanMinimum.getText().toString().trim(),
						true);
				
			}
		

		}
		/**
		 * <pre>
		 * Method to Verify WebElement is Displayed or Not
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyWebElementIsDisplayed(String fieldName) {
			
		WebElement element = getWebElement(fieldName);
			if (Web.isWebElementDisplayed(element)) {
				lib.Reporter.logEvent(Status.PASS, "Verify "+fieldName 
						+ " is Displayed", fieldName 
						+ " is Displayed",
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify "+fieldName 
						+ " is Displayed", fieldName 
						+ " is Not Displayed",
						true);
				throw new Error(fieldName+" is not displayed");
			}
		

		}
		/**
		 * <pre>
		 * Method to Verify MinimumLoan Amount Error Message is Displayed
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyLoanMinimumErrorMessageIsDisplayed(String loanAmount) {
			
			Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
			if (Web.isWebElementDisplayed(txtErrorMsg,true)) {
				lib.Reporter.logEvent(Status.PASS, "Verify Minimum Loan Amount Error Message is Displayed" 
						, "Minimum Loan Amount Error Message is Displayed\n"+txtErrorMsg.getText().toString().trim(),
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify Minimum Loan Amount Error Message is Displayed" 
						, "Minimum Loan Amount Error Message is not Displayed\n"+txtErrorMsg.getText().toString().trim(),
						true);
				
			}
			if(!btnContinue.isEnabled()){
				lib.Reporter.logEvent(Status.PASS, "Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed" 
						, "Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed" 
								, "Continue Button is Not Disabled when Minimum Loan Amount Error Message is Displayed",
								true);

				
			
			}
		

		}
		
		/**
		 * <pre>
		 * Method to Click On Continue Button
		 * </pre>
		 *
		 */

		public void clickContinueButton() {
			boolean isTextDisplayed = false;
			try {

				isTextDisplayed = Web.isWebElementDisplayed(btnContinue, true);

				if (isTextDisplayed)
					Web.clickOnElement(btnContinue);

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}

		}
		
		/**
		 * <pre>
		 * Method Click on Back Button
		 * </pre>
		 * 
		 */
		public void clickOnBackButton() {
			try {
				Web.clickOnElement(btnBack);
			} catch (NoSuchElementException e) {
				throw new Error("Back Button is not displayed");

			}
		}
		
		/**
		 * <pre>
		 * Method to Verify Default Repayment Term Options
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyDefaultRepamentTerm() {
			String[] repaymentTerm={"12months","24months","36months","48months","60months"};
			for(int i=1;i<=repaymentTerm.length;i++){
				List<WebElement> inpLoanTerm= Web.getDriver().findElements(By.xpath(strRepaymentTerm.replace("rownum", Integer.toString(i))));
				int j=inpLoanTerm.size();
			String actualLoanTerm=inpLoanTerm.get(0).getText().toString().trim()+inpLoanTerm.get(1).getText().toString().trim();
		
			if(repaymentTerm[i].equalsIgnoreCase(actualLoanTerm)){
				lib.Reporter.logEvent(Status.PASS, "Verify Repayment Term is Displayed in Ascending Order" 
						, "Repayment Term "+i+actualLoanTerm,
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify Repayment Term is Displayed in Ascending Order" 
						, "Repayment Term is not displayed as expected\nExpected:"+repaymentTerm[i]+"\nActual:"+actualLoanTerm,
						true);
				
			}
		}
		}
		/**
		 * <pre>
		 * Method to Verify Loan Provision Link and Modal Pop Up
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifyLoanProvisionLink() {
			
			verifyWebElementIsDisplayed("LINK LOAN PROVISION");
			Web.clickOnElement(lnkLoanProvision);
			Web.waitForElement(modalHeaderLoanProvision);
			verifyWebElementIsDisplayed("MODAL CONTENT LOAN PROVISIONS");
			verifyWebElementIsDisplayed("BUTTON OK");
			Web.clickOnElement(btnOK);
			verifyPageHeaderIsDisplayed("Header Loan Review");
		
		}
		
		/**
		 * <pre>
		 * Method to Verify Each Loan Term Have a Radio Button associated with it
		 *
		 * </pre>
		 * 
		 *
		 */
		public void verifySelectColumnForLoanTerm() {
			String[] repaymentTerm={"12","24","36","48","60"};
			for(int i=1;i<=repaymentTerm.length;i++){
				 WebElement inpLoanTerm= Web.getDriver().findElement(By.xpath(this.loanTerm.replace("Repayment Term", repaymentTerm[i])));
				
			if(inpLoanTerm.isDisplayed()){
				lib.Reporter.logEvent(Status.PASS, "Verify Radio Button is Displayed for Loan Term" 
						, "Radio Button is Displayed for Loan Term "+repaymentTerm[i]+" Months",
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,"Verify Radio Button is Displayed for Loan Term" 
						, "Radio Button is Not Displayed for Loan Term "+repaymentTerm[i]+" Months",
						true);

				
			}
		}
		}
		
		/**
		 * <pre>
		 * Method to Verify Mail Delivery Options Available
		 *
		 * </pre>
		 *
		 */
		public void verifyMailDeliveryOptions() {
			verifyWebElementIsDisplayed("REGULAR MAIL RADIO BUTTON");
			verifyWebElementIsDisplayed("EXPEDITED MAIL RADIO BUTTON");
			verifyWebElementIsDisplayed("ACH DELIVERY RADIO BUTTON");
			
				if(inpRegularMail.isDisplayed()){
				lib.Reporter.logEvent(Status.PASS, "Verify Radio Button is Displayed for Loan Term" 
						, "Radio Button is Displayed for Loan Term ",
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,"Verify Radio Button is Displayed for Loan Term" 
						, "Radio Button is Not Displayed for Loan Term ",
						true);

				
			}
		}
		
}
