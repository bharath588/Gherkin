package pageobject.AGGP;

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

public class AGG_Insert_New_Acct_Groupings {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AGGP_Insert_New_Acct_Grouping";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0; 
	String AG1_TYPE_CODE_1; 
	String AG1_STATUS_EFFDATE_1 ;
	String AGIL1_GA_ID_LOV4 ;
	String AGIL1_DESIGNATED_GA_IND_0;
	String AGIL1_EFFDATE_0;
	
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
	public void setAG1_TYPE_CODE_1(String aG1_TYPE_CODE_1) {
		AG1_TYPE_CODE_1 = aG1_TYPE_CODE_1;
	}
	public void setAG1_STATUS_EFFDATE_1(String aG1_STATUS_EFFDATE_1) {
		AG1_STATUS_EFFDATE_1 = aG1_STATUS_EFFDATE_1;
	}
	public void setAGIL1_GA_ID_LOV4(String aGIL1_GA_ID_LOV4) {
		AGIL1_GA_ID_LOV4 = aGIL1_GA_ID_LOV4;
	}
	public void setAGIL1_DESIGNATED_GA_IND_0(String aGIL1_DESIGNATED_GA_IND_0) {
		AGIL1_DESIGNATED_GA_IND_0 = aGIL1_DESIGNATED_GA_IND_0;
	}
	public void setAGIL1_EFFDATE_0(String aGIL1_EFFDATE_0) {
		AGIL1_EFFDATE_0 = aGIL1_EFFDATE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setAG1_STATUS_EFFDATE_1(Stock.GetParameterValue("AG1_STATUS_EFFDATE_1"));
		 this.setAG1_TYPE_CODE_1(Stock.GetParameterValue("AG1_TYPE_CODE_1"));
		 this.setAGIL1_DESIGNATED_GA_IND_0(Stock.GetParameterValue("AGIL1_DESIGNATED_GA_IND_0"));
		 this.setAGIL1_EFFDATE_0(Stock.GetParameterValue("AGIL1_EFFDATE_0"));
		 this.setAGIL1_GA_ID_LOV4(Stock.GetParameterValue("AGIL1_GA_ID_LOV4"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&AG1_TYPE_CODE_1="+AG1_TYPE_CODE_1+"&AG1_STATUS_EFFDATE_1="+AG1_STATUS_EFFDATE_1+"&AGIL1_GA_ID_LOV4="+AGIL1_GA_ID_LOV4+
				 "&AGIL1_DESIGNATED_GA_IND_0="+AGIL1_DESIGNATED_GA_IND_0+"&AGIL1_EFFDATE_0="+AGIL1_EFFDATE_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for AGGP service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run  AGGP service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run AGGP service", "Running Failed:", false);
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
	}
	
	public void validateInDatabase() throws SQLException{
		String type_codedb =null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAddingNewAcctGrp")[1], this.GET_GA_GA_ID_0.split("-")[0], this.AG1_TYPE_CODE_1);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists in Database", false);
			while(queryResultSet.next()){
				type_codedb = queryResultSet.getString("TYPE_CODE");
				Reporter.logEvent(Status.INFO, "From Database: \nTable name: ACCT_GRPG", "ID: "+queryResultSet.getString("ID")+
						"\nTYPECODE: "+queryResultSet.getString("TYPE_CODE")+
						"\nSTATUS_CODE: "+queryResultSet.getString("STATUS_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("STATUS_EFFDATE")+
						"\nGCID: "+queryResultSet.getString("GC_ID"), false);
			}
			
			if(type_codedb.equalsIgnoreCase(this.AG1_TYPE_CODE_1)){
				Reporter.logEvent(Status.PASS, "Comparing and validating Account Type code from input and Response", "Both the values are same as expected\n"+
			"From Input; "+this.AG1_TYPE_CODE_1+"\nFrom database: "+type_codedb, false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Comparing and validating Account Type code from input and Response", "Both the values are not same \n"+
						"From Input; "+this.AG1_TYPE_CODE_1+"\nFrom database: "+type_codedb, false);
			}
		
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists for Account Transaction", "Records does not exists in Database", false);
		}		
  
	}
}
