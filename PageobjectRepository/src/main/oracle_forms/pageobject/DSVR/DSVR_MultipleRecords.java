package pageobject.DSVR;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import core.framework.Globals;

public class DSVR_MultipleRecords {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/DSVR_multiple_records_w_same_step_compltn_date_1";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	public ResultSet queryResultSet;
	
	
	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public DocumentBuilderFactory getDocBuilderFactory() {
		return docBuilderFactory;
	}

	public void setDocBuilderFactory(DocumentBuilderFactory docBuilderFactory) {
		this.docBuilderFactory = docBuilderFactory;
	}

	public DocumentBuilder getDocBuilder() {
		return docBuilder;
	}

	public void setDocBuilder(DocumentBuilder docBuilder) {
		this.docBuilder = docBuilder;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public ResultSet getQueryResultSet() {
		return queryResultSet;
	}

	public void setQueryResultSet(ResultSet queryResultSet) {
		this.queryResultSet = queryResultSet;
	}

	public String getCONTROL_SELECTION_0() {
		return CONTROL_SELECTION_0;
	}

	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}

	public String getLOGON_DATABASE() {
		return LOGON_DATABASE;
	}

	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}

	public String getLOGON_PASSWORD() {
		return LOGON_PASSWORD;
	}

	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}

	public String getLOGON_USERNAME() {
		return LOGON_USERNAME;
	}

	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}

	public String getINP1_DATE_VALUE_9() {
		return INP1_DATE_VALUE_9;
	}

	public void setINP1_DATE_VALUE_9(String iNP1_DATE_VALUE_9) {
		INP1_DATE_VALUE_9 = iNP1_DATE_VALUE_9;
	}

	public String getINP1_DATE_VALUE_9_X1() {
		return INP1_DATE_VALUE_9_X1;
	}

	public void setINP1_DATE_VALUE_9_X1(String iNP1_DATE_VALUE_9_X1) {
		INP1_DATE_VALUE_9_X1 = iNP1_DATE_VALUE_9_X1;
	}

	public String getINP1_DISP_DFT_VALUE_1() {
		return INP1_DISP_DFT_VALUE_1;
	}

	public void setINP1_DISP_DFT_VALUE_1(String iNP1_DISP_DFT_VALUE_1) {
		INP1_DISP_DFT_VALUE_1 = iNP1_DISP_DFT_VALUE_1;
	}

	public String getINP1_DISP_DFT_VALUE_9() {
		return INP1_DISP_DFT_VALUE_9;
	}

	public void setINP1_DISP_DFT_VALUE_9(String iNP1_DISP_DFT_VALUE_9) {
		INP1_DISP_DFT_VALUE_9 = iNP1_DISP_DFT_VALUE_9;
	}

	public String getINP1_DISP_DFT_VALUE_9_X1() {
		return INP1_DISP_DFT_VALUE_9_X1;
	}

	public void setINP1_DISP_DFT_VALUE_9_X1(String iNP1_DISP_DFT_VALUE_9_X1) {
		INP1_DISP_DFT_VALUE_9_X1 = iNP1_DISP_DFT_VALUE_9_X1;
	}

	public String getMO1_DISP_DFT_PRIN_CODE_0() {
		return MO1_DISP_DFT_PRIN_CODE_0;
	}

	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String INP1_DATE_VALUE_9;
	String INP1_DATE_VALUE_9_X1;
	String INP1_DISP_DFT_VALUE_1;
	String INP1_DISP_DFT_VALUE_9;
	String INP1_DISP_DFT_VALUE_9_X1;
	String MO1_DISP_DFT_PRIN_CODE_0;
	
	public void setServiceParameters()
	{
		this.CONTROL_SELECTION_0 = Stock.GetParameterValue("CONTROL_SELECTION_0");
		this.INP1_DATE_VALUE_9 = Stock.GetParameterValue("INP1_DATE_VALUE_9");
		this.INP1_DATE_VALUE_9_X1 = Stock.GetParameterValue("INP1_DATE_VALUE_9_X1");
		this.INP1_DISP_DFT_VALUE_1 = Stock.GetParameterValue("INP1_DISP_DFT_VALUE_1");
		this.INP1_DISP_DFT_VALUE_9 = Stock.GetParameterValue("INP1_DISP_DFT_VALUE_9");
		this.INP1_DISP_DFT_VALUE_9_X1 = Stock.GetParameterValue("INP1_DISP_DFT_VALUE_9_X1");
		this.MO1_DISP_DFT_PRIN_CODE_0 = Stock.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0");
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		queryString = "?LOGON_USERNAME=" + LOGON_USERNAME + "&LOGON_PASSWORD=" + LOGON_PASSWORD + "&LOGON_DATABASE=" + LOGON_DATABASE
				+"&CONTROL_SELECTION_0=" + CONTROL_SELECTION_0 + "&MO1_DISP_DFT_PRIN_CODE_0=" + MO1_DISP_DFT_PRIN_CODE_0
				+"&INP1_DISP_DFT_VALUE_9_X1="+INP1_DISP_DFT_VALUE_9_X1+"&INP1_DISP_DFT_VALUE_9="+INP1_DISP_DFT_VALUE_9
				+"&INP1_DISP_DFT_VALUE_1="+INP1_DISP_DFT_VALUE_1+"&INP1_DATE_VALUE_9_X1="+INP1_DATE_VALUE_9_X1
				+"&INP1_DATE_VALUE_9="+INP1_DATE_VALUE_9
				+"&numOfRowsInTable=20&json=false&handlePopups=false";	
		Reporter.logEvent(Status.INFO, "Prepare test data for DSVR service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run DSVR service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run DSVR service", "Running Failed:", false);
		}
	}
	

	public void validateOutput()
    {
           final String fileName = "GQ19DSVR_"+this.getINP1_DISP_DFT_VALUE_1()+"."+this.getLOGON_USERNAME()+".";//+ processing Id from output
           boolean isFileExists = new File("H:\\common\\devl\\reports\\"+fileName).exists();
           
        
           File file = new File("H:\\common\\devl\\reports\\");
           
           File[] fileList = file.listFiles(new FilenameFilter() {
                  
                  @Override
                  public boolean accept(File dir, String name) {
                        
                        return name.startsWith(fileName);
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
                  }else{
                        Reporter.logEvent(Status.FAIL, "Verify if the .txt file is created for the user to the designated folder", "The .txt file has not created in the designated output folder", false);
                  }      
           
    }


	
	
}
