package pageobject.RTMP;

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

public class RTMP_Insert_New_Revenue_Share_Temp_Record {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RTMP_Insert_New_Revenue_Share_Template_Record";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REV_SHARE_RECOV_TEMPLATE_ID_0;
	String REV_SHARE_RECOV_TEMPLATE_DESCR_0;
	String RVRS_CODE_LOV;
	String BASIS_TYPE_CODE_LOV;
	String FAT_CODE_LOV;
	String CALC_LVL_LOV;
	String FREQ_LOV;
	String REV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0;
	String REV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0;
	String REV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0;
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
	public void setREV_SHARE_RECOV_TEMPLATE_ID_0(
			String rEV_SHARE_RECOV_TEMPLATE_ID_0) {
		REV_SHARE_RECOV_TEMPLATE_ID_0 = rEV_SHARE_RECOV_TEMPLATE_ID_0;
	}
	public void setREV_SHARE_RECOV_TEMPLATE_DESCR_0(
			String rEV_SHARE_RECOV_TEMPLATE_DESCR_0) {
		REV_SHARE_RECOV_TEMPLATE_DESCR_0 = rEV_SHARE_RECOV_TEMPLATE_DESCR_0;
	}
	public void setRVRS_CODE_LOV(String rVRS_CODE_LOV) {
		RVRS_CODE_LOV = rVRS_CODE_LOV;
	}
	public void setBASIS_TYPE_CODE_LOV(String bASIS_TYPE_CODE_LOV) {
		BASIS_TYPE_CODE_LOV = bASIS_TYPE_CODE_LOV;
	}
	public void setFAT_CODE_LOV(String fAT_CODE_LOV) {
		FAT_CODE_LOV = fAT_CODE_LOV;
	}
	public void setCALC_LVL_LOV(String cALC_LVL_LOV) {
		CALC_LVL_LOV = cALC_LVL_LOV;
	}
	public void setFREQ_LOV(String fREQ_LOV) {
		FREQ_LOV = fREQ_LOV;
	}
	public void setREV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0(
			String rEV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0) {
		REV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0 = rEV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0;
	}
	public void setREV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0(
			String rEV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0) {
		REV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0 = rEV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0;
	}
	public void setREV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0(
			String rEV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0) {
		REV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0 = rEV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0;
	}

	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setBASIS_TYPE_CODE_LOV(Stock.GetParameterValue("BASIS_TYPE_CODE_LOV"));
		this.setCALC_LVL_LOV(Stock.GetParameterValue("cALC_LVL_LOV"));
		this.setFAT_CODE_LOV(Stock.GetParameterValue("fAT_CODE_LOV"));
		this.setFREQ_LOV(Stock.GetParameterValue("fREQ_LOV"));
		this.setREV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0(Stock.GetParameterValue("rEV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0"));
		this.setREV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0(Stock.GetParameterValue("rEV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0"));
		this.setREV_SHARE_RECOV_TEMPLATE_DESCR_0(Stock.GetParameterValue("rEV_SHARE_RECOV_TEMPLATE_DESCR_0"));
		this.setREV_SHARE_RECOV_TEMPLATE_ID_0(Stock.GetParameterValue("rEV_SHARE_RECOV_TEMPLATE_ID_0"));
		this.setREV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0(Stock.GetParameterValue("rEV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0"));
		this.setRVRS_CODE_LOV(Stock.GetParameterValue("rVRS_CODE_LOV"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		
		queryString = "?BASIS_TYPE_CODE_LOV="+BASIS_TYPE_CODE_LOV+"&CALC_LVL_LOV="+CALC_LVL_LOV+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&FAT_CODE_LOV="+FAT_CODE_LOV+"&FREQ_LOV="+FREQ_LOV+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_USERNAME="+LOGON_USERNAME+"&REV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0="+REV_SHARE_RECOV_TEMPLATE_DEBIT_METHOD_CODE_0+
				"&REV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0="+REV_SHARE_RECOV_TEMPLATE_DERIVED_RATE_FLAG_0+"&REV_SHARE_RECOV_TEMPLATE_DESCR_0="+REV_SHARE_RECOV_TEMPLATE_DESCR_0+
				"&REV_SHARE_RECOV_TEMPLATE_ID_0="+REV_SHARE_RECOV_TEMPLATE_ID_0+"&REV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0="+REV_SHARE_RECOV_TEMPLATE_STATEMENT_LABEL_0+"&RVRS_CODE_LOV="+RVRS_CODE_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for RTMP service", this.queryString.replaceAll("&", "\n"), false);
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run RTMP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RTMP service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response", "REV_SHARE_RECOV_TEMPLATE_RECOV_STRUC_NAME_PAT_0: "+doc.getElementsByTagName("REV_SHARE_RECOV_TEMPLATE_RECOV_STRUC_NAME_PAT_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String recov_struc = doc.getElementsByTagName("REV_SHARE_RECOV_TEMPLATE_RECOV_STRUC_NAME_PAT_0").item(0).getTextContent();
		String recov_struc_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRTMPInsert")[1], this.REV_SHARE_RECOV_TEMPLATE_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database\nTablename: REV_SHARE_RECOV", false);
			while(queryResultSet.next()){
				recov_struc_db = queryResultSet.getString("RECOV_STRUC_NAME_PAT");
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nRECOV_STRUC_NAME_PAT: "+queryResultSet.getString("RECOV_STRUC_NAME_PAT")+
					"\nSTATEMENT_LABEL: "+queryResultSet.getString("STATEMENT_LABEL"), false);
			}
			if(recov_struc.equalsIgnoreCase(recov_struc_db)){
				Reporter.logEvent(Status.PASS, "Comparing and validating RECOV_STRUC_NAME_PAT from Response and Database",
						"Both values are same as expected"+"\nFrom Database: "+recov_struc_db+"\nFrom Response: "+recov_struc, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating RECOV_STRUC_NAME_PAT from Response and Database",
						"Both values are not same "+"\nFrom Database: "+recov_struc_db+"\nFrom Response: "+recov_struc, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void FlushDataCreated(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRTMPInsertDelete")[1], this.REV_SHARE_RECOV_TEMPLATE_ID_0);
	}
}
