package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

public class ManageMyInvestment extends LoadableComponent<ManageMyInvestment> {

	private LoadableComponent<?> parent;
	String investmentFundName1;
	String investmentFundName2;
	String fromInvestmentOption;
	static String toInvestmentOption;
	static String targetYearFund=null;
	String confirmationNo;
	public  Map<String, String> mapInvestmentOptions ;
	public  Map<String, String> mapCurrentInvestmentOptionsPecentage ;

	// @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']")
	// private WebElement lblUserName;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//h1[text()[normalize-space()='My investments' or normalize-space()='My Investments']]")
	private WebElement lblMyInvestments;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//button[text()[normalize-space()='Change My Investments']]")
	private WebElement btnChangeMyInvestment;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Rebalance my current balance']]")
	private WebElement radioRebalanceCurrBal;
	//@FindBy(xpath = ".//strong[text()[normalize-space()='Rebalance my investments']]")
	//private WebElement radioRebalanceCurrBal;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Change how my future contributions will be invested']]")
	private WebElement radioChangeFutureContr;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Change how my current balance is invested']]")
	private WebElement radioChangeCurrentBalInvst;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Dollar-cost average']]")
	private WebElement radioDollarCost;
	@FindBy(xpath = "//button[text()[normalize-space()='Continue']]")
	private WebElement btnContinue1;
	// @FindBy(xpath="//button[text()[normalize-space()='Continue']]") private
	// WebElement btnContinue;
	@FindBy(xpath = ".//input[@value = 'Continue']")
	private WebElement btnCont;
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement btnContinue2;
	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement btnCancel;
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement btnSubmitForRebalancer;
	@FindBy(xpath = "//button[@id='submitTransferbuttonOnVerifyId']")
	private WebElement btnSubmitForF2F;
	@FindBy(xpath = "//input[@value='View Pending Transfer']")
	private WebElement btnViewPendingTransfer;
	@FindBy(id = "perdTrfFreq_once")
	private WebElement radioOnce;
	@FindBy(id = "frequency_M")
	private WebElement radioMonthlyDollarCost;
	@FindBy(id = "perdTrfFreq_quarterly")
	private WebElement radioQuarterly;
	@FindBy(id = "perdTrfFreq_semi_annually")
	private WebElement radioSemiAnnually;
	@FindBy(id = "perdTrfFreq_annually")
	private WebElement radioAnnually;
	@FindBy(id = "perdTrfFreq_today")
	private WebElement radiotoday;
	@FindBy(id = "perdTrfFreq_future")
	private WebElement radioFuture;
	@FindBy(id = "future_effdate_no")
	private WebElement radiotodayDollarCost;
	@FindBy(id = "future_effdate_yes")
	private WebElement radioFutureDollarCost;
	@FindBy(id = "legacyFeatureIframe")
	private WebElement iframeLegacyFeature;
	@FindBy(id = "the_iframe")
	private WebElement theIframe;
	@FindBy(id = "transferPreSubmitButton")
	private List<WebElement> btnReviewTransfer;
	@FindBy(id = "preValidationWarningAId")
	private WebElement btnPreValidationOK;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr//td[2]//input")
	private List<WebElement> lsttxtPercentage;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr//td[1]")
	private List<WebElement> lstInvestmentFundName;
	@FindBy(xpath = "//table[@class='default']")
	private WebElement tblInvestmentOptionsTable;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr")
	private List<WebElement> lstInvestmentOptions;
	@FindBy(xpath = "//span[contains(text(),'Rebalancer frequency')]")
	private WebElement lblRebalancerFrequency;
	@FindBy(xpath = "//span[contains(text(),'setup date')]")
	private WebElement lblSetupDate;
	@FindBy(xpath = "//p//table/tbody/tr[contains(@class,'tableData')]")
	private List<WebElement> lstInvestmentFunds;
	@FindBy(xpath = "//h1[text()[normalize-space()='Change how my current balance is invested']]")
	private WebElement lblCurrentBalanceInvestment;
	@FindBy(xpath = "//div[@id='transferGraphByAssetCurrentDivId']")
	private WebElement currentAssetBalGraph;
	@FindBy(xpath = "//div[@id='transferGraphByAssetCurrentDivId']")
	private WebElement postTransferBalanceGraph;
	@FindBy(xpath = "//div[@class='dollarpercentselector']/a[contains(@class, 'percentbutton')]")
	private WebElement btnPercent;

	@FindBy(id = "transferFundFromTableId")
	private WebElement tblTransferFundFrom;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/thead/tr")
	private WebElement hdrTransferFundFromTable;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/tbody/tr/td/a")
	private WebElement lnkTransferFromInvestmentOption;
	@FindBy(xpath = "//input[contains(@id,'FromPercent')]")
	private List<WebElement> txtTransferFromPercent;
	@FindBy(xpath = "//input[contains(@id,'checkboxFundFrom')]")	private List<WebElement> inpFundFrom;
	@FindBy(id = "fundTransferToTableSelectId")
	private WebElement tblTransferFundTo;
	@FindBy(xpath = "//table[@id='fundTransferToTableSelectId']/thead/tr")
	private WebElement hdrTransferFundToTable;
	@FindBy(xpath = "//table[@id='fundTransferToTableSelectId']/tbody/tr/td[5]")
	private List<WebElement> lnkTransferToInvestmentOption;
	@FindBy(xpath = "//input[contains(@id,'ToPercent')]")
	private List<WebElement> txtTransferToPercent;
	@FindBy(xpath = "//div[text()='View by Asset Class']")
	private WebElement tabViewByAssetClass;
	@FindBy(xpath = "//div[@id='fundingoptions']/a[text()='Asset Class']")
	private WebElement selAssetClass;
	@FindBy(xpath = "//div[@id='fundingtype']/a[text()='Investment Option Family']")
	private WebElement selInvestmentOptFamily;

	@FindBy(xpath = "//tr[@id='verifyTransferFromRowId']")
	private WebElement hdrVerifyTransferFromTable;
	@FindBy(xpath = "//tbody[@id='fundTransferDisplayId']/tr/td[1]")
	private WebElement lblTransferFromOption;
	@FindBy(xpath = "//tbody[@id='fundTransferDisplayId']/tr/td[3]")
	private WebElement lblTransferFromPercent;

