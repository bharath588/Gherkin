package pageobjects.landingpage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
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
	@FindBy(linkText = "Home")
	private WebElement lnkHome;
	@FindBy(linkText = "My Accounts")
	private WebElement lnkMyAccounts;
	@FindBy(xpath = ".//*[text()='Retirement income']")
	private WebElement lblRetirementIncome;
	@FindBy(xpath = ".//button[@aria-label='Exit tutorial']")
	private WebElement btnCloseTutorial;
	@FindBy(xpath = "//button[text()[normalize-space()='Dismiss']]")
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
	@FindBy(xpath=".//button[@id='submit'][./span[contains(text(),'CONTINUE')]]") private WebElement btnContinue1;
	@FindBy(linkText="Guidance")
	private WebElement lnkGuidance;
	@FindBy(xpath="//button[contains(text(),'Close without enrolling')]") private WebElement btnCloseWithOutEnrolling;
	
	
	private String lnkEnrollNow="//li[./a[contains(@ng-href,'planid')]]//a[./span[contains(text(),'Enrollment now open')]]";

	/**
	 * Empty args constructor
	 * 
	 */
	public LandingPage() {
		this.parent = new TwoStepVerification();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public LandingPage(LoadableComponent<?> parent) {
		// this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName,true),"Landing Page is Not Loaded");
		/*String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {

			try {
				strUserInfo =Common.getParticipantInfoFromDataBase(ssn);
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
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn))*/if(Common.verifyLoggedInUserIsSame()) {
			   Assert.assertTrue(true,"Landing page is not loaded");		
			//Assert.assertTrue(lib.Web.isWebElementDisplayed(lblRetirementIncome,true));
		} else {
			
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
			
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
				if(Web.isWebElementDisplayed(btnContinue1))
				{
					Web.clickOnElement(btnContinue1);
				}
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}
		if (Web.isWebElementDisplayed(btnCloseTutorial,true)) {
			btnCloseTutorial.click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (Web.isWebElementDisplayed(lnkCancelGoalSetup)) {
			this.lnkCancelGoalSetup.click();
		}
		if(!Web.isWebElementDisplayed(lblUserName, true)){
			String url=Web.getDriver().getCurrentUrl();
			url=url.concat("?accu=Empower");
			Web.getDriver().get(url);
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		if (fieldName.trim().equalsIgnoreCase("CONTINUE")) {
			return this.btnContinue1;
		}
		
		if (fieldName.trim().equalsIgnoreCase("GUIDANCE")) {
			return this.lnkGuidance;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Close WithOut Enrolling")) {
			return this.btnCloseWithOutEnrolling;
		}
		if (fieldName.trim().equalsIgnoreCase("Link Dismiss")) {
			return this.lnkDismiss;
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
	
	/**
	 * Method to Click on Guidance Link
	 * 
	 * 
	 */
	public void clickOnGuidanceLink() {
		Web.waitForElement(lnkGuidance);
		if ( Web.isWebElementDisplayed(lnkGuidance))
			Web.clickOnElement(lnkGuidance);
	}
	
	/**
	 * method to click on Enrollment Now Open  link which corresponds to the specified
	 * group account ID
	 * 
	 * @param groupAccountID
	 *            - Example: 95301-01
	 */
	public void clickEnrollNowByGAID(String groupAccountID) {
		
		try
		{
			Web.waitForElement(btnCloseWithOutEnrolling);
			if(Web.isWebElementDisplayed(btnCloseWithOutEnrolling, true))
				Web.clickOnElement(btnCloseWithOutEnrolling);
			List<WebElement> lnkEnrollment = Web.getDriver().findElements(By		
				.xpath(lnkEnrollNow.replace("planid",groupAccountID)));
			int i=lnkEnrollment.size();
			if(lnkEnrollment.get(1).isEnabled())
				Web.clickOnElement(lnkEnrollment.get(1));
		}
          catch(NoSuchElementException e){
        	  e.printStackTrace();
          }
  		
		
	}
	
	/**
	 * method to Verify on Enrollment Now Open  link which corresponds to the specified
	 * group account ID
	 * 
	 * @param groupAccountID
	 *            - Example: 95301-01
	 */
	public void verifyEnrollmentOpenNowLinkIsDisplayed(String groupAccountID) {
		
		try
		{
			WebElement lnkEnrollment = Web.getDriver().findElement(By		
				.xpath(lnkEnrollNow.replace("planid",groupAccountID)));
			if(Web.isWebElementDisplayed(lnkEnrollment, true)){
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Enrollment Now Open' Link is Displayed in LIAT Page",
						"'Enrollment Now Open' Link is Displayed in LIAT Page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Enrollment Now Open' Link is Displayed in LIAT Page",
						"'Enrollment Now Open' Link is Not Displayed in LIAT Page",
						true);
			}
		}
          catch(NoSuchElementException e){
        	  e.printStackTrace();
          }
  		
		
	}
}
