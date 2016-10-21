package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;





import java.util.Set;

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

public class InvestmentLineup extends LoadableComponent<InvestmentLineup>{

	//Declarations
	private LoadableComponent<?> parent;
	String parentWindow;
	

	//@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	 @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Investment lineup']") private WebElement lblInvestmentlineup;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//a[text()[normalize-space()='Options']]") private WebElement tabOptions;
	@FindBy(id="legacyFeatureIframe") private WebElement iframeLegacyFeature;
	@FindBy(xpath="//input[@id='ALL']") private WebElement radShowAll;
	@FindBy(id="fundOverviewWrapperTable") private WebElement tblFundOverviewTable;
	@FindBy(xpath="//table[@id='fundOverviewWrapperTable']/thead/tr") private WebElement hdrFundOverviewTable;
	@FindBy(xpath="//table[@id='fundOverviewWrapperTable']/tbody/tr/td[1]") private List<WebElement> lstInvestmentNames;
	@FindBy(xpath="//table[@id='fundOverviewWrapperTable']/tbody/tr/td[4]") private List<WebElement> lstOnlineProspectus;
	@FindBy(xpath="//table[@id='fundOverviewWrapperTable']/tbody/tr/td[4]/a") private List<WebElement> lstlnkOnlineProspectus;
	@FindBy(xpath="//table[@id='fundOverviewWrapperTable']/tbody/tr") private List<WebElement> lstFundOverviewTableRows;
	@FindBy(xpath="//span[@class='legacyFundDocumentTitleLabel']") private WebElement lblFundDocTitle;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	
	/** Empty args constructor
	 * 
	 */
	public InvestmentLineup() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public InvestmentLineup(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblInvestmentlineup,true),"Investment LineUp Page is Not Loaded");
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
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));	
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblInvestmentlineup),"Investment LineUp Page is Not Loaded\n");
		} else {
			this.lnkLogout.click();
			Assert.assertTrue(false,"Logging in with new User");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Investment lineup");
		Web.waitForPageToLoad(Web.webdriver);
		lib.Web.isWebElementDisplayed(lblInvestmentlineup,true);

	}
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Options tab")) {
			return this.tabOptions;
		}
		return null;
	}
	
	public void navigateToTab(String tabName){
		WebElement tab = this.getWebElement(tabName);
		tab.click();
		
	}
	
	public void viewProspectus(){
		String investmentName;
		Web.webdriver.switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(radShowAll);
		radShowAll.click();
		if(Web.isWebElementDisplayed(tblFundOverviewTable, true)){
			Reporter.logEvent(Status.PASS, "Verify if Fund Overview Table is displayed", "Table is displayed",true);
			if(hdrFundOverviewTable.getText().contains("INVESTMENT NAME ASSET CLASS INVESTMENT OVERVIEW ONLINE PROSPECTUS TICKER SYMBOL"))
				Reporter.logEvent(Status.PASS, "Verify if Fund Overview Table HEADER is displayed", "Expected : INVESTMENT NAME ASSET CLASS INVESTMENT OVERVIEW ONLINE PROSPECTUS TICKER SYMBOL \n Actual : "+hdrFundOverviewTable.getText(),false);
			else
				Reporter.logEvent(Status.PASS, "Verify if Fund Overview Table HEADER is displayed", "Expected : INVESTMENT NAME ASSET CLASS INVESTMENT OVERVIEW ONLINE PROSPECTUS TICKER SYMBOL \n Actual : "+hdrFundOverviewTable.getText(),true);
			
			int noOfRows=lstFundOverviewTableRows.size();
			for(int i=1;i<noOfRows;i++){
				
				if(lstOnlineProspectus.get(i).getText().contains("View")){
					investmentName=lstInvestmentNames.get(i).getText();
					System.out.println(investmentName);
					lstlnkOnlineProspectus.get(i).click();
					if(switchToWindow("Prospectus Express")){
						System.out.println(lblFundDocTitle.getText());
						Web.waitForElement(lblFundDocTitle);
						if(lblFundDocTitle.getText().contains(investmentName))
							Reporter.logEvent(Status.PASS, "Verify if we are able to view the prospectus", "Prospectus displayed for : "+lblFundDocTitle.getText()+"\n Expected : "+investmentName,true);
						else
							Reporter.logEvent(Status.FAIL, "Verify if we are able to view the prospectus", "Prospectus displayed for : "+lblFundDocTitle.getText()+"\n Expected : "+investmentName,true);
						 Web.webdriver.close(); //closing child window
				         Web.webdriver.switchTo().window(parentWindow);
				         break;
					}
					else
						Reporter.logEvent(Status.FAIL, "Verify if we are able to view the prospectus", "Prospectus not displayed",true);
					
				}

			
		}
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify if Fund Overview Table is displayed", "Table not displayed",true);
		
	}
	
	public boolean switchToWindow(String title){
		boolean windowFound=false;
		parentWindow = Web.webdriver.getWindowHandle();
		Set<String> handles =  Web.webdriver.getWindowHandles();
		   for(String windowHandle  : handles)
		       {
		       if(!windowHandle.equals(parentWindow)){
		    	   if(Web.webdriver.switchTo().window(windowHandle).getTitle().equalsIgnoreCase(title)){
		    		   windowFound=true;
		    		   break;
		    	   } 
//		    	   Web.webdriver.close(); //closing child window
//		           Web.webdriver.switchTo().window(parentWindow); //cntrl to parent window
		       }
		     
		    }
		 return windowFound;  
	}
	
}
