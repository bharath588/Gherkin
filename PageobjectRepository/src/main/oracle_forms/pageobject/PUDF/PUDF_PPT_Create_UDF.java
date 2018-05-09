/**
 * 
 */
package pageobject.PUDF;

import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

/**
 * @author smykjn
 *
 */
public class PUDF_PPT_Create_UDF {
 public String queryString;
 private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PUDF_PPT_Create_UDF";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String QUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	String STEP_TYPE_LOV;
	String UDF_SEQ_LOV;
	String PPT_UDF_INFO_VALID_VALUE_4;
	
	
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
		CONTROL_SSN_DISPL_0=cONTROL_SSN_DISPL_0;
	}
	public void setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(String qUERY_CHG_MULTIPLE_SSN_EXT_LOV) {
		QUERY_CHG_MULTIPLE_SSN_EXT_LOV=qUERY_CHG_MULTIPLE_SSN_EXT_LOV;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV=sTEP_TYPE_LOV;
	}
	public void setUDF_SEQ_LOV(String uDF_SEQ_LOV) {
		UDF_SEQ_LOV=uDF_SEQ_LOV;
	}
	public void setPPT_UDF_INFO_VALID_VALUE_4(String ppt_UDF_INFO_VALID_VALUE_4) {
		PPT_UDF_INFO_VALID_VALUE_4=ppt_UDF_INFO_VALID_VALUE_4;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		this.setQUERY_CHG_MULTIPLE_SSN_EXT_LOV(Stock.GetParameterValue("QUERY_CHG_MULTIPLE_SSN_EXT_LOV"));
		this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		this.setUDF_SEQ_LOV(Stock.GetParameterValue("UDF_SEQ_LOV"));
		this.setPPT_UDF_INFO_VALID_VALUE_4(Stock.GetParameterValue("PPT_UDF_INFO_VALID_VALUE_4"));
		
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&QUERY_CHG_MULTIPLE_SSN_EXT_LOV="+QUERY_CHG_MULTIPLE_SSN_EXT_LOV+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+""
						+ "&UDF_SEQ_LOV="+UDF_SEQ_LOV+"&PPT_UDF_INFO_VALID_VALUE_4="+PPT_UDF_INFO_VALID_VALUE_4+""
								+"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PUDF service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: "+serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PUDF service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PUDF service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","PopupMessages: "+doc.getElementsByTagName("PopupMessages").item(0).getTextContent()+
					"\nCONTROL_BUTTON_OTHER_FORMS_0: "+doc.getElementsByTagName("CONTROL_BUTTON_OTHER_FORMS_0").item(0).getTextContent()+
					"\nCONTROL_HEADER_0:"+doc.getElementsByTagName("CONTROL_HEADER_0")+
					"\nTRANSACTION_CODE_0:"+doc.getElementsByTagName("TRANSACTION_CODE_0")+
					"\nTRANSACTION_DESCR_0:"+doc.getElementsByTagName("TRANSACTION_DESCR_0"),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
}
