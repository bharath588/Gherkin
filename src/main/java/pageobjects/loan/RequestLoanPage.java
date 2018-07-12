package pageobjects.loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;

import org.apache.xalan.lib.sql.SQLQueryParser;

import org.openqa.selenium.By;
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

import com.aventstack.extentreports.Status;
import com.mongodb.connection.QueryResult;

import core.framework.Globals;

public class RequestLoanPage extends LoadableComponent<RequestLoanPage> {

	// Declarations
	private LoadableComponent<?> parent;
//	private static boolean waitforLoad = false;
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
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
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
	/*
	 * @FindBy(xpath = ".//p[2]//.") private WebElement txtLoanType;
	 */
	@FindBy(xpath = "//td[@align='left']//table//h2[@class='section508']//strong")
	private WebElement txtLoanRequestEstimate;
	/*
	 * @FindBy(xpath = "//tr[@class='even']//td//font") private WebElement
	 * txtLoanTerm;
	 */
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
	@FindBy(id = "first-class-mail-radio-selection")
	private WebElement inpRegularMail;
	@FindBy(id = "express-mail-radio-selection")
	private WebElement inpExpressMail;
	@FindBy(id = "ach-delivery-radio-selection")
	private WebElement inpACHDelivery;
	@FindBy(xpath = "//span[./strong[contains(text(),'Regular mail')]]//following-sibling::span")
	private WebElement txtRegularMailDeliveryTime;
	@FindBy(xpath = "//span[./strong[contains(text(),'Expedited mail')]]//following-sibling::span")
	private WebElement txtExpeditedMailDeliveryTime;
	@FindBy(xpath = "//span[./strong[contains(text(),'ACH')]]//following-sibling::span")
	private WebElement txtACHMailDeliveryTime;
	@FindBy(xpath = "//tr[./td[contains(text(),'Interest rate')]]//td[2]//span")
	private WebElement txtGeneralPurposeIntrestRate;
	@FindBy(xpath = "//tr[./td[contains(text(),'Interest rate')]]//td[3]//span")
	private WebElement txtMortgageIntrestRate;
	@FindBy(xpath = "//td[@data-header='INTEREST RATE']/a")
	private WebElement txtIntrestRateLoanSummary;
	@FindBy(xpath = "//td[@data-header='FEES*']/a")
	private WebElement txtOriginationFee;
	@FindBy(xpath = "//td[@data-header='CHECK TOTAL']")
	private WebElement txtCheckTotal;
	@FindBy(xpath = "//h1[text()[normalize-space()='Loan request received']]")
	private WebElement hdrLoanConfirmationPage;
	@FindBy(xpath = "//div[@class='page-title'][./h1[contains(text(),'Loan request received')]]//following-sibling::div[1]")
	private WebElement txtconfirmationdisclaimer;
	@FindBy(xpath = "//div[contains(text(),'Confirmation number')]")
	private WebElement txtConfirmationNumber;
	@FindBy(xpath = "//div[./div/div[contains(text(),'Confirmation number')]]//following-sibling::div[contains(@id,'desktop-complete-text')]")
	private WebElement txtComplte;
	@FindBy(xpath = "//div[contains(@id,'desktop-complete-text')]//span[@class='em-check-bold']")
	private WebElement iconGreenCheck;
	@FindBy(xpath = "//div[./b[contains(text(),'PLAN')]]//following-sibling::div")
	private WebElement txtPlanName;
	@FindBy(xpath = "//div[./b[contains(text(),'LOAN TYPE')]]//following-sibling::div")
	private WebElement txtLoanType;
	@FindBy(xpath = "//div[./b[contains(text(),'TERM')]]//following-sibling::div")
	private WebElement txtLoanTerm;
	@FindBy(xpath = "//div[./b[contains(text(),'MATURITY DATE')]]//following-sibling::div")
	private WebElement txtMaturityDate;
	@FindBy(xpath = "//div[./b[contains(text(),'INTEREST RATE')]]//following-sibling::div")
	private WebElement txtInterestRate;
	@FindBy(xpath = "//div[./b[contains(text(),'APR')]]//following-sibling::div")
	private WebElement txtAPR;
	@FindBy(xpath = "//div[./b[contains(text(),'CHECK AMOUNT')]]//following-sibling::div")
	private WebElement txtCheckAmount;
	@FindBy(xpath = "//div[./b[contains(text(),'LOAN AMOUNT')]]//following-sibling::div")
	private WebElement txtLoanAmount;
	@FindBy(xpath = "//div[./b[contains(text(),'TOTAL INTEREST AMOUNT')]]//following-sibling::div")
	private WebElement txtInterestAmount;
	@FindBy(xpath = "//div[./b[contains(text(),'TOTAL PRINCIPAL AND INTEREST AMOUNT')]]//following-sibling::div")
	private WebElement txtTotalPrincipalAndInterestAmount;
	@FindBy(xpath = "//div[./b[contains(text(),'FIRST PAYMENT DUE')]]//following-sibling::div")
	private WebElement txtFirstPaymentDue;
	@FindBy(xpath = "//div[./b[contains(text(),'LAST PAYMENT DUE')]]//following-sibling::div")
	private WebElement txtLastPaymentDue;
	@FindBy(xpath = "//div[./b[contains(text(),'NUMBER OF PAYMENTS')]]//following-sibling::div")
	private WebElement txtNumberOfPayments;
	@FindBy(xpath = "//div[./b[contains(text(),'PAYMENT AMOUNT')]]//following-sibling::div")
	private WebElement txtPaymentAmount;
	@FindBy(xpath = "//div[./b[contains(text(),'PAYMENT METHOD')]]//following-sibling::div")
	private WebElement txtPaymentMethod;
	@FindBy(xpath = "//div[./b[contains(text(),'PAYMENT FREQUENCY')]]//following-sibling::div")
	private WebElement txtPaymentFrequency;
	
	@FindBy(xpath = "//div[./b[contains(text(),'ORIGINATION FEE')]]//following-sibling::div")
	private WebElement txtOriginationFeeInConfirmationPage;
	@FindBy(xpath = "//div[./b[contains(text(),'DELIVERY METHOD')]]//following-sibling::div")
	private WebElement txtDeliveryMethodInConfirmationPage;
	
	@FindBy(linkText = "Print")
	private WebElement lnkPrint;
	@FindBy(id = "ach-account-selection")
	private WebElement drpACHAcoount;
	@FindBy(xpath = "//div[@class='alert alert-warning']/p[1]")
	private WebElement txtWar1Addresschange;
	@FindBy(xpath = "//div[@class='alert alert-warning']/p[2]")
	private WebElement txtWar2Addresschange;
	@FindBy(id = "futureDate")
	private WebElement inpProcessLoanAfterHold;
	@FindBy(xpath = "//div[./input[@id='futureDate']]")
	private WebElement txtProcessLoanAfterHold;
	@FindBy(id = "prefilled")
	private WebElement inpEmailForm;
	@FindBy(xpath = "//div[./input[@id='prefilled']]")
	private WebElement txtEmailForm;
	@FindBy(xpath = "//div[@class='principal-residence-requirement-container']/p[1]")
	private WebElement txtPrincipalloanDisclaimer1;
	@FindBy(xpath = "//div[@class='principal-residence-requirement-container']/p[2]")
	private WebElement txtPrincipalloanDisclaimer2;
	@FindBy(xpath = "//div[@class='principal-residence-requirement-container']//li//span")
	private WebElement txtPrincipalloanDisclaimer3;
	@FindBy(xpath = "//div[./strong[contains(text(),'Request form received')]]/following-sibling::div")
	private WebElement txtBeingReviewed;
	@FindBy(xpath = "//div[./strong[contains(text(),'Form review complete')]]/following-sibling::div[1]")
	private WebElement txtLoanReqInProcess;
	@FindBy(xpath = "//div[./strong[contains(text(),'Form review complete')]]/following-sibling::div[2]")
	private WebElement txtTypically2BDays;
	@FindBy(xpath = "//div[./strong/span[contains(text(),'Check sent by Regular mail')]]/following-sibling::div/span")
	private WebElement txtDeliveryUpTo5BDays;
@FindBy(xpath = "//*[@id='loanReasons']/div[2]/div/p[1]")
	private WebElement txtLoanReasons;
	@FindBy(id = "prefilled")
	private WebElement btnLoanReasonsPreFilledForm;
	@FindBy(id = "futureDate")
	private WebElement btnLoanReasonsFutureDate;
	@FindBy(xpath = ".//*[@id='quoteTable']/tbody/tr/td[1]/a")
	private WebElement mortgageInterestRateLoanSummaryPage;
	@FindBy(xpath = ".//*[@id='quoteTable']/tbody/tr/td[2]/a")
	private WebElement mortgageFeesLoanSummaryPage;
	@FindBy(xpath = ".//*[@id='loanReasons']/div[contains(@ng-if,'showErrors()')]")
	private WebElement txtPendingTransactionError;
	
	
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/*[1]/*")
	private WebElement toolTipInterestRateText1;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/*[2]/div[1]/*")
	private WebElement toolTipInterestRateText2;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/*[2]/div[2]/*")
	private WebElement toolTipInterestRateText3;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/*[3]/div[1]/*")
	private WebElement toolTipInterestRateText4;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/*[3]/div[2]/*")
	private WebElement toolTipInterestRateText5;
	
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/div[1]/div[1]/span")
	private WebElement toolTipFeeDeliveryText;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/div[1]/div[2]/*")
	private WebElement toolTipFeeDeliveryValue;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/div[2]/div[1]/span")
	private WebElement toolTipFeeOriginationText;
	@FindBy(xpath = "//div[@class='top small-popover fade tooltip in']/div[2]/*/*/div[1]/div[2]/*")
	private WebElement toolTipFeeOriginationValue;

	@FindBy(xpath = ".//*[@id='emailContainer']//input[@name='updateThruEmail']")
	private WebElement chkBoxEmail;
	@FindBy(xpath = ".//*[@id='phoneNbrContainer']//input[@name='updateThruTextMsg']")
	private WebElement chkBoxTextMsg;
	
	@FindBy(xpath = "//*[@id='account-details-container']/div/div/div[2]/loan-summary/div/div/div[4]/div[2]/div/div[2]/div[2]")
	private WebElement mailingAddressValue;
	
	@FindBy(xpath = "//p[@class='addr-holding-error']") private WebElement errAddressHold;

	@FindBy(xpath = "//p[@class='defaulted-addr-error']") private WebElement errDefaultedAddress;
	@FindBy(xpath = "//span[contains(text(),'BR_479')]/..") private WebElement errBR_479;
	@FindBy(xpath = "//span[contains(text(),'BR_480')]/..") private WebElement errBR_480;
	@FindBy(xpath = "//span[contains(text(),'BR_481')]/..") private WebElement errBR_481;
	@FindBy(xpath = "//span[contains(text(),'BR_484')]/..") private WebElement errBR_484;
	@FindBy(xpath = "//span[contains(text(),'BR_486')]/..") private WebElement errBR_486;
	@FindBy(xpath = "//span[contains(text(),'BR_467')]/..") private WebElement errBR_467;
	@FindBy(xpath = "//span[contains(text(),'BR_468')]/..") private WebElement errBR_468;
	@FindBy(xpath = "//span[contains(text(),'BR_478')]/..") private WebElement errBR_478;
	@FindBy(xpath = "//span[contains(text(),'BR_510')]/..") private WebElement errBR_510;
	@FindBy(xpath = "//div[@class='modal-content'][./div[@id='rulesViolationsModal']]") private WebElement modalBR_510;
	@FindBy(xpath = "//span[contains(text(),'BR_473')]/..") private WebElement errBR_473;
	@FindBy(xpath = "//span[contains(text(),'BR_488')]/..") private WebElement errBR_488;
	@FindBy(xpath = "//span[contains(text(),'BR_485')]/..") private WebElement errBR_485;
	@FindBy(xpath = "//span[contains(text(),'BR_474')]/..") private WebElement errBR_474;
	@FindBy(xpath = "//span[contains(text(),'BR_470')]/..") private WebElement errBR_470;
	@FindBy(xpath = "//span[contains(text(),'BR_487')]/..") private WebElement errBR_487;
	@FindBy(xpath = "//span[contains(text(),'BR_489')]/..") private WebElement errBR_489;
	@FindBy(xpath = "//span[contains(text(),'BR_471')]/..") private WebElement errBR_471;
	@FindBy(xpath = "//span[contains(text(),'BR_482')]/..") private WebElement errBR_482;
	@FindBy(xpath = "//div[@id='rulesViolationsModal']//li") private WebElement errcheckMailed;
	@FindBy(xpath = "//div[./b[contains(text(),'MAILING ADDRESS:')]]/following-sibling::div") private WebElement txtcheckMailed;

	
	
	private String loanQuote = "//*[contains(text(),'webElementText')]";

	private String strmaxloan = "//span[contains(@ng-if,'LoanTypeLoans')]//li";
	private String strRepaymentTerm = "//table[@id='quoteTable']/tbody/tr[rownum]/td//span";

	private String loanTerm = "//table[@id='quoteTable']//tr[./td//span[contains(text(),'Repayment Term')]]//input";
	private String payment = "//table[@id='quoteTable']//tr[./td//span[contains(text(),'Repayment Term')]]/td[2]";
	
	private String originationFee = "//table[@id='desktopTable']//div[contains(@ng-if,'loanType')]/div[contains(@translate,'originationFee')]";
	private String repaymentTerm = "//table[@id='desktopTable']//tr[contains(@ng-if,'repaymentTerm')]//span[contains(@ng-if,'loanType')]";
	private static String checkTotal = "";
	private static String interestRate = "";
	private static String loanType = "";
	private static String maturityDate = "";
	
	public static String getMaturityDate() {
		return maturityDate;
	}

	public static void setMaturityDate(String maturityDate) {
		RequestLoanPage.maturityDate = maturityDate;
	}

	private static String paymentAmount = "";
	private static String deliveryMethodSelected = "";
	private static String totalLoanAmount;
	
	public static String getTotalLoanAmount() {
		return totalLoanAmount;
	}

	public static void setTotalLoanAmount(String totalLoanAmount) {
		RequestLoanPage.totalLoanAmount = totalLoanAmount;
	}

	//Refinancing
	@FindBy(xpath = "//*[@id='mainBody']//div[@id='loanRefinanceContainer']")
	private WebElement loanRefinancingSection;
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']/div[2]//input[contains(@type,'radio')]")
	private WebElement btnRefinanceExistingLoan;
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']/div[1]//input[contains(@type,'radio')]")
	private WebElement btnNewLoanRefinance;
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']/div[2]/*")
	private WebElement txtRefinanceExistingLoan;
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']/div[1]/*")
	private WebElement txtNewLoanRefinance;
	@FindBy(xpath = "//div[@class='loan-refinance-checkbox']//input")
	private List<WebElement> refinanceLoanChkBox;
	@FindBy(xpath = "//*[@id='account-details-container']//h1[contains(@ng-if,'loanRefinanceCtrl.isNewLoansAllowed')]")
	private WebElement refinancePageTitle;
	@FindBy(xpath = "//*[@id='loanQuotesContainer']/div[1]/h2/span/div/a")
	private WebElement lnkEnterYourOwnTerm;
	@FindBy(xpath = "//*[@id='loanQuotesContainer']/div[1]/h2/span/div/div/div[2]/div")
	private WebElement popOverEnterYourOwnLoanTerm;
	@FindBy(id = "loanTermInput")
	private WebElement loanTermInput;
	@FindBy(id = "submitButton")
	private WebElement btnAddEnterYourOwnLoanTerm;
	@FindBy(xpath = "//*[@id='customQuotePopupContainer']/div[3]/div/span")
	private WebElement errorMsgAS_212;
	
	private String strRepaymentTermRefinance = "//*[@id='loanQuotesContainer']//table[@id='quoteTable']/tbody/tr[rownum]/td//span";
	
	private String 
	refinanceExistingLoansTableHeader = "//*[@id='loanQuotesContainer']/div[2]/div[@class='allocation-table-wrapper']/div[1]/table/thead/tr/th[colNum]";
	
	private String
	refinanceExistingLoansTableRepaymentTerm = "//*[@id='loanQuotesContainer']/div[2]/div[@class='allocation-table-wrapper']/div[1]/table/tbody/tr[rownum]/td[contains(@data-header,'Repayment Term')]";
	
	@FindBy(xpath = "//span[@class='currency existing-loan-total']")
	private WebElement refinanceCurrentBalance;
	@FindBy(xpath = "//table[@class='overview-table']/tbody/tr[2]/td/div[@class='error-placeholder']")
	private WebElement errorBlockForInvalidAmountEntered;
	
	private String 
	activeLoansDetailTableHeaderGP = "//*[@id='loanRefinanceContainer']/div[2]/div[2]/div[2]/div/table/thead/tr/th[colNum]";
	
	private String 
	activeLoansDetailTableBodyGP = "//*[@id='loanRefinanceContainer']/div[2]/div[2]/div[2]/div/table/tbody/tr/td[colNum]";
	
	private String 
	activeLoansDetailTableHeaderPR = "//*[@id='loanRefinanceContainer']/div[1]/div[2]/div[2]/div/table/thead/tr/th[colNum]";
	
	private String 
	activeLoansDetailTableBodyPR = "//*[@id='loanRefinanceContainer']/div[1]/div[2]/div[2]/div/table/tbody/tr[rowNum]/td[colNum]";
	
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']/div[2]/div[2]/div[2]/div/table/tbody/tr/td[@data-header= 'Outstanding Balance']")
	private WebElement outstandingBalanceValue;
	
	@FindBy(xpath = "//*[@id='loanRefinanceContainer']//div[contains(text(),' Select the loan or loans you would like to refinance.')]")
	private WebElement txtActiveLoansHeader;
	
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[1]/div[1]/b")
	private WebElement toolTipCurrentBalPaymentAmount;
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[1]/div[2]/b")
	private WebElement toolTipCurrentBalMaturityDate;
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[1]/div[3]/b")
	private WebElement toolTipCurrentBalLoanTermRemaining;
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[2]/div[1]/span")
	private WebElement toolTipCurrentBalPaymentAmountValue;
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[2]/div[2]/span")
	private WebElement toolTipCurrentBalMaturityDateValue;
	@FindBy(xpath = ".//*[@class='top table-popover fade tooltip in']/div/*/*/div[2]/div[3]/span")
	private WebElement toolTipCurrentBalLoanTermRemainingValue;
	
	@FindBy(xpath = "//p[contains(text(),'You have reached the maximum number of loans')]")
	private WebElement errorMsgExhaustedLoans;
	
