package pageobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import framework.util.CommonLib;

public class DisbursementInfoOrHistory extends
		LoadableComponent<DisbursementInfoOrHistory> {

	@FindBy(xpath = "//div[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	@FindBy(xpath = "//*[starts-with(@id,'oCMenu')][contains(text() , 'Disbursement Info/History')]")
	private WebElement MenuDisbursementInfo;

	@FindBy(css = "td.pageMenuTitle")
	private WebElement DisbursementInfoPageTitle;

	@FindBy(css = "table[id='DisbSummary']")
	private WebElement tblDisbursementSummary;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu0']")
	private WebElement tabDisbursementType;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu1']")
	private WebElement tabProcessingDet;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu2']")
	private WebElement tabPaymentHist;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu3']")
	private WebElement tabWithholding;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu4']")
	private WebElement tabInvestmentSales;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu5']")
	private WebElement tabCostBasis;

	@FindBy(css = "div[id='tabMenuDiv'] span[id='tabMenu6']")
	private WebElement tabVesting;

	@FindBy(xpath = ".//*[@id='tabPane2']/table/tbody/tr/td/div/div[2]")
	private WebElement divPaymentHistoryData;

	@FindBy(xpath = ".//*[@id='tabPane1']/table/tbody/tr/td/div/div/div[2]")
	private WebElement divProcessingDetailsData;

	@FindBy(css = "input[name = 'fromDate'][type = 'text']")
	private WebElement DHFromDateFiled;

	@FindBy(css = "input[name = 'submitDateQuery'][type = 'submit']")
	private WebElement DHGoBtn;

	// Disbursement Summary sections..
	@FindBy(xpath = "//th[contains(text(),'Event ID/')]//..//..//..//following-sibling::tbody/tr/td[2]/a")
	private List<WebElement> DSEventID;

	@FindBy(xpath = "//th[contains(text(),'Disbursement')]//..//..//..//following-sibling::tbody/tr/td[3]")
	private List<WebElement> DSDisbursementSts;

	// Disbursement tabs..
	@FindBy(xpath = "//div[@id = 'tabMenuDiv']//span[contains(text(),'Vesting')]")
	private WebElement DHVestingTab;

	@FindBy(xpath = "//div[@id = 'tabMenuDiv']//span[contains(text(),'Withholding')]")
	private WebElement DHWithholdingTab;

	@FindBy(xpath = "//div[@id = 'tabMenuDiv']//span[contains(text(),'Processing Details')]")
	private WebElement DHProcessingDtlsTab;

	@FindBy(xpath = "//div[@id = 'tabMenuDiv']//span[contains(text(),'Disbursement Type')]")
	private WebElement DHDisbursementType;

	// Payment history details..
	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Date')]//..//..//..//following-sibling::tbody//tr//td[1]")
	private List<WebElement> PHDate;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Status')]//..//..//..//following-sibling::tbody//tr//td[2]")
	private List<WebElement> PHStatus;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Amount')]//..//..//..//following-sibling::tbody//tr//td[3]")
	private List<WebElement> PHAmount;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Check')]//..//..//..//following-sibling::tbody//tr//td[4]")
	private List<WebElement> PHCheckNo;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Express')]//..//..//..//following-sibling::tbody//tr//td[12]")
	private List<WebElement> PHExpressCarrier;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Tracking')]//..//..//..//following-sibling::tbody//tr//td[13]")
	private List<WebElement> PHTrackingNumber;

	@FindBy(xpath = "//table[@id = 'paymentHistory']//th[contains(text(),'Ship')]//..//..//..//following-sibling::tbody//tr//td[14]")
	private List<WebElement> PHShipDate;

	// Vesting details..

	@FindBy(xpath = "//th[contains(text(), 'Money Type/Description')]//..//..//following-sibling::tbody//td[1]")
	private WebElement VMoneyType;

	@FindBy(xpath = "//th[contains(text(), 'Vesting%')]//..//..//following-sibling::tbody//td[2]")
	private WebElement VVestingPercentage;

	@FindBy(xpath = "//th[contains(text(), 'Forfeited Amount')]//..//..//following-sibling::tbody//td[3]")
	private WebElement VFortifiedAmt;

	@FindBy(xpath = "//th[contains(text(), 'Vesting Schedule')]//..//..//following-sibling::tbody//td[4]/a")
	private WebElement VVestingSchedule;

	@FindBy(xpath = "//th[contains(text(), 'Hire Date')]//..//..//following-sibling::tbody//td[5]")
	private WebElement VHireDate;

	@FindBy(xpath = "//th[contains(text(), 'Termination Date')]//..//..//following-sibling::tbody//td[6]")
	private WebElement VTermDate;

	@FindBy(xpath = "//th[contains(text(), 'Vesting')]//..//..//following-sibling::tbody//td[7]")
	private WebElement VVestingOverrideIND;

	LoadableComponent<?> parent;

	public DisbursementInfoOrHistory() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(DisbursementInfoPageTitle));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		verify_DisbursementInfoPage();
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	VESTING - [Tab]
	 * 	WITHHOLDING - [Tab]
	 * 	PROCESSINGDETAILS - [Tab]
	 * 	DISTBURSEMENTTYPE - [Tab]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("VESTING")) {
			return this.DHVestingTab;
		}

		if (fieldName.trim().equalsIgnoreCase("WITHHOLDING")) {
			return this.DHWithholdingTab;
		}

		if (fieldName.trim().equalsIgnoreCase("PROCESSINGDETAILS")) {
			return this.DHProcessingDtlsTab;
		}

		if (fieldName.trim().equalsIgnoreCase("DISTBURSEMENTTYPE")) {
			return this.DHDisbursementType;
		}

		if (fieldName.trim().equalsIgnoreCase("PAYMENTHISTORY")) {
			return this.tabPaymentHist;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * <pre>
	 * Method to Loan info page
	 * </pre>
	 * 
	 * @author RANJAN
	 */
	public void verify_DisbursementInfoPage() {

		if (Web.isWebElementDisplayed(MenuPPTInfo)) {
			Web.mouseHover(MenuPPTInfo);
			if (Web.isWebElementDisplayed(MenuDisbursementInfo)) {
				Web.clickOnElement(MenuDisbursementInfo);
				if (Web.isWebElementDisplayed(DisbursementInfoPageTitle, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Disbursement info page displayed or not",
							"Disbursement info page displyed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Disbursement info page displayed or not",
							"Disbursement info page didn't disply successfully",
							true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Disbursement info Link on Participant Info tab displayed or not",
						"Disbursement info Link on Participant Info tab didn't disply successfully",
						true);
			}
		}
	}

	/**
	 * <pre>
	 * Method to get Disbursement history details from DB
	 * </pre>
	 * 
	 * @return DisbursementHistoryDetails List
	 * @author rnjbdn
	 */
	public ArrayList<String> getDisbursementHistoryDtls_DB() throws Exception {
		ArrayList<String> disbursementHistoryDtls = new ArrayList<String>();
		ResultSet resultset = null;
		resultset = DB.executeQuery(
				Stock.getTestQuery("getPaymentHistoryDtls")[0],
				Stock.getTestQuery("getPaymentHistoryDtls")[1]);
		if (resultset != null) {
			while (resultset.next()) {
				disbursementHistoryDtls.add(resultset.getString("SSN"));
				disbursementHistoryDtls.add(resultset.getString("EV_ID"));
				disbursementHistoryDtls.add(resultset.getString("EFFDATE"));
				disbursementHistoryDtls.add(resultset
						.getString("sap_payment_status_code"));
				disbursementHistoryDtls.add(resultset.getString("amount"));
				disbursementHistoryDtls.add(resultset.getString("check_nbr"));
				disbursementHistoryDtls
						.add(resultset.getString("carrier_code"));
				disbursementHistoryDtls
						.add(resultset.getString("tracking_nbr"));
				disbursementHistoryDtls.add(resultset.getString("ship_date"));
			}
		}
		return disbursementHistoryDtls;
	}

	/**
	 * <pre>
	 * Method to verify payment history details
	 * </pre>
	 * 
	 * @throws ParseException
	 */
	public void validatePaymentHisDtls(ArrayList<String> paymentHistDtls)
			throws ParseException {
		if (PHDate.size() <= 0) {
			throw new AssertionError(
					"Does not display any information in payment history section.");
		} else {
			int webEle_Indx = 0 ;
			boolean isPaymentHisDtls = false ;
			for (int i = 0; i < PHDate.size(); i++) {
				System.out.println(paymentHistDtls.get(2));
					if (CommonLib.compareDB_Date_With_Web_Date(
							paymentHistDtls.get(2), PHDate.get(i).getText())
							/*
							 * && PHStatus .get(i) .getText() .contains(
							 * paymentHistDtls.get(3))
							 */
							&& PHAmount
									.get(i)
									.getText()
									.replaceAll(",", "")
									.contains(
											paymentHistDtls.get(4).replaceAll(
													"-", ""))
							&& PHCheckNo.get(i).getText()
									.contains(paymentHistDtls.get(5))
							&& PHExpressCarrier.get(i).getText()
									.contains(paymentHistDtls.get(6))
							&& PHTrackingNumber.get(i).getText()
									.contains(paymentHistDtls.get(7))
							&& CommonLib.compareDB_Date_With_Web_Date(
									paymentHistDtls.get(8), PHShipDate.get(i)
											.getText())) {
						isPaymentHisDtls = true ;
						webEle_Indx = i ;
						break ;
					}
			}
			if (isPaymentHisDtls) {
				Reporter.logEvent(
						Status.PASS,
						"Check if Processing history displayed with follwing details. \n Date:"
								+ paymentHistDtls.get(2)
								+ " \n Status: "
								+ paymentHistDtls.get(3)
								+ " \n Amount: "
								+ paymentHistDtls.get(4)
								+ " \n Check No: "
								+ paymentHistDtls.get(5)
								+ " \n Express Carrier: "
								+ paymentHistDtls.get(6)
								+ " \n Tracking Number: "
								+ paymentHistDtls.get(7)
								+ " \n Ship Date: "
								+ paymentHistDtls.get(8) + " \n",
						"Processing history displayed with follwing details successfully. \n Date:"
								+ PHDate.get(webEle_Indx).getText()
								+ " \n Status: "
								+ PHStatus.get(webEle_Indx).getText()
								+ " \n Amount: "
								+ PHAmount.get(webEle_Indx).getText()
								+ " \n Check No: "
								+ PHCheckNo.get(webEle_Indx).getText()
								+ " \n Express Carrier: "
								+ PHExpressCarrier.get(webEle_Indx).getText()
								+ " \n Tracking Number: "
								+ PHTrackingNumber.get(webEle_Indx).getText()
								+ " \n Ship Date: "
								+ PHShipDate.get(webEle_Indx).getText() + " \n",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Processing history displayed with follwing details. \n Date:"
								+ paymentHistDtls.get(2)
								+ " \n Status: "
								+ paymentHistDtls.get(3)
								+ " \n Amount: "
								+ paymentHistDtls.get(4)
								+ " \n Check No: "
								+ paymentHistDtls.get(5)
								+ " \n Express Carrier: "
								+ paymentHistDtls.get(6)
								+ " \n Tracking Number: "
								+ paymentHistDtls.get(7)
								+ " \n Ship Date: "
								+ paymentHistDtls.get(8) + " \n",
						"Processing history displayed with follwing details successfully. \n Date:"
								+ PHDate.get(webEle_Indx).getText()
								+ " \n Status: "
								+ PHStatus.get(webEle_Indx).getText()
								+ " \n Amount: "
								+ PHAmount.get(webEle_Indx).getText()
								+ " \n Check No: "
								+ PHCheckNo.get(webEle_Indx).getText()
								+ " \n Express Carrier: "
								+ PHExpressCarrier.get(webEle_Indx).getText()
								+ " \n Tracking Number: "
								+ PHTrackingNumber.get(webEle_Indx).getText()
								+ " \n Ship Date: "
								+ PHShipDate.get(webEle_Indx).getText() + " \n",
						false);
			}
		}
	}

	/**
	 * <pre>
	 * Method to select specific event based on the status if <b>COMPLETE</b>
	 * </pre>
	 * 
	 * @throws ParseException
	 */
	public void clickOnEventID(String date, String eventNo)
			throws ParseException {

		if (Web.isWebElementDisplayed(DHFromDateFiled)) {
			String dateFormat = DHFromDateFiled.getAttribute("placeholder");
			Date date1 = new Date();
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",
					Locale.ENGLISH);
			SimpleDateFormat format2 = new SimpleDateFormat(dateFormat);
			date1 = format1.parse(date);
			String date2 = format2.format(date1);
			DHFromDateFiled.clear();
			DHFromDateFiled.sendKeys(date2);
			Web.clickOnElement(DHGoBtn);
			if (Web.isWebElementDisplayed(tblDisbursementSummary)) {
				Reporter.logEvent(Status.PASS,
						"Check if Disbursement Summary displayed",
						"Disbursement Summary displayed successfully", true);
				if (DSEventID.size() <= 0) {
					throw new AssertionError("Does not display any events.");
				} else {
					for (int i = 0; i <= DSEventID.size(); i++) {
						if (eventNo
								.equalsIgnoreCase(DSEventID.get(i).getText())
								&& DSDisbursementSts.get(i).getText().trim()
										.equalsIgnoreCase("COMPLETE")) {
							Web.clickOnElement(DSEventID.get(i));
							Reporter.logEvent(
									Status.PASS,
									"Check if Disbursement Summary displayed with events or not.",
									"Disbursement Summary displayed with lis of events successfully",
									true);
							break;
						}
					}
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Disbursement Summary displayed",
						"Disbursement Summary didn't displayed with list of events.",
						true);
			}
		}
	}

	/**
	 * <pre>
	 * Click on specific tab on Disbursement History page based on the test case
	 * </pre>
	 * 
	 * @param dhTabEle
	 *            <b>Disbursement History Tab element</b>
	 */
	public void clickOnTABOnDHPage(String dhTabEle) {
		WebElement DibHisTabField = getWebElement(dhTabEle.toUpperCase());
		Web.clickOnElement(DibHisTabField);
		String classAttribute = DibHisTabField.getAttribute("class");
		if (classAttribute.contains("Active")) {
			Reporter.logEvent(Status.PASS, "Check if we are in " + dhTabEle
					+ " page.", "Yes,we are in " + dhTabEle + " page.", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Check if we are in " + dhTabEle
					+ " page.", "Yes,we are not in " + dhTabEle + " page.",
					true);
		}
	}

	/**
	 * <pre>
	 * Verify vesting details
	 * </pre>
	 */
	public void verifyVestingDetails() {
		if (Web.isWebElementDisplayed(VMoneyType)
				&& Web.isWebElementDisplayed(VVestingPercentage)
				&& Web.isWebElementDisplayed(VFortifiedAmt)
				&& Web.isWebElementDisplayed(VVestingSchedule)
				&& Web.isWebElementDisplayed(VHireDate)
				&& Web.isWebElementDisplayed(VTermDate)
				&& Web.isWebElementDisplayed(VVestingOverrideIND)) {
			Reporter.logEvent(Status.PASS, "Verify vesting details.",
					"Vesting details verified successfully.", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify vesting details.",
					"Vesting details did not verified successfully.", true);
		}
	}

	/**
	 * <pre>
	 * Method to get Processing details information from DB
	 * </pre>
	 * 
	 * @param ind_id
	 * @return processingDtlsList : Event ID,Master Event ID, Disbursement
	 *         Processing date, PSC Approval Required on Plan?
	 * @throws SQLException
	 * 
	 */
	public ArrayList<String> getProcessingDetails_DB(String ind_id)
			throws SQLException {
		ArrayList<String> processingDtlsList = new ArrayList<String>();
		ResultSet resultset = null;
		resultset = DB.executeQuery(
				Stock.getTestQuery("getProcessingDetails")[0],
				Stock.getTestQuery("getProcessingDetails")[1], ind_id);
		if (resultset != null) {
			while (resultset.next()) {
				processingDtlsList.add(resultset.getString("EV_ID"));
				processingDtlsList.add(resultset.getString("MASTER_EV_ID"));
				processingDtlsList.add(resultset.getString("DPDATE_TIME"));
				processingDtlsList.add(resultset.getString("DISB_PSC_IND"));

			}
		}
		return processingDtlsList;
	}

	/**
	 * <pre>
	 * Method to verify Processing details
	 * </pre>
	 */
	public void validateProcessingDetails() {

	}

}
