package pageobject.CSIP;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.Status;

import oracleforms.common.OracleForms;

public class Manual_Remittance_with_Auto_Refund_Loan_Payements {
	
	static String inputParams[]={"AG_REMIT_AMOUNT_0","AG_REMIT_AMOUNT_1","AG_REMIT_PAYROLL_DATE_0",
								"AG_REMIT_SDMT_CODE_LOV","AG_REMIT_SDMT_CODE_LOV_X1","CONTROL_SELECTION_0",
								"IND_REMIT_NEW_SSN_0","IND_REMIT_NEW_SSN_1","IND_REMIT_SUM_AMOUNT_1_0",
								"IND_REMIT_SUM_AMOUNT_2_1","LOGON_DATABASE","LOGON_PASSWORD"
								,"LOGON_USERNAME","REMIT_NOTICE_ID_0"};
	static String outputParams[]={"ACCESS_CUST_VIEW_ACCESS_TYPE_CODE_0","ACCESS_CUST_VIEW_ACCU_CODE_0",
		"ACCESS_CUST_VIEW_AUDI_CODE_0","ACCESS_CUST_VIEW_PORTAL_0","ACCESS_CUST_VIEW_PUBLISHED_ACCESS_IND_0"};
	static String queryParams[]={Stock.GetParameterValue("GET_GA_GA_ID_0_X1")};
OracleForms oracle_forms=new OracleForms();



}
