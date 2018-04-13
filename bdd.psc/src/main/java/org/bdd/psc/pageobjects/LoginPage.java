/**
 * 
 */
package org.bdd.psc.pageobjects;

import java.util.List;

import lib.CommonLib;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import reporter.Reporter;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

/**
 * @author rvpndy
 *
 */
public class LoginPage extends LoadableComponent<LoginPage> {

	public static WebDriver webDriver;
	@FindBy(id = "username")
	private WebElement txtUserName;
	@FindBy(id = "password")
	private WebElement txtPassword;
	@FindBy(css = "iframe[id='mpwr_login']")
	private WebElement frmLogin;
	@FindBy(css = "button[id='login']")
	private WebElement btnLogin;
	@FindBy(css = "img[alt = 'Empower Retirement']")
	private WebElement logoEmpower;
	@FindBy(xpath = ".//*[@id='loginSpinner']")
	private WebElement loginSpinner;
	@FindBy(css = "input[class *= 'emailBox']")
	private WebElement txtUserVerificationEmail;
	@FindBy(id = "logOutLink")
	private WebElement logout;
	@FindBy(id = "errorMessage")
	private WebElement errorMessage;
	@FindBy(xpath = "//div[@class='disclosure']/p/a")
	private WebElement termsAndConditionsLink;
	@FindBy(xpath = "//div[contains(@class,'footer-doc-links')]//li")
	private List<WebElement> footerLinks;
	@FindBy(linkText = "Forgot Password?")
	private WebElement forgotPassword;
	@FindBy(xpath = ".//*[@id='main']/div[2]/div[2]/div")
	private WebElement sysReqDiv;
	@FindBy(id = "ifrmFooter")
	private WebElement iframeFooter;
	@FindBy(id = "fund_iframe")
	private WebElement prospectusFrame;
	@FindBy(id = "fundProspectusDisclaimer")
	private List<WebElement> fundProspectusContent;
	@FindBy(xpath = "//*[@id='main']//div/ol[@class='breadcrumb']")
	private WebElement prospectusBreadcrumb;
	@FindBy(xpath = ".//*[@id='main']/div[1]/div/ol/li[2]")
	private WebElement preloginBreadcrumbs;
	@FindBy(xpath = ".//*[@id='main']/div[1]/div/ol/li[1]")
	private WebElement homeLinkInBreadcrumb;

	LoadableComponent<?> parent;
	public static String URL;
	CommonLib commonLib;
	ForgotPasswordPage forgotPwdPage;
	String primeWindow = "";
	static String accucode;

	/*-----------------------------------------------------------------*/

	public LoginPage() {

		PageFactory.initElements(Web.getDriver(), this);
	}

	public LoginPage(WebDriver webDriver) {
		LoginPage.webDriver = webDriver;
		commonLib = new CommonLib(webDriver);
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		// webDriver.get(Stock.getConfigParam("AppURLPSC"));
		if (!(URL == null)) {
			Web.getDriver().get(URL);
			Web.getDriver().manage().window().maximize();
		} else {
			URL = Stock.getConfigParam("AppURLPSC");
			URL = URL.replace("accucode", Stock.getConfigParam("accucode"));
			Web.getDriver().get(URL);
			Web.getDriver().manage().window().maximize();
		}
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Web.getDriver().switchTo().defaultContent();
		Assert.assertTrue(Web.isWebElementDisplayed(frmLogin, false)
				&& Web.getDriver().getCurrentUrl()
						.contains(LoginPage.accucode + "/welcome"));

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

		if (fieldName.trim().equalsIgnoreCase("Forgot Password")) {
			return this.forgotPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("LOGIN FRAME")) {
			return this.frmLogin;
		}

		if (fieldName.trim().equalsIgnoreCase("LOGO EMPOWER")) {
			return this.logoEmpower;
		}
		if (fieldName.trim().equalsIgnoreCase("Logout")) {
			return this.logout;
		}

		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;

	}

