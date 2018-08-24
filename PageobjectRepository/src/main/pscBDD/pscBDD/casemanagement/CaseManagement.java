/**
 * 
 */
package pscBDD.casemanagement;



import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


















import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;


















import bdd_annotations.FindBy;
import bdd_core.framework.Globals;
import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_lib.Web;
import bdd_lib.XL_ReadWrite;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

















import pscBDD.commonLib.CommonLib;
import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;

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
	
	@FindBy(how=How.XPATH,using="//*[@id='casetable']/td[1]/a")
	private List<WebElement> caseNumber;
	@FindBy(how=How.XPATH,using="//*[@id='participantChart']//*[@class='highcharts-series-group']/*/*")
	private List<WebElement> legendLinkParticipantCasesByCategory;
	@FindBy(how=How.XPATH,using="//*[@id='chartCaseListDataTable']//*[@id='casetable']/td[1]/a")
	private List<WebElement> caseNumberUnderParticipantCasesByContributionsPopup;
	@FindBy(how=How.XPATH,using="//button[@id='chartDataModalClose']/preceding-sibling::button/span")
	private WebElement ClosePopupParticipantCasesByContributions;
	
	@FindBy(how=How.XPATH,using="//*[*[text()='Newest open cases']]/following-sibling::div//button")
	private WebElement createNewCaseButton;
	@FindBy(how=How.NAME,using="caseType")
	private WebElement caseType;
	@FindBy(how=How.XPATH,using="//*[*[@name='caseType']]/following-sibling::div/button")
	private WebElement caseTypeNextButton;
	@FindBy(how=How.NAME,using="searchType")
	private WebElement searchType;
	@FindBy(how=How.XPATH,using = "//*[*[text()='Participant search']]/following-sibling::div//input[@type='text']")
	private WebElement participantSearchInput;
	@FindBy(how=How.XPATH,using="//*[*[@name='searchType']]/following-sibling::div//button")
	private WebElement participantSearchGoButton;
	@FindBy(how=How.XPATH,using="//*[text()='New case']/preceding-sibling::button")
	private WebElement newCasePopUpCloseButton;
	@FindBy(how=How.XPATH,using=" //*[@id='participantTableBody']/tr/td[3]")
	private List<WebElement> PlanNumberInNewCasePopUp;
	//@FindBy(how=How.ANGULAR_ATTRIBUTE,using = "ng-show", text = "caseHistoryLoading")
	@FindBy(how=How.XPATH,using="//div[@id='caseList']/div[3]")
	private WebElement caseHistoryLoading;
	@FindBy(how=How.XPATH,using="//*[text()='Employee ID']")
	private WebElement labelEmpIdUnderCaseDetails;
	
	
	
	
	

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
		try{
			//CommonLib.FrameSwitchONandOFF(true,caseIframe);
			Web.FrameSwitchONandOFF(false);
			Web.FrameSwitchONandOFF(true,caseIframe);
			Web.clickOnElement(viewAllCaseHistory);
			do{
				Thread.sleep(1000);
				System.out.println("loading......");
			}while(Web.isWebElementDisplayed(caseHistoryLoading));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
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
			String fileName = "Case_history_"+Globals.planNumber+".xls";
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
	
	public boolean verify_Part_ID_Emp_ID_forCaseNumber(String partid,
			String empid, String casenumber) {
		boolean flag = false;
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, caseIframe);
		Web.waitForElements(caseNumber);
		int count = 1;
		for (WebElement element : caseNumber) {
			if (element.getText().trim().contains(casenumber)) {
				flag = true;
				String xpathForPartIdEmpId = "(//*[@id='casetable'])[" + count
						+ "]/td[4]/div";
				try {
					List<WebElement> elements = Web.nextGenDriver
							.findElements(By.xpath(xpathForPartIdEmpId));
					if (elements.get(0).getText().contains(partid)
							&& elements.get(1).getText().contains(empid))
						return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			++count;

		}
		if (!flag)
			Reporter.logEvent(Status.INFO,
					"case number not found",
					"case number not found", true);
		return false;

	}
	public void clickOnParticipantByCategoryLink(String caseNo) throws InterruptedException{
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, caseIframe);
		boolean flag=false;
		if (Web.isWebElementsDisplayed(legendLinkParticipantCasesByCategory,
				true)) {		
			for (WebElement element : legendLinkParticipantCasesByCategory) {
				Thread.sleep(2000);
				Web.actionsClickOnElement(element);
				Thread.sleep(2000);
				for (WebElement elementCaseNo : caseNumberUnderParticipantCasesByContributionsPopup) {
					if (elementCaseNo.getText().trim().contains(caseNo)) {
						flag=true;
						break;

					}		
				}
				if(flag)
					break;
				else
					Web.clickOnElement(ClosePopupParticipantCasesByContributions);
			}

		}
		
	}
	public boolean verify_Part_ID_Emp_ID_forCaseNumberInThePopUp(String partid,
			String empid, String casenumber) {
		boolean flag = false;
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, caseIframe);
		Web.waitForElements(caseNumberUnderParticipantCasesByContributionsPopup);
		int count = 1;
		for (WebElement element : caseNumberUnderParticipantCasesByContributionsPopup) {
			if (element.getText().trim().contains(casenumber)) {
				flag = true;
				String xpathForPartIdEmpId = "(//*[@id='chartCaseListDataTable']//*[@id='casetable'])[" + count
						+ "]/td[4]/div";
				try {
					List<WebElement> elements = Web.nextGenDriver
							.findElements(By.xpath(xpathForPartIdEmpId));
					if (elements.get(0).getText().contains(partid)
							&& elements.get(1).getText().contains(empid))
						return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			++count;

		}
		if (!flag)
			Reporter.logEvent(Status.INFO,
					"case number not found in pop up",
					"case number not found in pop up", true);
		return false;

	}
	public void clickCreateNewCase() throws InterruptedException{
		System.out.println("---");
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, caseIframe);
		if(Web.isWebElementDisplayed(createNewCaseButton, true)){
			Web.clickOnElement(createNewCaseButton);
			Thread.sleep(2000);
		}
			
	}
	
	public boolean selectTheCaseTypeInNewCaseAndVerify(String partid, String empid,
			String planNo,String caseType) {
		boolean flag1=false,flag2=false;
		try {
			if (Web.isWebElementDisplayed(this.caseType, true)) {
				Web.selectDropDownOption(this.caseType, caseType, true);
				Web.clickOnElement(caseTypeNextButton);
				Thread.sleep(3000);
				if (partid.length() > 0) {
					Web.selectDropDownOption(searchType, "Participant ID", true);
					Thread.sleep(1000);
					System.out.println("");
					Web.setTextToTextBox(participantSearchInput, partid);
					Web.clickOnElement(participantSearchGoButton);
					Thread.sleep(2000);
					flag1=this.verifyEmpidPartidInNewCasePopupForPlanNo(partid, empid, planNo);
				}
				if (empid.length() > 0) {
					Web.selectDropDownOption(searchType, "Employee ID", true);
					Thread.sleep(1000);
					Web.setTextToTextBox(participantSearchInput, empid);
					Web.clickOnElement(participantSearchGoButton);
					Thread.sleep(2000);
					flag2=this.verifyEmpidPartidInNewCasePopupForPlanNo(partid, empid, planNo);
				}
				Web.actionsClickOnElement(newCasePopUpCloseButton);
			}
			if(flag1&&flag2)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	public boolean verifyEmpidPartidInNewCasePopupForPlanNo(String partid,
			String empid, String planNo) {
		try {
			int count = 1;
			if (Web.isWebElementsDisplayed(PlanNumberInNewCasePopUp, true)) {
				for (WebElement element : PlanNumberInNewCasePopUp) {
					if (element.getText().contains(planNo)) {
						String xpathForPartId = "//*[@id='participantTableBody']/tr["
								+ count + "]/td[4]";
						String xpathForEmpId = "//*[@id='participantTableBody']/tr["
								+ count + "]/td[5]";
						if (Web.nextGenDriver
								.findElement(By.xpath(xpathForPartId))
								.getText().contains(partid)
								&& Web.nextGenDriver
										.findElement(By.xpath(xpathForEmpId))
										.getText().contains(empid))
							return true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public void selectTheCaseNumber(String caseNo){
		boolean flag=false;
		for (WebElement element : caseNumber) {
			if (element.getText().trim().contains(caseNo)) {
				Web.clickOnElement(element);
				Web.nextGenDriver.waitForAngular();
				Reporter.logEvent(Status.INFO,
						"case number selected",
						"case number selected", true);
				flag=true;
				break;
			}
		}
		if(!flag)
			Reporter.logEvent(Status.INFO,
					"case number not found",
					"case number not found", true);
	}
	
	public boolean verifyBreadcrumb(String pageText){
		Web.FrameSwitchONandOFF(false);
		if(Web.isWebElementDisplayed(breadCrumb, true)){
			if(breadCrumb.getText().contains(pageText.trim()))
				return true;
		}
		return false;
		
	}
	public boolean verifyTheLabelUnderCaseDetails(String labelName){
		if(Web.isWebElementDisplayed(labelEmpIdUnderCaseDetails, true))
			return true;
		return false;
		
	}
	
	

}
