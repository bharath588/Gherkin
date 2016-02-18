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
import lib.Reporter.Status;


public class PriorPlanContributions extends LoadableComponent<PriorPlanContributions>{
	
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;
	@FindBy(xpath="//h1[text()='Prior Plan Contributions']") private WebElement lblPriorContributions;
	@FindBy(xpath="//input[@id='radioPreviousContributionYes']") private WebElement radioYes;
	@FindBy(xpath="//input[@id='radioPreviousContributionNo']") private WebElement radioNo;
//	@FindBy(xpath="//button[@id='btnSubmit']") private WebElement btnsubmit;
	@FindBy(xpath="//button[@id='btnSubmit submit']") private WebElement btnsubmit;
	@FindBy(xpath="//button[@id='btnContinue']") private WebElement btnContinue;
//	@FindBy(xpath="//div[@class='page-title ng-scope']/p']") private WebElement txtPriorContribution;
	@FindBy(xpath="//p[@class='ng-binding']") private WebElement txtPriorContribution;
	@FindBy(xpath="//input[@id='regular']") private WebElement txtYearToDateContribution;
	@FindBy(xpath="//input[@id='catchup']") private WebElement txtCatchupContribution;
	@FindBy(xpath="//h2[text()='Confirmation Details']") private WebElement lblConfirmationDetails;
//	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr[0]/td") private WebElement lblYearToDateContribution;
	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr[1]/td") private WebElement lblYearToDateContribution;
//	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr[1]/td") private WebElement lblCatchupContribution;
	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr[2]/td") private WebElement lblCatchupContribution;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	
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
			if (!lib.Web.isWebElementDisplayed(lblPriorContributions,PriorPlanContributions.waitforLoad)) {
				PriorPlanContributions.waitforLoad = true;
				throw new Error("'My contributions' page is not loaded");
			}else{
				PriorPlanContributions.waitforLoad = false;
			}
		} else {
			this.lnkLogout.click();
		}
	}
	
	@Override
	protected void load() {
		this.parent.get();	
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Prior plan contributions");
	}

	public void verifyPriorPlanContributionsPage(){
		
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		String actualText = txtPriorContribution.getText();
		if(lib.Web.VerifyText("Have you made contributions to any other retirement plans since 1/1/"+"year",actualText , true))
			Reporter.logEvent(Status.PASS, "Verify text in Prior Contributions page", "text is matching", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify text in Prior Contributions page", "text is not matching", true);
		
		if(lib.Web.isWebElementDisplayed(radioYes))
			Reporter.logEvent(Status.PASS, "Verify Yes Radio button", "Yes Radio button is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Yes Radio button", "Yes Radio button is displayed", true);
		
		if(lib.Web.isWebElementDisplayed(radioNo))
			Reporter.logEvent(Status.PASS, "Verify No Radio button", "No Radio button is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify No Radio button", "No Radio button is displayed", true);
	}
	
	public boolean verifyParticipantsHiredInPriorYear(){
		boolean issuccess = false;
		String actualMessage = null;
		String expectedMessage = lib.Stock.GetParameterValue("Expected_message");
		issuccess = lib.Web.VerifyText(expectedMessage, actualMessage, true);
		return issuccess;
	}
	
	public void enterContributionValue(String yearToDateContribution, String catchupContribution){
		this.radioYes.click();
		if(!yearToDateContribution.equalsIgnoreCase("null"))
			lib.Web.setTextToTextBox(txtYearToDateContribution,yearToDateContribution);
		if(!catchupContribution.equalsIgnoreCase("null"))
			lib.Web.setTextToTextBox(txtCatchupContribution,catchupContribution);
		this.btnsubmit.click();
	}
	
	public boolean verifyConfirmationDetails(String value, String type){
		Boolean success = false;
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(type.equalsIgnoreCase("Year To Date"))
			success = lib.Web.VerifyText("$"+value+".00", lblYearToDateContribution.getText(), true);
		
		if(type.equalsIgnoreCase("Catch up"))
			success = lib.Web.VerifyText("$"+value+".00", lblCatchupContribution.getText(), true);
		
		this.btnContinue.click();
		try {
			lib.Web.waitForElement(lblPriorContributions);
		} catch (Exception e) {
		}
		return success;
	}
	
	
}
