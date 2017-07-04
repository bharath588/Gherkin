package pageobjects.employeesearch;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.cookie.Cookie;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;

import com.aventstack.extentreports.Status;

import framework.util.CommonLib;

public class EmployeeSearch extends LoadableComponent<EmployeeSearch> {

	
	//Locators declaration
	@FindBy(xpath=".//font[contains(text(),'We were unable to process your request.')]")
	private WebElement processingError;
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
	@FindBy(xpath = ".//*[@id='employeeSearchOverviewContainer_content']//h1//label")
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
	@FindBy(id="IPB_CONTACT_3")
	private WebElement radioCheck;
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
	@FindBy(xpath="//*[@id='contactInfoEditDialogId']//preceding-sibling::div//*[contains(text(),'close')]")
	private WebElement closeEmpContactModalWindow;
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
	@FindBy(xpath="//input[@value='Cancel']")
	private WebElement cancel;
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
	private List<WebElement> empIDLabel;
	@FindBy(id="EmployeebasicInfoIndividualID")
	private List<WebElement> empIdValue;
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
	@FindBy(xpath="//*[@id='empUpdateVestingInfoPriorDialogId']/preceding-sibling::div//span[.='close']")
	private WebElement closeIconVestingWindow;
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
	private List<WebElement> enrollmentAndEligibilitySection;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//a")
	private WebElement enrolAndEligibltyEditLink;
	@FindBy(id="enrollEligEditFrameId")
	private WebElement enrollAndEligEditFrame;
	@FindBy(xpath="//*[@name='ChangeEligInfo']//td[1]/font[contains(text(),'')]")
	private List<WebElement> eligibilityLabels;
	@FindBy(xpath="//*[@name='ChangeEligInfo']//td[2]/font[text()!='']")
	private List<WebElement> eligibilityFields;
	@FindBy(id="eligibilityInd")
	private WebElement eligiCode;
	@FindBy(id="ineligibilityReasonCode")
	private WebElement inEligiReasonCode;
	@FindBy(xpath=".//*[@id='eligibilityInd']/option[@selected='']")
	private WebElement selectedEligiCode;
	@FindBy(xpath=".//*[@id='ineligibilityReasonCode']/option[@selected='']")
	private WebElement selectedInEligiCode;
	@FindBy(xpath="//label[contains(text(),'Eligibility code:')]/../following-sibling::td//td")
	private WebElement eligibilityCodeOverview;
	@FindBy(xpath="//label[contains(text(),'Ineligibility code:')]/../following-sibling::td//td")
	private WebElement inEligibilityCodeOverview;
	@FindBy(xpath="//label[contains(text(),'Participation date:')]/../following-sibling::td//td")
	private WebElement partDateOverview;
	@FindBy(id="participationDate")
	private WebElement partDate;
	@FindBy(xpath="//div[contains(text(),'Percent')]/preceding-sibling::div")
	private WebElement benName;
	@FindBy(xpath="//a/h4[contains(text(),'Investments')]/ancestor::*[5]/following-sibling::div[3]//span")
	private WebElement disclaimerText;
	@FindBy(xpath="//a/h4[contains(text(),'Money Source')]/../../following-sibling::div//table[2]//td/a")
	private List<WebElement> moneyTypeLinks;
	@FindBy(xpath="//a/h4[contains(text(),'Investments')]/../../following-sibling::div//table//td/a")
	private List<WebElement> investTypeLinks;
	@FindBy(css="div[id='moneySourceModal'] div[class='modal-content']")
	private WebElement moneyTypeModalContent;
	@FindBy(xpath="//div[@id='fundSummaryModal']//div[@class='modal-content']")
	private WebElement investTypeModalContent;
	@FindBy(css="div[id='moneySourceModal'] div[class='modal-content'] h4")
	public WebElement modalContentHeader;
	@FindBy(xpath=".//*[@id='moneySourceModal']//div[@class='modal-body']//div//table")
	public List<WebElement> investmentOptionDataTables;
	@FindBy(xpath=".//*[@id='moneySourceModal']//button")
	private WebElement closeMoneyType;
	@FindBy(xpath="//*[@id='fundSummaryModal']//button")
	private WebElement closeInvestType;
	@FindBy(xpath="//div[@id='fundSummaryModal']//div[@class='modal-content']//h4")
	private WebElement modalContentHeaderInvestment;
	@FindBy(xpath="//h4[contains(text(),'Money Source')]/ancestor::div[2]//table[2]//th")
	private List<WebElement> monySrcHeader;
	@FindBy(xpath="//h4[contains(text(),'Money Source')]/ancestor::div[2]//table[2]//tr")
	private List<WebElement> monySrcDataRows;
	@FindBy(xpath=".//*[@id='asOfDateCal']")
	private WebElement asOfDateInput;
	@FindBy(xpath="//html//span")
	private WebElement errorWebElement;
	@FindBy(xpath="//*[@id='newMenu']//li/a[.='Search employee']")
	private WebElement searchEmployeeOptionLink;
	@FindBy(xpath="//ul[@id='newMenu']//a[contains(text(),'Process center')]")
	private WebElement proCenterMenu;
	@FindBy(xpath="//ul[@id='newMenu']//a[contains(text(),'Compliance')]")
	private WebElement compMenu;
	@FindBy(xpath="//li/a[contains(text(),'Administration')]//following-sibling::ul//a[contains(text(),'Transfer restrictions')]")
	private WebElement transferRestrictions;
	@FindBy(id="accountDetail")
	private WebElement accountDetail;
	@FindBy(id="allocationMoreButton")
	private WebElement allocMoreButton;
	@FindBy(xpath=".//*[@id='allocationDashboard']//th/span")
	private List<WebElement> allocationHeaders;
	@FindBy(xpath=".//*[@id='allocationDisplay_content']//tbody//tr/td[5]")
	private List<WebElement> investPercentList;
	@FindBy(xpath=".//*[@id='allocationDisplay_content']//tbody//tr")
	private List<WebElement> maxAllocationsRow;
	@FindBy(xpath=".//*[@id='allocationDashboard_content']//div[@class='noData']")
	private WebElement noallocations;
	@FindBy(xpath="(.//*[@id='allocationHistorySSOFrame'])[1]")
	private WebElement allocModalWindowFrame;
	@FindBy(name="CHG_ALLOC_BTN")
	private WebElement changeAllocBtn;
	@FindBy(name="ChangeAllocations")
	private WebElement changeAllocPage;
	@FindBy(xpath="//input[contains(@id,'percentage_')]")
	private List<WebElement> listOfInPercentageField;
	@FindBy(id="totalPercent")
	private WebElement totalPercent;
	@FindBy(xpath="//input[contains(@id,'percentage_')]//ancestor::tr")
	private List<WebElement> allocationsRows;
	@FindBy(xpath=".//*[@id='allocationHistoryDialog']//preceding-sibling::div[1]//span[.='close']")
	private WebElement allocationModalClose;
	@FindBy(id="participantStatementsDetailMpwr")
	private WebElement partStatementTab;
	@FindBy(id="ptctStatementsTypes_data")
	private WebElement statementContent;
	@FindBy(xpath=".//*[@id='ptctStatementsTypes']//th/span")
	private List<WebElement> stmntHeader; 
	@FindBy(id="stmtOptionDropDown")
	private WebElement stmtDropDown;
	@FindBy(xpath="//tbody[@id='ptctStatementsTypes_data']//td[2]//span")
	private List<WebElement> stmtCategory;
	@FindBy(xpath="//tbody[@id='ptctStatementsTypes_data']//td[1]//span")
	private List<WebElement> stmtDateStringList;
	@FindBy(xpath="//tbody[@id='ptctStatementsTypes_data']//td[3]//span")
	private List<WebElement> stmtDescription;
	@FindBy(xpath="//*[@name='ChangeEmployment']//td[1]//font[text()!='']")
	private List<WebElement>  editEmplmentInfoModalWindowLabels;
	@FindBy(xpath="//*[@id='employeeInfoEditDialogId']/preceding-sibling::div//*[contains(text(),'close')]")
	private WebElement closeEditEmpntInfoWindow;
	@FindBy(xpath="//*[@id='feeDashboard']//thead/following-sibling::tbody")
	private WebElement feesTable;
	@FindBy(xpath="//*[@id='feeDashboard']//thead/following-sibling::tbody//tr")
	private List<WebElement> feesRecords;
	@FindBy(xpath=".//*[@id='feeDashboard']//th//span")
	private List<WebElement> feesHeader;
	@FindBy(id="feeMoreButton")
	private WebElement feeMoreButton;
	@FindBy(xpath="//*[@id='feeHistoryDialog']//preceding-sibling::div//span[contains(text(),'close')]")
	private WebElement closeFeeModalWindow;
	@FindBy(id="feesHistorySSOFrame")
	private WebElement feesModalFrame;
	@FindBy(xpath="//table[2]//tr[1]//font/font")
	private List<WebElement> FeesModalWinHeader;
	@FindBy(xpath="//select[contains(@name,'from')]")
	private List<WebElement> from_date;
	@FindBy(xpath="//select[contains(@name,'to')]")
	private List<WebElement> to_date;
	@FindBy(name="SUBMIT_BUTTON")
	private WebElement submit;
	@FindBy(xpath=".//table[2]//tr[1]/following-sibling::tr//td[3]//font")
	private List<WebElement> listEffcDate;
	@FindBy(xpath=".//*[@id='feeDashboard']//*[@class='noData']")
	private WebElement noFeesData;
	@FindBy(xpath=".//*[@id='vestingDashboard']//td[contains(text(),'Computation period')]")
	private WebElement computationPrd;
	@FindBy(xpath=".//*[@id='vestingDashboard']//a[contains(text(),'Vested balance')]")
	private WebElement vestedBalance;
	@FindBy(xpath=".//*[@id='vestingDashboard']//a[contains(text(),'Vesting by money source')]")
	private WebElement vestedByMoneySource;
	@FindBy(id="vestingDashboard")
	private WebElement vestingBoxWithData;
	@FindBy(id="vestingDialog")
	private WebElement vestingDialog;
	@FindBy(xpath=".//*[@id='vestingDialog']/preceding-sibling::div//span[.='close']")
	private WebElement vestingModalCloseLink;
	@FindBy(xpath=".//*[@id='vestingDialog']//a[contains(text(),'Click here')]")
	private WebElement clickHereLink;
	@FindBy(xpath=".//*[@id='vestingDialog']//th//span/following-sibling::span")
	private List<WebElement> vestingModalHeaders;
	@FindBy(id="accountBalanceSSOFrame")
	private WebElement accntBalFrame;
	@FindBy(className="section_title_text")
	private WebElement accntBalTitle;
	@FindBy(xpath=".//*[@id='accountBalanceHistoryDialog']/preceding-sibling::div//span[contains(text(),'close')]")
	private WebElement accntBalCloseLink;
	@FindBy(xpath=".//*[contains(@id,'vestingForm')]//thead/following-sibling::tbody//a")
	private List<WebElement> periodBeginDateLinks;
	@FindBy(xpath=".//*[@id='vestingDetailDialog']//div[contains(text(),'Vesting Compliance Details for Period Beginning')]")
	private WebElement vestingDetailPage;
	@FindBy(xpath=".//*[@id='vestingDetailDialog']/preceding-sibling::div//span[contains(@class,'ui-icon-closethick')]")
	private WebElement vestingDetailClose;
	@FindBy(className="money_content")
	private WebElement moneyOutSection;
	@FindBy(xpath="//*[@class='money_content']//th")
	private List<WebElement> loansColumnsList;
	@FindBy(xpath=".//*[@class='loansHeader']//td[contains(text(),'Active loans')]")
	private WebElement activeLoansLabel;
	@FindBy(xpath=".//*[@class='loansHeader']//td[contains(text(),'Max loans allowed')]")
	private WebElement maxLoanAllowedlabel;
	@FindBy(xpath=".//*[@class='loansHeader']//td[contains(text(),'Minimum amount')]")
	private WebElement minAmntLabel;
	@FindBy(xpath=".//*[@class='loansHeader']//td[contains(text(),'Maximum amount')]")
	private WebElement maxAmntLabel;
	@FindBy(xpath="//*[@class='money_content']//th/ancestor::thead/following-sibling::tbody//tr")
	private List<WebElement> loanRows;
	@FindBy(xpath="//*[@class='money_content']//th/ancestor::thead/following-sibling::tbody//tr//td[1]")
	private List<WebElement> loanEffDateList;
	@FindBy(xpath="//*[@class='money_content']//th/ancestor::thead/following-sibling::tbody//tr//td[6]/button")
	private List<WebElement> loanViewButtons;
	@FindBy(xpath="//span/b[.='Loans']")
	private WebElement loanDetailTitle;
	@FindBy(id="framecA")
	private WebElement loanDetailFrame;
	@FindBy(xpath="//span[.='Loans']/../following-sibling::table[1]//th//a")
	private List<WebElement> loanDetailsHeader;
	@FindBy(xpath="//span[.='Loans']/../following-sibling::table[1]//tbody//tr")
	private List<WebElement> loanDataRowsOnDetailsPage;
	@FindBy(xpath="//span/b[contains(text(),'Employee loan account information')]")
	private WebElement empLoanAccntNumber;
	@FindBy(xpath="//b[contains(text(),'Employee loan account information')]/ancestor::div[1]//following-sibling::table[1]//tr[1]//td")
	private List<WebElement> acctDetailColumn;
	@FindBy(xpath="//b[contains(text(),'Employee loan account information')]/ancestor::div[1]//following-sibling::table[1]//tr")
	private WebElement loanAcctInfoRows;
	@FindBy(xpath="//span[contains(text(),'Effective date:')]/..")
	private WebElement loanEffDate;
	@FindBy(xpath="//span[contains(text(),'First due date:')]/..")
	private WebElement loanFstDueDate;
	@FindBy(xpath="//span[contains(text(),'Maturity date:')]/..")
	private WebElement loanMatDate;
	@FindBy(xpath="//span[contains(text(),'Interest rate:')]/..")
	private WebElement loanIntRate;
	@FindBy(xpath="//span[contains(text(),'Loan term:')]/..")
	private WebElement loanTerm;
	@FindBy(xpath=".//span[contains(text(),'Days late')]/following-sibling::span")
	private WebElement daysLateField;
	@FindBy(xpath="//*[contains(text(),'Employee loan activity information')]/ancestor::div[1]//following-sibling::table[1]//tr//td[10][contains(text(),'UNPAID') or contains(text(),'PARTIAL')]//preceding-sibling::td[2]")
	private List<WebElement> listOfUpaidDueDates;
	@FindBy(xpath="//span[contains(text(),'Payments remaining')]/..")
	private WebElement payRemaining;
	@FindBy(linkText="Return to employee overview")
	private WebElement returnToEmpPageLink;
	@FindBy(id="payCheckContribution")
	private WebElement paycheckTitleLink;
	@FindBy(id="ytdcontributions")
	private WebElement ytdTab;
	@FindBy(id="currDeferral")
	private WebElement currentRate;
	@FindBy(id="pendDeferral")
	private WebElement pendingRate;
	@FindBy(xpath=".//*[@id='payCheckYtdcontributions_content']//th//span")
	private List<WebElement> ytdColumnHeader;
	@FindBy(xpath=".//*[@id='payCheckYtdcontributions_content']//thead//following-sibling::tbody//tr")
	private List<WebElement> ytdRecordsUI; 
	@FindBy(xpath=".//*[@id='payCheckYtdcontributions_content']//thead//following-sibling::tbody//tr//td[1]//span")
	private List<WebElement> ytdTypeUI;
	@FindBy(xpath=".//*[@id='payCheckYtdcontributions_content']//thead//following-sibling::tbody//tr//td[2]//span")
	private List<WebElement> ytdContributionsUI;
	@FindBy(id="transHistoryMoreButton")
	private WebElement txnHistoryMoreBtn;
	@FindBy(xpath=".//*[@id='transactionDashboard_content']//div[@class='noData']//a")
	private WebElement clickHereLinkInTxnHist;
	@FindBy(id="transactionHistSSOFrame")
	private WebElement txnHistFrame;
	@FindBy(xpath="(.//input[@type='RADIO'])[3]")
	private WebElement contriRadBtn;
	@FindBy(xpath=".//*[@id='rsTable']//tbody//tr[not(self::tr[@class='lightBgColor'])]//td[4]//font")
	private List<WebElement> listContriAmount;
	@FindBy(xpath=".//*[@id='rsTable']//tbody//tr[not(self::tr[@class='lightBgColor'])]//td[3]//font[text()='Contribution']")
	private List<WebElement> txnTypeList;
	@FindBy(xpath=".//*[@id='transactionHistoryDialog']//preceding-sibling::div//*[contains(text(),'close')]")
	private WebElement txnHistClose;
	@FindBy(xpath=".//*[contains(@id,'ytdTotalContributions')]")
	private WebElement totalYtdCon;
	@FindBy(id="ytdMoreButton")
	private WebElement payChkMoreBtn;
	@FindBy(xpath=".//*[@id='rsTable']//tbody//tr[1]//a")
	private List<WebElement> txnHistHeaders;
	@FindBy(xpath=".//*[@id='payCheckYtdcontributions_content']//tbody//tr//td")
	private WebElement noDataPaychkElement;
	@FindBy(xpath=".//*[@id='paycheckContributionMpwrDataTbl']//thead//th")
	private List<WebElement> paycheckHeaderColumn; 
	@FindBy(id="paycheckContribHistLink_mpwr")
	private WebElement payChkHistoryLink;
	@FindBy(id="ui-dialog-title-historyDialog_mpwr")
	private WebElement histModalWindowTitle;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Eligibility code')]/../following-sibling::td//td")
	private WebElement eligCodeUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Participation date')]/../following-sibling::td//td")
	private WebElement partDateUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Enroll invite date')]/../following-sibling::td//td")
	private WebElement enrInviteDateUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Enroll invite reply')]/../following-sibling::td//td")
	private WebElement enrInviteReplyUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Enroll PIN date')]/../following-sibling::td//td")
	private WebElement enrPinDateUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Ineligibility code')]/../following-sibling::td//td")
	private WebElement ineligCodeUI;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Enrollment notification date')]/../following-sibling::td//td")
	private WebElement enroNotificationDate;
	@FindBy(xpath=".//*[@id='enrollmentAndEligibilityInfo']//"
			+ "label[contains(text(),'Suppress auto enrollment indicator')]/../following-sibling::td//td")
	private WebElement suppressAutoIndicator;
	@FindBy(xpath=".//*[contains(text(),'Add a New Employee')]")
	private WebElement addEmpTitle;
	@FindBy(id="ssnId")
	private WebElement enterSSN;
	@FindBy(id="ssnReId")
	private WebElement reEnterSSN;
	@FindBy(id="date_of_birth")
	private WebElement pptDob;
	@FindBy(id="last_name")
	private WebElement lastName;
	@FindBy(id="ssnerrorNode")
	private WebElement ssnError;
	@FindBy(id="ssnReerrorNode")
	private WebElement ssnReError;
	@FindBy(xpath=".//input[@value='Continue']")
	private WebElement continueAddEmp;
	@FindBy(id="state")
	private WebElement state_;
	@FindBy(id="firstName")
	private WebElement first_Name;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Employee Basic Information')]")
	private WebElement empBasicInfoTitle;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Eligibility Information')]")
	private WebElement eligibilityTitle;
	@FindBy(xpath=".//font[@class='important_note']//li")
	private List<WebElement> mandatoryFieldValidationMsgs;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Enter Income Data')]")
	private WebElement incomeDataTitle;@FindBy(id="salAmtQual") private WebElement frequency;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Add new employment information')]")
	private WebElement employmentTitle;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Enter Subset Information')]")
	private WebElement subsetInformationTitle;
	@FindBy(xpath=".//*[@class='section_title_text'][contains(text(),'Managed Account Participant Enrollment')]")
	private WebElement managedAccntTitle;
	@FindBy(xpath=".//form[@name='Managed Account']//td[1]/font[not(self::font/input)][text()!='']")
	private List<WebElement> managedAccntFields;
	@FindBy(xpath=".//input[@name='ESC_MAN_ACCT_UPDATE_SAVE']")
	private WebElement conPptWithMangedAccntBtn;
	@FindBy(xpath=".//input[@name='ESC_MAN_ACCT_UPDATE_CANCEL']")
	private WebElement conPptWithoutMangedAccntBtn;
	@FindBy(name="ChangeAllocations")
	private WebElement changeAllocForm;
	@FindBy(name="ESC_ALLOC_CREATE_DEFAULT")
	private WebElement useDefltAllocBtn;
	@FindBy(name="ESC_ALLOC_CREATE_NONE")
	private WebElement conWithoutAllocBtn;
	@FindBy(id="EmployeeDeferralModule")
	private WebElement autoSuiteAngularDeferralPage;
	@FindBy(xpath=".//button[contains(text(),'Continue without deferrals')]")
	private WebElement conWithoutDeffNgBtn;@FindBy(id="uses_models_4")
	private WebElement newAllocRadioButton;
	@FindBy(xpath=".//font[@class='important_note']//li"
			+ "[contains(text(),'First Name contains 1 or more invalid characters.  Invalid characters')]")
	private WebElement fNValidation;
	@FindBy(xpath=".//font[@class='important_note']//li[contains(text(),'Field contains invalid characters')]")
	private WebElement addressValidation;
	@FindBy(xpath=".//font[@class='important_note']//li[contains(text(),'Zip Code is invalid')]")
	private WebElement zipCodeValidation;
	@FindBy(xpath=".//div[@id='enrollEligEditDialogId']//preceding-sibling::div//*[contains(text(),'close')]")
	private WebElement enrollEligCloseWindow;
	@FindBy(xpath=".//a[contains(text(),'current period vesting')]")
	private WebElement changeCurrentPeriodVesting;
	@FindBy(xpath=".//a[contains(text(),'prior period vesting')]")
	private WebElement changePriorPeriodVesting;
	
