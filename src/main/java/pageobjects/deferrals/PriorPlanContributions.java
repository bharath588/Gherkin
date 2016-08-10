package pageobjects.deferrals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.general.LeftNavigationBar;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;


public class PriorPlanContributions extends LoadableComponent<PriorPlanContributions>{
	
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;
	@FindBy(xpath="//h1[text()='Prior Plan Contributions']") private WebElement lblPriorContributions;
	@FindBy(xpath="//label[contains(text(),'Yes')]") private WebElement labelYes;
	@FindBy(xpath="//label[contains(text(),'No')]") private WebElement labelNo;
//	@FindBy(xpath="//button[@id='btnSubmit']") private WebElement btnsubmit;
	@FindBy(xpath="//button[contains(text(),'Save and Close')]") private WebElement btnSaveAndClose;
	@FindBy(xpath="//button[contains(text(),'Cancel')]") private WebElement btnCancel;
	@FindBy(xpath="//button[contains(@ng-click,'editPrevContribution')]") private WebElement btnEdit;
//	@FindBy(xpath="//div[@class='page-title ng-scope']/p']") private WebElement txtPriorContribution;
	@FindBy(xpath="//td[@class='col-sm-9']//div[contains(@class,'title ng-binding')]") private WebElement txtPriorContribution;
	@FindBy(xpath=".//a[./font[contains(text(),'Why is this important?')]]") private WebElement lnkWhyThisIsImportant;
	
	@FindBy(xpath="//input[@id='catchup']") private WebElement txtCatchupContribution;
	
