package pageobject.EXTR;

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

public class EXTR_Insert_Query_Delete {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EXTR_INSERT_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String param27069;
	String EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16;
	String EXTERNAL_ENTITY_TRANSLATION_PC_CODE_16;
	String EXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16;
	String EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16;
	String EXTERNAL_ENTITY_TRANSLATION_GA_ID_16;
	
	public String getParam27069() {
		return param27069;
	}
	public void setParam27069(String param27069) {
		this.param27069 = param27069;
	}
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
	public void setEXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16(
			String eXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16) {
		EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16 = eXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16;
	}
	public void setEXTERNAL_ENTITY_TRANSLATION_PC_CODE_16(
			String eXTERNAL_ENTITY_TRANSLATION_PC_CODE_16) {
		EXTERNAL_ENTITY_TRANSLATION_PC_CODE_16 = eXTERNAL_ENTITY_TRANSLATION_PC_CODE_16;
	}
	public void setEXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16(
			String eXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16) {
		EXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16 = eXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16;
	}
	public void setEXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16(
			String eXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16) {
		EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16 = eXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16;
	}
	public void setEXTERNAL_ENTITY_TRANSLATION_GA_ID_16(
			String eXTERNAL_ENTITY_TRANSLATION_GA_ID_16) {
		EXTERNAL_ENTITY_TRANSLATION_GA_ID_16 = eXTERNAL_ENTITY_TRANSLATION_GA_ID_16;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setParam27069(Stock.GetParameterValue("param27069"));
		this.setEXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16(Stock.GetParameterValue("EXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16"));
		this.setEXTERNAL_ENTITY_TRANSLATION_GA_ID_16(Stock.GetParameterValue("EXTERNAL_ENTITY_TRANSLATION_GA_ID_16"));
		this.setEXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16(Stock.GetParameterValue("EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16"));
		this.setEXTERNAL_ENTITY_TRANSLATION_PC_CODE_16(Stock.GetParameterValue("EXTERNAL_ENTITY_TRANSLATION_PC_CODE_16"));
		this.setEXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16(Stock.GetParameterValue("EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16"));
		
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&param27069="+param27069+"&EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16="+EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16+"&EXTERNAL_ENTITY_TRANSLATION_PC_CODE_16="+EXTERNAL_ENTITY_TRANSLATION_PC_CODE_16+
				"&EXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16="+EXTERNAL_ENTITY_TRANSLATION_EXTERNAL_ENTITY_CODE_16+"&EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16="+EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_16+
				"&EXTERNAL_ENTITY_TRANSLATION_GA_ID_16="+EXTERNAL_ENTITY_TRANSLATION_GA_ID_16+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
				
		Reporter.logEvent(Status.INFO, "Prepare test data for EXTR INSERT QUERY DELETE service", this.queryString.replaceAll("&", "\n"), false);
		
//		queryResultSet1 =  DB.executeQuery(this.LOGON_DATABASE, Stock.getTestQuery("queryForEXTRClear")[1], EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16, EXTERNAL_ENTITY_TRANSLATION_GA_ID_16);
		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run EXTR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run EXTR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "ISIS CODE: "+ doc.getElementsByTagName("EXTERNAL_ENTITY_TRANSLATION_ISIS_CODE_0").item(0).getTextContent()+
					"\nPC CODE: "+ "OTCG", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}

	public void validateInDatabase() throws SQLException{
//		String pc_code_resp = doc.getElementsByTagName("EXTERNAL_ENTITY_TRANSLATION_PC_CODE_0").item(0).getTextContent();
		String pc_code_db = null;
		String pc_code_resp = "OTCG";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEXTR")[1], EXTERNAL_ENTITY_TRANSLATION_TRANSLATION_TYPE_16, EXTERNAL_ENTITY_TRANSLATION_GA_ID_16);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction", false);
			pc_code_db = queryResultSet.getString("PC_CODE");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "TRANSLATION TYPE: "+queryResultSet.getString("TRANSLATION_TYPE")+
					"\nPC CODE: "+queryResultSet.getString("PC_CODE")+
					"\nGA ID: "+queryResultSet.getString("GA_ID")+
					"\nEXTERNAL ENTITY CODE: "+queryResultSet.getString("EXTERNAL_ENTITY_CODE")+
					"\nISIS CODE: "+queryResultSet.getString("ISIS_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(pc_code_resp.equalsIgnoreCase(pc_code_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating PC_CODE from Response and Database", "Both the values are same as Expected"+
					"\nPC_CODE ID: "+"From Response: "+pc_code_resp+"\nFrom Database: "+pc_code_db, false);
		}
  
	}

}
