package pageobject.APFM;

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

public class APFM_Account_Pay_History {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/APFM_Account_Pay_History";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String PART_MODULE_LOV;
	String param270200;
	String param270200_X1;
	String param270200_X2;
	String param270200_X3;
	String param270200_X4;
	String param270200_X5;
	String param270200_X6;
	String param270200_X7;
	String param270200_X8;
	String param270200_X9;
	String param270200_X10;
	String param270200_X11;
	String param270200_X12;
	String param270200_X13;
	String param270200_X14;
	String param270200_X15;
	String param270200_X16;
	String param270200_X17;
	String param270200_X18;
	String param270200_X19;
	String param270200_X20;
	String param270200_X21;
	String param270200_X22;
	String param270200_X23;
	String param270200_X24;
	String param270200_X25;
	String param270200_X26;
	String param270200_X27;
	String param270200_X28;
	String param270200_X29;
	String param270200_X30;
	String param270200_X31;
	String param270200_X32;
	String param270200_X33;
	String param270200_X34;
	String param270200_X35;
	String param270200_X36;
	String param270200_X37;
	String param270200_X38;
	String param270200_X39;
	String param270200_X40;
	String param270200_X41;
	String param270200_X42;
	String param270200_X43;
	String param270200_X44;
	String param270200_X45;
	String param270200_X46;
	String param270200_X47;
	String param270200_X48;
	String param270200_X49;
	String param270200_X50;
	String param270200_X51;
	
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
	public void setPART_MODULE_LOV(String pART_MODULE_LOV) {
		PART_MODULE_LOV = pART_MODULE_LOV;
	}
	public void setParam270200(String param270200) {
		this.param270200 = param270200;
	}
	public void setParam270200_X1(String param270200_X1) {
		this.param270200_X1 = param270200_X1;
	}
	public void setParam270200_X2(String param270200_X2) {
		this.param270200_X2 = param270200_X2;
	}
	public void setParam270200_X3(String param270200_X3) {
		this.param270200_X3 = param270200_X3;
	}
	public void setParam270200_X4(String param270200_X4) {
		this.param270200_X4 = param270200_X4;
	}
	public void setParam270200_X5(String param270200_X5) {
		this.param270200_X5 = param270200_X5;
	}
	public void setParam270200_X6(String param270200_X6) {
		this.param270200_X6 = param270200_X6;
	}
	public void setParam270200_X7(String param270200_X7) {
		this.param270200_X7 = param270200_X7;
	}
	public void setParam270200_X8(String param270200_X8) {
		this.param270200_X8 = param270200_X8;
	}
	public void setParam270200_X9(String param270200_X9) {
		this.param270200_X9 = param270200_X9;
	}
	public void setParam270200_X10(String param270200_X10) {
		this.param270200_X10 = param270200_X10;
	}
	public void setParam270200_X11(String param270200_X11) {
		this.param270200_X11 = param270200_X11;
	}
	public void setParam270200_X12(String param270200_X12) {
		this.param270200_X12 = param270200_X12;
	}
	public void setParam270200_X13(String param270200_X13) {
		this.param270200_X13 = param270200_X13;
	}
	public void setParam270200_X14(String param270200_X14) {
		this.param270200_X14 = param270200_X14;
	}
	public void setParam270200_X15(String param270200_X15) {
		this.param270200_X15 = param270200_X15;
	}
	public void setParam270200_X16(String param270200_X16) {
		this.param270200_X16 = param270200_X16;
	}
	public void setParam270200_X17(String param270200_X17) {
		this.param270200_X17 = param270200_X17;
	}
	public void setParam270200_X18(String param270200_X18) {
		this.param270200_X18 = param270200_X18;
	}
	public void setParam270200_X19(String param270200_X19) {
		this.param270200_X19 = param270200_X19;
	}
	public void setParam270200_X20(String param270200_X20) {
		this.param270200_X20 = param270200_X20;
	}
	public void setParam270200_X21(String param270200_X21) {
		this.param270200_X21 = param270200_X21;
	}
	public void setParam270200_X22(String param270200_X22) {
		this.param270200_X22 = param270200_X22;
	}
	public void setParam270200_X23(String param270200_X23) {
		this.param270200_X23 = param270200_X23;
	}
	public void setParam270200_X24(String param270200_X24) {
		this.param270200_X24 = param270200_X24;
	}
	public void setParam270200_X25(String param270200_X25) {
		this.param270200_X25 = param270200_X25;
	}
	public void setParam270200_X26(String param270200_X26) {
		this.param270200_X26 = param270200_X26;
	}
	public void setParam270200_X27(String param270200_X27) {
		this.param270200_X27 = param270200_X27;
	}
	public void setParam270200_X28(String param270200_X28) {
		this.param270200_X28 = param270200_X28;
	}
	public void setParam270200_X29(String param270200_X29) {
		this.param270200_X29 = param270200_X29;
	}
	public void setParam270200_X30(String param270200_X30) {
		this.param270200_X30 = param270200_X30;
	}
	public void setParam270200_X31(String param270200_X31) {
		this.param270200_X31 = param270200_X31;
	}
	public void setParam270200_X32(String param270200_X32) {
		this.param270200_X32 = param270200_X32;
	}
	public void setParam270200_X33(String param270200_X33) {
		this.param270200_X33 = param270200_X33;
	}
	public void setParam270200_X34(String param270200_X34) {
		this.param270200_X34 = param270200_X34;
	}
	public void setParam270200_X35(String param270200_X35) {
		this.param270200_X35 = param270200_X35;
	}
	public void setParam270200_X36(String param270200_X36) {
		this.param270200_X36 = param270200_X36;
	}
	public void setParam270200_X37(String param270200_X37) {
		this.param270200_X37 = param270200_X37;
	}
	public void setParam270200_X38(String param270200_X38) {
		this.param270200_X38 = param270200_X38;
	}
	public void setParam270200_X39(String param270200_X39) {
		this.param270200_X39 = param270200_X39;
	}
	public void setParam270200_X40(String param270200_X40) {
		this.param270200_X40 = param270200_X40;
	}
	public void setParam270200_X41(String param270200_X41) {
		this.param270200_X41 = param270200_X41;
	}
	public void setParam270200_X42(String param270200_X42) {
		this.param270200_X42 = param270200_X42;
	}
	public void setParam270200_X43(String param270200_X43) {
		this.param270200_X43 = param270200_X43;
	}
	public void setParam270200_X44(String param270200_X44) {
		this.param270200_X44 = param270200_X44;
	}
	public void setParam270200_X45(String param270200_X45) {
		this.param270200_X45 = param270200_X45;
	}
	public void setParam270200_X46(String param270200_X46) {
		this.param270200_X46 = param270200_X46;
	}
	public void setParam270200_X47(String param270200_X47) {
		this.param270200_X47 = param270200_X47;
	}
	public void setParam270200_X48(String param270200_X48) {
		this.param270200_X48 = param270200_X48;
	}
	public void setParam270200_X49(String param270200_X49) {
		this.param270200_X49 = param270200_X49;
	}
	public void setParam270200_X50(String param270200_X50) {
		this.param270200_X50 = param270200_X50;
	}
	public void setParam270200_X51(String param270200_X51) {
		this.param270200_X51 = param270200_X51;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		this.setPART_MODULE_LOV(Stock.GetParameterValue("PART_MODULE_LOV"));
		this.setParam270200(Stock.GetParameterValue("param270200"));
		this.setParam270200_X1(Stock.GetParameterValue("param270200_X1"));
		this.setParam270200_X2(Stock.GetParameterValue("param270200_X2"));
		this.setParam270200_X3(Stock.GetParameterValue("param270200_X3"));
		this.setParam270200_X4(Stock.GetParameterValue("param270200_X4"));
		this.setParam270200_X5(Stock.GetParameterValue("param270200_X5"));
		this.setParam270200_X6(Stock.GetParameterValue("param270200_X6"));
		this.setParam270200_X7(Stock.GetParameterValue("param270200_X7"));
		this.setParam270200_X8(Stock.GetParameterValue("param270200_X8"));
		this.setParam270200_X9(Stock.GetParameterValue("param270200_X9"));
		this.setParam270200_X10(Stock.GetParameterValue("param270200_X10"));
		this.setParam270200_X11(Stock.GetParameterValue("param270200_X11"));
		this.setParam270200_X12(Stock.GetParameterValue("param270200_X12"));
		this.setParam270200_X13(Stock.GetParameterValue("param270200_X13"));
		this.setParam270200_X14(Stock.GetParameterValue("param270200_X14"));
		this.setParam270200_X15(Stock.GetParameterValue("param270200_X15"));
		this.setParam270200_X16(Stock.GetParameterValue("param270200_X16"));
		this.setParam270200_X17(Stock.GetParameterValue("param270200_X17"));
		this.setParam270200_X18(Stock.GetParameterValue("param270200_X18"));
		this.setParam270200_X19(Stock.GetParameterValue("param270200_X19"));
		this.setParam270200_X20(Stock.GetParameterValue("param270200_X20"));
		this.setParam270200_X21(Stock.GetParameterValue("param270200_X21"));
		this.setParam270200_X22(Stock.GetParameterValue("param270200_X22"));
		this.setParam270200_X23(Stock.GetParameterValue("param270200_X23"));
		this.setParam270200_X24(Stock.GetParameterValue("param270200_X24"));
		this.setParam270200_X25(Stock.GetParameterValue("param270200_X25"));
		this.setParam270200_X26(Stock.GetParameterValue("param270200_X26"));
		this.setParam270200_X27(Stock.GetParameterValue("param270200_X27"));
		this.setParam270200_X28(Stock.GetParameterValue("param270200_X28"));
		this.setParam270200_X29(Stock.GetParameterValue("param270200_X29"));
		this.setParam270200_X30(Stock.GetParameterValue("param270200_X30"));
		this.setParam270200_X31(Stock.GetParameterValue("param270200_X31"));
		this.setParam270200_X32(Stock.GetParameterValue("param270200_X32"));
		this.setParam270200_X33(Stock.GetParameterValue("param270200_X33"));
		this.setParam270200_X34(Stock.GetParameterValue("param270200_X34"));
		this.setParam270200_X35(Stock.GetParameterValue("param270200_X35"));
		this.setParam270200_X36(Stock.GetParameterValue("param270200_X36"));
		this.setParam270200_X37(Stock.GetParameterValue("param270200_X37"));
		this.setParam270200_X38(Stock.GetParameterValue("param270200_X38"));
		this.setParam270200_X39(Stock.GetParameterValue("param270200_X39"));
		this.setParam270200_X40(Stock.GetParameterValue("param270200_X40"));
		this.setParam270200_X41(Stock.GetParameterValue("param270200_X41"));
		this.setParam270200_X42(Stock.GetParameterValue("param270200_X42"));
		this.setParam270200_X43(Stock.GetParameterValue("param270200_X43"));
		this.setParam270200_X44(Stock.GetParameterValue("param270200_X44"));
		this.setParam270200_X45(Stock.GetParameterValue("param270200_X45"));
		this.setParam270200_X46(Stock.GetParameterValue("param270200_X46"));
		this.setParam270200_X47(Stock.GetParameterValue("param270200_X47"));
		this.setParam270200_X48(Stock.GetParameterValue("param270200_X48"));
		this.setParam270200_X49(Stock.GetParameterValue("param270200_X49"));
		this.setParam270200_X50(Stock.GetParameterValue("param270200_X50"));
		this.setParam270200_X51(Stock.GetParameterValue("param270200_X51"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&PART_MODULE_LOV="+PART_MODULE_LOV+"&param270200="+param270200+"&param270200_X1="+param270200_X1+"&param270200_X2="+param270200_X2+
				"&param270200_X3="+param270200_X3+"&param270200_X4="+param270200_X4+"&param270200_X5="+param270200_X5+"&param270200_X6="+param270200_X6+"&param270200_X7="+param270200_X7+
				"&param270200_X8="+param270200_X8+"&param270200_X9="+param270200_X9+"&param270200_X10="+param270200_X10+"&param270200_X11="+param270200_X11+"&param270200_X12="+param270200_X12+
				"&param270200_X13="+param270200_X13+"&param270200_X14="+param270200_X14+"&param270200_X15="+param270200_X15+"&param270200_X16="+param270200_X16+"&param270200_X17="+param270200_X17+
				"&param270200_X18="+param270200_X18+"&param270200_X19="+param270200_X19+"&param270200_X20="+param270200_X20+"&param270200_X21="+param270200_X21+"&param270200_X22="+param270200_X22+
				"&param270200_X23="+param270200_X23+"&param270200_X24="+param270200_X24+"&param270200_X25="+param270200_X25+"&param270200_X26="+param270200_X26+"&param270200_X27="+param270200_X27+
				"&param270200_X28="+param270200_X28+"&param270200_X29="+param270200_X29+"&param270200_X30="+param270200_X30+"&param270200_X31="+param270200_X31+"&param270200_X32="+param270200_X32+
				"&param270200_X33="+param270200_X33+"&param270200_X34="+param270200_X34+"&param270200_X35="+param270200_X35+"&param270200_X36="+param270200_X36+"&param270200_X37="+param270200_X37+
				"&param270200_X38="+param270200_X38+"&param270200_X39="+param270200_X39+"&param270200_X40="+param270200_X40+"&param270200_X41="+param270200_X41+"&param270200_X42="+param270200_X42+
				"&param270200_X43="+param270200_X43+"&param270200_X44="+param270200_X44+"&param270200_X45="+param270200_X45+"&param270200_X46="+param270200_X46+"&param270200_X47="+param270200_X47+
				"&param270200_X48="+param270200_X48+"&param270200_X49="+param270200_X49+"&param270200_X50="+param270200_X50+"&param270200_X51="+param270200_X51+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for APFM service", this.queryString.replaceAll("&", "\n"), false);		
		
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run APFM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run APFM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
	
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"ACTY CODE: "+ doc.getElementsByTagName("APACTY_ACTY_CODE_0").item(0).getTextContent()+
					"\nAMOUNT: "+ doc.getElementsByTagName("APACTY_AMOUNT_0").item(0).getTextContent()+
					"\nAUTH IND: "+ doc.getElementsByTagName("APACTY_AUTH_IND_0").item(0).getTextContent()+
					"\nMAILING NAME: "+ doc.getElementsByTagName("APACTY_MAILING_NAME_1_0").item(0).getTextContent()+
					"\nEV ID: "+ doc.getElementsByTagName("APACTY_EV_ID_0").item(0).getTextContent()+
					"\nGA ID: "+ doc.getElementsByTagName("APACTY_GA_ID_0").item(0).getTextContent()+
					"\nPAYMENT STATUS: "+ doc.getElementsByTagName("APACTY_SAP_PAYMENT_STATUS_CODE_0").item(0).getTextContent()+
					"\nEFFDATE: "+ doc.getElementsByTagName("APACTY_EFFDATE_0").item(0).getTextContent()+
					"\nSSN: "+ doc.getElementsByTagName("APACTY_SSN_0").item(0).getTextContent()+
					"\nTRANS CONTROL LABEL1: "+ doc.getElementsByTagName("CFG_CONTROL_TRAN_LABEL_0").item(0).getTextContent()+
					"\nTRANS CONTROL LABEL2: "+ doc.getElementsByTagName("CFG_CONTROL_TRAN_LABEL2_0").item(0).getTextContent()
					, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String payment_status = null;
		String PAYMENT_STATUS = doc.getElementsByTagName("APACTY_SAP_PAYMENT_STATUS_CODE_0").item(0).getTextContent();
		String EV_ID = doc.getElementsByTagName("APACTY_EV_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPFM")[1], this.INDIVIDUAL_SSN_0, EV_ID, PAYMENT_STATUS);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", "Records exists for Account Transaction\nTable Name: AP_ACTY", false);
			payment_status = queryResultSet.getString("SAP_PAYMENT_STATUS_CODE");
			Reporter.logEvent(Status.INFO, "Values From Database: ", 					
					"ID: "+queryResultSet.getString("ID")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
					"\nSTATUS : "+queryResultSet.getString("SAP_PAYMENT_STATUS_CODE")
					, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(PAYMENT_STATUS.equalsIgnoreCase(payment_status)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating SAP PAYMENT STATUS from Response and Database", "Both the values are same as Expected"+
					"\nFrom Response: "+PAYMENT_STATUS+"\nFrom Database: "+payment_status, false);
		}
	}
}
