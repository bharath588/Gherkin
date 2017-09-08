/**
 * 
 */
package plan;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

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
	@FindBy(className="employeeSpinner")
	private WebElement employeespinner;
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
	@FindBy(id="availablePlan")
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
	@FindBy(xpath=".//div[@ng-show='loading']")
	private WebElement planDocLoader;
	@FindBy(id="BASIS")
	private WebElement division;
	@FindBy(xpath="(.//div[contains(@class,'checkboxLayer')]//label//span)[2]")
	private WebElement subDivision;
	@FindBy(xpath=".//button[@id='']")
	private WebElement subDiviBtn;
	@FindBy(xpath=".//h3[contains(text(),'General forms and documents')]")
	private WebElement genFormAndDocTitle;
	@FindBy(xpath=".//h3[contains(text(),'Plan-specific documents')]")
	private WebElement planSpecificDocTitle;
	@FindBy(linkText="PDI File Layout")
	private WebElement pdiFileLayout;
	@FindBy(linkText="Bank Form")
	private WebElement bankForms;
	@FindBy(xpath=".//*[contains(@ng-if,'hasPartGrouping')]//a")
	private List<WebElement> planSpecificDocLinks;
	@FindBy(xpath="//div[@class='bulletinAlignment']//span//span")
	private WebElement noPlanDocText;@FindBy(xpath=".//*[@id='tabForm:outputPanelb']//td[2]//a")
	private WebElement printLink;
	@FindBy(xpath="//div[@class='printHeader']/h3")
	private WebElement printPriviewWindowHeader;
	@FindBy(xpath=".//div[contains(@class,'instructions')]//a")
	private List<WebElement>  actDocumentList;
	@FindBy(linkText="Safe Harbor Notice")
	private WebElement safeHarborLink;
	@FindBy(xpath=".//*[@id='RequiredNoticesModule']//h3")
	private WebElement reqNoticeTitle;
	@FindBy(xpath=".//*[@class='icon-arrow-left pull-left']")
	private WebElement backArrowLink;
	@FindBy(xpath="(.//div[@class='headcontainer']//h4)[1]")
	private WebElement docStaticTitle;
	@FindBy(xpath="(.//div[@class='headcontainer']//h4)[2]")
	private WebElement docStaticHistoryTitle;
	@FindBy(xpath=".//span[@class='large icon-stopwatch']")
	private WebElement stopWatchIcon;
	@FindBy(id="framebA")
	private WebElement framebA;
	@FindBy(xpath="(.//accordion)[1]/div/div")
	private List<WebElement> historyAccordion;
	@FindBy(css="div[ng-if='showDisclaimer'] h4")
	private WebElement disclaimer;
	@FindBy(xpath=".//div[@ng-if='showDisclaimer']//h4/../following-sibling::div[1]")
	private WebElement disclaimerDesc;
	@FindBy(xpath=".//ul[contains(@class,'mainlist')]//div/following-sibling::a")
	private List<WebElement> doclinks;
	@FindBy(xpath="//*[@class='large icon-calendar']")
	private WebElement calendarIcon;
	@FindBy(xpath="(.//div[@class='headcontainer']//h4)[2]/../following-sibling::div//a")
	private WebElement historyLink;
	@FindBy(xpath=".//div[@class='headcontainer']//h4")
	private WebElement docTitleOnHistoryPage;
	@FindBy(xpath=".//div[@class='headcontainer']//h4/../following-sibling::div[1]")
	private WebElement docDescOnHistoryPage;
	@FindBy(xpath="(.//accordion)[1]/div/div/div/h4//span")
	private List<WebElement> accordionTitle;
	@FindBy(xpath="(.//accordion)[1]/div/div/div[1]//a")
	private List<WebElement> accordionLink;
	@FindBy(xpath="//div[@class='accordion']//a[@class='accordion-toggle']//span")
	private List<WebElement> accordionMonthsText;
	@FindBy(xpath="//div[@class='accordion']//a[@class='accordion-toggle']")
	private List<WebElement> linksUnderAccordion;
	@FindBy(id="collapse-init")
	private WebElement expandAllLink;
	@FindBy(id="expand-init")
	private WebElement collapseAllLink; 
	@FindBy(xpath=".//a[text()='Overview']//following-sibling::ul//a[text()='Plan provisions' or "
			+ "text()='Vesting schedules' or text()='Participant web and VRU options']")
	private List<WebElement> planoverviewOptions;
	@FindBy(xpath=".//*[@id='exportable']//th//span | .//*[@id='exportable']//th//a")
	private List<WebElement> invOptColumnsList;
	@FindBy(xpath=".//div[@id='investmentOptions']//div[@ng-show='loading']")
	private WebElement invOptLoader;
	@FindBy(linkText="Investment performance detail")
	private WebElement investmentAndPerfLink;
	@FindBy(xpath=".//*[@id='investmentPerform']//a[.='Excel']")
	private WebElement excellink;
	@FindBy(xpath=".//*[@id='exportable']/preceding-sibling::div/div[1]/div")
	private WebElement invoptHeaderTitle;
	@FindBy(xpath="(.//*[@id='inv-Popover'])[1]")
	private WebElement fstInvName;
	@FindBy(xpath=".//h3[text()='Quick Links']/following-sibling::div//a")
	private List<WebElement> qukLinks;
	@FindBy(xpath=".//*[@class='invDesclaimerNote']/p")
	private WebElement disclaimer1;
	@FindBy(xpath=".//*[@class='invDesclaimerNote']/i/p")
	private WebElement disclaimer2;
	@FindBy(xpath=".//*[@class='invDesclaimerNote']/following-sibling::i/p")
	private List<WebElement> disclaimers;
	@FindBy(xpath=".//*[@class='invDesclaimerNote']/following-sibling::p")
	private List<WebElement> disclaimerLast;
	@FindBy(id="msRatingPopover0")
	private WebElement firstMorngStarInfo;
	@FindBy(xpath=".//div[@class='popover-content']/span")
	private WebElement mrngStarContent;
	@FindBy(xpath=".//*[@id='exportable']//tr//td[6]/span[text()!='']")
	private List<WebElement> balanceListInOptPage;
	@FindBy(xpath=".//*[@id='exportable']//th//a[contains(text(),'Balance')]")
	private WebElement balanceLinkHeader;
	@FindBy(name="bankingInformationForm")
	private WebElement bankInformationsSection;
	@FindBy(linkText="Update")
	private List<WebElement> updateLinks;
	@FindBy(className="section_title_text")
	private List<WebElement> bankSectionTitles;
	@FindBy(id="newBankRoutingNumber")
	private WebElement newRoutingNoInput;
	@FindBy(id="newBankAccountNumber")
	private WebElement newAccNoInput;
	@FindBy(id="newBankAccountType_sav")
	private WebElement accTypeSavings;
	@FindBy(xpath="//input[@value='Continue']")
	private WebElement continueButtonInput;
	@FindBy(xpath=".//div[@id='unitShareValues']//th[not(text()!='')]//span[1]")
	List<WebElement> unitShareHeaders;
	@FindBy(xpath=".//p[@class='footnote']/span")
	List<WebElement> watermarkText1;
	@FindBy(xpath="(.//*[@id='unitShareValues']//p)[2]")
	WebElement watermarkText2;
	@FindBy(xpath="(.//*[@id='unitShareValues']//p)[3]")
	WebElement watermarkText3;
	@FindBy(xpath="(.//*[@id='unitShareValues']//p)[4]")
	WebElement watermarkText4;
	@FindBy(xpath="(.//*[@id='unitShareValues']//p)[5]")
	WebElement watermarkText5;
	@FindBy(id="saButton")
	private WebElement searchUserGoBtn;
	@FindBy(xpath=".//*[@id='searchListDataTable']//tbody//tr")
	private WebElement searchResultRow;
	private String menuQDIA = "//a[contains(text(),'Participant QDIA notice listing order')]";
	private String docHistoryLinkPath = "./ancestor::div[1]/following-sibling::div//a[contains(@class,'accordion-toggle-doclink')]";
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
		Web.waitForElement(weGreeting);
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
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(frameb);
				Thread.sleep(2000);
				Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
			}catch(Exception e){
				Web.waitForElement(frameb);
				Thread.sleep(2000);
			}
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

		if (fieldName.trim().equalsIgnoreCase("ASSIGNED_USERID")) {
			return this.searchUserId;
		}
		
		return null;
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
					+ " page.\n1)Investment options\n2)Documents\n3)Charts\n4)Asset allocation models\n5)Fixed investment rates"
					+ "\n6)Unit/share values", "Specified columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate following columns are displayed on plan-->Investments & Performance"
					+ " page.\n1)Investment options\n2)Documents\n3)Charts\n4) Asset allocation models\n5)Fixed investment rates"
					+ "\n6)Unit/share values", "Specified columns are not displayed.", true);
		
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
 * <pre>This method Validates columns on Investments & Performance page for Investment options tab.</pre>
 * @author smykjn
 * @Date 22nd-June-2017
 * @return void
 */
