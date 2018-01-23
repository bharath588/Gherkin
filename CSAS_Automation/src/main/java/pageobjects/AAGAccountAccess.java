package pageobjects;

import java.util.List;

import lib.Reporter;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class AAGAccountAccess extends LoadableComponent<LoanInfo> {
	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	//@FindBy(xpath = "//*[@id='oCMenu_25129'][contains(text() , 'Loan Info')]")
	@FindBy(xpath = ".//*[contains(@id,'oCMenu')][contains(text() , 'AAG Account Access')]")
	private WebElement MenuAAGAccountAccess;

	@FindBy(id = "page")
	private WebElement AAGAccountAccessPageTitle;
	
	@FindBy(id = "header-logo")
	private WebElement FinancialEngines;
	

	LoadableComponent<?> parent;

	public AAGAccountAccess() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(AAGAccountAccessPageTitle));
	}

	@Override
	protected void load() {
		 this.parent = new ParticipantHome().get();
		Web.mouseHover(MenuPPTInfo);
		if (Web.isWebElementDisplayed(MenuAAGAccountAccess, true)) {
			Web.clickOnElement(MenuAAGAccountAccess);
			if (Web.isWebElementDisplayed(AAGAccountAccessPageTitle, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if AAGAccount Access page displayed or not",
						"Loan info page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if AAGAccount Access page displayed or not",
						"Loan info page didn't disply successfully", true);
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan info Link on Participant Info tab displayed or not",
					"Loan info Link on Participant Info tab didn't display successfully",
					true);
		}
	}

}
