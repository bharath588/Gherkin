package pageobjects;

import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

//import framework.util.CommonLib;
import lib.Reporter.Status;
import lib.Stock;

public class ParticipantHome extends LoadableComponent<ParticipantHome> {

	private ArrayList<String> hireTermDateList;
	private boolean isElementDisplayed_Flag = false;
	private ArrayList<String> personalDataDB;

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

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT/PLAN SEARCH')]")
	private WebElement participantPlanSearchPage;

	@FindBy(css = "input[name = 'searchPartId']")
	private WebElement PPTIdfield;

	public WebElement getPPTIdfield() {
		return PPTIdfield;
	}

	public void setPPTIdfield(WebElement pPTIdfield) {
		PPTIdfield = pPTIdfield;
	}

	@FindBy(id = "submitPpt")
	private WebElement SubmitPPTIdBtn;

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT HOME PAGE')]")
	private WebElement PPTHomePageTitle;

	@FindBy(css = "input[name = 'searchPartSsn']")
	private WebElement SSNfield;

	public WebElement getSSNfield() {
		return SSNfield;
	}

	public void setSSNfield(WebElement sSNfield) {
		SSNfield = sSNfield;
	}

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

	@FindBy(css = "table.compactDataTable tr:nth-of-type(7) a:nth-of-type(2)")
	private WebElement lnkOrderPIN;

	@FindBy(css = "table#table_popupLayout tr:nth-of-type(2) span.titleShadow")
	private WebElement OrderPINTitle;

	@FindBy(css = "input[value = 'Mail Existing PIN']")
	private WebElement btnOPMailExistingPIN;

	@FindBy(css = "input[value = 'Order Temp PIN']")
	private WebElement btnOPOrderTempPin;

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

	private List<String> getFooterLinkList = null;

	public ParticipantHome() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertEquals(Web.webdriver.getTitle(), "CSAS v12.03.2");
	}

	@Override
	protected void load() {
		this.parent = parent;
		Web.webdriver.get(Stock.globalParam.get("AppURL"));
	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void submitLoginCredentials(String username, String password) {

		boolean isElementDisplayed = false;
		try {
			Thread.sleep(5000);
			System.out.println(username + "   " + password);
			Web.setTextToTextBox(CSASUserNameField, username);
			Thread.sleep(2000);
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
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if the user logged in to Participant/plan search page",
						"User is not logged in to Participant/plan search page",
						false);
			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to enter participant ID and click on Sign In button
	 * 
	 * @param ppt_id
	 * @throws Exception
	 */
	public void search_PPT_Plan_With_PPT_ID_OR_SSN(String ppt_id,
			WebElement searchField) {

		boolean isElementDisplayed = false;

		Web.setTextToTextBox(searchField, ppt_id);
		Web.clickOnElement(this.SubmitPPTIdBtn);
		try {
			Web.waitForElement(this.PPTHomePageTitle);
			isElementDisplayed = Web.isWebElementDisplayed(
					this.PPTHomePageTitle, true);

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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Retrieve data from db & verify
	 * 
	 * @PARAMETER = ppt_id
	 */
	public void verify_HireDate_TermDate(String ppt_id) {
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

				if (empStatus.getText().equalsIgnoreCase("ACTIVE")
						&& HireDate_TermDate_List.get(1) == null) {

					Web.mouseHover(empStatus);

					if (compareDB_Date_With_Web_Date(
							HireDate_TermDate_List.get(0), HireDate_List.get(i)
									.getText()))

						Reporter.logEvent(Status.PASS,
								"Check if Employment status is ACTIVE and Hire date: "
										+ HireDate_TermDate_List.get(0),
								"Employment status is ACTIVE and the Hire Date is:  "
										+ HireDate_List.get(i).getText(), true);
					else
						Reporter.logEvent(Status.FAIL,
								"Check if Employment status is not ACTIVE and Hire date: "
										+ HireDate_TermDate_List.get(0),
								"Employment status is not ACTIVE and the Hire Date is:  "
										+ HireDate_List.get(i).getText(), false);
					Web.clickOnElement(PPTHomePageTitle);
					Thread.sleep(100);

				} else {

					Web.mouseHover(empStatus);

					if (lnkEmploymentStatus.get(i).getText()
							.contains("TERMINATED")
							&& compareDB_Date_With_Web_Date(
									HireDate_TermDate_List.get(0),
									HireDate_List.get(i).getText())
							&& compareDB_Date_With_Web_Date(
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

				}

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> get_Hire_Term_Date_From_DB(

	String[] getHireDateAndTerminationDate, String planNum, String indID,
			int index) {

		ResultSet resultset;

		hireTermDateList = new ArrayList();

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
				// TODO Auto-generated catch block
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

			// Personal data validation..

			String FullName_DB = personal_Data_From_DB.get(0) + " "
					+ personal_Data_From_DB.get(1);

			if (personalData_On_PPT_Home_List.get(0).getText()
					.equalsIgnoreCase(FullName_DB)
					&& personalData_On_PPT_Home_List.get(1).getText()
							.equalsIgnoreCase(personal_Data_From_DB.get(2))
					&& compareDB_Date_With_Web_Date(
							personalData_On_PPT_Home_List.get(2).getText(),
							(personal_Data_From_DB.get(3)))
					&& personalData_On_PPT_Home_List.get(3).getText()
							.equalsIgnoreCase(personal_Data_From_DB.get(4))) {

				Reporter.logEvent(
						Status.PASS,
						"Check if Name,SSN,Date Of Birth and Gender in database & web is same or not \n"
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
						"Check if Name,SSN,Date Of Birth and Gender in database & web is same or not \n"
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Method to validate personal data
	 * 
	 * @PARAMETER = getPersonalDataOnPPTHomePage
	 * 
	 * @PARAMETER = ind_id
	 */
	public ArrayList<String> get_Personal_Data_From_DB(
			String[] getPersonalDataOnPPTHomePage,

			String ind_id) {

		ResultSet resultset;
		personalDataDB = new ArrayList<String>();

		resultset = DB.executeQuery(getPersonalDataOnPPTHomePage[0], ind_id);
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

	/*
	 * Compare data base date and web date
	 * 
	 * @PARAMETER = dbDate
	 * 
	 * @PARAMETER = webDate
	 * 
	 * @Author: Ranjan
	 */
	public static boolean compareDB_Date_With_Web_Date(String dbDate,
			String webDate) {

		boolean isDateEqual_Flag = false;

		Date date;

		try {

			date = new Date();

			dbDate = dbDate.substring(0, dbDate.indexOf(" "));

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

			date = df.parse(dbDate);

			SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");

			String strDate = sfd.format(date);

			if (strDate.equalsIgnoreCase(webDate))
				isDateEqual_Flag = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isDateEqual_Flag;
	}

}
