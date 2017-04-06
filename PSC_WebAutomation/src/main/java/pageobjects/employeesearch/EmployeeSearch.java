package pageobjects.employeesearch;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.DB;
import lib.Reporter;

import com.aventstack.extentreports.*;

import db.filters.NotEqualFilter;
import framework.util.CommonLib;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.jetty.util.component.FileNoticeLifeCycleListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;

public class EmployeeSearch extends LoadableComponent<EmployeeSearch> {

	
	//Locators declaration
	@FindBy(css = "iframe[id = 'framec']")
	private WebElement employeeSearchFrame;
	@FindBy(xpath = ".//*[@id='newMenu']/li[2]/a")
	private WebElement tabEmployees;
//	@FindBy(id = "profileLink")
//	private WebElement linkProfile;
	@FindBy(id = "angularProfileLink")
	private WebElement linkProfile;
	@FindBy(id = "searchSelector")
	private WebElement drpdwnSearchEmployee;
	@FindBy(id = "searchEmployeeName")
	private WebElement txtSearchbox;
	@FindBy(css = "div[class = 'validationError']")
	private WebElement errorTxt;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td")
	private WebElement searchResultsTablerow;
	@FindBy(xpath = ".//*[@id='employeeSearchButton']")
	private WebElement btnGoEmployeeSearch;
	@FindBy(xpath = ".//*[@id='searchResultsTable_data']/tr/td")
	private WebElement errortxtSearchResults;
	@FindBy(css = "input[id = 'planSearchAc_input']")
	private WebElement txtPlanNumberField;
	@FindBy(css = "span[class *= 'growl-title']")
	private WebElement errorMsgBox;
	@FindBy(id = "planSearchAutocompleteButton")
	private WebElement btnGoPlanNumberforSearchBox;
	@FindBy(css = "button[id = 'planSearchDropdownButton']")
	private WebElement btnGoPlanNumberfordrpdwn;
	@FindBy(css = "select[id = 'planDropDown']")
	private WebElement planDropdown;
	@FindBy(xpath = ".//div[@class='ui-datatable-scrollable-body']/table//tr//a")
	private List<WebElement> divisionSearchReslist;
	@FindBy(xpath = "(//table[@class='pscNoPrint'])[2]/tbody")
	private WebElement tableDivresults;
	@FindBy(xpath = "//tbody[@id = 'searchResultsTable_data']/tr/td/a")
	private List<WebElement> searchResultsFordiv;
	@FindBy(xpath = "//tbody[@id = 'searchResultsTable_data']/tr[1]/td[1]/a")
	private WebElement searchResdivLastname;
	@FindBy(xpath = ".//tbody[contains(@id,'searchResultsTable_data')]/tr/td[4]")
	private List<WebElement> searchResultsSSNTable;
	@FindBy(css = "a[class = 'print']")
	private WebElement iconPrint;
	@FindBy(xpath = ".//div[@id = 'searchResultsTable']/table/.//th/span[text()[normalize-space()!='']]")
	private List<WebElement> headerSearchResTable;
	@FindBy(xpath = ".//*[@id='searchResultsTable']/table//tbody/tr/td[1]/a")
	private List<WebElement> listLastName;
	@FindBy(xpath = ".//*[@id='searchResultsTable']/table//tbody/tr/td[2]/a")
	private List<WebElement> listFirstName;
	@FindBy(xpath = ".//*[@id='searchResultsTable']/table//tbody/tr/td[3]/a")
	private List<WebElement> listMI;
	@FindBy(xpath = ".//*[@id='searchResultsTable_data']/tr/td[4]")
	private List<WebElement> listSSNtbl;
	@FindBy(xpath = ".//*[@id='searchResultsTable_data']/tr/td[7]")
	private List<WebElement> listPartID;
	@FindBy(xpath = ".//*[@id='searchResultsTable_data']/tr/td[6]")
	private List<WebElement> listEmpID;
	@FindBy(xpath = ".//tbody[@id='searchResultsTable_data']/tr[1]/td[2]/a")
	private WebElement searchResultsFirstName;
	@FindBy(xpath = ".//tbody[@id='searchResultsTable_data']/tr[1]/td[1]/a")
	private WebElement searchResultsLastName;
	@FindBy(xpath = ".//tbody[@id='searchResultsTable_data']/tr[1]/td[3]/a")
	private WebElement searchResultsMI;
	@FindBy(xpath = ".//*[@id='employeeSearchOverviewContainer_content']/div[1]/div[1]/h1/label")
	private WebElement txtOverview;
	@FindBy(xpath = ".//div[@id='searchResultsTable_paginatortop']/span[2]/a")
	private List<WebElement> linkPagination;
	@FindBy(xpath = ".//div[@id='searchResultsTable_paginatortop']/select")
	private WebElement searchResDropdown;
	@FindBy(xpath = ".//*[@id='gritter-item-1']/div/div[1]")
	private WebElement linkDismissErrorMsgBox;
	@FindBy(xpath = ".//div[@id='searchResultsTable']/table/thead/tr/th[4]/span")
	private WebElement sortOptnSSN;
	@FindBy(xpath = ".//div[@id='searchResultsTable']/table/thead/tr/th[2]/span[2]")
	private WebElement sortOptnFirstName;
	@FindBy(xpath = ".//div[@id='searchResultsTable']/table/thead/tr/th[1]/span")
	private WebElement sortOptnLastName;
	@FindBy(xpath = ".//div[@id='searchResultsTable']/table/thead/tr/th[6]/span")
	private WebElement sortOptnEmpID;
	@FindBy(xpath = ".//div[@id='searchResultsTable']/table/thead/tr/th[7]/span")
	private WebElement sortOptnPartID;
	@FindBy(id = "employeeFilter")
	private WebElement txtFilter;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td[4]")
	private WebElement linkSSN;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td[5]")
	private WebElement linkEXT;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td[6]")
	private WebElement linkEMP_ID;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td[7]")
	private WebElement linkPART_ID;
	@FindBy(xpath = ".//*[@id='searchResultsTable_row_0']/td[8]")
	private WebElement linkDivision;
	@FindBy(id = "logOutLink")
	private WebElement linkLogout;
	@FindBy(id = "planSearchAutocompleteButton")
	private WebElement btnGoPlanNumber;
	@FindBy(xpath = ".//tbody[contains(@id,'searchResultsTable_data')]/tr/td[5] | .//tbody[contains(@id,'searchResultsTable_data')]/tr/td[4]")
	private List<WebElement> searchResultsSSNMItable;
	@FindBy(xpath="//td/a[contains(@id,'searchResultsTable')]")
	private List<WebElement> fNLNMILink;
	@FindBy(id="EmployeebasicInfoMailingName")
	private WebElement empNameHeader;
	@FindBy(id="participantDetail")
	private WebElement partDetailTab;
	@FindBy(xpath="//span[contains(text(),'Employment information')]")
	private WebElement empMntInfoHeader;
	@FindBy(xpath="//div[@id='employmentInfo']//label")
	List<WebElement> listOfEmpmntInfoLabels;
	@FindBy(id="empInfoEditLink")
	private WebElement empInfoEditLink;
	@FindBy(id="EmployeebasicInfoMailingName")
	private WebElement empName;
	@FindBy(id="EmployeebasicInfoSSN")
	private WebElement empSSN;
	@FindBy(id="hireDate")
	private WebElement hireDate;
	@FindBy(id="termDate")
	private WebElement termDate;
	@FindBy(id="terminationReasonCode")
	private WebElement termReason;
	@FindBy(id="employeeId")
	private WebElement empId;
	@FindBy(id="officerInd")
	private WebElement officer;
	@FindBy(id="highlyCompensatedInd")
	private WebElement highlyCompensatedInd;
	@FindBy(id="ownershipPercent")
	private WebElement ownership;
	@FindBy(id="sarbanesOxleyReporting")
	private WebElement tradeMonitor;
	@FindBy(xpath="//div[@id='employeeInfoEditDialogId']/iframe")
	private WebElement empInfoEditFrame;
	@FindBy(xpath="//input[contains(@name,'UPDATE_SAVE')]")
	private WebElement empUpdateSaveBtn;
	@FindBy(xpath="//label[contains(text(),'Employee ID:')]/../following-sibling::td//td")
	private WebElement expEmployeeID;
	@FindBy(xpath="//label[contains(text(),'Hire date:')]/../following-sibling::td//td")
	private WebElement expHireDate;
	@FindBy(xpath="//label[contains(text(),'Officer:')]/../following-sibling::td//td")
	private WebElement expOfficer;
	@FindBy(id="empInfoHistLink")
	private WebElement empInfoHistroyLink;
	//@FindBy(xpath="//a[@href='#' and @role='button']")
	@FindBy(xpath=".//*[@id='employmentInfo_content']//span[.='close']")
	private WebElement modalWindowCloseLink;
	@FindBy(xpath="//div[@class='oheading']//label[contains(text(),'Overview')]")
	private WebElement overwLabel;
	@FindBy(xpath="//div[@id='contactInfo_content']/table")
	private WebElement contctInFoTable;
	@FindBy(linkText="Account detail")
	private WebElement accntDetailsTab;
	@FindBy(xpath="//div[@id='contactInfo']//label")
	private List<WebElement> contactInFoLabels;
	@FindBy(id="contactEditLink")
	private WebElement contctEditLink;
	@FindBy(xpath="//*[@id='contactInfoEditDialogId']/iframe")
	private WebElement contctInfoEditFrame;
	@FindBy(xpath="//form[@name='ChangeGenInfo']//input/../../preceding-sibling::td//font")
	private List<WebElement> actCtcInputLabelsWindow;
	@FindBy(xpath="//form[@name='ChangeGenInfo']//select/../../preceding-sibling::td//font")
	private List<WebElement> actCtcSelectLabelsWindow;
	@FindBy(xpath="//form[@name='ChangeGenInfo']//font/input")
	private List<WebElement> listOfContactInFoValues;
	@FindBy(id="lastName")
	private WebElement lName;
	@FindBy(id="firstName")
	private WebElement fName;
	@FindBy(id="middleName")
	private WebElement mName;
	@FindBy(xpath=".//*[@id='maritalStatus']/option[@selected='']")
	private WebElement mStatus;
	@FindBy(id="firstLineMailing")
	private WebElement address;
	@FindBy(id="city")
	private WebElement city;
	@FindBy(xpath=".//*[@id='state']/option[@selected='']")
	private WebElement state;
	@FindBy(id="zip")
	private WebElement zip;
	@FindBy(xpath=".//*[@id='country']/option[@selected='']")
	private WebElement country;
	@FindBy(id="homePhoneAreaCode")
	private WebElement homeAreaCode;
	@FindBy(id="homePhoneNumber")
	private WebElement homePhoneNumber;
	@FindBy(id="workPhoneAreaCode")
	private WebElement workAreaCode;
	@FindBy(id="workPhoneNumber")
	private WebElement workPhoneNumber;
	@FindBy(xpath="//input[@value='Save']")
	private WebElement save;
	@FindBy(xpath="//li[contains(@class,'default')]/a[.='Account detail']")
	private WebElement accntLink;
	@FindBy(linkText="Beneficiaries")
	private WebElement benFiciary;
	@FindBy(xpath="//*[@id='beneficiaryDashboard']//iframe")
	private WebElement beneFiciaryFrame;
	@FindBy(id="beneficiaryEmployee")
	private WebElement beneficiaryHeader;
	@FindBy(xpath="//div[@id='iframePanelcA']/iframe")
	private WebElement framecA;
	@FindBy(xpath="//span[1]//div[@class='benTableRow']/div[contains(text(),'effective date') or contains(text(),'Percent') or contains(text(),'SSN') or contains(text(),'Beneficiary type') or contains(text(),'Birth date') or contains(text(),'Relationship')]")
	private WebElement benUIRecords;
	@FindBy(id="recentlyViewedEmployeeLink")
	private WebElement recentlyViewedLink;
	@FindBy(id="recentlyViewedEmployeeTable")
	private WebElement recentlyViewedEmpTable;
	@FindBy(xpath="//*[@id='recentlyViewedEmployeeTable']//a")
	private List<WebElement> recentlyViewedEmployee;
	@FindBy(xpath="//div[@id='salaryInfo']//a")
	private WebElement salaryEditLink;
	@FindBy(xpath="//div[@id='salaryInfo']//span[@class='sectionHeader']")
	private WebElement salaryHeader;
	@FindBy(xpath="//div[@id='income']//th")
	private List<WebElement> salaryTableHeaders;
	@FindBy(id="salAmt")
	private WebElement salaryInput;
	@FindBy(id="empIncomeEditFrameId")
	private WebElement incomeEditFrame;
	@FindBy(xpath="//font[@class='important_note']//li")
	private List<WebElement> errorBoxes;
	@FindBy(xpath=".//*[@id='paycheckContributionInfo']//span[@class='sectionHeader']")
	private WebElement paycheckContriHeader;
	@FindBy(xpath="//font[@class='column_title' and contains(text(),'SSN')]")
	private WebElement modalWindowSSN;
	@FindBy(xpath="//font[@class='section_title_text' and contains(text(),'Update employment')]")
	private WebElement upDateEmploymentHeader;
	@FindBy(xpath="//label[contains(text(),'Hire date')]/../following-sibling::td//td")
	private WebElement hireDateReadOnly;
	@FindBy(xpath="//label[contains(text(),'Overview')]")
	private WebElement overviewLabel;
	@FindBy(xpath=".//*[@id='participantIndicative']//label[contains(text(),'Name')]")
	private WebElement nameLabel;
	@FindBy(xpath=".//*[@id='participantIndicative']//label[contains(text(),'SSN')]")
	private WebElement ssnLabel;
	@FindBy(xpath=".//*[@id='participantIndicative']//label[contains(text(),'DOB')]")
	private WebElement dobLabel;
	@FindBy(xpath=".//*[@id='participantIndicative']//label[contains(text(),'Employee ID')]")
	private WebElement empIDLabel;
	@FindBy(id="EmployeebasicInfoIndividualID")
	private WebElement empIdValue;
	@FindBy(id="EmployeebasicInfoBirthDate")
	private WebElement dobValue;
	@FindBy(xpath="//*[@id='paeUrlButton']")
	private WebElement employeeWebButton;
	@FindBy(xpath="//*[@id='selectedPlanHeaderPanel']//a[.='Print']")
	private WebElement print;
	@FindBy(xpath="//div[@class='printHeader']/h3")
	private WebElement printPriviewWindowHeader;
	@FindBy(xpath="//div[@id='overviewtable']//span[contains(text(),'Plan number')]")
	private WebElement planNumberColumn;
	@FindBy(xpath="//div[@id='overviewtable']//span[contains(text(),'Plan name')]")
	private WebElement planNameColumn;
	@FindBy(xpath="//div[@id='overviewtable']//span[contains(text(),'Balance')]")
	private WebElement balanceColumn;
	@FindBy(xpath="//div[@id='overviewtable']//span[contains(text(),'Details')]")
	private WebElement detailsColumn;
	@FindBy(xpath=".//*[@id='overviewtable']//td[1]")
	private List<WebElement>  planNumbersUI;
	@FindBy(xpath=".//*[@id='overviewtable']//td[2]")
	private List<WebElement>  planNamesUI;
	@FindBy(xpath=".//*[@id='selectedPlanHeaderPanel']//h3")
	private WebElement selectedPlanHeader;
	@FindBy(xpath=".//*[@id='overviewtable_data']//tr[contains(@class,'selected')]//a")
	private WebElement highlightedPlannumber;
	@FindBy(xpath=".//*[@id='overviewtable_data']//tr//a")
	private List<WebElement> planListOnOveriewPage;
	@FindBy(xpath="//div[contains(@class,'ui-datatable-footer')]//span")
	private List<WebElement> totalBalance;
	@FindBy(xpath=".//*[@id='overviewtable']//td[3]//span")
	private List<WebElement> balanceList;
	@FindBy(xpath="//*[@class='page-title']/h1")
	private WebElement accountDetailHeader;
	@FindBy(xpath="//div[@id='iframePanelcA']/iframe")
	private WebElement viewAccountDetailFrame;
	@FindBy(xpath="//a/h4[contains(text(),'Money Source')]")
	private WebElement moneySource;
	@FindBy(xpath="//a/h4[contains(text(),'Investments')]")
	private WebElement investment;
	@FindBy(xpath=".//*[@id='asOfDateCal']//preceding-sibling::span")
	private WebElement balanceOnViewAccountPage; 
	@FindBy(xpath="//a/h4[contains(text(),'Money Source')]//ancestor::*[2]/following-sibling::div//th")
	private List<WebElement> moneySrcHeaders;
	@FindBy(xpath="//a/h4[contains(text(),'Investments')]//ancestor::*[2]/following-sibling::div//th")
	private List<WebElement> investmentHeaders;
	@FindBy(xpath="//a/h4[contains(text(),'Money Source')]//ancestor::*[2]/following-sibling::div//tr")
	private List<WebElement> moneySrcRow;
	@FindBy(xpath="//a/h4[contains(text(),'Investments')]//ancestor::*[2]/following-sibling::div//tr")
	private List<WebElement> investmentRows;
	@FindBy(xpath="//i[contains(text(),'Return to employee overview')]")
	private WebElement returnToEmployeeOverview;
	@FindBy(xpath=".//*[@id='asOfDateCal']//following-sibling::span[text()='*']/preceding-sibling::*[1]")
	private WebElement calendarIcon;
	@FindBy(id="asOfDateCal_table")
	private WebElement asOfDate;
	@FindBy(xpath="//h4[contains(text(),'Money Sources')]/ancestor::*[2]/following-sibling::div//strong")
	private List<WebElement> totalForMoneySources;
	@FindBy(xpath="//h4[contains(text(),'Investments')]/ancestor::*[2]/following-sibling::div//strong")
	private List<WebElement> totalForInvetsment;
	@FindBy(xpath="//span[contains(text(),'Basic information')]")
	private WebElement basicinfoHeader;
	@FindBy(xpath="//*[@id='basicInfo']//label")
	private List<WebElement> basicInfoLabels;
	@FindBy(xpath="//*[@id='basicInfo']//label/../following-sibling::*//td")
	private List<WebElement> basicInfoValues;
	public Map<String,String> basicInfoMapBeforeChanges;
	@FindBy(id="basicInfoEditLink")
	private WebElement basicInfoEdit;
	@FindBy(xpath="//*[@id='basicInfoEditDialogId']//iframe")
	private WebElement basicInfoEditFrame;
	@FindBy(xpath="//*[@id='basicInfoEditDialogId']/preceding-sibling::div//span[.='close']")
	private WebElement closeIconBasicInfoEditWindow;
	@FindBy(xpath="//font[@class='column_title'][contains(text(),'SSN')]")
	private WebElement ssnHeaderOnModalWindow;
	@FindBy(xpath="//font[@class='column_title'][contains(text(),'Name')]")
	private WebElement nameHeaderOnModalWindow;
	@FindBy(xpath="//font[contains(text(),'Employee Contact Information')]/../following-sibling::*[3]//tr//td[1]//font[text()!='']")
	private List<WebElement> modalWindowContactInfoLabels;
	@FindBy(xpath="//font[contains(text(),'Employee Basic Information')]/../following-sibling::*[3]//tr//td[1]//font[text()!='']")
	private List<WebElement> modalWindowBasicInfoLabels;
	@FindBy(id="birthDate")
	private WebElement birthDate;
	@FindBy(id="maritalStatus")
	private WebElement maritalSts;
	@FindBy(id="sex")
	private WebElement gender;
	@FindBy(id="language")
	private WebElement language;
	@FindBy(xpath=".//*[@id='sex']/option[@selected='']")
	private WebElement checkGender;
	@FindBy(xpath=".//*[@id='language']/option[@selected='']")
	private WebElement checkLanguage;
	@FindBy(xpath=".//*[@id='maritalStatus']/option[@selected='']")
	private WebElement checkMaritalStatus;
	@FindBy(xpath="//b[contains(text(),'Incoming Rollover Assistance')]")
	private WebElement incomingRollAssitance;
	@FindBy(id="IPB_CONTACT_4")
	private WebElement outsideAssets;
	@FindBy(xpath="//label[contains(text(),'Birth date')]/../following-sibling::td//td")
	private WebElement updatedBirthDate;
	@FindBy(xpath="//label[contains(text(),'Marital status')]/../following-sibling::td//td")
	private WebElement updatedMaritalStatus;
	@FindBy(xpath="//label[contains(text(),'Gender')]/../following-sibling::td//td")
	private WebElement updatedGender;
	@FindBy(xpath="//label[contains(text(),'Language')]/../following-sibling::td//td")
	private WebElement updatedLanguage;
	@FindBy(xpath="//div[contains(@id,'subsetInfo_content')]")
	private WebElement subSetsection; 
	@FindBy(xpath="//div[@id='subSetInfoId']//th")
	private List<WebElement> subSetHeaders;
	@FindBy(id="subHisttLink")
	WebElement subSetHistoryLink;
	@FindBy(id="empSubsetHistFrameId")
	WebElement empSubSetHistFrame;
	@FindBy(xpath="//table[2]//tr[1]//font/font")
	List<WebElement> subSetHistoryHeaders;
	@FindBy(xpath="//table[2]//tr")
	List<WebElement> subSetDataRows;
	@FindBy(xpath="//*[@id='empSubsetHistDialogId']/preceding-sibling::div//span[.='close']")
	WebElement closeIconOnSubsetHistoryWindow;
	@FindBy(xpath="//div[@id='contactInfo']//label[contains(text(),'Name:')]/../following-sibling::td//td")
	private WebElement contactName;
	@FindBy(xpath="//div[@id='basicInfo']//label[contains(text(),'SSN:')]/../following-sibling::td//td")
	private WebElement ssnInBasicInfoSection;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo_content']/table")
	private WebElement enrollmentAndEligibilitySection;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//a")
	private WebElement enrolAndEligibltyEditLink;
	@FindBy(id="enrollEligEditFrameId")
	private WebElement enrollAndEligEditFrame;
	
