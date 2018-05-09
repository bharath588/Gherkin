package pageobject.MECA;

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

public class MECA_Verify_Assets1 {

	public String queryString;
	 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() +
			 						":8080/ServiceManager/Macro/ExecMacro/MECA_Verify_Assets1";
		private DocumentBuilderFactory docBuilderFactory;
		private DocumentBuilder docBuilder;
		private Document doc;
		private ResultSet queryResultSet;
		private ResultSet queryResultSet1;
		
		String CONTROL_SELECTION_0;
		String LOGON_DATABASE;
		String LOGON_PASSWORD;
		String LOGON_USERNAME;
		String CONTROL_GA_ID_0;
		String GRP_COMP_PKG_STRUC_LOV;
		String CONTROL_START_DATE_0;
		String CONTROL_STOP_DATE_0;
		
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
		public void setCONTROL_GA_ID_0(String cONTROL_GA_ID_0) {
			CONTROL_GA_ID_0 = cONTROL_GA_ID_0;
		}
		public void setGRP_COMP_PKG_STRUC_LOV(String gRP_COMP_PKG_STRUC_LOV){
			GRP_COMP_PKG_STRUC_LOV = gRP_COMP_PKG_STRUC_LOV;
		}
		public void setCONTROL_START_DATE_0(String cONTROL_START_DATE_0){
			CONTROL_START_DATE_0 = cONTROL_START_DATE_0;
		}
		public void setCONTROL_STOP_DATE_0(String cONTROL_STOP_DATE_0){
			CONTROL_STOP_DATE_0 = cONTROL_STOP_DATE_0;
		}
		
		
		public void setServiceParameters()
		{
			this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
			this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
			this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
			this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
			this.setCONTROL_GA_ID_0(Stock.GetParameterValue("CONTROL_GA_ID_0"));
			this.setGRP_COMP_PKG_STRUC_LOV(Stock.GetParameterValue("GRP_COMP_PKG_STRUC_LOV"));
			this.setCONTROL_START_DATE_0(Stock.GetParameterValue("CONTROL_START_DATE_0"));
			this.setCONTROL_STOP_DATE_0(Stock.GetParameterValue("CONTROL_STOP_DATE_0"));
			
			
			queryString = "?LOGON_USERNAME="+ LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD +
					"&LOGON_DATABASE=" + LOGON_DATABASE +"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
					+ "&CONTROL_GA_ID_0=" + CONTROL_GA_ID_0 +"&GRP_COMP_PKG_STRUC_LOV=" + GRP_COMP_PKG_STRUC_LOV 
					+ "&CONTROL_START_DATE_0=" + CONTROL_START_DATE_0 
					+ "&CONTROL_STOP_DATE_0=" + CONTROL_STOP_DATE_0 +
					"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
			
			Reporter.logEvent(Status.INFO, "Prepare test data for MECA service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run MECA service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run MECA service", "Running Failed:", false);
			}
		}
		
		public void validateOutput()
		{
			String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

			if (Error.isEmpty()){
				Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			}
				else{
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
				System.out.println(Error);
			}	
				
		}
		
		
		public void validateInDatabase() throws SQLException{
			
			String formAsset1 = doc.getElementsByTagName("CONTROL_TOTAL_ASSETS_0").item(0).getTextContent().trim().replaceAll(",", "");
			String formAsset2 = doc.getElementsByTagName("CONTROL_TOTAL_PCAS_ASSETS_0").item(0).getTextContent().trim().replaceAll(",", "");
			String formAsset3 = doc.getElementsByTagName("CONTROL_TOTAL_INT_FIXED_FUNDS_0").item(0).getTextContent().trim().replaceAll(",", "");
			Reporter.logEvent(Status.INFO,"Validating if asset values displayed in form",
					"Asset values displayed in form are:"+formAsset1+"\n"+formAsset2+"\n"+formAsset3,false);
			
			
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTotalAsset1MECA")[1],this.CONTROL_GA_ID_0);
						if(DB.getRecordSetCount(queryResultSet)>0)
						{
							Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
						
							while(queryResultSet.next())
							{
								String DBtotalAsset1 = "$"+queryResultSet.getString("NVL(SUM(BALTOT_BALANCE),0)") ;
								if(formAsset1.equalsIgnoreCase(DBtotalAsset1))
								{
									Reporter.logEvent(Status.PASS,"Validating if the total Asset value is equal in both form and database.",
											"Total asset value is same in database and form.", false);
								}
								else
								{
									Reporter.logEvent(Status.FAIL,"Validating if the total Asset value is equal in both form and database.",
											"Total asset value is not same in database and form.", false);
								}
							}
						}
						else{
						Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
						}		
		
			
			queryResultSet1 = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForTotalAsset2MECA")[1],this.CONTROL_GA_ID_0);
						if(DB.getRecordSetCount(queryResultSet1)>0)
						{
							Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
						
							while(queryResultSet1.next())
							{
								String DBtotalAsset2 = "$"+queryResultSet1.getString("NVL(SUM(S.BALTOT_BALANCE),0)");
								if(formAsset3.equalsIgnoreCase(DBtotalAsset2))
								{
									Reporter.logEvent(Status.PASS,"Validating if the total Asset value is equal in both form and database.",
											"Total asset value is same in database and form.", false);
								}
								else
								{
									Reporter.logEvent(Status.FAIL,"Validating if the total internal fixed fund value is equal in both form and database.",
											"Total internal fixed fund value is not same in database and form.", false);
								}
							}
						}
			else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
			}		
		}
	
	
	
	
}