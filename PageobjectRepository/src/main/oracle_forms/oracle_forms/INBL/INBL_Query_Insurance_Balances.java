/**
 * 
 */
package pageobject.INBL;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

/**
 * @author smykjn
 *
 */
public class INBL_Query_Insurance_Balances {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/INBL_Query_insurance_balances";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String WORK_PART_INSURANCE_BAL_GA_ID_0;
	String WORK_PART_INSURANCE_BAL_SSN_0;
	String WORK_PART_INSURANCE_BAL_CASH_VALUE_0;
	String WORK_PART_INSURANCE_BAL_EFFDATE_0;
	String WORK_PART_INSURANCE_BAL_PULL_STATUS_0;
	String WORK_PART_INSURANCE_BAL_DPDATE_TIME_0;
	
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
	public void setWORK_PART_INSURANCE_BAL_GA_ID_0(String work_PART_INSURANCE_BAL_GA_ID_0) {
		WORK_PART_INSURANCE_BAL_GA_ID_0 = work_PART_INSURANCE_BAL_GA_ID_0;
	}
	public void setWORK_PART_INSURANCE_BAL_SSN_0(String work_PART_INSURANCE_BAL_SSN_0) {
		WORK_PART_INSURANCE_BAL_SSN_0 = work_PART_INSURANCE_BAL_SSN_0;
	}
	public void setWORK_PART_INSURANCE_BAL_CASH_VALUE_0(String work_PART_INSURANCE_BAL_CASH_VALUE_0) {
		WORK_PART_INSURANCE_BAL_CASH_VALUE_0 = work_PART_INSURANCE_BAL_CASH_VALUE_0;
	}
	public void setWORK_PART_INSURANCE_BAL_EFFDATE_0(String work_PART_INSURANCE_BAL_EFFDATE_0) {
		WORK_PART_INSURANCE_BAL_EFFDATE_0 = work_PART_INSURANCE_BAL_EFFDATE_0;
	}
	public void setWORK_PART_INSURANCE_BAL_PULL_STATUS_0(String work_PART_INSURANCE_BAL_PULL_STATUS_0) {
		WORK_PART_INSURANCE_BAL_PULL_STATUS_0 = work_PART_INSURANCE_BAL_PULL_STATUS_0;
	}
	public void setWORK_PART_INSURANCE_BAL_DPDATE_TIME_0(String work_PART_INSURANCE_BAL_DPDATE_TIME_0) {
		WORK_PART_INSURANCE_BAL_DPDATE_TIME_0 = work_PART_INSURANCE_BAL_DPDATE_TIME_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setWORK_PART_INSURANCE_BAL_GA_ID_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_GA_ID_0"));	
		this.setWORK_PART_INSURANCE_BAL_SSN_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_SSN_0"));
		this.setWORK_PART_INSURANCE_BAL_CASH_VALUE_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_CASH_VALUE_0"));
		this.setWORK_PART_INSURANCE_BAL_EFFDATE_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_EFFDATE_0"));
		this.setWORK_PART_INSURANCE_BAL_PULL_STATUS_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_PULL_STATUS_0"));
		this.setWORK_PART_INSURANCE_BAL_DPDATE_TIME_0(Stock.GetParameterValue("WORK_PART_INSURANCE_BAL_DPDATE_TIME_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&WORK_PART_INSURANCE_BAL_GA_ID_0="+WORK_PART_INSURANCE_BAL_GA_ID_0+""
						+ "&WORK_PART_INSURANCE_BAL_SSN_0="+WORK_PART_INSURANCE_BAL_SSN_0+
						"&WORK_PART_INSURANCE_BAL_CASH_VALUE_0="+WORK_PART_INSURANCE_BAL_CASH_VALUE_0+
						"&WORK_PART_INSURANCE_BAL_EFFDATE_0="+WORK_PART_INSURANCE_BAL_EFFDATE_0+
						"&WORK_PART_INSURANCE_BAL_PULL_STATUS_0="+WORK_PART_INSURANCE_BAL_PULL_STATUS_0+
						"&WORK_PART_INSURANCE_BAL_DPDATE_TIME_0="+WORK_PART_INSURANCE_BAL_DPDATE_TIME_0+
						"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for INBL_Query_insurance_balances service", 
				this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run INBL_Query_insurance_balances service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run INBL_Query_insurance_balances service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from Response:", ""
					+ "CFG_CONTROL_N_TODAYS_DATE1_0: "+doc.getElementsByTagName("CFG_CONTROL_N_TODAYS_DATE1_0").item(0).getTextContent(), false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
}
