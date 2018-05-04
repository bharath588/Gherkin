package pageobjects.bcomregression;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import shell.utils.ShellUtils;
import aag_lib.General;
import aag_lib.GlobalVar;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import lib.DB;
import lib.Reporter;
import lib.Stock;

public class BcomRegressionUtils {

	ResultSet queryResultSet ;
	
	public int getSecAuditRecordsCount(String dbInst,String query)
	{
		queryResultSet = DB.executeQuery(dbInst, query);
		return DB.getRecordSetCount(queryResultSet);
	}
	
	
	public int getSecAuditGrpDetlRecordsCount(String dbInst,String query)
	{
		queryResultSet = DB.executeQuery(dbInst, query);
		return DB.getRecordSetCount(queryResultSet);
	}
	
	public int getSecAuditPptDetlRecordsCount(String dbInst,String query)
	{
		queryResultSet = DB.executeQuery(dbInst, query);
		return DB.getRecordSetCount(queryResultSet);
	}


	public void runPopulateBatch() throws IOException {
			Reporter.logEvent(Status.INFO, "Run Populate Batch Job", "Populate Batch Job is Completed", false);
			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
			ShellUtils.executeShellCommand("cd ~");
			ShellUtils.executeShellCommand("bash");
			General.threadSleepInvocation(5000);                     
			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
			General.threadSleepInvocation(5000); 
			ShellUtils.executeShellCommand(0);
			General.threadSleepInvocation(5000); 
			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
			ShellUtils.runAndWriteShellCommandsToFile("./populate.ksh");
			General.threadSleepInvocation(5000);
			Reporter.logEvent(Status.PASS, "Run Populate Batch Job", "Populate Batch Job is Completed", false);
	}
	
	
	public void runCollectorBatch() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Run Collector Batch Job", "Collector Batch Job is Completed", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./collector.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run collector Batch Job", "collector Batch Job is Completed", false);
	}
	
	public String selectPptForAnnualKit() throws SQLException
	{
		String pptId = null;
		queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("getPptFromCommXMLQuery")[1]);
		while(queryResultSet.next())
		{
			pptId = queryResultSet.getString("PARTICIPANTID");
			break;
		}
		
		return pptId;
	}
	
	
	public int getMaxSecAuditId() throws SQLException
	{
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getMaxSecId")[1]);
		int id = 0;
		while(queryResultSet.next())
		{
			id = queryResultSet.getInt("ID");
			break;
		}
		return id;
	}
	
	public void insertMaxRecordtoSecAudit(int number) throws Exception
	{
		
		DB.executeUpdate(Globals.dbNameAlias,Stock.getTestQuery("insertMaxIdintoSecAudit")[1], Integer.toString(number),Stock.getConfigParam(Globals.GC_COLNAME_USERID));
	}
	/**
	 * <pre>Method to select participant having aum balance in easy and get all the properties</pre>
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String>  findPptWithAUMBalance() throws SQLException
	{
		Map<String,String> aumPptMap = new HashMap<>();
		String pptWithAumBal = null;
		String secAuditId = null;
		String gaId = null;
		
	queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getPptWithAumBalQuery")[1]);
	
	while(queryResultSet.next())
	{
		pptWithAumBal = queryResultSet.getString("ind_id");
		secAuditId = queryResultSet.getString("SEC_AUDIT_ID");
		gaId = queryResultSet.getString("ga_id");
		break;
	}
	aumPptMap.put("IND_ID", pptWithAumBal);
	aumPptMap.put("SEC_AUDIT_ID", secAuditId);
	aumPptMap.put("GA_ID", gaId);

	return aumPptMap;
	}
	
	public Date getEnrollmentDateForPptFromPartService(String indId,String ga_id) throws SQLException
	{
		Date enrollDate = null;
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getEnrollmentDateFromPartService")[1],indId,ga_id);
		while(queryResultSet.next())
		{
			enrollDate = queryResultSet.getDate("EFFDATE");
			break;
		}
		return enrollDate;
	}
	
	public Date getEffdateFromSecAudit(String secId) throws SQLException 
	{
		Date effDate = null;
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getEffDateFromSecAudit")[1], secId);
		while(queryResultSet.next())
		{
			effDate = queryResultSet.getDate("EFFDATE");
			break;
		}
		return effDate;
	}
	public static void updateEnrollmentDateforPpt(Date enrollDate , Date effDate) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("")[1]);
	}
	
	
}
