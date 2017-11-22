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

	protected void handleFailure(Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		Throwable t = e.getCause();
		String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
		if (null != t) {
			msg = t.getMessage();

		}
		Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg,
				true);
	}

	protected void handleError(Error ae) {
		ae.printStackTrace();
		Globals.error = ae;
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
				"Assertion Error Occured ==" + ae.getMessage(), true);
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
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void ValidateGrpIdField(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

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
	 * TESTCASE: Enter Indicative Data -  CITY
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 18-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void ValidateIndicativeCity(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicativeCity();

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
	 * TESTCASE: Enter Indicative Data -  PERSONAL EMAIL
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 21-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void ValidateIndicativePersonalEmail(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicativePersonalEmail();

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
	 * TESTCASE: Enter Indicative Data - MOBILE PHONE
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 21-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeMobilePhone(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicativeMobilePhone();

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
	 * TESTCASE: Enter Indicative Data - HOME PHONE
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 21-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeHomePhone(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicativeHomePhone();

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
	 * TESTCASE: Enter Indicative Data - VALIDATION ERROR MESSAGES
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 21-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicative_ValidationErrorMessage(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicative_ValidationErrorMessage();

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
	 * TESTCASE: Enter Indicative Data - INLINE VALIDATION
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 21-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_InlineValidation(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateIndicativeData_InlineValidation();

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
	 * TESTCASE: Confirmation Page
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 31-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateConfirmationPage(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateConfirmationPage();

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
	 * TESTCASE: Clear Button
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 31-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_ClearButton(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateClearButton();

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
	 * TESTCASE: Personal Email Phone Number Error Message
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 31-AUG-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_PersonalEmail_Phone_ErrorMsg(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateErrorMsgPersonalPhoneNumber();

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
	 * TESTCASE: Enter Indicative Data_Country
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 05-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_Country(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.validateIndicativeData_Country();
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
	 * TESTCASE: Enter Indicative Data_State
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 08-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_State(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.validateIndicativeData_State();
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
	 * TESTCASE: Enter Indicative Data_ZipCode
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 08-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void validateIndicativeData_ZipCode(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.validateIndicativeData_ZipCode();
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
	 * TESTCASE: Confirmation Page Informational Message
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 14-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void confirmationPageInformationalMessage(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.confirmationPage_InformationalMsg();
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
	 * TESTCASE: Confirmation Page Back Button
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 14-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void confirmationPageBackButton(int itr, Map<String, String> testdata)
			throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.confirmationPage_BackButton();
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
	 * TESTCASE: Confirmation Page Create New Account
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  BINDU   Date : 14-SEP-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void confirmationPageCreateNewAccount(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.confirmationPage_CreateNewAccount();
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

	/*
	 * AUTHOR: VIRAJ
	 */

	@Test(dataProvider = "setData")
	public void CSAS_OnlineEnrollment_ddtc_28131_invalid_ssn(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			if (itr == 1) {
				// participantHomeObj = new ParticipantHome().get();
				onlineEnrollmentObj = new OnlineEnrollment().get();

				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
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
	public void CSAS_OnlineEnrollment_ddtc_28138_28141_Existing_ssn(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			if (itr == 1) {
				// participantHomeObj = new ParticipantHome().get();
				onlineEnrollmentObj = new OnlineEnrollment().get();

				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
				onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
				onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); // 736012845
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
				onlineEnrollmentObj = new OnlineEnrollment().get();

				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
				onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
				onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); // 736012845
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
				onlineEnrollmentObj = new OnlineEnrollment().get();

				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
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
				onlineEnrollmentObj = new OnlineEnrollment().get();

				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
				onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
				onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); // 736012845
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
	public void CSAS_OnlineEnrollment_ddtc_28155_checkSsn(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1:Launch and logged into CSAS application..
			if (itr == 1) {
				// participantHomeObj = new ParticipantHome().get();
				onlineEnrollmentObj = new OnlineEnrollment().get();
				onlineEnrollmentObj.Enter_gaId(Stock
						.GetParameterValue("PlanNumber"));
				onlineEnrollmentObj.Enter_Pec(Stock.GetParameterValue("PEC"));
				onlineEnrollmentObj.Enter_ssn(Stock.GetParameterValue("SSN")); // 736012845
				onlineEnrollmentObj.submit();
				String ssn = onlineEnrollmentObj.Validate_Ssn_Enroll();

				if (ssn.equalsIgnoreCase(Stock.GetParameterValue("SSN")))
					;
				{

					Reporter.logEvent(Status.PASS,
							"Verify ssn is Same as entered in search",
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

	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TestCase: DDTC-28159  CORE_14409_15279 _Enter Indicative data_Address Line 1 and Line 2
	 * DESCRIPTION:	 Address line1 and Address Line 2 Error Message Validation.  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 12-Oct-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param Test
	 *            data for Automation
	 */

	@Test(dataProvider = "setData")
	public void DDTC_28159_validIndicativeData_AddressLine1_Line2(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Check Addressline1 and Addressline2 fields validation.
			onlineEnrollmentObj.validateErrorMsgAddressLine1AndAddressLine2();

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
	 * TestCase: Address line1 and Address Line 2 Error Message Validation.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 19-Oct-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void DDTC_28158_validIndicativeData_DateOfHire(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);

			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.validateErrorMsgDateOfHire();

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
	 * TestCase: GroupID,PlanEnrollmentCode and SSN fields validation.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 24-Oct-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void DDTC_28132_CORE_18525_ValidIndicativeDataFor_GrpId_PEC_SSN(
			int itr, Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();

			// Verify GroupID,PlanEnrollmentCode and SSN fields validation.
			onlineEnrollmentObj.verifyValidIndicativeDateFor_GrpId_PEC_SSN();

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
	 * TestCase: End to end testing for happy case scenario for successful enrollment with numeric GroupID.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 25-Oct-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18526_EndToEndTesting_Without_Alphanumeric(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();

			// Verify Enrollment details in database after successful
			// enrollment.
			onlineEnrollmentObj.enterValidInputDetails();
			onlineEnrollmentObj.verifyEndToEndTestingWithoutAlphaNumaricOfGId();

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
	 * TestCase: End to end testing for happy case scenario for successful enrollment with alphanumeric GroupID.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 26-Oct-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18527_EndToEndTesting_With_Alphanumeric(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();

			// Verify Enrollment details in database after successful
			// enrollment.
			onlineEnrollmentObj.verifyEndToEndTestingWithAlphaNumaricOfGId();

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
	 * TestCase: Division field error message validations on online enrollment page.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 02-Nov-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18528_CORE_18534_CORE_18547_EnableDivision_DivisionField_HasMoreThanOne_DivisionName(
			int itr, Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify Division field Error Message Validations on online
			// enrollment page.
			onlineEnrollmentObj.validateDivisionField();

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
	 * TestCase: Division field value validation on create account confirmation page.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 02-Nov-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18530_DivisionDisplayonConfirmationPage_DivisionName(
			int itr, Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify division field value validation on create account
			// confirmation page.
			onlineEnrollmentObj.verifyDivisionFieldValueOnConfirmationPage();

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
	 * TestCase: End to end testing for happy case scenario for successful enrollment with division field.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 15-Nov-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18537_EndToEndTesting_WithDivision(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();
			

			// Verify end to end testing with division.
			onlineEnrollmentObj.verifyEndToEndTestingWithDivisionField();

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
	 * TestCase: Test to verify division field enabled with more than one division values.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 15-Nov-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18555_Enable_Division_Field_More_ThanOne_DivisionName(
			int itr, Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify division field has more than one division value.
			onlineEnrollmentObj.verifyDivisionFieldHasMoreThanOneValue();

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
	 * TestCase: Test to verify division field not displayed for the plan where division information not available.
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:  Saraswathi   Date : 15-Nov-2017    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18542_DivisionShouldNotDisplayOnConfirmationPage_When_NODivision(
			int itr, Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();

			// Enter valid SSN,PEC and enter GroupID where division information
			// not available.
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify division field not displayed for this plan.
			onlineEnrollmentObj.verifyNoDivisionDisplayedOnConfirmationPage();

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

	/*
	 * -------------------------------------------------------------------
	 * 
	 * <pre> TestCase: Test to verify division field enabled depends on the
	 * plan. DESCRIPTION: RETURNS: VOID REVISION HISTORY:
	 * --------------------------------------------------------------------
	 * Author: Saraswathi Date : 15-Nov-2017
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br> CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_18539_Enable_Selection_Of_Division(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify division field displayed depends the plan we are
			// selecting.
			onlineEnrollmentObj.verifyEnableSelectionOfDivision();

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
	
	/*
	 * -------------------------------------------------------------------
	 * 
	 * <pre> TestCase: Test to verify division field enabled depends on the
	 * plan. DESCRIPTION: RETURNS: VOID REVISION HISTORY:
	 * --------------------------------------------------------------------
	 * Author: Saraswathi Date : 15-Nov-2017
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br> CSAS Credential</br>
	 */

	@Test(dataProvider = "setData")
	public void CORE_19805_EnterIndicativedata_Gender_RadioButton(int itr,
			Map<String, String> testdata) throws InterruptedException {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Go to onlineEnrollment page.
			onlineEnrollmentObj = new OnlineEnrollment();
			onlineEnrollmentObj.get();
			onlineEnrollmentObj.enterValidInputDetails();

			// Verify division field displayed depends the plan we are
			// selecting.
			onlineEnrollmentObj.verifyEnableSelectionOfDivision();

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
