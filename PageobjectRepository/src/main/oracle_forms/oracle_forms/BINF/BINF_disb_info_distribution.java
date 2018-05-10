package pageobject.BINF;

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

public class BINF_disb_info_distribution {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/BINF_FORM";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String STEP1_QRY_EV_ID_0;
	String DISB_BASIC_REQUESTED_DATE_0;
	String DISB_BASIC_DSRS_CODE_0;
	String DISB_BASIC_TAX_REASON_CODE_0;
	String DISB_BASIC_PART_WD_0;
	
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
	public void setSTEP1_QRY_EV_ID_0(String sTEP1_QRY_EV_ID_0) {
		STEP1_QRY_EV_ID_0 = sTEP1_QRY_EV_ID_0;
	}
	public void setDISB_BASIC_REQUESTED_DATE_0(String dISB_BASIC_REQUESTED_DATE_0) {
		DISB_BASIC_REQUESTED_DATE_0 = dISB_BASIC_REQUESTED_DATE_0;
	}
	public void setDISB_BASIC_DSRS_CODE_0(String dISB_BASIC_DSRS_CODE_0) {
		DISB_BASIC_DSRS_CODE_0 = dISB_BASIC_DSRS_CODE_0;
	}
	public void setDISB_BASIC_TAX_REASON_CODE_0(String dISB_BASIC_TAX_REASON_CODE_0) {
		DISB_BASIC_TAX_REASON_CODE_0 = dISB_BASIC_TAX_REASON_CODE_0;
	}
	public void setDISB_BASIC_PART_WD_0(String dISB_BASIC_PART_WD_0) {
		DISB_BASIC_PART_WD_0 = dISB_BASIC_PART_WD_0;
	}
	
	public void setServiceParameters(String EV_ID) throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setDISB_BASIC_DSRS_CODE_0(Stock.GetParameterValue("DISB_BASIC_DSRS_CODE_0"));
		 this.setDISB_BASIC_PART_WD_0(Stock.GetParameterValue("DISB_BASIC_PART_WD_0"));
		 this.setDISB_BASIC_REQUESTED_DATE_0(Stock.GetParameterValue("DISB_BASIC_REQUESTED_DATE_0"));
		 this.setDISB_BASIC_TAX_REASON_CODE_0(Stock.GetParameterValue("DISB_BASIC_TAX_REASON_CODE_0"));
	//	 this.setSTEP1_QRY_EV_ID_0(Stock.GetParameterValue("STEP1_QRY_EV_ID_0"));
		 this.setSTEP1_QRY_EV_ID_0(EV_ID);
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&STEP1_QRY_EV_ID_0="+STEP1_QRY_EV_ID_0+"&DISB_BASIC_REQUESTED_DATE_0="+DISB_BASIC_REQUESTED_DATE_0+
				 "&DISB_BASIC_DSRS_CODE_0="+DISB_BASIC_DSRS_CODE_0+"&DISB_BASIC_TAX_REASON_CODE_0="+DISB_BASIC_TAX_REASON_CODE_0+
				 "&DISB_BASIC_PART_WD_0="+DISB_BASIC_PART_WD_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for CONH service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run BINF_Disbursement_Information_Individual_Distribution service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run BINF_Disbursement_Information_Individual_Distribution service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.PASS, "From response: ","SSN: "+doc.getElementsByTagName("IND_ONLY_HEADER_SSN_0").item(0).getTextContent()+
				"\nSTATUS_CODE: "+doc.getElementsByTagName("DISB_DETL_STATUS_CODE_0").item(0).getTextContent()+
				"\nBLOCK_MODE: "+doc.getElementsByTagName("VAR_BLK_MODE_0").item(0).getTextContent()+
				"EV_ID: "+doc.getElementsByTagName("VAR_BLK_EV_ID_0").item(0).getTextContent()+
				"\n",false);
		
	}
	
	public void validateInDatabase()throws SQLException{
		queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbInfoBinf1")[1], STEP1_QRY_EV_ID_0);
		if(queryResultSet1.next()){
			System.out.println("request has been created");
			Reporter.logEvent(Status.PASS, "Validate DAtabase:disb_req - Request has been created for indivdual", "EV_ID: "+queryResultSet1.getString("EV_ID")+
					"\nIND_ID: "+queryResultSet1.getString("IND_ID")+
					"\nSTATUS_CODE: "+queryResultSet1.getString("STATUS_CODE"), false);
		}
		
		queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDisbInfoBinf1")[1], STEP1_QRY_EV_ID_0);
		if(queryResultSet2.next()){
			System.out.println("request has been created");
			Reporter.logEvent(Status.PASS, "Validate DAtabase:disb_basic - Request has been created for indivdual", "EV_ID: "+queryResultSet1.getString("EV_ID")+
					"\nIND_ID: "+queryResultSet1.getString("IND_ID")+
					"\nSTATUS_CODE: "+queryResultSet1.getString("STATUS_CODE"), false);
		}
	} 

}