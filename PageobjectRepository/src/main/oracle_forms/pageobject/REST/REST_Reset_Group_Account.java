package pageobject.REST;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class REST_Reset_Group_Account {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/REST_Reset_Group_Account_Statements";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_DATABASE;
	String CONTROL_SELECTION_0;
	String GROUP_ACCT_GA_ID_0;
	String GROUP_ACCT_DMTY_CODE_0;
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getGROUP_ACCT_GA_ID_0() {
		return GROUP_ACCT_GA_ID_0;
	}
	public void setGROUP_ACCT_GA_ID_0(String gROUP_ACCT_GA_ID_0) {
		GROUP_ACCT_GA_ID_0 = gROUP_ACCT_GA_ID_0;
	}
	public String getGROUP_ACCT_DMTY_CODE_0() {
		return GROUP_ACCT_DMTY_CODE_0;
	}
	public void setGROUP_ACCT_DMTY_CODE_0(String gROUP_ACCT_DMTY_CODE_0) {
		GROUP_ACCT_DMTY_CODE_0 = gROUP_ACCT_DMTY_CODE_0;
	}
	
	public void setServiceParameters()
	{
		
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME  
				+"&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&GROUP_ACCT_GA_ID_0="+GROUP_ACCT_GA_ID_0
				+"&GROUP_ACCT_DMTY_CODE_0="+GROUP_ACCT_DMTY_CODE_0	
				+"&numOfRowsInTable=20&json=false&handlePopups=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for REST service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run REST service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run  REST service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryRestGroupAccstmt")[1], this.getGROUP_ACCT_GA_ID_0());
		
		if(DB.getRecordSetCount(queryResultSet) == 0)
		{
		Reporter.logEvent(Status.PASS, "Verify if Rows related to reset GA_ID DMTY_CODE for the date range will be deleted from the STMT_APR table"
				,"Rows related to reset GA_ID DMTY_CODE for the date range is deleted from the STMT_APR "
						+ "table " + "GA_ID is " + this.GROUP_ACCT_GA_ID_0,false);	
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if Rows related to reset GA_ID DMTY_CODE for the date range will be deleted from the STMT_APR table"
					,"Rows related to reset GA_ID DMTY_CODE for the date range is not deleted from the STMT_APR"
							+ " table" + "GA_ID is" + this.getGROUP_ACCT_GA_ID_0(),false);	
		}
		
	}
}
