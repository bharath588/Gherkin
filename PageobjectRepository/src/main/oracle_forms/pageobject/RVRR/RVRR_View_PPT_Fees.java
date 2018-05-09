package pageobject.RVRR;

import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class RVRR_View_PPT_Fees {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RVRR_View_PPT_Fees";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String LOGON_DATABASE;
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
	public String getGROUP_ACCOUNT_ID_0() {
		return GROUP_ACCOUNT_ID_0;
	}
	public void setGROUP_ACCOUNT_ID_0(String gROUP_ACCOUNT_ID_0) {
		GROUP_ACCOUNT_ID_0 = gROUP_ACCOUNT_ID_0;
	}
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SELECTION_0;
	String GROUP_ACCOUNT_ID_0;
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGROUP_ACCOUNT_ID_0(Stock.GetParameterValue("GROUP_ACCOUNT_ID_0"));
	
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&GROUP_ACCOUNT_ID_0="+GROUP_ACCOUNT_ID_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for RVRR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run RVRR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RVRR service", "Running Failed:", false);
		}
	}
	
	String CALC_RECOV_CHRG_AMOUNT_1;
	
	public void validateOutput()
	{
		CALC_RECOV_CHRG_AMOUNT_1 = doc.getElementsByTagName("CALC_RECOV_CHRG_AMOUNT_1").item(0).getTextContent();
		
		if(!CALC_RECOV_CHRG_AMOUNT_1.equals(null) || Float.valueOf(CALC_RECOV_CHRG_AMOUNT_1) > 0)
		{			
			Reporter.logEvent(Status.PASS, "Validate if Able to view fees charged by spcific recovery." ,"User is Able to view fees charged by spcific recovery.",false);
			Reporter.logEvent(Status.INFO, "View Recover Charged amount: From Response","CALC_RECOV_CHRG_AMOUNT_1: "+CALC_RECOV_CHRG_AMOUNT_1,false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate if Able to view fees charged by spcific recovery." ,"User is not Able to view fees charged by spcific recovery.",false);
		}
	}
}
