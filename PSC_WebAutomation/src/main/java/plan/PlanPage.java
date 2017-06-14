/**
 * 
 */
package plan;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;

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
	@FindBy(linkText="Investment options")
	private WebElement investmentOptions;
	@FindBy(linkText="Documents")
	private WebElement documents;
	@FindBy(linkText="Charts")
	private WebElement charts;
	@FindBy(linkText="Asset allocation models")
	private WebElement asstAllocModels;
	@FindBy(linkText="Fixed investment rates")
	private WebElement fxdInvstmntRates;
	@FindBy(linkText="Unit/share values")
	private WebElement unitShareValues;
	@FindBy(xpath=".//*[@id='fixedInvestmentRate']//th")
	private List<WebElement> fixdInvstmntColumn;
	@FindBy(id="date-picker-begin")	
	private WebElement unitShareFromDate;
	@FindBy(id="date-picker-end")	
	private WebElement unitShareEndDate;
	@FindBy(xpath=".//*[contains(@id,'tabForm:headerInfob')]//a")
	private WebElement printLinkUnderPlanMenu;
	@FindBy(className="thumbnailModel")	
	private WebElement customModal;
	@FindBy(xpath=".//*[@class='panel-title']//span[contains(text(),'View Graph')]")
	private WebElement viewGraph;
	@FindBy(xpath=".//*[@class='panel-title']//span[contains(text(),'View Investment Options')]")
	private WebElement viewInvestmentOption;
	@FindBy(name="ADD_NEW_USER_BUTTON")	
	private WebElement addNewUserBtn;
	@FindBy(xpath=".//*[@class='subTitleDiv'][contains(text(),'Add New User')]")
	private WebElement addNewUserTitle;
	@FindBy(name="firstName")
	private WebElement firstNameInput;
	@FindBy(xpath=".//*[@name='firstName'][@class='nonEditableFields']")
	private WebElement firstNameDisabled;
	@FindBy(name="lastName")
	private WebElement lastNameInput;
	@FindBy(xpath=".//*[@name='lastName'][@class='nonEditableFields']")
	private WebElement lastNameDisabled;
	@FindBy(name="email")
	private WebElement emailInput;
	@FindBy(xpath=".//*[@name='email'][@class='nonEditableFields']")
	private WebElement emailDisabled;
	@FindBy(id="spinner")
	private WebElement spinner;
	@FindBy(id="continuebutton")
	private WebElement continueBtn;
	@FindBy(xpath=".//table[@id='permissions']")
	private WebElement userAccessTable;
	@FindBy(xpath=".//select[@name='selectedAnswer_5']")
	private WebElement userAccessdropDwon1;
	@FindBy(xpath="(.//select[@name='selectedAnswer_0'])[1]")
	private WebElement userAccessdropDwon2;
	@FindBy(id="availablePlans")
	private WebElement availablePlan;
	private String autoCompletePlanLink = ".//ul[contains(@class,'ui-autocomplete')]//a";
	@FindBy(id="createuserbutton")
	private WebElement createUserBtn;
	@FindBy(xpath=".//div[@class='yellowbox']//span")
	private WebElement confirmMsgBox;
	@FindBy(name="addPlans")
	private WebElement addPlanBtn;@FindBy(xpath=".//*[@value='Go']")
	private WebElement goBtn;
	@FindBy(xpath=".//*[@id='searchListDataTable']//td")
	private List<WebElement> searchedResultsColumns;@FindBy(name="logonId")
	private WebElement assignedUserName;
	@FindBy(name="userId")
	private WebElement searchUserId;@FindBy(linkText="Show Advanced Search")
	private WebElement showAdvSearchedLink;@FindBy(id="editButton")
	private WebElement editButtonn;
	@FindBy(id="OVERRIDE_DIV1")
	private WebElement ignoreDivisionYes;
	@FindBy(id="PARAM_START_DATE")
	private WebElement beginDate;
	@FindBy(id="PARAM_END_DATE")
	private WebElement endDate;
	@FindBy(id="PARAM_PPT_IDENTIFIER")
	private WebElement pptIdentifier;
	@FindBy(id="PARAM_SORT_ORDER")
	private WebElement pptSortOrder;
	@FindBy(id="EXPORT_FORMAT")
	private WebElement reportFormat;
	@FindBy(xpath=".//button[.='Submit']")
	private WebElement submitBtnforQDIA;
	@FindBy(css="table#progressMessageContainer div:nth-of-type(1)")
	private WebElement QDIALoader;
	@FindBy(xpath="(.//*[@id='scheduled']//div[@class='bulletinAlignment'])[1]")
	private WebElement QDIArequestConfirmation;
	@FindBy(id="orderForm")
	private WebElement orderForm;
	@FindBy(xpath=".//div[@ng-show='scheduledReport.loading']")
	private WebElement QDIALoader2;
	@FindBy(id="BASIS")
	private WebElement division;
	@FindBy(xpath="(.//div[contains(@class,'checkboxLayer')]//label//span)[2]")
	private WebElement subDivision;
	@FindBy(xpath=".//button[@id='']")
	private WebElement subDiviBtn;
	
	private String menuQDIA = "//a[contains(text(),'Participant QDIA notice listing order')]";
	private String newUserAssignedID = "";
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
			try{
				if(urlJumpPage.isDisplayed())
				Web.waitForElement(urlJumpPage);
				Web.clickOnElement(urlJumpPage);
				Web.waitForElement(weGreeting);
				Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
			}catch(Exception e){
				Web.waitForElement(weGreeting);
			}
		} catch (Exception e) {
			try {
				throw new Exception("Login to PSC failed , Error description : "+ e.getMessage());
			} catch (Exception t) {			
				ThrowException.Report(TYPE.EXCEPTION,t.getMessage());
			}
		}
	}

	

