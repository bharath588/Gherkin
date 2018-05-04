package aag.batchsync;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.apache.commons.io.FileUtils;

import shell.utils.ShellUtils;
import aag.aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class BatchsynUtils {
	ResultSet queryResultset;
	
	public void runBatchsync() throws IOException {
		Reporter.logEvent(Status.INFO, "Run BatchSync", "Run Batchsync", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000);
		ShellUtils
				.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./batchsync.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run BatchSync Job",
				"BatchSync Job is Completed", false);
	}

	public boolean checkGaServiceFe(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForFinEngine")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >=1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkGaServiceIb(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForMA")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >=1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
		public boolean checkGrpdefinv (String gaId) {
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("grpdefCheck")[1], gaId);

			if (DB.getRecordSetCount(queryResultset) >=1) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean checkGrpdefinv105 (String gaId) {
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("grpdefCheck105")[1], gaId);

			if (DB.getRecordSetCount(queryResultset) >=1) {
				return true;
			} else {
				return false;
			}
		}
		
		public String getPptFe(String gaId) throws Exception {
			String indId = null;
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("getBatchPpt")[1], gaId);
			

			while (queryResultset.next()) {
				indId = queryResultset.getString("IND_ID");
				break;
			}

			return indId;
		}
		
		
		public String getIDpart(String gaId) throws Exception {
			String indId = null;
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("getBatchIDPpt")[1], gaId);
			

			while (queryResultset.next()) {
				indId = queryResultset.getString("IND_ID");
				break;
			}

			return indId;
		}
		
		
		public boolean checkpartserv (String gaId) {
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("checkPartServ")[1], gaId);

			if (DB.getRecordSetCount(queryResultset) >=1) {
				return true;
			} else {
				return false;
			}
		}
		
		public void updatePartServiceRecords(String indId) throws Exception {
			DB.executeUpdate(Globals.dbNameAlias,
					Stock.getTestQuery("updateQdiaPartService")[1], indId);
		}
		
		public String getPptStatus(String IndId) throws Exception {
			String statuscode = null;
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("getpartServiceRecords")[1],IndId);
			System.out.println("");

			while (queryResultset.next()) {
				statuscode = queryResultset.getString("STATUS_CODE");
				break;
			}

			return statuscode;
		}
		
		public boolean checkeffdate (String IndId) {
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("checkeffdate")[1], IndId);

			if (DB.getRecordSetCount(queryResultset) >0) {
				return false;
			} else {
				return true;
			}
		}
		
		public boolean checkisisfeed (String IndId) {
			queryResultset = DB.executeQuery(Globals.dbNameAlias,
					Stock.getTestQuery("checkisisfeed")[1], IndId);

			if (DB.getRecordSetCount(queryResultset) >0) {
				return false;
			} else {
				return true;
			}
		}
		
	}

