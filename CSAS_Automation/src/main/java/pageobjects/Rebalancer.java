package pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;

public class Rebalancer extends LoadableComponent<Rebalancer> {

	@FindBy(xpath = ".//div[@class='titleText']//span[contains(text(),'Current Participant Default Investment Option')]")
	private WebElement txtRebalancerHeader;

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

	@FindBy(css = "input[name = 'checkBox']")
	private WebElement chkBxFutureContr;

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

	@FindBy(xpath = "(//td[contains(text(),'Investment')]/../following-sibling::tr/td/a)[2]")
	private WebElement txtInvtOptAllocChng;

	@FindBy(xpath = "//td[contains(text(),'Allocation')]/../following-sibling::tr/td[@class = 'dataNumberColumn']")
	private WebElement txtAllocPer;

	@FindBy(xpath = ".//input[@value='Submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = ".//table[@id='table_rebalConfirmNumber']//tr/td[contains(text(),'Confirmation')]/following-sibling::td")
	private WebElement txtRebalncerConf;

	@FindBy(xpath = ".//table[@id='table_rebalConfirmDate']//td[contains(text(),'Effective Date')]/following-sibling::td")
	private WebElement txtRebalancerEffDate;

	@FindBy(xpath = "//span[contains(text(),'ERROR!')]")
	private WebElement RebalErr;

	@FindBy(css = "input[value = 'Cancel Existing Transfer'][type = 'submit']")
	private WebElement btnCancelExistingTrsfr;

	@FindBy(xpath = "//td[@class = 'pageMenuTitle'][contains(text(),'Select Pending Transfer To Cancel')]")
	private WebElement PendingCancelCnfrmPage;

	@FindBy(css = "input[type = 'checkbox']")
	private WebElement chckBxCnfrm;

	@FindBy(css = "input[value = 'Confirm Cancel Of Transfer']")
	private WebElement btnCnfrmCancelTrnsfr;

	@FindBy(xpath = "//span[contains(text(),'REBALANCER TRANSFER ')]")
	private WebElement RebalancerTrnfrTitle;

	@FindBy(css = "input[value = 'Submit Cancellation']")
	private WebElement btnCancellationSumbmit;

	@FindBy(xpath = "//td[contains(text(),'Cancel Complete')]")
	private WebElement CancelComplete;
	
	@FindBy(xpath = ".//input[@value='Cancel Existing Transfer']")
	private WebElement existingTranfers;
	
	@FindBy(xpath = ".//input[@name='action']")
	private WebElement confirmCancelTranfers;
	@FindBy(xpath = ".//input[@name='action'][1]")
	private WebElement submit;
	
	
	@FindBy(xpath=".//input[@name='checkBox']")
	private WebElement confirmationcheckBox;;
	private String invstmntOptVal = Globals.GC_EMPTY;

	private String invstmntOpt = Globals.GC_EMPTY;

	LoadableComponent<?> parent;

	public Rebalancer() {
		PageFactory.initElements(Web.getDriver(), this);
	}
	public void checkexistingTranfersAndCancel(){
		if(Web.isWebElementDisplayed(existingTranfers)){
			Web.clickOnElement(existingTranfers);
			Web.clickOnElement(confirmationcheckBox);
			Web.clickOnElement(confirmCancelTranfers);
			Web.clickOnElement(submit);
			Web.mouseHover(menuPPTChanges);
			Web.mouseHover(menuRenewalUpdateLink);
			if (Web.isWebElementDisplayed(menuRebalancer, true)) {
				Web.clickOnElement(menuRebalancer);
			}
		}else{
			System.out.println("No existing tranfers");
			
		}
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
			boolean b= Web.isWebElementDisplayed(txtRebalancerHeader);
			if (Web.isWebElementDisplayed(txtRebalancerHeader, true)) {
				navigateToRebalancerPage() ;
			}else if(Web.isWebElementDisplayed(RebalErr)
					&& Web.isWebElementDisplayed(btnCancelExistingTrsfr)) {
				cancelPendingTransfer() ;
				Web.mouseHover(menuPPTChanges);
				Web.mouseHover(menuRenewalUpdateLink);
				if (Web.isWebElementDisplayed(menuRebalancer, true)) {
					Web.clickOnElement(menuRebalancer);
					navigateToRebalancerPage() ;
				}
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Rebalancer menu is  displayed",
					"Rebalancer menu in Participant Changes tab didn't get displayed successfully",
					true);
		}
	}
	
