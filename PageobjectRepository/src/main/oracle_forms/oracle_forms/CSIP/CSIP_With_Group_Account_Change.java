/**
 * 
 */
package pageobject.CSIP;

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

/**
 * @author smykjn
 *
 */
public class CSIP_With_Group_Account_Change {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CSIP_GA_CHANGE_DIFF_PROV_COMPANY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String REMIT_NOTICE_ID_0;
	String REMIT_NOTICE_GA_ID_0;
	String REMIT_NOTICE_GA_ID_0_X1;
	
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
	public void setREMIT_NOTICE_ID_0(String rEMIT_NOTICE_ID_0) {
		REMIT_NOTICE_ID_0 = rEMIT_NOTICE_ID_0;
	}
	public void setREMIT_NOTICE_GA_ID_0(String rEMIT_NOTICE_GA_ID_0) {
		REMIT_NOTICE_GA_ID_0 = rEMIT_NOTICE_GA_ID_0;
	}
	public void setREMIT_NOTICE_GA_ID_0_X1(String rEMIT_NOTICE_GA_ID_0_X1) {
		REMIT_NOTICE_GA_ID_0_X1 = rEMIT_NOTICE_GA_ID_0_X1;
	}
	
	
	public void setServiceParameters(String remit_id)	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE_1"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setREMIT_NOTICE_ID_0(remit_id);
		this.setREMIT_NOTICE_GA_ID_0(Stock.GetParameterValue("REMIT_NOTICE_GA_ID_0"));
		this.setREMIT_NOTICE_GA_ID_0_X1(Stock.GetParameterValue("REMIT_NOTICE_GA_ID_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&REMIT_NOTICE_ID_0="+REMIT_NOTICE_ID_0+"&REMIT_NOTICE_GA_ID_0="+REMIT_NOTICE_GA_ID_0+"&REMIT_NOTICE_GA_ID_0_X1="+REMIT_NOTICE_GA_ID_0_X1+				
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CSIP_GA_CHANGE_DIFF_PROV_COMPANY service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CSIP_GA_CHANGE_DIFF_PROV_COMPANY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CSIP_GA_CHANGE_DIFF_PROV_COMPANY service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();		
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
			Reporter.logEvent(Status.INFO,"From response:","StatusBarMessages="+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		
		}
	}
	
	public void validateInDatabase()throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("remittanceAllocationQueryGroup")[1], REMIT_NOTICE_ID_0);
		if(DB.getRecordSetCount(queryResultSet)==0){
			Reporter.logEvent(Status.PASS, "Record is not created in AG_REMIT table.", "Record is not created in AG_REMIT table.", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Record is not created in AG_REMIT table.", "Record is created in AG_REMIT table.", false);
		}
	}
}
