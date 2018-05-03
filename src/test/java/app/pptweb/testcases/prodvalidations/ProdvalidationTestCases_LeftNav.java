package app.pptweb.testcases.prodvalidations;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.balance.Balance;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.deferrals.Deferrals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.general.RateOfReturnPage;
import pageobjects.investment.Brokerage;
import pageobjects.investment.InvestmentLineup;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.landingpage.LegacyLandingPage;
import pageobjects.liat.HealthCareCosts;
import pageobjects.liat.HowDoICompare;
import pageobjects.liat.ProfilePage;
import pageobjects.liat.RetirementIncome;
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.ForgotPassword;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.payroll.PayrollCalendar;
import pageobjects.planinformation.PlanForms;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import pageobjects.transactionhistory.TransactionHistory;
import pageobjects.withdrawals.RequestWithdrawal;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ProdvalidationTestCases_LeftNav {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	static String printTestData = "";

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
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(
				Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}

	
	@Test(dataProvider = "setData")
	public void SF01_TC018_Verify_ROR_link(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Common.getSponser()
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RateOfReturnPage ROR = new RateOfReturnPage(lftNavBar);
			ROR.get();
			boolean isVerified = false;
			isVerified = ROR.verifyDataInRateOfReturnPage();
			if (isVerified) {
				Reporter.logEvent(
						Status.PASS,
						" Verify Rate Of Return Page is Displayed with Proper Data",
						"Rate Of Return Page is Displayed with Proper Data",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						" Verify Rate Of Return Page is Displayed with Proper Data",
						"Rate Of Return Page is Not Displayed with Proper Data",
						true);
			}
			Web.getDriver().switchTo().defaultContent();
			// Web.clickOnElement(ROR, "LOGOUT");
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
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	@Test(dataProvider = "setData")
	public void Balance_validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			Balance balance = new Balance(leftmenu);
			balance.get();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(5000);

			if (!Web.isWebElementDisplayed(balance, "Balance", true)) {
				Common.handlePageToLoad("Balance");
			}

			balance.navigateToTab("Balance");
			// not seeing this table in PRod data
			/*
			 * balance.verifyTableDisplayed("Balance by Money Table");
			 * balance.verifytableHeaderNotEmpty
			 * ("Balance by Money Table Header");
			 * balance.verifyTableDataDisplayed("Balance by Money Table");
			 */

			balance.verifyTableDisplayed("Balance by Investment Table");
			balance.verifytableHeaderNotEmpty("Balance by Investment Table Header");
			balance.verifyTableDataDisplayed("Balance by Investment Table");
			balance.verifyGraphDisplayed("High chart graph");

			balance.navigateToTab("Balance over time");
			balance.verifyTableDisplayed("Balance History Table");
			balance.verifytableHeaderNotEmpty("Balance History Table Header");
			balance.verifyTableDataDisplayed("Balance History Table");
			balance.verifyGraphDisplayed("Line graph");

			balance.navigateToTab("Balance comparison");
			balance.verifyTableDisplayed("Balance Comparison Table");
			balance.verifytableHeaderNotEmpty("Balance Comparison Table Header");
			balance.verifyTableDataDisplayed("Balance Comparison Table");
			balance.verifyGraphDisplayed("Balance Comparison Graph");
			// Web.clickOnElement(balance, "LOGOUT");
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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Transaction_History_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			TransactionHistory transaction = new TransactionHistory(leftmenu);

			transaction.get();
			Thread.sleep(5000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Common.switchToLegacyFutureFrame()) {
				if (!Web.isWebElementDisplayed(transaction,
						"DropDown Frequency", true)) {
					Common.handlePageToLoad("Transaction history");
				}
			} else
				Common.handlePageToLoad("Transaction history");

			transaction.selectDateFrequency("Three Years");
			Thread.sleep(5000);
			// transaction.clickConfirmationNumber();
			transaction.verifyTableDisplayed("Transaction Filter Option Table");
			transaction.clickConfirmationNumber();

			transaction
					.verifyTableDisplayed("Transaction History Contr Summary Table");
			transaction
					.verifytableHeaderNotEmpty("Transaction History Contr Summary Table Header");
			transaction
					.verifyTableDataDisplayed("Transaction History Contr Summary Table");

			transaction
					.verifyTableDisplayed("Transaction History Contr Detail Table");
			transaction
					.verifytableHeaderNotEmpty("Transaction History Contr Detail Table Header");
			transaction
					.verifyTableDataDisplayed("Transaction History Contr Detail Table");

			transaction.verifyReferenceNumber();
			// Web.clickOnElement(transaction, "LOGOUT");
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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void verify_Statement_On_Demand_Tab(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			StatementsAndDocuments statements = new StatementsAndDocuments(
					leftmenu);

			statements.get();
			Thread.sleep(5000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			statements.navigateToTab("Stmts On Demand Tab");
			statements.selectDateFrequency("Two Years");
			Thread.sleep(5000);
			statements.verifyTableDisplayed("Account at a Glance");
			statements
					.verifytableHeaderNotEmpty("Account at a Glance Table Header");
			statements.verifyTableDataDisplayed("Account at a Glance Table");

			statements
					.verifyTableDisplayed("Activity by Contribution Source");
			statements
					.verifytableHeaderNotEmpty("Activity by Contribution Source Table Header");
			statements
					.verifyTableDataDisplayed("Activity by Contribution Source Table");

			statements
					.verifyTableDisplayed("Activity by Investment Option");
			statements
					.verifytableHeaderNotEmpty("Activity by Investment Option Table Header");
			statements
					.verifyTableDataDisplayed("Activity by Investment Option Table");

			statements
					.clickOnContributionSource("Activity by Contribution Source");
			statements.verifyTableDisplayed("Transaction Details Table");
			statements
					.verifytableHeaderNotEmpty("Transaction Details Table Header");
			statements.verifyTableDataDisplayed("Transaction Details Table");
			statements.clickOnClose();
			
			Web.getDriver().switchTo().defaultContent();
			statements.clickOnInvestmentoption("Activity by Investment Option");
	statements.verifyTableDisplayed("Transaction Details Table");
	statements
			.verifytableHeaderNotEmpty("Transaction Details Table Header");
	statements.verifyTableDataDisplayed("Transaction Details Table");
	statements.clickOnClose();
	

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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void verify_Statement_and_Documents_Tab(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			StatementsAndDocuments statements = new StatementsAndDocuments(
					leftmenu);

			statements.get();
			Thread.sleep(5000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			

			statements.navigateToTab("Statements And Documents Tab");
			statements.verifyTableDisplayed("Statements And Documents");
			statements.verifyFilter("Confirms");
			statements.verifyFilter("Statements");
			statements.verifyViewAllLink();
			

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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	
	@Test(dataProvider = "setData")
	public void Payroll_Calendar_validations(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PayrollCalendar payroll = new PayrollCalendar(leftmenu);
			payroll.get();

			// Thread.sleep(5000);
			payroll.verifyDataIsDiaplyed();
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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	@Test(dataProvider = "setData")
	public void Edit_MyContribution(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferral = new Deferrals(leftmenu);
			deferral.get();
			// Thread.sleep(5000);
			if (deferral.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferral.click_Select_Your_Contribution_Rate();
			deferral.select_ContributionType("Before");
			lib.Web.clickOnElement(deferral, "Continue button");
			Thread.sleep(3000);
			if (deferral.verifyMyContributions(deferral.contrbution_rate,
					"Before Tax", "Standard"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Before contribution percent for Standard deferral",
						"Before contribution percent matching", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Before contribution percent for Standar deferral",
						"Before contribution percent matching", true);
			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				deferral.myContributions_Confirmation_Page();
				lib.Web.clickOnElement(deferral, "MyContribution Button");
			}
			Web.getDriver().switchTo().defaultContent();
			// lib.Web.clickOnElement(deferral, "LOG OUT");
			Web.clickOnElement(homePage, "HOME");
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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	@Test(dataProvider = "setData")
	public void PlanForms_Validations(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			leftmenu = new LeftNavigationBar(homePage);
			PlanForms planforms = new PlanForms(leftmenu);
			planforms.get();
			// Thread.sleep(7000);

			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			planforms.clickOnForm(null);
			/*if (planforms.verifyPlanFormIsOpened())
				Reporter.logEvent(Status.PASS, "Verify Plan Form is opened",
						"Plan form is opened ", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Form is opened",
						" Plan form is not opened", true);*/

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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	@Test(dataProvider = "setData")
	public void Edit_Beneficiary(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			beneficiary.get();
			// Thread.sleep(5000);
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.",
					"Beneficiary page is displayed", true);
			beneficiary.clickOnBeneficiaryFromTable(null, "Primary");

		
			beneficiary.enterBeneficiaryDetails();
			lib.Web.clickOnElement(beneficiary, "Save button");
			if (lib.Web.isWebElementDisplayed(beneficiary, "MyBeneficiaries"))
				Reporter.logEvent(Status.PASS,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if My Beneficiaries page is displayed",
						"My Beneficiaries page is not displayed", true);
			if (beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button",
						"Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button",
						"Confirm and Continue button is not displayed", true);

			if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
					"Yes")) {
				// click on continue and confirm button
				if (Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
					Reporter.logEvent(Status.PASS,
							"Confirm and Continue button",
							"Clicked confirm and continue button", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Confirm and Continue button",
							"Could not Click confirm and continue button", true);
				// verify beneficiary name

				if (beneficiary.verifyBeneficiaryDetails("Name"))
					Reporter.logEvent(Status.PASS, "verify beneficiary name",
							"beneficiary name is matching", true);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary name",
							"beneficiary name bot matching", true);

				// verify beneficiary allocation percentage
				if (beneficiary.verifyBeneficiaryDetails("Allocation"))
					Reporter.logEvent(Status.PASS,
							"verify beneficiary Allocation",
							"beneficiary Allocation is matching", false);
				else
					Reporter.logEvent(Status.FAIL,
							"verify beneficiary Allocation",
							"beneficiary Allocation bot matching", true);

				// verify beneficiary Relationship
				if (beneficiary.verifyBeneficiaryDetails("Relationship"))
					Reporter.logEvent(Status.PASS,
							"verify beneficiary Relationship",
							"beneficiary Relationship is matching", false);
				else
					Reporter.logEvent(Status.FAIL,
							"verify beneficiary Relationship",
							"beneficiary Relationship bot matching", true);

				// verify beneficiary ssn
				if (beneficiary.verifyBeneficiaryDetails("SSN"))
					Reporter.logEvent(Status.PASS, "verify beneficiary SSN",
							"beneficiary SSN is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary SSN",
							"beneficiary SSN bot matching", true);

				// verify beneficiary DOB
				if (beneficiary.verifyBeneficiaryDetails("DOB"))
					Reporter.logEvent(Status.PASS, "verify beneficiary DOB",
							"beneficiary DOB is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary DOB",
							"beneficiary DOB bot matching", true);
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
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC019_Verify_Request_A_Withdrawal_link(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Common.getSponser()
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			Actions keyBoard = new Actions(Web.getDriver());
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			boolean lblDisplayed = false;
			requestWithdrawal.selectSourceHierarchyForInService(Stock
					.GetParameterValue("withdrawalType"));
			/*
			 * requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue(
			 * "withdrawalType"));
			 * requestWithdrawal.enterWithdrawalAmountForInService
			 * (Stock.GetParameterValue
			 * ("withdrawalType"),Stock.GetParameterValue("isRothAvail"),
			 * Stock.GetParameterValue("isPreTaxAvail"));
			 */
			requestWithdrawal.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal.verifyWithdrawalMethodPage(
					Stock.GetParameterValue("withdrawalType"),
					Stock.GetParameterValue("withdrawalMethod"),
					Stock.GetParameterValue("emailAddress"));
			requestWithdrawal.verifyWithdrawalSummary(
					Stock.GetParameterValue("deliveryMethod"), true);
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"I AGREE AND SUBMIT", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify I Agree and Submit Button is Displayed",
						"I Agree and Submit Button is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify I Agree and Submit Button is Displayed",
						"I Agree and Submit Button is Not Displayed", false);
			}
			if (Stock.GetParameterValue("submitRequest")
					.equalsIgnoreCase("YES")) {
				Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");
				Common.waitForProgressBar();
				Thread.sleep(3000);
				lblDisplayed = requestWithdrawal
						.isTextFieldDisplayed("Request submitted!");
				if (lblDisplayed) {
					Reporter.logEvent(
							Status.INFO,
							"Verify Withdrawals Confirmation Page is Displayed",
							"Withdrawals Confirmation Page is Displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Withdrawals Confirmation Page is Displayed",
							"Withdrawals Confirmation is Not Displayed", true);
				}
				lblDisplayed = Web.VerifyPartialText(
						"Your confirmation number is", requestWithdrawal
								.getWebElementText("TEXT CONFIRMATION"), true);
				if (lblDisplayed) {
					Reporter.logEvent(Status.INFO,
							"Verify Request Confirmation is Displayed",
							"Request Confirmation is Displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation is Displayed",
							"Request Confirmation is Not Displayed", true);
				}

				if (Web.isWebElementDisplayed(requestWithdrawal,
						"TEXT CONFIRMATION NUMBER", true)) {
					lblDisplayed = Common
							.verifyStringIsInNumberFormat(requestWithdrawal
									.getWebElementText("TEXT CONFIRMATION NUMBER"));
					if (lblDisplayed) {
						Reporter.logEvent(
								Status.PASS,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation is in Number Format and \n Confirmation Number is:"
										+ requestWithdrawal
												.getWebElementText("TEXT CONFIRMATION NUMBER"),
								false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation Number is Not in Number Format"
										+ requestWithdrawal
												.getWebElementText("TEXT CONFIRMATION NUMBER"),
								true);
					}
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation Number is Displayed",
							"Request Confirmation Number is Not Displayed",
							true);
				}
			}
			Web.getDriver().switchTo().defaultContent();
			// Web.clickOnElement(requestWithdrawal, "LOG OUT");
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
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	

	@Test(dataProvider = "setData")
	public void Verify_Request_A_Loan_Flow(int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread
							.currentThread().getId())
							+ "_"
							+ Common.getSponser()
							+ "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftBar = new LeftNavigationBar(homePage);
			RequestLoanPage requestLone = new RequestLoanPage(lftBar);
			requestLone.get();

			boolean lblDisplayed = false;
			// int confirmationNumber = 0;
			Thread.sleep(5000);
			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"Request a loan", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Loan Page is Displayed",
						"Request A Loan Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Loan Page is Displayed",
						"Request A Loan Page is NOT Displayed", true);
			}

			Web.waitForElement(requestLone, "Button Request A New Loan");

			Web.clickOnElement(requestLone, "Button Request A New Loan");
			Web.waitForElement(requestLone, "LOAN TYPE GENERAL");
			requestLone.isTextFieldDisplayed("Loan purpose");
			requestLone.isTextFieldDisplayed("Maximum loan");
			requestLone.isTextFieldDisplayed("Minimum loan");
			requestLone.isTextFieldDisplayed("Repayment term");
			requestLone.isTextFieldDisplayed("Documentation required");
			requestLone.isTextFieldDisplayed("Interest rate");
			requestLone.isTextFieldDisplayed("Repayment");
			requestLone.isTextFieldDisplayed("Fees");
			requestLone.selectLoneType(Stock.GetParameterValue("loanType"));

			requestLone.EnterLoanAmtAndTerm("$1000", "12");

			Web.waitForElement(requestLone, "BUTTON CONTINUE");
			requestLone.isTextFieldDisplayed("My loan summary");
			requestLone.isTextFieldDisplayed("INTEREST RATE");
			requestLone.isTextFieldDisplayed("FEES*");
			requestLone.isTextFieldDisplayed("CHECK TOTAL");
			requestLone.isTextFieldDisplayed("LOAN TOTAL");
			if (Web.VerifyPartialText("$1,000",
					requestLone.getWebElementText("LOAN TOTAL"), true)) {

				Reporter.logEvent(Status.PASS,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Matching in Loan summary Table\nLoan Total:"
								+ requestLone.getWebElementText("LOAN TOTAL"),
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Loan Total Amount is Matching",
						"Loan Total Amount is Not Matching in Loan summary Table\nExpected:$1,000.00\nActual:"
								+ requestLone.getWebElementText("LOAN TOTAL"),
						true);
			}
			Web.clickOnElement(requestLone, "BUTTON CONTINUE");

			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"ProActive Notification Screen", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify ProActive Notification Screen is Displayed",
						"ProActive Notification Screen is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify ProActive Notification Screen is Displayed",
						"ProActive Notification Screen is Not Displayed", true);
			}
			requestLone
					.isTextFieldDisplayed("Sign up for updates on your loan process");
			Web.clickOnElement(requestLone, "BUTTON CONTINUE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestLone.verifyPageHeaderIsDisplayed("Header Loan Review");
			requestLone.isTextFieldDisplayed("Loan Details");
			requestLone.isTextFieldDisplayed("PLAN:");
			requestLone.isTextFieldDisplayed("LOAN TYPE:");
			requestLone.isTextFieldDisplayed("TERM:");
			requestLone.isTextFieldDisplayed("MATURITY DATE:");
			requestLone.isTextFieldDisplayed("INTEREST RATE:");
			requestLone.isTextFieldDisplayed("ANNUAL PERCENTAGE RATE (APR):");
			requestLone.isTextFieldDisplayed("CHECK AMOUNT:");
			requestLone.isTextFieldDisplayed("LOAN AMOUNT:");
			requestLone.isTextFieldDisplayed("TOTAL INTEREST AMOUNT:");
			requestLone
					.isTextFieldDisplayed("TOTAL PRINCIPAL AND INTEREST AMOUNT:");
			requestLone.isTextFieldDisplayed("Payment Information");
			requestLone.isTextFieldDisplayed("FIRST PAYMENT DUE:");
			requestLone.isTextFieldDisplayed("LAST PAYMENT DUE:");
			requestLone.isTextFieldDisplayed("NUMBER OF PAYMENTS:");
			requestLone.isTextFieldDisplayed("PAYMENT AMOUNT:");
			requestLone.isTextFieldDisplayed("PAYMENT METHOD:");
			requestLone.isTextFieldDisplayed("PAYMENT FREQUENCY:");
			requestLone.isTextFieldDisplayed("Fees and Taxes");
			requestLone.isTextFieldDisplayed("ORIGINATION FEE:");
			requestLone.isTextFieldDisplayed("Delivery Information");
			requestLone.isTextFieldDisplayed("DELIVERY METHOD:");
			requestLone.isTextFieldDisplayed("MAILING ADDRESS:");
			requestLone.isTextFieldDisplayed("Loan Provisions");

			Thread.sleep(3000);
			lblDisplayed = Web.isWebElementDisplayed(requestLone,
					"I AGREE AND SUBMIT", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify 'I Agree and Submit' Button is Displayed",
						"'I Agree and Submit' Button is Not Displayed", true);
			}
			if (Stock.GetParameterValue("submitRequest")
					.equalsIgnoreCase("YES")) {
				Web.clickOnElement(requestLone, "I ACCEPT");
				Thread.sleep(3000);
				lblDisplayed = Web.VerifyPartialText(
						"Your confirmation number is",
						requestLone.getWebElementText("TEXT CONFIRMATION"),
						true);
				if (lblDisplayed) {
					Reporter.logEvent(Status.INFO,
							"Verify RequestLoan Confirmation is Displayed",
							"RequestLoan Confirmation is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify RequestLoan Confirmation is Displayed",
							"RequestLoan Confirmation is Not Displayed", true);
				}
				if (Web.isWebElementDisplayed(requestLone,
						"TEXT CONFIRMATION NUMBER", true)) {
					lblDisplayed = Common
							.verifyStringIsInNumberFormat(requestLone
									.getWebElementText("TEXT CONFIRMATION NUMBER"));
					if (lblDisplayed) {
						Reporter.logEvent(
								Status.PASS,
								"Verify Request Confirmation Number is in Number Format",
								"Request Confirmation is in Number Format and \n Confirmation Number is:"
										+ requestLone
												.getWebElementText("TEXT CONFIRMATION NUMBER"),
								false);
					} else {
						Reporter.logEvent(
								Status.FAIL,
								"Verify Request Confirmation Number is Number Format",
								"Request Confirmation Number is  Not in Number Format"
										+ requestLone
												.getWebElementText("TEXT CONFIRMATION NUMBER"),
								true);
					}
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request Confirmation Number is Displayed",
							"Request Confirmation Number is Not Displayed",
							true);
				}
			}
			Web.getDriver().switchTo().defaultContent();
			// Web.getDriver().close();
			// Web.getDriver().switchTo().window(parentWindow);
			// Web.getDriver().switchTo().defaultContent();
			/*
			 * Web.clickOnElement(requestLone, "LOGOUT");
			 * Common.waitForProgressBar();
			 * Web.waitForPageToLoad(Web.getDriver()); Web.waitForElement(login,
			 * "SIGN IN");
			 */
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
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	

}
