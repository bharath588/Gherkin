package app.pptweb.testcases.enrollment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.NQEnrollment;
import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.mydistributions.MyDistributions;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class EnrollmentTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;

	LoginPage login;
	String tcName;
	static String printTestData = "";
	static String userName = null;
	static String confirmationNumber = "";
	public Map<String, String> mapDistributionElections = new HashMap<String, String>();
	List<String> disbMethod;
	List<String> disbReason;
	List<String> DisbTiming;

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
	/**
	 * This Test Case is to Verify and Make Sure that when a
	 *  User is Logged in as Returning user and user should
	 *  only be able to modify Deferral Elections via Account OverView Page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26997_NQ_SE_Via_AccountOverview_VerifyDeferral_Election_Page_As_Returning_User(
			int itr, Map<String, String> testdata) {

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
			disbMethod= new ArrayList<String>();
			disbReason = new ArrayList<String>();
			 DisbTiming = new ArrayList<String>();

			// Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			NQEnrollment nqEnroll = new NQEnrollment();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_id"));

			// Step 5
			AccountOverview accountOverview = new AccountOverview();
			Web.waitForElement(accountOverview, "LINK ENROLL NOW");
			accountOverview.isTextFieldDisplayed("Enrollment is now open.");

			if (Web.isWebElementDisplayed(accountOverview, "LINK ENROLL NOW")) {
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
						"'Enroll Now' Link is Displayed in Account Overview Page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
						"'Enroll Now' Link is Not Displayed in Account Overview Page",
						true);
			}
			// Step 6
			accountOverview.clickOnEnrollNow();
			if (Web.isWebElementDisplayed(accountOverview, "Button Continue",
					true)) {
				Web.clickOnElement(accountOverview, "Button Continue");
			}
			if(Web.isWebElementDisplayed(nqEnroll, "BUTTON EDIT MY OPTIONS", true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Displayed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Not Displayed",
						true);
			}
			String contributionRate=nqEnroll.getDeferralAmount();
			// Step 7
			
			nqEnroll.clickOnAddElectionsButton();
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			//Step 8
		
			disbReason = Common.getDisbursmentReason(Stock
					.GetParameterValue("ga_id"));
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
					disbMethod = Common
							.getMethodForDisbursmentReason(
									Stock.GetParameterValue("ga_id"),
									disbReason.get(i));
					nqEnroll.selectDisbursementMethod(disbReason.get(i),
							disbMethod.get(i));
					DisbTiming = Common.getTimingForDisbursmentMethod(
							Stock.GetParameterValue("ga_id"), disbMethod.get(i));
					nqEnroll.selectTimingForDistributionElectionMethod(DisbTiming.get(i));
					mapDistributionElections.put(disbReason.get(i), disbMethod.get(i)+" - "+DisbTiming.get(i));
				}
				nqEnroll.clickContinue();
			} else {
				Reporter.logEvent(Status.INFO,
						"Verify Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is Opted for Distribution Elections",
						"Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is not Opted for Distribution Elections",
						true);
			}
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			//Step 9
			
			nqEnroll.VerifyDeferralElectionPageAsReturningUser(contributionRate);
			//Step10
			nqEnroll.clickOnBackButton();
			if(Web.isWebElementDisplayed(nqEnroll, "BUTTON EDIT MY OPTIONS", true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Displayed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Not Displayed",
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case is to Verify Summary page when a
	 *  User is Logged in as Returning user via LIAT Page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_27026_NQ_SE_Via_Liat_Verify_Summary_Page_As_Returning_User(
			int itr, Map<String, String> testdata) {

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
			
			 String ssn=Stock.GetParameterValue("userName").substring(0, 9);
			// Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			NQEnrollment nqEnroll = new NQEnrollment();
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));

			// Step 4
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			//Step 5
			Web.clickOnElement(nqEnroll, "Button Cancel");
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true)){
			
				Reporter.logEvent(
						Status.PASS,
						"Verify Clicking on Cancel Button Navigates to LIAT Page",
						"User Navigated to LIAT Page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Clicking on Cancel Button Navigates to LIAT Page",
						"User is Not Navigated to LIAT Page",true);
			}
			//Step 6
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));
			Web.waitForElement(nqEnroll, "Button Continue");
			Web.clickOnElement(nqEnroll, "Button Continue");
			if(Web.isWebElementDisplayed(nqEnroll, "BUTTON EDIT MY OPTIONS", true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Displayed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Not Displayed",
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	

	/**
	 * This Test Case is to Verify Summary page when a
	 *  User is Logged in as Returning user via LIAT Page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_27025_NQ_SE_Via_Liat_Verify_Summary_Page_As_Returning_User_When_Deferral_Amount_meets_granularity_rules(
			int itr, Map<String, String> testdata) {

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
			
			 String ssn=Stock.GetParameterValue("userName").substring(0, 9);
			// Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			NQEnrollment nqEnroll = new NQEnrollment();
			Common.waitForProgressBar();
			if(Web.isWebElementDisplayed(homePage, "Button Close WithOut Enrolling", true))
				Web.clickOnElement(homePage, "Button Close WithOut Enrolling");
			
			//Step 4
			homePage.verifyEnrollmentOpenNowLinkIsDisplayed(Stock.GetParameterValue("ga_id"));
			//Step 5 & 6
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));

			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
			//Step 7
			
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));
			Web.waitForElement(nqEnroll, "Button Continue");
			Web.clickOnElement(nqEnroll, "Button Continue");
			if(Web.isWebElementDisplayed(nqEnroll, "BUTTON EDIT MY OPTIONS", true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Displayed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Not Displayed",
						true);
			}
		//Step 8	
			
			

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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case is to Verify Deferral Elections page 
	 * for New users during NQ Enrollment for a Single Election plan via LIAT.
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26975_NQ_SE_Via_Liat_VerifyDeferral_Election_Page_As_New_User(
			int itr, Map<String, String> testdata) {

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
			disbMethod= new ArrayList<String>();
			disbReason = new ArrayList<String>();
			 DisbTiming = new ArrayList<String>();

			// Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			//Step 4
			myAccount.VerifyEnorollNowLinkDisplayedForPlan(Stock.GetParameterValue("ga_id"));
			//Step 5
			myAccount.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));
			NQEnrollment nqEnroll = new NQEnrollment();

			// Step 6
			
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
			//Step 7
			
			
			disbReason = Common.getDisbursmentReason(Stock
					.GetParameterValue("ga_id"));
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
					disbMethod = Common
							.getMethodForDisbursmentReason(
									Stock.GetParameterValue("ga_id"),
									disbReason.get(i));
					nqEnroll.selectDisbursementMethod(disbReason.get(i),
							disbMethod.get(i));
					DisbTiming = Common.getTimingForDisbursmentMethod(
							Stock.GetParameterValue("ga_id"), disbMethod.get(i));
					nqEnroll.selectTimingForDistributionElectionMethod(DisbTiming.get(i));
					mapDistributionElections.put(disbReason.get(i), disbMethod.get(i)+" - "+DisbTiming.get(i));
				}
				nqEnroll.clickContinue();
			} else {
				Reporter.logEvent(Status.INFO,
						"Verify Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is Opted for Distribution Elections",
						"Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is not Opted for Distribution Elections",
						true);
			}
			
			//Step 8
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
			//Step 9
			Deferrals deferral=new Deferrals();
			List<String> expectedDeferrals= new ArrayList<String>();
			List<String> actualDeferrals= new ArrayList<String>();
			
			expectedDeferrals=Common.getAvailableDeferralsforPlan(Stock.GetParameterValue("ga_id"), Stock.GetParameterValue("enrollEndDate"));
			
			actualDeferrals=deferral.getAvailableDeferrals();
			//for(int i=0;i<expectedDeferrals.size();i++){
			if(expectedDeferrals.equals(actualDeferrals)){
				Reporter.logEvent(Status.PASS,
						"Verify All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container",
						"All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container\nExpected Deferral Types:"+expectedDeferrals+"\nActual Deferral Types:"+actualDeferrals,
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container",
						"All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are not displayed in Container\nExpected Deferral Types:"+expectedDeferrals+"\nActual Deferral Types:"+actualDeferrals,
						true);
			}
			
			//}
			//Step 10
			//TODO
			//Step 11
			for(int i=0;i<expectedDeferrals.size();i++){
				deferral.isTextFieldDisplayed("FROM "+actualDeferrals.get(i));
			}
			String minmaxRule=deferral.getWebElementText("Text Min/Max Rule");
			if(minmaxRule.contains("0 minimum to")&&minmaxRule.contains("maximum")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Min/Max Rule is Displayed","Min/Max Rule is Displayed\nMin/Max Rule:"+minmaxRule,
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Min/Max Rule is Displayed","Min/Max Rule is Not Proper\nMin/Max Rule:"+minmaxRule,
						true);
			}
			nqEnroll.VerifyDeferralElectionPageAsNewUser(Stock.GetParameterValue("Contribution Rate"));
			
			//Step 12
			if(Web.isWebElementDisplayed(deferral, "Things To Know Container")){
			
				Reporter.logEvent(Status.PASS,
						"Verify 'Things To Know' Container is Displayed","'Things To Know' Container is Displayed",
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Things To Know' Container is Displayed","'Things To Know' Container is Not Displayed",
						true);
			}
			deferral.isTextFieldDisplayed("Things to know");
			//Step 13
			deferral.verifyWebElementDisplayed("Continue Button");
			deferral.verifyWebElementDisplayed("Back Button");
			//Step 14
			nqEnroll.clickOnBackButton();
			
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			//Step 15
			nqEnroll.clickContinue();
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This Test Case is to Verify Distribution Elections page 
	 * for New users during NQ Enrollment for a Single Election plan .
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26963_NQ_SE_Vetify_Distribution_Election_Page_As_New_User(
			int itr, Map<String, String> testdata) {

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
			disbMethod= new ArrayList<String>();
			disbReason = new ArrayList<String>();
			 DisbTiming = new ArrayList<String>();
			 //Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_id"));

			// Step 5
			LeftNavigationBar leftNav = new LeftNavigationBar();
			MyDistributions distributions =new MyDistributions(leftNav);
			distributions.get();
			
			NQEnrollment nqEnroll = new NQEnrollment();

			// Step 6
			
			distributions.clickOnEnrollNow();
			
			if (Web.isWebElementDisplayed(distributions, "Button Continue",
					true)) {
				Web.clickOnElement(distributions, "Button Continue");
			}
			
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			//Step 7
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			//Step 8
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
		 //Step 9
			
			
			disbReason = Common.getDisbursmentReason(Stock
					.GetParameterValue("ga_id"));
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
					disbMethod = Common
							.getMethodForDisbursmentReason(
									Stock.GetParameterValue("ga_id"),
									disbReason.get(i));
					nqEnroll.selectDisbursementMethod(disbReason.get(i),
							disbMethod.get(i));
					 DisbTiming = Common.getTimingForDisbursmentMethod(
							Stock.GetParameterValue("ga_id"), disbMethod.get(i));
					nqEnroll.selectTimingForDistributionElectionMethod(DisbTiming.get(i));
					mapDistributionElections.put(disbReason.get(i), disbMethod.get(i)+" - "+DisbTiming.get(i));
				}
				nqEnroll.clickContinue();
			} else {
				Reporter.logEvent(Status.INFO,
						"Verify Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is Opted for Distribution Elections",
						"Plan '" + Stock.GetParameterValue("ga_id")
								+ "' is not Opted for Distribution Elections",
						true);
			}
			
			//Step 8
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
			//Step 9
			Deferrals deferral=new Deferrals();
			List<String> expectedDeferrals= new ArrayList<String>();
			List<String> actualDeferrals= new ArrayList<String>();
			
			expectedDeferrals=Common.getAvailableDeferralsforPlan(Stock.GetParameterValue("ga_id"), Stock.GetParameterValue("enrollEndDate"));
			
			actualDeferrals=deferral.getAvailableDeferrals();
			//for(int i=0;i<expectedDeferrals.size();i++){
			if(expectedDeferrals.equals(actualDeferrals)){
				Reporter.logEvent(Status.PASS,
						"Verify All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container",
						"All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container\nExpected Deferral Types:"+expectedDeferrals+"\nActual Deferral Types:"+actualDeferrals,
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are displayed in Container",
						"All the Possible Deferral Types Offered by the Plan '"+Stock.GetParameterValue("ga_id")+"' are not displayed in Container\nExpected Deferral Types:"+expectedDeferrals+"\nActual Deferral Types:"+actualDeferrals,
						true);
			}
			
			//}
			//Step 10
			//TODO
			//Step 11
			for(int i=0;i<expectedDeferrals.size();i++){
				deferral.isTextFieldDisplayed("FROM "+actualDeferrals.get(i));
			}
			String minmaxRule=deferral.getWebElementText("Text Min/Max Rule");
			if(minmaxRule.contains("0 minimum to")&&minmaxRule.contains("maximum")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Min/Max Rule is Displayed","Min/Max Rule is Displayed\nMin/Max Rule:"+minmaxRule,
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Min/Max Rule is Displayed","Min/Max Rule is Not Proper\nMin/Max Rule:"+minmaxRule,
						true);
			}
			nqEnroll.VerifyDeferralElectionPageAsNewUser(Stock.GetParameterValue("Contribution Rate"));
			
			//Step 12
			if(Web.isWebElementDisplayed(deferral, "Things To Know Container")){
			
				Reporter.logEvent(Status.PASS,
						"Verify 'Things To Know' Container is Displayed","'Things To Know' Container is Displayed",
						true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Things To Know' Container is Displayed","'Things To Know' Container is Not Displayed",
						true);
			}
			deferral.isTextFieldDisplayed("Things to know");
			//Step 13
			deferral.verifyWebElementDisplayed("Continue Button");
			deferral.verifyWebElementDisplayed("Back Button");
			//Step 14
			nqEnroll.clickOnBackButton();
			
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			//Step 15
			nqEnroll.clickContinue();
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	/**
	 * This Test Case is to Verify and Make Sure that when a
	 *  User is Logged in as Returning user and user should
	 *  only be able to modify Deferral Elections
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_26962_NQ_SE_Via_MyDistributions_Verify_Distribution_Election_Page_As_Returning_User(
			int itr, Map<String, String> testdata) {

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

			// Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_id"));

			// Step 5
			LeftNavigationBar leftNav = new LeftNavigationBar();
			MyDistributions distributions =new MyDistributions(leftNav);
			distributions.get();
			
			//Step 6
			Web.waitForElement(distributions, "LINK ENROLL NOW");
			distributions.isTextFieldDisplayed("Enrollment is now open.");

			if (Web.isWebElementDisplayed(distributions, "LINK ENROLL NOW")) {
				Reporter.logEvent(
						Status.PASS,
						"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
						"'Enroll Now' Link is Displayed in Account Overview Page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
						"'Enroll Now' Link is Not Displayed in Account Overview Page",
						true);
			}
		
			distributions.clickOnEnrollNow();
			
			if (Web.isWebElementDisplayed(distributions, "Button Continue",
					true)) {
				Web.clickOnElement(distributions, "Button Continue");
			}
			
			NQEnrollment nqEnroll = new NQEnrollment();
			if(Web.isWebElementDisplayed(nqEnroll, "BUTTON EDIT MY OPTIONS", true)){
			
				Reporter.logEvent(
						Status.PASS,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Displayed",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Enrollment Summary Page is Displayed",
						"Enrollment Summary Page is Not Displayed",
						true);
			}
			
			// Step 7
			nqEnroll.clickEditMyOptionsButton();
			if(Web.isWebElementDisplayed(nqEnroll, "DEFERRAL ELECTION", true)){
				
				Reporter.logEvent(
						Status.PASS,
						"Verify User is Redirected to Deferral Election Page",
						"User is Redirected to Deferral Election Page",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify User is Redirected to Deferral Election Page",
						"User is Not Redirected to Deferral Election Page",
						true);
			}
			
			
			
			
			
/*
			deferral.selectContributionWhileEnroll();
			Web.clickOnElement(deferral, "Continue Button");
			nqEnroll.addCarryOverContribution(Stock.GetParameterValue("CarryOver_Option"));
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
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
