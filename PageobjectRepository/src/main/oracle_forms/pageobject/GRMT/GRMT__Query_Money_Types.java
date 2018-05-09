package pageobject.GRMT;

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

public class GRMT__Query_Money_Types {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRMT_Query_Account_Money_Types";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
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
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for GRMT Inquire Money Types service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() throws SQLException {
		try {
				this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
				serviceURL += this.queryString;
				docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setIgnoringComments(true);
				docBuilderFactory.setIgnoringElementContentWhitespace(true);
				docBuilderFactory.setNamespaceAware(true);
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(serviceURL);
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run GRMT Query Account Money Types service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GRMT Query Account Money Types service", "Running Failed:", false);
			}			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From response: ","SDMT_CODE: "+doc.getElementsByTagName("CDMNTY1_SDMT_CODE_0").item(0).getTextContent(),false);
		}
		 else {
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}
				
		}
	
	public void validateInDatabase() throws SQLException{
		String sdmt_code_resp = doc.getElementsByTagName("CDMNTY1_SDMT_CODE_0").item(0).getTextContent();
		String sdmt_code_db = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGRMT")[1], this.GET_GA_GA_ID_0, sdmt_code_resp);
		if(queryResultSet.next()){
			sdmt_code_db = queryResultSet.getString("SDMT_CODE");
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists for above Money Type", false);
			while(queryResultSet.next()){
			Reporter.logEvent(Status.INFO, "Values From Database","Table Name: GRP_DEF_MNTY\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE")+
					"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
					"\nDESCR: "+queryResultSet.getString("DESCR"),false); 
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of Records in Database","No Record exists for above Money Type", false);
		}
		if(sdmt_code_resp.equalsIgnoreCase(sdmt_code_db)){
			Reporter.logEvent(Status.PASS, "Comparing and validating SDMT_CODE from Response and Database", "Both the values are same as expected"+
		"\nFrom Response: "+sdmt_code_resp+"\nFrom Database: "+sdmt_code_db,false);
		}
	}
}
