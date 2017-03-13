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

import lib.DB;
import lib.Reporter;

import com.aventstack.extentreports.*;

import framework.util.CommonLib;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
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
	
	
	String maritalStatus = null;
	String state1 = null;
	String country1 = null;
	String relanShipCode = null;
	String beneTypeCode = null;
	public  String[] str1 = new String[2];
	public String[] individual = new String[3];
	
	Map<String,String> m = new LinkedHashMap<String,String>();
	LoadableComponent<?> parent;
	Actions actions;
	Select select;
	ResultSet queryResultSet;
	List<String> listSSN;
	Set<String> setSSN;
	List<String> expectedListofElements;
	List<String> actualListofElements;
	

	public EmployeeSearch() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Web.getDriver().switchTo().defaultContent();
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
			//actions = new Actions(Web.getDriver());
			//actions.moveToElement(tabEmployees).click();
			//actions.click();
			Web.clickOnElement(tabEmployees);
			Web.waitForElement(drpdwnSearchEmployee);
			actions = new Actions(Web.getDriver());
			actions.moveToElement(linkProfile);
			actions.build().perform();

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
		Web.setTextToTextBox(txtSearchbox, Name);		
		Thread.sleep(2000);
		btnGoEmployeeSearch.click();
		Thread.sleep(2500);		
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
		Thread.sleep(2000);
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
		select = new Select(drpdwnSearchEmployee);
		select.selectByVisibleText("Division");
		if (Web.isWebElementDisplayed(tableDivresults)) {
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
				"Participant ID", "Division"};
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
				planNumwithDivQuery[1], "K_" + username);
		return queryResultSet;
	}

	/**
	 * It verifies the filter functionality by taking a text as the filter criteria
	 * @param searchText
	 */
	public void verifyFilterFunctionality(String searchText) {
		switchToFrame();
		Web.setTextToTextBox(txtFilter, searchText);
		Web.waitForElement(searchResultsTablerow);
		if (Web.isWebElementDisplayed(searchResultsTablerow)) {
			if (StringUtils.containsIgnoreCase(searchResultsLastName.getText(),
					searchText)
					|| StringUtils.containsIgnoreCase(
							searchResultsFirstName.getText(), searchText)
					|| StringUtils.containsIgnoreCase(
							searchResultsMI.getText(), searchText)
					|| StringUtils.containsIgnoreCase(linkSSN.getText(),
							searchText)
					|| StringUtils.containsIgnoreCase(linkEXT.getText(),
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
						"The SSN are masked for the specific plan", true);
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
		//String empName = empNameHeader.getText();
		Web.clickOnElement(empInfoEditLink);
		Thread.sleep(3000);
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(empInfoEditFrame);
		CommonLib.waitForProgressBar();
		Web.getDriver().switchTo().frame(empInfoEditFrame);
		Web.waitForElement(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+ssn+"')]")));
		//Web.waitForElement(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+empName+"')]")));
		if(Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+ssn+"')]")).getText().contains(ssn))
				//&& Web.getDriver().findElement(By.xpath("//font[contains(text(),'"+empName+"')]")).getText().contains(empName))
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
			Reporter.logEvent(Status.PASS, "Verify labels on contact info section:'"+expLabels2+"'", "actual labels are displayed:'"+actLabels+"'.", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify labels on contact info section:'"+expLabels2+"'", "actual labels are not displayed:'"+actLabels+"'.", true);
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
	
	
	/*
	 * This method takes user to the employee overview page
	 */
	public void navigateToEmployeeOverViewPage() throws Exception
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
	public void Verify_Recently_Viewed_Employee() throws Exception
	{
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		CommonLib.waitForProgressBar();
		Web.clickOnElement(recentlyViewedLink);
		Web.waitForElement(recentlyViewedEmpTable);
		if(recentlyViewedEmpTable.isDisplayed())
		{
			Reporter.logEvent(Status.PASS,"Verify after clicking on recently viewed link,option collapses.", "reccently viewed option gets collapsed after clicking on Recently viewed link.", false);
			String recentEmp = recentlyViewedEmployee.get(0).getText();
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
		
		
	}
	

	/*
	 * This method is used to navigate to employee detail page on overview page
	 */
	public void navigateToEmpDetailPage() throws Exception
	{
		
		Web.getDriver().switchTo().frame(employeeSearchFrame);
		Web.waitForElement(partDetailTab);
		CommonLib.waitForProgressBar();
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
