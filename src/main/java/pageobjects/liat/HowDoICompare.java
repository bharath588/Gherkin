package pageobjects.liat;

import java.sql.ResultSet;
import java.sql.SQLException;

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
import pageobjects.landingpage.LandingPage;



public class HowDoICompare extends LoadableComponent<HowDoICompare> {
	
	//Declarations
		private LoadableComponent<?> parent;
		private static boolean waitforLoad = false;
		
		@FindBy(xpath=".//*[text()='How do I compare']") private WebElement lblHowDoICompare;
		@FindBy(xpath=".//*[text()='How do I compare to other people like me?']") private WebElement lblHwDoCmpOthrPplLikMe;
		@FindBy(xpath="//button[@ng-click='clickViewDetailsEvent()']/span[@class='ng-binding ng-scope' and text()[normalize-space()='View Details']]") private WebElement btnViewDetails;
		@FindBy(xpath=".//*[contains(text(),'Additional Contributions')]") private WebElement lblAdditionalContribution;
		@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
		@FindBy(linkText="Log out") private WebElement lnkLogout;
		
		/**
		 * Default Constructor
		 */
		public HowDoICompare()
		{
			this.parent=new LandingPage();
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		
		/**
		 * Arg Constructor
		 * @param parent
		 */
		public HowDoICompare(LoadableComponent<?> parent)
		{
			this.parent=parent;
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			String ssn = Stock.GetParameterValue("userName");
			String userFromDatasheet = null;
			ResultSet strUserInfo=null;
			if(Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD"))
			{
				userFromDatasheet=Stock.GetParameterValue("lblUserName");
			}
			else{
			
			try {
				strUserInfo = Common.getParticipantInfoFromDataBase((ssn.substring(
						0,9)));
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
				Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			}
			
		}

		@Override
		protected void load() {
			this.parent.get();
			
			((LandingPage) this.parent).dismissPopUps(true,true);
			this.lblHowDoICompare.click();
			
			
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
			System.out.println(Web.isWebElementDisplayed(this.btnViewDetails));
			if (Web.isWebElementDisplayed(this.btnViewDetails)) {
				
				this.btnViewDetails.click();
				if (this.lblAdditionalContribution.getText().contains("Additional Contributions:")) {
					Reporter.logEvent(Status.PASS, "Verify 'Additional Conctribution' Text",
							"User was able to click the view details button and check the 'Additional Conctribution' text", true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify 'Additional Conctribution' Text",
							"User was able to check the 'Additional Conctribution' text, please check if the user has any contributions", true);
				}
								
			}else{
				throw new Error("View Details button on HDIC page not desplayed, Verify the SSN(Test Data) used");
			}
		}

}