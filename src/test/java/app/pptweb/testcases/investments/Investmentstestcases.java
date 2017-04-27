package app.pptweb.testcases.investments;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
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
	public void DDTC_2672_DIM_Choose_individual_funds_or_DIM_Sanity(int itr, Map<String, String> testdata) {

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
						"How Would You Like To Invest Page Not is displayed ", true);
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
			
			Calendar cal = Calendar.getInstance();
			String date=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
					cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
					+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
					Integer.toString(cal.get(Calendar.YEAR))+","+" "+
					Integer.toString(cal.get(Calendar.HOUR))+":"+Integer.toString(cal.get(Calendar.MINUTE))+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			//Step 15
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			
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
		    
			// TODO
			
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
						"How Would You Like To Invest Page Not is displayed ", true);
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
			
			Calendar cal = Calendar.getInstance();
			String date=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
					cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
					+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
					Integer.toString(cal.get(Calendar.YEAR))+","+" "+
					Integer.toString(cal.get(Calendar.HOUR))+":"+Integer.toString(cal.get(Calendar.MINUTE))+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
			
			String expectedConfirmationMsg="Your investment allocation request for future contributions, has been received as of "+date+", and will be processed as soon as administratively feasible.";
			
			String actualConfirmationMsg=investment.getWebElementText("Text Confirmation");
			if(Web.VerifyText(expectedConfirmationMsg, actualConfirmationMsg, true)){
				
				Reporter.logEvent(Status.PASS,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is Displayed in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify Confirmation Message is Displayed in Confirmation Page",
						"Confirmation Message is not Matching in Confirmation Page\nExpected:"+expectedConfirmationMsg+"\nActual:"+actualConfirmationMsg, true);
			}
			//Step 15
			if(investment.VerifyInvestmentOptionOpenInNewWindow()){
				Reporter.logEvent(Status.PASS,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' opened in New Window ", true);
			}
			else{
				Reporter.logEvent(Status.FAIL,
						"Verify 'Investment Option' opened in New Window from Confirmation Page",
						"'Investment Option' is not opened in New Window ", true);
			}
			
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
		    
			// TODO
			
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
