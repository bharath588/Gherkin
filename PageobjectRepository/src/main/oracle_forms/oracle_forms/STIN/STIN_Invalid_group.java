package pageobject.STIN;

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

public class STIN_Invalid_group {
	
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STIN_Invalid_group";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
    public String getBASIC_INFO_EFFDATE_0() {
		return BASIC_INFO_EFFDATE_0;
	}
	public void setBASIC_INFO_EFFDATE_0(String bASIC_INFO_EFFDATE_0) {
		BASIC_INFO_EFFDATE_0 = bASIC_INFO_EFFDATE_0;
	}
	public String getBASIC_INFO_TERMDATE_0() {
		return BASIC_INFO_TERMDATE_0;
	}
	public void setBASIC_INFO_TERMDATE_0(String bASIC_INFO_TERMDATE_0) {
		BASIC_INFO_TERMDATE_0 = bASIC_INFO_TERMDATE_0;
	}
	public String getBASIC_INFO_GA_ID_0() {
		return BASIC_INFO_GA_ID_0;
	}
	public void setBASIC_INFO_GA_ID_0(String bASIC_INFO_GA_ID_0) {
		BASIC_INFO_GA_ID_0 = bASIC_INFO_GA_ID_0;
	}
	public void setParam27079(String param27079) {
		this.param27079 = param27079;
	}
	public void setParam27079_X1(String param27079_X1) {
		this.param27079_X1 = param27079_X1;
	}
	public void setParam27079_X2(String param27079_X2) {
		this.param27079_X2 = param27079_X2;
	}
	public void setParam27079_X3(String param27079_X3) {
		this.param27079_X3 = param27079_X3;
	}
	public void setParam27079_X4(String param27079_X4) {
		this.param27079_X4 = param27079_X4;
	}
	public void setParam27079_X5(String param27079_X5) {
		this.param27079_X5 = param27079_X5;
	}
	public void setParam27079_X6(String param27079_X6) {
		this.param27079_X6 = param27079_X6;
	}
	public void setParam27079_X7(String param27079_X7) {
		this.param27079_X7 = param27079_X7;
	}
	public void setParam27079_X8(String param27079_X8) {
		this.param27079_X8 = param27079_X8;
	}
	public void setParam27079_X9(String param27079_X9) {
		this.param27079_X9 = param27079_X9;
	}
	public void setParam27079_X10(String param27079_X10) {
		this.param27079_X10 = param27079_X10;
	}
	public void setParam27079_X11(String param27079_X11) {
		this.param27079_X11 = param27079_X11;
	}
	public void setParam27079_X12(String param27079_X12) {
		this.param27079_X12 = param27079_X12;
	}

	String param27079;
    String param27079_X1;
    String param27079_X2;
    String param27079_X3;
    String param27079_X4;
    String param27079_X5;
    String param27079_X6;
    String param27079_X7;
    String param27079_X8;
    String param27079_X9;
    String param27079_X10;
    String param27079_X11;
    String param27079_X12;
    String BASIC_INFO_EFFDATE_0;
    String BASIC_INFO_TERMDATE_0;
    String BASIC_INFO_GA_ID_0;
	
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
	
	

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam27079(Stock.GetParameterValue("param27079"));
		 this.setParam27079_X1(Stock.GetParameterValue("param27079_X1"));
		 this.setParam27079_X2(Stock.GetParameterValue("param27079_X2"));
		 this.setParam27079_X3(Stock.GetParameterValue("param27079_X3"));
		 this.setParam27079_X4(Stock.GetParameterValue("param27079_X4"));
		 this.setParam27079_X5(Stock.GetParameterValue("param27079_X5"));
		 this.setParam27079_X6(Stock.GetParameterValue("param27079_X6"));
		 this.setParam27079_X7(Stock.GetParameterValue("param27079_X7"));
		 this.setParam27079_X8(Stock.GetParameterValue("param27079_X8"));
		 this.setParam27079_X9(Stock.GetParameterValue("param27079_X9"));
		 this.setParam27079_X10(Stock.GetParameterValue("param27079_X10"));
		 this.setParam27079_X11(Stock.GetParameterValue("param27079_X11"));
		 this.setParam27079_X12(Stock.GetParameterValue("param27079_X12"));
		 this.setBASIC_INFO_EFFDATE_0(Stock.GetParameterValue("BASIC_INFO_EFFDATE_0"));
		 this.setBASIC_INFO_TERMDATE_0(Stock.GetParameterValue("BASIC_INFO_TERMDATE_0"));
		 this.setBASIC_INFO_GA_ID_0(Stock.GetParameterValue("BASIC_INFO_GA_ID_0"));
		
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD
				 +"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
				 +"&param27079="+param27079+"&param27079_X1="+param27079_X1
				 +"&param27079_X2="+param27079_X2+"&param27079_X3="+param27079_X3
				 +"&param27079_X4="+param27079_X4+"&param27079_X5="+param27079_X5
				 +"&param27079_X6="+param27079_X6+"&param27079_X7="+param27079_X7
				 +"&param27079_X8="+param27079_X8+"&param27079_X9="+param27079_X9
				 +"&param27079_X10="+param27079_X10+"&param27079_X11="+param27079_X11
				 +"&param27079_X12="+param27079_X12+"&BASIC_INFO_EFFDATE_0="+BASIC_INFO_EFFDATE_0
				 +"&BASIC_INFO_TERMDATE_0="+BASIC_INFO_TERMDATE_0+"&BASIC_INFO_GA_ID_0="+BASIC_INFO_GA_ID_0
				 +"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";

		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for STIN service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  STIN service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run STIN service", "Running Failed:", false);
			}
			
	}
	
   public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
		String Exp_output = "Invalid group_account.";
		String Act_output = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
		if(Act_output.contains(Exp_output))
		{
			Reporter.logEvent(Status.PASS,"Validating if message invalid group_account displayed","Message invalid group_account displayed", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Validating if message invalid group_account displayed","Message invalid group_account is not displayed", false);
		}
		
	}
  

}