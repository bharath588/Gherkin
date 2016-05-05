package pageobjects;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lib.DB;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import core.framework.Globals;

public class LoanQuote extends LoadableComponent<LoanQuote> {

	@FindBy(xpath = "//*[@id='oCMenu_316'][contains(text(),'Participant Changes')]")
	private WebElement menuPPTChanges;

	@FindBy(xpath = "//*[@id='oCMenu_327'][contains(text() , 'Loan Quote')]")
	private WebElement menuLoanQuote;

	@FindBy(xpath = "//*[@id='table_Loan Quote']//td[contains(text() , 'Loan Quote')]")
	private WebElement loanQuoteTitle;

	@FindBy(xpath = "//div[@class='dataContainer']//div[contains(text() , 'Select Loan Type')]")
	private WebElement selectLoanType;

	@FindBy(xpath = "//div[@class='dataContainerBody']//td[contains(text() , 'General Purpose')]/input")
	private WebElement generalLoanType;

	@FindBy(xpath = "//div[@class='dataContainerBody']//td[contains(text() , 'Principal Residence')]/input")
	private WebElement principalLoanType;

	@FindBy(xpath = "//table[@class='innerDataTable']//input[@value='Continue']")
	private WebElement btnContSelectLoanType;

	@FindBy(xpath = "//*[@id='table_workLayout']//table[@class='innerDataTable']//input[@value='Calculate Available Loan Amount']")
	private WebElement btnCalAvailableLoanAmt;

	@FindBy(xpath = "//*[@id='table_workLayout']//table[@class='innerDataTable']//td[@class='colTitle dataFieldTitleLeft']")
	private WebElement selectedLoanTypeText;

	@FindBy(xpath = "//*[@id='table_workLayout']//table[@class='compactDataTable']//tr[2]/td[@class='colTitle']")
	private WebElement existingLoanTypeText;

	@FindBy(xpath = "//form[@name='loanQuoteForm'][1]//table[@class='innerDataTable'][1]//td[contains(text(),'Plan Maximum Loan Amount')]/followingsibling::td/a")
	private WebElement planMaxLoanAmt;

	@FindBy(xpath = "//form[@name='loanQuoteForm'][1]//table[@class='innerDataTable'][1]//td[contains(text(),'Plan Minimum Loan Amount')]/followingsibling::td")
	private WebElement planMinLoanAmt;

	@FindBy(xpath = "//form[@name='loanQuoteForm'][1]//table[@class='innerDataTable'][1]//td[contains(text(),'Plan Interest Rate')]/followingsibling::td/a")
	private WebElement planIntRate;

	@FindBy(xpath = "//form[@name='loanQuoteForm'][1]//table[@class='innerDataTable'][1]//td[contains(text(),'maximum number of loans allowed')]/followingsibling::td")
	private WebElement planMaxNoOfLoanAllowed;

	@FindBy(xpath = "//*[@id='table_workLayout']//div[@id='overDiv']//table[@class='compactDataTable']//tr")
	private List<WebElement> planMaxLoanAmtDetails;

	private String selectedLoanTypeMsg = "Account balance is reduced by the amount of the loan."
			+ "Loan payments will be deposited based on the investment allocation at the time payment is received."
			+ "The payroll frequency for loan repayments is the same as plan contributions."
			+ "Some plans may have a loan processing fee and/or maintenance fee.";

	private String existingLoanTypeMsg = "The maximum available loan amount is VAL"
			+ "Submitting a loan application through your employer to obtain your current vesting percentage may qualify you for a higher loan amount."
			+ "The plan allows for:"
			+ "A General Purpose loan may be obtained for a term of 12 to 60 months."
			+ "Note: A maximum of 5 quotes can be run and viewed at the same time.";

	LoadableComponent<?> parent;

