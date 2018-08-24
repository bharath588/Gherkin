/**
 * 
 */
package pscBDD.PlanExpress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





























import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pscBDD.commonLib.CommonLib;
import pscBDD.login.LoginPage;
import pscBDD.partnerlinkHomePage.PartnerlinkHomePage;
import bdd_core.framework.Globals;
import bdd_lib.DB;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;


/**
 * @author rvpndy
 *
 */
public class PlanExpressPage extends LoadableComponent<PlanExpressPage> {

	@FindBy(linkText = "1")
	private WebElement planPipeLineData;
	@FindBy(linkText = "2")
	private WebElement completePlanData;
	@FindBy(id = "framea")
	private WebElement planExpressFrame;
	@FindBy(xpath = "html/body/div[1]/div[1]")
	private WebElement welcomeToPlanExpress;
	@FindBy(xpath = "//span[@class='currentPageName']")
	private WebElement currentPageName;
	@FindBy(name = "provCompanyWithDb")
	private WebElement partnerDropdown;
	@FindBy(name = "planName")
	private WebElement planNameField;
	@FindBy(name = "internalComplianceInd")
	private WebElement internalComplianceDropdown;
	@FindBy(name = "rolloverInd")
	private WebElement transferPlanDropdown;
	@FindBy(xpath = "//input[@value='Save & Continue']")
	private WebElement saveAndContinueButton;
	@FindBy(xpath="//input[@value='Save & Return']")
	private WebElement saveAndReturnButton;
	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement cancelButton;
	@FindBy(className = "error")
	private List<WebElement> saveError;
	@FindBy(name = "clientName")
	private WebElement clientName;
	@FindBy(name = "firstLineMailing")
	private WebElement addressLn1;
	@FindBy(name = "scndLineMailing")
	private WebElement addressLn2;
	@FindBy(name = "city")
	private WebElement city;
	@FindBy(name = "stateCode")
	private WebElement stateDropdown;
	@FindBy(name = "zipCode")
	private WebElement zipCodeField;
	@FindBy(name = "country")
	private WebElement countryDropdown;
	@FindBy(name = "gaId")
	private WebElement planNumberField;
	@FindBy(xpath = "//input[@value='Implementation Checklist']")
	private WebElement implementationCheckListBreadCrumb;
	@FindBy(xpath="//tr[contains(@id,'fullanswer')]/td[1]")
	private WebElement nextGenEmpExpQuestion;
	@FindBy(xpath = "html/body/div[1]/div[3]/span[5]")
	private WebElement implementationCheckList;
	@FindBy(xpath=".//input[@type='radio' and @value='Y']")
	private WebElement nextGenEmpExpAnsY;
	@FindBy(xpath = "//table/tbody/tr//td[2]")
	private List<WebElement> planNumbers;
	@FindBy(linkText = "3600: Enrollment Kits")
	private WebElement question3600;
	@FindBy(linkText = "Create Forms and Update Recordkeeping System")
	private WebElement createFormsLink;
	@FindBy(xpath = "//div[@class='message']")
	private WebElement planLoadMessage;
	@FindBy(xpath = ".//*[@id='answer(30-0)']")
	private WebElement beneficiaryFormY;
	@FindBy(xpath = "//tbody/tr[1]/td[1]/a")
	private WebElement linkPlanName;
	@FindBy(xpath = "//a[contains(text(),'6200')]//ancestor::table/following-sibling::div")
	private List<WebElement> lnkTabsInPlanExpress;
	@FindBy(xpath = "//a[contains(text(),'Frozen Money Types')]")
	private WebElement lnkFrozenMoney;	
	@FindBy(xpath = "//td[@class='promptLeft' and contains(text(),'Additional Frozen')]")
	private WebElement lblAdditionalFrozen;	
	@FindBy(xpath = "//td[@class='textEntry']//input[@value='N']")
	private List<WebElement> optnNo;	
	@FindBy(xpath = "//td[@class='promptLeft' and contains(text(),'Does the Plan')]")
	private WebElement earlyRetirement;
	@FindBy(xpath = "//td[contains(text(),'computation method')]/..//select")
	private WebElement computationMethod;
	@FindBy(xpath = "//td[contains(text(),'computation period')]/..//select")
	private WebElement computationPeriod;
	@FindBy(xpath = "//input[@id='answer(30)']")
	private WebElement hrsRequired;
	@FindBy(xpath = "//td[@class='textEntry']//input[@value='Y']")
	private List<WebElement> optnYes;
	@FindBy(xpath = "//select[@id='answer(20)']")
	private WebElement MoneyType;
	@FindBy(xpath = "//input[@id='answer(40)']")
	private WebElement MoneyTypeDesc;
	@FindBy(xpath = "//select[@id='answer(60)']")
	private WebElement vestingSchedule;	
	@FindBy(xpath = "//textarea[@id='answer(10)']")
	private WebElement txtfrozenMoney;	
	@FindBy(xpath = "//input[@id='answer(10)']")
	private WebElement retirementAge;
	@FindBy(xpath = "//select")
	private List<WebElement> dropDowns;	
	@FindBy(xpath = "//td[contains(text(),'hours')]/..//input")
	private WebElement txtHours;
	@FindBy(xpath = "//td[contains(text(),'contributions')]/..//input")
	private WebElement txtContributions;	
	@FindBy(xpath = "//input[@name='beginCreateDate']")
	private WebElement txtSearchBegindate;
	@FindBy(xpath = "//input[@name='gaId']")
	private WebElement txtPlanNumber;
	@FindBy(xpath = "//input[@value='Refresh Available Plan List']")
	private WebElement btnRefreshPlan;
	@FindBy(xpath = "//td[contains(text(),'American Funds')]")
	private WebElement txtTPAFunds;
	@FindBy(xpath = "//input[@value='Year to date hours']")
	private WebElement radioBtnYrToDate;	
	@FindBy(xpath = "//td[contains(text(),'Distribution')]/..//select")
	private WebElement drpdwnDistribution;	
	@FindBy(xpath = "//td[contains(text(),'TPA loan')]/..//select")
	private WebElement drpdwnTPALoan;	
	@FindBy(xpath = "//td[contains(text(),'annual loan')]/..//select")
	private WebElement drpdwnAnnualLoan;
	@FindBy(xpath = "//span[contains(@class,'currentPageName')]")
	private WebElement lblHeader;
	
