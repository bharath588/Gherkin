/**
 * 
 */
package pscBDD.employee;



import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;







import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;


import bdd_annotations.FindBy;
import bdd_core.framework.Globals;
import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_lib.DB;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;



/**
 * @author rvpndy
 *
 */
public class EmployeePages extends LoadableComponent<EmployeePages> {

	LoadableComponent<?> parent;
	public static String planNumber;
	ResultSet queryResultSet;
	@FindBy(how = How.XPATH, using = ".//*[@id='newMenu']//*[contains(text(),'Employees')]")
	private WebElement employeesTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='newMenu']//li//a[text()='Search employee']")
	private WebElement searchEmployee;
	@FindBy(how = How.XPATH, using = ".//*[@id='newMenu']//li//a[text()='Add employee']")
	private WebElement addEmployee;
	
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
	
	
	@FindBy(how=How.ID,using="payCheckAutoSuiteEditLink")
	private WebElement paycheckContributionEditLink;
	@FindBy(how=How.ID,using="framecA")
	private WebElement editDeferralFrame;
	
	@FindBy(how=How.XPATH,using= ".//*[text()='Current and Pending Deferrals']")
	private WebElement deferralText;
	@FindBy(how=How.XPATH,using= "//*[text()='Employee does not have any current deferrals.']")
	private WebElement employeeDoesNotHaveAnyCurrentDeferrals;
	@FindBy(how=How.XPATH,using= ".//*[*[span[text()='Ongoing']]]//a")
	private WebElement ongoingEditLink;
	@FindBy(how=How.XPATH,using= "//*[*[span[text()='Schedule automatic increase']]]//a")
	private WebElement scheduleAutomaticIncreaseEditLink;
	
	@FindBy(how=How.ID,using="aedrvalue1")
	private WebElement enterDefElectionInDeflInformForBeforeTax;
	//@FindBy(how=How.NAME,using="customDate1")
	@FindBy(how=How.XPATH,using="//*[input[@id='aedrvalue1']]/following-sibling::div//select")
	private WebElement enterDateInDeflInformForBeforeTax;
	@FindBy(how=How.XPATH,using=".//div[*[*[@id='aedrvalue1']]]//button[@id='two']")
	private WebElement dollarButtonInDeflInformForBeforeTax;
	
	@FindBy(how=How.ID,using="aedrvalue0")
	private WebElement enterDefElectionContributionBeforeTax;
	@FindBy(how=How.ID,using="aedrvalue1")
	private WebElement enterDefElectionContributionRoth;
	
	@FindBy(how=How.ID,using="aedrvalue2")
	private WebElement enterDefElectionInDeflInformForRoth;
	//@FindBy(how=How.NAME,using="customDate2")
	@FindBy(how=How.XPATH,using="//*[input[@id='aedrvalue2']]/following-sibling::div//select")
	private WebElement enterDateInDeflInformForRoth;
	@FindBy(how=How.XPATH,using=".//div[*[*[@id='aedrvalue2']]]//button[@id='two']")
	private WebElement dollarButtonInDeflInformForRoth;
	@FindBy(how=How.ID,using="ongoingContinueButton")
	private WebElement ongoingContinueButton;
	@FindBy(how=How.ANGULAR_ATTRIBUTE,using="click",text="saveEditForm")
	private WebElement ongoingButtonContinue;
	@FindBy(how=How.XPATH,using="//button[text()='Done']")
	private WebElement buttonConfirmationDone;
	@FindBy(how=How.XPATH,using="//td[text()='Before Tax']/preceding-sibling::td[2]")
	private List<WebElement> effectiveDateForBeforeTax;
	@FindBy(how=How.XPATH,using="//td[text()='Roth']/preceding-sibling::td[2]")
	private List<WebElement> effectiveDateRoth;
	
	@FindBy(how=How.XPATH,using="//*[*[label[contains(text(),'Deferral information for Before Tax')]]]//select[@id='customDt']")
	private WebElement selectTargetPayrollBeforeTax;
	@FindBy(how=How.XPATH,using="//*[*[label[contains(text(),'Deferral information for Before Tax')]]]//*[@id='two']")
	private WebElement dollarButtonBeforeTax;
	@FindBy(how=How.XPATH,using="//*[*[label[contains(text(),'Deferral information for Before Tax')]]]//input[@ng-model='aedr.increaseValue']")
	private WebElement incrementTextBoxBeforeTax;
	@FindBy(how=How.XPATH,using="//*[*[label[contains(text(),'Deferral information for Before Tax')]]]//input[@ng-model='aedr.stopAtValue']")
	private WebElement maximumAmountTextBoxBeforeTax;
	@FindBy(how=How.ID,using="scheduleContinueButton")
	private WebElement scheduleContinueButton;
	
	@FindBy(how=How.LINK_TEXT,using="Vesting")
	private WebElement vestingLink;
	@FindBy(how=How.XPATH,using="//*[contains(text(),'Vesting balance information')]")
	private WebElement vestingSectionNoData;
	@FindBy(how=How.ID,using="vestingForm:vestingPanel_content")
	private WebElement vestingFormDataNewWindow;
	@FindBy(how=How.XPATH,using="//*[@id='vestingDialog']/preceding-sibling::div//span[text()='close']")
	private WebElement vestingFormDataNewWindowCloseButton;
	@FindBy(how=How.XPATH,using="//*[@id='vestingDashboard']/table")
	private WebElement vestingInformationTableData;
	
	@FindBy(how=How.XPATH,using="//*[@id='overviewtable_data']/tr/td[1]/a")
	private List<WebElement> planNumberInAccountBalanceSection;
	
	@FindBy(how=How.ID,using="empInfoEditLinkNextGen")
	private WebElement empInformationEditLink;
	
	//objects in employment information section
	
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Union:']")
	private WebElement empInformationUnionLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Insider:']")
	private WebElement empInformationInsiderLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Super officer:']")
	private WebElement empInformationSuperOfficerLabel;
	//@FindBy(how=How.XPATH,using="//label[normalize-space()='FT/PT employee:']")
	@FindBy(how=How.XPATH,using="//label[contains(text(),'Full time/Part time:') or contains(text(),'FT/PT employee:')]")
	private WebElement empInformationFTPTemployeeLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Job description:']")
	private WebElement empInformationJobDescriptionLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Employment type:']")
	private WebElement empInformationEmploymentTypeLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Overseas employee:']")
	private WebElement empInformationOverseasEmployeeLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Overseas date:']")
	private WebElement empInformationOverseasDateLabel;
	@FindBy(how=How.XPATH,using="//label[normalize-space()='Employer classification code:']")
	private WebElement empInformationEmployerClassificationCodeLabel;
	
	@FindBy(how=How.LINK_TEXT,using="Details")
	private WebElement detailsLinkUnderEmploymentHistory;
	
	//objects under update employment information section
	
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[contains(text(),'Union')]")
	private WebElement updateEmpInformationUnionLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[contains(text(),'Insider')]")
	private WebElement updateEmpInformationInsiderLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[contains(text(),'Super officer')]")
	private WebElement updateEmpInformationSuperOfficerLabel;
	//@FindBy(how=How.XPATH,using="//label[normalize-space()='FT/PT employee:']")
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[contains(text(),'Full time/Part time') or contains(text(),'FT/PT employee')]")
	private WebElement updateEmpInformationFTPTemployeeLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[normalize-space()='Job description']")
	private WebElement updateEmpInformationJobDescriptionLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[normalize-space()='Employment type']")
	private WebElement updateEmpInformationEmploymentTypeLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[normalize-space()='Overseas employee']")
	private WebElement updateEmpInformationOverseasEmployeeLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[normalize-space()='Overseas date']")
	private WebElement updateEmpInformationOverseasDateLabel;
	@FindBy(how=How.XPATH,using="//form[@name='employment.step2.form']//label[normalize-space()='Employer classification code']")
	private WebElement updateEmpInformationEmployerClassificationCodeLabel;
	
	
	
	SimpleDateFormat sdf;
	Calendar c;
	LocalDate date;

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

	public boolean isSearchResultDisplayed() {
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
			Web.waitForProgressBar();
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
	public boolean isEmployeeDetailsPage(){
		if (Web.isWebElementDisplayed(employeeDetailTab))
			return true;
		return false;
		
	}

	public void clickOnEmpInfoEditLink() {
		if (Web.isWebElementDisplayed(empInformationEditLink, true)) {
			Web.clickOnElement(empInformationEditLink);
			Web.nextGenDriver.waitForAngular();
		}
		
	}
	public void clickOnDetailsLinkUnderEmploymentHistory() {
		if (Web.isWebElementDisplayed(detailsLinkUnderEmploymentHistory, true)) {
			Web.clickOnElement(detailsLinkUnderEmploymentHistory);
			Web.nextGenDriver.waitForAngular();
		}
		
	}
	
	public void clickOnVestingLink(){
		if(Web.isWebElementDisplayed(vestingLink, true))
			Web.clickOnElement(vestingLink);
	}
	
	public boolean verifyVestingSectionMessage(String input){
		if(Web.isWebElementDisplayed(vestingSectionNoData, true)){
			if(vestingSectionNoData.getText().trim().equalsIgnoreCase(input.trim()))
				return true;
		}
			
		return false;
		
	}
	public boolean verifyVestingWindow() throws InterruptedException{
		if(Web.isWebElementDisplayed(vestingFormDataNewWindow, true)){
			Web.actionsClickOnElement(vestingFormDataNewWindowCloseButton);
			Thread.sleep(3000);
			return true;
		}
		return false;
		
	}
	public boolean verifyVestingSectionDisplays(){
		if(Web.isWebElementDisplayed(vestingInformationTableData, true))
			return true;
		return false;
		
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
	
	
	public void openDeferralDetailsPage() throws InterruptedException{
		Web.clickOnElement(employeeSearchButton);
		Web.nextGenDriver.waitForAngular();	
		this.openEmployeeOverviewPage();
		this.openEmployeeDetailPage();
		if(Web.isWebElementDisplayed(paycheckContributionEditLink,true))
			Web.clickOnElement(paycheckContributionEditLink);
		else
			Reporter.logEvent(Status.INFO, "paycheck Contribution Edit Link is not displayed", 
					"paycheck Contribution Edit Link is not displayed", true);
	    Web.nextGenDriver.waitForAngular();
	    Web.FrameSwitchONandOFF(false);
	    Web.isWebElementDisplayed(editDeferralFrame,true);
		Web.FrameSwitchONandOFF(true, editDeferralFrame);
		
		if(Web.isWebElementDisplayed(deferralText,true))
			Reporter.logEvent(Status.INFO, "User is on deferral page", 
					"User is on deferral page", true);
		else
			Reporter.logEvent(Status.INFO, "User is on deferral page", 
					"User isn't landed on deferral page", true);
	}
	
	public void clickOngoingEdit(){
		Web.clickOnElement(ongoingEditLink);
		Web.nextGenDriver.waitForAngular();
		
	}
	public void clickScheduleAutomaticIncreaseEdit(){
		Web.clickOnElement(scheduleAutomaticIncreaseEditLink);
		Web.nextGenDriver.waitForAngular();
		
	}

	public void checkAndAddOngoingDeferral(String deferraltype) {
		try {
			if (Web.isWebElementDisplayed(
					employeeDoesNotHaveAnyCurrentDeferrals, true))
				if (Web.isWebElementDisplayed(ongoingEditLink, true)) {
					this.clickOngoingEdit();
					this.enterContributionAndSave(deferraltype);
					Reporter.logEvent(Status.INFO,
							"added ongoing deferral for the participant",
							"added ongoing deferral for the participant", true);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void enterFutureDateContributionAndSave(String type,String...deferralDate){
		if(type.trim().equalsIgnoreCase("Before Tax")){
			Web.setTextToTextBox(enterDefElectionInDeflInformForBeforeTax, "50");
			Web.clickOnElement(dollarButtonInDeflInformForBeforeTax);
			if(deferralDate.length>0)
				Web.selectDropDownOption(enterDateInDeflInformForBeforeTax, this.getDateFormat(deferralDate[0]), true);
				//Web.setTextToTextBox(enterDateInDeflInformForBeforeTax, this.getDateFormat(deferralDate[0]));
		}
		if(type.trim().equalsIgnoreCase("Roth")){
			Web.setTextToTextBox(enterDefElectionInDeflInformForRoth, "50");
			Web.clickOnElement(dollarButtonInDeflInformForRoth);
			if(deferralDate.length>0)
				Web.selectDropDownOption(enterDateInDeflInformForRoth, this.getDateFormat(deferralDate[0]), true);
				//Web.setTextToTextBox(enterDateInDeflInformForRoth, this.getDateFormat(deferralDate[0]));
		}
		Web.clickOnElement(ongoingContinueButton);
		Web.nextGenDriver.waitForAngular();
		if(Web.isWebElementDisplayed(ongoingButtonContinue, true))
			Web.clickOnElement(ongoingButtonContinue);
		Web.nextGenDriver.waitForAngular();
		if(Web.isWebElementDisplayed(buttonConfirmationDone, true))
			Web.clickOnElement(buttonConfirmationDone);
		Web.nextGenDriver.waitForAngular();
		
		
	}
	public void enterContributionAndSave(String type,String...deferralDate){
		if(type.trim().equalsIgnoreCase("Before Tax")){
			Web.setTextToTextBox(enterDefElectionContributionBeforeTax, "50");
			Web.clickOnElement(dollarButtonInDeflInformForBeforeTax);
		}
		if(type.trim().equalsIgnoreCase("Roth")){
			Web.setTextToTextBox(enterDefElectionContributionRoth, "50");
			Web.clickOnElement(dollarButtonInDeflInformForRoth);
		}
		Web.clickOnElement(ongoingContinueButton);
		Web.nextGenDriver.waitForAngular();
		if(Web.isWebElementDisplayed(ongoingButtonContinue, true))
			Web.clickOnElement(ongoingButtonContinue);
		Web.nextGenDriver.waitForAngular();
		if(Web.isWebElementDisplayed(buttonConfirmationDone, true))
			Web.clickOnElement(buttonConfirmationDone);
		Web.nextGenDriver.waitForAngular();
		
		
	}
	
	public String getDateFormat(String inputDate){
		try {
			inputDate = inputDate.replaceAll("[^a-zA-Z0-9]", "");
			String regexp = "(\\d+)";
			String dateCount = "";
			Matcher matcher = Pattern.compile(regexp).matcher(inputDate);
			while (matcher.find()) {
				dateCount = matcher.group();
				System.out.println(dateCount);
			}
			int COUNT = Integer.parseInt(dateCount);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			c = Calendar.getInstance();
			c.add(Calendar.DATE, COUNT);
			String output = sdf.format(c.getTime());
			System.out.println(output);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean isEffectiveDateSameAsFutureDate(String deferralType,String...futureDate) throws ParseException{
		
		Boolean flag=false;
		try{
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			String enteredDate=sdf.format(Calendar.getInstance().getTime());
			
			if(futureDate.length>0)
				enteredDate=this.getDateFormat(futureDate[0]);
			
			if(deferralType.trim().equalsIgnoreCase("Before Tax")){
				for(WebElement dateElement:effectiveDateForBeforeTax){
					if(enteredDate.equals(sdf.format(sdf.parse(dateElement.getText().trim()))))
						return true;			
				}
			}
			if(deferralType.trim().equalsIgnoreCase("Roth")){
				for(WebElement dateElement:effectiveDateRoth){
					if(enteredDate.equals(sdf.format(sdf.parse(dateElement.getText().trim()))))
						return true;			
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	public boolean isEffectiveDateWeekend(String deferralType){
		
		if(deferralType.trim().equalsIgnoreCase("Before Tax")){
			for(WebElement dateElement:effectiveDateForBeforeTax){
				if(isWeekend(dateElement.getText().trim()))
					return true;			
			}
		}
		if(deferralType.trim().equalsIgnoreCase("Roth")){
			for(WebElement dateElement:effectiveDateRoth){
				if(isWeekend(dateElement.getText().trim()))
					return true;			
			}
		}
		return false;
	}
	
	public boolean isWeekend(String dateText){
		int i=0;
		int[] dateArr=new int[3];
		String regexp = "(\\d+)";
		Matcher matcher = Pattern.compile(regexp).matcher(dateText);		
		while (matcher.find()) {
			dateArr[i]= Integer.parseInt(matcher.group());
			i++;
		}
		 date = LocalDate.of(dateArr[2], dateArr[0],dateArr[1]);//MM/dd/yyyy-->YYYY,MM,DD
		for (int j = 0; j < date.lengthOfMonth(); j++) {
			System.out.println(date.getDayOfWeek().toString());
			if ("Saturday".equalsIgnoreCase(date.getDayOfWeek()
					.toString())
					|| "Sunday".equalsIgnoreCase(date.getDayOfWeek()
							.toString())) {
				return true;
			} else {
				date = date.plusDays(1);
			}
		}
		return false;
	}
	
	public String getPlanNumber(String query, String sdsv_subcode) {
		String planNo = "";
		try {
			ResultSet resultSet = DB
					.executeQuery("D_INST", query, sdsv_subcode);
			while (resultSet.next()) {
				planNo = resultSet.getString(1);
				break;
			}
			if (planNo.length() > 0)
				planNo = resultSet.getString(1);
			else
				Reporter.logEvent(Status.INFO,
						"No record founds in the given query",
						"No record founds in the given query", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return planNo;

	}
	
	public boolean isAllDateAvialableInSelectTargetPayRoll() {
		try {
			LocalDate start = LocalDate.now();
			LocalDate end = LocalDate.now().plusMonths(18)
					.with(TemporalAdjusters.lastDayOfMonth());
			List<String> dates = Stream
					.iterate(start, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(start, end))
					.map(element -> (String) element.format(DateTimeFormatter
							.ofPattern("yyyy/MM/dd")))
					.collect(Collectors.toList());

			dates.remove(0);
			System.out.println(dates);
			Web.FrameSwitchONandOFF(false);
			Web.isWebElementDisplayed(editDeferralFrame, true);
			Web.FrameSwitchONandOFF(true, editDeferralFrame);

			List<String> dropDownDate = new ArrayList<String>();
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			if (Web.isWebElementDisplayed(selectTargetPayrollBeforeTax, true)) {
				List<WebElement> element = new Select(
						selectTargetPayrollBeforeTax).getOptions();
				element.remove(0);
				element.remove(element.size() - 1);
				element.forEach(ele -> dropDownDate.add(this.doFormat(ele
						.getText())));
			}

			if (dates.containsAll(dropDownDate))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String doFormat(String input) {
		 sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			String output = sdf.format(new SimpleDateFormat("MM/dd/yyyy")
					.parse(input.trim()));
			return output;
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}
	
	public boolean isGivenDateAvailableInSelectTargetPayRoll(String dateString){
		
		return false;
		
	}
	public void addSchedule(String deferralType,String weekendDate){
		if(deferralType.trim().equalsIgnoreCase("Before Tax")){
			Web.selectDropDownOption(selectTargetPayrollBeforeTax, weekendDate, true);
			Web.setTextToTextBox(incrementTextBoxBeforeTax, "20");
			Web.setTextToTextBox(maximumAmountTextBoxBeforeTax, "50");
			Web.clickOnElement(dollarButtonBeforeTax);
			Web.clickOnElement(scheduleContinueButton);	
			Web.nextGenDriver.waitForAngular();
		}
		
		
	}
	
	public boolean isTheDateWithinNextMonth(String deferralType) {
		try {
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate, endDate;
			Date dateToCheck=null;
	
			c = Calendar.getInstance();
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DATE, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			startDate = sdf.parse(sdf.format(c.getTime()));
			System.out.println(startDate);
			c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate = sdf.parse(sdf.format(c.getTime()));
			System.out.println(endDate);
			
			if(deferralType.trim().equalsIgnoreCase("Before Tax")){
				for(WebElement dateElement:effectiveDateForBeforeTax){
					dateToCheck = sdf.parse(dateElement.getText().trim());
					if (dateToCheck.equals(startDate)
							|| dateToCheck.equals(endDate)
							|| (dateToCheck.after(startDate) && dateToCheck
									.before(endDate)))
						return true;
				}
			}
			if(deferralType.trim().equalsIgnoreCase("Roth")){
				for(WebElement dateElement:effectiveDateRoth){
					dateToCheck = sdf.parse(dateElement.getText().trim());
					if (dateToCheck.equals(startDate)
							|| dateToCheck.equals(endDate)
							|| (dateToCheck.after(startDate) && dateToCheck
									.before(endDate)))
						return true;
				}
			}
			
		} catch (Exception e) {

		}
		return false;

	}
	
	public boolean isCalendarType(String deferralType, String calendarType){
		if(calendarType.trim().equalsIgnoreCase("Calendar")){
			if(deferralType.trim().equalsIgnoreCase("Before Tax")){
				if(Web.isWebElementDisplayed(enterDateInDeflInformForBeforeTax, true))
					return true;
			}
			if(deferralType.trim().equalsIgnoreCase("Roth")){
				if(Web.isWebElementDisplayed(enterDateInDeflInformForRoth, true))
					return true;
			}
		}	
		return false;
		
	}

	String balanceXpath="";
	String viewButtonXpath="";

	public boolean verifyAccountBalanceSuppressed(String planNo) {
		Web.nextGenDriver.waitForAngular();
		int i = 0;
		if (Web.isWebElementsDisplayed(planNumberInAccountBalanceSection, true)) {
			for (WebElement element : planNumberInAccountBalanceSection) {
				if (element.getText().trim().equals(planNo)) {
					i++;
					balanceXpath = "//*[@id='overviewtable_data']/tr[" + i
							+ "]/td[3]/span";
					viewButtonXpath = "//*[@id='overviewtable_data']/tr[" + i
							+ "]//button";

					if (Web.nextGenDriver.findElement(By.xpath(balanceXpath))
							.getText().trim().equals("N/A"))
						return true;
				}
			}
		}
		return false;
	}
	public boolean verifyViewButtonSuppressed(){
		try{
			Web.nextGenDriver.findElement(By.xpath(viewButtonXpath));
			return false;
		}catch(Exception e){
			return true;
		}
	}
	
	public boolean verifyEmploymentInformationLabel(List<String> label,
			boolean displaysOrNot) {
		if (label.size() <= 0)
			return false;
		for (String labelName : label) {
			if (displaysOrNot) {
				if (!this.verifyEmploymentInfoLabel(labelName))
					return false;
			} else {
				if (this.verifyEmploymentInfoLabel(labelName))
					return false;
			}

		}
		return true;

	}
	
	public boolean verifyEmploymentInfoLabel(String labelName) {	
			System.out.println(labelName);
			switch (labelName.trim()) {
			case "Union":
				if (Web.isWebElementDisplayed(empInformationUnionLabel, false))
					return true;
				break;
			case "Insider":
				if (Web.isWebElementDisplayed(empInformationInsiderLabel, false))
					return true;
				break;
			case "Super Officer":
				if (Web.isWebElementDisplayed(empInformationSuperOfficerLabel,
						false))
					return true;
				break;
			case "FT/PT Employee":
				if (Web.isWebElementDisplayed(empInformationFTPTemployeeLabel,
						false))
					return true;
				break;
			case "Job description":
				if (Web.isWebElementDisplayed(
						empInformationJobDescriptionLabel, false))
					return true;
				break;
			case "Employment type":
				if (Web.isWebElementDisplayed(
						empInformationEmploymentTypeLabel, false))
					return true;
				break;
			case "Overseas employee":
				if (Web.isWebElementDisplayed(
						empInformationOverseasEmployeeLabel, false))
					return true;
				break;
			case "Overseas date":
				if (Web.isWebElementDisplayed(empInformationOverseasDateLabel,
						false))
					return true;
				break;
			case "Employer classification code":
				if (Web.isWebElementDisplayed(
						empInformationEmployerClassificationCodeLabel, false))
					return true;
				break;
			default: {
				Reporter.logEvent(Status.INFO, "label name don't match- "
						+ labelName, "label name don't match- " + labelName,
						true);
				return false;
				}

			}
		return false;
	}
	
	
	public boolean verifyUpdateEmploymentInformationLabel(List<String> label,
			boolean displaysOrNot) {
		if (label.size() <= 0)
			return false;
		for (String labelName : label) {
			if (displaysOrNot) {
				if (!this.verifyUpdateEmploymentInfoLabel(labelName))
					return false;
			} else {
				if (this.verifyUpdateEmploymentInfoLabel(labelName))
					return false;
			}

		}
		return true;

	}
	
	public boolean verifyUpdateEmploymentInfoLabel(String labelName) {	
		System.out.println(labelName);
		switch (labelName.trim()) {
		case "Union":
			if (Web.isWebElementDisplayed(updateEmpInformationUnionLabel, false))
				return true;
			break;
		case "Insider":
			if (Web.isWebElementDisplayed(updateEmpInformationInsiderLabel, false))
				return true;
			break;
		case "Super Officer":
			if (Web.isWebElementDisplayed(updateEmpInformationSuperOfficerLabel,
					false))
				return true;
			break;
		case "FT/PT Employee":
			if (Web.isWebElementDisplayed(updateEmpInformationFTPTemployeeLabel,
					false))
				return true;
			break;
		case "Job description":
			if (Web.isWebElementDisplayed(
					updateEmpInformationJobDescriptionLabel, false))
				return true;
			break;
		case "Employment type":
			if (Web.isWebElementDisplayed(
					updateEmpInformationEmploymentTypeLabel, false))
				return true;
			break;
		case "Overseas employee":
			if (Web.isWebElementDisplayed(
					updateEmpInformationOverseasEmployeeLabel, false))
				return true;
			break;
		case "Overseas date":
			if (Web.isWebElementDisplayed(updateEmpInformationOverseasDateLabel,
					false))
				return true;
			break;
		case "Employer classification code":
			if (Web.isWebElementDisplayed(
					updateEmpInformationEmployerClassificationCodeLabel, false))
				return true;
			break;
		default: {
			Reporter.logEvent(Status.INFO, "label name don't match- "
					+ labelName, "label name don't match- " + labelName,
					true);
			return false;
			}

		}
	return false;
}

	public void retrivePlanFromDBAndSerach(String user,String plan, String db) {
		try
		{
			Web.getDriver().switchTo().defaultContent();
			String planNumber = null;
			queryResultSet = DB.executeQuery(db, plan);
			while (queryResultSet.next()) {
				planNumber = queryResultSet.getString("GA_ID");
			}
			
			new HomePage().switchPlan(planNumber);
			
		}
		catch(Exception e)
		{
			Reporter.logEvent(Status.FAIL, " Exception Occured While Retriving Plan from DB", "" + e.getMessage(),true);
		}
		
	}

	public void verifyMenuItems(String menuitems) {
		try
		{
			String []Menu=menuitems.split(",");
			
			if(Menu[0].equalsIgnoreCase("Employees"))
			{
				
				Web.waitForElement(employeesTab);
				/*Web.actionsClickOnElement(employeesTab);
				Web.actionsClickOnElement(searchEmployee);
				Web.waitForElement(employeeFrame);
				Web.FrameSwitchONandOFF(true, employeeFrame);
				if (Web.isWebElementDisplayed(employeeSearchButton, true)) {
					Reporter.logEvent(Status.INFO, "User is on employee search page",
							"User is on employee search page", true);
				}*/
				Web.actionsClickOnElement(employeesTab);
				if(Web.actionsClickOnElement(employeesTab))
				{
					Reporter.logEvent(Status.PASS, "Employees Tab Should Display", "Employees Tab is Displayed" ,true);
					Web.actionsClickOnElement(employeesTab);
					if(Web.isWebElementDisplayed(searchEmployee, true))
					{
						Reporter.logEvent(Status.PASS, "Search Employees Tab Should Display", "Search Employees Tab is Displayed" ,true);
					}
					else
					{
						Reporter.logEvent(Status.FAIL, "Search Employees Tab Should Display", "Search Employees Tab is Displayed" ,true);
					}
					
					if(Web.isWebElementDisplayed(addEmployee, true))
					{
						Reporter.logEvent(Status.PASS, "Add Employees Tab Should Display", "Add Employees Tab is Displayed" ,true);
					}
					else
					{
						Reporter.logEvent(Status.FAIL, "Add Employees Tab Should Display", "Add Employees Tab is Displayed" ,true);
					}
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Employees Tab Should Display", "Employees Tab is Displayed" ,true);
				}
				//Web.clickOnElement(employeesTab);
			
				
				
			}
			
			
			
			
			System.out.println(Menu + "  "+menuitems);
			
		}
		catch(Exception e)
		{
			Reporter.logEvent(Status.FAIL, " Exception Occured While Retriving Plan from DB", "" + e.getMessage(),true);
		}
		
		
	}
	
	

}
