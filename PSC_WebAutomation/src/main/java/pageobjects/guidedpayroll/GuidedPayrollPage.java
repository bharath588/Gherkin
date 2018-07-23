package pageobjects.guidedpayroll;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.DB;
import lib.Reporter;

//import lib.Reporter.Status;
import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;
import framework.util.CommonLib;

public class GuidedPayrollPage extends LoadableComponent<GuidedPayrollPage> {

	// @FindBy(xpath = "//ul[contains(@id ,'newMenu')]/li[4]/a") //locator
	// changed for process center tab
	// @FindBy(xpath = "//ul[contains(@id ,'newMenu')]/li[3]/a")//locator
	// changed for process center tab
	@FindBy(xpath = ".//*[@id='newMenu']/li[4]/a")
	private WebElement tabProcessCenter;
	@FindBy(xpath = ".//*[@id='newMenu']/li[4]/ul/li[1]/a")
	// @FindBy(xpath=".//*[@id='newMenu']/li[3]/ul/li[1]/a") // Locator changed
	// for Overview link in process center tab
	private WebElement linkOverview;
	@FindBy(xpath = "//div[contains(@id,'process_center_accordion_acco')]/h3[1]/a")
	private WebElement tabEnterPayroll;
	@FindBy(xpath = ".//*[@id='j_idt51']")
	private WebElement urlClicktoCont;
	@FindBy(css = "iframe[id = 'framek']")
	private WebElement iframeGpp;
	@FindBy(css="iframe[id='keysFrame']")
	private WebElement keyFrame;	
	@FindBy(id = "profileLink")
	private WebElement linkProfile;
	//@FindBy(xpath = ".//*[@id='payrollInfoForm']/table/tbody/tr[5]/td/table/tbody/tr[2]/td[4]/div")
	@FindBy(xpath = "//*[@id='estimatedContribAmt']/following-sibling::div")
	private WebElement errorTextwithoutDiv;
	@FindBy(xpath = ".//*[@id='payrollInfoForm']/table/tbody/tr[5]/td/table/tbody/tr[3]/td[4]/div")
	private WebElement errorTextwithDiv;
	@FindBy(id = "payrollDate")
	private WebElement txtPayrollDate;
	@FindBy(id = "estimatedContribAmt")
	private WebElement txtExpectedContrTotal;
	@FindBy(id = "payrollInfoContinueButton")
	private WebElement btnContinue;
	@FindBy(xpath = ".//*[@id='moneySourcesForm']/table/tbody/tr[7]/td")
	private List<WebElement> chkboxMoneySourceTable;
	@FindBy(id = "moneySourcesContinue")
	private WebElement btnMoneysrcContinue;
	//@FindBy(xpath = ".//*[@id='updateInfoSection']/table[1]/tbody/tr[3]/td")
	@FindBy(xpath = "//*[@id='updateInfoSection']//td[contains(text(),'Expected Contribution')]")
	private WebElement expContrbtnTotal;
	@FindBy(xpath = ".//*[@id='amount1_0']")
	private WebElement txtinputContfirst;
	@FindBy(xpath = ".//*[@id='amount1_1']")
	private WebElement txtinputContSecond;
	@FindBy(id = "updateInfoContinue")
	private WebElement btnSaveandContProcessing;
	@FindBy(id = "entriesPerPage")
	private WebElement drpdwnShowEntries;
	@FindBy(xpath = ".//*[@id='ATK1']")
	private WebElement chkboxFrst;
	@FindBy(xpath = "//.[@id = 'contributionTableId']/tbody/tr")
	private List<List<WebElement>> tableContributionGrid;
	@FindBy(xpath = "//form[@name='CashRemit']/table//tr[2]/td[1]/font")
	private WebElement txtRefNum;
	@FindBy(xpath = "//form[contains(@name,'CashRemit')]/table[6]//tr/td[3]//input")
	private List<WebElement> chkboxVerifyMoneySource;
	@FindBy(xpath = "//form[contains(@name,'CashRemit')]/input[2]")
	private WebElement chkboxtoAcceptWarnings;
	@FindBy(xpath = "//form[@name='CashRemit']/div[9]/table//td/font/input[1]")
	private WebElement btnCompleteTransaction;
	@FindBy(xpath = "//form[@name='CashRemit']/font[3]/font")
	private WebElement txtSuccessfulMsg;
	@FindBy(xpath = "//form[@name='CashRemit']/div/table//td//input")
	private WebElement btnContinueConfirmationPage;
	@FindBy(xpath = "//form[@name='CashRemit']/table[2]//tr[2]/td[2]/font")
	private WebElement payrollDataConfPage;
	@FindBy(xpath = "//form[@name='CashRemit']/table[2]//tr[2]/td[3]/font")
	private WebElement expectedTotalConfPage;
	@FindBy(css = "table[id='moneySourceCheckboxTable'] tbody tr")
	private List<WebElement> moneySource;
	@FindBy(xpath = ".//*[@id='step2PayrollDate']")
	private WebElement moneysrcPayroll;
	@FindBy(xpath = ".//*[@id='step2EstimatedContribAmt']")
	private WebElement moneysrcContribution;
	@FindBy(xpath = ".//*[@id='updateInfoSection']/table[1]/tbody/tr[2]/td")
	private WebElement updtPayroll;
	@FindBy(xpath = ".//*[@id='updateInfoSection']/table[1]/tbody/tr[3]/td")
	private WebElement updtContribution;
	@FindBy(id = "continueAddEmployeeSsnButton")
	private WebElement btnAddEmployeeContinue;
	@FindBy(id = "gpdto_ssn")
	private WebElement txtSSN;
	@FindBy(id = "gpdto_ssn_match")
	private WebElement txtSSNConf;
	@FindBy(id = "addEmployeeGppButtonDiv")
	private WebElement addEmployeeBtn;
	@FindBy(id = "addEmployee")
	private WebElement addEmployeeForm;
	@FindBy(id = "saveAndContinueAddEmployeeButton")
	private WebElement saveAddEmployeeContBtn;
	@FindBy(id = "cancelAddEmployeeSsnButton")
	private WebElement cancelSSNBtn;
	@FindBy(css = "div[id='participantContributionGridSubstAreaId'] table[id='contributionTableFrozen']")
	private WebElement gppContributionFreezedTable;
	@FindBy(css = "div[id='participantContributionGridSubstAreaId'] table[id='contributionTableId']")
	private WebElement gppContributionUnfreezedTable;
	@FindBy(id = "entriesPerPage")
	private WebElement selectNoOfEntries;
	@FindBy(id = "gpdto_totalCompensation")
	private WebElement txtAddEmpTotalComp;
	@FindBy(xpath = ".//*[@id='editEmployeeSsnTd']/div")
	private WebElement ssnFormatErr;
	@FindBy(xpath = ".//*[@id='editEmployeePersonalDetailDiv']/table/tbody/tr[2]/td[2]/div")
	private WebElement ssnConfFormatErr;
	@FindBy(xpath = ".//*[@id='addEmployee_AlreadyInGridForm']/div/table/tbody/tr[1]/td")
	private WebElement duplicateSSNErrMsg;
	@FindBy(xpath = ".//*[@id='deleteConfirmationPersonDiv']")
	private WebElement deleteEmployeeDetails;
	@FindBy(xpath = ".//*[@id='yesDeleteEmployeeButton']")
	private WebElement comfirmDelEmp;
	@FindBy(xpath = ".//*[@id='editEmployeeSsnTd']")
	private WebElement addEmpSSNConf;
	@FindBy(xpath = "//*[@id='continueAlreadyInGrid']")
	private WebElement continueEmpInGrid;
	@FindBy(xpath = ".//*[@id='deleteConfirmationActionDiv']")
	private WebElement empDelConfMsg;
	@FindBy(xpath = ".//*[@id='searchEmployeeGppButtonDiv']")
	private WebElement searchEmployeeBtn;
	@FindBy(xpath = ".//*[@id='searchType']")
	private WebElement empSearchByList;
	@FindBy(xpath = ".//*[@id='searchEmployeeParam']")
	private WebElement empSearchParam;
	@FindBy(xpath = ".//*[@id='searchSearchEmployeeButton']")
	private WebElement empSearchBtn;
	@FindBy(xpath = ".//*[@id='cancelSearchEmployeeButton']")
	private WebElement empSrchCancelBtn;
	@FindBy(xpath = ".//*[@id='saveEditParticipantButton']")
	private WebElement empEditSaveBtn;
	@FindBy(xpath = ".//*[@id='cancelEditParticipantButton']")
	private WebElement empEditCancelBtn;
	@FindBy(xpath = ".//*[@id='edit_image_0']")
	private WebElement editEmpIcon;
	@FindBy(xpath = ".//*[@id='searchEmployeeAddCheckbox_0']")
	private WebElement addSSNCheckBox;
	@FindBy(xpath = ".//*[@id='addParticipantsSearchEmployeeButton']")
	private WebElement addSSNBtn;
	@FindBy(xpath = ".//*[@id='updateInfoContinue']")
	private WebElement btnContributionSave;
	@FindBy(xpath=".//*[@name='CashRemit']")
	private WebElement frmCashRemit;
	@FindBy(xpath=".//*[@name='CashRemit']/table[2]/tbody/tr[2]/td[2]/font")
	private WebElement cashRemitanceDt;
	@FindBy(xpath=".//*[@name='CashRemit']/table[2]/tbody/tr[2]/td[3]/font")
	private WebElement cashRemitanceAmt;
	@FindBy(xpath=".//*[@name='CashRemit']/table[2]/tbody/tr[2]/td[1]/font")
	private WebElement cashRemitanceNum;
	@FindBy(xpath=".//*[@name='CashRemit']/div[2]/table/tbody/tr/td/font/input")
	private WebElement remitanceContinue;
	@FindBy(xpath=".//*[@type='checkbox'][@name='ACCEPT_WARNINGS']")
	private WebElement acceptWarnings;
	@FindBy(xpath=".//*[@type='SUBMIT'][@name='FINAL_SUBMIT']")
	private WebElement btnCompTransaction;
	@FindBy(xpath=".//*[@type='SUBMIT'][@name='CASH_AFTER_SAVE']")
	private WebElement cashAfterSave;
	@FindBy(xpath=".//*[@type='SUBMIT'][@name='CASH_ENTRY_POINT']")
	private WebElement remitanceFinalSubmit;
	@FindBy(xpath=".//*[@name='CashRemit']/table[6]/tbody/tr")
	private List<WebElement> moneySrcList;
	@FindBy(css = "ul[class*='navProcessCenterTreeLevel0'] li:nth-of-type(1) a") 
	private WebElement subMenuOverview;
	
	
	List<WebElement> contributionGridRows;
	String userVerificationEmail;
	String userVerificationAns;
	String username;
	String password;

