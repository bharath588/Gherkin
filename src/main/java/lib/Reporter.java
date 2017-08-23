package lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import mobile.IOSDeviceConfiguration;
import mobile.IOSDriverManager;
import mobile.Mobile;
import mobile.Reader;
import shell.utils.SftpUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Throwables;

import core.framework.Globals;
import core.framework.TestListener;
/**
 * Reporter - Class contains methods to initialize and log report events
 * @author vrjnyk
 *
 */
public class Reporter{
	
	public static Map<Long,ExtentTest> testCaseMap = new LinkedHashMap<>();
	public static String strLogFolderPath;
	public static ExtentTest objTestCaseNode = null;
	private static int iRandTraceCntr = 0;
	public static ExtentTest objModuleNameNode = null;
	public static Map<Long,String> moduleRefMap = new LinkedHashMap<Long,String>();
	public static ExtentReports objReport;
	public static Map<Long,ExtentTest> machineDetMap = new LinkedHashMap<Long, ExtentTest>();
	public static ExtentTest objMachineDetNode;
	public static Map<String,ExtentTest> moduleNameMap = new HashMap<String, ExtentTest>();
	

	private static Map<Long,Boolean> checkTestStatusMap = new LinkedHashMap<Long, Boolean>();
	static{
	checkTestStatusMap.put(Thread.currentThread().getId(), true);
	}

	 public static boolean isCheckTestStatus() {
		 return checkTestStatusMap.get(Thread.currentThread().getId());
		 }
		
