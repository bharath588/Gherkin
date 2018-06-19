package pscBDD.planPage;

import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenBy;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_lib.Web;
import bdd_core.framework.Globals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import bdd_reporter.Reporter;
import bdd_annotations.FindBy;

import com.aventstack.extentreports.Status;


public class InvestmentsPerformancePage extends LoadableComponent<InvestmentsPerformancePage> {
	LoadableComponent<?> parent;
	@FindBy(how=How.XPATH,using="//*[@id='newMenu']//a[text()='Plan']")
	private WebElement tabPlanMenu;
	@FindBy(how=How.XPATH,using="//*[@class='breadcrumb']/i")
	private WebElement investmentsBreadcrumb;
	@FindBy(how=How.XPATH,using="//*[@id='tabForm:navPlan']//div[@class='breadcrumb']/i")
	private WebElement investmentsBreadcrumb2;
	//@FindBy(how=How.XPATH,using="//a[contains(text(),'Investments & Performance') or contains(text(),'Investments & Results')]")
	@FindBy(how=How.XPATH,using="//a[contains(text(),'Investments')]")
	private WebElement investmentsLinkUnderPlanTab;
	
	@FindBy(how=How.ID,using="frameb")
	private WebElement breadcrumbFrame;
	
	@FindBy(how=How.ID,using="fixedInvestmentRateTab")
	private WebElement fixedInvestmentRatesTab;
	@FindBy(how=How.XPATH,using="//span[contains(text(),'Interest Rate *%') or contains(text(),'Interest Rate')]")
	private WebElement intrestRateLable;


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
			Web.clickOnElement(investmentsLinkUnderPlanTab);
			Web.nextGenDriver.waitForAngular();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	@Override
	protected void isLoaded() throws Error {
		Web.waitForElement(investmentsBreadcrumb);
		Web.actionsClickOnElement(investmentsBreadcrumb);
		String text = investmentsBreadcrumb.getText();
		if (text.equals(""))
			text = investmentsBreadcrumb2.getText();		
		Assert.assertTrue(text.trim().equalsIgnoreCase("Investments & Performance") ||text.trim().equalsIgnoreCase("Investments & Results") );

		
	}
	public InvestmentsPerformancePage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver());
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	public boolean isCorrectTabName(String tabName) {
		try {
			Web.FrameSwitchONandOFF(false);
			Web.FrameSwitchONandOFF(true, breadcrumbFrame);
			if (Web.isWebElementDisplayed(fixedInvestmentRatesTab, true))
				if (fixedInvestmentRatesTab.getText().trim()
						.equals(tabName.trim()))
					return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean isCorrectIntrestRateLableName(String intrestRate) {
		try {
			Web.FrameSwitchONandOFF(false);
			Web.FrameSwitchONandOFF(true, breadcrumbFrame);
			if (Web.isWebElementDisplayed(fixedInvestmentRatesTab, true)) {
				Web.clickOnElement(fixedInvestmentRatesTab);
				Web.nextGenDriver.waitForAngular();

				if (Web.isWebElementDisplayed(intrestRateLable, true)) {
					if (LoginPage.accucode.contains("InstMetLife")
							&& intrestRateLable.getText().trim()
									.equals(intrestRate.trim()))
						return true;
					if (intrestRateLable.getText().trim()
							.equals(intrestRate.trim()))
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	

}
