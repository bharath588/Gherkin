package org.bdd.psc.pageobjects;

import gwgwebdriver.How;


import gwgwebdriver.NextGenBy;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lib.CommonLib;
import lib.Web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import reporter.Reporter;
import annotations.FindBy;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class PlanProvisionsPage extends LoadableComponent<PlanProvisionsPage> {
	
	LoadableComponent<?> parent;
	CommonLib commonLib;
	
	@FindBy(how=How.XPATH,using="//*[@id='newMenu']//a[text()='Plan']")
	private WebElement tabPlanMenu;
	@FindBy(how=How.XPATH,using="//*[@class='dropdown-submenu navPlanTreeLevel1']//a[text()='Overview']")
	private WebElement overviewLinkUnderPlanMenu;
	@FindBy(how=How.LINK_TEXT,using="Plan provisions")
	private WebElement planProvisionsLinkUnderPlanMenu;
	@FindBy(how=How.XPATH,using="//*[@class='breadcrumb']/i")
	private WebElement planProvisionsBreadcrumb;
	@FindBy(how=How.XPATH,using="//*[@id='tabForm:navPlan']//div[@class='breadcrumb']/i")
	private WebElement planProvisionsBreadcrumb2;
	
	
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
		Web.actionsClickOnElement(planProvisionsBreadcrumb);
		String text = planProvisionsBreadcrumb.getText();
		if (text.equals(""))
			text = planProvisionsBreadcrumb2.getText();
		Assert.assertTrue(text.trim().equals("Plan provisions"));
	}
	
	public WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Breadcrumb")) {
			return this.planProvisionsBreadcrumb;
		}
		return null;
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

}
