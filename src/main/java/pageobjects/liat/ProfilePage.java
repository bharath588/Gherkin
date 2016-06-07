package pageobjects.liat;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.landingpage.LandingPage;
import appUtils.Common;
import core.framework.Globals;

public class ProfilePage extends LoadableComponent<ProfilePage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//*[text()='Profile']")
	private WebElement lblProfile;
	@FindBy(xpath = "//*[@id='content-container']//h1")
	private WebElement hrdUserName;
	 @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//h2")
	private WebElement hrdPersonalInfo;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//div[1]//strong")
	private WebElement lblPersonalEmailAdd;
	@FindBy(xpath = ".//div[contains(@class,'personal-information')]//div[2]//strong")
	private WebElement lblMobileNo;
	@FindBy(xpath = ".//*[@id='personalEmailAddress']")
	private WebElement txtPersonalEmailAdd;
	@FindBy(xpath = ".//*[@id='mobilePhoneNumber']")
	private WebElement txtMobileNo;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//h2")
	private WebElement hrdHomeMaililngAdd;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//div[2]//strong")
	private WebElement lblHomeAdd;
	@FindBy(xpath = ".//div[contains(@class,'home-information')]//div[2]//div")
	private WebElement txtHomeAdd;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//h2")
	private WebElement hrdUserNamePassword;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[1]//strong")
	private WebElement lblUsername;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[2]//strong")
	private WebElement lblPassword;
	@FindBy(xpath = ".//div[contains(@class,'profile-username')]")
	private WebElement txtUsername;
	@FindBy(xpath = ".//div[contains(@class,'login-information')]//div[2]//div[1]")
	private WebElement txtPassword;
	@FindBy(linkText = "Home")
	private WebElement lblHome;
	

	/**
	 * Default Constructor
	 */
	public ProfilePage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public ProfilePage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.hrdUserName, true));
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		 else {

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
		String userLogedIn = this.hrdUserName.getText().replaceAll("\\s+", " ").trim();
		String sponser = this.lblSponser.getAttribute("Alt");
		if (sponser.isEmpty()) {
			sponser = Common.GC_DEFAULT_SPONSER;
		}
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn))
				 {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblProfile, true));
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName, true));
		}

	}

	@Override
	protected void load() {
		this.parent.get();

		((LandingPage) this.parent).dismissPopUps(false, false);
		this.lblUserName.click();

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
		if (fieldName.trim().equalsIgnoreCase("HOME")) {
			return this.lblHome;
		}

		// Profile
		if (fieldName.trim().equalsIgnoreCase("PROFILE")) {
			return this.lblProfile;
		}

		// Personal Contact Info
		if (fieldName.trim().equalsIgnoreCase("PERSONAL CONTACT INFORMATION")) {
			return this.hrdPersonalInfo;
		}
		// UserName
		if (fieldName.trim().equalsIgnoreCase("LABLE USER NAME")) {
			return this.hrdUserName;
		}

		// PERSONAL EMAIL ADDRESS
		if (fieldName.trim().equalsIgnoreCase("LABLE PERSONAL EMAIL ADDRESS")) {
			return this.lblPersonalEmailAdd;
		}
		// MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("LABLE MOBILE PHONE NUMBER")) {
			return this.lblMobileNo;
		}
		// TEXT PERSONAL EMAIL ADDRESS
		if (fieldName.trim().equalsIgnoreCase("PERSONAL EMAIL ADDRESS")) {
			return this.txtPersonalEmailAdd;
		}
		// TEXT MOBILE PHONE NUMBER
		if (fieldName.trim().equalsIgnoreCase("MOBILE PHONE NUMBER")) {
			return this.txtMobileNo;
		}
		// HOME MAILING ADDRESS
		if (fieldName.trim().equalsIgnoreCase("HOME MAILING ADDRESS")) {
			return this.hrdHomeMaililngAdd;
		}
		// HOME ADDRESS
		if (fieldName.trim().equalsIgnoreCase("LABLE HOME ADDRESS")) {
			return this.lblHomeAdd;
		}
		// TEXT HOME ADDRESS
		if (fieldName.trim().equalsIgnoreCase("HOME ADDRESS")) {
			return this.txtHomeAdd;
		}
		// USER NAME AND PASSWORD
		if (fieldName.trim().equalsIgnoreCase("USER NAME AND PASSWORD")) {
			return this.hrdUserNamePassword;
		}
		// LABEL USER NAME
		if (fieldName.trim().equalsIgnoreCase("LABEL USER NAME")) {
			return this.lblUsername;
		}
		// PASSWORD
		if (fieldName.trim().equalsIgnoreCase("LABLE PASSWORD")) {
			return this.lblPassword;
		}
		// TEXT USER NAME
		if (fieldName.trim().equalsIgnoreCase("USER NAME")) {
			return this.txtUsername;
		}
		// TEXT PASSWORD
		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed
	 */
	public boolean validateUserProfileInfo() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.lblProfile);
		boolean isTextMatching = false;
		if (isElementPresent) {

			Reporter.logEvent(Status.PASS,
					" Verify User 'Profile' Page is displayed",
					"User 'Profile' Page is displayed", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify User 'Profile' Page is displayed",
					"User 'Profile' Page is not displayed", true);
		}
		String userName=this.hrdUserName.getText().replaceAll("\\s+", " ").trim();
		isTextMatching = Web.VerifyText(Stock.GetParameterValue("lblUserName"),
				userName, true);

		if (isTextMatching) {
			Reporter.logEvent(Status.PASS,
					"Verify 'User Name In Profile Page'  is displayed",
					"USer Name is displayed\nExpected:"+Stock.GetParameterValue("lblUserName").trim()+"\nActual:"+userName, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'User Name In Profile Page'  is displayed",
					"USer Name is not Same\nExpected:"+Stock.GetParameterValue("lblUserName").trim()+"\nActual:"+userName, false);
		}

		verifyWebElementDisplayed("PERSONAL CONTACT INFORMATION");
		verifyWebElementDisplayed("LABLE PERSONAL EMAIL ADDRESS");
		verifyWebElementDisplayed("LABLE MOBILE PHONE NUMBER");
		verifyWebElementText("PERSONAL EMAIL ADDRESS");
		verifyWebElementText("MOBILE PHONE NUMBER");
		verifyWebElementDisplayed("HOME MAILING ADDRESS");
		verifyWebElementDisplayed("LABLE HOME ADDRESS");
		verifyWebElementText("HOME ADDRESS");
		verifyWebElementDisplayed("USER NAME AND PASSWORD");
		verifyWebElementDisplayed("LABEL USER NAME");
		verifyWebElementDisplayed("LABLE PASSWORD");
		verifyWebElementText("USER NAME");
		verifyWebElementText("PASSWORD");

		return isTextMatching;
	}

	public void verifyWebElementDisplayed(String fieldName) {

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName), true)) {

			Reporter.logEvent(Status.PASS, "Verify 'FiledName' is displayed",
					fieldName + " is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'FiledName'  is displayed",
					fieldName + " is not Same", false);
		}

	}

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return boolean - Displayed
	 */
	public void verifyWebElementText(String fieldName) {

		String getText = "";

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

			getText = this.getWebElement(fieldName).getText().trim();
			if (!getText.isEmpty()) {

				Reporter.logEvent(Status.PASS,
						"Verify Text for 'FiledName'  is displayed",
						"Text for " + fieldName + " is displayed", false);

			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Text for 'FiledName' is displayed", "Text for"
								+ fieldName + " is not Same", false);
			}
		}

	}
}
