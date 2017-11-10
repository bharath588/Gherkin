package app.pptweb.testcases.enrollment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.general.AccountOverview;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.RetirementIncome;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class EnrollmentTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	
	LoginPage login;
	String tcName;
	static String printTestData="";
	static String userName=null;
	static String confirmationNumber="";

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
		printTestData="";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
		}
	 return printTestData;
	}
	
	@Test(dataProvider = "setData")
	public void DDTC_26997_NQ_SE_Via_AccountOverview_VerifyDeferral_Election_Page_As_Returning_User(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccount = new MyAccountsPage(homePage);
			myAccount.get();
			myAccount.clickPlanNameByGAID(Stock.getConfigParam("ga_id"));
			//Step 5
			AccountOverview accountOverview = new AccountOverview();
			
			accountOverview.isTextFieldDisplayed("Enrollment is now open.");
			
			if(Web.isWebElementDisplayed(accountOverview, "LINK ENROLL NOW")){
			Reporter.logEvent(Status.PASS,
					"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
					"'Enroll Now' Link is Displayed in Account Overview Page", true);
			}
			else 
			{
			Reporter.logEvent(Status.FAIL,
					"Verify 'Enroll Now' Link is Displayed in Account Overview Page",
					"'Enroll Now' Link is Not Displayed in Account Overview Page", true);
			}
			//Step 6
			accountOverview.clickOnEnrollNow();
			//Step 7
			Web.waitForElement(accountOverview, "Button Continue");
			Web.clickOnElement(accountOverview, "Button Continue");
			Web.waitForElement(accountOverview, "Button Continue To Enrollment");
			Web.clickOnElement(accountOverview, "Button Continue To Enrollment");
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	
}
}

