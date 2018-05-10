package pageobject.PROP;

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

public class PROP_EXEC_Setup_Flow {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PROP_EXER_EASY_SETUP_form_flow";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0; 	
	String CONTROL_SELECTION_0_X1; 	
	String CONTROL_SELECTION_0_X2; 	
	String CONTROL_SELECTION_0_X3; 
	String INP1_CATEGORY_0; 	 	
	String INP1_DATA_TYPE_CODE_0; 	
	String INP1_DFT_VALUE_0; 
	String INP1_MAND_IND_0; 
	String INP1_PARM_NAME_0;
	String INP1_PROMPT_0; 	
	String INP1_SEQNBR_0; 		
	String INP1_USER_CHANGEABLE_IND_0;
	String LOGON_DATABASE; 	
	String LOGON_PASSWORD;
	String LOGON_USERNAME; 
	String TRANSACTION_CODE_0; 	
	String TXN1_CHOICE_TYPE_0; 	 	
	String TXN1_CODE_0; 
	String TXN1_CODE_0_X1;
	String TXN1_DESCR_0; 
	String TXN1_FORM_VERSION_0; 	
	String TXN1_MENU_CHOICE_SEQNBR_0;
	String TXN1_MENU_CODE_0; 	
	String TXN1_MOD_SHORT_NAME_0; 	
	String TXN1_MOD_SHORT_NAME_0_X1; 	
	String TXN1_REPT_MOD_SHORT_NAME_0; 
	String USAULM1_UPDATE_IND_0;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public void setCONTROL_SELECTION_0_X1(String cONTROL_SELECTION_0_X1) {
		CONTROL_SELECTION_0_X1 = cONTROL_SELECTION_0_X1;
	}

	public void setCONTROL_SELECTION_0_X2(String cONTROL_SELECTION_0_X2) {
		CONTROL_SELECTION_0_X2 = cONTROL_SELECTION_0_X2;
	}

	public void setCONTROL_SELECTION_0_X3(String cONTROL_SELECTION_0_X3) {
		CONTROL_SELECTION_0_X3 = cONTROL_SELECTION_0_X3;
	}

	public void setINP1_CATEGORY_0(String iNP1_CATEGORY_0) {
		INP1_CATEGORY_0 = iNP1_CATEGORY_0;
	}

	public void setINP1_DATA_TYPE_CODE_0(String iNP1_DATA_TYPE_CODE_0) {
		INP1_DATA_TYPE_CODE_0 = iNP1_DATA_TYPE_CODE_0;
	}

	public void setINP1_DFT_VALUE_0(String iNP1_DFT_VALUE_0) {
		INP1_DFT_VALUE_0 = iNP1_DFT_VALUE_0;
	}

	public void setINP1_MAND_IND_0(String iNP1_MAND_IND_0) {
		INP1_MAND_IND_0 = iNP1_MAND_IND_0;
	}

	public void setINP1_PARM_NAME_0(String iNP1_PARM_NAME_0) {
		INP1_PARM_NAME_0 = iNP1_PARM_NAME_0;
	}

	public void setINP1_PROMPT_0(String iNP1_PROMPT_0) {
		INP1_PROMPT_0 = iNP1_PROMPT_0;
	}

	public void setINP1_SEQNBR_0(String iNP1_SEQNBR_0) {
		INP1_SEQNBR_0 = iNP1_SEQNBR_0;
	}

