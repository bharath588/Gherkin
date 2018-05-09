package pageobject.CABL;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class CABL_Errors_Navigation {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CABLerrors_and_navigation";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String INDIVIDUAL_SSN_0_X1;
	String INDIVIDUAL_SSN_0_X2;
	String NAVIGATION_LOV;
	String NAVIGATION_LOV_X1;
	String NAVIGATION_LOV_X2;
	String NAVIGATION_LOV_X3;
	String NAVIGATION_LOV_X4;
	String NAVIGATION_LOV_X5;
	String NAVIGATION_LOV_X6;
	String NAVIGATION_LOV_X7;
	String NAVIGATION_LOV_X8;
	String NAVIGATION_LOV_X9;
	String NAVIGATION_LOV_X10;
	String NAVIGATION_LOV_X11;
	
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
	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}
	public void setINDIVIDUAL_SSN_0_X1(String iNDIVIDUAL_SSN_0_X1) {
		INDIVIDUAL_SSN_0_X1 = iNDIVIDUAL_SSN_0_X1;
	}
	public void setINDIVIDUAL_SSN_0_X2(String iNDIVIDUAL_SSN_0_X2) {
		INDIVIDUAL_SSN_0_X2 = iNDIVIDUAL_SSN_0_X2;
	}
	public void setNAVIGATION_LOV(String nAVIGATION_LOV) {
		NAVIGATION_LOV = nAVIGATION_LOV;
	}
	public void setNAVIGATION_LOV_X1(String nAVIGATION_LOV_X1) {
		NAVIGATION_LOV_X1 = nAVIGATION_LOV_X1;
	}
	public void setNAVIGATION_LOV_X2(String nAVIGATION_LOV_X2) {
		NAVIGATION_LOV_X2 = nAVIGATION_LOV_X2;
	}
	public void setNAVIGATION_LOV_X3(String nAVIGATION_LOV_X3) {
		NAVIGATION_LOV_X3 = nAVIGATION_LOV_X3;
	}
	public void setNAVIGATION_LOV_X4(String nAVIGATION_LOV_X4) {
		NAVIGATION_LOV_X4 = nAVIGATION_LOV_X4;
	}
	public void setNAVIGATION_LOV_X5(String nAVIGATION_LOV_X5) {
		NAVIGATION_LOV_X5 = nAVIGATION_LOV_X5;
	}
	public void setNAVIGATION_LOV_X6(String nAVIGATION_LOV_X6) {
		NAVIGATION_LOV_X6 = nAVIGATION_LOV_X6;
	}
	public void setNAVIGATION_LOV_X7(String nAVIGATION_LOV_X7) {
		NAVIGATION_LOV_X7 = nAVIGATION_LOV_X7;
	}
	public void setNAVIGATION_LOV_X8(String nAVIGATION_LOV_X8) {
		NAVIGATION_LOV_X8 = nAVIGATION_LOV_X8;
	}
	public void setNAVIGATION_LOV_X9(String nAVIGATION_LOV_X9) {
		NAVIGATION_LOV_X9 = nAVIGATION_LOV_X9;
	}
	public void setNAVIGATION_LOV_X10(String nAVIGATION_LOV_X10) {
		NAVIGATION_LOV_X10 = nAVIGATION_LOV_X10;
	}
	public void setNAVIGATION_LOV_X11(String nAVIGATION_LOV_X11) {
		NAVIGATION_LOV_X11 = nAVIGATION_LOV_X11;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		this.setINDIVIDUAL_SSN_0_X1(Stock.GetParameterValue("INDIVIDUAL_SSN_0_X1"));
		this.setINDIVIDUAL_SSN_0_X2(Stock.GetParameterValue("INDIVIDUAL_SSN_0_X2"));
		this.setNAVIGATION_LOV(Stock.GetParameterValue("NAVIGATION_LOV"));
		this.setNAVIGATION_LOV_X1(Stock.GetParameterValue("NAVIGATION_LOV_X1"));
		this.setNAVIGATION_LOV_X2(Stock.GetParameterValue("NAVIGATION_LOV_X2"));
		this.setNAVIGATION_LOV_X3(Stock.GetParameterValue("NAVIGATION_LOV_X3"));
		this.setNAVIGATION_LOV_X4(Stock.GetParameterValue("NAVIGATION_LOV_X4"));
		this.setNAVIGATION_LOV_X5(Stock.GetParameterValue("NAVIGATION_LOV_X5"));
		this.setNAVIGATION_LOV_X6(Stock.GetParameterValue("NAVIGATION_LOV_X6"));
		this.setNAVIGATION_LOV_X7(Stock.GetParameterValue("NAVIGATION_LOV_X7"));
		this.setNAVIGATION_LOV_X8(Stock.GetParameterValue("NAVIGATION_LOV_X8"));
		this.setNAVIGATION_LOV_X9(Stock.GetParameterValue("NAVIGATION_LOV_X9"));
		this.setNAVIGATION_LOV_X10(Stock.GetParameterValue("NAVIGATION_LOV_X10"));
		this.setNAVIGATION_LOV_X11(Stock.GetParameterValue("NAVIGATION_LOV_X11"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&INDIVIDUAL_SSN_0_X1="+INDIVIDUAL_SSN_0_X1+"&INDIVIDUAL_SSN_0_X2="+INDIVIDUAL_SSN_0_X2+"&NAVIGATION_LOV="+NAVIGATION_LOV+"&NAVIGATION_LOV_X1="+NAVIGATION_LOV_X1+
				"&NAVIGATION_LOV_X2="+NAVIGATION_LOV_X2+"&NAVIGATION_LOV_X3="+NAVIGATION_LOV_X3+"&NAVIGATION_LOV_X4="+NAVIGATION_LOV_X4+"&NAVIGATION_LOV_X5="+NAVIGATION_LOV_X5+"&NAVIGATION_LOV_X6="+NAVIGATION_LOV_X6+
				"&NAVIGATION_LOV_X7="+NAVIGATION_LOV_X7+"&NAVIGATION_LOV_X8="+NAVIGATION_LOV_X8+"&NAVIGATION_LOV_X9="+NAVIGATION_LOV_X9+"&NAVIGATION_LOV_X10="+NAVIGATION_LOV_X10+"&NAVIGATION_LOV_X11="+NAVIGATION_LOV_X11+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CABLErrors service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CABLError service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CABLError service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		String statusBarMsgs = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
		
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "Expected: Error messages should be displayed in Status bar messages",
					"From Response: "+statusBarMsgs, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
}
