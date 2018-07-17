package pscBDD.planPage;

import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenBy;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_annotations.FindBy;
import bdd_lib.Web;
import bdd_core.framework.Globals;

import java.util.concurrent.TimeUnit;

import bdd_lib.CommonLib;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;

import org.testng.Assert;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import bdd_reporter.Reporter;




import com.aventstack.extentreports.Status;



public class PlanProvisionsPage extends LoadableComponent<PlanProvisionsPage> {
	
	LoadableComponent<?> parent;
	CommonLib commonLib;
	@FindBy(how=How.CLASS_NAME,using="planheading")
	private WebElement planAnalytics;
	
	@FindBy(how=How.XPATH,using="//*[@id='newMenu']//a[text()='Plan']")
	private WebElement tabPlanMenu;
	@FindBy(how=How.XPATH,using="//*[@class='dropdown-submenu navPlanTreeLevel1']//a[text()='Overview']")
	private WebElement overviewLinkUnderPlanMenu;
	@FindBy(how=How.LINK_TEXT,using="Plan provisions")
	private WebElement planProvisionsLinkUnderPlanMenu;
	@FindBy(how=How.XPATH,using="//*[@class='breadcrumb']/i")
	private WebElement breadcrumb1;
	@FindBy(how=How.XPATH,using="//*[@id='tabForm:navPlan']//div[@class='breadcrumb']/i")
	private WebElement breadcrumb2;
	
	@FindBy(how=How.XPATH,using="//label[contains(text(),'General information')]")
	private WebElement labelGeneralInformation;
	
	@FindBy(how=How.ID,using="frameb")
	private WebElement planFrame;
	
	@FindBy(how=How.LINK_TEXT,using="Automatic enrollment")
	private WebElement automaticEnrollmentLink;
	@FindBy(how=How.LINK_TEXT,using="Deferrals")
	private WebElement deferralsLink;
	@FindBy(how=How.LINK_TEXT,using="Plan match")
	private WebElement planMatchLink;
	@FindBy(how=How.LINK_TEXT,using="Eligibility")
	private WebElement eligibilityLink;
	@FindBy(how=How.LINK_TEXT,using="Loan information")
	private WebElement loanInformationLink;
	@FindBy(how=How.XPATH,using=".//div[contains(@class,'page-title') or contains(@class,'headcontainer') or contains(@class,'headcontainer ng-scope')]")
	private WebElement buttonPageText;
	@FindBy(how=How.LINK_TEXT,using="Return to plan provisions")
	private WebElement returnToPlanProvisionsLink;
	@FindBy(how=How.XPATH,using="(//table[@class='blueBox ui-corner-all']//tr/td[2])[5]//tr[1]//span")
	private WebElement vestingServiceLevel;
	
	//@FindBy(xpath = ".//div[contains(@class,'page-title') or contains(@class,'headcontainer') or contains(@class,'headcontainer ng-scope')]")
	
	public PlanProvisionsPage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}	
	@Override
	protected void load() {
		try {
			new HomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
			Reporter.logEvent(Status.PASS,
					"Check if the user has landed on homepage",
					"The user has landed on homepage", true);
			Web.actionsClickOnElement(tabPlanMenu);
			Web.clickOnElement(overviewLinkUnderPlanMenu);
			Web.clickOnElement(planProvisionsLinkUnderPlanMenu);
			Web.nextGenDriver.waitForAngular();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void isLoaded() throws Error {
		// if (Web.isWebElementDisplayed(planProvisionsBreadcrumb, true))
		//Web.actionsClickOnElement(breadcrumb1);
		Web.waitForElement(breadcrumb1);
		String text = breadcrumb1.getText();
		if (text.equals(""))
			text = breadcrumb2.getText();
		Assert.assertTrue(text.trim().equals("Plan provisions"));
		
	}
	
	public WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Breadcrumb1")) {
			return this.breadcrumb1;
		}
		if (fieldName.trim().equalsIgnoreCase("Breadcrumb2")) {
			return this.breadcrumb2;
		}
		if (fieldName.trim().equalsIgnoreCase("Plan Menu")) {
			return this.tabPlanMenu;
		}
		if (fieldName.trim().equalsIgnoreCase("Loan Information")) {
			return this.loanInformationLink;
		}
		if (fieldName.trim().equalsIgnoreCase("Plan Frame")) {
			return this.planFrame;
		}
		
		return null;
	}

	public void clickOnLoanButton() {
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, planFrame);
		if (Web.isWebElementDisplayed(loanInformationLink, true)) {
			Web.clickOnElement(loanInformationLink);
			Web.nextGenDriver.waitForAngular();

		}
	}
	public boolean isPlanAnalyticsSectionDiplays(){
		Web.nextGenDriver.waitForAngular();
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, planFrame);
		if (Web.isWebElementDisplayed(planAnalytics, true))
			return true;
		return false;
		
	}

	public String gettingButtonPageName(String buttonName){
		
		
		Web.getDriver().switchTo().frame(planFrame);
		WebElement element=null;
		String pageNameText=null;
		if(buttonName.trim().equalsIgnoreCase("Automatic enrollment"))
			element=automaticEnrollmentLink;
		else if(buttonName.trim().equalsIgnoreCase("Deferrals"))
			element=deferralsLink;
		else if(buttonName.trim().equalsIgnoreCase("Plan match"))
			element=planMatchLink;
		else if(buttonName.trim().equalsIgnoreCase("Eligibility"))
			element=eligibilityLink;
		else if(buttonName.trim().equalsIgnoreCase("Loan information"))
			element=loanInformationLink;
		else
		{
			Reporter.logEvent(Status.INFO,
					"Check if the user has provided correct button name",
					"The user hasn't provide correct button name", true);
			return null;
		}
		try{
			Web.nextGenDriver.waitForAngular();
			if(Web.isWebElementDisplayed(element)){
				Web.clickOnElement(element);
				Web.nextGenDriver.waitForAngular();
				if(Web.isWebElementDisplayed(buttonPageText, true))
					pageNameText=buttonPageText.getText().trim();
				Web.clickOnElement(returnToPlanProvisionsLink);
				Web.nextGenDriver.waitForAngular();
			}			
		}
		catch(Exception e){
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, buttonName+" button displayes in plan provision page", buttonName+" button isn't displays in plan provision page",false);
		}		
		Web.getDriver().switchTo().defaultContent();
		return pageNameText;
	}
	
	public boolean verifyVestingServiceLevel(String vestingServiceLevel) {
		Web.waitForElement(planFrame);
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, planFrame);
		if (Web.isWebElementDisplayed(this.vestingServiceLevel, true)) {
			if (this.vestingServiceLevel.getText().trim()
					.equalsIgnoreCase(vestingServiceLevel.trim())) {
				Web.FrameSwitchONandOFF(false);
				return true;
			}

		}
		Web.FrameSwitchONandOFF(false);
		return false;
	}
	public boolean isPlanProvisionPage(){
		Web.waitForElement(planFrame);
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, planFrame);
		if (Web.isWebElementDisplayed(labelGeneralInformation, true)) {
			return true;
		}
		return false;
		
	}


}
