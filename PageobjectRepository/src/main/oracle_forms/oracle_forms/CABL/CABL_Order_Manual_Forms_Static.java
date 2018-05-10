/**
 * 
 */
package pageobject.CABL;

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
public class CABL_Order_Manual_Forms_Static {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CABL_Order_Manual_Forms_STATIC";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INDIVIDUAL_SSN_0;
	String GRP_MODULE_LOV;
	String STATIC_AVAILABLE_0;
	
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
	public void setINDIVIDUAL_SSN_0(String iNDIVIDUAL_SSN_0) {
		INDIVIDUAL_SSN_0 = iNDIVIDUAL_SSN_0;
	}
	public void setGRP_MODULE_LOV(String gRP_MODULE_LOV) {
		GRP_MODULE_LOV = gRP_MODULE_LOV;
	}
	public void setSTATIC_AVAILABLE_0(String sTATIC_AVAILABLE_0) {
		STATIC_AVAILABLE_0 = sTATIC_AVAILABLE_0;
	}

	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setINDIVIDUAL_SSN_0(Stock.GetParameterValue("INDIVIDUAL_SSN_0"));
		this.setGRP_MODULE_LOV(Stock.GetParameterValue("GRP_MODULE_LOV"));
		this.setSTATIC_AVAILABLE_0(Stock.GetParameterValue("STATIC_AVAILABLE_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&INDIVIDUAL_SSN_0="+INDIVIDUAL_SSN_0
				+"&GRP_MODULE_LOV="+GRP_MODULE_LOV+"&STATIC_AVAILABLE_0="+STATIC_AVAILABLE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CABL_Order_Manual_Forms_STATIC service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run CABL_Order_Manual_Forms_STATIC service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CABL_Order_Manual_Forms_STATIC service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ",""
					+ "IND_ADDRESS_LAST_NAME_0 : "+doc.getElementsByTagName("IND_ADDRESS_LAST_NAME_0").item(0).getTextContent()+
			"\nIND_HEADER_GA_ID_0 : "+doc.getElementsByTagName("IND_HEADER_GA_ID_0").item(0).getTextContent()+
			"\nIND_HEADER_SSN_0 : "+doc.getElementsByTagName("IND_HEADER_SSN_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String ga_id=doc.getElementsByTagName("IND_HEADER_GA_ID_0").item(0).getTextContent();
		String query = Stock.getTestQuery("cablOrderManulaFormsDynamic")[1];
		queryResultSet = DB.executeQuery(General.dbInstance,query,ga_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validate Records exist in DB when below query is "
					+ "executed:"+query,"Records exist in DB.", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validating Records exists in DB when below query is "
					+ "executed:"+query, "Records do not exist in DB.\ndmty_code='PFORMS is not assigend to the plan.", false);
		}
	}

}
