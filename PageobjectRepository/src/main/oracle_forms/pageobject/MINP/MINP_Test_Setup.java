package pageobject.MINP;

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

public class MINP_Test_Setup {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MINP_Test_Setup";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
//	private ResultSet queryResultSet2;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String TXN1_CODE_0 ;
	String INP1_SEQNBR_0 ;
	String INP1_PARM_NAME_0; 
	String INP1_PROMPT_0 ;
	String INP1_DATA_TYPE_CODE_0; 
	String INP1_MAND_IND_0 ;
	String INP1_USER_CHANGEABLE_IND_0; 
	String INP1_SEQNBR_1 ;
	String INP1_PARM_NAME_1; 
	String INP1_PROMPT_1 ;
	String INP1_DATA_TYPE_CODE_1; 
	String INP1_MAND_IND_1 ;
	String INP1_USER_CHANGEABLE_IND_1; 
	String INP1_SEQNBR_2 ;
	String INP1_PARM_NAME_2; 
	String INP1_PROMPT_2 ;
	String INP1_PARM_NAME_2_X1; 
	String INP1_PROMPT_2_X1 ;
	String INP1_DATA_TYPE_CODE_2; 
	String INP1_MAND_IND_2 ;
	String INP1_USER_CHANGEABLE_IND_2; 
	String INP1_SEQNBR_3 ;
	String INP1_PARM_NAME_3; 
	String INP1_PROMPT_3 ;
	String INP1_DATA_TYPE_CODE_3;  
	String INP1_MAND_IND_3 ;
	String INP1_USER_CHANGEABLE_IND_3; 
	String INP1_SEQNBR_4 ;
	String INP1_PARM_NAME_4; 
	String INP1_PROMPT_4 ;
	String INP1_DATA_TYPE_CODE_4; 
	String INP1_MAND_IND_4 ;
	String INP1_USER_CHANGEABLE_IND_4; 
	String INP1_DFT_VALUE_4;
	
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
	public void setTXN1_CODE_0(String tXN1_CODE_0) {
		TXN1_CODE_0 = tXN1_CODE_0;
	}
	public void setINP1_SEQNBR_0(String iNP1_SEQNBR_0) {
		INP1_SEQNBR_0 = iNP1_SEQNBR_0;
	}
	public void setINP1_PARM_NAME_0(String iNP1_PARM_NAME_0) {
		INP1_PARM_NAME_0 = iNP1_PARM_NAME_0;
	}
	public void setINP1_PROMPT_0(String iNP1_PROMPT_0) {
		INP1_PROMPT_0 = iNP1_PROMPT_0;
	}
	public void setINP1_DATA_TYPE_CODE_0(String iNP1_DATA_TYPE_CODE_0) {
		INP1_DATA_TYPE_CODE_0 = iNP1_DATA_TYPE_CODE_0;
	}
	public void setINP1_MAND_IND_0(String iNP1_MAND_IND_0) {
		INP1_MAND_IND_0 = iNP1_MAND_IND_0;
	}
	public void setINP1_USER_CHANGEABLE_IND_0(String iNP1_USER_CHANGEABLE_IND_0) {
		INP1_USER_CHANGEABLE_IND_0 = iNP1_USER_CHANGEABLE_IND_0;
	}
	public void setINP1_SEQNBR_1(String iNP1_SEQNBR_1) {
		INP1_SEQNBR_1 = iNP1_SEQNBR_1;
	}
	public void setINP1_PARM_NAME_1(String iNP1_PARM_NAME_1) {
		INP1_PARM_NAME_1 = iNP1_PARM_NAME_1;
	}
	public void setINP1_PROMPT_1(String iNP1_PROMPT_1) {
		INP1_PROMPT_1 = iNP1_PROMPT_1;
	}
	public void setINP1_DATA_TYPE_CODE_1(String iNP1_DATA_TYPE_CODE_1) {
		INP1_DATA_TYPE_CODE_1 = iNP1_DATA_TYPE_CODE_1;
	}
	public void setINP1_MAND_IND_1(String iNP1_MAND_IND_1) {
		INP1_MAND_IND_1 = iNP1_MAND_IND_1;
	}
	public void setINP1_USER_CHANGEABLE_IND_1(String iNP1_USER_CHANGEABLE_IND_1) {
		INP1_USER_CHANGEABLE_IND_1 = iNP1_USER_CHANGEABLE_IND_1;
	}
	public void setINP1_SEQNBR_2(String iNP1_SEQNBR_2) {
		INP1_SEQNBR_2 = iNP1_SEQNBR_2;
	}
	public void setINP1_PARM_NAME_2(String iNP1_PARM_NAME_2) {
		INP1_PARM_NAME_2 = iNP1_PARM_NAME_2;
	}
	public void setINP1_PROMPT_2(String iNP1_PROMPT_2) {
		INP1_PROMPT_2 = iNP1_PROMPT_2;
	}
	public void setINP1_PARM_NAME_2_X1(String iNP1_PARM_NAME_2_X1) {
		INP1_PARM_NAME_2_X1 = iNP1_PARM_NAME_2_X1;
	}
	public void setINP1_PROMPT_2_X1(String iNP1_PROMPT_2_X1) {
		INP1_PROMPT_2_X1 = iNP1_PROMPT_2_X1;
	}
	public void setINP1_DATA_TYPE_CODE_2(String iNP1_DATA_TYPE_CODE_2) {
		INP1_DATA_TYPE_CODE_2 = iNP1_DATA_TYPE_CODE_2;
	}
	public void setINP1_MAND_IND_2(String iNP1_MAND_IND_2) {
		INP1_MAND_IND_2 = iNP1_MAND_IND_2;
	}
	public void setINP1_USER_CHANGEABLE_IND_2(String iNP1_USER_CHANGEABLE_IND_2) {
		INP1_USER_CHANGEABLE_IND_2 = iNP1_USER_CHANGEABLE_IND_2;
	}
	public void setINP1_SEQNBR_3(String iNP1_SEQNBR_3) {
		INP1_SEQNBR_3 = iNP1_SEQNBR_3;
	}
	public void setINP1_PARM_NAME_3(String iNP1_PARM_NAME_3) {
		INP1_PARM_NAME_3 = iNP1_PARM_NAME_3;
	}
	public void setINP1_PROMPT_3(String iNP1_PROMPT_3) {
		INP1_PROMPT_3 = iNP1_PROMPT_3;
	}
	public void setINP1_DATA_TYPE_CODE_3(String iNP1_DATA_TYPE_CODE_3) {
		INP1_DATA_TYPE_CODE_3 = iNP1_DATA_TYPE_CODE_3;
	}
	public void setINP1_MAND_IND_3(String iNP1_MAND_IND_3) {
		INP1_MAND_IND_3 = iNP1_MAND_IND_3;
	}
	public void setINP1_USER_CHANGEABLE_IND_3(String iNP1_USER_CHANGEABLE_IND_3) {
		INP1_USER_CHANGEABLE_IND_3 = iNP1_USER_CHANGEABLE_IND_3;
	}
	public void setINP1_SEQNBR_4(String iNP1_SEQNBR_4) {
		INP1_SEQNBR_4 = iNP1_SEQNBR_4;
	}
	public void setINP1_PARM_NAME_4(String iNP1_PARM_NAME_4) {
		INP1_PARM_NAME_4 = iNP1_PARM_NAME_4;
	}
	public void setINP1_PROMPT_4(String iNP1_PROMPT_4) {
		INP1_PROMPT_4 = iNP1_PROMPT_4;
	}
	public void setINP1_DATA_TYPE_CODE_4(String iNP1_DATA_TYPE_CODE_4) {
		INP1_DATA_TYPE_CODE_4 = iNP1_DATA_TYPE_CODE_4;
	}
	public void setINP1_MAND_IND_4(String iNP1_MAND_IND_4) {
		INP1_MAND_IND_4 = iNP1_MAND_IND_4;
	}
	public void setINP1_USER_CHANGEABLE_IND_4(String iNP1_USER_CHANGEABLE_IND_4) {
		INP1_USER_CHANGEABLE_IND_4 = iNP1_USER_CHANGEABLE_IND_4;
	}
	public void setINP1_DFT_VALUE_4(String iNP1_DFT_VALUE_4) {
		INP1_DFT_VALUE_4 = iNP1_DFT_VALUE_4;
	} 
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setINP1_DATA_TYPE_CODE_0(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_0"));
		 this.setINP1_DATA_TYPE_CODE_1(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_1"));
		 this.setINP1_DATA_TYPE_CODE_2(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_2"));
		 this.setINP1_DATA_TYPE_CODE_3(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_3"));
		 this.setINP1_DATA_TYPE_CODE_4(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_4"));
		 this.setINP1_MAND_IND_0(Stock.GetParameterValue("INP1_MAND_IND_0"));
		 this.setINP1_MAND_IND_1(Stock.GetParameterValue("INP1_MAND_IND_1"));
		 this.setINP1_MAND_IND_2(Stock.GetParameterValue("INP1_MAND_IND_2"));
		 this.setINP1_MAND_IND_3(Stock.GetParameterValue("INP1_MAND_IND_3"));
		 this.setINP1_MAND_IND_4(Stock.GetParameterValue("INP1_MAND_IND_4"));
		 this.setINP1_PARM_NAME_0(Stock.GetParameterValue("INP1_PARM_NAME_0"));
		 this.setINP1_PARM_NAME_1(Stock.GetParameterValue("INP1_PARM_NAME_1"));
		 this.setINP1_PARM_NAME_2(Stock.GetParameterValue("INP1_PARM_NAME_2"));
		 this.setINP1_PARM_NAME_3(Stock.GetParameterValue("INP1_PARM_NAME_3"));
		 this.setINP1_PARM_NAME_4(Stock.GetParameterValue("INP1_PARM_NAME_4"));
		 this.setINP1_PROMPT_0(Stock.GetParameterValue("INP1_PROMPT_0"));
		 this.setINP1_PROMPT_1(Stock.GetParameterValue("INP1_PROMPT_1"));
		 this.setINP1_PROMPT_2(Stock.GetParameterValue("INP1_PROMPT_2"));
		 this.setINP1_PROMPT_3(Stock.GetParameterValue("INP1_PROMPT_3"));
		 this.setINP1_PROMPT_4(Stock.GetParameterValue("INP1_PROMPT_4"));
		 this.setINP1_SEQNBR_0(Stock.GetParameterValue("INP1_SEQNBR_0"));
		 this.setINP1_SEQNBR_1(Stock.GetParameterValue("INP1_SEQNBR_1"));
		 this.setINP1_SEQNBR_2(Stock.GetParameterValue("INP1_SEQNBR_2"));
		 this.setINP1_SEQNBR_3(Stock.GetParameterValue("INP1_SEQNBR_3"));
		 this.setINP1_SEQNBR_4(Stock.GetParameterValue("INP1_SEQNBR_4"));
		 this.setINP1_USER_CHANGEABLE_IND_4(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_4"));
		 this.setINP1_USER_CHANGEABLE_IND_3(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_3"));
		 this.setINP1_USER_CHANGEABLE_IND_2(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_2"));
		 this.setINP1_USER_CHANGEABLE_IND_1(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_1"));
		 this.setINP1_USER_CHANGEABLE_IND_0(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_0"));
		 this.setTXN1_CODE_0(Stock.GetParameterValue("TXN1_CODE_0"));
		 this.setINP1_PROMPT_2_X1(Stock.GetParameterValue("INP1_PROMPT_2_X1"));
		 this.setINP1_PARM_NAME_2_X1(Stock.GetParameterValue("INP1_PARM_NAME_2_X1"));
		 this.setINP1_DFT_VALUE_4(Stock.GetParameterValue("INP1_DFT_VALUE_4"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&TXN1_CODE_0="+TXN1_CODE_0+"&INP1_SEQNBR_0="+INP1_SEQNBR_0+
				 "&INP1_PARM_NAME_0="+INP1_PARM_NAME_0+"&INP1_PROMPT_0="+INP1_PROMPT_0+"&INP1_DATA_TYPE_CODE_0="+INP1_DATA_TYPE_CODE_0+
				 "&INP1_MAND_IND_0="+INP1_MAND_IND_0+"&INP1_USER_CHANGEABLE_IND_0="+INP1_USER_CHANGEABLE_IND_0+
				 "&INP1_SEQNBR_1="+INP1_SEQNBR_1+"&INP1_PARM_NAME_1="+INP1_PARM_NAME_1+"&INP1_PROMPT_1="+INP1_PROMPT_1+
				 "&INP1_DATA_TYPE_CODE_1="+INP1_DATA_TYPE_CODE_1+"&INP1_MAND_IND_1="+INP1_MAND_IND_1+"&INP1_USER_CHANGEABLE_IND_1="+INP1_USER_CHANGEABLE_IND_1+
				 "&INP1_SEQNBR_2="+INP1_SEQNBR_2+"&INP1_PARM_NAME_2="+INP1_PARM_NAME_2+"&INP1_PROMPT_2="+INP1_PROMPT_2+
				 "&INP1_PARM_NAME_2_X1="+INP1_PARM_NAME_2_X1+"&INP1_PROMPT_2_X1="+INP1_PROMPT_2_X1+"&INP1_DATA_TYPE_CODE_2="+INP1_DATA_TYPE_CODE_2+
				 "&INP1_MAND_IND_2="+INP1_MAND_IND_2+"&INP1_USER_CHANGEABLE_IND_2="+INP1_USER_CHANGEABLE_IND_2+"&INP1_SEQNBR_3="+INP1_SEQNBR_3+
				 "&INP1_PARM_NAME_3="+INP1_PARM_NAME_3+"&INP1_PROMPT_3="+INP1_PROMPT_3+"&INP1_DATA_TYPE_CODE_3="+INP1_DATA_TYPE_CODE_3+
				 "&INP1_MAND_IND_3="+INP1_MAND_IND_3+"&INP1_USER_CHANGEABLE_IND_3="+INP1_USER_CHANGEABLE_IND_3+"&INP1_SEQNBR_4="+INP1_SEQNBR_4+
				 "&INP1_PARM_NAME_4="+INP1_PARM_NAME_4+"&INP1_PROMPT_4="+INP1_PROMPT_4+"&INP1_DATA_TYPE_CODE_4="+INP1_DATA_TYPE_CODE_4+
				 "&INP1_MAND_IND_4="+INP1_MAND_IND_4+"&INP1_USER_CHANGEABLE_IND_4="+INP1_USER_CHANGEABLE_IND_4+"&INP1_DFT_VALUE_4="+INP1_DFT_VALUE_4+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for MINP TEST SETUP service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run / MINP_Test_Setup service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run / MINP_Test_Setup service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "Values From response: ","TXN1_DESCR_0: "+doc.getElementsByTagName("TXN1_DESCR_0").item(0).getTextContent()+
		"\nTXN1_TRANSACTION_0: "+doc.getElementsByTagName("TXN1_TRANSACTION_0").item(0).getTextContent()+
		"\n",false);
	}

	public void validateInDatabase()throws SQLException{
		String TXN1_TRANSACTION_0 = doc.getElementsByTagName("TXN1_TRANSACTION_0").item(0).getTextContent();
		String txn_code = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryMinp")[1], this.TXN1_CODE_0);
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: EVENT", false);
			txn_code = queryResultSet.getString("TXN_CODE");
			Reporter.logEvent(Status.INFO, "Values From DAtabase: \nTABLE NAME: INPUT_PARM", "TXN_CODE: "+queryResultSet.getString("TXN_CODE")+
				"\nSEQ NBR: "+queryResultSet.getString("SEQNBR")+
				"\nMAND IND: "+queryResultSet.getString("MAND_IND")+
				"\nPARM NAME: "+queryResultSet.getString("PARM_NAME")+"\n", false); 
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(TXN1_TRANSACTION_0.equalsIgnoreCase(txn_code)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Tax Transaction from Response and Database", "Both the values are same as Expected"+
					"\nTax Transaction: "+"From Response: "+TXN1_TRANSACTION_0+"\nFrom Database: "+txn_code, false);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Comparing and Validating Tax Transaction from Response and Database", "Both the values are same as Expected"+
					"\nTax Transaction: "+"From Response: "+TXN1_TRANSACTION_0+"\nFrom Database: "+txn_code, false);
		
		}
	}

	
}
