package pageobject.PREN;

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

public class PREN_QueryMode {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PREN_QUERYMODE";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	
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
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&CONTROL_SSN_DISPL_0=" + CONTROL_SSN_DISPL_0 
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for QYPA service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run QYPA service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run QYPA service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","CONTROL_BLOCK_GA_ID_0: "+doc.getElementsByTagName("CONTROL_BLOCK_GA_ID_0").item(0).getTextContent()+
					"\nCONTROL_BLOCK_SSN_0: "+doc.getElementsByTagName("CONTROL_BLOCK_SSN_0").item(0).getTextContent()+
					"\nCONTROL_BLOCK_PROD_ID_0: "+doc.getElementsByTagName("CONTROL_BLOCK_PROD_ID_0").item(0).getTextContent()+
					"\nCONTROL_BLOCK_PLAN_NAME_0: "+doc.getElementsByTagName("CONTROL_BLOCK_PLAN_NAME_0").item(0).getTextContent()+
					"\nGFTR_TODO_TO_SDIO_ID_0: "+doc.getElementsByTagName("GFTR_TODO_TO_SDIO_ID_0").item(0).getTextContent()+
					"\nGFTR_TODO_LEGAL_NAME_0: "+doc.getElementsByTagName("GFTR_TODO_LEGAL_NAME_0").item(0).getTextContent()+
					"\nDEFAULT_ALLOC_CODE_HIST_PART_OPT_OUT_METHOD_0: "+doc.getElementsByTagName("DEFAULT_ALLOC_CODE_HIST_PART_OPT_OUT_METHOD_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
		
		String part_opt_out_mthd = doc.getElementsByTagName("DEFAULT_ALLOC_CODE_HIST_PART_OPT_OUT_METHOD_0").item(0).getTextContent();
		String part_opt_out_mthd_db = null;
		String ssn = doc.getElementsByTagName("CONTROL_BLOCK_SSN_0").item(0).getTextContent();
		String ga_id = doc.getElementsByTagName("CONTROL_BLOCK_GA_ID_0").item(0).getTextContent();
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPREN")[1], ssn, ga_id);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: FUND_UPDATE_REQUEST"+
					"\nFUND_UPDATE_PART_LIST"+"\nDEFAULT_ALLOC_CODE_HIST", false);
			while(queryResultSet.next()){
				part_opt_out_mthd_db = queryResultSet.getString("PART_OPT_OUT_METHOD");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "GA_ID:  "+ queryResultSet.getString("GA_ID")+
		 				"\nIND_ID: "+ queryResultSet.getString("IND_ID")+
						"\nSSN: "+queryResultSet.getString("SSN")+
						"\nPART_OPT_OUT_METHOD: "+queryResultSet.getString("PART_OPT_OUT_METHOD"), false);
			}
			if(part_opt_out_mthd.contains(part_opt_out_mthd_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating PART_OPT_OUT_METHOD from Response and Database", "Both the values are same as expected"+
						"\nFrom Response: "+part_opt_out_mthd+"\nFrom Database: "+part_opt_out_mthd_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating PART_OPT_OUT_METHOD from Response and Database", "Both the values are not same "+
						"\nFrom Response: "+part_opt_out_mthd+"\nFrom Database: "+part_opt_out_mthd_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
}
