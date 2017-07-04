package pageobjects;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class LoanInfo extends LoadableComponent<LoanInfo> {

	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	//@FindBy(xpath = "//*[@id='oCMenu_25129'][contains(text() , 'Loan Info')]")
	@FindBy(xpath = ".//*[contains(@id,'oCMenu')][contains(text() , 'Loan Info')]")
	private WebElement MenuLoanInfo;

	@FindBy(css = "td.pageMenuTitle")
	private WebElement LoanInfoPageTitle;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(1) div.titleText")
	private WebElement LoanStatus_Tab;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(2) div.titleText")
	private WebElement Loans_At_othr_Prvdr_Tab;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(3) div.titleText")
	private WebElement Loan_Quaotes_Tab;

	@FindBy(xpath = "//div[contains(text(),'Loan Payoff Quote Information')]")
	private WebElement Loan_Payoff_Quaotes_info_Tab;

	// Loan Status WE..
	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(1)")
	private WebElement Loan_Number_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(2)")
	private WebElement View_Payment_History_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(3)")
	private WebElement Payoff_Quote_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(4)")
	private WebElement Loan_Status_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(5)")
	private WebElement Effective_date_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(6)")
	private WebElement Loan_Term_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(7)")
	private WebElement Maturity_Date_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(8)")
	private WebElement Loan_Amount_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(9)")
	private WebElement Payment_Amount_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(10)")
	private WebElement Payment_Frq_Mthd_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(11)")
	private WebElement Total_Outstanding_Bal_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(12)")
	private WebElement Default_Indicator_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(13)")
	private WebElement Default_Date_Label;

	@FindBy(css = "a#link_inlnagSeqnbr")
	private List<WebElement> Loan_Number_List;

	@FindBy(css = "a#link_history")
	private List<WebElement> View_Payment_Hst_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(4)")
	private List<WebElement> Loan_Status_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(5)")
	private List<WebElement> Effective_Date_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(10)")
	private List<WebElement> Payment_Frq_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(11)")
	private List<WebElement> Total_Outstanding_Bal_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(12)")
	private List<WebElement> Default_Indicator_List;

	// Loan info Loan History
	@FindBy(id = "table_paymentHistory")
	private WebElement PaymentHistoryTab;

	@FindBy(css = "table#table_paymentHistory tr>td:nth-of-type(12)")
	private List<WebElement> PaymentStatus;

	@FindBy(css = "table#table_paymentHistory tr>td:nth-of-type(10)")
	private List<WebElement> PaymentEffectiveDate;
	
	@FindBy(css = "table#table_paymentHistory tr>td:nth-of-type(9)")
	private List<WebElement> PaymentDueDate;

	LoadableComponent<?> parent;

	public LoanInfo() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(LoanInfoPageTitle));
	}

	@Override
	protected void load() {
		// this.parent = new ParticipantHome().get();
		Web.mouseHover(MenuPPTInfo);
		if (Web.isWebElementDisplayed(MenuLoanInfo, true)) {
			Web.clickOnElement(MenuLoanInfo);
			if (Web.isWebElementDisplayed(LoanInfoPageTitle, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Loan info page displayed or not",
						"Loan info page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Loan info page displayed or not",
						"Loan info page didn't disply successfully", true);
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan info Link on Participant Info tab displayed or not",
					"Loan info Link on Participant Info tab didn't display successfully",
					true);
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

	private List<WebElement> getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("Total Outstanding Bal")) {
			return this.Total_Outstanding_Bal_List;
		}
		if (fieldName.trim().equalsIgnoreCase("PaymentFrqMthd")) {
			return this.Payment_Frq_List;
		}
		if (fieldName.trim().equalsIgnoreCase("Dafault_Ind")) {
			return this.Default_Indicator_List;
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
	public void verify_LoanInfoPage() {

		if (Web.isWebElementDisplayed(MenuPPTInfo, true)) {

			if (Web.isWebElementDisplayed(MenuLoanInfo, true)) {
				Web.clickOnElement(MenuLoanInfo);
				if (Web.isWebElementDisplayed(LoanInfoPageTitle, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Loan info page displayed or not",
							"Loan info page displyed successfully", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Loan info page displayed or not",
							"Loan info page didn't disply successfully", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Loan info Link on Participant Info tab displayed or not",
						"Loan info Link on Participant Info tab didn't disply successfully",
						true);
			}
		}
	}

	/**
	 * <pre>
	 * Method to get PPT ID based on the PDI status
	 * </pre>
	 * 
	 * @return pptID
	 * @author rnjbdn
	 */
	public ArrayList<String> getPPTIDAndExpDataForLoanInfo(
			String expecteddata_loaninfo) throws Exception {
		ResultSet resultset = null;
		ArrayList<String> loanInfo_List = new ArrayList<String>();
		resultset = DB.executeQuery(
				Stock.getTestQuery("getPPTIDforLoanInfo")[0],
				Stock.getTestQuery("getPPTIDforLoanInfo")[1]);
		if (resultset != null) {
			while (resultset.next()) {
				loanInfo_List.add(resultset.getString("IND_ID"));
				loanInfo_List.add(resultset.getString("GA_ID"));
				loanInfo_List.add(resultset.getString(expecteddata_loaninfo));

			}
		}
		return loanInfo_List;
	}

	/**
	 * <pre>
	 * Method to verify Loan status details on Loan info page
	 * </pre>
	 * 
	 * @return pptID
	 * @author rnjbdn
	 * @throws ParseException
	 */
	public void verify_Loan_Status_Details(String webelement,
			String loanStsVal_From_DB, String loanSts_var)
			throws ParseException {
		boolean isLoanStatusEleDisplayed = false;
		boolean isLoanStsEqual = false;
		String loanSts_Val_On_Web;
		List<WebElement> loanStsWE = getWebElement(webelement.toUpperCase());
		if (Web.isWebElementDisplayed(LoanStatus_Tab, true)) {
			Reporter.logEvent(Status.PASS,
					"Check if Loan Status table displayed or not",
					"Loan Status table displayed successfully", false);
			if (Loan_Status_List.size() <= 0) {
				throw new AssertionError("Non of the loan is in active state");
			}
			for (int i = 0; i < Loan_Status_List.size(); i++) {
				if (Loan_Status_List.get(i).getText().equalsIgnoreCase("Active")) {
					isLoanStatusEleDisplayed = Web.isWebElementDisplayed(loanStsWE
							.get(i));

					if (isLoanStatusEleDisplayed) {
						loanSts_Val_On_Web = loanStsWE.get(i).getText();
						switch (loanSts_var) {
						case "LOAN_AMT":
							// if (loanSts_Val_On_Web.contains(loanStsVal_From_DB))
							// {
							Number number = null;
							number = NumberFormat.getCurrencyInstance(Locale.US)
									.parse(loanSts_Val_On_Web);
							if (number != null) {
								isLoanStsEqual = true;
							}
							break;
						case "REPAY_FREQ":
							switch (loanStsVal_From_DB) {
							case "W":
								if (loanSts_Val_On_Web.contains("Weekly")) {
									isLoanStsEqual = true;
								}
								break;
							case "M":
								if (loanSts_Val_On_Web.contains("Monthly")) {
									isLoanStsEqual = true;
								}
								break;
							case "BW":
								if (loanSts_Val_On_Web.contains("Bi Weekly")) {
									isLoanStsEqual = true;
								}
								break;
							case "SM":
								break;
							}
							break;
						case "REPAY_MTHD_CODE":
							if (StringUtils.containsIgnoreCase(loanSts_Val_On_Web,
									loanStsVal_From_DB)) {
								isLoanStsEqual = true;
							}
							break;
						case "DEFAULT_IND":
							switch (loanStsVal_From_DB) {
							case "Y":
								if (StringUtils.containsIgnoreCase(
										loanSts_Val_On_Web, "YES")) {
									isLoanStsEqual = true;
								}
								break;
							case "N":
								if (StringUtils.containsIgnoreCase(
										loanSts_Val_On_Web, "NO")) {
									isLoanStsEqual = true;
								}
								break;
							}
							break;
						}
						if (isLoanStsEqual) {
							Reporter.logEvent(
									Status.PASS,
									"Check if " + loanSts_var
											+ " displayed properly for Loan status",
									loanSts_var
											+ " displayed successfully for the Loan status \n\n"
											+ loanSts_var + " :"
											+ loanSts_Val_On_Web, false);
						} else {
							Reporter.logEvent(
									Status.FAIL,
									"Check if " + loanSts_var
											+ " displayed properly for Loan status",
									loanSts_var
											+ " displayed successfully for the Loan status \n\n"
											+ loanSts_var + " :"
											+ loanSts_Val_On_Web, false);
						}
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Check if " + loanSts_var
										+ " displayed properly for Loan status",
								loanSts_var
										+ " didn't displayed successfully for the Loan status \n\n",
								true);
					}
					break ;
				}
			}	
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Loan Status table displayed or not",
					"Loan Status table didn't displayed successfully", true);
		}
	}

	/**
	 * <pre>
	 * Method to verify Loan Info LOan History
	 * </pre>
	 */
	public void verify_LoanHistory_PAID_Or_UNPAID(String paymentSts) {
		boolean isPSAsExpected = false;
		if (Loan_Status_List.size() <= 0) {
			throw new AssertionError("Non of the loan is in active state");
		} else {
			for (int i = 0; i < Loan_Status_List.size(); i++) {
				if (Loan_Status_List.get(i).getText().contains("Active")
						&& View_Payment_Hst_List.get(i).getText()
								.equalsIgnoreCase("History")) {
					Web.clickOnElement(View_Payment_Hst_List.get(i));
					if (Web.isWebElementDisplayed(PaymentHistoryTab)) {
						if (PaymentStatus.size() <= 0
								&& PaymentEffectiveDate.size() <= 0)
							throw new AssertionError(
									"Didn't display any info in Payment History table.");
						else {
							for (int j = 0; j < PaymentStatus.size(); j++) {
								switch (paymentSts) {
								case "UNPAID":
									if (PaymentStatus.get(j).getText()
											.equalsIgnoreCase("UNPAID")
											&& PaymentEffectiveDate.get(j)
													.getText().contains("N/A"))
										isPSAsExpected = true;
									break;
								case "PAID":
									if (PaymentStatus.get(j).getText()
											.equalsIgnoreCase("PAID")
											&& PaymentDueDate
													.get(j)
													.getText()
													.matches(
															"^\\d{2}-[a-zA-Z]{3}-\\d{4}$"))
										isPSAsExpected = true;
									break;
								}
							}
						}
					} else
						Reporter.logEvent(
								Status.FAIL,
								"Check if Payment History tab displayed or not.",
								"Payment History tab didn't displayed.", true);
					break;
				}
			}
			if (isPSAsExpected) {
				Reporter.logEvent(
						Status.PASS,
						"Check if for the for the Payment status "
								+ paymentSts
								+ "  payment effective date type is N/A and date respectively",
						"For the Payment status "
								+ paymentSts
								+ " , payment effective date type is N/A and date respectively.",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check for the Payment status "
								+ paymentSts
								+ "  payment effective date type is N/A and date respectively",
						"For the Payment status "
								+ paymentSts
								+ " , payment effective date type is not N/A and date respectively.",
						true);
			}
		}
	}
	

}
