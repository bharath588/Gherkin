/**
 * 
 */
package plan;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;

import com.aventstack.extentreports.Status;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import framework.util.CommonLib;

/**
 * @author smykjn
 *
 */
public class PlanPage extends LoadableComponent<PlanPage>{
	
	@FindBy(css="div[id='greeting'] span[class='label']") private WebElement weGreeting;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(xpath=".//div[@class='breadcrumb']//i")
	private WebElement invAndPerformance;
	@FindBy(xpath=".//*[@id='addMessagesTab']/a")
	private WebElement addNewMessageTab;
	@FindBy(id="frameb")
	private WebElement frameb;
	@FindBy(xpath="//div//h3[contains(text(),'Plan Messaging')]")
	private WebElement planMsgingTitle;
	@FindBy(xpath=".//ul[@class='multiselect-search-list']//input")
	private WebElement planMsgSearch;
	@FindBy(xpath=".//li[contains(@class,'search-list-item_loader')]")
	private WebElement loader;
	@FindBy(xpath=".//div[@class='multiselect-dropdown']//li")
	private List<WebElement> plnDrpDwnList;
	@FindBy(xpath=".//td[contains(@class,'ui-datepicker-today')]//following-sibling::*[1]//a")
	private WebElement nextDayDate;
	@FindBy(xpath="//*[@id='date-picker-begin']//following-sibling::label")
	private WebElement datePickerBegin;
	@FindBy(xpath="//*[@id='date-picker-end']//following-sibling::label")
	private WebElement datePickerEnd;
	@FindBy(xpath=".//td[contains(@class,'ui-datepicker-today')]//following-sibling::*[2]//a")
	private WebElement nextToNextDayDate;
	@FindBy(id="selectMessage")
	private WebElement selectMsgLink; 
	@FindBy(xpath=".//td[contains(@id,'planMessage')]//input")
	private List<WebElement> planMessageListRadioButtons;
	@FindBy(xpath="//*[@id='planMessagingModel']//div[@class='modal-footer']//button[.='Ok']")
	private WebElement plnMsgOkBtn;
	@FindBy(xpath=".//label[contains(text(),'Message preview')]/../following-sibling::div//div")
	private WebElement plnaMsgDivBox;
	@FindBy(xpath=".//*[@id='addMessages']//button[contains(text(),'Submit')]")
	private WebElement submitPlnMsgBtn;
	@FindBy(xpath=".//*[@id='addMessages']//button[contains(text(),'Cancel')]")
	private WebElement cancelPlnMsgBtn;
	@FindBy(xpath=".//div[@type='success']//span/span")
	private WebElement plnMsgConfrmTitle;
	@FindBy(xpath=".//*[@id='PlanMsngModule']//button[.='Ok']")
	private WebElement okBtnOnPlnConfrmPage;

	
	HomePage homePage;
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;
	
	Map<String,String> securityAnsMap=null;
	ResultSet queryResultSet;

	public PlanPage(LoadableComponent<?> parent,boolean performVerification,String... userData){
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData; 
		this.userVeriData = new String[2];		
		PageFactory.initElements(Web.getDriver(), this);
	}

	public PlanPage(){
		PageFactory.initElements(Web.getDriver(), this);
	}


