package pageobject.LMNT;

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

public class LMNT_Loan_Money_Type_Split_Add_Record {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LMNT_Loan_Money_Type_Split_Add_Record";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String LNMTSP1_EFFDATE_0;
	String LNMTSP1_EFFDATE_0_X1;
	String LNMTSP1_EFFDATE_0_X2;
	String LNMTSP1_EFFDATE_0_X3;
	String LNMTSP1_EFFDATE_0_X4;
	String LNMTSP1_EFFDATE_0_X5;
	String LNMTSP1_EFFDATE_0_X6;
	String LNMTSP1_INLNAG_SEQNBR_8;
	String LNMTSP1_SPLIT_PERCENT_0;
	String LNMTSP1_SPLIT_PERCENT_0_X1;
	String LNMTSP1_SPLIT_PERCENT_0_X2;
	String LNMTSP1_SPLIT_PERCENT_0_X3;
	String LNMTSP1_SPLIT_PERCENT_0_X4;
	String LNMTSP1_SPLIT_PERCENT_0_X5;
	String LNMTSP1_TAX_BASIS_RATIO_0;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setLNMTSP1_EFFDATE_0(String lNMTSP1_EFFDATE_0) {
		LNMTSP1_EFFDATE_0 = lNMTSP1_EFFDATE_0;
	}
	public void setLNMTSP1_EFFDATE_0_X1(String lNMTSP1_EFFDATE_0_X1) {
		LNMTSP1_EFFDATE_0_X1 = lNMTSP1_EFFDATE_0_X1;
	}
	public void setLNMTSP1_EFFDATE_0_X2(String lNMTSP1_EFFDATE_0_X2) {
		LNMTSP1_EFFDATE_0_X2 = lNMTSP1_EFFDATE_0_X2;
	}
	public void setLNMTSP1_EFFDATE_0_X3(String lNMTSP1_EFFDATE_0_X3) {
		LNMTSP1_EFFDATE_0_X3 = lNMTSP1_EFFDATE_0_X3;
	}
	public void setLNMTSP1_EFFDATE_0_X4(String lNMTSP1_EFFDATE_0_X4) {
		LNMTSP1_EFFDATE_0_X4 = lNMTSP1_EFFDATE_0_X4;
	}
	public void setLNMTSP1_EFFDATE_0_X5(String lNMTSP1_EFFDATE_0_X5) {
		LNMTSP1_EFFDATE_0_X5 = lNMTSP1_EFFDATE_0_X5;
	}
	public void setLNMTSP1_EFFDATE_0_X6(String lNMTSP1_EFFDATE_0_X6) {
		LNMTSP1_EFFDATE_0_X6 = lNMTSP1_EFFDATE_0_X6;
	}
	public void setLNMTSP1_INLNAG_SEQNBR_8(String lNMTSP1_INLNAG_SEQNBR_8) {
		LNMTSP1_INLNAG_SEQNBR_8 = lNMTSP1_INLNAG_SEQNBR_8;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0(String lNMTSP1_SPLIT_PERCENT_0) {
		LNMTSP1_SPLIT_PERCENT_0 = lNMTSP1_SPLIT_PERCENT_0;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0_X1(String lNMTSP1_SPLIT_PERCENT_1) {
		LNMTSP1_SPLIT_PERCENT_0_X1 = lNMTSP1_SPLIT_PERCENT_1;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0_X2(String lNMTSP1_SPLIT_PERCENT_2) {
		LNMTSP1_SPLIT_PERCENT_0_X2 = lNMTSP1_SPLIT_PERCENT_2;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0_X3(String lNMTSP1_SPLIT_PERCENT_3) {
		LNMTSP1_SPLIT_PERCENT_0_X3 = lNMTSP1_SPLIT_PERCENT_3;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0_X4(String lNMTSP1_SPLIT_PERCENT_4) {
		LNMTSP1_SPLIT_PERCENT_0_X4 = lNMTSP1_SPLIT_PERCENT_4;
	}
	public void setLNMTSP1_SPLIT_PERCENT_0_X5(String lNMTSP1_SPLIT_PERCENT_5) {
		LNMTSP1_SPLIT_PERCENT_0_X5 = lNMTSP1_SPLIT_PERCENT_5;
	}
	public void setLNMTSP1_TAX_BASIS_RATIO_0(String lNMTSP1_TAX_BASIS_RATIO_0) {
		LNMTSP1_TAX_BASIS_RATIO_0 = lNMTSP1_TAX_BASIS_RATIO_0;
	}
	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(
			String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV = qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE").toLowerCase());
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setLNMTSP1_EFFDATE_0(Stock.GetParameterValue("LNMTSP1_EFFDATE_0"));
		this.setLNMTSP1_EFFDATE_0_X1(Stock.GetParameterValue("LNMTSP1_EFFDATE_0_X1"));
		this.setLNMTSP1_EFFDATE_0_X2(Stock.GetParameterValue("LNMTSP1_EFFDATE_0_X2"));
		this.setLNMTSP1_EFFDATE_0_X3(Stock.GetParameterValue("LNMTSP1_EFFDATE_0_X3"));
		this.setLNMTSP1_EFFDATE_0_X4("");
		this.setLNMTSP1_EFFDATE_0_X5(Stock.GetParameterValue("LNMTSP1_EFFDATE_0_X5"));
		this.setLNMTSP1_EFFDATE_0_X6(Stock.GetParameterValue("LNMTSP1_EFFDATE_0_X6"));
		this.setLNMTSP1_INLNAG_SEQNBR_8(Stock.GetParameterValue("LNMTSP1_INLNAG_SEQNBR_8"));
		this.setLNMTSP1_SPLIT_PERCENT_0(Stock.GetParameterValue("LNMTSP1_SPLIT_PERCENT_0"));
		this.setLNMTSP1_SPLIT_PERCENT_0_X1(Stock.GetParameterValue("LNMTSP1_SPLIT_PERCENT_0_X1"));
		this.setLNMTSP1_SPLIT_PERCENT_0_X2(Stock.GetParameterValue("LNMTSP1_SPLIT_PERCENT_0_X2"));
		this.setLNMTSP1_SPLIT_PERCENT_0_X3(Stock.GetParameterValue("LNMTSP1_SPLIT_PERCENT_0_X3"));
		this.setLNMTSP1_SPLIT_PERCENT_0_X4("");
		this.setLNMTSP1_SPLIT_PERCENT_0_X5(Stock.GetParameterValue("LNMTSP1_SPLIT_PERCENT_0_X5"));
		this.setLNMTSP1_TAX_BASIS_RATIO_0(Stock.GetParameterValue("LNMTSP1_TAX_BASIS_RATIO_0"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&LNMTSP1_EFFDATE_0="+LNMTSP1_EFFDATE_0+
				"&LNMTSP1_EFFDATE_0_X1="+LNMTSP1_EFFDATE_0_X1+"&LNMTSP1_EFFDATE_0_X2="+LNMTSP1_EFFDATE_0_X2+"&LNMTSP1_EFFDATE_0_X3="+LNMTSP1_EFFDATE_0_X3+
				"&LNMTSP1_EFFDATE_0_X4="+LNMTSP1_EFFDATE_0_X4+"&LNMTSP1_EFFDATE_0_X5="+LNMTSP1_EFFDATE_0_X5+"&LNMTSP1_EFFDATE_0_X6="+LNMTSP1_EFFDATE_0_X6+
				"&LNMTSP1_INLNAG_SEQNBR_8="+LNMTSP1_INLNAG_SEQNBR_8+"&LNMTSP1_SPLIT_PERCENT_0="+LNMTSP1_SPLIT_PERCENT_0+"&LNMTSP1_SPLIT_PERCENT_0_X1="+LNMTSP1_SPLIT_PERCENT_0_X1+
				"&LNMTSP1_SPLIT_PERCENT_0_X2="+LNMTSP1_SPLIT_PERCENT_0_X2+"&LNMTSP1_SPLIT_PERCENT_0_X3="+LNMTSP1_SPLIT_PERCENT_0_X3+"&LNMTSP1_SPLIT_PERCENT_0_X4="+LNMTSP1_SPLIT_PERCENT_0_X4+
				"&LNMTSP1_SPLIT_PERCENT_0_X5="+LNMTSP1_SPLIT_PERCENT_0_X5+"&LNMTSP1_TAX_BASIS_RATIO_0="+LNMTSP1_TAX_BASIS_RATIO_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="+QUERY_CHG_MULTIPLE_SSN_EXT_LOV+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for LMNT service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run LMNT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run LMNT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","ADDRESS_VALIDATION_SOURCE_CODE_0: "+doc.getElementsByTagName("ADDRESS_VALIDATION_SOURCE_CODE_0").item(0).getTextContent()+
					"\nCONTROL_IRS_CODE_0: "+doc.getElementsByTagName("CONTROL_IRS_CODE_0").item(0).getTextContent()+
					"\nCONTROL_PROD_ID_0: "+doc.getElementsByTagName("CONTROL_PROD_ID_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForLMNTAdd")[1], this.CONTROL_SSN_DISPL_0, this.LNMTSP1_INLNAG_SEQNBR_8, this.LNMTSP1_TAX_BASIS_RATIO_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records exists in DB", "Records exists in DB\nTableName: LOAN_MNTY_SPLT", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "From Database", "IND_ID: "+queryResultSet.getString("IND_ID")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTAX_BASIS_RATIO: "+queryResultSet.getString("TAX_BASIS_RATIO")+
						"\nSPLIT_PERCENT: "+queryResultSet.getString("SPLIT_PERCENT"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking whether records exists in DB", "Records doesn't exists in DB\nTableName: LOAN_MNTY_SPLT", false);
		}
	}
}
