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

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import core.framework.Globals;

public class ProdvalidationTestCases_Liat {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	static String printTestData="";

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
		printTestData="";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
		}
	 return printTestData;
	}

	
	
	
	@Test(dataProvider = "setData")
	public void RIP_TC004_To_verify_Retirement_Income_tab_Plan_Savings(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify if we are able to navigate to Plan Savings Tab
			if (retirement.isFieldDisplayed("Plan Savings"))
				Reporter.logEvent(
						Status.PASS,
						"Verify Plan Savings tab",
						"Plan Savings tab is displayed and able to click on it",
						false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Plan Savings tab",
						"Plan Savings tab is not displayed", true);
        Web.clickOnElement(retirement,"Plan Savings");
			// verify if Contribution rate slider is present
			if (retirement.verifyIfSliderPresent("Contribution rate slider"))
				Reporter.logEvent(Status.PASS,
						"Verify Contribution rate slider",
						"Contribution rate slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Contribution rate slider",
						"Contribution rate slider not displayed", true);
			// verify if Retirement age slider is present
			if (retirement.verifyIfSliderPresent("Retirement age slider"))
				Reporter.logEvent(Status.PASS, "Verify Retirement age slider",
						"Retirement age slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Retirement age slider",
						"Retirement age slider not displayed", true);

			// verify if Investment mix slider is present
			/*if (retirement.verifyIfSliderPresent("Investment mix slider"))
				Reporter.logEvent(Status.PASS, "Verify Investment mix slider",
						"Investment mix slider displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Investment mix slider",
						"Investment mix slider not displayed", true);*/

			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				Web.clickOnElement(retirement, "Do It Myself");
				if (retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", true);
			} else {
				if (!retirement.verifyIfSliderPresent("Investment mix slider"))
					Reporter.logEvent(Status.PASS,
							"Verify Investment mix slider",
							"Investment mix slider not displayed", false);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Investment mix slider",
							"Investment mix slider displayed", true);
			}
			// verify if we are able to navigate to Help Me Do It Tab
			if (lib.Stock.GetParameterValue("Help Me Do It").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Able to navigate to Help Me Do It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Not Able to navigate to Help Me Do It tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Help Me Do It"))
					Reporter.logEvent(Status.PASS, "Verify help Me Do It tab",
							"Help Me DO it tab is not displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify help Me Do It tab",
							"Help Me Do It tab is displayed", true);
			}

			// verify if we are able to navigate to Do It For Me Tab
			if (lib.Stock.GetParameterValue("Do It For Me").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Able to navigate to Do It For Me It tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Not Able to navigate to Do It For Me tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It For Me"))
					Reporter.logEvent(Status.PASS, "Verify Do It For Me tab",
							"Do It For Me tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It For Me tab",
							"Do It For Me tab is displayed", true);
			}

			// verify if we are able to navigate to Do It Myself Tab
			if (lib.Stock.GetParameterValue("Do It Myself").trim().equalsIgnoreCase("Y")) {
				if (Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Able to navigate to Do It Myself tab", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Not Able to navigate to Do It Myself tab", true);
			} else {
				if (!Web.clickOnElement(retirement, "Do It Myself"))
					Reporter.logEvent(Status.PASS, "Verify Do It Myself tab",
							"Do It Myself tab is not displayed", false);
				else

					Reporter.logEvent(Status.FAIL, "Verify Do It Myself tab",
							"Do It Myself tab is displayed", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void RIP_TC005_To_Verify_RetirementIncomeTab_Social_Security(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Social Security Tab");

				// Verify if Social Security Tab is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Social Security Administration"))
					Reporter.logEvent(Status.PASS,
							"Verify if Social Security Page is Loaded",
							"Social Security page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Social Security Page is Loaded",
							"Social Security page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void RIP_TC006_To_Verify_RetirementIncomeTab_Other_Assets(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verifying if Retirement Income Page is loaded.
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Other Assets Tab");

				// Verifying if Other Assets page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Add an Account"))
					Reporter.logEvent(Status.PASS,
							"Verify if Other Assets Page is Loaded",
							"Other Assets page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Other AssetsPage is Loaded",
							"Other Assets page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void RIP_TC007_To_Verify_RetirementIncomeTab_Income_Gap(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification twoStepVerification = new TwoStepVerification(
					login);
			LandingPage landing = new LandingPage(twoStepVerification);
			RetirementIncome retirementIncome = new RetirementIncome(landing);
			retirementIncome.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			// Verify if Retirement Income Page is loaded
			if (Web.isWebElementDisplayed(retirementIncome,
					"Estimated Retirement Income")) {
				Reporter.logEvent(Status.PASS,
						"Navigate to 'Retirement Income Page",
						"Navigation succeeded", true);
				Web.clickOnElement(retirementIncome, "Income Gap Tab");

				// Verify if Income Gap Page is loaded
				if (Web.isWebElementDisplayed(retirementIncome,
						"Catch up Contributions"))
					Reporter.logEvent(Status.PASS,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is loaded", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Income Gap Page is Loaded",
							"Income Gap page is not loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Navigate to 'Retirement Income Page",
						"Retirement Page is not loaded", true);

			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void RIP_TC008_To_verify_Retirement_Income_tab_percent_of_my_goal_section(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();

			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Incomep page.",
					"Retirement Income page is displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtMonthlyIncome = retirement.verifyPercentOfMyGoalSection("Monthly Income");
			if (expectedtxtMonthlyIncome.contains("My goal for monthly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for monthly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for monthly retirement income ",
						"Percent of my goal section not displayed", true);

			// verify Percent of my goal section for monthly retirement income
			String expectedtxtYearlyIncome = retirement.verifyPercentOfMyGoalSection("Yearly Income");
			if (expectedtxtYearlyIncome.contains("My goal for yearly retirement income"))
				Reporter.logEvent(
						Status.PASS,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section is displayed", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check Percent of my goal section for yearly retirement income",
						"Percent of my goal section not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void HCC_TC009_To_verify_Health_care_costs(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			float projectedIncome;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();

			projectedIncome = retirementIncome.getProjectedIncome();
			Web.clickOnElement(healthCareCost, "Health-care costs");
			healthCareCost.verifyAttainedAgeSlide();
			healthCareCost.verifyPieChart();
			healthCareCost.verifyHealthCostFromUI(projectedIncome);
			healthCareCost.verifyPersonalizeBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void HDIC_TC010_To_verify_How_do_I_compare_tab(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare hdic = new HowDoICompare(homePage);
			hdic.get();
			hdic.verifyViewDetails();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void LIAT_To_verify_PayCheckView_Displayed(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			lblDisplayed = Web.isWebElementDisplayed(retirement,
					"Paycheck Rainbow", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is displayed",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is Not displayed",
						true);

		} catch (Exception e) {
			e.printStackTrace(); 
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void LIAT_To_verify_PayCheckView_Displayed_With_ColorBar(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			lblDisplayed = Web.isWebElementDisplayed(retirement,
					"Paycheck Rainbow", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is displayed",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify PayCheck View in Retirement Income Page",
						"PayCheck View in Retirement Income Page is Not displayed",
						true);
			Web.waitForElement(retirement,"VIEW DETAILS");
			Web.clickOnElement(retirement,"VIEW DETAILS");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("My current savings");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("My future savings");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Employer past contribution");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Employer future contribution");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Social Security");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Other assets");
			retirement.verifyPayCheckContributionInColorBarForNonZeroValue("Income gap");
			retirement.verifyValueInColorBarForNonZeroValue("My savings");
			retirement.verifyValueInColorBarForNonZeroValue("Employer contributions");
			retirement.verifyValueInColorBarForNonZeroValue("Social Security");
			retirement.verifyValueInColorBarForNonZeroValue("Other assets");
			retirement.verifyValueInColorBarForNonZeroValue("Income gap");
					} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void LIAT_To_verify_HowDoICompare_Labels_Circles(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare howdoicompare = new HowDoICompare(homePage);
			howdoicompare.get();
			boolean lblDisplayed = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to How Do I Compare page.",
					"How Do I Compare page is displayed", true);

			
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label MyPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'My Peers' Label in How Do I Compare Page",
						"'My Peers' Label is Displayed in How Do I Compare Page",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'My Peers' Label in How Do I Compare Page",
						"'My Peers' Label is Not Displayed in How Do I Compare Page",
						true);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label TopPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Top Peers' Label in How Do I Compare Page",
						"'Top Peers' Label is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Top Peers' Label in How Do I Compare Page",
						"'Top Peers' Label is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Label Me", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Me' Label in How Do I Compare Page",
						"'Me' Label is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Me' Label in How Do I Compare Page",
						"'Me' Label is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal MyPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'My Peers Goal Section' in How Do I Compare Page",
						"'My Peers Goal Circle' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'My Peers Goal Section' in How Do I Compare Page",
						"'Goal Circle For My Peers' is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal TopPeers", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Top Peers Goal Section' in How Do I Compare Page",
						"'Goal Circle For Top Peers' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Top Peers Goal Section' in How Do I Compare Page",
						"'Top Peers Goal Circle' is Not Displayed in How Do I Compare Page",
						false);
			lblDisplayed = Web.isWebElementDisplayed(howdoicompare,
					"Goal Me", true);
			if (lblDisplayed)

				Reporter.logEvent(Status.PASS,
						"Verify 'Goal Section For Me' in How Do I Compare Page",
						"'Goal Circle For Me' is Displayed in How Do I Compare Page",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Goal Section For Me' in How Do I Compare Page",
						"'Goal Circle For Me' is Not Displayed in How Do I Compare Page",
						false);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void LIAT_To_verify_LIS_Score_Gets_Changed_as_we_Change_Dropdown_Values(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HowDoICompare howdoicompare = new HowDoICompare(homePage);
			howdoicompare.get();
			boolean lblDisplayed = false;
			List<String> DefaultContributionValues=new ArrayList<String>();
			List<String> DefaultBalanceValues= new ArrayList<String>();
			List<String> DefaultContributionValues1=new ArrayList<String>();
			List<String> DefaultBalanceValues1= new ArrayList<String>();
			List<String> ContributionRateValuesAfetrChange= new ArrayList<String>();
			List<String> BalanceValuesAfterChange=new ArrayList<String>();
			Reporter.logEvent(Status.INFO,
					"Navigate to How Do I Compare page.",
					"How Do I Compare page is displayed", true);
			DefaultContributionValues=howdoicompare.GetValuesofContributionRate();
			DefaultBalanceValues=howdoicompare.GetValuesofBalance();
			DefaultContributionValues1=DefaultBalanceValues;
			DefaultBalanceValues1=DefaultBalanceValues;
            Web.selectDropDownOption(howdoicompare, "DropDown Age", "50-59", true);
            ContributionRateValuesAfetrChange=howdoicompare.GetValuesofContributionRate();
            BalanceValuesAfterChange=howdoicompare.GetValuesofBalance();
			for(int i=0;i< DefaultContributionValues.size()-1;i++){
				if(!(DefaultContributionValues1.get(i).equalsIgnoreCase(ContributionRateValuesAfetrChange.get(i)))){
					lblDisplayed=true;
					break;
				}
			}
				
				if(lblDisplayed){
					Reporter.logEvent(Status.PASS,
							"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Age Option",
							"'Contribution Rate' Changed in How Do I Compare Page",
							true);
				}
				else{
					Reporter.logEvent(
							Status.FAIL,
							"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Age Option",
							"'Contribution Rate' is Not Changed in How Do I Compare Page",
							true);
				}
				
				for(int i=0;i< DefaultBalanceValues.size()-1;i++){
					if(!DefaultBalanceValues1.get(i).equalsIgnoreCase(BalanceValuesAfterChange.get(i))){
						lblDisplayed=true;
						break;
					}
				}
					
					if(lblDisplayed){
						Reporter.logEvent(Status.PASS,
								"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Age Option",
								"'Balance' Changed in How Do I Compare Page",
								false);
					}
					else{
						Reporter.logEvent(
								Status.FAIL,
								"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Age Option",
								"'Balance' is Not Changed in How Do I Compare Page",
								false);
					}
					 Web.selectDropDownOption(howdoicompare, "DropDown Salary", "$75K - Less than $100K", true);
			            ContributionRateValuesAfetrChange=howdoicompare.GetValuesofContributionRate();
			            BalanceValuesAfterChange=howdoicompare.GetValuesofBalance();
						for(int i=0;i< DefaultContributionValues.size()-1;i++){
							if(!DefaultContributionValues1.get(i).equalsIgnoreCase(ContributionRateValuesAfetrChange.get(i))){
								lblDisplayed=true;
								break;
							}
						}
							
							if(lblDisplayed){
								Reporter.logEvent(Status.PASS,
										"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Salary Option",
										"'Contribution Rate' Changed in How Do I Compare Page",
										true);
							}
							else{
								Reporter.logEvent(
										Status.FAIL,
										"Verify Value for 'Contribution Rate' is Changed in How Do I Compare Page After Selecting Different Salary Option",
										"'Contribution Rate' is Not Changed in How Do I Compare Page",
										true);
							}
							
							for(int i=0;i< DefaultBalanceValues.size()-1;i++){
								if(!DefaultBalanceValues1.get(i).equalsIgnoreCase(BalanceValuesAfterChange.get(i))){
									lblDisplayed=true;
									break;
								}
							}
								
								if(lblDisplayed){
									Reporter.logEvent(Status.PASS,
											"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Salary Option",
											"'Balance' Changed in How Do I Compare Page",
											false);
								}
								else{
									Reporter.logEvent(
											Status.FAIL,
											"Verify Value for 'Balance' is Changed in How Do I Compare Page After Selecting Different Salary Option",
											"'Balance' is Not Changed in How Do I Compare Page",
											false);
								}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void LIAT_To_verify_ContributionRate_Slider_For_Suspended_And_Terminated_Users(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			boolean lblEnabled = false;
			Reporter.logEvent(Status.INFO,
					"Navigate to Retirement Income page.",
					"Retirement Income page is displayed", true);

			// verify PayCheck View in retirement income page
			if(Stock.GetParameterValue("userType").equalsIgnoreCase("Terminated")){
				lblEnabled = retirement.verifyIfSliderEnabled("Contribution rate slider");
			if (!lblEnabled)

				Reporter.logEvent(Status.PASS,
						"Verify Contribution Rate Slider in Retirement Income Page",
						"Contribution Rate Slider in Retirement Income Page is Not Enabled",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Contribution Rate Slider in Retirement Income Page",
						"Contribution Rate Slider in Retirement Income Page is Enabled",
						true);
			}
			if(Stock.GetParameterValue("userType").equalsIgnoreCase("Suspended")){
				lblEnabled = retirement.verifyIfSliderEnabled("Contribution rate slider");
				if (lblEnabled)

					Reporter.logEvent(Status.PASS,
							"Verify Contribution Rate Slider in Retirement Income Page",
							"Contribution Rate Slider in Retirement Income Page is Enabled",
							true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify Contribution Rate Slider in Retirement Income Page",
							"Contribution Rate Slider in Retirement Income Page is Not Enabled",
							true);
				retirement.enterContributionRate("15");
				Reporter.logEvent(Status.INFO,
						"Enter Contribution Rate in Retirement Income Page",
						"Contribution Rate is changed in Contribution Rate Slider",
						true);
				lblEnabled = retirement.verifyIfSliderEnabled("Review Changes");
				if (!lblEnabled)

					Reporter.logEvent(Status.PASS,
							"Verify 'Review Changes' Button in Retirement Income Page",
							"'Review Changes' Button in Retirement Income Page is Not Enabled",
							true);
				else
					Reporter.logEvent(
							Status.FAIL,
							"Verify 'Review Changes' Button in Retirement Income Page",
							"'Review Changes' Button in Retirement Income Page is Enabled",
							true);
				
				}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);

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
	public void Verify_Health_care_costs_helpful_links(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			HealthCareCosts healthCareCost = new HealthCareCosts(homePage);
			//RetirementIncome retirementIncome = new RetirementIncome();
			healthCareCost.get();
			
			Web.clickOnElement(healthCareCost, "Personalize Button");
			Reporter.logEvent(Status.INFO,"Verify 'Personalize Button' is clicked","Clicked on 'Personalize Button'", true);
			Web.waitForElement(healthCareCost, "Your full report(PDF)");
			if(Web.isWebElementDisplayed(healthCareCost, "Your full report(PDF)", true)){
				Reporter.logEvent(Status.PASS,"Verify 'Your full report(PDF)' link is displayed","'Your full report(PDF)' link is displayed", false);
				Web.clickOnElement(healthCareCost, "Your full report(PDF)");
				Reporter.logEvent(Status.INFO,"Verify 'Your full report(PDF)' link is clicked","clicked on 'Your full report(PDF)' link", false);
				String parentWindow = Web.getDriver().getWindowHandle();
				Set<String> handles = Web.getDriver().getWindowHandles();

				for (String windowHandle : handles) {

					if (!windowHandle.equals(parentWindow)) {
						Web.getDriver().switchTo().window(windowHandle);
						break;
					}
				}
				// closing child window
				Web.getDriver().close(); 
				//Switching to main window
				Web.getDriver().switchTo().window(parentWindow);
			}
			else
				Reporter.logEvent(Status.FAIL,"Verify 'Your full report(PDF)' link is displayed","'Your full report(PDF)' link is Not displayed", true);
			
			healthCareCost.verifyMedicareLink();
			
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Throwable t = e.getCause();
            String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured",ae.getMessage() , true);                    
                        
        }
		finally { 
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	@Test(dataProvider = "setData")
	public void Verify_Tour_Link(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome retirement = new RetirementIncome(homePage);
			retirement.get();
			
			if(Web.isWebElementDisplayed(retirement, "TOUR Button", true)){
				Web.clickOnElement(retirement, "TOUR Button");
				Reporter.logEvent(Status.INFO,"Verify TOUR button is clicked","clicked on TOUR button", false);
				retirement.verifyTourModals("Your retirement account experience");
				Web.clickOnElement(retirement, "Next Button");
				retirement.verifyTourModals("Progress toward your goal");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Your estimated monthly retirement income");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Income sources");
				Web.clickOnElement(retirement, "NextButton");
				retirement.verifyTourModals("Compare your options");
				Web.clickOnElement(retirement, "Finish Button");
				if (Web.isWebElementDisplayed(retirement, "Cancel Goal setup", true)) {
					Web.clickOnElement(retirement, "Cancel Goal setup");
				}
			}
			else
				Reporter.logEvent(Status.FAIL,"Verify TOUR button is displayed","Tour button is displayed", false);
			//Web.clickOnElement(retirement, "LOG OUT");
			
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Throwable t = e.getCause();
            String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
            Reporter.logEvent(Status.FAIL, "A run time exception occured.",msg, true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured",ae.getMessage(), true);                    
                        
        }
		finally { 
			try { 
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}
	
	
}