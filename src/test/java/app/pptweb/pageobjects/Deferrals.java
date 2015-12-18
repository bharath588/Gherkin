package app.pptweb.pageobjects;

import java.util.List;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;


import app.pptweb.common.Reporter;
import app.pptweb.common.Reporter.Status;
import app.pptweb.common.common;
import core.utils.Stock;

public class Deferrals extends LoadableComponent<Deferrals> {
	
		//Declarations
		private LoadableComponent<?> parent;
		private static boolean waitforLoad = false;	
		@FindBy(xpath=".//div[@class='page-title ng-scope']/h1") private WebElement lblMyContributions;
		@FindBy(xpath=".//table/thead/tr/th[1][text()[normalize-space()='Contribution']]")
		private WebElement tblhdrlblContribution;
		@FindBy(id="buttonAdd_STANDARD") private WebElement btnAddOrEditStandard;
		@FindBy(id="buttonEdit_STANDARD") private WebElement btnEditStandard;
		@FindBy(id="buttonAdd_AFTERTAX") private WebElement btnAddorEditAfterTax;
		@FindBy(id="buttonEdit_AFTERTAX") private WebElement btnEditAfterTax;
		@FindBy(id="buttonAdd_CATCHUP") private WebElement btnAddOrEditCatchUp;
		@FindBy(id="buttonEdit_CATCHUP") private WebElement btnEditCatchUp;
		@FindBy(xpath=".//span[text()[normalize-space()='More Options']]") private WebElement lnkMoreOptions;
		@FindBy(xpath=".//span[text()[normalize-space()='Fewer Options']]") private WebElement lnkFewerOptions;		
		@FindBy(id="buttonAdd_OTHER") private WebElement btnAddOther;
		@FindBy(xpath=".//h1[text()[normalize-space()='Add a After-tax contribution']]") 
		private WebElement lblAddAfterTaxContribution;
		@FindBy(xpath=".//strong[text()[normalize-space()='Select another contribution rate']]") 
		private WebElement radioSelectAnotherContributionRate;		
		@FindBy(xpath="//*[contains(@class, 'editable-text-trigger')]")
		private WebElement lnkContributionRate;
		@FindBy(id="contributionRateSlider-text-edit")
		private WebElement txtcontributionRateSlider;
		@FindBy(xpath=".//span[@class='valueAndEnd']") private WebElement lnksliderValue;
		@FindBy(xpath=".//button[text()[normalize-space()='Done']]") private WebElement btnDone;
		@FindBy(xpath=".//label[text()[normalize-space()='Percent']]") private WebElement lnkPercent;
		@FindBy(xpath=".//label[text()[normalize-space()='Dollar']]") private WebElement lnkDollar;
		@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBack;
		@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Continue']]") private WebElement btnContinue;
		@FindBy(xpath=".//*[@id='buttonContinue' and text()[normalize-space()='Confirm & Continue']]") private WebElement btnConfirmAndContinue;
		@FindBy(xpath=".//button[text()[normalize-space()='My Contributions']]") private WebElement btnMyContributions;
		@FindBy(linkText="Plan Rules") private WebElement lnkPlanRules;
		@FindBy(xpath=".//header/label[@class='radio-inline panel-title']/span/strong") private List<WebElement> lstradioBeforeTaxContibutionsSplit;
		@FindBy(xpath=".//strong[text()[normalize-space()='Split your contribution']]") private WebElement radioSplitContribution;
		@FindBy(id="BEFORE") private WebElement txtSplitBeforeTax;
		@FindBy(id="ROTH") private WebElement txtSplitRothTax;
		@FindBy(xpath=".//button[text()[normalize-space()='Continue']]") private WebElement btnContinueBeforeTaxSplitContribution;
		@FindBy(xpath=".//button[text()[normalize-space()='Back']]") private WebElement btnBackBeforeTaxSplitContribution;
		
