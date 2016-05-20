package pageobjects.landingpage;

import java.sql.ResultSet;
import java.sql.SQLException;



import lib.DB;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import core.framework.Globals;

public class LegacyLandingPage extends LoadableComponent<LegacyLandingPage> {

	//Declarations
	//private WebDriver driver;
	private LoadableComponent<?> parent;
	/*private String username;
	private String password;*/
	
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id="home") private WebElement lnkHome;	
	@FindBy(xpath=".//div[contains(@class,'categoryTile myaccount')]") private WebElement lnkMyAccounts;
	
    @FindBy(xpath=".//div[contains(@class,'categoryTile investments')]") private WebElement lnkInvestments;
    @FindBy(id="name") private WebElement lblUserName;
    @FindBy(xpath=".//div[@class='container']/span[@ng-if='accuLogoLoaded']/img") private WebElement lblSponser;
    @FindBy(id = "fancybox-close")
	private WebElement btnClose;
	
    /** Empty args constructor
     * 
     */
    public LegacyLandingPage() {
    	this.parent = new TwoStepVerification();
    	PageFactory.initElements(lib.Web.webdriver, this);
    }
    
    /** Constructor taking parent as input
     * 
     * @param parent
     */
	public LegacyLandingPage(LoadableComponent<?> parent){
		//this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName").toString().trim();
				
		}
		else{
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(
				0, ssn.length() - 3));

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		String userLogedIn = this.lblUserName.getText();
		String sponser = this.lblSponser.getAttribute("Alt");
		if(sponser.isEmpty())
		{
			sponser=Globals.GC_DEFAULT_SPONSER;
		}
				
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)&& Common.isCurrentSponser(sponser)){
			
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			//Assert.assertTrue(Web.isWebElementDisplayed(lblRetirementIncome));
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName,true));
		}
		
		
	}

	@Override
	protected void load() {
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		this.parent.get();
		
		if (!Web.isWebElementDisplayed(this.lnkHome)) {
			mfaScreen.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			try {
				mfaScreen.submitVerificationCode(Stock.getConfigParam("defaultActivationCode"), true, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (Web.isWebElementDisplayed(this.btnClose)) {
			this.btnClose.click();
		}
		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Reporter.logEvent(this.getClass(), Level.INFO, "Secure PIN entered.", true);
	}
	
	
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	LOG OUT Or LOGOUT - [Link]
	 * 	HOME - [Link]
	 * 	MY ACCOUNTS - [Link]
	 * 	RETIREMENT INCOME - [Label]
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT") || fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		
		//HOME
		if (fieldName.trim().equalsIgnoreCase("HOME")) {
			return this.lnkHome;
		}
		
		//My Accounts
		if (fieldName.trim().equalsIgnoreCase("MY ACCOUNT")) {
			return this.lnkMyAccounts;
		}
		
		//Retirement income
		if (fieldName.trim().equalsIgnoreCase("INVESTMENTS")) {
			return this.lnkInvestments;
		}
		if (fieldName.trim().equalsIgnoreCase("USER NAME")) {
			return this.lblUserName;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		
		return null;
	}
	
	
}
