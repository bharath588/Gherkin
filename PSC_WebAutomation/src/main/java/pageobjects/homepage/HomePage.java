package pageobjects.homepage;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.DB;
import lib.Stock;
import lib.Web;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import framework.util.CommonLib;

public class HomePage extends LoadableComponent<HomePage>{
	//Object Declarations 

	@FindBy(css="div[id='footContainer'] ul[id='footBottomLinks'] li") private List<WebElement> wePostFooterLinkListLogin;
	@FindBy(css="div[id='greeting'] span[class='label']") private WebElement weGreeting;
	@FindBy(css="table[class='verificationTable']") private WebElement tabUserVerification;
	@FindBy(css="input[class*='emailBox']") private WebElement txtUserVerificationEmail;
	@FindBy(css="input[class*='answerBox']") private WebElement txtUserVerificationSecAns;
	@FindBy(css="button[class*='btn-u next ui-button']") private WebElement btnUserVerificationNxt;
	@FindBy(css="iframe[id='ifrmFooter']") private WebElement frmModalWindow;
	@FindBy(css="section[id='main'] > div[class='row'] h1[class='text-red flush-top']") private WebElement weModalWindowHeadertxt;
	@FindBy(css="div[class*='footerDialog ui-draggable'] a[class*='close']") private WebElement linkModalClose;
	@FindBy(css = "a[id = 'angularProfileLink']") private WebElement myProfileLink;
	@FindBy(css = "a[id = 'faqHeaderLink']") private WebElement faqLink;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(id="greeting")
	private WebElement usernameOnHomePage;
	@FindBy(xpath="//div[@id='logo']/img")
	private WebElement homePageLogo;
	@FindBy(xpath=".//a[@id='logOutLink']") private WebElement logoutLink;
	@FindBy(xpath=".//*[@id='planSearchAc_input']")
	private WebElement searchPlansInput;
	@FindBy(xpath=".//*[@id='planSearchAutocompleteButton']")
	private WebElement searchPlanButton;
	@FindBy(xpath=".//*[@id='headerInfo_xhtml']")
	private WebElement planHeaderInfo;
	@FindBy(xpath=".//div[@class='ui-growl-message']/span")
	private WebElement blankPlanNumberErrText;
	@FindBy(xpath=".//ul[contains(@class,'ui-autocomplete')]/li")
	private List<WebElement> autoCompleteSuggestionText;
	@FindBy(xpath="//div[@id='lastLogin']")
	private WebElement lastLogin;
	@FindBy(xpath = ".//*[@id='tooltip_todos']")
	private WebElement toDoButton;
	@FindBy(xpath = ".//*[@id='tooltip_notifications']")
	private WebElement notificationButton;
	@FindBy(xpath="//*[@id='tooltip_compliance']")
	private WebElement complianceHyperLink;
	@FindBy(xpath = ".//iframe[@id = 'frameb']")
	private WebElement iFramePlanB;
	@FindBy(xpath = ".//*[@id='main']/div/a/following-sibling::div[@class='breadcrumb']")
	private WebElement tdlBreadcrumb;
	@FindBy(xpath=".//div[@class='breadcrumb']/i")
	private List<WebElement> breadCrumb;
	@FindBy(xpath = ".//*[@id='main']/div/a")
	private WebElement homeLink;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/a")
	private WebElement planMenu;
	//@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/a")
	@FindBy(xpath="//*[@id='newMenu']//a[.='Plan']//following-sibling::ul//a[.='Administration...']")
	private WebElement planAdministration;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/ul/li[1]/a")
	private WebElement planAdministrationToDo;
	@FindBy(xpath = "html/body/h4[text()='Disbursement requests']")
	private WebElement disbursementRequest;
	@FindBy(xpath = ".//*[@id='PlanSummaryMain']//div/ul/li")
	private List<WebElement> restyledHomeButtons;
	@FindBy(xpath=".//div[@class='button-row']//button[.='More']")
	private WebElement moreButton;
	@FindBy(id="jumpPage")
	private WebElement verifyJumpPage;
	@FindBy(id="planListTable_data")
	private WebElement planListData;
	@FindBy(className="planheading")
	private WebElement planAnalytics;
	@FindBy(xpath="(.//*[contains(@class,'planinfopanel')])[2]")
	private WebElement planinfoAndStatus;
	@FindBy(xpath="(//h2[text() = 'Funds'])[2]")
	private WebElement funds;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/a/following-sibling::ul//a[contains(text(),'Overview')]")
	private WebElement PlanOverviewTab;
	@FindBy(xpath="//ul//a[contains(text(),'Overview')]/following-sibling::ul//a[text()='Plan provisions']")
	private WebElement  planProvisionTab;
	@FindBy(xpath="//ul//a[contains(text(),'Overview')]/following-sibling::ul//a[text()='Vesting schedules']")
	private WebElement  planVestingSchedules;
	@FindBy(xpath="//ul//a[contains(text(),'Overview')]/following-sibling::ul//a[text()='Participant web and VRU options']")
	private WebElement  pptWebAndvruTab;
	@FindBy(id="tooltip_todos")
	private WebElement toDoTab;
	@FindBy(id="tooltip_compliance")
	private WebElement complianceTabToolTip;
	@FindBy(xpath=".//span[contains(text(),'File sharing')]")
	private WebElement fileSharingTab;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/a/following-sibling::ul//a[text()='Administration']")
	private WebElement administrationTab;
	@FindBy(xpath="//ul//a[contains(text(),'Administration')]/following-sibling::ul//a[text()='Pay plan expenses']")
	private WebElement payPlanExpense;
	@FindBy(xpath=".//input[@value='View Plan Invoices']")
	private WebElement viewPlanInvoicesBtn;
	@FindBy(xpath = ".//*[@id='newMenu']/li[8]/a")
	private WebElement fileSharingMenu;
	@FindBy(xpath="//*[@id='newMenu']/li[5]/a")
	private WebElement complianceMenu;
	private WebElement menuElement(String menuName)
	{
		return Web.getDriver().findElement(By.xpath("//ul[@id='newMenu']/li/a[contains(text(),'"+menuName+"')]"));
	}
	private List<WebElement> menuItems()
	{
		return Web.getDriver().findElements(By.xpath("//ul[@id='newMenu']/li"));
	}
	private List<WebElement> subMenuItems()
	{
		return Web.getDriver().findElements(By.xpath("//ul[@id='newMenu']/li/a[contains(text(),'"+Stock.GetParameterValue("menuname")+"')]/following-sibling::ul/li/a"));
	}
	private List<WebElement> subSubMenuItems(String subMenu)
	{

		return Web.getDriver().findElements(By.xpath("//ul[@id='newMenu']/li/a[contains(text(),'"+Stock.GetParameterValue("menuname")+"')]/following-sibling::ul/li/a[contains(text(),'"+subMenu+"')]/following-sibling::ul//a"));
	}
	private WebElement returnActionButtonsOnHomePage(String actionButtonName)
	{
		return Web.getDriver().findElement(By.xpath(".//*[@id='tooltip_"+actionButtonName+"']"));
	}
	/*-----------------------------------------------------------------*/
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;

	Map<String,String> securityAnsMap=null;
	static ResultSet queryResultSet;

