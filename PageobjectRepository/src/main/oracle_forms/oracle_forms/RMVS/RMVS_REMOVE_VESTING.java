/**
 * 
 */
package pageobject.RMVS;

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

/**
 * @author smykjn
 *
 */
public class RMVS_REMOVE_VESTING {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/RMVS_REMOVE_VESTING";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	//input parameters
	String GROUP_BLOCK_GA_ID_0;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	
	
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
	public void setGROUP_BLOCK_GA_ID_0(String gROUP_BLOCK_GA_ID_0) {
		GROUP_BLOCK_GA_ID_0 = gROUP_BLOCK_GA_ID_0;
	}
	
	
	public void setServiceParameters()
	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGROUP_BLOCK_GA_ID_0(Stock.GetParameterValue("GROUP_BLOCK_GA_ID_0"));

		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE=" + LOGON_DATABASE + 
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&GROUP_BLOCK_GA_ID_0="+GROUP_BLOCK_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for RMVS_REMOVE_VESTING service", this.queryString.replaceAll("&", "\n"), false);
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
			//System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run RMVS_REMOVE_VESTING service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run RMVS_REMOVE_VESTING service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{

		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForRMVS")[1],GROUP_BLOCK_GA_ID_0);
		if(queryResultSet.next())		
			Reporter.logEvent(Status.FAIL, "Validate No records exist for below query.\n"+Stock.getTestQuery("queryForRMVS")[1],
					"Count of number of records: "+DB.getRecordSetCount(queryResultSet), false);
		else
			Reporter.logEvent(Status.PASS, "Validate No records exist for below query.\n"+Stock.getTestQuery("queryForRMVS")[1],
					"Count of number of records: "+DB.getRecordSetCount(queryResultSet), false);
		
		/*queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForVSCI")[1],GROUP_BLOCK_GA_ID_0);
		if(queryResultSet.next())
			Reporter.logEvent(Status.FAIL, "Validate No records exist for below query.\n"+Stock.getTestQuery("queryForVSCI")[1],
					"Count of number of records: "+DB.getRecordSetCount(queryResultSet), false);
		else
			Reporter.logEvent(Status.PASS, "Validate No records exist for below query.\n"+Stock.getTestQuery("queryForVSCI")[1],
					"Count of number of records: "+DB.getRecordSetCount(queryResultSet), false);
	*/
	}
}
