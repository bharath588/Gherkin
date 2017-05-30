package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Choose;
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

import core.framework.Globals;
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;

public class ManageMyInvestment extends LoadableComponent<ManageMyInvestment> {

	private LoadableComponent<?> parent;
	String investmentFundName1;
	String investmentFundName2;
	String fromInvestmentOption;
	static String toInvestmentOption;
	String confirmationNo;
	static Map<String, String> mapInvestmentOptions = new HashMap<String, String>();
	static Map<String, String> mapCurrentInvestmentOptions = new HashMap<String, String>();

	// @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']")
	// private WebElement lblUserName;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//h1[text()[normalize-space()='My Investments']]")
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
	@FindBy(xpath = "//div[@class='dollarpercent']/a[contains(@class, 'percentbutton')]")
	private WebElement btnPercent;

	@FindBy(id = "transferFundFromTableId")
	private WebElement tblTransferFundFrom;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/thead/tr")
	private WebElement hdrTransferFundFromTable;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/tbody/tr/td/a")
	private WebElement lnkTransferFromInvestmentOption;
	@FindBy(xpath = "//input[contains(@id,'FromPercent')]")
	private List<WebElement> txtTransferFromPercent;

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
	@FindBy(xpath = "//span[@id='effectiveDate']") private WebElement txtTransactionDetails;
	@FindBy(xpath = "//*[@id='rebalanceConfirmationHeading']/i") private WebElement lblGreenCheck;
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
	
	@FindBy(xpath = ".//*[@id='portfolio-link']//span[text()[normalize-space()='Current']]")
	private WebElement lblCurrent;
	
	@FindBy(xpath = "//*[@id='view-all-funds' or @id='rebalance-view-all-funds']") private WebElement lnkAddViewAllFundsFuture;
	
	@FindBy(xpath = ".//a/span[@class='em-x-bold']") private WebElement btnRemoveInvestment;
	
	@FindBy(xpath = "//tr//td[contains(@class,'allocation-fund-name')]//a") private WebElement txtAllocationFundName;
	
	@FindBy(xpath = "//div[@class='row allocation-content']//p//strong") private WebElement lblConfirmationNoFutureFund;
	
	@FindBy(xpath = "//button[@id='target-year-fund-link']") private WebElement btnChooseTargetDateFund;
	
	@FindBy(xpath = "//h1[text()[normalize-space()='Select a target date fund']]")
	private WebElement txtSelectTargetDateFund;
	
	@FindBy(xpath = "//tr[contains(@ng-if,'targetYearFund')][./td[@class='allocation-fund-name-container'][not(span)]]//td//input")
	private List<WebElement> inpTargetDateFund;
	
	@FindBy(xpath = "//tr[contains(@ng-if,'targetYearFund')][./td[@class='allocation-fund-name-container'][not(span)]]//td[@class='allocation-fund-name-container']")
	private List<WebElement> txtTargetDateFund;
	
	@FindBy(xpath = "//a[@id='submitButton']")
	private WebElement btnContinueToTargetDateFund;
	
	@FindBy(xpath = "//button[@id='target-year-fund-link']//span[text()[normalize-space()='Current']]")
	private WebElement lblCurrentTagetDateFund;

	@FindBy(xpath = "//span[./span[text()[normalize-space()='Expand sources']]]")
	private WebElement lnkExpandSources;
	
	@FindBy(xpath = "//span[./span[text()[normalize-space()='Collapse sources']]]")
	private WebElement lnkCollapseSources;
	
	@FindBy(xpath = "//button[@id='risk-based-fund-link']") private WebElement btnChooseRiskBasedFund;
	
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
	
	String inputAllocationPercrntage="//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String buttonlock=".//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//button[contains(@class,'btn-link')]";
	String inputAllocationPercrntageFuture="//*[@id='allocation-current-funds-table' or @id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String txtFutureFundAllocationPercrntage="//tr[contains(@ng-repeat,'fund in currentFunds')][./td/a[contains(text(),'Investment Option')]]//td[2]";
	String slider="//input[contains(@aria-label,'Investment Option')]";
	String btnChangeInvestments="//div[./h2[contains(text(),'Money Type Grouping')]]//button[contains(text(),'Change My Investments')]";
	String txtMoneyTypeGrouping="//div[./h2[contains(text(),'Money Type Grouping')]]";
	private String textField="//*[contains(text(),'webElementText')]";
	String choice=null;
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

		/*//Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Manage My Investment Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {
			ResultSet strUserInfo=null;
			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
						+ strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String userLogedIn = this.lblUserName.getText();
		*/
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
				((LandingPage) this.parent).clickOnGuidanceLink();
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
		if (fieldName.trim().equalsIgnoreCase("Header Select Target Date Fund")) {
			return this.txtSelectTargetDateFund;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Select Target Date Fund")) {
			return this.txtSelectTargetDateFund;
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

	public void fundToFundTransfer(String fromPercent, String toPercent) {
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
			Web.setTextToTextBox(txtTransferFromPercent.get(0), fromPercent);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			fromInvestmentOption = lnkTransferFromInvestmentOption.getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund From' Table is displayed",
					"Table is not displayed", true);

		if (Web.isWebElementDisplayed(tblTransferFundTo)) {
			Reporter.logEvent(Status.INFO,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is displayed", true);
			Web.setTextToTextBox(txtTransferToPercent.get(1), toPercent);
			toInvestmentOption = lnkTransferToInvestmentOption.get(1).getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is not displayed", true);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		Web.waitForElement(btnReviewTransfer.get(1));
		btnReviewTransfer.get(1).click();
		// Web.waitForElement(btnPreValidationOK);
		// btnPreValidationOK.click();
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
	public void addInvestments(int noOfInvestmentoptions,String[] percent) throws InterruptedException {
		try{
		    mapInvestmentOptions.clear();
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
				.xpath(inputAllocationPercrntageFuture.replace("Investment Option",
						mapInvestmentOptions.get("investmentFundName"+i))));
		Web.setTextToTextBox(inptAllocationPercent, percent[i]);
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
	
          mapInvestmentOptions.clear();
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
	
          String targetYearFund="";
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
  				.xpath(txtFutureFundAllocationPercrntage.replace("Investment Option",
  						fundName)));
          percentage=txtAllocationPercent.getText().toString().trim();
  		
  		return percentage;

	}
	
	/**
	 * This Method to select the investment options and enter the percentage
	 * @author srsksr
	 *@param noOfInvestmentoptions
	 *@param percent
	 *
	 */
	public Map<String,String> selectInvestmentOptions(int noOfInvestmentoptions) throws InterruptedException {
		try{
		    mapInvestmentOptions.clear();
			
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
	 */
	public boolean verifySubmitButtonisEnabled() {
	
		boolean isEnabled=false;
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
	

		String date=getInvestmentsSubmissionTime();
		String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible. Your account will automatically rebalance "+frequencyType+".";
		
		String actualConfirmationMsg=txtTransactionDetails.getText().toString().trim();
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
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			throw new Error(fieldName+" is not displayed");
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
	public String getRebalanceConfirmation() {
	
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
	public String getInvestmentsSubmissionTime(){
		String time =null;
		String minute=null;
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.MINUTE)<=9){
			 minute="0"+Integer.toString(cal.get(Calendar.MINUTE));
			
		}
		else{
			 minute=Integer.toString(cal.get(Calendar.MINUTE));
		}
		 time=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
				cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
				+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
				Integer.toString(cal.get(Calendar.YEAR))+","+" "+
				Integer.toString(cal.get(Calendar.HOUR))+":"+minute+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
		 System.out.println("TIME STAMP"+time);
		return time;
		
	}
}
