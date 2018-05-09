/**
 * 
 */
package pageobject.SPMC;

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

/**
 * @author smykjn
 *
 */
public class SPMC_QUERY_FREQ {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SPMC_QUERY_FREQ";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String dmty_code;
	String end_date;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String SELECTION_FREQUENCY_0;
	String SELECTION_BEGIN_EFFDATE_0;
	String SELECTION_END_EFFDATE_0;
	
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
	public void setSELECTION_FREQUENCY_0(String sELECTION_FREQUENCY_0) {
		SELECTION_FREQUENCY_0 = sELECTION_FREQUENCY_0;
	}
	public void setSELECTION_BEGIN_EFFDATE_0(String sELECTION_BEGIN_EFFDATE_0) {
		SELECTION_BEGIN_EFFDATE_0 = sELECTION_BEGIN_EFFDATE_0;
	}
	public void setSELECTION_END_EFFDATE_0(String sELECTION_END_EFFDATE_0) {
		SELECTION_END_EFFDATE_0 = sELECTION_END_EFFDATE_0;
	}
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSELECTION_BEGIN_EFFDATE_0(Stock.GetParameterValue("SELECTION_BEGIN_EFFDATE_0"));
		this.setSELECTION_END_EFFDATE_0(Stock.GetParameterValue("SELECTION_END_EFFDATE_0"));
		this.setSELECTION_FREQUENCY_0(Stock.GetParameterValue("SELECTION_FREQUENCY_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&SELECTION_FREQUENCY_0="+SELECTION_FREQUENCY_0+"&SELECTION_BEGIN_EFFDATE_0="+SELECTION_BEGIN_EFFDATE_0+"&SELECTION_END_EFFDATE_0="+SELECTION_END_EFFDATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SPMC_QUERY_FREQ service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run SPMC_QUERY_FREQ service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SPMC_QUERY_FREQ service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		dmty_code = doc.getElementsByTagName("GROUP_ACCOUNT_DMTY_CODE_0").item(0).getTextContent();
		end_date = doc.getElementsByTagName("GROUP_ACCOUNT_END_EFFDATE_0").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "CFG_CONTROL_CANVAS_HEADER_0: "+ doc.getElementsByTagName("CFG_CONTROL_CANVAS_HEADER_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_PART_COUNT_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_PART_COUNT_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_DMTY_CODE_0: "+ dmty_code+
					"\nGROUP_ACCOUNT_END_EFFDATE_0: "+ end_date+
					"\nGROUP_ACCOUNT_GA_ID_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_GC_NAME_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_GC_NAME_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_HAND_CODE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_HAND_CODE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_IRSRL_CODE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_IRSRL_CODE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_RELEASED_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_RELEASED_DATE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_STMT_RECEIVED_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_STMT_RECEIVED_DATE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_STMT_MAIL_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_STMT_MAIL_DATE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_PLSM_RECEIVED_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_PLSM_RECEIVED_DATE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_PLSM_MAIL_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_PLSM_MAIL_DATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{

		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("SPMC_QUERY_FREQ")[1],
				this.SELECTION_FREQUENCY_0,this.SELECTION_BEGIN_EFFDATE_0,this.SELECTION_END_EFFDATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Verifies whether record exists in Database", "Records exists in Database\nTableName: STMT_ACTY_PERD\n"
					+ "AG_INCL\n"
					+ "ACCT_GRPG\n" 
					+ "PRODUCT \n"
					+ "STMT_HANDLING \n"
					+ "GROUP HEADER\n"
					+ "\nSTMT_TO_DO", false);
			if(queryResultSet.next()){
				String freq = queryResultSet.getString("DMTY_CODE");
				String endDateDB = queryResultSet.getString("END_EFFDATE");
				if(freq.equals(dmty_code)&&endDateDB.equals(end_date))
					Reporter.logEvent(Status.PASS,"Validate response from DB.","Response matches with DB.",false);
				else
					Reporter.logEvent(Status.FAIL,"Validate response from DB.","Response doest not match with DB.",false);
				
				Reporter.logEvent(Status.INFO, "Values from Database","\nDMTY_CODE: "+queryResultSet.getString("DMTY_CODE")+
						"\nIRSRL_CODE: "+queryResultSet.getString("IRSRL_CODE")+
						"\nENd_EFFDATE: "+endDateDB, false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Verifies whether records exist in Database.", "Records do not exist into Database.", false);
		}
	}

}
