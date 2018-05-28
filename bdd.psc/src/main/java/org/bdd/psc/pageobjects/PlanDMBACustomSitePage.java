package org.bdd.psc.pageobjects;

import java.lang.reflect.Method;

import lib.Web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import reporter.Reporter;

import com.aventstack.extentreports.Status;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import cucumber.api.java.en.Then;

public class PlanDMBACustomSitePage extends LoadableComponent<PlanDMBACustomSitePage> {
	
	@FindBy(id = "userNavLabel")
	private WebElement userNavLabelMenu;
	
	private LoadableComponent<?> parent;
	public static WebDriver webDriver;
	private String[] userVeriData;
	private boolean ifUserOrAccntVerificationMandate = false;
	private String[] userData;
	private Method invokeMethod;
	
	public PlanDMBACustomSitePage(LoadableComponent<?> parent, boolean performVerification,
			String... userData) {
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData;
		this.userVeriData = new String[2];
		PageFactory.initElements(Web.getDriver(), this);
	}
	public PlanDMBACustomSitePage() {
		PageFactory.initElements(Web.getDriver(), this);
	}


	public PlanDMBACustomSitePage(WebDriver webDriver) {
		HomePage.webDriver = webDriver;
		this.parent = new LoginPage(webDriver);
		PageFactory.initElements(webDriver, this);
	}
	
	
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
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
				if (Web.isWebElementDisplayed(Web.returnElement(userVeriPg,
						"EMAIL ADDRESS"))) {
					userVeriData = new String[] {
							"discard@gwl.com",
							userVeriPg.getSecurityAnswer((Web.returnElement(
									userVeriPg, "SECURITYQUESTION")).getText()
									.trim()) };
					userVeriPg.performVerification(userVeriData);

				}
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
		JumpPage jumpPage = new JumpPage();
		if (Web.isWebElementDisplayed(jumpPage, "JUMP PAGE URL", false))
			try {
				Web.clickOnElement(Web.returnElement(jumpPage, "JUMP PAGE URL"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		if (!Web.isWebElementDisplayed(userNavLabelMenu)) {
			throw new AssertionError(
					"Plan DBMA Custom site landing page not loaded.");
		}

	}
	
	public boolean isPlanDMBACustomSitePage() {
		if (Web.getDriver()
				.getCurrentUrl()
				.contains(
						"devthree-retirementpartner.cs8.force.com/dmba/apex/DMBAPortalHome"))
			return true;
		return false;
	}
	
	


}
