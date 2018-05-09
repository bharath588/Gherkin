package pageobject.SOIS;

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

public class SOIS_Order_Interim_by_Group {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SOIS_Order_Interim_by_Group_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	private ResultSet queryResultSet3;
	private ResultSet queryResultSetbeforeExe;
	
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String CONTROL_SELECTION_0_X1;
	String GROUP_HEADER_GA_ID_0;
	String PRT_SEL_DELIVERY_OPTION_EMAIL_0;
	
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}
	public void setGROUP_HEADER_GA_ID_0(String gROUP_HEADER_GA_ID_0) {
		GROUP_HEADER_GA_ID_0 = gROUP_HEADER_GA_ID_0;
	}
	public void setPRT_SEL_DELIVERY_OPTION_EMAIL_0(
			String pRT_SEL_DELIVERY_OPTION_EMAIL_0) {
		PRT_SEL_DELIVERY_OPTION_EMAIL_0 = pRT_SEL_DELIVERY_OPTION_EMAIL_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		this.setGROUP_HEADER_GA_ID_0(Stock.GetParameterValue("GROUP_HEADER_GA_ID_0"));
		this.setPRT_SEL_DELIVERY_OPTION_EMAIL_0(Stock.GetParameterValue("PRT_SEL_DELIVERY_OPTION_EMAIL_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&CONTROL_SELECTION_0_X1=" +CONTROL_SELECTION_0_X1+ "&GROUP_HEADER_GA_ID_0="+ GROUP_HEADER_GA_ID_0
	//			+"&PRT_SEL_DELIVERY_OPTION_EMAIL_0=" + PRT_SEL_DELIVERY_OPTION_EMAIL_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false";		
		
		queryResultSetbeforeExe = DB.executeQuery(General.dbInstance, Stock.getTestQuery("querySOISbeforEXE")[1], GROUP_HEADER_GA_ID_0);
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SOIS service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SOIS_Order_Interim_by_Group service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SOIS_Order_Interim_by_Group service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From RESPONSE: ", "GA_ID: " + doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent()+
					"\nBEGIN_DATE: "+ doc.getElementsByTagName("IND_HDR_TOP_BEGIN_DATE_0").item(0).getTextContent()+
					"\nEND_DATE: "+ doc.getElementsByTagName("IND_HDR_TOP_END_DATE_0").item(0).getTextContent()+
					"\nCURRENT_DATE: "+ doc.getElementsByTagName("IND_HDR_TOP_CURRDATE_0").item(0).getTextContent()+
					"\nDMTY_CODE: "+ doc.getElementsByTagName("IND_HDR_TOP_DMTY_CODE_0").item(0).getTextContent()+
					"\nSSN: "+ doc.getElementsByTagName("IND_HEADER_SSN_0").item(0).getTextContent()+
					"\nPLAN NAME: "+doc.getElementsByTagName("IND_HDR_TOP_PLAN_NAME_0").item(0).getTextContent(), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		String evnt_id = null;
	
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSOISbyGrp1")[1], GROUP_HEADER_GA_ID_0);
		if(queryResultSet1.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE:" , "Table: stmt_acty_perd"+"\nDMTY_CODE: " +queryResultSet1.getString("DMTY_CODE")+
					"\nAG_ID: " +queryResultSet1.getString("AG_ID")+
					"\nBEGIN_EFFDATE: " +queryResultSet1.getString("BEGIN_EFFDATE")+
					"\nEND_EFFDATE: " +queryResultSet1.getString("END_EFFDATE"), false);		
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSOISbyGrp2")[1], GROUP_HEADER_GA_ID_0);
		while(queryResultSet2.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			evnt_id = queryResultSet2.getString("ID");
			Reporter.logEvent(Status.INFO, "Values From DATABASE:" , "Table: Event"+"\nID: " +queryResultSet2.getString("ID")+
					"\nMASTER_EV_ID: " +queryResultSet2.getString("MASTER_EV_ID")+
					"\nEVTY_CODE: " +queryResultSet2.getString("EVTY_CODE")+
					"\nSUBJECT_ID: " +queryResultSet2.getString("SUBJECT_ID")+
					"\nWKUN_SHORT_NAME: " +queryResultSet2.getString("WKUN_SHORT_NAME"), false);		
		}
		
		queryResultSet3 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSOISbyGrp3")[1], evnt_id);
		if(queryResultSet3.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE:" , "Table: Step"+"\nEV_ID: " +queryResultSet3.getString("EV_ID")+
					"\nSEQNBR: " +queryResultSet3.getString("SEQNBR")+
					"\nEVTY_CODE: " +queryResultSet3.getString("EVTY_CODE")+
					"\nSETY_CODE: " +queryResultSet3.getString("SETY_CODE")+
					"\nWKUN_SHORT_NAME: " +queryResultSet3.getString("WKUN_SHORT_NAME"), false);		
		}
	}
}
