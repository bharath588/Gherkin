package pageobject.APRU;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class APRU_Add_Annuity_Option_Provider_Level {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/APRU_Add_Annuity_Option_Provider_Level";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String APO_VIEW_EFFDATE_8;
	
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
	public void setAPO_VIEW_EFFDATE_8(String aPO_VIEW_EFFDATE_8) {
		APO_VIEW_EFFDATE_8 = aPO_VIEW_EFFDATE_8;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setAPO_VIEW_EFFDATE_8(Stock.GetParameterValue("APO_VIEW_EFFDATE_8"));
		
	/*	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String currentDate=dateFormat.format(date);
		
		System.out.println("Current Date: "+currentDate);
		
		this.setAPO_VIEW_EFFDATE_8(currentDate);*/
	
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&APO_VIEW_EFFDATE_8="+APO_VIEW_EFFDATE_8+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for APRU service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run APRU service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run APRU service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		NodeList list = doc.getElementsByTagName("APRU_Add_Annuity_Option_Provider_LevelArrayItem");
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);		
			Reporter.logEvent(Status.INFO, "Values from response", "ANNUITY_PROVIDER_RULE_CODE_0: " +
			doc.getElementsByTagName("ANNUITY_PROVIDER_RULE_CODE_0").item(0).getTextContent()+
			"\nTotal no of items: "+ list.getLength(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		String apo_code = doc.getElementsByTagName("ANNUITY_PROVIDER_RULE_CODE_0").item(0).getTextContent();
		String apo_code_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPRUProvider")[1], this.APO_VIEW_EFFDATE_8);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record in DB", "Records inserted in DB\nTableName: ANNUITY_PROVIDER_RULE_OPTION", false);
			while(queryResultSet.next()){
				apo_code_db = queryResultSet.getString("APR_CODE");
				Reporter.logEvent(Status.INFO, "Values from Database", "APR_CODE: "+queryResultSet.getString("APR_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTERMDATE: "+queryResultSet.getString("TERMDATE"), false);
			}
			if(apo_code.equalsIgnoreCase(apo_code_db)){
				Reporter.logEvent(Status.PASS, "Comparing APR CODE field from Response and Database", "Both the value are same as expected"+
			"\nFrom Response: "+apo_code+"\nFrom DB: "+apo_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing TYPE CODE field from Input and Database", "Both the value are not same "+
						"\nFrom Response: "+apo_code+"\nFrom DB: "+apo_code_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record in DB", "Records not inserted DB\nTableName: ANNUITY_PROVIDER_RULE_OPTION", false);
		}
		
	}
	
	public void resetData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPRUProviderReset")[1], this.APO_VIEW_EFFDATE_8);
	}
	
}
