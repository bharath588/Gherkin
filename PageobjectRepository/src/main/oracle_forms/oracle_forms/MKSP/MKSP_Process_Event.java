package pageobject.MKSP;

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

public class MKSP_Process_Event {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MKSP_process_event";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CFG_CONTROL_GA_ID_0;
	String REMIT_NOTICE_SELECT_BOX_8;
	String REMIT_NOTICE_SELECT_BOX_9;
	String param27070;
	String param27070_X1;
	String param27070_X2;
	String param27070_X3;
	String param27070_X4;
	String param27070_X5;
	String param27070_X6;
	String param27070_X7;
	String param27070_X8;
	String param27070_X9;
	String param27070_X10;
	String param27070_X11;
	String param27070_X12;
	String param27070_X13;
	String param27070_X14;
	
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
	public void setCFG_CONTROL_GA_ID_0(String cFG_CONTROL_GA_ID_0) {
		CFG_CONTROL_GA_ID_0 = cFG_CONTROL_GA_ID_0;
	}
	public void setREMIT_NOTICE_SELECT_BOX_8(String rEMIT_NOTICE_SELECT_BOX_8) {
		REMIT_NOTICE_SELECT_BOX_8 = rEMIT_NOTICE_SELECT_BOX_8;
	}
	public void setREMIT_NOTICE_SELECT_BOX_9(String rEMIT_NOTICE_SELECT_BOX_9) {
		REMIT_NOTICE_SELECT_BOX_9 = rEMIT_NOTICE_SELECT_BOX_9;
	}
	public void setParam27070(String param27070) {
		this.param27070 = param27070;
	}
	public void setParam27070_X1(String param27070_X1) {
		this.param27070_X1 = param27070_X1;
	}
	public void setParam27070_X2(String param27070_X2) {
		this.param27070_X2 = param27070_X2;
	}
	public void setParam27070_X3(String param27070_X3) {
		this.param27070_X3 = param27070_X3;
	}
	public void setParam27070_X4(String param27070_X4) {
		this.param27070_X4 = param27070_X4;
	}
	public void setParam27070_X5(String param27070_X5) {
		this.param27070_X5 = param27070_X5;
	}
	public void setParam27070_X6(String param27070_X6) {
		this.param27070_X6 = param27070_X6;
	}
	public void setParam27070_X7(String param27070_X7) {
		this.param27070_X7 = param27070_X7;
	}
	public void setParam27070_X8(String param27070_X8) {
		this.param27070_X8 = param27070_X8;
	}
	public void setParam27070_X9(String param27070_X9) {
		this.param27070_X9 = param27070_X9;
	}
	public void setParam27070_X10(String param27070_X10) {
		this.param27070_X10 = param27070_X10;
	}
	public void setParam27070_X11(String param27070_X11) {
		this.param27070_X11 = param27070_X11;
	}
	public void setParam27070_X12(String param27070_X12) {
		this.param27070_X12 = param27070_X12;
	}
	public void setParam27070_X13(String param27070_X13) {
		this.param27070_X13 = param27070_X13;
	}
	public void setParam27070_X14(String param27070_X14) {
		this.param27070_X14 = param27070_X14;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCFG_CONTROL_GA_ID_0(Stock.GetParameterValue("CFG_CONTROL_GA_ID_0"));
		 this.setParam27070(Stock.GetParameterValue("param27070"));
		 this.setParam27070_X1(Stock.GetParameterValue("param27070_X1"));
		 this.setParam27070_X2(Stock.GetParameterValue("param27070_X2"));
		 this.setParam27070_X3(Stock.GetParameterValue("param27070_X3"));
		 this.setParam27070_X4(Stock.GetParameterValue("param27070_X4"));
		 this.setParam27070_X5(Stock.GetParameterValue("param27070_X5"));
		 this.setParam27070_X6(Stock.GetParameterValue("param27070_X6"));
		 this.setParam27070_X7(Stock.GetParameterValue("param27070_X7"));
		 this.setParam27070_X8(Stock.GetParameterValue("param27070_X8"));
		 this.setParam27070_X9(Stock.GetParameterValue("param27070_X9"));
		 this.setParam27070_X10(Stock.GetParameterValue("param27070_X10"));
		 this.setParam27070_X11(Stock.GetParameterValue("param27070_X11"));
		 this.setParam27070_X12(Stock.GetParameterValue("param27070_X12"));
		 this.setParam27070_X13(Stock.GetParameterValue("param27070_X13"));
		 this.setParam27070_X14(Stock.GetParameterValue("param27070_X14"));
		 this.setREMIT_NOTICE_SELECT_BOX_8(Stock.GetParameterValue("REMIT_NOTICE_SELECT_BOX_8"));
		 this.setREMIT_NOTICE_SELECT_BOX_9(Stock.GetParameterValue("REMIT_NOTICE_SELECT_BOX_9"));
		 
		 queryString = "?CFG_CONTROL_GA_ID_0="+CFG_CONTROL_GA_ID_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				 "&REMIT_NOTICE_SELECT_BOX_8="+REMIT_NOTICE_SELECT_BOX_8+"&REMIT_NOTICE_SELECT_BOX_9="+REMIT_NOTICE_SELECT_BOX_9+"&param27070="+param27070+
				 "&param27070_X1="+param27070_X1+"&param27070_X10="+param27070_X10+"&param27070_X11="+param27070_X11+"&param27070_X12="+param27070_X12+"&param27070_X13="+param27070_X13+
				 "&param27070_X14="+param27070_X14+"&param27070_X2="+param27070_X2+"&param27070_X3="+param27070_X3+"&param27070_X4="+param27070_X4+"&param27070_X5="+param27070_X5+"&param27070_X6="+param27070_X6+
				 "&param27070_X7="+param27070_X7+"&param27070_X8="+param27070_X8+"&param27070_X9="+param27070_X9+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for MSKP service", this.queryString.replaceAll("&", "\n"), false);
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
				System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run MKSP service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MKSP service", "Running Failed:", false);
			}
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Valiadtion and Allocation Messages from Response",doc.getElementsByTagName("PopupMessages").item(0).getTextContent(), false);
			Reporter.logEvent(Status.INFO, "From Response: ", "CFG_CONTROL_GA_NAME_0: "+doc.getElementsByTagName("CFG_CONTROL_GA_NAME_0").item(0).getTextContent()+
					"\nREMIT_NOTICE_CASH_EFFDATE_0: "+doc.getElementsByTagName("REMIT_NOTICE_CASH_EFFDATE_0").item(0).getTextContent()+
					"\nREMIT_NOTICE_CSRS_CODE_0: "+doc.getElementsByTagName("REMIT_NOTICE_CSRS_CODE_0").item(0).getTextContent()+
					"\nREMIT_NOTICE_ID_0: "+doc.getElementsByTagName("REMIT_NOTICE_ID_0").item(0).getTextContent()+
					"\nREMIT_NOTICE_PROCESS_STATUS_CODE_0: "+doc.getElementsByTagName("REMIT_NOTICE_PROCESS_STATUS_CODE_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		
	}
	
}
