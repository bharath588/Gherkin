package pageobjects.balance;

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
import pageobjects.general.LeftNavigationBar;

public class Balance extends LoadableComponent<Balance> {

	//Declarations
	private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	//@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Balance']") private WebElement lblBalance;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//li[contains(@class,'tab ng-scope')]//a[text()[normalize-space()='Balance']]") private WebElement tabBalance;
	@FindBy(xpath="//li[contains(@class,'tab ng-scope')]//a[text()[normalize-space()='Balance over time']]") private WebElement tabBalanceOverTime;
	@FindBy(xpath="//li[contains(@class,'tab ng-scope')]//a[text()[normalize-space()='Balance comparison']]") private WebElement tabBalanceComparison;
	@FindBy(id="balanceByMoneyTypeTable") private WebElement tblBalanceByMoney;
	@FindBy(id="balanceByFundDetailTable") private WebElement tblBalanceByInvestment;
	@FindBy(id="balanceHistoryTable") private WebElement tblBalanceHistory;
	@FindBy(id="balanceComparisonTable") private WebElement tblBalanceComparison;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(id="highChartsGraphDiv") private WebElement highChartsGraph;
	@FindBy(id="lineGraphDiv") private WebElement lineGraph;
	@FindBy(id="graphDiv") private WebElement balanceComparisonGraph;
	@FindBy(xpath="//table[@id='balanceByMoneyTypeTable']//tbody[@id='balByMoneySourceTBody']/tr") private List<WebElement> lstBalanceByMoneyTypeDetail;
	@FindBy(xpath="//table[@id='balanceByMoneyTypeTable']//thead/tr") private WebElement hdrBalanceByMoneyTable;
	@FindBy(xpath="//table[@id='balanceByFundDetailTable']//tbody[@id='balanceByFundDetailTbody']/tr") private List<WebElement> lstBalanceByInvestmentTypeDetail;
	@FindBy(xpath="//table[@id='balanceByFundDetailTable']//thead/tr") private WebElement hdrBalanceByInvestmentTable;
	@FindBy(xpath="//table[@id='balanceHistoryTable']//tbody/tr") private List<WebElement> lstBalanceHistoryDetail;
	@FindBy(xpath="//table[@id='balanceHistoryTable']//thead/tr") private WebElement hdrBalanceHistoryTable;
	@FindBy(xpath="//table[@id='balanceComparisonTable']//tbody/tr") private List<WebElement> lstBalancceComparisonDetail;
	@FindBy(xpath="//table[@id='balanceComparisonTable']//thead/tr") private WebElement hdrBalancceComparisonTable;
	@FindBy(xpath = "//img[@class='site-logo']") private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	
	/** Empty args constructor
	 * 
	 */
	public Balance() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public Balance(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Balance Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = null;
		String userFromDatasheet = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName").toString().trim();
				
		}
		else{
		 strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(
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
		if(sponser.isEmpty())
		{
			sponser=Common.GC_DEFAULT_SPONSER;
		}
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblBalance,true));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Balance");
		
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Balance")) {
			return this.tabBalance;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance over time")) {
			return this.tabBalanceOverTime;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance comparison")) {
			return this.tabBalanceComparison;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance by Money Table")) {
			return this.tblBalanceByMoney;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance by Investment Table")) {
			return this.tblBalanceByInvestment;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance History Table")) {
			return this.tblBalanceHistory;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance Comparison Table")) {
			return this.tblBalanceComparison;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Balance Comparison table Header")) {
			return this.hdrBalancceComparisonTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance by Money Table Header")) {
			return this.hdrBalanceByMoneyTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance by Investment Table Header")) {
			return this.hdrBalanceByInvestmentTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance History Table Header")) {
			return this.hdrBalanceHistoryTable;
		}
		if (fieldName.trim().equalsIgnoreCase("High chart graph")) {
			return this.highChartsGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("Line graph")) {
			return this.lineGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance Comparison Graph")) {
			return this.balanceComparisonGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		return null;
	}
	private List<WebElement> getWebElementList(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Balance by Money Table")) {
			return this.lstBalanceByMoneyTypeDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance by Investment Table")) {
			return this.lstBalanceByInvestmentTypeDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance History table")) {
			return this.lstBalanceHistoryDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Balance comparison table")) {
			return this.lstBalancceComparisonDetail;
		}
		return null;
	}
	
	
	
	public void navigateToTab(String tabName){
		boolean isnavigateSuccessful=false;
		Web.webdriver.switchTo().defaultContent();

		if(tabName.equalsIgnoreCase("Balance")){
			Web.clickOnElement(tabBalance);
			Web.webdriver.switchTo().frame(iframeLegacyFeature);
			if(Web.isWebElementDisplayed(tblBalanceByInvestment,true))
				isnavigateSuccessful=true;
		}
		if(tabName.equalsIgnoreCase("Balance over time")){
			Web.clickOnElement(tabBalanceOverTime);
			Web.waitForElement(tabBalanceOverTime);
			Web.webdriver.switchTo().frame(iframeLegacyFeature);
			if(Web.isWebElementDisplayed(tblBalanceHistory,true))
				isnavigateSuccessful=true;
		}
		if(tabName.equalsIgnoreCase("Balance comparison")){
			Web.clickOnElement(tabBalanceComparison);
			Web.waitForElement(tabBalanceComparison);
			Web.webdriver.switchTo().frame(iframeLegacyFeature);
			if(Web.isWebElementDisplayed(tblBalanceComparison,true))
				isnavigateSuccessful=true;
		}
		if(isnavigateSuccessful)
			Reporter.logEvent(Status.PASS, "verify navigate to "+tabName+" tab successfull", "Able to navigate to "+tabName+" tab", true);
		else
			Reporter.logEvent(Status.FAIL, "verify navigate to "+tabName+" tab successfull", " Not Able to navigate to "+tabName+" tab",true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifytableHeaderNotEmpty(String tableName){
	 	
	 	Web.webdriver.switchTo().frame(iframeLegacyFeature);
	 	WebElement tableHeader = this.getWebElement(tableName);
	 	if(!tableHeader.getText().isEmpty())
	 		Reporter.logEvent(Status.PASS, "verify "+tableName+" displayed",tableName+" is displayed" , false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" displayed", tableName+" not displayed",true);
	 	Web.webdriver.switchTo().defaultContent();


	}	
	
	
	public void verifyTableDisplayed(String tableName){
		
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement table = this.getWebElement(tableName);
		if(Web.isWebElementDisplayed(table))
			Reporter.logEvent(Status.PASS, "verify "+tableName+" is displayed", tableName+" is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+tableName+" is displayed", tableName+" is not displayed",true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifyTableDataDisplayed(String tableName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		List<WebElement> table = this.getWebElementList(tableName);
		int noOfRows=table.size();
		if(noOfRows>=1)
			Reporter.logEvent(Status.PASS, "verify data is displayed for "+tableName, "Data is displayed for "+tableName, false);
		else
			Reporter.logEvent(Status.FAIL, "verify data is displayed for "+tableName, "Data is not displayed for "+tableName,true);
		Web.webdriver.switchTo().defaultContent();
	}
	
	public void verifyGraphDisplayed(String graphName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement graph = this.getWebElement(graphName);
		if(Web.isWebElementDisplayed(graph))
			Reporter.logEvent(Status.PASS, "verify "+graphName+" is displayed", graphName+" is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify "+graphName+" is displayed", graphName+" is not displayed",true);
		Web.webdriver.switchTo().defaultContent();
	}
	
}
