package pageobject.ABLT;

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

public class ABLT_Add_Bulletin {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19ABLT_Add_Bulletin";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String param9200;
	String BULLETIN_NAME_0;
	String CB_D_FUNCTION_0;
	String CB_D_RESP_USER_0;
	String CB_INFO_INFORMATION_0;
	String BULLETIN_ID_0;
	String CB_D_DISPLAY_TERMDATE_0;
	String CB_D_DISPLAY_TERMDATE_0_X1;
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
	public void setParam9200(String param9200) {
		this.param9200 = param9200;
	}
	public void setBULLETIN_NAME_0(String bULLETIN_NAME_0) {
		BULLETIN_NAME_0 = bULLETIN_NAME_0;
	}
	public void setCB_D_FUNCTION_0(String cB_D_FUNCTION_0) {
		CB_D_FUNCTION_0 = cB_D_FUNCTION_0;
	}
	public void setCB_D_RESP_USER_0(String cB_D_RESP_USER_0) {
		CB_D_RESP_USER_0 = cB_D_RESP_USER_0;
	}
	public void setCB_INFO_INFORMATION_0(String cB_INFO_INFORMATION_0) {
		CB_INFO_INFORMATION_0 = cB_INFO_INFORMATION_0;
	}
	public void setBULLETIN_ID_0(String bULLETIN_ID_0) {
		BULLETIN_ID_0 = bULLETIN_ID_0;
	}
	public void setCB_D_DISPLAY_TERMDATE_0(String cB_D_DISPLAY_TERMDATE_0) {
		CB_D_DISPLAY_TERMDATE_0 = cB_D_DISPLAY_TERMDATE_0;
	}
	public void setCB_D_DISPLAY_TERMDATE_0_X1(String cB_D_DISPLAY_TERMDATE_0_X1) {
		CB_D_DISPLAY_TERMDATE_0_X1 = cB_D_DISPLAY_TERMDATE_0_X1;
	}
	
	public void setServiceParameters() throws SQLException
	{
	//	 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam9200(Stock.GetParameterValue("param9200"));
		 this.setBULLETIN_ID_0(Stock.GetParameterValue("BULLETIN_ID_0"));
		 this.setBULLETIN_NAME_0(Stock.GetParameterValue("BULLETIN_NAME_0"));
		 this.setCB_D_DISPLAY_TERMDATE_0(Stock.GetParameterValue("CB_D_DISPLAY_TERMDATE_0"));
		 this.setCB_D_DISPLAY_TERMDATE_0_X1(Stock.GetParameterValue("CB_D_DISPLAY_TERMDATE_0_X1"));
		 this.setCB_D_FUNCTION_0(Stock.GetParameterValue("CB_D_FUNCTION_0"));
		 this.setCB_D_RESP_USER_0(Stock.GetParameterValue("CB_D_RESP_USER_0"));
		 this.setCB_INFO_INFORMATION_0(Stock.GetParameterValue("CB_INFO_INFORMATION_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&param9200="+param9200+"&BULLETIN_NAME_0="+BULLETIN_NAME_0+"&CB_D_FUNCTION_0="+CB_D_FUNCTION_0+
				 "&CB_D_RESP_USER_0="+CB_D_RESP_USER_0+"&CB_INFO_INFORMATION_0="+CB_INFO_INFORMATION_0+"&BULLETIN_ID_0="+BULLETIN_ID_0+
				 "&CB_D_DISPLAY_TERMDATE_0="+CB_D_DISPLAY_TERMDATE_0+"&CB_D_DISPLAY_TERMDATE_0_X1="+CB_D_DISPLAY_TERMDATE_0_X1+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for ABLT service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  ABLT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run  ABLT service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		org.w3c.dom.NodeList list = doc.getElementsByTagName("GQ19ABLT_Add_BulletinArrayItem");
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","Total no. of items: "+ list.getLength()		
					+"\n",false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase()throws SQLException{
		String id = this.BULLETIN_ID_0;
		String ter_date = this.CB_D_DISPLAY_TERMDATE_0_X1;
		String term_date_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForABLT")[1], id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: BULLETIN", false);
			while(queryResultSet.next()){			
			Reporter.logEvent(Status.INFO, "Values From Database: ","ID: "+queryResultSet.getString("ID")+
					"\nSUBJECT_TYPE: "+queryResultSet.getString("SUBJECT_TYPE")+
					"\nSUBJECT_ID: "+queryResultSet.getString("SUBJECT_ID"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForABLT2")[1], id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: BULLETIN_DETAIL", false);
			while(queryResultSet.next()){	
			term_date_db = 	queryResultSet.getString("DISPLAY_TERMDATE").split(" ")[0];
			Reporter.logEvent(Status.INFO, "Values From Database: ","ID: "+queryResultSet.getString("BULLETIN_ID")+
					"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME")+
					"\nTERM_DATE: "+queryResultSet.getString("DISPLAY_TERMDATE"), false);
			}
			
			/*try {
				if(DateCompare.compareTwoDates(ter_date, term_date_db)){
					Reporter.logEvent(Status.PASS, "Comapring and Validating TERM DATE from Input and DB", "Both values are same as expected"+
				"\nFrom Input: "+ter_date+"\nFrom DB: "+term_date_db, false);
				}else{
					Reporter.logEvent(Status.FAIL, "Comapring and Validating TERM DATE from Input and DB", "Both values are not same as expected"+
							"\nFrom Input: "+ter_date+"\nFrom DB: "+term_date_db, false);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
		}
	
	public void ResetData() throws SQLException{
		String id = this.BULLETIN_ID_0;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForABLTReset1")[1], id);
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForABLTReset2")[1], id);
	}
}