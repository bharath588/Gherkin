/**
 * 
 */
package pageobject.RIND;

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
 *
 */
public class GQ19RIND_Related_Individual_Add {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/gq19RIND_Related_Individual_Add";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String param9912;
	String REL_IND_RELATIONSHIP_CODE_0;
	String REL_IND_PERCENT_0;
	String param9913;
	String REL_IND_SSN_0;
	
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
	public void setparam9912(String param9912_) {
		param9912 = param9912_;
	}
	public void setREL_IND_RELATIONSHIP_CODE_0(String rEL_IND_RELATIONSHIP_CODE_0) {
		REL_IND_RELATIONSHIP_CODE_0 = rEL_IND_RELATIONSHIP_CODE_0;
	}
	public void setREL_IND_PERCENT_0(String rEL_IND_PERCENT_0) {
		REL_IND_PERCENT_0 = rEL_IND_PERCENT_0;
	}
	public void setparam9913(String param9913_) {
		param9913 = param9913_;
	}
	public void setREL_IND_SSN_0(String rEL_IND_SSN_0) {
		REL_IND_SSN_0 = rEL_IND_SSN_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setparam9912(Stock.GetParameterValue("param9912"));
		this.setREL_IND_RELATIONSHIP_CODE_0(Stock.GetParameterValue("REL_IND_RELATIONSHIP_CODE_0"));
		this.setREL_IND_PERCENT_0(Stock.GetParameterValue("REL_IND_PERCENT_0"));
		this.setparam9913(Stock.GetParameterValue("param9913"));
		this.setREL_IND_SSN_0(Stock.GetParameterValue("REL_IND_SSN_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0
				+"&param9912="+param9912+"&REL_IND_RELATIONSHIP_CODE_0="+REL_IND_RELATIONSHIP_CODE_0
				+"&REL_IND_PERCENT_0="+REL_IND_PERCENT_0+"&param9913="+param9913+"&REL_IND_SSN_0="+REL_IND_SSN_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for gq19RIND_Related_Individual_Add service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run gq19RIND_Related_Individual_Add service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run gq19RIND_Related_Individual_Add service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","REL_IND_N_MARITAL_STATUS_0-"+
			doc.getElementsByTagName("REL_IND_N_MARITAL_STATUS_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ssn=Stock.GetParameterValue("CONTROL_SSN_DISPL_0");
		String query = Stock.getTestQuery("benefeciaryQuery")[1];
		queryResultSet = DB.executeQuery(General.dbInstance,query,ssn);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in DB when below query is "
					+ "executed:"+query,"Records exists in DB", false);
			Reporter.logEvent(Status.INFO, "From DATABASE: \nTable Name: RELATED_INDIVIDUAL", 
				"Total Records: "+DB.getRecordSetCount(queryResultSet), false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in DB when below query is "
					+ "executed:"+query, "Records does not exist in DB.", false);
		}
	}
	
	public void resetDataForNextExecution() throws SQLException{
		String ssn=Stock.GetParameterValue("REL_IND_SSN_0");
		String delRelIndquery = Stock.getTestQuery("deleteRelatedIndividual")[1];
		String deladdress = Stock.getTestQuery("deleteAddress")[1];
		String delRelIndHist = Stock.getTestQuery("deleteRelatedIndHistory")[1];
		String benefeciaryQuery = Stock.getTestQuery("benefeciaryQuery")[1];
		queryResultSet = DB.executeQuery(General.dbInstance,delRelIndquery,ssn);
		queryResultSet = DB.executeQuery(General.dbInstance,deladdress,ssn);
		queryResultSet = DB.executeQuery(General.dbInstance,delRelIndHist,ssn);
		
		queryResultSet = DB.executeQuery(General.dbInstance,benefeciaryQuery,ssn);
		if(DB.getRecordSetCount(queryResultSet)==0)
			Reporter.logEvent(Status.INFO,"Delete the benefeciary record in order to "
					+ "reset the data for next execution.","Data has been reset.", false);
		else
			Reporter.logEvent(Status.WARNING,"Delete the benefeciary record in order to "
					+ "reset the data for next execution.","Data has not been reset.please execute below queries to"
							+ " reset the data.\n"+delRelIndquery+"\n"+deladdress+"\n"+delRelIndHist, false);
	}
}
