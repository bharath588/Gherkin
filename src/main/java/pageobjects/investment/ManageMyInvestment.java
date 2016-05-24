package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.general.LeftNavigationBar;

public class ManageMyInvestment extends LoadableComponent<ManageMyInvestment> {
	
	private LoadableComponent<?> parent;
	String investmentFundName1;
	String investmentFundName2;
	String fromInvestmentOption;
	String toInvestmentOption;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()[normalize-space()='My Investments']]") private WebElement lblMyInvestments;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//button[text()[normalize-space()='Change My Investments']]") private WebElement btnChangeMyInvestment;
	@FindBy(xpath=".//strong[text()[normalize-space()='Rebalance my current balance']]") private WebElement radioRebalanceCurrBal;
	@FindBy(xpath=".//strong[text()[normalize-space()='Change how my future contributions will be invested']]") private WebElement radioChangeFutureContr;
	@FindBy(xpath=".//strong[text()[normalize-space()='Change how my current balance is invested']]") private WebElement radioChangeCurrentBalInvst;
	@FindBy(xpath=".//strong[text()[normalize-space()='Dollar-cost average']]") private WebElement radioDollarCost;
	@FindBy(xpath="//button[text()[normalize-space()='Continue']]") private WebElement btnContinue1;
	@FindBy(xpath="//input[@value='Continue']") private WebElement btnContinue2;
	@FindBy(xpath="//input[@value='Cancel']") private WebElement btnCancel;
	@FindBy(id="perdTrfFreq_once") private WebElement radioOnce;
	@FindBy(id="perdTrfFreq_quarterly") private WebElement radioQuarterly;
	@FindBy(id="perdTrfFreq_semi_annually") private WebElement radioSemiAnnually;
	@FindBy(id="perdTrfFreq_annually") private WebElement radioAnnually;
	@FindBy(id="perdTrfFreq_today") private WebElement radiotoday;
	@FindBy(id="perdTrfFreq_future") private WebElement radioFuture;
	@FindBy(id="future_effdate_no") private WebElement radiotodayDollarCost;
	@FindBy(id="future_effdate_yes") private WebElement radioFutureDollarCost;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(id="transferPreSubmitButton") private WebElement btnReviewTransfer;
	@FindBy(id="preValidationWarningAId") private WebElement btnPreValidationOK;
	@FindBy(xpath="//table[@class='default']//tbody//table//tr//td[2]//input") private List<WebElement> lsttxtPercentage;
	@FindBy(xpath="//table[@class='default']//tbody//table//tr//td[1]") private List<WebElement> lstInvestmentFundName;
	@FindBy(xpath="//table[@class='default']") private WebElement tblInvestmentOptionsTable;
	@FindBy(xpath="//table[@class='default']//tbody//table//tr") private List<WebElement> lstInvestmentOptions;
	@FindBy(xpath="//span[contains(text(),'Rebalancer frequency')]") private WebElement lblRebalancerFrequency;
	@FindBy(xpath="//span[contains(text(),'setup date')]") private WebElement lblSetupDate;
	@FindBy(xpath="//p//table/tbody/tr[contains(@class,'tableData')]") private List<WebElement> lstInvestmentFunds;
	@FindBy(xpath="//h1[text()[normalize-space()='Change how my current balance is invested']]") private WebElement lblCurrentBalanceInvestment;
	@FindBy(xpath="//div[@id='transferGraphByAssetCurrentDivId']") private WebElement currentAssetBalGraph;
	@FindBy(xpath="//div[@id='transferGraphByAssetCurrentDivId']") private WebElement postTransferBalanceGraph;
	@FindBy(xpath="//div[@class='dollarpercent']/a[contains(@class, 'percentbutton')]") private WebElement btnPercent;
	
	@FindBy(id="transferFundFromTableId") private WebElement tblTransferFundFrom;
	@FindBy(xpath="//table[@id='transferFundFromTableId']/thead/tr") private WebElement hdrTransferFundFromTable;
	@FindBy(xpath="//table[@id='transferFundFromTableId']/tbody/tr/td/a") private WebElement lnkTransferFromInvestmentOption;
	@FindBy(xpath="//input[contains(@id,'FromPercent')]") private List<WebElement> txtTransferFromPercent;
	
	@FindBy(id="fundTransferToTableSelectId") private WebElement tblTransferFundTo;
	@FindBy(xpath="//table[@id='fundTransferToTableSelectId']/thead/tr") private WebElement hdrTransferFundToTable;
	@FindBy(xpath="//table[@id='fundTransferToTableSelectId']/tbody/tr/td[5]") private WebElement lnkTransferToInvestmentOption;
	@FindBy(xpath="//input[contains(@id,'ToPercent')]") private List<WebElement> txtTransferToPercent;
	@FindBy(xpath="//div[text()='View by Asset Class']") private WebElement tabViewByAssetClass;
	@FindBy(xpath="//div[@id='fundingoptions']/a[text()='Asset Class']") private WebElement selAssetClass;
	@FindBy(xpath="//div[@id='fundingtype']/a[text()='Investment Option Family']") private WebElement selInvestmentOptFamily;
	
	@FindBy(xpath="//tr[@id='verifyTransferFromRowId']") private WebElement hdrVerifyTransferFromTable;
	@FindBy(xpath="//tbody[@id='fundTransferDisplayId']/tr/td[1]") private WebElement lblTransferFromOption;
	@FindBy(xpath="//tbody[@id='fundTransferDisplayId']/tr/td[3]") private WebElement lblTransferFromPercent;
	
	@FindBy(xpath="//tr[@id='fixedRatesColTitle']") private WebElement hdrVerifyTransferToTable;
	@FindBy(xpath="//table[@id='transactions-validation']//tbody/tr/td[1]") private WebElement lblTransferToOption;
	@FindBy(xpath="//table[@id='transactions-validation']//tbody/tr/td[2]") private WebElement lblTransferToPercent;
	
	@FindBy(xpath="//input[@value='Continue to the next step']") private WebElement btnContinueToNextStep;
	@FindBy(xpath="//input[@id='use_termdate_no']") private WebElement radioDoNoTerminate;
	@FindBy(xpath="//input[@id='use_termdate_yes']") private WebElement radioTerminate;
	@FindBy(xpath="//table/tbody/tr[@class='tableSubtitle']") private WebElement hdrInvestmentOptionTblForDollarCost;
	@FindBy(xpath="//table/tbody/tr[contains(@class,'tableData')]/td[2]") private List<WebElement> lstInvestmentOptionsDollarCost;
	@FindBy(xpath="//table/tbody/tr[contains(@class,'tableData')]/td/input") private List<WebElement> lstChkInvestmentOptionDollarCost;
	@FindBy(xpath = ".//div[@class='container']/span[@ng-if='accuLogoLoaded']/img")
	private WebElement lblSponser;
	
	/** Empty args constructor
	 * 
	 */
	public ManageMyInvestment() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public ManageMyInvestment(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		else{
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(
				0, ssn.length() - 3));

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		String userLogedIn = this.lblUserName.getText();
		String sponser = this.lblSponser.getAttribute("Alt");
		if (sponser.isEmpty()) {
			sponser = Common.GC_DEFAULT_SPONSER;
		}
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)
				&& Common.isCurrentSponser(sponser)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyInvestments,true));
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("View/manage my investments");
		
	}
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Rebalance Currnet Balance")) {
			return this.radioRebalanceCurrBal;
		}
		if (fieldName.trim().equalsIgnoreCase("Change Future Contribution")) {
			return this.radioChangeFutureContr;
		}
		if (fieldName.trim().equalsIgnoreCase("Change Current Balance Investment")) {
			return this.radioChangeCurrentBalInvst;
		}
		if (fieldName.trim().equalsIgnoreCase("Dollar Cost")) {
			return this.radioDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Continue button1")) {
			return this.btnContinue1;
		}
		if (fieldName.trim().equalsIgnoreCase("Cancel button")) {
			return this.btnCancel;
		}
		if (fieldName.trim().equalsIgnoreCase("Once")) {
			return this.radioOnce;
		}
		if (fieldName.trim().equalsIgnoreCase("Today")) {
			return this.radiotoday;
		}
		if (fieldName.trim().equalsIgnoreCase("Setup Today Dollar Cost")) {
			return this.radiotodayDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Future Dollar Cost")) {
			return this.radioFutureDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("View By Asset Class Tab")) {
			return this.tabViewByAssetClass;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Assets Balance Graph")) {
			return this.currentAssetBalGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("Post Transfer Balance Graph")) {
			return this.postTransferBalanceGraph;
		}
		
		return null;
	}
	
	public void clickChangeMyInvestmentButton(){
		if(Web.isWebElementDisplayed(btnChangeMyInvestment, true)){
			btnChangeMyInvestment.click();
			Reporter.logEvent(Status.INFO, "verify Change My Investment button is displayed", "Clicked on Change My Investment button",true);
		}
		else
			Reporter.logEvent(Status.FAIL, "verify Change My Investment button is displayed", "Change My Investment button not displayed",true);
	}
	
	
	public void choseInvestmentOption(String investmentOption){
		WebElement option = this.getWebElement(investmentOption);
		if(Web.isWebElementDisplayed(option, true)){
			Reporter.logEvent(Status.INFO, "verify "+investmentOption+" option is displayed",investmentOption+" option is displayed",true);
			option.click();
		}
		else
			Reporter.logEvent(Status.FAIL, "verify "+investmentOption+" option is displayed", investmentOption+" option not displayed",true);
	}
	
	public void rebalanceInvestment(String frequency,String setupDate,String percent){
		Web.waitForElement(iframeLegacyFeature);
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement freq = this.getWebElement(frequency);
		WebElement date = this.getWebElement(setupDate);
		Web.clickOnElement(freq);
		Reporter.logEvent(Status.INFO, "verify if frequency period is selected","Selected Frequency Period : "+frequency,true);
		Web.clickOnElement(date);
		Reporter.logEvent(Status.INFO, "Specify the Rebalancer Setup Date","Setup Date : "+setupDate,false);
		//Web.webdriver.switchTo().frame(iframeLegacyFeature);
		btnContinue2.click();
		if(Web.isWebElementDisplayed(tblInvestmentOptionsTable,true)){
			Reporter.logEvent(Status.PASS, "Verify Investment options table is displayed","Investment options table is displayed ",true);
			int noOfRows=lstInvestmentOptions.size();
			if(noOfRows>=2){
				Reporter.logEvent(Status.PASS, "Verify Investment options are available","Investment options available ",false);
				investmentFundName1=lstInvestmentFundName.get(0).getText().trim();
				investmentFundName2=lstInvestmentFundName.get(1).getText().trim();
				System.out.println(investmentFundName1);
				System.out.println(investmentFundName2);
				Web.setTextToTextBox(lsttxtPercentage.get(1),percent);
				Web.setTextToTextBox(lsttxtPercentage.get(2),percent);
				Reporter.logEvent(Status.INFO, "Enter Investment Percent","Entered investment percent : "+percent+" for "+investmentFundName1+"and"+investmentFundName2+"each",true);
			}	
			else
				Reporter.logEvent(Status.FAIL, "Verify Investment options are available","Investment options not available ",true);
			
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Investment options Table is displayed","Investment options table not displayed ",true);
		
		btnContinue2.click();
		Web.webdriver.switchTo().defaultContent();
	}
	public void dollarCostAverageFlow(String frequency,String setupDate,String percent){
		Web.waitForElement(iframeLegacyFeature);
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement freq = this.getWebElement(frequency);
		WebElement date = this.getWebElement(setupDate);
		Web.clickOnElement(freq);
		Reporter.logEvent(Status.INFO, "verify if frequency period is selected","Selected Frequency Period : "+frequency,true);
		Web.clickOnElement(date);
		Reporter.logEvent(Status.INFO, "Specify the Dollar Cost Average Setup Date","Setup Date : "+setupDate,false);
		//Web.webdriver.switchTo().frame(iframeLegacyFeature);
		btnContinueToNextStep.click();
		Web.waitForElement(radioDoNoTerminate);
		radioDoNoTerminate.click();
		Reporter.logEvent(Status.INFO, "verify if 'Do not terminate this Dollar cost average' option is selected","'Do not terminate this Dollar cost average' option is selected",false);
		if(Web.VerifyText("Investment Options Balance Effective Date", hdrInvestmentOptionTblForDollarCost.getText(), true))
			Reporter.logEvent(Status.PASS, "verify Investment Options Table Header for Dollar Cost Average", "Expected: Investment Options Balance Effective Date /n Actual: "+hdrInvestmentOptionTblForDollarCost.getText().trim(), false);
		else
			Reporter.logEvent(Status.FAIL, "verify Investment Options Table Header for Dollar Cost Average", "Expected: Investment Options Balance Effective Date /n Actual: "+hdrInvestmentOptionTblForDollarCost.getText().trim(), true);
		lstChkInvestmentOptionDollarCost.get(1).click();
		
		fromInvestmentOption=lstInvestmentOptionsDollarCost.get(1).getText();
		btnContinueToNextStep.click();
		
		
	}
	
	public void verifyRebalanceInvestmentDetails(String frequency,String setupDate,String date,String percent){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		if(Web.VerifyText("Your chosen Rebalancer frequency is: "+frequency, lblRebalancerFrequency.getText().trim()+" "+radioOnce.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer Frequency", "Expected: Your chosen Rebalancer frequency is: "+frequency+"\n Actual: "+lblRebalancerFrequency.getText().trim()+" "+radioOnce.getText().trim(), true);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer Frequency", "Expected: Your chosen Rebalancer frequency is: "+frequency+"\n Actual: "+lblRebalancerFrequency.getText().trim()+" "+radioOnce.getText().trim(),true);
		
		if(Web.VerifyText("Your chosen Rebalancer setup date is: "+setupDate, lblSetupDate.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer setup date", "Expected: Your chosen Rebalancer setup date is: "+date+"\n Actual: "+lblSetupDate.getText().trim(), false);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer setup date", "Expected: Your chosen Rebalancer setup date is: "+date+"\n Actual: "+lblSetupDate.getText().trim(),true);
		
		if(lstInvestmentFunds.get(0).getText().contains(investmentFundName1) & lstInvestmentFunds.contains(percent))
			Reporter.logEvent(Status.PASS, "verify Fund name and investment percent displayed", "Fund name: "+investmentFundName1+"\n Investment percent: "+percent, false);
		else
			Reporter.logEvent(Status.FAIL, "verify Fund name and investment percent displayed", "Fund name: "+investmentFundName1+"\n Investment percent: "+percent,true);
		
		if(lstInvestmentFunds.get(1).getText().contains(investmentFundName2) & lstInvestmentFunds.contains(percent))
			Reporter.logEvent(Status.PASS, "verify Fund name and investment percent displayed", "Fund name: "+investmentFundName2+"\n Investment percent: "+percent, false);
		else
			Reporter.logEvent(Status.FAIL, "verify Fund name and investment percent displayed", "Fund name: "+investmentFundName2+"\n Investment percent: "+percent,true);
		
		Web.webdriver.switchTo().defaultContent();
		
	}
	
	public void fundToFundTransfer(String fromPercent, String toPercent){
		
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		if(Web.isWebElementDisplayed(tblTransferFundFrom)){
			Reporter.logEvent(Status.INFO, "Verify 'Transfer Fund From' Table is displayed", "Table is displayed",true);
			btnPercent.click();
			Web.setTextToTextBox(txtTransferFromPercent.get(0), fromPercent);
			fromInvestmentOption=lnkTransferFromInvestmentOption.getText();
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify 'Transfer Fund From' Table is displayed", "Table is not displayed",true);
			
		if(Web.isWebElementDisplayed(tblTransferFundTo)){
			Reporter.logEvent(Status.INFO, "Verify 'Transfer Fund To' Table is displayed", "Table is displayed",true);
			Web.setTextToTextBox(txtTransferToPercent.get(0), toPercent);
			toInvestmentOption=lnkTransferToInvestmentOption.getText();
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify 'Transfer Fund To' Table is displayed", "Table is not displayed",true);
		btnReviewTransfer.click();
		Web.waitForElement(btnPreValidationOK);
		btnPreValidationOK.click();
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void ReviewFundToFundTransfer(String fromPercent, String toPercent){
		Web.waitForElement(iframeLegacyFeature);
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(hdrVerifyTransferFromTable);
		if(Web.VerifyText("Investment Option Balance Percent", hdrVerifyTransferFromTable.getText(), true)){
			Reporter.logEvent(Status.PASS, "Verify Transfer From Table Header is Displayed", "Expected : Investment Option Balance Percent \n Actual : "+hdrVerifyTransferFromTable.getText(),true);
			
			if(Web.VerifyText(fromInvestmentOption, lblTransferFromOption.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify From Investment Option in Review Transfer Page", "Expected : "+fromInvestmentOption+"\n Actual :"+lblTransferFromOption.getText(),false);
			else
				Reporter.logEvent(Status.FAIL, "Verify From Investment Option in Review Transfer Page", "Expected : "+fromInvestmentOption+"\n Actual :"+lblTransferFromOption.getText(),true);
			
			if(Web.VerifyText(fromPercent+"%", lblTransferFromPercent.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify From Investment Percent in Review Transfer Page", "Expected : "+fromPercent+"%"+"\n Actual :"+lblTransferFromPercent.getText(),false);
			else
				Reporter.logEvent(Status.FAIL, "Verify From Investment Percent in Review Transfer Page", "Expected : "+fromPercent+"%"+"\n Actual :"+lblTransferFromPercent.getText(),true);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Transfer From Table Header is Displayed", "Expected : Investment Option Balance Percent \n Actual : "+hdrVerifyTransferFromTable.getText(),true);
		
		if(Web.VerifyText("Investment Option Balance Percent", hdrVerifyTransferFromTable.getText(), true)){
			Reporter.logEvent(Status.PASS, "Verify Transfer From Table Header is Displayed", "Expected : Investment Option Balance Percent \n Actual : "+hdrVerifyTransferFromTable.getText(),true);
		
			if(Web.VerifyText(toInvestmentOption, lblTransferToOption.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify To Investment Option in Review Transfer Page", "Expected : "+toInvestmentOption+"\n Actual :"+lblTransferToOption.getText(),false);
			else
				Reporter.logEvent(Status.FAIL, "Verify To Investment Option in Review Transfer Page", "Expected : "+toInvestmentOption+"\n Actual :"+lblTransferToOption.getText(),true);
			
			if(Web.VerifyText(toPercent+"%", lblTransferToPercent.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify To Investment Percent in Review Transfer Page", "Expected : "+toPercent+"%"+"\n Actual :"+lblTransferToPercent.getText(),false);
			else
				Reporter.logEvent(Status.FAIL, "Verify To Investment Percent in Review Transfer Page", "Expected : "+toPercent+"%"+"\n Actual :"+lblTransferToPercent.getText(),true);
		
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Transfer To Table Header is Displayed", "Expected : Investment Option Balance Percent \n Actual : "+hdrVerifyTransferToTable.getText(),true);
				
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void navigateToTab(String tabName){
		Web.waitForElement(iframeLegacyFeature);
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement tab = this.getWebElement(tabName);
		Web.waitForElement(tab);
		tab.click();
		Reporter.logEvent(Status.INFO, "Navigate To "+tabName, "Navigated to "+tabName,true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifyIfGraphDisplayed(String graphName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement graph = this.getWebElement(graphName);
		if(Web.isWebElementDisplayed(graph, true))
			Reporter.logEvent(Status.PASS, "Verify "+graphName+"is displayed", graphName+"is displayed",false);
		else
			Reporter.logEvent(Status.FAIL, "Verify "+graphName+"is displayed", graphName+"is not displayed",true);
		Web.webdriver.switchTo().defaultContent();
	}
}
