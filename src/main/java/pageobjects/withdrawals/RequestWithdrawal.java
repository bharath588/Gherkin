package pageobjects.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import appUtils.Common;
import core.framework.Globals;

public class RequestWithdrawal extends LoadableComponent<RequestWithdrawal> {

	// Declarations
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;

	@FindBy(xpath = ".//*[@id='account-details-container']//div[@class='page-title']/h1")
	private WebElement lblRequestAWithdrawal;
    @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	 @FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = ".//input[contains(@ng-click,'maxAmountCheck')]")
	private WebElement inptMaxAmount;
	@FindBy(xpath = ".//button[contains(text(),'Continue')]")
	private WebElement btnContinue;
	@FindBy(xpath= ".//label[./input[@id='inlineRadio1']]")
	private WebElement inpYes;
	@FindBy(id = "inlineRadio2")
	private WebElement inpNo;
	@FindBy(id = "ssnInput")
	private WebElement inputSSN;
	@FindBy(xpath = ".//button[contains(text(),'Confirm and continue')]")
	private WebElement btnConfirmContinue;
	@FindBy(xpath = ".//select[contains(@ng-model,'withDrawalType')]")
	private WebElement drpWithdrawalType;
	@FindBy(xpath = ".//*[@id='btn-confirm submit']")
	private WebElement btnContinueWithdrawal;
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]")
	private WebElement txtConfirmation;
	@FindBy(xpath = "//p[./i[@class='em-checkbox-icon']]//strong")
	private WebElement txtConfirmationNo;
	@FindBy(xpath = "//*[@id='inserviceradiono']")
	private WebElement inptCurrentEmpNo;
	@FindBy(xpath=".//*[text()[normalize-space()='Dismiss']]") private WebElement lnkDismiss;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	private String textField="//*[contains(text(),'webElementText')]";
	private String inputWithdrawalType = "//div[@id='test_id'][.//span[contains(text(),'Withdrawal Type')]]//input";
	private String inpMailType="//input[contains(@value,'mailType')]";
	
	/**
	 * Default Constructor
	 */
	public RequestWithdrawal() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	/**
	 * Arg Constructor
	 * 
	 * @param parent
	 */
	public RequestWithdrawal(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblRequestAWithdrawal,
				true));
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		 else {

				try {
					strUserInfo = Common.getParticipantInfoFromDataBase(ssn
							.substring(0, 9));
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
		String sponser = this.lblSponser.getAttribute("Alt");
		if (sponser.isEmpty()) {
			sponser = Common.GC_DEFAULT_SPONSER;
		}
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));
			/*Assert.assertTrue(Web
					.isWebElementDisplayed(this.lblRequestAWithdrawal));*/
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}

	}

	@Override
	protected void load() {
		
		if (Web.isWebElementDisplayed(lnkDismiss)) {
			this.lnkDismiss.click();
		}
		
		this.parent.get();

		((LeftNavigationBar) this.parent)
				.clickNavigationLink("Request a withdrawal");

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
		if (fieldName.trim().equalsIgnoreCase("REQUEST A WITHDRAWAL")) {
			return this.lblRequestAWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("MAX AMOUNT")) {
			return this.inptMaxAmount;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("YES")) {
			return this.inpYes;
		}
		if (fieldName.trim().equalsIgnoreCase("NO")) {
			return this.inpNo;
		}
		if (fieldName.trim().equalsIgnoreCase("SSN")) {
			return this.inputSSN;
		}
		if (fieldName.trim().equalsIgnoreCase("CONFIRM AND CONTINUE")) {
			return this.btnConfirmContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("WITHDRAWAL METHOD")) {
			return this.drpWithdrawalType;
		}
		if (fieldName.trim().equalsIgnoreCase("CONTINUE TO WITHDRAWAL")) {
			return this.btnContinueWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("I AGREE AND SUBMIT")) {
			return this.btnContinueWithdrawal;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION")) {
			return this.txtConfirmation;
		}
		if (fieldName.trim().equalsIgnoreCase("TEXT CONFIRMATION NUMBER")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT CURRENT EMPLOYER NO")) {
			return this.inptCurrentEmpNo;
		}
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
	}

	/**
	 * Method to Select WithDrawal Type 
	 * @param withdrawalType
	 * 
	 */
	public boolean selectWithdrawalType(String withdrawalType) {
		boolean isSelected=false;
		try {
			
		WebElement inptWithdrawalType = Web.webdriver.findElement(By
				.xpath(inputWithdrawalType.replace("Withdrawal Type",
						withdrawalType)));
		if(Web.isWebElementDisplayed(inptWithdrawalType, true))
		{
		inptWithdrawalType.click();
		isSelected=true;
		}
			
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return isSelected;
	}

	/**
	 * <pre>
	 * Method to get the text of an webElement
	 * Returns string webElement is displayed
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementText(String fieldName) {
		String getText = "";

		if (Web.isWebElementDisplayed(this.getWebElement(fieldName))) {

			getText = this.getWebElement(fieldName).getText().trim();

		}

		return getText;

	}
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.webdriver.findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
					+ "  is Displayed", fieldName + " is Displayed",
					false);

		} else {
			lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
					+ " is Displayed",
					fieldName + " is Not Displayed", false);
		}
	
return isTextDisplayed;
	}
	
	/**
	 * Method to Select Delivery Method 
	 * @param deliveryMethod
	 * 
	 */
	public void selectDelivaryMethod(String deliveryMethod) {
		WebElement inptDeliveryMethod = Web.webdriver.findElement(By
				.xpath(inpMailType.replace("mailType",
						deliveryMethod)));
		inptDeliveryMethod.click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void enterSSN(String ssn) {
		
		try {
			if(Web.isWebElementDisplayed(this.inputSSN, true))
			{
				Web.setTextToTextBox(inputSSN, ssn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
