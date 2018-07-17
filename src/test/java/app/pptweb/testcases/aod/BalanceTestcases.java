package app.pptweb.testcases.aod;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
public class BalanceTestcases {
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
	public void AOD_Balance_DDTC_27442_TC_01_Verify_New_Balance_card_on_Account_overview_page_for_single_plan_ppt_with_balance_more_than_Zero(
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
 			homePage.get();
 			String totalAccBalance=homePage.getTotalAccountBalance();
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateBalanceCard();
			myAccountOverview.validateBalanceCardParticipantAmount(totalAccBalance);
			myAccountOverview.validateBalanceCardViewDetailslink();
			myAccountOverview.clickOnBalanceCardViewDeatilsLink();
			Balance myBalance=new Balance();
			myBalance.verifyParticipanttakenToBalancePage();
						
						
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
	public void AOD_Balance_DDTC_27443_TC_02_Verify_New_Balance_card_on_Account_overview_page_for_multiple_plan_ppt_has_Balance_more_than_zero(
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
 			MyAccountsPage myAccount = new MyAccountsPage();
 			myAccount.get();
 			String sAmount = homePage.getAccountBalanceByPlan(Stock.GetParameterValue("ga_PlanId"));
 			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
 			Balance myBalance=new Balance();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateBalanceCard();
			myAccountOverview.validateBalanceAmountForPlan(sAmount);
			myAccountOverview.validateBalanceCardViewDetailslink();
			myAccountOverview.clickOnBalanceCardViewDeatilsLink();
			
			myBalance.verifyParticipanttakenToBalancePage();
			Web.clickOnElement(myBalance, "Home");
			
						
			Thread.sleep(5000);
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
	public void AOD_Balance_DDTC_28693_TC_03_Verify_New_Balance_card_on_Account_overview_page_for_single_plan_ppt_with_Zero_or_no_Balance(
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
 			homePage.get();
 			String totalAccBalance=homePage.getTotalAccountBalance();
 			LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
 			sLeftNav.get();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateBalanceCard();
			myAccountOverview.validateBalanceCardParticipantAmount(totalAccBalance);						
						
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
	public void AOD_Balance_DDTC_28694_TC_04_Verify_New_Balance_card_on_Account_overview_page_for_multiple_plan_ppt_when_one_of_plan_has_no_Balance(
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
 			MyAccountsPage myAccount = new MyAccountsPage();
 			myAccount.get();
 			String sAmount = homePage.getAccountBalanceByPlan(Stock.GetParameterValue("ga_PlanId"));
 			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
 			Balance myBalance=new Balance();
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateBalanceCard();
			myAccountOverview.validateBalanceAmountForPlan(sAmount);
			if(itr==1)
			{
				myAccountOverview.validateBalanceCardViewDetailslink();
				myAccountOverview.clickOnBalanceCardViewDeatilsLink();
				
				myBalance.verifyParticipanttakenToBalancePage();
				Web.clickOnElement(myBalance, "My Accounts");
			}
			Thread.sleep(5000);
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
    public void AOD_Balance_DDTC_29378_TC_05_Verify_New_Balance_card_on_Account_overview_page_for_participant_from_401A_plan(
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
                 homePage.get();
                  String sTotalAmt=homePage.getTotalAccountBalance();
                 LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
                 sLeftNav.get();
                  AccountOverview myAccountOverview=new AccountOverview();
                  myAccountOverview.validateBalanceCard();
                  myAccountOverview.validateBalanceCardParticipantAmount(sTotalAmt);
                  myAccountOverview.validateBalanceCardViewDetailslink();
                  myAccountOverview.clickOnBalanceCardViewDeatilsLink();
                  Balance myBalance=new Balance();
                  myBalance.verifyParticipanttakenToBalancePage();

                                      
                                      
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
    public void AOD_Balance_DDTC_29379_TC_06_Verify_New_Balance_card_on_Account_overview_page_for_participant_from_403b_plan(
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
                 homePage.get();
                  String sTotalAmt=homePage.getTotalAccountBalance();
                 LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
                 sLeftNav.get();
                  AccountOverview myAccountOverview=new AccountOverview();
                  myAccountOverview.validateBalanceCard();
                  myAccountOverview.validateBalanceCardParticipantAmount(sTotalAmt);
                 
                  myAccountOverview.validateBalanceCardViewDetailslink();
                  myAccountOverview.clickOnBalanceCardViewDeatilsLink();
                  Balance myBalance=new Balance();
                  myBalance.verifyParticipanttakenToBalancePage();
                  

                                      
                                      
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
    public void AOD_Balance_DDTC_29380TC_07_Verify_New_Balance_card_on_Account_overview_page_for_participant_from_457_special_catchup_plan(
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
                 homePage.get();
                  String sTotalAmt=homePage.getTotalAccountBalance();
                 LeftNavigationBar sLeftNav=new LeftNavigationBar(homePage);
                 sLeftNav.get();
                  AccountOverview myAccountOverview=new AccountOverview();
                  myAccountOverview.validateBalanceCard();
                  myAccountOverview.validateBalanceCardParticipantAmount(sTotalAmt);
                  
                  myAccountOverview.validateBalanceCardViewDetailslink();
                  myAccountOverview.clickOnBalanceCardViewDeatilsLink();
                  Balance myBalance=new Balance();
                  myBalance.verifyParticipanttakenToBalancePage();
                  

                                      
                                      
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
	public void AOD_Balance_DDTC_2938_TC_08_Verify_New_Balance_card_on_Account_overview_page_for_participant_from_NQ_plan(
			int itr, Map<String, String> testdata) {

		try {
			ArrayList<String> lstAmount = new ArrayList<String>();
			Reporter.initializeReportForTC(itr,
					Globals.GC_MANUAL_TC_NAME + "_" + Common.getSponser() + "_"
							+ Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,
					"Test Data used for this Test Case:", printTestData(),
					false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			homePage.get();
			lstAmount = homePage.getMultipleTotalAccountBalance();
			System.out.println("Size" + lstAmount.size());
			MyAccountsPage myAccount = new MyAccountsPage();
			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
			Balance myBalance = new Balance();
			AccountOverview myAccountOverview = new AccountOverview();
			myAccountOverview.validateBalanceCard();
			myAccountOverview.validateBalanceMultiplePlan(lstAmount, 1);
			if (!(lstAmount.get(1).equals("$0.00"))) {
				myAccountOverview.validateBalanceCardViewDetailslink();
				myAccountOverview.clickOnBalanceCardViewDeatilsLink();
				myBalance.verifyParticipanttakenToBalancePage();
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Verify \"View details\" (only when balance is greater than 0) link when clicked will take the participant to the existing balance page.",
						"Since Balance Amount is not greater than 0 (i.e Balance Amount is "
								+ lstAmount.get(1)
								+ " )no need to verify \"View Details\" link ",
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
	public void AOD_Balance_DDTC_29383_TC_09_Verify_New_Balance_card_not_display_on_Account_overview_page_for_participant_when_DB_plan_selected(
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
			myAccountPage.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateAccOverviewPageforDBPlan("Defined Benefit Test Plan",myAccountOverview);												
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
	public void validate_BalanceCard_NotDisplayed(
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
 			MyAccountsPage myAccount = new MyAccountsPage(homePage);
 			myAccount.get();
 			myAccount.clickPlanNameByGAID(Stock.GetParameterValue("ga_PlanId"));
 			AccountOverview myAccountOverview=new AccountOverview();
			myAccountOverview.validateBalanceCardNotDisplayed();
						
			
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
	public void AOD_Balance_DDTC_29384_TC_10_Verify_New_Balance_card_not_display_on_Account_overview_page_when_TRS_FLEX_Annuity_plan_is_selected(
			int itr, Map<String, String> testdata) throws Exception {
    	validate_BalanceCard_NotDisplayed(itr, testdata);
	}
	@Test(dataProvider = "setData")
	public void AOD_Balance_DDTC_29385_TC_11_Verify_New_Balance_card_not_display_on_Account_overview_when_TRS_cash_balance_plan_selected(
			int itr, Map<String, String> testdata) throws Exception {
		validate_BalanceCard_NotDisplayed(itr, testdata);
	}
  }
