package pageobjects.statementsanddocuments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class StatementsAndDocuments extends LoadableComponent<StatementsAndDocuments>{


	//Declarations
	private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//a[contains(text(),'Statements and Documents')]") private WebElement tabStmtsAndDocs;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(id="contentFrame") private WebElement iframeContentFrame;
	@FindBy(xpath="//a[text()[normalize-space()='Statements on Demand']]") private WebElement tabStmtsOnDemand;
	@FindBy(xpath="//div[contains(@ng-if,'atAGlance')]") private WebElement tblStmtsSummary;
	@FindBy(xpath="//div[contains(@class,'statementsDocumentsTable')]") private WebElement tabelStmtsAndDocuments;
	@FindBy(xpath="//div[contains(@ng-if,'atAGlance')]//div[@class='dtable']//div") private WebElement tblStmtsSummaryData;
	@FindBy(xpath="//div[contains(@ng-if,'activityByContributionSources ')]") private WebElement tblStmtsByMoneyType;
	@FindBy(xpath="//div[contains(@ng-if,'activityByInvestmentOptions')]") private WebElement tblStmtsByFundDetail;
	@FindBy(xpath="//div[contains(@ng-if,'activityByInvestmentOptions')]//div[@class='dtable']//div") private WebElement tblInvestmentOptions;
	@FindBy(xpath="//div[contains(@ng-if,'atAGlance')]//h2") private WebElement hdrStmtsSummaryTable;
	@FindBy(xpath="//div[contains(@ng-if,'activityByContributionSources ')]//h2") private WebElement hdrStmtsByMoneyTable;
	@FindBy(xpath="//div[contains(@ng-if,'activityByContributionSources')]//div[@class='dtable']//div") private WebElement tblContributionSources;
	@FindBy(xpath="//div[contains(@ng-if,'activityByInvestmentOptions')]//h2") private WebElement hdrStmtsByFundDetailTable;
	@FindBy(xpath="//div[contains(@ng-if,'atAGlance')]//div[@class='dtable']//div") private List<WebElement> lstStmtsSummaryTableRows;
	@FindBy(xpath="//div[contains(@ng-if,'activityByContributionSources')]//div[@class='dtable']//div") private List<WebElement> lstStmtsByMoneyTableRows;
	@FindBy(xpath="//div[contains(@ng-if,'activityByInvestmentOptions')]//div[@class='dtable']//div") private List<WebElement> lstStmtsByFundDetailTableRows;
	@FindBy(xpath="//div[contains(@ng-if,'activityByContributionSources')]//div[@class='dtable']//div//span//a") private List<WebElement> lstStmtsByMoneyType;
	@FindBy(xpath="//div[contains(@ng-if,'activityByInvestmentOptions')]//div[@class='dtable']//div[1]//a") private List<WebElement> lstStmtsByFundDetail;
	
	
	@FindBy(xpath="//div[contains(@ng-if,'statementsOnDemandTransactionDetails')]") private WebElement tblStmtsByTxnDetail;
	@FindBy(xpath="//div[@class='modal-header']//h2") private WebElement hdrStmtsByTxnDetailTable;
	@FindBy(xpath="//div[contains(@ng-if,'statementsOnDemandTransactionDetails')]//div[@class='trow']") private List<WebElement> lstStmtsByTxnDetailTableRows;
	@FindBy(xpath="//div[contains(@class,'statementsDocumentsTable')]//div[@aria-label='CATEGORY']//span") private List<WebElement> lstCategory;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = "//button[contains(text(),'close')]")
	private WebElement btnClose;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath = "//button[contains(text(),' View All')]") private WebElement btnViewAll;
	@FindBy(xpath = "//a[contains(@ng-click,'pageNumber')]") private List<WebElement> lstPageNo;
	@FindBy(xpath = "//button[contains(text(),' View Less')]") private WebElement btnViewLess;
	@FindBy(id = "searchFilterDropdown")
	private WebElement drpDateFreequency;
	private String frequency="//a[text()='frequency']";
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
			Assert.assertTrue(lib.Web.isWebElementDisplayed(tabStmtsAndDocs),"Statements and Documenta page is not loaded");
		
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
		lib.Web.isWebElementDisplayed(tabStmtsAndDocs,true);
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Stmts On Demand Tab")) {
			return this.tabStmtsOnDemand;
		}
		if (fieldName.trim().equalsIgnoreCase("Statements And Documents Tab")) {
			return this.tabStmtsAndDocs;
		}
		if (fieldName.trim().equalsIgnoreCase("Statements And Documents")) {
			return this.tabelStmtsAndDocuments;
		}
		if (fieldName.trim().equalsIgnoreCase("Account at a Glance Table")) {
			return this.tblStmtsSummaryData;
		}
		if (fieldName.trim().equalsIgnoreCase("Account at a Glance")) {
			return this.tblStmtsSummary;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source")) {
			return this.tblStmtsByMoneyType;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Contribution Source Table")) {
			return this.tblContributionSources;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option")) {
			return this.tblStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option Table")) {
			return this.tblInvestmentOptions;
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
		if (fieldName.trim().equalsIgnoreCase("Activity by Investment Option")) {
			return this.lstStmtsByFundDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction Details Table")) {
			return this.lstStmtsByTxnDetailTableRows;
		}
		return null;
	}
	
