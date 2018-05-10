package pageobject.SUCU;

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

public class SUCU_Customize_by_Standard {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SUCU_Customize_at_Standard";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String CONTROL_SELECTION_0_X1;
	String SELECTION_ORIG_LABEL_LOV0;
	String SELECTION_SUBTYPE_ID_0;
	String LOV36;
	
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public void setSELECTION_ORIG_LABEL_LOV0(String sELECTION_ORIG_LABEL_LOV0) {
		SELECTION_ORIG_LABEL_LOV0 = sELECTION_ORIG_LABEL_LOV0;
	}
	public void setSELECTION_SUBTYPE_ID_0(String sELECTION_SUBTYPE_ID_0) {
		SELECTION_SUBTYPE_ID_0 = sELECTION_SUBTYPE_ID_0;
	}
	public void setLOV36(String lOV36) {
		LOV36 = lOV36;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setLOV36(Stock.GetParameterValue("LOV36"));		
		this.setSELECTION_ORIG_LABEL_LOV0(Stock.GetParameterValue("SELECTION_ORIG_LABEL_LOV0"));
		this.setSELECTION_SUBTYPE_ID_0(Stock.GetParameterValue("SELECTION_SUBTYPE_ID_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&CONTROL_SELECTION_0_X1=" +CONTROL_SELECTION_0_X1 
				+"&SELECTION_ORIG_LABEL_LOV0="+SELECTION_ORIG_LABEL_LOV0+"&SELECTION_SUBTYPE_ID_0="+SELECTION_SUBTYPE_ID_0
				+"&LOV36="+LOV36+"&numOfRowsInTable=20&json=false&handlePopups=false";		
			
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SUCU by STANDARD service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SUCU_Customize_by_Standard service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SUCU_Customize_by_Standard service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		String statusBarMsgs = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "Validate response ", "StatusBarMesssages: "+statusBarMsgs, false);
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase(String transaction) throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSUCUbyStd")[1], SELECTION_SUBTYPE_ID_0, transaction);
		if(queryResultSet.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.PASS, "From DATABASE:\nTable: TXN_TRANSLATION","ORIG_LABEL: " +queryResultSet.getString("ORIG_LABEL")+
					"\nSUBTYPE_ID: " +queryResultSet.getString("SUBTYPE_ID")+
					"\nPROD_ID: " +queryResultSet.getString("PROD_ID")+
					"\nTRANSACTION: " +queryResultSet.getString("TRANSACTION")+
					"\nTRANSLATION: " +queryResultSet.getString("TRANSLATION")+
					"\nGA_ID: " +queryResultSet.getString("GA_ID"), false);		
		}
	}

}
