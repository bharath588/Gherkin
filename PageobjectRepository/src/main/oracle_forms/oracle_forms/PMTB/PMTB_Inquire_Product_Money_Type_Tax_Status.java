/**
 * 
 */
package pageobject.PMTB;

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
public class PMTB_Inquire_Product_Money_Type_Tax_Status {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PMTB_inquire_product_money_type_tax_status";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_PROD_ID_0;
	String PMTTBRL1_SDMT_CODE_0;
	
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
	public void setPMTTBRL1_SDMT_CODE_0(String pMTTBRL1_SDMT_CODE_0) {
		PMTTBRL1_SDMT_CODE_0 = pMTTBRL1_SDMT_CODE_0;
	}
	
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGET_PROD_ID_0(Stock.GetParameterValue("GET_PROD_ID_0"));
		this.setPMTTBRL1_SDMT_CODE_0(Stock.GetParameterValue("PMTTBRL1_SDMT_CODE_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&GET_PROD_ID_0="+GET_PROD_ID_0+"&PMTTBRL1_SDMT_CODE_0="+PMTTBRL1_SDMT_CODE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PMTB_inquire_product_money_type_tax_status service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PMTB_inquire_product_money_type_tax_status service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PMTB_inquire_product_money_type_tax_status service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		String statusBarMsg =  doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
		if (Error.isEmpty())
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		else 
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		
		if(statusBarMsg.contains("FRM-"))
			Reporter.logEvent(Status.PASS,"Validate Lower left hand corner will display 'FRM-(some number) Last record of query retrieved.",""
					+ "FRM-(some number) is displayed.check below the response from Auraplayer:"+statusBarMsg, false);
		else
			Reporter.logEvent(Status.FAIL,"Validate Lower left hand corner will display 'FRM-(some number) Last record of query retrieved.",""
					+ "FRM-(some number) is not displayed in auraplayer response.check below the response from Auraplayer:"+statusBarMsg, false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("pmtbProductMoneyTypeTax")[1]);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validate records exist in DB when below query is executed.\n"
					+ Stock.getTestQuery("pmtbProductMoneyTypeTax")[1], ""
							+ "Records exist in DB.\nNo. of Records from DB:"+DB.getRecordSetCount(queryResultSet), false);
			if(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "CNT_TAX_STATUS_CODE: "+queryResultSet.getString("CNT_TAX_STATUS_CODE")+
					"\nERN_TAX_STATUS_CODE: "+queryResultSet.getString("ERN_TAX_STATUS_CODE"), false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
}