		 public static void setCheckTestStatus(boolean checkTestStatus) {
			 checkTestStatusMap.put(Thread.currentThread().getId(),checkTestStatus);
		 }
	/**
	 * <pre>
	 * Method to initiate 
	 * 1) currently running test suite name (Used in HTML Report)
	 * 2) Set users' decision of overwriting existing HTML report for the class
	 * 3) Initiate reporter object for the module/class
	 * </pre>
	 * 
	 * @param className
	 *            -
	 * 
	 *            <pre>
	 * <b>Ex:</b>           this.getClass().getName()
	 * </pre>
	 */
	public static synchronized void initializeModule(String className) {

			Globals.GBL_REPLACE_EXISTING_HTML_REPORT = Stock
					.getConfigParam("Overwrite_Existing_Report");
			Globals.GBL_SuiteName = className;

			// Initialize reporter object
		 String reportFilePath = Globals.GC_TEST_REPORT_DIR+Stock.getConfigParam("AUT").toUpperCase()
			 + ".html";

			 if(!new File(Globals.GC_TEST_REPORT_DIR).exists()){
			 new File(Globals.GC_TEST_REPORT_DIR).mkdir();
			 }
			
			 if(objReport == null)
			 {
		objReport = new ExtentReports();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
		//Option for appending on the existing reporter
		
		if(Globals.GBL_REPLACE_EXISTING_HTML_REPORT.equalsIgnoreCase("false"))
		{
			htmlReporter.setAppendExisting(true);
		}
		
        htmlReporter.config().setDocumentTitle(Stock.getConfigParam("AUT")+"Reports");
        htmlReporter.config().setReportName(Stock.getConfigParam("AUT")+"Reports");
		objReport.attachReporter(htmlReporter);

			 }
		//Added by Siddartha 
			 objReport.setSystemInfo("PLATFORM", Stock.getConfigParam("PLATFORM"));
			objReport.setSystemInfo("Environment", Stock.getConfigParam("TEST_ENV"));
			 objReport.setSystemInfo("Selenium Version", "3.0.1");
			 objReport.setSystemInfo("Type ", Stock.getConfigParam("type"));
			 String deviceName ="";
			if(Mobile.mobilePlatform){
				 objReport.setSystemInfo("IOS Version", "10.0.2");
				 objReport.setSystemInfo("Device UDID ", IOSDriverManager.deviceName);	
				
				try {
					deviceName = IOSDeviceConfiguration.getDeviceName(IOSDriverManager.deviceName);
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 objReport.setSystemInfo("Device Name ",deviceName );	
				
				
			 }
			if(moduleNameMap == null || !moduleNameMap.containsKey(className))
			{
				
				objModuleNameNode = objReport.createTest(className).assignCategory(className);
				moduleNameMap.put(className, objModuleNameNode);
			}
			 
			 if(machineDetMap == null || machineDetMap.get(Thread.currentThread().getId()) == null || !moduleRefMap.get(Thread.currentThread().getId()).equalsIgnoreCase(className))
			 {

			if(Stock.getConfigParam("type").equalsIgnoreCase("grid"))
			{
			objMachineDetNode = moduleNameMap.get(className).createNode(SftpUtils.getHostname(TestListener.portMap.get(Thread.currentThread().getId())));
			}else{
				if(Mobile.mobilePlatform){
					String deviceInfo="";					
						deviceInfo = deviceName+"_"+IOSDriverManager.deviceName;
				
					objMachineDetNode = moduleNameMap.get(className).createNode(deviceInfo).assignCategory("Machine Details");
					
				}else{
					objMachineDetNode = moduleNameMap.get(className).createNode(SftpUtils.getHostname()).assignCategory("Machine Details");
				}
				}
			machineDetMap.put(Thread.currentThread().getId(), objMachineDetNode);
			moduleRefMap.put(Thread.currentThread().getId(), className);
			
			 } 
		}
	

	/**
	 * <pre>
	 * Method to initialize HTML Report and Local Results spreadsheet
	 * This method has to be called before initiating each test iteration
	 * 
	 * Actions performed:
	 *            1. initialize HTML reporter object by
	 *                            a. Passing test case name,
	 *                            b. Sorting order as 'Oldest to Latest'
	 *                            c. Report type as 'Masonry'
	 *            2. Configure document title
	 *            3. Configure report head line
	 *            4. Configure report title
	 *            5. Configure displaying caller class option to false
	 *            6. Configure displaying footer section option to false
	 *            7. Configure screen shot size to 10%
	 *            8. Start test case in HTML report
	 *            9. Set test iteration status to NOT_COMPLETE
	 *            10. Call method to log pre-execution details to spreadsheet report file
	 * </pre>
	 * 
	 * @param testCaseName
	 * @throws Exception
	 */
	public static synchronized void initializeReportForTC(int currentIterationNumber,
			String testCaseName,String... description) throws Exception {
		testCaseName = Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId());
		Globals.GBL_CurrentIterationNumber = currentIterationNumber;
		String tmpStr1 = "", tmpStr2 = "", tmpStr3 = "";
		
		if (testCaseName.length() > 50) {
			tmpStr1 = (String) testCaseName.subSequence(0, 50);
			if (testCaseName.length() > 100) {
				tmpStr2 = (String) testCaseName.subSequence(50, 100);
				tmpStr3 = (String) testCaseName.subSequence(100,
						testCaseName.length());
			} else {
				tmpStr2 = (String) testCaseName.subSequence(50,
						testCaseName.length());
			}
		} else {
			tmpStr1 = testCaseName;
		}
		
	     
		if(description.length > 0)
		{
			objTestCaseNode = machineDetMap.get(Thread.currentThread().getId()).createNode(tmpStr1
					+ (tmpStr2.length() > 0 ? ("<br>     " + tmpStr2) : "")
					+ (tmpStr3.length() > 0 ? ("<br>     " + tmpStr3) : "")
					+ "<br>Iteration " + Globals.GBL_CurrentIterationNumber,description[0]);
			
			
			testCaseMap.put(Thread.currentThread().getId(), objTestCaseNode);
		}else{
		objTestCaseNode = machineDetMap.get(Thread.currentThread().getId()).createNode(tmpStr1
				+ (tmpStr2.length() > 0 ? ("<br>     " + tmpStr2) : "")
				+ (tmpStr3.length() > 0 ? ("<br>     " + tmpStr3) : "")
				+ "<br>Iteration " + Globals.GBL_CurrentIterationNumber).assignCategory("TestCases");
		
		
		testCaseMap.put(Thread.currentThread().getId(), objTestCaseNode);
		}
		
		
		
		
		
		//Categories can be assigned to the extent test object
		//checkTestStatus = true;
		checkTestStatusMap.put(Thread.currentThread().getId(), true);
	}
	
	

