package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.Keys;
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
	public void Verify_Warning_Message_SelfService(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			String[] updateQuery = Stock.getTestQuery("updateRMDService");
			
			int updateService=0;
			updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateService = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "01-JAN-2017", Stock.GetParameterValue("planId"));
			if(updateService>0)
			{
				Reporter.logEvent(
						Status.PASS,"Verify plan is self Service","plan is self Service",
						false);
			
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
						Stock.GetParameterValue("rmd_Date"),
						Stock.GetParameterValue("sqlUpdateRMDDate"));
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
				requestWithdrawal_rmd.verifyMandatoryText();
				requestWithdrawal_rmd.verifyRMDDetails();
				requestWithdrawal_rmd.verifyEstimatedTax();
				requestWithdrawal_rmd.verifyDeliveryMethod();
				requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"Warning Message");
			}
			else
				Reporter.logEvent(
						Status.FAIL,"Verify plan is self Service","plan is not self Service",
						false);
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
	public void Verify_Warning_Message_FullService(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			String[] updateQuery = Stock.getTestQuery("updateRMDService");
			
			int updateService=0;
			updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateService = DB.executeUpdate(updateQuery[0],
					updateQuery[1], null, Stock.GetParameterValue("planId"));
			if(updateService>0)
			{
				Reporter.logEvent(
						Status.PASS,"Verify plan is full Service","plan is full Service",
						false);
			
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
						Stock.GetParameterValue("rmd_Date"),
						Stock.GetParameterValue("sqlUpdateRMDDate"));
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
				requestWithdrawal_rmd.verifyMandatoryText();
				requestWithdrawal_rmd.verifyRMDDetails();
				requestWithdrawal_rmd.verifyEstimatedTax();
				requestWithdrawal_rmd.verifyDeliveryMethod();
				requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"Warning Message");
			}
			else
				Reporter.logEvent(
						Status.FAIL,"Verify plan is full Service","plan is not full Service",
						false);
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
		
		
		Verify_Warning_Message_SelfService(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29619_SelfService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_SelfService(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29622_FullService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_FullService(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29623_FullService_NonGrace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_FullService(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29624_FullService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_FullService(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29627_FullService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_FullService(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29666_SelfService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_current_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_SelfService(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_29668_SelfService_Grace_plan_take_RMD_in_current_calendar_year_with_RMD_starting_in_the_previous_calendar_year(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Warning_Message_SelfService(itr, testdata);
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
					Stock.GetParameterValue("percent_Ownership"),
					Stock.GetParameterValue("sqlUpdateOwnersip"));
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"TxtInfoMessage");
			Web.clickOnElement(requestWithdrawal_rmd, "How is this calculated");
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("informationalMessage"),"TxtDistributionMessageModal");
			
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
	
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29981_PPT_with_Spousal_Consent_route_to_Withdrawals_flow_and_stops_after_sending_minimally_pre_filled_form_or_email(
			int itr, Map<String, String> testdata) {
		String[] updateQuery = Stock.getTestQuery("getSpousalConsentPpt");
		String gcId[]=Stock.GetParameterValue("planId").split("-");
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");
		int spousalRowUpdated = 0, pptTermDate=0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateQuery[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptTermDate = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], null, "4",
					Stock.GetParameterValue("ind_ID"), gcId[0]);
			spousalRowUpdated = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "Y", "N", gcId[0]);
			
			if (spousalRowUpdated > 0 && pptTermDate>0) {
				System.out.println("Spousal consent Data is ready");
				Reporter.logEvent(
						Status.PASS,
						"Verify Spousal Consent Data is Set Up for the participant",
						"Spousal Consent Data is Set up for this participant",
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
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD();
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//Requierd
				/*if(requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType")))		
					requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));	
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
				{
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Common.waitForProgressBar();
				}
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				
				requestWithdrawal_rmd.validateAlertText(Stock.GetParameterValue("alertText"));*/
				
				
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Spousal Data is Set Up",
						"Spousal Data is NOT Set up for this participant",
						false);
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				
				updateOwnerShip[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptTermDate = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1], null, "6",
						Stock.GetParameterValue("ind_ID"), gcId[0]);
				updateQuery[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				spousalRowUpdated = DB.executeUpdate(updateQuery[0],
						updateQuery[1], "N", "N", gcId[0]);
				
				if (spousalRowUpdated > 0)
					System.out.println("Spousal consent Data is reverted back");
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29706_PPT_with_QJSA_Consent_route_to_Withdrawals_flow_and_stops_after_sending_minimally_pre_filled_form_or_email(
			int itr, Map<String, String> testdata) {
		String[] updateQuery = Stock.getTestQuery("getSpousalConsentPpt");
		String gcId[]=Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		int spousalRowUpdated = 0, pptTermDate=0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateQuery[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			spousalRowUpdated = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "Y", "Y", gcId[0]);
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],null, Stock.GetParameterValue("ind_ID") ,gcId[0]);
			if (spousalRowUpdated > 0 && pptTermDate>0) {
				System.out.println("QJSA consent Data is ready");
				Reporter.logEvent(
						Status.PASS,
						"Verify QJSA Consent Data is Set Up for the participant",
						"QJSA Consent Data is Set up for this participant",
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
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				//requierd
				/*if(requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType")))		
					requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));	
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
				{
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Common.waitForProgressBar();
				}
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD();
				requestWithdrawal_rmd.validateAlertText(Stock.GetParameterValue("alertText"));*/
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify QJSA Data is Set Up",
						"QJSA Data is NOT Set up for this participant",
						false);
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				
				updateTermDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				updateQuery[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				spousalRowUpdated = DB.executeUpdate(updateQuery[0],
						updateQuery[1], "N", "N", gcId[0]);
				pptTermDate = DB.executeUpdate(updateTermDate[0],
						updateTermDate[1],"20-MAR-14", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				if (spousalRowUpdated > 0)
					System.out.println("QJSA consent Data is reverted back");
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				//RequestWithdrawal.resetTotalValues();
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29707_Sep_Service_PPT_with_SDB_route_to_Withdrawals_flow_and_stops_after_sending_minimally_pre_filled_form_or_email(
			int itr, Map<String, String> testdata) {
		String[] updateQuery = Stock.getTestQuery("getSpousalConsentPpt");
		String gcId[]=Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		int spousalRowUpdated = 0, pptTermDate=0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateQuery[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			spousalRowUpdated = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "Y", "Y", gcId[0]);
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],"21-NOV-17", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			if (spousalRowUpdated > 0 && pptTermDate>0) {
				System.out.println("QJSA consent Data is ready");
				Reporter.logEvent(
						Status.PASS,
						"Verify QJSA Consent Data is Set Up for the participant",
						"QJSA Consent Data is Set up for this participant",
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
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD();
				requestWithdrawal_rmd.validateAlertText(Stock.GetParameterValue("alertText"));
				requestWithdrawal_rmd.validatePSRText(Stock.GetParameterValue("psrText"),Stock.GetParameterValue("emailToForm"));
				} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify QJSA Data is Set Up",
						"QJSA Data is NOT Set up for this participant",
						false);
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				
				updateTermDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				updateQuery[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				spousalRowUpdated = DB.executeUpdate(updateQuery[0],
						updateQuery[1], "N", "N", gcId[0]);
				pptTermDate = DB.executeUpdate(updateTermDate[0],
						updateTermDate[1],"21-NOV-17", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				if (spousalRowUpdated > 0)
					System.out.println("QJSA consent Data is reverted back");
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				RequestWithdrawal.resetTotalValues();
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29766_PPTWEB_RMD_In_Service_PPT_with_SDB_route_to_Withdrawals_flow(
			int itr, Map<String, String> testdata) {
		String[] updateQuery = Stock.getTestQuery("getSpousalConsentPpt");
		String gcId[]=Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		int spousalRowUpdated = 0, pptTermDate=0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateQuery[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			spousalRowUpdated = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "Y", "N", gcId[0]);
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],null, Stock.GetParameterValue("ind_ID") ,gcId[0]);
			if (spousalRowUpdated > 0 && pptTermDate>0) {
				System.out.println("Spousal consent Data is ready");
				Reporter.logEvent(
						Status.PASS,
						"Verify Spousal Consent Data is Set Up for the participant",
						"Spousal Consent Data is Set up for this participant",
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
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if(requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType")))		
					requestWithdrawal.enterWithdrawalAmountForInService(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("isRothAvail"), 
							Stock.GetParameterValue("isNonRothAvail"));	
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE"))
				{
					Web.clickOnElement(requestWithdrawal, "CONTINUE");
					Common.waitForProgressBar();
				}
				else
					throw new Error("Continue button is not Displayed");
				
				requestWithdrawal.isTextFieldDisplayed("Plan withdrawal");
				requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				Web.waitForElement(requestWithdrawal, "YES");
				Thread.sleep(2000);
				Web.clickOnElement(requestWithdrawal, "YES");
				Common.waitForProgressBar();
				Web.setTextToTextBox("SSN", requestWithdrawal, Stock.GetParameterValue("SSN"));
				Actions keyboardEvent=new Actions(Web.getDriver());
				keyboardEvent.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD();
				requestWithdrawal_rmd.validateAlertText(Stock.GetParameterValue("alertText"));
				if(Web.isWebElementDisplayed(requestWithdrawal, "CONFIRM AND CONTINUE"))
				Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
				Web.waitForPageToLoad(Web.getDriver());
				
				requestWithdrawal.verifyWithdrawalMethodPage(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("withdrawalMethod"),
						Stock.GetParameterValue("emailAddress"));
				requestWithdrawal.verifyWithdrawalSummary(Stock.GetParameterValue("deliveryMethod"),true);				
				//requestWithdrawal_rmd.validatePSRText(Stock.GetParameterValue("psrText"),Stock.GetParameterValue("emailToForm"));
				requestWithdrawal.verifyWithdrawalConfForSpousalConsentPpts(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("ind_ID"));
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Spousal Data is Set Up",
						"Spousal Data is NOT Set up for this participant",
						false);
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				
				updateTermDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				updateQuery[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				spousalRowUpdated = DB.executeUpdate(updateQuery[0],
						updateQuery[1], "N", "N", gcId[0]);
				pptTermDate = DB.executeUpdate(updateTermDate[0],
						updateTermDate[1],"21-NOV-17", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				if (spousalRowUpdated > 0)
					System.out.println("Spousal consent Data is reverted back");
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				//RequestWithdrawal.resetTotalValues();
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29699_PPT_with_Wait_Period_condition_route_to_Withdrawals_flow(
			int itr, Map<String, String> testdata) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-yy");

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.minusDays(7);
		String date = String.format(then.format(format));
		
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		int pptTermDate = 0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],date, Stock.GetParameterValue("ind_ID") ,gcId[0]);
			if (pptTermDate>0) {
				System.out.println("Wait period Data is Set");
				Reporter.logEvent(
						Status.PASS,
						"Verify wait period Data is Set Up for the participant",
						"Wait period is Set up for this participant",
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
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify wait period Data is Set Up for the participant",
						"Wait period is not Set up for this participant",
						false);
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
				updateTermDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				
				pptTermDate = DB.executeUpdate(updateTermDate[0],
						updateTermDate[1],"07-SEP-16", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				RequestWithdrawal.resetTotalValues();
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29946_screen_with_proactive_notification_not_set_up_for_the_particpants_plan(int itr, Map<String, String> testdata){
		
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
			/*requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDProcessing();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateProactiveNotification(Stock.GetParameterValue("selectRMDBy"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock.GetParameterValue("userName"));
			requestWithdrawal_rmd.validateEventTable(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("userName"));*/
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
	public void Withdrawal_RMD_DDTC_29637_Routing_to_withdrawal_flow_for_PPT_with_age_less_than_70_and_5_Percent_owner_with_KTMG_enabled_for_RMD(
			int itr, Map<String, String> testdata) {
		int pptBirthDate = 0, pptOwnerShip=0;
		String[] updateOwnerShip = Stock.getTestQuery("updateOwnership");
		String[] updateBirthDate = Stock.getTestQuery("updateBirthDate");
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateBirthDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptBirthDate = DB.executeUpdate(updateBirthDate[0],
					updateBirthDate[1], "01-JUL-1950",
					Stock.GetParameterValue("ind_ID"));

			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "4", Stock.GetParameterValue("ind_ID"),
					gcId[0]);
			System.out.println("Birth date "+pptBirthDate+"OwnerShip "+pptOwnerShip);
			if (pptBirthDate > 0 && pptOwnerShip > 0) {
				System.out.println("Birth date is set to less than 70.5");
				Reporter.logEvent(
						Status.PASS,
						"Verify Birth date is less than 70.5 years Set Up for the participant",
						"Birth date "+pptBirthDate+"OwnerShip "+pptOwnerShip, false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true)) {
					Reporter.logEvent(
							Status.INFO,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Birth date is less than 70.5 years Set Up for the participant",
						" Birth date is not set to less than 70.5 years", false);
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
				updateBirthDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptBirthDate = DB.executeUpdate(updateBirthDate[0],
						updateBirthDate[1], "01-JUL-1946",
						Stock.GetParameterValue("ind_ID"));
				if (pptBirthDate > 0){
					System.out.println("Reverted the date back to normal");
					Reporter.logEvent(Status.PASS, "Change the birth date",
							"Birth date is changed back to normal", false);
				}
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29636_PPTWEB_RMD_Routing_of_RMD_flow_when_KTMG_service_call_is_successful(int itr, Map<String, String> testdata){
		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser() + "_" + Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(), false);
			String[] updateQuery = Stock.getTestQuery("updateRMDService");
			
			int updateService=0;
			updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateService = DB.executeUpdate(updateQuery[0],
					updateQuery[1], "01-JAN-2017", Stock.GetParameterValue("planId"));
			if(updateService>0)
			{
				Reporter.logEvent(
						Status.PASS,"Verify plan is self Service","plan is self Service",
						false);
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
			requestWithdrawal_rmd.verifyMandatoryText();
			requestWithdrawal_rmd.verifyRMDDetails();
			requestWithdrawal_rmd.verifyEstimatedTax();
			requestWithdrawal_rmd.verifyDeliveryMethod();
			Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			requestWithdrawal_rmd.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			}
			else
				Reporter.logEvent(
						Status.FAIL,"Verify plan is self Service","plan is not self Service",
						false);
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
	public void Withdrawal_RMD_DDTC_29625_Routing_to_RMD_flow_qualifies_age_greater_than_70_terminated_and_Mindistr_amt_greater_than_0_with_KTMG_enabled(
			int itr, Map<String, String> testdata) {
		
		String gcId[]=Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		int pptTermDate=0;
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],"21-NOV-17", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			if (pptTermDate>0) {
				System.out.println("Term date is set to the PPT");
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set to the participant",
						"Term date is set to the PPT",
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
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
				requestWithdrawal_rmd.verifyMandatoryText();
				requestWithdrawal_rmd.verifyRMDDetails();
				requestWithdrawal_rmd.verifyEstimatedTax();
				requestWithdrawal_rmd.verifyDeliveryMethod();
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				requestWithdrawal_rmd.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Term date is set to the participant",
						"Term date is not set to the PPT",
						false);
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
	public void Withdrawal_RMD_DDTC_29912_RMD_Citizenship_screen_validations(
			int itr, Map<String, String> testdata) {
		int pptTermDate=0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateOwnershipAndTermDate");
		try {
			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],"07-SEP-16","4", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			
			if (pptTermDate>0) {
				System.out.println("Term date is set");
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is set", false);
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
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				lftNavBar.clickNavigationLink("REQUEST A WITHDRAWAL");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					pptTermDate = DB.executeUpdate(updateTermDate[0],
							updateTermDate[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					pptTermDate = DB.executeUpdate(updateTermDate[0],
							updateTermDate[1],"07-SEP-16","4", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
				}
				if (Web.isWebElementDisplayed(requestWithdrawal_rmd,"Required Minimum Distribution", true))
					Reporter.logEvent(Status.PASS,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is displayed successfully", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is NOT displayed", true);
				
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				requestWithdrawal_rmd.ssnValidation(Stock
						.GetParameterValue("wrongSSN"),"No");
				requestWithdrawal_rmd.ssnValidation(Stock
						.GetParameterValue("SSN"),"Yes");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			}
			else
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is not set", false);
			
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
				if(Stock.getConfigParam("AUT").contains("apple"))
				{
					RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
					Web.clickOnElement(requestWithdrawal, "LOGOUT");
				}
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29899_Request_Current_Year_RMD_with_Terminated_Participant_Non_5_percent_owner_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		
		
		int pptTermDate=0,pptBeginDate=0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateOwnershipAndTermDate");
		String[] updateBeginDate = Stock.getTestQuery("updateRMDDate");
		try {
			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateBeginDate[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			pptTermDate = DB.executeUpdate(updateBeginDate[0],
					updateBeginDate[1],Stock.GetParameterValue("rmd_Date"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("planId"));
			
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			System.out.println("ENV:"+Stock.getConfigParam("AUT"));
			pptBeginDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],"07-SEP-16","4", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			
			if (pptTermDate>0 && pptBeginDate>0) {
				System.out.println("Term date is set");
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is set", false);
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
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				lftNavBar.clickNavigationLink("REQUEST A WITHDRAWAL");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					pptTermDate = DB.executeUpdate(updateTermDate[0],
							updateTermDate[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					pptTermDate = DB.executeUpdate(updateTermDate[0],
							updateTermDate[1],"07-SEP-16","4", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
				}
				if (Web.isWebElementDisplayed(requestWithdrawal_rmd,"Required Minimum Distribution", true))
					Reporter.logEvent(Status.PASS,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is displayed successfully", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is NOT displayed", true);
				
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				
				requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
				requestWithdrawal_rmd.validateFianlSubmitRMD();
			}
			else
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is not set", false);
			
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
				if(Stock.getConfigParam("AUT").contains("apple"))
				{
					RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
					Web.clickOnElement(requestWithdrawal, "LOGOUT");
				}
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29901_Request_Current_Year_RMD_with_Active_Participant_Non_5_percent_owner_RMD_begin_year_is_current_year(
			int itr, Map<String, String> testdata) {
		
		
		int pptDate=0,pptTermDate=0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateOwnershipAndTermDate");
		String[] updateBeginDate = Stock.getTestQuery("updateRMDDate");
		try {
			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateBeginDate[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			pptDate = DB.executeUpdate(updateBeginDate[0],
					updateBeginDate[1],Stock.GetParameterValue("rmd_Date"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("planId"));
			
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],null,"4", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			
			
			if (pptDate > 0 && pptTermDate>0) {
				System.out.println("Ownership is set < 5 and Term date is null");
				Reporter.logEvent(
						Status.PASS,
						"Verify Ownership is set < 5 and Term date is null",
						" Ownership is set < 5 and Term date is null", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Ownership is set < 5 and Term date is set to null",
						"Ownership is not set < 5 and Term date is set to null", false);
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
				if(Stock.getConfigParam("AUT").contains("apple"))
				{
					RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
					Web.clickOnElement(requestWithdrawal, "LOGOUT");
				}
					
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29904_Request_Current_Year_RMD_with_Active_Participant_5_percent_owner_RMD_begin_year_is_current_year(
			int itr, Map<String, String> testdata) {
		
		
		int pptTermDate=0,pptOwnerShip=0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateTermDate = Stock.getTestQuery("updateOwnershipAndTermDate");
		String[] sqlQueryHomeAddress = Stock.getTestQuery("updateRMDDate");
		try {
			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			sqlQueryHomeAddress[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			pptTermDate = DB.executeUpdate(sqlQueryHomeAddress[0],
					sqlQueryHomeAddress[1],Stock.GetParameterValue("rmd_Date"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("planId"));
			
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptOwnerShip = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			
			if (pptTermDate>0 && pptOwnerShip>0) {
				System.out.println("Term date is set");
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is set", false);
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
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				
				requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
				requestWithdrawal_rmd.validateFianlSubmitRMD();
			}
			else
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is not set", false);
			
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
				if(Stock.getConfigParam("AUT").contains("apple"))
				{
					RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
					Web.clickOnElement(requestWithdrawal, "LOGOUT");
				}
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawals_RMD_DDTC_29907_Request_Current_Year_RMD_with_Terminated_Participant_5_percent_owner_RMD_begin_year_is_current_year(
			int itr, Map<String, String> testdata) {
		
		
		int pptTermDate=0,pptOwnerShip=0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateOwnerShip = Stock.getTestQuery("updateOwnershipAndTermDate");
		String[] updateRMDDate = Stock.getTestQuery("updateRMDDate");
		try {
			
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateRMDDate[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			pptTermDate = DB.executeUpdate(updateRMDDate[0],
					updateRMDDate[1],Stock.GetParameterValue("rmd_Date"),Stock.GetParameterValue("ind_ID"),
					Stock.GetParameterValue("planId"));
			
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1],"07-SEP-16","6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			
			if (pptTermDate>0 && pptOwnerShip>0) {
				System.out.println("Term date is set");
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is set", false);
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
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				lftNavBar.clickNavigationLink("REQUEST A WITHDRAWAL");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					pptTermDate = DB.executeUpdate(updateOwnerShip[0],
							updateOwnerShip[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					
					pptTermDate = DB.executeUpdate(updateOwnerShip[0],
							updateOwnerShip[1],"07-SEP-16","6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					
				}
				if (Web.isWebElementDisplayed(requestWithdrawal_rmd,"Required Minimum Distribution", true))
					Reporter.logEvent(Status.PASS,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is displayed successfully", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify if Withdrawal - RMD Page is Displayed",
							"Withdrawal - RMD Page is NOT displayed", true);
				
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				
				requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
				requestWithdrawal_rmd.validateFianlSubmitRMD();
			}
			else
				Reporter.logEvent(
						Status.PASS,
						"Verify Term date is set",
						"Term date is not set", false);
			
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
				updateOwnerShip[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				
				pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				if(Stock.getConfigParam("AUT").contains("apple"))
				{
					RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
					Web.clickOnElement(requestWithdrawal, "LOGOUT");
				}
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29931_RMD_Request_Current_Year_RMD_with_Regular_Mail_Delivery_Method(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Delivery_Method(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29934_RMD_Request_Current_Year_RMD_with_Expedited_Mail_Delivery_Method(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Delivery_Method(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29936_RMD_Request_Current_Year_RMD_with_ACH_Mail_Delivery_Method(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_Delivery_Method(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Verify_Delivery_Method(int itr, Map<String, String> testdata) {

		int pptOwnerShip = 0;
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");
		
		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1],"07-SEP-16","6", Stock.GetParameterValue("ind_ID") ,gcId[0]);

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
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			lftNavBar.clickNavigationLink("REQUEST A WITHDRAWAL");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true))
			{
				pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1],null,"6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				Web.getDriver().navigate().refresh();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				
				pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1],"07-SEP-16","6", Stock.GetParameterValue("ind_ID") ,gcId[0]);
				Web.getDriver().navigate().refresh();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				
			}
			if (Web.isWebElementDisplayed(requestWithdrawal_rmd,"Required Minimum Distribution", true))
				Reporter.logEvent(Status.PASS,
						"Verify if Withdrawal - RMD Page is Displayed",
						"Withdrawal - RMD Page is displayed successfully", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Withdrawal - RMD Page is Displayed",
						"Withdrawal - RMD Page is NOT displayed", true);
			requestWithdrawal_rmd.selectRMDBy(
					Stock.GetParameterValue("selectRMDBy"),
					Stock.GetParameterValue("selectDeliveryMethod"));

			Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");

			requestWithdrawal_rmd.citizenShipValidation(Stock
					.GetParameterValue("SSN"));
			requestWithdrawal_rmd
					.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock
					.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock
					.GetParameterValue("userName"));
			requestWithdrawal_rmd.validateFianlSubmitRMD();
			/*
			 * } else Reporter.logEvent(Status.PASS, "Verify Term date is set",
			 * "Term date is not set", false);
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

				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29671_confirmation_screen_for_current_year_RMD_withdrawal(int itr, Map<String, String> testdata)
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
			requestWithdrawal_rmd.verifyHeaderDetailsCurrentYear(currentYear);
			requestWithdrawal_rmd.verifyMandatoryText();
			requestWithdrawal_rmd.verifyRMDDetails();
			requestWithdrawal_rmd.verifyEstimatedTax();
			requestWithdrawal_rmd.verifyDeliveryMethod();
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
	public void Withdrawal_RMD_DDTC_29640_PPTWEB_RMD_Routing_of_regular_WD_flow_when_KTMG_turned_off_for_RMD(
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
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					Reporter.logEvent(Status.INFO,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
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
				
				RequestWithdrawal.resetTotalValues();
			    Reporter.finalizeTCReport();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29626_routing_of_RMD_doesnot_qualify_RMD_such_as_age_greater_70_and_employed_and_non_5_Owner_with_KTMG_disabled(
			int itr, Map<String, String> testdata) {
		int pptTermDate = 0, pptOwnership=0;
		String[] updateOwnerShip = Stock.getTestQuery("updateOwnership");
		String[] updateTermDate = Stock.getTestQuery("updateTermDate");
		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateTermDate[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			pptTermDate = DB.executeUpdate(updateTermDate[0],
					updateTermDate[1],null, Stock.GetParameterValue("ind_ID") ,gcId[0]);
			
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnership = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "4", Stock.GetParameterValue("ind_ID"),
					gcId[0]);
			System.out.println("Term date "+pptTermDate+"OwnerShip "+pptOwnership);
			if (pptOwnership > 0 && pptTermDate > 0) {
				System.out.println("Updated the term date and ownership");
				Reporter.logEvent(
						Status.PASS,
						"Updated the term date and ownership for the participant",
						"Updated the term date and ownership", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				requestWithdrawal.get();
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true)) {
					Reporter.logEvent(
							Status.INFO,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed after setting the wait period",
							"Request A Withdrawal Page is Displayed", true);
					throw new Error("Withdrawal Page is not displayed");
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Updated the term date and ownership",
						"Did not updated the term date and ownership", false);
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
				updateOwnerShip[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptOwnership = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1], "6", Stock.GetParameterValue("ind_ID"),
						gcId[0]);

				if (pptOwnership > 0){
					System.out.println("Reverted the data back to normal");
					Reporter.logEvent(Status.PASS, "Change the birth date",
							"Birth date is changed back to normal", false);
				}
				RequestWithdrawal.resetTotalValues();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30837_PPTWEB_Grace_RMD_Confirmation_page_with_only_Grace_RMD_submit(int itr, Map<String, String> testdata) {

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
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
			requestWithdrawal_rmd.verifyDeliveryMethod(Stock.GetParameterValue("sOnlyRMD"));
			requestWithdrawal_rmd.validateChangeMethodLink(true);
			requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
					Stock.GetParameterValue("selectGraceDeliveryMethod"));
			requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDProcessing();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateProactiveNotification(Stock.GetParameterValue("selectRMDBy"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock.GetParameterValue("userName"));
			
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
	public void Verify_GraceCurrentYear_ConfirmatioPage(int itr, Map<String, String> testdata) {
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
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
			requestWithdrawal_rmd.verifyDeliveryMethod();
			requestWithdrawal_rmd.validateChangeMethodLink(true);
			requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
					Stock.GetParameterValue("selectGraceDeliveryMethod"));
			requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
			requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			requestWithdrawal_rmd.validateConfirmationPage_RMDProcessing();
			requestWithdrawal_rmd.validateConfirmationPage_RMDPaymentType(Stock.GetParameterValue("selectDeliveryMethod"));
			requestWithdrawal_rmd.validateProactiveNotification(Stock.GetParameterValue("selectRMDBy"));
			requestWithdrawal_rmd.validateConfirmationPage_FinalAmount(Stock.GetParameterValue("userName"));
			
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
	public void Withdrawal_RMD_DDTC_30934_PPTWEB_Grace_RMD_Request_both_Current_and_Grace_for_delivery_methods_as_Regular_mail_and_Direct_Deposit(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30932_PPTWEB_Grace_RMD_Request_both_Current_and_Grace_for_delivery_methods_as_Regular_mail_and_Expedite_Deposit(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30933_PPTWEB_Grace_RMD_Request_both_Current_and_Grace_for_delivery_methods_as_Expedite_mail_and_Direct_Deposit(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29689_PPTWEB_Grace_RMD_Confirmation_page_with_both_Grace_RMD_and_Current_year_RMD_submit(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30931_PPTWEB_Grace_RMD_Request_RMD_with_both_delivery_methods_selected_as_Direct_Deposit(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30913_PPTWEB_Grace_RMD_Request_Previous_Year_RMD_with_Direct_deposit_Payment_Method(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30952_Grace_RMD_Verify_the_Federal_Tax_withholding_amount_in_the_Confirmation_page(
			int itr, Map<String, String> testdata) throws Exception {
		Verify_GraceCurrentYear_ConfirmatioPage(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29642_Grace_Full_Service_plan_Participant_take_RMD_in_Prev_year_with_RMD_starting_in_the_Previous_year(
			int itr, Map<String, String> testdata) throws Exception {
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			String[] updateQuery = Stock.getTestQuery("updateRMDService");
			int updateService=0;
			updateQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName"))+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			updateService = DB.executeUpdate(updateQuery[0],
					updateQuery[1], null, Stock.GetParameterValue("planId"));
			if(updateService>0)
				Reporter.logEvent(Status.PASS,
						"Verify if RMD Service is updated or not",
						"RMD Service is updated to null", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if RMD Service is updated or not",
						"RMD Service is not updated to null", false);
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
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.updateRMDService(null);
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
			/*requestWithdrawal_rmd.verifyDeliveryMethod();
			requestWithdrawal_rmd.validateChangeMethodLink(true);*/
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("txtIrsRule"),"TxtDistributionMessage");
			requestWithdrawal_rmd.validateWaringMessageforGrace(Stock.GetParameterValue("informationalMessage"),
					Stock.GetParameterValue("informationalMessageGrace"));
			
			
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
	public void Withdrawal_RMD_DDTC_29670_Grace_Self_Service_plan_Participant_take_RMD_in_Prev_year_with_RMD_starting_in_the_Previous_year(
			int itr, Map<String, String> testdata) throws Exception {
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
			RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
					lftNavBar);
			requestWithdrawal_rmd.updateRMDService("01-JAN-17");
			requestWithdrawal_rmd.get();
			requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
			/*requestWithdrawal_rmd.verifyDeliveryMethod();
			requestWithdrawal_rmd.validateChangeMethodLink(true);*/
			requestWithdrawal_rmd.validateInfoMessgae(Stock.GetParameterValue("txtIrsRule"),"TxtDistributionMessage");
			requestWithdrawal_rmd.validateWaringMessageforGrace(Stock.GetParameterValue("informationalMessage"),
					Stock.GetParameterValue("informationalMessageGrace"));
			
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
	public void Withdrawal_RMD_DDTC_29687_Grace_Year_RMD_with_Terminated_Participant_Non_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			
			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "07-SEP-16", "4",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set less than 5 % and ppt is Terminated",
						" Owner Ship is set to less than 5% and ppt is Terminated", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
						lftNavBar);
				lftNavBar.clickNavigationLink("REQUEST A WITHDRAWAL");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				if (Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true))
				{
					pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
							updateOwnerShip[1], null, "6",
							Stock.GetParameterValue("ind_ID"), gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
					pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
							updateOwnerShip[1], "07-SEP-16", "4",
							Stock.GetParameterValue("ind_ID"), gcId[0]);
					Web.getDriver().navigate().refresh();
					Common.waitForProgressBar();
					Web.waitForPageToLoad(Web.getDriver());
				}
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(
						lastYear, currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set less than 5% and ppt is Terminated",
						"Owner Ship is not set tless than 5% and ppt is not Terminated", false);
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30830_Grace_Year_RMD_with_Terminated_Participant_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "07-SEP-16", "6",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set greater than 5% and ppt is Terminated",
						"Owner Ship is set greater than 5% and ppt is Terminated", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(
						lastYear, currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set greater than 5% and ppt is Terminated",
						" Owner Ship is not set greater than 5 % and ppt is Terminated", false);
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30831_Grace_Year_RMD_with_Active_Participant_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], null, "6",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set greater than 5% and ptt is Active",
						" Owner Ship is set greater than 5% and ptt is Active", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(
						lastYear, currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set greater than 5% and ptt is Active",
						" Owner Ship is not set greater than 5% and ptt is Active", false);
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30833_Grace_Year_and_Current_Year_with_Active_Participant_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], null, "6",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set greater than 5% and ptt is Active",
						" Owner Ship is set greater than 5% and ptt is Active", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set greater than 5% and ptt is Active",
						" Owner Ship is not set greater than 5% and ptt is Active", false);
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30835_Grace_Year_and_Current_Year_with_Terminated_Participant_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "07-SEP-16", "6",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set greater than 5% and ptt is Active",
						" Owner Ship is set greater than 5% and ptt is Active", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set greater than 5 %",
						" Owner Ship is not set greater than 5 %", false);
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

				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30836_Grace_Year_and_Current_Year_with_Terminated_Participant_Non_5_percent_owner_and_RMD_begin_year_is_Previous_year(
			int itr, Map<String, String> testdata) {
		int pptOwnerShip = 0;
		String[] updateOwnerShip = Stock
				.getTestQuery("updateOwnershipAndTermDate");

		String gcId[] = Stock.GetParameterValue("planId").split("-");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updateOwnerShip[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
					updateOwnerShip[1], "07-SEP-16", "4",
					Stock.GetParameterValue("ind_ID"), gcId[0]);

			if (pptOwnerShip > 0) {

				Reporter.logEvent(Status.PASS,
						"Verify Owner Ship is set less than 5% and ptt is Terminated",
						" Owner Ship is set less than 5% and ptt is Terminated", false);
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.verifyHeaderDetailsPreviousYears(lastYear,currentYear);
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				//requestWithdrawal_rmd.citizenShipValidation(Stock.GetParameterValue("SSN"));
				//requestWithdrawal_rmd.validateConfirmationPage_RMDConfirmationNumber();
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Owner Ship is set less than 5% and ptt is Terminated",
						" Owner Ship is not set less than 5% and ptt is Terminated", false);
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
				updateOwnerShip[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptOwnerShip = DB.executeUpdate(updateOwnerShip[0],
						updateOwnerShip[1], null, "6",
						Stock.GetParameterValue("ind_ID"), gcId[0]);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29882_PPT_with_Pin_auth_I_PAE_user_with_inquire_and_update_permissions(
			int itr, Map<String, String> testdata) {

		int pptPinAuth = 0;
		String[] updatePinAuth = Stock.getTestQuery("updatePinAuthCode");

		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			updatePinAuth[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptPinAuth = DB.executeUpdate(updatePinAuth[0], updatePinAuth[1],
					Stock.GetParameterValue("pinAuth"),
					Stock.GetParameterValue("planId"),
					Stock.GetParameterValue("ind_ID"));
			if (pptPinAuth > 0) {
				Reporter.logEvent(
						Status.PASS,
						"Verify pin auth is updated",
						"Pin Auth is set to"
								+ Stock.GetParameterValue("pinAuth"), false);

				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				/*requestWithdrawal_rmd.selectRMDBy(
						Stock.GetParameterValue("selectRMDBy"),
						Stock.GetParameterValue("selectDeliveryMethod"));*/
				Web.clickOnElement(requestWithdrawal_rmd, "Request My RMD Button");
				requestWithdrawal_rmd.citizenShipValidation(Stock
						.GetParameterValue("SSN"));
				if(itr==1)
				{
					System.out.println("In Itr1");
					Common.waitForProgressBar();
					requestWithdrawal_rmd.isTextFieldDisplayed("You are not authorized to view this page.");
				}
					
				else
					requestWithdrawal_rmd
					.validateConfirmationPage_RMDConfirmationNumber();
				
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify pin auth is updated",
						"Pin Auth is not set to"
								+ Stock.GetParameterValue("pinAuth"), false);
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
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30928_GRACE_and_Current_yr_RMD_transactions_are_submitted_to_EASY_and_Current_yr_RMD_submit_Fails(
			int itr, Map<String, String> testdata) {

		int pptExpediteChanrge = 0;
		ResultSet getExpediteChanrge;
		String sBeforeExpediteCharge=null;
		String[] updateExpediteCharge = Stock.getTestQuery("updateExpeditedAmount");
		String[] getExpediteCharge = Stock.getTestQuery("getExpeditedAmount");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			getExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			sBeforeExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("Before: ExpediteChanrge="+sBeforeExpediteCharge);
			updateExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],"300000",				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			String sExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("After: ExpediteChanrge="+sExpediteCharge);
			if (pptExpediteChanrge > 0) {
				Reporter.logEvent(
						Status.PASS,
						"Verify ExpediteCharge is greater than RMD amount",
						"ExpediteCharge is set to 3,00,000", false);

				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				requestWithdrawal_rmd.citizenShipValidation(Stock
						.GetParameterValue("SSN"));
				//New Function only for this TC 
				requestWithdrawal_rmd.validateConfirmationPage_RequestFailed(lastYear, currentYear);
				
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify pin auth is updated",
						"Pin Auth is not set to"
								+ Stock.GetParameterValue("pinAuth"), false);
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
				updateExpediteCharge[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],sBeforeExpediteCharge,				
						Stock.GetParameterValue("planId"));
				if (pptExpediteChanrge > 0) {
					Reporter.logEvent(
							Status.PASS,
							"Verify ExpediteCharge is greater than RMD amount",
							"ExpediteCharge is set to "+sBeforeExpediteCharge, false);
				}
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30914_GRACE_RMD_and_Current_year_RMD_transactions_are_submitted_to_EASY_the_GRACE_RMD_fails(
			int itr, Map<String, String> testdata) {

		int pptExpediteChanrge = 0;
		ResultSet getExpediteChanrge;
		String sBeforeExpediteCharge=null;
		String[] updateExpediteCharge = Stock.getTestQuery("updateExpeditedAmount");
		String[] getExpediteCharge = Stock.getTestQuery("getExpeditedAmount");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			getExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			sBeforeExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("Before: ExpediteChanrge="+sBeforeExpediteCharge);
			updateExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],"300000",				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			String sExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("After: ExpediteChanrge="+sExpediteCharge);
			if (pptExpediteChanrge > 0) {
				Reporter.logEvent(
						Status.PASS,
						"Verify ExpediteCharge is greater than RMD amount",
						"ExpediteCharge is set to 3,00,000", false);

				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				requestWithdrawal_rmd.citizenShipValidation(Stock
						.GetParameterValue("SSN"));
				Common.waitForProgressBar();
				requestWithdrawal_rmd.isTextFieldDisplayed("Unable to continue.");
				requestWithdrawal_rmd.isTextFieldDisplayed("An error has occurred. "
						+ "Please contact a Participant Services Representative if the problem persists.");
				
				
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify pin auth is updated",
						"Pin Auth is not set to"
								+ Stock.GetParameterValue("pinAuth"), false);
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
				updateExpediteCharge[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],sBeforeExpediteCharge,				
						Stock.GetParameterValue("planId"));
				if (pptExpediteChanrge > 0) {
					Reporter.logEvent(
							Status.PASS,
							"Verify ExpediteCharge is greater than RMD amount",
							"ExpediteCharge is set to "+sBeforeExpediteCharge, false);
				}
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_30927_Grace_RMD_Submit_Failure_Confirmation_page_display_when_GRACE_RMD_transaction_submitted_to_EASY_Fails(
			int itr, Map<String, String> testdata) {

		int pptExpediteChanrge = 0;
		ResultSet getExpediteChanrge;
		String sBeforeExpediteCharge=null;
		String[] updateExpediteCharge = Stock.getTestQuery("updateExpeditedAmount");
		String[] getExpediteCharge = Stock.getTestQuery("getExpeditedAmount");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			
			getExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			sBeforeExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("Before: ExpediteChanrge="+sBeforeExpediteCharge);
			updateExpediteCharge[0] = Common.getParticipantDBName(Stock
					.GetParameterValue("userName"))
					+ "DB_"
					+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

			pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],"300000",				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge = DB.executeQuery(getExpediteCharge[0], getExpediteCharge[1],				
					Stock.GetParameterValue("planId"));
			getExpediteChanrge.first();
			String sExpediteCharge = getExpediteChanrge.getString("amount");
			System.out.println("After: ExpediteChanrge="+sExpediteCharge);
			if (pptExpediteChanrge > 0) {
				Reporter.logEvent(
						Status.PASS,
						"Verify ExpediteCharge is greater than RMD amount",
						"ExpediteCharge is set to 3,00,000", false);

				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
				myAccountPage.get();
				if (Web.isWebElementDisplayed(myAccountPage, "PLAN NAME", true)) {
					myAccountPage.clickPlanNameByGAID(Stock
							.GetParameterValue("planId"));
				}
				LeftNavigationBar lftNavBar = new LeftNavigationBar(
						myAccountPage);
				RequestWithdrawal_RMD requestWithdrawal_rmd = new RequestWithdrawal_RMD(
						lftNavBar);
				requestWithdrawal_rmd.get();
				requestWithdrawal_rmd.selectRMDBy(Stock.GetParameterValue("selectRMDBy"),Stock.GetParameterValue("selectDeliveryMethod"),
						Stock.GetParameterValue("selectGraceDeliveryMethod"));
				requestWithdrawal_rmd.citizenShipValidation(Stock
						.GetParameterValue("SSN"));
				Common.waitForProgressBar();
				requestWithdrawal_rmd.isTextFieldDisplayed("Unable to continue.");
				requestWithdrawal_rmd.isTextFieldDisplayed("An error has occurred. "
						+ "Please contact a Participant Services Representative if the problem persists.");
				
				
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify pin auth is updated",
						"Pin Auth is not set to"
								+ Stock.GetParameterValue("pinAuth"), false);
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
				updateExpediteCharge[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				pptExpediteChanrge = DB.executeUpdate(updateExpediteCharge[0], updateExpediteCharge[1],sBeforeExpediteCharge,				
						Stock.GetParameterValue("planId"));
				if (pptExpediteChanrge > 0) {
					Reporter.logEvent(
							Status.PASS,
							"Verify ExpediteCharge is greater than RMD amount",
							"ExpediteCharge is set to "+sBeforeExpediteCharge, false);
				}
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Withdrawal_RMD_DDTC_29674_PPTWEB_RMD_Exception_Hard_Stop_scenarios_for_NextGen_PartWeb_Withdrawals(
			int itr, Map<String, String> testdata) {
		String[] updateDefaultDate = Stock.getTestQuery("updateDefaultDate");
		String[] updateFileRestriction = Stock.getTestQuery("updateFileRestriction");
		String[] updateOwnership = Stock.getTestQuery("updateOwnershipPartAgrmt");
		String[] updateDeathDate = Stock.getTestQuery("updateDeathDate");
		String[] updateActivationCode = Stock.getTestQuery("updateActivationCode");
		try {
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);

			if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("birth_date_default"))
			{
				updateDefaultDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateDefaultDate[0], updateDefaultDate[1],"Y",			
						Stock.GetParameterValue("ind_ID"));
			}
			else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("file_restriction"))
			{
				updateFileRestriction[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateFileRestriction[0], updateFileRestriction[1],"FILE",			
						Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
			}
			else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("death_Ownership"))
			{
				updateOwnership[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateOwnership[0], updateOwnership[1],"T",			
						Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
			}
			else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_qdro"))
			{
				updateOwnership[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateOwnership[0], updateOwnership[1],"Q",			
						Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
			}
			else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_deceased"))
			{
				updateDeathDate[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateDeathDate[0], updateDeathDate[1],"10-DEC-10",			
						Stock.GetParameterValue("ind_ID"));
			}
			else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_login"))
			{
				updateActivationCode[0] = Common.getParticipantDBName(Stock
						.GetParameterValue("userName"))
						+ "DB_"
						+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

				DB.executeUpdate(updateActivationCode[0], updateActivationCode[1],"T","09-OCT-10",		
						Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
				Thread.sleep(2000);
				login = new LoginPage();

				login.get();
				login.submitLoginCredentials(
						lib.Stock.GetParameterValue("userName"),
						lib.Stock.GetParameterValue("password"));

				String errMsg = "";
				errMsg = login.isValidCredentials();

				Web.VerifyText(lib.Stock.GetParameterValue("ExpectedErrorMessage"),
						errMsg, true);
			}
			if(!Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_login"))
			{
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
				Common.waitForProgressBar();
				requestWithdrawal
						.isTextFieldDisplayed("Your request cannot be completed on-line. "
								+ "Please call a Participant Services Representative at");
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
				if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("birth_date_default"))
				{
					updateDefaultDate[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateDefaultDate[0], updateDefaultDate[1],"N",			
							Stock.GetParameterValue("ind_ID"));
				}
				else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("file_restriction"))
				{
					updateFileRestriction[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateFileRestriction[0], updateFileRestriction[1],null,			
							Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
				}
				else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("death_Ownership"))
				{
					updateOwnership[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateOwnership[0], updateOwnership[1],"N",			
							Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
				}
				else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_qdro"))
				{
					updateOwnership[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateOwnership[0], updateOwnership[1],"N",			
							Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
				}
				else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_deceased"))
				{
					updateDeathDate[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateDeathDate[0], updateDeathDate[1],null,			
							Stock.GetParameterValue("ind_ID"));
				}
				else if(Stock.GetParameterValue("dbValidation").equalsIgnoreCase("ppt_login"))
				{
					updateActivationCode[0] = Common.getParticipantDBName(Stock
							.GetParameterValue("userName"))
							+ "DB_"
							+ Common.checkEnv(Stock.getConfigParam("TEST_ENV"));

					DB.executeUpdate(updateActivationCode[0], updateActivationCode[1],"A","09-OCT-17",		
							Stock.GetParameterValue("ind_ID"),Stock.GetParameterValue("planId"));
				}
				RequestWithdrawal requestWithdrawal = new RequestWithdrawal();
				Web.clickOnElement(requestWithdrawal, "LOGOUT");
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}


