package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RateOfReturnPage extends LoadableComponent<RateOfReturnPage> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//*[@id='account-details-container']//h1")
	private WebElement lblRateOfReturn;
	@FindBy(id = "period")
	private WebElement drpPeriod;
	@FindBy(id = "submit")
	private WebElement btnGo;
	/*@FindBy(xpath = ".//*[@id='overview-investments-table']//th[2]")
	private WebElement tableHeaderFrom;
	@FindBy(xpath = ".//*[@id='overview-investments-table']//th[3]")
	private WebElement tableHeaderTo;
	@FindBy(xpath = ".//*[@id='overview-investments-table']//th[4]")
	private WebElement tableHeaderReturn;
	@FindBy(xpath = ".//*[@id='overview-investments-table']//tr//td[2]")
	private WebElement tableBodyFrom;
	@FindBy(xpath = ".//*[@id='overview-investments-table']//tr//td[3]")
	private WebElement tableBodyTo;
	@FindBy(xpath = ".//*[@id='overview-investments-table']//tr//td[4]")
	private WebElement tableBodyReturn;
	*/
	@FindBy(xpath = ".//*[@ng-model='rateOfReturnCtrl.startDate']")
	private WebElement startDate;
	@FindBy(xpath = ".//*[@ng-model='rateOfReturnCtrl.endDate']")
	private WebElement endDate;
	 @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath=".//*[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	private String tableHeader =".//*[@id='overview-investments-table']//th[HeaderIndex]";
	private String tableContent=".//*[@id='overview-investments-table']//tr//td[ContentIndex]";
	
	//Bhargav	
	@FindBy(xpath="//h1[text()='Rate of Return']") private WebElement rorPageHeader;
	@FindBy(xpath="//span[contains(@ng-class,'rateOfReturn') and contains(@ng-class,'annualized')]") private WebElement rorAnnualizedPercent;
	@FindBy(xpath="//span[contains(@ng-class,'rateOfReturn') and contains(@ng-class,'cummulative')]") private WebElement rorcummulativePercent;
	@FindBy(xpath="(//table//td[not(i)])[1]") private WebElement sFromDate;
	@FindBy(xpath="(//table//td[not(i)])[2]") private WebElement sToDate;
	@FindBy(xpath="//*[text()='Account Information']/parent::div[contains(@class,'nav')]//*[contains(text(),'Rate of return')]")
    private WebElement sLeftNavRORLink;
	
	/**
	 * Default Constructor
	 */
	public RateOfReturnPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RateOfReturnPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblRateOfReturn, true),"Rate Of Return Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		 else {

				try {
					strUserInfo =Common.getParticipantInfoFromDataBase(ssn);;
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
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblRateOfReturn),"Rate of return Page is Not Loaded\n");
		} else {
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}

	}

	@Override
	protected void load() {
		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}
		
		this.parent.get();

		((LeftNavigationBar) this.parent).clickNavigationLink("Rate Of Return");
		Web.waitForPageToLoad(Web.getDriver());
		Common.waitForProgressBar();

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

	/**
	 * <pre>
	 * Method to validate if customer care information is displayed or not.
	 * Returns the customer care info if it is displayed
	 * Returns empty string if customer care info block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed
	 * @throws InterruptedException 
	 */
