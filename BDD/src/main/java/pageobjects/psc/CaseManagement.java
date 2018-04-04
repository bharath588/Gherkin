/**
 * 
 */
package pageobjects.psc;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lib.CommonLib;
import lib.Web;







import reporter.Reporter;
import lib.XL_ReadWrite;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;



import com.aventstack.extentreports.Status;

import stepDefinitions.HomeStepDefinitions;
import core.framework.Globals;
import annotations.FindBy;

/**
 * @author rvpndy
 *
 */
public class CaseManagement extends LoadableComponent<CaseManagement> {

	@FindBy(how=How.XPATH,using=".//*[@id='main']/div/div[2]")
	private WebElement breadCrumb;

	@FindBy(how=How.BUTTON_TEXT,using="View all case history")
	private WebElement viewAllCaseHistory;

	@FindBy(how=How.ID,using="frameb")
	private WebElement caseIframe;

	@FindBy(how=How.ANGULAR_ATTRIBUTE,using="click",text="exportToExcel")
	private WebElement excelLink;

	@FindBy(how=How.XPATH,using=".//*[@id='newMenu']/li[1]/a")
	private WebElement planMenu;

	@FindBy(how=How.XPATH,using=".//*[@id='newMenu']/li[1]/ul/li//a")
	private List<WebElement> planSubMenus;

	public CaseManagement(){
		Web.getDriver().manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}
	LoadableComponent<?> parent;
	Actions act;
	public static String submenuName;
	XL_ReadWrite xlUtil;
	public String[] colNames;

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		try{
			act = new Actions(Web.getDriver());
			HomePage homePage = (HomePage)this.parent;
			new HomePage(new LoginPage(),false,new String[]
					{Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password")}).get();
			act.moveToElement(planMenu).click().build().perform();
			this.clickOnPlanSubmenu("Case Management");
			Web.waitForElement(caseIframe);
			Thread.sleep(5000);
			Web.FrameSwitchONandOFF(true,caseIframe);
			Web.nextGenDriver.waitForAngular();
			Web.FrameSwitchONandOFF(false);
		}
		catch(Exception e){
			e.printStackTrace();}
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(breadCrumb.getText().contains("Case Management"));
	}

	public void clickOnPlanSubmenu(String subMenuName){
		for (WebElement ele : planSubMenus){
			if(ele.getText().contains(subMenuName)){
				Web.clickOnElement(ele);
				break;
			}
		}
	}

	public void clickOnViewAllCaseHistroy(){
		Web.FrameSwitchONandOFF(true,caseIframe);
		Web.clickOnElement(viewAllCaseHistory);
		Web.nextGenDriver.waitForAngular();
	}

	public void clickOnExcelLink(){
		Web.FrameSwitchONandOFF(true,caseIframe);
		String primeWindow = Web.getDriver().getWindowHandle();
		Web.clickOnElement(excelLink);
		for(String winHandle : Web.getDriver().getWindowHandles()){
			Web.getDriver().switchTo().window(winHandle);
		}
		Web.getDriver().switchTo().window(primeWindow);
	}

	public String[] getCaseHistoryColNames(){
		try{
			String fileName = "Case_history_"+HomeStepDefinitions.planNumber+".xls";
			xlUtil = new XL_ReadWrite("H:\\Downloads\\"+fileName);
			return xlUtil.getColNames("Case_history");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isColumnExist(String[] requiredColNames){
		try{
			int count = 0;
			String allColumns = "";
			Arrays.sort(this.colNames=getCaseHistoryColNames());
			for(String name : this.colNames){
				allColumns+=name+",";
			}
			Reporter.logEvent(Status.INFO, "Columns in excel are:", allColumns, true);
			for(String colName : requiredColNames){
				if(CommonLib.binarySearch(this.colNames, colName, 0, this.colNames.length-1)>0){
					count++;
				}
			}
			if(count==requiredColNames.length)
				return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}

}
