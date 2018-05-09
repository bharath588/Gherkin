package pageobject.cashdropflow;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Stock;

import org.w3c.dom.Document;

public class AGRM_Process_Dup {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/AGRM_Process";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	public String AGRM1_AMOUNT_0;
	String  AGRM1_PAYROLL_DATE_0;
	String  AGRM1_SDMT_CODE_LOV1;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String LOGON_DATABASE_X1;
	String LOGON_PASSWORD_X1;
	String LOGON_USERNAME_X1;
	String RMNC1_ID_0;
	
	
	public void setAGRM1_AMOUNT_0(String aGRM1_AMOUNT_0) {
		AGRM1_AMOUNT_0 = aGRM1_AMOUNT_0;
	}
	public void setAGRM1_PAYROLL_DATE_0(String aGRM1_PAYROLL_DATE_0) {
		AGRM1_PAYROLL_DATE_0 = aGRM1_PAYROLL_DATE_0;
	}
	public void setAGRM1_SDMT_CODE_LOV1(String aGRM1_SDMT_CODE_LOV1) {
		AGRM1_SDMT_CODE_LOV1 = aGRM1_SDMT_CODE_LOV1;
	}
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
	public void setLOGON_DATABASE_X1(String lOGON_DATABASE_X1) {
		LOGON_DATABASE_X1 = lOGON_DATABASE_X1;
	}
	public void setLOGON_PASSWORD_X1(String lOGON_PASSWORD_X1) {
		LOGON_PASSWORD_X1 = lOGON_PASSWORD_X1;
	}
	public void setLOGON_USERNAME_X1(String lOGON_USERNAME_X1) {
		LOGON_USERNAME_X1 = lOGON_USERNAME_X1;
	}
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}
	
	
}