	private LoadableComponent<?> parent;
	Actions actions;
	Select drpdwnSelect;
	String datePikrDate = "";
	private ResultSet rs = null;
	File inboundFilePath ;
	File[] inboundFiles;
	boolean checkFileExists;

	public GuidedPayrollPage() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	public GuidedPayrollPage(String email, String secondaryAnswer) {
		this.userVerificationEmail = email;
		this.userVerificationAns = secondaryAnswer;
		PageFactory.initElements(Web.getDriver(), this);
	}

	public GuidedPayrollPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(tabProcessCenter));
	}

	@Override
	protected void load() {
		@SuppressWarnings("unused")
		HomePage homepage = (HomePage) this.parent;
		LoginPage login = new LoginPage();
		/*new HomePage(new UserVerificationPage(login), true, new String[] {
				Stock.GetParameterValue("userVerificationEmail"), Stock.GetParameterValue("userVerificationAns") })
						.get();*/
		new HomePage(login, false, new String[] {
				Stock.GetParameterValue("username"), Stock.GetParameterValue("password")})
					.get();
		Reporter.logEvent(Status.PASS, "Check if the user has landed on homepage", "The user has landed on homepage",
				true);
	}

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("FRAME")) {
			return this.iframeGpp;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE MONEYSRC")) {
			return this.btnMoneysrcContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("EXP CONTRIBUTION")) {
			return this.expContrbtnTotal;
		}
		if (fieldName.trim().equalsIgnoreCase("BTN CONTCONF")) {
			return this.btnContinueConfirmationPage;
		}
		if (fieldName.trim().equalsIgnoreCase("ADD SSN CHKBOX")) {
			return this.addSSNCheckBox;
		}
		if (fieldName.trim().equalsIgnoreCase("ADD SSN BTN")) {
			return this.addSSNBtn;
		}
		if (fieldName.trim().equalsIgnoreCase("SEARCH CANCEL BTN")) {
			return this.empSrchCancelBtn;
		}
		if (fieldName.trim().equalsIgnoreCase("EDIT CANCEL BTN")) {
			return this.empEditCancelBtn;
		}

		if (fieldName.trim().equalsIgnoreCase("GPP CONTRIBUTION FREEZE TABLE")) {
			return this.gppContributionFreezedTable;
		}

		if (fieldName.trim().equalsIgnoreCase("BTN CONTRIBUTION SAVE")) {
			return this.btnContributionSave;
		}
		if (fieldName.trim().equalsIgnoreCase("BTN CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("BTN ADD EMP CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("SAVE EMP CONTINUE BTN")) {
			return this.saveAddEmployeeContBtn;
		}
		return null;
	}

	public String validateContributionTotal(String amount) throws InterruptedException {
		String actualMessage = "";
		Web.getDriver().switchTo().frame(iframeGpp);
		Web.clickOnElement(urlClicktoCont);
		//urlClicktoCont.click();
		Thread.sleep(4000);
		txtExpectedContrTotal.sendKeys(amount);
		if (Web.isWebElementDisplayed(errorTextwithoutDiv)) {
			actualMessage = errorTextwithoutDiv.getText();
			Web.getDriver().switchTo().defaultContent();
			return actualMessage;
		} else {
			Web.getDriver().switchTo().defaultContent();
			return "";
		}
	}

	public boolean compareContributionTotal(String amount) {
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iframeGpp);
		try{System.out.println("------------------");
			System.out.println(expContrbtnTotal.getText());
		}catch(Exception e){
			
		}
		if (expContrbtnTotal.getText().contains(amount)) {
			Web.getDriver().switchTo().defaultContent();
			return true;
		} else {
			Web.getDriver().switchTo().defaultContent();
			return false;
		}
	}

	public void chooseMultipleMoneySource() {
		int size = chkboxMoneySourceTable.size();
		for (int chkBoxCounter = 0; chkBoxCounter < size; chkBoxCounter++) {
			if (!chkboxMoneySourceTable.get(chkBoxCounter).isSelected()) {
				chkboxMoneySourceTable.get(chkBoxCounter).click();
			}

		}
	}

	public void createContributionProcessingforSingleMoneySource(String date, String amount) {
		Web.getDriver().switchTo().frame(iframeGpp);	
		try {
			Thread.sleep(3000);
			urlClicktoCont.click();
			Thread.sleep(4000);
			txtPayrollDate.sendKeys(date);
			txtExpectedContrTotal.sendKeys(amount);
			btnContinue.click();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iframeGpp);
		chkboxFrst.click();
		btnMoneysrcContinue.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Web.getDriver().switchTo().defaultContent();
	}

	public void enterPayRollInfo(String date, String amount) throws Exception {
		Thread.sleep(1000);
		Web.waitForElement(iframeGpp);
		Web.getDriver().switchTo().frame(iframeGpp);
		Web.waitForElement(urlClicktoCont);
		Web.clickOnElement(urlClicktoCont);
		Web.waitForElement(txtPayrollDate);
		checkIfButtonEnabled("BTN CONTINUE", false);
		txtPayrollDate.sendKeys(date);
		txtExpectedContrTotal.sendKeys(amount);
		Reporter.logEvent(Status.INFO, "Entering Payroll Information", "Payroll Information entered successfully",
				false);
		checkIfButtonEnabled("BTN CONTINUE", true);
		btnContinue.click();
		Thread.sleep(3000);
		Web.getDriver().switchTo().defaultContent();
	}

	public String fillUpdateEmployeeContribution(String amount1, String amount2, String date, String contributionamount)
			throws InterruptedException {
		String referenceNumber = "";
		createContributionProcessingforSingleMoneySource(date, contributionamount);
		Web.getDriver().switchTo().frame(iframeGpp);
		drpdwnSelect = new Select(drpdwnShowEntries);
		drpdwnSelect.selectByVisibleText("All");
		txtinputContfirst.sendKeys(amount1);
		txtinputContSecond.sendKeys(amount2);
		btnSaveandContProcessing.click();

		Thread.sleep(3000);
		for (WebElement chkboxMoneySource : chkboxVerifyMoneySource) {
			if (!chkboxMoneySource.isSelected()) {
				chkboxMoneySource.click();
			}
		}
		chkboxtoAcceptWarnings.click();
		btnCompleteTransaction.click();
		Thread.sleep(2000);
		referenceNumber = txtRefNum.getText();
		Web.getDriver().switchTo().defaultContent();
		return referenceNumber;
	}

	public boolean verifyDateandtotalonConfPage(String date, String expectedTotal) {
		Web.getDriver().switchTo().frame(iframeGpp);
		boolean isDisplayedInfoCorrect = false;
		if (payrollDataConfPage.getText().trim().equalsIgnoreCase(date)
				&& expectedTotalConfPage.getText().trim().equalsIgnoreCase(expectedTotal)) {
			isDisplayedInfoCorrect = true;
		}
		Web.getDriver().switchTo().defaultContent();
		return isDisplayedInfoCorrect;
	}

	public void navigateToProcessCenter() throws Exception {
		Web.waitForElement(tabProcessCenter);
		Actions act = new Actions(Web.getDriver());
		act.moveToElement(tabProcessCenter).build().perform();
		Thread.sleep(1000);
		CommonLib.HighlightElement(tabProcessCenter, Web.getDriver());
		tabProcessCenter.click();
		// Web.clickOnElement(tabProcessCenter);
		Thread.sleep(1000);
		tabProcessCenter.click();
		Web.waitForElement(linkOverview);
		Thread.sleep(1000);
		Web.clickOnElement(linkOverview);
		
		
	}

	public void selectmoneysource(String SelectOrVerify) throws Exception {
		WebElement monsrc = null, monsrctext = null;
		int moneySrcSize = 0;
		List<String> moneySourceList = null;
		List<String> moneySrcDataList = new ArrayList<String>();
		if(SelectOrVerify.equalsIgnoreCase("SELECT")){
			Web.getDriver().switchTo().frame(iframeGpp);
			VerifyPayrollDateAndContribution("money_sources");
			// Checking if Continue button is disabled
			checkIfButtonEnabled("CONTINUE MONEYSRC", false);
			moneySrcSize=moneySource.size();
		}else{
			moneySrcSize=moneySrcList.size();
		}
		
		// Check if correct DB entries of money source are listed
		moneySourceList = new ArrayList<String>();		
		if (Stock.GetParameterValue("planNumber") != null) {
			try {
				
				rs = DB.executeQuery(Stock.getTestQuery("getMoneySources")[0],
						Stock.getTestQuery("getMoneySources")[1], Stock.GetParameterValue("planNumber"));
				if(rs!=null)
				{
				while (rs.next()) {
					moneySourceList.add(rs.getString("SDMTCODE"));
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			moneySourceList.add("LON");
			if(SelectOrVerify.equalsIgnoreCase("SELECT")){
				for (int iLoopMSrcDB = 0; iLoopMSrcDB <= moneySourceList.size() - 1; iLoopMSrcDB++) {
					monsrctext = moneySource.get(iLoopMSrcDB + 1).findElement(By.xpath("td[2]"));
					if (monsrctext.getText().contains(moneySourceList.get(iLoopMSrcDB))) {
						Reporter.logEvent(Status.PASS, "Money Source DB validation",
								"Money Source : " + monsrctext.getText() + " validated successfully", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Money Source DB validation",
								"Money Source : " + Stock.GetParameterValue("moneySource") + " validation failed", true);
					}
				}
			}			
		}
		
		if (!Stock.GetParameterValue("moneySource").contains(",")) {
			moneySrcDataList = Arrays.asList((Stock.GetParameterValue("moneySource") + ",").split(","));
		} else {
			moneySrcDataList = Arrays.asList((Stock.GetParameterValue("moneySource")).split(","));
		}
		for (String monsrcdata : moneySrcDataList) {
			for (int iMoneySourceLoop = 1; iMoneySourceLoop <= moneySrcSize - 1; iMoneySourceLoop++) {
				if(SelectOrVerify.equalsIgnoreCase("SELECT")){
					monsrc = moneySource.get(iMoneySourceLoop).findElement(By.xpath("td[1]/input"));
				}else if(SelectOrVerify.equalsIgnoreCase("VERIFY")){
					monsrc = moneySrcList.get(iMoneySourceLoop).findElement(By.xpath("td[3]/font/input"));					
				}				
				if (monsrc.getAttribute("id").trim().contains(monsrcdata)|monsrc.getAttribute("name").trim().contains(monsrcdata)) {
					Web.clickOnElement(monsrc);
					Reporter.logEvent(Status.INFO, "Money Source "+SelectOrVerify+" successfully", "Money Source : " + monsrcdata,false);
					break;
				}
			}
		}
		if(SelectOrVerify.equalsIgnoreCase("SELECT")){
			// Checking if Continue button is enabled after money source is selected
			checkIfButtonEnabled("CONTINUE MONEYSRC", true);
			Web.clickOnElement(btnMoneysrcContinue);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Web.getDriver().switchTo().defaultContent();
		}		
	}

	public void VerifyPayrollDateAndContribution(String stepDesc) throws Exception {
		String payRollDt = "", payRollAmt = "";

		if (stepDesc.equals("money_sources")) {
			Web.waitForElement(moneysrcPayroll);
			payRollDt = moneysrcPayroll.getText();
			payRollAmt = moneysrcContribution.getText();
		} else if (stepDesc.equals("update")) {
			Web.waitForElement(updtPayroll);
			payRollDt = updtPayroll.getText();
			payRollAmt = updtContribution.getText();
		} else if(stepDesc.equals("confirm_contribution")){
			Web.waitForElement(keyFrame);
			Web.getDriver().switchTo().frame(keyFrame);
			Web.waitForElement(frmCashRemit);
			Web.waitForElement(cashRemitanceDt);
			payRollDt = cashRemitanceDt.getText();
			payRollAmt = cashRemitanceAmt.getText().replaceAll(",","");     
			
		}
		
		if (payRollDt.contains(Stock.GetParameterValue("payrollDate"))
				&& payRollAmt.contains(Stock.GetParameterValue("payrollAmt"))) {
			Reporter.logEvent(Status.PASS,
					"Validate Money Sources Payroll Date & Expected contribution details matches with previous step data",
					"Payroll Date & Expected contribution details matches with previous step data", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate Money Sources Payroll Date & Expected contribution details matches with previous step data",
					"Payroll Date & Expected contribution details doesnt match with previous step data", true);
		}
	}

	public void EnterSSN() throws Exception {
		String setSSN, setSSNConf;

		Web.getDriver().switchTo().frame(iframeGpp);
		Web.waitForElement(addEmployeeBtn);
		VerifyPayrollDateAndContribution("update");
		Web.clickOnElement(addEmployeeBtn);
		Web.waitForElement(btnAddEmployeeContinue);
		checkIfButtonEnabled("BTN ADD EMP CONTINUE", false);
		if (Stock.GetParameterValue("SSN").equals("read_table_data")// To read
																		// 4th
																		// row
																		// of
																		// the
																		// grid
				& Stock.GetParameterValue("ConfSSN").equals("read_table_data")) {
			setSSN = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[4]/td[4]")).getText().trim();
			setSSNConf = setSSN;
		} else {
			setSSN = Stock.GetParameterValue("SSN");
			setSSNConf = Stock.GetParameterValue("ConfSSN");
		}

		Web.setTextToTextBox(txtSSN, setSSN);
		Web.setTextToTextBox(txtSSNConf, setSSNConf);

		Reporter.logEvent(Status.INFO, "SSN details entered", "SSN details has been provided successfully", false);
		Thread.sleep(1000);
		if ((Web.isWebElementDisplayed(ssnFormatErr, false)) // Format
																// checker
				&& (Web.isWebElementDisplayed(ssnConfFormatErr, false))) {
			if (ssnFormatErr.getText().trim().equals(Stock.GetParameterValue("errMessage_1"))) {
				Reporter.logEvent(Status.PASS, "Validate SSN format error", "SSN format error validated successfully",
						false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate SSN format error", "SSN format error validation failed",
						false);
			}
			if (ssnConfFormatErr.getText().trim().equals(Stock.GetParameterValue("errMessage_2"))) {
				Reporter.logEvent(Status.PASS, "Validate SSN mismatch error",
						"SSN mismatch error validated successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate SSN mismatch error", "SSN mismatch error validation failed",
						false);
			}
			checkIfButtonEnabled("BTN ADD EMP CONTINUE", false);
			Web.clickOnElement(addEmployeeBtn);
		} else {
			checkIfButtonEnabled("BTN ADD EMP CONTINUE", true);
			Web.clickOnElement(btnAddEmployeeContinue);
			Thread.sleep(3000);
		}
		if (Web.isWebElementDisplayed(duplicateSSNErrMsg, false)) { // Duplicate
																		// SSN
																		// error
																		// message
			if (Web.VerifyText(Stock.GetParameterValue("errMessage_1"), duplicateSSNErrMsg.getText(), false)) {
				Reporter.logEvent(Status.PASS, "Validate if duplicate SSN error message displayed",
						"Duplicate SSN error message displayed successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate if duplicate SSN error message displayed",
						"Duplicate SSN error message is not displayed", true);
			}
			Web.clickOnElement(continueEmpInGrid);
			Thread.sleep(5000);

			// Verify if the record moves up to first row
			if (gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[4]")).getText().trim()
					.equals(setSSN)) {
				Reporter.logEvent(Status.PASS, "Validate if existing employee moved to the top of grid",
						"Employee successfully moved to top of the grid", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate if existing employee moved to the top of grid",
						"Employee details not found at top of the grid", true);
			}
			Web.clickOnElement(addEmployeeBtn);
		}

		Web.getDriver().switchTo().defaultContent();
	}

	public void AddEmployee(String checkErr) throws Exception {
		String existingSSN = "";
		new GuidedPayrollPage();
		Web.getDriver().switchTo().frame(iframeGpp);

		Web.waitForElement(addEmployeeForm);
		checkIfButtonEnabled("SAVE EMP CONTINUE BTN", false);
		Web.clickOnElement(addEmpSSNConf);
		if (addEmpSSNConf.getText().trim().equals(Stock.GetParameterValue("SSN"))) {
			Reporter.logEvent(Status.PASS, "Validate if new employee SSN number is displayed in Add employee form",
					"SSN number is displayed successfully", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate if new employee SSN number is displayed in Add employee form",
					"SSN number is not displayed", true);
		}
		CommonLib.fillForm(addEmployeeForm, "gpdto_creditedHours", "gpdto_lastName", "gpdto_firstName", "gpdto_birthDate",
				"gpdto_sex", "gpdto_maritalStatus", "gpdto_firstLineMailing", "gpdto_city", "gpdto_stateCode",
				"gpdto_zipCode", "gpdto_hireDate", "gpdto_participationDate", "gpdto_salAmt", "gpdto_salAmtQual",
				"gpdto_eligibilityYes");
		Web.setTextToTextBox(txtAddEmpTotalComp, "");
		Reporter.logEvent(Status.INFO, "New employee details entered",
				"New employee details has been provided successfully", false);
		if (checkErr.equalsIgnoreCase("invalid_input_error")) {
			checkIfButtonEnabled("SAVE EMP CONTINUE BTN", false);
		} else {
			checkIfButtonEnabled("SAVE EMP CONTINUE BTN", true);
			Web.clickOnElement(saveAddEmployeeContBtn);
			Web.waitForElement(cancelSSNBtn);
			Web.clickOnElement(cancelSSNBtn);
			rs = DB.executeQuery(Stock.getTestQuery("checkIfSSNExist")[0],
					Stock.getTestQuery("checkIfSSNExist")[1], Stock.GetParameterValue("SSN"));
			while (rs.next()) {
				existingSSN = rs.getString("SSN");
			}
			if (readGPPEmployeeAndContributionTable(Stock.GetParameterValue("SSN"), 4).equals(existingSSN)) {
				Reporter.logEvent(Status.PASS, "Validate if a new employee added using GPP",
						"New employee added successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate if a new employee added using GPP",
						"Failed to add a new employee", true);
			}
		}
		Web.getDriver().switchTo().defaultContent();
	}

	public void checkIfButtonEnabled(String btnNm,boolean enableDisable) {
		WebElement ele =  getWebElement(btnNm);				
		String btnName = ele.getAttribute("value").trim();
		String btnStats = Globals.GC_EMPTY;
		boolean enableDisableStatus = false;
		if (!enableDisable) {
			if (ele.getAttribute("class").contains("disable")){
				enableDisableStatus = true;	btnStats = "Disabled";
			} 
		} else {
			if (ele.getAttribute("class").contains("enable")) {
				enableDisableStatus = true; btnStats = "Enabled";
			}
		}
		
		if(enableDisableStatus){
			Reporter.logEvent(Status.PASS, "Validating if " + btnName + " button is "+btnStats,
					btnName + " button is "+btnStats+" as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating if " + btnName + " button is "+btnStats,
					btnName + " button is not "+btnStats+" as not expected", true);
		}
		
		
		
		
	}

	public String readGPPEmployeeAndContributionTable(String keyElement, int searchColNum) {
		new GuidedPayrollPage();
		int iLoopRow = 0;
		try {
			CommonLib.enterData(selectNoOfEntries, "All");
			Web.waitForElement(gppContributionFreezedTable);
			String tabData = "";
			List<WebElement> tableRows = gppContributionFreezedTable.findElements(By.cssSelector("tbody tr"));
			for (iLoopRow = 1; iLoopRow <= tableRows.size(); iLoopRow++) {
				tabData = tableRows.get(iLoopRow).findElement(By.xpath("td[" + searchColNum + "]")).getText();
				if (tabData.trim().equals(keyElement)) {
					break;
				}
			}
			WebElement tableCols = null;
			if (searchColNum <= 6) {
				tableCols = gppContributionFreezedTable
						.findElement(By.xpath("tbody/tr[" + iLoopRow + "]/td[" + searchColNum + "]"));
			} else {
				tableCols = gppContributionUnfreezedTable
						.findElement(By.xpath("tbody tr[" + iLoopRow + "]/td[" + searchColNum + "]"));
			}
			return tableCols.getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Globals.GC_EMPTY;
	}

	public void checkInputError(String AddOrEdit) throws Exception {
		new GuidedPayrollPage();
		String tagName = "", actualText = "";
		WebElement parentNode = addEmployeeForm;
		String colNms = "";
		if (AddOrEdit.equalsIgnoreCase("add")) {
			colNms = "gpdto_creditedHours,gpdto_lastName,gpdto_firstName,gpdto_birthDate,"
					+ "gpdto_firstLineMailing,gpdto_city,gpdto_stateCode,gpdto_zipCode,"
					+ "gpdto_hireDate,gpdto_participationDate,gpdto_salAmt,gpdto_salAmtQual";
		} else if (AddOrEdit.equalsIgnoreCase("edit")) {
			colNms = "gpdto_salAmt,gpdto_hireDate";
		}
		Web.getDriver().switchTo().frame(iframeGpp);

		for (String colNm : colNms.split(",")) {
			tagName = parentNode.findElement(By.id(colNm)).getTagName();
			if (tagName.equals("input")) {
				Web.waitForElement(parentNode
						.findElement(By.cssSelector("input[id='" + colNm + "']+div[class='validationError']")));
				actualText = parentNode
						.findElement(By.cssSelector("input[id='" + colNm + "']+div[class='validationError']")).getText()
						.trim();
			} else if (tagName.equals("select")) {
				Web.waitForElement(parentNode
						.findElement(By.cssSelector("select[id='" + colNm + "']+div[class='validationError']")));
				actualText = parentNode
						.findElement(By.cssSelector("select[id='" + colNm + "']+div[class='validationError']"))
						.getText().trim();
			}

			if (actualText.equals(Stock.GetParameterValue(colNm))) {
				Reporter.logEvent(Status.PASS, "Validating invalid input error" + Stock.GetParameterValue(colNm)
						+ " for " + AddOrEdit + " Employee", "Error : " + actualText + " displayed as expected", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Validating invalid input error" + Stock.GetParameterValue(colNm) + " for " + AddOrEdit
								+ " Employee",
						"Expected Error : " + Stock.GetParameterValue(colNm) + " , Actual Error : " + actualText,
						true);
			}
		}
		Web.clickOnElement(addEmployeeBtn);
		Web.getDriver().switchTo().defaultContent();
	}

	public void deleteEmployeeAndConfirm() {
		String empFNameBeforeDel, empLNameBeforeDel, SSNBeforeDel;
		String empFNameAfterDel, empLNameAfterDel, SSNAfterDel;
		WebElement trash;
		new GuidedPayrollPage();
		Web.getDriver().switchTo().frame(iframeGpp);

		trash = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[1]/img"));
		if (Web.isWebElementDisplayed(trash, true)) {
			empFNameBeforeDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[6]")).getText().trim();
			empLNameBeforeDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[5]")).getText().trim();
			SSNBeforeDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[4]")).getText().trim();
			Web.clickOnElement(trash);
			// Verify Delete Employee details
			if (Web.isWebElementDisplayed(deleteEmployeeDetails, true)) {
				String delEmpDetails = deleteEmployeeDetails.getText();
				if (delEmpDetails.split(" ")[0].equals(SSNBeforeDel)
						&& delEmpDetails.split(" ")[1].equals(empLNameBeforeDel + ",")
						&& delEmpDetails.split(" ")[4].equals(empFNameBeforeDel)
						&& empDelConfMsg.getText().trim().equals(Stock.GetParameterValue("errMessage_1"))) {

					Reporter.logEvent(Status.PASS, "Verify delete employee details and error message",
							"Employee details successfully validated while deleting employee record :" + delEmpDetails,
							false);
					Web.clickOnElement(comfirmDelEmp);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Reporter.logEvent(Status.INFO, "Confirm employee record delete",
							"Confirming employee record delete", false);
					// Verify successfull delete
					empFNameAfterDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[6]")).getText()
							.trim();
					empLNameAfterDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[5]")).getText()
							.trim();
					SSNAfterDel = gppContributionFreezedTable.findElement(By.xpath("tbody/tr[1]/td[4]")).getText()
							.trim();
					if (!empFNameBeforeDel.equals(empFNameAfterDel) && !empLNameBeforeDel.equals(empLNameAfterDel)
							&& !SSNBeforeDel.equals(SSNAfterDel)) {
						Reporter.logEvent(Status.PASS, "Verify employee delete from contribution table",
								"Employee has been deleted successfully", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify employee delete from contribution table",
								"Employee " + delEmpDetails + "delete failed", true);
					}
				} else {
					Reporter.logEvent(Status.FAIL, "Verify delete employee details",
							"Employee details validating failed while deleting employee record", true);
				}
			}
		} else {
			Reporter.logEvent(Status.FAIL, "Delete GPP Employee record", "Couldn't delete GPP employee record ", false);
		}
		Web.getDriver().switchTo().defaultContent();
	}

	public void editEmployee(String dataInput) throws Exception {
		new GuidedPayrollPage();
		Web.getDriver().switchTo().frame(iframeGpp);
		Web.clickOnElement(editEmpIcon);
		Web.waitForElement(addEmployeeForm);
		if (dataInput.equalsIgnoreCase("valid")) {
			CommonLib.fillForm(addEmployeeForm, "gpdto_lastName", "gpdto_firstName");
		} else if (dataInput.equalsIgnoreCase("invalid")) {
			CommonLib.fillForm(addEmployeeForm, "gpdto_salAmt", "gpdto_hireDate");
		}

		Web.setTextToTextBox(txtAddEmpTotalComp, "");
		Reporter.logEvent(Status.INFO, "Edit employee details entered",
				"Edit employee details has been provided successfully", false);
		if (dataInput.equalsIgnoreCase("valid")) {
			Web.clickOnElement(empEditSaveBtn);
		} else if (dataInput.equalsIgnoreCase("invalid")) {

		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Validating SSN,LName,FName,Edit icon class
		if (dataInput.equalsIgnoreCase("valid")) {
			if (readGPPEmployeeAndContributionTable(Stock.GetParameterValue("SearchParameterSSN"), 4)
					.equals(Stock.GetParameterValue("SearchParameterSSN"))
					&& readGPPEmployeeAndContributionTable(Stock.GetParameterValue("gpdto_lastName"), 5)
							.equals(Stock.GetParameterValue("gpdto_lastName"))
					&& readGPPEmployeeAndContributionTable(Stock.GetParameterValue("gpdto_firstName"), 6)
							.equals(Stock.GetParameterValue("gpdto_firstName"))
					&& Web.VerifyText("centered employeeInfoUpdated", gppContributionFreezedTable
							.findElement(By.xpath("tbody/tr[1]/td[2]")).getAttribute("class"), true)) {

				Reporter.logEvent(Status.PASS, "Validate edit employee for GPP", "Employee edited successfully", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate edit employee for GPP", "Employee edit failed", true);
			}
		}

		Web.getDriver().switchTo().defaultContent();
	}

	public void performSearchEmployee(boolean closeSearchForm) throws Exception {
		new GuidedPayrollPage();
		Web.getDriver().switchTo().frame(iframeGpp);
		Web.waitForElement(searchEmployeeBtn);
		Web.clickOnElement(searchEmployeeBtn);
		Web.waitForElement(empSearchByList);
		drpdwnSelect = new Select(empSearchByList);
		drpdwnSelect.selectByVisibleText(Stock.GetParameterValue("SearchBy"));
		Web.setTextToTextBox(empSearchParam, Stock.GetParameterValue("SearchParameterSSN"));
		Web.clickOnElement(empSearchBtn);
		Reporter.logEvent(Status.INFO, "Searching Employee " + Stock.GetParameterValue("SearchParameterSSN"),
				"Employee search performed successfully", false);
		Thread.sleep(2000);
		if (closeSearchForm) {
			Web.waitForElement(empSrchCancelBtn);
			Web.clickOnElement(empSrchCancelBtn);
		}
		Web.getDriver().switchTo().defaultContent();
	}

	public void performContributionProcessing(int RowNum) throws Exception {
		new GuidedPayrollPage();
		int colCnt = 1;
		Web.getDriver().switchTo().frame(iframeGpp);
		List<String> moneySourceList = new ArrayList<String>();
		List<WebElement> tableCols = null;

		rs = DB.executeQuery(Stock.getTestQuery("getMoneySourceCols")[0],
				Stock.getTestQuery("getMoneySourceCols")[1], Stock.GetParameterValue("planNumber"),
				Stock.GetParameterValue("moneySource"));// Replace with GA_ID
															// *********************
		while (rs.next()) {
			moneySourceList.add(rs.getString("DESCR"));
		}
		Web.waitForElement(gppContributionUnfreezedTable);
		tableCols = gppContributionUnfreezedTable.findElements(By.cssSelector("thead th"));
		for (String colNm : moneySourceList) {
			for (WebElement ele : tableCols) {
				if (ele.findElement(By.cssSelector("div")).getText().trim().equalsIgnoreCase(colNm)) {
					Web.setTextToTextBox(
							gppContributionUnfreezedTable
									.findElement(By.xpath("tbody/tr[" + RowNum + "]/td[" + colCnt + "]/input")),
							Stock.GetParameterValue(colNm));
					ContributionTabFormatChecker(RowNum, colCnt, Stock.GetParameterValue(colNm));
					break;
				}
				colCnt = colCnt + 1;
			}
			colCnt = 1;
		}

		Web.getDriver().switchTo().defaultContent();
	}

	private void ContributionTabFormatChecker(int CurrentRowNo, int CurrentColNo, String data) {
		Web.clickOnElement(gppContributionUnfreezedTable
				.findElement(By.xpath("tbody/tr[" + CurrentRowNo + "]/td[" + (CurrentColNo + 1) + "]/input")));
		if (!data.contains(".")) {
			if (gppContributionUnfreezedTable
					.findElement(By.xpath("tbody/tr[" + CurrentRowNo + "]/td[" + (CurrentColNo) + "]/input"))
					.getAttribute("value").equals(data + ".00")) {
				Reporter.logEvent(Status.PASS, "Checking numeric format for Contribution Table",
						"Number formatter successfully validated for data :" + data, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Checking numeric format for Contribution Table",
						"Number formatter validation failed for data :" + data, true);
			}
		} else {
			if ((gppContributionUnfreezedTable
					.findElement(By.xpath("tbody/tr[" + CurrentRowNo + "]/td[" + (CurrentColNo) + "]/input"))
					.getAttribute("value")).toString().replace(".", ",").split(",").length > 1) {
				Reporter.logEvent(Status.PASS, "Checking numeric format for Contribution Table",
						"Number formatter successfully validated for data :" + data, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Checking numeric format for Contribution Table",
						"Number formatter validation failed for data :" + data, true);
			}
		}
	}

	public void validateRemittance() throws Exception {
		VerifyPayrollDateAndContribution("confirm_contribution");
		if(StringUtils.isNumeric(cashRemitanceNum.getText().trim())){
			Reporter.logEvent(Status.PASS,"Validating Contribution reference number","Reference number :"+
		                      cashRemitanceNum.getText().trim()+ " is validated as a numeric value", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Contribution reference number",
					          "Reference number validation failed as it is non numeric value", true);
		}			
		Web.clickOnElement(remitanceContinue);
		Thread.sleep(5000);
		selectmoneysource("VERIFY");
		Reporter.logEvent(Status.INFO,"Remitance trasaction completion status", "Verified money sources", false);
		Web.clickOnElement(acceptWarnings);
		Web.clickOnElement(btnCompTransaction);
		Web.waitForElement(cashAfterSave);
		Web.clickOnElement(cashAfterSave);
		Reporter.logEvent(Status.INFO,"Remitance trasaction completion status", "Remitance transaction reached final step", true);
		Web.waitForElement(remitanceFinalSubmit);
		Web.clickOnElement(remitanceFinalSubmit);
		Web.getDriver().switchTo().defaultContent();
		Reporter.logEvent(Status.INFO,"Remitance trasaction completion status", "Remitance transaction completed successfully", false);
	}

	public void navigateBack()
	{
		tabProcessCenter.click();
		subMenuOverview.click();
	}
	
	public boolean checkInboundDirectory(String path,String fileName)
	{
		inboundFilePath = new File(path);
		inboundFiles = inboundFilePath.listFiles();
		checkFileExists = new File(path,fileName).exists();
		for(int iCounter = 0 ; iCounter < inboundFiles.length ; iCounter++)
		{
			if(inboundFiles[iCounter].getName().equalsIgnoreCase(fileName))
		{
		return true;
		}
	}
	return false;
	}

	public void switchToFrame(String string) {
		lib.Web.getDriver().switchTo().frame(getWebElement("FRAME"));
	}

	public void checkIfEditICONHighlighted() {
		if(Web.VerifyText("centered",
			   getWebElement("GPP CONTRIBUTION FREEZE TABLE").
			   findElement(By.xpath("tbody/tr[1]/td[2]")).getAttribute("class"), true)){
			   
			   Reporter.logEvent(Status.PASS, "Validate if the Edit icon is highlighted",
				"Successfully validated that edit icon is not highlighted as expected", false);
			 }else{
				 Reporter.logEvent(Status.FAIL, "Validate if the Edit icon is highlighted",
							"Successfully validated that edit icon is highlighted as not expected", true);
			 }			
	}
	
}