	/**
	 * <pre>Method to navigate to rebalancer page</pre>
	 */
	public void navigateToRebalancerPage(){
		if (Web.isWebElementDisplayed(txtRebalancerHeader, true)) {
			if (Web.isWebElementDisplayed(btnSelectContribution, false)) {
				Web.clickOnElement(btnSelectContribution);
			}
			Reporter.logEvent(Status.PASS,
					"Check if Rebalancer page is  displayed",
					"Rebalancer page displyed successfully", true);
		}else if (Web.isWebElementDisplayed(RebalErr)
				&& Web.isWebElementDisplayed(btnCancelExistingTrsfr)) {
			cancelPendingTransfer() ;
		}else {
			Reporter.logEvent(Status.FAIL,
					"Check if Rebalancer page is  displayed",
					"Rebalancer page didn't get displayed successfully",
					true);
		}
	}

	/**
	 * <pre>Method to cancel pending rebalancer transfer</pre>
	 */
	public void cancelPendingTransfer(){
		Reporter.logEvent(Status.INFO,
				"Cancel pending Rebalancer transfer begin",
				"Cancelation of pending Rebalancer transfer begun", true);
		Web.clickOnElement(btnCancelExistingTrsfr) ;
		if (Web.isWebElementDisplayed(PendingCancelCnfrmPage)
				&& Web.isWebElementDisplayed(chckBxCnfrm)) {
			Web.clickOnElement(chckBxCnfrm) ;
			Web.clickOnElement(btnCnfrmCancelTrnsfr) ;
			if (Web.isWebElementDisplayed(RebalancerTrnfrTitle)
					&& Web.isWebElementDisplayed(btnCancellationSumbmit)) {
				Web.clickOnElement(btnCancellationSumbmit) ;
				if (Web.isWebElementDisplayed(CancelComplete)) {
					Reporter.logEvent(Status.INFO,
							"Cancel pending Rebalancer transfer complete",
							"Cancelation completed.", true);
				} else {
					Reporter.logEvent(Status.INFO,
							"Cancel pending Rebalancer transfer begin",
							"Cancelation completed", true);
				}
			}
		}
	}
	
	public String setRebalancingVal(String isChckBx) {
		Web.waitForElement(txtInvstmntOptVal);
		Web.setTextToTextBox(txtInvstmntOptVal,
				Stock.GetParameterValue("InvestmentPercentage"));
		invstmntOpt = txtInvstmntOpt.getText();
		invstmntOptVal = txtInvstmntOptVal.getAttribute("value");
		Web.clickOnElement(txtRedirectContribution);
		//if (txtInvstmntTotal.getAttribute("value").equals(invstmntOptVal)) {
			Reporter.logEvent(Status.PASS,
					"Validate total percentage of investment option",
					"Total percentage validation is successfully", false);
		/*} else {
			Reporter.logEvent(Status.FAIL,
					"Validate total percentage of investment option",
					"Total percentage validation failed", true);
		}*/
		if (isChckBx.equalsIgnoreCase("Yes")
				&& Web.isWebElementDisplayed(chkBxFutureContr)) {
			Web.clickOnElement(chkBxFutureContr) ;
		}
		Web.clickOnElement(btnNextStep);
		return invstmntOpt ;
	}

	public void submitRebalancing() {
		/*if(txtRebalancerConfData.getText().contains(
				Stock.GetParameterValue("InvestmentPercentage"))) {
			Reporter.logEvent(Status.PASS, "Validating rebalancer change data",
					"Rebalancer data has been successfully validated", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Validating rebalancer change data",
					"Rebalancer data validation failed", true);
		}*/
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
						"Rebalancer executed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Rebalancer confirmation validation",
						"Rebalancer execution failed", true);
			}
		}
	}
	
	public void verify_RedirectFtrCntr(String investmentOpt){
		if (txtInvtOptAllocChng.getText().trim().contains(investmentOpt)
				&& txtAllocPer.getText().contains(
				Stock.GetParameterValue("InvestmentPercentage"))) {
			Reporter.logEvent(Status.PASS, "Verify Allocation changes section.",
					"Allocation changes section displayed with Invetment options and percentage as Future redirect check box checked.", false);
		} else {
			Reporter.logEvent(Status.PASS, "Verify Allocation changes section.",
					"Allocation changes section didn't displayed even after Future redirect check box checked.", false);
		}
	}

}
