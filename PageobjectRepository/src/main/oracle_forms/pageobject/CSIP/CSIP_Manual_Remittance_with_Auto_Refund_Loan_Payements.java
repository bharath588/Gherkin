package pageobject.CSIP;

import java.sql.SQLException;
import java.util.Date;

import generallib.DateCompare;
import lib.Stock;
import oracleforms.common.OracleForms;

public class CSIP_Manual_Remittance_with_Auto_Refund_Loan_Payements {

	String queryStringPart1;
	//put all the variables whose values we are considering from inut params and overriding them in array
	static String dynamicParams[]={"REMIT_NOTICE_ID_0","AG_REMIT_PAYROLL_DATE_0"};
	//If there is a test data which we need to pass dynamically then don't opt array as of now
	//pass separate values. Look into this if changing the values inside array is possible?
	static String inputParams[]={"AG_REMIT_AMOUNT_0","AG_REMIT_AMOUNT_1","AG_REMIT_PAYROLL_DATE_0","AG_REMIT_SDMT_CODE_LOV",
		"AG_REMIT_SDMT_CODE_LOV_X1","CONTROL_SELECTION_0","IND_REMIT_NEW_SSN_0","IND_REMIT_NEW_SSN_1",
		"IND_REMIT_SUM_AMOUNT_1_0","IND_REMIT_SUM_AMOUNT_2_1","LOGON_DATABASE","LOGON_PASSWORD",
		"LOGON_USERNAME","REMIT_NOTICE_ID_0"};
	/*static String outputParams[]={"ACCESS_CUST_VIEW_ACCESS_TYPE_CODE_0","ACCESS_CUST_VIEW_ACCU_CODE_0",
		"ACCESS_CUST_VIEW_AUDI_CODE_0","ACCESS_CUST_VIEW_PORTAL_0","ACCESS_CUST_VIEW_PUBLISHED_ACCESS_IND_0"};*/
	static String query[]={Stock.getTestQuery("queryForCSIPExtTrustee1")[1],Stock.getTestQuery("queryForCSIPExtTrustee2")[1]};
	static String dbColumns[]={"PROCESS_STATUS_CODE"};
	
	OracleForms oracle_forms;
	String AG_REMIT_PAYROLL_DATE_0=DateCompare.GeneratePastDate();
	public void executeCSIP_Manual_Remittance_with_Auto_Refund_Loan_Payements(String remit_id) throws SQLException, InterruptedException{
		oracle_forms=new OracleForms();

		String dynamicParamsValues[]={remit_id,DateCompare.GeneratePastDate()};
		queryStringPart1=oracle_forms.setQueryStringForDynamicParams(dynamicParams,dynamicParamsValues, inputParams);
		oracle_forms.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,Stock.GetParameterValue("queryString2ndPart"));
		oracle_forms.validateOutputIgnoreTimeOut();
		//Hard coded if there are more parameteres in query. Refine it.
		oracle_forms.validateInDatabaseWithParam(query,remit_id);
	}
}
