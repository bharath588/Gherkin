package pptweb.liat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pptweb.appUtils.Common;
import pptweb.landingpage.LandingPage;
import core.framework.Globals;



public class HowDoICompare extends LoadableComponent<HowDoICompare> {
	
	//Declarations
		private LoadableComponent<?> parent;
		private static boolean waitforLoad = false;
		
		@FindBy(xpath=".//*[text()='How do I compare']") private WebElement lblHowDoICompare;
		@FindBy(xpath=".//*[text()='How do I compare to other people like me?']") private WebElement lblHwDoCmpOthrPplLikMe;
		@FindBy(xpath="//button[@ng-click='clickViewDetailsEvent()']/span[text()[normalize-space()='View details']]") private WebElement btnViewDetails;
		@FindBy(xpath="//div[@class='how-do-compare-container']//p") private WebElement lblAdditionalContribution;
		@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
		@FindBy(linkText="Log out") private WebElement lnkLogout;
		@FindBy(xpath="//td[@class='target-my-peers']//div[contains(text(),'My peers')]") private WebElement lblMyPeers;
		@FindBy(xpath="//td[@class='target-top-peers']//strong") private WebElement lblTopPeers;
		@FindBy(xpath="//td[contains(text(),'Me')]") private WebElement lblMe;
		@FindBy(xpath=".//*[@id='my-peers-pog-inner']") private WebElement circleMyPeers;
		@FindBy(xpath=".//*[@id='top-peers-pog-inner']") private WebElement circleTopPeers;
		@FindBy(xpath=".//*[@id='progress-to-goal']/div/pw-radial-chart") private WebElement circleMe;
		@FindBy(xpath=".//*[@id='ageRange']") private WebElement drpdwnAge;
		@FindBy(xpath=".//*[@id='salaryRange']") private WebElement drpdwnSalary;
		@FindBy(xpath="//tr[@class='balances']//td") private List<WebElement> lstBalance;
		@FindBy(xpath="//tr[@class='contribution-rates']//td") private List<WebElement> lstContributionRate;
		@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
		
		
		
		
		
		
		/**
		 * Default Constructor
		 */
		public HowDoICompare()
		{
			this.parent=new LandingPage();
			PageFactory.initElements(lib.Web.getDriver(), this);
		}
		
		
		/**
		 * Arg Constructor
		 * @param parent
		 */
		public HowDoICompare(LoadableComponent<?> parent)
		{
			this.parent=parent;
			PageFactory.initElements(lib.Web.getDriver(), this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"How Do I Compare Page is Not Loaded");
			String ssn = Stock.GetParameterValue("userName");
			String userFromDatasheet = null;
			ResultSet strUserInfo=null;
			if(Stock.getConfigParam(Globals.GC_COLNAME_TEST_ENV).contains("PROD"))
			{
				userFromDatasheet=Stock.GetParameterValue("lblUserName");
			}
			else{
			
			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			
			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			}
			
			String userLogedIn = this.lblUserName.getText();
			if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
				Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
				if (!Web.isWebElementDisplayed(lblHwDoCmpOthrPplLikMe,HowDoICompare.waitforLoad)) {
					HowDoICompare.waitforLoad = true;
					throw new Error("'How Do I Compare ' page is not loaded");
				}else{
					HowDoICompare.waitforLoad = false;
				}
			} else {
				this.lnkLogout.click();
				Web.waitForElement(this.btnLogin);
				Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			}
			
		}

		@Override
		protected void load() {
			this.parent.get();
			
			//((LandingPage) this.parent).dismissPopUps(true,true);
			this.lblHowDoICompare.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
		}
		

/**<pre>Method to read the projected income value from the retirement income page
 * 
 *  
 *  @return - (float) projected retirement income after the string is parsed into float.
 * @throws Exception
 */
private WebElement getWebElement(String fieldName) {

	if (fieldName.trim().equalsIgnoreCase("Label MyPeers")) {
		return this.lblMyPeers;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Label TopPeers")) {
		return this.lblTopPeers;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Label Me")) {
		return this.lblMe;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Goal MyPeers")) {
		return this.circleMyPeers;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Goal TopPeers")) {
		return this.circleTopPeers;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Goal Me")) {
		return this.circleMe;
	}
	if (fieldName.trim().equalsIgnoreCase("DropDown Age")) {
		return this.drpdwnAge;
	}
	if (fieldName.trim().equalsIgnoreCase("DropDown Salary")) {
		return this.drpdwnSalary;
	}
		
	return null;

}
		
		/** Method to verify the details in 'View Details' section.
	     * 
	     * 
	     */
		public void verifyViewDetails(){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
			System.out.println(Web.isWebElementDisplayed(this.btnViewDetails));
			if (Web.isWebElementDisplayed(this.btnViewDetails)) {
				
				this.btnViewDetails.click();
				
				if (lblAdditionalContribution.getText().contains("Current")) {
					Reporter.logEvent(Status.PASS, "Verify 'Current contributions:' Text",
							"User was able to click the view details button and check the 'Current contributions' text", true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify 'Current contributions:' Text",
							"User was not able to check the 'Current contributions:' text, please check if the user has any contributions", true);
				}
								
			}else{
				throw new Error("View Details button on HDIC page not displayed, Verify the SSN(Test Data) used");
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		/** Method to get the list of values for Contribution Rate.
	     * 
	     * 
	     */
		public List<String> GetValuesofContributionRate(){
			List<String> contributionRate= new ArrayList<String>();
			contributionRate.clear();
			if (Web.isWebElementDisplayed(this.lblMyPeers)) {
				
				for(int i=1;i<lstContributionRate.size();i++){
					lstContributionRate.get(i).getText().trim();
					contributionRate.add(lstContributionRate.get(i).getText().trim());
				}
			}
			return contributionRate;
		}
		/** Method to get the list of values for Balance.
	     * 
	     * 
	     */
		public List<String> GetValuesofBalance(){
			List<String> balance = new ArrayList<String>();
			balance.clear();
			if (Web.isWebElementDisplayed(this.lblMyPeers)) {
				
				for(int i=1;i<lstBalance.size();i++){
					balance.add(lstBalance.get(i).getText().trim());
				}
			}
			return balance;
		}

}
