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
		Web.waitForElement(planExpressFrame);
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
							.xpath("/preceding-sibling::td")));
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
		if (Web.isWebElementDisplayed(planLoadMessage, true))
			return true;
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

}
