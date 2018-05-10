package pageobject.DPMD;
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

/**
 * @author smykjn
 */
public class DPMD_PART_GRPING_4 {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DPMD_PART_GRPING_4";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String DMTY1_CODE_0;
	String param9122;
	int dbcountbeforeexe;
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
	public void setDMTY1_CODE_0(String dMTY1_CODE_0) {
		DMTY1_CODE_0 = dMTY1_CODE_0;
	}
	public void setparam9122(String Param9122) {
		param9122 = Param9122;
	}
	
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setDMTY1_CODE_0(Stock.GetParameterValue("DMTY1_CODE_0"));
		this.setparam9122(Stock.GetParameterValue("param9122"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&DMTY1_CODE_0="+DMTY1_CODE_0+"&setparam9122="+param9122+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for DPMD_PART_GRPING_4 service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void validateInDatabaseBeforeExe() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("dpmdAddgroupingtodmtycode")[1],DMTY1_CODE_0);
		dbcountbeforeexe=DB.getRecordSetCount(queryResultSet);
		if(dbcountbeforeexe>=0){
			Reporter.logEvent(Status.INFO, "Number of records before running service.\nQuery:"+Stock.getTestQuery("dpmdAddgroupingtodmtycode")[1], 
					"Number of records:"+dbcountbeforeexe, false);
		}
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
			Reporter.logEvent(Status.PASS, "Run DPMD_PART_GRPING_4 service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DPMD_PART_GRPING_4 service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values From Response: ", "DMTY1_DESCR_0: "+ doc.getElementsByTagName("DMTY1_DESCR_0").item(0).getTextContent()+
					"\nDMTY1_DOC_CLASS_0: "+ doc.getElementsByTagName("DMTY1_DOC_CLASS_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("dpmdAddgroupingtodmtycode")[1],DMTY1_CODE_0);
		int dbcountafterexe = DB.getRecordSetCount(queryResultSet);
		if(dbcountafterexe>0 && dbcountafterexe>dbcountbeforeexe){
			Reporter.logEvent(Status.PASS, "Verify whether record is inserted in Database.", "Record is inserted in Database\nTableName: doc_type#part_grpg_purpose", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify whether record is inserted in Database.", "Record is not inserted in Database\nTableName: doc_type#part_grpg_purpose", false);
		}
	}
}
