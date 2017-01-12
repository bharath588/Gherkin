
package pageobjects.liat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;

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
import pageobjects.landingpage.LandingPage;

public class RetirementIncome extends LoadableComponent<RetirementIncome> {

//Declarations
private LoadableComponent<?> parent;
//private static boolean waitforLoad = false;


//@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Social Security']") private WebElement lnkSocialSecurity;
//@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Social Security' and @role='tab']") private WebElement lnkSocialSecurity;
@FindBy(linkText="Social Security") private WebElement lnkSocialSecurity;
@FindBy(xpath=".//*[text()='Social Security Administration']") private WebElement lnkSocialSecuirtyAdministration;
//@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Other Assets']") private WebElement lnkOtherAssets;
@FindBy(xpath=".//*[text()='Retirement income']") private WebElement tabRetirementIncome;
@FindBy(linkText="Other Assets") private WebElement lnkOtherAssets;
@FindBy(xpath=".//button[text()[normalize-space()='Add an account']]") private WebElement btnAddAnAccount;
@FindBy(xpath="//a[./span[text()='Income Gap']]") private WebElement lnkIncomeGap;
@FindBy(xpath=".//*[text()[normalize-space()='Catch-up contributions']]") private WebElement txtCatchUpContributions;

@FindBy(xpath="//h1[text()='My Estimated Retirement Income']") private WebElement lblRetirementIncome;
@FindBy(xpath=".//*[text()='Retirement income']") private WebElement lnkRetirementIncome;
@FindBy(xpath="//div[@id='paycheck-breakdown']") private WebElement paycheckBreakdown;
@FindBy(xpath="//div[@class='marker section-savings-marker']") private WebElement barMySavings;
@FindBy(xpath="//div[@class='paycheck-label-inner-container']/h5") private WebElement txtIncomeType;
@FindBy(xpath="//div[@class='paycheck-label-inner-container']/h3") private WebElement txtIncomeTypeValue;
@FindBy(xpath=".//*[contains(text(),'Retirement age')]") private WebElement lblRetirementAge;
//@FindBy(xpath="//div[@id='contribution-rate-slider']") private WebElement sliderContributionRate;
@FindBy(xpath="//div[@id='retirement-age-slider']//button[@class='sliderThumb']") private WebElement sliderRetirementAge;
@FindBy(xpath="//div[@id='investment-mix-slider']//button[@class='sliderThumb']") private WebElement sliderInvestmentMix;
//@FindBy(xpath="//div[@class='svg-wrap']") private WebElement myGoalPercent;
@FindBy(xpath="//div[contains(@id,'contribution-rate-slider')]//button[contains(@class,'sliderThumb')]//div[contains(@class,'sliderThumbValue')]") private WebElement sliderContributionRate;
@FindBy(xpath="//pw-radial-chart[@goal-label='of my goal']") private WebElement myGoalPercent;//pw-radial-chart[@goal-label='of my goal']
@FindBy(xpath="//div[@class='modal-header']/h2") private WebElement txtMyGoalPercent;
@FindBy(xpath="//input[@id='monthlyOption']") private WebElement radButtonMonthly;
@FindBy(xpath="//input[@id='yearlyOption']") private WebElement radButtonYearly;
@FindBy(xpath=".//*[@id='progressModal']/.//button[text()[normalize-space()='Cancel']]") private WebElement lnkCancelGoalSetup;
@FindBy(xpath="//ul[@id='investmentTabs']/li/a[text()[normalize-space()='Help Me Do It']]") private WebElement lnkHelpMeDoIt;
@FindBy(xpath="//ul[@id='investmentTabs']/li/a[text()[normalize-space()='Do It Myself']]") private WebElement lnkDoItMyself;
@FindBy(xpath="//ul[@id='investmentTabs']/li/a[text()[normalize-space()='Do It For Me']]") private WebElement lnkDoItForMe;
@FindBy(xpath="//ul[@id='middleTabs']/li/a[text()[normalize-space()='Plan Savings']]") private WebElement lnkPlanSavings;
@FindBy(xpath="//div[@id='helpMeDoItUnenrolled']//ul/li/a[text()='Target Date Funds']") private WebElement lblTargetDateFunds;
@FindBy(xpath="//label[@id='investments-chooser-label']") private WebElement lblInvestment;
@FindBy(xpath="//div[@id='paycheck-breakdown']") private WebElement paycheckRainbowLine;
@FindBy(xpath="//button[@ng-click='viewDetailsClick()']/span[text()[normalize-space()='View Details']]") private WebElement btnViewDetails;
@FindBy(xpath="//ul/li[@label='My Current Savings']/span[@class='paycheck-item-val ng-binding']") private WebElement lblCurentSavings;
@FindBy(xpath="//ul/li[@label='My Future Savings']/span[@class='paycheck-item-val ng-binding']") private WebElement lblFutureSavings;
//@FindBy(xpath="//ul/li[@label='Employer Past Contribution']/span[@class='paycheck-item-val ng-binding']") private WebElement lblEmployerPastContribution;
//@FindBy(xpath="//ul/li[@label='Employer Future Contribution']/span[@class='paycheck-item-val ng-binding']") private WebElement lblEmployerFutureContribution;
@FindBy(xpath="//ul/li[contains(@label,'Employer')]/span[@class='paycheck-item-val ng-binding']") private WebElement lblEmployerPastContribution;
@FindBy(xpath="//ul/li[contains(@label,'Employer')]/span[@class='paycheck-item-val ng-binding']") private WebElement lblEmployerFutureContribution;
@FindBy(xpath="//ul/li[@label='Social Security']/span[@class='paycheck-item-val ng-binding']") private WebElement lblSocialSecurity;
@FindBy(xpath="//ul/li[@label='Other Assets']/span[@class='paycheck-item-val ng-binding']") private WebElement lblOtherAssets;
@FindBy(xpath="//ul/li[@label='Income Gap']/span[@class='paycheck-item-val ng-binding']") private WebElement lblIncomeGap;
@FindBy(xpath=".//*[contains(@class,'projected-income-number currency')]") private WebElement lblProjectedIncome;

//@FindBy(xpath="//button[@class='view-details-trigger hidden-xs']/span[text()[normalize-space()='Close']]") private WebElement lnkCloseViewDetails;
@FindBy(xpath="//button[@ng-click='viewDetailsClick()']/span[@class='ng-binding ng-scope' and text()[normalize-space()='Close']]") private WebElement lnkCloseViewDetails;
@FindBy(xpath="//div[@id='doItForMeUnenrolled']//a[text()[normalize-space()='Enroll in managed accounts']]") private WebElement lnkEnrollInManagedAccounts;
@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
@FindBy(linkText="Log out") private WebElement lnkLogout;
@FindBy(xpath="//h1[text()='My Estimated Retirement Income']") private WebElement hdrEstimatedRetirementIncome;

@FindBy(xpath="//button[text()[normalize-space()='Tour']]") private WebElement btnTour;
@FindBy(xpath="//button[contains(@class,'nextBtn')]") private WebElement btnNext;
@FindBy(xpath="//button[@id='nextBtn']") private WebElement btnNext1;
private String modalHeader="//h3[text()[normalize-space()='webElementText']]";

@FindBy(xpath="//ul[contains(@class,'view-details-list')]//li") private List<WebElement> lstViewDetails;
@FindBy(xpath=".//*[@id='contribution-rate-slider']//span[contains(@class,'editable-text-trigger')]") private WebElement lnkContributionPercent;
@FindBy(xpath=".//*[@id='contributionRate-text-edit']") private WebElement inputContributionRate;
@FindBy(xpath="//button[contains(text(),'Done')]") private WebElement btnDone;
@FindBy(xpath=".//*[@id='pending-changes-btn']") private WebElement btnReviewChanges;
@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

String labelViewDetail="//ul[contains(@class,'view-details-list')]//li[position]//div[contains(@class,'viewDetailsLabel')]";
String valueViewDetail="//ul[contains(@class,'view-details-list')]//li[position]//span[contains(@class,'paycheck-item-val')]";
String lblIncomeType="//div[@class='paycheck-label-inner-container']/h5[contains(text(),'IncomeType')]";
String valIncomeType="//div[@class='paycheck-label-inner-container'][./h5[contains(text(),'IncomeType')]]//h3";


HashMap<String, String> mapViewDetails = new HashMap<String, String>();
//ul/li[contains(@label,'Employer')]/span[@class='paycheck-item-val ng-binding']
//div[@id='doItForMeUnenrolled']//a[text()[normalize-space()='Enroll in managed accounts']]
/** Empty args constructor
 * 
 */
public RetirementIncome() {
	this.parent = new LandingPage();
	PageFactory.initElements(lib.Web.getDriver(), this);
}

/** Constructor taking parent as input
 * 
 * @param parent
 */
public RetirementIncome(LoadableComponent<?> parent) {
	this.parent = parent;
	PageFactory.initElements(lib.Web.getDriver(), this);
}

@Override
protected void isLoaded() throws Error {
	Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Retirement Income Page is Not Loaded");
	String ssn = Stock.GetParameterValue("userName");
	String userFromDatasheet = null;
	ResultSet strUserInfo=null;
	if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
	{
		userFromDatasheet=Stock.GetParameterValue("lblUserName");
	}
	else{
	
	try {
		strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
		
	try {
		userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
	} catch (SQLException e) {
		e.printStackTrace();
	}		
	}
	String userLogedIn = this.lblUserName.getText();
	if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
		Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
		Assert.assertTrue(Web.isWebElementDisplayed(this.hdrEstimatedRetirementIncome));
	} else {
		this.lnkLogout.click();
		Web.waitForElement(this.btnLogin);
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
	}
	
}


@Override
protected void load() {
	this.parent.get();
	//((LandingPage) this.parent).dismissPopUps(true,true);
	this.tabRetirementIncome.click();
	Common.waitForProgressBar();
	Web.waitForPageToLoad(Web.getDriver());
}

/**<pre>Method to read the projected income value from the retirement income page
 * 
 *  
 *  @return - (float) projected retirement income after the string is parsed into float.
 * @throws Exception
 */
private WebElement getWebElement(String fieldName) {

	if (fieldName.trim().equalsIgnoreCase("Retirement Income")) {
		return this.lnkRetirementIncome;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Estimated Retirement Income")) {
		return this.lblRetirementIncome;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Social Security Tab")) {
		return this.lnkSocialSecurity;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Social Security Administration")) {
		return this.lnkSocialSecuirtyAdministration;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Other Assets Tab")) {
		return this.lnkOtherAssets;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Add an Account")) {
		return this.btnAddAnAccount;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Income Gap Tab")) {
		return this.lnkIncomeGap;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Catch up Contributions")) {
		return this.txtCatchUpContributions;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Help Me Do It")) {
		return this.lnkHelpMeDoIt;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Do It Myself")) {
		return this.lnkDoItMyself;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Do It For Me")) {
		return this.lnkDoItForMe;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Plan Savings")) {
		return this.lnkPlanSavings;
	}
	
	
	if (fieldName.trim().equalsIgnoreCase("Contribution rate slider")) {
		return this.sliderContributionRate;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Retirement age slider")) {
		return this.sliderRetirementAge;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Investment mix slider")) {
		return this.sliderInvestmentMix;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Monthly Income")) {
		return this.radButtonMonthly;
	}
	if (fieldName.trim().equalsIgnoreCase("Yearly Income")) {
		return this.radButtonYearly;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Paycheck Rainbow")) {
		return this.paycheckRainbowLine;
	}
	
	if (fieldName.trim().equalsIgnoreCase("My Current Savings")) {
		return this.lblCurentSavings;
	}
	if (fieldName.trim().equalsIgnoreCase("My Future Savings")) {
		return this.lblFutureSavings;
	}
	if (fieldName.trim().equalsIgnoreCase("Employer Past Contribution")) {
		return this.lblEmployerPastContribution;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Employer Future Contribution")) {
		return this.lblEmployerFutureContribution;
	}
	
	if (fieldName.trim().equalsIgnoreCase("Social Security")) {
		return this.lblSocialSecurity;
	}
	if (fieldName.trim().equalsIgnoreCase("Other Assets")) {
		return this.lblOtherAssets;
	}
	if (fieldName.trim().equalsIgnoreCase("Income Gap")) {
		return this.lblIncomeGap;
	}
	if (fieldName.trim().equalsIgnoreCase("TOUR Button")) {
		return this.btnTour;
	}
	if (fieldName.trim().equalsIgnoreCase("Next Button")) {
		return this.btnNext;
	}
	if (fieldName.trim().equalsIgnoreCase("Finish Button")) {
		return this.btnNext1;
	}
	if (fieldName.trim().equalsIgnoreCase("NextButton")) {
		return this.btnNext1;
	}
	if (fieldName.trim().equalsIgnoreCase("VIEW DETAILS")) {
		return this.btnViewDetails;
	}
	if (fieldName.trim().equalsIgnoreCase("Cancel Goal setup")) {
		return this.lnkCancelGoalSetup;
	}
		if (fieldName.trim().equalsIgnoreCase("Review Changes")) {
		return this.btnReviewChanges;
	}
		// Log out
				if (fieldName.trim().equalsIgnoreCase("LOG OUT")
								|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
							return this.lnkLogout;
						}

	return null;

}

/**<pre>Method to read the projected income value from the retirement income page
 * 
 *  
 *  @return - (float) projected retirement income after the string is parsed into float.
 * @throws Exception
 */

public float getProjectedIncome(){
	float projectedIncome = 0;
	if (Web.isWebElementDisplayed(this.lblRetirementIncome)) {
		projectedIncome = Web.getIntegerCurrency(this.lblProjectedIncome.getText());
	} else {
		this.lnkRetirementIncome.click();
		try {
			Web.waitForElement(lblRetirementIncome);
			projectedIncome = Web.getIntegerCurrency(this.lblProjectedIncome.getText());
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Get Estimated Projected Income", "User was unable to navigated to the Retirement Income page and get the required value", false);
			e.printStackTrace();
		}
	}
	
	return projectedIncome;
}

//public float getProjectedIncome(){
//	float currentSavingsAmount = 0;
//	float futureSavingsAmount = 0;
//	float employerPastContributionAmount = 0;
//	float employerFutureContributionAmount = 0;
//	float socialsecurityAmount = 0;
//	float otherAssetsAmount = 0;
//	float projectedIncome = 0;
//	
//	
//	this.lnkRetirementIncome.click();
//	try {
//		Web.waitForElement(lblRetirementIncome);
//	} catch (Exception e) {
//		//
//	}
//		
//	
//	Web.clickOnElement(btnViewDetails);
//	
//	if(Web.isWebElementDisplayed(lblCurentSavings)){
//		currentSavingsAmount = Web.getIntegerCurrency(this.lblCurentSavings.getText());
//		System.out.println("current savings"+this.lblCurentSavings.getText());
//	}
//	if(Web.isWebElementDisplayed(lblFutureSavings)){
//		futureSavingsAmount = Web.getIntegerCurrency(this.lblFutureSavings.getText());
//		System.out.println("future savings:"+this.lblFutureSavings.getText());
//	}
//	
//	if(Web.isWebElementDisplayed(lblEmployerPastContribution)){
//		employerPastContributionAmount = Web.getIntegerCurrency(this.lblEmployerPastContribution.getText());
//		System.out.println(this.lblEmployerPastContribution.getText());
//	}
//	if(Web.isWebElementDisplayed(lblEmployerFutureContribution)){
//		employerFutureContributionAmount = Web.getIntegerCurrency(this.lblEmployerFutureContribution.getText());
//		System.out.println(this.lblEmployerFutureContribution.getText());
//	}
//	
//	if(Web.isWebElementDisplayed(lblSocialSecurity)){
//		socialsecurityAmount = Web.getIntegerCurrency(this.lblSocialSecurity.getText());
//		System.out.println(this.lblSocialSecurity.getText());
//	}
//	
//	if(Web.isWebElementDisplayed(lblOtherAssets)){
//		otherAssetsAmount = Web.getIntegerCurrency(this.lblOtherAssets.getText());
//		System.out.println(this.lblOtherAssets.getText());
//	}
//	projectedIncome = currentSavingsAmount+futureSavingsAmount+employerFutureContributionAmount+employerPastContributionAmount+socialsecurityAmount+otherAssetsAmount;
//	System.out.println(projectedIncome);
//	
//	return projectedIncome;
//}

/**<pre> Method to check if a particular element is present on the page.
 *.</pre>
 * @param fieldName - fieldname
 * 
 * @return - boolean
 */
public boolean isFieldDisplayed(String fieldName) {
	WebElement element = this.getWebElement(fieldName);
	
	if (element == null) {
		return false;
	} else {
		return Web.isWebElementDisplayed(element);
	}
}


/**<pre> Method to get the value for given contribution type.
 *.</pre>
 * @param contributionType - type of contribution made by the participant
 * 
 * @return - String
 */
public String getContributionValueFromViewDetails(String contributionType){
	String contributionValue=null;
	
	WebElement element = this.getWebElement(contributionType);
	
	if(Web.isWebElementDisplayed(element)){
		contributionValue=element.getText();
	}
	System.out.println(contributionValue);
	return contributionValue;
}

/**<pre> Method to verify if view details button is present.
 *.</pre>
 * 
 * 
 * @return - boolean
 */

public boolean verifyViewDetailsLink(){
	boolean issuccess = false;
	if(Web.isWebElementDisplayed(this.btnViewDetails)){
		this.btnViewDetails.click();
		issuccess = true;
	}
	else{
		throw new Error("View Details button not found");
	}
	return issuccess;
	
}


/**<pre> Method to verify if we are able to close view details.
 *.</pre>
 * 
 * 
 * @return - boolean
 */
public boolean verifyViewDetailsCloseLink(){
	System.out.println("inside close button");
	boolean issuccess = false;
	if(Web.isWebElementDisplayed(this.lnkCloseViewDetails)){
		this.lnkCloseViewDetails.click();
		issuccess = true;
	}
	else{
		throw new Error("Close View Details button not found");
	}
	return issuccess;
	
}



public String getPaycheckSliceDetails(){
	
	return paycheckRainbowLine.getText();
}


/**<pre> Method to verify if we are able to navigate to a tab.
*.</pre>
* @param tabName - a String value - tab name on retirement income page"
* 
* @return - boolean
*/
public boolean navigateToTabAndVerify(String tabName) {
	boolean isSuccess = false;
	WebElement element = this.getWebElement(tabName);
	
	if (tabName.trim().equalsIgnoreCase("Help Me Do It")) {
		if(Web.isWebElementDisplayed(element)){
			element.click();
			if(Web.isWebElementDisplayed(lblTargetDateFunds))
				isSuccess=true;
		}
	}
	else if(tabName.trim().equalsIgnoreCase("Do It Myself")) {
		if(Web.isWebElementDisplayed(element)){
			element.click();
			if(Web.isWebElementDisplayed(sliderInvestmentMix))
				isSuccess=true;
		}
	}
	else if(tabName.trim().equalsIgnoreCase("Plan Savings")) {
		if(Web.isWebElementDisplayed(element)){
			element.click();
			if(Web.isWebElementDisplayed(lblInvestment))
				isSuccess=true;
		}
	}
	
	else if(tabName.trim().equalsIgnoreCase("Do It For Me")) {
		if(Web.isWebElementDisplayed(element)){
			element.click();
			if(Web.isWebElementDisplayed(lnkEnrollInManagedAccounts))
				isSuccess=true;
		}
	}
	
	else {
		return false;
	}
		
		return isSuccess;
	}

/**<pre> Method to verify if a slider is present on retirement income page.
 *.</pre>
 * @param sliderName - a String value - slider name displyed on retirement income page"
 * 
 * @return - boolean
 */

public boolean verifyIfSliderPresent(String sliderName){
	boolean isSuccess = false;
	WebElement element = this.getWebElement(sliderName);
	if(Web.isWebElementDisplayed(element,true))
		isSuccess = true;
	
	return isSuccess;
	
}


/**<pre> Method to verify the percent of my goal section.
 *.</pre>
 * @param incomeType - a String value - retirement income type can be monthly or yearly"
 * 
 * @return - String
 * @throws InterruptedException 
 */

public String verifyPercentOfMyGoalSection(String retirementIncomeView) throws InterruptedException{
	String modalHeader =null;
	WebElement element = this.getWebElement(retirementIncomeView);
		Web.waitForElement(myGoalPercent);
	Web.clickOnElement(myGoalPercent);
	Thread.sleep(3000);
		if(Web.isWebElementDisplayed(element)){
			if(!element.isSelected()){
				element.click();
			}
			modalHeader = txtMyGoalPercent.getText();
		}
	
	if (Web.isWebElementDisplayed(lnkCancelGoalSetup)) {
		this.lnkCancelGoalSetup.click();
	}
	
	return modalHeader;
}

public void verifyTourModals(String modalHeaderTxt) throws InterruptedException,NoSuchElementException{
	boolean isModalHeaderDisplayed=false;
	 Thread.sleep(2000);
	 WebElement lblModalHeader= Web.getDriver().findElement(By.xpath(modalHeader.replace("webElementText", modalHeaderTxt)));
	 Web.waitForElement(lblModalHeader);
	 Thread.sleep(3000);
	 isModalHeaderDisplayed = Web.isWebElementDisplayed(lblModalHeader, true);
	if (isModalHeaderDisplayed)
		lib.Reporter.logEvent(Status.PASS, "Verify '" + modalHeaderTxt+ "' modal is Displayed", "'"+modalHeaderTxt + "' modal is Displayed",true);
	else 
		lib.Reporter.logEvent(Status.FAIL, "Verify " + modalHeaderTxt+ "' modal is Displayed","'"+modalHeaderTxt + "' modal is Not Displayed", false);
	
	
}


/**<pre> Method to verify the PayCheck Contribution and Color bar is displayed for NonZero Values.
 *.</pre>
 * @param label
 * @throws InterruptedException 
 */

public void verifyPayCheckContributionInColorBarForNonZeroValue(String label) throws InterruptedException{
	
	int i;
	int listofelements=lstViewDetails.size();
for( i=1;i<=listofelements;i++)
{
	WebElement lblViewDetail= Web.getDriver().findElement(By.xpath(labelViewDetail.replace("position",Integer.toString(i) )));
	WebElement valViewDetail= Web.getDriver().findElement(By.xpath(valueViewDetail.replace("position",Integer.toString(i) )));
	mapViewDetails.put(lblViewDetail.getText(), valViewDetail.getText());
	
}

if(mapViewDetails.containsKey(label)){
		if(!mapViewDetails.get(label).equalsIgnoreCase("$0.00")){
	if(label.contains("Saving"))
	{
			WebElement lblincomeType= Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType","My Savings")));
			if(Web.isWebElementDisplayed(lblincomeType, true))
			{
				
				Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", "My Savings is Displayed on Color Bar", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", "My Savings is  not Displayed on Color Bar", false);
			}
	}
	else if(label.contains("Employer"))
	{
		WebElement lblincomeType= Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType","Employer Contributions")));
		if(Web.isWebElementDisplayed(lblincomeType, true))
		{
			
			Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", "Employer Contributions is Displayed on Color Bar", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", "Employer Contributions is  not Displayed on Color Bar", false);
		}
	}
	else
	{
		WebElement lblincomeType= Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType",label)));
		if(Web.isWebElementDisplayed(lblincomeType, true))
		{
			
			Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", label+" is Displayed on Color Bar", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", label+" is not Displayed on Color Bar", false);
		}
	}
	}

		else if(mapViewDetails.get(label).equalsIgnoreCase("$0.00"))
		{
			
			if(label.contains("Saving"))
			{
				if(mapViewDetails.get("My Current Savings").equalsIgnoreCase("$0.00") && mapViewDetails.get("My Future Savings").equalsIgnoreCase("$0.00")){
					if(Web.isWebElementDisplayed(Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType","My Savings")))))
					{
						Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", "My Savings is not Displayed on Color Bar", false);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", "My Savings is Displayed on Color Bar", false);
					}
				}
			}
			else if(label.contains("Employer"))
			{
				if(Web.isWebElementDisplayed(Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType","Employer Contributions")))))
				{
					
					Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", "Employer Contributions is not Displayed on Color Bar", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", "Employer Contributions is Displayed on Color Bar", false);
				}
			}
			else
			{
			boolean blnElementDisplayed=true;
			
			try{
				WebElement lblincomeType= Web.getDriver().findElement(By.xpath(lblIncomeType.replace("IncomeType",label)));
				if(lblincomeType.isDisplayed())
					blnElementDisplayed=true;
			}
			catch(NoSuchElementException e){
				blnElementDisplayed = false;
			}
				if(!blnElementDisplayed)
				{
					Reporter.logEvent(Status.PASS, "Verify Income Type is Displayed on Color Bar", label+" is not Displayed on Color Bar", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Income Type is Displayed on Color Bar", label+" is Displayed on Color Bar", false);
				}
			}
			
		}
}
else{
	Reporter.logEvent(Status.INFO, "Verify Income Type is Displayed on Color Bar", label+" is not Applicable", false);
}

}
/**<pre> Method to verify the PayCheck Contribution and Color bar is displayed for NonZero Values.
 *.</pre>
 * @param label
 * @throws InterruptedException 
 */

public void verifyValueInColorBarForNonZeroValue(String label) throws InterruptedException{
	int listofelements=lstViewDetails.size();
	double currentSavings=0.00;
	double futureSavings=0.00;
	double currentContribution=0.00;
	double futureContribution=0.00;
	int mySavings;
	mapViewDetails.clear();
for( int i=1;i<=listofelements;i++)
{
	WebElement lblViewDetail= Web.getDriver().findElement(By.xpath(labelViewDetail.replace("position",Integer.toString(i) )));
	WebElement valViewDetail= Web.getDriver().findElement(By.xpath(valueViewDetail.replace("position",Integer.toString(i) )));
	if(!valViewDetail.getText().equalsIgnoreCase("$0.00")){
	mapViewDetails.put(lblViewDetail.getText(), valViewDetail.getText());
}
	
}
if(label.contains("Savings"))
{
	
if(mapViewDetails.containsKey("My Current Savings")){
	
currentSavings=Double.parseDouble(mapViewDetails.get("My Current Savings").substring(1).replaceAll(",", ""));
}
if(mapViewDetails.containsKey("My Future Savings")){
	futureSavings=Double.parseDouble(mapViewDetails.get("My Future Savings").substring(1).replaceAll(",", ""));
}
mySavings=(int)Math.round((currentSavings+futureSavings));
			WebElement valincomeType= Web.getDriver().findElement(By.xpath(valIncomeType.replace("IncomeType","My Savings")));
			int savings =Integer.parseInt(valincomeType.getText().substring(1).replaceAll(",", ""));
			if(mySavings==savings || mySavings==savings+1 ||  mySavings==savings-1){
							
				Reporter.logEvent(Status.PASS, "Verify Value for Income Type is Displayed on Color Bar", "Value for My Savings is Same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify Value for Income Type is Displayed on Color Bar", "Value for My Savings is not Same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, true);
			}
	}
	else if(label.contains("Employer"))
	{	
		if(mapViewDetails.containsKey("Employer Past Contribution")){
			
			currentContribution=Double.parseDouble(mapViewDetails.get("Employer Past Contribution").substring(1).replaceAll(",", ""));
			}
			if(mapViewDetails.containsKey("Employer Future Contribution")){
				futureContribution=Double.parseDouble(mapViewDetails.get("Employer Future Contribution").substring(1).replaceAll(",", ""));
			}
			mySavings=(int)Math.round((currentContribution+futureContribution));
						WebElement valincomeType= Web.getDriver().findElement(By.xpath(valIncomeType.replace("IncomeType","Employer Contributions")));
						int savings =Integer.parseInt(valincomeType.getText().substring(1).replaceAll(",", ""));
						if(mySavings==savings || mySavings==savings+1 ||  mySavings==savings-1){
												
			Reporter.logEvent(Status.PASS, "Verify Value for Income Type is Displayed on Color Bar", "Value for Employer Contributions is Same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify Value for Income Type is Displayed on Color Bar", "Value for Employer Contributions is  not Same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, false);
		}
	}
	else
	{
		if(mapViewDetails.containsKey(label)){
		mySavings=(int)Math.round((Double.parseDouble(mapViewDetails.get(label).substring(1).replaceAll(",", ""))));
		WebElement valincomeType= Web.getDriver().findElement(By.xpath(valIncomeType.replace("IncomeType",label)));
		int savings =Integer.parseInt(valincomeType.getText().substring(1).replaceAll(",", ""));
		if(mySavings==savings || mySavings==savings+1 ||  mySavings==savings-1){
										
			Reporter.logEvent(Status.PASS, "Verify Value for Income Type is Displayed on Color Bar", "Value for "+label+" is same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify Value for Income Type is Displayed on Color Bar", "Value for "+label+" is not same on Color Bar\nExpected:"+mySavings+"\nActual:"+savings, false);
		}
		}
	}
	
}
/**<pre> Method to verify if a slider is enabled on retirement income page.
 *.</pre>
 * @param sliderName - a String value - slider name Enabled on retirement income page"
 * 
 * @return - boolean
 */

public boolean verifyIfSliderEnabled(String sliderName){
	boolean isSuccess = false;
	WebElement element = this.getWebElement(sliderName);
	Web.waitForElement(element);
	if(element.isEnabled())
		isSuccess = true;

	return isSuccess;
	
}
/**<pre> Method to verify the percent of my goal section.
 *.</pre>
 * @param incomeType - a String value - retirement income type can be monthly or yearly"
 * 
 * @return - String
 * @throws InterruptedException 
 */

public void enterContributionRate(String contributionRate,boolean... args) throws InterruptedException{
	Actions keyBoard = new Actions(Web.getDriver());
	Web.waitForElement(lnkContributionPercent);
	if (args.length > 0) {
		Web.clickOnElement(this.lnkContributionPercent);
		Thread.sleep(3000);
			if(Web.isWebElementDisplayed(inputContributionRate)){
				Web.setTextToTextBox(inputContributionRate, contributionRate);
			}
			keyBoard.sendKeys(Keys.TAB).perform();
	}
	else{
           int contributionrate=Integer.parseInt(lnkContributionPercent.getText().split("%")[0])+1;
	Web.clickOnElement(this.lnkContributionPercent);
	Thread.sleep(3000);
		if(Web.isWebElementDisplayed(inputContributionRate)){
			Web.setTextToTextBox(inputContributionRate, Integer.toString(contributionrate));
		}
		keyBoard.sendKeys(Keys.TAB).perform();
	
	}
	
}

}
		
