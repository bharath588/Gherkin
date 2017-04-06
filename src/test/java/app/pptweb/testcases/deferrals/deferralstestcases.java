package app.pptweb.testcases.deferrals;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.internal.NewProfileExtensionConnection;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.deferrals.PriorPlanContributions;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import appUtils.TestDataFromDB;

import com.aventstack.extentreports.Status;
import com.sun.prism.paint.Stop;

import core.framework.Globals;

public class deferralstestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private static HashMap<String, String> testDataFromDB = null;
	LoginPage login;
	String tcName;
	static String printTestData="";
	

	@BeforeClass
	public void InitTest() throws Exception {
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
	
	 public void prepareDynamicTestData(String quesryNmae,String... queryParam) {
			try {
				testDataFromDB = TestDataFromDB.getParticipantDetails(
						quesryNmae, queryParam);
				TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	    
	    private String printTestData() throws Exception {
			printTestData="";
			for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
				if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
					printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
			}
		 return printTestData;
		}

	

	/**
	 * The following script After Tax Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_001_After-tax_Select
	 * another contribution rate_Sanity
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__AfterTax(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			

			
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * The following script Bonus Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_002_Bonus_Select another
	 * contribution rate 2.Bonus_SelectAnotherContribution_after
	 * 3.Bonus_SelectAnotherContribution_split
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__Bonus(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Bonus contribution page",
						"Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Bonus contribution page",
						"Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			

			if (Stock.GetParameterValue("Contribution_type").equalsIgnoreCase(
					"split"))
				deferrals.split_bonus_contribution();
			else
				deferrals.select_ContributionType(Stock
						.GetParameterValue("Contribution_type"));

			deferrals.add_Auto_Increase(Stock
					.GetParameterValue("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Deferral_002_Bonus_Select_another_contribution_rate(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Bonus_SelectAnotherContribution_after(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Bonus_SelectAnotherContribution_split(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_012_Regular_BEFORE-Select Another contribution
	 * rate_Sanity 2.SIT_PPTWEB_Deferral_016_Regular_SPLIT- Select Another
	 * contributiont rate_Sanity
	 * 3.SIT_PPTWEB_Deferral_010_Regular_BEFORE-Maximize to the company match
	 * 4.SIT_PPTWEB_Deferral_011_Regular_BEFORE-Maximize to the IRS limit
	 * 5.SIT_PPTWEB_Deferral_013_Regular_ROTH-Maximize to the company match
	 * 6.SIT_PPTWEB_Deferral_014_Regular_ROTH-Maximize to the IRS limit
	 * 7.SIT_PPTWEB_Deferral_015_Regular_ROTH-Select Another contribution rate
	 * 8.SIT_PPTWEB_Deferral_017_Regular_SPLIT-Maximize to the company match
	 * 9.SIT_PPTWEB_Deferral_018_Regular_SPLIT-Maximize to the IRS limit
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__Regular(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Participant ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else{
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();

			try {
				Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);

			if (Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to company match"))
				deferrals.click_MaximizeToCompanyMatch();
			if (Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit();
			if (Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			if (!Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock
						.GetParameterValue("Add_auto_increase_type"));

			deferrals.myContributions_Confirmation_Page();

			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "",
					true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_012_Regular_BEFORE_Select_Another_contribution_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_016_Regular_SPLIT_Select_Another_contributiont_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_010_Regular_BEFORE_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_011_Regular_BEFORE_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_013_Regular_ROTH_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_014_Regular_ROTH_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_015_Regular_ROTH_Select_Another_contribution_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_017_Regular_SPLIT_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_018_Regular_SPLIT_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Catch_up(int itr,
			Map<String, String> testdata) throws Exception {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Catch up contribution page",
						"Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Catch up contribution page",
						"Catch up Contributions page is not displayed", true);

			if (Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit();
			if (Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			if (Stock.GetParameterValue("Contribution_type")
					.equalsIgnoreCase("Split"))
				deferrals.split_catchup_contribution();
			else
				deferrals.select_ContributionType(lib.Stock
						.GetParameterValue("Contribution_type"));

			if (!Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock
						.GetParameterValue("Add_auto_increase_type"));

			deferrals.myContributions_Confirmation_Page();

			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			Web.getDriver().navigate().refresh();
			Deferrals deferrals = new Deferrals();
			Web.waitForElement(deferrals, "My Contributions");
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferral_005_Catch_up_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_004_Catch_up_Select_another_contribution_rate(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_SelectAnotherContribution_Split(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_SelectAnotherContribution_Roth(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_MaximizeToIRSLimit_Roth(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_MaximizeToIRSLimit_Split(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_006_Previous_Contributions_Participant_hired_in_prior_year(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if (homePage.getNoOfPlansFromDB(lib.Stock
					.GetParameterValue("Particicpant_ssn")) <= 2)
				leftmenu = new LeftNavigationBar(homePage);
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);
			priorContributions.get();

			if (priorContributions.verifyParticipantsHiredInPriorYear())
				Reporter.logEvent(Status.PASS,
						"Verify if Participant was hired in previous year",
						"Participant hired in previous year", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Participant was hired in previous year",
						"Participant not hired in previous year", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Thee following scripts test for the prior contribution details of the
	 * participants that are in the following category 1. Participant in the
	 * first year of joining the plan or first year of the employment 2.
	 * Participant in the first year of catchup contribution 3. Participant who
	 * is not in the first year of employment
	 * 
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_008_Prior Plan
	 * Contributions_Participant not in first year of employment
	 * 2.SIT_PPTWEB_Deferral_007_Prior Plan Contributions_Catchup - participant
	 * within first year of employment 3.SIT_PPTWEB_Deferral_006_Previous
	 * Contributions_Participant hired in current year
	 */

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			// logic to verify the prior plan contribution functionality with 3
			// combination of manual test cases listed above in comments
			// if
			// (Stock.GetParameterValue("Period_of_Participation").equalsIgnoreCase("Not_In_First_Year"))
			// {
			//
			// if
			// (Web.isWebElementDisplayed(leftmenu,"Prior plan contributions"
			// )) {
			// Reporter.logEvent(Status.FAIL,
			// "Prior plan contributions link visibility",
			// "participant is unable to find the Prior plan contributions link since the participant is not in his first year of employment",
			// true);
			// } else {
			// Reporter.logEvent(Status.PASS,
			// "Prior plan contributions link visibility",
			// "participant is able to find the Prior plan contributions link even though the participant is not in his first year of employment. Please check the test data",
			// true);
			// }
			// } else {
			// Navigate to the prior plan contribution
			priorContributions.get();

			if (Stock.GetParameterValue("Period_of_Participation")
					.equalsIgnoreCase("Not_In_First_Year")) {

				if (Web.isWebElementDisplayed(leftmenu,
						"Prior plan contributions")) {
					Reporter.logEvent(
							Status.FAIL,
							"Prior plan contributions link visibility",
							"participant is unable to find the Prior plan contributions link since the participant is not in his first year of employment",
							true);
				} else {
					Reporter.logEvent(
							Status.PASS,
							"Prior plan contributions link visibility",
							"participant is able to find the Prior plan contributions link even though the participant is not in his first year of employment. Please check the test data",
							true);
				}
			} else {
				// perform a initial check on the prior plan contribution
				priorContributions.verifyPriorPlanContributionsPage();
				// enter the year to date and catchup contribution value
				priorContributions.enterContributionValue(
						Stock.GetParameterValue("yearToDateContribution"),
						Stock.GetParameterValue("catchupContribution"));
				priorContributions.verifyContributionMessage(lib.Stock
						.GetParameterValue("yearToDateContribution"));
				
				// verify the values for the year to date and catchup
				// contribution value on the confirmtion page
				if (priorContributions.verifyContributionMessage(lib.Stock
						.GetParameterValue("yearToDateContribution"))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify catchup Contribution Confirmation Detials verification",
							"Test data for catchup Contribution value matched with test data on the confirmation page",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify catchup Contribution Confirmation Detials verification",
							"Test data for catchup Contribution value DID NOT matched with test data on the confirmation page",
							true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Thee following scripts Add Other deferral type
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_009_Other_Select another
	 * contribution rate
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Other(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Other contribution page",
						"Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Other contribution page",
						"Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			

			if (Stock.GetParameterValue("Contribution_type")
					.equalsIgnoreCase("Split"))
				deferrals.split_Other_contribution();
			else
				deferrals.select_ContributionType(lib.Stock
						.GetParameterValue("Contribution_type"));

			deferrals.add_Auto_Increase(Stock
					.GetParameterValue("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Other_SelectAnotherContribution_split(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Other_SelectAnotherContribution_Catchup_Roth(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Deferral_009_Other_Select_another_contribution_rate(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}
	
	/**
	 * Thee following script checks if the participant is available for Bonus
	 * type deferral
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_003_Bonus_Participant
	 * not eligible for Bonus type contribution
	 */
	@Test(dataProvider = "setData")
	public void Participant_not_elegible_for_Bonus_type_contribution(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);150551
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if(Web.isWebElementDisplayed(myAccountPage,"PLAN NAME", true)) { 
				 myAccountPage.clickPlanNameByGAID("150551-01"); 
							  }
			leftmenu = new LeftNavigationBar(myAccountPage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (!deferrals
					.check_if_participant_eligible_for_deferral_type("Bonus"))
				Reporter.logEvent(
						Status.PASS,
						"Check if Participant eligible for bonus type contribution",
						"Participant is not eligible for bonus type contribution",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check if Participant eligible for bonus type contribution",
						"Participant eligible for bonus type contribution",
						true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * The participant selects the Maximize me always option for Standard
	 * deferral type
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_020_Regular_SPLIT-
	 * Change of Maximized with Catchup to Maximize me always
	 */
	@Test(dataProvider = "setData")
	public void Regular_SPLIT_Maximize_me_always(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution",true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);

			deferrals.click_Maximize_IRS_Limit();
			/**
			 * This step is not applicable as new functionalitychange
			 */

			/*if (Web.isWebElementDisplayed(deferrals, "Maximize Checkbox"))
				Reporter.logEvent(Status.PASS,
						"Check if Maximize me always check box is present",
						"Maximize me always check box is present", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Check if Maximize me always check box is present",
						"Maximize me always check box is not present", true);
*/
			deferrals.regular_maximize_me_always("Yes");

			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_003_View_only_Standard_with_changes_allowed_deferral(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			boolean isTextMatching = false;
			Thread.sleep(5000);
			String perRoth = deferrals
					.getContributionPercentage(Stock.GetParameterValue("Roth"));//RTH
			String perBeforeTax = deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"));//BFRTX

			String contributionRate = Stock
					.GetParameterValue("Contribution Rate");

			deferrals.View_only_Standard_with_changes(contributionRate);
			contributionRate = deferrals.contrbution_rate;
           Thread.sleep(5000);
			int cr = Integer.parseInt(contributionRate.split("%")[0]);
			int bft = Integer.parseInt(perBeforeTax.split("%")[0]);
			String newPerRoth = Integer.toString(cr - bft);
			isTextMatching = Web.VerifyText(newPerRoth
					+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
					+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX"), deferrals
					.getWebElementText("VIEW ONLY SPLIT CONTRIBUTION TEXT")
					.trim(), true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page \nExpected:"+newPerRoth
										+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
										+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX")+"\nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is not Same on Select Contribution page \nExpected:"+newPerRoth
										+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
										+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX")+"\nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT").trim(), true);
			}
			Web.clickOnElement(deferrals, "Continue button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "Confirm button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			isTextMatching = Web.VerifyText(perRoth ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("Roth")),true);
			if (!isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Standard Roth After Split Contribution on My Contribution page",
								"Roth is updated on My Contribution page \nExpected:"
										+ newPerRoth
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("Roth"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Standard Roth After Split Contribution on My Contribution page",
								"Roth is not updated on My Contribution page \nExpected:"
										+ newPerRoth
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("Roth"))
										.trim(),
								true);
			}
			isTextMatching = Web.VerifyText(perBeforeTax ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX")),true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Standard Before Tax After Split Contribution on My Contribution page",
								"Before Tax is Not updated on My Contribution page \nExpected:"
										+ perBeforeTax
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Standard Before Tax After Split Contribution on My Contribution page",
								"Standard Before Tax is updated on My Contribution page \nExpected:"
										+ perBeforeTax
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"))
										.trim(),
								true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_002_View_only_After_tax_with_no_split_contributions(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (deferrals.checkAftertaxOptionNotdisplayed()) {
				Reporter.logEvent(
						Status.FAIL,
						"Check if after tax option is displayed in contributions page",
						"The after tax option is displayed", true);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Check if after tax option is displayed in contributions page",
						"The after tax option is not displayed", true);
			}

			if (Web.isWebElementDisplayed(deferrals, "Edit Btn Aftertax")) {
				Reporter.logEvent(
						Status.FAIL,
						"Check if edit button is displayed for after tax with no split contributions",
						"The edit button is displayed for after tax with no split contributions",
						true);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Check if edit button is displayed for after tax with no split contributions",
						"The edit button is not displayed for after tax with no split contributions",
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_001_View_only_Catchup_with_split_contributions(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			boolean isTextMatching = false;
			Thread.sleep(5000);
			String perAgeRothCatchUp = deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"));//Age roth catch up
			String perAgeCatchupBefore = deferrals
					.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"));//Age Catch- up before

			String contributionRate = Stock
					.GetParameterValue("Contribution Rate");

			deferrals.Catchup_with_split_contributions(contributionRate);
			contributionRate = deferrals.contrbution_rate;
            Thread.sleep(5000);
			int cr = Integer.parseInt(contributionRate.split("%")[0]);
			int acr = Integer.parseInt(perAgeRothCatchUp.split("%")[0]);
			String newPerAgeCatchupBefore = Integer.toString(cr - acr);
			isTextMatching = Web.VerifyText(newPerAgeCatchupBefore
					+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
					+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth"), deferrals
					.getWebElementText("VIEW ONLY SPLIT CONTRIBUTION TEXT")
					.trim(), true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page \nExpected:"
										+newPerAgeCatchupBefore
										+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
										+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth")+" \nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page. \nExpected:"
										+newPerAgeCatchupBefore
										+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
										+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth")+" \n Actual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(), true);
			}
			Web.clickOnElement(deferrals, "Continue button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "Confirm button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			isTextMatching = Web.VerifyText(perAgeCatchupBefore ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax")),true);
			if (!isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Age Catch- up before After Split Contribution on My Contribution page",
								"Age Catch- up before is updated on My Contribution page \nExpected"
										+ newPerAgeCatchupBefore
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Age Catch- up before After Split Contribution on My Contribution page",
								"Age Catch- up before is updated on My Contribution page \nExpected"
										+ newPerAgeCatchupBefore
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"))
										.trim(),
								true);
			}
			isTextMatching = Web.VerifyText(perAgeRothCatchUp ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyroth")),true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Age Catch- up Roth After Split Contribution on My Contribution page",
								"Age Catch- up Age roth catch up is Not updated on My Contribution page \nExpected:"
										+ perAgeRothCatchUp
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,
								"Verify Age Catch- up Roth After Split Contribution on My Contribution page",
								"Age Catch- up Age roth catch up is updated on My Contribution page \nExpected:"
										+ perAgeRothCatchUp
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"))
										.trim(),
								true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_020_Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();

//			if (deferrals.getCatchupMaximized() == null) {
//				deferrals.clickAddEditButton("Catch Up Add");
//				deferrals.click_Maximize_IRS_Limit();
//				deferrals.select_ContributionType("Before");
//				deferrals.myContributions_Confirmation_Page();
//				Web.clickOnElement(deferrals, "MyContribution Button");
//			}
			deferrals
					.Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always();

			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_006_Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals
					.Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer();

			Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_007_Catch_up_Salary_changes_on_IRS_panel(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals.Catch_up_Salary_changes_on_IRS_panel();

			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_008_Catch_up_Maximize_me_always(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals.Catch_up_Maximize_me_always();
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@AfterSuite
	public void cleanupSessions() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}

	/**
	 * Thee following scripts test for the prior contribution details of the
	 * participants that are in the following category 1. Participant in the
	 * first year of joining the plan or first year of the employment 2.
	 * Participant in the first year of catchup contribution 3. Participant who
	 * is not in the first year of employment
	 * 
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_008_Prior Plan
	 * Contributions_Participant not in first year of employment
	 * 2.SIT_PPTWEB_Deferral_007_Prior Plan Contributions_Catchup - participant
	 * within first year of employment 3.SIT_PPTWEB_Deferral_006_Previous
	 * Contributions_Participant hired in current year
	 */

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions6(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			Thread.sleep(5000);

			if (Web.isWebElementDisplayed(priorContributions, "EDIT")) {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.waitForElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "EDIT");
			}

			// perform a initial check on the prior plan contribution
			priorContributions.verifyPriorPlanContributionsPage();
			Web.clickOnElement(priorContributions, "CANCEL");
			Web.clickOnElement(priorContributions, "YES");
			// Web.VerifyText(inExpectedText, inActualText, ignoreCase)
			// enter the year to date and catchup contribution value
			priorContributions.enterContributionValue(
					"YEAR TO DATE CONTRIBUTION",
					Stock.GetParameterValue("yearToDateContribution"));
			Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
			priorContributions.verifyContributionMessage(lib.Stock
					.GetParameterValue("yearToDateContribution"));
			

			if (Web.isWebElementDisplayed(priorContributions, "Edit"))
				Reporter.logEvent(Status.PASS, "Verify Edit button",
						"Edit button is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Edit button",
						"Edit button is Not displayed", true);
			// this will clear thedata
			try {
				Thread.sleep(4000);
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.clickOnElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "LOGOUT");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions7(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			//Web.waitForElement(priorContributions, "EDIT");
			Thread.sleep(5000);
			/*if (Web.isWebElementDisplayed(priorContributions, "EDIT")) {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.waitForElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "EDIT");
			}*/

			// perform a initial check on the prior plan contribution
			priorContributions.verifyPriorPlanContributionsPage();
			Web.clickOnElement(priorContributions, "CANCEL");
			Web.clickOnElement(priorContributions, "YES");
			// Web.VerifyText(inExpectedText, inActualText, ignoreCase)
			// enter the year to date and catchup contribution value
			priorContributions.enterContributionValue(
					"YEAR TO DATE CONTRIBUTION",
					Stock.GetParameterValue("yearToDateContribution"));
			priorContributions.enterContributionValue("CATCHUP CONTRIBUTION",
					Stock.GetParameterValue("catchupContribution"));
			Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
		boolean updated=	priorContributions.verifyContributionMessage(lib.Stock
					.GetParameterValue("totalContribution"));
			
			if (updated) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Year to Date Confirmation Detials Updated",
						"Year to Date Confirmation Detials Updated",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Year to Date Confirmation Detials Updated",
						"Year to Date Confirmation Detials Not Updated",
						true);
			}

			if (Web.isWebElementDisplayed(priorContributions, "Edit"))
				Reporter.logEvent(Status.PASS, "Verify Edit button",
						"Edit button is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Edit button",
						"Edit button is Not displayed", true);
			// this will clear thedata
			try {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.clickOnElement(priorContributions, "EDIT");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions8(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			PriorPlanContributions priorContributions=new PriorPlanContributions();
			if (!Web.isWebElementDisplayed(priorContributions,
					"TEXT PRIOR PLAN CONTRIBUTION"))
				Reporter.logEvent(Status.PASS,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions9_Plan_not_set_for_PPC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			PriorPlanContributions priorContributions=new PriorPlanContributions();
			if (!Web.isWebElementDisplayed(priorContributions,
					"TEXT PRIOR PLAN CONTRIBUTION"))
				Reporter.logEvent(Status.PASS,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferrals_Standard_Contribution_with_Chaining(int itr,
			Map<String, String> testdata) throws Exception {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);

			deferrals.get();
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType(Stock.GetParameterValue("Contributing_type"));
			if(Web.isWebElementDisplayed(deferrals, "Confirm button", true))
				Reporter.logEvent(Status.PASS,"Verify Shopping cart  page is displayed","Shopping cart  page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Shopping cart  page is displayed","Shopping cart  page not displayed", true);
			deferrals.chainingForStandardContribution(Stock.GetParameterValue("Chaining_Option"));
			deferrals.verifyChainingConfirmationPage(Stock.GetParameterValue("Chaining_Option"), Stock.GetParameterValue("Plan_id"));

			Web.clickOnElement(deferrals,"MyContribution Button");
			Reporter.logEvent(Status.PASS,"Verify My Contributions button is clicked","Clicked n My Contribution button", false);
			
			if (deferrals.verifyMyContributions(new DecimalFormat("##.##").format(deferrals.before_tax), "Before-tax", "Standard"))
				Reporter.logEvent(Status.PASS,"Verify Before contribution percent for Standard deferral","Before contribution percent matching", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Before contribution percent for Standar deferral","Before contribution percent matching", true);
			
			deferrals.verifyChainingMessage(Stock.GetParameterValue("Chaining_Option"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			Deferrals deferral=new Deferrals();
			deferral.deleteActiveChainingFromDB(Stock.GetParameterValue("SSN"),Stock.GetParameterValue("first_name"));
			deferral.refresh();
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_012M_Standard_contribution_with_chaining_to_Aftertax(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_013M_Standard_contribution_with_chaining_to_Aftertax_to_NonQual(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_014M_Standard_contribution_with_chaining_to_NonQua(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	/**
	 * The following script adds or edits all the deferral type and then confirm
	 * it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_020_Multiple_Create
	 * contribution rate for multiple deferral type, split contribution
	 * 2.SIT_PPTWEB_Deferral_021_Multiple_Change contribution rate for multiple
	 * deferral type, split contributions
	 */
	@Test(dataProvider = "setData")
	public void Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (Web.isWebElementDisplayed(deferrals,"Table Header Contribution",true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page","My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page","My Contributions page is not displayed", true);
			
			if(Stock.GetParameterValue("create_or_edit").equalsIgnoreCase("create")){
				
				//Addind Regular Deferral and split
				if (deferrals.clickAddEditButton("Standard Add"))
					Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.select_ContributionType("Split");
				
				
				//Adding Catch up deferral and split
				if (deferrals.clickAddEditButton("Catch Up Add"))
					Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_catchup_contribution();
				
				//Adding After-Tax deferral
				if (deferrals.clickAddEditButton("After Tax Add"))
					Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				Web.clickOnElement(deferrals, "Continue button");
				
				//Adding Bonus defferal and split
				/*if (deferrals.clickAddEditButton("Bonus Add"))
					Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_bonus_contribution();*/
				
				//Adding Other deferral and split
				if (deferrals.clickAddEditButton("Other Add"))
					Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_Other_contribution();
			}
			
			if(Stock.GetParameterValue("create_or_edit").equalsIgnoreCase("edit")){
				
				//Addind Regular Deferral and split
				if (deferrals.clickAddEditButton("Standard Edit"))
					Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.select_ContributionType("Split");
				
				
				//Adding Catch up deferral and split
				if (deferrals.clickAddEditButton("Catch Up Edit"))
					Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_catchup_contribution();
				
				//Adding After-Tax deferral
				if (deferrals.clickAddEditButton("After Tax Edit"))
					Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				Web.clickOnElement(deferrals, "Continue button");
				
				//Adding Bonus defferal and split
				/*if (deferrals.clickAddEditButton("Bonus Edit"))
					Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_bonus_contribution();
				*/
				//Adding Other deferral and split
				if (deferrals.clickAddEditButton("Other Edit"))
					Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_Other_contribution();
			}

				//add auto increase
				deferrals.add_Auto_Increase("Before Add Auto Increase");
				deferrals.add_Auto_Increase("Roth Add auto Increase");
				deferrals.add_Auto_Increase("After Add Auto Increase");
				deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
				deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
				//deferrals.add_Auto_Increase("Roth Bonus Add Auto Increase");
				//deferrals.add_Auto_Increase("Before Bonus Add Auto Increase");
				
				//Confirm all the deferrals
				deferrals.myContributions_Confirmation_Page();

				Web.clickOnElement(deferrals, "MyContribution Button");
				if (Web.isWebElementDisplayed(deferrals,
						"Table Header Contribution", true))
					Reporter.logEvent(Status.PASS, "Verify My Contributions page",
							"My Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
							"My Contributions page is not displayed", true);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void Create_Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {
		Multiple_deferral_split_contribution(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Edit_Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {
		Multiple_deferral_split_contribution(itr, testdata);
	}
	
	/**
	 * The following script Standard Deferral and confirms it and Verify Plan rules
	 * 
	 * Covered Manual Test Cases: SIT_PPTWEB_Deferral_010M_Verify combined rules on 
	 * deferral types with election greater than zero
	 */
	@Test(dataProvider = "setData")
	public void Deferral_010M_Verify_combined_rules_with_election_greater_than_zero(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			

			Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			Web.clickOnElement(deferrals, "Continue button");
			Web.clickOnElement(deferrals, "PLAN RULE");
			Web.waitForElement(deferrals, "Header PlanRule");

			if (Web.isWebElementDisplayed(deferrals,
					"Header PlanRule"))
				Reporter.logEvent(Status.PASS, "Verify Pan Rule PopUp is Displayed",
						"Pan Rule PopUp is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pan Rule PopUp is Displayed",
						"Pan Rule PopUp is Not Displayed", true);
			
			if (Web.isWebElementDisplayed(deferrals,
					"Text PlanRule"))
				Reporter.logEvent(Status.PASS, "Verify Pan Rule Text is Displayed and Proper",
						"Pan Rule Text is Displayed and Proper", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pan Rule Text is Displayed and Proper",
						"Pan Rule Text is Not Proper", true);
			Web.clickOnElement(deferrals, "OK BUTTON");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * The following script Standard Deferral and confirms it and Verify Plan rules
	 * 
	 * Covered Manual Test Cases: SIT_PPTWEB_Deferral_011M_Verify combined rules 
	 * on deferral types with zero election
	 */
	@Test(dataProvider = "setData")
	public void Deferral_011M_Verify_combined_rules_with_zero_election(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			LeftNavigationBar	leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions Page is Displayed and combined rules a1re not checked on deferral types ",
						"My Contributions page is  displayed and combined rules are not checked on deferral types ", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions Page is Displayed and combined rules a1re not checked on deferral types ",
						"My Contributions page is not displayed and combined rules are checked on deferral types ", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following scriptStandard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_021_Regular_SPLIT- Company match suppressed
	 */
	@Test(dataProvider = "setData")
	public void Deferral_021_Regular_SPLIT_Company_match_suppressed(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3
			deferrals.verifyCompanyMatchChangesDynamically();
			//Step4
			// TODO
			//Step 5
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 6 & 7
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			//Step 9 10 11 12 13
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_Roth"));
			//Step 14
			deferrals.myContributions_Confirmation_Page();
			//Step 15
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following scriptStandard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_022_Regular_SPLIT- Company match
	 */
	@Test(dataProvider = "setData")
	public void Deferral_022_Regular_SPLIT_Company_match(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			//Step4
			
			// TODO
			
			//Step 5
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 6 & 7
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			//Step 9 10 11 12 13
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_Roth"));
			//Step 14
			deferrals.myContributions_Confirmation_Page();
			//Step 15
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_025_Regular_BEFORE-Select Another contribution rate_subcode ADJRUNDATE or GENERIC
	 */
	@Test(dataProvider = "setData")
	public void Deferral_025_Regular_BEFORE_Select_Another_contribution_rate_subcode_ADJRUNDATE_GENERIC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			//Update date picker to calendar
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_PlanId"));
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_026_Regular_SPLIT- Maximizer to custom with same contribution rate
	 */
	@Test(dataProvider = "setData")
	public void Deferral_026_Regular_SPLIT_Maximizer_to_custom_with_same_contribution_rate(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Precondition
			
			if (deferrals.clickAddEditButton("Standard Add"))
				
			deferrals.click_Maximize_IRS_Limit();
		
			deferrals.select_ContributionType("split");
			
			deferrals.myContributions_Confirmation_Page();

			Web.clickOnElement(deferrals, "MyContribution Button");
			
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 
			Web.clickOnElement(deferrals, "RADIO SELECT OTHER CONTRIBUTION");
			
			//Step 4
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step5
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 6
			deferrals.myContributions_Confirmation_Page();
		
			//Step 7
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_027_Multiple_No_changes_in_the_rate
	 */
	@Test(dataProvider = "setData")
	public void Deferral_027_Multiple_No_changes_in_the_rate(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
				//Step 1			
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			//PreCondition
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("Split");
			
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_catchup_contribution();
			
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			Web.clickOnElement(deferrals, "Continue button");
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_Other_contribution();
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();

			Web.clickOnElement(deferrals, "MyContribution Button");
			
			
			//Step 2
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 5 & 6
			deferrals.select_ContributionType("Split");
			//Step 7
			//Editing After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Editing Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Edit"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_catchup_contribution();
			
			//Editing Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Edit"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();
			*/
			//Editing Other deferral and split
			if (deferrals.clickAddEditButton("Other Edit"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_Other_contribution();
		
			//Step 9 & 10
			//add auto increase
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			//deferrals.add_Auto_Increase("Roth Bonus Add Auto Increase");
			//deferrals.add_Auto_Increase("Before Bonus Add Auto Increase");
			
			//Confirm all the deferrals
			/*deferrals.myContributions_Confirmation_Page();

			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);*/
			
			//Step 11
			deferrals.clickAddEditButton("Standard Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.select_ContributionType("Split");
	
			deferrals.clickAddEditButton("After Tax Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			deferrals.clickAddEditButton("Catch Up Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_catchup_contribution();
			
			/*deferrals.clickAddEditButton("Bonus Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_bonus_contribution();*/
			
			deferrals.clickAddEditButton("Other Edit");
				
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_Other_contribution();
			
			//Confirm all the deferrals
			
			if (!Web.isWebElementDisplayed(deferrals,
					"Confirm button"))
				Reporter.logEvent(Status.PASS, "Verify 'Confirm And Continue' Button is Not Displayed",
						"'Confirm And Continue' Button is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Confirm And Continue' Button is Not Displayed",
						"'Confirm And Continue' Button is Displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_029_Multiple_Change contribution rate for multiple deferral type, split contribution - Dollar deferrals
	 */
	@Test(dataProvider = "setData")
	public void Deferral_029_Multiple_Change_Dollar_deferrals(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			//Step 1			
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Step 2
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			
			//Step 3
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			//Step 4 & 5 & 6
			deferrals.select_ContributionType("Split");
			
			//Step 7
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Edit"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_catchup_contribution();
			
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Edit"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_Other_contribution();
			
			//Step 9 & 10
			
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			
			//Step 11
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();
			
			
			
			deferrals.verifyDollarInConfirmationPage();
			
			//Step 12
			
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_028_Multiple_Create contribution rate for multiple deferral type, split contribution - Dollar deferrals
	 */
	@Test(dataProvider = "setData")
	public void Deferral_028_Multiple_Create_Dollar_deferrals(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			//Step 1			
			Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Step 2
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			
			//Step 3
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			//Step 4 & 5 & 6
			deferrals.select_ContributionType("Split");
			
			//Step 7
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_catchup_contribution();
			
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_Other_contribution();
			
			//Step 9 & 10
			
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			
			//Step 11
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();
			
			//Step 12
			
			deferrals.verifyDollarInConfirmationPage();
			
			//Step 13
			
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_030_Regular_BEFORE-Update auto increase when payroll calendar ADJRUNDATE
	 */
	@Test(dataProvider = "setData")
	public void Deferral_030_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_ADJRUNDATE(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to dropdown
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase_Date_DropDown(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_031_Regular_BEFORE-Update auto increase when payroll calendar GENERIC
	 */
	@Test(dataProvider = "setData")
	public void Deferral_031_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_GENERIC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to Calendar
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_032_Regular_BEFORE-Update auto increase when payroll calendar ADJRUN_PAYROLLDATE
	 */
	@Test(dataProvider = "setData")
	public void Deferral_032_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_ADJRUN_PAYROLLDATE(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to dropdown
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
						
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase_Date_DropDown("Before Add Auto Increase");
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.Deferral_003A_Nonvisible Bonus deferral not displayed on confirm page
	 */
	@Test(dataProvider = "setData")
	public void Deferral_003A_Nonvisible_Bonus_deferral_not_displayed_on_confirm_page(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 12
			deferrals.myContributions_Confirmation_Page();
			
			if (!deferrals.verifyContributionTypeIsDisplayedInConfirmationPage("Bonus"))
				Reporter.logEvent(Status.PASS, "Verify 'Bonus' is Displayed in Confirmation page",
						"'Bonus' is Not Displayed in Confirmation page", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Bonus' is Displayed in Confirmation page",
						"'Bonus' is Displayed in Confirmation page", true);
			
			//Step 13
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Verify Pending Deferral
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_035_Pending Deferral View
	 */
	@Test(dataProvider = "setData")
	public void Deferral_035_Pending_Deferral_View(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Precondition
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			
			Web.clickOnElement(deferrals, "Continue button");
			
			deferrals.myContributions_Confirmation_Page();
			
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			//update DB effective date to get pending deferral
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, 1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			//DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			deferrals.ClickPendingDeferralLink(Stock.GetParameterValue("DeferralType"));
			deferrals.verifyPendingDeferralModal(Stock.GetParameterValue("DeferralType"));
			Web.clickOnElement(deferrals, "Button Close Pending Deferral");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar calendar = Calendar.getInstance();         
				String date=dateFormat.format(calendar.getTime());
				System.out.println("DATE"+date);
				String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
				Web.getDriver().navigate().refresh();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * The following script Verify Pending Deferral
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_036_Pending_Deferral_Delete
	 */
	@Test(dataProvider = "setData")
	public void Deferral_036_Pending_Deferral_Delete(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Precondition
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			
			Web.clickOnElement(deferrals, "Continue button");
			
			deferrals.myContributions_Confirmation_Page();
			
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			//update DB effective date to get pending deferral
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, 1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
		//	DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 2
			deferrals.ClickPendingDeferralLink(Stock.GetParameterValue("DeferralType"));
			deferrals.verifyPendingDeferralModal(Stock.GetParameterValue("DeferralType"));
			
			//Step 3;
			Web.clickOnElement(deferrals, "Button Delete Pending Deferral");
			Web.waitForElement(deferrals, "Modal Delete Deferral");
			if (Web.isWebElementDisplayed(deferrals,
					"Modal Delete Deferral", true))
				Reporter.logEvent(Status.PASS, "Verify Delete Deferral Modal is Displayed",
						"Delete Deferral Modal is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Delete Deferral Modal is Displayed",
						"Delete Deferral Modal is not displayed", true);
			//Step 4
			Web.clickOnElement(deferrals, "Delete Pending Deferral");
			Web.waitForElement(deferrals, "Confirm button");
			if(!deferrals.verifyPendingDeferralLinkisDisplayed(Stock.GetParameterValue("DeferralType")))
				Reporter.logEvent(Status.PASS, "Verify Pending Deferral Link Not is Displayed",
						"Pending Deferral Link Not is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pending Deferral Link Not is Displayed",
						"Pending Deferral Link is Displayed", true);
			
			if(Web.isWebElementDisplayed(deferrals, "Confirm button", true))
				Reporter.logEvent(Status.PASS, "Verify 'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Not Displayed", true);
			//Step 5
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyDeletedDeferralinConfirmationPage(Stock.GetParameterValue("DeferralType"));
			//Step 6
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
			//step 7
			String statusCode=deferrals.getPendingDeferralStatusCodeFromDB(Stock.GetParameterValue("getStatusCode"));
			
			if(Web.VerifyText("C", statusCode, true))				
				Reporter.logEvent(Status.PASS, "Verify Pending Deferral Status in data Base",
						"Pending Deferral Status is 'Canceled'", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pending Deferral Status in data Base",
						"Pending Deferral Status is Not 'Canceled'", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_037_Regular_BEFORE-Select Another contribution rate with save change popup modal
	 */
	@Test(dataProvider = "setData")
	public void Deferral_037_Regular_BEFORE_Select_Another_Contributionrate_With_Save_Change_Popup_Modal(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
						
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			if(Web.isWebElementDisplayed(deferrals, "Confirm button", true))
				Reporter.logEvent(Status.PASS, "Verify 'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Not Displayed", true);
			//Step 11 
			
			leftmenu.clickNavigationLink("Beneficiaries");
			if (Web.isWebElementDisplayed(deferrals,
					"Modal Save Changes", true))
				Reporter.logEvent(Status.PASS, "Verify 'Save Changes' Modal Is Displayed",
						"'Save Changes' Modal Is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Save Changes' Modal Is Displayed",
						"'Save Changes' Modal Is Not Displayed", true);
			
			//Step 12
				deferrals.verifySaveYourChangesModal();
			
			//Step 13
			Web.clickOnElement(deferrals, "Button Stay");
			if(Web.isWebElementDisplayed(deferrals, "Confirm button", true))
				Reporter.logEvent(Status.PASS, "Verify 'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify'Confirm & Continue' Button is Displayed",
						"'Confirm & Continue' Button is Not Displayed", true);
			//Step 14
			leftmenu.clickNavigationLink("Beneficiaries");
			if (Web.isWebElementDisplayed(deferrals,
					"Modal Save Changes", true))
				Reporter.logEvent(Status.PASS, "Verify 'Save Changes' Modal Is Displayed",
						"'Save Changes' Modal Is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Save Changes' Modal Is Displayed",
						"'Save Changes' Modal Is Not Displayed", true);
			
			
			//step 15
			Web.clickOnElement(deferrals, "Button Dont Save");
			Web.waitForElement(deferrals, "Label Beneficiary");
			
			if (Web.isWebElementDisplayed(deferrals,
					"Label Beneficiary", true))
				Reporter.logEvent(Status.PASS, "Verify 'Beneficiary' Page Is Displayed",
						"'Beneficiary' Page Is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Beneficiary' Page Is Displayed",
						"'Beneficiary' Page Is Not Displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Thee following scripts test for the prior contribution details of the
	 * participants that are in the following category 1. Participant in the
	 * first year of joining the plan or first year of the employment 
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_Deferral_005M_Prior Plan Contributions_ No Left hand Nav link
	 */

	@Test(dataProvider = "setData")
	public void Deferral_005M_Prior_Plan_Contributions_No_Left_hand_Nav_link(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			leftmenu.get();
			Thread.sleep(5000);
			//Step 2
			if(!leftmenu.isLeftNavigationLinkDisplayed("PRIOR PLAN CONTRIBUTIONS"))
				Reporter.logEvent(Status.PASS, "Verify 'PRIOR PLAN CONTRIBUTIONS' Link Is Displayed in Left Nav Bar",
				"'PRIOR PLAN CONTRIBUTIONS' Link Is Not Displayed in Left Nav Bar", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'PRIOR PLAN CONTRIBUTIONS' Link Is Displayed in Left Nav Bar",
				"'PRIOR PLAN CONTRIBUTIONS' Link Is Displayed in Left Nav Bar", true);
		
			PriorPlanContributions priorContributions = new PriorPlanContributions(
				leftmenu);

			priorContributions.get();
			
			// Verify Participant with first year of the employment 
			//able to see prior plan contribution page with out having 
			//prior plan contribution link in Left Nav Bar .
			priorContributions.verifyPriorPlanContributionsPage();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * The following script Standard Deferral for combined Rules  
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_017M_Combined_rules_error_message_when_minimum_not_met
	 */
	@SuppressWarnings("null")
	@Test(dataProvider = "setData")
	public void Deferral_017M_Combined_rules_error_message_when_minimum_not_met(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 4
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 5
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.click_Select_Your_Contribution_Rate();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			//Step 10
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11
			
			deferrals.verifyCombinedRuleErrorMessage(Stock.GetParameterValue("RuleType"),Stock.GetParameterValue("MinimumLimit%"),Stock.GetParameterValue("deferralType1"),Stock.GetParameterValue("deferralType2"));
			//Step 12
			
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.select_Another_Contribution_Rate_Dollar();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			//Step 10
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11
			
			
			deferrals.verifyCombinedRuleErrorMessage(Stock.GetParameterValue("RuleType"),Stock.GetParameterValue("MinimumLimit$"),Stock.GetParameterValue("deferralType1"),Stock.GetParameterValue("deferralType2"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral for combined Rules  
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_018M_Combined_rules_error_message_when_maximum_is_breached
	 */
	@Test(dataProvider = "setData")
	public void Deferral_018M_Combined_rules_error_message_when_maximum_is_breached(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 4
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 5
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.click_Select_Your_Contribution_Rate();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			//Step 10
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11
			
			deferrals.verifyCombinedRuleErrorMessage(Stock.GetParameterValue("RuleType"),Stock.GetParameterValue("MaximumLimit%"),Stock.GetParameterValue("deferralType1"),Stock.GetParameterValue("deferralType2"));
			//Step 12
			
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.select_Another_Contribution_Rate_Dollar();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			//Step 10
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11
			
			deferrals.verifyCombinedRuleErrorMessage(Stock.GetParameterValue("RuleType"),Stock.GetParameterValue("MaximumLimit$"),Stock.GetParameterValue("deferralType1"),Stock.GetParameterValue("deferralType2"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral for combined Rules  
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_019M_Combined_rules_error_message_when_bott_dollar_and_percent_are_added
	 */
	@Test(dataProvider = "setData")
	public void Deferral_019M_Combined_rules_error_message_when_both_dollar_and_percent_are_added(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 4
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 5
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.click_Select_Your_Contribution_Rate();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			//Step 10
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11
			Web.waitForElement(deferrals, "Text Combined Rule Error Message");
			String actualErrorMessage=deferrals.getWebElementText("Text Combined Rule Error Message");
			String expectedErrorMsg=Stock.GetParameterValue("ExpectedErrorMsg");
			if (Web.VerifyText(expectedErrorMsg, actualErrorMessage))
				Reporter.logEvent(Status.PASS, "Verify Error Message for Combined Rule",
						"Error Message for Combined Rule is Displayed and Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMessage, true);
						else
				Reporter.logEvent(Status.FAIL, "Verify Error Message for Combined Rule",
						"Error Message for Maximum Combined Rule is Displayed and Not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+actualErrorMessage, true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral for Tired Rules When Minimum Not Met
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_020M_Tiered_rules_error_message_when_minimum_not_met
	 */
	@Test(dataProvider = "setData")
	public void Deferral_020M_Tiered_rules_error_message_when_minimum_not_met(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 4
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 5
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 6
			deferrals.click_Select_Your_Contribution_Rate();
			//Step  7 & 8
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 9
			
			deferrals.verifyTieredRuleErrorMessage( Stock.GetParameterValue("tieredLimitValue%"), 
					Stock.GetParameterValue("contributingDeferralType"),  
					Stock.GetParameterValue("requiredDeferral"), 
					Stock.GetParameterValue("contributingDeferral"));
			//Step 10
			
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			
			Web.clickOnElement(deferrals, "Continue button");
			
			
			deferrals.verifyTieredRuleErrorMessage( Stock.GetParameterValue("tieredLimitValue$"), 
					Stock.GetParameterValue("contributingDeferralType"),  
					Stock.GetParameterValue("requiredDeferral"), 
					Stock.GetParameterValue("contributingDeferral"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * PreCondition-Need Participant Enrolled Under a Plan which is tied with RULEA
	 * The following scriptStandard Deferral and Verify Company Match For RULEA
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_021M_Company Match RULEA
	 */
	@Test(dataProvider = "setData")
	public void Deferral_021M_Company_Match_RULEA(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.YEAR, -1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("QueryUpdateEmployeeHireDate"));
		
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 5
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 6
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 7
			
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			
			String actualCompanyMatchRule=deferrals.getCompanyMatchRuleDescription(Stock.GetParameterValue("minYearsOfService"));
			
			if(Web.VerifyText(Stock.GetParameterValue("ExpectedRuleDescription"), actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Not Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);

			//Step 8
			//0-2 Yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService<2")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentagefor<2")));
			//2-5 yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService<5")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentagefor<5")));
			//Above 5 yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService>5")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentagefor>5")));
			
			deferrals.verifyCompanyMatchChangesDynamically();
			
		//Step 9
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			if(!deferrals.verifyCompanyMatchDisplayed())
				
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Not Displayed",
						"COMPANY MATCH is Not displayed", true);
			}
			catch(NoSuchElementException e){
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Not Displayed",
						"COMPANY MATCH is displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * PreCondition-Need Participant Enrolled Under a Plan which is tied with RULE4
	 * The following scriptStandard Deferral and Verify Company Match For RULE4
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_022M_Company Match RULE4
	 */
	@Test(dataProvider = "setData")
	public void Deferral_022M_Company_Match_RULE4(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 5
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 6
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 7
			
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			
			String actualCompanyMatchRule=deferrals.getCompanyMatchRuleDescription(Stock.GetParameterValue("minYearsOfService"));
			
			if(Web.VerifyText(Stock.GetParameterValue("ExpectedRuleDescription"), actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Not Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);

			//Step 8
			
			// TODO
			deferrals.verifyCompanyMatchChangesDynamically();
			
		//Step 9
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			if(!deferrals.verifyCompanyMatchDisplayed())
			
			Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is Not displayed", true);
		}
		catch(NoSuchElementException e){
			Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * PreCondition-Need Participant Enrolled Under a Plan which is tied with RULE13
	 * The following scriptStandard Deferral and Verify Company Match For RULE13
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_023M_Company_Match_RULE13
	 */
	@Test(dataProvider = "setData")
	public void Deferral_023M_Company_Match_RULE13(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
						
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 5
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 6
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 7
			
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			
			String actualCompanyMatchRule=deferrals.getCompanyMatchRuleDescription(Stock.GetParameterValue("minYearsOfService"));
			
			if(Web.VerifyText(Stock.GetParameterValue("ExpectedRuleDescription"), actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Not Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);

			//Step 8
			deferrals.verifyCompanyMatchChangesDynamically();
			
		//Step 9
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			if(!deferrals.verifyCompanyMatchDisplayed())
			
			Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is Not displayed", true);
		}
		catch(NoSuchElementException e){
			Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * PreCondition-Need Participant Enrolled Under a Plan which is tied with RULE15
	 * The following scriptStandard Deferral and Verify Company Match For RULE15
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral__024M_Company_Match_RULE15
	 */
	@Test(dataProvider = "setData")
	public void Deferral_024M_Company_Match_RULE15(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.YEAR, -1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE:"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("QueryUpdateEmployeeHireDate"));
		
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 5
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 6
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 7
			
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			
			String actualCompanyMatchRule=deferrals.getCompanyMatchRuleDescription(Stock.GetParameterValue("minYearsOfService"));
			
			if(Web.VerifyText(Stock.GetParameterValue("ExpectedRuleDescription"), actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Not Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);

			//Step 8
			deferrals.verifyCompanyMatchChangesDynamically();
			
		//Step 9
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			if(!deferrals.verifyCompanyMatchDisplayed())
			
			Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is Not displayed", true);
		}
		catch(NoSuchElementException e){
			Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * PreCondition-Need Participant Enrolled Under a Plan which is tied with Non Supported Rules
	 * The following scriptStandard Deferral and Verify Company Match For Non Supported Rules
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_025M_Company_Match_for_non_supported_rules
	 */
	@Test(dataProvider = "setData")
	public void Deferral_025M_Company_Match_for_non_supported_rules(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.MONTH, -1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("QueryUpdateEmployeeHireDate"));
		
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 5
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 6
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 7
			
			if (Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			
			String actualCompanyMatchRule=deferrals.getCompanyMatchRuleDescription(Stock.GetParameterValue("minYearsOfService"));
			
			if(Web.VerifyText(Stock.GetParameterValue("ExpectedRuleDescription"), actualCompanyMatchRule))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH RULE is Displayed",
						"COMPANY MATCH is displayed for RULEA and Not Matching\nExpected:"+Stock.GetParameterValue("ExpectedRuleDescription")+"\nActual:"+actualCompanyMatchRule, true);

			//Step 8
			//0-2 Yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService<2")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentage<2")));
			//2-5 yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService<5")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentage<5")));
			//Above 5 yrs
			deferrals.verifyCompanyMatchChangesForRuleA(Integer.parseInt(Stock.GetParameterValue("minYrsOfService>5")),
					Stock.GetParameterValue("Contribution Rate"),
					Float.parseFloat(Stock.GetParameterValue("companyMatchPercentage>5")));
			
			deferrals.verifyCompanyMatchChangesDynamically();
			
		//Step 9
			
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			if(!deferrals.verifyCompanyMatchDisplayed())
			
			Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is Not displayed", true);
		}
		catch(NoSuchElementException e){
			Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Not Displayed",
					"COMPANY MATCH is displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it for 457 Plan
	 * Need Participant enrolled under 457 Plan
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_034_457_Plan_BEFORE_Select_Another_contribution_rate
	 */
	@Test(dataProvider = "setData")
	public void Deferral_034_457_Plan_BEFORE_Select_Another_contribution_rate(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
						
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 & 12
						
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
		
			//Step 13
			ResultSet effectivDate;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			String expectedDate=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+expectedDate);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("QueryGetEffectiveDate"));
		
			effectivDate=DB.executeQuery(sqlQuery[0], sqlQuery[1],Stock.GetParameterValue("username").substring(0, 9));
			
			if (DB.getRecordSetCount(effectivDate) > 0) {
				try {
					effectivDate.first();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			}
		
			String actualDate=effectivDate.getString("effdate");
			
			if (expectedDate.equalsIgnoreCase(actualDate))
				Reporter.logEvent(Status.PASS, "Verify Effective Date in Elective Deferral Table in DB",
						"Effective Date is Set to First day Of Next Month\nExpected:"+expectedDate+"\nActual:"+actualDate, false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Effective Date in Elective Deferral Table in DB",
						"Effective Date is Not Set to First day Of Next Month\nExpected:"+expectedDate+"\nActual:"+actualDate, false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * The following script Standard Deferral and Verify 457 IRS Catchup Period Error Message
	 * Need Participant enrolled under 457 Plan and under Catch up Period
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_033_457_Plan_IRS_catchup_period_message
	 */
	@Test(dataProvider = "setData")
	public void Deferral_033_457_Plan_IRS_catchup_period_message(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
						
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			deferrals.verifyCatchUpPeriodErrorMessage(Stock.GetParameterValue("expectedErrorMsg"));
			if(!Web.isWebElementDisplayed(deferrals, "Standard Add"))
			
				Reporter.logEvent(Status.PASS,
						"Verify Changes to Contributions are not allowed",
						"Changes to the Contribution are disabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Changes to Contributions are not allowed",
						"Changes to the Contribution are allowed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and Verify Terminated Participant not able to do any contributions
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_026M_Terminated_Participant
	 */
	@Test(dataProvider = "setData")
	public void Deferral_026M_Terminated_Participant(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
						
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1 to 4
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 5
			
			if(!Web.isWebElementDisplayed(deferrals, "Standard Add"))
			
				Reporter.logEvent(Status.PASS,
						"Verify Changes to Contributions are not allowed",
						"Changes to the Contribution are disabled", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Changes to Contributions are not allowed",
						"Changes to the Contribution are allowed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/*
	 * This test case verify View only deferral 
	 * deferral type should have 3 type(Befor,Roth,AfterTax)
	 * After Tax Should be View Only
	 */
	@Test(dataProvider = "setData")
	public void Deferral_016M_Deferral_viewonly_source_with_two_changes_allowed_sources(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			boolean isTextMatching = false;
			Thread.sleep(5000);
			String bonusRoth = deferrals
					.getContributionPercentage(Stock.GetParameterValue("BonusRoth"));//RTH Bonus
			String bonusBeforeTax = deferrals
					.getContributionPercentage(Stock.GetParameterValue("BonusBeforeTax"));//BeforeTax Bonus
			String bonusAfterTax = deferrals
					.getContributionPercentage(Stock.GetParameterValue("BonusAterTax"));//AfterTax Bonus
			if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Bonus contribution page",
						"Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Bonus contribution page",
						"Bonus Contributions page is not displayed", true);
			String contributionRate = Stock
					.GetParameterValue("Contribution Rate");

			deferrals.click_Select_Your_Contribution_Rate();
			Web.clickOnElement(deferrals, "Continue button");
			contributionRate = deferrals.contrbution_rate;
           Thread.sleep(5000);
			int cr = Integer.parseInt(contributionRate.split("%")[0]);
			int bft = Integer.parseInt(bonusBeforeTax.split("%")[0]);
			int roth = Integer.parseInt(bonusRoth.split("%")[0]);
			int baft = Integer.parseInt(bonusAfterTax.split("%")[0]);
			int newcr=cr - baft;
			String newcontibution = Integer.toString(newcr);
			String newbft=Integer.toString((int)Math.round(newcr*0.40));
			String newroth=Integer.toString((int)Math.round(newcr*0.60));
			
			
			//Step 7
			deferrals.verifyViewOnlyLabelDisplayed(Stock.GetParameterValue("viewOnlyDeferral"));
			deferrals.verifyInputFieldGreyedOutForViewOnlyDeferral(Stock.GetParameterValue("viewOnlyDeferral"));
			//Step 8
			Web.clickOnElement(deferrals, "Continue button");
			if(Web.isWebElementDisplayed(deferrals, "Split Error Message", true))
				Reporter.logEvent(Status.PASS, "Verify Error Message Displayed For Split Percent",
						"Error Message Displayed For Split Percent", true);
						else
				Reporter.logEvent(Status.FAIL,  "Verify Error Message Displayed For Split Percent",
						"Error Message Not Displayed For Split Percent", true);
			
			//Step 9
			Web.setTextToTextBox("Input Before Tax Bonus", deferrals, newbft);
			Web.setTextToTextBox("Input Roth Bonus", deferrals, newroth);
			
			newbft=newbft+"%";
			newroth=newroth+"%";
			Web.clickOnElement(deferrals, "Continue button");
			//Step 10
			Web.waitForElement(deferrals, "Confirm button");
			if(Web.isWebElementDisplayed(deferrals, "Confirm button")){
				Web.clickOnElement(deferrals, "Continue button");
				Web.waitForElement(deferrals, "Confirm button");
			}
			
			Web.clickOnElement(deferrals, "Confirm button");
			
			Web.waitForElement(deferrals, "MyContribution Button");
			Web.clickOnElement(deferrals, "MyContribution Button");
			Web.waitForElement(deferrals, "Table Header Contribution");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			Web.waitForElement(deferrals, "Table Header Contribution");
			isTextMatching = Web.VerifyText(newbft ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("BonusBeforeTax")),true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Before Tax Bonus After Split Contribution on My Contribution page",
								"Before Tax Bonus is updated on My Contribution page \nExpected:"
										+ newbft
										+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("BonusBeforeTax"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,"Verify Before Tax Bonus After Split Contribution on My Contribution page",
								"Before Tax Bonus is not updated on My Contribution page \nExpected:"
										+ newbft
										+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("BonusBeforeTax"))
										.trim(),
								true);
			}
			isTextMatching = Web.VerifyText(newroth ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("BonusRoth")),true);
			if (isTextMatching) {
				Reporter
						.logEvent(
								Status.PASS,
								"Verify Roth Bonus After Split Contribution on My Contribution page",
								"Roth Bonus is updated on My Contribution page \nExpected:"
										+ newroth
										+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("BonusRoth"))
										.trim(),
								true);

			} else {
				Reporter
						.logEvent(
								Status.FAIL,"Verify Roth Bonus After Split Contribution on My Contribution page",
								"Roth Bonus is not updated on My Contribution page \nExpected:"
										+ newroth
										+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("BonusRoth"))
										.trim(),
								true);

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	
}