	private String transHistory = ".//*[@id='transactions']";
	private String interactions = ".//*[@id='participantengagement']";
	private String investments = ".//*[@id='allocations']";
	private String fees = ".//*[@id='fees']";
	private String loans = ".//a[contains(text(),'Loans')]";
	private String vesting = "//*[contains(@class,'vesting')]";
	private String beneficiarySection = ".//*[contains(@class,'beneficiary')]";
	private String compensation = ".//*[@id='salaryInfo']";
	private String empLeaveInfoSection  = ".//*[@id='employeeDetailTabLoaInfoSection']";
	private String basicInfoSection = ".//*[@id='basicInfo']";
	private String contactInfoSection = ".//*[@id='contactInfo']";
	private String employmentInfoSection = ".//*[@id='employmentInfo']";
	private String enrollAndEligSection = ".//*[@id='enrollmentAndEligibilityInfo']";
	private String payCheckContriSection = ".//*[@id='paycheckContributionInfo']";
	private String feesDataForThreeMonth = ".//*[@id='feeDashboard']//thead/following-sibling::tbody";
	
	
	String qdroPart = null;
	String normalPart = null;
	String transferPart=null;
	private String ssn="";
	public String employeeSearched = null;
		
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
	private List<String> uscsID = new ArrayList<String>();
	private List<String> uscsIDReset;
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
		//Web.getDriver().switchTo().defaultContent();
		Assert.assertTrue(Web.isWebElementDisplayed(employeeSearchFrame));
	}

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
			Web.waitForPageToLoad(Web.getDriver());
			//CommonLib.waitForProgressBar();
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
				Web.waitForPageToLoad(Web.getDriver());
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
		if(fieldName.trim().equalsIgnoreCase("PRINT PAGE HEADER")){
			return this.printPriviewWindowHeader;
		}
		if(fieldName.trim().equalsIgnoreCase("PRINT LINK")){
			return this.print;
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
		Web.waitForElement(txtSearchbox);
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
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.isWebElementDisplayed(drpdwnSearchEmployee,true);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("SSN - all plans");
		Web.waitForElement(txtSearchbox);
		Web.setTextToTextBox(txtSearchbox, SSN);
		if(Web.isWebElementDisplayed(btnGoEmployeeSearch, true))
		Web.clickOnElement(btnGoEmployeeSearch);
		Web.isWebElementsDisplayed(this.getWebElementasList("EmpLastNameLink"),true);
		Web.getDriver().switchTo().defaultContent();
		//dismissErrorBox();
	}
	
	/**
	 * This method used to search the employee by SSN-All Plans 
	 * @param SSN
	 * @throws InterruptedException
	 */
	public void searchEmployeeBySSNAllPlans(String SSN,boolean wantWaitOrNot) throws InterruptedException {
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.isWebElementDisplayed(drpdwnSearchEmployee,true);
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("SSN - all plans");
		Web.waitForElement(txtSearchbox);
		Web.setTextToTextBox(txtSearchbox, SSN);
		if(Web.isWebElementDisplayed(btnGoEmployeeSearch, true))
		Web.clickOnElement(btnGoEmployeeSearch);
		Web.isWebElementsDisplayed(this.getWebElementasList("EmpLastNameLink"),wantWaitOrNot);
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
		Web.waitForElement(txtSearchbox);
		Web.setTextToTextBox(txtSearchbox, EmployeeID);
		Thread.sleep(2000);
		Web.clickOnElement(btnGoEmployeeSearch);	
		CommonLib.waitForProgressBar();
		Thread.sleep(2000);
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
			Web.selectDropDownOption(planDropdown, planWithDivisons,true);
			btnGoPlanNumberfordrpdwn.click();
			if (getErrorMessageText().trim().isEmpty()) {
				break;
			}
		}
		return planWithDivisons;

	}
	
	/**
	 * This method selects the required plan from the plan dropdown box on homepage
	 * @param resultset
	 * @return
	 * @throws SQLException
	 * @author smykjn
	 */
	public String selectFromDropdown(String planNumber) throws SQLException {
		Web.selectDropDownOption(planDropdown, planNumber,true);
		btnGoPlanNumberfordrpdwn.click();
		return planNumber;
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
	 * This is a generic method which checks which field is displayed to select plan number
	 * @param String
	 * @return
	 * @throws SQLException
	 */

	public String selectPlanFromResultset(String planNumber)
			throws SQLException, InterruptedException {
		if (Web.isWebElementDisplayed(planDropdown)) {
			planNumber = selectFromDropdown(planNumber);
			Thread.sleep(2000);
			navigateToEmployeeTab();
		} 
		if (Web.isWebElementDisplayed(txtPlanNumberField)) {
			planNumber = enterFromtextBox(planNumber);
			Thread.sleep(2000);
			navigateToEmployeeTab();
		}
		return planNumber;
	}
	
	/**
	 * This is a generic method which checks which field is displayed to select plan number
	 * @param String
	 * @return
	 * @throws SQLException
	 */

	public String searchPlan(String planNumber)
			throws SQLException, InterruptedException {
		if (Web.isWebElementDisplayed(planDropdown)) {
			planNumber = selectFromDropdown(planNumber);
			Thread.sleep(3000);
			navigateToEmployeeTab();
		} 
		if (Web.isWebElementDisplayed(txtPlanNumberField)) {
			planNumber = enterFromtextBox(planNumber);
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
	 * This method selects the required plan from the plan text box on home page
	 * @param String
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public String enterFromtextBox(String planNumber) throws SQLException {
		
		Web.setTextToTextBox(txtPlanNumberField, planNumber);
		btnGoPlanNumberforSearchBox.click();
		return planNumber;
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
		Web.waitForElement(tabEmployees);
		actions = new Actions(Web.getDriver());
		actions.moveToElement(tabEmployees).click().build().perform();
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementDisplayed(drpdwnSearchEmployee, true);
		actions = new Actions(Web.getDriver());
		actions.moveToElement(linkProfile);
		actions.build().perform();
		Thread.sleep(2000);
		if(!Web.isWebElementDisplayed(employeeSearchFrame, true))
		{
			actions.moveToElement(tabEmployees).click().build().perform();
			actions.click(searchEmployeeOptionLink).perform();
		}
		
	}
	/**
	 * <pre>This method used to search the employee by Division</pre>
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
		Web.waitForElement(drpdwnSearchEmployee);
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
		//For Non Apple plan
		String[] actualOptions = new String[] { "SSN", "Name", "Employee ID",
				"Participant ID", "Division", "--------------------","Name - all plans","SSN - all plans"};
		
		//for Apple plan
		/*String[] actualOptions = new String[] { "SSN", "Name", "Employee ID",
				"Participant ID","Division"};*/
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
		CommonLib.waitForProgressBar();
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
		Web.getDriver().switchTo().defaultContent();
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
			empInfo.put("Last Name",queryResultSet.getString("LAST_NAME").trim());
			empInfo.put("First Name",queryResultSet.getString("FIRST_NAME").trim());
			empInfo.put("Middle Name",queryResultSet.getString("MIDDLE_NAME").trim());
			empInfo.put("Address",queryResultSet.getString("FIRST_LINE_MAILING").trim());
			empInfo.put("City",queryResultSet.getString("CITY").trim());
			empInfo.put("Zip",queryResultSet.getString("ZIP_CODE").trim());
			empInfo.put("Home Phone Area Code",queryResultSet.getString("HOME_PHONE_AREA_CODE").trim());
			empInfo.put("Home Phone Number",queryResultSet.getString("HOME_PHONE_NBR").trim());
			empInfo.put("Work Phone Area Code",queryResultSet.getString("WORK_PHONE_AREA_CODE").trim());
			empInfo.put("Work Phone Number",queryResultSet.getString("WORK_PHONE_NBR").trim());
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
	
	
	/**
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
		try{
		String dateString = m.get("Term date:");
		DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date dt = dateFormat.parse(dateString);
		Calendar calendar = Calendar.getInstance();         
		calendar.setTime(dt);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
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
		Thread.sleep(5000);
		if(expEmployeeID.getText().equals(updatedEmpID)&&expHireDate.getText().equals(dateFormat.format(calendar.getTime()))
				&&expOfficer.getText().equals(updatedOfficer))
		{
			Reporter.logEvent(Status.PASS, "Verify Hire date,emp id and officer fields are updated.", "mentioned fields are updated.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify Hire date,emp id and officer fields are updated.", "mentioned fields are not updated.", true);
		}
		}catch(Exception e){
			
			/*Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
					+"It may be due to bad data issue.Please check manually with other data.", true);*/
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(closeEditEmpntInfoWindow);
			CommonLib.waitForProgressBar();
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
		try{
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
			Web.waitForElement(modalWindowCloseLink);
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
		}catch(Exception e){
			Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
					+"It may be due to bad data issue.Please check manually with other data.", true);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(closeEditEmpntInfoWindow);
			CommonLib.waitForProgressBar();
		}
	
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	/*
	 * This method validated if contact info section is displayed on overview page
	 */
	public void contactInFoSectionValidation() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		//CommonLib.waitForProgressBar();
		Web.waitForElements(fNLNMILink);
		Web.clickOnElement(fNLNMILink.get(0));
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
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
		try{
		Web.clickOnElement(radioCheck);
		}catch(Exception e){Web.clickOnElement(radioCheck);}
		try{
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
		}catch(Exception e){
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
					+"It may be due to bad data issue.Please check manually with other data.", true);
			Web.clickOnElement(closeEmpContactModalWindow);
			CommonLib.waitForProgressBar();
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
	
	
	/**
	 * <pre>This method takes user to the employee overview page</pre>
	 * @author smykjn
	 */
	public void navigateToEmployeeOverViewPage() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(this.getWebElementasList("EmpLastNameLink").size()>0)
		{
			/*if(!fNLNMILink.get(0).isDisplayed())
			{
				//Web.waitForElements(this.getWebElementasList("EmpLastNameLink"));
				Web.clickOnElement(this.getWebElementasList("EmpLastNameLink").get(0));
				employeeSearched = this.getWebElementasList("EmpLastNameLink").get(0).getText();
			}
			else
			{*/
				employeeSearched = this.getWebElementasList("EmpLastNameLink").get(0).getText();
				Thread.sleep(2000);
				Web.clickOnElement(this.getWebElementasList("EmpLastNameLink").get(0));
			//}
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
	
	
	
	/**
	 * <pre>This method takes user to employee's beneficiary page from Overview page</pre>
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

	
	
	/**
	 * <pre>This method validates the DB record with UI for Beneficiary</pre>
	 * @author smykjn
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
				beneficiaryDetails.put("Mailing Name"+j, benName.getText().replace(" ","").trim());
			}
			else
			{
				beneficiaryDetails.put("Mailing Name", benName.getText().replace(" ","").trim());
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
	
	
	/**
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
		if(partDetailTab.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
		{
			Web.getDriver().switchTo().defaultContent();
		}
		else{
			
			Web.waitForElement(partDetailTab);
			Thread.sleep(2000);
			Web.clickOnElement(partDetailTab);
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
		}
	}
	
	
	/*
	 * This method is used to navigate to Account detail page on overview page
	 */
	public void navigateToAccountDetailPage() throws Exception
	{
		CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(accountDetail.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
		{
			Web.getDriver().switchTo().defaultContent();
		}
		else{
			
			Web.waitForElement(accountDetail);
			Thread.sleep(2000);
			Web.clickOnElement(accountDetail);
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
		}
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
	private List<WebElement> unexpErro;
	
	/*
	 * This method edit the salary information of an employee and save it.
	 */
	public void updateSalaryinfo() throws SQLException,Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(salaryEditLink);
		//CommonLib.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(incomeEditFrame);
		Web.getDriver().switchTo().frame(incomeEditFrame);
		if(unexpErro.size()>0)
		{	
			Reporter.logEvent(Status.FAIL, "Check if unexpected error message is displayed.if yes,it may be due to data issue"
					+ "or may be issue.kindly check manually once.", "Unexpected error is displayed.", true);
			HomePage homePage = new HomePage();
			Web.getDriver().switchTo().defaultContent();
			homePage.logoutPSC();
			/*Web.clickOnElement(Web.returnElement(homePage, "Home_Page_Logo"));
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().frame(employeeSearchFrame);*/
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
				Stock.getTestQuery("getSSNForPaycheckContribution")[1],"K_"+Stock.GetParameterValue("username"));
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
	/**
	 * @author smykjn
	 * @return Map<String,String>
	 * @throws SQLException
	 */

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
			employmentDetailsBeforeRehiring.put("ELIGIBILITY_DATE",queryResultSet.getString("ELIGIBILITY_DATE"));
			break;
		}
		System.out.println(employmentDetailsBeforeRehiring);
		return employmentDetailsBeforeRehiring;
		
	}
	
	/**
	 * @author smykjn
	 * @throws Exception
	 * @throws SQLException
	 */
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
			employmentDetailsAfterRehiring.put("ELIGIBILITY_DATE",queryResultSet.getString("ELIGIBILITY_DATE"));
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
	
	
	/**
	 * <pre>This method updates the hire date and removes the term date in process of rehiring of an employee</pre>
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
			try{
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
			}catch(Exception e){
				Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
						+"It may be due to bad data issue.Please check manually with other data.", true);
				Web.getDriver().switchTo().defaultContent();
				Web.getDriver().switchTo().frame(employeeSearchFrame);
				Web.clickOnElement(closeEditEmpntInfoWindow);
				CommonLib.waitForProgressBar();
				
			}
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
	
	if(nameLabel.isDisplayed()&&ssnLabel.isDisplayed()&&dobLabel.isDisplayed()||empIDLabel.size()>0)
	{
		Reporter.logEvent(Status.PASS,"Verify labels name,SSN,DOB end empid(if applicable) is displayed under overview section.", "Mentioned labels are displayed.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL,"Verify labels name,SSN,DOB end empid(if applicable) is displayed under overview section.", "Mentioned labels are not displayed.", true);
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
	if(empIDLabel.size()>0)
	{
		if(empIdValue.size()>0)
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
	/*String parentWindow = Web.getDriver().getWindowHandle();
	System.out.println("Parent Window:"+parentWindow);*/
	Web.waitForElement(print);
	
	/*Set<String> printWindow = Web.getDriver().getWindowHandles();
	System.out.println(printWindow);
	int count=0;
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
  		  if(printPriviewWindowHeader.getText().contains("THIS IS A PRINT PREVIEW"))
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
	if(CommonLib.getBrowserName().equalsIgnoreCase("chrome")){
		if(print.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate print link is displayed.","Print link is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate print link is displayed.","Print link is not displayed.", true);
	}else{
		Web.clickOnElement(print);
		String parentWindow = CommonLib.switchToWindow();
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
	int count1=0;
	while(queryResultSet.next())
	{
		String ssnQ = queryResultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(ssnQ);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.isWebElementsDisplayed(fNLNMILink,true);
		if(fNLNMILink.size()>0)
		{
			qntTypeIndividuals.add(ssnQ);
			qdroPart = queryResultSet.getString("OWNERSHIP_IND");
			Web.getDriver().switchTo().defaultContent();
			break;
		}
		count1++;
		if(count1==5){break;}
	}
	
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getTransferIndividual")[0],Stock.getTestQuery("getTransferIndividual")[1],
			"K_"+Stock.GetParameterValue("username"));
	int count2=0;
	while(queryResultSet.next())
	{
		String ssnT = queryResultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(ssnT);
		System.out.println("after Transfer emp search.");
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.isWebElementsDisplayed(fNLNMILink,true);
		if(fNLNMILink.size()>0)
		{
			qntTypeIndividuals.add(ssnT);
			transferPart = queryResultSet.getString("OWNERSHIP_IND");
			Web.getDriver().switchTo().defaultContent();
			break;
		}
		count2++;if(count2==5){break;}
	}
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getNormalIndividual")[0],Stock.getTestQuery("getNormalIndividual")[1],
			"K_"+Stock.GetParameterValue("username"));
	int count3=0;
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
		count3++;if(count3==5){break;}
	}
	return qntTypeIndividuals;
}


	
/**
 * <pre>this methos verifies the Basic Information section on employee overview page</pre>
 * @author smykjn
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
	
	

	
	/**
	 * This method verifies the Edit and save functionality for Employee Basic Info section on overview page.
	 * THis also verifies the fields to be editable on basic info modal window
	 */
	public void verifyBasicInfoModalWindow() throws Exception
	{
		List<String> actModalInfoLabels = new LinkedList<String>();
		List<String> expModalInfoLabels = Arrays.asList(Stock.GetParameterValue("ExpectedLabels").split(","));
		boolean islabelExist = false;
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		try{
		Web.clickOnElement(basicInfoEdit);
		CommonLib.waitForProgressBar();
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
		}catch(Exception e){
			Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
					+"It may be due to bad data issue.Please check manually with other data.", true);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(closeIconBasicInfoEditWindow);
			CommonLib.waitForProgressBar();
		}
	}
	
	


/**
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
	if(checkLanguage.getText().trim().equals("ENGLISH")){
	selLanguage.selectByVisibleText("SPANISH");
	}else{
		selLanguage.selectByVisibleText("ENGLISH");
	}
	String selectedLanguage = checkLanguage.getText();
	Web.clickOnElement(outsideAssets);
	try{
	Web.clickOnElement(save);
	
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	CommonLib.waitForProgressBar();
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
	Web.getDriver().switchTo().defaultContent();
	this.switchToFrame();
	CommonLib.waitForProgressBar();
	Web.getDriver().switchTo().defaultContent();
	}catch(Exception e)
	{
		Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
				+"It may be due to bad data issue.Please check manually with other data.", true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(closeIconBasicInfoEditWindow);
		CommonLib.waitForProgressBar();
	}
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
	queryResultSet = DB.executeQuery(dBQueryName[0],dBQueryName[1],"K_"+username);
	return queryResultSet;
}

/**
 * This is a generic method to return SSN of an employee from DB
 */
public String selectEmployeeFromResultSet(ResultSet resultSet) throws SQLException,InterruptedException
{
	String empSSN = null;
	while(resultSet.next())
	{
		empSSN = resultSet.getString("SSN");
		this.searchEmployeeBySSNAllPlans(empSSN);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if (fNLNMILink.size()>=1) {
			Web.getDriver().switchTo().defaultContent();
			break;
		}
	}
	return empSSN;
}

/**
 * <pre>This method validates message under fees section if there is no past three month data found.</pre>
 * @author smykjn
 * @return void
 * @Date 28th-April-2017
 
 */
public void validateNoPastThreeMonthDataMessage()
{
	String actText;
	String expText = Stock.GetParameterValue("ExpectedMessage");
	try{
		this.navigateToAccountDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(Web.getDriver().findElement(By.xpath(fees)));
		actText = noFeesData.getText();
		if(Web.VerifyPartialText(expText, actText, true))
			Reporter.logEvent(Status.PASS,"Search for a participant with no past 3 months fees data."
					+ " and validate following message is displayed:"+expText,"Message displayed:"+actText, false);
		else
			Reporter.logEvent(Status.FAIL,"Search for a participant with no past 3 months fees data."
					+ " and validate following message is displayed:"+expText,"Message displayed:"+actText, true);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL,"Exception occurred.",e.getMessage(),true);
	}
}

/**
 * @author smykjn
 * This method update the Enrollment and Eligibility section and save.
 */
public boolean editEnrollmentAndEligibilityAndSave() throws InterruptedException,Exception
{
	boolean isEnrollmentSectionDisplayed = false;
	String updatedEliCode= null;
	String updatedInEliCode = null;
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(enrollmentAndEligibilitySection.size()>0)
	{
		isEnrollmentSectionDisplayed = true;
		Reporter.logEvent(Status.PASS,"Validate Enrollment and eligibility section is displayed.",""
				+"Enrollment and eligibility section is displayed.", false);
		try{
		Web.clickOnElement(enrolAndEligibltyEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(enrollAndEligEditFrame);
		Web.getDriver().switchTo().frame(enrollAndEligEditFrame);
		Select eligibilityCode = new Select(eligiCode);
		Select inEligibilityCode = new Select(inEligiReasonCode);
		if(selectedEligiCode.getText().equals("No"))
		{
			eligibilityCode.selectByVisibleText("Yes");
			inEligibilityCode.selectByValue("0");
			updatedEliCode = "Yes";
			updatedInEliCode = "";
		}
		else
		{
			eligibilityCode.selectByVisibleText("No");
			inEligibilityCode.selectByVisibleText("Other");
			updatedEliCode = "No";
			updatedInEliCode = "Other";
		}
		Web.clickOnElement(this.save);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		if(eligibilityCodeOverview.getText().trim().equals(updatedEliCode)
				&& inEligibilityCodeOverview.getText().trim().equals(updatedInEliCode))
		{
			Reporter.logEvent(Status.PASS, "Edit Enrollment and Eligibility section and update eligibility code and reason code accordingly and save.", "Details are saved correctly.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Edit Enrollment and Eligibility section and update eligibility code and reason code accordingly and save.", "Details are not saved correctly.", true);
		}
		Web.clickOnElement(enrolAndEligibltyEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(enrollAndEligEditFrame);
		Web.getDriver().switchTo().frame(enrollAndEligEditFrame);
		String participationDate = partDate.getAttribute("value");
		DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date dt = dateFormat.parse(participationDate);
		Calendar calendar = Calendar.getInstance();         
		calendar.setTime(dt);
		calendar.add(Calendar.DATE, 1);
		String partDateInString  = dateFormat.format(calendar.getTime());
		Web.setTextToTextBox(partDate,partDateInString);
		if(selectedEligiCode.getText().equals("No"))
		{
			eligibilityCode.selectByVisibleText("Yes");
			inEligibilityCode.selectByValue("0");
			updatedEliCode = "Yes";
			updatedInEliCode = "";
		}
		else
		{
			eligibilityCode.selectByVisibleText("No");
			inEligibilityCode.selectByVisibleText("Other");
			updatedEliCode = "No";
			updatedInEliCode = "Other";
		}
		Web.clickOnElement(this.cancel);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		if(!updatedEliCode.equals(eligibilityCodeOverview.getText().trim())
				&&!updatedInEliCode.equals(inEligibilityCodeOverview.getText().trim())
				&&!partDateInString.equals(partDateOverview.getText().trim()))
		{
			Reporter.logEvent(Status.PASS, "Again click on Edit link and change eligibility code,Ineligibility code and participation date"
					+  " and click on cancel.", "Changes are not saved.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Again click on Edit link and change eligibility code,Ineligibility code and participation date"
					+  " and click on cancel.", "Changes are saved.", true);
		}
		}catch(Exception e){
			Reporter.logEvent(Status.FAIL,"Some error occurred.either Processing error or blank screen.",""
					+ "this may be due to bad data.kindly check manually once.", true);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(enrollEligCloseWindow);
		}
	}
	else
	{
		isEnrollmentSectionDisplayed = false;
		Reporter.logEvent(Status.FAIL,"Validate Enrollment and eligibility section is displayed.",""
				+"Enrollment and eligibility section is not displayed.It may be due to bad data.Please check with"
				+ " different participant.", true);
	}
	
	return isEnrollmentSectionDisplayed;
}


public boolean validateAccountBalanceScreen_0() throws Exception
{
	String expecDisclaimer = Stock.GetParameterValue("ExpectedDisclaimer");
	boolean isLink = false;
	boolean isBalance = false;
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	for(WebElement balance : balanceList)
	{
		if(!balance.getText().equals("N/A"))
		{
			String bal = balance.getText();
			System.out.println("Bal on Overview:"+bal);
			Thread.sleep(2000);
			Web.clickOnElement(balance.findElement(By.xpath("./../following-sibling::td/button")));
			CommonLib.waitForProgressBar();
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(viewAccountDetailFrame);
			Web.waitForElement(accountDetailHeader);
			WebElement expandCollapseMoneySource = moneySource.findElement(By.tagName("i"));
			WebElement expandCollapseInvestment = investment.findElement(By.tagName("i"));
			if(moneySource.isDisplayed()&&investment.isDisplayed())
			{
				Reporter.logEvent(Status.PASS,"User should be able to view balance by money source"
						+ " and balance by Investments.", "Money source and Investment sections are displayed.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"User should be able to view balance by money source"
						+ " and balance by Investments.", "Money source and Investment sections are displayed.", true);
			}
			
			if(bal.equals(balanceOnViewAccountPage.getText()))
			{
				Reporter.logEvent(Status.PASS,"Validate the balance displayed on overview page is same as displayed "
						+ "on Account details page.", "Total Balance value from Account Balance page matches Total "
								+ "balance as of {current date} on Overview page.", true);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validate the balance displayed on overview page is same as displayed "
						+ "on Account details page.", "Total Balance value from Account Balance page does not matche total "
								+ "balance as of {current date} on Overview page.", true);
			}
			if(!expandCollapseMoneySource.getAttribute("class").contains("down"))
			{
				Web.clickOnElement(expandCollapseMoneySource);
			}
			if(!expandCollapseInvestment.getAttribute("class").contains("down"))
			{
				Web.clickOnElement(expandCollapseInvestment);
			}
			if(disclaimerText.getText().trim().equalsIgnoreCase(expecDisclaimer))
			{
				Reporter.logEvent(Status.PASS,"Validate the note text down on Acount balance page."
						+ "It should be '* All dates and times are in Mountain Time unless otherwise noted.'", ""
								+ "Actual text displayed is:"+disclaimerText.getText().trim(), false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validate the note text down on Acount balance page."
						+ "It should be '* All dates and times are in Mountain Time unless otherwise noted.'", ""
								+ "Actual text displayed is:"+disclaimerText.getText().trim(), true);
			}
			if(moneyTypeLinks.size()>0){
				for(WebElement moneyTypeLink : moneyTypeLinks)
				{
					if(moneyTypeLink.getTagName().equals("a")){
						isLink = true;
					}
					else{
						isLink = false;
						break;
					}
				}
			}
			else
			{
				Reporter.logEvent(Status.INFO, "There is no money type found.", "No money type found.", true);
			}
			if(investTypeLinks.size()>0){
				for(WebElement investTypeLink : investTypeLinks)
				{
					if(investTypeLink.getTagName().equals("a")){
						isLink = true;
					}
					else{
						isLink = false;
						break;
					}
				}
			}
			else
			{
				Reporter.logEvent(Status.INFO, "There is no investment type found.", "No investment type found.", true);
			}
			if(isLink)
				Reporter.logEvent(Status.PASS,"Validate money type and investment type(if applicable) are hyperlinks.","" 
						+"Money type and investment type are found to be as hyperlinks.", false);
			
			else
			{
				Reporter.logEvent(Status.FAIL,"Validate money type and investment type(if applicable) are hyperlinks.","" 
						+"Money type and investment type are not found to be as hyperlinks.", true);
			}
			isBalance = true;
			break;
		}
		else
		{
			isBalance = false;
		}
	}
	return isBalance;
}


public void validateAccountBalanceScreen_1() throws Exception
{
	if(moneyTypeLinks.size()>0)
	{
		String moneyType = moneyTypeLinks.get(0).getText();
		Web.clickOnElement(moneyTypeLinks.get(0));
		Web.waitForElement(moneyTypeModalContent);
		if(modalContentHeader.getText().contains(moneyType.substring(0, moneyType.length()-1))&&investmentOptionDataTables.size()>0)
		{
			Reporter.logEvent(Status.PASS, "Click on one of the investment type and check if investment options for selected "
					+ "money type is displayed.", "Investment options for selected money type is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Click on one of the investment type and check if investment options for selected "
					+ "money type is displayed.", "Investment options for selected money type is not displayed.", true);
		}
		Web.clickOnElement(closeMoneyType);
		Web.waitForElement(moneySource);
	}
	if(investTypeLinks.size()>0)
	{
		String investmentType = investTypeLinks.get(0).getText();
		Web.clickOnElement(investTypeLinks.get(0));
		Web.waitForElement(investTypeModalContent);
		if(modalContentHeaderInvestment.getText().contains(investmentType.substring(0, investmentType.length()))&&investmentOptionDataTables.size()>0)
		{
			Reporter.logEvent(Status.PASS, "Click on one of the investment type and check if money sources for selected "
					+ "investment type is displayed.", "money sources for selected investment type is displayed.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Click on one of the investment type and check if money sources for selected "
					+ "investment type is displayed.", "money sources for selected investment type is not displayed.", true);
		}
		Web.clickOnElement(closeInvestType);
		Web.waitForElement(moneySource);
	}
	
}


public void ChangeDateAndVerifyData() throws Exception
{
	String[] balance = new String[2];
	String[] vestedBalance = new String[2];
	int headersize = monySrcHeader.size();
	String xpathforColumn = "//h4[contains(text(),'Money Source')]/ancestor::div[2]//table[2]//tr["+monySrcDataRows.size()+"]//td";
	List<WebElement> monySrcColumn = Web.getDriver().findElements(By.xpath(xpathforColumn));
	int columnsize = monySrcColumn.size();
	if(headersize==columnsize)
	{
		for(int k=0;k<headersize;k++)
		{
			if(monySrcHeader.get(k).getText().trim().equals("Balance"))
			{
			
				balance[0] = monySrcColumn.get(k).getText().trim();
			}
			if(monySrcHeader.get(k).getText().trim().equals("Vested Balance:"))
			{
				
				vestedBalance[0] =  monySrcColumn.get(k).getText().trim();
			}
		}
	}
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.YEAR, -2);
	SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
	String formatted = format1.format(cal.getTime());
	Web.setTextToTextBox(asOfDateInput,formatted);
	Web.waitForElement(moneySource);
	if(headersize==columnsize)
	{
		for(int k=0;k<headersize;k++)
		{
			if(monySrcHeader.get(k).getText().trim().equals("Balance"))
			{
				balance[1] = monySrcColumn.get(k).getText().trim();
			}
			if(monySrcHeader.get(k).getText().trim().equals("Vested Balance:"))
			{
				vestedBalance[1] = monySrcColumn.get(k).getText().trim();
			}
		}
	}
	if(!balance[0].equals(balance[1])&&!vestedBalance[0].equals(vestedBalance[1]))
	{
		Reporter.logEvent(Status.PASS, "Change the date to one year back and observe the total balance and vested balance.",""
				+"balance and vested balance is updated as date is changed.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Change the date to one year back and observe the total balance and vested balance.",""
				+"balance and vested balance is not updated as date is changed.", true);
	}
	
}

/*
 * This method disable access to PPT Web via PSC
 */
public boolean disablePAEURL() throws SQLException
{
	boolean isRecordExist = false;
	queryResultSet = DB.executeQuery(Stock.getTestQuery("CheckPortalTypecodeForEmpower")[0],
			Stock.getTestQuery("CheckPortalTypecodeForEmpower")[1]);
	if(DB.getRecordSetCount(queryResultSet)>0)
	{
		queryResultSet.close();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("DisablePAE")[0],
				Stock.getTestQuery("DisablePAE")[1]);
		isRecordExist = true;
	}
	else
	{
		isRecordExist = false;
		Reporter.logEvent(Status.INFO, "Check in DB if portal_type_code is set to PAE_URL for empower type accu_code.",""
				+"No record is found in DB for 'PAE_URL' portal_type_code", false);
	}
	return isRecordExist;
}


