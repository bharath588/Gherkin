package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.AccountOverview;
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

public class BeneficiariesTestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	Date date = new Date();
	DateFormat time = new SimpleDateFormat("M/d/yyyy");
	String newDate = time.format(date);
	static String printTestData = "";
	public static String participant_SSN = null;
	public static String first_Name = null;
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
	public void AOD_Beneficiaries_DDTC_30779_TC_01_verify_If_WNBEN_is_ON_WUBENE_is_OFF_there_is_at_least_one_beneficiary_card_the_link_should_say_View_details(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountpre = new AccountOverview();
			myAccountpre.updateBeneficiary(Stock.GetParameterValue("planId"),null);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);		
			AccountOverview myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			myAccount.verifyBenficiaryCard(true);
			myAccount.verifyBenficiaryCardViewDetailsLink(true);
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
				/*AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "LOGOUT");*/
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_30781_TC_03_verify_If_WNBEN_and_WUBENE_is_ON_and_no_bene_on_card_AOD_page_should_display_beneficiaries_card_with_the_text_None_on_file_and_with_the_link_Add_now(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccount = new AccountOverview();
			
			try {
				myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar lftNavBar = new LeftNavigationBar(homePage);		
			myAccount = new AccountOverview(lftNavBar);
			myAccount.get();
			myAccount.verifyBenficiaryCard(true);
			myAccount.verifyBenficiaryCardAddnowLink(true);
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
				/*AccountOverview myAccount = new AccountOverview();
				Web.clickOnElement(myAccount, "LOGOUT");*/
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_30782_TC_04_To_verify_If_WNBEN_is_ON_WUBENE_is_ON_and_greater_than_1_bene_on_card_AOD_page_should_display_beneficiaries_card_with_the_AddEdit_link(
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
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			Web.clickOnElement(myAccount, "Add now Bene");
			Thread.sleep(1000);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(2000);
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			Thread.sleep(5000);
			Web.clickOnElement(myAccount, "Account Overview Link");
			myAccount.verifyBenficiaryCard(true);
			myAccount.verifyBenficiaryCardAddEditLink(true);
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
				
				try {
					myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				Web.clickOnElement(myAccount, "LOGOUT");
				Thread.sleep(3000);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_28317_QA01_PW_21965_Verify_New_Beneficiaries_Card_in_Account_overview_page_if_benes_are_on_file(
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
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			Web.clickOnElement(myAccount, "Add now Bene");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(2000);
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", true);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			Thread.sleep(5000);
			Web.clickOnElement(myAccount, "Account Overview Link");
			myAccount.verifyBenficiaryCard(true);
			myAccount.getBeneficiaryInfo(Stock.GetParameterValue("noOfBene"));
			myAccount.verifyBenficiaryCardAddEditLink(true);
			
			myAccount.validateDate(newDate);
			myAccount.validateAddEditNavigation();
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
				//Web.clickOnElement(myAccount, "LOGOUT");
				try {
					myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
		
	@Test(dataProvider = "setData")
	public void Verifying_Beneficiary_AddnowLink(int itr, Map<String, String> testdata){
		
		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccount = new AccountOverview();
			
			try {
				myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			myAccount = new AccountOverview(leftNav);
			myAccount.get();
			myAccount.verifyBenficiaryCard(true);
			myAccount.verifyBenficiaryCardAddnowLink(true);
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
	public void AOD_Beneficiaries_DDTC_28318_QA02_PW_21965_Verify_New_Beneficiaries_Card_Account_overview_page_if_benes_are_not_on_file(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_AddnowLink(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_28325_QA03_PW_21965_Verify_Beneficiaries_Card_Account_overview_page_if_a_participant_has_Zero_Beneficiaries_and_editable(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_AddnowLink(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Verifying_Beneficiary_Uneditable(
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
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			myAccount.verifyBenficiaryCard(false);
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
	public void AOD_Beneficiaries_DDTC_28326_QA04_PW_21965_New_Beneficiaries_Card_Account_overview_page_if_a_participant_has_Zero_Beneficiaries_un_editable(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_Uneditable(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_30780_TC_02_To_verify_If_WNBEN_is_ON_WUBENE_is_OFF_and_there_is_NO_beneficiary_on_file_Bene_card_should_not_display(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_Uneditable(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void Verifying_Beneficiary_Link(
			int itr, Map<String, String> testdata) {

		try {

			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			AccountOverview myAccountpre = new AccountOverview();
			myAccountpre.updateBeneficiary(Stock.GetParameterValue("planId"),"24-OCT-17");
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			myAccount.verifyBenficiaryCard(false);
			
			if(!Web.isWebElementDisplayed(myAccount, "Beneficiaries Link"))
				Reporter.logEvent(Status.PASS, "Verify Beneficiaries Link is not displayed in Left Navigation",
						"Beneficiaries Link is not displayed in Left Navigation", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Beneficiaries Link is not displayed in Left Navigation",
						"Beneficiaries Link is displayed in Left Navigation", true);
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
				/*if(itr==2)
				{
					AccountOverview myAccount = new AccountOverview();
					
					Web.clickOnElement(myAccount, "LOGOUT");
				}
				*/
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_30783_TC_05_To_verify_If_WNBEN_is_OFF_Bene_card_should_not_show(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_Link(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_28387_QA07_PW_21965_Verify_New_Beneficiaries_Card_Account_overview_page_beneficiary_turned_off_at_the_plan_level(
			int itr, Map<String, String> testdata) throws Exception {
		Verifying_Beneficiary_Link(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_28328_QA05_PW_21965_New_Beneficiaries_Card_Account_overview_page_if_a_participant_has_Multiple_BeneficiariesLessthanFive(
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
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			if(itr==1)
			{
				Web.getDriver().navigate().refresh();
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(5000);
				Web.clickOnElement(myAccount, "Add now Bene");
			}
				
			else
				Web.clickOnElement(myAccount, "AddEdit Link");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(2000);
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			/*if(myAccount.isTextFieldDisplayed("Unable to continue."))
			{
				myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
				Web.clickOnElement(myAccount, "LOGOUT");
			}*/
			Thread.sleep(5000);
			Web.clickOnElement(myAccount, "Account Overview Link");
			myAccount.verifyBenficiaryCard(true);
			if(itr==2)
				myAccount.getBeneficiaryInfo(Stock.GetParameterValue("noOfBene"));
			
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
				if(itr==2)
				{
					try {
						myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
						
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void AOD_Beneficiaries_DDTC_28329_QA06_PW_21965Beneficiaries_Card_Account_overview_page_if_a_participant_has_Multiple_BeneficiariesgreaterthanFive(
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
			LeftNavigationBar leftNav = new LeftNavigationBar(homePage);
			AccountOverview myAccount = new AccountOverview(leftNav);
			myAccount.get();
			if(itr==1)
				Web.clickOnElement(myAccount, "Add now Bene");
			else
				Web.clickOnElement(myAccount, "AddEdit Link");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(2000);
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			if(itr==1)
			{
				for(int i=0;i<3;i++)
					beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);
			}
			else if(itr==2)
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);
			else
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"),true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			Thread.sleep(5000);
			Web.clickOnElement(myAccount, "Account Overview Link");
			myAccount.verifyBenficiaryCard(true);
			if(itr==3)
				myAccount.getBeneficiaryInfo(Stock.GetParameterValue("noOfBene"));
			
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
				if(itr==3)
				{
					AccountOverview myAccount = new AccountOverview();
					try {
						myAccount.deleteAODBeneficiariesFromDB(Stock.GetParameterValue("ind_ID"));
						
					} catch (Exception e) {

						e.printStackTrace();
					}
					//Web.clickOnElement(myAccount, "LOGOUT");
				}
				
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
