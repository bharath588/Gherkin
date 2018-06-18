/**
 * 
 */
package pscBDD.employee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.apache.commons.lang.RandomStringUtils;
/*import org.apache.commons.lang3.RandomStringUtils;*/
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pscBDD.homePage.HomePage;
import bdd_lib.CommonLib;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;



/**
 * @author rvpndy
 *
 */
public class EmployeePage extends LoadableComponent<EmployeePage> {

	//Locators declaration
	@FindBy(css = "iframe[id = 'framec']")
	private WebElement employeeSearchFrame;
	@FindBy(xpath = ".//*[@id='newMenu']/li[2]/a")
	private WebElement tabEmployees;
	//		@FindBy(id = "profileLink")
	//		private WebElement linkProfile;
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
	@FindBy(xpath = ".//*[@id='gritter-item-1']/div/div[1]")
	private WebElement linkDismissErrorMsgBox;
	@FindBy(xpath=".//tbody[@id='searchResultsTable_data']/tr[1]/td[2]/a")
	private WebElement searchResultsFirstName;
	@FindBy(xpath = ".//tbody[@id='searchResultsTable_data']/tr[1]/td[1]/a")
	private WebElement searchResultsLastName;
	@FindBy(xpath = ".//*[@id='employeeSearchOverviewContainer_content']/div[1]/div[1]/h1/label")
	private WebElement txtOverview;
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
	@FindBy(xpath="//span[contains(text(),'Basic information')]")
	private WebElement basicinfoHeader;
	@FindBy(xpath="//*[@id='basicInfo']//label")
	private List<WebElement> basicInfoLabels;
	@FindBy(xpath="//*[@id='basicInfo']//label/../following-sibling::*//td")
	private List<WebElement> basicInfoValues;
	@FindBy(xpath=".//ul[@class='nav nav-tabs']//a[@id='participantDetail']")
	private WebElement EmployeeDetTAB;
	@FindBy(xpath=".//a[@id='basicInfoEditLink']")
	private WebElement lnkEditEmployee;
	@FindBy(xpath=".//input[@id='lastName']")
	private WebElement txtEmpEditLastName;
	@FindBy(xpath=".//input[@id='firstName']")
	private WebElement txtEmpEditFirstName;
	@FindBy(xpath=".//input[@id='middleName']")
	private WebElement txtEmpEditMidName;
	@FindBy(xpath=".//iframe[@id='basicInfoEditFrameId']")
	private WebElement frmEmployeeEdit;
	@FindBy(xpath=".//input[@value='Save']")
	private WebElement btnEmpEditSave;
	@FindBy(xpath=".//button[@id='paeUrlButton']")
	private WebElement EmployeeWeb;
	@FindBy(xpath=".//div[@id='contactInfo']//table[@class='newBlueBox ui-corner-all']//tr[1]/td[2]//td")
	private WebElement txtNameInfo;
	@FindBy(id="IPB_CONTACT_4")
	private WebElement empInfoEditPageRadioBtn;

	LoadableComponent<?> parent;
	public static WebDriver webDriver;
	WebDriverWait wait;
	Actions act;
	Select select;
	CommonLib commonLib;
	public Map<String,String> basicInfoMapBeforeChanges;
	String qdroPart = null;
	String normalPart = null;
	String transferPart=null;
	String rndString = RandomStringUtils.random(5);//,'a','b','c','d','e','f','g','h'

	public EmployeePage(WebDriver webDriver){
		EmployeePage.webDriver = webDriver;
		commonLib = new CommonLib(webDriver);
		PageFactory.initElements(webDriver, this);
	}


