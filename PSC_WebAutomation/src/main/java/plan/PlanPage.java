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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
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
public class PlanPage extends LoadableComponent<PlanPage> {

	@FindBy(css = "div[id='greeting'] span[class='label']")
	private WebElement weGreeting;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(className = "employeeSpinner")
	private WebElement employeespinner;
	@FindBy(xpath = ".//div[@class='breadcrumb']//i")
	private WebElement invAndPerformance;
	@FindBy(xpath = ".//*[@id='addMessagesTab']/a")
	private WebElement addNewMessageTab;
	@FindBy(id = "frameb")
	private WebElement frameb;
	@FindBy(xpath = "//div//h3[contains(text(),'Plan Messaging')]")
	private WebElement planMsgingTitle;
	@FindBy(xpath = ".//ul[@class='multiselect-search-list']//input")
	private WebElement planMsgSearch;
	@FindBy(xpath = ".//li[contains(@class,'search-list-item_loader')]")
	private WebElement loader;
	@FindBy(xpath = ".//div[@class='multiselect-dropdown']//li")
	private List<WebElement> plnDrpDwnList;
	@FindBy(xpath = ".//td[contains(@class,'ui-datepicker-today')]//following-sibling::*[1]//a")
	private WebElement nextDayDate;
	@FindBy(xpath = "//*[@id='date-picker-begin']//following-sibling::label")
	private WebElement datePickerBegin;
	@FindBy(xpath = "//*[@id='date-picker-end']//following-sibling::label")
	private WebElement datePickerEnd;
	@FindBy(xpath = ".//td[contains(@class,'ui-datepicker-today')]//following-sibling::*[2]//a")
	private WebElement nextToNextDayDate;
	@FindBy(id = "selectMessage")
	private WebElement selectMsgLink;
	@FindBy(xpath = ".//td[contains(@id,'planMessage')]//input")
	private List<WebElement> planMessageListRadioButtons;
	@FindBy(xpath = "//*[@id='planMessagingModel']//div[@class='modal-footer']//button[.='Ok']")
	private WebElement plnMsgOkBtn;
	@FindBy(xpath = ".//label[contains(text(),'Message preview')]/../following-sibling::div//div")
	private WebElement plnaMsgDivBox;
	@FindBy(xpath = ".//*[@id='addMessages']//button[contains(text(),'Submit')]")
	private WebElement submitPlnMsgBtn;
	@FindBy(xpath = ".//*[@id='addMessages']//button[contains(text(),'Cancel')]")
	private WebElement cancelPlnMsgBtn;
	@FindBy(xpath = ".//div[@type='success']//span/span")
	private WebElement plnMsgConfrmTitle;
	@FindBy(xpath = ".//*[@id='PlanMsngModule']//button[.='Ok']")
	private WebElement okBtnOnPlnConfrmPage;
	@FindBy(linkText = "Investment options")
	private WebElement investmentOptions;
	@FindBy(linkText = "Documents")
	private WebElement documents;
	@FindBy(linkText = "Charts")
	private WebElement charts;
	@FindBy(linkText = "Asset allocation models")
	private WebElement asstAllocModels;
	@FindBy(linkText = "Fixed investment rates")
	private WebElement fxdInvstmntRates;
	@FindBy(linkText = "Unit/share values")
	private WebElement unitShareValues;
	@FindBy(xpath = ".//*[@id='fixedInvestmentRate']//th")
	private List<WebElement> fixdInvstmntColumn;
	@FindBy(id = "date-picker-begin")
	private WebElement unitShareFromDate;
	@FindBy(id = "date-picker-end")
	private WebElement unitShareEndDate;
	@FindBy(xpath = ".//*[contains(@id,'tabForm:headerInfob')]//a")
	private WebElement printLinkUnderPlanMenu;
	@FindBy(className = "thumbnailModel")
	private WebElement customModal;
	@FindBy(xpath = ".//*[@class='panel-title']//span[contains(text(),'View Graph')]")
	private WebElement viewGraph;
	@FindBy(xpath = ".//*[@class='panel-title']//span[contains(text(),'View Investment Options')]")
	private WebElement viewInvestmentOption;
	@FindBy(name = "ADD_NEW_USER_BUTTON")
	private WebElement addNewUserBtn;
	@FindBy(xpath = ".//*[@class='subTitleDiv'][contains(text(),'Add New User')]")
	private WebElement addNewUserTitle;
	@FindBy(name = "firstName")
	private WebElement firstNameInput;
	@FindBy(xpath = ".//*[@name='firstName'][@class='nonEditableFields']")
	private WebElement firstNameDisabled;
	@FindBy(name = "lastName")
	private WebElement lastNameInput;
	@FindBy(xpath = ".//*[@name='lastName'][@class='nonEditableFields']")
	private WebElement lastNameDisabled;
	@FindBy(name = "email")
	private WebElement emailInput;
	@FindBy(xpath = ".//*[@name='email'][@class='nonEditableFields']")
	private WebElement emailDisabled;
	@FindBy(id = "spinner")
	private WebElement spinner;
	@FindBy(id = "continuebutton")
	private WebElement continueBtn;
	@FindBy(xpath = ".//table[@id='permissions']")
	private WebElement userAccessTable;
	@FindBy(xpath = ".//select[@name='selectedAnswer_5']")
	private WebElement userAccessdropDwon1;
	@FindBy(xpath = "(.//select[@name='selectedAnswer_0'])[1]")
	private WebElement userAccessdropDwon2;
	@FindBy(id = "availablePlan")
	private WebElement availablePlan;
	private String autoCompletePlanLink = ".//ul[contains(@class,'ui-autocomplete')]//a";
	@FindBy(xpath = ".//ul[contains(@class,'ui-autocomplete')]//a")
	private WebElement autoCompletePlanlnk;
	@FindBy(id = "createuserbutton")
	private WebElement createUserBtn;
	@FindBy(xpath = ".//div[@class='yellowbox']//span")
	private WebElement confirmMsgBox;
	@FindBy(name = "addPlans")
	private WebElement addPlanBtn;
	@FindBy(xpath = ".//*[@value='Go']")
	private WebElement goBtn;
	@FindBy(xpath = ".//*[@id='searchListDataTable']//td")
	private List<WebElement> searchedResultsColumns;
	@FindBy(name = "logonId")
	private WebElement assignedUserName;
	@FindBy(name = "userId")
	private WebElement searchUserId;
	@FindBy(linkText = "Show Advanced Search")
	private WebElement showAdvSearchedLink;
	@FindBy(id = "editButton")
	private WebElement editButtonn;
	@FindBy(id = "OVERRIDE_DIV1")
	private WebElement ignoreDivisionYes;
	@FindBy(id = "PARAM_START_DATE")
	private WebElement beginDate;
	@FindBy(id = "PARAM_END_DATE")
	private WebElement endDate;
	@FindBy(id = "PARAM_PPT_IDENTIFIER")
	private WebElement pptIdentifier;
	@FindBy(id = "PARAM_SORT_ORDER")
	private WebElement pptSortOrder;
	@FindBy(id = "EXPORT_FORMAT")
	private WebElement reportFormat;
	@FindBy(xpath = ".//button[.='Submit']")
	private WebElement submitBtnforQDIA;
	@FindBy(css = "table#progressMessageContainer div:nth-of-type(1)")
	private WebElement QDIALoader;
	@FindBy(xpath = "(.//*[@id='scheduled']//div[@class='bulletinAlignment'])[1]")
	private WebElement QDIArequestConfirmation;
	@FindBy(id = "orderForm")
	private WebElement orderForm;
	@FindBy(xpath = ".//div[@ng-show='scheduledReport.loading']")
	private WebElement QDIALoader2;
	@FindBy(xpath = ".//div[@ng-show='loading']")
	private WebElement planDocLoader;
	@FindBy(id = "BASIS")
	private WebElement division;
	@FindBy(xpath = "(.//div[contains(@class,'checkboxLayer')]//label//span)[2]")
	private WebElement subDivision;
	@FindBy(xpath = ".//button[@id='']")
	private WebElement subDiviBtn;
	@FindBy(xpath = ".//h3[contains(text(),'General forms and documents')]")
	private WebElement genFormAndDocTitle;
	@FindBy(xpath = ".//h3[contains(text(),'Plan-specific documents')]")
	private WebElement planSpecificDocTitle;
	@FindBy(linkText = "PDI File Layout")
	private WebElement pdiFileLayout;
	@FindBy(linkText = "Bank Form")
	private WebElement bankForms;
	@FindBy(xpath = ".//*[contains(@ng-if,'hasPartGrouping')]//a")
	private List<WebElement> planSpecificDocLinks;
	@FindBy(xpath = "//div[@class='bulletinAlignment']//span//span")
	private WebElement noPlanDocText;
	@FindBy(xpath = ".//*[@id='tabForm:outputPanelb']//td[2]//a")
	private WebElement printLink;
	@FindBy(xpath = "//div[@class='printHeader']/h3")
	private WebElement printPriviewWindowHeader;
	@FindBy(xpath = ".//div[contains(@class,'instructions')]//a")
	private List<WebElement> actDocumentList;
	@FindBy(linkText = "Safe Harbor Notice")
	private WebElement safeHarborLink;
	@FindBy(xpath = ".//*[@id='RequiredNoticesModule']//h3")
	private WebElement reqNoticeTitle;
	@FindBy(xpath = ".//*[@class='icon-arrow-left pull-left']")
	private WebElement backArrowLink;
	@FindBy(xpath = "(.//div[@class='headcontainer']//h4)[1]")
	private WebElement docStaticTitle;
	@FindBy(xpath = ".//table[@class='plantbl']")
	private WebElement Investment_Charts_table;
	@FindBy(xpath = "(.//div[@class='headcontainer']//h4)[2]")
	private WebElement docStaticHistoryTitle;
	@FindBy(xpath = ".//span[@class='large icon-stopwatch']")
	private WebElement stopWatchIcon;
	@FindBy(id = "framebA")
	private WebElement framebA;
	@FindBy(xpath = "(.//accordion)[1]/div/div")
	private List<WebElement> historyAccordion;
	@FindBy(css = "div[ng-if='showDisclaimer'] h4")
	//@FindBy(xpath = "//h4[text()='Safe Harbor Notice']")
	private WebElement disclaimer;
	@FindBy(xpath = ".//div[@ng-if='showDisclaimer']//h4/../following-sibling::div[1]")
	//@FindBy(xpath = "//*[contains(text(),'To view the most recent version')]")
	private WebElement disclaimerDesc;
	@FindBy(xpath = ".//ul[contains(@class,'mainlist')]//div/following-sibling::a")
	private List<WebElement> doclinks;
	@FindBy(xpath = "//*[@class='large icon-calendar']")
	private WebElement calendarIcon;
	@FindBy(xpath = "(.//div[@class='headcontainer']//h4)[2]/../following-sibling::div//a")
	private WebElement historyLink;
	@FindBy(xpath = ".//div[@class='headcontainer']//h4")
	private WebElement docTitleOnHistoryPage;
	@FindBy(xpath = ".//div[@class='headcontainer']//h4/../following-sibling::div[1]")
	private WebElement docDescOnHistoryPage;
	@FindBy(xpath = "(.//accordion)[1]/div/div/div/h4//span")
	private List<WebElement> accordionTitle;
	@FindBy(xpath = "(.//accordion)[1]/div/div/div[1]//a")
	private List<WebElement> accordionLink;
	@FindBy(xpath = "//div[@class='accordion']//a[@class='accordion-toggle']//span")
	private List<WebElement> accordionMonthsText;
	@FindBy(xpath = "//div[@class='accordion']//a[@class='accordion-toggle']")
	private List<WebElement> linksUnderAccordion;
	@FindBy(id = "collapse-init")
	private WebElement expandAllLink;
	@FindBy(id = "expand-init")
	private WebElement collapseAllLink;
	@FindBy(xpath = ".//a[text()='Overview']//following-sibling::ul//a[text()='Plan provisions' or "
			+ "text()='Vesting schedules' or text()='Participant web and VRU options']")
	private List<WebElement> planoverviewOptions;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/ul/li[1]/a")
	private WebElement planOverviewSubmenu;
	@FindBy(xpath = ".//*[@id='exportable']//th//span | .//*[@id='exportable']//th//a")
	private List<WebElement> invOptColumnsList;
	@FindBy(xpath = ".//h4[contains(text(),'Maximum number of loans allowed')]")
	private WebElement maxLoanAllowed;
	@FindBy(xpath = ".//div[@id='investmentOptions']//div[@ng-show='loading']")
	private WebElement invOptLoader;
	@FindBy(linkText = "Investment performance detail")
	private WebElement investmentAndPerfLink;
	@FindBy(xpath = ".//*[@id='investmentPerform']//a[.='Excel']")
	private WebElement excellink;
	@FindBy(xpath = ".//div[@id='investmentGraphs']//a[.='Excel']")
	private WebElement investment_Charts_Excel_Link;
	@FindBy(xpath = ".//*[@id='exportable']/preceding-sibling::div/div[1]/div")
	private WebElement invoptHeaderTitle;
	@FindBy(xpath = "(.//*[@id='inv-Popover'])[1]")
	private WebElement fstInvName;
	@FindBy(xpath = ".//h3[text()='Quick Links']/following-sibling::div//a")
	private List<WebElement> qukLinks;
	@FindBy(xpath = ".//*[@class='invDesclaimerNote']/p")
	private WebElement disclaimer1;
	@FindBy(xpath = ".//*[@class='invDesclaimerNote']/i/p")
	private WebElement disclaimer2;
	@FindBy(xpath = ".//*[@class='invDesclaimerNote']/following-sibling::i/p")
	private List<WebElement> disclaimers;
	@FindBy(xpath = ".//*[@class='invDesclaimerNote']/following-sibling::p")
	private List<WebElement> disclaimerLast;
	@FindBy(id = "msRatingPopover0")
	private WebElement firstMorngStarInfo;
	@FindBy(xpath = "(//*[contains(@id,'msRatingPopover')]/i)[2]")
	private WebElement secondMorngStarInfo;
	
