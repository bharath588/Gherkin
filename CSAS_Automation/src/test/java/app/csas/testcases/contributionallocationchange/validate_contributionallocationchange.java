package app.csas.testcases.contributionallocationchange;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.ContributionAllocationChange;
import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_contributionallocationchange {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	ContributionAllocationChange contrAllocChng_Obj;
	boolean isPageDisplayed;
	String ga_id = null;

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

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 * TESTCASE:	Validate_Default_Investment_Option_Details
	 * DESCRIPTION:	Validate default group allocation  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Ranjan     Date : 08-07-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Default_Investment_Option_Details(int itr,
			Map<String, String> testdata) {
		ArrayList<String> contrAllChange_List = new ArrayList<String>();
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			// Step2:Search participant using SSN
			contrAllocChng_Obj = new ContributionAllocationChange();
			contrAllChange_List = contrAllocChng_Obj
					.getContributionAllChangeDtls_DB(Stock.GetParameterValue("testType"));
			if (contrAllChange_List.size() <= 0) {
				throw new AssertionError(
						"Did not get any Change allocation ppts or ga_ids");
			}
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					contrAllChange_List.get(0));
			// Step3: Navigate to Contribution change allocation page..
			contrAllocChng_Obj.get();

			// Step4: Validate default investment options..
			contrAllocChng_Obj.validate_DefaultInvstOpts(contrAllChange_List);
			
			//Validate re-balancer checkbox test case
			if (Stock.GetParameterValue("testType").equalsIgnoreCase("RebalancerCheckbox")) {
				contrAllocChng_Obj.select_RebalancerChkBoxs();
			} 

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * -------------------------------------------------------------------
	 * <pre>
	 * TESTCASE:	Validate_Current_Deposite_Allocation_Details
	 * DESCRIPTION:	Validate current deposit allocation details    
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Ranjan     Date : 08-07-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * @param <br>CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Current_Deposite_Allocation_Details(int itr,
			Map<String, String> testdata) {
		ArrayList<String> currDepositeAllocList;
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			participantHomeObj = new ParticipantHome().get();
			// Step2:Search participant using SSN
			contrAllocChng_Obj = new ContributionAllocationChange();
			currDepositeAllocList = contrAllocChng_Obj.getCurrDepositeAllocDtls_DB() ;
			if (currDepositeAllocList.size() <= 0) {
				throw new AssertionError(
						"Did not get any Change allocation ppts or ga_ids");
			}
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					currDepositeAllocList.get(0));
			// Step3: Navigate to Contribution change allocation page..
			contrAllocChng_Obj.get();
			// Step4: Validate default investment options..
			contrAllocChng_Obj.validate_CurrDepositeAlloc_Dtls(currDepositeAllocList);
			//Step5: Complete the Fund percentage transfer transaction..
			contrAllocChng_Obj.transferInvestmentPercentage(currDepositeAllocList);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", errorMsg,
					true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * Method to cleanup all active session
	 * </pre>
	 * 
	 * @author rnjbdn
	 */
	@AfterSuite
	public void cleanUpSession() {
		Web.webdriver.close();
		Web.webdriver.quit();
	}

}
