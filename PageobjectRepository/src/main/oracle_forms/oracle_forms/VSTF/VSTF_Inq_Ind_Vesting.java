package pageobject.VSTF;

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

public class VSTF_Inq_Ind_Vesting {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/VSTF_Inquiry_Ind_Vesting";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String INDIVIDUAL_SSN_0;
	String GA_ID_LOV;
	String INDIVIDUAL_DETAIL_EFF_DATE_0;
	
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
	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}
	public void setGA_ID_LOV(String gA_ID_LOV) {
		GA_ID_LOV = gA_ID_LOV;
	}
	public void setINDIVIDUAL_DETAIL_EFF_DATE_0(String iNDIVIDUAL_DETAIL_EFF_DATE_0) {
		INDIVIDUAL_DETAIL_EFF_DATE_0 = iNDIVIDUAL_DETAIL_EFF_DATE_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGA_ID_LOV(Stock.GetParameterValue("GA_ID_LOV"));
		this.setINDIVIDUAL_DETAIL_EFF_DATE_0(Stock.GetParameterValue("INDIVIDUAL_DETAIL_EFF_DATE_0"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0+"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0+"&GA_ID_LOV="+GA_ID_LOV+"&INDIVIDUAL_DETAIL_EFF_DATE_0="+INDIVIDUAL_DETAIL_EFF_DATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for VSTF_Inquiry_Ind_Vesting service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VSTF_Inquiry_Ind_Vesting service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VSTF_Inquiry_Ind_Vesting service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		
		if(Error.isEmpty())
		{
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);	
			Reporter.logEvent(Status.INFO, "Values From RESPONSE", "GA_ID: "+doc.getElementsByTagName("INDIVIDUAL_DETAIL_GA_ID_0").item(0).getTextContent()+
					"\nEFFDATE: "+doc.getElementsByTagName("INDIVIDUAL_VESTING_RULE_EFFDATE_0").item(0).getTextContent()+
					"\nTRANSACTION_CODE_0: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
					"\nTRANSACTION_DESCR_0: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()+
					"\nBEGIN_DATE_0: "+doc.getElementsByTagName("VESTING_CMPUT_PERIOD_PERIOD_BEGIN_DATE_0").item(0).getTextContent()+
					"\nEND_DATE_1: "+doc.getElementsByTagName("VESTING_CMPUT_PERIOD_PERIOD_BEGIN_DATE_1").item(0).getTextContent(), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
/*	public void validateInDatabase() throws SQLException{
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSCI")[1], PLAN_VESTING_RULE_GA_ID_0);
		if(queryResultSet.next()){			
			Reporter.logEvent(Status.PASS, "Validating Record creation in database", "Records exists in Database", false);
			Reporter.logEvent(Status.PASS, "From DATABASE:" , "Table: IND_VESTING_RULE"+"\nIND_ID: " +queryResultSet.getString("IND_ID")+
					"\nPLAN_ID: " +queryResultSet.getString("PLAN_ID")+
					"\nSDMT_CODE: " +queryResultSet.getString("SDMT_CODE")+
					"\nGA_ID: " +queryResultSet.getString("GA_ID")+
					"\nEFFDATE: " +queryResultSet.getString("EFFDATE"), false);		
		}
*/	
	
}
