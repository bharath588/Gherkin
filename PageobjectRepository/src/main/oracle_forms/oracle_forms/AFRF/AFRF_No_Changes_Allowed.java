package pageobject.AFRF;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import core.framework.Globals;

public class AFRF_No_Changes_Allowed {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AFRF_No_Changes_Allowed";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PROV_COMPANY_CODE_0;
	
	
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
	public void setPROV_COMPANY_CODE_0(String pROV_COMPANY_CODE_0) {
		PROV_COMPANY_CODE_0 = pROV_COMPANY_CODE_0;
	}
	
	String EXT_PRICE_CLASS_COMP_RATE_ADVISOR_BASIS_POINTS_0 ;
	String EXT_PRICE_CLASS_COMP_RATE_CREATION_EV_ID_0;
	String  EXT_PRICE_CLASS_COMP_RATE_EFFDATE_0;
	String   EXT_PRICE_CLASS_COMP_RATE_PRICING_CLASS_0;
	String EXT_PRICE_CLASS_COMP_RATE_RATE_TYPE_CODE_0 ;
	String EXT_PRICE_CLASS_COMP_RATE_SDIO_ID_0;
	String EXT_PRICE_CLASS_COMP_RATE_TERMDATE_0;
	String EXT_PRICE_CLASS_COMP_RATE_TERMINATION_EV_ID_0;
	String EXT_PRICE_CLASS_COMP_RATE_TPA_BASIS_POINTS_0;
	String PROV_COMPANY_NAME_0;
	
	
	public String getEXT_PRICE_CLASS_COMP_RATE_ADVISOR_BASIS_POINTS_0() {
		return EXT_PRICE_CLASS_COMP_RATE_ADVISOR_BASIS_POINTS_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_CREATION_EV_ID_0() {
		return EXT_PRICE_CLASS_COMP_RATE_CREATION_EV_ID_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_EFFDATE_0() {
		return EXT_PRICE_CLASS_COMP_RATE_EFFDATE_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_PRICING_CLASS_0() {
		return EXT_PRICE_CLASS_COMP_RATE_PRICING_CLASS_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_RATE_TYPE_CODE_0() {
		return EXT_PRICE_CLASS_COMP_RATE_RATE_TYPE_CODE_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_SDIO_ID_0() {
		return EXT_PRICE_CLASS_COMP_RATE_SDIO_ID_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_TERMDATE_0() {
		return EXT_PRICE_CLASS_COMP_RATE_TERMDATE_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_TERMINATION_EV_ID_0() {
		return EXT_PRICE_CLASS_COMP_RATE_TERMINATION_EV_ID_0;
	}
	public String getEXT_PRICE_CLASS_COMP_RATE_TPA_BASIS_POINTS_0() {
		return EXT_PRICE_CLASS_COMP_RATE_TPA_BASIS_POINTS_0;
	}
	
public void setServiceParameters()
{
	this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
	this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
	this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
	this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
	this.PROV_COMPANY_CODE_0 = Stock.GetParameterValue("PROV_COMPANY_CODE_0");
	queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD 
			+ "&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 
			+ "&PROV_COMPANY_CODE_0=" + PROV_COMPANY_CODE_0 +"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
	Reporter.logEvent(Status.INFO, "Prepare test data for AFRF service", this.queryString.replaceAll("&", "\n"), false);
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
		Reporter.logEvent(Status.PASS, "Run AFRF service", "Execution Done!", false);
	} catch (Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		Reporter.logEvent(Status.FAIL, "Run AFRF service", "Running Failed:", false);
	}
}

public void validateOutput()
{
	String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
	
	if (Error.isEmpty()){
		Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
	} else {
		Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
	}
	

	//Output parameters:
			int editableFieldCount = 0;
			String msgEditableField = "";
			String StatusBarMessages = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
			StatusBarMessages = StatusBarMessages.replaceAll("\\s", "");
			StatusBarMessages = StatusBarMessages.replaceAll(";", " ");
			String[] statusBarMeesage = StatusBarMessages.split("\\.");
			
			List<String> statusBar = Arrays.asList(statusBarMeesage);
			
			Reporter.logEvent(Status.INFO, "Check the status bar meesages", "The status bar messages are :\n"+StatusBarMessages, false);
			
			for(int i =1 ; i < statusBar.size()-1; i++)
			{
				if(!StringUtils.containsIgnoreCase(statusBar.get(i), "FRM-40200"))
				{
					msgEditableField = statusBar.get(i);
					editableFieldCount ++;
				}
			}
			
			if(editableFieldCount == 1)
			{
				Reporter.logEvent(Status.PASS, "Verify that it Should not allow to update any field except Termination Date", 
						"None of the fields are editable execpt the termination date\n"+ "The StatusBarMessage of editable field is :\n"
								+ msgEditableField + "\nAnd the status bar message of non editable field is\n"
								+statusBar.get(2), false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verify that it Should not allow to update any field except Termination Date", 
						"Few other of the fields are editable execpt the termination date also\n"+ " The StatusBarMessage of editable field is :\n"
								+ msgEditableField + "\nAnd the status bar message of non editable field is\n"
								+statusBar.get(2), false);
			}
			
	
}
public void extractOutput()
{
	PROV_COMPANY_NAME_0 = doc.getElementsByTagName("PROV_COMPANY_NAME_0").item(0).getTextContent();
	Reporter.logEvent(Status.INFO, "Prov company name in response", PROV_COMPANY_NAME_0, false);
}
}
