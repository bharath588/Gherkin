package pageobjects.enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;
import com.gargoylesoftware.htmlunit.javascript.host.media.GainNode;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.LeftNavigationBar;
import pageobjects.login.TwoStepVerification;

public class NQEnrollment extends LoadableComponent<NQEnrollment> {

	// Declarations
	private LoadableComponent<?> parent;
	// private static boolean waitforLoad = false;

	@FindBy(xpath = "//h1[contains(text(),'Enrollment')]")
	private WebElement hdrEnrollment;
	@FindBy(id = "home")
	private WebElement lnkHome;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;

	@FindBy(xpath = "//button[./span[contains(text(),'Edit my options')]]")
	private WebElement btnEditMyOptions;
	@FindBy(xpath = "//button[contains(text(),'Continue')]")
	private WebElement btnContinue;
	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement btnCancel;
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	private WebElement btnYes;
	@FindBy(xpath = "//button[contains(text(),'No')]")
	private WebElement btnNo;
	@FindBy(xpath = "//p[contains(text(),'Carry over my contribution if I reach the plan or IRS limit?')]")
	private WebElement hdrCarryoverContribution;
	@FindBy(xpath = "//div[@class='col-sm-3']//button[text()[normalize-space()='I Agree, Enroll Now']]")
	private WebElement btnIAgreeEnrollNow;
	@FindBy(xpath = "//h1[contains(text(),'How much would you like to contribute?')]")
	private WebElement hdrContributions;
	@FindBy(xpath = "//h1[contains(text(),'When would you like to receive your savings?')]")
	private WebElement hdrDistributionElection;
	@FindBy(xpath = "//button[contains(text(),'Add Elections')]")
	private WebElement btnAddElection;
	@FindBy(id = "deferralAmounts0")
	private WebElement inpContributionRate;
	@FindBy(xpath = "//div[@class='btn-group']//button[contains(@class,'active')][./following-sibling::input]")
	private WebElement txtContributionRateType;
	@FindBy(xpath = "//*[contains(@class, 'editable-text-trigger')]")
	private WebElement lnkContributionRate;
	@FindBy(xpath = "//div[@class='sliderThumbValue']")
	private WebElement inpSliderThumb;
	@FindBy(xpath = "//button[contains(text(),'Back')]")
	private WebElement btnBack;
	@FindBy(xpath = "//button[./strong[text()[normalize-space()='Continue to enrollment']]]")
	private WebElement btnContinueToEnroll;
	@FindBy(xpath = "//div[@class='modal-body auto-increase']//div/p")
	private List<WebElement> txtMadalContent;
	@FindBy(xpath = "//div[@class='error-block']")
	private WebElement errorBlock;
	@FindBy(xpath = "//ui-view//div[@class='page-title']//h1")
	private WebElement hdrforContinueToEnrollmentPage;
	@FindBy(xpath = "//label[@class='panel-title']/strong")
	private WebElement panelTitle;
	@FindBy(xpath = "//pw-non-qual-participation-agreement//span[1]")
	private WebElement lblIAgree;
	@FindBy(xpath = "//pw-non-qual-participation-agreement//a")
	private WebElement lnkPartipantAgreementForm;

	private String textField = "//*[contains(text(),'webElementText')]";
	private String strMethodTerminationOrRetirement = "//input[contains(@id,'method_Termination')][following-sibling::div//span[contains(text(),'webElementText')]]";
	private String strMethodDeath = "//input[contains(@id,'method_Death')][following-sibling::div//span[contains(text(),'webElementText')]]";
	private String strTerminationOrRetirementMethod = "//div[./span[contains(text(),'webElementText')]]//preceding-sibling::input";
	private String strDisbReasonMethod = "//input[contains(@id,'method_DisbReason')][following-sibling::div//span[contains(text(),'DisbMethod')]]";
	private String lblPreTaxToAftTaxToNQ = "//div[contains(@class,'radio')]//label[text()[normalize-space()='electionType']]";
	private String txtDisbType = "//div[./div/p/strong[text()='DisbReason']]/following-sibling::div[./div[contains(text(),'DisbMetodAndTiming')]]";

