package pageobject.STEP;

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

public class Step_View {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STEP_VIEW_STEP";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	ResultSet queryResultSet;
	
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String EV1_ID_0;
	String param27018;
	String param27018_X1;
	String param27018_X2;
	String param27018_X3;
	String param27018_X4;
	String param27018_X5;
	String CONTROL_SELECTION_0_X1;
	String EV1_ID_0_X1;
	
	String CONTROL_SELECTION_0;
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getEV1_ID_0() {
		return EV1_ID_0;
	}
	public void setEV1_ID_0(String eV1_ID_0) {
		EV1_ID_0 = eV1_ID_0;
	}
	public String getParam27018() {
		return param27018;
	}
	public void setParam27018(String param27018) {
		this.param27018 = param27018;
	}
	public String getParam27018_X1() {
		return param27018_X1;
	}
	public void setParam27018_X1(String param27018_X1) {
		this.param27018_X1 = param27018_X1;
	}
	public String getParam27018_X2() {
		return param27018_X2;
	}
	public void setParam27018_X2(String param27018_X2) {
		this.param27018_X2 = param27018_X2;
	}
	public String getParam27018_X3() {
		return param27018_X3;
	}
	public void setParam27018_X3(String param27018_X3) {
		this.param27018_X3 = param27018_X3;
	}
	public String getParam27018_X4() {
		return param27018_X4;
	}
	public void setParam27018_X4(String param27018_X4) {
		this.param27018_X4 = param27018_X4;
	}
	public String getParam27018_X5() {
		return param27018_X5;
	}
	public void setParam27018_X5(String param27018_X5) {
		this.param27018_X5 = param27018_X5;
	}
	public String getCONTROL_SELECTION_0_X1() {
		return CONTROL_SELECTION_0_X1;
	}
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public String getEV1_ID_0_X1() {
		return EV1_ID_0_X1;
	}
	public void setEV1_ID_0_X1(String eV1_ID_0_X1) {
		EV1_ID_0_X1 = eV1_ID_0_X1;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setEV1_ID_0(Stock.GetParameterValue("EV1_ID_0"));
		this.setEV1_ID_0_X1(Stock.GetParameterValue("EV1_ID_0_X1"));
		this.setParam27018(Stock.GetParameterValue("param27018"));
		this.setParam27018_X1(Stock.GetParameterValue("param27018_X1"));
		this.setParam27018_X2(Stock.GetParameterValue("param27018_X2"));
		this.setParam27018_X3(Stock.GetParameterValue("param27018_X3"));
		this.setParam27018_X4(Stock.GetParameterValue("param27018_X4"));
		this.setParam27018_X5(Stock.GetParameterValue("param27018_X5"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&EV1_ID_0="+EV1_ID_0
				+"&param27018="+param27018
				+"&param27018_X1="+param27018_X1
				+"&param27018_X2="+param27018_X2
				+"&param27018_X3="+param27018_X3
				+"&param27018_X4="+param27018_X4
				+"&param27018_X5="+param27018_X5
				+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1
				+"&EV1_ID_0_X1="+EV1_ID_0_X1
				+"&numOfRowsInTable=20&json=false&handlePopups=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STEP service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run STEP view service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STEP view service", "Running Failed:", false);
		}
	}
	
	 
	public void validateOutput() throws SQLException
	{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("checkInSteptableQuery")[1], EV1_ID_0);
		
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Verify if records are displayed from the STEP table", "Records are created in step table with event ID: "+this.EV1_ID_0, false);
		}
		
		
		if(!(DB.getRecordSetCount(queryResultSet) > 0) )
		{
			Reporter.logEvent(Status.FAIL, "Verify if A record is displayed from the STEP table", "A record is not created in step table", false);
		}
	}
	
	
	}

