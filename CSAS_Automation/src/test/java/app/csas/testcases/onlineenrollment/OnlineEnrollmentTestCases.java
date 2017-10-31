package app.csas.testcases.onlineenrollment;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.OnlineEnrollment;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class OnlineEnrollmentTestCases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;	
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
				}
			  finally{
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
		
		/**
		 * -------------------------------------------------------------------
		 * 
		 * <pre>
		 * TESTCASE: Enter Indicative Data_Country
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 05-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_Country(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeData_Country();
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
		 * TESTCASE: Enter Indicative Data_State
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 08-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_State(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeData_State();
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
		 * TESTCASE: Enter Indicative Data_ZipCode
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 08-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void validateIndicativeData_ZipCode(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.validateIndicativeData_ZipCode();
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
		 * TESTCASE: Confirmation Page Informational Message
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 14-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void confirmationPageInformationalMessage(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.confirmationPage_InformationalMsg();
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
		 * TESTCASE: Confirmation Page Back Button
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 14-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void confirmationPageBackButton(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.confirmationPage_BackButton();
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
		 * TESTCASE: Confirmation Page Create New Account
		 * DESCRIPTION:	  
		 * RETURNS:	VOID	
		 * REVISION HISTORY: 
		 * --------------------------------------------------------------------
		 * Author:  BINDU   Date : 14-SEP-2017    
		 * --------------------------------------------------------------------
		 * </pre>
		 * @param <br>
		 *        CSAS Credential</br>
		 */

		
		@Test(dataProvider="setData")
		public void confirmationPageCreateNewAccount(int itr,Map<String,String> testdata) throws InterruptedException
		{
			  try{
				  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
				  
				  onlineEnrollmentObj = new OnlineEnrollment();
				  onlineEnrollmentObj.get();	 
				  onlineEnrollmentObj.confirmationPage_CreateNewAccount();
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
		
		
		/*
		 * AUTHOR: VIRAJ
		 */
		
		 @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28131_invalid_ssn(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
	                        onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN"));
	                        onlineEnrollmentObj.Validate_invalid_ssn();                    
	                        
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
	     
	     @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28138_28141_Existing_ssn(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
	                        onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); //736012845
	                        onlineEnrollmentObj.submit();
	                        onlineEnrollmentObj.Validate_existing_ssn();                    
	                        
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
	     
	     
	     
	     @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28137_28140_invalid_Pec_Gaid(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
	                        onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); //736012845
	                        onlineEnrollmentObj.submit();
	                        onlineEnrollmentObj.validate_ga_pec_Error();
	                        
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
	     
	     @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28139_validate_plan_num_field(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.air_click();
	                      onlineEnrollmentObj.validate_invalid_PlanNum_Error();
	                        
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
	     
	     @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28152_28150_validate_LastName_field(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
	                        onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); //736012845
	                        onlineEnrollmentObj.submit();
	                        onlineEnrollmentObj.last_Name_Field();
	                        onlineEnrollmentObj.first_Name_Field();
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
	     
	     @Test(dataProvider = "setData")
	     public void CSAS_OnlineEnrollment_ddtc_28159_validate_addressLine_field_28155_checkSsn(
	                   int itr, Map<String, String> testdata) {
	            
	            try {
	                   Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
	                   // Step1:Launch and logged into CSAS application..
	                   if (itr == 1) {
	                       // participantHomeObj = new ParticipantHome().get();
	                        onlineEnrollmentObj= new OnlineEnrollment().get();
	                        
	                        onlineEnrollmentObj.Enter_gaId(Stock.GetParameterValue("PlanNumber"));
	                        onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
	                        onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); //736012845
	                        onlineEnrollmentObj.submit();
	                        onlineEnrollmentObj.validate_Address_Lines();
	                        String ssn=onlineEnrollmentObj.Validate_Ssn_Enroll();
	                        
	                        if(ssn.equalsIgnoreCase(Stock.GetParameterValue("SSN")));
	                        {
	                        
	                    			
	                    			Reporter.logEvent(Status.PASS, "Verify ssn is Same as entered in search",
	                    					"SSn is the same", true);
	                    		}
	                        
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
