package pageobjects;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class LoanInfo extends LoadableComponent<LoanInfo> {

	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	@FindBy(xpath = "//*[@id='oCMenu_329'][contains(text() , 'Loan Info')]")
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

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(11)")
	private List<WebElement> Default_Indicator_List;

	LoadableComponent<?> parent;

	public LoanInfo() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(LoanInfoPageTitle));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		Web.mouseHover(MenuPPTInfo);
		if (Web.isWebElementDisplayed(MenuLoanInfo, true)) {
			Web.clickOnElement(MenuLoanInfo);
			Reporter.logEvent(Status.INFO, "Check if Loan info page open",
					"Loan info page displayed successfully", true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan info Link on Participant Info tab displayed or not",
					"Loan info Link on Participant Info tab didn't disply successfully",
					true);
		}
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
	 */
	public void verify_Loan_Status_Details(String loanStsVal_From_DB,
			String loanSts_var) {
		boolean isLoanStatusEleDisplayed = false;
		String loanSts_Val_On_Web;
		if (Web.isWebElementDisplayed(LoanStatus_Tab, true)) {
			Reporter.logEvent(Status.PASS,
					"Check if Loan Status table displayed or not",
					"Loan Status table displayed successfully", false);
			if (Loan_Status_List.size() <= 0) {
				throw new AssertionError("Non of the loan is in active state");
			}
			for (int i = 0; i < Loan_Status_List.size(); i++) {
				isLoanStatusEleDisplayed = Web
						.isWebElementDisplayed(Total_Outstanding_Bal_List
								.get(i));
				if (isLoanStatusEleDisplayed) {
					loanSts_Val_On_Web = Total_Outstanding_Bal_List.get(i)
							.getText();
					if (loanSts_Val_On_Web.contains(loanStsVal_From_DB)) {
						Reporter.logEvent(
								Status.PASS,
								"Check if " + loanSts_var
										+ " displayed properly for Loan status",
								loanSts_var
										+ " displayed successfully for the Loan status /n/n"
										+ loanSts_var + " :"
										+ loanSts_Val_On_Web, false);
					}else{
						Reporter.logEvent(
								Status.FAIL,
								"Check if " + loanSts_var
										+ " displayed properly for Loan status",
								loanSts_var
										+ " displayed successfully for the Loan status /n/n"
										+ loanSts_var + " :"
										+ loanSts_Val_On_Web, false);
					}
				}else{
					Reporter.logEvent(
							Status.FAIL,
							"Check if " + loanSts_var
									+ " displayed properly for Loan status",
							loanSts_var
									+ " didn't displayed successfully for the Loan status /n/n"
									, true);
				}
			}
		}else{
			Reporter.logEvent(Status.PASS,
					"Check if Loan Status table displayed or not",
					"Loan Status table didn't displayed successfully", true);
		}
	}
}
