package pageobject.SIID;

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

public class SIID_Std_Invopt_Ibbotson_Dtl {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GQ19SIID_standard_invopt_ibbotson_dtl";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String STD_DETAIL_DURATION_0;
	String STD_DETAIL_DURATION_0_X1;
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
	public void setSTD_DETAIL_DURATION_0(String sTD_DETAIL_DURATION_0) {
		STD_DETAIL_DURATION_0 = sTD_DETAIL_DURATION_0;
	}
	public void setSTD_DETAIL_DURATION_0_X1(String sTD_DETAIL_DURATION_0_X1) {
		STD_DETAIL_DURATION_0_X1 = sTD_DETAIL_DURATION_0_X1;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setSTD_DETAIL_DURATION_0(Stock.GetParameterValue("STD_DETAIL_DURATION_0"));
		 this.setSTD_DETAIL_DURATION_0_X1(Stock.GetParameterValue("STD_DETAIL_DURATION_0_X1"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&STD_DETAIL_DURATION_0="+STD_DETAIL_DURATION_0+"&STD_DETAIL_DURATION_0_X1="+STD_DETAIL_DURATION_0_X1+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for SIID service", this.queryString.replaceAll("&", "\n"), false);
		
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
				Reporter.logEvent(Status.PASS, "Run  SIID service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run  SIID service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		org.w3c.dom.NodeList list = doc.getElementsByTagName("GQ19SIID_standard_invopt_ibbotson_dtlArrayItem");
		
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
		String rate = this.STD_DETAIL_DURATION_0_X1;
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIID")[1], rate);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records inserted in Database\nTable Name: std_invopt_ibbotson_detail", false);
			while(queryResultSet.next()){			
			Reporter.logEvent(Status.INFO, "Values From Database: ","SDIO_ID: "+queryResultSet.getString("SDIO_ID")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nDURATION: "+queryResultSet.getString("DURATION"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
		
}
