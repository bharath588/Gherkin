package app.csas.testcases.onlineenrollment;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LoanRequest;
import pageobjects.OnlineEnrollment;
import pageobjects.ParticipantHome;
import pageobjects.Statement_View_Update;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class OnlineEnrollmentTestCases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	LoanRequest LoanRequestPage;
	Statement_View_Update objStatement_View_Update;
	OnlineEnrollment onlineEnrollmentObj;
	
	boolean isPageDisplayed;
	

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
		 * TESTCASE: Sample test case 	
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 14-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void ValidateGrpIdField(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.EnterValidInputDetails();				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		 /**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data -  CITY
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 18-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void ValidateIndicativeCity(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeCity();
			  }
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data -  PERSONAL EMAIL
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 21-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void ValidateIndicativePersonalEmail(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativePersonalEmail();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data - MOBILE PHONE
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 21-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeMobilePhone(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeMobilePhone();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data - HOME PHONE
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 21-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeHomePhone(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeHomePhone();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data - VALIDATION ERROR MESSAGES
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 21-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicative_ValidationErrorMessage(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicative_ValidationErrorMessage();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data - INLINE VALIDATION
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 21-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_InlineValidation(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeData_InlineValidation();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Confirmation Page
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 31-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateConfirmationPage(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateConfirmationPage();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Clear Button
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 31-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_ClearButton(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateClearButton();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Personal Email Phone Number Error Message
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 31-AUG-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_PersonalEmail_Phone_ErrorMsg(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateErrorMsgPersonalPhoneNumber();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}finally{
					  try{
						  Reporter.finalizeTCReport();
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				}
		}
}
