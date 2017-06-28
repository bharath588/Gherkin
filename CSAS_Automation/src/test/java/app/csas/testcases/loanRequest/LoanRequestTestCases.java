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

import pageobjects.LoanQuote;
import pageobjects.LoanRequest;
import pageobjects.Loan_Request1;
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


}
