package pageobject.INVO;

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

public class INVO_Create {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/INVO_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DEP_INTO_SDIO_I_LOV0;
	String DEP_EFFDATE_0;
	String DEP_INCOMING_DE_LOV1;
	String DEP_CASH_IO_ACT_LOV3;
	String DEP_TRF_IO_ACTY_LOV5;
	String DEP_REN_IO_ACTY_LOV7;
	String DEP_LOAN_IO_ACT_LOV9;
	String DEP_DEF_DEP_PER_LOV10;
	
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
	public void setDEP_INTO_SDIO_I_LOV0(String dEP_INTO_SDIO_I_LOV0) {
		DEP_INTO_SDIO_I_LOV0 = dEP_INTO_SDIO_I_LOV0;
	}
	public void setDEP_EFFDATE_0(String dEP_EFFDATE_0) {
		DEP_EFFDATE_0 = dEP_EFFDATE_0;
	}
	public void setDEP_INCOMING_DE_LOV1(String dEP_INCOMING_DE_LOV1) {
		DEP_INCOMING_DE_LOV1 = dEP_INCOMING_DE_LOV1;
	}
	public void setDEP_CASH_IO_ACT_LOV3(String dEP_CASH_IO_ACT_LOV3) {
		DEP_CASH_IO_ACT_LOV3 = dEP_CASH_IO_ACT_LOV3;
	}
	public void setDEP_TRF_IO_ACTY_LOV5(String dEP_TRF_IO_ACTY_LOV5) {
		DEP_TRF_IO_ACTY_LOV5 = dEP_TRF_IO_ACTY_LOV5;
	}
	public void setDEP_REN_IO_ACTY_LOV7(String dEP_REN_IO_ACTY_LOV7) {
		DEP_REN_IO_ACTY_LOV7 = dEP_REN_IO_ACTY_LOV7;
	}
	public void setDEP_LOAN_IO_ACT_LOV9(String dEP_LOAN_IO_ACT_LOV9) {
		DEP_LOAN_IO_ACT_LOV9 = dEP_LOAN_IO_ACT_LOV9;
	}
	public void setDEP_DEF_DEP_PER_LOV10(String dEP_LOAN_IO_ACT_LOV10) {
		DEP_DEF_DEP_PER_LOV10 = dEP_LOAN_IO_ACT_LOV10;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDEP_CASH_IO_ACT_LOV3(Stock.GetParameterValue("DEP_CASH_IO_ACT_LOV3"));
		this.setDEP_EFFDATE_0(Stock.GetParameterValue("DEP_EFFDATE_0"));
		this.setDEP_INCOMING_DE_LOV1(Stock.GetParameterValue("DEP_INCOMING_DE_LOV1"));
		this.setDEP_INTO_SDIO_I_LOV0(Stock.GetParameterValue("DEP_INTO_SDIO_I_LOV0"));
		this.setDEP_DEF_DEP_PER_LOV10(Stock.GetParameterValue("DEP_DEF_DEP_PER_LOV10"));
		this.setDEP_LOAN_IO_ACT_LOV9(Stock.GetParameterValue("DEP_LOAN_IO_ACT_LOV9"));
		this.setDEP_REN_IO_ACTY_LOV7(Stock.GetParameterValue("DEP_REN_IO_ACTY_LOV7"));
		this.setDEP_TRF_IO_ACTY_LOV5(Stock.GetParameterValue("DEP_TRF_IO_ACTY_LOV5"));
				
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&DEP_INTO_SDIO_I_LOV0="+DEP_INTO_SDIO_I_LOV0+"&DEP_EFFDATE_0="+DEP_EFFDATE_0+"&DEP_INCOMING_DE_LOV1="+DEP_INCOMING_DE_LOV1+"&DEP_CASH_IO_ACT_LOV3="+DEP_CASH_IO_ACT_LOV3+
				"&DEP_TRF_IO_ACTY_LOV5="+DEP_TRF_IO_ACTY_LOV5+"&DEP_REN_IO_ACTY_LOV7="+DEP_REN_IO_ACTY_LOV7+"&DEP_LOAN_IO_ACT_LOV9="+DEP_LOAN_IO_ACT_LOV9+"&DEP_DEF_DEP_PER_LOV10="+DEP_DEF_DEP_PER_LOV10+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for INVO service", this.queryString.replaceAll("&", "\n"), false);
		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run INVO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run INVO service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From Response", "DEP TYPE: "+doc.getElementsByTagName("DEP_CASH_IO_ACTY_DEP_TYPE_0").item(0).getTextContent()+
					"\nSDIO ID: "+doc.getElementsByTagName("DEP_INTO_SDIO_ID_0").item(0).getTextContent(), false);
			}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String sdio_id = doc.getElementsByTagName("DEP_INTO_SDIO_ID_0").item(0).getTextContent();
		String effdate = this.DEP_EFFDATE_0;
		String incoming_dep_type = doc.getElementsByTagName("DEP_LOAN_IO_ACTY_DEP_TYPE_0").item(0).getTextContent();
		String perd_dep_type = doc.getElementsByTagName("DEP_REN_IO_ACTY_DEP_TYPE_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForINVO")[1], sdio_id, effdate, incoming_dep_type, perd_dep_type);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists for Event", false);			
				Reporter.logEvent(Status.PASS, "From DATABASE: \nTable Name: DEP_TYPE_SEL", "INCOMING_DEP_TYPE: "+queryResultSet.getString("INCOMING_DEP_TYPE")+
						"\nSDIO_ID: "+queryResultSet.getString("INTO_SDIO_ID")+
						"\nDEF_DEP_PERD_DEP_TYPE: "+queryResultSet.getString("DEF_DEP_PERD_DEP_TYPE")+
						"\nTRF_IO_ACTY_DEP_TYPE: "+queryResultSet.getString("TRF_IO_ACTY_DEP_TYPE")+
						"\nREN_IO_ACTY_DEP_TYPE: "+queryResultSet.getString("REN_IO_ACTY_DEP_TYPE"), false);
			}
			/*if(prov_company_input.equalsIgnoreCase(prov_company_Db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating providing Company from Input and database", "Both the values are same as expected"+
						"\nInput: "+prov_company_input+
						"\nDatabase: "+prov_company_Db, false);	
			}else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating providing Company from Input and database", "Both the values are not same"+
						"\nInput: "+prov_company_input+
						"\nDatabase: "+prov_company_Db, false);	
			}*/
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
	}
}
