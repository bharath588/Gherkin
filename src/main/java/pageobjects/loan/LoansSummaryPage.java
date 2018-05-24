package pageobjects.loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import oracle.sql.ARRAY;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;
import core.framework.Globals;

public class LoansSummaryPage extends LoadableComponent<RequestLoanPage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = "//h1[text()[normalize-space()='Loans summary']]")
	private WebElement lblLoanSummary;
	
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//tr[@id='loanSummaryData']")
	private List<WebElement> loanRows;
	@FindBy(xpath = "//tr[@id='loanSummaryData']/td[@id='loanNo']")
	private List<WebElement> loanRowsId;
	@FindBy(id = "legacyFeatureIframe")
	private WebElement iframeLegacyFeature;
	
	/**
	 * Default Constructor
	 */
	public LoansSummaryPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public LoansSummaryPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestALoan, true),"Request A Loan Page is Not Loaded");
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
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}

		
		String userLogedIn = this.lblUserName.getText();
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblLoanSummary),"Loans Summary Page is not loadeded\n");
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

		((LeftNavigationBar) this.parent).clickNavigationLink("Loan Summary");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		lib.Web.isWebElementDisplayed(lblLoanSummary,true);
		

	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	LOG OUT Or LOGOUT - [Link]
	 * 	HOME - [Link]
	 * 	MY ACCOUNTS - [Link]
	 * 	RETIREMENT INCOME - [Label]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
				|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
			return this.lnkLogout;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	public void verifyParticipanttakenToLoanSummaryPage()
	{
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(lblLoanSummary);
		if(Web.isWebElementDisplayed(lblLoanSummary,true))
			Reporter.logEvent(Status.PASS,
					"Verify \"View details\" link when clicked will take the participant to the existing loan summary page",
					"Participant is taken to loan summary page", true);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify \"View details\" link when clicked will take the participant to the existing loan summary page",
					"Participant is taken to loan summary page", true);
		
	}
	public void verifyLoanSummaryPage()
	{
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if(Web.isWebElementDisplayed(loanRows.get(0), true))
			Reporter.logEvent(Status.PASS,
					"Verify if loan summary is available",
					"Loan summary are displayed", true);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify if loan summary is available",
					"Loan summary are not displayed", true);
		
		if(loanRows.size()>1)
		{
			for(int i=0;i<loanRows.size();i++)
			{
				Reporter.logEvent(Status.PASS,
						"There are multiple loans summary available",
						"Load ID for the "+i+1+"st summary is "+loanRowsId.get(i).getText(), true);
			}
			
		}
		else if(loanRows.size()==1)
		{
			Reporter.logEvent(Status.PASS,
					"There is only one multiple loans summary available",
					"Load ID for the summary is "+loanRowsId.get(0).getText(), true);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,
					"Verify if loan summary is available",
					"There is no loans summary available", true);
		}
	}
}
