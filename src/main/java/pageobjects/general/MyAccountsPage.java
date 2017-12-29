package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.landingpage.LandingPage;

public class MyAccountsPage extends LoadableComponent<MyAccountsPage> {

	// Declarations
	private LoadableComponent<?> parent;
	// private static boolean waitforLoad = false;

	// @FindBy(xpath=".//h1[text()='My Accounts']") private WebElement
	// hdrMyAccounts;
	@FindBy(xpath = ".//h1[text()='My Accounts']")
	private WebElement hdrMyAccounts;
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
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	
	@FindBy(xpath = "//button[contains(text(),'Continue')]") private WebElement btnContinue;
	@FindBy(xpath = "//button[contains(text(),'Cancel')]") private WebElement btnCancel;
	@FindBy(xpath = "//div[@class='modal-body auto-increase']//div[@class='row'][1]//p") private WebElement txtYouEnrolled;
	@FindBy(xpath = "//div[@class='modal-body auto-increase']//div[@class='row'][2]//p") private WebElement txtClickCancel;
	@FindBy(xpath = "//button[./strong[text()[normalize-space()='Continue to enrollment']]]") private WebElement btnContinueToEnroll;
	
	
	private String lnkEnrollNow="//span[./a[@id='ga_planid']]//a[./span[contains(text(),'Enrollment now open')]]";

	/**
	 * Empty args constructor
	 * 
	 */
	public MyAccountsPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public MyAccountsPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

			if(Common.verifyLoggedInUserIsSame()) {
			
				Assert.assertTrue(Web.isWebElementDisplayed(hdrMyAccounts));
			} else {
				this.lnkLogout.click();
				System.out.println("Clicked Log Out on My Accoounts Page");
				Web.waitForElement(btnLogin);
				Assert.assertTrue(false,"Login page is displayed");
			}
		
	}
		
	

	@Override
	protected void load() {
		LandingPage land = (LandingPage) this.parent;

		this.parent.get();
		((LandingPage) this.parent).dismissPopUps(true, true);
		try {
			 Web.clickOnElement(land, "MY ACCOUNTS");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
			return this.hdrMyAccounts;
		}
		if (fieldName.trim().equalsIgnoreCase("Graph")) {
			return this.imgGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("PLAN NAME")) {
			return this.lnkPlanName;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);
		return null;
	}

	/**
	 * method to click on Plan name link which corresponds to the specified
	 * group account ID
	 * 
	 * @param groupAccountID
	 *            - Example: 95301-01
	 * @return boolean - <b>true</b> if successful in clicking specified link.
	 *         <b>false</b> otherwise.
	 */
	public boolean clickPlanNameByGAID(String... groupAccountID) {
		boolean success = false;
		int planCount = this.lstLnkPlanName.size();
		String actualGAID = "";
		WebElement currElement;

		if (groupAccountID.length == 0) {
			currElement = this.lstLnkPlanName.get(0);
			currElement.click();
			success = true;
		} else {
			for (int iCntr = 0; iCntr < planCount; iCntr++) {
				currElement = this.lstLnkPlanName.get(iCntr);
				actualGAID = currElement.getAttribute("id");

				if (actualGAID.trim().equals("ga_" + groupAccountID[0].trim())) {
					currElement.click();
					success = true;
					break;
				}
			}
		}

		return success;
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
			WebElement lnkEnrollment = Web.getDriver().findElement(By		
				.xpath(lnkEnrollNow.replace("planid",groupAccountID)));
			if(lnkEnrollment.isDisplayed())
				Web.clickOnElement(lnkEnrollment);
		}
          catch(NoSuchElementException e){
        	  e.printStackTrace();
          }
  		
		
	}
	/**
	 * method to Verify Enrollment Now Open link displayed for corresponding
	 * group account ID
	 * 
	 * @param groupAccountID
	 *            - Example: 95301-01
	 */
	public void VerifyEnorollNowLinkDisplayedForPlan(String planID) {
		
		try
		{
			WebElement lnkEnrollment = Web.getDriver().findElement(By		
				.xpath(lnkEnrollNow.replace("planid",planID)));
			if(lnkEnrollment.isDisplayed()){
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Now Open Link is Displayed for Specified Plan",
						"Enrollment Now Open Link is Displayed for Specified Plan "+planID,
						true);
			} 
		}
          catch(NoSuchElementException e){
        	  Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Now Open Link is Displayed for Specified Plan",
						"Enrollment Now Open Link is Not Displayed for Specified Plan "+planID,
						true);
          }
  		
		
	}
}
