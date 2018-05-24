package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.balance.Balance;
import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.ProfilePage;
import pageobjects.loan.LoansSummaryPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.statementsanddocuments.StatementsAndDocuments;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class LoansBalanceTestcases {
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
	public void AOD_Loans_DDTC_28724_SIT_PPTWEB_Account_overview_Loan_Balance_card_multiple_loans(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),false);
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateLoansBalanceCard();
			myAccountOverview.verifyMaturityDates();
			myAccountOverview.clickOnLoanViewDetailsLink(true);
			
			LoansSummaryPage myLoan=new LoansSummaryPage();
			myLoan.verifyParticipanttakenToLoanSummaryPage();
			myLoan.verifyLoanSummaryPage();
			
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
	public void AOD_Loans_DDTC_28723_SIT_PPTWEB_Account_overview_Loan_Balance_card_single_loan(
			int itr, Map<String, String> testdata) {

		try {
					
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),false);
			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
 			LandingPage homePage = new LandingPage(mfaPage);
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateLoansBalanceCard();
			myAccountOverview.verifyMaturityDates();
			myAccountOverview.clickOnLoanViewDetailsLink(true);
			
			LoansSummaryPage myLoan=new LoansSummaryPage();
			myLoan.verifyParticipanttakenToLoanSummaryPage();
			myLoan.verifyLoanSummaryPage();
			
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
