package pageobject.STMC;

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

public class STMC_Update_Handling_Code {

	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/STMC_Update_Handling_Code";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private int queryResultSet;
	private ResultSet queryResultSet1;
	private ResultSet queryResultSet2;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_START_GA_ID_0;
	String SELECTION_END_GA_ID_0;
	String SELECTION_BEGIN_EFFDATE_0;
	String SELECTION_END_EFFDATE_0;
	String GROUP_ACCOUNT_SELECT_IND_0;
	String GROUP_ACCOUNT_SELECT_IND_1;
	String param9252;
	String GROUP_INFO_INT_EXT_0;

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

	public void setSELECTION_START_GA_ID_0(String sELECTION_START_GA_ID_0) {
		SELECTION_START_GA_ID_0 = sELECTION_START_GA_ID_0;
	}

	public void setSELECTION_END_GA_ID_0(String sELECTION_END_GA_ID_0) {
		SELECTION_END_GA_ID_0 = sELECTION_END_GA_ID_0;
	}

	public void setSELECTION_BEGIN_EFFDATE_0(String sELECTION_BEGIN_EFFDATE_0) {
		SELECTION_BEGIN_EFFDATE_0 = sELECTION_BEGIN_EFFDATE_0;
	}
	
	public void setSELECTION_END_EFFDATE_0(String sELECTION_END_EFFDATE_0) {
		SELECTION_END_EFFDATE_0 = sELECTION_END_EFFDATE_0;
	}
	
	public void setGROUP_ACCOUNT_SELECT_IND_0(String gROUP_ACCOUNT_SELECT_IND_0) {
		GROUP_ACCOUNT_SELECT_IND_0 = gROUP_ACCOUNT_SELECT_IND_0;
	}
	
	public void setGROUP_ACCOUNT_SELECT_IND_1(String gROUP_ACCOUNT_SELECT_IND_1) {
		GROUP_ACCOUNT_SELECT_IND_1 = gROUP_ACCOUNT_SELECT_IND_1;
	}
	
	public void setparam9252(String Param9252) {
		param9252 = Param9252;
	}
	
	public void setGROUP_INFO_INT_EXT_0(String gROUP_INFO_INT_EXT_0) {
		GROUP_INFO_INT_EXT_0 = gROUP_INFO_INT_EXT_0;
	}

	public void setServiceParameters() {
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setSELECTION_START_GA_ID_0(Stock.GetParameterValue("SELECTION_START_GA_ID_0"));
		this.setSELECTION_END_GA_ID_0(Stock.GetParameterValue("SELECTION_END_GA_ID_0"));
		this.setSELECTION_BEGIN_EFFDATE_0(Stock.GetParameterValue("SELECTION_BEGIN_EFFDATE_0"));
		this.setSELECTION_END_EFFDATE_0(Stock.GetParameterValue("SELECTION_END_EFFDATE_0"));
		this.setGROUP_ACCOUNT_SELECT_IND_0(Stock.GetParameterValue("GROUP_ACCOUNT_SELECT_IND_0"));
		this.setGROUP_ACCOUNT_SELECT_IND_1(Stock.GetParameterValue("GROUP_ACCOUNT_SELECT_IND_1"));
		this.setparam9252(Stock.GetParameterValue("param9252"));
		this.setGROUP_INFO_INT_EXT_0(Stock.GetParameterValue("GROUP_INFO_INT_EXT_0"));
		

		queryString = "?LOGON_USERNAME="
				+LOGON_USERNAME
				+"&LOGON_PASSWORD="
				+LOGON_PASSWORD
				+"&LOGON_DATABASE="
				+LOGON_DATABASE
				+"&CONTROL_SELECTION_0="
				+CONTROL_SELECTION_0
				+"&SELECTION_START_GA_ID_0="
				+SELECTION_START_GA_ID_0
				+"&SELECTION_END_GA_ID_0="
				+SELECTION_END_GA_ID_0
				+"&SELECTION_BEGIN_EFFDATE_0="
				+SELECTION_BEGIN_EFFDATE_0
				+"&SELECTION_END_EFFDATE_0="
				+SELECTION_END_EFFDATE_0
				+"&GROUP_ACCOUNT_SELECT_IND_0="
				+GROUP_ACCOUNT_SELECT_IND_0
				+"&GROUP_ACCOUNT_SELECT_IND_1="
				+GROUP_ACCOUNT_SELECT_IND_1
				+"&param9252="
				+param9252
				+"&GROUP_INFO_INT_EXT_0="
				+GROUP_INFO_INT_EXT_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for STMC service",
				this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run STMC service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STMC service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() {
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,"Validate response - Error is empty", "Error tag is empty",false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);}
	}
	public void updateInDatabase() throws SQLException {
			try {
				queryResultSet = DB.executeUpdate(General.dbInstance,Stock.getTestQuery("queryForUpdateSTMC")[1]);
				System.out.println(queryResultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE.","Updating values From DATABASE.", false);
		
	}
	public void validateInDatabaseBfrService() throws SQLException {

		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForSTMC")[1]);

		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);

			while (queryResultSet1.next()) {
				String spl_hndl_codeDB = queryResultSet1.getString("STMT_SPECIAL_HANDLING_CODE");
				Reporter.logEvent(Status.INFO,"Checking value of STM_SPECIAL_HANDLING_CODE before running the service.",
						"Value of STM_SPECIAL_HANDLING_CODE before running the service:" +spl_hndl_codeDB , false);
			}
		}
				
				else {
					Reporter.logEvent(
							Status.FAIL,
							"Validating Records exists in Database",
							"Records does not exist in Database.",
							false);
				}
	}
	public void validateInDatabaseAftrService() throws SQLException 
	{
		String exp_value = "Stuffer";
		queryResultSet2 =  DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForSTMC")[1]);
		if (DB.getRecordSetCount(queryResultSet2) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);

			while (queryResultSet2.next()) {
				String spl_hndl_codeDBupdated = queryResultSet2.getString("STMT_SPECIAL_HANDLING_CODE");
				Reporter.logEvent(Status.INFO,"Checking value of STM_SPECIAL_HANDLING_CODE after running the service.",
						"Value of STM_SPECIAL_HANDLING_CODE after running the service:" +spl_hndl_codeDBupdated , false);
			
			if(exp_value.equalsIgnoreCase(spl_hndl_codeDBupdated))
			{
				Reporter.logEvent(Status.PASS,"Validating if STM_SPECIAL_HANDLING_CODE is updated to STUFFER.",
						"Value of STM_SPECIAL_HANDLING_CODE is updated to STUFFER.", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if STM_SPECIAL_HANDLING_CODE is updated to STUFFER.",
						"Value of STM_SPECIAL_HANDLING_CODE is not updated to STUFFER.", false);
			}	
			}
		}
				
        	else {
					Reporter.logEvent(Status.FAIL,"Validating Records exists in Database",
							"Records does not exist in Database.",
							false);}
		}	
	}


	
