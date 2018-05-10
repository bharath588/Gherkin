package pageobject.DOCU;

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

public class DOCU_Insert {


	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DOCU_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	
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
	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_PROD_ID_0="+GET_PROD_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DOCU service", this.queryString.replaceAll("&", "\n"), false);		
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
			Reporter.logEvent(Status.PASS, "Run DOCU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DOCU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response ", "CONTROL CODE: "+doc.getElementsByTagName("CFG_CONTROL_CODE_P2_0").item(0).getTextContent()+
					"\nCONTROL ID: "+doc.getElementsByTagName("CFG_CONTROL_DESCR_P2_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prod_id = this.GET_PROD_ID_0;
		String dmty_code =	doc.getElementsByTagName("CFG_CONTROL_CODE_P2_0").item(0).getTextContent();	
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDOCU")[1], prod_id, dmty_code);
		
		if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Event\nTable name: DOC_SEL\nTotal number of records: "+DB.getRecordSetCount(queryResultSet), false);
				while(queryResultSet.next()){
					Reporter.logEvent(Status.INFO, "From Database ", "DMFT_ID: "+queryResultSet.getString("DMFT_ID")+
							"\nPROD_ID: "+queryResultSet.getString("PROD_ID")+
							"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
				}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void cleanUpDB() throws SQLException{
		String prod_id = doc.getElementsByTagName("CFG_CONTROL_CODE_P2_0").item(0).getTextContent();
		String dmty_code =	doc.getElementsByTagName("CFG_CONTROL_DESCR_P2_0").item(0).getTextContent();	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDOCU")[1], prod_id, dmty_code);
		if(DB.getRecordSetCount(queryResultSet)>0){		
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDOCUClean")[1], prod_id, dmty_code);
			Reporter.logEvent(Status.INFO, "Deleting records in Database", "Records successfully deleted", false);
		}
	}
	

}
