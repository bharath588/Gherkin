package pageobject.IPRR;

import generallib.DateCompare;

import java.io.File;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class IPRR_PlanLevel_RateofReturn {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/IPRR_Plan_Level_Rate_Of_Return";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CFG_CONTROL_GA_ID_0;
	String CALC_TYPE_LOV;
	String CALC_ACTY_LOV;
	String DATERANGE_START_DATE_0;
	String DATERANGE_END_DATE_0;
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
	public void setCFG_CONTROL_GA_ID_0(String cFG_CONTROL_GA_ID_0) {
		CFG_CONTROL_GA_ID_0 = cFG_CONTROL_GA_ID_0;
	}
	public void setCALC_TYPE_LOV(String cALC_TYPE_LOV) {
		CALC_TYPE_LOV = cALC_TYPE_LOV;
	}
	public void setCALC_ACTY_LOV(String cALC_ACTY_LOV) {
		CALC_ACTY_LOV = cALC_ACTY_LOV;
	}
	public void setDATERANGE_START_DATE_0(String dATERANGE_START_DATE_0) {
		DATERANGE_START_DATE_0 = dATERANGE_START_DATE_0;
	}
	public void setDATERANGE_END_DATE_0(String dATERANGE_END_DATE_0) {
		DATERANGE_END_DATE_0 = dATERANGE_END_DATE_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setCALC_ACTY_LOV(Stock.GetParameterValue("CALC_ACTY_LOV"));
		this.setCALC_TYPE_LOV(Stock.GetParameterValue("CALC_TYPE_LOV"));
		this.setCFG_CONTROL_GA_ID_0(Stock.GetParameterValue("CFG_CONTROL_GA_ID_0"));
		this.setDATERANGE_END_DATE_0(Stock.GetParameterValue("DATERANGE_END_DATE_0"));
		this.setDATERANGE_START_DATE_0(Stock.GetParameterValue("DATERANGE_START_DATE_0"));
	
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&CFG_CONTROL_GA_ID_0="+CFG_CONTROL_GA_ID_0+"&CALC_TYPE_LOV="+CALC_TYPE_LOV+"&CALC_ACTY_LOV="+CALC_ACTY_LOV+
				"&DATERANGE_START_DATE_0="+DATERANGE_START_DATE_0+"&DATERANGE_END_DATE_0="+DATERANGE_END_DATE_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for IPRR Disable service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run IPRR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run IPRR service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From response: ","CFG_CONTROL_PLAN_ID_0: "+doc.getElementsByTagName("CFG_CONTROL_PLAN_ID_0").item(0).getTextContent()+
					"\nCFG_CONTROL_PLAN_NAME_0: "+doc.getElementsByTagName("CFG_CONTROL_PLAN_NAME_0").item(0).getTextContent(),false);
			Reporter.logEvent(Status.INFO, "From response: ", "Expected message: \"IPRR Report Genertor is running, email notification will follow on complete!' and  email shall be generated to inbox.\" "+
					"\nActual message: "+doc.getElementsByTagName("StatusBarMessages").item(0).getTextContent() , false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void verifyFileCreated(){
		String directoryPath = "//fss-devlfs/qa_test/tmp/io" ;
		File directory = new File(directoryPath);
		File[] listOfFiles = directory.listFiles() ;
		
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		for (File file2 : listOfFiles) {
			
			String fileName = file2.getName() ;
			Long lastmodified = file2.lastModified() ;
	//		System.out.println(fileName+" : "+lastmodified);
			if(fileName.contains("PROR") && dateFormat.format(Calendar.getInstance().getTime()).equals("")){
				System.out.println(fileName);
				Reporter.logEvent(Status.PASS, "Validating the File generated", "File is generated on Current date: "+directory.lastModified(), false);
			//	break;
			}			
		}		
		
	}
}