	@Override
	protected void isLoaded() throws Error {	
		if(!Web.isWebElementDisplayed(weGreeting,false)){
			//CommonLib.waitForProgressBar();
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
					/*userVeriData = new String[]{userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),Stock.GetParameterValue("username")),
					          getSecurityAnswer((Web.returnElement(userVeriPg, "SECURITYQUESTION")).getText().trim())};*/
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
			Web.waitForElement(urlJumpPage);
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

	

/**
 * <pre>This method navigates to Plan--->Investments & Performance</pre>
 * @author smykjn
 * @Date 5th-May-2017
 * @return void
 */
public void navigateToInvestmentAndPerformace() throws Exception
{
	homePage = new HomePage();
	homePage.navigateToProvidedPage("Plan","Administration","Plan messaging");
	Web.getDriver().switchTo().defaultContent();
	if(invAndPerformance.getText().contains("Plan messaging"))
		Reporter.logEvent(Status.PASS,"Navigate to Plan-->Administration-->Plan messaging page.","Plan messaging page is"
				+ " displayed.", false);
	else
		Reporter.logEvent(Status.FAIL,"Navigate to Plan-->Administration-->Plan messaging page.","Plan messaging page is"
				+ " not displayed.", true);
}




/**
 * <pre>This method creates new plan message on Plan messaging page under 'Add new Message' page.</pre>
 * @author smykjn
 * @Date 5th-May-2017
 * @return void
 */
public void addNewPlanMessage() 
{
	String planNumber="";
	try{
		Web.getDriver().switchTo().frame(frameb);
		if(Web.isWebElementDisplayed(addNewMessageTab, true))
		{
			Web.clickOnElement(addNewMessageTab);
			Web.waitForElement(planMsgingTitle);
			if(submitPlnMsgBtn.isDisplayed()&&cancelPlnMsgBtn.isDisplayed())
				Reporter.logEvent(Status.PASS, "Validate Submit and cancel button is displayed on Add New Message page.",""
						+"Submit and Cancel buttton is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate Submit and cancel button is displayed on Add New Message page.",""
						+"Submit or Cancel buttton is not displayed.", true);
			queryResultSet = DB.executeQuery(Stock.getTestQuery("queryTofindPlansForNextGen")[0],
					Stock.getTestQuery("queryTofindPlansForNextGen")[1],"K_"+Stock.GetParameterValue("username"));
			while(queryResultSet.next())
			{
				planNumber = queryResultSet.getString("GA_ID");
				break;
			}
			Web.setTextToTextBox(planMsgSearch, planNumber);
			do{
				Thread.sleep(2000);
				System.out.println("Loading,Have Patience..");
			}while(loader.isDisplayed());
			if(Web.isWebElementsDisplayed(plnDrpDwnList, true))
				Web.clickOnElement(plnDrpDwnList.get(0));
			Web.clickOnElement(datePickerBegin);
			Web.waitForElement(nextDayDate);
			Web.clickOnElement(nextDayDate);
			Web.clickOnElement(datePickerEnd);
			Web.waitForElement(nextToNextDayDate);
			Web.clickOnElement(nextToNextDayDate);
			Web.clickOnElement(selectMsgLink);
			Web.isWebElementsDisplayed(planMessageListRadioButtons,true);
			Web.clickOnElement(planMessageListRadioButtons.get(0));
			Web.clickOnElement(plnMsgOkBtn);
			Web.waitForElement(planMsgingTitle);
			Reporter.logEvent(Status.INFO, "Plan Message to be displayed:", plnaMsgDivBox.getText(), false);
			Web.clickOnElement(submitPlnMsgBtn);
			Web.waitForElement(plnMsgConfrmTitle);
			if(plnMsgConfrmTitle.isDisplayed())
				Reporter.logEvent(Status.PASS,"Fill all mandatory fields on Add new Plan message page and submit.","Comfirmation message:'"+plnMsgConfrmTitle.getText()+"' is displayed."
						+ "", false);
			else
				Reporter.logEvent(Status.FAIL,"Fill all mandatory fields on Add new Plan message page and submit.","Comfirmation message:'"+plnMsgConfrmTitle.getText()+"' is not displayed.", true);
			Web.clickOnElement(okBtnOnPlnConfrmPage);
			if(Web.isWebElementDisplayed(addNewMessageTab, true))
				Reporter.logEvent(Status.PASS,"Click on OK button on confirmation page.","user is navigated back to Plan messaging page.", false);
			else
				Reporter.logEvent(Status.FAIL,"Click on OK button on confirmation page.","user is not navigated back to Plan messaging page.", true);
		Web.getDriver().switchTo().defaultContent();
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Validate if Add new Message tab is displayed.","Ad new message tab is not displayed", true);
		}
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
