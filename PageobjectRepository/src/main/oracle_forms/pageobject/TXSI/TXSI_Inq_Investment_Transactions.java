package pageobject.TXSI;

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

public class TXSI_Inq_Investment_Transactions {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TXSI_Inquire_Investment_Transactions";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String TCSU1_FAT_CODE_0;
	String TCSU1_SDMT_CODE_0;
	String TCSU1_GDMT_SEQNBR_0;
	String TCSU1_SDDETY_CODE_0;
	String TCSU1_SDIO_ID_0;
	
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setTCSU1_FAT_CODE_0(String tCSU1_FAT_CODE_0) {
		TCSU1_FAT_CODE_0 = tCSU1_FAT_CODE_0;
	}
	public void setTCSU1_SDMT_CODE_0(String tCSU1_SDMT_CODE_0) {
		TCSU1_SDMT_CODE_0 = tCSU1_SDMT_CODE_0;
	}
	public void setTCSU1_GDMT_SEQNBR_0(String tCSU1_GDMT_SEQNBR_0) {
		TCSU1_GDMT_SEQNBR_0 = tCSU1_GDMT_SEQNBR_0;
	}
	public void setTCSU1_SDDETY_CODE_0(String tCSU1_SDDETY_CODE_0) {
		TCSU1_SDDETY_CODE_0 = tCSU1_SDDETY_CODE_0;
	}
	public void setTCSU1_SDIO_ID_0(String tCSU1_SDIO_ID_0) {
		TCSU1_SDIO_ID_0 = tCSU1_SDIO_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setTCSU1_FAT_CODE_0(Stock.GetParameterValue("TCSU1_FAT_CODE_0"));
		this.setTCSU1_GDMT_SEQNBR_0(Stock.GetParameterValue("TCSU1_GDMT_SEQNBR_0"));
		this.setTCSU1_SDDETY_CODE_0(Stock.GetParameterValue("TCSU1_SDDETY_CODE_0"));
		this.setTCSU1_SDIO_ID_0(Stock.GetParameterValue("TCSU1_SDIO_ID_0"));
		this.setTCSU1_SDMT_CODE_0(Stock.GetParameterValue("TCSU1_SDMT_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&TCSU1_FAT_CODE_0="+TCSU1_FAT_CODE_0+
				"&TCSU1_SDMT_CODE_0="+TCSU1_SDMT_CODE_0+"&TCSU1_GDMT_SEQNBR_0="+TCSU1_GDMT_SEQNBR_0+"&TCSU1_SDDETY_CODE_0="+TCSU1_SDDETY_CODE_0+
				"&TCSU1_SDIO_ID_0="+TCSU1_SDIO_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for TXSI service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run TXSI service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run TXSI service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response: ", "Transacion Code: "+ doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
						"\nTransaction Descr: "+ doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String sdio_id = this.TCSU1_SDIO_ID_0;
		String sdio_id_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTXSI1")[1],  this.CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database Records", "Records exists in Database ISIS\nTable Name: TXN_SUMM", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Checking for Database Records", "Records doesn't exist in Database ISIS\nTable Name: TXN_SUMM"+
		"\nTotal number of records: "+DB.getRecordSetCount(queryResultSet), false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTXSI2")[1],  this.CONTROL_SSN_DISPL_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records exists in Database\nTable Name: INVOPT_TXN_MTH", false);
			while(queryResultSet.next()){
				sdio_id_db = queryResultSet.getString("SDIO_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "FAT CODE: "+queryResultSet.getString("FAT_CODE")+
						"\nGA_ID: "+queryResultSet.getString("GA_ID")+
						"\nSDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE")+
						"\nIND_ID: "+queryResultSet.getString("IND_ID")+
						"\nDPAMT: "+queryResultSet.getString("DPAMT"), false);
			}
			if(sdio_id.equalsIgnoreCase(sdio_id_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating SDIO ID from Input and Database", "Both the values are same as expected"+
						"\nFrom Input: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating SDIO ID from Input and Database", "Both the values are not same"+
						"\nFrom Input: "+sdio_id+"\nFrom Database: "+sdio_id_db, false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database\nTable Name: ACCOUNT", false);
		}
	}
}
