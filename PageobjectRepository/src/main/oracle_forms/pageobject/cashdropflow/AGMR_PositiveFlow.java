package pageobject.cashdropflow;

import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;


import core.framework.Globals;

public class AGMR_PositiveFlow {
	protected String LOGON_USERNAME = "";
	protected String LOGON_PASSWORD = "";
	protected String LOGON_DATABASE = "";
	protected String AMOUNT = "";
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public void setEVENT_ID(String eVENT_ID) {
		EVENT_ID = eVENT_ID;
	}

	protected String PAYROLL_DATE = Stock.GetParameterValue("PAYROLL_DATE");
	protected String EVENT_ID = "";
	protected String MONEY_TYPE = "";
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/Agrm_ouput";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	private String outStatusBarMessages;
	public String getOutStatusBarMessages() {
		return outStatusBarMessages;
	}

	private String outPopupMessages;
	public String getOutPopupMessages() {
		return outPopupMessages;
	}

	private String outError;
	public String getOutError() {
		return outError;
	}
	
	public void setMONEY_TYPE(String mONEY_TYPE) {
		MONEY_TYPE = mONEY_TYPE;
	}

	//private String outBHRM1_BKA_CODE_LOV1;
	private String outAGRM1_CASH_EFFDATE_0;
	public String getOutAGRM1_CASH_EFFDATE_0() {
		return outAGRM1_CASH_EFFDATE_0;
	}

	public String getOutAGRM1_COMP_IND_0() {
		return outAGRM1_COMP_IND_0;
	}

	public String getOutAGRM1_GA_ID_0() {
		return outAGRM1_GA_ID_0;
	}

	public String getOutAGRM1_GDMT_SEQNBR_0() {
		return outAGRM1_GDMT_SEQNBR_0;
	}

	public String getOutAGRM1_GROUP_DEF_MNTY_DESCR_0() {
		return outAGRM1_GROUP_DEF_MNTY_DESCR_0;
	}

	public String getOutAGRM1_PROCESS_STATUS_CODE_0() {
		return outAGRM1_PROCESS_STATUS_CODE_0;
	}

	public String getOutAGRM1_SDDETY_CODE_0() {
		return outAGRM1_SDDETY_CODE_0;
	}

	public String getOutAGRM1_SDMT_CODE_0() {
		return outAGRM1_SDMT_CODE_0;
	}

	private String outAGRM1_COMP_IND_0;
	private String outAGRM1_GA_ID_0;
	private String outAGRM1_GDMT_SEQNBR_0;
	private String outAGRM1_GROUP_DEF_MNTY_DESCR_0;
	private String outAGRM1_PROCESS_STATUS_CODE_0;
	private String outAGRM1_SDDETY_CODE_0;
	private String outAGRM1_SDMT_CODE_0;
	
