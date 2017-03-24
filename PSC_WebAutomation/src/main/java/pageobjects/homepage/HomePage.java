package pageobjects.homepage;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.DB;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

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
	@FindBy(css = "a[id = 'profileLink']") private WebElement myProfileLink;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
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
	@FindBy(xpath = ".//*[@id='main']/div/a")
	private WebElement homeLink;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/a")
	private WebElement planMenu;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/a")
	private WebElement planAdministration;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/ul/li[1]/a")
	private WebElement planAdministrationToDo;
	@FindBy(xpath = "html/body/h4[text()='Disbursement requests']")
	private WebElement disbursementRequest;
	@FindBy(xpath = ".//*[@id='PlanSummaryMain']//div/ul/li")
	private List<WebElement> restyledHomeButtons;
	/*-----------------------------------------------------------------*/
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;
	
	Map<String,String> securityAnsMap=null;

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
			//	Reporter.logEvent(Status.PASS, "Check if Home page is loaded","Home page has loaded successfully",false);
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
					userVeriData = new String[]{Stock.GetParameterValue("userVerificationEmail"),
					          getSecurityAnswer((Web.returnElement(userVeriPg, "SECURITYQUESTION")).getText().trim())};
					/*userVeriData[0] = userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));
					userVeriData[1] = Stock.GetParameterValue("userVerificationAns");*/
					invokeMethodforUserVerification = userVeriPg.getClass().getDeclaredMethod("performVerification",String[].class);
					invokeMethodforUserVerification.invoke(userVeriPg,new Object[]{userVeriData});
				}else{
					//navigate to Home
				}
			}
			//urlJumpPage.click();
			Web.clickOnElement(urlJumpPage);
			Web.waitForElement(weGreeting);
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

	public void logoutPSC(){
		Web.getDriver().switchTo().defaultContent();
		if(Web.isWebElementDisplayed(logoutLink,true)){
			logoutLink.click();
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
		Web.waitForPageToLoad(Web.getDriver());
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

}
