package pageobject.GAWK;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GAWK_Assign {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GAWK_ASSIGN";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String WK_UNIT_SHORT_NAME_0;
	String GROUP_ACCOUNT_ID_0;
	String GROUP_ACCOUNT_SEL_0;
	
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
	public void setWK_UNIT_SHORT_NAME_0(String wK_UNIT_SHORT_NAME_0) {
		WK_UNIT_SHORT_NAME_0 = wK_UNIT_SHORT_NAME_0;
	}
	public void setGROUP_ACCOUNT_ID_0(String gROUP_ACCOUNT_ID_0) {
		GROUP_ACCOUNT_ID_0 = gROUP_ACCOUNT_ID_0;
	}
	public void setGROUP_ACCOUNT_SEL_0(String gROUP_ACCOUNT_SEL_0) {
		GROUP_ACCOUNT_SEL_0 = gROUP_ACCOUNT_SEL_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGROUP_ACCOUNT_ID_0(Stock.GetParameterValue("GROUP_ACCOUNT_ID_0"));
		 this.setGROUP_ACCOUNT_SEL_0(Stock.GetParameterValue("GROUP_ACCOUNT_SEL_0"));
		 this.setWK_UNIT_SHORT_NAME_0(Stock.GetParameterValue("WK_UNIT_SHORT_NAME_0"));
	
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDeleteGAWK")[1], this.WK_UNIT_SHORT_NAME_0, this.GROUP_ACCOUNT_ID_0);
		 if(!queryResultSet.next()){
			 System.out.println("deleted");
		 }
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&WK_UNIT_SHORT_NAME_0="+WK_UNIT_SHORT_NAME_0+"&GROUP_ACCOUNT_ID_0="+GROUP_ACCOUNT_ID_0+"&GROUP_ACCOUNT_SEL_0="+GROUP_ACCOUNT_SEL_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for GAWK service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GAWK service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GAWK service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","GROUP ACCOUNT GROUP CLIENT: "+doc.getElementsByTagName("GROUP_ACCOUNT_GROUP_CLIENT_0").item(0).getTextContent()+					
					"\nWORK UNIT DESCR: "+doc.getElementsByTagName("WK_UNIT_DESCR_0").item(0).getTextContent(),false);
		}
		 else {
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}
				
	}
	
	public void validateInDatabase() throws SQLException{
		/*DateFormat dateFormat = new SimpleDateFormat("");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		*/
		String wkn_shrt_name = this.WK_UNIT_SHORT_NAME_0;
		String wkun_short_name = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGAWK")[1], this.WK_UNIT_SHORT_NAME_0, this.GROUP_ACCOUNT_ID_0);
		if(queryResultSet.next()){
			wkun_short_name = queryResultSet.getString("WKUN_SHORT_NAME");
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From Database","Table Name: GA_WK_ASGNMT_CRIT\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nWKUN_SHORT_NAME: "+queryResultSet.getString("WKUN_SHORT_NAME")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nTERMDATE: "+queryResultSet.getString("TERMDATE")+
					"\nDPDATE_TIME: "+queryResultSet.getString("DPDATE_TIME"),false); 
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of Records in Database","No Record exists ", false);
		}
		if(wkn_shrt_name.equalsIgnoreCase(wkun_short_name)){
			Reporter.logEvent(Status.PASS, "Comparing and validating WORK UNIT SHORT NAME from Response and Database", "Both the values are same as expected"+			
					"\nFrom Response: "+wkn_shrt_name+"\nFrom Database: "+wkun_short_name,false);			
		}
	}
	
}
