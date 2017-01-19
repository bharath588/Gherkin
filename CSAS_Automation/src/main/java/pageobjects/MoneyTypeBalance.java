package pageobjects;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import framework.util.CommonLib;

public class MoneyTypeBalance extends LoadableComponent<MoneyTypeBalance> {

	ArrayList<String> legal_Name_List;
	ArrayList<String> investmentOptPerList;
	ArrayList<String> money_Type_List;
	ArrayList<String> money_Type_Info_List;

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

	@FindBy(xpath = "//a[contains(@id,'variableFund')]")
	private List<WebElement> VIInvstOptValList;

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

	// FI values..

	@FindBy(xpath = "//a[contains(@id,'fixedFund')]")
	private List<WebElement> FIInvstOptValList;

	@FindBy(css = "table#fixedFunds tr:nth-of-type(1) td:nth-of-type(7)[class ^= 'data']")
	private WebElement FIVestedBal;

	@FindBy(css = "table#fixedFunds tr:nth-of-type(1) td:nth-of-type(8)[class ^= 'data']")
	private WebElement FINonVestedBal;

	@FindBy(css = "table#fixedFunds tr:nth-of-type(1) td:nth-of-type(9)[class ^= 'data']")
	private WebElement FICurrentBal;

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
	
	//Money Type Totals values..
	
	@FindBy(css = "table#acctBalMntySummary tbody>tr>td:nth-of-type(1)")
	private List<WebElement> MTTMoneyTypeVal;
	
	@FindBy(css = "table#acctBalMntySummary tbody>tr:nth-of-type(1)>td:nth-of-type(2)")
	private WebElement MTTCurBalVal;
	
	@FindBy(css = "table#acctBalMntySummary tbody>tr:nth-of-type(1)>td:nth-of-type(6)")
	private WebElement MTTVestedcBalVal;
	
	LoadableComponent<?> parent;

