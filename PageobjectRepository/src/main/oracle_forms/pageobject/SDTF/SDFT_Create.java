package pageobject.SDTF;

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

public class SDFT_Create {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SDFT_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DOL_FEE_TYPE_CODE_0;
	String DOL_FEE_TYPE_DESCR_0;
	String DOL_FEE_TYPE_FUNCTIONAL_AREA_0;
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
	public void setDOL_FEE_TYPE_CODE_0(String dOL_FEE_TYPE_CODE_0) {
		DOL_FEE_TYPE_CODE_0 = dOL_FEE_TYPE_CODE_0;
	}
	public void setDOL_FEE_TYPE_DESCR_0(String dOL_FEE_TYPE_DESCR_0) {
		DOL_FEE_TYPE_DESCR_0 = dOL_FEE_TYPE_DESCR_0;
	}
	public void setDOL_FEE_TYPE_FUNCTIONAL_AREA_0(
			String dOL_FEE_TYPE_FUNCTIONAL_AREA_0) {
		DOL_FEE_TYPE_FUNCTIONAL_AREA_0 = dOL_FEE_TYPE_FUNCTIONAL_AREA_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setDOL_FEE_TYPE_CODE_0(Stock.GetParameterValue("DOL_FEE_TYPE_CODE_0"));
		this.setDOL_FEE_TYPE_DESCR_0(Stock.GetParameterValue("DOL_FEE_TYPE_DESCR_0"));
		this.setDOL_FEE_TYPE_FUNCTIONAL_AREA_0(Stock.GetParameterValue("DOL_FEE_TYPE_FUNCTIONAL_AREA_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
				"&DOL_FEE_TYPE_CODE_0="+DOL_FEE_TYPE_CODE_0+"&DOL_FEE_TYPE_DESCR_0="+DOL_FEE_TYPE_DESCR_0+"&DOL_FEE_TYPE_FUNCTIONAL_AREA_0="+DOL_FEE_TYPE_FUNCTIONAL_AREA_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SDFT service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run SDFT service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SDFT service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","Messages: "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}			
	}
	
	public void validateInDatabase() throws SQLException{
		String descr = this.DOL_FEE_TYPE_DESCR_0;
		String func_area = this.DOL_FEE_TYPE_FUNCTIONAL_AREA_0;
		String descr_db = null;
		String func_area_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSDFT")[1], this.DOL_FEE_TYPE_CODE_0);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: DOL_FEE_TYPE", false);
			while(queryResultSet.next()){
				descr_db = queryResultSet.getString("DESCR");
				func_area_db = queryResultSet.getString("FUNCTIONAL_AREA");
				
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ","CODE: "+queryResultSet.getString("CODE")+
						"\nDESCR: "+queryResultSet.getString("DESCR")+
						"\nFUNCTIONAL_AREA: "+queryResultSet.getString("FUNCTIONAL_AREA"), false);
			}
			if(descr.equalsIgnoreCase(descr_db) && (func_area.equalsIgnoreCase(func_area_db))){
				Reporter.logEvent(Status.PASS, "Comparing DESCR and FUNCTIONAL AREA from Input and Database", "Both the values are same as expected"+
						"\nDESCR"+"\nFrom Input: "+descr+"\nFrom DB: "+descr_db+
						"\nFUNCTIONAL AREA: "+"\nFrom Input: "+func_area+"\nFrom DB: "+func_area_db, false);
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing DESCR and FUNCTIONAL AREA from Input and Database", "Both the values are not same "+
			"\nDESCR"+"\nFrom Input: "+descr+"\nFrom DB: "+descr_db+
			"\nFUNCTIONAL AREA: "+"\nFrom Input: "+func_area+"\nFrom DB: "+func_area_db, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void Flushdata() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSDFTFlush")[1], this.DOL_FEE_TYPE_CODE_0);
	}
}
