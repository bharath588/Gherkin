package pageobject.GBRV;

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

public class GBRV_Process {


	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GBRV_Process";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet1;
		private ResultSet queryResultSet2;
		private ResultSet queryResultSet3;
		private int rows1;
		private int rows2;
		private String recov_id;
		
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String GET_GA_GA_ID_0;
		String BILL_BASIC_PAST_DUE_PERD_0;
		String BILL_BASIC_RUN_FREQ_0;
		String BILL_BASIC_NEXT_PRODN_DATE_0;
		String PRIM_RECVRS_DMFT_ID_0;
		
		
		
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
		public void setBILL_BASIC_PAST_DUE_PERD_0(String bILL_BASIC_PAST_DUE_PERD_0){
			BILL_BASIC_PAST_DUE_PERD_0 = bILL_BASIC_PAST_DUE_PERD_0;
		}
		public void setBILL_BASIC_RUN_FREQ_0(String bILL_BASIC_RUN_FREQ_0){
			BILL_BASIC_RUN_FREQ_0 = bILL_BASIC_RUN_FREQ_0;
		}
		public void setBILL_BASIC_NEXT_PRODN_DATE_0(String bILL_BASIC_NEXT_PRODN_DATE_0){
			BILL_BASIC_NEXT_PRODN_DATE_0 = bILL_BASIC_NEXT_PRODN_DATE_0;
		}
		public void setPRIM_RECVRS_DMFT_ID_0(String pRIM_RECVRS_DMFT_ID_0){
			PRIM_RECVRS_DMFT_ID_0 = pRIM_RECVRS_DMFT_ID_0;
		}
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
			this.setBILL_BASIC_PAST_DUE_PERD_0(Stock.GetParameterValue("BILL_BASIC_PAST_DUE_PERD_0"));
			this.setBILL_BASIC_RUN_FREQ_0(Stock.GetParameterValue("BILL_BASIC_RUN_FREQ_0"));
			this.setBILL_BASIC_NEXT_PRODN_DATE_0(Stock.GetParameterValue("BILL_BASIC_NEXT_PRODN_DATE_0"));
			this.setPRIM_RECVRS_DMFT_ID_0(Stock.GetParameterValue("PRIM_RECVRS_DMFT_ID_0"));
			
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			              + LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0
			     		  + "&GET_GA_GA_ID_0=" + GET_GA_GA_ID_0 +"&BILL_BASIC_PAST_DUE_PERD_0=" + BILL_BASIC_PAST_DUE_PERD_0
			     		  + "&BILL_BASIC_RUN_FREQ_0=" +BILL_BASIC_RUN_FREQ_0 + "&BILL_BASIC_NEXT_PRODN_DATE_0="+BILL_BASIC_NEXT_PRODN_DATE_0
			     		  + "&PRIM_RECVRS_DMFT_ID_0="+PRIM_RECVRS_DMFT_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for GBRV service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run GRID service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GRID service", "Running Failed:", false);
			}
		}
		
		public void validateOutput()
		{
			String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

			if (Error.isEmpty()){
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}	
				
		}
		
		
		public void cleanUp() throws SQLException{
		    		
			try {
				rows1 = DB.executeUpdate(General.dbInstance,
						Stock.getTestQuery("queryForDeleteGBRV1")[1], this.GET_GA_GA_ID_0);
				System.out.println("Number of rows deleted="+rows1);
				Reporter.logEvent(Status.INFO, "Reseting database values by CleaUp",
						"Number of rows deleted="+rows1, false);
				rows2 =DB.executeUpdate(General.dbInstance,
						Stock.getTestQuery("queryForDeleteGBRV2")[1], this.GET_GA_GA_ID_0);
				System.out.println("Number of rows deleted="+rows2);
				Reporter.logEvent(Status.INFO, "Reseting database values by CleaUp",
						"Number of rows deleted="+rows2, false);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Reporter.logEvent(Status.INFO, "Updating values From DATABASE.",
					"Values in database reset before the service run. CleanUp performed", false);
		}

		public void validateInDatabase() throws SQLException
		{
			recov_id = doc.getElementsByTagName("GA_RECOV_STRUC_RECOV_ID_0").item(0).getTextContent();
			queryResultSet1 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForGBRV1")[1], this.GET_GA_GA_ID_0, recov_id);
			if(DB.getRecordSetCount(queryResultSet1)>0)
			{
				Reporter.logEvent(Status.PASS,"Validating if records exist in database", "Records exist in Database.",false);
				while(queryResultSet1.next())
				{
			
					Reporter.logEvent(Status.INFO,"Records in DB.","GA_ID from DB:"+queryResultSet1.getString("GA_ID"), false);
				}
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if records exist in database", "Records do not exist in Database",false);
			}
			
			queryResultSet2 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForGBRV2")[1], this.GET_GA_GA_ID_0);
			if(DB.getRecordSetCount(queryResultSet2)>0)
			{
				Reporter.logEvent(Status.PASS,"Validating if records exist in database", "Records exist in Database.",false);
				while(queryResultSet2.next())
				{
			
					Reporter.logEvent(Status.INFO,"Records in DB.","GA_ID from DB:"+queryResultSet2.getString("ID"), false);
				}
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if records exist in database", "Records do not exist in Database",false);
			}
			
			queryResultSet3 = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForGBRV3")[1], this.GET_GA_GA_ID_0);
			if(DB.getRecordSetCount(queryResultSet3)>0)
			{
				Reporter.logEvent(Status.PASS,"Validating if records exist in database", "Records exist in Database.",false);
				while(queryResultSet3.next())
				{
			
					Reporter.logEvent(Status.INFO,"Records in DB.","BIBA"
							+ "_ID from DB:"+queryResultSet3.getString("BIBA_ID"), false);
				}
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if records exist in database", "Records do not exist in Database",false);
			}
			
			
		}
			
	
	
	
	
	
}
