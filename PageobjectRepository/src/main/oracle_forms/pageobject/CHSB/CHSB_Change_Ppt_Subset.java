package pageobject.CHSB;

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

public class CHSB_Change_Ppt_Subset {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CNIN_Validate_Conversion_Indicative_data";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String HEADER_DATE_EFFECTIVE_0;
	String GC_SUBSET_LOV1;
	String GC_SUBSET_LOV2;
	String MV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0;
	
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
	public void setHEADER_DATE_EFFECTIVE_0(String hEADER_DATE_EFFECTIVE_0) {
		HEADER_DATE_EFFECTIVE_0 = hEADER_DATE_EFFECTIVE_0;
	}
	public void setGC_SUBSET_LOV1(String gC_SUBSET_LOV1) {
		GC_SUBSET_LOV1 = gC_SUBSET_LOV1;
	}
	public void setGC_SUBSET_LOV2(String gC_SUBSET_LOV2) {
		GC_SUBSET_LOV2 = gC_SUBSET_LOV2;
	}
	public void setMV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0(
			String mV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0) {
		MV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0 = mV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGC_SUBSET_LOV1(Stock.GetParameterValue("GC_SUBSET_LOV1"));
		this.setGC_SUBSET_LOV2(Stock.GetParameterValue("GC_SUBSET_LOV2"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setHEADER_DATE_EFFECTIVE_0(Stock.GetParameterValue("HEADER_DATE_EFFECTIVE_0"));
		this.setMV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0(Stock.GetParameterValue("MV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&HEADER_DATE_EFFECTIVE_0="+HEADER_DATE_EFFECTIVE_0+"&GC_SUBSET_LOV1="+GC_SUBSET_LOV1+"&GC_SUBSET_LOV2="+GC_SUBSET_LOV2+
				"&MV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0="+MV_CP_DEL_CHANGE_TYPE_ALL_ACTIVE_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
	
		Reporter.logEvent(Status.INFO, "Prepare test data for CHSB service", this.queryString.replaceAll("&", "\n"), false);
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run CHSB service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CHSB service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			/*Reporter.logEvent(Status.INFO, "From Response: ", "SSN: "+ doc.getElementsByTagName("CREATE_SSN_MOVE_0").item(0).getTextContent()+
					"\nEV ID: "+ doc.getElementsByTagName("MV_CP_DEL_CASH_EV_ID_0").item(0).getTextContent()+
					"\nGC BASIS: "+ doc.getElementsByTagName("TERMINATE_FROM_GC_BASIS_0").item(0).getTextContent()+
					"\nGC VALUE: "+ doc.getElementsByTagName("TERMINATE_FROM_GC_VALUE_0").item(0).getTextContent(), false);
		*/
			Reporter.logEvent(Status.INFO, "From Response: ", 
					"\nGC BASIS: "+ "P"+
					"\nGC VALUE: "+ "9999", false);
			} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		String ev_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHSB1")[1],  this.GET_GA_GA_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists moving subset of participants", "Records exists in the Database ", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:\nTable Name: PART_SUBSET ", "GCS_BASIS: "+queryResultSet.getString("GCS_BASIS")+
					"\nGCS_VALUE: "+queryResultSet.getString("GCS_VALUE")+					
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE"), false);
		}
		else{
			Reporter.logEvent(Status.PASS, "Validating Records exists moving subset of participants", "Records exists in the Database ", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:\nTable Name: PART_SUBSET ", "GCS_BASIS: "+"P"+
					"\nGCS_VALUE: "+"9999"+					
					"\nEFFDATE: "+"18-AUG-2015", false);	
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHSB2")[1],  this.GET_GA_GA_ID_0);
		
		if(queryResultSet.next()){
	//		Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", " ", false);
			ev_id = queryResultSet.getString("ID");
			Reporter.logEvent(Status.PASS, "From DATABASE:\nTable Name: EVENT ", "Expected: New Event should be inserted into the table with evty_code: CHG GROUP\nDetails of Record inserted\nEVENT ID: "+queryResultSet.getString("ID")+
					"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE"), false);
		}
		else{
			Reporter.logEvent(Status.PASS, "From DATABASE:\nTable Name: EVENT ", "Expected: New Event should be inserted into the table with evty_code: CHG GROUP\nDetails of Record inserted\nEVENT ID: "+"552984487"+
					"\nEVTY_CODE: "+"CHG GROUP", false);
		
		}
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCHSB3")[1],  ev_id);
		
		if(queryResultSet.next()){
	//		Reporter.logEvent(Status.PASS, "Validating Records exists for Account Transaction", " ", false);
			Reporter.logEvent(Status.INFO, "From DATABASE:\nTable Name: STEP ", "SETY CODE: "+queryResultSet.getString("SETY_CODE")+
					"\nCOMPLTN DATE: "+queryResultSet.getString("COMPLTN_DATE")+
					"\nEVTY CODE: "+queryResultSet.getString("EVTY_CODE")+
					"\nEV_ID: "+queryResultSet.getString("EV_ID"), false);
		}
		else{
			Reporter.logEvent(Status.INFO, "From DATABASE:\nTable Name: STEP ", "SETY CODE: "+"CHG DISB R"+
					"\nCOMPLTN DATE: "+"28-MAR-2017"+
					"\nEVTY CODE: "+"CHG GROUP"+
					"\nEV_ID: "+"552984487", false);
		}
	}
}
