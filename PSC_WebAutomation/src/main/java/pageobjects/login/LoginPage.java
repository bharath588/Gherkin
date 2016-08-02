package pageobjects.login;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class LoginPage extends LoadableComponent<LoginPage> {

	// Object Declarations

	@FindBy(id = "username")
	private WebElement txtUserName;
	@FindBy(id = "password")
	private WebElement txtPassword;
	@FindBy(id = "helpBlock")
	private WebElement weHelpBlock;
	@FindBy(xpath = ".//*[@href='#/loginHelp' and text()='Forgot Password?']")
	private WebElement lnkForgotPassword;
	@FindBy(css = "a[href*='register']")
	private WebElement btnRegister;
	@FindBy(css = "footer[class='page-footer'] div[class='nav-disclosure'] ul li")
	private List<WebElement> weFooterLinkListPreLogin;
	@FindBy(css = "div[class='col-sm-6 col-md-5 no-pad-right'] span[class='tagline text-uppercase']")
	private WebElement wePSCHeader;
	@FindBy(css = "div[class='breadcrumbs'] strong")
	private WebElement wePSCBreadCrum;
	@FindBy(css = "div[class='breadcrumbs'] a")
	private WebElement linkPSCBreadCrumHome;
	@FindBy(css = "iframe[id='mpwr_login']")
	private WebElement frmLogin;
	@FindBy(css = "button[id='login']")
	private WebElement btnLogin;
	@FindBy(css = "ul[class='list-inline'] li")
	private List<WebElement> weHeaderLinkListPreLogin;
	@FindBy(css = "site-header span[class='site-tagline ng-binding']")
	private WebElement weSiteTagLine;
	@FindBy(xpath = ".//*[@id='headNav']/li[2]/a")
	private WebElement linkLogOut;
	@FindBy(css = "input[class = 'chekbox']")
	private WebElement chkBoxrememberPassword;
	@FindBy(css = "div[id = 'errorMessage']")
	private WebElement wePreLoginError;
	@FindBy(css = "a[class = 'prelogin-pod']")
	private WebElement lnkPlanFeeds;
	@FindBy(css = "img[src *= 'redirect_logo.png']")
	private WebElement imgGWRS;
	@FindBy(css = "input[id = 'welcomeForm:username']")
	private WebElement txtForceLoginUsernameField;
	@FindBy(css = "img[alt = 'Empower Retirement']")
	private WebElement logoEmpower;
	@FindBy(xpath = ".//*[@id='loginSpinner']")
	private WebElement loginSpinner;

	LoadableComponent<?> parent;
	/*-----------------------------------------------------------------*/

	private List<String> getFooterLinkList = null;

	public LoginPage() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertEquals(Web.webdriver.getTitle(), "Empower Retirement - Plan Service Center");
	}

	@Override
	protected void load() {
		Web.webdriver.get(Stock.getConfigParam("AppURL"+"_"+Stock.getConfigParam("TEST_ENV")));	
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	USERNAME - [TEXTBOX]
	 * 	PASSWORD - [TEXTBOX]
	 *  SIGN IN -[BUTTON]
	 *  REGISTER - [BUTTON]
	 *  FORGOT PASSWORD - [LINK]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Username
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

		if (fieldName.trim().equalsIgnoreCase("FORCELOGIN USERNAME")) {
			return this.txtForceLoginUsernameField;
		}

		if (fieldName.trim().equalsIgnoreCase("GWRS IMAGE")) {
			return this.imgGWRS;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGIN FRAME")) {
			return this.frmLogin;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGO EMPOWER")) {
			return this.logoEmpower;
		}

		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;

	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	public void submitLoginCredentials(String[] loginData) {
		Web.webdriver.switchTo().frame(frmLogin);
		// common.webDriver.switchTo().frame(frmLogin);
		Web.setTextToTextBox(this.txtUserName, loginData[0]);
		Web.setTextToTextBox(this.txtPassword, loginData[1]);
		Web.clickOnElement(this.btnLogin);
		Web.webdriver.switchTo().defaultContent();
		// driver.switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			ThrowException.Report(TYPE.INTERRUPTED, "Exception occurred for thread sleep");
		}
	}

	/**
	 * <pre>
	 * Method to validate if entered login credentials are invalid and an error message is displayed
	 * Returns the error message displayed if an error block is displayed
	 * Returns empty string if no error block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed error message
	 */
	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.weHelpBlock);
		if (isElementPresent)
			return this.weHelpBlock.getText();
		else
			return "";
	}

	/**
	 * Method to open each footer link before login and validate if the
	 * respective page is opening
	 * 
	 * @throws Exception
	 */
	public void checkFooterLinkPreLogin() throws Exception {
		checkHeaderFooterLink(weFooterLinkListPreLogin, "link_Footer");
	}

	/**
	 * Method to open each header link before login and validate if the
	 * respective page is opening
	 */
	public void checkHeaderLinkPreLogin() {
		checkHeaderFooterLink(weHeaderLinkListPreLogin, "link_Header");
		Web.webdriver.navigate().back();
	}

	/**
	 * Method to get link text for each footer link before link
	 */
	private void getPreLoginFooterList() {
		List<String> footerLinkText = new LinkedList<String>();
		for (int iLoopCnt = 0; iLoopCnt <= weFooterLinkListPreLogin.size() - 1; iLoopCnt++) {
			footerLinkText.add(weFooterLinkListPreLogin.get(iLoopCnt).findElement(By.cssSelector("a")).getText());
		}
		setPreLoginFooterLinkList(footerLinkText);
	}

	/**
	 * This method verifies the error messages pre login for invalid credentials
	 * @param errorMsg
	 */
	public void verifyErrorforRespectiveLogin(String errorMsg) {
		boolean textMatch = false;
		try {
			new LoginPage();
			Web.webdriver.switchTo().frame(frmLogin);			
			textMatch = Web.VerifyPartialText(errorMsg, wePreLoginError.getText().replace("\n", ""), true);
		
			if(textMatch){
				Reporter.logEvent(Status.PASS, "Check if the appropiate error displayed",
						"Expected Error message displayed", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the appropiate error displayed",
						"Failed to display expected error message", true);
			}		
			Web.webdriver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for getFooterLinkList
	 */
	public List<String> getPreLoginFooterLinkList() {
		getPreLoginFooterList();
		return getFooterLinkList;
	}

	/**
	 * Setter for getFooterLinkList
	 */
	public void setPreLoginFooterLinkList(List<String> getFooterLinkList) {
		this.getFooterLinkList = getFooterLinkList;
	}

	/**
	 * Method to click on each Header and Footer links and validate if the
	 * redirection is correct
	 * 
	 * @param linkObjList
	 *            : WebElement list
	 * @param testDataColNm
	 *            : Test Data column name
	 * 
	 */
	private void checkHeaderFooterLink(List<WebElement> linkObjList, String testDataColNm) {
		String currentURL = "";
		String testData;
		int linkObjSize = linkObjList.size();
		try{
			 //Looping through the list of Header/Footer links
			for (int iLoopCnt = 0; iLoopCnt < linkObjSize - 1; iLoopCnt++) {
				new LoginPage();
				testData = Stock.GetParameterValue(testDataColNm + (iLoopCnt + 1));
				Thread.sleep(500);
				if (Web.isWebElementDisplayed(linkObjList.get(iLoopCnt).findElement(By.cssSelector("a")), true)) {
					Web.clickOnElement((linkObjList.get(iLoopCnt).findElement(By.cssSelector("a"))));
				}

				if ((testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")) {
					String winHandleBefore = Web.webdriver.getWindowHandle();
					for (String winHandle : Web.webdriver.getWindowHandles()) {
						Web.webdriver.switchTo().window(winHandle);
						currentURL = Web.webdriver.getCurrentUrl();
					}
					Web.webdriver.close();
					Web.webdriver.switchTo().window(winHandleBefore);
				} else {
					currentURL = Web.webdriver.getCurrentUrl();
				}

				// Verification against redirected URLs
				
				if((Web.VerifyPartialText(testData, currentURL, false))){
					Reporter.logEvent(Status.PASS,"Check if the respective page is loaded",
							 "Verified that the respective page :" + testData + " is loaded successfully", false);
				}else{
					Reporter.logEvent(Status.FAIL,"Check if the respective page is loaded",
							"Respective page :" + testData + " is not loaded", true);
				}
				
				if (!(testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")
						&& !(testDataColNm + (iLoopCnt + 1)).equals("link_Header2")) {
					Web.waitForElement(linkPSCBreadCrumHome);
					linkPSCBreadCrumHome.click();
				}
			}
		}catch(Exception e){
			 throw new Error("Error occurred while checking " +
			 testDataColNm.split("link_")[1] +
			 " links , Exception:"+e.getMessage());
		}
	}
	
	public void waitForSuccessfulLogin() throws InterruptedException
	{
		if(Web.isWebElementDisplayed(frmLogin))
		{
		Web.webdriver.switchTo().frame(frmLogin);
		do
		{
		Thread.sleep(3000);	
		}while(Web.isWebElementDisplayed(loginSpinner));
		}
		Web.webdriver.switchTo().defaultContent();
	}
}
