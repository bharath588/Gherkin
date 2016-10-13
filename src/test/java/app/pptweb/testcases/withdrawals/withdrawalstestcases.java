package app.pptweb.testcases.withdrawals;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import appUtils.Common;
import core.framework.Globals;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import pageobjects.withdrawals.RequestWithdrawal;

public class withdrawalstestcases {

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
				.getName(), Globals.GC_MANUAL_TC_NAME);

	}

	private String printTestData() throws Exception {
		printTestData = "";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData = printTestData + entry.getKey() + "="
						+ entry.getValue() + "\n";
		}
		return printTestData;
	}


	@Test(dataProvider = "setData")
	public void Withdrawals_TC01_In_Service_Age59_And_Half_Payment_To_Self(
			int itr, Map<String, String> testdata) {

		try {
			boolean isLabelDisplayed = false;
			int enteredRothWithdrawalAmt=0;
			int enteredPreTaxWithdrawalAmt=0;			
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME + "_"
					+ Common.getSponser()+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);			
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			
			 myAccountPage.get();
			 if(Web.isWebElementDisplayed(myAccountPage,"PLAN NAME", true)) { 
			 myAccountPage.clickPlanNameByGAID("384037-01"); 
						  }
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			//Land on Request a Withdrawal Page
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			
			//Verify if Withdrawal Page is displayed
			isLabelDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
						"Request A Withdrawal", true);
				if (isLabelDisplayed) 
					Reporter.logEvent(Status.INFO,
							"Verify  if Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is displayed successfully", true);
				else
					Reporter.logEvent(Status.FAIL,
							"Verify Request A Withdrawal Page is Displayed",
							"Request A Withdrawal Page is NOT displayed successfully", true);
				
			//Verify is the user is current employee and select Age 59 1/2 withdrawal Type
				isLabelDisplayed = requestWithdrawal.selectWithdrawalType(Stock.GetParameterValue("withdrawalType")); 
				  if (isLabelDisplayed) {
				  Reporter.logEvent(Status.INFO,
				  "Verify if Age 59 1/2 WithDrawal Type is Selected",
				  "Age 59 1/2 WithDrawal Type is Selected", true); } else {
				  Reporter.logEvent(Status.FAIL,
				  "Verify if Age 59 1/2 WithDrawal Type is Selected",
				  "Age 59 1/2 WithDrawal Type is NOT Selected", true); }
				  
			//check if Age 59 1/2 section is displayed
			//requestWithdrawal.isTextFieldDisplayed("Total withdrawal amount");
				 
			//Select Roth and Pre-tax Money Type Resources and Enter Amount
			/*requestWithdrawal.enterAmountforMoneyTypeSource(Stock.GetParameterValue("withdrawalType"),
			Stock.GetParameterValue("rothMoneyType"), 
			Integer.toString(requestWithdrawal.getMaxAmountForMoneyTypeSource(Stock.GetParameterValue("withdrawalType"), Stock.GetParameterValue("rothMoneyType"))));
			
			enteredRothWithdrawalAmt=requestWithdrawal.getMaxAmountForMoneyTypeSource(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("rothMoneyType"));*/
			
            enteredPreTaxWithdrawalAmt=requestWithdrawal.getMaxAmountForMoneyTypeSource(Stock.GetParameterValue("withdrawalType"),Stock.GetParameterValue("pretaxMoneyType"));
			
			requestWithdrawal.enterAmountforMoneyTypeSource(Stock.GetParameterValue("withdrawalType"),
			Stock.GetParameterValue("pretaxMoneyType"),Integer.toString(enteredPreTaxWithdrawalAmt));
						
			//Click on Continue			
			if(Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE",true))
			{
				Web.clickOnElement(requestWithdrawal, "CONTINUE"); 
				Reporter.logEvent(Status.PASS, 
			"Select Age 59 1/2 option, and enter withdrawal amount for multiple / single  money type sources and click Continue",				
			"User enters the withdrawal amount for multiple money types source and clicked on continue button", false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Select Age 59 1/2 option, and enter withdrawal amount for both Roth and Pre-tax  money type sources and Click Continue",						
						"Continue button is not displayed in Request a Withdrawal Page", true);
					throw new Error("'Continue' is not displayed");
				}
			Thread.sleep(2000);
			
			//U S Citizenship and SSN Validation 
			if(requestWithdrawal.isTextFieldDisplayed("Plan withdrawal"))

			requestWithdrawal.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			Web.clickOnElement(requestWithdrawal, "YES");
			Thread.sleep(3000);
			if(requestWithdrawal.isTextFieldDisplayed("Please enter your Social Security number."))	
			{
				requestWithdrawal.enterSSN(Stock.GetParameterValue("SSN"));
				Reporter.logEvent(Status.PASS,
						"Verify if user enters Social Security number.",
						"User enters Social Security number "+Stock.GetParameterValue("SSN") +" in the field which is displayed", true);
			}
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if user enters Social Security number.",
						"User enters Social Security number "+Stock.GetParameterValue("SSN") +" in the field which is displayed", true);
			
			
			//Click on Confirm and Continue
			Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
			Thread.sleep(4000);
			
			//Verify Withdrawal Method Page is displayed
			if(requestWithdrawal.isTextFieldDisplayed("Withdrawal method"))			
					Reporter.logEvent(Status.PASS,
							"Verify Withdrawal Method Page is Displayed",
							"Withdrawal Method Page is Displayed", true);
			else
					Reporter.logEvent(Status.FAIL,
							"Verify Withdrawal Method Page is Displayed",
							"Withdrawal Method Page is NOT Displayed", true);
			
			requestWithdrawal.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			
			//Select Payment to Self Withdrawal Distribution
			if(Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD",
					Stock.GetParameterValue("withdrawalMethod"),true))			
				Reporter.logEvent(Status.PASS,
						"Verify if User selects PAYMENT TO SELF withdrawal distribution option",
						"User selects PAYMENT TO SELF withdrawal distribution option", true);
			else
			Reporter.logEvent(Status.FAIL,
					"Verify if User selects PAYMENT TO SELF withdrawal distribution option",
					"User did NOT select PAYMENT TO SELF withdrawal distribution option", true);
							
			Thread.sleep(1000);
			
			//Verify if Confirm your conatct information section is displayed
			requestWithdrawal.isTextFieldDisplayed("Confirm your contact information");
			
			//Enter participant email address and click on continue
			Web.setTextToTextBox("EMAIL ADDRESS",requestWithdrawal,Stock.GetParameterValue("emailAddress"));
			
			Web.clickOnElement(requestWithdrawal, "CONTINUE TO WITHDRAWAL");
			Thread.sleep(4000);
			
			//verify if Delivery Method page is displayed
			requestWithdrawal.isTextFieldDisplayed("Delivery method");
			
			//Select first - class mail delivery option and verify withdrawal summary
			requestWithdrawal.selectDeliveryMethod(Stock.GetParameterValue("deliveryMethod"));
			Thread.sleep(10000);
			requestWithdrawal.isTextFieldDisplayed("Withdrawal summary");
						
			/*if(enteredRothWithdrawalAmt ==
					requestWithdrawal.getFinalWithdrawalAmountForMoneyTypeSource(Stock.GetParameterValue("rothMoneyType")))
				Reporter.logEvent(Status.PASS, "Verify if Entered Roth Withdrawal Amount is displayed as same in the Final Withdrawal Amount",
						"The Entered roth amount is same as the final Withdrawal Roth amount "+enteredRothWithdrawalAmt, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Entered Roth Withdrawal Amount is displayed as same in the Final Withdrawal Amount",
						"The Entered roth amount is NOT same as the final Withdrawal Roth amount"+enteredRothWithdrawalAmt, false);
			*/
			
			int enteredTotalWithdrawalAmt = enteredPreTaxWithdrawalAmt+0;
			int finalWithdrwalAmount = requestWithdrawal
					.getFinalWithdrawalAmountForMoneyTypeSource("Total withdrawal amount");
			if(enteredTotalWithdrawalAmt == finalWithdrwalAmount)					
				Reporter.logEvent(Status.PASS, "Verify if Entered total Withdrawal Amount is displayed as same in the Final Withdrawal Amount displayed in the Withdrawal Summary Page",
						"The Entered roth amount is same as the final Withdrawal Roth amount "+
				"Expected Withrawal Amount: "+enteredTotalWithdrawalAmt+"\n"+
				"Actual Withdrawal Amount: "+finalWithdrwalAmount, false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Entered total Withdrawal Amount is displayed as same in the Final Withdrawal Amount displayed in the Withdrawal Summary Page",
						"The Entered roth amount is same as the final Withdrawal Roth amount "+
								"Expected Withrawal Amount: "+enteredTotalWithdrawalAmt+"\n"+
								"Actual Withdrawal Amount: "+finalWithdrwalAmount, true);	
			
			//click on Agree and Submit
			Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");
			Thread.sleep(7000);
			
			if(requestWithdrawal
					.isTextFieldDisplayed("Request submitted!"))			
				Reporter.logEvent(Status.INFO,
						"Verify Request Submission Page is Displayed",
						"Request Submission Page is Displayed", true);			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Request Submission Page is Displayed",
						"Request Submission is Not Displayed", true);
			
			if(Web.VerifyPartialText("Your confirmation number is",
					requestWithdrawal.getWebElementText("TEXT CONFIRMATION"),
					true))		
				Reporter.logEvent(Status.INFO,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Displayed with the Confirmation Number: "
						+requestWithdrawal.getWebElementText("TEXT CONFIRMATION"), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Displayed with the Confirmation Number "
						+requestWithdrawal.getWebElementText("TEXT CONFIRMATION"), false);

			
			//confirmation number DB Validation			
			if (Web.isWebElementDisplayed(requestWithdrawal,
                    "TEXT CONFIRMATION NUMBER", true)) {
             String txtConfirmationNo=requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER");
             String dbName=Common.getParticipantDBName(Stock.GetParameterValue("userName"));
       int isconfirmationNumDisplayed=DB.getRecordSetCount(DB.executeQuery(dbName+"DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"))
, Stock.getTestQuery("getWithDrawalConfirmationNo")[1], txtConfirmationNo,Stock.GetParameterValue("ind_ID")));
       
        if (isconfirmationNumDisplayed > 0) {
                    Reporter.logEvent(
                                  Status.PASS,
                                  "Verify Request Confirmation Number is Populated in the DataBase",
                                  "Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"
                                                + requestWithdrawal
                                                             .getWebElementText("TEXT CONFIRMATION NUMBER"),false);
            } else {
                    Reporter.logEvent(
                                  Status.FAIL,
                                  "Verify Request Confirmation Number is Populated in the DataBase",
                                  "Request Confirmation Number is NOT Populated in the DataBase",true);
             }
       } else {
             Reporter.logEvent(Status.FAIL,
                           "Verify Request Confirmation Number is Displayed",
                           "Request Confirmation Number is Not Displayed", true);
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
	public void withDrawal_SepService_PWD_Roll_IRA(int itr,
			Map<String, String> testdata) {
		boolean lblDisplayed = false;
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
			if(Web.isWebElementDisplayed(myAccountPage,"PLAN NAME", true)) { 
				 myAccountPage.clickPlanNameByGAID("385030-01"); 
							  }
			LeftNavigationBar lftNavBar = new LeftNavigationBar(myAccountPage);
			 
			RequestWithdrawal requestWithdrawal = new RequestWithdrawal(
					lftNavBar);
			requestWithdrawal.get();
			Thread.sleep(4000);
			// Click on request withdrawal
			lblDisplayed = Web.isWebElementDisplayed(requestWithdrawal,
					"Request A Withdrawal", true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.INFO,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is visible", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request A Withdrawal Page is Displayed",
						"Request A Withdrawal Page is NOT visible", true);
			}

			
			// Sect Part withdrawal
			Web.clickOnElement(requestWithdrawal, "VESTED PART WITHDRAWAL");
			// Enter Amount for Part Withdrawal
			requestWithdrawal.enterAmountforPartWthdrawalMoneyTypeSource(Stock
					.GetParameterValue("moneyTypeSourceRoth"), Integer
					.toString(requestWithdrawal
							.getMaxAmountForPWMoneyTypeSource(Stock.GetParameterValue("moneyTypeSourceRoth"))));
			int enteredAmount = requestWithdrawal
					.getMaxAmountForPWMoneyTypeSource(Stock
							.GetParameterValue("moneyTypeSourceRoth"));
			if (Web.isWebElementDisplayed(requestWithdrawal, "CONTINUE")) {
				Web.clickOnElement(requestWithdrawal, "CONTINUE");
			} else {
				throw new Error("'Continue' is not displayed");
			}

			Thread.sleep(2000);
			Web.waitForElement(requestWithdrawal, "YES");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Plan withdrawal");

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Citizenship Confirmation Page is Displayed",
						"Citizenship Confirmation Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Citizenship Confirmation Page is Displayed",
						"Citizenship Confirmation Page is Not Displayed", true);
			}

			requestWithdrawal
					.isTextFieldDisplayed("Are you a U.S. citizen or resident?");
			// Click on Yes
			lblDisplayed = Web.clickOnElement(requestWithdrawal, "YES");
			Thread.sleep(3000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Please enter your Social Security number.");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Social Security number Field is Displayed.",
						"Social Security number Field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Social Security number Field is Displayed",
						"Social Security number Field is Not Displayed", true);
			}
			// Enter SSN
			requestWithdrawal.enterSSN(Stock.GetParameterValue("SSN"));
			Web.clickOnElement(requestWithdrawal, "CONFIRM AND CONTINUE");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Withdrawal method");

			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Method Page is Displayed",
						"Withdrawal Method Page is Not Displayed", true);
			}
			requestWithdrawal
					.isTextFieldDisplayed("How would you like your withdrawal distributed?");
			// Select withdrawal method
			Web.selectDropDownOption(requestWithdrawal, "WITHDRAWAL METHOD",
					Stock.GetParameterValue("withdrawalMethod"));
			Web.waitForElement(requestWithdrawal, "ROLLOVER COMPANY");
			// Select RollOver Company
			requestWithdrawal.selectRollOverCompany(Stock
					.GetParameterValue("rollOverCompany"));
			requestWithdrawal.enterAddressDetailsForRollOverCompany(
					Stock.GetParameterValue("accountNo"),
					Stock.GetParameterValue("address1"),
					Stock.GetParameterValue("city"),
					Stock.GetParameterValue("stateCode"),
					Stock.GetParameterValue("zipCode"));
			Web.clickOnElement(requestWithdrawal, "CONTINUE TO WITHDRAWAL");
			Thread.sleep(4000);
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Delivery method");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Delivery Method Page is Displayed",
						"Delivery Method Page is Not Displayed", true);
			}
			// Select delivary method
			requestWithdrawal.selectDeliveryMethod(Stock
					.GetParameterValue("deliveryMethod"));
			Thread.sleep(10000);
			Web.waitForElement(requestWithdrawal, "I AGREE AND SUBMIT");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Withdrawal summary");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Summary is Displayed",
						"Withdrawal Summary is Not Displayed", true);
			}
			int withdrwalAmount = requestWithdrawal
					.getFinalWithdrawalAmountForMoneyTypeSource("Total withdrawal amount");
			if (enteredAmount == withdrwalAmount) {

				Reporter.logEvent(Status.PASS,
						"Verify Withdrawal Amount is Displayed",
						"Withdrawal Amount is Displayed and Macthing \nExpected:$"
								+ enteredAmount + "\nActual:$"
								+ withdrwalAmount, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Withdrawal Amount is Displayed",
						"Withdrawal Amount is Not Matching\nExpected:$"
								+ enteredAmount + "\nActual:$"
								+ withdrwalAmount, true);
			}
			Web.clickOnElement(requestWithdrawal, "I AGREE AND SUBMIT");
			Thread.sleep(8000);
			Web.waitForElement(requestWithdrawal,"TEXT CONFIRMATION NUMBER");
			lblDisplayed = requestWithdrawal
					.isTextFieldDisplayed("Request submitted!");
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Request Submission Page is Displayed",
						"Request Submission Page is Displayed", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Submission Page is Displayed",
						"Request Submission is Not Displayed", true);
			}
			/*lblDisplayed = Web.VerifyPartialText("Your confirmation number is",
					requestWithdrawal.getWebElementText("TEXT CONFIRMATION"),
					true);
			if (lblDisplayed) {
				Reporter.logEvent(Status.PASS,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Displayed\n ConfirmationNO:"+requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER").trim(), false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify Request Confirmation is Displayed",
						"Request Confirmation is Not Displayed", true);
			}
*/
			
			if (Web.isWebElementDisplayed(requestWithdrawal,
					"TEXT CONFIRMATION NUMBER", true)) {
				Reporter.logEvent(Status.INFO,
						"Verify Request Confirmation is Displayed on UI",
						"Request Confirmation is Displayed\n ConfirmationNO:"+requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER").trim(), false);
				
				String txtConfirmationNo=requestWithdrawal.getWebElementText("TEXT CONFIRMATION NUMBER").trim();
				
				String dbName=Common.getParticipantDBName(Stock.GetParameterValue("userName"));
				
				int isconfirmationDisplayed=DB.getRecordSetCount(DB.executeQuery(dbName+"DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getWithDrawalConfirmationNo")[1], txtConfirmationNo,Stock.GetParameterValue("ind_ID")));
			
					if (isconfirmationDisplayed>0) {
						Reporter.logEvent(
							Status.PASS,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation is Populated in the DataBase And \n Confirmation Number is:"
									+ requestWithdrawal
											.getWebElementText("TEXT CONFIRMATION NUMBER").trim(),
							false);
						} else {
							Reporter.logEvent(
							Status.FAIL,
							"Verify Request Confirmation Number is Populated in the DataBase",
							"Request Confirmation Number is NOT Populated in the DataBase",	true);
				}
			} else {
					Reporter.logEvent(Status.FAIL,
						"Verify Request Confirmation Number is Displayed",
						"Request Confirmation Number is Not Displayed", true);
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
}
