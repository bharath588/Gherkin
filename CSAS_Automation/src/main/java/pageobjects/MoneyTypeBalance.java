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

public class MoneyTypeBalance extends LoadableComponent<MoneyTypeBalance> {

	ArrayList<String> legal_Name_List;
	ArrayList<String> investmentOptPerList;
	// PPT Info Menu links..

	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	@FindBy(xpath = "//*[@id='oCMenu_323'][contains(text(),'Account Balance')]")
	private WebElement MenuAccBal;

	@FindBy(xpath = "//*[@id='oCMenu_325'][contains(text(),'Money Type Balance')]")
	private WebElement MenuMoneyTypeBal;

	// Money Type Balance..(MTB)
	@FindBy(css = "table[id = 'table_Money Type Balance'] td.pageMenuTitle")
	private WebElement MTBPageTitle;

	@FindBy(css = "td input[name = 'date']")
	private WebElement MTBAsOfDate;

	@FindBy(css = "select[name = 'planNumber']>option")
	private WebElement MTBForPlan;
	// Active Deposite Allocation(ADA)..

	@FindBy(xpath = "//span[contains(text(),'Active Deposit Allocation Information')]")
	private WebElement ADAInfoTitle;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataStringColumnTitle")
	private WebElement ADAInvstOptLabel;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataNumberColumnTitle")
	private WebElement ADAAllocationPer;

	// Total 'ld be 100%..
	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataStringColumn")
	private List<WebElement> ADAllcInvstOpt;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataNumberColumn")
	private List<WebElement> ADAllocationPerVal;

	// Variable Investment(VI)..
	@FindBy(xpath = "//span[contains(text(),'Variable Investments')]")
	private WebElement VITitle;

	@FindBy(css = "table#variableFunds th:nth-of-type(1)")
	private WebElement VIAssetModelInvstOpt;

	@FindBy(css = "table#variableFunds th:nth-of-type(2)")
	private WebElement VIMoneyType;

	@FindBy(css = "table#variableFunds th:nth-of-type(3)")
	private WebElement VIValueEffDate;

	@FindBy(css = "table#variableFunds th:nth-of-type(4)")
	private WebElement VIUnitShareVal;

	@FindBy(css = "table#variableFunds th:nth-of-type(5)")
	private WebElement VIUnitShareBal;

	@FindBy(css = "table#variableFunds th:nth-of-type(6)")
	private WebElement VIVestingPercentage;

	@FindBy(css = "table#variableFunds th:nth-of-type(7)")
	private WebElement VIVestedBalLabel;

	@FindBy(css = "table#variableFunds th:nth-of-type(8)")
	private WebElement VINonVestedBalLabel;

	@FindBy(css = "table#variableFunds th:nth-of-type(9)")
	private WebElement VICurrentBalLabel;
	// VI values..
	@FindBy(css = "table#variableFunds tr:nth-of-type(1) td:nth-of-type(1)[class ^= 'data']>a")
	private WebElement VIInvstOptVal;

	@FindBy(css = "table#variableFunds tr:nth-of-type(1) td:nth-of-type(2)[class ^= 'data']>a")
	private WebElement VIMoneyTypeVal;

	@FindBy(css = "table#variableFunds tr:nth-of-type(1) td:nth-of-type(7)[class ^= 'data']")
	private WebElement VIVestedBal;

	@FindBy(css = "table#variableFunds tr:nth-of-type(1) td:nth-of-type(8)[class ^= 'data']")
	private WebElement VINonVestedBal;

	@FindBy(css = "table#variableFunds tr:nth-of-type(1) td:nth-of-type(9)[class ^= 'data']")
	private WebElement VICurrentBal;

	// Fixed Investment(FI)..
	@FindBy(xpath = "//span[contains(text(),'Fixed Investments')]")
	private WebElement FITitle;

