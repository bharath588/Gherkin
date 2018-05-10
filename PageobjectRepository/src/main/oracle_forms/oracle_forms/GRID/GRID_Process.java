package pageobject.GRID;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import oracleforms.common.OracleForms;

public class GRID_Process {
	
	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRID_Process";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet;
		private int rows;
		
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String PC1_CODE_0;
		String TIMING_RULES_START_DATE_0;
		String TIMING_RULES_STOP_DATE_0;
		String TIMING_RULES_LAG_IN_0;
		String TIMING_RULES_RECOVERY_LAG_0;
		String TIMING_RULES_TRANSFER_LAG_IN_0;
		String TIMING_RULES_TRANSFER_LAG_OUT_0;
		String TIMING_RULES_INTERNAL_TRANSFER_LAG_0;
		String TIMING_RULES_DISB_LAG_OUT_0;
		String TIMING_RULES_DISB_DECISION_IN_0;
		String TIMING_RULES_COMBINE_CHECK_IND_0;
		String TIMING_RULES_COMBINE_CHECK_IND_0_X1;
		String TIMING_RULES_GROUP_DISB_CHECK_DAYS_0;
		String TIMING_RULES_FLOAT_OUT_0;
		String TIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0;
		String TIMING_RULES_TRADE_BASIS_LAG_0;
		String TIMING_RULES_TRADING_RULE_0;
		String TIMING_RULES_DIVIDEND_PURCHASE_LAG_0;
		String TIMING_RULES_DIVIDEND_REDEMPTION_LAG_0;
		
		
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
		public void setPC1_CODE_0(String pC1_CODE_0) {
			PC1_CODE_0 = pC1_CODE_0;
		}
		public void setTIMING_RULES_START_DATE_0(String tIMING_RULES_START_DATE_0){
			TIMING_RULES_START_DATE_0 = tIMING_RULES_START_DATE_0;
		}
		public void setTIMING_RULES_STOP_DATE_0(String tIMING_RULES_STOP_DATE_0){
			TIMING_RULES_STOP_DATE_0 = tIMING_RULES_STOP_DATE_0;
		}
		public void setTIMING_RULES_LAG_IN_0(String tIMING_RULES_LAG_IN_0){
			TIMING_RULES_LAG_IN_0 = tIMING_RULES_LAG_IN_0;
		}
		public void setTIMING_RULES_RECOVERY_LAG_0(String tIMING_RULES_RECOVERY_LAG_0){
			TIMING_RULES_RECOVERY_LAG_0 = tIMING_RULES_RECOVERY_LAG_0;
		}
		public void setTIMING_RULES_TRANSFER_LAG_IN_0(String tIMING_RULES_TRANSFER_LAG_IN_0){
			TIMING_RULES_TRANSFER_LAG_IN_0 = tIMING_RULES_TRANSFER_LAG_IN_0;
		}
		public void setTIMING_RULES_TRANSFER_LAG_OUT_0(String tIMING_RULES_TRANSFER_LAG_OUT_0){
			TIMING_RULES_TRANSFER_LAG_OUT_0 = tIMING_RULES_TRANSFER_LAG_OUT_0;
		}
		public void setTIMING_RULES_INTERNAL_TRANSFER_LAG_0(String tIMING_RULES_INTERNAL_TRANSFER_LAG_0){
			TIMING_RULES_INTERNAL_TRANSFER_LAG_0 = tIMING_RULES_INTERNAL_TRANSFER_LAG_0;
		}
		public void setTIMING_RULES_DISB_LAG_OUT_0(String tIMING_RULES_DISB_LAG_OUT_0){
			TIMING_RULES_DISB_LAG_OUT_0 = tIMING_RULES_DISB_LAG_OUT_0;
		}
		public void setTIMING_RULES_DISB_DECISION_IN_0(String tIMING_RULES_DISB_DECISION_IN_0){
			TIMING_RULES_DISB_DECISION_IN_0 = tIMING_RULES_DISB_DECISION_IN_0;
		}
		public void setTIMING_RULES_COMBINE_CHECK_IND_0(String tIMING_RULES_COMBINE_CHECK_IND_0){
			TIMING_RULES_COMBINE_CHECK_IND_0 = tIMING_RULES_COMBINE_CHECK_IND_0;
		}
		public void setTIMING_RULES_COMBINE_CHECK_IND_0_X1(String tIMING_RULES_COMBINE_CHECK_IND_0_X1){
			TIMING_RULES_COMBINE_CHECK_IND_0_X1 = tIMING_RULES_COMBINE_CHECK_IND_0_X1;
		}
		public void setTIMING_RULES_GROUP_DISB_CHECK_DAYS_0(String tIMING_RULES_GROUP_DISB_CHECK_DAYS_0){
			TIMING_RULES_GROUP_DISB_CHECK_DAYS_0 = tIMING_RULES_GROUP_DISB_CHECK_DAYS_0;
		}
		public void setTIMING_RULES_FLOAT_OUT_0(String tIMING_RULES_FLOAT_OUT_0){
			TIMING_RULES_FLOAT_OUT_0 = tIMING_RULES_FLOAT_OUT_0;
		}
		public void setTIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0(String tIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0){
			TIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0 = tIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0;
		}
		public void setTIMING_RULES_TRADE_BASIS_LAG_0(String tIMING_RULES_TRADE_BASIS_LAG_0){
			TIMING_RULES_TRADE_BASIS_LAG_0 = tIMING_RULES_TRADE_BASIS_LAG_0;
		}
		public void setTIMING_RULES_TRADING_RULE_0(String tIMING_RULES_TRADING_RULE_0){
			TIMING_RULES_TRADING_RULE_0 = tIMING_RULES_TRADING_RULE_0;
		}
		public void setTIMING_RULES_DIVIDEND_PURCHASE_LAG_0(String tIMING_RULES_DIVIDEND_PURCHASE_LAG_0){
			TIMING_RULES_DIVIDEND_PURCHASE_LAG_0 = tIMING_RULES_DIVIDEND_PURCHASE_LAG_0;
		}
		public void setTIMING_RULES_DIVIDEND_REDEMPTION_LAG_0(String tIMING_RULES_DIVIDEND_REDEMPTION_LAG_0){
			TIMING_RULES_DIVIDEND_REDEMPTION_LAG_0 = tIMING_RULES_DIVIDEND_REDEMPTION_LAG_0;
		}
		
		
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setPC1_CODE_0(Stock.GetParameterValue("PC1_CODE_0"));
			this.setTIMING_RULES_START_DATE_0(Stock.GetParameterValue("TIMING_RULES_START_DATE_0"));
			this.setTIMING_RULES_STOP_DATE_0(Stock.GetParameterValue("TIMING_RULES_STOP_DATE_0"));
			this.setTIMING_RULES_LAG_IN_0(Stock.GetParameterValue("TIMING_RULES_LAG_IN_0"));
			this.setTIMING_RULES_RECOVERY_LAG_0(Stock.GetParameterValue("TIMING_RULES_RECOVERY_LAG_0"));
			this.setTIMING_RULES_TRANSFER_LAG_IN_0(Stock.GetParameterValue("TIMING_RULES_TRANSFER_LAG_IN_0"));
			this.setTIMING_RULES_TRANSFER_LAG_OUT_0(Stock.GetParameterValue("TIMING_RULES_TRANSFER_LAG_OUT_0"));
			this.setTIMING_RULES_INTERNAL_TRANSFER_LAG_0(Stock.GetParameterValue("TIMING_RULES_INTERNAL_TRANSFER_LAG_0"));
			this.setTIMING_RULES_DISB_LAG_OUT_0(Stock.GetParameterValue("TIMING_RULES_DISB_LAG_OUT_0"));
			this.setTIMING_RULES_DISB_DECISION_IN_0(Stock.GetParameterValue("TIMING_RULES_DISB_DECISION_IN_0"));
			this.setTIMING_RULES_COMBINE_CHECK_IND_0(Stock.GetParameterValue("TIMING_RULES_COMBINE_CHECK_IND_0"));
			this.setTIMING_RULES_COMBINE_CHECK_IND_0_X1(Stock.GetParameterValue("TIMING_RULES_COMBINE_CHECK_IND_0_X1"));
			this.setTIMING_RULES_GROUP_DISB_CHECK_DAYS_0(Stock.GetParameterValue("TIMING_RULES_GROUP_DISB_CHECK_DAYS_0"));
			this.setTIMING_RULES_FLOAT_OUT_0(Stock.GetParameterValue("TIMING_RULES_FLOAT_OUT_0"));
			this.setTIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0(Stock.GetParameterValue("TIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0"));
			this.setTIMING_RULES_TRADE_BASIS_LAG_0(Stock.GetParameterValue("TIMING_RULES_TRADE_BASIS_LAG_0"));
			this.setTIMING_RULES_TRADING_RULE_0(Stock.GetParameterValue("TIMING_RULES_TRADING_RULE_0"));
			this.setTIMING_RULES_DIVIDEND_PURCHASE_LAG_0(Stock.GetParameterValue("TIMING_RULES_DIVIDEND_PURCHASE_LAG_0"));
			this.setTIMING_RULES_DIVIDEND_REDEMPTION_LAG_0(Stock.GetParameterValue("TIMING_RULES_DIVIDEND_REDEMPTION_LAG_0"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +"&LOGON_DATABASE=" 
			              + LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0
			     		  + "&PC1_CODE_0=" + PC1_CODE_0 +"&TIMING_RULES_START_DATE_0=" + TIMING_RULES_START_DATE_0
			     		  + "&TIMING_RULES_STOP_DATE_0=" +TIMING_RULES_STOP_DATE_0 + "&TIMING_RULES_LAG_IN_0="+TIMING_RULES_LAG_IN_0
			     		  + "&TIMING_RULES_RECOVERY_LAG_0="+TIMING_RULES_RECOVERY_LAG_0+"&TIMING_RULES_TRANSFER_LAG_IN_0="+TIMING_RULES_TRANSFER_LAG_IN_0
			     		  + "&TIMING_RULES_TRANSFER_LAG_OUT_0="+TIMING_RULES_TRANSFER_LAG_OUT_0+"&TIMING_RULES_INTERNAL_TRANSFER_LAG_0="+TIMING_RULES_INTERNAL_TRANSFER_LAG_0
			     		  + "&TIMING_RULES_DISB_LAG_OUT_0="+TIMING_RULES_DISB_LAG_OUT_0+"&TIMING_RULES_DISB_DECISION_IN_0="+TIMING_RULES_DISB_DECISION_IN_0
			     		  + "&TIMING_RULES_COMBINE_CHECK_IND_0="+TIMING_RULES_COMBINE_CHECK_IND_0+"&TIMING_RULES_COMBINE_CHECK_IND_0_X1="+TIMING_RULES_COMBINE_CHECK_IND_0_X1
			     		  + "&TIMING_RULES_GROUP_DISB_CHECK_DAYS_0="+TIMING_RULES_GROUP_DISB_CHECK_DAYS_0+"&TIMING_RULES_FLOAT_OUT_0="+TIMING_RULES_FLOAT_OUT_0
			     		  + "&TIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0="+TIMING_RULES_PAYMENT_SUBMISSION_DELAY_IND_0+"&TIMING_RULES_TRADE_BASIS_LAG_0="+TIMING_RULES_TRADE_BASIS_LAG_0
			     		  + "&TIMING_RULES_TRADING_RULE_0="+TIMING_RULES_TRADING_RULE_0+"&TIMING_RULES_DIVIDEND_PURCHASE_LAG_0="+TIMING_RULES_DIVIDEND_PURCHASE_LAG_0
			     		  + "&TIMING_RULES_DIVIDEND_REDEMPTION_LAG_0="+TIMING_RULES_DIVIDEND_REDEMPTION_LAG_0+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for GRID service", this.queryString.replaceAll("&", "\n"), false);
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
				rows = DB.executeUpdate(General.dbInstance,
						Stock.getTestQuery("queryForDeleteGRID")[1], this.TIMING_RULES_START_DATE_0,this.TIMING_RULES_STOP_DATE_0);
				System.out.println("Number of rows deleted="+rows);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Reporter.logEvent(Status.INFO, "Updating values From DATABASE.",
					"Updating values From DATABASE.", false);
		}

		public void validateInDatabase() throws SQLException
		{
			queryResultSet = DB.executeQuery(General.dbInstance,
					Stock.getTestQuery("queryForGRID")[1], this.TIMING_RULES_START_DATE_0,this.TIMING_RULES_STOP_DATE_0);
			if(DB.getRecordSetCount(queryResultSet)>0)
			{
				Reporter.logEvent(Status.PASS,"Validating if records exist in database", "Records exist in Database",false);
				while(queryResultSet.next())
				{
					String start_dateDB = queryResultSet.getString("START_DATE"); 
					String stop_dateDB = queryResultSet.getString("STOP_DATE");
					String idDB = queryResultSet.getString("ID");
					Reporter.logEvent(Status.INFO,"Records in DB.","ID="+idDB+"\nSTART_DATE="+start_dateDB+"\nSTOP_DATE="+stop_dateDB, false);
				}
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if records exist in database", "Records do not exist in Database",false);
			}
		}
			
}

