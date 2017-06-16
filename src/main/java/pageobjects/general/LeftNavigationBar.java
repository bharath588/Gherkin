package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.landingpage.LandingPage;

public class LeftNavigationBar extends LoadableComponent<LeftNavigationBar> {

	// Declarations
	LoadableComponent<?> parent;
	private String strLinkText = "";
	@FindBy(xpath = ".//a[text()[normalize-space()='Prior plan contributions']]")
	private WebElement lnkPriorPlanContributions;

	@FindBy(xpath = ".//*[@role='navigation' and .//h3]")
	private WebElement weLeftNavSection;
	private By lnkLeftNavItem;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	private String lnkRequestLoan="//a[text()[normalize-space()='Request a loan']]";
	private String lnkBrokerage="//a[text()[normalize-space()='Brokerage']]";
	@FindBy(xpath = ".//*[@class='plan']/*[starts-with(@id,'ga_')]")
	private WebElement lnkPlanName;
	@FindBy(xpath = "//span[@class='plan']//a")
	private List<WebElement> lstlnkPlanName;
	private String lnkStatementDocument="//a[text()[normalize-space()='Statements and documents']]";
	

	/**
	 * Empty args constructor
	 * 
	 */
	public LeftNavigationBar() {
		this.parent = new MyAccountsPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public LeftNavigationBar(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {

		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertTrue(Web.isWebElementDisplayed(weLeftNavSection, true),"Left Navigation Bar is Not Loaded");

	}

	@Override
	protected void load() {
		// boolean paramFlag = false;

		this.parent.get();
		//((LandingPage) this.parent).dismissPopUps(true, true);
		// if(new
		// LandingPage().getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))<=2){
		// System.out.println("inside");
		// ((LandingPage) this.parent).dismissPopUps(true,true);
		try {
			if(!Web.isWebElementDisplayed(weLeftNavSection)){
				if(Web.isWebElementDisplayed(new LandingPage(), "MY ACCOUNTS")){
			Web.clickOnElement(new LandingPage(), "MY ACCOUNTS");
			}
				
			else{
				if(Web.isWebElementsDisplayed(lstlnkPlanName))
				Web.clickOnElement(lstlnkPlanName.get(0));
				
				
			}
			}
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		// else
		// ((MyAccountsPage) this.parent).clickPlanNameByGAID();

		// else{
		// Set<String> paramNames = TestDataContainer.getParameterNames();
		//
		// for(String param: paramNames){
		// if (param.equalsIgnoreCase("groupAccountID")) {
		// paramFlag = true;
		// }
		// }

		// if (paramFlag==true) {
		// ((MyAccountsPage) this.parent).clickPlanNameByGAID(
		// Stock.GetParameterValue("groupAccountID"));
		// } else {
		// ((MyAccountsPage) this.parent).clickPlanNameByGAID();
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
	}

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("Prior plan contributions")) {
			return this.lnkPriorPlanContributions;
		}
		if (fieldName.trim().equalsIgnoreCase("Left Navigation Bar")) {
			return this.weLeftNavSection;
		}

		return null;
	}

	/**
	 * Method to click on the specified link in Left navigation bar
	 * 
	 * @param linkName
	 *            - Link name as it is displayed on the page
	 * @return boolean - <b>true</b> if link is successfully found and clicked.
	 *         <b>false</b> otherwise.
	 * @throws InterruptedException 
	 */
	public boolean clickNavigationLink(String linkName)  {
		boolean success = false;
		Actions mouse = new Actions(Web.getDriver());
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
		} else if (linkName.trim().equalsIgnoreCase("PRIOR PLAN CONTRIBUTIONS")) {
			strLinkText = "Prior plan contributions";
		} else if (linkName.trim().equalsIgnoreCase("RATE OF RETURN")) {
			strLinkText = "Rate of return";
		} else if (linkName.trim().equalsIgnoreCase("REQUEST A LOAN")) {
			strLinkText=lnkRequestLoan;
		} else if (linkName.trim().equalsIgnoreCase("BROKERAGE")) {
			strLinkText=lnkBrokerage;
		} else if (linkName.trim().equalsIgnoreCase("REQUEST A WITHDRAWAL")) {
			strLinkText = "Request a withdrawal";
		} else if (linkName.trim().equalsIgnoreCase("STATEMENTS AND DOCUMENTS")) {
			strLinkText = lnkStatementDocument;

		} else {
			strLinkText = linkName.trim();
		}
		if(linkName.equalsIgnoreCase("REQUEST A LOAN")){
			lnkLeftNavItem=By.xpath(strLinkText);
			WebElement leftNavLink = weLeftNavSection.findElement(lnkLeftNavItem);
			mouse.moveToElement(leftNavLink).keyDown(Keys.SHIFT).click(leftNavLink).keyUp(Keys.SHIFT).build().perform();
			//mouse.moveToElement(leftNavLink).clickAndHold(leftNavLink).build().perform();
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mouse.release().build().perform();
			
		}
		else if(linkName.equalsIgnoreCase("Brokerage")){
			lnkLeftNavItem=By.xpath(strLinkText);
			WebElement leftNavLink = weLeftNavSection.findElement(lnkLeftNavItem);
			mouse.moveToElement(leftNavLink).keyDown(Keys.SHIFT).click(leftNavLink).keyUp(Keys.SHIFT).build().perform();
			
			//mouse.moveToElement(leftNavLink).clickAndHold(leftNavLink).build().perform();
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mouse.release().build().perform();
			
			
		}
		else if(linkName.equalsIgnoreCase("Statements and documents")){
			lnkLeftNavItem=By.xpath(strLinkText);
			WebElement leftNavLink = weLeftNavSection.findElement(lnkLeftNavItem);
			mouse.moveToElement(leftNavLink).keyDown(Keys.SHIFT).click(leftNavLink).keyUp(Keys.SHIFT).build().perform();
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mouse.release().build().perform();
			
			
		}
		else {
		lnkLeftNavItem = By.linkText(strLinkText);
		List<WebElement> leftNavLink = weLeftNavSection
				.findElements(lnkLeftNavItem);

		if (leftNavLink.size() > 0) {
		
			mouse.moveToElement(leftNavLink.get(0)).build().perform();
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mouse.click().build().perform();
			//leftNavLink.get(0).click();;
			success = true;
		} 
		else {
			Reporter.logEvent(Status.INFO,
					"Click the specified link on left navigation bar", "'"
							+ linkName + "' not found", false);
		}
		}

		return success;
	}
	
	
	/**
	 * Method to click on the specified link in Left navigation bar
	 * 
	 * @param linkName
	 *            - Link name as it is displayed on the page
	 * @return boolean - <b>true</b> if link is  found .
	 *         <b>false</b> otherwise.
	 * @throws InterruptedException 
	 */
	public boolean isLeftNavigationLinkDisplayed(String linkName)  {
		boolean success = false;
		Actions mouse = new Actions(Web.getDriver());
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
		} else if (linkName.trim().equalsIgnoreCase("PRIOR PLAN CONTRIBUTIONS")) {
			strLinkText = "Prior plan contributions";
		} else if (linkName.trim().equalsIgnoreCase("RATE OF RETURN")) {
			strLinkText = "Rate of return";
		} else if (linkName.trim().equalsIgnoreCase("REQUEST A LOAN")) {
			strLinkText=lnkRequestLoan;
		} else if (linkName.trim().equalsIgnoreCase("BROKERAGE")) {
			strLinkText=lnkBrokerage;
		} else if (linkName.trim().equalsIgnoreCase("REQUEST A WITHDRAWAL")) {
			strLinkText = "Request a withdrawal";
		} else if (linkName.trim().equalsIgnoreCase("STATEMENTS AND DOCUMENTS")) {
			strLinkText = lnkStatementDocument;

		} else {
			strLinkText = linkName.trim();
		}
		lnkLeftNavItem = By.linkText(strLinkText);
		try{
		WebElement leftNavLink = weLeftNavSection.findElement(lnkLeftNavItem);
		
        if(Web.isWebElementDisplayed(leftNavLink,true))
			
			success = true;
		
		}
		catch(NoSuchElementException e){
			success = false;
		}
		

		return success;
	}
}