	public LoanQuote() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(loanQuoteTitle));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		Web.mouseHover(menuPPTChanges);
		if (Web.isWebElementDisplayed(menuLoanQuote, true)) {
			Web.clickOnElement(menuLoanQuote);
			if (Web.isWebElementDisplayed(loanQuoteTitle, true)) {
				Reporter.logEvent(Status.PASS,
						"Check if Loan Quote page displayed or not",
						"Loan Quote page displyed successfully", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if Loan Quote page displayed or not",
						"Loan Quote didn't get displayed successfully", true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Loan Quote Link on Participant Changes tab displayed or not",
					" Loan Quote Link on Participant Changes tab didn't get displayed successfully",
					true);
		}
	}

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("BTN_CALC_AVAIL_LOAN_AMT")) {
			return this.btnCalAvailableLoanAmt;
		}
		return null;
	}

	public boolean SelectLoanType(String LoanType) {
		if (Web.isWebElementDisplayed(selectLoanType)) {
			Reporter.logEvent(Status.INFO, "Select Loan Type page displayed",
					"Select Loan Type page displayed as expected", true);
			if (LoanType.equalsIgnoreCase("General")) {
				Web.clickOnElement(generalLoanType);
			} else if (LoanType.equalsIgnoreCase("Principal")) {
				Web.clickOnElement(principalLoanType);
			} else {
				return false;
			}
			Web.clickOnElement(btnContSelectLoanType);
			Reporter.logEvent(Status.INFO, "Selected Loan Type :" + LoanType,
					"Selected Loan Type :" + LoanType + " successfully", false);
			return true;
		}
		Reporter.logEvent(Status.FAIL,
				"Select Loan Type page is not displayed",
				"Select Loan Type page is not displayed", true);
		return false;
	}

	public boolean VerifyMsg_for_Selected_and_Existing_Loan(
			String getParameterValue) {
		String existingLoanMsg = Globals.GC_EMPTY;
		if (getParameterValue.equalsIgnoreCase("Selected")) {
			if (!Web.isWebElementDisplayed(btnCalAvailableLoanAmt, true)) {
				Reporter.logEvent(Status.FAIL,
						"Navigation to Loan Quote page unsuccessfull",
						"Loan Quote page didn't get displayed", true);
				return false;
			} else {
				Web.clickOnElement(btnCalAvailableLoanAmt);
				Reporter.logEvent(Status.INFO,
						"Navigation to Loan Quote page is successfull",
						"Loan Quote page is displayed as expected", true);
			}
			if (selectedLoanTypeText.getText().trim().replaceAll("\\n", "")
					.replaceAll(" ", "")
					.equals(selectedLoanTypeMsg.replaceAll(" ", ""))) {
				return true;
			}
		}

		if (getParameterValue.equalsIgnoreCase("Existing")) {
			existingLoanMsg = existingLoanTypeText.getText()
					.replaceAll("\\$.*.", "VAL").replaceAll("\\n", "")
					.replaceAll(" ", "");
			if (existingLoanMsg.equals(existingLoanTypeMsg.replaceAll(" ", ""))) {
				return true;
			}
		}
		return false;
	}

	public boolean Verify_Details_for_Selected_Loan(String InformationType) {
		ResultSet resultset = null;
		Map<Integer, Map<String, String>> results = null;
		Map<String, String> resultData = null;

		// Executing query
		try {
			resultset = DB.executeQuery(
					Stock.getTestQuery("get" + InformationType)[0],
					Stock.getTestQuery("get" + InformationType)[1],
					Stock.GetParameterValue("planNumber"));
			if (resultset != null) {
				ResultSetMetaData meta = resultset.getMetaData();
				results = new HashMap<Integer, Map<String, String>>();
				int iKey = 1;
				while (resultset.next()) {
					resultData = new LinkedHashMap<String, String>();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						String key = meta.getColumnName(i);
						String value = (String) resultset.getString(key);
						resultData.put(key, value);
					}
					results.put(iKey, resultData);
					iKey++;
				}
			}
		} catch (Exception e) {
			// Throw no exception
			// To handle a scenario where no queries required
		}

		// Handles Loan Quote various information validation
		switch (InformationType) {
		case "MaxLoanAmount":
			if (Web.isWebElementDisplayed(planMaxLoanAmt)) {
				String maxAmt = planMaxLoanAmt.getText();
				String[] actualMaxLoanRange = null;
				if (maxAmt.replace(",", "").matches(".*\\d.*")) {
					Reporter.logEvent(
							Status.PASS,
							"Validating if Plan Max Loan Amount has a numeric value",
							"Validated successfully that a numeric value : "
									+ maxAmt
									+ " is displayed against Plan Maximum Loan Amount",
							false);
					Web.mouseHover(planMaxLoanAmt);

					for (int iRow = 1; iRow <= planMaxLoanAmtDetails.size() - 1; iRow++) {
						actualMaxLoanRange = planMaxLoanAmtDetails.get(iRow)
								.getText().replace(".00", "")
								.replace(".0%", "").replace(".00%", "")
								.replace("$", "").replace("%", "")
								.replace("N/A", "null").replace(",", "")
								.split(" ");
						if (!results.get(iRow).values().toString()
								.equals(Arrays.toString(actualMaxLoanRange))) {
							return false;
						}
					}
					return planMaxLoanAmtDetails.size() >= 1 ? true : false;
				}
			}
		case "MinLoanAmount":
			if (Web.isWebElementDisplayed(planMinLoanAmt)) {
				String minAmt = planMinLoanAmt.getText().replace("$", "")
						.replace(",", "").replace(".00", "");
				if (!results.get(1).values().toString()
						.equals("[" + minAmt + "]")) {
					return false;
				}
				return true;
			}
		case "PlanIntRate":
			if (Web.isWebElementDisplayed(planIntRate)) {
				String minAmt = planIntRate.getText().replace(",", "")
						.replace("%", "");
				if (!NumberUtils.isNumber(minAmt)) {
					return false;
				}
				return true;
			}
		case "MaxLoanAllowed":
			if (Web.isWebElementDisplayed(planMaxNoOfLoanAllowed)) {
				String maxNoOfLoans = planMaxNoOfLoanAllowed.getText();
				if (!results.get(1).values().toString()
						.equals("[" + maxNoOfLoans + "]")) {
					return false;
				}
				return true;
			}
		default:
			Reporter.logEvent(Status.FAIL, "Invalid parameter passed",
					"Invalid InformationType passed : " + InformationType,
					false);
		}
		return false;
	}

}