	public void setINP1_USER_CHANGEABLE_IND_0(String iNP1_USER_CHANGEABLE_IND_0) {
		INP1_USER_CHANGEABLE_IND_0 = iNP1_USER_CHANGEABLE_IND_0;
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

	public void setTRANSACTION_CODE_0(String tRANSACTION_CODE_0) {
		TRANSACTION_CODE_0 = tRANSACTION_CODE_0;
	}

	public void setTXN1_CHOICE_TYPE_0(String tXN1_CHOICE_TYPE_0) {
		TXN1_CHOICE_TYPE_0 = tXN1_CHOICE_TYPE_0;
	}

	public void setTXN1_CODE_0(String tXN1_CODE_0) {
		TXN1_CODE_0 = tXN1_CODE_0;
	}

	public void setTXN1_CODE_0_X1(String tXN1_CODE_0_X1) {
		TXN1_CODE_0_X1 = tXN1_CODE_0_X1;
	}

	public void setTXN1_DESCR_0(String tXN1_DESCR_0) {
		TXN1_DESCR_0 = tXN1_DESCR_0;
	}

	public void setTXN1_FORM_VERSION_0(String tXN1_FORM_VERSION_0) {
		TXN1_FORM_VERSION_0 = tXN1_FORM_VERSION_0;
	}

	public void setTXN1_MENU_CHOICE_SEQNBR_0(String tXN1_MENU_CHOICE_SEQNBR_0) {
		TXN1_MENU_CHOICE_SEQNBR_0 = tXN1_MENU_CHOICE_SEQNBR_0;
	}

	public void setTXN1_MENU_CODE_0(String tXN1_MENU_CODE_0) {
		TXN1_MENU_CODE_0 = tXN1_MENU_CODE_0;
	}

	public void setTXN1_MOD_SHORT_NAME_0(String tXN1_MOD_SHORT_NAME_0) {
		TXN1_MOD_SHORT_NAME_0 = tXN1_MOD_SHORT_NAME_0;
	}

	public void setTXN1_MOD_SHORT_NAME_0_X1(String tXN1_MOD_SHORT_NAME_0_X1) {
		TXN1_MOD_SHORT_NAME_0_X1 = tXN1_MOD_SHORT_NAME_0_X1;
	}

	public void setTXN1_REPT_MOD_SHORT_NAME_0(String tXN1_REPT_MOD_SHORT_NAME_0) {
		TXN1_REPT_MOD_SHORT_NAME_0 = tXN1_REPT_MOD_SHORT_NAME_0;
	}

	public void setUSAULM1_UPDATE_IND_0(String uSAULM1_UPDATE_IND_0) {
		USAULM1_UPDATE_IND_0 = uSAULM1_UPDATE_IND_0;
	}

	 String CODES_MARKER_0;
	 String USAULM1_USCS_ID_0; 
	
	
	public String getCODES_MARKER_0() {
		return CODES_MARKER_0;
	}

	public String getUSAULM1_USCS_ID_0() {
		return USAULM1_USCS_ID_0;
	}

	public void setServiceParameters()
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setCONTROL_SELECTION_0_X1(Stock.GetParameterValue("CONTROL_SELECTION_0_X1"));
		 this.setCONTROL_SELECTION_0_X2(Stock.GetParameterValue("CONTROL_SELECTION_0_X2"));
		 this.setCONTROL_SELECTION_0_X3(Stock.GetParameterValue("CONTROL_SELECTION_0_X3"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setINP1_CATEGORY_0(Stock.GetParameterValue("INP1_CATEGORY_0"));
		 this.setINP1_DATA_TYPE_CODE_0(Stock.GetParameterValue("INP1_DATA_TYPE_CODE_0"));
		 this.setINP1_DFT_VALUE_0(Stock.GetParameterValue("INP1_DFT_VALUE_0"));
		 this.setINP1_MAND_IND_0(Stock.GetParameterValue("INP1_MAND_IND_0"));
		 this.setINP1_PARM_NAME_0(Stock.GetParameterValue("INP1_PARM_NAME_0"));
		 this.setINP1_PROMPT_0(Stock.GetParameterValue("INP1_PROMPT_0"));
		 this.setINP1_SEQNBR_0(Stock.GetParameterValue("INP1_SEQNBR_0"));
		 this.setINP1_USER_CHANGEABLE_IND_0(Stock.GetParameterValue("INP1_USER_CHANGEABLE_IND_0"));
		 this.setTRANSACTION_CODE_0(Stock.GetParameterValue("TRANSACTION_CODE_0"));
		 this.setTXN1_CHOICE_TYPE_0(Stock.GetParameterValue("TXN1_CHOICE_TYPE_0"));
		 this.setTXN1_CODE_0_X1(Stock.GetParameterValue("TXN1_CODE_0_X1"));
		 this.setTXN1_DESCR_0(Stock.GetParameterValue("TXN1_DESCR_0"));
		 this.setTXN1_FORM_VERSION_0(Stock.GetParameterValue("TXN1_FORM_VERSION_0"));
		 this.setTXN1_MENU_CHOICE_SEQNBR_0(Stock.GetParameterValue("TXN1_MENU_CHOICE_SEQNBR_0"));
		 this.setTXN1_MENU_CODE_0(Stock.GetParameterValue("TXN1_MENU_CODE_0"));
		 this.setTXN1_MOD_SHORT_NAME_0(Stock.GetParameterValue("TXN1_MOD_SHORT_NAME_0"));
		 this.setTXN1_MOD_SHORT_NAME_0_X1(Stock.GetParameterValue("TXN1_MOD_SHORT_NAME_0_X1"));
		 this.setTXN1_REPT_MOD_SHORT_NAME_0(Stock.GetParameterValue("TXN1_REPT_MOD_SHORT_NAME_0"));
		
	
		 queryString ="?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+ 
				 "&CONTROL_SELECTION_0_X1="+CONTROL_SELECTION_0_X1+
				 "&CONTROL_SELECTION_0_X2="+CONTROL_SELECTION_0_X2+
				 "&CONTROL_SELECTION_0_X3="+CONTROL_SELECTION_0_X3+
				 "&INP1_CATEGORY_0="+INP1_CATEGORY_0+
				 "&INP1_DATA_TYPE_CODE_0="+INP1_DATA_TYPE_CODE_0+
				 "&INP1_DFT_VALUE_0="+INP1_DFT_VALUE_0+
				 "&INP1_MAND_IND_0="+INP1_MAND_IND_0+
				 "&INP1_PARM_NAME_0="+INP1_PARM_NAME_0+
				 "&INP1_PROMPT_0="+INP1_PROMPT_0+
				 "&INP1_SEQNBR_0="+INP1_SEQNBR_0+
				 "&INP1_USER_CHANGEABLE_IND_0="+INP1_USER_CHANGEABLE_IND_0+
				 "&LOGON_DATABASE="+LOGON_DATABASE+
				 "&LOGON_PASSWORD="+LOGON_PASSWORD+ 
				 "&LOGON_USERNAME="+LOGON_USERNAME+
				 "&TRANSACTION_CODE_0="+TRANSACTION_CODE_0+
				 "&TXN1_CHOICE_TYPE_0="+TXN1_CHOICE_TYPE_0+
				 "&TXN1_CODE_0="+TXN1_CODE_0+
				 "&TXN1_CODE_0_X1="+TXN1_CODE_0_X1+
				 "&TXN1_DESCR_0="+TXN1_DESCR_0+
				 "&TXN1_FORM_VERSION_0="+TXN1_FORM_VERSION_0+
				 "&TXN1_MENU_CHOICE_SEQNBR_0="+TXN1_MENU_CHOICE_SEQNBR_0+				 
				 "&TXN1_MENU_CODE_0="+TXN1_MENU_CODE_0+
				 "&TXN1_MOD_SHORT_NAME_0="+TXN1_MOD_SHORT_NAME_0+
				 "&TXN1_MOD_SHORT_NAME_0_X1="+TXN1_MOD_SHORT_NAME_0_X1+
				 "&TXN1_REPT_MOD_SHORT_NAME_0="+TXN1_REPT_MOD_SHORT_NAME_0+
				 "&USAULM1_UPDATE_IND_0="+USAULM1_UPDATE_IND_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		 
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToRemoveTran")[1], TXN1_CODE_0);
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToRemoveUcau")[1], TXN1_MOD_SHORT_NAME_0);
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToRemoveMinp")[1], TXN1_CODE_0_X1);
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToRemoveLuma")[1], TXN1_CODE_0_X1);
				 
		 Reporter.logEvent(Status.INFO, "Prepare test data for PROP service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run PROP EXEC Setup Flow service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PROP EXEC Setup Flow service", "Running Failed:", false);
		}
	}
		
	
	public void validateOutput() throws SQLException, Exception{

		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "Values from response", "USAULM1_USCS_ID_0: "+doc.getElementsByTagName("USAULM1_USCS_ID_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryTran")[1], this.TXN1_CODE_0_X1);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking whether records are inserted into Database", "Records are inserted into DB\nTablename: TRANSACTION", false);
			while(queryResultSet.next()){			
			Reporter.logEvent(Status.INFO, "Validating TRAN", "CODE: "+queryResultSet.getString("CODE") +
					"DESCR: "+queryResultSet.getString("DESCR")+
					"MOD SHORT NAME: "+queryResultSet.getString("MOD_SHORT_NAME"), false);
			}			
		}
		
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryUcau")[1], this.TXN1_CODE_0_X1);
		 if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Checking whether records are inserted into Database", "Records are inserted into DB\nTablename: USER_TXN_AUTH", false);
				while(queryResultSet.next()){	
				Reporter.logEvent(Status.INFO, "Validating UCAU", "TXN_CODE: "+queryResultSet.getString("TXN_CODE") +
						"\nUSCS_ID: "+queryResultSet.getString("USCS_ID")+
						"\nMAX_AMT: "+queryResultSet.getString("MAX_AMT"), false);
				}
		}
		 
		 queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryMinp")[1], this.TXN1_CODE_0_X1);
		 if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Checking whether records are inserted into Database", "Records are inserted into DB\nTablename: INPUT_PARM", false);
				while(queryResultSet.next()){					
				Reporter.logEvent(Status.INFO, "Validating MINP", "TXN_CODE: "+queryResultSet.getString("TXN_CODE") +
						"\nSEQNBR: "+queryResultSet.getString("SEQNBR")+
						"\nMAND ID: "+queryResultSet.getString("MAND_IND"), false);
				}				
		}
		 
		/* queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryLuma")[1], TXN1_CODE_0_X1);
		 if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Checking whether records are inserted into Database", "Records are inserted into DB\nTablename: USER_MENU_ACCESS", false);
				while(queryResultSet.next()){	
				Reporter.logEvent(Status.INFO, "Validating LUMA", "CODE: "+queryResultSet.getString("CODE") +
						"\nDESCR: "+queryResultSet.getString("DESCR"), false);
				
			}
		 }*/
	}
	
	
}