		//Add Auto Increase		
		@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'After')]/../td[3]/.//a") private WebElement lnkAfterTaxAutoIncrease;
		@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'Before')]/../td[3]/.//a") private WebElement lnkBeforeTaxAutoIncrease;
		@FindBy(xpath=".//*[@id='account-details-container']/.//td[contains(text(),'catch')]/../td[3]/.//a") private WebElement lnkCatchupAutoIncrease;
		@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase Before-tax deferral']]")	
		private WebElement lblAutoIncreaseBeforeTaxDeferral;
		@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase After-tax deferral']]")	
		private WebElement lblAutoIncreaseAfterTaxDeferral;
		@FindBy(xpath=".//div[text()[normalize-space()='Auto Increase Age catch-up before-tax deferral']]")	
		private WebElement lblAutoIncreaseCatchIpDeferral;		
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
			PageFactory.initElements(common.webdriver, this);
		}
		
		/** Argument Constructor with parent as input
		 * 
		 * @param parent
		 */
		public Deferrals(LoadableComponent<?> parent) {
			this.parent = parent;			
			PageFactory.initElements(common.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			if (!common.isWebElementDisplayed(lblMyContributions,Deferrals.waitforLoad)) {
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
		
		@SuppressWarnings("unused")
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
			if(common.isWebElementDisplayed(btnAddorEditAfterTax, false)) {
					this.btnAddorEditAfterTax.click();										
			} else if(common.isWebElementDisplayed(btnEditAfterTax, false)) {
					this.btnEditAfterTax.click();							
			} else 		
				throw new Error("Add or Edit button for After Tax is not displayed");				   
			try {
				Thread.sleep(1000); }
				catch (InterruptedException e) {	 }		
						
			if(common.isWebElementDisplayed(radioSelectAnotherContributionRate, true))
				Reporter.logEvent(Status.PASS, "Click on Add or Edit Button", "Add After Tax Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add or Edit Button", "Add After Tax Contribution Page is not displayed", false);
			
			//Select Another Contribution Rate option and enter contribution rate for Percent tab
			this.radioSelectAnotherContributionRate.click();
			this.lnkPercent.click();
			this.lnkContributionRate.click();			
			this.txtcontributionRateSlider.clear();
			this.txtcontributionRateSlider.sendKeys(Stock.globalTestdata.get("Contribution Rate"));
			this.btnDone.click();
			//System.out.println(lnksliderValue.getText()); 
			
			//Verify Accuracy of My Contribution
			sliderValue=common.VerifyText(Stock.globalTestdata.get("Contribution Rate"), lnksliderValue.getText());
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution After Tax", "My Contribution value is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution After Tax", "My Contribution value is not matching", true);
			
			this.btnContinue.click();		
			try {
			Thread.sleep(1000);
			} catch (Exception e) { }			
			if(common.isWebElementDisplayed(this.lnkAfterTaxAutoIncrease, false))
			{
			Reporter.logEvent(Status.PASS, "Check Add Auto Increase link for After Tax", "Add Auto Increase link is displayed", false);
			this.lnkAfterTaxAutoIncrease.click();							
			if(common.isWebElementDisplayed(btnSaveAddAutoIncreaseModal, true))
				Reporter.logEvent(Status.PASS, "Click on Add Auto Increase for After Tax", "Add Auto Increase pop up for Afterax is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add Auto Increase for After Tax", "Add Auto Increase pop up for AfterTax is not displayed", true);
			this.txtAutoIncreaseMyContributionPercent.clear();
			this.txtAutoIncreaseMyContributionPercent.sendKeys(Stock.globalTestdata.get("Auto Increase Contribution Percent"));
			this.txtAutoIncreaseUntilItReachesPercent.clear();
			this.txtAutoIncreaseUntilItReachesPercent.sendKeys(Stock.globalTestdata.get("Auto Increases Until Reaches Percent"));
			common.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, Stock.globalTestdata.get("Auto Increase Period"));											
			this.btnSaveAddAutoIncreaseModal.click();								
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Save button", "My Contributions Page is displayed with Auto Increase Details", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Save button", "My Contributions Page is not displayed with Auto Increase Details", false);
			}
			else 		
				Reporter.logEvent(Status.FAIL, "Check Add Auto Increase link for After Tax", "Add Auto Increase link is not displayed", false);
				
			this.btnConfirmAndContinue.click();
			if(common.isWebElementDisplayed(lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);								
			this.btnMyContributions.click();							
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on My Contributions button", "My Contributions Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on My Contributions button", "My Contributions Page is not displayed", true);
		
		}

		/**<pre> Method to add or Edit Before Tax Contribution
		 *.</pre>
		 * @param fieldName - fieldname
		 * 
		 * @return - void
		 */
		public void addOrEditBeforeTaxContribution() 		
		{
		this.lnkMoreOptions.click();
			if(common.isWebElementDisplayed(btnAddOrEditStandard, true)) {
				this.btnAddOrEditStandard.click();											
			} else if(common.isWebElementDisplayed(btnEditStandard, true)) {
				this.btnEditStandard.click();					
			} else {			
				throw new Error("Add or Edit button for Before tax is not displayed");	
			   	} 
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { }					
			
			//verify if Add/Edit Standard Page is displayed
			if(common.isWebElementDisplayed(radioSelectAnotherContributionRate, true))
				Reporter.logEvent(Status.PASS, "Click on Add or Edit Button", "Add a Standard Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add or Edit Button", "Add a Standard Contribution Page is not displayed", false);
			
			//Select Another Contribution Rate option and enter contribution rate for Percent tab
			this.radioSelectAnotherContributionRate.click();
			this.lnkPercent.click();
			this.lnkPercent.click();
			this.lnkContributionRate.click();			
			this.txtcontributionRateSlider.clear();			
			this.txtcontributionRateSlider.sendKeys(Stock.globalTestdata.get("Contribution Rate"));
			this.btnDone.click();
			//System.out.println(lnksliderValue.getText()); 						
			
			//Verify Accuracy of My Contribution
			boolean sliderValue=common.VerifyText(Stock.globalTestdata.get("Contribution Rate"), lnksliderValue.getText());
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is not matching", true);	
			this.btnContinue.click();				
			if(common.isWebElementDisplayed(lnkPlanRules, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "Before Tax Select Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "Before Tax Select Contribution Page is not displayed", false);
			
			this.lstradioBeforeTaxContibutionsSplit.get(0).click();
			this.btnContinueBeforeTaxSplitContribution.click();			
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "My Contributions Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "My Contributions Page is not displayed", false);
			
			if(common.isWebElementDisplayed(this.lnkBeforeTaxAutoIncrease, false))
			{
				Reporter.logEvent(Status.PASS, "Check Add Auto Increase link for Before Tax", "Add Auto Increase link is displayed", false);	
			this.lnkBeforeTaxAutoIncrease.click();
			if(common.isWebElementDisplayed(btnSaveAddAutoIncreaseModal, true))
				Reporter.logEvent(Status.PASS, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is not displayed", false);
			this.txtAutoIncreaseMyContributionPercent.clear();
			this.txtAutoIncreaseMyContributionPercent.sendKeys((Stock.globalTestdata.get("Auto Increase Contribution Percent")));
			this.txtAutoIncreaseUntilItReachesPercent.clear();
			this.txtAutoIncreaseUntilItReachesPercent.sendKeys((Stock.globalTestdata.get("Auto Increases Until Reaches Percent")));
			common.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.globalTestdata.get("Auto Increase Period")));							
			this.btnSaveAddAutoIncreaseModal.click();				
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Save button", "My Contributions Page is displayed with Auto Increase Details", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Save button", "My Contributions Page is not displayed with Auto Increase Details", true);
			} else	{
				Reporter.logEvent(Status.FAIL, "Check Add Auto Increase link for After Tax", "Add Auto Increase link is not displayed", false);				
			}			
			this.btnConfirmAndContinue.click();
			if(common.isWebElementDisplayed(lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				
			this.btnMyContributions.click();
			
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
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
			if(common.isWebElementDisplayed(btnAddOrEditStandard, true)) {
				this.btnAddOrEditStandard.click();											
		} else if(common.isWebElementDisplayed(btnEditStandard, true)) {
				this.btnEditStandard.click();					
		} else {			
				throw new Error("Add or Edit button for Before tax is not displayed");	
			   } 						
			//verify if Add/Edit Standard Page is displayed
			if(common.isWebElementDisplayed(radioSelectAnotherContributionRate, true))
				Reporter.logEvent(Status.PASS, "Click on Add or Edit Button", "Add a Standard Contribution Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add or Edit Button", "Add a Standard Contribution Page is not displayed", false);
			
			//Select Another Contribution Rate option and enter contribution rate for Percent tab
			this.radioSelectAnotherContributionRate.click();
			this.lnkPercent.click();
			this.lnkPercent.click();
			this.lnkContributionRate.click();			
			this.txtcontributionRateSlider.clear();
			this.txtcontributionRateSlider.sendKeys((Stock.globalTestdata.get("Contribution Rate")));
			this.btnDone.click();
			System.out.println(lnksliderValue.getText()); 						
			
			//Verify Accuracy of My Contribution
			boolean sliderValue=common.VerifyText((Stock.globalTestdata.get("Contribution Rate")), lnksliderValue.getText());
			if(sliderValue)
				Reporter.logEvent(Status.PASS, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify accuracy of My Contribution for Before Tax", "My Contribution value is not matching", true);
	
			this.btnContinue.click();					
			if(common.isWebElementDisplayed(lnkPlanRules, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "Before Tax Select Contribution Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "Before Tax Select Contribution Page is not displayed", true);
			
			this.radioSplitContribution.click();
			this.txtSplitBeforeTax.clear();
			this.txtSplitBeforeTax.sendKeys((Stock.globalTestdata.get("Split Before Tax")));
			this.txtSplitRothTax.clear();
			this.txtSplitRothTax.sendKeys((Stock.globalTestdata.get("Split Roth Tax")));					
			this.btnContinueBeforeTaxSplitContribution.click();
			
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Continue button", "My Contributions Page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Continue button", "My Contributions Page is not displayed", false);	
		if(common.isWebElementDisplayed(this.lnkBeforeTaxAutoIncrease, false))
			{				
				Reporter.logEvent(Status.PASS, "Check Add Auto Increase link for Before Tax Split", "Add Auto Increase link is displayed", false);
			this.lnkBeforeTaxAutoIncrease.click();
			if(common.isWebElementDisplayed(btnSaveAddAutoIncreaseModal, true))
				Reporter.logEvent(Status.PASS, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Add Auto Increase for Before Tax", "Add Auto Increase pop up for BeforeTax is not displayed", true);
			this.txtAutoIncreaseMyContributionPercent.clear();
			this.txtAutoIncreaseMyContributionPercent.sendKeys((Stock.globalTestdata.get("Auto Increase Contribution Percent")));
			this.txtAutoIncreaseUntilItReachesPercent.clear();
			this.txtAutoIncreaseUntilItReachesPercent.sendKeys((Stock.globalTestdata.get("Auto Increases Until Reaches Percent")));
			common.selectDropnDownOptionAsIndex(this.drpDownAutoIncreasePeriod, (Stock.globalTestdata.get("Auto Increase Period")));
			try {
				Thread.sleep(1000);
				} catch (Exception e) {
				}		
			this.btnSaveAddAutoIncreaseModal.click();				
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on Save button", "My Contributions Page is displayed with Auto Increase Details", false);
			else
				Reporter.logEvent(Status.FAIL, "Click on Save button", "My Contributions Page is not displayed with Auto Increase Details", false);
			}
			else
				Reporter.logEvent(Status.FAIL, "Check Add Auto Increase link for Before Tax Split", "Add Auto Increase link is not displayed", false);
				
			this.btnConfirmAndContinue.click();
			
			if(common.isWebElementDisplayed(lblContributionDetails, true))
				Reporter.logEvent(Status.PASS, "Click on Confirm and Continue button", "My Contributions Confirmations Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on Confirm and Continue button", "My Contributions Confirmations Page is not displayed", true);
				
			this.btnMyContributions.click();
			if(common.isWebElementDisplayed(tblhdrlblContribution, true))
				Reporter.logEvent(Status.PASS, "Click on My Contributions button", "My Contributions Page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on My Contributions button", "My Contributions Page is not displayed", true);
			
		}
			
	}
		
		


