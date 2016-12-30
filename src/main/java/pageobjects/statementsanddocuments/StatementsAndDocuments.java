package pageobjects.statementsanddocuments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.LeftNavigationBar;
import pageobjects.transactionhistory.TransactionHistory;

public class StatementsAndDocuments extends LoadableComponent<StatementsAndDocuments>{


	//Declarations
	private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Statements and documents']") private WebElement lblStmtsAndDocs;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(id="contentFrame") private WebElement iframeContentFrame;
	@FindBy(xpath="//a[text()[normalize-space()='Statements on Demand']]") private WebElement tabStmtsOnDemand;
	@FindBy(id="statementsSummaryTable") private WebElement tblStmtsSummary;
	@FindBy(id="statementsByMoneyTypeTable") private WebElement tblStmtsByMoneyType;
	@FindBy(id="statementsByFundDetailTable") private WebElement tblStmtsByFundDetail;
	@FindBy(xpath="//table[@id='statementsSummaryTable']//thead/tr") private WebElement hdrStmtsSummaryTable;
	@FindBy(xpath="//table[@id='statementsByMoneyTypeTable']//thead/tr") private WebElement hdrStmtsByMoneyTable;
	@FindBy(xpath="//table[@id='statementsByFundDetailTable']//thead/tr") private WebElement hdrStmtsByFundDetailTable;
	@FindBy(xpath="//table[@id='statementsSummaryTable']//tbody/tr") private List<WebElement> lstStmtsSummaryTableRows;
	@FindBy(xpath="//table[@id='statementsByMoneyTypeTable']//tbody[@id='txnSummaryTbody']/tr") private List<WebElement> lstStmtsByMoneyTableRows;
	@FindBy(xpath="//table[@id='statementsByFundDetailTable']//tbody[@id='actByInvestmentTableBody']/tr") private List<WebElement> lstStmtsByFundDetailTableRows;
	@FindBy(xpath="//table[@id='statementsByMoneyTypeTable']//*[@id='statementsByMoneyTypeName']/a") private List<WebElement> lstStmtsByMoneyType;
	@FindBy(xpath="//table[@id='statementsByFundDetailTable']//*[@id='statementsByFundDetailName']/a") private List<WebElement> lstStmtsByFundDetail;
	
	@FindBy(id="statementsByTxnDetailTable") private WebElement tblStmtsByTxnDetail;
	@FindBy(xpath="//table[@id='statementsByTxnDetailTable']//tbody/tr[@id='statementsByTxnDetailColTitle']") private WebElement hdrStmtsByTxnDetailTable;
	@FindBy(xpath="//table[@id='statementsByTxnDetailTable']//tbody/tr[@id='statementsByTxnDetailData']") private List<WebElement> lstStmtsByTxnDetailTableRows;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath = "//div[@class='executable customDropdown']//div[@class='ddDiv']")
	private WebElement drpDateFreequency;
	private String dateFrequency="//div[@class='executable customDropdown']//div[@class='ddDiv']//ul/li[contains(text(),'frequency')]";
	@FindBy(xpath = "//input[@type='submit']")
	private WebElement btnSubmitQuery;
	@FindBy(xpath = ".//*[@id='statementsByMoneyTypeNameColTitle']/a")
	private WebElement colContributionSource;

	/** Empty args constructor
	 * 
	 */
	public StatementsAndDocuments() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public StatementsAndDocuments(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblStmtsAndDocs,true),"Statements And Documents Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		else{
		ResultSet strUserInfo=null;
		try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		String userLogedIn = this.lblUserName.getText();
		/*String sponser = this.lblSponser.getAttribute("Alt");
		if (sponser.isEmpty()) {
			sponser = Common.GC_DEFAULT_SPONSER;
		}*/
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblStmtsAndDocs),"Statements and Documenta page is not loaded");
		
		} else {
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Statements and documents");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		lib.Web.isWebElementDisplayed(lblStmtsAndDocs,true);
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Stmts On Demand Tab")) {
			return this.tabStmtsOnDemand;
		}
		if (fieldName.trim().equalsIgnoreCase("Account at a Glance Table")) {
			return this.tblStmtsSummary;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source Table")) {
			return this.tblStmtsByMoneyType;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option Table")) {
			return this.tblStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Account at a Glance Table Header")) {
			return this.hdrStmtsSummaryTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source Table Header")) {
			return this.hdrStmtsByMoneyTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option Table Header")) {
			return this.hdrStmtsByFundDetailTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction Details Table")) {
			return this.tblStmtsByTxnDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction Details Table Header")) {
			return this.hdrStmtsByTxnDetailTable;
		}
		return null;
	}
	
	private List<WebElement> getWebElementList(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Account at a Glance Table")) {
			return this.lstStmtsSummaryTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source Table")) {
			return this.lstStmtsByMoneyTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option Table")) {
			return this.lstStmtsByFundDetailTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source")) {
			return this.lstStmtsByMoneyType;
		}
		if (fieldName.trim().equalsIgnoreCase("Fund Detail Table")) {
			return this.lstStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction Details Table")) {
			return this.lstStmtsByTxnDetailTableRows;
		}
		return null;
	}
	
