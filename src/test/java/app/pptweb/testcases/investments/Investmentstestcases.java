package app.pptweb.testcases.investments;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.general.GuidancePage;
import pageobjects.general.LeftNavigationBar;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;

public class Investmentstestcases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public Map<String, String> mapInvestmentOptionsReviewPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsConfirmPage = new HashMap<String, String>();
	LoginPage login;
	String tcName;
	static String printTestData="";
	static String userName=null;

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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			//investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
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
			}
			*/
			//Step 14
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			String date=getInvestmentsSubmissionTime();
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			//actualConfirmationMsg.substring(beginIndex, endIndex)
			
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}

		    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag is displayed on DIM button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag is not displayed on DIM button", true);
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
			
		    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag is displayed on DIM button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag  is not displayed on DIM button", true);
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 8
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
			
		    
			String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
			//Step 17
			leftmenu.clickNavigationLink("View/Manage my investments");
			investment.clickChangeMyInvestmentButton();
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if(Web.isWebElementDisplayed(investment, "Current Flag", true)){
				Reporter.logEvent(Status.PASS,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag is displayed on DIM button", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Current' Flag displayed on DIM button",
						"'Current' Flag  is not displayed on DIM button", true);
			}
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 9
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
		
			//Step 10
			String targetYearFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(targetYearFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+targetYearFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+targetYearFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(targetYearFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 9
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
		
			//Step 10
			String targetYearFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(targetYearFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+targetYearFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+targetYearFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(targetYearFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			investment.verifyWebElementDisplayed("Do It Myself");
			investment.verifyWebElementDisplayed("Help Me Do It");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
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
			investment.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investment, "Continue Button");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 9
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			//Step 11
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
				    
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 33
		
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
			}
			
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			//Step 35
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
				    
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
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
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
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
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
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(selectedRiskBasedFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+selectedRiskBasedFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+selectedRiskBasedFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
			
			String ExpectedAllertMsg="Because your plan has multiple contribution sources, "
					+ "we are unable to display your current allocation.  "
					+ "Changes to your investment allocation will apply to all your contribution sources. "
					+ "To make changes based on contribution source, go to My Accounts.";
			
			String actualAllertMsg=investment.getWebElementText("Risk Based Fund Allert Message");
			
			if(ExpectedAllertMsg.contentEquals(actualAllertMsg)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Static Message is Displayed in Select Risk Based Fund Page",
						"Static Message is Displayed in Select Risk Based Fund Page\nStatic Message is:"+actualAllertMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Static Message is Displayed in Select Risk Based Fund Page",
						"Static Message is not Displayed/Matching in Select Risk Based Fund Page\nExpected Message:"+ExpectedAllertMsg+"\nActual Message:"+actualAllertMsg, true);
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
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(selectedRiskBasedFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+selectedRiskBasedFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+selectedRiskBasedFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			
			String ExpectedAllertMsg="Because your plan has multiple contribution sources, "
					+ "we are unable to display your current allocation.  "
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
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(selectedTargetDateFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+selectedTargetDateFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+selectedTargetDateFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			
		
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
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			if(mapInvestmentOptionsReviewPage.size()==1){
				if(mapInvestmentOptionsReviewPage.containsValue(selectedTargetDateFund)){
				Reporter.logEvent(Status.PASS,
						"Verify Participant is allowed to select only one fund",
						"Participant is able to select only one fund \n Selected Fund:"+selectedTargetDateFund, false);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Fund is Matching in Review Page ",
						"Funds are not matching in Review Page\nExpected:"+selectedTargetDateFund+"", true);
			}
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Participant is allowed to select only one fund",
						"Participant is not allowed to select only one fund \n No.of Funds Selected:"+mapInvestmentOptionsReviewPage.size(), true);
			}
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
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
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
			investment.verifyWebElementDisplayed("Choose Risk Based Funds");
			investment.verifyWebElementDisplayed("Do It For Me");
			
			//Step 7
			
			Web.clickOnElement(investment,"Choose Individual Funds");
			Web.waitForElement(investment, "Header Build Your Own Portfolio");
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
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
			String[] percentage={"50","50"};
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
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
				if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
					Reporter.logEvent(Status.PASS,
							"Verify Confirmation Page is Displayed",
							"Confirmation Page is Displayed", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify Confirmation Page is Displayed",
							"Confirmation Page is Not Displayed", true);
				}
				
				String date=getInvestmentsSubmissionTime();
				String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
				
				String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
				if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
					
					Reporter.logEvent(Status.PASS,
							"Verify Confirmation Message is Displayed in Confirmation Page",
							"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
				}
				else{
					Reporter.logEvent(Status.INFO,
							"Verify Confirmation Message is Displayed in Confirmation Page",
							"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
				}
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
				if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
					Reporter.logEvent(Status.PASS,
							"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
							"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
				else 
					{
					Reporter.logEvent(Status.FAIL,
							"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
							"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
					}

			    
				String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
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
			if (Web.isWebElementDisplayed(investment,"Header Build Your Own Portfolio")) {
				Reporter.logEvent(Status.PASS,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Build Your Own Portfolio Page is displayed",
						"Build Your Own Portfolio Page is not displayed ", true);
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
			String[] percentage={"50","50"};
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
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
				if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
					Reporter.logEvent(Status.PASS,
							"Verify Confirmation Page is Displayed",
							"Confirmation Page is Displayed", true);
				}
				else{
					Reporter.logEvent(Status.FAIL,
							"Verify Confirmation Page is Displayed",
							"Confirmation Page is Not Displayed", true);
				}
				
				String date=getInvestmentsSubmissionTime();
				String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
				
				String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
				if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
					
					Reporter.logEvent(Status.PASS,
							"Verify Confirmation Message is Displayed in Confirmation Page",
							"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
				}
				else{
					Reporter.logEvent(Status.INFO,
							"Verify Confirmation Message is Displayed in Confirmation Page",
							"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
				}
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
				if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
					Reporter.logEvent(Status.PASS,
							"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
							"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
				else 
					{
					Reporter.logEvent(Status.FAIL,
							"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
							"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
					}

			    
				String confirmationNumber=investment.getConfirmationNoChangeFutureFlow();
			
			
			
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 9
		
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 10
			investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
		
			//Step 11
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
				    
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 33
		
			Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 34
			investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		
		
			//Step 35
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			String date=getInvestmentsSubmissionTime();
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.INFO,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
				    
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
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22712_Rebal_Sync_Allocation_DIM_Choose_Individual_Funds_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
			investment.VerifyAllocatedPecentageForFunds();
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
	public void DDTC_22712_Rebal_Sync_Allocation_DIM_Choose_Individual_Funds_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(1,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(5,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
			String confirmationNumber=investment.getRebalanceConfirmationNO();
			
			investment.VerifyAllocatedPecentageForFunds();
			
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
				/*ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);*/
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void DDTC_22707_Rebal_Only_HMDI_Target_Date_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			/*if(Web.isWebElementDisplayed(investment, "Expand Sources", true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is displayed ", true);
				Web.clickOnElement(investment, "Expand Sources");
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Expand Sources Link is displayed",
						"Expand Sources Link is Not displayed ", true);
			}*/
		
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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
			Reporter.logEvent(Status.PASS,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is Not displayed ", true);
		}
		//Step 10
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 11
			
		investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header Confirmation");
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
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
	public void DDTC_22707_Rebal_Only_HMDI_Target_Date_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
				/*ManageMyInvestment investment= new ManageMyInvestment();
				investment.deleteRebalancePendingTransaction(userName);*/
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider = "setData")
	public void DDTC_22708_Rebal_And_Sync_Allocation_HMDI_Target_Date_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
			Reporter.logEvent(Status.PASS,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is Not displayed ", true);
		}
		//Step 10
		 Web.clickOnElement(investment,"Choose Target Date Fund");
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 11
			
		investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Target Date Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Target Date Fund Page is displayed",
						"Select Target Date Fund Page is not displayed ", true);
			}
			
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
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
	public void DDTC_22708_Rebal_And_Sync_Allocation_HMDI_Target_Date_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedTargetDateFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedTargetDateFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
	public void DDTC_22711_Rebal_Only_Allocation_DIM_Choose_Individual_Funds_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(2,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
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
	public void DDTC_22711_Rebal_Only_Allocation_DIM_Choose_Individual_Funds_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(1,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
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
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
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
			investment.addInvestments(5,percentage);
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			mapInvestmentOptionsReviewPage=investment.getCurrentFunds();
			
			
			//Step 10
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Thread.sleep(15000);
			Web.waitForElement(investment, "Header Confirmation");
			
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			
			mapInvestmentOptionsConfirmPage=investment.getCurrentFunds();
			if(mapInvestmentOptionsReviewPage.equals(mapInvestmentOptionsConfirmPage)){
				Reporter.logEvent(Status.PASS,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
			}
			else 
				{
				Reporter.logEvent(Status.FAIL,
						"Verify Selected Investment Options are in same order in Review your changes Page and Confirmation Page",
						"Investment Options are in same order in Review your changes Page and Confirmation Page", true);
				}
			
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
	public void DDTC_22709_Rebal_Only_HMDI_RiskBased_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
			Reporter.logEvent(Status.PASS,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is Not displayed ", true);
		}
		//Step 10
		Web.clickOnElement(investment,"Choose Risk Based Funds");
		Web.waitForElement(investment, "Header Select Risk Based Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Risk Based Fund Page is displayed",
					"Select Risk Based Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Risk Based Fund Page is displayed",
					"Select Risk Based Fund Page is not displayed ", true);
		}
			
			//Step 11
			
		investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header Confirmation");
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
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
	public void DDTC_22709_Rebal_Only_HMDI_RiskBased_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	

			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
		
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
	public void DDTC_22710_Rebal_Sync_HMDI_RiskBased_Offer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
		 Web.clickOnElement(investment,"Choose Target Date Fund");
		 Common.waitForProgressBar();
		Web.waitForElement(investment, "Header Select Target Date Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Target Date Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Target Date Fund Page is displayed",
					"Select Target Date Fund Page is not displayed ", true);
		}
		
		//Step 9
		
		if(Web.clickOnElement(investment, "Back Link"))
			Reporter.logEvent(Status.INFO,
					"Clicking on BACK Link ",
					"Clicked on Back Link in Select Target Date Fund Page", false);
		 Common.waitForProgressBar();
		
		Web.waitForElement(investment, "Header How Would You Like To Invest");
		
		if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
			Reporter.logEvent(Status.PASS,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify How Would You Like To Invest Page is displayed",
					"How Would You Like To Invest Page is Not displayed ", true);
		}
		//Step 10
		Web.clickOnElement(investment,"Choose Risk Based Funds");
		Web.waitForElement(investment, "Header Select Risk Based Fund");
		if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
			Reporter.logEvent(Status.PASS,
					"Verify Select Risk Based Fund Page is displayed",
					"Select Risk Based Fund Page is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Select Risk Based Fund Page is displayed",
					"Select Risk Based Fund Page is not displayed ", true);
		}
			
			//Step 11
			
		investment.selectTargetYearFund();
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
		//Step 12	
			if(Web.clickOnElement(investment, "Back Link"))
				Reporter.logEvent(Status.INFO,
						"Clicking on BACK Link ",
						"Clicked on Back Link in Review Your Changes Page", false);	
			
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes",true)) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investment, "Header Confirmation");
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
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
	public void DDTC_22710_Rebal_Sync_HMDI_RiskBased_DoesNotOffer_All_Mntype(int itr, Map<String, String> testdata) {

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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	
			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
			
			
			/*if(investment.verifyMoneyTypeGroupIsDisplayed(Stock.GetParameterValue("moneyTypeGrouping"))){
				Web.clickOnElement(investment, "Continue Button");
			}*/
			
			Web.waitForElement(investment, "Header How Would You Like To Invest");
			if (Web.isWebElementDisplayed(investment,"Header How Would You Like To Invest")) {
				Reporter.logEvent(Status.PASS,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify How Would You Like To Invest Page is displayed",
						"How Would You Like To Invest Page is Not displayed ", true);
			}
			
			//Step 8
	

			Web.clickOnElement(investment,"Choose Risk Based Funds");
			Web.waitForElement(investment, "Header Select Risk Based Fund");
			if (Web.isWebElementDisplayed(investment,"Header Select Risk Based Fund")) {
				Reporter.logEvent(Status.PASS,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Select Risk Based Fund Page is displayed",
						"Select Risk Based Fund Page is not displayed ", true);
			}
		
		
		 //Step 9 to 12 are duplicate
	
			//Step 13
			String selectedRiskBasedFund=investment.selectTargetYearFund();
			
			if (Web.isWebElementDisplayed(investment,"Header Review Your Changes")) {
				Reporter.logEvent(Status.PASS,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is displayed ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Review Your Changes Page is displayed",
						"Review Your Changes Page is not displayed ", true);
			}
			
			//Step 14
			
			Web.clickOnElement(investment, "Button Confirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(Web.isWebElementDisplayed(investment, "Header Confirmation", true)){
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Displayed", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Page is Displayed",
						"Confirmation Page is Not Displayed", true);
			}
			
			investment.verifyRebalanceInvestmentConfirmationDetails(Stock.GetParameterValue("RebalFrequency").toLowerCase());
			if(investment.getAllocatedPecentageForFund(selectedRiskBasedFund).contains("100")){
	  			Reporter.logEvent(Status.PASS,
	  					"Verify Investment Percentage is Equals to 100",
	  					"Investment Percentage is Matching and Equals to 100", false);

	  		}
	  		else 
	  			{
	  			Reporter.logEvent(Status.FAIL,
	  							"Verify Investment Percentage is Equals to 100",
	  				"Investment Percentage is not Matching", true);
	  			}
			
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
}
