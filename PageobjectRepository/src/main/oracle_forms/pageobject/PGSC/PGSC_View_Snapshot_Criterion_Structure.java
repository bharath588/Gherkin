package pageobject.PGSC;

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

public class PGSC_View_Snapshot_Criterion_Structure {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PGSC_View_Snapshot_Criterion_Structure";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0_X1;
	String SNCRSR1_TYPE_CODE_0;
	String Standard_Investment_Options;
	String SCSSIO1_SDIO_ID_LOV0;
	String SNCRSR1_TYPE_CODE_0_X1;
	
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
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public void setSNCRSR1_TYPE_CODE_0(String sNCRSR1_TYPE_CODE_0) {
		SNCRSR1_TYPE_CODE_0 = sNCRSR1_TYPE_CODE_0;
	}
	public void setStandard_Investment_Options(String standard_Investment_Options) {
		Standard_Investment_Options = standard_Investment_Options;
	}
	public void setSCSSIO1_SDIO_ID_LOV0(String sCSSIO1_SDIO_ID_LOV0) {
		SCSSIO1_SDIO_ID_LOV0 = sCSSIO1_SDIO_ID_LOV0;
	}
	public void setSNCRSR1_TYPE_CODE_0_X1(String sNCRSR1_TYPE_CODE_0_X1) {
		SNCRSR1_TYPE_CODE_0_X1 = sNCRSR1_TYPE_CODE_0_X1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setSCSSIO1_SDIO_ID_LOV0(Stock.GetParameterValue("SCSSIO1_SDIO_ID_LOV0"));
		this.setSNCRSR1_TYPE_CODE_0(Stock.GetParameterValue("SNCRSR1_TYPE_CODE_0"));
		this.setSNCRSR1_TYPE_CODE_0_X1(Stock.GetParameterValue("SNCRSR1_TYPE_CODE_0_X1"));
		this.setStandard_Investment_Options(Stock.GetParameterValue("Standard_Investment_Options"));		
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&SNCRSR1_TYPE_CODE_0="+SNCRSR1_TYPE_CODE_0+"&Standard_Investment_Options="+Standard_Investment_Options+
				"&SCSSIO1_SDIO_ID_LOV0="+SCSSIO1_SDIO_ID_LOV0+"&SNCRSR1_TYPE_CODE_0_X1="+SNCRSR1_TYPE_CODE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PGSC service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PGSC service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PGSC service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "PROD_ID_0: "+doc.getElementsByTagName("PUSNCR1_PROD_ID_0").item(0).getTextContent()+
					"\nPROD_ID_1: "+doc.getElementsByTagName("PUSNCR1_PROD_ID_1").item(0).getTextContent()+
					"\nPROD_NAME_0: "+doc.getElementsByTagName("PUSNCR1_PROD_NAME_0").item(0).getTextContent()+
					"\nPROD_NAME_1: "+doc.getElementsByTagName("PUSNCR1_PROD_NAME_1").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String type_code = this.SNCRSR1_TYPE_CODE_0;
		String type_code_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPGSCAdd")[1], type_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: SNAP_CRIT_STRUC",false);
			while(queryResultSet.next()){
			type_code_db = queryResultSet.getString("TYPE_CODE");
			Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "ID: "+ queryResultSet.getString("ID")+
					"\nRSN_NARRATIVE: "+queryResultSet.getString("RSN_NARRATIVE")+
					"\nBASIS_CODE: "+queryResultSet.getString("BASIS_CODE")+
					"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE"), false);
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE", "Record does not Exist", false);
		}
		if(type_code.equalsIgnoreCase(type_code_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating TYPE CODE from Input and Database", "Both the values are same as Expected"+
					"\nTYPE CODE: "+"From Input: "+type_code+"\nFrom Database: "+type_code_db, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Comparing and Validating TYPE CODE from Input and Database", "Both the values are not same as Expected"+
					"\nTYPE CODE: "+"From Input: "+type_code+"\nFrom Database: "+type_code_db, false);
		}
		
	}
}
