/**
 * 
 */
package org.bdd.psc.pageobjects;

import java.util.concurrent.TimeUnit;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;
import lib.Web;

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
	
	@FindBy(how=How.XPATH,using=".//*[@id='newMenu']//*[contains(text(),'Employees')]")
	private WebElement employeesTab;
	@FindBy(how=How.XPATH,using=".//*[@id='newMenu']//li//a[text()='Search employee']")
	private WebElement searchEmployee;
	@FindBy(how=How.ID,using="framec")
	private WebElement employeeFrame;
	@FindBy(how=How.ID,using="searchSelector")
	private WebElement searchEmpByDropDown;
	@FindBy(how=How.ID,using="searchEmployeeName")
	private WebElement searchEmpTextBox;
	@FindBy(how=How.ID,using="employeeSearchButton")
	private WebElement employeeSearchButton;
	@FindBy(how=How.XPATH,using= ".//*[@id='searchResultsTable_row_0']/td")
	private WebElement searchResultsTablerow;
	@FindBy(how=How.XPATH,using=".//tbody[@id='searchResultsTable_data']/tr[1]/td[2]/a")
	private WebElement firstNameLink;
	@FindBy(how=How.ID,using="participantDetail")
	private WebElement employeeDetailTab;
	
	public EmployeePages(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
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
		if(!(planNumber==null)){
			new HomePage().switchPlan(planNumber);
		}
		Web.waitForElement(employeesTab);
		Web.actionsClickOnElement(employeesTab);
		Web.waitForElement(employeeFrame);
		Web.FrameSwitchONandOFF(true, employeeFrame);
		if(Web.isWebElementDisplayed(employeeSearchButton, true)){
			Reporter.logEvent(Status.INFO, "User is on employee search page", "User is on employee search page", true);
		}
		
		
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(employeeSearchButton));
	}
	
	
	/**Method to search employee by SSN,Name,Employee ID,Participant ID, Division, etc.
	 * @param searchEmployeeBy
	 * @param searchText - Optional if no searchFilter required.
	 */
	public void searchEmployeeBy(String searchEmployeeBy, String... searchText){
		if(searchEmployeeBy==null){
			return;
		}
		Web.selectDropDownOption(searchEmpByDropDown, searchEmployeeBy);
		Web.waitForElement(employeeSearchButton);
		if(searchText.length>0){
			Web.setTextToTextBox(searchEmpTextBox, searchText);
		}
		Web.clickOnElement(employeeSearchButton);
		Web.waitForProgressBar();
	}
	
	private boolean isSearchResultDisplayed(){
		try{
			Web.FrameSwitchONandOFF(true, employeeFrame);
			if(Web.isWebElementDisplayed(searchResultsTablerow)){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}
	
	private void clickOnFirstNameLinkInSearchResult(){
		if(isSearchResultDisplayed()){
			Web.clickOnElement(firstNameLink);
		}
	}
	
	public void openEmployeeOverviewPage(){
		this.clickOnFirstNameLinkInSearchResult();
		Web.waitForProgressBar();
		Web.FrameSwitchONandOFF(true, employeeFrame);
		if(Web.isWebElementDisplayed(employeeDetailTab, true)){
			Reporter.logEvent(Status.INFO, "User is on employee overview page", 
					"User is on employee overview page", true);
		}
	}
	
	public void openEmployeeDetailPage(){
		if(Web.isWebElementDisplayed(employeeDetailTab))
			Web.clickOnElement(employeeDetailTab);
		else
			Reporter.logEvent(Status.INFO, "Employee Detail tab is not displayed", 
					"Employee Detail tab is not displayed", true);
	}

}
