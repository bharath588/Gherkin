package pageobject.LEUP;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class LEUP_Update_Expected_Return {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LEUP_Update_Expected_Return";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String ONE_LIFE_MULTIPLE_2;
	String ONE_LIFE_MULTIPLE_2_X1;
	String ONE_LIFE_MULTIPLE_1;
	String ONE_LIFE_MULTIPLE_1_X1;
	String ONE_LIFE_MULTIPLE_0;
	String ONE_LIFE_MULTIPLE_0_X1;
	
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
	public void setONE_LIFE_MULTIPLE_2(String oNE_LIFE_MULTIPLE_2) {
		ONE_LIFE_MULTIPLE_2 = oNE_LIFE_MULTIPLE_2;
	}
	public void setONE_LIFE_MULTIPLE_2_X1(String oNE_LIFE_MULTIPLE_2_X1) {
		ONE_LIFE_MULTIPLE_2_X1 = oNE_LIFE_MULTIPLE_2_X1;
	}
	public void setONE_LIFE_MULTIPLE_1(String oNE_LIFE_MULTIPLE_1) {
		ONE_LIFE_MULTIPLE_1 = oNE_LIFE_MULTIPLE_1;
	}
	public void setONE_LIFE_MULTIPLE_1_X1(String oNE_LIFE_MULTIPLE_1_X1) {
		ONE_LIFE_MULTIPLE_1_X1 = oNE_LIFE_MULTIPLE_1_X1;
	}
	public void setONE_LIFE_MULTIPLE_0(String oNE_LIFE_MULTIPLE_0) {
		ONE_LIFE_MULTIPLE_0 = oNE_LIFE_MULTIPLE_0;
	}
	public void setONE_LIFE_MULTIPLE_0_X1(String oNE_LIFE_MULTIPLE_0_X1) {
		ONE_LIFE_MULTIPLE_0_X1 = oNE_LIFE_MULTIPLE_0_X1;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setONE_LIFE_MULTIPLE_0(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_0"));
		this.setONE_LIFE_MULTIPLE_0_X1(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_0_X1"));
		this.setONE_LIFE_MULTIPLE_1(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_1"));
		this.setONE_LIFE_MULTIPLE_1_X1(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_1_X1"));
		this.setONE_LIFE_MULTIPLE_2(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_2"));
		this.setONE_LIFE_MULTIPLE_2_X1(Stock.GetParameterValue("ONE_LIFE_MULTIPLE_2_X1"));
	
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&ONE_LIFE_MULTIPLE_2="+ONE_LIFE_MULTIPLE_2+"ONE_LIFE_MULTIPLE_2_X1="+ONE_LIFE_MULTIPLE_2_X1+"&ONE_LIFE_MULTIPLE_1="+ONE_LIFE_MULTIPLE_1+
				"&ONE_LIFE_MULTIPLE_1_X1="+ONE_LIFE_MULTIPLE_1_X1+"&ONE_LIFE_MULTIPLE_0="+ONE_LIFE_MULTIPLE_0+"&ONE_LIFE_MULTIPLE_0_X1="+ONE_LIFE_MULTIPLE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for LEUP service", this.queryString.replaceAll("&", "\n"), false);
		
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
			Reporter.logEvent(Status.PASS, "Run LEUP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run LEUP service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	
	public void validateInDatabase()throws SQLException{
		
		Map<String, String> data = new HashMap<String, String>();
		String mutiple_for_age8 = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForLEUPUpdate")[1], this.ONE_LIFE_MULTIPLE_0, this.ONE_LIFE_MULTIPLE_1, this.ONE_LIFE_MULTIPLE_2);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database\nTable Name: IRS_LIFE_EXP_V", "Record exists in the Database", false);

			while(queryResultSet.next()){				
			  data.put(queryResultSet.getString("AGE"), queryResultSet.getString("MULTIPLE"));
				mutiple_for_age8 = queryResultSet.getString("MULTIPLE");
			Reporter.logEvent(Status.INFO, "Values from Database", "AGE: "+queryResultSet.getString("AGE")
					+"\nMULTIPLE: "+queryResultSet.getString("MULTIPLE")
					, false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
		
		if(this.ONE_LIFE_MULTIPLE_2_X1.equalsIgnoreCase(mutiple_for_age8)){
			Reporter.logEvent(Status.PASS,"Comparing and validating ages and their respective multiples from Input and Database",
					"From Input:\n"+this.ONE_LIFE_MULTIPLE_0+"\t"+this.ONE_LIFE_MULTIPLE_0_X1+
									"\n"+this.ONE_LIFE_MULTIPLE_1+"\t"+this.ONE_LIFE_MULTIPLE_1_X1+
									"\n"+this.ONE_LIFE_MULTIPLE_2+"\t"+this.ONE_LIFE_MULTIPLE_2_X1
									,false);
			for (@SuppressWarnings("rawtypes") Map.Entry entry : data.entrySet()) {
			  Reporter.logEvent(Status.PASS, "From Database:\nKey: "+entry.getKey(), "Multiple: "+entry.getValue(), false);
			}
		}
		else{
			Reporter.logEvent(Status.PASS,"Comparing and validating ages and their respective multiples from Input and Database","Values are not same as expected",false);
		}
	}
}
