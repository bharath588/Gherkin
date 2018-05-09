/**
 * 
 */
package pageobject.PL2P;

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
public class PL2P_P2P_Deselect {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PL2P_P2P_Deselect";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String BLK_FR_TO_FROM_GA_ID_0;
	String BLK_FR_TO_TO_GA_ID_0;
	String BLK_INDIVIDUAL_L_SSN_CHK_0;
	String BLK_INDIVIDUAL_L_SSN_CHK_1;
	String BLK_INDIVIDUAL_L_SSN_CHK_1_X1;
	
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
	public void setBLK_FR_TO_FROM_GA_ID_0(String blk_FR_TO_FROM_GA_ID_0) {
		BLK_FR_TO_FROM_GA_ID_0 = blk_FR_TO_FROM_GA_ID_0;
	}
	public void setBLK_FR_TO_TO_GA_ID_0(String blk_FR_TO_TO_GA_ID_0) {
		BLK_FR_TO_TO_GA_ID_0 = blk_FR_TO_TO_GA_ID_0;
	}
	public void setBLK_INDIVIDUAL_L_SSN_CHK_0(String blk_INDIVIDUAL_L_SSN_CHK_0) {
		BLK_INDIVIDUAL_L_SSN_CHK_0 = blk_INDIVIDUAL_L_SSN_CHK_0;
	}
	public void setBLK_INDIVIDUAL_L_SSN_CHK_1(String blk_INDIVIDUAL_L_SSN_CHK_1) {
		BLK_INDIVIDUAL_L_SSN_CHK_1 = blk_INDIVIDUAL_L_SSN_CHK_1;
	}
	public void setBLK_INDIVIDUAL_L_SSN_CHK_1_X1(String blk_INDIVIDUAL_L_SSN_CHK_1_X1) {
		BLK_INDIVIDUAL_L_SSN_CHK_1_X1 = blk_INDIVIDUAL_L_SSN_CHK_1_X1;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setBLK_FR_TO_FROM_GA_ID_0(Stock.GetParameterValue("BLK_FR_TO_FROM_GA_ID_0"));
		this.setBLK_FR_TO_TO_GA_ID_0(Stock.GetParameterValue("BLK_FR_TO_TO_GA_ID_0"));
		this.setBLK_INDIVIDUAL_L_SSN_CHK_0(Stock.GetParameterValue("BLK_INDIVIDUAL_L_SSN_CHK_0"));
		this.setBLK_INDIVIDUAL_L_SSN_CHK_1(Stock.GetParameterValue("BLK_INDIVIDUAL_L_SSN_CHK_1"));
		this.setBLK_INDIVIDUAL_L_SSN_CHK_1_X1(Stock.GetParameterValue("BLK_INDIVIDUAL_L_SSN_CHK_1_X1"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&BLK_FR_TO_FROM_GA_ID_0="+BLK_FR_TO_FROM_GA_ID_0
				+"&BLK_FR_TO_TO_GA_ID_0="+BLK_FR_TO_TO_GA_ID_0+"&BLK_INDIVIDUAL_L_SSN_CHK_0="+BLK_INDIVIDUAL_L_SSN_CHK_0
				+"&BLK_INDIVIDUAL_L_SSN_CHK_1="+BLK_INDIVIDUAL_L_SSN_CHK_1+"&BLK_INDIVIDUAL_L_SSN_CHK_1_X1="+BLK_INDIVIDUAL_L_SSN_CHK_1_X1
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PL2P_P2P_Deselect service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PL2P_P2P_Deselect service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PL2P_P2P_Deselect service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","BLK_CTRL_FORM_TXN_CODE_0-"+doc.getElementsByTagName("BLK_CTRL_FORM_TXN_CODE_0").item(0).getTextContent()+
					"\nBLK_CTRL_L_ALL_SSN_CHK_0: "+doc.getElementsByTagName("BLK_CTRL_L_ALL_SSN_CHK_0").item(0).getTextContent()+
					"\nBLK_EVENT_EVENT_STATUS_0:"+doc.getElementsByTagName("BLK_EVENT_EVENT_STATUS_0").item(0).getTextContent()+
					"\nBLK_EVENT_MASTER_EV_ID_0:"+doc.getElementsByTagName("BLK_EVENT_MASTER_EV_ID_0").item(0).getTextContent()+
					"\nBLK_FR_TO_FROM_PLAN_NAME_0:"+doc.getElementsByTagName("BLK_FR_TO_FROM_PLAN_NAME_0").item(0).getTextContent()+
					"\nBLK_INDIVIDUAL_SSN_0:"+doc.getElementsByTagName("BLK_INDIVIDUAL_SSN_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForPL2PDeselect")[1],
				this.BLK_FR_TO_FROM_GA_ID_0, this.BLK_FR_TO_TO_GA_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0)
			Reporter.logEvent(Status.PASS,"Validate Records exist in Database\nExpected: Query"
					+ " should return record.\n"+Stock.getTestQuery("queryForPL2PDeselect")[1], "Records exists in Database\nTable name: PART_GRP_TO_GRP_TRF"+
					"\nTotal number of Records: "+DB.getRecordSetCount(queryResultSet), false);
		else
			Reporter.logEvent(Status.FAIL, "Validating Records exists in Database.\n"+
		Stock.getTestQuery("queryForPL2PDeselect")[1], "Records doesn't exists in Database",false);
		
	}
}