/*
 * This method validates the data set up error message if PAE_URL is not set up properly
 */
public void verifyDataSetErrorForDisabledPAEURL() throws InterruptedException,NoSuchWindowException
{
	String expectedError = Stock.GetParameterValue("ExpectedErrorMessage");
	String actualError = null;
	int count = 0;
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	String currentWindow =  Web.getDriver().getWindowHandle();
	System.out.println("Parent window"+currentWindow);
    Thread.sleep(3000);
    if(Web.isWebElementDisplayed(employeeWebButton,true)){
       Web.clickOnElement(employeeWebButton);             
       while(Web.getDriver().getWindowHandles().size()==1)
       {
              if(count==10) break;
              Thread.sleep(500);
              count++;
              System.out.println("Counter : "+count);
       }
       count=0;
       System.out.println("Window size is:"+Web.getDriver().getWindowHandles().size());
       for(String newWindow : Web.getDriver().getWindowHandles())
       {
    	   if(!newWindow.equals(currentWindow))
    	   {
    		   	Web.getDriver().switchTo().window(newWindow);
    		   	actualError = errorWebElement.getText().trim();
   				System.out.println("Actual Error Message:"+actualError);
   				Web.getDriver().close();
   				Web.getDriver().switchTo().window(currentWindow);
   				break;
    	   }
       }
       if(actualError.equals(expectedError))
				Reporter.logEvent(Status.PASS, "Execute the query:"+Stock.getTestQuery("DisablePAE")[1]+" and click on"
					+ "Employe web button.","Data set up error message is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Execute the query:"+Stock.getTestQuery("DisablePAE")[1]+" and click on"
					+ "Employe web button.","Data set up error message is not displayed.", true);
    }

	
	
}

/*
 * This method enables access to PPT Web via PSC
 */
public void enablePAEURL() throws SQLException
{
	queryResultSet = DB.executeQuery(Stock.getTestQuery("enablePAE")[0],
			Stock.getTestQuery("enablePAE")[1]);
		
}


/**
 * This method deletes the PSCPAE txn_code from all user class assigned to a user.
 */
public void deletePSCPAETxnCodeFromDB() throws SQLException
{
	queryResultSet = DB.executeQuery(Stock.getTestQuery("CheckPSCPAETxnCode")[0],Stock.getTestQuery("CheckPSCPAETxnCode")[1],
			"K_"+Stock.GetParameterValue("username"));
	if(DB.getRecordSetCount(queryResultSet)>0)
	{
		while(queryResultSet.next())
		{
			uscsID.add(queryResultSet.getString("USCS_ID"));
		}
		System.out.println("User class for PSCPAE txn_code are:"+uscsID);
	}
	else
	{
		queryResultSet.close();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("insertPSCPAETxnCode")[0],Stock.getTestQuery("insertPSCPAETxnCode")[1]);
		queryResultSet.close();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("CheckPSCPAETxnCode")[0],Stock.getTestQuery("CheckPSCPAETxnCode")[1],
				"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next())
		{
			uscsID.add(queryResultSet.getString("USCS_ID"));
		}
	}
	queryResultSet.close();
	for(int i=0;i<uscsID.size();i++){
		queryResultSet = DB.executeQuery(Stock.getTestQuery("deletePSCPAETxnCode")[0],Stock.getTestQuery("deletePSCPAETxnCode")[1],uscsID.get(i));
	}
}


/*
 * This method validates the Employee Web button is displayed or not on Employee overview page after deleting respective transaction code
 */
public boolean validateEmployeeWebButton()
{
	boolean isEmployeeWebBtnDisplayed = false;
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(Web.getDriver().findElements(By.id("paeUrlButton")).size() != 0)
		isEmployeeWebBtnDisplayed = true;
	else
		isEmployeeWebBtnDisplayed = false;
	return isEmployeeWebBtnDisplayed;
}


/**
 * This method inserts back the PSCPAE txn_code from all user class assigned to a user.
 */
public void insertPSCPAETxnCode() throws SQLException,Exception
{
	
	for(int i=0;i<uscsID.size();i++){
		Thread.sleep(2000);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("insertPSCPAETxnCode_1")[0],Stock.getTestQuery("insertPSCPAETxnCode_1")[1],uscsID.get(i));
	}
	queryResultSet.close();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("CheckPSCPAETxnCode")[0],Stock.getTestQuery("CheckPSCPAETxnCode")[1],
			"K_"+Stock.GetParameterValue("username"));
	if(DB.getRecordSetCount(queryResultSet)>0)
	{
		Reporter.logEvent(Status.PASS, "Execute query to insert back PSCPAE txn_code:'"+Stock.getTestQuery("insertPSCPAETxnCode_1")[1]+"'.",""
				+"Record is inserted.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Execute query to insert back PSCPAE txn_code:'"+Stock.getTestQuery("insertPSCPAETxnCode_1")[1]+"'.",""
				+"Record is not inserted.please Check once manually in DB.", false);
	}
	
}


/**
 * @author smykjn
 * @Date 13-April-2017
 * @Objective this method checks that Process center and Compliance tab is not displayed for TRS-Flex plan and displayed for<br>
 * Non TRS-Flex plans
 */
public boolean validateComplianceAndProCenterTabForTRSFlex() throws NoSuchElementException
{
	
	boolean isTabExist = false;
	Web.getDriver().switchTo().defaultContent();
	if(proCenterMenu.isDisplayed()&&compMenu.isDisplayed())
		isTabExist = true;
	else
		isTabExist = false;
	return isTabExist;
	
}

/**
 * @author smykjn
 * @Date 13-April-2017
 * @Objective This method is to validate all basic validations related to TRSFlex plan :Like<br>
 * Plan-->Administration--->Transfer restriction must not be displayed for TRSFlex plan
 */
