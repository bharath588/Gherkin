package pageobject.GABD;

import java.net.URLDecoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lib.Reporter;
import lib.Stock;
import org.w3c.dom.Document;
import com.aventstack.extentreports.Status;
import core.framework.Globals;

public class GABD_Group_Forfeiture {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GABD_group_has_forfeiture";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_ID_0;
	
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
	public void setGET_GA_ID_0(String gET_GA_ID_0) {
		GET_GA_ID_0 = gET_GA_ID_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setGET_GA_ID_0(Stock.GetParameterValue("GET_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 +"&GET_GA_GA_ID_0="+GET_GA_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";	
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GABD service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run GABD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GABD service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","PABD_HDR0_TODAYS_DATE1_0: "+doc.getElementsByTagName("PABD_HDR1_DPDATE_TIME_0").item(0).getTextContent()+
					"\nPABD_HDR1_TOT_BALANCE_0: "+doc.getElementsByTagName("PABD_HDR1_TOT_BALANCE_0").item(0).getTextContent(),false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	/*public void validateInDatabase() throws SQLException{
		String code = doc.getElementsByTagName("FOOTNOTE_DETERMINANT_LEVEL_CODE_0").item(0).getTextContent();
		String code_db = null;
		String name = doc.getElementsByTagName("FOOTNOTE_DETERMINANT_NAME_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForFTDT")[1], name);
		
		if(DB.getRecordSetCount(queryResultSet)>0){	
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTableName: FOOTNOTE_DETERMINANT", false);
			while(queryResultSet.next()){	
				code_db = queryResultSet.getString("LEVEL_CODE");
				Reporter.logEvent(Status.INFO, "Values From DATABASE: ", "NAME: "+queryResultSet.getString("NAME")+
					"\nLEVEL_CODE: "+queryResultSet.getString("LEVEL_CODE")+
					"\nDESCR: "+queryResultSet.getString("DESCR"), false);
			}
			if(code.equalsIgnoreCase(code_db)){
				Reporter.logEvent(Status.PASS, "Comapring and validating Level Code form response and database ","Both the values are same as expected"+
						"\nFrom Response: "+code+"\nFrom Database: "+code_db, false);
			}else{
				Reporter.logEvent(Status.PASS, "Comapring and validating Level Code form response and database ","Both the values are not same"+
						"\nFrom Response: "+code+"\nFrom Database: "+code_db, false);
			}
		}	
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}*/
	
}
