package pageobject.QUOT;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class QUOT_QUOTE_SETUP_FOR_NEW_PLAN {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/QUOT_Add_Quote";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_N_CNTL_NAME_0;
	String PROSPECT_MAILING_NAME_1_0;
	String PROSPECT_FIRST_LINE_MAILING_0;
	String PROSPECT_CITY_0;
	String PROSPECT_STATE_CODE_0;
	String PROSPECT_ZIP_CODE_0;
	String PROSPECT_WORK_PHONE_AREA_CODE_0;
	String PROSPECT_WORK_PHONE_NBR_0;
	String PROSPECT_RES_STATE_C_LOV3;
	String PROSPECT_TAX_ID_0;
	String QUOTE_PLAN_NAME_0;
	String QUOTE_SOURCE_CODE_LOV7;
	String QUOTE_PRICE_DATE_0;
	String QUOTE_IRS_CODE_LOV8;
	String QUOTE_PROD_ID_LOV9;
	String QUOTE_MK_TYPE_COD_LOV10;
	String QUOTE_SALE_REASON_LOV13;
	String QUOTE_ELIG_FREQ_CODE_0;
	String QUOTE_FIRST_ENROLL_DATE_0;
	String QUOTE_ELIG_SERV_LENGTH_0;
	String QUOTE_ELIG_SERV_TIME_PERD_0;
	String QUOTE_MIN_ELIG_AGE_0;
	String QUOTE_INIT_ELIG_IND_0;
	String QUOTE_MIN_SERV_RET_LENGTH_0;
	String SALESOFFICE_SO_CODE_LOV17;
	String CONTACT_COMPANY_NAM_LOV19;
	String CONTACT_TYPE_CODE_0;
	String COMPINVOPT_COMPANY_NAM_LOV21;
	String COMPINVOPT_N_DESCR_TYP_LOV22;
	String TRFASSET_DEP_SCHED_DATE_0;
	String TRFASSET_AMOUNT_0;
	String QUOTE_TAKEOVER_TY_LOV14;
	String QUOTE_PAYROLL_TYPE_0;
	String QUOTE_L_H_CLIENT_IND_0;
	String PUP1_CURSOR1_1;
	String PUP1_CURSOR1_3;
	String PROD_MT_CURSOR1_2;
	String PROD_MT_CURSOR1_4;
	String DEPTYPE_CURSOR1_2;
	String DEPTYPE_CURSOR1_3;
	String RATEADJ_UW_RATE_CODE_0;
	String RATEADJ_UW_RATE_VALUE_0;
	String DEPPCT1_SPLIT_PERCENT_0;
	String DEPPCT1_SPLIT_PERCENT_1;
	String QUOTE_N_SALE_DATE_DISP_0;
	String QUOTE_N_GC_DISP_0;
	String GC_RANGE_LOV;
	String QUOTE_N_GC_DISP_0_X1;
	String QUOTE_N_PROFIT_CENTER_0;

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setCONTROL_N_CNTL_NAME_0(String cONTROL_N_CNTL_NAME_0) {
		CONTROL_N_CNTL_NAME_0 = cONTROL_N_CNTL_NAME_0;
	}

	public void setPROSPECT_MAILING_NAME_1_0(String pROSPECT_MAILING_NAME_1_0) {
		PROSPECT_MAILING_NAME_1_0 = pROSPECT_MAILING_NAME_1_0;
	}

	public void setPROSPECT_FIRST_LINE_MAILING_0(
			String pROSPECT_FIRST_LINE_MAILING_0) {
		PROSPECT_FIRST_LINE_MAILING_0 = pROSPECT_FIRST_LINE_MAILING_0;
	}

	public void setPROSPECT_CITY_0(String pROSPECT_CITY_0) {
		PROSPECT_CITY_0 = pROSPECT_CITY_0;
	}

	public void setPROSPECT_STATE_CODE_0(String pROSPECT_STATE_CODE_0) {
		PROSPECT_STATE_CODE_0 = pROSPECT_STATE_CODE_0;
	}

	public void setPROSPECT_ZIP_CODE_0(String pROSPECT_ZIP_CODE_0) {
		PROSPECT_ZIP_CODE_0 = pROSPECT_ZIP_CODE_0;
	}

	public void setPROSPECT_WORK_PHONE_AREA_CODE_0(
			String pROSPECT_WORK_PHONE_AREA_CODE_0) {
		PROSPECT_WORK_PHONE_AREA_CODE_0 = pROSPECT_WORK_PHONE_AREA_CODE_0;
	}

	public void setPROSPECT_WORK_PHONE_NBR_0(String pROSPECT_WORK_PHONE_NBR_0) {
		PROSPECT_WORK_PHONE_NBR_0 = pROSPECT_WORK_PHONE_NBR_0;
	}

	public void setPROSPECT_RES_STATE_C_LOV3(String pROSPECT_RES_STATE_C_LOV3) {
		PROSPECT_RES_STATE_C_LOV3 = pROSPECT_RES_STATE_C_LOV3;
	}

	public void setPROSPECT_TAX_ID_0(String pROSPECT_TAX_ID_0) {
		PROSPECT_TAX_ID_0 = pROSPECT_TAX_ID_0;
	}

	public void setQUOTE_PLAN_NAME_0(String qUOTE_PLAN_NAME_0) {
		QUOTE_PLAN_NAME_0 = qUOTE_PLAN_NAME_0;
	}

	public void setQUOTE_SOURCE_CODE_LOV7(String qUOTE_SOURCE_CODE_LOV7) {
		QUOTE_SOURCE_CODE_LOV7 = qUOTE_SOURCE_CODE_LOV7;
	}

	public void setQUOTE_PRICE_DATE_0(String qUOTE_PRICE_DATE_0) {
		QUOTE_PRICE_DATE_0 = qUOTE_PRICE_DATE_0;
	}

	public void setQUOTE_IRS_CODE_LOV8(String qUOTE_IRS_CODE_LOV8) {
		QUOTE_IRS_CODE_LOV8 = qUOTE_IRS_CODE_LOV8;
	}

	public void setQUOTE_PROD_ID_LOV9(String qUOTE_PROD_ID_LOV9) {
		QUOTE_PROD_ID_LOV9 = qUOTE_PROD_ID_LOV9;
	}

	public void setQUOTE_MK_TYPE_COD_LOV10(String qUOTE_MK_TYPE_COD_LOV10) {
		QUOTE_MK_TYPE_COD_LOV10 = qUOTE_MK_TYPE_COD_LOV10;
	}

	public void setQUOTE_SALE_REASON_LOV13(String qUOTE_SALE_REASON_LOV13) {
		QUOTE_SALE_REASON_LOV13 = qUOTE_SALE_REASON_LOV13;
	}

	public void setQUOTE_ELIG_FREQ_CODE_0(String qUOTE_ELIG_FREQ_CODE_0) {
		QUOTE_ELIG_FREQ_CODE_0 = qUOTE_ELIG_FREQ_CODE_0;
	}

	public void setQUOTE_FIRST_ENROLL_DATE_0(String qUOTE_FIRST_ENROLL_DATE_0) {
		QUOTE_FIRST_ENROLL_DATE_0 = qUOTE_FIRST_ENROLL_DATE_0;
	}

	public void setQUOTE_ELIG_SERV_LENGTH_0(String qUOTE_ELIG_SERV_LENGTH_0) {
		QUOTE_ELIG_SERV_LENGTH_0 = qUOTE_ELIG_SERV_LENGTH_0;
	}

	public void setQUOTE_ELIG_SERV_TIME_PERD_0(
			String qUOTE_ELIG_SERV_TIME_PERD_0) {
		QUOTE_ELIG_SERV_TIME_PERD_0 = qUOTE_ELIG_SERV_TIME_PERD_0;
	}

	public void setQUOTE_MIN_ELIG_AGE_0(String qUOTE_MIN_ELIG_AGE_0) {
		QUOTE_MIN_ELIG_AGE_0 = qUOTE_MIN_ELIG_AGE_0;
	}

	public void setQUOTE_INIT_ELIG_IND_0(String qUOTE_INIT_ELIG_IND_0) {
		QUOTE_INIT_ELIG_IND_0 = qUOTE_INIT_ELIG_IND_0;
	}

	public void setQUOTE_MIN_SERV_RET_LENGTH_0(
			String qUOTE_MIN_SERV_RET_LENGTH_0) {
		QUOTE_MIN_SERV_RET_LENGTH_0 = qUOTE_MIN_SERV_RET_LENGTH_0;
	}

	public void setSALESOFFICE_SO_CODE_LOV17(String sALESOFFICE_SO_CODE_LOV17) {
		SALESOFFICE_SO_CODE_LOV17 = sALESOFFICE_SO_CODE_LOV17;
	}

	public void setCONTACT_COMPANY_NAM_LOV19(String cONTACT_COMPANY_NAM_LOV19) {
		CONTACT_COMPANY_NAM_LOV19 = cONTACT_COMPANY_NAM_LOV19;
	}

	public void setCONTACT_TYPE_CODE_0(String cONTACT_TYPE_CODE_0) {
		CONTACT_TYPE_CODE_0 = cONTACT_TYPE_CODE_0;
	}

	public void setCOMPINVOPT_COMPANY_NAM_LOV21(
			String cOMPINVOPT_COMPANY_NAM_LOV21) {
		COMPINVOPT_COMPANY_NAM_LOV21 = cOMPINVOPT_COMPANY_NAM_LOV21;
	}

	public void setCOMPINVOPT_N_DESCR_TYP_LOV22(
			String cOMPINVOPT_N_DESCR_TYP_LOV22) {
		COMPINVOPT_N_DESCR_TYP_LOV22 = cOMPINVOPT_N_DESCR_TYP_LOV22;
	}

	public void setTRFASSET_DEP_SCHED_DATE_0(String tRFASSET_DEP_SCHED_DATE_0) {
		TRFASSET_DEP_SCHED_DATE_0 = tRFASSET_DEP_SCHED_DATE_0;
	}

	public void setTRFASSET_AMOUNT_0(String tRFASSET_AMOUNT_0) {
		TRFASSET_AMOUNT_0 = tRFASSET_AMOUNT_0;
	}

	public void setQUOTE_TAKEOVER_TY_LOV14(String qUOTE_TAKEOVER_TY_LOV14) {
		QUOTE_TAKEOVER_TY_LOV14 = qUOTE_TAKEOVER_TY_LOV14;
	}

	public void setQUOTE_PAYROLL_TYPE_0(String qUOTE_PAYROLL_TYPE_0) {
		QUOTE_PAYROLL_TYPE_0 = qUOTE_PAYROLL_TYPE_0;
	}

	public void setQUOTE_L_H_CLIENT_IND_0(String qUOTE_L_H_CLIENT_IND_0) {
		QUOTE_L_H_CLIENT_IND_0 = qUOTE_L_H_CLIENT_IND_0;
	}

	public void setPUP1_CURSOR1_1(String pUP1_CURSOR1_1) {
		PUP1_CURSOR1_1 = pUP1_CURSOR1_1;
	}

	public void setPUP1_CURSOR1_3(String pUP1_CURSOR1_3) {
		PUP1_CURSOR1_3 = pUP1_CURSOR1_3;
	}

	public void setPROD_MT_CURSOR1_2(String pROD_MT_CURSOR1_2) {
		PROD_MT_CURSOR1_2 = pROD_MT_CURSOR1_2;
	}

	public void setPROD_MT_CURSOR1_4(String pROD_MT_CURSOR1_4) {
		PROD_MT_CURSOR1_4 = pROD_MT_CURSOR1_4;
	}

	public void setDEPTYPE_CURSOR1_2(String dEPTYPE_CURSOR1_2) {
		DEPTYPE_CURSOR1_2 = dEPTYPE_CURSOR1_2;
	}

	public void setDEPTYPE_CURSOR1_3(String dEPTYPE_CURSOR1_3) {
		DEPTYPE_CURSOR1_3 = dEPTYPE_CURSOR1_3;
	}

	public void setRATEADJ_UW_RATE_CODE_0(String rATEADJ_UW_RATE_CODE_0) {
		RATEADJ_UW_RATE_CODE_0 = rATEADJ_UW_RATE_CODE_0;
	}

	public void setRATEADJ_UW_RATE_VALUE_0(String rATEADJ_UW_RATE_VALUE_0) {
		RATEADJ_UW_RATE_VALUE_0 = rATEADJ_UW_RATE_VALUE_0;
	}

	public void setDEPPCT1_SPLIT_PERCENT_0(String dEPPCT1_SPLIT_PERCENT_0) {
		DEPPCT1_SPLIT_PERCENT_0 = dEPPCT1_SPLIT_PERCENT_0;
	}

	public void setDEPPCT1_SPLIT_PERCENT_1(String dEPPCT1_SPLIT_PERCENT_1) {
		DEPPCT1_SPLIT_PERCENT_1 = dEPPCT1_SPLIT_PERCENT_1;
	}

	public void setQUOTE_N_SALE_DATE_DISP_0(String qUOTE_N_SALE_DATE_DISP_0) {
		QUOTE_N_SALE_DATE_DISP_0 = qUOTE_N_SALE_DATE_DISP_0;
	}

	public void setQUOTE_N_GC_DISP_0(String qUOTE_N_GC_DISP_0) {
		QUOTE_N_GC_DISP_0 = qUOTE_N_GC_DISP_0;
	}

	public void setGC_RANGE_LOV(String gC_RANGE_LOV) {
		GC_RANGE_LOV = gC_RANGE_LOV;
	}

	public void setQUOTE_N_GC_DISP_0_X1(String qUOTE_N_GC_DISP_0_X1) {
		QUOTE_N_GC_DISP_0_X1 = qUOTE_N_GC_DISP_0_X1;
	}

	public void setQUOTE_N_PROFIT_CENTER_0(String qUOTE_N_PROFIT_CENTER_0) {
		QUOTE_N_PROFIT_CENTER_0 = qUOTE_N_PROFIT_CENTER_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_N_CNTL_NAME_0(Stock
				.GetParameterValue("CONTROL_N_CNTL_NAME_0"));
		this.setPROSPECT_MAILING_NAME_1_0(Stock
				.GetParameterValue("PROSPECT_MAILING_NAME_1_0"));
		this.setPROSPECT_FIRST_LINE_MAILING_0(Stock
				.GetParameterValue("PROSPECT_FIRST_LINE_MAILING_0"));
		this.setPROSPECT_CITY_0(Stock.GetParameterValue("PROSPECT_CITY_0"));
		this.setPROSPECT_STATE_CODE_0(Stock
				.GetParameterValue("PROSPECT_STATE_CODE_0"));
		this.setPROSPECT_ZIP_CODE_0(Stock
				.GetParameterValue("PROSPECT_ZIP_CODE_0"));
		this.setPROSPECT_WORK_PHONE_AREA_CODE_0(Stock
				.GetParameterValue("PROSPECT_WORK_PHONE_AREA_CODE_0"));
		this.setPROSPECT_WORK_PHONE_NBR_0(Stock
				.GetParameterValue("PROSPECT_WORK_PHONE_NBR_0"));
		this.setPROSPECT_RES_STATE_C_LOV3(Stock
				.GetParameterValue("PROSPECT_RES_STATE_C_LOV3"));
		this.setPROSPECT_TAX_ID_0(Stock.GetParameterValue("PROSPECT_TAX_ID_0"));
		this.setQUOTE_PLAN_NAME_0(Stock.GetParameterValue("QUOTE_PLAN_NAME_0"));
		this.setQUOTE_SOURCE_CODE_LOV7(Stock
				.GetParameterValue("QUOTE_SOURCE_CODE_LOV7"));
		this.setQUOTE_PRICE_DATE_0(Stock
				.GetParameterValue("QUOTE_PRICE_DATE_0"));
		this.setQUOTE_IRS_CODE_LOV8(Stock
				.GetParameterValue("QUOTE_IRS_CODE_LOV8"));
		this.setQUOTE_PROD_ID_LOV9(Stock
				.GetParameterValue("QUOTE_PROD_ID_LOV9"));
		this.setQUOTE_MK_TYPE_COD_LOV10(Stock
				.GetParameterValue("QUOTE_MK_TYPE_COD_LOV10"));
		this.setQUOTE_SALE_REASON_LOV13(Stock
				.GetParameterValue("QUOTE_SALE_REASON_LOV13"));
		this.setQUOTE_ELIG_FREQ_CODE_0(Stock
				.GetParameterValue("QUOTE_ELIG_FREQ_CODE_0"));
		this.setQUOTE_FIRST_ENROLL_DATE_0(Stock
				.GetParameterValue("QUOTE_FIRST_ENROLL_DATE_0"));
		this.setQUOTE_ELIG_SERV_LENGTH_0(Stock
				.GetParameterValue("QUOTE_ELIG_SERV_LENGTH_0"));
		this.setQUOTE_ELIG_SERV_TIME_PERD_0(Stock
				.GetParameterValue("QUOTE_ELIG_SERV_TIME_PERD_0"));
		this.setQUOTE_MIN_ELIG_AGE_0(Stock
				.GetParameterValue("QUOTE_MIN_ELIG_AGE_0"));
		this.setQUOTE_INIT_ELIG_IND_0(Stock
				.GetParameterValue("QUOTE_INIT_ELIG_IND_0"));
		this.setQUOTE_MIN_SERV_RET_LENGTH_0(Stock
				.GetParameterValue("QUOTE_MIN_SERV_RET_LENGTH_0"));
		this.setSALESOFFICE_SO_CODE_LOV17(Stock
				.GetParameterValue("SALESOFFICE_SO_CODE_LOV17"));
		this.setCONTACT_COMPANY_NAM_LOV19(Stock
				.GetParameterValue("CONTACT_COMPANY_NAM_LOV19"));
		this.setCONTACT_TYPE_CODE_0(Stock
				.GetParameterValue("CONTACT_TYPE_CODE_0"));
		this.setCOMPINVOPT_COMPANY_NAM_LOV21(Stock
				.GetParameterValue("COMPINVOPT_COMPANY_NAM_LOV21"));
		this.setCOMPINVOPT_N_DESCR_TYP_LOV22(Stock
				.GetParameterValue("COMPINVOPT_N_DESCR_TYP_LOV22"));
		this.setTRFASSET_DEP_SCHED_DATE_0(Stock
				.GetParameterValue("TRFASSET_DEP_SCHED_DATE_0"));
		this.setTRFASSET_AMOUNT_0(Stock.GetParameterValue("TRFASSET_AMOUNT_0"));
		this.setQUOTE_TAKEOVER_TY_LOV14(Stock
				.GetParameterValue("QUOTE_TAKEOVER_TY_LOV14"));
		this.setQUOTE_PAYROLL_TYPE_0(Stock
				.GetParameterValue("QUOTE_PAYROLL_TYPE_0"));
		this.setQUOTE_L_H_CLIENT_IND_0(Stock
				.GetParameterValue("QUOTE_L_H_CLIENT_IND_0"));
		this.setPUP1_CURSOR1_1(Stock.GetParameterValue("PUP1_CURSOR1_1"));
		this.setPUP1_CURSOR1_3(Stock.GetParameterValue("PUP1_CURSOR1_3"));
		this.setPROD_MT_CURSOR1_2(Stock.GetParameterValue("PROD_MT_CURSOR1_2"));
		this.setPROD_MT_CURSOR1_4(Stock.GetParameterValue("PROD_MT_CURSOR1_4"));
		this.setDEPTYPE_CURSOR1_2(Stock.GetParameterValue("DEPTYPE_CURSOR1_2"));
		this.setDEPTYPE_CURSOR1_3(Stock.GetParameterValue("DEPTYPE_CURSOR1_3"));
		this.setRATEADJ_UW_RATE_CODE_0(Stock
				.GetParameterValue("RATEADJ_UW_RATE_CODE_0"));
		this.setRATEADJ_UW_RATE_VALUE_0(Stock
				.GetParameterValue("RATEADJ_UW_RATE_VALUE_0"));
		this.setDEPPCT1_SPLIT_PERCENT_0(Stock
				.GetParameterValue("DEPPCT1_SPLIT_PERCENT_0"));
		this.setDEPPCT1_SPLIT_PERCENT_1(Stock
				.GetParameterValue("DEPPCT1_SPLIT_PERCENT_1"));
		this.setQUOTE_N_SALE_DATE_DISP_0(Stock
				.GetParameterValue("QUOTE_N_SALE_DATE_DISP_0"));
		this.setQUOTE_N_GC_DISP_0(Stock.GetParameterValue("QUOTE_N_GC_DISP_0"));
		this.setGC_RANGE_LOV(Stock.GetParameterValue("GC_RANGE_LOV"));
		this.setQUOTE_N_GC_DISP_0_X1(Stock
				.GetParameterValue("QUOTE_N_GC_DISP_0_X1"));
		this.setQUOTE_N_PROFIT_CENTER_0(Stock
				.GetParameterValue("QUOTE_N_PROFIT_CENTER_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONTROL_N_CNTL_NAME_0="
				+ CONTROL_N_CNTL_NAME_0
				+ "&PROSPECT_MAILING_NAME_1_0"
				+ PROSPECT_MAILING_NAME_1_0
				+ "&PROSPECT_FIRST_LINE_MAILING_0"
				+ PROSPECT_FIRST_LINE_MAILING_0
				+ "&PROSPECT_CITY_0="
				+ PROSPECT_CITY_0
				+ "&PROSPECT_STATE_CODE_0"
				+ PROSPECT_STATE_CODE_0
				+ "&PROSPECT_ZIP_CODE_0"
				+ PROSPECT_ZIP_CODE_0
				+ "&PROSPECT_WORK_PHONE_AREA_CODE_0="
				+ PROSPECT_WORK_PHONE_AREA_CODE_0
				+ "&PROSPECT_WORK_PHONE_NBR_0"
				+ PROSPECT_WORK_PHONE_NBR_0
				+ "&PROSPECT_RES_STATE_C_LOV3"
				+ PROSPECT_RES_STATE_C_LOV3
				+ "&PROSPECT_TAX_ID_0="
				+ PROSPECT_TAX_ID_0
				+ "&QUOTE_PLAN_NAME_0"
				+ QUOTE_PLAN_NAME_0
				+ "&QUOTE_SOURCE_CODE_LOV7"
				+ QUOTE_SOURCE_CODE_LOV7
				+ "&QUOTE_PRICE_DATE_0="
				+ QUOTE_PRICE_DATE_0
				+ "&QUOTE_IRS_CODE_LOV8"
				+ QUOTE_IRS_CODE_LOV8
				+ "&QUOTE_PROD_ID_LOV9"
				+ QUOTE_PROD_ID_LOV9
				+ "&QUOTE_MK_TYPE_COD_LOV10="
				+ QUOTE_MK_TYPE_COD_LOV10
				+ "&QUOTE_SALE_REASON_LOV13"
				+ QUOTE_SALE_REASON_LOV13
				+ "&QUOTE_ELIG_FREQ_CODE_0"
				+ QUOTE_ELIG_FREQ_CODE_0
				+ "&QUOTE_FIRST_ENROLL_DATE_0="
				+ QUOTE_FIRST_ENROLL_DATE_0
				+ "&QUOTE_ELIG_SERV_LENGTH_0"
				+ QUOTE_ELIG_SERV_LENGTH_0
				+ "&QUOTE_ELIG_SERV_TIME_PERD_0"
				+ QUOTE_ELIG_SERV_TIME_PERD_0
				+ "&QUOTE_MIN_ELIG_AGE_0="
				+ QUOTE_MIN_ELIG_AGE_0
				+ "&QUOTE_INIT_ELIG_IND_0"
				+ QUOTE_INIT_ELIG_IND_0
				+ "&QUOTE_MIN_SERV_RET_LENGTH_0"
				+ QUOTE_MIN_SERV_RET_LENGTH_0
				+ "&SALESOFFICE_SO_CODE_LOV17="
				+ SALESOFFICE_SO_CODE_LOV17
				+ "&CONTACT_COMPANY_NAM_LOV19"
				+ CONTACT_COMPANY_NAM_LOV19
				+ "&CONTACT_TYPE_CODE_0"
				+ CONTACT_TYPE_CODE_0
				+ "&COMPINVOPT_COMPANY_NAM_LOV21="
				+ COMPINVOPT_COMPANY_NAM_LOV21
				+ "&COMPINVOPT_N_DESCR_TYP_LOV22"
				+ COMPINVOPT_N_DESCR_TYP_LOV22
				+ "&TRFASSET_DEP_SCHED_DATE_0"
				+ TRFASSET_DEP_SCHED_DATE_0
				+ "&TRFASSET_AMOUNT_0="
				+ TRFASSET_AMOUNT_0
				+ "&QUOTE_TAKEOVER_TY_LOV14"
				+ QUOTE_TAKEOVER_TY_LOV14
				+ "&QUOTE_PAYROLL_TYPE_0"
				+ QUOTE_PAYROLL_TYPE_0
				+ "&QUOTE_L_H_CLIENT_IND_0="
				+ QUOTE_L_H_CLIENT_IND_0
				+ "&PUP1_CURSOR1_1"
				+ PUP1_CURSOR1_1
				+ "&PUP1_CURSOR1_3"
				+ PUP1_CURSOR1_3
				+ "&PROD_MT_CURSOR1_2="
				+ PROD_MT_CURSOR1_2
				+ "&PROD_MT_CURSOR1_4"
				+ PROD_MT_CURSOR1_4
				+ "&DEPTYPE_CURSOR1_2"
				+ DEPTYPE_CURSOR1_2
				+ "&DEPTYPE_CURSOR1_3="
				+ DEPTYPE_CURSOR1_3
				+ "&RATEADJ_UW_RATE_CODE_0"
				+ RATEADJ_UW_RATE_CODE_0
				+ "&RATEADJ_UW_RATE_VALUE_0"
				+ RATEADJ_UW_RATE_VALUE_0
				+ "&DEPPCT1_SPLIT_PERCENT_0="
				+ DEPPCT1_SPLIT_PERCENT_0
				+ "&DEPPCT1_SPLIT_PERCENT_1"
				+ DEPPCT1_SPLIT_PERCENT_1
				+ "&QUOTE_N_SALE_DATE_DISP_0"
				+ QUOTE_N_SALE_DATE_DISP_0
				+ "&QUOTE_N_GC_DISP_0"
				+ QUOTE_N_GC_DISP_0
				+ "&GC_RANGE_LOV="
				+ GC_RANGE_LOV
				+ "&QUOTE_N_GC_DISP_0_X1"
				+ QUOTE_N_GC_DISP_0_X1
				+ "&QUOTE_N_PROFIT_CENTER_0"
				+ QUOTE_N_PROFIT_CENTER_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for QUOTE service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run QUOTE service",
					"Execution Done Successfully!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run QUOTE service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
			Reporter.logEvent(
					Status.PASS,
					"From Response: ",
					"CONTROL_N_CNTL_ID_0: "
							+ doc.getElementsByTagName("CONTROL_N_CNTL_ID_0")
									.item(0).getTextContent()
							+ "\nCONTROL_N_PROSGC_INFO_IND_0: "
							+ doc.getElementsByTagName(
									"CONTROL_N_PROSGC_INFO_IND_0").item(0)
									.getTextContent()
							+ "\nCONTROL_N_PG1_DATE_0: "
							+ doc.getElementsByTagName("CONTROL_N_PG1_DATE_0")
							+ "\nQUOTE_ID_0: "
							+ doc.getElementsByTagName("QUOTE_ID_0").item(0)
									.getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException {

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForQUOT")[1],
				this.QUOTE_N_GC_DISP_0_X1);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database\nTableName: GROUP_ACCOUNT",
					false);
		}

		else {
			Reporter.logEvent(Status.FAIL,
					"Validating Record existence in Database",
					"No records exists in Database", false);
		}

	}
}
