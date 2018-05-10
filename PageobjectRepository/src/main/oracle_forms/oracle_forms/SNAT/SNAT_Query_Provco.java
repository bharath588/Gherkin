/**
 * 
 */
package pageobject.SNAT;

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
public class SNAT_Query_Provco {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SNAT_QUERY_PROVCO";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	//input parameters
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String MORE_FIELD1_0;
	String MORE_FIELD1_1;
	String MORE_FIELD1_2;
	
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
	public void setMORE_FIELD1_0(String mORE_FIELD1_0) {
		MORE_FIELD1_0 = mORE_FIELD1_0;
	}
	public void setMORE_FIELD1_1(String mORE_FIELD1_1) {
		MORE_FIELD1_1 = mORE_FIELD1_1;
	}
	public void setMORE_FIELD1_2(String mORE_FIELD1_2) {
		MORE_FIELD1_2 = mORE_FIELD1_2;
	}
	
	public void setServiceParameters()
	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setMORE_FIELD1_0(Stock.GetParameterValue("MORE_FIELD1_0"));
		this.setMORE_FIELD1_1(Stock.GetParameterValue("MORE_FIELD1_1"));
		this.setMORE_FIELD1_2(Stock.GetParameterValue("MORE_FIELD1_2"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +
				"&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
				"&MORE_FIELD1_0="+MORE_FIELD1_0+"&MORE_FIELD1_1="+MORE_FIELD1_1+"&MORE_FIELD1_2="+MORE_FIELD1_2+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for SNAT_QUERY_PROVCO service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SNAT_QUERY_PROVCO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SNAT_QUERY_PROVCO service", "Running Failed:", false);
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
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("snatQueryProvco")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snatQueryProvco")[1], ""
					+ "Records exist in DB.\nRecords count:"+DB.getRecordSetCount(queryResultSet), false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO,"From DB:","PROV_COMPANY:"+queryResultSet.getString("PROV_COMPANY")+
						"\nNARR_CODE:"+queryResultSet.getString("NARR_CODE")+
						"\nNARR_LEVEL:"+queryResultSet.getString("NARR_LEVEL")+
						"\nSTART_DATE:"+queryResultSet.getString("START_DATE")+
						"\nSTOP_DATE:"+queryResultSet.getString("STOP_DATE")+
						"\nNARR_OPTION:"+queryResultSet.getString("NARR_OPTION"), false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snatQueryProvco")[1],
					"Record does not exist in DB." , false);
		}
	}
}


/**
 * 
 *//*
package pageobject.SNAT;

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

*//**
 * @author smykjn
 *
 *//*
public class SNAT_Query_Provco {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SNAT_PROVCO_QUERY";
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
	public void setHEADER_SHOW_ACTIVE_ONLY_0(String mORE_FIELD1_0) {
		HEADER_SHOW_ACTIVE_ONLY_0 = mORE_FIELD1_0;
	}
	public void setHEADER_PROV_CO_0(String mORE_FIELD1_1) {
		HEADER_PROV_CO_0 = mORE_FIELD1_1;
	}
	
	public void setServiceParameters()
	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setHEADER_PROV_CO_0(Stock.GetParameterValue("HEADER_PROV_CO_0"));
		this.setHEADER_SHOW_ACTIVE_ONLY_0(Stock.GetParameterValue("HEADER_SHOW_ACTIVE_ONLY_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +
				"&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
				"&HEADER_SHOW_ACTIVE_ONLY_0="+HEADER_SHOW_ACTIVE_ONLY_0+"&HEADER_PROV_CO_0="+HEADER_PROV_CO_0 +
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SNAT_QUERY_PROVCO service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SNAT_QUERY_PROVCO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SNAT_QUERY_PROVCO service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO,"From Response:","LIST_NARR_CODE_0:"+doc.getElementsByTagName("LIST_NARR_CODE_0").item(0).getTextContent()+
					"\nLIST_BEGIN_DATE_0:"+doc.getElementsByTagName("LIST_BEGIN_DATE_0").item(0).getTextContent()+
					"\nLIST_END_DATE_0:"+doc.getElementsByTagName("LIST_END_DATE_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
public void validateInDatabase() throws SQLException{
		
		String narr_code = doc.getElementsByTagName("LIST_NARR_CODE_0").item(0).getTextContent();
		String strt_date = doc.getElementsByTagName("LIST_BEGIN_DATE_0").item(0).getTextContent();
		String end_date = doc.getElementsByTagName("LIST_END_DATE_0").item(0).getTextContent();
		
		String narr_code_db = null;
		String strt_date_db = null;
		String end_date_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("snatQueryProvco")[1], this.HEADER_PROV_CO_0);
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snatQueryProvco")[1], ""
					+ "Records exist in DB.\nRecords count:"+DB.getRecordSetCount(queryResultSet), false);
			while(queryResultSet.next()){
				narr_code_db = queryResultSet.getString("CODE");
				strt_date_db = queryResultSet.getString("START_DATE").trim();
				end_date_db = queryResultSet.getString("STOP_DATE").trim();
				
				Reporter.logEvent(Status.INFO,"From DB:","PROV_COMPANY:"+queryResultSet.getString("PROV_COMPANY")+
						"\nNARR_CODE:"+queryResultSet.getString("CODE")+
						"\nNARR_LEVEL:"+queryResultSet.getString("NARR_LEVEL")+
						"\nSTART_DATE:"+queryResultSet.getString("START_DATE")+
						"\nSTOP_DATE:"+queryResultSet.getString("STOP_DATE")+
						"\nNARR_OPTION:"+queryResultSet.getString("NARR_OPTION"), false);
			}
			
			if(narr_code.equalsIgnoreCase(narr_code_db) ){
				Reporter.logEvent(Status.PASS, "Comparing and Validating NARR_CODE values from Response and DB",
						"Both the values are same as expected"+"\nFrom response: "+narr_code+
						"\nFrom DB: "+narr_code_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating values from Response and DB",
						"Both the values are same as expected"+"\nFrom response: "+narr_code+
						"\nFrom DB: "+narr_code_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Execute below query and Validate Records exist in DB.\n"+ Stock.getTestQuery("snatQueryProvco")[1],
					"Record does not exist in DB." , false);
		}
	}
}
*/