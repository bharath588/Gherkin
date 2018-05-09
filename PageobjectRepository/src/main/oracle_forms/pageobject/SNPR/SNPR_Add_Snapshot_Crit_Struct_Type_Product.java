package pageobject.SNPR;

import generallib.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class SNPR_Add_Snapshot_Crit_Struct_Type_Product {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SNPR_Add_Snapshot_Criterion_Structure_Type_to_Product";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	String param9113;
	
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
	public void setGET_PROD_ID_0(String gET_PROD_ID_0) {
		GET_PROD_ID_0 = gET_PROD_ID_0;
	}
	public String getGET_PROD_ID_0() {
		return this.GET_PROD_ID_0;
	}
	public void setParam9113(String param9113) {
		this.param9113 = param9113;
	}
	
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		this.setParam9113(Stock.GetParameterValue("param9113"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GET_PROD_ID_0="+GET_PROD_ID_0+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&param9113="+param9113+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SNPR service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			

			//print result
			System.out.println(response.toString());
			con.getContent();
			PrintWriter pw = new PrintWriter("response.txt");
			pw.println(response.toString().replaceAll("<></>", ""));
			pw.close();

			 BufferedReader br = new BufferedReader(new FileReader("response.txt"));
			 String line;
			 while((line = br.readLine()) != null)
			 {
			     System.out.println(line);
			 }
			 br.close();
			 
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File("response.txt"));
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run SNPR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SNPR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "PUSNCR1_TYPE_CODE_0: "+doc.getElementsByTagName("PUSNCR1_TYPE_CODE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	
	public void validateInDatabase() throws SQLException{
		String prod_id = this.GET_PROD_ID_0;
		String type_code = doc.getElementsByTagName("PUSNCR1_TYPE_CODE_0").item(0).getTextContent();
		String scs_id = this.param9113;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR1")[1], prod_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: PROD_SNAP_CRIT", false);
			while(queryResultSet.next()){
				scs_id = queryResultSet.getString("SCS_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "SCS_ID: "+queryResultSet.getString("SCS_ID")+
						"\nPROD ID: "+queryResultSet.getString("PROD_ID"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR2")[1],  scs_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: SNAP_CRIT_STRUC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nRSN_NARRATIVE: "+queryResultSet.getString("RSN_NARRATIVE")+
						"\nBASIS_CODE: "+queryResultSet.getString("BASIS_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR3")[1], type_code);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: SNAP_CRIT_STRUC", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "ID: "+queryResultSet.getString("ID")+
						"\nRSN_NARRATIVE: "+queryResultSet.getString("RSN_NARRATIVE")+
						"\nBASIS_CODE: "+queryResultSet.getString("BASIS_CODE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
	public void FlushData(){
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR1Delete")[1], this.GET_PROD_ID_0);
//		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR2")[1]);
//		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSNPR3")[1]);
	}
}
