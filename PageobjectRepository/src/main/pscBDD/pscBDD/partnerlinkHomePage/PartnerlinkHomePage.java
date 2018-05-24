/**
 * 
 */
package pscBDD.partnerlinkHomePage;

import java.lang.reflect.Method;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pscBDD.commonLib.CommonLib;
import pscBDD.login.LoginPage;
import pscBDD.userVerification.UserVerificationPage;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

/**
 * @author rvpndy
 *
 */
public class PartnerlinkHomePage extends LoadableComponent<PartnerlinkHomePage> {
	
	@FindBy(xpath=".//*[contains(@id,'content')]/h2")
	private WebElement welcomeToPartnerLink;
	@FindBy(id="framea")
	private WebElement landingPageFrame;
	@FindBy(xpath=".//*[contains(@id,'menu')]//li/a//span[text()='Implementation']")
	private WebElement implementationMenu;
	@FindBy(xpath="//a/span/span[text()='Plan Express']")
	private WebElement planExpressSubMenu;
	@FindBy(xpath="html/body/div[1]/div[1]")
	private WebElement welcomeToPlanExpress;
	
	private LoadableComponent<?> parent;
	public static WebDriver webDriver;
	private String[] userVeriData;
	private boolean ifUserOrAccntVerificationMandate = false;
	private String[] userData;
	private Method invokeMethod;
	Actions act;
	JavascriptExecutor jse;
	
	public PartnerlinkHomePage(LoadableComponent<?> parent, boolean performVerification,
			String... userData) {
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData;
		this.userVeriData = new String[2];
		PageFactory.initElements(Web.getDriver(), this);
	}
	public PartnerlinkHomePage(){
		PageFactory.initElements(Web.getDriver(), this);
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
						"performVerificationHeritage", String[].class);
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
						"HERITAGE EMAIL"))) {
					userVeriData = new String[] {
							"discard@gwl.com",
							userVeriPg.getSecurityAnswer((Web.returnElement(
									userVeriPg, "HERITAGE SEC QUESTN")).getText()
									.trim()) };
					userVeriPg.performVerificationHeritage(userVeriData);
					CommonLib.FrameSwitchONandOFF(true, landingPageFrame);
					Web.waitForElement(welcomeToPartnerLink);
					CommonLib.FrameSwitchONandOFF(false, landingPageFrame);

				}
			}
		} catch (Exception e) {
			try {
				throw new Exception(
						"Login to PSC Partnerlink failed , Error description : "
								+ e.getMessage());
			} catch (Exception t) {
				ThrowException.Report(TYPE.EXCEPTION, t.getMessage());
			}
		}
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.VerifyText(Web.getDriver().getTitle(), "PL", false));
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String elementName){
		if(elementName.equalsIgnoreCase("WELCOME TO PLAN EXPRESS"))
			return this.welcomeToPlanExpress;
		return null;
		
	}
	
	private void clickOnImplementationMenu(){
		CommonLib.FrameSwitchONandOFF(false);
		if(Web.isWebElementDisplayed(implementationMenu, true)){
			Web.actionsClickOnElement(implementationMenu);
		}
	}
	private void clickOnPlanExpressSubMenu() throws InterruptedException{
		this.clickOnImplementationMenu();
		Thread.sleep(3000);
		/*Web.getDriver().switchTo().activeElement();
		act = new Actions(Web.getDriver());
		act.moveByOffset(getCoordinates()[0], getCoordinates()[1]).doubleClick(planExpressSubMenu);*/
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('mouseover',"
				+ "true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject)"
				+ "{ arguments[0].fireEvent('onmouseover');}";

				String onClickScript = "if(document.createEvent)"
						+ "{var evObj = document.createEvent('MouseEvents');"
						+ "evObj.initEvent('click',true, false); "
						+ "arguments[0].dispatchEvent(evObj);} "
						+ "else if(document.createEventObject)"
						+ "{ arguments[0].fireEvent('onclick');}";
				jse = (JavascriptExecutor)Web.getDriver();
				jse.executeScript(mouseOverScript, planExpressSubMenu);
				jse.executeScript(onClickScript, planExpressSubMenu);
		/*WebDriverWait wait = new WebDriverWait(Web.getDriver(),30);
		wait.until(ExpectedConditions.elementToBeClickable(planExpressSubMenu));
		//Thread.sleep(4000);
		Web.jsClick(planExpressSubMenu);*/
	}
	
	public boolean isPlanExpressPage() throws InterruptedException{
		this.clickOnPlanExpressSubMenu();
		CommonLib.FrameSwitchONandOFF(true, landingPageFrame);
		if(Web.isWebElementDisplayed(welcomeToPlanExpress, true))
			return true;
		return false;
	}
	
	public int[] getCoordinates(){
		Point pePoints = planExpressSubMenu.getLocation();
		int xCord = pePoints.getX();
		int yCord = pePoints.getY();
		int[] cords = {xCord,yCord};
		return cords;
	}

}
