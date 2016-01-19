package pageobjects;

import java.util.List;

import lib.Reporter;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

import lib.Reporter.Status;
import lib.Stock;

public class ParticipantHome extends LoadableComponent<ParticipantHome> {

	// CSAS Login..

	@FindBy(xpath = "//span[contains(text(),'CLIENT SERVICE ACCESS SYSTEM USER LOGON')]")
	private WebElement CSASLoginHome;

	@FindBy(css = "input[name = 'username']")
	private WebElement CSASUserNameField;

	@FindBy(css = "input[name = 'password']")
	private WebElement CSASPwdField;

	@FindBy(css = "input[value='Log In']")
	private WebElement CSASLoginBtn;

	// Participant Plan Search..

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT/PLAN SEARCH')]")
	private WebElement participantPlanSearchPage;

	@FindBy(css = "input[name = 'searchPartId']")
	private WebElement PPTIdfield;

	@FindBy(id = "submitPpt")
	private WebElement SubmitPPTIdBtn;

	@FindBy(xpath = "//span[contains(text(),'PARTICIPANT HOME PAGE')]")
	private WebElement PPTHomePageTitle;

	// Menu items..

	@FindBy(css = "div#oCMenu_315")
	private WebElement menuParticipantInfo;

	@FindBy(css = "div#oCMenu_316")
	private WebElement menuParticipantChanges;

	@FindBy(css = "div#oCMenu_317")
	private WebElement menuPlanInfo;

	@FindBy(css = "div#oCMenu_318")
	private WebElement menuAddtlResources;

	@FindBy(css = "div#oCMenu_319")
	private WebElement menuSearch;

	@FindBy(css = "div#oCMenu_320")
	private WebElement menuContactReason;

	@FindBy(css = "div#oCMenu_15000")
	private WebElement menuIRA;

	@FindBy(css = "div#oCMenu_20519")
	private WebElement menuPlanSetup;

	@FindBy(css = "div#oCMenu_5555")
	private WebElement menuTestPages;

	@FindBy(css = "oCMenu_15483")
	private WebElement menuAdmin;

	@FindBy(name = "username")
	private WebElement userNameField;

	@FindBy(name = "password")
	private WebElement passWordField;

	// Account Balance..

	@FindBy(css = "td.colTitle.balance")
	private WebElement tdParticipantBalance;

	@FindBy(css = "td.data.balance>a.floatingDiv")
	private WebElement lnkHoverablePlanBalance;

	@FindBy(css = "div#overDiv[style *= 'visible']>table")
	private WebElement lnkHoverablePlanBalanceAfterHover;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Vested Balance')]")
	private WebElement tbVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Non-Vested Balance')]")
	private WebElement tbNonVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Current Balance')]")
	private WebElement tbCurrentBal;

	// Required List implementaions..

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(2)>td")
	private WebElement tbTotalVarBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(3)>td")
	private WebElement tbTotalFixBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(4)>td")
	private WebElement tbTotalExcludingLoans;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(5)>td")
	private WebElement tbTotalLoanBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(6)>td")
	private WebElement tbTotalIncludingLoan;

	@FindBy(xpath = "//td[contains(text(),'Total Balance:')]")
	private WebElement participantTotalBalLabel;

	@FindBy(css = "td.total.balance")
	private WebElement participantTotalBalVal;

	// Personal Data...
	// Required list implementation..

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Name:')]")
	private WebElement participantNameLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(3)>td.data")
	private WebElement participantName;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SSN')]")
	private WebElement participantSSNLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(4)>td.data")
	private WebElement participantSSN;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Date Of Birth')]")
	private WebElement participantDOBLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(5)>td.data")
	private WebElement participantDOB;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Gender')]")
	private WebElement participantGenderLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(6)>td.data")
	private WebElement participantGender;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Address')]")
	private WebElement participantAddLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(7)>td.data")
	private WebElement participantAdd;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Mail Hold')]")
	private WebElement participantMailLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(8)>td.data")
	private WebElement participantMail;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Cash Hold Status')]")
	private WebElement participantCHSLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(9)>td.data")
	private WebElement participantCHS;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Web Registration Status')]")
	private WebElement participantRegStatusLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(11)>td.data")
	private WebElement participantRegStatus;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Managed Account Status')]")
	private WebElement participantMngAccStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(12)>td.data")
	private WebElement participantMngAccSts;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SecureFoundation Status')]")
	private WebElement participantSecFoundationStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(13)>td.data")
	private WebElement participantSecFoundationSts;

	// Order PIN..

	@FindBy(css = "table.compactDataTable tr:nth-of-type(7) a:nth-of-type(2)")
	private WebElement lnkOrderPIN;

	@FindBy(css = "table#table_popupLayout tr:nth-of-type(2) span.titleShadow")
	private WebElement OrderPINTitle;

	@FindBy(css = "input[value = 'Mail Existing PIN']")
	private WebElement btnOPMailExistingPIN;

	@FindBy(css = "input[value = 'Order Temp PIN']")
	private WebElement btnOPOrderTempPin;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(8)")
	private WebElement EmploymentStatusLabel;

	// implement List...
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(8)>a")
	private WebElement lnkEmploymentStatus;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(9)")
	private WebElement PDILabel;

	// implement List...
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(9)")
	private WebElement PDIStatus;

	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(10)")
	private WebElement InstanceLabel;

	// implement List...
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(10)")
	private WebElement InstanceStatus;

	LoadableComponent<?> parent;
	/*-----------------------------------------------------------------*/

	private List<String> getFooterLinkList = null;

	public ParticipantHome() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertEquals(Web.webdriver.getTitle(), "CSAS v12.03.2");
	}

	@Override
	protected void load() {
		this.parent = parent;
		Web.webdriver.get(Stock.globalParam.get("AppURL"));
	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void submitLoginCredentials(String username, String password) {

		boolean isElementDisplayed = false;
		try {
			Thread.sleep(5000);
			System.out.println(username+"   "+password);
			Web.setTextToTextBox(CSASUserNameField, username);
			Thread.sleep(2000);
			Web.setTextToTextBox(CSASPwdField, password);
			Web.clickOnElement(CSASLoginBtn);

			Web.waitForElement(participantPlanSearchPage);
			isElementDisplayed = Web.isWebElementDisplayed(
					this.participantPlanSearchPage, true);

			if (isElementDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Check if the user logged in to Participant/plan search page",
						"User is successfully logged in to Participant/plan search page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if the user logged in to Participant/plan search page",
						"User is not logged in to Participant/plan search page",
						false);
			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to enter participant ID and click on Sign In button
	 * 
	 * @param ppt_id
	 * @throws Exception
	 */
	public void search_PPT_Plan_With_PPT_ID(String ppt_id) {

		boolean isElementDisplayed = false;

		Web.setTextToTextBox(this.PPTIdfield, ppt_id);
		Web.clickOnElement(this.SubmitPPTIdBtn);
		try {
			Web.waitForElement(this.PPTHomePageTitle);
			isElementDisplayed = Web.isWebElementDisplayed(
					this.PPTHomePageTitle, true);

			if (isElementDisplayed) {
				Reporter.logEvent(
						Status.PASS,
						"Participant Home Page with all the details should display",
						"Participant Home Page with all the details is displayed successfully",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Participant Home Page with all the details should not display",
						"Participant Home Page with all the details is not displayed",
						false);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
