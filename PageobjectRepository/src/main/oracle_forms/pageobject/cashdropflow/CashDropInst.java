package pageobject.cashdropflow;

//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.List;
import java.net.URLDecoder;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import core.framework.Globals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;


//@RunWith(Parameterized.class)

public class CashDropInst {
	protected String AGRM1_AMOUNT_0 = "";
	protected String AGRM1_AMOUNT_1 = "";
	protected String AGRM1_PAYROLL_DATE_0 = "";
	protected String AGRM1_PAYROLL_DATE_0_X1 = "";
	protected String AGRM1_PAYROLL_DATE_1 = "";
	protected String AGRM1_SDMT_CODE_LOV1 = "";
	protected String AGRM1_SDMT_CODE_LOV1_X1 = "";
	protected String ASK_PYRLDT_ANSWER_0 = "";
	protected String BHRM1_AMOUNT_0 = "";
	protected String BHRM1_BHSX_ID_LOV0 = "";
	protected String BHRM1_BKA_CODE_0 = "";
	protected String BHRM1_CASH_EFFDATE_0 = "";
	protected String BHRM1_CASH_EFFDATE_0_X1 = "";
	protected String CONTROL_ACTION_CODE_0 = "";
	protected String CONTROL_SELECTION_0 = "";
	protected String CONTROL_SELECTION_0_X1 = "";
	protected String CONTROL_SELECTION_0_X2 = "";
	protected String LOGON_DATABASE = "";
	protected String LOGON_PASSWORD = "";
	protected String LOGON_USERNAME = "";
	protected String RMNC1_AMOUNT_0 = "";
	protected String RMNC1_GA_ID_0 = "";
	protected String RMNC1_ID_0 = "";
	protected String RMNC1_ID_0_X1 = "";
	protected String TPINRM1_AMOUNT1_0 = "";
	protected String TPINRM1_AMOUNT1_1 = "";
	protected String TPINRM1_AMOUNT2_0 = "";
	protected String TPINRM1_AMOUNT2_1 = "";
	protected String TPINRM1_SSN_0 = "";
	protected String TPINRM1_SSN_0_X1 = "";
	protected String TPINRM1_SSN_1 = "";
	public String queryString;
	private String serviceURL =  "http://jwpwdn1:8080/ServiceManager/Macro/ExecMacro/cashdropinst";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
//	public CashDropInst (String AGRM1_AMOUNT_0,String AGRM1_AMOUNT_1,String AGRM1_PAYROLL_DATE_0,String AGRM1_PAYROLL_DATE_0_X1,String AGRM1_PAYROLL_DATE_1,String AGRM1_SDMT_CODE_LOV1,String AGRM1_SDMT_CODE_LOV1_X1,String ASK_PYRLDT_ANSWER_0,String BHRM1_AMOUNT_0,String BHRM1_BHSX_ID_LOV0,String BHRM1_BKA_CODE_0,String BHRM1_CASH_EFFDATE_0,String BHRM1_CASH_EFFDATE_0_X1,String CONTROL_ACTION_CODE_0,String CONTROL_SELECTION_0,String CONTROL_SELECTION_0_X1,String CONTROL_SELECTION_0_X2,String LOGON_DATABASE,String LOGON_PASSWORD,String LOGON_USERNAME,String RMNC1_AMOUNT_0,String RMNC1_GA_ID_0,String RMNC1_ID_0,String RMNC1_ID_0_X1,String TPINRM1_AMOUNT1_0,String TPINRM1_AMOUNT1_1,String TPINRM1_AMOUNT2_0,String TPINRM1_AMOUNT2_1,String TPINRM1_SSN_0,String TPINRM1_SSN_0_X1,String TPINRM1_SSN_1) {
//		this.AGRM1_AMOUNT_0 = AGRM1_AMOUNT_0;
//		this.AGRM1_AMOUNT_1 = AGRM1_AMOUNT_1;
//		this.AGRM1_PAYROLL_DATE_0 = AGRM1_PAYROLL_DATE_0;
//		this.AGRM1_PAYROLL_DATE_0_X1 = AGRM1_PAYROLL_DATE_0_X1;
//		this.AGRM1_PAYROLL_DATE_1 = AGRM1_PAYROLL_DATE_1;
//		this.AGRM1_SDMT_CODE_LOV1 = AGRM1_SDMT_CODE_LOV1;
//		this.AGRM1_SDMT_CODE_LOV1_X1 = AGRM1_SDMT_CODE_LOV1_X1;
//		this.ASK_PYRLDT_ANSWER_0 = ASK_PYRLDT_ANSWER_0;
//		this.BHRM1_AMOUNT_0 = BHRM1_AMOUNT_0;
//		this.BHRM1_BHSX_ID_LOV0 = BHRM1_BHSX_ID_LOV0;
//		this.BHRM1_BKA_CODE_0 = BHRM1_BKA_CODE_0;
//		this.BHRM1_CASH_EFFDATE_0 = BHRM1_CASH_EFFDATE_0;
//		this.BHRM1_CASH_EFFDATE_0_X1 = BHRM1_CASH_EFFDATE_0_X1;
//		this.CONTROL_ACTION_CODE_0 = CONTROL_ACTION_CODE_0;
//		this.CONTROL_SELECTION_0 = CONTROL_SELECTION_0;
//		this.CONTROL_SELECTION_0_X1 = CONTROL_SELECTION_0_X1;
//		this.CONTROL_SELECTION_0_X2 = CONTROL_SELECTION_0_X2;
//		this.LOGON_DATABASE = LOGON_DATABASE;
//		this.LOGON_PASSWORD = LOGON_PASSWORD;
//		this.LOGON_USERNAME = LOGON_USERNAME;
//		this.RMNC1_AMOUNT_0 = RMNC1_AMOUNT_0;
//		this.RMNC1_GA_ID_0 = RMNC1_GA_ID_0;
//		this.RMNC1_ID_0 = RMNC1_ID_0;
//		this.RMNC1_ID_0_X1 = RMNC1_ID_0_X1;
//		this.TPINRM1_AMOUNT1_0 = TPINRM1_AMOUNT1_0;
//		this.TPINRM1_AMOUNT1_1 = TPINRM1_AMOUNT1_1;
//		this.TPINRM1_AMOUNT2_0 = TPINRM1_AMOUNT2_0;
//		this.TPINRM1_AMOUNT2_1 = TPINRM1_AMOUNT2_1;
//		this.TPINRM1_SSN_0 = TPINRM1_SSN_0;
//		this.TPINRM1_SSN_0_X1 = TPINRM1_SSN_0_X1;
//		this.TPINRM1_SSN_1 = TPINRM1_SSN_1;
//		
//		queryString = "?AGRM1_AMOUNT_0="+AGRM1_AMOUNT_0+"&AGRM1_AMOUNT_1="+AGRM1_AMOUNT_1+"&AGRM1_PAYROLL_DATE_0="+AGRM1_PAYROLL_DATE_0+"&AGRM1_PAYROLL_DATE_0_X1="+AGRM1_PAYROLL_DATE_0_X1+"&AGRM1_PAYROLL_DATE_1="+AGRM1_PAYROLL_DATE_1+"&AGRM1_SDMT_CODE_LOV1="+AGRM1_SDMT_CODE_LOV1+"&AGRM1_SDMT_CODE_LOV1_X1="+AGRM1_SDMT_CODE_LOV1_X1+"&ASK_PYRLDT_ANSWER_0="+ASK_PYRLDT_ANSWER_0+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0+"&BHRM1_BHSX_ID_LOV0="+BHRM1_BHSX_ID_LOV0+"&BHRM1_BKA_CODE_0="+BHRM1_BKA_CODE_0+"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0+"&BHRM1_CASH_EFFDATE_0_X1="+BHRM1_CASH_EFFDATE_0_X1+"&CONTROL_ACTION_CODE_0="+CONTROL_ACTION_CODE_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&CONTROL_SELECTION_0_X2="+CONTROL_SELECTION_0_X2+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0+"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0+"&RMNC1_ID_0="+RMNC1_ID_0+"&RMNC1_ID_0_X1="+RMNC1_ID_0_X1+"&TPINRM1_AMOUNT1_0="+TPINRM1_AMOUNT1_0+"&TPINRM1_AMOUNT1_1="+TPINRM1_AMOUNT1_1+"&TPINRM1_AMOUNT2_0="+TPINRM1_AMOUNT2_0+"&TPINRM1_AMOUNT2_1="+TPINRM1_AMOUNT2_1+"&TPINRM1_SSN_0="+TPINRM1_SSN_0+"&TPINRM1_SSN_0_X1="+TPINRM1_SSN_0_X1+"&TPINRM1_SSN_1="+TPINRM1_SSN_1+"";
//	}


//	@Parameters
//	public static List<Object[]> data() {
//		return Arrays.asList(new Object[][] {
//			{"1000","1000","23-JAN-2015","20-JAN-2016","20-JAN-2016","1","3","N","2000","1","INWF","20-DEC-2015","23-DEC-2015","A","BATR","AGRM","KOSP","d_inst","nsnw","nsnw","2000","1200000-01","800342279","800342279","500","500","500","500","96669669","966669669","948913596"}
//		});
//	}
//
//
//	@Test
//	public void runTestCase() {
//		System.out.println("=== Start cashdropinst test ===");
//		try {
//		String url =  "http://5t5zmx1:8080/ServiceManager/Macro/ExecMacro/cashdropinst";
//		String queryString = "?AGRM1_AMOUNT_0="+AGRM1_AMOUNT_0+"&AGRM1_AMOUNT_1="+AGRM1_AMOUNT_1+"&AGRM1_PAYROLL_DATE_0="+AGRM1_PAYROLL_DATE_0+"&AGRM1_PAYROLL_DATE_0_X1="+AGRM1_PAYROLL_DATE_0_X1+"&AGRM1_PAYROLL_DATE_1="+AGRM1_PAYROLL_DATE_1+"&AGRM1_SDMT_CODE_LOV1="+AGRM1_SDMT_CODE_LOV1+"&AGRM1_SDMT_CODE_LOV1_X1="+AGRM1_SDMT_CODE_LOV1_X1+"&ASK_PYRLDT_ANSWER_0="+ASK_PYRLDT_ANSWER_0+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0+"&BHRM1_BHSX_ID_LOV0="+BHRM1_BHSX_ID_LOV0+"&BHRM1_BKA_CODE_0="+BHRM1_BKA_CODE_0+"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0+"&BHRM1_CASH_EFFDATE_0_X1="+BHRM1_CASH_EFFDATE_0_X1+"&CONTROL_ACTION_CODE_0="+CONTROL_ACTION_CODE_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&CONTROL_SELECTION_0_X2="+CONTROL_SELECTION_0_X2+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0+"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0+"&RMNC1_ID_0="+RMNC1_ID_0+"&RMNC1_ID_0_X1="+RMNC1_ID_0_X1+"&TPINRM1_AMOUNT1_0="+TPINRM1_AMOUNT1_0+"&TPINRM1_AMOUNT1_1="+TPINRM1_AMOUNT1_1+"&TPINRM1_AMOUNT2_0="+TPINRM1_AMOUNT2_0+"&TPINRM1_AMOUNT2_1="+TPINRM1_AMOUNT2_1+"&TPINRM1_SSN_0="+TPINRM1_SSN_0+"&TPINRM1_SSN_0_X1="+TPINRM1_SSN_0_X1+"&TPINRM1_SSN_1="+TPINRM1_SSN_1+"";
//		queryString = URLDecoder.decode(queryString, "UTF-8");
//		url = url + queryString;
//		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//		docBuilderFactory.setIgnoringComments(true);
//		docBuilderFactory.setIgnoringElementContentWhitespace(true);
//		docBuilderFactory.setNamespaceAware(true);
//		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//		Document doc = docBuilder.parse(url);
//		doc.getDocumentElement().normalize();
//		
//		//Output parameters:
//		String BHRM1_BKA_CODE_LOV1 = doc.getElementsByTagName("BHRM1_BKA_CODE_LOV1").item(0).getTextContent();
//		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
//		String PopupMessages = doc.getElementsByTagName("PopupMessages").item(0).getTextContent();
//		String RMNC1_EV_ID_0 = doc.getElementsByTagName("RMNC1_EV_ID_0").item(0).getTextContent();
//		String StatusBarMessages = doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent();
//
//		//Print output parameters:
//		System.out.println("BHRM1_BKA_CODE_LOV1: "+BHRM1_BKA_CODE_LOV1);
//		System.out.println("Error: "+Error);
//		System.out.println("PopupMessages: "+PopupMessages);
//		System.out.println("RMNC1_EV_ID_0: "+RMNC1_EV_ID_0);
//		System.out.println("StatusBarMessages: "+StatusBarMessages);
//
//		//TestCase validations:
//		Assert.assertTrue(Error.isEmpty());
//
//
//		System.out.println("=== End cashdropinst test ===");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void main(String[] args) {
//		CashDropInst tcj = new CashDropInst("1000","1000","23-JAN-2015","20-JAN-2016","20-JAN-2016","1","3","N","2000","1","INWF","20-DEC-2015","23-DEC-2015","A","BATR","AGRM","KOSP","d_inst","nsnw","nsnw","2000","1200000-01","800342279","800342279","500","500","500","500","96669669","966669669","948913596");
//		tcj.runTestCase();
//	}
	
