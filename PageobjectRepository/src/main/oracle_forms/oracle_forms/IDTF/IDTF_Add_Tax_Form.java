package pageobject.IDTF;

import generallib.General;
import generallib.ToXML;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.apache.commons.io.FileUtils;
import org.testng.reporters.XMLUtils;
import org.testng.xml.XmlUtils;
import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;
import com.gargoylesoftware.htmlunit.xml.XmlUtil;

import core.framework.Globals;

public class IDTF_Add_Tax_Form {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IDTF_add_tax_form";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String param9671;
	String STEP_TYPE_LOV;
	
	public void setParam9671(String param9671) {
		this.param9671 = param9671;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
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
	public void setCONTROL_SSN_DISPL_0(String cONTROL_SSN_DISPL_0) {
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	
	
	public void setServiceParameters()	throws SQLException
	{
	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setParam9671(Stock.GetParameterValue("param9671"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+
				"&STEP_TYPE_LOV="+STEP_TYPE_LOV+"&param9671="+param9671+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for IDTF service", this.queryString.replaceAll("&", "\n"), false);
	
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
			Reporter.logEvent(Status.PASS, "Run IDTF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run IDTF service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "TAX FORM CODE: "+ doc.getElementsByTagName("INDIVIDUAL_TAX_FORM_TAX_FORM_CODE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String tax_code_resp = doc.getElementsByTagName("INDIVIDUAL_TAX_FORM_TAX_FORM_CODE_0").item(0).getTextContent();
		String tax_code_db = null;
		
		ResultSet queryResult = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIndIdIDTF")[1],  CONTROL_SSN_DISPL_0);
		if(queryResult.next()){

		String ind_id = queryResult.getString("ID");		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIDTtaxForm")[1],  ind_id);
		
			if(queryResultSet.next()){
			tax_code_db = queryResultSet.getString("TAX_FORM_CODE");
				Reporter.logEvent(Status.PASS, "Values From DATABASE:\n Table: INDIVIDUAL_TAX_FORM ", "IND ID: "+queryResultSet.getString("IND_ID")+
					"\nTAX FORM CODE: "+queryResultSet.getString("TAX_FORM_CODE")+					
					"\nEXPIRATION DATE: "+queryResultSet.getString("EXPIRATION_DATE")+
					"\nRECEIVED DATE: "+queryResultSet.getString("RECEIVED_DATE"), false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
			}
			if(tax_code_resp.equalsIgnoreCase(tax_code_db)){
				Reporter.logEvent(Status.PASS, "Comparing and Validating Tax Form Code from Response and Database", "Both the values are same as Expected"+
						"\nTax Form Code: "+"From Response: "+tax_code_resp+"\nFrom Database: "+tax_code_db, false);
			}
			else{
				Reporter.logEvent(Status.FAIL, "Comparing and Validating Tax Form Code from Response and Database", "Both the values are not same as Expected"+
						"\nTax Form Code: "+"From Response: "+tax_code_resp+"\nFrom Database: "+tax_code_db, false);
			}

		}
	}
}
