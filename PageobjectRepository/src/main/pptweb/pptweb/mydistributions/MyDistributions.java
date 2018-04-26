package pptweb.mydistributions;

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

import pptweb.appUtils.Common;
import pptweb.general.LeftNavigationBar;
import pptweb.general.MyAccountsPage;
import pptweb.landingpage.LandingPage;
import core.framework.Globals;

public class MyDistributions extends LoadableComponent<MyDistributions> {

	// Declarations
	private LoadableComponent<?> parent;
	// private static boolean waitforLoad = false;

	// @FindBy(xpath=".//h1[text()='My Accounts']") private WebElement
	// hdrMyAccounts;
	@FindBy(xpath = ".//h1[text()='My Distributions']")
	private WebElement hdrMyDistributions;
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
	@FindBy(xpath = "//a[./span[contains(text(),'Enroll now')]]") private WebElement lnkEnrollNow;
	private String textField="//*[contains(text(),'webElementText')]";
	
	

	/**
	 * Empty args constructor
	 * 
	 */
	public MyDistributions() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public MyDistributions(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		if(Common.verifyLoggedInUserIsSame()) {
			
				Assert.assertTrue(Web.isWebElementDisplayed(hdrMyDistributions));
			} else {
				this.lnkLogout.click();
				System.out.println("Clicked on Log Out My Accoounts Page");
				Web.waitForElement(btnLogin);
				Assert.assertTrue(false,"Login page is displayed");
			}
		
	}

	@Override
	protected void load() {
		this.parent.get();	
		
		((LeftNavigationBar) this.parent).clickNavigationLink("MY DISTRIBUTIONS");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementDisplayed(hdrMyDistributions,true);
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
		if (fieldName.trim().equalsIgnoreCase("My Distributions")) {
			return this.hdrMyDistributions;
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
		
		try
		{
			if(Web.isWebElementDisplayed(lnkEnrollNow, true))
			Web.clickOnElement(lnkEnrollNow);
		}
		catch(NoSuchElementException e){
			e.printStackTrace();
		}
		
	}
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
}