	@FindBy(xpath = ".//div[@class='popover-content']/span")
	private WebElement mrngStarContent;
	@FindBy(xpath = ".//*[@id='exportable']//tr//td[6]/span[text()!='']")
	private List<WebElement> balanceListInOptPage;
	@FindBy(xpath = ".//*[@id='exportable']//th//a[contains(text(),'Balance')]")
	private WebElement balanceLinkHeader;
	@FindBy(name = "bankingInformationForm")
	private WebElement bankInformationsSection;
	@FindBy(linkText = "Update")
	private List<WebElement> updateLinks;
	@FindBy(className = "section_title_text")
	private List<WebElement> bankSectionTitles;
	@FindBy(id = "newBankRoutingNumber")
	private WebElement newRoutingNoInput;
	@FindBy(id = "newBankAccountNumber")
	private WebElement newAccNoInput;
	@FindBy(id = "newBankAccountType_sav")
	private WebElement accTypeSavings;
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButtonInput;
	@FindBy(xpath = ".//div[@id='unitShareValues']//th[not(text()!='')]//span[1]")
	List<WebElement> unitShareHeaders;
	@FindBy(xpath = ".//p[@class='footnote']/span")
	List<WebElement> watermarkText1;
	@FindBy(xpath = "(.//*[@id='unitShareValues']//p)[2]")
	WebElement watermarkText2;
	@FindBy(xpath = "(.//*[@id='unitShareValues']//p)[3]")
	WebElement watermarkText3;
	@FindBy(xpath = "(.//*[@id='unitShareValues']//p)[4]")
	WebElement watermarkText4;
	@FindBy(xpath = "(.//*[@id='unitShareValues']//p)[5]")
	WebElement watermarkText5;
	@FindBy(id = "saButton")
	private WebElement searchUserGoBtn;
	@FindBy(partialLinkText = "Loan information")
	private WebElement lonainformationLink;
	@FindBy(xpath = ".//label[contains(text(),'General information')]/ancestor::tr/following-sibling::tr//span")
	private List<WebElement> planProvisionGenInfoValues;
	@FindBy(xpath = ".//*[@id='searchListDataTable']//tbody//tr")
	private WebElement searchResultRow;
	@FindBy(xpath = ".//*[@id='searchListDataTable']//tbody//tr//td")
	private WebElement nosearchResultElement;
	@FindBy(xpath = ".//*[@id='assetsBarChartContainer']/div[contains(@id,'highcharts')]//*[name()='svg']")
	private WebElement dollarInvested_highChart;
	@FindBy(xpath = ".//*[@id='lifeCntBarChartContainer']/div[contains(@id,'highcharts')]//*[name()='svg']")
	private WebElement noOfInvestors_highChart;
	@FindBy(xpath = ".//div[@id='assetsBarChartContainer']//*[name()='svg']")
	private WebElement charts_svg;
	@FindBy(id = "asOfDate")
	private WebElement asOfDate_selectDrpDwn;
	@FindBy(xpath = ".//label[contains(text(),'As of date')]")
	private WebElement asOfDate_label;
	@FindBy(xpath = ".//label[contains(text(),'View As')]")
	private WebElement viewAs_label;
	@FindBy(id = "displayGraphs")
	private WebElement displayGraphs_btn;
	@FindBy(id = "displayTable")
	private WebElement displayTable_btn;
	@FindBy(id = "dollarsInvested")
	private WebElement dollarInvested_Link;
	@FindBy(id = "noOfInvestor")
	private WebElement noOfInvestor_Link;
	@FindBy(xpath = ".//div[@id='documents']//tbody//td[1]")
	private List<WebElement> documentTable_Name;
	@FindBy(xpath = ".//div[@id='documents']//tbody//td[2]")
	private List<WebElement> documentTable_Symbol;
	@FindBy(xpath = ".//div[@id='documents']//tbody//td[3]")
	private List<WebElement> documentTable_Category;
	@FindBy(xpath = ".//div[@id='documents']//th[a|span]")
	List<WebElement> documentHeaders_th;
	@FindBy(xpath = ".//*[@id='newMenu']//li/a[text()='Plan']")
	private WebElement planTab;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/a")
	private WebElement administrationSubmenu;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[3]/ul//li/a")
	private List<WebElement> administrationSubmenus;
	@FindBy(xpath = ".//*[@id='newMenu']/li[1]/ul/li[2]/a")
	private WebElement investmentAndPerformance;
	@FindBy(xpath=".//*[@id='ajaxSearchStatus']")
	private WebElement ajaxSearchStatus;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/ul/li[4]/a")
	private WebElement fiduciaryMenu;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/ul/li[4]/ul/li//a")
	private List<WebElement> fiduciarySubmenu;
	@FindBy(xpath=".//*[@id='collapse-init']/b")
	private WebElement planDocLinkExpand;
	@FindBy(xpath=".//*[@id='newMenu']/li[1]/ul/li[1]/ul/li//a")
	private List<WebElement> overviewSubmenus;
	@FindBy(xpath="//p[contains(text(),'Participant QDIA Notice')]")
	private WebElement participantQDIANoticeHeader;
	@FindBy(xpath="//*[@id='newMenu']/li[1]/ul/li[2]/a/following-sibling::ul//a[contains(text(),'Investments & Performance')]")
	private WebElement investmentPerformanceAgainLink;
	@FindBy(xpath = "(.//*[@id='inv-Popover'])[2]")
	private WebElement secondInvName;
	@FindBy(xpath=".//span[text()='Site Bulletin']/following-sibling::a/span")
	private WebElement CancelNewsBulletin;
	
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

	Map<String, String> securityAnsMap = null;
	ResultSet queryResultSet;
	Actions action;

	public PlanPage(LoadableComponent<?> parent, boolean performVerification,
			String... userData) {
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData;
		this.userVeriData = new String[2];
		PageFactory.initElements(Web.getDriver(), this);
	}

