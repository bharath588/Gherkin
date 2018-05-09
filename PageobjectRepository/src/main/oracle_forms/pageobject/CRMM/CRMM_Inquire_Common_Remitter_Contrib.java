package pageobject.CRMM;

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

public class CRMM_Inquire_Common_Remitter_Contrib {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CRMM_Inquire_Common_Remitter_Contributions";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				 "&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CRMM service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run CRMM service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run CRMM service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","SDIO_ID: "+doc.getElementsByTagName("CM_REMIT_MNTY_MAPPING_SOURCE_SDIO_ID_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}		
	}
	
	public void validateInDatabase()throws SQLException{
		String sdio_id_db=null;
		String sdio_id = doc.getElementsByTagName("CM_REMIT_MNTY_MAPPING_SOURCE_SDIO_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCRMM")[1], this.GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database\nTableName: CM_REMIT_mnty_mapping", false);
			while(queryResultSet.next()){
			sdio_id_db = queryResultSet.getString("SOURCE_SDIO_ID");			
			Reporter.logEvent(Status.INFO, "Validate Database: ", "GA_ID: "+queryResultSet.getString("SOURCE_GA_ID")+
				"\nSDIO_ID: "+queryResultSet.getString("SOURCE_SDIO_ID")+
				"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+"\n", false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "Validating from database: ", "No record found in databse", false);
		}
		if(sdio_id.equalsIgnoreCase(sdio_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating SDIO ID from Response and Database", "Both the values are same as Expected"+
					"\nSDIO_IDr: "+"\nFrom Response: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
		}else{
//			Reporter.logEvent(Status.FAIL, "Comparing and Validating SDIO ID from Response and Database", "Both the values are not same "+
	//				"\nSDIO_IDr: "+"\nFrom Response: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
		}
	}
}
