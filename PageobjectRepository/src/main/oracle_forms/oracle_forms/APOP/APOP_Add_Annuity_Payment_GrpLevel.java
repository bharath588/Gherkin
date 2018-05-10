package pageobject.APOP;

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
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class APOP_Add_Annuity_Payment_GrpLevel {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/APOP_Add_Annuity_Option_Group_Level";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
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
	String param27067_X15;
	String param27067_X16;
	String param27067_X17;
	String param27067_X18;
	String param27067_X19;
	String param27067_X20;
	String GA_QUAL_ANNUITY_OPTION_EFFDATE_0;
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
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
	public void setParam27067_X15(String param27067_X15) {
		this.param27067_X15 = param27067_X15;
	}
	public void setParam27067_X16(String param27067_X16) {
		this.param27067_X16 = param27067_X16;
	}
	public void setParam27067_X17(String param27067_X17) {
		this.param27067_X17 = param27067_X17;
	}
	public void setParam27067_X18(String param27067_X18) {
		this.param27067_X18 = param27067_X18;
	}
	public void setParam27067_X19(String param27067_X19) {
		this.param27067_X19 = param27067_X19;
	}
	public void setParam27067_X20(String param27067_X20) {
		this.param27067_X20 = param27067_X20;
	}
	public void setGA_QUAL_ANNUITY_OPTION_EFFDATE_0(
			String gA_QUAL_ANNUITY_OPTION_EFFDATE_0) {
		GA_QUAL_ANNUITY_OPTION_EFFDATE_0 = gA_QUAL_ANNUITY_OPTION_EFFDATE_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGA_QUAL_ANNUITY_OPTION_EFFDATE_0(Stock.GetParameterValue("GA_QUAL_ANNUITY_OPTION_EFFDATE_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
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
		this.setParam27067_X15(Stock.GetParameterValue("param27067_X15"));
		this.setParam27067_X16(Stock.GetParameterValue("param27067_X16"));
		this.setParam27067_X17(Stock.GetParameterValue("param27067_X17"));
		this.setParam27067_X18(Stock.GetParameterValue("param27067_X18"));
		this.setParam27067_X19(Stock.GetParameterValue("param27067_X19"));
		this.setParam27067_X20(Stock.GetParameterValue("param27067_X20"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&param27067="+param27067+"&param27067_X1="+param27067_X1+"&param27067_X2="+param27067_X2+"&param27067_X3="+param27067_X3+
				"&param27067_X4="+param27067_X4+"&param27067_X5="+param27067_X5+"&param27067_X6="+param27067_X6+"&param27067_X7="+param27067_X7+
				"&param27067_X8="+param27067_X8+"&param27067_X9="+param27067_X9+"&param27067_X10="+param27067_X10+"&param27067_X11="+param27067_X11+
				"&param27067_X12="+param27067_X12+"&param27067_X13="+param27067_X13+"&param27067_X14="+param27067_X14+"&param27067_X15="+param27067_X15+"&param27067_X16="+param27067_X16+				
				"&param27067_X17="+param27067_X17+"&param27067_X18="+param27067_X18+"&param27067_X19="+param27067_X19+"&param27067_X20="+param27067_X20+"&GA_QUAL_ANNUITY_OPTION_EFFDATE_0="+GA_QUAL_ANNUITY_OPTION_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for APOP service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run APOP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run APOP service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		NodeList list = doc.getElementsByTagName("APOP_Add_Annuity_Option_Standard_LevelArrayItem");
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);		
			Reporter.logEvent(Status.INFO, "Values from response", "ANNUITY_PAYOUT_OPTION_TYPE_CODE_0: " +
			doc.getElementsByTagName("ANNUITY_PAYOUT_OPTION_TYPE_CODE_0").item(0).getTextContent()+
			"\nTotal no of items: "+ list.getLength(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPOPAdd1")[1], this.GET_GA_GA_ID_0, this.GA_QUAL_ANNUITY_OPTION_EFFDATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record in DB", "Records inserted in DB\nTableName: GA_QUAL_ANNUITY_OPTION", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record in DB", "Records not inserted DB\nTableName: GA_QUAL_ANNUITY_OPTION", false);
		}
		
	}
	
	
	public void resetData() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForAPOPReset1")[1], this.GET_GA_GA_ID_0, this.GA_QUAL_ANNUITY_OPTION_EFFDATE_0);
	}
}
