/**
 * 
 */
package pscBDD.planPage;

import java.util.List;
import lib.Reporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.Status;
import bdd_lib.Web;
import pscBDD.commonLib.CommonLib;
import pscBDD.homePage.HomePage;

/**
 * @author rvpndy
 *
 */
public class PlanPage extends LoadableComponent<PlanPage> {
	
	public PlanPage(){
		PageFactory.initElements(Web.getDriver(), this);
	}

	public PlanPage(WebDriver webDriver){
		PlanPage.webDriver = webDriver;
		commonLib = new CommonLib(webDriver);
		PageFactory.initElements(webDriver, this);
	}
	
	@Override
	protected void load() {
		wait = new WebDriverWait(webDriver,40);
		HomePage homepage = (HomePage) this.parent;
		new HomePage(webDriver).get();
		wait.until(ExpectedConditions.elementToBeClickable(tabPlan));
		act = new Actions(webDriver);
		act.moveToElement(tabPlan).click().build().perform();
		
		
	}

	@Override
	protected void isLoaded() throws Error {
		
		Assert.assertTrue(Web.isWebElementDisplayed(planOverviewBreadcrumb));
	}
	
	@FindBy(xpath=".//*[@id='main']/div/div[2]/i")
	private WebElement planOverviewBreadcrumb;
	
	@FindBy(xpath="//*[@id='newMenu']/li[1]/a")
	private WebElement tabPlan;
	
	@FindBy(xpath="//*[@id='newMenu']/li[1]/ul/li[2]/a")
	private WebElement investmentAndResults;
	
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/ul/li[2]/a/following-sibling::ul/li[1]/a")
	private List<WebElement> investmentAndPerformance;
	
	@FindBy(id="frameb")
	private WebElement planFrame;
	
	@FindBy(id="assetAllocationTab")
	private WebElement modelPortfolioAssetAllocation;
	

	@FindBy(xpath="//ul[@id='newMenu']//a[contains(text(),'Contacts')]")
	private WebElement linkContacts;
	
	@FindBy(xpath="//ul[@id='newMenu']//a[contains(text(),'contacts')]")
	private List<WebElement> contactsMenuItems;
	
	@FindBy(xpath="//div[@class='button-row']/button[text()='More']")
	private WebElement lnkMore;
	
	@FindBy(xpath="//span[text()='Managed account usage: my plan data']")
	private WebElement lblManagedAccount;
	
	@FindBy(xpath="//span[contains(text(),'Managed account')]/ancestor::div[1]/following-sibling::div")
	private WebElement imgMAXEnrollment;

	
	
	LoadableComponent<?> parent;
	public static WebDriver webDriver;
	CommonLib commonLib;
	WebDriverWait wait;
	Actions act;
	
	public boolean isInvestmentResultsDisplays() throws InterruptedException{
		act = new Actions(webDriver);
		act.moveToElement(tabPlan).click().build().perform();
		Thread.sleep(5000);
		if(investmentAndResults.isDisplayed())
			return true;
		return false;
	}
	
	public boolean isInvestmentResultsBreadcrumb() throws InterruptedException{
		wait = new WebDriverWait(webDriver,40);
		act = new Actions(webDriver);
		act.moveToElement(tabPlan).click().build().perform();
		Thread.sleep(2000);
		if(investmentAndResults.isDisplayed())
			act.moveToElement(investmentAndResults).click().build().perform();
		//Thread.sleep(2000);
		
		if(investmentAndPerformance.size()>0){
			/*for(WebElement ele : investmentAndPerformance){
				System.out.println(ele.getText());
				if(ele.findElement(By.tagName("a")).getText().contains("Investments & Performance")){
					Thread.sleep(2000);
					ele.click();
				}
				
			}
			Thread.sleep(2000);*/
			act.moveToElement(investmentAndPerformance.get(0)).click().build().perform();
		}
		wait.until(ExpectedConditions.visibilityOf(planOverviewBreadcrumb));
		if(planOverviewBreadcrumb.getText().contains("Investments & Results"))
			return true;
		else
			return false;
	}
	
	public boolean correctLabelForAssetAlocation(String label){
		commonLib = new CommonLib(webDriver);
		Web.FrameSwitchONandOFF(true, planFrame);
		//commonLib.FrameSwitchONandOFF(true, planFrame);
		if(commonLib.isWebElementDisplayed(modelPortfolioAssetAllocation, true)){
			String tabLabel = modelPortfolioAssetAllocation.getText();
			if(tabLabel.equalsIgnoreCase(label)){
				//commonLib.FrameSwitchONandOFF(false, planFrame);
				Web.FrameSwitchONandOFF(false);
				return true;
			}
		}
		//commonLib.FrameSwitchONandOFF(false, planFrame);
		Web.FrameSwitchONandOFF(false);
		return false;
	}
	

	public void clickOnContactsUnderPlanTab() {	
			try {
				Thread.sleep(3000);
				if (Web.isWebElementDisplayed(tabPlan,true)) {
					Web.clickOnElement(tabPlan);
					if (Web.isWebElementDisplayed(linkContacts,true))
						act = new Actions(Web.getDriver());
					    act.moveToElement(linkContacts).click().build().perform();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
	public boolean isEmpowerContactsMenu(String menuOption) {
		boolean flag=true;
		if(Web.isWebElementsDisplayed(contactsMenuItems,true)){
		//if(!Web.verifyDropDownOptionExists(linkContacts, menuOption, flag)){		
		if(Web.isWebElementsDisplayed(contactsMenuItems,true)){		
			int menuOptions = contactsMenuItems.size();
			for (int i = 0; i < menuOptions; i++) {
				String menuName = contactsMenuItems.get(i).getText().trim();
				if (menuName.equalsIgnoreCase(menuOption)){
					flag=false;
			        break;
				}						
			}
		}					
	}
		return flag;
	}	
	
	public void clickOnMOre(){
		Web.FrameSwitchONandOFF(true, planFrame);
		if (Web.isWebElementDisplayed(lnkMore,true))
			Web.clickOnElement(lnkMore);
	}
	
	public boolean isMAXEnrollmentHighChart(){
		try {
			Web.FrameSwitchONandOFF(true, planFrame);
			if((Web.isWebElementDisplayed(lblManagedAccount, true)) && (Web.isWebElementDisplayed(imgMAXEnrollment, true)))
			return true;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean verifyHighChart(){
		try {
			Web.FrameSwitchONandOFF(true, planFrame);
			if((!Web.isWebElementDisplayed(lblManagedAccount, false)) && (!Web.isWebElementDisplayed(imgMAXEnrollment, false)))
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	
}
