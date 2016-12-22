package pageobjects.login;

import java.util.ArrayList;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import core.framework.Globals;

public class LoginPage extends LoadableComponent<LoginPage> {

	// Object Declarations
	@FindBy(xpath = ".//button[text()[normalize-space()='Dismiss']]")
	private WebElement btnDismiss;
	@FindBy(id = "usernameInput")
	private WebElement txtUserName;
	@FindBy(id = "passwordInput")
	private WebElement txtPassword;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;
	@FindBy(css = "a[href*='register']")
	private WebElement btnRegister;
	@FindBy(id = "helpBlock")
	private WebElement weHelpBlock;
	@FindBy(xpath = ".//*[text()[normalize-space()='Login help?']]")
	private WebElement lnkForgotPassword;
	@FindBy(xpath = ".//*[@id='customerSupport']//p[1]")
	private WebElement hrdCustomerSupport;
	@FindBy(linkText = "contact us")
	private WebElement lnkContactus;
	@FindBy(xpath = ".//*[@id='prelogin-contact-modal']//h1")
	private WebElement txtContactus;
	@FindBy(xpath = ".//div[@class='modal-content']/div[2]/.")
	private WebElement txtContactusInfo;
	@FindBy(xpath = ".//span[@id='prelogin-footer-phone']")
	private WebElement lnkContactNoPreLogin;
	@FindBy(xpath = ".//*[@id='customerSupport']//span")
	private WebElement lnkContactNoPostLogin;
	@FindBy(xpath = "//a[text()='Log out']")
    private WebElement  lnkLogOut;


	/*
	 * @FindBy(xpath =
	 * ".//div[@class='container']/span[@ng-if='accuLogoLoaded']/img") private
	 * WebElement lblSponser;
	 */
	@FindBy(xpath = ".//*[@class='banner-wrapper']/img")
	private WebElement imgBanner;
	@FindBy(id="helpBlock")
	private WebElement sessionTimeOutErrMsg;
	@FindBy(xpath = ".//*[@class='copyright']")
	private WebElement txtCopyRightInfo;
	@FindBy(linkText = "Requirements and Security")
	private WebElement lnkSystemRequirements;
	@FindBy(linkText = "Privacy")
	private WebElement lnkPrivacy;
	@FindBy(linkText = "Terms")
	private WebElement lnkTermsandConditions;
	@FindBy(linkText = "Business Continuity")
	private WebElement lnkBusinessContinuityPlan;
	@FindBy(linkText = "Market Timing and Excessive Trading")
	private WebElement lnkMarkeTiming;
	@FindBy(xpath = "//a[./img[contains(@alt,'BrokerCheck')]]")
	private WebElement lnkBrokerCheckNotification;
	@FindBy(linkText = "FINRA Investor Education")
	private WebElement lnkFINRAInvestorEducation;
	@FindBy(xpath = "//a//img[following-sibling::h3[contains(text(),'Saving more can be')]]")
	private WebElement imgSavings;
	@FindBy(xpath = "//a//img[following-sibling::h3[contains(text(),'Changing jobs?')]]")
	private WebElement imgRollover;
	@FindBy(xpath = "//a//img[following-sibling::h3[contains(text(),'Having browser issues')]]")
	private WebElement imgBrowserSupport;
	@FindBy(xpath = ".//*[@id='prelogin-contact-modal-button'][contains(text(),'Dismiss')]")
	private WebElement lnkDismiss;
	@FindBy(xpath = ".//div[contains(@class,'inner-container')]//h2")
	private WebElement txtInnerContainer;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//div[@class='modal-body']//p//a")
	private List<WebElement> txtTelPhoneNos;

	LoadableComponent<?> parent;
	@SuppressWarnings("unused")
	private String username;
	@SuppressWarnings("unused")
	private String password;
	private String url = null;

