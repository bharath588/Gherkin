package pageobject.CPAL;

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

public class CPAL_Copy_Allocs_1 {
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CPAL_Copy_Allocs_1";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private int queryResultSet;
		private ResultSet queryResultSet2;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String GET_GA_GA_ID_0;
		String MONEY_TYPES_COPY_ALLOC_6;
		String LOV_MONEY_TYPE;
		
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
		public void setMONEY_TYPES_COPY_ALLOC_6(String mONEY_TYPES_COPY_ALLOC_6){
			MONEY_TYPES_COPY_ALLOC_6 = mONEY_TYPES_COPY_ALLOC_6;
		}
		public void setLOV_MONEY_TYPE(String lOV_MONEY_TYPE){
			LOV_MONEY_TYPE = lOV_MONEY_TYPE;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
			this.setMONEY_TYPES_COPY_ALLOC_6(Stock.GetParameterValue("MONEY_TYPES_COPY_ALLOC_6"));
			this.setLOV_MONEY_TYPE(Stock.GetParameterValue("LOV_MONEY_TYPE"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			+ LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +
		     "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 +"&MONEY_TYPES_COPY_ALLOC_6=" + MONEY_TYPES_COPY_ALLOC_6 +
			"&LOV_MONEY_TYPE="	+ LOV_MONEY_TYPE+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for CPAL service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run CPAL service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run CPAL service", "Running Failed:", false);
			}
		}
		
		public void validateOutput()
		{
			String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

			if (Error.isEmpty()){
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			} else {
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}	
				
		}
		
		public void cleanUp() throws Exception
		{	
		try {
			queryResultSet = DB.executeUpdate(General.dbInstance,
					Stock.getTestQuery("queryForCPALdelete")[1],this.GET_GA_GA_ID_0,
					Stock.GetParameterValue("sdmt_code"),Stock.GetParameterValue("gdmt_seqnbr"));
			System.out.println(queryResultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.logEvent(Status.INFO, "Updating values From DATABASE.",
				"Updating values From DATABASE.", false);
	}

		
		public void validateInDatabase() throws SQLException{
			String dbCount = null;
			queryResultSet2 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForCPAL")[1],this.GET_GA_GA_ID_0,
					Stock.GetParameterValue("sdmt_code"),Stock.GetParameterValue("gdmt_seqnbr"));
			if(DB.getRecordSetCount(queryResultSet2)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
				while(queryResultSet2.next()){
				    dbCount = queryResultSet2.getString("COUNT(*)");
					Reporter.logEvent(Status.INFO,"Validating the record count is more than zero.", "Record count is more than zero.Record count is:"+dbCount, false);
				}}
			  
			else{
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
			
		}	

}
