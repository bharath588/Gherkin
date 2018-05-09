package pageobject.GADB;

import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class GADB_Avg_Daily_Balance {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GADB_avg_daily_balance";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GROUP_ACCOUNT_ID_0;
	String GROUP_ACCOUNT_EFFDATE_0;
	String GROUP_ACCOUNT_ENDDATE_0;
	
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
	public void setGROUP_ACCOUNT_ID_0(String gROUP_ACCOUNT_ID_0) {
		GROUP_ACCOUNT_ID_0 = gROUP_ACCOUNT_ID_0;
	}
	public void setGROUP_ACCOUNT_EFFDATE_0(String gROUP_ACCOUNT_EFFDATE_0) {
		GROUP_ACCOUNT_EFFDATE_0 = gROUP_ACCOUNT_EFFDATE_0;
	}
	public void setGROUP_ACCOUNT_ENDDATE_0(String gROUP_ACCOUNT_ENDDATE_0) {
		GROUP_ACCOUNT_ENDDATE_0 = gROUP_ACCOUNT_ENDDATE_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGROUP_ACCOUNT_EFFDATE_0(Stock.GetParameterValue("GROUP_ACCOUNT_EFFDATE_0"));
		this.setGROUP_ACCOUNT_ENDDATE_0(Stock.GetParameterValue("GROUP_ACCOUNT_ENDDATE_0"));
		this.setGROUP_ACCOUNT_ID_0(Stock.GetParameterValue("GROUP_ACCOUNT_ID_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
				"&GROUP_ACCOUNT_ID_0="+GROUP_ACCOUNT_ID_0+"&GROUP_ACCOUNT_EFFDATE_0="+GROUP_ACCOUNT_EFFDATE_0+"&GROUP_ACCOUNT_ENDDATE_0="+GROUP_ACCOUNT_ENDDATE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GADB service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run GADB service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GADB service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","GROUP_ACCOUNT_PLAN_NAME_0: "+doc.getElementsByTagName("GROUP_ACCOUNT_PLAN_NAME_0").item(0).getTextContent()+
					"\nGA_INVOPT_SDIO_ID_0: "+doc.getElementsByTagName("GA_INVOPT_SDIO_ID_0").item(0).getTextContent()+
					"\nGA_INVOPT_AVG_BAL_0: "+doc.getElementsByTagName("GA_INVOPT_AVG_BAL_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
}
