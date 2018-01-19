package app.pptweb.testcases.investments;

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
import pageobjects.general.LeftNavigationBar;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.RetirementIncome;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
@SuppressWarnings("unused")
public class Investmentstestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public Map<String, String> mapInvestmentOptionsReviewPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsConfirmPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptions = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsRebalance = new HashMap<String, String>();
	public List<String> ConfirmationNos = new ArrayList<String>();
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
	public void DDTC_2672_DIM_Choose_individual_funds_or_DIM_NoMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 6
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 7
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			investment.verifyWebElementDisplayed("Link Add/View All Funds");
			investment.verifyWebElementDisplayed("Reset All Changes Link");
			investment.verifyWebElementDisplayed("Submit Button Change Future Allocation");
			investment.verifyWebElementDisplayed("Back Link");
			
			//precondition for step 10
			
			//investment.removeAllIvestments();
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			//Step 10,11,12
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
				investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Sep 13
			
			 mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			*/
			//Step 14
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 15
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//step 16
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
		    
		    //Step 18
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_2844_DIM_Choose_individual_funds(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
			//Step 6
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 7
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Link Add/View All Funds");
			investment.verifyWebElementDisplayed("Reset All Changes Link");
			investment.verifyWebElementDisplayed("Submit Button Change Future Allocation");
			investment.verifyWebElementDisplayed("Back Link");
			
			
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			//Step 10,11,12
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 13
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			/*	if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 14
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 15
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//step 16
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
		    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is displayed on DIM Choose Individual Funds button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is not displayed on DIM Choose Individual Funds button", true);
			}
		    //Step 18
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_2317_MTG_Viewmanage_invest_DIM_Doesnot_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
			//Step 6
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 7
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Link Add/View All Funds");
			investment.verifyWebElementDisplayed("Reset All Changes Link");
			investment.verifyWebElementDisplayed("Submit Button Change Future Allocation");
			investment.verifyWebElementDisplayed("Back Link");
			
			
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			//Step 10,11,12
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 13
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 14
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 15
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//step 16
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			leftmenu.clickNavigationLink("View/Manage my investments");
		    //Step 18
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
	@Test(dataProvider = "setData")
	public void DDTC_2714_HMDI_target_based_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 6
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
			//Step 7
			investment.clickChangeMyInvestmentButton();
			
			//Step 8
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 9
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
		
			//Step 10
			String targetYearFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(targetYearFund);
			
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			//Step 11
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			
			//Step 12
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 13
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			*/
			
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
			//Step 14
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
			leftmenu.clickNavigationLink("View/Manage my investments");
		  
		    //Step 15
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_2640_MTG_Viewmanage_invest_HDMI_Doesnot_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 6
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
			//Step 7
			investment.clickChangeMyInvestmentButton();
			
			//Step 8
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			if (investment.verifyMoneyTypeGroupIsDisplayed("MTG")) {
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is displayed",
						" Money Type Grouping is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify  Money Type Grouping is displayed",
						" Money Type Grouping is Not displayed ", false);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 9
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
			//Step 10
			String targetYearFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(targetYearFund);
			
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			//Step 11
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			
			//Step 12
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 13
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			*/
			
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
			//Step 14
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
			leftmenu.clickNavigationLink("View/Manage my investments");
		    //Step 15
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_5410_DoItMyself_Allocation_based_on_slider(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			investment.clickChangeMyInvestmentButton();
			
			//Step 6
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Link Add/View All Funds");
			investment.verifyWebElementDisplayed("Reset All Changes Link");
			investment.verifyWebElementDisplayed("Submit Button Change Future Allocation");
			investment.verifyWebElementDisplayed("Back Link");
			
			//Step 8
			investment.removeAllIvestments();
			Web.clickOnElement(investment, "Link Add/View All Funds");
			mapInvestmentOptionsReviewPage=investment.selectInvestmentOptions(2);
			investment.moveSlider(mapInvestmentOptionsReviewPage.get("investmentFundName0"),100);
			if (investment.verifySubmitButtonisEnabled()) {
				Reporter.logEvent(Status.PASS,
						"Verify Submit button is enabled when total pecentage is 100",
						"Submit button is enabled when total pecentage is 100", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Submit button is enabled when total pecentage is 100",
						"Submit button is not enabled when total pecentage is 100", true);
			}
		
			investment.moveSlider(mapInvestmentOptionsReviewPage.get("investmentFundName0"),80);
			investment.moveSlider(mapInvestmentOptionsReviewPage.get("investmentFundName1"),46);
			
			if (investment.verifySubmitButtonisEnabled()) {
				Reporter.logEvent(Status.PASS,
						"Verify Submit button is enabled when total pecentage is 100",
						"Submit button is enabled when total pecentage is 100", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Submit button is enabled when total pecentage is 100",
						"Submit button is not enabled when total pecentage is 100", true);
			}
		
			investment.moveSlider(mapInvestmentOptionsReviewPage.get("investmentFundName0"),20);
			investment.moveSlider(mapInvestmentOptionsReviewPage.get("investmentFundName1"),30);
			
			if (!investment.verifySubmitButtonisEnabled()) {
				Reporter.logEvent(Status.PASS,
						"Verify Submit button is not enabled when total pecentage is less than 100",
						"Submit button is not enabled when total pecentage is less than 100", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Submit button is not enabled when total pecentage is 100 less than 100",
						"Submit button is enabled when total pecentage is less than 100", true);
			}
			
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_24360_Handling_Employer_directed_MTGs_Allocations_DIM(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//Step 5
			String sqlQuery[]=Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_id"),Stock.GetParameterValue("moneyTypeGrouping"));
			
			//Step 1 to 6
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				Web.clickOnElement(investment, "Expand Sources");
			}
			
			if(!investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is Filtered out and not Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Filtered out and is Displayed on My Investments Page", true);
			}
			
			//Step 7
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping1"));
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Rebalance Current Balance");
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping1"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping1")+" is Displayed on Change Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping1")+" is not Displayed on Change Investments Page", true);
			}
			
			//Step 8
			if(Web.isWebElementDisplayed(investment, "Change Future Contribution", true)){
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			}
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 9
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			
			//Step 10
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 11
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
				    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
		    //Step 12
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			Web.clickOnElement(homePage, "LOG OUT");
			Web.waitForElement(login, "SIGN IN");
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.removeEmployerDirectedRule(Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_24360_Employer_directed_MTGs_Allocations_DIM_When_Employer_Directed_Indic_Setto_N(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//Step 29
			String sqlQuery[]=Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_id"),Stock.GetParameterValue("moneyTypeGrouping"));
			
			//Step 30
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				Web.clickOnElement(investment, "Expand Sources");
			}
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is included and Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Displayed on My Investments Page", true);
			}
			
			//Step 31
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Rebalance Current Balance");
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is Displayed on Change Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Displayed on Change Investments Page", true);
			}
			
			//Step 32
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 33
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			
			//Step 34
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 35
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
				    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
		    //Step 36
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.removeEmployerDirectedRule(Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	//label[contains(text(),'Non-Roth')]
	@Test(dataProvider = "setData")
	public void DDTC_2534_MTG_Guidance_HMDI_Risk_based_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			//GuidancePage guidance = new GuidancePage(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
			
			
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
			
			//Step 9
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(selectedRiskBasedFund);
		
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			//Step 10
			
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			*/
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
		
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Risk Based Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag is displayed for HMDI on Risk Based Funds button",
						"'Current' Flag is displayed for HMDI on Risk Based Funds button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag is displayed for HMDI on Risk Based Funds button",
						"'Current' Flag is not displayed for HMDI on Risk Based Funds button", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	@Test(dataProvider = "setData")
	public void DDTC_2804_MTG_Guidance_HMDI_Risk_based_DoesNot_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			//GuidancePage guidance = new GuidancePage(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify Guidance Selection Page is displayed",
						"Guidance Selection Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Guidance Selection Page is displayed",
						"Guidance Selection Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
			
			String ExpectedAllertMsg="Because your plan has multiple contribution sources, "
					+ "we are unable to display your current allocation.  "
					+ "Changes to your investment allocation will apply to all your contribution sources. "
					+ "To make changes based on contribution source, go to My Accounts.";
	//TODO		
			/*String actualAllertMsg=investment.getWebElementText("Risk Based Fund Allert Message");
			
			if(ExpectedAllertMsg.contentEquals(actualAllertMsg)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Static Message is Displayed in Select Risk Based Fund Page",
						"Static Message is Displayed in Select Risk Based Fund Page\nStatic Message is:"+actualAllertMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Static Message is Displayed in Select Risk Based Fund Page",
						"Static Message is not Displayed/Matching in Select Risk Based Fund Page\nExpected Message:"+ExpectedAllertMsg+"\nActual Message:"+actualAllertMsg, true);
			}*/
			
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
			
			//Step 9
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(selectedRiskBasedFund);
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			//Step 10
			/*
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
		
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Risk Based Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag is displayed for HMDI on Risk Based Funds button",
						"'Current' Flag is displayed for HMDI on Risk Based Funds button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag is displayed for HMDI on Risk Based Funds button",
						"'Current' Flag is not displayed for HMDI on Risk Based Funds button", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	
	
	@Test(dataProvider = "setData")
	public void DDTC_2367_MTG_Guidance_HMDI_Target_based_DoesNot_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify Guidance Selection Page is displayed",
						"Guidance Selection Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Guidance Selection Page is displayed",
						"Guidance Selection Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Choose Target Date Fund");
			
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
	//TODO		
			/*String ExpectedAllertMsg="Because your plan has multiple contribution sources, "
					+ "we are unable to display your current allocation. "
					+ "Changes to your investment allocation will apply to all your contribution sources. "
					+ "To make changes based on contribution source, go to My Accounts.";
			
			String actualAllertMsg=investment.getWebElementText("Risk Based Fund Allert Message");
			
			if(ExpectedAllertMsg.contentEquals(actualAllertMsg)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Static Message is Displayed in Select Target Date Fund Page",
						"Static Message is Displayed in Select Target Date Fund Page\nStatic Message is:"+actualAllertMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Static Message is Displayed in Select Target Date Fund Page",
						"Static Message is not Displayed/Matching in Select Target Date Fund Page\nExpected Message:"+ExpectedAllertMsg+"\nActual Message:"+actualAllertMsg, true);
			}
			*/
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
			
			//Step 9
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
		
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(selectedTargetDateFund);
			
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			//Step 10
			
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_5680_MTG_Guidance_HMDI_Target_based_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify Guidance Selection Page is displayed",
						"Guidance Selection Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Guidance Selection Page is displayed",
						"Guidance Selection Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Choose Target Date Fund");
			
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
			
		
			if(!Web.isWebElementDisplayed(investment, "Risk Based Fund Allert Message")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Static Allert Message is Not Displayed in Select Target Date Fund Page",
						"Static Allert Message is Not Displayed in Select Target Date Fund Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Static Allert Message is Not Displayed in Select Target Date Fund Page",
						"Static Allert Message is Displayed", true);
			}
			
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
	
			//Step 9
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		
			investment.verifyParticipantisAllowedToSelectOnlyOneFund(selectedTargetDateFund);
			if (Web.isWebElementDisplayed(investment,"Button Confirm")) {
				Reporter.logEvent(Status.PASS,
						"Verify Confirm button is displayed",
						"Confirm button is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirm button is displayed",
						"Confirm button is not displayed ", false);
			}
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			//Step 10
			/*
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}*/
			
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_2616_MTG_Guidance_DIM_Doesnot_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify Guidance Selection Page is displayed",
						"Guidance Selection Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Guidance Selection Page is displayed",
						"Guidance Selection Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			investment.verifyWebElementDisplayed("Help Me Do It");
			
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			
			
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
			
			
            Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			//Step 9
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		
			 mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
				/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
					Reporter.logEvent(Status.PASS,
							"Verify 'Investment Option' opened in New Window from Review Changes Page",
							"'Investment Option' opened in New Window ", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify 'Investment Option' opened in New Window from Review Changes Page",
							"'Investment Option' is not opened in New Window ", true);
				}*/
				
				//Step 10
				Web.clickOnElement(investment, "Button Confirm");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(20000);
				investment.verifyPageHeaderIsDisplayed("Header Confirmation");
				
				investment.verifyConfirmationMessageForChangeFutureFlow();
				//Step 11
				/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
					Reporter.logEvent(Status.PASS,
							"Verify 'Investment Option' opened in New Window from Confirmation Page",
							"'Investment Option' opened in New Window ", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify 'Investment Option' opened in New Window from Confirmation Page",
							"'Investment Option' is not opened in New Window ", true);
				}*/
				
				//step 12
				mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
				investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
				String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
				investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is displayed on DIM Choose Individual Funds button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is not displayed on DIM Choose Individual Funds button", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	@Test(dataProvider = "setData")
	public void DDTC_2855_MTG_Guidance_DIM_Offer_all_mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 5
			LeftNavigationBar leftNav=new LeftNavigationBar();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			ManageMyInvestment investment = new ManageMyInvestment(homePage);
			investment.get();
			//Step 6
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify Guidance Selection Page is displayed",
						"Guidance Selection Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Guidance Selection Page is displayed",
						"Guidance Selection Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Choose Individual Funds");
			/*investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			investment.verifyWebElementDisplayed("Do It For Me");*/
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			//Step 8
			
			if (!Web.isWebElementDisplayed(leftNav,"Left Navigation Bar")) {
				Reporter.logEvent(Status.PASS,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is not displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Left Navigation Bar is displayed",
						"Left Navigation Bar is displayed ", true);
			}
			
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			//Step 9
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
		
			 mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
				/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
					Reporter.logEvent(Status.PASS,
							"Verify 'Investment Option' opened in New Window from Review Changes Page",
							"'Investment Option' opened in New Window ", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify 'Investment Option' opened in New Window from Review Changes Page",
							"'Investment Option' is not opened in New Window ", true);
				}
				*/
				//Step 10
				Web.clickOnElement(investment, "Button Confirm");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(20000);
				investment.verifyPageHeaderIsDisplayed("Header Confirmation");
				
				investment.verifyConfirmationMessageForChangeFutureFlow();
				//Step 11
				/*if(investment.VerifyInvestmentOptionOpenInNewWindow()){
					Reporter.logEvent(Status.PASS,
							"Verify 'Investment Option' opened in New Window from Confirmation Page",
							"'Investment Option' opened in New Window ", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify 'Investment Option' opened in New Window from Confirmation Page",
							"'Investment Option' is not opened in New Window ", true);
				}*/
				
				//step 12
				mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
				investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			    
				String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
				investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			
			//Step 13
			
			Web.clickOnElement(homePage, "GUIDANCE");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is displayed on DIM Choose Individual Funds button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM Choose Individual Funds button",
						"'Current' Flag is not displayed on DIM Choose Individual Funds button", true);
			}
		  
		    
		    //Step 14
		    
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
			
			//Step 15
			Web.clickOnElement(homePage, "HOME");
			homePage.dismissPopUps(true, true);
			if(Web.isWebElementDisplayed(homePage, "RETIREMENT INCOME", true) || Web.isWebElementDisplayed(homePage, "TEXT MY ACCOUNTS")){
				Reporter.logEvent(Status.PASS,
						"Verify user is navigated to 'LIAT' Page ",
						"User is navigated to LIAT Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify user is navigated to 'LIAT' Page ",
						"User is not navigated to LIAT Page", true);
			}
			
			
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
	
	@Test(dataProvider = "setData")
	public void DDTC_24362_Handling_Employer_directed_MTGs_Allocations_HMDI(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//Step 5
			String sqlQuery[]=Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_id"),Stock.GetParameterValue("moneyTypeGrouping"));
			
			//Step 1 to 6
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			if(!investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is Filtered out and not Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Filtered out and is Displayed on My Investments Page", true);
			}
			
			//Step 7
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping1"));
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Rebalance Current Balance");
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping1")+" is Displayed on Change Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping1")+" is not Displayed on Change Investments Page", true);
			}
			
			//Step 8
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 9
		
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
			//Step 10
			investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
		
		
			//Step 11
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
		    //Step 12
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			Web.clickOnElement(homePage, "LOG OUT");
			Web.waitForElement(login, "SIGN IN");
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.removeEmployerDirectedRule(Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_24362_Employer_directed_MTGs_Allocations_HMDI_When_Employer_Directed_Indic_Setto_N(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//Step 29
			String sqlQuery[]=Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_id"),Stock.GetParameterValue("moneyTypeGrouping"));
			
			//Step 30
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				Web.clickOnElement(investment, "Expand Sources");
			}
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is included and Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Displayed on My Investments Page", true);
			}
			
			//Step 31
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Rebalance Current Balance");
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is Displayed on Change Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Displayed on Change Investments Page", true);
			}
			
			//Step 32
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 33
		
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//Step 34
			investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		
		
			//Step 35
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyConfirmationMessageForChangeFutureFlow();
				    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
		    //Step 36
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"), confirmationNumber, "Invopt_alloc");
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.removeEmployerDirectedRule(Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29167_Rebal_Sync_Allocation_DIM_Choose_Individual_Funds_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29168_Rebal_Sync_Allocation_DIM_Choose_Individual_Funds_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"));
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"100"};
			mapInvestmentOptions=investment.addInvestments(1,percentage);
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22712_Rebal_Sync_Allocation_DIM_Choose_Individual_Funds_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"9","20","25","11","35"};
			mapInvestmentOptions=investment.addInvestments(5,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(30000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29169_Rebal_Only_HMDI_Target_Date_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
		//Step 10
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
			//Step 11
			
		investment.selectTargetYearFund();
		Web.waitForElement(investment, "Header Review Your Changes");
		investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29170_Rebal_Only_HMDI_Target_Date_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Web.waitForElement(investment, "Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(10000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_22707_Rebal_Only_HMDI_Target_Date_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
	   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_29171_Rebal_And_Sync_Allocation_HMDI_Target_Date_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
		//Step 10
		 Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			
			//Step 11
			
		investment.selectTargetYearFund();
		investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29172_Rebal_And_Sync_Allocation_HMDI_Target_Date_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22708_Rebal_And_Sync_Allocation_HMDI_Target_Date_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
	   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_29178_Rebal_Only_Allocation_DIM_Choose_Individual_Funds_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29179_Rebal_Only_Allocation_DIM_Choose_Individual_Funds_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"));
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"100"};
			mapInvestmentOptions=investment.addInvestments(1,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22711_Rebal_Only_Allocation_DIM_Choose_Individual_Funds_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			
			//Step 9
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"9","20","25","11","35"};
			mapInvestmentOptions=investment.addInvestments(5,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
		
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			investment.verifyFundsinReviewAndConfirmationPageAreMatching(mapInvestmentOptionsReviewPage, mapInvestmentOptionsConfirmPage);
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions);
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_29173_Rebal_Only_HMDI_RiskBased_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
		//Step 10
		Web.clickOnElement(investment,"Choose Risk Based Funds");
		Web.waitForElement(investment, "Header Select Risk Based Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
			
			//Step 11
			
		investment.selectTargetYearFund();
		investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29174_Rebal_Only_HMDI_RiskBased_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22709_Rebal_Only_HMDI_RiskBased_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	

			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
	   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_29176_Rebal_Sync_HMDI_RiskBased_Offer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		/* Web.clickOnElement(investment,"Choose Risk Based Funds");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");*/
		//Step 10
		Web.clickOnElement(investment,"Choose Risk Based Funds");
		Web.waitForElement(investment, "Header Select Risk Based Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");	
			//Step 11
			
		investment.selectTargetYearFund();
		investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29177_Rebal_Sync_HMDI_RiskBased_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
		
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22710_Rebal_Sync_HMDI_RiskBased_Offer_No_Mntype(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 8
	

			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			investment.VerifyAllocatedPecentageForHMDIFunds();
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
	   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	

	@Test(dataProvider = "setData")
	public void DDTC_22685_Target_Date_Fund_Rebalance_Sync_Querterly_withALLMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			
			if (investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))) {
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify  Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is Not displayed ", false);
			}
			if (investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))) {
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify  Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is Not displayed ", false);
			}
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
		
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 10
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//TODO  Add static Message	
			//Step 11 &12
			
			investment.selectTargetYearFund();
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 13	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//Step 14
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 15
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			//Step 16
			
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
			leftmenu.clickNavigationLink("View/Manage my investments");
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22690_Target_Date_Fund_Rebalance_Sync_Annually_withNOMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton();
			
			investment.verifyMoneyTypeGroupIsDisplayed("All sources");
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			if(Web.isWebElementDisplayed(investment, "Label Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Label for Direct Future Investment is displayed",
						"Label for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
		
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 10
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//TODO  Add static Message	
			//Step 11 &12
			
			investment.selectTargetYearFund();
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 13	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//Step 14
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 15
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
			
			//Step 16
			
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
			leftmenu.clickNavigationLink("View/Manage my investments");
			
			
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22691_Target_Date_Fund_Rebalance_Sync_Annually_withNONAllMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(!Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is Not displayed ", true);
				
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is Not displayed",
						"Expand Sources Link is displayed ", true);
			}
		
			//Step 6
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
		
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			if (investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))) {
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is displayed ", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify  Money Type Grouping is displayed",
						" Money Type Grouping '"+Stock.GetParameterValue("moneyTypeGrouping")+"' is Not displayed ", false);
			}
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.VerifyFrequencyForRebalanceisMatching("Annually");
			if(Web.isWebElementDisplayed(investment, "CheckBox Direct Future Investments")){
				
				Reporter.logEvent(Status.PASS,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is displayed", false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify CheckBox for Direct Future Investment is displayed",
						"CheckBox for Direct Future Investment is Not displayed", false);
			}
			
			//Step 7
			investment.choseInvestmentOption("Rebalance Current Balance");
			
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			
			if(investment.verifyMoneyTypeLabelIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
		
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			//Step 10
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//TODO  Add static Message	
			//Step 11 &12
			
			investment.selectTargetYearFund();
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 13	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
			//Step 14
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 15
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			
			 confirmationNumber=investment.getRebalanceConfirmationNO();
			
			
			//Step 16
			
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag Target Date Fund", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag displayed on Choose Target Date Fund button for HMDI ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on Choose Target Date Fund button for HMDI ",
						"'Current' Flag is not displayed on Choose Target Date Fund button for HMDI ", true);
			}
			leftmenu.clickNavigationLink("View/Manage my investments");
			
			
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
				investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
				investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_29163_FullRebal_PAE_User_with_Inquire_Only_Permissions_Choose_IndividualFunds(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			 String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToI");
			 sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1],Stock.GetParameterValue("userName").substring(0, 9));	
			//Step 1 to 8
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 9
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 10
			
			investment.choseInvestmentOption("Rebalance Current Balance");;
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 11
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Rebalance your portfolio");
			investment.verifyWebElementDisplayed("Link Add/View All Funds");
			investment.verifyWebElementDisplayed("Reset All Changes Link");
			investment.verifyWebElementDisplayed("Submit Button Change Future Allocation");
			investment.verifyWebElementDisplayed("Back Link");
			
			
			
			//Step 12
			Web.clickOnElement(investment, "Link Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			//Step 13,14,15
			String[] percentage={"50","50"};
			investment.addInvestments(2,percentage);
			Thread.sleep(7000);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
				//Step 16
			 
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Review Changes Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			
			//Step 17
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
		    investment.verifyErrorPageDisplayed();
			
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
				String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToU");
				sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29164_FullRebal_PAE_User_with_Inquire_Only_Permissions_Choose_TargetDateFunds(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToI");
			sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
			//Step 1 to 8
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 9
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 10
			
			investment.choseInvestmentOption("Rebalance Current Balance");;
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 11		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Target Date Fund");
		
			//Step 12 to 15 
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Thread.sleep(7000);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 16
			
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
			Reporter.logEvent(Status.PASS,
					"Verify 'Investment Option' opened in New Window from Review Changes Page",
					"'Investment Option' opened in New Window ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify 'Investment Option' opened in New Window from Review Changes Page",
					"'Investment Option' is not opened in New Window ", true);
		 }
			
		
			//Step 17
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
		    investment.verifyErrorPageDisplayed();
		   
			
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
				String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToU");
				sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29165_FullRebal_PAE_User_with_Inquire_Only_Permissions_Choose_RiskBasedFunds(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 8
			String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToI");
			sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 9
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 10
			
			investment.choseInvestmentOption("Rebalance Current Balance");;
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 11		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			investment.verifyPageHeaderIsDisplayed("Header Select Risk Based Fund");
		
			//Step 12 to 15 
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Thread.sleep(7000);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 16
			
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
			Reporter.logEvent(Status.PASS,
					"Verify 'Investment Option' opened in New Window from Review Changes Page",
					"'Investment Option' opened in New Window ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify 'Investment Option' opened in New Window from Review Changes Page",
					"'Investment Option' is not opened in New Window ", true);
		 }
			
		
			//Step 17
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
		    investment.verifyErrorPageDisplayed();
		   
			
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
				String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToU");
				sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_29166_FullRebal_PAE_User_with_Inquire_Only_Permissions_BasedOnModelPortfolio(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToI");
			sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
			//Step 1 to 8
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 9
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 10
			
			investment.choseInvestmentOption("Rebalance Current Balance");;
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 11		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			Web.clickOnElement(investment,"Link Based On Model Portfolio");
			Web.waitForElement(investment, "Select a Model Portfolio");
			investment.verifyPageHeaderIsDisplayed("Select a Model Portfolio");
		
			//Step 12 to 15 
			String selectedTargetDateFund=investment.selectTargetYearFund();
			Thread.sleep(7000);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 16
			/*
			 * Verify investment is opens in new window
			 * this step is not applicable for Model Portfolio
			 */
		
			//Step 17
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
		    investment.verifyErrorPageDisplayed();
		   
			
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
				String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToU");
				sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], userName.substring(0, 9));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_24643_Handling_Employer_directed_MTGs_Rebalance_DIM(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 5
			String sqlQuery[]=Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			sqlQuery[0] = Common.getParticipantDBName(Stock.GetParameterValue("userName")) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_id"),Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping"));
			
			//Step 1 to 6
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			if(Stock.GetParameterValue("EmployerDirected").equalsIgnoreCase("Yes"))	{
			if(!investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping")+" is Filtered out and not Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Filtered out and not Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping")+" is not Filtered out and is Displayed on My Investments Page", true);
			}
			}
			
		if(Stock.GetParameterValue("EmployerDirected").equalsIgnoreCase("No"))	{
			if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				Web.clickOnElement(investment, "Expand Sources");
			}
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping")+" is included and Displayed on My Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is included and Displayed on My Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("EmpDirectedmoneyTypeGrouping")+" is not Displayed on My Investments Page", true);
			}
		}
			
			//Step 7
			investment.clickChangeMyInvestmentButton(Stock.GetParameterValue("moneyTypeGrouping"));
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Rebalance Current Balance");
			if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				
				Reporter.logEvent(Status.PASS,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is Displayed on Change Investments Page", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Money Type Grouping is Displayed on Change Investments Page",
						"Money Type Grouping:"+Stock.GetParameterValue("moneyTypeGrouping")+" is not Displayed on Change Investments Page", true);
			}
			
			//Step 8
			investment.choseInvestmentOption("Rebalance Current Balance");
			investment.selectFrequencyForRebalance(Stock.GetParameterValue("RebalFrequency"));
			
			if(Stock.GetParameterValue("selectFutureInvestmentCheckBox").equalsIgnoreCase("NO")){
				investment.selectFutureInvestmentCheckBox(false);
			}
			
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			/*if(investment.isTextFieldDisplayed("Rebalance my current balance")){
			
			Web.clickOnElement(investment, "Continue Button");
			}*/
		
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			//Step 9
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			
			Web.waitForElement(investment, "Header Rebalance your portfolio");
			investment.verifyPageHeaderIsDisplayed("Header Rebalance your portfolio");
			
		
			//Step 10
			Web.waitForElement(investment, "Rebalance Add/View All Funds");
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			
			Web.waitForPageToLoad(Web.getDriver());
			
			String[] percentage={"50","50"};
			investment.addInvestments(2,percentage);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 11
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			//Step12
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), confirmationNumber, Stock.GetParameterValue("FrequencyCode"));
		   
			
			Web.clickOnElement(homePage, "LOG OUT");
			Web.waitForElement(login, "SIGN IN");
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				investment.removeEmployerDirectedRule(Stock.GetParameterValue("ga_id"));
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public String getInvestmentsSubmissionTime(){
		String time =null;
		String minute=null;
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.MINUTE)<=9){
			 minute="0"+Integer.toString(cal.get(Calendar.MINUTE));
			
		}
		else{
			 minute=Integer.toString(cal.get(Calendar.MINUTE));
		}
		 time=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
				cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
				+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
				Integer.toString(cal.get(Calendar.YEAR))+","+" "+
				Integer.toString(cal.get(Calendar.HOUR))+":"+minute+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
		 System.out.println("TIME STAMP"+time);
		return time;
		
	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22678_FullRebal_ModelPortfolio(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			//Step 6
			investment.clickChangeMyInvestmentButton();
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
			investment.verifyWebElementDisplayed("Continue Button");
			investment.verifyWebElementDisplayed("Back Button");
			
			//Step 7
			
			investment.choseInvestmentOption("Rebalance Current Balance");;
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
		
			
			//Step 8 & 9		
			
			Web.clickOnElement(investment,"Link Based On Model Portfolio");
			Web.waitForElement(investment, "Select a Model Portfolio");
			investment.verifyPageHeaderIsDisplayed("Select a Model Portfolio");
			investment.isTextFieldDisplayed("Because you own investments with restrictions, only your unrestricted assets will be exchanged into the model portfolio you select.");
			investment.isTextFieldDisplayed("Changing your investment allocation will result in your current core account balance and future contributions being allocated to your selected investment(s).");
			investment.isTextFieldDisplayed("Model portfolios contain a mix of funds offered by your plan and your allocations will be automatically rebalanced on a periodic basis. The underlying funds, allocations, and rebalance frequency of the portfolio are subject to change by your plan. By selecting a model portfolio, you can view the funds within the portfolio.");
			
			//Step 10 11 & 12
			String selectedModelPortfolioFund=investment.selectModelPortfolioFund();
			Thread.sleep(7000);
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 13
			investment.isTextFieldDisplayed("Changing your investment allocation will result in your current core account balance and future contributions being allocated to your selected investment(s).");
			investment.isTextFieldDisplayed("Model portfolios contain a mix of funds offered by your plan. Your allocations will be automatically rebalanced on a quarterly basis. The underlying funds, allocations, and rebalance frequency of the portfolio are subject to change by your plan.");
			investment.isTextFieldDisplayed("The funds you are selecting are part of a model portfolio. The automatic rebalancing feature of the model will override any other rebalancing elections you may have requested.");
			
			
			//Step 15
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForModelPortfolio();    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 16
			
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), confirmationNumber, "Step");
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_25495_Custom_Enrollment_PPT_with_NotALLMTGs(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 to 6
			enrollment.selectCustomizeEnroll();
			Common.waitForProgressBar();
			enrollment.verifyPriorPlanContributionsPage();
			
			//Step 7 & 8
			enrollment.addPreviousContribution(Stock.GetParameterValue("contibutionAmount"));
			
			deferral.verifyContributionRatePage();
			//Step 9 to 11
			deferral.click_Select_Your_Contribution_Rate();
			//Step 12
			Web.clickOnElement(deferral, "Continue Button");
			
			deferral.verifyWebElementDisplayed("Split Contribution");
			//Step 13
			deferral.verifyPlanRulesPopUp();
			//Step 14
			deferral.verifyContributionTypesDisplayed();
			//Step 15
			deferral.selectContributionType(Stock.GetParameterValue("Contribution_type"));
			//Step 16
			deferral.add_Auto_Increase("Before Add Auto Increase");
			
			Web.clickOnElement(deferral, "Button Confirm And Continue");
			
			ManageMyInvestment investment = new ManageMyInvestment();
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Header My Allocations");
			investment.verifyPageHeaderIsDisplayed("Header My Allocations");
			/*investment.isTextFieldDisplayed("Select investments for each of your allocation groups.");
			//Step 17
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			}
			
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping1")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			}
			//Step 18
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));*/
			//Step 19
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 20
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
		
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage);
			//Step 21
			if (!Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is not displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is displayed", true);
			}
			
			/*investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage1={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage1);*/
			//Step 22
			Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
		 		investment.isInvestmentOptiondDisplayed(key);
		 	};
		
			//Step 23
			Web.clickOnElement(investment, "Button Confirm");
			
			//Step 24
			Common.waitForProgressBar();
			Web.waitForElement(enrollment, "Button I Agree Enroll Now");
			enrollment.isTextFieldDisplayed("CONTRIBUTION RATE");
			enrollment.isTextFieldDisplayed("COMPANY MATCH");
			enrollment.isTextFieldDisplayed("INVESTMENT OPTION");
			enrollment.verifyContributionRateMatching(Stock.GetParameterValue("Contribution Rate"));
		 
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			
			
			//Step 25
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			
			//Step 26
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			//Step 27
			Web.waitForElement(enrollment, "Confirmation Number");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			enrollment.isTextFieldDisplayed("Investment Options");
			
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				
			
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25499_Custom_Enrollment_with_Plan_level_ALL_MTGs(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 to 6
			enrollment.selectCustomizeEnroll();
			Common.waitForProgressBar();
			enrollment.verifyPriorPlanContributionsPage();
			
			//Step 7 & 8
			enrollment.addPreviousContribution(Stock.GetParameterValue("contibutionAmount"));
			
			deferral.verifyContributionRatePage();
			//Step 9 to 11
			deferral.click_Select_Your_Contribution_Rate();
			//Step 12
			Web.clickOnElement(deferral, "Continue Button");
			
			deferral.verifyWebElementDisplayed("Split Contribution");
			//Step 13
			deferral.verifyPlanRulesPopUp();
			//Step 14
			deferral.verifyContributionTypesDisplayed();
			//Step 15
			deferral.selectContributionType(Stock.GetParameterValue("Contribution_type"));
			//Step 16
			deferral.add_Auto_Increase("Before Add Auto Increase");
			
			Web.clickOnElement(deferral, "Button Confirm And Continue");
			
			ManageMyInvestment investment = new ManageMyInvestment();
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Header My Allocations");
			investment.verifyPageHeaderIsDisplayed("Header My Allocations");
			/*investment.isTextFieldDisplayed("Select investments for each of your allocation groups.");
			//Step 17
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			}
			
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping1")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			}
			//Step 18
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));*/
			//Step 19
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 20
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
		
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage);
			//Step 21
			if (!Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is not displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is displayed", true);
			}
			
			/*investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage1={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage1);*/
			//Step 22
			Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
		 		investment.isInvestmentOptiondDisplayed(key);
		 	};
		
			//Step 23
			Web.clickOnElement(investment, "Button Confirm");
			
			//Step 24
			Common.waitForProgressBar();
			Web.waitForElement(enrollment, "Button I Agree Enroll Now");
			enrollment.isTextFieldDisplayed("CONTRIBUTION RATE");
			enrollment.isTextFieldDisplayed("COMPANY MATCH");
			enrollment.isTextFieldDisplayed("INVESTMENT OPTION");
			enrollment.verifyContributionRateMatching(Stock.GetParameterValue("Contribution Rate"));
		 
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			
			
			//Step 25
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			
			//Step 26
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			//Step 27
			Web.waitForElement(enrollment, "Confirmation Number");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			enrollment.isTextFieldDisplayed("Investment Options");
			
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25496_Custom_Enrollment_PPT_with_PartLevel_MTGs(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 to 6
			enrollment.selectCustomizeEnroll();
			Common.waitForProgressBar();
			enrollment.verifyPriorPlanContributionsPage();
			
			//Step 7 & 8
			enrollment.addPreviousContribution(Stock.GetParameterValue("contibutionAmount"));
			
			deferral.verifyContributionRatePage();
			//Step 9 to 11
			deferral.click_Select_Your_Contribution_Rate();
			//Step 12
			Web.clickOnElement(deferral, "Continue Button");
			
			deferral.verifyWebElementDisplayed("Split Contribution");
			//Step 13
			deferral.verifyPlanRulesPopUp();
			//Step 14
			deferral.verifyContributionTypesDisplayed();
			//Step 15
			deferral.selectContributionType(Stock.GetParameterValue("Contribution_type"));
			//Step 16
			deferral.add_Auto_Increase("Before Add Auto Increase");
			
			Web.clickOnElement(deferral, "Button Confirm And Continue");
			
			ManageMyInvestment investment = new ManageMyInvestment();
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Header My Allocations");
			investment.verifyPageHeaderIsDisplayed("Header My Allocations");
			investment.isTextFieldDisplayed("Select investments for each of your allocation groups.");
			//Step 17
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			}
			
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping1")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			}
			//Step 18
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			//Step 19
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 20
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
		
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			investment.addInvestmentsforEnrollmentFlow(2,percentage);
			//Step 21
			if (!Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is not displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is displayed", true);
			}
			
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage1={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage1);
			//Step 22
			Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
		 		investment.isInvestmentOptiondDisplayed(key);
		 	};
			
			//Step 23
			Web.clickOnElement(investment,"Continue Button");
			
			//Step 24
			Common.waitForProgressBar();
			Web.waitForElement(enrollment, "Button I Agree Enroll Now");
			enrollment.isTextFieldDisplayed("CONTRIBUTION RATE");
			enrollment.isTextFieldDisplayed("COMPANY MATCH");
			enrollment.isTextFieldDisplayed("INVESTMENT OPTION");
			enrollment.verifyContributionRateMatching(Stock.GetParameterValue("Contribution Rate"));
		 
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			
			
			//Step 25
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			
			//Step 26
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			//Step 27
			Web.waitForElement(enrollment, "Confirmation Number");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			enrollment.isTextFieldDisplayed("Investment Options");
			
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				
			
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_25497_Custom_Enrollment_PPT_with_EmployerDirected_MTGs(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 to 6
			enrollment.selectCustomizeEnroll();
			Common.waitForProgressBar();
			enrollment.verifyPriorPlanContributionsPage();
			
			//Step 7 & 8
			enrollment.addPreviousContribution(Stock.GetParameterValue("contibutionAmount"));
			
			deferral.verifyContributionRatePage();
			//Step 9 to 11
			deferral.click_Select_Your_Contribution_Rate();
			//Step 12
			Web.clickOnElement(deferral, "Continue Button");
			
			deferral.verifyWebElementDisplayed("Split Contribution");
			//Step 13
			deferral.verifyPlanRulesPopUp();
			//Step 14
			deferral.verifyContributionTypesDisplayed();
			//Step 15
			deferral.selectContributionType(Stock.GetParameterValue("Contribution_type"));
			//Step 16
			deferral.add_Auto_Increase("Before Add Auto Increase");
			
			Web.clickOnElement(deferral, "Button Confirm And Continue");
			
			ManageMyInvestment investment = new ManageMyInvestment();
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Header My Allocations");
			investment.verifyPageHeaderIsDisplayed("Header My Allocations");
			investment.isTextFieldDisplayed("Select investments for each of your allocation groups.");
			//Step 17
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			}
			
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping1")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			}
			//Step 18
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			//Step 19
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 20
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
		
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			investment.addInvestmentsforEnrollmentFlow(2,percentage);
			//Step 21
			if (!Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is not displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is displayed", true);
			}
			
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage1={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage1);
			//Step 22
			Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
		 		investment.isInvestmentOptiondDisplayed(key);
		 	};
			
			//Step 23
			Web.clickOnElement(investment,"Continue Button");
			
			//Step 24
			Common.waitForProgressBar();
			Web.waitForElement(enrollment, "Button I Agree Enroll Now");
			enrollment.isTextFieldDisplayed("CONTRIBUTION RATE");
			enrollment.isTextFieldDisplayed("COMPANY MATCH");
			enrollment.isTextFieldDisplayed("INVESTMENT OPTION");
			enrollment.verifyContributionRateMatching(Stock.GetParameterValue("Contribution Rate"));
		 
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			
			
			//Step 25
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			
			//Step 26
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			//Step 27
			Web.waitForElement(enrollment, "Confirmation Number");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			enrollment.isTextFieldDisplayed("Investment Options");
			
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				
			
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25500_Custom_Enrollment_with_No_MTGs(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 to 6
			enrollment.selectCustomizeEnroll();
			Common.waitForProgressBar();
			enrollment.verifyPriorPlanContributionsPage();
			
			//Step 7 & 8
			enrollment.addPreviousContribution(Stock.GetParameterValue("contibutionAmount"));
			
			deferral.verifyContributionRatePage();
			//Step 9 to 11
			deferral.click_Select_Your_Contribution_Rate();
			//Step 12
			Web.clickOnElement(deferral, "Continue Button");
			
			deferral.verifyWebElementDisplayed("Split Contribution");
			//Step 13
			deferral.verifyPlanRulesPopUp();
			//Step 14
			deferral.verifyContributionTypesDisplayed();
			//Step 15
			deferral.selectContributionType(Stock.GetParameterValue("Contribution_type"));
			//Step 16
			deferral.add_Auto_Increase("Before Add Auto Increase");
			
			Web.clickOnElement(deferral, "Button Confirm And Continue");
			
			ManageMyInvestment investment = new ManageMyInvestment();
			Common.waitForProgressBar();
			Web.waitForElement(investment, "Header My Allocations");
			investment.verifyPageHeaderIsDisplayed("Header My Allocations");
			/*investment.isTextFieldDisplayed("Select investments for each of your allocation groups.");
			//Step 17
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));
			}
			
			if(investment.verifyMoneyTypeGroupDisplayed(Stock.GetParameterValue("moneyTypeGrouping1")))
			{
				investment.verifyInvestmentButtonDisplayedforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			}
			//Step 18
			investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping"));*/
			//Step 19
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 20
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
		
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage);
			//Step 21
			if (!Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is not displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes is not displayed",
						"Review Your Changes is displayed", true);
			}
			
			/*investment.clickSelectInvestmentButtonforMoneyTypeGroup(Stock.GetParameterValue("moneyTypeGrouping1"));
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			
			if (Web.isWebElementDisplayed(investment,"Table Select Funds")) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage1={"50","50"};
			mapInvestmentOptions=investment.addInvestmentsforEnrollmentFlow(2,percentage1);*/
			//Step 22
			Set<String> keys = mapInvestmentOptions.keySet();
		 	for(String key: keys){
		 		investment.isInvestmentOptiondDisplayed(key);
		 	};
		
			//Step 23
			Web.clickOnElement(investment, "Button Confirm");
			
			//Step 24
			Common.waitForProgressBar();
			Web.waitForElement(enrollment, "Button I Agree Enroll Now");
			enrollment.isTextFieldDisplayed("CONTRIBUTION RATE");
			enrollment.isTextFieldDisplayed("COMPANY MATCH");
			enrollment.isTextFieldDisplayed("INVESTMENT OPTION");
			enrollment.verifyContributionRateMatching(Stock.GetParameterValue("Contribution Rate"));
		 
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			
			
			//Step 25
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			
			//Step 26
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			//Step 27
			Web.waitForElement(enrollment, "Confirmation Number");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			enrollment.isTextFieldDisplayed("Investment Options");
			
		 	for(String key: keys){
			enrollment.isInvestmentOptiondDisplayed(key);
		 	}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
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
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_25438_Auto_Enrollment_for_NonAllMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 
			enrollment.selectQuickEnroll();;
			
			//Step 4 to 9
			String contributionPercent=enrollment.verifyAutoEnrollmentSection();
			
			//Step 10
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
			
		    //Step 11
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 12
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 13
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25441_Auto_Enrollment_for_PlanLevel_AllMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 
			enrollment.selectQuickEnroll();;
			
			//Step 4 to 10
			String contributionPercent=enrollment.verifyAutoEnrollmentSection();
			
			//Step 11
			Web.clickOnElement(enrollment, "Label No");
			//Step 12
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			Web.clickOnElement(enrollment, "Link Participation Agreement");
			
			lib.Reporter.logEvent(Status.INFO, "Clicking on Participation Agreement Link", 
					"Clicked on Participation Agreement Link",
					false);
			
			if(Web.isWebElementDisplayed(enrollment, "Participation Agreement Modal", true)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Not Displayed",
						true);

			}
			//Step13
			Web.clickOnElement(enrollment, "Link I Agree");
			
			//Step 14
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
		    //Step 15
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 16
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 17
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25442_Auto_Enrollment_for_PPT_with_NoMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 
			enrollment.selectQuickEnroll();;
			
			//Step 4 to 10
			String contributionPercent=enrollment.verifyAutoEnrollmentSection();
			
			//Step 11
			Web.clickOnElement(enrollment, "Label No");
			//Step 12
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			Web.clickOnElement(enrollment, "Link Participation Agreement");
			
			lib.Reporter.logEvent(Status.INFO, "Clicking on Participation Agreement Link", 
					"Clicked on Participation Agreement Link",
					false);
			
			if(Web.isWebElementDisplayed(enrollment, "Participation Agreement Modal", true)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Not Displayed",
						true);

			}
			//Step13
			Web.clickOnElement(enrollment, "Link I Agree");
			
			//Step 14
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
		    //Step 15
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 16
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 17
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_25440_Auto_Enrollment_for_Employerdirected_MTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 
			enrollment.selectQuickEnroll();;
			
			//Step 4 to 10
			String contributionPercent=enrollment.verifyAutoEnrollmentSection();
			
			//Step 11
			Web.clickOnElement(enrollment, "Label No");
			//Step 12
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			Web.clickOnElement(enrollment, "Link Participation Agreement");
			
			lib.Reporter.logEvent(Status.INFO, "Clicking on Participation Agreement Link", 
					"Clicked on Participation Agreement Link",
					false);
			
			if(Web.isWebElementDisplayed(enrollment, "Participation Agreement Modal", true)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Not Displayed",
						true);

			}
			//Step13
			Web.clickOnElement(enrollment, "Link I Agree");
			
			//Step 14
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
		    //Step 15
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 16
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 17
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_25445_Quick_Enrollment_for_PPT_with_AllMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			
			//Step 3 
			enrollment.selectQuickEnroll();
			
			//Step 4 to 10
			String contributionPercent=enrollment.verifyQuickEnrollmentSection();
			
			//Step 11
			Web.clickOnElement(enrollment, "Label No");
			//Step 12
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			Web.clickOnElement(enrollment, "Link Participation Agreement");
			
			lib.Reporter.logEvent(Status.INFO, "Clicking on Participation Agreement Link", 
					"Clicked on Participation Agreement Link",
					false);
			
			if(Web.isWebElementDisplayed(enrollment, "Participation Agreement Modal", true)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Not Displayed",
						true);

			}
			
			Web.clickOnElement(enrollment, "Link I Agree");
			
			//Step13
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
			//Step 14
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 15
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 16
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_25446_Quick_Enrollment_for_PPT_with_NoMTG(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			ManageMyInvestment investment=new ManageMyInvestment();
			//Step 1 to 2
			Deferrals deferral=new Deferrals();
			LandingPage homePage=new LandingPage();
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			
			Enrollment enrollment=new Enrollment(mfaPage);
			enrollment.get();
			//Step 3 
			enrollment.selectQuickEnroll();
			
			//Step 4 to 10
			String contributionPercent=enrollment.verifyQuickEnrollmentSection();
			
			//Step 11
			Web.clickOnElement(enrollment, "Label No");
			//Step 12
			enrollment.isTextFieldDisplayed("By clicking the \"I Agree, Enroll Now\" button, you confirm you have reviewed and agree to the ");
			enrollment.isTextFieldDisplayed("Participation Agreement for Online Enrollment");
			Web.clickOnElement(enrollment, "Link Participation Agreement");
			
			lib.Reporter.logEvent(Status.INFO, "Clicking on Participation Agreement Link", 
					"Clicked on Participation Agreement Link",
					false);
			
			if(Web.isWebElementDisplayed(enrollment, "Participation Agreement Modal", true)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Displayed",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Participation Agreement Modal is Displayed", 
						"Participation Agreement Modal is Not Displayed",
						true);

			}
			
			Web.clickOnElement(enrollment, "Link I Agree");
			
			//Step13
			Web.clickOnElement(enrollment, "Button I Agree Enroll Now");
			
			//Step 14
			Web.waitForElement(enrollment, "Button View My Account");
			enrollment.isTextFieldDisplayed("Contribution Rate");
			if(enrollment.verifyTextFieldDisplayed(contributionPercent)){
				
				lib.Reporter.logEvent(Status.PASS, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Displayed\nContribution Perecent:"+contributionPercent,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Contribution Perecent is Displayed", 
						"Contribution Perecent is Not Displayed",
						true);
				
			}
			String confirmationNumber=enrollment.getWebElementText("Confirmation Number");
			
			if(Web.isWebElementDisplayed(enrollment, "Confirmation Number")){
				lib.Reporter.logEvent(Status.PASS, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is Displayed\nConfirmation Number:"+confirmationNumber,
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Confirmation Number is Displayed", 
						"Confirmation Number is not Displayed", true);
				
			}
			//Step 15
			Web.clickOnElement(enrollment, "Button View My Account");
			if(Web.isWebElementDisplayed(homePage, "HOME", true)){
				lib.Reporter.logEvent(Status.PASS, "Verify Home Page is Displayed", 
						"User is Navigated to Home Page",
						true);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL, "Verify Home Page is Displayed", 
						"User is not Navigated to Home Page",
						true);
				
			}
			//Step 16
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_29363_LIAT_Smart_Restriction_Rebalance_LandingPage_Withonly_UnRetsricted_Fund(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome objretirementIncome=new RetirementIncome(homePage);
			
			ManageMyInvestment investment = new ManageMyInvestment();
			
			objretirementIncome.get();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s). Your current account balance also will be allocated to your selected investment(s)"
					+ " unless such investment(s) are restricted, requiring you to separately select your current account balance investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9 & 10
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			//Step 11 & 12
			investment.verifyUnRestrictedFundsWarningSymbol();
			
			//Step 13 & 14
			
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			
			String[] percentage1={"20","50","30"};
			mapInvestmentOptionsRebalance=investment.addInvestments(3,percentage1);
			Web.waitForElement(investment,"Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 15
			investment.isTextFieldDisplayed("Future Investments");
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			investment.isTextFieldDisplayed("Rebalance Your Portfolio");
			investment.VerifySmartRestrictionReviewChangesPageforRebalanceInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptionsRebalance, "Rebalance Investment");
			//Step 16
			//investment.verifyEvaluationMessageinReviewPage();
			//Step 17
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForSmartRestriction();
			ConfirmationNos=investment.verifyConfirmationNumbersForSmartRestriction();
			//Step 18
			investment.isTextFieldDisplayed("Future Investments");
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			investment.isTextFieldDisplayed("Rebalance your portfolio");
			investment.VerifySmartRestrictionReviewChangesPageforRebalanceInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptionsRebalance, "Rebalance Investment");
			
			//Step 19
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), ConfirmationNos.get(0), "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(0), "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(0), "Invopt_alloc");
			//verifying in DB for Rebalance Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(1), "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), ConfirmationNos.get(1), "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), ConfirmationNos.get(1), Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_29364_LIAT_Smart_Restriction_Rebalance_LandingPage_With_Restricted_UnRetsricted_Funds(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome objretirementIncome=new RetirementIncome(homePage);
			
			ManageMyInvestment investment = new ManageMyInvestment();
			//Step 1 to 4
			objretirementIncome.get();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s). Your current account balance also will be allocated to your selected investment(s)"
					+ " unless such investment(s) are restricted, requiring you to separately select your current account balance investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9 & 10
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			if (Web.isWebElementDisplayed(investment,"Header Rebalance your portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance your Portfolio Page is displayed",
						"Rebalance your Portfolio Page is not displayed ", true);
			}
			//Step 11 & 12
			investment.verifyRestrictedFundsWarningSymbol();
			investment.verifyUnRestrictedFundsWarningSymbol();
			
			//Step 13 & 14
			
			Web.clickOnElement(investment, "Rebalance Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			
			String[] percentage1={"20","50","30"};
			mapInvestmentOptionsRebalance=investment.addInvestments(3,percentage1);
			Web.waitForElement(investment,"Header Review Your Changes");
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			//Step 15
			investment.isTextFieldDisplayed("Future Investments");
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			investment.isTextFieldDisplayed("Rebalance Your Portfolio");
			investment.VerifySmartRestrictionReviewChangesPageforRebalanceInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptionsRebalance, "Rebalance Investment");
			//Step 16
			//TODO no restricted funds should display in review page
			//Step 17
			//investment.verifyEvaluationMessageinReviewPage();
			
			//Step 18
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForSmartRestriction();
			ConfirmationNos=investment.verifyConfirmationNumbersForSmartRestriction();
			//Step 19
			investment.isTextFieldDisplayed("Future Investments");
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			investment.isTextFieldDisplayed("Rebalance your portfolio");
			investment.VerifySmartRestrictionReviewChangesPageforRebalanceInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptionsRebalance, "Rebalance Investment");
			
			//Step 20
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), ConfirmationNos.get(0), "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(0), "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(0), "Invopt_alloc");
			//verifying in DB for Rebalance Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"),  ConfirmationNos.get(1), "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"), ConfirmationNos.get(1), "Step");
			investment.verifyTRF_BasicTableInDB("VerifyRebalanceConfirmationRecord",Stock.GetParameterValue("username"), ConfirmationNos.get(1), Stock.GetParameterValue("FrequencyCode"));
		   
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	@Test(dataProvider = "setData")
	public void DDTC_29365_LIAT_No_SmartRestriction_When_Pending_TranferExists_Due_To_F2FTransfer(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav= new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftNav);
			//Step 1 to 4
			investment.get();
			//preCondition
			investment.submitFundToFundTransfer();
			Web.clickOnElement(homePage, "HOME");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			RetirementIncome objretirementIncome=new RetirementIncome();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 10
			Web.waitForElement(investment,"Header Review Your Changes");
			if(!Web.isWebElementDisplayed(investment, "Header Rebalance Your Portfolio")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is Not displayed", true);
			} else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is displayed", true);
		}
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
						//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 13
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  confirmationNumber, "Invopt_alloc");
			
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	@Test(dataProvider = "setData")
	public void DDTC_29366_LIAT_No_SmartRestriction_When_Pending_TranferExists_Due_To_DCATransaction(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav= new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftNav);
			//Step 1 to 4
			investment.get();
			//preCondition
			investment.submitDollarCostAverageTransaction();
			Web.clickOnElement(homePage, "HOME");
			RetirementIncome objretirementIncome=new RetirementIncome();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 10
			Web.waitForElement(investment,"Header Review Your Changes");
			if(!Web.isWebElementDisplayed(investment, "Header Rebalance Your Portfolio")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is Not displayed", true);
			} else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is displayed", true);
		}
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 13
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  confirmationNumber, "Invopt_alloc");
			
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	@Test(dataProvider = "setData")
	public void DDTC_29367_LIAT_No_SmartRestriction_When_Pending_TranferExists_DueTo_FullRebal_Via_LeftNav(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav= new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftNav);
			//Step 1 to 4
			investment.get();
			//preCondition
			investment.submitRebalanceAllocationViaLeftNav();
			Web.clickOnElement(homePage, "HOME");
			RetirementIncome objretirementIncome=new RetirementIncome();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 10
			Web.waitForElement(investment,"Header Review Your Changes");
			if(!Web.isWebElementDisplayed(investment, "Header Rebalance Your Portfolio")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is Not displayed", true);
			} else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is displayed", true);
		}
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 13
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  confirmationNumber, "Invopt_alloc");
			
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	@Test(dataProvider = "setData")
	public void DDTC_29368_LIAT_No_SmartRestriction_When_Pending_TranferExists_DueTo_FullRebal_Via_SmartRestriction(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			RetirementIncome objretirementIncome=new RetirementIncome(homePage);
			
			ManageMyInvestment investment = new ManageMyInvestment();
			
			objretirementIncome.get();
			//preCondition
			objretirementIncome.navigateToSmartRestrictionPage();
			
			investment.submitFullRebalanceAllocationViaSmartRestriction();
			
			Web.clickOnElement(homePage, "HOME");
			
			
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 10
			Web.waitForElement(investment,"Header Review Your Changes");
			if(!Web.isWebElementDisplayed(investment, "Header Rebalance Your Portfolio")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is Not displayed", true);
			} else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is displayed", true);
		}
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
		
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 13
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  confirmationNumber, "Invopt_alloc");
			
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	@Test(dataProvider = "setData")
	public void DDTC_29369_LIAT_No_SmartRestriction_When_Pending_TranferExists_DueTo_ModelPortfolio_Transaction(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
		
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftNav= new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftNav);
			investment.get();
			//preCondition
			//investment.submitFundToFundTransfer();
			Web.clickOnElement(homePage, "HOME");
			RetirementIncome objretirementIncome=new RetirementIncome();
			//Step 5 & 6
			objretirementIncome.navigateToSmartRestrictionPage();
			investment.isTextFieldDisplayed("Select your investment strategy");
			//Step 7
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			//Step 8
			investment.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
			
			investment.verifyWebElementDisplayed("Smart Restriction Error Message");
			
			String expectedErrorMsg="Changing your investment allocation will result in your future contributions being allocated "
					+ "to your selected investment(s).";
			
			String ActualErrorMsg=investment.getWebElementText("Smart Restriction Error Message");
			
			if(Web.VerifyText(expectedErrorMsg, ActualErrorMsg)){
				Reporter.logEvent(Status.PASS,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is displayed\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Smart Restriction Error Message is displayed",
						"Smart Restriction Error Message is not Matching\nExpected:"+expectedErrorMsg+"\nActual:"+ActualErrorMsg, true);
			}
			
			//Step 9
			Web.clickOnElement(investment, "Link Add/View All Funds");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment,"Table Select Funds");
	
			if (Web.isWebElementDisplayed(investment,"Table Select Funds",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Investment Allocation table is displayed",
						"Investment Allocation table is not displayed ", true);
			}
			
			String[] percentage={"50","50"};
			mapInvestmentOptions=investment.addInvestments(2,percentage);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//Step 10
			Web.waitForElement(investment,"Header Review Your Changes");
			if(!Web.isWebElementDisplayed(investment, "Header Rebalance Your Portfolio")){
				
				Reporter.logEvent(Status.PASS,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is Not displayed", true);
			} else{
				Reporter.logEvent(Status.FAIL,
						"Verify Rebalance Your Portfolio Section is Not displayed",
						"Rebalance Your Portfolio Section is displayed", true);
		}
			investment.verifyPageHeaderIsDisplayed("Header Review Your Changes");
			
			//Step 11
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(20000);
			Web.waitForElement(investment, "Header Confirmation");
			investment.verifyPageHeaderIsDisplayed("Header Confirmation");
			investment.verifyConfirmationMessageForChangeFutureFlow();
			//Step 12
			investment.VerifySmartRestrictionReviewChangesPageforFutureInvestments();
			investment.VerifyAllocatedPecentageForFunds(mapInvestmentOptions, "Future Investment");
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			//Step 13
			//verifying in DB for Future Investments
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.GetParameterValue("username"), confirmationNumber, "Event");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.GetParameterValue("username"),  confirmationNumber, "Step");
			investment.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.GetParameterValue("username"),  confirmationNumber, "Invopt_alloc");
			
			
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
				ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}	
	
	
	@Test(dataProvider = "setData")
	public void DDTC_30083_FE_HMDI_Access_Online_Advice(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
			//Step 5
			investment.clickChangeMyInvestmentButton();
		
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
		
			//Step 6 to 9
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 10
			investment.verifyWebElementDisplayed("Access Online Advice");			
			investment.isTextFieldDisplayed("Online investment advice can help you select investments that align with your retirement objectives.");
			//Step 11
			Web.clickOnElement(investment,"Access Online Advice");
			Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");
			   Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   
			    	   Thread.sleep(10000);
				    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Financial Engines - Log in"))
				    	  {
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Financial Engine Page'opened in New Window",
									"'Financial Engine Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Financial Engine Page'opened in New Window ",
									"'Financial Engine Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
	           Web.getDriver().switchTo().defaultContent();
	        
		 
		}catch (Exception e) {
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void DDTC_30104_FE_DIM_Access_Online_Advice(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 4
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
			//Step 5
			investment.clickChangeMyInvestmentButton();
		
			
			investment.verifyInvestmentOptionIsDisplayed("Rebalance Current Balance");
			investment.verifyInvestmentOptionIsDisplayed("Change Future Contribution");
			investment.verifyInvestmentOptionIsDisplayed("Change Current Balance Investment");
			investment.verifyInvestmentOptionIsDisplayed("Dollar Cost");
		
			//Step 6 to 9
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			investment.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 10
			investment.verifyWebElementDisplayed("Access Online Guidance");			
			investment.isTextFieldDisplayed("Online Guidance can help you get pointed in the right direction.");
			//Step 11
			Web.clickOnElement(investment,"Access Online Guidance");
			Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");
			Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(10000);
			    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Financial Engines - Log in")){
			    	  
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Financial Engine Page'opened in New Window",
									"'Financial Engine Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Financial Engine Page'opened in New Window ",
									"'Financial Engine Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
	           Web.getDriver().switchTo().defaultContent();
	        
		} 
		catch (Exception e) {
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void DDTC_30106_FE_Guidance_HMDI_Access_Online_Advice(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
			
		
			//Step 6
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 7
			investment.verifyWebElementDisplayed("Access Online Advice");			
			investment.isTextFieldDisplayed("Online investment advice can help you select investments that align with your retirement objectives.");
			//Step 8
			Web.clickOnElement(investment,"Access Online Advice");
			/*Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");*/
			   Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   
			    	   Thread.sleep(10000);
				    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Financial Engines - Log in"))
				    	  {
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Financial Engine Page'opened in New Window",
									"'Financial Engine Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Financial Engine Page'opened in New Window ",
									"'Financial Engine Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
	           Web.getDriver().switchTo().defaultContent();
	           Web.clickOnElement(investment, "Button Refresh");
	           Web.waitForPageToLoad(Web.getDriver());
	           Common.waitForProgressBar();
	        
		 
		}catch (Exception e) {
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void DDTC_30107_FE_Guidance_DIM_Access_Online_Advice(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			userName=Stock.GetParameterValue("userName");
			//Step 1 to 5
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			ManageMyInvestment investment = new ManageMyInvestment(leftmenu);
			investment.get();
			
		
		
			//Step 6 
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			//Step 7
			investment.verifyWebElementDisplayed("Access Online Guidance");			
			investment.isTextFieldDisplayed("Online Guidance can help you get pointed in the right direction.");
			//Step 8
			Web.clickOnElement(investment,"Access Online Guidance");
			/*Web.waitForElement(investment, "Continue Button");
			investment.isTextFieldDisplayed("You are now accessing Advisory Services");
			Web.clickOnElement(investment, "Continue Button");*/
			Thread.sleep(10000);
			//Step 12
			
			String parentWindow = Web.getDriver().getWindowHandle();
			Set<String> handles =  Web.getDriver().getWindowHandles();
			   for(String windowHandle  : handles)
			       {
			       if(!windowHandle.equals(parentWindow)){
			    	   Web.getDriver().switchTo().window(windowHandle);
			    	   Web.waitForPageToLoad(Web.getDriver());
			    	   Thread.sleep(10000);
			    	  if(Web.getDriver().getTitle().toString().trim().equalsIgnoreCase("Financial Engines - Log in")){
			    	  
			    		  Reporter.logEvent(Status.PASS,
									"Verify 'Financial Engine Page'opened in New Window",
									"'Financial Engine Page' opened in New Window", true);
						}
						else{
							Reporter.logEvent(Status.FAIL,
									"Verify 'Financial Engine Page'opened in New Window ",
									"'Financial Engine Page' is not opened in New Window ", true);
						}
			     
			    }
			       }
			   Web.getDriver().close(); //closing child window
	           Web.getDriver().switchTo().window(parentWindow); //cntrl to parent window
	           Web.getDriver().switchTo().defaultContent();
	           Web.clickOnElement(investment, "Button Refresh");
	           Web.waitForPageToLoad(Web.getDriver());
	           Common.waitForProgressBar();
	        
		} 
		catch (Exception e) {
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
				
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
}
