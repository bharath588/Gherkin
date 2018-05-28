/**
 * 
 */
package org.bdd.psc.pageobjects;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.gargoylesoftware.htmlunit.javascript.host.media.webkitAudioContext;

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
	
	
	
	
	@FindBy(how=How.ID,using="payCheckAutoSuiteEditLink")
	private WebElement paycheckContributionEditLink;
	@FindBy(how=How.ID,using="framecA")
	private WebElement editDeferralFrame;
	
	@FindBy(how=How.XPATH,using= ".//*[text()='Current and Pending Deferrals']")
	private WebElement deferralText;
	@FindBy(how=How.XPATH,using= ".//*[*[span[text()='Ongoing']]]//a")
	private WebElement ongoingEditLink;
	@FindBy(how=How.ID,using="aedrvalue1")
	private WebElement enterDefElectionInDeflInformForBeforeTax;
	@FindBy(how=How.NAME,using="customDate1")
	private WebElement enterDateInDeflInformForBeforeTax;
	@FindBy(how=How.XPATH,using=".//div[*[*[@id='aedrvalue1']]]//button[@id='two']")
	private WebElement dollarButtonInDeflInformForBeforeTax;
	
	@FindBy(how=How.ID,using="aedrvalue0")
	private WebElement enterDefElectionContributionBeforeTax;
	@FindBy(how=How.ID,using="aedrvalue1")
	private WebElement enterDefElectionContributionRoth;
	
	@FindBy(how=How.ID,using="aedrvalue2")
	private WebElement enterDefElectionInDeflInformForRoth;
	@FindBy(how=How.NAME,using="customDate2")
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
			if(Web.isWebElementDisplayed(searchResultsTablerow,true)){
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
	public void enterFutureDateContributionAndSave(String type,String...deferralDate){
		if(type.trim().equalsIgnoreCase("Before Tax")){
			Web.setTextToTextBox(enterDefElectionInDeflInformForBeforeTax, "50");
			Web.clickOnElement(dollarButtonInDeflInformForBeforeTax);
			if(deferralDate.length>0)
				Web.setTextToTextBox(enterDateInDeflInformForBeforeTax, this.getDateFormat(deferralDate[0]));
		}
		if(type.trim().equalsIgnoreCase("Roth")){
			Web.setTextToTextBox(enterDefElectionInDeflInformForRoth, "50");
			Web.clickOnElement(dollarButtonInDeflInformForRoth);
			if(deferralDate.length>0)
				Web.setTextToTextBox(enterDateInDeflInformForRoth, this.getDateFormat(deferralDate[0]));
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
				System.out.print(dateCount);
			}
			int COUNT = Integer.parseInt(dateCount);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, COUNT); // Adding 5 days
			String output = sdf.format(c.getTime());
			System.out.println(output);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean isEffectiveDateSameAsFutureDate(String deferralType,String...futureDate){
		Boolean flag=false;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String enteredDate=sdf.format(Calendar.getInstance().getTime());
		
		if(futureDate.length>0)
			enteredDate=this.getDateFormat(futureDate[0]);
		
		if(deferralType.trim().equalsIgnoreCase("Before Tax")){
			for(WebElement dateElement:effectiveDateForBeforeTax){
				if(enteredDate.equals(dateElement.getText().trim()))
					return true;			
			}
		}
		if(deferralType.trim().equalsIgnoreCase("Roth")){
			for(WebElement dateElement:effectiveDateRoth){
				if(enteredDate.equals(dateElement.getText().trim()))
					return true;			
			}
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
		LocalDate date = LocalDate.of(dateArr[2], dateArr[0],dateArr[1]);//MM/dd/yyyy-->YYYY,MM,DD
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
	
	

}
