package pageobjects.landingpage;

import java.sql.ResultSet;
import java.sql.SQLException;





import lib.DB;
import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;


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
	
	@FindBy(linkText="Logout") private WebElement lnkLogout;
	@FindBy(id="home") private WebElement lnkHome;	
	@FindBy(xpath=".//div[contains(@class,'categoryTile myaccount')]") private WebElement lnkMyAccounts;
	
    @FindBy(xpath=".//div[contains(@class,'categoryTile investments')]") private WebElement lnkInvestments;
    @FindBy(id="name") private WebElement lblUserName;
    @FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
    @FindBy(xpath= "//*[@id='fancybox-close']")
	private WebElement btnClose;
    @FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

    /** Empty args constructor
     * 
     */
    public LegacyLandingPage() {
    	this.parent = new TwoStepVerification();
    	PageFactory.initElements(lib.Web.getDriver(), this);
    }
    
    /** Constructor taking parent as input
     * 
     * @param parent
     */
	public LegacyLandingPage(LoadableComponent<?> parent){
		//this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Legacy Landing Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo=null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName").toString().trim();
				
		}
		else{
	
		try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
		} catch (SQLException e1) {
						e1.printStackTrace();
		}

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		String userLogedIn = this.lblUserName.getText();
		/*String sponser = this.lblSponser.getAttribute("Alt");
		if(sponser.isEmpty())
		{
			sponser=Common.GC_DEFAULT_SPONSER;
		}
			*/	
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)){
			
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"Legacy Landing page is not loaded");
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
				mfaScreen.submitVerificationCode(Stock.getConfigParam("defaultActivationCode"), true, false);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Web.waitForElement(btnClose);
		if (Web.isWebElementDisplayed(this.btnClose, true)) {
			this.btnClose.click();
		}
		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Reporter.logEvent(this.getClass(), Level.INFO, "Secure PIN entered.", true);
		Web.isWebElementDisplayed(lblUserName,true);
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
		//HOME
				if (fieldName.trim().equalsIgnoreCase("Button Close")) {
					return this.btnClose;
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