	public MoneyTypeBalance() {

		PageFactory.initElements(Web.getDriver(), this);
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
	public void verify_Investment_Option_Percentage(String ppt_id,
			String plan_No) throws Exception {
		if (Web.isWebElementDisplayed(ADAInvstOptLabel, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify the Active Deposite Allocation Information.",
					"Active Deposite Allocation Information is displayed.",
					true);
			legal_Name_List = get_Active_Legal_Name(
					Stock.getTestQuery("getActiveLegalNameList"), ppt_id,
					plan_No);
			if (legal_Name_List.size() <= 0) {
				throw new AssertionError(
						"Plan does not have any active Investment Options.");
			}

			for (int i = 0; i < legal_Name_List.size(); i++) {

				if (legal_Name_List.size() == ADAllcInvstOpt.size()
						&& legal_Name_List.contains(ADAllcInvstOpt.get(i)
								.getText().trim())) {
					investmentOptPerList = get_Asset_Allocation_Percentage(
							Stock.getTestQuery("getInvestmentOptPer"), ppt_id,
							ADAllcInvstOpt.get(i).getText());
					if (investmentOptPerList.size() <= 0) {
						throw new AssertionError(
								"Investment Options does not provide any percentage");
					}
					if (ADAllcInvstOpt.get(i).getText().trim()
							.contains(investmentOptPerList.get(0).trim())
							&& ADAllocationPerVal
									.get(i)
									.getText()
									.contains(
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
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Invetment Options against DB",
							"Invetment Optionsis not available in DB", false);
					break;
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
			String[] getActiveLegalNameList, String ind_id, String ga_id)
			throws Exception {
		ResultSet resultset;
		legal_Name_List = new ArrayList<String>();
		resultset = DB.executeQuery(getActiveLegalNameList[0],
				getActiveLegalNameList[1], ind_id, ga_id);
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
	public void verify_VI_MoneyType_Link(String ppt_id, String ga_id)
			throws Exception {
		if (Web.isWebElementDisplayed(VITitle, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Variable Investment section.",
					"Verify Variable Investment section is displayed.", true);
			money_Type_List = get_Money_Type(
					Stock.getTestQuery("getMoneyType"), ppt_id, ga_id);
			if (money_Type_List.size() <= 0) {
				throw new AssertionError(
						"Variable Investment section does not contains any Money Type.");
			}
			String moneyType = VIMoneyTypeVal.getText().substring(0,
					VIMoneyTypeVal.getText().length() - 1);
			if (money_Type_List.contains(moneyType)) {
				money_Type_Info_List = get_Money_Type_Info(
						Stock.getTestQuery("getMoneyTypeInfo"), ppt_id,
						moneyType);
				Web.mouseHover(VIMoneyTypeVal);
			/*	// String al =
				// Web.getDriver().findElements(By.cssSelector("script[language = 'javascript']:nth-of-type(4)")).get(0).getText();
				JavascriptExecutor js = (JavascriptExecutor) Web.getDriver();
				// Object obj =js.executeScript("variableMTFunc(0)") ;
				Object obj = js
						.executeScript("function variableMTFunc(index){overlib( variableMTArray[index],  HAUTO, VAUTO);}; variableMTFunc(0)");
				// Object val = js.executeScript("return nd();") ;
				// Object val = js.executeScript("return variableMTFunc('0');")
				// ;
				// Object val = js.executeScript(return document.getWe) ;
				System.out.println(obj.toString());
				// String text = VIMoneyTypeVal.getAttribute("Money Type") ;
				if (true) {
					System.out.println("report");
				}*/
				Reporter.logEvent(Status.PASS,
						"Verify Money type balance link.",
						"Money type balance link verified successfully \n MoneyType details on hover : "+money_Type_Info_List, true);
			} else {

			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify Variable Investment section.",
					"Verify Variable Investment section is displayed.", true);
		}
	}

	/**
	 * <pre>
	 * Method to get all the active Legal name from DB
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> get_Money_Type(String[] getMoneyType,
			String ind_id, String ga_id) throws Exception {
		ResultSet resultset;
		money_Type_List = new ArrayList<String>();
		resultset = DB.executeQuery(getMoneyType[0], getMoneyType[1], ind_id,
				ga_id);
		if (resultset != null) {
			while (resultset.next()) {
				String money_Type = resultset.getString("sdmt_code");
				money_Type_List.add(money_Type);
			}
		}
		return money_Type_List;
	}

	/**
	 * <pre>
	 * Method to get all the active Legal name from DB
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> get_Money_Type_Info(String[] getMoneyTypeInfo,
			String ind_id, String code) throws Exception {
		ResultSet resultset;
		money_Type_Info_List = new ArrayList<String>();
		resultset = DB.executeQuery(getMoneyTypeInfo[0], getMoneyTypeInfo[1],
				ind_id, code);
		if (resultset != null) {
			while (resultset.next()) {
				String moneyType = resultset.getString("code");
				String description = resultset.getString("descr");
				String seq_No = resultset.getString("gdmt_seqnbr");
				money_Type_Info_List.add(moneyType);
				money_Type_Info_List.add(description);
				money_Type_Info_List.add(seq_No);
			}
		}
		return money_Type_Info_List;
	}

	/**
	 * <pre>
	 * Method to verify Variable Investment balance
	 * </pre>
	 */
	public void verify_VariableInvestment_Balane(String pptid) {
		if (Web.isWebElementDisplayed(VITitle)) {
			Reporter.logEvent(Status.PASS,
					"Verify Variable Investment section.",
					"Verify Variable Investment section is displayed.", true);
			if (VIInvstOptValList.size() <= 0) {
				throw new AssertionError(
						"Did not display any Investment option for Variable Investment type");
			} else {
				if (CommonLib.isAccountBalance_In_ProperFormat(VIVestedBal
						.getText())
						&& CommonLib
								.isAccountBalance_In_ProperFormat(VINonVestedBal
										.getText())
						&& CommonLib
								.isAccountBalance_In_ProperFormat(VICurrentBal
										.getText())) {
					Reporter.logEvent(
							Status.PASS,
							"For participant : "+pptid+" verify Variable Investment balance for Investment option : "
									+ VIInvstOptValList.get(0).getText(),
							"For participant : "+pptid+" the Investment option or Asset model : "
									+ VIInvstOptValList.get(0).getText()
									+ " under Variable Investment is displaying \n vested balance: "
									+ VIVestedBal.getText()
									+ " \n Non vested balance: "
									+ VINonVestedBal.getText() + " "
									+ " \n Current Balance: "
									+ VICurrentBal.getText()
									+ " in proper format.", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"For participant : "+pptid+" verify Variable Investment balance for Investment option : "
									+ VIInvstOptValList.get(0).getText(),
							"For participant : "+pptid+" the Investment option or Asset model : "
									+ VIInvstOptValList.get(0).getText()
									+ " under Variable Investment is not displaying \n vested balance: "
									+ VIVestedBal.getText()
									+ " \n Non vested balance: "
									+ VINonVestedBal.getText() + " "
									+ " \n Current Balance: "
									+ VICurrentBal.getText()
									+ " in proper format.", true);
				}
			}
		}
	}

	/**
	 * <pre>
	 * Method to verify Fixed Investment balance
	 * </pre>
	 */
	public void verify_FixedInvestment_Balane(String pptid) {
		if (Web.isWebElementDisplayed(FITitle)) {
			Reporter.logEvent(Status.PASS, "Verify Fixed Investment section.",
					"Verify Fixed Investment section is displayed.", true);
			if (FIInvstOptValList.size() <= 0) {
				throw new AssertionError(
						"Did not display any Investment options for Fixed Investment type");
			} else {
				if (CommonLib.isAccountBalance_In_ProperFormat(FIVestedBal
						.getText())
						&& CommonLib
								.isAccountBalance_In_ProperFormat(FINonVestedBal
										.getText())
						&& CommonLib
								.isAccountBalance_In_ProperFormat(FICurrentBal
										.getText())) {
					Reporter.logEvent(
							Status.PASS,
							"For participant : "+pptid+" verify Fixed Investment balance for Investment option : "
									+ FIInvstOptValList.get(0).getText(),
							"For participant : "+pptid+" the Investment option or Asset model : "
									+ FIInvstOptValList.get(0).getText()
									+ " under Fixed Investment is displaying \n vested balance: "
									+ FIVestedBal.getText()
									+ " \n Non vested balance: "
									+ FINonVestedBal.getText() + " "
									+ " \n Current Balance: "
									+ FICurrentBal.getText()
									+ " in proper format.", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"For participant : "+pptid+" verify Fixed Investment balance for Investment option : "
									+ FIInvstOptValList.get(0).getText(),
							"For participant : "+pptid+" the Investment option or Asset model : "
									+ FIInvstOptValList.get(0).getText()
									+ " under Fixed Investment is displaying \n vested balance: "
									+ FINonVestedBal.getText()
									+ " \n Current Balance: "
									+ FICurrentBal.getText()
									+ " in proper format.", true);
				}
			}
		}
	}
	

	/**
	 * <pre>
	 * Method to verify Fixed Investment balance
	 * </pre>
	 */
	public void verify_MoneyTypeTotalsBalance(String pptid) {
		if (Web.isWebElementDisplayed(MoneyTypeTotalTitle)) {
			Reporter.logEvent(Status.PASS, "Verify Money Type totals section.",
					"Money Type Totals section is displayed.", true);
			if (MTTMoneyTypeVal.size() <= 0) {
				throw new AssertionError(
						"Did not display any Money Type for Money Type Totals section.");
			} else {
				if (CommonLib.isAccountBalance_In_ProperFormat(MTTCurBalVal
						.getText())
						&& CommonLib
								.isAccountBalance_In_ProperFormat(MTTVestedcBalVal
										.getText())) {
					Reporter.logEvent(
							Status.PASS,
							"For participant : "+pptid+" verify Money type totals for Money Type : "
									+ MTTMoneyTypeVal.get(0).getText(),
							"For participant : "+pptid+" Money Type : "
									+ MTTMoneyTypeVal.get(0).getText()
									+ " under Money Type Totals is displaying \n Current balance: "
									+ MTTCurBalVal.getText()
									+ " \n Vested Balance: "
									+ MTTVestedcBalVal.getText()
									+ " in proper format.", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"For participant : "+pptid+" verify Money type totals for Money Type : "
									+ MTTMoneyTypeVal.get(0).getText(),
							"For participant : "+pptid+" Money Type : "
									+ MTTMoneyTypeVal.get(0).getText()
									+ " under Money Type Totals is not displaying \n Current balance: "
									+ MTTCurBalVal.getText()
									+ " \n Vested Balance: "
									+ MTTVestedcBalVal.getText()
									+ " in proper format.", true);
				}
			}
		}
	}
}
