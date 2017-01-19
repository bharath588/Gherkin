package app.csas.testcases.fundtofund;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import com.aventstack.extentreports.*;
import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.FundtoFund;
import pageobjects.ParticipantHome;
import core.framework.Globals;

public class validate_fundtofundtransfer {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	ParticipantHome participantHomeObj;
	FundtoFund fund2fund_Obj;
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

	/**
	 * -------------------------------------------------------------------
	 * 
	 * <pre>
	 * TESTCASE:	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Souvik    Date : 27-02-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Fund_to_Fund_Confirm_Transfer(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1: Launch and logged into CSAS application.
			participantHomeObj = new ParticipantHome().get();

			// Step2: Searching for a PPT using PPT ID
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					Stock.GetParameterValue("PPT_ID"),
					Stock.GetParameterValue("GA_ID"));

			participantHomeObj.cancelExistingFundTransfers();

			// Step3: Launching Fund to Fund transfer
			fund2fund_Obj = new FundtoFund().get();

			fund2fund_Obj.NavToTrnsfrFundsPg();

			switch (Stock.GetParameterValue("TrasactionType")) {
			case "Cancel":
				fund2fund_Obj.cancelTransferFromOptions();
				break;
			case "Complete":
				// Step4: Enter Transfer from fund val
				fund2fund_Obj.EnterTransferFromFundVal(Stock
						.GetParameterValue("TransferFromVal"));
				// Step5: Enter Transfer to fund val
				fund2fund_Obj.EnterTransferToFundVal(Stock
						.GetParameterValue("TransferToVal"));
				// Step6: Validating confirmation page
				fund2fund_Obj.ConfirmFundTransfer();
				// Step7: Validate fund to fund transfer complete page
				fund2fund_Obj.validate_FundTransfer_Complete();
				break;
			default:
				// Step8: Validating default investment option
				fund2fund_Obj.ChkDefInvstOpt();
				break;
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
	 * 
	 * <pre>
	 * TESTCASE:	
	 * DESCRIPTION:	  
	 * RETURNS:	VOID	
	 * REVISION HISTORY: 
	 * --------------------------------------------------------------------
	 * Author:Souvik    Date : 27-02-16    
	 * --------------------------------------------------------------------
	 * </pre>
	 * 
	 * @param <br>
	 *            CSAS Credential</br>
	 */
	@Test(dataProvider = "setData")
	public void Validate_Fund2Fund_Transfer_Restriction_Type(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			// Step1: Launch and logged into CSAS application.
			participantHomeObj = new ParticipantHome().get();

			// Step2: Searching for a PPT using PPT ID
			participantHomeObj.search_PPT_Plan_With_PPT_ID_OR_SSN("PPT_ID",
					participantHomeObj.getGAID_or_pptID_RestrictionType()
							.get(0), participantHomeObj
							.getGAID_or_pptID_RestrictionType().get(1));

			participantHomeObj.cancelExistingFundTransfers();

			// Step3: Launching Fund to Fund transfer
			fund2fund_Obj = new FundtoFund().get();

			fund2fund_Obj.NavToTrnsfrFundsPg();

			// Step4: Cancel Fund to fund transfer..
			if (Stock.GetParameterValue("TrasactionType").equalsIgnoreCase(
					"Cancel")) {
				fund2fund_Obj.cancelTransferFromOptions();
			} else {

				// Step 8: Verify greater prior day amount error message
				// Step5: Validating default investment option
				fund2fund_Obj.ChkDefInvstOpt();

				if (Stock.GetParameterValue("isRestrictionChckBxSelected")
						.equalsIgnoreCase("NO")) {
					// Step6: Enter Transfer from fund val
					fund2fund_Obj.EnterTransferFromFundVal(Stock
							.GetParameterValue("TransferFromVal"));
					// Step7: Verify error message
					fund2fund_Obj.verifyRestrictionTypeErrMsg();

				} else {
					fund2fund_Obj.checkRestrictionType();
					switch (Stock.GetParameterValue("isErrMsg")) {
					case "grtrPriordayBal":
						fund2fund_Obj.enter$AmtGrtPriordayBal();
						fund2fund_Obj.EnterTransferToFundVal(Stock
								.GetParameterValue("TransferToVal"));
						fund2fund_Obj.verifyErrMsg$AmtGrtPriordayBal();
						break;
					case "grtr100Perc":
						fund2fund_Obj.EnterTransferFromPer(Stock
								.GetParameterValue("TransferFromVal"));
						fund2fund_Obj.verifyErrMsgPercentageGrtPriordayBal();
						break;
					default:
						fund2fund_Obj.EnterTransferFromFundVal(Stock
								.GetParameterValue("TransferFromVal"));
						break;
					}
				}
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
	 * <pre>
	 * Method to cleanup all active session
	 * </pre>
	 * 
	 * @author rnjbdn
	 */
	@AfterSuite
	public void cleanUpSession() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}

}
