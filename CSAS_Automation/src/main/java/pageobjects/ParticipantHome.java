package pageobjects;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import framework.util.CommonLib;

public class ParticipantHome extends LoadableComponent<ParticipantHome> {

	private ArrayList<String> hireTermDateList;
	ArrayList<String> personalDataDB;
	ArrayList<String> plan_And_Participant_List;

	// CSAS Login..

	@FindBy(xpath = "//span[contains(text(),'CLIENT SERVICE ACCESS SYSTEM USER LOGON')]")
	private WebElement CSASLoginHome;

	@FindBy(css = "input[name = 'username']")
	private WebElement CSASUserNameField;

	@FindBy(css = "input[name = 'password']")
	private WebElement CSASPwdField;

	@FindBy(css = "input[value='Log In']")
	private WebElement CSASLoginBtn;

	@FindBy(xpath = "//a[contains(text(),'Registered')]//td[@class = 'colTitle rightJust']")
	private WebElement Reg_Status_UserName_Label;

	@FindBy(xpath = "//a[contains(text(),'Registered')]//td[@class = 'data']")
	private WebElement Reg_Status_UserName_data;

	@FindBy(css = "table#partList")
	private WebElement partList_Tab;

	@FindBy(css = "table#partList tr>td:nth-of-type(15)")
	private List<WebElement> PlanNoOnPartList_Link;

	// Participant Plan Search..

	@FindBy(xpath = "//*[@id='titleTab']/tbody/tr/td[4]/font/a")
	private WebElement lnkLogOut;

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT/PLAN SEARCH')]")
	private WebElement participantPlanSearchPage;

	@FindBy(css = "input[name = 'searchPartId']")
	private WebElement PPTIdfield;

	@FindBy(id = "submitPpt")
	private WebElement SubmitPPTIdBtn;

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT HOME PAGE')]")
	private WebElement PPTHomePageTitle;

	@FindBy(css = "input[name = 'searchPartSsn']")
	private WebElement SSNfield;

	// Menu items..
	@FindBy(xpath = "//*[@id='oCMenu_316'][contains(text(),'Participant Changes')]")
	private WebElement menuPPTChanges;
	
	@FindBy(css = "div#oCMenu_315")
	private WebElement MenuParticipantInfo;

	@FindBy(css = "div#oCMenu_316")
	private WebElement MenuParticipantChanges;

	@FindBy(css = "div#oCMenu_317")
	private WebElement MenuPlanInfo;

	@FindBy(css = "div#oCMenu_318")
	private WebElement menuAddtlResources;

	@FindBy(css = "div#oCMenu_319")
	private WebElement menuSearch;

	@FindBy(css = "div#oCMenu_320")
	private WebElement menuContactReason;

	@FindBy(css = "div#oCMenu_15000")
	private WebElement menuIRA;

	@FindBy(css = "div#oCMenu_20519")
	private WebElement menuPlanSetup;

	@FindBy(css = "div#oCMenu_5555")
	private WebElement menuTestPages;

	@FindBy(css = "oCMenu_15483")
	private WebElement menuAdmin;

	@FindBy(name = "username")
	private WebElement userNameField;

	@FindBy(name = "password")
	private WebElement passWordField;

	// Account Balance..

	@FindBy(css = "td.colTitle.balance")
	private WebElement tdParticipantBalance;

	@FindBy(css = "td.data.balance>a.floatingDiv")
	private WebElement lnkHoverablePlanBalance;

	@FindBy(css = "div#overDiv[style *= 'visible']>table")
	private WebElement lnkHoverablePlanBalanceAfterHover;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Vested Balance')]")
	private WebElement tbVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Non-Vested Balance')]")
	private WebElement tbNonVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Current Balance')]")
	private WebElement tbCurrentBal;

	// Required List implementaions..

	@FindBy(css = "td[valign='top'] table.compactDataTable tr>td:nth-of-type(1)")
	private List<WebElement> numberOFRowsInBalPopUp;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(2)>td")
	private List<WebElement> tbTotalVarBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(2)>td")
	private List<WebElement> tbTotalFixBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(4)>td")
	private List<WebElement> tbTotalExcludingLoans;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(5)>td")
	private List<WebElement> tbTotalLoanBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(6)>td")
	private List<WebElement> tbTotalIncludingLoan;

	@FindBy(xpath = "//td[contains(text(),'Total Balance:')]")
	private WebElement participantTotalBalLabel;

	@FindBy(css = "td.total.balance")
	private WebElement participantTotalBalVal;

	// Personal Data...
	// Required list implementation..

	@FindBy(css = "table[name = 'pptinfo'] tr>td.data")
	private List<WebElement> personalData_On_PPT_Home_List;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Name:')]")
	private WebElement participantNameLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(3)>td.data")
	private WebElement participantName;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SSN')]")
	private WebElement participantSSNLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(4)>td.data")
	private WebElement participantSSN;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Date Of Birth')]")
	private WebElement participantDOBLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(5)>td.data")
	private WebElement participantDOB;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Gender')]")
	private WebElement participantGenderLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(6)>td.data")
	private WebElement participantGender;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Address')]")
	private WebElement participantAddLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(7)>td.data")
	private WebElement participantAdd;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Mail Hold')]")
	private WebElement participantMailLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(8)>td.data")
	private WebElement participantMail;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Cash Hold Status')]")
	private WebElement participantCHSLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(9)>td.data")
	private WebElement participantCHS;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Web Registration Status')]")
	private WebElement participantRegStatusLabel;

	@FindBy(xpath = "//a[contains(text(),'Registered')]")
	private WebElement participantRegStatus;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Managed Account Status')]")
	private WebElement participantMngAccStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(12)>td.data")
	private WebElement participantMngAccSts;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SecureFoundation Status')]")
	private WebElement participantSecFoundationStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(13)>td.data")
	private WebElement participantSecFoundationSts;

	// Order PIN..
	// @FindBy(xpath =
	// "(//div[@class='dataContainerBody'])[2]/table/tbody/tr/td[2]/table/tbody/tr[5]/td/a[2]")
	@FindBy(xpath = "//a[contains(text(), 'Order PIN ')]")
	private WebElement lnkOrderPIN;

	@FindBy(css = "table#table_popupLayout tr:nth-of-type(2) span.titleShadow")
	private WebElement OrderPINTitle;

	@FindBy(css = "input[value = 'Mail Existing PIN']")
	private WebElement btnMailExistingPIN;

	@FindBy(css = "input[value = 'Order Temp PIN']")
	private WebElement btnOrderTempPin;

	@FindBy(css = "input[value = 'Mail Existing VRU PIN and Web Passcode']")
	private WebElement btnMailExstngVRUPINandWebPscd;

	@FindBy(css = "input[value = 'Mail Existing Web Passcode']")
	private WebElement btnMailExstngWebPscd;

	@FindBy(css = "input[value = 'Mail Existing VRU PIN']")
	private WebElement btnMailExstngVRUPIN;

	@FindBy(css = "input[value = 'Order Temp VRU PIN and Web Passcode']")
	private WebElement btnOrderTempPINandWebPscd;

	@FindBy(css = "input[value = 'Order Temp Web Passcode']")
	private WebElement btnOrderTempWebPscd;

	@FindBy(css = "input[value = 'Order Temp VRU PIN']")
	private WebElement btnOrderTempVRUPIN;

	@FindBy(css = "table[id='table_messageHandlerMessage'] div[class='messageContent']")
	private List<WebElement> txtExistingAndOrderPINMessage;

