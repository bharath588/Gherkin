package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.withdrawals.RequestWithdrawal;
import pageobjects.withdrawals.RequestWithdrawal_AF;
import appUtils.Common;

import com.aventstack.extentreports.Status;
import com.gargoylesoftware.htmlunit.javascript.host.media.webkitMediaStream;

import core.framework.Globals;

public class withdrawalsamericanfundstestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	static String printTestData = "";
	static int enteredRothWithdrawalAmt = 0;
	static int enteredPreTaxWithdrawalAmt = 0;
	int enteredTotalWithdrawalAmt = 0;

	@BeforeClass
	public void ReportInit() {
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
				.getName(), testCase.getName());
	}

	private String printTestData() throws Exception {
		printTestData = "";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}
	
	
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22546_Submit_Form_Data_InService(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
			
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);

			requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={70,30};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"),Stock.GetParameterValue("empMarried"));
		//	requestWithdrawalAF.addPrimaryBenf1("No", 100);
			requestWithdrawalAF.addPrimaryBenf1("No", 60,"Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 40,"Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50,"Other");	
			Actions keyBoard = new Actions(Web.getDriver());			
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();
			requestWithdrawalAF.clickContinueButton();			
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"),1);
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			requestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));			
			if(Stock.GetParameterValue("designateBeneficary").equalsIgnoreCase("Yes") && (Web.isWebElementDisplayed(requestWithdrawal, "CONFIRMATION NUM")))
			{
				String confirmationNum=requestWithdrawal.getWebElementText("CONFIRMATION NUM").trim();
				RequestWithdrawal_AF.verifyDesignateBenfDetails(confirmationNum);
			}*/	

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22547_Submit_Form_Data_SepService(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
			
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));		
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();				
			int[] investmentsOptionAllocationPercentage={50,30,20};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			if(Stock.GetParameterValue("isRothAvail").equalsIgnoreCase("Roth"))
			{
				requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
				Web.clickOnElement(requestWithdrawalAF,"FUNDS SAME AS TRAIDTIONAL IRA");
				requestWithdrawalAF.clickContinueButton();
			}
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"),Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 20,"Non Spouse");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 80,"Trust");
			requestWithdrawalAF.addContingentBenf1("Yes", 30, "Non Spouse");
			requestWithdrawalAF.addContingentBenf2("Yes", 70,"Other");
			Actions keyBoard = new Actions(Web.getDriver());			
			keyBoard.sendKeys(Keys.TAB).build().perform();			
			requestWithdrawalAF.clickContinueButton();			
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"),2);
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			RequestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));		
			if(Stock.GetParameterValue("designateBeneficary").equalsIgnoreCase("Yes") && (Web.isWebElementDisplayed(requestWithdrawal, "CONFIRMATION NUM")))
			{
				String confirmationNum=requestWithdrawal.getWebElementText("CONFIRMATION NUM").trim();
				RequestWithdrawal_AF.verifyDesignateBenfDetails(confirmationNum);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22182_Default_Beneficiary(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
		
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();			
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={50,30,20};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();			
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			RequestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));*/		
			} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22234_Married_Ppt_NonSpouse_Beneficiary(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
		
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();		
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={50,30,20};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();
			
			Web.clickOnElement(requestWithdrawalAF, "DESIGNATE BENF");
			requestWithdrawal.isTextFieldDisplayed("Are you married?");
			Web.clickOnElement(requestWithdrawalAF, "PPT MARRIED");
			
			WebElement spouseType=Web.returnElement(requestWithdrawalAF, "PRIMARY BENF1 TYPE");
			if(spouseType.getAttribute("readonly").equalsIgnoreCase("true") && spouseType.getAttribute("value").equalsIgnoreCase("Spouse"))
			Reporter.logEvent(Status.PASS, "Verify for the married participant, non-spouse benficiary cannot be added",
							"For the married participant,user cannot add non-spouse beneficiary", false);
			else
			Reporter.logEvent(Status.FAIL, "Verify for the married participant, non-spouse benficiary cannot be added",
						"For the married participant,user is able to add non-spouse beneficiary", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22235_Married_Ppt_Multiple_Beneficiaries(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
		
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={20,30,20,20,10};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();
			
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"),Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 70,"Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 30,"Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50,"Other");
			Actions keyBoard = new Actions(Web.getDriver());			
			keyBoard.sendKeys(Keys.TAB).build().perform();
			
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			RequestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));			

			if(Stock.GetParameterValue("designateBeneficary").equalsIgnoreCase("Yes") && (Web.isWebElementDisplayed(requestWithdrawal, "CONFIRMATION NUM")))
			{
				String confirmationNum=requestWithdrawal.getWebElementText("CONFIRMATION NUM").trim();
				RequestWithdrawal_AF.verifyDesignateBenfDetails(confirmationNum);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_21605_21613_22640_InService_Age59AndHalf_Roth_Only_And_RothNonRoth_Payee_Dropdown(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
			
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));		
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions=Stock.GetParameterValue("withdrawalMethod").split(",");
			int optionsLength=withdrawalMethodOptions.length;			
			for(int i=0;i<optionsLength;i++)
			{
			System.out.println(withdrawalMethodOptions[i]);
			Web.verifyDropDownOptionExists(requestWithdrawal, "WITHDRAWAL METHOD",withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_21616_22643_PWD_Roth_Only_And_RothNonRoth_Payee_Dropdown(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Common.waitForProgressBar();
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));		
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions=Stock.GetParameterValue("withdrawalMethod").split(",");
			int optionsLength=withdrawalMethodOptions.length;			
			for(int i=0;i<optionsLength;i++)
			{
			System.out.println(withdrawalMethodOptions[i]);
			Web.verifyDropDownOptionExists(requestWithdrawal, "WITHDRAWAL METHOD",withdrawalMethodOptions[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_21615_FWD_Roth_Only_Payee_Dropdown(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Common.waitForProgressBar();
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));		
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));		
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethods=Stock.GetParameterValue("withdrawalMethod").split(",");
			//Select RollOVer
			for(int j=0;j<withdrawalMethods.length;j++)
			{
			Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD FWD", withdrawalMethods[j]);	
			String[] withdrawalMethodOptions=Stock.GetParameterValue("withdrawalMethodOptions").split(",");						
			for(int i=0;i<withdrawalMethodOptions.length;i++)
			{
			System.out.println(withdrawalMethodOptions[i]);
			Web.verifyDropDownOptionExists(requestWithdrawal, "WITHDRAWAL METHOD",withdrawalMethodOptions[i]);
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22183_Married_PPT_Spouse_Beneficiary(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
		
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={50,50};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();			
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 100);
			Actions keyBoard = new Actions(Web.getDriver());			
			keyBoard.sendKeys(Keys.TAB).build().perform();
			if(requestWithdrawalAF.isWebElementEnabled("CONTINUE BUTTON"))
				Reporter.logEvent(Status.PASS, "Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is  Enabled after entering primary beneficary details", false);
			else
				Reporter.logEvent(Status.PASS, "Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is NOT Enabled after entering primary beneficary details", true);
			
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			RequestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));
			if(Stock.GetParameterValue("designateBeneficary").equalsIgnoreCase("Yes") && (Web.isWebElementDisplayed(requestWithdrawal, "CONFIRMATION NUM")))
			{
				String confirmationNum=requestWithdrawal.getWebElementText("CONFIRMATION NUM").trim();
				RequestWithdrawal_AF.verifyDesignateBenfDetails(confirmationNum);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_22250_Beneficiary_Existing_Account(
			int itr, Map<String, String> testdata) 
	{
		try {
			boolean isLabelDisplayed = false;			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);			
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
		
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",true);
			else
				Reporter.logEvent(Status.FAIL,"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",true);
			
		/*	requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));*/
			requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
			requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));			
			requestWithdrawal.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"), 
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock.GetParameterValue("doYouAlreadyHaveAF"),Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage={100};
			requestWithdrawalAF.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();			
			requestWithdrawalAF.designateBeneficiary(Stock.GetParameterValue("designateBeneficary"));
			
			if(requestWithdrawalAF.isWebElementEnabled("CONTINUE BUTTON"))
				Reporter.logEvent(Status.PASS, "Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is  Enabled after entering primary beneficary details", false);
			else
				Reporter.logEvent(Status.PASS, "Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is NOT Enabled after entering primary beneficary details", true);
			
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(Stock.GetParameterValue("agreegateAccounts"),Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);
			RequestWithdrawal.isAmericanFunds=true;
			requestWithdrawal.verifyWithdrawalConfirmation(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),Stock.GetParameterValue("deliveryMethod"));	*/		

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds=false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_26489_SepService_NonUS_Citizen(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawlAmountForSepService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
					Stock.GetParameterValue("isNonRothAvail"));
					
			Web.waitForElement(requestWithdrawal, "CONTINUE");
			if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")){
				Web.clickOnElement(requestWithdrawal, "CONTINUE");
				Reporter.logEvent(
						Status.PASS,
						"Enter withdrawal amount for multiple / single  money type sources and click Continue",
						"User enters the withdrawal amount for multiple money types source and clicked on continue button",
						false);
			}
			else{
				Reporter.logEvent(
						Status.FAIL,
						"Enter withdrawal amount for both Roth / Pre-tax  money type sources and Click Continue",
						"Continue button is not displayed in Request a Withdrawal Page",
						true);
				throw new Error("'Continue' is not displayed");
			}
			Web.waitForElement(requestWithdrawal, "NO");
			Thread.sleep(2000);
			requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");			
			requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");			
			lblDisplayed = Web.clickOnElement(requestWithdrawal, "NO");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Common.waitForProgressBar();
			if(Web.isWebElementDisplayed(requestWithdrawal, "GENERIC ALERT ICON"))
				Reporter.logEvent(Status.INFO, "Verify Alert Message has been displayed if the participants selects \"No\"",
						"The Alert Message has been displayed \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"), false);
			requestWithdrawal.isTextFieldDisplayed("Please verify your email address:");		
			Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal, Stock.GetParameterValue("emailAddress"));
			Web.clickOnElement(requestWithdrawal, "EMAIL FORM");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(requestWithdrawal, "PLAN WITHDRAWAL WARNING", true))
				Reporter.logEvent(Status.INFO, "Verify confirmation page has been displayed", "The confirmation page has been displayed: \n"
						+requestWithdrawal.getWebElementText("PLAN WITHDRAWAL WARNING"), true);	
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_26490_InService_NonUS_Citizen(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);

			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);

			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}
			
			requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType"));	
				requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
						Stock.GetParameterValue("isNonRothAvail"));			
								
			Web.waitForElement(requestWithdrawal, "CONTINUE");
			if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")){
				Web.clickOnElement(requestWithdrawal, "CONTINUE");
				Reporter.logEvent(
						Status.PASS,
						"Enter withdrawal amount for multiple / single  money type sources and click Continue",
						"User enters the withdrawal amount for multiple money types source and clicked on continue button",
						false);
			}
			else{
				Reporter.logEvent(
						Status.FAIL,
						"Enter withdrawal amount for both Roth / Pre-tax  money type sources and Click Continue",
						"Continue button is not displayed in Request a Withdrawal Page",
						true);
				throw new Error("'Continue' is not displayed");
			}
						
			Web.waitForElement(requestWithdrawal, "NO");
			Thread.sleep(2000);
			requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");			
			requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");			
			lblDisplayed = Web.clickOnElement(requestWithdrawal, "NO");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Common.waitForProgressBar();
			if(Web.isWebElementDisplayed(requestWithdrawal, "GENERIC ALERT ICON"))
				Reporter.logEvent(Status.INFO, "Verify Alert Message has been displayed if the participants selects \"No\"",
						"The Alert Message has been displayed \n"+requestWithdrawal.getWebElementText("GENERIC ALERT ICON"), false);
			requestWithdrawal.isTextFieldDisplayed("Please verify your email address:");	
			Web.setTextToTextBox("EMAIL FORM FIELD", requestWithdrawal, Stock.GetParameterValue("emailAddress"));
			Web.clickOnElement(requestWithdrawal, "EMAIL FORM");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(requestWithdrawal, "PLAN WITHDRAWAL WARNING", true))
				Reporter.logEvent(Status.INFO, "Verify confirmation page has been displayed", "The confirmation page has been displayed: \n"
						+requestWithdrawal.getWebElementText("PLAN WITHDRAWAL WARNING"), true);		
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22252_beneficiary_tool_tips_and_links(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			if (Web.isWebElementDisplayed(requestWithdrawalAF,
					"IRA CUSTODIAL LINK")) {
				Reporter.logEvent(Status.PASS,
						"Verify the designate a beneficiary Page",
						"IRA Custodial Agreement default link is displayed",
						false);
				Web.clickOnElement(requestWithdrawalAF, "IRA CUSTODIAL LINK");
				String getParentWindow = Web.getDriver().getWindowHandle();
				for (String windowHandles : Web.getDriver().getWindowHandles()) {
					Web.getDriver().switchTo().window(windowHandles); 
				}
				System.out.println(Web.getDriver().getCurrentUrl());
				if (Web.getDriver()
						.getCurrentUrl()
						.equalsIgnoreCase(
								Stock.GetParameterValue("IRA Custodial Agreement Url")))
					Reporter.logEvent(Status.PASS,
							"IRA Custodial Agreement default link",
							"IRA Custodial Agreement default url is matchng ",
							false);
				else
					Reporter.logEvent(
							Status.FAIL,
							"IRA Custodial Agreement default link",
							"IRA Custodial Agreement default url is not matchng",
							true);
				Web.getDriver().close();
				Web.getDriver().switchTo().window(getParentWindow);
				Web.getDriver().switchTo().defaultContent();
				System.out.println(Web.getDriver().getCurrentUrl());
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify the designate a beneficiary Page",
						"IRA Custodial Agreement default link is not displayed",
						true);

			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));

			requestWithdrawalAF.validateToolTip("Primary Beneficiary Link",
					Stock.GetParameterValue("primaryToolTip"));
			requestWithdrawalAF.validateToolTip("Contingent Beneficiary Link",
					Stock.GetParameterValue("contingentToolTip"));

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_AF_24984_AF_Confirmation_ScreenChanges(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(
					Stock.GetParameterValue("doYouAlreadyHaveAF"),
					Stock.GetParameterValue("advisoryOption"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 100 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(Stock
					.GetParameterValue("designateBeneficary"));

			if (requestWithdrawalAF.isWebElementEnabled("CONTINUE BUTTON"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is  Enabled after entering primary beneficary details",
						false);
			else
				Reporter.logEvent(
						Status.PASS,
						"Verify Continue Button is  Enabled after entering the primary beneficary details",
						"The Continue Button is NOT Enabled after entering primary beneficary details",
						true);

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Added this as it will change the data for the next TC's other wise
			//Web.clickOnElement(requestWithdrawal, "LOGOUT");
			//Needed
			/*
			requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"), true);
			RequestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22246_unmarried_participant_multiple_beneficiaries(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();

			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			Web.clickOnElement(requestWithdrawalAF, "BACK BUTTON");

			requestWithdrawalAF
					.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			if (Web.getDriver()
					.findElement(
							By.xpath(".//*[@id='primaryBene1']//select [@id='beneficiaryType1']"))
					.getTagName().equalsIgnoreCase("select"))
				Reporter.logEvent(Status.PASS,
						"Verify there is no verbiage for mandatory spouse",
						"No message is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify there is no verbiage for mandatory spouse",
						"Error message is displayed", false);

			requestWithdrawalAF.addPrimaryBenf1("No", 60, "Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 40, "Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50, "Other");
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"), 1);
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"), true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));
			if (Stock.GetParameterValue("designateBeneficary")
					.equalsIgnoreCase("Yes")
					&& (Web.isWebElementDisplayed(requestWithdrawal,
							"CONFIRMATION NUM"))) {
				String confirmationNum = requestWithdrawal.getWebElementText(
						"CONFIRMATION NUM").trim();
				RequestWithdrawal_AF
						.verifyDesignateBenfDetails(confirmationNum);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				Web.clickOnElement(requestWithdrawal, "LOG OUT");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22247_beneficiary_mandatory_field_messages(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 60, "Trust");
			requestWithdrawalAF.validateErrorMessages("", "", "", "12", "12",
					"", "", "", "Select option", "1", "No", "Select option");
			requestWithdrawalAF
					.isErrorMessageDisplayed("First name is required.");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Last name is required.");
			
			
			Web.isWebElementDisplayed(requestWithdrawalAF, "DOD Error Message");
			requestWithdrawalAF.isErrorMessageDisplayed("Type is required.");
			Web.isWebElementDisplayed(requestWithdrawalAF, "SSN Error Message");
			
			requestWithdrawalAF
					.isErrorMessageDisplayed("Allocation is required.");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Address line 1 is required.");
			requestWithdrawalAF
					.isErrorMessageDisplayed("City/Town is required.");
			requestWithdrawalAF.isErrorMessageDisplayed("State is required.");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Zip code must be 5 digits.");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
				if (!Web.getDriver()
						.findElement(
								By.xpath(".//*[@id='primaryBene1']//input[contains(@ng-model,'useCurrent')]"))
						.isSelected()) {
					requestWithdrawalAF.validateErrorMessages("", "", "", "",
							"", "", "", "", "Select option", "", "No",
							"Select option");
					Web.clickOnElement(requestWithdrawalAF, "Click on checkbox");
				}

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22248_beneficiary_mandatory_field_messages(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 60, "Trust");
			requestWithdrawalAF.validateErrorMessages("123", "", "abc",
					"12/12/1958", "123456789", "60", "abc", "New York",
					"New York", "1", "Yes", "Trust");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Name may not include special characters or numbers.");
			requestWithdrawalAF.validateErrorMessages("abc", "@", "abc",
					"12/12/1958", "123456789", "60", "abc", "New York",
					"New York", "1", "Yes", "Trust");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Name may not include special characters or numbers.");
			requestWithdrawalAF.validateErrorMessages("123", "", "@123",
					"12/12/1958", "123456789", "60", "abc", "New York",
					"New York", "1", "Yes", "Trust");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Name may not include special characters or numbers.");

			requestWithdrawalAF.validateErrorMessages("123", "", "@123", "12",
					"12", "0", "abc", "New York", "New York", "1", "No",
					"Trust");

			Web.isWebElementDisplayed(requestWithdrawalAF, "SSN Error Message");
			requestWithdrawalAF
					.isErrorMessageDisplayed("Allocation must be 1-100%.");

			requestWithdrawalAF
					.isErrorMessageDisplayed("Zip code must be 5 digits.");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
				if (!Web.getDriver()
						.findElement(
								By.xpath(".//*[@id='primaryBene1']//input[contains(@ng-model,'useCurrent')]"))
						.isSelected()) {
					requestWithdrawalAF.validateErrorMessages("", "", "", "",
							"", "", "", "", "Select option", "", "No",
							"Select option");
					Web.clickOnElement(requestWithdrawalAF, "Click on checkbox");
				}
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22249_beneficiary_field_range_validations(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("No", 60, "Trust");
			requestWithdrawalAF.validateErrorMessages(
					Stock.GetParameterValue("firstName"),
					Stock.GetParameterValue("middleName"),
					Stock.GetParameterValue("lastName"), "12/12/2020",
					"123456789", "60", "abc", "New York", "New York", "10009",
					"No", "Trust");
			String sActualFirstName = Web
					.getDriver()
					.findElement(
							By.xpath(".//*[@id='primaryBene1']//input[@id='beneficiaryFirstName1']"))
					.getText();
			String sActualMiddleName = Web
					.getDriver()
					.findElement(
							By.xpath(".//*[@id='primaryBene1']//input[@id='beneficiaryMiddleName1']"))
					.getText();
			String sActualLastName = Web
					.getDriver()
					.findElement(
							By.xpath(".//*[@id='primaryBene1']//input[@id='beneficiaryLastName1']"))
					.getText();

			if (!sActualFirstName.equalsIgnoreCase(Stock
					.GetParameterValue("firstName")))
				Reporter.logEvent(Status.PASS,
						"verify field range validations",
						"FirstName Maximum of 20 characters is allowed", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"verify field range validations",
						"FirstName: The field should only allow to enter a maximum of 20 characters.",
						true);

			if (!sActualMiddleName.equalsIgnoreCase(Stock
					.GetParameterValue("middleName")))
				Reporter.logEvent(Status.PASS,
						"verify field range validations",
						"MiddleName Maximum of 1 characters is allowed", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"verify field range validations",
						"MiddleName: The field should only allow to enter a maximum of 1 characters.",
						true);

			if (!sActualLastName.equalsIgnoreCase(Stock
					.GetParameterValue("lastName")))
				Reporter.logEvent(Status.PASS,
						"verify field range validations",
						"LastName Maximum of 35 characters is allowed", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"verify field range validations",
						"LastName: The field should only allow to enter a maximum of 35 characters.",
						true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
				if (!Web.getDriver()
						.findElement(
								By.xpath(".//*[@id='primaryBene1']//input[contains(@ng-model,'useCurrent')]"))
						.isSelected()) {
					requestWithdrawalAF.validateErrorMessages("", "", "", "",
							"", "", "", "", "Select option", "", "No",
							"Select option");
					Web.clickOnElement(requestWithdrawalAF, "Click on checkbox");
				}

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_AF_22251_contingent_only_beneficiaries(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addContingentBenf1("No", 50, "Trust");
			requestWithdrawalAF.addContingentBenf2("Yes", 50, "Other");
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();

			if (!requestWithdrawalAF.isWebElementEnabled("CONTINUE BUTTON"))
				Reporter.logEvent(Status.PASS,
						"Verify Continue Button is Not Enabled by default",
						"The Continue Button is NOT Enabled by Default", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Continue Button is Not Enabled by default",
						"The Continue Button is Enabled by Default", true);

			Web.clickOnElement(requestWithdrawalAF, "BACK BUTTON");
			requestWithdrawalAF
					.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
			//Added this as it will change the data for the next TC's other wise
			Web.clickOnElement(requestWithdrawal, "LOGOUT");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22765_AF_18683_having_both_moneysource_and_working_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			Thread.sleep(15000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"),
					Stock.GetParameterValue("rollingOverAccount"));
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();

			Web.clickOnElement(requestWithdrawalAF,
					"FUNDS SAME AS TRAIDTIONAL IRA");
			requestWithdrawalAF.clickContinueButton();

			requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(Stock
					.GetParameterValue("designateBeneficary"));

			

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			/*requestWithdrawal
					.verifyFWD_PartialPayment_MailDeliveryTypeAndWithDrawalSummary(
							Stock.GetParameterValue("PTSDeliveryMethod"),
							Stock.GetParameterValue("rollOverDeliveryMethod"));
			requestWithdrawal
					.verify_FWD_PartialPayments_WithdrawalConfirmation(
							Stock.GetParameterValue("ind_ID"),
							Stock.GetParameterValue("PTSDeliveryMethod"),
							Stock.GetParameterValue("rollOverDeliveryMethod"));*/

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22764_AF_18683_IN_SVC_existing_AF_PPT_having_both_money_source_working_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Thread.sleep(15000);
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();

			Web.clickOnElement(requestWithdrawalAF,
					"FUNDS SAME AS TRAIDTIONAL IRA");
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(Stock
					.GetParameterValue("designateBeneficary"));

			

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22763_AF_18683_set_up_for_SEP_SVC_existing_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//Thread.sleep(20000);
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			requestWithdrawalAF.verifyResearchInvestmentLink();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			requestWithdrawalAF.clickContinueButton();
			
			
			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(Stock
					.GetParameterValue("designateBeneficary"));

			

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22762_AF_18683_Roth_IRA_set_up_for_IN_SVC_existing_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			requestWithdrawalAF.verifyResearchInvestmentLink();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			requestWithdrawalAF.clickContinueButton();
			
			
			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(Stock
					.GetParameterValue("designateBeneficary"));

			

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22761_AF_18683_Roll_IRA_set_up_for_SEP_SVC_existing_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.verifyResearchInvestmentLink();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22760_AF_18683_Roll_IRA_set_up_for_In_SVC_existing_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			requestWithdrawalAF.verifyResearchInvestmentLink();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			Web.clickOnElement(requestWithdrawal, "LOGOUT");
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22758_AF_18683_Roll_IRA_set_up_for_IN_SVC_new_AF_PPT_having_both_money_source_and_working_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
				
			//Thread.sleep(20000);
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22759_AF_18683_Roll_IRA_set_up_for_SEP_SVC_new_AF_PPT_having_both_money_source_and_working_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22757_Roth_IRA_set_up_for_SEP_SVC_new_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
		
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22756_Roth_IRA_set_up_for_In_SVC_new_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
				
			//Thread.sleep(20000);
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22755_Roll_IRA_set_up_for_SEP_SVC_new_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
		
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22754_Roll_IRA_set_up_for_In_SVC_new_AF_PPT_without_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"No");
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			
			requestWithdrawalAF.verifyResearchInvestmentLink();
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"));
			

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"), true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				Web.clickOnElement(requestWithdrawal, "LOG OUT");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22753_Roll_IRA_set_up_for_SEP_SVC_new_AF_PPT_having_both_money_source_and_working_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22752_Roll_IRA_set_up_for_In_SVC_new_AF_PPT_having_both_money_source_and_working_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
				
			//Thread.sleep(20000);
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentsRothlessthan100percenet = { 45, 35 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothlessthan100percenet);
			int[] investmentsRothAllocationPercentage = { 45, 55 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			
			requestWithdrawalAF.verifyResearchInvestmentLink();

			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22751_Roth_IRA_set_up_for_SEP_SVC_new_AF_PPT_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22750_Roth_IRA_set_up_for_In_SVC_new_AF_PPT_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
				
			//Thread.sleep(20000);
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
					
			requestWithdrawalAF.verifyResearchInvestmentLink();

			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22749_Roll_IRA_set_up_for_SEP_SVC_new_AF_PPT_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			
			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			
			requestWithdrawalAF.validateInvestmentPage();
			int[] investmentslessthan100percenet = { 45, 35 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentslessthan100percenet);
			int[] investmentsOptionAllocationPercentage = { 45, 55 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			
			
			requestWithdrawalAF.verifyResearchInvestmentLink();


			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22748_Roll_IRA_set_up_for_In_SVC_new_AF_PPT_with_advisor(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();
			
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
				
			//Thread.sleep(20000);
			
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"),"Yes");
			requestWithdrawalAF.clickContinueButton();
			
			
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Traditional IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "Select Financial Advisor");
			
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			

			requestWithdrawalAF.addPrimaryBenf1("Yes",100);
			
			requestWithdrawalAF.addContingentBenf1("No", 100, "Other");
			
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();

			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"));
			requestWithdrawalAF.clickContinueButton();
			//Needed
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"),true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/
			//requestWithdrawal.verifyDisbursementRequest();

			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {

				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22643_PW_18681_PPTWEB_Withdrawal_SEP_Service_RothandNon_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22642_PW_18681_PPTWEB_Withdrawal_SEP_Service_Non_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22641_PW_18681_PPTWEB_Withdrawal_SEP_Service_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_22640_PW_18681_PPTWEB_Withdrawal_IN_Service_RothandNon_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));

			
				Web.setTextToTextBox("Non Roth Input TextBox",
						requestWithdrawalAF, "");
				Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
						"");
			
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22639_S_PW_18681_PPTWEB_Withdrawal_IN_Service_Non_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));

			
				Web.setTextToTextBox("Non Roth Input TextBox",
						requestWithdrawalAF, "");
				Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
						"");
			
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_22614_PW_18681_PPTWEB_Withdrawal_IN_Service_Roth_AF_IRA_Dropdown(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));

			
				Web.setTextToTextBox("Non Roth Input TextBox",
						requestWithdrawalAF, "");
				Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
						"");
			
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			String[] withdrawalMethodOptions = Stock.GetParameterValue(
					"withdrawalMethod").split(",");
			int optionsLength = withdrawalMethodOptions.length;
			for (int i = 0; i < optionsLength; i++) {
				System.out.println(withdrawalMethodOptions[i]);
				Web.verifyDropDownOptionExists(requestWithdrawal,
						"WITHDRAWAL METHOD", withdrawalMethodOptions[i]);
			}
			Web.clickOnElement(requestWithdrawal, "LOGOUT");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_23606_PW_18855_PPTWEB_Withdrawal_Sep_Service_PPT_AF_Citizenship_Screen(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalTypeForSepService(Stock
					.GetParameterValue("withdrawalType"));
			//requestWithdrawal.valdiateWithdrawOptions();
			Web.waitForElement(requestWithdrawalAF, "Sep Non Roth Input TextBox");
			Web.setTextToTextBox("Sep Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Sep Roth Input TextBox", requestWithdrawalAF,
					"");
			
			//Thread.sleep(20000);
			requestWithdrawal.enterWithdrawlAmountForSepService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawal.ssnValidation(Stock
					.GetParameterValue("wrongSSN"),"No");
			requestWithdrawal.ssnValidation(Stock
					.GetParameterValue("SSN"),"Yes");
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_DDTC_23605_PW_18855_PPTWEB_Withdrawals_In_Service_PPT_AF_Citizenship_Screen(
			int itr, Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);

			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));

			
				Web.setTextToTextBox("Non Roth Input TextBox",
						requestWithdrawalAF, "");
				Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
						"");
			
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawal.ssnValidation(Stock
					.GetParameterValue("wrongSSN"),"No");
			requestWithdrawal.ssnValidation(Stock
					.GetParameterValue("SSN"),"Yes");
			requestWithdrawal.isTextFieldDisplayed("Withdrawal method");
			
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_DDTC_22520_PW_18685_Social_Security_and_ROA_account_linking_Non_Roth_only(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("Yes", 60, "Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 40, "Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50, "Other");
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.verifyROAFields();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"), 1);
			requestWithdrawalAF.clickContinueButton();
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"),true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_DDTC_22519_PW_18685_Social_Security_and_ROA_account_linking_Roth_only(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();

			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			requestWithdrawalAF.addPrimaryBenf1("Yes", 60, "Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 40, "Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50, "Other");
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.verifyROAFields();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"), 1);
			requestWithdrawalAF.clickContinueButton();
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"),true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_DDTC_22210_PW_18685_Social_Security_and_ROA_account_linking_multiple_money(int itr,
			Map<String, String> testdata) {
		try {
			boolean isLabelDisplayed = false;
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage.clickPlanNameByGAID(Stock
						.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			Common.waitForProgressBar();
			RequestWithdrawal_AF requestWithdrawalAF = new RequestWithdrawal_AF();

			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (isLabelDisplayed)
				Reporter.logEvent(Status.INFO,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is displayed successfully",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify if Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT displayed successfully",
						true);
			// System.out.println(Stock.GetParameterValue("IRA Custodial Agreement Url"));
			requestWithdrawal.selectWithdrawalType(Stock
					.GetParameterValue("withdrawalType"));
			Web.setTextToTextBox("Non Roth Input TextBox",
					requestWithdrawalAF, "");
			Web.setTextToTextBox("Roth Input TextBox", requestWithdrawalAF,
					"");
			requestWithdrawal.enterWithdrawalAmountForInService(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("isRothAvail"),
					Stock.GetParameterValue("isNonRothAvail"));
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawalAF.verifyWithdrawalMethodPage_AF(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.verifyDoYouAlreadyHaveIRA_AccountPage(Stock
					.GetParameterValue("doYouAlreadyHaveAF"));
			requestWithdrawalAF.clickContinueButton();
			int[] investmentsOptionAllocationPercentage = { 70, 30 };
			requestWithdrawalAF
					.selectInvestmentOption(investmentsOptionAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			requestWithdrawalAF.isTextFieldDisplayed("How would you like your American Funds Roth IRA rollover assets to be invested?");
			Web.clickOnElement(requestWithdrawalAF, "SELECT MY OWN INVESTMENTS");
			int[] investmentsRothAllocationPercentage = { 70, 30 };
			requestWithdrawalAF.selectInvestmentOption(investmentsRothAllocationPercentage);
			requestWithdrawalAF.clickContinueButton();
			//requestWithdrawalAF.designateBenfPageValidation();
			requestWithdrawalAF.designateBeneficiary(
					Stock.GetParameterValue("designateBeneficary"),
					Stock.GetParameterValue("empMarried"));
			/*requestWithdrawalAF.addPrimaryBenf1("Yes", 60, "Trust");
			requestWithdrawalAF.addPrimaryBenf2("Yes", 40, "Non Spouse");
			requestWithdrawalAF.addContingentBenf1("Yes", 50, "Other");
			requestWithdrawalAF.addContingentBenf2("Yes", 50, "Other");*/
			Actions keyBoard = new Actions(Web.getDriver());
			keyBoard.sendKeys(Keys.TAB).build().perform();
			keyBoard.sendKeys(Keys.ENTER).build().perform();
			requestWithdrawalAF.clickContinueButton();
			
			requestWithdrawalAF.verifyROAFields();
			requestWithdrawalAF.verifyROA_ForAF(
					Stock.GetParameterValue("agreegateAccounts"),
					Stock.GetParameterValue("exchangeOverPhone"),
					Stock.GetParameterValue("redemptionOverPhone"), 1);
			requestWithdrawalAF.clickContinueButton();
			/*requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"),true);
			requestWithdrawal.isAmericanFunds = true;
			requestWithdrawal.verifyWithdrawalConfirmation(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("deliveryMethod"));*/
			

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					ae.getMessage(), true);

		} finally {
			try {
				RequestWithdrawal.isAmericanFunds = false;
				RequestWithdrawal.resetTotalValues();
				RequestWithdrawal_AF.lstBenfDetails.clear();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}