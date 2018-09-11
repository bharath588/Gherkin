/**
 * 
 */
package pscBDD.partnerlinkHomePage;

import java.lang.reflect.Method;





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

import com.aventstack.extentreports.Status;

import bdd_lib.Web;
import bdd_reporter.Reporter;
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
	
	@FindBy(xpath="//a[contains(text(),'Partner')]")
	private WebElement partnerLinkTab;
	
	@FindBy(xpath=".//*[contains(@id,'content')]/h2")
	private WebElement welcomeToPartnerLink;
	@FindBy(id="framea")
	private WebElement landingPageFrame;
	@FindBy(xpath=".//*[contains(@id,'menu')]//li/a//span[text()='Implementation']")
	private WebElement implementationMenuPL;
	@FindBy(xpath="//a[contains(text(),'Partner')]/following-sibling::ul//*[text()='Implementation']")
	private WebElement implementationMenu;
	@FindBy(xpath="//a/span/span[text()='Plan Express']")
	private WebElement planExpressSubMenuPL;
	@FindBy(xpath="//a[contains(text(),'PlanExpress')]")
	private WebElement planExpressSubMenu;
	@FindBy(xpath="//div[@class='pageTitle' and contains(text(),'Welcome to PlanExpress')]")
	private WebElement welcomeToPlanExpress;
	
	@FindBy(xpath="//div[@id='logo']/img")
	private WebElement metLifeLogo;
	
	@FindBy(xpath="//ul[@id='newMenu']/li[1]/a/following-sibling::ul/li[6]/a[text()='PlanVisualizer']")
	private WebElement lnkPlanVisualizer;
	
	@FindBy(xpath="//div[@class='breadcrumb']/i/ancestor::div[contains(text(),'Partner')]")
	private WebElement breadCrumb;
	
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
					//CommonLib.FrameSwitchONandOFF(true, landingPageFrame);
					Web.FrameSwitchONandOFF(true, landingPageFrame);
					Web.waitForElement(welcomeToPartnerLink);
					//CommonLib.FrameSwitchONandOFF(false, landingPageFrame);
					Web.FrameSwitchONandOFF(false);

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
		//CommonLib.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(false);
		if(Web.isWebElementDisplayed(partnerLinkTab, true))
			Web.actionsClickOnElement(partnerLinkTab);
		if(Web.isWebElementDisplayed(implementationMenu, true)){
			Web.actionsClickOnElement(implementationMenu);
		}		
		/*if(Web.isWebElementDisplayed(implementationMenuPL, true)){
			Web.actionsClickOnElement(implementationMenuPL);
		}*/
	}
	private void clickOnPlanExpressSubMenu() throws InterruptedException{
		this.clickOnImplementationMenu();
		Thread.sleep(3000);
		/*Web.getDriver().switchTo().activeElement();
		act = new Actions(Web.getDriver());
		act.moveByOffset(getCoordinates()[0], getCoordinates()[1]).doubleClick(planExpressSubMenu);*/
		/*String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
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
				jse.executeScript(onClickScript, planExpressSubMenu);*/
		if(Web.isWebElementDisplayed(planExpressSubMenu, true))
			Web.actionsClickOnElement(planExpressSubMenu);
		Thread.sleep(3000);	
		/*if(Web.isWebElementDisplayed(planExpressSubMenuPL, true))
			Web.actionsClickOnElement(planExpressSubMenuPL);
		Thread.sleep(3000);*/
		/*WebDriverWait wait = new WebDriverWait(Web.getDriver(),30);
		wait.until(ExpectedConditions.elementToBeClickable(planExpressSubMenu));
		//Thread.sleep(4000);
		Web.jsClick(planExpressSubMenu);*/
	}
	
	public void clickOnPLPlanExpressSubMenu() {
		try {
			Web.FrameSwitchONandOFF(false);
			if(Web.isWebElementDisplayed(implementationMenuPL, true)){
				Web.actionsClickOnElement(implementationMenuPL);
			}
			if(Web.isWebElementDisplayed(planExpressSubMenuPL, true))
				Web.actionsClickOnElement(planExpressSubMenuPL);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean isPlanExpressPage() throws InterruptedException{
		this.clickOnPlanExpressSubMenu();
		//CommonLib.FrameSwitchONandOFF(true, landingPageFrame);
		Web.FrameSwitchONandOFF(true, landingPageFrame);
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
	
	public boolean isPLPlanExpressPage() throws InterruptedException{
		this.clickOnPLPlanExpressSubMenu();
		Web.FrameSwitchONandOFF(true, landingPageFrame);
		if(Web.isWebElementDisplayed(welcomeToPlanExpress, true))
			return true;
		return false;
	}
	
	public boolean isMetLifeLogo(){
		if(metLifeLogo.isDisplayed())
			return true;
		return false;
	}
	

	public void clickOnPlanVisualizer(){
		if(Web.isWebElementDisplayed(partnerLinkTab, true))
			Web.actionsClickOnElement(partnerLinkTab);
		Reporter.logEvent(Status.INFO, "Clicked on PartnerLink Tab",
				"Clicked on PartnerLink Tab", true);
		if(Web.isWebElementDisplayed(lnkPlanVisualizer, true)){
			Web.actionsClickOnElement(lnkPlanVisualizer);
			Reporter.logEvent(Status.INFO, "Select Plan Visualizer from the list",
					"Plan Visualizer menu option selected", true);
		}	
	}
		
	public boolean isBreadcrumb(String expectedText){
		String actualText;
		actualText= breadCrumb.getText().trim();
		if (expectedText.trim().contains(actualText.split("/")[0]))
			return true;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
