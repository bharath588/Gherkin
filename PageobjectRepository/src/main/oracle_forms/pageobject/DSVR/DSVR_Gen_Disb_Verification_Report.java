package pageobject.DSVR;

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

public class DSVR_Gen_Disb_Verification_Report {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSVR_multiple_records_w_same_step_compltn_date_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_1;
	String INP1_DISP_DFT_VALUE_9;
	String INP1_DATE_VALUE_9;
	String INP1_DATE_VALUE_9_X1;
	String INP1_DISP_DFT_VALUE_9_X1;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
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
	public void setINP1_DISP_DFT_VALUE_1(String iNP1_DISP_DFT_VALUE_1) {
		INP1_DISP_DFT_VALUE_1 = iNP1_DISP_DFT_VALUE_1;
	}
	public void setINP1_DISP_DFT_VALUE_9(String iNP1_DISP_DFT_VALUE_9) {
		INP1_DISP_DFT_VALUE_9 = iNP1_DISP_DFT_VALUE_9;
	}
	public void setINP1_DATE_VALUE_9(String iNP1_DATE_VALUE_9) {
		INP1_DATE_VALUE_9 = iNP1_DATE_VALUE_9;
	}
	public void setINP1_DATE_VALUE_9_X1(String iNP1_DATE_VALUE_9_X1) {
		INP1_DATE_VALUE_9_X1 = iNP1_DATE_VALUE_9_X1;
	}
	public void setINP1_DISP_DFT_VALUE_9_X1(String iNP1_DISP_DFT_VALUE_9_X1) {
		INP1_DISP_DFT_VALUE_9_X1 = iNP1_DISP_DFT_VALUE_9_X1;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setINP1_DATE_VALUE_9(Stock.GetParameterValue("INP1_DATE_VALUE_9"));
		this.setINP1_DATE_VALUE_9_X1(Stock.GetParameterValue("iNP1_DATE_VALUE_9_X1"));
		this.setINP1_DISP_DFT_VALUE_1(Stock.GetParameterValue("iNP1_DISP_DFT_VALUE_1"));
		this.setINP1_DISP_DFT_VALUE_9(Stock.GetParameterValue("iNP1_DISP_DFT_VALUE_9"));
		this.setINP1_DISP_DFT_VALUE_9_X1(Stock.GetParameterValue("iNP1_DISP_DFT_VALUE_9_X1"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&INP1_DISP_DFT_VALUE_1="+INP1_DISP_DFT_VALUE_1+"&INP1_DISP_DFT_VALUE_9="+INP1_DISP_DFT_VALUE_9+"&INP1_DATE_VALUE_9="+INP1_DATE_VALUE_9+
				"&INP1_DATE_VALUE_9_X1="+INP1_DATE_VALUE_9_X1+"&INP1_DISP_DFT_VALUE_9_X1="+INP1_DISP_DFT_VALUE_9_X1+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DSVR service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run DSVR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSVR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "INP1_DISP_DFT_VALUE_8: "+doc.getElementsByTagName("INP1_DISP_DFT_VALUE_8").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
}
