package pageobjects.deferrals;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.general.LeftNavigationBar;
import lib.*;
import lib.Reporter.Status;

public class Deferrals extends LoadableComponent<Deferrals> {
	
	//Declarations
			private LoadableComponent<?> parent;
			private static boolean waitforLoad = false;	
			private int irs_limit;
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
			@FindBy(id="ROTH") private WebElement txtSplitRothTax;
			@FindBy(xpath=".//button[text()[normalize-space()='Continue']]") private WebElement btnContinueBeforeTaxSplitContribution;
			@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBackBeforeTaxSplitContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-percentage ng-binding']") private WebElement txtCompanyMatchMyContribution;
			@FindBy(xpath="//div[@id='companyMatch']//div[@class='contribution-amount']") private WebElement txtCompanyMatchContributionAmount;
			@FindBy(xpath="//div[@id='irsMax']//div[@class='contribution-percentage ng-binding']") private WebElement txtIRSMyContribution;
			@FindBy(xpath="//div[@id='irsMax']//div[@class='contribution-amount']") private WebElement txtIRSContributionAmount;
							
			//Add Auto Increase		
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'AFTRTX')]/../td[3]/.//a") private WebElement lnkAfterTaxAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[3]/.//a") private WebElement lnkBeforeTaxAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'catch')]/../td[3]/.//a") private WebElement lnkCatchupAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Bonus')]/../td[3]/.//a") private WebElement lnkBonusAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'other')]/../td[3]/.//a") private WebElement lnkOtherAutoIncrease;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'ROTH')]/../td[3]/.//a") private WebElement lnkRothAutoIncrease;
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
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'ROTH')]/../td[3]") private WebElement txtMaximizeMeAlwaysRoth;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[1]") private WebElement txtBeforeTaxContributionAmt;
			@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'ROTH')]/../td[1]") private WebElement txtRothContributionAmt;
			
			@FindBy(xpath="//table[@class='deferrals-breakdown ng-scope']/tbody") private WebElement tblContributionDetails;
			@FindBy(xpath="//table[@class='responsive-tables contributions-cart']/tbody") private WebElement tblMyContributions;
			
			//view only
			@FindBy(xpath = ".//div[@class='clearfix']/.//h2") private List<WebElement> headersContrPage;
			@FindBy(xpath = "(//td[@class='ng-scope']/button)[3]") private WebElement btnEditAftertaxNoSplit;
			@FindBy(xpath = "//span[contains(text(),'More Options')]") private WebElement linkMoreOptions;
			@FindBy(id = "AGERTH") private WebElement rothCatchUprate;
			@FindBy(id = "AGEBEF") private WebElement txtCatchupRate;
			@FindBy(xpath = ".//*[@id='splitContribution']/.//span[2]") private WebElement txtViewOnly;
			@FindBy(xpath = "//table[@class='table-details']//th[contains(text(),'Confirmation Number')]/../td") private WebElement txtConfirmationNo;
			
			@FindBy(id = "BEFORE") private WebElement bfrtaxRate;
			@FindBy(id = "ROTH") private WebElement txtRothtaxRate;
			
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
			if (!lib.Web.isWebElementDisplayed(lblMyContributions,Deferrals.waitforLoad)) {
				Deferrals.waitforLoad = true;
				throw new Error("'My contributions' page is not loaded");
			}else{
				Deferrals.waitforLoad = false;
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
			if(fieldName.trim().equalsIgnoreCase("Bonus Add Auto Increase")) {
				return this.lnkBonusAutoIncrease;
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
            try {
				lib.Web.waitForElement(deferral);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			try {
				lib.Web.waitForElement(radioSelectAnotherContributionRate);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			Reporter.logEvent(Status.PASS, "Select Another Contribution rate", "Select another Contribution radio button is clicked", false);
			try {
				lib.Web.waitForElement(lnkPercent);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.lnkPercent.click();			
			this.lnkContributionRate.click();
			try {
				lib.Web.waitForElement(txtcontributionRateSlider);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			try {
				lib.Web.waitForElement(btnContinue);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lib.Web.clickOnElement(this.btnContinue);
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
			try {
				lib.Web.waitForElement(btnConfirmAndContinue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lib.Web.clickOnElement(this.btnConfirmAndContinue); 
			
			if(lib.Web.isWebElementDisplayed(this.lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				this.btnMyContributions.click();

		}
		
		/**<pre> Method to click on maximize _to_irs radio button and verify My contributions percentage and amount.
		 *.</pre>
		 * 
		 * @return - boolean
		 */
		public void click_Maximize_IRS_Limit(String deferralType)
		{
			if(deferralType.equalsIgnoreCase("Catch Up"))
				lib.Web.clickOnElement(radioMaximizeToIRSLimit);
			else if(deferralType.equalsIgnoreCase("Standard"))
			{
				lib.Web.clickOnElement(radioMaximizeToIRSLimit);
			
			String irs=	txtIRSMyContribution.getText().split("%")[0];
			irs_limit=Integer.parseInt(irs);
			
			if(this.txtIRSMyContribution.getText().contains("%"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions percentage ", "My Contributions percentage is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions percentage", "My Contributions percentage is not displayed", true);
			if(this.txtIRSContributionAmount.getText().contains("$"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions amount ", "My Contributions amount is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions amount", "My Contributions amount is not displayed", true);
			}
			
		}
		
		/**<pre> Method to select contribution type.
		 *.</pre>
		 * 
		 */
		
		public void select_ContributionType(String contributionType)
		{		
			try {
				lib.Web.waitForElement(btnContinue);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lib.Web.clickOnElement(this.btnContinue);
			try {
				lib.Web.waitForElement(radioSplitContribution);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(contributionType.equalsIgnoreCase("Split") ){
				if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)  )  
				{				
				this.radioSplitContribution.click();
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
				
				
				if(Stock.GetParameterValue("Standard_Contribution").equalsIgnoreCase("Maximize to irs limit")){
					int befor_tax=irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth")));
					lib.Web.setTextToTextBox(txtSplitBeforeTax, Integer.toString(befor_tax));
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
					if(lstradioSelectContibution.get(i).getText().contains(contributionType))
						lstradioSelectContibution.get(i).click();
					
					break;
				}
			}
//			if(contributionType.equalsIgnoreCase("Before") || contributionType.equalsIgnoreCase("After"))
//			{
//				this.lstradioSelectContibution.get(0).click();
//				if(lib.Web.clickOnElement(this.lstradioSelectContibution.get(0)))
//					Reporter.logEvent(Status.PASS, "Verify Before/After Tax Contribution Rate radio button is clicked ", "Before/After Tax radio option is clicked", false);
//				else
//					Reporter.logEvent(Status.FAIL, "Verify Before/After Tax Contribution Rate radio button is clicked", "Before/After Tax radio option is not clicked", true);					
//			}			
//			if(contributionType.equalsIgnoreCase("Roth"))
//			{
//					this.lstradioSelectContibution.get(1).click();
//					if(lib.Web.clickOnElement(this.lstradioSelectContibution.get(1)))
//						Reporter.logEvent(Status.PASS, "Verify Roth Tax Contribution Rate radio button is clicked ", "Roth Tax radio option is clicked", false);
//					else
//						Reporter.logEvent(Status.FAIL, "Verify Roth Tax Contribution Rate radio button is clicked", "Roth Tax radio option is not clicked", true);
//			}
			
			
				
		}
		
		/**<pre> Method to verify catch up contribution after adding.
		 *.</pre>
		 * 
		 */
		public void catchup_maximize_to_irs() {
			lib.Web.clickOnElement(this.btnContinue);
			if(this.txtCatchup.getText().contains("Effective "))
				Reporter.logEvent(Status.PASS, "Verify Roth Tax Contribution Rate radio button is clicked ", "Roth Tax radio option is clicked", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Roth Tax Contribution Rate radio button is clicked", "Roth Tax radio option is not clicked", true);
			
		}
		
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
		
		
		/**<pre> Method to check if bonus deferral is available for the participant
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
				lib.Web.clickOnElement(btnContinue);
				select_ContributionType("Split");
				lib.Web.clickOnElement(btnContinue);
				
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysBefore.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for before contribution", "Maximize Me Always is displayed for before contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for before contribution", "Maximize Me Always is displayed for before contribution", true);
				if(lib.Web.VerifyText("Maximize Me Always", txtMaximizeMeAlwaysRoth.getText(), true))
					Reporter.logEvent(Status.PASS, "Verify Maximize Me Always for Roth contribution", "Maximize Me Always is displayed for Roth contribution", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Maximize Me Always for Roth contribution", "Maximize Me Always is displayed for Roth contribution", true);
				
				if(lib.Web.VerifyText(Integer.toString(irs_limit-(Integer.parseInt(Stock.GetParameterValue("Split_Tax_roth"))))+"%", txtBeforeTaxContributionAmt.getText(), true))
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
			try {
				lib.Web.waitForElement(lblMyContributions);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			String ConfirmationNo = this.txtConfirmationNo.getText();
			if(ConfirmationNo!=null)
				Reporter.logEvent(Status.INFO, "Confirmation Number", "Confirmation Number : "+ConfirmationNo, true);
			else
				Reporter.logEvent(Status.FAIL, "Confirmation Number", "Confirmation Number not generated", true);
			
			if(tblContributionDetails.getText().contains(percent+"% "+type+"with auto-increase "+autoIncreaseRate+"% "+"up to "+autoIncreaseUpto+"%"))
				return true;
			
			return false;
		}
		
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
		
		public void select_contribution_type(String contribution_type){
			for(int i=0;i<lstradioSelectContibution.size();i++){
				if(lstradioSelectContibution.get(i).getText().equalsIgnoreCase(contribution_type))
					lstradioSelectContibution.get(i).click();
				
				break;
			}
		}
	}		
	


