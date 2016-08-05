package pageobjects;

import java.util.List;
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

public class FundtoFund extends LoadableComponent<FundtoFund> {

	@FindBy(xpath = ".//table[@id='table_Fund To Fund Transfer - Select Transfer Type']"
				   + "//td[contains(text(),'Fund To Fund Transfer')]")
	private WebElement ftofTransferPgTitle;
	
	@FindBy(xpath = "//*[@id='oCMenu_316'][contains(text(),'Participant Changes')]")
	private WebElement menuPPTChanges;
	
	@FindBy(xpath = "//div[contains(@id,'oCMenu')][contains(text(),'Fund to Fund')]")
	private WebElement menuF2FLink;
	
	@FindBy(xpath = "//div[contains(@id,'oCMenu')][contains(text(),'Cancel Pending Transfer')]")
	private WebElement menuCancelF2FLink;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//td[contains(text(),'Fund To Fund Transfer')]")
	private WebElement selectF2FTransfer;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//td[contains(text(),'Self Directed Account Transfer To')]")
	private WebElement selectSelfTransferTo;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//td[contains(text(),'Self Directed Account Transfer From')]")
	private WebElement selectSelfTransferFrm;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//input[@value='Begin Fund To Fund Transfer']")
	private WebElement btnBeginF2FTransfer;
	
	@FindBy(xpath = "//table[@id='table_activeDefaultFunds']/thead")
	private WebElement tabDefInvstOptnHead;
	
	@FindBy(xpath = "//table[@id='table_activeDefaultFunds']/tbody")
	private WebElement tabDefInvstOptnBody;
	
	@FindBy(xpath = "//table[@class='bradsDataTable']//tr[@id='null'][1]/td[3]/input")
	private WebElement txtTrnsfrFrmDollarAmt;
	
	@FindBy(xpath = "//table[@class='bradsDataTable']//tr[@id='null'][1]/td[5]/input")
	private WebElement txtTrnsfrFrmOutPrcntg;
	
	@FindBy(xpath = "//input[@value='Select Transfer To Options']")
	private WebElement btnSelectTransferTo;
	
	@FindBy(xpath = "//table[@class='bradsDataTable']//tr[@id='null'][1]//td[2]/input")
	private WebElement txtTrnsfrToDollarAmt;
	
	@FindBy(xpath = "//table[@class='bradsDataTable']//tr[@id='null'][1]//td[4]/input")
	private WebElement txtTrnsfrToOutPrcntg;
	
	@FindBy(xpath = "//input[@value='Confirm Transfer']")
	private WebElement btnConfirmTransfer;
	
	@FindBy(xpath = "//table[@id='table_fundList0']//td[contains(text(),'$')]")
	private WebElement txtFndToFndTransferDollarVal;
	
	@FindBy(xpath = "//table[@id='table_fundList0']//td[contains(text(),'%')]")
	private WebElement txtFndToFndTransferPercentageVal;
	
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement btnSubmit;
	
	@FindBy(xpath = "//table[@id='table_activeDefaultFunds']")
	private WebElement tabDefInvstmntOpt;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//td[@class='dataStringColumnTitle'][contains(text(),'Confirmation')]")
	private WebElement fndtofndConfNum;
	
	@FindBy(xpath = "//table[@class='contentTableColumn']//td[@class='dataStringColumnTitle'][contains(text(),'Effective')]")
	private WebElement fndtofndEffDate;
	
	@FindBy(xpath = "//table[@id='table_workLayout']//table[@align='center']//input[@type='checkbox']")
	private List<WebElement> cancelFndTransferCheckBox;
	
	@FindBy(xpath = "//table[@id='table_messageHandlerMessage']//div[contains(text(),'EMPLOYER STOCK')]")
	private WebElement cancelFndTransferPgHeader;
	
	@FindBy(xpath = "//input[@value='Confirm Cancel Of Transfer']")
	private WebElement btnConfCancelTranfer;
	
	@FindBy(xpath = "//input[@value='Submit Cancellation']")
	private WebElement btnSubmitCancelTransfer;
	
	private String defInvstOptHdr = "Rule|Criteria|Investment|Option|Percentage"; 
	
	LoadableComponent<?> parent;
	