public void validateInvestmentOptionsColumns() 
{
	List<String> expColumns = Arrays.asList(Stock.GetParameterValue("ExpectedInvestmentColumns").split(","));
	String expheaderText = Stock.GetParameterValue("ExpHeaderText");
	try{
		CommonLib.waitForLoader(invOptLoader);
		System.out.println(invOptColumnsList.size());
		if(CommonLib.isAllHeadersDisplayed(invOptColumnsList, expColumns))
			Reporter.logEvent(Status.PASS,"Validate columns on investment options page as follows:"+expColumns,""
					+ "As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate columns on investment options page as follows:"+expColumns,""
					+ "specified columns are not displayed.",true);
		if(invoptHeaderTitle.isDisplayed())
			Reporter.logEvent(Status.PASS,"Verify top left section has following text:"+expheaderText+"<Date>",""
					+ "actual text displayed is:"+invoptHeaderTitle.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Verify top left section has following text:"+expheaderText+"<Date>",""
					+ "actual text displayed is:"+invoptHeaderTitle.getText(), false);
		if(investmentAndPerfLink.isDisplayed()&&excellink.isDisplayed())
			Reporter.logEvent(Status.PASS,"Verify links '"+investmentAndPerfLink.getText().trim()+"'"
					+ " and '"+excellink.getText().trim()+"'.", "Links are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Verify links '"+investmentAndPerfLink.getText().trim()+"'"
					+ " and '"+excellink.getText().trim()+"'.", "Links are not displayed.", true);
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
	String parentWindow="";
	try{
		Web.getDriver().switchTo().defaultContent();
		if(CommonLib.getBrowserName().equalsIgnoreCase("chrome")){
			if(printLink.isDisplayed())
				Reporter.logEvent(Status.PASS,"Validate print link is displayed.","Print link is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,"Validate print link is displayed.","Print link is not displayed.", true);
		}else{
			Web.clickOnElement(printLinkUnderPlanMenu);
			parentWindow =CommonLib.switchToWindow();
			if(printPriviewWindowHeader.getText().contains("THIS IS A PRINT PREVIEW"))
			{
				Reporter.logEvent(Status.PASS, "Verify after clicking on print link,a "
						+ "print preview page is displayed.", "Print preview page is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify after clicking on print link,a "
						+ "print preview page is displayed.", "Print preview page is not displayed.", true);
			}
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
				
			
		}
		/*printLinkUnderPlanMenu.click();
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
	     }*/
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
 * <pre>This method checks for spinner and wait until spinning is done.</pre>
 * @author smykjn
 */
public void waitForEmployeeSpinner() throws Exception
{
	int iTimeInSecond=100;
	try{
		Web.waitForElement(employeespinner);
		int iCount = 0;
		while (employeespinner.isDisplayed()){
			if(iCount ==iTimeInSecond){
				break;
			}   

			System.out.println("Employee Spinner displaying......");
			Thread.sleep(1000);                       
			iCount++;
		}


	}catch(Exception e){
		e.getMessage();
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
		Web.waitForPageToLoad(Web.getDriver());
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
		Web.waitForElement(confirmMsgBox);
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



/**
* <pre>This method validates the Plan Documents page for PSC-NextGen.
* It validates page headers 'General forms and documents' and 'Plan-specific documents',print link,PDI File Layout link.</pre>
*@author smykjn
*@date 14-June-2017
* @return void
*/
public void validatePlanDocumentsPage()
{
	try{
		Web.getDriver().switchTo().frame(frameb);
		if(Web.isWebElementDisplayed(planDocLoader, true)){
			do{
				Thread.sleep(2000);
				System.out.println("Loading.......");
			}while(planDocLoader.isDisplayed());
		}
		if(Web.isWebElementDisplayed(genFormAndDocTitle,true)&&Web.isWebElementDisplayed(planSpecificDocTitle, true))
			Reporter.logEvent(Status.PASS,"Validate title 'General forms and documents' "
					+ "and 'Plan-specific documents' are displayed.",""
					+ "Specified titles are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate title 'General forms and documents' "
					+ "and 'Plan-specific documents' are displayed.",""
					+ "Specified titles are not displayed.", true);
		Web.clickOnElement(pdiFileLayout);
		String parentWindow = CommonLib.switchToWindow();
		String pdfURL = Web.getDriver().getCurrentUrl();
		if(pdfURL.contains(".pdf"))
			Reporter.logEvent(Status.PASS,"Click on PDI File Layout link and check if new window is "
					+ "opened with url containing .pdf as extension.","PDF is displayed in a seperate window.",false);
		else
			Reporter.logEvent(Status.FAIL,"Click on PDI File Layout link and check if new window is "
					+ "opened with url containing .pdf as extension.","PDF is not displayed in a seperate window.",true);
		Web.getDriver().close();
		Web.getDriver().switchTo().window(parentWindow);
		Thread.sleep(2000);
		Web.getDriver().switchTo().defaultContent();
		if(CommonLib.getBrowserName().equalsIgnoreCase("chrome")){
			if(printLink.isDisplayed())
				Reporter.logEvent(Status.PASS,"Validate print link is displayed.","Print link is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,"Validate print link is displayed.","Print link is not displayed.", true);
		}else{
			Web.clickOnElement(printLink);
			CommonLib.switchToWindow();
			if(printPriviewWindowHeader.getText().contains("THIS IS A PRINT PREVIEW"))
			{
				Reporter.logEvent(Status.PASS, "Verify after clicking on print link,a "
						+ "print preview page is displayed.", "Print preview page is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify after clicking on print link,a "
						+ "print preview page is displayed.", "Print preview page is not displayed.", true);
			}
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
* <pre>This method validates Plan Documents are Link</pre>
*@author smykjn
*@date 14-June-2017
* @return void
*/
public void validatePlanDocumentsLinks()
{
	List<String> docDesc = new ArrayList<String>();
	try{
		CommonLib.switchToFrame(frameb);
		if(Web.isWebElementsDisplayed(planSpecificDocLinks, true))
			Reporter.logEvent(Status.PASS,"Validate Plan document links are displayed.",""
					+ "Plan document links are displayed.No. of Links:"+planSpecificDocLinks.size(),false);
		else
			Reporter.logEvent(Status.FAIL,"Validate Plan document links are displayed.",""
					+ "Plan document links are not displayed.",true);
		for(WebElement description : planSpecificDocLinks){
			docDesc.add(description.getText().trim());
		}
		Reporter.logEvent(Status.INFO,"Documents descriptions:",""+docDesc, false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
* <pre>This method validates message when no plan documents are found.</pre>
*@author smykjn
*@date 14-June-2017
* @return void
*/
public void validateNoPlanDocMsg()
{
	String expMsg = Stock.GetParameterValue("NoPlanDocMsg");
	String actMsg = "";
	try{
		homePage = new HomePage();
		Web.getDriver().switchTo().defaultContent();
		if(homePage.searchPlanWithIdOrName(Stock.GetParameterValue("planNumber")))
			Reporter.logEvent(Status.PASS,"Search for a plan with no plan documents available.","Plan is found and user navigates to"
					+ "plan page.", false);
		else
			Reporter.logEvent(Status.FAIL,"Search for a plan with no plan documents available.","Plan is not found.", true);
		if(homePage.navigateToProvidedPage("Plan","Fiduciary records","Plan documents"))
			Reporter.logEvent(Status.PASS,"Navigate to Plan-->Fiduciary records-->Plan documents page.","Plan documents page is "
					+ "displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Navigate to Plan-->Fiduciary records-->Plan documents page.","Plan documents page is "
					+ "not displayed.", true);
		Web.getDriver().switchTo().frame(frameb);
		Web.waitForElement(noPlanDocText);
		actMsg = noPlanDocText.getText().trim();
		if(actMsg.equalsIgnoreCase(expMsg))
			Reporter.logEvent(Status.PASS,"Following message is displayed:"+expMsg,"Actual message displayed is:"+actMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Following message is displayed:"+expMsg,"Actual message displayed is:"+actMsg, true);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
* <pre>This method validates general fiduciary documents links
* 1.Fee Disclosure Documents 2.Qualified Default Investment Alternative 3.Safe Harbor Notice</pre>
*@author smykjn
*@date 15-June-2017
* @return void
*/
public void validateFiduciaryRequiredNotices()
{
	List<String> documents = Arrays.asList(Stock.GetParameterValue("Notice Documents").split(","));
	try{
		CommonLib.switchToFrame(frameb);
		if(CommonLib.isAllHeadersDisplayed(actDocumentList, documents))
			Reporter.logEvent(Status.PASS,"Validate following document links are displayed"
					+ " on required notices page:"+documents,"Specified document links are displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate following document links are displayed"
					+ " on required notices page:"+documents,"Specified document links are displayed.",true);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
*@author smykjn
*@date 15-June-2017
* @return void
*/
public void validateFiduciaryRequiredNotices_1()
{
	String expMsg = Stock.GetParameterValue("ExpectedDocMsg");
	try{
		String docActTitle = safeHarborLink.getText().trim();
		Web.clickOnElement(safeHarborLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.switchToFrame(framebA);
		Web.isWebElementDisplayed(reqNoticeTitle,true);
		if(reqNoticeTitle.getText().trim().equals("Required notices"))
			Reporter.logEvent(Status.PASS,"Validate static label 'Required notices'.","static label 'Required notices'"
					+ " is displayed", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate static label 'Required notices'.","static label 'Required notices'"
					+ " is not displayed", false);
		try{
		WebElement accept = disclaimerDesc.findElement(By.xpath(".//button[.='Accept']"));
		Web.clickOnElement(accept);}catch(Exception e)
		{}
		WebElement backTextElement =  backArrowLink.findElement(By.tagName("i"));
		String backText = backArrowLink.findElement(By.tagName("i")).getText().trim();
		
		if(backArrowLink.getTagName().equals("a")&&backArrowLink.isDisplayed()&&backTextElement.isDisplayed()&&
				backText.equals("Back"))
			Reporter.logEvent(Status.PASS,"Validate 'Back' text followed by arrow link.","'Back' text followed by"
					+ " arrow link is displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate 'Back' text followed by arrow link.","'Back' text followed by"
					+ " arrow link is not displayed or arrow is not displayed.",true);
		
		if(docStaticTitle.getText().trim().equals(docActTitle))
			Reporter.logEvent(Status.PASS,"Validate document static title based on the documents"
					+ " viewed by user.in this case validate for title:"+docActTitle,""
							+ "Document title,which is being viewed is:"+docStaticTitle.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Validate document static title based on the documents"
					+ " viewed by user.","Document being viewed is:"+docStaticTitle.getText(), true);
		boolean isDisplayed = Web.isWebElementDisplayed(stopWatchIcon, true);
		String actMsg = stopWatchIcon.findElement(By.xpath("./..")).getText().trim();
		System.out.println("Actual message text:"+actMsg);
		if(isDisplayed&&actMsg.contains(expMsg))
			Reporter.logEvent(Status.PASS,"Validate clock icon and text '"+expMsg+"' followed by clock icon.",""
					+ "Clock is displayed:"+isDisplayed+" and text displayed:"+actMsg,false);
		else
			Reporter.logEvent(Status.FAIL,"Validate clock icon and text '"+expMsg+"' followed by clock icon.",""
					+ "Clock is displayed:"+isDisplayed+" and text displayed:"+actMsg,true);
		Web.clickOnElement(backArrowLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.switchToFrame(frameb);
		if(Web.isWebElementsDisplayed(actDocumentList, true))
			Reporter.logEvent(Status.PASS,"Click on back link and validate user lands on "
					+ "Required Notices landing page.","As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Click on back link and validate user lands on "
					+ "Required Notices landing page.","Required Notices landing page is not displayed.",true);
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
*@author smykjn
*@date 16-June-2017
* @return void
*/
public void validateSafeHarBorNoticeForPartGrpng()
{
	List<String> docs = new ArrayList<String>();
	String expHistoryText = Stock.GetParameterValue("ExpectedHistoryLinkText");
	try{
		Web.clickOnElement(safeHarborLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.switchToFrame(framebA);
		if(Web.isWebElementDisplayed(disclaimer, true))
			Reporter.logEvent(Status.PASS,"Again click on Safe harbor link and Validate Disclaimer is displayed.","Disclaimer is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate Disclaimer is displayed.","Disclaimer is not displayed.", true);
		Reporter.logEvent(Status.INFO,"Disclaimer:",disclaimerDesc.getText(), true);
		WebElement accept = disclaimerDesc.findElement(By.xpath(".//button[.='Accept']"));
		Web.clickOnElement(accept);
		Web.waitForElement(stopWatchIcon);
		for(WebElement doc : doclinks){
			docs.add(doc.getText().trim());
		}
		if(Web.isWebElementsDisplayed(doclinks, true))
			Reporter.logEvent(Status.PASS,"Click on Accept button and check if "
					+ "document links are displayed.","Document links are displayed as:"+docs,false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Accept button and check if "
					+ "document links are displayed.","Document links are not displayed.",true);
		if(docStaticHistoryTitle.getText().equals("Safe Harbor Notice Document History")&&docStaticHistoryTitle.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate static title 'Safe Harbor Notice Document History' is"
					+ " displayed.","As expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate static title 'Safe Harbor Notice Document History' is"
					+ " displayed.","Title is not displayed.", true);
		if(historyLink.getText().trim().equals(expHistoryText)&&
				historyLink.isDisplayed())
			Reporter.logEvent(Status.PASS,"Verify Static text/Hyperlinks for history documents as:"+expHistoryText,""
					+ "link text is:"+historyLink.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Verify Static text/Hyperlinks for history documents as:"+expHistoryText,""
					+ "link text is:"+historyLink.getText(), true);
		if(calendarIcon.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate calendar icon is displayed.","As expected.", true);
		else
			Reporter.logEvent(Status.FAIL,"Validate calendar icon is displayed.","Validate calendar icon is not displayed.", false);
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}





/**
*@author smykjn
*@date 16-June-2017
* @return void
*/
public void validateSafeHarBorNoticeForPartGrpng_1()
{
	boolean isAccordionExpanded = false;
	String expTitle = Stock.GetParameterValue("ExpTitleOnHistoryPage");
	String expDesc = Stock.GetParameterValue("ExpDescOnHistoryPage");
	try{
		Web.clickOnElement(historyLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElements(historyAccordion);
		WebElement backTextElement =  backArrowLink.findElement(By.tagName("i"));
		String backText = backArrowLink.findElement(By.tagName("i")).getText().trim();
		if(backArrowLink.getTagName().equals("a")&&backArrowLink.isDisplayed()&&backTextElement.isDisplayed()&&
				backText.equals("Back"))
			Reporter.logEvent(Status.PASS,"Validate 'Back' text followed by arrow link.","'Back' text followed by"
					+ " arrow link is displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate 'Back' text followed by arrow link.","'Back' text followed by"
					+ " arrow link is not displayed or arrow is not displayed.",true);
		if(historyAccordion.size()==1){
			
			
		}else if(historyAccordion.size()>1){
			for(int i=0;i<historyAccordion.size();i++){
				if(!historyAccordion.get(i).findElement(By.xpath("./div[2]")).isDisplayed()){
					isAccordionExpanded=true;
				}else{
					isAccordionExpanded=false;break;
				}
					
			}
		}else{
			
		}
		if(isAccordionExpanded)
			Reporter.logEvent(Status.PASS,"If accordion row is more than 1 then all rows must be"
					+ " dispayed in collapsed state else row must be displayed in expanded state.", "As expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"If accordion row is more than 1 then all rows must be"
					+ " dispayed in collapsed state else row must be displayed in expanded state.", ""
							+ "accordion collapse/expanded features based on number of accordion row is not working as expected.", true);
		String actTitle = docTitleOnHistoryPage.getText().trim();
		String actDesc = docDescOnHistoryPage.getText().trim();
		if(actTitle.equals(expTitle)&&actDesc.equals(expDesc))
			Reporter.logEvent(Status.PASS,"Validate title to be '"+expTitle+"' and "
					+ "description to be '"+expDesc+"'.","Title is '"+actTitle+"' and document description is '"+actDesc+"'.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate title to be '"+expTitle+"' and "
					+ "description to be '"+expDesc+"'.","Title is '"+actTitle+"' and document description is '"+actDesc+"'.", true);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
*@author smykjn
*@date 19-June-2017
* @return void
*/
public void validateSafeHarBorNoticeForPartGrpng_2()
{
	List<String> expMonths = Arrays.asList(Stock.GetParameterValue("ExpListOfMonths").split(","));
	try{
		String accordionTitle_ = Stock.GetParameterValue("AccordionTitle");
		List<String> listOfAccordionTitle = new ArrayList<String>();
		boolean isDisplayed = false;
		for(WebElement title : accordionTitle){
			if(title.getText().trim().contains(accordionTitle_)){
				isDisplayed=true;listOfAccordionTitle.add(title.getText().trim());
			}else{
				isDisplayed = false;break;
			}
		}
		if(isDisplayed)
			Reporter.logEvent(Status.PASS,"validate accordian title to be [Year]+'"+accordionTitle_+"'.",""
					+ "Accordions titles are :"+listOfAccordionTitle, false);
		else
			Reporter.logEvent(Status.FAIL,"validate accordian title to be [Year]+'"+accordionTitle_+"'.",""
					+ "Accordions titles are :"+listOfAccordionTitle, true);
		Web.clickOnElement(accordionLink.get(0));
		Web.clickOnElement(accordionLink.get(1));
		if(CommonLib.isAllHeadersDisplayed(accordionMonthsText, expMonths)&&Web.isWebElementsDisplayed(linksUnderAccordion,false))
			Reporter.logEvent(Status.PASS,"Validate documents are grouped by months links.","As expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate documents are grouped by months links.","As expected.", false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
*@author smykjn
*@date 19-June-2017
* @return void
*/
public void validateSafeHarBorNoticeForPartGrpng_3()
{
	try{
		Web.clickOnElement(linksUnderAccordion.get(0));
		List<WebElement> subAccordionLibnl = linksUnderAccordion.get(0).findElements(By.xpath(docHistoryLinkPath));
		List<String> historydocName = new ArrayList<String>();
		for(WebElement doc : subAccordionLibnl){
			historydocName.add(doc.getText().trim());
		}
		if(Web.isWebElementsDisplayed(subAccordionLibnl, false))
			Reporter.logEvent(Status.PASS,"Verify documents under sunaccordion.","Multiple hyperlinks are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Verify documents under sunaccordion.","Multiple hyperlinks are not displayed.", true);
		if(expandAllLink.isDisplayed()){
			Web.clickOnElement(expandAllLink);
			if(collapseAllLink.isDisplayed())
				Reporter.logEvent(Status.PASS,"Click on Expand All link.","Collapse all link is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,"Click on Expand All link.","Collapse all link is not displayed.", false);
		}
		Web.clickOnElement(collapseAllLink);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
 *<pre>This method validates following menu options under plan overview.
 *1.Plan Provisions 2.Vesting Schedules 3.Voice Response and Participant Web</pre>
 *@author smykjn
 *@date 20-June-2017
 *@return void
*/
public void validateThirdLevelMenuOptionForPlanOverview()
{
	List<String> expDropDownMenu = Arrays.asList(Stock.GetParameterValue("ExpPlanOverviewOptions"));
	try{
		Thread.sleep(2000);
		CommonLib.navigateToProvidedPage("Plan","Overview","");
		if(Web.isWebElementsDisplayed(planoverviewOptions, true))
			Reporter.logEvent(Status.PASS,"Validate following plan overview options:"+expDropDownMenu,""
					+ "All expected overview menu options are displatyed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate following plan overview options:"+expDropDownMenu,""
					+ "All expected overview menu options are not displatyed.", true);	
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}



/**
 *<pre>This method verify quick links on Investment options page</pre>
 *@author smykjn
 *@date 23rd-June-2017
 *@return void
*/
public void validateQuickLinksOnInvOptPage()
{
	List<String> expQucikLinks = Arrays.asList(Stock.GetParameterValue("QuickLinks").split(","));
	List<String> actQuickLinks = new ArrayList<String>();
	String investmentName = "";
	try{
		investmentName = fstInvName.getText().trim();
		Web.clickOnElement(fstInvName);
		Web.waitForElements(qukLinks);
		if(CommonLib.isAllHeadersDisplayed(qukLinks, expQucikLinks))
			Reporter.logEvent(Status.PASS,"Verify below quick links.\n"+expQucikLinks,""
					+ "Quick links displayed are as expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Verify below quick links.\n"+expQucikLinks,""
					+ "Quick links displayed are as below.\n"+actQuickLinks,true);
		if(firstMorngStarInfo.isDisplayed())
			Reporter.logEvent(Status.PASS,"Info link(question mark) is displayed beside morning star rating.",""
					+ "As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Info link(question mark) is displayed beside morning star rating.",""
					+ "Info link is not displayed.",true);
		Web.clickOnElement(firstMorngStarInfo);
		Web.waitForElement(mrngStarContent);
		String mrngStarInfo = mrngStarContent.getText();
		if(mrngStarContent.isDisplayed()&&mrngStarInfo.contains(investmentName))
			Reporter.logEvent(Status.PASS,"Click on morningstar info link and validate popup with "
					+ "morning star rules explaination for that fund will be displayed.Popup upcontains"
					+ "below explanation.\n"+mrngStarInfo,"As Expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on morningstar info link and validate popup with "
					+ "morning star rules explaination for that fund will be displayed.Popup up contains"
					+ "below explanation.\n"+mrngStarInfo,"Pop up is not displayed.", true);
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/**
 *<pre>This method validates Disclaimer on Investment option page.</pre>
 *@author smykjn
 *@date 23rd-June-2017
 *@return void
*/
public void validateDesclaimerForInvetmentOptionPage()
{
	String expDisclaimer1 = Stock.GetParameterValue("Disclaimer1");
	String expDisclaimer2 = Stock.GetParameterValue("Disclaimer2");
	String expDisclaimer3 = Stock.GetParameterValue("Disclaimer3");
	String expDisclaimer4 = Stock.GetParameterValue("Disclaimer4");
	String expDisclaimer5 = Stock.GetParameterValue("Disclaimer5");
	String expDisclaimer6 = Stock.GetParameterValue("Disclaimer6");
	String expDisclaimer7 = Stock.GetParameterValue("Disclaimer7");
	String expDisclaimer8 = Stock.GetParameterValue("Disclaimer8");
	String actDisclaimer1;
	String actDisclaimer2;
	String actDisclaimer3;
	String actDisclaimer4;
	String actDisclaimer5;
	String actDisclaimer6;
	String actDisclaimer7;
	String actDisclaimer8;
	try{
		actDisclaimer1 = disclaimer1.getText().trim();
		actDisclaimer2 = disclaimer2.getText();
		actDisclaimer3 = disclaimers.get(0).getText().trim();
		actDisclaimer4 = disclaimers.get(1).getText().trim();
		actDisclaimer5 = disclaimers.get(2).getText().trim();
		actDisclaimer6 = disclaimers.get(3).getText().trim();
		actDisclaimer7 = disclaimerLast.get(0).getText().trim();
		actDisclaimer8 = disclaimerLast.get(1).getText().trim();
		if(expDisclaimer1.equalsIgnoreCase(actDisclaimer1)&&expDisclaimer2.equalsIgnoreCase(actDisclaimer2)
				&&expDisclaimer3.equalsIgnoreCase(actDisclaimer3)&&expDisclaimer4.equalsIgnoreCase(actDisclaimer4)
				&&expDisclaimer5.equalsIgnoreCase(actDisclaimer5)&&expDisclaimer6.equalsIgnoreCase(actDisclaimer6)
				&&expDisclaimer7.equalsIgnoreCase(actDisclaimer7)&&expDisclaimer8.equalsIgnoreCase(actDisclaimer8))
			Reporter.logEvent(Status.PASS,"Varify investment options bottom disclaimer.","Disclaimer is displayed as below:"+
					"\n"+actDisclaimer1+"\n\n"+actDisclaimer2+"\n"+actDisclaimer3+"\n"+actDisclaimer4+"\n"+actDisclaimer5+""
							+ "\n"+actDisclaimer6+"\n"+actDisclaimer7+"\n"+actDisclaimer8, false);
		else
			Reporter.logEvent(Status.FAIL,"Varify investment options bottom disclaimer.","Disclaimer is displayed as below:"+
					"\n"+actDisclaimer1+"\n\n"+actDisclaimer2+"\n"+actDisclaimer3+"\n"+actDisclaimer4+"\n"+actDisclaimer5+""
							+ "\n"+actDisclaimer6+"\n"+actDisclaimer7+"\n"+actDisclaimer8+"\n\n\n\n Note:"
									+ "Expected disclaimer is not displayed.", true);
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}


/***
 * <pre>This method is to validate Balance column sorting in ascending 
 * and descending order</pre>
 * @author smykjn
 * @Date 27th-June-2017
 * 
 */
public void sortBalanceInDescAndAsc(){
	try{
		Web.clickOnElement(fstInvName);
		List<Double> balList = new ArrayList<Double>();
		Web.clickOnElement(balanceLinkHeader);//ascending
		Thread.sleep(2000);
		for(WebElement bal : balanceListInOptPage){
			if(bal.getText().trim().equals("N/A")){
				System.out.println(bal.getText().trim());
				balList.add(0.00);
			}else{
				double balanceInt = Double.parseDouble(bal.getText().trim().replace("$","").replace(",","").trim());
				System.out.println(balanceInt);
				balList.add(balanceInt);
			}
		}
		List<Double> copylist = new ArrayList<Double>(balList);
		Collections.sort(copylist);
		System.out.println("Ascending order list sorted by Collections:"+copylist);
		System.out.println("Ascending order list sorted by systems:"+balList);
		if(copylist.equals(balList))
			Reporter.logEvent(Status.PASS, "Sort the balance column in ascending order.\n below is the sorting order:\n"
					+ copylist,"As expected.",false);
		else
			Reporter.logEvent(Status.FAIL, "Sort the balance column in ascending order.\n below is the sorting order:\n"
					+ copylist,"sorting is not proper.\nbelow is actual sorting order:\n"+balList,true);
		balList.clear();
		Web.clickOnElement(balanceLinkHeader);//descending
		
		for(WebElement bal : balanceListInOptPage){
			if(bal.getText().trim().equals("N/A")){
				System.out.println(bal.getText().trim());
				balList.add(0.00);
			}else{
				double balanceInt = Double.parseDouble(bal.getText().trim().replace("$","").replace(",","").trim());
				System.out.println(balanceInt);
				balList.add(balanceInt);
			}
		}
		
		if(CommonLib.sortIntegerListDesc(balList))
			Reporter.logEvent(Status.PASS, "Sort the balance column in descending order.","As expected.",false);
		else
			Reporter.logEvent(Status.FAIL, "Sort the balance column in descending order.","As expected.",true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
	
}


/***
 * <pre>This method is to validate Balance column sorting in ascending 
 * and descending order</pre>
 * @author smykjn
 * @Date 27th-June-2017
 * 
 */
public void validateExcelDownload(){
	try{
		CommonLib.switchToFrame(frameb);
		Web.clickOnElement(excellink);
		/*if(CommonLib.getBrowserName().equalsIgnoreCase("firefox")){
			Robot rb = new Robot();
			Thread.sleep(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		}*/
		String fileName  =CommonLib.getDownloadedDocumentName(Stock.getConfigParam("Download_Directory"),".xls");
		if(fileName.contains(".xls"))
			Reporter.logEvent(Status.PASS,"Click on excel link and observe that "
					+ "file with .xls extension is downloaded.","As expected.File name is:"+fileName, false);
		else
			Reporter.logEvent(Status.FAIL,"Click on excel link and observe that "
					+ "file with .xls extension is downloaded.","File name is:"+fileName, false);
		Thread.sleep(2000);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
	
}

/**
 * <pre>This method validate .pdf extension in URL once click on Investment performance detail link</pre>
 * @author smykjn
 * @Date 27-June-2017
 * @return void
 */
public void validatePDFURL()
{
	try{
		Web.clickOnElement(investmentAndPerfLink);
		String parentWindow = CommonLib.switchToWindow();
		String pdfURL = Web.getDriver().getCurrentUrl();
		if(pdfURL.contains(".pdf"))
			Reporter.logEvent(Status.PASS,"Click on Investment performance detail link and check if new window is "
					+ "opened with url containing .pdf as extension.","As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Investment performance detail link link and check if new window is "
					+ "opened with url containing .pdf as extension.","PDF is not displayed in a seperate window.",true);
		Web.getDriver().close();
		Web.getDriver().switchTo().window(parentWindow);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}

/**
 * <pre>This method validates that update link and bank details are displayed.</pre>
 * @author smykjn
 * @Date 27-June-2017
 * @return void
 */
public void validateBankingInfoAndUpdateLink()
{
	try{
		CommonLib.switchToFrame(frameb);
		if(Web.isWebElementDisplayed(bankInformationsSection, true))
			Reporter.logEvent(Status.PASS,"Validate bank information is displayed.","Bank information is displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate bank information is displayed.","Bank information is not displayed.",true);
		if(Web.isWebElementsDisplayed(updateLinks, false))
			Reporter.logEvent(Status.PASS,"Validate Update links are displayed as user has access to below"
					+ " txn codes\n PSUACH,PSVACH.","Update links are displayed.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate Update links are displayed as user has access to below"
					+ " txn codes\n PSUACH,PSVACH.","Update links are not displayed.",true);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}



/**
 * <pre>This method updates the banking information.</pre>
 * @author smykjn
 * @Date 27-June-2017
 * @return void
 */
public void updateBankDetails()
{
	String routingNo = Stock.GetParameterValue("NewRoutingNumber");
	String accNumber = Stock.GetParameterValue("NewAccNumber");
	String expConfirmationMsg = Stock.GetParameterValue("ConfirmationMsg");
	String actConfirmationMsg="";
	try{
		Web.clickOnElement(updateLinks.get(0));
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementsDisplayed(bankSectionTitles, true);
		Web.setTextToTextBox(newRoutingNoInput, routingNo);
		Web.setTextToTextBox(newAccNoInput, accNumber);
		Web.clickOnElement(accTypeSavings);
		Web.clickOnElement(continueButtonInput);
		Web.waitForPageToLoad(Web.getDriver());
		Web.clickOnElement(continueButtonInput);
		Web.waitForPageToLoad(Web.getDriver());
		System.out.println("Form text:"+bankInformationsSection.getText());
		actConfirmationMsg = bankInformationsSection.getText().trim();
		if(actConfirmationMsg.contains(expConfirmationMsg))
			Reporter.logEvent(Status.PASS,"Fill all the banking details and click on continue.","Confirmation message is "
					+ "displayed as below:\n"+actConfirmationMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Fill all the banking details and click on continue.","Confirmation message is "
					+ "displayed as below:\n"+actConfirmationMsg, true);
		Web.clickOnElement(continueButtonInput);
		Web.waitForPageToLoad(Web.getDriver());
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
}



/**
 * <pre>This method validates the unit/Share values headers.</pre>
 * @author smykjn
 * @Date 08-Sept-2017
 * @return boolean
 */
public boolean validateUnitShareValuesheaders()
{
	boolean isHeaderExist = false;
	List<String> expUnitShareHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedUnitShareCol").split(","));
	List<String> actUnitShareHeaders = new ArrayList<String>();
	try{
		for(WebElement ele : unitShareHeaders)
		{
			actUnitShareHeaders.add(ele.getText().trim());
		}
		System.out.println("Headers are:"+actUnitShareHeaders);
		
		for(int i=0;i<expUnitShareHeaders.size();i++){
			if(actUnitShareHeaders.get(i).contains(expUnitShareHeaders.get(i)))
				{isHeaderExist=true;}
			else
				{isHeaderExist=false;
				break;}
		}
		
		if(isHeaderExist)
			Reporter.logEvent(Status.PASS,"Validate the headers as below:\n"+expUnitShareHeaders,""
					+ "below headers are displayed.\n"+actUnitShareHeaders,false);
		else
			Reporter.logEvent(Status.FAIL,"Validate the headers as below:\n"+expUnitShareHeaders,""
					+ "below headers are displayed.\n"+actUnitShareHeaders,true);
			
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
	return isHeaderExist;
}


/**
 * <pre>This method validates the watermark text on unit/share values page.</pre>
 * @author smykjn
 * @Date 09-Sept-2017
 * @return void
 */
public void validateWatermarkText()
{
	String expWatermarkText1 = Stock.GetParameterValue("ExpectedWatrMarkTxt1");
	String expWatermarkText2 = Stock.GetParameterValue("ExpectedWatrMarkTxt2");
	String expWatermarkText3 = Stock.GetParameterValue("ExpectedWatrMarkTxt3");
	String expWatermarkText4 = Stock.GetParameterValue("ExpectedWatrMarkTxt4");
	String expWatermarkText5 = Stock.GetParameterValue("ExpectedWatrMarkTxt5");
	String regExp = "/^([A-Z])\\w+[ ]([0-4]){1,2}\\,[ ]+\\b[2-9][0-9]{3}\\b/";
	System.out.println(regExp);
	String actWatermarkText1_1 = watermarkText1.get(0).getText();
	String actWatermarkText1_2 = watermarkText1.get(1).getText();
	String actWatermarkText2 = watermarkText2.getText().trim();
	String actWatermarkText3 = watermarkText3.getText().trim();
	String actWatermarkText4 = watermarkText4.getText().trim();
	String actWatermarkText5 = watermarkText5.getText().trim();
	try{
		if(actWatermarkText1_1.equals(expWatermarkText1))
			Reporter.logEvent(Status.PASS,"Validate below water mark text.\n"+expWatermarkText1,
					"below actual water mark text is displayed:\n"+actWatermarkText1_1, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below water mark text.\n"+expWatermarkText1,
					"below actual water mark text is displayed:\n"+actWatermarkText1_1, true);
		/*Pattern r = Pattern.compile(regExp);
		 Matcher m = r.matcher(actWatermarkText1_2);
		 m.matches();
		if(actWatermarkText1_2.matches(regExp))
			Reporter.logEvent(Status.PASS,"Validate below text format:\nMon dd, yyyy", "Text format is proper.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below text format:\nMon dd, yyyy", "Text format is not proper.\n"
					+ "it is in below format:"+actWatermarkText1_2, true);*/
		if(actWatermarkText2.equals(expWatermarkText2))
			Reporter.logEvent(Status.PASS,"Validate below water mark text.\n"+expWatermarkText2,
					"below actual water mark text is displayed:\n"+actWatermarkText2, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below water mark text.\n"+expWatermarkText2,
					"below actual water mark text is displayed:\n"+actWatermarkText2, true);
		if(actWatermarkText3.equals(expWatermarkText3))
			Reporter.logEvent(Status.PASS,"Validate below water mark text.\n"+expWatermarkText3,
					"below actual water mark text is displayed:\n"+actWatermarkText3, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below water mark text.\n"+expWatermarkText3,
					"below actual water mark text is displayed:\n"+actWatermarkText3, true);
		if(actWatermarkText4.equals(expWatermarkText4))
			Reporter.logEvent(Status.PASS,"Validate below water mark text.\n"+expWatermarkText4,
					"below actual water mark text is displayed:\n"+actWatermarkText4, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below water mark text.\n"+expWatermarkText4,
					"below actual water mark text is displayed:\n"+actWatermarkText4, true);
		if(actWatermarkText5.equals(expWatermarkText5))
			Reporter.logEvent(Status.PASS,"Validate below water mark text.\n"+expWatermarkText5,
					"below actual water mark text is displayed:\n"+actWatermarkText5, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate below water mark text.\n"+expWatermarkText5,
					"below actual water mark text is displayed:\n"+actWatermarkText5, true);
			
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
	
}


/**
 * <pre>This method is used to search a user with specified filters.</pre>
 * @author smykjn
 * @return void
 */
public boolean searchUser(String[] values,WebElement... filters)
{
	boolean isUserRetrieved=false;
	try{
		CommonLib.switchToFrame(frameb);
		if(filters.length==values.length){
			for(int i=0;i<filters.length;i++){
				Web.setTextToTextBox(filters[i], values[i]);
			}
			Web.clickOnElement(searchUserGoBtn);
			do{
				Thread.sleep(2000);
				System.out.println("Waiting till go button get enabled.");
			}while(!searchUserGoBtn.isEnabled());
			isUserRetrieved =Web.isWebElementDisplayed(searchResultRow, true);
		}else{
			throw new Exception("Please provide value for each filter.");
		}
	}catch(Exception e){
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occured.",e.getMessage(), true);
	}
	return isUserRetrieved;
}

/**
 * <pre>This method validates that if a user can be edited through SAW page or not.</pre>
 * @author smykjn
 * @return boolean
 */
public boolean canBeEdited() throws Exception{
	boolean isActionDropDownDisplayed=false;
	String indicator = searchResultRow.findElements(By.tagName("td")).get(10).getText().trim();
	if(indicator.equals("N/A"))
		isActionDropDownDisplayed=false;
	else
		isActionDropDownDisplayed=true;
	return isActionDropDownDisplayed;
	
}


/**
 * <pre>This method validates that if a user can be edited through SAW page or not.</pre>
 * @author smykjn
 * @return boolean
 */
public boolean validateActionDropDownOption(String expOption) throws Exception{
	boolean isActionOptionDisplayed=false;
	Select sel = new Select(searchResultRow.findElement(By.tagName("select")));
	List<WebElement> actions = sel.getOptions();
	for(WebElement option : actions){
		if(option.getText().equals(expOption)){
			isActionOptionDisplayed = true;break;
		}else{
			isActionOptionDisplayed=false;
		}
	}
	return isActionOptionDisplayed;
	
}

















}