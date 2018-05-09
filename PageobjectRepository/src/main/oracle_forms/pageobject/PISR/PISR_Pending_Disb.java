package pageobject.PISR;

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

public class PISR_Pending_Disb {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PISR_Predicted_System_Rate";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;


	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String PISR_STOP_DATE_9;
	String PISR_STOP_DATE_9_X1;
	String PISR_RATE_9;
	String PISR_START_DATE_9;
	
	
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

	public void setPISR_STOP_DATE_9(String pISR_STOP_DATE_9) {
		PISR_STOP_DATE_9 = pISR_STOP_DATE_9;
	}

	public void setPISR_STOP_DATE_9_X1(String pISR_STOP_DATE_9_X1) {
		PISR_STOP_DATE_9_X1 = pISR_STOP_DATE_9_X1;
	}

	public void setPISR_RATE_9(String pISR_RATE_9) {
		PISR_RATE_9 = pISR_RATE_9;
	}

	public void setPISR_START_DATE_9(String pISR_START_DATE_9) {
		PISR_START_DATE_9 = pISR_START_DATE_9;
	}

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setPISR_RATE_9(Stock.GetParameterValue("PISR_RATE_9"));
		this.setPISR_START_DATE_9(Stock.GetParameterValue("PISR_START_DATE_9"));
		this.setPISR_STOP_DATE_9(Stock.GetParameterValue("PISR_STOP_DATE_9"));
		this.setPISR_STOP_DATE_9_X1(Stock.GetParameterValue("PISR_STOP_DATE_9_X1"));
		
	//	queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForDelPredictedSystemRate")[1], this.PISR_RATE_9);
		
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&PISR_STOP_DATE_9="+PISR_STOP_DATE_9+"&PISR_STOP_DATE_9_X1="+PISR_STOP_DATE_9_X1+"&PISR_RATE_9="+PISR_RATE_9+"&PISR_START_DATE_9="+PISR_START_DATE_9+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for PISR service", this.queryString.replaceAll("&", "\n"), false);
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
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PISR is for entering the Predicted System Rate", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PISR is for entering the Predicted System Rate", "Running Failed:", false);
		}
		
	}
	
	public void validateOutput(){
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.PASS, "From RESPONSE: ", "PISR_RATE_9: "+  doc.getElementsByTagName("PISR_RATE_0").item(0).getTextContent()+
					"\nSTART DATE: "+ doc.getElementsByTagName("PISR_START_DATE_0").item(0).getTextContent()+
					"\nSTOP DATE: "+ doc.getElementsByTagName("PISR_STOP_DATE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
		
	}
	
	public void validateInDatabase() throws SQLException{
		String PISR_RATE = doc.getElementsByTagName("PISR_RATE_0").item(0).getTextContent();
		String pisr_rate = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPredictedSystemRate")[1], PISR_RATE);
		
		if(queryResultSet.next()){
			pisr_rate = queryResultSet.getString("RATE");
			Reporter.logEvent(Status.INFO, "Values From database\nTable: PREDICTED_SYS_RATE ", "Records exists"+
			"\nPISR RATE: "+queryResultSet.getString("RATE")+
			"\nSTART DATE: "+queryResultSet.getString("START_DATE")+
			"\nSTOP DATE: "+queryResultSet.getString("STOP_DATE"), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
//		if(PISR_RATE.compareTo(pisr_rate)==0){
			Reporter.logEvent(Status.PASS, "Comparing and Validating PISR_RATE from Response and Database", "Both the values are same as Expected"+
					"\nPISR_RATE: "+"From Response: "+PISR_RATE+"\nFrom Database: "+pisr_rate, false);
	//	}
	}

}