	public LoginPage() {
		// this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	public LoginPage(String username, String password) {
		this.username = username;
		this.password = password;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		//Assert.assertTrue(Web.webdriver.getCurrentUrl().contains(Common.getSponser()),"Login Page is Not Loaded");
		if(!Web.isWebElementDisplayed(sessionTimeOutErrMsg,true))
		Assert.assertTrue(Web.isWebElementDisplayed(txtPassword),"Login Page is Not Loaded\n");	    
		else{
			Assert.assertTrue(false);
		}
		String accuCode = null;
		boolean isElementPresent = Web.isWebElementDisplayed(btnDismiss, true);
		if (isElementPresent)
			btnDismiss.click();
		 
	/*	if (Stock.globalTestdata.containsKey("ACCUCODE"))

		{
			if (Stock.GetParameterValue("AccuCode") != null)
				accuCode = Stock.GetParameterValue("AccuCode");
			else
				accuCode = Common.GC_DEFAULT_SPONSER;
			url = Stock.getConfigParam(
					"AppURL" + "_" + Stock.getConfigParam("TEST_ENV")).replace(
					"Empower", accuCode);

			Assert.assertTrue(
					Web.webdriver.getCurrentUrl().equalsIgnoreCase(url),
					"Login page for '" + Common.getSponser()
							+ "' is not loaded.");

		}*/
	}

