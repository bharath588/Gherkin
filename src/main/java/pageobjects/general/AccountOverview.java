package pageobjects.general;

import java.util.ArrayList;
import java.util.List;

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
	
	/*@FindBy(linkText = "Show more")
	private List<WebElement> lnkShowMoreStatements;*/
	@FindBy(xpath = "//a[text()='Show more']")
	private WebElement lnkShowMoreStatements;
	
	
	//Stmt - No Stmts
	@FindBy(xpath = "//h2[text()='Statements']/following-sibling::p")
	private WebElement txtNoStatements;
	
	//Stmt - Delivery Option
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

	public void updateBeneficiary(String plan_id) throws Exception {
		String[] sqlQuery;
		int updateBene = 0;
		sqlQuery = Stock.getTestQuery("updateWnbene");

		sqlQuery[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		updateBene = DB.executeUpdate(sqlQuery[0], sqlQuery[1], plan_id);
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
	
	//Mosin - Verify Delivery Option for Statement Card
	public void verifyDeliveryOption(String sDeliveryText)
	{
		if(txtStmtDeliveryOptions.getText().equalsIgnoreCase("Delivery options"))
			Reporter.logEvent(Status.PASS,
					"Verify Delivery option text in Statement Card",
					"Delivery option text in Statement Card is displayed. Expected: Delivery options Actual:"
							+ txtStmtDeliveryOptions.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Delivery option text in Statement Card",
					"Delivery option text in Statement Card is not displayed. Expected: Delivery options Actual:"
							+ txtStmtDeliveryOptions.getText(), true);
		
		if(txtStmtBenefit.getText().equalsIgnoreCase(sDeliveryText))
			Reporter.logEvent(Status.PASS,
					"Verify Delivery option Sign up text is displayed",
					"Delivery option Sign up text is displayed. Expected: "+sDeliveryText+" Actual: "+ txtStmtBenefit.getText(), true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Delivery option Sign up text is displayed",
					"Delivery option Sign up text is not displayed. Expected: "+sDeliveryText+" Actual: "+ txtStmtBenefit.getText(), true);
		
		isTextFieldDisplayed("More secure delivery of important communications");
		isTextFieldDisplayed("Potentially minimize exposure to identity theft");
		isTextFieldDisplayed("Better document management");
		isTextFieldDisplayed("Less mail to fill your mailbox at home");
		
	}
	//Mosin - Verify Delivery Option for Statement Card
	public void verifyNonStements()
	{
		if(txtNoStatements.getText().equalsIgnoreCase("None available"))
			Reporter.logEvent(Status.PASS,
					"Verify no statements on Card",
					"No statements are dispalyed on Card."+txtNoStatements.getText()+" text is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify no statements on Card",
					"Statements are dispalyed on Card.", true);
		//System.out.println(lnkShowMoreStatements.size()+"@@@@@@");
		if(!Web.isWebElementDisplayed(lnkShowMoreStatements))
			Reporter.logEvent(Status.PASS,
					"Verify Show more link is not displayed",
					"Show more link is not displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Show more link is not displayed",
					"Show more link is displayed", true);
	}
	
	//Mosin - Click on Show more link of Statement card
	public void verifyShowMoreLink(boolean click)
	{
		if(Web.isWebElementDisplayed(lnkShowMoreStatements))
			Reporter.logEvent(Status.PASS,
					"Verify Show more link is displayed",
					"Show more link is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Show more link is displayed",
					"Show more link is not displayed", true);
		
		if(click)
		{
			lnkShowMoreStatements.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
		}
			
	}
	public void verifyStmtsareVisible()
	{
		if(Web.isWebElementDisplayed(lstStatements.get(0), true))
			Reporter.logEvent(Status.PASS,
					"Verify Statements are displayed for Participant",
					lstStatements.size()+" Statements are displayed fpr the ppt", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Statements are displayed for Participant",
					"Statements are not displayed", true);
	}
	public void clickOnFirstStmts()
	{
		Web.clickOnElement(lstStatementsFirst);
		String getParentWindow = Web.getDriver().getWindowHandle();
		for (String windowHandles : Web.getDriver().getWindowHandles()) {
			Web.getDriver().switchTo().window(windowHandles); 
		}
		System.out.println("Statements url"+Web.getDriver().getCurrentUrl());
		if (Web.getDriver()
				.getCurrentUrl()
				.contains(
						Stock.GetParameterValue("Statements url")))
			Reporter.logEvent(Status.PASS,
					"Verify when you click a statement It should open a PDF file in new tab",
					"Opened a PDF file in new tab",
					false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify when you click a statement It should open a PDF file in new tab",
					"Did not open a PDF file in new tab",
					true);
		Web.getDriver().close();
		Web.getDriver().switchTo().window(getParentWindow);
		Web.getDriver().switchTo().defaultContent();
	}
	public void changeDeliveryOption(String ssn,String sOption) throws Exception {
		String[] sqlQuery;
		int updateDelivery = 0;
		sqlQuery = Stock.getTestQuery("updateDeliveryMethodForParticipant");

		sqlQuery[0] = Common.getParticipantDBName(Stock
				.GetParameterValue("userName"))
				+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		updateDelivery = DB.executeUpdate(sqlQuery[0], sqlQuery[1], sOption,ssn);

		if (updateDelivery > 0) {
			System.out.println("Changed the Delivery option to"+sOption);
		}
	}
	public void clickOnEdelivery(boolean click)
	{
		if(click)
		{
			if(Web.isWebElementDisplayed(lnkSignupEdelivery, true))
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
		}
		else
		{
			if(Web.isWebElementDisplayed(lnkSignupEdelivery, true))
				Reporter.logEvent(Status.PASS,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Sign up e-Delivery link is displayed",
						"Sign up e-Delivery link is displayed", true);
		}
	}
	//Mosin - To verify the Loan balance card and text
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
	//Mosin - To click on view details link of loan balance card
	public void clickOnLoanViewDetailsLink(boolean click) throws InterruptedException
	{
		if(click)
		{
			if(Web.isWebElementDisplayed(lnkViewDetailsLoans, true))
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
		}
		else
		{
			if(!Web.isWebElementDisplayed(lnkViewDetailsLoans, true))
				Reporter.logEvent(Status.PASS,
						"Verify View details link is not displayed",
						"View details link is not displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify View details link is not displayed",
						"View details link is displayed", true);
		}
	}
	public void verifyMaturityDates()
	{
		if(Web.isWebElementDisplayed(txtMaturityDateLoans, true))
			Reporter.logEvent(Status.PASS,
					"Verify Maturity date is displayed",
					"Maturity date is displayed", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Maturity date is displayed",
					"Maturity date is not displayed", true);
	}
}