public void validationsForTRSFlexPlan() 
{
	try{
		Actions act = new Actions(Web.getDriver());
		act.click(Web.returnElement(new HomePage(), "Plan Menu")).build().perform();
		act.click(Web.returnElement(new HomePage(), "Plan Administration")).build().perform();
		if(transferRestrictions.isDisplayed())
			Reporter.logEvent(Status.FAIL, "Navigate to Plan/Administration menu and check Transfer restriction"
					+ " is supressed for TRS-Flex plan.", "Transfer restriction is displayed for TRS-Flex plan.", true);
		else
			Reporter.logEvent(Status.PASS, "Navigate to Plan/Administration menu and check Transfer restriction"
					+ " is supressed for TRS-Flex plan.", "Transfer restriction is supressed for TRS-Flex plan.", false);
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}

/**
 * @author smykjn
 * @Date 13-April-2017
 * @Objective This method is to validate all basic validations related to Non-TRSFlex plan :Like<br>
 * Plan-->Administration--->Transfer restriction must  be displayed for Non-TRSFlex plan
 */
public void validationsForNonTRSFlexPlan() 
{
	try{
		Actions act = new Actions(Web.getDriver());
		act.click(Web.returnElement(new HomePage(), "Plan Menu")).build().perform();
		Web.waitForElement(Web.returnElement(new HomePage(), "Plan Administration"));
		act.click(Web.returnElement(new HomePage(), "Plan Administration")).build().perform();
		if(transferRestrictions.isDisplayed())
			Reporter.logEvent(Status.PASS, "Navigate to Plan/Administration menu and check Transfer restriction"
					+ " is displayed for non TRS-Flex plan.", "Transfer restriction is displayed for non TRS-Flex plan.", false);
		else
			Reporter.logEvent(Status.FAIL, "Navigate to Plan/Administration menu and check Transfer restriction"
					+ " is displayed for non TRS-Flex plan.", "Transfer restriction is supressed for non TRS-Flex plan.", true);
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}

/**
 * @author smykjn
 * @Date 13-April-2017
 * @Objective This method switches between employee from non TRS Plan employee to TRS-Flex plan employee and validates that<br>
 * top menu 'Process center' and 'Compliance' must be suppressed after switch and Transfer restrictions should not be<br>
 * displayed for TRS-Flex plan employee.
 */
public void switchEmployeeAndValidateTopMenu() 
{
	String planName = null;
	String empName = null;
	try{
		if(proCenterMenu.isDisplayed()&&compMenu.isDisplayed())
			Reporter.logEvent(Status.PASS, "Navigate to employee overview page of an employee who is in"
					+ " non TRS-Flex plan and observe Process center and compliance tabs are displayed.", "Process center and "
							+ " compliance tabs are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Navigate to employee overview page of an employee who is in"
					+ " non TRS-Flex plan and observe Process center and compliance tabs are displayed.", "Process center and "
							+ " compliance tabs are not displayed.", true);
		
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		planName = selectedPlanHeader.getText().trim();
		Web.clickOnElement(recentlyViewedLink);
		Web.waitForElement(recentlyViewedEmpTable);
		empName = empNameHeader.getText().trim();
		Web.clickOnElement(recentlyViewedEmployee.get(1));//switch to TRS-Flex employee from recently viewed link
		CommonLib.waitForProgressBar();
		Thread.sleep(3000);
		if(this.validateComplianceAndProCenterTabForTRSFlex())
			Reporter.logEvent(Status.FAIL, "Switch to employee overview page of an employee who is in"
					+ " TRS-Flex plan and observe Process center and compliance tabs are suppressed.", "Process center and "
							+ " compliance tabs are displayed.", true);
		else
			Reporter.logEvent(Status.PASS, "Switch to employee overview page of an employee who is in"
				+ " TRS-Flex plan and observe Process center and compliance tabs are suppressed.", "Process center and "
						+ " compliance tabs are suppressed.", false);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(!planName.equals(selectedPlanHeader.getText().trim())&&!empName.equals(empNameHeader.getText().trim()))
			Reporter.logEvent(Status.PASS, "After switching to non TRS-Flex plan's employee to TRS-Flex plan "
					+ " employee,verify plan name and employee name is also updated.", "Plan name and employee name is updated.", false);
		
		else
			Reporter.logEvent(Status.FAIL, "After switching to non TRS-Flex plan's employee to TRS-Flex plan "
					+ " employee,verify plan name and employee name is also updated.", "Plan name and employee name is not updated.", true);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}



/**
 * @author smykjn
 * @date 17th-April-2017
 * @Objective This test case searches for sections present in Account detail tab for TRSFlex plans
 * @return void<br>
 * 
 */
public void validateAccountDetailSectionsTRSFlexPlan() throws Exception
{
	this.navigateToAccountDetailPage();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(CommonLib.isElementExistByXpath(transHistory)&&CommonLib.isElementExistByXpath(interactions))
		Reporter.logEvent(Status.PASS, "Navigate to employee overview page of an employee who is in TRSFlex plan"
				+ " and check only Transaction hitory and Interactions sections are displayed under Account detail.",""
				+"Transaction history and Interactions are displayed.", false);
	else
		Reporter.logEvent(Status.FAIL, "Navigate to employee overview page of an employee who is in TRSFlex plan"
				+ " and check only Transaction hitory and Interactions sections are displayed under Account detail.",""
				+"Transaction history and Interactions are not displayed.", true);
	if(!CommonLib.isElementExistByXpath(investments)&&!CommonLib.isElementExistByXpath(fees)&&
			!CommonLib.isElementExistByXpath(loans)&&!CommonLib.isElementExistByXpath(vesting)&&
			!CommonLib.isElementExistByXpath(beneficiarySection))
		Reporter.logEvent(Status.PASS, "Investments,Fees,Loans,Vesting,Beneficiary sections should not"
				+ " be displayed for TRSFlex plan as Account detail elements.", "Mentioned sections are not displayed.", false);
	
	else
		Reporter.logEvent(Status.FAIL, "Investments,Fees,Loans,Vesting,Beneficiary sections should not"
				+ " be displayed for TRSFlex plan as Account detail elements.", "Mentioned sections or any of them , are displayed.", true);
	Web.getDriver().switchTo().defaultContent();
}


/**
 * @author smykjn
 * @date 17th-April-2017
 * @Objective This test case searches for sections present in Employee detail tab for TRSFlex plans
 * @return void
 */
public void validateEmployeeDetailSectionsTRSFlexPlan() throws Exception
{
	this.navigateToEmpDetailPage();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(CommonLib.isElementExistByXpath(compensation)&&CommonLib.isElementExistByXpath(payCheckContriSection)
			&&CommonLib.isElementExistByXpath(basicInfoSection)&&CommonLib.isElementExistByXpath(contactInfoSection)
			&&CommonLib.isElementExistByXpath(employmentInfoSection))
		Reporter.logEvent(Status.PASS, "Navigate to employee overview page of an employee who is in TRSFlex plan"
				+ " and check following sections are exist in Employee details tab:Compensation,Paycheck contribution"
				+ ",Basic Information,Contact Information,Employment Information",""
				+"All the mentioned sections are exist as Employee detail elements.", false);
	else
		Reporter.logEvent(Status.FAIL, "Navigate to employee overview page of an employee who is in TRSFlex plan"
				+ " and check following sections are exist in Employee details tab:Compensation,Paycheck contribution"
				+ ",Basic Information,Contact Information,Employment Information",""
				+"All the mentioned sections or few of the sections do not exist as Employee detail elements.", true);
	if(!CommonLib.isElementExistByXpath(empLeaveInfoSection)&&!CommonLib.isElementExistByXpath(enrollAndEligSection))
		Reporter.logEvent(Status.PASS, "'Employee Leave Information' and 'Enrollment and Eligibility' sections should"
				+ " be suppressed for TRSFlex plan as employee detail elements.", "Mentioned sections are found to be suppressed.", false);
	
	else
		Reporter.logEvent(Status.FAIL, "Investments,Fees,Loans,Vesting,Beneficiary sections should"
				+ " be suppressed for TRSFlex plan as employee detail elements.", "Mentioned sections or any of them , are not suppressed.", true);
	
	Web.getDriver().switchTo().defaultContent();
}


/**
 * @author smykjn
 * @date 17th-april-2017
 * @Objective This test case searches for sections present in Account detail tab for NonTRSFlex plans
 * @return void
 */
public void validateAccountDetailSectionsNonTRSFlexPlan() throws Exception
{
	this.navigateToAccountDetailPage();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(CommonLib.isElementExistByXpath(transHistory)&&CommonLib.isElementExistByXpath(interactions)
			&&CommonLib.isElementExistByXpath(investments)&&CommonLib.isElementExistByXpath(fees)
			&&CommonLib.isElementExistByXpath(loans)&&CommonLib.isElementExistByXpath(vesting)
			&&CommonLib.isElementExistByXpath(beneficiarySection))
		Reporter.logEvent(Status.PASS, "Navigate to employee overview page of an employee who is in NonTRSFlex plan"
				+ " and check following sections are exist in Account detail tab:Transaction history,Interactions"
				+ ",Loans,Vesting,Beneficiary,fees,Investment",""
				+"All the mentioned sections are displayed as Account detail elements.", false);
	else
		Reporter.logEvent(Status.FAIL, "Navigate to employee overview page of an employee who is in NonTRSFlex plan"
				+ " and check following sections are exist in Account detail tab:Transaction history,Interactions"
				+ ",Loans,Vesting,Beneficiary,fees,Investment",""
				+"All or few of the sections are not displayed as Account detail elements.", true);
	Web.getDriver().switchTo().defaultContent();
}

/**
 * @author smykjn
 * @date 17th-April-2017
 * @Objective This test case searches for sections present in Employee detail tab for NonTRSFlex plans
 * @return void
 */
public void validateEmployeeDetailSectionsNonTRSFlexPlan() throws Exception
{
	this.navigateToEmpDetailPage();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(CommonLib.isElementExistByXpath(compensation)&&CommonLib.isElementExistByXpath(payCheckContriSection)
			&&CommonLib.isElementExistByXpath(empLeaveInfoSection)&&CommonLib.isElementExistByXpath(basicInfoSection)
			&&CommonLib.isElementExistByXpath(contactInfoSection)&&CommonLib.isElementExistByXpath(employmentInfoSection)
			&&CommonLib.isElementExistByXpath(enrollAndEligSection))
		Reporter.logEvent(Status.PASS, "Navigate to employee overview page of an employee who is in NonTRSFlex plan"
				+ " and check following sections are exist in employee detail tab:Compensation,Paycheck contribution"
				+ ",Employee leave information,Basic information,contact information,employement information,Enrollment and eligibility.",""
				+"All the mentioned sections are displayed as employee detail elements.", false);
	else
		Reporter.logEvent(Status.FAIL, "Navigate to employee overview page of an employee who is in NonTRSFlex plan"
				+ " and check following sections are exist in employee detail tab:Compensation,Paycheck contribution"
				+ ",Employee leave information,Basic information,contact information,employement information,Enrollment and eligibility.",""
				+"All or few of the mentioned sections are not displayed as employee detail elements.", true);
	Web.getDriver().switchTo().defaultContent();
}

/**
 * @author smykjn
 * @date 17th-April-2017
 * @Objective This test case validates sections present in Employee detail and account detail tabs when employee is 
 * switched from Non TRSFlex plan to employee in TRSFlex plan.
 * @return void
 */
public void validateEmpDetailAccDetail_SwitchEmploye() throws Exception
{
	Reporter.logEvent(Status.INFO, "switch to participant who is in TRSFlex plan through recently viewed and again verify "
			+ "all the employee detail and account detail sections that TRSFlex plan access to are displayed.", "", false);
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.clickOnElement(this.recentlyViewedLink);
	Web.waitForElement(recentlyViewedEmpTable);
	Web.clickOnElement(recentlyViewedEmployee.get(1));
	CommonLib.waitForProgressBar();
	Web.getDriver().switchTo().defaultContent();
	this.validateAccountDetailSectionsTRSFlexPlan();
	this.validateEmployeeDetailSectionsTRSFlexPlan();
	
}


/**
 * @author smykjn
 * @date 18th-April-2017
 * @Objective This test case validates Investments section(Columns) present under Account detail tab
 * @return boolean
 */
public boolean validateInvestmentsSectionElements()
{
	boolean proceed = false;
	boolean isHeaderExist = false;
	boolean isMoreDisplayed = false;
	List<String> expHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedHeaders").split(","));
  try{
	  	this.navigateToAccountDetailPage();
	  	Web.getDriver().switchTo().frame(employeeSearchFrame);
	  	if(CommonLib.isElementExistByXpath(investments))
	  	{
	  		proceed = true;
	  		Reporter.logEvent(Status.PASS, "Validate Investment section is displayed under Account detail.","Investment section is"
	  				+ " dislayed.", false);
	  		Web.clickOnElement(Web.getDriver().findElement(By.xpath(investments)));
	  		Web.waitForElement(allocMoreButton);
	  		for(WebElement header : allocationHeaders)
	  		{
	  			if(expHeaders.contains(header.getText().trim())){
	  				isHeaderExist = true;}
	  			else{
	  				isHeaderExist = false;
	  				break;
	  			}
	  		}
	  		isMoreDisplayed = allocMoreButton.isDisplayed();
	  		if(isHeaderExist&&isMoreDisplayed)
	  			Reporter.logEvent(Status.PASS, "Verify following Investment details displayed in Investment tab."
	  					+ "1.Investment option 2.Fund short name 3.Deposit period 4.Default indicator 5.Percent and 'More' button.", ""
	  							+ "All the mentioned details are displayed.", false);
	  		else
	  			Reporter.logEvent(Status.FAIL, "Verify following Investment details displayed in Investment tab."
	  					+ "1.Investment option 2.Fund short name 3.Deposit period 4.Default indicator 5.Percent and 'More' button.", ""
	  							+ "All the mentioned details or few of the details are not displayed.", true);
	  		
	  	}
	  	else
	  	{
	  		proceed = false;
	  		Reporter.logEvent(Status.FAIL, "Validate Investment section is displayed under Account detail.","Investment section is"
	  				+ " not dislayed.", true);
	  		
	  	}
	  	return proceed;
  }
  catch(Exception e)
  {
	  e.printStackTrace();
	  return proceed;
  }
	
	
}

/**
 * @author smykjn
 * @date 18th-April-2017
 * @Objective This method validates <br>1.descending order by Percent of records under investment under Account detail<br>
 * 2.Max 5 records can fit for investment section
 * @return void
 */
public void validateInvestmentsPercentOrderAndMaxRecords()
{
	boolean sorted = false;
	List<Double> investmentPercents = new ArrayList<Double>();
	try{
	  	for(WebElement percent : investPercentList)
	  	{
	  		investmentPercents.add(Double.parseDouble(percent.getText().replace("%", "").trim()));
	  	}
	 	System.out.println(investmentPercents);
	 	sorted = CommonLib.isSortedByDescOrder(investmentPercents);
	  	if(sorted)
	  		Reporter.logEvent(Status.PASS, "Validate records are sorted by percent in descending order.", "Reocrds are sorted by percent in"
	  				+ " descending order."+investmentPercents, false);
	  	else
	  		Reporter.logEvent(Status.FAIL, "Validate records are sorted by percent in descending order.", "Reocrds are not sorted by percent in"
	  				+ " descending order."+investmentPercents, true);
	  	//To validate max allocation row under investment section under account detail
	  	if(maxAllocationsRow.size()<=5)
	  		Reporter.logEvent(Status.PASS, "Validate max 5 allocations can be shown under investment section.", ""
	  				+ "5 allocations or less than 5, are shown.", false);
	  	else
	  		Reporter.logEvent(Status.FAIL, "Validate max 5 allocations can be shown under investment section.", ""
	  				+ "more than 5 allocations are shown.", true);
	  	
  }
  catch(Exception e)
  {
	  e.printStackTrace();
  }
	
	
}

/**
 * @author smykjn
 * @date 19th-April-2017
 * @Objective This method validates message when no allocations for a participant
 * @return void
 */
public void validateNoAllocation()
{
	String expectedMsg = Stock.GetParameterValue("ExpectedMessage");
	try{
		this.searchEmployeeBySSNAllPlans("345654695");//employee without allocation
		this.navigateToEmployeeOverViewPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(Web.getDriver().findElement(By.xpath(investments)));
  		Web.waitForElement(allocMoreButton);
  		System.out.println(noallocations.getText());
  		if(CommonLib.VerifyText(expectedMsg, noallocations.getText().trim(), true))
  			Reporter.logEvent(Status.PASS, "Search an Employee who has no allocations.", "message,'"+expectedMsg+"' is displayed.", false);
  		else
  			Reporter.logEvent(Status.FAIL, "Search an Employee who has no allocations.", "message,'"+expectedMsg+" is not displayed.", true);
}
  catch(Exception e)
  {
	  e.printStackTrace();
  }
}


/**
 * @author smykjn
 * @date 19th-April-2017
 * @Objective This method validates if user has access to Add/Change allocations button.
 * @return boolean <br> returns true if Add/Change Allocations button is displayed else returns false
 */
public boolean validateAddAllocationBtnIfUserHasAccess()
{
	Set<String> txn_code = new HashSet<String>();
	boolean isCodeExist = false;
	boolean isChangeBtnDisplayed = false;
	try{
		queryResultSet = DB.executeQuery(Stock.getTestQuery("checkESCCPAtxnCode_1")[0],Stock.getTestQuery("checkESCCPAtxnCode_1")[1],
				"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next())
		{
			txn_code.add(queryResultSet.getString("TXN_CODE"));
			System.out.println("code is:"+txn_code);
		}
		if(txn_code.contains("ESCCPA")&&txn_code.contains("ESCPAL"))
			isCodeExist = true;
		else
			isCodeExist = false;
		if(isCodeExist){
			Reporter.logEvent(Status.PASS, "Make sure user has assigned with 'ESCCPA' and 'ESCPAL' txn codes.","" 
					+"user is assigned with codes:"+txn_code, false);
			Web.getDriver().switchTo().defaultContent();
			//navigateToEmployeeOverViewPage();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(Web.getDriver().findElement(By.xpath(investments)));
			Web.waitForElement(allocMoreButton);
			if(allocMoreButton.isDisplayed())
				Reporter.logEvent(Status.PASS, "Validate more button is displayed.", "more button is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate more button is displayed.", "more button is not displayed.", true);
			Web.clickOnElement(allocMoreButton);
			Web.waitForElement(allocModalWindowFrame);
			Web.getDriver().switchTo().frame(allocModalWindowFrame);
			Web.waitForElement(changeAllocBtn);
			if(changeAllocBtn.isDisplayed()){
				Reporter.logEvent(Status.PASS, "Validate Add/Change Allocations button is displayed.","" 
						+"Add/Change Allocations button is displayed.", false);
				isChangeBtnDisplayed = true;
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validate Add/Change Allocations button is displayed.","" 
						+"Add/Change Allocations button is not displayed.", true);
				isChangeBtnDisplayed = false;
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Make sure user has assigned with 'ESCCPA' and 'ESCPAL' txn codes.", 
					"user is assigned with codes:"+txn_code, false);
		}
		
		return isChangeBtnDisplayed;
		
}
  catch(Exception e)
  {
	  e.printStackTrace();
	  return isChangeBtnDisplayed;
  }
}


/**
 * @author smykjn
 * @date 19th-April-2017
 * @Objective This method add a new allocation and validates the elements(save,cancel button,input percentage fields)
 * @return void
 */
public void addAllocation()
{
	String invsetOptionAfterSave = null;
	String percentageAfterSave = null;
	try{
		Web.clickOnElement(changeAllocBtn);
		Web.waitForElement(changeAllocPage);
		if(changeAllocPage.isDisplayed())
			Reporter.logEvent(Status.PASS, "In the allocations modal window click on 'Add/Change Allocations' button"
					+ " and validate if 'Enter New allocations for future contributions page is displayed.'", ""
							+"'Enter New allocations for future contributions page is displayed page' is displayed.", false);
		else
			Reporter.logEvent(Status.PASS, "In the allocations modal window click on 'Add/Change Allocations' button"
					+ " and validate if 'Enter New allocations for future contributions page is displayed.'", ""
							+"'Enter New allocations for future contributions page is displayed page' is not displayed.", true);
		if(Web.isWebElementsDisplayed(listOfInPercentageField, true)&&
				save.isDisplayed()&&cancel.isDisplayed()&&totalPercent.isDisplayed())	
			Reporter.logEvent(Status.PASS,"Validate save,cancel,total % and input fields to enter allocation% is displayed.","" 
					+"All mentioned elelemts are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate save,cancel and input fields to enter allocation% is displayed.","" 
					+"All mentioned or few of the elelemts are not displayed.", true);
		List<WebElement> columns = allocationsRows.get(0).findElements(By.tagName("font"));
		System.out.println(columns.size());
		String percentBeforeSave = "100";
		String investOptionBeforeSave = columns.get(0).getText().trim();
		System.out.println("Before save investment option:"+investOptionBeforeSave);
		if(newAllocRadioButton.isDisplayed()){
			Web.clickOnElement(newAllocRadioButton);
			Thread.sleep(2000);
			Web.setTextToTextBox(columns.get(3).findElement(By.tagName("input")), percentBeforeSave);
		}else{
			Web.setTextToTextBox(columns.get(3).findElement(By.tagName("input")), percentBeforeSave);}
		try{
		Web.clickOnElement(save);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(allocationModalClose);
		Web.waitForElement(allocMoreButton);
		List<WebElement> columnsAfterSave = maxAllocationsRow.get(0).findElements(By.tagName("td"));
		invsetOptionAfterSave  = columnsAfterSave.get(0).getText().trim();
		percentageAfterSave = columnsAfterSave.get(4).findElement(By.tagName("span")).getText().replace("%","").trim();
		System.out.println("After save investment:"+invsetOptionAfterSave+" and after save %:"+percentageAfterSave);
		if(invsetOptionAfterSave.contains(investOptionBeforeSave)&&percentageAfterSave.equals(percentBeforeSave+".00"))
			Reporter.logEvent(Status.PASS, "Add an investment '"+investOptionBeforeSave+"' with 100% allocation and save it.close the modal window and validate"
					+ " allocation is updated on account detail page.", "Allocation % is updated with investment option"+invsetOptionAfterSave, false);
		else
			Reporter.logEvent(Status.FAIL, "Add an investment '"+investOptionBeforeSave+"' with 100% allocation and save it.close the modal window and validate"
					+ " allocation is updated on account detail page.", "Allocation % is not updated with investment option"+invsetOptionAfterSave, true);
		Web.getDriver().switchTo().defaultContent();
		}catch(Exception e){
			Reporter.logEvent(Status.WARNING, "error message displayed:"+processingError.getText(),""
					+"It may be due to bad data issue.Please check manually with other data.", true);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(allocationModalClose);
			CommonLib.waitForProgressBar();
		}
	}
  catch(Exception e)
  {
	  e.printStackTrace();
  }
}

/**
 * @author smykjn
 * @date 19th-April-2017
 * @Objective This method deletes ESCCPA txn_code if user is assigned with same code.
 * @return void
 */
public void deleteAdd_ChangeAllocationTxn_Code() throws SQLException
{
	uscsIDReset = new ArrayList<String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("checkESCCPAtxnCode")[0], Stock.getTestQuery("checkESCCPATxnCode")[1],"" 
			+"K_"+Stock.GetParameterValue("username"));
	if(DB.getRecordSetCount(queryResultSet)>0)
	{
		while(queryResultSet.next())
		{
			uscsIDReset.add(queryResultSet.getString("USCS_ID"));
		}
		System.out.println("User class for ESCCPA txn_code are:"+uscsIDReset);
		for(int i=0;i<uscsIDReset.size();i++){
			queryResultSet = DB.executeQuery(Stock.getTestQuery("DeleteESCCPAtxnCode")[0], Stock.getTestQuery("DeleteESCCPAtxnCode")[1],"" 
					+"K_"+Stock.GetParameterValue("username"));
		}
	}
	else
	{
		System.out.println("User is not assigned with ESCCPA txn_code so no need to delete from DB.");
	}
}

/**
 * @author smykjn
 * @Date 20th-April-2017
 * @Objective This method validates Add/Change Allocations button is not displayed if ESCCPA txn_code is not assigned to user
 */
public void validateAdd_ChangeAllocIfNoAccess()
{
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	Web.clickOnElement(Web.getDriver().findElement(By.xpath(investments)));
	Web.waitForElement(allocMoreButton);
	if(allocMoreButton.isDisplayed())
		Reporter.logEvent(Status.PASS, "Validate more button is displayed is user is not having access to ESCCPA txn_code.", "more button is displayed.", false);
	else
		Reporter.logEvent(Status.FAIL, "Validate more button is displayed is user is not having access to ESCCPA txn_code", "more button is not displayed.", true);
	Web.clickOnElement(allocMoreButton);
	Web.waitForElement(allocModalWindowFrame);
	Web.getDriver().switchTo().frame(allocModalWindowFrame);
	Web.waitForElement(changeAllocBtn);
	if(CommonLib.isElementExistByXpath("//input[@name='CHG_ALLOC_BTN']")){
		Reporter.logEvent(Status.FAIL, "Validate Add/Change Allocations button is displayed.","" 
				+"Add/Change Allocations button is displayed.", true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(allocationModalClose);
	}
	else{
		Reporter.logEvent(Status.PASS, "Validate Add/Change Allocations button is displayed.","" 
				+"Add/Change Allocations button is not displayed.", false);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(allocationModalClose);
	}
	Web.getDriver().switchTo().defaultContent();
}


/**
 * @author smykjn
 * This method inserts back the ESCCPA txn_code for user class it was assigned before deleting.
 */
public void insertESCCPATxnCode() throws SQLException,Exception
{
	
	for(int i=0;i<uscsIDReset.size();i++){
		Thread.sleep(2000);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("insertESCCPAtxnCode")[0],
				Stock.getTestQuery("insertESCCPAtxnCode")[1],uscsIDReset.get(i));
	}
	uscsIDReset.clear();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("checkESCCPAtxnCode")[0],
			Stock.getTestQuery("checkESCCPAtxnCode")[1],"K_"+Stock.GetParameterValue("username"));
	
	while(queryResultSet.next())
	{
		uscsIDReset.add(queryResultSet.getString("TXN_CODE"));
	}
		
	if(DB.getRecordSetCount(queryResultSet)>0&&uscsIDReset.contains("ESCCPA"))
	{
		Reporter.logEvent(Status.PASS, "Execute query to insert back ESCCPA txn_code:'"+Stock.getTestQuery("insertESCCPAtxnCode")[1]+"'.",""
				+"Record is inserted.", false);
	}
	else
	{
		Reporter.logEvent(Status.FAIL, "Execute query to insert back ESCCPA txn_code:'"+Stock.getTestQuery("insertESCCPAtxnCode")[1]+"'.",""
				+"Record is not inserted.please Check once manually in DB.", false);
	}
	
}

/**
 * @author smykjn
 * @Date 20th-April-2017
 * @Objective This method navigates to Statements tab
 * @return boolean, true if content inside statement tab is displayed.false if there is no content available
 */
public boolean navigateToStatementTab() throws Exception
{
	boolean isContentDisplayed = false;
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(partStatementTab.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
	{
		Web.getDriver().switchTo().defaultContent();
	}
	else
	{
		Web.waitForElement(partStatementTab);
		Thread.sleep(2000);
		Web.clickOnElement(partStatementTab);
		Web.waitForPageToLoad(Web.getDriver());
	}
	if(statementContent.isDisplayed()){
		Reporter.logEvent(Status.PASS, "Navigate to statement tab on overview page.", "Statement page is displayed.", false);
		Web.getDriver().switchTo().defaultContent();
		isContentDisplayed = true;}
	else{
		Reporter.logEvent(Status.FAIL, "Navigate to statement tab on overview page.", "Statement page is not displayed.", true);
		isContentDisplayed = false;}
	return isContentDisplayed;
}


/**
 * @author smykjn
 * @Date 20th-April-2017
 * @Objective This method validates the statement tab column header names,PDF must open in different Tab,Sorting of 
 * records by Date descending order
 * @return void
 */
public void validateStatementTabHeaderAndDateSorting() 
{
	List<String> expHeader = Arrays.asList(Stock.GetParameterValue("ExpectedHeader").split(","));
	int windowSize = 0;
	boolean isHeaderExist = false;
	String currentWindow = null;
	Set<String> childWindows;
	try{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		for(WebElement header : stmntHeader)
		{
			if(expHeader.contains(header.getText().trim())){
				isHeaderExist = true;}
			else{
				isHeaderExist = false;break;}
		}
		if(isHeaderExist)
			Reporter.logEvent(Status.PASS, "Validate Columns under Statement tab as following:"+expHeader,""+
					"Expected Columns are found.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate Columns under Statement tab as following:"+expHeader, ""+
					"Expected Columns are not found.", true);
		currentWindow = Web.getDriver().getWindowHandle();
		Web.clickOnElement(statementContent.findElements(By.tagName("a")).get(0));
		windowSize = Web.getDriver().getWindowHandles().size(); 
		childWindows = Web.getDriver().getWindowHandles();
		System.out.println("Window size is:"+windowSize);
		for(String chlWindow : childWindows)
		{
			if(!chlWindow.equals(currentWindow))
			{
				Web.getDriver().switchTo().window(chlWindow);
				Web.getDriver().close();
			}
		}
		Web.getDriver().switchTo().window(currentWindow);
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(windowSize==2)
			Reporter.logEvent(Status.PASS,"Click on one of the PDF and validate PDF must be displayed in different Tab.",""
					+"PDF is opened in different tab/Window.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on one of the PDF and validate PDF must be displayed in different Tab.",""
					+"PDF is not opened in different tab/window.", true);
		this.validateSortingOrderOfDateOfStmt();
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}



/**
 * @author smykjn
 * @Date 20th-April-2017
 * @Objective This method validates filter functionality of Statement 'SHOW' drop down
 * @return void
 */
public void validateStatementFilter() 
{
	List<WebElement> stmtFilterOptions = new ArrayList<WebElement>();
	List<String> stmtFilterOptionsStr = new ArrayList<String>();
	boolean isStatementDisplayed = false;
	boolean isStatementDescDisplayed = false;
	try{
		if(stmtDropDown.isDisplayed())
		{
			Select stmtDrpdwn = new Select(stmtDropDown);
			stmtFilterOptions = stmtDrpdwn.getOptions();
			for(WebElement option : stmtFilterOptions)
			{
				stmtFilterOptionsStr.add(option.getText());
			}
			for(int i=0;i<stmtFilterOptions.size();i++)
			{
				String option = stmtFilterOptions.get(i).getText();
				stmtDrpdwn.selectByVisibleText(option);
				CommonLib.waitForProgressBar();
				if(option.equals("All"))
				{
					for(WebElement category : stmtCategory)
					{
						
						if(stmtFilterOptionsStr.contains(category.getText().trim())){
							isStatementDisplayed = true;}
						else{isStatementDisplayed=false;break;}
					}
				}
				else
				{
					for(WebElement category : stmtCategory)
					{
						if(category.getText().trim().equals(option)){
							isStatementDisplayed = true;}
						else{isStatementDisplayed=false;break;}
						if(category.getText().trim().equals("Statement"))
						{
							for(WebElement desc : stmtDescription)
							{
								if(desc.getText().contains("your Statement ending")&&desc.getText().contains("is ready"))
									isStatementDescDisplayed = true;
								else
									isStatementDescDisplayed = false;
							}
							if(isStatementDescDisplayed)
								Reporter.logEvent(Status.PASS, "Validate the Statement description 'Your Statement ending '(mm/dd/yyyy)' is ready' for Statement type category.",""
										+"Expected Description for Statement type category is displayed.", false);
							else
								Reporter.logEvent(Status.FAIL, "Validate the Statement description 'Your Statement ending '(mm/dd/yyyy)' is ready' for Statement type category.",""
										+"Expected Description for Statement type category is not displayed.", true);
						}
					}
				}
				if(isStatementDisplayed)
					Reporter.logEvent(Status.PASS, "Select '"+option+"' category from dropdown and check all records"
							+ " must be filtered with '"+option+"' category.", "All records are filtered with '"+option+"' category.", false);
				else
					Reporter.logEvent(Status.FAIL, "Select '"+option+"' category from dropdown and check all records"
							+ " must be filtered with '"+option+"' category.", "All records are not filtered with '"+option+"' category.", true);
			}
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Validate Filter dropdown is displayed.", "Filter drop down is not displayed.", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred.",e.getMessage(), true);
	}
}


/**
 * @author smykjn
 * @Date 20th-April-2017
 * @Objective This method validates the Date of statement must be in Chronological descending order
 * @return void
 */
public void validateSortingOrderOfDateOfStmt() 
{
	List<String> dateStringList = new ArrayList<String>();
	List<Date> dateList = new ArrayList<Date>();
	try{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		for(WebElement dateString : stmtDateStringList)
		{
			dateStringList.add(dateString.getText().trim());
		}
		System.out.println("before string to date conversion:"+dateStringList);
		for(String str : dateStringList)
		{
			dateList.add(simpleDateFormat.parse(str));
		}
		List<Date> dateListOriginalOrder = new ArrayList<Date>(dateList);
		System.out.println("After string to date conversion:"+dateList);
		System.out.println("Copy List to another list:"+dateListOriginalOrder);
		Collections.sort(dateList);
		System.out.println("After sorting:"+dateList);
		Collections.reverse(dateList);
		System.out.println("After reverse sorting:"+dateList);
		if(dateListOriginalOrder.equals(dateList))
			Reporter.logEvent(Status.PASS, "Validate statements are sorted in chronological descending order by date.",""
					+"Statements are sorted by Date in chronological descending order.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate statements are sorted in chronological descending order by date.",""
					+"Statements are not found to be sorted by Date in chronological descending order.", true);
		Calendar present = Calendar.getInstance();
	    present.setTime(dateList.get(0));
	    Calendar past = Calendar.getInstance();
	    past.setTime(dateList.get(dateList.size()-1));

	    int years = 0;
	    while (past.before(present)) {
	        past.add(Calendar.YEAR, 1);
	        if (past.before(present)) {
	            years++;
	        }
	    } 
	    System.out.println("Number of years:"+years);
	    if(years>=0&&years<=5)
	    	Reporter.logEvent(Status.PASS, "Validate only up to 5 years records are diplayed.", "up to 5 years records are displayed.", false);
	    else
	    	Reporter.logEvent(Status.FAIL, "Validate only up to 5 years records are diplayed.", "up to 5 years records are not displayed.", true);
		
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred.",e.getMessage(), true);
	}
}

/**
 * @author smykjn
 * @Objective This method validates is Enrollment and eligibility section is displayed.
 * @return boolean
 * true is displayed,false if not displayed
 */
public boolean isEnrollmentEligiDisplayed() throws Exception
{
	boolean isdisplayed = false;
	Web.getDriver().switchTo().defaultContent();
	Web.getDriver().switchTo().frame(employeeSearchFrame);
	if(CommonLib.isElementExistByXpath(enrollAndEligSection))
		isdisplayed = true;
	else
		isdisplayed = false;
	Web.getDriver().switchTo().defaultContent();
	return isdisplayed;
}

/**
 * @author smykjn
 * @Objective This method validates if following fields are displayed as Employment information on overview page.<br>
  Fileds are:Union,Insider,Super officer,FT/PT Employee,Job description,Employment type,Employer classification code
 * @return void
 */
public void validateNewEmploymentFileds()
{
	boolean isLabelDisplayed = false;
	List<String> actLabels = new  ArrayList<String>();
	List<String> expLabels = Arrays.asList(Stock.GetParameterValue("ExpectedLabels").split(","));
	List<String> expEditWinLabels = Arrays.asList(Stock.GetParameterValue("ExpModalWindowLabels").split(","));
	try{
		this.navigateToEmpDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		for(WebElement label : listOfEmpmntInfoLabels){
			actLabels.add(label.getText().replace(":","").trim());
		}
		for(String label_ : expLabels){
			if(actLabels.contains(label_)){isLabelDisplayed=true;}
			else{isLabelDisplayed=false;break;}
		}
		if(isLabelDisplayed)
			Reporter.logEvent(Status.PASS, "Validate all the following labels are displayed:"+expLabels,""
					+"All the expected labels are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate all the following labels are displayed:"+expLabels,""
					+"All the expected labels are not displayed.", true);
		System.out.println(actLabels);
		actLabels.clear();
		System.out.println("after clear"+actLabels);
		//Edit section and validate fields not to be displayed
		Web.clickOnElement(empInfoEditLink);
		Web.waitForPageToLoad(Web.getDriver());
		CommonLib.waitForProgressBar();
		Web.waitForElement(empInfoEditFrame);
		Web.getDriver().switchTo().frame(empInfoEditFrame);
		for(WebElement label : editEmplmentInfoModalWindowLabels)
		{
			actLabels.add(label.getText().replace("?","").trim());
		}
		System.out.println("Edit Window labels:"+actLabels);
		isLabelDisplayed=false;
		for(String label_1 : expEditWinLabels){
			System.out.println(label_1);
			if(actLabels.contains(label_1)){isLabelDisplayed=true;}
			else{isLabelDisplayed=false;break;}
		}
		if(isLabelDisplayed)
			Reporter.logEvent(Status.PASS, "Validate all the following labels are displayed on Update "
					+ "employment information modal window:"+expEditWinLabels,""
					+"All the expected labels are not displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate all the following labels are displayed displayed on Update "
					+ "employment information modal window:"+expEditWinLabels,""
					+"All/few of the expected labels are not displayed.", true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(closeEditEmpntInfoWindow);
		CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().defaultContent();
			
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL,"Exception occurred.", e.getMessage(), true);
	}
	
}



/**
 * @author smykjn
 * @Objective this method validate the Tool tip elements(SSN,EMP ID,Participant ID) for recently viewed links on 
 * mouser hovering event
 * @Date 26th-April-2017
 */
public void recentlyViwedMouseHoverElementsValidation()
{
	try{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(recentlyViewedLink);
		Web.waitForElement(recentlyViewedEmpTable);
		WebElement tootlTip = recentlyViewedEmployee.get(0).findElement(By.xpath("./preceding-sibling::div"));
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(recentlyViewedEmployee.get(0)).build().perform();
		Thread.sleep(2000);
		boolean isDisplayed = tootlTip.isDisplayed();
		String toolTipText = tootlTip.getText();
		Web.clickOnElement(recentlyViewedLink);
		System.out.println(toolTipText+" and isToolTipDisplayed:"+isDisplayed);
		if(isDisplayed && toolTipText.contains("SSN")&&toolTipText.contains("Employee ID")&&toolTipText.contains("Participant ID"))
			Reporter.logEvent(Status.PASS, "Mouse hover on employee link present under recenlty viewed."
					+ " SSN,Employee ID,Participant ID must be displayed in tool tip.",""
							+ "Tool tip is displayed:"+isDisplayed+" details inside tool tip:"+toolTipText, false);
		else
			Reporter.logEvent(Status.FAIL, "Mouse hover on employee link present under recenlty viewed."
				+ " SSN,Employee ID,Participant ID must be displayed in tool tip.",""
						+ "Tool tip is displayed:"+isDisplayed+" details inside tool tip:"+toolTipText, true);
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>this method validate Fees section if past 3 months data is displayed.</pre>
 * @author smykjn
 * @Date 27th-April-2017
 * @return boolean 
 * <pre>Returns true is data is displayed inside fees section else returns false</pre>
 */
public boolean validatePastThreeMonthFees()
{
	List<String> expFeeHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedHeaders").split(","));
	boolean isHeaderDisplayed = false;
	boolean isDataDisplayed = false;
	try{
		this.navigateToAccountDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(CommonLib.isElementExistByXpath(fees)){
			Reporter.logEvent(Status.PASS, "Validate Fees section is displayed under account detail.", "Fees"
					+ " section is displayed.", false);
			Web.clickOnElement(Web.getDriver().findElement(By.xpath(fees)));
			if(CommonLib.isElementExistByXpath(feesDataForThreeMonth))
			{
				for(WebElement feeHeader : feesHeader){
					if(expFeeHeaders.contains(feeHeader.getText().trim())){isHeaderDisplayed=true;}
					else{isHeaderDisplayed=false;break;}
				}
				if(isHeaderDisplayed)
					Reporter.logEvent(Status.PASS, "Validate following information is displayed"
							+ " in fees section if past 3 months data exists:"+expFeeHeaders, "Mentioned fees details are displayed.", false);
				else
					Reporter.logEvent(Status.FAIL, "Validate following information is displayed"
							+ " in fees section if past 3 months data exists:"+expFeeHeaders, "Mentioned fees information or"
									+ " few of the information are not displayed.", true);
				isDataDisplayed= true;
			}else{
				Reporter.logEvent(Status.FAIL, "Validate past 3 months data displayed under Fees section.", "there is"
						+ " no fees data found within past three months.", true);
				isDataDisplayed = false;
			}
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Validate Fees section is displayed under account detail.", "Fees"
					+ " section is not displayed.", true);
		}
		return isDataDisplayed;
		
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isDataDisplayed;
	}
}


/**
 * <pre>this method validate Max number of fees records over past three month,<br>that should be 5 fees records over past 3 month.</pre>
 * @author smykjn
 * @Date 27th-April-2017
 * @return void
 */
public void validateMaxNumberOfFeesRecords()
{
	try{
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getPastThreeMonthFeesCount")[0],
				Stock.getTestQuery("getPastThreeMonthFeesCount")[1],Stock.GetParameterValue("SSN"),""+
				"K_"+Stock.GetParameterValue("username"));
		int count = DB.getRecordSetCount(queryResultSet);
		if(count<=5&&count>0){
			if(count==feesRecords.size())
				Reporter.logEvent(Status.PASS, "Validate only up to 5 fees over the past 3 month is displayed.",""
						+"Section shows up to 5 fees over the past 3 months.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate only up to 5 fees over the past 3 month is displayed.",""
						+"Section doesn't show up to 5 fees over the past 3 months.", true);
		}else{
			if(feesRecords.size()==5)
				Reporter.logEvent(Status.PASS, "Validate only up to 5 fees over the past 3 month is displayed.",""
						+"Section shows up to 5 fees over the past 3 months.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate only up to 5 fees over the past 3 month is displayed.",""
						+"Section doesn't show up to 5 fees over the past 3 months.", true);
		}
		
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/**
 * <pre>This method validates fees modal window columns header,Date drop down and submit button on fees modal window.</pre>
 * @author smykjn
 * @Date 27th-April-2017
 * @return boolean<br>
 * <pre>returns true if data is displayed inside fees modal window else false</pre>
 */
public boolean validateFeesModalWindow()
{
	List<String> expFeesModalWinHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedModalWinHeader").split(","));
	boolean isHeaderDisplayed= false;
	try{
		if(feesRecords.size()>=1)
		{
			if(feeMoreButton.isDisplayed()){
				Web.clickOnElement(feeMoreButton);
				Web.waitForElement(feesModalFrame);
				Web.getDriver().switchTo().frame(feesModalFrame);
				Web.waitForElement(submit);
				for(WebElement header : FeesModalWinHeader)
				{
					if(expFeesModalWinHeaders.contains(header.getText().trim()))
						{isHeaderDisplayed=true;}
					else{isHeaderDisplayed=false;break;}
				}
				if(isHeaderDisplayed)
					Reporter.logEvent(Status.PASS, "Validate more button is displayed."
							+ " and if dislayed click on it and validate following column header"+expFeesModalWinHeaders,""
							+"More button is displayed and expected column headers are displayed.", false);
				else
					Reporter.logEvent(Status.FAIL, "Validate more button is displayed."
							+ " and if dislayed click on it and validate following column header"+expFeesModalWinHeaders,""
							+"More button is displayed and expected column headers are not displayed.", true);
				if(Web.isWebElementsDisplayed(to_date, false)&&Web.isWebElementsDisplayed(from_date, false)&&
						submit.isDisplayed())
					Reporter.logEvent(Status.PASS, "Validtae From and to date drop down and submit button is displayed.",""
							+"From and to date drop down and submit button is displayed.", false);
				else
					Reporter.logEvent(Status.FAIL, "Validtae From and to date drop down and submit button is displayed.",""
						+"Either From and to date drop down or submit button is not displayed.", true);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validate more button is displayed.", "More button is not displayed.", true);
				isHeaderDisplayed=false;
			}
			
		}
		return isHeaderDisplayed;
	}catch(Exception e){
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isHeaderDisplayed;
	}
}


/**
 * <pre>This method validates fees records are updated based on selected date range.</pre>
 * @author smykjn
 * @Date 27th-April-2017
 * @return void
 */
public void updateDateRengeAndValidate()
{
	List<String> dateString =  new ArrayList<String>();
	List<Date> dateList = new ArrayList<Date>();
	try{
		LocalDate localDate = LocalDate.now();
		//set month
		Select sel1 = new Select(from_date.get(0));
		sel1.selectByIndex(localDate.getMonthValue()-1);
		//set year
		Select sel2 = new Select(from_date.get(2));
		int year = localDate.getYear() - 1;
		sel2.selectByVisibleText(Integer.toString(year));
		Web.clickOnElement(submit);
		Web.waitForPageToLoad(Web.getDriver());
		for(WebElement date_String :listEffcDate)
		{
			dateString.add(date_String.getText().trim());
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		for(String str : dateString)
		{
			dateList.add(simpleDateFormat.parse(str));
		}
		Collections.sort(dateList);
		Collections.reverse(dateList);
		Calendar present = Calendar.getInstance();
	    present.setTime(dateList.get(0));
	    Calendar past = Calendar.getInstance();
	    past.setTime(dateList.get(dateList.size()-1));

	    int years = 0;
	    while (past.before(present)) {
	        past.add(Calendar.YEAR, 1);
	        if (past.before(present)) {
	            years++;
	        }
	    } 
	    System.out.println("year difference:"+years);
	    if(years<=1||years==0)
	    	Reporter.logEvent(Status.PASS, "Change the date to one year back and see all records are within selected "
	    			+ "date range.", "Records are filtered within selected Date range.", false);
	    else
	    	Reporter.logEvent(Status.FAIL, "Change the date to one year range back and see all records are within selected "
	    			+ "date range.", "Records are not filtered within selected Date range.", false);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(closeFeeModalWindow);
		Thread.sleep(2000);
	}catch(Exception e){
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(closeFeeModalWindow);
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates vesting Title should be link and once clicked,it should open a modal window.</pre>
 * @author smykjn
 * @Date 28th-April-2017
 * @return void
 */
public void validateVestingSection_1()
{
	try{
		this.navigateToAccountDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(vestingBoxWithData.isDisplayed()&&
				vestingBoxWithData.findElement(By.xpath(".//h1/a")).getText().equals("Vesting"))
			Reporter.logEvent(Status.PASS, "Navigate to Account detail page and check Vesting box is displayed"
					+ " with title 'Vesting'.", "Vesting box is displayed with title 'vesting'.", false);
		else
			Reporter.logEvent(Status.FAIL, "Navigate to Account detail page and check Vesting box is displayed"
					+ " with title 'Vesting'.", "Vesting box is not displayed or title 'vesting' is not displayed.", true);
		WebElement vestingLink = vestingBoxWithData.findElement(By.xpath(".//h1/a"));
		String tagName  = vestingLink.getTagName();
		Web.clickOnElement(vestingLink);
		Web.waitForElement(vestingDialog);
		if(tagName.equals("a")&&vestingDialog.isDisplayed())
			Reporter.logEvent(Status.PASS, "Validate Title 'Vesting' is link when data is found for vesting and "
					+ "once the link is clicked,Vesting detail information page(Modal window) is displayed.", "title 'Vesting' "
							+ "is a link and when clicked,Vesting information detail page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate Title 'Vesting' is link when data is found for vesting and "
					+ "once the link is clicked,Vesting detail information page(Modal window) is displayed.", "title 'Vesting' "
							+ "is not a link or when clicked,Vesting information detail page is not displayed.", true);
		Web.clickOnElement(vestingModalCloseLink);
		CommonLib.waitForProgressBar();
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates vesting section fields that is displayed to the user.
 * Ex: Computation period,Vested balance,Vesting by money source</pre>
 * @author smykjn
 * @Date 28th-April-2017
 * @return void
 */
public void validateVestingSection_2()
{
	String computationPeriod = computationPrd.findElement(By.xpath("./following-sibling::td/span")).getText();
	String vestedBalance_ = vestedBalance.findElement(By.xpath("./../following-sibling::td/span")).getText();
	try{
		if(computationPrd.isDisplayed()&&vestedBalance.isDisplayed()&&vestedByMoneySource.isDisplayed())
			Reporter.logEvent(Status.PASS,"Check if following information is displayed under vesting section:"+
					"Computation period,Vested balance,Vesting by money source.","Specified information is displayed:"
							+ "Computation period:"+computationPeriod+","+"Vested balance:"+vestedBalance_,false);
		else
			Reporter.logEvent(Status.FAIL,"Check if following information is displayed under vesting section:"+
					"Computation period,Vested balance,Vesting by money source.","Specified information is not displayed.",true);
		
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}




/**
 * <pre>This method validates vesting modal window section elements.</pre>
 * @author smykjn
 * @Date 28th-April-2017
 * @return void
 */
public void validateVestingModalWindowSection_1()
{
	List<String> expHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedElements").split(","));
	boolean isHeaderDisplayed = false;
	try{
		Web.clickOnElement(vestingBoxWithData.findElement(By.xpath(".//h1/a")));
		Web.waitForElement(clickHereLink);
		for(WebElement header : vestingModalHeaders){
			System.out.println("Actual Header is"+header.getText().replaceAll("\\s+", " ").trim());
			if(expHeaders.contains(header.getText().replaceAll("\\s+", " ").trim()))
				{isHeaderDisplayed = true;}
			else
				{isHeaderDisplayed = false;break;}
		}
		if(isHeaderDisplayed)
			Reporter.logEvent(Status.PASS, "Validate header columns on vesting modal info window  as following:"+expHeaders,""
					+"All column headers are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate header columns on vesting modal info window  as following:"+expHeaders,""
					+"All column headers or few headers are not displayed.", true);
		if(clickHereLink.isDisplayed())
			Reporter.logEvent(Status.PASS,"Check click here link is displayed.", "Click here link is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Check click here link is displayed.", "Click here link is not displayed.", true);
		//Step8 has to be validated
		//method to get number of days between two dates
		if(changeCurrentPeriodVesting.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate 'Change current period vesting' button is displayed.","As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate 'Change current period vesting' button is displayed.","Specified "
					+ "button is not displayed.",true);
		if(changePriorPeriodVesting.isDisplayed())
			Reporter.logEvent(Status.PASS,"Validate 'Change prior period vesting' button is displayed.","As expected.",false);
		else
			Reporter.logEvent(Status.FAIL,"Validate 'Change prior period vesting' button is displayed.","Specified "
					+ "button is not displayed.",true);
		
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}
@FindBy(id="empUpdateVestingInfoPriorFrameId")
private WebElement empUpdateVestingPriorInfoframe;
/**
 * <pre>This method validates 
 * 1.if Click here link is clicked then it should display Account Balance by money sources page.
 * 2.Validates Period begin dates are link.</pre>
 * @author smykjn
 * @Date 28th-April-2017
 * @return void
 */
public void validateVestingModalWindowSection_2()
{
	boolean isLink = false;
	try{
		Web.clickOnElement(clickHereLink);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(accntBalFrame);
		Web.getDriver().switchTo().frame(accntBalFrame);
		Web.waitForElement(accntBalTitle);
		if(accntBalTitle.getText().contains("Account Balance by Money Sources"))
			Reporter.logEvent(Status.PASS,"Click on 'Click here' link and check 'Account Balance by Money Sources' page is displayed.",""
					+ "Account Balance by Money Sources page is displayed." , false);
		else
			Reporter.logEvent(Status.FAIL,"Click on 'Click here' link and check 'Account Balance by Money Sources' page is displayed.",""
					+ "Account Balance by Money Sources page is not displayed." , true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(accntBalCloseLink);
		Web.waitForElement(clickHereLink);	
		if(periodBeginDateLinks.size()>0){
		for(WebElement periodBeginLink : periodBeginDateLinks)
		{
			if(periodBeginLink.getTagName().equals("a")){
				isLink = true;}
			else{isLink=false;break;}
		}
		if(isLink)
			Reporter.logEvent(Status.PASS,"Validate column 'Period begin date' is having dates as Links.","Period begin dates are Links.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate column 'Period begin date' is having dates as Links.","Period begin dates are not found to be Links.", true);
		
		Web.clickOnElement(periodBeginDateLinks.get(0));
		Web.waitForElement(vestingDetailPage);
		if(vestingDetailPage.isDisplayed())
			Reporter.logEvent(Status.PASS,"Click on Period begin date link and validate vesting detail page is displayed.",""+
					"Vesting detail page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Period begin date link and validate vesting detail page is displayed.",""+
					"Vesting detail page is not displayed.", true);
		Web.clickOnElement(vestingDetailClose);
		Web.waitForElement(clickHereLink);
		Web.clickOnElement(changePriorPeriodVesting);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(empUpdateVestingPriorInfoframe,false))
			Reporter.logEvent(Status.PASS,"Click on 'Change prior period vesting' and "
					+ "validate modal window is displayed to change vesting period.","As expected.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on 'Change prior period vesting' and "
					+ "validate modal window is displayed to change vesting period.","Modal window is not displayed.", true);
		//CommonLib.switchToFrame(empUpdateVestingPriorInfoframe);
		CommonLib.switchToFrame(employeeSearchFrame);
		Web.clickOnElement(closeIconVestingWindow);
		Web.clickOnElement(vestingModalCloseLink);
		//CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().defaultContent();
		}else{
			Web.clickOnElement(vestingModalCloseLink);
			CommonLib.waitForProgressBar();
			Web.getDriver().switchTo().defaultContent();
			Reporter.logEvent(Status.WARNING,"Validate column 'Period begin date' is having dates as Links.","Date links are not displayed.Please get participant"
				+ " with required data.", true);}
		
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}



/**
 * <pre>This method searches participant in DB who is having loans and search him on PSC search page.</pre>
 * @author smykjn
 * @Date 2nd-May-2017
 * @return String
 * <pre>Returns SSN of Participant with Loan details</pre>
 */
public String searchPPTWithLoan() throws SQLException
{
		queryResultSet = this.selectEmployeesForUser(Stock.getTestQuery("getPPTWithLoans"),""
				+Stock.GetParameterValue("username"));
		try{
		ssn = this.selectEmployeeFromResultSet(queryResultSet);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return ssn;
	
}

/**
 * <pre>This method validates basic elements of Loan section.
 * Like 1.Money out title and columns 'Active Loans,Max Loans allowed,Min amount,Max amount'</pre>
 * @author smykjn
 * @Date 2nd-May-2017
 * @return void
 */
public void validateLoanSection_1()
{
	List<String> expElements = Arrays.asList(Stock.GetParameterValue("ExpectedElements").split(","));
	try{
		this.navigateToAccountDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(moneyOutSection.findElement(By.tagName("h1")).getText().trim().equalsIgnoreCase("Money out"))
			Reporter.logEvent(Status.PASS,"Validate that title Money out is displayed.", "Money out title is"
					+ " displayed.",false);
		else
			Reporter.logEvent(Status.PASS,"Validate that title Money out is displayed.", "Money out title is"
					+ " displayed.",true);
		if(activeLoansLabel.isDisplayed()&&maxLoanAllowedlabel.isDisplayed()&&minAmntLabel.isDisplayed()&&maxAmntLabel.isDisplayed())
			Reporter.logEvent(Status.PASS, "Validate following columns are displayed under loans section:"+
					""+expElements, "All expected columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate following columns are displayed under loans section:"+
					""+expElements, "All expected columns or few columns are not displayed.", true);
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}



/**
 * <pre>This method fetches the loan data for a participant and stores it in map</pre>
 * @author smykjn
 * @Date 2nd-May-2017
 * @return map
 */
public Map<String,List<String>> getLoanDataFromDB() throws SQLException
{
	Map<String,List<String>> loanData = new LinkedHashMap<String,List<String>>();
	List<String> dbEffDate = new ArrayList<String>();
	List<String> loanAmt = new ArrayList<String>();
	List<String> sts_code = new ArrayList<String>();
	List<String> repay_Amt = new ArrayList<String>();
	List<String> loanNumber = new ArrayList<String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getLoanDetail")[0],""+
			Stock.getTestQuery("getLoanDetail")[1],ssn);
	while(queryResultSet.next())
	{
		dbEffDate.add(queryResultSet.getString("EFFDATE"));
		loanAmt.add(queryResultSet.getString("LOAN_AMT"));
		if(queryResultSet.getString("STATUS_CODE").equals("A")){
			sts_code.add("Active");}
		if(queryResultSet.getString("STATUS_CODE").equals("P"))
		{
			sts_code.add("Paid in Full");
		}
		repay_Amt.add(queryResultSet.getString("REPAY_AMT"));
		loanNumber.add(queryResultSet.getString("INLNAG_SEQNBR"));
	}
	loanData.put("Effective Date", dbEffDate);
	loanData.put("Loan Amount", loanAmt);
	loanData.put("Status", sts_code);
	loanData.put("Repay Amount", repay_Amt);
	loanData.put("Loan Number", loanNumber);
	System.out.println("Loan map from DB is"+loanData);
	return loanData;
}

/**
 * <pre>This method validates if loan data is displayed and compares data from DB.
 * Like Effective date,Loan Amount,Status,Repay Amount,Principal Balance,Details</pre>
 * @author smykjn
 * @Date 3rd-May-2017
 * @return void
 */
public void compareLoanDataWithDB()
{
	List<String> effDate = new ArrayList<String>();
	List<String> loanAmt = new ArrayList<String>();
	List<String> status = new ArrayList<String>();
	List<String> repayAmt = new ArrayList<String>();
	Map<String,List<String>> loanDataFromGUI = new HashMap<String,List<String>>();
	Map<String,List<Date>> loanDataFromGUI_Date = new HashMap<String,List<Date>>();
	Map<String,List<String>> loanDataFromDB;
	try{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			for(int j=0;j<loanRows.size();j++)
			{
				List<WebElement> loanColumn = loanRows.get(j).findElements(By.tagName("td"));
				for(int k=0;k<loansColumnsList.size();k++)
				{
					if(loansColumnsList.get(k).getText().equals("Effective Date"))
						effDate.add(loanColumn.get(k).getText());
					if(loansColumnsList.get(k).getText().equals("Loan Amount"))
						loanAmt.add(loanColumn.get(k).getText().replace("$","").replace(",","").trim());
					if(loansColumnsList.get(k).getText().equals("Status"))
						status.add(loanColumn.get(k).getText());
					if(loansColumnsList.get(k).getText().equals("Repay Amount"))
						repayAmt.add(loanColumn.get(k).getText().replace("$","").replace(",","").trim());
				}
			}
			loanDataFromGUI.put("Effective Date", effDate);
			loanDataFromGUI.put("Loan Amount", loanAmt);
			loanDataFromGUI.put("Status", status);
			loanDataFromGUI.put("Repay Amount", repayAmt);
			System.out.println("Loan data from GUI:"+loanDataFromGUI);
			loanDataFromDB = this.getLoanDataFromDB();
			if(loanDataFromDB.get("Effective Date").containsAll(loanDataFromGUI.get("Effective Date"))
					&&loanDataFromDB.get("Loan Amount").containsAll(loanDataFromGUI.get("Loan Amount"))
					&&loanDataFromDB.get("Status").containsAll(loanDataFromGUI.get("Status"))
					&&loanDataFromDB.get("Repay Amount").containsAll(loanDataFromGUI.get("Repay Amount")))
				Reporter.logEvent(Status.PASS, "Validate Loan data for selected participant from DB in terms of "
							+ "Effective date,Loan amount,Status,Repay Amount.", "Data is validated and found to be proper.", false);
			else
				Reporter.logEvent(Status.FAIL, "Validate Loan data for selected participant from DB in terms of "
						+ "Effective date,Loan amount,Status,Repay Amount.", "Data is validated and it is not matching with GUI."
								+ "Please check manually once.", true);
	}catch(Exception e){
		e.getStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/**
 * <pre>This method validates the descending order of Loan effective date.</pre>
 * @author smykjn
 * @Date 3rd-May-2017
 */
public void validateLoanEffDateOrder()
{
	try{
		boolean isSorted = CommonLib.validateDateSorting(loanEffDateList);
		if(isSorted)
			Reporter.logEvent(Status.PASS, "Validate effective date is sorted in descending order.", "Effective date is sorted in "
					+ "descending order.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate effective date is sorted in descending order.", "Effective date is not sorted in "
					+ "descending order.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/**
 * <pre>This method validates clicking on View button opens loan detail page.</pre>
 * @author smykjn
 * @Date 3rd-May-2017
 */
public void validateLoanDetailPage_1()
{
	try{
		if(loanViewButtons.size()>0)
		{
			Web.clickOnElement(loanViewButtons.get(0));
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(loanDetailFrame);
			Web.waitForElement(loanDetailTitle);
			if(loanDetailTitle.getText().contains("Loans"))
				Reporter.logEvent(Status.PASS, "Click on View button for any Loan record.", "Loan details page is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on View button for any Loan record.", "Loan details page is not displayed.", true);
			Web.getDriver().switchTo().defaultContent();
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/**
 * <pre>This method validates Loan data on details page.</pre>
 * @author smykjn
 * @Date 3rd-May-2017
 */
public void validateLoanDetailPage_2()
{
	List<String> expLoanDetailHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedLoanDetailHeaders").split(","));
	List<String> effDate = new ArrayList<String>();
	List<String> loanAmt = new ArrayList<String>();
	List<String> status = new ArrayList<String>();
	List<String> repayAmt = new ArrayList<String>();
	List<String> loanNumber = new ArrayList<String>();
	Map<String,List<String>> loanDetailsFromDB = new HashMap<String,List<String>>();
	Map<String,List<String>> loanDetailsFromGUI = new HashMap<String,List<String>>();
	try{
		Web.getDriver().switchTo().frame(loanDetailFrame);
		if(CommonLib.isAllHeadersDisplayedWhiteSpace(loanDetailsHeader, expLoanDetailHeaders))
			Reporter.logEvent(Status.PASS, "Validate following headers:"+expLoanDetailHeaders,"Expected headers are displayed.",false);
		else
			Reporter.logEvent(Status.FAIL, "Validate following headers:"+expLoanDetailHeaders,"Expected headers or few headers are not"
					+ " displayed.",true);
		loanDetailsFromDB = this.getLoanDataFromDB();
		for(int j=0;j<loanDataRowsOnDetailsPage.size();j++)
		{
			List<WebElement> loanColumnOnDetailsPage = loanDataRowsOnDetailsPage.get(j).findElements(By.tagName("td"));
			for(int k=0;k<loanDetailsHeader.size();k++)
			{
				if(loanDetailsHeader.get(k).getText().contains("Loan number"))
					loanNumber.add(loanColumnOnDetailsPage.get(k).getText().trim());
				if(loanDetailsHeader.get(k).getText().contains("Effective date"))
					effDate.add(loanColumnOnDetailsPage.get(k).getText().trim());
				if(loanDetailsHeader.get(k).getText().equals("Loan amount"))
					loanAmt.add(loanColumnOnDetailsPage.get(k).getText().replace("$","").replace(",","").trim());
				if(loanDetailsHeader.get(k).getText().equals("Status"))
					status.add(loanColumnOnDetailsPage.get(k).getText().trim());
				if(loanDetailsHeader.get(k).getText().equals("Repay amount"))
					repayAmt.add(loanColumnOnDetailsPage.get(k).getText().replace("$","").replace(",","").trim());
			}
		}
		loanDetailsFromGUI.put("Loan Number", loanNumber);
		loanDetailsFromGUI.put("Effective Date", effDate);
		loanDetailsFromGUI.put("Loan Amount", loanAmt);
		loanDetailsFromGUI.put("Status", status);
		loanDetailsFromGUI.put("Repay Amount", repayAmt);
		System.out.println("Loan data from GUI:"+loanDetailsFromGUI);
		if(loanDetailsFromDB.get("Loan Number").containsAll(loanDetailsFromGUI.get("Loan Number"))
				&&loanDetailsFromDB.get("Status").containsAll(loanDetailsFromGUI.get("Status"))
						&&loanDetailsFromDB.get("Repay Amount").containsAll(loanDetailsFromGUI.get("Repay Amount"))
						&&loanDetailsFromDB.get("Effective Date").containsAll(loanDetailsFromGUI.get("Effective Date"))
						&&loanDetailsFromDB.get("Loan Amount").containsAll(loanDetailsFromGUI.get("Loan Amount")))
			Reporter.logEvent(Status.PASS, "Validate Loan data on details page for selected participant from DB in terms of "
					+ "Effective date,Loan amount,Status,Repay Amount,Loan Number.", "Data is validated and found to be proper.", false);
	else
		Reporter.logEvent(Status.FAIL, "Validate Loan data on details page for selected participant from DB in terms of "
				+ "Effective date,Loan amount,Status,Repay Amount,Laon Number.", "Data is validated and it is not matching with GUI."
						+ "Please check manually once.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/**
 * <pre>This method validates Employee loan account data under Employee Loan Account Information section.</pre>
 * @author smykjn
 * @Date 4th-May-2017
 */
public void validateEmpLoanAccountInformation()
{
	List<String> expColumn = Arrays.asList(Stock.GetParameterValue("ExpLoanAccountDetailColumn").split(","));
	boolean isDisplayed = false;
	Map<String,String> actDetailsDB  = new HashMap<String,String>();
	Map<String,Date> actDetailsDB_Date  = new HashMap<String,Date>();
	Map<String,String> actDetailsGUI  = new HashMap<String,String>();
	Map<String,Date> actDetailsGUI_Date  = new HashMap<String,Date>();
	try{
		String loanNumber = loanDataRowsOnDetailsPage.get(0).findElement(By.tagName("a")).getText();
		Web.clickOnElement(loanDataRowsOnDetailsPage.get(0).findElement(By.tagName("a")));
		do{
			Thread.sleep(3000);
		}while(!empLoanAccntNumber.getText().split(":")[0].trim().contains(loanNumber));
		for(int i=0;i<acctDetailColumn.size();i++){
			List<WebElement> labels_1 = loanAcctInfoRows.findElements(By.xpath(".//td["+(i+1)+"]//span[text()!=''][1]"));
			System.out.println("Size of columns:"+labels_1.size());
			if(CommonLib.isAllHeadersDisplayed(labels_1, expColumn))
				{isDisplayed=true;}
			else
				{isDisplayed = false;break;}
			
		}
		if(isDisplayed)
			Reporter.logEvent(Status.PASS, "Validate all expected labels under Loan account information section is displayed."+expColumn,"" 
					+"All expected labels are displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate all expected labels under Loan account information section is displayed."+expColumn,"" 
					+"All expected labels are not displayed", true);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getEmpAcctDetails")[0],Stock.getTestQuery("getEmpAcctDetails")[1], ssn,loanNumber);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		while(queryResultSet.next())
		{
			actDetailsDB_Date.put("Effective Date",simpleDateFormat.parse(queryResultSet.getString("EFFDATE")));
			actDetailsDB_Date.put("First Due Date",simpleDateFormat.parse(queryResultSet.getString("FIRST_DUE_DATE")));
			actDetailsDB_Date.put("Maturity Date",simpleDateFormat.parse(queryResultSet.getString("MATURITY_DATE")));
			actDetailsDB.put("Interest Rate",queryResultSet.getString("FIXED_INT_RATE"));
			actDetailsDB.put("Loan Term",queryResultSet.getString("LOAN_TERM"));
		}
		System.out.println("Map obtained from DB:"+actDetailsDB);
		
		actDetailsGUI_Date.put("Effective Date",simpleDateFormat.parse(loanEffDate.getText().split(":")[1].trim()));
		actDetailsGUI_Date.put("First Due Date",simpleDateFormat.parse(loanFstDueDate.getText().split(":")[1].trim()));
		actDetailsGUI_Date.put("Maturity Date",simpleDateFormat.parse(loanMatDate.getText().split(":")[1].trim()));
		actDetailsGUI.put("Interest Rate",loanIntRate.getText().split(":")[1].trim());
		actDetailsGUI.put("Loan Term",loanTerm.getText().trim().split(":")[1].trim().split("\\s+")[0].trim());
		System.out.println("Map obtained from GUI:"+actDetailsGUI);
		
		if(actDetailsDB.equals(actDetailsGUI)&&actDetailsDB_Date.equals(actDetailsGUI_Date))
			Reporter.logEvent(Status.PASS, "Validate Employee Loan account information.", "Account information is validated and proper.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate Employee Loan account information.", "Account information is validated and not proper.", true);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates Days Late and payments remaining 
 * field under Employee loan Account information section.</pre>
 * @author smykjn
 * @Date 5th-May-2017
 * @return void
 */
public void validateDaysLateAndPayRemainingField()
{
	List<Date> unPaidDueDates = new ArrayList<Date>();
	String loanNumber;
	try{
		int counter =0;
		do{
			loanNumber= loanDataRowsOnDetailsPage.get(counter).findElement(By.tagName("a")).getText();
			System.out.println("Loan number:"+loanNumber);
			Web.clickOnElement(loanDataRowsOnDetailsPage.get(counter).findElement(By.tagName("a")));
			do{
				Thread.sleep(2000);
			}while(!empLoanAccntNumber.getText().split(":")[0].trim().contains(loanNumber));
			counter++;
		}while(daysLateField.getText().trim().equals("N/A")&&counter<loanDataRowsOnDetailsPage.size());
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUnpaidDueDates")[0],
				Stock.getTestQuery("getUnpaidDueDates")[1],ssn,loanNumber);
		int payRemainingDB = DB.getRecordSetCount(queryResultSet);
		while(queryResultSet.next())
		{
			unPaidDueDates.add(queryResultSet.getDate("DUE_DATE"));
		}
		int daysLate = Integer.parseInt(daysLateField.getText().trim());
		int payRemainingUI = Integer.parseInt(payRemaining.getText().split(":")[1].trim());
		System.out.println("Pay remaining:"+payRemainingUI);
		TimeZone zone = TimeZone.getTimeZone("MST");
		Calendar present = Calendar.getInstance(zone);
	    present.setTime(present.getTime());
	    Calendar past = Calendar.getInstance();
	    past.setTime(unPaidDueDates.get(0));
	    int days = 0;
	    while((past.before(present))||past.equals(present)){
	    	
	        past.add(Calendar.DAY_OF_MONTH, 1);
	        days++;
	     } 
	    System.out.println("Number of Days:"+(days-1));
	    if((days-1)==daysLate)
	    	Reporter.logEvent(Status.PASS, "Validate Days late field as 'Current date-first unpaid/partial due date'","" 
	    			+"Days late field is having value as per calculation.", false);
	    else
	    	Reporter.logEvent(Status.FAIL, "Validate Days late field as 'Current date-first unpaid/partial due date'","" 
	    			+"Days late field is not having value as per calculation.", true);
	    
	    if(payRemainingUI==payRemainingDB)
	    	Reporter.logEvent(Status.PASS, "Validate payments remaining field as number of unpaid/partial payments record.","" 
	    			+"payments remaining field is having value as per calculation.", false);
	    else
	    	Reporter.logEvent(Status.FAIL, "Validate payments remaining field as number of unpaid/partial payments record.","" 
	    			+"payments remaining field is not having value as per calculation.", true);
	    Web.clickOnElement(returnToEmpPageLink);
	    Web.waitForPageToLoad(Web.getDriver());
	    Web.getDriver().switchTo().defaultContent();
	    Web.getDriver().switchTo().frame(employeeSearchFrame);
	    Web.waitForElement(txtOverview);
	    if(txtOverview.isDisplayed())
	    	Reporter.logEvent(Status.PASS, "Click on Retrun to overview link.","" 
	    			+"Employee overview page is displayed.", false);
	    else
	    	Reporter.logEvent(Status.FAIL, "Click on Retrun to overview link.","" 
	    			+"Employee overview page is not displayed.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates Paycheck  contribution title as Link.</pre>
 * @author smykjn
 * @Date 9th-May-2017
 */
public void validatePaycheckContTitle()
{
	try{

		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(Web.isWebElementDisplayed(paycheckTitleLink,false)&&paycheckTitleLink.getTagName().equals("a"))
			Reporter.logEvent(Status.PASS, "Validate if Paycheck Contribution title is displayed and "
					+ "it is a link.", "Title is displayed as Link if Paycheck contribution data is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate if Paycheck Contribution title is displayed and "
					+ "it is a link.", "Title is not displayed as Link if Paycheck contribution data is displayed.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates Tabs for Paycheck contribution section.</pre>
 * @author smykjn
 * @Date 9th-May-2017
 * @return void
 */
public void validatePaycheckContTabs()
{
	try{
		if(Web.isWebElementDisplayed(ytdTab,false)&&Web.isWebElementDisplayed(currentRate,false)
				&&Web.isWebElementDisplayed(pendingRate,false))
			Reporter.logEvent(Status.PASS, "Validate following tab is displayed:YTD,Current rate and Pending rate", ""
					+ "Expected tabs are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate following tab is displayed:YTD,Current rate and Pending rate", ""
					+ "Expected tabs are not displayed.", true);

	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method Fetches YTD data from DB(Paycheck contribution)</pre>
 * @author smykjn
 * @Date 9th-May-2017
 * @return Map<String,String>
 * <pre>first key is Amount and 2nd key is Type of contribution</pre>
 */
public Map<String,String> fetchYTDDataFromDB() throws SQLException
{
	Map<String,String> ytdDataDB = new HashMap<String,String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getYTDData")[0],""
			+Stock.getTestQuery("getYTDData")[1],Stock.GetParameterValue("planId"),Stock.GetParameterValue("SSN"));
	while(queryResultSet.next())
	{
		ytdDataDB.put("Amount",queryResultSet.getString("AMOUNT").trim());
		ytdDataDB.put("Type",queryResultSet.getString("DESCR").trim());
		break;
	}
	return ytdDataDB;
}


/**
 * <pre>This method validates data for YTD Tab with DB for Paycheck contribution section.</pre>
 * @author smykjn
 * @Date 9th-May-2017
 * @return void
 */
public void validateYTDTabDataWithDB()
{
	List<String> expHeader = Arrays.asList(Stock.GetParameterValue("ExpectedYTDHeader").split(","));
	Map<String,String> ytdDataDB;
	Map<String,String> ytdDataUI = new HashMap<String,String>(); 
	List<String> ytdType = new ArrayList<String>();
	List<String> ytdContributions = new ArrayList<String>();
	try{
		if(CommonLib.isAllHeadersDisplayed(ytdColumnHeader, expHeader))
			Reporter.logEvent(Status.PASS, "Validate following YTD columns."+expHeader,"Expected columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate following YTD columns."+expHeader,"Expected column are not displayed.", true);
		if(ytdTab.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
			Reporter.logEvent(Status.PASS, "Validate following YTD tab is default active.","YTD tab is default active.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate following YTD tab is default active.","YTD tab is not found to be default active.", false);
		ytdDataDB = this.fetchYTDDataFromDB();
		System.out.println("YTD map DB"+ytdDataDB);
		if(ytdRecordsUI.size()>0)
		{
			for(WebElement type:ytdTypeUI){
				ytdType.add(type.getText().trim());
			}
			System.out.println("Type:"+ytdType);
			for(WebElement contribution:ytdContributionsUI){
				ytdContributions.add(contribution.getText().replace("$","").replace(",","").trim());
			}
			System.out.println("YTD Contributions:"+ytdContributions);
			if(ytdTypeUI.size()==ytdContributionsUI.size())
			{
				for(int i=0;i<ytdTypeUI.size();i++)
				{
					ytdDataUI.put(ytdTypeUI.get(i).getText(), ytdContributionsUI.get(i).getText().replace("$","").trim());
				}
			}
			System.out.println("YTD map UI"+ytdDataUI);
		}
		if(ytdType.contains(ytdDataDB.get("Type"))&&ytdContributions.contains(ytdDataDB.get("Amount")))
			Reporter.logEvent(Status.PASS, "Validate ytd data with DB."+ytdDataDB.get("Type")+" = "+ytdDataDB.get("Amount"),"Data is found to be proper.", false);
		else
			Reporter.logEvent(Status.FAIL, "Validate ytd data with DB.","Data is not found to be proper.", true);
			
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates transaction history tab and sum the Contributions to match YTD Contribution.</pre>
 * @author smykjn
 * @Date 9th-May-2017
 * @return void
 */
public void validateTransactionHistory()
{
	List<Float> amount = new ArrayList<Float>();
	float sum=0;
	float totalYtd;
	try{
		if(Web.isWebElementDisplayed(clickHereLinkInTxnHist, false))
			Web.clickOnElement(clickHereLinkInTxnHist);
		else
			Web.clickOnElement(txnHistoryMoreBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(txnHistFrame);
			Web.getDriver().switchTo().frame(txnHistFrame);
			Web.waitForElements(from_date);
			TimeZone zone = TimeZone.getTimeZone("MST");
			Calendar calendar = Calendar.getInstance(zone);
			calendar.setTime(calendar.getTime());
			//set month
			Select sel1 = new Select(from_date.get(0));
			sel1.selectByIndex(Calendar.JANUARY);
			//set Date
			Select sel2 = new Select(from_date.get(1));
			sel2.selectByIndex(0);
			//set year
			Select sel3 = new Select(from_date.get(2));
			sel3.selectByVisibleText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			Web.clickOnElement(submit);
			Web.waitForPageToLoad(Web.getDriver());
			Web.clickOnElement(contriRadBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			List<WebElement> amntDisplayed =new  ArrayList<WebElement>();
			for(WebElement ele : listContriAmount)
			{
				if(ele.isDisplayed())
					{amntDisplayed.add(ele);}
				else{}
			}
			System.out.println("Total amount list size is:"+listContriAmount.size()+" "
					+ "and total amounts displayed is:"+amntDisplayed.size());
			for(WebElement amt : amntDisplayed)
			{
				try{
				amount.add(Float.parseFloat(amt.getText().replace(",","").trim()));
				}catch(NumberFormatException e)
				{
					e.printStackTrace();
				}
			}
			System.out.println("Contributions:"+amount);
			for(int i=0;i<amount.size();i++)
			{
				sum=sum+amount.get(i);
			}
			System.out.println("Sum of Contributions:"+sum);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(employeeSearchFrame);
			Web.clickOnElement(txnHistClose);
			CommonLib.waitForProgressBar();
			totalYtd = Float.parseFloat(totalYtdCon.getText().replace("$","").replace(",","").trim());
			System.out.println("Sum of Contributions on YTD contribution column:"+totalYtd);
			if(totalYtd==sum)
				Reporter.logEvent(Status.PASS, "Click on more button and filter with contribution for YTD range and "
						+ " observe that all contribution amount sum up to what is displayed on YTD Contribution.", ""+
						"All contribution amount sum up to what is displayed as YTD Contribution.", false);
			else
				Reporter.logEvent(Status.INFO, "Click on more button and filter with contribution for YTD range and "
						+ " observe that all contribution amount sum up to what is displayed on YTD Contribution.", ""+
						"All contribution amount do not sum up to what is displayed as YTD Contribution due to some change."
						+ "Please refer SWEB-13170.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates paycheck window that is displayed once user clicks on more button.</pre>
 * @author smykjn
 * @Date 10th-May-2017
 * @return void
 */
public void validateMoreButtonPage()
{
	List<String> expClmHeaders = Arrays.asList(Stock.GetParameterValue("ExpectedHeaders").split(","));
	boolean isdisplayed=false;
	try{
		Web.clickOnElement(payChkMoreBtn);
		Web.waitForPageToLoad(Web.getDriver());
		Web.getDriver().switchTo().frame(txnHistFrame);
		Web.waitForElements(from_date);
		if(CommonLib.isAllHeadersDisplayed(txnHistHeaders, expClmHeaders))
			Reporter.logEvent(Status.PASS,"Validate following column is displayed:"+expClmHeaders, "Specified columns are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Validate following column is displayed:"+expClmHeaders, "Specified columns are not displayed.", true);
		System.out.println("if Contribution radio btn is selected:"+contriRadBtn.isSelected());
		if(contriRadBtn.isSelected())
			Reporter.logEvent(Status.PASS,"Validate that Contribution Transactions radio button is selected by default.",""
					+ "Contribution Transactions radio button is selected by default." ,false);
		else
			Reporter.logEvent(Status.FAIL,"Validate that Contribution Transactions radio button is selected by default.",""
					+ "Contribution Transactions radio button is not selected by default." ,true);
		
		System.out.println("Displayed contribution list size is:"+txnTypeList.size());
		for(WebElement txnType:txnTypeList)
		{
			if(txnType.isDisplayed())
				{isdisplayed=true;}
			else
				{isdisplayed=false;break;}
		}
		if(isdisplayed)
			Reporter.logEvent(Status.PASS,"Validate only 'Contribution' type records are displayed.",""
					+ "Contribution records are displayed by default." ,false);
		else
			Reporter.logEvent(Status.FAIL,"Validate only 'Contribution' type records are displayed.",""
				+ "Contribution records are displayed by default." ,true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.clickOnElement(txnHistClose);
		CommonLib.waitForProgressBar();
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates message when no data present for YTD.</pre>
 * @author smykjn
 * @Date 10th-May-2017
 * @return void
 */
public void validateNoDataScenarioPayChkContribution()
{
	String expMsg = Stock.GetParameterValue("ExpectedMessage");
	try{
		Web.getDriver().switchTo().defaultContent();
		queryResultSet = this.selectEmployeesForUser(Stock.getTestQuery("noYTDContData"),Stock.GetParameterValue("username"));
		this.selectEmployeeFromResultSet(queryResultSet);
		this.navigateToEmployeeOverViewPage();
		this.navigateToAccountDetailPage();
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		String actMsg= noDataPaychkElement.getText().trim();
		if(Web.VerifyText(expMsg, actMsg, false))
			Reporter.logEvent(Status.PASS,"If no data is present for contribution then following message "
						+ "will be displayed"+expMsg,"Actual message displayed is:"+actMsg ,false);
		else
			Reporter.logEvent(Status.FAIL,"If no data is present for contribution then following message "
					+ "will be displayed"+expMsg,"Actual message displayed is:"+actMsg ,true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}



/**
 * <pre>This method validates page navigation once user close the Paycheck contribution modal window.</pre>
 * @author smykjn
 * @Date 11th-May-2017
 * @return void
 */
public void validateNavigationWhenClosingHisWindow()
{
	try{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		if(payChkHistoryLink.isDisplayed())
		{
			Web.clickOnElement(payChkHistoryLink);
			CommonLib.waitForProgressBar();
			Web.waitForElement(histModalWindowTitle);
			if(histModalWindowTitle.isDisplayed())
				Reporter.logEvent(Status.PASS, "Click on paycheck contribution history link.",""
						+"Paycheck contribution history window is displayed.", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on paycheck contribution history link.",""
						+"Paycheck contribution history window is displayed.", true);
			Web.clickOnElement(histModalWindowTitle.findElement(By.xpath(".//following-sibling::a")));
			Web.waitForElement(txtOverview);
			if(txtOverview.isDisplayed())
				Reporter.logEvent(Status.PASS, "Click on close link on top right corner of the history window.",""
						+"Modal window is closed and user is redirected back to PPT overview page.", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on close link on top right corner of the history window.",""
						+"Modal window is closed and user is not redirected back to PPT overview page.", true);
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates the default emp search option 'Name'.</pre>
 * @author smykjn
 * @Date 11th-May-2017
 * @return void
 */
public void validateDefaultEmpSearchOption()
{
	
	try{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Select drpdwn = new Select(drpdwnSearchEmployee);
		List<WebElement> defaultOption = drpdwn.getAllSelectedOptions();
		for(WebElement option : defaultOption)
		{
			if(option.isSelected()&&option.getText().equals("Name"))
				Reporter.logEvent(Status.PASS,"Validate default search option for Employee search drop down.It should be 'Name'.",""
						+"Default search option is:"+option.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,"Validate default search option for Employee search drop down.It should be 'Name'.",""
						+"Default search option is:"+option.getText(), true);
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}



/**
 * <pre>This method validates that employee search drop down remember Search employee selection option.</pre>
 * @author smykjn
 * @Date 11th-May-2017
 * @return void
 */
public void validateRememberOfEmpSearchOption()
{
	try{
		Select setOption = new Select(drpdwnSearchEmployee);
		setOption.selectByVisibleText("Employee ID");
		Web.waitForElement(drpdwnSearchEmployee);
		this.logoutFromApplication();
		this.get();
		this.navigateToEmployeeTab();
		this.switchToFrame();
		if(setOption.getAllSelectedOptions().get(0).getText().trim().equals("Employee ID"))
			Reporter.logEvent(Status.PASS, "Set search drop down to 'Employee ID' and Logout from PSC Next-Gen and navigate to Employee search page."
					+ "and validate drop down is set to 'Employee ID'.", "Employee ID is found to be selected for employee search drop down.", false);
		else
			Reporter.logEvent(Status.FAIL, "Set search drop down to 'Employee ID' and Logout from PSC Next-Gen and navigate to Employee search page."
					+ "and validate drop down is set to 'Employee ID'.", "Employee ID is not found to be selected for employee search drop down.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates that employee search drop down remember Search employee selection option
 * even when user switches to different plan.</pre>
 * @author smykjn
 * @Date 11th-May-2017
 * @return void
 */
public void validateRememberOfEmpSearchOptionWhenPlanSwitch()
{
	try{
		this.switchToDefaultContent();
		this.selectPlanFromResultset(this.selectPlanForUser(Stock.getTestQuery("getPlanswithDivisions"),""
				+Stock.GetParameterValue("username")));
		this.switchToFrame();
		Select setOption = new Select(drpdwnSearchEmployee);
		if(setOption.getAllSelectedOptions().get(0).getText().trim().equals("Employee ID"))
			Reporter.logEvent(Status.PASS, "Switch to different plan(with divisions) and validate employee search drop down is set to 'Employee ID'.",
					"'Employee ID' is found to be selected for employee search drop down.", false);
		else
			Reporter.logEvent(Status.FAIL, "Switch to different plan and validate employee search drop down is set to 'Employee ID'.",
					"'Employee ID' is not found to be selected for employee search drop down.", true);
		setOption.selectByVisibleText("Division");
		if(Web.isWebElementDisplayed(tableDivresults,true))
			Reporter.logEvent(Status.PASS,"Select Division option and observe that Divisions are displayed.","Divisions are "
					+ "displayed just below the search drop down.", false);
		else
			Reporter.logEvent(Status.FAIL,"Select Division option and observe that Divisions are displayed.","Divisions are not "
					+ "displayed just below the search drop down.", true);
		this.switchToDefaultContent();
		this.selectPlanFromResultset(this.selectPlanForUser(Stock.getTestQuery("getPlanswithNoDivisions"),""
				+Stock.GetParameterValue("username")));
		this.switchToFrame();
		if(setOption.getAllSelectedOptions().get(0).getText().trim().equals("Name"))
			Reporter.logEvent(Status.PASS, "Switch to different plan(with no division) and validate employee search drop down is"
					+ " set to 'Name'.","'Name' is found to be selected for employee search drop down.", false);
		else
			Reporter.logEvent(Status.FAIL, "Switch to different plan(with no division) and validate employee search drop down is "
					+ "set to 'Name'.","'Name' is not found to be selected for employee search drop down.", true);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method fetches data from enrollment and eligibility table in DB.</pre>
 * @author smykjn
 * @Date 12th-May-2017
 * @return void
 */
public Map<String,String> getEnrollAndEligDataFromDB(String ssn) throws SQLException
{
	Map<String,String> enrollAndEligDataDB = new HashMap<String,String>();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("getPPtWithEnrollAndEligDetails_1")[0],
			Stock.getTestQuery("getPPtWithEnrollAndEligDetails_1")[1],ssn);
	while(queryResultSet.next()){
		enrollAndEligDataDB.put("Eligibility Code",queryResultSet.getString("ELIGIBILITY_IND").trim());
		enrollAndEligDataDB.put("Participation Date",queryResultSet.getString("PARTICIPATION_DATE").trim());
		enrollAndEligDataDB.put("Enroll Invite Date",queryResultSet.getString("ENROLL_INVITE_DATE_TIME").trim());
		enrollAndEligDataDB.put("Enroll Invite Reply",queryResultSet.getString("ENROLL_INVITE_REPLY_DATE_TIME").trim());
		enrollAndEligDataDB.put("Enroll Pin Date",queryResultSet.getString("ENROLL_PIN_DATE_TIME").trim());
		enrollAndEligDataDB.put("Ineligibility Code",queryResultSet.getString("INELIGIBILITY_REASON_CODE").trim());
		enrollAndEligDataDB.put("Enrollment Notification Date",queryResultSet.getString("ENROLL_NOTIFICATION_DATE_TIME").trim());
		enrollAndEligDataDB.put("Suppress Auto Enrollment Indicator",queryResultSet.getString("SUPPRESS_AUTO_ENROLL_IND").trim());
		break;
	}
	System.out.println("Enrollment and eligibility data fetched from DB:"+enrollAndEligDataDB);
	return enrollAndEligDataDB;
}


/**
 * <pre>This method captures fields values of enrollment and eligibility section from UI.</pre>
 * @author smykjn
 * @Date 15th-May-2017
 * @return void
 */
public Map<String,String> getEnrollAndEligDataFromUI()
{
	Map<String,String> enrollAndEligDataUI = new HashMap<String,String>();
	try{
		this.switchToFrame();
		if(CommonLib.isElementExistByXpath(enrollAndEligSection))
		{
			if(eligCodeUI.getText().trim().equals("No")){
			enrollAndEligDataUI.put("Eligibility Code","N");
			}else{enrollAndEligDataUI.put("Eligibility Code","Y");};
			enrollAndEligDataUI.put("Participation Date",partDateUI.getText().trim());
			enrollAndEligDataUI.put("Enroll Invite Date",enrInviteDateUI.getText().trim());
			enrollAndEligDataUI.put("Enroll Invite Reply",enrInviteReplyUI.getText().trim());
			enrollAndEligDataUI.put("Enroll Pin Date",enrPinDateUI.getText().trim());
			enrollAndEligDataUI.put("Ineligibility Code",ineligCodeUI.getText().trim());
			enrollAndEligDataUI.put("Enrollment Notification Date",enroNotificationDate.getText().trim());
			enrollAndEligDataUI.put("Suppress Auto Enrollment Indicator",suppressAutoIndicator.getText().trim());
		}
		System.out.println("Enrollment and eligibility data fetched from UI:"+enrollAndEligDataUI);
		return enrollAndEligDataUI;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return enrollAndEligDataUI;
	}
}




/**
 * <pre>This method takes user to the Add Employee page.</pre>
 * @author smykjn
 * @Date 15th-May-2017
 * @return void
 */
public void navigateToAddEmpPage()
{
	try{
		HomePage homePage = new HomePage();
		homePage.navigateToProvidedPage("Employees","Add employee","");
		this.switchToFrame();
		Web.waitForElement(addEmpTitle);
		if(addEmpTitle.isDisplayed())
			Reporter.logEvent(Status.PASS,"Navigate to Employees-->Add employee page.","Add employee page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Navigate to Employees-->Add employee page.","Add employee page is not displayed.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		
	}
}


/**
 * @author smykjn
 * @Date 15th-May-2017
 */
public String generateUniqueNineDigitNumber()
{
	long timeSeed = System.nanoTime();
	double randSeed = Math.random() * 1000;
    long midSeed = (long) (timeSeed * randSeed); 
    String s = midSeed + "";
    String subStr = s.substring(0, 9);
    System.out.println(subStr);
    return subStr;
}


/**
 * <pre>This method does SSN field validation(Positive and negative field testing) for add employee.</pre>
 * @author smykjn
 * @Date 15th-May-2017
 * @return boolean
 */
public boolean fillSSNForAddNewEmp()
{
	boolean isPageDisplayed = false;
	try{
		Web.setTextToTextBox(enterSSN,"32-34-34-434");
		Thread.sleep(2000);
		Web.setTextToTextBox(reEnterSSN, "32-34-34-434");
		Thread.sleep(2000);
		Web.setTextToTextBox(pptDob,"05/05/1991");
		Web.setTextToTextBox(lastName, Stock.GetParameterValue("LastName"));
		Thread.sleep(2000);
		if(ssnError.getText().trim().contains("SSN is not a valid SSN")&&ssnReError.getText().trim().contains("SSN is not a valid SSN"))
			Reporter.logEvent(Status.PASS,"Enter invalid SSN(xx-xx-xx-xxx) and observe the error message.",""
					+ "Actual Error message displayed is:"+ssnError.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Enter invalid SSN(xx-xx-xx-xxx) and observe the error message.",""
					+ "Error message displayed is not displayed.", true);
		Web.setTextToTextBox(enterSSN,"smith");
		Thread.sleep(2000);
		Web.setTextToTextBox(reEnterSSN, "smith");
		Thread.sleep(2000);
		Web.setTextToTextBox(pptDob,"05/05/1991");
		Web.setTextToTextBox(lastName, Stock.GetParameterValue("LastName"));
		if(ssnError.getText().trim().contains("SSN is not a valid SSN")
				&&ssnReError.getText().trim().contains("SSN is not a valid SSN"))
			Reporter.logEvent(Status.PASS,"Enter invalid SSN(like smith) and observe the error message.",""
					+ "Actual Error message displayed is:"+ssnError.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,"Enter invalid SSN(like smith) and observe the error message.",""
					+ "Error message displayed is not displayed.", true);
		String ssn = generateUniqueNineDigitNumber();
		Web.setTextToTextBox(enterSSN,ssn);
		Thread.sleep(2000);
		Web.setTextToTextBox(reEnterSSN,ssn);
		Thread.sleep(2000);
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(empBasicInfoTitle, true))
			isPageDisplayed= true;
		else
			isPageDisplayed = false;
		return isPageDisplayed;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isPageDisplayed;
	}
}



/**
 * <pre>This method fills invalid data for the new employee basic info.</pre>
 * @author smykjn
 * @Date 15th-May-2017
 * @return void
 */
public void fillNewEmpBasicInfoInvalid()
{
	boolean isPageDisplayed = false;
	try{
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(empBasicInfoTitle);
		for(WebElement errorMsg : mandatoryFieldValidationMsgs)
		{
			if(errorMsg.getText().trim().equalsIgnoreCase("Some fields contain errors. Please correct errors and continue.")
					||errorMsg.getText().trim().equalsIgnoreCase("First Name is a required field.")
					||errorMsg.getText().trim().equalsIgnoreCase("Address Line 1 is a required field.")
					||errorMsg.getText().trim().equalsIgnoreCase("City is a required field.")
					||errorMsg.getText().trim().equalsIgnoreCase("State is a required field.")
					||errorMsg.getText().trim().equalsIgnoreCase("Zip is a required field.")
					||errorMsg.getText().trim().equalsIgnoreCase("Radio button below was not selected. Please select."))
				{isPageDisplayed=true;}
			else
				{isPageDisplayed=false;
				break;}
		}
		if(isPageDisplayed)
			Reporter.logEvent(Status.PASS,"Click on continue button without entering mandatory fields.", "Error messages are displayed"
					+ " for respective mandatory fields which were left blank.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on continue button without entering mandatory fields.", "Error messages are not displayed"
					+ " for respective mandatory fields which were left blank.", true);
		isPageDisplayed =false;
		
		Web.setTextToTextBox(first_Name,Stock.GetParameterValue("FirstName")+"12#()@;");
		Select marital_Status = new Select(maritalSts);
		marital_Status.selectByVisibleText("SINGLE");
		Select gender_ = new Select(gender);
		gender_.selectByVisibleText("MALE");
		Web.setTextToTextBox(address,"f-E !$#<>");
		Web.setTextToTextBox(city, "Chicago");
		Select state = new Select(state_);
		state.selectByVisibleText("ILLINOIS");
		Web.setTextToTextBox(zip,"<>3#");
		Select country_ = new Select(country.findElement(By.xpath("./..")));
		country_.selectByVisibleText("UNITED STATES");
		if(CommonLib.isElementExistByXpath(".//*[@id='IPB_CONTACT_4']")){
		Web.clickOnElement(outsideAssets);}
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		try{
			if(fNValidation.isDisplayed()){
				Reporter.logEvent(Status.PASS,"Enter invalid first name and observe the error message."+fNValidation.getText(),""
						+ "First name field validation message is displayed as expected.", false);
			}
		}catch(Exception e){
			e.getStackTrace();
			Reporter.logEvent(Status.FAIL,"Enter invalid first name and observe the error message."+fNValidation.getText(),""
					+ "First name field validation message is not displayed.", true);
			}
		
		try{
			if(addressValidation.isDisplayed()){
				Reporter.logEvent(Status.PASS,"Enter invalid address and observe the error message."+addressValidation.getText(),""
						+ "Address field validation message is displayed as expected.", false);
			}
		}catch(Exception e){
				e.getStackTrace();
				Reporter.logEvent(Status.FAIL,"Enter invalid address and observe the error message."+addressValidation.getText(),""
						+ "address field validation message is not displayed.", true);	
		}
		
		try{
			if(zipCodeValidation.isDisplayed()){
				Reporter.logEvent(Status.PASS,"Enter invalid zip code and observe the error message."+zipCodeValidation.getText(),""
						+ "zip code field validation message is displayed as expected.", false);
			}
		}catch(Exception e){
			e.getStackTrace();
				Reporter.logEvent(Status.FAIL,"Enter invalid zip code and observe the error message."+zipCodeValidation.getText(),""
						+ "zip code field validation message is not displayed.", true);	
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}



/**
 * <pre>This method fills valid data for the new employee basic info.</pre>
 * @author smykjn
 * @Date 15th-May-2017
 * @return boolean
 */
public boolean fillNewEmpBasicInfoValid()
{
	boolean isPageDisplayed = false;
	try{
		Web.setTextToTextBox(first_Name,Stock.GetParameterValue("FirstName"));
		Select maritlSts = new Select(maritalSts);
		maritlSts.selectByVisibleText("SINGLE");
		Select gndr = new Select(gender);
		gndr.selectByVisibleText("MALE");
		Web.setTextToTextBox(address, "F-E");
		Web.setTextToTextBox(city, "Chicago");
		Select state_ = new Select(state.findElement(By.xpath("./..")));
		state_.selectByVisibleText("ILLINOIS");
		Web.setTextToTextBox(zip,"60007");
		Select country_ = new Select(country.findElement(By.xpath("./..")));
		country_.selectByVisibleText("UNITED STATES");
		if(CommonLib.isElementExistByXpath(".//*[@id='IPB_CONTACT_4']")){
		Web.clickOnElement(outsideAssets);}
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(eligibilityTitle);
		if(eligibilityTitle.isDisplayed()){
			Reporter.logEvent(Status.PASS, "Fill valid data and click on continue button.", "Eligibility information page is diplayed.",false);
			isPageDisplayed=true;
		}
		else{
			Reporter.logEvent(Status.FAIL, "Fill valid data and click on continue button.", "Eligibility "
					+ "information page is not diplayed.",true);
			isPageDisplayed=false;
		}
		return isPageDisplayed;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isPageDisplayed = false;
	}
}

/**
 * <pre>This method validates error messages when mandatory fields are not filled 
 * on Eligibility Information page while adding new employee.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return void
 */
public void fillEligibilityInfoNegativeFlow()
{
	List<String> expErrorMsg = Arrays.asList(Stock.GetParameterValue("EligibilityErrorMsg").split(","));
	try{
		Web.clickOnElement(continueAddEmp);
		if(CommonLib.isAllHeadersDisplayed(mandatoryFieldValidationMsgs, expErrorMsg))
			Reporter.logEvent(Status.PASS,"Click on Continue without filling any fields.Error message"
					+ " should be displayed as:"+expErrorMsg,"Expected error message is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Click on Continue without filling any fields.Error message"
					+ " should be displayed as:"+expErrorMsg,"Expected error message is not displayed.", true);
		
		Select elgCode = new Select(eligiCode);
		elgCode.selectByVisibleText("Yes");
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(mandatoryFieldValidationMsgs.get(2).getText().trim().equalsIgnoreCase("Participation Date is required."))
			Reporter.logEvent(Status.PASS,"Select eligibility code as Yes and click on continue.","Error message "
					+ "'Participation Date is required.' is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Select eligibility code as Yes and click on continue.","Error message "
					+ "'Participation Date is required.' is not displayed.", true);
		elgCode.selectByVisibleText("No");
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(mandatoryFieldValidationMsgs.get(2).getText().trim().equalsIgnoreCase("Participation Date is required.")
				&&mandatoryFieldValidationMsgs.get(3).getText().trim().equalsIgnoreCase("Ineligibility Reason code is"
						+ " required because Eligibility code is No."))
			Reporter.logEvent(Status.PASS,"Select eligibility code as No and click on continue.","Error messages "
					+ "'Participation Date is required.' and 'Ineligibility Reason code is required because Eligibility"
					+ " code is No.' are displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Select eligibility code as No and click on continue.","Error messages "
					+ "'Participation Date is required.' and 'Ineligibility Reason code is required because Eligibility"
					+ " code is No.' are not displayed.", true);
		elgCode.selectByVisibleText("No");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Web.setTextToTextBox(partDate,sdf.format(new Date()));
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(mandatoryFieldValidationMsgs.get(1).getText().trim().equalsIgnoreCase("Ineligibility Reason code is"
						+ " required because Eligibility code is No."))
			Reporter.logEvent(Status.PASS,"Select eligibility code as No,Enter participation date and click on continue.","Error message "
					+ "'Ineligibility Reason code is required because Eligibility code is No.' is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Select eligibility code as No and click on continue.","Error messages "
					+ "'Participation Date is required.' and 'Ineligibility Reason code is required because Eligibility"
					+ " code is No.' are not displayed.", true);
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/**
 * <pre>This method validates error messages when mandatory fields are not filled 
 * on Eligibility Information page while adding new employee.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return boolean
 */
public boolean fillEligibilityInfoPositiveFlow()
{
	boolean isPageDisplayed = false;
	List<String> inelgResnCode = Arrays.asList(Stock.GetParameterValue("IneligibilityReasonCode").split(","));
	try{
		
		Select resnCode = new Select(inEligiReasonCode);
		if(CommonLib.isAllHeadersDisplayed(resnCode.getOptions(), inelgResnCode))
			Reporter.logEvent(Status.PASS,"Validate Ineligibility reason code drop down values."
					+ "Values should be as follows:"+inelgResnCode,"Reason codes displayed are:"+resnCode.getOptions(), false);
		else
			Reporter.logEvent(Status.FAIL,"Validate Ineligibility reason code drop down values."
					+ "Values should be as follows:"+inelgResnCode,"Reason codes displayed are:"+resnCode.getOptions(), true);
		Select elgCode = new Select(eligiCode);
		elgCode.selectByVisibleText("Yes");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Web.setTextToTextBox(partDate,sdf.format(new Date()));
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(incomeDataTitle, true)){
			Reporter.logEvent(Status.PASS,"Select eligibility code as No,enter participation date and click on continue.","Enter Income Data "
					+ "page is displayed.", false);
			isPageDisplayed=true;
		}
		else{
			Reporter.logEvent(Status.FAIL,"Select eligibility code as No,enter participation date and click on continue.","Enter Income Data "
					+ "page is not displayed.", true);
			isPageDisplayed=false;	
		}
		return isPageDisplayed;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isPageDisplayed=false;
	}
}

/**
 * <pre>This method fills income data while adding new employee.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return boolean
 */
public boolean enterIncomeData()
{
	boolean isPageDisplayed = false;
	String expIncomeErrorMsg = Stock.GetParameterValue("IncomeDataErrorMsg");
	String actIncomeErrorMsg = "";
	List<String> expFreqOptions = Arrays.asList(Stock.GetParameterValue("FrequencyOptions").split(","));
	try{
		Web.setTextToTextBox(salaryInput,"TestSalary");
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		for(WebElement errorMsg : mandatoryFieldValidationMsgs)
		{
			actIncomeErrorMsg = errorMsg.getText().trim();
			if(actIncomeErrorMsg.equals(expIncomeErrorMsg))
			{
				isPageDisplayed=true;
				break;
			}
		}
		if(isPageDisplayed)
			Reporter.logEvent(Status.PASS,"Enter invalid salary "
					+ "'TestSalary' and click on continue."
					+ "error message '"+expIncomeErrorMsg+"' should be displayed.","Error message displayed is:"+actIncomeErrorMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Enter invalid salary 'TestSalary' and click on continue."
					+ "error message '"+expIncomeErrorMsg+"' should be displayed.","Error message displayed is:"+actIncomeErrorMsg, true);
		isPageDisplayed=false;
		Select freq = new Select(frequency);
		if(CommonLib.isAllHeadersDisplayed(freq.getOptions(), expFreqOptions))
			Reporter.logEvent(Status.PASS,"Validate frequency drop down values as following."+expFreqOptions,""
					+ "Frequency drop down options are:"+freq.getOptions(), false);
		else
			Reporter.logEvent(Status.FAIL,"Validate frequency drop down values as following."+expFreqOptions,""
					+ "Frequency drop down options are:"+freq.getOptions(), true);
		Web.setTextToTextBox(salaryInput,"1200000");
		freq.selectByValue("1");
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(employmentTitle,true)){
			Reporter.logEvent(Status.PASS,"Enter valid salary,select frequency and click continue.",""
					+ "Add new employment information page is displayed",false);
			isPageDisplayed = true;
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Enter valid salary,select frequency and click continue.",""
					+ "Add new employment information page is not displayed",true);
			isPageDisplayed = false;
		}
		return isPageDisplayed;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isPageDisplayed=false;
	}
}


/**
 * <pre>This method adds new employment information while adding new employee.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return boolean
 */
public boolean addNewEmploymentInfo()
{
	boolean isPageDisplayed = false;
	String expHireDateErrorMsg = Stock.GetParameterValue("expHireDateErrorMsg");
	String actHireDataErrorMsg = "";
	try{
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		for(WebElement msg : mandatoryFieldValidationMsgs)
		{
			actHireDataErrorMsg = msg.getText();
			if(expHireDateErrorMsg.equals(actHireDataErrorMsg))
			{isPageDisplayed=true;break;}
		}
		if(isPageDisplayed)
			Reporter.logEvent(Status.PASS, "Leave hire date as blank and click continue.Error message"
					+ "displayed is:"+expHireDateErrorMsg,"Error message displayed is:"+actHireDataErrorMsg, false);
		else
			Reporter.logEvent(Status.FAIL, "Leave hire date as blank and click continue.Error message"
					+ "displayed is:"+expHireDateErrorMsg,"Error message displayed is:"+actHireDataErrorMsg, true);
		isPageDisplayed=false;
		TimeZone zone = TimeZone.getTimeZone("MST");
		Calendar c = Calendar.getInstance(zone);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		c.add(Calendar.DATE,-2);
		Date d = c.getTime();
		Web.setTextToTextBox(hireDate,sdf.format(d));
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(subsetInformationTitle,true))
			isPageDisplayed=true;
		else
			isPageDisplayed=false;
		
		return isPageDisplayed;
	}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
		return isPageDisplayed=false;
	}
}

/**
 * <pre>This method adds Subset information while adding new employee.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return boolean
 */
public void addSubSetInfo()
{
	try{
		Web.clickOnElement(continueAddEmp);
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(managedAccntTitle,true))
			Reporter.logEvent(Status.PASS,"Enter subset details and click on continue.", "Managed Account"
					+ " Participant Enrollment page is displayed.", false);
		else
			Reporter.logEvent(Status.FAIL,"Enter subset details and click on continue.", "Managed Account"
					+ " Participant Enrollment page is not displayed.", true);
}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


 /** <pre>This method adds Manage Account details with participant enrollment into plan with managed accounts
  * and without any deferrals.</pre>
 * @author smykjn
 * @Date 16th-May-2017
 * @return void
 */
public void addManageAccountDetailsWithManageAccntEnroll()
{
	List<String> expManagedAccntLabels = Arrays.asList(Stock.GetParameterValue("ExpectedManagedAccntLabels").split(","));
	try{
		if(Web.isWebElementDisplayed(managedAccntTitle,true))
		{
			if(CommonLib.isAllHeadersDisplayed(managedAccntFields, expManagedAccntLabels))
				Reporter.logEvent(Status.PASS,"If managed account page is displayed,validate following"
						+ " fields are displayed:"+expManagedAccntLabels, "Expected fields are displayed.",false);
			else
				Reporter.logEvent(Status.FAIL,"If managed account page is displayed,validate following"
						+ " fields are displayed:"+expManagedAccntLabels, "All Expected fields are not displayed.",true);
			Select gender_ = new Select(gender);
			gender_.selectByVisibleText("MALE");
			Web.clickOnElement(conPptWithMangedAccntBtn);
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(changeAllocForm, true))
				Reporter.logEvent(Status.PASS,"Clcik 'Continue participant Enrollment into plan with Managed Accounts' button.",""
						+ "Participant is enrolled into Managed account.",false);
			else
				Reporter.logEvent(Status.FAIL,"Clcik 'Continue participant Enrollment into plan with Managed Accounts' button.",""
						+ "Participant is not enrolled into Managed account.",true);
			
			if(useDefltAllocBtn.isDisplayed()&&conWithoutAllocBtn.isDisplayed())
				Reporter.logEvent(Status.PASS,"Observe two buttons 'Use Default Allocation' and 'Continue Without Allocation' button"
						+ " are displayed.","Expected buttons are displayed.",false);
			else
				Reporter.logEvent(Status.FAIL,"Observe two buttons 'Use Default Allocation' and 'Continue Without Allocation' button"
						+ " are displayed.","Expected buttons are displayed.",true);
			Web.clickOnElement(useDefltAllocBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(framecA);
			Web.getDriver().switchTo().frame(framecA);
			if(autoSuiteAngularDeferralPage.isDisplayed()&&autoSuiteAngularDeferralPage.getAttribute("class").contains("ng-scope"))
				Reporter.logEvent(Status.PASS,"Click on Use Default Allocations and observe that user"
						+ "is navigated to angular page to enter deferral information.","User is navigated to "
								+ "angular page to enter deferral information.", false);
			else
				Reporter.logEvent(Status.FAIL,"Click on Use Default Allocations and observe that user"
					+ "is navigated to angular page to enter deferral information.","User is not navigated to "
							+ "angular page to enter deferral information.", true);
			Web.clickOnElement(conWithoutDeffNgBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(addEmpTitle);
			Web.getDriver().switchTo().defaultContent();
		}
		else
		{

		}
}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}

/** <pre>This method adds Manage Account details with participant enrollment into plan without managed accounts
* and without any deferrals</pre>
* @author smykjn
* @Date 16th-May-2017
* @return void
*/
public void addManageAccountDetailsWithOutManageAccntEnroll()
{
	List<String> expManagedAccntLabels = Arrays.asList(Stock.GetParameterValue("ExpectedManagedAccntLabels").split(","));
	String expAllocSumMsg = Stock.GetParameterValue("AllocationValidationMsg");
	String actAllocSumMsg="";
	boolean isDisplayed=false;
	try{
		if(Web.isWebElementDisplayed(managedAccntTitle,true))
		{
			if(CommonLib.isAllHeadersDisplayed(managedAccntFields, expManagedAccntLabels))
				Reporter.logEvent(Status.PASS,"If managed account page is displayed,validate following"
						+ " fields are displayed:"+expManagedAccntLabels, "Expected fields are displayed.",false);
			else
				Reporter.logEvent(Status.FAIL,"If managed account page is displayed,validate following"
						+ " fields are displayed:"+expManagedAccntLabels, "All Expected fields are not displayed.",true);
			Select gender_ = new Select(gender);
			gender_.selectByVisibleText("MALE");
			Web.clickOnElement(conPptWithoutMangedAccntBtn);
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(continueAddEmp, true)&&
					Web.isWebElementDisplayed(useDefltAllocBtn, true)&&
					Web.isWebElementDisplayed(conWithoutAllocBtn, true))
				Reporter.logEvent(Status.PASS,"Clcik 'Continue participant Enrollment into plan without Managed Accounts' button.",""
						+ "following buttons are displayed.1.Continue 2.Continue Using Default Allocation 3.Continue Without Allocations.",false);
			else
				Reporter.logEvent(Status.FAIL,"Clcik 'Continue participant Enrollment into plan without Managed Accounts' button.",""
						+ "following buttons are not displayed.1.Continue 2.Continue Using Default Allocation 3.Continue Without Allocations.",true);
			if(newAllocRadioButton.isDisplayed()){
			Web.clickOnElement(newAllocRadioButton);}
			Web.setTextToTextBox(allocationsRows.get(0).findElement(By.xpath(".//td[4]//input")),"10");
			for(WebElement message : mandatoryFieldValidationMsgs)
			{
				actAllocSumMsg = message.getText().trim();
				if(expAllocSumMsg.equals(actAllocSumMsg)){
					isDisplayed = true;
					break;
				}
			}
			if(isDisplayed)
				Reporter.logEvent(Status.PASS,"Enter allocation not adding up to 100% and click continue."
						+ "Error message '"+expAllocSumMsg+"' is displayed.","Error message displayed is:"+actAllocSumMsg, false);
			else
				Reporter.logEvent(Status.FAIL,"Enter allocation not adding up to 100% and click continue."
						+ "Error message '"+expAllocSumMsg+"' is displayed.","Error message displayed is:"+actAllocSumMsg, true);
			Web.setTextToTextBox(allocationsRows.get(0).findElement(By.xpath(".//td[4]//input")),"100");
			Web.clickOnElement(continueAddEmp);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(framecA);
			Web.getDriver().switchTo().frame(framecA);
			if(autoSuiteAngularDeferralPage.isDisplayed()&&autoSuiteAngularDeferralPage.getAttribute("class").contains("ng-scope"))
				Reporter.logEvent(Status.PASS,"Enter 100% allocations and click continue.","User is navigated to "
								+ "angular page to enter deferral information.", false);
			else
				Reporter.logEvent(Status.FAIL,"Enter 100% allocations and click continue.","User is not navigated to "
							+ "angular page to enter deferral information.", true);
			Web.clickOnElement(conWithoutDeffNgBtn);
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(addEmpTitle);
			Web.getDriver().switchTo().defaultContent();
		}
		else
		{

		}
}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}


/** <pre>This method validates error message when SSN under locked plan is searched.</pre>
* @author smykjn
* @Date 4th-July-2017
* @return void
*/
public void validateSSNForLockedPlan()
{
	String expectedMsg = Stock.GetParameterValue("ExpectedMessage");
	try{
		UserVerificationPage uvp = new UserVerificationPage();
		Web.getDriver().switchTo().defaultContent();
		String error = uvp.getErrorMessageText();
		if(error.equalsIgnoreCase(expectedMsg))
			Reporter.logEvent(Status.PASS,"Search SSN that is associated with locked plan and observe the"
					+ " error message:"+expectedMsg,"Below Error message is displayed when SSN is searched for Loacked plan."
							+ "\n"+error, false);
		else
			Reporter.logEvent(Status.FAIL,"Search SSN that is associated with locked plan and observe the"
					+ " error message:"+expectedMsg,"below Error message is displayed when SSN is searched for Loacked plan."
							+ "\n"+error, true);
		Web.clickOnElement(Web.returnElement(uvp, "DISMISS"));
}catch(Exception e)
	{
		e.printStackTrace();
		Reporter.logEvent(Status.FAIL, "Exception occurred:", e.getMessage(), true);
	}
}




}

	
	
	
	

