package pageobjects.landingpage;

import java.sql.ResultSet;

import java.sql.SQLException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.login.TwoStepVerification;

public class LandingPage extends LoadableComponent<LandingPage> {

	// Declarations
	// private WebDriver driver;
	private LoadableComponent<?> parent;
	/*
	 * private String username; private String password;
	 */

	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id = "home")
	private WebElement lnkHome;
	@FindBy(linkText = "My Accounts")
	private WebElement lnkMyAccounts;
	@FindBy(xpath = ".//*[text()='Retirement income']")
	private WebElement lblRetirementIncome;
	@FindBy(xpath = ".//*[@aria-label='Exit Tutorial']")
	private WebElement btnCloseTutorial;
	@FindBy(xpath = ".//button[contains(text(),'Dismiss')]")
	private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[@id='progressModal']/.//button[text()[normalize-space()='Cancel']]")
	private WebElement lnkCancelGoalSetup;

	@FindBy(className = "AccountOverview")
	private WebElement lnkAccOverview;
	@FindBy(xpath = ".//*[@alt='At-a-Glance']")
	private WebElement lblAt_a_Glance;
	@FindBy(id = "atAGlanceBalance")
	private WebElement DailyBalance;
	@FindBy(id = "atAGlanceROR")
	private WebElement RateOfReturn;
	@FindBy(id = "atAGlanceLastContribution")
	private WebElement LastContributionAmount;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()='My Accounts']")
	private WebElement lblMyAccounts;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/**
	 * Empty args constructor
	 * 
	 */
	public LandingPage() {
		this.parent = new TwoStepVerification();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public LandingPage(LoadableComponent<?> parent) {
		// this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName,true),"Landing Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {

			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn
						.substring(0, 9));
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
			// Assert.assertTrue(Web.isWebElementDisplayed(lblRetirementIncome,true));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}

	}

	@Override
	protected void load() {
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		this.parent.get();

		if (!Web.isWebElementDisplayed(this.lnkHome)) {
			mfaScreen.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			try {
				mfaScreen.submitVerificationCode(
						Stock.getConfigParam("defaultActivationCode"), true,
						false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (Web.isWebElementDisplayed(this.btnCloseTutorial)) {
			this.btnCloseTutorial.click();
		}

		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}

		if (Web.isWebElementDisplayed(this.lnkCancelGoalSetup,true)) {
			this.lnkCancelGoalSetup.click();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Reporter.logEvent(this.getClass(), Level.INFO, "Secure PIN entered.",
		// true);
	}

	/**
	 * <pre>
	 * Method to return the no of plans associated to a user from db
	 * 
	 * @return noOfPlans
	 * @throws Exception
	 */
	public int getNoOfPlansFromDB(String ssn) throws Exception {

		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("fetchNoOfPlans");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet recSet = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
		int noOfPlans = DB.getRecordSetCount(recSet);

		return noOfPlans;
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

		// HOME
		if (fieldName.trim().equalsIgnoreCase("HOME")) {
			return this.lnkHome;
		}

		// My Accounts
		if (fieldName.trim().equalsIgnoreCase("MY ACCOUNTS")) {
			return this.lnkMyAccounts;
		}

		// Retirement income
		if (fieldName.trim().equalsIgnoreCase("RETIREMENT INCOME")) {
			return this.lblRetirementIncome;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT MY ACCOUNTS")) {
			return this.lblMyAccounts;
		}


		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * <pre>
	 * Method to validate Balance At a Glance section displayed on Landing page
	 * Validates:
	 * 	1) Daily Balance
	 * 	2) Rate of return
	 * 	3) Last Contribution Amount
	 * 
	 * Expected values to be passed from test data file
	 * </pre>
	 * 
	 * @param DailyBalance
	 * @param RateOfReturn
	 * @param LastContributionAmount
	 * 
	 * @throws Exception
	 */
	/*
	 * public void validateBalanceAtaGlanceSection() throws Exception {
	 * 
	 * WebActions.waitForElement(DailyBalanceLoctor);
	 * 
	 * String strDailyBal = DailyBalance.getText();
	 * 
	 * //Common.VerifyText(TestDataContainer.GetParameterValue("DailyBalance"),
	 * strDailyBal);
	 * Common.VerifyText(TestDataContainer.GetParameterValue("DailyBalance"),
	 * strDailyBal);
	 * 
	 * String strRateOfReturn = RateOfReturn.getText();
	 * 
	 * Common.VerifyText(TestDataContainer.GetParameterValue("RateOfReturn"),
	 * strRateOfReturn);
	 * 
	 * String strLastContributionAmount = LastContributionAmount.getText();
	 * 
	 * Common.VerifyText(TestDataContainer.GetParameterValue(
	 * "LastContributionAmount"), strLastContributionAmount); }
	 */

	/**
	 * Method to logout from PPTWeb
	 * 
	 * @param bLogoutAfterExecution
	 *            - <b>true</b> to logout from application. <b>false</b>
	 *            otherwise
	 */
	public void logout(boolean bLogoutAfterExecution) {
		if (bLogoutAfterExecution && Web.isWebElementDisplayed(this.lnkLogout))
			this.lnkLogout.click();
	}

	/**
	 * Method to close the displayed pop ups on landing page
	 * 
	 * @param closeTutorial
	 *            - <b>true</b> if Tutorials pop up need to be closed.
	 *            <b>flase</b> otherwise
	 * @param cancelGoalSetup
	 *            - <b>false</b> if goal setup pop up need to be closed.
	 *            <b>false</b> otherwise
	 */
	public void dismissPopUps(boolean closeTutorial, boolean cancelGoalSetup) {
		if (closeTutorial
				&& Web.isWebElementDisplayed(this.btnCloseTutorial, true)) {
			this.btnCloseTutorial.click();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// Do nothing
		}

		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// Do nothing
		}

		if (cancelGoalSetup
				&& Web.isWebElementDisplayed(this.lnkCancelGoalSetup)) {
			this.lnkCancelGoalSetup.click();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
