package pageobject.IRSR;

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

public class IRSR_Insert {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IRSR_Insert";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IRS_RULE_CODE_0;
	String IRS_RULE_LONG_NAME_0;
	String IRS_RULE_DESCR_0;
	String INDIVIDUAL_LEVEL_IND;
	
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
	public void setIRS_RULE_CODE_0(String iRS_RULE_CODE_0) {
		IRS_RULE_CODE_0 = iRS_RULE_CODE_0;
	}
	public void setIRS_RULE_LONG_NAME_0(String iRS_RULE_LONG_NAME_0) {
		IRS_RULE_LONG_NAME_0 = iRS_RULE_LONG_NAME_0;
	}
	public void setIRS_RULE_DESCR_0(String iRS_RULE_DESCR_0) {
		IRS_RULE_DESCR_0 = iRS_RULE_DESCR_0;
	}
	public void setINDIVIDUAL_LEVEL_IND(String iNDIVIDUAL_LEVEL_IND) {
		INDIVIDUAL_LEVEL_IND = iNDIVIDUAL_LEVEL_IND;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINDIVIDUAL_LEVEL_IND(Stock.GetParameterValue("INDIVIDUAL_LEVEL_IND"));
		this.setIRS_RULE_CODE_0(Stock.GetParameterValue("IRS_RULE_CODE_0"));
		this.setIRS_RULE_DESCR_0(Stock.GetParameterValue("IRS_RULE_DESCR_0"));
		this.setIRS_RULE_LONG_NAME_0(Stock.GetParameterValue("IRS_RULE_LONG_NAME_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IRS_RULE_CODE_0="+IRS_RULE_CODE_0+"&IRS_RULE_LONG_NAME_0="+IRS_RULE_LONG_NAME_0+"&IRS_RULE_DESCR_0="+IRS_RULE_DESCR_0+"&INDIVIDUAL_LEVEL_IND="+INDIVIDUAL_LEVEL_IND+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for IRSR service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run IRSR service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run IRSR service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "IRS_RULE_CONTRIB_LIMIT_CHECK_IND_0: "+ doc.getElementsByTagName("IRS_RULE_CONTRIB_LIMIT_CHECK_IND_0").item(0).getTextContent()+
					"\nIRS_RULE_INDIVIDUAL_LEVEL_IND_0: "+doc.getElementsByTagName("IRS_RULE_INDIVIDUAL_LEVEL_IND_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String ind_level = doc.getElementsByTagName("IRS_RULE_INDIVIDUAL_LEVEL_IND_0").item(0).getTextContent();
		String ind_level_db = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIRSR")[1], this.IRS_RULE_CODE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			while(queryResultSet.next()){
				ind_level_db = queryResultSet.getString("INDIVIDUAL_LEVEL_IND");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: IRS_RULE", "CODE: "+queryResultSet.getString("CODE")+
					"\nNAME: "+queryResultSet.getString("LONG_NAME")+
					"\nDESCR: "+queryResultSet.getString("DESCR")+
					"\nINDIVIDUAL LEVEL IND: "+queryResultSet.getString("INDIVIDUAL_LEVEL_IND"), false);
			}
			if(ind_level.equalsIgnoreCase(ind_level_db)){
				Reporter.logEvent(Status.PASS,"Comparing and validating Gross amount from response and Database","Both the values are same as expected"
						+"\nFrom Response: "+ind_level+"\nFrom Database: "+ind_level_db,false);
			}else{
				Reporter.logEvent(Status.FAIL,"Comparing and validating Gross amount from response and Database","Both the values are not same"
						+"\nFrom Response: "+ind_level+"\nFrom Database: "+ind_level_db,false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
	
	public void FlushData(){
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIRSRDelete")[1], this.IRS_RULE_CODE_0);
	}
}
