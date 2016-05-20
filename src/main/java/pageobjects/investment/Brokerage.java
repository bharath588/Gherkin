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
import pageobjects.general.LeftNavigationBar;

public class Brokerage extends LoadableComponent<Brokerage>{
private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
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
	@FindBy(xpath = ".//div[@class='container']/span[@ng-if='accuLogoLoaded']/img")
	private WebElement lblSponser;
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
			sponser = Globals.GC_DEFAULT_SPONSER;
		}
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)
				&& Common.isCurrentSponser(sponser)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblBrokerage,true));
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
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
			
		return null;
	}
	
	public void verifyBrokerageDataDisplayed(String dataName){
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
	 	WebElement data = this.getWebElement(dataName);
		
		if(Web.isWebElementDisplayed(data))
			Reporter.logEvent(Status.PASS, "Verify "+dataName+" is displayed", dataName+" is displayed",false);
		else
			Reporter.logEvent(Status.PASS, "Verify "+dataName+" is displayed", dataName+" is not displayed",true);
	}

}
