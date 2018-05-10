package pageobject.STRV;

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

public class STRV_Edit_Std_Recov_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STRV_Edit_Standard_Recovery_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String RECOV_STRUC_ID_LOV0;
	String RECOV_STRUC_PRORATE_IND_0;
	
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
	public void setRECOV_STRUC_ID_LOV0(String rECOV_STRUC_ID_LOV0) {
		RECOV_STRUC_ID_LOV0 = rECOV_STRUC_ID_LOV0;
	}
	public void setRECOV_STRUC_PRORATE_IND_0(String rECOV_STRUC_PRORATE_IND_0) {
		RECOV_STRUC_PRORATE_IND_0 = rECOV_STRUC_PRORATE_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setRECOV_STRUC_ID_LOV0(Stock.GetParameterValue("RECOV_STRUC_ID_LOV0"));
		this.setRECOV_STRUC_PRORATE_IND_0(Stock.GetParameterValue("RECOV_STRUC_PRORATE_IND_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&RECOV_STRUC_ID_LOV0="+RECOV_STRUC_ID_LOV0+"&RECOV_STRUC_PRORATE_IND_0="+RECOV_STRUC_PRORATE_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STRV service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run STRV service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STRV service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "RECOV_BASIS_TYPE: "+doc.getElementsByTagName("RECOV_STRUC_BASIS_TYPE_CODE_0").item(0).getTextContent()+
					"\nDESCR: "+doc.getElementsByTagName("RECOV_STRUC_DESCR_0").item(0).getTextContent()+
					"\nFEE_TYPE_CODE: "+doc.getElementsByTagName("RECOV_STRUC_DOL_FEE_TYPE_CODE_0").item(0).getTextContent()+
					"\nSTRUCT_NAME: "+doc.getElementsByTagName("RECOV_STRUC_NAME_0").item(0).getTextContent()+
					"\nASSMNT_DESCR: "+doc.getElementsByTagName("RECOV_STRUC_N_ASSMNT_DESCR_0").item(0).getTextContent()+
					"\nASSMNT_FREQ_DESCR: "+doc.getElementsByTagName("RECOV_STRUC_N_ASSMNT_FREQ_DESCR_0").item(0).getTextContent()+
					"\nCALC_LEVEL_DESCR: "+doc.getElementsByTagName("RECOV_STRUC_N_CALC_LEVEL_DESCR_0").item(0).getTextContent()+
					"\nRECOV_STRUCT_ID: "+doc.getElementsByTagName("RECOV_STRUC_ID_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String str_id = doc.getElementsByTagName("RECOV_STRUC_ID_0").item(0).getTextContent();
		String str_id_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTRVEdit")[1], str_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: RECOV_STRUC",false);
				while(queryResultSet.next()){
					str_id_db = queryResultSet.getString("ID");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "RECOV_STRUCT_ID: "+ queryResultSet.getString("ID")+
							"\nNAME: "+ queryResultSet.getString("NAME")+
							"\nRVRS_CODE: "+ queryResultSet.getString("RVRS_CODE"), false);
				}
				if(str_id.equalsIgnoreCase(str_id_db)){
					Reporter.logEvent(Status.PASS, "Comparing and validating STRUCT ID from Response and Database", "Both the values are same as expected"+
					"\nFrom Response: "+ str_id+"\nFrom Database: "+str_id_db, false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Comparing and validating STRUCT ID from Response and Database", "Both the values are not same"+
								"\nFrom Response: "+ str_id+"\nFrom Database: "+str_id_db, false);
				}
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
			}	
		}
}
