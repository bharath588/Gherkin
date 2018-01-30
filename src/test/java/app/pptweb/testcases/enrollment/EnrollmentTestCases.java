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
	public HashMap<String, String> mapDistributionMethodAndTiming = new HashMap<String, String>();
	public HashMap<String, String> mapDistributionReasonAndMethod = new HashMap<String, String>();
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> mapSelectedDistributionElections = new LinkedHashMap<>();
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
			
			nqEnroll.VerifySummaryPageWithErrorMessage("500000");
			//Step 9
			Web.clickOnElement(nqEnroll, "BUTTON EDIT MY OPTIONS");
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			//Step 10
			nqEnroll.clickOnBackButton();
			
			Web.waitForElement(nqEnroll, "BUTTON EDIT MY OPTIONS");
			Common.isLabelDisplayed("FROM Employee deferral");
			//Step 11
			//TODO
			

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
	 * This Test Case is to Verify aDeferral Elections page for New users with 
	 * multiple reasons during NQ Enrollment 
	 * for a Single Election plan via Account Overview.
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26977_NQ_SE_Via_AccountOverview_VerifyDeferral_Election_Page_As_New_User_With_Multiple_Reasons(
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
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			//Step 7
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			
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
			
			//Step 9
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
			//Step 10
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
			//Step 11 & 12
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
			
			//Step 13
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
			//Step 14
			deferral.verifyWebElementDisplayed("Continue Button");
			deferral.verifyWebElementDisplayed("Back Button");
			//Step 15
			nqEnroll.clickOnBackButton();
			
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			//Step 16
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
	 * This Test Case is To verify the NQ enrollment Confirmation Page with 
	 * Multiple Structure for new user via My Distributions page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26955_NQ_SE_As_New_User_Verify_ConfirmationPage_With_MulltipleStructures_via_MyDistributionPage(
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
			
					
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			//Step 7
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			//Step 8
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
		 //Step 9 and 10
			
			nqEnroll.VerifyPageHeaderForContinueToEnrollPage(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("planId"));
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
			
			//Step 11
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
			
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
			String contributionRate=Stock.GetParameterValue("contributionRate");
			deferral.selectContributionWhileEnroll(contributionRate);
			//Step 12
			nqEnroll.clickContinue();
			nqEnroll.VerifyPanelTitle(Stock.GetParameterValue("ga_id"));
			nqEnroll.VerifySummaryPageAsNewUser(contributionRate);
			//Step 13 is duplicate of Step 15
			
			//Step 14
		    nqEnroll.clickIAgreeAndEnrollNowButton();
		    Common.waitForProgressBar();
		  
		    //Step 15
		    //TODO
		    //VerifyConfirmationPage
			
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
	 * This Test Case is To verify the NQ enrollment Summary Page as
	 * new user via My Distributions page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26846_NQ_SE_As_New_User_Verify_Summary_Page_via_MyDistributionPage(
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
			
					
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			//Step 7
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			//Step 8
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
		 //Step 9 and 10
			
			nqEnroll.VerifyPageHeaderForContinueToEnrollPage(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("planId"));
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
			
			//Step 11
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			
			
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
			String contributionRate=Stock.GetParameterValue("contributionRate");
			deferral.selectContributionWhileEnroll(contributionRate);
			//Step 12
			nqEnroll.clickContinue();
			nqEnroll.VerifyPanelTitle(Stock.GetParameterValue("ga_id"));
			nqEnroll.VerifySummaryPageAsNewUser(contributionRate);
			
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
	 * This Test Case is To verify the NQ enrollment Summary Page as
	 * new user via LIAT page
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void DDTC_26831_NQ_SE_As_New_User_Verify_Summary_Page_via_LIATPage(
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
			homePage.get();
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));
			NQEnrollment nqEnroll = new NQEnrollment();

			// Step 5
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			nqEnroll.VerifyPageHeaderForContinueToEnrollPage(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("planId"));
			
			//Step 6 ,7 are not applicable
			//Step 8
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
			nqEnroll.VerifyPageHeaderForContinueToEnrollPage(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("planId"));
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
			
			//Step 9
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTION");
			Deferrals deferral=new Deferrals();
			
			String contributionRate=Stock.GetParameterValue("contributionRate");
			deferral.selectContributionWhileEnroll(contributionRate);
			nqEnroll.addCarryOverContribution(Stock.GetParameterValue("carryOverOption"));
			nqEnroll.clickContinue();
			//Step 10
			nqEnroll.VerifyPanelTitle(Stock.GetParameterValue("ga_id"));
			nqEnroll.VerifySummaryPageAsNewUser(contributionRate);
			
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
	public void DDTC_27023_NQ_SE_Verify_Summary_Page_As_Returning_User_via_LIAT(
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
			//Step 4
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));

			// Step 5, 6 and 7
			Web.waitForElement(nqEnroll, "Button Continue");
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
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
			nqEnroll.VerifySummaryPageAsReturningUser(Stock.GetParameterValue("gc_id"),
					Stock.GetParameterValue("plan_id"), Stock.GetParameterValue("ga_Id"));

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
	public void DDTC_26959_NQ_SE_Via_LIAT_Verify_Distribution_Election_Page_As_Returning_User(
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
			//Step 4
			Common.isTextFieldDisplayed("Enrollment is now open.");
			//step 5
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));

			
			Web.waitForElement(nqEnroll, "Button Continue");
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
			
			//Step 6
			
			nqEnroll.clickContinue();

			
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
	 *   be able to modify Distribution Elections 
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_26961_NQ_SE_Via_LIAT_Verify_Distribution_Election_Page_As_Returning_User_Same_Enrollment_Window(
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
			
			//Precondition Login as new user and Complete Enrollment
			disbMethod= new ArrayList<String>();
			disbReason = new ArrayList<String>();
			 DisbTiming = new ArrayList<String>();
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));
			NQEnrollment nqEnroll = new NQEnrollment();

		
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			
			Web.waitForElement(nqEnroll,"DISTRIBUTION ELECTIONS");
			
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
					
					mapDistributionReasonAndMethod.put(disbReason.get(i), disbMethod.get(i));
					
					mapDistributionMethodAndTiming.put(disbMethod.get(i),DisbTiming.get(i) );
					
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
			
			Web.waitForElement(nqEnroll,"DEFERRAL ELECTION");
			Deferrals deferral=new Deferrals();
			
			String contributionRate=Stock.GetParameterValue("contributionRate");
			deferral.selectContributionWhileEnroll(contributionRate);
			nqEnroll.addCarryOverContribution(Stock.GetParameterValue("carryOverOption"));
			nqEnroll.clickIAgreeAndEnrollNowButton();
			Common.waitForProgressBar();
			nqEnroll.clickContinue();
			Common.waitForProgressBar();
			
			//Step 1 to 3
			Web.clickOnElement(homePage, "HOME");
			Common.waitForProgressBar();
			String ssn=Stock.GetParameterValue("userName").substring(0, 9);
			
			//Step 4
			Common.isTextFieldDisplayed("Enrollment is now open.");
			//step 5
			homePage.clickEnrollNowByGAID(Stock.GetParameterValue("ga_id"));

			Web.waitForElement(nqEnroll, "Button Continue");
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			//Step 6
			
			nqEnroll.clickContinue();
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
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
				}
			}
			nqEnroll.VerifyDeferralElectionPageAsReturningUser(contributionRate);
			//Step 8 and 9 are not applicable
			
			//step 10
			
			nqEnroll.clickEditMyOptionsButton();
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
			//Step 11
			
			nqEnroll.VerifyDistibutionElectionPageAsReturningUser(mapDistributionReasonAndMethod, mapDistributionMethodAndTiming);
			//Step 12
			
			if (disbReason.size() > 0) {
				int j=1;
			
				for (int i = 0; i < disbReason.size(); i++) {
					
				
					disbMethod = Common
							.getMethodForDisbursmentReason(
									Stock.GetParameterValue("ga_id"),
									disbReason.get(i));
					nqEnroll.selectDisbursementMethod(disbReason.get(i),
							disbMethod.get(i));
					 DisbTiming = Common.getTimingForDisbursmentMethod(
							Stock.GetParameterValue("ga_id"), disbMethod.get(i));
					nqEnroll.selectTimingForDistributionElectionMethod(DisbTiming.get(j));
					
					mapDistributionElections.put(disbReason.get(i), disbMethod.get(i)+" - "+DisbTiming.get(j));
				}
			}
		
				nqEnroll.clickContinue();
			
				deferral.selectContributionWhileEnroll(contributionRate);;
				nqEnroll.addCarryOverContribution(Stock.GetParameterValue("CarryOver_Option"));
				Web.clickOnElement(deferral, "Continue Button");
			
			//Step 13
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
				}
			}
			
		}
				

		catch (Exception e) {
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
	public void DDTC_26827_NQ_SE_Via_AccountOverview_Verify_Distribution_Election_Page_As_Returning_User(
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
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			//Step 4
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
			NQEnrollment nqEnroll = new NQEnrollment();
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
			nqEnroll.clickContinue();
			
			nqEnroll.VerifyPageHeaderForContinueToEnrollPage(Stock.GetParameterValue("gc_id"), Stock.GetParameterValue("planId"));
			
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
	 *  User is Logged in as Returning user in the same Enrollment Window user should
	 *   be able to modify Distribution Elections  
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_26777_NQ_SE_Via_LIAT_Verify_Distribution_Election_Page_As_Returning_User_Same_Enrollment_Window(
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
			
			//Precondition Login as new user and Complete Enrollment
			disbMethod= new ArrayList<String>();
			disbReason = new ArrayList<String>();
			 DisbTiming = new ArrayList<String>();
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
		
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_id"));

	
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
	
			accountOverview.clickOnEnrollNow();
	
			NQEnrollment nqEnroll = new NQEnrollment();

		
			Web.waitForElement(nqEnroll, "Button Continue To Enrollment");
			
			Web.clickOnElement(nqEnroll, "Button Continue To Enrollment");
			
			Web.waitForElement(nqEnroll,"DISTRIBUTION ELECTIONS");
			
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
					
					mapDistributionReasonAndMethod.put(disbReason.get(i), disbMethod.get(i));
					
					mapDistributionMethodAndTiming.put(disbMethod.get(i),DisbTiming.get(i) );
					
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
			
			Web.waitForElement(nqEnroll,"DEFERRAL ELECTION");
			Deferrals deferral=new Deferrals();
			
			String contributionRate=Stock.GetParameterValue("contributionRate");
			deferral.selectContributionWhileEnroll(contributionRate);
			nqEnroll.addCarryOverContribution(Stock.GetParameterValue("carryOverOption"));
			nqEnroll.clickIAgreeAndEnrollNowButton();
			Common.waitForProgressBar();
			nqEnroll.clickContinue();
			Common.waitForProgressBar();
			
			//Step 1 to 6
			accountOverview.clickOnEnrollNow();
			Common.waitForProgressBar();
			String ssn=Stock.GetParameterValue("userName").substring(0, 9);
		
			Web.waitForElement(nqEnroll, "Button Continue");
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
			nqEnroll.clickContinue();
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
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
				}
			}
			nqEnroll.VerifyDeferralElectionPageAsReturningUser(contributionRate);
			//Step 8 and 9 are duplicate which are verified in Step 6
			
			//step 10
			
			nqEnroll.clickEditMyOptionsButton();
			nqEnroll.verifyPageHeaderIsDisplayed("DISTRIBUTION ELECTIONS");
			
			//Step 11
			
			nqEnroll.VerifyDistibutionElectionPageAsReturningUser(mapDistributionReasonAndMethod, mapDistributionMethodAndTiming);
			//Step 12
			
			if (disbReason.size() > 0) {
				int j=1;
			
				for (int i = 0; i < disbReason.size(); i++) {
					
				
					disbMethod = Common
							.getMethodForDisbursmentReason(
									Stock.GetParameterValue("ga_id"),
									disbReason.get(i));
					nqEnroll.selectDisbursementMethod(disbReason.get(i),
							disbMethod.get(i));
					 DisbTiming = Common.getTimingForDisbursmentMethod(
							Stock.GetParameterValue("ga_id"), disbMethod.get(i));
					nqEnroll.selectTimingForDistributionElectionMethod(DisbTiming.get(j));
					
					mapDistributionElections.put(disbReason.get(i), disbMethod.get(i)+" - "+DisbTiming.get(j));
				}
			}
		
				nqEnroll.clickContinue();
			
				deferral.selectContributionWhileEnroll(contributionRate);
				nqEnroll.addCarryOverContribution(Stock.GetParameterValue("CarryOver_Option"));
				Web.clickOnElement(deferral, "Continue Button");
			
			//Step 13
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
				}
			}
			
		}
				

		catch (Exception e) {
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
	 *  User is Logged in as Returning user in the same Enrollment Window user should
	 *   be able to modify Distribution Elections  
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27002_NQ_SE_Via_AccountOverview_Verify_Deferral_Election_Page_As_Returning_User_Same_Enrollment_Window(
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
			
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
		
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_id"));

	
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
	
			accountOverview.clickOnEnrollNow();
	
			NQEnrollment nqEnroll = new NQEnrollment();

		
			String ssn=Stock.GetParameterValue("userName").substring(0, 9);
		
			/*Web.waitForElement(nqEnroll, "Button Continue");
			nqEnroll.VerifyModalPopupForReturningUser(Stock.GetParameterValue("ga_id"), ssn);
			
			nqEnroll.clickContinue();*/
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
			
			
		//	nqEnroll.VerifyDeferralElectionPageAsReturningUser(contributionRate);
			//Step 8 and 9 are duplicate which are verified in Step 6
			
			//step 10
			
			nqEnroll.clickEditMyOptionsButton();
			nqEnroll.verifyPageHeaderIsDisplayed("DEFERRAL ELECTIONS");
			
			//Step 11
			
			
			
			/*	deferral.selectContributionWhileEnroll(contributionRate);
				nqEnroll.addCarryOverContribution(Stock.GetParameterValue("CarryOver_Option"));
				Web.clickOnElement(deferral, "Continue Button");*/
			
			//Step 13
			if (disbReason.size() > 0) {
				for (int i = 0; i < disbReason.size(); i++) {
			nqEnroll.VerifyDisbursementMethodIsDisplayed(disbReason.get(i), mapDistributionElections.get(disbReason.get(i)));
				}
			}
			
		}
				

		catch (Exception e) {
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
