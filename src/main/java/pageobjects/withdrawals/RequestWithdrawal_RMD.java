package pageobjects.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RequestWithdrawal_RMD extends
		LoadableComponent<RequestWithdrawal_RMD> {
			String sEmail=null;
			String sFianlPhoneNum=null;
			String confirmationNo=null;	
			String prevConfirmationNo=null;	
			Actions keyBoardEvent=new Actions(Web.getDriver());
	private LoadableComponent<?> parent;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = ".//h1[contains(text(),'required minimum distribution')]")
	private WebElement lblrmd;
	@FindBy(xpath = ".//h1[contains(text(),'How is the RMD calculated?')]")
	private WebElement txtModalRMDCalculation;
	
	@FindBy(xpath = ".//a[contains(@translate,'Logout')]")
	// @FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;
	private String textField="//*[contains(text(),'webElementText')]";
	@FindBy(xpath = "//h1[@class='page-title']")
	private WebElement txtPageTitle;
	
	@FindBy(xpath = "//p[@ng-if='showIrsRules']")
	private WebElement txtIrsRule;
	@FindBy(id = "distributionTemplate")
	private List<WebElement> RMDWindow;
	@FindBy(id = "distributionPanelHeader")
	private List<WebElement> RMDHeader;
	@FindBy(id = "distributionSelection")
	private WebElement RMDHeaderCheckBox;
	@FindBy(xpath = ".//h1[text()[normalize-space()='Request a withdrawal']]")
	private WebElement lblRequestAWithdrawal;
	
	@FindBy(xpath = "//div[@id='rmdAmountSec']/p")
	private List<WebElement> txtRemainingRMD;
	@FindBy(xpath = "//div[@id='withholdingsSec']/p/span")
	private List<WebElement> txtWithholdingSec;
	@FindBy(xpath = "//div[@id='withholdingsSec']/span")
	private List<WebElement> txtDefaultFederal;
	
	@FindBy(xpath = "//div[@id='paymentMethod']/p")
	private List<WebElement> txtPaymentMethod;
	@FindBy(xpath = "//div[@id='paymentMethod']/span")
	private List<WebElement> txtRegularMail;
	
	
	@FindBy(xpath = "//div[@id='rmdAmountSec']/a")
	private WebElement lnkChangeAmount;
	
	@FindBy(xpath = "//div[@id='rmdAmountSec']/div/div/div")
	private List<WebElement> txtBalanceRMDAmount;
	@FindBy(xpath = "//div[@id='rmdAmountSec']/div/div/span")
	private List<WebElement> txtBalanceRMDText;
	@FindBy(xpath = "//div[@id='rmdAmountSec']/div/div/a")
	private List<WebElement> lnkBalanceCalculationLink;
	@FindBy(xpath = "//div[@id='rmdAmountSec']/div/div/a")
	private WebElement lnkBalanceCalculation;
	@FindBy(xpath = "//div[contains(text(),'Taken year-to-date')]/following-sibling::div")
	private List<WebElement> txtTakenRMDAmount;
	
	@FindBy(xpath = "//div[@id='rmdAmountSec']/span")
	private List<WebElement> txtRemainingRMDAmount;
	
	@FindBy(linkText = "Modify withholding")
	private WebElement lnkModifyWithholding;

	/*@FindBy(linkText = "Change method")
	private WebElement lnkChangeMethod;*/
	
	@FindBy(xpath = "//.[@id='paymentMethod']/a")
	private List<WebElement> lnkChangeMethod;
	
	
	@FindBy(xpath = "//div[@id='paymentMethod']/div/span/following-sibling::span/span")
	private List<WebElement> lstHomeAddressProfilePage;
	
	//How is the RMD Calculated Modal
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.balanceAsOf']")
	private WebElement txtBalanceAsOf;
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.balanceAsOf']/following-sibling::div")
	private WebElement txtBalanceAsOfDollar;
	
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.ageAsOf']")
	private WebElement txtAgeAsOf;
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.ageAsOf']/following-sibling::div")
	private WebElement txtYearsOld;
	
	@FindBy(xpath = "//div[@ng-if='!distribution.isJointLifeTable']")
	private WebElement txtFactorAge;
	@FindBy(xpath = "//div[@ng-if='!distribution.isJointLifeTable']/following-sibling::div")
	private WebElement txtFactorAgeNumber;
	
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.rmdForYear.title']")
	private WebElement txtRMDForYear;
	@FindBy(xpath = "//div[@translate='howIsThisCalcModal.rmdForYear.title']/following-sibling::div[1]")
	private WebElement txtRMDRemainingNumber;
	
	@FindBy(linkText = "Close")
	private WebElement lnkCloseModal;
	
	//Modify Withholding
	
	@FindBy(xpath="//*[contains(@class, 'editable-text-trigger')]")
	private WebElement lnkContributionRate;
	
	@FindBy(id="taxWithholdingSlider-text-edit") private WebElement txtcontributionRateSlider;
	@FindBy(xpath=".//span[@class='valueAndEnd']/span[1]") private WebElement lnksliderValue;
	@FindBy(xpath="//button[@class='btn btn-primary reset-padding']") private WebElement btnDone;
	
	@FindBy(xpath=".//button[contains(text(),'Cancel')]")
	private WebElement lnkCancelTax;
	@FindBy(xpath=".//button[contains(text(),'Update')]")
	private WebElement lnkUpdateTax;
	
	//Change method link
	//div[contains(@ng-if,'showConditionalViews')]
	@FindBy(xpath="//input[@id='first-class-mail-radio-selection']/..//following-sibling::div/span[@class='bold first-class-mail-cost']")
	private WebElement txtRegularMailFee;
	
	@FindBy(id="first-class-mail-radio-selection") private WebElement btnRegaularMail;
	@FindBy(id="express-mail-radio-selection") private WebElement btnExpeditedMail;
	@FindBy(id="ach-delivery-radio-selection") private WebElement btnDirectDeposit;
	@FindBy(id="ach-account-selection") private WebElement drpDwnSelectAccount;
	
	@FindBy(xpath="//input[@id='express-mail-radio-selection']/..//following-sibling::div/strong/span")
	private WebElement txtExpeditedMailFee;
	
	@FindBy(xpath="//input[@id='ach-delivery-radio-selection']/..//following-sibling::div/strong/span")
	private WebElement txtDirectDepositFee;
	
	//RMD Informational Message
	@FindBy(xpath="(//rmd-info[@calc-criteria-msg='rmdReqCtrl.calCriteriaMessage']/div/p)[2]")
	private WebElement txtInfoMessage;
	@FindBy(xpath="(//rmd-info[@calc-criteria-msg='rmdReqCtrl.calCriteriaMessage']/div/p)[1]")
	private WebElement txtInfodistributionMessage;
	@FindBy(xpath="(//rmd-info[@calc-criteria-msg='distribution.calCriteriaMessage']/div/p)[1]")
	private WebElement txtInfodistributionMessageModal;
	
	@FindBy(xpath="//div[@id='rmdAlert']/p")
	private WebElement txtInfowarningMessage;
	@FindBy(xpath="//div[@id='rmdAlert']/p")
	private List<WebElement> txtInfoGracewarningMessage;
	
	//RMD Selection
	
	@FindBy(xpath="//input[@name='updateStatus']")
	private WebElement chkBoxStatus;
	@FindBy(id="pronotification-selection") private WebElement dropDownStatus;
	@FindBy(id="emailId") private WebElement txtStatus;
	@FindBy(id="phoneNumberId") private WebElement txtPhoneStatus;
	
	@FindBy(xpath="//button[@ng-click='rmdReqCtrl.requestDistribution()']")
	private WebElement btnRequestRMD;
	
	//SSN Validation
	
	@FindBy(xpath="//input[@value='true']")
	private WebElement inpYes;
	@FindBy(xpath="//input[@value='false']")
	private WebElement inpNo;
	@FindBy(id = "ssn")
	private WebElement inputSSN;
	@FindBy(xpath = ".//button[contains(text(),'Confirm and continue')]")
	private WebElement btnConfirmContinue;
	
	//Confirmation Page
	
	/*@FindBy(xpath="//div[@class='confirmation-process']/dl/div[@class='complete']/dd/span[@ng-if]")
	private WebElement txtConfirmationNumber;*/
	
	@FindBy(xpath="//div[@class='confirmation-process']/dl/div[@class='complete']/dd/span[@ng-if and not(contains(text() ,','))]")
	private List<WebElement> txtConfirmationNumber;
	
	
	
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div[@class='complete']/dd)[2]")
	private WebElement txtCurrentTimeStapm;
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div)[3]/dt/span")
	private WebElement txtPaymentType;
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div)[3]/dt")
	private List<WebElement> fulltxtPaymentType;
	@FindBy(xpath="//div[@class='confirmation-process']/p")
	private WebElement txtProactiveNotification;
	
	
	@FindBy(xpath="//tr[./th[contains(text(),'Delivery Method')]]/td")
	private List<WebElement> confirmationPageDeliveryMethod;
	@FindBy(xpath="//tr[./th[contains(text(),'Sent to')]]/td")
	private List<WebElement> confirmationPageSentToAddress;
	@FindBy(xpath="//tr[./th[contains(text(),'Payment amount')]]/td")
	private List<WebElement> confirmationPagePaymentAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'Federal Tax Withholding')]]/td")
	private List<WebElement> confirmationPageFederalTaxAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'RMD Amount')]]/td")
	private List<WebElement> confirmationPageRMDAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'Processing date')]]/td")
	private List<WebElement> confirmationPageProcessingDate;
	@FindBy(xpath="//tr[./th[contains(text(),'State Tax')]]/td")
	private List<WebElement> confirmationPageStateTax;
	@FindBy(xpath="//tr[./th[contains(text(),'Delivery Fee')]]/td")
	private List<WebElement> confirmationPageDeliveryFee;
	
	
	//Alert Message
	
	@FindBy(xpath=".//div[contains(@class,'alert alert-')]/p")
	private WebElement txtAlertConsentText;
	
	@FindBy(xpath="//*[contains(text(),'Email Form')]")
	private WebElement emailFormButton;
	
	@FindBy(xpath="//p[contains(text(), 'If you have additional questions, please contact Participant Services Representative at')]")
	private WebElement txtPSRNumber;
	@FindBy(xpath="//p[contains(text(), 'A form has been emailed to')]")
	private WebElement txtEmailFormTo;
	
	@FindBy(xpath="//input[@type='email']")
	private WebElement emailFormInputBox;
	
	//SSN Validation
	
	@FindBy(xpath = "//div[@class='invalid-ssn ssn-error text-right']")
	private WebElement ssnErrorMsg;
	/**
	 * Default Constructor
	 */
	public RequestWithdrawal_RMD() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestWithdrawal_RMD(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),
				"User Name is Not Displayed\n");

		// Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestAWithdrawal,
		// true),"Request A WithDrawal Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {

			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
						+ strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String userLogedIn = this.lblUserName.getText();
		/*
		 * String sponser = this.lblSponser.getAttribute("Alt"); if
		 * (sponser.isEmpty()) { sponser = Common.GC_DEFAULT_SPONSER; }
		 */
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));
			Assert.assertTrue(Web.isWebElementDisplayed(lblrmd),
					"RMD page is not loaded");

		} else {
			this.lnkLogout.click();
			Web.waitForElement(btnLogin);
			Assert.assertTrue(false, "");
		}
	}

	@Override
	protected void load() {
		/*
		 * if (Web.isWebElementDisplayed(lnkDismiss)) { this.lnkDismiss.click();
		 * }
		 */
		this.parent.get();

		((LeftNavigationBar) this.parent)
				.clickNavigationLink("Request a withdrawal");
		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();
		if (Web.isWebElementDisplayed(lblrmd, true))
			Reporter.logEvent(Status.PASS,
					"Verify if Withdrawal - RMD Page is Displayed",
					"Withdrawal - RMD Page is displayed successfully", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify if Withdrawal - RMD Page is Displayed",
					"Withdrawal - RMD Page is NOT displayed", true);

	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	LOG OUT Or LOGOUT - [Link]
	 * 	HOME - [Link]
	 * 	MY ACCOUNTS - [Link]
	 * 	RETIREMENT INCOME - [Label]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		if (fieldName.trim().equalsIgnoreCase("Required Minimum Distribution")) {
			return this.lblrmd;
		}
		if (fieldName.trim().equalsIgnoreCase("IRS Rule")) {
			return this.txtIrsRule;
		}
		if (fieldName.trim().equalsIgnoreCase("How is this calculated")) {
			return this.lnkBalanceCalculation;
		}
		if (fieldName.trim().equalsIgnoreCase("TxtInfoMessage")) {
			return this.txtInfoMessage;
		}
		if (fieldName.trim().equalsIgnoreCase("TxtDistributionMessage")) {
			return this.txtInfodistributionMessage;
		}
		if (fieldName.trim().equalsIgnoreCase("TxtDistributionMessageModal")) {
			return this.txtInfodistributionMessageModal;
		}
		if (fieldName.trim().equalsIgnoreCase("Close")) {
			return this.lnkCloseModal;
		}
		if (fieldName.trim().equalsIgnoreCase("Warning Message")) {
			return this.txtInfowarningMessage;
		}
		if (fieldName.trim().equalsIgnoreCase("Email form button")) {
			return this.emailFormButton;
		}
		if (fieldName.trim().equalsIgnoreCase("Request My RMD Button")) {
			return this.btnRequestRMD;
		}
		if (fieldName.trim().equalsIgnoreCase("Cancel TAX Modal")) {
			return this.lnkCancelTax;
		}
			
		return null;
	}
	
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					true);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", true);
			throw new Error(fieldName+" is not displayed");
		}
	
		return isTextDisplayed;
	}
	public ArrayList<String> getRDMAmount(String queryName,String userName,String columnName) throws SQLException
	{
		int j=0;
		ArrayList<String> lstAmount = new ArrayList<String>();
		String[] sqlQuery = Stock.getTestQuery(queryName);
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Amount = DB.executeQuery(sqlQuery[0], sqlQuery[1],userName);
		while (rs_Amount.next()) {
			lstAmount.add(j, rs_Amount.getString(columnName));
			j++;
		}
		//System.out.println(lstAmount.size());
		return lstAmount;
	}
	public void verifySentToAddress() throws SQLException
	{
		String sFirstLine, sSecondLine;
		
		String[] sqlQueryHomeAddress = Stock.getTestQuery("getAddress");
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		sFirstLine = rs_Number.getString("first_line_mailing");
		sSecondLine = rs_Number.getString("scnd_line_mailing");
		
		String sCity = rs_Number.getString("city");
		String sState = rs_Number.getString("state_code");
		String[] sZip = rs_Number.getString("zip_code").split("-");
		//String sCountry = rs_Number.getString("country");

		String sFullAddress = sCity + " " + sState + " " + sZip[0] + " ";
		//System.out.println("From DB " + sFullAddress);
		
		if (sFirstLine.equalsIgnoreCase(lstHomeAddressProfilePage.get(0).getText()))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Sent To Address: FirstLine Address",
						"FirstLine matches the DB values. Expected: "+sFirstLine+" Actual: "+lstHomeAddressProfilePage.get(0).getText(), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Sent To Address: FirstLine Address",
						"FirstLine does not match the DB values. Expected: "+sFirstLine+" Actual: "+lstHomeAddressProfilePage.get(0).getText(), true);
			}
			if(sSecondLine!=null)
			{
				if (sSecondLine.equalsIgnoreCase(lstHomeAddressProfilePage.get(1).getText()))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Sent To Address: sSecondLine Address",
							"sSecondLine matches the DB values. Expected: "+sSecondLine+" Actual: "+lstHomeAddressProfilePage.get(1).getText(), false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Sent To Address: sSecondLine Address",
							"sSecondLine does not match the DB values. Expected: "+sSecondLine+" Actual: "+lstHomeAddressProfilePage.get(1).getText(), true);
				}
				if (sFullAddress.trim().equalsIgnoreCase(lstHomeAddressProfilePage.get(2).getText()))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Sent To Address: Full Address",
							"Address matches the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(2).getText(), false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Sent To Address: Full Address",
							"Address does not match the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(2).getText(), true);
				}
			}
			else
			{
				if (sFullAddress.trim().equalsIgnoreCase(lstHomeAddressProfilePage.get(1).getText()))
				{
					Reporter.logEvent(Status.PASS,
							"Verify Sent To Address: Full Address",
							"Address matches the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(1).getText(), false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Sent To Address: Full Address",
							"Address does not match the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(1).getText(), true);
				}
			}
			
		
		/*else
		{
			if (sFirstLine.equalsIgnoreCase(lstHomeAddressProfilePage.get(2).getText()))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Sent To Address: FirstLine Address",
						"FirstLine matches the DB values. Expected: "+sFirstLine+" Actual: "+lstHomeAddressProfilePage.get(2).getText(), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Sent To Address: FirstLine Address",
						"FirstLine does not match the DB values. Expected: "+sFirstLine+" Actual: "+lstHomeAddressProfilePage.get(2).getText(), true);
			}
			if (sFullAddress.trim().equalsIgnoreCase(lstHomeAddressProfilePage.get(3).getText()))
			{
				Reporter.logEvent(Status.PASS,
						"Verify Sent To Address: FirstLine Address",
						"Address matches the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(3).getText(), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Sent To Address: FirstLine Address",
						"Address does not match the DB values. Expected: "+sFullAddress+" Actual: "+lstHomeAddressProfilePage.get(3).getText(), true);
			}
		}*/
		
	}
	public void verifyHeaderDetailsPreviousYears(String lastYear,String currentYear)
	{
		for (int i = 0; i < RMDWindow.size(); i++) {
			if (i == 0) {
				//System.out.println(lastYear+" RMD");
				if (RMDHeader.get(i).getText().equalsIgnoreCase(lastYear+" RMD"))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Header for previous year",
							"RMD Header is displayed in previous year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2017 RMD", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Header for previous year",
							"RMD Header is displayed in previous year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2017 RMD", true);
				
				
				
			}
			else
			{
				if (RMDHeader.get(i).getText().equalsIgnoreCase(currentYear+" RMD"))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Header for current year",
							"RMD Header is displayed in current year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2018 RMD", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Header for current year",
							"RMD Header is displayed in current year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2018 RMD", true);
				// Checkbox validation

				if (Web.isWebElementDisplayed(RMDHeaderCheckBox) && RMDHeaderCheckBox.isSelected())
					Reporter.logEvent(
							Status.PASS,
							"Verify if CheckBox is displayed for current year",
							"Checkbox is dipalyed and selected by default in current year",
							false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if CheckBox is displayed for current year",
							"Checkbox is not displayed in current year", true);
				
				if(Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
				{
					if(this.RMDHeaderCheckBox.isSelected())
						this.RMDHeaderCheckBox.click();
				}

			}
		}
	}
	
	public void verifyHeaderDetailsCurrentYear(String currentYear)
	{
		if(txtIrsRule.getText().equalsIgnoreCase(Stock.GetParameterValue("txtIrsRule")))
			Reporter.logEvent(Status.PASS,
					"Verify IRS Rule text",
					"IRS Rule Text is matching", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify IRS Rule text",
					"IRS Rule Text is not matching", true);
		
		for (int i = 0; i < RMDWindow.size(); i++) {
			if(RMDWindow.size()>=2)
			{
				Reporter.logEvent(Status.FAIL,"Verify if only current year block is displayed",
						"Current year and Previous year block is displayed where it should be only Current year",
						true);
			}
			else
			{
				if (RMDHeader.get(i).getText().equalsIgnoreCase(currentYear+" RMD"))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Header for current year",
							"RMD Header is displayed in current year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2018 RMD", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Header for current year",
							"RMD Header is displayed in current year section. Expected: "
									+ RMDHeader.get(i).getText()
									+ " Actual: 2018 RMD", true);
				if (!Web.isWebElementDisplayed(RMDHeaderCheckBox))
					Reporter.logEvent(
							Status.PASS,
							"Verify if CheckBox is not displayed for current year",
							"Checkbox is not displayed in current year",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify if CheckBox is not displayed for current year",
							"Checkbox is dipalyed in current year", true);
			}
		}
	}
	
	public void verifyMandatoryText()
	{
		isTextFieldDisplayed("Remaining RMD");
		isTextFieldDisplayed("Estimated tax withholding");
		isTextFieldDisplayed("Delivery method");
	}
	
	public void verifyRMDDetails() throws SQLException
	{
		ArrayList<String> lstAmount = new ArrayList<String>();
		
		lstAmount=getRDMAmount(Stock.GetParameterValue("getMinDistrbutionAmount"), Stock.GetParameterValue("userName"),"min_distr_amt");
		
		
		for (int i = 0; i < RMDWindow.size(); i++) {
			if (txtRemainingRMD.get(i).getText()
					.equalsIgnoreCase("Remaining RMD"))
				Reporter.logEvent(Status.PASS, "Verify Remaining RMD Text ",
						"Remaining RMD Text Text is displayed. Expected: "
								+ txtRemainingRMD.get(i).getText()
								+ " Actual: Remaining RMD", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Remaining RMD Text ",
						"Remaining RMD Text Text is displayed. Expected: "
								+ txtRemainingRMD.get(i).getText()
								+ " Actual: Remaining RMD", true);
			
			if (String.valueOf(
					Web.getIntegerCurrency(txtRemainingRMDAmount.get(i)
							.getText())).equalsIgnoreCase(lstAmount.get(i)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Remaining RMD Amount",
						"Remaining RMD Amount is matched. Expected: "
								+ lstAmount.get(i)+ " Actual: "
								+ Web.getIntegerCurrency(txtRemainingRMDAmount
										.get(i).getText()), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Remaining RMD Amount",
						"Remaining RMD Amount did not match. Expected: "
								+ lstAmount.get(i)+ " Actual: "
								+ Web.getIntegerCurrency(txtRemainingRMDAmount
										.get(i).getText()), true);
			
			if(RMDWindow.size()==2 && i==0)
			{
				if(!Web.isWebElementDisplayed(lnkChangeAmount))
					Reporter.logEvent(
							Status.PASS,
							"Verify if Change amount link is not displayed for previous year",
							"Change amount link is not displayed in previous year",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify if Change amount link is not displayed for previous year",
							"Change amount link is displayed in previous year", true);
				
				isTextFieldDisplayed("2017 RMD amount");
			}
			else
			{
				
				if(Web.isWebElementDisplayed(lnkChangeAmount))
				{
					Reporter.logEvent(
							Status.PASS,
							"Verify if Change Amount Link is displayed",
							"Change Amount Link is displayed",
							false);
					Web.clickOnElement(lnkChangeAmount);
					Web.waitForPageToLoad(Web.getDriver());
					Common.waitForProgressBar();
					if(Web.isWebElementDisplayed(lblRequestAWithdrawal))
						Reporter.logEvent(Status.PASS,
								"Verify if Request WithDrawal page is displayed",
								"Request WithDrawal page is displayed", false);
					else
						Reporter.logEvent(Status.FAIL,
								"Verify if Request WithDrawal page is displayed",
								"Request WithDrawal page is not displayed", true);
					
					
					Web.getDriver().navigate().back();
					Web.waitForPageToLoad(Web.getDriver());
					Common.waitForProgressBar();
					isTextFieldDisplayed("required minimum distribution");
					isTextFieldDisplayed("2018 RMD amount");
				}
			}
			
			
			if (String.valueOf(
					Web.getIntegerCurrency(txtBalanceRMDAmount.get(i)
							.getText())).equalsIgnoreCase(lstAmount.get(i)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Remaining Balance RMD Amount",
						"Remaining Balance RMD Amount is matched. Expected: "
								+ lstAmount.get(i) + " Actual: "
								+ Web.getIntegerCurrency(txtBalanceRMDAmount
										.get(i).getText()), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Remaining Balance RMD Amount",
						"Remaining Balance RMD Amount did not match. Expected: "
								+ lstAmount.get(i) + " Actual: "
								+ Web.getIntegerCurrency(txtBalanceRMDAmount
										.get(i).getText()), true);
			// Balance remaining RMD calculation link
			
			if(Web.isWebElementDisplayed(lnkBalanceCalculationLink.get(i)))
				Reporter.logEvent(Status.PASS,
						"Verify if Balance remaining RMD calculation link is displayed",
						"Balance remaining RMD calculation link is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Balance remaining RMD calculation link is displayed",
						"Balance remaining RMD calculation link is not displayed", true);
			
			//Taken year-to-date text and amount
			isTextFieldDisplayed("Taken year-to-date");
			if (String.valueOf(
					Web.getIntegerCurrency(txtTakenRMDAmount.get(i)
							.getText())).contains("0"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Taken year-to-date Amount",
						"Taken year-to-date Amount matched. Expected: "
								+ "0" + " Actual: "
								+ Web.getIntegerCurrency(txtTakenRMDAmount
										.get(i).getText()), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Taken year-to-date Amount",
						"Taken year-to-date Amount did not match. Expected: "
								+ "0" + " Actual: "
								+ Web.getIntegerCurrency(txtTakenRMDAmount
										.get(i).getText()), true);
		}
	}
	
	public void verifyEstimatedTax()
	{
		for (int i = 0; i < RMDWindow.size(); i++) {
			if (txtWithholdingSec.get(i).getText()
					.equalsIgnoreCase("Estimated tax withholding"))
				Reporter.logEvent(Status.PASS,
						"Verify Estimated tax withholding Text ",
						"Estimated tax withholding Text is displayed for "+i+" Block. Expected: "
								+ txtWithholdingSec.get(i).getText()
								+ " Actual: Estimated tax withholding", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Estimated tax withholding Text ",
						"Estimated tax withholding Text is displayed for "+i+" Block. Expected: "
								+ txtWithholdingSec.get(i).getText()
								+ " Actual: Estimated tax withholding", true);
			
			if (txtDefaultFederal.get(i).getText()
					.equalsIgnoreCase("10%* federal"))
				Reporter.logEvent(Status.PASS,
						"By default Estimated tax withholding should be 10% Federal",
						"Estimated tax withholding by default is 10% for "+i+" Block.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"By default Estimated tax withholding should be 10% Federal",
						"Estimated tax withholding by default is not 10% for "+i+" Block.", true);
			
			if(Web.isWebElementDisplayed(lnkModifyWithholding))
				Reporter.logEvent(Status.PASS,
						"Verify if Modify Withholding link is displayed for "+i+" Block.",
						"Modify Withholding link is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Modify Withholding link is displayed for "+i+" Block.",
						"Modify Withholding link is not displayed", true);
			isTextFieldDisplayed("Applies to taxable amount only.");
		}
		
	}
	public void verifyDeliveryMethod(String... onlyGrace) throws SQLException
	{
		for (int i = 0; i < RMDWindow.size(); i++) {
			//System.out.println(i);
			if (txtPaymentMethod.get(i).getText()
					.equalsIgnoreCase("Delivery method"))
				Reporter.logEvent(Status.PASS, "Verify Delivery method Text ",
						"Delivery method Text is displayed. Expected: "
								+ txtPaymentMethod.get(i).getText()
								+ " Actual: Delivery method", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Delivery method Text ",
						"Delivery method Text is not displayed. Expected: "
								+ txtPaymentMethod.get(i).getText()
								+ " Actual: Delivery method", true);
			
			/*if (txtRegularMail.get(i).getText()
					.equalsIgnoreCase("Regular mail"))
				Reporter.logEvent(Status.PASS,
						"By default Regular Mail should be displayed",
						"Regular mail is displayed by default for "+i+" Block.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"By default Regular Mail should be displayed",
						"Regular mail is not displayed by default for "+i+" Block.", true);*/
			
			if(Web.isWebElementDisplayed(lnkChangeMethod.get(i)))
				Reporter.logEvent(Status.PASS,
						"Verify if Change method link is displayed for "+i+" Block.",
						"Change method link is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Change method link is displayed for "+i+" Block.",
						"Change method link is not displayed", true);
			
			verifySentToAddress();
			if(onlyGrace != null )
				break;
		}
	}
	
	public void validateHowIsThisCalculatedLink(String queryName, String userName) throws SQLException
	{
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String lastYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
		
		String priorYearBlan, calcAge, factor, tableUsed, actBalance;
		
		String[] sqlQueryHomeAddress = Stock.getTestQuery(queryName);
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		
		priorYearBlan = rs_Number.getString("prior_yr_end_bal");
		calcAge = rs_Number.getString("calc_age");
		factor = rs_Number.getString("factor");
		tableUsed = rs_Number.getString("table_used");
		actBalance = rs_Number.getString("min_distr_amt");
		
		if(Web.isWebElementDisplayed(lnkBalanceCalculation))
		{
			Reporter.logEvent(Status.PASS,
					"Verify if Balance remaining RMD calculation link is displayed",
					"Balance remaining RMD calculation link is displayed", false);
			lnkBalanceCalculation.click();
			isTextFieldDisplayed("How is the RMD calculated?");
			//isTextFieldDisplayed(Stock.GetParameterValue("txtBalanceRule"));
			
			isTextFieldDisplayed("Balance as of 12/31/"+lastYear+":");
			if (String.valueOf(Web.getIntegerCurrency(txtBalanceAsOfDollar.getText())).contains(priorYearBlan))
				Reporter.logEvent(Status.PASS,"Verify Prior Year balance Amount",
						"Prior Year balance Amount is matched. Expected: "
								+ Web.getIntegerCurrency(txtBalanceAsOfDollar.getText()) + " Actual: "
								+ priorYearBlan, false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Prior Year balance Amount",
						"Prior Year balance Amount is not matched. Expected: "
								+ Web.getIntegerCurrency(txtBalanceAsOfDollar.getText()) + " Actual: "
								+ priorYearBlan, true);
			
			isTextFieldDisplayed("Your age as of 12/31/"+year+":");
			if(txtYearsOld.getText().contains(calcAge))
				Reporter.logEvent(Status.PASS,
						"Verify if YearsOld Text is displayed",
						"Age is matching the DB. Expected: "+calcAge+
						" Actual:"+txtYearsOld.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if YearsOld Text is displayed",
						"Age is not matching the DB Values. Expected: "+calcAge+
						" Actual:"+txtYearsOld.getText(), true);
			
			isTextFieldDisplayed("Factor based on your age:");
			if(txtFactorAgeNumber.getText().equalsIgnoreCase(factor))
				Reporter.logEvent(Status.PASS,
						"Verify Factor based on your age value",
						"Factor based on your age is matching. Expected: "+factor+
						" Actual:"+txtFactorAgeNumber.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Factor based on your age value",
						"Factor based on your age is matching. Expected: "+factor+
						" Actual:"+txtFactorAgeNumber.getText(), true);
			
			isTextFieldDisplayed("IRS table used:");
			if(tableUsed.equalsIgnoreCase("A"))
				isTextFieldDisplayed("Uniform Lifetime");
			else if(tableUsed.equalsIgnoreCase("VI"))
				isTextFieldDisplayed("Joint Life and Last Survivor Expectancy");
			else
				isTextFieldDisplayed("Single Life Expectancy");
			
			isTextFieldDisplayed("Your RMD for "+year+":");
			if (String.valueOf(Web.getIntegerCurrency(txtRMDRemainingNumber
					.getText())).contains(actBalance))
				Reporter.logEvent(Status.PASS,"Verify Remaining Balance RMD Amount",
						"Remaining Balance RMD Amount is matched. Expected: "
								+ actBalance + " Actual: "
								+ Web.getIntegerCurrency(txtRMDRemainingNumber
										.getText()), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Remaining Balance RMD Amount",
						"Remaining Balance RMD Amount is not matched. Expected: "
								+ actBalance + " Actual: "
								+ Web.getIntegerCurrency(txtRMDRemainingNumber
										.getText()), true);
			Web.clickOnElement(lnkCloseModal);
			
		}
		
	}
	public void validateModifyWithholdingLink() throws InterruptedException
	{
		Actions keyBoard = new Actions(Web.getDriver());
		lnkModifyWithholding.click();
		isTextFieldDisplayed("Modify tax withholding");
		isTextFieldDisplayed("10% will be withheld from the taxable amount of your RMD unless you modify the amount using the slider below.");
		isTextFieldDisplayed("* Applies to taxable amount only.");
		isTextFieldDisplayed("Federal tax withholding:");
		this.lnkContributionRate.click();
		lib.Web.waitForElement(txtcontributionRateSlider);
		if(lnksliderValue.getText().equals(Stock.GetParameterValue("Contribution Rate")))
			Reporter.logEvent(Status.PASS,
					"Verify By default 10% is the Federal tax",
					"By default the federal tax is 10%", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify By default 10% is the Federal tax",
					"By default the federal tax is not 10%", true);
		
		
		lib.Web.setTextToTextBox(txtcontributionRateSlider, "0");
		
		txtcontributionRateSlider.sendKeys(Keys.TAB);
		//Web.clickOnElement(btnDone);
		//keyBoard.sendKeys(Keys.TAB).build().perform();
		//keyBoard.sendKeys(Keys.ENTER).build().perform();
		if(btnDone.isDisplayed())
		{
			//System.out.println("IN side IF");
			//Web.clickOnElement(btnDone);
			keyBoard.sendKeys(Keys.TAB).build().perform();
		}
		
		if(lnksliderValue.getText().equals("0"))
			Reporter.logEvent(Status.PASS,
					"Verify Participant should be able to edit the percentage to 0% as well",
					"0% is also selected", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Participant should be able to edit the percentage to 0% as well",
					"Participant is not able to select 0%", true);
		
		this.lnkContributionRate.click();
		lib.Web.waitForElement(txtcontributionRateSlider);
		lib.Web.setTextToTextBox(txtcontributionRateSlider, "100");
		//Web.clickOnElement(btnDone);
		txtcontributionRateSlider.sendKeys(Keys.TAB);
		if(btnDone.isDisplayed())
		{
			//Web.clickOnElement(btnDone);
			keyBoard.sendKeys(Keys.TAB).build().perform();
		}
		if(lnksliderValue.getText().equals("90"))
			Reporter.logEvent(Status.PASS,
					"Verify Participant should be able to edit the percentage only till 90%",
					"Even though the participant entered 100%, by default it went to 90%", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Participant should be able to edit the percentage only till 90%",
					"100% is also selected", true);
		
		this.lnkContributionRate.click();
		lib.Web.waitForElement(txtcontributionRateSlider);
		lib.Web.setTextToTextBox(txtcontributionRateSlider, "20");
		txtcontributionRateSlider.sendKeys(Keys.TAB);
		//Web.clickOnElement(btnDone);
		if(btnDone.isDisplayed())
		{
			Web.clickOnElement(btnDone);
			keyBoard.sendKeys(Keys.TAB).build().perform();
		}
		if(lnksliderValue.getText().equals("20"))
			Reporter.logEvent(Status.PASS,
					"Verify that Estimated federal withholding amount should get changed appropriately to match with the new percentage set.",
					"Changed the Federal tax withholding to: 20% and clicked cancel", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify that Estimated federal withholding amount should get changed appropriately to match with the new percentage set.",
					"Did not changed the Federal tax withholding to: 20%", true);
		
		this.lnkCancelTax.click();
		isTextFieldDisplayed("10%* federal");
		if(Web.isWebElementDisplayed(lnkModifyWithholding, true))
			lnkModifyWithholding.click();
		else
			Reporter.logEvent(Status.FAIL,
					"Verify tax withholding button",
					"tax withholding button is not displayed", true);
		
		this.lnkContributionRate.click();
		lib.Web.waitForElement(txtcontributionRateSlider);
		lib.Web.setTextToTextBox(txtcontributionRateSlider, Stock.GetParameterValue("New Contribution Rate"));
		//Web.clickOnElement(btnDone);
		txtcontributionRateSlider.sendKeys(Keys.TAB);
		if(btnDone.isDisplayed())
		{
			keyBoard.sendKeys(Keys.TAB).build().perform();
			//Web.clickOnElement(btnDone);
		}
		this.lnkUpdateTax.click();
		isTextFieldDisplayed(Stock.GetParameterValue("New Contribution Rate")+"%* federal");
		
	}
	public void validateChangeMethodLink(boolean isACH) throws SQLException, InterruptedException
	{
		
		String[] sqlQueryHomeAddress = Stock.getTestQuery("getExpeditedAmount");
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("planId"));
		rs_Number.first();
		
		String sExpeditedCharge = rs_Number.getString("AMOUNT");
		
		//System.out.println("@@"+sExpeditedCharge);
		for (int i = 0; i < RMDWindow.size(); i++) 
		{
			//System.out.println(i);
			Thread.sleep(2000);
			this.lnkChangeMethod.get(i).click();
			Thread.sleep(2000);
			/*if(this.btnRegaularMail.isSelected())
				Reporter.logEvent(Status.PASS,
						"Verify by default Regaular Mail is selected",
						"Regaular Mail is selected by default", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify by default Regaular Mail is selected",
						"Regaular Mail is not selected by default", true);*/
			Web.waitForElement(txtRegularMailFee);
			if(txtRegularMailFee.getText().equalsIgnoreCase("Free"))
				Reporter.logEvent(Status.PASS,
						"Regular Mail Fee should be Free",
						"Regaular Mail fee is Free, Fee Cost: "+txtRegularMailFee.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Regular Mail Fee should be Free",
						"Regaular Mail fee is not Free, Fee Cost: "+txtRegularMailFee.getText(), true);
			Thread.sleep(2000);
			isTextFieldDisplayed("Delivery up to 5 business days");
			if (String.valueOf(Web.getIntegerCurrency(txtExpeditedMailFee
							.getText())).contains(sExpeditedCharge))
				Reporter.logEvent(Status.PASS,
						"Expedited Mail Fee should be: "+sExpeditedCharge,
						"Expedited Mail Fee from UI: "+txtExpeditedMailFee.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Expedited Mail Fee should be: "+sExpeditedCharge,
						"Expedited Mail Fee from UI: "+txtExpeditedMailFee.getText(), true);
			
			this.btnExpeditedMail.click();
			isTextFieldDisplayed("Delivery up to 2 business days");
			this.lnkUpdateTax.click();
			isTextFieldDisplayed("Expedited mail");
			
			
			if (isACH) {
				String[] sqlACHAmount = Stock.getTestQuery("getACHAmount");
				sqlACHAmount[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				ResultSet rs_ACHAmount = DB.executeQuery(sqlACHAmount[0],
						sqlACHAmount[1]);
				rs_ACHAmount.first();
				String sdirectDepositFee = rs_ACHAmount.getString("AMOUNT");
				Thread.sleep(2000);
				this.lnkChangeMethod.get(i).click();
				isTextFieldDisplayed("Direct deposit");
				isTextFieldDisplayed("Delivery up to 3 business days");
				if (String.valueOf(
						Web.getIntegerCurrency(txtDirectDepositFee.getText()))
						.contains(sdirectDepositFee))
					Reporter.logEvent(Status.PASS, "Verify Direct Deposit Fee"
							, "Expedited Mail Fee Matched Expected: "+sdirectDepositFee
							+ " Actual: "+txtDirectDepositFee.getText(), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Direct Deposit Fee"
							, "Expedited Mail Fee did not Match Expected: "+sdirectDepositFee
							+ " Actual: "+txtDirectDepositFee.getText(), true);
				this.btnDirectDeposit.click();
				
				Select accountDrpDwn = new Select(drpDwnSelectAccount);
				String defaultItem = accountDrpDwn.getFirstSelectedOption().getText();
				
				if(defaultItem.equalsIgnoreCase("Select account"))
					Reporter.logEvent(Status.PASS, "Verify Direct Deposit Default selected account"
							, "Default selected account matched. Expected: "+defaultItem
							+ " Actual: "+accountDrpDwn.getFirstSelectedOption().getText(), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Direct Deposit Default selected account"
							, "Default selected account doesn't match. Expected: "+defaultItem
							+ " Actual: "+accountDrpDwn.getFirstSelectedOption().getText(), true);
				this.lnkUpdateTax.click();
				isTextFieldDisplayed("Bank account is required");
				Web.selectDropnDownOptionAsIndex(drpDwnSelectAccount, "1");
				this.lnkUpdateTax.click();
				isTextFieldDisplayed("Direct deposit");
			}
			//Not sure y written
			/*if(Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
			{
				if(this.RMDHeaderCheckBox.isSelected())
					this.RMDHeaderCheckBox.click();
			}*/
		}
		
	}
	
	public void validateInfoMessgae(String infoMessage, String infoXpath) throws Exception
	{
		
		WebElement ele = getWebElement(infoXpath);
		
		System.out.println(infoMessage);
		System.out.println(ele.getText());
		//if(ele.getText().equalsIgnoreCase(infoMessage))
		if(ele.getText().contains(infoMessage))
			Reporter.logEvent(Status.PASS,
					"Verify RMD Information Message",
					"RMD Information Message Matched Expected: "+infoMessage+" Actual: "+ele.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify RMD Information Message",
					"RMD Information Message did not Match Expected: "+infoMessage+" Actual: "+ele.getText(), true);
	}
	public void validateWaringMessageforGrace(String infoMessage,String infoGraceMessage) throws Exception
	{
		for(int i=0;i<txtInfoGracewarningMessage.size();i++)
		{
			
			if(txtInfoGracewarningMessage.size()==2 && i==0)
			{
				System.out.println("Grace From UI:"+txtInfoGracewarningMessage.get(i).getText());
				System.out.println("From Excel:"+infoGraceMessage);
				if(txtInfoGracewarningMessage.get(i).getText().contains(infoGraceMessage))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Information Message Expected: "+infoGraceMessage+" Actual: "+txtInfoGracewarningMessage.get(i).getText(),
							"RMD Information Message Matched", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Information Message Expected: "+infoGraceMessage+" Actual: "+txtInfoGracewarningMessage.get(i).getText(),
							"RMD Information Message not matched", true);
			}
			else
			{
				System.out.println("Current From UI:"+txtInfoGracewarningMessage.get(i).getText());
				System.out.println("From Excel:"+infoGraceMessage);
				if(txtInfoGracewarningMessage.get(i).getText().contains(infoMessage))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Information Message Expected: "+infoMessage+" Actual: "+txtInfoGracewarningMessage.get(i).getText(),
							"RMD Information Message Matched", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Information Message Expected: "+infoMessage+" Actual: "+txtInfoGracewarningMessage.get(i).getText(),
							"RMD Information Message not matched", true);
			}
		}
		
		
	}
	
	public void updateFields(String indId, String filedValue, String queryName) throws Exception
	{
		/*System.out.println(filedValue);
		System.out.println(queryName);*/
		int updateOwnership=0;
		String[] sqlQueryHomeAddress = Stock.getTestQuery(queryName);
		//System.out.println(sqlQueryHomeAddress[1]);
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		if(queryName.equalsIgnoreCase("updateRMDDate"))
			updateOwnership=DB.executeUpdate(sqlQueryHomeAddress[0],
			sqlQueryHomeAddress[1], filedValue,indId,Stock.GetParameterValue("planId"));
		else
			updateOwnership=DB.executeUpdate(sqlQueryHomeAddress[0],
					sqlQueryHomeAddress[1], filedValue,indId,gcId[0]);
		if(updateOwnership>0)
			Reporter.logEvent(Status.PASS,
				"Verify if updated the DataBase with proper fields",
				"Updated db with proper fields", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify if updated the DataBase with proper fields",
					queryName+" didn't get updated in DB", false);
		
	}
	public void getEmail_PhoneNumber()
	{
		try {
			String[] sqlQueryNumber = Stock.getTestQuery("getPhoneNumber");
			String[] sqlQueryEmail = Stock.getTestQuery("getEmailId");
			sqlQueryNumber[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			sqlQueryEmail[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs_Number = DB.executeQuery(sqlQueryNumber[0],
					sqlQueryNumber[1], Stock.GetParameterValue("userName"));
			rs_Number.first();
			String sMobileAreaCode = rs_Number
					.getString("mobile_phone_area_code");
			String sMobileNbr = rs_Number.getString("mobile_phone_nbr");
			String sPhoneNum = sMobileAreaCode+""+sMobileNbr;
			sFianlPhoneNum=sPhoneNum.replaceAll("[-+.^:,]","");
			ResultSet rs_Email = DB.executeQuery(sqlQueryEmail[0],
					sqlQueryEmail[1], Stock.GetParameterValue("userName"));
			rs_Email.first();
			sEmail = rs_Email.getString("email_address");

			System.out
					.println("From DataBase: " + sEmail + " and " + sFianlPhoneNum);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void selectRMDBy(String option, String deliveryMethod,String... graceDeliveryMethod) {
		try {

			getEmail_PhoneNumber();
			for (int i = 0; i < RMDWindow.size(); i++) 
			{
				if (graceDeliveryMethod.length != 0 && i==0)
				{
					
					this.lnkChangeMethod.get(i).click();
					Thread.sleep(2000);
					if (Stock.GetParameterValue("selectGraceDeliveryMethod").equalsIgnoreCase("Regular Mail"))
						this.btnRegaularMail.click();
					else if (Stock.GetParameterValue("selectGraceDeliveryMethod").equalsIgnoreCase("Expedited Mail"))
						this.btnExpeditedMail.click();
					else
					{
						
						Web.isWebElementDisplayed(btnDirectDeposit, true);
						this.btnDirectDeposit.click();
						//Select accountDrpDwn = new Select(drpDwnSelectAccount);
						Web.selectDropnDownOptionAsIndex(drpDwnSelectAccount, "1");
					}
					this.lnkUpdateTax.click();
				}
				else
				{
					
					this.lnkChangeMethod.get(i).click();
					Thread.sleep(2000);
					if (deliveryMethod.equalsIgnoreCase("Regular Mail"))
						this.btnRegaularMail.click();
					else if (deliveryMethod.equalsIgnoreCase("Expedited Mail"))
						this.btnExpeditedMail.click();
					else
					{
						this.btnDirectDeposit.click();
						//Select accountDrpDwn = new Select(drpDwnSelectAccount);
						Web.selectDropnDownOptionAsIndex(drpDwnSelectAccount, "1");
					}
					this.lnkUpdateTax.click();
				}
				//To break the loop for RMD only with Grace
				if(Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
					break;
				
			}
			//Thread.sleep(10000);
			
			
			
			if(option.equalsIgnoreCase("No"))
			{
				if(!Web.isWebElementDisplayed(dropDownStatus))
					Reporter.logEvent(Status.PASS,
							"Verify proactive notification is not set up for the particpants plan",
							"Proactive notification is not set up for the particpants plan", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify proactive notification is not set up for the particpants plan",
							"Proactive notification is set up for the particpants plan", true);
			}
			else
			{
				
				if (!this.chkBoxStatus.isSelected())
					chkBoxStatus.click();
				Thread.sleep(1000);
				Web.selectDropDownOption(dropDownStatus, option);
				if (option.equalsIgnoreCase("Email")) {

					if (txtStatus.getAttribute("value").isEmpty()) {
						Web.setTextToTextBox(txtStatus,
								Stock.GetParameterValue("emailID"));
						sEmail = Stock.GetParameterValue("emailID");
					}

				} else if (option.equalsIgnoreCase("Text message")) {

					if (txtPhoneStatus.getAttribute("value").isEmpty()) {
						txtPhoneStatus.sendKeys(Stock.GetParameterValue("phone"));
						sFianlPhoneNum = Stock.GetParameterValue("phone");
					}

				} else {
					if (txtStatus.getAttribute("value").isEmpty()) {
						Web.setTextToTextBox(txtStatus,
								Stock.GetParameterValue("emailID"));
						sEmail = Stock.GetParameterValue("emailID");
					}
					if (txtPhoneStatus.getAttribute("value").isEmpty()) {
						txtPhoneStatus.sendKeys(Stock.GetParameterValue("phone"));
						sFianlPhoneNum = Stock.GetParameterValue("phone");
					}
				}
				Thread.sleep(2000);
				this.btnRequestRMD.click();
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void citizenShipValidation(String SSN){
		try {
			
			if(Web.isWebElementDisplayed(btnRequestRMD, false))
			{
				this.btnRequestRMD.click();
			}
			isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			Web.waitForElement(inpYes);
			Thread.sleep(2000);
			Web.clickOnElement(inpYes);
			Thread.sleep(2000);
			if (isTextFieldDisplayed("Please enter your Social Security number.")) {
				enterSSN(SSN);
				Reporter.logEvent(Status.PASS,
						"Verify if user enters Social Security number.",
						"User enters Social Security number " + SSN
								+ " in the field which is displayed", false);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify if user enters Social Security number.",
						"User enters Social Security number " + SSN
								+ " in the field which is displayed", true);

			// Click on Confirm and Continue
			if (Web.isWebElementDisplayed(btnConfirmContinue)) {
				Web.clickOnElement(btnConfirmContinue);
				Thread.sleep(2000);
				Common.waitForProgressBar();	
			
			}

			else
				throw new Error("'Confirm and Continue' is not displayed");
		} catch (NoSuchElementException e) {
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL,
					"Element is not displayed in the Application", msg, true);
		} catch (Exception e) {
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		}
	}
	
	public void enterSSN(String ssn) {		
		try {
				
				if(Web.isWebElementDisplayed(this.inputSSN, true))
				{
					//Robot robot=new Robot();				
					Web.setTextToTextBox(inputSSN, ssn);			
					//keyBoardEvent.sendKeys(Keys.TAB).build().perform();
					Thread.sleep(1000);
					//Web.clickOnElement(btnConfirmContinue);
							
					Reporter.logEvent(Status.INFO, "Entering SSN "
							+ ssn, "Entered SSN: " + ssn, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void validateConfirmationPage_RequestFailed(String lastYear,String currentYear)
	{
		Common.waitForProgressBar();
		
		if(isTextFieldDisplayed("RMD Request Received"))
			isTextFieldDisplayed("We were unable to process part of your request. Please see below for details.");
		
		//1st Step
		isTextFieldDisplayed("Your "+lastYear+" RMD request received");
		isTextFieldDisplayed("Your "+currentYear+" RMD request failed");
	}
	public void validateConfirmationPage_RMDConfirmationNumber()
	{
		Common.waitForProgressBar();
		
		if(isTextFieldDisplayed("RMD Request Received"))
			isTextFieldDisplayed("Your RMD request has been received and is being processed.");
		
		//1st Step
		isTextFieldDisplayed("RMD request received");
		isConfirmationNumDisplayed();
		if (!Stock.GetParameterValue("selectDeliveryMethod")
				.equalsIgnoreCase("no"))
		{
			/*validateCurrentTimeStamp();*/
		}
			
		isTextFieldDisplayed("Complete");
	}
	public void isConfirmationNumDisplayed()
	{
		
		int isConfirmationNumDisplayed=0;
		int isGraceNumDisplayed=0;
		try {	
			String[] sqlQuery = Stock.getTestQuery("getRMDWithdrawalConfirmationNumber");
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) 
					+ "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			for(int i=0;i<txtConfirmationNumber.size();i++)
			{
				if(txtConfirmationNumber.size()==2 && i==0)
				{
					prevConfirmationNo=txtConfirmationNumber.get(i).getText();
				}
				else
				{
					confirmationNo=txtConfirmationNumber.get(i).getText();
				}
				if(Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
					break;
			}
			System.out.println(prevConfirmationNo+" "+confirmationNo);
			
			if(!Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
			{
				ResultSet getWithdrawalConfRs=DB.executeQuery(sqlQuery[0],sqlQuery[1],confirmationNo);
				isConfirmationNumDisplayed = DB.getRecordSetCount(getWithdrawalConfRs);
				if (isConfirmationNumDisplayed > 0) 
					Reporter.logEvent(
							Status.PASS,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"+confirmationNo,false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation is not Populated in the DataBase",true);
			}
			
			if(txtConfirmationNumber.size()==2)
			{
				ResultSet getGraceWithdrawalConfRs=DB.executeQuery(sqlQuery[0],sqlQuery[1],prevConfirmationNo);
				isGraceNumDisplayed = DB.getRecordSetCount(getGraceWithdrawalConfRs);
				
				if (isGraceNumDisplayed > 0) 
					Reporter.logEvent(
							Status.PASS,
							"Verify Grace Request Confirmation Number is Populated in the DataBase",
							"Grace Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"+prevConfirmationNo,false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Grace Request Confirmation Number is Populated in the DataBase",
							"Grace Request Confirmation is not Populated in the DataBase",true);
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void validateCurrentTimeStamp()
	{
		Date date = new Date();
		DateFormat time = new SimpleDateFormat("M/d/yyyy");
		//time.setTimeZone(TimeZone.getTimeZone("MST"));
		String sDateTime = txtCurrentTimeStapm.getText().substring(0, 9);
		
		
		if(sDateTime.contains(time.format(date)))
			Reporter.logEvent(
					Status.PASS,
					"Verify Request Confirmation time stamp.",
					"RMD Request Time matched Expected: "+time.format(date)+" Actual: "+sDateTime,false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Request Confirmation time stamp.",
					"RMD Request Time did not match Expected: "+time.format(date)+" Actual: "+sDateTime,true);
	}
	public void validateConfirmationPage_RMDProcessing()
	{
		isTextFieldDisplayed("Processing");
		isTextFieldDisplayed("Typically 1 business day.");
	}
	public void validateConfirmationPage_RMDPaymentType(String sDeliveryType, String... sGraceDeliveryType)
	{
		String sFinalDelivery;
		for(int i=0;i<fulltxtPaymentType.size();i++)
		{
			if(fulltxtPaymentType.size()==2 && i==0)
			{
				sFinalDelivery=Stock.GetParameterValue("selectGraceDeliveryMethod");
			}
			else
			{
				sFinalDelivery=sDeliveryType;
			}
			System.out.println(fulltxtPaymentType.size());
			if(sFinalDelivery.equalsIgnoreCase("Regular Mail"))
			{
				if(fulltxtPaymentType.get(i).getText().equalsIgnoreCase("Payment sent by "+sFinalDelivery))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Payment Type.",
							"RMD Payment Type Matched  Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Payment Type.",
							"RMD Payment Type Matched  Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), true);
				
				isTextFieldDisplayed("Delivery can be up to 5 days.");
			}
			else if(sFinalDelivery.equalsIgnoreCase("expedited mail"))
			{
				if(fulltxtPaymentType.get(i).getText().equalsIgnoreCase("Payment sent by "+sFinalDelivery))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Payment Type",
							"RMD Payment Type Matched. Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Payment Type",
							"RMD Payment Type Matched. Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), true);
				
				isTextFieldDisplayed("Delivery can be up to 2 days.");
			}
			else if(sFinalDelivery.equalsIgnoreCase("direct deposit"))
			{
				if(fulltxtPaymentType.get(i).getText().equalsIgnoreCase("Payment sent by "+sFinalDelivery))
					Reporter.logEvent(Status.PASS,
							"Verify RMD Payment Type",
							"RMD Payment Type Matched. Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify RMD Payment Type",
							"RMD Payment Type Matched. Expected: "+"Payment sent by "+sFinalDelivery+" Actual: "+fulltxtPaymentType.get(i).getText(), true);
				
				isTextFieldDisplayed("Delivery can be up to 3 days.");
			}
			if(Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no"))
				break;
		}
		
	}

	public void validateProactiveNotification(String option)
	{
		if(option.equalsIgnoreCase("Email"))
		{
			if(txtProactiveNotification.getText().equalsIgnoreCase("You will receive status updates at "+sEmail+"."))
				Reporter.logEvent(Status.PASS,
				"Verify Proactive Notification Text",
				"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sEmail+"."+" Actual: "+txtProactiveNotification.getText(),false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Proactive Notification Text",
						"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sEmail+"."+" Actual: "+txtProactiveNotification.getText(),true);
		}
		else if(option.equalsIgnoreCase("Text Message"))
		{
			if(txtProactiveNotification.getText().equalsIgnoreCase("You will receive status updates at "+sFianlPhoneNum+"."))
				Reporter.logEvent(Status.PASS,
				"Verify Proactive Notification Text",
				"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sFianlPhoneNum+"."+" Actual: "+txtProactiveNotification.getText(),false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Proactive Notification Text",
						"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sFianlPhoneNum+"."+" Actual: "+txtProactiveNotification.getText(),true);
		}
		else if(option.equalsIgnoreCase("Both"))
		{
			if(txtProactiveNotification.getText().equalsIgnoreCase("You will receive status updates at "+sEmail+" and "+sFianlPhoneNum+"."))
				Reporter.logEvent(Status.PASS,
				"Verify Proactive Notification Text",
				"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sEmail+" and "+sFianlPhoneNum+"."+" Actual: "+txtProactiveNotification.getText(),false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Proactive Notification Text",
						"Proactive Notification Text matched. Expected: "+"You will receive status updates at "+sEmail+" and "+sFianlPhoneNum+"."+" Actual: "+txtProactiveNotification.getText(),true);
		}
		else if(option.equalsIgnoreCase("No"))
		{
			if(!Web.isWebElementDisplayed(txtProactiveNotification, false))
				Reporter.logEvent(Status.PASS,
				"Verify Proactive Notification Text is not visible",
				"Proactive Notification Text is not visible",false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Proactive Notification Text is not visible",
						"Proactive Notification Text is displayed",true);
		}

	}
	
	public String getAllConfirmationAmount(String sColoumnName) throws SQLException
	{
		String sRequestedAmount;
		String[] sqlidEvent = Stock.getTestQuery("getIdFromEvent");
		sqlidEvent[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_NumberId = DB.executeQuery(sqlidEvent[0],
				sqlidEvent[1], confirmationNo);
		rs_NumberId.first();
		String sFinalConfirmationNumber = rs_NumberId.getString("ID");
		/*String sFinalConfirmationNumber = String.valueOf(Integer.parseInt(confirmationNo)+1);*/
		String[] sqlAmount = Stock.getTestQuery("getAllConfirmationAmount");
		sqlAmount[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlAmount[0],
				sqlAmount[1], sFinalConfirmationNumber,sColoumnName);
		rs_Number.first();
		sRequestedAmount = rs_Number.getString("Final");
		return sRequestedAmount;
	}
	public String getAllGraceConfirmationAmount(String sColoumnName) throws SQLException
	{
		String sRequestedAmount;
		String[] sqlidEvent = Stock.getTestQuery("getIdFromEvent");
		sqlidEvent[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_NumberId = DB.executeQuery(sqlidEvent[0],
				sqlidEvent[1], prevConfirmationNo);
		rs_NumberId.first();
		String sFinalConfirmationNumber = rs_NumberId.getString("ID");
		/*String sFinalConfirmationNumber = String.valueOf(Integer.parseInt(prevConfirmationNo)+1);*/
		String[] sqlAmount = Stock.getTestQuery("getAllConfirmationAmount");
		sqlAmount[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlAmount[0],
				sqlAmount[1], sFinalConfirmationNumber,sColoumnName);
		rs_Number.first();
		sRequestedAmount = rs_Number.getString("Final");
		return sRequestedAmount;
	}
	
	public void validateConfirmationPage_FinalAmount(String userName) {
		try {
			int j = 0;
			ArrayList<String> lstAmount = new ArrayList<String>();
			DecimalFormat f = new DecimalFormat(".##");
			String sGraceFT = null, sGraceFA = null, sGraceST = null, sFederalTax = null, sFinalAmount = null, sStateTax = null;
			// double finalA;
			String[] sqlRMDAmount = Stock.getTestQuery("getMinDistrAmt");
			sqlRMDAmount[0] = Common.getParticipantDBName(userName) + "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs_Number = DB.executeQuery(sqlRMDAmount[0],
					sqlRMDAmount[1], Stock.GetParameterValue("userName"));

			while (rs_Number.next()) {
				lstAmount.add(j, rs_Number.getString("min_distr_amt"));
				j++;
			}
			System.out.println("Size of RMD Amount from DB" + lstAmount.size());
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("M/d/yyyy");
			time.setTimeZone(TimeZone.getTimeZone("MST"));
			String sDateTime = confirmationPageProcessingDate.get(0).getText();

			if (!Stock.GetParameterValue("selectDeliveryMethod")
					.equalsIgnoreCase("no")) {
				sFederalTax = getAllConfirmationAmount("INTERNAL REVENUE SERVICE WITHHOLDING");
				sFinalAmount = getAllConfirmationAmount("CHECK AMOUNT");
				// sStateTax = getAllConfirmationAmount("MAINE WITHHOLDING");
			}
			if (txtConfirmationNumber.size() == 2) {
				sGraceFT = getAllGraceConfirmationAmount("INTERNAL REVENUE SERVICE WITHHOLDING");
				sGraceFA = getAllGraceConfirmationAmount("CHECK AMOUNT");
				// sGraceST =
				// getAllGraceConfirmationAmount("MAINE WITHHOLDING");
			}

			// Verify Processing date
			/*if (sDateTime.contains(time.format(date)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Request Confirmation Processing Date.",
						"Processing Date matched. Expected: "
								+ time.format(date) + " Actual: " + sDateTime,
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request Confirmation Processing Date.",
						"Processing Date did not match. Expected: "
								+ time.format(date) + " Actual: " + sDateTime,
						true);*/

			for (int i = 0; i < txtConfirmationNumber.size(); i++) {
				// Verify Grace and current RMD Amount
				if (String.valueOf(
						Web.getIntegerCurrency(confirmationPageRMDAmount.get(i)
								.getText())).equalsIgnoreCase(lstAmount.get(i)))
					Reporter.logEvent(
							Status.PASS,
							"Verify Grace and Current RMD Amount in confirmation page",
							"RMD Amount matched. Actual: "
									+ Web.getIntegerCurrency(confirmationPageRMDAmount
											.get(i).getText()) + " Expected: "
									+ lstAmount.get(i), false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify RMD Amount in confirmation page",
							"RMD Amount is not matching. Actual: "
									+ Web.getIntegerCurrency(confirmationPageRMDAmount
											.get(i).getText()) + " Expected: "
									+ lstAmount.get(i), true);

				if (txtConfirmationNumber.size() == 2 && i == 0) {
					// Verify Grace Federal Tax
					validateConfirmationPageAmounts(i,sGraceFT,sGraceFA,
							Stock.GetParameterValue("selectGraceDeliveryMethod"),
							"grace");

				} else {
					// Verify Current Federal Tax
					System.out.println("From DB the total Federal amount:"+sFederalTax+" Final: "+sFinalAmount);
					validateConfirmationPageAmounts(i, sFederalTax,	sFinalAmount,
							Stock.GetParameterValue("selectDeliveryMethod"),
							"current");
					
				}
				if (Stock.GetParameterValue("selectDeliveryMethod")
						.equalsIgnoreCase("no"))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateConfirmationPageAmounts(int i,String sFedralTax,String sFinalAmount,
			String sDeliverMethod,String sRMDType) throws SQLException
	{
		if(String.valueOf(
				Web.getIntegerCurrency(confirmationPageFederalTaxAmount.get(i).getText())).equalsIgnoreCase(sFedralTax))
			Reporter.logEvent(
					Status.PASS,
					"Verify "+sRMDType+" Federal Tax in Confirmation Page.",
					"Grace Federal Tax matched. Expected: "+sFedralTax+" Actual: "+String.valueOf(Web.getIntegerCurrency(confirmationPageFederalTaxAmount.get(i).getText())),false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify "+sRMDType+" Federal Tax in Confirmation Page.",
					"Grace Federal Tax did not match. Expected: "+sFedralTax+" Actual: "+String.valueOf(Web.getIntegerCurrency(confirmationPageFederalTaxAmount.get(i).getText())),true);
		
		
		if (String.valueOf(
				Web.getIntegerCurrency(confirmationPagePaymentAmount.get(i).getText())).contains(sFinalAmount))
			Reporter.logEvent(
					Status.PASS,
					"Verify "+sRMDType+" Final payment Amount",
					sRMDType+" Final payment Amount matched. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.get(i).getText()) +
					" Expected: "+ sFinalAmount, false);
		else
			Reporter.logEvent(
					Status.FAIL,
					sRMDType+" Verify Final payment Amount",
					sRMDType+" Final payment Amount did not match. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.get(i).getText()) +
					" Expected: "+ sFinalAmount,  true);
		//Verify Grace Delivery Method
		if(confirmationPageDeliveryMethod.get(i).getText().equalsIgnoreCase(sDeliverMethod))
			Reporter.logEvent(
					Status.PASS,
					"Verify "+sRMDType+" Delivery Method in Confirmation Page.",
					sRMDType+" Delivery Method matched. Expected: "+sDeliverMethod+" Actual: "+confirmationPageDeliveryMethod.get(i).getText(),false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify "+sRMDType+" Delivery Method in Confirmation Page.",
					sRMDType+" Delivery Method did not match. Expected: "+sDeliverMethod+" Actual: "+confirmationPageDeliveryMethod.get(i).getText(),true);
		//Verify Grace Delivery Charge
		if(!sDeliverMethod.equalsIgnoreCase("regular mail"))
		{
			try{
				if(Web.isWebElementDisplayed(confirmationPageDeliveryFee.get(i)))
					validateDeliveryAmount(sDeliverMethod,i,sRMDType);
			}
			catch(IndexOutOfBoundsException e)
			{
				if(Web.isWebElementDisplayed(confirmationPageDeliveryFee.get(0)))
					validateDeliveryAmount(sDeliverMethod,0,sRMDType);
			}
			
		}
		//Verify Grace Sent To
		Reporter.logEvent(Status.INFO, "Verify the Grace Sent To Address in the Confirmation Page", 
				"For Grace year RMD, The Address is displayed as:\n"+
			"Address: "+confirmationPageSentToAddress.get(i).getText().trim(), false);
	}
	public void validateDeliveryAmount(String sDeliverMethod,int itr, String sRMDType)
			throws SQLException {
		DecimalFormat f = new DecimalFormat(".##");
		String sExpeditedCharge=null, sACHCharge=null;
		if(sRMDType.equalsIgnoreCase("current") && (!Stock.GetParameterValue("selectDeliveryMethod").equalsIgnoreCase("no")))
		{
			 sExpeditedCharge = getAllConfirmationAmount("OVERNIGHT DELIVERY CHARGE");
			 sACHCharge = getAllConfirmationAmount("ACH CHARGE");
		}
		else
		{
			 sExpeditedCharge = getAllGraceConfirmationAmount("OVERNIGHT DELIVERY CHARGE");
			 sACHCharge = getAllGraceConfirmationAmount("ACH CHARGE");
		}
		
		System.out.println(sExpeditedCharge+""+sACHCharge);
		System.out.println(String.valueOf(Math.round(Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr).getText()))));
		if (sDeliverMethod.equalsIgnoreCase("Expedited Mail")) {
			if (String.valueOf(Math.round(Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr).getText())))
					.equalsIgnoreCase(sExpeditedCharge))
				Reporter.logEvent(
						Status.PASS,
						"Verify Expedited Charge",
						"Expedited Charge matched. Actual: "
								+ Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr)
										.getText()) + " Expected: "
								+ sExpeditedCharge, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Expedited Charge",
						"Expedited Charge did not match. Actual: "
								+ Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr)
										.getText()) + " Expected: "
								+ sExpeditedCharge, true);
		}
		else if (sDeliverMethod.equalsIgnoreCase("Direct deposit")) {
			if (String.valueOf(Math.round(Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr).getText())))
					.equalsIgnoreCase(sACHCharge))
				Reporter.logEvent(
						Status.PASS,
						"Verify Direct Mail Fee",
						"Direct Mail Fee matched. Actual: "
								+ Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr)
										.getText()) + " Expected: "
								+ sACHCharge, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Direct Mail Fee",
						"Direct Mail Fee did not match. Actual: "
								+ Web.getIntegerCurrency(confirmationPageDeliveryFee.get(itr)
										.getText()) + " Expected: "
								+ sACHCharge, true);
		}
	}
	public void Verify_confirmation_page_content()
	{
		Common.waitForProgressBar();
		isTextFieldDisplayed("RMD Request Received");
		isTextFieldDisplayed("Confirmation");
		isTextFieldDisplayed("2018 RMD");
		isTextFieldDisplayed("Processing date");
		isTextFieldDisplayed("RMD Amount");
		isTextFieldDisplayed("Federal Tax Withholding");
		//isTextFieldDisplayed("State Tax Withholding");
		isTextFieldDisplayed("Delivery Method");
		isTextFieldDisplayed("Sent to");
		isTextFieldDisplayed("Payment amount");
	}

	public void validateEventTable(String option, String userName) {
		try {
			int sEv_id = Integer.parseInt(confirmationNo) + 1;
			//System.out.println(sEv_id);
			String[] sqlquery = Stock.getTestQuery("getRMDEmailAndPhoneNumber");
			sqlquery[0] = Common.getParticipantDBName(userName) + "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs_Number = DB.executeQuery(sqlquery[0], sqlquery[1],
					String.valueOf(sEv_id));

			if (option.equalsIgnoreCase("Email")) {
				rs_Number.first();
				String sRMDEmail = rs_Number.getString("email_address");

				if (sRMDEmail.equalsIgnoreCase(sEmail))
					Reporter.logEvent(Status.PASS,
							"Verify Event table Email value",
							"Event table Email filed matched. Expected: " + sEmail
									+ " Actual: " + sRMDEmail, false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Event table Email value",
							"Event table Email filed did not match. Expected: " + sEmail
									+ " Actual: " + sRMDEmail, true);
			}
			else if(option.equalsIgnoreCase("Text Message")) {
				rs_Number.first();
				String sMobileAreaCode = rs_Number.getString("phone_area_code");
				String sMobileNbr = rs_Number.getString("phone_nbr");
				String sPhoneNum = sMobileAreaCode+""+sMobileNbr;

				if (sPhoneNum.equalsIgnoreCase(sFianlPhoneNum))
					Reporter.logEvent(Status.PASS,
							"Verify Event table Phone Number",
							"Event table Phone Number matched. Expected: " + sPhoneNum
									+ " Actual: " + sFianlPhoneNum, false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Event table Phone Number",
							"Event table Phone Number di not match. Expected: " + sPhoneNum
									+ " Actual: " + sFianlPhoneNum, true);
			}
			else
			{
				int j=0;
				ArrayList<String> lstEmail = new ArrayList<String>();
				ArrayList<String> lstAreaCode = new ArrayList<String>();
				ArrayList<String> lstMobilenbr = new ArrayList<String>();
				while(rs_Number.next())
				{
					lstEmail.add(j, rs_Number.getString("email_address"));
					lstAreaCode.add(j, rs_Number.getString("phone_area_code"));
					lstMobilenbr.add(j, rs_Number.getString("phone_nbr"));
					j++;
				}
				String sRMDEmail=lstEmail.get(0);
				String sMobileAreaCode=lstAreaCode.get(1);
				String sMobileNbr=lstMobilenbr.get(1);
				String sPhoneNum = sMobileAreaCode+""+sMobileNbr;
				if (sRMDEmail.equalsIgnoreCase(sEmail))
					Reporter.logEvent(Status.PASS,
							"Verify Event table Email value",
							"Event table Email filed matched. Expected: " + sEmail
									+ " Actual: " + sRMDEmail, false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Event table Email value",
							"Event table Email filed did not match. Expected: " + sEmail
									+ " Actual: " + sRMDEmail, true);
				if (sPhoneNum.equalsIgnoreCase(sFianlPhoneNum))
					Reporter.logEvent(Status.PASS,
							"Verify Event table Phone Number",
							"Event table Phone Number matched. Expected: " + sPhoneNum
									+ " Actual: " + sFianlPhoneNum, false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Event table Phone Number",
							"Event table Phone Number di not match. Expected: " + sPhoneNum
									+ " Actual: " + sFianlPhoneNum, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void validateAlertText(String expectedText)
	{
		String actualText=txtAlertConsentText.getText();
		System.out.println("From UI:"+actualText);
		System.out.println("From XL:"+expectedText);
		if(actualText.equalsIgnoreCase(expectedText))
			Reporter.logEvent(Status.PASS,
					"Verify alert text is displayed",
					"Alert text is displayed and matched. Expected:"+expectedText+"Actual Text"+actualText, false);
		 else 
			Reporter.logEvent(Status.FAIL,
					"Verify alert text is displayed",
					"Alert text is not matched. Expected:"+expectedText+"Actual Text"+actualText, true);
	}
	public void validatePSRText(String psrText,String emailToForm)
	{
		if(Web.isWebElementDisplayed(emailFormInputBox))
			Web.setTextToTextBox(emailFormInputBox, "discard@gwg.com");
			//emailFormInputBox.sendKeys("discard@gwg.com");
		Web.clickOnElement(emailFormButton);
		Common.waitForProgressBar();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		System.out.println("From UI:"+txtPSRNumber.getText());
		System.out.println("From XL:"+psrText);
		//if(txtPSRNumber.getText().equalsIgnoreCase(psrText))
		if(txtPSRNumber.getText().contains(psrText))
			Reporter.logEvent(Status.PASS,
					"Verify Participant Services number in confirmation page",
					"Participant Services number is matched. Expected:"+psrText+"Actual Text:"+txtPSRNumber.getText(), false);
		 else 
			Reporter.logEvent(Status.FAIL,
					"Verify Participant Services number in confirmation page",
					"Participant Services number did not matched. Expected:"+psrText+"Actual Text:"+txtPSRNumber.getText(), true);
		
		if(txtEmailFormTo.getText().equalsIgnoreCase(emailToForm))
			Reporter.logEvent(Status.PASS,
					"Verify Email To Form Text in confirmation page",
					"Email To Form Text is matched. Expected:"+emailToForm+"Actual Text"+txtEmailFormTo.getText(), false);
		 else 
			Reporter.logEvent(Status.FAIL,
					"Verify Email To Form Text in confirmation page",
					"Email To Form Text did not matched. Expected:"+emailToForm+"Actual Text"+txtEmailFormTo.getText(), true);
	}
	public void ssnValidation(String SSN,String isCitizen)
	{
		try {	
			
		isTextFieldDisplayed("Are you a U.S. citizen or resident?");
		Web.waitForElement(inpYes);
		Thread.sleep(2000);
		Web.clickOnElement(inpYes);		
		Thread.sleep(2000);
		
		if(Web.isWebElementDisplayed(this.inputSSN, true))
		
			Reporter.logEvent(
					Status.PASS,
					"Verify upon selecting Yes",
					"SSN Textbox is visibled", false);
			else
			Reporter.logEvent(
					Status.FAIL,
					"Verify upon selecting Yes",
					"SSN Textbox is not visibled", true);
		
		if (isTextFieldDisplayed("Please enter your Social Security number.")) 
			//enterSSN(SSN);
			Reporter.logEvent(
					Status.PASS,
					"Verify upon selecting Yes",
					"Please enter your Social Security number message is displayed", false);
		 else
			 Reporter.logEvent(
						Status.FAIL,
						"Verify upon selecting Yes",
						"Please enter your Social Security number message is not displayed", true);

		// Click on Confirm and Continue
		if(!btnConfirmContinue.isEnabled())
			Reporter.logEvent(
					Status.PASS,
					"Verify upon selecting Yes",
					"Confirm and Continue button is disabled", false);
		 else
			 Reporter.logEvent(
						Status.FAIL,
						"Verify upon selecting Yes",
						"Confirm and Continue button is enabled by default", true);
		
		enterSSN(SSN);
		
		if(Stock.GetParameterValue("SSN").equalsIgnoreCase(SSN))
		{
			Thread.sleep(2000);
			
			
			if (Web.isWebElementDisplayed(btnConfirmContinue)) {
				Web.clickOnElement(btnConfirmContinue);	
				Thread.sleep(2000);
			}
			
			
			else
				throw new Error("'Confirm and Continue' is not displayed");	
		}
		
		
		
		if(Stock.GetParameterValue("wrongSSN").equalsIgnoreCase(SSN))
		{
			
			Thread.sleep(1000);
		
			//if(ssnErrorMsg.getText().equalsIgnoreCase("Please enter a 9-digit number."))
			if(isTextFieldDisplayed("Please enter a 9-digit number."))
				Reporter.logEvent(
						Status.PASS,
						"Verify SSN Error message",
						"SSN Error message is matching", false);
			 else
				 Reporter.logEvent(
							Status.FAIL,
							"Verify SSN Error message",
							"SSN Error message is not matching", true);
			
			if(!btnConfirmContinue.isEnabled())
				Reporter.logEvent(
						Status.PASS,
						"Verify upon selecting Yes",
						"Confirm and Continue button is disabled", false);
			 else
				 Reporter.logEvent(
							Status.FAIL,
							"Verify upon selecting Yes",
							"Confirm and Continue button is enabled by default", true);
		}
		if(isCitizen.equalsIgnoreCase("No"))
		{
			Web.waitForElement(inpNo);
			Thread.sleep(2000);
			Web.clickOnElement(inpNo);
			if(!inpNo.isSelected())
				Web.clickOnElement(inpNo);
			Thread.sleep(2000);
			isTextFieldDisplayed("If you are not a U.S. citizen or U.S. resident alien, to request a withdrawal:");
			isTextFieldDisplayed("Download and complete IRS form W-8BEN from the IRS website.");
			//isTextFieldDisplayed("Call us at 1-855-756-4738 to request a pre-filled withdrawal form.");
			isTextFieldDisplayed("Return both completed forms to the address provided.");
		}
		
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);
		}
		
	}
	public void validateFianlSubmitRMD() throws SQLException
	{
		String sExpected="MIN DISTR",sActual=null;
		String[] sqlQuery = Stock.getTestQuery("getFinalConfirmationNumber");
		for(int i=0;i<txtConfirmationNumber.size();i++)
		{
			if(txtConfirmationNumber.size()==2 && i==0)
			{
				prevConfirmationNo=txtConfirmationNumber.get(i).getText();
			}
			else
			{
				confirmationNo=txtConfirmationNumber.get(i).getText();
			}
		}
		String[] sqlidEvent = Stock.getTestQuery("getIdFromEvent");
		sqlidEvent[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_NumberId = DB.executeQuery(sqlidEvent[0],
				sqlidEvent[1], confirmationNo);
		rs_NumberId.first();
		String sFinalConfirmationNumber = rs_NumberId.getString("ID");
		/*String sFinalConfirmationNumber = String.valueOf(Integer.parseInt(confirmationNo)+1);*/
		
		sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		
		ResultSet getWithdrawalConfRs=DB.executeQuery(sqlQuery[0],sqlQuery[1],Stock.GetParameterValue("ind_ID"),
				sFinalConfirmationNumber);
		getWithdrawalConfRs.first();
		sActual = getWithdrawalConfRs.getString("dsrs_code");
		if(sExpected.equalsIgnoreCase(sActual))
			Reporter.logEvent(
					Status.PASS,
					"Verify Final Confirmation Number",
					"Final Confirmation Number matched. Expected: "+sExpected+" Actual: "+sActual, false);
		 else
			 Reporter.logEvent(
						Status.FAIL,
						"Verify Final Confirmation Number",
						"Final Confirmation Number did not match. Expected: "+sExpected+" Actual: "+sActual, true);
	}
	
	public void updateRMDService(String service) throws Exception
	{
		String[] updateQuery = Stock.getTestQuery("updateRMDService");
		int updateService=0;
		updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"
				+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		updateService = DB.executeUpdate(updateQuery[0],
				updateQuery[1], service, Stock.GetParameterValue("planId"));
		
		if(updateService>0)
			Reporter.logEvent(Status.PASS,
					"Verify if RMD Service is updated or not",
					"RMD Service is updated to null", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify if RMD Service is updated or not",
					"RMD Service is not updated to null", false);
	}
}