	/**
	 * Empty args constructor
	 * 
	 */
	/**
	 * Empty args constructors
	 * 
	 */
	public NQEnrollment() {
		this.parent = new TwoStepVerification();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public NQEnrollment(LoadableComponent<?> parent) {
		// this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {

		Assert.assertTrue(Web.isWebElementDisplayed(this.hdrEnrollment),
				"NQEnrollment Page is Not Loaded");

	}

	@Override
	protected void load() {
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		this.parent.get();

		if (!Web.isWebElementDisplayed(this.lnkHome)) {
			mfaScreen.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			try {
				mfaScreen.submitVerificationCode(
						Stock.getConfigParam("defaultActivationCode"), true,
						false);

				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private WebElement getWebElement(String fieldName) {

		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON EDIT MY OPTIONS")) {
			return this.btnEditMyOptions;
		}
		if (fieldName.trim().equalsIgnoreCase("DEFERRAL ELECTION")) {
			return this.hdrContributions;
		}
		if (fieldName.trim().equalsIgnoreCase("DISTRIBUTION ELECTIONS")) {
			return this.hdrDistributionElection;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON ADD ELECTIONS")) {
			return this.btnAddElection;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Continue To Enrollment")) {
			return this.btnContinueToEnroll;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Continue")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Cancel")) {
			return this.btnCancel;
		}
		return null;
	}

	/**
	 * <pre>
	 * Method to select Distribution Election Reason for Death
	 * </pre>
	 * 
	 * @param distributionElectionType
	 *
	 */

	public void selectDistributionElectionTypeForDeath(
			String distributionElectionType) {
		boolean isTextDisplayed = false;
		try {
			WebElement inpMethodDeath = Web.getDriver().findElement(
					By.xpath(strMethodDeath.replace("webElementText",
							distributionElectionType)));

			isTextDisplayed = Web.isWebElementDisplayed(inpMethodDeath, true);

			if (isTextDisplayed)
				Web.clickOnElement(inpMethodDeath);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to select Termination Or Retirement Type for Distribution Election Reason
	 * </pre>
	 * 
	 * @param distributionElectionType
	 *
	 */

	public void selectDistributionElectionTypeForTermination(
			String distributionElectionType) {
		boolean isTextDisplayed = false;
		try {
			WebElement inpTerminationOrRetirementType = Web
					.getDriver()
					.findElement(
							By.xpath(strMethodTerminationOrRetirement.replace(
									"webElementText", distributionElectionType)));

			isTextDisplayed = Web.isWebElementDisplayed(
					inpTerminationOrRetirementType, true);

			if (isTextDisplayed)
				Web.clickOnElement(inpTerminationOrRetirementType);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to select Timing for Distribution Election Method
	 * </pre>
	 * 
	 * @param distributionElectionType
	 *
	 */

	public void selectTimingForDistributionElectionMethod(String disbTiming) {
		boolean isTextDisplayed = false;
		try {
			WebElement inpMethod = Web.getDriver().findElement(
					By.xpath(strTerminationOrRetirementMethod.replace(
							"webElementText", disbTiming)));

			isTextDisplayed = Web.isWebElementDisplayed(inpMethod, true);

			if (isTextDisplayed) {
				Web.clickOnElement(inpMethod);
				Reporter.logEvent(Status.INFO, "Select Disbursement Timing",
						"Selected Distribution Timing '" + disbTiming, true);
			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Click On Continue Button
	 * </pre>
	 *
	 */

	public void clickContinue() {
		boolean isTextDisplayed = false;
		try {

			isTextDisplayed = Web.isWebElementDisplayed(btnContinue, true);

			if (isTextDisplayed)
				Web.clickOnElement(btnContinue);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to select Distribution Election Method  for Disbursement Reason
	 * </pre>
	 * 
	 * @param disbursementReason
	 *            , disbursementMethod
	 *
	 */

	public void selectDisbursementMethod(String disbursementReason,
			String disbursementMethod) {
		boolean isInputFieldDisplayed = false;
		try {
			WebElement inpMethod = Web.getDriver().findElement(
					By.xpath(strDisbReasonMethod.replace("DisbReason",
							disbursementReason).replace("DisbMethod",
							disbursementMethod)));

			isInputFieldDisplayed = Web.isWebElementDisplayed(inpMethod, true);

			if (isInputFieldDisplayed) {
				Web.clickOnElement(inpMethod);
				Reporter.logEvent(Status.INFO, "Select Distribution Method",
						"Selected Distribution Method '" + disbursementMethod
								+ "' For" + disbursementReason, true);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Add Carry Over Contributions
	 * </pre>
	 * 
	 * @param carryOverOption
	 *
	 */
	public void addCarryOverContribution(String carryOverOption) {

		if (Web.isWebElementDisplayed(btnYes)) {
			btnYes.click();

			if (Web.isWebElementDisplayed(hdrCarryoverContribution, true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Carry over my contribution is displayed",
						"Carry over my contribution displayed", true);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Carry over my contribution is displayed",
						"Carry over my contribution is not displayed", true);
			boolean isInputFieldDisplayed = false;
			try {
				WebElement inpCarryOver = Web.getDriver().findElement(
						By.xpath(lblPreTaxToAftTaxToNQ.replace("electionType",
								carryOverOption)));

				isInputFieldDisplayed = Web.isWebElementDisplayed(inpCarryOver,
						true);

				if (isInputFieldDisplayed)
					Web.clickOnElement(inpCarryOver);

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		} else
			Reporter.logEvent(Status.INFO,
					"Verify Carry over my contribution is Available",
					"Carry over my contribution is not Available", true);

	}

	/**
	 * <pre>
	 * Method to delete Active Chaining
	 * </pre>
	 * 
	 * @param ssn
	 *            , first_name
	 *
	 */
	public void deleteActiveChainingFromDB(String ssn, String first_name)
			throws Exception {
		String[] sqlQuery;
		String[] sqlQuery_commit;
		sqlQuery = Stock.getTestQuery("deleteActiveChaining");
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], ssn, first_name);
		DB.executeUpdate(sqlQuery[0], "commit");

	}

	/**
	 * <pre>
	 * Method Click on Edit My Options Button
	 * </pre>
	 * 
	 */
	public void clickEditMyOptionsButton() {

		boolean isInputFieldDisplayed = false;
		try {

			isInputFieldDisplayed = Web.isWebElementDisplayed(btnEditMyOptions,
					true);

			if (isInputFieldDisplayed)
				Web.clickOnElement(btnEditMyOptions);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Edit My Option Button is Displayed",
						" Edit My Option Button is not Displayed", true);
		} catch (NoSuchElementException e) {
			Reporter.logEvent(Status.FAIL,
					"Verify Edit My Option Button is Displayed",
					" Edit My Option Button is not Displayed", true);

		}
	}

	/**
	 * <pre>
	 * Method Click on I Agree And Enroll Now Button
	 * </pre>
	 * 
	 */
	public void clickIAgreeAndEnrollNowButton() {

		boolean isInputFieldDisplayed = false;
		try {

			isInputFieldDisplayed = Web.isWebElementDisplayed(
					btnIAgreeEnrollNow, true);

			if (isInputFieldDisplayed)
				Web.clickOnElement(btnIAgreeEnrollNow);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree And Enroll Now' Button is Displayed",
						" ,I Agree And Enroll Now' Button is not Displayed",
						true);
		} catch (NoSuchElementException e) {
			Reporter.logEvent(Status.FAIL,
					"Verify 'I Agree And Enroll Now' Button is Displayed",
					" ,I Agree And Enroll Now' Button is not Displayed", true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify Selected Distribution Method is Displayed in Edit My Options Page
	 * </pre>
	 * 
	 * @param disbursementReason
	 *            , disbursementMethodAndTiming
	 *
	 */

	public void VerifyDisbursementMethodIsDisplayed(String disbursementReason,
			String disbursementMethodAndTiming) {
		boolean isInputFieldDisplayed = false;
		try {
			WebElement lblDisbType = Web.getDriver().findElement(
					By.xpath(txtDisbType.replace("DisbReason",
							disbursementReason).replace("DisbMetodAndTiming",
							disbursementMethodAndTiming)));

			isInputFieldDisplayed = Web
					.isWebElementDisplayed(lblDisbType, true);

			if (isInputFieldDisplayed)

				Reporter.logEvent(
						Status.PASS,
						"Verify Distribution Method And Timing Displayed for Disbursement Reason",
						"Distribution Method And Timing Displayed for Disbursement Reason\nDistibution Election:\n"
								+ disbursementReason
								+ "\n"
								+ disbursementMethodAndTiming, true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Distribution Method And Timing Displayed for Disbursement Reason",
						"Distribution Method And Timing is not Displayed for Disbursement Reason:"
								+ disbursementReason, true);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method Click on Add Election Button
	 * </pre>
	 * 
	 */
	public void clickOnAddElectionsButton() {

		boolean isInputFieldDisplayed = false;
		try {

			isInputFieldDisplayed = Web.isWebElementDisplayed(btnAddElection,
					true);

			if (isInputFieldDisplayed)
				Web.clickOnElement(btnAddElection);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Add Elections Button is Displayed",
						"Add Elections Button is not Displayed", true);
		} catch (NoSuchElementException e) {
			Reporter.logEvent(Status.FAIL,
					"Verify Add Elections Button is Displayed",
					"Add Elections Button is not Displayed", true);

		}
	}

	/**
	 * <pre>
	 * Method to Verify the Page Header is Displayed or Not
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyPageHeaderIsDisplayed(String webElement) {

		WebElement webelement = getWebElement(webElement);
		Web.waitForElement(webelement);

		if (Web.isWebElementDisplayed(webelement)) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + webElement
					+ " Page is Displayed", webElement + " Page  is Displayed",
					true);

		} else {

			lib.Reporter.logEvent(Status.FAIL, "Verify " + webElement
					+ " Page  is Displayed", webElement
					+ " Page  is Not Displayed", true);
			throw new Error(webElement + " is not displayed");
		}

	}

	/**
	 * <pre>
	 * Method to get the Contribution Rate From Summary Page
	 *
	 * </pre>
	 * 
	 * @return deferralAmt
	 * 
	 *
	 */
	public String getDeferralAmount() {
		String deferralAmt = "";

		if (Web.isWebElementDisplayed(inpContributionRate)) {
			deferralAmt =txtContributionRateType.getText()+inpContributionRate.getAttribute("value");
		} else {

			lib.Reporter.logEvent(Status.FAIL, "Get Contribution Rate",
					" Contribution Rate is Not Displayed", true);
			throw new Error("Contribution Rate is not displayed");
		}

		return deferralAmt;
	}

	/**
	 * <pre>
	 * Method to Verify Deferral Election Page As returning User
	 * </pre>
	 * 
	 * @param contributionRate
	 *
	 */

	public void VerifyDeferralElectionPageAsReturningUser(
			String contributionRate) {
		String actualContributionRate;
		try {
			actualContributionRate = lnkContributionRate.getText().trim();
			if (contributionRate.equalsIgnoreCase(actualContributionRate))
				Reporter.logEvent(
						Status.PASS,
						"Verify Previously Elected Deferral is Same in Editable Text Field",
						"Previously Elected Deferral is Same in Editable Text Field\nExpected Contrbution"
								+ " Rate:"
								+ contributionRate
								+ "\nActual Contribution Rate:"
								+ actualContributionRate, true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Previously Elected Deferral is Same in Editable Text Field",
						"Previously Elected Deferral is Not Same in Editable Text Field\nExpected Contrbution"
								+ " Rate:"
								+ contributionRate
								+ "\nActual Contribution Rate:"
								+ actualContributionRate, true);

			actualContributionRate = inpSliderThumb.getText().trim();
			if (contributionRate.equalsIgnoreCase(actualContributionRate))
				Reporter.logEvent(Status.PASS,
						"Verify Previously Elected Deferral is Same in Slider",
						"Previously Elected Deferral is Same in Slider\nExpected Contrbution"
								+ " Rate:" + contributionRate
								+ "\nActual Contribution Rate:"
								+ actualContributionRate, true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Previously Elected Deferral is Same in Slider",
						"Previously Elected Deferral is Not Same in Slider\nExpected Contrbution"
								+ " Rate:" + contributionRate
								+ "\nActual Contribution Rate:"
								+ actualContributionRate, true);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method Click on Back Button
	 * </pre>
	 * 
	 */
	public void clickOnBackButton() {
		try {
			Web.clickOnElement(btnBack);
		} catch (NoSuchElementException e) {
			throw new Error("Back Button is not displayed");

		}
	}

	/**
	 * <pre>
	 * Method to Verify Deferral Election Page As New User
	 * </pre>
	 * 
	 * @param contributionRate
	 *
	 */

	public void VerifyDeferralElectionPageAsNewUser(String contributionRate) {
		String actualContributionRate;
		try {
			actualContributionRate = lnkContributionRate.getText().trim();
			if (contributionRate.equalsIgnoreCase(actualContributionRate))
				Reporter.logEvent(Status.PASS,
						"Verify Default Contribution is set to 0",
						"Default Contribution is set to 0", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Default Contribution is set to 0",
						"Default Contribution is not set to 0", true);

			actualContributionRate = inpSliderThumb.getText().trim();
			if (contributionRate.equalsIgnoreCase(actualContributionRate))
				Reporter.logEvent(Status.PASS,
						"Verify Default Slider is set to 0",
						"Default Slider is set to 0", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Default Slider is set to 0",
						"Default Slider is set to 0", true);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Modal Pop Up for Returning User
	 * </pre>
	 * @throws ParseException 
	 * 
	 */

	public void VerifyModalPopupForReturningUser(String ga_id, String ssn) throws ParseException {

		try {
			String expectedText = "";
			String actualText = "";
			Web.waitForElement(btnContinue);

			if (Web.isWebElementDisplayed(btnContinue)) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Modal Pop Up Displayed When Returning User Clicks on Enroll Now Link",
						" Modal Pop Up Displayed When Returning User Clicks on Enroll Now Link",
						true);
				Common.isTextFieldDisplayed("Make changes to your elections?");
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Modal Pop Up Displayed When Returning User Clicks on Enroll Now Link",
						" Modal Pop Up is Not Displayed When Returning User Clicks on Enroll Now Link",
						true);
			expectedText = "You enrolled in the plan on "
					+ getEffectiveDate(ga_id, ssn)
					+ ", however, you may make changes until "
					+ getEnrollmentEndDate(ga_id) + ".";
			actualText = txtMadalContent.get(0).getText().trim().toString();
			if (expectedText.equalsIgnoreCase(actualText)) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Modal Popup is displaying the Enrollment Effective Date and Enrollment Last Date",
						"Modal Popup is displaying the Enrollment Effective Date and Enrollment Last Date\nExpected:"
								+ expectedText + "\nActualText:" + actualText,
						true);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Verify Modal Popup is displaying the Enrollment Effective Date and Enrollment Last Date",
						"Modal Popup is Not displaying the Enrollment Effective Date and Enrollment Last Date\nExpected:"
								+ expectedText + "\nActualText:" + actualText,
						true);
			}
			expectedText = "Click \"Cancel\" to return to the previous page, or \"Continue\" to make changes.";
			actualText = txtMadalContent.get(1).getText().trim().toString();
			if (expectedText.equalsIgnoreCase(actualText)) {
				Reporter.logEvent(Status.PASS,
						"Verify Continue and Cancel Text in Modal Popup",
						" Continue and Cancel Text is Displayed in Modal Popup\nExpected:"
								+ expectedText + "\nActualText:" + actualText,
						true);
			} else {
				Reporter.logEvent(Status.PASS,
						"Verify Continue and Cancel Text in Modal Popup",
						" Continue and Cancel Text is Displayed in Modal Popup\nExpected:"
								+ expectedText + "\nActualText:" + actualText,
						true);
			}
			verifyWebElementDisplayed("Button Continue");
			verifyWebElementDisplayed("Button Cancel");

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Webelement is Displayed
	 * 
	 * </pre>
	 */
	public boolean verifyWebElementDisplayed(String fieldName) {

		boolean isDisplayed = Web.isWebElementDisplayed(
				this.getWebElement(fieldName), true);

		if (isDisplayed) {

			Reporter.logEvent(Status.PASS, "Verify \'" + fieldName
					+ "\' is displayed", "\'" + fieldName + "\' is displayed",
					false);
			isDisplayed = true;

		} else {
			Reporter.logEvent(Status.FAIL, "Verify\'" + fieldName
					+ "\' is displayed", "\'" + fieldName
					+ "\' is not displayed", false);
		}

		return isDisplayed;

	}

	/**
	 * <pre>
	 * Method to get the Effective Date for DIstributions
	 *
	 * </pre>
	 * 
	 * @param ga_id
	 *            , ssn
	 * @return effectiveDate
	 * @throws ParseException 
	 * 
	 *
	 */
	public String getEffectiveDate(String ga_id, String ssn) throws ParseException {
		String effectiveDate = "";

		String[] sqlQuery = null;
		ResultSet DisbursmentEffectiveDate = null;

		try {
			sqlQuery = Stock.getTestQuery("getDistribtionEffectiveDate");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DisbursmentEffectiveDate = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				ga_id, ssn);

		if (DB.getRecordSetCount(DisbursmentEffectiveDate) > 0) {

			try {
				DisbursmentEffectiveDate.last();
				effectiveDate = DisbursmentEffectiveDate.getString("effdate").split(" ")[0];
			
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		Date effectiveDate1 = dateFormat.parse(effectiveDate);
		effectiveDate=dateFormat1.format(effectiveDate1);
		System.out.println("Effective DATE " + effectiveDate);

		return effectiveDate;
	}

	/**
	 * <pre>
	 * Method to get the Enrollment End  Date
	 * </pre>
	 * 
	 * @param ga_id
	 * @return endDate
	 * @throws ParseException 
	 * 
	 *
	 */
	public String getEnrollmentEndDate(String ga_id) throws ParseException {
		String endDate = "";

		String[] sqlQuery = null;
		ResultSet EnrollmentEndDate = null;

		try {
			sqlQuery = Stock.getTestQuery("GetEnrollmentEndDate");
		} catch (Exception e) {
			e.printStackTrace();
		}

		EnrollmentEndDate = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_id);

		if (DB.getRecordSetCount(EnrollmentEndDate) > 0) {

			try {
				EnrollmentEndDate.last();
				endDate = EnrollmentEndDate.getString("end_of_enrl_processing_date").split(" ")[0];
				System.out.println("DATE" + endDate);
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

		Date effectiveDate1 = dateFormat.parse(endDate);
		endDate=dateFormat1.format(effectiveDate1);
		System.out.println("End DATE" + endDate);

		return endDate;
	}

	/**
	 * <pre>
	 * Method to enter the Contribution Rate in Summary Page
	 *
	 * </pre>
	 * 
	 * @param contributionRate
	 * 
	 *
	 */
	public void enterDeferralAmountinSummaryPage(String contributionRate) {

		if (Web.isWebElementDisplayed(inpContributionRate)) {
			Web.setTextToTextBox(inpContributionRate, contributionRate);
		} else {

			lib.Reporter.logEvent(Status.FAIL, "Enter Contribution Rate",
					" Not Able to Enter Contribution Rate", true);
			throw new Error("Contribution Rate Input Field is not displayed");
		}

	}

	/**
	 * <pre>
	 * Method to Verify Summary Page When Error Message is Displayed
	 * </pre>
	 * 
	 * @param contributionRate
	 */

	public void VerifySummaryPageWithErrorMessage(String contributionRate) {

		try {
			enterDeferralAmountinSummaryPage(contributionRate);
			if (Web.isWebElementDisplayed(errorBlock, true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Error Message is Displayed in Summary Page",
						"Error Message is Displayed in Summary Page", true);
				if (!btnEditMyOptions.isEnabled()) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Edit My Options' Button is disabled When Error Message is Dispalyed in Summary Page",
							"'Edit My Options' Button is Disabled When Error Message is Dispalyed in Summary Page",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Edit My Options' Button is disabled When Error Message is Dispalyed in Summary Page",
							"'Edit My Options' Button is Not Disabled When Error Message is Dispalyed in Summary Page",
							true);
				}
				Web.clickOnElement(btnIAgreeEnrollNow);
				if (Web.isWebElementDisplayed(errorBlock, false)) {

					Reporter.logEvent(
							Status.PASS,
							"Verify No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button",
							"No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button and user is on Summary Page",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button",
							"User Navigated to confirmation Page When User Clicks on 'I Agree, Enroll Now' Button ",
							true);
				}
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Error Message is Displayed in Summary Page",
						"Error Message is Not Displayed in Summary Page", true);

			enterDeferralAmountinSummaryPage("");

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Summary Page As New User
	 * </pre>
	 * 
	 * @param contributionRate
	 */

	public void VerifySummaryPageAsNewUser(String contributionRate) {

		try {

			if (Web.isWebElementDisplayed(btnEditMyOptions, true)) {

				Reporter.logEvent(
						Status.PASS,
						"Verify 'Edit My Options' Button is  is Dispalyed in Summary Page",
						"'Edit My Options' Button is Dispalyed in Summary Page",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Edit My Options' Button is Dispalyed in Summary Page",
						"'Edit My Options' Button is Not Dispalyed in Summary Page",
						true);
			}
			String expectedText = "By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the Participation Agreement for Online Enrollment";
			String actualText = lblIAgree.getText().toString().trim() + " "
					+ lnkPartipantAgreementForm.getText().toString().trim();
			if (expectedText.equals(actualText)) {

				Reporter.logEvent(Status.PASS,
						"Verify Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is dispalyed on the Summary Page",
						"Standard Enrollment Disclosure ' " + expectedText
								+ "' is dispalyed on the Summary Page", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is dispalyed on the Summary Page",
						"Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is not Same on the Summary Page\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);
			}
			if (Web.isWebElementDisplayed(btnYes)) {
				btnYes.click();
			}
			Web.clickOnElement(btnEditMyOptions);

			verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			clickContinue();
			verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			clickContinue();

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Summary Page As Returning User
	 * </pre>
	 * 
	 * @param gc_id
	 *            ,plan_id,ga_Id
	 */

	public void VerifySummaryPageAsReturningUser(String gc_id, String plan_id,
			String ga_Id) {

		try {
			VerifyPageHeaderForContinueToEnrollPage(gc_id, plan_id);
			VerifyPanelTitle(ga_Id);
			if (Web.isWebElementDisplayed(btnEditMyOptions, true)) {

				Reporter.logEvent(
						Status.PASS,
						"Verify 'Edit My Options' Button is  is Dispalyed in Summary Page",
						"'Edit My Options' Button is Dispalyed in Summary Page",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Edit My Options' Button is Dispalyed in Summary Page",
						"'Edit My Options' Button is Not Dispalyed in Summary Page",
						true);
			}
			String expectedText = "By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the Participation Agreement for Online Enrollment";
			String actualText = lblIAgree.getText().toString().trim() + " "
					+ lnkPartipantAgreementForm.getText().toString().trim();
			if (expectedText.equals(actualText)) {

				Reporter.logEvent(Status.PASS,
						"Verify Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is dispalyed on the Summary Page",
						"Standard Enrollment Disclosure ' " + expectedText
								+ "' is dispalyed on the Summary Page", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is dispalyed on the Summary Page",
						"Standard Enrollment Disclosure ' "
								+ expectedText
								+ "' is not Same on the Summary Page\nExpected:"
								+ expectedText + "\nActual:" + actualText,
						false);
			}
			if (Web.isWebElementDisplayed(btnYes)) {
				btnYes.click();
			}
			Web.clickOnElement(btnEditMyOptions);

			verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			clickContinue();
			verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			clickContinue();

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Page Header for Continue to EnrollMent Page
	 * </pre>
	 *
	 */

	public void VerifyPageHeaderForContinueToEnrollPage(String gc_Id,
			String planId) {

		try {
			Web.waitForElement(hdrforContinueToEnrollmentPage);

			String expectedText = hdrforContinueToEnrollmentPage.getText()
					.toString().trim();
			String actualText = Common.getPlanName(gc_Id, planId);
			if (expectedText.equals(actualText)) {
				Reporter.logEvent(Status.PASS,
						"Verify Enrollment Page Header is Displayed",
						"Enrollment Page Header is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText, true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Enrollment Page Header is Displayed",
						"Enrollment Page Header is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText, true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Panel Title
	 * </pre>
	 *
	 */

	public void VerifyPanelTitle(String ga_Id) {

		try {
			Web.waitForElement(panelTitle);

			String expectedText = panelTitle.getText().toString().trim();
			String actualText = Common.getEnrollmentPanelHeading(ga_Id);
			if (expectedText.equals(actualText)) {
				Reporter.logEvent(Status.PASS,
						"Verify Enrollment Panel Header is Displayed",
						"Enrollment Panel Header is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText, true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Enrollment Panel Header is Displayed",
						"Enrollment Panel Header is Displayed\nExpected:"
								+ expectedText + "\nActual:" + actualText, true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Method to Verify previously selected Distributions are same when the user logged  As Returning User
	 * </pre>
	 * 
	 * @param disbursmentDetails
	 */

	public void VerifyDistibutionElectionPageAsReturningUser(
			HashMap<String, String> disbursmentMethod,
			HashMap<String, String> disbursmentTiming) {

		try {

			Web.waitForElement(hdrDistributionElection);

			for (Entry<String, String> entry : disbursmentMethod.entrySet()) {
				String disbReason = entry.getKey();
				String disbMethod = entry.getValue();

				WebElement element = Web.getDriver().findElement(
						By.xpath(strDisbReasonMethod.replace("DisbReason",
								disbReason).replace("DisbMethod", disbMethod)));

				if (element.isDisplayed() && element.isSelected()) {

					Reporter.logEvent(
							Status.PASS,
							"Verify Previously Selected Distribution Method is Defaulted Correctly",
							"Previously Selected Distribution Method is Defaulted Correctly'"
									+ disbMethod + "' For" + disbReason, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Selected Distribution Method is Defaulted Correctly",
							"Previously Selected Distribution Method is Not Defaulted Correctly '"
									+ disbMethod + "' For" + disbMethod, true);
				}

				WebElement inpTiming = Web.getDriver().findElement(
						By.xpath(strTerminationOrRetirementMethod.replace(
								"webElementText",
								disbursmentTiming.get(disbMethod))));

				if (inpTiming.isDisplayed() && inpTiming.isSelected()) {

					Reporter.logEvent(
							Status.PASS,
							"Verify Previously Selected Distribution Timing is Defaulted Correctly",
							"Previously Selected Distribution Timing is Defaulted Correctly'"
									+ disbursmentTiming.get(disbMethod)
									+ "' For" + disbMethod, true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Selected Distribution Timing is Defaulted Correctly",
							"Previously Selected Distribution Timing is Not Defaulted Correctly '"
									+ disbursmentTiming.get(disbMethod)
									+ "' For" + disbMethod, true);
				}

			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * Method to Verify Confirmation Page for Enrollment flow
	 * </pre>
	 * 
	 * @param contributionRate
	 */

	public void VerifyConfirmationPage(String contributionRate) {

		try {
			verifyWebElementDisplayed("Congratulations!");
			enterDeferralAmountinSummaryPage(contributionRate);
			if (Web.isWebElementDisplayed(errorBlock, true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Error Message is Displayed in Summary Page",
						"Error Message is Displayed in Summary Page", true);
				if (!btnEditMyOptions.isEnabled()) {
					Reporter.logEvent(
							Status.PASS,
							"Verify 'Edit My Options' Button is disabled When Error Message is Dispalyed in Summary Page",
							"'Edit My Options' Button is Disabled When Error Message is Dispalyed in Summary Page",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Edit My Options' Button is disabled When Error Message is Dispalyed in Summary Page",
							"'Edit My Options' Button is Not Disabled When Error Message is Dispalyed in Summary Page",
							true);
				}
				Web.clickOnElement(btnIAgreeEnrollNow);
				if (Web.isWebElementDisplayed(errorBlock, false)) {

					Reporter.logEvent(
							Status.PASS,
							"Verify No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button",
							"No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button and user is on Summary Page",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify No Action is Performed When User Clicks on 'I Agree, Enroll Now' Button",
							"User Navigated to confirmation Page When User Clicks on 'I Agree, Enroll Now' Button ",
							true);
				}
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Error Message is Displayed in Summary Page",
						"Error Message is Not Displayed in Summary Page", true);

			enterDeferralAmountinSummaryPage("");

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

}
