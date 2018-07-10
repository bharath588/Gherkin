package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;

import com.aventstack.extentreports.Status;

public class AccountOverview extends LoadableComponent<AccountOverview> {

	// Declarations
	private LoadableComponent<?> parent;
	// private static boolean waitforLoad = false;

	// @FindBy(xpath=".//h1[text()='My Accounts']") private WebElement
	// hdrMyAccounts;
	@FindBy(xpath = ".//h1[text()='Account overview']")
	private WebElement hdrAccountOverview;
	@FindBy(xpath = ".//*[@class='plan']/*[starts-with(@id,'ga_')]")
	private List<WebElement> lstLnkPlanName;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//*[@id='account-overview-chart']")
	private WebElement imgGraph;
	@FindBy(xpath = ".//*[@class='plan']/*[starts-with(@id,'ga_')]")
	private WebElement lnkPlanName;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;

	@FindBy(xpath = "//button[contains(text(),'Continue')]")
	private WebElement btnContinue;
	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement btnCancel;
	@FindBy(xpath = "//div[@class='modal-body auto-increase']//div[@class='row'][1]//p")
	private WebElement txtYouEnrolled;
	@FindBy(xpath = "//div[@class='modal-body auto-increase']//div[@class='row'][2]//p")
	private WebElement txtClickCancel;
	@FindBy(xpath = "//button[./strong[text()[normalize-space()='Continue to enrollment']]]")
	private WebElement btnContinueToEnroll;
	@FindBy(xpath = "//a[./span[contains(text(),'Enroll now')]]")
	private WebElement lnkEnrollNow;
	private String textField = "//*[contains(text(),'webElementText')]";

	// Bhargav

	@FindBy(xpath = "//h1[text()='Account overview']/parent::div/following-sibling::div//h2[text()='Balance']/ancestor::div[contains(@class,'dashboard__card') and contains(@class,'with-gradient')]")
	private WebElement txtBalanceCard;
	@FindBy(xpath = "//h1[text()='Account overview']/parent::div/following-sibling::div//h2[text()='Balance']")
	private WebElement txtBalance;
	@FindBy(xpath = "//h1[text()='Account overview']/parent::div/following-sibling::div//h2[text()='Balance']/following-sibling::a[text()='View details' and @href]")
	private WebElement lnkBalanceCardViewDetails;
	@FindBy(xpath = "//h1[text()='Account overview']/parent::div/following-sibling::div//h2[text()='Balance']/following-sibling::div/span[@class='currency']")
	private WebElement blnCardPtpAmount;// Balance Card Participant Amount

	// Rate of Retun Card
	@FindBy(xpath = "//div[contains(@class,'card')]//h2[text()='Rate of return']")
	private WebElement rorCardHeader;
	@FindBy(xpath = "//div[contains(@class,'card')]//h2[text()='Annualized Rate of return']")
	private WebElement rorCardHeaderAnnual;
	@FindBy(xpath = "//h2[contains(text(),'Rate of return')]/following-sibling::div[contains(@class,'dates')]")
	private WebElement rorCardFromandTodate;
	@FindBy(xpath = "//h2[contains(text(),'Rate of return')]/ancestor::div[contains(@class,'card')]//div[contains(@class,'rate') and contains(@class,'value')]")
	private WebElement rorpercentage;
	@FindBy(xpath = "//h2[text()='Rate of return']/following-sibling::p[text()='Data currently unavailable']")
	private WebElement rorCardValuewithNoData;
	@FindBy(xpath = "//h2[contains(text(),'Rate of return')]/following-sibling::a[text()='View details']")
	private WebElement lnkViewdetailsRorCard;
	@FindBy(xpath = "//*[text()='Account Information']/parent::div[contains(@class,'nav')]//*[contains(text(),'Rate of return')]")
	private WebElement sLeftNavRORLink;
	@FindBy(xpath = "//body")
	private WebElement spagebody;

	// Mosin - Beneficiary Card:

	@FindBy(xpath = "//div[@ng-if='accountOverviewNewCtrl.showBeneficiaryCard()']")
	private WebElement txtBeneficiaryCard;
	@FindBy(xpath = "//div[@ng-if='accountOverviewNewCtrl.showBeneficiaryCard()']/div/div/pw-beneficiary-card/a")
	private WebElement lnkBeneficiaryCardViewDetails;
	@FindBy(linkText = "Add / Edit")
	private WebElement lnkBeneficiaryCardAddOrEdit;
	@FindBy(linkText = "Add now")
	private WebElement lnkBeneficiaryCardAddnow;
	@FindBy(linkText = "Account overview")
	private WebElement lnkAccountOverview;

	@FindBy(xpath = "//div[@class='overview-value']")
	private WebElement txtOverviewValue;
	@FindBy(xpath = "//div[@class='overview-value']/following-sibling::div")
	private WebElement txtOverviewDate;

	@FindBy(linkText = "Beneficiaries")
	private WebElement lnkBeneficiaries;
	@FindBy(xpath = ".//h1[text()='My Beneficiaries']")
	private WebElement txtBeneficiaries;

	// Bhargav
	@FindBy(xpath = "//select[@id='accounts']")
	private WebElement accountsDropdown;

	// Statements - Mosin

	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNSTMT')]")
	private WebElement tagStatementCard;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNSTMT')]//following-sibling::h2[text()='Statements']")
	private WebElement lblStatementCard;
	
	//Stmt - Mulptiple stmts
	@FindBy(xpath = "//h2[text()='Statements']/following-sibling::ul/li[@ng-repeat='statement in statements']")
	private List<WebElement> lstStatements;
	@FindBy(xpath = "(//h2[text()='Statements']/following-sibling::ul/li[@ng-repeat='statement in statements'])[1]/a")
	private WebElement lstStatementsFirst;

	// @FindBy(xpath = "//a[text()='Show more']")
	@FindBy(xpath = "//pw-statements-card[@statements='accountOverviewNewCtrl.statements']/div/ul/li/a[text()='Show more']")
	private WebElement lnkShowMoreStatements;

	// Stmt - No Stmts
	@FindBy(xpath = "//h2[text()='Statements']/following-sibling::p")
	private WebElement txtNoStatements;

	// Stmt - Delivery Option
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNSTMT')]//following-sibling::h5[text()='Delivery options']")
	private WebElement txtStmtDeliveryOptions;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNSTMT')]//following-sibling::h5[text()='Delivery options']//following-sibling::p")
	private WebElement txtStmtBenefit;
	
	//Stmt - e-delivery button
	
	@FindBy(partialLinkText = "Sign up for")
	private WebElement lnkSignupEdelivery;
	
