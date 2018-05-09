package pageobject.IACS;

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

public class IACS_Create {

	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IACS_Create";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String param9224;
	String WIRE_INFO_BHSX_ID_LOV4;
	String RCC_SSN_0;
	String RCC_SDMT_CODE_LOV2;
	String RCC_GROSS_AMOUNT_0;
	
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
	public void setParam9224(String param9224) {
		this.param9224 = param9224;
	}
	public void setWIRE_INFO_BHSX_ID_LOV4(String wIRE_INFO_BHSX_ID_LOV4) {
		WIRE_INFO_BHSX_ID_LOV4 = wIRE_INFO_BHSX_ID_LOV4;
	}
	public void setRCC_SSN_0(String rCC_SSN_0) {
		RCC_SSN_0 = rCC_SSN_0;
	}
	public void setRCC_SDMT_CODE_LOV2(String rCC_SDMT_CODE_LOV2) {
		RCC_SDMT_CODE_LOV2 = rCC_SDMT_CODE_LOV2;
	}
	public void setRCC_GROSS_AMOUNT_0(String rCC_GROSS_AMOUNT_0) {
		RCC_GROSS_AMOUNT_0 = rCC_GROSS_AMOUNT_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setParam9224(Stock.GetParameterValue("param9224"));
		this.setRCC_GROSS_AMOUNT_0(Stock.GetParameterValue("RCC_GROSS_AMOUNT_0"));
		this.setRCC_SDMT_CODE_LOV2(Stock.GetParameterValue("RCC_SDMT_CODE_LOV2"));
		this.setRCC_SSN_0(Stock.GetParameterValue("RCC_SSN_0"));
		this.setWIRE_INFO_BHSX_ID_LOV4(Stock.GetParameterValue("WIRE_INFO_BHSX_ID_LOV4"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&param9224="+param9224+"&WIRE_INFO_BHSX_ID_LOV4="+WIRE_INFO_BHSX_ID_LOV4+"&RCC_SSN_0="+RCC_SSN_0+"&RCC_SDMT_CODE_LOV2="+RCC_SDMT_CODE_LOV2+"&RCC_GROSS_AMOUNT_0="+RCC_GROSS_AMOUNT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for IACS service", this.queryString.replaceAll("&", "\n"), false);
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

			Reporter.logEvent(Status.PASS, "Run IACS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run IACS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "FULL_NAME: "+ doc.getElementsByTagName("RCC_FULL_NAME_0").item(0).getTextContent()+
					"\nGA_ID: "+doc.getElementsByTagName("RCC_GA_ID_0").item(0).getTextContent()+
					"\nGDMT_SEQNBR: "+doc.getElementsByTagName("RCC_GDMT_SEQNBR_0").item(0).getTextContent()+
					"\nSDDETY_CODE: "+doc.getElementsByTagName("RCC_SDDETY_CODE_0").item(0).getTextContent()+
					"\nSDMT_CODE: "+doc.getElementsByTagName("RCC_SDMT_CODE_0").item(0).getTextContent()+
					"\nBHRM_ID: "+doc.getElementsByTagName("WIRE_INFO_BHRM_ID_0").item(0).getTextContent()+
					"\nBHSK_ID: "+doc.getElementsByTagName("WIRE_INFO_BHSX_ID_0").item(0).getTextContent()+
					"\nBKA_CODE: "+doc.getElementsByTagName("WIRE_INFO_BKA_CODE_0").item(0).getTextContent()+
					"\nBKA_TYPE_CODE: "+doc.getElementsByTagName("WIRE_INFO_BKA_TYPE_CODE_0").item(0).getTextContent()+
					"\nAMOUNT: "+doc.getElementsByTagName("WIRE_INFO_WIRE_AMOUNT_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase()throws SQLException{
		String amt_resp = doc.getElementsByTagName("WIRE_INFO_WIRE_AMOUNT_0").item(0).getTextContent().replace("$", "").trim().split("\\.")[0];
	//	amt_resp = amt_resp.split("\\.")[0];
		String amt_db = null;
		String bhrm_id = doc.getElementsByTagName("WIRE_INFO_BHRM_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForIACS")[1], bhrm_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of data in the Database", "Record exists in the Database", false);
			while(queryResultSet.next()){
				amt_db = queryResultSet.getString("GROSS_AMOUNT");
				Reporter.logEvent(Status.INFO, "Values from Database\nTable Name: REMOTE_CASH_COLL", "IND_ID: "+queryResultSet.getString("IND_ID")+
					"\nSSN: "+queryResultSet.getString("SSN")+
					"\nGA_ID: "+queryResultSet.getString("GA_ID")+
					"\nBHRM_ID: "+queryResultSet.getString("BHRM_ID")+
					"\nBHSX_ID: "+queryResultSet.getString("BHSX_ID")+
					"\nBKA_CODE: "+queryResultSet.getString("BKA_CODE")+
					"\nSDDETY_CODE: "+queryResultSet.getString("SDDETY_CODE")+
					"\nSDMT_CODE: "+queryResultSet.getString("SDMT_CODE")+
					"\nGROSS_AMOUNT: "+queryResultSet.getString("GROSS_AMOUNT"), false);
			}
			if(amt_resp.equalsIgnoreCase(amt_db)){
				Reporter.logEvent(Status.PASS,"Comparing and validating Gross amount from response and Database","Both the values are same as expected"
						+"\nFrom Response: "+amt_resp+"\nFrom Database: "+amt_db,false);
			}else{
				Reporter.logEvent(Status.FAIL,"Comparing and validating Gross amount from response and Database","Both the values are not same"
						+"\nFrom Response: "+amt_resp+"\nFrom Database: "+amt_db,false);
			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating existence of data in the Database", "No Record exists in the Database", false);
		}
	}

}