	public FundtoFund() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(ftofTransferPgTitle));
	}

	@Override
	protected void load() {		
		this.parent = new ParticipantHome().get();
		Web.mouseHover(menuPPTChanges);
		if (Web.isWebElementDisplayed(menuF2FLink, true)) {
			Web.clickOnElement(menuF2FLink);
			if (Web.isWebElementDisplayed(ftofTransferPgTitle, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Fund to Fund transfer page is displayed",
						"Fund to Fund transfer page displayed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Fund to Fund transfer page is displayed",
						"Fund to Fund transfer page didn't get displayed successfully", true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Fund to Fund transfer menu is  displayed or not",
					"Fund to Fund transfer menu Participant Changes tab didn't get displayed successfully",
					true);
		}
	}
	
	
	public void NavToTrnsfrFundsPg() {
		Web.waitForElement(btnBeginF2FTransfer);
		
		// To select various options in Begin Fund to Fund Transfer page
		switch(Stock.GetParameterValue("SelectTransferType").toLowerCase()){
			case "fundtofund" : selectF2FTransfer.click();break;
			case "selftransferto" : selectSelfTransferTo.click();break;
			case "selftransferfrom" : selectSelfTransferFrm.click();break;
			default : selectF2FTransfer.click();break;
		}		
		btnBeginF2FTransfer.click();		
	}
	
	public void ChkDefInvstOpt(){	
		if(Web.isWebElementDisplayed(tabDefInvstOptnHead,true)){
			if(tabDefInvstOptnHead.getText().replace("\n","|")
			  .replace(" ","|").contentEquals(defInvstOptHdr)
			  && tabDefInvstOptnBody.getText().matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9$&+,.:;=?@#|% ]+$")){
				 Reporter.logEvent(Status.PASS,"Validating partcipant default investment option details"
						       ,"Participant default investment option validated successfully",false);
			}else{
				 Reporter.logEvent(Status.FAIL,"Validating partcipant default investment option details"
					           ,"Participant default investment option validation failed",true);	
			}
		}else{
			Reporter.logEvent(Status.FAIL,"Load Fund to Fund transfer page","Fund to Fund transfer page not loaded",true);
		}		
	
	}

	public void EnterTransferFromFundVal(String intVal) {
		String txtVal = Globals.GC_EMPTY;
		if(!intVal.isEmpty()){txtVal=intVal;}else{txtVal="100";}
		txtTrnsfrFrmDollarAmt.sendKeys(txtVal);
		Reporter.logEvent(Status.INFO,"Enter Transfer-Out Dollar amount",
				          "Dollar amount provided successfully",false);
		btnSelectTransferTo.click();
	}
	
	public void EnterTransferToFundVal(String intVal){
		String txtVal = Globals.GC_EMPTY;
		if(!intVal.isEmpty()){txtVal=intVal;}else{txtVal="100";}
		Web.waitForElement(txtTrnsfrToOutPrcntg);
		txtTrnsfrToOutPrcntg.sendKeys(txtVal);
		Reporter.logEvent(Status.INFO,"Enter Transfer-In Percentage",
				          "Percentage provided successfully",false);
		btnConfirmTransfer.click();
	}
	
	public void ConfirmFundTransfer(){
		Web.waitForElement(txtFndToFndTransferDollarVal);
		if(txtFndToFndTransferDollarVal.getText().contains(Stock.GetParameterValue("TransferFromVal"))
		&& txtFndToFndTransferPercentageVal.getText().contains(Stock.GetParameterValue("TransferToVal"))){
			Reporter.logEvent(Status.PASS,"Validating Fund to Fund Transfer investement values displayed in confirmation page",
					          "Fund to Fund investment values are displayed correctly",true);			
		}else{
			Reporter.logEvent(Status.FAIL,"Validating Fund to Fund Transfer investement values displayed in confirmation page",
							  "Fund to Fund investment values are displayed incorrectly: Expected value $"
			                  +Stock.GetParameterValue("TransferFromVal")+" and %"
						      +Stock.GetParameterValue("TransferToVal"),true);
		}
		btnSubmit.click();
	}
	
	public void validate_FundTransfer_Complete(){
		Pattern p = Pattern.compile("^\\d\\d.*[A-Za-z].*\\d\\d\\d\\d$");		
		if(Web.isWebElementDisplayed(fndtofndConfNum,true) && !Web.isWebElementDisplayed(tabDefInvstmntOpt,false)){
			Matcher match = p.matcher(fndtofndEffDate.getText().split(" ")[2]);							
			if(fndtofndConfNum.getText().matches(".*\\d+.*") && match.find()){
				Reporter.logEvent(Status.PASS,"Fund to Fund transfer confirmation validation", "Fund to Fund transfer executed successfully", false);
			}else{
				Reporter.logEvent(Status.FAIL,"Fund to Fund transfer confirmation validation", "Fund to Fund transfer execution failed", true);
			}
		}
	}
	
}
