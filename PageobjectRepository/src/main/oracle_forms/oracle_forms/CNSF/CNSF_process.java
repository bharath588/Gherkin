package pageobject.CNSF;

import generallib.General;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import shell.utils.ShellUtils;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class CNSF_process {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/CNSF_Process";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String OMNI_SPLIT_FILE_NAME_0;
	String OMNI_SPLIT_GA_ID_0;
	
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
	public void setOMNI_SPLIT_FILE_NAME_0(String oMNI_SPLIT_FILE_NAME_0) {
		OMNI_SPLIT_FILE_NAME_0 = oMNI_SPLIT_FILE_NAME_0;
	}
	public void setOMNI_SPLIT_GA_ID_0(String oMNI_SPLIT_GA_ID_0) {
		OMNI_SPLIT_GA_ID_0 = oMNI_SPLIT_GA_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setOMNI_SPLIT_FILE_NAME_0(Stock.GetParameterValue("OMNI_SPLIT_FILE_NAME_0"));
		this.setOMNI_SPLIT_GA_ID_0(Stock.GetParameterValue("OMNI_SPLIT_GA_ID_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+
				"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&OMNI_SPLIT_FILE_NAME_0="+OMNI_SPLIT_FILE_NAME_0+
				"&OMNI_SPLIT_GA_ID_0="+OMNI_SPLIT_GA_ID_0+
				"&numOfRowsInTable=20&json=false&handlePopups=true&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for CNSF service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: " + serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run CNSF service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CNSF service",
					"Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException, IOException, InterruptedException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);			
		
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
		/*
		write script to run shell commands
		author: bbndsh
		*/
		
		
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		ShellUtils.executeShellCommand(0);
		ShellUtils.executeShellCommand("cd /grphome/pnp/conversion_pnp/1350109-01/");	
		ShellUtils.executeShellCommand("if [-f sv.dat]; then echo \"Files found\" ; else echo \"No files found\" ; fi ");
	
		
	//	ExecuteShellCommand com = new ExecuteShellCommand();
		System.out.println(executeCommand("cd ~"));
		System.out.println(executeCommand("bash"));
		System.out.println(executeCommand("1"));
		System.out.println(executeCommand("0"));
		System.out.println(executeCommand("cd /grphome/pnp/conversion_pnp/1350109-01/"));
		System.out.println(executeCommand("if [-f sv.dat]; then echo \"Files found\" ; else echo \"No files found\" ; fi "));
		
	
	}	
	
	public String executeCommand(String cmd) throws IOException, InterruptedException{
		 
		StringBuffer output = new StringBuffer();

	    Process p;
	    try {
	        p = Runtime.getRuntime().exec(cmd);
	        p.waitFor();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

	        String line = "";           
	        while ((line = reader.readLine())!= null) {
	            output.append(line + "\n");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return output.toString();
	}

}
