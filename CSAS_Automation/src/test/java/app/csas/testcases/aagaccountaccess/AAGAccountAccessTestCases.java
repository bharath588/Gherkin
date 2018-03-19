package app.csas.testcases.aagaccountaccess;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.framework.Globals;
import pageobjects.AAGAccountAccess;
import pageobjects.ParticipantHome;

public class AAGAccountAccessTestCases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	ParticipantHome participantHomeObj;
	AAGAccountAccess aAGAccountAccessObj;

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
		this.testData = Stock.getTestData(this.getClass().getPackage()
		.getName(), testCase.getName());
	}
	
	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE: Sample test case 	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  SARASWATHI   Date : 23-JAN-2018    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void verifyFinancialEngines_ProspectUser(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			participantHomeObj = new ParticipantHome();
			participantHomeObj.gotoParticipantPageAndSearchParticipant();
			aAGAccountAccessObj = new AAGAccountAccess();
			aAGAccountAccessObj.verifyFinancialEnginesTitle();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE: Sample test case 	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  SARASWATHI   Date : 23-JAN-2018    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void verifyverifyAdvisoryServicesTitle_GuidenceUser(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			participantHomeObj = new ParticipantHome();
			participantHomeObj.gotoParticipantPageAndSearchParticipant();
			aAGAccountAccessObj = new AAGAccountAccess();
			aAGAccountAccessObj.verifyAdvisoryServicesTitle();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
