package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.withdrawals.RequestWithdrawal;
import pageobjects.withdrawals.RequestWithdrawal_RMD;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class withdrawalsrmdtestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;

	static String printTestData = "";
	static int enteredRothWithdrawalAmt = 0;
	static int enteredPreTaxWithdrawalAmt = 0;
	int enteredTotalWithdrawalAmt = 0;
	String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String lastYear =String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
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
	public void Verify_Warning_Message(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage
						.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.updateFields(Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("planId"), Stock.GetParameterValue("rmd_Date"),
					Stock.GetParameterValue("sqlUpdateRMDDate"));
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
			requestWithdrawal_rmd.verifyMandatoryText();
			requestWithdrawal_rmd.verifyRMDDetails();
			requestWithdrawal_rmd.verifyEstimatedTax();
			requestWithdrawal_rmd.verifyDeliveryMethod();
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"Warning Message");
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29608_SelfService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29619_SelfService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29622_FullService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29623_FullService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29624_FullService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29627_FullService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29666_SelfService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29668_SelfService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29685_RMD_Verifying_How_is_this_Calculated_Modal_in_RMD_screen(
			int itr, Map<String, String> testdata) throws Exception {
		try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
				+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
		lib.Reporter.logEvent(Status.INFO,
				"Test Data used for this Test Case:", printTestData(), false);
		LoginPage login = new LoginPage();
		TwoStepVerification mfaPage = new TwoStepVerification(login);
		LandingPage homePage = new LandingPage(mfaPage);
		MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
		myAccountPage.get();
		if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
			myAccountPage
					.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
		}
		LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
		RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
				lftNavBar);
		requestWithdrawal_rmd.get();
		requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
		requestWithdrawal_rmd.verifyRMDDetails();
		requestWithdrawal_rmd.validateHowIsThisCalculatedLink(Stock.GetParameterValue("getBalanceAmountDB"),
				Stock.GetParameterValue("userName"));
		
		//requestWithdrawal_rmd.getRMDDetails();
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
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29686_RMD_Verifying_Tax_Withholdings_Modal_in_RMD_screen(
			int itr, Map<String, String> testdata) throws Exception {
		try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
				+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
		lib.Reporter.logEvent(Status.INFO,
				"Test Data used for this Test Case:", printTestData(), false);
		LoginPage login = new LoginPage();
		TwoStepVerification mfaPage = new TwoStepVerification(login);
		LandingPage homePage = new LandingPage(mfaPage);
		MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
		myAccountPage.get();
		if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
			myAccountPage
					.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
		}
		LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
		RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
				lftNavBar);
		requestWithdrawal_rmd.get();
		requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
		requestWithdrawal_rmd.verifyEstimatedTax();
		requestWithdrawal_rmd.validateModifyWithholdingLink();
		
		//requestWithdrawal_rmd.getRMDDetails();
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
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29759_RMD_Verifying_Delivery_Method_Modal_in_RMD_screen(
			int itr, Map<String, String> testdata) throws Exception {
		try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
				+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
		lib.Reporter.logEvent(Status.INFO,
				"Test Data used for this Test Case:", printTestData(), false);
		LoginPage login = new LoginPage();
		TwoStepVerification mfaPage = new TwoStepVerification(login);
		LandingPage homePage = new LandingPage(mfaPage);
		MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
		myAccountPage.get();
		if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
			myAccountPage
					.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
		}
		LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
		RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
				lftNavBar);
		requestWithdrawal_rmd.get();
		requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
		requestWithdrawal_rmd.verifyDeliveryMethod();
		if(itr==1)
			requestWithdrawal_rmd.validateChangeMethodLink(false);
		else
			requestWithdrawal_rmd.validateChangeMethodLink(true);
		
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
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Verifying_Informational_Message(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage
						.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.updateFields(Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("gcId"), Stock.GetParameterValue("percent_Ownership"),
					Stock.GetParameterValue("sqlUpdateOwnersip"));
			requestWithdrawal_rmd.get();
			
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"TxtInfoMessage");
			Web.clickOnElement(requestWithdrawal_rmd, "How is this calculated");
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"TxtDistributionMessage");
			Web.clickOnElement(requestWithdrawal_rmd, "Close");
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29975_Verifying_Informational_Message_5_or_greater_owner_using_joint_life_table_and_has_Spouse_DOB_on_file(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Informational_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29987_Verifying_Informational_Message_5_owner_using_joint_life_table_and_has_Spouse_DOB_on_file(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Informational_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29988_Verifying_Informational_Message_greate_than_5_owner_using_uniform_life_table_and_has_No_Spouse_DOB_on_file(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Informational_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29990_Verifying_Informational_Message_less_than_5_owner_using_uniform_life_table_and_has_No_Spouse_DOB_on_file(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Informational_Message(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29793_PPTWEB_RMD_confirmation_screen_Proactive_enabled_for_RMD_withdrawal(int itr, Map<String, String> testdata)
	{
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage
						.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal_rmd.Verify_confirmation_page_content();
			requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDProcessing();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateProactiveNotification(Stock.GetParameterValue("selectRMDBy"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock.GetParameterValue("userName"));
			//requestWithdrawal_rmd.validateEventTable(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("userName"));
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29938_Request_Current_Year_RMD_with_proactive_notification_type_selected_as_Email
	(int itr, Map<String, String> testdata)
	{
		Verifying_Confirmation_Page(itr, testdata);
		}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29939_RMD_Request_Current_Year_RMD_with_proactive_notification_type_selected_as_Text_Message
	(int itr, Map<String, String> testdata)
	{
		Verifying_Confirmation_Page(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29941_RMD_Request_Current_Year_RMD_with_proactive_notification_type_selected_as_Text_Message_and_Email(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Confirmation_Page(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Verifying_Confirmation_Page(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
				myAccountPage
						.clickPlanNameByGAID(Stock.GetParameterValue("planId"));
			}
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDProcessing();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateProactiveNotification(Stock.GetParameterValue("selectRMDBy"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock.GetParameterValue("userName"));
			requestWithdrawal_rmd.validateEventTable(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("userName"));
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
					RequestWithdrawal.resetTotalValues();
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	}
}