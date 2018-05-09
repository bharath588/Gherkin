package pageobject.UCSR;

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

public class UCSR_User_Class_Setup_Rule {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RVRR_View_PPT_Fees";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String BUSINESS_CODE_L_LOV;
	String ROLE_L_LOV;
	String USER_CLASS_LOV;
	
	
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
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
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getBUSINESS_CODE_L_LOV() {
		return BUSINESS_CODE_L_LOV;
	}
	public void setBUSINESS_CODE_L_LOV(String bUSINESS_CODE_L_LOV) {
		BUSINESS_CODE_L_LOV = bUSINESS_CODE_L_LOV;
	}
	public String getROLE_L_LOV() {
		return ROLE_L_LOV;
	}
	public void setROLE_L_LOV(String rOLE_L_LOV) {
		ROLE_L_LOV = rOLE_L_LOV;
	}
	public String getUSER_CLASS_LOV() {
		return USER_CLASS_LOV;
	}
	public void setUSER_CLASS_LOV(String uSER_CLASS_LOV) {
		USER_CLASS_LOV = uSER_CLASS_LOV;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setBUSINESS_CODE_L_LOV(Stock.GetParameterValue("BUSINESS_CODE_L_LOV"));
		this.setROLE_L_LOV(Stock.GetParameterValue("ROLE_L_LOV"));
		this.setUSER_CLASS_LOV(Stock.GetParameterValue("USER_CLASS_LOV"));
	
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&BUSINESS_CODE_L_LOV="+BUSINESS_CODE_L_LOV
				+"&ROLE_L_LOV="+ROLE_L_LOV
				+"&USER_CLASS_LOV="+USER_CLASS_LOV
				+"&numOfRowsInTable=20&json=false&handlePopups=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for UCSR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run UCSR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run UCSR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String uscs_id = "";
		String biz_Code="";
		String role = "";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("userClassSetupQuery")[1]);
		
		while(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Verify if the user class setup rule created", "The user class setup rule created", false);
			uscs_id = queryResultSet.getString("USCS_ID");
			biz_Code = queryResultSet.getString("BUSINESS_CODE");
			role = queryResultSet.getString("ROLE");
		}
		
		Reporter.logEvent(Status.INFO, "The uscs id is : \n", uscs_id, false);
		Reporter.logEvent(Status.INFO, "The business_code is : \n", biz_Code, false);
		Reporter.logEvent(Status.INFO, "The role is : \n", role, false);
	}
	
}
