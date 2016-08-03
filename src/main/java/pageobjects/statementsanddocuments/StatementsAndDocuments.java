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

	/** Empty args constructor
	 * 
	 */
	public StatementsAndDocuments() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public StatementsAndDocuments(LoadableComponent<?> parent) {
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
				) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblStmtsAndDocs,true));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Statements and documents");
		
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Stmts On Demand Tab")) {
			return this.tabStmtsOnDemand;
		}
		if (fieldName.trim().equalsIgnoreCase("Statements Summary Table")) {
			return this.tblStmtsSummary;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts By Money Type Table")) {
			return this.tblStmtsByMoneyType;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Fund Detail Table")) {
			return this.tblStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Statements Summary Table Header")) {
			return this.hdrStmtsSummaryTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts By Money Type Table Header")) {
			return this.hdrStmtsByMoneyTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Fund Detail Table Header")) {
			return this.hdrStmtsByFundDetailTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Transmission Detail Table")) {
			return this.tblStmtsByTxnDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Transmission Detail Table Header")) {
			return this.hdrStmtsByTxnDetailTable;
		}
		return null;
	}
	
	private List<WebElement> getWebElementList(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Statements Summary Table")) {
			return this.lstStmtsSummaryTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts By Money Type Table")) {
			return this.lstStmtsByMoneyTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Fund Detail Table")) {
			return this.lstStmtsByFundDetailTableRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Money Type Table")) {
			return this.lstStmtsByMoneyType;
		}
		if (fieldName.trim().equalsIgnoreCase("Fund Detail Table")) {
			return this.lstStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Stmts by Transmission Detail Table")) {
			return this.lstStmtsByTxnDetailTableRows;
		}
		return null;
	}
	
public void verifyTableDisplayed(String tableName){
		if(tableName.equalsIgnoreCase("Stmts by Transmission Detail Table"))
			Web.webdriver.switchTo().frame(iframeContentFrame);
		else
			Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement table = this.getWebElement(tableName);
		if(Web.isWebElementDisplayed(table))
			Reporter.logEvent(Status.PASS, "verify "+tableName+" is displayed", tableName+" is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" is displayed", tableName+" is not displayed",true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifyTableDataDisplayed(String tableName){
		if(tableName.equalsIgnoreCase("Stmts by Transmission Detail Table"))
			Web.webdriver.switchTo().frame(iframeContentFrame);
		else
			Web.webdriver.switchTo().frame(iframeLegacyFeature);
		
		List<WebElement> table = this.getWebElementList(tableName);
		int noOfRows=table.size();
		if(noOfRows>=1)
			Reporter.logEvent(Status.PASS, "verify data is displayed for "+tableName, "Data is displayed for "+tableName, false);
		else
			Reporter.logEvent(Status.FAIL, "verify data is displayed for "+tableName, "Data is not displayed for "+tableName,true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifytableHeaderNotEmpty(String tableName){
	 	
		if(tableName.equalsIgnoreCase("Stmts by Transmission Detail Table Header"))
			Web.webdriver.switchTo().frame(iframeContentFrame);
		else
			Web.webdriver.switchTo().frame(iframeLegacyFeature);  
	 	WebElement tableHeader = this.getWebElement(tableName);
	 	if(!tableHeader.getText().isEmpty())
	 		Reporter.logEvent(Status.PASS, "verify "+tableName+" displayed",tableName+" is displayed" , false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" displayed", tableName+" not displayed",true);
	 	Web.webdriver.switchTo().defaultContent();


	}	
	
	public void clickOnStatementFromTable(String tableName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		List<WebElement> statements = this.getWebElementList(tableName);
		if(statements.size()>=1){
			Reporter.logEvent(Status.PASS, "verify statements displayed for "+tableName, "statements displayed",true);
			statements.get(0).click();
			
		}
		else
			Reporter.logEvent(Status.FAIL, "verify statement displayed for "+tableName, "statements not displayed",true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void switchToWindow(){
		String parentWindow = Web.webdriver.getWindowHandle();
		Set<String> handles =  Web.webdriver.getWindowHandles();
		   for(String windowHandle  : handles)
		       {
		       if(!windowHandle.equals(parentWindow)){
		         Web.webdriver.switchTo().window(windowHandle);
		         Reporter.logEvent(Status.INFO, "verify statement window is opened", "Statement window opens",true);
		         Web.waitForElement(tblStmtsByTxnDetail);
		         verifyTableDisplayed("Stmts by Transmission Detail Table");
		   		 verifytableHeaderNotEmpty("Stmts by Transmission Detail Table Header");
		   		 verifyTableDataDisplayed("Stmts by Transmission Detail Table");
		   			
		         //closing child window
		   		 Web.webdriver.close();
		          //cntrl to parent window
		       }
		      
		       }
		   Web.webdriver.switchTo().window(parentWindow);
	}
	
	public void navigateToTab(String tabName){
		boolean isnavigateSuccessful=false;
		Web.webdriver.switchTo().frame(iframeLegacyFeature);;
		if(tabName.equalsIgnoreCase("Stmts On Demand Tab")){
			Web.clickOnElement(tabStmtsOnDemand);
			if(Web.isWebElementDisplayed(tblStmtsSummary,true))
				isnavigateSuccessful=true;
		}
		if(isnavigateSuccessful)
			Reporter.logEvent(Status.PASS, "verify navigate to "+tabName+"  successfull", "Able to navigate to "+tabName+" tab", true);
		else
			Reporter.logEvent(Status.FAIL, "verify navigate to "+tabName+" successfull", " Not Able to navigate to "+tabName+" tab",true);
		Web.webdriver.switchTo().defaultContent();
	}
}
