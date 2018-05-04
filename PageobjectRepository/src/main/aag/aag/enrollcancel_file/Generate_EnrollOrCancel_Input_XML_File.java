package aag.enrollcancel_file;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;









import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import aag.prospect_file.Prospect_XML_File_Validation;
import aag.prospectplanlevel.ProspectPlanLevelPageobject;
import shell.utils.ShellUtils;
import aag.aag_lib.General;
import aag.aag_lib.GlobalVar;
import core.framework.Globals;

public class Generate_EnrollOrCancel_Input_XML_File {
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	private static TransformerFactory transformerFactory =null;
	private static Transformer transformer =null;
	private static DOMSource source = null;		
	private static StreamResult result =null;
	//private static File enrollCancelTemplateFile=new File("C:\\temp\\ma_fei_enrll_template.xml");
	public static String enrollInputFileName = null;
	private static File enrollCancelTemplateFile=new File(System.getProperty("user.dir")+"\\Resource\\ma_fei_enrll_template.xml");
	public static File enrollCancelInputFile = null;
	public static String cancelInputFileName = null;
	public static LinkedHashMap<String,Map<String,String>> multiplePptMap= null;
	public static Map<String, String> attributesMap = null;
	public static List<String> houseHoldList = null;
	public static List<String> individualIdList = null;
	public static Map<String,String> tempAttrMap;

	
	/**
	 * This method initializes the multiple participant map
	 * @return
	 */
	public static Map<String,Map<String,String>> initMultiPptMap()
	{
		multiplePptMap = Prospect_XML_File_Validation
				.getProspectHouseHoldID();
		return multiplePptMap;
	}

	/**
	 * This method takes the key of the map as a parameter and returns  the values as a list
	 * @param key
	 * @return
	 */
	public static List<String> getSpecifiedAttribute(String key)
	{
		List<String> attributeList = new ArrayList<String>();
		for(Map.Entry<String, Map<String,String>> entry: multiplePptMap.entrySet())
		{
			attributesMap = entry.getValue();
			attributeList.add(attributesMap.get(key));
		}
		return attributeList;
	}
	
	/**
	 * This method writes to enroll / cancel file based on the parameter passed
	 * by the user
	 * @param enrollOrCancelJob
	 */
	public static void generateEnrollCancelFile(String enrollOrCancelJob)
    {
           int numberOfppts;
           try {
                  dbFactory = DocumentBuilderFactory.newInstance();
                  dBuilder = dbFactory.newDocumentBuilder();
                  document = dBuilder.parse(enrollCancelTemplateFile);
                  document.getDocumentElement().normalize();
                  multiplePptMap = Prospect_XML_File_Validation
                               .getProspectHouseHoldID();
                  Node batchRunDate = document.getElementsByTagName(
                               "fei_batch:Run_Date").item(0);
                  batchRunDate.setTextContent(General
                               .getCurrentSystemDate(Globals.GC_BATCH_RUN_DATE_FORMAT));

                  Node sponsorNode = document.getElementsByTagName(
                               "fei_batch:Sponsor_ID").item(0);
                  sponsorNode.setTextContent(Stock.GetParameterValue("plan_SponsorId"));
                  houseHoldList = getSpecifiedAttribute(GlobalVar.houseHoldId);
                  individualIdList=getSpecifiedAttribute(GlobalVar.Individualid);
                  Node newEnrollCancelNode = document.getElementsByTagName(
                               "fei_batch:Batch_Enrollment_Status_Update").item(0);
                  numberOfppts = General.getNumberOfParticipants();
                  if(enrollOrCancelJob.equalsIgnoreCase(Globals.GC_ENROLL_STATUS))
                  {
                        for(int iCount = 0; iCount<numberOfppts ;iCount++)
                        {
                               Element enrollment = document
                                             .createElement("fei_batch:Enrollment");  
                               newEnrollCancelNode.appendChild(enrollment);
                               Element userID = document.createElement("fei_batch:User_ID");
                               enrollment.appendChild(userID);
                               userID.setTextContent(houseHoldList.get(iCount));
                               Element enrollDate = document
                                             .createElement("fei_batch:Enrollment_Date");
                               enrollment.appendChild(enrollDate);
                               enrollDate.setTextContent(General.getBatchRunDate());                              
                        }
                  }else if(enrollOrCancelJob.equalsIgnoreCase(Globals.GC_CANCEL_STATUS))
                  {
                        for(int iCount = 0; iCount<numberOfppts ;iCount++)
                        {
                               Element enrollment = document
                                             .createElement("fei_batch:Cancellation");       
                               newEnrollCancelNode.appendChild(enrollment);
                               Element userID = document.createElement("fei_batch:User_ID");
                               enrollment.appendChild(userID);
                               userID.setTextContent(houseHoldList.get(iCount));
                               Element enrollDate = document
                                             .createElement("fei_batch:Cancellation_Date");
                               enrollment.appendChild(enrollDate);
                               enrollDate.setTextContent(General.getBatchRunDate());                              
                        }
                  }
                  
                  // write the content into xml file
                                      transformerFactory = TransformerFactory.newInstance();
                                      transformer = transformerFactory.newTransformer();
                                      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                      transformer.setOutputProperty(
                                                    "{http://xml.apache.org/xslt}indent-amount", "1");
                                      source = new DOMSource(document);
                                      if(enrollOrCancelJob.equalsIgnoreCase("enroll"))
                                      {
                                      enrollInputFileName = "C:\\temp\\ma_fei_enrll_"+General.getCurrentSystemDate("yyyyMMdd_HHmmss")+Globals.GC_FILE_TYPE;
                                      enrollCancelInputFile = new File(enrollInputFileName);
                                      }
                                      else if(enrollOrCancelJob.equalsIgnoreCase("cancel"))
                                      {
                                      cancelInputFileName = "C:\\temp\\ma_fei_enrll_"+General.getCurrentSystemDate("yyyyMMdd_HHmmss")+Globals.GC_FILE_TYPE; 
                                      enrollCancelInputFile = new File(cancelInputFileName);
                                      }
                                      result = new StreamResult(enrollCancelInputFile);
                                      
                                      transformer.transform(source, result);
                                      if(enrollOrCancelJob.equalsIgnoreCase(Globals.GC_ENROLL_STATUS))
                                      {
                                             Reporter.logEvent(
                                                           Status.PASS,
                                                           "Verify Enroll Input File is created",
                                                           "Enroll Input File  "
                                                                        + enrollCancelInputFile.getName()
                                                                        + " is created successfully for enrolling multiple participant: \n"
                                                                        + "Participant Household Id's:"
                                                                        + houseHoldList + "\n"
                                                                        + "Participant Individual Id: "
                                                                        + individualIdList,
                                                                        false);
                                      }else if(enrollOrCancelJob.equalsIgnoreCase(Globals.GC_CANCEL_STATUS))
                                      {
                                             Reporter.logEvent(
                                                           Status.PASS,
                                                           "Verify Cancel Input File is created",
                                                           "cancel Input File  "
                                                                        + enrollCancelInputFile.getName()
                                                                        + " is created successfully for enrolling multiple participant: \n"
                                                                        + "Participant Household Id(s):"
                                                                        + houseHoldList + "\n"
                                                                        + "Participant Individual Id(s): "
                                                                        + individualIdList,
                                                                        false);
                                      }
           }catch (ParserConfigurationException | SAXException | IOException e) {
                  e.printStackTrace();
           } catch (TransformerConfigurationException e) {
                 e.printStackTrace();
           } catch (TransformerException e) {
        	   	e.printStackTrace();           
           }
    }
	
