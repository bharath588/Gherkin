package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.general.LeftNavigationBar;

public class Brokerage extends LoadableComponent<Brokerage>{
private LoadableComponent<?> parent;
	
@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Brokerage']") private WebElement lblBrokerage;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//table[@class='default']") private WebElement tblBrokerage;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableSubtitle']") private WebElement hdrBrokerageTable;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']") private WebElement brokerageTableData;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']/td[1]/a") private WebElement lnkProviderName;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']/td[2]/img") private WebElement imgEnroll;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']/td[3]/a") private WebElement lnkTransferInto;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']/td[4]/a") private WebElement lnkTransferFrom;
	@FindBy(xpath="//table[@class='default']/tbody/tr[@class='tableData']/td[5]/img") private WebElement imgPdf;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/** Empty args constructor
	 * 
	 */
	public Brokerage() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public Brokerage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblBrokerage,true),"Brokerage Page is Not Loaded\n");
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
		};

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		
		String userLogedIn = this.lblUserName.getText();
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));			
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblBrokerage));
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(false,"Logging in with new User");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Brokerage");
		
	}
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Brokerage Table")) {
			return this.tblBrokerage;
		}
		if (fieldName.trim().equalsIgnoreCase("Brokerage Table Header")) {
			return this.hdrBrokerageTable;
		}
		if (fieldName.trim().equalsIgnoreCase("Provider Name")) {
			return this.lnkProviderName;
		}
		if (fieldName.trim().equalsIgnoreCase("Enroll image")) {
			return this.imgEnroll;
		}
		if (fieldName.trim().equalsIgnoreCase("Transfer into sda link")) {
			return this.lnkTransferInto;
		}
		if (fieldName.trim().equalsIgnoreCase("Transfer from sda link")) {
			return this.lnkTransferFrom;
		}
		if (fieldName.trim().equalsIgnoreCase("PDF image")) {
			return this.imgPdf;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}	
		return null;
	}
	
	public void verifyBrokerageTableDisplayed(){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
	 
		
	 	
	 	if (Web.isWebElementDisplayed(tblBrokerage,true)) {
			Reporter.logEvent(Status.PASS,"Verify brokerage table is displayed","Table displayed", true);
			
			if(StringUtils.containsIgnoreCase(hdrBrokerageTable.getText(), "PROVIDER NAME ENROLL TRANSFER INTO SDA TRANSFER FROM SDA FACT SHEET"))
				Reporter.logEvent(Status.PASS, "Verify table header is displayed","Expected Header: PROVIDER NAME ENROLL TRANSFER INTO SDA TRANSFER FROM SDA FACT SHEET \n Actual: "+hdrBrokerageTable.getText(), true);
			else
				Reporter.logEvent(Status.FAIL, "Verify table header is displayed","Expected Header: PROVIDER NAME ENROLL TRANSFER INTO SDA TRANSFER FROM SDA FACT SHEET \n Actual: "+hdrBrokerageTable.getText(),true);
			
		} else
			Reporter.logEvent(Status.FAIL,"Verify brokerage table is displayed","Table is not displayed", true);
	 	Web.webdriver.switchTo().defaultContent();
		
		
		
	}
	
	public void verifyBrokerageTableDataDisplayed(String dataName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		WebElement data = this.getWebElement(dataName);
		
		if(Web.isWebElementDisplayed(data))
			Reporter.logEvent(Status.PASS, "Verify "+dataName+" is displayed", dataName+" is displayed",false);
		else
			Reporter.logEvent(Status.PASS, "Verify "+dataName+" is displayed", dataName+" is not displayed",true);
	
		Web.webdriver.switchTo().defaultContent();
	}

}
