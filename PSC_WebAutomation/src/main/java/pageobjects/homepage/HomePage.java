package pageobjects.homepage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

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
	@FindBy(xpath=".//div[@class='button-row']//button[.='More']")
	private WebElement moreButton;
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
	/*-----------------------------------------------------------------*/
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;

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
		if(!Web.isWebElementDisplayed(weGreeting,true)){
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
					userVeriData[0] = userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));
					userVeriData[1] = Stock.GetParameterValue("userVerificationAns");
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
				expMenuMap.put(expectSubMenuItems.get(i),new LinkedList<String>());
			}
			else
			{
				expMenuMap.put(expectSubMenuItems.get(i), Arrays.asList(Stock.GetParameterValue(expectSubMenuItems.get(i)).split(",")));
			}
		}
		System.out.println("Expected map is:"+expMenuMap);
		Web.waitForPageToLoad(Web.getDriver());
		Actions act = new Actions(Web.getDriver());
		try{
				if(menuItem.getText().equals(menu))
				{
					Web.clickOnElement(menuItem);
					Thread.sleep(3000);
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
		/*finally
		{
			expectSubMenuItems.clear();
			actualSubMenuItems.clear();
			actualSubSubMenuItems.clear();
			actMenuMap.clear();
			expMenuMap.clear();
			
			
		}*/
	
	}
	
	
	
	
	
	
	

}
