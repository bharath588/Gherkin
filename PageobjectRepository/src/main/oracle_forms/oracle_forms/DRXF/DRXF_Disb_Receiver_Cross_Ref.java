package pageobject.DRXF;

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

public class DRXF_Disb_Receiver_Cross_Ref {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DRXF_Disbursement_Receiver_Cross_Reference";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_0;
	String param27067;
	String param27067_X1;
	String param27067_X2;
	String param27067_X3;
	String param27067_X4;
	String param27067_X5;
	String param27067_X6;
	String param27067_X7;
	String param27067_X8;
	String param27067_X9;
	String param27067_X10;
	String param27067_X11;
	String param27067_X12;
	String param27067_X13;
	String param27067_X14;
	
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
	public void setCONTROL_SSN_0(String cONTROL_SSN_0) {
		CONTROL_SSN_0 = cONTROL_SSN_0;
	}
	public void setParam27067(String param27067) {
		this.param27067 = param27067;
	}
	public void setParam27067_X1(String param27067_X1) {
		this.param27067_X1 = param27067_X1;
	}
	public void setParam27067_X2(String param27067_X2) {
		this.param27067_X2 = param27067_X2;
	}
	public void setParam27067_X3(String param27067_X3) {
		this.param27067_X3 = param27067_X3;
	}
	public void setParam27067_X4(String param27067_X4) {
		this.param27067_X4 = param27067_X4;
	}
	public void setParam27067_X5(String param27067_X5) {
		this.param27067_X5 = param27067_X5;
	}
	public void setParam27067_X6(String param27067_X6) {
		this.param27067_X6 = param27067_X6;
	}
	public void setParam27067_X7(String param27067_X7) {
		this.param27067_X7 = param27067_X7;
	}
	public void setParam27067_X8(String param27067_X8) {
		this.param27067_X8 = param27067_X8;
	}
	public void setParam27067_X9(String param27067_X9) {
		this.param27067_X9 = param27067_X9;
	}
	public void setParam27067_X10(String param27067_X10) {
		this.param27067_X10 = param27067_X10;
	}
	public void setParam27067_X11(String param27067_X11) {
		this.param27067_X11 = param27067_X11;
	}
	public void setParam27067_X12(String param27067_X12) {
		this.param27067_X12 = param27067_X12;
	}
	public void setParam27067_X13(String param27067_X13) {
		this.param27067_X13 = param27067_X13;
	}
	public void setParam27067_X14(String param27067_X14) {
		this.param27067_X14 = param27067_X14;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_0(Stock.GetParameterValue("CONTROL_SSN_0"));
		this.setParam27067(Stock.GetParameterValue("param27067"));
		this.setParam27067_X1(Stock.GetParameterValue("param27067_X1"));
		this.setParam27067_X2(Stock.GetParameterValue("param27067_X2"));
		this.setParam27067_X3(Stock.GetParameterValue("param27067_X3"));
		this.setParam27067_X4(Stock.GetParameterValue("param27067_X4"));
		this.setParam27067_X5(Stock.GetParameterValue("param27067_X5"));
		this.setParam27067_X6(Stock.GetParameterValue("param27067_X6"));
		this.setParam27067_X7(Stock.GetParameterValue("param27067_X7"));
		this.setParam27067_X8(Stock.GetParameterValue("param27067_X8"));
		this.setParam27067_X9(Stock.GetParameterValue("param27067_X9"));
		this.setParam27067_X10(Stock.GetParameterValue("param27067_X10"));
		this.setParam27067_X11(Stock.GetParameterValue("param27067_X11"));
		this.setParam27067_X12(Stock.GetParameterValue("param27067_X12"));
		this.setParam27067_X13(Stock.GetParameterValue("param27067_X13"));
		this.setParam27067_X14(Stock.GetParameterValue("param27067_X14"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_0="+CONTROL_SSN_0+"&param27067="+param27067+"&param27067_X1="+param27067_X1+"&param27067_X2="+param27067_X2+"&param27067_X3="+param27067_X3+
				"&param27067_X4="+param27067_X4+"&param27067_X5="+param27067_X5+"&param27067_X6="+param27067_X6+"&param27067_X7="+param27067_X7+"&param27067_X8="+param27067_X8+
				"&param27067_X9="+param27067_X9+"&param27067_X10="+param27067_X10+"&param27067_X11="+param27067_X11+"&param27067_X12="+param27067_X12+"&param27067_X13="+param27067_X13+
				"&param27067_X14="+param27067_X14+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for DRXF service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run DRXF service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run DRXF service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "SSN: "+doc.getElementsByTagName("DISB_RECVR_PART_SSN_4").item(0).getTextContent()+
					"\nTRANSACTION CODE: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nDESCR"+ doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDRXF")[1], this.CONTROL_SSN_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: DISB_BASIC\nDISB_RECVR", "Record exists in the Database", false);
			Reporter.logEvent(Status.INFO, "Expected: Records should be displayed from sponsor_comm_preferences table", "Total No. of Records: "+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}
}
