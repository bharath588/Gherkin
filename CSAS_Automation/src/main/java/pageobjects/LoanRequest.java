package pageobjects;

import lib.Reporter;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class LoanRequest extends LoadableComponent<LoanRequest> {
	
	LoadableComponent<?> parent;

	public LoanRequest() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	
	@FindBy(xpath = "//*[@class='pageMenuTitle' and text()='Loans']")
	private WebElement loanRequest;

	@FindBy(xpath = "//*[text()='Participant Changes']")
	private WebElement menuPPTChanges;	
	
	@FindBy(xpath = "//*[text()='Loan Request']")
	private WebElement menuLoanQuote;
	

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(loanRequest));
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent = new ParticipantHome().get();
		Web.mouseHover(menuPPTChanges);
		if (Web.isWebElementDisplayed(menuLoanQuote)) {
			Web.clickOnElement(menuLoanQuote);
			if (Web.isWebElementDisplayed(loanRequest, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Loan Request page displayed or not",
						"Loan Quote page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Loan Request page displayed or not",
						"Loan Quote didn't get displayed successfully", true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan Request Link on Participant Changes tab displayed or not",
					" Loan Request Link on Participant Changes tab didn't get displayed successfully",
					true);
		}	
		
	}
	

}
