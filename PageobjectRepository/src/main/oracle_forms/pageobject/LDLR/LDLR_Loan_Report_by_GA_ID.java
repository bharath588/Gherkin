package pageobject.LDLR;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class LDLR_Loan_Report_by_GA_ID {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/LDLR_Defaulted_Loan_Report_by_GA_ID";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String INP1_DISP_DFT_VALUE_1;
	String INP1_DATE_VALUE_3;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
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
	public void setINP1_DISP_DFT_VALUE_1(String iNP1_DISP_DFT_VALUE_1) {
		INP1_DISP_DFT_VALUE_1 = iNP1_DISP_DFT_VALUE_1;
	}
	public void setINP1_DATE_VALUE_3(String iNP1_DATE_VALUE_3) {
		INP1_DATE_VALUE_3 = iNP1_DATE_VALUE_3;
	}
	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setINP1_DATE_VALUE_3(Stock.GetParameterValue("INP1_DATE_VALUE_3"));
		 this.setINP1_DISP_DFT_VALUE_1(Stock.GetParameterValue("INP1_DISP_DFT_VALUE_1"));
		 this.setMO1_DISP_DFT_PRIN_CODE_0(Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));
		 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&INP1_DISP_DFT_VALUE_1="+INP1_DISP_DFT_VALUE_1+
				 "&INP1_DATE_VALUE_3="+INP1_DATE_VALUE_3+"&MO1_DISP_DFT_PRIN_CODE_0="+MO1_DISP_DFT_PRIN_CODE_0+
				 "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for LDLR By GA ID service", this.queryString.replaceAll("&", "\n"), false);
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
				Reporter.logEvent(Status.PASS, "Run  LDLR Defaulted Loan Report by GA_ID service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run LDLR Defaulted Loan Report by GA_ID service", "Running Failed:", false);
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
	
		Reporter.logEvent(Status.INFO, "From response: ","TRANSACTION_CODE_0: "+doc.getElementsByTagName("TRANSACTION_CODE_0").item(0).getTextContent()+
				"\nTRANSACTION_DESCR_0: "+doc.getElementsByTagName("TRANSACTION_DESCR_0").item(0).getTextContent()+
				"\nResponse Messages: "+doc.getElementsByTagName("PopupMessages").item(0).getTextContent()+
				"\n",false);
		
		/*final String fileName = "GQ19LDLR_"+this.LOGON_USERNAME+".*";	//+ processing Id from output
        
		boolean isFileExists = new File("H:\\common\\devl\\reports\\"+fileName).exists();
           
        
           File file = new File("H:\\common\\devl\\reports\\");
           
           File[] fileList = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return false;
			}
           });
           
           Arrays.sort(fileList, new Comparator<File>() {

                  @Override
                  public int compare(File o1, File o2) {
                        // TODO Auto-generated method stub
                        return Long.compare(o2.lastModified(),o1.lastModified());
                  }
           });
           Date date = new Date(fileList[0].lastModified());
           
           if(date.getDate() == new Date().getDate())
           {      
             Reporter.logEvent(Status.PASS, "Verify if the .txt file is created for the user to the designated folder", "The .txt file has been created successfully", false);
           }
           else{
                 Reporter.logEvent(Status.FAIL, "Verify if the .txt file is created for the user to the designated folder", "The .txt file has not created in the designated output folder", false);
           }      
*/
	} 

}