	@FindBy(xpath="//p[contains(text(),'I have')]") private WebElement lblContribution;
	@FindBy(xpath="//input[contains(@ng-model,'regularPrevContribution')]") private WebElement inputYearToDateContribution;
//	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr[1]/td") private WebElement lblCatchupContribution;
	@FindBy(xpath="//input[contains(@ng-model,'catchupPrevContribution')]") private WebElement inputCatchupContribution;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//div[contains(@class,'text-notation')]") private WebElement txtYearToDateContribution;
	
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/**
	 * Default Constructor
	 */
	public PriorPlanContributions() {
	
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Argument Constructor with parent as input
	 * 
	 * @param parent
	 */
	public PriorPlanContributions(LoadableComponent<?> parent) {
		this.parent = parent;			
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblPriorContributions),"My Contribution Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
				
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		String userLogedIn = this.lblUserName.getText();
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));	
			//Assert.assertTrue(Web.isWebElementDisplayed(txtPriorContribution,true));
			
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
	}
	
	@Override
	protected void load() {
		this.parent.get();	
		
		((LeftNavigationBar) this.parent).clickNavigationLink("My contributions");
	}
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	LOG OUT Or LOGOUT - [Link]
	 * 	HOME - [Link]
	 * 	MY ACCOUNTS - [Link]
	 * 	RETIREMENT INCOME - [Label]
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT") || fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT PRIOR PLAN CONTRIBUTION")) {
			return this.txtPriorContribution;
		}
		
		//SAVE AND CLOSE
		if (fieldName.trim().equalsIgnoreCase("SAVE AND CLOSE")) {
			return this.btnSaveAndClose;
		}
		
		//CANCEL
		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
			return this.btnCancel;
		}
		
		//YES
		if (fieldName.trim().equalsIgnoreCase("YES")) {
			return this.labelYes;
		}
		//NO
		if (fieldName.trim().equalsIgnoreCase("NO")) {
			return this.labelNo;
		}
		if (fieldName.trim().equalsIgnoreCase("YEAR TO DATE CONTRIBUTION")) {
			return this.inputYearToDateContribution;
		}
		if (fieldName.trim().equalsIgnoreCase("CATCHUP CONTRIBUTION")) {
			return this.inputCatchupContribution;
		}
		if (fieldName.trim().equalsIgnoreCase("EDIT")) {
			return this.btnEdit;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		
		return null;
	}
	
	public void verifyPriorPlanContributionsPage(){
		
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		Web.waitForElement(txtPriorContribution);
		String actualText = txtPriorContribution.getText();
		if(lib.Web.VerifyPartialText("Have you made contributions to any other retirement plans since 1/1/"+year+"?",actualText , true))
			Reporter.logEvent(Status.PASS, "Verify text in Prior Contributions page", "text is matching", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify text in Prior Contributions page", "text is not matching", true);
		String toolTip=lnkWhyThisIsImportant.getAttribute("uib-tooltip-html");
		if(lib.Web.VerifyPartialText(Stock.GetParameterValue("toolTip"),toolTip , true))
			Reporter.logEvent(Status.PASS, "Verify ToolTip in Prior Contributions page", "ToolTip is matching", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify ToolTip in Prior Contributions page", "ToolTip is not Same", true);
		if(lib.Web.isWebElementDisplayed(labelYes))
			Reporter.logEvent(Status.PASS, "Verify Yes Radio button", "Yes Radio button is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Yes Radio button", "Yes Radio button is Not displayed", true);
		
		if(lib.Web.isWebElementDisplayed(labelNo))
			Reporter.logEvent(Status.PASS, "Verify No Radio button", "No Radio button is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify No Radio button", "No Radio button is Not displayed", true);
		this.labelYes.click();
		actualText = txtYearToDateContribution.getText();
		if(lib.Web.VerifyPartialText("This includes 401(k) pre-tax, Roth 401(k), 403(b), SARSEP and Simple IRA contributions.",actualText , true))
			Reporter.logEvent(Status.PASS, "Verify Year to date contributions: text in Prior Contributions page", "text is matching", true);
		else
			Reporter.logEvent(Status.FAIL, "Verify Year to date contributions: text in Prior Contributions page", "text is not matching", true);
		
		if(lib.Web.isWebElementDisplayed(btnSaveAndClose))
		{
			Reporter.logEvent(Status.PASS, "Verify SaveAndClose button is Displyed", "SaveAndClose button is displayed", false);
			actualText = btnSaveAndClose.getAttribute("disabled");
			if(lib.Web.VerifyText("true",actualText , true))
				Reporter.logEvent(Status.PASS, "Verify SaveAndClose button is Disabled", "SaveAndClose button is Disabled", false);
			else
				Reporter.logEvent(Status.FAIL,  "Verify SaveAndClose button is Disabled", "SaveAndClose button is Not Disabled /nExpected:disabled /nActual:"+actualText, true);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify SaveAndClose button is Displyed", "SaveAndClose button is  not displayed", true);
		if(lib.Web.isWebElementDisplayed(btnCancel))
			Reporter.logEvent(Status.PASS, "Verify Cancel button", "Cancel button is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Cancel button", "Cancel button is Not displayed", true);
	}
	
	public boolean verifyParticipantsHiredInPriorYear(){
		boolean issuccess = false;
		String actualMessage = null;
		String expectedMessage = lib.Stock.GetParameterValue("Expected_message");
		issuccess = lib.Web.VerifyText(expectedMessage, actualMessage, true);
		return issuccess;
	}
	
	public void enterContributionValue(String contributionType, String value) throws InterruptedException{
		
		if(contributionType.equalsIgnoreCase("YEAR TO DATE CONTRIBUTION")){
			Thread.sleep(4000);
			Web.waitForElement(inputYearToDateContribution);
			Web.clickOnElement(inputYearToDateContribution);
			Web.setTextToTextBox(this.inputYearToDateContribution,value);
		}
		if(contributionType.equalsIgnoreCase("CATCHUP CONTRIBUTION")){
			Thread.sleep(4000);
			Web.waitForElement(inputCatchupContribution);
			Web.clickOnElement(inputCatchupContribution);
			Web.setTextToTextBox(this.inputCatchupContribution,value);
		}
	}
	
	public boolean verifyCoontributionMessage(String value){
		Boolean success = false;
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			
		if(Web.isWebElementDisplayed(this.lblContribution, true)){
			success = lib.Web.VerifyText("I have contributed $"+value+".00 to other retirement plans since 1/1/"+year+".", lblContribution.getText(), true);
		}
		return success;
	}

	
}