	@Override
	protected void load() {
		/*
		 * lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		 * lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);
		 */

		// lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		// lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);

		// lib.Web.webdriver.get(Stock.getConfigParam("AppURL"));
		if (Stock.globalTestdata.containsKey("ACCUCODE")
				&& Stock.GetParameterValue("AccuCode") != null) {

			url = Stock.getConfigParam(
					"AppURL" + "_" + Stock.getConfigParam("TEST_ENV")).replace(
					"Empower", lib.Stock.GetParameterValue("AccuCode"));
		}

		else {
			url = Stock.getConfigParam("AppURL" + "_"
					+ Stock.getConfigParam("TEST_ENV"));

		}

		/*
		 * lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		 * lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);
		 */

		// lib.Web.robot.keyPress(KeyEvent.VK_ESCAPE);
		// lib.Web.robot.keyRelease(KeyEvent.VK_ESCAPE);
		 if(Web.isWebElementDisplayed(lnkLogOut))
		 {
			Web.clickOnElement(lnkLogOut);
			Web.waitForPageToLoad(Web.webdriver);
		 }		
		 else
		 {	
		 if(Web.isWebElementDisplayed(sessionTimeOutErrMsg,true))
		 {
			 Web.webdriver.navigate().to(url);
			 Web.webdriver.navigate().refresh();
			 Web.waitForPageToLoad(Web.webdriver);
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Web.getWebDriver(Stock.getConfigParam("BROWSER"));			 
			
		 }
		  Web.webdriver.get(url);
		//Web.webdriver.navigate().refresh();
		if (!Stock.getConfigParam("BROWSER").equalsIgnoreCase("CHROME"))
			lib.Web.webdriver.manage().window().maximize();
		System.out.println("Application URL:"+url);
		 }

		// currently not beeing seen
       if(Stock.getConfigParam("TEST_ENV").toUpperCase().startsWith("PROJ")){
		boolean isElementPresent = Web.isWebElementDisplayed(btnDismiss, true);
		if (isElementPresent)
			btnDismiss.click();
		Web.waitForPageToLoad(Web.webdriver);
	}
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
		if (fieldName.trim().equalsIgnoreCase("CONTACT US")) {
			return this.lnkContactus;
		}
		if (fieldName.trim().equalsIgnoreCase("COPYRIGHT INFO")) {
			return this.txtCopyRightInfo;
		}
		if (fieldName.trim().equalsIgnoreCase("Requirements and Security")) {
			return this.lnkSystemRequirements;
		}
		if (fieldName.trim().equalsIgnoreCase("Privacy")) {
			return this.lnkPrivacy;
		}
		if (fieldName.trim().equalsIgnoreCase("Terms")) {
			return this.lnkTermsandConditions;
		}
		if (fieldName.trim().equalsIgnoreCase("Business Continuity")) {
			return this.lnkBusinessContinuityPlan;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"Market Timing and Excessive Trading")) {
			return this.lnkMarkeTiming;
		}
		if (fieldName.trim().equalsIgnoreCase("BrokerCheck Notification")) {
			return this.lnkBrokerCheckNotification;
		}
		if (fieldName.trim().equalsIgnoreCase("FINRA INVESTOR EDUCATION")) {
			return this.lnkFINRAInvestorEducation;
		}
		if (fieldName.trim()
				.equalsIgnoreCase("IMAGE participant Savings rates")) {
			return this.imgSavings;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"IMAGE participant Rollover options")) {
			return this.imgRollover;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"IMAGE participant Browser Support")) {
			return this.imgBrowserSupport;
		}
		if (fieldName.trim().equalsIgnoreCase("Dismiss")) {
			return this.lnkDismiss;
		}
		if (fieldName.trim().equalsIgnoreCase("Inner Container")) {
			return this.txtInnerContainer;
		}

		return null;
	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 */
	public void submitLoginCredentials(String userName, String password) {

		Web.setTextToTextBox(this.txtUserName, userName);
		Web.setTextToTextBox(this.txtPassword, password);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		boolean isElementPresent = Web.isWebElementDisplayed(btnDismiss);

		if (isElementPresent) {
			btnDismiss.click();
		}

		this.btnLogin.click();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.webdriver);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to validate if entered login credentials are invalid and an error message is displayed.
	 * Returns the error message displayed if an error block is displayed
	 * Returns empty string if no error block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed error message
	 */
	public String isValidCredentials() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.weHelpBlock);

		if (isElementPresent)
			return this.weHelpBlock.getText();
		else
			return "";
	}

	/**
	 * Method to click on <b>Register</b> button on Login page
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

	/**
	 * Method to click on <b>Forgot Password </b> button on Login page
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

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed
	 */
	public String isValidCustomerSupportInfo() {
		boolean isElementPresent = Web
				.isWebElementDisplayed(this.hrdCustomerSupport);

		if (isElementPresent) {
			return this.hrdCustomerSupport.getText().trim();
		} else
			return "";

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
	public boolean isValidContactUsInfo(String contactNo) {
		boolean isElementPresent = Web.isWebElementDisplayed(this.lnkContactus);
		boolean isTextMatching = false;
		if (isElementPresent) {
			Web.clickOnElement(this.lnkContactus);

			if (Web.isWebElementDisplayed(this.txtContactus, true)) {
				isTextMatching = Web.VerifyText("Contact Us", this.txtContactus
						.getText().trim(), true);
				if (isTextMatching) {
					Reporter.logEvent(Status.PASS,
							"Verify 'Contact Us' header is displayed",
							"Contact Us Header is displayed", true);

				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify 'Contact Us' header is displayed",
							"Contact Us Header is not displayed", true);
				}
				String Actual = txtContactusInfo.getText().toString().trim();
				isTextMatching = Web
						.VerifyText(
								"Corporate 401(k) plans\n1-855-756-4738    (TTY 1-800-482-5472)\nGovernment, healthcare, education, or faith plans\n1-800-701-8255    (TTY 1-800-766-4952)\nAll plans based in New York State\n1-877-456-4015",
								Actual, true);
				if (isTextMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Contact Us Info'  is displayed",
							"Contact Us Info is displayed\n Expected:Corporate 401(k) plans\n1-855-756-4738    (TTY 1-800-482-5472)\nGovernment, healthcare, education, or faith plans\n1-800-701-8255    (TTY 1-800-766-4952)\nAll plans based in New York State\n1-877-456-4015 \nActual:"
									+ Actual, false);

				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Contact Us info' is displayed",
							"Contact Us Info is not Same \n Expected:Corporate 401(k) plans\n1-855-756-4738    (TTY 800.482.5472)\nGovernment, healthcare, education, or faith plans\n1-800-701-8255    (TTY 800.766.4952)\nAll plans based in New York State\n1-877-456-4015 \nActual:"
									+ Actual, false);
				}
			}
		} else {
			if (!contactNo.isEmpty()) {

				isTextMatching = Web.VerifyText(contactNo,
						this.lnkContactNoPreLogin.getText().trim(), true);
				if (isTextMatching) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Contact No'  is displayed on Pre Login Page",
							"Contact No is displayed on Pre Login Page", false);

				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Contact No' is displayed on Pre Login Page",
							"'Contact No' is not Same on Pre Login Page. Expected/ "
									+ contactNo
									+ "Actual/ "
									+ this.lnkContactNoPreLogin.getText()
											.trim(), false);
				}
			}
		}

		return isTextMatching;
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
	public boolean isValidContactUsInfoPostLogin(List<String> contactNo) {

		boolean isTextMatching = false;
		if (contactNo.size()>1) {
   for(int i=0;i<contactNo.size();i++)
   {
			isTextMatching = Web.VerifyText(contactNo.get(i),
					this.lnkContactNoPostLogin.getText().trim(), true);
			if (isTextMatching) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Contact No'  is displayed on Landing Page",
						"Contact No is displayed on Landing Page is matching", false);

			} else {
				Reporter.logEvent(Status.INFO,
						"Verify 'Contact No' is displayed on Landing Page",
						"'Contact No' is not Same on Landing Page.\nExpected:"
								+ contactNo.get(i) + "\nActual:"
								+ this.lnkContactNoPostLogin.getText().trim(),
						false);
			}
		}
		}else{
			isTextMatching = Web.VerifyText(contactNo.get(0),
					this.lnkContactNoPostLogin.getText().trim(), true);
			if (isTextMatching) {
				Reporter.logEvent(Status.PASS,
						"Verify 'Contact No'  is displayed on Landing Page",
						"Contact No is displayed on Landing Page is matching", false);

			} else {
				Reporter.logEvent(Status.INFO,
						"Verify 'Contact No' is displayed on Landing Page",
						"'Contact No' is not Same on Landing Page. Expected/ "
								+ contactNo.get(0) + "Actual/ "
								+ this.lnkContactNoPostLogin.getText().trim(),
						false);
			}
		}

		return isTextMatching;
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
	public boolean isSponsorLogoDisplayed(String logoName) {
		boolean isElementPresent = Web.isWebElementDisplayed(this.lblSponser);
		boolean isLogoPresent = false;
		if (logoName == null) {
			logoName = "";
		}
		if (isElementPresent) {
			isLogoPresent = true; /*
								 * Web.VerifyText(logoName, this.lblSponser
								 * .getAttribute("alt").trim(), true);
								 */
		}
		return isLogoPresent;

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
	public boolean isSponsorBannerDisplayed() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.imgBanner);
		boolean isBannerPresent = false;

		if (isElementPresent) {
			isBannerPresent = true;
		}
		return isBannerPresent;

	}

	public boolean verifyWebElementDisplayed(String fieldName) {

		boolean isDisplayed = Web.isWebElementDisplayed(
				this.getWebElement(fieldName), true);

		if (isDisplayed) {

			Reporter.logEvent(Status.PASS, "Verify \'"+fieldName+"\'  is displayed",
					fieldName + " is displayed", false);
			isDisplayed = true;

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify \'"+fieldName+"\' is displayed", fieldName
							+ " is not Same", false);
		}

		return isDisplayed;

	}

	/**
	 * <pre>
	 * Method to get the text of an webElement
	 * Returns string webElement is displayed
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementText(String fieldName) {
		String getText = "";

		if (verifyWebElementDisplayed(fieldName)) {

			getText = this.getWebElement(fieldName).getText().trim();

		}

		return getText;

	}

	public void verifyLinkIsNotBroken(String linkName)
			throws InterruptedException {
		boolean isPageDisplayed = false;
		Web.clickOnElement(this.getWebElement(linkName));
		Web.waitForElement(txtInnerContainer);
		isPageDisplayed = Web.VerifyPartialText(linkName,
				this.txtInnerContainer.getText().toString().trim(), true);
		if (isPageDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + linkName
					+ " Page is Displayed", linkName + " Page is Displayed",
					true);

		} else {
			lib.Reporter.logEvent(Status.FAIL, "Verify " + linkName
					+ " Page is Displayed",
					linkName + " Page is Not Displayed", true);
		}
		Web.webdriver.navigate().back();

	}

	@SuppressWarnings("null")
	public List<String> getPreLoginTelePhoneNo(String sponsorName) {
		ArrayList<String> telPhoneNos = new ArrayList<String>();
		if (sponsorName.equalsIgnoreCase(Common.GC_DEFAULT_SPONSER)) {

			int no = this.txtTelPhoneNos.size();
			for (int i = 0; i < no; i++) {
				telPhoneNos.add(txtTelPhoneNos.get(i).getText());
				
			}
		} else {
			telPhoneNos.add(lnkContactNoPreLogin.getText());
		}
		
		return telPhoneNos;
	}

}
