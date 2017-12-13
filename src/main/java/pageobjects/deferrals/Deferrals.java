package pageobjects.deferrals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.general.LeftNavigationBar;
import lib.*;

import com.aventstack.extentreports.*;


public class Deferrals extends LoadableComponent<Deferrals> {
	
	//Declarations
			private LoadableComponent<?> parent;
			private static boolean waitforLoad = false;	
			public Float irs_limit;
			//public Float befor_tax;
			public float before_tax; 
			public float roth;
			public static String contrbution_rate;
			public static String payRoll_Date;
			//My Contributions Page
			@FindBy(xpath=".//h1[text()[normalize-space()='My Contributions']]") private WebElement lblMyContributions;
			@FindBy(xpath=".//table/thead/tr/th[1][text()[normalize-space()='Contribution']]")
			private WebElement tblhdrlblContribution;
			@FindBy(id="buttonAdd_STANDARD") private WebElement btnAddOrEditStandard;
			@FindBy(id="buttonEdit_STANDARD") private WebElement btnEditStandard;
			@FindBy(id="buttonAdd_AFTERTAX") private WebElement btnAddorEditAfterTax;
			@FindBy(id="buttonEdit_AFTERTAX") private WebElement btnEditAfterTax;
			@FindBy(id="buttonAdd_CATCHUP") private WebElement btnAddOrEditCatchUp;
			@FindBy(id="buttonEdit_CATCHUP") private WebElement btnEditCatchUp;
			@FindBy(id="buttonAdd_BONUS") private WebElement btnAddOrEditBonus;
			@FindBy(id="buttonEdit_BONUS") private WebElement btnEditBonus;
			@FindBy(id="buttonAdd_OTHER") private WebElement btnAddOrEditOther;
			@FindBy(id="buttonEdit_OTHER") private WebElement buttonEditOther;
			@FindBy(xpath=".//span[text()[normalize-space()='More Options']]") private WebElement lnkMoreOptions;
			@FindBy(xpath=".//span[text()[normalize-space()='Fewer Options']]") private WebElement lnkFewerOptions;
			
			//Add Contribution Page
			@FindBy(xpath=".//h1[text()[normalize-space()='Add a After-tax contribution']]") 
			private WebElement lblAddAfterTaxContribution;
			@FindBy(xpath=".//strong[text()[normalize-space()='Select another contribution rate']]") 
			private WebElement radioSelectAnotherContributionRate;	
			@FindBy(xpath="//strong[contains(text(),'Maximize to the company') or contains(text(),'Maximize to the Apple')]")
			private WebElement radioMaximizeToCompanyMatch;
			//input[@id='contributionTypeMaximizerCompanyMatch']")
			@FindBy(xpath=".//strong[contains(text(),'IRS limit')]")
			private WebElement radioMaximizeToIRSLimit;		
			@FindBy(xpath="//*[contains(@class, 'editable-text-trigger')]")
			private WebElement lnkContributionRate;
			@FindBy(id="contributionRateSlider-text-edit") private WebElement txtcontributionRateSlider;
			@FindBy(xpath=".//span[@class='valueAndEnd']/span[1]") private WebElement lnksliderValue;
//			@FindBy(xpath=".//button[text()[normalize-space()='Done']]") private WebElement btnDone;
			@FindBy(xpath="//button[@class='btn btn-primary reset-padding']") private WebElement btnDone;
//			@FindBy(xpath=".//label[text()[normalize-space()='Percent']]") private WebElement lnkPercent;
//			@FindBy(xpath="//span[@class='editable-text-trigger ng-binding']") private WebElement lnkPercent;
			@FindBy(xpath="//label[text()[normalize-space()='Percent']]") private WebElement lnkPercent;
			@FindBy(xpath=".//label[text()[normalize-space()='Dollar']]") private WebElement lnkDollar;
			@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBack;
//			@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Continue']]") private WebElement btnContinue;
			@FindBy(xpath="//button[text()[normalize-space()='Continue']]") private WebElement btnContinue;
//			@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Confirm & Continue']]") private WebElement btnConfirmAndContinue;
			@FindBy(xpath=".//*[text()[normalize-space()='Confirm & Continue']]") private WebElement btnConfirmAndContinue;
			
			@FindBy(xpath=".//*[@id='buttonContinue submit' and text()[normalize-space()='Confirm & Continue']]") private WebElement btnConfirmAndContinue1;
			@FindBy(xpath=".//button[text()[normalize-space()='My Contributions']]") private WebElement btnMyContributions;
			@FindBy(linkText="Plan Rules") private WebElement lnkPlanRules;
			@FindBy(xpath=".//header/label[@class='radio-inline panel-title']/span/strong") private List<WebElement> lstradioSelectContibution;
			@FindBy(xpath=".//strong[text()[normalize-space()='Split your contribution']]") private WebElement radioSplitContribution;
			@FindBy(xpath=".//strong[contains(text(),'Before Tax contribution')]") private WebElement radioBeforeTaxContribution;
			@FindBy(xpath=".//strong[contains(text(),'Roth contribution')]") private WebElement radioRothContribution;
			@FindBy(id="BEFORE") private WebElement txtSplitBeforeTax;
			@FindBy(id="ABONUS") private WebElement txtSplitAfterTaxBonus;
			@FindBy(id="RBONUS") private WebElement txtSplitRothBonus;
			@FindBy(id="BBONUS") private WebElement txtSplitBeforeBonus;
			@FindBy(id="ROTH") private WebElement txtSplitRothTax;
			@FindBy(id="AGEBEF") private WebElement txtSplitBeforeCatchup;
			@FindBy(id="AGERTH") private WebElement txtSplitRothCatchup;
			@FindBy(id="AGRVAR") private WebElement txtSplitAgeRothVariable;
			@FindBy(id="RTHVAR") private WebElement txtSplitRothVariable;
			@FindBy(id="BEFVAR") private WebElement txtSplitBeforeVariable;
			@FindBy(xpath=".//button[text()[normalize-space()='Continue']]") private WebElement btnContinueBeforeTaxSplitContribution;
			@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBackBeforeTaxSplitContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-percentage']") private WebElement txtCompanyMatchMyContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-amount']//p[1]") private WebElement txtCompanyMatchContributionAmount;
			@FindBy(xpath="//div[@id='anotherRate']//div[@class='contribution-amount']//p[1]") private WebElement txtAnotherContributionAmount;
			@FindBy(xpath=".//div[@id='irsMax' or @id='catchupMax']//div[@class='contribution-percentage']") private WebElement txtIRSMyContribution;
			@FindBy(xpath="//div[@class='contribution-amount']/a[@class='max-links']") private WebElement txtIRSContributionAmount;
			@FindBy(xpath="//div[@class='contribution-amount']/p[1]") private WebElement txtIRSCatchUpContributionAmount;
			@FindBy(xpath=".//div[@id='irsMax']//a[contains(@uib-popover-template,'maxPopoverTemplate')]") private WebElement txtIRSAnnualCompensationAmount;
			@FindBy(xpath="//div[@id='irsMax']//label[text()='Yes']") private WebElement lblIRSMaximizeYes;
			@FindBy(xpath="//div[@id='irsMax']//label[text()='No']") private WebElement lblIRSMaximizeNo;

			//Add Auto Increase		
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'After Tax')]/../td[3]/.//a") private WebElement lnkAfterTaxAutoIncrease;
			@FindBy(xpath="/.//td[contains(text(),'Before')]/../td[3]/.//a") private WebElement lnkBeforeTaxAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Age catch-up Before')]/../td[3]/.//a") private WebElement lnkCatchupBeforeAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Age catch-up ROTH')]/../td[3]/.//a") private WebElement lnkCatchupRothAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before Tax Bonus')]/../td[3]/.//a") private WebElement lnkBeforeBonusAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'After Tax Bonus')]/../td[3]/.//a") private WebElement lnkAfterBonusAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth Bonus')]/../td[3]/.//a") private WebElement lnkRothBonusAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'other')]/../td[3]/.//a") private WebElement lnkOtherAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth')]/../td[3]/.//a") private WebElement lnkRothAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Catch')]/../td[3]/.//div") private WebElement txtCatchup;
			@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase Before-tax deferral']]")	
			private WebElement lblAutoIncreaseBeforeTaxDeferral;
			@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase After-tax deferral']]")	
			private WebElement lblAutoIncreaseAfterTaxDeferral;
			@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase Age catch-up before-tax deferral']]")	
			private WebElement lblAutoIncreaseCatchUpDeferral;
			
			@FindBy(xpath=".//input[@name='increment']") private WebElement txtAutoIncreaseMyContributionPercent;
			@FindBy(xpath=".//input[@name='maximum']") private WebElement txtAutoIncreaseUntilItReachesPercent;
			@FindBy(name="effectiveDate") private WebElement drpDownAutoIncreasePeriod;
			//@FindBy(xpath="//input[@name='effectiveDate']") private WebElement drpDownAutoIncreasePeriod;
			@FindBy(xpath=".//button[text()[normalize-space()='Save']]") private WebElement btnSaveAddAutoIncreaseModal;
			@FindBy(xpath=".//button[text()[normalize-space()='Cancel']]") private WebElement btnCancelAddAutoIncreaseModal;
			@FindBy(xpath=".//h2[text()[normalize-space()='Contribution Details']]") private WebElement lblContributionDetails;
			@FindBy(id="deleteAutoIncr") private WebElement chkDeleteAutoIncrease;
			@FindBy(xpath=".//button[text()[normalize-space()='Delete']]") private WebElement btnDeleteAddAutoIncreaseModal;
			
			@FindBy(xpath="//h2[text()[normalize-space()='Bonus']]") private WebElement lblBonus;
			@FindBy(xpath="//input[@type='checkbox']") private WebElement chkboxMaximize;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before-tax') or contains(text(),'Before Tax')]/../td[3]") private WebElement txtMaximizeMeAlwaysBefore;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth')]/../td[3]") private WebElement txtMaximizeMeAlwaysRoth;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Catch-Up Before')]/../td[3]") private WebElement txtMaximizeMeAlwaysCatchupBefore;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[1]") private WebElement txtBeforeTaxContributionAmt;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth')]/../td[1]") private WebElement txtRothContributionAmt;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Catch-Up Before')]/../td[1]") private WebElement txtCatchupBeforeAmt;
			
			//table[@class='uib-daypicker']//th[./button[contains(@class,'pull-right')]]
			@FindBy(xpath="//table[@class='deferrals-breakdown']/tbody") private WebElement tblContributionDetails;
			@FindBy(xpath="//table[@class='responsive-tables contributions-cart']/tbody") private WebElement tblMyContributions;
			
			//view only
			@FindBy(xpath = ".//div[@class='clearfix']/.//h2") private List<WebElement> headersContrPage;
			@FindBy(xpath = ".//*[@id='account-details-container']/.//td[contains(text(),'after tax')]/../td[4]/button") private WebElement btnEditAftertaxNoSplit;
			@FindBy(xpath = "//span[contains(text(),'More Options')]") private WebElement linkMoreOptions;
			@FindBy(id = "AGERTH") private WebElement rothCatchUprate;
			@FindBy(id = "AGEBEF") private WebElement txtCatchupRate;
