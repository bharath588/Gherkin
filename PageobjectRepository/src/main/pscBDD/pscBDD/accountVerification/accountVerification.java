package pscBDD.accountVerification;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import bdd_lib.CommonLib;
import bdd_lib.Web;
import bdd_reporter.Reporter;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.userVerification.UserVerificationPage;


public class accountVerification extends LoadableComponent<accountVerification> {
	
	
	@FindBy(xpath = "//h1[contains(text(),'Account verification')]")
	private WebElement lblaccountVerify;
	@FindBy(xpath = "//table//td[2]/input")
	private List<WebElement> txtSecretAns;
	@FindBy(xpath = "//table//td[1]/select")
	private List<WebElement> txtSecretQues;
	@FindBy(xpath = "//button/span[text()='Next']")
	private WebElement btnNext;
	@FindBy(css = "div[id='greeting'] span[class='label']")
	private WebElement weGreeting;
	@FindBy(xpath = "//table//td[1]/select/option[@selected='selected']")
	private List<WebElement> drpdwnSecretQues;
	
	LoadableComponent<?> parent;
	public static WebDriver webDriver;
	CommonLib commonLib;
	WebDriverWait wait;
	UserVerificationPage userVeriPg;
	private boolean ifUserOrAccntVerificationMandate = false;
	private String[] userData;
	private Method invokeMethod;
	
	public accountVerification(){
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	public accountVerification(WebDriver webDriver){
		accountVerification.webDriver = webDriver;
		this.parent = new LoginPage(webDriver);	
		PageFactory.initElements(webDriver, this);	
	}
	
	public accountVerification(LoadableComponent<?> parent, boolean performVerification,
			String... userData) {
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData;	
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void load() {
		this.parent.get();	
		LoginPage loginObj = new LoginPage();
		try {
			if (ifUserOrAccntVerificationMandate) { // Performs User or Account
				// Verification
				invokeMethod = this.parent.getClass().getDeclaredMethod(
						"performVerification", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),
						new Object[] { userData });
			} else {
				// Performing Login
				Reporter.logEvent(Status.INFO, "Verify user is on Login Page",
						"User is on Login Page", true);
				Object login = this.parent.getClass().newInstance();
				Web.getDriver().switchTo()
				.frame(Web.returnElement(login, "LOGIN FRAME"));
				Web.waitForElement(login, "USERNAME");
				Web.waitForElement(login, "PASSWORD");
				Web.getDriver().switchTo().defaultContent();
				invokeMethod = this.parent.getClass().getDeclaredMethod(
						"submitLoginCredentials", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),
						new Object[] { userData });
				loginObj.waitForSuccessfulLogin();
				Reporter.logEvent(Status.PASS, "Verify user is on Account Verification Page after successful login",
						"User is on Account Verification Page after successful login", true);
			}	
		} catch (Exception e) {
			try {
				throw new Exception(
						"Login to PSC failed , Error description : "
								+ e.getMessage());
			} catch (Exception t) {
				ThrowException.Report(TYPE.EXCEPTION, t.getMessage());
			}
		}	
	}

	
	
	@Override
	protected void isLoaded() throws Error {
		if (!Web.isWebElementDisplayed(lblaccountVerify)) {
			throw new AssertionError(
					"Account Verification landing page not loaded.");
		}
	}
	
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Account Verification")) {
			return this.lblaccountVerify;
		}			
		if (fieldName.trim().equalsIgnoreCase("Next")) {
			return this.btnNext;
		}
		return null;

	}
	
	
	public boolean verifySecurityQuestions(){
		int secretQues;
		secretQues= txtSecretQues.size();
		if (secretQues == 3){
			return true;
		}
		return false;		
	}
	
	
	

}
