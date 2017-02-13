package app.psc.testcases.guidedpayroll;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
//import lib.Reporter.Status;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.guidedpayroll.GuidedPayrollPage;
import pageobjects.homepage.HomePage;
import core.framework.Globals;

public class guidedpayrolltc_regression {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private String tcName = Globals.GC_EMPTY;
	private GuidedPayrollPage guidedpayroll =null;	
	private EmployeeSearch empSearch = null;
	HomePage homepage;

	@BeforeClass
	public void InitTest() throws Exception {
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
	
	// QUERY NEEDED FOR PayRollInfo, return 3 plans out of which two are working,
	//need query to eliminate the invalid one
//	select distinct uda.ga_id,uda.user_logon_id
//	from user_data_access uda
//	join(select ga_id from access_customization_view 
//	where access_type_code='PSC_WEB' and accu_code='PlanEmpower') reftable 
//	ON uda.ga_id = reftable.ga_id 
//	where uda.user_logon_id like 'K_04ISIS';
	
	
	@Test(dataProvider = "setData")
	public void TC001_Verify_if_New_Employee_is_added_successfully(int itr, Map<String, String> testdata) {
		String planNumber = Globals.GC_EMPTY; 
		ResultSet resultset = null;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			guidedpayroll = new GuidedPayrollPage().get();
			empSearch = new EmployeeSearch();
			
			// Searching for Plans enrolled for the user
			resultset = empSearch.selectPlanForUser(
					Stock.getTestQuery("getNumberOfPlansQuery"),
					Stock.GetParameterValue("username"));
			
			//Select the respective plan number of the user
			planNumber = empSearch.selectPlanFromResultset(resultset);			
			guidedpayroll.navigateToProcessCenter();
			
			// Enter payroll info for date and amount 
			guidedpayroll.enterPayRollInfo(testdata.get("payrollDate"),testdata.get("payrollAmt"));
			guidedpayroll.selectmoneysource("SELECT"); 
			guidedpayroll.EnterSSN();
			guidedpayroll.AddEmployee("");			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC002_Verify_Enable_or_Disabled_Continue_button_for_Money_Source_selection(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, tcName);
			guidedpayroll = new GuidedPayrollPage();			
			guidedpayroll.navigateToProcessCenter();
			guidedpayroll.enterPayRollInfo(testdata.get("payrollDate"),testdata.get("payrollAmt"));
			guidedpayroll.selectmoneysource("SELECT");		
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC003_Verify_SSN_Errors_for_Duplicate_and_Invalid_input(int itr, Map<String, String> testdata) {
		try {		
			Reporter.initializeReportForTC(itr, tcName);
			if(itr==1){					  
			   guidedpayroll = new GuidedPayrollPage();			
			   guidedpayroll.navigateToProcessCenter();
			   guidedpayroll.enterPayRollInfo(testdata.get("payrollDate"),testdata.get("payrollAmt"));
			   guidedpayroll.selectmoneysource("SELECT");	
			}	
			guidedpayroll.EnterSSN();			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC004_Verify_Errors_when_improper_Employee_Data_provided_during_add_employee(int itr, Map<String, String> testdata) {
		try {	
			Reporter.initializeReportForTC(itr, tcName);
			if(itr==1){			   			  
			   guidedpayroll = new GuidedPayrollPage();
			   guidedpayroll.navigateToProcessCenter();
			   guidedpayroll.enterPayRollInfo(testdata.get("payrollDate"),testdata.get("payrollAmt"));
			   guidedpayroll.selectmoneysource("SELECT");
			   guidedpayroll.EnterSSN();			   
			}		
			if(itr==1|itr==2){
			   guidedpayroll.AddEmployee("invalid_input_error");
			}
			if(itr==3){
			   guidedpayroll.checkInputError("add");			   
			}			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC005_Delete_Record_and_confirm_same(int itr, Map<String, String> testdata) {
		try {
			 Reporter.initializeReportForTC(itr, tcName);			
			 guidedpayroll = new GuidedPayrollPage();	
			 guidedpayroll.performSearchEmployee(true);
			 guidedpayroll.deleteEmployeeAndConfirm();				 
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC006_Edit_Employee_with_Valid_Input(int itr, Map<String, String> testdata) {
		try {
			 Reporter.initializeReportForTC(itr, tcName);			
			 guidedpayroll = new GuidedPayrollPage();						
			 guidedpayroll.performSearchEmployee(false);
			 guidedpayroll.switchToFrame("FRAME");
			 
			 Web.waitForElement(guidedpayroll,"ADD SSN CHKBOX");
			 Web.clickOnElement(guidedpayroll, "ADD SSN CHKBOX");
			 Web.clickOnElement(guidedpayroll, "ADD SSN BTN");
			 Web.waitForElement(guidedpayroll,"SEARCH CANCEL BTN");
			 Web.clickOnElement(guidedpayroll,"SEARCH CANCEL BTN");
			 Web.getDriver().switchTo().defaultContent();
			 guidedpayroll.editEmployee("valid");
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC007_Edit_Employee_with_InValid_Input(int itr, Map<String, String> testdata) {
		try {	
			 Reporter.initializeReportForTC(itr, tcName);
			 if(itr==1){				
				 guidedpayroll = new GuidedPayrollPage();						
				 guidedpayroll.performSearchEmployee(true);		
				 guidedpayroll.editEmployee("invalid");
			 }			 
			 if(itr==2){
				 guidedpayroll.checkInputError("edit");
				 guidedpayroll.switchToFrame("FRAME");
				 Web.waitForElement(guidedpayroll,"EDIT CANCEL BTN");
				 Web.clickOnElement(guidedpayroll,"EDIT CANCEL BTN");	
				 guidedpayroll.checkIfEditICONHighlighted();
				 Web.getDriver().switchTo().defaultContent();
		     }
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void TC008_Process_and_Confirm_contribution(int itr, Map<String, String> testdata) {
		try {
			 Reporter.initializeReportForTC(itr, tcName);
			 if(itr==1){
				 guidedpayroll = new GuidedPayrollPage();						
				 guidedpayroll.navigateToProcessCenter();
				 guidedpayroll.enterPayRollInfo(testdata.get("payrollDate"),testdata.get("payrollAmt"));
				 guidedpayroll.selectmoneysource("SELECT");
			 }
			 guidedpayroll.performContributionProcessing(itr);
			 guidedpayroll.switchToFrame("FRAME");
			 if(itr==1){				
				 guidedpayroll.checkIfButtonEnabled("BTN CONTRIBUTION SAVE",false);				 
			 }else if(itr==2){
				 guidedpayroll.checkIfButtonEnabled("BTN CONTRIBUTION SAVE",true);				 
				 Web.clickOnElement(guidedpayroll, "BTN CONTRIBUTION SAVE");			
				 guidedpayroll.validateRemittance();				
			 }
			 Web.getDriver().switchTo().defaultContent();			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	

	
}
