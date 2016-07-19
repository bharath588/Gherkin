package pageobjects;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import framework.util.CommonLib;

public class ContributionAllocationChange extends
		LoadableComponent<ContributionAllocationChange> {

	@FindBy(xpath = ".//*[@id='table_Contribution Allocation Change Select Contribution Source Category']/tbody/tr/td")
	private WebElement ContrAllChngSrcPageTitle;

	@FindBy(css = "input[value='Select Contribution Source Category']")
	private WebElement SeelctContrSrcCat_Btn;

	//Select own allocation / select Asset Models..	
	@FindBy(xpath = "//td[contains(text(),'Select Own Allocations')]/input[@type = 'radio']")
	private WebElement SelectOwnAllo_Btn;

	//Select own allocation / select Asset Models..	
	@FindBy(xpath = "//td[contains(text(),'Select Asset Allocation Models')]/input[@type = 'radio']")
	private WebElement SelectAsstAlloc_Btn;

	@FindBy(css = "input[value = 'Begin Allocation Change'][type = 'submit']")
	private WebElement BegingAllocChng_Btn;

	@FindBy(xpath = ".//*[@id='table_Contribution Allocation Change Select Fund Percents']/tbody/tr/td[1]")
	private WebElement ContrAllChngPageTitle;

	@FindBy(xpath = "//td[@class = 'pageMenuTitle'][contains(text(),'Contribution Allocation Change Select Asset Allocation Model')]")
	private WebElement SelectAsstModelPageTitle;

	
	// Default Investment option..
	@FindBy(xpath = "//span[@class = 'titleShadow'][contains(text(),'Current Participant Default Investment Option')]")
	private WebElement DefaultInvstOptsSection;

	@FindBy(xpath = "//span[contains(text(),'Default Investment Option')]/../../following-sibling::div[@class = 'hidden']")
	private WebElement DIOSectionHidden;

	@FindBy(css = "form[name = 'allocChgForm'] table:nth-of-type(1) img[src *= 'restore']")
	private WebElement DIOExpandIcon;

	@FindBy(css = "table#table_activeDefaultFunds thead th:nth-child(1)")
	private WebElement DI_RuleCriteria;

	@FindBy(css = "table#table_activeDefaultFunds thead th:nth-child(2)")
	private WebElement DI_InvestmentOptions;

	@FindBy(css = "table#table_activeDefaultFunds thead th:nth-child(3)")
	private WebElement DI_Percentage;

	@FindBy(xpath = "//table[@id = 'table_activeDefaultFunds']//th[contains(text(),'Rule')]/../..//following-sibling::tbody//td[1]")
	private WebElement DI_RuleCriteria_Val;

	@FindBy(xpath = "//table[@id = 'table_activeDefaultFunds']//th[contains(text(),'Investment')]/../..//following-sibling::tbody//td[2]")
	private WebElement DI_InvstOpts_Val;

	@FindBy(xpath = "//table[@id = 'table_activeDefaultFunds']//th[contains(text(),'Percentage')]/../..//following-sibling::tbody//td[3]")
	private WebElement DI_Percentage_Val;

	//WebElements for Current Deposite Allocation(CDA)..
	@FindBy(xpath = "(//span[@class = 'titleShadow'][contains(text(),'Current Deposit Allocation')])[1]")
	private WebElement CurrDepositeAllocSection;
	
	@FindBy(xpath = "//span[contains(text(),'Current Deposit Allocation')]/../../following-sibling::div[@class = 'hidden']")
	private WebElement CDAHiddenState;

	@FindBy(css = "form[name = 'allocChgForm'] table:nth-of-type(2) img[src *= 'restore']")
	private List<WebElement> CDAExpandIcon;

	@FindBy(css = "table#partAllocList th:nth-child(1)")
	private List<WebElement> CDA_InvestmentOptions;

	@FindBy(css = "table#partAllocList th:nth-child(2)")
	private List<WebElement> CDA_InvestmentCode;

	@FindBy(css = "table#partAllocList th:nth-child(3)")
	private List<WebElement> CDA_DepositType;

	@FindBy(css = "table#partAllocList th:nth-child(4)")
	private List<WebElement> CDA_EffctiveDate;

	@FindBy(css = "table#partAllocList th:nth-child(5)")
	private List<WebElement> CDA_InvestmentPer;

	@FindBy(xpath = "//table[@id = 'partAllocList']//th[contains(text(),'Option')]/../..//following-sibling::tbody//td[1]")
	private List<WebElement> CDA_InvestmentOptions_Val;

	@FindBy(xpath = "//table[@id = 'partAllocList']//th[contains(text(),'Code')]/../..//following-sibling::tbody//td[2]")
	private List<WebElement> CDA_InvestmentCode_Val;

	@FindBy(xpath = "//table[@id = 'partAllocList']//th[contains(text(),'Type')]/../..//following-sibling::tbody//td[3]")
	private List<WebElement> CDA_DepositType_Val;

	@FindBy(xpath = "//table[@id = 'partAllocList']//th[contains(text(),'Date')]/../..//following-sibling::tbody//td[4]")
	private List<WebElement> CDA_EffctiveDate_Val;

	@FindBy(xpath = "//table[@id = 'partAllocList']//th[contains(text(),'Investment')]/../..//following-sibling::tbody//td[5]")
	private List<WebElement> CDA_InvestmentPer_Val;

	//WebElement for Redistribute Allocation Percentages Below..
	@FindBy(xpath = "(//span[@class = 'titleShadow'][contains(text(),'Redistribute Allocation Percentages Below for Non-Roth')])[1]")
	private WebElement RedistributeAloocPerSec;
	
	@FindBy(xpath = "//span[contains(text(),'Redistribute Allocation')]/../../following-sibling::div[@class = 'hidden']")
	private WebElement RAPHiddenState;

	@FindBy(css = "form[name = 'allocChgForm'] table:nth-of-type(3) img[src *= 'restore']")
	private List<WebElement> RAPExpandIcon;

	@FindBy(xpath = "//table[@class = 'dataTable']//td[contains(text(),'Investment')]/../following-sibling::tr/td[1]/a")
	private List<WebElement> RAP_InvestmentOpts_Left;

	@FindBy(xpath = "//table[@class = 'dataTable']//td[contains(text(),'Percent')]/../following-sibling::tr/td[2]/input")
	private List<WebElement> RAP_InvestmentPer_Left;

	@FindBy(xpath = "//table[@class = 'dataTable']//td[contains(text(),'Investment')]/../following-sibling::tr/td[1]/a")
	private List<WebElement> RAP_InvestmentOpts_Right;

	@FindBy(xpath = "//table[@class = 'dataTable']//td[contains(text(),'Percent')]/../following-sibling::tr/td[4]/input")
	private List<WebElement> RAP_InvestmentPer_Right;

	@FindBy(id = "percentTotal")
	private WebElement RAP_PercentageTotal;

	@FindBy(css = "select[name = 'formDepositType']>option[value = 'REG']")
	private WebElement RAP_DepositType;

	@CacheLookup
	@FindBy(css = "input[value = 'Confirm Allocations']")
	private WebElement RAP_ConfirmAlloc_Btn;

	@CacheLookup
	@FindBy(css = "input[value = 'Cancel']")
	private WebElement RAP_Cancel_Btn;
	
	//Contribution Allocation change approval page..
	@FindBy(css = "input[value = 'Submit']")
	private WebElement CnfmAlloc_SubmitBtn;
	
	@FindBy(xpath = "//table[@class = 'dataTable']//td[contains(text(),'Investment')]/../following-sibling::tr/td[3]")
	private WebElement CnfmAlloc_Percentage;
	
	//Confirmation page..
	@FindBy(xpath = ".//*[@id='table_Contribution Allocation Change Confirmation']/tbody/tr/td")
	private WebElement ConfirmationPageTitle;
	
	@FindBy(css = "table tr:nth-child(1)>td.dataConfirmationNumber")
	private WebElement ConfirmationNumber;
	
	@FindBy(css = "table tr:nth-child(2)>td.dataConfirmationNumber")
	private WebElement CnfmEffDate;

	@FindBy(css = ".bradsDataTable>tbody>tr:nth-child(1)>td>strong")
	private WebElement ConfrmMsg;

	//Rebalancer check box
	@FindBy(css = "input.radioInput[value = '1']")
	private WebElement AssetModel_RadioBtn;

	@FindBy(xpath = "//a[contains(text(),'Aggressive (Passive Model)')]")
	private WebElement AggressiveLink;
	
	@FindBy(xpath = "//a[contains(text(),'Aggressive (Passive Model)')]//tr[@class = 'even']//td[@class = 'dataStringColumn']")
	private WebElement Selected_InvstOptns;
	
	@FindBy(xpath = "//a[contains(text(),'Aggressive (Passive Model)')]//tr[@class = 'even']//td[@class = 'dataNumberColumn']")
	private WebElement Selected_Percentage;
	
	@FindBy(css = "input[name = 'processRebalancer']")
	private WebElement Rebalancer_ChckBx;

	@FindBy(css = "input[name = 'checkBoxAdvisePPT']")
	private WebElement AdvisedPPts_ChckBx;

	@FindBy(css = "input[value = 'Submit Model Selection']")
	private WebElement ModelSel_SubmitBtn;
	
	
	LoadableComponent<?> parent;

	public ContributionAllocationChange() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(ContrAllChngPageTitle)
				|| Web.isWebElementDisplayed(SelectAsstModelPageTitle));
	}

	@Override
	protected void load() {
		this.parent = new ParticipantHome().get();
		new ParticipantHome().navigateToCACPage();
		if (Web.isWebElementDisplayed(SeelctContrSrcCat_Btn)
				&& Web.isWebElementDisplayed(ContrAllChngSrcPageTitle)) {
			Web.clickOnElement(SeelctContrSrcCat_Btn);
			if(Web.isWebElementDisplayed(SelectOwnAllo_Btn)
					&& Web.isWebElementDisplayed(BegingAllocChng_Btn)){
				Web.clickOnElement(SelectAsstAlloc_Btn) ;
				Web.clickOnElement(BegingAllocChng_Btn) ;
			}
		} else if(Web.isWebElementDisplayed(SelectOwnAllo_Btn)
				&& Web.isWebElementDisplayed(BegingAllocChng_Btn)){
			Web.clickOnElement(SelectOwnAllo_Btn) ;
			Web.clickOnElement(BegingAllocChng_Btn) ;
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
	public ArrayList<String> getContributionAllChangeDtls_DB(String testType) throws Exception {
		ArrayList<String> contributionAllocChng = new ArrayList<String>();
		ResultSet resultset = null;
		if (testType.equalsIgnoreCase("RebalancerCheckbox")) {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getGrpDefaultDtlsWithChkbx")[0],
					Stock.getTestQuery("getGrpDefaultDtlsWithChkbx")[1]);
		} else {
			resultset = DB.executeQuery(
					Stock.getTestQuery("getDefaultInvsOptsDtls")[0],
					Stock.getTestQuery("getDefaultInvsOptsDtls")[1]);
		}	
		if (resultset != null) {
			while (resultset.next()) {
				contributionAllocChng.add(resultset.getString("IND_ID"));
				contributionAllocChng.add(resultset.getString("GA_ID"));
				contributionAllocChng.add(resultset.getString("RULE_CRITERIA"));
				contributionAllocChng.add(resultset.getString("PERCENT"));
			}
		}
		return contributionAllocChng;
	}

	/**
	 * <pre>
	 * Method to validate Default investemnet options details under Contribution Allocation Change
	 * </pre>
	 * 
	 * @param contrAllChange_List
	 */
	public void validate_DefaultInvstOpts(ArrayList<String> contrAllChange_List) {
		if (Web.isWebElementDisplayed(DIOSectionHidden)) {
			Web.clickOnElement(DIOExpandIcon);
		}
		if (Web.isWebElementDisplayed(DI_RuleCriteria)
				&& Web.isWebElementDisplayed(DI_Percentage)
				&& Web.isWebElementDisplayed(DI_InvestmentOptions)) {
			if (DI_RuleCriteria_Val.getText().trim()
					.equalsIgnoreCase(contrAllChange_List.get(2))
					&& DI_Percentage_Val.getText().trim()
							.contains(contrAllChange_List.get(3))) {
				Reporter.logEvent(
						Status.PASS,
						"Validate the Default Investment Option is getting display correctly with Rule Criteria "
								+ contrAllChange_List.get(2)
								+ ", Investment Option and Percentage "
								+ contrAllChange_List.get(3)
								+ "\n under Current Participant Default Investment Option section",
						"Default Investment Option is getting displayed correctly with\n Rule Criteria: "
								+ DI_RuleCriteria_Val.getText()
								+ ", \n Investment Option and \n Percentage: "
								+ DI_Percentage_Val.getText()
								+ "\n under Current Participant Default Investment Option section",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Validate the Default Investment Option is getting display correctly with Rule Criteria "
								+ contrAllChange_List.get(2)
								+ ", Investment Option and Percentage "
								+ contrAllChange_List.get(3)
								+ " under Current Participant Default Investment Option section",
						"Default Investment Option is not getting displayed correctly with \n Rule Criteria: "
								+ DI_RuleCriteria_Val.getText()
								+ ", \n Investment Option and \n Percentage: "
								+ DI_Percentage_Val.getText()
								+ " under Current Participant Default Investment Option section",
						true);
			}
		}
	}

	/**
	 * <pre>
	 * Method to get current deposite allocation details from DB
	 * </pre>
	 * 
	 * @return DisbursementHistoryDetails List
	 * @author rnjbdn
	 */
	public ArrayList<String> getCurrDepositeAllocDtls_DB() throws Exception {
		ArrayList<String> currDepositeAllocList = new ArrayList<String>();
		ResultSet resultset = null;
		resultset = DB.executeQuery(
				Stock.getTestQuery("getCurrentDepositeAllDtls")[0],
				Stock.getTestQuery("getCurrentDepositeAllDtls")[1]);
		if (resultset != null) {
			while (resultset.next()) {
				currDepositeAllocList.add(resultset.getString("IND_ID"));
				currDepositeAllocList.add(resultset.getString("GA_ID"));
				currDepositeAllocList.add(resultset.getString("LEGAL_NAME"));
				currDepositeAllocList.add(resultset.getString("SDIO_ID"));
				currDepositeAllocList.add(resultset.getString("SDDETY_CODE"));
				currDepositeAllocList.add(resultset.getString("EFFDATE"));
				currDepositeAllocList.add(resultset.getString("PERCENT"));
			}
		}
		return currDepositeAllocList;
	}

	/**
	 * <pre>
	 * Method to validate Default investment options details under Contribution Allocation Change
	 * </pre>
	 * 
	 * @param contrAllChange_List
	 * @throws ParseException 
	 */
	public void validate_CurrDepositeAlloc_Dtls(ArrayList<String> currDepositeAllocList) throws ParseException {
		if (Web.isWebElementDisplayed(CDAHiddenState)) {
			Web.clickOnElement(CDAExpandIcon.get(0));
		}
		if (CDA_InvestmentOptions.size() <= 0) {
			throw new AssertionError("Did not display any deposite allocation information.") ;
		}
		if (Web.isWebElementDisplayed(CDA_InvestmentOptions.get(0))
				&& Web.isWebElementDisplayed(CDA_InvestmentCode.get(0))
				&& Web.isWebElementDisplayed(CDA_DepositType.get(0))
				&& Web.isWebElementDisplayed(CDA_EffctiveDate.get(0))
				&& Web.isWebElementDisplayed(CDA_InvestmentPer.get(0))) {
			if (CDA_InvestmentOptions_Val.size() <= 0) {
				throw new AssertionError("Did not display any deposite allocation information.") ;
			}
			if (CDA_InvestmentOptions_Val.get(0).getText().trim()
					.equalsIgnoreCase(currDepositeAllocList.get(2))
					&& CDA_InvestmentCode_Val.get(0).getText().trim()
							.equalsIgnoreCase(currDepositeAllocList.get(3))
							&& CDA_DepositType_Val.get(0).getText().trim()
							.equalsIgnoreCase(currDepositeAllocList.get(4))
							&& CommonLib.compareDB_Date_With_Web_Date(currDepositeAllocList.get(5),CDA_EffctiveDate_Val.get(0).getText().trim())
							&& CDA_InvestmentPer_Val.get(0).getText().trim()
							.contains(currDepositeAllocList.get(6))) {
				Reporter.logEvent(
						Status.PASS,
						"Validate the Current Deposit is getting display correctly or not.",
						"Current Deposit Allocation is getting displayed correctly with following details "
						+ "\n"
						+ "<b>Expected Details:"
						+ "\n"
						+ "Investment Option: "+currDepositeAllocList.get(2)
						+ "\n"
						+ "Investment Code: "+currDepositeAllocList.get(3)
						+ "\n"
						+ "Deposit Type: "+currDepositeAllocList.get(4)
						+ "\n"
						+ "Effective Date: "+currDepositeAllocList.get(5)
						+ "\n"
						+ "Investment Percentage: "+currDepositeAllocList.get(6)
						+"\n"
						+ "<b>Actual Details:"
						+ "\n"
						+ "Investment Option: "+CDA_InvestmentOptions_Val.get(0).getText().trim()
						+ "\n"
						+ "Investment Code: "+CDA_InvestmentCode_Val.get(0).getText().trim()
						+ "\n"
						+ "Deposit Type: "+CDA_DepositType_Val.get(0).getText().trim()
						+ "\n"
						+ "Effective Date: "+CDA_EffctiveDate_Val.get(0).getText().trim()
						+ "\n"
						+ "Investment Percentage: "+CDA_InvestmentPer_Val.get(0).getText().trim(),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Validate the Current Deposit is getting display correctly or not.",
						"Current Deposit Allocation is not getting displayed correctly with following details "
						+ "\n"
						+ "<b>Expected Details:"
						+ "\n"
						+ "Investment Option: "+currDepositeAllocList.get(2)
						+ "\n"
						+ "Investment Code: "+currDepositeAllocList.get(3)
						+ "\n"
						+ "Deposit Type: "+currDepositeAllocList.get(4)
						+ "\n"
						+ "Effective Date: "+currDepositeAllocList.get(5)
						+ "\n"
						+ "Investment Percentage: "+currDepositeAllocList.get(6)
						+"\n"
						+ "<b>Actual Details:"
						+ "\n"
						+ "Investment Option: "+CDA_InvestmentOptions_Val.get(0).getText().trim()
						+ "\n"
						+ "Investment Code: "+CDA_InvestmentCode_Val.get(0).getText().trim()
						+ "\n"
						+ "Deposit Type: "+CDA_DepositType_Val.get(0).getText().trim()
						+ "\n"
						+ "Effective Date: "+CDA_EffctiveDate_Val.get(0).getText().trim()
						+ "\n"
						+ "Investment Percentage: "+CDA_InvestmentPer_Val.get(0).getText().trim(),
						true);
			}
		}
	}
	
	/**
	 * <pre>Method to transfer fund percentage</pre>
	 * @param currDepositeAllocList
	 * @throws ParseException 
	 */
	public void transferInvestmentPercentage(ArrayList<String> currDepositeAllocList) throws ParseException{
		if (RAP_InvestmentOpts_Left.size() <= 0
				&& RAP_InvestmentOpts_Right.size() <= 0) {
			throw new AssertionError("Didn't display any Investment options.") ;
		}
		for (int i = 0; i < RAP_InvestmentOpts_Left.size(); i++) {
			if (RAP_InvestmentOpts_Left.get(i).getText().trim().equalsIgnoreCase(currDepositeAllocList.get(2))
					&& Web.isWebElementDisplayed(RAP_InvestmentPer_Left.get(i))) {
				String invstPer = Web.setTextToTextBox(RAP_InvestmentPer_Left.get(i), Stock.GetParameterValue("InvestmentPercentage")) ;
				String invstOptionSelected = RAP_InvestmentOpts_Left.get(i).getText().trim();
				Web.clickOnElement(RAP_PercentageTotal) ;
				if (Web.isWebElementDisplayed(RAP_ConfirmAlloc_Btn)) {
					Reporter.logEvent(
							Status.PASS,
							"Select Investement option to rebalance into section and enter percentage.",
							invstPer+" % is selected for the \n Investment options: "+RAP_InvestmentOpts_Left.get(i).getText()+" \n to rebalance into section.",
							false);
					clickOnConfirmAlloc_Btn();
					confirm_Allocation_Changes(invstOptionSelected,invstPer) ;
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Select Investement option to rebalance into section and enter percentage.",
							invstPer+" % is selected for the \n Investment options: "+RAP_InvestmentOpts_Left.get(i).getText()+" \n to rebalance into section.",
							false);
				}
				break ;
			} 
		}
	}
	
	/**
	 * <pre>Method to click on Confirm Allocation button</pre>
	 */
	public void clickOnConfirmAlloc_Btn(){
		if (Web.clickOnElement(RAP_ConfirmAlloc_Btn)) {
			Reporter.logEvent(
					Status.PASS,
					"Click on Confirm Allocations button",
					"Clicked on Confirm Allocations button",
					false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Click on Confirm Allocations button",
					"Didn't click on Confirm Allocations button",
					false);
		}
	}
	
	/**
	 * <pre>Method to Validate confirmation page..</pre>
	 * @param invstOptSelected
	 * @param invstPer
	 * @throws ParseException
	 */
	public void confirm_Allocation_Changes(String invstOptSelected,String invstPer) throws ParseException{
		if (RAP_InvestmentOpts_Left.get(0).getText().equalsIgnoreCase(invstOptSelected)
				&& CnfmAlloc_Percentage.getText().contains(invstPer)) {
			Reporter.logEvent(
					Status.PASS,
					"Validate Investment options selected.",
					"Investment options selected is displayed as expected.",
					false);
			Web.clickOnElement(CnfmAlloc_SubmitBtn) ;
			//get current date time with Date()
			Date sysDate = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (Web.isWebElementDisplayed(ConfirmationPageTitle,true)
				//	&& CommonLib.compareDB_Date_With_Web_Date(dateFormat.format(sysDate).toString(), CnfmEffDate.getText())
					&& ConfirmationNumber.getText().matches("[0-9]+")
					&& ConfrmMsg.getText().contains(Stock.GetParameterValue("confmMsg"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify the confirmation page.",
						"Confirmation page displayed with below information\n"
						+ "Confirmation message: "+ConfrmMsg.getText()
						+"Allocation Confirmation Number: "+ConfirmationNumber.getText()
						+"Effdate: "+CnfmEffDate.getText(),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify the confirmation page.",
						"Confirmation page displayed with below information\n"
						+ "Confirmation message: "+ConfrmMsg.getText()
						+"Allocation Confirmation Number: "+ConfirmationNumber.getText()
						+"Effdate: "+CnfmEffDate.getText(),
						true);
			}
			
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Validate Investment options selected.",
					"Investment options selected is not displayed as expected.",
					false);
		}
	}
	
	/**
	 * <pre>Method to click on Rebalancer check box</pre>
	 * @throws ParseException 
	 */
	public void select_RebalancerChkBoxs() throws ParseException{
		boolean isBtnSelected = false ;
		if (Web.isWebElementDisplayed(AssetModel_RadioBtn)) {
			if (Web.clickOnElement(AssetModel_RadioBtn)) {
				isBtnSelected = true ;
			} else {
				isBtnSelected = false ;
			} 
		} 
		if (Web.isWebElementDisplayed(AdvisedPPts_ChckBx)) {
			if(Web.clickOnElement(AdvisedPPts_ChckBx)) {
				isBtnSelected = true ;
			} else {
				isBtnSelected = false ;
			} 
		}
		if (Web.isWebElementDisplayed(Rebalancer_ChckBx)) {
			if(Web.clickOnElement(Rebalancer_ChckBx)) {
				isBtnSelected = true ;
			} else {
				isBtnSelected = false ;
			} 
		}
		Web.mouseHover(AggressiveLink);
		String invtOptns = Selected_InvstOptns.getText().trim() ;
		String invstPer =  Selected_Percentage.getText().trim();
		if (isBtnSelected) {
			Web.clickOnElement(ModelSel_SubmitBtn) ;
			Reporter.logEvent(
					Status.PASS,
					"Check and Click on Rebalancer radio button and check box.",
					"Clicked on Rebalancer radio button and check box.",
					true);
			confirm_Allocation_Changes(invtOptns, invstPer);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check and Click on Rebalancer radio button and check box.",
					"Didn't click on Rebalancer radio button and check box.",
					true);
		}
	}
}