	public void setServiceParameters () {
		this.LOGON_USERNAME = Stock.GetParameterValue("LOGON_USERNAME");
		this.LOGON_PASSWORD = Stock.GetParameterValue("LOGON_PASSWORD");
		this.LOGON_DATABASE = Stock.GetParameterValue("LOGON_DATABASE");
		this.AMOUNT = Stock.GetParameterValue("AMOUNT");
		
		
		//queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=BATR&BHRM1_BKA_CODE_LOV1=" + BHRM1_BKA_CODE_LOV1 + "&BHRM1_CASH_EFFDATE_0=" + BHRM1_CASH_EFFDATE_0 + "&BHRM1_BHSX_ID_LOV0=" + BHRM1_BHSX_ID_LOV0 + "&RMNC1_GA_ID_0=" + RMNC1_GA_ID_0 + "&RMNC1_AMOUNT_0=" + RMNC1_AMOUNT_0 + "&numOfRowsInTable=20&json=false&handlePopups=false";
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=AGRM&AGRM1_AMOUNT_0=" + AMOUNT + "&AGRM1_PAYROLL_DATE_0=" + PAYROLL_DATE + "&AGRM1_SDMT_CODE_LOV1=" + MONEY_TYPE + "&RMNC1_ID_0=" + EVENT_ID + "&numOfRowsInTable=20&json=false&handlePopups=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for AGMR service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
//		CashDropInst cdi = new CashDropInst();
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
			
			
			Reporter.logEvent(Status.PASS, "Run AGRM service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run AGRM service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() {
		//Output parameters:
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		String PopupMessages = doc.getElementsByTagName("PopupMessages").item(0).getTextContent();
		String StatusBarMessages = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
		
		String AGRM1_CASH_EFFDATE_0 = doc.getElementsByTagName("AGRM1_CASH_EFFDATE_0").item(0).getTextContent();
		String AGRM1_COMP_IND_0 = doc.getElementsByTagName("AGRM1_COMP_IND_0").item(0).getTextContent();
		String AGRM1_GA_ID_0  = doc.getElementsByTagName("AGRM1_GA_ID_0").item(0).getTextContent();
		String AGRM1_GDMT_SEQNBR_0 = doc.getElementsByTagName("AGRM1_GDMT_SEQNBR_0").item(0).getTextContent();
		String AGRM1_GROUP_DEF_MNTY_DESCR_0 = doc.getElementsByTagName("AGRM1_GROUP_DEF_MNTY_DESCR_0").item(0).getTextContent();
		String AGRM1_PROCESS_STATUS_CODE_0 = doc.getElementsByTagName("AGRM1_PROCESS_STATUS_CODE_0").item(0).getTextContent();
		String AGRM1_SDDETY_CODE_0 = doc.getElementsByTagName("AGRM1_SDDETY_CODE_0").item(0).getTextContent();
		String AGRM1_SDMT_CODE_0 = doc.getElementsByTagName("AGRM1_SDMT_CODE_0").item(0).getTextContent();

		//Print output parameters:
		System.out.println("AGRM1_CASH_EFFDATE_0: "+AGRM1_CASH_EFFDATE_0);
		System.out.println("AGRM1_COMP_IND_0: "+AGRM1_COMP_IND_0);
		System.out.println("AGRM1_GA_ID_0: "+AGRM1_GA_ID_0);
		System.out.println("AGRM1_GDMT_SEQNBR_0: "+AGRM1_GDMT_SEQNBR_0);
		System.out.println("AGRM1_GROUP_DEF_MNTY_DESCR_0: "+AGRM1_GROUP_DEF_MNTY_DESCR_0);
		System.out.println("AGRM1_PROCESS_STATUS_CODE_0: "+AGRM1_PROCESS_STATUS_CODE_0);
		System.out.println("AGRM1_SDDETY_CODE_0: "+AGRM1_SDDETY_CODE_0);
		System.out.println("AGRM1_SDMT_CODE_0: "+AGRM1_SDMT_CODE_0);
		System.out.println("Error: "+Error);
		System.out.println("PopupMessages: "+PopupMessages);
		System.out.println("StatusBarMessages: "+StatusBarMessages);

		//TestCase validations:
//		Assert.assertTrue(Error.isEmpty());
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
		}
		
		if (PopupMessages.isEmpty() == false){
			Reporter.logEvent(Status.PASS, "Validate response - Popup Messages in response is not empty", "PopupMessages tag is not empty.\nActual Value:\n" + PopupMessages, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Popup Messages in response is not empty", "PopupMessages tag is empty.", false);
		}
		
		if (AGRM1_PROCESS_STATUS_CODE_0.equalsIgnoreCase("S4")){
			Reporter.logEvent(Status.PASS, "Validate AGRM1_PROCESS_STATUS_CODE_0 = \"S4\"", "Expected Value: S4\nActual Value: " + AGRM1_PROCESS_STATUS_CODE_0, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate AGRM1_PROCESS_STATUS_CODE_0 = \"S4\"", "Expected Value: S4\nActual Value: " + AGRM1_PROCESS_STATUS_CODE_0, false);
		}
	}
	
	public void printOutput() {
		Reporter.logEvent(Status.INFO, "Output from Service:", doc.getTextContent(), false);
		System.out.println(doc.getTextContent());
	}
	
	public void extractOutput(){
//		outBHRM1_BKA_CODE_LOV1 = (doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").getLength() > 0)? doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").item(0).getTextContent() : null;
		outError = (doc.getElementsByTagName("Error").getLength() > 0) ? doc.getElementsByTagName("Error").item(0).getTextContent() : null;
		outPopupMessages = (doc.getElementsByTagName("PopupMessages").getLength() > 0) ? doc.getElementsByTagName("PopupMessages").item(0).getTextContent() : null;
		outStatusBarMessages = (doc.getElementsByTagName("StatusBarMessages").getLength() > 0) ? doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent() : null;

//		outBHRM1_BHSX_ID_0 = (doc.getElementsByTagName("BHRM1_BHSX_ID_0").getLength() > 0)? doc.getElementsByTagName("BHRM1_BHSX_ID_0").item(0).getTextContent() : null;
//		outRMNC1_WKUN_SHORT_NAME_0 = (doc.getElementsByTagName("RMNC1_WKUN_SHORT_NAME_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_WKUN_SHORT_NAME_0").item(0).getTextContent() : null;
//		outRMNC1_ID_0 = (doc.getElementsByTagName("RMNC1_ID_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent() : null;
//		outRMNC1_CASH_EFFDATE_0 = (doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").item(0).getTextContent() : null;
		
		outAGRM1_CASH_EFFDATE_0 = (doc.getElementsByTagName("AGRM1_CASH_EFFDATE_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_CASH_EFFDATE_0").item(0).getTextContent() : null;
		outAGRM1_COMP_IND_0 = (doc.getElementsByTagName("AGRM1_COMP_IND_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_COMP_IND_0").item(0).getTextContent() : null;
		outAGRM1_GA_ID_0  = (doc.getElementsByTagName("AGRM1_GA_ID_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_GA_ID_0").item(0).getTextContent() : null;
		outAGRM1_GDMT_SEQNBR_0 = (doc.getElementsByTagName("AGRM1_GDMT_SEQNBR_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_GDMT_SEQNBR_0").item(0).getTextContent() : null;
		outAGRM1_GROUP_DEF_MNTY_DESCR_0 = (doc.getElementsByTagName("AGRM1_GROUP_DEF_MNTY_DESCR_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_GROUP_DEF_MNTY_DESCR_0").item(0).getTextContent() : null;
		outAGRM1_PROCESS_STATUS_CODE_0 = (doc.getElementsByTagName("AGRM1_PROCESS_STATUS_CODE_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_PROCESS_STATUS_CODE_0").item(0).getTextContent() : null;
		outAGRM1_SDDETY_CODE_0 = (doc.getElementsByTagName("AGRM1_SDDETY_CODE_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_SDDETY_CODE_0").item(0).getTextContent() : null;
		outAGRM1_SDMT_CODE_0 = (doc.getElementsByTagName("AGRM1_SDMT_CODE_0").getLength() > 0)? doc.getElementsByTagName("AGRM1_SDMT_CODE_0").item(0).getTextContent() : null;
	}
}
