package pageobject.TMCA;

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

public class TMCA_tax_mass_calc_build {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/TMCA_tax_mass_calc_build";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;
	String ln_Tax="LN_TAX";
	
	String Event_ID;
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String param9197;
	String NAME_DATE_EFFECTIVE_DATE_0;
	String TAX_ASSER_AVAILABLE_0;
	String IRS_RULES_AVAILABLE_0;
	String REASONS_AVAILABLE_0;

	
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
	public void setParam9197(String param9197) {
		this.param9197 = param9197;
	}
	public void setNAME_DATE_EFFECTIVE_DATE_0(String nAME_DATE_EFFECTIVE_DATE_0) {
		NAME_DATE_EFFECTIVE_DATE_0 = nAME_DATE_EFFECTIVE_DATE_0;
	}
	public void setTAX_ASSER_AVAILABLE_0(String tAX_ASSER_AVAILABLE_0) {
		TAX_ASSER_AVAILABLE_0 = tAX_ASSER_AVAILABLE_0;
	}
	public void setIRS_RULES_AVAILABLE_0(String iRS_RULES_AVAILABLE_0) {
		IRS_RULES_AVAILABLE_0 = iRS_RULES_AVAILABLE_0;
	}
	public void setREASONS_AVAILABLE_0(String rEASONS_AVAILABLE_0) {
		REASONS_AVAILABLE_0 = rEASONS_AVAILABLE_0;
	}
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam9197(Stock.GetParameterValue("param9197"));
		 this.setNAME_DATE_EFFECTIVE_DATE_0(Stock.GetParameterValue("NAME_DATE_EFFECTIVE_DATE_0"));
		 this.setTAX_ASSER_AVAILABLE_0(Stock.GetParameterValue("TAX_ASSER_AVAILABLE_0"));
		 this.setIRS_RULES_AVAILABLE_0(Stock.GetParameterValue("IRS_RULES_AVAILABLE_0"));
		 this.setREASONS_AVAILABLE_0(Stock.GetParameterValue("REASONS_AVAILABLE_0"));
		 
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&param9197="+param9197+
				 "&NAME_DATE_EFFECTIVE_DATE_0="+NAME_DATE_EFFECTIVE_DATE_0+
				 "&TAX_ASSER_AVAILABLE_0="+TAX_ASSER_AVAILABLE_0+
				 "&IRS_RULES_AVAILABLE_0="+IRS_RULES_AVAILABLE_0+
				 "&REASONS_AVAILABLE_0="+REASONS_AVAILABLE_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for TMCA service", this.queryString.replaceAll("&", "\n"), false);
		 
	}
	
public void deleteInDatabase() throws SQLException{
	
	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMCA")[1], ln_Tax,NAME_DATE_EFFECTIVE_DATE_0);
	if(DB.getRecordSetCount(queryResultSet)>0){
		Reporter.logEvent(Status.INFO, "Validating Records in Database", "There are Records in the Database.", false);
		while(queryResultSet.next()){
			queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMCADelete")[1], ln_Tax,NAME_DATE_EFFECTIVE_DATE_0);
			Reporter.logEvent(Status.PASS, "Deleting record in Database", "Records Deleted.", false);
		}
	}else{
		Reporter.logEvent(Status.FAIL, "Validating Records in Database", "There are Records in the Database to delete.", false);
	}
	
		
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
				Reporter.logEvent(Status.PASS, "Run  TMCA service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run TMCA service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		ln_Tax = doc.getElementsByTagName("NAME_DATE_TAXABLE_PROCEDURE_0").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTMCA")[1], ln_Tax,NAME_DATE_EFFECTIVE_DATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Record generated in Database", "Record is generated in Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "IRSRL_CODE: "+queryResultSet.getString("IRSRL_CODE")+
						"\nTXAE_CODE: "+queryResultSet.getString("TXAE_CODE")+
						"\nFUNC_TAX_AREA_CODE: "+queryResultSet.getString("FUNC_TAX_AREA_CODE")+
						"\nTAX_REASON_CODE: "+queryResultSet.getString("TAX_REASON_CODE")+
						"\nTAXABLE_PROC_CODE: "+queryResultSet.getString("TAXABLE_PROC_CODE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Record generated in Database", "Record is not generated in Database", false);
		}
		
		
	}
}
