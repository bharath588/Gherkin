package pageobject.STUV;

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

public class STUV_View_Inv_Option {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() +
			":8080/ServiceManager/Macro/ExecMacro/STUV_View_Investment_Option";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String IOCNTRL_N_SDIO_ID_LOV0;
	String IOCNTRL_N_EFFDATE_0;
	
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
	public void setIOCNTRL_N_SDIO_ID_LOV0(String iOCNTRL_N_SDIO_ID_LOV0) {
		IOCNTRL_N_SDIO_ID_LOV0 = iOCNTRL_N_SDIO_ID_LOV0;
	}
	public void setIOCNTRL_N_EFFDATE_0(String iOCNTRL_N_EFFDATE_0) {
		IOCNTRL_N_EFFDATE_0 = iOCNTRL_N_EFFDATE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setIOCNTRL_N_EFFDATE_0(Stock.GetParameterValue("IOCNTRL_N_EFFDATE_0"));
		this.setIOCNTRL_N_SDIO_ID_LOV0(Stock.GetParameterValue("IOCNTRL_N_SDIO_ID_LOV0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&IOCNTRL_N_SDIO_ID_LOV0="+IOCNTRL_N_SDIO_ID_LOV0+"&IOCNTRL_N_EFFDATE_0="+IOCNTRL_N_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STUV service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run STUV service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run STUV service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{	
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSTUV")[1], this.IOCNTRL_N_SDIO_ID_LOV0, this.IOCNTRL_N_EFFDATE_0);			
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: INV_PULL", "Record exists in the Database", false);
				while(queryResultSet.next()){	
					Reporter.logEvent(Status.INFO, "From Database: ", "ID: "+queryResultSet.getString("ID")+
							"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
							"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
				}
				
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
			}
	}
	
}
