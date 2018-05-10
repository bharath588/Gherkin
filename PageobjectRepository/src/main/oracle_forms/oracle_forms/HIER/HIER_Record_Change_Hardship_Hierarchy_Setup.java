package pageobject.HIER;

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

public class HIER_Record_Change_Hardship_Hierarchy_Setup {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/HIER_Insert";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String HIER_RETREIVE_SEQNBR_4;
	String FUND_LOV;
	String MONEY_SOURCE_LOV;
	String RULE_LOV;
	
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
	public void setHIER_RETREIVE_SEQNBR_4(String hIER_RETREIVE_SEQNBR_4) {
		HIER_RETREIVE_SEQNBR_4 = hIER_RETREIVE_SEQNBR_4;
	}
	public void setFUND_LOV(String fUND_LOV) {
		FUND_LOV = fUND_LOV;
	}
	public void setRULE_LOV(String fUND_LOV) {
		RULE_LOV = fUND_LOV;
	}
	public void setMONEY_SOURCE_LOV(String mONEY_SOURCE_LOV) {
		MONEY_SOURCE_LOV = mONEY_SOURCE_LOV;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setFUND_LOV(Stock.GetParameterValue("FUND_LOV"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setHIER_RETREIVE_SEQNBR_4(Stock.GetParameterValue("HIER_RETREIVE_SEQNBR_4"));
		this.setMONEY_SOURCE_LOV(Stock.GetParameterValue("MONEY_SOURCE_LOV"));
		this.setRULE_LOV(Stock.GetParameterValue("RULE_LOV"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&RULE_LOV="+RULE_LOV+"&HIER_RETREIVE_SEQNBR_4="+HIER_RETREIVE_SEQNBR_4+"&FUND_LOV="+FUND_LOV+"&MONEY_SOURCE_LOV="+MONEY_SOURCE_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for HIER service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run HIER service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run HIER service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "GC_NAME: "+ doc.getElementsByTagName("GROUP_ACCOUNT_GC_NAME_0").item(0).getTextContent()+
					"\nGA_ID: "+doc.getElementsByTagName("GROUP_ACCOUNT_ID_0").item(0).getTextContent()+
					"\nIRS_CODE: "+doc.getElementsByTagName("GROUP_ACCOUNT_IRS_CODE_0").item(0).getTextContent()+
					"\nPRODUCT: "+doc.getElementsByTagName("GROUP_ACCOUNT_PRODUCT_0").item(0).getTextContent()+
					"\nPRODUCT_NAME: "+doc.getElementsByTagName("GROUP_ACCOUNT_PRODUCT_NAME_0").item(0).getTextContent()+
					"\nRULE_TYPE: "+doc.getElementsByTagName("STD_RULE_RULE_TYPE_0").item(0).getTextContent()+
					"\nHIER_TYPE: "+doc.getElementsByTagName("VARS_HIER_TYPE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String ga_id = this.GET_GA_GA_ID_0;
		String rule_id = doc.getElementsByTagName("STD_RULE_ID_0").item(0).getTextContent();
		String seq_id = doc.getElementsByTagName("HIER_RETREIVE_GDMT_SEQNBR_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForHIERInsert")[1], ga_id, rule_id, seq_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			while(queryResultSet.next()){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: DR_METHD_ORDER", "ID: "+queryResultSet.getString("ID")+
					"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nRULE_ID: "+queryResultSet.getString("STD_RL_ID"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}
