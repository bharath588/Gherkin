package app.csas.testcases.loanRequest;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.LoanRequest;
import pageobjects.ParticipantHome;


import pageobjects.Statement_View_Update;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class LoanRequestTestCases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	ParticipantHome participantHomeObj;
	LoanRequest LoanRequestPage;
	Statement_View_Update objStatement_View_Update;
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
	 * Author:  Megha   Date : 05-04-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * @param <br>
	 *        CSAS Credential</br>
	 */

	
	@Test(dataProvider="setData")
	public void validating_loan_number_link(int itr,Map<String,String> testdata) throws InterruptedException
	{
		  try{
			  System.out.println("validating the loan number link");
			  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
			  LoanRequestPage=new LoanRequest();	
			  LoanRequestPage.get();	 
			  LoanRequestPage.validating_loan_number_link();
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
	
	
	@Test(dataProvider="setData")
	public void validating_mobile_phone_text_box(int itr,Map<String,String> testdata) throws InterruptedException
	{
		  try{
			  System.out.println("validating mobile phone field");
			  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
			  LoanRequestPage=new LoanRequest();	
			  LoanRequestPage.get();		
			  LoanRequestPage.validating_mobile_phone();
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
	
	

@Test(dataProvider="setData")
public void statement_order_update_confirmation(int itr,Map<String,String> testdata)throws InterruptedException
{
	try{
		System.out.println("Testing update confirmation page");
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);			
		objStatement_View_Update=new Statement_View_Update();
		objStatement_View_Update.get();
		objStatement_View_Update.verifying_if_fax_is_selected();
		objStatement_View_Update.checking_fax();
		objStatement_View_Update.updating_delivery_information();		
		objStatement_View_Update.confirm_delivery_information();		
		objStatement_View_Update.return_confirmation_number();
	
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


@Test(dataProvider="setData")
public void validating_if_payment_method_noneditable(int itr,Map<String,String> testdata) throws InterruptedException
{
	try{
		System.out.println("Validating if payment method is a non-editable field");
		  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
		  LoanRequestPage=new LoanRequest();	
		  LoanRequestPage.get();
		  LoanRequestPage.validating_payment_method_noneditable();
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



@Test(dataProvider="setData")
public void validating_vesting_field(int itr,Map<String,String> testdata) throws InterruptedException
{
	try{
		System.out.println("Validating the vesting field");
		 Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
		  LoanRequestPage=new LoanRequest();	
		  LoanRequestPage.get();
		  LoanRequestPage.validating_vesting_field();
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try{
			Reporter.finalizeTCReport();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	
}


@Test(dataProvider="setData")
public void validating_recalculate_functionality(int itr,Map<String,String> testdata) throws InterruptedException
{
	try{
		System.out.println("Validating the recalculate button's functionality");
		 Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
		  LoanRequestPage=new LoanRequest();	
		  LoanRequestPage.get();
		  LoanRequestPage.validating_recalculate_buttton();
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try{
			Reporter.finalizeTCReport();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
}


@Test(dataProvider="setData")
public void validating_green_tick_on_selection_of_loan_quote(int itr,Map<String,String> testdata) throws InterruptedException
{
try{
	System.out.println("Validating if green tick is appearing on selecting a loan quote");
	Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
	  LoanRequestPage=new LoanRequest();	
	  LoanRequestPage.get();
	  LoanRequestPage.verifying_enable_of_select_button();
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
	

@Test(dataProvider="setData")
public void validating_refinance_with_adtn_loan_amnt_functionality(int itr,Map<String,String> testdata) throws InterruptedException
{
	try{
		System.out.println("Validating refinance functionality of loan request with additional loan amount");
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);	
		  LoanRequestPage=new LoanRequest();	
		  LoanRequestPage.get();
		  LoanRequestPage.refinance();
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
 * TESTCASE:  DDTC-26947 Paperless Loan_CORE_12115
 * DESCRIPTION: TestCase takes min amount for Loan Request and Deliver Form is submitted      
  * RETURNS:   VOID   
 * REVISION HISTORY: 
  * --------------------------------------------------------------------
 * Author: Bindu    Date : 28-04-17    
  * --------------------------------------------------------------------
 * </pre>
 * @param <br>
 *        CSAS Credential</br>
 */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_TC02_DDTC_26947(int itr, Map<String, String> testdata) {
	 
        try {
      	  Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
      	  LoanRequestPage = new LoanRequest();
      	  LoanRequestPage.get();
      	  LoanRequestPage.verifyDeliverForm();                    
                                 
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26942 Paperless Loan_CORE_13693
  * DESCRIPTION: TestCase edits address for participant in Loan Request page       
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 03-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_TC04_DDTC_26942(int itr, Map<String, String> testdata) {
       
        try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoanRequestPage = new LoanRequest();
			LoanRequestPage.get();
			LoanRequestPage.verify_EditAddress_Field();

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
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26083 Paperless Loan_Exception_CORE_10724
  * DESCRIPTION: TestCase takes eligible Outstanding loans and Re-finance it and then submits Loan request      
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 03-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_TC06_DDTC_26083(int itr, Map<String, String> testdata) {
      
        try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoanRequestPage = new LoanRequest();
			LoanRequestPage.get();
			LoanRequestPage.verify_Refinance_Loan_Multiple_Outstanding_Loans();                 
                                   
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
 
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26084 Paperless Loan_Exception_CORE_10724
  * DESCRIPTION: TestCase takes eligible Outstanding loans and Re-finance it and then submits Loan request      
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 03-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_TC08_DDTC_26084(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get(); 
               LoanRequestPage.verify_Refinance_Loan_Maximum_Outstanding_Loans();
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26925 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies the loan term in months or years      
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 03-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_TC10_DDTC_26925(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_Loan_Term();
                                   
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

 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26082 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies the loan term in months or years      
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 03-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_Exceptions_TC12_DDTC_26082(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get(); 
               LoanRequestPage.verify_Exceptions_Refinance_Multiple_NO_Addtl_Amt();      
               
                                   
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

 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-27970 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies promissory note set to PPT ID-Deliver Form     
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 17-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_HappyPath_TC14_DDTC_27970(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_HappyPath_PromissoryNote_SetTo_pptID_DeliverForm();
                     
               
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-27971 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies promissory note set to PPT ID-LoanRequest     
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 17-05-17    
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_HappyPath_TC16_DDTC_27971(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_HappyPath_PromissoryNote_SetTo_pptID_LoanRequest();
                     
               
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-27976 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies participant with ACH
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date :     
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_HappyPath_TC18_DDTC_27976(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get(); 
               LoanRequestPage.verify_HappyPath_ACH();
                     
               
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-27976 Paperless Loan_CORE_5368
  * DESCRIPTION: TestCase verifies the Back Hyperlink button
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date :     
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_Back_Hyperlink_TC20_DDTC_24665(int itr, Map<String, String> testdata) {
     
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verifyBackHyperlink();
                     
               
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26093 Paperless Loan_Exceptions_CORE_11164
  * Priority: HIGH
  * DESCRIPTION: TestCase verifyCheckbox_RefinanceLoanTerm
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 02-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC22_DDTC_26093(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get(); 
               LoanRequestPage.verifyCheckbox_RefinanceLoanTerm();
                     
               
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26088 Paperless Loan_Exceptions_CORE_11533
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Different Loan Choices
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 02-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC24_DDTC_26088(int itr, Map<String, String> testdata) {
     
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get(); 
               LoanRequestPage.verifyLoanChoices();                           
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26085 Paperless Loan_Exceptions_CORE_5368
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Term under Selected Loan Quote
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 02-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC26_DDTC_26085(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verifyLoanTerm_Under_SelectedLoanQuote();                           
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-26080 Paperless Loan_Exceptions_CORE_10707
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Term under Selected Loan Quote
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 02-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC28_DDTC_26080(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.LoanRefinancing_NoAdditionalAmount();                           
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  DDTC-24268 Paperless Loan_Loan Request_Loans_Loan Structure Overview Section_Payment & repayment options Field
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Structure Overview Hyperlink(Payment and Repayment options)
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 29-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC30_DDTC_24268(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_LoanStructureOverview_Hyperlink();                           
                                   
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
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE: DDTC-24267 Paperless Loan_Loan Request_Loans_Loan Structure Overview Section_Loan structure Field
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Structure Overview Loan structure field
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu    Date : 30-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC32_DDTC_24267(int itr, Map<String, String> testdata) {
     
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
 			  	 LoanRequestPage.verify_FullLoanStructureDetails();                           
                                   
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
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  Paperless Loan_Loan Request_Loans_Loan Structure Overview Approval Requirements field
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Structure Overview Approval Requiremnets field
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 30-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC34_DDTC_24266(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_LoanStructure_ApprovalReqFields();                           
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  Paperless Loan_Loan Request_Loans_Loan Structure Overview Number of loans allowed field
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Structure Overview number of loans allowed field
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 30-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC36_DDTC_24264(int itr, Map<String, String> testdata) {
       
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_LoanStructure_NumberOfLoansAllowed();                           
                                   
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
 
 
 /**
  * -------------------------------------------------------------------
  * 
   * <pre>
  * TESTCASE:  Paperless Loan_Loan Request_Loans_Loan Structure Overview Fees field
  * Priority: HIGH
  * DESCRIPTION: TestCase verify Loan Structure Overview Fees field
   * RETURNS:   VOID   
  * REVISION HISTORY: 
   * --------------------------------------------------------------------
  * Author: Bindu   Date : 30-JUN-17
   * --------------------------------------------------------------------
  * </pre>
  * @param <br>
  *        CSAS Credential</br>
  */
 @Test(dataProvider = "setData")
 public void CSAS_Paperless_Loan_LoanRequest_TC38_DDTC_24262(int itr, Map<String, String> testdata) {
      
        try {
               Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
               LoanRequestPage=new LoanRequest();	
 			  	 LoanRequestPage.get();
               LoanRequestPage.verify_LoanStructure_Fees();                           
                                   
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
