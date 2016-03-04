package pageobjects;

import lib.Reporter;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class DisbursementInfoOrHistory extends LoadableComponent<DisbursementInfoOrHistory> {
	
	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;
	
	@FindBy(xpath = "//*[@id='oCMenu_329'][contains(text() , 'Loan Info')]")
	private WebElement MenuLoanInfo;
	
	@FindBy(css = "td.pageMenuTitle")
	private WebElement LoanInfoPageTitle;
	
	@FindBy(css="table[id='DisbSummary']") 
	private WebElement tblDisbursementSummary;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu0']") 
	private WebElement tabDisbursementType;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu1']") 
	private WebElement tabProcessingDet;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu2']") 
	private WebElement tabPaymentHist;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu3']") 
	private WebElement tabWithholding;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu4']") 
	private WebElement tabInvestmentSales;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu5']") 
	private WebElement tabCostBasis;
	
	@FindBy(css="div[id='tabMenuDiv'] span[id='tabMenu6']") 
	private WebElement tabVesting;
	
	@FindBy(xpath=".//*[@id='tabPane2']/table/tbody/tr/td/div/div[2]") 
	private WebElement divPaymentHistoryData;
	
	@FindBy(xpath=".//*[@id='tabPane1']/table/tbody/tr/td/div/div/div[2]") 
	private WebElement divProcessingDetailsData;
	
	LoadableComponent<?> parent;
	public DisbursementInfoOrHistory() {
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.webdriver.getTitle()
				.contains("Disbursement Info/History"));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		verify_DisbursementInfoPage() ;
	}

	/**
	 * <pre>
	 * Method to Loan info page
	 * </pre>
	 * @author RANJAN
	 */
	public void verify_DisbursementInfoPage() {

		if (Web.isWebElementDisplayed(MenuPPTInfo, true)) {
			Web.mouseHover(MenuPPTInfo);
			if (Web.isWebElementDisplayed(MenuLoanInfo, true)) {
				Web.clickOnElement(MenuLoanInfo);
				if (Web.isWebElementDisplayed(LoanInfoPageTitle, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Loan info page displayed or not",
							"Loan info page displyed successfully", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Loan info page displayed or not",
							"Loan info page didn't disply successfully", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Loan info Link on Participant Info tab displayed or not",
						"Loan info Link on Participant Info tab didn't disply successfully",
						true);
			}
		}
	}
	
}
