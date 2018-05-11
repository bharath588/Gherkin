/**
 * 
 */
package org.bdd.psc.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import reporter.Reporter;
import annotations.FindBy;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

/**
 * @author rvpndy
 *
 */
public class EmployeePages extends LoadableComponent<EmployeePages> {

	LoadableComponent<?> parent;
	public static String planNumber;

	@FindBy(how = How.XPATH, using = ".//*[@id='newMenu']//*[contains(text(),'Employees')]")
	private WebElement employeesTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='newMenu']//li//a[text()='Search employee']")
	private WebElement searchEmployee;
	@FindBy(how = How.ID, using = "framec")
	private WebElement employeeFrame;
	@FindBy(how = How.ID, using = "searchSelector")
	private WebElement searchEmpByDropDown;
	@FindBy(how = How.ID, using = "searchEmployeeName")
	private WebElement searchEmpTextBox;
	@FindBy(how = How.ID, using = "employeeSearchButton")
	private WebElement employeeSearchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchResultsTable_row_0']/td")
	private WebElement searchResultsTablerow;
	@FindBy(how = How.XPATH, using = ".//tbody[@id='searchResultsTable_data']/tr[1]/td[2]/a")
	private WebElement firstNameLink;
	@FindBy(how = How.ID, using = "participantDetail")
	private WebElement employeeDetailTab;
	@FindBy(how = How.XPATH, using = "//table/tbody[@id='overviewtable_data']/tr/td/a")
	private List<WebElement> planNumberLinksInOverviewTable;
	@FindBy(how = How.XPATH, using = ".//*[@id='topLevelTabs']/ul/li/a")
	private List<WebElement> topLevelTabs;
	@FindBy(how = How.XPATH, using = ".//*[@id='planTitle']/span[1]")
	private WebElement planTitleOnAccountBalancePage;
	@FindBy(how = How.ID, using = "framecA")
	private WebElement iFramecA;
	@FindBy(how = How.ANGULAR_ATTRIBUTE, using = "click", text = "returnToEmployeeOverview()")
	private WebElement returnToEmpOverviewLink;
	@FindBy(how = How.ID, using = "headerInfo_xhtml")
	private WebElement planHeaderOnOverview;

	public EmployeePages() {
		Web.getDriver().manage().timeouts()
				.setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver());
		NextGenPageFactory.initWebElements(Web.getDriver(), this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		HomePage homepage = (HomePage) this.parent;
		// LoginPage login = new LoginPage();
		new HomePage(new LoginPage(), false, new String[] {
				Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password") }).get();
		Reporter.logEvent(Status.PASS,
				"Check if the user has landed on homepage",
				"The user has landed on homepage", true);
		Web.waitForElement(employeesTab);
		if (!(planNumber == null)) {
			new HomePage().switchPlan(planNumber);
		}
		Web.waitForElement(employeesTab);
		Web.actionsClickOnElement(employeesTab);
		Web.actionsClickOnElement(searchEmployee);
		Web.waitForElement(employeeFrame);
		Web.FrameSwitchONandOFF(true, employeeFrame);
		if (Web.isWebElementDisplayed(employeeSearchButton, true)) {
			Reporter.logEvent(Status.INFO, "User is on employee search page",
					"User is on employee search page", true);
		}

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(employeeSearchButton));
	}

	public void setSearchBy(String searchEmployeeBy) {
		if (searchEmployeeBy == null) {
			return;
		}
		Web.selectDropDownOption(searchEmpByDropDown, searchEmployeeBy);
		Web.waitForElement(employeeSearchButton);
	}

	public void enterSearchTextAndSearch(String searchText) {
		Web.setTextToTextBox(searchEmpTextBox, searchText);
		Web.clickOnElement(employeeSearchButton);
		Web.waitForProgressBar();
	}

	public void enterSearchText(String searchText) {
		if (Web.isWebElementDisplayed(searchEmpTextBox)) {
			Web.setTextToTextBox(searchEmpTextBox, searchText);
		}
	}

	public void clickOnSearchButton() {
		if (Web.isWebElementDisplayed(employeeSearchButton, false)) {
			Web.clickOnElement(employeeSearchButton);
			Web.waitForProgressBar();
		}
	}

	/**
	 * Method to search employee by SSN,Name,Employee ID,Participant ID,
	 * Division, etc.
	 * 
	 * @param searchEmployeeBy
	 * @param searchText
	 *            - Optional if no searchFilter required.
	 */
	public void searchEmployeeBy(String searchEmployeeBy, String... searchText) {
		if (searchEmployeeBy == null) {
			return;
		}
		Web.selectDropDownOption(searchEmpByDropDown, searchEmployeeBy);
		Web.waitForElement(employeeSearchButton);
		if (searchText.length > 0) {
			Web.setTextToTextBox(searchEmpTextBox, searchText);
		}
		Web.clickOnElement(employeeSearchButton);
		Web.waitForProgressBar();
	}

	private boolean isSearchResultDisplayed() {
		try {
			Thread.sleep(3000);
			Web.FrameSwitchONandOFF(true, employeeFrame);
			if (Web.isWebElementDisplayed(searchResultsTablerow)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public void clickOnFirstNameLinkInSearchResult() {
		if (isSearchResultDisplayed()) {
			Web.clickOnElement(firstNameLink);
		}
	}

	public void openEmployeeOverviewPage() {
		this.clickOnFirstNameLinkInSearchResult();
		Web.waitForProgressBar();
		Web.FrameSwitchONandOFF(true, employeeFrame);
		if (Web.isWebElementDisplayed(employeeDetailTab, true)) {
			Reporter.logEvent(Status.INFO, "User is on employee overview page",
					"User is on employee overview page", true);
		}
	}

	public void openEmployeeDetailPage() {
		if (Web.isWebElementDisplayed(employeeDetailTab))
			Web.clickOnElement(employeeDetailTab);
		else
			Reporter.logEvent(Status.INFO,
					"Employee Detail tab is not displayed",
					"Employee Detail tab is not displayed", true);
	}

	public void clickOnViewButtonOfPlan(String planNumber) {
		WebElement viewButton = null;
		if (planNumberLinksInOverviewTable.size() > 0) {
			for (WebElement ele : planNumberLinksInOverviewTable) {
				if (ele.getText().equalsIgnoreCase(planNumber)) {
					viewButton = ele
							.findElement(By
									.xpath("./parent::td/following-sibling::td//button"));
					break;
				}
			}
			Web.clickOnElement(viewButton);
			Web.waitForProgressBar();
			Web.FrameSwitchONandOFF(false);
			Web.waitForElement(iFramecA);
		}
	}

	public void returnToEmployeeOverviewPage() throws InterruptedException {
		Thread.sleep(3000);
		Web.FrameSwitchONandOFF(true, iFramecA);
		if (Web.isWebElementDisplayed(planTitleOnAccountBalancePage)) {
			Reporter.logEvent(Status.INFO,
					"On account balance page of plan number:",
					planTitleOnAccountBalancePage.getText(), true);
		}
		if (Web.isWebElementDisplayed(returnToEmpOverviewLink)) {
			Web.clickOnElement(returnToEmpOverviewLink);
			Web.FrameSwitchONandOFF(false);
			Web.FrameSwitchONandOFF(true, employeeFrame);
			Web.waitForProgressBar();
		}
	}

	public void clickOnTopLevelTabsAndVerifyPlanHeader(List<String> tabs) throws InterruptedException {
		Thread.sleep(2000);
		int count = 0;
		if (topLevelTabs.size() > 0) {
			for (String tab : tabs) {
				for (WebElement ele : topLevelTabs) {
					if (ele.getText().equalsIgnoreCase(tab.trim())) {
						Web.clickOnElement(ele);
						Thread.sleep(1500);
						if (planHeaderOnOverview.getText().contains(
								EmployeePages.planNumber))
							count++;
					}
				}
			}
		}
		if (count == tabs.size()) {
			Reporter.logEvent(
					Status.PASS,
					"Data under tabs reflect data for the plan displayed in header on employee overview page",
					"Data under tabs reflect data for the plan displayed in header on employee overview page",
					true);
		} else {
			
			Reporter.logEvent(
					Status.FAIL,
					"Data under tabs reflect data for the plan displayed in header on employee overview page",
					"Data under tabs do not reflect data for the plan displayed in header on employee overview page",
					true);
		}
	}

}