	// Investment Total(IT)..
	@FindBy(xpath = "//span[contains(text(),'Investment Totals')]")
	private WebElement ITTitle;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(3)")
	private WebElement ITVestedBalLable;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(4)")
	private WebElement ITNonVestedBalLable;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(5)")
	private WebElement ITCurrentBalLable;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(3)")
	private WebElement ITVestedBalVal;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(4)")
	private WebElement ITNonVestedBalVal;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(5)")
	private WebElement ITCurrentBalVal;

	// Secure Foundation Benefits Base Details..

	@FindBy(xpath = "//span[contains(text(),'SecureFoundation Benefit Base Details')]")
	private WebElement SecureFndtnBnftBsDtlsTitle;

	// Money Type Total(MTT)..

	@FindBy(xpath = "//span[contains(text(),'Money Type Totals')]")
	private WebElement MoneyTypeTotalTitle;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(1)")
	private WebElement MTTMoneyType;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(2)")
	private WebElement MTTCurrentBal;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(3)")
	private WebElement MTTLoanPrinciapl;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(4)")
	private WebElement MTTPriorWithdrawls;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(5)")
	private WebElement MTTVestingPer;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(6)")
	private WebElement MTTVestedBal;

	LoadableComponent<?> parent;

	public MoneyTypeBalance() {

		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(MTBPageTitle));
	}

	@Override
	protected void load() {
		Web.mouseHover(MenuPPTInfo);
		if (Web.isWebElementDisplayed(MenuAccBal, true)) {
			Web.mouseHover(MenuAccBal);
			if (Web.isWebElementDisplayed(MenuMoneyTypeBal, true)) {
				Web.clickOnElement(MenuMoneyTypeBal);
				if (Web.isWebElementDisplayed(MTBPageTitle, true)) {
					Reporter.logEvent(
							Status.PASS,
							"Check if Money Type Balance page displayed or not",
							"Money Type Balance page displyed successfully",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if Money Type Balance page displayed or not",
							"Money Type Balance page didn't disply successfully",
							true);
				}
			}

		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if Money Type Balance Link on Participant Info tab displayed or not",
					"Money Type Balance Link on Participant Info tab didn't display successfully",
					true);
		}
	}

	/**
	 * <pre>
	 * Method to verify Investment Options percentage on Money Type Balance
	 * </pre>
	 * 
	 * @param pptID
	 * @author rnjbdn
	 * @throws Exception
	 */
	public void verify_Investment_Option_Percentage(String ppt_id)
			throws Exception {
		if (Web.isWebElementDisplayed(ADAInvstOptLabel, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify the Active Deposite Allocation Information.",
					"Active Deposite Allocation Information is displayed.",
					true);
			legal_Name_List = get_Active_Legal_Name(
					Stock.getTestQuery("getActiveLegalNameList"), ppt_id);
			if (legal_Name_List.size() <= 0) {
				throw new AssertionError(
						"Plan does not have any active Investment Options.");
			}

			for (int i = 0; i < legal_Name_List.size(); i++) {
				
				if (legal_Name_List.size() == ADAllcInvstOpt.size()
						&& legal_Name_List.contains(
										ADAllcInvstOpt.get(i).getText().trim())) {
					investmentOptPerList = get_Asset_Allocation_Percentage(
							Stock.getTestQuery("getInvestmentOptPer"), ppt_id,
							ADAllcInvstOpt.get(i).getText());
					if (investmentOptPerList.size() <= 0) {
						throw new AssertionError(
								"Investment Options does not provide any percentage");
					}
					if (ADAllcInvstOpt.get(i).getText().trim()
							.contains(investmentOptPerList.get(0).trim())
							&& ADAllocationPerVal.get(i).getText().contains(
											investmentOptPerList.get(1).trim())) {
						Reporter.logEvent(
								Status.PASS,
								"For Participant "
										+ ppt_id
										+ " : validate the Investment option percentage for "
										+ investmentOptPerList.get(0) + " :"
										+ investmentOptPerList.get(1),
								"For Participant "
										+ ppt_id
										+ " : Investment option percentage for "
										+ ADAllcInvstOpt.get(i).getText()
										+ "  :"
										+ ADAllocationPerVal.get(i).getText(),
								false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"For Participant "
										+ ppt_id
										+ " : validate the Investment option percentage for "
										+ investmentOptPerList.get(0) + " :"
										+ investmentOptPerList.get(1),
								"For Participant "
										+ ppt_id
										+ " : Investment option percentage for "
										+ ADAllcInvstOpt.get(i).getText()
										+ "  :"
										+ ADAllocationPerVal.get(i).getText(),
								true);
					}
				}else{
					Reporter.logEvent(Status.FAIL,
							"Verify Invetment Options against DB",
							"Invetment Optionsis not available in DB",
							false);
					break ;
				}
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify the Active Deposite Allocation Information.",
					"Active Deposite Allocation Information is not displayed.",
					false);
		}
	}

	/**
	 * <pre>
	 * Method to get all the active Legal name from DB
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> get_Active_Legal_Name(
			String[] getActiveLegalNameList, String ind_id) throws Exception {
		ResultSet resultset;
		legal_Name_List = new ArrayList<String>();
		resultset = DB.executeQuery(getActiveLegalNameList[0],
				getActiveLegalNameList[1], ind_id, ind_id);
		if (resultset != null) {
			while (resultset.next()) {
				String legalName = resultset.getString("legal_name");
				legal_Name_List.add(legalName);
			}
		}
		return legal_Name_List;
	}

	/**
	 * <pre>
	 * Method to get all the active Legal name percentage from DB
	 * </pre>
	 * 
	 * @param
	 * @throws Exception
	 */
	public ArrayList<String> get_Asset_Allocation_Percentage(
			String[] getInvestmentOptPer, String ind_id, String legalName)
			throws Exception {
		ResultSet resultset;
		investmentOptPerList = new ArrayList<String>();
		resultset = DB.executeQuery(getInvestmentOptPer[0],
				getInvestmentOptPer[1], ind_id, legalName);
		if (resultset != null) {
			while (resultset.next()) {
				String legalNameVar = resultset.getString("legal_name");
				String allocPer = resultset.getString("percent");
				investmentOptPerList.add(legalNameVar);
				investmentOptPerList.add(allocPer);
			}
		}
		return investmentOptPerList;
	}
	
	/**
	 * <pre>
	 * Method to verify Investment Options percentage on Money Type Balance
	 * </pre>
	 * 
	 * @param pptID
	 * @author rnjbdn
	 * @throws Exception
	 */
	public void verify_VI_MoneyType_Link(String ppt_id)
			throws Exception {
		if (Web.isWebElementDisplayed(VITitle, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Variable Investment section.",
					"Verify Variable Investment section is displayed.",
					true);
			
		} else {
			Reporter.logEvent(Status.PASS,
					"Verify Variable Investment section.",
					"Verify Variable Investment section is displayed.",
					true);
		}
	}
	
	/**
	 * <pre>
	 * Method to get all the active Legal name from DB
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> get_Money_Type(
			String[] getActiveLegalNameList, String ind_id) throws Exception {
		ResultSet resultset;
		legal_Name_List = new ArrayList<String>();
		resultset = DB.executeQuery(getActiveLegalNameList[0],
				getActiveLegalNameList[1], ind_id, ind_id);
		if (resultset != null) {
			while (resultset.next()) {
				String legalName = resultset.getString("legal_name");
				legal_Name_List.add(legalName);
			}
		}
		return legal_Name_List;
	}
	
	/**
	 * <pre>
	 * Method to get all the active Legal name from DB
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> get_Money_Type_Info(
			String[] getActiveLegalNameList, String ind_id) throws Exception {
		ResultSet resultset;
		legal_Name_List = new ArrayList<String>();
		resultset = DB.executeQuery(getActiveLegalNameList[0],
				getActiveLegalNameList[1], ind_id, ind_id);
		if (resultset != null) {
			while (resultset.next()) {
				String legalName = resultset.getString("legal_name");
				legal_Name_List.add(legalName);
			}
		}
		return legal_Name_List;
	}
}