	@FindBy(xpath = "//tr[@id='fixedRatesColTitle']")
	private WebElement hdrVerifyTransferToTable;
	@FindBy(xpath = "//table[@id='transactions-validation']//tbody/tr/td[1]")
	private WebElement lblTransferToOption;
	@FindBy(xpath = "//table[@id='transactions-validation']//tbody/tr/td[2]")
	private WebElement lblTransferToPercent;
	@FindBy(xpath = "//*[@id='rebalanceConfirmationHeading']")
	private WebElement txtRebalanceTransactionDetails;
	@FindBy(xpath = "//*[@id='rebalanceConfirmationHeadingWhenSyncAllocationFailed' or @id='effectiveDate']") private WebElement txtTransactionDetails;
	@FindBy(xpath = "//span[contains(@translate-values,'effectiveChangeDate')]//b") private WebElement txtTransactionDate;
	//@FindBy(xpath = "//span[contains(@translate-values,'effectiveChangeDate')]//b") private WebElement txtTransactionDateforRebalSyncFail;
	@FindBy(xpath = "//div[@id='rebalanceConfirmationHeadingWhenSyncAllocationFailed']") private WebElement txtConfirmationWhenRebalneceSyncFailed;
	@FindBy(xpath = "//i[@class='em-checkbox-icon']") private WebElement lblGreenCheck;
	@FindBy(xpath = "//*[@id='confirmationNumber']") private WebElement lblConfirmationNumber;
	@FindBy(xpath = "//*[@id='confirmationNumber']/b")	private WebElement  txtConfirmationNumber;
	@FindBy(xpath = "//p[@class='bold confirmationNumber']")
	private WebElement lblConfirmationNumberF2F;
	@FindBy(xpath = "//span[@class='baseFontFace sectionTitleFontSize sectionTitleFontColor sectionTitleFontWeight']")
	private WebElement lblConfirmationNumberForCancel;
	@FindBy(xpath = "//button[text()[normalize-space()='Back']]")
	private WebElement btnBack;
	@FindBy(xpath = "//input[@class='canceltransferBtn']")
	private WebElement btnCancelTransfer;
	@FindBy(xpath = "//input[@value='Cancel Transfer']")
	private WebElement btnCancelTransferF2F;
	@FindBy(xpath = "//input[@value='Continue to the next step']")
	private WebElement btnContinueToNextStep;
	@FindBy(xpath = "//input[@id='use_termdate_no']")
	private WebElement radioDoNoTerminate;
	@FindBy(xpath = "//input[@id='use_termdate_yes']")
	private WebElement radioTerminate;
	@FindBy(xpath = "//table/tbody/tr[@class='tableSubtitle']")
	private WebElement hdrInvestmentOptionTblForDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td[1]")
	private List<WebElement> lstInvestmentOptionsDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td[2]")
	private List<WebElement> lstToInvestmentOptionsDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td/input")
	private List<WebElement> lstChkInvestmentOptionDollarCost;
	@FindBy(xpath = "//input[contains(@id,'ID')]")
	private WebElement lstInvestmentPercentDollarCost;
	@FindBy(xpath = "//input[@name='DOLLARS']")
	private WebElement txtTransferAmtDollarCost;
	@FindBy(xpath = "//div[contains(@id,'perdTrfFreq')]")
	private WebElement lblDollarCostFreq;
	@FindBy(xpath = "//span[contains(text(),'Setup Date')]")
	private WebElement lblDollarCostSetupDate;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(id = "mtgRadioBtn0")
	private WebElement radioMTG1;
	@FindBy(xpath = "//input[@value='MTG1']")
	private WebElement radioF2fMTG1;
	@FindBy(xpath = "//input[@value='ALL']")
	private WebElement radioF2fAll;
	
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath = "//input[@name='Total']")
	private WebElement inpTotal;
	@FindBy(xpath = ".//button[@id='portfolio-link']") private WebElement btnChooseIndividualFunds;
	@FindBy(xpath = ".//a[@id='rebalance-view-all-funds']") private WebElement lnkAddViewAllFunds;
	@FindBy(xpath = ".//div[@class='allocation-table-wrapper']//table[@id='allocation-all-funds-table']") private WebElement tableAllocationFund;
	
	@FindBy(xpath = ".//*[@id='allocation-all-funds-table']//tr//td[./label[@class='add-view-label']]//input") private List<WebElement> inpInvestmentOption;
	@FindBy(xpath = "//*[@id='allocation-all-funds-table']//tr[./td[./label]]//td//a[contains(@class,'allocation-fund-name')]") private List<WebElement> txtInvestmentOption;
	@FindBy(xpath = ".//*[@id='add-funds-button']") private WebElement btnAdd;
	@FindBy(xpath = ".//*[@id='pptGrpModalSubmit']") private WebElement btnAlertContinue;
	@FindBy(xpath = ".//*[@id='submitButton submit']") private WebElement btnSubmit;
	@FindBy(xpath = ".//a/span[@class='em-x-bold']") private List<WebElement> btnRemoveInvestments;
	@FindBy(xpath = "//span[@class='allocation-funds-portfolio-total']") private WebElement txttotalInvestmentPercent;
	@FindBy(xpath = "//tr[contains(@ng-repeat,'rebalanceCurrentFunds')]//span[@class='fund-overview-url']//a") private List<WebElement> txtCurrentFunds;
	@FindBy(xpath = "//h1[text()[normalize-space()='How would you like to invest?']]")
	private WebElement txtHowWouldLikeToInvest;
	
	@FindBy(xpath = "//h2[text()[normalize-space()='Do It Myself']]")
	private WebElement txtDoItMySelf;
	
	@FindBy(xpath = "//h2[text()[normalize-space()='Help Me Do It']]")
	private WebElement txtHelpMeDoIt;
	
	@FindBy(xpath = "//h2[text()[normalize-space()='Do It for Me']]")
	private WebElement txtDoItForMe;

	@FindBy(xpath = "//h1[text()[normalize-space()='Build Your Own Portfolio']]")
	private WebElement txtBuildYourOwnPortfolio;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Rebalance your portfolio']]")
	private WebElement txtRebalanceYourPortfolio;
	@FindBy(xpath = "//h1[text()[normalize-space()='Rebalance Your Portfolio']]")
	private WebElement hdrRebalanceYourPortfolio;
	
	@FindBy(xpath = "//a[contains(text(),'Back')]")
	private WebElement lnkBack;
	
	@FindBy(xpath = "//a[@id='reset-funds-changes']")
	private WebElement lnkResetFundChanges;
	
	@FindBy(xpath = ".//*[@id='submit-button'or @id='submitButton submit']") private WebElement btnSubmitChangeFutureFund;
	
	@FindBy(xpath = ".//*[@id='allocation-all-funds-table']//tr//td[./label]//input") private List<WebElement> inpInvestmentOptionFutureAllocation;
	
	@FindBy(xpath = "//tr[contains(@ng-repeat,'fund in currentFunds') or contains(@ng-repeat,'rebalanceCurrentFunds')]//td//a") private List<WebElement> txtCurrentFundsforChangeFutureFlow;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Review Your Changes']]")
	private WebElement hdrReviewYourChanges;
	
	@FindBy(xpath = "//*[@id='span_name']")
	private WebElement txtFundNameinNewWindow;
	
	@FindBy(xpath = "//button[./span[text()[normalize-space()='Confirm']]]")
	private WebElement btnConfirm;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Confirmation']]")
	private WebElement hdrConfirmation;
	
	@FindBy(xpath = "//span[contains(@ng-if,'confirmChanges')]")
	private WebElement txtConfirmation;
	@FindBy(xpath = "//span[contains(@ng-if,'confirmChanges')]//strong")
	private WebElement txtConfirmationDate;
	
	@FindBy(xpath = ".//*[@id='portfolio-link']//span[text()[normalize-space()='Current']]")
	private WebElement lblCurrent;
	
	@FindBy(xpath = "//*[@id='view-all-funds' or @id='rebalance-view-all-funds']") private WebElement lnkAddViewAllFundsFuture;
	
	@FindBy(xpath = ".//a/span[@class='em-x-bold']") private WebElement btnRemoveInvestment;
	
	@FindBy(xpath = "//tr//td[contains(@class,'allocation-fund-name')]//a") private WebElement txtAllocationFundName;
	
	@FindBy(xpath = "//div[@class='row allocation-content']//p//strong") private WebElement lblConfirmationNoFutureFund;
	
	@FindBy(xpath = "//button[@id='target-year-fund-link' or @id='targetDateFundsButton']") private WebElement btnChooseTargetDateFund;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Select a Target date fund'] or text()[normalize-space()='Select a target date fund']]")
	private WebElement txtSelectTargetDateFund;
	
	@FindBy(xpath = "//tr[contains(@ng-if,'targetYearFund') or contains(@id,'fundRow')][./td[@class='allocation-fund-name-container'][not(span)]]//td//input")
	private List<WebElement> inpTargetDateFund;
	
	@FindBy(xpath = "//tr[contains(@ng-if,'targetYearFund') or contains(@id,'fundRow')][./td[@class='allocation-fund-name-container'][not(span)]]//td[@class='allocation-fund-name-container']")
	private List<WebElement> txtTargetDateFund;
	
	@FindBy(xpath = "//*[@id='submitButton' or @id='submit']")
	private WebElement btnContinueToTargetDateFund;
	
	@FindBy(xpath = "//button[@id='target-year-fund-link']//span[text()[normalize-space()='Current']]")
	private WebElement lblCurrentTagetDateFund;

	@FindBy(xpath = "//span[./span[text()[normalize-space()='Expand sources']]]")
	private WebElement lnkExpandSources;
	
	@FindBy(xpath = "//span[./span[text()[normalize-space()='Collapse sources']]]")
	private WebElement lnkCollapseSources;
	
	@FindBy(xpath = "//button[@id='riskBasedFundsButton' or @id='risk-based-fund-link']") private WebElement btnChooseRiskBasedFund;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Select a risk-based fund']]")
	private WebElement txtSelectRiskBasedFund;
	
	@FindBy(xpath = "//button[@id='risk-based-fund-link']/span[text()[normalize-space()='Current']]")
	private WebElement currentFlagRiskBasedFund;
	
	@FindBy(xpath = "//div[contains(@class,'alert-warning')]//p")
	private WebElement allertRiskBasedFund;
	
	@FindBy(xpath = "//*[@id='frequency']")
	private WebElement drpRebalFrequency;
	
	@FindBy(xpath = "//*[@id='direct-future-investments']")
	private WebElement inpDirectFutureInvest;
	
	@FindBy(xpath = "//label[@aria-label='Direct my future investments this way']")
	private WebElement lblDirectFutureInvest;
	@FindBy(xpath="//p[text()[normalize-space()='You are not authorized to view this page.']]") private WebElement GenericErrorMsg;
	@FindBy(id = "modelPortfolioLink") private WebElement btnBasedOnModelPortfolio;
	@FindBy(xpath = "//h1[text()[normalize-space()='Select a model portfolio']]")
	private WebElement txtSelectModelPortfolio;
	@FindBy(xpath = ".//div[contains(@class,'allocation-model-funds')]//table")
	private WebElement tableAssetClass;
	@FindBy(xpath = "//h1[text()[normalize-space()='My Allocations']]")
	private WebElement lblMyAllocations;
	@FindBy(xpath = "//strong[contains(@ng-if,'isRebalancerAllowed')]")	private WebElement errMsgSmartRestriction;
	@FindBy(xpath = "//div[contains(@ng-if,'restrictedFunds')]//table//tbody//tr")	private WebElement tblRestrictedFunds;
	@FindBy(xpath = "//table[@id='rebalance-restricted-funds-table']/tbody/tr/td[1]/div/span")	private WebElement restrictedFundWarningIcon;
	@FindBy(xpath = "//show-restrictions//div[@class='modal-body']") private WebElement modalDialogRestrictedFund;
	@FindBy(xpath = "//button[contains(text(),'Close')]") private WebElement btnClose;
	@FindBy(xpath = "//*[@id='rebalance-destination-funds-table']//tbody//tr//td[2]//div//span") private WebElement unRestrictedFundWarningIcon;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'originalFundsExist')]]") private WebElement tblFutureInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'originalFundsExist')]]//div[@id='old-fund-container']") private WebElement fromSectionFutureInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'originalFundsExist')]]//div[@id='new-fund-container']") private WebElement toSectionFutureInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]") private WebElement tblRebalanceInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]//div[@id='old-fund-container']") private WebElement fromSectionRebalanceInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]//div[@id='new-fund-container']") private WebElement toSectionRebalanceInvestments;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'originalFundsExist')]]//div[@id='new-fund-container']//table//tr[./td]") private List<WebElement> listFutureInvestmentsToFunds;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]//div[@id='new-fund-container']//table//tr[./td]") private List<WebElement> listRebalanceInvestmentsToFunds;
	@FindBy(xpath = "//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]//div[@id='new-fund-container']//table//tr//td//span[contains(@class,'warning-icon')]") private WebElement unRestrictedFundWarningIconReviewPage;
	@FindBy(xpath = "//p[contains(@ng-if,'rebalanceConfirmationNumber')]//span[1]") private WebElement smartestrictionConfirmationMsg;
	@FindBy(xpath = "//p[contains(@ng-if,'rebalanceConfirmationNumber')]//span[2]") private WebElement smartestrictionConfirmationNos;
	@FindBy(xpath = "//p[contains(@ng-if,'rebalanceConfirmationNumber')]//span[1]/Strong") private WebElement smartestrictionConfirmationdate;
	@FindBy(xpath = "//button[@id='advice-link']") private WebElement btnAccessOnlineadvice;
	@FindBy(xpath = "//button[@id='guidance-link']") private WebElement btnAccessOnlineGuidance;
	@FindBy(xpath = "//button[contains(text(),'Refresh')]") private WebElement btnRefresh;
	@FindBy(xpath = "//button[contains(text(),'Close and continue')]") private WebElement btnCloseAndContinue;
	@FindBy(id = "btnOkBot") private WebElement btnNext;
	@FindBy(xpath = "//button[contains(text(),'Accept')]") private WebElement btnAccept;
	
	String inputAllocationPercrntage="//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String buttonlock=".//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//button[contains(@class,'btn-link')]";
	String inputAllocationPercentageFuture="//*[@id='allocation-current-funds-table' or @id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')] or .//td//span[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String txtFutureFundAllocationPercentage="//tr[contains(@ng-repeat,'fund in currentFunds' ) or contains(@ng-repeat,'fund in rebalanceCurrentFunds')][./td//a[contains(text(),'Investment Option')] or .//td[contains(text(),'Investment Option')] or .//td//span[contains(text(),'Investment Option')]]//td[2]";
	String slider="//input[contains(@aria-label,'Investment Option')]";
	String btnChangeInvestments="//div[./h2[contains(text(),'Money Type Grouping')]]//button[contains(text(),'Change My Investments')]";
	String txtMoneyTypeGrouping="//div[./h2[contains(text(),'Money Type Grouping')]]";
	String txtMoneyType="//div[contains(text(),'Money Type Grouping')]";
	String selectInvestment="//div[./div[./div[contains(text(),'Money Type Grouping')]]]//button[contains(text(),'Select Investments ')]";
	private String textField="//*[contains(text(),'webElementText')]";
	String inputAllocationPercentageFutureEnroll="//*[@id='allocation-current-funds-table' or @id='rebalance-destination-funds-table']//tbody//tr[.//td//span[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String choice=null;
	String smartRestrictionFutureFundAllocationPercentag="//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'originalFundsExist')]]//div[@id='new-fund-container']//table//tr[contains(.,'Investment Option')]//td[2]";
	String smartRestrictionRebalanceFundAllocationPercentag="//div[@class='fund-tables-wrapper'][./div[contains(@ng-if,'RebalanceFundsExist')]]//div[@id='new-fund-container']//table//tr[contains(.,'Investment Option')]//td[3]";
	String lblMoneyTypeGrouping="//label[contains(text(),'Money Type Grouping')]";

	/**
	 * Empty args constructor
	 * 
	 */
	public ManageMyInvestment() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public ManageMyInvestment(LoadableComponent<?> parent) {
		this.parent = parent;
		
		PageFactory.initElements(lib.Web.getDriver(), this);
		
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		if(Common.verifyLoggedInUserIsSame()) {
			
			if (Stock.globalTestdata.get(Thread.currentThread().getId()).containsKey("NAVIGATEFROM")){
				
				if(Stock.GetParameterValue("NAVIGATEFROM").equalsIgnoreCase("GuidancePage"))	{
					
					Assert.assertTrue(lib.Web.isWebElementDisplayed(txtHowWouldLikeToInvest),"Guidance Page is Not Loaded");
					
				}
				if(Stock.GetParameterValue("NAVIGATEFROM").equalsIgnoreCase("LeftNav")){
					Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyInvestments),"View/Manage MyInvestments Page is Not Loaded");
				}
				
			}else
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyInvestments),"View/Manage MyInvestments Page is Not Loaded");
				
		} else {
			
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		if (Stock.globalTestdata.get(Thread.currentThread().getId()).containsKey("NAVIGATEFROM")){
			if(Stock.GetParameterValue("NAVIGATEFROM").equalsIgnoreCase("GuidancePage"))	{
				(new LandingPage()).clickOnGuidanceLink();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.isWebElementDisplayed(txtHowWouldLikeToInvest,true);
			}
			else if(Stock.GetParameterValue("NAVIGATEFROM").equalsIgnoreCase("LIATPage"))	{
			//TODO
			}
			else if(Stock.GetParameterValue("NAVIGATEFROM").equalsIgnoreCase("LeftNav")){
				((LeftNavigationBar) this.parent)
				.clickNavigationLink("View/Manage my investments");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.isWebElementDisplayed(lblMyInvestments,true);	
			}
		}else{
			
		((LeftNavigationBar) this.parent)
				.clickNavigationLink("View/Manage my investments");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementDisplayed(lblMyInvestments,true);	
	
		}
	}

	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Rebalance Current Balance")) {
			return this.radioRebalanceCurrBal;
		}
		if (fieldName.trim().equalsIgnoreCase("Change Future Contribution")) {
			return this.radioChangeFutureContr;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"Change Current Balance Investment")) {
			return this.radioChangeCurrentBalInvst;
		}
		if (fieldName.trim().equalsIgnoreCase("Dollar Cost")) {
			return this.radioDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Continue button1")) {
			return this.btnContinue1;
		}
		if (fieldName.trim().equalsIgnoreCase("Submit button Rebalancer")) {
			return this.btnSubmitForRebalancer;
		}
		if (fieldName.trim().equalsIgnoreCase("F2F MTG1")) {
			return this.radioF2fMTG1;
		}
		if (fieldName.trim().equalsIgnoreCase("Radio Buuton F2F ALL")) {
			return this.radioF2fAll;
		}
		if (fieldName.trim().equalsIgnoreCase("Submit button F2F")) {
			return this.btnSubmitForF2F;
		}
		if (fieldName.trim().equalsIgnoreCase("Once")) {
			return this.radioOnce;
		}
		if (fieldName.trim().equalsIgnoreCase("Monthly Dollar Cost")) {
			return this.radioMonthlyDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Today")) {
			return this.radiotoday;
		}
		if (fieldName.trim().equalsIgnoreCase("Today Dollar Cost")) {
			return this.radiotodayDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Setup Today Dollar Cost")) {
			return this.radiotodayDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Future Dollar Cost")) {
			return this.radioFutureDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("View By Asset Class Tab")) {
			return this.tabViewByAssetClass;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Assets Balance Graph")) {
			return this.currentAssetBalGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("Post Transfer Balance Graph")) {
			return this.postTransferBalanceGraph;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Continue Button")) {
			return this.btnContinue1;
		}
		if (fieldName.trim().equalsIgnoreCase("Back Button")) {
			return this.btnBack;
		}
		if (fieldName.trim().equalsIgnoreCase("Header How Would You Like To Invest")) {
			return this.txtHowWouldLikeToInvest;
		}
		if (fieldName.trim().equalsIgnoreCase("Do It Myself")) {
			return this.txtDoItMySelf;
		}
		if (fieldName.trim().equalsIgnoreCase("Help Me Do It")) {
			return this.txtHelpMeDoIt;
		}
		if (fieldName.trim().equalsIgnoreCase("Do It For Me")) {
			return this.txtDoItForMe;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Choose Individual Funds")) {
			return this.btnChooseIndividualFunds;
		}

		if (fieldName.trim().equalsIgnoreCase("Header Build Your Own Portfolio")) {
			return this.txtBuildYourOwnPortfolio;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Header Rebalance your portfolio")) {
			return this.txtRebalanceYourPortfolio;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Back Link")) {
			return this.lnkBack;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Reset All Changes Link")) {
			return this.lnkResetFundChanges;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Submit Button Change Future Allocation")) {
			return this.btnSubmitChangeFutureFund;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Rebalance Add/View All Funds")) {
			return this.lnkAddViewAllFunds;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Table Select Funds")) {
			return this.tableAllocationFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Header Review Your Changes")) {
			return this.hdrReviewYourChanges;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Confirm")) {
			return this.btnConfirm;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Header Confirmation")) {
			return this.hdrConfirmation;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Text Confirmation")) {
			return this.txtConfirmation;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Flag")) {
			return this.lblCurrent;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Link Add/View All Funds")) {
			return this.lnkAddViewAllFundsFuture;
		}
		if (fieldName.trim().equalsIgnoreCase("Choose Target Date Fund")) {
			return this.btnChooseTargetDateFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Access Online Advice")) {
			return this.btnAccessOnlineadvice;
		}
		if (fieldName.trim().equalsIgnoreCase("Access Online Guidance")) {
			return this.btnAccessOnlineGuidance;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Refresh")) {
			return this.btnRefresh;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Close And Continue")) {
			return this.btnCloseAndContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Header Select Target Date Fund")) {
			return this.txtSelectTargetDateFund;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Select Target Date Fund")) {
			return this.txtSelectTargetDateFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Select a Model Portfolio")) {
			return this.txtSelectModelPortfolio;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Flag Target Date Fund")) {
			return this.lblCurrentTagetDateFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Expand Sources")) {
			return this.lnkExpandSources;
		}
		if (fieldName.trim().equalsIgnoreCase("Collapse Sources")) {
			return this.lnkCollapseSources;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Choose Risk Based Funds")) {
			return this.btnChooseRiskBasedFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Link Based On Model Portfolio")) {
			return this.btnBasedOnModelPortfolio;
		}
		if (fieldName.trim().equalsIgnoreCase("Header Select Risk Based Fund")) {
			return this.txtSelectRiskBasedFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Flag Risk Based Fund")) {
			return this.currentFlagRiskBasedFund;
		}
		if (fieldName.trim().equalsIgnoreCase("Risk Based Fund Allert Message")) {
			return this.allertRiskBasedFund;
		}
		if (fieldName.trim().equalsIgnoreCase("CheckBox Direct Future Investments")) {
			return this.inpDirectFutureInvest;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Label Direct Future Investments")) {
			return this.lblDirectFutureInvest;
		}
		if (fieldName.trim().equalsIgnoreCase("Header My Allocations")) {
			return this.lblMyAllocations;
		}
		if (fieldName.trim().equalsIgnoreCase("Smart Restriction Error Message")) {
			return this.errMsgSmartRestriction;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Warning Message Restricted Funds")) {
			return this.restrictedFundWarningIcon;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Text Confirmation Smart Restriction")) {
			return this.smartestrictionConfirmationMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("Header Rebalance Your Portfolio")) {
			return this.hdrRebalanceYourPortfolio;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Next")) {
			return this.btnNext;
		}
		if (fieldName.trim().equalsIgnoreCase("Button Accept")) {
			return this.btnAccept;
		}
		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
						|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
					return this.lnkLogout;
				}
		if (fieldName.trim().equalsIgnoreCase("Login")) {
			return this.btnLogin;
		}

		return null;
	}

	public void clickChangeMyInvestmentButton() {
		Web.waitForElement(btnChangeMyInvestment);
		if (Web.isWebElementDisplayed(btnChangeMyInvestment, true)) {
			btnChangeMyInvestment.click();
			Reporter.logEvent(Status.INFO,
					"verify Change My Investment button is displayed",
					"Clicked on Change My Investment button", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Change My Investment button is displayed",
					"Change My Investment button not displayed", true);
	}

	public void choseInvestmentOption(String investmentOption) {
		WebElement option = this.getWebElement(investmentOption);
		if (Web.isWebElementDisplayed(option, true)) {
			Web.clickOnElement(option);
			Reporter.logEvent(Status.INFO, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option is displayed", true);
			
		} else
			Reporter.logEvent(Status.FAIL, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option not displayed", true);
	}
	
	public void verifyInvestmentOptionIsDisplayed(String investmentOption) {
		WebElement option = this.getWebElement(investmentOption);
		if (Web.isWebElementDisplayed(option, true)) {
		
			Reporter.logEvent(Status.PASS, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option is displayed", false);
			
		} else
			Reporter.logEvent(Status.FAIL, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option not displayed", false);
	}

	public  void rebalanceInvestment(String frequency, String setupDate,
			String percent) {
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		WebElement freq = this.getWebElement(frequency);
		WebElement date = this.getWebElement(setupDate);
		Web.clickOnElement(freq);
		Reporter.logEvent(Status.INFO,
				"verify if frequency period is selected",
				"Selected Frequency Period : " + frequency, true);
		Web.clickOnElement(date);
		Reporter.logEvent(Status.INFO, "Specify the Rebalancer Setup Date",
				"Setup Date : " + setupDate, false);
		// Web.getDriver().switchTo().frame(iframeLegacyFeature);
		btnContinue2.click();
		if (Web.isWebElementDisplayed(tblInvestmentOptionsTable, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Investment options table is displayed",
					"Investment options table is displayed ", true);
			int noOfRows = lstInvestmentOptions.size();
			if (noOfRows >= 2) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options available ", false);
				investmentFundName1 = lstInvestmentFundName.get(1).getText()
						.trim();
				investmentFundName2 = lstInvestmentFundName.get(2).getText()
						.trim();
				System.out.println(investmentFundName1);
				System.out.println(investmentFundName2);
				Web.setTextToTextBox(lsttxtPercentage.get(1), percent);
				Web.setTextToTextBox(lsttxtPercentage.get(2), percent);
				Reporter.logEvent(Status.INFO, "Enter Investment Percent",
						"Entered investment percent : " + percent + " for "
								+ investmentFundName1 + "and"
								+ investmentFundName2 + "each", true);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						"Investment options not available ", true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Investment options Table is displayed",
					"Investment options table not displayed ", true);
		Web.clickOnElement(inpTotal);
		btnContinue2.click();
		Web.getDriver().switchTo().defaultContent();
	}

	public void dollarCostAverageFlow(String frequency, String setupDate,
			String percent, String amount) throws InterruptedException,NullPointerException,IndexOutOfBoundsException {
		Actions keyBoard = new Actions(Web.getDriver());
		
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(iframeLegacyFeature);
		iframeLegacyFeature.isDisplayed();
		if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			else{
				Web.waitForElement(iframeLegacyFeature);			
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			}
			WebElement freq = this.getWebElement(frequency);
			WebElement date = this.getWebElement(setupDate);
			Web.clickOnElement(freq);
			Reporter.logEvent(Status.INFO,
					"verify if frequency period is selected",
					"Selected Frequency Period : " + frequency, true);
			Web.clickOnElement(date);
			Reporter.logEvent(Status.INFO,
					"Specify the Dollar Cost Average Setup Date", "Setup Date : "
							+ setupDate, false);
			// Web.getDriver().switchTo().frame(iframeLegacyFeature);
			btnContinueToNextStep.click();
		try{
			Web.waitForElement(radioDoNoTerminate);
			radioDoNoTerminate.isDisplayed();
					
		}catch(NoSuchElementException e){
			cancelTransfer("Dollar Cost");
			clickChangeMyInvestmentButton();
			choseInvestmentOption("Dollar Cost");
			Web.clickOnElement(btnContinue1);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(iframeLegacyFeature);
			iframeLegacyFeature.isDisplayed();
			if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
				Web.getDriver().switchTo().frame(iframeLegacyFeature);
				else{
					Web.waitForElement(iframeLegacyFeature);			
				Web.getDriver().switchTo().frame(iframeLegacyFeature);
				}
				/*WebElement freq = this.getWebElement(frequency);
				WebElement date = this.getWebElement(setupDate);*/
				Web.clickOnElement(freq);
				Reporter.logEvent(Status.INFO,
						"verify if frequency period is selected",
						"Selected Frequency Period : " + frequency, true);
				Web.clickOnElement(date);
				Reporter.logEvent(Status.INFO,
						"Specify the Dollar Cost Average Setup Date", "Setup Date : "
								+ setupDate, false);
				// Web.getDriver().switchTo().frame(iframeLegacyFeature);
				btnContinueToNextStep.click();
		}
		
		
		
		radioDoNoTerminate.click();
		Reporter.logEvent(
				Status.INFO,
				"verify if 'Do not terminate this Dollar cost average' option is selected",
				"'Do not terminate this Dollar cost average' option is selected",
				false);
		if (Web.VerifyText("Investment Options Balance Effective Date",
				hdrInvestmentOptionTblForDollarCost.getText(), true))
			Reporter.logEvent(
					Status.PASS,
					"verify Investment Options Table Header for Dollar Cost Average",
					"Expected: Investment Options Balance Effective Date \n Actual: "
							+ hdrInvestmentOptionTblForDollarCost.getText()
									.trim(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Investment Options Table Header for Dollar Cost Average",
					"Expected: Investment Options Balance Effective Date \n Actual: "
							+ hdrInvestmentOptionTblForDollarCost.getText()
									.trim(), true);
		lstChkInvestmentOptionDollarCost.get(0).click();

		fromInvestmentOption = lstInvestmentOptionsDollarCost.get(0).getText();
		btnContinueToNextStep.click();
		Web.waitForElement(txtTransferAmtDollarCost);
		Web.setTextToTextBox(txtTransferAmtDollarCost, amount);
		Reporter.logEvent(Status.INFO, "verify if transfer amount is entered",
				"Entered Transfer amount: " + amount, true);
		btnContinueToNextStep.click();
		Thread.sleep(5000);
		Web.waitForElement(lstChkInvestmentOptionDollarCost.get(0));
		lstChkInvestmentOptionDollarCost.get(0).click();
		toInvestmentOption = lstToInvestmentOptionsDollarCost.get(0).getText();
		btnContinueToNextStep.click();
		Thread.sleep(5000);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		Web.waitForElements(lstInvestmentOptionsDollarCost);
		if (lstInvestmentOptionsDollarCost.get(0).getText()
				.contains(toInvestmentOption))
			Reporter.logEvent(Status.PASS,
					"verify Investment Option displayed", "Expected: "
							+ toInvestmentOption + "\nActual: "
							+ lstInvestmentOptionsDollarCost.get(0).getText(),
					true);
		else
			Reporter.logEvent(Status.FAIL,
					"verify Investment Options displayed", "Expected: "
							+ toInvestmentOption + "\n Actual: "
							+ lstInvestmentOptionsDollarCost.get(0).getText(),
					true);
		//Thread.sleep(5000);
		Web.waitForElement(lstInvestmentPercentDollarCost);
		Web.clickOnElement(lstInvestmentPercentDollarCost);
		keyBoard.sendKeys(Keys.BACK_SPACE).perform();
		Web.setTextToTextBox(lstInvestmentPercentDollarCost,
				Stock.GetParameterValue("percent"));
		Reporter.logEvent(Status.INFO, "verify investment percent is entered",
				"investment percent : " + Stock.GetParameterValue("percent"),
				true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
		Web.setTextToTextBox(inpTotal,
				Stock.GetParameterValue("percent"));
		keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
				
		// to handle allert
		for(int i=0;i<3;i++){
            if(Common.isAlerPresent()){
            Common.HandlePopAlert();
            Web.clickOnElement(lstInvestmentPercentDollarCost);
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            Web.setTextToTextBox(lstInvestmentPercentDollarCost,Stock.GetParameterValue("percent"));  
            keyBoard.sendKeys(Keys.TAB).perform();                  
            keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
            Web.clickOnElement(inpTotal);
            keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
            }
    }

		//Web.clickOnElement(btnContinueToNextStep);
		Web.getDriver().switchTo().defaultContent();

	}

	public void verifyDollarCostInvestmentDetails(String frequency,
			String date, String percent) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(lblDollarCostFreq);
		if (Web.VerifyText("Your chosen Dollar Cost Average frequency is: "
				+ frequency, "Your chosen Dollar Cost Average frequency is: "
				+ lblDollarCostFreq.getText(), true))
			Reporter.logEvent(
					Status.PASS,
					"verify Dollar Cost Average Frequency",
					"Expected: Your chosen Dollar Cost Average frequency is: "
							+ frequency + "\n Actual: "
							+ lblDollarCostFreq.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Dollar Cost Average Frequency",
					"Expected: Your chosen Dollar Cost Average frequency is: "
							+ frequency + "\n Actual: "
							+ lblDollarCostFreq.getText(), true);

		if (Web.VerifyText(
				"Specify the Dollar Cost Average Setup Date " + date,
				lblDollarCostSetupDate.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer setup date",
					"Expected: Specify the Dollar Cost Average Setup Date "
							+ date + "\n Actual: "
							+ lblDollarCostSetupDate.getText().trim(), false);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer setup date",
					"Expected: Specify the Dollar Cost Average Setup Date "
							+ date + "\n Actual: "
							+ lblDollarCostSetupDate.getText().trim(), true);

	}

	public void verifyRebalanceInvestmentDetails(String frequency,
			String setupDate, String date, String percent) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(lblRebalancerFrequency);
		if (Web.VerifyText("Your chosen Rebalancer frequency is: " + frequency,
				lblRebalancerFrequency.getText().trim() + " "
						+ radioOnce.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer Frequency",
					"Expected: Your chosen Rebalancer frequency is: "
							+ frequency + "\n Actual: "
							+ lblRebalancerFrequency.getText().trim() + " "
							+ radioOnce.getText().trim(), true);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer Frequency",
					"Expected: Your chosen Rebalancer frequency is: "
							+ frequency + "\n Actual: "
							+ lblRebalancerFrequency.getText().trim() + " "
							+ radioOnce.getText().trim(), true);

		if (Web.VerifyText("Your chosen Rebalancer setup date is: " + date,
				lblSetupDate.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer setup date",
					"Expected: Your chosen Rebalancer setup date is: " + date
							+ "\n Actual: " + lblSetupDate.getText().trim(),
					false);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer setup date",
					"Expected: Your chosen Rebalancer setup date is: " + date
							+ "\n Actual: " + lblSetupDate.getText().trim(),
					true);
		System.out.println(lstInvestmentFunds.get(0).getText());
		System.out.println(percent);
		if (lstInvestmentFunds.get(0).getText().contains(investmentFundName1)
				& lstInvestmentFunds.get(0).getText().contains(percent))
			Reporter.logEvent(
					Status.PASS,
					"verify Fund name and investment percent displayed",
					"Expected Fund name:  " + investmentFundName1
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(0).getText(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Fund name and investment percent displayed",
					"Expected Fund name: " + investmentFundName1
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(0).getText(), true);

		if (lstInvestmentFunds.get(1).getText().contains(investmentFundName2)
				& lstInvestmentFunds.get(0).getText().contains(percent))
			Reporter.logEvent(
					Status.PASS,
					"verify Fund name and investment percent displayed",
					"Fund name: " + investmentFundName2
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(1).getText(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Fund name and investment percent displayed",
					"Fund name: " + investmentFundName2
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(1).getText(), true);
		if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
				"Yes")) {
			btnSubmitForRebalancer.click();
			Web.getDriver().switchTo().defaultContent();
			Web.waitForElement(btnBack);
			Reporter.logEvent(Status.INFO, "Submit TRansfer Details",
					"Transfer Details Submitted", true);
		}

	}

	public void verifyRebalanceInvestmentConfirmationDetails() {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (StringUtils.containsIgnoreCase(txtTransactionDetails.getText(),
				"Your Confirmation Number is")) {
			confirmationNo = lblConfirmationNumber.getText();
			if (lblConfirmationNumber.getText() != " "
					& lblConfirmationNumber.getText() != "null")
				Reporter.logEvent(Status.PASS,
						"verify Confirmation Number Displayed",
						"Confirmation Numberdisplayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Confirmation Number Displayed",
						"Confirmation Number not displayed", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Confirmation Number Displayed",
					"Confirmation Number not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void verifyF2FConfirmationDetails() {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (StringUtils.containsIgnoreCase(lblConfirmationNumberF2F.getText(),
				"Your Confirmation Number is")) {
			confirmationNo = lblConfirmationNumberF2F.getText().split(":")[1]
					.trim();
			if (lblConfirmationNumberF2F.getText() != " "
					& lblConfirmationNumberF2F.getText() != "null")
				Reporter.logEvent(Status.PASS,
						"verify Confirmation Number Displayed",
						"Confirmation Numberdisplayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Confirmation Number Displayed",
						"Confirmation Number not displayed", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Confirmation Number Displayed",
					"Confirmation Number not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void fundToFundTransfer(String fromPercent, String toPercent) throws InterruptedException {
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
	Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (Web.isWebElementDisplayed(tblTransferFundFrom)) {
			Reporter.logEvent(Status.INFO,
					"Verify 'Transfer Fund From' Table is displayed",
					"Table is displayed", true);
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			Web.clickOnElement(btnPercent);
			//btnPercent.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(1000);
		
			Web.setTextToTextBox(txtTransferFromPercent.get(0), fromPercent);
			Actions action= new Actions(Web.getDriver());
			action.sendKeys(Keys.TAB);
			Web.clickOnElement(txtTransferFromPercent.get(0));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			fromInvestmentOption = lnkTransferFromInvestmentOption.getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund From' Table is displayed",
					"Table is not displayed", true);

		if (Web.isWebElementDisplayed(tblTransferFundTo,true)) {
			Reporter.logEvent(Status.INFO,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is displayed", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(tblTransferFundTo.isEnabled())
			{
				try{
					Web.setTextToTextBox(txtTransferToPercent.get(1), toPercent);
				}
				catch(Exception e)
				{
					Web.setTextToTextBox(txtTransferFromPercent.get(0), Keys.TAB);
					Web.setTextToTextBox(txtTransferFromPercent.get(0), fromPercent);
					txtTransferFromPercent.get(0).click();
					Web.setTextToTextBox(txtTransferToPercent.get(1), toPercent);
				}
			}
			toInvestmentOption = lnkTransferToInvestmentOption.get(1).getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is not displayed", true);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		Web.waitForElement(btnReviewTransfer.get(1));
		btnReviewTransfer.get(1).click();
		/*Web.waitForElement(btnPreValidationOK);
		Web.clickOnElement(btnPreValidationOK);*/
		
		Web.getDriver().switchTo().defaultContent();
	}
	public void submitFundToFundTransfer() throws InterruptedException {

			clickChangeMyInvestmentButton();
			choseInvestmentOption("Change Current Balance Investment");
			Web.clickOnElement(btnContinue1);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().frame("legacyFeatureIframe");
			Web.clickOnElement(radioF2fAll);
			Thread.sleep(2000);
			Web.clickOnElement(btnSubmitForRebalancer);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			Web.clickOnElement(btnPercent);
			Web.setTextToTextBox(txtTransferFromPercent.get(0), "90");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			Web.setTextToTextBox(txtTransferToPercent.get(1), "100");
			toInvestmentOption = lnkTransferToInvestmentOption.get(1).getText();
		
		 ((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		 Web.waitForElement(btnReviewTransfer.get(1));
		btnReviewTransfer.get(1).click();
		
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		Web.waitForElement(btnPreValidationOK);
		Web.clickOnElement(btnPreValidationOK);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		 Web.waitForElement(btnSubmitForF2F);
		Web.clickOnElement(btnSubmitForF2F);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		
		Web.getDriver().switchTo().defaultContent();
					
		
	}
	public void ReviewFundToFundTransfer(String fromPercent, String toPercent) throws InterruptedException {
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(hdrVerifyTransferFromTable);
		
		do{
			Thread.sleep(3000);
		}while(!((JavascriptExecutor)Web.getDriver()).
				executeScript("return document.readyState").toString().equalsIgnoreCase("complete"));
		
		if (Web.VerifyText("Investment Option Balance Percent",
				hdrVerifyTransferFromTable.getText(), true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

			if (Web.VerifyText(fromInvestmentOption,
					lblTransferFromOption.getText(), true))
				Reporter.logEvent(
						Status.PASS,
						"Verify From Investment Option in Review Transfer Page",
						"Expected : " + fromInvestmentOption + "\n Actual :"
								+ lblTransferFromOption.getText(), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify From Investment Option in Review Transfer Page",
						"Expected : " + fromInvestmentOption + "\n Actual :"
								+ lblTransferFromOption.getText(), true);

			if (Web.VerifyText(fromPercent + "%",
					lblTransferFromPercent.getText(), true))
				Reporter.logEvent(
						Status.PASS,
						"Verify From Investment Percent in Review Transfer Page",
						"Expected : " + fromPercent + "%" + "\n Actual :"
								+ lblTransferFromPercent.getText(), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify From Investment Percent in Review Transfer Page",
						"Expected : " + fromPercent + "%" + "\n Actual :"
								+ lblTransferFromPercent.getText(), true);
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

		if (Web.VerifyText("Investment Option Balance Percent",
				hdrVerifyTransferFromTable.getText(), true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

			if (Web.VerifyText(toInvestmentOption,
					lblTransferToOption.getText(), true))
				Reporter.logEvent(Status.PASS,
						"Verify To Investment Option in Review Transfer Page",
						"Expected : " + toInvestmentOption + "\n Actual :"
								+ lblTransferToOption.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify To Investment Option in Review Transfer Page",
						"Expected : " + toInvestmentOption + "\n Actual :"
								+ lblTransferToOption.getText(), true);

			if (Web.VerifyText(toPercent + "%", lblTransferToPercent.getText(),
					true))
				Reporter.logEvent(Status.PASS,
						"Verify To Investment Percent in Review Transfer Page",
						"Expected : " + toPercent + "%" + "\n Actual :"
								+ lblTransferToPercent.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify To Investment Percent in Review Transfer Page",
						"Expected : " + toPercent + "%" + "\n Actual :"
								+ lblTransferToPercent.getText(), true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Transfer To Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferToTable.getText(), true);
		if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
				"Yes")) {
			btnSubmitForF2F.click();
			Web.getDriver().switchTo().defaultContent();
			Web.waitForElement(btnBack);
			Reporter.logEvent(Status.INFO, "Submit TRansfer Details",
					"Transfer Details Submitted", true);
		}

	}

	public void navigateToTab(String tabName) throws InterruptedException {
		
		try{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(iframeLegacyFeature);
			Web.getDriver().switchTo().frame("legacyFeatureIframe");
			WebElement tab = this.getWebElement(tabName);
			tab.isDisplayed();
					
		}catch(NoSuchElementException e){
			cancelTransfer("F2F");
			clickChangeMyInvestmentButton();
			choseInvestmentOption("Change Current Balance Investment");
			Web.clickOnElement(btnContinue1);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
				Web.getDriver().switchTo().frame(iframeLegacyFeature);
				else{
					Web.waitForElement(iframeLegacyFeature);
				Web.getDriver().switchTo().frame(iframeLegacyFeature);
				}
		}
		
		
		WebElement tab = this.getWebElement(tabName);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(tab);
		tab.click();
		Reporter.logEvent(Status.INFO, "Navigate To " + tabName,
				"Navigated to " + tabName, true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void verifyIfGraphDisplayed(String graphName) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		WebElement graph = this.getWebElement(graphName);
		if (Web.isWebElementDisplayed(graph, true))
			Reporter.logEvent(Status.PASS, "Verify " + graphName
					+ "is displayed", graphName + "is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify " + graphName
					+ "is displayed", graphName + "is not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void cancelTransfer(String transferType) throws InterruptedException {
		//btnBack.click();
		//choseInvestmentOption(transferType);
		//btnContinue1.click();
		if (transferType.equalsIgnoreCase("Rebalance Currnet Balance")) {
			//Web.waitForElement(iframeLegacyFeature);
			//Web.getDriver().switchTo().frame(iframeLegacyFeature);
			//radioOnce.click();
			btnContinue1.click();
			Web.waitForElement(iframeLegacyFeature);
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			Web.waitForElement(btnContinue2);
			btnContinue2.click();
			Web.waitForElement(btnCancelTransferF2F);
			btnCancelTransferF2F.click();
			//btnViewPendingTransfer.click();
			
		}
		if (transferType.equalsIgnoreCase("F2F")) {
		
			Web.waitForElement(btnCancelTransfer);
			btnCancelTransfer.click();
			Thread.sleep(4000);
			Web.getDriver().switchTo().frame("the_iframe");
			Web.waitForElement(btnCont);
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
			Web.waitForElement(btnCont);
			btnCont.click();
			Web.waitForElement(btnCancelTransferF2F);
			btnCancelTransferF2F.click();

		}
		
		if (transferType.equalsIgnoreCase("Dollar Cost")) {
			Web.waitForElement(btnViewPendingTransfer);
			btnViewPendingTransfer.click();
			Web.waitForElement(btnCont);
			btnCont.click();
			Web.waitForElement(btnCancelTransferF2F);
			btnCancelTransferF2F.click();

		}
		
		Web.getDriver().switchTo().defaultContent();
		Web.waitForElement(btnBack);
		btnBack.click();
		/*Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		// Web.getDriver().switchTo().frame(theIframe);
		btnCont.click();
		if (StringUtils.containsIgnoreCase(
				lblConfirmationNumberForCancel.getText(),
				"Transfer Details for Confirmation Number: " + confirmationNo))
			Reporter.logEvent(Status.PASS,
					"Verify Confirmation number displayed",
					"Expected : Transfer Details for Confirmation Number: "
							+ confirmationNo + "\n Actual : "
							+ lblConfirmationNumberForCancel.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Confirmation number displayed",
					"Expected : "
							+ "Expected : Transfer Details for Confirmation Number: "
							+ confirmationNo + "\n Actual : "
							+ lblConfirmationNumberForCancel.getText(), true);

		btnCancelTransferF2F.click();
		Reporter.logEvent(Status.INFO, "Verify Transfer Cancelled",
				"Transfer Cancelled", true);*/
		Web.getDriver().switchTo().defaultContent();
	}
	public void rebalanceInvestment_New(int noOfInvestmentoptions,String[] percent) throws InterruptedException {
		try{
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(btnChooseIndividualFunds);
			btnChooseIndividualFunds.isDisplayed();
					
		}catch(NoSuchElementException e){
			cancelTransfer("Rebalance Currnet Balance");
			clickChangeMyInvestmentButton();
			choseInvestmentOption("Rebalance Currnet Balance");
			Web.clickOnElement(btnContinue1);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
		}
		
		if(Stock.getConfigParam("TEST_ENV").toUpperCase().startsWith("PROJ")){
			Web.clickOnElement(radioMTG1);
			Thread.sleep(2000);
			Web.clickOnElement(btnContinue1);
		}
		/*Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());*/
		Web.waitForElement(btnChooseIndividualFunds);
		Web.clickOnElement(btnChooseIndividualFunds);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(lnkAddViewAllFunds, true)){
		Reporter.logEvent(Status.PASS,
				"verify if Rebalance Your Portfolio Page is Displayed",
				"Rebalance Your Portfolio Page is Displayed", true);
		}
		else{
		Reporter.logEvent(Status.FAIL,
				"verify if Rebalance Your Portfolio Page is Displayed",
				"Rebalance Your Portfolio Page is Not Displayed", true);
		}
		if(Web.isWebElementsDisplayed(btnRemoveInvestments))
		{
			for(int i=0;i<btnRemoveInvestments.size();i++){
				btnRemoveInvestments.get(i).click();
				Web.waitForPageToLoad(Web.getDriver());
			}
		}
				Web.clickOnElement(lnkAddViewAllFunds);
				Web.waitForPageToLoad(Web.getDriver());
				
		if (Web.isWebElementDisplayed(tableAllocationFund)) {
			Reporter.logEvent(Status.PASS,
					"Verify Investment Allocation table is displayed",
					"Investment Allocation table is displayed ", true);
			////////////////////////////////////////////
			int noOfRows = inpInvestmentOption.size();
			if (noOfRows >= noOfInvestmentoptions) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options are available ", false);
				investmentFundName1 = txtInvestmentOption.get(0).getText()
						.trim();
				investmentFundName2 = txtInvestmentOption.get(1).getText()
						.trim();
				System.out.println(investmentFundName1);
				System.out.println(investmentFundName2);
				Web.clickOnElement(inpInvestmentOption.get(0));
				Web.clickOnElement(inpInvestmentOption.get(1));
				Reporter.logEvent(Status.INFO, "Selected Investment Options",
						"Selected Investment Options : \n" 
								+ investmentFundName1 + ",\n"
								+ investmentFundName2 , true);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						noOfInvestmentoptions+"Investment options are not available ", true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Investment options Table is displayed",
					"Investment options table is not displayed ", true);
		Web.clickOnElement(btnAdd);
			
		WebElement inptAllocationPercent1 = Web.getDriver().findElement(By
				.xpath(inputAllocationPercrntage.replace("Investment Option",
						investmentFundName1)));
		Web.setTextToTextBox(inptAllocationPercent1, percent[0]);
		WebElement inptAllocationPercent2 = Web.getDriver().findElement(By
				.xpath(inputAllocationPercrntage.replace("Investment Option",
						investmentFundName2)));
		Web.setTextToTextBox(inptAllocationPercent2, percent[1]);
		Web.waitForPageToLoad(Web.getDriver());
		if(txttotalInvestmentPercent.getText().contains("100")){
			Reporter.logEvent(Status.PASS,
					"Verify Investment Percentage is Entered and Equals to 100",
					"Investment Percentage is Matching and Equals to 100", true);
		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
							"Verify Investment Percentage is Entered and Matching",
				"Investment Percentage is not Matching", true);
			}
		btnSubmit.click();
		if(Web.isWebElementDisplayed(btnAlertContinue, true)){
			Web.clickOnElement(btnAlertContinue);
		}
	Common.waitForProgressBar();
	Web.waitForPageToLoad(Web.getDriver());
	Web.waitForElements(txtCurrentFunds);
	String currentFund1=txtCurrentFunds.get(0).getText().trim();
	String currentFund2=txtCurrentFunds.get(1).getText().trim();
	boolean iscurrentFund1Matching=false;
	boolean iscurrentFund2Matching=false;
	for(int i=0;i<txtCurrentFunds.size();i++){
		if(txtCurrentFunds.get(i).getText().trim().equalsIgnoreCase(investmentFundName1)){
			iscurrentFund1Matching=true;
			break;
		}
	}
	for(int i=0;i<txtCurrentFunds.size();i++){
		if(txtCurrentFunds.get(i).getText().trim().equalsIgnoreCase(investmentFundName2)){
			iscurrentFund2Matching=true;
			break;
		}
	}
if(iscurrentFund1Matching&&iscurrentFund2Matching){
		
		Reporter.logEvent(Status.PASS,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Matching in Review your changes Page\nInvestment Options:"+investmentFundName1+",\n"+investmentFundName2, true);
	}
	else 
		{
		Reporter.logEvent(Status.FAIL,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Not Matching in Review your changes Page\nInvestment Options:"+investmentFundName1+",\n"+investmentFundName2, true);
		}
	/*if(currentFund1.equalsIgnoreCase(investmentFundName1)&&currentFund2.equalsIgnoreCase(investmentFundName2)){
		
		Reporter.logEvent(Status.PASS,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Matching in Review your changes Page\nExpected\n:"+investmentFundName1+",\n"+investmentFundName2+"\nActual:\n"+currentFund1+",\n "+currentFund2, true);
	}
	else 
		{
		Reporter.logEvent(Status.FAIL,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Matching in Review your changes Page\nExpected:"+investmentFundName1+" and "+investmentFundName2+"\nActual:"+currentFund1+" and "+currentFund2, true);
		}
	*/
	}
	
	public boolean verifyWebElementDisplayed(String fieldName) {

		boolean isDisplayed = Web.isWebElementDisplayed(
				this.getWebElement(fieldName), true);

		if (isDisplayed) {

			Reporter.logEvent(Status.PASS, "Verify Text Field \'"+fieldName+"\'  is displayed",
					"Text Field \'"+fieldName+"\' is displayed", false);
			isDisplayed = true;

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify Text Field \'"+fieldName+"\'  is displayed",
					"Text Field \'"+fieldName+"\' is not displayed", false);
		}

		return isDisplayed;

	}
	/**
	 * Method to remove Investment options from Build Your Own Portfolio Page
	 */
	public void removeAllIvestments() {
    
		if(Web.isWebElementDisplayed(btnRemoveInvestment))
		{
			for(int i=0;i<btnRemoveInvestments.size();i++){
				btnRemoveInvestments.get(0).click();
				Web.waitForPageToLoad(Web.getDriver());
			}
			Web.clickOnElement(btnRemoveInvestment);
		}
		

	}
	/**
	 * This Method to select the investment options and enter the percentage
	 * @author srsksr
	 *@param noOfInvestmentoptions
	 *@param percent
	 *
	 */
	public synchronized Map<String, String>  addInvestments(int noOfInvestmentoptions,String[] percent) throws InterruptedException {
		try{
		    mapInvestmentOptions=new HashMap<String, String>();
		    mapCurrentInvestmentOptionsPecentage=new HashMap<String, String>();
		    Web.waitForElements(inpInvestmentOptionFutureAllocation);
			
			int noOfRows = inpInvestmentOptionFutureAllocation.size();
			if (noOfRows >= noOfInvestmentoptions) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options are available ", false);
				for(int i=0;i<noOfInvestmentoptions;i++){
					int j=i+1;
					mapInvestmentOptions.put("investmentFundName"+i, txtInvestmentOption.get(i).getText());
				
				Web.clickOnElement(inpInvestmentOptionFutureAllocation.get(i));
				
				Reporter.logEvent(Status.INFO, "Selected Investment Option",
						"Selected Investment Option"+j+" : \n" 
								+ mapInvestmentOptions.get("investmentFundName"+i) , true);
				}
			
				
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						noOfInvestmentoptions+"Investment options are not available ", true);

			Web.clickOnElement(btnAdd);
		
			Web.waitForElement(lnkAddViewAllFundsFuture);
			
			for(int i=0;i<noOfInvestmentoptions;i++){
		try{		
		WebElement inptAllocationPercent = Web.getDriver().findElement(By
				.xpath(inputAllocationPercentageFuture.replace("Investment Option",
						mapInvestmentOptions.get("investmentFundName"+i))));
		
		
		Web.setTextToTextBox(inptAllocationPercent, percent[i]);
		Thread.sleep(1000);
		mapCurrentInvestmentOptionsPecentage.put(mapInvestmentOptions.get("investmentFundName"+i), inptAllocationPercent.getAttribute("value"));
		
			}
		catch(NoSuchElementException e){
			e.printStackTrace();
		}
			}
			
		Web.waitForPageToLoad(Web.getDriver());
		
		if(txttotalInvestmentPercent.getText().contains("100")){
			Reporter.logEvent(Status.PASS,
					"Verify Investment Percentage is Entered and Equals to 100",
					"Investment Percentage is Matching and Equals to 100", true);

		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
							"Verify Investment Percentage is Entered and Matching",
				"Investment Percentage is not Matching", true);
			}
		btnSubmitChangeFutureFund.click();
		if(Web.isWebElementDisplayed(btnAlertContinue, true)){
			Web.clickOnElement(btnAlertContinue);
		}
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElements(txtCurrentFundsforChangeFutureFlow);
		}
		catch(NoSuchElementException e){
			
		}
		return mapCurrentInvestmentOptionsPecentage;
	}
	
	/**
	 * Method to click and verify Investment option opens in new Tab/Window in Review Your Changes Page
	 * @return boolean
	 */
	public boolean VerifyInvestmentOptionOpenInNewWindow() {
		boolean windowFound=false;
		String parentWindow;
		if(Web.isWebElementsDisplayed(txtCurrentFundsforChangeFutureFlow))
		{
				String fundName=txtCurrentFundsforChangeFutureFlow.get(0).getText().toString().trim();
				txtCurrentFundsforChangeFutureFlow.get(0).click();
				Web.waitForPageToLoad(Web.getDriver());
				parentWindow = Web.getDriver().getWindowHandle();
				Set<String> handles =  Web.getDriver().getWindowHandles();
				   for(String windowHandle  : handles)
				       {
				       if(!windowHandle.equals(parentWindow)){
				    	   Web.getDriver().switchTo().window(windowHandle);
				    	   Web.waitForPageToLoad(Web.getDriver());
				    	   Web.waitForElement(txtFundNameinNewWindow);
				    	   System.out.println(txtFundNameinNewWindow.getText().toString().replaceAll("®", "").replaceAll(":", "").trim());
			    		   System.out.println(fundName);
				    	   if(txtFundNameinNewWindow.getText().toString().replaceAll("®", "").replaceAll(":", "").trim().contains(fundName)){
				    		   System.out.println(txtFundNameinNewWindow.getText().toString().trim());
				    		   System.out.println(fundName);
				    		   windowFound=true;
				    		   break;
				    	   } 
				    	  
				       }
				     
				    }
				   Web.getDriver().close(); //closing child window
		           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
		           Web.getDriver().switchTo().defaultContent();
		        
			}
		return windowFound;  
	}

	/**
	 * <pre>
	 * Method to get the text of an webElement
	 * Returns string webElement is displayed
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementText(String fieldName) {
		String getText = "";

		if (verifyWebElementDisplayed(fieldName)) {

			getText = this.getWebElement(fieldName).getText().trim();

		}

		return getText;

	}
	
	/**
	 * <pre>
	 * Method to get the Current Funds from Review changes and Confirmation Pages
	 * Returns map
	 * </pre>
	 * 
	 * @return mapInvestmentOptions
	 */
	public Map<String, String> getCurrentFunds() {
	
          mapInvestmentOptions=new HashMap<String,String>();
		if (Web.isWebElementsDisplayed(txtCurrentFundsforChangeFutureFlow)) {

			for(int i=0;i<txtCurrentFundsforChangeFutureFlow.size();i++){
				mapInvestmentOptions.put("investmentOption"+i, txtCurrentFundsforChangeFutureFlow.get(i).getText().toString().trim());
			}

		}

		return mapInvestmentOptions;

	}
	
	/**
	 * <pre>
	 * Method to get the Confirmation Number from Confirmation Page for Change Future Allocation Flow
	 * Returns String
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getConfirmationNoChangeFutureFlow() {
	
          String confirmationNo="";
		if (Web.isWebElementDisplayed(lblConfirmationNoFutureFund)) {

			confirmationNo=lblConfirmationNoFutureFund.getText().toString().trim();
		}

		return confirmationNo;

	}
	
	
	/**
	 * <pre>
	 * Method to verify the Confirmation Number is updated in Data Base
	 * Returns boolean
	 * </pre>
	 * 
	 * @return Boolean - isDisplayed
	 * @throws SQLException 
	 */
	public boolean verifyConfirmationNoUpdatedInDB(String queryName,String userName,String confirmationNo,String tableName) throws SQLException {
	
		boolean isRowCreated=false;
         
		String[] sqlQuery = Stock.getTestQuery(queryName);
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				confirmationNo);
		
		int noOfRows = DB.getRecordSetCount(participants);
		
		if (noOfRows>=1) {

			Reporter.logEvent(Status.PASS,
					"Verify New Row is Cretaed in "+tableName+ " Table with Confirmation No:"+confirmationNo,
					"New Row is Cretaed in "+tableName+ " Table with Confirmation No:"+confirmationNo, false);
			isRowCreated=true;
		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
					"Verify New Row is Cretaed in "+tableName+ "Table with Confirmation No:"+confirmationNo,
					"New Row is not Cretaed in "+tableName+ " Table with Confirmation No:"+confirmationNo, false);
			}
		return isRowCreated;

	}
	
	
	/**
	 * <pre>
	 * Method to select the Target year fund/Risk based Fund for Change Future Allocation-HMDI
	 * Returns String
	 * </pre>
	 * 
	 * @return String - targetYearFund
	 */
	public String selectTargetYearFund() {
	
        
          if (Web.isWebElementsDisplayed(inpTargetDateFund)) {
			if(inpTargetDateFund.size()>=1){
				Web.clickOnElement(inpTargetDateFund.get(0));
				targetYearFund=txtTargetDateFund.get(0).getText().toString().trim();
				Reporter.logEvent(Status.PASS,
						"Verify 'Target Year Fund' is selected",
						"Selected 'Target Year Fund' is:"+targetYearFund, true);
				
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Target Year Fund' is selected",
						"'Target Year Fund' is not selected", true);
				}
			
		}
   Web.clickOnElement(btnContinueToTargetDateFund);
   Web.waitForElement(hdrReviewYourChanges);
		return targetYearFund;

	}
	
	/**
	 * <pre>
	 * Method to select the Target year fund for Change Future Allocation-HMDI
	 * Returns String
	 * </pre>
	 * 
	 * @return String - percentage
	 */
	public String getAllocatedPecentageForFund(String fundName) {
	
          String percentage="";
          WebElement txtAllocationPercent = Web.getDriver().findElement(By
  				.xpath(txtFutureFundAllocationPercentage.replace("Investment Option",
  						fundName)));
          percentage=txtAllocationPercent.getText().toString().trim();
  		
  		return percentage;

	}
	
	/**
	 * This Method to select the investment options 
	 * @author srsksr
	 *@param noOfInvestmentoptions
	 *@param percent
	 *
	 */
	public Map<String,String> selectInvestmentOptions(int noOfInvestmentoptions) throws InterruptedException {
		try{
		    mapInvestmentOptions=new HashMap<String, String>();
			
			int noOfRows = inpInvestmentOptionFutureAllocation.size();
			if (noOfRows >= noOfInvestmentoptions) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options are available ", false);
				for(int i=0;i<noOfInvestmentoptions;i++){
					mapInvestmentOptions.put("investmentFundName"+i, txtInvestmentOption.get(i).getText());
				
				Web.clickOnElement(inpInvestmentOptionFutureAllocation.get(i));
				
				Reporter.logEvent(Status.INFO, "Selected Investment Option",
						"Selected Investment Option : \n" 
								+ mapInvestmentOptions.get("investmentFundName"+i) , true);
				}
			
				
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						noOfInvestmentoptions+"Investment options are not available ", true);

			Web.clickOnElement(btnAdd);
		
			Web.waitForElement(lnkAddViewAllFundsFuture);
			
			
		}
		
		catch(Exception e){
			
		}
		
		return mapInvestmentOptions;
	}
	
	public void moveSlider(String fundName,int percent){
		
		WebElement inpSlider = Web.getDriver().findElement(By
				.xpath(slider.replace("Investment Option",fundName)));
		
		    int width=inpSlider.getSize().getWidth();
		    System.out.println("Slider width"+width);
		    Actions move = new Actions(Web.getDriver());
		    move.moveToElement(inpSlider, ((width*percent)/100), 0).click();
		  
		    move.build().perform();
		   
		  
			
		}
	
	/**
	 * <pre>
	 * Method to Verify Submit Button is Enabled or Not
	 * Returns boolean
	 * </pre>
	 * 
	 * @return boolean - isEnabled
	 * @throws InterruptedException 
	 */
	public boolean verifySubmitButtonisEnabled() throws InterruptedException {
	
		boolean isEnabled=false;
		Thread.sleep(3000);
          if(btnSubmitChangeFutureFund.isEnabled())
        	  isEnabled=true;
  		
  		return isEnabled;

	}
	
	/**
	 * <pre>
	 * Method to Click on Change My Investments Button for Particular Money Type Grouping
	 * 
	 * </pre>
	 * 
	 * @param String - moneyTypeGrouping
	 */
	public void clickChangeMyInvestmentButton(String moneyTypeGrouping) {
	
  		
		WebElement btnChangeMyInvestment = Web.getDriver().findElement(By
				.xpath(btnChangeInvestments.replace("Money Type Grouping",moneyTypeGrouping)));
		Web.clickOnElement(btnChangeMyInvestment);
		
	}
	
	/**
	 * <pre>
	 * Method to Verify Money Type Grouping is Displayed in My Investments Page
	 * Returns boolean
	 * </pre>
	 * 
	 * @return boolean - isDisplayed
	 */
	public boolean verifyMoneyTypeGroupIsDisplayed(String moneyTypeGrouping) {
	
		boolean isDisplayed=false;
		
		try
		{
			WebElement textMoneyTypeGrouping = Web.getDriver().findElement(By		
				.xpath(txtMoneyTypeGrouping.replace("Money Type Grouping",moneyTypeGrouping)));
			if(textMoneyTypeGrouping.isDisplayed())
				isDisplayed=true;
		}
          catch(NoSuchElementException e){
        	  isDisplayed=false;
          }
  		
  		return isDisplayed;

	}
	
	/**
	 * <pre>
	 * Method to Select the Frequency For Rebalance
	 * 
	 * </pre>
	 * 
	 * @param String - frequencyType
	 */
	public void selectFrequencyForRebalance(String frequencyType) {
	
		Web.waitForElement(drpRebalFrequency);
		Web.selectDropDownOption(drpRebalFrequency, frequencyType, true);
		
	}
	
	/**
	 * <pre>
	 * Method to Select/UnSelect Direct My Future Investment Check Box For Rebalance
	 * 
	 * </pre>
	 * 
	 * @param boolean - select
	 */
	public void selectFutureInvestmentCheckBox(boolean select ) {
	
		Web.waitForElement(inpDirectFutureInvest);
		if(!select){
			Web.clickOnElement(inpDirectFutureInvest);
		}
		
	}
	
	
	/**
	 * <pre>
	 * Method to Verify Frequency For Rebalance is Anual By default
	 * 
	 * </pre>
	 * 
	 * @param String - frequencyType
	 */
	public void VerifyFrequencyForRebalanceisMatching(String frequencyType) {
	
		Web.waitForElement(drpRebalFrequency);
		Select drpDown=new Select(drpRebalFrequency);
		if(drpDown.getFirstSelectedOption().getText().equalsIgnoreCase(frequencyType)){
			
			Reporter.logEvent(Status.INFO, "Verify the Default Frequency Option For Rebalance",
					"Default Frequency Option For Rebalance is Matching\nExpected:"+frequencyType +"\nActual:"
							+drpDown.getFirstSelectedOption().getText() , true);
			}
		
			
		 else
			Reporter.logEvent(Status.INFO, "Verify the Default Frequency Option For Rebalance",
					"Default Frequency Option For Rebalance is Not Matching\nExpected:"+frequencyType +"\nActual:"
							+drpDown.getFirstSelectedOption().getText() , true);
		
	}
	
	/**
	 * <pre>
	 * Method to Verify ConfirmationPage For Rebalance flow
	 * 
	 * </pre>
	 * 
	 * @param String - frequencyType
	 */
	public void verifyRebalanceInvestmentConfirmationDetails(String frequencyType) {
		String expectedConfirmationMsg=null;
		String actualConfirmationMsg=null;
		
		List<String> dates=getInvestmentsSubmissionTime();
		String date=txtTransactionDate.getText().toString().trim();
		if(date.equalsIgnoreCase(dates.get(0))){
			date=dates.get(0);
		}
		else{
			date=dates.get(1);
		}
		
		if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
		if(Stock.GetParameterValue("RebalFrequency").equalsIgnoreCase("Once")){
			
			expectedConfirmationMsg="Your investment allocation request for current account balance, has been received as of "+date+", and will be processed as soon as administratively feasible.";
		}
		else{
			expectedConfirmationMsg="Your investment allocation request for current account balance, has been received as of "+date+", and will be processed as soon as administratively feasible. Your account will automatically rebalance "+frequencyType+".";
		}
			
			actualConfirmationMsg=txtConfirmationWhenRebalneceSyncFailed.getText().toString().trim();
		}
		else{
			expectedConfirmationMsg="Your investment allocation request for current account balance, and future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible. Your account will automatically rebalance "+frequencyType+".";
		
		   actualConfirmationMsg=txtTransactionDetails.getText().toString().trim();
		}
		
		if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
			
			Reporter.logEvent(Status.PASS,
					"Verify Confirmation Message is Displayed in Confirmation Page",
					"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
		}
		else{
			Reporter.logEvent(Status.INFO,
					"Verify Confirmation Message is Displayed in Confirmation Page",
					"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
		}
		if(Web.isWebElementDisplayed(txtConfirmationNumber)){
		confirmationNo = txtConfirmationNumber.getText();
		expectedConfirmationMsg="Your confirmation number is "+confirmationNo+".";
		actualConfirmationMsg=lblConfirmationNumber.getText().toString().trim();
		if (expectedConfirmationMsg.equalsIgnoreCase(actualConfirmationMsg))
			Reporter.logEvent(Status.PASS,
					"Verify Confirmation Number Displayed",
					"Confirmation Number is displayed \nConfirmation No:"+confirmationNo, false);
		}else
			Reporter.logEvent(Status.FAIL,
					"Verify Confirmation Number Displayed",
					"Confirmation Number not displayed", false);
		if (Web.isWebElementDisplayed(lblGreenCheck))
			Reporter.logEvent(Status.PASS,
					"Verify Right Tick is Displayed below Confirmation header",
					"Right Tick is Displayed below Confirmation header", false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify Right Tick is Displayed below Confirmation header",
					"Right Tick is Displayed below Confirmation header", false);
	    isTextFieldDisplayed("Please refer to this number for inquires regarding this transaction.");
		
	}
	
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
	
	/**
	 * <pre>
	 * Method to get the Confirmation Number from Confirmation Page for Rebalance Flow
	 * Returns String
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getRebalanceConfirmationNO() {
	
          String confirmationNo="";
		if (Web.isWebElementDisplayed(txtConfirmationNumber)) {

			confirmationNo=txtConfirmationNumber.getText().toString().trim();
		}

		return confirmationNo;

	}
	
	/**<pre> Method to delete Pending Transaction for Rebalance.
	 *.</pre>
	 * @param userName - Partcipant userName
	 * @param 
	 * @return - boolean
	 * @throws Exception 
	 */
	public void deleteRebalancePendingTransaction(String userName) throws Exception{
		String[] sqlQuery=null;
		
		sqlQuery = Stock.getTestQuery("deleteRebalancePendingTransaction");
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
	
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
		
		
	}
	
	/**
	 * <pre>
	 * Method to verify the Confirmation Number is updated in Data Base
	 * Returns boolean
	 * </pre>
	 * 
	 * @return Boolean - isDisplayed
	 * @throws SQLException 
	 */
	public boolean verifyTRF_BasicTableInDB(String queryName,String userName,String confirmationNo,String Freequency) throws SQLException {
	
		boolean isRowCreated=false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();         
		String date=dateFormat.format(calendar.getTime());
		System.out.println("DATE"+date);
		String[] sqlQuery = Stock.getTestQuery(queryName);
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				userName.substring(0, 9));
		
		int noOfRows = DB.getRecordSetCount(participants);
		Reporter.logEvent(Status.INFO,
				"Verifying  Confirmation Details in 'TRF_BASIC' Table",
				"", false);
		
		if (noOfRows>0) {
			participants.first();
			
			if(participants.getString("EV_ID").equalsIgnoreCase(confirmationNo))
				Reporter.logEvent(Status.PASS,
						"Verify  Confirmation No is Matching in 'TRF_BASIC' Table",
						" Confirmation No is Matching in 'TRF_BASIC' Table \nConfirmation No:"+confirmationNo, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify  Confirmation No is Matching in 'TRF_BASIC' Table",
						" Confirmation No is Not Matching in 'TRF_BASIC' Table \nConfirmation No:"+confirmationNo, false);
		
		if(participants.getString("next_sched_trf_date").contains(date))
			Reporter.logEvent(Status.PASS,
					"Verify 'Next_SCHED_TRF_DATE' is Matching",
					"'Next_SCHED_TRF_DATE' is Matching \n'Next_SCHED_TRF_DATE' :"+date, false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Next_SCHED_TRF_DATE' is Matching",
					"'Next_SCHED_TRF_DATE' is Not Matching \nExpected:"+date+"\nActual"+participants.getString("next_sched_trf_date"), false);
		
		if(participants.getString("status_code").equalsIgnoreCase("PER TRF"))
			Reporter.logEvent(Status.PASS,
					"Verify 'STATUS_CODE' is Matching",
					"'STATUS_CODE' is Matching \n'STATUS_CODE':"+participants.getString("status_code"), false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'STATUS_CODE' is Matching",
					"'STATUS_CODE' is Not Matching \n'Expected:PER TRF\nActual"+participants.getString("status_code"), false);
		
		if(participants.getString("freq_code").equalsIgnoreCase(Freequency))
			Reporter.logEvent(Status.PASS,
					"Verify 'FREQ_CODE' is Matching",
					"'FREQ_CODE' is Matching \n'FREQ_CODE':"+participants.getString("freq_code"), false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'FREQ_CODE' is Matching",
					"'FREQ_CODE' is Not Matching\n'Expected:"+Freequency+"\nActual"+participants.getString("freq_code"), false);
	
		
		if(participants.getString("trf_type_code").equalsIgnoreCase("RE BAL"))
			Reporter.logEvent(Status.PASS,
					"Verify 'TRF_TYPE_CODE' is Matching",
					"'TRF_TYPE_CODE' is Matching \n'TRF_TYPE_CODE':"+participants.getString("trf_type_code"), false);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify 'TRF_TYPE_CODE' is Matching",
					"'TRF_TYPE_CODE' is Not Matching\n'Expected:RE BAL\nActual"+participants.getString("trf_type_code"), false);
	
		
		}else 
			{
			Reporter.logEvent(Status.FAIL,
					"Verify New Row is Cretaed in 'TRF_BASIC' Table with Confirmation No:"+confirmationNo,
					"New Row is not Cretaed in 'TRF_BASIC' Table with Confirmation No:"+confirmationNo, false);
			}
		return isRowCreated;

	}
	
	/**
	 * <pre>
	 * Method to Verify the FundName and Allocated Percentage is Same in Confirmation Page
	 * Returns String
	 * </pre>
	 * 
	 *
	 */
	public synchronized void VerifyAllocatedPecentageForFunds(Map<String,String> mapInvestmentOptions) {
	String  actualPercentage=null;
	String  expectedPercentage=null;
	
	
	
	 	Set<String> keys = mapInvestmentOptions.keySet();
	 	for(String key: keys){
        System.out.println(key);

          WebElement txtAllocationPercent = Web.getDriver().findElement(By
  				.xpath(txtFutureFundAllocationPercentage.replace("Investment Option",
  						key)));
          actualPercentage=txtAllocationPercent.getText().toString().trim();
          expectedPercentage=mapInvestmentOptions.get(key);
          expectedPercentage=expectedPercentage+"%";
          if(expectedPercentage.contains(actualPercentage)){
        	  
        	  Reporter.logEvent(Status.PASS,
  					"Verify Allocated Percentage For Fund '"+key+"' is Same in Confirmation Page ",
  					"Allocated Percentage For Fund '"+key+"' is Same in Confirmation Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, false);
  	
  		
  		}else 
  			{
  			Reporter.logEvent(Status.FAIL,
  					"Verify Allocated Percentage For Fund '"+key+"' is Same in Confirmation Page ",
  					"Allocated Percentage For Fund '"+key+"' is Not Same in Confirmation Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, false);
  	
  			}
          
         }
	}
         
  		
	/**
	 * <pre>
	 * Method to Verify the  Allocated Percentage for Fund Name equal to 100 in Confirmation Page for HMDI flow
	 *
	 * </pre>
	 * 
	 *
	 */
	public void VerifyAllocatedPecentageForHMDIFunds() {
		if(getAllocatedPecentageForFund(targetYearFund).contains("100")){
  			Reporter.logEvent(Status.PASS,
  					"Verify Investment Percentage is Equals to 100",
  					"Investment Percentage is Matching and Equals to 100", false);

  		}
  		else 
  			{
  			Reporter.logEvent(Status.FAIL,
  							"Verify Investment Percentage is Equals to 100",
  				"Investment Percentage is not Matching", true);
  			}
	}
	/**
	 * <pre>
	 * Method to Verify the Page Header is Displayed or Not
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyPageHeaderIsDisplayed(String webElement) {
		
		WebElement webelement= getWebElement(webElement);
	 String pageHeader=webelement.getText().toString().trim();
		if (Web.isWebElementDisplayed(webelement)) {
			lib.Reporter.logEvent(Status.PASS, "Verify "+pageHeader 
					+ " Page  is Displayed", pageHeader 
					+ " Page  is Displayed",
					true);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "Verify "+pageHeader 
					+ " Page  is Displayed", pageHeader 
					+ " Page  is Not Displayed",true);
			throw new Error(webElement+" is not displayed");
		}
	

	}
	/**
	 * <pre>
	 * Method to Verify Participant is Allowed to select only one fund.
	 * This method is applicable for Target Date Fund And Risk Based Fund
	 *
	 * </pre>
	 * 
	 *
	 */
public void verifyParticipantisAllowedToSelectOnlyOneFund(String selectedFund) {
		
	Map<String, String> mapInvestmentOption = new HashMap<String, String>();
	
	mapInvestmentOption=getCurrentFunds();
	
	if(mapInvestmentOption.size()==1){
		if(getCurrentFunds().containsValue(selectedFund)){
		Reporter.logEvent(Status.PASS,
				"Verify Participant is allowed to select only one fund",
				"Participant is able to select only one fund \n Selected Fund:"+selectedFund, false);
	}
	else{
		Reporter.logEvent(Status.FAIL,
				"Verify Fund is Matching in Review Page ",
				"Funds are not matching in Review Page\nExpected:"+selectedFund+"", true);
	}
	}
	else{
		Reporter.logEvent(Status.FAIL,
				"Verify Participant is allowed to select only one fund",
				"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOption.size(), true);
	}

	}

/**
 * <pre>
 * Method to Verify the Confirmation Message for Change Future Investments Flow
 *
 * </pre>
 * 
 *
 */
public void verifyConfirmationMessageForChangeFutureFlow() {
	List<String> dates=getInvestmentsSubmissionTime();
	String date=txtConfirmationDate.getText().toString().trim();
	if(date.equalsIgnoreCase(dates.get(0))){
		date=dates.get(0);
	}
	else{
		date=dates.get(1);
	}
	String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
	
	String actualConfirmationMsg=getWebElementText("Text Confirmation");
	if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
		
		Reporter.logEvent(Status.PASS,
				"Verify Confirmation Message is Displayed in Confirmation Page",
				"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
	}
	else{
		Reporter.logEvent(Status.INFO,
				"Verify Confirmation Message is Displayed in Confirmation Page",
				"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
	}

	}


/**
 * <pre>
 * Method to Verify the The Funds Selected are same in Review and Confirmation Pages
 *
 * </pre>
 * 
 *
 */
public void verifyFundsinReviewAndConfirmationPageAreMatching(Map<String, String> fundsinReviewPage,Map<String, String> fundsinConfirmationPage) {
	
	if(fundsinReviewPage.equals(fundsinConfirmationPage)){
		Reporter.logEvent(Status.PASS,
				"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
				"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
	}
	else 
		{
		Reporter.logEvent(Status.FAIL,
				"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
				"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
		}


	}
/**
 * <pre>
 * Method to Verify the Error Page is Displayed
 *
 * </pre>
 * 
 *
 */
public void verifyErrorPageDisplayed() {
	
	Web.waitForElement(GenericErrorMsg);
	if(lib.Web.isWebElementDisplayed( GenericErrorMsg,true))
		Reporter.logEvent(Status.PASS, "Verify 'You are not authorised to view', Page is displayed", "'You are not authorised to view', Page is displayed", false);
	else
		Reporter.logEvent(Status.FAIL, "Verify 'You are not authorised to view', Page is displayed", "'You are not authorised to view', Page is not displayed", true);
		

	}
/**
 * <pre>
 * Method to select the Model Portfolio  for Change Future Allocation-HMDI
 * Returns String
 * </pre>
 * 
 * @return String - modelPortfolioFund
 */
public String selectModelPortfolioFund() {

    
      if (Web.isWebElementsDisplayed(inpTargetDateFund)) {
		if(inpTargetDateFund.size()>=1){
			Web.clickOnElement(inpTargetDateFund.get(0));
			targetYearFund=txtTargetDateFund.get(0).getText().toString().trim();
			Reporter.logEvent(Status.PASS,
					"Verify 'Model Portfolio Fund' is selected",
					"Selected 'Model Portfolio Fund' is:"+targetYearFund, true);
			
		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
					"Verify 'Model Portfolio Fund' is selected",
					"'Model Portfolio Fund is not selected", true);
			}
		
	}

      if (Web.isWebElementDisplayed(tableAssetClass))
    	  Reporter.logEvent(Status.PASS,
					"Verify 'Asset Table' is Displayed",
					"Asset Table' is Displayed", true);
      
      else
    	  Reporter.logEvent(Status.FAIL,
					"Verify 'Asset Table' is Displayed",
					"Asset Table' is Not Displayed", true);  
    
      Web.clickOnElement(btnContinueToTargetDateFund);
      Web.waitForElement(hdrReviewYourChanges);
	return targetYearFund;

}


	public List<String> getInvestmentsSubmissionTime(){
		List<String> timeStamp=new ArrayList<String>();
		String time =null;
		String time1 =null;
		String minute=null;
		String minute1=null;
		String hour=null;
		
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.HOUR)==0){
			hour="12";
		}
		else{
			hour=Integer.toString(cal.get(Calendar.HOUR));
		}
		if(cal.get(Calendar.MINUTE)<=9){
			 minute="0"+Integer.toString(cal.get(Calendar.MINUTE));
			 minute1="0"+Integer.toString(cal.get(Calendar.MINUTE)-1);
		}
		else{
			 minute=Integer.toString(cal.get(Calendar.MINUTE));
			 minute1=Integer.toString(cal.get(Calendar.MINUTE)-1);
		}
		 time=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
					cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
					+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
					Integer.toString(cal.get(Calendar.YEAR))+","+" "+
					hour+":"+minute+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
		 
		 time1=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
				cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
				+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
				Integer.toString(cal.get(Calendar.YEAR))+","+" "+
				hour+":"+minute1+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
		
		 timeStamp.add(time);
		 timeStamp.add(time1);
		 
		 System.out.println("TIME STAMP1"+time);
		 System.out.println("TIME STAMP2"+time1);
		 
		return timeStamp;
		
	}
	/**
	 * <pre>
	 * Method to Verify the Confirmation Message for Model Portfolio Investments Flow
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyConfirmationMessageForModelPortfolio() {
		List<String> dates=getInvestmentsSubmissionTime();
		String date=txtConfirmationDate.getText().toString().trim();
		if(date.equalsIgnoreCase(dates.get(0))){
			date=dates.get(0);
		}
		else{
			date=dates.get(1);
		}
		String expectedConfirmationMsg="Your investment allocation request for current account balance, and future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
		
		String actualConfirmationMsg=getWebElementText("Text Confirmation");
		if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
			
			Reporter.logEvent(Status.PASS,
					"Verify Confirmation Message is Displayed in Confirmation Page",
					"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
		}
		else{
			Reporter.logEvent(Status.INFO,
					"Verify Confirmation Message is Displayed in Confirmation Page",
					"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
		}

		}
	/**
	 * <pre>
	 * Method to Verify Select Investment Button is displayed for Money Type Grouping
	 * 
	 * </pre>
	 * 
	 * 
	 */
	public void verifyInvestmentButtonDisplayedforMoneyTypeGroup(String moneyTypeGroup) {
		
		try
		{
			WebElement btnSelectInvestment = Web.getDriver().findElement(By		
				.xpath(selectInvestment.replace("Money Type Grouping",moneyTypeGroup)));
			if(Web.isWebElementDisplayed(btnSelectInvestment)){
				
				Reporter.logEvent(Status.PASS,
						"Verify 'Select Investments' Button is Displayed for "+moneyTypeGroup,
						"'Select Investments' Button is Displayed for "+moneyTypeGroup, false);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify 'Select Investments' Button is Displayed for "+moneyTypeGroup,
						"'Select Investments' Button is Not Displayed for "+moneyTypeGroup, true);
				}
		}
          catch(NoSuchElementException e){
        	 
          }
  
	}
	
	/**
	 * <pre>
	 * Method to click on Select Investment Button is displayed for Money Type Grouping
	 * 
	 * </pre>
	 * 
	 * 
	 */
	public void clickSelectInvestmentButtonforMoneyTypeGroup(String moneyTypeGroup) {
		
		try
		{
			WebElement btnSelectInvestment = Web.getDriver().findElement(By		
				.xpath(selectInvestment.replace("Money Type Grouping",moneyTypeGroup)));
			if(Web.isWebElementDisplayed(btnSelectInvestment)){
				
				Web.clickOnElement(btnSelectInvestment);
				}
		}
          catch(NoSuchElementException e){
        	 
          }
  
	}
	

	public boolean isInvestmentOptiondDisplayed(String investmentOption) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", investmentOption)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify Investment Option " + investmentOption
					+ "  is Displayed", "Investment Option '"+investmentOption + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "Verify Investment Option " + investmentOption
					+ "  is Displayed", "Investment Option'"+investmentOption + "' is Not Displayed", false);
			throw new Error(investmentOption+" is not displayed");
		}
	
return isTextDisplayed;
	}
	/**
	 * <pre>
	 * Method to Verify Money Type Grouping is Displayed in Enrollment Flow
	 * Returns boolean
	 * </pre>
	 * 
	 * @return boolean - isDisplayed
	 */
	public boolean verifyMoneyTypeGroupDisplayed(String moneyTypeGrouping) {
	
		boolean isDisplayed=false;
		
		try
		{
			WebElement textMoneyTypeGrouping = Web.getDriver().findElement(By		
				.xpath(txtMoneyType.replace("Money Type Grouping",moneyTypeGrouping)));
			if(textMoneyTypeGrouping.isDisplayed())
				isDisplayed=true;
		}
          catch(NoSuchElementException e){
        	  isDisplayed=false;
          }
  		
  		return isDisplayed;

	}
	/**
	 * This Method to select the investment options and enter the percentage for Enrollment Flow
	 * @author srsksr
	 *@param noOfInvestmentoptions
	 *@param percent
	 *
	 */
	public synchronized Map<String, String> addInvestmentsforEnrollmentFlow(int noOfInvestmentoptions,String[] percent) throws InterruptedException {
		try{
			mapInvestmentOptions=new HashMap<String, String>();
		    mapCurrentInvestmentOptionsPecentage=new HashMap<String, String>();
		    Web.waitForElements(inpInvestmentOptionFutureAllocation);
			
			int noOfRows = inpInvestmentOptionFutureAllocation.size();
			if (noOfRows >= noOfInvestmentoptions) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options are available ", false);
				for(int i=0;i<noOfInvestmentoptions;i++){
					int j=i+1;
					mapInvestmentOptions.put("investmentFundName"+i, txtInvestmentOption.get(i).getText());
				
				Web.clickOnElement(inpInvestmentOptionFutureAllocation.get(i));
				
				Reporter.logEvent(Status.INFO, "Selected Investment Option",
						"Selected Investment Option"+j+" : \n" 
								+ mapInvestmentOptions.get("investmentFundName"+i) , true);
				}
			
				
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						noOfInvestmentoptions+"Investment options are not available ", true);

			Web.clickOnElement(btnAdd);
		
			Web.waitForElement(lnkAddViewAllFundsFuture);
			
			for(int i=0;i<noOfInvestmentoptions;i++){
		WebElement inptAllocationPercent = Web.getDriver().findElement(By
				.xpath(inputAllocationPercentageFutureEnroll.replace("Investment Option",
						mapInvestmentOptions.get("investmentFundName"+i))));
		Web.setTextToTextBox(inptAllocationPercent, percent[i]);
		Thread.sleep(1000);
		mapCurrentInvestmentOptionsPecentage.put(mapInvestmentOptions.get("investmentFundName"+i), inptAllocationPercent.getAttribute("value"));
			}
		Web.waitForPageToLoad(Web.getDriver());
		
		if(txttotalInvestmentPercent.getText().contains("100")){
			Reporter.logEvent(Status.PASS,
					"Verify Investment Percentage is Entered and Equals to 100",
					"Investment Percentage is Matching and Equals to 100", true);

		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
							"Verify Investment Percentage is Entered and Matching",
				"Investment Percentage is not Matching", true);
			}
		btnSubmitChangeFutureFund.click();
		if(Web.isWebElementDisplayed(btnAlertContinue, true)){
			Web.clickOnElement(btnAlertContinue);
		}
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElements(txtCurrentFundsforChangeFutureFlow);
		}
		catch(Exception e){
			
		}
		return mapCurrentInvestmentOptionsPecentage;
	}
	
	
	/**<pre> Method to update Employer Directed Funds to Normal.
	 *.</pre>
	 * @param planId - 
	 * @param 
	 * @return - 
	 * @throws Exception 
	 */
	public void removeEmployerDirectedRule(String planId) throws Exception{
		String[] sqlQuery=null;
		
		sqlQuery = Stock.getTestQuery("updateBackEmployerDirecyedIndicator");
	
	
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], planId);
		
		
	}
	
	 public void verifyRestrictedFundsWarningSymbol(){
		
		 if (Web.isWebElementDisplayed(tblRestrictedFunds)) {
			Reporter.logEvent(Status.PASS, "Verify Restricted Funds Table is Displayed",
						"Restricted Funds Table is Displayed",
						true);
			
			
			Web.clickOnElement(restrictedFundWarningIcon);
			Web.waitForElement(modalDialogRestrictedFund);
			 if (Web.isWebElementDisplayed(modalDialogRestrictedFund)) {
					Reporter.logEvent(Status.PASS, "Verify Evaluation Message for Restricted Funds is Displayed",
								"Evaluation Message for Restricted Funds is Displayed",
								true);
					Web.clickOnElement(btnClose);
					Web.waitForElement(restrictedFundWarningIcon);
			 }	
			 else {
						
						Reporter.logEvent(Status.FAIL, "Verify Evaluation Message for Restricted Funds is Displayed",
								"Evaluation Message for Restricted Funds is Not Displayed",
								true);
			
			}
		 }else {
						
				Reporter.logEvent(Status.FAIL, "Verify Restricted Funds Table is Displayed",
						"Restricted Funds Table is Not Displayed",
						true);
			}
		  
			
	}
	 
	 public void verifyUnRestrictedFundsWarningSymbol(){
			
		 if (Web.isWebElementDisplayed(unRestrictedFundWarningIcon)) {
				Reporter.logEvent(Status.PASS, "Verify Warning Symbol is Displayed for UnRestricted Fund",
							"Warning Symbol is Displayed for UnRestricted Fund",
							true);
				
				
			Web.clickOnElement(unRestrictedFundWarningIcon);
			Web.waitForElement(modalDialogRestrictedFund);
			 if (Web.isWebElementDisplayed(modalDialogRestrictedFund)) {
					Reporter.logEvent(Status.PASS, "Verify Evaluation Message for Un Restricted Funds is Displayed",
								"Verify Evaluation Message for Un Restricted Funds is Displayed",
								true);
					Web.clickOnElement(btnClose);
					Web.waitForElement(restrictedFundWarningIcon);
			 }	
			 else {
						Reporter.logEvent(Status.FAIL, "Verify Evaluation Message for Un Restricted Funds is Displayed",
								"Verify Evaluation Message for Un Restricted Funds is Not Displayed",
								true);
			
			}
		 }
	
			 else {
					
					Reporter.logEvent(Status.FAIL, "Verify Warning Symbol is Displayed for UnRestricted Fund",
							"Warning Symbol is Not Displayed for UnRestricted Fund",
							true);
			
	}
}
	 
	 /**
	  *  Method to Verify  Review Changes page for Smart Restriction flow for Future Investments
	 	  */
	 public synchronized void VerifySmartRestrictionReviewChangesPageforFutureInvestments() {
			
			if(Web.isWebElementDisplayed(fromSectionFutureInvestments)){
				Reporter.logEvent(Status.PASS, "Verify From Section For Future Investments is Displayed",
						"From Section is Displayed for Future Investments",
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Verify From Section For Future Investments is Displayed",
						"From Section is Not Displayed for Future Investments",
						true);
			}
			if(Web.isWebElementDisplayed(toSectionFutureInvestments)){
				Reporter.logEvent(Status.PASS, "Verify TO Section For Future Investments is Displayed",
						"TO Section is Displayed for Future Investments",
						false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Verify TO Section For Future Investments is Displayed",
						"TO Section is Not Displayed for Future Investments",
						false);
			}
			
			
			
	 }
	 
	 /**
	  *  Method to Verify  Review Changes page for Smart Restriction flow for Rebalance Investments
	  */
	 public synchronized void VerifySmartRestrictionReviewChangesPageforRebalanceInvestments() {
			
		
			if(Web.isWebElementDisplayed(fromSectionRebalanceInvestments)){
				Reporter.logEvent(Status.PASS, "Verify From Section For Rebalance Investments is Displayed",
						"From Section is Displayed for Rebalance Investments",
						false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Verify From Section For Rebalance Investments is Displayed",
						"From Section is Not Displayed for Rebalance Investments",
						false);
			}
			if(Web.isWebElementDisplayed(toSectionRebalanceInvestments)){
				Reporter.logEvent(Status.PASS, "Verify TO Section For Rebalance Investments is Displayed",
						"TO Section is Displayed for Rebalance Investments",
						false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Verify TO Section For Rebalance Investments is Displayed",
						"TO Section is Not Displayed for Rebalance Investments",
						false);
			}
			
	 }
			
	 /**
	  * Method to Verify the FundName and Allocated Percentage is Same in Review page for Smart Restriction flow
	  * @param mapInvestmentOptions
	  * @param investmentType - Future Investment/Rebalance Investment
	  */
		public synchronized void VerifyAllocatedPecentageForFunds(Map<String,String> mapInvestmentOptions,String investmentType) {
		String  actualPercentage=null;
		String  expectedPercentage=null;
		
		
		if(investmentType.equalsIgnoreCase("Future Investment")){
			if(listFutureInvestmentsToFunds.size()==mapInvestmentOptions.size()){
		 	Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
	        System.out.println(key);

	          WebElement txtAllocationPercent = Web.getDriver().findElement(By
	  				.xpath(smartRestrictionFutureFundAllocationPercentag.replace("Investment Option",
	  						key)));
	          actualPercentage=txtAllocationPercent.getText().toString().trim();
	          expectedPercentage=mapInvestmentOptions.get(key);
	          expectedPercentage=expectedPercentage+"%";
	          if(expectedPercentage.contains(actualPercentage)){
	        	  
	        	  Reporter.logEvent(Status.PASS,
	  					"Verify Allocated Percentage For Future Investment Fund '"+key+"' is Same in Review Page ",
	  					"Allocated Percentage For Future Investment Fund '"+key+"' is Same in Review Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, false);
	  	
	  		
	  		}else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  					"Verify Allocated Percentage For Future Investment Fund '"+key+"' is Same in Review Page ",
	  					"Allocated Percentage For Future Investment Fund '"+key+"' is Not Same in Review Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, true);
	  	
	  			}
		 	}
			}
		 	else{
		 		Reporter.logEvent(Status.FAIL,
	  					"Verify Future Investment Options in To Section are Matching",
	  					"Future Investment Options in To Section are Not Same in Review Page \nExpected Funds:"+mapInvestmentOptions.size()+"\nActual Funds:"+listFutureInvestmentsToFunds, true);
		 	}
	          
	         }
		
		if(investmentType.equalsIgnoreCase("Rebalance Investment")){
			if(listRebalanceInvestmentsToFunds.size()==mapInvestmentOptions.size()){
		 	Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
	        System.out.println(key);

	          WebElement txtAllocationPercent = Web.getDriver().findElement(By
	  				.xpath(smartRestrictionRebalanceFundAllocationPercentag.replace("Investment Option",
	  						key)));
	          actualPercentage=txtAllocationPercent.getText().toString().trim();
	          expectedPercentage=mapInvestmentOptions.get(key);
	          expectedPercentage=expectedPercentage+"%";
	          if(expectedPercentage.contains(actualPercentage)){
	        	  
	        	  Reporter.logEvent(Status.PASS,
	  					"Verify Allocated Percentage For Rebalance Investment Option '"+key+"' is Same in Review Page ",
	  					"Allocated Percentage For Rebalance Investment Option '"+key+"' is Same in Review Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, false);
	  	
	  		
	  		}else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  					"Verify Allocated Percentage For Rebalance Investment Option '"+key+"' is Same in Review Page ",
	  					"Allocated Percentage For Rebalance Investment Option '"+key+"' is Not Same in Review Page \nExpected Pecentage:"+expectedPercentage+"\nActual Pecentage:"+actualPercentage, true);
	  	
	  			}
		 	}
			}
		 	else{
		 		Reporter.logEvent(Status.FAIL,
	  					"Verify Rebalance Investment Options in To Section are Matching",
	  					"Rebalance Investment Options in To Section are Not Same in Review Page \nExpected Funds:"+mapInvestmentOptions.size()+"\nActual Funds:"+listFutureInvestmentsToFunds, true);
		 	}
	          
	         }
		}
		
		
		public void verifyEvaluationMessageinReviewPage(){
			
			 if (Web.isWebElementDisplayed(unRestrictedFundWarningIconReviewPage)) {
				Reporter.logEvent(Status.PASS, "Verify Warning Symbol Displayed for UnRestricted Fund",
							"Warning Symbol Displayed for UnRestricted Fund",
							true);
				
				
				Web.clickOnElement(unRestrictedFundWarningIconReviewPage);
				Reporter.logEvent(Status.INFO, "Clicked on Warning Symbol",
						"",
						false);
				Web.waitForElement(modalDialogRestrictedFund);
				 if (Web.isWebElementDisplayed(modalDialogRestrictedFund)) {
						Reporter.logEvent(Status.PASS, "Verify Evaluation Message is Displayed",
									"valuation Message is Displayed",
									true);
						Web.clickOnElement(btnClose);
						Web.waitForElement(unRestrictedFundWarningIconReviewPage);
				 }	
				 else {
							
							Reporter.logEvent(Status.FAIL, "Verify Evaluation Message is Displayed",
									"Evaluation Messageis Not Displayed",
									true);
				
				}
			 }else {
							
					Reporter.logEvent(Status.INFO, "Verify Warning Symbol Displayed for UnRestricted Fund",
							"Warning Symbol is Not Displayed for UnRestricted Fund",
							true);
				}
			  
				
		}
		 
		
		
	     /**
	  	 * <pre>
	  	 * Method to Verify the Confirmation Message for Smart Restriction Flow
	  	 *
	  	 * </pre>
	  	 */
	  	public void verifyConfirmationMessageForSmartRestriction() {
	  		List<String> dates=getInvestmentsSubmissionTime();
	  		String date=smartestrictionConfirmationdate.getText().toString().trim();
	  		if(date.equalsIgnoreCase(dates.get(0))){
	  			date=dates.get(0);
	  		}
	  		else{
	  			date=dates.get(1);
	  		}
	  		String expectedConfirmationMsg="Your investment allocation request for current account balance, and future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
	  		
	  		String actualConfirmationMsg=getWebElementText("Text Confirmation Smart Restriction");
	  		if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
	  			
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Confirmation Message is Displayed in Confirmation Page",
	  					"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
	  		}
	  		else{
	  			Reporter.logEvent(Status.INFO,
	  					"Verify Confirmation Message is Displayed in Confirmation Page",
	  					"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
	  		}

	  		}  
	  	
	  	/**
	  	 * <pre>
	  	 * Method to Verify Confirmation page is having 2 confirmation numbers for Smart Restriction Flow
	  	 *
	  	 * </pre>
	  	 */
	  	public List<String> verifyConfirmationNumbersForSmartRestriction() {
	  		
	  		List<String> ConfirmationNos=new ArrayList<String>();
	  		
	  		try{	
	  			
	  		String txtConfirmationNumbers=smartestrictionConfirmationNos.getText().toString().trim();
	  	String[] txtConfirmationNos=txtConfirmationNumbers.split("is");
	  	
	  	String futureFundsConfirmationNo=txtConfirmationNos[1].split(",")[0].toString().trim();
	  	String rebalanceConfirmationNo=txtConfirmationNos[2].replace(".", "").toString().trim();
	  	
	  		if(futureFundsConfirmationNo.length()==9 && rebalanceConfirmationNo.length()==9  ){
	  			ConfirmationNos.add(futureFundsConfirmationNo);
	  			ConfirmationNos.add(rebalanceConfirmationNo);
	  			Reporter.logEvent(Status.PASS,
	  					"Verify 2 Confirmation Numbers Displayed in Confirmation Page",
	  					"2 Confirmation Numbers Displayed in Confirmation Page\nConfirmation Number for Future Investments:"+futureFundsConfirmationNo+"\nConfirmation Number for Rebalance:"+rebalanceConfirmationNo, true);
	  		}
	  		else{
	  			Reporter.logEvent(Status.FAIL,
	  					"Verify 2 Confirmation Numbers Displayed in Confirmation Page",
	  					"2 Confirmation Numbers are not Displayed in Confirmation Page\nConfirmation Number for Future Investments:"+futureFundsConfirmationNo+"\nConfirmation Number for Rebalance:"+rebalanceConfirmationNo, true);

	  		}
	  		}
	  		catch(Exception e){
	  		e.printStackTrace();	
	  		}
	  		return ConfirmationNos;
	  		}  
	  	
	  	
	
		public void submitDollarCostAverageTransaction() {

			try {
				
				clickChangeMyInvestmentButton();
				choseInvestmentOption("Dollar Cost");
				Web.clickOnElement(btnContinue1);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				
				Actions keyBoard = new Actions(Web.getDriver());
				
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(iframeLegacyFeature);
			
				if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
					Web.getDriver().switchTo().frame(iframeLegacyFeature);
					else{
						Web.waitForElement(iframeLegacyFeature);			
					Web.getDriver().switchTo().frame(iframeLegacyFeature);
					}
					WebElement freq = this.getWebElement("Once");
					WebElement date = this.getWebElement("Today");
					Web.clickOnElement(freq);
					
					Web.clickOnElement(date);
					
					btnContinueToNextStep.click();
				
				    radioDoNoTerminate.click();
				
				lstChkInvestmentOptionDollarCost.get(0).click();

				
				btnContinueToNextStep.click();
				Web.waitForElement(txtTransferAmtDollarCost);
				Web.setTextToTextBox(txtTransferAmtDollarCost, "1000");
				
				btnContinueToNextStep.click();
				Common.waitForProgressBar();
				
				Web.waitForElement(lstChkInvestmentOptionDollarCost.get(0));
				lstChkInvestmentOptionDollarCost.get(0).click();
				
				btnContinueToNextStep.click();
				
				Common.waitForProgressBar();
				
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
				((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
				Web.waitForElements(lstInvestmentOptionsDollarCost);
				
				Web.waitForElement(lstInvestmentPercentDollarCost);
				Web.clickOnElement(lstInvestmentPercentDollarCost);
				keyBoard.sendKeys(Keys.BACK_SPACE).perform();
				Web.setTextToTextBox(lstInvestmentPercentDollarCost,
						"100");
				
				Web.getDriver().switchTo().defaultContent();
				Web.getDriver().switchTo().frame(iframeLegacyFeature);
				keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
				Web.setTextToTextBox(inpTotal,"100");
				keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
						
				// to handle allert
				for(int i=0;i<3;i++){
		            if(Common.isAlerPresent()){
		            Common.HandlePopAlert();
		            Web.clickOnElement(lstInvestmentPercentDollarCost);
		            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
		            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
		            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
		            Web.setTextToTextBox(lstInvestmentPercentDollarCost,"100");  
		            keyBoard.sendKeys(Keys.TAB).perform();                  
		            keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
		            Web.clickOnElement(inpTotal);
		            keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
		            }
		    }

				Web.clickOnElement(btnContinueToNextStep);
				Web.getDriver().switchTo().defaultContent();
			}
				  catch (Exception e1) {
					e1.printStackTrace();
				}
			
		}	
	  	
		public void submitRebalanceAllocationViaLeftNav() {

			try {
				
				clickChangeMyInvestmentButton();
				choseInvestmentOption("Rebalance Current Balance");
				
				Web.clickOnElement(btnContinue1);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(txtHowWouldLikeToInvest);
				
				Web.clickOnElement(btnChooseIndividualFunds);
				Web.waitForElement(txtRebalanceYourPortfolio);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
					
				Web.waitForElement(lnkAddViewAllFunds);
				Web.clickOnElement(lnkAddViewAllFunds);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(tableAllocationFund);
		
				String[] percentage={"50","50"};
				addInvestments(2,percentage);
				Web.waitForElement(hdrReviewYourChanges);
				
				Web.clickOnElement(btnConfirm);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			
			}
				  catch (Exception e1) {
					e1.printStackTrace();
				}
			
		}	 	
	  	
		public void submitFullRebalanceAllocationViaSmartRestriction() {

			try {
				
				Web.waitForElement(btnChooseIndividualFunds);
				Web.clickOnElement(btnChooseIndividualFunds);
				Web.waitForElement(txtBuildYourOwnPortfolio);
				
				Web.clickOnElement(lnkAddViewAllFundsFuture);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(tableAllocationFund);
				String[] percentage={"50","50"};
				addInvestments(2,percentage);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(hdrRebalanceYourPortfolio);
				
				Web.clickOnElement(lnkAddViewAllFunds);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Web.waitForElement(tableAllocationFund);
				String[] percentage1={"100"};
				addInvestments(1,percentage1);
				Web.waitForElement(hdrReviewYourChanges);
				
				Web.clickOnElement(btnConfirm);
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(15000);
				Web.waitForElement(hdrConfirmation);
				
			}
				  catch (Exception e1) {
					e1.printStackTrace();
				}
			
		}	
		
		/**
		 * <pre>
		 * Method to Verify Money Type Label is Displayed in My Investments Page
		 * Returns boolean
		 * </pre>
		 * 
		 * @return boolean - isDisplayed
		 */
		public boolean verifyMoneyTypeLabelIsDisplayed(String moneyTypeGrouping) {
		
			boolean isDisplayed=false;
			
			try
			{
				WebElement textMoneyTypeGrouping = Web.getDriver().findElement(By		
					.xpath(lblMoneyTypeGrouping.replace("Money Type Grouping",moneyTypeGrouping)));
				if(textMoneyTypeGrouping.isDisplayed())
					isDisplayed=true;
			}
	          catch(NoSuchElementException e){
	        	  isDisplayed=false;
	          }
	  		
	  		return isDisplayed;

		}
}