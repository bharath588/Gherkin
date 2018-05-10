package pageobject.RESN_Disb_Reason;

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

public class RESN_Disb_Reason {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RESN_Disbursement_Reason_Information";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String DSRS1_CODE_0;
	String DSRS1_DESCR_0;
	String LOV_DISB_CAT_CD;
	String DSRS1_DISB_FOR_INELIG_FUNDS_IND_0;
	String DSRS1_SPECIAL_HANDLING_DEFAULT_0;
	
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
	public void setDSRS1_CODE_0(String dSRS1_CODE_0) {
		DSRS1_CODE_0 = dSRS1_CODE_0;
	}
	public void setDSRS1_DESCR_0(String dSRS1_DESCR_0) {
		DSRS1_DESCR_0 = dSRS1_DESCR_0;
	}
	public void setLOV_DISB_CAT_CD(String lOV_DISB_CAT_CD) {
		LOV_DISB_CAT_CD = lOV_DISB_CAT_CD;
	}
	public void setDSRS1_DISB_FOR_INELIG_FUNDS_IND_0(
			String dSRS1_DISB_FOR_INELIG_FUNDS_IND_0) {
		DSRS1_DISB_FOR_INELIG_FUNDS_IND_0 = dSRS1_DISB_FOR_INELIG_FUNDS_IND_0;
	}
	public void setDSRS1_SPECIAL_HANDLING_DEFAULT_0(
			String dSRS1_SPECIAL_HANDLING_DEFAULT_0) {
		DSRS1_SPECIAL_HANDLING_DEFAULT_0 = dSRS1_SPECIAL_HANDLING_DEFAULT_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setDSRS1_CODE_0(Stock.GetParameterValue("DSRS1_CODE_0"));
		 this.setDSRS1_DESCR_0(Stock.GetParameterValue("DSRS1_DESCR_0"));
		 this.setDSRS1_DISB_FOR_INELIG_FUNDS_IND_0(Stock.GetParameterValue("DSRS1_DISB_FOR_INELIG_FUNDS_IND_0"));
		 this.setDSRS1_SPECIAL_HANDLING_DEFAULT_0(Stock.GetParameterValue("DSRS1_SPECIAL_HANDLING_DEFAULT_0"));
		 this.setLOV_DISB_CAT_CD(Stock.GetParameterValue("LOV_DISB_CAT_CD"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&DSRS1_CODE_0="+DSRS1_CODE_0+"&DSRS1_DESCR_0="+DSRS1_DESCR_0+"&LOV_DISB_CAT_CD="+LOV_DISB_CAT_CD+
				 "&DSRS1_DISB_FOR_INELIG_FUNDS_IND_0="+DSRS1_DISB_FOR_INELIG_FUNDS_IND_0+
				 "&DSRS1_SPECIAL_HANDLING_DEFAULT_0="+DSRS1_SPECIAL_HANDLING_DEFAULT_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for RESn service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run RESN_Disb_Reason_Info service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run RESN_Disb_Reason_Info service", "Running Failed:", false);
			}
			
		}
	
	public void validateOutput() throws SQLException{
		String code = null;
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
	//		Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}

		Reporter.logEvent(Status.INFO, "Validating from response", "Today's Date: " +doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE1_0").item(0).getTextContent() + "\nCATEGORY_CODE: " + doc.getElementsByTagName("DSRS1_DISB_CAT_CODE_0").item(0).getTextContent(), false);
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbReason")[1],this.DSRS1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Enterring Disb Code into EASY", " Done!"+"\nTable Name: DISB_RSN", false);
			while(queryResultSet.next()){				
				code = queryResultSet.getString("CODE");
				String descr = queryResultSet.getString("DESCR");
				Reporter.logEvent(Status.INFO, "Values from Database:", "CODE: "+code+"\nDESCR:" + descr, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(this.DSRS1_CODE_0.equalsIgnoreCase(code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating RESN ID from Input and Database", "Both the values are same as Expected"+
					"\nRESN ID: "+"From Input: "+this.DSRS1_CODE_0+"\nFrom Database: "+code, false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbReason2")[1],this.DSRS1_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Enterring Disb Code into EASY", " Done!"+"\nDISB_RSN_CATEGORY", false);
			while(queryResultSet.next()){				
				code = queryResultSet.getString("DISB_CAT_CODE");
				String descr = queryResultSet.getString("DSRS_CODE");
				Reporter.logEvent(Status.INFO, "Values from Database:", "DISB_CAT_CODE: "+code+"\nDSRS_CODE:" + descr, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void resetData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbReasonDelete1")[1],this.DSRS1_CODE_0);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbReasonDelete2")[1],this.DSRS1_CODE_0);
	}
}