/*	public boolean verifyDataInRateOfReturnPage() throws InterruptedException {
		boolean isElementDisplayed = false;
		boolean isTextMatching = false;

		isTextMatching = Web.VerifyText("Rate of Return", this.lblRateOfReturn
				.getText().trim(), true);

		if (isTextMatching) {

			Reporter.logEvent(Status.PASS,
					" Verify 'Rate Of Return' Page is displayed",
					"User 'Rate Of Return' Page is displayed", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Rate Of Return' Page is displayed",
					"User 'Rate Of Return' Page is not displayed", true);
		}
		//Thread.sleep(5000);
		isElementDisplayed = Web.isWebElementDisplayed(this.tableHeaderFrom,
				true);

		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header FROM is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header FROM is Not displayed", false);
		}
		isElementDisplayed = Web
				.isWebElementDisplayed(this.tableHeaderTo, true);

		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header TO is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header TO is Not displayed", false);
		}
		isElementDisplayed = Web.isWebElementDisplayed(this.tableHeaderReturn,
				true);

		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header RETURN is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header RETURN is Not displayed", false);
		}
		

		String fromDate = this.tableBodyTo.getText().toString().trim();

		if (!fromDate.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'FROM DATE'  is Displayed In Table",
					"'FROM DATE' is Displayed in Table", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'FROM DATE'  is Displayed In Table",
					"'FROM DATE' is Not Displayed in Table", true);
		}
		String toDate = this.tableBodyTo.getText().toString().trim();

		if (!toDate.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'TO DATE'  is Displayed In Table",
					"'TO DATE' is Displayed in Table", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'TO DATE'  is Displayed In Table",
					"'TO DATE' is Not Displayed in Table", true);
		}

		String rateOfReturn = this.tableBodyReturn.getText().toString().trim();

		if (!rateOfReturn.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'RATE OF RETURN'  is Displayed In Table",
					"'RATE OF RETURN' is Displayed in Table", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'RATE OF RETURN'  is Displayed In Table",
					"'RATE OF RETURN' is Not Displayed in Table", true);
		}
		
		return isTextMatching;

	}
	*/
	public boolean verifyDataInRateOfReturnPage() throws InterruptedException {
		boolean isElementDisplayed = false;
		boolean isTextMatching = false;

		isTextMatching = Web.VerifyText("Rate of Return", this.lblRateOfReturn
				.getText().trim(), true);

		if (isTextMatching) {

			Reporter.logEvent(Status.PASS,
					" Verify 'Rate Of Return' Page is displayed",
					"User 'Rate Of Return' Page is displayed", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'Rate Of Return' Page is displayed",
					"User 'Rate Of Return' Page is not displayed", true);
		}
		//Thread.sleep(5000);
		WebElement tableHeaderFrom=Web.getDriver().findElement(By.xpath(tableHeader.replace("HeaderIndex", "2")));
		isElementDisplayed = Web.isWebElementDisplayed(tableHeaderFrom,true);
		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header FROM is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header FROM is Not displayed", false);
		}
		WebElement tableHeaderTo=Web.getDriver().findElement(By.xpath(tableHeader.replace("HeaderIndex", "3")));
		isElementDisplayed = Web.isWebElementDisplayed(tableHeaderTo);
		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header TO is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header TO is Not displayed", false);
		}
		WebElement tableHeaderAnnualized=Web.getDriver().findElement(By.xpath(tableHeader.replace("HeaderIndex", "4")));
		isElementDisplayed = Web.isWebElementDisplayed(tableHeaderAnnualized);
		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header ANNUALIZED is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header ANNUALIZED is Not displayed", false);
		}
		
		WebElement tableHeaderCumulative=Web.getDriver().findElement(By.xpath(tableHeader.replace("HeaderIndex", "5")));
		isElementDisplayed = Web.isWebElementDisplayed(tableHeaderCumulative);
		if (isElementDisplayed) {
			Reporter.logEvent(Status.PASS,
					"Verify 'Table Header'  is displayed",
					"Table Header CUMULATIVE is displayed", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify 'Table Header'  is displayed",
					"Table Header CUMULATIVE is Not displayed", false);
		}	
		
		//verify annualized and Cumulative tool tip
		String expectedAnnualizedToolTipText="Annualized rate of return is the average annual return over a period of years, taking into account the effect of compounding.";
		String expectedCumulativeToolTipText="Cumulative rate of return is the aggregate amount an investment has gained or lost over a given time period.";
		WebElement annualizedToolTipText=Web.getDriver().
				findElement((By.xpath(tableHeader.replace("HeaderIndex", "4").concat("/span"))));
		String actualAnnualizedToolTipText=annualizedToolTipText.getAttribute("title");		
		System.out.println("Actual Annualized \n"+actualAnnualizedToolTipText);
		
		WebElement cumulativeToolTipText=Web.getDriver().
				findElement((By.xpath(tableHeader.replace("HeaderIndex", "5").concat("/span"))));
		String actualCumulativeToolTipText=annualizedToolTipText.getAttribute("value");
		System.out.println("Actual Cumulative \n"+actualCumulativeToolTipText);
		
		
		WebElement tableContentFrom=Web.getDriver().findElement(By.xpath(tableContent.replace("ContentIndex", "2")));
		String fromDate = tableContentFrom.getText().toString().trim();

		if (!fromDate.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'FROM DATE'  is Displayed In Table",
					"'FROM DATE' is Displayed in Table as : "+fromDate, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'FROM DATE'  is Displayed In Table",
					"'FROM DATE' is Not Displayed in Table", true);
		}
		WebElement tableContentTo=Web.getDriver().findElement(By.xpath(tableContent.replace("ContentIndex", "3")));
		String toDate = tableContentTo.getText().toString().trim();

		if (!toDate.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					" Verify 'TO DATE'  is Displayed In Table",
					"'TO DATE' is Displayed in Table as "+toDate, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'TO DATE'  is Displayed In Table",
					"'TO DATE' is Not Displayed in Table", true);
		}

		WebElement tableContentAnnualizedReturn=Web.getDriver().findElement(By.xpath(tableContent.replace("ContentIndex", "4")));
		String annualizedReturn = tableContentAnnualizedReturn.getText().toString().trim();

		if (!annualizedReturn.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'ANNUALIZED RATE OF RETURN'  is Displayed In Table",
					"'ANNUALIZED RATE OF RETURN' is Displayed in Table as "+annualizedReturn, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'ANNUALIZED RATE OF RETURN'  is Displayed In Table",
					"'ANNUALIZED RATE OF RETURN' is Not Displayed in Table", true);
		}
		
		WebElement tableContentCumulativeReturn=Web.getDriver().findElement(By.xpath(tableContent.replace("ContentIndex", "5")));
		String cumulativeReturn = tableContentCumulativeReturn.getText().toString().trim();

		if (!cumulativeReturn.isEmpty()) {

			Reporter.logEvent(Status.PASS,
					" Verify 'CUMULATIVE RATE OF RETURN'  is Displayed In Table",
					"'CUMULATIVE RATE OF RETURN' is Displayed in Table as "+cumulativeReturn, true);

		} else {
			Reporter.logEvent(Status.FAIL,
					" Verify 'CUMULATIVE RATE OF RETURN'  is Displayed In Table",
					"'CUMULATIVE RATE OF RETURN' is Not Displayed in Table", true);
		}
		
		return isTextMatching;

	}
	
	//Bhargav
	
	public void verifyRORPageDisplayed(boolean sValue) throws InterruptedException
	{
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Thread.sleep(2000);
		if(sValue){
			if(Web.isWebElementDisplayed(rorPageHeader, true))
				Reporter.logEvent(Status.PASS,
						"Verify the rate of return page is displayed.",
						"Rate of return page is displayed.", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the rate of return page is displayed.",
						"Rate of return page is not displayed.", true);
		}
		else
		{
			if(!Web.isWebElementDisplayed(rorPageHeader))
				Reporter.logEvent(Status.PASS,
						"Verify the rate of return page is not displayed.",
						"Rate of return page is not displayed.", true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the rate of return page is displayed.",
						"Rate of return page is  displayed.", true);
		}
		
		
	}
	public void verifyDatesinRORPage(String[] sDates) throws InterruptedException
	{
		Common.waitForProgressBar();
		Web.waitForElement(rorPageHeader);
		Web.waitForElement(sFromDate);
		String sFromDateRor=Web.getWebElementText(sFromDate).trim();
		String sToDateRor=Web.getWebElementText(sToDate).trim();
		if(sFromDateRor.equals(sDates[0].trim()) && sToDateRor.equals(sDates[1].trim()))
			Reporter.logEvent(Status.PASS,
					"Verify the dates of rate of return shows the correct date range.",
					"Dates of rate of return shows the correct date", true);
		
		else
			Reporter.logEvent(Status.FAIL,
					"Verify the dates of rate of return shows the correct date range.",
					"Dates of rate of return not shows the correct date-  Expected FromDate:"+sDates[0]+" Actual  FromDate:"+sFromDateRor+" \n Expected ToDate:"+sDates[1]+" Actual  FromDate:"+sToDateRor, true);
		
	}
	
	public void verifyRORPercentageinRORPage(String sRorType,String sPercentage)
	{
		Web.waitForElement(rorPageHeader);
		if(sRorType.equalsIgnoreCase("annualized"))
		{
			if(Web.getWebElementText(rorAnnualizedPercent).trim().equals(sPercentage))
				Reporter.logEvent(Status.PASS,
					"Verify the rate of return on this page matches the percentage on rate of return card.",
					"Rate of return on this page matches the percentage on rate of return card.", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify the rate of return on this page matches the percentage on rate of return card.",
					"Rate of return on this page not matches the percentage on rate of return card. Actual:"+rorAnnualizedPercent.getText()+"Expected:"+sPercentage, true);
		}
		else if(sRorType.equalsIgnoreCase("cummulative"))
		{
			if(Web.getWebElementText(rorcummulativePercent).trim().equals(sPercentage))
				Reporter.logEvent(Status.PASS,
					"Verify the rate of return on this page matches the percentage on rate of return card.",
					"Rate of return on this page matches the percentage on rate of return card.", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify the rate of return on this page matches the percentage on rate of return card.",
					"Rate of return on this page not matches the percentage on rate of return card. Actual:"+rorcummulativePercent.getText()+"Expected:"+sPercentage, true);
		}
	}
	

}
