package pageobject.STPB;

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

public class STPB_Req_Plsm_Status {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STPB_REQ_PLSM_STATUS";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_BEGIN_GA_ID_0;
	
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
	public void setSELECTION_BEGIN_GA_ID_0(String sELECTION_BEGIN_GA_ID_0) {
		SELECTION_BEGIN_GA_ID_0 = sELECTION_BEGIN_GA_ID_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSELECTION_BEGIN_GA_ID_0(Stock.GetParameterValue("SELECTION_BEGIN_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_BEGIN_GA_ID_0="+SELECTION_BEGIN_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STPB service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run STPB service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STPB service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response ", "GA_ID: "+doc.getElementsByTagName("SELECTION_END_GA_ID_0").item(0).getTextContent()+
					"\nAG_ID: "+doc.getElementsByTagName("STMT_BATCH_AG_ID_0").item(0).getTextContent()+
					"\nEFFDATE: "+doc.getElementsByTagName("STMT_BATCH_BEGIN_EFFDATE_0").item(0).getTextContent()+
					"\nDMTY_CODE: "+doc.getElementsByTagName("STMT_BATCH_DMTY_CODE_0").item(0).getTextContent()+
					"\nHAND_CODE: "+doc.getElementsByTagName("STMT_BATCH_HAND_CODE_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ag_id = doc.getElementsByTagName("STMT_BATCH_AG_ID_0").item(0).getTextContent();
		String ag_id_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTPB")[1], ag_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			if(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Event", false);
				ag_id_db = queryResultSet.getString("AG_ID");
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: STMT_TO_DO", "AG_ID: "+queryResultSet.getString("AG_ID")+
					"\nDMTY_CODE: "+queryResultSet.getString("DMTY_CODE")+					
					"\nEFFDATE: "+queryResultSet.getString("BEGIN_EFFDATE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(ag_id.equalsIgnoreCase(ag_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating AG_ID from Input and Database", "Both the values are same as Expected"+
					"\nAG_ID: "+"From Response: "+ag_id+"\nFrom Database: "+ag_id_db, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Comparing and Validating AG_ID from Input and Database", "Both the values are not same "+
					"\nAG_ID: "+"From Response: "+ag_id+"\nFrom Database: "+ag_id_db, false);
		}
	}

}
