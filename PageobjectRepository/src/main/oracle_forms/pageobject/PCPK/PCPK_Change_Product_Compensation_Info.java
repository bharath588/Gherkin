package pageobject.PCPK;

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

public class PCPK_Change_Product_Compensation_Info {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PCPK_CHANGE_PRODUCT_COMPENSATION_INFORMATION";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	String PROD_COMP_P_COMP_PKG_ID_LOV1;
	
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
	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}
	public void setPROD_COMP_P_COMP_PKG_ID_LOV1(String pROD_COMP_P_COMP_PKG_ID_LOV1) {
		PROD_COMP_P_COMP_PKG_ID_LOV1 = pROD_COMP_P_COMP_PKG_ID_LOV1;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));		
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));		
		this.setPROD_COMP_P_COMP_PKG_ID_LOV1(Stock.GetParameterValue("PROD_COMP_P_COMP_PKG_ID_LOV1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_PROD_ID_0="+GET_PROD_ID_0+"&PROD_COMP_P_COMP_PKG_ID_LOV1="+PROD_COMP_P_COMP_PKG_ID_LOV1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for PCPK service", this.queryString.replaceAll("&", "\n"), false);
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PCPK service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PCPK service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response", "IRSRL_CODE: "+doc.getElementsByTagName("GET_PROD_IRSRL_CODE_0").item(0).getTextContent()+
					"\nPROD_NAME: "+doc.getElementsByTagName("GET_PROD_NAME_0").item(0).getTextContent()+
					"\nPKG_ID: "+doc.getElementsByTagName("PROD_COMP_PKG_COMP_PKG_ID_1").item(0).getTextContent()+
					"\nSTEP_TYPE_DESCR: "+doc.getElementsByTagName("STEP_TYPE_DESCR_0").item(0).getTextContent(), false);			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String prod_id = this.GET_PROD_ID_0;
		String pkg_id = doc.getElementsByTagName("PROD_COMP_PKG_COMP_PKG_ID_1").item(0).getTextContent();
		String pkg_id_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPCPKChg")[1], prod_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable name: PROD_COMP_PKG",false);
				while(queryResultSet.next()){
					pkg_id_db = queryResultSet.getString("COMP_PKG_ID");
					Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "COMP_PKG_ID: "+ queryResultSet.getString("COMP_PKG_ID")+
							"\nPROD_ID: "+ queryResultSet.getString("PROD_ID")+
							"\nPKG_TYPE: "+ queryResultSet.getString("COMP_PKG_TYPE_CODE"), false);
				}
				if(pkg_id_db.equalsIgnoreCase(pkg_id)){
					Reporter.logEvent(Status.PASS, "Comparing and validating Package ID from Response and Database", "Both values are same as expected"+
				"\nFrom Response: "+pkg_id+"\nFrom Database: "+pkg_id_db, false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Comparing and validating Package ID from Response and Database", "Both values are not same "+
							"\nFrom Response: "+pkg_id+"\nFrom Database: "+pkg_id_db, false);
				}
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Records exists in Database", "Records doesn't exists in Database",false);
			}	
	}
}