public void verifyTableDisplayed(String tableName){
		if(tableName.equalsIgnoreCase("Transaction Details Table"))
			Web.getDriver().switchTo().frame(iframeContentFrame);
		else
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
		WebElement table = this.getWebElement(tableName);
		if(Web.isWebElementDisplayed(table))
			Reporter.logEvent(Status.PASS, "verify "+tableName+" is displayed", tableName+" is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" is displayed", tableName+" is not displayed",true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void verifyTableDataDisplayed(String tableName){
		if(tableName.equalsIgnoreCase("Transaction Details Table"))
			Web.getDriver().switchTo().frame(iframeContentFrame);
		else
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
		
		List<WebElement> table = this.getWebElementList(tableName);
		int noOfRows=table.size();
		if(noOfRows>=1)
			Reporter.logEvent(Status.PASS, "verify data is displayed for "+tableName, "Data is displayed for "+tableName, false);
		else
			Reporter.logEvent(Status.FAIL, "verify data is displayed for "+tableName, "Data is not displayed for "+tableName,true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void verifytableHeaderNotEmpty(String tableName){
	 	
		if(tableName.equalsIgnoreCase("Transaction Details Table Header"))
			Web.getDriver().switchTo().frame(iframeContentFrame);
		else
			Web.getDriver().switchTo().frame(iframeLegacyFeature);  
	 	WebElement tableHeader = this.getWebElement(tableName);
	 	if(!tableHeader.getText().isEmpty())
	 		Reporter.logEvent(Status.PASS, "verify "+tableName+" displayed",tableName+" is displayed" , false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" displayed", tableName+" not displayed",true);
	 	Web.getDriver().switchTo().defaultContent();


	}	
	
	public void clickOnStatementFromTable(String tableName){
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		List<WebElement> statements = this.getWebElementList(tableName);
		if(statements.size()>=1){
			Reporter.logEvent(Status.PASS, "verify Contribution Source displayed in "+tableName, "Contribution Source is displayed",true);
			Reporter.logEvent(Status.INFO, "Clicking on Contribution Source", "Clicking on Contribution Source Type:"+statements.get(0).getText().trim(),true);
			statements.get(0).click();
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			         Web.getDriver().switchTo().window(windowHandle);
			         Reporter.logEvent(Status.INFO, "verify statement window is opened", "Statement window opened for Transaction Details",true);
			         Common.waitForProgressBar();
			         Web.waitForPageToLoad(Web.getDriver());
			        
			       }
			       }
		}
		else
			Reporter.logEvent(Status.FAIL, "verify Contribution Source displayed in "+tableName, "Contribution Source is not displayed in"+tableName,true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void switchToWindow(){
		String parentWindow = Web.getDriver().getWindowHandle();
		Set<String> handles =  Web.getDriver().getWindowHandles();
		   for(String windowHandle  : handles)
		       {
		       if(!windowHandle.equals(parentWindow)){
		         Web.getDriver().switchTo().window(windowHandle);
		         Reporter.logEvent(Status.INFO, "verify statement window is opened", "Statement window opened for Transaction Details",true);
		         Web.waitForPageToLoad(Web.getDriver());
		         Web.waitForElement(tblStmtsByTxnDetail);
		         verifyTableDisplayed("Transaction Details Table");
		   		 verifytableHeaderNotEmpty("Transaction Details Table Header");
		   		 verifyTableDataDisplayed("Transaction Details Table");
		   			
		         //closing child window
		   		 Web.getDriver().close();
		          //cntrl to parent window
		       }
		      
		       }
		   Web.getDriver().switchTo().window(parentWindow);
	}
	
	public void navigateToTab(String tabName){
		boolean isnavigateSuccessful=false;
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		
		if(tabName.equalsIgnoreCase("Stmts On Demand Tab")){
			Web.waitForElement(tabStmtsOnDemand);
			Web.clickOnElement(tabStmtsOnDemand);
			if(Web.isWebElementDisplayed(tblStmtsSummary,true))
				isnavigateSuccessful=true;
		}
		if(isnavigateSuccessful)
			Reporter.logEvent(Status.PASS, "verify navigate to "+tabName+"  successfull", "Able to navigate to "+tabName+" tab", true);
		else
			Reporter.logEvent(Status.FAIL, "verify navigate to "+tabName+" successfull", " Not Able to navigate to "+tabName+" tab",true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	/**
	 * This Method is to select Date Frequency and click on Submit Query
	 * @param dateFrequency
	 * @throws InterruptedException 
	 */
	public void selectDateFrequency(String frequency) throws InterruptedException{
		Actions mouse = new Actions(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(drpDateFreequency);
		if(!Web.isWebElementDisplayed(drpDateFreequency)){
		
		}
		Web.clickOnElement(drpDateFreequency);
		 WebElement DateFrequency= Web.getDriver().findElement(By.xpath(dateFrequency.replaceAll("frequency", frequency)));
		mouse.moveToElement(DateFrequency).click().build().perform();
		Thread.sleep(3000);
		Web.clickOnElement(btnSubmitQuery);
		Web.waitForElement(colContributionSource);
		Web.getDriver().switchTo().defaultContent();
	}
}
