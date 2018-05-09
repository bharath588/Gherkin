package pageobject.cashdropflow;

import generallib.General;

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

public class KOSP_Process {
	
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/KOSP_Process";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	
	private String CONTROL_SELECTION_0;
	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public String getRMNC1_ID_0() {
		return RMNC1_ID_0;
	}
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}
	public String getRMNC1_ID_0_X1() {
		return RMNC1_ID_0_X1;
	}
	public void setRMNC1_ID_0_X1(String rMNC1_ID_0_X1) {
		RMNC1_ID_0_X1 = rMNC1_ID_0_X1;
	}
	private String LOGON_DATABASE;
	private String LOGON_USERNAME;
	private String LOGON_PASSWORD;
	private String RMNC1_ID_0;
	private String RMNC1_ID_0_X1;
	
	public void setServiceParameters()
	{
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE 
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
				+"&RMNC1_ID_0="+RMNC1_ID_0
				+"&RMNC1_ID_0_X1="+RMNC1_ID_0_X1
				+"&numOfRowsInTable=20&json=false&handlePopups=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for KOSP service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService()
	{
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
			Reporter.logEvent(Status.PASS, "Run KOSP_Process service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run KOSP_Process service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String processCodeInd = "";
		String processCodeAG="";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("remittanceAllocationQuery")[1], RMNC1_ID_0);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				processCodeInd = queryResultSet.getString("PROCESS_STATUS_CODE");
				if(processCodeInd.equalsIgnoreCase("UN"))
				{
					Reporter.logEvent(Status.PASS, "Verify the status of cash drop for the individuals after\n KOSP in Ind_remit table",
							"Individual SSN : " + queryResultSet.getString("SSN")+
							"\nprocess status code \n" + 
							"Expected : UN\n"+
							"Actual : "+processCodeInd, false);	
				}
				
			}
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("remittanceAllocationQueryGroup")[1], RMNC1_ID_0);
		
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				processCodeAG = queryResultSet.getString("PROCESS_STATUS_CODE");
				if(processCodeAG.equalsIgnoreCase("S4"))
				{
					Reporter.logEvent(Status.PASS, "Verify the status of cash drop for the group id after\n KOSP in AG_remit table",
							"Group Client ID : " + queryResultSet.getString("GC_ID")+
							"\nprocess status code \n" + 
							"Expected : S4\n"+
							"Actual : "+processCodeAG, false);	
				}
			}
		}
		
		
		
		
		//Reporter.logEvent(Status.INFO, "Verify the status of cash drop after KOSP","The process status code in AG_REMIT is "+ processCodeInd, false);
		
		/*if(processCode.equalsIgnoreCase("D"))
		{
			Reporter.logEvent(Status.PASS, "Verify the status of cash drop after KOSP","The cash drop is successful"+" As the process status code is equal to D", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the status of cash drop after KOSP","The cash drop is failed due to data setup issues"+" As the process status code is equal to RJ", false);
		}*/
	}
}
