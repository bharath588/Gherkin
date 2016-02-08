package pageobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lib.DB;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import core.framework.Globals;
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

	@FindBy(css = "div#oCMenu_315")
	private WebElement menuParticipantInfo;

	@FindBy(css = "div#oCMenu_316")
	private WebElement menuParticipantChanges;

	@FindBy(css = "div#oCMenu_317")
	private WebElement menuPlanInfo;

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

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(2)>td")
	private WebElement tbTotalVarBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(3)>td")
	private WebElement tbTotalFixBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(4)>td")
	private WebElement tbTotalExcludingLoans;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(5)>td")
	private WebElement tbTotalLoanBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(6)>td")
	private WebElement tbTotalIncludingLoan;

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

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(11)>td.data")
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

	// @FindBy(css =
	// "table.compactDataTable tr:nth-of-type(7) a:nth-of-type(2)")
	@FindBy(xpath = "(//div[@class='dataContainerBody'])[2]/table/tbody/tr/td[2]/table/tbody/tr[5]/td/a[2]")
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
	private WebElement PDIStatus;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(10)")
	private WebElement InstanceLabel;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'data centered']:nth-of-type(10)")
	private List<WebElement> InstanceValue_List;
	
	@FindBy(css="table[id='table_messageHandlerMessage']")
	private 

	LoadableComponent<?> parent;
	
	/*-----------------------------------------------------------------*/

	public ParticipantHome() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		// Assert.assertEquals(Web.webdriver.getTitle(), "CSAS v12.03.2");
		Assert.assertTrue(Web.webdriver.getTitle().contains("CSAS v12.03.2"));
	}

	@Override
	protected void load() {
		this.parent = parent;
		Web.webdriver.get(Stock.globalParam.get("AppURL"));
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
		
		if(fieldName.trim().equalsIgnoreCase("Mail Existing PIN")){
			return this.btnMailExistingPIN;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Order Temp PIN")){
			return this.btnOrderTempPin;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Mail Existing VRU PIN and Web Passcode")){
			return this.btnMailExstngVRUPINandWebPscd;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Mail Existing Web Passcode")){
			return this.btnMailExstngWebPscd;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Mail Existing VRU PIN")){
			return this.btnMailExstngVRUPIN;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Order Temp VRU PIN and Web Passcode")){
			return this.btnOrderTempPINandWebPscd;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Order Temp Web Passcode")){
			return this.btnOrderTempWebPscd;
		}
		
		if(fieldName.trim().equalsIgnoreCase("Order Temp VRU PIN")){
			return this.btnOrderTempVRUPIN;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}
	
	public String getPPTID(String WebRegStatus) throws Exception {		
		ResultSet resultset = null;
		String pptID = Globals.GC_EMPTY;
		if(WebRegStatus.equalsIgnoreCase("Registered")){
			resultset = DB.executeQuery(Stock.getTestQuery("getPPTIDforWebRegStatus")[0],
					                    Stock.getTestQuery("getPPTIDforWebRegStatus")[1]);
			if (resultset != null) {
				while (resultset.next()) {
					   pptID = resultset.getString("ID");
				}
			}
		}
		if(WebRegStatus.equalsIgnoreCase("NonRegistered")){
			resultset = DB.executeQuery(Stock.getTestQuery("getPPTIDforWebNonRegStatus")[0],
                    					Stock.getTestQuery("getPPTIDforWebNonRegStatus")[1]);
			if (resultset != null) {
				while (resultset.next()) {
				   pptID = resultset.getString("ID");
				}
			}
		}		
		return pptID;
	}


	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
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
				this.participantPlanSearchPage, true);

		if (isElementDisplayed) {
			Reporter.logEvent(
					Status.PASS,
					"Check if the user logged in to Participant/plan search page",
					"User is successfully logged in to Participant/plan search page",
					true);
			Web.appLoginStatus = true;
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if the user logged in to Participant/plan search page",
					"User is not logged in to Participant/plan search page",
					false);
		}
	}

	/**
	 * Method to enter participant ID and click on Sign In button
	 * 
	 * @param ppt_id
	 * @throws Exception
	 */
	public void search_PPT_Plan_With_PPT_ID_OR_SSN(String PPT_Or_SSN_Value,
			WebElement searchField) throws Exception {
		boolean isElementDisplayed = false;
		Web.waitForElement(this.menuSearch);
		Web.clickOnElement(this.menuSearch);
		Web.setTextToTextBox(searchField, PPT_Or_SSN_Value);
		Reporter.logEvent(Status.INFO,"Performing search using PPT ID", "PPT ID : "+PPT_Or_SSN_Value , true);
		Web.clickOnElement(this.SubmitPPTIdBtn);
		
		
		Web.waitForElement(this.PPTHomePageTitle);
		isElementDisplayed = Web.isWebElementDisplayed(this.PPTHomePageTitle,
				true);
		if (isElementDisplayed) {
			Reporter.logEvent(
					Status.PASS,
					"Participant Home Page with all the details should display",
					"Participant Home Page with all the details is displayed successfully",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Participant Home Page with all the details should not display",
					"Participant Home Page with all the details is not displayed",
					false);
		}

	}

	/*
	 * Retrieve data from db & verify
	 * 
	 * @PARAMETER = ppt_id
	 */
	public void verify_HireDate_TermDate(String ppt_id, String emp_Status) {
		List<String> HireDate_TermDate_List;
		WebElement empStatus = null;

		try {
			Thread.sleep(100);
			for (int i = 0; i < PlanNumber.size(); i++) {
				String planNum = PlanNumber.get(i).getText();
				String ind_id = IndID_List.get(i).getText();
				planNum = planNum.substring(0, planNum.indexOf('-'));
				HireDate_TermDate_List = get_Hire_Term_Date_From_DB(
						Stock.getTestQuery("getHireDateAndTerminationDate"),
						planNum, ind_id, i);

				empStatus = lnkEmploymentStatus.get(i);
				switch (emp_Status) {
				case "ACTIVE":
					if (empStatus.getText().equalsIgnoreCase("ACTIVE")) {
						Web.mouseHover(empStatus);
						if (HireDate_TermDate_List.get(1) == null){

							if (DB.compareDB_Date_With_Web_Date(
									HireDate_TermDate_List.get(0),
									HireDate_List.get(i).getText()))

								Reporter.logEvent(
										Status.PASS,
										"Check if Employment status is ACTIVE and Hire date: "
												+ HireDate_TermDate_List.get(0),
										"Employment status is ACTIVE and the Hire Date is:  "
												+ HireDate_List.get(i)
														.getText(), true);
							else
								Reporter.logEvent(
										Status.FAIL,
										"Check if Employment status is not ACTIVE and Hire date: "
												+ HireDate_TermDate_List.get(0),
										"Employment status is not ACTIVE and the Hire Date is:  "
												+ HireDate_List.get(i)
														.getText(), false);
							Web.clickOnElement(PPTHomePageTitle);
							Thread.sleep(100);

						}
					}
					break;

				case "TERMINATED":

					if (lnkEmploymentStatus.get(i).getText()
							.contains("TERMINATED")) {
						Web.mouseHover(empStatus);

						if (DB.compareDB_Date_With_Web_Date(
								HireDate_TermDate_List.get(0), HireDate_List
										.get(i).getText())
								&& DB.compareDB_Date_With_Web_Date(
										HireDate_TermDate_List.get(1),
										TermDate_List.get(i).getText()))
							Reporter.logEvent(Status.PASS,
									"Check if Employment status is TERMINATED and Hire date: "
											+ HireDate_TermDate_List.get(0)
											+ "Termination date is : "
											+ HireDate_TermDate_List.get(1),
									"Employment status is TERMINATED and Hire Date is:  "
											+ HireDate_List.get(i).getText()
											+ "Termination date is : "
											+ TermDate_List.get(i).getText(),
									true);
						else
							Reporter.logEvent(Status.FAIL,
									"Check if Employment status is not TERMINATED and Hire date: "
											+ HireDate_TermDate_List.get(0)
											+ "Termination date is : "
											+ HireDate_TermDate_List.get(1),
									"Employment status is not TERMINATED and Hire Date is:  "
											+ HireDate_List.get(i).getText()
											+ "Termination date is : "
											+ TermDate_List.get(i).getText(),
									false);

						Web.clickOnElement(PPTHomePageTitle);
						Thread.sleep(100);
					}

					break;

				default:
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public ArrayList<String> get_Hire_Term_Date_From_DB(

	String[] getHireDateAndTerminationDate, String planNum, String indID,
			int index) {

		ResultSet resultset;

		hireTermDateList = new ArrayList<String>();

		resultset = DB.executeQuery(getHireDateAndTerminationDate[0],
				getHireDateAndTerminationDate[1], indID, planNum);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					String hireDate = resultset.getString("hire_date");
					String termDate = resultset.getString("emp_termdate");
					hireTermDateList.add(hireDate);
					hireTermDateList.add(termDate);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hireTermDateList;
	}

	/*
	 * verify page instance against db
	 */
	public void verify_Page_Instance() {

		String instance_DB;
		String instance_Web;
		String ind_id;
		boolean flag;
		flag = Web.isWebElementDisplayed(InstanceLabel, true);

		if (flag) {

			Reporter.logEvent(Status.PASS,
					"Check if Instance label is present ",
					"Instance Label is present", true);
			for (int i = 0; i < InstanceValue_List.size(); i++) {
				instance_Web = InstanceValue_List.get(i).getText();
				ind_id = IndID_List.get(i).getText();
				try {
					instance_DB = get_Page_Instance_From_DB(
							Stock.getTestQuery("getPageInstanceFromInd_id"),
							ind_id);
					if (instance_Web.equalsIgnoreCase(instance_DB)) {

						Reporter.logEvent(Status.PASS,
								"Check if database name for individual ID:  "
										+ ind_id + "  is: " + instance_DB,
								"Check if database name for individual ID :"
										+ ind_id + "  is: " + instance_Web,
								true);
					} else {

						Reporter.logEvent(
								Status.FAIL,
								"Check if database name for individual ID:  "
										+ ind_id + "  is not : " + instance_DB,
								"Check if database name for individual ID :"
										+ ind_id + "  is not : " + instance_Web,
								false);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else
			Reporter.logEvent(Status.FAIL,
					"Check if Instance label is not present ",
					"Instance Label is not present", false);

	}

	public String get_Page_Instance_From_DB(String[] getPageInstanceFromInd_id,

	String ind_id) {

		ResultSet resultset;
		String instance = null;

		resultset = DB.executeQuery(getPageInstanceFromInd_id[0],
				getPageInstanceFromInd_id[1], ind_id);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					instance = resultset.getString("database_instance");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	/*
	 * Validate Personal Data on PPT home page..
	 * 
	 * @PARAMETER = SSN
	 */

	public void validate_Personal_Data_On_PPT_Home(String ssn) {

		ArrayList<String> personal_Data_From_DB;
		try {
			personal_Data_From_DB = get_Personal_Data_From_DB(
					Stock.getTestQuery("getPersonalDataOnPPTHomePage"), ssn);
		
			if (personal_Data_From_DB.isEmpty()) {
				
			// Personal data validation..

			String FullName_DB = personal_Data_From_DB.get(0) + " "
					+ personal_Data_From_DB.get(1);

			String SSN = personalData_On_PPT_Home_List.get(1).getText();
			String[] splitedStr = SSN.split("-");

			String concatenated_SSN = null;

			concatenated_SSN = splitedStr[0] + splitedStr[1] + splitedStr[2];

			if (personalData_On_PPT_Home_List.get(0).getText()
					.equalsIgnoreCase(FullName_DB)
					&& DB.compareDB_Date_With_Web_Date(
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
										.getText() + "\n", true);
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
										.getText() + "\n", false);
			}
			}else{
				Reporter.logEvent(Status.INFO,
						"Validate personal data from DB for the SSN:  "+ssn,
						"No records found in DB", false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	}

	/*
	 * Method to validate personal data
	 * 
	 * @PARAMETER = getPersonalDataOnPPTHomePage
	 * 
	 * @PARAMETER = ssn
	 */
	public ArrayList<String> get_Personal_Data_From_DB(
			String[] getPersonalDataOnPPTHomePage,

			String ssn) {

		ResultSet resultset;
		personalDataDB = new ArrayList<String>();

		resultset = DB.executeQuery(getPersonalDataOnPPTHomePage[0],
				getPersonalDataOnPPTHomePage[1], ssn);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					personalDataDB.add(resultset.getString("first_name"));
					personalDataDB.add(resultset.getString("last_name"));
					personalDataDB.add(resultset.getString("ssn"));
					personalDataDB.add(resultset.getString("birth_date"));
					personalDataDB.add(resultset.getString("sex"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return personalDataDB;
	}

	public void verifyPIN_ExistingOrTemp() throws Throwable {
		String parentWindow = Globals.GC_EMPTY;
		Map<String,String> getMessage = new LinkedHashMap<String,String>();
		int iEleIndex=0;
		CommonLib cl = new CommonLib();
		String msgVarName = Stock.GetParameterValue("ValidateMsgVarNm");
		if(Stock.GetParameterValue("searchUser").equalsIgnoreCase("TRUE")){
			Web.waitForElement(lnkOrderPIN);
			Web.clickOnElement(lnkOrderPIN);			
			parentWindow = Web.webdriver.getWindowHandle();		
			for (String childWindow : Web.webdriver.getWindowHandles()) {
				Web.webdriver.switchTo().window(childWindow);
			}
		}
	 
		if(Web.isWebElementDisplayed(getWebElement(Stock.GetParameterValue("btnName")))){
			   getWebElement(Stock.GetParameterValue("btnName")).click();
			   if(!msgVarName.contains(",")){msgVarName=msgVarName+", ";}
			   for(String str : msgVarName.split(",")){
				   if(!str.trim().equals(Globals.GC_EMPTY)){getMessage.put(str.trim(),(String) cl.getVarByName(str));}	
			   }
			   if(getMessage.size()>0 & txtExistingAndOrderPINMessage.size()>0){
					   for (Map.Entry<String, String> expectedMsg : getMessage.entrySet()) {
						   String getObjText = new StringBuilder(txtExistingAndOrderPINMessage.get(iEleIndex)
								                                .getText()).delete(0,1).toString().trim();
						   if(!expectedMsg.getKey().contains("Note")){
							  if(getObjText.matches(".*\\d+.*") 
							    & getObjText.replaceAll("\\d","").replaceAll(" ","").equals(expectedMsg.getValue().replaceAll(" ",""))
							    & Web.isWebElementDisplayed(imgInfoMsg)){
								Reporter.logEvent(Status.PASS,"Validate info message for button "
							                      +expectedMsg.getKey(),"Info message successfully validated",false);
							 }else{
								 Reporter.logEvent(Status.FAIL,"Validate info message for button "
							                      +expectedMsg.getKey(),"Info message validation failed",true);
							 }   
						   }else{
							  if(getObjText.replaceAll(" ","").equals(expectedMsg.getValue().replaceAll(" ",""))){
								  Reporter.logEvent(Status.PASS,"Validate info message for button "
					                      +expectedMsg.getKey(),"Info message successfully validated",false);
							  }else{
								  Reporter.logEvent(Status.FAIL,"Validate info message for button "
					                      +expectedMsg.getKey(),"Info message validation failed",true);
							  }
						   }
						   iEleIndex++;
					   }    
			   }else{
				   Reporter.logEvent(Status.FAIL,"Validate if respective info message for the button is displayed",
						             "Info message for button :" +Stock.GetParameterValue("btnName")
						             + " is not displayed",true);
			   }
		}else{
			Reporter.logEvent(Status.FAIL,"Validate if respective info message for the button is displayed",
		             "Info message for button :" +Stock.GetParameterValue("btnName")
		             + " is not displayed",true);
		}	
		if(Stock.GetParameterValue("closeChildBrowser").equalsIgnoreCase("TRUE")){
			Web.webdriver.close();
			Web.webdriver.switchTo().window(parentWindow);
		}
	}
	
	/**
	 * Method to verify registration status
	 * 
	 * @param =reg_status
	 * @author rnjbdn
	 */
	public void verify_Registration_Status(String reg_status) {

		String reg_status_On_Web;
		try {
			Web.waitForElement(participantRegStatusLabel);
			if (Web.isWebElementDisplayed(participantRegStatusLabel)
					&& Web.isWebElementDisplayed(participantRegStatus)) {
				Reporter.logEvent(
						Status.PASS,
						"Validate Web registration status label and reg status on ppt homepage",
						"Web registration status label and reg status on ppt homepage displayed successfully",
						false);
				switch (reg_status) {
				case "Registered":
					reg_status_On_Web = participantRegStatus.getText();
					if (reg_status_On_Web.equalsIgnoreCase(reg_status)) {
						Reporter.logEvent(Status.PASS,
								"Validate Web registration on ppt homepage is : "
										+ reg_status,
								"Web registration status on PPT homepage is: "
										+ reg_status_On_Web, false);
						Web.mouseHover(participantRegStatus);
						String userName_label = Reg_Status_UserName_Label
								.getText();
						String userName = Reg_Status_UserName_data.getText();

						if (userName.matches("[0-9a-zA-Z]+")) {
							Reporter.logEvent(
									Status.PASS,
									"Validate on hover Username display with number on ppt homepage",
									"On hover Username displayed succefully with number on ppt homepage:\n\n"
											+ userName_label + ":  " + userName,
									false);
						} else {
							Reporter.logEvent(
									Status.FAIL,
									"Validate on hover Username display with number on ppt homepage",
									"On hover Username didn't display with number on ppt homepage:\n\n"
											+ userName_label + ":  " + userName,
									false);
						}
					} else {

						Reporter.logEvent(Status.FAIL,
								"Validate Web registration on ppt homepage is : "
										+ reg_status,
								"Web registration status on PPT homepage is not : "
										+ reg_status_On_Web, false);
					}
					break;
				case "Not Registered":

					break;
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Validate Web registration status label and reg status on ppt homepage",
						"Web registration status label and reg status on ppt homepage didn't display successfully",
						false);

			}
		} catch (Exception e) {
			e.printStackTrace();
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
						plan_And_Participant_ID_From_DB.get(0), PPTIdfield);
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
						plan_And_Participant_ID_From_DB.get(0), PPTIdfield);
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
				// plan_Num_DB = get_Plan_number_From_DB(
				// Stock.getTestQuery("get_Plan_Number_From_DB"));

				part_id = get_Participant_Id_From_DB(
						Stock.getTestQuery("get_Part_ID_From_DB"), "331883-01");
				// Search with ppt_id
				search_PPT_Plan_With_PPT_ID_OR_SSN(part_id, PPTIdfield);

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
	 */
	public ArrayList<String> get_Participant_ID_From_DB_For_Managed_Acc_Status(
			String[] get_IndID_PlanID_FromPartService, String managed_Acc_Status) {

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
	 * Method to retrieve plan Number from d_isis DB
	 * 
	 * @param get_Plan_Num_From_PartService
	 * @return ArrayList
	 * @author rnjbdn
	 */
	public String get_Plan_number_From_DB(String[] get_Plan_Num_From_PartService) {

		ResultSet resultset;
		String plan_Num = null;
		System.out.println(get_Plan_Num_From_PartService[0] + "   "
				+ get_Plan_Num_From_PartService[1]);
		resultset = DB.executeQuery(get_Plan_Num_From_PartService[0],
				get_Plan_Num_From_PartService[1]);
		if (resultset != null) {
			try {
				while (resultset.next()) {
					plan_Num = resultset.getString("ga_id");
					System.out.println(plan_Num + "-------");
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
	 */
	public String get_Participant_Id_From_DB(String[] get_Part_ID_From_DB,
			String planNum) {

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
	 * Method to click on the first plan while an ind_id shows multiple SSN
	 * 
	 * @param plan_Number_DB
	 * @author rnjbdn
	 */
	public void click_And_Verify_Plan_On_Search_Page(String plan_Number_DB) {

		String plan_No_Web = null;
		boolean isElementDisplayed;
		try {
			Web.waitForElement(partList_Tab);
			if (Web.isWebElementDisplayed(partList_Tab)) {
				Reporter.logEvent(Status.PASS,
						"Participant table should display",
						"Participant table is displayed successfully", false);
				for (int i = 1; i < PlanNoOnPartList_Link.size(); i++) {
					plan_No_Web = PlanNoOnPartList_Link.get(0).getText();
					System.out.println(plan_No_Web + "  : " + plan_Number_DB);
					if (plan_Number_DB.equalsIgnoreCase(plan_No_Web)) {

						Web.webdriver.findElement(
								By.xpath("//table[@id = 'partList']//tr[" + i
										+ "]/td[1]/a")).click();

						Web.waitForElement(this.PPTHomePageTitle);
						isElementDisplayed = Web.isWebElementDisplayed(
								this.PPTHomePageTitle, true);
						if (isElementDisplayed) {
							Reporter.logEvent(
									Status.PASS,
									"Participant Home Page with all the details should display",
									"Participant Home Page with all the details is displayed successfully",
									true);
							break;
						} else {
							Reporter.logEvent(
									Status.FAIL,
									"Participant Home Page with all the details should not display",
									"Participant Home Page with all the details is not displayed",
									false);
						}
					}
				}

			} else {
				Reporter.logEvent(Status.FAIL,
						"Participant table should display",
						"Participant table is displayed successfully", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to verify Managed account message
	 * 
	 * @param managed_Acc_Status
	 * @author rnjbdn
	 */
	public void verify_Managed_Account_Message(String managed_Acc_Status) {

		try {
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
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Method to enter participant ID and click on Sign In button on QAA
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

}