	public static String getSponsorIdFromIndid(String indId) throws SQLException
	{
		String sponsorId = "";
		ResultSet queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getSponsorId")[1],indId);
		while(queryResultSet.next())
		{
			sponsorId = queryResultSet.getString("GA_ID");
			break;
		}
		
		return sponsorId.split("-")[0];
	}
	
	public static void generateEnrollFile(String indId) throws SQLException
	{
		 try {
             dbFactory = DocumentBuilderFactory.newInstance();
             dBuilder = dbFactory.newDocumentBuilder();
             document = dBuilder.parse(enrollCancelTemplateFile);
             document.getDocumentElement().normalize();
             
             Node batchRunDate = document.getElementsByTagName(
                          "fei_batch:Run_Date").item(0);
             batchRunDate.setTextContent(General
                          .getCurrentSystemDate(Globals.GC_BATCH_RUN_DATE_FORMAT));

             Node sponsorNode = document.getElementsByTagName(
                          "fei_batch:Sponsor_ID").item(0);
             sponsorNode.setTextContent(getSponsorIdFromIndid(indId));
             String houseHoldId = ProspectPlanLevelPageobject.getHouseholdIdFromIndId(indId);
             
             Node newEnrollCancelNode = document.getElementsByTagName(
                          "fei_batch:Batch_Enrollment_Status_Update").item(0);
             
                 
                          Element enrollment = document
                                        .createElement("fei_batch:Enrollment");  
                          newEnrollCancelNode.appendChild(enrollment);
                          Element userID = document.createElement("fei_batch:User_ID");
                          enrollment.appendChild(userID);
                          userID.setTextContent(houseHoldId);
                          Element enrollDate = document
                                        .createElement("fei_batch:Enrollment_Date");
                          enrollment.appendChild(enrollDate);
                          enrollDate.setTextContent(General.getBatchRunDate());                              
                   
             
             
             // write the content into xml file
                                 transformerFactory = TransformerFactory.newInstance();
                                 transformer = transformerFactory.newTransformer();
                                 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                 transformer.setOutputProperty(
                                               "{http://xml.apache.org/xslt}indent-amount", "1");
                                 source = new DOMSource(document);
                                 
                                 enrollInputFileName = "C:\\temp\\ma_fei_enrll_"+General.getCurrentSystemDate("yyyyMMdd_HHmmss")+Globals.GC_FILE_TYPE;
                                 enrollCancelInputFile = new File(enrollInputFileName);
                                 
                                 
                                 result = new StreamResult(enrollCancelInputFile);
                                 
                                 transformer.transform(source, result);
                                 
                                        Reporter.logEvent(
                                                      Status.PASS,
                                                      "Verify Enroll Input File is created",
                                                      "Enroll Input File  "
                                                                   + enrollCancelInputFile.getName()
                                                                   + " is created successfully for enrolling multiple participant: \n"
                                                                   + "Participant Household Id's:"
                                                                   + houseHoldList + "\n"
                                                                   + "Participant Individual Id: "
                                                                   + individualIdList,
                                                                   false);
                                 
      }catch (ParserConfigurationException | SAXException | IOException e) {
             e.printStackTrace();
      } catch (TransformerConfigurationException e) {
            e.printStackTrace();
      } catch (TransformerException e) {
   	   	e.printStackTrace();           
      }
	}

	public static void runEnrollJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "EnrollFile File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./enroll.ksh");
	}
}
