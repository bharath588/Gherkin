package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.landingpage.LandingPage;

public class GuidancePage extends LoadableComponent<GuidancePage> {

	// Declarations
	private LoadableComponent<?> parent;
	// private static boolean waitforLoad = false;

	@FindBy(xpath="//span[contains(text(),'Guidance')]")
	private WebElement hrdGuidance;
	
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//*[@id='account-overview-chart']")
	private WebElement imgGraph;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/**
	 * Empty args constructor
	 * 
	 */
	public GuidancePage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public GuidancePage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

				if(Common.verifyLoggedInUserIsSame()) {
					 		
					Assert.assertTrue(Web.isWebElementDisplayed(hrdGuidance),"Guidance Page is Not Loaded");
				} else {
					this.lnkLogout.click();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					Assert.assertTrue(false,"Login Page is not loaded\n");
				}

		
	}

	@Override
	protected void load() {
		LandingPage land = (LandingPage) this.parent;
		this.parent.get();
		
		try {
			 Web.clickOnElement(land, "GUIDANCE");
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
		// Guidance Header
		if (fieldName.trim().equalsIgnoreCase("Header Guidance")) {
			return this.hrdGuidance;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);
		return null;
	}

	

}