	/**
	 * <pre>
	 * Method to log steps details to HTML report
	 * Following options are provided
	 *            1. Log step with detials: Step status, Step name, Step Details and choice to capture screenshot for the step
	 *            2. Log step with details: Step status, Step name and step details
	 *            3. Log step with only screenshot
	 *            4. Log step with screenshot and step details without status
	 *            5. Include stack trace if step status is either FAIL or WARNING
	 * </pre>
	 * 
	 * @param logStatus
	 *            - Status for the event being logged. <b>Note:</b> logStatus is
	 *            ignored while logging attachment only steps
	 * @param Step
	 * @param Details
	 * @param attachScreenshot
	 *            - <b>true</b> to attach screenshot. <b>false</b> otherwise.
	 */
	public static synchronized void logEvent(Status logStatus, String Step,
			String Details, boolean attachScreenshot) {
		
		  Globals.GC_CAPTURE_SCREENSHOT = Stock
		  .getConfigParam("CAPTURESCREENSHOT");
		 
		Details = Details.replaceAll("&", "&amp;").replaceAll(">", "&gt;")
				.replaceAll("<", "&lt;");
		if (Step.trim().length() == 0 && Details.trim().length() == 0
				&& attachScreenshot) {
			Web.captureScreenshot();
		} else if (Step.trim().length() == 0 && attachScreenshot) {
			Web.captureScreenshot();
			try {
				testCaseMap.get(Thread.currentThread().getId()).addScreenCaptureFromPath(Web.captureScreenshot());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Status tmpLogStatus;
			String stackTrace = "";
			if (Globals.exception != null) {
				stackTrace = Throwables
						.getStackTraceAsString(Globals.exception);
				Globals.exception = null;
			} else if (Globals.assertionerror != null) {
				stackTrace = Throwables
						.getStackTraceAsString(Globals.assertionerror);
				Globals.assertionerror = null;
			} else
				stackTrace = Throwables.getStackTraceAsString(new Throwable());
			
			stackTrace=stackTrace.replace("\n", "").replace("\r", "");

			iRandTraceCntr = new Random().nextInt(10000);
			String stackTraceLnk = "<br>"
					+"<a href=\"#\" onclick=\"var el=getElementById('div"
					+ iRandTraceCntr
					+ "');  alert('"+stackTrace+"');"
					+ "\"><font size='1'>+ Show stack trace</font></a>"
					+ "<div id=\"div" + iRandTraceCntr
					+ "\" style=\"display:none\">" + stackTrace
					+ "</div>";
			
			
			Details = "<table style border = \"1px\" >"
					  +"<tr> <th>Step</th><th>Details</th>" 
					  +"</tr>"
					  +"<tr> <td  border = \"1px\">"+Step+"</td> <td word-wrap:break:word>"+Details+"</td></tr>"
					+"</table>";
			
			
			switch (logStatus) {
			case PASS:
				tmpLogStatus = Status.PASS;
				Details = "<font size=\"3\" color=\"green\"><pre>" + Details
						+ "</pre></font>";
				break;
			case FAIL:
				tmpLogStatus = Status.FAIL;
				Details = "<font size=\"3\" color=\"red\"><pre>" + Details
						+ "</pre></font>";
				checkTestStatusMap.put(Thread.currentThread().getId(), false);
				Details += stackTraceLnk;
			//	Mobile.reportStatus = true;
				break;
			case WARNING:
				tmpLogStatus = Status.WARNING;
				Details = "<font size=\"3\" color=\"amber\"><pre>" + Details
						+ "</pre></font>";
				Details += stackTraceLnk;
				break;
			case INFO:
				tmpLogStatus = Status.INFO;
				Details = "<pre>" + Details + "</pre>";
				break;
			default:
				tmpLogStatus = Status.WARNING;
				Details = "<pre>" + Details + "</pre>";
				Details += stackTraceLnk;
			}


			if(Globals.exception != null)
			{
				
			Globals.exception = null;
			}
			if(Globals.error !=null)
			{	
				
			
				Globals.error = null;
			}
			try{
			if (isScreenshotRequired(tmpLogStatus, attachScreenshot)) {
			testCaseMap.get(Thread.currentThread().getId()).log(tmpLogStatus, Details);
		
				//testCaseMap.get(Thread.currentThread().getId()).info("Screenshot below:").addScreenCaptureFromPath(Web.captureScreenshot());
				
				testCaseMap.get(Thread.currentThread().getId()).
				info("Screenshot Below : ", MediaEntityBuilder.createScreenCaptureFromPath(Web.captureScreenshot()).build());
			
			} else {
				testCaseMap.get(Thread.currentThread().getId()).log(tmpLogStatus, Details);
				
				if(Globals.exception != null)
				{
				objTestCaseNode.log(Status.FAIL, Globals.exception);
				objTestCaseNode.fail(Globals.exception);
				Globals.exception = null;
				testCaseMap.get(Thread.currentThread().getId()).
				info("Screenshot Below : ", MediaEntityBuilder.createScreenCaptureFromPath(Web.captureScreenshot()).build());
				}
				if(Globals.error !=null)
				{
					objTestCaseNode.log(Status.FAIL, Globals.error);
					objTestCaseNode.fail(Globals.error);
					Globals.error = null;
					
						testCaseMap.get(Thread.currentThread().getId()).
						info("Screenshot Below : ", MediaEntityBuilder.createScreenCaptureFromPath(Web.captureScreenshot()).build());
				}
			}
		}catch(Exception e)
			{
			objTestCaseNode.log(Status.FAIL, e);
			e.printStackTrace();
			}
		}
	}
/**
 * Method to capture screen shot
 * @param logStatus
 * @param attachScreenshot
 * @return
 */
	public static boolean isScreenshotRequired(Status logStatus,
			boolean attachScreenshot) {
		boolean isRequired = false;

		if (Globals.GC_CAPTURE_SCREENSHOT == null
				|| Globals.GC_CAPTURE_SCREENSHOT.isEmpty()) {
			Globals.GC_CAPTURE_SCREENSHOT = "AS_USER_DEFINED";
			isRequired = attachScreenshot;
			
		}

		if (!Globals.GC_CAPTURE_SCREENSHOT
				.equalsIgnoreCase(Globals.option_Never)) {
			if (Globals.GC_CAPTURE_SCREENSHOT
					.equalsIgnoreCase(Globals.option_Always)
					|| (Globals.GC_CAPTURE_SCREENSHOT
							.equalsIgnoreCase(Globals.option_OnFailure) && logStatus
							.name().equalsIgnoreCase("FAIL"))
					|| (Globals.GC_CAPTURE_SCREENSHOT
							.equalsIgnoreCase(Globals.option_UserDefault) && attachScreenshot)) {
				isRequired = true;
			}
		}

		return isRequired;
	}

	/**
	 * <pre>
	 * Method to finalize HTML Report and local results spreadsheet
	 * This method has to be called at the end of every test iteration
	 *            1. End test case in HTML report
	 *            2. Call method to log post execution details to local results spreadsheet
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public static synchronized void finalizeTCReport() throws Exception {
		// Checking final report status
		if (!checkTestStatusMap.get(Thread.currentThread().getId())) {
			TestListener.setFinalTestStatus(false);
		}else{
			TestListener.setFinalTestStatus(true);
		}
		if(Mobile.mobilePlatform){
			if(Reader.getConfigParam("APPIUMSEVER").equalsIgnoreCase("PROGRAM")){
			objModuleNameNode.log(Status.INFO, "<a target=\"_parent\" href="
	                    + IOSDriverManager.getDeviceName().replaceAll("\\W", "_") + "__AppiumLog.txt"+ ">AppiumServerLogs</a>");
//			Reporter.parent.log(LogStatus.INFO,
//	                "<a target=\"_parent\" href="
//	                    + IOSDriverManager.getDeviceName().replaceAll("\\W", "_") + "__AppiumLog.txt"+ ">AppiumServerLogs</a>");
		}
	}
	}

	
	
   

}

	

