package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;

public class DisbursementInfoOrHistory extends LoadableComponent<DisbursementInfoOrHistory> {
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
		
	}

	@Override
	protected void load() {
		
	}	
	
}
