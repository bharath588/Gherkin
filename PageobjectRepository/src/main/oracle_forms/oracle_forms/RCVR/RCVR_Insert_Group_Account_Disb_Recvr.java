package pageobject.RCVR;

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

public class RCVR_Insert_Group_Account_Disb_Recvr {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RCVR_Insert_Group_Account_Disb_Recvr";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;
	public ResultSet queryResultSet2 = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	
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
	
	
	
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0
				+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for RCVR service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void deleteInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRCVR")[1],GET_GA_GA_ID_0 );
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.INFO, "Validating Records in Database", "There are Records in the Database.", false);
			while(queryResultSet.next()){
				queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRCVRDelete")[1], GET_GA_GA_ID_0);
				Reporter.logEvent(Status.PASS, "Deleting record in Database", "Records Deleted.", false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Records in Database", "There are Records in the Database to delete.", false);
		}
		
			
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
			Reporter.logEvent(Status.PASS, "Run RCVR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RCVR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
//			Reporter.logEvent(Status.PASS, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.INFO, "From Response: ", "CFG_CONTROL_TODAYS_DATE1_0: "+ doc.getElementsByTagName("CFG_CONTROL_TODAYS_DATE1_0").item(0).getTextContent()+
					"\nGROUP_HEADER1_GC_ID_0: "+ doc.getElementsByTagName("GROUP_HEADER1_GC_ID_0").item(0).getTextContent()+
					"\nGROUP_HEADER1_GC_NAME_0: "+ doc.getElementsByTagName("GROUP_HEADER1_GC_NAME_0").item(0).getTextContent()+
					"\nGROUP_HEADER1_ID_0: "+ doc.getElementsByTagName("GROUP_HEADER1_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRCVR")[1], GET_GA_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Record generated in Database", "Record is generated in Database", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from DB", "GA_ID: "+queryResultSet.getString("GA_ID")+
						"\nDSDFRV_CODE: "+queryResultSet.getString("DSDFRV_CODE")+
						"\nDSMD_CODE: "+queryResultSet.getString("DSMD_CODE")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Record generated in Database", "A record is inserted into ga_dft_disb_recvr", false);
		}
		
		
	}
	
	
}
