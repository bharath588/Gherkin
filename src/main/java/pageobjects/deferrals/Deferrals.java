package pageobjects.deferrals;

import java.util.List;

import org.openqa.selenium.WebElement;
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
					@FindBy(id="contributionRateSlider-text-edit")
					private WebElement txtcontributionRateSlider;
					@FindBy(xpath=".//span[@class='valueAndEnd']") private WebElement lnksliderValue;
					@FindBy(xpath=".//button[text()[normalize-space()='Done']]") private WebElement btnDone;
//					@FindBy(xpath=".//label[text()[normalize-space()='Percent']]") private WebElement lnkPercent;
					@FindBy(xpath="//span[@class='editable-text-trigger ng-binding']") private WebElement lnkPercent;
					@FindBy(xpath=".//label[text()[normalize-space()='Dollar']]") private WebElement lnkDollar;
					@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBack;
//					@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Continue']]") private WebElement btnContinue;
					@FindBy(xpath="//button[text()[normalize-space()='Continue']]") private WebElement btnContinue;
					@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Confirm & Continue']]") private WebElement btnConfirmAndContinue;
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
					@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'After')]/../td[3]/.//a") private WebElement lnkAfterTaxAutoIncrease;
					@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[3]/.//a") private WebElement lnkBeforeTaxAutoIncrease;
					@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'catch')]/../td[3]/.//a") private WebElement lnkCatchupAutoIncrease;
					@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Bonus')]/../td[3]/.//a") private WebElement lnkBonusAutoIncrease;
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
					@FindBy(xpath=".//button[text()[normalize-space()='Save']]") private WebElement btnSaveAddAutoIncreaseModal;
					@FindBy(xpath=".//button[text()[normalize-space()='Cancel']]") private WebElement btnCancelAddAutoIncreaseModal;
					@FindBy(xpath=".//h2[text()[normalize-space()='Contribution Details']]") private WebElement lblContributionDetails;
					@FindBy(id="deleteAutoIncr") private WebElement chkDeleteAutoIncrease;
					@FindBy(xpath=".//button[text()[normalize-space()='Delete']]") private WebElement btnDeleteAddAutoIncreaseModal;
			
			
			
		
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
			return null;
			}
		
		/**<pre> Method to add or Edit after Tax Contribution
		 *.</pre>
		 * @param fieldName - fieldname
		 * 
		 * @return - void
		 */
		public void addOrEditAfterTaxContribution()
		{
			boolean sliderValue;
			this.lnkMoreOptions.click();
			if(lib.Web.isWebElementDisplayed(btnAddorEditAfterTax, false)) {
					this.btnAddorEditAfterTax.click();										
			} else if(lib.Web.isWebElementDisplayed(btnEditAfterTax, false)) {
					this.btnEditAfterTax.click();							
			} else 		
				throw new Error("Add or Edit button for After Tax is not displayed");				   
			try {
				Thread.sleep(1000); }
				catch (InterruptedException e) {	 }		
						
			if(lib.Web.isWebElementDisplayed(radioSelectAnotherContributionRate, true))
				Reporter.logEvent(Status.PASS, "Click on Add or Edit Button", "Add After Tax Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add or Edit Button", "Add After Tax Contribution Page is not displayed", false);
			
			//Select Another Contribution Rate option and enter contribution rate for Percent tab
			this.radioSelectAnotherContributionRate.click();
			this.lnkPercent.click();
			this.lnkContributionRate.click();			
			this.txtcontributionRateSlider.clear();
			this.txtcontributionRateSlider.sendKeys(Stock.GetParameterValue("Contribution Rate"));
			this.btnDone.click();
			//System.out.println(lnksliderValue.getText()); 
			
			//Verify Accuracy of My Contribution
			sliderValue=lib.Web.VerifyText(Stock.GetParameterValue("Contribution Rate"), lnksliderValue.getText());
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution After Tax", "My Contribution value is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution After Tax", "My Contribution value is not matching", true);
			
			this.btnContinue.click();		
			try {
			Thread.sleep(1000);
			} catch (Exception e) { }			
			if(lib.Web.isWebElementDisplayed(this.lnkAfterTaxAutoIncrease, false))
			{
			Reporter.logEvent(Status.PASS, "Check Add Auto Increase link for After Tax", "Add Auto Increase link is displayed", false);
			this.lnkAfterTaxAutoIncrease.click();							
			if(lib.Web.isWebElementDisplayed(btnSaveAddAutoIncreaseModal, true))
				Reporter.logEvent(Status.PASS, "Click on Add Auto Increase for After Tax", "Add Auto Increase pop up for Afterax is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add Auto Increase for After Tax", "Add Auto Increase pop up for AfterTax is not displayed", true);
			this.txtAutoIncreaseMyContributionPercent.clear();
			this.txtAutoIncreaseMyContributionPercent.sendKeys(Stock.GetParameterValue("Auto Increase Contribution Percent"));
			this.txtAutoIncreaseUntilItReachesPercent.clear();
			this.txtAutoIncreaseUntilItReachesPercent.sendKeys(Stock.GetParameterValue("Auto Increases Until Reaches Percent"));
			lib.Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, Stock.GetParameterValue("Auto Increase Period"));											
			this.btnSaveAddAutoIncreaseModal.click();								
			if(lib.Web.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Save button", "My Contributions Page is displayed with Auto Increase Details", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Save button", "My Contributions Page is not displayed with Auto Increase Details", false);
			}
			else 		
				Reporter.logEvent(Status.FAIL, "Check Add Auto Increase link for After Tax", "Add Auto Increase link is not displayed", false);
				
			this.btnConfirmAndContinue.click();
			if(lib.Web.isWebElementDisplayed(lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);								
			this.btnMyContributions.click();							
			if(lib.Web.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on My Contributions button", "My Contributions Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on My Contributions button", "My Contributions Page is not displayed", true);
		
		}

				
		/**<pre> Method to add or Edit Before Tax with split Contribution
		 *.</pre>
		 * @param fieldName - fieldname
		 * 
		 * @return - void
		 */
		public void addOrEditBeforeTaxSplitContribution() 
		{
			this.lnkMoreOptions.click();			
			if(lib.Web.isWebElementDisplayed(btnAddOrEditStandard, true)) {
				this.btnAddOrEditStandard.click();											
		} else if(lib.Web.isWebElementDisplayed(btnEditStandard, true)) {
				this.btnEditStandard.click();					
		} else {			
				throw new Error("Add or Edit button for Before tax is not displayed");	
			   } 						
			//verify if Add/Edit Standard Page is displayed
			if(lib.Web.isWebElementDisplayed(radioSelectAnotherContributionRate, true))
				Reporter.logEvent(Status.PASS, "Click on Add or Edit Button", "Add a Standard Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add or Edit Button", "Add a Standard Contribution Page is not displayed", false);
			
			//Select Another Contribution Rate option and enter contribution rate for Percent tab
			this.radioSelectAnotherContributionRate.click();
			this.lnkPercent.click();
			this.lnkPercent.click();
			this.lnkContributionRate.click();			
			this.txtcontributionRateSlider.clear();
			this.txtcontributionRateSlider.sendKeys((Stock.GetParameterValue("Contribution Rate")));
			this.btnDone.click();
			System.out.println(lnksliderValue.getText()); 						
			
			//Verify Accuracy of My Contribution
			boolean sliderValue=lib.Web.VerifyText((Stock.GetParameterValue("Contribution Rate")), lnksliderValue.getText());
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is not matching", true);
	
			this.btnContinue.click();					
			if(lib.Web.isWebElementDisplayed(lnkPlanRules, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "Before Tax Select Contribution Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "Before Tax Select Contribution Page is not displayed", true);
			
			this.radioSplitContribution.click();
			this.txtSplitBeforeTax.clear();
			this.txtSplitBeforeTax.sendKeys((Stock.GetParameterValue("Split Before Tax")));
			this.txtSplitRothTax.clear();
			this.txtSplitRothTax.sendKeys((Stock.GetParameterValue("Split Roth Tax")));					
			this.btnContinueBeforeTaxSplitContribution.click();
			
			if(lib.Web.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "My Contributions Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "My Contributions Page is not displayed", false);	
		if(lib.Web.isWebElementDisplayed(this.lnkBeforeTaxAutoIncrease, false))
			{				
				Reporter.logEvent(Status.PASS, "Check Add Auto Increase link for Before Tax Split", "Add Auto Increase link is displayed", false);
			this.lnkBeforeTaxAutoIncrease.click();
			if(lib.Web.isWebElementDisplayed(btnSaveAddAutoIncreaseModal, true))
				Reporter.logEvent(Status.PASS, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is not displayed", true);
			this.txtAutoIncreaseMyContributionPercent.clear();
			this.txtAutoIncreaseMyContributionPercent.sendKeys((Stock.GetParameterValue("Auto Increase Contribution Percent")));
			this.txtAutoIncreaseUntilItReachesPercent.clear();
			this.txtAutoIncreaseUntilItReachesPercent.sendKeys((Stock.GetParameterValue("Auto Increases Until Reaches Percent")));
			lib.Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.GetParameterValue("Auto Increase Period")));
			try {
				Thread.sleep(1000);
				} catch (Exception e) {
				}		
			this.btnSaveAddAutoIncreaseModal.click();				
			if(lib.Web.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Save button", "My Contributions Page is displayed with Auto Increase Details", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Save button", "My Contributions Page is not displayed with Auto Increase Details", false);
			}
			else
				Reporter.logEvent(Status.FAIL, "Check Add Auto Increase link for Before Tax Split", "Add Auto Increase link is not displayed", false);
				
			this.btnConfirmAndContinue.click();
			
			if(lib.Web.isWebElementDisplayed(lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				
			this.btnMyContributions.click();
			if(lib.Web.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on My Contributions button", "My Contributions Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on My Contributions button", "My Contributions Page is not displayed", true);
			
		}
			
		
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
			lib.Web.clickOnElement(this.radioSelectAnotherContributionRate);
			this.lnkPercent.click();			
			this.lnkContributionRate.click();
			lib.Web.setTextToTextBox(txtcontributionRateSlider, Stock.globalTestdata.get("Contribution Rate"));			
			this.btnDone.click();
			boolean sliderValue=lib.Web.VerifyText(Stock.globalTestdata.get("Contribution Rate"), lnksliderValue.getText());			
			return sliderValue;
		}
		
		/**<pre> Method to add auto increase.
		 *.</pre>
		 * 
		 */
		public void add_Auto_Increase(String deferralType)
		{
			WebElement autoIncreaseDeferralType=this.getWebElement(deferralType);
			lib.Web.clickOnElement(this.btnContinue);
			if(lib.Web.isWebElementDisplayed(this.tblhdrlblContribution, true))
			{
				if(lib.Web.isWebElementDisplayed(autoIncreaseDeferralType, false))
				{
				Reporter.logEvent(Status.PASS, "Verify if Add Auto Increase link is displayed", "Add Auto Increase link is displayed", false);
//				this.lnkBeforeTaxAutoIncrease.click();
				autoIncreaseDeferralType.click();
				lib.Web.setTextToTextBox(txtAutoIncreaseMyContributionPercent, Stock.globalTestdata.get("Auto Increase Contribution Percent"));			
				lib.Web.setTextToTextBox(txtAutoIncreaseUntilItReachesPercent, Stock.globalTestdata.get("Auto Increases Until Reaches Percent"));
				lib.Web.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.globalTestdata.get("Auto Increase Period")));
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
			lib.Web.clickOnElement(this.btnConfirmAndContinue);
			if(lib.Web.isWebElementDisplayed(this.lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				this.btnMyContributions.click();
			if(lib.Web.isWebElementDisplayed(this.tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page", "My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page", "My Contributions page is not displayed", true);
				
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
		
		public void select_ContributionType_Standard(String contributionType)
		{				
			lib.Web.clickOnElement(this.btnContinue);
			if(contributionType.equalsIgnoreCase("Split") ){
				if(lib.Web.isWebElementDisplayed(this.radioSplitContribution, true)  )  
				{				
				this.radioSplitContribution.click();
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is clicked", false);
				lib.Web.setTextToTextBox(txtSplitBeforeTax, Stock.globalTestdata.get("Split Before Tax"));
				lib.Web.setTextToTextBox(txtSplitRothTax, Stock.globalTestdata.get("Split_Tax"));					
				}
				else
				Reporter.logEvent(Status.PASS, "Verify Split Contribution Rate radio button is clicked", "Split Contibution is not clicked", false);
			}
			if(contributionType.equalsIgnoreCase("Before") || contributionType.equalsIgnoreCase("After"))
			{
				this.lstradioSelectContibution.get(0).click();
				if(lib.Web.clickOnElement(this.lstradioSelectContibution.get(0)))
					Reporter.logEvent(Status.PASS, "Verify Before/After Tax Contribution Rate radio button is clicked ", "Before/After Tax radio option is clicked", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Before/After Tax Contribution Rate radio button is clicked", "Before/After Tax radio option is not clicked", true);					
			}			
			if(contributionType.equalsIgnoreCase("Roth"))
			{
					this.lstradioSelectContibution.get(1).click();
					if(lib.Web.clickOnElement(this.lstradioSelectContibution.get(1)))
						Reporter.logEvent(Status.PASS, "Verify Roth Tax Contribution Rate radio button is clicked ", "Roth Tax radio option is clicked", false);
					else
						Reporter.logEvent(Status.FAIL, "Verify Roth Tax Contribution Rate radio button is clicked", "Roth Tax radio option is not clicked", true);
			}
				
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
	}
		
		


