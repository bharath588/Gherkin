package pageobjects.userverification;

import java.sql.ResultSet;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import pageobjects.login.LoginPage;
import lib.DB;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Web;
import lib.Stock;

public class UserVerificationPage extends LoadableComponent<UserVerificationPage> {

	/* Object declarations for UserVerificationPage */
	@FindBy(css = "input[class *= 'emailBox']")
	private WebElement txtUserVerificationEmail;
	@FindBy(css = "input[class *='answerBox']")
	private WebElement txtUserVerificationSecAns;
	@FindBy(css = "button[class *= 'swap-prelogin-cancel']")
	private WebElement btnUserVerfificationCancel;
	@FindBy(css = "button[class *= 'btn-u next']")
	private WebElement btnUserVerificationNext;
	@FindBy(xpath = ".//*[@id='logo']/img")
	private WebElement imgEmpowerPsc;
	@FindBy(xpath = ".//*[@class='verificationTable']/tbody/tr[1]/td/table/tbody/tr[1]/td[2]/label")
	private WebElement dispUserId;
	@FindBy(css = "div[class = 'ui-growl-item']")
	private WebElement errorMsgBox;
	@FindBy(css = "li[class = 'lastLink'] a")
	private WebElement linkLogOut;
	@FindBy(xpath = ".//*[@id='branding-topnav']/div/div[1]/h2/a/span")
	private WebElement linkPSC;
	@FindBy(xpath = ".//*[@id='gritter-item-1']/div/div[1]")
	private WebElement dismissErrorBox;
	/* variable declaration */
	LoadableComponent<?> parent;
	ResultSet resultset;
	private String[] loginData = new String[2];

	public UserVerificationPage() {
		this.parent = new LoginPage();
		PageFactory.initElements(Web.webdriver, this);
	}

	public UserVerificationPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Web.webdriver.switchTo().defaultContent();
		Assert.assertTrue(Web.isWebElementDisplayed(txtUserVerificationEmail));
	}

	@Override
	protected void load() {
		LoginPage login = new LoginPage();
		login.get();
		try {
			loginData[0] = Stock.GetParameterValue("username");
			loginData[1] = Stock.GetParameterValue("password");
			login.submitLoginCredentials(loginData);
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		try {
			Web.waitForElement(txtUserVerificationEmail);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	EMAIL ADDRESS- [TEXTBOX]
	 * 	SECURITY ANSWER - [TEXTBOX]
	 *  NEXT -[BUTTON]
	 *  CANCEL - [BUTTON]
	 *  CONTACT US - [LINK]
	 *  LOG OUT - [LINK]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("EMAIL ADDRESS")) {
			return this.txtUserVerificationEmail;
		}

		if (fieldName.trim().equalsIgnoreCase("SECURITY ANSWER")) {
			return this.txtUserVerificationSecAns;
		}

		if (fieldName.trim().equalsIgnoreCase("NEXT")) {
			return this.btnUserVerificationNext;
		}

		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
			return this.btnUserVerfificationCancel;
		}

		if (fieldName.trim().equalsIgnoreCase("LOG OUT")) {
			return this.linkLogOut;
		}
		if (fieldName.trim().equalsIgnoreCase("DISMISS")) {
			return this.dismissErrorBox;
		}
		if (fieldName.trim().equalsIgnoreCase("ERRORMSG")) {
			return this.errorMsgBox;
		}
		//
		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;
	}
	
	/** This method Performs user verification based on the user input */
	public void performVerification(String[] userVerfiData) {
		new UserVerificationPage();	
		
		if (Web.isWebElementDisplayed(txtUserVerificationEmail)) {			
			Web.setTextToTextBox(txtUserVerificationEmail, userVerfiData[0]);
			Web.setTextToTextBox(txtUserVerificationSecAns, userVerfiData[1]);
			Web.clickOnElement(btnUserVerificationNext);
			Web.waitForElement(imgEmpowerPsc);
		}		
		if (!Web.isWebElementDisplayed(imgEmpowerPsc)) {
				Reporter.logEvent(Status.INFO, "Verify if the user verification screen is loaded",
						"The user verification screen is not loaded", false);
			}else{
				Reporter.logEvent(Status.PASS, "Verify if the user verification screen is loaded",
						"The user verification screen is loaded", true);
			}
		}

	/**
	 * <pre>
	 * This method verifies the actual user id with the userid displayed on the
	 * user verification screen
	 * </pre>
	 */
	public void verifyUserIdDisplayed(String userName) {
		if((Web.VerifyText(userName, dispUserId.getText(), true))){
			Reporter.logEvent(Status.PASS, "Verify if the userid displayed","The user id is verified successfully",
					false);			
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the userid displayed", "The user id displayed on screen is not displayed correctly",
					true);
		}
	}

	/**
	 * <pre>
	 * This method checks whether the user id which is entered during logging into the application is 
	 * 	displayed on the user verification page
	 * </pre>
	 * 
	 * @return string
	 */

	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.errorMsgBox);

		if (isElementPresent)
			return this.errorMsgBox.getText();
		else
			return "";
	}

	/**
	 * This method fetches the email address of user from database
	 * @param getEmailQuery
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String getEmailAddressOfuser(String[] getEmailQuery, String userid) throws Exception {
		String emailAddr = "";
		resultset = DB.executeQuery(getEmailQuery[0], getEmailQuery[1], "K_" + userid);
		if (resultset != null) {
			while (resultset.next()) {
				emailAddr = resultset.getString("EMAIL_ADDRESS");
			}
		}
		return emailAddr;
	}
}
