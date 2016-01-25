package pageobjects;
		
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lib.DB;
import lib.Reporter;
import lib.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import core.framework.Globals;
import framework.util.CommonLib;
import lib.Reporter.Status;
import lib.Stock;
		
public class ParticipantHome extends LoadableComponent<ParticipantHome> {
		
		private ArrayList<String> hireTermDateList;
	
		
		// CSAS Login..
		
		@FindBy(xpath = "//span[contains(text(),'CLIENT SERVICE ACCESS SYSTEM USER LOGON')]")
		private WebElement CSASLoginHome;
		
		@FindBy(css = "input[name = 'username']")
		private WebElement CSASUserNameField;
		
		@FindBy(css = "input[name = 'password']")
		private WebElement CSASPwdField;
		
		@FindBy(css = "input[value='Log In']")
		private WebElement CSASLoginBtn;
		
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
		private WebElement btnOPMailExistingPIN;
		
		@FindBy(css = "table[id='table_messageHandlerMessage'] div[class='messageContent']")
		private WebElement txtExistingAndOrderPINMessage;
		
		@FindBy(css = "input[value = 'Order Temp PIN']")
		private WebElement btnOPOrderTempPin;
		
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
		
		LoadableComponent<?> parent;
		/*-----------------------------------------------------------------*/
		
		
		public ParticipantHome() {
			PageFactory.initElements(Web.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			//Assert.assertEquals(Web.webdriver.getTitle(), "CSAS v12.03.2");
			Assert.assertTrue(Web.webdriver.getTitle().contains("CSAS v12.03.2"));
		}
		
		@Override
		protected void load() {
			this.parent = parent;
			Web.webdriver.get(Stock.globalParam.get("AppURL"));
		}
		
		/** <pre> Method to return WebElement object corresponding to specified field name
		 * Elements available for fields:
		 * 	LOG OUT Or LOGOUT - [Link]
		 * 	HOME - [Link]
		 * 	MY ACCOUNTS - [Link]
		 * 	RETIREMENT INCOME - [Label]
		 * </pre>
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
			
			Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
					"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
			
			return null;
		}
		
		/**
		 * Method to enter user credentials and click on Sign In button
		 * 
		 * @param userName
		 * @param password
		 * @throws Exception
		 */
		public void submitLoginCredentials(String username, String password) throws Exception {		
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
	public void search_PPT_Plan_With_PPT_ID_OR_SSN(String PPT_Or_SSN_Value,WebElement searchField) throws Exception {
			boolean isElementDisplayed = false;
			Web.waitForElement(this.menuSearch);
			Web.clickOnElement(this.menuSearch);
			Web.setTextToTextBox(searchField, PPT_Or_SSN_Value);			
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
					if (empStatus.getText().equalsIgnoreCase("ACTIVE")
							&& HireDate_TermDate_List.get(1) == null) {
		
						Web.mouseHover(empStatus);

						if (DB.compareDB_Date_With_Web_Date(
								HireDate_TermDate_List.get(0), HireDate_List
										.get(i).getText()))

							Reporter.logEvent(Status.PASS,
									"Check if Employment status is ACTIVE and Hire date: "
											+ HireDate_TermDate_List.get(0),
									"Employment status is ACTIVE and the Hire Date is:  "
											+ HireDate_List.get(i).getText(),
									true);
						else
							Reporter.logEvent(Status.FAIL,
									"Check if Employment status is not ACTIVE and Hire date: "
											+ HireDate_TermDate_List.get(0),
									"Employment status is not ACTIVE and the Hire Date is:  "
											+ HireDate_List.get(i).getText(),
									false);
						Web.clickOnElement(PPTHomePageTitle);
						Thread.sleep(100);

					}
					break;

				case "TERMINATED":

					Web.mouseHover(empStatus);

					if (lnkEmploymentStatus.get(i).getText()
							.contains("TERMINATED")
							&& DB.compareDB_Date_With_Web_Date(
									HireDate_TermDate_List.get(0),
									HireDate_List.get(i).getText())
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
										+ TermDate_List.get(i).getText(), true);
					else
						Reporter.logEvent(Status.FAIL,
								"Check if Employment status is not TERMINATED and Hire date: "
										+ HireDate_TermDate_List.get(0)
										+ "Termination date is : "
										+ HireDate_TermDate_List.get(1),
								"Employment status is not TERMINATED and Hire Date is:  "
										+ HireDate_List.get(i).getText()
										+ "Termination date is : "
										+ TermDate_List.get(i).getText(), false);

					Web.clickOnElement(PPTHomePageTitle);
					Thread.sleep(100);

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

			System.out.println("I am here");
			personal_Data_From_DB = get_Personal_Data_From_DB(
					Stock.getTestQuery("getPersonalDataOnPPTHomePage"), ssn);
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
		}
		try {
			Reporter.finalizeTCReport();
		} catch (Exception e1) {
			e1.printStackTrace();
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

		ArrayList<String> personalDataDB ;
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
	
	public void verifyPIN_ExistingOrTemp(String pinType) throws Exception {
			String parentWindow = Globals.GC_EMPTY;
			String getMessage = Globals.GC_EMPTY;
			String getOrderPINMsgA = Globals.GC_EMPTY;
			String getOrderPINMsgB = Globals.GC_EMPTY;
			Web.waitForElement(lnkOrderPIN);
			Web.clickOnElement(lnkOrderPIN);
			parentWindow = Web.webdriver.getWindowHandle();
			for (String childWindow : Web.webdriver.getWindowHandles()) {
				Web.webdriver.switchTo().window(childWindow);
			}
			if (Web.isWebElementDisplayed(btnOPMailExistingPIN)
					& Web.isWebElementDisplayed(btnOPOrderTempPin)) {
				Reporter.logEvent(Status.PASS,
						"Validate if both PIN buttons display",
						"Successfully validated that both Mail Existing and"
								+ " Temporary PIN buttons are displayed", false);
		
				if (pinType.equalsIgnoreCase("Existing")) {
					Web.clickOnElement(btnOPMailExistingPIN);
					getMessage = txtExistingAndOrderPINMessage.getText()
							.replaceAll("\\d", "").replace(" ", "").trim();
		
					// Checking if all the characters are present excluding " "
					if (getMessage.replace(" ", "").equals(
							(CommonLib.MailExistingPINMsg).replace(" ", ""))
							& Web.isWebElementDisplayed(imgInfoMsg)) {
						Reporter.logEvent(
								Status.PASS,
								"Validate information message for Mail Existing PIN",
								"Message validated successfully", false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Validate information message for Mail Existing PIN",
								"Message validation failed", true);
					}
				} else if (pinType.equalsIgnoreCase("Temporary")) {
					Web.clickOnElement(btnOPOrderTempPin);
					getOrderPINMsgA = txtExistingAndOrderPINMessage
							.findElement(By.xpath("//tbody/tr/td[2]/div[2]"))
							.getText().replace(" ", "");
					getOrderPINMsgB = txtExistingAndOrderPINMessage
							.findElement(By.xpath("//tbody/tr/td[2]/div[4]"))
							.getText().replaceAll("\\d", "").replace(" ", "");
					if (getOrderPINMsgA.replace(" ", "").equals(
							(CommonLib.OrderTempPINMsgA).replace(" ", ""))
							& getOrderPINMsgB.replace(" ", "").equals(
									(CommonLib.OrderTempPINMsgB).replace(" ", ""))
					                    & Web.isWebElementDisplayed(imgInfoMsg)){
						Reporter.logEvent(
								Status.PASS,
								"Validate information message for Order Temporary PIN",
								"Message validated successfully", false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Validate information message for Order Temporary PIN",
								"Message validation failed", true);
					}
				}
			} else {
				Reporter.logEvent(Status.FAIL,
						"Validate if both PIN buttons display",
						"Either of Mail Existing or"
					  + " Temporary PIN button is not displayed", true);
			}
			Web.webdriver.close();
			Web.webdriver.switchTo().window(parentWindow);
		}

}