package lib;


import java.io.File;
import java.util.Random;

import com.google.common.base.Throwables;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.GridType;
import com.relevantcodes.extentreports.LogStatus;

import core.framework.Globals;

public class Reporter {

	public static String strLogFolderPath;
	
//	private static String currIterationStatus;
//	private static final String NOT_COMPLETE = "NOT COMPLETE";
//	private static final String PASS = "PASS";
//	private static final String FAIL = "FAIL";
	private static int iRandTraceCntr = 0;
	
	public static ExtentReports objReport;
	public enum Status {
		PASS, FAIL, WARNING, INFO
	}
	
	/**  
	 *<pre> Method to initiate 
	 * 1) currently running test suite name (Used in HTML Report)
	 * 2) Set users' decision of overwriting existing HTML report for the class
	 * 3) Initiate reporter object for the module/class</pre>
	 * 
	 * @param className - <pre><b>Ex:</b>	this.getClass().getName()</pre> 
	 */
	public static void initializeModule(String className) {
		if (!Globals.GBL_SuiteName.equalsIgnoreCase(className)) {
			Globals.GBL_REPLACE_EXISTING_HTML_REPORT = Stock.getConfigParam("Overwrite_Existing_Report");
			Globals.GBL_SuiteName = className;
			
			//Initialize reporter object
			String reportFilePath = Globals.GC_TEST_REPORT_DIR 
					+ Globals.GBL_SuiteName + ".html";
			
			Reporter.objReport = ExtentReports.get(Reporter.class);
			if(!new File(Globals.GC_TEST_REPORT_DIR).exists()){
				new File(Globals.GC_TEST_REPORT_DIR).mkdir();
			}
			
			Reporter.objReport.init(reportFilePath, 
					Boolean.parseBoolean(Globals.GBL_REPLACE_EXISTING_HTML_REPORT), 
					DisplayOrder.BY_OLDEST_TO_LATEST, 
					GridType.MASONRY);
			
			Globals.GBL_REPLACE_EXISTING_HTML_REPORT = "false";
			
			Reporter.objReport.config().documentTitle(Globals.GBL_SuiteName + ": Summary Report");
			Reporter.objReport.config().reportHeadline("Report options");
			Reporter.objReport.config().reportTitle("Execution summary report for [" + Globals.GBL_SuiteName + "]");
			Reporter.objReport.config().displayCallerClass(false);
			Reporter.objReport.config().useExtentFooter(false);
			Reporter.objReport.config().setImageSize("10%");
		}
	}
	
	/**<pre> Method to initialize HTML Report and Local Results spreadsheet
	 * This method has to be called before initiating each test iteration
	 * 
	 * Actions performed:
	 * 	1. initialize HTML reporter object by
	 * 		a. Passing test case name,
	 * 		b. Sorting order as 'Oldest to Latest'
	 * 		c. Report type as 'Masonry'
	 * 	2. Configure document title
	 * 	3. Configure report head line
	 * 	4. Configure report title
	 * 	5. Configure displaying caller class option to false
	 * 	6. Configure displaying footer section option to false
	 * 	7. Configure screen shot size to 10%
	 * 	8. Start test case in HTML report
	 * 	9. Set test iteration status to NOT_COMPLETE
	 * 	10. Call method to log pre-execution details to spreadsheet report file</pre>
	 * 
	 * @param testCaseName
	 * @throws Exception
	 */
	public static void initializeReportForTC(int currentIterationNumber, String testCaseName) throws Exception {
		Globals.GBL_TestCaseName = testCaseName;
		Globals.GBL_CurrentIterationNumber = currentIterationNumber;
		
		String tmpStr1 = "", tmpStr2 = "", tmpStr3="";
//		String reportFilePath = Globals.GC_TEST_REPORT_DIR 
//				+ Globals.GBL_SuiteName + ".html";
//		
//		Reporter.objReport = ExtentReports.get(Reporter.class);
//		if(!new File(Globals.GC_TEST_REPORT_DIR).exists()){
//			new File(Globals.GC_TEST_REPORT_DIR).mkdir();
//		}
//		
//		Reporter.objReport.init(reportFilePath, 
//				Boolean.parseBoolean(Globals.GBL_REPLACE_EXISTING_HTML_REPORT), 
//				DisplayOrder.BY_OLDEST_TO_LATEST, 
//				GridType.MASONRY);
//		
//		Globals.GBL_REPLACE_EXISTING_HTML_REPORT = "false";
//		
//		Reporter.objReport.config().documentTitle(Globals.GBL_SuiteName + ": Summary Report");
//		Reporter.objReport.config().reportHeadline("Report options");
//		Reporter.objReport.config().reportTitle("Execution summary report for [" + Globals.GBL_SuiteName + "]");
//		Reporter.objReport.config().displayCallerClass(false);
//		Reporter.objReport.config().useExtentFooter(false);
//		Reporter.objReport.config().setImageSize("10%");
		
		if (testCaseName.length() > 50) {
			tmpStr1 = (String) testCaseName.subSequence(0, 50);
			if (testCaseName.length() > 100) {
				tmpStr2 = (String) testCaseName.subSequence(50, 100);
				tmpStr3 = (String) testCaseName.subSequence(100, testCaseName.length());
			} else {
				tmpStr2 = (String) testCaseName.subSequence(50, testCaseName.length());
			}
		} else {
			tmpStr1 = testCaseName;
		}
		
		Reporter.objReport.startTest(tmpStr1 + 
				(tmpStr2.length() > 0 ? ("<br>     " + tmpStr2) : "") + 
				(tmpStr3.length() > 0 ? ("<br>     " + tmpStr3) : "") + 
				"<br>Iteration " + Globals.GBL_CurrentIterationNumber);
		
		//Reporter.currIterationStatus = Reporter.NOT_COMPLETE;
	}
	
