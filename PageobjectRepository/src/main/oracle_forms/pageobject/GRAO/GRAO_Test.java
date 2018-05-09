package pageobject.GRAO;

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

public class GRAO_Test {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GRAO_Test";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String CONTROL_EFFDATE_0;
	String CONTROL_SDDETY_CODE_0; 
	String CONTROL_SDMT_CODE_0 ;
	String CONTROL_GDMT_SEQNBR_0; 
	String IOAO1_PERCENT_0 ;
	String IOAO1_PERCENT_0_X1; 
	String IOAO1_PERCENT_1 ;
	String IOAO1_PERCENT_1_X1; 
	String IOAO1_PERCENT_2 ;
	String IOAO1_PERCENT_2_X1; 
	String IOAO1_PERCENT_3 ;
	String IOAO1_PERCENT_3_X1; 
	String IOAO1_PERCENT_4 ;
	String IOAO1_PERCENT_4_X1; 
	String IOAO1_PERCENT_5 ;
	String IOAO1_PERCENT_5_X1; 
	
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setCONTROL_EFFDATE_0(String cONTROL_EFFDATE_0) {
		CONTROL_EFFDATE_0 = cONTROL_EFFDATE_0;
	}
	public void setCONTROL_SDDETY_CODE_0(String cONTROL_SDDETY_CODE_0) {
		CONTROL_SDDETY_CODE_0 = cONTROL_SDDETY_CODE_0;
	}
	public void setCONTROL_SDMT_CODE_0(String cONTROL_SDMT_CODE_0) {
		CONTROL_SDMT_CODE_0 = cONTROL_SDMT_CODE_0;
	}
	public void setCONTROL_GDMT_SEQNBR_0(String cONTROL_GDMT_SEQNBR_0) {
		CONTROL_GDMT_SEQNBR_0 = cONTROL_GDMT_SEQNBR_0;
	}
	public void setIOAO1_PERCENT_0(String iOAO1_PERCENT_0) {
		IOAO1_PERCENT_0 = iOAO1_PERCENT_0;
	}
	public void setIOAO1_PERCENT_0_X1(String iOAO1_PERCENT_0_X1) {
		IOAO1_PERCENT_0_X1 = iOAO1_PERCENT_0_X1;
	}
	public void setIOAO1_PERCENT_1(String iOAO1_PERCENT_1) {
		IOAO1_PERCENT_1 = iOAO1_PERCENT_1;
	}
	public void setIOAO1_PERCENT_1_X1(String iOAO1_PERCENT_1_X1) {
		IOAO1_PERCENT_1_X1 = iOAO1_PERCENT_1_X1;
	}
	public void setIOAO1_PERCENT_2(String iOAO1_PERCENT_2) {
		IOAO1_PERCENT_2 = iOAO1_PERCENT_2;
	}
	public void setIOAO1_PERCENT_2_X1(String iOAO1_PERCENT_2_X1) {
		IOAO1_PERCENT_2_X1 = iOAO1_PERCENT_2_X1;
	}
	public void setIOAO1_PERCENT_3(String iOAO1_PERCENT_3) {
		IOAO1_PERCENT_3 = iOAO1_PERCENT_3;
	}
	public void setIOAO1_PERCENT_3_X1(String iOAO1_PERCENT_3_X1) {
		IOAO1_PERCENT_3_X1 = iOAO1_PERCENT_3_X1;
	}
	public void setIOAO1_PERCENT_4(String iOAO1_PERCENT_4) {
		IOAO1_PERCENT_4 = iOAO1_PERCENT_4;
	}
	public void setIOAO1_PERCENT_4_X1(String iOAO1_PERCENT_4_X1) {
		IOAO1_PERCENT_4_X1 = iOAO1_PERCENT_4_X1;
	}
	public void setIOAO1_PERCENT_5(String iOAO1_PERCENT_5) {
		IOAO1_PERCENT_5 = iOAO1_PERCENT_5;
	}
	public void setIOAO1_PERCENT_5_X1(String iOAO1_PERCENT_5_X1) {
		IOAO1_PERCENT_5_X1 = iOAO1_PERCENT_5_X1;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setCONTROL_EFFDATE_0(Stock.GetParameterValue("CONTROL_EFFDATE_0"));
		 this.setCONTROL_GDMT_SEQNBR_0(Stock.GetParameterValue("CONTROL_GDMT_SEQNBR_0"));
		 this.setCONTROL_SDDETY_CODE_0(Stock.GetParameterValue("CONTROL_SDDETY_CODE_0"));
		 this.setCONTROL_SDMT_CODE_0(Stock.GetParameterValue("CONTROL_SDMT_CODE_0"));
		 this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		 this.setIOAO1_PERCENT_0(Stock.GetParameterValue("IOAO1_PERCENT_0"));
		 this.setIOAO1_PERCENT_1(Stock.GetParameterValue("IOAO1_PERCENT_1"));
		 this.setIOAO1_PERCENT_2(Stock.GetParameterValue("IOAO1_PERCENT_2"));
		 this.setIOAO1_PERCENT_3(Stock.GetParameterValue("IOAO1_PERCENT_3"));
		 this.setIOAO1_PERCENT_4(Stock.GetParameterValue("IOAO1_PERCENT_4"));
		 this.setIOAO1_PERCENT_5(Stock.GetParameterValue("IOAO1_PERCENT_5"));
		 this.setIOAO1_PERCENT_5_X1(Stock.GetParameterValue("IOAO1_PERCENT_5_X1"));
		 this.setIOAO1_PERCENT_4_X1(Stock.GetParameterValue("IOAO1_PERCENT_4_X1"));
		 this.setIOAO1_PERCENT_3_X1(Stock.GetParameterValue("IOAO1_PERCENT_3_X1"));
		 this.setIOAO1_PERCENT_2_X1(Stock.GetParameterValue("IOAO1_PERCENT_2_X1"));
		 this.setIOAO1_PERCENT_1_X1(Stock.GetParameterValue("IOAO1_PERCENT_1_X1"));
		 this.setIOAO1_PERCENT_0_X1(Stock.GetParameterValue("IOAO1_PERCENT_0_X1"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				 "&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&CONTROL_EFFDATE_0="+CONTROL_EFFDATE_0+"&CONTROL_SDDETY_CODE_0="+CONTROL_SDDETY_CODE_0+"&CONTROL_SDMT_CODE_0="+CONTROL_SDMT_CODE_0+
				 "&CONTROL_GDMT_SEQNBR_0="+CONTROL_GDMT_SEQNBR_0+"&IOAO1_PERCENT_0="+IOAO1_PERCENT_0+"&IOAO1_PERCENT_0_X1="+IOAO1_PERCENT_0_X1+"&IOAO1_PERCENT_0_X1="+IOAO1_PERCENT_0_X1+
				 "&IOAO1_PERCENT_1_X1="+IOAO1_PERCENT_1_X1+"&IOAO1_PERCENT_2="+IOAO1_PERCENT_2+"&IOAO1_PERCENT_2_X1="+IOAO1_PERCENT_2_X1+"&IOAO1_PERCENT_3="+IOAO1_PERCENT_3+
				 "&IOAO1_PERCENT_3_X1="+IOAO1_PERCENT_3_X1+"&IOAO1_PERCENT_4="+IOAO1_PERCENT_4+"&IOAO1_PERCENT_4_X1="+IOAO1_PERCENT_4_X1+"&IOAO1_PERCENT_5="+IOAO1_PERCENT_5+
				 "IOAO1_PERCENT_5_X1="+IOAO1_PERCENT_5_X1+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
	Reporter.logEvent(Status.INFO, "Preparing Input Data: ", "CONTROL_SELECTION_0: "+CONTROL_SELECTION_0+
			"\nLOGON_DATABASE: "+LOGON_DATABASE+"\nGET_GA_GA_ID_0: "+GET_GA_GA_ID_0+
			"\nCONTROL_GDMT_SEQNBR_0: "+CONTROL_GDMT_SEQNBR_0+"\nCONTROL_SDDETY_CODE_0: "+CONTROL_SDDETY_CODE_0+
			"\nCONTROL_SDMT_CODE_0: "+CONTROL_SDMT_CODE_0, false);
	
	}
	
	public void runService() throws SQLException {
		try {
				this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
				serviceURL += this.queryString;
				docBuilderFactory = DocumentBuilderFactory.newInstance();
				docBuilderFactory.setIgnoringComments(true);
				docBuilderFactory.setIgnoringElementContentWhitespace(true);
				docBuilderFactory.setNamespaceAware(true);
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(serviceURL);
		//		System.out.println(serviceURL);
				doc.getDocumentElement().normalize();
				Reporter.logEvent(Status.PASS, "Run  GRAO_TEST service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run GRAO_TEST service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "Values From RESPONSE: ","CONTROL_GDMT_DESCR_0: "+doc.getElementsByTagName("CONTROL_GDMT_DESCR_0").item(0).getTextContent()+
				"\nCONTROL_SDDETY_DESCR_0: "+doc.getElementsByTagName("CONTROL_SDDETY_DESCR_0").item(0).getTextContent()+
				"\nGA_ID: "+doc.getElementsByTagName("GROUP_HEADER1_GA_ID_0").item(0).getTextContent()+
				"\nGC_ID: "+doc.getElementsByTagName("GROUP_HEADER1_GC_ID_0").item(0).getTextContent()+
				"\nPROD_ID: "+doc.getElementsByTagName("GROUP_HEADER1_PROD_ID_0").item(0).getTextContent()+
				"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToChangeAllocPercent")[1], GET_GA_GA_ID_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating from Database", "Record has been created into Database", false);
			Reporter.logEvent(Status.INFO, "Values From DATABASE: \nTable Name: INVOPT_ALLOC", "Total Number of Records: "
					+DB.getRecordSetCount(queryResultSet), false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		

		
	} 


}
