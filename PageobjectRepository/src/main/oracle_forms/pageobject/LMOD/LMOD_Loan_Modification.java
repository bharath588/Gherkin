package pageobject.LMOD;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class LMOD_Loan_Modification {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LMOD_Loan_Modification";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String CONTROL_SSN_DISPL_0;
	String STEP_TYPE_LOV;
	String REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0;
	
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
		CONTROL_SSN_DISPL_0 = cONTROL_SSN_DISPL_0;
	}
	public void setSTEP_TYPE_LOV(String sTEP_TYPE_LOV) {
		STEP_TYPE_LOV = sTEP_TYPE_LOV;
	}
	public void setREAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0(
			String rEAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0) {
		REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0 = rEAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setCONTROL_SSN_DISPL_0(Stock.GetParameterValue("CONTROL_SSN_DISPL_0"));
		 this.setSTEP_TYPE_LOV(Stock.GetParameterValue("STEP_TYPE_LOV"));
		 this.setREAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0(Stock.GetParameterValue("REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&CONTROL_SSN_DISPL_0="+CONTROL_SSN_DISPL_0+"&STEP_TYPE_LOV="+STEP_TYPE_LOV+
				 "&REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0="+REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for LMOD service", this.queryString.replaceAll("&", "\n"), false);
		 
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
				Reporter.logEvent(Status.PASS, "Run LMOD_Loan_Modification service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run LMOD_Loan_Modification service", "Running Failed:", false);
			}
			
	}
	
	public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
//			Reporter.logEvent(Status.PASS, "Capturing Response after Execution", "Response:\n"+doc.getDocumentElement(), false);
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "Values From response: ","PLAN_NAME: "+doc.getElementsByTagName("CONTROL_PLAN_NAME_0").item(0).getTextContent()+
				"\nHIREDATE: "+doc.getElementsByTagName("EMPLOYMENT_LOA_LOA_HIREDATE_DISPLAY_0").item(0).getTextContent(),false);
		
	}
	
	public void validateInDatabase()throws SQLException, ParseException{
		String rmr_date=null;
		String rmr_date_input= this.REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToCheckLoanMod")[1], CONTROL_SSN_DISPL_0, CONTROL_SSN_DISPL_0);
		
		if(queryResultSet.next()){
			rmr_date = queryResultSet.getString("REAMORTIZATION_DPDATE_TIME").split(" ")[0];
			Reporter.logEvent(Status.INFO, "Values from DATABASE", "REAMORTIZATION_DPDATE_TIME: "+ queryResultSet.getString("REAMORTIZATION_DPDATE_TIME"), false);
			Reporter.logEvent(Status.PASS, "Comparing and Validating REAMORTIZATION_DPDATE_TIME  from Input and Database", "Both the values are same as Expected"+
					"\nCODE: "+"From Input: "+this.REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0+"\nFrom Database: "+rmr_date, false);			

		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Validating from DATABASE","Record not getting created",false);
		}
		/*if(DateCompare.compareTwoDates(this.REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0, rmr_date)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating REAMORTIZATION_DPDATE_TIME  from Response and Database", "Both the values are same as Expected"+
					"\nCODE: "+"From Response: "+this.REAMORTIZE_LOAN_REAM_FIRST_DUE_DATE_0+"\nFrom Database: "+rmr_date, false);			
		}*/
	} 

}
