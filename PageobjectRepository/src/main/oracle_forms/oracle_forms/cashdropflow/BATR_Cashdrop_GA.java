package pageobject.cashdropflow;

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

public class BATR_Cashdrop_GA {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/BATR_REMIT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	private String CONTROL_SELECTION_0;
	private String LOGON_DATABASE;
	private String LOGON_USERNAME;
	private String LOGON_PASSWORD;
	private String BHRM1_BKA_CODE_0;
	private String BHRM1_AMOUNT_0;
	private String BHRM1_CASH_EFFDATE_0;
	private String BHRM1_BHSX_ID_0;
	private String RMNC1_GA_ID_0;	
	private String RMNC1_AMOUNT_0;
	
	private String RMNC1_ID_0;
	
	public void setBHRM1_AMOUNT_0(String bHRM1_AMOUNT_0) {
		BHRM1_AMOUNT_0 = bHRM1_AMOUNT_0;
	}
	public void setBHRM1_BHSX_ID_0(String bHRM1_BHSX_ID_0) {
		BHRM1_BHSX_ID_0 = bHRM1_BHSX_ID_0;
	}
	public void setBHRM1_BKA_CODE_0(String bHRM1_BKA_CODE_0) {
		BHRM1_BKA_CODE_0 = bHRM1_BKA_CODE_0;
	}
	public void setBHRM1_CASH_EFFDATE_0(String bHRM1_CASH_EFFDATE_0) {
		BHRM1_CASH_EFFDATE_0 = bHRM1_CASH_EFFDATE_0;
	}
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setRMNC1_AMOUNT_0(String rMNC1_AMOUNT_0) {
		RMNC1_AMOUNT_0 = rMNC1_AMOUNT_0;
	}
	public void setRMNC1_GA_ID_0(String rMNC1_GA_ID_0) {
		RMNC1_GA_ID_0 = rMNC1_GA_ID_0;
	}
	
	
	public void setRMNC1_ID_0(String rMNC1_ID_0) {
		RMNC1_ID_0 = rMNC1_ID_0;
	}
	public String getRMNC1_ID_0() {
		return RMNC1_ID_0;
	}
	
	public void setServiceParameters()
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setBHRM1_AMOUNT_0(Stock.GetParameterValue("BHRM1_AMOUNT_0"));
		this.setBHRM1_BHSX_ID_0(Stock.GetParameterValue("BHRM1_BHSX_ID_0"));
		this.setBHRM1_BKA_CODE_0(Stock.GetParameterValue("BHRM1_BKA_CODE_0"));
		this.setBHRM1_CASH_EFFDATE_0(Stock.GetParameterValue("BHRM1_CASH_EFFDATE_0"));
		this.setRMNC1_AMOUNT_0(Stock.GetParameterValue("RMNC1_AMOUNT_0"));
		this.setRMNC1_GA_ID_0(Stock.GetParameterValue("RMNC1_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&BHRM1_BKA_CODE_0="+BHRM1_BKA_CODE_0+"&BHRM1_AMOUNT_0="+BHRM1_AMOUNT_0+
				"&BHRM1_CASH_EFFDATE_0="+BHRM1_CASH_EFFDATE_0+"&BHRM1_BHSX_ID_0="+BHRM1_BHSX_ID_0+"&RMNC1_GA_ID_0="+RMNC1_GA_ID_0+
				"&RMNC1_AMOUNT_0="+RMNC1_AMOUNT_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		
		Reporter.logEvent(Status.INFO, "Prepare test data for BATR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run BATR_Cashdrop service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run BATR_Cashdrop service", "Running Failed:", false);
		}
	}
	
	
	
	public void validateOutput() throws SQLException
	{
		//BHRM1_EV_ID_0 = doc.getElementsByTagName("BHRM1_EV_ID_0").item(0).getTextContent();
		this.setRMNC1_ID_0(doc.getElementsByTagName("RMNC1_ID_0").item(0).getTextContent());
		Reporter.logEvent(Status.INFO, "Remittance Id", this.RMNC1_ID_0, false);
		Reporter.logEvent(Status.INFO, "Amount", this.RMNC1_AMOUNT_0, false);
		/*String amount = "";
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("validateBATRinBatchRemit")[1], BHRM1_EV_ID_0);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				amount = queryResultSet.getString("AMOUNT");
			}
		}
		
		if(amount.equalsIgnoreCase(RMNC1_AMOUNT_0))
		{
Reporter.logEvent(Status.PASS, "Validate the amount in database", 
"A record is created in the batch_remit table - the query returns 1 record, batch_remit.amount=input.amount and Amount is " + " "+amount,
false);
		}else{
			Reporter.logEvent(Status.FAIL, "Validate the amount in database", 
					"No record is created in the batch_remit table , batch_remit.amount != "
					+ "input.amount"
					+ "\nExpected : " + RMNC1_AMOUNT_0
					+ "\n Actual : "+ amount,
					false);
			
		}*/
	}
}
