package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	private String lnkRequestLoan="//li[@class='ng-scope']//a[text()[normalize-space()='Request a loan']]";
	

	/**
	 * Empty args constructor
	 * 
	 */
	public LeftNavigationBar() {
		this.parent = new MyAccountsPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public LeftNavigationBar(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
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
			Web.clickOnElement(new LandingPage(), "MY ACCOUNTS");
		
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
		} else if (linkName.trim().equalsIgnoreCase("REQUEST A WITHDRAWAL")) {
			strLinkText = "Request a withdrawal";

		} else {
			strLinkText = linkName.trim();
		}
		if(linkName.equalsIgnoreCase("REQUEST A LOAN")){
			lnkLeftNavItem=By.xpath(strLinkText);
		}
		else
		lnkLeftNavItem = By.linkText(strLinkText);
		List<WebElement> leftNavLink = weLeftNavSection
				.findElements(lnkLeftNavItem);

		if (leftNavLink.size() > 0) {
			Actions mouse = new Actions(Web.webdriver);
			mouse.moveToElement(leftNavLink.get(0)).build().perform();
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mouse.click().build().perform();
			//leftNavLink.get(0).click();;
			success = true;
		} else {
			Reporter.logEvent(Status.INFO,
					"Click the specified link on left navigation bar", "'"
							+ linkName + "' not found", false);
		}

		return success;
	}
}
