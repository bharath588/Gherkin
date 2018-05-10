package pageobject.SNRT;

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
public class SNRT_Query_Provco {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SNRT_PROVCO_QUERY";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	//input parameters
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String HEADER_SHOW_ACTIVE_ONLY_0;
	String HEADER_PROV_CO_0;
	String HEADER_BEGIN_EFFDATE_0;
	
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
	public void setHEADER_SHOW_ACTIVE_ONLY_0(String hEADER_SHOW_ACTIVE_ONLY_0) {
		HEADER_SHOW_ACTIVE_ONLY_0 = hEADER_SHOW_ACTIVE_ONLY_0;
	}
	public void setHEADER_PROV_CO_0(String hEADER_PROV_CO_0) {
		HEADER_PROV_CO_0 = hEADER_PROV_CO_0;
	}
	
	public void setHEADER_BEGIN_EFFDATE_0(String hEADER_BEGIN_EFFDATE_0) {
		HEADER_BEGIN_EFFDATE_0 = hEADER_BEGIN_EFFDATE_0;
	}
	
	public void setServiceParameters()
	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setHEADER_SHOW_ACTIVE_ONLY_0(Stock.GetParameterValue("HEADER_SHOW_ACTIVE_ONLY_0"));
		this.setHEADER_PROV_CO_0(Stock.GetParameterValue("HEADER_PROV_CO_0"));
		this.setHEADER_BEGIN_EFFDATE_0(Stock.GetParameterValue("HEADER_BEGIN_EFFDATE_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +
				"&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
				"&HEADER_SHOW_ACTIVE_ONLY_0="+HEADER_SHOW_ACTIVE_ONLY_0+
				"&HEADER_PROV_CO_0="+HEADER_PROV_CO_0+"&HEADER_BEGIN_EFFDATE_0="+HEADER_BEGIN_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for SNRT_PROVCO_QUERY service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SNRT_PROVCO_QUERY service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SNRT_PROVCO_QUERY service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			/*Reporter.logEvent(Status.INFO,"From Response:","PROD1_NAME_0:"+doc.getElementsByTagName("PROD1_NAME_0").item(0).getTextContent(),false);*/
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("snrtQueryProvco")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snrtQueryProvco")[1], ""
					+ "Records exist in DB.\nRecords count:"+DB.getRecordSetCount(queryResultSet), false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snrtQueryProvco")[1],
					"Record does not exist in DB." , false);
		}
	}
	
}