	public void setServiceParameters () {
		this.AGRM1_AMOUNT_0 = Stock.GetParameterValue("AGRM1_AMOUNT_0");
		this.AGRM1_AMOUNT_1 = Stock.GetParameterValue("AGRM1_AMOUNT_1");
		this.AGRM1_PAYROLL_DATE_0 = Stock.GetParameterValue("AGRM1_PAYROLL_DATE_0");
		this.AGRM1_PAYROLL_DATE_0_X1 = Stock.GetParameterValue("AGRM1_PAYROLL_DATE_0_X1");
		this.AGRM1_PAYROLL_DATE_1 = Stock.GetParameterValue("AGRM1_PAYROLL_DATE_1");
		this.AGRM1_SDMT_CODE_LOV1 = Stock.GetParameterValue("AGRM1_SDMT_CODE_LOV1");
		this.AGRM1_SDMT_CODE_LOV1_X1 = Stock.GetParameterValue("AGRM1_SDMT_CODE_LOV1_X1");
		this.ASK_PYRLDT_ANSWER_0 = Stock.GetParameterValue("ASK_PYRLDT_ANSWER_0");
		this.BHRM1_AMOUNT_0 = Stock.GetParameterValue("BHRM1_AMOUNT_0");
		this.BHRM1_BHSX_ID_LOV0 = Stock.GetParameterValue("BHRM1_BHSX_ID_LOV0");
		this.BHRM1_BKA_CODE_0 = Stock.GetParameterValue("BHRM1_BKA_CODE_0");
		this.BHRM1_CASH_EFFDATE_0 = Stock.GetParameterValue("BHRM1_CASH_EFFDATE_0");
		this.BHRM1_CASH_EFFDATE_0_X1 = Stock.GetParameterValue("BHRM1_CASH_EFFDATE_0_X1");
		this.CONTROL_ACTION_CODE_0 = Stock.GetParameterValue("CONTROL_ACTION_CODE_0");
		this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
		this.CONTROL_SELECTION_0_X1 = Stock.GetParameterValue("CONTROL_SELECTION_0_X1");
		this.CONTROL_SELECTION_0_X2 = Stock.GetParameterValue("CONTROL_SELECTION_0_X2");
		this.LOGON_DATABASE = Stock.GetParameterValue("LOGON_DATABASE");
		this.LOGON_PASSWORD = Stock.GetParameterValue("LOGON_PASSWORD");
		this.LOGON_USERNAME = Stock.GetParameterValue("LOGON_USERNAME");
		this.RMNC1_AMOUNT_0 = Stock.GetParameterValue("RMNC1_AMOUNT_0");
		this.RMNC1_GA_ID_0 = Stock.GetParameterValue("RMNC1_GA_ID_0");
		this.RMNC1_ID_0 = Stock.GetParameterValue("RMNC1_ID_0");
		this.RMNC1_ID_0_X1 = Stock.GetParameterValue("RMNC1_ID_0_X1");
		this.TPINRM1_AMOUNT1_0 = Stock.GetParameterValue("TPINRM1_AMOUNT1_0");
		this.TPINRM1_AMOUNT1_1 = Stock.GetParameterValue("TPINRM1_AMOUNT1_1");
		this.TPINRM1_AMOUNT2_0 = Stock.GetParameterValue("TPINRM1_AMOUNT2_0");
		this.TPINRM1_AMOUNT2_1 = Stock.GetParameterValue("TPINRM1_AMOUNT2_1");
		this.TPINRM1_SSN_0 = Stock.GetParameterValue("TPINRM1_SSN_0");
		this.TPINRM1_SSN_0_X1 = Stock.GetParameterValue("TPINRM1_SSN_0_X1");
		this.TPINRM1_SSN_1 = Stock.GetParameterValue("TPINRM1_SSN_1");
		
		queryString = "?AGRM1_AMOUNT_0="+AGRM1_AMOUNT_0+"&AGRM1_AMOUNT_1="+AGRM1_AMOUNT_1+"&AGRM1_PAYROLL_DATE_0="+AGRM1_PAYROLL_DATE_0+"&AGRM1_PAYROLL_DATE_0_X1="+AGRM1_PAYROLL_DATE_0_X1+"&AGRM1_PAYROLL_DATE_1="+AGRM1_PAYROLL_DATE_1+"&AGRM1_SDMT_CODE_LOV1="+AGRM1_SDMT_CODE_LOV1+"&AGRM1_SDMT_CODE_LOV1_X1="+AGRM1_SDMT_CODE_LOV1_X1+"&ASK_PYRLDT_ANSWER_0="+ASK_PYRLDT_ANSWER_0+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0+"&BHRM1_BHSX_ID_LOV0="+BHRM1_BHSX_ID_LOV0+"&BHRM1_BKA_CODE_0="+BHRM1_BKA_CODE_0+"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0+"&BHRM1_CASH_EFFDATE_0_X1="+BHRM1_CASH_EFFDATE_0_X1+"&CONTROL_ACTION_CODE_0="+CONTROL_ACTION_CODE_0+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+"&CONTROL_SELECTION_0_X2="+CONTROL_SELECTION_0_X2+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0+"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0+"&RMNC1_ID_0="+RMNC1_ID_0+"&RMNC1_ID_0_X1="+RMNC1_ID_0_X1+"&TPINRM1_AMOUNT1_0="+TPINRM1_AMOUNT1_0+"&TPINRM1_AMOUNT1_1="+TPINRM1_AMOUNT1_1+"&TPINRM1_AMOUNT2_0="+TPINRM1_AMOUNT2_0+"&TPINRM1_AMOUNT2_1="+TPINRM1_AMOUNT2_1+"&TPINRM1_SSN_0="+TPINRM1_SSN_0+"&TPINRM1_SSN_0_X1="+TPINRM1_SSN_0_X1+"&TPINRM1_SSN_1="+TPINRM1_SSN_1+"";
		Reporter.logEvent(Status.INFO, "Prepare test data for Cash Drop service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runCashDropService() {
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
	}
}
