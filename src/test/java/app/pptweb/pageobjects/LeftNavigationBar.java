package app.pptweb.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import app.pptweb.common.Reporter;
import app.pptweb.common.Reporter.Status;
import app.pptweb.common.common;

import org.testng.Assert;

public class LeftNavigationBar extends LoadableComponent<LeftNavigationBar> {
	
	
	//Declarations
	LoadableComponent<?> parent;
	private String strLinkText = "";
//	private static boolean waitforLoad = false;
	
	@FindBy(xpath=".//*[@role='navigation' and .//h3]") private WebElement weLeftNavSection;
	private By lnkLeftNavItem;
	
	/** Empty args constructor
	 * 
	 */
	public LeftNavigationBar() {
		this.parent = new MyAccountsPage();
		PageFactory.initElements(common.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public LeftNavigationBar(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(common.webdriver, this);
	}
	

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(common.isWebElementDisplayed(weLeftNavSection));
//		if (!common.isWebElementDisplayed(this.weLeftNavSection,LeftNavigationBar.waitforLoad)) {
//			LeftNavigationBar.waitforLoad = true;
//			throw new Error("Left navigation menu bar is not displayed");
//		} else{
//			LeftNavigationBar.waitforLoad = false;
//		}
	}

	@Override
	protected void load() {
		//boolean paramFlag = false;
		
		this.parent.get();
//		if(new LandingPage().getNoOfPlansFromDB()<=2){
//			System.out.println("inside");
//			((LandingPage) this.parent).dismissPopUps(true,true);
//			WebActions.clickOnElement(new LandingPage(), "MY ACCOUNTS");
//		}
//		else{
//			Set<String> paramNames = TestDataContainer.getParameterNames();
//			
//			for(String param: paramNames){
//				if (param.equalsIgnoreCase("groupAccountID")) {
//					paramFlag = true;
//				}
//			}
//			
//			if (paramFlag==true) {
//				((MyAccountsPage) this.parent).clickPlanNameByGAID(
//						Stock.globalTestdata.get("groupAccountID"));
//			} else {
				((MyAccountsPage) this.parent).clickPlanNameByGAID();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		}
		
		
	
	
	/** Method to click on the specified link in Left navigation bar
	 * 
	 * @param linkName - Link name as it is displayed on the page
	 * @return boolean - <b>true</b> if link is successfully found and clicked. <b>false</b> otherwise.
	 */
	public boolean clickNavigationLink(String linkName) {
		boolean success = false;
		
		if (linkName.trim().equalsIgnoreCase("ACCOUNT INFORMATION")) {
			strLinkText = "Account Information";
		} else if (linkName.trim().equalsIgnoreCase("PAYCHECK CONTRIBUTIONS")) {
			strLinkText = "Paycheck Contributions";
		} else if (linkName.trim().equalsIgnoreCase("INVESTMENTS")) {
			strLinkText = "Investments";
		} else if (linkName.trim().equalsIgnoreCase("LOANS & WITHDRAWALS")) {
			strLinkText = "Loans & Withdrawals";
		} else if (linkName.trim().equalsIgnoreCase("PLAN INFORMATION")) {
			strLinkText = "Plan Information";
		} else {
			strLinkText = linkName.trim();
		}
		
		lnkLeftNavItem = By.linkText(strLinkText);
		List<WebElement> leftNavLink = weLeftNavSection.findElements(lnkLeftNavItem);
		
		if (leftNavLink.size() > 0) {
			leftNavLink.get(0).click();
			success = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Click the specified link on left navigation bar", "'" + linkName + "' not found", false);
		}
		
		return success;
	}
}
