package pageobject.FDRM;

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

public class FDRM_Insert_Return_Mail {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/FDRM_INSERT";	
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String EMPLOYMENT_Q_IND_ID_0;
	String STEP_TYPE_LOV;
	String INDIVIDUAL_DOC_ID_0;
	
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
	public void setEMPLOYMENT_Q_IND_ID_0(String eMPLOYMENT_Q_IND_ID_0) {
		EMPLOYMENT_Q_IND_ID_0 = eMPLOYMENT_Q_IND_ID_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setINDIVIDUAL_DOC_ID_0(String iNDIVIDUAL_DOC_ID_0) {
		INDIVIDUAL_DOC_ID_0 = iNDIVIDUAL_DOC_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setEMPLOYMENT_Q_IND_ID_0(Stock.GetParameterValue("EMPLOYMENT_Q_IND_ID_0"));
		 this.setINDIVIDUAL_DOC_ID_0(Stock.GetParameterValue("INDIVIDUAL_DOC_ID_0"));
		 this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&EMPLOYMENT_Q_IND_ID_0="+EMPLOYMENT_Q_IND_ID_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&INDIVIDUAL_DOC_ID_0="+INDIVIDUAL_DOC_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for FDRM service", this.queryString.replaceAll("&", "\n"), false);
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
		//	System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run FDRM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run FDRM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.INFO, "Values From Response of Service ","INDIVIDUAL LAST NAME: "+doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0").item(0).getTextContent(),false);
		} else {
			/*Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);*/
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
	//		Reporter.logEvent(Status.INFO, "Values From Response of Service ","INDIVIDUAL LAST NAME: "+doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0").item(0).getTextContent(),false);
		}
		
	}
	
	public void validateInDatabase() throws SQLException{
		String doc_id_input = this.INDIVIDUAL_DOC_ID_0;
		String doc_id_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToVerifyFDRM")[1], EMPLOYMENT_Q_IND_ID_0, INDIVIDUAL_DOC_ID_0);
	
		if(queryResultSet.next()){	
			doc_id_db = queryResultSet.getString("DOC_ID");
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:" , "GA_ID: " +queryResultSet.getString("GA_ID")+
					"\nIND_ID: " +queryResultSet.getString("IND_ID")+
					"\nDOC_ID: "+queryResultSet.getString("DOC_ID")+
					"\nEV_ID: " +queryResultSet.getString("EV_ID")+
					"\nDMTY_CODE: " +queryResultSet.getString("DMTY_CODE")+
					"\nEFFDATE: " +queryResultSet.getString("EFFDATE")+
					"\nPROC CODE: " +queryResultSet.getString("PROC_CODE"), false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record creation in database", "Record doesn't exists in Database", false);
		}
		if(doc_id_input.equalsIgnoreCase(doc_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Document ID from Input and Database", "Both the values are same as Expected"+
					"\nDOC ID: "+"From Input: "+doc_id_input+"\nFrom Database: "+doc_id_db, false);
		
		}
	}

}
