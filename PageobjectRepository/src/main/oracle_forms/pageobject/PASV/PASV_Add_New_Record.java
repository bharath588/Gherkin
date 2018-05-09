package pageobject.PASV;

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

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class PASV_Add_New_Record {
	
	public String queryString;
	private String serviceURL = "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PASV_Add_New2";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;

	//input param
	
	String CONTROL_SELECTION_0; 
	String CONTROL_SSN_DISPL_0; 
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME; 
	String param9775;
	String param9778;
	
	//output param
	
	String HireDate;

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
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

	public void setParam9775(String param9775) {
		this.param9775 = param9775;
	}
	
	public void setParam9778(String param9775) {
		this.param9778 = param9775;
	}

	public String getHireDate() {
		return HireDate;
	}

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setParam9775(Stock.GetParameterValue("param9775"));
		 this.setParam9778(Stock.GetParameterValue("param9775"));
	
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&param9778="+param9778+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for PASV service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PASV service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PASV service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException, Exception{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "Individual Last Name: "+doc.getElementsByTagName("INDIVIDUAL_LAST_NAME_0").item(0).getTextContent(), false);
		} else {
				Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
			
		}
				
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToPASVAddNew")[1], CONTROL_SSN_DISPL_0);
		
		if(queryResultSet.next())
		{
			Reporter.logEvent(Status.PASS, "Validating Record existence in Database", "Records exists in Database\nTable Name: PART_SERVICE", false);
			String status_effdate = queryResultSet.getString("STATUS_EFFDATE");
			String status_reason_code = queryResultSet.getString("STATUS_REASON_CODE");
			System.out.println("Status_EFFDATE : " + status_effdate + "\n" + "Status_Reason_Code : " + status_reason_code);
			Reporter.logEvent(Status.INFO, "Values From Database: \nA new Record has been created into table", "IND_ID: "+queryResultSet.getString("IND_ID")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE")+
					"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
					"\nSTATUS CODE: "+queryResultSet.getString("STATUS_CODE")+
					"\nPROVIDER CODE: "+queryResultSet.getString("PROVIDER_CODE")+
					"\nSTATUS REASON CODE: "+queryResultSet.getString("STATUS_REASON_CODE"), false);
			
			
		}
	}
		
}
