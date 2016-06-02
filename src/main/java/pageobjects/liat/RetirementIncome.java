
package pageobjects.liat;



import java.sql.ResultSet;
import java.sql.SQLException;

import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;










import org.testng.Assert;

import appUtils.Common;
import pageobjects.landingpage.LandingPage;

public class RetirementIncome extends LoadableComponent<RetirementIncome> {

//Declarations
private LoadableComponent<?> parent;
//private static boolean waitforLoad = false;


//@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Social Security']") private WebElement lnkSocialSecurity;
@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Social Security' and @role='tab']") private WebElement lnkSocialSecurity;
@FindBy(xpath=".//*[text()='Social Security Administration']") private WebElement lnkSocialSecuirtyAdministration;
//@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Other Assets']") private WebElement lnkOtherAssets;

@FindBy(xpath=".//*[@class='ng-binding' and text() = 'Other Assets' and @role='tab']") private WebElement lnkOtherAssets;
@FindBy(xpath=".//button[text()[normalize-space()='Add an account']]") private WebElement btnAddAnAccount;
@FindBy(xpath=".//*[@class='ng-binding ng-scope' and text() = 'Income Gap']") private WebElement lnkIncomeGap;
@FindBy(xpath=".//*[text()[normalize-space()='Catch-up contributions']]") private WebElement txtCatchUpContributions;

@FindBy(xpath="//h1[text()='My Estimated Retirement Income']") private WebElement lblRetirementIncome;
@FindBy(xpath=".//*[text()='Retirement income']") private WebElement lnkRetirementIncome;
@FindBy(xpath="//div[@id='paycheck-breakdown']") private WebElement paycheckBreakdown;
@FindBy(xpath="//div[@class='marker section-savings-marker']") private WebElement barMySavings;
@FindBy(xpath="//div[@class='paycheck-label-inner-container']/h5") private WebElement txtIncomeType;
@FindBy(xpath="//div[@class='paycheck-label-inner-container']/h3") private WebElement txtIncomeTypeValue;
@FindBy(xpath=".//*[contains(text(),'Retirement age')]") private WebElement lblRetirementAge;
@FindBy(xpath="//div[@id='contribution-rate-slider']") private WebElement sliderContributionRate;
@FindBy(xpath="//div[@id='retirement-age-slider']//button[@class='sliderThumb']") private WebElement sliderRetirementAge;
@FindBy(xpath="//div[@id='investment-mix-slider']//button[@class='sliderThumb']") private WebElement sliderInvestmentMix;
//@FindBy(xpath="//div[@class='svg-wrap']") private WebElement myGoalPercent;

@FindBy(xpath="//*[@id='progress-to-goal-inner']") private WebElement myGoalPercent;
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
@FindBy(xpath="//button[@ng-click='viewDetailsClick()']/span[@class='ng-binding ng-scope' and text()[normalize-space()='View Details']]") private WebElement btnViewDetails;
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

//ul/li[contains(@label,'Employer')]/span[@class='paycheck-item-val ng-binding']
//div[@id='doItForMeUnenrolled']//a[text()[normalize-space()='Enroll in managed accounts']]
/** Empty args constructor
 * 
 */
public RetirementIncome() {
	this.parent = new LandingPage();
	PageFactory.initElements(lib.Web.webdriver, this);
}

/** Constructor taking parent as input
 * 
 * @param parent
 */
public RetirementIncome(LoadableComponent<?> parent) {
	this.parent = parent;
	PageFactory.initElements(lib.Web.webdriver, this);
}

@Override
protected void isLoaded() throws Error {
	Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
	String ssn = Stock.GetParameterValue("userName");
	ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
	
	String userFromDatasheet = null;
	try {
		userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
	} catch (SQLException e) {
		e.printStackTrace();
	}		
	
	String userLogedIn = this.lblUserName.getText();
	if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
		Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
		Assert.assertTrue(Web.isWebElementDisplayed(this.hdrEstimatedRetirementIncome));
	} else {
		this.lnkLogout.click();
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
	}
	
}


@Override
protected void load() {
	this.parent.get();
	((LandingPage) this.parent).dismissPopUps(true,true);
	
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

	Web.clickOnElement(this.myGoalPercent);
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

}
		
