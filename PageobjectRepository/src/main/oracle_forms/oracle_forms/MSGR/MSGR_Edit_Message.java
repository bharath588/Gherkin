package pageobject.MSGR;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class MSGR_Edit_Message {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MSGR_Edit_Message_Type_Notification_Rules";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String TOP_HEADER_RV_LOW_VALUE_0;
	String MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0;
	
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
	public void setTOP_HEADER_RV_LOW_VALUE_0(String tOP_HEADER_RV_LOW_VALUE_0) {
		TOP_HEADER_RV_LOW_VALUE_0 = tOP_HEADER_RV_LOW_VALUE_0;
	}
	public void setMSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0(
			String mSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0) {
		MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0 = mSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0;
	}
	
	String MSG_TYPE_hash_MSG_NOTIFICATION_RULE_SEVERITY_LEVEL_0;
	String MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_TEXT_0;
	String MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MT_MSG_CODE_0;
	String HEADER_PGM_NAME_2; 
	
	public String getMSG_TYPE_hash_MSG_NOTIFICATION_RULE_SEVERITY_LEVEL_0() {
		return MSG_TYPE_hash_MSG_NOTIFICATION_RULE_SEVERITY_LEVEL_0;
	}
	public String getMSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_TEXT_0() {
		return MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_TEXT_0;
	}
	public String getMSG_TYPE_hash_MSG_NOTIFICATION_RULE_MT_MSG_CODE_0() {
		return MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MT_MSG_CODE_0;
	}
	public String getHEADER_PGM_NAME_2() {
		return HEADER_PGM_NAME_2;
	}
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setMSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0(Stock.GetParameterValue("MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0"));
		 this.setTOP_HEADER_RV_LOW_VALUE_0(Stock.GetParameterValue("TOP_HEADER_RV_LOW_VALUE_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&TOP_HEADER_RV_LOW_VALUE_0="+TOP_HEADER_RV_LOW_VALUE_0+
				 "&MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0="+MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_CD_SEL_CHKBOX_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false" ;
		 Reporter.logEvent(Status.INFO, "Prepare test data for MSGR EDIT service", this.queryString.replaceAll("&", "\n"), false); 
	}
	
	public void runService() throws SQLException {
		try {
				this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
				serviceURL += this.queryString;
				docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setIgnoringComments(true);
				docBuilderFactory.setIgnoringElementContentWhitespace(true);
				docBuilderFactory.setNamespaceAware(true);
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(serviceURL);
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run  MSGR_Edit_Message_Type_Notification_Rules service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MSGR_Edit_Message_Type_Notification_Rules service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.PASS, "Values From response: ","HEADER_PGM_NAME_2: "+doc.getElementsByTagName("HEADER_PGM_NAME_2").item(0).getTextContent()+
		"\nMSG_TYPE: "+doc.getElementsByTagName("MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MSG_TEXT_0").item(0).getTextContent()+
		"\nMSG_CODE: "+doc.getElementsByTagName("MSG_TYPE_hash_MSG_NOTIFICATION_RULE_MT_MSG_CODE_0").item(0).getTextContent()+
		"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForViewMsgType")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
			Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: MSG_TYPE#MSG_NOTIFICATION_RULE ", "Total Number of Records: \n"+"Expected: Query should return more than 1 record\nActual: "+ DB.getRecordSetCount(queryResultSet), false);
		}
  
	}

}
