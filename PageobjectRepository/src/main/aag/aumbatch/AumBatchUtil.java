package pageobject.aumbatch;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.sql.rowset.FilteredRowSet;

import lib.DB;
import lib.Reporter;
import lib.Stock;


import shell.utils.ShellUtils;
import aag_lib.General;

import com.aventstack.extentreports.Status;
import com.sun.rowset.FilteredRowSetImpl;

import core.framework.Globals;
import db.filters.EqualsFilter;

public class AumBatchUtil {
	public static int maxId = 0;
	static boolean isExist = false;
	static FilteredRowSet objFilterRecordSet = null;
	// static boolean isCollectorbatchExectued = false;
	public static boolean isPopulatebatchExectued = false;
	static ResultSet queryResultSet;
	public static String effdate=null;

	public static int getMaxSecAuditId(String dbInst)
			throws SQLException {
		
		String dbInstance = Globals.GC_Database_Prefix+""+dbInst;
		Stock.setConfigParam("TARGETDB", dbInstance);
		ResultSet objResultSet = DB.executeQuery("TARGETDB", Stock.getTestQuery("getMaxSecAuditId")[1]);
		while (objResultSet.next()) {
			maxId = objResultSet.getInt("ID");
			break;
		}
		return maxId;
	}
	
	public static void updateAuditStatusToCp(String dbInst)
			throws SQLException {
		
		String dbInstance = Globals.GC_Database_Prefix+""+dbInst;
		Stock.setConfigParam("TARGETDB", dbInstance);
				try {
					DB.executeUpdate("TARGETDB", Stock.getTestQuery("updateAuditToCp")[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	public static Date RandomDategenerate() {

		Calendar cal = Calendar.getInstance();
		Date dMin = cal.getTime();
		cal.add(Calendar.YEAR, 1);
		Date dMax = cal.getTime();

		long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
		GregorianCalendar s = new GregorianCalendar();
		s.setTimeInMillis(dMin.getTime());
		GregorianCalendar e = new GregorianCalendar();
		e.setTimeInMillis(dMax.getTime());

		long endL = e.getTimeInMillis()
				+ e.getTimeZone().getOffset(e.getTimeInMillis());
		long startL = s.getTimeInMillis()
				+ s.getTimeZone().getOffset(s.getTimeInMillis());
		long dayDiff = (endL - startL) / MILLIS_PER_DAY;

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dMin);
		cal1.add(Calendar.DATE, new Random().nextInt((int) dayDiff));
		return cal1.getTime();
	}

	public static String getUniquePastDate(ResultSet objResultSet)
			throws SQLException {
		Calendar cal = Calendar.getInstance();
        Date dMin = cal.getTime();
        cal.add(Calendar.MONTH, -3);
        Date dMax = cal.getTime();
        Date date = null;
//        int i=0;
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MMM-dd");
//		try {
//			date = fmt.parse("2017-AUG-01");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		do {
//			System.out.println(i++);
			objFilterRecordSet = new FilteredRowSetImpl();
			objFilterRecordSet.populate(objResultSet);
			date = generate(dMin, dMax);
			EqualsFilter equalFilter = new EqualsFilter(date, "EFFDATE");
			objFilterRecordSet.setFilter(equalFilter);

		} while (DB.getFilteredRowSetCount(objFilterRecordSet) > 0);
	//} while (DB.getRecordSetCount(objFilterRecordSet) > 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		return sdf.format(date);

	}

	public static boolean checkForSuccessfulBatchRun(String id) {
		queryResultSet = DB.executeQuery("AUMDB",
				Stock.getTestQuery("checkPopulate")[1], id);

		return DB.getRecordSetCount(queryResultSet) > 0;

	}

	public static void runPopulateBatch() {
		Reporter.logEvent(Status.INFO, "Run populate batch",
				"Run populate Batch", false);
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000);
		ShellUtils
		.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils
		.runAndWriteShellCommandsToFile("/opt/isis/solaris/qa_test/solaris/fss_batch_java/managedAccounts/scripts/sec_audit_populate.csh 3 inst");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run Populate Job",
				"Populate Job is Completed", false);
		if (checkForSuccessfulBatchRun(Integer.toString(maxId))) {
			isPopulatebatchExectued = true;
		}
	}

	public static void runCollectorBatch() {
		Reporter.logEvent(Status.INFO, "Run populate batch",
				"Run populate Batch", false);
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000);
		ShellUtils
		.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils
		.runAndWriteShellCommandsToFile("/opt/isis/solaris/qa_test/solaris/fss_batch_java/managedAccounts/scripts/sec_audit_collector.csh 3 inst");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run Collector Job",
				"Collector Job is Completed", false);

	}

	public static Date generate(Date dMin, Date dMax) {
		long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
		GregorianCalendar s = new GregorianCalendar();
		s.setTimeInMillis(dMin.getTime());
		GregorianCalendar e = new GregorianCalendar();
		e.setTimeInMillis(dMax.getTime());

		long endL = e.getTimeInMillis()
				+ e.getTimeZone().getOffset(e.getTimeInMillis());
		long startL = s.getTimeInMillis()
				+ s.getTimeZone().getOffset(s.getTimeInMillis());
		long dayDiff = (startL - endL) / MILLIS_PER_DAY;

		Calendar cal = Calendar.getInstance();
		cal.setTime(dMax);
		cal.add(Calendar.DAY_OF_YEAR, new Random().nextInt((int) dayDiff));
		return cal.getTime();
	}

}

