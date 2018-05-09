package pageobject.cashdropflow;

import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class BATR_PositiveFlow {
	protected String LOGON_USERNAME = "";
	protected String LOGON_PASSWORD = "";
	protected String LOGON_DATABASE = "";
	protected String BHRM1_BKA_CODE_LOV1 = "";
	protected String BHRM1_AMOUNT_0 = "";
	protected String BHRM1_CASH_EFFDATE_0 = "";
	protected String BHRM1_BHSX_ID_LOV0 = "";
	protected String RMNC1_GA_ID_0 = "";
	protected String RMNC1_AMOUNT_0 = "";
	public String queryString;
//	private String serviceURL =  "http://jwpwdn1:8080/ServiceManager/Macro/ExecMacro/output_test";
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/output_test";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	private String outStatusBarMessages;
	public String getOutStatusBarMessages() {
		return outStatusBarMessages;
	}

	private String outBHRM1_BHSX_ID_0;
	public String getOutBHRM1_BHSX_ID_0() {
		return outBHRM1_BHSX_ID_0;
	}

	private String outRMNC1_WKUN_SHORT_NAME_0;
	public String getOutRMNC1_WKUN_SHORT_NAME_0() {
		return outRMNC1_WKUN_SHORT_NAME_0;
	}

	private String outRMNC1_ID_0;
	public String getOutRMNC1_ID_0() {
		return outRMNC1_ID_0;
	}

	private String outRMNC1_CASH_EFFDATE_0;
	public String getOutRMNC1_CASH_EFFDATE_0() {
		return outRMNC1_CASH_EFFDATE_0;
	}

	private String outPopupMessages;
	public String getOutPopupMessages() {
		return outPopupMessages;
	}

	private String outError;
	public String getOutError() {
		return outError;
	}

	private String outBHRM1_BKA_CODE_LOV1;
	public String getOutBHRM1_BKA_CODE_LOV1() {
		return outBHRM1_BKA_CODE_LOV1;
	}

	public void setServiceParameters () {
		this.LOGON_USERNAME = Stock.GetParameterValue("LOGON_USERNAME");
		this.LOGON_PASSWORD = Stock.GetParameterValue("LOGON_PASSWORD");
		this.LOGON_DATABASE = Stock.GetParameterValue("LOGON_DATABASE");
		this.BHRM1_BKA_CODE_LOV1 = Stock.GetParameterValue("BANK_CODE");
		this.BHRM1_AMOUNT_0 = Stock.GetParameterValue("AMOUNT");
		this.BHRM1_CASH_EFFDATE_0 = Stock.GetParameterValue("CASH_EFFDATE");
		this.BHRM1_BHSX_ID_LOV0 = Stock.GetParameterValue("BHRM1_BHSX_ID_LOV0");
		this.RMNC1_GA_ID_0 = Stock.GetParameterValue("RMNC1_GA_ID");
		this.RMNC1_AMOUNT_0 = Stock.GetParameterValue("AMOUNT");
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE + "&CONTROL_SELECTION_0=BATR&BHRM1_BKA_CODE_LOV1=" + BHRM1_BKA_CODE_LOV1 + "&BHRM1_AMOUNT_0=" + BHRM1_AMOUNT_0 + "&BHRM1_CASH_EFFDATE_0=" + BHRM1_CASH_EFFDATE_0 + "&BHRM1_BHSX_ID_LOV0=" + BHRM1_BHSX_ID_LOV0 + "&RMNC1_GA_ID_0=" + RMNC1_GA_ID_0 + "&RMNC1_AMOUNT_0=" + RMNC1_AMOUNT_0 + "&numOfRowsInTable=20&json=false&handlePopups=false";
		Reporter.logEvent(Status.INFO, "Prepare test data for Cash Drop service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run Cash Drop service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run Cash Drop service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() {
		//Output parameters:
		String BHRM1_BKA_CODE_LOV1 = doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").item(0).getTextContent();
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		String PopupMessages = doc.getElementsByTagName("PopupMessages").item(0).getTextContent();
		String RMNC1_EV_ID_0 = doc.getElementsByTagName("RMNC1_EV_ID_0").item(0).getTextContent();
		String StatusBarMessages = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();

		//Print output parameters:
		System.out.println("BHRM1_BKA_CODE_LOV1: "+BHRM1_BKA_CODE_LOV1);
		System.out.println("Error: "+Error);
		System.out.println("PopupMessages: "+PopupMessages);
		System.out.println("RMNC1_EV_ID_0: "+RMNC1_EV_ID_0);
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
		
		if (BHRM1_BKA_CODE_LOV1.equalsIgnoreCase("TestValue!")){
			Reporter.logEvent(Status.PASS, "Validate BHRM1_BKA_CODE_LOV1 = \"TestValue!\"", "Expected Value: TestValue!\nActual Value: " + BHRM1_BKA_CODE_LOV1, false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate BHRM1_BKA_CODE_LOV1 = \"TestValue!\"", "Expected Value: TestValue!\nActual Value: " + BHRM1_BKA_CODE_LOV1, false);
		}
	}
	
	public void printOutput() {
		Reporter.logEvent(Status.INFO, "Output from Service:", doc.getTextContent(), false);
		System.out.println(doc.getTextContent());
	}
	
	public void extractOutput(){
		outBHRM1_BKA_CODE_LOV1 = (doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").getLength() > 0)? doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").item(0).getTextContent() : null;
		outError = (doc.getElementsByTagName("Error").getLength() > 0) ? doc.getElementsByTagName("Error").item(0).getTextContent() : null;
		outPopupMessages = (doc.getElementsByTagName("PopupMessages").getLength() > 0) ? doc.getElementsByTagName("PopupMessages").item(0).getTextContent() : null;
		outStatusBarMessages = (doc.getElementsByTagName("StatusBarMessages").getLength() > 0) ? doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent() : null;

		outBHRM1_BHSX_ID_0 = (doc.getElementsByTagName("BHRM1_BHSX_ID_0").getLength() > 0)? doc.getElementsByTagName("BHRM1_BHSX_ID_0").item(0).getTextContent() : null;
		outRMNC1_WKUN_SHORT_NAME_0 = (doc.getElementsByTagName("RMNC1_WKUN_SHORT_NAME_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_WKUN_SHORT_NAME_0").item(0).getTextContent() : null;
		outRMNC1_ID_0 = (doc.getElementsByTagName("RMNC1_ID_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent() : null;
		outRMNC1_CASH_EFFDATE_0 = (doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").getLength() > 0)? doc.getElementsByTagName("RMNC1_CASH_EFFDATE_0").item(0).getTextContent() : null;
	}

}
