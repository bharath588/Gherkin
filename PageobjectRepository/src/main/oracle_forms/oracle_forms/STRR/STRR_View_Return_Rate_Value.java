package pageobject.STRR;

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

public class STRR_View_Return_Rate_Value {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STRR_View_Return_Rate_Value";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IOCNTRL_N_EFFDATE_0;
	String SDIO_ID;
	
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
	public void setIOCNTRL_N_EFFDATE_0(String iOCNTRL_N_EFFDATE_0) {
		IOCNTRL_N_EFFDATE_0 = iOCNTRL_N_EFFDATE_0;
	}
	public void setSDIO_ID(String sDIO_ID) {
		SDIO_ID = sDIO_ID;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setIOCNTRL_N_EFFDATE_0(Stock.GetParameterValue("IOCNTRL_N_EFFDATE_0"));
		this.setSDIO_ID(Stock.GetParameterValue("SDIO_ID"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IOCNTRL_N_EFFDATE_0="+IOCNTRL_N_EFFDATE_0+"&SDIO_ID="+SDIO_ID+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STRR service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run STRR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STRR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response ", "LEGAL_NAME: "+doc.getElementsByTagName("IOCNTRL_N_LEGAL_NAME_0").item(0).getTextContent()+
					"\nSDIO CODE: "+doc.getElementsByTagName("IOCNTRL_N_SDIO_ID_0").item(0).getTextContent()+
					"\nRATE OF RETURN SRC CODE: "+doc.getElementsByTagName("IOCNTRL_N_RATE_RETURN_SOURCE_CODE_0").item(0).getTextContent()+
					"\nCUSIP NUMBER: "+doc.getElementsByTagName("IOCNTRL_N_CUSIP_NUMBER_0").item(0).getTextContent(), false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String sdio_id = doc.getElementsByTagName("IOCNTRL_N_SDIO_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTRR")[1], sdio_id);		
		if(DB.getRecordSetCount(queryResultSet)>0){
			
				Reporter.logEvent(Status.PASS, "Validating Records exists in DB", "Records exists in DB", false);
				
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: INV_PULL", 
						"Total Records: "+DB.getRecordSetCount(queryResultSet), false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}