public void verifyTableDisplayed(String tableName){
		
		WebElement table = this.getWebElement(tableName);
		if(Web.isWebElementDisplayed(table))
			Reporter.logEvent(Status.PASS, "verify "+tableName+" Table is displayed", tableName+" Table is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" Table is displayed", tableName+" Table is not displayed",true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void verifyTableDataDisplayed(String tableName){
		
		List<WebElement> table = this.getWebElementList(tableName);
		int noOfRows=table.size();
		if(noOfRows>=1)
			Reporter.logEvent(Status.PASS, "verify data is displayed for "+tableName, "Data is displayed for "+tableName, false);
		else
			Reporter.logEvent(Status.FAIL, "verify data is displayed for "+tableName, "Data is not displayed for "+tableName,true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void verifytableHeaderNotEmpty(String tableName){
	 	
		
	 	WebElement tableHeader = this.getWebElement(tableName);
	 	if(!tableHeader.getText().isEmpty())
	 		Reporter.logEvent(Status.PASS, "verify "+tableName+" displayed",tableName+" is displayed" , false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" displayed", tableName+" not displayed",true);
	 	Web.getDriver().switchTo().defaultContent();


	}	
	
	public void clickOnStatementFromTable(String tableName){
		
		List<WebElement> statements = this.getWebElementList(tableName);
		if(statements.size()>=1){
			Reporter.logEvent(Status.PASS, "verify Contribution Source displayed in "+tableName, "Contribution Source is displayed",true);
			Reporter.logEvent(Status.INFO, "Clicking on Contribution Source", "Clicking on Contribution Source Type:"+statements.get(0).getText().trim(),true);
			statements.get(1).click();
			 Common.waitForProgressBar();
	         Web.waitForPageToLoad(Web.getDriver());
	         if(hdrStmtsByTxnDetailTable.isDisplayed()){
			         Reporter.logEvent(Status.INFO, "verify statement window is opened", "Statement window opened for Transaction Details",true);
			     
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
	/**
	 * Method to click on close Button
	 */
	public void clickOnClose(){
		if(Web.isWebElementDisplayed(btnClose, true)){
			Web.clickOnElement(btnClose);
		}
	}
	
	public void navigateToTab(String tabName){
		boolean isnavigateSuccessful=false;
		//Web.getDriver().switchTo().frame(iframeLegacyFeature);
		
		if(tabName.equalsIgnoreCase("Stmts On Demand Tab")){
			Web.waitForElement(tabStmtsOnDemand);
			Web.clickOnElement(tabStmtsOnDemand);
			if(Web.isWebElementDisplayed(tblStmtsSummary,true))
				isnavigateSuccessful=true;
		}
		else if(tabName.equalsIgnoreCase("Statements And Documents Tab")){
			Web.waitForElement(tabStmtsAndDocs);
			Web.clickOnElement(tabStmtsAndDocs);
			if(Web.isWebElementDisplayed(tabelStmtsAndDocuments,true))
				isnavigateSuccessful=true;
		}
			
		if(isnavigateSuccessful)
			Reporter.logEvent(Status.PASS, "verify navigate to "+tabName+"  successfull", "Able to navigate to "+tabName+" tab", true);
		else
			Reporter.logEvent(Status.FAIL, "verify navigate to "+tabName+" successfull", " Not Able to navigate to "+tabName+" tab",true);
		//Web.getDriver().switchTo().defaultContent();
	}
	
	/**
	 * This Method is to select Date Frequency and click on Submit Query
	 * @param dateFrequency
	 * @throws InterruptedException 
	 */
	public void selectDateFrequency(String frequency) throws InterruptedException{
		
		Web.waitForElement(drpDateFreequency);
		Web.clickOnElement(drpDateFreequency);
		Thread.sleep(3000);
		WebElement ele= Web.getDriver().findElement(By.xpath(this.frequency.replaceAll("frequency", frequency)));
		ele.click();
		Common.waitForProgressBar();
		
	}
	
	/**
	 * This Method is to verify the filter
	 * @param dateFrequency
	 * @throws InterruptedException 
	 */
	public void verifyFilter(String filterType) throws InterruptedException{
		boolean matching=true;
		String category="";
		Web.waitForElement(drpDateFreequency);
		Web.clickOnElement(drpDateFreequency);
		Thread.sleep(3000);
		WebElement ele= Web.getDriver().findElement(By.xpath(this.frequency.replaceAll("frequency", filterType)));
		ele.click();
		Common.waitForProgressBar();
		int size=lstCategory.size();
		for(int i=0;i<size;i++){
			if(!filterType.contains(lstCategory.get(i).getText().toString().trim())){
				matching=false;
				category=lstCategory.get(i).getText().toString().trim();
			}
			
		}
		if(matching){
			Reporter.logEvent(Status.PASS, "verify Documents are Filtered by selected Category", "Documents Are Filted Properly\nExpected:"+filterType+"\nActual:"+filterType,true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "verify Documents are Filtered by selected Category", "Documents Are Not Filted Properly\nExpected:"+filterType+"\nActual:"+category,true);
		}
		
		
	}
	
	
	/**
	 * This Method is to verify View All Link
	 * @throws InterruptedException 
	 */
	public void verifyViewAllLink() throws InterruptedException{
		
		Web.waitForElement(btnViewAll);
	
		if(Web.isWebElementsDisplayed(lstPageNo, false)){
			Reporter.logEvent(Status.PASS, "verify Page Numbering is Displayed", "Page Numbering is Displayed",false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "verify Page Numbering is Displayed", "Page Numbering is Displayed",true);
		}
		
		Web.clickOnElement(btnViewAll);
		Reporter.logEvent(Status.INFO, "Clicking on ViewAll Link", "Clicked on ViewAll Link",false);
		if(Web.isWebElementDisplayed(btnViewLess, true)){
			Reporter.logEvent(Status.PASS, "verify 'ViewLess' Button is Displayed by Clicking on ViewAll Link", "'ViewLess' Button is Displayed'",true);
		}
		else{
			Reporter.logEvent(Status.FAIL,  "verify 'ViewLess' Button is Displayed by Clicking on ViewAll Link", "'ViewLess' Button is Displayed'",true);
		}
		Web.clickOnElement(btnViewLess);
		Reporter.logEvent(Status.INFO, "Clicking on ViewLess Link", "Clicked on ViewLess Link",false);
		if(Web.isWebElementDisplayed(btnViewAll, true)&& Web.isWebElementsDisplayed(lstPageNo, false) ){
			Reporter.logEvent(Status.PASS, "verify 'ViewAll' Button And 'Page Numbering' is Displayed by Clicking on ViewLess Link", "ViewAll' Button And 'Page Numbering' Button is Displayed'",true);
		}
		else{
			Reporter.logEvent(Status.FAIL,  "verify 'ViewAll' Button And 'Page Numbering' is Displayed by Clicking on ViewLess Link", "ViewAll' Button And 'Page Numbering' Button is Displayed'",true);
		}
	}
}