	static ResultSet resultSet;
	static int i = 0;
	
	public PlanExpressPage() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		new PartnerlinkHomePage(new LoginPage(), false, new String[] {
				Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password") }).get();
		try {
			if (new PartnerlinkHomePage().isPlanExpressPage()) {
				Reporter.logEvent(Status.INFO, "Plan Express page is loaded",
						"Plan Express page is loaded", true);
			} else {
				Reporter.logEvent(Status.INFO,
						"Plan Express page is not loaded",
						"Plan Express page is not loaded", true);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web
				.isWebElementDisplayed(welcomeToPlanExpress, false));
	}

	public void clickOnPlanPipelineData() {
		Web.waitForElement(planExpressFrame);
		CommonLib.FrameSwitchONandOFF(true, planExpressFrame);
		if (Web.isWebElementDisplayed(planPipeLineData, true)) {
			Web.actionsClickOnElement(planPipeLineData);
		}
	}

	public boolean isAddPlanPage() {
		if (Web.isWebElementDisplayed(currentPageName, true)
				&& Web.VerifyText(currentPageName.getText(), "Add Plan Page 1",
						false)) {
			return true;
		}
		return false;
	}

	public void selectPartner(String value) {
		Web.selectDropDownOption(partnerDropdown, value);
	}

	public void enterPlanName(String planName) {
		Web.setTextToTextBox(planNameField, planName);
	}

	public void selectTransferPlan(String transferValue) {
		Web.selectDropDownOption(transferPlanDropdown, transferValue);
	}

	public void selectInternalCompliance(String internalCompliance) {
		Web.selectDropDownOption(internalComplianceDropdown, internalCompliance);
	}

	public void clickSaveAndContinueOnAddPlanPage1() {
		Web.clickOnElement(saveAndContinueButton);
		if (saveError.size() > 0) {
			Reporter.logEvent(Status.INFO, "Error occured during save",
					saveError.get(0).getText(), true);
		} else if (currentPageName.getText().contains("Page 2")) {
			Reporter.logEvent(Status.PASS, "Add plan page 1 save successfull",
					"Add plan page 1 save successfull", true);
		}
	}

	public boolean isAddPlanPage2() {
		if (currentPageName.getText().contains("Page 2")) {
			Reporter.logEvent(Status.PASS, "Add plan page 1 save successfull",
					"Add plan page 1 save successfull", true);
			return true;
		}
		return false;
	}

	public void fillAddressOnAddPlanPage2(String companyName,
			String addressLn1, String addressLn2, String cityName,
			String stateName, String zipCode, String countryName) {
		Web.waitForElement(stateDropdown);
		Web.setTextToTextBox(clientName, companyName);
		Web.waitForElement(stateDropdown);
		Web.setTextToTextBox(this.addressLn1, addressLn1);
		Web.waitForElement(stateDropdown);
		Web.setTextToTextBox(this.addressLn2, addressLn2);
		Web.waitForElement(stateDropdown);
		Web.setTextToTextBox(this.city, cityName);
		Web.waitForElement(stateDropdown);
		Web.selectDropDownOption(stateDropdown, stateName);
		Web.setTextToTextBox(zipCodeField, zipCode);
		Web.selectDropDownOption(countryDropdown, countryName);

	}

	public void fillPlanNumber(String query) throws InterruptedException {
		ArrayList<String> planList = new ArrayList<String>();
		resultSet = DB.executeQuery("D_ISIS", query);
		String rvLow, rvHigh;
		try {
			while (resultSet.next()) {
				rvLow = resultSet.getString("RV_LOW_VALUE");
				rvHigh = resultSet.getString("RV_HIGH_VALUE");
				i = 0;
				planList = getPlanNumber(rvLow, rvHigh);

				if(fillCorrectPlanNumber(planList))
					break;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> getPlanNumber(String rv_low, String rv_high) {
		ArrayList<String> planList = new ArrayList<String>();
		Long rvLowValue = Long.valueOf(rv_low);
		Long rvHighValue = Long.valueOf(rv_high);

		while (rvLowValue <= rvHighValue) {
			planList.add(String.valueOf(rvLowValue) + "-01");
			rvLowValue++;
		}
		Collections.sort(planList);
		return planList;
	}

	private boolean fillCorrectPlanNumber(List<String> gaId)
			throws InterruptedException {
		Web.waitForElement(planNumberField);
		String planNumber = Web.setTextToTextBox(planNumberField, gaId.get(i));
		if(planNumber.length()>0){
		Web.waitForElement(saveAndContinueButton);
		Thread.sleep(1000);
		Web.clickOnElement(saveAndContinueButton);}
		Web.waitForElements(saveError);
		if (saveError.size() > 0
				&& Web.isWebElementsDisplayed(saveError)
				&& !Web.isWebElementDisplayed(nextGenEmpExpQuestion, false)) {
			if (gaId.size() > 1) {
				i++;
				Thread.sleep(1000);
				fillCorrectPlanNumber(gaId);
			}
		} else {
			
			Reporter.logEvent(Status.PASS,
					"Data save successfull on Add plan page 2",
					"Data save successfull on Add plan page 2 " + gaId.get(i),
					true);
			isNextGenExperience();
			return true;
		}
		return false;
	}
	
	public boolean isImplementationCheckList(){
		if(Web.isWebElementDisplayed(implementationCheckList, true))
			return true;
		return false;
	}
	
	public void isNextGenExperience(){
		if(Web.isWebElementDisplayed(nextGenEmpExpQuestion, false)){
			Web.actionsClickOnElement(nextGenEmpExpAnsY);
		}
	}
	
	public void clickOnSaveAndReturn(){
		if(Web.isWebElementDisplayed(saveAndReturnButton, false)){
			Web.clickOnElement(saveAndReturnButton);
		}
	}
	
	public void clickOnCompletePlanData() {
		//Web.waitForElement(planExpressFrame);
		Web.FrameSwitchONandOFF(true, planExpressFrame);
		if (Web.isWebElementDisplayed(completePlanData, true)) {
			Web.actionsClickOnElement(completePlanData);
		}
	}
	
	public void clickOnPlanLink(String ga_id) {
		if (planNumbers.size() > 0) {
			for (WebElement ele : planNumbers) {
				if (ele.getText().trim().equalsIgnoreCase(ga_id))
					Web.clickOnElement(ele.findElement(By
							.xpath("/preceding-sibling::td/a")));
				break;
			}
		}
	}
	public void clickOn3600EnrollmentKits() {
		if (Web.isWebElementDisplayed(question3600, true))
			Web.clickOnElement(question3600);
	}

	public void selectYBeneficiaryForm() {
		if (Web.isWebElementDisplayed(beneficiaryFormY, true))
			Web.actionsClickOnElement(beneficiaryFormY);
	}

	public void clickOnCreateFormsAndUpdate() {
		if (Web.isWebElementDisplayed(createFormsLink, true))
			Web.clickOnElement(createFormsLink);
	}

	public boolean isSuccessMessage() {
		try {
			Thread.sleep(3000);
			if (Web.isWebElementDisplayed(planLoadMessage, true))
				return true;			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void verifyDBValues(String query, String ga_id) {
		try {
			resultSet = DB.executeQuery("D_INST", query, ga_id);
			resultSet.last();
			int row = resultSet.getRow();
			if (row >= 1)
				Reporter.logEvent(Status.PASS,
						"PXIS has run and plan data is created",
						"PXIS has run and plan data is created", false);
			else
				Reporter.logEvent(Status.PASS,
						"PXIS has run and plan data is created",
						"PXIS has run and plan data is not created", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public boolean validateTabsInPlanExpress(String expectedTab){
		String linktab;
		Web.scrollDown();
		Web.FrameSwitchONandOFF(true, planExpressFrame);
		for (int i=0;i<3;i++){
			linktab=lnkTabsInPlanExpress.get(i).getText().trim();
			if (expectedTab.equalsIgnoreCase(linktab))
				return true;					
	}
		return false;
	}
	
	public void clickOnlegalPlanLink(String ga_id) {
		if (planNumbers.size() > 0) {
			Web.clickOnElement(Web.getDriver().findElement(By.xpath("//td[text()='"+ga_id+"']/../td[1]/a")));
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Plan number not found in the List",
					"Plan number not found in the List", true);
		}
	}
	
	public void clickOnFrozenMoneytype(){
		Web.scrollDown();
		Web.FrameSwitchONandOFF(true, planExpressFrame);
		if (Web.isWebElementDisplayed(lnkFrozenMoney, true))
			Web.actionsClickOnElement(lnkFrozenMoney);		
	}
	
	public void addFrozenMoney(){
		try {
			if (lblHeader.getText().trim().contains("5400")){			
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(lblAdditionalFrozen, true))
				Web.actionsClickOnElement(optnYes.get(0));
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5410FrozenMoney(String moneyType, String descr) {
		try {
			if (lblHeader.getText().trim().contains("5410")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnYes.get(0), true))
				Web.actionsClickOnElement(optnYes.get(0));
			Web.selectDropDownOption(MoneyType, moneyType);
			Web.setTextToTextBox(MoneyTypeDesc, descr);
			Web.actionsClickOnElement(optnYes.get(1));
			Select vesting= new Select(vestingSchedule);
			vesting.selectByIndex(2);
			Web.actionsClickOnElement(optnYes.get(2));
			Web.actionsClickOnElement(optnYes.get(3));
			Web.actionsClickOnElement(optnYes.get(4));
			if (Web.isWebElementDisplayed(optnYes.get(5)))
				Web.actionsClickOnElement(optnYes.get(5));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5420FrozenMoney(String moneyType,String typeDescr) {
		try {
			if (lblHeader.getText().trim().contains("5420")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnYes.get(0), true))
			Web.actionsClickOnElement(optnYes.get(0));
			Web.selectDropDownOption(MoneyType, moneyType, true);
			Web.setTextToTextBox(MoneyTypeDesc, typeDescr);
			Web.actionsClickOnElement(optnYes.get(1));
			Select schedule= new Select(vestingSchedule);
			schedule.selectByIndex(13);
			Web.actionsClickOnElement(optnYes.get(2));
			Web.actionsClickOnElement(optnYes.get(3));
			Web.actionsClickOnElement(optnYes.get(4));
			if (Web.isWebElementDisplayed(optnYes.get(5)))
				Web.actionsClickOnElement(optnYes.get(5));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
			Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5430FrozenMoney(String moneyType) {
		try {
			if (lblHeader.getText().trim().contains("5430")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnYes.get(0), true))
				Web.actionsClickOnElement(optnYes.get(0));
			Web.selectDropDownOption(MoneyType, moneyType, true);
			Web.setTextToTextBox(MoneyTypeDesc, "safe harcor match 1");
			Web.actionsClickOnElement(optnYes.get(1));
			Select vesting= new Select(vestingSchedule);
			vesting.selectByIndex(4);
			Web.actionsClickOnElement(optnYes.get(2));
			Web.actionsClickOnElement(optnYes.get(3));
			Web.actionsClickOnElement(optnYes.get(4));
			if (Web.isWebElementDisplayed(optnYes.get(5)))
				Web.actionsClickOnElement(optnYes.get(5));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5440FrozenMoney() {
		try {
			if (lblHeader.getText().trim().contains("5440")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);		
			if (Web.isWebElementDisplayed(txtfrozenMoney, true))
			Web.setTextToTextBox(txtfrozenMoney, "employer purchase test");
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5500FrozenMoney() {
		try {
			if (lblHeader.getText().trim().contains("5500")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			Web.setTextToTextBox(retirementAge, "65");
			Web.selectDropDownOption(MoneyType, "2", true);
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}			
	}
	
	public void add5600FrozenMoney() {
		try {
			if (lblHeader.getText().trim().contains("5600")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(earlyRetirement, true))
				Web.actionsClickOnElement(optnNo.get(0));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	
	public void add5620FrozenMoney() {
		try {
			if (lblHeader.getText().trim().contains("5620")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnYes.get(0), true))
				Web.actionsClickOnElement(optnYes.get(0));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void add5640Vesting() {
		try {
			if (lblHeader.getText().trim().contains("5640")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(computationMethod, true))
				Web.selectDropDownOption(computationMethod, "Actual Hours",
						true);
			Thread.sleep(1000);
			Web.selectDropDownOption(computationPeriod, "Anniversary Date",
					true);
			Web.setTextToTextBox(hrsRequired, "2");
			Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5645Vesting(){
		try {
			if (lblHeader.getText().trim().contains("5645")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(txtTPAFunds, true))
			Web.clickOnElement(radioBtnYrToDate);
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5650Vesting() {
		try {
			if (lblHeader.getText().trim().contains("5650")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnNo.get(0), true))
				Web.actionsClickOnElement(optnNo.get(0));
			Web.actionsClickOnElement(optnNo.get(1));
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void add5660Eligibility() {
		try {
			if (lblHeader.getText().trim().contains("5660")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			Web.selectDropDownOption(dropDowns.get(0), "Actual Hours", true);
			Web.setTextToTextBox(txtHours, "1000");
			Web.selectDropDownOption(dropDowns.get(1), "Annual", true);
			Web.selectDropDownOption(dropDowns.get(2), "Next Following", true);
			Web.selectDropDownOption(dropDowns.get(4), "Anniversary Date", true);
			Web.setTextToTextBox(txtContributions, "21");
			Web.selectDropDownOption(dropDowns.get(5), "Day", true);
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5670Eligibility() {
		try {
			if (lblHeader.getText().trim().contains("5670")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			Web.selectDropDownOption(dropDowns.get(0), "1", true);
			Web.actionsClickOnElement(optnNo.get(0));
			Thread.sleep(1000);
			Web.actionsClickOnElement(optnNo.get(1));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void add5685Safeharbor() {
		try {
			if (lblHeader.getText().trim().contains("5685") || (lblHeader.getText().trim().contains("5693")) || (lblHeader.getText().trim().contains("5697"))) {
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnNo.get(0), true))
				Web.actionsClickOnElement(optnNo.get(0));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.actionsClickOnElement(saveAndContinueButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void add5800Misc() {
		try {
			if (lblHeader.getText().trim().contains("5800")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnNo.get(0), true))
				Web.actionsClickOnElement(optnNo.get(0));
			Web.actionsClickOnElement(optnNo.get(1));
			Thread.sleep(1000);
			Web.actionsClickOnElement(optnNo.get(2));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void add5900Distribution() {
		try {
			if (lblHeader.getText().trim().contains("5900")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnNo.get(0), true))
				Web.actionsClickOnElement(optnNo.get(0));
			Web.actionsClickOnElement(optnNo.get(1));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void add6000Loans() {
		try {
			if (lblHeader.getText().trim().contains("6000")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnNo.get(0), true))
				Web.actionsClickOnElement(optnNo.get(0));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
			Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void add6100FeeRecovery() {
		try {
			if (lblHeader.getText().trim().contains("6100")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(drpdwnDistribution, true))
				Web.selectDropDownOption(drpdwnDistribution, "$30", true);
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.clickOnElement(saveAndContinueButton);
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void add6200Loans() {
		try {
			if (lblHeader.getText().trim().contains("6200")){
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			if (Web.isWebElementDisplayed(optnYes.get(0), true))
				Web.actionsClickOnElement(optnYes.get(0));
			if (Web.isWebElementDisplayed(saveAndContinueButton, true))
				Web.clickOnElement(saveAndContinueButton);
				Thread.sleep(3000);
			if (Web.isWebElementDisplayed(saveAndReturnButton, true))
				Web.clickOnElement(saveAndReturnButton);
			Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public boolean getFrozenMoneyCreated(String query, String ga_id,String classification, String descr,String... db_name)
	{
		String db_Name="D_INST";
		if(db_name.length>0)
			db_Name=db_name[0];
		try {
			ResultSet resultSet = DB
					.executeQuery(db_Name, query, ga_id,classification,descr);
			resultSet.last();
			int row = resultSet.getRow();
			if (row == 1)
				Reporter.logEvent(Status.PASS,
						"Frozen Money type has been created in database",
						"Frozen Money type has been created in database", false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}	
			Reporter.logEvent(Status.FAIL,
					"Frozen Money type has not been created in database",
					"Frozen Money type has not been created in database", true);
			return false;
	}
	
	public void searchPlanNo(String ga_ID){
		try {
			Web.FrameSwitchONandOFF(true, planExpressFrame);
			Web.setTextToTextBox(txtSearchBegindate, "02/20/2017");
			Thread.sleep(1000);
			Web.setTextToTextBox(txtPlanNumber, ga_ID);
			Web.clickOnElement(btnRefreshPlan);
			
			Thread.sleep(1000);
			
			Web.waitForPageToLoad(Web.getDriver());
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
	}
	
	

}