	@SuppressWarnings("unused")
	private List<WebElement> getWebElements(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Footer Links")) {
			return this.footerLinks;
		}
		return null;
	}

	public void submitLoginCredentials(String userName, String password) {
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frmLogin);
		Web.setTextToTextBox(this.txtUserName, userName);
		Web.setTextToTextBox(this.txtPassword, password);
		Web.clickOnElement(this.btnLogin);
		Web.getDriver().switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			ThrowException.Report(TYPE.INTERRUPTED,
					"Exception occurred for thread sleep");
		}

	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	public void submitLoginCredentials(String[] loginData) {
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frmLogin);
		Web.setTextToTextBox(this.txtUserName, loginData[0]);
		Web.setTextToTextBox(this.txtPassword, loginData[1]);
		Web.clickOnElement(this.btnLogin);
		Web.getDriver().switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			ThrowException.Report(TYPE.INTERRUPTED,
					"Exception occurred for thread sleep");
		}
	}

	public void waitForSuccessfulLogin() throws InterruptedException {
		// TODO Auto-generated method stub

		if (Web.isWebElementDisplayed(frmLogin))
			Web.getDriver().switchTo().frame(frmLogin);
		do {
			Thread.sleep(3000);
		} while (Web.isWebElementDisplayed(loginSpinner));
		Web.getDriver().switchTo().defaultContent();

	}

	public void logout() throws InterruptedException {
		if (Web.isWebElementDisplayed(logout)) {
			Web.clickOnElement(logout);
			Web.waitForElement(frmLogin);
		}
	}

	public static String setURL(String accucode) {
		LoginPage.accucode = accucode;
		String cleintURL = Stock.getConfigParam("AppURL"+"_"+Stock.getConfigParam("TEST_ENV"));
		System.out.println("client URL is:" + cleintURL);
		System.out.println("Accucode is :" + LoginPage.accucode);
		URL = cleintURL.replace("accucode", accucode);
		if (accucode.equalsIgnoreCase("InstAF")) {
			URL = URL.replace("welcome", "welcome_bundled");
			return URL;
		}
		return URL;
	}

	public boolean isErrorDisplayed() {
		if (errorMessage.isDisplayed()) {
			Reporter.logEvent(Status.INFO, "Error displays", "Error displays:"
					+ errorMessage.getText(), true);
			return true;
		}
		return false;
	}

	public boolean isTermsAndConditionsDisplayed() {
		if (Web.isWebElementDisplayed(termsAndConditionsLink))
			return true;
		return false;
	}

	public boolean isTnCDisplayedInSysTray() {
		String footerLink = "";
		System.out.println("Footer links size:" + footerLinks.size());
		if (Web.isWebElementsDisplayed(footerLinks)) {
			for (WebElement ele : footerLinks) {
				footerLink += ele.getText() + ", ";
			}
			Reporter.logEvent(Status.INFO, "Footer links on login page are:",
					footerLink, true);
			if (!footerLink.contains("Terms and Conditions"))
				return true;
		}
		return false;
	}

	public void clickOnTermsAndConditionsLink() {
		if (Web.isWebElementDisplayed(termsAndConditionsLink))
			Web.clickOnElement(termsAndConditionsLink);
	}

	public boolean tNCOpensInNewTab() {
		primeWindow = Web.getDriver().getWindowHandle();
		if (Web.getDriver().getWindowHandles().size() > 1) {
			for (String subWindow : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(subWindow);
			}
			String tNcURL = Web.getDriver().getCurrentUrl();
			if (tNcURL.contains("InstSunTrust/pdf/TermsandConditions.pdf")) {
				Web.getDriver().close();
				Web.getDriver().switchTo().window(primeWindow);
				return true;
			}
		}
		return false;
	}

	public void clickOnFooterLink(String footerLink) {
		try {
			if (Web.isWebElementsDisplayed(footerLinks)) {
				for (WebElement ele : footerLinks) {
					if (ele.getText().contains(footerLink)) {
						Web.clickOnElement(ele);
						Reporter.logEvent(Status.INFO,
								"Click on " + footerLink, "Click on "
										+ footerLink, true);
						Thread.sleep(2000);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSysReqDisplayed() {
		try {
			if (Web.isWebElementDisplayed(sysReqDiv, true)) {
				Reporter.logEvent(Status.INFO,
						"System Requirement marking text:",
						sysReqDiv.getText(), true);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean doTextMatches(String marketingText, WebElement... frame) {

		try {
			forgotPwdPage = new ForgotPasswordPage();
			if (frame.length > 0) {
				Web.FrameSwitchONandOFF(true, frame);
				if (Web.isWebElementDisplayed(forgotPwdPage,
						"System Requirement Window")) {
					if (Web.returnWebElement(forgotPwdPage,
							"System Requirement Window").getText().trim()
							.contains(marketingText.trim()))
						return true;
				}
			} else if (Web.isWebElementDisplayed(sysReqDiv)) {
				if (sysReqDiv.getText().trim()
						.equalsIgnoreCase(marketingText.trim()))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public void displayFundProspectusContent() {

		try {
			String fundProsContent = "";
			Web.FrameSwitchONandOFF(true, prospectusFrame);
			Web.waitForElements(fundProspectusContent);
			for (WebElement ele : fundProspectusContent) {
				fundProsContent += ele.getText() + "\n\n";
			}
			if (fundProsContent.length() > 0) {
				Reporter.logEvent(Status.PASS,
						"Fund prospectus content displayed", fundProsContent,
						true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Fund prospectus content displayed",
						"Fund prospectus content not displayed", true);
			}
			Web.FrameSwitchONandOFF(false, prospectusFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isFundProspectusBreadCrumbDisplayed() {
		try {
			if (Web.isWebElementDisplayed(prospectusBreadcrumb, true))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static String getBrowserTitle() {
		return Web.getDriver().getTitle();
	}

	public boolean isLinkDisplayed(String footerLinkName) {
		for (WebElement link : footerLinks) {
			if (link.getText().trim().equalsIgnoreCase(footerLinkName))
				return true;
		}
		return false;
	}

	public boolean isCorrectPage(String pageName) {
		if (Web.isWebElementDisplayed(preloginBreadcrumbs, true)) {
			if (preloginBreadcrumbs.getText().equalsIgnoreCase(pageName))
				return true;
		}
		return false;
	}

	// click on home link
	public void clickOnHomeLink() {
		try {
			if (Web.isWebElementDisplayed(homeLinkInBreadcrumb, true)) {
				Web.clickOnElement(homeLinkInBreadcrumb);
				Thread.sleep(2000);
				/*
				 * Reporter.logEvent(Status.INFO,
				 * "Click on home link in breadcrumb",
				 * "Click on home link in breadcrumb", true);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// verify pre login page is loaded
	public boolean isPreloginPage() {
		if (Web.getDriver().getCurrentUrl()
				.contains(LoginPage.accucode + "/welcome"))
			return true;
		return false;
	}

}
