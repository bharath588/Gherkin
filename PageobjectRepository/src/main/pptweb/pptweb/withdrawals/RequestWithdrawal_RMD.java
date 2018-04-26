package pptweb.withdrawals;

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

import pptweb.appUtils.Common;
import pptweb.general.LeftNavigationBar;
import pptweb.landingpage.LandingPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RequestWithdrawal_RMD extends
		LoadableComponent<RequestWithdrawal_RMD> {
			String sEmail=null;
			String sFianlPhoneNum=null;
			String confirmationNo=null;	
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

	@FindBy(linkText = "Change method")
	private WebElement lnkChangeMethod;
	
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
	@FindBy(xpath="//rmd-info[@calc-criteria-msg='distribution.calCriteriaMessage']/div/p")
	private WebElement txtInfodistributionMessage;
	@FindBy(xpath="//div[@id='rmdAlert']/p")
	private WebElement txtInfowarningMessage;
	
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
	
	@FindBy(xpath="//div[@class='confirmation-process']/dl/div[@class='complete']/dd/span[@ng-if]")
	private WebElement txtConfirmationNumber;
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div[@class='complete']/dd)[2]")
	private WebElement txtCurrentTimeStapm;
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div)[3]/dt/span")
	private WebElement txtPaymentType;
	@FindBy(xpath="(//div[@class='confirmation-process']/dl/div)[3]/dt")
	private WebElement fulltxtPaymentType;
	@FindBy(xpath="//div[@class='confirmation-process']/p")
	private WebElement txtProactiveNotification;
	
	
	@FindBy(xpath="//tr[./th[contains(text(),'Delivery Method')]]/td")
	private WebElement confirmationPageDeliveryMethod;
	@FindBy(xpath="//tr[./th[contains(text(),'Sent to')]]/td")
	private WebElement confirmationPageSentToAddress;
	@FindBy(xpath="//tr[./th[contains(text(),'Payment amount')]]/td")
	private WebElement confirmationPagePaymentAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'Federal Tax Withholding')]]/td")
	private WebElement confirmationPageFederalTaxAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'RMD Amount')]]/td")
	private WebElement confirmationPageRMDAmount;
	@FindBy(xpath="//tr[./th[contains(text(),'Processing date')]]/td")
	private WebElement confirmationPageProcessingDate;
	@FindBy(xpath="//tr[./th[contains(text(),'State Tax')]]/td")
	private WebElement confirmationPageStateTax;
	@FindBy(xpath="//tr[./th[contains(text(),'Delivery Fee')]]/td")
	private WebElement confirmationPageDeliveryFee;
	
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
					"Withdrawal - RMD Page is NOT displayed successfully", true);

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
		if (fieldName.trim().equalsIgnoreCase("Close")) {
			return this.lnkCloseModal;
		}
		if (fieldName.trim().equalsIgnoreCase("Warning Message")) {
			return this.txtInfowarningMessage;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}
	
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
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
	public void verifySentToAddress(String queryName,String userName) throws SQLException
	{
		String sFirstLine, sSecondLine;
		
		String[] sqlQueryHomeAddress = Stock.getTestQuery(queryName);
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("userName"));
		rs_Number.first();
		sFirstLine = rs_Number.getString("first_line_mailing");
		sSecondLine = rs_Number.getString("scnd_line_mailing");
		
		String sCity = rs_Number.getString("city");
		String sState = rs_Number.getString("state_code");
		String[] sZip = rs_Number.getString("zip_code").split("-");
		String sCountry = rs_Number.getString("country");

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
				
					if (Web.isWebElementDisplayed(RMDHeaderCheckBox))
						Reporter.logEvent(
								Status.PASS,
								"Verify if CheckBox is displayed for current year",
								"Checkbox is dipalyed and selected by default in current year",
								false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify if CheckBox is displayed for current year",
								"Checkbox is not displayed in current year",
								true);
				
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
	public void verifyDeliveryMethod() throws SQLException
	{
		for (int i = 0; i < RMDWindow.size(); i++) {
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
			
			if (txtRegularMail.get(i).getText()
					.equalsIgnoreCase("Regular mail"))
				Reporter.logEvent(Status.PASS,
						"By default Regular Mail should be displayed",
						"Regular mail is displayed by default for "+i+" Block.", false);
			else
				Reporter.logEvent(Status.FAIL,
						"By default Regular Mail should be displayed",
						"Regular mail is not displayed by default for "+i+" Block.", true);
			
			if(Web.isWebElementDisplayed(lnkChangeMethod))
				Reporter.logEvent(Status.PASS,
						"Verify if Change method link is displayed for "+i+" Block.",
						"Change method link is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Change method link is displayed for "+i+" Block.",
						"Change method link is not displayed", true);
			
			verifySentToAddress(Stock.GetParameterValue("getHomeAddress"),Stock.GetParameterValue("userName"));
			
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
		Web.clickOnElement(btnDone);
		if(btnDone.isDisplayed())
		{
			Web.clickOnElement(btnDone);
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
		Web.clickOnElement(btnDone);
		if(btnDone.isDisplayed())
		{
			Web.clickOnElement(btnDone);
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
		lnkModifyWithholding.click();
		
		this.lnkContributionRate.click();
		lib.Web.waitForElement(txtcontributionRateSlider);
		lib.Web.setTextToTextBox(txtcontributionRateSlider, Stock.GetParameterValue("New Contribution Rate"));
		Web.clickOnElement(btnDone);
		if(btnDone.isDisplayed())
		{
			Web.clickOnElement(btnDone);
		}
		this.lnkUpdateTax.click();
		isTextFieldDisplayed(Stock.GetParameterValue("New Contribution Rate")+"%* federal");
		
	}
	public void validateChangeMethodLink(boolean isACH) throws SQLException
	{
		
		String[] sqlQueryHomeAddress = Stock.getTestQuery(Stock.GetParameterValue("sqlExpeditedCharge"));
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs_Number = DB.executeQuery(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], Stock.GetParameterValue("planId"));
		rs_Number.first();
		
		String sExpeditedCharge = rs_Number.getString("AMOUNT");
		
		//System.out.println("@@"+sExpeditedCharge);
		this.lnkChangeMethod.click();
		if(this.btnRegaularMail.isSelected())
			Reporter.logEvent(Status.PASS,
					"Verify by default Regaular Mail is selected",
					"Regaular Mail is selected by default", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify by default Regaular Mail is selected",
					"Regaular Mail is not selected by default", true);
		
		if(txtRegularMailFee.getText().equalsIgnoreCase("Free"))
			Reporter.logEvent(Status.PASS,
					"Regular Mail Fee should be Free",
					"Regaular Mail fee is Free, Fee Cost: "+txtRegularMailFee.getText(), false);
		else
			Reporter.logEvent(Status.FAIL,
					"Regular Mail Fee should be Free",
					"Regaular Mail fee is not Free, Fee Cost: "+txtRegularMailFee.getText(), true);
		
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
			String[] sqlACHAmount = Stock.getTestQuery(Stock.GetParameterValue("sqldirectDepositFee"));
			sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs_ACHAmount = DB.executeQuery(sqlACHAmount[0],
					sqlACHAmount[1]);
			rs_ACHAmount.first();
			
			String sdirectDepositFee = rs_ACHAmount.getString("AMOUNT");
			
			this.lnkChangeMethod.click();
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
	}
	
	public void validateInfoMessgae(String infoMessage, String infoXpath) throws Exception
	{
		WebElement ele = getWebElement(infoXpath);
		//System.out.println(infoMessage);
		//System.out.println(ele.getText());
		if(ele.getText().equalsIgnoreCase(infoMessage))
			Reporter.logEvent(Status.PASS,
					"Verify RMD Information Message Expected: "+infoMessage+" Actual: "+ele.getText(),
					"RMD Information Message Matched", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify RMD Information Message Expected: "+infoMessage+" Actual: "+ele.getText(),
					"RMD Information Message not matched", true);
	}
	public void updateFields(String indId,String gcId, String filedValue, String queryName) throws Exception
	{
		String[] sqlQueryHomeAddress = Stock.getTestQuery(queryName);
		sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		DB.executeUpdate(sqlQueryHomeAddress[0],
				sqlQueryHomeAddress[1], filedValue,indId,gcId);
		Reporter.logEvent(Status.PASS,
				"Verify if updated the DataBase with proper fields",
				"Updated db with proper fields", false);
		
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

	public void selectRMDBy(String option, String deliveryMethod) {
		try {

			getEmail_PhoneNumber();
			this.lnkChangeMethod.click();

			if (deliveryMethod.equalsIgnoreCase("Regular Mail"))
				this.btnRegaularMail.click();
			else if (deliveryMethod.equalsIgnoreCase("Expedited Mail"))
				this.btnExpeditedMail.click();
			else
				this.btnDirectDeposit.click();

			this.lnkUpdateTax.click();

			if (!this.chkBoxStatus.isSelected())
				chkBoxStatus.click();

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
			this.btnRequestRMD.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void citizenShipValidation(String SSN){
		try {
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
					keyBoardEvent.sendKeys(Keys.TAB).build().perform();
					Thread.sleep(1000);
					//Web.clickOnElement(btnConfirmContinue);
							
					Reporter.logEvent(Status.INFO, "Entering SSN "
							+ ssn, "Entered SSN: " + ssn, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void validateConfirmationPage_RMDConfirmationNumber()
	{
		Common.waitForProgressBar();
		if(isTextFieldDisplayed("RMD Request Received"))
			isTextFieldDisplayed("Your RMD request has been received and is being processed.");
		
		//1st Step
		isTextFieldDisplayed("RMD request received");
		isConfirmationNumDisplayed(Stock.GetParameterValue("getConfirmationNumber"));
		validateCurrentTimeStamp();
		isTextFieldDisplayed("Complete");
	}
	public void isConfirmationNumDisplayed(String queryName)
	{
		
		int isConfirmationNumDisplayed=0;
		try {	
			confirmationNo=txtConfirmationNumber.getText();
			
			String[] sqlQuery = Stock.getTestQuery(queryName);
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
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
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void validateCurrentTimeStamp()
	{
		Date date = new Date();
		DateFormat time = new SimpleDateFormat("M/dd/yyyy hh");
		time.setTimeZone(TimeZone.getTimeZone("MST"));
		String sDateTime = txtCurrentTimeStapm.getText().substring(0, 12);
		
		
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
	public void validateConfirmationPage_RMDPaymentType(String sDeliveryType)
	{
		if(sDeliveryType.equalsIgnoreCase("Regular Mail"))
		{
			if(fulltxtPaymentType.getText().equalsIgnoreCase("Payment sent by "+sDeliveryType))
				Reporter.logEvent(Status.PASS,
						"Verify RMD Payment Type.",
						"RMD Payment Type Matched  Expected: "+"Payment sent by "+sDeliveryType+" Actual: "+fulltxtPaymentType.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify RMD Payment Type.",
						"RMD Payment Type Matched  Expected: "+"Payment sent by "+sDeliveryType+" Actual: "+fulltxtPaymentType.getText(), true);
			
			isTextFieldDisplayed("Delivery can be up to 5 days.");
		}
		else if(sDeliveryType.equalsIgnoreCase("expedited mail"))
		{
			if(fulltxtPaymentType.getText().equalsIgnoreCase("Payment sent by "+sDeliveryType))
				Reporter.logEvent(Status.PASS,
						"Verify RMD Payment Type",
						"RMD Payment Type Matched. Expected: "+"Payment sent by "+sDeliveryType+" Actual: "+fulltxtPaymentType.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify RMD Payment Type",
						"RMD Payment Type Matched. Expected: "+"Payment sent by "+sDeliveryType+" Actual: "+fulltxtPaymentType.getText(), true);
			
			isTextFieldDisplayed("Delivery can be up to 2 days.");
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
	}
	public void validateConfirmationPage_FinalAmount(String userName)
	{
		try
		{
			DecimalFormat f = new DecimalFormat(".##");
			String sRMDAmount;
			double finalA;
			String[] sqlRMDAmount = Stock.getTestQuery("getMinDistrAmt");
			sqlRMDAmount[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs_Number = DB.executeQuery(sqlRMDAmount[0],
					sqlRMDAmount[1], Stock.GetParameterValue("userName"));
			rs_Number.first();
			sRMDAmount = rs_Number.getString("min_distr_amt");
			
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("M/dd/yyyy");
			time.setTimeZone(TimeZone.getTimeZone("MST"));
			String sDateTime = confirmationPageProcessingDate.getText();
			
			double expectedFederalTax = Double.parseDouble(f.format((Double.parseDouble(sRMDAmount)*10)/100));
			double stateTax=Double.parseDouble(f.format((expectedFederalTax*10)/100));
			if(Web.isWebElementDisplayed(confirmationPageStateTax))
			{
				finalA = Double.parseDouble(sRMDAmount)-expectedFederalTax-stateTax;
			}
			else
			{
				finalA = Double.parseDouble(sRMDAmount)-expectedFederalTax;
			}
			
			/*System.out.println("expectedFederalTax"+expectedFederalTax);
			System.out.println("stateTax"+stateTax);
			System.out.println("finalA"+f.format(finalA));*/
			
			
			//Verify RMD Ammount
			if (String.valueOf(
					Web.getIntegerCurrency(confirmationPageRMDAmount.getText())).equalsIgnoreCase(sRMDAmount))
				Reporter.logEvent(
						Status.PASS,
						"Verify RMD Amount in confirmation page",
						"RMD Amount matched. Actual: "+ Web.getIntegerCurrency(confirmationPageRMDAmount.getText()) +
						" Expected: "+ sRMDAmount, false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify RMD Amount in confirmation page",
						"RMD Amount is not matching. Actual: "+ Web.getIntegerCurrency(confirmationPageRMDAmount.getText()) +
						" Expected: "+ sRMDAmount, true);
			
			//Verify Processing date
			if(sDateTime.contains(time.format(date)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Request Confirmation Processing Date.",
						"Processing Date matched. Expected: "+time.format(date)+" Actual: "+sDateTime,false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Request Confirmation Processing Date.",
						"Processing Date did not match. Expected: "+time.format(date)+" Actual: "+sDateTime,true);
			
			// Verify Federal Tax 
			if(String.valueOf(
					Web.getIntegerCurrency(confirmationPageFederalTaxAmount.getText())).equalsIgnoreCase(f.format(expectedFederalTax)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Federal Tax in Confirmation Page.",
						"Federal Tax matched. Expected: "+expectedFederalTax+" Actual: "+String.valueOf(Web.getIntegerCurrency(confirmationPageFederalTaxAmount.getText())),false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Federal Tax in Confirmation Page.",
						"Federal Tax matched. Expected: "+expectedFederalTax+" Actual: "+String.valueOf(Web.getIntegerCurrency(confirmationPageFederalTaxAmount.getText())),true);
			
			//Verify Delivery Method
			if(confirmationPageDeliveryMethod.getText().equalsIgnoreCase(Stock.GetParameterValue("selectDeliveryMethod")))
				Reporter.logEvent(
						Status.PASS,
						"Verify Delivery Method in Confirmation Page.",
						"Delivery Method matched. Expected: "+Stock.GetParameterValue("selectDeliveryMethod")+" Actual: "+confirmationPageDeliveryMethod.getText(),false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Delivery Method in Confirmation Page.",
						"Delivery Method did not match. Expected: "+Stock.GetParameterValue("selectDeliveryMethod")+" Actual: "+confirmationPageDeliveryMethod.getText(),true);
			
			//Verify Sent To
			Reporter.logEvent(Status.INFO, "Verify the Sent To Address in the Confirmation Page", 
					"The Address is displayed as:\n"+
				"Address: "+confirmationPageSentToAddress.getText().trim(), false);
			
			validateFinalPaymentAmount(Stock.GetParameterValue("selectDeliveryMethod"),finalA);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void validateFinalPaymentAmount(String sDeliverMethod, double finalAmount)
	{
		DecimalFormat f = new DecimalFormat(".##");
		if(sDeliverMethod.equalsIgnoreCase("Regular Mail"))
		{
			if (String.valueOf(
					Web.getIntegerCurrency(confirmationPagePaymentAmount.getText())).equalsIgnoreCase(f.format(finalAmount)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Final payment Amount",
						"Final payment Amount matched. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.getText()) +
						" Expected: "+ f.format(finalAmount), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Final payment Amount",
						"Final payment Amount did not match. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.getText()) +
						" Expected: "+ f.format(finalAmount),  true);
		}
		else if(sDeliverMethod.equalsIgnoreCase("Expedited Mail"))
		{
			double sDeliverFee = Double.parseDouble(String.valueOf(Web.getIntegerCurrency(confirmationPageDeliveryFee.getText())));
			finalAmount = finalAmount-sDeliverFee;
			if (String.valueOf(
					Web.getIntegerCurrency(confirmationPagePaymentAmount.getText())).equalsIgnoreCase(f.format(finalAmount)))
				Reporter.logEvent(
						Status.PASS,
						"Verify Final payment Amount",
						"Final payment Amount matched. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.getText()) +
						" Expected: "+ f.format(finalAmount), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Final payment Amount",
						"Final payment Amount did not match. Actual: "+ Web.getIntegerCurrency(confirmationPagePaymentAmount.getText()) +
						" Expected: "+ f.format(finalAmount),  true);
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
}