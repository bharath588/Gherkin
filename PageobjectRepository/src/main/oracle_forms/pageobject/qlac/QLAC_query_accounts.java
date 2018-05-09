package pageobject.qlac;

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

public class QLAC_query_accounts {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/QLAC_Create_New";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String  CFG_CONTROL_EV_NARRATIVE_0;
	String  CFG_CONTROL_IND_ID_0 ;
	String  CFG_CONTROL_IND_ID_0_X1 ;
	String  CONTROL_SELECTION_0 ;
	String  GIVA_PMT_FREQ_CODE_LOV ;
	String  GLWB_INV_ACCT_APO_ID_LOV ;
	String  GLWB_INV_ACCT_QLAC_ELECT_DATE_0 ;
	String  GLWB_INV_ACCT_QLAC_PYMT_START_DATE_0 ;
	String  LOGON_DATABASE ;
	String  LOGON_PASSWORD ;
	String  LOGON_USERNAME ;
	
	public void setCFG_CONTROL_EV_NARRATIVE_0(String cFG_CONTROL_EV_NARRATIVE_0) {
		CFG_CONTROL_EV_NARRATIVE_0 = cFG_CONTROL_EV_NARRATIVE_0;
	}
	public void setCFG_CONTROL_IND_ID_0(String cFG_CONTROL_IND_ID_0) {
		CFG_CONTROL_IND_ID_0 = cFG_CONTROL_IND_ID_0;
	}
	public void setCFG_CONTROL_IND_ID_0_X1(String cFG_CONTROL_IND_ID_0_X1) {
		CFG_CONTROL_IND_ID_0_X1 = cFG_CONTROL_IND_ID_0_X1;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setGIVA_PMT_FREQ_CODE_LOV(String gIVA_PMT_FREQ_CODE_LOV) {
		GIVA_PMT_FREQ_CODE_LOV = gIVA_PMT_FREQ_CODE_LOV;
	}
	public void setGLWB_INV_ACCT_APO_ID_LOV(String gLWB_INV_ACCT_APO_ID_LOV) {
		GLWB_INV_ACCT_APO_ID_LOV = gLWB_INV_ACCT_APO_ID_LOV;
	}
	public void setGLWB_INV_ACCT_QLAC_ELECT_DATE_0(
			String gLWB_INV_ACCT_QLAC_ELECT_DATE_0) {
		GLWB_INV_ACCT_QLAC_ELECT_DATE_0 = gLWB_INV_ACCT_QLAC_ELECT_DATE_0;
	}
	public void setGLWB_INV_ACCT_QLAC_PYMT_START_DATE_0(
			String gLWB_INV_ACCT_QLAC_PYMT_START_DATE_0) {
		GLWB_INV_ACCT_QLAC_PYMT_START_DATE_0 = gLWB_INV_ACCT_QLAC_PYMT_START_DATE_0;
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
	
	String  GLWB_BENEBASE_BAL_QLAC_BAL_RSN_CODE_0;
String	GLWB_BENEBASE_BAL_QLAC_EV_ID_0;
String	GLWB_BENEBASE_BAL_QLAC_WITHDRAWAL_PERCENT_0;
String	GLWB_INV_ACCT_QLAC_APO_ID_0;
String	GLWB_INV_ACCT_QLAC_APO_TYPE_CODE_0;
String	GLWB_INV_ACCT_QLAC_COLA_PCT_0;
String	GLWB_INV_ACCT_QLAC_GA_ID_0;
String	GLWB_INV_ACCT_QLAC_ID_0;
String	GLWB_INV_ACCT_QLAC_IND_ID_0;
String	GLWB_INV_ACCT_QLAC_NB_LEGAL_NAME_0;
String	GLWB_INV_ACCT_QLAC_NB_NAME_0;
String	GLWB_INV_ACCT_QLAC_NB_RIDER_CODE_0;
String	GLWB_INV_ACCT_QLAC_PYMT_FREQ_CODE_0;
String	GLWB_INV_ACCT_QLAC_PYMT_OPTION_CODE_0;
String	GLWB_INV_ACCT_QLAC_RTN_OF_PREMIUM_IND_0;
String	GLWB_INV_ACCT_QLAC_SDIO_ID_0;
String	GLWB_INV_ACCT_QLAC_STATUS_CODE_0;

public String getGLWB_BENEBASE_BAL_QLAC_BAL_RSN_CODE_0() {
	return GLWB_BENEBASE_BAL_QLAC_BAL_RSN_CODE_0;
}
public String getGLWB_BENEBASE_BAL_QLAC_EV_ID_0() {
	return GLWB_BENEBASE_BAL_QLAC_EV_ID_0;
}
public String getGLWB_BENEBASE_BAL_QLAC_WITHDRAWAL_PERCENT_0() {
	return GLWB_BENEBASE_BAL_QLAC_WITHDRAWAL_PERCENT_0;
}
public String getGLWB_INV_ACCT_QLAC_APO_ID_0() {
	return GLWB_INV_ACCT_QLAC_APO_ID_0;
}
public String getGLWB_INV_ACCT_QLAC_APO_TYPE_CODE_0() {
	return GLWB_INV_ACCT_QLAC_APO_TYPE_CODE_0;
}
public String getGLWB_INV_ACCT_QLAC_COLA_PCT_0() {
	return GLWB_INV_ACCT_QLAC_COLA_PCT_0;
}
public String getGLWB_INV_ACCT_QLAC_GA_ID_0() {
	return GLWB_INV_ACCT_QLAC_GA_ID_0;
}
public String getGLWB_INV_ACCT_QLAC_ID_0() {
	return GLWB_INV_ACCT_QLAC_ID_0;
}
public String getGLWB_INV_ACCT_QLAC_IND_ID_0() {
	return GLWB_INV_ACCT_QLAC_IND_ID_0;
}
public String getGLWB_INV_ACCT_QLAC_NB_LEGAL_NAME_0() {
	return GLWB_INV_ACCT_QLAC_NB_LEGAL_NAME_0;
}
public String getGLWB_INV_ACCT_QLAC_NB_NAME_0() {
	return GLWB_INV_ACCT_QLAC_NB_NAME_0;
}
public String getGLWB_INV_ACCT_QLAC_NB_RIDER_CODE_0() {
	return GLWB_INV_ACCT_QLAC_NB_RIDER_CODE_0;
}
public String getGLWB_INV_ACCT_QLAC_PYMT_FREQ_CODE_0() {
	return GLWB_INV_ACCT_QLAC_PYMT_FREQ_CODE_0;
}
public String getGLWB_INV_ACCT_QLAC_PYMT_OPTION_CODE_0() {
	return GLWB_INV_ACCT_QLAC_PYMT_OPTION_CODE_0;
}
public String getGLWB_INV_ACCT_QLAC_RTN_OF_PREMIUM_IND_0() {
	return GLWB_INV_ACCT_QLAC_RTN_OF_PREMIUM_IND_0;
}
public String getGLWB_INV_ACCT_QLAC_SDIO_ID_0() {
	return GLWB_INV_ACCT_QLAC_SDIO_ID_0;
}
public String getGLWB_INV_ACCT_QLAC_STATUS_CODE_0() {
	return GLWB_INV_ACCT_QLAC_STATUS_CODE_0;
} 

public void setServiceParameters()
{
	this.setCFG_CONTROL_EV_NARRATIVE_0(Stock.GetParameterValue("CFG_CONTROL_EV_NARRATIVE_0"));
	this.setCFG_CONTROL_IND_ID_0(Stock.GetParameterValue("CFG_CONTROL_IND_ID_0"));
	this.setCFG_CONTROL_IND_ID_0_X1(Stock.GetParameterValue("CFG_CONTROL_IND_ID_0_X1"));
	this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
	this.setGIVA_PMT_FREQ_CODE_LOV(Stock.GetParameterValue("GIVA_PMT_FREQ_CODE_LOV"));
	this.setGLWB_INV_ACCT_APO_ID_LOV(Stock.GetParameterValue("GLWB_INV_ACCT_APO_ID_LOV"));
	this.setGLWB_INV_ACCT_QLAC_ELECT_DATE_0(Stock.GetParameterValue("GLWB_INV_ACCT_QLAC_ELECT_DATE_0"));
	this.setGLWB_INV_ACCT_QLAC_PYMT_START_DATE_0(Stock.GetParameterValue("GLWB_INV_ACCT_QLAC_PYMT_START_DATE_0"));
	this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
	this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
	this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
	
	queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
			"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CFG_CONTROL_IND_ID_0="+CFG_CONTROL_IND_ID_0+"&GLWB_INV_ACCT_QLAC_ELECT_DATE_0="+GLWB_INV_ACCT_QLAC_ELECT_DATE_0+
			"&GLWB_INV_ACCT_APO_ID_LOV="+GLWB_INV_ACCT_APO_ID_LOV+"&GLWB_INV_ACCT_QLAC_PYMT_START_DATE_0="+GLWB_INV_ACCT_QLAC_PYMT_START_DATE_0+
			"&GIVA_PMT_FREQ_CODE_LOV="+GIVA_PMT_FREQ_CODE_LOV+"&CFG_CONTROL_EV_NARRATIVE_0="+CFG_CONTROL_EV_NARRATIVE_0+
			"&CFG_CONTROL_IND_ID_0_X1="+CFG_CONTROL_IND_ID_0_X1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
	
	Reporter.logEvent(Status.INFO, "Prepare test data for QLAC service", this.queryString.replaceAll("&", "\n"), false);
	
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
		System.out.println(serviceURL);
		doc.getDocumentElement().normalize();
		Reporter.logEvent(Status.PASS, "Run QLAC Account Maintainance service", "Execution Done!", false);
	} catch (Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		Reporter.logEvent(Status.FAIL, "QLAC Account Maintainance service", "Running Failed:", false);
	}
	
}
public void validateOutput(){
	String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

	if (Error.isEmpty()){
		Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
	} else {
		Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		System.out.println(Error);
	}
	
		Reporter.logEvent(Status.INFO, "GA_ID", doc.getElementsByTagName("GLWB_INV_ACCT_QLAC_GA_ID_0").item(0).getTextContent(), false);
		Reporter.logEvent(Status.INFO, "IND_ID", doc.getElementsByTagName("GLWB_INV_ACCT_QLAC_IND_ID_0").item(0).getTextContent(), false);
		Reporter.logEvent(Status.INFO, "STATUS CODE", doc.getElementsByTagName("GLWB_INV_ACCT_QLAC_STATUS_CODE_0").item(0).getTextContent(), false);
}

public void validatInDatabase() throws SQLException{
	Reporter.logEvent(Status.INFO,"Validations of record generated in database","Validating record creation",false);
	queryResultSet = DB.executeQuery(Stock.getTestQuery("queryTocheckRecordCreated")[0], Stock.getTestQuery("queryTocheckRecordCreated")[1], doc.getElementsByTagName("GLWB_INV_ACCT_QLAC_IND_ID_0").item(0).getTextContent());
	if(DB.getRecordSetCount(queryResultSet) > 0 )
	{
		Reporter.logEvent(Status.PASS,"Validations of record generated in database","Record has been created",false);
		while(queryResultSet.next())
		{
			String ind_id = queryResultSet.getString("IND_ID");
			String ga_id = queryResultSet.getString("GA_ID");
			System.out.println(ind_id);
			System.out.println(ga_id);
		
			Reporter.logEvent(Status.PASS,"From DATABASE: ","IND ID: "+ ind_id+
				"GA ID: "+ga_id + "SDIO_ID: "+ queryResultSet.getString("SDIO_ID")+
				"STATUS_CODE: "+ queryResultSet.getString("STATUS_CODE"),false);
		}
	
	}else{
		Reporter.logEvent(Status.INFO ,"Validations of record generated in database","Record has not created",false);
	}
	}
}
