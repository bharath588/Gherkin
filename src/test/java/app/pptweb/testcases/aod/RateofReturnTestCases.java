package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.general.RateOfReturnPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RateofReturnTestCases {
	
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
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}
	
	
	
	@Test(dataProvider = "setData")
	public void AOD_ROR_DDTC_28555_SIT_PPTWEB_AOD_ROR_card_Rate_of_return_not_available(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/* Steps
			  1: Pre-requisite :A participant having year to date rate of return option.
			  2: Log in to the participant account with the credentials.
			  3: On the MFA page, click on the link - Already have a code.
			  4: Enter the default verification code (74196385) and click on Sign In.
			 */
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			MyAccountsPage myAccount=new MyAccountsPage(homePage);
 			myAccount.get();
 			
 			/*5.Click on My accounts link on the LIAT page.
 			  6.Verify the Rate of return card is displayed on the Account Overview page with the title - 'RATE OF RETURN'
 			  7.Verify card will display "Data currently  unavailable" when there is no ROR data. 
 			 */
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));//To click on plan with no RoR-data
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.verifyRORCardWithNoData();
			
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
	public void AOD_ROR_DDTC_28526_SIT_PPTWEB_Account_overview_ROR_card_annualized(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/* Steps:
			  1.Pre-requisite :A participant having 3 year rate of return option.
			  2.Log in to the participant account with the credentials.
			  3.On the MFA page, click on the link - Already have a code.
			  4.Enter the default verification code (74196385) and click on Sign In.
			  */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			
 			/*5.Click on My accounts link on the LIAT page.
 			  6.Verify the Rate of return card is displayed on the Account Overview page with the title - 'Annualized rate of return' 
 			  */
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.verifyRORCardAnnualized();
			/* 7.Verify the dates of rate of return shows the correct date range.
			   8.Click on View details.
			   9.Verify the rate of return on this page matches the percentage on rate of return card.
			 */
			String[] sDates=myAccountOverview.getDatesFromRORCard();
			String sPercentage=myAccountOverview.getPercentageFromRORCard();
			myAccountOverview.clickonRORCardViewDetailLink();
			Common.waitForProgressBar();
			RateOfReturnPage sRor=new RateOfReturnPage();
			sRor.verifyDatesinRORPage(sDates);
			sRor.verifyRORPercentageinRORPage("annualized",sPercentage);			
						
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
	public void AOD_ROR_DDTC_29480_SIT_PPTWEB_Account_overview_ROR_card_not_displayed(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/* Steps:
              1:Pre-requisite :A participant having year to date rate of return option. 
              2:Log in to the participant account with the credentials.
              3.On the MFA page, click on the link - Already have a code.
              4.Enter the default verification code (74196385) and click on Sign In.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			/* 5.Click on My accounts link on the LIAT page.
 			   6.Verify the Rate of return card is not displayed on the Account Overview page
 			 */
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.verifyRORCardNotDisplayed();	
						
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
	public void AOD_ROR_DDTC_28870_SIT_PPTWEB_Account_overview_ROR_card_multiple_plans(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/* Steps:
			  1.Pre-requisite :A participant having rate of return option enabled for multiple plans.
			  2.Log in to the participant account with the credentials.
			  3.On the MFA page, click on the link - Already have a code.
			  4.Enter the default verification code (74196385) and click on Sign In.
			  5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			/*
			   6.Click on one of the plan links.
			   7.Verify the Rate of return card is displayed on the Account Overview page with the title - 'ANNUALIZED RATE OF RETURN' and the default option as Annualized.
			   8.Verify the dates of rate of return shows the correct date range. 
			   9.Click on View details.
			 */
			ArrayList<String> sPlanNames = myAccount.getPlanNamesinPage();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
			Common.waitForProgressBar();
			AccountOverview myAccountOverview = new AccountOverview();
			myAccountOverview.verifyRORCardAnnualized();
			String[] sDatesFirstPlan = myAccountOverview.getDatesFromRORCard();
			String sPercentageFirstPlan = myAccountOverview.getPercentageFromRORCard();
			myAccountOverview.clickonRORCardViewDetailLink();
			Common.waitForProgressBar();
			
			/*
			 10.Verify the rate of return on this page matches the percentage on rate of return card.
			 11.Select the second plan from the dropdown available for plan selection.
			 12.Verify the details displayed on the card is for the second plan and does not show any detail from the first plan.
			 */
			RateOfReturnPage sRor = new RateOfReturnPage();
			sRor.verifyRORPageDisplayed(true);
			sRor.verifyDatesinRORPage(sDatesFirstPlan);
			sRor.verifyRORPercentageinRORPage("annualized",sPercentageFirstPlan);
			myAccountOverview.selectPlanfromAccountsDropdown(sPlanNames.get(1));// Selects Second plan																
			String[] sDatesSecondPlan = myAccountOverview.getDatesFromRORCard();
			String sPercentageSecondPlan = myAccountOverview.getPercentageFromRORCard();
			if (!Arrays.equals(sDatesFirstPlan, sDatesSecondPlan))
				Reporter.logEvent(
						Status.PASS,
						"Verify the details displayed on the card is for the second plan and does not show any detail from the first plan.",
						"second plan From and To-dates are not same as first Plan",
						true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify the rate of return on this page matches the percentage on rate of return card.",
						"second plan From and To-dates are not same as first Plan",
						true);
			if (!sPercentageFirstPlan.trim().equals(sPercentageSecondPlan.trim()))
				Reporter.logEvent(
						Status.PASS,
						"Verify the details displayed on the card is for the second plan and does not show any detail from the first plan.",
						"second plan ROR % is not same as first Plan", true);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify the rate of return on this page matches the percentage on rate of return card.",
						"second plan ROR % is same as first Plan ,Second Paln Ror% is="+sPercentageSecondPlan+" First Plan Ror% is="+sPercentageFirstPlan, true);

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
	public void AOD_ROR_DDTC_28554_SIT_PPTWEB_Account_overview_ROR_card_cumulative(
			int itr, Map<String, String> testdata) {

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
 			homePage.get();
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.verifyRORCardCumulative();//for Cumulative Type
			String[] sDates=myAccountOverview.getDatesFromRORCard();
			String sPercentage=myAccountOverview.getPercentageFromRORCard();
			myAccountOverview.clickonRORCardViewDetailLink();
			Common.waitForProgressBar();
			RateOfReturnPage sRor=new RateOfReturnPage();
			sRor.verifyDatesinRORPage(sDates);
			sRor.verifyRORPercentageinRORPage("cummulative",sPercentage);	
						
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

}
