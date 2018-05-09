package pageobject.SBTY;

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

public class SBTY_Subject_Type {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SBTY_subject_type";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SJTY1_CODE_15;
	String SJTY1_DESCR_15;
	
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
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setSJTY1_DESCR_15(Stock.GetParameterValue("SJTY1_DESCR_15"));
		this.setSJTY1_CODE_15(Stock.GetParameterValue("SJTY1_CODE_15"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&SJTY1_CODE_15="+SJTY1_CODE_15+"&SJTY1_DESCR_15="+SJTY1_DESCR_15
				+"&numOfRowsInTable=20&json=false&handlePopups=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SBTY service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void setSJTY1_CODE_15(String sJTY1_CODE_15) {
		SJTY1_CODE_15 = sJTY1_CODE_15;
	}
	public void setSJTY1_DESCR_15(String sJTY1_DESCR_15) {
		SJTY1_DESCR_15 = sJTY1_DESCR_15;
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
			Reporter.logEvent(Status.PASS, "Run SBTY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SBTY service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","SJTY1_CODE_0: "+doc.getElementsByTagName("SJTY1_CODE_0").item(0).getTextContent()+
					"\nSJTY1_DESCR_0: "+doc.getElementsByTagName("SJTY1_DESCR_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRCMT")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: SUBJ_TYPE", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "Total number of records: "+DB.getRecordSetCount(queryResultSet), false);
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
}
