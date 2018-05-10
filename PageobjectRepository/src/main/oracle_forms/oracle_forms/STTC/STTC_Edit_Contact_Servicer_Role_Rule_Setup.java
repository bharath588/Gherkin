package pageobject.STTC;

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

public class STTC_Edit_Contact_Servicer_Role_Rule_Setup {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() +
			":8080/ServiceManager/Macro/ExecMacro/STTC_Edit_Contact_Servicer_Role_Rule_Setup";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_ROLE_TYPE_PC_CODE_0;
	String STD_ROLE_TYPE_TYPE_CODE_0;
	String STD_ROLE_TYPE_INTERNAL_ROLE_IND_0;
	String STD_ROLE_TYPE_PART_DISPLAY_IND_0;
	
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
	public void setSTD_ROLE_TYPE_PC_CODE_0(String sTD_ROLE_TYPE_PC_CODE_0) {
		STD_ROLE_TYPE_PC_CODE_0 = sTD_ROLE_TYPE_PC_CODE_0;
	}
	public void setSTD_ROLE_TYPE_TYPE_CODE_0(String sTD_ROLE_TYPE_TYPE_CODE_0) {
		STD_ROLE_TYPE_TYPE_CODE_0 = sTD_ROLE_TYPE_TYPE_CODE_0;
	}
	public void setSTD_ROLE_TYPE_INTERNAL_ROLE_IND_0(
			String sTD_ROLE_TYPE_INTERNAL_ROLE_IND_0) {
		STD_ROLE_TYPE_INTERNAL_ROLE_IND_0 = sTD_ROLE_TYPE_INTERNAL_ROLE_IND_0;
	}
	public void setSTD_ROLE_TYPE_PART_DISPLAY_IND_0(
			String sTD_ROLE_TYPE_PART_DISPLAY_IND_0) {
		STD_ROLE_TYPE_PART_DISPLAY_IND_0 = sTD_ROLE_TYPE_PART_DISPLAY_IND_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTD_ROLE_TYPE_INTERNAL_ROLE_IND_0(Stock.GetParameterValue("STD_ROLE_TYPE_INTERNAL_ROLE_IND_0"));
		this.setSTD_ROLE_TYPE_PART_DISPLAY_IND_0(Stock.GetParameterValue("STD_ROLE_TYPE_PART_DISPLAY_IND_0"));
		this.setSTD_ROLE_TYPE_PC_CODE_0(Stock.GetParameterValue("STD_ROLE_TYPE_PC_CODE_0"));
		this.setSTD_ROLE_TYPE_TYPE_CODE_0(Stock.GetParameterValue("STD_ROLE_TYPE_TYPE_CODE_0"));
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STD_ROLE_TYPE_PC_CODE_0="+STD_ROLE_TYPE_PC_CODE_0+"&STD_ROLE_TYPE_TYPE_CODE_0="+STD_ROLE_TYPE_TYPE_CODE_0+"&STD_ROLE_TYPE_INTERNAL_ROLE_IND_0="+STD_ROLE_TYPE_INTERNAL_ROLE_IND_0+
				"&STD_ROLE_TYPE_PART_DISPLAY_IND_0="+STD_ROLE_TYPE_PART_DISPLAY_IND_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STTC service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run STTC service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run STTC service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "STD_ROLE_TYPE_DESCR"+doc.getElementsByTagName("STD_ROLE_TYPE_DESCRIPTION_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{	
		
		String std_descr_resp = doc.getElementsByTagName("STD_ROLE_TYPE_DESCRIPTION_0").item(0).getTextContent();
		String std_descr_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTTC")[1], this.STD_ROLE_TYPE_PC_CODE_0, this.STD_ROLE_TYPE_TYPE_CODE_0);			
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: STD_ROLE_TYPE", "Record exists in the Database", false);
				while(queryResultSet.next()){	
					std_descr_db = queryResultSet.getString("DESCRIPTION");
					Reporter.logEvent(Status.INFO, "From Database: ", "TYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
							"\nDESCR: "+queryResultSet.getString("DESCRIPTION")+
							"\nPC_CODE: "+queryResultSet.getString("PC_CODE"), false);
				}
				if(std_descr_resp.equalsIgnoreCase(std_descr_db)){
					Reporter.logEvent(Status.PASS, "Comparing and Validating STD ROLE DESCRIPTION from response and Database",
							"Both the values are same as expected\n"+"From Response: "+std_descr_resp+"From Database: "+std_descr_db, false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Comparing and Validating STD ROLE DESCRIPTION from response and Database",
							"Both the values are not same \n"+"From Response: "+std_descr_resp+"\nFrom Database: "+std_descr_db, false);
				}				
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
			}
	}
}
