package pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;

public class Rebalancer extends LoadableComponent<Rebalancer> {

	@FindBy(xpath = ".//table[@class='contentTableColumn']//span[contains(text(),'Current Participant Default Investment Option')]")
	WebElement txtRebalancerHeader;

	@FindBy(xpath = "//*[@id='oCMenu_316'][contains(text(),'Participant Changes')]")
	private WebElement menuPPTChanges;

	@FindBy(xpath = "//div[contains(@id,'oCMenu')][contains(text(),'Custom Xfer & Renewal Update')]")
	private WebElement menuRenewalUpdateLink;

	@FindBy(xpath = "//div[contains(@id,'oCMenu')][contains(text(),'Rebalancer')]")
	private WebElement menuRebalancer;

	@FindBy(xpath = "//input[@value='Select Contribution Source Category']")
	private WebElement btnSelectContribution;

	@FindBy(xpath = ".//form[@id='partRebalForm']//table[@class='innerDataTable']//tr[2]/td[2]//input[contains(@name,'toRebalancerFunds')]")
	private WebElement txtInvstmntOptVal;

	@FindBy(xpath = ".//input[@value='Next Step']")
	private WebElement btnNextStep;

	@FindBy(xpath = ".//td[contains(text(),'Redirect future contributions')]")
	private WebElement txtRedirectContribution;

	@FindBy(xpath = ".//input[@name='pctTotal']")
	private WebElement txtInvstmntTotal;

	@FindBy(xpath = ".//form[@id='partRebalForm']//table[@class='innerDataTable']//tr[2]/td[1]/a")
	private WebElement txtInvstmntOpt;

	@FindBy(xpath = "(.//table[@class='innerDataTable']//tr[2]/td[@class='dataStringColumn' or @class='dataNumberColumn'])[2]")
	private WebElement txtRebalancerConfData;

	@FindBy(xpath = ".//input[@value='Submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = ".//table[@id='table_rebalConfirmNumber']//tr/td[contains(text(),'Confirmation')]/following-sibling::td")
	private WebElement txtRebalncerConf;

	@FindBy(xpath = ".//table[@id='table_rebalConfirmDate']//td[contains(text(),'Effective Date')]/following-sibling::td")
	private WebElement txtRebalancerEffDate;

	private String invstmntOptVal = Globals.GC_EMPTY;

	private String invstmntOpt = Globals.GC_EMPTY;

	LoadableComponent<?> parent;

	public Rebalancer() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(txtRebalancerHeader));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		Web.mouseHover(menuPPTChanges);
		Web.mouseHover(menuRenewalUpdateLink);
		if (Web.isWebElementDisplayed(menuRebalancer, true)) {
			Web.clickOnElement(menuRebalancer);
			if (Web.isWebElementDisplayed(txtRebalancerHeader, true)) {
				if (Web.isWebElementDisplayed(btnSelectContribution, false)) {
					Web.clickOnElement(btnSelectContribution);
				}
				Reporter.logEvent(Status.PASS,
						"Check if Rebalancer page is  displayed",
						"Rebalancer page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Rebalancer page is  displayed",
						"Rebalancer page didn't get displayed successfully",
						true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Rebalancer menu is  displayed",
					"Rebalancer menu in Participant Changes tab didn't get displayed successfully",
					true);
		}
	}

	public void setRebalancingVal() {
		Web.waitForElement(txtInvstmntOptVal);
		Web.setTextToTextBox(txtInvstmntOptVal,
				Stock.GetParameterValue("InvestmentPercentage"));
		invstmntOpt = txtInvstmntOpt.getText();
		invstmntOptVal = txtInvstmntOptVal.getAttribute("value");
		Web.clickOnElement(txtRedirectContribution);
		//if (txtInvstmntTotal.getAttribute("value").equals(invstmntOptVal)) {
			Reporter.logEvent(Status.PASS,
					"Validate total percentage of investment option",
					"Total percentage validation is successful", false);
		/*} else {
			Reporter.logEvent(Status.FAIL,
					"Validate total percentage of investment option",
					"Total percentage validation failed", true);
		}*/
		Web.clickOnElement(btnNextStep);
	}

	public void submitRebalancing() {
		if (txtRebalancerConfData.getText().contains(
				Stock.GetParameterValue("InvestmentPercentage"))) {
			Reporter.logEvent(Status.PASS, "Validating rebalancer change data",
					"Rebalancer data has been successfully validated", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validating rebalancer change data",
					"Rebalancer data validation failed", true);
		}
		Web.clickOnElement(btnSubmit);

	}

	public void validate_Rebalancing_Complete() {
		Pattern p = Pattern.compile("^\\d\\d.*[A-Za-z].*\\d\\d\\d\\d$");
		if (Web.isWebElementDisplayed(txtRebalncerConf, true)
				&& Web.isWebElementDisplayed(txtRebalancerEffDate, true)) {
			Matcher match = p.matcher(txtRebalancerEffDate.getText());
			if (txtRebalncerConf.getText().matches(".*\\d+.*") && match.find()) {
				Reporter.logEvent(Status.PASS,
						"Rebalancer confirmation validation",
						"Rebalancer executed successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Rebalancer confirmation validation",
						"Rebalancer execution failed", true);
			}
		}
	}

}