	/**<pre> Method to log steps details to HTML report
	 * Following options are provided
	 * 	1. Log step with detials: Step status, Step name, Step Details and choice to capture screenshot for the step
	 * 	2. Log step with details: Step status, Step name and step details
	 * 	3. Log step with only screenshot
	 * 	4. Log step with screenshot and step details without status
	 * 	5. Include stack trace if step status is either FAIL or WARNING</pre>
	 * 
	 * @param logStatus - Status for the event being logged. <b>Note:</b> logStatus is ignored while logging attachment only steps
	 * @param Step
	 * @param Details
	 * @param attachScreenshot - <b>true</b> to attach screenshot. <b>false</b> otherwise.
	 */
	public static void logEvent(Reporter.Status logStatus, String Step, String Details, boolean attachScreenshot) {
		if (Step.trim().length() == 0 && Details.trim().length() == 0 && attachScreenshot) {
			Reporter.objReport.attachScreenshot(Web.captureScreenshot());
		} else if (Step.trim().length() == 0 && attachScreenshot) {
			Reporter.objReport.attachScreenshot(Web.captureScreenshot(), Details);
		} else {
			LogStatus tmpLogStatus;
			String stackTrace = "";
			
			if (Globals.exception != null) {
				stackTrace = Throwables.getStackTraceAsString(Globals.exception);
				Globals.exception = null;
			} else if (Globals.assertionerror != null) {
				stackTrace = Throwables.getStackTraceAsString(Globals.assertionerror);
				Globals.assertionerror = null;
			} else
				stackTrace = Throwables.getStackTraceAsString(new Throwable());
			
			iRandTraceCntr = new Random().nextInt(10000);
			String stackTraceLnk = "<br><a href=\"#div" + iRandTraceCntr + "\" onclick=\"var el=getElementById('div" + iRandTraceCntr + "');" +
					"(el.style.display=='none')? (el.style.display='block') : (el.style.display='none'); " +
					"(this.text == '+ Show stack trace')? " +
					"(this.text='- Hide stack trace') : (this.text='+ Show stack trace'); this.style.fontSize='small'\"><font size='1'>+ Show stack trace</font></a>" +
					"<div id=\"div" + iRandTraceCntr + "\" style=\"display:none\"><pre>" + stackTrace + "</pre></div>";
			
			switch (logStatus) {
			case PASS:
				tmpLogStatus = LogStatus.PASS;
				Details = "<font size=\"3\" color=\"green\"><pre>" + Details + "</pre></font>";
				break;
			case FAIL:
				tmpLogStatus = LogStatus.FAIL;
				
				Details = "<font size=\"3\" color=\"red\"><pre>" + Details + "</pre></font>";
				Details += stackTraceLnk;
				break;
			case WARNING:
				tmpLogStatus = LogStatus.WARNING;
				Details = "<font size=\"3\" color=\"amber\"><pre>" + Details + "</pre></font>";
				Details += stackTraceLnk;
				break;
			case INFO:
				tmpLogStatus = LogStatus.INFO;
				Details = "<pre>" + Details + "</pre>";
				break;
				default:
					tmpLogStatus = LogStatus.WARNING;
					Details = "<pre>" + Details + "</pre>";
					Details += stackTraceLnk; 
			}
			
			if (attachScreenshot) {
				Reporter.objReport.log(tmpLogStatus, Step, Details, Web.captureScreenshot());
			} else {
				Reporter.objReport.log(tmpLogStatus, Step, Details);
			}
		}
		
//		if (logStatus == Status.FAIL)
//			Reporter.currIterationStatus = "FAIL";
	}
	
	/**<pre> Method to finalize HTML Report and local results spreadsheet
	 * This method has to be called at the end of every test iteration
	 * 	1. End test case in HTML report
	 * 	2. Call method to log post execution details to local results spreadsheet</pre>
	 * 
	 * @throws Exception
	 */
	public static void finalizeTCReport () throws Exception {
		Reporter.objReport.endTest();
	}
	

}