/**
 * <pre>This method navigates to Plan--->Administration-->Plan messaging page</pre>
 * @author smykjn
 * @Date 5th-May-2017
 * @return void
 */
public void navigateToPlanMessagingPage() throws Exception
{
	homePage = new HomePage();
	homePage.navigateToProvidedPage("Plan","Administration","Plan messaging");
	Web.getDriver().switchTo().defaultContent();
	if(Web.getDriver().findElement(By.tagName("i")).getText().contains("Plan messaging"))
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
	



/**
 * <pre>This method Validates columns on Investments & Performance page.
 * Investment options,Documents,Charts,Asset allocation models,Fixed investment rates,Unit/share values</pre>
 * @author smykjn
 * @Date 5th-June-2017
 * @return void
 */
public void validateInvestmentAndPerformanceColumns() 
{
	try{
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frameb);
		Web.isWebElementDisplayed(asstAllocModels, true);
		if(investmentOptions.isDisplayed()&&documents.isDisplayed()&&charts.isDisplayed()&&asstAllocModels.isDisplayed()
				&&fxdInvstmntRates.isDisplayed()&&unitShareValues.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate following columns are displayed on plan-->Investments & Performance"
					+ " page.1)Investment options 2)Documents 3)Charts 4) Asset allocation models 5)Fixed investment rates"
					+ " 6)Unit/share values", "Specified columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate following columns are displayed on plan-->Investments & Performance"
					+ " page.1)Investment options 2)Documents 3)Charts 4) Asset allocation models 5)Fixed investment rates"
					+ " 6)Unit/share values", "Specified columns are not displayed.", true);
		
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
 * <pre>This method Validates columns of Fixed investment rates page.</pre>
 * @author smykjn
 * @Date 5th-June-2017
 * @return void
 */
public void validateColumnFixedInvstmntRatePage() 
{
	List<String> expColumn = Arrays.asList(Stock.GetParameterValue("ExpectedFixedInvstColumn").split(","));
	try{
		Web.clickOnElement(fxdInvstmntRates);
		Web.waitForElements(fixdInvstmntColumn);
		if(CommonLib.isAllHeadersDisplayed(fixdInvstmntColumn, expColumn))
			Reporter.logEvent(Status.PASS,"Click on Fixed investment rates and validate following columns are displayed."
					+expColumn,"All mentioned columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Fixed investment rates and validate following columns are displayed."
					+expColumn,"All/few mentioned columns are not displayed.", true);
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
 * <pre>This method Validates From and to field for Unit/Share values.</pre>
 * @author smykjn
 * @Date 5th-June-2017
 * @return void
 */
public void validateUnitShareFromToField() 
{
	try{
		Web.clickOnElement(unitShareValues);
		Web.waitForElement(unitShareFromDate);
		if(unitShareFromDate.isDisplayed()&&unitShareFromDate.isDisplayed())
			Reporter.logEvent(Status.PASS,"Click on Unit/share values button and check"
					+ " From and to date fields are displayed.","As expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Unit/share values button and check"
					+ " From and to date fields are displayed.","As expected.", true);
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
 * <pre>This method Validates a window will open once clicked on Print link</pre>
 * @author smykjn
 * @Date 5th-June-2017
 * @return void
 */
public void validatePrintFunctionality() 
{
	int count=0;
	try{
		Web.getDriver().switchTo().defaultContent();
		Web.clickOnElement(printLinkUnderPlanMenu);
		printLinkUnderPlanMenu.click();
		String parentWindow = Web.getDriver().getWindowHandle();
		System.out.println("Parent window:"+parentWindow);
		 while(Web.getDriver().getWindowHandles().size()==1)
	     {
	            if(count==10) break;
	            Thread.sleep(500);
	            count++;
	            System.out.println("Counter : "+count);
	     }
	     System.out.println("Window size is:"+Web.getDriver().getWindowHandles().size());
	     for(String newWindow : Web.getDriver().getWindowHandles())
	     {
	  	   if(!newWindow.equals(parentWindow))
	  	   {
	  		   	Web.getDriver().switchTo().window(newWindow);
	  		  if(Web.returnElement(new EmployeeSearch(), "PRINT PAGE HEADER").getText().contains("THIS IS A PRINT PREVIEW"))
				{
					Reporter.logEvent(Status.PASS, "Verify after clicking on print link,a print preview page is displayed.", "Print preview page is displayed.", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify after clicking on print link,a print preview page is displayed.", "Print preview page is not displayed.", true);
				}
	 				Web.getDriver().close();
	 				Web.getDriver().switchTo().window(parentWindow);
	 				break;
	  	   }
	     }
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}
	
	
/**
 * <pre>This method takes user to Plna-->Investment & Performance page</pre>
 * @author smykjn
 * @Date 05-June-2017
 * @return void
 */
public void navigateToInvestmentAndPerformance()
{
	WebElement breadCrumb;
	try{
		HomePage homePage = new HomePage();
		homePage.navigateToProvidedPage("Plan","Investments & Performance","");
		Web.getDriver().switchTo().defaultContent();
		breadCrumb = Web.getDriver().findElement(By.tagName("i"));
		Web.waitForElement(breadCrumb);
		if(Web.getDriver().findElement(By.tagName("i")).getText().contains("Investments & Performance"))
			Reporter.logEvent(Status.PASS,"Navigate to Plan-->Investment & Performance page.","Investment & Performance page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Navigate to Plan-->Investment & Performance page.","Investment & Performance page is not displayed.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		
	}
}	
	


/**
 * <pre>This method Validates Asset allocation models page's elements.</pre>
 * @author smykjn
 * @Date 6th-June-2017
 * @return void
 */
public void validateAssetAllocPage() 
{
	try{
		Web.clickOnElement(asstAllocModels);
		Web.waitForElement(customModal);
		if(customModal.isDisplayed()&&viewGraph.isDisplayed()&&viewInvestmentOption.isDisplayed())
			Reporter.logEvent(Status.PASS, "Click on Asset Allocation models tab and validate following "
					+ "section is displayed.1)Custom Model 2)View Graph 3)View Investment Options","All mentioned "
							+ "sections are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Click on Asset Allocation models tab and validate following "
					+ "section is displayed.1)Custom Model 2)View Graph 3)View Investment Options","All mentioned "
							+ "sections are not displayed.", true);
		
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}	


/**
 * <pre>This method Adds New User till Confirmation message appears.</pre>
 *@author smykjn
 *@date 09-June-2017
* @return void
*/
public void addNewUser()
{
	String fn = Stock.GetParameterValue("firstName");
	String ln = Stock.GetParameterValue("lastName");
	String email_ = Stock.GetParameterValue("userVerificationEmail");
	try{
		Web.waitForElement(frameb);
		Web.getDriver().switchTo().frame(frameb);
		Thread.sleep(2000);
		Web.clickOnElement(addNewUserBtn);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(addNewUserTitle);
		if(addNewUserTitle.getText().trim().equalsIgnoreCase("Add New User"))
			Reporter.logEvent(Status.PASS,"Click on Add New User button.","Add New User page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Add New User button.","Add New User page is not displayed.", true);
		//User Information
		Web.setTextToTextBox(firstNameInput,fn+System.currentTimeMillis());
		Web.setTextToTextBox(lastNameInput,ln);
		Web.setTextToTextBox(emailInput,email_);
		Web.clickOnElement(firstNameInput);
		//Web.waitForElement(spinner);
		do{
			Thread.sleep(1000);
			System.out.println("Spinning..........");
		}while(spinner.isDisplayed());
		Web.clickOnElement(continueBtn);
		Web.waitForElement(userAccessTable);
		
		//User Access
		Select userAccess = new Select(userAccessdropDwon1);
		userAccess.selectByIndex(1);
		
		//Plan Access
		if(availablePlan.getTagName().equals("input")){
			Web.setTextToTextBox(availablePlan, Stock.GetParameterValue("PlanNumber")+" ");
			do{
				Thread.sleep(1000);
				System.out.println("Waiting for plan link to be displayed.");
			}while(!CommonLib.isElementExistByXpath(autoCompletePlanLink));
			Web.clickOnElement(Web.getDriver().findElement(By.xpath(autoCompletePlanLink)));
			do{
				Thread.sleep(1000);
				System.out.println("Spinning..........");
			}while(spinner.isDisplayed());
		}else{
			Select selPlan = new Select(availablePlan);
			selPlan.selectByIndex(0);
			Web.clickOnElement(addPlanBtn);
			Thread.sleep(2000);
		}
		Web.clickOnElement(createUserBtn);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(confirmMsgBox,true))
			Reporter.logEvent(Status.PASS,"Fill all the required details to create user and click on"
					+ " create new user button.", "Confirmation message is displayed displaying following message:"+confirmMsgBox.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Fill all the required details to create user and click on"
					+ " create new user button.", "Confirmation message is not displayed", true);
		this.ValidateFieldsOnConfMsgPageNewUser();
		newUserAssignedID = assignedUserName.getAttribute("value").trim();
		Web.clickOnElement(confirmMsgBox.findElement(By.tagName("button")));
		Web.waitForPageToLoad(Web.getDriver());
	}
	catch(Exception e)
	{
			  e.printStackTrace();
			  Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}
	
/**
 * <pre>This method Validates the if first name,last name and email are not editable after creating new user.</pre>
 *@author smykjn
 *@date 12-June-2017
* @return void
*/
public void ValidateFieldsOnConfMsgPageNewUser()
{
	try{
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "Validate that on confirmation screen first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "Validate that on confirmation screen first name,last name and email are not editable.",""
					+ "Specified fields are not editable.", false);
	}
	catch(Exception e)
	{
			  e.printStackTrace();
			  Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}	
	


/**
 * <pre>This method search the new user with assigned username.</pre>
 *@author smykjn
 *@date 12-June-2017
* @return void
* @param assigned username
*/
public void searchUser(String newUserAssignedID)
{
	try{
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		homePage = new HomePage();
		homePage.navigateToProvidedPage("Plan","Administration","Username security management");
		Web.getDriver().switchTo().frame(frameb);
		try{
			if(showAdvSearchedLink.isDisplayed())
				Web.clickOnElement(showAdvSearchedLink);
			Web.waitForPageToLoad(Web.getDriver());
		}catch(Exception e){

		}
		Thread.sleep(2000);
		Web.setTextToTextBox(searchUserId, newUserAssignedID);
		Web.clickOnElement(goBtn);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frameb);
		//Web.waitForElements(searchedResultsColumns);
		Thread.sleep(4000);
		if(searchedResultsColumns.get(2).getText().equals(newUserAssignedID))
			Reporter.logEvent(Status.PASS,"Search the new user:"+newUserAssignedID,"User record is displayed on search screen.", false);
		else
			Reporter.logEvent(Status.FAIL,"Search the new user:"+newUserAssignedID,"User record is not displayed on search screen.", true);

	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}
	

/**
 * <pre>This method validates the editability of first name,last name and email fields when user selects View/Edit option and
 * edit user information.</pre>
 *@author smykjn
 *@date 12-June-2017
 * @return void
 * @param assigned username
 */
public void validateEditabilityOfUserFields()
{
	try{
		searchUser(newUserAssignedID);
		Select action = new Select(searchedResultsColumns.get(10).findElement(By.tagName("select")));
		action.selectByVisibleText("View / Edit");
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "select View/Edit from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "select View/Edit from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable.", false);
		Select sel = new Select(userAccessdropDwon2);
		sel.selectByIndex(1);
		Thread.sleep(2000);
		Web.clickOnElement(editButtonn);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(confirmMsgBox,true))
			Reporter.logEvent(Status.PASS,"edit user access section and click on"
					+ " save button.", "Confirmation message is displayed displaying following message:"+confirmMsgBox.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"edit user access section and click on"
					+ " save button.", "Confirmation message is not displayed", true);
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "validate that first name,last name and email are not editable on edit user confirmation"
					+ "screen.","Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "validate that first name,last name and email are not editable on edit user confirmation"
					+ "screen.","Specified fields are not editable.", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}	


/**
* <pre>This method validates the editability of first name,last name and email fields on reset password page.</pre>
*@author smykjn
*@date 12-June-2017
* @return void
* @param assigned username
*/
public void validateEditabilityOnResetPasswordScreen()
{
	try{
		searchUser(newUserAssignedID);
		Select action = new Select(searchedResultsColumns.get(10).findElement(By.tagName("select")));
		action.selectByVisibleText("Reset Password");
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "select 'Reset Password' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "select 'Reset Password' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable.Confirmation message:"+confirmMsgBox.getText(), false);
		Web.clickOnElement(confirmMsgBox.findElement(By.xpath(".//button[text()='Yes']")));
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable on confirmation screen.", true);
		else
			Reporter.logEvent(Status.PASS, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable on confirmation screen.", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
* <pre>This method validates the editability of first name,last name and email fields on Reset security question user screen.</pre>
*@author smykjn
*@date 12-June-2017
* @return void
* @param assigned username
*/
public void validateEditabilityOnResetSecurityQues()
{
	try{
		searchUser(newUserAssignedID);
		Select action = new Select(searchedResultsColumns.get(10).findElement(By.tagName("select")));
		action.selectByVisibleText("Reset Security Question(s)");
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "select 'Reset Security Question(s)' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "select 'Reset Security Question(s)' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable.Confirmation message:"+confirmMsgBox.getText(), false);
		Web.clickOnElement(confirmMsgBox.findElement(By.xpath(".//button[text()='Yes']")));
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable on confirmation screen.", true);
		else
			Reporter.logEvent(Status.PASS, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable on confirmation screen.", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
* <pre>This method validates the editability of first name,last name and email fields on terminate user screen.</pre>
*@author smykjn
*@date 12-June-2017
* @return void
* @param assigned username
*/
public void validateEditabilityOnTerminateUserScreen()
{
	try{
		searchUser(newUserAssignedID);
		Select action = new Select(searchedResultsColumns.get(10).findElement(By.tagName("select")));
		action.selectByVisibleText("Terminate");
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "select 'Terminate' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "select 'Terminate' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable.Confirmation message:"+confirmMsgBox.getText(), false);
		Web.clickOnElement(confirmMsgBox.findElement(By.xpath(".//button[text()='Yes']")));
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable on confirmation screen.", true);
		else
			Reporter.logEvent(Status.PASS, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable on confirmation screen.", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}



/**
* <pre>This method validates the editability of first name,last name and email fields on terminate user screen.</pre>
*@author smykjn
*@date 12-June-2017
* @return void
* @param assigned username
*/
public void validateEditabilityOnReactivateUserScreen()
{
	boolean isEditable=false;
	try{
		searchUser(newUserAssignedID);
		Select action = new Select(searchedResultsColumns.get(10).findElement(By.tagName("select")));
		action.selectByVisibleText("View / Reactivate");
		Web.waitForPageToLoad(Web.getDriver());
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "select 'View / Reactivate' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable.", true);
		else
			Reporter.logEvent(Status.PASS, "select 'View / Reactivate' from Action drop down and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable.Confirmation message:"+confirmMsgBox.getText(), false);
		Web.clickOnElement(confirmMsgBox.findElement(By.xpath(".//button[text()='Yes']")));
		Web.waitForPageToLoad(Web.getDriver());
		/*
		if(firstNameInput.getAttribute("disabled").equals("disabled")&&lastNameInput.getAttribute("disabled").equals("disabled")
				&&emailInput.getAttribute("disabled").equals("disabled"))
			isEditable = false;
		else
			isEditable = true;*/
		if(isFieldEditable())
			Reporter.logEvent(Status.FAIL, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are editable on confirmation screen.", true);
		else
			Reporter.logEvent(Status.PASS, "click on yes and validate that first name,last name and email are not editable.",""
					+ "Specified fields are not editable on confirmation screen.", false);
		Web.clickOnElement(confirmMsgBox.findElement(By.tagName("button")));
		Web.waitForPageToLoad(Web.getDriver());
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
 * <pre>This method checks the editability of firs name,Last name,email fields of User when user is searched through search screen.</pre>
 * @author smykjn 
 */
public boolean isFieldEditable() throws Exception{
	boolean isEditable = false;
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(frameb);
	if(firstNameDisabled.getAttribute("disabled").equals("true")&&lastNameDisabled.getAttribute("disabled").equals("true")
			&&emailDisabled.getAttribute("disabled").equals("true"))
		isEditable = false;
	else
		isEditable = true;
	return isEditable;
}



/**
 * 
 * <pre>this method fills Participant QDIA Notice - Mailing Service form with or without 'Ignore divisions' option
 * based on parameter set.</pre>
 * @author smykjn
 */
public void fill_QDIA_Notice_Mailing_Form() 
{
	try{
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frameb);
		Web.waitForElement(orderForm);
		if(Stock.GetParameterValue("ignoreDivision").equals("yes")){
			Web.waitForElement(ignoreDivisionYes);
			Web.clickOnElement(ignoreDivisionYes);
			Thread.sleep(2000);
		}else{
			Select div = new Select(division);
			div.selectByIndex(0);
			Web.clickOnElement(subDiviBtn);
			Web.waitForElement(subDivision);
			Web.clickOnElement(subDivision);
			Web.clickOnElement(subDiviBtn);
		}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			cal.add(Calendar.DAY_OF_YEAR,-90);
			Web.setTextToTextBox(beginDate, sdf.format(cal.getTime()));
			Web.setTextToTextBox(endDate, sdf.format(new Date()));
			Select partIdentifier = new Select(pptIdentifier);
			partIdentifier.selectByVisibleText(Stock.GetParameterValue("Participant Identifier"));
			Select partSortOrder = new Select(pptSortOrder);
			partSortOrder.selectByVisibleText(Stock.GetParameterValue("Sort Order"));
			Select repFormat = new Select(reportFormat);
			repFormat.selectByVisibleText(Stock.GetParameterValue("Report Format"));
			Web.clickOnElement(submitBtnforQDIA);
			Web.waitForPageToLoad(Web.getDriver());
			do{
				System.out.println("Loading...............");
				Thread.sleep(2000);
			}while(QDIALoader.isDisplayed());
			//Web.waitForElement(QDIALoader2);
			do{
				System.out.println("Loading...............");
				Thread.sleep(2000);
			}while(QDIALoader2.isDisplayed());
			if(Web.isWebElementDisplayed(QDIArequestConfirmation, true))
				Reporter.logEvent(Status.PASS,"Fill all the required details on QDIA notice mailing form and submit.",""
						+ "QDIA request is submitted successfully with following message:"+QDIArequestConfirmation.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,"Fill all the required details on QDIA notice mailing form and submit.",""
						+ "QDIA request is not submitted successfully.", true);
			Web.getDriver().switchTo().defaultContent();
	}catch(Exception e){
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
 * <pre>This method validates Participant QDIA notice listing order menu option is displayed or not.</pre>
 * @author smykjn
 * @return boolean
 */
public boolean QDIAisDisplayed() throws Exception
{
	boolean isDisplayed=false;
	HomePage homePage = new HomePage();
	Web.getDriver().switchTo().defaultContent();
	homePage.navigateToProvidedPage("Plan","Fiduciary records","");
	if(CommonLib.isElementExistByXpath(menuQDIA))
		isDisplayed = false;
	else
		isDisplayed = true;
	return isDisplayed;
}

}