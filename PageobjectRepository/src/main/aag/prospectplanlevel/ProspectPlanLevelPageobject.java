package pageobjects.prospectplanlevel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.Status;
import com.sun.rowset.FilteredRowSetImpl;

import shell.utils.SftpUtils;
import shell.utils.ShellUtils;
import aag_lib.General;
import core.framework.Globals;
import lib.DB;
import lib.Reporter;
import lib.Stock;

public class ProspectPlanLevelPageobject {
	private LinkedHashMap<Integer, Map<String,String>> testData = null;
	static ResultSet queryResultSet;
	
	
	@BeforeClass
	public void InitTest() throws Exception {
		General.initialize_Log_File_Name_And_Path_Env_Variables();
		Reporter.initializeModule(this.getClass().getName());	
		SftpUtils.initializeRemoteDirectoryPath();	
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}
	
	
	
	public static ResultSet verifyInAccessCustomisation(String[] queryToVerifyAccessCustomisationTable,String planNumber)
	{
		queryResultSet = DB.executeQuery("MemberParticipantDB", queryToVerifyAccessCustomisationTable[0], planNumber);
		return queryResultSet;
	}
	

	
	public static ResultSet executeProspectBaseQuery(String[] prospectBaseQuery,String gcId)
	{
		queryResultSet = DB.executeQuery(Globals.dbNameAlias,prospectBaseQuery[1],gcId);
		return queryResultSet;
	}
	
	public static String getHouseholdIdFromIndId(String indId) throws SQLException
	{
		String houseHoldId = null;
		queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getHouseholdIdQuery")[1], indId);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				houseHoldId = queryResultSet.getString("MANAGED_USER_ID");
				break;
			}
		}
		return houseHoldId;
	}
	
	public static int getCountFromExistingRecordSet(FilteredRowSet existingFilterObj,FilteredRowSet derivedFilterObj,Predicate p) throws SQLException
	{
		
		derivedFilterObj.populate(existingFilterObj);
		derivedFilterObj.setFilter(p);
		return DB.getRecordSetCount(derivedFilterObj);
	}
	
	public static String getIndIdFromHousehold(String houseHoldId) throws SQLException
	{
		String indId = null;
		queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getIndIdfromHouseholdId")[1], houseHoldId);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				indId = queryResultSet.getString("ind_id");
				break;
			}
		}
		return indId;
	}
	
	public static void runBatchSyncJob() throws IOException
	{
		
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./batchsync.ksh");
	}
	
	public static void runDefaultProspectJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "Default Prospect File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./DefaultProspect.ksh");
	}
	
}
