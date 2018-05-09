package pageobject.CSIP;

import generallib.DateCompare;

import java.sql.SQLException;

import lib.Stock;
import oracleforms.common.OracleForms;

public class CSIP_NEGATIVE_BALANCE_REMITTANCE {
	String queryStringPart1;
	static String dynamicParams[]={"REMIT_NOTICE_ID_0","AG_REMIT_PAYROLL_DATE_0"};
	
	static String inputParams[]={"LOGON_USERNAME","LOGON_PASSWORD","LOGON_DATABASE","CONTROL_SELECTION_0",
		"REMIT_NOTICE_ID_0","AG_REMIT_AMOUNT_0","AG_REMIT_SDMT_CODE_0","AG_REMIT_GDMT_SEQNBR_0",
		"AG_REMIT_PAYROLL_DATE_0","IND_REMIT_NEW_SSN_0","IND_REMIT_SUM_AMOUNT_1_0","IND_REMIT_NEW_SSN_1",
		"IND_REMIT_SUM_AMOUNT_1_1"};
	
	static String query[]={Stock.getTestQuery("queryForCSIPExtTrustee1")[1]
			,Stock.getTestQuery("queryForCSIPExtTrustee2")[1]};
	static String query1[]={Stock.getTestQuery("queryForCSIPNEGATIVEBALANCEREMITTANCE")[1]};
	
	static String dbColumns[]={"EV_ID","BALANCE_STATUS_CODE","OUT_OF_BAL_AMT"};
	
	OracleForms oracle_forms;
	String AG_REMIT_PAYROLL_DATE_0=DateCompare.GeneratePastDate();
	public void executeCSIP_NEGATIVE_BALANCE_REMITTANCE(String remit_id) throws SQLException, InterruptedException{
		oracle_forms=new OracleForms();

		String dynamicParamsValues[]={remit_id,DateCompare.GeneratePastDate()};
		queryStringPart1=oracle_forms.setQueryStringForDynamicParams(dynamicParams,dynamicParamsValues, inputParams);
		oracle_forms.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,Stock.GetParameterValue("queryString2ndPart"));
		oracle_forms.validateOutputIgnoreTimeOut();
		oracle_forms.validateInDatabaseWithParam(query,remit_id);
		oracle_forms.validateInDatabaseWithParamFetchValues(query1, remit_id, dbColumns);
	}
}
