package app.csas.testcases.loanRequest;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LoanQuote;
import pageobjects.LoanRequest;
import pageobjects.ParticipantHome;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class LoanRequestTestCases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	LoanRequest LoanRequestPage;
	boolean isPageDisplayed;
	String ga_id = null ;

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

	
	 protected void handleFailure(Exception e){
		 e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();				
				
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
	 }
	 
	 protected void handleError(Error ae){
		    ae.printStackTrace();
	        Globals.error = ae;	      
	        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Error Occured =="+ ae.getMessage(), true);
	 }
	
	
	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE:	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:    Date : 05-04-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * @param <br>
	 *        CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void CSAS_TC01_DDTC_01(
			int itr, Map<String, String> testdata) {
		LoanRequestPage = new LoanRequest();
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			if (itr == 1) {
				participantHomeObj = new ParticipantHome().get();
		
				participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
						Stock.GetParameterValue("PPT_ID"));
				// Step2:Navigating to Loan Quote
				LoanRequestPage.get();
			
			}

			
			
			
			
		} catch (Exception e) {
			handleFailure(e);
		} catch (Error ae) {
	      	handleError(ae);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	

}