	Actions keyBoardEvent = new Actions(Web.getDriver());
	

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
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),
				"User Name is Not Displayed\n");

		// Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestALoan,
		// true),"Request A Loan Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {

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
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblRequestALoan),
					"Requuest Loan page is not loadeded\n");
		} else {
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false, "Login Page is not loaded\n");
		}

	}

	@Override
	protected void load() {
		this.parent.get();

		((LeftNavigationBar) this.parent).clickNavigationLink("Request a loan");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		lib.Web.isWebElementDisplayed(lblRequestALoan, true);

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
		if (fieldName.trim().equalsIgnoreCase("Loan Confirmation")) {
			return this.hdrLoanConfirmationPage;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"Loan Confirmation Number And Date")) {
			return this.txtConfirmationNumber;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT COMPLETE")) {
			return this.txtComplte;
		}

		if (fieldName.trim().equalsIgnoreCase("GREEN CHECK MARK")) {
			return this.iconGreenCheck;
		}
		if (fieldName.trim().equalsIgnoreCase("PRINT LINK")) {
			return this.lnkPrint;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT AMOUNT FIELD")) {
			return this.inputLoanAmount;
		}
		if (fieldName.trim().equalsIgnoreCase("ACH ACCOUNT DROP DOWN")) {
			return this.drpACHAcoount;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT PROCESS LOAN AFTER HOLD")) {
			return this.inpProcessLoanAfterHold;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT EMAIL A FORM")) {
			return this.inpEmailForm;
		}
		if (fieldName.trim().equalsIgnoreCase("loanReasons")) {
			return this.txtLoanReasons;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE")) {
			return this.txtIntrestRateLoanSummary;
		}
		if (fieldName.trim().equalsIgnoreCase("FEES*")) {
			return this.txtOriginationFee;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE TEXT 1")) {
			return this.toolTipInterestRateText1;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE TEXT 2")) {
			return this.toolTipInterestRateText2;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE TEXT 3")) {
			return this.toolTipInterestRateText3;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE TEXT 4")) {
			return this.toolTipInterestRateText4;
		}
		if (fieldName.trim().equalsIgnoreCase("INTEREST RATE TEXT 5")) {
			return this.toolTipInterestRateText5;
		}
		if (fieldName.trim().equalsIgnoreCase("DELIVERY FEE TEXT")) {
			return this.toolTipFeeDeliveryText;
		}
		if (fieldName.trim().equalsIgnoreCase("DELIVERY FEE VALUE")) {
			return this.toolTipFeeDeliveryValue;
		}
		if (fieldName.trim().equalsIgnoreCase("ORIGINATION FEE TEXT")) {
			return this.toolTipFeeOriginationText;
		}
		if (fieldName.trim().equalsIgnoreCase("ORIGINATION FEE VALUE")) {
			return this.toolTipFeeOriginationValue;
		}
		if (fieldName.trim().equalsIgnoreCase(getInterestRate())) {
			return this.mortgageInterestRateLoanSummaryPage;
		}
		if (fieldName.trim().equalsIgnoreCase(getOriginationFeeFromLoanSummaryTable())) {
			return this.mortgageFeesLoanSummaryPage;
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

			if (Web.isWebElementDisplayed(inputLonatypeGeneral, true)) {
				inputLonatypeGeneral.click();
			} else {

				throw new Error(
						"Loan type radio button 'General' is not displayed");
			}

		} else if (loanType.equalsIgnoreCase("MORTGAGE")) {
			if (Web.isWebElementDisplayed(inputLonatypeMortgage, true)) {
				this.inputLonatypeMortgage.click();
			} else {

				throw new Error(
						"Loan type radio button 'Mortgage' is not displayed");
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

		WebElement element = getWebElement(fieldName);

		if (element.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Reqest Loan Button is Displayed", "Reqest "
							+ fieldName + " Button is Displayed", false);
		} else {
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Reqest Loan Button is Displayed", "Reqest "
							+ fieldName + " Buttton is not Displayed", true);
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
	 * <pre>
	 * Method to get the attribute of @value of an webElement
	 * Returns string
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementValueAttribute(String fieldName) {
		String getText = "";

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

			getText = this.getWebElement(fieldName).getAttribute("value")
					.toString().trim();

		}

		return getText;

	}

	/**
	 * Method to Enter Loan Amount,Loan Term and Click on Calculate Payment
	 * Information
	 * 
	 * @param loanAmount
	 * @param loanTerm
	 * 
	 */
	public void EnterLoanAmtAndTerm(String loanAmount, String loanTerm) {

		Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
		Web.clickOnElement(btnContinue);
		Common.waitForProgressBar();
		WebElement inpLoanTerm = Web.getDriver().findElement(
				By.xpath(this.loanTerm.replace("Repayment Term", loanTerm)));
		Web.clickOnElement(inpLoanTerm);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to Select Loan Term
	 * 
	 * @param loanTerm
	 * 
	 */
	public void selectLoanTerm(String loanTerm) {

		Common.waitForProgressBar();
		WebElement inpLoanTerm = Web.getDriver().findElement(
				By.xpath(this.loanTerm.replace("Repayment Term", loanTerm)));
		Web.clickOnElement(inpLoanTerm);
		lib.Reporter.logEvent(Status.INFO, "verifying Loan Term is selected", "Loan term selected: "+loanTerm, false);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setpaymentAmount(String loanTerm) {

		WebElement inpLoanTerm = Web.getDriver().findElement(
				By.xpath(this.payment.replace("Repayment Term", loanTerm)));
		RequestLoanPage.paymentAmount = "$" +inpLoanTerm.getText();
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
		Web.waitForPageToLoad(Web.getDriver());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		inputLoanAmount.clear();
		Web.setTextToTextBox(this.inputLoanAmount, loanAmount);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed = false;
		WebElement txtLoanQuote = Web.getDriver().findElement(
				By.xpath(loanQuote.replace("webElementText", fieldName)));

		isTextDisplayed = Web.isWebElementDisplayed(txtLoanQuote, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
					+ "  is Displayed", fieldName + " is Displayed", false);

		} else {
			lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
					+ " is Displayed", fieldName + " is Not Displayed", false);
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

		WebElement webelement = getWebElement(fieldName);
		String pageHeader = webelement.getText().toString().trim();
		if (Web.isWebElementDisplayed(webelement)) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + pageHeader
					+ " Page  is Displayed",
					pageHeader + " Page  is Displayed", true);

		} else {

			lib.Reporter.logEvent(Status.FAIL, "Verify " + pageHeader
					+ " Page  is Displayed", pageHeader
					+ " Page  is Not Displayed", true);
			throw new Error(fieldName + " is not displayed");
		}

	}

	/**
	 * Method to get Maximum Loan Amount
	 * 
	 * @return loanAmount
	 */
	public String getMaximumLoanAmount() {
		String loanAmount = " ";
		Web.waitForElement(txtLoanMaximum);
		try {
			if (txtLoanMaximum.isDisplayed())
				loanAmount = txtLoanMaximum.getText().trim().toString();

		} catch (NoSuchElementException e) {

			lib.Reporter.logEvent(Status.FAIL, "get Loan Maximum Amount",
					"Loan Maximum Amount is Not Displayed", true);
		}
		return loanAmount;
	}

	/**
	 * Method to get Maximum No.Of Loans Allowed from Application
	 * 
	 * @return maximumNoOfLoans
	 */
	public String getMaximumLoansAllowed() {
		String maximumNoOfLoans = " ";
		Web.waitForElement(txtMaximumNoOfLoans);
		try {
			if (txtMaximumNoOfLoans.isDisplayed())
				maximumNoOfLoans = txtMaximumNoOfLoans.getText().split("Up to")[1]
						.trim().toString();

		} catch (NoSuchElementException e) {

			lib.Reporter.logEvent(Status.FAIL,
					"get Maximum No Of Loans Allowed",
					" Maximum No Of Loans Allowed is Not Displayed", true);
		}
		return maximumNoOfLoans;
	}

	/**
	 * Method to get Maximum Loans Allowed for the plan
	 * 
	 * @param ga_Id
	 *            150049
	 * @return maxLoansAllowed
	 * @throws SQLException
	 */
	public String getMaximumLoansAllowedforPlan(String ga_Id)
			throws SQLException {

		// query to get the no of Loans
		String[] sqlQuery = null;
		ResultSet loans = null;
		String maxLoansAllowed = "";

		try {
			sqlQuery = Stock.getTestQuery("getMaxLoansAllowed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		loans = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
		try {
			if (DB.getRecordSetCount(loans) > 0) {
				loans.first();
				maxLoansAllowed = loans.getString("max_loans_allowed");
			}
		} catch (SQLException e) {
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
	 * 
	 * @param ga_Id
	 *            150049
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getMaximumLoansAllowedforLoanReason(String ga_Id)
			throws SQLException {

		// query to get the no of Loans
		String[] sqlQuery = null;
		ResultSet loans = null;
		Map<String, String> mapMaxLoansAllowed = new HashMap<String, String>();

		try {
			sqlQuery = Stock.getTestQuery("getMaxLoansAllowedforLoanReason");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		loans = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
		try {
			if (DB.getRecordSetCount(loans) > 0) {
				System.out.println();
				while (loans.next()) {
					mapMaxLoansAllowed.put(loans.getString("loan_reason_code"),
							loans.getString("max_loans_allowed"));
				}
			}
		} catch (SQLException e) {
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
	 * 
	 * @throws SQLException
	 * 
	 *
	 */
	public void verifyMaximumLoansForLoanStructure(String ga_id)
			throws SQLException {
		WebElement txtLoanQuote = null;
		Map<String, String> maxloansActual;
		Map<String, String> maxloansExpected = new HashMap<String, String>();
		maxloansActual = getMaximumLoansAllowedforLoanReason(ga_id);
		Set<String> keys = maxloansActual.keySet();
		try {
			for (String key : keys) {
				if (key.equalsIgnoreCase("GENERAL")) {
					txtLoanQuote = Web.getDriver().findElement(
							By.xpath(strmaxloan.replace("LoanType", "GenPur")));
				} else if (key.equalsIgnoreCase("MORTGAGE")) {
					txtLoanQuote = Web.getDriver().findElement(
							By.xpath(strmaxloan.replace("LoanType",
									"PrimaryRes")));
				}
				System.out.println();
				maxloansExpected.put(key, txtLoanQuote.getText().split(" ")[0]);

			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		if (maxloansExpected.equals(maxloansActual))
			Reporter.logEvent(
					Status.PASS,
					"Verify  Maximum Loan Amount for each Loan Structure is displayed",
					"Maximum Loan Amount for each Loan Structure is Matching "
							+ maxloansActual, true);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify  Maximum Loan Amount for each Loan Structure is displayed",
					"Maximum Loan Amount for  Loan Structure is not Matching\nExpected:"
							+ maxloansExpected + "\nActual:" + maxloansActual,
					true);

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
					" Loans Diclaimer is displayed: "
							+ txtloanDisclaimer1.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected: "
							+ Stock.GetParameterValue("Disclaimer1")
							+ "\nActual: "
							+ txtloanDisclaimer1.getText().toString().trim(),
					true);

		if (txtloanDisclaimer2.getText().toString().trim()
				.equals(Stock.GetParameterValue("Disclaimer2")))
			Reporter.logEvent(Status.PASS,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is displayed: "
							+ txtloanDisclaimer2.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected: "
							+ Stock.GetParameterValue("Disclaimer2")
							+ "\nActual: "
							+ txtloanDisclaimer2.getText().toString().trim(),
					true);

		if (txtloanDisclaimer3.getText().toString().trim()
				.equals(Stock.GetParameterValue("Disclaimer3")))
			Reporter.logEvent(Status.PASS,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is displayed: "
							+ txtloanDisclaimer3.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Loans Diclaimer is displayed",
					" Loans Diclaimer is not matching \nExpected: "
							+ Stock.GetParameterValue("Disclaimer3")
							+ "\nActual: "
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
			lib.Reporter
					.logEvent(Status.PASS,
							"Verify Minimum Loan Amount is Displayed",
							"Minimum Loan Amount is Displayed"
									+ txtLoanMinimum.getText().toString()
											.trim(), false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Minimum Loan Amount is Displayed",
					"Minimum Loan Amount is Displayed"
							+ txtLoanMinimum.getText().toString().trim(), true);

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
		if (Web.isWebElementDisplayed(element,true)) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
					+ " is Displayed", fieldName + " is Displayed", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
					+ " is Displayed", fieldName + " is Not Displayed", true);
			throw new Error(fieldName + " is not displayed");
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
		if (Web.isWebElementDisplayed(txtErrorMsg, true)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Minimum Loan Amount Error Message is Displayed",
					"Minimum Loan Amount Error Message is Displayed\n"
							+ txtErrorMsg.getText().toString().trim(), false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Minimum Loan Amount Error Message is Displayed",
					"Minimum Loan Amount Error Message is not Displayed\n"
							+ txtErrorMsg.getText().toString().trim(), true);

		}
		if (!btnContinue.isEnabled()) {
			lib.Reporter
					.logEvent(
							Status.PASS,
							"Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							"Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							false);

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							"Continue Button is Not Disabled when Minimum Loan Amount Error Message is Displayed",
							true);

		}

	}

	/**
	 * <pre>
	 * Method to Verify Maximum Loan Amount Error Message is Displayed
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyLoanMaximumErrorMessageIsDisplayed(String loanAmount) {

		Web.setTextToTextBox(this.inputLoanAmount, loanAmount);
		if (Web.isWebElementDisplayed(txtErrorMsg, true)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Maximum Loan Amount Error Message is Displayed",
					"Maximum Loan Amount Error Message is Displayed\n"
							+ txtErrorMsg.getText().toString().trim(), false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Maximum Loan Amount Error Message is Displayed",
					"Maximum Loan Amount Error Message is not Displayed\n"
							+ txtErrorMsg.getText().toString().trim(), true);

		}
		if (!btnContinue.isEnabled()) {
			lib.Reporter
					.logEvent(
							Status.PASS,
							"Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							"Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							false);

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Continue Button is Disabled when Minimum Loan Amount Error Message is Displayed",
							"Continue Button is Not Disabled when Minimum Loan Amount Error Message is Displayed",
							true);

		}

	}

	/**
	 * <pre>
	 * Method to Verify Continue Button Is Enabled
	 *
	 * </pre>
	 */
	public void verifyContinueButtonIsEnabled(boolean enable) {
		Web.waitForElement(btnContinue);
		if (enable) {
			if (btnContinue.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Enabled",
						"Continue Button is Enabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Enabled",
						"Continue Button is Not Enabled", true);

			}
		} else {
			if (!btnContinue.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Disabled",
						"Continue Button is Disabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Disabled",
						"Continue Button is Not Disabled", true);

			}
		}

	}
	/**
	 * <pre>
	 * Method to Verify Continue Button Is Displayed
	 *
	 * </pre>
	 */
	public void verifyContinueButtonIsDisplayed(boolean enable) {
		
		
		if (enable) {
			if (btnContinue.isDisplayed()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Displayed",
						"Continue Button is Displayed", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Displayed",
						"Continue Button is not Displayed", true);

			}
		} else {
			try{
				if (btnContinue.isDisplayed()) {
					lib.Reporter.logEvent(Status.FAIL,
							"Verify Continue Button is Displayed",
							"Continue Button is Displayed", true);
				}
			}catch(Exception e){
					lib.Reporter.logEvent(Status.PASS,
							"Verify Continue Button is Displayed",
							"Continue Button is not Displayed "+e.getMessage(), false);				

			}
			 
		}

	}

	
	/**
	 * <pre>
	 * Method to Verify Continue Button Is Enabled
	 *
	 * </pre>
	 */
	public void verifyUpdateButtonIsEnabled(boolean enable) {
		Web.waitForElement(btnUpdate);
		if (enable) {
			if (btnUpdate.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Enabled",
						"Continue Button is Enabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Enabled",
						"Continue Button is Not Enabled", true);

			}
		} else {
			if (!btnUpdate.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Disabled",
						"Continue Button is Disabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Disabled",
						"Continue Button is Not Disabled", true);

			}
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
	 * Method to Click On Update Button
	 * </pre>
	 *
	 */

	public void clickUpdateButton() {
		boolean isTextDisplayed = false;
		try {

			isTextDisplayed = Web.isWebElementDisplayed(btnUpdate, true);

			if (isTextDisplayed)
				Web.clickOnElement(btnUpdate);

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
	 * Method to Verify Back Button Is Enabled
	 *
	 * </pre>
	 */
	public void verifyBackButtonIsEnabled(boolean enable) {
		Web.waitForElement(btnBack);
		if (enable) {
			if (btnBack.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Back Button is Enabled",
						"Back Button is Enabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Back Button is Enabled",
						"Back Button is Not Enabled", true);

			}
		} else {
			if (!btnBack.isEnabled()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Back Button is Disabled",
						"Back Button is Disabled", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Back Button is Disabled",
						"Back Button is Not Disabled", true);

			}
		}

	}
	/**
	 * <pre>
	 * Method to Verify Back Button Is Displayed
	 *
	 * </pre>
	 */
	public void verifyBackButtonIsDisplayed(boolean displayed) {
		Web.waitForElement(btnBack);
		if (displayed) {
			if (btnBack.isDisplayed()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Displayed",
						"Continue Button is Displayed", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Displayed",
						"Continue Button is not Displayed", true);

			}
		} else {
			try{
				if (btnBack.isDisplayed()) {
					lib.Reporter.logEvent(Status.FAIL,
							"Verify Continue Button is Displayed",
							"Continue Button is Displayed", true);
				}
			}catch(Exception e){
					lib.Reporter.logEvent(Status.PASS,
							"Verify Continue Button is Displayed",
							"Continue Button is not Displayed "+e.getMessage(), false);				

			}
			 
		}

	}

	/**
	 * <pre>
	 * Method to Verify Default Repayment Term Options
	 * For General Purpose
	 * </pre>
	 * 
	 *
	 */
	public void verifyDefaultRepamentTerm() {
		String[] repaymentTerm = { "12months", "24months", "36months",
				"48months", "60months" };
		for (int i = 0; i < repaymentTerm.length; i++) {
			List<WebElement> inpLoanTerm = Web.getDriver().findElements(
					By.xpath(strRepaymentTerm.replace("rownum",
							Integer.toString(i+1))));
	//		int j = inpLoanTerm.size();
			String actualLoanTerm = inpLoanTerm.get(0).getText().toString()
					.trim()
					+ inpLoanTerm.get(1).getText().toString().trim();

			if (repaymentTerm[i].equalsIgnoreCase(actualLoanTerm)) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term " + (i+1)+" :" + actualLoanTerm, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL, 
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term is not displayed as expected\nExpected:"
										+ repaymentTerm[i] + "\nActual:"
										+ actualLoanTerm, true);

			}
		}
	}
	/**
	 * <pre>
	 * Method to Verify Default Repayment Term Options for Loan Refinancing
	 *
	 * </pre>
	 * 
	 *
	 *//*
	public void verifyDefaultRepamentTermForLoanRefinance() {
	//	String[] repaymentTerm = { "12months", "24months", "36months", "37months"};
		String[] repaymentTerm = { "12months", "24months", "36months"};
		for (int i = 0; i < repaymentTerm.length; i++) {
			List<WebElement> inpLoanTerm = Web.getDriver().findElements(
					By.xpath(strRepaymentTermRefinance.replace("rownum",
							Integer.toString(i+1))));
//			int j = inpLoanTerm.size();
			String actualLoanTerm = inpLoanTerm.get(0).getText().toString()
					.trim()
					+ inpLoanTerm.get(1).getText().toString().trim();

			if (repaymentTerm[i].contains(actualLoanTerm)) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term " + (i+1)+" :" + actualLoanTerm, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL, 
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term is not displayed as expected\nExpected:"
										+ repaymentTerm[i] + "\nActual:"
										+ actualLoanTerm, true);

			}
		}
	}*/
	/**
	 * <pre>
	 * Method to Verify Default Repayment Term Options for Loan Refinancing
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyDefaultRepamentTermForLoanRefinance() {
	//	String[] repaymentTerm = { "12months", "24months", "36months", "37months"};
		String[] repaymentTerm = { "12months", "24months", "36months"};
		for (int i = 0; i < repaymentTerm.length; i++) {
			List<WebElement> inpLoanTerm = Web.getDriver().findElements(
					By.xpath(strRepaymentTermRefinance.replace("rownum",
							Integer.toString(i+1))));
//			int j = inpLoanTerm.size();
			String actualLoanTerm = inpLoanTerm.get(0).getText().toString()
					.trim()
					+ inpLoanTerm.get(1).getText().toString().trim();

			if (repaymentTerm[i].contains(actualLoanTerm)) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term " + (i+1)+" :" + actualLoanTerm, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL, 
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term is not displayed as expected\nExpected:"
										+ repaymentTerm[i] + "\nActual:"
										+ actualLoanTerm, true);

			}
		}
	}
	
	/**
	 * Method to verify Repayment Term options
	 * in Sorted order
	 * Principal Residence Loan
	 * @author bbndsh	
	 */
	
	public void verifyDefaultRepyamentTermPrincipalResidence() {
		
		String[] repaymentTerm = { "60months", "120months", "180months", "240months", "300months" };
		for (int i = 0; i < repaymentTerm.length; i++) {
			List<WebElement> inpLoanTerm = Web.getDriver().findElements(
					By.xpath(strRepaymentTerm.replace("rownum",
							Integer.toString(i+1))));
	//		int j = inpLoanTerm.size();
			String actualLoanTerm = inpLoanTerm.get(0).getText().toString()
					.trim()
					+ inpLoanTerm.get(1).getText().toString().trim();

			if (repaymentTerm[i].equalsIgnoreCase(actualLoanTerm)) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term " + (i+1)+" :" + actualLoanTerm, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL, 
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term is not displayed as expected\nExpected:"
										+ repaymentTerm[i] + "\nActual:"
										+ actualLoanTerm, true);

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
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(lblLoanReview);
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
		String[] repaymentTerm = { "12", "24", "36", "48", "60" };
		for (int i = 0; i < repaymentTerm.length; i++) {
			WebElement inpLoanTerm = Web.getDriver().findElement(
					By.xpath(this.loanTerm.replace("Repayment Term",
							repaymentTerm[i])));

			if (inpLoanTerm.isDisplayed()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Radio Button is Displayed for Loan Term",
						"Radio Button is Displayed for Loan Term "
								+ repaymentTerm[i] + " Months", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Radio Button is Displayed for Loan Term",
						"Radio Button is Not Displayed for Loan Term "
								+ repaymentTerm[i] + " Months", true);

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
	public void verifyMailDeliveryOptionsWithoutACH() {
		verifyWebElementIsDisplayed("REGULAR MAIL RADIO BUTTON");
		verifyWebElementIsDisplayed("EXPEDITED MAIL RADIO BUTTON");

		if (inpRegularMail.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Radio Button is Displayed for Regulr Mail",
					"Radio Button is Displayed for Regulr Mail", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Radio Button is Displayed for Regulr Mail",
					"Radio Button is Displayed forRegulr Mail", true);

		}
		if (inpExpressMail.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Radio Button is Displayed for EXPEDITED MAIL",
					"Radio Button is Displayed for EXPEDITED MAIL", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Radio Button is Displayed for EXPEDITED MAIL",
					"Radio Button is Displayed for EXPEDITED MAIL", true);

		}
		try{
		if (inpACHDelivery.isDisplayed()) {
			lib.Reporter.logEvent(Status.FAIL,
					"Verify  ACH DELIVERY Option is Displayed",
					" ACH DELIVERY Option is Displayed", true);
		}
		}
		catch(NoSuchElementException e){
			
		lib.Reporter.logEvent(Status.PASS,
				"Verify  ACH DELIVERY Option is Displayed",
				" ACH DELIVERY Option is Not Displayed", false);
		}
		
		

		if (txtRegularMailDeliveryTime.isDisplayed()) {
			String expectedText = "Delivery up to 5 business days";
			String actualText = txtRegularMailDeliveryTime.getText().toString()
					.trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Delivery Time for Regular Mail is Displayed",
						"Delivery Time for Regular Mail is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Delivery Time for Regular Mail is Displayed",
								"Delivery Time for Regular Mail is not Matching \nExpected:"
										+ expectedText + "\nActual:"
										+ actualText, true);

			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Delivery Time for Regular Mail is Displayed",
					"Delivery Time for Regular Mail is Not Displayed", true);

		}
		if (txtExpeditedMailDeliveryTime.isDisplayed()) {
			String expectedText = "Delivery up to 2 business days";
			String actualText = txtExpeditedMailDeliveryTime.getText()
					.toString().trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Delivery Time for Expedited Mail is Displayed",
						"Delivery Time for Expedited Mail is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Delivery Time for Expedited Mail is Displayed",
								"Delivery Time for Expedited Mail is not Matching \nExpected:"
										+ expectedText + "\nActual:"
										+ actualText, true);

			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Delivery Time for Expedited Mail is Displayed",
					"Delivery Time for Expedited Mail is Not Displayed", true);

		}

	}

	/**
	 * <pre>
	 * Method to Verify Mail Delivery Options Available
	 *
	 * </pre>
	 *
	 */
	public void verifyMailDeliveryOptionsWithACH() {
		verifyWebElementIsDisplayed("REGULAR MAIL RADIO BUTTON");
		verifyWebElementIsDisplayed("EXPEDITED MAIL RADIO BUTTON");
		verifyWebElementIsDisplayed("ACH DELIVERY RADIO BUTTON");

		if (inpRegularMail.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Radio Button is Displayed for Regulr Mail",
					"Radio Button is Displayed for Regulr Mail", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Radio Button is Displayed for Regulr Mail",
					"Radio Button is Displayed forRegulr Mail", true);

		}
		if (inpExpressMail.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Radio Button is Displayed for EXPEDITED MAIL",
					"Radio Button is Displayed for EXPEDITED MAIL", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Radio Button is Displayed for EXPEDITED MAIL",
					"Radio Button is Displayed for EXPEDITED MAIL", true);

		}
		if (inpACHDelivery.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Radio Button is Displayed for ACH DELIVERY",
					"Radio Button is Displayed for ACH DELIVERY", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Radio Button is Displayed for ACH DELIVERY",
					"Radio Button is Displayed for ACH DELIVERY", true);

		}

		if (txtRegularMailDeliveryTime.isDisplayed()) {
			String expectedText = "Delivery up to 5 business days";
			String actualText = txtRegularMailDeliveryTime.getText().toString()
					.trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Delivery Time for Regular Mail is Displayed",
						"Delivery Time for Regular Mail is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Delivery Time for Regular Mail is Displayed",
								"Delivery Time for Regular Mail is not Matching \nExpected:"
										+ expectedText + "\nActual:"
										+ actualText, true);

			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Delivery Time for Regular Mail is Displayed",
					"Delivery Time for Regular Mail is Not Displayed", true);

		}
		if (txtExpeditedMailDeliveryTime.isDisplayed()) {
			String expectedText = "Delivery up to 2 business days";
			String actualText = txtExpeditedMailDeliveryTime.getText()
					.toString().trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Delivery Time for Expedited Mail is Displayed",
						"Delivery Time for Expedited Mail is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Delivery Time for Expedited Mail is Displayed",
								"Delivery Time for Expedited Mail is not Matching \nExpected:"
										+ expectedText + "\nActual:"
										+ actualText, true);

			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Delivery Time for Expedited Mail is Displayed",
					"Delivery Time for Expedited Mail is Not Displayed", true);

		}
		if (txtACHMailDeliveryTime.isDisplayed()) {
			String expectedText = "Delivery up to 3 business days";
			String actualText = txtACHMailDeliveryTime.getText().toString()
					.trim();
			if (expectedText.equalsIgnoreCase(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Delivery Time for ACH is Displayed",
						"Delivery Time for ACH is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);

			} else {

				lib.Reporter
						.logEvent(Status.FAIL,
								"Verify Delivery Time for ACH is Displayed",
								"Delivery Time for ACH is not Matching \nExpected:"
										+ expectedText + "\nActual:"
										+ actualText, true);

			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Delivery Time for ACH is Displayed",
					"Delivery Time for ACH is Not Displayed", true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify Regular Mail Delivery Option is Selected by Default
	 *
	 * </pre>
	 *
	 */
	public void verifyRegularMailSelectedAsDefault() {

		if (inpRegularMail.isDisplayed() && inpRegularMail.isSelected()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Regulr Mail' Radio Button is Selected By Default",
					"'Regulr Mail' Radio Button is Selected By Default", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Regulr Mail' Radio Button is Selected By Default",
					"'Regulr Mail' Radio Button is Not Selected By Default",
					true);

		}
	}
	
	/**
	 * 
	 * Method to verify Expedited mail Delivery option is displayed
	 * If so select Expedited mail button 
	 */

	public void clickExpeditedMailDeliveryOption(){
		boolean isTextDisplayed = false;
		
		try {

			isTextDisplayed = Web.isWebElementDisplayed(txtExpeditedMailDeliveryTime,
					true);

			if (isTextDisplayed)
				Web.clickOnElement(inpExpressMail);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 * Method to verify 'Electronically transfer funds directly to my bank account (ACH)' option is displayed
	 * If so select ACH button 
	 */

	public void clickElectronicallyTransferFundsACHOption(){
		boolean isTextDisplayed = false;
		
		try {

			isTextDisplayed = Web.isWebElementDisplayed(txtACHMailDeliveryTime,
					true);

			if (isTextDisplayed)
				Web.clickOnElement(inpACHDelivery);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * <pre>
	 * Method to get the interest Rate for General Purpose/Mortgage loan from Request Loan Page
	 *
	 * </pre>
	 *
	 */
	public String getInterestRateFromRequestLoanPage(String loanType) {
		String interestRate = "";
		if (loanType.equalsIgnoreCase("GENERAL")) {
			interestRate = txtGeneralPurposeIntrestRate.getText().toString()
					.trim();

		} else if (loanType.equalsIgnoreCase("MORTGAGE")) {
			interestRate = txtMortgageIntrestRate.getText().toString().trim();

		}
		return interestRate;
	}

	/**
	 * <pre>
	 * Method to get the interest Rate from loan summary Table
	 *
	 * </pre>
	 *
	 */
	public String getInterestRateFromLoanSummaryTable() {
		String interestRate = "";
		if (txtIntrestRateLoanSummary.isDisplayed()) {

			interestRate = txtIntrestRateLoanSummary.getText().toString()
					.trim();

		} else {
			throw (new Error(
					"Interest Rate is Not Displayed in Loan Summary Page"));
		}
		return interestRate;
	}

	/**
	 * <pre>
	 * Method to get the Origination Fee from loan summary Table
	 *
	 * </pre>
	 *
	 */
	public String getOriginationFeeFromLoanSummaryTable() {
		String fee = "";
		if (txtOriginationFee.isDisplayed()) {

			fee = txtOriginationFee.getText().toString().trim();

		} else {
			throw (new Error(
					"Origination Fee is Not Displayed in Loan Summary Page"));
		}
		
		return fee;
	}

	/**
	 * <pre>
	 * Method to get Check Total from loan summary Table
	 *
	 * </pre>
	 *
	 */
	public String getCheckTotalFromSummaryTable() {
		String checkTotal = "";
		if (txtCheckTotal.isDisplayed()) {

			checkTotal = txtCheckTotal.getText().toString().trim();

		} else {
			throw (new Error(
					"Check Total is Not Displayed in Loan Summary Page"));
		}
		return checkTotal;
	}

	/**
	 * <pre>
	 * Method to get the Loan Total from loan summary Table
	 *
	 * </pre>
	 *
	 */
	public String getLoanTotalFromLoanSummaryTable() {
		String loanTotal = "";
		if (txtLoanTotal.isDisplayed()) {

			loanTotal = txtLoanTotal.getText().toString().trim();

		} else {
			throw (new Error("Loan Total is Not Displayed in Loan Summary Page"));
		}
		return loanTotal;
	}

	/**
	 * <pre>
	 * Method to Verify  the Origination Fee from loan summary Table
	 *
	 * </pre>
	 *
	 */
	public void verifyOriginationFeeDisplayed() {

		if (txtOriginationFee.isDisplayed()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Origination Fee' is Displayed",
					"'Origination Fee' is Displayed\n FEE*="
							+ getOriginationFeeFromLoanSummaryTable(), false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Origination Fee' is Displayed",
					"'Origination Fee' is Not Displayed", true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify  Check Total Amount in Summary Page
	 *
	 * </pre>
	 *
	 */
	public void verifyCheckTotalAmount() {
		double originationFee = Web
				.getIntegerCurrency(getOriginationFeeFromLoanSummaryTable());
		double loanTotal = Web
				.getIntegerCurrency(getLoanTotalFromLoanSummaryTable());
		String expectedCheckTotal = "$"
				+ Double.toString(loanTotal - originationFee);
		String actualCheckTotal = getCheckTotalFromSummaryTable();

		if (actualCheckTotal.contains(expectedCheckTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Check Total' Amount is Same",
					"'Check Total' Amount is Same\nCheck Total="
							+ actualCheckTotal, false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Check Total' Amount is Same",
					"'Check Total' Amount is Not Same\nExpected:"
							+ expectedCheckTotal + "\nActual:"
							+ actualCheckTotal, true);

		}
	}
	
	/**
	 * <pre>
	 * Method to Verify  Check Total Amount in Summary Page
	 *
	 * </pre>
	 *
	 */
	public void verifyCheckTotalAmountForRefinance() {
		double originationFee = Web
				.getIntegerCurrency(getOriginationFeeFromLoanSummaryTable());
		double loanTotal = Double.valueOf(Stock.GetParameterValue("loanAmount"));
		String expectedCheckTotal = "$"
				+ Double.toString(loanTotal - originationFee);
		String actualCheckTotal = getCheckTotalFromSummaryTable();

		if (actualCheckTotal.contains(expectedCheckTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Check Total' Amount is Same",
					"'Check Total' Amount is Same\nCheck Total="
							+ actualCheckTotal, false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Check Total' Amount is Same",
					"'Check Total' Amount is Not Same\nExpected:"
							+ expectedCheckTotal + "\nActual:"
							+ actualCheckTotal, true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify  Loan Total Amount is Matching
	 *
	 * </pre>
	 *
	 */
	public void verifyLoanTotalAmount() {
		double originationFee = Web.getIntegerCurrency(getOriginationFeeFromLoanSummaryTable());
		double checkTotal = Web.getIntegerCurrency(getCheckTotalFromSummaryTable());
		String expectedLoanTotal = "$"
				+ Double.toString(checkTotal + originationFee);
		
		String actualLoanTotal = getLoanTotalFromLoanSummaryTable().replace(",", "");

		if (actualLoanTotal.contains(expectedLoanTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Loan Total' Amount is Same",
					"'Loan Total' Amount is Same\nLoan Total="
							+ actualLoanTotal, false);

		} else {

			lib.Reporter
					.logEvent(Status.FAIL,
							"Verify 'Loan Total' Amount is Same",
							"'Loan Total' Amount is Not Same\nExpected:"
									+ expectedLoanTotal + "\nActual:"
									+ actualLoanTotal, true);

		}
	}

	/**
	 * <pre>
	 * Method to Click On I Agree And Submit Button
	 * </pre>
	 *
	 */

	public void clickOnIAgreeAndSubmit() {
		boolean isTextDisplayed = false;
		try {

			isTextDisplayed = Web.isWebElementDisplayed(btnIAgreeAndSubmit,
					true);

			if (isTextDisplayed)
				Web.clickOnElement(btnIAgreeAndSubmit);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Disclaimer in Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public void verifyLoanConfirmationDisclaimer() {

		String expectedText = "Your loan request has been received is being processed. "
				+ "Depending on your preference, you will receive an email and/or text with your loan's status.";
		String actualLoanText = txtconfirmationdisclaimer.getText().toString()
				.trim();

		if (expectedText.equalsIgnoreCase(actualLoanText)) {
			lib.Reporter
					.logEvent(
							Status.PASS,
							"Verify disclaimer message below the 'Loan request received' section",
							"'Disclaimer message below the 'Loan request received' section is displayed\n Disclaimer:"
									+ actualLoanText, false);

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify disclaimer message below the 'Loan request received' section",
							"'Disclaimer message below the 'Loan request received' section is not same\n Expected Disclaimer:"
									+ expectedText
									+ "\n Actual Disclaimer:"
									+ actualLoanText, true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify Plan Name Confirmation Page
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public void verifyPlanName(String gc_Id) throws Exception {

		String expectedText = Common.getPlanName(gc_Id);
		String actualText = txtPlanName.getText().toString().trim();
		if (expectedText.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS, "Verify Plan Name is Displayed",
					"Plan Name is Displayed in Confirmation Page\nPlan Name: "
							+ actualText, false);

		} else {

			Reporter.logEvent(Status.FAIL, "Verify Plan Name is Displayed",
					"Plan Name is not Same in Confirmation Page\nExpected Plan Name: "
							+ expectedText + "\nActual Plan Name: " + actualText,
					true);

		}
	}

	/**
	 * <pre>
	 * Method to get the interest Rate for General Purpose/Mortgage loan from Loan Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public String getInterestRateFromLoanConfirmationPage() {
		String interestRateinConfirmation = "";
		if (txtInterestRate.isDisplayed()) {
			interestRateinConfirmation=txtInterestRate.getText().toString().trim();
		} else {
			throw (new Error(
					"Interest Rate is Not Displayed in Loan Confirmation Page"));
		}
		return interestRateinConfirmation;
	}

	/**
	 * <pre>
	 * Method to get Check Amount from loan Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public String getCheckAmountFromConfirmationPage() {
		String checkTotal = "";
		if (txtCheckAmount.isDisplayed()) {

			checkTotal = txtCheckAmount.getText().toString().trim();

		} else {
			throw (new Error(
					"Check Amount is Not Displayed in Loan Confirmation Page"));
		}
		return checkTotal;
	}

	/**
	 * <pre>
	 * Method to get the Loan Amount from loan confirmation page
	 *
	 * </pre>
	 *
	 */
	public String getLoanAmountFromLoanConfirmationPage() {
		String loanTotal = "";
		if (txtLoanAmount.isDisplayed()) {

			loanTotal = txtLoanAmount.getText().toString().trim();

		} else {
			throw (new Error(
					"Loan Amount is Not Displayed in Loan Confirmation Page"));
		}
		return loanTotal;
	}

	/**
	 * <pre>
	 * Method to Verify  Loan Type from Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public void verifyLoanTypeInConfirmationPage() {
		String loanType =Stock.GetParameterValue("loan_type").trim();
		if (txtLoanType.isDisplayed()
				&& txtLoanType.getText().toString().trim()
						.equalsIgnoreCase(loanType)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Loan Type' is Displayed",
					"'Loan Type' is Displayed\n loan type: "
							+ txtLoanType.getText().toString().trim(), false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Loan Type' is Displayed",
					"'Loan Type' is Not Displayed", true);

		}
		setLoanType(loanType);
	}

	/**
	 * <pre>
	 * Method to Verify Loan Term in Confirmation Page
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyLoanTermInConfirmationPage() {
		String expectedLoanTerm;
		if(getLoanType().equalsIgnoreCase("Principal Residence"))
		 expectedLoanTerm = Stock.GetParameterValue("loanTerm")
				+ " years";
		else
			expectedLoanTerm = Stock.GetParameterValue("loanTerm")
			+ " months";

		String actualLoanTerm = txtLoanTerm.getText().toString().trim();

		if (expectedLoanTerm.equalsIgnoreCase(actualLoanTerm)) {
			Reporter.logEvent(Status.PASS,
					"Verify Loan Term is Displayed in Confirmation Page",
					"Loan Term is Displayed in Confirmation Page\nLoan Term: "
							+ actualLoanTerm, false);

		} else {

			Reporter.logEvent(Status.FAIL,
					"Verify Loan Term is Displayed in Confirmation Page",
					"Loan Term is Displayed in Confirmation Page\nExpected: "
							+ expectedLoanTerm + "\nActual: " + actualLoanTerm,
					true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify Interest Rate in Confirmation Page
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyInterestRateInConfirmationPage() {

		if (getInterestRateFromLoanConfirmationPage().equalsIgnoreCase(
				getInterestRate())) {
			Reporter.logEvent(Status.PASS,
					"Verify Interest Rate is Displayed in Confirmation Page",
					"Interest Rate is Displayed in Confirmation Page\nInterest Rate:"
							+ getInterestRate(), false);

		} else {

			Reporter.logEvent(Status.FAIL,
					"Verify Interest Rate is Displayed in Confirmation Page",
					"Interest Rate is not Displayed in Confirmation Page\nExpected:"
							+ getInterestRateFromLoanConfirmationPage()
							+ "\nActual:" + getInterestRate(), true);

		}
	}
	
	/**
	 * <pre>
	 * Method to Verify Interest Rate in My Loan Summary Page
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyInterestRateInLoanSummaryTable() {

		
		if (getInterestRate().equalsIgnoreCase(
				getInterestRateFromLoanSummaryTable())) {
			Reporter.logEvent(Status.PASS,
					"Verify Interest Rate in My Loan Summary page Displayed is same as Displayed in Request a Loan Page",
					"Interest Rate in My Loan Summary page Displayed is same as Displayed in Request a Loan Page:"
							+ getInterestRate(), false);

		} else {

			Reporter.logEvent(Status.FAIL,
					"Verify Interest Rate in My Loan Summary page Displayed is same as Displayed in Request a Loan Page",
					"Interest Rate in My Loan Summary page Displayed is not same as Displayed in Request a Loan Page:"
							+ getInterestRate()
							+ "\nActual:" + getInterestRateFromLoanSummaryTable(), true);

		}
	}

	
	
	/**
	 * <pre>
	 * Method to Verify  Check Total Amount is Matching in Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public void verifyCheckAmountInConfirmationPage() {

		double fee = Web.getIntegerCurrency(getOriginationFee());
		double requestedAmount = Double.valueOf(Stock.GetParameterValue("loanAmount"));
		double checkTotal = requestedAmount - fee;
		
		String expectedCheckTotal = "$"+Double.toString(checkTotal);
		
		String actualCheckTotal = getCheckAmountFromConfirmationPage();

		if (actualCheckTotal.contains(expectedCheckTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Check Total' Amount is Same",
					"'Check Total' Amount is Same\nCheck Total="
							+ actualCheckTotal, false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'Check Total' Amount is Same", 
					"'Check Total' Amount is Not Same\nExpected:"
							+ expectedCheckTotal + "\nActual: "
							+ actualCheckTotal, true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify  Loan Amount is Matching in Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public void verifyLoanAmountInConfirmationPage() {

		String expectedLoanTotal = "$"+Stock.GetParameterValue("LoanTotal")+".00";
		String actualLoanTotal = getLoanAmountFromLoanConfirmationPage().replace(",", "");

		if (actualLoanTotal.contains(expectedLoanTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Loan Total' Amount is Same",
					"'Loan Total' Amount is Same\nLoan Total="
							+ actualLoanTotal, false);

		} else {

			lib.Reporter
					.logEvent(Status.FAIL,
							"Verify 'Loan Total' Amount is Same",
							"'Loan Total' Amount is Not Same\nExpected:"
									+ expectedLoanTotal + "\nActual:"
									+ actualLoanTotal, true);

		}
	}

	
	/**
	 * <pre>
	 * Method to Verify  Loan Amount is Matching in Confirmation Page
	 *
	 * </pre>
	 *
	 */
	public void verifyLoanAmountInConfirmationPageForRefinance() {

		String expectedLoanTotal = RequestLoanPage.getTotalLoanAmount();
		String actualLoanTotal = getLoanAmountFromLoanConfirmationPage().replace(",", "");

		if (actualLoanTotal.contains(expectedLoanTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Loan Total' Amount is Same",
					"'Loan Total' Amount is Same\nLoan Total="
							+ actualLoanTotal, false);

		} else {

			lib.Reporter
					.logEvent(Status.FAIL,
							"Verify 'Loan Total' Amount is Same",
							"'Loan Total' Amount is Not Same\nExpected:"
									+ expectedLoanTotal + "\nActual:"
									+ actualLoanTotal, true);

		}
	}

	
	/**
	 * <pre>
	 * Method to Verify  Loan Amount is Matching in Confirmation Page
	 *
	 * </pre>
	 *
	 *//*
	public void verifyLoanAmountInConfirmationPageForRefinance() {

		String expectedLoanTotal = RequestLoanPage.getTotalLoanAmount();
		String actualLoanTotal = getLoanAmountFromLoanConfirmationPage().replace(",", "");

		if (actualLoanTotal.contains(expectedLoanTotal)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'Loan Total' Amount is Same",
					"'Loan Total' Amount is Same\nLoan Total="
							+ actualLoanTotal, false);

		} else {

			lib.Reporter
					.logEvent(Status.FAIL,
							"Verify 'Loan Total' Amount is Same",
							"'Loan Total' Amount is Not Same\nExpected:"
									+ expectedLoanTotal + "\nActual:"
									+ actualLoanTotal, true);

		}
	}
*/
	/**
	 * <pre>
	 * Method to Verify Loan Request Received Section in Confirmation Page for General Purpose Loan
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String  verifyLoanRequestRecievedSectionForRegularMail()
			throws Exception {

		isTextFieldDisplayed("Loan request received");
//		isTextFieldDisplayed("Processing");
		isTextFieldDisplayed("Check sent by Regular mail");
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getCurrentDate("M/d/yyyy");
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is Same\nDate: " + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is not Same\nExpected Date:"
							+ expectedDate + "\nActual Date:" + date, true);
		}

		verifyWebElementIsDisplayed("TEXT COMPLETE");
		verifyWebElementIsDisplayed("GREEN CHECK MARK");
		isTextFieldDisplayed("Typically 2 business days");
		isTextFieldDisplayed("Delivery up to 5 business days");
		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPageForRefinance();
		return confirmationNo;
	}

	/**
	 * <pre>
	 * Method to Verify Loan Request Received Section in Confirmation Page for Expedited Mail Delivery
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String verifyLoanRequestRecievedSectionforExpeditedMail()
			throws Exception {

		isTextFieldDisplayed("Loan request received");
		isTextFieldDisplayed("Processing");
		isTextFieldDisplayed("Check sent by Expedited mail");
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getCurrentDate("M/d/yyyy");
		System.out.println("system Date:"+expectedDate);
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is Same\nDate:" + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is not Same\nExpected Date:"
							+ expectedDate + "\nActual Date: " + date, true);
		}

		verifyWebElementIsDisplayed("TEXT COMPLETE");
		verifyWebElementIsDisplayed("GREEN CHECK MARK");
		isTextFieldDisplayed("Typically 2 business days");
		isTextFieldDisplayed("Delivery up to 2 business days");
		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPage();
		return confirmationNo;
	}

	/**
	 * <pre>
	 * Method to Verify Loan Request Received Section in Confirmation Page for ACH Delivery
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String verifyLoanRequestRecievedSectionforACHDelivery()
			throws Exception {

		isTextFieldDisplayed("Loan request received");
		isTextFieldDisplayed("Processing");
		isTextFieldDisplayed("Funds sent by ACH");
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getCurrentDate("M/d/yyyy");
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is Same\nDate:" + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is not Same\nExpected Date:"
							+ expectedDate + "\nActual Date: " + date, true);
		}

		verifyWebElementIsDisplayed("TEXT COMPLETE");
		verifyWebElementIsDisplayed("GREEN CHECK MARK");
		isTextFieldDisplayed("Typically 2 business days");
		isTextFieldDisplayed("Delivery up to 3 business days");
		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPage();
		return confirmationNo;
	}

	public  String getInterestRate() {
		return interestRate;
	}

	public  void setInterestRate(String interestRate) {
		RequestLoanPage.interestRate = interestRate;
	}

	public  String getCheckTotal() {
		return checkTotal;
	}

	public  void setCheckTotal(String checkTotal) {
		RequestLoanPage.checkTotal = checkTotal;
	}
	public  String getLoanType() {
		return loanType;
	}

	public  void setLoanType(String loanType) {
		RequestLoanPage.loanType = loanType;
	}

	/**
	 * Method to get the ACH Account Numbers
	 * 
	 * @param ssn
	 * @return accounts
	 * @throws SQLException
	 */
	public static List<String> getACHAccounts(String ssn) throws SQLException {

		// query to get the no of ACH Accounts
		List<String> accounts = new ArrayList<String>();
		String[] sqlQuery = null;
		ResultSet Plan = null;

		try {
			sqlQuery = Stock.getTestQuery("getACHAccountNumbers");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
		try {
			while (Plan.next()) {

				accounts.add(Plan.getString("acct_nbr"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query get ACH Acoount Numbers:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}

		return accounts;
	}

	/**
	 * <pre>
	 * Method to Verify ACH Account Numbers
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public void verifyACHAccountNumbersinDropDown(String ssn) throws Exception {

		Web.waitForElement(drpACHAcoount);
		int actualAccounts = Web.getDropDownOptionCount(new RequestLoanPage(),
				"ACH ACCOUNT DROP DOWN");
		if(Web.getDropDownOptionAsText(drpACHAcoount, "Select Account").equalsIgnoreCase("Select Account"))
			actualAccounts=actualAccounts-1;
		int expectedAccounts = getACHAccounts(ssn).size();
		List<String> accounts = getACHAccounts(ssn);
		if (actualAccounts == expectedAccounts) {
			for (int i = 0; i < expectedAccounts; i++) {
				StringBuffer buffer = new StringBuffer(accounts.get(i));
				String acc = buffer.reverse().substring(0, 4);
				buffer = new StringBuffer(acc);
				acc = buffer.reverse().toString();
				String drpOption = Web.getDropDownOptionAsText(drpACHAcoount,
						acc, false);
				if (drpOption != null) {

					lib.Reporter.logEvent(Status.PASS,
							"Verify ACH Account is Displayed in Drop Down",
							"'ACH Account' is Displayed in Drop Down\nDrop Down Option:"
									+ drpOption, false);

				} else {

					lib.Reporter.logEvent(Status.FAIL,
							"Verify ACH Account is Displayed in Drop Down",
							"'ACH Account' is Not Displayed in Drop Down\nExpected:"
									+ accounts.get(i) + "\nActual" + drpOption,
							true);
				}
			}
		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify No.Of ACH Accounts in DropDown",
					"'No.Of ACH Accounts in DropDown are not Same\nExpected:"
							+ expectedAccounts + "\nActual:" + actualAccounts,
					true);
		}
	}

	/**
	 * <pre>
	 * Method to Select ACH Account
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public void selectACHAccountNumber() throws Exception {

		Web.waitForElement(drpACHAcoount);

		Web.selectDropnDownOptionAsIndex(drpACHAcoount, "1");
	}

	/**
	 * <pre>
	 * Method to Verify Warning messages for Address change in request loan page
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public void verifyAddressChangeBanner() throws Exception {

		Web.waitForElement(txtWar1Addresschange);
		String actualText = txtWar1Addresschange.getText().toString().trim();
		String date = Common.getFutureDate("M/dd/yyyy", 14);
		String expectedText = "Due to a recent change to your account, a temporary hold is on your account until "
				+ date
				+ ". If you complete this loan request, it will be processed after the hold is removed.";

		if (txtWar1Addresschange.isDisplayed()) {
			if (actualText.equalsIgnoreCase(expectedText)) {

				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Warning Message for Address Change is Displayed",
								"Warning Message for Address Change is Displayed\nWarning Message:"
										+ actualText, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Warning Message for Address Change is Displayed",
								"Warning Message for Address Change is not Matching\nExpected:"
										+ expectedText + "\nActual"
										+ actualText, true);
			}

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Warning Message for Address Change is Displayed",
							"Warning Message for Address Change is Not Displayed",
							true);
		}

		if (txtWar2Addresschange.isDisplayed()) {
			actualText = txtWar2Addresschange.getText().toString().trim();
			expectedText = "Alternatively, to expedite your loan request, you can choose to complete a form and return it with a notarized signature. The form will be emailed to you once you complete your loan request.";
			if (actualText.equalsIgnoreCase(expectedText)) {

				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Warning Message for Address Change is Displayed",
								"Warning Message for Address Change is Displayed\nWarning Message:"
										+ actualText, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Warning Message for Address Change is Displayed",
								"Warning Message for Address Change is not Matching\nExpected:"
										+ expectedText + "\nActual"
										+ actualText, true);
			}

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Warning Message for Address Change is Displayed",
							"Warning Message for Address Change is Not Displayed",
							true);
		}
	}

	/**
	 * <pre>
	 * Method to Verify Radio Buttons in Address change section in request loan page
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public void verifyAddressChangeSectionInLaonPage() throws Exception {

		Web.waitForElement(inpProcessLoanAfterHold);
		verifyWebElementIsDisplayed("INPUT PROCESS LOAN AFTER HOLD");
		verifyWebElementIsDisplayed("INPUT EMAIL A FORM");
		
		String actualText = txtProcessLoanAfterHold.getText().toString().trim();

		String expectedText = "Process my loan after the hold is removed.";

		if (actualText.equalsIgnoreCase(expectedText)) {

			lib.Reporter
					.logEvent(
							Status.PASS,
							"Verify Text Field 'Process my loan after the hold is removed.' is Displayed",
							"Text Field 'Process my loan after the hold is removed.' is Displayed",
							false);

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Text Field 'Process my loan after the hold is removed.' is Displayed",
							"Text Field 'Process my loan after the hold is removed.' is Not Displayed",
							true);
		}
		actualText = txtEmailForm.getText().toString().trim();

		 expectedText = " Email a form to me, which I’ll complete, get notarized, and return.";

		if (actualText.equalsIgnoreCase(expectedText)) {

			lib.Reporter
					.logEvent(
							Status.PASS,
							"Verify Text Field ' Email a form to me, which I’ll complete, get notarized, and return.' is Displayed",
							"Text Field ' Email a form to me, which I’ll complete, get notarized, and return.' is Displayed",
							false);

		} else {

			lib.Reporter
					.logEvent(
							Status.FAIL,
							"Verify Text Field ' Email a form to me, which I’ll complete, get notarized, and return.' is Displayed",
							"Text Field ' Email a form to me, which I’ll complete, get notarized, and return.' is Not Displayed",
							true);
		}
	}
	/**
	 * <pre>
	 * Method to Verify Loan Request Received Section Confirmation Page for Future Dated Loan
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String verifyLoanRequestRecievedSectionForFutureDatedLoan()
			throws Exception {

		isTextFieldDisplayed("Loan request received");
	//	isTextFieldDisplayed("Processing");
		isTextFieldDisplayed("Check sent by Regular mail");
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getFutureDate("M/d/yyyy", 14);
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Request Received Date",
					"'Loan Request Received Date is After 14 days\nDate:" + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Request Received Date",
					"'Loan Request Received Date is Not After 14 days\nExpected Date:"
							+ expectedDate + "\nActual Date:" + date, true);
		}

		verifyWebElementIsDisplayed("TEXT COMPLETE");
		verifyWebElementIsDisplayed("GREEN CHECK MARK");
		isTextFieldDisplayed("Typically 2 business days");
		isTextFieldDisplayed("Delivery up to 5 business days");
		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyInterestRateInConfirmationPage();
//		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPageForRefinance();
		return confirmationNo;
	}
	
	/**
	 * <pre>
	 * Method to Verify Loans Disclaimer
	 *
	 * </pre>
	 */
	public void verifyPrincipalResidenceLoanDisclaimer() throws SQLException {

		if (txtPrincipalloanDisclaimer1.getText().toString().trim()
				.equals(Stock.GetParameterValue("PrincipalResidenceDisclaimer1").trim()))
			Reporter.logEvent(Status.PASS,
					"Verify Principal Residence Loans Diclaimer is displayed",
					" Principal Residence Loans Diclaimer is displayed"
							+ txtPrincipalloanDisclaimer1.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Principal Residence Loans Diclaimer is displayed",
					" Principal Residence Loans Diclaimer is not matching \nExpected:"
							+ Stock.GetParameterValue("PrincipalResidenceDisclaimer1")
							+ "\nActual:"
							+ txtPrincipalloanDisclaimer2.getText().toString().trim(),
					true);

		if (txtPrincipalloanDisclaimer2.getText().toString().trim()
				.equals(Stock.GetParameterValue("PrincipalResidenceDisclaimer2").trim()))
			Reporter.logEvent(Status.PASS,
					"Verify Principal Residence Loans Diclaimer is displayed",
					" Principal Residence Loans Diclaimer is displayed"
							+ txtPrincipalloanDisclaimer2.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Principal Residence Loans Diclaimer is displayed",
					" Principal Residence Loans Diclaimer is not matching \nExpected:"
							+ Stock.GetParameterValue("PrincipalResidenceDisclaimer2")
							+ "\nActual:"
							+ txtPrincipalloanDisclaimer2.getText().toString().trim(),
					true);

		if (txtPrincipalloanDisclaimer3.getText().toString().trim()
				.equals("Purchase agreement"))
			Reporter.logEvent(Status.PASS,
					"Verify Purchase agreement text is displayed",
					" Purchase agreement text is displayed"
							+ txtPrincipalloanDisclaimer3.getText().toString().trim(),
					false);

		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Purchase agreement text is displayed",
					" Purchase agreement text is displayed"
							+ txtPrincipalloanDisclaimer3.getText().toString().trim(),
					true);
		verifyContinueButtonIsEnabled(true);
		verifyBackButtonIsEnabled(true);

	}
	
	/**
	 * <pre>
	 * Method to Verify Loan Request Received Section in Confirmation Page for Principal Residence Loan
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String  verifyPrincipalResidenceLoanRequestRecievedSectionForRegularMail()
			throws Exception {
	/*	isTextFieldDisplayed("Your loan request has been received and a pre-filled form will be emailed to you shortly. "
				+ "To complete the process, you must sign and return the form to the address provided. Depending on your preference, "
				+ "you will receive an email and/or text with your loan's status.");
	*/	
		isTextFieldDisplayed("Print, sign, and return the pre-filled form that will be emailed to you at the end of this process.");
		
		
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getCurrentDate("M/d/yyyy");
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is Same\nDate: " + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is not Same\nExpected Date:"
							+ expectedDate + "\nActual Date:" + date, true);
		}

		isTextFieldDisplayed("Request form received");
		String expectedText="Being reviewed for completeness";
		String actualText=txtBeingReviewed.getText().toString().trim();
		if (expectedText.equals(actualText)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Request form received section",
					"Text Field '"+expectedText+"' is Displayed",
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Request form received section",
					"Text Field '"+expectedText+"' is Not Matching", true);
		}
		isTextFieldDisplayed("Form review complete");
	
		 expectedText="Loan request in process";
		 actualText=txtLoanReqInProcess.getText().toString().trim();
			if (expectedText.equals(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify 'Form Review Complete' section",
						"Text Field '"+expectedText+"' is Displayed",
						false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify 'Form Review Complete' section",
						"Text Field '"+expectedText+"' is Not Matching", true);
			}
			expectedText="Typically 2 business days";
			 actualText=txtTypically2BDays.getText().toString().trim();
				if (expectedText.equals(actualText)) {
					lib.Reporter.logEvent(Status.PASS,
							"Verify 'Form Review Complete' section",
							"Text Field '"+expectedText+"' is Displayed",
							false);

				} else {

					lib.Reporter.logEvent(Status.FAIL,
							"Verify 'Form Review Complete' section",
							"Text Field '"+expectedText+"' is Not Matching", true);
				}
		
	
		isTextFieldDisplayed("Check sent by Regular mail");
		expectedText="Delivery up to 5 business days";
		 actualText=txtDeliveryUpTo5BDays.getText().toString().trim();
			if (expectedText.equals(actualText)) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify 'Check Sent by Regular Mail' section",
						"Text Field '"+expectedText+"' is Displayed",
						false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify 'Check Sent by Regular Mail' section",
						"Text Field '"+expectedText+"' is Not Matching", true);
			}
	
			verifyLoanDetailsSectionConfirmationPage();
		return confirmationNo;
	}
	

	/**
	 * <pre>
	 * Method to Verify  Loan Details Section in Confirmation Page
	 *
	 * </pre>
	 * @throws Exception 
	 *
	 */
	public void verifyLoanDetailsSectionConfirmationPage() throws Exception {

		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyLoanMaturityDateInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyAPRinConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPage();
		verifyTotalInterestAmountInConfirmationpage();
		verifyTotalPrincipalAndInterestAmount();
		
	}
	
	/**
	 * <pre>
	 * Method to Verify  Loan Details Section in Confirmation Page For GP Loan Refinance
	 *
	 * </pre>
	 * @throws Exception 
	 *
	 */
	public void verifyLoanDetailsSectionConfirmationPageForLoanRefinance() throws Exception {

		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyLoanMaturityDateLoanRefinanceInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyAPRinConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPageForRefinance();
		verifyTotalInterestAmountInConfirmationpage();
		verifyTotalPrincipalAndInterestAmountLoanRefinance();
		
	}
	
	/**
	 * @author bbndsh
	 */
	private void verifyAPRinConfirmationPage() {
		if(txtAPR.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"Verify APR","APR value is displayed: "+txtAPR.getText().toString(),
					false);
		} else {
			lib.Reporter.logEvent(Status.FAIL,
					"Verify APR","APR value is not displayed: ", true);
		}		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyTotalInterestAmountInConfirmationpage() {
		if(txtInterestAmount.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Total Interest Amount","Total Interest Amount value is displayed: "+txtInterestAmount.getText().toString(),
					false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Total Interest Amount","Total Interest Amount value is not displayed: "
			, true);
		}
		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyTotalPrincipalAndInterestAmount() {
		String loanAmount = "$"+Stock.GetParameterValue("LoanTotal")+".00";
		String interestAmount = txtInterestAmount.getText().toString().trim();
		
		double deliveryFee  = Web.getIntegerCurrency(loanAmount) ;
		double originationFee = Web.getIntegerCurrency(interestAmount);
		double totalFee = deliveryFee + originationFee;
		
		String expectedTotalPrincipalAndInterestAmount = "$" + Double.toString(totalFee); 
		String actualTotalPrincipalAndInterestAmount = null;
		
		if(txtTotalPrincipalAndInterestAmount.isDisplayed()){
			actualTotalPrincipalAndInterestAmount = txtTotalPrincipalAndInterestAmount.getText().toString().trim().replaceAll(",", "");
			if(expectedTotalPrincipalAndInterestAmount.contains(actualTotalPrincipalAndInterestAmount)){
				lib.Reporter.logEvent(Status.PASS,
						"Verify Total Principal And Interest Amount",
						"Total Principal And Interest Amount is displayed as Expected: "+expectedTotalPrincipalAndInterestAmount,
						false);
			}else{
				lib.Reporter.logEvent(Status.FAIL,
						"Verify Total Principal And Interest Amount",
						"Total Principal And Interest Amount is displayed as Expected: "+
						"\nActual Value: "+actualTotalPrincipalAndInterestAmount+
						"\nExpected Value: "+expectedTotalPrincipalAndInterestAmount,
						true);
			}
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Total Principal And Interest Amount","Total Principal And Interest Amount is not Displayed",false);
		}
		
	}
	
	/**
	 * @author bbndsh
	 */
	private void verifyTotalPrincipalAndInterestAmountLoanRefinance() {
		
		double loanAmount  = Web.getIntegerCurrency(txtLoanAmount.getText().toString().trim()) ;
		double interestAmount = Web.getIntegerCurrency( txtInterestAmount.getText().toString().trim());
		double totalPrincipalInterestAmount = loanAmount + interestAmount;
		
		String expectedTotalPrincipalAndInterestAmount = "$" + Double.toString(totalPrincipalInterestAmount); 
		String actualTotalPrincipalAndInterestAmount = null;
		
		if(txtTotalPrincipalAndInterestAmount.isDisplayed()){
			actualTotalPrincipalAndInterestAmount = txtTotalPrincipalAndInterestAmount.getText().toString().trim().replaceAll(",", "");
			if(expectedTotalPrincipalAndInterestAmount.contains(actualTotalPrincipalAndInterestAmount)){
				lib.Reporter.logEvent(Status.PASS,
						"Verify Total Principal And Interest Amount",
						"Total Principal And Interest Amount is displayed as Expected: "+expectedTotalPrincipalAndInterestAmount,
						false);
			}else{
				lib.Reporter.logEvent(Status.FAIL,
						"Verify Total Principal And Interest Amount",
						"Total Principal And Interest Amount is displayed as Expected: "+
						"\nActual Value: "+actualTotalPrincipalAndInterestAmount+
						"\nExpected Value: "+expectedTotalPrincipalAndInterestAmount,
						true);
			}
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Total Principal And Interest Amount","Total Principal And Interest Amount is not Displayed",false);
		}
		
	}

	/**
	 * Maturity Date for Princiapl Residence
	 * @author bbndsh
	 */
	private void verifyLoanMaturityDateInConfirmationPage() {
		String date = null;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, Integer.valueOf(Stock.GetParameterValue("loanTerm")));
		date = c.get(Calendar.MONTH+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR);		
		
		String expectedDate = date;
		String actualDate = null;
		
		if(txtMaturityDate.isDisplayed()){			
				actualDate = txtMaturityDate.getText().toString().trim();
				if(actualDate.equalsIgnoreCase(expectedDate)){
					lib.Reporter.logEvent(Status.PASS,
					"Verify Maturity Date",
					"Maturity Date is displayed as Expected: "+expectedDate,
					false);
					setMaturityDate(expectedDate);
				} else {
					lib.Reporter.logEvent(Status.FAIL,
					"Verify Maturity Date",
					"Maturity Date is not displayed as expected"+"\nExpected Date: "+expectedDate+"\nActual Date: "+actualDate, true);
				}
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Maturity Date","Maturity Date is not Displayed",true);
		}
		
		
		
	}
	
	/**
	 * Maturity Date for General Purpose Loan refinance
	 * @author bbndsh
	 */
	private void verifyLoanMaturityDateLoanRefinanceInConfirmationPage() {
		Date date = null;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, Integer.valueOf(Stock.GetParameterValue("loanTerm")));
		date = c.getTime();		
		
		String expectedDate = new SimpleDateFormat("M/dd/yyyy").format(date);		
		String actualDate = null;
		
		if(txtMaturityDate.isDisplayed()){			
				actualDate = txtMaturityDate.getText().toString().trim();
				if(actualDate.equalsIgnoreCase(expectedDate)){
					lib.Reporter.logEvent(Status.PASS,
					"Verify Maturity Date",
					"Maturity Date is displayed as Expected: "+expectedDate,
					false);
					RequestLoanPage.maturityDate = expectedDate;
				} else {
					lib.Reporter.logEvent(Status.FAIL,
					"Verify Maturity Date",
					"Maturity Date is not displayed as expected"+"\nExpected Date: "+expectedDate+"\nActual Date: "+actualDate, true);
				}
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Maturity Date","Maturity Date is not Displayed",true);
		}
		
		
		
	}

	/**
	 * <pre>
	 * Method to Verify  Payment Information Section in Confirmation Page
	 *
	 * </pre>
	 * @throws Exception 
	 *
	 */
	public void verifyPaymentInformationSectionInConfirmationPage() throws Exception {

		verifyFirstPaymentDueInLoanSummaryPage();
		verifyLastPaymentDueInLoanSummaryPage();
		verifyNumberOfPaymentsInLoanSummaryPage();
		verifyPaymentAmountInLoanSummaryPage();		
		verifyPaymentMethodInLoanSummaryPage();
		verifyPaymentFrequencyInLoanSummaryPage();
		
	}
	
	/**
	 * @author bbndsh
	 */
	private void verifyPaymentFrequencyInLoanSummaryPage() {
		if(txtPaymentFrequency.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"\nVerifying Payment Frequency", "Payment Frequency is displayed: "+txtPaymentFrequency.getText().toString(),
					false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"\nVerifying Payment Frequency", "Payment Frequency is not displayed: ",
					false);
		}
		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyPaymentMethodInLoanSummaryPage() {
		if(txtPaymentMethod.isDisplayed()){
			String paymentMethod = txtPaymentMethod.getText().toString().trim();
			if(paymentMethod.equalsIgnoreCase("PAYROLL") || (paymentMethod.equalsIgnoreCase("CHECK"))){
				lib.Reporter.logEvent(Status.PASS,
						"\nVerifying Payment method", "Payment method is displayed: "+paymentMethod, false);
			}else{
				lib.Reporter.logEvent(Status.FAIL,
						"\nVerifying Payment method", "Payment method is not displayed as expected: "+
								"\nExpected: "+"PAYROLL or CHECK"+"\nActual: "+paymentMethod, true);
			}
		}
		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyPaymentAmountInLoanSummaryPage() {
		String actualPaymentAmount = null;
		String expectedPaymentAmount = RequestLoanPage.paymentAmount;
		if(txtPaymentAmount.isDisplayed()){
			actualPaymentAmount = txtPaymentAmount.getText().toString().trim();
			if(expectedPaymentAmount.contains(actualPaymentAmount)){
				lib.Reporter.logEvent(Status.PASS,
						"Verify Payment Amount is displayed and is equal to Amount selected in Loan Quote Page",
						"Payment Amount is displayed and is equal to Amount selected in Loan Quote Page: "+expectedPaymentAmount
						,false);
			}else{
				lib.Reporter.logEvent(Status.FAIL,
						"Verify Payment Amount is displayed and is equal to Amount selected in Loan Quote Page",
						"Payment Amount is not equal to Amount selected in Loan Quote Page: "+
						"\nExpected: "+expectedPaymentAmount+"\nActual: "+actualPaymentAmount
						,true);
			}
		}
	}

	/**
	 * @author bbndsh
	 */
	private void verifyNumberOfPaymentsInLoanSummaryPage() {
		
		if(txtNumberOfPayments.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Number of Payments",
					"Number of Payments is Displayed "+txtNumberOfPayments.getText().toString().trim(),
					false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Number of Payments",
					"Number of Payments is not Displayed ",
					true);
		}
		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyLastPaymentDueInLoanSummaryPage() {
		
		String actualLastDueDate = txtLastPaymentDue.getText().toString().trim();
		
		if(txtLastPaymentDue.isDisplayed()){
			if(getMaturityDate().toString().trim().equalsIgnoreCase(actualLastDueDate)){
				lib.Reporter.logEvent(Status.PASS,
						"Verify Last Payment Due Date",
						"Last Payment Due Date is Displayed as expected "+RequestLoanPage.maturityDate,
						false);
			}else{
				lib.Reporter.logEvent(Status.FAIL,
						"Verify Last Payment Due Date",
						"Last Payment Due Date is not Displayed as expected "+
						"\nExpected Date: "+RequestLoanPage.maturityDate+"\nActual Date: "+actualLastDueDate,
						true);
			}
		}
		
	}

	/**
	 * @author bbndsh
	 */
	private void verifyFirstPaymentDueInLoanSummaryPage() {
		if(txtLastPaymentDue.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"Verify First Payment Due Date",
					"First Payment Due Date is Diplayed: "+txtLastPaymentDue.getText().toString(),
					false);
		}else{			
			lib.Reporter.logEvent(Status.FAIL,
						"Verify First Payment Due Date",
						"First Payment Due Date is not Diplayed ",
						true);
			
		}
		
	}

	/**
	 * <pre>
	 * Method to Verify Fees And Taxes Section in Confirmation Page
	 *
	 * </pre>
	 * @throws Exception 
	 *
	 */
	public void verifyFeesAndTaxesSectionInConfirmationPage() throws Exception {
		verifyOriginationFeeFromLoansLandingPage();

		
	}
	
	private void verifyOriginationFeeFromLoansLandingPage() {
		
		String expectedOriginationFeeValue = this.originationFee;
		String actualOriginationFeeValue = txtOriginationFeeInConfirmationPage.getText().trim();
		
		if(expectedOriginationFeeValue.equalsIgnoreCase(actualOriginationFeeValue)){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Origination Fee",
					"Origination fee is displayed as same as in Loans landing Page: "+expectedOriginationFeeValue,
					false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Origination Fee",
					"Origination fee is displayed is not same as in Loans landing Page: "+
					"\nExpected: "+expectedOriginationFeeValue+"\nActual: "+actualOriginationFeeValue,
					true);
		}

		
	}
	
	/*private void verifyOriginationFeeFromLoansLandingPage() {
		
		String expectedOriginationFeeValue = this.originationFee;
		String actualOriginationFeeValue = txtOriginationFeeInConfirmationPage.getText().trim();
		
		if(expectedOriginationFeeValue.equalsIgnoreCase(actualOriginationFeeValue)){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Origination Fee",
					"Origination fee is displayed as same as in Loans landing Page: "+expectedOriginationFeeValue,
					false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Origination Fee",
					"Origination fee is displayed is not same as in Loans landing Page: "+
					"\nExpected: "+expectedOriginationFeeValue+"\nActual: "+actualOriginationFeeValue,
					true);
		}
		
	}*/

	/**
	 * <pre>
	 * Method to Verify Delivery Information Section in Confirmation Page
	 *
	 * </pre>
	 * @throws Exception 
	 *
	 */
	public void verifyDeliveryInformationSectionInConfirmationPage() throws Exception {

		verifyDeliveryMethod();
		verifyMailingAddressForPptFromDB(Stock.GetParameterValue("SSN"));
		
	}
	
	private void verifyDeliveryMethod(){
		
		String expectedDeliveryMethod = getDeliveryMethodSelected();
		String actualDeliveryMethod = null;
		
		if(txtDeliveryMethodInConfirmationPage.isDisplayed()){
			actualDeliveryMethod = txtDeliveryMethodInConfirmationPage.getText().toString().trim();
			if(expectedDeliveryMethod.equalsIgnoreCase(actualDeliveryMethod)){
				lib.Reporter.logEvent(Status.PASS, "verifying Delivery method in Confirmation page", 
						"Delivery method is same as expected "+expectedDeliveryMethod, false);
			}else{
				lib.Reporter.logEvent(Status.FAIL, "verifying Delivery method in Confirmation page", 
						"Delivery method is not same as in Request loan type page"+
								"\nExpected: "+expectedDeliveryMethod+"\nActual: "+actualDeliveryMethod, true);
			}
		}
	}
	
	private void verifyMailingAddressForPptFromDB(String ssn) throws SQLException {
		String expectedAddress = null;
		String actualAddress = null;
		
		expectedAddress = getAddressForPptFromDB(ssn).replaceAll(",", "");
		if(mailingAddressValue.isDisplayed()){
			actualAddress = mailingAddressValue.getText().toString().replaceAll("\n", " ").replaceAll(",", "");
		}
		
		if(expectedAddress.replaceAll(" ", "").contains(actualAddress.replaceAll(" ", ""))){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Mailing Address from DB",
					"Mailing address in Confirmation Page is same as that from DB ADDRESS table: "+expectedAddress, false);
		}else{
			lib.Reporter.logEvent(Status.FAIL,
					"Verify Mailing Address from DB",
					"Mailing address in Confirmation Page is same as that from DB ADDRESS table: "+
					"\nExpected:"+ expectedAddress+"\nActual: "+actualAddress, true);
		}
		
	}
	

	private String getAddressForPptFromDB(String ssn) throws SQLException {
		
				String[] sqlQuery = null;
				ResultSet resultSet = null;
				String address = "";

				try {
					sqlQuery = Stock.getTestQuery("getMailingAddressForPpt");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				resultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
				try {
					if (DB.getRecordSetCount(resultSet) > 0) {
						resultSet.first();
						address = resultSet.getString("LOC");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query get PlanName:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}

				return address;
	}

	

	/**
	 * <pre>
	 * Method to Verify Default Repayment Term Options for Principal Residence Loan
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyDefaultRepamentTermForPrinciplaResidence() {
		String[] repaymentTerm = { "5years", "10years", "15years",
				"20years", "25years" };
		for (int i = 0; i < repaymentTerm.length; i++) {
			List<WebElement> inpLoanTerm = Web.getDriver().findElements(
					By.xpath(strRepaymentTerm.replace("rownum",
							Integer.toString(i+1))));
	//		int j = inpLoanTerm.size();
			String actualLoanTerm = inpLoanTerm.get(0).getText().toString()
					.trim()
					+ inpLoanTerm.get(1).getText().toString().trim();

			if (repaymentTerm[i].equalsIgnoreCase(actualLoanTerm)) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term " + (i+1)+" :" + actualLoanTerm, false);

			} else {

				lib.Reporter
						.logEvent(
								Status.FAIL, 
								"Verify Repayment Term is Displayed in Ascending Order",
								"Repayment Term is not displayed as expected\nExpected:"
										+ repaymentTerm[i] + "\nActual:"
										+ actualLoanTerm, true);

			}
		}
	}
	/**
	 * <pre>
	 * Method to Verify Each Loan Term Have a Radio Button associated with it
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifySelectColumnForPrincipalResidenceLoanTerm() {
		String[] repaymentTerm = { "5", "10", "15", "20", "25" };
		for (int i = 0; i < repaymentTerm.length; i++) {
			WebElement inpLoanTerm = Web.getDriver().findElement(
					By.xpath(this.loanTerm.replace("Repayment Term",
							repaymentTerm[i])));

			if (inpLoanTerm.isDisplayed()) {
				lib.Reporter.logEvent(Status.PASS,
						"Verify Radio Button is Displayed for Loan Term",
						"Radio Button is Displayed for Loan Term "
								+ repaymentTerm[i] + " Years", false);

			} else {

				lib.Reporter.logEvent(Status.FAIL,
						"Verify Radio Button is Displayed for Loan Term",
						"Radio Button is Not Displayed for Loan Term "
								+ repaymentTerm[i] + " Years", true);

			}
		}
	}
		/**
	 * <pre>
	 * Method to Verify Loan Request Received Section in Confirmation Page for Principal Residence Loan
	 *
	 * </pre>
	 * 
	 * @throws Exception
	 *
	 */
	public String  verifyPrincipalResidenceLoanRequestRecievedSectionForACH() throws Exception {
		isTextFieldDisplayed("Print, sign, and return the pre-filled form that will be emailed to you at the end of this process.");
		isTextFieldDisplayed("Request form received");
		isTextFieldDisplayed("Form review complete");
		isTextFieldDisplayed("Funds sent by ACH");
		String confirmationtext = txtConfirmationNumber.getText().toString()
				.trim();
		String confirmationNo = confirmationtext.split(":")[1].split("\n")[0].toString().trim();
		String date = confirmationtext.split(":")[1].split("\n")[1].toString().trim();
		verifyWebElementIsDisplayed("Loan Confirmation Number And Date");
		String expectedDate = Common.getCurrentDate("M/d/yyyy");
		if (date.equalsIgnoreCase(expectedDate)) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is Same\nDate: " + expectedDate,
					false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify Loan Submittion Date is Current date",
					"'Loan Submittion Date is not Same\nExpected Date:"
							+ expectedDate + "\nActual Date:" + date, true);
		}

		
		isTextFieldDisplayed("Typically 2 business days");
		isTextFieldDisplayed("Delivery up to 3 business days");
		verifyPlanName(Stock.GetParameterValue("gc_id"));
		verifyLoanTypeInConfirmationPage();
		verifyLoanTermInConfirmationPage();
		verifyInterestRateInConfirmationPage();
		verifyCheckAmountInConfirmationPage();
		verifyLoanAmountInConfirmationPage();
		return confirmationNo;
	}

	public void verifyLoanReasons() {
		
	//	verifyWebElementIsDisplayed("txtLoanReasons");
		if(txtLoanReasons.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS,
					"Verify Loan Reasons is Displayed",
					"Loan Reasons Displayed: " ,	false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify LoanLoan Reasons is Displayed",
					"Loan Reasons not Displayed: " ,	false);
		}
		
	}

	public void clickLoanReasonOptionPreFilled() {
		if(Web.isWebElementDisplayed(btnLoanReasonsPreFilledForm, true)){
			Web.clickOnElement(btnLoanReasonsPreFilledForm);
		}
	}
	
	public void clickLoanReasonOptionFutureDate() {
		if(Web.isWebElementDisplayed(btnLoanReasonsFutureDate, true)){		
			Web.clickOnElement(btnLoanReasonsFutureDate);
		}
	}

	
	public void validateToolTipForInterestRate()
			throws InterruptedException {
		
		String expectedtext1 = null;
		WebElement ele = this.getWebElement("INTEREST RATE");

		keyBoardEvent.moveToElement(ele).build().perform();
		Thread.sleep(3000);

		String expectedtext = "Interest rate I pay to myself";
		String actualText = toolTipInterestRateText1.getText().toString()
				.trim();

		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is not displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext1 = "Rate PRIME + 1";
		expectedtext =  "Rate PRIME + 0";
		actualText = toolTipInterestRateText2.getText().toString().trim() + " "
				+ toolTipInterestRateText3.getText().toString().trim();
		
		if (expectedtext.equalsIgnoreCase(actualText) || (expectedtext1.equalsIgnoreCase(actualText))) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is displayed for INTEREST RATE\nExpected: "
							+ expectedtext +"/"+expectedtext1 + "\nActual: " + actualText, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is not displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext = "Type FIXED";
		expectedtext1 = "Type VARIABLE";
		actualText = toolTipInterestRateText4.getText().toString().trim() + " "
				+ toolTipInterestRateText5.getText().toString().trim();
		
		if (expectedtext.equalsIgnoreCase(actualText) || (expectedtext1.equalsIgnoreCase(actualText))) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is displayed for INTEREST RATE\nExpected: "
							+ expectedtext +"/"+expectedtext1+ "\nActual: " + actualText, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for INTEREST RATE is displayed",
					"Tooltip Text is not displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}
		
			
	}
	
		public void validateToolTipForFee() throws InterruptedException {
		
		WebElement ele = this.getWebElement("FEES*");
		String sActualText = null;
		String sExpectedText = ele.getText();
	
		if(inpRegularMail.isSelected()){
			
			keyBoardEvent.moveToElement(ele).build().perform();
			Thread.sleep(3000);
				
			sActualText = toolTipFeeOriginationValue.getText();					
			
			if(sExpectedText.equalsIgnoreCase(sActualText)){
				Reporter.logEvent(Status.PASS, "Verify ToolTip Text for FEES* is displayed",
					"Tooltip text displayed for FEES* and is equal to Origination Fee as Expected" +
				"\nFEE*: "+ sExpectedText, false);
			}else{				
					Reporter.logEvent(Status.FAIL, "Verify ToolTip Text for FEES* is displayed",
						"Tooltip text displayed for FEES* is not displayed as Expected" + 
					"\nExpected Text: " + sExpectedText+
					"\nActual Text: "+sActualText, true);
			}
			
		}
		
		else{
			
			keyBoardEvent.moveToElement(ele).build().perform();
			Thread.sleep(3000);
				
			double deliveryFee  = Web.getIntegerCurrency(toolTipFeeDeliveryValue.getText()) ;
			double originationFee = Web.getIntegerCurrency(toolTipFeeOriginationValue.getText());
			double totalFee = deliveryFee + originationFee;
			
			sActualText = "$" + Double.toString(totalFee); 
		
			if(sExpectedText.contains(sActualText)){
				Reporter.logEvent(Status.PASS, "Verify ToolTip Text for FEES* is displayed",
					"Tooltip text displayed for FEES* and is equal to sum of Origination Fee and Delivery Fee as Expected" + sExpectedText, false);
			}else{				
					Reporter.logEvent(Status.FAIL, "Verify ToolTip Text for FEES*",
						"Tooltip text displayed for FEES* is displayed as Expected" + 
					"\nExpected Text: " + sExpectedText+
					"\nActual Text: "+sActualText, true);
			}
		}
		}
	
		public String getOriginationFee() {
			return originationFee;
		}

		public void setOriginationFee(String loanType) {
			WebElement originationFeeInRequestTypePage = Web.getDriver().findElement(
					By.xpath(originationFee.replace("loanType", loanType)));
			
			this.originationFee = originationFeeInRequestTypePage.getText().split(" ")[1];
		}

	public void verifyLoansLandingPage() {
		isTextFieldDisplayed("AVAILABLE TO BORROW");
		String loanAmt = getMaximumLoanAmount();
		if(!loanAmt.isEmpty())
			Reporter.logEvent(Status.PASS,
					"Verify Loan Maximum Amount is displayed",
					"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Loan Maximum Amount is displayed",
					"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
	}

	public void verifyActiveLoansForparticipant(String gaId) throws SQLException {
		
		String expectedNoofLoans = getMaximumLoansAllowed();
		String ActualNoofLoans = getMaximumLoansAllowedforPlan(gaId);
		if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
			Reporter.logEvent(Status.PASS,
					"Verify Maximum Loans Allowed is Matching",
					"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Maximum Loans Allowed is Matching",
					"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
		
		
	}

	public void clickOnRequestANewLoan() {
				
		boolean isTextDisplayed = false;
		try {

			isTextDisplayed = Web.isWebElementDisplayed(btnRequestNewLoan, true);

			if (isTextDisplayed)
				Web.clickOnElement(btnRequestNewLoan);
			Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		Web.waitForElement(inputLonatypeGeneral);
	}

	public void verifyLoanRequestTypePage() {
		
		  isTextFieldDisplayed("Loan purpose");
		  isTextFieldDisplayed("Maximum loan");
		  try{
			  isTextFieldDisplayed("Minimum loan");
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  isTextFieldDisplayed("Repayment term");
		  isTextFieldDisplayed("Documentation required");
		  isTextFieldDisplayed("Interest rate");
		  isTextFieldDisplayed("Repayment");
		  isTextFieldDisplayed("Fees");
		  try{
			  isTextFieldDisplayed("Waiting period");
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  verifyLoneTypeisDisplayed("GENERAL PURPOSE");
		  verifyLoneTypeisDisplayed("MORTAGAGE");
		
	}

	public void verifyMyLoanSummarySection() {
		
		isTextFieldDisplayed("My loan summary");
		isTextFieldDisplayed("INTEREST RATE");
		isTextFieldDisplayed("FEES*");
		isTextFieldDisplayed("CHECK TOTAL");
		isTextFieldDisplayed("LOAN TOTAL");
		verifyLoanTotalAmount();
		setCheckTotal(getCheckTotalFromSummaryTable());
		setInterestRate(getInterestRateFromLoanSummaryTable());
		
	}

	public void verifyMyLoanSummarySectionForLoanRefinance() {
		
		isTextFieldDisplayed("My loan summary");
		isTextFieldDisplayed("INTEREST RATE");
		isTextFieldDisplayed("FEES*");
		isTextFieldDisplayed("CHECK TOTAL");
		isTextFieldDisplayed("LOAN TOTAL");
		verifyLoanTotalAmountLoanRefinance();
		verifyCheckTotalLoanRefinance();
		setCheckTotal(getCheckTotalFromSummaryTable());
		
	}

	private void verifyCheckTotalLoanRefinance() {
		double refinanceAmount = Double.valueOf(Stock.GetParameterValue("loanAmount"));
		double fee = Web.getIntegerCurrency(txtOriginationFee.getText().toString().trim());
		double checkTotal = refinanceAmount - fee;
		
		String expectedCheckTotal = "$" + Double.toString(checkTotal);
		String actualCheckTotal = null;
		
		if(txtCheckTotal.isDisplayed()){
			actualCheckTotal = txtCheckTotal.getText().toString().trim();
			if(actualCheckTotal.contains(expectedCheckTotal)){
				Reporter.logEvent(Status.PASS,
						"Verify Check Total Amount",
						"Check Total Amount is displayed: "+expectedCheckTotal, false);
			}else{
				Reporter.logEvent(Status.FAIL,
						"Verify Check Total Amount",
						"Check box 'Update me by email at' is selected"+
						"\nExpected: "+expectedCheckTotal+
						"\nActual: "+actualCheckTotal, true);
			}
		}
		
		
	}

	public void verifyLoanTotalAmountLoanRefinance() {
		double actualCheckTotal = Web.getIntegerCurrency(txtCheckTotal.getText().toString().trim());
		double fee = Web.getIntegerCurrency(txtOriginationFee.getText().toString().trim());
		double currentBalance = Web.getIntegerCurrency(refinanceCurrentBalance.getText().toString().trim());
		
		double totalLoanAmount = (double)actualCheckTotal + fee + currentBalance;
		 DecimalFormat f = new DecimalFormat("##.##");
	     System.out.println(f.format(totalLoanAmount));
	     totalLoanAmount = Double.valueOf(f.format(totalLoanAmount));
		String expectedLoanAmount = "$"+Double.toString(totalLoanAmount);
		String actualLoanAmount = getLoanTotalFromLoanSummaryTable().replaceAll(",", "");
		
		if(expectedLoanAmount.contains(actualLoanAmount)){
			Reporter.logEvent(Status.PASS,
					"Verify Loan Total Amount",
					"Loan Total Amount is displayed: "+expectedLoanAmount, false);
			RequestLoanPage.setTotalLoanAmount(actualLoanAmount);
		}else{
			Reporter.logEvent(Status.FAIL,
					"Verify Loan Total Amount",
					"Check box 'Update me by email at' is selected"+
					"\nExpected: "+expectedLoanAmount+
					"\nActual: "+actualLoanAmount, true);
		}
	}
		

	public void verifyLoanSummarypage() {
		
		verifyPageHeaderIsDisplayed("Header Loan Review");
		
		isTextFieldDisplayed("Loan Details");
		isTextFieldDisplayed("PLAN:");
		isTextFieldDisplayed("LOAN TYPE:");
		isTextFieldDisplayed("TERM:");
		isTextFieldDisplayed("MATURITY DATE:");
		isTextFieldDisplayed("INTEREST RATE:");
		isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
		isTextFieldDisplayed("CHECK AMOUNT:");
		isTextFieldDisplayed("LOAN AMOUNT:");
		isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
		isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
		isTextFieldDisplayed("Payment Information");
		isTextFieldDisplayed("FIRST PAYMENT DUE:");
		isTextFieldDisplayed("LAST PAYMENT DUE:");
		isTextFieldDisplayed("NUMBER OF PAYMENTS:");
		isTextFieldDisplayed("PAYMENT AMOUNT:");
		isTextFieldDisplayed("PAYMENT METHOD:");
		isTextFieldDisplayed("PAYMENT FREQUENCY:");
		isTextFieldDisplayed("Fees and Taxes");
		isTextFieldDisplayed("ORIGINATION FEE:");
		isTextFieldDisplayed("Delivery Information");
		isTextFieldDisplayed("DELIVERY METHOD:");
		isTextFieldDisplayed("MAILING ADDRESS:");
		isTextFieldDisplayed("Loan Provisions");
		
		Web.isWebElementDisplayed(btnIAgreeAndSubmit, true);
		Web.isWebElementDisplayed(btnBack, true);
	}

	public void verifyCheckBoxesInAddressPage() {
		
		if(chkBoxEmail.isSelected()){
			Reporter.logEvent(Status.PASS,
					"Verify 'Update me by email at' check box is selected",
					"Check box 'Update me by email at' is selected", false);
		}else{
			chkBoxEmail.click();
			Reporter.logEvent(Status.PASS,
					"Verify 'Update me by email at' check box is selected",
					"Check box 'Update me by email at' is selected", false);
		}
		
		if(chkBoxTextMsg.isSelected()){
			Reporter.logEvent(Status.PASS,
					"Verify 'Update me by text message at' check box is selected",
					"Check box 'Update me by text message at' is selected", false);
		}else{
			chkBoxTextMsg.click();
			Reporter.logEvent(Status.PASS,
					"Verify 'Update me by text message at' check box is selected",
					"Check box 'Update me by text message at' is selected", false);
		}
		chkBoxTextMsg.click();
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for Mail Hold
	 */
	public void verifyPPTRequestLoanPageWithMailHold() {
		
		String expectedErrorMsg="Due to a problem with your mailing address, you are unable to request a loan at this time. "
				+ "Please contact a participant services representative to complete this transaction.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errAddressHold, true)){
		 actualErrorMsg=errAddressHold.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for Mailing Address is Displayed",
						"Hard Stop Error Message for Mailing Address is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for Mailing Address is Displayed",
						"Hard Stop Error Message for Mailing Address is not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for Mailing Address is Displayed",
					"Error Message for Mailing Address is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();

		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
		
	}

	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_479
	 */
	public void verifyPPTRequestLoanPageWithBR_479() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_479, true)){
		 actualErrorMsg=errBR_479.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_479' is Displayed",
						"Error Message for 'BR_479' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_479' is Displayed",
						"Error Message for 'BR_479' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_479' is Displayed",
					"Error Message for 'BR_479' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_480
	 */
	public void verifyPPTRequestLoanPageWithBR_480() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_480, true)){
		 actualErrorMsg=errBR_480.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_480' is Displayed",
						"Error Message for 'BR_480' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_480' is Displayed",
						"Error Message for 'BR_480' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_480' is Displayed",
					"Error Message for 'BR_480' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_481
	 */
	public void verifyPPTRequestLoanPageWithBR_481() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_481, true)){
		 actualErrorMsg=errBR_481.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_481' is Displayed",
						"Error Message for 'BR_481' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_481' is Displayed",
						"Error Message for 'BR_481' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_481' is Displayed",
					"Error Message for 'BR_481' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_484
	 */
	public void verifyPPTRequestLoanPageWithBR_484() {
		
		String expectedErrorMsg="You are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_484, true)){
		 actualErrorMsg=errBR_484.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_484' is Displayed",
						"Error Message for 'BR_484' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_484' is Displayed",
						"Error Message for 'BR_484' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_484' is Displayed",
					"Error Message for 'BR_484' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_486
	 */
	public void verifyPPTRequestLoanPageWithBR_486() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_486, true)){
		 actualErrorMsg=errBR_486.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_486' is Displayed",
						"Error Message for 'BR_486' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_486' is Displayed",
						"Error Message for 'BR_486' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_486' is Displayed",
					"Error Message for 'BR_486' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_467
	 */
	public void verifyPPTRequestLoanPageWithBR_467() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan. "
				+ "Please call a participant services representative for additional information.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_467, true)){
		 actualErrorMsg=errBR_467.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_467' is Displayed",
						"Error Message for 'BR_467' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_467' is Displayed",
						"Error Message for 'BR_467' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_467' is Displayed",
					"Error Message for 'BR_467' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_468
	 */
	public void verifyPPTRequestLoanPageWithBR_468() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_468, true)){
		 actualErrorMsg=errBR_468.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_468' is Displayed",
						"Error Message for 'BR_468' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_468' is Displayed",
						"Error Message for 'BR_468' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_468' is Displayed",
					"Error Message for 'BR_468' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Request a General Purpose & principle Residence Loan Buttons Are disabled
	 */
	public void verifyRequestLoanButtonsAreDisabled() {
		
		
		
		if(!inputLonatypeGeneral.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify 'Request a General Purpose Loan' Button is disabled",
					"'Request a General Purpose Loan' Button is disabled", false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Request a General Purpose Loan' Button is disabled",
					"'Request a General Purpose Loan' Button is not disabled", true);
		
		if(!inputLonatypeMortgage.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify 'Request a Principal Residence Loan' Button is disabled",
					"'Request a Principal Residence Loan' Button is disabled", false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Request a Principal Residence Loan' Button is disabled",
					"'Request a Principal Residence Loan' Button is not disabled", true);
		
		
	}

	/**
	 * Method to get Pending loans for PPT
	 * 
	 * @param username
	 * @return noofLoans
	 * @throws SQLException
	 */
	public boolean getPendingLoans(String userName) throws SQLException {

		String[] sqlQuery = null;
		ResultSet Plan = null;
		boolean pendingLoan=false;
		try {
			sqlQuery = Stock.getTestQuery("GetPPTPendingLoanRequestData");
		
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], Common.getParticipantID(userName.substring(0, userName.length() - 3)));
	
			if(DB.getRecordSetCount(Plan) > 0)

			pendingLoan=true;
		}	
		 catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query getGetPPTPendingLoanRequestData :" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}

		return pendingLoan;
	
}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_478
	 */
	public void verifyPPTRequestLoanPageWithBR_478() {
		
		String expectedErrorMsg="Due to a pending transaction, you are unable to request a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_478, true)){
		 actualErrorMsg=errBR_478.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_478' is Displayed",
						"Error Message for 'BR_478' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_478' is Displayed",
						"Error Message for 'BR_478' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_478' is Displayed",
					"Error Message for 'BR_478' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_510
	 */
	public void verifyPPTRequestLoanPageWithBR_510() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_510, true)){
		 actualErrorMsg=errBR_510.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_510' is Displayed in Modal Dialog",
						"Error Message for 'BR_510' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_510' is Displayed in Modal Dialog",
						"Error Message for 'BR_510' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_510' is Displayed in Modal Dialog",
					"Error Message for 'BR_510' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_473
	 */
	public void verifyPPTRequestLoanPageWithBR_473() {
		
		String expectedErrorMsg="Based on your plan provisions, you are eligible to take a loan on the basis of hardship only. "
				+ "You are required to complete a loan application and submit with supporting documentation for processing. "
				+ "The form is located in the Plan Information section of your account.";
		String actualErrorMsg="";
		
		if(Web.isWebElementDisplayed(errBR_473, true)){
		 actualErrorMsg=errBR_473.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_473' is Displayed",
						"Error Message for 'BR_473' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_473' is Displayed",
						"Error Message for 'BR_473' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_473' is Displayed",
					"Error Message for 'BR_473' is Not Displayed", true);
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_488
	 */
	public void verifyPPTRequestLoanPageWithBR_488() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan. "
				+ "Please call a participant services representative for additional information.";
		String actualErrorMsg="";
		
		if(Web.isWebElementDisplayed(errBR_488, true)){
		 actualErrorMsg=errBR_488.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_488' is Displayed",
						"Error Message for 'BR_488' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_488' is Displayed",
						"Error Message for 'BR_488' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_488' is Displayed",
					"Error Message for 'BR_488' is Not Displayed", true);
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_485
	 */
	public void verifyPPTRequestLoanPageWithBR_485() {
		
		String expectedErrorMsg="You are not currently eligible to request a loan.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_485, true)){
		 actualErrorMsg=errBR_485.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_485' is Displayed in Modal Dialog",
						"Error Message for 'BR_485' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_485' is Displayed in Modal Dialog",
						"Error Message for 'BR_485' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_485' is Displayed in Modal Dialog",
					"Error Message for 'BR_485' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_474
	 */
	public void verifyPPTRequestLoanPageWithBR_474() {
		
		String expectedErrorMsg="You are not currently eligible to request a loan. "
				+ "Please call a participant services representative for additional information.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_474, true)){
		 actualErrorMsg=errBR_474.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_474' is Displayed in Modal Dialog",
						"Error Message for 'BR_474' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_474' is Displayed in Modal Dialog",
						"Error Message for 'BR_474' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_474' is Displayed in Modal Dialog",
					"Error Message for 'BR_474' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_470
	 */
	public void verifyPPTRequestLoanPageWithBR_470() {
		
		String expectedErrorMsg="You are not eligible to request a loan because you have reached the maximum number of loans allowed.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_470, true)){
		 actualErrorMsg=errBR_470.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_470' is Displayed in Modal Dialog",
						"Error Message for 'BR_470' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_470' is Displayed in Modal Dialog",
						"Error Message for 'BR_470' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_470' is Displayed in Modal Dialog",
					"Error Message for 'BR_470' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
		
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_487
	 */
	public void verifyPPTRequestLoanPageWithBR_487() {
		
		String expectedErrorMsg="You are not eligible to request a loan because you have reached the maximum number of loans allowed.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_487, true)){
		 actualErrorMsg=errBR_487.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_487' is Displayed in Modal Dialog",
						"Error Message for 'BR_487' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_487' is Displayed in Modal Dialog",
						"Error Message for 'BR_487' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_487' is Displayed in Modal Dialog",
					"Error Message for 'BR_487' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_489
	 */
	public void verifyPPTRequestLoanPageWithBR_489() {
		
		String expectedErrorMsg="You are not eligible to request a loan because you have reached the maximum number of loans allowed.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_489, true)){
		 actualErrorMsg=errBR_489.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_489' is Displayed in Modal Dialog",
						"Error Message for 'BR_489' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_489' is Displayed in Modal Dialog",
						"Error Message for 'BR_489' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_489' is Displayed in Modal Dialog",
					"Error Message for 'BR_489' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_471
	 */
	public void verifyPPTRequestLoanPageWithBR_471() {
		
		String expectedErrorMsg="You are not currently eligible to request a loan. Please call a participant services representative for additional information.";
		String actualErrorMsg="";
		Web.clickOnElement(inputLonatypeGeneral);
		Web.waitForElement(modalBR_510);
		if(Web.isWebElementDisplayed(errBR_471, true)){
		 actualErrorMsg=errBR_471.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_471' is Displayed in Modal Dialog",
						"Error Message for 'BR_471' is Displayed in Modal Dialog\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_471' is Displayed in Modal Dialog",
						"Error Message for 'BR_471' is Not Matching in Modal Dialog\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_471' is Displayed in Modal Dialog",
					"Error Message for 'BR_471' is Not Displayed in Modal Dialog", true);
		
		Web.clickOnElement(btnOK);
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for BR_482
	 */
	public void verifyPPTRequestLoanPageWithBR_482() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errBR_482, true)){
		 actualErrorMsg=errBR_482.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for 'BR_482' is Displayed",
						"Error Message for 'BR_482' is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for 'BR_482' is Displayed",
						"Error Message for 'BR_482' is Not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for 'BR_482' is Displayed",
					"Error Message for 'BR_482' is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
	}
	
	/**
	 * @author srsksr
	 * Method to verify Hard Stop Message for Default code in Address Table
	 */
	public void verifyPPTRequestLoanPageWithDefaultedAddress() {
		
		String expectedErrorMsg="Based on your plan provisions, you are not eligible to take a loan. "
				+ "Please call a participant services representative for additional information.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errDefaultedAddress, true)){
		 actualErrorMsg=errDefaultedAddress.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message for Defaulted Address is Displayed",
						"Hard Stop Error Message for Defaulted Address is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message for Defaulted Address is Displayed",
						"Hard Stop Error Message for Defaulted Address is not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message for Defaulted Address is Displayed",
					"Error Message for Defaulted Address is Not Displayed", true);
		
		
		verifyRequestLoanButtonsAreDisabled();
		Web.clickOnElement(lnkLogout);
		Web.waitForElement(btnLogin);
		
		
	}
	/**
	 * @author srsksr
	 * Method to verify Your loan check will be mailed to your employer in request Loan Page
	 */
	public void verifyCheckMailedToEmployerErrorMsg() {
		
		String expectedErrorMsg="Your loan check will be mailed to your employer.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(errcheckMailed, true)){
		 actualErrorMsg=errcheckMailed.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify Error Message is Displayed",
						"Error Message is Displayed\nError Message:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify Error Message is Displayed",
						"Error Message is not Matching\nExpected Error Message: "+expectedErrorMsg+"\nActual Error Message: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Error Message is Displayed",
					"Error Message is Not Displayed for Check Mailed to Employer", true);
		
		Web.clickOnElement(btnOK);
		Web.waitForElement(inputLoanAmount);
		
		
	}
	/**
	 * @author srsksr
	 * Method to verify Your loan check will be mailed to your employer in confirmation Page
	 */
	public void verifyDeliveryInformationSectionInConfirmationPageForCheckMailedToEmployer() {
		
		String expectedErrorMsg="Your loan check will be mailed to your employer.";
		String actualErrorMsg="";
		if(Web.isWebElementDisplayed(txtcheckMailed, true)){
		 actualErrorMsg=txtcheckMailed.getText().toString().trim();
		 if(expectedErrorMsg.equalsIgnoreCase(actualErrorMsg)){
			 Reporter.logEvent(Status.PASS,
					 "Verify 'Your loan check will be mailed to your employer' Message is Displayed in Confirmation Page",
						" Message is Displayed\n Mailing Address:"+actualErrorMsg,true);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,
					 "Verify 'Your loan check will be mailed to your employer' Message is Displayed in Confirmation Page",
						" Message is Not Displayed\n Expected Mailing Address: "+expectedErrorMsg+"\nActual  Mailing Address: "+actualErrorMsg,true);
		 }
		}
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Your loan check will be mailed to your employer' Message is Displayed in Confirmation Page",
					" Message is Not Displayed", true);
		
	
	}

/*	public static String getDeliveryMethodSelected() {
		return deliveryMethodSelected;
	}

	public void setDeliveryMethodSelected() {
		if(inpRegularMail.isSelected()){
			RequestLoanPage.deliveryMethodSelected = "Regular mail";
		}
		else if(inpExpressMail.isSelected()){
			RequestLoanPage.deliveryMethodSelected = "Expedited mail";
		}else{
//			RequestLoanPage.deliveryMethodSelected = "Regular mail";
		}
	}

	public void verifyRepaymentTerm() {
		WebElement txtRepaymentTerm = Web.getDriver().findElement(By.xpath(repaymentTerm.replace("loanType", Stock.GetParameterValue("loanTypeForRepaymentTerm"))));
		if(txtRepaymentTerm.isDisplayed()){
			Reporter.logEvent(Status.PASS,
					"Verify Repayment term information under 'PRIMARY RESIDENCE LOAN'",
					"Repayment term information under 'PRIMARY RESIDENCE LOAN' is displayed "+txtRepaymentTerm.getText().toString(), false);
		}else{
			Reporter.logEvent(Status.FAIL,
					"Verify Repayment term information under 'PRIMARY RESIDENCE LOAN'",
					"Repayment term information under 'PRIMARY RESIDENCE LOAN' is not displayed ", true);
		}
	}

	public void verifyMaxLoanThresholdLimitErrorMsgIsDisplayed() {
		String expectedErrorMsg = Stock.GetParameterValue("PendingTransactionErrorMsg");
		String actualErrorMsg = null;
		
		if(txtPendingTransactionError.isDisplayed()){
			actualErrorMsg = txtPendingTransactionError.getText().toString();
			if(actualErrorMsg.equalsIgnoreCase(expectedErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verifying Error message is Displayed",
						"Error Message is displayed: "+actualErrorMsg, true);
			}else{
				Reporter.logEvent(Status.FAIL,
						"Verifying Error message is Displayed",
						"Error Message is not displayed as expected: "+
						"\nExpected: "+expectedErrorMsg+
						"\nActual: "+actualErrorMsg, true);
			}
		}
		
	}

	public boolean verifyLoanRefinancingPageLoad() {
		boolean isPageLoaded = false;
		try {

			if(Web.isWebElementDisplayed(loanRefinancingSection, true)){
				isPageLoaded = true; 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isPageLoaded;		
	}

	public void selectRefinanceExistingLoanButton() {
		Web.clickOnElement(btnRefinanceExistingLoan);		
	}
	
	public void selectRequestANewLoan() {
		Web.clickOnElement(btnNewLoanRefinance);		
	}

	*//**
	 * @author bbndsh
	 * @param noofLoans
	 * 
	 *//*
	public void selectActiveLoanCheckBox(int noofLoans)  {
		try{
			for(int i=0;i<noofLoans;i++){
		
		Web.clickOnElement(refinanceLoanChkBox.get(i));	
		
			}
	}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void verifyLoanRefinancePage() {
		String font_weight = null;
		
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(refinancePageTitle.isDisplayed()){
			font_weight = refinancePageTitle.getCssValue("font-weight");
			if(Integer.parseInt(font_weight) >= 700){
				lib.Reporter.logEvent(Status.PASS, "Verifying page Title of Loan Refinance page", "Page Title is displayed and is in BOLD: "+
						refinancePageTitle.getText().toString().trim(),  false);
			}else{
				lib.Reporter.logEvent(Status.FAIL, "Verifying page Title of Loan Refinance page", "Page Title is not displayed properly: "+
						refinancePageTitle.getText().toString().trim(), true);
			}
		}
		
		if(txtNewLoanRefinance.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying options for Loan Refinance page is displayed", "Options for 'New Loan' Loan Refinance page is displayed: "+
					txtNewLoanRefinance.getText().toString().trim(),  false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying options for Loan Refinance page is displayed", "Options for 'New Loan' Loan Refinance page is not displayed", true);
		}
		
		if (txtRefinanceExistingLoan.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying options for Loan Refinance page is displayed", "Options for 'Refinance Existing Loans' Loan Refinance page is displayed: "+
					txtRefinanceExistingLoan.getText().toString().trim(),  false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying options for Loan Refinance page is displayed", "Options for 'Refinance Existing Loans' Loan Refinance page is not displayed", true);
		}
	}

	public void verifyRefinanceRepaymentTermAfterAddingNewTerm(String ...newLoanTerm) {
		
		*//**
		 * repayment term body
		 *//*
		List<String> repaymentTermList = new ArrayList<>();
	//	repaymentTermList.addAll(Arrays.asList("12months", "24months", "36months"));
		repaymentTermList.addAll(Arrays.asList("60months", "83months"));
		
		for(String str:newLoanTerm){
			repaymentTermList.add(repaymentTermList.size(), str+" months");
		}
		
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> refinanceRepaymentTerm = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableRepaymentTerm.replace("rownum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Repayment Loan Term ", "Repayment Loan Term "+
					refinanceRepaymentTerm.get(0).getText().toString(), false);
			
		}	
	}
	
	public void verifyRefinanceRepaymentTerm(String repaymentTerms) {
		
		List<String> repaymentTermList = new ArrayList<>();
		for(int i = 0; i < repaymentTermList.size(); i++){
			repaymentTermList.add(repaymentTerms.split(",")[i]);
		}
		*//**
		 * repayment term Header verifying
		 *//*
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> repaymentLoanTermHeader = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableHeader.replace("colNum",Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.INFO, "Verifying Repayment Loan Term header", "Repayment Loan Term header value: "+
					repaymentLoanTermHeader.get(0).getText().toString().trim(), false);
		}
		
		*//**
		 * repayment term body
		 *//*
		List<String> repaymentTermList = new ArrayList<>();
		repaymentTermList.addAll(Arrays.asList("12months", "24months"));//, "36months"));
		
		
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> refinanceRepaymentTerm = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableRepaymentTerm.replace("rownum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Repayment Loan Term ", "Repayment Loan Term "+
					refinanceRepaymentTerm.get(0).getText().toString(), false);
			
		}	
	}

	public void clickOnEnterYourOwnLoanTerm(String newLoanTerm) {

		if(lnkEnterYourOwnTerm.isDisplayed()){
			lnkEnterYourOwnTerm.click();
			if(popOverEnterYourOwnLoanTerm.isDisplayed()){
				Web.setTextToTextBox(loanTermInput, newLoanTerm);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				btnAddEnterYourOwnLoanTerm.click();
			}
		}
		
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		
		
	}

	public void verifyAS_212ErrorMessage() {
		
		try{
			Web.waitForElement(errorMsgAS_212);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(errorMsgAS_212.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "verifying AS_212 error message is displayed when Invalid Loan Term entered",
					"AS_212 error message is displayed "+errorMsgAS_212.getText().toString().trim(), true);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "verifying AS_212 error message is displayed when Invalid Loan Term entered",
					"AS_212 error message is not displayed ", true);
		}
	}

	public void verifyErrorMsgForInvalidLoanAmount(String amountToBeEntered) {
		
		EnterLoanAmount(amountToBeEntered);
		
		try{
			Web.waitForElement(errorBlockForInvalidAmountEntered);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(errorBlockForInvalidAmountEntered.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Error message for Invalid Amount Entered is Displayed",
					"Error message for Invalid Amount entered is displayed: "+
							errorBlockForInvalidAmountEntered.getText().toString().trim(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Error message for Invalid Amount Entered is Displayed",
					"Error message for Invalid Amount entered is not displayed: ", true);
		}

	}

	public void clearInputLoanAmountField() {
		
		try{
			inputLoanAmount.clear();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void verifyNewLoanRefinanceisSelected() {
		
		if (btnNewLoanRefinance.isDisplayed() && btnNewLoanRefinance.isSelected()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'New Loan' Radio Button is Selected By Default",
					"'New Loan' Radio Button is Selected By Default", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'New Loan' Radio Button is Selected By Default",
					"'New Loan' Radio Button is Not Selected By Default",
					true);

		}
		
	}

	public void verifyActiveLoansDetailsHeaderSectionForRefinance(){
		
		if(txtActiveLoansHeader.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Header text is Displayed Section ", "Active Loans Header text is Displayed: "+
					txtActiveLoansHeader.getText().toString(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Active Loans Header text is Displayed Section ", "Active Loans Header text is not Displayed", true);
			
		}
	}
	
	//TODO
	public void verifyActiveLoansDetailsSectionForRefinance(String headers) {
		
		
		for (int i = 0; i < 5; i++) {
			List<WebElement> activeLoansHeader = Web.getDriver().findElements(
					By.xpath(activeLoansDetailTableHeaderGP.replace("colNum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details header "+
					activeLoansHeader.get(0).getText().toString(), false);
			
			WebElement activeLoansBody = Web.getDriver().findElement(
					By.xpath(activeLoansDetailTableBodyGP.replace("dataHeader", StringUtils.capitalize(activeLoansHeader.get(0).getText().toString().trim))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details Body "+
					activeLoansBody.getText().toString(), false);
			
		}
		
	}
	
	public void verifyActiveLoansDetailsSectionForRefinancePR() {
		
		for (int i = 0; i < 5; i++) {
			List<WebElement> activeLoansHeader = Web.getDriver().findElements(
					By.xpath(activeLoansDetailTableHeaderPR.replace("colNum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details header "+
					activeLoansHeader.get(0).getText().toString(), false);
			
			WebElement activeLoansBody = Web.getDriver().findElement(
					By.xpath(activeLoansDetailTableBodyPR.replace("dataHeader", StringUtils.capitalize(activeLoansHeader.get(0).getText().toString().trim()))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details Body "+
					activeLoansBody.getText().toString(), false);
			
		}
		
	}
	
	

	public void verifyCurrentActiveLoanBalanceIsDisplayed() {
		
		if(refinanceCurrentBalance.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying current active loan balance is displayed", 
						"current active loan balance is displayed: "+refinanceCurrentBalance.getText().toString().trim(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying current active loan balance is displayed", 
					"current active loan balance is displayed: ", true);
		}
		
	}

	public void validateToolTipForLoanOutstandingBalance() {
		
		keyBoardEvent.moveToElement(refinanceCurrentBalance).build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String expectedtext = "PAYMENT AMOUNT";
		String actualText = toolTipCurrentBalPaymentAmount.getText().toString()
				.trim();
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalPaymentAmountValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext = "MATURITY DATE";
		actualText = toolTipCurrentBalMaturityDate.getText().toString().trim() ;
		
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalMaturityDateValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext = "LOAN TERM REMAINING";
		actualText = toolTipCurrentBalLoanTermRemaining.getText().toString().trim() ;
		
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalLoanTermRemainingValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}
		//TODO
		keyBoardEvent.release(toolTipCurrentBalMaturityDate);
		
	}

	public void verifyOutstandingBalance() {
		
		if(outstandingBalanceValue.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Outstanding balance after selecting 'Refinance existing loan'", 
					"Outstanding Balance is : "+outstandingBalanceValue.getText().toString().trim(), true);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Outstanding balance after selecting 'Refinance existing loan'", 
					"Outstanding Balance is not displayed: ", true);
		}
		
	}

	public void verifyInformationalMessageForExhaustedloans() {
		String expectedMsg = "You have reached the maximum number of loans allowed by your plan. You may still be able to borrow more by refinancing another loan.";
		String actualMsg = null;
		
		try{
			if(errorMsgExhaustedLoans.isDisplayed()){
				actualMsg = errorMsgExhaustedLoans.getText().toString().trim();
				if(actualMsg.equalsIgnoreCase(expectedMsg)){
					lib.Reporter.logEvent(Status.PASS, "Verify the informational message at the top of the  active loan details table", 
							"Informational message is displayed: "+actualMsg, false);
				}
			}
		}catch(Exception e){
			lib.Reporter.logEvent(Status.FAIL, "Verify the informational message at the top of the  active loan details table", 
					"Informational message is not displayed as expected: "+
							"\nActual: "+actualMsg+"\nExpected: "+expectedMsg+"\n"+e.getMessage(), true);
		}
		
	}

	public void verifyFrequencyRestrictionMsg() {
		
		
	}

	public void setMortgageOrSpousalStepInDatabase(String StepToBeSetInDB) throws SQLException {
		ResultSet queryResultSet = null;		
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("checkStepToBeInsertedPresentInDB");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], StepToBeSetInDB);
		
		if(DB.getRecordSetCount(queryResultSet) == 0){
			if(StepToBeSetInDB.equalsIgnoreCase("Mortgage")){
				try {
					sqlQuery = Stock.getTestQuery("insertStepIntoDB_Mortgage");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				try {
					sqlQuery = Stock.getTestQuery("insertStepIntoDB_Spousal");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
			try {
				if (DB.getRecordSetCount(queryResultSet) > 0) {
					lib.Reporter.logEvent(Status.INFO, "Inserting 'Mortgage/Spousal' Step in TDL", "No of rows inserted: "+DB.getRecordSetCount(queryResultSet), false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.logEvent(
					Status.WARNING,
					"Query to insert 'Mortgage/Spousal' step:" + sqlQuery[0],
					"The Query did not return any results.",
					false);
			}
		}
		
	}
	
	public void ResetMortgageOrSpousalStepInDatabase(String StepToBeSetInDB) throws SQLException {
		ResultSet queryResultSet = null;
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("checkStepToBeDeletedInDB");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], StepToBeSetInDB);
		
		if(DB.getRecordSetCount(queryResultSet) > 0){
		if(StepToBeSetInDB.equalsIgnoreCase("Mortgage")){
			try {
				sqlQuery = Stock.getTestQuery("deleteStepFromDB_Mortgage");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				sqlQuery = Stock.getTestQuery("deleteStepFromDB_Spousal");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
		try {
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				lib.Reporter.logEvent(Status.INFO, "Deleting 'Mortgage/Spousal' Step in TDL", "No of rows deleted: "+DB.getRecordSetCount(queryResultSet), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query to delete 'Mortgage/Spousal' step:" + sqlQuery[0],
					"The Query did not return any results.",
					false);
		}
		}
	}

	public void setMortgagePaperWorkIndicator(String reviewIndicator, String ga_id) throws SQLException {
		ResultSet queryResultSet = null;		
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("setMortgagePaperworkIndicator");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], reviewIndicator, ga_id);
		try {
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				lib.Reporter.logEvent(Status.INFO, "Setting Mortgage Paperwork indicator to: "+reviewIndicator, 
						"Mortgage paperwork Indicator is set to : "+reviewIndicator+"\nTable Name: grp_loan_struc ", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Setting Mortgage Paperwork indicator to: "+reviewIndicator, 
					"Mortgage paperwork Indicator is not set to : "+reviewIndicator+"\nTable Name: grp_loan_struc ", false);
		}
	}
}*/




	public static String getDeliveryMethodSelected() {
		return deliveryMethodSelected;
	}

	public void setDeliveryMethodSelected() {
		if(inpRegularMail.isSelected()){
			RequestLoanPage.deliveryMethodSelected = "Regular mail";
		}
		else if(inpExpressMail.isSelected()){
			RequestLoanPage.deliveryMethodSelected = "Expedited mail";
		}else{
//			RequestLoanPage.deliveryMethodSelected = "Regular mail";
		}
	}

	public void verifyRepaymentTerm() {
		WebElement txtRepaymentTerm = Web.getDriver().findElement(By.xpath(repaymentTerm.replace("loanType", Stock.GetParameterValue("loanTypeForRepaymentTerm"))));
		if(txtRepaymentTerm.isDisplayed()){
			Reporter.logEvent(Status.PASS,
					"Verify Repayment term information under 'PRIMARY RESIDENCE LOAN'",
					"Repayment term information under 'PRIMARY RESIDENCE LOAN' is displayed "+txtRepaymentTerm.getText().toString(), false);
		}else{
			Reporter.logEvent(Status.FAIL,
					"Verify Repayment term information under 'PRIMARY RESIDENCE LOAN'",
					"Repayment term information under 'PRIMARY RESIDENCE LOAN' is not displayed ", true);
		}
	}

	public void verifyMaxLoanThresholdLimitErrorMsgIsDisplayed() {
		String expectedErrorMsg = Stock.GetParameterValue("PendingTransactionErrorMsg");
		String actualErrorMsg = null;
		
		if(txtPendingTransactionError.isDisplayed()){
			actualErrorMsg = txtPendingTransactionError.getText().toString();
			if(actualErrorMsg.equalsIgnoreCase(expectedErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verifying Error message is Displayed",
						"Error Message is displayed: "+actualErrorMsg, true);
			}else{
				Reporter.logEvent(Status.FAIL,
						"Verifying Error message is Displayed",
						"Error Message is not displayed as expected: "+
						"\nExpected: "+expectedErrorMsg+
						"\nActual: "+actualErrorMsg, true);
			}
		}
		
	}

	public boolean verifyLoanRefinancingPageLoad() {
		boolean isPageLoaded = false;
		try {

			if(Web.isWebElementDisplayed(loanRefinancingSection, true)){
				isPageLoaded = true; 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isPageLoaded;		
	}

	public void selectRefinanceExistingLoanButton() {
		Web.clickOnElement(btnRefinanceExistingLoan);		
	}
	
	public void selectRequestANewLoan() {
		Web.clickOnElement(btnNewLoanRefinance);		
	}

	/**
	 * @author bbndsh
	 * @param noofLoans
	 * 
	 */
	public void selectActiveLoanCheckBox(int noofLoans)  {
		try{
			for(int i=0;i<noofLoans;i++){
		
		Web.clickOnElement(refinanceLoanChkBox.get(i));	
		
			}
	}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void verifyLoanRefinancePage() {
		String font_weight = null;
		
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(refinancePageTitle.isDisplayed()){
			font_weight = refinancePageTitle.getCssValue("font-weight");
			if(Integer.parseInt(font_weight) >= 700){
				lib.Reporter.logEvent(Status.PASS, "Verifying page Title of Loan Refinance page", "Page Title is displayed and is in BOLD: "+
						refinancePageTitle.getText().toString().trim(),  false);
			}else{
				lib.Reporter.logEvent(Status.FAIL, "Verifying page Title of Loan Refinance page", "Page Title is not displayed properly: "+
						refinancePageTitle.getText().toString().trim(), true);
			}
		}
		
		if(txtNewLoanRefinance.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying options for Loan Refinance page is displayed", "Options for 'New Loan' Loan Refinance page is displayed: "+
					txtNewLoanRefinance.getText().toString().trim(),  false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying options for Loan Refinance page is displayed", "Options for 'New Loan' Loan Refinance page is not displayed", true);
		}
		
		if (txtRefinanceExistingLoan.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying options for Loan Refinance page is displayed", "Options for 'Refinance Existing Loans' Loan Refinance page is displayed: "+
					txtRefinanceExistingLoan.getText().toString().trim(),  false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying options for Loan Refinance page is displayed", "Options for 'Refinance Existing Loans' Loan Refinance page is not displayed", true);
		}
	}

	public void verifyRefinanceRepaymentTermAfterAddingNewTerm(String ...newLoanTerm) {
		
		/**
		 * repayment term body
		 */
		List<String> repaymentTermList = new ArrayList<>();
	//	repaymentTermList.addAll(Arrays.asList("12months", "24months", "36months"));
		repaymentTermList.addAll(Arrays.asList("60months", "83months"));
		
		for(String str:newLoanTerm){
			repaymentTermList.add(repaymentTermList.size(), str+" months");
		}
		
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> refinanceRepaymentTerm = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableRepaymentTerm.replace("rownum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Repayment Loan Term ", "Repayment Loan Term "+
					refinanceRepaymentTerm.get(0).getText().toString(), false);
			
		}	
	}
	
	public void verifyRefinanceRepaymentTerm(String repaymentTerms) {
		
		List<String> repaymentTermList = new ArrayList<>();
		for(int i = 0; i < repaymentTermList.size(); i++){
			repaymentTermList.add(repaymentTerms.split(",")[i]);
		}
		/**
		 * repayment term Header verifying
		 */
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> repaymentLoanTermHeader = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableHeader.replace("colNum",Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.INFO, "Verifying Repayment Loan Term header", "Repayment Loan Term header value: "+
					repaymentLoanTermHeader.get(0).getText().toString().trim(), false);
		}
		
		/**
		 * repayment term body
		 */
		/*List<String> repaymentTermList = new ArrayList<>();
		repaymentTermList.addAll(Arrays.asList("12months", "24months"));//, "36months"));
		*/
		
		for (int i = 0; i < repaymentTermList.size(); i++) {
			List<WebElement> refinanceRepaymentTerm = Web.getDriver().findElements(
					By.xpath(refinanceExistingLoansTableRepaymentTerm.replace("rownum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Repayment Loan Term ", "Repayment Loan Term "+
					refinanceRepaymentTerm.get(0).getText().toString(), false);
			
		}	
	}

	public void clickOnEnterYourOwnLoanTerm(String newLoanTerm) {

		if(lnkEnterYourOwnTerm.isDisplayed()){
			lnkEnterYourOwnTerm.click();
			if(popOverEnterYourOwnLoanTerm.isDisplayed()){
				Web.setTextToTextBox(loanTermInput, newLoanTerm);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				btnAddEnterYourOwnLoanTerm.click();
			}
		}
		
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		
		
	}

	public void verifyAS_212ErrorMessage() {
		
		try{
			Web.waitForElement(errorMsgAS_212);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(errorMsgAS_212.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "verifying AS_212 error message is displayed when Invalid Loan Term entered",
					"AS_212 error message is displayed "+errorMsgAS_212.getText().toString().trim(), true);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "verifying AS_212 error message is displayed when Invalid Loan Term entered",
					"AS_212 error message is not displayed ", true);
		}
	}

	public void verifyErrorMsgForInvalidLoanAmount(String amountToBeEntered) {
		
		EnterLoanAmount(amountToBeEntered);
		
		try{
			Web.waitForElement(errorBlockForInvalidAmountEntered);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(errorBlockForInvalidAmountEntered.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Error message for Invalid Amount Entered is Displayed",
					"Error message for Invalid Amount entered is displayed: "+
							errorBlockForInvalidAmountEntered.getText().toString().trim(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Error message for Invalid Amount Entered is Displayed",
					"Error message for Invalid Amount entered is not displayed: ", true);
		}

	}

	public void clearInputLoanAmountField() {
		
		try{
			inputLoanAmount.clear();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void verifyNewLoanRefinanceisSelected() {
		
		if (btnNewLoanRefinance.isDisplayed() && btnNewLoanRefinance.isSelected()) {
			lib.Reporter.logEvent(Status.PASS,
					"Verify 'New Loan' Radio Button is Selected By Default",
					"'New Loan' Radio Button is Selected By Default", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL,
					"Verify 'New Loan' Radio Button is Selected By Default",
					"'New Loan' Radio Button is Not Selected By Default",
					true);

		}
		
	}

	public void verifyActiveLoansDetailsHeaderSectionForRefinance(){
		
		if(txtActiveLoansHeader.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Header text is Displayed Section ", "Active Loans Header text is Displayed: "+
					txtActiveLoansHeader.getText().toString(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Active Loans Header text is Displayed Section ", "Active Loans Header text is not Displayed", true);
			
		}
	}
	
	//TODO
	public void verifyActiveLoansDetailsSectionForRefinance(String headers) {
		
		
		for (int i = 0; i < 5; i++) {
			List<WebElement> activeLoansHeader = Web.getDriver().findElements(
					By.xpath(activeLoansDetailTableHeaderGP.replace("colNum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details header "+
					activeLoansHeader.get(0).getText().toString(), false);
			
			/*WebElement activeLoansBody = Web.getDriver().findElement(
					By.xpath(activeLoansDetailTableBodyGP.replace("dataHeader", StringUtils.capitalize(activeLoansHeader.get(0).getText().toString().trim))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details Body "+
					activeLoansBody.getText().toString(), false);
			*/
		}
		
	}
	
	public void verifyActiveLoansDetailsSectionForRefinancePR() {
		
		for (int i = 0; i < 5; i++) {
			List<WebElement> activeLoansHeader = Web.getDriver().findElements(
					By.xpath(activeLoansDetailTableHeaderPR.replace("colNum",	Integer.toString(i+1))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details header "+
					activeLoansHeader.get(0).getText().toString(), false);
			
			WebElement activeLoansBody = Web.getDriver().findElement(
					By.xpath(activeLoansDetailTableBodyPR.replace("dataHeader", StringUtils.capitalize(activeLoansHeader.get(0).getText().toString().trim()))));
			
			lib.Reporter.logEvent(Status.PASS, "Verifying Active Loans Details Section ", "Active Loans Details Body "+
					activeLoansBody.getText().toString(), false);
			
		}
		
	}
	
	

	public void verifyCurrentActiveLoanBalanceIsDisplayed() {
		
		if(refinanceCurrentBalance.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying current active loan balance is displayed", 
						"current active loan balance is displayed: "+refinanceCurrentBalance.getText().toString().trim(), false);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying current active loan balance is displayed", 
					"current active loan balance is displayed: ", true);
		}
		
	}

	public void validateToolTipForLoanOutstandingBalance() {
		
		keyBoardEvent.moveToElement(refinanceCurrentBalance).build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String expectedtext = "PAYMENT AMOUNT";
		String actualText = toolTipCurrentBalPaymentAmount.getText().toString()
				.trim();
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalPaymentAmountValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext = "MATURITY DATE";
		actualText = toolTipCurrentBalMaturityDate.getText().toString().trim() ;
		
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalMaturityDateValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for INTEREST RATE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}

		expectedtext = "LOAN TERM REMAINING";
		actualText = toolTipCurrentBalLoanTermRemaining.getText().toString().trim() ;
		
		if (expectedtext.equalsIgnoreCase(actualText)) {
			Reporter.logEvent(Status.PASS,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, false);
			Reporter.logEvent(Status.PASS,	"Verifying Value",
					"Value is displayed: "+toolTipCurrentBalLoanTermRemainingValue.getText().toString().trim(), false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify ToolTip Text for CURRENT BALANCE is displayed",
					"Tooltip Text is not displayed for CURRENT BALANCE\nExpected: "
							+ expectedtext + "\nActual: " + actualText, true);
		}
		//TODO
		keyBoardEvent.release(toolTipCurrentBalMaturityDate);
		
	}

	public void verifyOutstandingBalance() {
		
		if(outstandingBalanceValue.isDisplayed()){
			lib.Reporter.logEvent(Status.PASS, "Verifying Outstanding balance after selecting 'Refinance existing loan'", 
					"Outstanding Balance is : "+outstandingBalanceValue.getText().toString().trim(), true);
		}else{
			lib.Reporter.logEvent(Status.FAIL, "Verifying Outstanding balance after selecting 'Refinance existing loan'", 
					"Outstanding Balance is not displayed: ", true);
		}
		
	}

	public void verifyInformationalMessageForExhaustedloans() {
		String expectedMsg = "You have reached the maximum number of loans allowed by your plan. You may still be able to borrow more by refinancing another loan.";
		String actualMsg = null;
		
		try{
			if(errorMsgExhaustedLoans.isDisplayed()){
				actualMsg = errorMsgExhaustedLoans.getText().toString().trim();
				if(actualMsg.equalsIgnoreCase(expectedMsg)){
					lib.Reporter.logEvent(Status.PASS, "Verify the informational message at the top of the  active loan details table", 
							"Informational message is displayed: "+actualMsg, false);
				}
			}
		}catch(Exception e){
			lib.Reporter.logEvent(Status.FAIL, "Verify the informational message at the top of the  active loan details table", 
					"Informational message is not displayed as expected: "+
							"\nActual: "+actualMsg+"\nExpected: "+expectedMsg+"\n"+e.getMessage(), true);
		}
		
	}

	/**
	 * @author bbndsh
	 */
	public void verifyFrequencyRestrictionMsg() {
		
		
	}

	/**
	 * @author bbndsh
	 * @param StepToBeSetInDB
	 * @throws SQLException
	 */
	public void setMortgageOrSpousalStepInDatabase(String StepToBeSetInDB) throws SQLException {
		ResultSet queryResultSet = null;		
		String[] sqlQuery = null;
		
		if(StepToBeSetInDB.equalsIgnoreCase("Mortgage")){
			sqlQuery = Stock.getTestQuery("checkStepToBeInsertedPresentInDBMORT");
		}else{
			sqlQuery = Stock.getTestQuery("checkStepToBeInsertedPresentInDBSPOC");
		}
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
		
		if(DB.getRecordSetCount(queryResultSet) == 0){
			if(StepToBeSetInDB.equalsIgnoreCase("Mortgage")){
				try {
					sqlQuery = Stock.getTestQuery("insertStepIntoDB_Mortgage");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				try {
					sqlQuery = Stock.getTestQuery("insertStepIntoDB_Spousal");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
			try {
				if (DB.getRecordSetCount(queryResultSet) > 0) {
					lib.Reporter.logEvent(Status.INFO, "Inserting 'Mortgage/Spousal' Step in TDL", "No of rows inserted: "+DB.getRecordSetCount(queryResultSet), false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.logEvent(
					Status.WARNING,
					"Query to insert 'Mortgage/Spousal' step:" + sqlQuery[0],
					"The Query did not return any results.",
					false);
			}
		}
		
	}
	
	/**
	 * @author bbndsh
	 * @param StepToBeSetInDB
	 * @throws SQLException
	 */
	public void ResetMortgageOrSpousalStepInDatabase(String StepToBeSetInDB) throws SQLException {
		ResultSet queryResultSet = null;
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("checkStepToBeDeletedInDB");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], StepToBeSetInDB);
		
		if(DB.getRecordSetCount(queryResultSet) > 0){
		if(StepToBeSetInDB.equalsIgnoreCase("Mortgage")){
			try {
				sqlQuery = Stock.getTestQuery("deleteStepFromDB_Mortgage");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				sqlQuery = Stock.getTestQuery("deleteStepFromDB_Spousal");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
		try {
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				lib.Reporter.logEvent(Status.INFO, "Deleting 'Mortgage/Spousal' Step in TDL", "No of rows deleted: "+DB.getRecordSetCount(queryResultSet), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query to delete 'Mortgage/Spousal' step:" + sqlQuery[0],
					"The Query did not return any results.",
					false);
		}
		}
	}

	/**
	 * @author bbndsh
	 * @param reviewIndicator
	 * @param ga_id
	 * @throws SQLException
	 */
	public void setMortgagePaperWorkIndicator(String reviewIndicator, String ga_id) throws SQLException {
		ResultSet queryResultSet = null;		
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("setMortgagePaperworkIndicator");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

		queryResultSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], reviewIndicator, ga_id);
		try {
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				lib.Reporter.logEvent(Status.INFO, "Setting Mortgage Paperwork indicator to: "+reviewIndicator, 
						"Mortgage paperwork Indicator is set to : "+reviewIndicator+"\nTable Name: grp_loan_struc ", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Setting Mortgage Paperwork indicator to: "+reviewIndicator, 
					"Mortgage paperwork Indicator is not set to : "+reviewIndicator+"\nTable Name: grp_loan_struc ", false);
		}
	}

	/**
	 * @author bbndsh
	 * @param QJSA_Ind
	 * @param Spousal_Consent_Ind
	 * @param gc_id
	 * @throws Exception
	 */
	public void setQJSAandSpousalConsentInDatabase(String QJSA_Ind,
			String Spousal_Consent_Ind, String gc_id) throws Exception {
		int queryResultSet = 0;		
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("updateQJSAandSPOUSALCONSENTplan");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

		queryResultSet = DB.executeUpdate(sqlQuery[0], sqlQuery[1], QJSA_Ind, Spousal_Consent_Ind, gc_id);
		try {
			if (queryResultSet > 0) {
				lib.Reporter.logEvent(Status.INFO, "Setting QJSA and Spousal Consent: ", 
						"QJSA and Spousal Consent has been updated  \nTable Name: PLAN ", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Setting QJSA and Spousal Consent: ", 
					"QJSA and Spousal Consent has not been updated  \nTable Name: PLAN ", false);
		}
		
	}
	
	/**
	 * @author bbndsh
	 * @param rl_id
	 * @param ga_id
	 * @throws Exception
	 */
	public void setRULE_IDInDatabase(String rl_id, String ga_id) throws Exception {
		int queryResultSet = 0;		
		String[] sqlQuery = null;
		
		sqlQuery = Stock.getTestQuery("updateRULEIDstd_sel");
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

		queryResultSet = DB.executeUpdate(sqlQuery[0], sqlQuery[1], rl_id, ga_id);
		try {
			if (queryResultSet > 0) {
				lib.Reporter.logEvent(Status.INFO, "Setting RULE_ID: ", 
						"RULE_ID has been updated  \nTable Name: STD_RL_ID ", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Setting RULE_ID: ", 
					"RULE_ID has not been updated  \nTable Name: STD_RL_ID ", false);
		}
		
	}
	
}
