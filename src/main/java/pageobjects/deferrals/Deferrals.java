package pageobjects.deferrals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.general.LeftNavigationBar;
import lib.*;
import lib.Reporter.Status;

public class Deferrals extends LoadableComponent<Deferrals> {
	
	//Declarations
			private LoadableComponent<?> parent;
			private static boolean waitforLoad = false;	
			public float irs_limit;
			public float befor_tax;
			//My Contributions Page
			@FindBy(xpath=".//div[@class='page-title ng-scope']/h1") private WebElement lblMyContributions;
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
			@FindBy(xpath=".//strong[contains(text(),'Maximize to the company')]")
			private WebElement radioMaximizeToCompanyMatch;
			@FindBy(xpath=".//strong[contains(text(),'IRS limit')]")
			private WebElement radioMaximizeToIRSLimit;		
			@FindBy(xpath="//*[contains(@class, 'editable-text-trigger')]")
			private WebElement lnkContributionRate;
			@FindBy(id="contributionRateSlider-text-edit") private WebElement txtcontributionRateSlider;
			@FindBy(xpath=".//span[@class='valueAndEnd']") private WebElement lnksliderValue;
//			@FindBy(xpath=".//button[text()[normalize-space()='Done']]") private WebElement btnDone;
			@FindBy(xpath="//button[@class='btn btn-primary reset-padding ng-binding']") private WebElement btnDone;
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
			@FindBy(id="ROTH") private WebElement txtSplitRothTax;
			@FindBy(xpath=".//button[text()[normalize-space()='Continue']]") private WebElement btnContinueBeforeTaxSplitContribution;
			@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBackBeforeTaxSplitContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-percentage ng-binding']") private WebElement txtCompanyMatchMyContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-amount']") private WebElement txtCompanyMatchContributionAmount;
			@FindBy(xpath="//div[@class='contribution-percentage ng-binding']") private WebElement txtIRSMyContribution;
			@FindBy(xpath="//div[@class='contribution-amount']/p[@class='ng-binding']") private WebElement txtIRSContributionAmount;
							
			//Add Auto Increase		
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'After-Tax')]/../td[3]/.//a") private WebElement lnkAfterTaxAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[3]/.//a") private WebElement lnkBeforeTaxAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Catch')]/../td[3]/.//a") private WebElement lnkCatchupAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before Bonus')]/../td[3]/.//a") private WebElement lnkBeforeBonusAutoIncrease;
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
//			@FindBy(name="effectiveDate") private WebElement drpDownAutoIncreasePeriod;
			@FindBy(xpath="//select[@name='effectiveDate']") private WebElement drpDownAutoIncreasePeriod;
			@FindBy(xpath=".//button[text()[normalize-space()='Save']]") private WebElement btnSaveAddAutoIncreaseModal;
			@FindBy(xpath=".//button[text()[normalize-space()='Cancel']]") private WebElement btnCancelAddAutoIncreaseModal;
			@FindBy(xpath=".//h2[text()[normalize-space()='Contribution Details']]") private WebElement lblContributionDetails;
			@FindBy(id="deleteAutoIncr") private WebElement chkDeleteAutoIncrease;
			@FindBy(xpath=".//button[text()[normalize-space()='Delete']]") private WebElement btnDeleteAddAutoIncreaseModal;
			
			@FindBy(xpath="//h2[text()[normalize-space()='Bonus']]") private WebElement lblBonus;
			@FindBy(xpath="//input[@type='checkbox']") private WebElement chkboxMaximize;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[3]") private WebElement txtMaximizeMeAlwaysBefore;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth')]/../td[3]") private WebElement txtMaximizeMeAlwaysRoth;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Catch-Up Before')]/../td[3]") private WebElement txtMaximizeMeAlwaysCatchupBefore;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[1]") private WebElement txtBeforeTaxContributionAmt;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Roth')]/../td[1]") private WebElement txtRothContributionAmt;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Age Catch-Up Before')]/../td[1]") private WebElement txtCatchupBeforeAmt;
			
			
			@FindBy(xpath="//table[@class='deferrals-breakdown ng-scope']/tbody") private WebElement tblContributionDetails;
			@FindBy(xpath="//table[@class='responsive-tables contributions-cart']/tbody") private WebElement tblMyContributions;
			
			//view only
			@FindBy(xpath = ".//div[@class='clearfix']/.//h2") private List<WebElement> headersContrPage;
			@FindBy(xpath = "(//td[@class='ng-scope']/button)[3]") private WebElement btnEditAftertaxNoSplit;
			@FindBy(xpath = "//span[contains(text(),'More Options')]") private WebElement linkMoreOptions;
			@FindBy(id = "AGERTH") private WebElement rothCatchUprate;
			@FindBy(id = "AGEBEF") private WebElement txtCatchupRate;