	@FindBy(xpath = "//*[@id='table_messageHandlerMessage']/tbody/tr/td[1]/img")
	private WebElement imgInfoMsg;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(8)")
	private WebElement EmploymentStatusLabel;

	// implement List...
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(8)>a")
	// "//a[.//table[@id = '#']]"
	private List<WebElement> lnkEmploymentStatus;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td.data:nth-of-type(4)")
	private List<WebElement> PlanNumber;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td.data:nth-of-type(7)")
	private List<WebElement> IndID_List;

	@FindBy(css = "#table_employmentStatusFDiv>tbody>tr:nth-of-type(1)>td:nth-of-type(2)")
	private List<WebElement> HireDate_List;

	@FindBy(css = "#table_employmentStatusFDiv>tbody>tr:nth-of-type(2)>td:nth-of-type(2)")
	private List<WebElement> TermDate_List;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td.data:nth-of-type(7)")
	private WebElement Individual_ID;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(9)")
	private WebElement PDILabel;

	// implement List...
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(9)")
	private List<WebElement> PDIStatus;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(10)")
	private WebElement InstanceLabel;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'data centered']:nth-of-type(10)")
	private List<WebElement> InstanceValue_List;

	@FindBy(css = "table.compactDataTable tr:nth-of-type(2) input[type = 'radio'][checked = 'checked']")
	private WebElement PPT_Indx_Radio_Btn;
	//F2F
	@FindBy(xpath = "//div[contains(@id,'oCMenu')][contains(text(),'Cancel Pending Transfer')]")
	private WebElement menuCancelF2FLink;
	
	//@FindBy(xpath = "//table[@id='table_messageHandlerMessage']//div[contains(text(),'EMPLOYER STOCK')]")
	//@FindBy(xpath = "//table[@id='table_messageHandlerMessage']//div[@class = 'messageContent']")
	@FindBy(xpath = "//td[contains(text(),'Select Pending Transfer To Cancel')]")
	private WebElement cancelFndTransferPgHeader;
	
	@FindBy(xpath = "//table[@id='table_workLayout']//table[@align='center']//input[@type='checkbox']")
	private List<WebElement> cancelFndTransferCheckBox;
	
	@FindBy(xpath = "//input[@value='Confirm Cancel Of Transfer']")
	private WebElement btnConfCancelTranfer;
	
	@FindBy(xpath = "//input[@value='Submit Cancellation']")
	private WebElement btnSubmitCancelTransfer;
	
	// Participants changes Menu Webelements..
	@FindBy(xpath = "//div[contains(text(),'Contribution Allocation Change')]")
	private WebElement ContrAllChngLink;

	@FindBy(xpath = "//*[@id='oCMenu_324'][contains(text(),'Contribution Allocation Change')]")
	private WebElement ContrAllChngSubLink;

	@FindBy(css = "table[id='table_messageHandlerMessage']")
	private LoadableComponent<?> parent;

	/*-----------------------------------------------------------------*/
	private Map<String, String> Mail_Existing_PIN = new LinkedHashMap<String, String>();
	private Map<String, String> Order_Temp_PIN = new LinkedHashMap<String, String>();
	private Map<String, String> Mail_Existing_VRUPIN_and_Web_PassCode = new LinkedHashMap<String, String>();
	private Map<String, String> Mail_Existing_Web_Passcode = new LinkedHashMap<String, String>();
	private Map<String, String> Mail_Existing_VRUPIN = new LinkedHashMap<String, String>();
	private Map<String, String> Order_Temp_VRU_PIN_and_Web_PassCode = new LinkedHashMap<String, String>();
	private Map<String, String> Order_Temp_Web_PassCode = new LinkedHashMap<String, String>();
	private Map<String, String> Order_Temp_VRUPIN = new LinkedHashMap<String, String>();

	private void initTempandOrderPINMsg() {
		Mail_Existing_PIN
				.put("MSG1",
						"Combined verification and pin letter generated: doc_id = "
								+ " If a PIN confirmation letter doc id is displayed then a reminder PIN letter will be"
								+ " sent to the participant.  If \"No document to generate.\" displays then a"
								+ " reminder PIN letter will not be sent until PIN has been system generated."
								+ " CSR should not attempt to issue through ISIS.");
		Order_Temp_PIN
				.put("MSG1",
						"Note: this VRU PIN (or web passcode) is a single use PIN (passcode) "
								+ "that must be re-set after first use. Please direct participant accordingly.");
		Order_Temp_PIN.put("MSG2", "The temporary PIN number is");

		Mail_Existing_VRUPIN_and_Web_PassCode.put("MSG1",
				"An order to mail the participant's existing web passcode "
						+ "has been issued, confirmation #.");
		Mail_Existing_VRUPIN_and_Web_PassCode.put("MSG2",
				"An order to mail the participant's existing VRU PIN "
						+ "has been issued, confirmation #.");

		Mail_Existing_Web_Passcode.put("MSG1",
				"An order to mail the participant's existing web passcode "
						+ "has been issued, confirmation #.");

		Mail_Existing_VRUPIN.put("MSG1",
				"An order to mail the participant's existing VRU PIN "
						+ "has been issued, confirmation #.");

		Order_Temp_VRU_PIN_and_Web_PassCode
				.put("MSG1",
						"Note: this VRU PIN (or web passcode) is a single use PIN (passcode) that must "
								+ "be re-set after first use. "
								+ "Please direct participant accordingly.");
		Order_Temp_VRU_PIN_and_Web_PassCode.put("MSG2",
				"The temporary web passcode number is");
		Order_Temp_VRU_PIN_and_Web_PassCode.put("MSG3",
				"The temporary VRU PIN number is");

		Order_Temp_Web_PassCode
				.put("MSG1",
						"Note: this VRU PIN (or web passcode) is a single use PIN (passcode) that must "
								+ "be re-set after first use. "
								+ "Please direct participant accordingly.");
		Order_Temp_Web_PassCode.put("MSG2",
				"The temporary web passcode number is");

		Order_Temp_VRUPIN
				.put("MSG1",
						"Note: this VRU PIN (or web passcode) is a single use PIN (passcode) that must "
								+ "be re-set after first use. "
								+ "Please direct participant accordingly.");
		Order_Temp_VRUPIN.put("MSG2", "The temporary VRU PIN number is");
	}

	public ParticipantHome() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.getDriver().getTitle().contains("CSAS v12.03.2"));
	}

	@Override
	protected void load() {
		try {
			// Web.getDriver().get(Stock.getConfigParam("AppURL"));
			Web.getDriver().get(Stock.getConfigParam("AppURL" + "_"
					+ Stock.getConfigParam("TEST_ENV")));
			/*
			 * System.out.println(Stock.getConfigParam("AppURL" + "_" +
			 * Stock.getConfigParam("TEST_ENV")));
			 */
			Reporter.logEvent(Status.INFO,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			submitLoginCredentials(Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, e.getMessage());
		}

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

		if (fieldName.trim().equalsIgnoreCase("PPT_ID")) {
			return this.PPTIdfield;
		}

		if (fieldName.trim().equalsIgnoreCase("SSN")) {
			return this.SSNfield;
		}

		if (fieldName.trim().equalsIgnoreCase("Mail_Existing_PIN")) {
			return this.btnMailExistingPIN;
		}

		if (fieldName.trim().equalsIgnoreCase("Order_Temp_PIN")) {
			return this.btnOrderTempPin;
		}

		if (fieldName.trim().equalsIgnoreCase(
				"Mail_Existing_VRUPIN_and_Web_PassCode")) {
			return this.btnMailExstngVRUPINandWebPscd;
		}

		if (fieldName.trim().equalsIgnoreCase("Mail_Existing_Web_Passcode")) {
			return this.btnMailExstngWebPscd;
		}

		if (fieldName.trim().equalsIgnoreCase("Mail_Existing_VRUPIN")) {
			return this.btnMailExstngVRUPIN;
		}

		if (fieldName.trim().equalsIgnoreCase(
				"Order_Temp_VRU_PIN_and_Web_PassCode")) {
			return this.btnOrderTempPINandWebPscd;
		}

		if (fieldName.trim().equalsIgnoreCase("Order_Temp_Web_PassCode")) {
			return this.btnOrderTempWebPscd;
		}

		if (fieldName.trim().equalsIgnoreCase("Order_Temp_VRUPIN")) {
			return this.btnOrderTempVRUPIN;
		}

		if (fieldName.trim().equalsIgnoreCase("Plan_No")) {
			return this.PlanNumber.get(0);
		}

		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * <pre>
	 * Method to get PPT ID based on the registration status
	 * </pre>
	 * 
	 * @param WebRegStatus
	 * @return
	 * @throws Exception
	 * @author RANJAN
	 */
	public Map<String, String> getSSN_or_pptID(String WebRegStatus,
			String... paramNm) throws Exception {
		ResultSet resultset = null;
		Map<String, String> res = new LinkedHashMap<String, String>();

		if (WebRegStatus.equalsIgnoreCase("Registered")) {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getPPTIDforWebRegStatus")[0],
					Stock.getTestQuery("getPPTIDforWebRegStatus")[1]);
		} else if (WebRegStatus.equalsIgnoreCase("Not Registered")) {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getPPTIDforWebNonRegStatus")[0],
					Stock.getTestQuery("getPPTIDforWebNonRegStatus")[1]);
		}

		if (resultset != null) {
			while (resultset.next()) {
				for (String param : paramNm) {
					res.put(param, resultset.getString(param));
				}
			}
		}
		return res;
	}
	
	/**
	 * <pre>
	 * Method to get PPT ID based on the registration status
	 * </pre>
	 * 
	 * @param WebRegStatus
	 * @return
	 * @throws Exception
	 * @author SOUVIK
	 */
	public Map<String, String> getSSN_or_pptID(String WebRegStatus,boolean ifPlanNumReqd,String... paramNm) throws Exception {
		ResultSet resultset = null;
		Map<String, String> res = new LinkedHashMap<String, String>();
		String planNum = Globals.GC_EMPTY; 
		
		if(ifPlanNumReqd){
			planNum =  Stock.GetParameterValue("planID");
		}else{
			planNum = "194037-01"; // Default Plan number
		}
		
		if (WebRegStatus.equalsIgnoreCase("Registered")) {
			resultset = DB.executeQuery(
						Stock.getTestQuery("getPPTIDforOrderPIN_Reg")[0],
						Stock.getTestQuery("getPPTIDforOrderPIN_Reg")[1],planNum);
		
		} else if (WebRegStatus.equalsIgnoreCase("Not Registered")) {
			resultset = DB.executeQuery(
						Stock.getTestQuery("getPPTIDforOrderPIN_NonReg")[0],
						Stock.getTestQuery("getPPTIDforOrderPIN_NonReg")[1],planNum);
		}
		if (resultset != null) {
			while (resultset.next()) {
				for (String param : paramNm) {
					res.put(param, resultset.getString(param));
				}
			}
		}
		return res;
	}

	/**
	 * <pre>
	 * Method to get GA ID based on the registration status
	 * </pre>
	 * 
	 * @param WebRegStatus
	 * @return
	 * @throws Exception
	 * @author RANJAN
	 */
	public String getSSN_or_pptID_EmpSts(String ppt_id) throws Exception {
		ResultSet resultset;
		String plan_Num = null;
		resultset = DB.executeQuery(Stock.getTestQuery("getGAID")[0],
				Stock.getTestQuery("getGAID")[1], ppt_id);
		if (resultset != null) {
			while (resultset.next()) {
				plan_Num = resultset.getString("gc_id");
			}
		}
		return plan_Num;
	}

	/**
	 * <pre>
	 * Method to enter user credentials and click on Sign In button
	 * </pre>
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 * @author RANJAN
	 */
	public void submitLoginCredentials(String username, String password)
			throws Exception {
		boolean isElementDisplayed = false;
		Web.waitForElement(CSASUserNameField);
		Web.setTextToTextBox(CSASUserNameField, username);

		Web.setTextToTextBox(CSASPwdField, password);
		Web.clickOnElement(CSASLoginBtn);

		Web.waitForElement(participantPlanSearchPage);
		isElementDisplayed = Web.isWebElementDisplayed(
				participantPlanSearchPage, true);
		if (isElementDisplayed) {
			Reporter.logEvent(
					Status.PASS,
					"Check if the user logged in to Participant/plan search page",
					"User is successfully logged in to Participant/plan search page",
					false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if the user logged in to Participant/plan search page",
					"User is not logged in to Participant/plan search page",
					true);
		}
	}

	/**
	 * <pre>
	 * Method to enter participantID or SSN and click on Sign In button
	 * </pre>
	 * 
	 * @param ppt_id
	 * @throws Exception
	 * @author RANJAN
	 */
	public void search_PPT_Plan_With_PPT_ID_OR_SSN(String ssnOrIDElement,
			String... searchValue) throws Exception {
		WebElement searchField = getWebElement(ssnOrIDElement.toUpperCase());

		boolean isElementDisplayed = false;
		Web.waitForElement(menuSearch);
		Web.clickOnElement(menuSearch);
		Web.setTextToTextBox(searchField, searchValue[0]);
		Reporter.logEvent(Status.INFO, "Performing search using PPT ID/SSN",
				"PPT ID/SSN : " + searchValue[0], true);
		Web.clickOnElement(SubmitPPTIdBtn);

		// ------------- Handle Multiple PPT Search Result -----
		if (Web.isWebElementsDisplayed(PlanNoOnPartList_Link)
				&& PlanNoOnPartList_Link.size() > 1) {
			click_And_Verify_Plan_On_Search_Page(searchValue[1]);
		}

		Web.waitForElement(PPTHomePageTitle);
		isElementDisplayed = Web.isWebElementDisplayed(PPTHomePageTitle, true);
		if (isElementDisplayed) {
			Reporter.logEvent(
					Status.PASS,
					"Participant Home Page with all details should display",
					"Participant Home Page with all the details are displayed successfully",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Participant Home Page with all details should display",
					"Participant Home Page with all the details are not displayed",
					true);
		}
	}

	/**
	 * <pre>
	 * Method to verify Employment status
	 * </pre>
	 * 
	 * @param
	 * @author RANJAN
	 * @throws Exception
	 */
	public void verify_Employment_Status(String ppt_id, String emp_Status)
			throws Exception {
		List<String> HireDate_TermDate_List;
		WebElement empStatus_WE = null;
		int noOFPlans = 0;
		boolean chkEmploymentStats = false;

		noOFPlans = PlanNumber.size();
		if (PlanNumber.size() <= 0) {
			throw new AssertionError("Plan number not displayed");
		}
		if (IndID_List.size() <= 0) {
			throw new AssertionError("Indiviual ID not displayed");
		}
		if (lnkEmploymentStatus.size() <= 0) {
			throw new AssertionError("Employment Status not displayed");
		}

		for (int i = 0; i < noOFPlans; i++) {
			String planNum = PlanNumber.get(i).getText();
			String ind_id = IndID_List.get(i).getText();
			planNum = planNum.substring(0, planNum.indexOf('-'));

			HireDate_TermDate_List = get_Hire_Term_Date_From_DB(
					Stock.getTestQuery("getHireDateAndTerminationDate"),
					planNum, ind_id, i);
			empStatus_WE = lnkEmploymentStatus.get(i);

			if (HireDate_List.size() <= 0) {
				throw new AssertionError("Hire Date not displayed");
			}
			if (TermDate_List.size() <= 0) {
				throw new AssertionError("Termination Date not displayed");
			}

			if (empStatus_WE.getText().contains("ACTIVE")
					&& emp_Status.equalsIgnoreCase("ACTIVE")) {
				Web.mouseHover(empStatus_WE);
				if ((HireDate_TermDate_List.get(1) == null)
						&& CommonLib.compareDB_Date_With_Web_Date(
								HireDate_TermDate_List.get(0), HireDate_List
										.get(i).getText())) {
					chkEmploymentStats = true;
				}
			} else if (empStatus_WE.getText().contains("TERMINATED")
					&& emp_Status.equalsIgnoreCase("TERMINATED")) {
				Web.mouseHover(empStatus_WE);
				if ((CommonLib.compareDB_Date_With_Web_Date(
						HireDate_TermDate_List.get(0), HireDate_List.get(i)
								.getText()) && CommonLib
						.compareDB_Date_With_Web_Date(HireDate_TermDate_List
								.get(1), TermDate_List.get(i).getText()))) {
					chkEmploymentStats = true;
				}
			} else if (!empStatus_WE.equals(emp_Status)) {
				continue;
			}
			if (chkEmploymentStats) {
				Reporter.logEvent(
						Status.PASS,
						"Check if Employment status for plan number : "
								+ planNum + " is " + emp_Status
								+ ", Hire date: "
								+ HireDate_TermDate_List.get(0)
								+ "and Termination date: "
								+ HireDate_TermDate_List.get(1),
						"Employment status for plan number : " + planNum
								+ " is " + emp_Status
								+ " and the Hire Date is: "
								+ HireDate_List.get(i).getText()
								+ "and Termination date: "
								+ TermDate_List.get(i).getText(), true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Employment status is " + emp_Status
								+ ", Hire date: "
								+ HireDate_TermDate_List.get(0)
								+ "and Termination date: "
								+ HireDate_TermDate_List.get(1),
						"Employment status plan number : " + planNum
								+ " is neither " + emp_Status
								+ " nor the Hire Date is: "
								+ HireDate_List.get(i).getText()
								+ " and Termination date: "
								+ TermDate_List.get(i).getText(), true);
			}
			Web.clickOnElement(PPTHomePageTitle);
		}
	}

	/**
	 * <pre>
	 * Method to get Hire date and Termination date from DB for a participant
	 * </pre>
	 * 
	 * @param getHireDateAndTerminationDate
	 * @param planNum
	 * @param indID
	 * @param index
	 * @return
	 * @author RANJAN
	 * @throws Exception
	 */
	public ArrayList<String> get_Hire_Term_Date_From_DB(
			String[] getHireDateAndTerminationDate, String planNum,
			String indID, int index) throws Exception {

		ResultSet resultset;
		hireTermDateList = new ArrayList<String>();
		resultset = DB.executeQuery(getHireDateAndTerminationDate[0],
				getHireDateAndTerminationDate[1], indID, planNum);
		if (resultset != null) {

			while (resultset.next()) {
				String hireDate = resultset.getString("hire_date");
				String termDate = resultset.getString("emp_termdate");
				hireTermDateList.add(hireDate);
				hireTermDateList.add(termDate);
			}
		}
		return hireTermDateList;
	}

	/**
	 * <pre>
	 * Method to verify database instance on Participant home page
	 * </pre>
	 * 
	 * @throws Exception
	 * @author RANJAN
	 */
	public void verify_Page_Instance() throws Exception {
		String instance_DB;
		String instance_Web;
		String ind_id;
		String ga_id;
		boolean flag;
		flag = Web.isWebElementDisplayed(InstanceLabel, true);
		if (flag) {
			Reporter.logEvent(Status.PASS,
					"Check if Instance label is present ",
					"Instance Label is present", true);
			if (InstanceValue_List.size() <= 0) {
				throw new AssertionError(
						"Plan does not diaplay any database instance value");
			}
			for (int i = 0; i < InstanceValue_List.size(); i++) {
				instance_Web = InstanceValue_List.get(i).getText();
				ind_id = IndID_List.get(i).getText();
				ga_id = PlanNumber.get(i).getText();
				instance_DB = get_Page_Instance_From_DB(
						Stock.getTestQuery("getPageInstanceFromInd_id"), ind_id);
				if (instance_Web.equalsIgnoreCase(instance_DB)) {
					Reporter.logEvent(Status.PASS,
							"Check if database name for individual ID:  "
									+ ind_id + " and plan number " + ga_id
									+ "  is: " + instance_DB,
							"Check if database name for individual ID :"
									+ ind_id + " and plan number " + ga_id
									+ "  is: " + instance_Web, false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if database name for individual ID:  "
									+ ind_id + " and plan number " + ga_id
									+ "  is not : " + instance_DB,
							"Check if database name for individual ID :"
									+ ind_id + " and plan number " + ga_id
									+ "  is not : " + instance_Web, true);
				}
			}
		} else
			Reporter.logEvent(Status.FAIL,
					"Check if Instance label is not present ",
					"Instance Label is not present", true);
	}

	/**
	 * <pre>
	 * Method to verify Page instance from DB
	 * </pre>
	 * 
	 * @param getPageInstanceFromInd_id
	 * @param ind_id
	 * @return
	 * @author RANJAN
	 * @throws Exception
	 */
	public String get_Page_Instance_From_DB(String[] getPageInstanceFromInd_id,
			String ind_id) throws Exception {

		ResultSet resultset;
		String instance = null;
		resultset = DB.executeQuery(getPageInstanceFromInd_id[0],
				getPageInstanceFromInd_id[1], ind_id);
		if (resultset != null) {
			while (resultset.next()) {
				instance = resultset.getString("database_instance");
			}
		}
		return instance;
	}

	/**
	 * <pre>
	 * Validate Personal Data on PPT home page
	 * </pre>
	 * 
	 * @param = SSN
	 * @author RANJAN
	 * @throws Exception
	 */
	public void verify_Personal_Data(String ssn) throws Exception {

		ArrayList<String> personal_Data_From_DB;
		personal_Data_From_DB = get_Personal_Data_From_DB(
				Stock.getTestQuery("getPersonalDataOnPPTHomePage"), ssn);
		if (!personal_Data_From_DB.isEmpty()) {
			// Personal data validation..
			if (personalData_On_PPT_Home_List.size() == 0) {
				throw new AssertionError("Personal data is not displayed");
			}
			String FullName_DB = personal_Data_From_DB.get(0) + " "
					+ personal_Data_From_DB.get(1);
			String SSN = personalData_On_PPT_Home_List.get(1).getText();
			String[] splitedStr = SSN.split("-");
			String concatenated_SSN = null;
			concatenated_SSN = splitedStr[0] + splitedStr[1] + splitedStr[2];
			if (personalData_On_PPT_Home_List.get(0).getText()
					.equalsIgnoreCase(FullName_DB)
					&& CommonLib.compareDB_Date_With_Web_Date(
							personal_Data_From_DB.get(3),
							personalData_On_PPT_Home_List.get(2).getText())
					&& concatenated_SSN.equalsIgnoreCase(personal_Data_From_DB
							.get(2))
					&& personalData_On_PPT_Home_List.get(3).getText()
							.contains(personal_Data_From_DB.get(4))) {
				Reporter.logEvent(
						Status.PASS,
						"Check if Name,SSN,Date Of Birth and Gender in database & web is same or not \n\n\n"
								+ FullName_DB
								+ "\n"
								+ personal_Data_From_DB.get(2)
								+ "\n"
								+ personal_Data_From_DB.get(3)
								+ "\n"
								+ personal_Data_From_DB.get(4),
						"Name,SSN,Date Of Birth and Gender in database in database & web is same \n"
								+ personalData_On_PPT_Home_List.get(0)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(1)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(2)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(3)
										.getText() + "\n", false);
			} else {

				Reporter.logEvent(
						Status.FAIL,
						"Check if Name,SSN,Date Of Birth and Gender in database & web is same or not \n\n\n"
								+ FullName_DB
								+ "\n"
								+ personal_Data_From_DB.get(2)
								+ "\n"
								+ personal_Data_From_DB.get(3)
								+ "\n"
								+ personal_Data_From_DB.get(4),
						"Name,SSN,Date Of Birth and Gender in database in database & web is not same \n"
								+ personalData_On_PPT_Home_List.get(0)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(1)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(2)
										.getText()
								+ "\n"
								+ personalData_On_PPT_Home_List.get(3)
										.getText() + "\n", true);
			}
		} else {
			Reporter.logEvent(Status.INFO,
					"Validate personal data from DB for the SSN:  " + ssn,
					"No records found in DB", false);
		}
	}

	/**
	 * <pre>
	 * Method to validate personal data
	 * </pre>
	 * 
	 * @param - getPersonalDataOnPPTHomePage,ssn
	 * @author RANJAN
	 * @throws Exception
	 */
	public ArrayList<String> get_Personal_Data_From_DB(
			String[] getPersonalDataOnPPTHomePage, String ssn) throws Exception {
		ResultSet resultset;
		personalDataDB = new ArrayList<String>();
		resultset = DB.executeQuery(getPersonalDataOnPPTHomePage[0],
				getPersonalDataOnPPTHomePage[1], ssn);
		if (resultset != null) {
			while (resultset.next()) {
				personalDataDB.add(resultset.getString("first_name"));
				personalDataDB.add(resultset.getString("last_name"));
				personalDataDB.add(resultset.getString("ssn"));
				personalDataDB.add(resultset.getString("birth_date"));
				personalDataDB.add(resultset.getString("sex"));
			}
		}
		return personalDataDB;
	}

	@SuppressWarnings("unchecked")
	public void verifyPIN_ExistingOrTemp() throws Throwable {
		String parentWindow = Globals.GC_EMPTY;
		String msgHolder = Globals.GC_EMPTY;
		Map<String, String> getMessage = new LinkedHashMap<String, String>();
		int iEleIndex = 0;
		boolean msgValidate = false;

		initTempandOrderPINMsg();
		if (Web.getDriver().getWindowHandles().size() == 1) {
			Web.waitForElement(lnkOrderPIN);
			Web.clickOnElement(lnkOrderPIN);
			parentWindow = Web.getDriver().getWindowHandle();
			for (String childWindow : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(childWindow);
			}

		}

		if (Web.isWebElementDisplayed(getWebElement(Stock
				.GetParameterValue("btnName")))) {
			Reporter.logEvent(
					Status.PASS,
					"Validate if Mail Existing PIN / Order Temp PIN child window pops up",
					"Mail Existing PIN / Order Temp PIN window pops up as expected",
					true);
			getWebElement(Stock.GetParameterValue("btnName")).click();
		} else {
			throw new AssertionError("Web Element "
					+ Stock.GetParameterValue("btnName")
					+ "not displayed in the page");
		}

		getMessage = (Map<String, String>) getVarByName(Stock
				.GetParameterValue("btnName"));

		if (getMessage.size() > 0 & txtExistingAndOrderPINMessage.size() > 0) {
			for (Map.Entry<String, String> expectedMsg : getMessage.entrySet()) {
				String getObjText = new StringBuilder(
						txtExistingAndOrderPINMessage.get(iEleIndex).getText())
						.delete(0, 1).toString().trim();
				msgHolder = expectedMsg.getValue();
				if (!expectedMsg.getValue().contains("Note")) {
					if (getObjText.matches(".*\\d+.*")
							& getObjText
									.replaceAll("\\d", "")
									.replaceAll(" ", "")
									.equals(expectedMsg.getValue().replaceAll(
											" ", ""))
							& Web.isWebElementDisplayed(imgInfoMsg)) {
						msgValidate = true;
					}
				} else {
					if (getObjText.replaceAll(" ", "").equals(
							expectedMsg.getValue().replaceAll(" ", ""))) {
						msgValidate = true;
					}
				}

				if (msgValidate) {
					Reporter.logEvent(Status.PASS,
							"Validate info message for button "
									+ Stock.GetParameterValue("btnName")
											.replaceAll("_", " "),
							"Info message successfully validated, "
									+ expectedMsg.getKey() + ": " + msgHolder,
							false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Validate info message for button "
									+ Stock.GetParameterValue("btnName")
											.replaceAll("_", " "),
							"Info message successfully validated, "
									+ expectedMsg.getKey() + ": " + msgHolder,
							true);
				}
				iEleIndex++;
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validate if respective info message for the button "
							+ Stock.GetParameterValue("btnName").replaceAll(
									"_", " ") + "is displayed",
					"Info message for button :"
							+ Stock.GetParameterValue("btnName").replaceAll(
									"_", " ") + " is not displayed", true);
		}
		Web.getDriver().navigate().back();
		if (Web.isLastIteration()) {
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
		}
	}

	private Object getVarByName(String fieldName) throws Throwable {
		Field field = this.getClass().getDeclaredField(fieldName);
		return field.get(this);
	}

	/**
	 * <pre>
	 * Method to verify registration status
	 * </pre>
	 * 
	 * @param =reg_status
	 * @author rnjbdn
	 * @throws Exception
	 */
	public void verify_Registration_Status(String reg_status) throws Exception {

		String reg_status_On_Web;
		boolean isWebRegSts = false;
		Web.waitForElement(participantRegStatusLabel);
		if (Web.isWebElementDisplayed(participantRegStatusLabel)
				&& Web.isWebElementDisplayed(participantRegStatus)) {

			reg_status_On_Web = participantRegStatus.getText();
			if (reg_status_On_Web.equalsIgnoreCase(reg_status)) {
				isWebRegSts = true;
			}
			if (isWebRegSts) {
				Reporter.logEvent(Status.PASS,
						"Validate Web registration status label "
								+ participantRegStatusLabel.getText()
								+ " and reg status as " + reg_status
								+ " on ppt homepage",
						"Web registration status label "
								+ participantRegStatusLabel.getText()
								+ " and reg status as " + reg_status_On_Web
								+ " on ppt homepage displayed successfully",
						false);
				Web.clickOnElement(participantRegStatus);
				Web.mouseHover(participantRegStatus);
				switch (reg_status) {
				case "Registered":
					String userName = Reg_Status_UserName_data.getText();
					if (userName.matches("[0-9a-zA-Z]+")) {
						Reporter.logEvent(
								Status.PASS,
								"Validate on hover Username display with number on ppt homepage",
								"On hover Username display with number on ppt homepage:\n\n"
										+ userName, true);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Validate on hover Username display with number on ppt homepage",
								"On hover Username didn't display with number on ppt homepage:\n\n"
										+ userName, true);
					}
					break;
				case "Not Registered":
					break;
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Validate registration status on ppt homepage as "
								+ reg_status,
						"Registration status on ppt homepage is incorrect for \n\n"
								+ reg_status, true);
			}
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate Web registration status as " + reg_status
							+ " on ppt homepage", "Web registration status as "
							+ reg_status
							+ " on ppt homepage didn't display successfully",
					false);
		}
	}

	/**
	 * Method to verify managed account status
	 * 
	 * @param managed_Acc_Status
	 * @author rnjbdn
	 */
	public void verify_Managed_Account_Status(String managed_Acc_Status) {

		ArrayList<String> plan_And_Participant_ID_From_DB;
		String plan_Num_DB = null;
		String part_id = null;
		try {
			switch (managed_Acc_Status) {
			case "ENROLLED":

				// Get data from db for Enrolled participant
				plan_And_Participant_ID_From_DB = get_Participant_ID_From_DB_For_Managed_Acc_Status(
						Stock.getTestQuery("get_Particiant_ID_From_Part_Services"),
						managed_Acc_Status);
				// Search with ppt_id
				search_PPT_Plan_With_PPT_ID_OR_SSN(
						plan_And_Participant_ID_From_DB.get(0), "PPT_ID");
				// Verify managed account message
				if (plan_And_Participant_ID_From_DB.get(1).equalsIgnoreCase(
						"ENROLLED")) {
					verify_Managed_Account_Message(Stock
							.GetParameterValue("expected_msg"));
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"As per the effective date participant status has been changed",
							"As per the effective date participant status has been changed please look for other participant",
							false);
				}
				break;
			case "UNENROLLED":
				// Get data from db for Enrolled participant
				plan_And_Participant_ID_From_DB = get_Participant_ID_From_DB_For_Managed_Acc_Status(
						Stock.getTestQuery("get_Particiant_ID_From_Part_Services"),
						managed_Acc_Status);
				// Search with ppt_id
				search_PPT_Plan_With_PPT_ID_OR_SSN(
						plan_And_Participant_ID_From_DB.get(0), "PPT_ID");
				// Verify managed account message
				if (plan_And_Participant_ID_From_DB.get(1).equalsIgnoreCase(
						"UNENROLLED")) {
					verify_Managed_Account_Message(Stock
							.GetParameterValue("expected_msg"));
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"As per the effective date participant status has been changed",
							"As per the effective date participant status has been changed please look for other participant",
							false);
				}
				break;
			case "Plan Not Offered":
				// Get data from db for Enrolled participant
				plan_Num_DB = get_Plan_number_From_DB(Stock
						.getTestQuery("get_Plan_Number_From_DB"));

				part_id = get_Participant_Id_From_DB(
						Stock.getTestQuery("get_Part_ID_From_DB"),
						plan_Num_DB.trim());
				// Search with ppt_id
				search_PPT_Plan_With_PPT_ID_OR_SSN(part_id, "PPT_ID");

				// Verify managed account message
				verify_Managed_Account_Message(Stock
						.GetParameterValue("expected_msg"));
				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to retrieve managed account status from d_isis DB
	 * 
	 * @param get_IndID_PlanID_FromPartService
	 *            ,managed_Acc_Status
	 * @return ArrayList
	 * @author rnjbdn
	 * @throws Exception
	 */
	public ArrayList<String> get_Participant_ID_From_DB_For_Managed_Acc_Status(
			String[] get_IndID_PlanID_FromPartService, String managed_Acc_Status)
			throws Exception {

		ResultSet resultset;
		plan_And_Participant_List = new ArrayList<String>();
		String ind_id = null;
		String status_reason_code = null;
		resultset = DB.executeQuery(get_IndID_PlanID_FromPartService[0],
				get_IndID_PlanID_FromPartService[1], managed_Acc_Status);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					ind_id = resultset.getString("ind_id");
					status_reason_code = resultset
							.getString("status_reason_code");
					plan_And_Participant_List.add(ind_id);
					plan_And_Participant_List.add(status_reason_code);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return plan_And_Participant_List;
	}

	/**
	 * Method to retrieve participant ID from d_isis DB
	 * 
	 * @param get_Part_ID_From_DB
	 *            ,planNum
	 * @return ArrayList
	 * @author rnjbdn
	 * @throws Exception
	 */
	public String get_Participant_Id_From_DB(String[] get_Part_ID_From_DB,
			String planNum) throws Exception {

		ResultSet resultset;
		String part_ID = null;
		resultset = DB.executeQuery(get_Part_ID_From_DB[0],
				get_Part_ID_From_DB[1], planNum);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					part_ID = resultset.getString("ind_id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return part_ID;
	}

	/**
	 * <pre>
	 * Method to click on the first plan while an ind_id shows multiple SSN
	 * </pre>
	 * 
	 * @param plan_Number_DB
	 * @author rnjbdn
	 * @throws Exception
	 */
	public void click_And_Verify_Plan_On_Search_Page(String plan_Number_DB)
			throws InterruptedException {
		String plan_No_Web = null;
		boolean checkAllEmp = false;
		Thread.sleep(2000);
		if (Web.isWebElementDisplayed(partList_Tab)) {
			Reporter.logEvent(Status.INFO,
					"Participant table displayed for multiple search",
					"Participant table is displayed successfully", true);
			for (int i = 0; i <= PlanNoOnPartList_Link.size() - 1; i++) {
				plan_No_Web = PlanNoOnPartList_Link.get(i).getText();
				if (plan_No_Web.contains(plan_Number_DB))
				// if (plan_Number_DB.equalsIgnoreCase(plan_No_Web)
				// && partList_Tab
				// .findElement(
				// By.xpath("//tr[" + (i + 1) + "]/td[16]"))
				// .getText().equalsIgnoreCase("ACTIVE"))
				{

					partList_Tab.findElement(
							By.xpath("//tr[" + (i + 1) + "]/td[1]/a")).click();
					Reporter.logEvent(
							Status.PASS,
							"Choose participant with ACTIVE status",
							"Participant with ACTIVE status has been chosen successfully",
							false);
					checkAllEmp = true;
					break;
				}
			}
			if (!checkAllEmp) {
				Reporter.logEvent(Status.FAIL,
						"Choose participant with ACTIVE status",
						"Participant with ACTIVE status not found in the list",
						true);
			}
		}
	}

	/**
	 * <pre>
	 * Method to verify managed account status
	 * </pre>
	 * 
	 * @param managed_Acc_Status
	 * @author rnjbdn
	 * @throws Exception
	 */
	public void verify_Managed_Account_Status(String managed_Acc_Status,
			String mangedAccSts_DB) throws Exception {

		if (mangedAccSts_DB.equalsIgnoreCase(managed_Acc_Status)
				|| managed_Acc_Status.equalsIgnoreCase("Plan Not Offered")) {
			if (Web.isWebElementDisplayed(participantMngAccStsLabel, true)
					&& Web.isWebElementDisplayed(participantMngAccSts)) {
				Reporter.logEvent(
						Status.PASS,
						"Validate Managed Account status label and managed account status on ppt homepage",
						"Managed Account status label and managed account status on ppt homepage displayed successfully",
						false);

				if (participantMngAccSts.getText().trim()
						.contains(Stock.GetParameterValue("expected_msg"))) {
					Reporter.logEvent(Status.PASS,
							"Verify the Participant's Managed Account Status - for "
									+ managed_Acc_Status,
							"The Participant's Managed Account Status: "
									+ participantMngAccSts.getText(), false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify the Participant's Managed Account Status - for "
									+ managed_Acc_Status,
							"The Participant's Managed Account Status: "
									+ participantMngAccSts.getText(), false);
				}

			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Validate Managed Account status label & managed account status on ppt homepage",
						"Managed Account status label and managed account status on ppt homepage displayed successfully",
						false);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"As per the effective date participant status has been changed",
					"As per the effective date participant status has been changed please look for other participant",
					false);
		}
	}

	/**
	 * <pre>
	 * Method to retrieve managed account status from d_isis DB
	 * </pre>
	 * 
	 * @param get_IndID_PlanID_FromPartService
	 *            ,managed_Acc_Status
	 * @return ArrayList
	 * @author rnjbdn
	 * @throws Exception
	 */
	public ArrayList<String> get_Ppt_ID_And_Managed_Acc_Sts_From_DB(
			String[] get_IndID_PlanID_FromPartService, String managed_Acc_Status)
			throws Exception {

		ResultSet resultset;
		plan_And_Participant_List = new ArrayList<String>();
		resultset = DB.executeQuery(get_IndID_PlanID_FromPartService[0],
				get_IndID_PlanID_FromPartService[1], managed_Acc_Status);
		if (resultset != null) {
			while (resultset.next()) {
				plan_And_Participant_List.add(resultset.getString("ind_id"));
				plan_And_Participant_List.add(resultset
						.getString("status_reason_code"));
			}
		}
		return plan_And_Participant_List;
	}

	/**
	 * Method to retrieve plan Number from d_isis DB
	 * 
	 * @param get_Plan_Num_From_PartService
	 * @return ArrayList
	 * @author rnjbdn
	 * @throws Exception
	 */
	public String get_Plan_number_From_DB(String[] get_Plan_Num_From_PartService)
			throws Exception {
		ResultSet resultset;
		String plan_Num = null;
		resultset = DB.executeQuery(get_Plan_Num_From_PartService[0],
				get_Plan_Num_From_PartService[1]);
		if (resultset != null) {
			while (resultset.next()) {
				plan_Num = resultset.getString("ga_id");
			}
		}
		return plan_Num;
	}

	/**
	 * Method to retrieve participant ID from d_isis DB
	 * 
	 * @param get_Part_ID_From_DB
	 *            ,planNum
	 * @return ArrayList
	 * @author rnjbdn
	 * @throws Exception
	 */
	public ArrayList<String> get_PptID_Id_From_DB(String[] get_Part_ID_From_DB,
			String planNum) throws Exception {
		plan_And_Participant_List = new ArrayList<String>();
		ResultSet resultset;
		resultset = DB.executeQuery(get_Part_ID_From_DB[0],
				get_Part_ID_From_DB[1], planNum);
		if (resultset != null) {
			while (resultset.next()) {
				plan_And_Participant_List.add(resultset.getString("ind_id"));
			}
		}
		return plan_And_Participant_List;
	}

	/**
	 * <pre>
	 * Method to verify Managed account message
	 * </pre>
	 * 
	 * @param managed_Acc_Status
	 * @author rnjbdn
	 */
	public void verify_Managed_Account_Message(String managed_Acc_Status) {

		if (Web.isWebElementDisplayed(participantMngAccStsLabel, true)
				&& Web.isWebElementDisplayed(participantMngAccSts)) {
			Reporter.logEvent(
					Status.PASS,
					"Validate Managed Account status label and managed account status on ppt homepage",
					"Managed Account status label and managed account status on ppt homepage displayed successfully",
					false);

			if (participantMngAccSts.getText().trim()
					.contains(managed_Acc_Status)) {
				Reporter.logEvent(Status.PASS,
						"Verify the Participant's Managed Account Status - for "
								+ managed_Acc_Status,
						"The Participant's Managed Account Status: "
								+ participantMngAccSts.getText(), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify the Participant's Managed Account Status - for "
								+ managed_Acc_Status,
						"The Participant's Managed Account Status: "
								+ participantMngAccSts.getText(), false);
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validate Managed Account status label & managed account status on ppt homepage",
					"Managed Account status label and managed account status on ppt homepage displayed successfully",
					false);
		}
	}

	/**
	 * <pre>
	 * Method to enter participant ID and click on Sign In button on QAA
	 * </pre>
	 * 
	 * @param ppt_id
	 * @throws Exception
	 */
	public void search_PPT_Plan_With_PPT_ID_OR_SSN_On_QAA(
			String PPT_Or_SSN_Value, WebElement searchField) throws Exception {
		boolean isElementDisplayed = false;
		Web.waitForElement(menuSearch);
		Web.clickOnElement(menuSearch);
		Web.setTextToTextBox(searchField, PPT_Or_SSN_Value);
		Web.clickOnElement(SubmitPPTIdBtn);
		isElementDisplayed = Web.isWebElementDisplayed(partList_Tab, true);
		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Search should display list of participant",
					"Search displayed with list of participant successfully",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Search should display list of participant",
					"Search didn't display with list of participant successfully",
					false);
		}
	}

	/**
	 * <pre>
	 * Method to get PPT ID based on the PDI status
	 * </pre>
	 * 
	 * @param pdiStatus
	 * @return pptID
	 * @author rnjbdn
	 */
	public String getPPTIDForPDIStatus(String pdiStatus) throws Exception {
		ResultSet resultset = null;
		String pptID = Globals.GC_EMPTY;
		if (pdiStatus.equalsIgnoreCase("Yes")) {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getPPTIDforPDIStatusY")[0],
					Stock.getTestQuery("getPPTIDforPDIStatusY")[1]);
			if (resultset != null) {
				while (resultset.next()) {
					pptID = resultset.getString("IND_ID");
				}
			}
		}
		if (pdiStatus.equalsIgnoreCase("No")) {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getPPTIDforPDIStatusN")[0],
					Stock.getTestQuery("getPPTIDforPDIStatusN")[1]);
			if (resultset != null) {
				while (resultset.next()) {
					pptID = resultset.getString("IND_ID");
				}
			}
		}
		return pptID;
	}

	/**
	 * <pre>
	 * Method to verify PDI status
	 * </pre>
	 * 
	 * @param pdi_Status
	 * 
	 * @author rnjbdn
	 */
	public void verify_PDI_Status(String pdi_Status) {

		String pdistatus_Web;
		int noOfPlans;
		boolean flag = false;
		flag = Web.isWebElementDisplayed(PDILabel, true);
		if (flag) {
			Reporter.logEvent(Status.PASS,
					"Check if PDI status label is present ",
					"PDI status Label is present", false);
			noOfPlans = PDIStatus.size();
			if (noOfPlans > 1) {
				for (int i = 1; i < noOfPlans; i++) {
					pdistatus_Web = PDIStatus.get(i).getText();
					if (pdi_Status.equalsIgnoreCase("YES")
							& pdistatus_Web.equalsIgnoreCase(pdi_Status)) {
						Reporter.logEvent(Status.PASS,
								"Check PDI status for a participant is : "
										+ pdi_Status,
								"PDI status for a participant is : "
										+ pdistatus_Web, false);
					} else if (pdi_Status.equalsIgnoreCase("No")
							& pdistatus_Web.equalsIgnoreCase(pdi_Status)) {
						Reporter.logEvent(Status.PASS,
								"Check if PDI status for a participant is : "
										+ pdi_Status,
								"PDI status for a participant is: "
										+ pdistatus_Web, false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Check PDI status for a participant",
								"PDI status for a participant is neither YES nor NO",
								true);
					}
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if PDI status is present or not",
						"PDI status is not present", true);
			}
		} else
			Reporter.logEvent(Status.FAIL,
					"Check if PDI label is not present ",
					"PDI Label is not present", true);
	}

	/**
	 * <pre>
	 * Method to get PPT ID based on the Managed status
	 * </pre>
	 * 
	 * @param managedAccSts
	 * @return pptID,planNumber
	 * @author rnjbdn
	 */
	public ArrayList<String> getPPTIDAndManagedAccSts(String managedAccSts)
			throws Exception {
		ArrayList<String> plan_And_PPT_ID_From_DB = null;
		String plan_Num_DB = null;
		if (managedAccSts.contains("ENROLLED")) {
			// Get data from db for Enrolled/UnEnrolled participant
			plan_And_PPT_ID_From_DB = get_Ppt_ID_And_Managed_Acc_Sts_From_DB(
					Stock.getTestQuery("get_Particiant_ID_From_Part_Services"),
					managedAccSts);
		}
		if (managedAccSts.contains("Plan Not Offered")) {

			// Get data from db for Plan not offered participant
			plan_Num_DB = get_Plan_number_From_DB(Stock
					.getTestQuery("get_Plan_Number_From_DB"));

			plan_And_PPT_ID_From_DB = get_PptID_Id_From_DB(
					Stock.getTestQuery("get_Part_ID_From_DB"),
					plan_Num_DB.trim());
		}
		return plan_And_PPT_ID_From_DB;
	}

	/**
	 * <pre>
	 * Method to get the checked plan number
	 * </pre>
	 * 
	 */
	public String getCheckedPlanOnPPTHome(String plan_No_Chkd) {
		String ga_id = null;
		if (Web.isWebElementDisplayed(PPT_Indx_Radio_Btn, false)) {
			WebElement plan_No = getWebElement(plan_No_Chkd.toUpperCase());
			ga_id = plan_No.getText();
		}
		return ga_id;
	}

	/**
	 * <pre>
	 * Method to validate Account balance on PPT home page
	 * </pre>
	 */
	public void verifyAccountBal_On_PPTHome() {
		String accountBal = null;
		String accountBal_Col = null;
		boolean isAccBalPopupDisplayedWithDtls = false;
		ArrayList<String> accBalList = new ArrayList<String>();
		if (Web.isWebElementDisplayed(tdParticipantBalance)) {
			accountBal = lnkHoverablePlanBalance.getText();
			CommonLib.isAccountBalance_In_ProperFormat(accountBal);
			accountBal_Col = tdParticipantBalance.getCssValue("color");
			if (CommonLib.isAccountBalance_In_ProperFormat(accountBal)) {
				// && accountBal_Col.contains("hexCodeForGreen()")) {
				Reporter.logEvent(
						Status.PASS,
						"Check if Plan account balance displayed with proper format and with green color",
						"Plan account balance displayed with proper format and with green color",
						true);
				Web.mouseHover(lnkHoverablePlanBalance);
				if (Web.isWebElementDisplayed(lnkHoverablePlanBalanceAfterHover)) {
					int colCount = tbTotalVarBal.size();
					accBalList = new ArrayList<String>();
					accBalList.add("Total Variable Balance");
					accBalList.add("Total Fixed Balance");
					accBalList.add("Total Excluding Loans");
					accBalList.add("Total Loan Balance");
					accBalList.add("Total Including Loans");
					if (Web.isWebElementDisplayed(tbVestedBal)
							&& Web.isWebElementDisplayed(tbNonVestedBal)
							&& Web.isWebElementDisplayed(tbCurrentBal)) {
						if (numberOFRowsInBalPopUp.size() <= 0) {
							throw new AssertionError(
									"Plan balance popup didn't display with different Balance");
						}

						for (int rowIndx = 1; rowIndx < numberOFRowsInBalPopUp
								.size(); rowIndx++) {

							if (accBalList.get(rowIndx - 1).contains(
									numberOFRowsInBalPopUp.get(rowIndx)
											.getText().trim())) {
								for (int colIndx = 2, tempIndx = 0; colIndx <= colCount; colIndx++) {
									tempIndx = rowIndx + 1;
									String webElement = "td[valign='top'] table.compactDataTable tr:nth-of-type("
											+ tempIndx
											+ ")>td:nth-of-type("
											+ colIndx + ")";
									String balance = Web.getDriver()
											.findElement(
													By.cssSelector(webElement))
											.getText().trim();
									if (CommonLib
											.isAccountBalance_In_ProperFormat(balance))
										isAccBalPopupDisplayedWithDtls = true;
									else
										isAccBalPopupDisplayedWithDtls = false;
									// break ;
								}
							}
						}
					}
				}
				if (isAccBalPopupDisplayedWithDtls) {
					Reporter.logEvent(
							Status.PASS,
							"Check On hover on Plan Account balance Link in Account balance Popup all the balance displayed in proper format.",
							"On hover on Plan Account balance Link in Account balance Popup all the balance displayed in proper format.",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check On hover on Plan Account balance Link in Account balance Popup all the balance displayed in proper format.",
							"On hover on Plan Account balance Link in Account balance Popup all the balance displayed in proper format.",
							true);
				}

			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Plan account balance displayed with proper format and with green color",
						"Plan account balance didn't display with proper format and with green color",
						true);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plan account balance display or not",
					"Plan account balance displayed successfully", true);
		}
	}

	/**
	 * <pre>
	 * Method to navigate to Contribution Allocations changes page.
	 * </pre>
	 */
	public void navigateToCACPage() {
		if (Web.isWebElementDisplayed(MenuParticipantChanges)) {
			Web.mouseHover(MenuParticipantChanges);
			Web.mouseHover(ContrAllChngLink);
			if (Web.clickOnElement(ContrAllChngSubLink)) {
				Reporter.logEvent(
						Status.PASS,
						"Click on Contribution Allocation Change link under Participant changes Mene link.",
						"Clicked on Contribution Allocation Change link under Participant changes Mene link successfully",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Click on Contribution Allocation Change link under Participant changes Mene link.",
						"Din't click on Contribution Allocation Change link under Participant changes Mene link successfully",
						true);
			}
		}
	}
	
	public void cancelExistingFundTransfers(){
		Web.mouseHover(menuPPTChanges);
		if(Web.isWebElementDisplayed(menuCancelF2FLink,true)) menuCancelF2FLink.click();		
		Web.waitForElement(cancelFndTransferPgHeader);
				
		if(cancelFndTransferCheckBox.size() > 0){
			if(Web.isWebElementDisplayed(cancelFndTransferCheckBox.get(0),false)){			
				for(WebElement ele : cancelFndTransferCheckBox){
					ele.click();
				}
				btnConfCancelTranfer.click();			
				if(Web.isWebElementDisplayed(btnSubmitCancelTransfer, true)){
					btnSubmitCancelTransfer.click();
					Reporter.logEvent(Status.INFO,"Cancelling existing fund transfers","Existing fund transfers cancelled",true);
				}
			}
		}		
	}	
	
	/**
	 * <pre>
	 * Method to get PPT_ID,GA_ID based on restriction type
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 * @author RANJAN
	 */
	public ArrayList<String> getGAID_or_pptID_RestrictionType() throws Exception {
		ResultSet resultset;
		ArrayList<String> participant_Info = new ArrayList<String>() ;
		String plan_Num = null;
		String ind_id = null ;
		resultset = DB.executeQuery(Stock.getTestQuery("getGAIDorpptID_RestrictionType")[0],
				Stock.getTestQuery("getGAIDorpptID_RestrictionType")[1]);
		if (resultset != null) {
			while (resultset.next()) {
				ind_id = resultset.getString("ind_id") ;
 				plan_Num = resultset.getString("ga_id") ;
				participant_Info.add(ind_id) ;
				participant_Info.add(plan_Num) ;
			}
		}
		return participant_Info;
	}
}