package pptweb.withdrawals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pptweb.appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RequestWithdrawal_AF  extends LoadableComponent<RequestWithdrawal_AF> {
	
	private LoadableComponent<?> parent;
	private static boolean waitforLoad = false;
	public static List<DesignateBeneficiaries> lstBenfDetails=new LinkedList<DesignateBeneficiaries>();
	DesignateBeneficiaries designateBeneficiaries;
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalType')]")
	private WebElement drpWithdrawalType;
	@FindBy(xpath = ".//select[contains(@ng-model,'withdrawalReason')]")
	private WebElement drpFullWithdrawalType;
	@FindBy(xpath=".//input[contains(@ng-model,'homePhoneNbr')]")
	private WebElement txtHomePhoneNum;
	@FindBy(xpath=".//input[contains(@ng-model,'mobilePhoneNbr')]")
	private WebElement txtMobPhoneNum;
	@FindBy(xpath=".//input[contains(@ng-model,'email')]")
	private WebElement txtEmailAddress;
	@FindBy(id="yesChoice")
	private WebElement radioAlreadyHaveAFAccountYes;
	@FindBy(id="noChoice")
	private WebElement radioAlreadyHaveAFAccountNo;
	@FindBy(id="traditionalAccNbr")
	private WebElement txtTraditionalAccountNum;
	@FindBy(id="rothAccNbr")
	private WebElement txtRothAccountNum;
	@FindBy(xpath=".//div[contains(@class,'has-error help-text')]")
	private WebElement txtHelpErrorMessage;	
	//@FindBy(xpath=".//button[normalize-space(.='Continue')]")
	@FindBy(xpath=".//button[ ./span[contains(text(),'Continue')] or contains(text(),'Continue') ]")
	private WebElement btnContinue;
	@FindBy(id="text-NaN")
	private List<WebElement> txtInvestmentAllocationPercentage;
	@FindBy(xpath=".//b[contains(text(),'Select my own')]")
	private WebElement radioSelectOwnInvestments;
	@FindBy(xpath=".//label[contains(@ng-class,'.pptStatus')]")
	private WebElement btnBenf_MarriedNo;
	@FindBy(xpath=".//label[contains(@ng-class,'.married')]")
	private WebElement btnBenf_MarriedYes;
	@FindBy(xpath=".//input[@ng-if='isSpousePrimary' and @value='Spouse']")
	private WebElement txtPrmBenf1Type;
	@FindBy(xpath=".//label[contains(@ng-class,'yes')]")
	private WebElement btnAggregateAccountsYes;
	@FindBy(xpath=".//label[contains(@ng-class,'no')]")
	private WebElement btnAggregateAccountsNo;
	@FindBy(id="deliveryOptionsUSPS")
	private WebElement radioWireTransferToAF;
	@FindBy(xpath=".//label[@class='af-radio-button']/input")
	private List<WebElement> designateBeneficiaryRadioButtons;
	@FindBy(xpath=".//button[contains(text(),'Back') ]")
	private WebElement btnBack;	
	@FindBy(xpath=".//input[contains(@ng-click,'designateNow')]") private WebElement radioDesignateNow;	
	@FindBy(xpath=".//*[@id='primaryBene1']//input[contains(@ng-model,'useCurrent')]")
	private WebElement chkPrm1CurrentAddress; 
	@FindBy(xpath=".//*[@id='primaryBene2']//input[contains(@ng-model,'useCurrent')]")
	private WebElement chkPrm2CurrentAddress;
	@FindBy(xpath=".//*[@id='contingentBene1']//input[contains(@ng-model,'useCurrent')]")
	private WebElement chkCont1CurrentAddress;
	@FindBy(xpath=".//*[@id='contingentBene2']//input[contains(@ng-model,'useCurrent')]")private WebElement chkCont2CurrentAddress;
	@FindBy(id="ssnInput1") private WebElement txtSSNInput1;
	@FindBy(id="ssnInput2") private WebElement txtSSNInput2;
	@FindBy(id="ssnInput3") private WebElement txtSSNInput3;
	@FindBy(xpath=".//input[@value='traditionalIRAFunds']") private WebElement radioFundsSameAsTraditionalIRA;
	
	//Mosin
	@FindBy(linkText="IRA Custodial Agreement default") private WebElement lnkIRA;
@FindBy(linkText = "Research investment options")
	private WebElement lnkResearchInvestmentOptions;
	@FindBy(linkText="primary beneficiary") private WebElement lnkPrimaryBeneficiary;
	@FindBy(linkText="contingent beneficiary(ies)") private WebElement lnkContingentBeneficiary;
	@FindBy(xpath="//h1[text()='Rights of accumulation (cumulative discount) for your American Funds IRA']") 
	private WebElement lblROAPage;
	//@FindBy(id = "Non-Roth_input")
	@FindBy(id = "Pre-tax_input")
	private WebElement txtNonRothInput;
	@FindBy(id = "Pre-tax_input")
	private WebElement txtPreTaxInput;
	@FindBy(id = "Roth_input")
	private WebElement txtRothInput;
	@FindBy(xpath = "(//*[contains(text(),'Date of birth must follow MM/DD/YYYY format.')])[2]")
	private WebElement dobErrorMsg;
	@FindBy(xpath = "(//*[contains(text(),'Social Security/Tax ID number must be 9 digits.')])[2]")
	private WebElement SSNErrorMsg;
	@FindBy(xpath = ".//input[@value='invest']")
	private WebElement radioSelectMyOwnInvestment;
	@FindBy(xpath = "//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Roth']]]//input[@type='text']")
	private WebElement txtSepRothTextBox;
	//@FindBy(xpath = "//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Non-Roth']]]//input[@type='text']")
	@FindBy(xpath = "//div[contains(@ng-repeat,'withdrawalMoneySources')][.//div[text()[normalize-space()='Pre-tax']]]//input[@type='text']")
	private WebElement txtSepNonRothTextBox;
	@FindBy(xpath = ".//input[@value='advisor']")
	private WebElement radioSelectMyFinancialAdvisor;
	private String lblerrorMessage = "//*[contains(text(),'webElementText')]";
	
	private String btnAdvisoryOptionYesOrNo=".//Strong[.='advisoryOption']";
	private String radioInvestOptions=".//input[@value='investOption']";
	private String textField="//*[contains(text(),'webElementText')]";
	private String radioDesignateBenficiary = ".//input[contains(@ng-click,'beneficiaryOption')]";
	private String btnExchangeOverPhone="//label[contains(@ng-class,'Exchange')][contains(@ng-class,'privilege')]";
	private String btnRedemptionOverPhone="//label[contains(@ng-class,'Redemption')][contains(@ng-class,'privilege')]";
	private String btnaggregateAccts=".//label[contains(@ng-class,'aggregate')]";	
	private String benfMarriedOrNot=".//label[contains(@ng-class,'.pptStatus')]";
	private String prmBenf1PersonalDetails=".//*[@id='primaryBene1']//input[@id='Benf Fields']";
	private String prmBenf2PersonalDetails =".//*[@id='primaryBene2']//input[@id='Benf Fields']";
	private String contBenf1PersonalDetails=".//*[@id='contingentBene1']//input[@id='Benf Fields']";
	private String contBenf2PersonalDetails=".//*[@id='contingentBene2']//input[@id='Benf Fields']";
	
	private String prmBenf1TypeOrState=".//*[@id='primaryBene1']//select [@id='Benf Fields']";
	private String prmBenf2TypeOrState=".//*[@id='primaryBene2']//select[@id='Benf Fields']";
	private String contBenf1TypeOrState=".//*[@id='contingentBene1']//select[@id='Benf Fields']";
	private String contBenf2TypeOrState=".//*[@id='contingentBene2']//select[@id='Benf Fields']";

	//error message
	private String errorMessage="//*[@id='primaryBene1']//label[contains(text(),'fieldName')]//following-sibling::ng-messages//ng-message";

	@FindBy(xpath = ".//label[contains(@ng-class,'yes')]")
	private WebElement aggregateYes;
	
	@FindBy(xpath = ".//label[contains(@ng-class,'no')]")
	private WebElement aggregateNo;
	
	@FindBy(xpath = "//label[contains(@ng-class,'Exchange')][contains(@ng-class,'N')]")
	private WebElement exchangeNo;
	
	@FindBy(xpath = "//label[contains(@ng-class,'Exchange')][contains(@ng-class,'Y')]")
	private WebElement exchangeYes;
	
	@FindBy(xpath = "//label[contains(@ng-class,'Redemption')][contains(@ng-class,'Y')]")
	private WebElement redemptionYes;
	
	@FindBy(xpath = "//label[contains(@ng-class,'Redemption')][contains(@ng-class,'N')]")
	private WebElement redemptionNo;
	
	@FindBy(xpath = "//div[text()='Account number or SSN must be 7-11 digits']")
	private WebElement aggregateError;
	
	/**
	 * Default Constructor
	 */
	
	public RequestWithdrawal_AF() {
		
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Log out
		if (fieldName.trim().equalsIgnoreCase("CONTINUE BUTTON"))
		{
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("BACK BUTTON"))
		{
			return this.btnBack;
		}
		if (fieldName.trim().equalsIgnoreCase("DESIGNATE BENF"))
		{
			return this.radioDesignateNow;
		}
		if (fieldName.trim().equalsIgnoreCase("PPT MARRIED"))
		{
			return this.btnBenf_MarriedYes;
		}
		if (fieldName.trim().equalsIgnoreCase("PRIMARY BENF1 TYPE"))
		{
			return this.txtPrmBenf1Type;
		}
		if (fieldName.trim().equalsIgnoreCase("FUNDS SAME AS TRAIDTIONAL IRA"))
		{
			return this.radioFundsSameAsTraditionalIRA;
		}
		if (fieldName.trim().equalsIgnoreCase("SELECT MY OWN INVESTMENTS")) {
			return this.radioSelectMyOwnInvestment;
		}
		if (fieldName.trim().equalsIgnoreCase("Select Financial Advisor")) {
			return this.radioSelectMyFinancialAdvisor;
		}
		if (fieldName.trim().equalsIgnoreCase("IRA CUSTODIAL LINK"))
		{
			return this.lnkIRA;
		}
		if (fieldName.trim().equalsIgnoreCase("Research Investment Options")) {
			return this.lnkResearchInvestmentOptions;
		}
		if (fieldName.trim().equalsIgnoreCase("Primary Beneficiary Link"))
		{
			return this.lnkPrimaryBeneficiary;
		}
		if (fieldName.trim().equalsIgnoreCase("Contingent Beneficiary Link"))
		{
			return this.lnkContingentBeneficiary;
		}
		if (fieldName.trim().equalsIgnoreCase("Verify ROA Page"))
		{
			return this.lblROAPage;
		}
		if (fieldName.trim().equalsIgnoreCase("Click on checkbox")) {
			return this.chkPrm1CurrentAddress;
		}
		if (fieldName.trim().equalsIgnoreCase("Non Roth Input TextBox")) {
			return this.txtNonRothInput;
		}
		if (fieldName.trim().equalsIgnoreCase("Pre Tax Input TextBox")) {
			return this.txtPreTaxInput;
		}
		if (fieldName.trim().equalsIgnoreCase("Roth Input TextBox")) {
			return this.txtRothInput;
		}
		if (fieldName.trim().equalsIgnoreCase("Sep Roth Input TextBox")) {
			return this.txtSepRothTextBox;
		}
		if (fieldName.trim().equalsIgnoreCase("Sep Non Roth Input TextBox")) {
			return this.txtSepNonRothTextBox;
		}
		if (fieldName.trim().equalsIgnoreCase("DOD Error Message")) {
			return this.dobErrorMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("SSN Error Message")) {
			return this.SSNErrorMsg;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);
		return null;
	}
	
	
	@SuppressWarnings("unused")
	private List<WebElement> getWebElements(String fieldName) {
		// Log out
		if (fieldName.trim().equalsIgnoreCase("DESIGNATE RADIO BUTTON"))
		{
			return this.designateBeneficiaryRadioButtons;
		}
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '"
				+ fieldName + "'",
				"No WebElement mapped for this field\nPage: <b>"
						+ this.getClass().getName() + "</b>", false);

		return null;
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
	
	public boolean isWebElementEnabled(String fieldName) {
			
			if(this.getWebElement(fieldName).isEnabled())
				return true;
			else
				return false;

	}
	
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			throw new Error(fieldName+" is not displayed");
		}
	
		return isTextDisplayed;
	}

	public void clickContinueButton()
	{
		
		Web.waitForElement(btnContinue);
		if(Web.isWebElementDisplayed(btnContinue,true))			
			Web.clickOnElement(btnContinue);
		Web.waitForPageToLoad(Web.getDriver());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public void verifyWithdrawalMethodPage_AF(String withdrawalType,String withdrawalMethodType,String emailAddress)
	{
		try {
			Thread.sleep(1000);
			if (isTextFieldDisplayed("Withdrawal method")) {
				isTextFieldDisplayed("How would you like your withdrawal distributed?");

				Web.selectDropDownOption(drpWithdrawalType,withdrawalMethodType, true);
				isTextFieldDisplayed("Delivery information for mail or expedited delivery");
				if (Web.isWebElementDisplayed(txtHomePhoneNum))
					Web.setTextToTextBox(txtHomePhoneNum, "1234567891");
				Web.setTextToTextBox(txtEmailAddress, emailAddress);
				txtEmailAddress.sendKeys(Keys.ENTER);

			}
		}
		catch(NoSuchElementException e)		
		{ 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "Element is not displayed in the Application",
					msg, true); }
		catch(Exception e)
		{ Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();
		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
				msg, true);}
	}
	
	public void verifyResearchInvestmentLink() throws InterruptedException {
		

		if (Web.isWebElementDisplayed(lnkResearchInvestmentOptions)) {
			Reporter.logEvent(Status.PASS,
					"Verify the designate a beneficiary Page",
					"Research Investment Options default link is displayed",
					false);
			String tag=lnkResearchInvestmentOptions.getTagName();
			
			if(tag.equalsIgnoreCase("a"))
				Reporter.logEvent(Status.PASS,
						"Research Investment Options tag Validation",
						"Research Investment Options is a hyperlink ", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Research Investment Options tag Validation",
						"Research Investment Options is not hyperlink", true);
			
			Web.clickOnElement(lnkResearchInvestmentOptions);
			String getParentWindow = Web.getDriver().getWindowHandle();
			for (String windowHandles : Web.getDriver().getWindowHandles()) {
				Web.getDriver().switchTo().window(windowHandles);
				// Web.waitForPageToLoad(Web.getDriver());
			}
			System.out.println(Web.getDriver().getCurrentUrl());
			if (Web.getDriver()
					.getCurrentUrl()
					.equalsIgnoreCase(
							Stock.GetParameterValue("Research Investment Options Url")))
				Reporter.logEvent(Status.PASS,
						"Research Investment Options Url",
						"Research Investment Options Url is matchng ", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Research Investment Options Url",
						"Research Investment Options Url is not matchng", true);
			Web.getDriver().close();
			Web.getDriver().switchTo().window(getParentWindow);
			Web.getDriver().switchTo().defaultContent();
			System.out.println(Web.getDriver().getCurrentUrl());
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify the designate a beneficiary Page",
					"Research Investment Options Url link is not displayed",
					true);
	}

	public void validateToolTip(String element, String toolTipText)
			throws InterruptedException {
		WebElement ele = this.getWebElement(element);

		Actions keyBoardEvent = new Actions(Web.getDriver());
		keyBoardEvent.moveToElement(ele).build().perform();
		Thread.sleep(3000);
		WebElement ele1 = Web.getDriver().findElement(
				By.xpath("//div[@class='top fade popover in']"));
		String sExpectedText = ele1.getAttribute("content");
		System.out.println(sExpectedText);
		if (sExpectedText.equals(toolTipText)) {
			Reporter.logEvent(Status.PASS, "Verify ToolTip Text",
					"Tooltip text matched for " + element, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify ToolTip Text",
					"Tooltip text didnot matched " + element, true);

		}
	}

	public void validateInvestmentPage() throws InterruptedException {
		boolean sort = false;
		List<String> lstSorted = new ArrayList<>();
		Thread.sleep(2000);
		// isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
		List<WebElement> lstInvenstments = Web.getDriver().findElements(
				By.xpath("//div[@class='col-sm-7 allocation-fund-margin']"));
		List<WebElement> lstScrollBars = Web.getDriver().findElements(
				By.xpath("//div[@class='allocation-range-slider-wrapper']"));

		if (lstInvenstments.size() == lstScrollBars.size())
			Reporter.logEvent(
					Status.PASS,
					"verify scroll bars for the Investments",
					"Investment scroll bars are present for all the Investment options",
					false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify scroll bars for the Investments",
					"Investment scroll bars are not present for all the Investment options",
					true);

		for (WebElement match : lstInvenstments) {

			lstSorted.add(match.getText());
		}

		Collections.sort(lstSorted);

		for (int i = 0; i < lstInvenstments.size(); i++) {

			if (lstInvenstments.get(i).getText()
					.equalsIgnoreCase(lstSorted.get(i))) {
				sort = true;
			}
		}
		if (sort)
			Reporter.logEvent(Status.PASS,
					"verify if Investments types are sorted",
					"Investment types are sorted", false);
		else
			Reporter.logEvent(Status.FAIL,
					"verify if Investments types are sorted",
					"Investment types are not sorted", true);

		if (!isWebElementEnabled("CONTINUE BUTTON")) {
			Reporter.logEvent(Status.PASS,
					"Verify the 'Continue' button is disabled by default",
					"The Continue button is disabled by default", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify the 'Continue' button is disabled by default",
					"The Continue button is enabled by default", true);
		}
	}

	public void isErrorMessageDisplayed(String errorMessage) {

		WebElement txterrorMessage = Web.getDriver().findElement(
				By.xpath(lblerrorMessage
						.replace("webElementText", errorMessage)));

		if (Web.isWebElementDisplayed(txterrorMessage, true))
			Reporter.logEvent(Status.PASS, "verify " + errorMessage
					+ " Error Message is Displayed", errorMessage
					+ " Error Message is Displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "verify " + errorMessage
					+ " Error Message is Displayed", errorMessage
					+ " Error Message is not Displayed", true);

	}

	
	public void verifyDoYouAlreadyHaveIRA_AccountPage(String doYouAlreadyHaveAF_IRA,String... advisoryOption)
	{
		try {
			String accountNumberErrMsg = "Account number must be 7-11 digits.";
			/*
			 * if(withdrawalMethodValue.endsWith("Traditional or Roth IRA")) {
			 * 
			 * }
			 */
			//System.out.println(Stock.GetParameterValue("withdrawalMethod"));
			/*if(Stock.GetParameterValue("withdrawalMethod").equalsIgnoreCase("ROLLOVER TO AMERICAN FUNDS ROTH IRA"))
			{
				System.out.println("Inside If");
				isTextFieldDisplayed("Do you already have an American Funds Roth IRA?");
			}
			else*/
				isTextFieldDisplayed("Do you already have an American Funds");

			if (doYouAlreadyHaveAF_IRA.equalsIgnoreCase("Yes")) {

				Web.clickOnElement(radioAlreadyHaveAFAccountYes);
				if (Web.isWebElementDisplayed(txtTraditionalAccountNum)) {
					Web.setTextToTextBox(txtTraditionalAccountNum, "1234");
					txtTraditionalAccountNum.sendKeys(Keys.ENTER);
					if (accountNumberErrMsg
							.equalsIgnoreCase(txtHelpErrorMessage.getText()))
						Reporter.logEvent(
								Status.PASS,
								"Verify System displays Error Message on entering inaccurate account number",
								"System displays the following error message \n"
										+ txtHelpErrorMessage.getText(), true);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify System displays Error Message on entering inaccurate account number",
								"System displays the following error message \n"
										+ txtHelpErrorMessage.getText(), true);
					Web.setTextToTextBox(txtTraditionalAccountNum, "1234567");
					txtTraditionalAccountNum.sendKeys(Keys.ENTER);
				}

				else if (Web.isWebElementDisplayed(txtRothAccountNum)) {
					Web.setTextToTextBox(txtRothAccountNum, "1234");
					txtRothAccountNum.sendKeys(Keys.ENTER);
					if (accountNumberErrMsg
							.equalsIgnoreCase(txtHelpErrorMessage.getText()))
						Reporter.logEvent(
								Status.PASS,
								"Verify System displays Error Message on entering inaccurate account number",
								"System displays the following error message \n"
										+ txtHelpErrorMessage.getText(), true);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify System displays Error Message on entering inaccurate account number",
								"System displays the following error message \n"
										+ txtHelpErrorMessage.getText(), true);
					Web.setTextToTextBox(txtRothAccountNum, "1234567");
					txtRothAccountNum.sendKeys(Keys.ENTER);
				}
			} else if (doYouAlreadyHaveAF_IRA.equalsIgnoreCase("No")) {
				Web.clickOnElement(radioAlreadyHaveAFAccountNo);
				// isTextFieldDisplayed("Do you have a financial advisor you're working with to establish this IRA?");
				WebElement btnAdvisoryOption = Web.getDriver().findElement(
						By.xpath(btnAdvisoryOptionYesOrNo.replace(
								"advisoryOption", advisoryOption[0])));
				Web.clickOnElement(btnAdvisoryOption);
				
		}	
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void selectInvestmentOption(int[] allocationPercentage) {
		try {
			if (Web.isWebElementDisplayed(radioSelectOwnInvestments))
				Web.clickOnElement(radioSelectOwnInvestments);
			Thread.sleep(1000);
			int lstFunds = txtInvestmentAllocationPercentage.size();
			int total = 0;
			if (lstFunds >= allocationPercentage.length) {
				for (int i = 0; i < allocationPercentage.length; i++) {
					total += allocationPercentage[i];
					Web.setTextToTextBox(
							txtInvestmentAllocationPercentage.get(i),
							Integer.toString(allocationPercentage[i]));
					Reporter.logEvent(
							Status.PASS,
							"Select the investment fund and the enter the allocation percentage",
							"The allocation percentage has been entered for the investment fund: "
									+ allocationPercentage[i], false);
				}
			}

			if (total < 100) {
				if (!isWebElementEnabled("CONTINUE BUTTON")) {
					Reporter.logEvent(
							Status.PASS,
							"Verify the 'Continue' button",
							"Total allocation is: "
									+ total
									+ ". Continue button is disabled since the allocation is < 100%",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify the 'Continue' button",
							"Total allocation is: "
									+ total
									+ ". Continue button should be disabled when the allocation is <100%, But it is Enable here",
							true);
				}
			} else if (total == 100) {
				if (isWebElementEnabled("CONTINUE BUTTON")) {
					Reporter.logEvent(
							Status.PASS,
							"Verify the 'Continue' button",
							"Total allocation is: "
									+ total
									+ ". Continue button is enabled since the allocation is > 100%",
							true);

				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify the 'Continue' button",
							"Total allocation is: "
									+ total
									+ ". Continue button should be enabled when the allocation is 100%, But it is disabled here",
							true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void designateBeneficiary(String designateBeneficary, String... isEmpMarried)
	{
		if(Stock.GetParameterValue("doYouAlreadyHaveAF").equalsIgnoreCase("no"))
			isTextFieldDisplayed("Would you like to designate your American Funds beneficiary?");
		else
			isTextFieldDisplayed("How would you like to designate a beneficiary for your American Funds IRA?");
		
		if(designateBeneficary.equalsIgnoreCase("No"))
		{
			WebElement radioDoNotAddBenf=Web.getDriver().findElement
					(By.xpath(radioDesignateBenficiary.replace("beneficiaryOption","later")));
			Web.clickOnElement(radioDoNotAddBenf);
			Reporter.logEvent(Status.PASS, "Verify if user has selected on how he would like to desingate a beneficary for American Funds",
					"The User has selected \"I do not wish to change my beneficiaries on my IRA at this time\"",false);		

		}else if(designateBeneficary.equalsIgnoreCase("Yes"))
		{
			Web.clickOnElement(radioDesignateNow);	
			Reporter.logEvent(Status.PASS, "Verify if user has selected on how he would like to desingate a beneficary for American Funds",
					"The User liked to designate his beneficiaries",false);	
			if(isEmpMarried.length>0)
			{
			WebElement isEmpMarriedOrNot=Web.getDriver().
					findElement(By.xpath(benfMarriedOrNot.replace("pptStatus",isEmpMarried[0])));
			Web.clickOnElement(isEmpMarriedOrNot);
			}
		}
		else 
		{
			WebElement radioElectAmericanFundsOption=Web.getDriver().findElement
					(By.xpath(radioDesignateBenficiary.replace("beneficiaryOption","default")));
			Web.clickOnElement(radioElectAmericanFundsOption);
			Reporter.logEvent(Status.PASS, "Verify if user has selected on how he would like to desingate a beneficary for American Funds",
							"The User has elected the American funds IRA Custodial Agreement by default",false);			
		} 
		
	}

	
	public void validateErrorMessages(String FirstName, String MiddleName,
			String LastName, String DOB, String SSN,
			String allocationPercentage, String Address1, String City,
			String State, String Zip, String useCurrentAddress, String... type) {
		System.out.println("");
		WebElement txtPrmBenf1FirstName = Web.getDriver().findElement(
				By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
						"beneficiaryFirstName1")));
		WebElement txtPrmBenf1LastName = Web.getDriver().findElement(
				By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
						"beneficiaryLastName1")));
		WebElement txtPrmBenf1MiddleName = Web.getDriver().findElement(
				By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
						"beneficiaryMiddleName1")));
		WebElement txtPrmBenf1DOB = Web.getDriver().findElement(
				By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
						"dateOfBirth1")));
		WebElement txtPrmBenf1SSN = Web.getDriver().findElement(
				By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
						"socialSecurityNumber1")));

		//mosin
		WebElement txtPrmBenf1maskedDOB=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "dateOfBirth1Masked")));
		WebElement txtPrmBenf1maskedSSN=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "socialSecurityNumber1Masked")));
		designateBeneficiaries = new DesignateBeneficiaries();

		designateBeneficiaries.setBenfCategory("Primary1");

		designateBeneficiaries.setFirstName(FirstName);
		Web.setTextToTextBox(txtPrmBenf1FirstName,
				designateBeneficiaries.getFirstName());

		designateBeneficiaries.setMiddleName(MiddleName);
		Web.setTextToTextBox(txtPrmBenf1MiddleName,
				designateBeneficiaries.getMiddleName());

		designateBeneficiaries.setLastName(LastName);
		Web.setTextToTextBox(txtPrmBenf1LastName,
				designateBeneficiaries.getLastName());

		designateBeneficiaries.setdOB(DOB);
		try{
			Web.setTextToTextBox(txtPrmBenf1DOB, designateBeneficiaries.getdOB());	
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf1maskedDOB.clear();
			
			Web.setTextToTextBox(txtPrmBenf1DOB, designateBeneficiaries.getdOB());
		}
		designateBeneficiaries.setSsn(SSN);
		try{
			Web.setTextToTextBox(txtPrmBenf1SSN, designateBeneficiaries.getSsn());	
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf1maskedSSN.clear();
			Web.setTextToTextBox(txtPrmBenf1SSN, designateBeneficiaries.getSsn());
			
		}

		if (Stock.GetParameterValue("empMarried").equalsIgnoreCase("unmarried")
				&& type.length >= 0) {
			WebElement drpPrmBenf1Type = Web.getDriver().findElement(
					By.xpath(prmBenf1TypeOrState.replace("Benf Fields",
							"beneficiaryType1")));
			designateBeneficiaries.setType(type[0]);
			Web.selectDropDownOption(drpPrmBenf1Type,
					designateBeneficiaries.getType());
		} else
			designateBeneficiaries.setType(txtPrmBenf1Type
					.getAttribute("value"));
		if (Stock.GetParameterValue("empMarried").equalsIgnoreCase("married")) {
			WebElement txtPrmBenf1Allocation = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"allocation2")));

		} else if (Stock.GetParameterValue("empMarried").equalsIgnoreCase(
				"unmarried")) {
			WebElement txtPrmBenf1Allocation = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"allocation1")));

			Web.setTextToTextBox(txtPrmBenf1Allocation, allocationPercentage);
		}

		if (useCurrentAddress.equalsIgnoreCase("No")) {

			WebElement txtPrmBenf1Addr1 = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"beneficiaryAddress11")));
			WebElement txtPrmBenf1Addr2 = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"beneficiaryAddress21")));
			WebElement txtPrmBenf1CityOrTown = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"beneficiaryCityOrTown1")));
			WebElement drpPrmBenf1State = Web.getDriver().findElement(
					By.xpath(prmBenf1TypeOrState.replace("Benf Fields",
							"beneficiarystate1")));
			WebElement txtPrmBenf1ZipCode = Web.getDriver().findElement(
					By.xpath(prmBenf1PersonalDetails.replace("Benf Fields",
							"beneficiaryZipCode1")));

			designateBeneficiaries.setAddress1(Address1);
			Web.setTextToTextBox(txtPrmBenf1Addr1,
					designateBeneficiaries.getAddress1());

			designateBeneficiaries.setAddress2("");
			Web.setTextToTextBox(txtPrmBenf1Addr2,
					designateBeneficiaries.getAddress2());

			designateBeneficiaries.setCityOrTown(City);
			Web.setTextToTextBox(txtPrmBenf1CityOrTown,
					designateBeneficiaries.getCityOrTown());

			designateBeneficiaries.setState(State);
			Web.selectDropDownOption(drpPrmBenf1State,
					designateBeneficiaries.getState());

			designateBeneficiaries.setZipcode(Zip);
			Web.setTextToTextBox(txtPrmBenf1ZipCode,
					designateBeneficiaries.getZipcode());
			Web.setTextToTextBox(txtPrmBenf1Addr1,
					designateBeneficiaries.getAddress1());
		}
		lstBenfDetails.add(designateBeneficiaries);
		System.out.println(lstBenfDetails.size());

	}

	public void addPrimaryBenf1(String useCurrentAddress,
			int allocationPercentage, String... type) {
		System.out.println("");
		WebElement txtPrmBenf1FirstName=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryFirstName1")));
		WebElement txtPrmBenf1LastName=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryLastName1")));
		WebElement txtPrmBenf1MiddleName=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryMiddleName1")));
		WebElement txtPrmBenf1DOB=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "dateOfBirth1")));
		WebElement txtPrmBenf1SSN=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "socialSecurityNumber1")));	
		
		//mosin
		WebElement txtPrmBenf1maskedDOB=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "dateOfBirth1Masked")));
		WebElement txtPrmBenf1maskedSSN=Web.getDriver().findElement
				(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "socialSecurityNumber1Masked")));
		
		designateBeneficiaries = new DesignateBeneficiaries();
		
		designateBeneficiaries.setBenfCategory("Primary1");
		
		designateBeneficiaries.setFirstName("John");
		Web.setTextToTextBox(txtPrmBenf1FirstName, designateBeneficiaries.getFirstName());
				
		designateBeneficiaries.setMiddleName("A");
		Web.setTextToTextBox(txtPrmBenf1MiddleName, designateBeneficiaries.getMiddleName());
			
		designateBeneficiaries.setLastName("David");
		Web.setTextToTextBox(txtPrmBenf1LastName, designateBeneficiaries.getLastName());		
		
		designateBeneficiaries.setdOB("01/20/2008");
		try{
			Web.setTextToTextBox(txtPrmBenf1DOB, designateBeneficiaries.getdOB());	
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf1maskedDOB.clear();
			
			Web.setTextToTextBox(txtPrmBenf1DOB, designateBeneficiaries.getdOB());
		}
		designateBeneficiaries.setSsn("123456789");
		try{
			Web.setTextToTextBox(txtPrmBenf1SSN, designateBeneficiaries.getSsn());	
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf1maskedSSN.clear();
			Web.setTextToTextBox(txtPrmBenf1SSN, designateBeneficiaries.getSsn());
			
		}
		
		if(Stock.GetParameterValue("empMarried").equalsIgnoreCase("unmarried") && type.length>=0 )
		{
			WebElement drpPrmBenf1Type=Web.getDriver().findElement
					(By.xpath(prmBenf1TypeOrState.replace("Benf Fields", "beneficiaryType1")));
			designateBeneficiaries.setType(type[0]);
			Web.selectDropDownOption(drpPrmBenf1Type, designateBeneficiaries.getType());
		}
		else
			designateBeneficiaries.setType(txtPrmBenf1Type.getAttribute("value"));
		if(Stock.GetParameterValue("empMarried").equalsIgnoreCase("married"))
		{
			WebElement txtPrmBenf1Allocation=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "allocation2")));
			allocationPercentage=Integer.parseInt(txtPrmBenf1Allocation.getAttribute("value").split(" ")[0]);
		
			designateBeneficiaries.setAllocation(allocationPercentage);
			//Web.setTextToTextBox(txtPrmBenf1Allocation, Integer.toString(designateBeneficiaries.getAllocation()));

		} else if(Stock.GetParameterValue("empMarried").equalsIgnoreCase("unmarried"))
		{
		WebElement txtPrmBenf1Allocation=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "allocation1")));
		designateBeneficiaries.setAllocation(allocationPercentage);
		Web.setTextToTextBox(txtPrmBenf1Allocation, Integer.toString(designateBeneficiaries.getAllocation()));
		}
	
		if(useCurrentAddress.equalsIgnoreCase("No"))
		{
			Web.clickOnElement(chkPrm1CurrentAddress);
			WebElement txtPrmBenf1Addr1 = null;
			try{
			txtPrmBenf1Addr1=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryAddress11")));
			}
			catch(NoSuchElementException e)
			{
				Web.clickOnElement(chkPrm1CurrentAddress);
				txtPrmBenf1Addr1=Web.getDriver().findElement
						(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryAddress11")));
			}
			WebElement txtPrmBenf1Addr2=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryAddress21")));
			WebElement txtPrmBenf1CityOrTown=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryCityOrTown1")));
			WebElement drpPrmBenf1State=Web.getDriver().findElement
					(By.xpath(prmBenf1TypeOrState.replace("Benf Fields", "beneficiarystate1")));
			WebElement txtPrmBenf1ZipCode=Web.getDriver().findElement
					(By.xpath(prmBenf1PersonalDetails.replace("Benf Fields", "beneficiaryZipCode1")));
			
			designateBeneficiaries.setAddress1("123 park street");
			Web.setTextToTextBox(txtPrmBenf1Addr1, designateBeneficiaries.getAddress1());
			
			designateBeneficiaries.setAddress2("Rosewood Apartments");
			Web.setTextToTextBox(txtPrmBenf1Addr2, designateBeneficiaries.getAddress2());
			
			designateBeneficiaries.setCityOrTown("New York");
			Web.setTextToTextBox(txtPrmBenf1CityOrTown, designateBeneficiaries.getCityOrTown());	
			
			designateBeneficiaries.setState("New York");
			Web.selectDropDownOption(drpPrmBenf1State, designateBeneficiaries.getState());			
			
			designateBeneficiaries.setZipcode("10005");
			Web.setTextToTextBox(txtPrmBenf1ZipCode, designateBeneficiaries.getZipcode());
		}
		lstBenfDetails.add(designateBeneficiaries);	
		System.out.println(lstBenfDetails.size());
	}
	
	public void addPrimaryBenf2(String useCurrentAddress,int allocationPercentage,String type)
	{
		WebElement txtPrmBenf2FirstName=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryFirstName1")));
		WebElement txtPrmBenf2LastName=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryLastName1")));
		WebElement txtPrmBenf2MiddleName=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryMiddleName1")));
		WebElement txtPrmBenf2DOB=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "dateOfBirth1")));
		WebElement txtPrmBenf2SSN=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "socialSecurityNumber1")));
		WebElement txtPrmBenf2Allocation=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "allocation1")));
		WebElement drpPrmBenf2Type=Web.getDriver().findElement
				(By.xpath(prmBenf2TypeOrState.replace("Benf Fields", "beneficiaryType1")));
		

		//mosin
		WebElement txtPrmBenf2maskedDOB=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "dateOfBirth1Masked")));
		WebElement txtPrmBenf2maskedSSN=Web.getDriver().findElement
				(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "socialSecurityNumber1Masked")));
		
		
		designateBeneficiaries = new DesignateBeneficiaries();
		
		designateBeneficiaries.setBenfCategory("Primary2");
		
		designateBeneficiaries.setFirstName("Sonia");
		Web.setTextToTextBox(txtPrmBenf2FirstName, designateBeneficiaries.getFirstName());
				
		designateBeneficiaries.setMiddleName("M");
		Web.setTextToTextBox(txtPrmBenf2MiddleName, designateBeneficiaries.getMiddleName());
			
		designateBeneficiaries.setLastName("Smith");
		Web.setTextToTextBox(txtPrmBenf2LastName, designateBeneficiaries.getLastName());		
		
		designateBeneficiaries.setdOB("02/15/2006");
		try{
			Web.setTextToTextBox(txtPrmBenf2DOB, designateBeneficiaries.getdOB());	
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf2maskedDOB.clear();
			Web.setTextToTextBox(txtPrmBenf2DOB, designateBeneficiaries.getdOB());
		}
		
		designateBeneficiaries.setSsn("111111111");
		try{
		Web.setTextToTextBox(txtPrmBenf2SSN, designateBeneficiaries.getSsn());		
		}
		catch(ElementNotVisibleException e)
		{
			txtPrmBenf2maskedSSN.clear();
			Web.setTextToTextBox(txtPrmBenf2SSN, designateBeneficiaries.getSsn());
		}
		designateBeneficiaries.setType(type);
		Web.selectDropDownOption(drpPrmBenf2Type, designateBeneficiaries.getType());	
			
		designateBeneficiaries.setAllocation(allocationPercentage);
		Web.setTextToTextBox(txtPrmBenf2Allocation, Integer.toString(designateBeneficiaries.getAllocation()));
	
		if(useCurrentAddress.equalsIgnoreCase("No"))
		{
			Web.clickOnElement(chkPrm2CurrentAddress);
			WebElement txtPrmBenf2Addr1=Web.getDriver().findElement
					(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryAddress11")));
			WebElement txtPrmBenf2Addr2=Web.getDriver().findElement
					(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryAddress21")));
			WebElement txtPrmBenf2CityOrTown=Web.getDriver().findElement
					(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryCityOrTown1")));
			WebElement drpPrmBenf2State=Web.getDriver().findElement
					(By.xpath(prmBenf2TypeOrState.replace("Benf Fields", "beneficiarystate1")));
			WebElement txtPrmBenf2ZipCode=Web.getDriver().findElement
					(By.xpath(prmBenf2PersonalDetails.replace("Benf Fields", "beneficiaryZipCode1")));
			
			designateBeneficiaries.setAddress1("345 greenwood high");
			Web.setTextToTextBox(txtPrmBenf2Addr1, designateBeneficiaries.getAddress1());
			
			designateBeneficiaries.setAddress2("Rosewood Apartments");
			Web.setTextToTextBox(txtPrmBenf2Addr2, designateBeneficiaries.getAddress2());
			
			designateBeneficiaries.setCityOrTown("Chicago");
			Web.setTextToTextBox(txtPrmBenf2CityOrTown, designateBeneficiaries.getCityOrTown());	
			
			designateBeneficiaries.setState("Illinois");
			Web.selectDropDownOption(drpPrmBenf2State, designateBeneficiaries.getState());			
			
			designateBeneficiaries.setZipcode("60001");
			Web.setTextToTextBox(txtPrmBenf2ZipCode, designateBeneficiaries.getZipcode());
		}
		lstBenfDetails.add(designateBeneficiaries);
		System.out.println(lstBenfDetails.size());
	}
	
	public void addContingentBenf1(String useCurrentAddress,int allocationPercentage,String type)
	{
		WebElement txtContBenf1FirstName=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryFirstName1")));
		WebElement txtContBenf1LastName=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryLastName1")));
		WebElement txtContBenf1MiddleName=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryMiddleName1")));
		WebElement txtContBenf1DOB=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "dateOfBirth1")));
		WebElement txtContBenf1SSN=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "socialSecurityNumber1")));
		WebElement txtContBenf1Allocation=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "allocation1")));
		WebElement drpContBenf1Type=Web.getDriver().findElement
				(By.xpath(contBenf1TypeOrState.replace("Benf Fields", "beneficiaryType1")));
		
		//mosin
		WebElement txtConBenf1maskedDOB=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "dateOfBirth1Masked")));
		WebElement txtConBenf1maskedSSN=Web.getDriver().findElement
				(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "socialSecurityNumber1Masked")));
				
		designateBeneficiaries = new DesignateBeneficiaries();
		
		designateBeneficiaries.setBenfCategory("Contingent1");
		
		designateBeneficiaries.setFirstName("Melissa");
		Web.setTextToTextBox(txtContBenf1FirstName, designateBeneficiaries.getFirstName());
				
		designateBeneficiaries.setMiddleName("O");
		Web.setTextToTextBox(txtContBenf1MiddleName, designateBeneficiaries.getMiddleName());
			
		designateBeneficiaries.setLastName("Clines");
		Web.setTextToTextBox(txtContBenf1LastName, designateBeneficiaries.getLastName());		
		
		designateBeneficiaries.setdOB("03/17/1991");
		try{
		Web.setTextToTextBox(txtContBenf1DOB, designateBeneficiaries.getdOB());	
		}
		catch(ElementNotVisibleException e)
		{
			txtConBenf1maskedDOB.clear();
			Web.setTextToTextBox(txtContBenf1DOB, designateBeneficiaries.getdOB());
		}
		
		designateBeneficiaries.setSsn("222222222");
		try{
		Web.setTextToTextBox(txtContBenf1SSN, designateBeneficiaries.getSsn());		
		}
		catch(ElementNotVisibleException e)
		{
			txtConBenf1maskedSSN.clear();
			Web.setTextToTextBox(txtContBenf1SSN, designateBeneficiaries.getSsn());
		}
		designateBeneficiaries.setType(type);
		Web.selectDropDownOption(drpContBenf1Type, designateBeneficiaries.getType());	
			
		designateBeneficiaries.setAllocation(allocationPercentage);
		Web.setTextToTextBox(txtContBenf1Allocation, Integer.toString(designateBeneficiaries.getAllocation()));
	
		if(useCurrentAddress.equalsIgnoreCase("No"))
		{
			Web.clickOnElement(chkCont1CurrentAddress);
			WebElement txtContBenf1Addr1=Web.getDriver().findElement
					(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryAddress11")));
			WebElement txtContBenf1Addr2=Web.getDriver().findElement
					(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryAddress21")));
			WebElement txtContBenf1CityOrTown=Web.getDriver().findElement
					(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryCityOrTown1")));
			WebElement drpContBenf1State=Web.getDriver().findElement
					(By.xpath(contBenf1TypeOrState.replace("Benf Fields", "beneficiarystate1")));
			WebElement txtContBenf1ZipCode=Web.getDriver().findElement
					(By.xpath(contBenf1PersonalDetails.replace("Benf Fields", "beneficiaryZipCode1")));
			
			designateBeneficiaries.setAddress1("555 park town street");
			Web.setTextToTextBox(txtContBenf1Addr1, designateBeneficiaries.getAddress1());
			
			designateBeneficiaries.setAddress2("Green Valley Apartments");
			Web.setTextToTextBox(txtContBenf1Addr2, designateBeneficiaries.getAddress2());
			
			designateBeneficiaries.setCityOrTown("OakLand");
			Web.setTextToTextBox(txtContBenf1CityOrTown, designateBeneficiaries.getCityOrTown());	
			
			designateBeneficiaries.setState("California");
			Web.selectDropDownOption(drpContBenf1State, designateBeneficiaries.getState());			
			
			designateBeneficiaries.setZipcode("90001");
			Web.setTextToTextBox(txtContBenf1ZipCode, designateBeneficiaries.getZipcode());
		}
		lstBenfDetails.add(designateBeneficiaries);
		System.out.println(lstBenfDetails.size());
	}
	
	public void addContingentBenf2(String useCurrentAddress,int allocationPercentage,String type)
	{
		WebElement txtContBenf2FirstName=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryFirstName1")));
		WebElement txtContBenf2LastName=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryLastName1")));
		WebElement txtContBenf2MiddleName=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryMiddleName1")));
		WebElement txtContBenf2DOB=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "dateOfBirth1")));
		WebElement txtContBenf2SSN=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "socialSecurityNumber1")));
		WebElement txtContBenf2Allocation=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "allocation1")));
		WebElement drpContBenf2Type=Web.getDriver().findElement
				(By.xpath(contBenf2TypeOrState.replace("Benf Fields", "beneficiaryType1")));
		
		//mosin
		WebElement txtConBenf2maskedDOB=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "dateOfBirth1Masked")));
		WebElement txtConBenf2maskedSSN=Web.getDriver().findElement
				(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "socialSecurityNumber1Masked")));
		designateBeneficiaries = new DesignateBeneficiaries();
		
		designateBeneficiaries.setBenfCategory("Primary2");
		
		designateBeneficiaries.setFirstName("Sonia");
		Web.setTextToTextBox(txtContBenf2FirstName, designateBeneficiaries.getFirstName());
				
		designateBeneficiaries.setMiddleName("M");
		Web.setTextToTextBox(txtContBenf2MiddleName, designateBeneficiaries.getMiddleName());
			
		designateBeneficiaries.setLastName("Smith");
		Web.setTextToTextBox(txtContBenf2LastName, designateBeneficiaries.getLastName());		
		
		designateBeneficiaries.setdOB("02/15/2006");
		try{
		Web.setTextToTextBox(txtContBenf2DOB, designateBeneficiaries.getdOB());	
		}
		catch(ElementNotVisibleException e)
		{
			txtConBenf2maskedDOB.clear();
			Web.setTextToTextBox(txtContBenf2DOB, designateBeneficiaries.getdOB());
		}
		
		designateBeneficiaries.setSsn("111111111");
		try{
		Web.setTextToTextBox(txtContBenf2SSN, designateBeneficiaries.getSsn());		
		}
		catch(ElementNotVisibleException e)
		{
			txtConBenf2maskedSSN.clear();
			Web.setTextToTextBox(txtContBenf2SSN, designateBeneficiaries.getSsn());
		}
		designateBeneficiaries.setType(type);
		Web.selectDropDownOption(drpContBenf2Type, designateBeneficiaries.getType());	
			
		designateBeneficiaries.setAllocation(allocationPercentage);
		Web.setTextToTextBox(txtContBenf2Allocation, Integer.toString(designateBeneficiaries.getAllocation()));
	
		if(useCurrentAddress.equalsIgnoreCase("No"))
		{
			Web.clickOnElement(chkCont2CurrentAddress);
			WebElement txtContBenf2Addr1=Web.getDriver().findElement
					(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryAddress11")));
			WebElement txtContBenf2Addr2=Web.getDriver().findElement
					(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryAddress21")));
			WebElement txtContBenf2CityOrTown=Web.getDriver().findElement
					(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryCityOrTown1")));
			WebElement drpContBenf2State=Web.getDriver().findElement
					(By.xpath(prmBenf2TypeOrState.replace("Benf Fields", "beneficiarystate1")));
			WebElement txtContBenf2ZipCode=Web.getDriver().findElement
					(By.xpath(contBenf2PersonalDetails.replace("Benf Fields", "beneficiaryZipCode1")));
			
			designateBeneficiaries.setAddress1("999  Gilbert House");
			Web.setTextToTextBox(txtContBenf2Addr1, designateBeneficiaries.getAddress1());
			
			designateBeneficiaries.setAddress1("Baldwins Garden");
			Web.setTextToTextBox(txtContBenf2Addr2, designateBeneficiaries.getAddress2());	
			
			designateBeneficiaries.setCityOrTown("Newark");
			Web.setTextToTextBox(txtContBenf2CityOrTown, designateBeneficiaries.getCityOrTown());	
			
			designateBeneficiaries.setState("Delaware");
			Web.selectDropDownOption(drpContBenf2State, designateBeneficiaries.getState());			
			
			designateBeneficiaries.setZipcode("19702");
			Web.setTextToTextBox(txtContBenf2ZipCode, designateBeneficiaries.getZipcode());
		}
		lstBenfDetails.add(designateBeneficiaries);
		System.out.println(lstBenfDetails.size());
	}
	
	public void designateBenfPageValidation()
	{
		isTextFieldDisplayed("designate a beneficiary");	
		for(int i=0;i<designateBeneficiaryRadioButtons.size();i++)
		{
			if(!designateBeneficiaryRadioButtons.get(i).isSelected())
				Reporter.logEvent(Status.PASS, "Verify on landing to Deisngate Benificary Page, the radio button options are not selected by default",
									 "The Option "+i+" is not selected by default", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify on landing to Deisngate Benificary Page, the radio button options are not selected by default",
						 "The Option "+i+" is  selected by default", true);
		}
		if(isWebElementEnabled("BACK BUTTON"))
			Reporter.logEvent(Status.PASS, "Verify Back Button is Enabled by default", "The Back Button is enabled by Default", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Back Button is Enabled by default", "The Back Button is NOT enabled by Default", true);
		
		if(!isWebElementEnabled("CONTINUE BUTTON"))
			Reporter.logEvent(Status.PASS, "Verify Continue Button is Not Enabled by default",
					"The Continue Button is NOT Enabled by Default", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify Continue Button is Not Enabled by default",
					"The Continue Button is Enabled by Default", true);
	}
	
	
	public void verifyROA_ForAF(String aggregateAccounts,String exchangeOverPhone,String redemptionOverPhone,int... enterSSN)
	{
		try {
		WebElement btnAggregateAccountsOption = Web.getDriver().findElement(By.xpath(btnaggregateAccts.replace("aggregate", aggregateAccounts)));
		if(Web.clickOnElement(btnAggregateAccountsOption))
			Reporter.logEvent(Status.PASS, "Verify if user is able to select Yes/No for the Aggregate Accounts",
					"The user is able to select "+aggregateAccounts, false);
		else
		{
			Reporter.logEvent(Status.FAIL,"Verify if user is able to select Yes/No for the Aggregate Accounts",
					"The user is able to select "+aggregateAccounts, false);
			throw new Error("Aggregate Option is not  Selected");
		}
		if(aggregateAccounts.equalsIgnoreCase("yes") && enterSSN.length>0)
		{
		if(enterSSN[0]==1)	
		{
			Web.setTextToTextBox(txtSSNInput1, "1234567890");
			Reporter.logEvent(Status.PASS, "Verify the user select Yes for Agregate Accounts and Enter 01 Acccount Number/SSN" ,
			"User is able to Select Yes and able to enter the Account number ", false);
		}
		else if(enterSSN[0]==2)
		{
			Web.setTextToTextBox(txtSSNInput1, "1234567890");
			Web.setTextToTextBox(txtSSNInput2, "789456123");
			Reporter.logEvent(Status.PASS, "Verify the user select Yes for Agregate Accounts and Enter 02 Acccount Numbers/SSNs",
					"User is able to Select Yes and able to enter the Account numbers ",false);
		}
		else
		{
			Web.setTextToTextBox(txtSSNInput1, "1234567890");
			Web.setTextToTextBox(txtSSNInput2, "789456123");
			Web.setTextToTextBox(txtSSNInput3, "1357924685");
			Reporter.logEvent(Status.PASS, "Verify the user select Yes for Agregate Accounts and Enter 02 Acccount Numbers/SSNs",
					"User is able to Select Yes and able to enter the Account numbers ",false);
		}
		}			
		
		WebElement btnExchangeOverPhoneOption = Web.getDriver().findElement(By.xpath(btnExchangeOverPhone.replace("privilege", exchangeOverPhone)));
		Web.clickOnElement(btnExchangeOverPhoneOption);
		
		WebElement btnRedemptionPverPhoneOption = Web.getDriver().findElement(By.xpath(btnRedemptionOverPhone.replace("privilege", redemptionOverPhone)));
		Web.clickOnElement(btnRedemptionPverPhoneOption);				
		}
		catch(NoSuchElementException e)
		{e.printStackTrace();}		 
		
	}
	
	public static String getRelationShipCode(String s)
	{
		String relationShipcode=null;
		switch(s)
		{
		case "Spouse":
				relationShipcode= "SPS";
				break;
		case "Non Spouse":
				relationShipcode= "NSP";
				break;
		case "Trust":
				relationShipcode= "TRU";
				break;
		case "Other":
				relationShipcode= "OTH";
				break;
		}
		return relationShipcode;
	}
	
	/**
	 * validate Designate Beneficiary Details in the Database
	 * @param confirmationNum
	 */
	public static void verifyDesignateBenfDetails(String confirmationNum) 
	{
		
		try {
			System.out.println("The size is "+lstBenfDetails.size());
			if (lstBenfDetails.size() > 0) 
			{
				int confNum = Integer.parseInt(confirmationNum) + 1;
				String[] getDisbReqIdSqlQuery = Stock.getTestQuery("designateBenf_GetDisBReqId");
				getDisbReqIdSqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				ResultSet getDisbReqIdRs = DB.executeQuery(getDisbReqIdSqlQuery[0],getDisbReqIdSqlQuery[1], Integer.toString(confNum));								
				//getDisbReqIdRs.first();
				if (DB.getRecordSetCount(getDisbReqIdRs) > 0 ) {
					while(getDisbReqIdRs.next()){
					Reporter.logEvent(Status.PASS,"Verify if the user has designated to add beneficiary the Disb Request Id is generated",
							"The Disb Req Id is generated: "+ getDisbReqIdRs.getInt("id"), false);	
					System.out.println("Disb Req ID: "+getDisbReqIdRs.getString("id"));
					for(DesignateBeneficiaries designateBeneficiaries: lstBenfDetails)	
					{	
						System.out.println(designateBeneficiaries.getType());					
						desgBenfDBValidation(designateBeneficiaries,getDisbReqIdRs.getString("id"),designateBeneficiaries.getBenfCategory());					
					}
					}
				}else {
					Reporter.logEvent(
							Status.PASS,
							"Verify if the user has designated to add beneficiary the Disb Request Id is generated",
							"The Disb Req Id is NOT generated", false);
					throw new Error("The Disb Req Id is Not Generated");
				}
				
			}		
		
		} catch (SQLException e) 
		{e.printStackTrace(); }
			
		}
	
	
	public static void desgBenfDBValidation(DesignateBeneficiaries designateBenf,String dbReqId,String benfCategory)
	{
	try 
		{
		String[] getDesgBenfDetailsSqlQuery=Stock.getTestQuery("designateBenf_GetDetails");
		getDesgBenfDetailsSqlQuery[0]=Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
	
			if (designateBenf.getBenfCategory().equalsIgnoreCase(benfCategory))
			{
				ResultSet getDesgBenfRs=DB.executeQuery(getDesgBenfDetailsSqlQuery[0],
						getDesgBenfDetailsSqlQuery[1], dbReqId,getRelationShipCode(designateBenf.getType()));
				System.out.println("No of Rows Generated: "+DB.getRecordSetCount(getDesgBenfRs));
				while(getDesgBenfRs.next())
					{
							if(designateBenf.getSsn().equalsIgnoreCase(getDesgBenfRs.getString("SSN")))
								Reporter.logEvent(Status.PASS, "Verify the participant details entered in the Application is displayed in the database",
										"The Participant Details are Displayed in the Database: \n"+
										"Expected SSN: "+designateBenf.getSsn()+"\n"+
										"Actual SSN: "+getDesgBenfRs.getString("SSN")+"\n"+		
										
										"Expected First Name: "+designateBenf.getFirstName()+"\n"+
										"Actual First Name: "+getDesgBenfRs.getString("FIRST_NAME")+"\n"+	
										
										"Expected Middle Name: "+designateBenf.getMiddleName()+"\n"+
										"Actual Middle Name: "+getDesgBenfRs.getString("MIDDLE_NAME")+"\n"+	
										
										"Expected Last Name: "+designateBenf.getLastName()+"\n"+
										"Actual Last Name: "+getDesgBenfRs.getString("LAST_NAME")+"\n"+	
										
										"Expected Percentage: "+designateBenf.getAllocation()+"\n"+
										"Actual Percentage: "+getDesgBenfRs.getString("PERCENT")+"\n"+	
										
										"Expected Type: "+designateBenf.getType()+"\n"+
										"Actual Type: "+getDesgBenfRs.getString("RELATIONSHIP_CODE")+"\n",false);
					}
			}
		}
		catch(SQLException e)
		{e.printStackTrace();}
									
		}
	
	//Mosin
	public void verifyROAFields()
	{
		//Step 12 and 13
		String sExchangeText = "Do you want the ability to make exchanges over the phone and on the website?";
		String sRedemptionsText = "Do you want the ability to make redemptions over the phone and on the website?";
		isTextFieldDisplayed("Rights of accumulation (cumulative discount) for your American Funds IRA");
		isTextFieldDisplayed("The account owner, spouse, and minor children (under 21) can aggregate American Funds accounts to reduce sales charges on Class A shares only. Any share classes within these accounts will contribute toward a reduced sales charge.");
		//step 14
		List<WebElement> ROAYesNoButtons = Web .getDriver() .findElements( By.xpath("//span[@class='ng-binding ng-scope']/strong"));
		List<WebElement>  ROAPrivileges = Web .getDriver() .findElements( By.xpath("//div[@class='col-xs-12 col-sm-9']/label[contains(text(),'Do you want the ability')]"));
		ArrayList<String> lstPrivilages = new ArrayList<>();
		for(int i=0;i<ROAYesNoButtons.size();i++)
		{
			
			if(ROAYesNoButtons.get(i).getTagName().equalsIgnoreCase("Strong") && ROAYesNoButtons.get(i).isEnabled())
				Reporter.logEvent(Status.PASS,
						"Verify if “Yes/No” buttons should be bolded and should be clickable",
						ROAYesNoButtons.get(i).getText()+" is Bold and Clickable" ,
						false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if “Yes/No” buttons should be bolded and should be clickable",
						ROAYesNoButtons.get(i).getText()+" is not Bold and Clickable" ,
						true);
		}
		
		//Step 16
		Web.clickOnElement(aggregateYes);
		
		if(txtSSNInput1.isDisplayed() && txtSSNInput1.getAttribute("placeholder").equalsIgnoreCase("Account number or SSN"))
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 1 is displayed and Account number or SSN is displayed" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 1 is not displayed and Account number or SSN is not displayed" ,
					true);
		
		if(txtSSNInput2.isDisplayed())
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 2 is displayed and Account number or SSN is displayed" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 2 is not displayed and Account number or SSN is not displayed" ,
					true);
		
		if(txtSSNInput3.isDisplayed())
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 3 is displayed and Account number or SSN is displayed" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not and Account number or SSN is displayed or not",
					"TextBox 3 is not displayed and Account number or SSN is not displayed" ,
					true);
		
		//step 17
		Web.setTextToTextBox(txtSSNInput1, "1234567890");
		Web.setTextToTextBox(txtSSNInput2, "789456123");
		Web.setTextToTextBox(txtSSNInput3, "1357924685");
		
		Web.clickOnElement(aggregateNo);
		
		try{
			if(txtSSNInput1.isDisplayed())
				Reporter.logEvent(Status.FAIL,
						"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
						"TextBox 1 is displayed" ,
						true);
		}
		catch (NoSuchElementException e) {
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
					"TextBox 1 is not displayed" ,
					false);
		}
		
		try{
			if(txtSSNInput2.isDisplayed())
				Reporter.logEvent(Status.FAIL,
						"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
						"TextBox 2 is displayed" ,
						true);
		}
		catch (NoSuchElementException e) {
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
					"TextBox 2 is not displayed" ,
					false);
		}
		
		try{
			if(txtSSNInput3.isDisplayed())
				Reporter.logEvent(Status.FAIL,
						"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
						"TextBox 3 is displayed" ,
						true);
		}
		catch (NoSuchElementException e) {
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible or not",
					"TextBox 3 is not displayed" ,
					false);
		}
		
		
		
		//step 18 and 19
		Web.clickOnElement(aggregateYes);
		
		if(txtSSNInput1.isDisplayed() && txtSSNInput1.getText().equalsIgnoreCase(""))
			Reporter.logEvent(Status.PASS,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible and Prior entered text is wiped off",
					"TextBox 1 is displayed and Prior entered text is wiped off" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify upon clicking Yes for Aggregate accounts 3 Texboxes are visible and Prior entered text is wiped off",
					"TextBox 1 is not displayed and Prior entered text is not wiped off" ,
					true);
		
		//Step 20
		
		if(txtSSNInput1.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Validate the UI display when user selects Yes button",
					"First TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"First TextBox field for SSN or account number is not enabled" ,
					true);
		if(!txtSSNInput2.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Validate the UI display when user selects Yes button",
					"Second TextBox field for SSN or account number is not enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"Second TextBox field for SSN or account number is enabled" ,
					true);
		if(!txtSSNInput3.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Validate the UI display when user selects Yes button",
					"Third TextBox field for SSN or account number is not enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"First TextBox field for SSN or account number is enabled" ,
					true);
		
		//Step 21
		Web.setTextToTextBox(txtSSNInput1, "1234567890");
		if(txtSSNInput2.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Validate the UI display when user selects Yes button",
					"Second TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"Second TextBox field for SSN or account number is not enabled" ,
					true);
		
		//Step 22
		Web.setTextToTextBox(txtSSNInput2, "789456123");
		if(txtSSNInput3.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Validate the UI display when user selects Yes button",
					"Thrid TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"Third TextBox field for SSN or account number is not enabled" ,
					true);
		Web.setTextToTextBox(txtSSNInput3, "789456123");
		//step 23
		Web.setTextToTextBox(txtSSNInput1, "");
		if(txtSSNInput1.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user enters valid data in all 3 text-fields and removes one of the text-field value.",
					"First TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"First TextBox field for SSN or account number is not enabled" ,
					true);
		if(txtSSNInput2.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user enters valid data in all 3 text-fields and removes one of the text-field value.",
					"Second TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"Second TextBox field for SSN or account number is not enabled" ,
					true);
		if(txtSSNInput3.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user enters valid data in all 3 text-fields and removes one of the text-field value.",
					"Third TextBox field for SSN or account number is enabled" ,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Validate the UI display when user selects Yes button",
					"Third TextBox field for SSN or account number is not enabled" ,
					true);
		
		//step 24 and 25
		Web.setTextToTextBox(txtSSNInput1, "1");
		if(aggregateError.getText().equalsIgnoreCase("Account number or SSN must be 7-11 digits"))
			Reporter.logEvent(Status.PASS,
					"Verify when user enter a invalid value < less than 7 digits> or deletes the entered value less than 7 digit length",
					"Error message for text-field is displayed as Account number or SSN is less than 7-11 digits",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify when user enter a invalid value < less than 7 digits> or deletes the entered value less than 7 digit length",
					"Error message for text-field is not displayed as Account number or SSN is less than 7-11 digits",
					true);
		
		Web.setTextToTextBox(txtSSNInput1, "1234567",Keys.BACK_SPACE);
		if(aggregateError.getText().equalsIgnoreCase("Account number or SSN must be 7-11 digits"))
			Reporter.logEvent(Status.PASS,
					"Verify when user enter a invalid value < less than 7 digits> or deletes the entered value less than 7 digit length",
					"Error message for text-field is displayed as Account number or SSN is less than 7-11 digits",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify when user enter a invalid value < less than 7 digits> or deletes the entered value less than 7 digit length",
					"Error message for text-field is not displayed as Account number or SSN is less than 7-11 digits",
					true);
		
		//step 26
		Web.setTextToTextBox(txtSSNInput1, Stock.GetParameterValue("invalidNumber"));
		String sEnteredNumber = txtSSNInput1.getText();
		if(!Stock.GetParameterValue("invalidNumber").equalsIgnoreCase(sEnteredNumber))
			Reporter.logEvent(Status.PASS,
					"Verify whether user can enter the text box value more than 11 digit.",
					"User is not be allowed to enter more than 11 digit length value. It is restricted in the text field.",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify whether user can enter the text box value more than 11 digit.",
					"User is  allowed to enter more than 11 digit length value. It is not restricted in the text field.",
					true);
		isTextFieldDisplayed("Exchange & redemption privileges for your American Funds IRA");
		
		//Step 27
		for(int i=0;i<ROAPrivileges.size();i++)
		{
			lstPrivilages.add(i, ROAPrivileges.get(i).getText());
		}
		if(lstPrivilages.get(0).equalsIgnoreCase(sExchangeText))
			Reporter.logEvent(Status.PASS,
					"Verify another section available as Exchange & redemption privileges for your American Funds IRA and the contents in it.",
					"Text Matched"+"Actual: "+lstPrivilages.get(0)+" Expected: "+sExchangeText,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify another section available as Exchange & redemption privileges for your American Funds IRA and the contents in it.",
					"Text not Matched"+"Actual: "+lstPrivilages.get(0)+" Expected: "+sExchangeText,
					true);
		
		if(lstPrivilages.get(1).equalsIgnoreCase(sRedemptionsText))
			Reporter.logEvent(Status.PASS,
					"Verify another section available as Exchange & redemption privileges for your American Funds IRA and the contents in it.",
					"Text Matched"+"Actual: "+lstPrivilages.get(1)+" Expected: "+sExchangeText,
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify another section available as Exchange & redemption privileges for your American Funds IRA and the contents in it.",
					"Text not Matched"+"Actual: "+lstPrivilages.get(1)+" Expected: "+sExchangeText,
					true);
		//step 28
		Web.clickOnElement(aggregateNo);
		Web.clickOnElement(aggregateYes);
		Web.clickOnElement(exchangeYes);
		Web.clickOnElement(redemptionYes);
		
		if(!btnContinue.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with out entering SSN/account number.",
					"Continue button is not enabled on the initial screen",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with out entering SSN/account number.",
					"Continue button is enabled on the initial screen",
					true);
		
		//step 29
		Web.clickOnElement(aggregateYes);
		Web.setTextToTextBox(txtSSNInput1, "1234567890");
		Web.setTextToTextBox(txtSSNInput2, "789456123");
		Web.setTextToTextBox(txtSSNInput3, "1357924685");
		Web.clickOnElement(exchangeYes);
		Web.clickOnElement(redemptionYes);
		
		if(btnContinue.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with entering SSN/account number.",
					"Continue button is enabled on the initial screen",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with entering SSN/account number.",
					"Continue button is not enabled on the initial screen",
					true);
		
		//step 30
		Web.clickOnElement(aggregateNo);
		Web.clickOnElement(exchangeNo);
		Web.clickOnElement(redemptionNo);
		if(btnContinue.isEnabled())
			Reporter.logEvent(Status.PASS,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with entering SSN/account number.",
					"Continue button is enabled on the initial screen",
					false);
		else
			Reporter.logEvent(Status.FAIL,
					"Verify when user selects Yes option from ROA section and selects yes option for both from Exchange & redemption privileges for your American Funds IRA with entering SSN/account number.",
					"Continue button is not enabled on the initial screen",
					true);
		
	}
	
}
