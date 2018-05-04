package aag_lib;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebElement;
import org.w3c.dom.NodeList;

import shell.utils.SftpUtils;
import lib.DB;
import lib.Stock;
import lib.Web;
import core.framework.Globals;

public class General {
	private static File localtempFolder=new File(Globals.GC_LOCAL_TEMP_DIRECTORY); 
	public static LinkedHashMap<String,Map<String,String>> globalMultiPptMap;
	static Map<String,String> tempAttrMap ;
	static NodeList nodeList=null;
	static ResultSet queryResultSet;
	static FileWriter inputFile;
	static BufferedWriter fileWriter;
	static Scanner fileScanner;
	public static File getlogfile=null;

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			getBatchRunDate()
    DESCRIPTION:	    This method returns the Batch run date which the previous date from Current System date to run the batch process
    PARAMETERS: 		None
    RETURNS:		    String - batch Run Date in string format   
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 03-12-2015       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
	public static String getBatchRunDate()
	{
		String lastDay=null;
		DateFormat df = new SimpleDateFormat(Globals.GC_BATCH_RUN_DATE_FORMAT);			
		Calendar previousDay = Calendar.getInstance();
		previousDay.add(Calendar.DATE, -1);
		lastDay=df.format(previousDay.getTime());		
		return lastDay;
	}
	
	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			getCurrentSystemDate(String dateFormat)
    DESCRIPTION:	    This method returns the Current System date based on the format specified
    PARAMETERS: 		date Format
    RETURNS:		    String - System  Date in string format   
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 03-12-2015       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
	public static String getCurrentSystemDate(String dateFormat)
	{
		String currentDate=null;		
		DateFormat df=new SimpleDateFormat(dateFormat);
		Date date = new Date();
		currentDate=df.format(date);
		return currentDate;
		
	}
	
	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			getDateInAnyPattern(Date inputDate,String dateFormat))
    DESCRIPTION:	    This method returns the input date provided in the format specified by the user
    PARAMETERS: 		inputDate, date Format
    RETURNS:		    String - InputDate  Date in string format   
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 18-01-2016       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/public static String getDateInAnyPattern(Date inputDate,String dateFormat)	{
		String returnDate=null;
		DateFormat df=new SimpleDateFormat(dateFormat);
		if(inputDate!=null)
		returnDate=df.format(inputDate).toUpperCase();
		return returnDate;
	}
	
	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			getInputFile(String fileNamePattern,String fileNameEndsWithPattern)	
    DESCRIPTION:	    This method returns latest/recent input file based on the file name pattern
    PARAMETERS: 		String file Name Pattern,fileNameEndsWithPattern
    RETURNS:		    returns the input file               	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani / Krushna    Date : 26-05-2016       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
     */	public static File getInputFile(String fileNamePattern,String fileNameEndsWithPattern)
	{
    	 File inputFile = null;	
 		List<File> list = new LinkedList<File>();
 		try{	
 			File[] listOfFiles=localtempFolder.listFiles();
 			
 			for(File file : listOfFiles)
 			{
 				if(file.getName().startsWith(fileNamePattern)  && file.getName().endsWith(fileNameEndsWithPattern))
 				list.add(file);	
 				}
 			
 			inputFile = Collections.max(list,new Comparator<File>() {

 				@Override
 				public int compare(File o1, File o2) {
 					return String.valueOf(o1.lastModified()).compareTo(String.valueOf(o2.lastModified()));
 					
 				}
 			});
 			
 		}
 		catch(Exception e) { e.printStackTrace(); }
 		return inputFile;
	}
     
  	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
     FUNCTION:			verifyFileInArchiveFolder(File inputFileName,String archiveFolderPath)	
     DESCRIPTION:	    This method Verifies whether Enroll/Cancel and Transaction request input file is moved to archive folder     					
     PARAMETERS: 		 inputFileName, archiveFolderPath
     RETURNS:		    Boolean - whether enroll/cancel and Transaction request file is processed successfully or not                	
     REVISION HISTORY: 
     ------------------------------------------------------------------------------------------------------------------------------------------------------------
     Author : Janani     Date : 02-10-2016       
     ------------------------------------------------------------------------------------------------------------------------------------------------------------
 */  public static boolean verifyFileInArchiveFolder(File inputFileName,String archiveFolderPath)
     {
    	 boolean isFileArchived =false;
    	 File archiveFolder=new File(archiveFolderPath);
    	 File[] listOfArchivedFiles=archiveFolder.listFiles();
    	 for(int i=0;i<listOfArchivedFiles.length;i++)
		   {
			   if(listOfArchivedFiles[i].getName().equalsIgnoreCase(inputFileName.getName()))
			   {
				   System.out.println("Archived File Name: "+listOfArchivedFiles[i].getName());
				   isFileArchived= true;	
			   }
		   }
    	 return isFileArchived;
     }
     
     
  	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
     FUNCTION:			verifyLog(String logFilePath,String validationMessage,String logFilePattern,String logFileType)	
     DESCRIPTION:	    This method Verifies whether Enroll/Cancel and Transaction request input file  log is displayed with success entry
     PARAMETERS: 		String logFilePath,String validationMessage,String logFilePattern,String logFileType
     RETURNS:		    Boolean - whether enroll/cancel and Transaction request file is processed successfully or not                	
     REVISION HISTORY: 
     ------------------------------------------------------------------------------------------------------------------------------------------------------------
     Author : Janani R    Date : 02-10-2016       
     ------------------------------------------------------------------------------------------------------------------------------------------------------------
 */  public static boolean verifyLog(String logFilePath,String validationMessage,String logFilePattern,String logFileType)
     {
    	 boolean isLogSuccess=false;
    	 File logFile=null;
    	 Scanner scanner = null;
   	  try {    	
    	 File[] listOfLogFiles = new File(logFilePath).listFiles(new FileFilter() { public boolean accept(File file) { return file.isFile(); } });        
    	 Arrays.sort(listOfLogFiles);
		 for (File file : listOfLogFiles) {			
			if (file.getName().startsWith(logFilePattern) && file.getName().endsWith(logFileType)) 					 
				   logFile = file;		
		   }
		 System.out.println("Log File Name: "+ logFile.getName());
		 scanner = new Scanner(logFile);
		 while(scanner.hasNext())
			{
				String line =scanner.nextLine();
			    Pattern p = Pattern.compile(validationMessage);
			    Matcher m = p.matcher(line);
				if(m.find())
				isLogSuccess=true;
				
			}
		 scanner.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }	
			
    	 return isLogSuccess;
     }
   
 /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
 FUNCTION:			threadSleepInvocation(int seconds)	
 DESCRIPTION:	    This method halts the execution for the given seconds of time
 PARAMETERS: 		int seconds
 RETURNS:		    None                	
 REVISION HISTORY: 
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
 Author : Janani R    Date : 02-08-2016       
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/ 
 public static void threadSleepInvocation(int seconds)
 {
	 try {  Thread.sleep(seconds); }                       
	 catch (InterruptedException e) {  e.printStackTrace();   }
 }

 /**
  * <pre>The method returns the number of participants from the test execution config</pre>
  * @return the number of participants
  */
 
 public static int getNumberOfParticipants()
 {
	 return Integer.valueOf(Stock.getConfigParam("No_Of_Participants"));
 }

 /**
  * <pre>This method returns the size of the global participant map</pre>
  * @return size of the multiple participant map
  */
 public static int getSizeOfMultipptMap()
 {
	 return globalMultiPptMap.size();
 }
 
 /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
 FUNCTION:			initialize_Log_File_Name_And_Path_Env_Variables()	
 DESCRIPTION:	    This method initializes the Global Variables based on the Environment(Dev o r QA)
 PARAMETERS: 		None
 RETURNS:		    None                	
 REVISION HISTORY: 
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
 Author : Janani R    Date : 11-05-2016       
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
 public static void initialize_Log_File_Name_And_Path_Env_Variables()
 {
	 if(Stock.getConfigParam("TEST_ENV").contains("PROJ"))
	 {
		 Globals.GC_PROSPECT_LOG_FILE_PATTERN="prospect_";
		 Globals.GC_DEFAULT_PROSPECT_LOG_FILE_PATTERN="DefaultProspect_";
		 Globals.GC_MEMBER_LOG_FILE_PATTERN="member_";
		 Globals.GC_ENROLL_CANCEL_LOG_FILE_PATTERN="enroll_";
		 Globals.GC_TRANSACTION_LOG_FILE_PATTERN="transaction_";
		 Globals.GC_TRANSACTION_COINFIRMATION_LOG_FILE_PATTERN="txnconf_";
		 Globals.GC_SELECT_ENVIRONMENT=1;
		 Globals. GC_SELECT_FOLDER_FOR_RUNNING_JOB="cd scripts";	
		 Globals. GC_Database_Prefix="D_";
		 Globals.GC_SetEnvCommand = "setenv ISIS_HOME opt/isis/solaris/qa_test/solaris";
		 Globals.GC_OPTOUT_LOG_FILE_PATTERN = "optout_";
	 }
	 else if(Stock.getConfigParam("TEST_ENV").contains("QA"))
	 {
		 Globals.GC_SetEnvCommand = "setenv ISIS_HOME /opt/isis/stage/qsinst/";
		 Globals.GC_PROSPECT_LOG_FILE_PATTERN="qaprospect_";
		 Globals.GC_MEMBER_LOG_FILE_PATTERN="qamember_";
		 Globals.GC_ENROLL_CANCEL_LOG_FILE_PATTERN="qaenroll_";
		 Globals.GC_TRANSACTION_LOG_FILE_PATTERN="qatransaction_";
		 Globals.GC_TRANSACTION_COINFIRMATION_LOG_FILE_PATTERN="qatxnconf_";
		 Globals.GC_SELECT_ENVIRONMENT=2;
		 Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB="cd scripts_qa";
		 Globals.GC_Database_Prefix="Q_";
		 Globals.GC_OPTOUT_LOG_FILE_PATTERN = "optout_";
	 }
}
 
 /*
 FUNCTION:			cleanupInputDirectory()	
 DESCRIPTION:	    This method cleans the remote input directory before uploading the Transaction and Enroll Input File
 PARAMETERS: 		None
 RETURNS:		    None                	
 REVISION HISTORY: 
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
 Author : Krushna Behera   Date : 25-05-2016       
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
public static void cleanupInputDirectory()
{
       SftpUtils.initializeRemoteDirectoryPath();
       File remoteInputFolder = new File(Globals.GC_REMOTE_INPUT_PATH);
       File[] fileList = remoteInputFolder.listFiles();
              for(File inputFile : fileList)
              {
              if(inputFile.getName().startsWith(Globals.GC_ENROLLCANCEL_FILE_NAMEPATTERN)  || 
                           inputFile.getName().startsWith(Globals.GC_TRANSACTION_INPUT_FILE_NAMEPATTERN))
                     inputFile.delete();
              }
}


 public static void setDBInstance(String sponsorId) throws SQLException
 {
	 String dbInstance = null;
	 queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")),
				Stock.getTestQuery("queryToGetDBInstance")[1],
				sponsorId + "%");
		if (queryResultSet != null) {
			while (queryResultSet.next()) {
				dbInstance = Globals.GC_Database_Prefix
						+ queryResultSet.getString("database_instance")
								.toUpperCase();
				break;
			}
		}
		Stock.setConfigParam(Globals.dbNameAlias, dbInstance, true);
 }
 
 public static boolean isProspectFileExists(String sponsorId)
 {
	 boolean isExist=false;
	 
	 File remoteOutoutFolder = new File(Globals.GC_REMOTE_OUTPUT_PATH);
	 File[] prospectFileList = remoteOutoutFolder.listFiles();
	 if(prospectFileList.length == 0)
	 {
		 return false;
	 }else{
	 for(File prospectFile : prospectFileList)
	 {
		 if(prospectFile.getName().endsWith("xml"))
		 {
		 if(prospectFile.getName().startsWith("ma_empower_dc_prosp")  &&
				 prospectFile.getName().endsWith(sponsorId+".xml"))
			 isExist = true;
		 break;
	 }
	 }
	 return isExist;
 }
 }
 
 public static void unenrollParticipant(String participantId) throws Exception
 {
	 DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("unenrollPptQuery")[0],participantId);
 }
 
 public static boolean isMultiplePlanOnly()
 {
	 if(Stock.getConfigParam(GlobalVar.typeOfFlow).equalsIgnoreCase("PlanOnly"))
	 {
		 return true;
	 }else{
		 return false;
	 }
 }
 
 public static void uploadFileUsingRest()
 {
	 File inFile = new File("OPTOUT.txt");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			
			// server back-end URL
			HttpPost httppost = new HttpPost("https://proj10.retirementpartner.com/aagAdmin/adhocBatchAjax.do");
			MultipartEntity entity = new MultipartEntity();
			// set the file input stream and file name as arguments
			httppost.addHeader("Content-Type", "multipart/form-data");
			entity.addPart("processCode", new StringBody("FEOPTOUT"));
			entity.addPart("gcId", new StringBody("95120"));
			entity.addPart("browseText", new InputStreamBody(fis, inFile.getName()));
			httppost.setEntity(entity);
			// execute the request
			HttpResponse response = httpclient.execute(httppost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");
			
			System.out.println("[" + statusCode + "] " + responseString);
			
		} catch (ClientProtocolException e) {
			System.err.println("Unable to make connection");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Unable to read file");
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (IOException e) {}
		}
 }
 
 public static void initializeFile(String filePath) throws IOException
 {
	 inputFile = new FileWriter(filePath);
	 fileWriter = new BufferedWriter(inputFile);
 }
 public static void writePptsIntotxtFile(String indId,String reasonCode) throws IOException
 { 
	fileWriter.write(indId + "," + reasonCode +"\n");
 }
 
 public static void flushFileWriterInstances() throws IOException
 {
	 fileWriter.close();
	 inputFile.close();
 }
 static String methodName = "";
 public static String getMethodNamefromStackTrace()
 {
	 methodName  =(Thread.currentThread().getStackTrace())[2].getMethodName();
	 methodName = methodName.substring(0, methodName.length() - 10);
	 return methodName;
 }
 
 
 public static boolean validateIfRecordExistsinMessageTable(String batchName,String userId)
 {
	 queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("validateMsgTableQuery")[1], batchName,userId);
	 
	 if(DB.getRecordSetCount(queryResultSet) > 0)
	 {
		 return true;
	 }else{
		 return false;
	 }
 }
 
 public static String getParticipantUnderPlan(String gaId) throws SQLException
 {
	 String indId = null;
	 General.setDBInstance(gaId.split("-")[0]);
	 
//	 queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getPptforPlan")[1], gaId.split("-")[0]);
	queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getOptoutPptforPlan")[1], gaId.split("-")[0]); 
	 while(queryResultSet.next())
	 {
		 indId = queryResultSet.getString("IND_ID");
		 break;
	 }
	 return indId;
 }
 
 public static String getOptoutParticipantUnderPlan(String gaId) throws SQLException
 {
	 String indId = null;
	 
	 
	 queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getOptoutPptforPlan")[1], gaId+"%");
	 
	 while(queryResultSet.next())
	 {
		 indId = queryResultSet.getString("IND_ID");
		 break;
	 }
	 return indId;
 }
 
 public static <T> boolean compareWithNull(T expected,T... actual)
 {
	 boolean tempCheck;
	 boolean actualCheck = true;
	 
	 for(T t:actual)
	 {
		 tempCheck = expected.equals(t);
		 actualCheck = actualCheck && tempCheck;
	 }
	 return actualCheck;
 }
 
 static File file = null;
 public static boolean checkIfTextPresent(String filePath)
 {
	 boolean isExist = false;
	 
	 
	 return isExist;
 }
 
 public static void CreateOptoutFile(String filePath,String indId,String reasonCode) throws IOException
 {
	initializeFile(filePath);
	writePptsIntotxtFile(indId, reasonCode);
	flushFileWriterInstances();
 }

/**
	 * Method to get the test environment
	 * @param envName
	 * @return String - Globals.DB_TYPE.get(envName)
	 */
	public static   String checkEnv(String envName){
		if(envName.contains("PROJ")){
			return Globals.DB_TYPE.get("PROJ");
		}
		if(envName.contains("QA")){
			return Globals.DB_TYPE.get("QA");
		}
		if(envName.contains("PROD")){
			return Globals.DB_TYPE.get("PROD");
		}
		return null;
	}
 
}