//			@FindBy(xpath = ".//*[@id='splitContribution']/.//span[2]") private WebElement txtViewOnly;
			@FindBy(xpath = "//span[text()[normalize-space()='View Only']]") private WebElement txtViewOnly;
			@FindBy(xpath = "//table[@class='table-details']//th[contains(text(),'Confirmation Number')]/../td") private WebElement txtConfirmationNo;
			
			@FindBy(id = "BEFORE") private WebElement bfrtaxRate;
			@FindBy(id = "ROTH") private WebElement txtRothtaxRate;
			@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
			@FindBy(linkText="Log out") private WebElement lnkLogout;
			@FindBy(xpath="//span[@class='editable-text-trigger ng-binding']") private WebElement txtMyCatchupContr;
			@FindBy(id = "contributionInput") private WebElement txtAnnualCompensation;
			//@FindBy(xpath = ".//span[text()[normalize-space()='Update']]") private WebElement btnUpdate;
			@FindBy(xpath = "//div[contains(@class,'alert')]/p[3]") private WebElement lblAlertMsg;
			@FindBy(xpath = "//div[contains(@class,'alert')]/p[5]") private WebElement lblAlertMsg1;
			
			@FindBy(xpath="//h1[contains(text(),'Carry over my contribution')]") private WebElement lblChainingText;
			@FindBy(xpath=".//*[@id='buttonEditChaining' or @id='buttonAddChaining']") private WebElement btnAddChaining;
			@FindBy(xpath="//h1[text()='Carry over my contribution?']") private WebElement hdrCarryoverContribution;
			@FindBy(xpath="//div[contains(@class,'radio')]//label[text()[normalize-space()='Regular Pre-Tax Contribution to After Tax']]") private WebElement radPreTaxToAftTax;
			@FindBy(xpath="//div[contains(@class,'radio')]//label[text()[normalize-space()='Regular Pre-Tax Contribution to Non-Qualified Plan']]") private WebElement radPreTaxToNQ;
			@FindBy(xpath="//div[contains(@class,'radio')]//label[text()[normalize-space()='ELECT TO AFT TO NQ']]") private WebElement radPreTaxToAftTaxToNQ;
			@FindBy(xpath="//div[contains(@class,'radio')]//strong[text()[normalize-space()='No, do not carryover my contribution']]") private WebElement radDoNotCarryoverContr;
			@FindBy(xpath="//button[text()[normalize-space()='Submit']]") private WebElement btnSubmit;
			@FindBy(xpath="//h1[text()[normalize-space()='Confirmation']]") private WebElement hdrConfirmation;
			@FindBy(xpath="//div[@id='account-details-container']//div[@class='page-title']//div") private WebElement lblChainingConfirmationMsg;
			@FindBy(xpath="//table[@class='table-details']/tbody") private WebElement tblChainingConfirmationDetails;
			
			@FindBy(xpath = "//label[@class='radio-inline panel-title']//strong") private WebElement lblViewOnlyCatchUP;
			@FindBy(xpath="//label[contains(text(),'Yes')]") private WebElement lblMaximizeYes;
			@FindBy(xpath="//label[contains(text(),'No')]") private WebElement lblMaximizeNo;
			@FindBy(xpath="//div[contains(@class,'alert-warning')]") private WebElement txtMaximizeAllert;
			@FindBy(xpath="//div[@class='modal-body rules']") private WebElement txtPlanRule;
			@FindBy(xpath="//div[@class='modal-header rules']") private WebElement headerPlanRule;
			@FindBy(xpath="//button[text()[normalize-space()='Ok']]") private WebElement btnOk;
			@FindBy(xpath="//div[contains(@ng-if,'hasCompanyMatch')]//div[@class='contribution-percentage']//strong") private WebElement txtCompanyMatch;
			@FindBy(xpath=".//*[@class='deferrals-breakdown']//tbody//tr[1]//td//span") private WebElement txtTotalForDollar;
			@FindBy(xpath=".//*[@id='account-details-container']//div[@class='page-title']//span") private WebElement txtConfirmationMessage;
			@FindBy(xpath=".//*[@class='deferrals-breakdown']//tbody//tr[1]//td//span") private List<WebElement> txtDeferralType;
			@FindBy(xpath="//div[contains(@class,'modal-header')]//div[text()='Pending Change']") private WebElement headerPendingDeferral;
			@FindBy(xpath=".//button[text()[normalize-space()='Delete']]") private WebElement btnDeletePendingDeferral;
			@FindBy(xpath=".//button[text()[normalize-space()='Close']]") private WebElement btnClosePendingDeferral;
			@FindBy(xpath="//div[@class='modal-content']//div[contains(text(),'Are you sure you want to delete this?')]") private WebElement hdrDeleteDeferralModal;
			@FindBy(xpath=".//button[text()[normalize-space()='Delete'] and contains(@ng-click,'onConfirmDeleteButtonClick')]") private WebElement btnDeletePendingDeferral1;
			@FindBy(xpath="//table[@class='table-details']/tbody//th[contains(text(),'Deleted Pending Transaction:')]") private WebElement txtPendingTrnsaction;
			@FindBy(xpath="//table[@class='table-details']/tbody//tr[./th[contains(text(),'Deleted Pending Transaction:')]]//td//span[2]") private WebElement txtPendingDeferral;
			@FindBy(xpath="//div[@class='modal-title' and contains(text(),'Do you want to save your changes?')]") private WebElement hdrSaveChangesModal;
			@FindBy(xpath=".//button[contains(@ng-click,'DoNotSaveClick')]") private WebElement btnDontSave;
			@FindBy(xpath=".//button[text()[normalize-space()='Stay']]") private WebElement btnStay;
			@FindBy(xpath=".//h1[contains(text(),'Designate beneficiary') or contains( text(),'My Beneficiaries')]") private WebElement lblDesignateBeneficiary;
			@FindBy(xpath="//p[contains(@ng-repeat,'errorMsg')][1]") private WebElement txtCombinedRuleErrorMsg;
			@FindBy(xpath="//div[@class='contribution-amount']//li") private WebElement txtCompanyMatchRuleDesc;
			@FindBy(xpath="//div[contains(@ng-if,'isPlanInSpecialCatchupPeriod')]//p") private WebElement txtCompanyMatchErrorMsg;
			@FindBy(xpath="//div[@class='error-block']/p") private WebElement lableErrorMsg;
			@FindBy(xpath="//a[contains(@uib-popover-template,'maxPopoverTemplate')]") private WebElement lnkAnnualCompensation;
			@FindBy(xpath="//button[./span[contains(text(),'Update')]]") private WebElement btnUpdate;
			@FindBy(id="contributionRateSlider:EEDEF1-text-edit") private WebElement inpcontributionRateSlider;
			@FindBy(className="contribution-heading") private List<WebElement> txtDeferralTypes;
			@FindBy(xpath="//p[contains(@ng-if,'newContributionRateType')]") private WebElement txtMinimumAndMaximum;
			@FindBy(xpath="//div[@class='rightblock']//div[@class='things-to-know-container']") private WebElement thingstoKnowContainer;

			
			String txtAgeCatchupRoth="//tr[./td[contains(text(),'webElement')]]/td[1]//span";
			String pendingDeferral=".//*[@id='account-details-container']/.//td[contains(text(),'DeferralType')]/../td/.//a[contains(text(),'Pending')]";
			String txtpendingDeferral="//div[contains(@class,'modal-body')]//div[contains(text(),'DeferralType')]";
			String deferralTiredRule="//div[contains(@class,'deferral-codes')]//h2[@class='DeferralType']//a";
			String lableViewOnlyABONUS="//div[./label[contains(text(),'DeferralType')]]//..//div[contains(@class,'percentage-input-container')]//span[contains(text(),'View Only')]";
			String inpViewOnly="//div[./label[contains(text(),'DeferralType')]]//..//div[contains(@class,'percentage-input-container')]//input";
			private String textField="//*[contains(text(),'webElementText')]";

			Actions mouse=new Actions(Web.getDriver());
		/**
		 * Default Constructor
		 */
		public Deferrals() {
		
			this.parent = new LeftNavigationBar();
			PageFactory.initElements(lib.Web.getDriver(), this);
		}
		
		/** Argument Constructor with parent as input
		 * 
		 * @param parent
		 */
		public Deferrals(LoadableComponent<?> parent) {
			this.parent = parent;			
			PageFactory.initElements(lib.Web.getDriver(), this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");
			/*	String ssn = Stock.GetParameterValue("userName");
			ResultSet strUserInfo = null;
			String userFromDatasheet = null;
			if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
			{
				userFromDatasheet=Stock.GetParameterValue("lblUserName").toString().trim();
					
			}
			else{
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
			if (userFromDatasheet.equalsIgnoreCase(userLogedIn))*/if(Common.verifyLoggedInUserIsSame()) {
				   Assert.assertTrue(true,"Mycontribution page is not loaded\n");
				if (!lib.Web.isWebElementDisplayed(lblMyContributions,Deferrals.waitforLoad)) {
					Deferrals.waitforLoad = true;
					throw new Error("'My contributions' page is not loaded");
				}else{
					Deferrals.waitforLoad = false;
				}
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
			
			((LeftNavigationBar) this.parent).clickNavigationLink("My contributions");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.isWebElementDisplayed(lblMyContributions,true);
			
		}
		
		//@SuppressWarnings("unused")
		private WebElement getWebElement(String fieldName) {

			if (fieldName.trim().equalsIgnoreCase("My Contributions")) {
				return this.lblMyContributions;
			}			
			if (fieldName.trim().equalsIgnoreCase("After Tax Edit")) {
				return this.btnEditAfterTax;
			}
			if(fieldName.trim().equalsIgnoreCase("Table Header Contribution")) {
				return this.tblhdrlblContribution;
			}
			if(fieldName.trim().equalsIgnoreCase("Standard Add")) {
				return this.btnAddOrEditStandard;
			}
			if(fieldName.trim().equalsIgnoreCase("Standard Edit")) {
				return this.btnEditStandard;
			}
			if(fieldName.trim().equalsIgnoreCase("After Tax Add")) {
				return this.btnAddorEditAfterTax;
			}
			if(fieldName.trim().equalsIgnoreCase("After Tax Edit")) {
				return this.btnEditAfterTax;
			}
			if(fieldName.trim().equalsIgnoreCase("Catch Up Add")) {
				return this.btnAddOrEditCatchUp;
			}
			if(fieldName.trim().equalsIgnoreCase("Catch Up Edit")) {
				return this.btnEditCatchUp;
			}
			if(fieldName.trim().equalsIgnoreCase("Bonus Add")) {
				return this.btnAddOrEditBonus;
			}
			if(fieldName.trim().equalsIgnoreCase("Bonus Edit")) {
				return this.btnEditBonus;
			}
			if(fieldName.trim().equalsIgnoreCase("Other Add")) {
				return this.btnAddOrEditOther;
			}
			if(fieldName.trim().equalsIgnoreCase("Other Edit")) {
				return this.buttonEditOther;
			}
			if(fieldName.trim().equalsIgnoreCase("Before Add Auto Increase")) {
				return this.lnkBeforeTaxAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("After Add Auto Increase")) {
				return this.lnkAfterTaxAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("Catch Up Before Add Auto Increase")) {
				return this.lnkCatchupBeforeAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("Catch Up Roth Add Auto Increase")) {
				return this.lnkCatchupRothAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("Roth Bonus Add Auto Increase")) {
				return this.lnkRothBonusAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("Before Bonus Add Auto Increase")) {
				return this.lnkBeforeBonusAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("After Bonus Add Auto Increase")) {
				return this.lnkAfterBonusAutoIncrease;
			}
			if(fieldName.trim().equalsIgnoreCase("Other Add auto Increase")) {
				return this.lnkOtherAutoIncrease;	
			}
			if(fieldName.trim().equalsIgnoreCase("Roth Add auto Increase")) {
				return this.lnkRothAutoIncrease;	
			}
			
			if(fieldName.trim().equalsIgnoreCase("Maximize Checkbox")) {
				return this.chkboxMaximize;	
			}
			if(fieldName.trim().equalsIgnoreCase("Continue button")) {
				return this.btnContinue;	
			}
			if(fieldName.trim().equalsIgnoreCase("Confirm button")) {
				return this.btnConfirmAndContinue;	
			}
			if(fieldName.trim().equalsIgnoreCase("Edit Btn Aftertax")) {
				return this.btnEditAftertaxNoSplit;	
			}
			if(fieldName.trim().equalsIgnoreCase("Bonus")) {
				return this.lblBonus;	
			}
			if(fieldName.trim().equalsIgnoreCase("MyContribution Button")) {
				return this.btnMyContributions;	
			}
			
			if(fieldName.trim().equalsIgnoreCase("Regular Pre-Tax Contribution to After Tax")) {
				return this.radPreTaxToAftTax;	
			}
			if(fieldName.trim().equalsIgnoreCase("Regular Pre-Tax Contribution to Non-Qualified Plan")) {
				return this.radPreTaxToNQ;	
			}
			if(fieldName.trim().equalsIgnoreCase("ELECT TO AFT TO NQ")) {
				return this.radPreTaxToAftTaxToNQ;	
			}
			if(fieldName.trim().equalsIgnoreCase("VIEW ONLY SPLIT CONTRIBUTION TEXT")) {
				return this.lblViewOnlyCatchUP;	
			}
			if(fieldName.trim().equalsIgnoreCase("LOG OUT")) {
				return this.lnkLogout;	
			}
			if(fieldName.trim().equalsIgnoreCase("PLAN RULE")) {
				return this.lnkPlanRules;
			}
			if(fieldName.trim().equalsIgnoreCase("Header PlanRule")) {
				return this.headerPlanRule;
			}
			if(fieldName.trim().equalsIgnoreCase("Text PlanRule")) {
				return this.txtPlanRule;
			}
			if(fieldName.trim().equalsIgnoreCase("OK BUTTON")) {
				return this.btnOk;
			}
			if(fieldName.trim().equalsIgnoreCase("TEXT COMPANY MATCH")) {
				return this.txtCompanyMatch;
			}
			if(fieldName.trim().equalsIgnoreCase("RADIO MAXIMIZE TO COMPANY MATCH")) {
				return this.radioMaximizeToCompanyMatch;
			}
			if(fieldName.trim().equalsIgnoreCase("RADIO MAXIMIZE TO IRS LIMIT")) {
				return this.radioMaximizeToIRSLimit;
			}
			if(fieldName.trim().equalsIgnoreCase("RADIO SELECT OTHER CONTRIBUTION")) {
				return this.radioSelectAnotherContributionRate;
			}
			if(fieldName.trim().equalsIgnoreCase("MAXIMIZE YES")) {
				return this.lblMaximizeYes;
			}
			if(fieldName.trim().equalsIgnoreCase("MAXIMIZE NO")) {
				return this.lblMaximizeNo;
			}
			if(fieldName.trim().equalsIgnoreCase("Button Close Pending Deferral")) {
				return this.btnClosePendingDeferral;
			}
			if(fieldName.trim().equalsIgnoreCase("Button Delete Pending Deferral")) {
				return this.btnDeletePendingDeferral;
			}
			if(fieldName.trim().equalsIgnoreCase("Modal Delete Deferral")) {
				return this.hdrDeleteDeferralModal;
			}
			
			if(fieldName.trim().equalsIgnoreCase("Delete Pending Deferral")) {
				return this.btnDeletePendingDeferral1;
			}
			if(fieldName.trim().equalsIgnoreCase("Modal Save Changes")) {
				return this.hdrSaveChangesModal;
			}
			if(fieldName.trim().equalsIgnoreCase("Button Stay")) {
				return this.btnStay;
			}
			if(fieldName.trim().equalsIgnoreCase("Button Dont Save")) {
				return this.btnDontSave;
			}
			if(fieldName.trim().equalsIgnoreCase("Label Beneficiary")) {
				return this.lblDesignateBeneficiary;
			}
			
			if(fieldName.trim().equalsIgnoreCase("Text Combined Rule Error Message")) {
				return this.txtCombinedRuleErrorMsg;
			}
			if(fieldName.trim().equalsIgnoreCase("Split Error Message")) {
				return this.lableErrorMsg;
			}
			
			if(fieldName.trim().equalsIgnoreCase("Input Roth Bonus")) {
				return this.txtSplitRothBonus;
			}
			if(fieldName.trim().equalsIgnoreCase("Input Before Tax Bonus")) {
				return this.txtSplitBeforeBonus;
			}
			
			if(fieldName.trim().equalsIgnoreCase("Select Another Contribution Radio Button")) {
				return this.radioSelectAnotherContributionRate;
			}
			if(fieldName.trim().equalsIgnoreCase("Percent Button")) {
				return this.lnkPercent;
			}
			if(fieldName.trim().equalsIgnoreCase("Dollar Button")) {
				return this.lnkDollar;
			}
			if(fieldName.trim().equalsIgnoreCase("Back Button")) {
				return this.btnBack;
			}
			if(fieldName.trim().equalsIgnoreCase("Continue Button")) {
				return this.btnContinue;
			}
			if(fieldName.trim().equalsIgnoreCase("Split Contribution")) {
				return this.radioSplitContribution;
			}
			if(fieldName.trim().equalsIgnoreCase("Button Confirm And Continue")) {
				return this.btnConfirmAndContinue;
			}
			if(fieldName.trim().equalsIgnoreCase("Text Min/Max Rule")) {
				return this.txtMinimumAndMaximum;
			}
			if(fieldName.trim().equalsIgnoreCase("Things To Know Container")) {
				return this.thingstoKnowContainer;
			}
			return null;
			}		
		
		/**<pre> Method to add or Edit after Tax Contribution
		 *.</pre>
		 * @param fieldName - fieldname
		 * 
		 * @return - void
		 */
//		
		
		/**<pre> Method to click on Add/Edit button for diferent deferral types.
		 *.</pre>
		 * @param deferralsType - deferral types
		 * 
		 * @return - boolean
		 */
		public boolean  clickAddEditButton(String deferralsType)
		{
			boolean isDisplayed = false;
            WebElement deferral = this.getWebElement(deferralsType);
            if(lib.Web.isWebElementDisplayed(this.lnkMoreOptions,true))
            	this.lnkMoreOptions.click();
        			lib.Web.waitForElement(deferral);
		            lib.Web.clickOnElement(deferral);
            Web.waitForElement(btnContinue);
			if(lib.Web.isWebElementDisplayed(btnContinue, true))				
             isDisplayed = true;			
            return isDisplayed;           

		}
		
		/**<pre> Method to select contribution rate.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public void click_Select_Your_Contribution_Rate() throws InterruptedException
		{	
			Actions keyBoard = new Actions(Web.getDriver());
			lib.Web.waitForElement(radioSelectAnotherContributionRate);
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Select another Contribution radio button is clicked", false);
			
			lib.Web.waitForElement(lnkContributionRate);
			
			if(Web.isWebElementDisplayed(lnkPercent))
				this.lnkPercent.click();			
			this.lnkContributionRate.click();
			
			lib.Web.waitForElement(txtcontributionRateSlider);
			if(lnksliderValue.getText().equals(Stock.GetParameterValue("Contribution Rate"))){
				contrbution_rate= Integer.toString(Integer.parseInt(Stock.GetParameterValue("Contribution Rate"))+1);
			}
			else
				contrbution_rate = Stock.GetParameterValue("Contribution Rate");
			
			lib.Web.setTextToTextBox(txtcontributionRateSlider, contrbution_rate);
			//keyBoard.sendKeys(Keys.TAB).perform();
			Web.clickOnElement(btnDone);
			if(btnDone.isDisplayed())
			{
				//keyBoard.sendKeys(Keys.ENTER).perform();
				Web.clickOnElement(btnDone);
			}
			Thread.sleep(5000);
			Reporter.logEvent(Status.INFO, "Select Another Contribution rate", "Contribution rate is  selected to "+contrbution_rate+"%", true);
			/*boolean sliderValue=lib.Web.VerifyText(Stock.GetParameterValue("Contribution Rate"), lnksliderValue.getText().trim());			
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Contribution rate is selected to "+contrbution_rate, true);
			else
				Reporter.logEvent(Status.INFO, "Select Another Contribution rate", "Contribution rate is not selected to "+contrbution_rate, true);
			return sliderValue;*/
		}
		
		/**<pre> Method to add auto increase.
		 *.</pre>
		 * @throws InterruptedException 
		 * 
		 */
		public void add_Auto_Increase(String deferralType) throws InterruptedException
		{
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			//System.out.println(dateFormat.format(calendar.getTime()));
			String date=dateFormat.format(calendar.getTime());
			WebElement autoIncreaseDeferralType=this.getWebElement(deferralType);
			
			lib.Web.waitForElement(tblhdrlblContribution);
			

			if(lib.Web.isWebElementDisplayed(this.tblhdrlblContribution, true))
			{
				if(lib.Web.isWebElementDisplayed(autoIncreaseDeferralType, false))
				{
				Reporter.logEvent(Status.PASS, "Verify if Add Auto Increase link is displayed", "Add Auto Increase link is displayed", true);
//				this.lnkBeforeTaxAutoIncrease.click();
				autoIncreaseDeferralType.click();
				try {
					lib.Web.waitForElement(txtAutoIncreaseMyContributionPercent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				lib.Web.setTextToTextBox(txtAutoIncreaseMyContributionPercent, Stock.GetParameterValue("Auto Increase Contribution Percent"));			
				lib.Web.setTextToTextBox(txtAutoIncreaseUntilItReachesPercent, Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
				Web.setTextToTextBox(drpDownAutoIncreasePeriod, date);
				//lib.Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.GetParameterValue("Auto Increase Period")));
				Thread.sleep(3000);
				Web.clickOnElement(txtAutoIncreaseUntilItReachesPercent);
				Web.clickOnElement(btnSaveAddAutoIncreaseModal);
				Thread.sleep(5000);
				}
				else
				Reporter.logEvent(Status.FAIL, "Verify if Add Auto Increase link is displayed for : "+"'"+deferralType+"'", "Add Auto Increase link is not displayed", true);
			}
			
			
		}
		
		/**<pre> Method to verify confirmation page.
		 *.</pre>
		 * 
		 */
		public void myContributions_Confirmation_Page()
		{
		
			Web.waitForElement(btnConfirmAndContinue);
			Web.clickOnElement(this.btnConfirmAndContinue); 
			
			if(Web.isWebElementDisplayed(this.lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				

		}
		
		/**<pre> Method to click on maximize _to_irs radio button and verify My contributions percentage and amount.
		 *.</pre>
		 * 
		 * @return - boolean
		 */
		public void click_Maximize_IRS_Limit()
		{
//			if(deferralType.equalsIgnoreCase("Catch Up")){
//				lib.Web.clickOnElement(radioMaximizeToIRSLimit);
//				Reporter.logEvent(Status.PASS, "Verify if maximize to IRS limit is selected", "Clicked Maximize to irs limit", false);
//			}
//				
//			else if(deferralType.equalsIgnoreCase("Standard"))
//			{
				Web.waitForElement(radioMaximizeToIRSLimit);
				lib.Web.clickOnElement(radioMaximizeToIRSLimit);
			
			String irs=	txtIRSMyContribution.getText().split("%")[0];
			irs_limit=(float) Float.parseFloat(irs);
			
			if(this.txtIRSMyContribution.getText().contains("%"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions percentage ", "My Contributions percentage is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions percentage", "My Contributions percentage is not displayed", true);
			
			/*if(this.txtIRSContributionAmount.getText().contains("$"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions amount ", "My Contributions amount is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions amount", "My Contributions amount is not displayed", true);
			*/
			//Web.clickOnElement(lblMaximizeYes);
		}
			
		
		
		/**<pre> Method to select contribution type.
		 *.</pre>
		 * 
		 */
		
		public void select_ContributionType(String contributionType)
		{		
			
			lib.Web.waitForElement(btnContinue);
			lib.Web.clickOnElement(this.btnContinue);
			lib.Web.waitForElement(radioSplitContribution);
			
			if(contributionType.equalsIgnoreCase("Split") ){
				if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)  )  
				{				
					this.radioSplitContribution.click();
					Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", true);
					
					
					if(Stock.GetParameterValue("Contributing_type").equalsIgnoreCase("Maximize to irs limit")){
//					int befor_tax=irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth")));
//						befor_tax=irs_limit-(Float.parseFloat(Stock.GetParameterValue("Split_Tax_roth")));
//						lib.Web.setTextToTextBox(txtSplitBeforeTax, Float.toString(befor_tax));
//						lib.Web.setTextToTextBox(txtSplitRothTax, Stock.GetParameterValue("Split_Tax_roth"));
						before_tax =Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*irs_limit;
						roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*irs_limit;
						lib.Web.setTextToTextBox(txtSplitBeforeTax, new DecimalFormat("##.##").format(before_tax));
						lib.Web.setTextToTextBox(txtSplitRothTax, new DecimalFormat("##.##").format(roth));
					}
					else{
//						lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.GetParameterValue("Split_Tax_before"));
//                        int i=Integer.parseInt(contrbution_rate);
//                        int j=Integer.parseInt(Stock.GetParameterValue("Split_Tax_before"));
//                        lib.Web.setTextToTextBox(txtSplitRothTax,Integer.toString(i-j));
						
						before_tax = Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*Float.parseFloat(contrbution_rate);
						roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*Float.parseFloat(contrbution_rate);
						lib.Web.setTextToTextBox(txtSplitBeforeTax, new DecimalFormat("##.##").format(before_tax));
						lib.Web.setTextToTextBox(txtSplitRothTax, new DecimalFormat("##.##").format(roth));
					}
					
					
									
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
			}
			else{
				for(int i=0;i<lstradioSelectContibution.size();i++){
					if(StringUtils.containsIgnoreCase(lstradioSelectContibution.get(i).getText(), contributionType))
					{
						lstradioSelectContibution.get(i).click();
						Reporter.logEvent(Status.PASS, "Verify "+contributionType+" Contribution is selected", contributionType+" Contribution is selected", false);
						break;
					}
				}
			}
			
			this.btnContinue.click();
				
		}
		
//		/**<pre> Method to verify catch up contribution after adding.
//		 *.</pre>
//		 * 
//		 */
//		public void catchup_maximize_to_irs() {
//			lib.Web.clickOnElement(this.btnContinue);
//			if(this.txtCatchup.getText().contains("Effective "))
//				Reporter.logEvent(Status.PASS, "Verify Roth Tax Contribution Rate radio button is clicked ", "Roth Tax radio option is clicked", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Roth Tax Contribution Rate radio button is clicked", "Roth Tax radio option is not clicked", true);
//			
//		}
		
		/**<pre> Method to click on maximize _to_company_match radio button and verify My contributions percentage and amount.
		 *.</pre>
		 * 
		 * @return - boolean
		 */

		public void click_MaximizeToCompanyMatch()
		{
			
			Web.waitForElement(radioMaximizeToCompanyMatch);
			mouse.moveToElement(radioMaximizeToCompanyMatch).click(radioMaximizeToCompanyMatch).build().perform();
			//lib.Web.clickOnElement(radioMaximizeToCompanyMatch);
			if(this.txtCompanyMatchMyContribution.isDisplayed())
				Reporter.logEvent(Status.PASS, "Verify My Contributions percentage ", "My Contributions percentage is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions percentage", "My Contributions percentage is not displayed", true);
			/*if(this.txtCompanyMatchContributionAmount.getText().contains("$"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions amount ", "My Contributions amount is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions amount", "My Contributions amount is not displayed", true);*/
			contrbution_rate = txtCompanyMatchMyContribution.getText().split("%")[0];
		}
		
		
		/**<pre> Method to check if a particular deferral is available for the participant
		 *.</pre>
		 * 
		 * @return - boolean
		 */
		public boolean check_if_participant_eligible_for_deferral_type(String deferralType){
			boolean issuccess=false;
			WebElement deferral=this.getWebElement(deferralType);
			if(lib.Web.isWebElementDisplayed(this.lnkMoreOptions))
            	this.lnkMoreOptions.click();
			if(Web.isWebElementDisplayed(deferral))
				issuccess=true;
			
			return issuccess;
		}
		
		/**<pre> Method to select  maximize me always option and verify if the contributions have the maximize me always option
		 *.</pre>
		 * 
		 *
		 */
		public void regular_maximize_me_always(String maximizeMeAlways){
			if(maximizeMeAlways.equalsIgnoreCase("Yes")){
				if(!this.txtMaximizeAllert.isDisplayed())
					this.lblMaximizeYes.click();
				
				select_ContributionType("Split");
				lib.Web.clickOnElement(btnContinue);
				Web.waitForElement(txtMaximizeMeAlwaysBefore);
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysBefore.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Standard before contribution", "Maximize Me Always is displayed for Standard before contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Standard before contribution", "Maximize Me Always is Not displayed for Standard before contribution", true);
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysRoth.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Standard Roth contribution", "Maximize Me Always is displayed for Standard Roth contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Standard Roth contribution", "Maximize Me Always is Not displayed for Standard Roth contribution", true);
				
//				if(lib.Web.VerifyText(Float.toString(irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth"))))+"%", txtBeforeTaxContributionAmt.getText(), true))
//					Reporter.logEvent(Status.PASS, "Verify before tax contribution percent", "Before tax contribution percent is matching", false);
//				else
//					Reporter.logEvent(Status.FAIL, "Verify before tax contribution percent", "Before tax contribution percent is not matching", true);
				before_tax =Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*irs_limit;
				roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*irs_limit;
				if(lib.Web.VerifyText( new DecimalFormat("##.##").format(before_tax)+"%", txtBeforeTaxContributionAmt.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify before tax contribution percent", "Before tax contribution percent is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify before tax contribution percent", "Before tax contribution percent is not matching", true);
				
				
				if(lib.Web.VerifyText(new DecimalFormat("##.##").format(roth)+"%", txtRothContributionAmt.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify roth contribution percent", "Roth contribution percent is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify roth contribution percent", "Roth contribution percent is not matching", true);
			}
			else{
				if(this.txtMaximizeAllert.isDisplayed())
					this.lblMaximizeNo.click();
			}
		}
		
		/**<pre> Method to verify the different contributions in the My Contributions table
		 *.</pre>
		 * @param percent - contribution percent
		 * @param contrType - Contribution type like Roth,Before, After tax etc.
		 * @param deferralsType - deferral type(Standard, Catch up, After Tax, Bonus, Other)
		 * 
		 * @return - boolean
		 */
		public boolean verifyMyContributions(String percent, String contrType, String deferralsType){
			boolean issuccess = false;
			
			lib.Web.waitForElement(lblMyContributions);
			if(deferralsType.equalsIgnoreCase("Standard")){
				if(tblMyContributions.getText().toUpperCase().contains(percent+"% "+contrType.toUpperCase()))
					return true;
			}
			if(deferralsType.equalsIgnoreCase("Catchup")){
				if(tblMyContributions.getText().contains(percent+"% "+contrType))
					return true;
			}
			if(deferralsType.equalsIgnoreCase("Aftertax")){
				if(tblMyContributions.getText().contains(percent+"% "+contrType))
					return true;
			}
			if(deferralsType.equalsIgnoreCase("Bonus")){
				if(tblMyContributions.getText().contains(percent+"% "+contrType))
					return true;
			}
			if(deferralsType.equalsIgnoreCase("Other")){
				if(tblMyContributions.getText().contains(percent+"% "+contrType))
					return true;
			}
			return issuccess;
		}
		
		/**<pre> Method to verify the contributions in the Confirmation table
		 *.</pre>
		 * @param percent - contribution percent
		 * @param type - Contribution type like Roth,Before, After tax etc.
		 * @param autoIncreaseRate - Auto Increase rate
		 * @param autoIncreaseUpto - Auto increase until it reaches percent
		 * 
		 * @return - boolean
		 */
		public boolean verifyContributionDetails(String percent, String type, String autoIncreaseRate, String autoIncreaseUpto){
				Web.waitForElement(txtConfirmationNo);			
				String ConfirmationNo = this.txtConfirmationNo.getText();
				if(ConfirmationNo!=null)
					Reporter.logEvent(Status.INFO, "Confirmation Number", "Confirmation Number : "+ConfirmationNo, false);
				else
					Reporter.logEvent(Status.FAIL, "Confirmation Number", "Confirmation Number not generated", true);
				if(autoIncreaseRate!=null & autoIncreaseUpto!=null){
					if(tblContributionDetails.getText().contains(percent+"% "+type+" with auto-increase "+autoIncreaseRate+"% per year up to "+autoIncreaseUpto+"%")){
						Reporter.logEvent(Status.INFO, "Contribution Details : ",tblContributionDetails.getText() , false);
						return true;
					}	
				}
				else{
					if(tblContributionDetails.getText().contains(percent+"% "+type))
						return true;
				}
			
			
			
			return false;
		}
		public boolean verifyConfirmationPageDisplayed(){
			if(Web.isWebElementDisplayed(txtConfirmationNo, true))
				return true;
			else
				return false;
		}
		
		/**<pre> Method to verify if After Tax option is available for the participant
		 *.</pre>
		 * 
		 */
		public boolean checkAftertaxOptionNotdisplayed()
		{
			boolean isDisplayed = false;
			lib.Web.clickOnElement(linkMoreOptions);
			
			for(WebElement webelement : headersContrPage)
			{
				if(webelement.getText().trim().contains("After tax"))
				{
					isDisplayed = true;
					break;
				}
			}
			
			return isDisplayed;	
		}
		
		/**<pre> Method to edit Catch-up contribution having a split  and verify if view only is displayed 
		 *.</pre>
		 * @param myContRate- contribution rate 
		 * @throws InterruptedException 
		 */
		public void Catchup_with_split_contributions(String myContRate) throws InterruptedException
		{
//			String rothCatchupRate="";
//			int rothCatchupRateNum;
			try {
				lib.Web.waitForElement(btnEditCatchUp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lib.Web.clickOnElement(btnEditCatchUp);
			click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(btnContinue);
			/*if(lib.Web.isWebElementDisplayed(txtViewOnly))
				Reporter.logEvent(Status.PASS, "Check if view only is displayed for age roth rate", "View only is displayed for roth catch up rate", false);
			else
				Reporter.logEvent(Status.FAIL, "Check if view only is displayed for age roth rate", "View only is not displayed for roth catch up rate", true);
			
//			rothCatchupRate = rothCatchUprate.getText();
//			rothCatchupRateNum = Integer.parseInt(rothCatchupRate);
			//lib.Web.setTextToTextBox(txtCatchupRate, String.valueOf((Integer.valueOf(myContRate)) - rothCatchupRateNum));
			lib.Web.setTextToTextBox(txtCatchupRate,String.valueOf(((Integer.valueOf(myContRate))-Integer.parseInt(Stock.GetParameterValue("Age_roth_Catchup_contr")))));
			lib.Web.clickOnElement(btnContinue);
			lib.Web.clickOnElement(btnConfirmAndContinue);
			myContributions_Confirmation_Page();*/
		}
		
		/**<pre> Method to edit Standard contribution having a split  and verify if view only is displayed 
		 *.</pre>
		 * @param myContRate- contribution rate 
		 * @throws InterruptedException 
		 */
		public void View_only_Standard_with_changes(String myContRate) throws InterruptedException
		{
			try {
				lib.Web.waitForElement(btnAddOrEditStandard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lib.Web.clickOnElement(btnAddOrEditStandard);
			click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(btnContinue);
			
			/*lib.Web.clickOnElement(btnEditStandard);
			click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(btnContinue);
			Web.waitForElement(txtViewOnly);
			if(lib.Web.isWebElementDisplayed(txtViewOnly))
			{
				Reporter.logEvent(Status.PASS, "Check if view only is displayed for age roth rate", "View only is displayed for roth catch up rate", false);
			}else
			{
				Reporter.logEvent(Status.FAIL, "Check if view only is displayed for age roth rate", "View only is not displayed for roth catch up rate", true);
			}
//			rothCatchupRate = bfrtaxRate.getText();
//			rothCatchupRateNum = Integer.parseInt(rothCatchupRate);
			rothCatchupRateNum = Integer.parseInt(Stock.GetParameterValue("Age_roth_standard_contr"));
			lib.Web.setTextToTextBox(txtRothtaxRate, String.valueOf((Integer.valueOf(myContRate)) -rothCatchupRateNum));
			lib.Web.clickOnElement(btnContinue);
			lib.Web.clickOnElement(btnConfirmAndContinue);
			myContributions_Confirmation_Page();*/
		}
		

		

		/**<pre> Method to edit Standard contribution and select Maximize me always option and verify Maximize me always is displayed for Catch-up 
		 *.</pre>
		 * 
		 */
		public void Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always(){
			
			
			clickAddEditButton("Standard Add");
			click_Maximize_IRS_Limit();
			regular_maximize_me_always("Yes");
			if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysCatchupBefore.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Catch-up before contribution", "Maximize Me Always is displayed for Catch-up before contribution", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Catch-up before contribution", "Maximize Me Always is not displayed for Catch-up before contribution", true);
			
			before_tax =Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*irs_limit;
			roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*irs_limit;
			
			String catchup_before_percent=txtCatchupBeforeAmt.getText().trim();
			
			Float total_contr=Float.parseFloat(catchup_before_percent.split("%")[0])+before_tax+ roth;
			Web.clickOnElement(btnConfirmAndContinue);
			
			
			
			if(verifyConfirmationPageDisplayed()){
				if(verifyContributionDetails(new DecimalFormat("##.##").format(before_tax),"Before Tax",null,null))
					Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+new DecimalFormat("##.##").format(before_tax), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+new DecimalFormat("##.##").format(before_tax), true);
				
//				if(verifyContributionDetails(Stock.GetParameterValue("Split_Tax_roth"),"Roth",null,null))
//					Reporter.logEvent(Status.PASS, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+Stock.GetParameterValue("Split_Tax_roth"), false);
//				else
//					Reporter.logEvent(Status.FAIL, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+Stock.GetParameterValue("Split_Tax_roth"), true);

				if(verifyContributionDetails(new DecimalFormat("##.##").format(roth),"Roth",null,null))
					Reporter.logEvent(Status.PASS, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+new DecimalFormat("##.##").format(roth), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+new DecimalFormat("##.##").format(roth), true);
				
				
				if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
					Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+new DecimalFormat("##.##").format(total_contr), false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+new DecimalFormat("##.##").format(total_contr), true);
				
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Confirmation page displayed", "Confirmation page not displayed", true);
			
			
		}
		
		

		/**<pre> Method to edit Maximized Standard deferral to a custom rate and verify add auto increase link is displayed for both standard and catch up deferrals 
		 *.</pre>
		 * @throws InterruptedException 
		 * 
		 */
		public void Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer() throws InterruptedException{
			
			
			clickAddEditButton("Standard Add");
			click_Select_Your_Contribution_Rate();
			select_ContributionType(Stock.GetParameterValue("Contribution_type"));
			
			if(Web.isWebElementDisplayed(lnkBeforeTaxAutoIncrease, true))
				Reporter.logEvent(Status.PASS, "Verify Add auto increase link displayed for Standard Contribution", "Verify Add auto increase link displayed for Standard Contribution", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Add auto increase link displayed for Standard Contribution", "Verify Add auto increase link not displayed for Standard Contribution", true);
			
			if(Web.isWebElementDisplayed(lnkCatchupBeforeAutoIncrease, true))
				Reporter.logEvent(Status.PASS, "Verify Add auto increase link displayed for CatchUp Contribution", "Verify Add auto increase link displayed for CatchUp Contribution", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Add auto increase link displayed for CatchUp Contribution", "Verify Add auto increase link not displayed for CatchUp Contribution", true);
			
			String catchup_before_percent=txtCatchupBeforeAmt.getText().trim();
			lib.Web.clickOnElement(btnEditCatchUp);
			
			Web.waitForElement(txtMyCatchupContr);
//			if(Web.VerifyText(catchup_before_percent, txtMyCatchupContr.getText().trim(), true))
//				Reporter.logEvent(Status.PASS, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is same as maximized contr rate", false);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is not same as maximized contr rate", true);
			
			if(txtMyCatchupContr.getText().contains(catchup_before_percent.split("%")[0]))
				Reporter.logEvent(Status.PASS, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is same as maximized contr rate", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is not same as maximized contr rate", true);
			
			
			
			Web.clickOnElement(btnBack);
			Web.waitForElement(btnConfirmAndContinue);
			System.out.println(Float.parseFloat(catchup_before_percent.split("%")[0]));
			System.out.println(Stock.GetParameterValue("Contribution Rate"));
			Float total_contr=Float.parseFloat(catchup_before_percent.split("%")[0])+Float.parseFloat(Stock.GetParameterValue("Contribution Rate"));
			System.out.println(total_contr);
			Web.clickOnElement(btnConfirmAndContinue);
			
					
//			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
//				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), true);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), true);
			
			if(verifyContributionDetails(Stock.GetParameterValue("Contribution Rate"),"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Stock.GetParameterValue("Contribution Rate"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Stock.GetParameterValue("Contribution Rate"), true);
			
			if(verifyContributionDetails(catchup_before_percent.split("%")[0],"Catch-Up Before",null,null))
				Reporter.logEvent(Status.PASS, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_before_percent, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_before_percent, true);
			
		}
		
		/**<pre> Method to change the salary on the maximize to IRS panel of the Standard deferral and verify catchup contribution is changed
		 *.</pre>
		 * 
		 */
		public void Catch_up_Salary_changes_on_IRS_panel(){
			
			Web.waitForElement(btnAddOrEditStandard);
			String catchup_contr1=txtCatchupBeforeAmt.getText().trim();
			Web.clickOnElement(btnAddOrEditStandard);
			Web.waitForElement(radioMaximizeToIRSLimit);
			Web.clickOnElement(radioMaximizeToIRSLimit);
			Web.waitForElement(lnkAnnualCompensation);
	        Web.clickOnElement(lnkAnnualCompensation);			//String irs_contr_rate=	txtIRSMyContribution.getText().split("%")[0];
			Web.setTextToTextBox(txtAnnualCompensation, Stock.GetParameterValue("Annual_Compensation"));
			Reporter.logEvent(Status.INFO, "Entered Annual Compansation amount", "Annual compensation amount entered is: "+Stock.GetParameterValue("Annual_Compensation"), false);
			Web.clickOnElement(btnUpdate);
			if(Web.isWebElementDisplayed(txtAnnualCompensation, true))
				Web.clickOnElement(lnkAnnualCompensation);
			if(Web.isWebElementDisplayed(lblAlertMsg)){
				if(Web.VerifyPartialText(Stock.GetParameterValue("Alert_Message"), lblAlertMsg.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Alert message is Displayed", "Alert message is Displayed\nExpected:"+Stock.GetParameterValue("Alert_Message")+"\nActual:"+lblAlertMsg.getText(), true);
				else
					Reporter.logEvent(Status.FAIL, "Verify Alert message is matching", "Alert message not matching\nExpected:"+Stock.GetParameterValue("Alert_Message")+"\nActual:"+lblAlertMsg.getText(), true);
				/*if(Web.VerifyText(Stock.GetParameterValue("Alert_Message1"), lblAlertMsg1.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Alert message is Displayed", "Alert message is Displayed\nExpected:"+Stock.GetParameterValue("Alert_Message1")+"\nActual:"+lblAlertMsg1.getText(), true);
				else
					Reporter.logEvent(Status.FAIL, "Verify Alert message is matching", "Alert message not matching\nExpected:"+Stock.GetParameterValue("Alert_Message1")+"\nActual:"+lblAlertMsg1.getText(), true);*/
				
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify if Alert is displayed", "No Alert displayed after updating Annual compensation amount", true);
				
			
			String irs_contr_rate=	txtIRSMyContribution.getText().split("%")[0];
			select_ContributionType(Stock.GetParameterValue("Contribution_type"));	
			
			Web.waitForElement(btnAddOrEditCatchUp);
			Web.clickOnElement(btnAddOrEditCatchUp);
			Web.waitForElement(radioMaximizeToIRSLimit);
			click_Maximize_IRS_Limit();

			select_ContributionType(Stock.GetParameterValue("Contribution_type"));
			
			Web.waitForElement(lblMyContributions);
			if(Web.isWebElementDisplayed(lblMyContributions))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page is displayed", "My Contributions page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page is displayed", "My Contributions page not displayed", true);
			
			String catchup_contr2=txtCatchupBeforeAmt.getText().trim();
			
			if(Float.parseFloat(catchup_contr1.split("%")[0])!=Float.parseFloat(catchup_contr2.split("%")[0]))
				Reporter.logEvent(Status.PASS, "Verify maximized Catchup contribution rate is changed", "Maximized Catchup contribution rate is changed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify maximized Catchup contribution rate is changed", "Maximized Catchup contribution rate is not changed", true);
			
			Float total_contr=Float.parseFloat(catchup_contr2.split("%")[0])+Float.parseFloat(irs_contr_rate);
			Web.clickOnElement(btnConfirmAndContinue);
			Web.waitForElement(btnMyContributions);
			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			
			if(verifyContributionDetails(irs_contr_rate,"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+irs_contr_rate, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+irs_contr_rate, true);
			
			if(verifyContributionDetails(catchup_contr2.split("%")[0],"Catch-Up Before",null,null))
				Reporter.logEvent(Status.PASS, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_contr2, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_contr2, true);
			
		}
		
		/**<pre> Method to add Catch up deferral and select Maximize me always option
		 *.</pre>
		 * 
		 */
		public void Catch_up_Maximize_me_always(){
			Web.waitForElement(btnAddOrEditCatchUp);
			Web.clickOnElement(btnAddOrEditCatchUp);
			Web.waitForElement(radioMaximizeToIRSLimit);
			click_Maximize_IRS_Limit();

			select_ContributionType(Stock.GetParameterValue("Contribution_type"));
			//Web.clickOnElement(btnContinue);
			Web.waitForElement(txtMaximizeMeAlwaysCatchupBefore);
			if(Web.isWebElementDisplayed(txtMaximizeMeAlwaysCatchupBefore))
				Reporter.logEvent(Status.PASS, "Verify Maximize Me Always displayed for catchup", "Maximize Me Always displayed for catchup", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always displayed for catchup", "Maximize Me Always not displayed for catchup", true);
			
			String standard_beforetax_contr=txtBeforeTaxContributionAmt.getText().split("%")[0];
			Float total_contr=Float.parseFloat(txtBeforeTaxContributionAmt.getText().split("%")[0])+irs_limit;
			Web.clickOnElement(btnConfirmAndContinue);
			
//			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
//				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
//			else
//				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			
			if(verifyContributionDetails(standard_beforetax_contr,"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+standard_beforetax_contr, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+standard_beforetax_contr, true);
			
			
			if(verifyContributionDetails(Float.toString(irs_limit),"Catch-Up Before",null,null))
				Reporter.logEvent(Status.PASS, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+Float.toString(irs_limit), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+Float.toString(irs_limit), true);
			
			
		}
		
		/**<pre> Method to split bonus contribution rate
		 *.</pre>
		 * 
		 */
		public void split_bonus_contribution(){
			try {
				lib.Web.waitForElement(btnContinue);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			lib.Web.clickOnElement(this.btnContinue);
			if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)){  
				this.radioSplitContribution.click();
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
//				if(Web.isWebElementDisplayed(txtSplitBeforeTax))
//					lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.GetParameterValue("Split_Tax_before"));
//				if(Web.isWebElementDisplayed(txtSplitAfterTaxBonus))
//					lib.Web.setTextToTextBox(txtSplitAfterTaxBonus, Stock.GetParameterValue("Split_Tax_after"));
//				if(Web.isWebElementDisplayed(txtSplitRothBonus))
//					lib.Web.setTextToTextBox(txtSplitRothBonus, Stock.GetParameterValue("Split_Tax_roth"));
			
			
				before_tax = Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*Float.parseFloat(contrbution_rate);
				roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*Float.parseFloat(contrbution_rate);
				float after_tax = Float.parseFloat(Stock.GetParameterValue("after_tax_ratio"))*Float.parseFloat(contrbution_rate);
				
				if(Web.isWebElementDisplayed(txtSplitBeforeBonus))
					lib.Web.setTextToTextBox(txtSplitBeforeBonus, new DecimalFormat("##.##").format(before_tax));
				if(Web.isWebElementDisplayed(txtSplitAfterTaxBonus))
					lib.Web.setTextToTextBox(txtSplitAfterTaxBonus,new DecimalFormat("##.##").format(after_tax));
				if(Web.isWebElementDisplayed(txtSplitRothBonus))
					lib.Web.setTextToTextBox(txtSplitRothBonus, new DecimalFormat("##.##").format(roth));
			
				
			
			
			
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
			this.btnContinue.click();
			
		}
		
		/**<pre> Method to split catch up contribution rate
		 *.</pre>
		 * 
		 */
		public void split_catchup_contribution(){
			lib.Web.waitForElement(btnContinue);
			lib.Web.clickOnElement(this.btnContinue);
			if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)){  
				this.radioSplitContribution.click();
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
//				lib.Web.setTextToTextBox(txtSplitBeforeCatchup, Stock.GetParameterValue("Split_Tax_before"));
//				lib.Web.setTextToTextBox(txtSplitRothCatchup, Stock.GetParameterValue("Split_Tax_roth"));
				
				if(Stock.GetParameterValue("Contributing_type").equalsIgnoreCase("Maximize to irs limit")){
					before_tax =Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*irs_limit;
					roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*irs_limit;
					lib.Web.setTextToTextBox(txtSplitBeforeCatchup, new DecimalFormat("##.##").format(before_tax));
					lib.Web.setTextToTextBox(txtSplitRothCatchup, new DecimalFormat("##.##").format(roth));
				}
				else{
					
					before_tax = Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*Float.parseFloat(contrbution_rate);
					roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*Float.parseFloat(contrbution_rate);
					System.out.println("before tax : "+before_tax);
					System.out.println("roth : "+roth);
					lib.Web.setTextToTextBox(txtSplitBeforeCatchup, new DecimalFormat("##.##").format(before_tax));
					lib.Web.setTextToTextBox(txtSplitRothCatchup, new DecimalFormat("##.##").format(roth));
				}
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
		
			this.btnContinue.click();
		}
		/**<pre> Method to split Other contribution rate
		 *.</pre>
		 * 
		 */
		public void split_Other_contribution(){
			lib.Web.waitForElement(btnContinue);
			lib.Web.clickOnElement(this.btnContinue);
			if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)){  
				this.radioSplitContribution.click();
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
//				if(Web.isWebElementDisplayed(txtSplitAgeRothVariable))
//					lib.Web.setTextToTextBox(txtSplitAgeRothVariable, Stock.GetParameterValue("Split_Tax-AgeRoth"));
//				if(Web.isWebElementDisplayed(txtSplitRothVariable))
//					lib.Web.setTextToTextBox(txtSplitRothVariable, Stock.GetParameterValue("Split_Tax_roth"));
//				if(Web.isWebElementDisplayed(txtSplitBeforeVariable))
//					lib.Web.setTextToTextBox(txtSplitBeforeVariable, Stock.GetParameterValue("Split_Tax_before"));
			
			
				before_tax = Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*Float.parseFloat(contrbution_rate);
				roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*Float.parseFloat(contrbution_rate);	
				
				if(Web.isWebElementDisplayed(txtSplitBeforeVariable))
					lib.Web.setTextToTextBox(txtSplitBeforeVariable, new DecimalFormat("##.##").format(before_tax));
				
				if(Web.isWebElementDisplayed(txtSplitRothVariable))
					lib.Web.setTextToTextBox(txtSplitRothVariable, new DecimalFormat("##.##").format(roth));
			
			
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
		
			this.btnContinue.click();
		}
		public String getCatchupMaximized(){

            Web.waitForElement(btnAddOrEditCatchUp);

            String status=txtMaximizeMeAlwaysCatchupBefore.getText();

            System.out.println("Statuss : "+status);

            return status;

         }
		
		public void chainingForStandardContribution(String chainingOption){
			WebElement option=this.getWebElement(chainingOption);
			//Web.waitForElement(lblChainingText);
			/*if(StringUtils.containsIgnoreCase(lblChainingText.getText(), "Carryover my contribution if I reach the plan or IRS limit?"))
				Reporter.logEvent(Status.PASS, "Verify Chaining message displayed", "Expected : Carryover my contribution if I reach the plan or IRS limit? \nActual : "+lblChainingText.getText(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Chaining message displayed", "Expected : Carryover my contribution if I reach the plan or IRS limit? \nActual : "+lblChainingText.getText(), true);*/
			if(Web.isWebElementDisplayed(btnAddChaining)){
				btnAddChaining.click();
				Reporter.logEvent(Status.INFO, "Verify Add button is clicked", "Add chaining button is clicked", true);
				if(Web.isWebElementDisplayed(hdrCarryoverContribution, true)){
					Reporter.logEvent(Status.PASS, "Verify Chaining Election page is displayed", "Chaining election page displayed", true);
					
					if(Web.isWebElementDisplayed(radPreTaxToAftTaxToNQ))
						Reporter.logEvent(Status.PASS, "Verify 'ELECT TO AFT TO NQ' radio button is displayed", "'ELECT TO AFT TO NQ' radio button is displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify 'ELECT TO AFT TO NQ' radio button is displayed","'ELECT TO AFT TO NQ' radio button not displayed" , true);
					if(Web.isWebElementDisplayed(radPreTaxToAftTaxToNQ))
						Reporter.logEvent(Status.PASS, "Verify 'Pre-Tax Contribution to Non-Qualified Plan' radio button is displayed", "'Pre-Tax Contribution to Non-Qualified Plan' radio button is displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify 'Pre-Tax Contribution to Non-Qualified Plan' radio button is displayed","'Pre-Tax Contribution to Non-Qualified Plan' radio button not displayed" , true);
					if(Web.isWebElementDisplayed(radPreTaxToAftTaxToNQ))
						Reporter.logEvent(Status.PASS, "Verify 'Pre-Tax Contribution to After Tax' radio button is displayed", "'Pre-Tax Contribution to After Tax' radio button is displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify 'Pre-Tax Contribution to After Tax' radio button is displayed","'Pre-Tax Contribution to After Tax' radio button not displayed" , true);
					if(Web.isWebElementDisplayed(radPreTaxToAftTaxToNQ))
						Reporter.logEvent(Status.PASS, "Verify 'do not carryover my contribution' radio button is displayed", "'do not carryover my contribution' radio button is displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify 'do not carryover my contribution' radio button is displayed","'do not carryover my contribution' radio button not displayed" , true);
				
					Web .clickOnElement(option);
					Reporter.logEvent(Status.INFO, "Verify '"+chainingOption+"' option is selected", chainingOption+" option is selected", true);
					Web .clickOnElement(btnSubmit);
					Reporter.logEvent(Status.INFO, "Verify 'Submit' button is clicked", "Submit button is clicked", true);
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Chaining Election page is displayed", "Chaining election page not displayed", true);
					
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify button to add chaining is displayed", "button to add chaining displayed", true);
		}
		
		public void verifyChainingConfirmationPage(String chainingType, String plan){
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			String date=dateFormat.format(cal.getTime());
			
			if(Web.isWebElementDisplayed(hdrConfirmation, true)){
				Reporter.logEvent(Status.PASS, "Verify Confirmation page is displayed", "Confirmation page displayed", true);
				
				if(Web.VerifyText("Your carry over election has been received on "+date+".", lblChainingConfirmationMsg.getText().trim(), true))
					Reporter.logEvent(Status.PASS, "Verify Chaining Confirmation message is displayed", "Expected : Your carry over election has been received on "+date+" \n Actual : "+lblChainingConfirmationMsg.getText(), false);
				else
					Reporter.logEvent(Status.FAIL,  "Verify Chaining Confirmation message is displayed", "Expected : Your carry over election has been received on "+date+" \n Actual : "+lblChainingConfirmationMsg.getText() , false);
				
//				if(StringUtils.containsIgnoreCase(tblChainingConfirmationDetails.getText(), "Plan: Test Plan_"+plan))
//					Reporter.logEvent(Status.PASS, "Verify Plan number is matching", "Expected : Plan: Test Plan_"+plan+ "\n Actual : "+tblChainingConfirmationDetails.getText(), false);
//				else
//					Reporter.logEvent(Status.FAIL,   "Verify Plan number is matching", "Expected : Plan: Test Plan_"+plan+ "\n Actual : "+tblChainingConfirmationDetails.getText(), false);
				
				if(StringUtils.containsIgnoreCase(tblChainingConfirmationDetails.getText(), "Carryover Election: "+chainingType))
					Reporter.logEvent(Status.PASS, "Verify Carryover Election is matching", "Expected : Carryover Election: "+chainingType+ "\n Actual : "+tblChainingConfirmationDetails.getText(), false);
				else
					Reporter.logEvent(Status.FAIL,   "Verify Carryover Election is matching", "Expected : Carryover Election: "+chainingType+ "\n Actual : "+tblChainingConfirmationDetails.getText(), false);
				
				
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Confirmation page is displayed", "Confirmation page not displayed", true);
				
			
		}
		
		public void verifyChainingMessage(String chainingType){
			
			if(StringUtils.containsIgnoreCase(lblChainingText.getText(), "Carryover my contribution from "+chainingType))
				Reporter.logEvent(Status.PASS, "Verify Chaining message", "Expected : Carryover my contribution from "+chainingType+ "\n Actual : "+lblChainingText.getText(), true);
			else
				Reporter.logEvent(Status.FAIL,   "Verify Chaining message","Expected : Carryover my contribution from "+chainingType+ "\n Actual : "+lblChainingText.getText(), true);
			
		}

		public void deleteActiveChainingFromDB(String ssn, String first_name) throws Exception{
			String[] sqlQuery;
			String[] sqlQuery_commit;
			sqlQuery = Stock.getTestQuery("deleteActiveChaining");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], ssn,first_name);
			DB.executeUpdate(sqlQuery[0], "commit");
			
		}
		/**
		 * <pre>
		 * This method to get the Contribution Percentage from the deferral main Page
		 * </pre>
		 *  @return String - percentage
		 */
		public String getContributionPercentage(String contibutionName) {
			String percentage=null;
			boolean isTextDisplayed=false;
			 WebElement textAgeCatchupRoth= Web.getDriver().findElement(By.xpath(txtAgeCatchupRoth.replace("webElement", contibutionName)));
		
			isTextDisplayed = Web.isWebElementDisplayed(textAgeCatchupRoth, true);
			if (isTextDisplayed) {
				percentage=textAgeCatchupRoth.getText();
			}
				
			return percentage;
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

			if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

				getText = this.getWebElement(fieldName).getText().trim();

			}

			return getText;

		}
		public void refresh(){
			lib.Web.getDriver().navigate().refresh();
		}
		
		
		/**<pre> Method to verify company Match Changes Dynamically.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyCompanyMatchChangesDynamically() throws InterruptedException
		{	
			String companyMacthBefore="";
			String companyMacthAfter="";
			boolean isMatching=true;
			lib.Web.waitForElement(radioSelectAnotherContributionRate);
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Select another Contribution radio button is clicked", false);
			
			lib.Web.waitForElement(lnkContributionRate);
			
			if(Web.isWebElementDisplayed(lnkPercent))
				this.lnkPercent.click();			
			this.lnkContributionRate.click();
			
			lib.Web.waitForElement(txtcontributionRateSlider);
			lib.Web.setTextToTextBox(txtcontributionRateSlider, "0");
			Web.clickOnElement(btnDone);
			Thread.sleep(3000);
			companyMacthBefore=txtCompanyMatch.getText().toString().trim();
            this.lnkContributionRate.click();
			lib.Web.waitForElement(txtcontributionRateSlider);
			lib.Web.setTextToTextBox(txtcontributionRateSlider, "6");
			Web.clickOnElement(btnDone);
			Thread.sleep(3000);
			companyMacthAfter=txtCompanyMatch.getText().toString().trim();
			Thread.sleep(5000);
			isMatching=Web.VerifyText(companyMacthBefore, companyMacthAfter);
			if(!isMatching)
			
				Reporter.logEvent(Status.PASS, "Verify the company match changes dynamically as the slider for the contribution rate is changed", "Company Match is Changed As Expected\nBefore:"+companyMacthBefore+"\nAfter"+companyMacthAfter, true);
			else
				Reporter.logEvent(Status.INFO, "Verify the company match changes dynamically as the slider for the contribution rate is changed", "Company Match is Not Changed As Expected\nBefore:"+companyMacthBefore+"\nAfter"+companyMacthAfter, true);
			return isMatching;
		}
		

		/**<pre> Method to verify Accuracy of pay period amount .
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyPayPeriodAmountIsMatching() throws InterruptedException
		{	
			String companyMacthcontribution="";
			String companyMacthPayPeriodAmount="";
			String standardPayPeriodAmount="";
			boolean isMatching=false;
			Web.clickOnElement(radioMaximizeToCompanyMatch);
			Web.waitForElement(txtCompanyMatchMyContribution);
			companyMacthcontribution=txtCompanyMatchMyContribution.getText().split("%")[0];
			companyMacthPayPeriodAmount=txtCompanyMatchContributionAmount.getText().toString().trim();
			Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Web.waitForElement(lnkContributionRate);
			if(Web.isWebElementDisplayed(lnkPercent))
				this.lnkPercent.click();			
			this.lnkContributionRate.click();
			
			Web.waitForElement(txtcontributionRateSlider);
			
			
			Web.setTextToTextBox(txtcontributionRateSlider, companyMacthcontribution);
			Web.clickOnElement(btnDone); 
			standardPayPeriodAmount=txtAnotherContributionAmount.getText().toString().trim();
			//keyBoard.sendKeys(Keys.TAB).perform();
			isMatching=Web.VerifyText(companyMacthPayPeriodAmount, standardPayPeriodAmount);
			if(isMatching)
			
				Reporter.logEvent(Status.PASS, "Verify the Pay Period Amount is Matching", "Pay Period Amount is Matching \nExpected:"+companyMacthPayPeriodAmount+"\nActual:"+standardPayPeriodAmount, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify the Pay Period Amount is Matching","Pay Period Amount is Not Matching \nExpected:"+companyMacthPayPeriodAmount+"\nActual:"+standardPayPeriodAmount, true);
			return isMatching;
		}
		/**<pre> Method to verify Accuracy of pay period amount .
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyAnnualCompensationDisplayed()
		{
			boolean isDisplayed=false;
				Web.waitForElement(radioMaximizeToIRSLimit);
				lib.Web.clickOnElement(radioMaximizeToIRSLimit);
				isDisplayed=txtIRSAnnualCompensationAmount.getText().contains("$");
			if(isDisplayed)
				Reporter.logEvent(Status.PASS, "Verify Annual Compensation is Displayed", "Annual Compensation is  displayed \nAnnual Compensation:"+txtIRSAnnualCompensationAmount.getText().trim(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Annual Compensation is Displayed", "Annual Compensation is not displayed \nAnnual Compensation:"+txtIRSAnnualCompensationAmount.getText().trim(), true);
			return isDisplayed;
		}
		
		
		
		/**<pre> Method to select contribution rate in Dollar.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public void select_Another_Contribution_Rate_Dollar() throws InterruptedException
		{	
			Actions keyBoard = new Actions(Web.getDriver());
			lib.Web.waitForElement(radioSelectAnotherContributionRate);
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Select another Contribution radio button is clicked", false);
			
			lib.Web.waitForElement(lnkContributionRate);
			
			if(Web.isWebElementDisplayed(lnkDollar))
				this.lnkDollar.click();			
			this.lnkContributionRate.click();
			
			lib.Web.waitForElement(txtcontributionRateSlider);
			if(lnksliderValue.getText().equals(Stock.GetParameterValue("Contribution Rate_Dollar"))){
				contrbution_rate= Integer.toString(Integer.parseInt(Stock.GetParameterValue("Contribution Rate_Dollar"))+1);
			}
			else
				contrbution_rate = Stock.GetParameterValue("Contribution Rate_Dollar");
			
			lib.Web.setTextToTextBox(txtcontributionRateSlider, contrbution_rate);
			//keyBoard.sendKeys(Keys.TAB).perform();
			Web.clickOnElement(btnDone);
			if(btnDone.isDisplayed())
			{
				//keyBoard.sendKeys(Keys.ENTER).perform();
				Web.clickOnElement(btnDone);
			}
			Thread.sleep(5000);
			Reporter.logEvent(Status.INFO, "Select Another Contribution rate", "Contribution rate is  selected to "+contrbution_rate+"$", true);
			/*boolean sliderValue=lib.Web.VerifyText(Stock.GetParameterValue("Contribution Rate"), lnksliderValue.getText().trim());			
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Contribution rate is selected to "+contrbution_rate, true);
			else
				Reporter.logEvent(Status.INFO, "Select Another Contribution rate", "Contribution rate is not selected to "+contrbution_rate, true);
			return sliderValue;*/
		}
		/**<pre> Method to verify Dollar Symbol in the Confirmation Page.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyDollarInConfirmationPage()
		{
			boolean isDisplayed=false;
				Web.waitForElement(txtTotalForDollar);
				
				isDisplayed=txtTotalForDollar.getText().contains("$");
			if(isDisplayed)
				Reporter.logEvent(Status.PASS, "Verify Confirmation Page Displays Totals of the Contribution rates in Dollars", "Totals of the Contribution rate is Displayed in Dollars\nContribution Rate:"+txtTotalForDollar.getText().trim(), true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Confirmation Page Displays Totals of the Contribution rates in Dollars", "Totals of the Contribution rate is Not Displayed in Dollars\nContribution Rate:"+txtTotalForDollar.getText().trim(), true);
			return isDisplayed;
		}
		/*
		 * This method to add auto increase when the Payroll calendar is Drop down
		 */
		public void add_Auto_Increase_Date_DropDown(String deferralType) throws InterruptedException, ParseException
		{
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			DateFormat dateFormat1= new SimpleDateFormat("M/d/yyyy");
			WebElement autoIncreaseDeferralType=this.getWebElement(deferralType);
			
			Web.waitForElement(tblhdrlblContribution);
			
			if(Web.isWebElementDisplayed(this.tblhdrlblContribution, true))
			{
				if(Web.isWebElementDisplayed(autoIncreaseDeferralType, false))
				{
				Reporter.logEvent(Status.PASS, "Verify if Add Auto Increase link is displayed", "Add Auto Increase link is displayed", true);

				autoIncreaseDeferralType.click();
				try {
					Web.waitForElement(txtAutoIncreaseMyContributionPercent);
					if(chkDeleteAutoIncrease.isDisplayed()){
						Web.clickOnElement(chkDeleteAutoIncrease);
						Web.waitForElement(btnDeleteAddAutoIncreaseModal);
						Web.clickOnElement(btnDeleteAddAutoIncreaseModal);
						Web.waitForElement(autoIncreaseDeferralType);
						Web.clickOnElement(autoIncreaseDeferralType);
						Web.waitForElement(txtAutoIncreaseMyContributionPercent);
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				Web.setTextToTextBox(txtAutoIncreaseMyContributionPercent, Stock.GetParameterValue("Auto Increase Contribution Percent"));			
				Web.setTextToTextBox(txtAutoIncreaseUntilItReachesPercent, Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
				//Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.GetParameterValue("Auto Increase Period")));
				Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, "1");
				Thread.sleep(3000);
				Web.clickOnElement(txtAutoIncreaseUntilItReachesPercent);
				payRoll_Date=drpDownAutoIncreasePeriod.getAttribute("value");
				Date date=dateFormat.parse(payRoll_Date);
				payRoll_Date=dateFormat1.format(date);
				Web.clickOnElement(btnSaveAddAutoIncreaseModal);
				Thread.sleep(5000);
				}
				else
				Reporter.logEvent(Status.FAIL, "Verify if Add Auto Increase link is displayed for : "+"'"+deferralType+"'", "Add Auto Increase link is not displayed", true);
			}
			
			
		}
		/**<pre> Method to verify Confirmation Message in the Confirmation Page.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyConfirmationMessage()
		{
			boolean isMatching=false;
				Web.waitForElement(txtConfirmationMessage);
				String confirmationMessage="";
				String actualConfirmationMessage=txtConfirmationMessage.getText().toString().trim();
				if(Stock.GetParameterValue("sdsv_subcode").equalsIgnoreCase("ADJRUN_PAYROLLDATE")){
					
					confirmationMessage="Your changes have been received and will be processed in the "+payRoll_Date+" pay cycle, subject to your plan terms and your payroll cycle.";
					isMatching=Web.VerifyText(confirmationMessage.trim(),actualConfirmationMessage);
				}
				else if(Stock.GetParameterValue("sdsv_subcode").equalsIgnoreCase("ADJRUNDATE")){
					
					confirmationMessage="Your changes will be reported to your plan sponsor on "+payRoll_Date+" and the changes will be effective as soon as administratively feasible, subject to your plan terms and your payroll cycle.";
					isMatching=Web.VerifyText(confirmationMessage.trim(),actualConfirmationMessage);
				}
				else if(Stock.GetParameterValue("sdsv_subcode").equalsIgnoreCase("GENERIC")){
					
					confirmationMessage="Your changes will be reported to your plan sponsor, and will be processed as soon as administratively feasible, subject to your plan terms and your payroll cycle.";
					isMatching=Web.VerifyText(confirmationMessage.trim(),actualConfirmationMessage);
				}
			if(isMatching)
				Reporter.logEvent(Status.PASS, "Verify Confirmation Page Displays With Sucess Message", "Confirmation Message is Matching\nExpected:"+confirmationMessage+"\nActual:"+actualConfirmationMessage, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Confirmation Page Displays With Sucess Message", "Confirmation Message is Not Matching\nExpected:"+confirmationMessage+"\nActual:"+actualConfirmationMessage, true);
			return isMatching;
		}
		
		/**<pre> Method to verify Contribution Type in the Confirmation Page.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public boolean verifyContributionTypeIsDisplayedInConfirmationPage(String deferralType)
		{
			boolean isDisplayed=false;
				Web.waitForElements(txtDeferralType);
				for(int i=0;i<txtDeferralType.size();i++)
				{
					if(txtDeferralType.get(i).getText().contains(deferralType)){
						isDisplayed=true;
						break;
					}
				}
				
			return isDisplayed;
		}
		
		/**<pre> Method to Click on Pending Deferral.
		 *.</pre>
		 * 
		 * 		 * @throws InterruptedException 
		 */
		public void ClickPendingDeferralLink(String deferralType)
		{
			
			WebElement lnkPendingDeferral=Web.getDriver().findElement(By.xpath(pendingDeferral.replaceAll("DeferralType", deferralType))) ;
				Web.waitForElement(lnkPendingDeferral);
				Web.clickOnElement(lnkPendingDeferral);
				Web.waitForElement(headerPendingDeferral);
								
		}
		
		/**<pre> Method to Verify Pending Deferral Change Modal .
		 *.</pre>
		 * 
		 * 		 * @throws InterruptedException 
		 */
		public void verifyPendingDeferralModal(String deferralType)
		{
			Web.waitForElement(headerPendingDeferral);
			if (Web.isWebElementDisplayed(headerPendingDeferral))
				Reporter.logEvent(Status.PASS, "Verify Pending Change Modal Is Displayed",
						"Pending Change Modal Is Displayed", true);
						else
				Reporter.logEvent(Status.FAIL, "Verify Pending Change Modal Is Displayed",
						"Pending Change Modal Is Not Displayed", true);
			
			WebElement txtPendingDeferral=Web.getDriver().findElement(By.xpath(txtpendingDeferral.replaceAll("DeferralType", deferralType)));
			
			if (Web.VerifyPartialText(deferralType, txtPendingDeferral.getText().toString().trim(), true))
				Reporter.logEvent(Status.PASS, "Verify 'Deferral Type' Is Displayed in Pending Change Modal",
						"'Deferral Type' Is Displayed in Pending Change Modal\nExpected:"+deferralType+"\nActual:"+ txtPendingDeferral.getText().toString().trim(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Deferral Type' Is Displayed in Pending Change Modal",
						"'Deferral Type' Is Not Displayed in Pending Change Modal\nExpected:"+deferralType+"\nActual:"+ txtPendingDeferral.getText().toString().trim(), true);
			
			if (Web.isWebElementDisplayed(btnDeletePendingDeferral))
				Reporter.logEvent(Status.PASS, "Verify 'Delete' Button  Is Displayed in Pending Change Modal",
						"'Delete' Button  Is Displayed in Pending Change Modal", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Delete' Button  Is Displayed in Pending Change Modal",
						"'Delete' Button  Is Not Displayed in Pending Change Modal", true);
			
			if (Web.isWebElementDisplayed(btnClosePendingDeferral))
				Reporter.logEvent(Status.PASS, "Verify 'Close' Button  Is Displayed in Pending Change Modal",
						"'Close' Button  Is Displayed in Pending Change Modal", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Close' Button  Is Displayed in Pending Change Modal",
						"'Close' Button  Is Not Displayed in Pending Change Modal", true);
			
								
		}
		
		/**<pre> Method to Verify Pending Deferral link is displayed.
		 *.</pre>
		 * 
		 * String deferralType
		 * 
		 *@throws InterruptedException 
		 */
		public boolean verifyPendingDeferralLinkisDisplayed(String deferralType)
		{
			boolean isDisplayed=false;
					try{
						WebElement lnkPendingDeferral=Web.getDriver().findElement(By.xpath(pendingDeferral.replaceAll("DeferralType", deferralType))) ;
					
				if(Web.isWebElementDisplayed(lnkPendingDeferral))
				{
					isDisplayed=true;		
				}
					}catch(NoSuchElementException ne)
					{
						 isDisplayed=false;
					}
				return isDisplayed;
		}
		
		/**<pre> Method to Verify Deleted Deferral In confirmation Page .
		 *.</pre>
		 * 
		 * 		 * @throws InterruptedException 
		 */
		public void verifyDeletedDeferralinConfirmationPage(String deferralType)
		{
			Web.waitForElement(txtPendingTrnsaction);
			if (Web.isWebElementDisplayed(txtPendingTrnsaction))
				Reporter.logEvent(Status.PASS, "Verify Pending Tnsaction Is Displayed",
						"Pending Transaction Is Displayed", true);
						else
				Reporter.logEvent(Status.FAIL, "Verify Pending Tnsaction Is Displayed",
						"Pending Transaction Is Not Displayed", true);
			
			
			
			if (Web.VerifyText(deferralType, txtPendingDeferral.getText().toString().trim(), true))
				Reporter.logEvent(Status.PASS, "Verify Deleted Pending 'Deferral Type' Is Displayed in Confirmation Page",
						"Deleted Pending 'Deferral Type' Is Displayed in Confirmation Page\nExpected:"+deferralType+"\nActual:"+ txtPendingDeferral.getText().toString().trim(), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Deferral Type' Is Displayed in Confirmation Page",
						"Deleted Pending 'Deferral Type' Is Not Displayed in Confirmation Page\nExpected:"+deferralType+"\nActual:"+ txtPendingDeferral.getText().toString().trim(), true);
			
			
			
								
		}
		/**<pre> Method to Verify Pending Deferral is cancelled or not in DB.
		 *.</pre>
		 * 
		 * String queryName
		 * @throws SQLException 
		 * 
		 */
		public String getPendingDeferralStatusCodeFromDB(String queryName) throws SQLException
		{
			String[] sqlQuery = null;
			ResultSet statusCode = null;
			
			try{
			sqlQuery = Stock.getTestQuery(queryName);
			}
		 catch (Exception e) {
			e.printStackTrace();
		}

			statusCode = DB.executeQuery(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("username").substring(0, 9));

		if (DB.getRecordSetCount(statusCode) > 0) {
			try {
				statusCode.first();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
	

return statusCode.getString("status_code");
				
		}
		
		/**<pre> Method to Verify Do you want to save your changes modal is Displayed .
		 *.</pre>
		 * 
		 * 	
		 */
		public void verifySaveYourChangesModal()
		{
			Web.waitForElement(hdrSaveChangesModal);
			if (Web.isWebElementDisplayed(btnDontSave))
				Reporter.logEvent(Status.PASS, "Verify 'Don't Save' Button Is Displayed",
						"'Don't Save' Button Is Displayed", false);
						else
				Reporter.logEvent(Status.FAIL, "Verify'Don't Save' Button Is Displayed",
						"'Don't Save' Button Is Not Displayed", true);
			
			if (Web.isWebElementDisplayed(btnStay))
				Reporter.logEvent(Status.PASS, "Verify 'Stay' Button Is Displayed",
						"'Stay' Button Is Displayed", false);
						else
				Reporter.logEvent(Status.FAIL, "Verify 'Stay' Button Is Displayed",
						"'Stay' Button Is Not Displayed", true);
													
		}
		
		/**<pre> Method to Verify Combined Rule Error Message.
		 * 
		 *.</pre>
		 * @param String[] deferralTypes(Ex:Before Tax, After Tax, Roth etc.)
		 * @param String RuleType(Min/Max)
		 * 	
		 */
		public void verifyCombinedRuleErrorMessage(String ruleType,String CombinedlimitValue,String...deferralTypes )
		{
			Web.waitForElement(txtCombinedRuleErrorMsg);
			String expectedErrorMsg="";
			String actualErrorMsg=txtCombinedRuleErrorMsg.getText().toString().trim();
			if (ruleType.equalsIgnoreCase("Minimum")){
			int i=deferralTypes.length;	
			expectedErrorMsg="The sum of contributions for "+deferralTypes[0]+" and "+deferralTypes[1]+" must be no less than "+CombinedlimitValue;
			if(Web.VerifyText(expectedErrorMsg, actualErrorMsg))
				Reporter.logEvent(Status.PASS, "Verify Error Message for Minimum Combined Rule is Displayed",
						"Error Message for Minimum Combined Rule is Displayed and Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, false);
						else
				Reporter.logEvent(Status.FAIL, "Verify Error Message for Minimum Combined Rule is Displayed",
						"Error Message for Minimum Combined Rule is Displayed and Not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, true);
			}
			if (ruleType.equalsIgnoreCase("Maximum")){
				expectedErrorMsg="The sum of contributions for "+deferralTypes[0]+" and "+deferralTypes[1]+" must be no more than "+CombinedlimitValue;
			if(Web.VerifyText(expectedErrorMsg, actualErrorMsg,false))
			Reporter.logEvent(Status.PASS, "Verify Error Message for Maximum Combined Rule is Displayed",
					"Error Message for Maximum Combined Rule is Displayed and Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, false);
					else
			Reporter.logEvent(Status.FAIL, "Verify Error Message for Maximum Combined Rule is Displayed",
					"Error Message for Maximum Combined Rule is Displayed and Not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, true);

		}
		}
		
		/**<pre> Method to Verify Tired Error Message.
		 * 
		 *.</pre>
		 * @param String[] deferralTypes(Ex:STANDARD, AFTERTX, CATCHUP etc.)
		 * @param String tieredLimitValue
		 * @param String requiredDeferral
		 * @param String contributingDeferral
		 * 	
		 */
		public void verifyTieredRuleErrorMessage(String tieredLimitValue,String deferralType,String requiredDeferral , String contributingDeferral )
		{
			WebElement lnkdeferralTiredRule=Web.getDriver().findElement(By.xpath(deferralTiredRule.replaceAll("DeferralType", deferralType)));
			Web.waitForElement(lnkdeferralTiredRule);
			String expectedErrorMsg="";
			String actualErrorMsg=lnkdeferralTiredRule.getAttribute("uib-tooltip-html").toString().split("'<ul class=\"margin-left-100 margin-top\"><li class=\"margin-bottom\">")[1].split("</li></ul>")[0];
			
			//String actualErrorMsg=lnkdeferralTiredRule.getText().toString().trim();
			
			expectedErrorMsg="You must contribute "+tieredLimitValue+" in "+requiredDeferral+" prior to contributing to "+contributingDeferral;
			if(Web.VerifyText(expectedErrorMsg, actualErrorMsg))
				Reporter.logEvent(Status.PASS, "Verify Error Message for Minimum Tiered Rule is Displayed",
						"Error Message for Minimum Tiered Rule is Displayed and Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, false);
						else
				Reporter.logEvent(Status.FAIL, "Verify Error Message for Minimum Tiered Rule is Displayed",
						"Error Message for Minimum Tiered Rule is Displayed and Not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMsg, true);
			

	
		}
		
		/**<pre> Method to Verify Company Match Rule Description .
		 *.</pre>
		 * @throws Exception 
		 * 
		 * 	
		 */
		public String getCompanyMatchRuleDescription(String minServiceYrs) throws Exception
		{
		
			ResultSet companyMatchRule = null;
			Web.waitForElement(txtCompanyMatchRuleDesc);
			String[] sqlQuery=Stock.getTestQuery(Stock.GetParameterValue("QueryGetRuleDescription"));
			companyMatchRule=DB.executeQuery(sqlQuery[0], sqlQuery[1],Stock.GetParameterValue("PlanID"),minServiceYrs);

		if (DB.getRecordSetCount(companyMatchRule) > 0) {
			
			try {
				companyMatchRule.first();
				
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
			
		
		}
	

return companyMatchRule.getString("rule_desc");
			
													
		}
		
		
		/**<pre> Method to Verify Company Match Rule Description .
		 *.</pre>
		 * @throws Exception 
		 * 
		 * 	
		 */
		public void verifyCompanyMatchChangesForRuleA(int minYrsOfService,String contributionRate,float companyMatchPercentage) throws Exception
		{
			String hireDate="";
			
			String expectedCompanyMatch="";
			String expectedCompanyMatchRule="";
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			
			Web.waitForElement(txtCompanyMatchRuleDesc);
			if(minYrsOfService<2){
				calendar.add(Calendar.YEAR, -1);
				hireDate=dateFormat.format(calendar.getTime());
				expectedCompanyMatch=calculateCompanyMatchforRuleA(contributionRate, companyMatchPercentage, hireDate);
				}
			if(minYrsOfService<5){
				calendar.add(Calendar.YEAR, -4);
				hireDate=dateFormat.format(calendar.getTime());
				expectedCompanyMatch=calculateCompanyMatchforRuleA(contributionRate, companyMatchPercentage, hireDate);
				expectedCompanyMatchRule=getCompanyMatchRuleDescription("2");
				}
			if(minYrsOfService>=5){
				calendar.add(Calendar.YEAR, -6);
				hireDate=dateFormat.format(calendar.getTime());
				expectedCompanyMatch=calculateCompanyMatchforRuleA(contributionRate, companyMatchPercentage, hireDate);
				expectedCompanyMatchRule=getCompanyMatchRuleDescription("5");

				}
			String actualCompanyMatch=txtCompanyMatch.getText().toString().trim();
			if(Web.VerifyText(expectedCompanyMatch+"%", actualCompanyMatch, false))
				Reporter.logEvent(Status.PASS, "Verify CompanyMatch is Displayed As Per Tired Rule",
						"Verify CompanyMatch is Displayed As Per Tired Rule\nExpected:"+expectedCompanyMatch+"%"+"\nActual:"+actualCompanyMatch, false);
						else
				Reporter.logEvent(Status.FAIL, "Verify CompanyMatch is Displayed As Per Tired Rule",
						"Verify CompanyMatch is Not Displayed As Per Tired Rule\nExpected:"+expectedCompanyMatch+"%"+"\nActual:"+actualCompanyMatch, true);
			
			String actualCompanyMatchRule=txtCompanyMatchRuleDesc.getText().toString().trim();
			if(Web.VerifyText(expectedCompanyMatchRule, actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed ",
						"COMPANY MATCH is displayed for RULEA for"+minYrsOfService+"Years and Matching\nExpected:"+expectedCompanyMatchRule+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA for"+minYrsOfService+"Years and Not Matching\nExpected:"+expectedCompanyMatchRule+"\nActual:"+actualCompanyMatchRule, true);

			}

		
/**<pre> Method to Verify Verify Company Match is Displayed in Select Another Contribution .
 *.</pre>
 * @throws Exception 
 * 
 * 	
 */
public String calculateCompanyMatchforRuleA(String contributionRate, float companyMatchPercentage,String hireDate) throws Exception
{	
	String expectedCompanyMatch="";
try{
	
	String[] sqlQuery=Stock.getTestQuery(Stock.GetParameterValue("QueryUpdateEmployeeHireDate"));
	DB.executeUpdate(sqlQuery[0], sqlQuery[1],hireDate,Stock.GetParameterValue("username").substring(0, 9));
	//DB.executeUpdate(sqlQuery[0], sqlQuery[1],hireDate,Stock.GetParameterValue("username").substring(0, 9));
	Web.getDriver().navigate().refresh();
	Web.waitForElement(txtCompanyMatchRuleDesc);
	this.lnkPercent.click();			
	this.lnkContributionRate.click();
	Web.waitForElement(txtcontributionRateSlider);
	Web.setTextToTextBox(txtcontributionRateSlider, contributionRate);
	Web.clickOnElement(btnDone);
	if(btnDone.isDisplayed())
	{
		//keyBoard.sendKeys(Keys.ENTER).perform();
		Web.clickOnElement(btnDone);
	}
	
	 expectedCompanyMatch=Double.toString(Integer.parseInt(contributionRate)*companyMatchPercentage);
}
catch(NoSuchElementException e){
	
}
return expectedCompanyMatch;
}
		
		/**<pre> Method to Verify Verify Company Match is Displayed in Select Another Contribution .
		 *.</pre>
		 * 
		 * 	
		 */
		public boolean verifyCompanyMatchDisplayed()
		{	
			boolean isDispalyed=false;
		try{
		    if(txtCompanyMatch.isDisplayed())
			isDispalyed=true;
		}
		catch(NoSuchElementException e){
			isDispalyed=false;
		}
		return isDispalyed;
		}
		
		
		/**<pre> Method to Verify 457 IRS Plan Catch Up Period Error Message.
		 * 
		 *.</pre>
		 * @param String errorMessage
		 * 	
		 */
		public void verifyCatchUpPeriodErrorMessage(String errorMessage)
		{
			Web.waitForElement(txtCompanyMatchErrorMsg);
			
			String actualErrorMsg=txtCompanyMatchErrorMsg.getText().toString().trim();
			try{
			if(txtCompanyMatchErrorMsg.isDisplayed()){
			if(Web.VerifyText(errorMessage, actualErrorMsg))
				Reporter.logEvent(Status.PASS, "Verify Error Message for457 IRS Plan Catch Up Period is Displayed",
						"Error Message for457 IRS Plan Catch Up Period is Displayed and Matching\nExpected:"+errorMessage+"\nActual:"+actualErrorMsg, false);
						else
				Reporter.logEvent(Status.FAIL, "Verify Error Message for457 IRS Plan Catch Up Period is Displayed",
						"Error Message for457 IRS Plan Catch Up Period is Not Matching \nExpected:"+errorMessage+"\nActual:"+actualErrorMsg, true);
			
		}
			}
			catch(NoSuchElementException e){
				Reporter.logEvent(Status.FAIL, "Verify Error Message for457 IRS Plan Catch Up Period is Displayed",
						"Error Message for457 IRS Plan Catch Up Period is Not Displayed \nExpected:"+errorMessage+"\nActual:"+actualErrorMsg, true);
			}
		
		}
		
		
		/**<pre> Method to Verify View Only Label For deferral type
		 * 	
		 */
		public void verifyViewOnlyLabelDisplayed(String deferralType)
		{
			WebElement lableViewOnly=Web.getDriver().findElement(By.xpath(lableViewOnlyABONUS.replaceAll("DeferralType", deferralType)));

			if(Web.isWebElementDisplayed(lableViewOnly))
			
				Reporter.logEvent(Status.PASS, "Verify View Only Label is Displayed for"+deferralType,
						"View Only Label is Displayed for"+deferralType, true);
						else
				Reporter.logEvent(Status.FAIL, "Verify View Only Label is Displayed for"+deferralType,
						"View Only Label is Not Displayed for"+deferralType, true);
		
		}
		
		/**<pre> Method to Verify input field is greyed out For deferral type
		 * 	
		 */
		public void verifyInputFieldGreyedOutForViewOnlyDeferral(String deferralType)
		{
			WebElement eleInpViewOnly=Web.getDriver().findElement(By.xpath(inpViewOnly.replaceAll("DeferralType", deferralType)));

			if(eleInpViewOnly.getAttribute("ng-readonly").equalsIgnoreCase("true"))
			
				Reporter.logEvent(Status.PASS, "Verify Input Field for"+deferralType+"is Greyed Out",
						"Input Field for"+deferralType+"is Greyed Out", true);
						else
				Reporter.logEvent(Status.FAIL, "Verify Input Field for"+deferralType+"is Greyed Out",
						"Input Field for"+deferralType+"is Not Greyed Out", true);
		
		}
		/**
	   	 * <pre>
	   	 * Method to Verify Webelement is Displayed
	   	 * 
	   	 * </pre>
	   	*/
	    public boolean verifyWebElementDisplayed(String fieldName) {

			boolean isDisplayed = Web.isWebElementDisplayed(
					this.getWebElement(fieldName), true);

			if (isDisplayed) {

				Reporter.logEvent(Status.PASS, "Verify \'"+fieldName+"\' is displayed",
						"\'"+fieldName+"\' is displayed", false);
				isDisplayed = true;

			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify\'"+fieldName+"\' is displayed",
						"\'"+fieldName+"\' is not displayed", false);
			}

			return isDisplayed;

		}
	    
	    /**
		 * <pre>
		 * Method to Verify Text Field is Displayed on the WebPage
		 * </pre>
		 * 
		 * @return boolean isTextDisplayed
		 */

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
		
		/**<pre> Method to Verify Contribution Rate Page For Enrollment Flow
		 * 	
		 */
		public void verifyContributionRatePage()
		{
			verifyWebElementDisplayed("Select Another Contribution Radio Button");
			isTextFieldDisplayed("My Standard Contribution");
			verifyWebElementDisplayed("Percent Button");
			verifyWebElementDisplayed("Dollar Button");
			verifyWebElementDisplayed("Continue Button");
			verifyWebElementDisplayed("Back Button");
			isTextFieldDisplayed("The estimated amount to be deducted from your paycheck is based on the salary provided times your contribution rate.");
			
	}		
		
		/**<pre> Method to Verify Plan Rules PopUp
		 * 	
		 */
		public void verifyPlanRulesPopUp()
		{	
	
		Web.clickOnElement(lnkPlanRules);
		Web.waitForElement(headerPlanRule);

		if (Web.isWebElementDisplayed(headerPlanRule))
			Reporter.logEvent(Status.PASS, "Verify Pan Rule PopUp is Displayed",
					"Pan Rule PopUp is Displayed", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Pan Rule PopUp is Displayed",
					"Pan Rule PopUp is Not Displayed", true);
		
		if (Web.isWebElementDisplayed(txtPlanRule))
			Reporter.logEvent(Status.PASS, "Verify Pan Rule Text is Displayed and Proper",
					"Pan Rule Text is Displayed and Proper", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Pan Rule Text is Displayed and Proper",
					"Pan Rule Text is Not Proper", false);
		Web.clickOnElement(btnOk);
		}
		
		public void verifyContributionTypesDisplayed()
		{		
			
			Web.waitForElement(radioSplitContribution);
			
				if(lib.Web.isWebElementDisplayed(this.radioSplitContribution)  )  
						
					
					Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is Displayed", "Split Contibution is Displayed", true);
				
				else
					Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is Displayed", "Split Contibution is not Displayed", true);
		
		
				if(lstradioSelectContibution.size()==2){
					if(StringUtils.containsIgnoreCase(lstradioSelectContibution.get(0).getText(), "Before"))
						Reporter.logEvent(Status.PASS, "Verify Before Tax Radio button is Displayed", "Before Tax Contibution is Displayed", false);
					
					else
						Reporter.logEvent(Status.FAIL, "Verify Before Tax Radio button is Displayed", "Before Tax Contibution is not Displayed", false);
					
					if(StringUtils.containsIgnoreCase(lstradioSelectContibution.get(1).getText(), "Roth"))
						Reporter.logEvent(Status.PASS, "Verify Roth Radio button is Displayed", "Roth Contibution is Displayed", false);
					
					else
						Reporter.logEvent(Status.FAIL, "Verify Roth Radio button is Displayed", "Roth Contibution is not Displayed", false);
				
			}
			
			this.btnContinue.click();
				
		}
		public void selectContributionType(String contributionType)
		{		
			Web.waitForElement(radioSplitContribution);
			
			if(contributionType.equalsIgnoreCase("Split") ){
				if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)  )  
				{				
					this.radioSplitContribution.click();
					Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", true);
					
					
					if(Stock.GetParameterValue("Contributing_type").equalsIgnoreCase("Maximize to irs limit")){
//					int befor_tax=irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth")));
//						befor_tax=irs_limit-(Float.parseFloat(Stock.GetParameterValue("Split_Tax_roth")));
//						lib.Web.setTextToTextBox(txtSplitBeforeTax, Float.toString(befor_tax));
//						lib.Web.setTextToTextBox(txtSplitRothTax, Stock.GetParameterValue("Split_Tax_roth"));
						before_tax =Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*irs_limit;
						roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*irs_limit;
						lib.Web.setTextToTextBox(txtSplitBeforeTax, new DecimalFormat("##.##").format(before_tax));
						lib.Web.setTextToTextBox(txtSplitRothTax, new DecimalFormat("##.##").format(roth));
					}
					else{
//						lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.GetParameterValue("Split_Tax_before"));
//                        int i=Integer.parseInt(contrbution_rate);
//                        int j=Integer.parseInt(Stock.GetParameterValue("Split_Tax_before"));
//                        lib.Web.setTextToTextBox(txtSplitRothTax,Integer.toString(i-j));
						
						before_tax = Float.parseFloat(Stock.GetParameterValue("before_tax_ratio"))*Float.parseFloat(contrbution_rate);
						roth = Float.parseFloat(Stock.GetParameterValue("roth_ratio"))*Float.parseFloat(contrbution_rate);
						lib.Web.setTextToTextBox(txtSplitBeforeTax, new DecimalFormat("##.##").format(before_tax));
						lib.Web.setTextToTextBox(txtSplitRothTax, new DecimalFormat("##.##").format(roth));
					}
							
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
			}
			else{
				for(int i=0;i<lstradioSelectContibution.size();i++){
					if(StringUtils.containsIgnoreCase(lstradioSelectContibution.get(i).getText(), contributionType))
					{
						lstradioSelectContibution.get(i).click();
						Reporter.logEvent(Status.PASS, "Verify "+contributionType+" Contribution is selected", contributionType+" Contribution is selected", false);
						break;
					}
				}
			}
			
			this.btnContinue.click();
				
		}
		
		/**<pre> Method to select contribution rate for Enrollment flow.
		 *.</pre>
		 * 
		 * @return - boolean
		 * @throws InterruptedException 
		 */
		public void selectContributionWhileEnroll() throws InterruptedException
		{	
			Actions keyBoard = new Actions(Web.getDriver());
			
			Web.waitForElement(lnkContributionRate);
			
			if(Web.isWebElementDisplayed(lnkPercent))
				this.lnkPercent.click();
			else if(Web.isWebElementDisplayed(lnkDollar))
					this.lnkDollar.click();
			
			this.lnkContributionRate.click();
			
			Web.waitForElement(inpcontributionRateSlider);
			if(lnksliderValue.getText().equals(Stock.GetParameterValue("Contribution Rate"))){
				contrbution_rate= Integer.toString(Integer.parseInt(Stock.GetParameterValue("Contribution Rate"))+1);
			}
			else
				contrbution_rate = Stock.GetParameterValue("Contribution Rate");
			
			Web.setTextToTextBox(inpcontributionRateSlider, contrbution_rate);
			//keyBoard.sendKeys(Keys.TAB).perform();
			Web.clickOnElement(btnDone);
			if(btnDone.isDisplayed())
			{
				//keyBoard.sendKeys(Keys.ENTER).perform();
				Web.clickOnElement(btnDone);
			}
			Thread.sleep(5000);
			Reporter.logEvent(Status.INFO, "Add Contribution rate", "Contribution rate is changed to "+contrbution_rate+"%", true);
			
		}
		
	/**
	 * <pre>
	 * Method to get Available deferrals from Deferral Election Page
	 * </pre>
	 * @return availableDeferralTypes
	 * @throws Exception
	 * 
	 * 
	 */
	public List<String> getAvailableDeferrals() throws Exception {
		List<String> availableDeferralTypes = new ArrayList<String>();
		Web.waitForElements(txtDeferralTypes);
		for (int i = 0; i < txtDeferralTypes.size(); i++) {
			String deferralType=txtDeferralTypes.get(i).getText().replaceAll("FROM", "").trim();
			availableDeferralTypes
					.add(deferralType);
		}

		return availableDeferralTypes;

	}
	
	

}
	