	String qdroPart = null;
	String normalPart = null;
	String transferPart=null;
		
	String maritalStatus = null;
	String state1 = null;
	String country1 = null;
	String relanShipCode = null;
	String beneTypeCode = null;
	public  String[] str1 = new String[2];
	public String[] individual = new String[3];
	public String[] terminatedEmp = new String[2];
	public List<String> qntTypeIndividuals;
	public Map<String,String> partDetails;
	
	Map<String,String> m = new LinkedHashMap<String,String>();
	LoadableComponent<?> parent;
	Actions actions;
	Select select;
	ResultSet queryResultSet;
	List<String> listSSN;
	Set<String> setSSN;
	List<String> expectedListofElements;
	List<String> actualListofElements;
	Map<String,String> employmentDetailsBeforeRehiring = new LinkedHashMap<String,String>();
	Map<String,String> employmentDetailsAfterRehiring = new LinkedHashMap<String,String>();
	//List<String> planNameList;
	List<String> planNumberList;

	public EmployeeSearch() {
		PageFactory.initElements(Web.getDriver(), this);
		
	}

	@Override
	protected void isLoaded() throws Error {
		Web.getDriver().switchTo().defaultContent();
		Assert.assertTrue(Web.isWebElementDisplayed(employeeSearchFrame));
	}
@FindBy(xpath="//*[@id='newMenu']//li/a[.='Search employee']")
private WebElement searchEmployeeOptionLink;
	@Override
	@SuppressWarnings("unused")
	protected void load() {
		try {
			HomePage homepage = (HomePage) this.parent;
			// LoginPage login = new LoginPage();
			new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			Reporter.logEvent(Status.PASS,
					"Check if the user has landed on homepage",
					"The user has landed on homepage", true);
			CommonLib.waitForProgressBar();
			Web.waitForElement(tabEmployees);
			//Web.clickOnElement(tabEmployees);
			//Web.waitForElement(drpdwnSearchEmployee);
			actions = new Actions(Web.getDriver());
			actions.moveToElement(tabEmployees).click().build().perform();
			Web.waitForPageToLoad(Web.getDriver());
			if(!Web.isWebElementDisplayed(employeeSearchFrame, true))
			{
				actions.moveToElement(tabEmployees).click().build().perform();
				actions.click(searchEmployeeOptionLink).perform();
			}
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("BTN GO")) {
			return this.btnGoPlanNumberforSearchBox;
		}
		if(fieldName.trim().equalsIgnoreCase("Link_EXT"))
		{
			return this.linkEXT;
		}

		if (fieldName.trim().equalsIgnoreCase("FILTER")) {
			return this.txtFilter;
		}
		if (fieldName.trim().equalsIgnoreCase("FRAME")) {
			return this.employeeSearchFrame;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.linkLogout;
		}
		if (fieldName.trim().equalsIgnoreCase("BTN_GO_EMP_SEARCH")) {
			return this.btnGoEmployeeSearch;
		}
		if (fieldName.trim().equalsIgnoreCase("ICON_PRINT")) {
			return this.iconPrint;
		}
		if (fieldName.trim().equalsIgnoreCase("TXT_SEARCH_BOX")) {
			return this.txtSearchbox;
		}

		return null;
	}

	@SuppressWarnings("unused")
	private List<WebElement> getWebElementasList(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("LastName Sort")) {
			return this.listLastName;
		}
		if(fieldName.trim().equalsIgnoreCase("EmpLastNameLink"))
		{
			return this.fNLNMILink;
		}
		if (fieldName.trim().equalsIgnoreCase("FirstName Sort")) {
			return this.listFirstName;
		}
		if (fieldName.trim().equalsIgnoreCase("MI Sort")) {
			return this.listMI;
		}

