package pageobject.VSCI;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class VSCI_Cascade_Vesting_Schedule {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSCI_Cascade_Vesting_Schedule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String PLAN_VESTING_RULE_GA_ID_0;
	
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setPLAN_VESTING_RULE_GA_ID_0(String pLAN_VESTING_RULE_GA_ID_0) {
		PLAN_VESTING_RULE_GA_ID_0 = pLAN_VESTING_RULE_GA_ID_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setPLAN_VESTING_RULE_GA_ID_0(Stock.GetParameterValue("PLAN_VESTING_RULE_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+"&PLAN_VESTING_RULE_GA_ID_0="+PLAN_VESTING_RULE_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSCI_Cascade_Vesting_Schedule_Groupings_Error service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSCI_Cascade_Vesting_Schedule service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSCI_Cascade_Vesting_Schedule service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.PASS, "From response ", "Response Messages: "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSCI")[1], this.PLAN_VESTING_RULE_GA_ID_0);
		if(queryResultSet.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:" , "Table: IND_VESTING_RULE"+"New Record has been inserted into table\nTotal Count: "+DB.getRecordSetCount(queryResultSet),false);
			 /* "\nIND_ID: " +queryResultSet.getString("IND_ID")+
					"\nPLAN_ID: " +queryResultSet.getString("PLAN_ID")+
					"\nSDMT_CODE: " +queryResultSet.getString("SDMT_CODE")+
					"\nGA_ID: " +queryResultSet.getString("GA_ID")+
					"\nEFFDATE: " +queryResultSet.getString("EFFDATE"), false);	*/	
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record creation in database", " No Records exists in Database", false);
		}
	}
	
}