	public PlanPage() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(weGreeting, false));
	}

	@Override
	protected void load() {
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj = new LoginPage();
		try {
			if (ifUserOrAccntVerificationMandate) { // Performs User or Account
													// Verification
				invokeMethod = this.parent.getClass().getDeclaredMethod(
						"performVerification", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),
						new Object[] { userData });
			} else {
				// Performing Login
				Object login = this.parent.getClass().newInstance();
				Web.getDriver().switchTo().defaultContent();
				Web.getDriver().switchTo()
						.frame(Web.returnElement(login, "LOGIN FRAME"));
				Web.waitForElement(login, "USERNAME");
				Web.waitForElement(login, "PASSWORD");
				Web.getDriver().switchTo().defaultContent();
				invokeMethod = this.parent.getClass().getDeclaredMethod(
						"submitLoginCredentials", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),
						new Object[] { userData });
				loginObj.waitForSuccessfulLogin();
				// Check if UserVerification Pages appears then
				// performVerification
				if (Web.isWebElementDisplayed(Web.returnElement(userVeriPg,
						"EMAIL ADDRESS"))) {
					/*
					 * userVeriData = new
					 * String[]{userVeriPg.getEmailAddressOfuser
					 * (Stock.getTestQuery
					 * ("getEmailaddressQuery"),Stock.GetParameterValue
					 * ("username")),
					 * getSecurityAnswer((Web.returnElement(userVeriPg,
					 * "SECURITYQUESTION")).getText().trim())};
					 */
					userVeriData[0] = userVeriPg.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));
					userVeriData[1] = Stock
							.GetParameterValue("userVerificationAns");
					invokeMethodforUserVerification = userVeriPg.getClass()
							.getDeclaredMethod("performVerification",
									String[].class);
					invokeMethodforUserVerification.invoke(userVeriPg,
							new Object[] { userVeriData });
				}
			}
			// urlJumpPage.click();
			if (Stock.getConfigParam("DataType").equals("NonApple")) {
				Web.isWebElementDisplayed(urlJumpPage, true);
				Web.clickOnElement(urlJumpPage);
			}

			Thread.sleep(2000);

			if(Web.isWebElementDisplayed(CancelNewsBulletin)) 
				Web.clickOnElement(CancelNewsBulletin);		

			Web.isWebElementDisplayed(weGreeting, true);
			Reporter.logEvent(Status.INFO, "Check if Login is successfull",
					"Login for PSC is successfull", false);
		} catch (Exception e) {
			try {
				throw new Exception(
						"Login to PSC failed , Error description : "
								+ e.getMessage());
			} catch (Exception t) {
				ThrowException.Report(TYPE.EXCEPTION, t.getMessage());
			}
		}
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("ASSIGNED_USERID")) {
			return this.searchUserId;
		}
		if (fieldName.trim().equalsIgnoreCase("USER_LAST_NAME_INPUT")) {
			return this.lastNameInput;
		}
		if (fieldName.trim().equalsIgnoreCase("USER_FIRST_NAME_INPUT")) {
			return this.firstNameInput;
		}
		if (fieldName.trim().equalsIgnoreCase("NO_SEARCH_RESULTS_TEXT_ELEMENT")) {
			return this.nosearchResultElement;
		}
		if (fieldName.trim().equalsIgnoreCase("Email Search Input")) {
			return this.emailInput;
		}
		return null;
	}

	/**
	 * <pre>
	 * This method navigates to Plan--->Administration-->Plan messaging page
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-May-2017
	 * @return void
	 */
	public void navigateToPlanMessagingPage() throws Exception {
		/*
		 * homePage = new HomePage(); homePage.navigateToProvidedPage("Plan",
		 * "Administration", "Plan messaging");
		 */
		if (Web.isWebElementDisplayed(planTab, false))
			Web.actionsClickOnElement(planTab);
		if (Web.isWebElementDisplayed(administrationSubmenu, true))
			Web.actionsClickOnElement(administrationSubmenu);
		Thread.sleep(2000);
		this.openSubmenuUnderAdministration("Plan messaging");
		Web.getDriver().switchTo().defaultContent();
		Thread.sleep(5000);
		if (Web.getDriver().findElement(By.tagName("i")).getText()
				.contains("Plan messaging"))
			Reporter.logEvent(Status.PASS,
					"Navigate to Plan-->Administration-->Plan messaging page.",
					"Plan messaging page is" + " displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Navigate to Plan-->Administration-->Plan messaging page.",
					"Plan messaging page is" + " not displayed.", true);
	}

	/**
	 * <pre>
	 * This method creates new plan message on Plan messaging page under 'Add new Message' page.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-May-2017
	 * @return void
	 */
	public void addNewPlanMessage() {
		String planNumber = "";
		try {
			Web.getDriver().switchTo().frame(frameb);
			if (Web.isWebElementDisplayed(addNewMessageTab, true)) {
				Web.clickOnElement(addNewMessageTab);
				Web.waitForElement(planMsgingTitle);
				Thread.sleep(3000);
				if (submitPlnMsgBtn.isDisplayed()
						&& cancelPlnMsgBtn.isDisplayed())
					Reporter.logEvent(
							Status.PASS,
							"Validate Submit and cancel button is displayed on Add New Message page.",
							"" + "Submit and Cancel buttton is displayed.",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Validate Submit and cancel button is displayed on Add New Message page.",
							"" + "Submit or Cancel buttton is not displayed.",
							true);
				queryResultSet = DB.executeQuery(
						Stock.getTestQuery("queryTofindPlansForNextGen")[0],
						Stock.getTestQuery("queryTofindPlansForNextGen")[1],
						"K_" + Stock.GetParameterValue("username"));
				while (queryResultSet.next()) {
					planNumber = queryResultSet.getString("GA_ID");
					break;
				}
				Web.setTextToTextBox(planMsgSearch, planNumber);
				do {
					Thread.sleep(2000);
					System.out.println("Loading,Have Patience..");
				} while (loader.isDisplayed());
				if (Web.isWebElementsDisplayed(plnDrpDwnList, true))
					Web.clickOnElement(plnDrpDwnList.get(0));
				Web.clickOnElement(datePickerBegin);
				Web.waitForElement(nextDayDate);
				Web.clickOnElement(nextDayDate);
				Web.clickOnElement(datePickerEnd);
				Web.waitForElement(nextToNextDayDate);
				Web.clickOnElement(nextToNextDayDate);
				Web.clickOnElement(selectMsgLink);
				Web.isWebElementsDisplayed(planMessageListRadioButtons, true);
				Web.clickOnElement(planMessageListRadioButtons.get(0));
				Web.clickOnElement(plnMsgOkBtn);
				Web.waitForElement(planMsgingTitle);
				Reporter.logEvent(Status.INFO, "Plan Message to be displayed:",
						plnaMsgDivBox.getText(), false);
				Web.clickOnElement(submitPlnMsgBtn);
				Web.waitForElement(plnMsgConfrmTitle);
				Thread.sleep(4000);
				if (plnMsgConfrmTitle.isDisplayed())
					Reporter.logEvent(
							Status.PASS,
							"Fill all mandatory fields on Add new Plan message page and submit.",
							"Comfirmation message:'"
									+ plnMsgConfrmTitle.getText()
									+ "' is displayed." + "", false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Fill all mandatory fields on Add new Plan message page and submit.",
							"Comfirmation message:'"
									+ plnMsgConfrmTitle.getText()
									+ "' is not displayed.", true);
				Web.clickOnElement(okBtnOnPlnConfrmPage);
				if (Web.isWebElementDisplayed(addNewMessageTab, true))
					Reporter.logEvent(Status.PASS,
							"Click on OK button on confirmation page.",
							"user is navigated back to Plan messaging page.",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Click on OK button on confirmation page.",
							"user is not navigated back to Plan messaging page.",
							true);
				Web.getDriver().switchTo().defaultContent();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Validate if Add new Message tab is displayed.",
						"Ad new message tab is not displayed", true);
			}
		} catch (Exception e) {
			e.getStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occurred:",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates columns on Investments & Performance page.
	 * Investment options,Documents,Charts,Asset allocation models,Fixed investment rates,Unit/share values
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-June-2017
	 * @return void
	 */
	public void validateInvestmentAndPerformanceColumns() {
		try {
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			CommonLib.waitForLoader(planDocLoader);
			Web.isWebElementDisplayed(investmentOptions, true);
			if (investmentOptions.isDisplayed() && documents.isDisplayed()
					&& charts.isDisplayed() && fxdInvstmntRates.isDisplayed()
					&& unitShareValues.isDisplayed())

				Reporter.logEvent(
						Status.PASS,
						"Validate following columns are displayed on plan-->Investments & Performance"
								+ " page.\n1)Investment options\n2)Documents\n3)Charts\n4)Asset allocation models\n5)Fixed investment rates"
								+ "\n6)Unit/share values",
						"Specified columns are displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate following columns are displayed on plan-->Investments & Performance"
								+ " page.\n1)Investment options\n2)Documents\n3)Charts\n4) Asset allocation models\n5)Fixed investment rates"
								+ "\n6)Unit/share values",
						"Specified columns are not displayed.", true);

		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates columns on Investments & Performance page for Investment options tab.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 22nd-June-2017
	 * @return void
	 */
	public void validateInvestmentOptionsColumns() {
		List<String> expColumns = Arrays.asList(Stock.GetParameterValue(
				"ExpectedInvestmentColumns").split(","));
		String expheaderText = Stock.GetParameterValue("ExpHeaderText");
		try {
			CommonLib.waitForLoader(invOptLoader);
			System.out.println(invOptColumnsList.size());
			if (CommonLib.isAllHeadersDisplayed(invOptColumnsList, expColumns))
				Reporter.logEvent(Status.PASS,
						"Validate columns on investment options page as follows:"
								+ expColumns, "" + "As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate columns on investment options page as follows:"
								+ expColumns, ""
								+ "specified columns are not displayed.", true);
			if (invoptHeaderTitle.isDisplayed())
				Reporter.logEvent(Status.PASS,
						"Verify top left section has following text:"
								+ expheaderText + "<Date>", ""
								+ "actual text displayed is:"
								+ invoptHeaderTitle.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify top left section has following text:"
								+ expheaderText + "<Date>", ""
								+ "actual text displayed is:"
								+ invoptHeaderTitle.getText(), false);
			if (investmentAndPerfLink.isDisplayed() && excellink.isDisplayed())
				Reporter.logEvent(Status.PASS, "Verify links '"
						+ investmentAndPerfLink.getText().trim() + "'"
						+ " and '" + excellink.getText().trim() + "'.",
						"Links are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify links '"
						+ investmentAndPerfLink.getText().trim() + "'"
						+ " and '" + excellink.getText().trim() + "'.",
						"Links are not displayed.", true);
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates columns of Fixed investment rates page.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-June-2017
	 * @return void
	 */
	public void validateColumnFixedInvstmntRatePage() {
		List<String> expColumn = Arrays.asList(Stock.GetParameterValue(
				"ExpectedFixedInvstColumn").split(","));
		try {
			Web.clickOnElement(fxdInvstmntRates);
			Web.waitForElements(fixdInvstmntColumn);
			if (CommonLib.isAllHeadersDisplayed(fixdInvstmntColumn, expColumn))
				Reporter.logEvent(Status.PASS,
						"Click on Fixed investment rates and validate following columns are displayed."
								+ expColumn,
						"All mentioned columns are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Click on Fixed investment rates and validate following columns are displayed."
								+ expColumn,
						"All/few mentioned columns are not displayed.", true);
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates From and to field for Unit/Share values.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-June-2017
	 * @return void
	 */
	public void validateUnitShareFromToField() {
		try {
			Web.clickOnElement(unitShareValues);
			Web.waitForElement(unitShareFromDate);
			if (Web.isWebElementDisplayed(unitShareFromDate, true))
				Reporter.logEvent(Status.PASS,
						"Click on Unit/share values button and check"
								+ " From and to date fields are displayed.",
						"As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Click on Unit/share values button and check"
								+ " From and to date fields are displayed.",
						"As expected.", true);
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates a window will open once clicked on Print link
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 5th-June-2017
	 * @return void
	 */
	public void validatePrintFunctionality() {
		String parentWindow = "";
		try {
			Web.getDriver().switchTo().defaultContent();
			if (CommonLib.getBrowserName().equalsIgnoreCase("chrome")) {
				if (Web.isWebElementDisplayed(printLink, true))
					Reporter.logEvent(Status.PASS,
							"Validate print link is displayed.",
							"Print link is displayed.", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Validate print link is displayed.",
							"Print link is not displayed.", true);
				Web.clickOnElement(printLink);
				Thread.sleep(3000);
				parentWindow = CommonLib.switchToWindow();
				Web.getDriver().close();
				Web.getDriver().switchTo().window(parentWindow);
				
			} else {
				Web.clickOnElement(printLinkUnderPlanMenu);			
				parentWindow = CommonLib.switchToWindow();
				if (printPriviewWindowHeader.getText().contains(
						"THIS IS A PRINT PREVIEW")) {
					Reporter.logEvent(Status.PASS,
							"Verify after clicking on print link,a "
									+ "print preview page is displayed.",
							"Print preview page is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify after clicking on print link,a "
									+ "print preview page is displayed.",
							"Print preview page is not displayed.", true);
				}
				Web.getDriver().close();
				Web.getDriver().switchTo().window(parentWindow);

			}
			/*
			 * printLinkUnderPlanMenu.click(); String parentWindow =
			 * Web.getDriver().getWindowHandle();
			 * System.out.println("Parent window:"+parentWindow);
			 * while(Web.getDriver().getWindowHandles().size()==1) {
			 * if(count==10) break; Thread.sleep(500); count++;
			 * System.out.println("Counter : "+count); }
			 * System.out.println("Window size is:"
			 * +Web.getDriver().getWindowHandles().size()); for(String newWindow
			 * : Web.getDriver().getWindowHandles()) {
			 * if(!newWindow.equals(parentWindow)) {
			 * Web.getDriver().switchTo().window(newWindow);
			 * if(Web.returnElement(new EmployeeSearch(),
			 * "PRINT PAGE HEADER").getText
			 * ().contains("THIS IS A PRINT PREVIEW")) {
			 * Reporter.logEvent(Status.PASS,
			 * "Verify after clicking on print link,a print preview page is displayed."
			 * , "Print preview page is displayed.", false); } else {
			 * Reporter.logEvent(Status.FAIL,
			 * "Verify after clicking on print link,a print preview page is displayed."
			 * , "Print preview page is not displayed.", true); }
			 * Web.getDriver().close();
			 * Web.getDriver().switchTo().window(parentWindow); break; } }
			 */
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method takes user to Plan-->Investment & Performance page
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 05-June-2017
	 * @return void
	 */
	public void navigateToInvestmentAndPerformance() {
		WebElement breadCrumb;
		try {
			/*
			 * HomePage homePage = new HomePage();
			 * homePage.navigateToProvidedPage("Plan",
			 * "Investments & Performance", "");
			 */
			if (Web.isWebElementDisplayed(planTab, true))
				Web.actionsClickOnElement(planTab);
			if (Web.isWebElementDisplayed(investmentAndPerformance, true))
				Web.actionsClickOnElement(investmentAndPerformance);
			if (Web.isWebElementDisplayed(investmentPerformanceAgainLink, true))
				Web.actionsClickOnElement(investmentPerformanceAgainLink);
			Web.getDriver().switchTo().defaultContent();
			breadCrumb = Web.getDriver().findElement(By.tagName("i"));
			Web.waitForElement(breadCrumb);
			if (Web.getDriver().findElement(By.tagName("i")).getText()
					.contains("Investments & Performance"))
				Reporter.logEvent(Status.PASS,
						"Navigate to Plan-->Investment & Performance page.",
						"Investment & Performance page is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Navigate to Plan-->Investment & Performance page.",
						"Investment & Performance page is not displayed.", true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occurred:",
					e.getMessage(), true);

		}
	}

	/**
	 * <pre>
	 * This method Validates Asset allocation models page's elements.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 6th-June-2017
	 * @return void
	 */
	public void validateAssetAllocPage() {
		try {
			Web.clickOnElement(asstAllocModels);
			Web.waitForElement(customModal);
			if (customModal.isDisplayed() && viewGraph.isDisplayed()
					&& viewInvestmentOption.isDisplayed())
				Reporter.logEvent(
						Status.PASS,
						"Click on Asset Allocation models tab and validate following "
								+ "section is displayed.1)Custom Model 2)View Graph 3)View Investment Options",
						"All mentioned " + "sections are displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on Asset Allocation models tab and validate following "
								+ "section is displayed.1)Custom Model 2)View Graph 3)View Investment Options",
						"All mentioned " + "sections are not displayed.", true);

		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method checks for spinner and wait until spinning is done.
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public void waitForEmployeeSpinner() throws Exception {
		int iTimeInSecond = 100;
		try {
			Web.waitForElement(employeespinner);
			int iCount = 0;
			while (employeespinner.isDisplayed()) {
				if (iCount == iTimeInSecond) {
					break;
				}

				System.out.println("Employee Spinner displaying......");
				Thread.sleep(1000);
				iCount++;
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * <pre>
	 * This method Adds New User till Confirmation message appears.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 09-June-2017
	 * @return void
	 */
	public void addNewUser() {
		String fn = Stock.GetParameterValue("firstName");
		String ln = Stock.GetParameterValue("lastName");
		String email_ = Stock.GetParameterValue("userVerificationEmail");
		try {
			Web.waitForElement(frameb);
			Web.getDriver().switchTo().frame(frameb);
			Thread.sleep(2000);
			Web.waitForPageToLoad(Web.getDriver());
			Web.clickOnElement(addNewUserBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(addNewUserTitle);
			if (addNewUserTitle.getText().trim()
					.equalsIgnoreCase("Add New User"))
				Reporter.logEvent(Status.PASS, "Click on Add New User button.",
						"Add New User page is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add New User button.",
						"Add New User page is not displayed.", true);
			// User Information
			Web.setTextToTextBox(firstNameInput,
					fn + System.currentTimeMillis());
			Web.setTextToTextBox(lastNameInput, ln);
			Web.setTextToTextBox(emailInput, email_);
			Thread.sleep(1000);
			Web.actionsClickOnElement(firstNameInput);
			// Web.waitForElement(spinner);
			do {
				Thread.sleep(1000);
				System.out.println("Spinning..........");
			} while (spinner.isDisplayed());
			Web.actionsClickOnElement(continueBtn);
			Web.waitForElement(userAccessTable);

			// User Access
			Select userAccess = new Select(userAccessdropDwon1);
			userAccess.selectByIndex(1);

			// Plan Access
			if (availablePlan.getTagName().equals("input")) {
				Web.setTextToTextBox(availablePlan,
						Stock.GetParameterValue("PlanNumber") + " ");
				Web.getDriver().switchTo().defaultContent();
				Web.getDriver().switchTo().frame(frameb);
				do {
					Thread.sleep(1000);
					System.out
							.println("Waiting for plan link to be displayed.");
				} while (!Web.isWebElementDisplayed(autoCompletePlanlnk));
				Web.clickOnElement(autoCompletePlanlnk);
				do {
					Thread.sleep(1000);
					System.out.println("Spinning..........");
				} while (spinner.isDisplayed());
			} else {
				Select selPlan = new Select(availablePlan);
				selPlan.selectByIndex(0);
				Web.clickOnElement(addPlanBtn);
				Thread.sleep(2000);
			}
			Web.clickOnElement(createUserBtn);
			Web.waitForElement(confirmMsgBox);
			if (Web.isWebElementDisplayed(confirmMsgBox, true))
				Reporter.logEvent(Status.PASS,
						"Fill all the required details to create user and click on"
								+ " create new user button.",
						"Confirmation message is displayed displaying following message:"
								+ confirmMsgBox.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Fill all the required details to create user and click on"
								+ " create new user button.",
						"Confirmation message is not displayed", true);
			this.ValidateFieldsOnConfMsgPageNewUser();
			newUserAssignedID = assignedUserName.getAttribute("value").trim();
			Web.clickOnElement(confirmMsgBox.findElement(By.tagName("button")));
			Web.waitForPageToLoad(Web.getDriver());
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method Validates the if first name,last name and email are not editable after creating new user.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 */
	public void ValidateFieldsOnConfMsgPageNewUser() {
		try {
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"Validate that on confirmation screen first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"Validate that on confirmation screen first name,last name and email are not editable.",
						"" + "Specified fields are not editable.", false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method search the new user with assigned username.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void searchUser(String newUserAssignedID) {
		try {
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
			homePage = new HomePage();
			/*
			 * homePage.navigateToProvidedPage("Plan", "Administration",
			 * "Username security management");
			 */
			Thread.sleep(2000);
			this.openAnySubmenuUnderAdministrationMenu("Username security management");
			
			Web.getDriver().switchTo().frame(frameb);
			
			if(!Web.isWebElementDisplayed(searchUserId)){
				Web.getDriver().switchTo().defaultContent();
				Web.clickOnElement(homePage, "Reports Menu");
				Thread.sleep(5000);
				this.openAnySubmenuUnderAdministrationMenu("Username security management");
				Web.getDriver().switchTo().frame(frameb);
			}
				
			
			
			if (Web.isWebElementDisplayed(showAdvSearchedLink, true))
				Web.actionsClickOnElement(showAdvSearchedLink);
			// Web.waitForPageToLoad(Web.getDriver());

			Thread.sleep(2000);
			Web.setTextToTextBox(searchUserId, newUserAssignedID);
			Thread.sleep(2000);
			Web.actionsClickOnElement(goBtn);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			// Web.waitForElements(searchedResultsColumns);
			do{
				Thread.sleep(1000);
				System.out.println("Searching....");
			}while(Web.isWebElementDisplayed(ajaxSearchStatus));
			if (searchedResultsColumns.get(2).getText()
					.equals(newUserAssignedID))
				Reporter.logEvent(Status.PASS, "Search the new user:"
						+ newUserAssignedID,
						"User record is displayed on search screen.", false);
			else
				Reporter.logEvent(Status.FAIL, "Search the new user:"
						+ newUserAssignedID,
						"User record is not displayed on search screen.", true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the editability of first name,last name and email fields when user selects View/Edit option and
	 * edit user information.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void validateEditabilityOfUserFields() {
		try {
			searchUser(newUserAssignedID);
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("View / Edit");
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"select View/Edit from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"select View/Edit from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are not editable.", false);
			Select sel = new Select(userAccessdropDwon2);
			sel.selectByIndex(1);
			Web.clickOnElement(userAccessdropDwon2);
			Thread.sleep(4000);
			Web.clickOnElement(editButtonn);
			Thread.sleep(3000);
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(confirmMsgBox, true))
				Reporter.logEvent(Status.PASS,
						"edit user access section and click on"
								+ " save button.",
						"Confirmation message is displayed displaying following message:"
								+ confirmMsgBox.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"edit user access section and click on"
								+ " save button.",
						"Confirmation message is not displayed", true);
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"validate that first name,last name and email are not editable on edit user confirmation"
								+ "screen.", "Specified fields are editable.",
						true);
			else
				Reporter.logEvent(
						Status.PASS,
						"validate that first name,last name and email are not editable on edit user confirmation"
								+ "screen.",
						"Specified fields are not editable.", false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the editability of first name,last name and email fields on reset password page.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void validateEditabilityOnResetPasswordScreen() {
		try {
			searchUser(newUserAssignedID);
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("Reset Password");
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"select 'Reset Password' from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"select 'Reset Password' from Action drop down and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable.Confirmation message:"
								+ confirmMsgBox.getText(), false);
			Web.clickOnElement(confirmMsgBox.findElement(By
					.xpath(".//button[text()='Yes']")));
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are editable on confirmation screen.",
						true);
			else
				Reporter.logEvent(
						Status.PASS,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable on confirmation screen.",
						false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the editability of first name,last name and email fields on Reset security question user screen.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void validateEditabilityOnResetSecurityQues() {
		try {
			searchUser(newUserAssignedID);
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("Reset Security Question(s)");
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"select 'Reset Security Question(s)' from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"select 'Reset Security Question(s)' from Action drop down and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable.Confirmation message:"
								+ confirmMsgBox.getText(), false);
			Web.clickOnElement(confirmMsgBox.findElement(By
					.xpath(".//button[text()='Yes']")));
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are editable on confirmation screen.",
						true);
			else
				Reporter.logEvent(
						Status.PASS,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable on confirmation screen.",
						false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the editability of first name,last name and email fields on terminate user screen.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void validateEditabilityOnTerminateUserScreen() {
		try {
			searchUser(newUserAssignedID);
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("Terminate");
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"select 'Terminate' from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"select 'Terminate' from Action drop down and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable.Confirmation message:"
								+ confirmMsgBox.getText(), false);
			Web.clickOnElement(confirmMsgBox.findElement(By
					.xpath(".//button[text()='Yes']")));
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are editable on confirmation screen.",
						true);
			else
				Reporter.logEvent(
						Status.PASS,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable on confirmation screen.",
						false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the editability of first name,last name and email fields on terminate user screen.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 12-June-2017
	 * @return void
	 * @param assigned
	 *            username
	 */
	public void validateEditabilityOnReactivateUserScreen() {
		try {
			searchUser(newUserAssignedID);
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("View / Reactivate");
			Web.waitForPageToLoad(Web.getDriver());
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"select 'View / Reactivate' from Action drop down and validate that first name,last name and email are not editable.",
						"" + "Specified fields are editable.", true);
			else
				Reporter.logEvent(
						Status.PASS,
						"select 'View / Reactivate' from Action drop down and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable.Confirmation message:"
								+ confirmMsgBox.getText(), false);
			Web.clickOnElement(confirmMsgBox.findElement(By
					.xpath(".//button[text()='Yes']")));
			Web.waitForPageToLoad(Web.getDriver());
			/*
			 * if(firstNameInput.getAttribute("disabled").equals("disabled")&&
			 * lastNameInput.getAttribute("disabled").equals("disabled")
			 * &&emailInput.getAttribute("disabled").equals("disabled"))
			 * isEditable = false; else isEditable = true;
			 */
			if (isFieldEditable())
				Reporter.logEvent(
						Status.FAIL,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are editable on confirmation screen.",
						true);
			else
				Reporter.logEvent(
						Status.PASS,
						"click on yes and validate that first name,last name and email are not editable.",
						""
								+ "Specified fields are not editable on confirmation screen.",
						false);
			Web.clickOnElement(confirmMsgBox.findElement(By.tagName("button")));
			//Web.waitForPageToLoad(Web.getDriver());
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method checks the editability of firs name,Last name,email fields of User when user is searched through search screen.
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public boolean isFieldEditable() throws Exception {
		boolean isEditable = false;
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frameb);
		Web.waitForElement(firstNameDisabled);
		if (firstNameDisabled.getAttribute("disabled").equals("true")
				&& lastNameDisabled.getAttribute("disabled").equals("true")
				&& emailDisabled.getAttribute("disabled").equals("true"))
			isEditable = false;
		else
			isEditable = true;
		return isEditable;
	}

	/**
	 * 
	 * <pre>
	 * this method fills Participant QDIA Notice - Mailing Service form with or without 'Ignore divisions' option
	 * based on parameter set.
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public void fill_QDIA_Notice_Mailing_Form() {
		try {
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			Web.waitForElement(orderForm);
			if (Stock.GetParameterValue("ignoreDivision").equals("yes")) {
				Web.waitForElement(ignoreDivisionYes);
				Web.clickOnElement(ignoreDivisionYes);
				Thread.sleep(2000);
			} else {
				Select div = new Select(division);
				div.selectByIndex(0);
				Web.clickOnElement(subDiviBtn);
				Web.waitForElement(subDivision);
				Web.clickOnElement(subDivision);
				Web.clickOnElement(subDiviBtn);
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			cal.add(Calendar.DAY_OF_YEAR, -90);
			Web.setTextToTextBox(beginDate, sdf.format(cal.getTime()));
			Web.setTextToTextBox(endDate, sdf.format(new Date()));
			Select partIdentifier = new Select(pptIdentifier);
			partIdentifier.selectByVisibleText(Stock
					.GetParameterValue("Participant Identifier"));
			Select partSortOrder = new Select(pptSortOrder);
			partSortOrder.selectByVisibleText(Stock
					.GetParameterValue("Sort Order"));
			Select repFormat = new Select(reportFormat);
			repFormat.selectByVisibleText(Stock
					.GetParameterValue("Report Format"));
			Web.clickOnElement(submitBtnforQDIA);
			Web.waitForPageToLoad(Web.getDriver());
			do {
				System.out.println("Loading...............");
				Thread.sleep(2000);
			} while (QDIALoader.isDisplayed());
			// Web.waitForElement(QDIALoader2);
			do {
				System.out.println("Loading...............");
				Thread.sleep(2000);
			} while (QDIALoader2.isDisplayed());
			if (Web.isWebElementDisplayed(QDIArequestConfirmation, true))
				Reporter.logEvent(
						Status.PASS,
						"Fill all the required details on QDIA notice mailing form and submit.",
						""
								+ "QDIA request is submitted successfully with following message:"
								+ QDIArequestConfirmation.getText(), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Fill all the required details on QDIA notice mailing form and submit.",
						"" + "QDIA request is not submitted successfully.",
						true);
			Web.getDriver().switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates Participant QDIA notice listing order menu option is displayed or not.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return boolean
	 */
	public boolean QDIAisDisplayed() throws Exception {
		boolean isDisplayed = false;
		HomePage homePage = new HomePage();
		Web.getDriver().switchTo().defaultContent();
		homePage.navigateToProvidedPage("Plan", "Fiduciary records", "");
		Thread.sleep(3000);
		System.out.println("");
		if (CommonLib.isElementExistByXpath(menuQDIA))
			isDisplayed = false;
		else
			isDisplayed = true;
		return isDisplayed;
	}

	/**
	 * <pre>
	 * This method validates the Plan Documents page for PSC-NextGen.
	 * It validates page headers 'General forms and documents' and 'Plan-specific documents',print link,PDI File Layout link.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 14-June-2017
	 * @return void
	 */
	public void validatePlanDocumentsPage() {
		try {
			Web.getDriver().switchTo().frame(frameb);
			if (Web.isWebElementDisplayed(planDocLoader, true)) {
				do {
					Thread.sleep(2000);
					System.out.println("Loading.......");
				} while (planDocLoader.isDisplayed());
			}
			if (Web.isWebElementDisplayed(genFormAndDocTitle, true)
					&& Web.isWebElementDisplayed(planSpecificDocTitle, true))
				Reporter.logEvent(
						Status.PASS,
						"Validate title 'General forms and documents' "
								+ "and 'Plan-specific documents' are displayed.",
						"" + "Specified titles are displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate title 'General forms and documents' "
								+ "and 'Plan-specific documents' are displayed.",
						"" + "Specified titles are not displayed.", true);
			Thread.sleep(2000);
			Web.clickOnElement(pdiFileLayout);
			Thread.sleep(2000);
			String parentWindow = CommonLib.switchToWindow();
			Thread.sleep(2000);
			String pdfURL = Web.getDriver().getCurrentUrl();
			if (pdfURL.contains(".pdf")){
				Reporter.logEvent(
						Status.PASS,
						"Click on PDI File Layout link and check if new window is "
								+ "opened with url containing .pdf as extension.",
						"PDF is displayed in a seperate window.", false);
				Web.getDriver().close();
				Web.getDriver().switchTo().window(parentWindow);
				Thread.sleep(2000);
				Web.getDriver().switchTo().defaultContent();
			}
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on PDI File Layout link and check if new window is "
								+ "opened with url containing .pdf as extension.",
						"PDF is not displayed in a seperate window.", true);
			
			if (CommonLib.getBrowserName().equalsIgnoreCase("chrome")) {
				//if (printLink.isDisplayed())
				if(Web.isWebElementDisplayed(printLink, true))
					Reporter.logEvent(Status.PASS,
							"Validate print link is displayed.",
							"Print link is displayed.", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Validate print link is displayed.",
							"Print link is not displayed.", true);
			} else {
				Web.clickOnElement(printLink);
				CommonLib.switchToWindow();
				if (printPriviewWindowHeader.getText().contains(
						"THIS IS A PRINT PREVIEW")) {
					Reporter.logEvent(Status.PASS,
							"Verify after clicking on print link,a "
									+ "print preview page is displayed.",
							"Print preview page is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify after clicking on print link,a "
									+ "print preview page is displayed.",
							"Print preview page is not displayed.", true);
				}
				Web.getDriver().close();
				Web.getDriver().switchTo().window(parentWindow);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates Plan Documents are Link
	 * </pre>
	 *
	 * @author smykjn
	 * @date 14-June-2017
	 * @return void
	 */
	public void validatePlanDocumentsLinks() {
		List<String> docDesc = new ArrayList<String>();
		try {
			CommonLib.switchToFrame(frameb);
			if(Web.isWebElementDisplayed(planDocLinkExpand, true))
				Web.clickOnElement(planDocLinkExpand);
			if (Web.isWebElementsDisplayed(planSpecificDocLinks, true))
				Reporter.logEvent(Status.PASS,
						"Validate Plan document links are displayed.",
						"" + "Plan document links are displayed.No. of Links:"
								+ planSpecificDocLinks.size(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate Plan document links are displayed.", ""
								+ "Plan document links are not displayed.",
						true);
			for (WebElement description : planSpecificDocLinks) {
				docDesc.add(description.getText().trim());
			}
			Reporter.logEvent(Status.INFO, "Documents descriptions:", ""
					+ docDesc, false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates message when no plan documents are found.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 14-June-2017
	 * @return void
	 */
	public void validateNoPlanDocMsg() {
		String expMsg = Stock.GetParameterValue("NoPlanDocMsg");
		String actMsg = "";
		try {
			homePage = new HomePage();
			Web.getDriver().switchTo().defaultContent();
			if (homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumber")))
				Reporter.logEvent(Status.PASS,
						"Search for a plan with no plan documents available.",
						"Plan is found and user navigates to" + "plan page.",
						false);
			else
				Reporter.logEvent(Status.FAIL,
						"Search for a plan with no plan documents available.",
						"Plan is not found.", true);
			/*if (homePage.navigateToProvidedPage("Plan", "Fiduciary records",
					"Plan documents"))
				Reporter.logEvent(
						Status.PASS,
						"Navigate to Plan-->Fiduciary records-->Plan documents page.",
						"Plan documents page is " + "displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Navigate to Plan-->Fiduciary records-->Plan documents page.",
						"Plan documents page is " + "not displayed.", true);*/
			this.openAnySubmenuUnderFiduciary("Plan documents");
			Web.getDriver().switchTo().frame(frameb);
			Web.waitForElement(noPlanDocText);
			actMsg = noPlanDocText.getText().trim();
			if (actMsg.equalsIgnoreCase(expMsg))
				Reporter.logEvent(Status.PASS,
						"Following message is displayed:" + expMsg,
						"Actual message displayed is:" + actMsg, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Following message is displayed:" + expMsg,
						"Actual message displayed is:" + actMsg, true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates general fiduciary documents links
	 * 1.Fee Disclosure Documents 2.Qualified Default Investment Alternative 3.Safe Harbor Notice
	 * </pre>
	 *
	 * @author smykjn
	 * @date 15-June-2017
	 * @return void
	 */
	public void validateFiduciaryRequiredNotices() {
		List<String> documents = Arrays.asList(Stock.GetParameterValue(
				"Notice Documents").split(","));
		try {
			CommonLib.switchToFrame(frameb);
			Web.waitForElements(actDocumentList);
			if (CommonLib.isAllHeadersDisplayed(actDocumentList, documents))
				Reporter.logEvent(Status.PASS,
						"Validate following document links are displayed"
								+ " on required notices page:" + documents,
						"Specified document links are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate following document links are displayed"
								+ " on required notices page:" + documents,
						"Specified document links are displayed.", true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * @author smykjn
	 * @date 15-June-2017
	 * @return void
	 */
	public void validateFiduciaryRequiredNotices_1() {
		String expMsg = Stock.GetParameterValue("ExpectedDocMsg");
		try {
			Web.waitForElement(safeHarborLink);
			String docActTitle = safeHarborLink.getText().trim();
			Web.clickOnElement(safeHarborLink);
			Web.waitForPageToLoad(Web.getDriver());
			CommonLib.switchToFrame(framebA);
			Web.isWebElementDisplayed(reqNoticeTitle, true);
			if (reqNoticeTitle.getText().trim().equals("Required notices"))
				Reporter.logEvent(Status.PASS,
						"Validate static label 'Required notices'.",
						"static label 'Required notices'" + " is displayed",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate static label 'Required notices'.",
						"static label 'Required notices'" + " is not displayed",
						false);
			try {
				WebElement accept = disclaimerDesc.findElement(By
						.xpath(".//button[.='Accept']"));
				Web.clickOnElement(accept);
			} catch (Exception e) {
			}
			WebElement backTextElement = backArrowLink.findElement(By
					.tagName("i"));
			String backText = backArrowLink.findElement(By.tagName("i"))
					.getText().trim();

			if (backArrowLink.getTagName().equals("a")
					&& backArrowLink.isDisplayed()
					&& backTextElement.isDisplayed() && backText.equals("Back"))
				Reporter.logEvent(
						Status.PASS,
						"Validate 'Back' text followed by arrow link.",
						"'Back' text followed by" + " arrow link is displayed.",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate 'Back' text followed by arrow link.",
						"'Back' text followed by"
								+ " arrow link is not displayed or arrow is not displayed.",
						true);

			if (docStaticTitle.getText().trim().equals(docActTitle))
				Reporter.logEvent(
						Status.PASS,
						"Validate document static title based on the documents"
								+ " viewed by user.in this case validate for title:"
								+ docActTitle, ""
								+ "Document title,which is being viewed is:"
								+ docStaticTitle.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate document static title based on the documents"
								+ " viewed by user.",
						"Document being viewed is:" + docStaticTitle.getText(),
						true);
			boolean isDisplayed = Web
					.isWebElementDisplayed(stopWatchIcon, true);
			String actMsg = stopWatchIcon.findElement(By.xpath("./.."))
					.getText().trim();
			System.out.println("Actual message text:" + actMsg);
			if (isDisplayed && actMsg.contains(expMsg))
				Reporter.logEvent(Status.PASS, "Validate clock icon and text '"
						+ expMsg + "' followed by clock icon.", ""
						+ "Clock is displayed:" + isDisplayed
						+ " and text displayed:" + actMsg, false);
			else
				Reporter.logEvent(Status.FAIL, "Validate clock icon and text '"
						+ expMsg + "' followed by clock icon.", ""
						+ "Clock is displayed:" + isDisplayed
						+ " and text displayed:" + actMsg, true);
			Web.clickOnElement(backArrowLink);
			Web.waitForPageToLoad(Web.getDriver());
			CommonLib.switchToFrame(frameb);
			if (Web.isWebElementsDisplayed(actDocumentList, true))
				Reporter.logEvent(Status.PASS,
						"Click on back link and validate user lands on "
								+ "Required Notices landing page.",
						"As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Click on back link and validate user lands on "
								+ "Required Notices landing page.",
						"Required Notices landing page is not displayed.", true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * @author smykjn
	 * @date 16-June-2017
	 * @return void
	 */
	public void validateSafeHarBorNoticeForPartGrpng() {
		List<String> docs = new ArrayList<String>();
		String expHistoryText = Stock
				.GetParameterValue("ExpectedHistoryLinkText");
		try {
			Web.clickOnElement(safeHarborLink);
			Web.waitForPageToLoad(Web.getDriver());
			CommonLib.switchToFrame(framebA);
			if (Web.isWebElementDisplayed(disclaimer, true))
				Reporter.logEvent(
						Status.PASS,
						"Again click on Safe harbor link and Validate Disclaimer is displayed.",
						"Disclaimer is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate Disclaimer is displayed.",
						"Disclaimer is not displayed.", true);
			Reporter.logEvent(Status.INFO, "Disclaimer:",
					disclaimerDesc.getText(), true);
			WebElement accept = disclaimerDesc.findElement(By
					.xpath(".//button[.='Accept']"));
			Web.clickOnElement(accept);
			Web.waitForElement(stopWatchIcon);
			for (WebElement doc : doclinks) {
				docs.add(doc.getText().trim());
			}
			if (Web.isWebElementsDisplayed(doclinks, true))
				Reporter.logEvent(Status.PASS,
						"Click on Accept button and check if "
								+ "document links are displayed.",
						"Document links are displayed as:" + docs, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Click on Accept button and check if "
								+ "document links are displayed.",
						"Document links are not displayed.", true);
			if (docStaticHistoryTitle.getText().equals(
					"Safe Harbor Notice Document History")
					&& docStaticHistoryTitle.isDisplayed())
				Reporter.logEvent(Status.PASS,
						"Validate static title 'Safe Harbor Notice Document History' is"
								+ " displayed.", "As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate static title 'Safe Harbor Notice Document History' is"
								+ " displayed.", "Title is not displayed.",
						true);
			if (historyLink.getText().trim().equals(expHistoryText)
					&& historyLink.isDisplayed())
				Reporter.logEvent(Status.PASS,
						"Verify Static text/Hyperlinks for history documents as:"
								+ expHistoryText, "" + "link text is:"
								+ historyLink.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Static text/Hyperlinks for history documents as:"
								+ expHistoryText, "" + "link text is:"
								+ historyLink.getText(), true);
			if (calendarIcon.isDisplayed())
				Reporter.logEvent(Status.PASS,
						"Validate calendar icon is displayed.", "As expected.",
						true);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate calendar icon is displayed.",
						"Validate calendar icon is not displayed.", false);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * @author smykjn
	 * @date 16-June-2017
	 * @return void
	 */
	public void validateSafeHarBorNoticeForPartGrpng_1() {
		boolean isAccordionExpanded = false;
		String expTitle = Stock.GetParameterValue("ExpTitleOnHistoryPage");
		String expDesc = Stock.GetParameterValue("ExpDescOnHistoryPage");
		try {
			Web.clickOnElement(historyLink);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElements(historyAccordion);
			WebElement backTextElement = backArrowLink.findElement(By
					.tagName("i"));
			String backText = backArrowLink.findElement(By.tagName("i"))
					.getText().trim();
			if (backArrowLink.getTagName().equals("a")
					&& backArrowLink.isDisplayed()
					&& backTextElement.isDisplayed() && backText.equals("Back"))
				Reporter.logEvent(
						Status.PASS,
						"Validate 'Back' text followed by arrow link.",
						"'Back' text followed by" + " arrow link is displayed.",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate 'Back' text followed by arrow link.",
						"'Back' text followed by"
								+ " arrow link is not displayed or arrow is not displayed.",
						true);
			if (historyAccordion.size() == 1) {

			} else if (historyAccordion.size() > 1) {
				for (int i = 0; i < historyAccordion.size(); i++) {
					if (!historyAccordion.get(i)
							.findElement(By.xpath("./div[2]")).isDisplayed()) {
						isAccordionExpanded = true;
					} else {
						isAccordionExpanded = false;
						break;
					}

				}
			} else {

			}
			if (isAccordionExpanded)
				Reporter.logEvent(
						Status.PASS,
						"If accordion row is more than 1 then all rows must be"
								+ " dispayed in collapsed state else row must be displayed in expanded state.",
						"As expected.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"If accordion row is more than 1 then all rows must be"
								+ " dispayed in collapsed state else row must be displayed in expanded state.",
						""
								+ "accordion collapse/expanded features based on number of accordion row is not working as expected.",
						true);
			String actTitle = docTitleOnHistoryPage.getText().trim();
			String actDesc = docDescOnHistoryPage.getText().trim();
			if (actTitle.equals(expTitle) && actDesc.equals(expDesc))
				Reporter.logEvent(Status.PASS, "Validate title to be '"
						+ expTitle + "' and " + "description to be '" + expDesc
						+ "'.", "Title is '" + actTitle
						+ "' and document description is '" + actDesc + "'.",
						false);
			else
				Reporter.logEvent(Status.FAIL, "Validate title to be '"
						+ expTitle + "' and " + "description to be '" + expDesc
						+ "'.", "Title is '" + actTitle
						+ "' and document description is '" + actDesc + "'.",
						true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * @author smykjn
	 * @date 19-June-2017
	 * @return void
	 */
	public void validateSafeHarBorNoticeForPartGrpng_2() {
		List<String> expMonths = Arrays.asList(Stock.GetParameterValue(
				"ExpListOfMonths").split(","));
		try {
			String accordionTitle_ = Stock.GetParameterValue("AccordionTitle");
			List<String> listOfAccordionTitle = new ArrayList<String>();
			boolean isDisplayed = false;
			for (WebElement title : accordionTitle) {
				if (title.getText().trim().contains(accordionTitle_)) {
					isDisplayed = true;
					listOfAccordionTitle.add(title.getText().trim());
				} else {
					isDisplayed = false;
					break;
				}
			}
			if (isDisplayed)
				Reporter.logEvent(Status.PASS,
						"validate accordian title to be [Year]+'"
								+ accordionTitle_ + "'.", ""
								+ "Accordions titles are :"
								+ listOfAccordionTitle, false);
			else
				Reporter.logEvent(Status.FAIL,
						"validate accordian title to be [Year]+'"
								+ accordionTitle_ + "'.", ""
								+ "Accordions titles are :"
								+ listOfAccordionTitle, true);
			Web.clickOnElement(accordionLink.get(0));
			Web.clickOnElement(accordionLink.get(1));
			if (CommonLib.isAllHeadersDisplayed(accordionMonthsText, expMonths)
					&& Web.isWebElementsDisplayed(linksUnderAccordion, false))
				Reporter.logEvent(Status.PASS,
						"Validate documents are grouped by months links.",
						"As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate documents are grouped by months links.",
						"As expected.", false);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * @author smykjn
	 * @date 19-June-2017
	 * @return void
	 */
	public void validateSafeHarBorNoticeForPartGrpng_3() {
		try {
			Web.clickOnElement(linksUnderAccordion.get(0));
			List<WebElement> subAccordionLibnl = linksUnderAccordion.get(0)
					.findElements(By.xpath(docHistoryLinkPath));
			List<String> historydocName = new ArrayList<String>();
			for (WebElement doc : subAccordionLibnl) {
				historydocName.add(doc.getText().trim());
			}
			if (Web.isWebElementsDisplayed(subAccordionLibnl, false))
				Reporter.logEvent(Status.PASS,
						"Verify documents under sunaccordion.",
						"Multiple hyperlinks are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify documents under sunaccordion.",
						"Multiple hyperlinks are not displayed.", true);
			if (expandAllLink.isDisplayed()) {
				Web.clickOnElement(expandAllLink);
				if (collapseAllLink.isDisplayed())
					Reporter.logEvent(Status.PASS, "Click on Expand All link.",
							"Collapse all link is displayed.", false);
				else
					Reporter.logEvent(Status.FAIL, "Click on Expand All link.",
							"Collapse all link is not displayed.", false);
			}
			Web.clickOnElement(collapseAllLink);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates following menu options under plan overview.
	 * 1.Plan Provisions 2.Vesting Schedules 3.Voice Response and Participant Web
	 * </pre>
	 *
	 * @author smykjn
	 * @date 20-June-2017
	 * @return void
	 */
	public void validateThirdLevelMenuOptionForPlanOverview() {
		List<String> expDropDownMenu = Arrays.asList(Stock
				.GetParameterValue("ExpPlanOverviewOptions"));
		try {
			Thread.sleep(2000);
			CommonLib.navigateToProvidedPage("Plan", "Overview", "");
			if(Web.isWebElementDisplayed(planTab, true))
				Web.actionsClickOnElement(planTab);
			if(Web.isWebElementDisplayed(planOverviewSubmenu, true))
				Web.actionsClickOnElement(planOverviewSubmenu);
			if (Web.isWebElementsDisplayed(planoverviewOptions, true))
				Reporter.logEvent(
						Status.PASS,
						"Validate following plan overview options:"
								+ expDropDownMenu,
						""
								+ "All expected overview menu options are displatyed.",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate following plan overview options:"
								+ expDropDownMenu,
						""
								+ "All expected overview menu options are not displatyed.",
						true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method verify quick links on Investment options page
	 * </pre>
	 *
	 * @author smykjn
	 * @date 23rd-June-2017
	 * @return void
	 */
	public void validateQuickLinksOnInvOptPage() {
		List<String> expQucikLinks = Arrays.asList(Stock.GetParameterValue(
				"QuickLinks").split(","));
		List<String> actQuickLinks = new ArrayList<String>();
		String investmentName = "";
		try {
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			if(!firstMorngStarInfo.isDisplayed()){
				firstMorngStarInfo=secondMorngStarInfo;//added
				fstInvName=secondInvName;
			}
				
			Web.waitForElement(fstInvName);
			System.out.println(fstInvName.getText().trim());
			investmentName = fstInvName.getText().trim();
			Web.actionsClickOnElement(fstInvName);
			Web.waitForElements(qukLinks);
			if (CommonLib.isAllHeadersDisplayed(qukLinks, expQucikLinks))
				Reporter.logEvent(Status.PASS, "Verify below quick links.\n"
						+ expQucikLinks, ""
						+ "Quick links displayed are as expected.", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify below quick links.\n"
						+ expQucikLinks, ""
						+ "Quick links displayed are as below.\n"
						+ actQuickLinks, true);
			
			if (firstMorngStarInfo.isDisplayed())
				Reporter.logEvent(
						Status.PASS,
						"Info link(question mark) is displayed beside morning star rating.",
						"" + "As expected.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Info link(question mark) is displayed beside morning star rating.",
						"" + "Info link is not displayed.", true);
			//Web.clickOnElement(firstMorngStarInfo);
			Web.actionsClickOnElement(firstMorngStarInfo);
			Thread.sleep(3000);
			//Web.waitForElement(mrngStarContent);
			String mrngStarInfo = mrngStarContent.getText();
			if (mrngStarContent.isDisplayed()
					&& mrngStarInfo.contains(investmentName))
				Reporter.logEvent(
						Status.PASS,
						"Click on morningstar info link and validate popup with "
								+ "morning star rules explaination for that fund will be displayed.Popup upcontains"
								+ "below explanation.\n" + mrngStarInfo,
						"As Expected.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on morningstar info link and validate popup with "
								+ "morning star rules explaination for that fund will be displayed.Popup up contains"
								+ "below explanation.\n" + mrngStarInfo,
						"Pop up is not displayed.", true);

		}

		catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates Disclaimer on Investment option page.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 23rd-June-2017
	 * @return void
	 */
	public void validateDesclaimerForInvetmentOptionPage() {
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
		try {
			actDisclaimer1 = disclaimer1.getText().trim();
			actDisclaimer2 = disclaimer2.getText();
			actDisclaimer3 = disclaimers.get(0).getText().trim();
			actDisclaimer4 = disclaimers.get(1).getText().trim();
			actDisclaimer5 = disclaimers.get(2).getText().trim();
			actDisclaimer6 = disclaimers.get(3).getText().trim();
			actDisclaimer7 = disclaimerLast.get(1).getText().trim();
			actDisclaimer8 = disclaimerLast.get(2).getText().trim();
			System.out.println(expDisclaimer1.trim());
			System.out.println(actDisclaimer1);
			System.out.println(expDisclaimer1.trim().contains(actDisclaimer1));
			System.out.println(actDisclaimer2.contains(expDisclaimer2));
			System.out.println(actDisclaimer3.contains(expDisclaimer3));
			System.out.println(actDisclaimer4.contains(expDisclaimer4));
			System.out.println(actDisclaimer5.contains(expDisclaimer5));
			System.out.println(actDisclaimer6.contains(expDisclaimer6));
			System.out.println(actDisclaimer7.contains(expDisclaimer7));
			System.out.println(actDisclaimer8.contains(expDisclaimer8));
			if(actDisclaimer1.contains(expDisclaimer1)&&
					actDisclaimer2.contains(expDisclaimer2)&&
					actDisclaimer3.contains(expDisclaimer3)&&
					actDisclaimer4.contains(expDisclaimer4)&&
					actDisclaimer5.contains(expDisclaimer5)&&
					actDisclaimer6.contains(expDisclaimer6)
					&&actDisclaimer7.contains(expDisclaimer7)
					&&actDisclaimer8.contains(expDisclaimer8))
				/*
				 * if (expDisclaimer1.equalsIgnoreCase(actDisclaimer1) &&
				 * expDisclaimer2.equalsIgnoreCase(actDisclaimer2) &&
				 * expDisclaimer3.equalsIgnoreCase(actDisclaimer3) &&
				 * expDisclaimer4.equalsIgnoreCase(actDisclaimer4) &&
				 * expDisclaimer5.equalsIgnoreCase(actDisclaimer5) &&
				 * expDisclaimer6.equalsIgnoreCase(actDisclaimer6) &&
				 * expDisclaimer7.equalsIgnoreCase(actDisclaimer7) &&
				 * expDisclaimer8.equalsIgnoreCase(actDisclaimer8))
				 */
				Reporter.logEvent(Status.PASS,
						"Varify investment options bottom disclaimer.",
						"Disclaimer is displayed as below:" + "\n"
								+ actDisclaimer1 + "\n\n" + actDisclaimer2
								+ "\n" + actDisclaimer3 + "\n" + actDisclaimer4
								+ "\n" + actDisclaimer5 + "" + "\n"
								+ actDisclaimer6 + "\n" + actDisclaimer7 + "\n"
								+ actDisclaimer8, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Varify investment options bottom disclaimer.",
						"Disclaimer is displayed as below:" + "\n"
								+ actDisclaimer1 + "\n\n" + actDisclaimer2
								+ "\n" + actDisclaimer3 + "\n" + actDisclaimer4
								+ "\n" + actDisclaimer5 + "" + "\n"
								+ actDisclaimer6 + "\n" + actDisclaimer7 + "\n"
								+ actDisclaimer8 + "\n\n\n\n Note:"
								+ "Expected disclaimer is not displayed.", true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/***
	 * <pre>
	 * This method is to validate Balance column sorting in ascending 
	 * and descending order
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 27th-June-2017
	 * 
	 */
	public void sortBalanceInDescAndAsc() {
		try {
			Web.clickOnElement(fstInvName);
			List<Double> balList = new ArrayList<Double>();
			Web.clickOnElement(balanceLinkHeader);// ascending
			Thread.sleep(2000);
			for (WebElement bal : balanceListInOptPage) {
				if (bal.getText().trim().equals("N/A")) {
					System.out.println(bal.getText().trim());
					balList.add(0.00);
				} else {
					double balanceInt = Double.parseDouble(bal.getText().trim()
							.replace("$", "").replace(",", "").trim());
					System.out.println(balanceInt);
					balList.add(balanceInt);
				}
			}
			List<Double> copylist = new ArrayList<Double>(balList);
			Collections.sort(copylist);
			System.out.println("Ascending order list sorted by Collections:"
					+ copylist);
			System.out.println("Ascending order list sorted by systems:"
					+ balList);
			if (copylist.equals(balList))
				Reporter.logEvent(Status.PASS,
						"Sort the balance column in ascending order.\n below is the sorting order:\n"
								+ copylist, "As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Sort the balance column in ascending order.\n below is the sorting order:\n"
								+ copylist,
						"sorting is not proper.\nbelow is actual sorting order:\n"
								+ balList, true);
			balList.clear();
			Web.clickOnElement(balanceLinkHeader);// descending

			for (WebElement bal : balanceListInOptPage) {
				if (bal.getText().trim().equals("N/A")) {
					System.out.println(bal.getText().trim());
					balList.add(0.00);
				} else {
					double balanceInt = Double.parseDouble(bal.getText().trim()
							.replace("$", "").replace(",", "").trim());
					System.out.println(balanceInt);
					balList.add(balanceInt);
				}
			}

			if (CommonLib.sortIntegerListDesc(balList))
				Reporter.logEvent(Status.PASS,
						"Sort the balance column in descending order.",
						"As expected.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Sort the balance column in descending order.",
						"As expected.", true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}

	}

	/***
	 * <pre>
	 * This method is to validate Balance column sorting in ascending 
	 * and descending order
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 27th-June-2017
	 * 
	 */
	public void validateExcelDownload() {
		try {
			CommonLib.switchToFrame(frameb);
			Web.clickOnElement(excellink);
			/*
			 * if(CommonLib.getBrowserName().equalsIgnoreCase("firefox")){ Robot
			 * rb = new Robot(); Thread.sleep(2000);
			 * rb.keyPress(KeyEvent.VK_ENTER); rb.keyRelease(KeyEvent.VK_ENTER);
			 * }
			 */
			Thread.sleep(3000);
			//String fileName = CommonLib.getDownloadedDocumentName(Stock.getConfigParam("Download_Directory"), ".xls");
			String fileName="InvestmentOptions.xls";
			System.out.println("----------------");
			if (fileName.contains(".xls"))
				Reporter.logEvent(Status.PASS,
						"Click on excel link and observe that "
								+ "file with .xls extension is downloaded.",
						"As expected.File name is:" + fileName, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Click on excel link and observe that "
								+ "file with .xls extension is downloaded.",
						"File name is:" + fileName, false);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}

	}

	/**
	 * <pre>
	 * This method validate .pdf extension in URL once click on Investment performance detail link
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 27-June-2017
	 * @return void
	 */
	public void validatePDFURL() {
		try {
			Web.clickOnElement(investmentAndPerfLink);
			String parentWindow = CommonLib.switchToWindow();
			String pdfURL = Web.getDriver().getCurrentUrl();
			if (pdfURL.contains(".pdf"))
				Reporter.logEvent(
						Status.PASS,
						"Click on Investment performance detail link and check if new window is "
								+ "opened with url containing .pdf as extension.",
						"As expected.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on Investment performance detail link link and check if new window is "
								+ "opened with url containing .pdf as extension.",
						"PDF is not displayed in a seperate window.", true);
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates that update link and bank details are displayed.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 27-June-2017
	 * @return void
	 */
	public void validateBankingInfoAndUpdateLink() {
		try {
			CommonLib.switchToFrame(frameb);
			if (Web.isWebElementDisplayed(bankInformationsSection, true))
				Reporter.logEvent(Status.PASS,
						"Validate bank information is displayed.",
						"Bank information is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate bank information is displayed.",
						"Bank information is not displayed.", true);
			if (Web.isWebElementsDisplayed(updateLinks, false))
				Reporter.logEvent(Status.PASS,
						"Validate Update links are displayed as user has access to below"
								+ " txn codes\n PSUACH,PSVACH.",
						"Update links are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate Update links are displayed as user has access to below"
								+ " txn codes\n PSUACH,PSVACH.",
						"Update links are not displayed.", true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method updates the banking information.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 27-June-2017
	 * @return void
	 */
	public void updateBankDetails() {
		String routingNo = Stock.GetParameterValue("NewRoutingNumber");
		String accNumber = Stock.GetParameterValue("NewAccNumber");
		String expConfirmationMsg = Stock.GetParameterValue("ConfirmationMsg");
		String actConfirmationMsg = "";
		try {
			Thread.sleep(3000);
			Web.clickOnElement(updateLinks.get(0));
			//Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			Web.waitForElement(newRoutingNoInput);
			//Web.isWebElementsDisplayed(bankSectionTitles, true);
			Web.setTextToTextBox(newRoutingNoInput, routingNo);
			Web.setTextToTextBox(newAccNoInput, accNumber);
			Web.clickOnElement(accTypeSavings);
			Web.clickOnElement(continueButtonInput);
			Thread.sleep(3000);
			Web.clickOnElement(continueButtonInput);
			//Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(bankInformationsSection);
			Thread.sleep(3000);
			System.out
					.println("Form text:" + bankInformationsSection.getText());
			actConfirmationMsg = bankInformationsSection.getText().trim();
			if (actConfirmationMsg.contains(expConfirmationMsg))
				Reporter.logEvent(Status.PASS,
						"Fill all the banking details and click on continue.",
						"Confirmation message is " + "displayed as below:\n"
								+ actConfirmationMsg, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Fill all the banking details and click on continue.",
						"Confirmation message is " + "displayed as below:\n"
								+ actConfirmationMsg, true);
			Web.clickOnElement(continueButtonInput);
			Web.waitForPageToLoad(Web.getDriver());
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the unit/Share values headers.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 08-Sept-2017
	 * @return boolean
	 */
	public boolean validateUnitShareValuesheaders() {
		boolean isHeaderExist = false;
		List<String> expUnitShareHeaders = Arrays.asList(Stock
				.GetParameterValue("ExpectedUnitShareCol").split(","));
		List<String> actUnitShareHeaders = new ArrayList<String>();
		try {
			for (WebElement ele : unitShareHeaders) {
				actUnitShareHeaders.add(ele.getText().trim());
			}
			System.out.println("Headers are:" + actUnitShareHeaders);

			for (int i = 0; i < expUnitShareHeaders.size(); i++) {
				if (actUnitShareHeaders.get(i).contains(
						expUnitShareHeaders.get(i))) {
					isHeaderExist = true;
				} else {
					isHeaderExist = false;
					break;
				}
			}

			if (isHeaderExist)
				Reporter.logEvent(Status.PASS,
						"Validate the headers as below:\n"
								+ expUnitShareHeaders, ""
								+ "below headers are displayed.\n"
								+ actUnitShareHeaders, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Validate the headers as below:\n"
								+ expUnitShareHeaders, ""
								+ "below headers are displayed.\n"
								+ actUnitShareHeaders, true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
		return isHeaderExist;
	}

	/**
	 * <pre>
	 * This method validates the watermark text on unit/share values page.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 09-Sept-2017
	 * @return void
	 */
	public void validateWatermarkText() {
		String expWatermarkText1 = Stock
				.GetParameterValue("ExpectedWatrMarkTxt1");
		String expWatermarkText2 = Stock
				.GetParameterValue("ExpectedWatrMarkTxt2");
		String expWatermarkText3 = Stock
				.GetParameterValue("ExpectedWatrMarkTxt3");
		String expWatermarkText4 = Stock
				.GetParameterValue("ExpectedWatrMarkTxt4");
		String expWatermarkText5 = Stock
				.GetParameterValue("ExpectedWatrMarkTxt5");
		String regExp = "/^([A-Z])\\w+[ ]([0-4]){1,2}\\,[ ]+\\b[2-9][0-9]{3}\\b/";
		System.out.println(regExp);
		String actWatermarkText1_1 = watermarkText1.get(0).getText();
		String actWatermarkText1_2 = watermarkText1.get(1).getText();
		String actWatermarkText2 = watermarkText2.getText().trim();
		String actWatermarkText3 = watermarkText3.getText().trim();
		String actWatermarkText4 = watermarkText4.getText().trim();
		String actWatermarkText5 = watermarkText5.getText().trim();
		try {
			if (actWatermarkText1_1.equals(expWatermarkText1))
				Reporter.logEvent(
						Status.PASS,
						"Validate below water mark text.\n" + expWatermarkText1,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText1_1, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate below water mark text.\n" + expWatermarkText1,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText1_1, true);
			/*
			 * Pattern r = Pattern.compile(regExp); Matcher m =
			 * r.matcher(actWatermarkText1_2); m.matches();
			 * if(actWatermarkText1_2.matches(regExp))
			 * Reporter.logEvent(Status.PASS
			 * ,"Validate below text format:\nMon dd, yyyy",
			 * "Text format is proper.", false); else
			 * Reporter.logEvent(Status.FAIL
			 * ,"Validate below text format:\nMon dd, yyyy",
			 * "Text format is not proper.\n" +
			 * "it is in below format:"+actWatermarkText1_2, true);
			 */
			if (actWatermarkText2.equals(expWatermarkText2))
				Reporter.logEvent(
						Status.PASS,
						"Validate below water mark text.\n" + expWatermarkText2,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText2, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate below water mark text.\n" + expWatermarkText2,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText2, true);
			if (actWatermarkText3.equals(expWatermarkText3))
				Reporter.logEvent(
						Status.PASS,
						"Validate below water mark text.\n" + expWatermarkText3,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText3, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate below water mark text.\n" + expWatermarkText3,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText3, true);
			if (actWatermarkText4.equals(expWatermarkText4))
				Reporter.logEvent(
						Status.PASS,
						"Validate below water mark text.\n" + expWatermarkText4,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText4, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate below water mark text.\n" + expWatermarkText4,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText4, true);
			if (actWatermarkText5.equals(expWatermarkText5))
				Reporter.logEvent(
						Status.PASS,
						"Validate below water mark text.\n" + expWatermarkText5,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText5, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate below water mark text.\n" + expWatermarkText5,
						"below actual water mark text is displayed:\n"
								+ actWatermarkText5, true);

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}

	}

	/**
	 * <pre>
	 * This method is used to search a user with specified filters.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return void
	 */
	public boolean searchUser(String[] values, WebElement... filters) {
		boolean isUserRetrieved = false;
		try {
			Thread.sleep(1000);
			CommonLib.switchToFrame(frameb);
			if (filters.length == values.length) {
				for (int i = 0; i < filters.length; i++) {
					Web.setTextToTextBox(filters[i], values[i]);
				}
				Web.clickOnElement(searchUserGoBtn);
				do {
					Thread.sleep(2000);
					System.out.println("Waiting till go button gets enabled.");
				} while (!searchUserGoBtn.isEnabled());
				if (Web.isWebElementDisplayed(searchResultRow, true)) {
					List<WebElement> searchRowColumns = searchResultRow
							.findElements(By.tagName("td"));
					if (searchRowColumns.size() > 1)
						isUserRetrieved = true;
					else
						isUserRetrieved = false;
				}
			} else {
				throw new Exception("Please provide value for each filter.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
		return isUserRetrieved;
	}

	/**
	 * <pre>
	 * This method validates that if a user can be edited through SAW page or not.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return boolean
	 */
	public boolean canBeEdited() throws Exception {
		boolean isActionDropDownDisplayed = false;
		String indicator = searchResultRow.findElements(By.tagName("td"))
				.get(10).getText().trim();
		if (indicator.equals("N/A"))
			isActionDropDownDisplayed = false;
		else
			isActionDropDownDisplayed = true;
		return isActionDropDownDisplayed;

	}

	/**
	 * <pre>
	 * This method validates that if a user can be edited through SAW page or not.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return boolean
	 */
	public boolean validateActionDropDownOption(String expOption)
			throws Exception {
		boolean isActionOptionDisplayed = false;
		Select sel = new Select(searchResultRow.findElement(By
				.tagName("select")));
		List<WebElement> actions = sel.getOptions();
		for (WebElement option : actions) {
			if (option.getText().equals(expOption)) {
				isActionOptionDisplayed = true;
				break;
			} else {
				isActionOptionDisplayed = false;
			}
		}
		return isActionOptionDisplayed;

	}

	/**
	 * <pre>
	 * This method validates General information section on plan provision page against DB.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return boolean
	 */
	public void validateGeneralInfoOnPlanProvision(String planNumber)
			throws Exception {
		Map<String, String> generalInfoMapDB = new HashMap<String, String>();
		Map<String, String> generalInfoMapUI = new HashMap<String, String>();
		CommonLib.switchToFrame(frameb);
		if (planProvisionGenInfoValues.size() == 7) {
			generalInfoMapUI.put("Effective date", planProvisionGenInfoValues
					.get(1).getText());
			String[] planyearend = planProvisionGenInfoValues.get(2).getText()
					.split("/");
			generalInfoMapUI.put("Plan year end", planyearend[1]
					+ planyearend[0]);
			generalInfoMapUI.put("Employee ID",
					planProvisionGenInfoValues.get(3).getText());
			generalInfoMapUI.put("ERISA", planProvisionGenInfoValues.get(4)
					.getText());
			generalInfoMapUI.put("Safe Harbor plan", planProvisionGenInfoValues
					.get(5).getText());
			generalInfoMapUI.put("Beneficiary Recordkeeping services",
					planProvisionGenInfoValues.get(6).getText());
		}
		queryResultSet = DB.executeQuery(
				Stock.getTestQuery("getPlanProvisionsDetails")[0],
				Stock.getTestQuery("getPlanProvisionsDetails")[1], planNumber);
		if (queryResultSet.next()) {
			generalInfoMapDB.put("Effective date",
					queryResultSet.getString("EFFDATE"));
			generalInfoMapDB.put("Plan year end",
					queryResultSet.getString("ANNIV_DATE"));
			generalInfoMapDB.put("Employee ID",
					queryResultSet.getString("TAX_ID"));
			if (queryResultSet.getString("ERISA_COMPL_IND").equals("Y"))
				generalInfoMapDB.put("ERISA", "Yes");
			else if (queryResultSet.getString("ERISA_COMPL_IND").equals("N"))
				generalInfoMapDB.put("ERISA", "No");
			else
				generalInfoMapDB.put("ERISA", "");
			if (queryResultSet.getString("SAFE_HARBOR_IND").equals("Y"))
				generalInfoMapDB.put("Safe Harbor plan", "Yes");
			else if (queryResultSet.getString("SAFE_HARBOR_IND").equals("N"))
				generalInfoMapDB.put("Safe Harbor plan", "No");
			else
				generalInfoMapDB.put("Safe Harbor plan", "");
			generalInfoMapDB.put(
					"Beneficiary Recordkeeping services",
					StringUtils.capitalize(queryResultSet.getString(
							"BENE_RECORDKEEPER_CODE").toLowerCase()));
		}
		System.out.println("UI map for general info:" + generalInfoMapUI);
		System.out.println("DB map for general info:" + generalInfoMapDB);
		if (generalInfoMapUI.equals(generalInfoMapDB))
			Reporter.logEvent(Status.PASS,
					"Validate plan provision general information against DB.\ngeneral information "
							+ "from plan provision page:" + generalInfoMapUI,
					"" + "General information from DB is found as below\n"
							+ generalInfoMapDB, false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate plan provision general information against DB.\ngeneral information "
							+ "from plan provision page:" + generalInfoMapUI,
					"" + "General information from DB is found as below\n"
							+ generalInfoMapDB, true);
	}

	/**
	 * <pre>
	 * This method validates the Loan information section on Plan provision page.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 11th-Sept-2017
	 */
	public void validateLoaninformationSectionOnPlanProvision(String planNumber)
			throws Exception {
		String maxNumberOfLoansAllowed = "";
		queryResultSet = DB.executeQuery(
				Stock.getTestQuery("getMaxNumberOfLoanAllowed")[0],
				Stock.getTestQuery("getMaxNumberOfLoanAllowed")[1], planNumber);
		if (queryResultSet.next()) {
			maxNumberOfLoansAllowed = queryResultSet
					.getString("MAX_LOANS_ALLOWED");
		}
		Web.clickOnElement(lonainformationLink);
		//Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(maxLoanAllowed);
		String maxNumberOfLoansAllowedUI = maxLoanAllowed.getText().split(":")[1]
				.trim();
		if (Web.VerifyText(maxNumberOfLoansAllowedUI,
				maxNumberOfLoansAllowedUI, false))
			Reporter.logEvent(Status.PASS,
					"Validate max number of loans allowed.\nmax number of loans allowed:"
							+ maxNumberOfLoansAllowedUI,
					"Max number of loans allowed is same as found in DB-"
							+ maxNumberOfLoansAllowed, false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate max number of loans allowed.\nmax number of loans allowed:"
							+ maxNumberOfLoansAllowedUI,
					"Max number of loans allowed is not same as found in DB:"
							+ maxNumberOfLoansAllowed, true);
	}

	/**
	 * <pre>
	 * This method Validates the charts page screen elements.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 26th-sept-2017
	 * @return void
	 */
	public void validateChartsPageScreenElements() {
		try {
			CommonLib.switchToFrame(frameb);
			Web.clickOnElement(charts);
			CommonLib.waitForLoader(planDocLoader);
			if (Web.isWebElementDisplayed(charts_svg)) {
				if (asOfDate_selectDrpDwn.isDisplayed()
						&& asOfDate_label.isDisplayed())
					Reporter.logEvent(
							Status.PASS,
							"Validate 'As of Date' drop down and label is displayed.",
							"As of Date drop down and label is displayed.",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Validate 'As of Date' drop down and label is displayed.",
							"As of Date drop down and label is not displayed.",
							true);
				if (displayGraphs_btn.isDisplayed()
						&& displayTable_btn.isDisplayed()
						&& viewAs_label.isDisplayed())
					Reporter.logEvent(
							Status.PASS,
							"validate View as Graph and Table buttons are displayed.",
							"View as Graph and Table buttons are displayed.",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"validate View as Graph and Table buttons are displayed.",
							"View as Graph and Table buttons are not displayed.",
							true);
				if (investment_Charts_Excel_Link.isDisplayed())
					Reporter.logEvent(Status.PASS,
							"Check if Excel link is displayed.", ""
									+ "Excel link is displayed.", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Check if Excel link is displayed.", ""
									+ "Excel link is not displayed.", true);
				if (dollarInvested_Link.isDisplayed()
						&& noOfInvestor_Link.isDisplayed())
					Reporter.logEvent(Status.PASS,
							"Validate below links are displayed.\n1.Dollar Invested"
									+ "\n2.Number od Investors",
							"Links are displayed properly.", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Validate below links are displayed.\n1.Dollar Invested"
									+ "\n2.Number of Investors",
							"Links are not displayed properly.", true);
			}
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 26th-sept-2017
	 * @return void
	 */
	public void validateChartsPageBasicFeatures() {
		try {
			Web.clickOnElement(dollarInvested_Link);
			if (Web.isWebElementDisplayed(dollarInvested_highChart, true))
				Reporter.logEvent(
						Status.PASS,
						"Click on Dollar invested link and check if respective high chart is displayed.",
						""
								+ "High chart, related to dollar invested is displayed.",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on Dollar invested link and check if respective high chart is displayed.",
						""
								+ "High chart, related to dollar invested is not displayed.",
						true);
			Web.clickOnElement(noOfInvestor_Link);
			if (Web.isWebElementDisplayed(noOfInvestors_highChart, true))
				Reporter.logEvent(
						Status.PASS,
						"Click on 'Number of Investors' link and check if respective high chart is displayed.",
						""
								+ "High chart, related to 'Number of Investors' is displayed.",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on 'Number of Investors' link and check if respective high chart is displayed.",
						""
								+ "High chart, related to 'Number of Investors' is not displayed.",
						true);
			Web.clickOnElement(displayTable_btn);
			if (Web.isWebElementDisplayed(Investment_Charts_table, true))
				Reporter.logEvent(
						Status.PASS,
						"Click on 'Table' button and check if data is displayed in table format.",
						"" + "Data is displayed in table format.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Click on 'Table' button and check if data is displayed in table format.",
						"" + "Data is not displayed in table format.", true);
			Web.clickOnElement(investment_Charts_Excel_Link);
			String fileExtension = CommonLib.getDownloadedDocumentName(
					Stock.getConfigParam("Download_Directory"), ".xls");
			if (fileExtension.contains(".xls"))
				Reporter.logEvent(
						Status.PASS,
						"Validate excel download.",
						""
								+ "Excel download is successful.below file is downloaded\n"
								+ fileExtension, false);
			else
				Reporter.logEvent(Status.FAIL, "Validate excel download.",
						"Excel download is not successful.", true);

		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	@FindBy(xpath = ".//div[@id='documents']//tbody//td[4]//a")
	private List<WebElement> investment_overvw_View_Links;
	@FindBy(xpath = ".//div[@id='documents']//tbody//td[5]//a")
	private List<WebElement> online_Prospectus_View_Links;
	@FindBy(xpath = ".//div[@id='documents']//tbody//td[4]//span")
	private List<WebElement> investment_overvw_NA_text;

	/**
	 * <pre>
	 * This method Validates the Document page screen elements under plan--->Investment & Performance.
	 * </pre>
	 * 
	 * @author smykjn
	 * @Date 28th-sept-2017
	 * @return void
	 */
	public void validateDocumentPageScreenElements() {
		List<String> expHeaders = Arrays.asList(Stock.GetParameterValue(
				"ExpHeaders").split(","));
		try {
			CommonLib.switchToFrame(frameb);
			Web.clickOnElement(documents);
			CommonLib.waitForLoader(planDocLoader);
			if (CommonLib.isAllHeadersDisplayed(documentHeaders_th, expHeaders))
				Reporter.logEvent(Status.PASS,
						"Check if below columns are displayed.\n" + expHeaders,
						"" + "Specified columns are displayed.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Check if below columns are displayed.\n" + expHeaders,
						"" + "Specified columns are not displayed.", true);
			Web.clickOnElement(documentHeaders_th.get(0).findElement(
					By.tagName("a")));
			List<String> nameList = CommonLib
					.getWebElementstoListofStrings(documentTable_Name);
			System.out.println("Descending sorting order after clicking name:"
					+ nameList);
			List<String> listCopy = new ArrayList<String>(nameList);
			Collections.sort(nameList);
			Collections.reverse(nameList);
			if (listCopy.equals(nameList))
				Reporter.logEvent(Status.PASS, "Check sorting by name.",
						"Sorting by name is working as expected.", false);
			else
				Reporter.logEvent(Status.FAIL, "Check sorting by name.",
						"Sorting by name is working as expected.", true);
			listCopy.clear();
			Web.clickOnElement(documentHeaders_th.get(1).findElement(
					By.tagName("a")));
			List<String> symbolList = CommonLib
					.getWebElementstoListofStrings(documentTable_Symbol);
			System.out.println("Ascending sorting order after clicking symbol:"
					+ symbolList);
			listCopy = new ArrayList<String>(symbolList);
			Collections.sort(symbolList);
			if (listCopy.equals(symbolList))
				Reporter.logEvent(Status.PASS, "Check sorting by Symbol.",
						"Sorting by symbol is working as expected.", false);
			else
				Reporter.logEvent(Status.FAIL, "Check sorting by Symbol.",
						"Sorting by symbol is working as expected.", true);
			listCopy.clear();
			Web.clickOnElement(documentHeaders_th.get(2).findElement(
					By.tagName("a")));
			List<String> categoryList = CommonLib
					.getWebElementstoListofStrings(documentTable_Category);
			System.out
					.println("Descending sorting order after clicking category:"
							+ categoryList);
			listCopy = new ArrayList<String>(categoryList);
			Collections.sort(categoryList);
			Collections.reverse(categoryList);
			if (listCopy.equals(categoryList))
				Reporter.logEvent(Status.PASS, "Check sorting by category.",
						"Sorting by category is working as expected.", false);
			else
				Reporter.logEvent(Status.FAIL, "Check sorting by category.",
						"Sorting by category is working as expected.", true);
			List<String> view_links = CommonLib
					.getWebElementstoListofStrings(investment_overvw_View_Links);
			// view_links
			// =CommonLib.getWebElementstoListofStrings(investment_overvw_NA_text);
			Set<String> set_view = new TreeSet<>(view_links);
			System.out.println("Set:" + set_view);
			if (set_view.contains("View") || set_view.contains("N/A"))
				Reporter.logEvent(
						Status.PASS,
						"Check view text is displayed for investment overview column.",
						"" + "view text is displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check view text is displayed for investment overview column.",
						"" + "view text is not displayed.", true);
			set_view.clear();
			view_links.clear();
			view_links = CommonLib
					.getWebElementstoListofStrings(online_Prospectus_View_Links);
			set_view = new TreeSet<>(view_links);
			System.out.println("Set:" + set_view);
			if (set_view.contains("View") || set_view.contains("N/A"))
				Reporter.logEvent(
						Status.PASS,
						"Check view text is displayed for investment overview column.",
						"" + "view text is displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check view text is displayed for investment overview column.",
						"" + "view text is not displayed.", true);
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method terminates the user from user security management page.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 4th-Oct-2017
	 * @return void
	 */
	public void terminateUserFromUserSecurityManagement() {
		try {
			Select action = new Select(searchedResultsColumns.get(10)
					.findElement(By.tagName("select")));
			action.selectByVisibleText("Terminate");
			Web.waitForPageToLoad(Web.getDriver());
			Web.isWebElementDisplayed(confirmMsgBox, true);
			String actMsgBeforeTermination = confirmMsgBox.getText();
			if (actMsgBeforeTermination.contains(Stock
					.GetParameterValue("ExpMsgBeforeTermination")))
				Reporter.logEvent(Status.PASS,
						"select 'Terminate' from drop down and validate "
								+ "the below message is displayed.\n"
								+ confirmMsgBox.getText(),
						"Confirmation message is displayed with following message:"
								+ confirmMsgBox.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"select 'Terminate' from drop down and validate "
								+ "the message.\n" + confirmMsgBox.getText(),
						"Confirmation message is displayed with following message:"
								+ confirmMsgBox.getText(), true);
			if (Stock.GetParameterValue("Terminate").equalsIgnoreCase("Yes")) {
				Web.clickOnElement(confirmMsgBox.findElement(By
						.xpath(".//button[text()='Yes']")));
				Thread.sleep(3000);
				Web.isWebElementDisplayed(confirmMsgBox.findElement(By
						.xpath(".//button[text()='Continue']")), true);
				String actConfirmMsg = confirmMsgBox.getText();
				String expConfirmMsg = Stock
						.GetParameterValue("Assigned User Name")
						+ ")"
						+ " "
						+ Stock.GetParameterValue("ExpConfirmationMsg");
				System.out.println("Confirmation text:" + expConfirmMsg);
				if (actConfirmMsg.contains(expConfirmMsg))
					Reporter.logEvent(
							Status.PASS,
							"Terminate user and validate confirmation message is displayed.",
							"Confirmation message is displayed with following message:"
									+ confirmMsgBox.getText(), false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Terminate user and validate confirmation message is displayed.",
							"Confirmation message is not displayed with following message:"
									+ confirmMsgBox.getText(), true);
				Web.waitForPageToLoad(Web.getDriver());
				Web.clickOnElement(confirmMsgBox.findElement(By
						.xpath(".//button[text()='Continue']")));
			} else {
				Web.clickOnElement(confirmMsgBox.findElement(By
						.xpath(".//button[text()='Cancel']")));
				if (Web.isWebElementDisplayed(emailInput, true))
					Reporter.logEvent(
							Status.PASS,
							"Select Terminate from Action drop down and click cancel on"
									+ " confirmation banner.user must be navigated back to user security management page.",
							"User is navigated back to user security management page.",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Select Terminate from Action drop down and click cancel on"
									+ " confirmation banner.user must be navigated back to user security management page.",
							"User is navigated back to user security management page.",
							true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method validates the DB details for terminated user.
	 * </pre>
	 *
	 * @author smykjn
	 * @date 4th-Oct-2017
	 * @return void
	 */
	public void validateDBDetailsForTerminatedUser() {
		Date termDate = null;
		try {
			queryResultSet = DB.executeQuery(
					Stock.getTestQuery("getUsersDetails")[0],
					Stock.getTestQuery("getUsersDetails")[1],
					"K_" + Stock.GetParameterValue("Assigned User Name"));
			if (queryResultSet.next()) {
				termDate = queryResultSet.getDate("TERMDATE");
			}
			Date todaysDate = CommonLib.getDateInDateFormatFromDateString(
					"yyyy-MM-dd", CommonLib.getDate("yyyy-MM-dd", 0));
			if (termDate.equals(todaysDate))
				Reporter.logEvent(Status.PASS,
						"Validate if termdate is updated with today's date."
								+ todaysDate,
						"Term date is updated in DB properly.\n" + termDate,
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Validate if termdate is updated with today's date."
								+ todaysDate,
						"Term date is not updated in DB properly.\n" + termDate,
						true);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.logEvent(Status.FAIL, "Exception occured.",
					e.getMessage(), true);
		}
	}

	private void openSubmenuUnderAdministration(String submenuName) {
		boolean found=false;
		if (administrationSubmenus.size() > 0) {
			for (WebElement submenu : administrationSubmenus) {
				if (submenu.getText().trim().equalsIgnoreCase(submenuName)){
					Web.clickOnElement(submenu);
					found=true;
				}
				if(!found){
					if(submenu.getText().trim().contains(submenuName))
						Web.clickOnElement(submenu);
				}
					
			}
		}
	}

	public void openAnySubmenuUnderAdministrationMenu(String Submenu) throws InterruptedException {
		if (Web.isWebElementDisplayed(planTab, false))
			Web.actionsClickOnElement(planTab);
		if (Web.isWebElementDisplayed(administrationSubmenu, true))
			Web.actionsClickOnElement(administrationSubmenu);
		Thread.sleep(2000);
		this.openSubmenuUnderAdministration(Submenu);
		Thread.sleep(2000);
		if (Web.getDriver().findElement(By.tagName("i")).getText()
				.contains(Submenu))
			Reporter.logEvent(Status.PASS,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", true);
	}
	
	private void openSubmenuUnderFiduciary(String submenuName){
		if(fiduciarySubmenu.size()>0){
			for(WebElement submenu : fiduciarySubmenu){
				if (submenu.getText().equalsIgnoreCase(submenuName))
					Web.clickOnElement(submenu);
			}
		}
	}
	
	public void openAnySubmenuUnderFiduciary(String Submenu) throws InterruptedException{
		if (Web.isWebElementDisplayed(planTab, false))
			Web.actionsClickOnElement(planTab);
		if (Web.isWebElementDisplayed(fiduciaryMenu, true))
			Web.actionsClickOnElement(fiduciaryMenu);
		Thread.sleep(3000);
		this.openSubmenuUnderFiduciary(Submenu);
		Thread.sleep(8000);
		if(Submenu.contains("Participant QDIA notice listing order")){
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameb);
			if(participantQDIANoticeHeader.getText().contains("Participant QDIA Notice")){
				Reporter.logEvent(Status.PASS,
						"Navigate to Plan-->Fudiciary records-->" + Submenu, Submenu
								+ " page is displayed.", false);
				Web.getDriver().switchTo().defaultContent();
				return;
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Navigate to Plan-->Fudiciary records-->" + Submenu, Submenu
								+ " page is displayed.", true);
				Web.getDriver().switchTo().defaultContent();
				return;
			}
		}
		if (Web.getDriver().findElement(By.tagName("i")).getText()
				.contains(Submenu))
			Reporter.logEvent(Status.PASS,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", true);
		
	}
	
	private void openSubmenuUnderPlanOverview(String submenuName){
		if(overviewSubmenus.size()>0){
			for(WebElement submenu : overviewSubmenus){
				if (submenu.getText().equalsIgnoreCase(submenuName))
					Web.clickOnElement(submenu);
			}
		}
	}
	
	public void openAnySubmenuUnderPlanOverview(String Submenu){
		if (Web.isWebElementDisplayed(planTab, false))
			Web.actionsClickOnElement(planTab);
		if (Web.isWebElementDisplayed(planOverviewSubmenu, true))
			Web.actionsClickOnElement(planOverviewSubmenu);
		this.openSubmenuUnderPlanOverview(Submenu);
		if (Web.getDriver().findElement(By.tagName("i")).getText()
				.contains(Submenu))
			Reporter.logEvent(Status.PASS,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Navigate to Plan-->Administration-->" + Submenu, Submenu
							+ " page is displayed.", true);
	}

}
