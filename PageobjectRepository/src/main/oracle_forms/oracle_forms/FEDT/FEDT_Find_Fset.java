package pageobject.FEDT;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class FEDT_Find_Fset {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/FEDT_Find_FSET_Generic_edit";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
//	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String FD_ID_0;
	String param270168 ;
	String param270168_X1 ;
	String param270168_X2 ;
	String param270168_X3 ;
	String param270168_X4 ;
	String param270168_X5 ;
	String param270168_X6 ;
	String param270168_X7 ;
	String param270168_X8 ;
	String param270168_X9 ;
	String param270168_X10 ;
	String param270168_X11 ;
	String param270168_X12 ;
	String param270168_X13 ;
	String param270168_X14 ;
	String param270168_X15 ;
	String param270168_X16 ;
	String param270168_X17 ;
	String param270168_X18 ;
	String param270168_X19 ;
	String param270168_X20 ;
	String param270168_X21 ;
	String param270168_X22 ;
	String param270168_X23 ;
	String param270168_X24 ;
	String param270168_X25 ;
	String param270168_X26 ;
	String param270168_X27 ;
	String param270168_X28 ;
	String param270168_X29 ;
	String param270168_X30 ;
	String param270168_X31 ;
	String param270168_X32 ;
	String param270168_X33 ;
	String param270168_X34 ;
	String param270168_X35 ;
	String param270168_X36 ;
	String param270168_X37 ;
	String param270168_X38 ;
	String param270168_X39 ;
	String param270168_X40 ;
	String param270168_X41 ;
	String param270168_X42 ;
	String param270168_X43 ;
	String param270168_X44 ;
	String param270168_X45 ;
	String param270168_X46 ;
	String param270168_X47 ;
	String param270168_X48 ;
	String param270168_X49 ;
	String param270168_X50 ;
	
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
	public void setFD_ID_0(String fD_ID_0) {
		FD_ID_0 = fD_ID_0;
	}
	public void setParam270168(String param270168) {
		this.param270168 = param270168;
	}
	public void setParam270168_X1(String param270168_X1) {
		this.param270168_X1 = param270168_X1;
	}
	public void setParam270168_X2(String param270168_X2) {
		this.param270168_X2 = param270168_X2;
	}
	public void setParam270168_X3(String param270168_X3) {
		this.param270168_X3 = param270168_X3;
	}
	public void setParam270168_X4(String param270168_X4) {
		this.param270168_X4 = param270168_X4;
	}
	public void setParam270168_X5(String param270168_X5) {
		this.param270168_X5 = param270168_X5;
	}
	public void setParam270168_X6(String param270168_X6) {
		this.param270168_X6 = param270168_X6;
	}
	public void setParam270168_X7(String param270168_X7) {
		this.param270168_X7 = param270168_X7;
	}
	public void setParam270168_X8(String param270168_X8) {
		this.param270168_X8 = param270168_X8;
	}
	public void setParam270168_X9(String param270168_X9) {
		this.param270168_X9 = param270168_X9;
	}
	public void setParam270168_X10(String param270168_X10) {
		this.param270168_X10 = param270168_X10;
	}
	public void setParam270168_X11(String param270168_X11) {
		this.param270168_X11 = param270168_X11;
	}
	public void setParam270168_X12(String param270168_X12) {
		this.param270168_X12 = param270168_X12;
	}
	public void setParam270168_X13(String param270168_X13) {
		this.param270168_X13 = param270168_X13;
	}
	public void setParam270168_X14(String param270168_X14) {
		this.param270168_X14 = param270168_X14;
	}
	public void setParam270168_X15(String param270168_X15) {
		this.param270168_X15 = param270168_X15;
	}
	public void setParam270168_X16(String param270168_X16) {
		this.param270168_X16 = param270168_X16;
	}
	public void setParam270168_X17(String param270168_X17) {
		this.param270168_X17 = param270168_X17;
	}
	public void setParam270168_X18(String param270168_X18) {
		this.param270168_X18 = param270168_X18;
	}
	public void setParam270168_X19(String param270168_X19) {
		this.param270168_X19 = param270168_X19;
	}
	public void setParam270168_X20(String param270168_X20) {
		this.param270168_X20 = param270168_X20;
	}
	public void setParam270168_X21(String param270168_X21) {
		this.param270168_X21 = param270168_X21;
	}
	public void setParam270168_X22(String param270168_X22) {
		this.param270168_X22 = param270168_X22;
	}
	public void setParam270168_X23(String param270168_X23) {
		this.param270168_X23 = param270168_X23;
	}
	public void setParam270168_X24(String param270168_X24) {
		this.param270168_X24 = param270168_X24;
	}
	public void setParam270168_X25(String param270168_X25) {
		this.param270168_X25 = param270168_X25;
	}
	public void setParam270168_X26(String param270168_X26) {
		this.param270168_X26 = param270168_X26;
	}
	public void setParam270168_X27(String param270168_X27) {
		this.param270168_X27 = param270168_X27;
	}
	public void setParam270168_X28(String param270168_X28) {
		this.param270168_X28 = param270168_X28;
	}
	public void setParam270168_X29(String param270168_X29) {
		this.param270168_X29 = param270168_X29;
	}
	public void setParam270168_X30(String param270168_X30) {
		this.param270168_X30 = param270168_X30;
	}
	public void setParam270168_X31(String param270168_X31) {
		this.param270168_X31 = param270168_X31;
	}
	public void setParam270168_X32(String param270168_X32) {
		this.param270168_X32 = param270168_X32;
	}
	public void setParam270168_X33(String param270168_X33) {
		this.param270168_X33 = param270168_X33;
	}
	public void setParam270168_X34(String param270168_X34) {
		this.param270168_X34 = param270168_X34;
	}
	public void setParam270168_X35(String param270168_X35) {
		this.param270168_X35 = param270168_X35;
	}
	public void setParam270168_X36(String param270168_X36) {
		this.param270168_X36 = param270168_X36;
	}
	public void setParam270168_X37(String param270168_X37) {
		this.param270168_X37 = param270168_X37;
	}
	public void setParam270168_X38(String param270168_X38) {
		this.param270168_X38 = param270168_X38;
	}
	public void setParam270168_X39(String param270168_X39) {
		this.param270168_X39 = param270168_X39;
	}
	public void setParam270168_X40(String param270168_X40) {
		this.param270168_X40 = param270168_X40;
	}
	public void setParam270168_X41(String param270168_X41) {
		this.param270168_X41 = param270168_X41;
	}
	public void setParam270168_X42(String param270168_X42) {
		this.param270168_X42 = param270168_X42;
	}
	public void setParam270168_X43(String param270168_X43) {
		this.param270168_X43 = param270168_X43;
	}
	public void setParam270168_X44(String param270168_X44) {
		this.param270168_X44 = param270168_X44;
	}
	public void setParam270168_X45(String param270168_X45) {
		this.param270168_X45 = param270168_X45;
	}
	public void setParam270168_X46(String param270168_X46) {
		this.param270168_X46 = param270168_X46;
	}
	public void setParam270168_X47(String param270168_X47) {
		this.param270168_X47 = param270168_X47;
	}
	public void setParam270168_X48(String param270168_X48) {
		this.param270168_X48 = param270168_X48;
	}
	public void setParam270168_X49(String param270168_X49) {
		this.param270168_X49 = param270168_X49;
	}
	public void setParam270168_X50(String param270168_X50) {
		this.param270168_X50 = param270168_X50;
	}

	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setFD_ID_0(Stock.GetParameterValue("FD_ID_0"));

this.setParam270168_X1(Stock.GetParameterValue("Param270168_X1"));
this.setParam270168_X2(Stock.GetParameterValue("Param270168_X2"));
this.setParam270168_X3(Stock.GetParameterValue("Param270168_X3"));
this.setParam270168_X4(Stock.GetParameterValue("Param270168_X4"));
this.setParam270168_X5(Stock.GetParameterValue("Param270168_X5"));
this.setParam270168_X6(Stock.GetParameterValue("Param270168_X6"));
this.setParam270168_X7(Stock.GetParameterValue("Param270168_X7"));
this.setParam270168_X8(Stock.GetParameterValue("Param270168_X8"));
this.setParam270168_X9(Stock.GetParameterValue("Param270168_X9"));
this.setParam270168_X10(Stock.GetParameterValue("Param270168_X10"));
this.setParam270168_X11(Stock.GetParameterValue("Param270168_X11"));
this.setParam270168_X12(Stock.GetParameterValue("Param270168_X12"));
this.setParam270168_X13(Stock.GetParameterValue("Param270168_X13"));
this.setParam270168_X14(Stock.GetParameterValue("Param270168_X14"));
this.setParam270168_X15(Stock.GetParameterValue("Param270168_X15"));
this.setParam270168_X16(Stock.GetParameterValue("Param270168_X16"));
this.setParam270168_X17(Stock.GetParameterValue("Param270168_X17"));
this.setParam270168_X18(Stock.GetParameterValue("Param270168_X18"));
this.setParam270168_X19(Stock.GetParameterValue("Param270168_X19"));
this.setParam270168_X20(Stock.GetParameterValue("Param270168_X20"));
this.setParam270168_X21(Stock.GetParameterValue("Param270168_X21"));
this.setParam270168_X22(Stock.GetParameterValue("Param270168_X22"));
this.setParam270168_X23(Stock.GetParameterValue("Param270168_X23"));
this.setParam270168_X24(Stock.GetParameterValue("Param270168_X24"));
this.setParam270168_X25(Stock.GetParameterValue("Param270168_X25"));
this.setParam270168_X26(Stock.GetParameterValue("Param270168_X26"));
this.setParam270168_X27(Stock.GetParameterValue("Param270168_X27"));
this.setParam270168_X28(Stock.GetParameterValue("Param270168_X28"));
this.setParam270168_X29(Stock.GetParameterValue("Param270168_X29"));
this.setParam270168_X30(Stock.GetParameterValue("Param270168_X30"));
this.setParam270168_X31(Stock.GetParameterValue("Param270168_X31"));
this.setParam270168_X32(Stock.GetParameterValue("Param270168_X32"));
this.setParam270168_X33(Stock.GetParameterValue("Param270168_X33"));
this.setParam270168_X34(Stock.GetParameterValue("Param270168_X34"));
this.setParam270168_X35(Stock.GetParameterValue("Param270168_X35"));
this.setParam270168_X36(Stock.GetParameterValue("Param270168_X36"));
this.setParam270168_X37(Stock.GetParameterValue("Param270168_X37"));
this.setParam270168_X38(Stock.GetParameterValue("Param270168_X38"));
this.setParam270168_X39(Stock.GetParameterValue("Param270168_X39"));
this.setParam270168_X40(Stock.GetParameterValue("Param270168_X40"));
this.setParam270168_X41(Stock.GetParameterValue("Param270168_X41"));
this.setParam270168_X42(Stock.GetParameterValue("Param270168_X42"));
this.setParam270168_X43(Stock.GetParameterValue("Param270168_X43"));
this.setParam270168_X44(Stock.GetParameterValue("Param270168_X44"));
this.setParam270168_X45(Stock.GetParameterValue("Param270168_X45"));
this.setParam270168_X46(Stock.GetParameterValue("Param270168_X46"));
this.setParam270168_X47(Stock.GetParameterValue("Param270168_X47"));
this.setParam270168_X48(Stock.GetParameterValue("Param270168_X48"));
this.setParam270168_X49(Stock.GetParameterValue("Param270168_X49"));
this.setParam270168_X50(Stock.GetParameterValue("Param270168_X50"));


		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0
		 		+ "&FD_ID_0="+FD_ID_0+"&param270168="+param270168+"&param270168_X1="+param270168_X1+"&param270168_X2="+param270168_X2+"&param270168_X3="+param270168_X3
		 		+ "&param270168_X4="+param270168_X4+"&param270168_X5="+param270168_X5+"&param270168_X6="+param270168_X6+"&param270168_X7="+param270168_X7+"&param270168_X8="+param270168_X8+"&param270168_X9="+param270168_X9
		 		+ "&param270168_X10="+param270168_X10+"&param270168_X11="+param270168_X11+"&param270168_X12="+param270168_X12+"&param270168_X13="+param270168_X13+"&param270168_X14="+param270168_X14
		 		+ "&param270168_X14="+param270168_X14+"&param270168_X16="+param270168_X16+"&param270168_X17="+param270168_X17+"&param270168_X18="+param270168_X18+"&param270168_X19="+param270168_X19
		 		+ "&param270168_X20="+param270168_X20+"&param270168_X21="+param270168_X21+"&param270168_X22="+param270168_X22+"&param270168_X23="+param270168_X23+"&param270168_X24="+param270168_X24
		 		+ "&param270168_X25="+param270168_X25+"&param270168_X26="+param270168_X26+"&param270168_X27="+param270168_X27+"&param270168_X28="+param270168_X28+"&param270168_X29="+param270168_X29
		 		+ "&param270168_X30="+param270168_X30+"&param270168_X31="+param270168_X31+"&param270168_X32="+param270168_X32+"&param270168_X33="+param270168_X33+"&param270168_X34="+param270168_X34
		 		+ "&param270168_X35="+param270168_X35+"&param270168_X36="+param270168_X36+"&param270168_X37="+param270168_X37+"&param270168_X38="+param270168_X38+"&param270168_X39="+param270168_X39
		 		+ "&param270168_X40="+param270168_X40+"&param270168_X41="+param270168_X41+"&param270168_X42="+param270168_X42+"&param270168_X43="+param270168_X43+"&param270168_X44="+param270168_X44
		 		+ "&param270168_X45="+param270168_X45+"&param270168_X46="+param270168_X46+"&param270168_X47="+param270168_X47+"&param270168_X48="+param270168_X48+"&param270168_X49="+param270168_X49
		 		+ "&param270168_X50="+param270168_X50+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for FEDT service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  FEDT service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run FEDT service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
//			Reporter.logEvent(Status.INFO, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "Values From response: ","Expected Display Information from Response"+"\nFDF_FIELD_NAME_0: "+doc.getElementsByTagName("FDF_FIELD_NAME_0").item(0).getTextContent()+
				"\nTABLE NAME: "+doc.getElementsByTagName("FDF_TABLE_NAME_0").item(0).getTextContent()+
				"\nFD_CODE_0: "+doc.getElementsByTagName("FD_CODE_0").item(0).getTextContent()+
				"\nFD_DESCR_0: "+doc.getElementsByTagName("FD_DESCR_0").item(0).getTextContent()+"\n",false);
	}

}
