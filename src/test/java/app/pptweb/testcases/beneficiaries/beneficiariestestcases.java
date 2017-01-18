package app.pptweb.testcases.beneficiaries;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;

import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.enrollment.Enrollment;
import pageobjects.general.LeftNavigationBar;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import appUtils.TestDataFromDB;
import core.framework.Globals;

public class beneficiariestestcases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private static HashMap<String, String> testDataFromDB = null;
	LoginPage login;
	String tcName;
	public static String participant_SSN = null;
	public static String first_Name = null;
	static String printTestData="";
	
	@BeforeClass
    public void ReportInit(){               
		Reporter.initializeModule(this.getClass().getName());
    }


    @DataProvider
    public Object[][] setData(Method tc) throws Exception {
        prepTestData(tc);
        return Stock.setDataProvider(this.testData);
    }

    private void prepTestData(Method testCase) throws Exception {
        this.testData = Stock.getTestData(this.getClass().getPackage().getName(), testCase.getName());

    }
    
    public void prepareBeneficiaryTestData(String quesryNmae,String... queryParam) {
		try {
			testDataFromDB = TestDataFromDB.getParticipantDetails(
					quesryNmae, queryParam);
			TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
	public void Beneficiary_TC001_Married_with_Spouse_One_beneficiary_new_address_Sanity(int itr, Map<String, String> testdata){
	
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
			//			leftmenu = new LeftNavigationBar(homePage);			
			//		else {
			//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			//			leftmenu = new LeftNavigationBar(accountPage);
			//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();
			participant_SSN = Stock.GetParameterValue("SSN");
			first_Name=Stock.GetParameterValue("FIRST_NAME");
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);

			//			// add a beneficiary
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"), Stock.GetParameterValue("Allocation"));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//verify beneficiary name
			Common.waitForProgressBar();
            Web.waitForPageToLoad(Web.getDriver());
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name not matching", true);

			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation not matching", true);

			//verify beneficiary Relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship not matching", true);

			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN not matching", true);

			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB not matching", true);

			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address not matching", true);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
			//throw ae;
		}
		finally {
			//delete beneficiary from Database
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			try {
				beneficiary.deleteBeneficiariesFromDB(participant_SSN, first_Name+"%");
				
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void UnMarried_Multiple_Individual_beneficiary(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		

		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);


			//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
			//			leftmenu = new LeftNavigationBar(homePage);			
			//		else {
			//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			//			leftmenu = new LeftNavigationBar(accountPage);
			//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();


			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);

			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			//verify beneficiary name
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name not matching", true);

			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation not matching", true);

			//verify beneficiary relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship not matching", true);

			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN not matching", true);

			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB not matching", true);

			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address not matching", true);
			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
			//throw ae;
		}
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(participant_SSN, first_Name+"%");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

	@Test(dataProvider = "setData")
	public void UnMarried_Multiple_Entity_beneficiary(int itr, Map<String, String> testdata){
	
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);


			//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
			//			leftmenu = new LeftNavigationBar(homePage);			
			//		else {
			//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
			//			leftmenu = new LeftNavigationBar(accountPage);
			//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

			beneficiary.get();


			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);

			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			//verify beneficiary name
			if(beneficiary.verifyEntityDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify Trust name", "beneficiary Trust is matching", true);
			else
				Reporter.logEvent(Status.FAIL, "verify Trust name", "beneficiary Trust not matching", true);

			//verify beneficiary allocation percentage
			if(beneficiary.verifyEntityDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify Trust Allocation", "beneficiary Trust is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Trust Allocation", "beneficiary Trust not matching", true);

			//verify beneficiary relationship
			if(beneficiary.verifyEntityDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify Entity Relationship", "Entity Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Entity Relationship", "Entity Relationship not matching", true);

			//verify beneficiary ssn
			if(beneficiary.verifyEntityDetails("TIN"))
				Reporter.logEvent(Status.PASS, "verify TIN", "beneficiary TIN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify TIN", "beneficiary TIN not matching", true);

			//verify beneficiary DOB
			if(beneficiary.verifyEntityDetails("DOT"))
				Reporter.logEvent(Status.PASS, "verify Date of trust", " Date of trust is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify  Date of trust", " Date of trust not matching", true);



			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");


		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
			//throw ae;
		}
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	@Test(dataProvider = "setData")
	public void Date_of_Birth_Error_Messages(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
						
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
			String error_msg="";
			if(lib.Web.isWebElementDisplayed(beneficiary, "DOB Error Msg")){
				error_msg=beneficiary.readErrorMessage("DOB Error Msg");
				if(lib.Web.VerifyText("Date of birth must be after 1/1/1850.", error_msg, true))
					Reporter.logEvent(Status.PASS, "verify DoB error message is after 01/01/1850", "Error messgae displayed : "+error_msg, false);
				else
					Reporter.logEvent(Status.FAIL, "verify DoB error message is after 01/01/1850", "Error messgae displayed : "+error_msg,true);
			}
			else
				Reporter.logEvent(Status.FAIL, "verify DoB error message is displayed", "Error messgae displayed not displayed",true);
			
//			

		}
			catch(Exception e)
	        {
	            e.printStackTrace();
	            Globals.exception = e;
	            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
	        }
			catch(Error ae)
	        {
	                        ae.printStackTrace();
	                        Globals.error = ae;
	                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
	                        //throw ae;
	        }
			finally {
				MyBeneficiaries beneficiay=new MyBeneficiaries();
				beneficiay.refresh();
	        }
			try {
	            Reporter.finalizeTCReport();
			} catch (Exception e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
			}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC005_Married_no_Spouse_Error_message(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
		
						
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			lib.Web.clickOnElement(beneficiary, "Married");
			lib.Web.selectDropDownOption(beneficiary,"Beneficiary Relation","Child");
			if(Web.isWebElementDisplayed(beneficiary,"Alert Msg")){
				String alert_msg= beneficiary.readErrorMessage("Alert Msg");
				if(lib.Web.VerifyText(Stock.GetParameterValue("Alert_message"),alert_msg,true))
					Reporter.logEvent(Status.PASS, "verify No Spouse Alert Message", "No Spouse Alert Message is matching. Alert messgae : "+alert_msg, false);
				else
					Reporter.logEvent(Status.FAIL, "verify No Spouse Alert Message", "No Spouse Alert Message is not matching. Alert message : "+alert_msg, true);
			}
			else
				Reporter.logEvent(Status.FAIL, "verify No Spouse Alert Message", "No Spouse Alert Message is not displayed", true);
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiay=new MyBeneficiaries();
			beneficiay.refresh();
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC013_UnMarried_Trust_beneficiary(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
			
			lib.Web.isWebElementDisplayed(beneficiary, "AddAnotherBeneficiary");
			
			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);
			
			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			//verify beneficiary name
			if(beneficiary.verifyEntityDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify Trust name", "beneficiary Trust is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Trust name", "beneficiary Trust not matching", true);
			
			//verify beneficiary allocation percentage
			if(beneficiary.verifyEntityDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify Trust Allocation", "beneficiary Trust is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Trust Allocation", "beneficiary Trust not matching", true);
			
			//verify beneficiary relationship
			if(beneficiary.verifyEntityDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify Entity Relationship", "Entity Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Entity Relationship", "Entity Relationship not matching", true);
			
			//verify beneficiary ssn
			if(beneficiary.verifyEntityDetails("TIN"))
				Reporter.logEvent(Status.PASS, "verify TIN", "beneficiary TIN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify TIN", "beneficiary TIN not matching", true);
			
			//verify beneficiary DOB
			if(beneficiary.verifyEntityDetails("DOT"))
				Reporter.logEvent(Status.PASS, "verify Date of trust", " Date of trust is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify  Date of trust", " Date of trust not matching", true);
			
		
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
					beneficiary.refresh();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
			
			
	}
	
	@Test(dataProvider = "setData")
	public void UnMarried_Trust_warning_message(int itr, Map<String, String> testdata){
//		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			
			lib.Web.clickOnElement(beneficiary, "Unmarried");
			lib.Web.selectDropDownOption(beneficiary,"Beneficiary Relation","A Trust");
			String alert_msg= beneficiary.readErrorMessage("Alert Msg");
			if(lib.Web.VerifyText(Stock.GetParameterValue("Alert_message"),alert_msg,true))
				Reporter.logEvent(Status.PASS, "verify Trust warning Message", "Trust Warning Message is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "verify Trust warning Message", "Trust Warning Message is not displayed", true);
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	

	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC016_QJSA_with_Beneficiary(int itr, Map<String, String> testdata) throws Exception{
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
					
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			if(Stock.GetParameterValue("BeneficiaryOnFile").equalsIgnoreCase("Yes")){
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
				Web.clickOnElement(beneficiary, "ContinueAndConfirm");
				Web.clickOnElement(beneficiary, "View Beneficiary Button");
			}
			String[] sqlQuery = Stock.getTestQuery("updateQJSA");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("gc_id"));	
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			if(lib.Web.isWebElementDisplayed(beneficiary,"Alert Msg",true )){
				String alert_msg= beneficiary.readErrorMessage("Alert Msg");
				if(lib.Web.VerifyText(Stock.GetParameterValue("Alert_message"),alert_msg,true))
					Reporter.logEvent(Status.PASS, "verify Error message displayed for QJSA account is matching", "Error Message is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Error message displayed for QJSA account is matching", "Error Message is not matching", true);
			}
			else	
				Reporter.logEvent(Status.FAIL, "verify Error message displayed ", "Error Message is not displayed", true);
			
			if(Stock.GetParameterValue("Beneficiary_added").equalsIgnoreCase("Yes")){
				if(beneficiary.ifElementDisabled("AddAnotherBeneficiary"))
					Reporter.logEvent(Status.PASS, "verify Add Another Beneficiary button is disabled", "Add Another Beneficiary button is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Add Another Beneficiary button is disabled", "Add Another Beneficiary button is not disabled", true);
				beneficiary.clickOnBeneficiaryFromTable(null, "Primary");
				
				if(beneficiary.ifElementDisabled("First name"))
					Reporter.logEvent(Status.PASS, "verify First name text box is disabled", "First name text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify First name text box is disabled", "First name text box not disabled", true);
				if(beneficiary.ifElementDisabled("Middle name"))
					Reporter.logEvent(Status.PASS, "verify Middle name text box is disabled", "Middle name text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Middle name text box is disabled", "Middle name text box not disabled", true);
				if(beneficiary.ifElementDisabled("Last name"))
					Reporter.logEvent(Status.PASS, "verify Last name text box is disabled", "Last name text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Last name text box is disabled", "Last name text box not disabled", true);
				if(beneficiary.ifElementDisabled("DOB"))
					Reporter.logEvent(Status.PASS, "verify DOB text box is disabled", "DOB text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify DOB text box is disabled", "DOB text box not disabled", true);
				if(beneficiary.ifElementDisabled("Suffix"))
					Reporter.logEvent(Status.PASS, "verify Suffix text box is disabled", "Suffix text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Suffix text box is disabled", "Suffix text box not disabled", true);
				if(beneficiary.ifElementDisabled("SSN"))
					Reporter.logEvent(Status.PASS, "verify SSN text box is disabled", "SSN text box is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify SSN text box is disabled", "SSN text box not disabled", true);
				
				lib.Web.clickOnElement(beneficiary, "Cancel button");
				if(lib.Web.isWebElementDisplayed(beneficiary, "MyBeneficiaries"))
					Reporter.logEvent(Status.PASS, "Verify if My Beneficiaries page is displayed", "My Beneficiaries page is displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if My Beneficiaries page is displayed", "My Beneficiaries page is not displayed", true);
			
			}
			
			
		
			if(Stock.GetParameterValue("Beneficiary_added").equalsIgnoreCase("No")){
			
				if(beneficiary.ifElementDisabled("Save button"))
					Reporter.logEvent(Status.PASS, "verify Save button is disabled", "Save button is disabled", false);
				else
					Reporter.logEvent(Status.FAIL, "verify Save button is disabled", "Save button is not disabled", true);
				
				
				if(Web.clickOnElement(beneficiary, "Cancel button"))
					Reporter.logEvent(Status.PASS, "Verify if Cancel button is clicked", "Cancel button is clicked", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if Cancel button is clicked", "Cancel button is not clicked", true);
				
				if(lib.Web.isWebElementDisplayed(beneficiary, "Account Overview"))
					Reporter.logEvent(Status.PASS, "Verify if Account overview page is displayed", "Account overview page is displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if Account overview page is displayed", "Account overview page is not displayed", true);
			
			}
			
		
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			String[] sqlQuery = Stock.getTestQuery("resetQJSA");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("gc_id"));
			beneficiary.refresh();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
					beneficiary.refresh();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC006_Married_no_allocations_for_beneficiaries_error_message(int itr, Map<String, String> testdata) throws Exception{
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
								
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			beneficiary.get();
			
			if(Stock.GetParameterValue("addBeneficiary").equalsIgnoreCase("Yes")){
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
				Web.clickOnElement(beneficiary, "ContinueAndConfirm");
				Web.clickOnElement(beneficiary, "View Beneficiary Button");
			}
			String[] sqlQuery = Stock.getTestQuery("updateAllocationsToNull");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1],participant_SSN);	
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			if(lib.Web.isWebElementDisplayed(beneficiary,"Alert Msg" )){
				String alert_msg= beneficiary.readErrorMessage("Alert Msg");
				if(lib.Web.VerifyText(Stock.GetParameterValue("Alert_message"),alert_msg,true))
					Reporter.logEvent(Status.PASS, "verify Error message displayed for married no allocations beneficiary is matching", "Error Message is matching", true);
				else
					Reporter.logEvent(Status.FAIL, "verify Error message displayed for married no allocations beneficiary is matching", "Error Message is matching", true);
			}
			else	
				Reporter.logEvent(Status.FAIL, "verify Error message displayed ", "Error Message is not displayed", true);
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			String[] sqlQuery = Stock.getTestQuery("updateAllocationsTo100");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], participant_SSN);
			beneficiary.refresh();
			if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
				try {
					beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
					beneficiary.refresh();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC020_Participants_with_auth_code_I(int itr, Map<String, String> testdata) throws Exception{
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			
			String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToI");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1],participant_SSN);	
			Web.getDriver().navigate().refresh();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			
			// add a beneficiary
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
			Web.waitForElement(beneficiary, "ContinueAndConfirm");
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			
			if(lib.Web.isWebElementDisplayed(beneficiary, "Generic Error Msg"))
				Reporter.logEvent(Status.PASS, "Verify if Error page is displayed", "Error page is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Error page is displayed", "Error page not displayed", true);
			
			String error_msg="";
			error_msg=beneficiary.readErrorMessage("Generic Error Msg");
			if(lib.Web.VerifyText(Stock.GetParameterValue("Error_msg"), error_msg, true))
				Reporter.logEvent(Status.PASS, "Verify if Error message is matching", "Error message is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Error message is matching", "Error message not matching", true);
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			String[] sqlQuery = Stock.getTestQuery("updatePinAuthCodeToU");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], participant_SSN);
			beneficiary.refresh();
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC017_Ability_to_remove_a_beneficiary(int itr, Map<String, String> testdata) throws Exception{
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
				Web.clickOnElement(beneficiary, "ContinueAndConfirm");
				Web.clickOnElement(beneficiary, "View Beneficiary Button");
				
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), "Child", Stock.GetParameterValue("Use Current Address"), "Contingent",Stock.GetParameterValue("Allocation"));
				Web.clickOnElement(beneficiary, "ContinueAndConfirm");
				Web.clickOnElement(beneficiary, "View Beneficiary Button");
			
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.clickOnBeneficiaryFromTable(null, "Contingent");
			if(lib.Web.isWebElementDisplayed(beneficiary, "Delete Checkbox",true))
				Reporter.logEvent(Status.PASS, "Verify if Delete Check box is present", "Delete Check box is present", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Delete Check box is present", "Delete Check box is not present", true);
			lib.Web.clickOnElement(beneficiary, "Delete Checkbox");
			if(lib.Web.isWebElementDisplayed(beneficiary, "Delete button",true))
				Reporter.logEvent(Status.PASS, "Verify if Delete button is present", "Delete button is present", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if Delete button is present", "Delete button is not present", true);
			lib.Web.clickOnElement(beneficiary, "Delete button");
			if(lib.Web.isWebElementDisplayed(beneficiary, "My Beneficiaries"))
				Reporter.logEvent(Status.PASS, "Verify if My Beneficiaries page is displayed", "My Beneficiaries page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify if My Beneficiaries page is displayed", "My Beneficiaries page is not displayed", true);
			
			if(beneficiary.clickOnBeneficiaryFromTable(null, "Primary"))
				Reporter.logEvent(Status.PASS, "Verify if a Primary beneficiary is selected", "Primary Beneficiary is selected", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify if a Primary beneficiary is selected", "Primary beneficiary not selected", true);
			
			lib.Web.clickOnElement(beneficiary, "Delete Checkbox");
			lib.Web.clickOnElement(beneficiary, "Delete button");
			if(lib.Web.clickOnElement(beneficiary, "Submit button"))
				Reporter.logEvent(Status.PASS, "Delete primary beneficiary", "Primary Beneficiary is deleted", false);
			else
				Reporter.logEvent(Status.FAIL, "Delete primary beneficiary", "Primary beneficiary not deleted", true);
			
			
			String error_msg = "";
			error_msg=beneficiary.readErrorMessage("Delete Beneficiary Error Message");
			if(lib.Web.isWebElementDisplayed(beneficiary, "Delete Beneficiary Error Message")){
				if(lib.Web.VerifyText(Stock.GetParameterValue("Error_message"), error_msg, true))
					Reporter.logEvent(Status.PASS, "Verify if Error message displayed is matching", "error message is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "Verify if  Error message displayed is matching", "error message is not matching", true);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify if  Error message displayed ", "Error messgae is not displayed", true);
				
				
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
			beneficiary.refresh();
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC007_Marital_status_not_displayed_One_beneficiary_use_address_on_record(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			String[] sqlQuery = Stock.getTestQuery("updateParticipantMarritalStatus");
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], participant_SSN);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(homePage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			String marital_status=beneficiary.fetchMaritalStatusFromDB(Stock.GetParameterValue("userName"));
			if(marital_status==null){
				Reporter.logEvent(Status.PASS, "Verify marital status should be null", "Marital status is null", false);
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
				Web.clickOnElement(beneficiary, "ContinueAndConfirm");
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify marital status should be null", "Marital status not null", false);
			Thread.sleep(4000);
			marital_status=beneficiary.fetchMaritalStatusFromDB(Stock.GetParameterValue("userName"));
			if(marital_status.equalsIgnoreCase("M"))
				Reporter.logEvent(Status.PASS, "Verify marital status should not be null", "Marital status is not null", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify marital status should not be null", "Marital status is null", false);
			
				
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			try {
				beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
				
			} catch (Exception e) {

				e.printStackTrace();
			}
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC018_Add_beneficiary_after_enrollment(int itr, Map<String, String> testdata){
		
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			participant_SSN = Stock.GetParameterValue("SSN");
		    first_Name=Stock.GetParameterValue("FIRST_NAME");
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			Enrollment enrollmentpage = new Enrollment(mfaPage);
			enrollmentpage.get();
			enrollmentpage.quickEnroll();
			
			
			
//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
//			leftmenu = new LeftNavigationBar(homePage);			
//		else {
//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
//			leftmenu = new LeftNavigationBar(accountPage);
//		}
			leftmenu = new LeftNavigationBar(enrollmentpage);
			MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			beneficiary.get();
			
			// add a beneficiary
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation"));
			
			lib.Web.isWebElementDisplayed(beneficiary, "AddAnotherBeneficiary");
			
			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);
			
			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//verify beneficiary name
			
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name not matching", true);
			
			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation not matching", true);
			
			//verify beneficiary Relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship not matching", true);
			
			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN not matching", true);
			
			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB not matching", true);
					
			
		}
		catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
			//delete beneficiary from Database
			MyBeneficiaries beneficiary = new MyBeneficiaries();
			try {
				beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
		
		@Test(dataProvider = "setData")
		public void Married_multiple_Primary_Beneficiary(int itr, Map<String, String> testdata){
			
//	      Globals.GBL_CurrentIterationNumber = itr;
			
			
			try{
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
				prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
				lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

				participant_SSN = Stock.GetParameterValue("SSN");
			    first_Name=Stock.GetParameterValue("FIRST_NAME");
				TestDataFromDB.updateTable("setQjsaAndBeneSpouseRule", Stock.GetParameterValue("qjsa_qosa_qpsa_ind"),Stock.GetParameterValue("bene_spousal_rule_code"),Stock.GetParameterValue("Plan_id"));
				//TestDataFromDB.fetchRegisteredParticipant("194391-01");
				LeftNavigationBar leftmenu;
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);

				//			if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn")) <= 2)			
				//			leftmenu = new LeftNavigationBar(homePage);			
				//		else {
				//			MyAccountsPage accountPage = new MyAccountsPage(homePage);
				//			leftmenu = new LeftNavigationBar(accountPage);
				//		}
				leftmenu = new LeftNavigationBar(homePage);
				MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);

				beneficiary.get();
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation1"));
				
				if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("No")){
					//verify if add another beneficiary button is displayed after adding a beneficiary
					if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
						Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

					//verify if continue and confirm button is displayed after adding a beneficiary
					if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
						Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
					//click on continue and confirm button
					if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
						Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
					else
						Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
					if(beneficiary.verifyConfirmationPageDisplayed()){
						Reporter.logEvent(Status.PASS, "Verify Confirmation page displayed", "Confirmation page displayed", true);
					
						Web.clickOnElement(beneficiary, "View Beneficiary Button");
						Reporter.logEvent(Status.INFO, "Click View Beneficiary Button", "Clicked on View Beneficiary Button", false);
						beneficiary.verifyBeneficiaryDisplayed("Primary",Stock.GetParameterValue("FirstName")+" "+Stock.GetParameterValue("MiddleName")+" "+Stock.GetParameterValue("LastName"));
					}
					else
						Reporter.logEvent(Status.FAIL, "Verify Confirmation page displayed", "Confirmation page not displayed", true);
					
					
				}
				else{
					Web.clickOnElement(beneficiary, "ContinueAndConfirm");
					Web.clickOnElement(beneficiary, "ContinueAndConfirm");
					beneficiary.verifyErrorMessage(Stock.GetParameterValue("Error_Message"));
					beneficiary.enterAllocations(Stock.GetParameterValue("Allocation2"));
					if(Stock.GetParameterValue("bene_spousal_rule_code")== null){
						Web.clickOnElement(beneficiary, "ContinueAndConfirm");
						Reporter.logEvent(Status.INFO, "Confirm and Continue button", "Clicked confirm and continue button", false);
						if(lib.Web.isWebElementDisplayed(beneficiary, "Generic Error Msg")){
							Reporter.logEvent(Status.PASS, "Verify if Error page is displayed", "Error page is displayed", false);
							String error_msg="";
							error_msg=beneficiary.readErrorMessage("Generic Error Msg");
							if(lib.Web.VerifyText(Stock.GetParameterValue("Generic_Error_Message"), error_msg, true))
								Reporter.logEvent(Status.PASS, "Verify if Error message is matching", "Error message is matching", false);
							else
								Reporter.logEvent(Status.FAIL, "Verify if Error message is matching", "Error message not matching", true);
						}
						else
							Reporter.logEvent(Status.FAIL, "Verify if Error page is displayed", "Error page not displayed", true);
						
					}
					if(Stock.GetParameterValue("bene_spousal_rule_code").contains("100%")){
						Web.clickOnElement(beneficiary, "ContinueAndConfirm");
						Web.clickOnElement(beneficiary, "ContinueAndConfirm");
						beneficiary.verifyErrorMessage(Stock.GetParameterValue("Error_Message2"));
					}
					if(Stock.GetParameterValue("bene_spousal_rule_code").contains("75%") || Stock.GetParameterValue("bene_spousal_rule_code").contains("66%") || Stock.GetParameterValue("bene_spousal_rule_code").contains("50%") ){
						Web.clickOnElement(beneficiary, "ContinueAndConfirm");
						Reporter.logEvent(Status.INFO, "Confirm and Continue button", "Clicked confirm and continue button", true);
						if(beneficiary.verifyConfirmationPageDisplayed()){
							Reporter.logEvent(Status.PASS, "Verify Confirmation page displayed", "Confirmation page displayed", true);
							
							Web.clickOnElement(beneficiary, "View Beneficiary Button");
							Reporter.logEvent(Status.INFO, "Click View Beneficiary Button", "Clicked on View Beneficiary Button", false);
							beneficiary.verifyBeneficiaryDisplayed("Primary",Stock.GetParameterValue("FirstName")+" "+Stock.GetParameterValue("MiddleName")+" "+Stock.GetParameterValue("LastName"));
						}
						else
							Reporter.logEvent(Status.FAIL, "Verify Confirmation page displayed", "Confirmation page not displayed", true);
						
					}
					
				}
				
			}
			catch(Exception e)
	        {
	            e.printStackTrace();
	            Globals.exception = e;
	            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
	        }
			catch(Error ae)
	        {
	                        ae.printStackTrace();
	                        Globals.error = ae;
	                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
	                        //throw ae;
	        }
			finally {
				//delete beneficiary from Database
				MyBeneficiaries beneficiary = new MyBeneficiaries();
				if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
					try {
						beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
						beneficiary.refresh();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
	        }
			try {
	            Reporter.finalizeTCReport();
			} catch (Exception e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
			}
		
	}
		
		
		
		@Test(dataProvider = "setData")
		public void create_Married_Spouse_Multiple_Spouse_Error(int itr, Map<String, String> testdata){
			
//	      Globals.GBL_CurrentIterationNumber = itr;
			
			
			try{
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
				prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
				lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

				participant_SSN = Stock.GetParameterValue("SSN");
			    first_Name=Stock.GetParameterValue("FIRST_NAME");
				//TestDataFromDB.fetchRegisteredParticipant("194391-01");
				LeftNavigationBar leftmenu;
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				leftmenu = new LeftNavigationBar(homePage);
				MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
				beneficiary.get();
				beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"),Stock.GetParameterValue("Allocation1"));
				
				if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("No")){
					//verify if add another beneficiary button is displayed after adding a beneficiary
					if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
						Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);

					//verify if continue and confirm button is displayed after adding a beneficiary
					if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
						Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
					else
						Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
					//click on continue and confirm button
					if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
						Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
					else
						Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
					if(beneficiary.verifyConfirmationPageDisplayed()){
						Reporter.logEvent(Status.PASS, "Verify Confirmation page displayed", "Confirmation page displayed", true);
						
						Web.clickOnElement(beneficiary, "View Beneficiary Button");
						Reporter.logEvent(Status.INFO, "Click View Beneficiary Button", "Clicked on View Beneficiary Button", false);
						beneficiary.verifyBeneficiaryDisplayed("Primary",Stock.GetParameterValue("FirstName")+" "+Stock.GetParameterValue("MiddleName")+" "+Stock.GetParameterValue("LastName"));
					}
					else
						Reporter.logEvent(Status.FAIL, "Verify Confirmation page displayed", "Confirmation page not displayed", true);
					
					
				}
				else{
					Web.clickOnElement(beneficiary, "ContinueAndConfirm");
					Web.clickOnElement(beneficiary, "ContinueAndConfirm");
					beneficiary.verifyErrorMessage(Stock.GetParameterValue("Error_Message"));
					
					
			}
				
			}
			catch(Exception e)
	        {
	            e.printStackTrace();
	            Globals.exception = e;
	            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
	        }
			catch(Error ae)
	        {
	                        ae.printStackTrace();
	                        Globals.error = ae;
	                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
	                        //throw ae;
	        }
			finally {
				//delete beneficiary from Database
				MyBeneficiaries beneficiary = new MyBeneficiaries();
				if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
					try {
						beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
						beneficiary.refresh();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
	        }
			try {
	            Reporter.finalizeTCReport();
			} catch (Exception e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
			}
		
	}

		@Test(dataProvider = "setData")
		public void validate_Beneficiary_ErrorMessages(int itr, Map<String, String> testdata){
			
//	      Globals.GBL_CurrentIterationNumber = itr;
			
			
			try{
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
				prepareBeneficiaryTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
				lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);

				participant_SSN = Stock.GetParameterValue("SSN");
			    first_Name=Stock.GetParameterValue("FIRST_NAME");
				//TestDataFromDB.fetchRegisteredParticipant("194391-01");
				LeftNavigationBar leftmenu;
				LoginPage login = new LoginPage();
				TwoStepVerification mfaPage = new TwoStepVerification(login);
				LandingPage homePage = new LandingPage(mfaPage);
				leftmenu = new LeftNavigationBar(homePage);
				MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
				beneficiary.get();
				Web.waitForElement(beneficiary, "Married");
				if(Web.isWebElementDisplayed(beneficiary, "Married")){
					Web.clickOnElement(beneficiary, "Married");
				}
				Web.selectDropDownOption(beneficiary,"Beneficiary Relation", "Spouse");
				Web.waitForElement(beneficiary, "First name");
				Web.clickOnElement(beneficiary, "Save button");
				Web.waitForPageToLoad(Web.getDriver());
				//verify All Error Messages Displayed properly
				if(beneficiary.isErrorMessageDisplayed("First name is required."))
					Reporter.logEvent(Status.PASS, "verify 'First name is required' Error Message is Displayed", "'First name is required' Error Message is Displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "verify 'First name is required' Error Message is Displayed", "'First name is required' Error Message is Not Displayed", true);

				if(beneficiary.isErrorMessageDisplayed("Last name is required."))
					Reporter.logEvent(Status.PASS, "verify 'Last name is required.' Error Message is Displayed", "'Last name is required.' Error Message is Displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "verify 'Last name is required.' Error Message is Displayed", "'Last name is required.' Error Message is Not Displayed", true);	
				if(beneficiary.isErrorMessageDisplayed("Date of birth is required."))
					Reporter.logEvent(Status.PASS, "verify 'Date of birth is required.' Error Message is Displayed", "'Date of birth is required.' Error Message is Displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "verify 'Date of birth is required.' Error Message is Displayed", "'Date of birth is required.' Error Message is Not Displayed", true);	
				if(beneficiary.isErrorMessageDisplayed("Social Security number is required."))
					Reporter.logEvent(Status.PASS, "verify 'Social Security number is required.' Error Message is Displayed", "'Social Security number is required.' Error Message is Displayed", false);
				else
					Reporter.logEvent(Status.FAIL, "verify 'Social Security number is required.' Error Message is Displayed", "'Social Security number is required.' Error Message is Not Displayed", true);	
					
			}
			catch(Exception e)
	        {
	            e.printStackTrace();
	            Globals.exception = e;
	            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
	        }
			catch(Error ae)
	        {
	                        ae.printStackTrace();
	                        Globals.error = ae;
	                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
	                        //throw ae;
	        }
			finally {
				//delete beneficiary from Database
				MyBeneficiaries beneficiary = new MyBeneficiaries();
				if(Stock.GetParameterValue("Delete_Beneficiary").equalsIgnoreCase("Yes"))
					try {
						beneficiary.deleteBeneficiariesFromDB(participant_SSN,first_Name+"%");
						beneficiary.refresh();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
	        }
			try {
	            Reporter.finalizeTCReport();
			} catch (Exception e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
			}
		
	}
}
