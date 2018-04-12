package pageobjects;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class AAGAccountAccess extends LoadableComponent<AAGAccountAccess> {
	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	@FindBy(xpath = ".//*[contains(@id,'oCMenu')][contains(text() , 'AAG Account Access')]")
	private WebElement MenuAAGAccountAccess;

	@FindBy(id = "page")
	private WebElement FinancialServicesPageTitle;
	
	@FindBy(id = "Body")
	private WebElement AdvisoryServicesPageTitle;
	
	@FindBy(id = "header-logo")
	private WebElement FinancialEngines;
	@FindBy(xpath = ".//td[@class,'ibbotson']")
	private WebElement advisoryServices;
	
	@FindBy(css = "div#oCMenu_319")
	private WebElement menuSearch;
	
	String parentWindow;
	
	
	
	LoadableComponent<?> parent;

	public AAGAccountAccess() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		String expectedPage = Stock.GetParameterValue("Page");
		if(expectedPage.equalsIgnoreCase("Financial Services")){
		Assert.assertTrue(Web.isWebElementDisplayed(FinancialServicesPageTitle));
		}else if(expectedPage.equalsIgnoreCase("Advisory Services")){
			Assert.assertTrue(Web.isWebElementDisplayed(AdvisoryServicesPageTitle));
		}
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		 parentWindow =  Web.getDriver().getWindowHandle();
		Web.mouseHover(MenuPPTInfo);
		if (Web.isWebElementDisplayed(MenuAAGAccountAccess, true)) {
			Web.clickOnElement(MenuAAGAccountAccess);
			switchToWindow(parentWindow);
			if (Web.isWebElementDisplayed(FinancialServicesPageTitle, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if AAGAccount Access page displayed or not",
						"AAGAccount Access page displyed successfully", true);
			}else if(Web.isWebElementDisplayed(AdvisoryServicesPageTitle, true)){
				Reporter.logEvent(Status.PASS,
						"Check if AAGAccount Access page displayed or not",
						"AAGAccount Access page successfully", true);
				
			}
				else {
				Reporter.logEvent(Status.FAIL,
						"Check if AAGAccount Access page displayed or not",
						" Expected AAGAccount Access page didn't disply successfully", true);
			}

		}
			else {
		
			Reporter.logEvent(
					Status.FAIL,
					"Expected AAGAccount Access page tab displayed or not",
					"Expected AAGAccount Access page tab didn't display successfully",
					true);
		}
	}
	
	public void switchToWindow(String parentWindow){
	
    for(String winHandle : Web.getDriver().getWindowHandles()){

            if(!winHandle.equals(parentWindow))

                    Web.getDriver().switchTo().window(winHandle);

    }
	}

	
	public void verifyFinancialEnginesTitle(){
		if (Web.isWebElementDisplayed(FinancialEngines, true)) {
			Reporter.logEvent(Status.PASS,
					"Check if FinancialEngines page displayed or not",
					"FinancialEngines page displyed successfully", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if FinancialEngines page displayed or not",
					"FinancialEngines page didn't disply successfully", true);
		}
		Web.getDriver().switchTo().window(parentWindow);
		Web.clickOnElement(menuSearch);
	}
	public void verifyAdvisoryServicesTitle(){
		if (Web.isWebElementDisplayed(AdvisoryServicesPageTitle, true)) {
			Reporter.logEvent(Status.PASS,
					"Check if AdvisoryServices page displayed or not",
					"AdvisoryServices page displyed successfully", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if AdvisoryServices page displayed or not",
					"AdvisoryServices page didn't disply successfully", true);
		}
	}
	
}