	//Loan Balance Card
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]")
	private WebElement tagLoanBalanceCard;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]//following-sibling::div/pw-loan-card/h2[text()='Loan balance']")
	private WebElement lblLoanBalanceCard;
	
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]//following-sibling::div/pw-loan-card/div/span[@class='currency']")
	private WebElement txtLoanBalanceAmount;
	@FindBy(xpath = "(//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]//following-sibling::div/pw-loan-card/div/span)[2]")
	private WebElement txtNumberOfLoans;
	
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]//following-sibling::div/pw-loan-card/div[contains(text(),'Maturity date')]")
	private WebElement txtMaturityDateLoans;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNLNSM')]//following-sibling::div/pw-loan-card/a")
	private WebElement lnkViewDetailsLoans;

	// TRANSACTION HISTORY Card
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNTRHI')]")
	private WebElement tagTransactionHistoryCard;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNTRHI')]//following-sibling::div/pw-transaction-history-card/div/h2[text()='Transaction History']")
	private WebElement lblTransactionHistoryCard;
	@FindBy(xpath = "//pw-transaction-history-card[@transactions='accountOverviewNewCtrl.transactions']//following-sibling::div/table/thead/tr/th")
	private List<WebElement> lstTransactionHeading;
	@FindBy(xpath = "//pw-transaction-history-card[@transactions='accountOverviewNewCtrl.transactions']//following-sibling::div/table/tbody/tr")
	private List<WebElement> lstTransactionHistory;
	private String lstTransactionHistoryData = "(//pw-transaction-history-card[@transactions='accountOverviewNewCtrl.transactions']//following-sibling::div/table/tbody/tr)[Iteration]/td";

	@FindBy(xpath = "//pw-transaction-history-card[@transactions='accountOverviewNewCtrl.transactions']/div/a")
	private WebElement lnkTransactionShowMore;

	// Contribution Card - Mosin
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNAOC')]")
	private WebElement cardContribution;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNAOC')]//following-sibling::div/pw-contributions-card/div/header/h2")
	private WebElement lblContributionCard;
	@FindBy(xpath = "//div[contains(@ng-if,'accountOverviewNewCtrl.allPermissions.WNAOC')]//following-sibling::div/pw-contributions-card/div/header/a")
	private WebElement lnkContributionViewDetails;

	// Contribution Donut
	private String txtDonutContribution="//section[@card-type='CardType']//div[contains(@id,'donut_chart_container')]//following-sibling::div/span/span";
	private String chartContirbutionDonut="//section[@card-type='CardType']//div[contains(@id,'donut_chart_container')]";
	private String txtContributionValue="//section[@card-type='CardType']//div[@ng-if='vm.expand']/ng-switch/div/div/a[contains(text(),'CardSubType')]";
	private String showMorelnk="//section[@card-type='CardType']//a[contains(text(),'Show more')]";

	// Contribution - Description Paragraph
	@FindBy(xpath = "//div[contains(@class,'contributions-card__description-paragraph')]/div/p[contains(@ng-if,'companyMatch')]")
	private WebElement txtDescriptionCompanyMatch;
	private String txtDescriptionOnTrack="//section[@card-type='CardType']//div[contains(@class,'contributions-card__description-paragraph')]/div/div[contains(@ng-if,'OnTrack')]";
	
	private String txtDescriptionOnTrackApple="(//section[@card-type='CardType']//div[contains(@class,'apple-maximizer-message')]/div/p)[1]";
	private String txtMaximizer="(//section[@card-type='CardType']//div[contains(@class,'apple-maximizer-message')]/div/p)[2]";
	
	// Contirbution - Table Year to date On track and IRS
	private String txtYearToDate="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr/td)[2]/span";
	private String amountYearToDate="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr/td)[3]";
	private String txtOnTrack="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'showOnTrack')]/td)[2]/span";
	private String amountOnTrack="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'showOnTrack')]/td)[3]";
	private String txtIRSLimit="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[2]";
	private String amountIRSLimit="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[3]";
	
	private String txtIRSLimitApple="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[5]";
	private String amountIRSLimitApple="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[6]";
	
	private String txtAdditionalApple="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[2]";
	private String amountAdditionalApple="(//section[@card-type='CardType']//table[contains(@class,'contributions-card__table')]/tbody/tr[contains(@ng-if,'irsLimit')]/td)[3]";
	
	// bhargav
	@FindBy(xpath = "//button[contains(text(),'Apply changes')]")
	private WebElement btnApplyChange;
	@FindBy(xpath = "//div[@class='modal-content']//div[contains(text(),'Unable to complete transaction')]")
	private WebElement txtinModalPopUpheader;
	@FindBy(xpath = "//button[text()='OK']")
	private WebElement btnOk;
	@FindBy(xpath = "//button[contains(text(),'Reset')]")
	private WebElement btnReset;
	@FindBy(xpath = "//button[@class='btn btn-primary reset-padding']")
	private WebElement btnDone;
	
	// Contribution - Model Contibution heading( + and - Symbols)
	@FindBy(xpath = "//div[@class='contribution-card__btns']/p")
	private WebElement txtModelContribution;
	@FindBy(xpath = "//div[@class='contribution-card__btns']/div/button/i[contains(@class,'minus')]")
	private WebElement txtModelContributionMinus;
	@FindBy(xpath = "//div[@class='contribution-card__btns']/div/button/i[contains(@class,'plus')]")
	private WebElement txtModelContributionPlus;
	// + or - button for Model Contribution based on deferral type
	private String sModelContributionPlusorMinusbtn = "//section[@card-type='CardType']//div[@class='contribution-card__btns']/div/button/i[contains(@class,'ButtonValue')]";
	private String sEditValueinDonut = "//section[@card-type='CardType']//span[contains(@class,'editable-text')]";
	private String sApplyChangebtn = "//section[@card-type='CardType']//button[contains(text(),'Apply changes')]";
	private String sResetbtn = "//section[@card-type='CardType']//button[contains(text(),'Reset')]";
	private String stxtcontributionRateSlider = "//section[@card-type='CardType']//*[contains(@id,'contributionRateSlider') and contains(@id,'edit')]";

	// Contribution - Set up chart
	@FindBy(xpath = "//div[contains(@class,'highcharts-xaxis-labels')]/span[contains(@style,'position: absolute')]")
	private List<WebElement> lstSetUpmonths;
	@FindBy(id = "hc_chart_container")
	private WebElement cardSetUpContirbution;

	// Post clicking on contribution's view details link
	@FindBy(xpath = ".//h1[text()[normalize-space()='My Contributions']]")
	private WebElement lblMyContributions;
	
	//Maximizer button
	private String maxButton="//section[@card-type='CardType']//div[@class='maximizer-button']/button";
		
	
	//To click on home page
	@FindBy(xpath = "(//a[text()='Home'])[1]")
	private WebElement lnkHome;
	
	//To click on My Account
	@FindBy(xpath = "(//a[text()='My Accounts'])[1]")
	private WebElement lnkMyAccount;
	/**
	 * Empty args constructor
	 * 
	 */
	public AccountOverview() {
		this.parent = new MyAccountsPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public AccountOverview(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),
				"User Name is Not Displayed\n");

		if (Common.verifyLoggedInUserIsSame()) {

			Assert.assertTrue(Web.isWebElementDisplayed(hdrAccountOverview));
		} else {
			this.lnkLogout.click();
			System.out.println("Clicked on Log Out My Accoounts Page");
			Web.waitForElement(btnLogin);
			Assert.assertTrue(false, "Login page is displayed");
		}

	}

	@Override
	protected void load() {
		// MyAccountsPage account = (MyAccountsPage) this.parent;

		this.parent.get();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		// Account Overview
		if (fieldName.trim().equalsIgnoreCase("Account Overview")) {
			return this.hdrAccountOverview;
		}
		if (fieldName.trim().equalsIgnoreCase("Account Overview Link")) {
			return this.lnkAccountOverview;
		}
		if (fieldName.trim().equalsIgnoreCase("Graph")) {
			return this.imgGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("PLAN NAME")) {
			return this.lnkPlanName;
		}
		if (fieldName.trim().equalsIgnoreCase("LINK ENROLL NOW")) {
			return this.lnkEnrollNow;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Continue")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Cancel")) {
			return this.btnCancel;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Continue To Enrollment")) {
			return this.btnContinueToEnroll;
		}
		if (fieldName.trim().equalsIgnoreCase("Add now Bene")) {
			return this.lnkBeneficiaryCardAddnow;
		}
		if (fieldName.trim().equalsIgnoreCase("Beneficiaries Link")) {
			return this.lnkBeneficiaries;
		}
		if (fieldName.trim().equalsIgnoreCase("AddEdit Link")) {
			return this.lnkBeneficiaryCardAddOrEdit;
		}
		// Bhargav
		if (fieldName.trim().equalsIgnoreCase("Link BalanceCard ViewDetails")) {
			return this.lnkBalanceCardViewDetails;
		}
		if (fieldName.trim().equalsIgnoreCase("Accounts Drop Down")) {
			return this.accountsDropdown;
		}
		//Mosin
		if (fieldName.trim().equalsIgnoreCase("Go to Home")) {
			return this.lnkHome;
		}
		if (fieldName.trim().equalsIgnoreCase("Go to My Account")) {
			return this.lnkMyAccount;
		}
		
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);
		return null;
	}

	/**
	 * method to click on EnrollNow link
	 */
	public void clickOnEnrollNow() {

		try {
			if (Web.isWebElementDisplayed(lnkEnrollNow, true))
				Web.clickOnElement(lnkEnrollNow);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed = false;
		try {
			WebElement txtField = Web.getDriver().findElement(
					By.xpath(textField.replace("webElementText", fieldName)));

			isTextDisplayed = Web.isWebElementDisplayed(txtField, true);

			if (isTextDisplayed)
				lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field "
						+ fieldName + "  is Displayed", "TEXT Field '"
						+ fieldName + "' is Displayed", false);

		} catch (NoSuchElementException e) {
			lib.Reporter.logEvent(Status.FAIL, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '" + fieldName
					+ "' is Not Displayed", false);
			isTextDisplayed = false;
		}

		return isTextDisplayed;
	}

	public void verifyBenficiaryCard(boolean shouldDisplay) {
		if (shouldDisplay)
			if (Web.isWebElementDisplayed(txtBeneficiaryCard, true))
				Reporter.logEvent(Status.PASS,
						"Verify Beneficiaries Card is displayed or not",
						"Beneficiaries Card is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Beneficiaries Card is displayed or not",
						"Beneficiaries Card is not displayed", true);
		else if (!Web.isWebElementDisplayed(txtBeneficiaryCard))
			Reporter.logEvent(Status.PASS,
					"Verify Beneficiaries Card is displayed or not",
					"Beneficiaries Card is not displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Beneficiaries Card is displayed or not",
					"Beneficiaries Card is displayed", true);

	}

	public void verifyBenficiaryCardViewDetailsLink(boolean shouldDisplay) {
		if (shouldDisplay)
			if (Web.isWebElementDisplayed(lnkBeneficiaryCardViewDetails))
				Reporter.logEvent(
						Status.PASS,
						"Verify Beneficiaries Card view details link is displayed or not",
						"Beneficiaries Card view details link is displayed",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Beneficiaries Card view details link is displayed or not",
						"Beneficiaries Card view details link is not displayed",
						true);
		else if (!Web.isWebElementDisplayed(lnkBeneficiaryCardViewDetails))
			Reporter.logEvent(
					Status.PASS,
					"Verify Beneficiaries Card view details link is displayed or not",
					"Beneficiaries Card view details link is not displayed",
					true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Beneficiaries Card view details link is displayed or not",
					"Beneficiaries Card view details link is displayed", true);
	}

	public void verifyBenficiaryCardAddEditLink(boolean shouldDisplay) {
		if (shouldDisplay)
			if (Web.isWebElementDisplayed(lnkBeneficiaryCardAddOrEdit))
				Reporter.logEvent(
						Status.PASS,
						"Verify Beneficiaries Card Add/Edit link is displayed or not",
						"Beneficiaries Card Add/Edit link is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Beneficiaries Card Add/Edit link is displayed or not",
						"Beneficiaries Card Add/Edit link is not displayed",
						true);
		else if (!Web.isWebElementDisplayed(lnkBeneficiaryCardAddOrEdit))
			Reporter.logEvent(
					Status.PASS,
					"Verify Beneficiaries Card Add/Edit link is displayed or not",
					"Beneficiaries Card Add/Edit link is not displayed", true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Beneficiaries Card Add/Edit link is displayed or not",
					"Beneficiaries Card Add/Edit link is displayed", true);
	}

	public void verifyBenficiaryCardAddnowLink(boolean shouldDisplay) {

		if (shouldDisplay)
			if (Web.isWebElementDisplayed(lnkBeneficiaryCardAddnow, true))
				Reporter.logEvent(
						Status.PASS,
						"Verify Beneficiaries Card Add now link is displayed or not",
						"Beneficiaries Card Add Now link is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Beneficiaries Card Add now link is displayed or not",
						"Beneficiaries Card Add Now link is not displayed",
						true);
		else if (!Web.isWebElementDisplayed(lnkBeneficiaryCardAddnow))
			Reporter.logEvent(
					Status.PASS,
					"Verify Beneficiaries Card Add now link is displayed or not",
					"Beneficiaries Card Add Now link is not displayed", true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Beneficiaries Card Add now link is displayed or not",
					"Beneficiaries Card Add Now link is displayed", true);

		isTextFieldDisplayed("None on file");
	}

	public void deleteAODBeneficiariesFromDB(String ind_id) throws Exception {
		String[] sqlQuery;
		int deleteBene = 0;
		sqlQuery = Stock.getTestQuery("deleteAODBeneficiaries");

		sqlQuery[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		deleteBene = DB.executeUpdate(sqlQuery[0], sqlQuery[1], ind_id);

		if (deleteBene > 0) 
			System.out.println("Deleted the Bene");
		else
			System.out.println("Bene are not deleted");
	}

	// Mosin -This () will change WNBENE Service to false

	public void updateBeneficiary(String plan_id, String termDate)
			throws Exception {
		String[] sqlQuery;
		int updateBene = 0;
		sqlQuery = Stock.getTestQuery("updateWnbene");

		sqlQuery[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		updateBene = DB.executeUpdate(sqlQuery[0], sqlQuery[1], termDate,
				plan_id);
		System.out.println("Number of rows update:" + updateBene);
		if (updateBene > 0) {
			System.out.println("Deleted Exsisting Beneficiary");
		}
	}

	public void getBeneficiaryInfo(String value) {
		// System.out.println(txtOverviewValue.getText());
		if (txtOverviewValue.getText().equalsIgnoreCase(value))
			Reporter.logEvent(Status.PASS,
					"Verify Beneficiaries value is displayed or not",
					"Beneficiaries value is matching. Expected:" + value
							+ "Actual:" + txtOverviewValue.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Beneficiaries value is displayed or not",
					"Beneficiaries value is not matching. Expected:" + value
							+ "Actual:" + txtOverviewValue.getText(), true);

	}

	public void validateBalanceCard() {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			// Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Web.isWebElementDisplayed(txtBalanceCard, true))
			Reporter.logEvent(Status.PASS, "Verify Balance Card is displayed",
					"Balance Card is displayed successfully", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Balance Card is displayed",
					"Balance Card is not displayed", true);

		if (Web.isWebElementDisplayed(txtBalance, true))
			Reporter.logEvent(Status.PASS, "Verify Balance text is displayed",
					"Balance text is displayed successfully. Expected: BALANCE Actual:"
							+ txtBalance.getText(), true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Balance text is displayed",
					"Balance text is not displayed. Expected: BALANCE Actual:"
							+ txtBalance.getText(), true);

	}

	public void validateBalanceCardViewDetailslink() {
		if (Web.isWebElementDisplayed(lnkBalanceCardViewDetails))
			Reporter.logEvent(
					Status.PASS,
					"Verify New balance card display a \"View details\" link, in blue clickable text",
					"Balance card \"View details\" link is displayed successfully in blue clickable text",
					true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify New balance card display a \"View details\" link, in blue clickable text",
					"Balance card \"View details\" link is not displayed  in blue clickable text",
					true);
	}

	public void clickOnBalanceCardViewDeatilsLink() {
		try {
			Web.clickOnElement(lnkBalanceCardViewDetails);
			Common.waitForProgressBar();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public void validateBalanceCardParticipantAmount(String sAmount) {
		if (Web.isWebElementDisplayed(blnCardPtpAmount))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card displayed participant's balance.", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance.", true);

		if (blnCardPtpAmount.getText().equals(sAmount))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card is displayed participant's balance."
							+ "Expected:" + sAmount + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance."
							+ "Expected:" + sAmount + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
	}

	public String getBalanceCardPtpAmnt() {
		return blnCardPtpAmount.getText();
	}

	// mosin
	public void validateBalanceMultiplePlan(ArrayList<String> sAmount, int i) {
		if (Web.isWebElementDisplayed(blnCardPtpAmount))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card displayed participant's balance.", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance.", true);

		if (blnCardPtpAmount.getText().equals(sAmount.get(i)))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card is displayed participant's balance."
							+ "Expected:" + sAmount.get(i) + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance."
							+ "Expected:" + sAmount.get(i) + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
	}

	public void validateBalanceAmountForPlan(String sAmount) {
		if (Web.isWebElementDisplayed(blnCardPtpAmount))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card displayed participant's balance.", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance.", true);

		if (blnCardPtpAmount.getText().equals(sAmount))
			Reporter.logEvent(Status.PASS,
					"Verify Balance card displayed participant's balance",
					"Balance card is displayed participant's balance."
							+ "Expected:" + sAmount + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance card displayed participant's balance",
					"Balance card not displayed participant's balance."
							+ "Expected:" + sAmount + "and Actual:"
							+ blnCardPtpAmount.getText(), true);
	}

	// Mosin - To validate the date
	public void validateDate(String date) {
		if (txtOverviewDate.getText().equalsIgnoreCase("Last updated " + date))
			Reporter.logEvent(Status.PASS,
					"Verify latest updated date in Account overview page",
					"Latest updated date is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify latest updated date in Account overview page",
					"Latest updated date is not displayed", true);
	}

	// Mosin - To click on Add/Edit link and verify it takes us to Beneficiary
	// page
	public void validateAddEditNavigation() {
		Web.waitForElement(lnkBeneficiaryCardAddOrEdit);
		Web.clickOnElement(lnkBeneficiaryCardAddOrEdit);
		if (Web.isWebElementDisplayed(txtBeneficiaries, true))
			Reporter.logEvent(
					Status.PASS,
					"Verify After clicking Beneficiary summary page is displaying.",
					"Beneficiary summary page is displaying", true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify After clicking Beneficiary summary page is displaying.",
					"Beneficiary summary page is not displaying", true);
	}

	// Bhargav-Method to select dropdown from Accounts in Account OverviewPage
	public void selectPlanfromAccountsDropdown(String sDropdownValue) {
		Web.selectDropDownOption(accountsDropdown, sDropdownValue);
		Common.waitForProgressBar();
	}

	public void validateBalanceCardNotDisplayed() {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!Web.isWebElementDisplayed(txtBalanceCard))
			Reporter.logEvent(
					Status.PASS,
					"Verify Balance Card is not displayed in Account Overview Page",
					"Balance Card is not displayed in Account Overview Page",
					true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Balance Card is not displayed in Account Overview Page",
					"Balance Card is displayed in Account Overview Page", true);

	}

	// Method to verify DB Plan selected in Accounts Overview Page-By Bhargav

	public void validateAccOverviewPageforDBPlan(String sDropdownValue,
			Object obj) {

		if (Web.isWebElementDisplayed(hdrAccountOverview, true))
			Reporter.logEvent(Status.PASS,
					"Verify Account Overview Page is displayed",
					"Account Overview Page is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Account Overview Page is displayed",
					"Account Overview Page is not displayed", true);
		// Web.waitForElement(accountsDropdown);
		if (Web.getSelectedDropDownOption(obj, "Accounts Drop Down").equals(
				sDropdownValue))
			Reporter.logEvent(
					Status.PASS,
					"Verify DB Plan is displayed in Account Overview Page as expected",
					"DB Plan is displayed in Account Overview Page as expected",
					true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify DB Plan is displayed in Account Overview Page as expected",
					"DB Plan is not displayed in Account Overview Page as expected",
					true);
	}

	// Mosin - To Verify Statement card
	public void validateStatementCard() {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			// Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Web.isWebElementDisplayed(tagStatementCard, true))
			Reporter.logEvent(Status.PASS,
					"Verify Statement Card is displayed",
					"Statement Card is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Statement Card is displayed",
					"Statement Card is not displayed", true);

		if (Web.isWebElementDisplayed(lblStatementCard, true))
			Reporter.logEvent(Status.PASS,
					"Verify Statement text is displayed",
					"Statement text is displayed. Expected: Statement Actual:"
							+ lblStatementCard.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Statement text is displayed",
					"Statement text is not displayed. Expected: Statement Actual:"
							+ lblStatementCard.getText(), true);
	}

	// Mosin - Verify Delivery Option for Statement Card
	public void verifyDeliveryOption(String sDeliveryText) {
		if (txtStmtDeliveryOptions.getText().equalsIgnoreCase(
				"Delivery options"))
			Reporter.logEvent(
					Status.PASS,
					"Verify Delivery option text in Statement Card",
					"Delivery option text in Statement Card is displayed. Expected: Delivery options Actual:"
							+ txtStmtDeliveryOptions.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Delivery option text in Statement Card",
					"Delivery option text in Statement Card is not displayed. Expected: Delivery options Actual:"
							+ txtStmtDeliveryOptions.getText(), true);

		if (txtStmtBenefit.getText().equalsIgnoreCase(sDeliveryText))
			Reporter.logEvent(
					Status.PASS,
					"Verify Delivery option Sign up text is displayed",
					"Delivery option Sign up text is displayed. Expected: "
							+ sDeliveryText + " Actual: "
							+ txtStmtBenefit.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Delivery option Sign up text is displayed",
					"Delivery option Sign up text is not displayed. Expected: "
							+ sDeliveryText + " Actual: "
							+ txtStmtBenefit.getText(), true);

		isTextFieldDisplayed("More secure delivery of important communications");
		isTextFieldDisplayed("Potentially minimize exposure to identity theft");
		isTextFieldDisplayed("Better document management");
		isTextFieldDisplayed("Less mail to fill your mailbox at home");

	}

	// Mosin - Verify Delivery Option for Statement Card
	public void verifyNonStements() {
		if (txtNoStatements.getText().equalsIgnoreCase("None available"))
			Reporter.logEvent(
					Status.PASS,
					"Verify no statements on Card",
					"No statements are dispalyed on Card."
							+ txtNoStatements.getText() + " text is displayed",
					true);
		else
			Reporter.logEvent(Status.FAIL, "Verify no statements on Card",
					"Statements are dispalyed on Card.", true);
		// System.out.println(lnkShowMoreStatements.size()+"@@@@@@");
		if (!Web.isWebElementDisplayed(lnkShowMoreStatements))
			Reporter.logEvent(Status.PASS,
					"Verify Show more link is not displayed",
					"Show more link is not displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Show more link is not displayed",
					"Show more link is displayed", true);
	}

	// Mosin - Click on Show more link of Statement card
	public void verifyShowMoreLink(boolean click) {
		if (Web.isWebElementDisplayed(lnkShowMoreStatements))
			Reporter.logEvent(Status.PASS,
					"Verify Show more link is displayed",
					"Show more link is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Show more link is displayed",
					"Show more link is not displayed", true);

		if (click) {
			lnkShowMoreStatements.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
		}
			
	}

	public void verifyStmtsareVisible() {
		if (Web.isWebElementDisplayed(lstStatements.get(0), true))
			Reporter.logEvent(Status.PASS,
					"Verify Statements are displayed for Participant",
					lstStatements.size()
							+ " Statements are displayed fpr the ppt", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Statements are displayed for Participant",
					"Statements are not displayed", true);
	}

	public void clickOnFirstStmts() {
		Web.clickOnElement(lstStatementsFirst);
		String getParentWindow = Web.getDriver().getWindowHandle();
		for (String windowHandles : Web.getDriver().getWindowHandles()) {
			Web.getDriver().switchTo().window(windowHandles);
		}
		System.out.println("Statements url" + Web.getDriver().getCurrentUrl());
		if (Web.getDriver().getCurrentUrl()
				.contains(Stock.GetParameterValue("Statements url")))
			Reporter.logEvent(
					Status.PASS,
					"Verify when you click a statement It should open a PDF file in new tab",
					"Opened a PDF file in new tab", false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify when you click a statement It should open a PDF file in new tab",
					"Did not open a PDF file in new tab", true);
		Web.getDriver().close();
		Web.getDriver().switchTo().window(getParentWindow);
		Web.getDriver().switchTo().defaultContent();
	}

	public void changeDeliveryOption(String ssn, String sOption)
			throws Exception {
		String[] sqlQuery;
		int updateDelivery = 0;
		sqlQuery = Stock.getTestQuery("updateDeliveryMethodForParticipant");

		sqlQuery[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		updateDelivery = DB.executeUpdate(sqlQuery[0], sqlQuery[1], sOption,
				ssn);

		if (updateDelivery > 0) {
			System.out.println("Changed the Delivery option to" + sOption);
		}
	}

	public void clickOnEdelivery(boolean click) {
		if (click) {
			if (Web.isWebElementDisplayed(lnkSignupEdelivery, true))
				Reporter.logEvent(Status.PASS,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is not displayed", true);

			Web.clickOnElement(lnkSignupEdelivery);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
		} else {
			if (Web.isWebElementDisplayed(lnkSignupEdelivery, true))
				Reporter.logEvent(Status.PASS,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is displayed", true);
		}
	}

	//Bhargav
	
		/**
		 * <pre>
		 * Method to Verify Rate of Return Card with No Data
		 * </pre>
		 * @author gbhrgv
		 */
		public void verifyRORCardWithNoData()
		{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(rorCardHeader);
			if(Web.isWebElementDisplayed(rorCardHeader,true))
				Reporter.logEvent(Status.PASS,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'",
						"Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'",
						"Rate of return card is not displayed on the Account Overview page with the title - 'RATE OF RETURN'", true);
			if(Web.isWebElementDisplayed(rorCardValuewithNoData,true))
				Reporter.logEvent(Status.PASS,
						"Verify card will display \"Data currently  unavailable\" when there is no ROR data.",
						"Rate of return card displayed with \"Data currently  unavailable\"", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify card will display \"Data currently  unavailable\" when there is no ROR data.",
						"Rate of return card not displayed with \"Data currently  unavailable\"", true);
			
		}
		
		/**
		 * <pre>
		 * Method to Verify Rate of Return Card with Annualized
		 * </pre>
		 * @author gbhrgv
		 */
		public void verifyRORCardAnnualized()
		{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(rorCardHeaderAnnual);
			if(Web.isWebElementDisplayed(rorCardHeaderAnnual,true))
				Reporter.logEvent(Status.PASS,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'ANNUALIZED RATE OF RETURN'",
						"Rate of return card is displayed on the Account Overview page with the title - 'ANNUALIZED RATE OF RETURN'", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'ANNUALIZED RATE OF RETURN'",
						"Rate of return card is not displayed on the Account Overview page with the title - 'ANNUALIZED RATE OF RETURN'", true);
			
		}
		
		
		/**
		 * <pre>
		 * Method to Get Dates from Rate of Return Card 
		 * </pre>
		 * @author gbhrgv
		 */
		public String[] getDatesFromRORCard() throws InterruptedException {
			Thread.sleep(1000);
			Web.waitForElement(rorCardFromandTodate);
			String sDate = Web.getWebElementText(rorCardFromandTodate);
			String sDates[] = sDate.split("-");
			sDates[0]=Common.setSimpleFormatDate(sDates[0],"MM/dd/yyyy");
			sDates[1]=Common.setSimpleFormatDate(sDates[1],"MM/dd/yyyy");
			return sDates;
		}
		
		/**
		 * <pre>
		 * Method to Get % from Rate of Return Card 
		 * </pre>
		 * @author gbhrgv
		 */
		public String getPercentageFromRORCard()
		{
			String sPercentage=Web.getWebElementText(rorpercentage);
			return sPercentage;		
		}
		
		/**
		 * <pre>
		 * Method to click on ROR card view Details link
		 * </pre>
		 * @author gbhrgv
		 */
		public void clickonRORCardViewDetailLink()
		{
			Web.clickOnElement(lnkViewdetailsRorCard);
			Common.waitForProgressBar();
		}
		
		/**
		 * <pre>
		 * Method to Verify ROR Card Not displayed
		 * </pre>
		 * @author gbhrgv
		 */
		public void verifyRORCardNotDisplayed()
		{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(hdrAccountOverview);
			if(!Web.isWebElementDisplayed(sLeftNavRORLink) && !Web.getWebElementText(spagebody).contains("Rate of return"))
				Reporter.logEvent(Status.PASS,
						"Verify the Rate of return card is not displayed on the Account Overview page ",
						"Rate of return card is not displayed on the Account Overview page", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the Rate of return card is not displayed on the Account Overview page ",
						"Rate of return card is displayed on the Account Overview page", true);
			
		}
		
		/**
		 * <pre>
		 * Method to Verify ROR Card Cumulative
		 * </pre>
		 * @author gbhrgv
		 */
		public void verifyRORCardCumulative()
		{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(rorCardHeader);
			if(Web.isWebElementDisplayed(rorCardHeader,true))
				Reporter.logEvent(Status.PASS,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'",
						"Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'",
						"Rate of return card is not displayed on the Account Overview page with the title - 'RATE OF RETURN'", true);
			
		}

	// Mosin - To verify the Loan balance card and text
	public void validateLoansBalanceCard() {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			// Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Web.isWebElementDisplayed(tagLoanBalanceCard, true))
			Reporter.logEvent(Status.PASS, "Verify Balance Card is displayed",
					"Balance Card is displayed successfully", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Balance Card is displayed",
					"Balance Card is not displayed", true);

		if (Web.isWebElementDisplayed(lblLoanBalanceCard, true))
			Reporter.logEvent(Status.PASS, "Verify Balance text is displayed",
					"Balance text is displayed successfully. Expected: LOAN BALANCE Actual:"
							+ lblLoanBalanceCard.getText(), true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Balance text is displayed",
					"Balance text is not displayed. Expected: LOAN BALANCE Actual:"
							+ lblLoanBalanceCard.getText(), true);
	}

	// Mosin - To verify the Loan balance card is not displayed
	public void validateNoLoansBalanceCard() {

		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());

		if (!Web.isWebElementDisplayed(tagLoanBalanceCard))
			Reporter.logEvent(Status.PASS,
					"Verify Balance Card is not displayed",
					"Balance Card is not displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Balance Card is not displayed",
					"Balance Card is displayed", true);

	}

	// Mosin - To click on view details link of loan balance card
	public void clickOnLoanViewDetailsLink(boolean click)
			throws InterruptedException {
		if (click) {
			if (Web.isWebElementDisplayed(lnkViewDetailsLoans, true))
				Reporter.logEvent(Status.PASS,
						"Verify View details link is displayed",
						"View details link is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify View details link is displayed",
						"View details link is not displayed", true);
			
			Web.clickOnElement(lnkViewDetailsLoans);
			Thread.sleep(5000);
			Web.waitForPageToLoad(Web.getDriver());
			Common.waitForProgressBar();
		} else {
			if (!Web.isWebElementDisplayed(lnkViewDetailsLoans, true))
				Reporter.logEvent(Status.PASS,
						"Verify View details link is not displayed",
						"View details link is not displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify View details link is not displayed",
						"View details link is displayed", true);
		}
	}

	// Mosin - To verify Maturity Dates
	public void verifyMaturityDates() {
		if (Web.isWebElementDisplayed(txtMaturityDateLoans, true))
			Reporter.logEvent(Status.PASS, "Verify Maturity date is displayed",
					"Maturity date is displayed", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Maturity date is displayed",
					"Maturity date is not displayed", true);
	}

	// Mosin - To verify Loan balance
	public String verifyLoanBalance() {
		if (Web.isWebElementDisplayed(txtLoanBalanceAmount, true))
			Reporter.logEvent(Status.PASS,
					"Verify total Loan balance amount is displayed",
					"Loan balance amount is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify total Loan balance amount is displayed",
					"Loan balance amount is not displayed", true);
		System.out.println(Web.getIntegerCurrency(txtLoanBalanceAmount
				.getText()));
		return Web.getWebElementText(txtLoanBalanceAmount);
	}

	// Mosin - To verify Transaction card Heading's
	public void verifyTranscationHeading() {
		for (int i = 0; i < this.lstTransactionHeading.size(); i++) {
			if (i == 0) {
				if (lstTransactionHeading.get(i).getText()
						.equalsIgnoreCase("Confirmation number"))
					Reporter.logEvent(Status.PASS,
							"Verify Transaction card has Confirmation Number",
							"Transaction card has hrading Confirmation Number",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Transaction card has Confirmation Number",
							"Transaction card hrading Confirmation Number is not displayed",
							true);
			} else if (i == 1) {
				if (lstTransactionHeading.get(i).getText()
						.equalsIgnoreCase("Type"))
					Reporter.logEvent(Status.PASS,
							"Verify Transaction card has Heading Type",
							"Transaction card has Heading Type", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Transaction card has Heading Type",
							"Transaction card Heading TYPE is not displayed",
							true);
			} else if (i == 2) {
				if (lstTransactionHeading.get(i).getText()
						.equalsIgnoreCase("Amount"))
					Reporter.logEvent(Status.PASS,
							"Verify Transaction card has Amount",
							"Transaction card has heading AMOUNT", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Transaction card has Heading Amount",
							"Transaction card Heading AMOUNT is not displayed",
							true);
			} else if (i == 3) {
				if (lstTransactionHeading.get(i).getText()
						.equalsIgnoreCase("Effective date"))
					Reporter.logEvent(Status.PASS,
							"Verify Transaction card has Effective date",
							"Transaction card has Heading Effective date",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Transaction card has Heading Effective date",
							"Transaction card has Heading Effective date is not displayed",
							true);
			}
		}
	}

	// Mosin - To verify Transaction history
	public void verifyTranscationHistory() {
		for (int i = 0; i < this.lstTransactionHistory.size(); i++) {
			String itr = Integer.toString(i + 1);
			List<WebElement> txtTransactionData = Web.getDriver().findElements(
					By.xpath(lstTransactionHistoryData
							.replace("Iteration", itr)));
			for (int j = 0; j < txtTransactionData.size(); j++) {
				if (j == 0) {
					if (!txtTransactionData.get(j).getText().isEmpty())
						Reporter.logEvent(
								Status.PASS,
								"Verify Transaction card has Confirmation Number",
								itr
										+ " Transaction history has Confirmation Number",
								false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify Transaction card has Confirmation Number",
								itr
										+ " Transaction history doesn't have Confirmation Number",
								true);
				} else if (j == 1) {
					if (!txtTransactionData.get(j).getText()
							.equalsIgnoreCase("Type"))
						Reporter.logEvent(Status.PASS,
								"Verify Transaction card has Heading Type", itr
										+ " Transaction card has Heading Type",
								false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify Transaction card has Heading Type",
								itr
										+ " Transaction card Heading TYPE is not displayed",
								true);
				} else if (j == 2) {
					if (!txtTransactionData.get(j).getText()
							.equalsIgnoreCase("Amount"))
						Reporter.logEvent(Status.PASS,
								"Verify Transaction card has Amount",
								itr + " Verify Transaction card has Amount",
								false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify Transaction card has Heading Amount",
								itr
										+ " Transaction card Heading AMOUNT is not displayed",
								true);
				} else if (j == 3) {
					if (!txtTransactionData.get(j).getText()
							.equalsIgnoreCase("Effective date"))
						Reporter.logEvent(
								Status.PASS,
								"Verify Transaction card has Effective date",
								itr
										+ " Verify Transaction card has Effective date",
								false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify Transaction card has Heading Effective date",
								itr
										+ " Transaction card Heading Effective date is not displayed",
								true);
				}
			}
		}
	}

	// Mosin - To verify the Transaction History card and text
	public void validateTransactionHistoryCard() {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			// Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Web.isWebElementDisplayed(tagTransactionHistoryCard, true))
			Reporter.logEvent(Status.PASS,
					"Verify Transaction History Card is displayed",
					"Transaction History is displayed successfully", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Transaction History Card is displayed",
					"Transaction History is displayed successfully", true);

		if (Web.isWebElementDisplayed(lblTransactionHistoryCard, true))
			Reporter.logEvent(
					Status.PASS,
					"Verify Transaction History text is displayed",
					"Transaction History text is displayed successfully. Expected: TRANSACTION HISTORY Actual:"
							+ lblTransactionHistoryCard.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Transaction History text is displayed",
					"Transaction History text is not displayed. Expected: TRANSACTION HISTORY Actual:"
							+ lblTransactionHistoryCard.getText(), true);
	}

	// Mosin - To click on show more link of Transaction History card
	public void clickOnTransactionShowMoreLink(boolean click)
			throws InterruptedException {
		if (click) {
			if (Web.isWebElementDisplayed(lnkTransactionShowMore, true))
				Reporter.logEvent(Status.PASS,
						"Verify show more link is displayed",
						"show more link is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify View details link is displayed",
						"View details link is not displayed", true);

			Web.clickOnElement(lnkTransactionShowMore);
			Thread.sleep(5000);
			Web.waitForPageToLoad(Web.getDriver());
			Common.waitForProgressBar();
		} else {
			if (!Web.isWebElementDisplayed(lnkTransactionShowMore, true))
				Reporter.logEvent(Status.PASS,
						"Verify show more link is not displayed",
						"show more link is not displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify show more link is not displayed",
						"show more link is displayed", true);
		}
	}

	// Mosin - To verify the Transaction History card is not displayed
	public void validateNoTransactionHistoryCard() {

		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());

		if (!Web.isWebElementDisplayed(tagTransactionHistoryCard))
			Reporter.logEvent(Status.PASS,
					"Verify Transaction History Card is not displayed",
					"Transaction History Card is not displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Transaction History Card is not displayed",
					"Transaction History Card is displayed", true);

	}

	// Mosin - To verify Transaction history
	public ArrayList<String> verifyTranscationHistoryValues()
			throws ParseException {
		ArrayList<String> lstValues = new ArrayList<String>();
		for (int i = 0; i < this.lstTransactionHistory.size(); i++) {

			String itr = Integer.toString(i + 1);
			List<WebElement> txtTransactionData = Web.getDriver().findElements(
					By.xpath(lstTransactionHistoryData
							.replace("Iteration", itr)));
			for (int j = 0; j < txtTransactionData.size(); j++) {
				if (j == 3) {
					Date date1 = new SimpleDateFormat("MM/dd/yyyy")
							.parse(txtTransactionData.get(j).getText());
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String strDate = dateFormat.format(date1);
					System.out.println(strDate);
					lstValues.add(strDate);
				} else
					lstValues.add(txtTransactionData.get(j).getText());
			}
			break;
		}
		return lstValues;
	}

	// Mosin - To verify the Contribution History card and text
	public void validateContributionCard(String year) {
		try {
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			// Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Web.isWebElementDisplayed(cardContribution, true))
			Reporter.logEvent(Status.PASS,
					"Verify Contribution Card is displayed",
					"Contribution card is displayed successfully", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Contribution Card is displayed",
					"Contribution card is displayed successfully", true);

		if (Web.getWebElementText(lblContributionCard).equalsIgnoreCase(
				year + " CONTRIBUTIONS") || Web.getWebElementText(lblContributionCard).equalsIgnoreCase(
						year + " ESTIMATED CONTRIBUTIONS") )
			Reporter.logEvent(Status.PASS,
					"Verify Contribution text is displayed",
					"Contribution text is displayed successfully. Expected: "
							+ year + " CONTRIBUTIONS. Actual:"
							+ lblContributionCard.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Contribution text is displayed",
					"Contribution text is displayed successfully. Expected: "
							+ year + " CONTRIBUTIONS. Actual:"
							+ lblContributionCard.getText(), true);
	}

	public void verifyDonutChart(String sCardType) {
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(chartContirbutionDonut.replace("CardType", sCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify Standard Contribution Donut chart is displayed",
					"Standard Contribution Donut chart is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Standard Contribution Donut chart is displayed",
					"Standard Contribution Donut chart is not displayed", true);
	}

	public int verifyYeartToDate(String sCardType) throws Exception {
		String[] yearToDate = Stock.getTestQuery("getYearToDate");
		String sYTD = null;
		yearToDate[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_ACHAmount = DB.executeQuery(yearToDate[0], yearToDate[1],
				Stock.GetParameterValue("ind_ID"),sCardType);
		Thread.sleep(5000);// It is taking time to run the query.
		rs_ACHAmount.first();
		try {
			sYTD = rs_ACHAmount.getString("CONT_AMT");
		} catch (SQLException e) {
			sYTD = "0";
		}
		
		int dbYTD = (int) Math.round(Double.parseDouble(sYTD));
		int uiYTD = (int) Web.getIntegerCurrency(Web.getDriver().
				findElement(By.xpath(amountYearToDate.replace("CardType", sCardType))).getText());
		if (Web.getDriver().
				findElement(By.xpath(txtYearToDate.replace("CardType", sCardType))).isDisplayed())
			Reporter.logEvent(Status.PASS,
					"Verify Year-to-date contributions text is displayed",
					"Year-to-date contributions text is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Year-to-date contributions text is displayed",
					"Year-to-date contributions text is not displayed", true);
		if (dbYTD == uiYTD)
			Reporter.logEvent(Status.PASS,
					"Verify Year-to-date contribution amount is matching DB",
					"Year-to-date contribution amount is matching in DB. Expected: "
							+ dbYTD + " Actual:" + uiYTD, false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Year-to-date contribution amount is matching DB",
					"Year-to-date contribution amount is not matching to the DB values. Expected: "
							+ dbYTD + " Actual:" + uiYTD, true);

		return uiYTD;
	}


	/**
	 * <pre>Formula to Calculate sPercent = salary's contribution percentage(25000's
	 * 5%) If it is By weekly so 26 weeks:sPercent = sPercent/26 How many weeks
	 * remaining sPercent = sPercent*14 -> 14 or 13 need to check from Network
	 * tab before running sPercent = sPercent+Year-To-date;</pre>
	 * @param sSalary
	 * @param yearToDate
	 * @param sCardType
	 * @param payRoll
	 * @param remainingPayRoll
	 * @param sIRSLimit
	 * @return
	 */
	public String verifyOnTrackToContribute(String sSalary, int yearToDate, String sCardType,
			int payRoll, int remainingPayRoll, String sIRSLimit) throws Exception {
		// sContribution = Contribution from Donut
		String sContribution=null;
		String sRemainingAmount[];
		//If there are multiple After tax contributions we need to click on Show more link
		if(sCardType.equalsIgnoreCase("AFTERTAX"))
		{
			try{
				Web.clickOnElement(Web.getDriver().
						findElement(By.xpath(showMorelnk.replace("CardType", sCardType))));
			}
			catch(NoSuchElementException e){
				Reporter.logEvent(Status.INFO,"Click on Show more Link",
						"Click on Show more Link", false);
				}
		}
		//To get the contribution value from donut if it is not in donut to get from inner text
		try{
			sContribution = Web.getDriver().
					findElement(By.xpath(txtDonutContribution.replace("CardType", sCardType))).getText().replaceAll("[$,%,*]", "");
		}
		catch(NoSuchElementException e) 
		{
			System.out.println("In else part of multiple contri");
			sContribution = Web.getDriver().
					findElement(By.xpath(txtContributionValue.replace("CardType", sCardType).replace("CardSubType", sCardType))).getText();
			System.out.println("Contribution from UI: " + sContribution);
			sRemainingAmount = sContribution.split("-");
			System.out.println("Contribution from UI: " + sRemainingAmount[1]);
			sContribution = sRemainingAmount[1].replaceAll("[$,%,*]", "").trim();
		}
		String sFinalOntrack = null;
		System.out.println("Contribution from UI: " + sContribution);
		double sPercent = ((Double.parseDouble(sSalary)) / 100)
				* Double.parseDouble(sContribution);
		sFinalOntrack = String.valueOf((int) Math.round((sPercent / payRoll) * remainingPayRoll)
				+ yearToDate);
		System.out.println("Final On trak from calculation: " + sFinalOntrack);
		String sFinalOnTrackFromUI = String.valueOf((int) Web
				.getIntegerCurrency(Web.getDriver().
						findElement(By.xpath(amountOnTrack.replace("CardType", sCardType))).getText()));
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(txtOnTrack.replace("CardType", sCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is not displayed", true);
		
		String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
        //For 401A Plan we will not have IRS Limit Validation
        if(!sPlanCode.equalsIgnoreCase("401A")||!sPlanCode.equalsIgnoreCase("457"))
        {
               //If contribution on track exceeds the actual on track and After tax will not have IRSLimit
         if(!sCardType.equalsIgnoreCase("AfterTax"))
         {
                if(Integer.parseInt(sFinalOntrack)>=Integer.parseInt(sIRSLimit))
                {
                      sFinalOntrack=sIRSLimit;
                }
         }
         

         if (sFinalOnTrackFromUI.equalsIgnoreCase(sFinalOntrack))
                Reporter.logEvent(
                             Status.PASS,
                             "Verify On track to contribute amount is matching as per the calculation",
                             "On track to contribute amount is matching as per the calculation. Expected: "
                                           + sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
                             true);
         else
                Reporter.logEvent(
                             Status.FAIL,
                             "Verify On track to contribute amount is correct",
                             "On track to contribute amount is not matching as per the calculation. Expected: "
                                           + sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
                             true);
        }
         return sFinalOntrack;
       }


	public void verifyIRSLimit(String sContributionType, String expIRSAmount, String sCardType) {
		if(!sCardType.equalsIgnoreCase("AfterTax"))
		{
			int actIRSAmount = (int) Web.getIntegerCurrency(Web.getDriver().
					findElement(By.xpath(amountIRSLimit.replace("CardType", sCardType)))
					.getText());
			if (Web.isWebElementDisplayed(Web.getDriver().
					findElement(By.xpath(txtIRSLimit.replace("CardType", sCardType))), true))
				Reporter.logEvent(Status.PASS,
						"Verify IRS limit text is displayed",
						"IRS limit text is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify IRS limit text is displayed",
						"IRS limit text is not displayed", true);

			if (actIRSAmount == Integer.parseInt(expIRSAmount))
				Reporter.logEvent(Status.PASS, "Verify IRS limit is matching",
						"IRS limit amount is matching. Expected: " + expIRSAmount
								+ " Actual:" + actIRSAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify IRS limit is matching",
						"IRS limit amount is not matching. Expected: "
								+ expIRSAmount + " Actual:" + actIRSAmount, true);
		}
		
	}
	
	//Apple - IRS limit
	
	public void verifyIRSLimit_Apple(String sContributionType, String expIRSAmount, String sCardType) {
		if(!sCardType.equalsIgnoreCase("AfterTax"))
		{
			int actIRSAmount = (int) Web.getIntegerCurrency(Web.getDriver().
					findElement(By.xpath(amountIRSLimitApple.replace("CardType", sCardType)))
					.getText());
			if (Web.isWebElementDisplayed(Web.getDriver().
					findElement(By.xpath(txtIRSLimitApple.replace("CardType", sCardType))), true))
				Reporter.logEvent(Status.PASS,
						"Verify IRS limit text is displayed",
						"IRS limit text is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify IRS limit text is displayed",
						"IRS limit text is not displayed", true);

			if (actIRSAmount == Integer.parseInt(expIRSAmount))
				Reporter.logEvent(Status.PASS, "Verify IRS limit is matching",
						"IRS limit amount is matching. Expected: " + expIRSAmount
								+ " Actual:" + actIRSAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify IRS limit is matching",
						"IRS limit amount is not matching. Expected: "
								+ expIRSAmount + " Actual:" + actIRSAmount, true);
		}
		
	}

	public void verifyDescriptionFullmatchQuestion(String sCardType) {
		if(sCardType.equalsIgnoreCase("Standard"))
		{
			if (Web.getWebElementText(txtDescriptionCompanyMatch).equalsIgnoreCase(
					"Are you getting your full match?"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Full match question text is displayed",
						"Full match question text is displayed. Expected: Are you getting your full match?"
								+ " Actual:"
								+ Web.getWebElementText(txtDescriptionCompanyMatch),
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Full match question text is displayed",
						"Full match question text is not matching. Expected: Are you getting your full match?"
								+ " Actual:"
								+ Web.getWebElementText(txtDescriptionCompanyMatch),
						true);
		}
		
	}

	public void verifyHowMuchToContribute(String sOnTrack, String sIRSAmount, String sCardType) {
		if(!sCardType.equalsIgnoreCase("AfterTax"))
		{
			int sRemaining = Integer.parseInt(sIRSAmount)
					- Integer.parseInt(sOnTrack);
			String sExpectedAmount = null;
			Locale locale = new Locale("en", "US");
			NumberFormat currencyFormatter = NumberFormat
					.getCurrencyInstance(locale);
			String sAmount = currencyFormatter.format(sRemaining);
			String sRemainingAmount[] = sAmount.split("\\.");
			System.out.println("On track from Method:"+sOnTrack+"IRS from Test data"+sIRSAmount);
			if (sOnTrack.equalsIgnoreCase(sIRSAmount)) {
				String sFinalAmount = currencyFormatter.format(Integer.parseInt(sOnTrack));
				String sRemainingAmountFull[] = sFinalAmount.split("\\.");
				sExpectedAmount = "Congratulations you're on track to reach the "
						+ sRemainingAmountFull[0] + " IRS limit this year.";
			} else
				sExpectedAmount = "You can contribute an additional "
						+ sRemainingAmount[0] + " this year.";

			String sActualAmount = (Web.getWebElementText(Web.getDriver().
					findElement(By.xpath(txtDescriptionOnTrack.replace("CardType", sCardType)))));
			if (sExpectedAmount.equalsIgnoreCase(sActualAmount))
				Reporter.logEvent(Status.PASS,
						"Verify how much to contribute text is matching UI",
						"How much to contribute text is matching UI. Expected:"
								+ sExpectedAmount + " Actual:" + sActualAmount,
						false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify how much to contribute text is matching UI",
						"How much to contribute text is not matching UI. Expected:"
								+ sExpectedAmount + " Actual:" + sActualAmount,
						true);
		}
	}
	
	public void verifyMaximizerText(String sCardType) throws InterruptedException {
		if(!sCardType.equalsIgnoreCase("STANDARD"))
			Web.clickOnElement(Web.getDriver().findElement(
				By.xpath(showMorelnk.replace("CardType", sCardType))));
		Web.waitForPageToLoad(Web.getDriver());
		WebElement ele = Web.getDriver().findElement(
				By.xpath(txtMaximizer.replace("CardType", sCardType)));
		Web.waitForElement(ele);
		Thread.sleep(3000);
		String ssMaxText = "You are enrolled in Maximize Me Always that automatically optimizes your rate to meet the IRS limit.";
		if (Web.isWebElementDisplayed(ele, true))
			if (ele.getText()
					.equalsIgnoreCase(
							ssMaxText))
				Reporter.logEvent(Status.PASS,
						"Verify Maximizer text is displayed",
						"Maximizer text is matching for "+sCardType+". Expected: "+ssMaxText+" Actual: "+ele.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximizer text is displayed",
						"Maximizer text is not matching for "+sCardType+". Expected: "+ssMaxText+" Actual: "+ele.getText(), true);

	}
	//Apple - Additional contribution
	
	public void verifyHowMuchToContribute_Apple(String sOnTrack, String sIRSAmount, String sCardType) {
		if(!sCardType.equalsIgnoreCase("AfterTax"))
		{
			String sRemainingExpected = String.valueOf(Integer.parseInt(sIRSAmount)
					- Integer.parseInt(sOnTrack));
			String sRemainingActual = String.valueOf((int) Math.round(Web.getIntegerCurrency(Web.getWebElementText((Web.getDriver().
					findElement(By.xpath(amountAdditionalApple.replace("CardType", sCardType))))))));
			
			System.out.println("Expected"+sRemainingExpected+"Actual"+sRemainingActual);
			if (Web.isWebElementDisplayed(Web.getDriver().
					findElement(By.xpath(txtAdditionalApple.replace("CardType", sCardType))), true))
				Reporter.logEvent(Status.PASS,
						"Verify Additional contribution text is displayed",
						"'You can contribute an additional' text is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Additional contribution text is displayed",
						"'You can contribute an additional' text is displayed", true);
			

			if (sRemainingActual.equalsIgnoreCase(sRemainingExpected))
				Reporter.logEvent(Status.PASS, "Verify Additional contribution is matching",
						"Additional contribution amount is matching. Expected: " + sRemainingExpected
								+ " Actual:" + sRemainingActual, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Additional contribution is matching",
						"Additional contribution amount is not matching. Expected: " + sRemainingExpected
						+ " Actual:" + sRemainingActual,true);
			
		
		}
	}

	public void verifyContributionModelData(String sCardType) {
		
		if(sCardType.equalsIgnoreCase("STANDARD"))
		{
			if (Web.getWebElementText(txtModelContribution).equalsIgnoreCase(
					"Model contribution rate change"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Model contribution rate change text is matching UI",
						"Model contribution rate change text is matching UI. Expected: Model contribution rate change"
								+ " Actual:"
								+ Web.getWebElementText(txtModelContribution), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Model contribution rate change text is matching UI",
						"Model contribution rate change text is not matching the UI. Expected: Model contribution rate change"
								+ " Actual:"
								+ Web.getWebElementText(txtModelContribution), true);

			if (Web.isWebElementDisplayed(txtModelContributionMinus))
				Reporter.logEvent(Status.PASS,
						"Verify there is \"-\" button in UI",
						"\"-\" button is displayed in UI", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify there is \"-\" button in UI",
						"\"-\" button is not displayed in UI", true);

			if (Web.isWebElementDisplayed(txtModelContributionPlus))
				Reporter.logEvent(Status.PASS,
						"Verify there is \"+\" button in UI",
						"\"+\" button is displayed in UI", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify there is \"+\" button in UI",
						"\"+\" button is not displayed in UI", true);
		}/*else if(sCardType.equalsIgnoreCase("CATCH-UP"))
		{
			if (!Web.getWebElementText(txtModelContribution).equalsIgnoreCase(
					"Model contribution rate change"))
				Reporter.logEvent(Status.PASS,"Verify Model contribution is not displayed for Cacth-UP",
						"Model contribution is not displayed for Cacth-UP", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Model contribution is not displayed for Cacth-UP",
						"Model contribution is displayed for Cacth-UP", true);
		}*/
		
	}

	public void verifyContributionModelData_Apple(String sCardType) {
		try{
		if(Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(showMorelnk.replace("CardType", sCardType)))))
		{
			Web.clickOnElement(Web.getDriver().
					findElement(By.xpath(showMorelnk.replace("CardType", sCardType))));
			
		}}
		catch(NoSuchElementException e)
		{
			Reporter.logEvent(Status.INFO,"Show more link is not displayed for Standard",
					"Show more link is not displayed for Standard", false);
		}
		if (Web.getWebElementText(txtModelContribution).equalsIgnoreCase(
				"Model contribution rate change"))
			Reporter.logEvent(
					Status.PASS,
					"Verify Model contribution rate change text is matching UI",
					"Model contribution rate change text is matching UI. Expected: Model contribution rate change"
							+ " Actual:"
							+ Web.getWebElementText(txtModelContribution),
					false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Model contribution rate change text is matching UI",
					"Model contribution rate change text is not matching the UI. Expected: Model contribution rate change"
							+ " Actual:"
							+ Web.getWebElementText(txtModelContribution), true);

		if (Web.isWebElementDisplayed(txtModelContributionMinus))
			Reporter.logEvent(Status.PASS,
					"Verify there is \"-\" button in UI",
					"\"-\" button is displayed in UI", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify there is \"-\" button in UI",
					"\"-\" button is not displayed in UI", true);

		if (Web.isWebElementDisplayed(txtModelContributionPlus))
			Reporter.logEvent(Status.PASS,
					"Verify there is \"+\" button in UI",
					"\"+\" button is displayed in UI", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify there is \"+\" button in UI",
					"\"+\" button is not displayed in UI", true);

	}

	// Mosin - To click on View details link and verify it takes us to Contribution page
	public void validateContributionViewDetailsLink() {
		Web.waitForElement(lnkContributionViewDetails);
		Web.clickOnElement(lnkContributionViewDetails);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		if (Web.isWebElementDisplayed(lblMyContributions, true))
			Reporter.logEvent(
					Status.PASS,
					"Verify After clicking My Contributions page is displaying.",
					"My Contributions page is displaying", false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify After clicking My Contributions page is displaying.",
					"My Contributions page is not displaying", true);
	}
	// Mosin - To verify contribution type for multiple type on contributions in a single Card
	public void validateContributionMultiple(String sCardType, String sSubCardType) {
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(txtContributionValue.replace("CardType", sCardType).replace("CardSubType", sSubCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify multiple contribution's are displyed in one Card.",
					sSubCardType+" Contribution is displayed", false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify multiple contribution's are displyed in one Card.",
					sSubCardType+" Contribution is not displayed", true);
	}	
	public void clickmaxButton(String sCardType, boolean click)
	{
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(maxButton.replace("CardType", sCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify Maximize me Always button is displayed",
					"Maximize me Always button is displayed", false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Maximize me Always button is displayed",
					"Maximize me Always button is not displayed", true);
		
		if(click)
		{
			Web.clickOnElement(Web.getDriver().
					findElement(By.xpath(maxButton.replace("CardType", sCardType))));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
		}
		
	}
	//To validate if maximize button should not display
	public void validateMaximizerButtonNotDisplayed(String sCardType)
	{
		try{
		Web.getDriver().
				findElement(By.xpath(maxButton.replace("CardType", sCardType)));
		Reporter.logEvent(
				Status.FAIL,
				"Verify Maximize me Always button is not displayed",
				"Maximize me Always button is displayed", true);
		}
		catch(NoSuchElementException e)
		{
			Reporter.logEvent(Status.PASS,
					"Verify Maximize me Always button is not displayed",
					"Maximize me Always button is not displayed", false);
		}		
	}
	/**
	 * <pre>Formula to Calculate On track to contribute amount in dollars
	 * FinalOntrack= (Contribution Rate_Dollar) * (Remaining PayPeriod)</pre>
	 * @param sCardType
	 * @param remainingPayRoll
	 * @return
	 */
	public String verifyOnTrackToContribute(String sCardType,
			int remainingPayRoll) {
		// sContribution = Contribution from Donut
		String sContribution=null;
		//If there are multiple After tax contributions we need to click on Show more link
		if(sCardType.equalsIgnoreCase("AFTERTAX"))
		{
			try{
				Web.clickOnElement(Web.getDriver().
						findElement(By.xpath(showMorelnk.replace("CardType", sCardType))));
			}
			catch(NoSuchElementException e){
				Reporter.logEvent(Status.INFO,"Click on Show more Link",
						"Click on Show more Link", false);
				}
		}
		//To get the contribution value from donut if it is not in donut to get from inner text
		try{
			sContribution = Web.getDriver().
					findElement(By.xpath(txtDonutContribution.replace("CardType", sCardType))).getText().replaceAll("[$,%,*]", "");
		}
		catch(NoSuchElementException e) 
		{
			e.printStackTrace();
		}
		String  sFinalOntrack ;
		sContribution=Stock.GetParameterValue("Contribution Rate_Dollar");
		sFinalOntrack = String.valueOf((Integer.parseInt(sContribution)  * remainingPayRoll));
		System.out.println("Final On track from calculation: " + sFinalOntrack);
		String sFinalOnTrackFromUI = String.valueOf((int) Web
				.getIntegerCurrency(Web.getDriver().
						findElement(By.xpath(amountOnTrack.replace("CardType", sCardType))).getText()));
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(txtOnTrack.replace("CardType", sCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is not displayed", true);
		
		

		if (sFinalOnTrackFromUI.equalsIgnoreCase(sFinalOntrack))
			Reporter.logEvent(
					Status.PASS,
					"Verify On track to contribute amount is matching as per the calculation",
					"On track to contribute amount is matching as per the calculation. Expected: "
							+ sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
					true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify On track to contribute amount is correct",
					"On track to contribute amount is not matching as per the calculation. Expected: "
							+ sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
					true);
		return sFinalOntrack;
	}	
	/**
	 * <pre>Formula to Calculate sPercent = salary's contribution percentage(25000's
	 * 5%) If it is By weekly so 26 weeks:sPercent = sPercent/26 How many weeks
	 * remaining sPercent = sPercent*14 -> 14 or 13 need to check from Network
	 * tab before running sPercent = sPercent+Year-To-date;</pre>
	 * @param sSalary
	 * @param yearToDate
	 * @param sCardType
	 * @param payRoll
	 * @param remainingPayRoll
	 * @param sIRSLimit
	 * @return
	 */
	public String verifyOnTrackToContributeCSOR(String sServiceValue, int yearToDate, String sCardType,
			int payRoll, int remainingPayRoll, String sIRSLimit) throws Exception{
		
		//If there are multiple After tax contributions we need to click on Show more link
		if(sCardType.equalsIgnoreCase("AFTERTAX"))
		{
			try{
				Web.clickOnElement(Web.getDriver().
						findElement(By.xpath(showMorelnk.replace("CardType", sCardType))));
			}
			catch(NoSuchElementException e){
				Reporter.logEvent(Status.INFO,"Click on Show more Link",
						"Click on Show more Link", false);
				}
		}
		String sFinalOntrack = null;
		double sPercent = Double.parseDouble(sServiceValue);
		System.out.println("Value from services"+sPercent);
		System.out.println("With out round off"+(sPercent / payRoll) * remainingPayRoll);
		sFinalOntrack = String.valueOf((int) Math.round((sPercent / payRoll) * remainingPayRoll)
				+ yearToDate);
		System.out.println("Final On trak from calculation: " + sFinalOntrack);
		String sFinalOnTrackFromUI = String.valueOf((int) Web
				.getIntegerCurrency(Web.getDriver().
						findElement(By.xpath(amountOnTrack.replace("CardType", sCardType))).getText()));
		if (Web.isWebElementDisplayed(Web.getDriver().
				findElement(By.xpath(txtOnTrack.replace("CardType", sCardType))), true))
			Reporter.logEvent(Status.PASS,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify On track to contribute text is displayed",
					"On track to contribute text is not displayed", true);
		
		String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
        
        //For 401A Plan we will not have IRS Limit Validation
        if(!sPlanCode.equalsIgnoreCase("401A"))
        {
          //If contribution on track exceeds the actual on track and After tax will not have IRSLimit
         if(!sCardType.equalsIgnoreCase("AfterTax"))
         {
                if(Integer.parseInt(sFinalOntrack)>=Integer.parseInt(sIRSLimit))
                {
                      sFinalOntrack=sIRSLimit;
                }
         }
         

         if (sFinalOnTrackFromUI.equalsIgnoreCase(sFinalOntrack))
                Reporter.logEvent(
                             Status.PASS,
                             "Verify On track to contribute amount is matching as per the calculation",
                             "On track to contribute amount is matching as per the calculation. Expected: "
                                           + sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
                             true);
         else
                Reporter.logEvent(
                             Status.FAIL,
                             "Verify On track to contribute amount is correct",
                             "On track to contribute amount is not matching as per the calculation. Expected: "
                                           + sFinalOntrack + " Actual:" + sFinalOnTrackFromUI,
                             true);
        }
         return sFinalOntrack;
	}

	/**
	 * <pre>Method to Validate ContributionCard not displayed</pre>
     *@author gbhrgv
	 * @return
	 */
	public void validateContributionCardNotDisplayed()
	{
			try {
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				// Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!Web.isWebElementDisplayed(cardContribution))
				Reporter.logEvent(Status.PASS,
						"Verify Contribution Card is not displayed in Account Overview page.",
						"Contribution card is not displayed in Account Overview page.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Contribution Card is not displayed in Account Overview page.",
						"Contribution card is displayed in Account Overview page.", true);		
	}

	/**
	 * Method returns OnTrackFrom UI
	 * 
	 * @param sCardType
	 * @return OnTrack Amount as String From UI
	 * @throws Exception
	 */
	public String getFinalOnTrackFromUI(String sCardType) throws Exception {
		Common.waitForProgressBar();
		String sFinalOnTrackFromUI = String.valueOf((int) Web
				.getIntegerCurrency(Web
						.getDriver()
						.findElement(
								By.xpath(amountOnTrack.replace("CardType",
										sCardType))).getText()));
		return sFinalOnTrackFromUI;
	}
	/**
     * <pre>Method to Validate Contributions Card with Zero YTD with non-zero prior plan contributions(i.e Account overview contributions card zero YTD with non-zero prior plan contributions) </pre>
     * @param sCardType
     * @return
     * @throws Exception
     */
     public int verifyZeroYTDwithNonzeroPriorPlanContributions(String sCardType,int priorPlanContributionAmnt) throws Exception {
            String[] yearToDate = Stock.getTestQuery("getYearToDate");
            String sYTD = null;
            yearToDate[0] = Common.getParticipantDBName(Stock
                         .GetParameterValue("userName"))
                         + "DB_"
                         + Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
            ResultSet rs_ACHAmount = DB.executeQuery(yearToDate[0], yearToDate[1],
                         Stock.GetParameterValue("ind_ID"),sCardType);
            Thread.sleep(5000);// It is taking time to run the query.
            rs_ACHAmount.first();
            try {
                   sYTD = rs_ACHAmount.getString("CONT_AMT");
            } catch (SQLException e) {
                   sYTD = "0";
            }
            
            int dbYTD = (int) Math.round(Double.parseDouble(sYTD));
            int zYTD=priorPlanContributionAmnt+dbYTD;
            int uiYTD = (int) Web.getIntegerCurrency(Web.getDriver().
                         findElement(By.xpath(amountYearToDate.replace("CardType", sCardType))).getText());
            if (Web.getDriver().
                         findElement(By.xpath(txtYearToDate.replace("CardType", sCardType))).isDisplayed())
                   Reporter.logEvent(Status.PASS,
                                "Verify Year-to-date contributions text is displayed",
                                "Year-to-date contributions text is displayed", false);
            else
                   Reporter.logEvent(Status.FAIL,
                                "Verify Year-to-date contributions text is displayed",
                                "Year-to-date contributions text is not displayed", true);
            if (zYTD == uiYTD)
                   Reporter.logEvent(Status.PASS,
                                "Verify Year-to-date contribution amount card with zero YTD",
                                "Year-to-date contribution amount card with zero YTD is displayed. Expected: "
                                              + zYTD + " Actual:" + uiYTD, false);
            else
                   Reporter.logEvent(Status.FAIL,
                                "Verify Year-to-date contribution amount card with zero YTD",
                                "Year-to-date contribution amount is not matching to the DB values. Expected: "
                                              + zYTD + " Actual:" + uiYTD, true);

            return uiYTD;
     }
     /**
 	 * <pre>
 	 * Method to Validate PriorPlan Contributions Message  in Contributions Page displayed or not
 	 * </pre>
 	 * @Author - Bhargav
 	 * @throws InterruptedException
 	 */
 	public void validateEditContributionsforViewOnlyDeferral(String sCardType)
 			throws InterruptedException {
 		WebElement wElementApplyChange = null, wElementReset = null, wContibutionSlider = null;
 		String sBtnApplyChange = sApplyChangebtn.replace("CardType", sCardType);
 		String sBtnReset = sResetbtn.replace("CardType", sCardType);
 		Common.waitForProgressBar();
 		if (Web.isWebElementDisplayed(
 				Web.getDriver().findElement(
 						By.xpath(showMorelnk.replace("CardType", sCardType))),
 				true)) {
 			Web.getDriver()
 					.findElement(
 							By.xpath(showMorelnk.replace("CardType", sCardType)))
 					.click();
 		}
 		String[] sbuttonType = { "plus", "minus" };
 		for (int i = 0; i < sbuttonType.length; i++) {
 			String button = sModelContributionPlusorMinusbtn.replace(
 					"CardType", sCardType).replace("ButtonValue",
 					sbuttonType[i]);
 			Web.getDriver().findElement(By.xpath(button)).click();
 			wElementApplyChange = Web.getDriver().findElement(
 					By.xpath(sBtnApplyChange));
 			Web.waitForElement(wElementApplyChange);
 			Web.clickOnElement(wElementApplyChange);
 			if (Web.isWebElementDisplayed(txtinModalPopUpheader, true))
 				Reporter.logEvent(
 						Status.PASS,
 						"Verify Modal Popup is displayed with 'Unable to complete transaction' title for button:"
 								+ sbuttonType[i],
 						"Modal Popup is displayed with 'Unable to complete transaction' title for button:"
 								+ sbuttonType[i], false);
 			else
 				Reporter.logEvent(
 						Status.FAIL,
 						"Verify Modal Popup is displayed with 'Unable to complete transaction' title for button:"
 								+ sbuttonType[i],
 						"Modal Popup is not displayed with 'Unable to complete transaction' title for button:"
 								+ sbuttonType[i], true);
 			Web.clickOnElement(btnOk);
 			wElementReset = Web.getDriver().findElement(By.xpath(sBtnReset));
 			Web.waitForElement(wElementReset);
 			Web.clickOnElement(wElementReset);
 		}
 		Web.getDriver()
 				.findElement(
 						By.xpath(sEditValueinDonut.replace("CardType",
 								sCardType))).click();
 		wContibutionSlider = Web.getDriver().findElement(
 				By.xpath(stxtcontributionRateSlider.replace("CardType",
 						sCardType)));
 		Web.waitForElement(wContibutionSlider);
 		String contrbution_rate = Stock.GetParameterValue("Contribution Rate");
 		Web.setTextToTextBox(wContibutionSlider, contrbution_rate);
 		Web.clickOnElement(btnDone);
 		if (btnDone.isDisplayed()) {
 			Web.clickOnElement(btnDone);
 		}
 		Thread.sleep(2000);
 		wElementApplyChange = Web.getDriver().findElement(
 				By.xpath(sBtnApplyChange));
 		Web.waitForElement(wElementApplyChange);
 		Web.clickOnElement(wElementApplyChange);
 		if (Web.isWebElementDisplayed(txtinModalPopUpheader, true))
 			Reporter.logEvent(
 					Status.PASS,
 					"Verify Modal Popup is displayed with 'Unable to complete transaction' title for contributionRateSlider",
 					"Modal Popup is displayed with 'Unable to complete transaction' title for contributionRateSlider",
 					false);
 		else
 			Reporter.logEvent(
 					Status.FAIL,
 					"Verify Modal Popup is displayed with 'Unable to complete transaction' title for contributionRateSlider",
 					"Modal Popup is not displayed with 'Unable to complete transaction' title for contributionRateSlider",
 					true);
 		Web.clickOnElement(btnOk);
 		wElementReset = Web.getDriver().findElement(By.xpath(sBtnReset));
 		Web.waitForElement(wElementReset);
 		Web.clickOnElement(wElementReset);
 	}
 	/**
	 * Method returns Contribution value in Dollars or in Percentage
	 * 
	 * @param CardType
	 * @return String
	 */
	public String getContributionValue(String sCardType) {
		WebElement wElementtxtContributionAmnt = Web.getDriver().findElement(
				By.xpath(txtDonutContribution.replace("CardType", sCardType)));
		return wElementtxtContributionAmnt.getText();

	}

	/**
	 * Method to Click on ApplyChanges button
	 */
	public void clickonApplyChangesbtn(String sCardType) {
		try {
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver()
					.findElement(
							By.xpath(sApplyChangebtn.replace("CardType",
									sCardType))).click();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to Click on Reset button
	 */
	public void clickonResetbtn(String sCardType) {
		try {
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().findElement(
					By.xpath(sResetbtn.replace("CardType", sCardType)));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to click on '+" or '-' button in Contribution card
	 * 
	 * @param sCardType
	 * @param sPlusorMinus
	 * @throws Exception
	 */
	public void clickonPlusorMinusbtn(String sCardType, String sPlusorMinus)
			throws Exception {
		String sButton = null;
		if (sPlusorMinus.equalsIgnoreCase("plus"))
			sButton = "plus";
		if (sPlusorMinus.equalsIgnoreCase("minus"))
			sButton = "minus";
		Web.getDriver()
				.findElement(
						By.xpath(sModelContributionPlusorMinusbtn.replace(
								"CardType", sCardType).replace("ButtonValue",
								sButton))).click();
		Web.waitForPageToLoad(Web.getDriver());
	}

	/**
	 * Method to click on Show More link for defferal in Contribution card
	 * 
	 * @param sCardType
	 * @throws Exception
	 */
	public void clickonShowMorelinkforDeferral(String sCardType)
			throws Exception {
		try {
			if (!sCardType.equalsIgnoreCase("STANDARD"))
				Web.clickOnElement(Web.getDriver().findElement(
						By.xpath(showMorelnk.replace("CardType", sCardType))));
		} catch (NoSuchElementException e) {
			Reporter.logEvent(Status.INFO, "Click on Show more Link",
					"Click on Show more Link", false);
		}
	}

	/**
	 * Method to wait For DonutChart for sCardType
	 * 
	 * @param sCardType
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void waitForDonutChart(String sCardType) throws InterruptedException {
		Thread.sleep(2000);
		String sChartContirbutionDonut = chartContirbutionDonut.replace(
				"CardType", sCardType);
		if (Web.isWebElementDisplayed(
				Web.getDriver().findElement(By.xpath(sChartContirbutionDonut)),
				true))
			Reporter.logEvent(Status.INFO, sCardType + "Donut Chart displayed",
					sCardType + "Donut Chart displayed", false);
	}
}
