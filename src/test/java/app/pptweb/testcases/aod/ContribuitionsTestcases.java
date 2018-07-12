package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.balance.Balance;
import pageobjects.deferrals.Deferrals;
import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import appUtils.WebService_PPT;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ContribuitionsTestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	static String printTestData = "";
	String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	int hour=Calendar.getInstance().get(Calendar.HOUR);
	DateFormat dateFormat = new SimpleDateFormat("a");
	Calendar cal = Calendar.getInstance();
	String sAMorPM=dateFormat.format(cal.getTime());
	static String sURLforPayRoll = "http://fss-dapps1:8615//ElectiveDeferralServices/rest/deferralServices/deferrals/participant/defrlType/";
		
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
	public void AOD_Contribution_DDTC_28863_SIT_PPTWEB_Account_overview_Contributions_card_combined_catchup_Zero_YTD_contributions(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			//myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			//Step 12
			myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_28867_SIT_PPTWEB_Account_overview_Contributions_card_Separate_catchup_Zero_YTD_contributions(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			deferrals.addDeferralsForAOD(ar);
			
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			//myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			//Step 12
			myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29350_Account_overview_Contributions_card_with_Auto_increase_for_Standard_Catch_up_and_After_tax_contributions(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 	 		String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			ar.add("After Tax Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());*/
 			String sValue[] = Stock.GetParameterValue("CardType").split(",");
 			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
 			for(int i=0;i<sValue.length;i++)
 			{
 				//Step 6
 				myAccount.validateContributionCard(currentYear);
 				//Step 7
 				myAccount.verifyDonutChart(sValue[i]);
 				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
 				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
 				myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),sLimit[i],sValue[i]);
 				//Step 8
 				myAccount.verifyDescriptionFullmatchQuestion(sValue[i]);
 				//Step 9
 				myAccount.verifyHowMuchToContribute(sOnTrack,sLimit[i],sValue[i]);
 				//Step 12
 				if(i==2)
 					myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29351_SIT_PPTWEB_Account_overview_Contributions_card_with_Standard_Catch_up_Bonus_After_tax_and_Other_contribution(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 	 		String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			ar.add("After Tax Add");
			ar.add("Other Add");
			ar.add("Bonus Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());
 			String sValue[] = Stock.GetParameterValue("CardType").split(",");
 			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
 			for(int i=0;i<sValue.length;i++)
 			{
 				/**
 	 			 * Step 6 -
 	 			 */
 				myAccount.validateContributionCard(currentYear);
 				//Step 7
 				myAccount.verifyDonutChart(sValue[i]);
 				if(sValue[i].equalsIgnoreCase("STANDARD"))
 				{
 					myAccount.validateContributionMultiple(sValue[i],"OTHER");
 		 			myAccount.validateContributionMultiple(sValue[i],"BONUS");
 				}
 				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
 				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
 				myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),sLimit[i],sValue[i]);
 				//Step 8
 				//myAccount.verifyDescriptionFullmatchQuestion(sValue[i]);
 				//Step 9
 				myAccount.verifyHowMuchToContribute(sOnTrack,sLimit[i],sValue[i]);
 				//Step 12
 				if(i==2)
 					myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_28866_SIT_PPTWEB_Account_overview_Contributions_card_separate_catchup(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			//Step 12
			myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_33186_SIT_PPTWEB_Account_overview_Contributions_card_for_Maximize_me_Always_Standard_contribution(
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
 			/*
 			 * To get the salary of the ppt
 			 */
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
 			
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit_Apple(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyHowMuchToContribute_Apple(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			myAccount.clickmaxButton(Stock.GetParameterValue("CardType"),true);
			//Deferrals deferrals = new Deferrals();
			deferrals.click_Maximize_IRS_Limit();
			//Web.clickOnElement(deferrals, "Continue button");
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			Web.clickOnElement(myAccount, "Account Overview Link");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			
			/*
 			 * To validate the TC after clicking Maximize me always button
 			 */
			myAccount.verifyMaximizerText(Stock.GetParameterValue("CardType"));
			
			//myAccount.validateContributionViewDetailsLink();
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
	public void AOD_Contribution_DDTC_33187_SIT_PPTWEB_Account_overview_Contributions_card_for_Maximize_me_Always_Catchup_contribution(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			/*
 			 * To get the salary of the ppt
 			 */
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			//ArrayList<String> ar = new ArrayList<String>();
			//ar.add("Standard Add");
			//Need to add maximizer standard
			deferrals.clickAddEditButton("Standard Add");
			deferrals.click_Maximize_IRS_Limit();
			Web.clickOnElement(deferrals,"MAXIMIZE YES");
			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals,"MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			
			deferrals.clickAddEditButton("Catch Up Add");
			deferrals.click_Maximize_IRS_Limit();
			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals,"MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			//ar.add("Catch Up Add");
			//deferrals.addDeferralsForAOD(ar);
			
			Web.clickOnElement(myAccount, "Account Overview Link");
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			/*
 			 * To validate the TC after clicking Maximize me always button
 			 */
			myAccount.verifyMaximizerText(Stock.GetParameterValue("CardType"));
			
			//myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29352_SIT_PPTWEB_Account_overview_Contributions_card_with_Base_pay_Catch_up_Bonus_After_tax_and_Variable_contributions(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			/*
 			 * To get the salary of the ppt
 			 */
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			ar.add("Catch Up Add");
			ar.add("After Tax Add");
			deferrals.addDeferralsForAOD(ar);
			
			Web.clickOnElement(myAccount, "Account Overview Link");
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
 			String sValue[] = Stock.GetParameterValue("CardType").split(",");
 			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
 			for(int i=0;i<sValue.length;i++)
 			{
 				//Step 6
 				myAccount.validateContributionCard(currentYear);
 				//Step 7
 				myAccount.verifyDonutChart(sValue[i]);
 				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
 				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
 				myAccount.verifyIRSLimit_Apple(Stock.GetParameterValue("sContributionType"),sLimit[i],sValue[i]);
 				//Step 9
 				myAccount.verifyHowMuchToContribute_Apple(sOnTrack,sLimit[i],sValue[i]);
 				myAccount.verifyContributionModelData_Apple(sValue[i]);
 				//Step 12
 				if(i==2)
 					myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_33188_SIT_PPTWEB_Account_overview_Contributions_card_for_One_time_Maximizer_for_Standard_contributions(
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
 			/*
 			 * To get the salary of the ppt
 			 */
 			//String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			//ArrayList<String> ar = new ArrayList<String>();
			//ar.add("Standard Add");
			//Need to add maximizer standard
			deferrals.clickAddEditButton("Standard Add");
			deferrals.click_Maximize_IRS_Limit();
			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals,"MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			
					
			Web.clickOnElement(myAccount, "Account Overview Link");
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			myAccount.clickmaxButton(Stock.GetParameterValue("CardType"),false);
			
			//myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Deferrals deferrals = new Deferrals();
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Web.clickOnElement(deferrals,"Button Dont Save");
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_33193_SIT_PPTWEB_Account_overview_Apple_Contributions_card_without_Standard_maximizer(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			/*
 			 * To get the salary of the ppt
 			 */
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
 			
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit_Apple(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyHowMuchToContribute_Apple(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			myAccount.clickmaxButton(Stock.GetParameterValue("CardType"),true);
			//Deferrals deferrals = new Deferrals();
			deferrals.click_Maximize_IRS_Limit();
			//Web.clickOnElement(deferrals, "Continue button");
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			Web.clickOnElement(myAccount, "Account Overview Link");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(3000);
			
			/*
 			 * To validate the TC after clicking Maximize me always button
 			 */
			myAccount.verifyMaximizerText(Stock.GetParameterValue("CardType"));
			
			//myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_33196_SIT_PPTWEB_Account_overview_Apple_Contributions_card_without_Catchup_maximizer(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			/*
 			 * To get the salary of the ppt
 			 */
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			//ArrayList<String> ar = new ArrayList<String>();
			//ar.add("Standard Add");
			//Need to add maximizer standard
			deferrals.clickAddEditButton("Standard Add");
			deferrals.click_Maximize_IRS_Limit();
			Web.clickOnElement(deferrals,"MAXIMIZE YES");
			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals,"MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			
			deferrals.clickAddEditButton("Catch Up Add");
			deferrals.click_Maximize_IRS_Limit();
			deferrals.select_ContributionType(lib.Stock.GetParameterValue("Contribution_type"));
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals,"MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			//ar.add("Catch Up Add");
			//deferrals.addDeferralsForAOD(ar);
			
			Web.clickOnElement(myAccount, "Account Overview Link");
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			/*
 			 * To validate the TC after clicking Maximize me always button
 			 */
			myAccount.verifyMaximizerText(Stock.GetParameterValue("CardType"));
			
			//myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_33189_SIT_PPTWEB_Account_overview_Contributions_card_for_One_time_Maximizer_for_Catchup_contributions(
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
 			/*
 			 * To get the salary of the ppt
 			 */
 			//String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			/*
 			 * To add predefined contributions for the TC
 			 */
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			
			deferrals.standard_Change_Salary();
			deferrals.checkEffectiveDate();
			Web.clickOnElement(myAccount, "Account Overview Link");
 			/*
 			 * To validate the TC before clicking Maximize me always button
 			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			myAccount.verifyContributionModelData_Apple(Stock.GetParameterValue("CardType"));
			myAccount.validateMaximizerButtonNotDisplayed(Stock.GetParameterValue("CardType"));
			
			myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Balance_DDTC_28871_SIT_PPTWEB_Account_overview_Contributions_card_multiple_plans(
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
 			MyAccountsPage myAccount = new MyAccountsPage(homePage);
 			myAccount.get();
 			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
 			AccountOverview myAccountOverview=new AccountOverview();
 			myAccountOverview.validateContributionCard(currentYear);
 			Web.clickOnElement(myAccountOverview, "Go to My Account");
 			System.out.println("ITR "+itr);
 			Thread.sleep(3000);
 			/*if(itr==1)
 				Web.clickOnElement(myAccount, "LOG OUT");*/
 			
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
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	// Bhargav
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29355_SIT_PPTWEB_Account_overview_Contributions_card_for_terminated_participant(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			/**
			 * 1.Pre-requisite :A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			Common.updateTermdate("27-NOV-2017");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year".
			 */
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock
					.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(
					Stock.GetParameterValue("sContributionType"),
					Stock.GetParameterValue("IRSLimit"),
					Stock.GetParameterValue("CardType"));
			myAccount.verifyHowMuchToContribute(sOnTrack,
					Stock.GetParameterValue("IRSLimit"),
					Stock.GetParameterValue("CardType"));

			/**
			 * 10.Verify the step-up chart has months on X-axis 14.Verify the
			 * view details link take the participant to the My contributions
			 * page.
			 */
			myAccount.validateContributionViewDetailsLink();
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
				Common.updateTermdate(null);
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_DDTC_29357_SIT_PPTWEB_Account_overview_Contributions_card_with_Standard_Catch_up_Bonus_After_tax_and_Other_contribution_percent_only(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			/**
			 * 1.Pre-requisite :A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			// Pre-Conditions to delete deferrals
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			arr.add("After Tax Add");
			arr.add("Other Add");
			arr.add("Bonus Add");
			deferrals.addDeferralsForAOD(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range". 12.Verify there is another donut
			 * chart for catchup contributions with the Year-to-date
			 * contributions, On track to contribute, and 2017 IRS Limit values.
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
			for (int i = 0; i < sValue.length; i++) {
				myAccount.validateContributionCard(currentYear);
				myAccount.verifyDonutChart(sValue[i]);
				if (sValue[i].equalsIgnoreCase("STANDARD")) {
					myAccount.validateContributionMultiple(sValue[i], "OTHER");
					myAccount.validateContributionMultiple(sValue[i], "BONUS");
				}
				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
				myAccount.verifyIRSLimit(
						Stock.GetParameterValue("sContributionType"),
						sLimit[i], sValue[i]);
				myAccount.verifyHowMuchToContribute(sOnTrack, sLimit[i],
						sValue[i]);

				/**
				 * 14.Verify the view details link take the participant to the
				 * My contributions page.
				 */
				if (i == 2)
					myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29358_SIT_PPTWEB_Account_overview_Contributions_card_for_NQ_plan(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * 1.Pre-requisite : A participant having separate catch-up
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 * 6.Verify the Contributions card is not displayed on the Account
			 * Overview page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			Common.waitForProgressBar();
			Deferrals deferrals = new Deferrals(lftNavBar);
			// deferrals.deleteAllAODDeferrals();
			deferrals.get();
			AccountOverview myAccountOverview = new AccountOverview();
			deferrals.clickAddEditButton("Bonus Add");
			deferrals.click_Select_Your_Contribution_Rate();
			Web.waitForElement(deferrals, "Continue button");
			Web.clickOnElement(deferrals, "Continue button");
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			Web.clickOnElement(myAccountOverview, "Account Overview Link");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			myAccountOverview.validateContributionCardNotDisplayed();

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
	public void AOD_Contribution_DDTC_29359_SIT_PPTWEB_Account_overview_Contributions_card_with_hire_date_less_than_one_year(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			// Pre-Conditions to Set Hire-date Less than one year
			Common.updateEmployeeHireDate(
					Stock.GetParameterValue("Hire_date_lessthan_year"),
					Stock.GetParameterValue("SSN"));
			/**
			 * 1.Pre-requisite :A participant having separate catch up
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			// Pre-Conditions to delete deferrals
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			deferrals.addDeferralsForAOD(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range". 12.Verify there is another donut
			 * chart for catchup contributions with the Year-to-date
			 * contributions, On track to contribute, and 2017 IRS Limit values.
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
			for (int i = 0; i < sValue.length; i++) {
				myAccount.validateContributionCard(currentYear);
				myAccount.verifyDonutChart(sValue[i]);
				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
				myAccount.verifyIRSLimit(
						Stock.GetParameterValue("sContributionType"),
						sLimit[i], sValue[i]);
				myAccount.verifyHowMuchToContribute(sOnTrack, sLimit[i],
						sValue[i]);
				myAccount.verifyContributionModelData(sValue[i]);

				/**
				 * 14.Verify the view details link take the participant to the
				 * My contributions page.
				 */
				if (i == 1)
					myAccount.validateContributionViewDetailsLink();

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
				// Conditions to Set Hire-date Greater than one year
				Common.updateEmployeeHireDate(
						Stock.GetParameterValue("Hire_date_greaterthan_year"),
						Stock.GetParameterValue("SSN"));
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29360_SIT_PPTWEB_Account_overview_Contributions_card_with_hire_date_more_than_one_year(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			// Pre-Conditions to Set Hire-date Less than one year
			Common.updateEmployeeHireDate(
					Stock.GetParameterValue("Hire_date_greaterthan_year"),
					Stock.GetParameterValue("SSN"));
			/**
			 * 1.Pre-requisite :A participant having separate catch up
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			// Pre-Conditions to delete deferrals
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			deferrals.addDeferralsForAOD(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range". 12.Verify there is another donut
			 * chart for catchup contributions with the Year-to-date
			 * contributions, On track to contribute, and 2017 IRS Limit values.
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
			for (int i = 0; i < sValue.length; i++) {
				myAccount.validateContributionCard(currentYear);
				myAccount.verifyDonutChart(sValue[i]);
				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
				String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
				myAccount.verifyIRSLimit(
						Stock.GetParameterValue("sContributionType"),
						sLimit[i], sValue[i]);
				myAccount.verifyHowMuchToContribute(sOnTrack, sLimit[i],
						sValue[i]);
				myAccount.verifyContributionModelData(sValue[i]);

				/**
				 * 14.Verify the view details link take the participant to the
				 * My contributions page.
				 */
				if (i == 1)
					myAccount.validateContributionViewDetailsLink();

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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29382_29661_SIT_PPTWEB_Account_overview_Contributions_card_with_participant_having_CSOR_deferrals_only(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			Common.updatePlanEnrollCode("N",Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			String sServiceValue=web.getEmployeeValue(sJSONResponse);
			Common.updatePlanEnrollCode("A",Stock.GetParameterValue("planId"));
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
 			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("After Tax Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());
			Common.updatePlanEnrollCode("N",Stock.GetParameterValue("planId"));
			Web.getDriver().navigate().refresh();
			
			//Step 7
			String sOnTrack=null;
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
			for (int i = 0; i < sValue.length; i++) {
				myAccount.validateContributionCard(currentYear);
				myAccount.verifyDonutChart(sValue[i]);
				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
				if(sValue[i].equalsIgnoreCase("STANDARD"))
					sOnTrack = myAccount.verifyOnTrackToContributeCSOR(sServiceValue,yearToDate,sValue[i],
							Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
							sLimit[i]);
				else
					sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,sValue[i],
 						Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),sLimit[i]);
				myAccount.verifyIRSLimit(
						Stock.GetParameterValue("sContributionType"),
						sLimit[i], sValue[i]);
				myAccount.verifyHowMuchToContribute(sOnTrack, sLimit[i],
						sValue[i]);
				myAccount.verifyContributionModelData(sValue[i]);

				/**
				 * 14.Verify the view details link take the participant to the
				 * My contributions page.
				 */
				if (i == 1)
					myAccount.validateContributionViewDetailsLink();

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
				Common.updatePlanEnrollCode("A",Stock.GetParameterValue("planId"));
				//Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_31344_SIT_PPTWEB_Account_overview_Contributions_card_for_payroll_type_code_ADJRUNPAYROLLDATE(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			String sPayRollType = web.getPayRollType(sJSONResponse);
			if(sPayRollType.equalsIgnoreCase(Stock.GetParameterValue("payRollType")))
				Reporter.logEvent(Status.PASS,
						"Verify Payroll is changed in DB",
						"Payroll is changed in DB successfully. Expected: "
								+ Stock.GetParameterValue("payRollType") + " CONTRIBUTIONS. Actual:"
								+ sPayRollType, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Payroll is changed in DB",
						"Payroll is not changed in DB. Expected: "
								+ Stock.GetParameterValue("payRollType") + " CONTRIBUTIONS. Actual:"
								+ sPayRollType, true);
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			//myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29673_SIT_PPTWEB_Account_overview_Contributions_card_for_payroll_type_code_GENERIC(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			Common.updateTermdate(null);
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			String sPayRollType = web.getPayRollType(sJSONResponse);
			if(sPayRollType.equalsIgnoreCase(Stock.GetParameterValue("payRollType")))
				Reporter.logEvent(Status.PASS,
						"Verify Payroll is changed in DB",
						"Payroll is changed in DB successfully. Expected: "
								+ Stock.GetParameterValue("payRollType") + " CONTRIBUTIONS. Actual:"
								+ sPayRollType, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Payroll is changed in DB",
						"Payroll is not changed in DB. Expected: "
								+ Stock.GetParameterValue("payRollType") + " CONTRIBUTIONS. Actual:"
								+ sPayRollType, true);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			//myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29643_SIT_PPTWEB_Account_overview_Contributions_card_for_457b_plan(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * To check if the plan is 457
			 */
			String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
			if(sPlanCode.equalsIgnoreCase("457"))
				Reporter.logEvent(Status.PASS,
						"Verify the plan is 457",
						"Plan is 457. Expected: "
								+ "457" + " Actual:"+ sPlanCode, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the plan is 457",
						"Plan is 457. Expected: "
								+ "457" + " Actual:"+ sPlanCode,  true);
			
			/**
			 * Updating Payroll to Generic
			 */
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			String sOnTrackUI = myAccount.getFinalOnTrackFromUI(Stock.GetParameterValue("CardType"));
			System.out.println("UI"+sOnTrackUI+"Code"+sOnTrack);
			Common.verifyTwoStrings(sOnTrack, sOnTrackUI);
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * Test Case Verifies Account Overview Card Zero YTD with Non Zero Prior
	 * Plan Contributions for 401A Plan
	 */
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29361_SIT_PPTWEB_Account_overview_Contributions_card_zero_YTD_with_non_zero_prior_plan_contributions_for_401a_plan(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * 1.Pre-requisite :A participant having separate catch up
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web
					.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service" + payRollType
					+ " Remaining payroll from Service" + remainingPayPeriod);
			String sServiceValue = web.getEmployeeValue(sJSONResponse);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			// Pre-Conditions to delete deferrals
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			int priorPlanContributionAmnt = deferrals
					.validatePriorPlanContributions(true, currentYear);
			deferrals.clickAddEditButton("Standard Add");
			deferrals.click_Select_Your_Contribution_Rate();
			Web.waitForElement(deferrals, "Continue button");
			Web.clickOnElement(deferrals, "Continue button");
			deferrals.myContributions_Confirmation_Page();
			Web.clickOnElement(deferrals, "MyContribution Button");
			Thread.sleep(3000);
			deferrals.checkEffectiveDate();
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 11.Verify
			 * there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			String sValue = Stock.GetParameterValue("CardType");
			String sLimit = Stock.GetParameterValue("IRSLimit");
			myAccount.validateContributionCard(currentYear);
			myAccount.verifyDonutChart(sValue);
			int yearToDatePrevious = myAccount
					.verifyZeroYTDwithNonzeroPriorPlanContributions(sValue,
							priorPlanContributionAmnt);// Contributions card
														// zero YTD with
														// non-zero prior plan
														// contributions for
														// 401a plan
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,
					yearToDatePrevious, Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),
					Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			String getCSOROnTrack = myAccount.verifyOnTrackToContributeCSOR(
					sServiceValue, 0, sValue, Integer.parseInt(payRollType),
					Integer.parseInt(remainingPayPeriod), sLimit);
			String sFinalOntrack = Integer.toString(Integer.parseInt(sOnTrack)
					+ Integer.parseInt(getCSOROnTrack));
			System.out.println("From Calculation: " + sFinalOntrack);
			String sFinalOnTrackFromUI = myAccount
					.getFinalOnTrackFromUI(sValue);
			System.out.println("From UI: " + sFinalOnTrackFromUI);
			Common.verifyTwoStrings(sFinalOntrack, sFinalOnTrackFromUI);
			myAccount.verifyContributionModelData(sValue);

			/**
			 * 14.Verify the view details link take the participant to the My
			 * contributions page.
			 */
			myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Test Case Validates Editing contributions for contributions card with
	 * View Only deferral(eg: After tax Deferral made view Only in CSAS)
	 */
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29600SIT_PPTWEB_Account_overview_Editing_contributions_through_Contributions_card_with_View_only_deferrals(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * 1.Pre-requisite :A participant having separate catch up
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			Common.waitForProgressBar();
			AccountOverview AccOverview = new AccountOverview(lftNavBar);
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.get();
			Common.waitForProgressBar();
			Web.clickOnElement(AccOverview, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 11.Verify
			 * there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			String sValue = Stock.GetParameterValue("CardType");
			AccOverview.validateContributionCard(currentYear);
			AccOverview.verifyDonutChart(sValue);
			AccOverview.validateEditContributionsforViewOnlyDeferral(sValue);

			/**
			 * 14.Verify the view details link take the participant to the My
			 * contributions page.
			 */
			AccOverview.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Test Case Validates Editing contributions using (+,-) for Contributions
	 * card with Standard Catch-up Bonus After-tax and Other
	 * contribution_dollar_only
	 */
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29601_SIT_PPTWEB_Account_overview_Editing_contributions_through_Contributions_card_with_Standard_Catchup_Bonus_Aftertax_and_Other_contribution_dollar_only(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * 1.Pre-requisite : A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			deferrals.addDeferralsForAOD_DollarOnly(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());
			Common.waitForProgressBar();
			myAccount.validateContributionCard(currentYear);

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String[] sbuttonType = { "plus", "minus" };
			for (int j = 0; j < sValue.length; j++) {
				for (int i = 0; i < sbuttonType.length; i++) {
					if (i == 0) {
						Thread.sleep(1000);
						myAccount.verifyDonutChart(sValue[j]);
					}
					myAccount.clickonShowMorelinkforDeferral(sValue[j]);
					myAccount.clickonPlusorMinusbtn(sValue[j], sbuttonType[i]);
					String sContributionValoeinAo = myAccount
							.getContributionValue(sValue[j]);
					myAccount.clickonApplyChangesbtn(sValue[j]);
					Common.waitForProgressBar();
					String sContributionValueinDeferal = deferrals
							.getContributionValueinDeferral();
					if (sContributionValoeinAo
							.equals(sContributionValueinDeferal))
						Reporter.logEvent(Status.PASS,
								"Verify the edit contributions using the "
										+ sbuttonType[i] + " button"
										+ sValue[j],
								"Edit contributions using the "
										+ sbuttonType[i]
										+ " button is working as expected for "
										+ sValue[j], false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify the edit contributions using the "
										+ sbuttonType[i] + " button"
										+ sValue[j],
								"Edit contributions using the "
										+ sbuttonType[i]
										+ " button is not working as expected for "
										+ sValue[j], true);
					Web.waitForElement(deferrals, "Back Button");
					Web.clickOnElement(deferrals, "Back Button");
					if (Web.isWebElementDisplayed(deferrals, "Button Dont Save"))
						Web.clickOnElement(deferrals, "Button Dont Save");
					myAccount.waitForDonutChart(sValue[j]);
					Web.waitForElement(myAccount, "Account Overview Link");
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Test Case Validates Editing contributions using (+,-) for Contributions
	 * card with Standard Catch-up Bonus After-tax and Other contribution in
	 * Percentage only
	 */
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29602_SIT_PPTWEB_Account_overview_Editing_contributions_through_Contributions_card_with_Standard_Catch_up_Bonus_After_tax_and_Other_contribution_percent_only(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * 1.Pre-requisite : A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			deferrals.addDeferralsForAOD(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());
			Common.waitForProgressBar();
			myAccount.validateContributionCard(currentYear);

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String[] sbuttonType = { "plus", "minus" };
			for (int j = 0; j < sValue.length; j++) {
				for (int i = 0; i < sbuttonType.length; i++) {
					if (i == 0) {
						Thread.sleep(1000);
						myAccount.verifyDonutChart(sValue[j]);
					}
					myAccount.clickonShowMorelinkforDeferral(sValue[j]);
					myAccount.clickonPlusorMinusbtn(sValue[j], sbuttonType[i]);
					String sContributionValoeinAo = myAccount
							.getContributionValue(sValue[j]);
					myAccount.clickonApplyChangesbtn(sValue[j]);
					Common.waitForProgressBar();
					String sContributionValueinDeferal = deferrals
							.getContributionValueinDeferral();
					if (sContributionValoeinAo
							.equals(sContributionValueinDeferal))
						Reporter.logEvent(Status.PASS,
								"Verify the edit contributions using the "
										+ sbuttonType[i] + " button"
										+ sValue[j],
								"Edit contributions using the "
										+ sbuttonType[i]
										+ " button is working as expected for "
										+ sValue[j], false);
					else
						Reporter.logEvent(
								Status.FAIL,
								"Verify the edit contributions using the "
										+ sbuttonType[i] + " button"
										+ sValue[j],
								"Edit contributions using the "
										+ sbuttonType[i]
										+ " button is not working as expected for "
										+ sValue[j], true);
					Web.waitForElement(deferrals, "Back Button");
					Web.clickOnElement(deferrals, "Back Button");
					if (Web.isWebElementDisplayed(deferrals, "Button Dont Save"))
						Web.clickOnElement(deferrals, "Button Dont Save");
					myAccount.waitForDonutChart(sValue[j]);
					Web.waitForElement(myAccount, "Account Overview Link");
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Test Case Validates Editing contributions using (+,-) for Contributions
	 * card for terminated participant i.e Contributions card will not be
	 * displayed for Non-Apple User.
	 */
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29603_SIT_PPTWEB_Account_overview_Editing_contributions_through_Contributions_card_for_terminated_participant(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			/**
			 * 1.Pre-requisite :A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			Common.updateTermdate("27-NOV-2017");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview accOverview = new AccountOverview(lftNavBar);
			accOverview.get();

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			accOverview.validateContributionCardNotDisplayed();

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
				Common.updateTermdate(null);
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29356_SIT_PPTWEB_Account_overview_Contributions_card_with_Standard_Catch_up_Bonus_After_tax_and_Other_contribution_dollar_only(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println("payRollType from Service"+payRollType+" Remaining payroll from Service"+remainingPayPeriod);
			/**
			 * 1.Pre-requisite : A participant having separate catchup
			 * contributions. 2.Log in to the participant account with the
			 * credentials. 3.On the MFA page, click on the link - Already have
			 * a code. 4.Enter the default verification code (74196385) and
			 * click on Sign In. 5.Click on My accounts link on the LIAT page.
			 */
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			String sSalary = homePage.getSalary();
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("Standard Add");
			arr.add("Catch Up Add");
			arr.add("After Tax Add");
			arr.add("Other Add");
			arr.add("Bonus Add");
			deferrals.addDeferralsForAOD_DollarOnly(arr);
			Web.clickOnElement(myAccount, "Account Overview Link");
			Web.waitForPageToLoad(Web.getDriver());

			/**
			 * 6.Verify the Contributions card is displayed on the Account
			 * Overview page with the title - 'MY 2017 CONTRIBUTIONS'. 7.Verify
			 * the donut chart is displayed with the Year-to-date contributions,
			 * On track to contribute, and 2017 IRS Limit values. 8.Verify the
			 * message is displayed for additional company match -
			 * "On track to get $X,XXX in company match this year". 9.Verify the
			 * message is displayed for additional contributions -
			 * "You can contribute an additional $X,XXX this year". 10.Verify
			 * the step-up chart has months on X-axis,dollar amounts on the
			 * Y-axis (IRS limit should be labeled), YTD contributions indicated
			 * by a vertical line that separates YTD contributions (green side
			 * of chart), on track to contribute contributions (blue side of
			 * chart), and Year-end projected values (should be labeled).
			 * 11.Verify there are '+' and '-' buttons with the label -
			 * "Model contribution date range".
			 */
			String sValue[] = Stock.GetParameterValue("CardType").split(",");
			String sLimit[] = Stock.GetParameterValue("IRSLimit").split(",");
			for (int i = 0; i < sValue.length; i++) {
				myAccount.validateContributionCard(currentYear);
				myAccount.verifyDonutChart(sValue[i]);
				if (sValue[i].equalsIgnoreCase("STANDARD")) {
					myAccount.validateContributionMultiple(sValue[i], "OTHER");
					myAccount.validateContributionMultiple(sValue[i], "BONUS");
				}
				int yearToDate = myAccount.verifyYeartToDate(sValue[i]);
				String sOnTrack = myAccount.verifyOnTrackToContribute(
						sValue[i], Integer.parseInt(remainingPayPeriod));// For
																			// _dollar
																			// only
				myAccount.verifyIRSLimit(
						Stock.GetParameterValue("sContributionType"),
						sLimit[i], sValue[i]);
				myAccount.verifyHowMuchToContribute(sOnTrack, sLimit[i],
						sValue[i]);

				/**
				 * 14.Verify the view details link take the participant to the
				 * My contributions page.
				 */
				if (i == 2)
					myAccount.validateContributionViewDetailsLink();
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
				Common.deleteMaximizer(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				Common.deletePartService(Stock.GetParameterValue("ind_ID"),
						Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29646_SIT_PPTWEB_Account_overview_Contributions_card_for_403B_plan(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * To check if the plan is 457
			 */
			String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
			if(sPlanCode.equalsIgnoreCase("403B"))
				Reporter.logEvent(Status.PASS,
						"Verify the plan is 403B",
						"Plan is 403B. Expected: "
								+ "403B" + " Actual:"+ sPlanCode, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the plan is 403B",
						"Plan is 403B. Expected: "
								+ "403B" + " Actual:"+ sPlanCode,  true);
			
			/**
			 * Updating Payroll to Generic
			 */
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
 			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 8
			myAccount.verifyDescriptionFullmatchQuestion(Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 11
			//myAccount.verifyContributionModelData(Stock.GetParameterValue("CardType"));
			//Step 12
			myAccount.validateContributionViewDetailsLink();
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
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29645_SIT_PPTWEB_Account_overview_Contributions_card_for_401A_plan(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * To check if the plan is 401A
			 */
			String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
			if(sPlanCode.equalsIgnoreCase("401A"))
				Reporter.logEvent(Status.PASS,
						"Verify the plan is 401A",
						"Plan is 401A. Expected: "
								+ "401A" + " Actual:"+ sPlanCode, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the plan is 401A",
						"Plan is 401A. Expected: "
								+ "401A" + " Actual:"+ sPlanCode,  true);
			
			/**
			 * Updating Payroll to Generic
			 */
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			String sOnTrackUI = myAccount.getFinalOnTrackFromUI(Stock.GetParameterValue("CardType"));
			System.out.println("UI"+sOnTrackUI+"Code"+sOnTrack);
			Common.verifyTwoStrings(sOnTrack, sOnTrackUI);
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
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Contribution_DDTC_29644_SIT_PPTWEB_Account_overview_Contributions_card_for_1165E_plan(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			/**
			 * To check if the plan is 1165E
			 */
			String sPlanCode= Common.getIRSRLCode(Stock.GetParameterValue("planId"));
			if(sPlanCode.equalsIgnoreCase("1165E"))
				Reporter.logEvent(Status.PASS,
						"Verify the plan is 1165E",
						"Plan is 1165E. Expected: "
								+ "1165E" + " Actual:"+ sPlanCode, false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify the plan is 1165E",
						"Plan is 1165E. Expected: "
								+ "1165E" + " Actual:"+ sPlanCode,  true);
			
			/**
			 * Updating Payroll to Generic
			 */
			Common.updatePayRollType(Stock.GetParameterValue("payRollType"),
					Stock.GetParameterValue("planId"));
			WebService_PPT web = new WebService_PPT();
			String sJSONResponse = web.getJSONResponse(sURLforPayRoll);
			ArrayList<String> payRoll = web.getPayPeriodsRemainingInYear(sJSONResponse);
			String remainingPayPeriod = payRoll.get(0);
			String payRollType = payRoll.get(1);
			System.out.println(payRollType+remainingPayPeriod);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			homePage.get();
 			String sSalary = homePage.getSalary();
 			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);	
 			AccountOverview myAccount = new AccountOverview(lftNavBar);
 			myAccount.get();
			/*Deferrals deferrals = new Deferrals(lftNavBar);
			deferrals.deleteAllAODDeferrals();
			deferrals.get();
			ArrayList<String> ar = new ArrayList<String>();
			ar.add("Standard Add");
			deferrals.addDeferralsForAOD(ar);
			Web.clickOnElement(myAccount, "Account Overview Link");*/
			//Step 6
 			myAccount.validateContributionCard(currentYear);
			//Step 7
			myAccount.verifyDonutChart(Stock.GetParameterValue("CardType"));
			int yearToDate = myAccount.verifyYeartToDate(Stock.GetParameterValue("CardType"));
			String sOnTrack = myAccount.verifyOnTrackToContribute(sSalary,yearToDate,Stock.GetParameterValue("CardType"),
					Integer.parseInt(payRollType),Integer.parseInt(remainingPayPeriod),
					Stock.GetParameterValue("IRSLimit"));
			myAccount.verifyIRSLimit(Stock.GetParameterValue("sContributionType"),Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
			//Step 9
			myAccount.verifyHowMuchToContribute(sOnTrack,Stock.GetParameterValue("IRSLimit"),Stock.GetParameterValue("CardType"));
		
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
				Common.deletePartService(Stock.GetParameterValue("ind_ID"), Stock.GetParameterValue("planId"));
				AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount,"Go to Home");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
