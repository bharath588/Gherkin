package pageobjects.login;

import java.awt.event.KeyEvent;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class LoginPage extends LoadableComponent<LoginPage>{

	//Object Declarations 
	@FindBy(xpath=".//button[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(id="usernameInput") private WebElement txtUserName;
	@FindBy(id="passwordInput") private WebElement txtPassword;
	@FindBy(xpath=".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(css="a[href*='register']") private WebElement btnRegister;
	@FindBy(id="helpBlock") private WebElement weHelpBlock;
	@FindBy(xpath=".//*[@href='#/loginHelp' and text()='Forgot Password?']") private WebElement lnkForgotPassword;	


	LoadableComponent<?> parent;
	@SuppressWarnings("unused")
	private String username;
	@SuppressWarnings("unused")
	private String password;

	public LoginPage(){
		//this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	public LoginPage(String username,String password) {
		this.username = username;
		this.password = password;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {

		Assert.assertTrue(Web.isWebElementDisplayed(txtPassword));;
	}

	@Override
	protected void load() {

		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block              
		}*/
		/*lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);*/
		
		lib.Web.webdriver.get(Stock.globalParam.get("AppURL"));

		/*lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);*/

		lib.Web.webdriver.manage().window().maximize();

		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block              
		}*/
		//currently not beeing seen
		/*boolean isElementPresent = Web.isWebElementDisplayed(lnkDismiss,true);
		if (isElementPresent)
			lnkDismiss.click();*/

	}

	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	USERNAME - [TEXTBOX]
	 * 	PASSWORD - [TEXTBOX]
	 *  SIGN IN -[BUTTON]
	 *  REGISTER - [BUTTON]
	 *  FORGOT PASSWORD - [LINK]
	 *  </pre>
	 * @param fieldName
	 * @return
	 */

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//Username
		if (fieldName.trim().equalsIgnoreCase("USERNAME")) {
			return this.txtUserName;
		}

		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("SIGN IN")) {
			return this.btnLogin;			
		}

		if (fieldName.trim().equalsIgnoreCase("REGISTER")) {
			return this.btnRegister;			
		}

		if (fieldName.trim().equalsIgnoreCase("FORGOT PASSWORD")) {
			return this.lnkForgotPassword;			
		}


		//		Reporter.logEvent(
		//                    Status.WARNING,
		//                    "Get WebElement for field '" + fieldName + "'",
		//                    "No WebElement mapped for this field\nPage: <b> "+this.getClass().getName()+"</b>"
		//                    );
		return null;
	}


	/**Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 */
	public void submitLoginCredentials(String userName, String password){

		Web.setTextToTextBox(this.txtUserName, userName);
		Web.setTextToTextBox(this.txtPassword, password);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		boolean isElementPresent = Web.isWebElementDisplayed(lnkDismiss);

		if (isElementPresent)
		{
			lnkDismiss.click();
		}

		this.btnLogin.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**<pre> Method to validate if entered login credentials are invalid and an error message is displayed.
	 * Returns the error message displayed if an error block is displayed
	 * Returns empty string if no error block is displayed</pre>
	 * 
	 * @return String - Displayed error message
	 */
	public String isValidCredentials(){
		boolean isElementPresent = Web.isWebElementDisplayed(this.weHelpBlock);

		if (isElementPresent)
			return this.weHelpBlock.getText();
		else
			return "";
	}

	/**Method to click on <b>Register</b> button on Login page
	 * 
	 */
	public void clickRegister() {

		this.btnRegister.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**Method to click on <b>Forgot Password </b> button on Login page
	 * 
	 */
	public void clickForgotPassword() {

		this.lnkForgotPassword.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
