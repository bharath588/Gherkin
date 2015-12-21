package pageobjects.liat;

import lib.Reporter;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.landingpage.LandingPage;



public class HowDoICompare extends LoadableComponent<HowDoICompare> {
	
	//Declarations
		private LoadableComponent<?> parent;
		private static boolean waitforLoad = false;
		
		@FindBy(xpath=".//*[text()='How do I compare']") private WebElement lblHowDoICompare;
		@FindBy(xpath=".//*[text()='How do I compare to other people like me?']") private WebElement lblHwDoCmpOthrPplLikMe;
		@FindBy(xpath="//button[@ng-click='clickViewDetailsEvent()']/span[@class='ng-binding ng-scope' and text()[normalize-space()='View Details']]") private WebElement btnViewDetails;
		@FindBy(xpath=".//*[contains(text(),'Additional Contributions')]") private WebElement lblAdditionalContribution;
		
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
			if (!Web.isWebElementDisplayed(lblHwDoCmpOthrPplLikMe,HowDoICompare.waitforLoad)) {
				HowDoICompare.waitforLoad = true;
				throw new Error("'How Do I Compare ' page is not loaded");
			}else{
				HowDoICompare.waitforLoad = false;
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
