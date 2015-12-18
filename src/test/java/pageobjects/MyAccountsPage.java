package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import app.common.Reporter;
import app.common.common;
import app.common.Reporter.Status;




import org.testng.Assert;

import pageobjects.landingpage.LandingPage;

public class MyAccountsPage extends LoadableComponent<MyAccountsPage> {

	//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath=".//h1[text()='My Accounts']") private WebElement hdrMyAccounts;
	@FindBy(xpath=".//*[@class='plan']/*[starts-with(@id,'ga_')]") private List<WebElement> lstLnkPlanName;
	
	/** Empty args constructor
	 * 
	 */
	public MyAccountsPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(common.webdriver, this);
	}
	
	/** Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public MyAccountsPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(common.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(common.isWebElementDisplayed(hdrMyAccounts));
//		boolean isDisplayed = common.isWebElementDisplayed(this.hdrMyAccounts,MyAccountsPage.waitforLoad);
//		if (!isDisplayed) {
//			MyAccountsPage.waitforLoad=true;
//			throw new Error("'My Accounts' header is not displayed.\nUser is not on My Accounts page.");
//		}else{
//			MyAccountsPage.waitforLoad=false;
//		}
	}

	@Override
	protected void load() {
		LandingPage land = (LandingPage) this.parent;
		
		this.parent.get();
		((LandingPage) this.parent).dismissPopUps(true,true);
		common.clickOnElement(land, "MY ACCOUNTS");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		
		return null;
	}
	
	
	/** method to click on Plan name link which corresponds to the specified group account ID
	 * 
	 * @param groupAccountID - Example: 95301-01
	 * @return boolean - <b>true</b> if successful in clicking specified link. <b>false</b> otherwise.
	 */
	public boolean clickPlanNameByGAID(String... groupAccountID) {
		boolean success = false;
		int planCount = this.lstLnkPlanName.size();
		String actualGAID = "";
		WebElement currElement;
		
		if (groupAccountID.length==0) {
			currElement = this.lstLnkPlanName.get(0);
			currElement.click();
			success = true;
		} else {
			for (int iCntr = 0; iCntr < planCount; iCntr++) {
				currElement = this.lstLnkPlanName.get(iCntr);
				actualGAID = currElement.getAttribute("id");
				
				if (actualGAID.trim().equals("ga_" + groupAccountID[0].trim())) {
					currElement.click();
					success = true;
					break;
				}
			}
		}
				
		return success;
	}

}