		if (fieldName.trim().equalsIgnoreCase("SSN Sort")) {
			return this.listSSNtbl;
		}
		if (fieldName.trim().equalsIgnoreCase("EMPID Sort")) {
			return this.listEmpID;
		}
		if (fieldName.trim().equalsIgnoreCase("PartID Sort")) {
			return this.listPartID;
		}
		return null;
	}

	/**
	 * This method used to search the employee by SSN number
	 * @param SSN
	 * @throws InterruptedException
	 */
	public void searchEmployeeBySSN(String SSN) throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("SSN");
		Thread.sleep(4000);
		Web.setTextToTextBox(txtSearchbox, SSN);
		if(Web.isWebElementDisplayed(btnGoEmployeeSearch, true))
			Web.clickOnElement(btnGoEmployeeSearch);
		//btnGoEmployeeSearch.click();
		Thread.sleep(2500);
		Web.getDriver().switchTo().defaultContent();
		dismissErrorBox();
	}
	
	/**
	 * This method used to search the employee by SSN-All Plans
	 * @param SSN
	 * @throws InterruptedException
	 */
	public void searchEmployeeBySSNAllPlans(String SSN) throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.isWebElementDisplayed(drpdwnSearchEmployee,true);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("SSN - all plans");
		Web.waitForElement(txtSearchbox);
		Web.setTextToTextBox(txtSearchbox, SSN);
		if(Web.isWebElementDisplayed(btnGoEmployeeSearch, true))
		Web.clickOnElement(btnGoEmployeeSearch);
		Web.waitForElement(fNLNMILink.get(0));
		Web.getDriver().switchTo().defaultContent();
		//dismissErrorBox();
	}

	/**
	 * This method validates if search results are displayed for a particular search criteria
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isSearchResultsDisplayed() throws InterruptedException {
		boolean isSearchttableDisplayed;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Thread.sleep(5000);
		isSearchttableDisplayed = Web
				.isWebElementDisplayed(searchResultsTablerow,true);
		Web.getDriver().switchTo().defaultContent();
		return isSearchttableDisplayed;
	}


	/**
	 * This method used to search the employee by Name
	 * @param Name
	 * @throws InterruptedException
	 */
	public void searchEmployeeByName(String Name) throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("Name");
		Web.isWebElementDisplayed(txtSearchbox, true);
		Web.clickOnElement(txtSearchbox);
		Web.setTextToTextBox(txtSearchbox, Name);		
		Thread.sleep(2000);
		Web.clickOnElement(btnGoEmployeeSearch);
		Thread.sleep(2500);		
		Web.waitForElements(fNLNMILink);
		Web.getDriver().switchTo().defaultContent();
	}
	/**
	 * This method used to search the employee by EmployeeID
	 * @param EmployeeID
	 * @throws InterruptedException
	 */
	public void searchEmployeeByEmployeeId(String EmployeeID) throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("Employee ID");
		Thread.sleep(3000);
		Web.setTextToTextBox(txtSearchbox, EmployeeID);
		Thread.sleep(2000);
		Web.clickOnElement(btnGoEmployeeSearch);	
		Thread.sleep(2500);
		Web.getDriver().switchTo().defaultContent();
	}

	/**
	 * This method checks the error messages for invalid search criteria
	 * @return
	 */
	public String getErrorMessageTextforInvalidSearch() {
		String errorText;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if (Web.isWebElementDisplayed(errortxtSearchResults)) {
			errorText = errortxtSearchResults.getText();
		} else {
			errorText = "";
		}
		Web.getDriver().switchTo().defaultContent();
		return errorText;
	}

	/**
	 * This method returns the total number of plans the user is having
	 * @param getNumberOfPlansQuery
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getNumberOfplans(String[] getNumberOfPlansQuery, String username)
			throws Exception {
		queryResultSet = DB.executeQuery(getNumberOfPlansQuery[0],
				getNumberOfPlansQuery[1], "K_" + username);
		return DB.getRecordSetCount(queryResultSet);
	}

	/**
	 * This method selects the required plan from the plan dropdown box on homepage
	 * @param resultset
	 * @return
	 * @throws SQLException
	 */
	public String selectFromDropdown(ResultSet resultset) throws SQLException {
		String planWithDivisons = "";
		while (resultset.next()) {
			planWithDivisons = resultset.getString("GA_ID");
			Web.selectDropDownOption(planDropdown, planWithDivisons);
			btnGoPlanNumberfordrpdwn.click();
			if (getErrorMessageText().trim().isEmpty()) {
				break;
			}
		}
		return planWithDivisons;

	}

	/**
	 * This is a generic method which checks which field is displayed to select plan number
	 * @param resultset
	 * @return
	 * @throws SQLException
	 */

	public String selectPlanFromResultset(ResultSet resultset)
			throws SQLException, InterruptedException {
		String planNumber = "";
		if (Web.isWebElementDisplayed(planDropdown)) {
			planNumber = selectFromDropdown(resultset);
			Thread.sleep(3000);
			navigateToEmployeeTab();
		} 
		if (Web.isWebElementDisplayed(txtPlanNumberField)) {
			planNumber = enterFromtextBox(resultset);
			Thread.sleep(3000);
			navigateToEmployeeTab();
		}
		return planNumber;
	}

	/**
	 * This method selects the required plan from the plan text box on home page
	 * @param resultset
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public String enterFromtextBox(ResultSet resultset) throws SQLException {
		int countResultSet;
		String planWithDivisons = "";
		while (resultset.next()) {
			planWithDivisons = resultset.getString("GA_ID");
			Web.setTextToTextBox(txtPlanNumberField, planWithDivisons);
			btnGoPlanNumberforSearchBox.click();
			if (getErrorMessageText().trim().isEmpty()) {
				break;
			}
		}
		return planWithDivisons;
	}

	/**
	 * Method captures the error message text from error pop up 
	 * @return
	 */
	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.errorMsgBox,
				false);

		if (isElementPresent)
			return this.errorMsgBox.getText();
		else
			return "";
	}

	/**
	 * This method navigates the user to employee tab
	 * @throws InterruptedException
	 */
	public void navigateToEmployeeTab() throws InterruptedException {
		Web.clickOnElement(tabEmployees);
		Web.isWebElementDisplayed(drpdwnSearchEmployee, false);
		actions = new Actions(Web.getDriver());
		actions.moveToElement(linkProfile);
		actions.build().perform();
		Thread.sleep(2000);
		
	}
	/**
	 * This method used to search the employee by Division
	 * @throws InterruptedException
	 */
	
	public void searchByDivision() throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(drpdwnSearchEmployee);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("Division");
		if (Web.isWebElementDisplayed(tableDivresults,true)) {
			Reporter.logEvent(Status.PASS,
					"Check if divisions are displayed for the plan",
					"The divisions related to plan are displayed", false);
		}
		divisionSearchReslist.get(0).click();
		Thread.sleep(3000);
		if (Web.isWebElementDisplayed(this.searchResdivLastname)) {
			Reporter.logEvent(
					Status.PASS,
					"Check if employee deatils are displayed for the specific division",
					"The employee details are polpulated for the selected diviosion",
					false);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	/**
	 * This method used to search the employee by Participant ID
	 * @param pptID
	 * @throws InterruptedException
	 */
	public void searchByParticipantID(String pptID) throws InterruptedException {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("Participant ID");
		Thread.sleep(3000);
		Web.setTextToTextBox(txtSearchbox, pptID);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Web.clickOnElement(btnGoEmployeeSearch);		
		Thread.sleep(2500);		
		lib.Web.getDriver().switchTo().defaultContent();
	}
	/**
	 * This method converts the List of WebElements to List of string
	 * @param refList
	 * @return
	 */

	public List<String> getWebElementstoListofStrings(List<WebElement> refList) {
		List<String> list = new ArrayList<String>();
		for (WebElement refWebElement : refList) {
			list.add(refWebElement.getText());
		}

		return list;
	}
	/**
	 * This method converts the List of WebElements to List of string when multiple columns are selected
	 * @param refList
	 * @return
	 */

	public List<String> getMultipleWebElementstoListofStrings(List<WebElement> refList) {
		List<String> list = new ArrayList<String>();

		for(int iCounter = 0;iCounter < refList.size();iCounter=iCounter+2)
		{
			list.add((refList.get(iCounter).getText())+refList.get(iCounter+1).getText());
		}

		return list;
	}
	/**
	 * This method verifies if the search results contain any duplicate values ,Returns true if it finds any duplicate record
	 * @return
	 */
	public boolean checkIfduplicateExists() {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		boolean isDuplateRowFound = false;
		listSSN = getMultipleWebElementstoListofStrings(searchResultsSSNMItable);
		setSSN = new TreeSet<>(listSSN);
		if (listSSN.size() == setSSN.size()) {
			isDuplateRowFound = true;
		} else {
			isDuplateRowFound = false;
		}
		Web.getDriver().switchTo().defaultContent();
		return isDuplateRowFound;
	}

	/**
	 * This method verifies the dropdown option on employeesearch page
	 * @return
	 */
	public boolean compareDropdownOptions() {
		List<String> actualOptionsList;
		boolean areDropdownOptionsSame;
		String[] actualOptions = new String[] { "SSN", "Name", "Employee ID",
				"Participant ID", "Division", "--------------------","Name - all plans","SSN - all plans"};
		/*String[] actualOptions = new String[] { "SSN", "Name", "Employee ID",
				"Participant ID", "--------------------","Name - all plans","SSN - all plans"};*/
		actualOptionsList = Arrays.asList(actualOptions);
		List<String> dropdownOptionlist = new ArrayList<String>();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		select = new Select(drpdwnSearchEmployee);
		select.getOptions();
		for (WebElement tempWebElement : select.getOptions()) {
			dropdownOptionlist.add(tempWebElement.getText());
		}
		if (actualOptionsList.equals(dropdownOptionlist)) {
			areDropdownOptionsSame = true;
		} else {
			areDropdownOptionsSame = false;
		}
		Web.getDriver().switchTo().defaultContent();
		return areDropdownOptionsSame;
	}

	/**
	 * This method verifies the different screen elements of Employeesearch page as Go button,Print button etc
	 */
	public void verifyScreenElements() {
		String[] verifyElements = new String[] {"BTN_GO_EMP_SEARCH",
				"ICON_PRINT", "TXT_SEARCH_BOX" };
		Web.getDriver().switchTo().frame(employeeSearchFrame);

		for (String ele : verifyElements) {
			if (Web.isWebElementDisplayed(getWebElement(ele))) {			
				Reporter.logEvent(Status.PASS, "Verify if the " + ele
						+ " button is displayed",
						"The "+ele+" button is displayed as expected", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify if the " + ele
						+ " button is displayed",
						"The "+ele+" button is not displayed on search page", true);
			}			
		}
		Web.getDriver().switchTo().defaultContent();
	}

	/**
	 * This method verifies the column headers
	 * @throws Exception
	 */
	public void verifyColumnHeaders() throws Exception {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		String[] headersArray = new String[] { "Last name", "First name",
				"M.I.", "SSN", "Ext", "Emp ID", "Part ID", "Division" };
		/*String[] headersArray = new String[] { "Last name", "First name",
				"M.I.", "SSN", "Ext", "Emp ID", "Part ID"};*/
		expectedListofElements = Arrays.asList(headersArray);
		Web.waitForElements(headerSearchResTable);
		actualListofElements = getWebElementstoListofStrings(headerSearchResTable);
		// removing "" from actualListofElements
		Set<String> finalSet = new LinkedHashSet<String>(actualListofElements);
		finalSet.remove("");
		List<String> actEle = new ArrayList<String>(finalSet);
		if (expectedListofElements.equals(actEle)) {
			Reporter.logEvent(
					Status.PASS,
					"Check if the headers/Order of search results are displayed as expected in search results table",
					"The headers aare displayed as expected", false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if the headers are displayed as expected in search results table",
					"The headers are not displayed as expected" + "Actual:"
							+ actualListofElements + "& Expected :"
							+ expectedListofElements, true);
			Web.getDriver().switchTo().defaultContent();
		}
	}
	/**
	 * This method verifies if the search results are displayed as Hyperlinks
	 * @return
	 */

	public boolean verifySearchResultsasLinks() {
		boolean areResultsdisplayedLinks;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(searchResultsFirstName);
		Web.waitForElement(searchResdivLastname);
		String tagNameLastNm = searchResultsFirstName.getTagName();
		String tagNameFirstNm = searchResdivLastname.getTagName();

		if (tagNameFirstNm.equalsIgnoreCase("a")
				&& tagNameLastNm.equalsIgnoreCase("a")) {
			areResultsdisplayedLinks = true;
		} else {
			areResultsdisplayedLinks = false;
		}
		Web.getDriver().switchTo().defaultContent();
		return areResultsdisplayedLinks;
	}

	/**
	 * This method verifies if the user is redirected to the Employee Overview page when it clicks any search results hyperlinks
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyEmployeeredirectOverviewPage() throws InterruptedException {
		boolean isRedirected;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(searchResultsFirstName);	
		Thread.sleep(5000);
		Web.waitForElement(txtOverview);		
		if (Web.isWebElementDisplayed(txtOverview)) {
			isRedirected = true;
		}else{
			isRedirected = false;
		}
		Web.getDriver().switchTo().defaultContent();
		return isRedirected;
	}

	/**
	 * This testcase verifies the pagination of search results
	 * @return
	 */
	public boolean verifyPaginationforSearchResults() {

		if (Web.isWebElementDisplayed(this.errorMsgBox)) {
			this.errorMsgBox.click();
			this.linkDismissErrorMsgBox.click();
		}
		boolean isPaginationCorrect = true;
		switchToFrame();
		Web.waitForElement(searchResDropdown);
		select = new Select(searchResDropdown);
		WebElement selectedElement = select.getFirstSelectedOption();
		String selectedText = selectedElement.getText();
		int numberofRes = Integer.parseInt(selectedText);

		Web.waitForElements(searchResultsSSNTable);
		if (searchResultsSSNTable.size() > numberofRes) {
			isPaginationCorrect = false;
		}

		Web.getDriver().switchTo().defaultContent();
		return isPaginationCorrect;
	}

	/**
	 * It verifies the limit of search results
	 * @return
	 */
	public boolean verifyLimitofsearchResults() {
		if (Web.isWebElementDisplayed(this.errorMsgBox)) {
			this.errorMsgBox.click();
			this.linkDismissErrorMsgBox.click();
		}
		int resultsSize;
		boolean isLimitcorrect;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		resultsSize = searchResultsSSNTable.size();
		if (resultsSize <= 1000) {
			isLimitcorrect = true;
		} else {
			isLimitcorrect = false;
		}
		Web.getDriver().switchTo().defaultContent();
		return isLimitcorrect;

	}

	/**
	 * It takes the sorting option as the parameter and clicks the respective column and checks if the column is in sorted order
	 * @param sortOption
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifySortingofColumns(String sortOption)
			throws InterruptedException {
		if (Web.isWebElementDisplayed(this.errorMsgBox)) {
			this.errorMsgBox.click();
			this.linkDismissErrorMsgBox.click();
		}
		List<WebElement> listTobeSorted = null;
		WebElement sortElement = null;
		List<String> sortedList;
		List<String> afterSortList;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		switch (sortOption) {
		case "FirstName":
			listTobeSorted = listFirstName;
			sortElement = sortOptnFirstName;
			break;
		default:
			return false;
		}
		Web.clickOnElement(sortElement);
		Thread.sleep(3000);
		Web.waitForElements(listTobeSorted);
		afterSortList = getWebElementstoListofStrings(listTobeSorted);
		sortedList = getWebElementstoListofStrings(listTobeSorted);
		Collections.sort(sortedList);
		if (sortedList.equals(afterSortList)) {
			Reporter.logEvent(
					Status.PASS,
					"Verify the sorting on search results for "
							+ sortOption.toUpperCase(),
					"The search results are sorted correctly for "
							+ sortOption.toUpperCase() + " column", true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify the sorting on search results for "
							+ sortOption.toUpperCase(),
					"The search results are not sorted correctly for "
							+ sortOption.toUpperCase() + " column", true);
		}
		Web.getDriver().switchTo().defaultContent();
		return true;
	}

	/**
	 * Used to switch to the Employeesearch frame 
	 */
	public void switchToFrame() {
		Web.getDriver().switchTo().frame(employeeSearchFrame);
	}
	/**
	 * Used to switch to the Default content
	 */
	public void switchToDefaultContent() {
		Web.getDriver().switchTo().defaultContent();
	}

	/**
	 * It selects the plan for user from DB
	 * @param planNumwithDivQuery
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectPlanForUser(String[] planNumwithDivQuery,
			String username) throws Exception {
		queryResultSet = DB.executeQuery(planNumwithDivQuery[0],
				planNumwithDivQuery[1], "K_"+username);
		return queryResultSet;
	}

	/**
	 * It verifies the filter functionality by taking a text as the filter criteria
	 * @param searchText
	 */
	public void verifyFilterFunctionality(String searchText) throws Exception {
		switchToFrame();
		Web.setTextToTextBox(txtFilter, searchText);
		Web.waitForElement(searchResultsTablerow);

		if (Web.isWebElementDisplayed(searchResultsTablerow,true)) {
			if (StringUtils.containsIgnoreCase(searchResultsLastName.getText(),
					searchText)
					|| StringUtils.containsIgnoreCase(
							searchResultsFirstName.getText(), searchText)
					|| StringUtils.containsIgnoreCase(
							searchResultsMI.getText(), searchText)
					|| StringUtils.containsIgnoreCase(linkSSN.getText(),
							searchText)
					|| StringUtils.containsIgnoreCase(this.getWebElement("Link_EXT").getText(),
							searchText)
					|| StringUtils.containsIgnoreCase(linkEMP_ID.getText(),
							searchText)
					|| StringUtils.containsIgnoreCase(linkPART_ID.getText(),
							searchText)
					|| StringUtils.containsIgnoreCase(linkDivision.getText(),
							searchText)) {
				Reporter.logEvent(Status.PASS,
						"Check for the filter criteria on the search results",
						"The results are displayed according to the filter text : "
								+ searchText, true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check for the filter criteria on the search results",
						"The results are not displayed according to the filter text : "
								+ searchText, true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if the filter text has any matching texts in search results",
					"There are no matching text in the search results according to the search criteria provided :"
							+ "Search Text : " + searchText, true);
		}
		switchToDefaultContent();
	}

	/**
	 * This method selects default plan of the user
	 * @param Query
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public String selectDefaultPlan(String[] Query, String username)
			throws Exception {
		String defaultPlan = "";
		queryResultSet = DB.executeQuery(Query[0], Query[1], "K_" + username);
		while (queryResultSet.next()) {
			defaultPlan = queryResultSet.getString("DEFAULT_GA_ID");
		}
		return defaultPlan;
	}

	/**
	 * This method provides the SSN which is having extensions
	 * @param SSNExtQuery
	 * @param defaultPlan
	 * @return
	 * @throws Exception
	 */
	public String getSSNwithExtn(String[] SSNExtQuery, String defaultPlan)
			throws Exception {
		String SSN = "";
		queryResultSet = DB.executeQuery(SSNExtQuery[0], SSNExtQuery[1],
				defaultPlan);
		if (queryResultSet.next()) {
			SSN = queryResultSet.getString("SSN");
		}
		return SSN;
	}

	/**
	 * This method gives the masked ssn in search results for external users
	 * @throws InterruptedException
	 */
	public void verifySSNmasking() throws InterruptedException {
		searchEmployeeBySSN("");
		switchToFrame();
		System.out.println("Results are : "+searchResultsSSNTable);
		if (Web.isWebElementDisplayed(searchResultsSSNTable.get(0), true)) {
			if (searchResultsSSNTable.get(0).getText().contains("XX")) {
				Reporter.logEvent(Status.PASS, "Check if SSN are masked",
						"The SSN are masked for the specific plan", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Check if SSN are masked",
						"The SSN are not masked for the specific plan", true);
			}
		}
		switchToDefaultContent();
	}
/**
 * Method used to logout from application
 * @throws InterruptedException
 */
	public void logoutFromApplication() throws InterruptedException {
		if (Web.isWebElementDisplayed(linkLogout)) {
			linkLogout.click();
			Thread.sleep(2000);
		}
	}
/**
 * Method navigates the user to employee tab
 */
	public void gotoEmployeeTab() {
		Web.clickOnElement(tabEmployees);
	}
/**
 * It dismisses/closes if any error message is diplayed on screen
 * @throws InterruptedException
 */
	public void dismissErrorBox() throws InterruptedException {
		if (Web.isWebElementDisplayed(errorMsgBox)) {
			Web.clickOnElement(errorMsgBox);
			Web.clickOnElement(linkDismissErrorMsgBox);			
			Thread.sleep(2000);			
		}
	}
/**
 * This method selects the plans which is having division for the logged in user
 * @param planNumwithDivQuery
 * @param username
 * @throws Exception
 */
	public void selectPlanwithDiv(String[] planNumwithDivQuery, String username)
			throws Exception {
		int iCounter = 0;
		int countResultSet;
		queryResultSet = DB.executeQuery(planNumwithDivQuery[0],
				planNumwithDivQuery[1], "K_" + username);
		countResultSet = DB.getRecordSetCount(queryResultSet);
		while (queryResultSet.next()) {
			while (iCounter != countResultSet) {
				this.txtPlanNumberField.sendKeys(queryResultSet
						.getString(iCounter));
				iCounter++;
				btnGoPlanNumber.click();
				if (!errorMsgBox.isDisplayed()) {
					break;
				}
			}
		}
	}
	/**
	 * Method used to enter plan number in text box 
	 * @throws Exception
	 */

	public void EnterPlanNumber() throws Exception {
		Web.setTextToTextBox(txtPlanNumberField,
				Stock.GetParameterValue("planNumber"));
		Web.clickOnElement(btnGoPlanNumber);
	}

	public void setSSNmaskingForPlan(String[] setMaskingForPlanQuery,String planNumber) throws Exception {
		DB.executeUpdate(setMaskingForPlanQuery[0],setMaskingForPlanQuery[1],planNumber);
	}
	
	public String findPlanForUser(String[] findPlanNumberQuery,String username) throws SQLException
	{
		String maskedPlan = null;
		queryResultSet = DB.executeQuery(findPlanNumberQuery[0], findPlanNumberQuery[1], "K_"+username);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				maskedPlan = queryResultSet.getString("GA_ID");
				break;
			}
		}
		return maskedPlan;
	}
	
	/*
	 * This method fetches empid from DB from the first record
	 */
	public String getEmployeeIdFromDB() throws SQLException
	{
		String empId=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeID")[0], 
				Stock.getTestQuery("getEmployeeID")[1],"K_"+Stock.GetParameterValue("username"));
		
		while(queryResultSet.next())
		{
			empId = queryResultSet.getString("ER_ASSIGNED_ID");
			break;
		}
		
		Reporter.logEvent(Status.INFO, "Employee Id fetched from DB.", "'"+empId+"'", false);
		return empId;
		
	}
	
	/*
	 * This method fetches ssn from DB from the first record
	 */
	public String getSSNFromDB() throws SQLException
	{
		String ssn=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeID")[0], 
				Stock.getTestQuery("getEmployeeID")[1],"K_"+Stock.GetParameterValue("username"));
		
		while(queryResultSet.next())
		{
			ssn = queryResultSet.getString("SSN");
			break;
		}
		
		Reporter.logEvent(Status.INFO, "SSN fetched from DB.", "'"+ssn+"'", false);
		return ssn;
		
	}
	
	public String[] getSSNAndGaIdForSalaryEmp() throws SQLException
	{
		
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getIncomeDataEmployee")[0], 
				Stock.getTestQuery("getIncomeDataEmployee")[1], "K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next())
		{
			individual[0] = queryResultSet.getString("GA_ID");
			individual[1] = queryResultSet.getString("IND_ID");
			break;
		}
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getIndividual")[0], 
				Stock.getTestQuery("getIndividual")[1], individual[1]);
		while(queryResultSet.next())
		{
			individual[2] = queryResultSet.getString("SSN");
		}
		System.out.println(individual[0]+","+individual[1]+","+individual[2]);
		return individual;
		
	}
	
	
	public Map<String,String> getEmpInfoFromDB(String ssn) throws SQLException
	{
		Map<String,String> empInfo=new LinkedHashMap<String,String>();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeInfo")[0], 
				Stock.getTestQuery("getEmployeeInfo")[1],ssn);
	
		
		while(queryResultSet.next())
		{
			maritalStatus = queryResultSet.getString("MARITAL_STATUS");
			state1 = queryResultSet.getString("STATE_CODE");
			country1 = queryResultSet.getString("COUNTRY");
			empInfo.put("Last Name",queryResultSet.getString("LAST_NAME"));
			empInfo.put("First Name",queryResultSet.getString("FIRST_NAME"));
			empInfo.put("Middle Name",queryResultSet.getString("MIDDLE_NAME"));
			empInfo.put("Address",queryResultSet.getString("FIRST_LINE_MAILING"));
			empInfo.put("City",queryResultSet.getString("CITY"));
			empInfo.put("Zip",queryResultSet.getString("ZIP_CODE"));
			empInfo.put("Home Phone Area Code",queryResultSet.getString("HOME_PHONE_AREA_CODE"));
			empInfo.put("Home Phone Number",queryResultSet.getString("HOME_PHONE_NBR"));
			empInfo.put("Work Phone Area Code",queryResultSet.getString("WORK_PHONE_AREA_CODE"));
			empInfo.put("Work Phone Number",queryResultSet.getString("WORK_PHONE_NBR"));
			break;
		}
		empInfo.put("Marital Status",getMaritalStatus());
		empInfo.put("State",getState());
		empInfo.put("Country",getCountry());
		System.out.println(empInfo);
		return empInfo;
	}
	
	public String getMaritalStatus() throws SQLException
	{
		String str=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getMaritalStatus")[0], Stock.getTestQuery("getMaritalStatus")[1],maritalStatus);
		while(queryResultSet.next()){
		str = queryResultSet.getString("UPPER(RV_MEANING)");
	}
		return str;
	}
	public String getState() throws SQLException
	{
		String str=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getState")[0], Stock.getTestQuery("getState")[1],state1);
		while(queryResultSet.next()){
		str = queryResultSet.getString("RV_MEANING");
		}
		return str;
	}
	public String getCountry() throws SQLException
	{
		String str=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getCountry")[0], Stock.getTestQuery("getCountry")[1],country1);
		while(queryResultSet.next()){
		str = queryResultSet.getString("RV_MEANING");}
		return str;
	}
	
	
	
	/*
	 * This method verifies the employee id, Hire date, Officer 
	 */
	public void verifyEmploymentInfoANDLabels() throws Exception
	{
		
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		Web.waitForElements(fNLNMILink);
		Web.clickOnElement(fNLNMILink.get(0));
		Thread.sleep(2000);
		Web.waitForElement(empNameHeader);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		if(empNameHeader.getText().contains(fNLNMILink.get(0).getText()))
		{
			do{
				Web.waitForElement(partDetailTab);
			}while(!partDetailTab.isDisplayed());
			Web.clickOnElement(partDetailTab);
			if(Web.isWebElementDisplayed(empMntInfoHeader, true))
			{
				for(WebElement ele : listOfEmpmntInfoLabels)
				{
					m.put(ele.getText().trim(), ele.findElement(By.xpath("./../following-sibling::td//td")).getText().trim());
				}
				System.out.println(m);
			}
			Reporter.logEvent(Status.INFO, "Employee Id:", "'"+m.get("Employee ID:")+"'", false);
			Reporter.logEvent(Status.INFO, "Hire Date:", "'"+m.get("Hire date:")+"'", false);
			Reporter.logEvent(Status.INFO, "Officer:", "'"+m.get("Officer:")+"'", false);
			if(m.keySet().contains("Employee ID:")&&m.keySet().contains("Hire date:")&&m.keySet().contains("Term date:")
					&&m.keySet().contains("Officer:")&&m.keySet().contains("Highly compensated:")&&m.keySet().contains("Ownership %:")
					&&m.keySet().contains("Term date change:")&&m.keySet().contains("Term reason:")&&m.keySet().contains("Trade monitoring:")
					&&m.keySet().contains("Disability date:"))
			{
				Reporter.logEvent(Status.PASS, "Verify all the labels are displayed under Employment information.", "All the labels are displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify all the labels are displayed under Employment information.", "All the labels are not displayed.", true);
			}
			
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	/*
	 * This method validates the fields available on Employment Information modal window which is displayed when
	 * Edit link is clicked.
	 */
	public void updateEmploymentInfoModalWindow() throws Exception
	{
		//String updatedHireDate = Stock.GetParameterValue("hireDate");
		String updatedEmpID = Stock.GetParameterValue("empId");
		String updatedOfficer = Stock.GetParameterValue("Officer");
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		String ssn = empSSN.getText();
		String empName = empNameHeader.getText();
		Web.clickOnElement(empInfoEditLink);
		Thread.sleep(3000);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(empInfoEditFrame);
		CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().frame(empInfoEditFrame);
		Web.waitForElement(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+ssn+"')]")));
		Web.waitForElement(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+empName+"')]")));
		if(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+ssn+"')]")).getText().contains(ssn)
				&& Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+empName+"')]")).getText().contains(empName))
		{
			Reporter.logEvent(Status.PASS, "Match employee name and ssn on employment information modal window with Employee overview page.","Employee name and ssn match.",false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Match employee name and ssn on employment information modal window with Employee overview page.","Employee name and ssn doesn't match.",true);
		}
		//To update fields.........................................
		String dateString = m.get("Hire date:");
		DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date dt = dateFormat.parse(dateString);
		Calendar calendar = Calendar.getInstance();         
		calendar.setTime(dt);
		calendar.add(Calendar.DATE, 1);
		dateFormat.format(calendar.getTime());
		System.out.println("Date string is:"+dateFormat.format(calendar.getTime()));
		Web.setTextToTextBox(hireDate,dateFormat.format(calendar.getTime()));
		Thread.sleep(1500);
		termDate.clear();
		Web.setTextToTextBox(empId, updatedEmpID);
		Thread.sleep(1500);
		Select sel = new Select(officer);
		sel.selectByVisibleText(updatedOfficer);
		Thread.sleep(1500);
		
		Web.clickOnElement(empUpdateSaveBtn);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		Thread.sleep(7000);
		if(expEmployeeID.getText().equals(updatedEmpID)&&expHireDate.getText().equals(dateFormat.format(calendar.getTime()))
				&&expOfficer.getText().equals(updatedOfficer))
		{
			Reporter.logEvent(Status.PASS, "Verify Hire date,emp id and officer fields are updated.", "mentioned fields are updated.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify Hire date,emp id and officer fields are updated.", "mentioned fields are not updated.", true);
		}
		
		//Web.getDriver().switchTo().defaultContent();
		//Web.getDriver().switchTo().frame(employeeSearchFrame);
		try{
			Web.waitForElement(empInfoEditLink);
		Web.clickOnElement(empInfoEditLink);
		}
		catch(Exception e)
		{
			Web.clickOnElement(empInfoEditLink);
		}
		Web.waitForElement(empInfoEditFrame);
		Web.getDriver().switchTo().frame(empInfoEditFrame);
		
		//reset the fields...................................................
		Web.setTextToTextBox(hireDate,m.get("Hire date:"));
		Thread.sleep(1500);
		Web.setTextToTextBox(empId, m.get("Employee ID:"));
		Thread.sleep(1500);
		Select sel2 = new Select(officer);
		sel2.selectByVisibleText(m.get("Officer:"));
		Thread.sleep(1500);
		Web.clickOnElement(empUpdateSaveBtn);
		
		Web.waitForPageToLoad(Web.getDriver());
		Thread.sleep(2000);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		Web.clickOnElement(empInfoHistroyLink);
		CommonLib.waitForProgressBar();
		//Web.waitForElement(modalWindowCloseLink);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(Web.isWebElementDisplayed(modalWindowCloseLink, true))
		{
			Web.clickOnElement(modalWindowCloseLink);
			Web.waitForPageToLoad(Web.getDriver());
			CommonLib.waitForProgressBar();
			if(overwLabel.isDisplayed())
			{
				Reporter.logEvent(Status.PASS, "Click on close button on history window.","Page is navigated back to overview page.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Click on close button on history window.","Page is navigated back to overview page.", true);
			}
			
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Close link is displayed on employee history modal window.","Close link is not displayed.", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	/*
	 * This method validated if contact info section is displayed on overview page
	 */
	public void contactInFoSectionValidation() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		Web.waitForElements(fNLNMILink);
		Web.clickOnElement(fNLNMILink.get(0));
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		CommonLib.waitForProgressBar();
		CommonLib.waitForProgressBar();
		
		Web.waitForPageToLoad(Web.getDriver());
		Thread.sleep(3000);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(empNameHeader);
		Web.isWebElementDisplayed(empNameHeader, true);
		if(empNameHeader.getText().contains(fNLNMILink.get(0).getText()))
		{
			do{
				Web.waitForElement(partDetailTab);
				Thread.sleep(2000);
			}while(!partDetailTab.isDisplayed());
			Web.clickOnElement(partDetailTab);
		}
		if(contctInFoTable.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Verify contact details section is displayed.", "Contact details section is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify contact details section is displayed.", "Contact details section is not displayed.", true);
		}
		Web.getDriver().switchTo().defaultContent();
		
	}
	
	/*
	 * This method validates contact info labels
	 */
	public void contactInFoLabelValidation() throws Exception
	{
		String expLabels = Stock.GetParameterValue("expContactInfoLabels");
		List<String> expLabels1 = Arrays.asList(expLabels.split(","));
		Set<String> expLabels2 = new LinkedHashSet<String>(expLabels1);
		Set<String> actLabels = new LinkedHashSet<String>();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		for(WebElement ele : contactInFoLabels)
		{
			actLabels.add(ele.getText().replace(":", "").trim());
		}
		if(expLabels2.equals(actLabels))
		{
			Reporter.logEvent(Status.PASS, "Verify labels on contact info section:'"+expLabels2+"'", "actual labels displayed as:'"+actLabels+"'.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify labels on contact info section:'"+expLabels2+"'", "actual labels displayed as:'"+actLabels+"'.", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	
	
	/*
	 * This method validates contact info labels on edit mode(Modal Window)
	 */
	public void contactInFoValidationModalWindow() throws Exception
	{
		List<String> expLabels1 = Arrays.asList(Stock.GetParameterValue("expContactInputLabelsWindow").split(","));
		List<String> expLabels2 = Arrays.asList(Stock.GetParameterValue("expContactSelectLabelsWindow").split(","));
		List<String> actLabels1 = new LinkedList<String>();
		List<String> actLabels2 = new LinkedList<String>();
		boolean bool1 = false;
		boolean bool2 = false;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(contctEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.clickOnElement(contctEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		Web.waitForElement(contctInfoEditFrame);
		Web.getDriver().switchTo().frame(contctInfoEditFrame);
		for(WebElement ele1 : actCtcInputLabelsWindow)	
		{
			actLabels1.add(ele1.getText().replace("*","").replace(":","").trim());
		}
		actLabels1.removeAll(Arrays.asList(null,""));
		System.out.println(actLabels1);
		for(WebElement ele2 : actCtcSelectLabelsWindow)	
		{
			actLabels2.add(ele2.getText().replace("*","").replace(":","").trim());
		}
		actLabels2.removeAll(Arrays.asList(null,""));
		System.out.println(actLabels2);
		
		for(int i=0;i<expLabels1.size();i++)
		{
			if(expLabels1.contains(actLabels1.get(i))){
				bool1 = true;
			}
			
			else{
				bool1 = false;
				break;
			}
			
		}
		
		for(int i=0;i<expLabels2.size();i++)
		{
			if(expLabels2.contains(actLabels2.get(i))){
				bool2 = true;
			}
			
			else{
				bool2 = false;
				break;
			}
			
		}
		
		if(bool1 && bool2)
		{
			Reporter.logEvent(Status.PASS,"Expected Labels:"+expLabels1+" and "+expLabels2, "Actual Labels:"+actLabels1+" and "+actLabels2, false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Expected Labels:"+expLabels1+" and "+expLabels2, "Actual Labels:"+actLabels1+" and "+actLabels2, true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	@FindBy(id="IPB_CONTACT_3")
	private WebElement radioCheck;
	
	
	/*
	 * This method validates DB fields with UI fields for employee information
	 */
	public void validateContactInfoWithDB() throws Exception
	{
		Map<String,String> infoFromUI = new LinkedHashMap<String,String>();
		Map<String,String> infoFromDB = this.getEmpInfoFromDB(this.getSSNFromDB());
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(contctInfoEditFrame);
		Web.getDriver().switchTo().frame(contctInfoEditFrame);
		infoFromUI.put("Last Name",lName.getAttribute("value"));
		infoFromUI.put("First Name",fName.getAttribute("value"));
		infoFromUI.put("Middle Name",mName.getAttribute("value"));
		infoFromUI.put("Address",address.getAttribute("value"));
		infoFromUI.put("City",city.getAttribute("value"));
		infoFromUI.put("Zip",zip.getAttribute("value"));
		infoFromUI.put("Home Phone Area Code",homeAreaCode.getAttribute("value"));
		infoFromUI.put("Home Phone Number",homePhoneNumber.getAttribute("value"));
		infoFromUI.put("Work Phone Area Code",workAreaCode.getAttribute("value"));
		infoFromUI.put("Work Phone Number",workPhoneNumber.getAttribute("value"));
		infoFromUI.put("Marital Status",mStatus.getText());
		infoFromUI.put("State",state.getText());
		infoFromUI.put("Country",country.getText());
		System.out.println("Info from UI"+infoFromUI);
		System.out.println("Info from DB"+infoFromDB);
		Web.clickOnElement(radioCheck);
		Web.clickOnElement(save);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		if(infoFromUI.equals(infoFromDB))
		{
			Reporter.logEvent(Status.PASS, "Validate employee basic info with DB.", "Employee info is displayed correctly.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Validate employee basic info with DB.", "Employee info is not displayed correctly.", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	/*
	 * This method fetches the Employee having Beneficiary data
	 */
	public String[] getBeneficiaryEmployeeSSN() throws SQLException
	{
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getBeneficiaryEmployee")[0],
				Stock.getTestQuery("getBeneficiaryEmployee")[1],"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next())
		{
			str1[0] = queryResultSet.getString("SSN");
			str1[1] = queryResultSet.getString("ID");
			break;
		}
		return str1;
	}
	
	/*
	 * This method fetches the Employee having Beneficiary data
	 */
	public String getBeneficiaryEmployeeID() throws SQLException
	{
		String str=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getBeneficiaryEmployee")[0],
				Stock.getTestQuery("getBeneficiaryEmployee")[1],"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next())
		{
			str = queryResultSet.getString("ID");
			
			break;
		}
		return str;
	}
	
	public String employeeSearched = null;
	/*
	 * This method takes user to the employee overview page
	 */
	public void navigateToEmployeeOverViewPage() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(this.getWebElementasList("EmpLastNameLink").size()>0)
		{
			if(!fNLNMILink.get(0).isDisplayed())
			{
				Web.waitForElements(this.getWebElementasList("EmpLastNameLink"));
				Web.clickOnElement(this.getWebElementasList("EmpLastNameLink").get(0));
				employeeSearched = this.getWebElementasList("EmpLastNameLink").get(0).getText();
			}
			else
			{
				employeeSearched = fNLNMILink.get(0).getText();
				Web.clickOnElement(fNLNMILink.get(0));
			}
		}
		CommonLib.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(empNameHeader);
		if(Web.isWebElementDisplayed(empNameHeader, true))
		{
			Reporter.logEvent(Status.PASS, "Navigate to Employee overview page of employee:'"+empNameHeader.getText()+"'", "Employee overview page is displayed.",false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Navigate to Employee overview page of employee:'"+empNameHeader.getText()+"'", "Employee overview page is not displayed.",true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	
	/*
	 * This method takes user to employee's beneficiary page from Overview page
	 */
	public void navigateBeneficiaryPage() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(!accntLink.getAttribute("class").contains("active"))
		{
			Web.clickOnElement(accntLink);
		}
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.getDriver().switchTo().frame(beneFiciaryFrame);
		Web.waitForElement(benFiciary);
		if(Web.isWebElementDisplayed(benFiciary, true))
		{
			Web.clickOnElement(benFiciary);
		}
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.waitForElement(framecA);
		Web.getDriver().switchTo().frame(framecA);
		if(Web.isWebElementDisplayed(beneficiaryHeader, true))
		{
			Reporter.logEvent(Status.PASS, "Navigate to beneficiary page.", "Beneficiary page is displayed.",false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Navigate to beneficiary page.", "Beneficiary page is not displayed.",true);
		}
		Web.getDriver().switchTo().defaultContent();
		
	}

	@FindBy(xpath="//div[contains(text(),'Percent')]/preceding-sibling::div")
	private WebElement benName;
	
	/*
	 * This method validates the DB record with UI for Beneficiary
	 */
	public void validateBeneficiaryWithDB() throws Exception
	{
		
		Map<String,String> beneficiaryRecordsDB = new LinkedHashMap<String,String>();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getBeneficiary")[0], Stock.getTestQuery("getBeneficiary")[1],
				str1[1]);
		int numberOfBene = DB.getRecordSetCount(queryResultSet);
		int i = 1;
		while(queryResultSet.next())
				{
					if(i==1)
					{
						beneficiaryRecordsDB.put("Beneficiary effective date", queryResultSet.getString("Effective_date").trim());
						beneficiaryRecordsDB.put("Mailing Name", queryResultSet.getString("MAILING_NAME_1").trim());
						beneficiaryRecordsDB.put("Percent", queryResultSet.getString("PERCENT").trim());
						beneficiaryRecordsDB.put("SSN", queryResultSet.getString("SSN").trim());
						beneficiaryRecordsDB.put("Birth date", queryResultSet.getString("BIRTH_DATE").trim());
					}
					else
					{
						beneficiaryRecordsDB.put("Beneficiary effective date"+i, queryResultSet.getString("Effective_date").trim());
						beneficiaryRecordsDB.put("Mailing Name"+i, queryResultSet.getString("MAILING_NAME_1").trim());
						beneficiaryRecordsDB.put("Percent"+i, queryResultSet.getString("PERCENT").trim());
						beneficiaryRecordsDB.put("SSN"+i, queryResultSet.getString("SSN").trim());
						beneficiaryRecordsDB.put("Birth date"+i, queryResultSet.getString("BIRTH_DATE").trim());
					}
					i++;
				}
		
		System.out.println(beneficiaryRecordsDB);
		Map<String,String> beneficiaryDetails = new HashMap<String,String>();
		Web.waitForElement(framecA);
		Web.getDriver().switchTo().frame(framecA);
		for(int j=1;j<=numberOfBene;j++){
			String xpath = "//span["+j+"]//div[@class='benTableRow']/div[contains(text(),'effective date') or contains(text(),'Percent') or contains(text(),'SSN') or contains(text(),'Birth date')]";
			String xpathForBenName = "//span["+j+"]//div[contains(text(),'Percent')]/preceding-sibling::div";
			Thread.sleep(2000);
			List<WebElement> benDetails = Web.getDriver().findElements(By.xpath(xpath));
			WebElement benName = Web.getDriver().findElement(By.xpath(xpathForBenName));
			for(WebElement ele : benDetails)
			{
				String key = ele.getText().split(":")[0].trim();
				String value = ele.getText().split(":")[1].replace("%","").replace("-", "").trim();
				if(beneficiaryDetails.containsKey(key))
				{
					beneficiaryDetails.put(key+j, value);
				}
				else
				{
					beneficiaryDetails.put(key, value);
				}
			}
			if(beneficiaryDetails.containsKey("Mailing Name"))
			{
				beneficiaryDetails.put("Mailing Name"+j, benName.getText().trim());
			}
			else
			{
				beneficiaryDetails.put("Mailing Name", benName.getText().trim());
			}
		}
		System.out.println(beneficiaryDetails.entrySet());
		Web.getDriver().switchTo().defaultContent();
	
		for(Map.Entry<String,String> entry: beneficiaryRecordsDB.entrySet())
		{
			if(beneficiaryDetails.entrySet().contains(entry))
			{
				Reporter.logEvent(Status.PASS,"Verify beneficiary data with DB.", "data is correct.'"+entry+"'", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify beneficiary data with DB.", "Records are not  correct.'"+entry+"'", true);
			}
		}
		
	}
	
	
	/*
	 * This method verifies the Recently viewed functionality on employee overview page
	 */
	public String Verify_Recently_Viewed_Employee() throws Exception
	{
		String recentEmp = null;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		//CommonLib.waitForProgressBar();
		Web.clickOnElement(recentlyViewedLink);
		Web.waitForElement(recentlyViewedEmpTable);
		if(recentlyViewedEmpTable.isDisplayed())
		{
			Reporter.logEvent(Status.PASS,"Verify after clicking on recently viewed link,option collapses.", "reccently viewed option gets collapsed after clicking on Recently viewed link.", false);
			recentEmp = recentlyViewedEmployee.get(0).getText();
			String overviewEmp = empNameHeader.getText();
			System.out.println("Recently Viewed:"+recentEmp+" && "+"Overview Emp "+overviewEmp);
			if(recentlyViewedEmployee.get(0).getText().equalsIgnoreCase(empNameHeader.getText()))
			{
				Reporter.logEvent(Status.PASS, "Verify if recently viewed employee is same as of employee overview page.", "Recently viewed employee:'"+recentEmp+"' and overview employee:"+overviewEmp, false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify if recently viewed employee is same as of employee overview page.", "Recently viewed employee:'"+recentEmp+"' and overview employee:'"+overviewEmp+"'", true);
			}
			Web.getDriver().switchTo().defaultContent();
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify after clicking on recently viewed link,option collapses.", "reccently viewed option does not get collapsed after clicking on Recently viewed link.", true);
		}
		return recentEmp;
		
	}
	

	/*
	 * This method is used to navigate to employee detail page on overview page
	 */
	public void navigateToEmpDetailPage() throws Exception
	{
		CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(partDetailTab);
		Thread.sleep(2000);
		Web.clickOnElement(partDetailTab);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
	}
	

	/*
	 * This method is used to verify columns in salary section and Compensation heading and edit link 
	 */
	public void verifySalarySection() throws Exception
	{
		try{
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			String expSalarySecTitle= Stock.GetParameterValue("SalarySectionTitle");
			String actSalarySectionTitle = salaryHeader.getText();
			boolean isEditLinkDisplayed = salaryEditLink.isDisplayed();
			List<String> salaryHeadesrUI = new LinkedList<String>();
			List<String> expSalaryHeaders = Arrays.asList(Stock.GetParameterValue("ExpSalarySectionHeader").split(","));
			for(WebElement ele : salaryTableHeaders)
			{
				salaryHeadesrUI.add(ele.getText());
			}
			System.out.println(salaryHeadesrUI);
			if(actSalarySectionTitle.equalsIgnoreCase(expSalarySecTitle)&&isEditLinkDisplayed)
			{
				Reporter.logEvent(Status.PASS,"Verify salary section title is:'"+expSalarySecTitle+"' and edit link is displayed next to title.", "Salary section title is:'"+actSalarySectionTitle+"' and edit link is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify salary section title is:'"+expSalarySecTitle+"' and edit link is displayed next to title.", "Salary section title is:'"+actSalarySectionTitle+"' and edit link is not displayed.", true);
			}
			for(String expHeader : expSalaryHeaders)
			{
				if(salaryHeadesrUI.contains(expHeader))
				{
					Reporter.logEvent(Status.PASS,"Verify if label '"+expHeader+"' is displayed in salary section.", "mentioned label is displayed.", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL,"Verify if label '"+expHeader+"' is displayed in salary section.", "mentioned label is not displayed.", true);
				}
			}
			Web.getDriver().switchTo().defaultContent();
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FindBy(xpath="//font[contains(text(),'We were unable to process')]")
	private WebElement unexpErro;
	
	/*
	 * This method edit the salary information of an employee and save it.
	 */
	public void updateSalaryinfo() throws SQLException,Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(salaryEditLink);
		CommonLib.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(incomeEditFrame);
		Web.getDriver().switchTo().frame(incomeEditFrame);
		if(unexpErro.isDisplayed())
		{	
			Reporter.logEvent(Status.FAIL, "Check if unexpected error message is displayed.if yes,it may be due to data issue"
					+ "or may be issue.kindly check manually once.", "Unexpected error is displayed.", true);
			HomePage homePage = new HomePage();
			Web.getDriver().switchTo().defaultContent();
			Web.clickOnElement(Web.returnElement(homePage, "Home_Page_Logo"));
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().frame(employeeSearchFrame);
		}
		else
		{
			Web.waitForElement(salaryInput);
			String salary = salaryInput.getAttribute("value");
			int sal = Integer.parseInt(salary);
			int updateSal = sal+1;
			String updatedSal = Integer.toString(updateSal);
			String salaryDB=null;
			Web.setTextToTextBox(salaryInput, "-"+updatedSal);
			Web.clickOnElement(empUpdateSaveBtn);
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementsDisplayed(errorBoxes, true))
			{
				Reporter.logEvent(Status.PASS, "Enter salary in negative.", "error message is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Enter salary in negative.", "error message is displayed.", true);
			}
			Web.setTextToTextBox(salaryInput,updatedSal);
			Web.clickOnElement(empUpdateSaveBtn);
			Web.waitForPageToLoad(Web.getDriver());
			CommonLib.waitForProgressBar();
			Web.getDriver().switchTo().defaultContent();
			queryResultSet = DB.executeQuery(Stock.getTestQuery("getSalary")[0],Stock.getTestQuery("getSalary")[1],individual[1]);
			while(queryResultSet.next())
			{
				salaryDB = queryResultSet.getString("SAL_AMT");
			}
			System.out.println(salaryDB);
			if(salaryDB.equals(updatedSal))
			{
				Reporter.logEvent(Status.PASS, "Update salary and save.", "Salary has been updated and changes are saved.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Update salary and save.", "Salary has not been updated.", true);
			}
		}
	}
	
	@FindBy(xpath=".//*[@id='paycheckContributionMpwrDataTbl']//thead//th")
	private List<WebElement> paycheckHeaderColumn; 
	/*
	 * This method verifies paycheck contribution section
	 */
	public void verify_Paycheck_ContributionSection() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(paycheckContriHeader);
		if(paycheckContriHeader.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Check if Paycheck contribution section is displayed.", "Paycheck contribution "
					+ "section is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Check if Paycheck contribution section is displayed.", "Paycheck contribution "
					+ "section is displayed.", true);
		}
		
		for(WebElement header : paycheckHeaderColumn)
		{
			Reporter.logEvent(Status.INFO, "Check column:'"+header.getText()+"'", "Column header:'"+header.getText()+"' is displayed.", true);
		}
		
	}
	
	
	/*
	 * This method fetches the 
	 */
	public String ssnOfPayCheckContribution() throws SQLException
	{
		String ssn=null;
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getSSNForPaycheckContribution")[0],
				Stock.getTestQuery("getSSNForPaycheckContribution")[1]);
		while(queryResultSet.next())
		{
			ssn = queryResultSet.getString("SSN");
			break;
		}
		return ssn;
	}
	
	/*
	 * This method fetches the 
	 */
	public String[] SSNOfEmployeeWithTermDate() throws SQLException,Exception
	{
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeWithTermDate")[0],
				Stock.getTestQuery("getEmployeeWithTermDate")[1],"K_"+Stock.GetParameterValue("username"));
		Thread.sleep(3000);
		while(queryResultSet.next())
		{
			terminatedEmp[0] = queryResultSet.getString("SSN");
			System.out.println("SSN is:"+terminatedEmp[0]);
			terminatedEmp[1] = queryResultSet.getString("ID");
			System.out.println("Ind_Id is:"+terminatedEmp[1]);
			break;
		}
		return terminatedEmp;
	}

	public Map<String,String> beforeRehiring() throws SQLException
	{
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmpHiringDetails")[0], Stock.getTestQuery("getEmpHiringDetails")[1], terminatedEmp[1]);
		while(queryResultSet.next())
		{
			queryResultSet.first();
			employmentDetailsBeforeRehiring.put("EMP_TERMDATE",queryResultSet.getString("EMP_TERMDATE"));
			employmentDetailsBeforeRehiring.put("HIRE_DATE",queryResultSet.getString("HIRE_DATE"));
			employmentDetailsBeforeRehiring.put("ELIGIBILITY_IND",queryResultSet.getString("ELIGIBILITY_IND"));
			employmentDetailsBeforeRehiring.put("PARTICIPATION_DATE",queryResultSet.getString("PARTICIPATION_DATE"));
			employmentDetailsBeforeRehiring.put("PARTICIPATION_DATA_SOURCE",queryResultSet.getString("PARTICIPATION_DATE_SOURCE"));
			employmentDetailsBeforeRehiring.put("PARTICIPATION_DATA_SOURCE",queryResultSet.getString("ELIGIBILITY_DATE"));
			break;
		}
		System.out.println(employmentDetailsBeforeRehiring);
		return employmentDetailsBeforeRehiring;
		
	}
	
	public void AfterRehiring() throws Exception,SQLException
	{
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmpHiringDetails")[0], Stock.getTestQuery("getEmpHiringDetails")[1], terminatedEmp[1]);
		while(queryResultSet.next())
		{
			queryResultSet.first();
			employmentDetailsAfterRehiring.put("EMP_TERMDATE",queryResultSet.getString("EMP_TERMDATE"));
			employmentDetailsAfterRehiring.put("HIRE_DATE",queryResultSet.getString("HIRE_DATE"));
			employmentDetailsAfterRehiring.put("ELIGIBILITY_IND",queryResultSet.getString("ELIGIBILITY_IND"));
			employmentDetailsAfterRehiring.put("PARTICIPATION_DATE",queryResultSet.getString("PARTICIPATION_DATE"));
			employmentDetailsAfterRehiring.put("PARTICIPATION_DATA_SOURCE",queryResultSet.getString("PARTICIPATION_DATE_SOURCE"));
			employmentDetailsAfterRehiring.put("PARTICIPATION_DATA_SOURCE",queryResultSet.getString("ELIGIBILITY_DATE"));
			break;
		}
		System.out.println(employmentDetailsAfterRehiring);
		if(employmentDetailsAfterRehiring.get("EMP_TERMDATE")==null
			&&!employmentDetailsBeforeRehiring.get("HIRE_DATE").equals(employmentDetailsAfterRehiring.get("HIRE_DATE"))
		    &&!employmentDetailsBeforeRehiring.get("ELIGIBILITY_IND").equals(employmentDetailsAfterRehiring.get("ELIGIBILITY_IND"))
		    &&employmentDetailsBeforeRehiring.get("PARTICIPATION_DATE").equals(employmentDetailsAfterRehiring.get("HIRE_DATE"))
		    &&!employmentDetailsBeforeRehiring.get("PARTICIPATION_DATA_SOURCE").equals(employmentDetailsAfterRehiring.get("PARTICIPATION_DATE_SOURCE"))
		    &&employmentDetailsBeforeRehiring.get("ELIGIBILITY_DATE").equals(employmentDetailsAfterRehiring.get("ELIGIBILITY_DATE")))
		{
			Reporter.logEvent(Status.PASS, "Compare the changes after rehiring employee as below:<br>Term Date=null"
					+ "<br>Hire Date=new Hire date<br>Eligibility code remains same<br>Participation date changes to Rehire date"
					+ "<br>Participation datesource changes<br>Eligibility date does not changed.","All the said changes are observed", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Compare the changes after rehiring employee as below:<br>Term Date=null"
					+ "<br>Hire Date=new Hire date<br>Eligibility code remains same<br>Participation date changes to Rehire date"
					+ "<br>Participation datesource changes<br>Eligibility date does not changed.","All the said changes are not observed", true);
		}
		
	}
	
	
	/*
	 * This method updates the hire date and removes the term date in process of rehiring of an employee
	 */
	public void rehireEmployee() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(empInfoEditLink);
		Web.clickOnElement(empInfoEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		Web.waitForElement(empInfoEditFrame);
		Web.getDriver().switchTo().frame(empInfoEditFrame);
		CommonLib.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(upDateEmploymentHeader);
		if(upDateEmploymentHeader.isDisplayed())
		{
			String termDate_1 = termDate.getAttribute("value");
			DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
			Date dt = dateFormat.parse(termDate_1);
			Calendar calendar = Calendar.getInstance();         
			calendar.setTime(dt);
			calendar.add(Calendar.DATE, 1);
			String newHireDate = dateFormat.format(calendar.getTime());
			System.out.println("Date string is:"+dateFormat.format(calendar.getTime()));
			hireDate.clear();
			Web.setTextToTextBox(hireDate,newHireDate);
			termDate.clear();
			Web.clickOnElement(save);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			CommonLib.waitForProgressBar();
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.waitForElement(hireDateReadOnly);
			if(hireDateReadOnly.getText().equals(newHireDate))
			{
				Reporter.logEvent(Status.PASS,"Delete the term date and enter new hire date.", "New hire date has been entered.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Delete the term date and enter new hire date.", "New hire date has not been entered.", true);
			}
			Web.getDriver().switchTo().defaultContent();
			
		}
		
	}

public String getSSNForMultiplePlans() throws SQLException
{
	String ssn=null;
	
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeWithMultiplePlan")[0],
			Stock.getTestQuery("getEmployeeWithMultiplePlan")[1],"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		ssn = queryResultSet.getString("SSN");
		break;
	}
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeWithMultiplePlan")[0],
			Stock.getTestQuery("getEmployeeWithMultiplePlan")[1],"K_"+Stock.GetParameterValue("username"));
	
	
	
	
	
	return ssn;
}
	


public void verifyOverviewScreenElements() throws Exception,SQLException
{
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.waitForElement(overviewLabel);
	if(overviewLabel.isDisplayed())
	{
		Reporter.logEvent(Status.PASS,"Verify Overview label is displayed.", "Overview label is displayed.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL,"Verify Overview label is displayed.", "Overview label is displayed.", true);
	}
	
	if(nameLabel.isDisplayed()&&ssnLabel.isDisplayed()&&dobLabel.isDisplayed()||empIDLabel.isDisplayed())
	{
		Reporter.logEvent(Status.PASS,"Verify labels name,SSN,DOB end empid(if applicable) is displayed under overview section.", "Mentioned labels are displayed.", false);
	}
	else
	{
		Reporter.logEvent(Status.PASS,"Verify labels name,SSN,DOB end empid(if applicable) is displayed under overview section.", "Mentioned labels are not displayed.", true);
	}
	
	String firstName=null,lastName=null,middleName=null,birthDate=null;
	String fullName=null;
	System.out.println(WordUtils.capitalizeFully(empNameHeader.getText().trim()));
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getIndividualDetails")[0],Stock.getTestQuery("getIndividualDetails")[1],
			WordUtils.capitalizeFully(empNameHeader.getText().trim()),"%"+empSSN.getText().replace("XXX-XX-", "").trim());
	while(queryResultSet.next())
	{
		firstName = queryResultSet.getString("FIRST_NAME");
		lastName = queryResultSet.getString("LAST_NAME");
		middleName = queryResultSet.getString("MIDDLE_NAME");
		birthDate = queryResultSet.getString("BIRTH_DATE");
		break;
	}
	if(middleName==null)
	{
		fullName = firstName+" "+lastName;
	}
	else{
		fullName = firstName+" "+middleName+" " +lastName;
	}
	System.out.println(fullName);
	if(fullName.equals(empName.getText()))
	{
		Reporter.logEvent(Status.PASS,"Verify name format as: FirstName middleName lastName.", "Name is in mentioned format.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL,"Verify name format as: FirstName middleName lastName.", "Name is not in mentioned format.", true);
	}
	
	//String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
	String expression = "\\d{4}";
	Pattern pattern = Pattern.compile(expression);
	Matcher matcher = pattern.matcher(empSSN.getText().replace("XXX-XX-", "").trim());
	if(matcher.matches()&&empSSN.getText().contains("XXX-XX-"))
	{
		Reporter.logEvent(Status.PASS, "Verify SSN is in format xxx-xx-0000 if masked.", "SSN is found to be in format:xxx-xx-0000", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify SSN is in format xxx-xx-0000.", "SSN is not found to be in format:xxx-xx-0000", true);
	}
	
	if(birthDate.equals(dobValue.getText().trim()))
	{
		Reporter.logEvent(Status.PASS, "Verify DOB format as MM/DD/YYYY.", "DOB format is:MM/DD/YYYY", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify DOB format as MM/DD/YYYY.", "DOB format is not in format:MM/DD/YYYY", true);
	}
	if(!empIDLabel.isDisplayed())
	{
		if(!empIdValue.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Emp Id value is not displayed provided emp id label is not displayed.", "Emp Id value is not displayed if label is not displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Emp Id value is not displayed provided emp id label is not displayed.", "Emp Id value is displayed if label is not displayed.", true);
		}
	}
		if(partDetailTab.isDisplayed()&&accntDetailsTab.isDisplayed()&&employeeWebButton.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Verify account detail,employee detail and employee web button is displayed.", "Account detail,employee detail and Employee web button is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify account detail,employee detail and employee web button is displayed.", "Account detail,employee detail and Employee web button is not displayed.", true);
		}
	String parentWindow = Web.getDriver().getWindowHandle();
	System.out.println("Parent Window:"+parentWindow);
	Web.waitForElement(print);
	Web.clickOnElement(print);
	Set<String> printWindow = Web.getDriver().getWindowHandles();
	System.out.println(printWindow);
	for(String childWindow:printWindow )
	{
		if(!parentWindow.equals(childWindow))
		{
			System.out.println("This is inside IF.");
			Web.getDriver().switchTo().window(childWindow);
			Web.waitForElement(printPriviewWindowHeader);
			if(printPriviewWindowHeader.getText().contains("THIS IS A PRINT PREVIEW"))
			{
				Reporter.logEvent(Status.PASS, "Verify after clicking on print link,a print preview page is displayed.", "Print preview page is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify after clicking on print link,a print preview page is displayed.", "Print preview page is not displayed.", true);
			}
		}
		
	}
	Web.getDriver().close();
	Web.getDriver().switchTo().window(parentWindow);
	Web.getDriver().switchTo().defaultContent();
	
}

public String getMultiPlanEmployee() throws SQLException
{
	String ssn=null;
	//planNameList = new ArrayList<String>();
	planNumberList = new ArrayList<String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmployeeWithMultiplePlan")[0],Stock.getTestQuery("getEmployeeWithMultiplePlan")[1],"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		ssn = queryResultSet.getString("SSN");
		break;
	}
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getPlanNamesForSSN")[0],Stock.getTestQuery("getPlanNamesForSSN")[1],ssn);
	while(queryResultSet.next())
	{
		//planNameList.add(queryResultSet.getString("NAME"));
		planNumberList.add(queryResultSet.getString("GA_ID"));
	}
	System.out.println(planNumberList);
	return ssn;
}



public List<String> getQNTTypeIndividuals() throws Exception,SQLException
{
	qntTypeIndividuals = new ArrayList<String>();
	partDetails = new HashMap<String,String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getQDROIndividual")[0],Stock.getTestQuery("getQDROIndividual")[1],
			"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		String ssnQ = queryResultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(ssnQ);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElements(fNLNMILink);
		if(fNLNMILink.size()>0)
		{
			qntTypeIndividuals.add(ssnQ);
			qdroPart = queryResultSet.getString("OWNERSHIP_IND");
			Web.getDriver().switchTo().defaultContent();
			break;
		}
	}
	
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getTransferIndividual")[0],Stock.getTestQuery("getTransferIndividual")[1],
			"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		String ssnT = queryResultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(ssnT);
		System.out.println("after Transfer emp search.");
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElements(fNLNMILink);
		if(fNLNMILink.size()>0)
		{
			qntTypeIndividuals.add(ssnT);
			transferPart = queryResultSet.getString("OWNERSHIP_IND");
			Web.getDriver().switchTo().defaultContent();
			break;
		}
	}
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getNormalIndividual")[0],Stock.getTestQuery("getNormalIndividual")[1],
			"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		String ssnN = queryResultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(ssnN);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElements(fNLNMILink);
		if(fNLNMILink.size()>0)
		{
			qntTypeIndividuals.add(ssnN);
			normalPart = queryResultSet.getString("OWNERSHIP_IND");
			partDetails.put("BIRTH_DATE",queryResultSet.getString("BIRTH_DATE"));
			partDetails.put("MARITAL_STATUS",queryResultSet.getString("MARITAL_STATUS"));
			partDetails.put("SEX",queryResultSet.getString("SEX"));
			partDetails.put("LANGUAGE_CODE",queryResultSet.getString("LANGUAGE_CODE"));
			Web.getDriver().switchTo().defaultContent();
			break;
		}
	}
	return qntTypeIndividuals;
}


	
/*
 * this methos verifies the Basic Information section on employee overview page
 */
public void verifyBasicInformationOnOverviewPage() throws Exception
{
	basicInfoMapBeforeChanges = new HashMap<String,String>();
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.waitForElement(basicinfoHeader);
	for(int i=0;i<basicInfoLabels.size();i++)
	{
		String key = basicInfoLabels.get(i).getText().replaceAll(":","").trim();
		String value = basicInfoValues.get(i).getText().trim();
		basicInfoMapBeforeChanges.put(key, value);
	}
	Set<String> actLabels = basicInfoMapBeforeChanges.keySet();
	if(actLabels.contains("Account status")&&actLabels.contains("SSN")&&actLabels.contains("Participant ID")
			&&actLabels.contains("Birth date")&&actLabels.contains("Death date")&&actLabels.contains("Gender")
			&&actLabels.contains("Marital status")&&actLabels.contains("Language")&&actLabels.contains("PIN effective date")
			&&actLabels.contains("Account type"))
	{
		Reporter.logEvent(Status.PASS, "Verify basic info categories as below on employee detail page:<br>"
				+ "Account status,SSN,Participant ID,Birth date,Death date,Gender,Marital status,Language,PIN effective date,Account type", "All mentioned categories are displayed for employee details.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify basic info categories as below on employee detail page:<br>"
				+ "Account status,SSN,Participant ID,Birth date,Death date,Gender,Marital status,Language,PIN effective date,Account type", "All mentioned categories are not displayed for employee details.", true);
	}
	
	String accounttype = 	basicInfoMapBeforeChanges.get("Account type");
	if(accounttype.equals(QNTMeaning(qdroPart))||accounttype.equals(QNTMeaning(normalPart))||accounttype.equals(QNTMeaning(transferPart)))
	{
		Reporter.logEvent(Status.PASS, "Verify Account type is dislayed accordingly."
				+ "<br>for QDRO participant,Account type is 'QUALIFIED DOMESTIC RELATIONS AGREEMENT'"
				+ "<br>for Normal participant,'NORMAL PARTICIPANT AGREEMENT'"
				+ "<br>for Takeover/Transfer employee,'TAKEOVER/TRANSFER'.", "Account type is:"+accounttype, false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify Account type is dislayed accordingly."
				+ "<br>for QDRO participant,Account type is 'QUALIFIED DOMESTIC RELATIONS AGREEMENT'"
				+ "<br>for Normal participant,'NORMAL PARTICIPANT AGREEMENT'"
				+ "<br>for Takeover/Transfer employee,'TAKEOVER/TRANSFER'.", "Account type is not displayed accordingly.", true);
	}
	//basicInfoMapBeforeChanges.clear();
	Web.getDriver().switchTo().defaultContent();
}

public String QNTMeaning(String accountTypeCode) throws Exception
{
	Map<String,String> qnt = new HashMap<String,String>();
	qnt.put("Q","Qualified Domestic Relations Agreement");
	qnt.put("N","Normal Participant Agreement");
	qnt.put("T","Takeover/transfer");
	return qnt.get(accountTypeCode);
}




public void verifyColumnsOfPlanListsection()
{
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.waitForElement(overviewLabel);
	boolean isDisplayed = false;
	if(planNumberColumn.isDisplayed()&&planNameColumn.isDisplayed()&&balanceColumn.isDisplayed())
	{
		Reporter.logEvent(Status.PASS, "Verify Plan number,Plan name,Balance and Details columns are displayed under overview section.",
				"Plan number,Plan name,Balance and Details columns are displayed.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify Plan number,Plan name,Balance and Details columns are displayed under overview section.",
				"Either of the columns are not displayed.", true);
	}
	for(WebElement planNumber:planNumbersUI)
	{
			if(planNumberList.contains(planNumber.getText().trim()))
				{
					isDisplayed = true;
				}
			else
			{
				isDisplayed = false;
				break;
			}
	}
		if(isDisplayed)
	Reporter.logEvent(Status.PASS, "Chekc all the plans are displayed for the employeee on overview page.", "All the plan are displayed.", false);
	else
	Reporter.logEvent(Status.FAIL, "Chekc all the plans are displayed for the employeee on overview page.", "All the plan are not displayed.", true);
}
	

public void verifySelectedPlanHeaderAndHighlightedPlan()
{
	if(selectedPlanHeader.getText().contains(highlightedPlannumber.getText()))
	{
		Reporter.logEvent(Status.PASS, "Verify selected plan and highlighted plan in lan list are same.", "Selected plan and highlighted selected plan are same.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify selected plan and highlighted plan in lan list are same.", "Selected plan and highlighted selected plan are not same.", true);
	}
	for(WebElement plan : planListOnOveriewPage)
	{
		if(!plan.getText().trim().equals(highlightedPlannumber.getText().trim()))
		{
			Web.clickOnElement(plan);
			CommonLib.waitForProgressBar();
			Web.waitForElement(highlightedPlannumber);
			if(selectedPlanHeader.getText().contains(highlightedPlannumber.getText()))
			{
				Reporter.logEvent(Status.PASS, "Select a different plan and verify plan header at top is updated with highlighted plan.", "Plan header is updated with highlighted plan.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Select a different plan and verify plan header at top is updated with highlighted plan.", "Plan header is not updated with highlighted plan.", true);
			}
			break;
		}
	}
}



public void verifyTotalBalance()
{
	if(totalBalance.get(0).getText().equalsIgnoreCase("Total balance"))
	{
		Reporter.logEvent(Status.PASS, "Verify Total Balance section is displayed.", "Total balance section is displayed.", false);
		Reporter.logEvent(Status.INFO, "Verify total balance for selected employee '"+empName.getText()+"'.", "Total balacne is :'"+totalBalance.get(1).getText()+"'.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify Total Balance section is displayed.", "Total balance section is not displayed.", true);
	}
}


public boolean verifyViewPageForAPlanHavingBalance() throws Exception
{
	String xpath = "./ancestor::*[2]/following-sibling::div";
	boolean isBalanceExist = false;
	List<String> moneySourceHeaders = new ArrayList<String>();
	for(WebElement balance : balanceList)
	{
		if(!balance.getText().equals("N/A"))
		{
			String bal = balance.getText();
			Thread.sleep(2000);
			Web.clickOnElement(balance.findElement(By.xpath("./../following-sibling::td/button")));
			CommonLib.waitForProgressBar();
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(viewAccountDetailFrame);
			Web.waitForElement(accountDetailHeader);
			WebElement expandCollapseMoneySource = moneySource.findElement(By.tagName("i"));
			WebElement expandCollapseInvestment = investment.findElement(By.tagName("i"));
			if(bal.contains(balanceOnViewAccountPage.getText()))
			{
				Reporter.logEvent(Status.PASS,"Verify balance displayed on overview page under<br>"
						+ "Balance column and balance displayed when clicking on View button, both should match.",""
								+ "Both balance matches.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify balance displayed on overview page under<br>"
						+ "Balance column and balance displayed when clicking on View button, both should match.",""
								+ "Both balance do not match.", true);
			}
			if(moneySource.isDisplayed()&&investment.isDisplayed())
			{
				String isInvestmentcollapsed = investment.findElement(By.xpath("./..")).getAttribute("aria-expanded");
				WebElement moneySourceData = moneySource.findElement(By.xpath(xpath));
				WebElement investmentData = investment.findElement(By.xpath(xpath));
				if(isInvestmentcollapsed.equals("true"))
				{
					Web.clickOnElement(expandCollapseInvestment);
					Web.waitForElement(moneySourceData);
				}
				if(moneySourceData.isDisplayed()&&investmentData.isDisplayed())
				{
					Reporter.logEvent(Status.PASS, "Verify Money Sources and Investments sections.", "All money sources and Investments are displayed with data.", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Money Sources and Investments sections.", "All money sources and Investments are not displayed.", true);
				}
				
				for(WebElement header : moneySrcHeaders)
				{
					moneySourceHeaders.add(header.getText().trim());
				}
				if(moneySourceHeaders.contains("Description")&&moneySourceHeaders.contains("Balance")&&moneySourceHeaders.contains("Vested Percent:")
					&&moneySourceHeaders.contains("Vested Balance:")&&moneySource.getText().trim().equals("Money Sources")&&expandCollapseMoneySource.isDisplayed())
				{
					Reporter.logEvent(Status.PASS,"1.Verify header with label Money sources."
							+ "<br>2.Expand and collapse icon is displayed."
							+ "<br>3.Labels Description,Balance,Vested Percent and Vested Balance are displayed.", ""
									+ "1.header with label money source is displayed."
									+ "<br>2.Expand and collapse icon is displayed."
									+ "<br>3.Labels Description,Balance,Vested Percent,Vested Balance are displayed.", false);
				}
				else
				{
					Reporter.logEvent(Status.PASS,"1.Verify header with label Money sources."
							+ "<br>2.Expand and collapse icon is displayed."
							+ "<br>3.Labels Description,Balance,Vested Percent and Vested Balance are displayed.", "Either label Money source is not displayed"
									+ "<br>or expand/collapse icon is not displayed"
									+ "<br>or labels Description,Balance,Vested Balance,Vested Percent are not displayed.", true);
				}
				if(totalForMoneySources.get(2).getText().equals("Total"))
				{
					Reporter.logEvent(Status.PASS, "Verify Total is displayed for Money source section.", "Total is displayed for Money source section.", false);
					Reporter.logEvent(Status.INFO, "Check for Balance.", "Balance is:"+totalForMoneySources.get(3).getText(), false);
					Reporter.logEvent(Status.INFO, "Check for Vested Balance.", "Vested Balance is:"+totalForMoneySources.get(4).getText(), false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Total is displayed for Money source section.", "Total is not displayed for Money source section.", true);
				}
			}
			isBalanceExist = true;
			break;
		}
		else
		{
			isBalanceExist = false;
		}
	}
	return isBalanceExist;
}

	public void verifyInvestmentSection()
	{
		String xpath = "./ancestor::*[2]/following-sibling::div";
		WebElement investmentData = investment.findElement(By.xpath(xpath));
		WebElement expandCollapseIcon = investment.findElement(By.tagName("i"));
		List<String> investmentHeadersAct = new ArrayList<String>();
		if(investment.isDisplayed())
		{
			String iscollapsed = investment.findElement(By.xpath("./..")).getAttribute("aria-expanded");
			if(iscollapsed.equals("false"))
			{
				Web.clickOnElement(expandCollapseIcon);
				Web.waitForElement(investmentData);
			}
			
			for(WebElement header : investmentHeaders)
			{
				investmentHeadersAct.add(header.getText().trim());
			}
			System.out.println("Investment headers:"+investmentHeadersAct);
			if(investmentHeadersAct.contains("Investment option")&&investmentHeadersAct.contains("Unit/Shares effective date")&&investmentHeadersAct.contains("Unit/Share price")
				&&investmentHeadersAct.contains("Unit/Shares owned")&&investmentHeadersAct.contains("Balance")&&investment.getText().contains("Investments")&&expandCollapseIcon.isDisplayed())
			{
				Reporter.logEvent(Status.PASS,"1.Verify header with label 'Investments'."
						+ "<br>2.Expand and collapse icon is displayed."
						+ "<br>3.Labels Investment option,Unit/Shares effective date,Unit/Share price,Unit/Shares owned,Balance are displayed.", ""
								+ "1.header with label Investments is displayed."
								+ "<br>2.Expand and collapse icon is displayed."
								+ "<br>3.Mentioned labels are displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"1.Verify header with label 'Investments'."
						+ "<br>2.Expand and collapse icon is displayed."
						+ "<br>3.Labels Investment option,Unit/Shares effective date,Unit/Share price,Unit/Shares owned,Balance are displayed.", "either"
								+ "label Investment is not displayed"
								+ "<br>or expand/collapse icon is not displayed."
								+ "<br>or mentioned labels are not displayed.", true);
			}
			if(totalForInvetsment.get(0).getText().equals("Total"))
			{
				Reporter.logEvent(Status.PASS,"Verify total is displayed for Investments.", "Total is displayed.", false);
				Reporter.logEvent(Status.INFO,"Balance for Investments section.", "Balance for investments section is:"+totalForInvetsment.get(1).getText(), false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Verify total is displayed for Investments.", "Total is not displayed.", true);
			}
			
		
	}
		
	}
	
	
	public void verifyCalendarIcon()
	{
		boolean isCalenderDisplayed = false;
		if(calendarIcon.isDisplayed())
		isCalenderDisplayed= true;
		if(isCalenderDisplayed)
		{
			Reporter.logEvent(Status.PASS,"Verify Calendar icon is displayed.", "Calendar icon is dislayed.", false);
			Web.clickOnElement(calendarIcon);
			Web.waitForElement(asOfDate);
			if(asOfDate.isDisplayed())
			{
				Reporter.logEvent(Status.PASS,"Click on calendar icon and verify calendar to pick date is displayed.", "Calendar to pick date is displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Click on calendar icon and verify calendar to pick date is displayed.", "Calendar to pick date is not displayed.", true);
			}
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify Calendar icon is displayed.", "Calendar icon is not dislayed.", true);
		}
		
	}
	
	public void returnToEmployeeOverview()
	{
		Web.clickOnElement(returnToEmployeeOverview);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(overviewLabel.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Click on 'Return to employee overview link'.", "Employee overview page is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Click on 'Return to employee overview link'.", "Employee overview page is not displayed.", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
	
	

	
	/*
	 * This method verifies the Edit and save functionality for Employee Basic Info section on overview page.
	 * THis also verifies the fields to be editable on basic info modal window
	 */
	public void verifyBasicInfoModalWindow() throws Exception
	{
		List<String> actModalInfoLabels = new LinkedList<String>();
		List<String> expModalInfoLabels = Arrays.asList(Stock.GetParameterValue("ExpectedLabels").split(","));
		boolean islabelExist = false;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(basicInfoEdit);
		CommonLib.waitForProgressBar();
		//Thread.sleep(3000);
		Web.waitForElement(basicInfoEditFrame);
		if(closeIconBasicInfoEditWindow.isDisplayed())
		{
			Reporter.logEvent(Status.PASS,"Verify cross icon is displayed on top right of modal window.", "Cross icon is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify cross icon is displayed on top right of modal window.", "Cross icon is not displayed.", true);
		}
		Web.getDriver().switchTo().frame(basicInfoEditFrame);
		String ssn = ssnHeaderOnModalWindow.getText().split(":")[1].replace("-","").trim();
		String name = nameHeaderOnModalWindow.getText().split(":")[1].trim();
		if(ssn.equals(qntTypeIndividuals.get(2))&&name.contains(employeeSearched))
		{
			Reporter.logEvent(Status.PASS, "Verify SSN and Employee Name on modal window.", "Employee name:'"+name+"' and SSN:'"+ssn+"' are displayed correctly.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify SSN and Employee Name on modal window.", "Employee name and SSN are not displayed correctly.", true);
		}
		for(WebElement basicInfoLabel : modalWindowBasicInfoLabels)
		{
			String label = basicInfoLabel.getText().replace("*","").replace(":","").trim(); 
			actModalInfoLabels.add(label);
		}
		for(WebElement contactInfoLabel : modalWindowContactInfoLabels)
		{
			String label = contactInfoLabel.getText().replace("*","").replace(":","").trim(); 
			actModalInfoLabels.add(label);
		}
		System.out.println(actModalInfoLabels);
		System.out.println(expModalInfoLabels);
		for(String expLabel : expModalInfoLabels)
		{
			if(actModalInfoLabels.contains(expLabel))
			{
				islabelExist = true;
			}
			else
			{
				islabelExist = false;
				break;
			}
		}
		
		if(islabelExist&&incomingRollAssitance.isDisplayed())
		{
			Reporter.logEvent(Status.PASS, "Verify fields on Basic info modal window.", "All Expected Fields '"+actModalInfoLabels+"' are displayed on modal window.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify fields on Basic info modal window.", "All Expected Fields are not displayed on modal window.", true);
		}
	}
	
	


/*
 * This method edit the few basic info and revert them back to previous values	
 */
public void editBasicInfoAndSave() throws Exception
{
	String birthDate_ = basicInfoMapBeforeChanges.get("Birth date");	
	String maritalStatus = basicInfoMapBeforeChanges.get("Marital status");
	String gender_ = basicInfoMapBeforeChanges.get("Gender");
	String language_ = basicInfoMapBeforeChanges.get("Language");
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.getDriver().switchTo().frame(basicInfoEditFrame);
	Web.waitForElement(birthDate);
	DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
	Date dt = dateFormat.parse(birthDate.getAttribute("value"));
	Calendar calendar = Calendar.getInstance();         
	calendar.setTime(dt);
	calendar.add(Calendar.DATE, 5);
	String selectedBirthDate = dateFormat.format(calendar.getTime());
	System.out.println("Date string is:"+dateFormat.format(calendar.getTime()));
	//update birth date
	Web.setTextToTextBox(birthDate,selectedBirthDate);
	
	//Update marital status
	Select selMaritalStatus = new Select(maritalSts);
	if(checkMaritalStatus.getText().equals("MARRIED"))
		selMaritalStatus.selectByVisibleText("SINGLE");
	else if(checkMaritalStatus.getText().equals("SINGLE"))
		selMaritalStatus.selectByVisibleText("MARRIED");
	else if(checkMaritalStatus.getText().equals("DIVORCED"))
		selMaritalStatus.selectByVisibleText("MARRIED");
	else
		selMaritalStatus.selectByVisibleText("MARRIED");
	String selectedMstatus = checkMaritalStatus.getText();
	//update Gender
	Select selectGender = new Select(gender);
	if(checkGender.getText().equals("FEMALE"))
		selectGender.selectByVisibleText("MALE");
	else
		selectGender.selectByVisibleText("FEMALE");
	String selectedGender = checkGender.getText();
	//update Language
	Select selLanguage = new Select(language);
	int value = Integer.parseInt(checkLanguage.getAttribute("value"));
	selLanguage.selectByIndex(value+1);
	String selectedLanguage = checkLanguage.getText();
	
	Web.clickOnElement(outsideAssets);
	Web.clickOnElement(save);
	CommonLib.waitForProgressBar();
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(selectedBirthDate.equals(updatedBirthDate.getText().trim())&&selectedGender.equals(updatedGender.getText().trim())&&selectedLanguage.equals(updatedLanguage.getText().trim())
			&&selectedMstatus.equals(updatedMaritalStatus.getText().trim()))
	{
		Reporter.logEvent(Status.PASS, "Update Gender,Marital Status,Birth date,Language and check radio button"
				+ "'Dont know if employee has outside assets' and save.Verify the updated changes.", "Changes are reflected properly.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Update Gender,Marital Status,Birth date,Language and check radio button"
				+ "'Dont know if employee has outside assets' and save.Verify the updated changes.", "Changes are not reflected properly.", true);
	}
	
	
	Thread.sleep(3000);
	Web.clickOnElement(basicInfoEdit);
	Web.waitForElement(basicInfoEditFrame);
	Web.getDriver().switchTo().frame(basicInfoEditFrame);
	Web.waitForElement(ssnHeaderOnModalWindow);
	
	//update birth date
	Web.setTextToTextBox(birthDate,birthDate_);
	selMaritalStatus.selectByVisibleText(maritalStatus.toUpperCase());
	selectGender.selectByVisibleText(gender_.toUpperCase());
	
	//update Language
	selLanguage.selectByVisibleText(language_.toUpperCase());
		
	Web.clickOnElement(outsideAssets);
	Web.clickOnElement(save);
	CommonLib.waitForProgressBar();
	Web.getDriver().switchTo().defaultContent();
	
}


/*
 * This method fetches the employee result set from DB with subset data on employee overview page.
 */
public ResultSet getEmployeeForUser(String[] queryName,String username) throws SQLException
{
	queryResultSet = DB.executeQuery(queryName[0], queryName[1], "K_"+username);
	return queryResultSet;
}


/*
 * This method checks if Subset section is displayed on employee overview page
 */
public boolean isSubSetSectionDisplayed() throws Exception
{
	boolean isSubSetDisplayed = false;
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(Web.isWebElementDisplayed(subSetsection, false))
		isSubSetDisplayed = true;
	else
		isSubSetDisplayed = false;
	return isSubSetDisplayed;
}
	

public void verifySubSetColumnHeaders() throws Exception
{
	boolean isHeaderExist = false;
	List<String> actualHeaders = new ArrayList<String>();
	List<String> expectedHeaders = Arrays.asList(Stock.GetParameterValue("expectedHeaders").split(","));
	for(WebElement header : subSetHeaders)
	{
		actualHeaders.add(header.getText().trim());
	}
	for(String expHeader : expectedHeaders)
	{
		if(actualHeaders.contains(expHeader))
		{
			isHeaderExist=true;
		}
		else
		{
			isHeaderExist=false;
			break;
		}
	}
	if(isHeaderExist)
		Reporter.logEvent(Status.PASS,"verify all the headers of employee subset section.<br>"+expectedHeaders, "All headers are displayed.<br>"
				+ ""+actualHeaders, false);
	else
		Reporter.logEvent(Status.FAIL,"verify all the headers of employee subset section.<br>"+expectedHeaders, "All headers are not displayed.<br>"
				+ ""+actualHeaders, true);
}


/*
 * This method returns SSN from resultset
 */
public String getEmployeeSSNFromResultSet(ResultSet resultset) throws SQLException,InterruptedException
{
	String ssn="";
	while(resultset.next()){
		ssn = resultset.getString("SSN");
		break;
	}
	return ssn;
}

/*
 * This method verifies subset History section headers
 */
public void verifySubsetHistorySection() throws Exception
{
	boolean isHistHeaderDisplayed = false;
	List<String> expectedHeaders = Arrays.asList(Stock.GetParameterValue("expectedHeadersHistory").split(","));
	List<String> actualHeaders = new ArrayList<String>();
	
	if(subSetHistoryLink.isDisplayed())
	{
		Web.clickOnElement(subSetHistoryLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(empSubSetHistFrame);
		Web.getDriver().switchTo().frame(empSubSetHistFrame);
		for(WebElement historyHeader : subSetHistoryHeaders)
		{
			actualHeaders.add(historyHeader.getText());
		}
		for(String header : expectedHeaders)
		{
			if(actualHeaders.contains(header))
			{
				isHistHeaderDisplayed = true;
			}
			else
			{
				isHistHeaderDisplayed = false;
			    break;
			}
		}
		if(isHistHeaderDisplayed)
			Reporter.logEvent(Status.PASS,"Verify subset history headers."+expectedHeaders, "headers are displayed."+actualHeaders, false);
		else
			Reporter.logEvent(Status.FAIL,"Verify subset history headers."+expectedHeaders, "headers are displayed."+actualHeaders, true);
	}
}




/*
 * This method verifies the subset history data is displayed if employee is having subset information
 */
public void checkSubSetHistDataIsDisplayed()
{
	List<String> actualSubsetData = new ArrayList<String>();
	if(subSetDataRows.size()>1)
		Reporter.logEvent(Status.PASS, "Verify that subset history data is displayed.", "Subset history data is displayed.", false);
	else
		Reporter.logEvent(Status.FAIL, "Verify that subset history data is displayed.", "Subset history data is not displayed.", true);

    for(int i=1;i<subSetDataRows.size();i++)
    {
    	String subsetRow = "//table[2]//tr["+(i+1)+"]";
    	WebElement row = Web.getDriver().findElement(By.xpath(subsetRow));
    	List<WebElement> subsetColumns  = row.findElements(By.xpath(".//font"));
    	for(WebElement column :subsetColumns)
    	{
    		actualSubsetData.add(column.getText());
    	}
    	Reporter.logEvent(Status.INFO, "Subset data:","Subset data:"+actualSubsetData, false);
    }	
    Web.getDriver().switchTo().defaultContent();
    Web.getDriver().switchTo().frame(employeeSearchFrame);
    Web.clickOnElement(closeIconOnSubsetHistoryWindow);
    CommonLib.waitForProgressBar();
    Web.getDriver().switchTo().defaultContent();
}



/*
 * This method switches to employee through recently viewed section and validates other details pertaining to switched employee
 */
public void switchToRecentlyViewedEmp() throws Exception,SQLException
{
	String recentlyViewedBeforeSwitch = null;
	String planBeforeSwitch = null;
	String recentlyViewedAfterSwitch = null;
	String ssnbeforeSwitch = null;
	ArrayList<String> plans = new ArrayList<String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("queryTofindPlansForNextGen")[0],
			Stock.getTestQuery("queryTofindPlansForNextGen")[1],"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		plans.add(queryResultSet.getString("GA_ID"));
	}
	
	recentlyViewedBeforeSwitch = this.Verify_Recently_Viewed_Employee();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	planBeforeSwitch = selectedPlanHeader.getText().split(" - ")[1].trim();
	ssnbeforeSwitch = empSSN.getText().substring(7);
	System.out.println("Plan before switch:"+planBeforeSwitch+",and employee before switch:"+recentlyViewedBeforeSwitch
			+",SSN before switch:"+ssnbeforeSwitch);
	HomePage homePage = new HomePage();
	for(String plan : plans){
		if(!plan.equals(planBeforeSwitch)){
			Web.getDriver().switchTo().defaultContent();
			homePage.searchPlanWithIdOrName(plan);
			break;
		}
	}
	this.navigateToEmployeeTab();
	this.searchEmployeeByName("");
	this.navigateToEmployeeOverViewPage();
	recentlyViewedAfterSwitch = this.Verify_Recently_Viewed_Employee();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	
	if(recentlyViewedEmployee.get(0).getText().equals(recentlyViewedAfterSwitch)
			&&recentlyViewedEmployee.get(1).getText().equals(recentlyViewedBeforeSwitch))
	{
		Reporter.logEvent(Status.PASS, "Verify recently viewed employee list after adding employees from"
				+ "different plan to Recently viewed list by searching employee and navigating to employee overview page.",""
						+ "employee from different plan is listed on top and previoulsy visited employee form other plan"
						+ " listed second.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Verify recently viewed employee list after adding employees from"
				+ "different plan to Recently viewed list by searching employee and navigating to employee overview page.",""
						+ "Recently viewed list is not ordered based on recently viewed employees order.", true);
	}
	Web.clickOnElement(recentlyViewedEmployee.get(1));
	CommonLib.waitForProgressBar();
	Web.waitForPageToLoad(Web.getDriver());
	Web.getDriver().switchTo().defaultContent();
	this.navigateToEmpDetailPage();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(empNameHeader.getText().equals(recentlyViewedBeforeSwitch)
	&&selectedPlanHeader.getText().replace(" - ", "").trim().contains(planBeforeSwitch)
	&&empSSN.getText().substring(7).equals(ssnbeforeSwitch)
	&&contactName.getText().toUpperCase().equals(recentlyViewedBeforeSwitch)
	&&ssnInBasicInfoSection.getText().contains(ssnbeforeSwitch))
	{
		Reporter.logEvent(Status.PASS, "Switch to another plan employee from recently viewed list"
				+ "and validate employee is switched and respective data is displayed.", "Employee is switched and respective data is displayed.", false);
	Web.getDriver().switchTo().defaultContent();
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Switch to another plan employee from recently viewed list"
				+ "and validate employee is switched and respective data is displayed.", "Employee is not switched or respective data is not displayed.", true);
	}

}

/*
 * This is a generic method to return all resultset of employee for a user
 */
public ResultSet selectEmployeesForUser(String[] dBQueryName,String username) throws SQLException
{
	queryResultSet = DB.executeQuery(dBQueryName[0],dBQueryName[1],username);
	return queryResultSet;
}

/*
 * This is a generic method to return SSN of an employee from DB
 */
public String selectEmployeeFromResultSet(ResultSet resultSet) throws SQLException,InterruptedException
{
	String empSSN = null;
	while(resultSet.next())
	{
		empSSN = resultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(empSSN);
		if (!errortxtSearchResults.getText().contains("PLEASE CHECK YOUR SEARCH CRITERIA.")) {
			break;
		}
	}
	return empSSN;
}



/*
 * This method update the Enrollment and Eligibility section and save.
 */
public boolean editEnrollmentAndEligibilityAndSave()
{
	boolean isEnrollmentSectionDisplayed = false;
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(enrollmentAndEligibilitySection.isDisplayed())
	{
		isEnrollmentSectionDisplayed = true;
		Web.clickOnElement(enrolAndEligibltyEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(enrollAndEligEditFrame);
		
	}
	else
	{
		isEnrollmentSectionDisplayed = false;
	}
	return isEnrollmentSectionDisplayed;
}














}





	
	
	
	

