package psc.jumppage;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import psc.login.LoginPage;
import psc.userverification.UserVerificationPage;

import com.aventstack.extentreports.Status;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class JumpPage extends LoadableComponent<JumpPage> {
	
	@FindBy(id="jumpPage")
	private WebElement jumpPage;
	@FindBy(xpath=".//*[@class='headerBlock']/h1")
	private WebElement jumpPageHeader;
	@FindBy(xpath="//*[@id='jumpPageInput']")
	private WebElement jumpPageSearchBox;
	@FindBy(xpath=".//*[@id='jumpPageTable_data']//a")
	private List<WebElement> jumpPageSiteLinks;
	@FindBy(xpath="//span[@class='jumpPageText']")
	private List<WebElement> jumpPageText;
	@FindBy(xpath="//*[@id='jumpPageList']/option")
	private WebElement jumpPageList;
	@FindBy(xpath=".//*[@Class='lastLink']//a[contains(text(),'Logout')]")	
	private WebElement logoutLink;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(css="div[id='greeting'] span[class='label']") private WebElement weGreeting;
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;
	ResultSet resultset;
	
	private static String jumpageList="//*[@id='jumpPageList']/option";
	public JumpPage() {
		this.parent = new UserVerificationPage();
		PageFactory.initElements(Web.getDriver(), this);
	}

	public JumpPage(LoadableComponent<?> parent,boolean performVerification,String... userData) {
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData; 
		this.userVeriData = new String[2];		
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(jumpPageHeader));
	}
	
	@Override
	protected void load() {
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj =  new LoginPage();
		try{			
			if(ifUserOrAccntVerificationMandate) {		
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
					/*userVeriData[0] = userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));*/
					userVeriData[0] = Stock.GetParameterValue("UserVeriEmail");
					userVeriData[1] = Stock.GetParameterValue("userVerificationAns");
					invokeMethodforUserVerification = userVeriPg.getClass().getDeclaredMethod("performVerification",String[].class);
					invokeMethodforUserVerification.invoke(userVeriPg,new Object[]{userVeriData});
				}else{
					//navigate to Home
				}
			}
			//urlJumpPage.click();
			Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
		} catch (Exception e) {
			try {
				throw new Exception("Login to PSC failed , Error description : "+ e.getMessage());
			} catch (Exception t) {			
				ThrowException.Report(TYPE.EXCEPTION,t.getMessage());
			}
		}

	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("LogOut Link")) {
			return this.logoutLink;
		}
		
		return null;
	}
	
	
	/**
	 * This method verifies the UI of Jump Page.(JumpPage text,Search box,Site links,Jump Page header)
	 */
	public void jumpPageUIValidation()
	{
		try
		{
			String jumpageHeader = jumpPageHeader.getText();
			int siteAccessLinks = jumpPageSiteLinks.size();
			WebElement searchBox = jumpPageSearchBox;
			//String dBName = CommonLib.getParticipantDBName(Stock.GetParameterValue("username"))+"DB_"+CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV"));
			resultset = DB.executeQuery(Stock.getTestQuery("getAccuCode")[0],
					Stock.getTestQuery("getAccuCode")[1],
					"K_"+Stock.GetParameterValue("username"));
			int numberOfSiteAccess = DB.getRecordSetCount(resultset);
			if(jumpageHeader.equalsIgnoreCase("Site selection") && siteAccessLinks==numberOfSiteAccess && Web.isWebElementDisplayed(searchBox, true))
			{
				Reporter.logEvent(Status.PASS,"Verify Search plan Box,Site links,header on Jump page.","All the mentioned elements are displayed.",false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify Search plan Box,Site links,header on Jump page.","All the mentioned elements are not displayed.",true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method verifies the Search plan Box functionality of Jump Page.(JumpPage text,Search box,Site links,Jump Page header)
	 */
	public void jumpPageSearchPlanBoxValidation() throws Exception
	{
		Set<String> planIDs = new LinkedHashSet<String>();
			resultset = DB.executeQuery(Stock.getTestQuery("getAccuCode1")[0],""
					+Stock.getTestQuery("getAccuCode1")[1],"K_"+Stock.GetParameterValue("username"));
			while(resultset.next())
			{
				planIDs.add(resultset.getString("GA_ID"));
				System.out.println("Plan Id is:"+resultset.getString("GA_ID"));
				break;
			}
			for(String str : planIDs){
				Web.setTextToTextBox(jumpPageSearchBox, str);
				Thread.sleep(2000);
				WebElement option = Web.getDriver().findElement(By.xpath(jumpageList));
				try{
					Web.isWebElementDisplayed(option, false);
				}catch(Exception e){
					option = Web.getDriver().findElement(By.xpath(jumpageList));
				}
				if(option.getText().contains(str))
				{
					Reporter.logEvent(Status.PASS,"Verify Search plan Box options when a valid plan number "+str+" is entered.","Plan '"+option.getText()+"' is displayed as user enters the valid plan number '"+str+"'.",false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,"Verify Search plan Box options when a valid plan number is "+str+" is entered.","Plan '"+option.getText()+"' is displayed as user enters the valid plan number '"+str+"'.",true);
				}
			}
		}
	
	public void logoutPSCFromJumpage(){
		Web.getDriver().switchTo().defaultContent();
		if(Web.isWebElementDisplayed(logoutLink,true)){
			logoutLink.click();
			Reporter.logEvent(Status.PASS,"Perform logout of PSC","Logged out of PSC successfully",false);
		}else{
			Reporter.logEvent(Status.FAIL,"Perform logout of PSC","Unable to log out of PSC application",true);
		}
	}


/**
 * This method clicks on the Jump page URL
 */
public void ClickOnJumpPageURL() throws Exception
{
	Web.getDriver().switchTo().defaultContent();
	Web.waitForElement(urlJumpPage);
	Web.clickOnElement(urlJumpPage);
	Web.waitForPageToLoad(Web.getDriver());
	Web.waitForElement(weGreeting);
	if(weGreeting.isDisplayed())
		Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
	else
		Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is not successfull",false);
}
	
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

