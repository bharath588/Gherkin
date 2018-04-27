package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class MeAndMyMoney extends LoadableComponent<MeAndMyMoney> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//*[@id='account-details-container']//h1")
	private WebElement lblRateOfReturn;
	@FindBy(linkText = "Me & My Money")
	private WebElement lnkMeAndMyMoney;
	@FindBy(linkText = "Articles & Calculators")
	private WebElement lnkArticlesAndCalculators;
	@FindBy(linkText = "Savings & Bill Manager")
	private WebElement lnkSavingsAndBillManager;
	 @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath=".//*[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	
	/**
	 * Default Constructor
	 */
	public MeAndMyMoney() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public MeAndMyMoney(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblRateOfReturn, true),"Rate Of Return Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		 else {

				try {
					strUserInfo =Common.getParticipantInfoFromDataBase(ssn);;
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
			Assert.assertTrue(Web.isWebElementDisplayed(this.lnkMeAndMyMoney),"Me And My Money Tab is Not Loaded\n");
		} else {
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}

	}

	@Override
	protected void load() {
		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}
		
		this.parent.get();

		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();

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

		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}
	public void verifyArticlesAndCalculatorsTab() throws InterruptedException{

		Web.clickOnElement(lnkMeAndMyMoney);
		Web.waitForElement(lnkArticlesAndCalculators);
		Web.clickOnElement(lnkArticlesAndCalculators);
		String parentWindow = Web.getDriver().getWindowHandle();
		Set<String> handles =  Web.getDriver().getWindowHandles();
		   for(String windowHandle  : handles)
		       {
		       if(!windowHandle.equals(parentWindow)){
		    	   Web.getDriver().switchTo().window(windowHandle);
		    	   Web.waitForPageToLoad(Web.getDriver());
		    	   Thread.sleep(10000);
		    	 
		    	   if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Empower Retirement - Wellness and Financial Center")){
				    	  
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Me and My Money' Page opened in New Window",
									"'Me and My Money' Page opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Me and My Money' Page opened in New Window ",
									"'Me and My Money' Page is not opened in New Window ", true);
						}
		     
		    }
		       }
		   Web.getDriver().close(); //closing child window
           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
           Web.getDriver().switchTo().defaultContent();
		

	}
	
}
