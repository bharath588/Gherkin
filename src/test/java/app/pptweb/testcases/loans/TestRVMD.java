/**
 * 
 */
package app.pptweb.testcases.loans;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.loan.RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU;

import com.aventstack.extentreports.Status;


import core.framework.Globals;

/**
 * @author smykjn
 *
 */
public class TestRVMD {
	RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU rvmd_reversal;
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	
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
			
	 @Test(dataProvider = "setData")
	    public void runRVMD_Multiple_Disbursement_Reversal(int itr, Map<String, String> testdata){
	    	try {
	    		Reporter.initializeReportForTC(itr, core.framework.Globals.GC_MANUAL_TC_NAME);
				Reporter.logEvent(Status.INFO, "Testcase Description:","GQ19RVMD - Disbursement (Multiple) Reversals Execution via MAIN MENU", false);
				rvmd_reversal = new RVMD_Multiple_Disbursement_Reversals_Executed_via_MAIN_MENU();
				//General.setDatabaseInstance(Stock.GetParameterValue("LOGON_DATABASE"));
				rvmd_reversal.setServiceParameters(Stock.GetParameterValue("SSN"));
				rvmd_reversal.runService();
				rvmd_reversal.validateOutput();
				rvmd_reversal.validateInDatabase(Stock.GetParameterValue("SSN"));
			}catch(Exception e){
				e.printStackTrace();
				Globals.exception = e;
				String exceptionMessage = e.getCause().getMessage();
				Reporter.logEvent(Status.FAIL, "A run time exception occured.",exceptionMessage, false);
			}
			catch(Error ae)
			{
				ae.printStackTrace();
				Globals.error = ae;
				Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , false);                    
				//throw ae;
			}
			finally {
			}
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
	 }
}