//			@FindBy(xpath = ".//*[@id='splitContribution']/.//span[2]") private WebElement txtViewOnly;
			@FindBy(xpath = "//span[text()[normalize-space()='View Only']]") private WebElement txtViewOnly;
			@FindBy(xpath = "//table[@class='table-details']//th[contains(text(),'Confirmation Number')]/../td") private WebElement txtConfirmationNo;
			
			@FindBy(id = "BEFORE") private WebElement bfrtaxRate;
			@FindBy(id = "ROTH") private WebElement txtRothtaxRate;
			@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
			@FindBy(linkText="Log out") private WebElement lnkLogout;
			@FindBy(xpath="//span[@class='editable-text-trigger ng-binding']") private WebElement txtMyCatchupContr;
			@FindBy(id = "contributionInput") private WebElement txtAnnualCompensation;
			@FindBy(xpath = ".//span[text()[normalize-space()='Update']]") private WebElement btnUpdate;
			@FindBy(xpath = "//div[contains(@class,'alert')]/p") private WebElement lblAlertMsg;
		/**
		 * Default Constructor
		 */
		public Deferrals() {
		
			this.parent = new LeftNavigationBar();
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		/** Argument Constructor with parent as input
		 * 
		 * @param parent
		 */
		public Deferrals(LoadableComponent<?> parent) {
			this.parent = parent;			
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {

			
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			String ssn = Stock.GetParameterValue("userName");
			ResultSet strUserInfo = null;
			try {
				strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String userFromDatasheet = null;
			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			
			String userLogedIn = this.lblUserName.getText();
			if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
				Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
				if (!lib.Web.isWebElementDisplayed(lblMyContributions,Deferrals.waitforLoad)) {
					Deferrals.waitforLoad = true;
					throw new Error("'My contributions' page is not loaded");
				}else{
					Deferrals.waitforLoad = false;
				}
			} else {
				this.lnkLogout.click();
				Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			}
		}
		
		@Override
		protected void load() {
			this.parent.get();	
			
			((LeftNavigationBar) this.parent).clickNavigationLink("My contributions");
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
			if(fieldName.trim().equalsIgnoreCase("Catch Up Add Auto Increase")) {
				return this.lnkCatchupAutoIncrease;
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
            if(lib.Web.isWebElementDisplayed(this.lnkMoreOptions))
            	this.lnkMoreOptions.click();
           
			lib.Web.waitForElement(deferral);
			
            lib.Web.clickOnElement(deferral);
			if(lib.Web.isWebElementDisplayed(btnContinue, true))				
             isDisplayed = true;			
            return isDisplayed;           

		}
		
		/**<pre> Method to select contribution rate.
		 *.</pre>
		 * 
		 * @return - boolean
		 */
		public boolean click_Select_Your_Contribution_Rate()
		{	
			
			lib.Web.waitForElement(radioSelectAnotherContributionRate);
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Select another Contribution radio button is clicked", false);
			
			lib.Web.waitForElement(lnkContributionRate);
			
			if(Web.isWebElementDisplayed(lnkPercent))
				this.lnkPercent.click();			
			this.lnkContributionRate.click();
			
			lib.Web.waitForElement(txtcontributionRateSlider);
			lib.Web.setTextToTextBox(txtcontributionRateSlider, Stock.GetParameterValue("Contribution Rate"));			
			this.btnDone.click();
			boolean sliderValue=lib.Web.VerifyText(Stock.GetParameterValue("Contribution Rate"), lnksliderValue.getText());			
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Contribution rate is selected to"+Stock.GetParameterValue("Contribution Rate"), false);
			else
				Reporter.logEvent(Status.FAIL, "Select Another Contribution rate", "Contribution rate is not selected to"+Stock.GetParameterValue("Contribution Rate"), false);
			return sliderValue;
		}
		
		/**<pre> Method to add auto increase.
		 *.</pre>
		 * 
		 */
		public void add_Auto_Increase(String deferralType)
		{
			WebElement autoIncreaseDeferralType=this.getWebElement(deferralType);
			
			lib.Web.waitForElement(tblhdrlblContribution);
			

			if(lib.Web.isWebElementDisplayed(this.tblhdrlblContribution, true))
			{
				if(lib.Web.isWebElementDisplayed(autoIncreaseDeferralType, false))
				{
				Reporter.logEvent(Status.PASS, "Verify if Add Auto Increase link is displayed", "Add Auto Increase link is displayed", false);
//				this.lnkBeforeTaxAutoIncrease.click();
				autoIncreaseDeferralType.click();
				try {
					lib.Web.waitForElement(txtAutoIncreaseMyContributionPercent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				lib.Web.setTextToTextBox(txtAutoIncreaseMyContributionPercent, Stock.GetParameterValue("Auto Increase Contribution Percent"));			
				lib.Web.setTextToTextBox(txtAutoIncreaseUntilItReachesPercent, Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
				lib.Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.GetParameterValue("Auto Increase Period")));
				this.btnSaveAddAutoIncreaseModal.click();
				}
				else
				Reporter.logEvent(Status.FAIL, "Verify if Add Auto Increase link is displayed", "Add Auto Increase link is not displayed", true);
			}
			
			
		}
		
		/**<pre> Method to verify confirmation page.
		 *.</pre>
		 * 
		 */
		public void myContributions_Confirmation_Page()
		{
		
			lib.Web.waitForElement(btnConfirmAndContinue);
			lib.Web.clickOnElement(this.btnConfirmAndContinue); 
			
			if(lib.Web.isWebElementDisplayed(this.lblContributionDetails, true))
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
			irs_limit=Float.parseFloat(irs);
			
			if(this.txtIRSMyContribution.getText().contains("%"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions percentage ", "My Contributions percentage is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions percentage", "My Contributions percentage is not displayed", true);
			if(this.txtIRSContributionAmount.getText().contains("$"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions amount ", "My Contributions amount is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions amount", "My Contributions amount is not displayed", true);
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
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
				
				
				if(Stock.GetParameterValue("Contributing_type").equalsIgnoreCase("Maximize to irs limit")){
//					int befor_tax=irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth")));
					befor_tax=irs_limit-(Float.parseFloat(Stock.GetParameterValue("Split_Tax_roth")));
					lib.Web.setTextToTextBox(txtSplitBeforeTax, Float.toString(befor_tax));
					lib.Web.setTextToTextBox(txtSplitRothTax, Stock.GetParameterValue("Split_Tax_roth"));
				}
				else{
					lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.GetParameterValue("Split_Tax_before"));
					lib.Web.setTextToTextBox(txtSplitRothTax, Stock.GetParameterValue("Split_Tax_roth"));
				}
									
				}
				else
					Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
			}
			else{
				for(int i=0;i<lstradioSelectContibution.size();i++){
					if(lstradioSelectContibution.get(i).getText().contains(contributionType)){
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
			lib.Web.clickOnElement(radioMaximizeToCompanyMatch);
			if(this.txtCompanyMatchMyContribution.getText().trim().equalsIgnoreCase("6%"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions percentage ", "My Contributions percentage is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions percentage", "My Contributions percentage is not displayed", true);
			if(this.txtCompanyMatchContributionAmount.getText().contains("$"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions amount ", "My Contributions amount is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions amount", "My Contributions amount is not displayed", true);
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
			if(lib.Web.isWebElementDisplayed(deferral));
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
				if(!this.chkboxMaximize.isSelected())
					this.chkboxMaximize.click();
				
				select_ContributionType("Split");
				lib.Web.clickOnElement(btnContinue);
				
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysBefore.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Standard before contribution", "Maximize Me Always is displayed for Standard before contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Standard before contribution", "Maximize Me Always is displayed for Standard before contribution", true);
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysRoth.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Standard Roth contribution", "Maximize Me Always is displayed for Standard Roth contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Standard Roth contribution", "Maximize Me Always is displayed for Standard Roth contribution", true);
				
				if(lib.Web.VerifyText(Float.toString(irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth"))))+"%", txtBeforeTaxContributionAmt.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify before tax contribution percent", "Before tax contribution percent is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify before tax contribution percent", "Before tax contribution percent is not matching", true);
				
				if(lib.Web.VerifyText(Stock.GetParameterValue("Split_Tax_roth")+"%", txtRothContributionAmt.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify roth contribution percent", "Roth contribution percent is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify roth contribution percent", "Roth contribution percent is not matching", true);
			}
			else{
				if(this.chkboxMaximize.isSelected())
					this.chkboxMaximize.click();
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
		 */
		public void Catchup_with_split_contributions(String myContRate)
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
			if(lib.Web.isWebElementDisplayed(txtViewOnly))
				Reporter.logEvent(Status.PASS, "Check if view only is displayed for age roth rate", "View only is displayed for roth catch up rate", false);
			else
				Reporter.logEvent(Status.FAIL, "Check if view only is displayed for age roth rate", "View only is not displayed for roth catch up rate", true);
			
//			rothCatchupRate = rothCatchUprate.getText();
//			rothCatchupRateNum = Integer.parseInt(rothCatchupRate);
			//lib.Web.setTextToTextBox(txtCatchupRate, String.valueOf((Integer.valueOf(myContRate)) - rothCatchupRateNum));
			lib.Web.setTextToTextBox(txtCatchupRate,String.valueOf(((Integer.valueOf(myContRate))-Integer.parseInt(Stock.GetParameterValue("Age_roth_Catchup_contr")))));
			lib.Web.clickOnElement(btnContinue);
			lib.Web.clickOnElement(btnConfirmAndContinue);
			myContributions_Confirmation_Page();
		}
		
		/**<pre> Method to edit Standard contribution having a split  and verify if view only is displayed 
		 *.</pre>
		 * @param myContRate- contribution rate 
		 */
		public void View_only_Standard_with_changes(String myContRate)
		{
			String rothCatchupRate="";
			int rothCatchupRateNum;
			try {
				lib.Web.waitForElement(btnEditStandard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lib.Web.clickOnElement(btnEditStandard);
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
			myContributions_Confirmation_Page();
		}
		

		/**<pre> Method to edit Standard contribution and select Maximize me always option and verify Maximize me always is displayed for Catch-up 
		 *.</pre>
		 * 
		 */
		public void Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always(){
			
			
			lib.Web.waitForElement(btnEditStandard);
			lib.Web.clickOnElement(btnEditStandard);
			click_Maximize_IRS_Limit();
			regular_maximize_me_always("Yes");
			if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysCatchupBefore.getText(), true))
				Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Catch-up before contribution", "Maximize Me Always is displayed for Catch-up before contribution", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Catch-up before contribution", "Maximize Me Always is not displayed for Catch-up before contribution", true);
			
			String catchup_before_percent=txtCatchupBeforeAmt.getText().trim();
			float total_contr=Float.parseFloat(catchup_before_percent.split("%")[0])+befor_tax+Float.parseFloat(Stock.GetParameterValue("Split_Tax_roth"));
			Web.clickOnElement(btnConfirmAndContinue);
			
			if(verifyContributionDetails(Float.toString(befor_tax),"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Float.toString(befor_tax), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Float.toString(befor_tax), true);
			
			if(verifyContributionDetails(Stock.GetParameterValue("Split_Tax_roth"),"New Roth",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+Stock.GetParameterValue("Split_Tax_roth"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Roth Contribution rate", "Standard Roth Contribution rate is "+Stock.GetParameterValue("Split_Tax_roth"), true);
			
			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), true);
			
			
		}
		
		

		/**<pre> Method to edit Maximized Standard deferral to a custom rate and verify add auto increase link is displayed for both standard and catch up deferrals 
		 *.</pre>
		 * 
		 */
		public void Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer(){
			
			
			lib.Web.waitForElement(btnEditStandard);
			lib.Web.clickOnElement(btnEditStandard);
			click_Select_Your_Contribution_Rate();
			select_ContributionType(Stock.GetParameterValue("Contribution_type"));
			
			if(Web.isWebElementDisplayed(lnkBeforeTaxAutoIncrease, true))
				Reporter.logEvent(Status.PASS, "Verify Add auto increase link displayed for Standard Contribution", "Verify Add auto increase link displayed for Standard Contribution", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Add auto increase link displayed for Standard Contribution", "Verify Add auto increase link not displayed for Standard Contribution", true);
			
			if(Web.isWebElementDisplayed(lnkCatchupAutoIncrease, true))
				Reporter.logEvent(Status.PASS, "Verify Add auto increase link displayed for CatchUp Contribution", "Verify Add auto increase link displayed for CatchUp Contribution", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Add auto increase link displayed for CatchUp Contribution", "Verify Add auto increase link not displayed for CatchUp Contribution", true);
			
			String catchup_before_percent=txtCatchupBeforeAmt.getText().trim();
			lib.Web.clickOnElement(btnEditCatchUp);
			
			Web.waitForElement(txtMyCatchupContr);
			if(Web.VerifyText(catchup_before_percent, txtMyCatchupContr.getText().trim(), true))
				Reporter.logEvent(Status.PASS, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is same as maximized contr rate", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Select Another Contribution rate same as maximized contr rate", "Select Another Contribution rate is not same as maximized contr rate", true);
			
			Web.clickOnElement(btnBack);
			Web.waitForElement(btnConfirmAndContinue);
			System.out.println(Float.parseFloat(catchup_before_percent.split("%")[0]));
			System.out.println(Stock.GetParameterValue("Contribution Rate"));
			float total_contr=Float.parseFloat(catchup_before_percent.split("%")[0])+Float.parseFloat(Stock.GetParameterValue("Contribution Rate"));
			System.out.println(total_contr);
			Web.clickOnElement(btnConfirmAndContinue);
			
					
			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+Float.toString(total_contr), true);
			
			if(verifyContributionDetails(Stock.GetParameterValue("Contribution Rate"),"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Stock.GetParameterValue("Contribution Rate"), false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+Stock.GetParameterValue("Contribution Rate"), true);
			
			if(verifyContributionDetails(catchup_before_percent,"Age Catch-Up Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_before_percent, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Age Catch-Up Before Contribution rate", "Age Catch-Up Before Contribution rate is "+catchup_before_percent, true);
			
		}
		
		/**<pre> Method to change the salary on the maximize to IRS panel of the Standard deferral and verify catchup contribution is changed
		 *.</pre>
		 * 
		 */
		public void Catch_up_Salary_changes_on_IRS_panel(){
			
			lib.Web.waitForElement(btnEditStandard);
			String catchup_contr1=txtCatchupBeforeAmt.getText().trim();
			lib.Web.clickOnElement(btnEditStandard);
			Web.waitForElement(txtAnnualCompensation);
			//String irs_contr_rate=	txtIRSMyContribution.getText().split("%")[0];
			Web.setTextToTextBox(txtAnnualCompensation, Stock.GetParameterValue("Annual_Compensation"));
			Reporter.logEvent(Status.INFO, "Entered Annual Compansation amount", "Annual compensation amount entered is: "+Stock.GetParameterValue("Annual_Compensation"), false);
			Web.clickOnElement(btnUpdate);
			if(Web.isWebElementDisplayed(lblAlertMsg)){
				if(Web.VerifyText(Stock.GetParameterValue("Alert_Message"), lblAlertMsg.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Alert message is matching", "Alert message is matching", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify Alert message is matching", "Alert message not matching", true);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify if Alert is displayed", "No Alert displayed after updating Annual compensation amount", true);
			String irs_contr_rate=	txtIRSMyContribution.getText().split("%")[0];
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
			
			float total_contr=Float.parseFloat(catchup_contr2.split("%")[0])+Float.parseFloat(irs_contr_rate);
			Web.clickOnElement(btnConfirmAndContinue);
			
			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			
			if(verifyContributionDetails(irs_contr_rate,"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+irs_contr_rate, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+irs_contr_rate, true);
			
			if(verifyContributionDetails(catchup_contr2.split("%")[0],"Age Catch-Up Before Tax",null,null))
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
			Web.clickOnElement(btnContinue);
			if(Web.isWebElementDisplayed(txtMaximizeMeAlwaysCatchupBefore))
				Reporter.logEvent(Status.PASS, "Verify Maximize Me Always displayed for catchup", "Maximize Me Always displayed for catchup", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always displayed for catchup", "Maximize Me Always not displayed for catchup", true);
			
			String standard_beforetax_contr=txtBeforeTaxContributionAmt.getText().split("%")[0];
			Float total_contr=Float.parseFloat(txtBeforeTaxContributionAmt.getText().split("%")[0])+irs_limit;
			Web.clickOnElement(btnConfirmAndContinue);
			
			if(verifyContributionDetails(Float.toString(total_contr),"Total",null,null))
				Reporter.logEvent(Status.PASS, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Total Contribution rate", "Total Contribution rate is "+total_contr, true);
			
			if(verifyContributionDetails(standard_beforetax_contr,"Before Tax",null,null))
				Reporter.logEvent(Status.PASS, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+txtBeforeTaxContributionAmt.getText().split("%")[0], false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Standard Before Contribution rate", "Standard Before Contribution rate is "+txtBeforeTaxContributionAmt.getText().split("%")[0], true);
			
			if(verifyContributionDetails(Float.toString(irs_limit),"Age Catch-Up Before Tax",null,null))
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
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
		
			if(Web.isWebElementDisplayed(txtSplitBeforeTax))
				lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.GetParameterValue("Split_Tax_before"));
			if(Web.isWebElementDisplayed(txtSplitAfterTaxBonus))
				lib.Web.setTextToTextBox(txtSplitAfterTaxBonus, Stock.GetParameterValue("Split_Tax_after"));
			
			lib.Web.setTextToTextBox(txtSplitRothBonus, Stock.GetParameterValue("Split_Tax_roth"));
		}
	}		
	


