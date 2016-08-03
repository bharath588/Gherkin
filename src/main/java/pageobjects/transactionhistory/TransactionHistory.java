package pageobjects.transactionhistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

public class TransactionHistory  extends LoadableComponent<TransactionHistory> {

	//Declarations
	private LoadableComponent<?> parent;
	private String confirmationNo;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Transaction history']") private WebElement lblTransactionHistory;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//table[@class='tranHistFilterOptions']") private WebElement tblTransactionFilterOption;
	@FindBy(id="tranHistSummaryTable") private WebElement tblTransactionHstSummary;
	@FindBy(id="tranHistContribSummaryTable") private WebElement tblTransactionHstContrSummary;
	@FindBy(id="tranHistContribDetlTable") private WebElement tblTransactionHstContrDetail;
	@FindBy(xpath="//table[@id='tranHistSummaryTable']//thead//tr") private WebElement hdrTransactionHstSummaryTable;
	@FindBy(xpath="//table[@id='tranHistContribSummaryTable']//thead//tr") private WebElement hdrTransactionHstContrTable;
	@FindBy(xpath="//table[@id='tranHistContribDetlTable']//thead//tr") private WebElement hdrTransactionHstContrDetailTable;
	@FindBy(xpath="//table[@id='tranHistSummaryTable']//tbody[@id='txnSummaryTbody']//tr") private List<WebElement> lstTransactionHstSummaryRows;
	@FindBy(xpath="//table[@id='tranHistContribSummaryTable']//tbody//tr") private List<WebElement> lstTransactionHstContrRows;
	@FindBy(xpath="//table[@id='tranHistContribDetlTable']//tbody//tr") private List<WebElement> lstTransactionHstContrDetailRows;
	@FindBy(xpath="//table[@id='tranHistSummaryTable']//*[@id='txnHistSummConfirmNbr']") private List<WebElement> lstConfirmationNumber;
	@FindBy(xpath="//div[@id='referenceTitle2']//*[contains(text(),'Contribution Details for Money Source')]") private WebElement lblContributionDetails;
	@FindBy(xpath="//table[@id='tranHistContribSummaryTable']//tbody//tr/td[1]") private WebElement txtConfirmationNo;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(xpath="*//td[@id='txnHistSummTxnDesc']") private List<WebElement> lstTransactionType;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/** Empty args constructor
	 * 
	 */
	public TransactionHistory() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public TransactionHistory(LoadableComponent<?> parent) {
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
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblTransactionHistory,true));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Transaction history");
		
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Transaction Filter Option Table")) {
			return this.tblTransactionFilterOption;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Summary Table")) {
			return this.tblTransactionHstContrSummary;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Detail Table")) {
			return this.tblTransactionHstContrDetail;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Summary Table Header")) {
			return this.hdrTransactionHstContrTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Detail Table Header")) {
			return this.hdrTransactionHstContrDetailTable;
		}
		// Log out
				if (fieldName.trim().equalsIgnoreCase("LOG OUT")
						|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
					return this.lnkLogout;
				}

		return null;
	}
	
	private List<WebElement> getWebElementList(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Summary Table")) {
			return this.lstTransactionHstContrRows;
		}
		if (fieldName.trim().equalsIgnoreCase("Transaction History Contr Details Table")) {
			return this.lstTransactionHstContrDetailRows;
		}
		return null;
	}
	
		
	public void clickConfirmationNumber(){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		if(lstConfirmationNumber.size()>=1){
//			Reporter.logEvent(Status.PASS, "verify confirmation number displayed", "Confirmation number is displayed",true);
//			confirmationNo=lstConfirmationNumber.get(0).getText().trim();
//			lstConfirmationNumber.get(0).click();
//			if(Web.isWebElementDisplayed(lblContributionDetails,true))
//				Reporter.logEvent(Status.PASS, "verify Contribution details displayed", "Contribution details displayed",true);
//			else
//				Reporter.logEvent(Status.FAIL, "verify Contribution details displayed", "Contribution details not displayed",true);
			for(int i=1;i<=lstConfirmationNumber.size();i++){
				System.out.println(lstTransactionType.get(i).getText());
				if(lstTransactionType.get(i).getText().equalsIgnoreCase("Additional Deposit")){
					confirmationNo=lstConfirmationNumber.get(i).getText().trim();
					System.out.println(confirmationNo);
					lstConfirmationNumber.get(i).click();
					if(Web.isWebElementDisplayed(lblContributionDetails,true))
						Reporter.logEvent(Status.PASS, "verify Contribution details displayed", "Contribution details displayed",true);
					else
						Reporter.logEvent(Status.FAIL, "verify Contribution details displayed", "Contribution details not displayed",true);
					break;
				}
			}
		}
		else
			Reporter.logEvent(Status.FAIL, "verify confirmation number displayed", "Confirmation number not displayed",true);
		
		
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
	
	public void verifytableHeaderNotEmpty(String tableName){
		 	
		 	Web.webdriver.switchTo().frame(iframeLegacyFeature);
		 	WebElement tableHeader = this.getWebElement(tableName);
		 	if(!tableHeader.getText().isEmpty())
		 		Reporter.logEvent(Status.PASS, "verify "+tableName+" Header displayed",tableName+" Header is displayed" , false);
			else
				Reporter.logEvent(Status.FAIL, "verify "+tableName+" Header displayed", tableName+" Header not displayed",true);
		 	Web.webdriver.switchTo().defaultContent();
	}

	public void verifyReferenceNumber(){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		if(txtConfirmationNo.getText().trim().equalsIgnoreCase(confirmationNo))
			Reporter.logEvent(Status.PASS, "Verify Reference Number displayed in Contribution Details Table" , "Expected: "+confirmationNo+" Actual: "+txtConfirmationNo.getText().trim(),false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Reference Number displayed in Contribution Details Table","Expected: "+confirmationNo+" Actual: "+txtConfirmationNo.getText().trim(),true);
		Web.webdriver.switchTo().defaultContent();
	}
	
}
