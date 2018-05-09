package pageobject.PNLT;

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

public class PNLT_Handles_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PNLT_HANDLES_INFO_RELATED_TO_DISBURSEMENT_TYPES";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String STEP1_QRY_EV_ID_0;
	
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
	public void setSTEP1_QRY_EV_ID_0(String sTEP1_QRY_EV_ID_0) {
		STEP1_QRY_EV_ID_0 = sTEP1_QRY_EV_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setSTEP1_QRY_EV_ID_0(Stock.GetParameterValue("STEP1_QRY_EV_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&STEP1_QRY_EV_ID_0="+STEP1_QRY_EV_ID_0+
				  "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";;
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for PNLT service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run  PNLT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run PNLT service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);//11
			Reporter.logEvent(Status.INFO, "From Response ", "CONTROL_DISPLAY_STEP_5_0" +doc.getElementsByTagName("CONTROL_DISPLAY_STEP_5_0").item(0).getTextContent()+
					"\nDISB_DETAIL_SUMM_GA_ID_0" +doc.getElementsByTagName("DISB_DETAIL_SUMM_GA_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPNLT")[1], this.STEP1_QRY_EV_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Event generated in Database", "Event generated in DB\nTableName: STEP", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "EV_ID: "+queryResultSet.getString("EV_ID")+
						"\nSETY_CODE: "+queryResultSet.getString("SETY_CODE")+
						"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE")+
						"\nCOMPLTN_DATE: "+queryResultSet.getString("COMPLTN_DATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Event generated in Database", "Event is not generated in DB\nTableName: STEP", false);
		}
	}
	
	public void resetData() throws SQLException{

		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPNLTReset")[1], this.STEP1_QRY_EV_ID_0);
	}
}