	// userVeriData is optional for HomePage constructor
	public HomePage(LoadableComponent<?> parent,boolean performVerification,String... userData){
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData; 
		this.userVeriData = new String[2];		
		PageFactory.initElements(Web.getDriver(), this);
	}

	public HomePage(){
		PageFactory.initElements(Web.getDriver(), this);
	}


	@Override
	protected void isLoaded() throws Error {	
		if(!Web.isWebElementDisplayed(weGreeting,false)){
			throw new AssertionError("Plan service center landing page not loaded.");
		}else{
			Reporter.logEvent(Status.PASS, "Check if Home page is loaded",""
					+ "Home page has loaded successfully",false);
		}	
	}

	@Override
	protected void load() {		
 		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj =  new LoginPage();
		try{			
			if(ifUserOrAccntVerificationMandate) {	// Performs User or Account Verification 		
				invokeMethod = this.parent.getClass().getDeclaredMethod("performVerification", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),new Object[]{userData});			
			}else{
				//Performing Login
				Object login = this.parent.getClass().newInstance(); 				
				Web.getDriver().switchTo().frame(Web.returnElement(login,"LOGIN FRAME"));
				Web.waitForElement(login,"USERNAME");
				Web.waitForElement(login,"PASSWORD");
				Web.getDriver().switchTo().defaultContent();
				invokeMethod = this.parent.getClass().getDeclaredMethod("submitLoginCredentials", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),new Object[]{userData});
				loginObj.waitForSuccessfulLogin();
				//Check if UserVerification Pages appears then performVerification
				if(Web.isWebElementDisplayed(Web.returnElement(userVeriPg,"EMAIL ADDRESS"))){
					/*userVeriData = new String[]{
							userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
									Stock.GetParameterValue("username")),
					          getSecurityAnswer((Web.returnElement(userVeriPg, "SECURITYQUESTION")).getText().trim())};*/
					userVeriData[0] = userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));
					userVeriData[1] = Stock.GetParameterValue("userVerificationAns");
					invokeMethodforUserVerification = userVeriPg.getClass().getDeclaredMethod("performVerification",String[].class);
					invokeMethodforUserVerification.invoke(userVeriPg,new Object[]{userVeriData});
				}else{

				}
			}
			if(Stock.getConfigParam("DataType").equals("NonApple")){
			Web.isWebElementDisplayed(urlJumpPage,true);
			Web.clickOnElement(urlJumpPage);}
			Web.getDriver().switchTo().defaultContent();
			Web.ispageloaded("framec");
			Web.isWebElementDisplayed(weGreeting,true);
			Thread.sleep(2000);
			Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
		} catch (Exception e) {
			try {
				throw new Exception("Login to PSC failed , Error description : "+ e.getMessage());
			} catch (Exception t) {			
				ThrowException.Report(TYPE.EXCEPTION,t.getMessage());
			}
		}
	}

	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 *  </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("MY PROFILE")) {
			return this.myProfileLink;
		}
		if(fieldName.trim().equalsIgnoreCase("FAQ")){
			return this.faqLink;
		}
		if(fieldName.trim().equalsIgnoreCase("Plan Menu")) {
			return this.planMenu;
		}
		if(fieldName.trim().equalsIgnoreCase("Plan Administration")) {
			return this.planAdministration;
		}
		if (fieldName.trim().equalsIgnoreCase("JUMP_PAGE_NXTGEN_LINK")) {
			return this.urlJumpPage;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGOUT_LINK")) {
			return this.logoutLink;
		}
		if(fieldName.trim().equalsIgnoreCase("todoLink")){
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(iFramePlanB);
			return this.toDoButton;
		}
		if(fieldName.trim().equalsIgnoreCase("notificationLink")){
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(iFramePlanB);
			return this.notificationButton;
		}
		if(fieldName.trim().equalsIgnoreCase("complianceLink")){
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(iFramePlanB);
			return this.complianceHyperLink;
		}
		if(fieldName.trim().equalsIgnoreCase("Welcome"))
		{
			Web.getDriver().switchTo().defaultContent();
			return this.weGreeting;
		}
		if(fieldName.trim().equalsIgnoreCase("Home_Page_Logo"))
		{
			Web.getDriver().switchTo().defaultContent();
			return this.homePageLogo;
		}
		if(fieldName.trim().equalsIgnoreCase("File Sharing"))
		{
			Web.getDriver().switchTo().defaultContent();
			return this.fileSharingMenu;
		}
		if(fieldName.trim().equalsIgnoreCase("Compliance Menu"))
		{
			Web.getDriver().switchTo().defaultContent();
			return this.complianceMenu;
		}
		return null;
	}

	/**Method to open each footer link after login and validate if the 
	 * respective page is opening
	 * 
	 * @throws Error 
	 */	
	public void checkFooterLinkPostLogin(List<String> FooterLinks) {				
		List<WebElement> footerLinks = wePostFooterLinkListLogin;
		WebElement footerlink; 
		boolean textMatch = false;
		String footerLinkText =null ;
		String testData = null;
		String modalWindowHeaderText = null;
		try{
			/*if(FooterLinks.size()!=footerLinks.size()){
				throw new Exception("Post login footer links doesnt match");
			}*/
			for(int iLoopCnt=0;iLoopCnt<=footerLinks.size()-1;iLoopCnt++){
				Web.waitForElement(footerLinks.get(iLoopCnt).findElement(By.cssSelector("a")));				
				footerlink =  footerLinks.get(iLoopCnt).findElement(By.cssSelector("a"));
				footerLinkText = footerlink.getText();

				if(FooterLinks.get(iLoopCnt).equals(footerlink.getText())){	
					Web.clickOnElement(footerlink);		
					//Thread.sleep(2000);

					if(Web.isWebElementDisplayed(frmModalWindow,true)){
						Web.getDriver().switchTo().frame(frmModalWindow);
						//testData = TestDataContainer.GetParameterValue("link_Footer"+(iLoopCnt+1)); 
						testData=Stock.GetParameterValue("link_Footer"+(iLoopCnt+1));

						if(testData.equalsIgnoreCase("PSCUserAuthorizationForm")){
							Web.getDriver().switchTo().defaultContent();	
							textMatch = Web.VerifyPartialText(testData, frmModalWindow.getAttribute("src"),true);
							modalWindowHeaderText = "PSC User Authorization Form";
						}else{
							textMatch = Web.VerifyPartialText(testData, weModalWindowHeadertxt.getText(),true);
							modalWindowHeaderText = weModalWindowHeadertxt.getText();
							Web.getDriver().switchTo().defaultContent();								
						}							
						linkModalClose.click();						
					}else{
						Reporter.logEvent(Status.FAIL,"Verify if respective modal window opens from footer link", 
								"No such modal window opended for " + footerLinkText + " link", true);
					}
				}
				if(textMatch)Reporter.logEvent(Status.PASS,"Verify if footer link opens respective modal window", 
						"Modal window " +  modalWindowHeaderText+ " opended from footer link : " + footerLinkText, false);	

				if(!textMatch)Reporter.logEvent(Status.FAIL,"Verify if footer link opens respective modal window", 
						"No Modal window from footer link : " + footerLinkText, true);
				textMatch = false;
			}
		}catch(Exception e){
			Reporter.logEvent(Status.FAIL,"Verify if footer link opens respective modal window", 
					"Exception Occurred while checking footer links : "+e.getMessage() , true);
		}
	}

	public void validate_if_homepage_loaded(String ifSingleSiteUser) throws Exception {
		if(ifSingleSiteUser.equalsIgnoreCase("false")){
			if(Web.isWebElementDisplayed(urlJumpPage, true)){
				Web.clickOnElement(urlJumpPage);
				Web.waitForElement(weGreeting);
				isLoaded();
			}else{
				throw new Exception("Expected Jump page not loaded");
			}                                 
		}else if (ifSingleSiteUser.equalsIgnoreCase("true")){
			Web.waitForElement(weGreeting);
			isLoaded();
		}
	}

	public void logoutPSC() throws Exception{
		Web.getDriver().switchTo().defaultContent();
		if(Web.isWebElementDisplayed(logoutLink,true)){
			logoutLink.click();
			Web.waitForPageToLoad(Web.getDriver());
			Web.isWebElementDisplayed(Web.returnElement(new LoginPage(),"USERNAME"),false);
			Reporter.logEvent(Status.PASS,"Perform logout of PSC","Logged out of PSC successfully",false);
		}else{
			Reporter.logEvent(Status.FAIL,"Perform logout of PSC","Unable to log out of PSC application",true);
		}
	}
	
	

	public boolean searchPlan()
	{ boolean planTextDisplayed = false;
	try{
		Web.setTextToTextBox(searchPlansInput, Stock.GetParameterValue("planNumber"));
		Web.clickOnElement(searchPlanButton);
		Web.isWebElementDisplayed(moreButton, true);
		if(Stock.GetParameterValue("planNumber")!=null)
			if(planHeaderInfo.getText().contains(Stock.GetParameterValue("planNumber")))
				return planTextDisplayed = true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return planTextDisplayed;
	}

	public boolean verifyErrorText()
	{
		boolean errorVerified = false;
		try{
			System.out.println("Actual Error Text is: "+blankPlanNumberErrText.getText());
			if(Stock.GetParameterValue("errortext").equalsIgnoreCase(blankPlanNumberErrText.getText()))
				return errorVerified=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return errorVerified;
	}

	public void enterPartialPlanNumber()
	{
		try{
			if(Stock.GetParameterValue("planNumber")!=null)
			{
				Web.setTextToTextBox(searchPlansInput, Stock.GetParameterValue("planNumber").substring(0, 5));
				Reporter.logEvent(Status.INFO, "Check if five characters are entered in plan search input box", 
						"Five characters from plan number are entered in plan search input box", true);
				Thread.sleep(2000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean verifyAutocompletePlanSuggestion()
	{
		boolean autoCompleteSuggested = false;
		if(autoCompleteSuggestionText.size()>0)
		{
			for(WebElement ele : autoCompleteSuggestionText)
			{
				if(ele.getText().contains(Stock.GetParameterValue("planNumber")))
					autoCompleteSuggested = true;
				Reporter.logEvent(Status.INFO,"Check if autocomplete plan suggestion is displayed", 
						"Autocomplete plan suggestion is displayed", false);
			}
		}
		else
		{
			Reporter.logEvent(Status.INFO, "Check if autocomplete plan suggestion is displayed", 
					"Autocomplete plan suggestion not displayed", true);
			autoCompleteSuggested = false;
		}
		return autoCompleteSuggested;
	}

	public ResultSet getLastLoginsFromDB(String[] getLastLogins,String application, String menuFeatureCode, String user)
	{
		return DB.executeQuery(getLastLogins[0], getLastLogins[1], application,menuFeatureCode,"K_"+user);
	}

	public String getLastLoginDate()
	{
		String lastLoginDate = "";
		ResultSet rs = getLastLoginsFromDB(Stock.getTestQuery("getLastLogins"),Stock.GetParameterValue("application"),Stock.GetParameterValue("menu_feature_code"),Stock.GetParameterValue("username"));
		try{
			while(rs.next())
			{
				lastLoginDate = rs.getString("DPDATE");
			}
			if(lastLoginDate!=null)
			{
				Reporter.logEvent(Status.PASS, "Check last login time is stored in DB in Denver time", 
						"Last login time is stored in DB in Denver time: "+lastLoginDate.trim(),
						false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Check last login time is stored.", 
						"Last login time is stored in DB",
						false);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lastLoginDate;
	}


	public void verifyHomePageMenuTabs()
	{

		try{
			String expectedTabs = Stock.GetParameterValue("menutabs");
			String[] splitTabs = expectedTabs.split(",");
			List<String> expectedMenuTabs = Arrays.asList(splitTabs);
			List<String> actualMenuTabs = new ArrayList<String>();
			for(WebElement ele : menuItems())
			{
				actualMenuTabs.add(ele.findElement(By.tagName("a")).getText());
			}
			System.out.println("Tabs fetched from GUI are:"+actualMenuTabs);
			System.out.println("Tabs fetched from xml are:"+expectedMenuTabs);
			if(actualMenuTabs.containsAll(expectedMenuTabs))
			{
				Reporter.logEvent(Status.PASS,"Verify if all menu tabs are displayed on home page.","All menu tabs are displayed.",false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify if all menu tabs are displayed on home page.","All menu tabs are not displayed",true);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getLastLoginDateFromWeb()
	{
		String displayedLastLoginDate = "";
		try
		{
			if(Web.isWebElementDisplayed(lastLogin, false))
			{
				displayedLastLoginDate = lastLogin.getText();
				Reporter.logEvent(Status.PASS, "Check last login time displays on home page", 
						"Last login time displays on home page: "+displayedLastLoginDate.trim().substring(11),
						false);
			}
			else
				Reporter.logEvent(Status.FAIL, "Check last login time displays on home page", 
						"Last login time do not displays on home page", false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return displayedLastLoginDate;
	}

	public boolean verifyLastLoginDateEquality(String dateFromDatabase)
	{
		boolean equalDate = false;
		System.out.println(dateFromDatabase);
		String dateFromDB1 = dateFromDatabase.trim().substring(0,16);
		String dateFromDB2 = dateFromDatabase.trim().substring(16);
		String dateFromDB = dateFromDB1+" "+dateFromDB2;
		String dateFromWeb = getLastLoginDateFromWeb().trim().substring(11,30);
		try
		{
			System.out.println("Date from db is: "+dateFromDB);
			System.out.println("Date from Web is: "+dateFromWeb);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa",Locale.US);
			Date date = sdf.parse(dateFromWeb);
			Date date1 = sdf.parse(dateFromDB);
			Calendar cal =Calendar.getInstance();
			cal.setTime(date1);
			cal.add(Calendar.HOUR, 2);
			date1 = cal.getTime();
			String lastLoginWeb = sdf.format(date);
			String lastLoginDB = sdf.format(date1);
			if(lastLoginWeb.equalsIgnoreCase(lastLoginDB))
			{
				equalDate = true;
			}
			else
				equalDate = false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return equalDate;
	}


	public void verifySubMenuAndOptions(String menu)
	{
		WebElement menuItem = menuElement(menu);
		List<String> expectSubMenuItems = Arrays.asList(Stock.GetParameterValue("submenu").split(","));
		List<String> actualSubMenuItems = new ArrayList<String>();
		List<String> actualSubSubMenuItems = new ArrayList<String>();	
		Map<String,List<String>> expMenuMap = new LinkedHashMap<String,List<String>>();
		Map<String,List<String>> actMenuMap = new LinkedHashMap<String,List<String>>();

		for(int i=0;i<expectSubMenuItems.size();i++)
		{
			if(expectSubMenuItems.get(i).equalsIgnoreCase("Investments & Performance")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Search employee")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Add employee")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Forms")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Overview")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Enter payroll")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Pending")||
					expectSubMenuItems.get(i).equalsIgnoreCase("View∕change banking information")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Year end compliance")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Compliance user guide")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Video demonstration")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Standard reports")||
					expectSubMenuItems.get(i).equalsIgnoreCase("My reports")||
					expectSubMenuItems.get(i).equalsIgnoreCase("Educational resources")
					)
			{
				if(menu.equals("Plan") && expectSubMenuItems.get(i).equalsIgnoreCase("Overview"))
					expMenuMap.put(expectSubMenuItems.get(i), Arrays.asList(Stock.GetParameterValue(expectSubMenuItems.get(i)).split(",")));
				else
				{
					expMenuMap.put(expectSubMenuItems.get(i), new LinkedList<String>());

				}
			}
			else
			{
				expMenuMap.put(expectSubMenuItems.get(i), Arrays.asList(Stock.GetParameterValue(expectSubMenuItems.get(i)).split(",")));
			}
		}

		Web.waitForPageToLoad(Web.getDriver());
		Actions act = new Actions(Web.getDriver());
		try{
			if(menuItem.getText().equals(menu))
			{
				Web.clickOnElement(menuItem);
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(10000);
				act.moveToElement(menuItem).click(menuItem).build().perform();

				for(WebElement ele : subMenuItems())
				{
					actualSubMenuItems.add(ele.getText().replace("...", "").trim());
					if(ele.getText().equals("Investments & Performance") || ele.getText().equals("Search employee") ||
							ele.getText().equals("Add employee") || ele.getText().equals("Forms")||
							ele.getText().equals("Overview")||ele.getText().equals("Enter payroll")||
							ele.getText().equals("Pending")|| ele.getText().equals("View∕change banking information")||
							ele.getText().equals("Year end compliance")|| ele.getText().equals("Compliance user guide")||ele.getText().equals("Video demonstration")|| 
							ele.getText().equals("Standard reports")|| ele.getText().equals("My reports")||ele.getText().equals("Educational resource"))
					{

					}
					else
					{
						act.click(ele).build().perform();
					}
					Thread.sleep(2000);
					actualSubSubMenuItems = new ArrayList<String>();
					for(WebElement ele2 : subSubMenuItems(ele.getText().replace("...", "").trim()))
					{
						actualSubSubMenuItems.add(ele2.getText());
					}
					actMenuMap.put(ele.getText().replace("...", "").trim(), actualSubSubMenuItems);
				}

				System.out.println("Actual subMenu items are:"+actualSubMenuItems);
				System.out.println("Expected submenu items fetched from xml are :"+expectSubMenuItems);
				System.out.println("Actual Map is:"+actMenuMap);
				System.out.println("Expected Map is:"+expMenuMap);

				for(int j=0;j<expectSubMenuItems.size();j++){

					if(actMenuMap.get(actualSubMenuItems.get(j)).isEmpty() && expMenuMap.get(expectSubMenuItems.get(j)).isEmpty()){
						Reporter.logEvent(Status.PASS,"Verify if all sub menu options are displayed on home page for '"+expectSubMenuItems.get(j)+"'.","All submenu options are displayed.",false);

					}

					else if(actMenuMap.get(actualSubMenuItems.get(j)).containsAll(expMenuMap.get(expectSubMenuItems.get(j))))
					{
						Reporter.logEvent(Status.PASS,"Verify if all sub menu options are displayed on home page for '"+expectSubMenuItems.get(j)+"'.","All submenu options are displayed.",false);
					}
					else
					{
						Reporter.logEvent(Status.FAIL,"Verify if all sub menu options are displayed on home page for '"+expectSubMenuItems.get(j)+"'.","All submenu options are not displayed.",true);
					}
				}
			}

			if(actualSubMenuItems.containsAll(expectSubMenuItems))
			{
				Reporter.logEvent(Status.PASS,"Verify if all submenu options are displayed on home page for '"+menu+"'.","All submenu tabs '"+actualSubMenuItems+"' are displayed.",false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify if all submenu options are displayed on home page for '"+menu+"'.","All submenu tabs '"+actualSubMenuItems+"' are not displayed.",true);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public ResultSet getSessionIDFromDB(String[] getSessionIDFromDB,String application, String menuFeatureCode, String user)
	{
		System.out.println("Query is:"+getSessionIDFromDB[1]);
		return DB.executeQuery(getSessionIDFromDB[0], getSessionIDFromDB[1], application,menuFeatureCode,"K_"+user);
	}

	public List<String> getActiveSessionIDFromDB()
	{
		List<String> sessionID = new ArrayList<String>();
		try{
			ResultSet rs = getSessionIDFromDB(Stock.getTestQuery("getSessionIDFromDB"),
					Stock.GetParameterValue("application"),Stock.GetParameterValue("menu_feature_code"),
					Stock.GetParameterValue("username"));
			while(rs.next())
			{
				//System.out.println(rs);
				sessionID.add(rs.getString("session_id")) ;
			}

			if(sessionID.size()>0)
			{
				Reporter.logEvent(Status.INFO, "Get Session ID from DB", 
						"Seesion ID retrieved from DB is: "+sessionID, false);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sessionID;
	}

	public String getSessionIDFromBrowserCookie()
	{
		String cookieSessionID = null;
		try
		{
			Set<Cookie> allCookies = new HashSet<Cookie>();
			allCookies = Web.getDriver().manage().getCookies();
			for(Cookie ck : allCookies)
			{
				if(ck.getName().equalsIgnoreCase("JSESSIONID")){
					cookieSessionID = ck.getValue();
					Reporter.logEvent(Status.INFO, "Get Session ID from browser cookie", 
							"Seesion ID retrieved from browser cookie is: "+cookieSessionID, false);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cookieSessionID;
	}

	public boolean validateCookieValue()
	{
		boolean equalSessionIds = false;
		String sessionIDFromBrowserCookie;
		try
		{			
			sessionIDFromBrowserCookie = getSessionIDFromBrowserCookie().trim();
			Thread.sleep(7000);
			for(String session : getActiveSessionIDFromDB())
			{
				if(sessionIDFromBrowserCookie.contains(session))
				{
					equalSessionIds = true;
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return equalSessionIds;
	}

	public boolean clickOnToDoFromHome()
	{
		boolean clickedOnToDoFromHomePage = false;
		try{
			Web.getDriver().switchTo().frame(iFramePlanB);
			lib.Web.clickOnElement(toDoButton);
			if(Web.isWebElementDisplayed(disbursementRequest, true)){
				Reporter.logEvent(Status.PASS, "Click on To Do from home page and check if to do list is loaded",
						"Clicked on To Do from home page and to do list is loaded", false);
				clickedOnToDoFromHomePage = true;
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Click on To Do from home page and check if to do list is loaded",
						"Clicked on To Do from home page and to do list is not loaded", true);
			}
			Web.getDriver().switchTo().defaultContent();
			logoutPSC();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return clickedOnToDoFromHomePage;
	}

	public boolean clickOnNotificationFromHome()
	{
		boolean clickedOnNotificationFromHomePage = false;
		try{
			Web.getDriver().switchTo().frame(iFramePlanB);
			Web.clickOnElement(notificationButton);
			if(Web.isWebElementDisplayed(disbursementRequest, true)){
				Reporter.logEvent(Status.PASS, "Click on notification from home page and check if to do list is loaded",
						"Clicked on notification from home page and to do list is loaded", false);
				clickedOnNotificationFromHomePage = true;
			}
			else
			{
				Reporter.logEvent(Status.PASS, "Click on notification from home page and check if to do list is loaded",
						"Clicked on notification from home page and to do list is not loaded", true);
			}
			Web.getDriver().switchTo().defaultContent();
			logoutPSC();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return clickedOnNotificationFromHomePage;
	}

	public boolean clickOnToDoFromPlanAdministration()
	{
		boolean clickedOnToDoFromPlanAdmin = false;
		try
		{
			Web.clickOnElement(planMenu);
			Thread.sleep(1000);
			Web.clickOnElement(planAdministration);
			Thread.sleep(1000);
			Web.clickOnElement(planAdministrationToDo);
			Thread.sleep(1000);
			Web.getDriver().switchTo().frame(iFramePlanB);
			if(Web.isWebElementDisplayed(disbursementRequest, true)){
				Reporter.logEvent(Status.PASS, "Click on to do from plan administration and check if to do list is loaded",
						"Clicked on to do from plan administration and to do list is loaded", false);
				clickedOnToDoFromPlanAdmin = true;
			}
			else
			{
				Reporter.logEvent(Status.PASS, "Click on to do from plan administration and check if to do list is loaded",
						"Clicked on to do from plan administration and to do list is not loaded", true);
			}
			Web.getDriver().switchTo().defaultContent();
			logoutPSC();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return clickedOnToDoFromPlanAdmin;
	}

	public boolean verifyToDoSiteNavigation(String buttonName)
	{
		boolean verified = false;
		try
		{
			if(buttonName.equalsIgnoreCase("toDoHomePage") && clickOnToDoFromHome())
			{
				verified = true;
			}
			else if(buttonName.equalsIgnoreCase("notificationHomePage") && clickOnNotificationFromHome())
			{
				verified = true;
			}
			else if (buttonName.equalsIgnoreCase("toDoPlanAdmin") && clickOnToDoFromPlanAdministration())
			{
				verified = true;
			}
			else
				verified = false;
		}
		catch(Exception e)
		{

		}
		return verified;
	}

	public boolean verifySentenceCase(String buttonText)
	{
		buttonText = buttonText.trim();
		boolean sentenceCase = false;
		boolean flag = false;
		try
		{
			char[] buttonTextArray = buttonText.toCharArray();
			int i = 0;
			for (char ch : buttonTextArray)
			{	
				if(i==0 && Character.isUpperCase(ch)){
					flag = true;
					i++;
				}
				else if(i!=0 && flag && !Character.isUpperCase(ch))
				{
					sentenceCase = true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sentenceCase;
	}

	public boolean verifyButtonTextForSentenceCase()
	{
		boolean isButtonTextInSentenceCase = false;
		try{
			Web.getDriver().switchTo().frame(iFramePlanB);
			for(WebElement button : restyledHomeButtons)
			{
				if(verifySentenceCase(button.getText()))
					isButtonTextInSentenceCase = true;
				if(isButtonTextInSentenceCase)
					Reporter.logEvent(Status.PASS, "Check button label "+button.getText()+" is in sentence case",
							"Given button label is in sentence case", false);
				else
					Reporter.logEvent(Status.PASS, "Check button label "+button.getText()+" is in sentence case",
							"Given button label is not in sentence case", true);
			}
			Web.getDriver().switchTo().defaultContent();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return isButtonTextInSentenceCase;
	}

	public String getSecurityAnswer(String securityQuestion) {
		String securityAnswer = null;
		initSecurityAnsMap();
		for (Map.Entry<String, String> entry : securityAnsMap.entrySet()) {
			if (StringUtils.contains(securityQuestion, entry.getKey())) {
				securityAnswer = entry.getValue();
				break;
			}
		}
		return securityAnswer;
	}

	private void initSecurityAnsMap() {
		String secAns = Stock.getConfigParam("SecurityAns_"
				+Stock.getConfigParam("TEST_ENV"));
		if (securityAnsMap == null & !secAns.isEmpty()) {
			securityAnsMap = new LinkedHashMap<String, String>();
			for (String QA : secAns.split("\\|")) {
				securityAnsMap.put(QA.split(",")[0].trim(),
						QA.split(",")[1].trim());
			}
		}
	}


	public boolean searchPlanWithName()
	{ boolean planTextDisplayed = false;
	try{
		Web.setTextToTextBox(searchPlansInput, Stock.GetParameterValue("planName"));
		Web.clickOnElement(searchPlanButton);
		Web.isWebElementDisplayed(moreButton, true);
		if(Stock.GetParameterValue("planName")!=null)
			if(planHeaderInfo.getText().contains(Stock.GetParameterValue("planName")))

				Web.clickOnElement(searchPlanButton);
		Web.isWebElementDisplayed(moreButton, true);
		if(Stock.GetParameterValue("planName")!=null)
			if(planHeaderInfo.getText().contains(Stock.GetParameterValue("planName")))
				return planTextDisplayed = true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return planTextDisplayed;
	}	

	public boolean searchPlanWithIdOrName(String iDOrName) throws Exception
	{
		boolean planTextDisplayed = false;
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(weGreeting).build().perform();
		Web.setTextToTextBox(searchPlansInput,iDOrName);
		Web.clickOnElement(searchPlanButton);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.waitForElement(iFramePlanB);
		Web.getDriver().switchTo().frame(iFramePlanB);
		Web.isWebElementDisplayed(moreButton, true);
		Web.getDriver().switchTo().defaultContent();
		if(iDOrName!=null)
			if(planHeaderInfo.getText().contains(iDOrName))
				planTextDisplayed = true;
			else
				planTextDisplayed =false;
		return planTextDisplayed;
	}






	/**
	 * This method checks if the jump page is displayed for the users having access to multiple sites and skip it as required
	 * @throws Exception 
	 */

	public boolean isJumpPageDisplayed() throws Exception {
		boolean isJumpDisplayed;
		Web.waitForElement(verifyJumpPage);
		if (Web.isWebElementDisplayed(verifyJumpPage))
		{
			isJumpDisplayed = true;
		}
		else
		{
			isJumpDisplayed = false;
		}
		System.out.println("Boolean value for jump page is:"+isJumpDisplayed);
		return isJumpDisplayed;
	}


	public void jumpPageVerificationWhenPlanAccessInSingleSite() throws Exception
	{
		//AccountVerificationPage act = new AccountVerificationPage();
		if(this.isJumpPageDisplayed())
		{
			Reporter.logEvent(Status.FAIL,"Verify if Jump page is not displayed if user has access to plans only in single site","Jump page is displayed.",true);
		}
		else
		{
			Reporter.logEvent(Status.PASS,"Verify if Jump page is not displayed if user has access to plans only in single site","Jump page is not displayed.",false);
		}
	}

	public void jumpPageVerificationWhenPlanAccessInAllSite() throws Exception
	{
		//AccountVerificationPage act = new AccountVerificationPage();
		if(this.isJumpPageDisplayed())
		{
			Reporter.logEvent(Status.PASS,"Verify if Jump page is displayed if user has access to plans in all sites.","Jump page is displayed.",false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify if Jump page is displayed if user has access to plans in all sites.","Jump page is not displayed.",true);
		}
	}
	
	/**
	 * This method verifies that Plan list is not available when user is not having any plan access.
	 */

	public void isPlanListDisplayed()
	{
		if(Web.isWebElementDisplayed(planListData, true))
		{
			Reporter.logEvent(Status.FAIL,"Verify Plan list data is not displayed on home page since user is not having any plan access.","Plan List is displayed.",true);
		}
		else
		{
			Reporter.logEvent(Status.PASS,"Verify Plan list data is not displayed on home page since user is not having any plan access.","Plan List is not displayed.",false);
		}
	}


	/**
	 * <pre>This method Takes you to the specified menu or submenu page.</pre>
	 * @author smykjn
	 *//*


	public void navigateToProvidedPage(String...specifiedTab) throws Exception
	{
		String bredCrumbValue= "";
		WebElement breadCrumb;
		boolean isPageDisplayed = false;
		Actions act = new Actions(Web.getDriver());
		String xpath1 = "//a[contains(text(),'"+specifiedTab[0]+"')]/following-sibling::ul";
		String xpath2 = "//a[contains(text(),'"+specifiedTab[1]+"')]/following-sibling::ul";
		String xpath3 = "//a[contains(text(),'"+specifiedTab[0]+"')]/following-sibling::ul//a[contains(text(),'"+specifiedTab[1]+"')]";
		String xpath4 = "//a[contains(text(),'"+specifiedTab[1]+"')]/following-sibling::ul//a[.='"+specifiedTab[2]+"']";
		if(Web.getDriver().findElements(By.xpath(xpath1)).size()>0){
			act.moveToElement(Web.returnElement(new HomePage(),"Welcome")).build().perform();
			//Web.clickOnElement(menuElement(specifiedTab[0]));
			act.moveToElement(menuElement(specifiedTab[0])).click().build().perform();
			//act.click(menuElement(specifiedTab[0])).build().perform();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.getDriver().findElements(By.xpath(xpath2)).size()>0)
			{
				Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath3)));
				if(Web.getDriver().findElements(By.xpath(xpath4)).size()>0){
				Web.isWebElementDisplayed(Web.getDriver().findElement(By.xpath(xpath4)), true);
				act.click(Web.getDriver().findElement(By.xpath(xpath4))).build().perform();
				Web.waitForPageToLoad(Web.getDriver());
				bredCrumbValue=specifiedTab[2];}
					Web.isWebElementDisplayed(Web.getDriver().findElement(By.xpath(xpath4)), true);
					Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath4)));
					Web.waitForPageToLoad(Web.getDriver());}
			}
			else
			{
				Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath3)));
				Web.waitForPageToLoad(Web.getDriver());
				bredCrumbValue=specifiedTab[1];
			}
		}*/
	
	/**
	 * @author smykjn
	 * @date 31st-May-2017
	 * @Objective This method validates following home screen section:
	 * <br>1.Plan Analytics<br>2.Plan information & status<br>3.Funds
	 * @return void
	 */
	public void validateHomeScreenSectionAfterLogin()
	{
		try{
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(iFramePlanB);
			Web.waitForElement(funds);
			if(planAnalytics.isDisplayed()&&planinfoAndStatus.isDisplayed()&&funds.isDisplayed())
				Reporter.logEvent(Status.PASS, "Validate Plan analytics,Plan information & status,"
						+ " and Funds section are displayed on home page.", "All specified sections are "
								+ "displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate Plan analytics,Plan information & status,"
						+ " and Funds section are displayed on home page.", "All specified sections are not"
								+ "displayed.", true);
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	}	
	

/**
 *@author smykjn
 *@date 31st-May-2017
* @Objective This method validates respective features against 
* following txn codes 'PSOVPG','PSWVRS','PSPROV','PSVSCH','ESCPPE','ESCVPE' in GUI
* @return void
*/
public void validatePlanSubmenuBasedOnTxnCodes()
{
	try{
		Web.getDriver().switchTo().defaultContent();
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(planMenu).build().perform();
		act.click(planMenu).build().perform();
		if(PlanOverviewTab.isDisplayed()){
			Reporter.logEvent(Status.PASS,"If txn code PSOVMN assigned,Overview menu is displayed.","Overview"
					+ " menu is displayed.", false);
			act.click(PlanOverviewTab).build().perform();
			if(planProvisionTab.isDisplayed()&&planVestingSchedules.isDisplayed()&&pptWebAndvruTab.isDisplayed())
				Reporter.logEvent(Status.PASS, "Validate Plan provisions,Vesting schedules and Participant web"
						+ " and VRU options are displayed.", "All specified tabs are displayed since respective codes('PSWVRS','PSPROV','PSVSCH') are"
								+ " assigned to user.", true);
			else
				Reporter.logEvent(Status.FAIL, "Validate Plan provisions,Vesting schedules and Participant web"
						+ " and VRU options are displayed.", "All specified tabs are displayed since respective codes('PSWVRS','PSPROV','PSVSCH') are"
								+ " assigned to user.", false);
		}
		else{
			Reporter.logEvent(Status.FAIL,"If txn code PSOVMN assigned,Overview menu is displayed.","Overview"
					+ " menu is not displayed.", true);
		}
		act.click(administrationTab).build().perform();
		if(payPlanExpense.isDisplayed())
			Reporter.logEvent(Status.PASS,"Check if Pay plan expenses tab is displayed.","Pay plan expenses tab is displayed as"
					+ "ESCPPE button is displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Check if Pay plan expenses tab is displayed.","Pay plan expenses tab is not displayed even"
					+ "ESCPPE button is displayed.",true);
		act.click(payPlanExpense).build().perform();
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().frame(iFramePlanB);
		if(Web.isWebElementDisplayed(viewPlanInvoicesBtn, true))
			Reporter.logEvent(Status.PASS,"Check if user can view plan invoices.","User can view plan invoices.", false);
		else
			Reporter.logEvent(Status.FAIL,"Check if user can view plan invoices.","User can not view plan invoices.", true);
		Web.getDriver().switchTo().defaultContent();
		Web.clickOnElement(homePageLogo);
		Web.waitForPageToLoad(Web.getDriver());
	 }
	catch(Exception e)
	{
			  e.printStackTrace();
	}
		
		
}


/**
 *@author smykjn
 *@date 02-June-2017
* @Objective This method validates respective features against 
* following txn codes 'PSCTOD','EMFIAR','PSCCTR' in GUI
* @return void
*/
public void validateOtherHomePageSecutiryTXNCodes()
{
	try{
		Web.getDriver().switchTo().frame(iFramePlanB);
		if(toDoTab.isDisplayed()&&fileSharingTab.isDisplayed()&&complianceTabToolTip.isDisplayed())
			Reporter.logEvent(Status.PASS,"Check Todo,File sharing and compliance tabs are displayed.","All mentioned"
					+ " tabs are displayed.", false);
		else
		Reporter.logEvent(Status.FAIL,"Check Todo,File sharing and compliance tabs are not displayed.","All mentioned"
				+ " tabs are displayed.", true);
	 }
	catch(Exception e)
	{
			  e.printStackTrace();
	}
		
		
}


/**
 *@author smykjn
 *@date 02-June-2017
* @Objective This method validates respective features against 
* following txn codes 'PSWVRS','PSPROV','PSVSCH','PSCTOD','EMFIAR','ESCPPE' in GUI after deleting them from DB
* @return void
*/
public void validateHomePageScreenAfterDeletingTxnCodes1()
{
	try{
		Web.getDriver().switchTo().defaultContent();
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(planMenu).build().perform();
		act.click(planMenu).build().perform();
		act.click(PlanOverviewTab).build().perform();
		try{
			planProvisionTab.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSPROV txn code and check plan provisions tab is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete PSPROV txn code and check plan provisions are not displayed.","As expected",false);
			e.getStackTrace();
		}
		try{
			planVestingSchedules.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSVSCH txn code and check vesting schedules tab is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete PSVSCH txn code and check vesting schedules tab is not displayed.","As expected",false);
			e.getStackTrace();
		}
		try{
			pptWebAndvruTab.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSWVRS txn code and check ppt web and vru tab is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete PSWVRS txn code and check ppt web and vru tab is not displayed.","As expected",false);
			e.getStackTrace();
		}
		act.click(administrationTab).build().perform();
		Thread.sleep(3000);
		try{
			payPlanExpense.isDisplayed();
			payPlanExpense.click();
			Reporter.logEvent(Status.FAIL,"Delete ESCPPE txn code and check pay plan expense tab is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete ESCPPE txn code and check pay plan expense tab is not displayed.","As expected",false);
			e.getStackTrace();
		}
		act.moveToElement(planMenu).build().perform();
		Web.getDriver().switchTo().frame(iFramePlanB);
		try{
			toDoTab.isDisplayed();
			toDoTab.click();
			Reporter.logEvent(Status.FAIL,"Delete PSCTOD txn code and check to-do's is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete PSCTOD txn code and check to-do's is not displayed.","As expected",false);
			e.getStackTrace();
		}
		try{
			fileSharingTab.isDisplayed();
			fileSharingTab.click();
			Reporter.logEvent(Status.FAIL,"Delete EMFIAR txn code and check file sharing is not displayed.","it is displayed.",true);
		}catch(Exception e)
		{
			Reporter.logEvent(Status.PASS,"Delete EMFIAR txn code and check file sharing is not displayed.","As expected",false);
			e.getStackTrace();
		}
	 }
	catch(Exception e)
	{
			  e.printStackTrace();
	}
		
		
}

	
/**
 *@author smykjn
 *@date 02-June-2017
* @Objective This method validates respective features against 
* following txn codes 'PSOVPG','PSOVMN' in GUI after deleting them from DB
* @return void
*/
public void validateOverviewAndPlanDashboardAfterDeletingTxnCodes()
{
	try{
		Web.getDriver().switchTo().defaultContent();
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(planMenu).build().perform();
		act.click(planMenu).build().perform();
		try{
			PlanOverviewTab.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSOVMN txn code and check plan overview page is not displayed.","Plan-->Overview tab is displayed.",true);
		}catch(Exception e){
			Reporter.logEvent(Status.PASS,"Delete PSOVMN txn code and check plan overview page is not displayed.","User"
					+ " can not click on plan-->overview sicne Overview tab is not found hence plan overview page is"
					+ " not displayed.",false);
		}
		Web.getDriver().switchTo().frame(iFramePlanB);
		try{
			planAnalytics.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSOVPG txn code from DB and check plan analytics section is not displayed.","Plan Analytics"
					+ " section is displayed.", true);}
		catch(Exception e){
			Reporter.logEvent(Status.PASS,"Delete PSOVPG txn code from DB and check plan analytics section is not displayed.","As expected.", false);
		}
		try{
			planinfoAndStatus.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSOVPG txn code from DB and check plan info and status section is not displayed.","Mentioned section "
					+ "is displayed.", true);}
		catch(Exception e){
			Reporter.logEvent(Status.PASS,"Delete PSOVPG txn code from DB and check dashboard pages are not displayed.","As expected.", false);
		}
		try{
			funds.isDisplayed();
			Reporter.logEvent(Status.FAIL,"Delete PSOVPG txn code from DB and check Funds section are not displayed.","Mentioned section"
					+ " is displayed.", true);}
		catch(Exception e){
			Reporter.logEvent(Status.PASS,"Delete PSOVPG txn code from DB and check Funds section are not displayed.","As expected.", false);
		}
	 }
	catch(Exception e)
	{
			  e.printStackTrace();
	}
		
		
}	
	

/**
 *<pre>This method is used to navigate to home page.</pre>
 * @author smykjn
 * @return
 */
public boolean navigateToHomePage() throws Exception{
	boolean isDisplayed=false;
	Web.getDriver().switchTo().defaultContent();
	Actions act = new Actions(Web.getDriver());
	act.moveToElement(weGreeting).build().perform();
	Web.clickOnElement(homePageLogo);
	Web.waitForPageToLoad(Web.getDriver());
	if(Web.isWebElementDisplayed(iFramePlanB, true))
		isDisplayed=true;
	else
		isDisplayed=false;
	return isDisplayed;
}
	public boolean navigatedToMenuPage(String...menuNamesAndBreadCrumbs)
	{
		boolean isNavigated = false;
		int counter = 0,navigated=0;
		String[] menuNames  = null,breadCrumbs = null;
		String concatedMenuNames = null, concatedBreadCrumbs = null;
		try
		{
			
			if(menuNamesAndBreadCrumbs.length>0)
			{
				concatedMenuNames = menuNamesAndBreadCrumbs[0];
				concatedBreadCrumbs = menuNamesAndBreadCrumbs[1];
				menuNames = concatedMenuNames.split(",");
				breadCrumbs = concatedBreadCrumbs.split(",");

				for(String menuName : menuNames){
					WebElement menuItem = menuElement(menuName);
					if(menuItem.isDisplayed())
						Web.clickOnElement(menuItem);
					Thread.sleep(5000);
					if(pageRedirected(breadCrumbs[counter]))
						navigated++;
					counter++;
				}
			}
			if(navigated==breadCrumbs.length)
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return isNavigated;
	}
	
	public boolean navigatedFromActionButton(String...actionButtonAndBreadCrumbs)
	{
		int counter = 0,navigated=0;
		String[] actionButtonNames  = null,breadCrumbs = null;
		String concatenatedButtonNames = null, concatenatedBreadCrumbs = null;
		try
		{
			if(actionButtonAndBreadCrumbs.length>0)
			{
				concatenatedButtonNames = actionButtonAndBreadCrumbs[0];
				concatenatedBreadCrumbs = actionButtonAndBreadCrumbs[1];
				actionButtonNames = concatenatedButtonNames.split(",");
				breadCrumbs = concatenatedBreadCrumbs.split(",");

				for(String buttonName : actionButtonNames){
					Web.clickOnElement(this.homePageLogo);
					Web.waitForElement(this.weGreeting);
					Web.getDriver().switchTo().defaultContent();
					Web.getDriver().switchTo().frame("frameb");
					WebElement buttonElement = returnActionButtonsOnHomePage(buttonName);
					if(buttonElement.isDisplayed())
						Web.clickOnElement(buttonElement);
					Thread.sleep(5000);
					if(pageRedirected(breadCrumbs[counter]))
						navigated++;
					counter++;
				}
			}
			if(navigated==breadCrumbs.length)
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
	private boolean pageRedirected(String... breadCrumbsAsPageIdentifiers) {
		if(breadCrumbsAsPageIdentifiers.length>0)
		{
			for(String breadCrumb : breadCrumbsAsPageIdentifiers)
			{
				if(validateBreadCrumb(breadCrumb))
					return true;
			}
		}

		return false;
	}

	private boolean validateBreadCrumb(String expBreadCrumbEndString){
		boolean isValidated = false;
		try{
			int count=0;
			Web.getDriver().switchTo().defaultContent();
			for(WebElement crumb : breadCrumb)
			{
				if(crumb.isDisplayed()){
					break;
				}
				count++;
			}
			String brdcrmbstring = breadCrumb.get(count).getText();
			if(brdcrmbstring.contains(expBreadCrumbEndString)){
				isValidated = true;
				Reporter.logEvent(Status.PASS,"Validate bread crumb for "+expBreadCrumbEndString+" page.",""
						+ "Below bread crumb is displayed on top.\n"+brdcrmbstring, false);}
			else
				Reporter.logEvent(Status.FAIL,"Validate bread crumb for "+expBreadCrumbEndString+" page.",""
						+ "Below bread crumb is not displayed", true);
		}catch(Exception e){
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL,"Exception occurred.",e.getMessage(),true);
		}
		return isValidated;
	}
	
	public boolean navigateToProvidedPage(String...specifiedTab) throws Exception
    {
             String bredCrumbValue= "";
             WebElement breadCrumb;
             boolean isPageDisplayed = false;
             Actions act = new Actions(Web.getDriver());
             String xpath1 = "//a[contains(text(),'"+specifiedTab[0]+"')]/following-sibling::ul";
             String xpath2 = "//a[contains(text(),'"+specifiedTab[1]+"')]/following-sibling::ul";
             String xpath3 = "//a[contains(text(),'"+specifiedTab[0]+"')]/following-sibling::ul//a[contains(text(),'"+specifiedTab[1]+"')]";
             String xpath4 = "//a[contains(text(),'"+specifiedTab[1]+"')]/following-sibling::ul//a[.='"+specifiedTab[2]+"']";
             if(Web.getDriver().findElements(By.xpath(xpath1)).size()>0){
                       act.moveToElement(Web.returnElement(new HomePage(),"Welcome")).build().perform();
                       //Web.clickOnElement(menuElement(specifiedTab[0]));
                       act.moveToElement(menuElement(specifiedTab[0])).click().build().perform();
                       //act.click(menuElement(specifiedTab[0])).build().perform();
                       Web.waitForPageToLoad(Web.getDriver());
                       if(Web.getDriver().findElements(By.xpath(xpath2)).size()>0)
                       {
                                Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath3)));
                                if(Web.getDriver().findElements(By.xpath(xpath4)).size()>0){
                                Web.isWebElementDisplayed(Web.getDriver().findElement(By.xpath(xpath4)), true);
                                act.click(Web.getDriver().findElement(By.xpath(xpath4))).build().perform();
                                Web.waitForPageToLoad(Web.getDriver());
                                bredCrumbValue=specifiedTab[2];}
                       }
                       else
                       {
                                Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath3)));
                                Web.waitForPageToLoad(Web.getDriver());
                                bredCrumbValue=specifiedTab[1];
                       }
             }
             else
             {
                       Web.clickOnElement(menuElement(specifiedTab[0]));
                       Web.waitForPageToLoad(Web.getDriver());
                       bredCrumbValue=specifiedTab[0];
             }
             breadCrumb = Web.getDriver().findElement(By.tagName("i"));
             Web.isWebElementDisplayed(breadCrumb,true);
             Thread.sleep(2000);
             if(Web.getDriver().findElement(By.tagName("i")).getText().contains(bredCrumbValue))
                       isPageDisplayed = true;
             else
                       isPageDisplayed = false;
             return isPageDisplayed;
    }
	
	public static Map<String,String> securityQuestionsAndAnswers;
	public static Map<String,Map<String,String>> userSecurityQuestion;
	public static Map<String,Map<String,String>> restoreUserSecurityQuestion;
/**
 * @author smykjn
 * <pre>Set user security answer.<pre>
 */
public void setUserSecurityAnswer() throws SQLException{
	List<String> users = Arrays.asList(Stock.getConfigParam("UserIds").split("|"));
	userSecurityQuestion = new HashMap<String,Map<String,String>>();
	for(int i=0;i<users.size();i++){
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserSecurityQuestion")[0],
				Stock.getTestQuery("getUserSecurityQuestion")[1],
				"K_"+users.get(i));
		securityQuestionsAndAnswers = new HashMap<String,String>();
		while(queryResultSet.next()){
			String secQuestion = queryResultSet.getString("SECURITY_QUESTION");
			String encodedSecAnswer =  queryResultSet.getString("ENCRYPTED_SECURITY_ANSWER");
			
			securityQuestionsAndAnswers.put(secQuestion, encodedSecAnswer);
		}
		//userSecurityQuestion.put(users.get(i), securityQuestionsAndAnswers);
	}
	System.out.println(userSecurityQuestion);
}

/**
 * method to check for access to FileSharing.
 */

/**<pre>Method to check if a given transaction code already exists in user_auth_txn table<br> for any USCS ID of a given user</br></pre> 
 * @param query to be used
* @param userName given user
* @param txnCode given transaction code
* @return
*/
public static boolean isTxnCode(String[] query,String userName,String txnCode){
      try{
           queryResultSet = DB.executeQuery(query[0], query[1], "K_"+userName,txnCode);
           int rowCount = 0;
           while(queryResultSet.next()){
                if(queryResultSet.last()){
                      rowCount = queryResultSet.getRow();
                      if(rowCount>0)
                           return true;
                }
           }
      }
      catch(Exception e){
           e.printStackTrace();
      }
      return false;
}

/**<pre> Method to insert the transaction code for a given USCS ID. Before inserting <br>it verifies if txn code exists for given user otherwise it inserts</br><pre>
* @param query insert query to be used
* @param userName User name 
 * @param txnCode Transaction code to be inserted.
* @param uscsId <pre>USCS id for which txn code need to be inserted.This can be <br>fetched from <b>user#user_class</b> table</br></pre>
* @return
*/
public static boolean insertTxnCode(String[] query,String userName,String txnCode,String uscsId){
      try{
           if(isTxnCode(Stock.getTestQuery("getTransactionCodeAvailability"),userName,txnCode))
                return true;
           else{
                int rowsUpdated = DB.executeUpdate(query[0], query[1], txnCode,uscsId,"0","N");
                if(rowsUpdated>0)
                      return true;
           }
      }
      catch(Exception e){
           e.printStackTrace();
      }
      return false;
}
}