	@Override
	protected void load() {
		// TODO Auto-generated method stub
		wait = new WebDriverWait(webDriver,40);
		HomePage homepage = (HomePage) this.parent;
		new HomePage(webDriver).get();
		wait.until(ExpectedConditions.elementToBeClickable(tabEmployees));
		act = new Actions(webDriver);
		act.moveToElement(tabEmployees).click().build().perform();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(employeeSearchFrame));
		wait.until(ExpectedConditions.visibilityOf(btnGoEmployeeSearch));
		if(Web.isWebElementDisplayed(btnGoEmployeeSearch,true)){
			Reporter.logEvent(Status.INFO, "Employee search page loaded", 
					"Employee search page loaded", true);
		}

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		webDriver.switchTo().defaultContent();
		Assert.assertTrue(commonLib.isWebElementDisplayed(employeeSearchFrame, false));
	}

	public void searchEmployeeBySSN(){
		try{
			webDriver.switchTo().defaultContent();
			webDriver.switchTo().frame(employeeSearchFrame);
			select = new Select(drpdwnSearchEmployee);
			select.selectByVisibleText("SSN");
			Thread.sleep(4000);
			txtSearchbox.sendKeys("135785475");
			if(commonLib.isWebElementDisplayed(btnGoEmployeeSearch, false)){
				btnGoEmployeeSearch.click();
			}
			Thread.sleep(3000);
			dismissErrorBox();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void dismissErrorBox(){
		try{
			if(commonLib.isWebElementDisplayed(errorMsgBox)){
				errorMsgBox.click();
				linkDismissErrorMsgBox.click();
				Thread.sleep(3000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean isSearchResultDisplayed(){
		try{
			webDriver.switchTo().defaultContent();
			webDriver.switchTo().frame(employeeSearchFrame);
			if(commonLib.isWebElementDisplayed(searchResultsTablerow, true)){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}

	public boolean verifyEmployeeRedirectOverviewPage() {
		try{
			webDriver.switchTo().defaultContent();
			webDriver.switchTo().frame(employeeSearchFrame);
			searchResultsFirstName.click();
			commonLib.waitForElement(txtOverview);
			Thread.sleep(7000);
			if(commonLib.isWebElementDisplayed(txtOverview, true)){
				return true;
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return false;
	}

	public void verifyEmployeeBasicEditInfo(){
		List<String> actualModalInfoLabels = new LinkedList<String>();
		List<String> expectedModalInfoLabels = Arrays.asList("Last Name,First Name,Middle Name,Suffix,Birth Date,Marital Status,Gender,Language Preference,Mailing Name 1,Mailing Name 2,Mailing Name 3,Address,City,State,Zip,Country,Home Phone,Work Phone,Extension,Fax,Email".split(","));
		webDriver.switchTo().defaultContent();
		webDriver.switchTo().frame(employeeSearchFrame);
		basicInfoEdit.click();
		commonLib.waitForElement(basicInfoEditFrame);
		webDriver.switchTo().frame(basicInfoEditFrame);
	}

	public void verifyBasicInformationOnOverviewPage() throws Exception
	{
		basicInfoMapBeforeChanges = new HashMap<String,String>();
		webDriver.switchTo().defaultContent();
		webDriver.switchTo().frame(employeeSearchFrame);
		commonLib.waitForElement(basicinfoHeader);
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

		webDriver.switchTo().defaultContent();
	}

	public boolean PerformEmployeeEditandVerify(){
		commonLib.FrameSwitchONandOFF(true,employeeSearchFrame); //switch into frame	
		try{
			if(commonLib.isWebElementDisplayed(EmployeeDetTAB, true)){
				EmployeeDetTAB.click();
			}
			Thread.sleep(5000);
			if(commonLib.isWebElementDisplayed(lnkEditEmployee,true)){
				lnkEditEmployee.click();
				if(commonLib.isWebElementDisplayed(frmEmployeeEdit,true)){	
					commonLib.FrameSwitchONandOFF(true, frmEmployeeEdit);				   
					String rndString = RandomStringUtils.random(5); 									   
					commonLib.setTextToTextBox(txtEmpEditFirstName, "Test"+rndString);
					commonLib.setTextToTextBox(txtEmpEditMidName, "Employee"+rndString);
					commonLib.setTextToTextBox(txtEmpEditLastName, "Update"+rndString);
					Reporter.logEvent(Status.INFO,"Employee edit field verification"
							,"Employee edit field opened successfully",true);
					btnEmpEditSave.click();
					commonLib.waitForElementEnablity(EmployeeWeb);
					//Thread.sleep(5000);

					commonLib.FrameSwitchONandOFF(false, frmEmployeeEdit);
					System.out.println("Switched off frame employee edit");
					commonLib.FrameSwitchONandOFF(true,employeeSearchFrame);
					System.out.println("Switched on frame employee search");
					Thread.sleep(5000);
					if(commonLib.isWebElementDisplayed(txtNameInfo,true) 
							&& txtNameInfo.getText().trim().equals("Test"+rndString+" Employee"+rndString+" Update"+rndString)) {
						Reporter.logEvent(Status.INFO, "Performed employee update and verified successfully", 
								"Performed employee update and verified successfully", false);
						commonLib.FrameSwitchONandOFF(false,employeeSearchFrame);
						return true;
					}
				}
			}
		}catch(Exception e){
			Reporter.logEvent(Status.FAIL,"Failed to edit Employee details",
					"Employee edit failed, Error : "+e.getMessage(),true);
			Reporter.logEvent(Status.INFO,"Failed to edit employees","Failed to edit employees",false);							
		}
		Reporter.logEvent(Status.INFO,"Employee update and verification failed","Employee update and verification failed",false);
		commonLib.FrameSwitchONandOFF(false,employeeSearchFrame);
		return false;
	}

	public void clickOnEmployeeDetailTab(){
		commonLib.FrameSwitchONandOFF(true,employeeSearchFrame); //switch into frame	
		try{
			if(commonLib.isWebElementDisplayed(EmployeeDetTAB, true)){
				EmployeeDetTAB.click();
			}
			Thread.sleep(5000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void clickOnEditLink(){
		if(commonLib.isWebElementDisplayed(lnkEditEmployee,true)){
			lnkEditEmployee.click();
		}
	}

	public void updateFirstNameInInfo(){
		commonLib.setTextToTextBox(txtEmpEditFirstName, "Test"+rndString);
	}

	public void updateLastNameInInfo(){
		if(commonLib.isWebElementDisplayed(frmEmployeeEdit,true)){	
			commonLib.FrameSwitchONandOFF(true, frmEmployeeEdit);
			commonLib.setTextToTextBox(txtEmpEditLastName, "Update"+rndString);
		}
	}

	public void updateMidNameInInfo(){
		commonLib.setTextToTextBox(txtEmpEditMidName, "Employee"+rndString);
	}

	public void saveUpdatedInfo(){
		if(Web.isWebElementDisplayed(empInfoEditPageRadioBtn, false))
			empInfoEditPageRadioBtn.click();
		btnEmpEditSave.click();
		commonLib.waitForElementEnablity(EmployeeWeb);
	}

	public boolean verifyChangesInInfo(){
		try{
		commonLib.FrameSwitchONandOFF(false, frmEmployeeEdit);
		System.out.println("Switched off frame employee edit");
		commonLib.FrameSwitchONandOFF(true,employeeSearchFrame);
		System.out.println("Switched on frame employee search");
		Thread.sleep(5000);
		if(commonLib.isWebElementDisplayed(txtNameInfo,true) 
				&& txtNameInfo.getText().trim().equals("Test"+rndString+" Employee"+rndString+" Update"+rndString)) {
			Reporter.logEvent(Status.INFO, "Performed employee update and verified successfully", 
					"Performed employee update and verified successfully", false);
			commonLib.FrameSwitchONandOFF(false,employeeSearchFrame);
			return true;
		}
	}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}

}
