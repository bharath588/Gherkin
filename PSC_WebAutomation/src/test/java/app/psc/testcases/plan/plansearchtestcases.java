package app.psc.testcases.plan;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import plan.PlanPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import framework.util.CommonLib;

public class plansearchtestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	HomePage homePage;
	PlanPage planPage;
	ResultSet queryResultSet;

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

	/**
	 * This test case aims to verify that user is redirected to plan summary
	 * page when plan is entered in the Search plans text on home page.
	 * 
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC01_search_plan_for_valid_plan(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"Verify the user is redirected to respective plan page when the plan number is entered");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase verifies that user is redirected to respective plan page when plan number is entered",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			Thread.sleep(3000);
			if (homePage.searchPlan()) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is redirected to entered plan summary page",
						"User is redirected to entered plan summary page",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is redirected to entered plan summary page",
						"User is not redirected to entered plan summary page",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion error occured during checking of plan summary page",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case aims to verify that user is prompted with an error message
	 * when user hits search for plan with plan field blank.
	 * 
	 * @param itr
	 * @param testData
	 */
	@Test(dataProvider = "setData")
	public void TC02_Validate_error_message_when_plan_field_is_left_blank(
			int itr, Map<String, String> testData) {
		try {

			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"Verify the user is prompted with error message when the plan field is left blank.");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase verifies that user is prompted with error message when the plan field is left blank.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			if (homePage.searchPlan() && homePage.verifyErrorText()) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is promted with error message if plan number is empty",
						"User is promted with error message if plan number is empty",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is promted with error message if plan number is empty",
						"User is not promted with error message if plan number is empty",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC03_Verify_planDetails_Populated(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"Verify that plan number and name are populated when user enters few text of the related plan numbers");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase verifies that plan number and name are populated when user enters few text of the related plan numbers",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			homePage.enterPartialPlanNumber();
			if (homePage.verifyAutocompletePlanSuggestion()) {
				Reporter.logEvent(
						Status.PASS,
						"Verify that plan number and name are populated when user enters few text of the related plan numbers",
						"Plan number and name are populated when user enters few text of the related plan numbers",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify that plan number and name are populated when user enters few text of the related plan numbers",
						"Plan number and name are not populated when user enters few text of the related plan numbers",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"A run time exception occured and and plan details are not populated when user type in few characters of a plan.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(
					Status.FAIL,
					"Assertion error occured and plan details are not populated when user type in few characters of a plan",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This test case aims to verify that user is prompted with an error message
	 * when invalid plan is searched.
	 * 
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC03_Validate_Error_Message_When_Invalid_Plan_Number_Entered(
			int itr, Map<String, String> testDat) {
		try {

			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"Verify the user is prompted with error message when invalid plan number is searched.");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase verifies that user is prompted with error message when invalid plan number is searched.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			if (!homePage.searchPlan() && homePage.verifyErrorText()) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is promted with error message if plan number is invalid.",
						"User is promted with error message if plan number is invalid.",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is promted with error message if plan number is invalid.",
						"User is not promted with error message if plan number is invalid.",
						true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case aims to verify that user is navigated to plan page when
	 * plan name is entered and searched on Home page.
	 * 
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC04_Validate_Plan_Page_When_PlanName_Entered(int itr,
			Map<String, String> testDat) {
		try {

			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"Verify the user is navigated to respective plan page when plan Name is entered in plan search box.");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase verifies that user is navigated to respective plan page when plan Name is entered in plan search box.",
					false);
			homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			if (homePage.searchPlanWithName()) {
				Reporter.logEvent(
						Status.PASS,
						"Verify user is Navigated to respective plan page if plan name is entered and searched.",
						"User is Navigated to repspective plan page.", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify user is Navigated to respective plan page if plan name is entered and searched.",
						"User is not Navigated to repspective plan page.", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case validates the plan messaging confirmation page.
	 * 
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC05_SIT_PSC_Plan_messaging_confirmation_page(int itr,
			Map<String, String> testDat) {
		try {

			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates the plan messaging confirmation page.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			planPage.navigateToPlanMessagingPage();
			Thread.sleep(1500);
			planPage.addNewPlanMessage();

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the Investment and Performance Tabs columns and Print functionality.
	 * in terms of clicking and check if a print preview page opens up.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC06_SIT_PSC_Investment_Fixed_Investment_Rates_Page(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates the Investment and Performance Tabs columns and Print functionality."
							+ " in terms of clicking and check if a print preview page opens up",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			HomePage homePage = new HomePage();
			if (Stock.getConfigParam("DataType").equals("NonApple")) {
				homePage.searchPlanWithIdOrName(Stock
						.GetParameterValue("planNumber"));
			}
			planPage.navigateToInvestmentAndPerformance();
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateColumnFixedInvstmntRatePage();
			planPage.validateUnitShareFromToField();
			planPage.validatePrintFunctionality();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the Asset allocation columns and Print functionality.
	 * in terms of clicking and check if a print preview page opens up.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC07_SIT_PSC_Investment_Asset_Allocations_page(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates the Asset allocation columns and Print functionality."
							+ " in terms of clicking and check if a print preview page opens up.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			HomePage homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumber"));
			planPage.navigateToInvestmentAndPerformance();
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateAssetAllocPage();
			planPage.validatePrintFunctionality();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the edit functionality after creating new user.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC08_Disable_certain_user_edit_fields_for_SAW(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates the edit functionality after creating new user.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			planPage.openAnySubmenuUnderAdministrationMenu("Username security management");
			HomePage homePage = new HomePage();
			/*
			 * if(homePage.navigateToProvidedPage("Plan","Administration",
			 * "Username security management")) Reporter.logEvent(Status.PASS,
			 * "Navigate to Plan-->Administration--->Username security management page."
			 * ,"" +
			 * "User is navigated to Username security management page.",false);
			 * else Reporter.logEvent(Status.FAIL,
			 * "Navigate to Plan-->Administration--->Username security management page."
			 * ,"" +
			 * "User is not navigated to Username security management page."
			 * ,true);
			 */
			planPage.addNewUser();
			planPage.validateEditabilityOfUserFields();
			planPage.validateEditabilityOnResetPasswordScreen();
			planPage.validateEditabilityOnResetSecurityQues();
			planPage.validateEditabilityOnTerminateUserScreen();
			planPage.validateEditabilityOnReactivateUserScreen();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates Participant QDIA notice Listing order functionality.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 * @Date 13-June-2017
	 */
	@Test(dataProvider = "setData")
	public void TC09_Order_Participant_QDIA_Notice_Listing(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates Participant QDIA notice Listing order functionality.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			queryResultSet = DB.executeQuery(
					Stock.getTestQuery("checkQDIARecords")[0],
					Stock.getTestQuery("checkQDIARecords")[1],
					Stock.GetParameterValue("planNumber"));
			if (DB.getRecordSetCount(queryResultSet) > 0) {
				queryResultSet = DB.executeQuery(
						Stock.getTestQuery("getPSCMMRandQNBO1TxnCodes")[0],
						Stock.getTestQuery("getPSCMMRandQNBO1TxnCodes")[1],
						"K_" + Stock.GetParameterValue("username"));
				if (DB.getRecordSetCount(queryResultSet) == 2) {
					homePage = new HomePage();
					homePage.searchPlanWithIdOrName(Stock
							.GetParameterValue("planNumber"));
					/*
					 * if(!homePage.navigateToProvidedPage("Plan",
					 * "Fiduciary records"
					 * ,"Participant QDIA notice listing order"))
					 * Reporter.logEvent(Status.PASS,
					 * "Navigate to Plan-->Fiduciary records--->Participant QDIA notice listing order page."
					 * ,"" +
					 * "User is navigated to Participant QDIA notice listing order page."
					 * ,false);
					 */
					new PlanPage()
							.openAnySubmenuUnderFiduciary("Participant QDIA notice listing order");
					Thread.sleep(3500);
					planPage.fill_QDIA_Notice_Mailing_Form();
					if (Stock.GetParameterValue("ignoreDivision").equals("yes")) {
						queryResultSet = DB.executeQuery(
								Stock.getTestQuery("checkQDIARecords")[0],
								Stock.getTestQuery("checkQDIARecords")[1],
								Stock.GetParameterValue("planNumber1"));
						if (DB.getRecordSetCount(queryResultSet) == 0) {
							homePage.searchPlanWithIdOrName(Stock
									.GetParameterValue("planNumber1"));
							if (planPage.QDIAisDisplayed())
								Reporter.logEvent(
										Status.PASS,
										"Validate 'Participant QDIA notice listing order' option is not displayed if GW_Service "
												+ "records are not set up.",
										"menu option is not displayed.", false);
							else
								Reporter.logEvent(
										Status.FAIL,
										"Validate 'Participant QDIA notice listing order' option is not displayed if GW_Service "
												+ "records are not set up.",
										"menu option is not displayed.", true);
						} else {
							Reporter.logEvent(
									Status.FAIL,
									"Check query "
											+ Stock.getTestQuery("checkQDIARecords")[1]
											+ " does not fetch"
											+ " any record.(GW_SERVICE are not set up)",
									"GW_SERVIC is set up and it should not be set up to validate"
											+ "'Participant QDIA notice listing order' menu option",
									false);
						}
					}
				} else {
					Reporter.logEvent(
							Status.WARNING,
							"Exceute query:"
									+ Stock.getTestQuery("getPSCMMRandQNBO1TxnCodes")[1],
							""
									+ "User is not assigned with PSCMMR and QNBO1 txn codes.",
							false);
				}
			} else {
				Reporter.logEvent(Status.WARNING,
						"Query " + Stock.getTestQuery("checkQDIARecords")[1]
								+ " does not fetch any record.",
						"Please set up test data." + " and execute test case.",
						false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates plan documents page.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 * @Date 13-June-2017
	 */
	@Test(dataProvider = "setData")
	public void TC10_SIT_PSC_Fiduciary_Records_Plan_Documents_No_Part_grouping(
			int itr, Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates plan documents page.", false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			planPage.openAnySubmenuUnderFiduciary("Plan documents");
			// homePage.searchPlanWithIdOrName(Stock.GetParameterValue("planNumber"));
			/*if (homePage.navigateToProvidedPage("Plan", "Fiduciary records",
					"Plan documents"))
				Reporter.logEvent(
						Status.PASS,
						"Navigate to Plan-->Fiduciary records--->Plan documents page.",
						"Plan documents page is displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Navigate to Plan-->Fiduciary records--->Plan documents page.",
						"Plan documents page is not displayed.", true);*/
			planPage.validatePlanDocumentsPage();
			planPage.validatePlanDocumentsLinks();
			//planPage.validateNoPlanDocMsg();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates Fiduciary records-->Required notices page elements.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 * @Date 15-June-2017
	 */
	@Test(dataProvider = "setData")
	public void TC11_SIT_PSC_HomePage_Fiduciary_Records_Required_Notices_Part_grouping(
			int itr, Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates Fiduciary records-->Required notices page elements.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumberWithPartGrp"));
			/*if (CommonLib.navigateToProvidedPage("Plan", "Fiduciary records",
					"Required notices"))
				Reporter.logEvent(
						Status.PASS,
						"Navigate to Plan-->Fiduciary records-->Required notices.",
						"" + "Required notices page is displayed.", false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Navigate to Plan-->Fiduciary records-->Required notices.",
						"" + "Required notices page is displayed.", true);*/
			planPage.openAnySubmenuUnderFiduciary("Required notices");
			planPage.validateFiduciaryRequiredNotices();
			planPage.validateFiduciaryRequiredNotices_1();
			planPage.validateSafeHarBorNoticeForPartGrpng();
			planPage.validateSafeHarBorNoticeForPartGrpng_1();
			planPage.validateSafeHarBorNoticeForPartGrpng_2();
			planPage.validateSafeHarBorNoticeForPartGrpng_3();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates 3rd level submenu options of Plan module.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 * @Date 15-June-2017
	 */
	@Test(dataProvider = "setData")
	public void TC12_3rd_Level_Links(int itr, Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates Fiduciary records-->Required notices page elements.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			planPage.validateThirdLevelMenuOptionForPlanOverview();
			homePage = new HomePage();
			homePage.navigateToHomePage();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the future rate,future effective date columns for Fixed investment rates.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC15_SIT_PSC_Investment_Fixed_Investment_FutureRates(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This test case validates the future rate,future "
							+ "effective date columns for Fixed investment rates.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			HomePage homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumber"));
			planPage.navigateToInvestmentAndPerformance();
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateColumnFixedInvstmntRatePage();
			homePage.logoutPSC();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the Investment option page elements like columns
	 * displayed,Excel and PDf links,sorting by balance,Disclaimer,Morning start popup info,Quick links.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC14_SIT_PSC_Investment_Options(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This test case validates the Investment option page elements like columns "
							+ "displayed,Excel and PDf links,sorting by balance,Disclaimer,Morning start popup info,Quick links",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumber"));
			planPage.navigateToInvestmentAndPerformance();
			planPage.validateInvestmentAndPerformanceColumns();
			planPage.validateInvestmentOptionsColumns();
			planPage.validateQuickLinksOnInvOptPage();
			planPage.validateDesclaimerForInvetmentOptionPage();
			planPage.sortBalanceInDescAndAsc();
			planPage.validateExcelDownload();
			planPage.validatePDFURL();
			planPage.validatePrintFunctionality();
			homePage.navigateToHomePage();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * This test case validates the update functionality of Bank details.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param itr
	 * @param testDat
	 */
	@Test(dataProvider = "setData")
	public void TC13_PLN_ADM_View_Change_Banking_Information_Regr(int itr,
			Map<String, String> testDat) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This testcase validates the update functionality of Bank details.",
					false);
			planPage = new PlanPage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
			homePage = new HomePage();
			homePage.searchPlanWithIdOrName(Stock
					.GetParameterValue("planNumber"));
			/*CommonLib.navigateToProvidedPage("Plan", "Administration",
					"Viewâˆ•change banking information");*/
			planPage.openAnySubmenuUnderAdministrationMenu("View∕change banking information");
			planPage.validateBankingInfoAndUpdateLink();
			planPage.updateBankDetails();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion error occured during checking of error message",
					errorMsg, true);
		}

		finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().close();
		Web.getDriver().quit();
		Web.removeWebDriverInstance();
	}

}
