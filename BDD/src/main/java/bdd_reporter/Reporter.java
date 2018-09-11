package bdd_reporter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import bdd_core.framework.Globals;
import bdd_core.framework.TestListener;
import bdd_lib.Web;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Throwables;

public class Reporter {
	public static String strLogFolderPath;
	public static ExtentTest objTestCaseNode = null;
	private static int iRandTraceCntr = 0;
	public static ExtentTest objModuleNameNode = null;
	public static Map<Long, String> moduleRefMap = new LinkedHashMap<Long, String>();
	public static ExtentReports objReport;
	public static String featureName = null;

	static Properties objProperties;

	//private static Map<Long,Boolean> checkTestStatusMap = new LinkedHashMap<Long, Boolean>();
	private static Map<Long,Map<String,Boolean>> checkTestStatusMap = new LinkedHashMap<Long, Map<String,Boolean>>();
	static{
		Map<String,Boolean> tempStatus = new LinkedHashMap<String,Boolean>();
		tempStatus.put("Status", true);
		tempStatus.put("hasMinStepsReported", false);
		checkTestStatusMap.put(Thread.currentThread().getId(), tempStatus);
	}

	public static boolean isCheckTestStatus() {
		return checkTestStatusMap.get(Thread.currentThread().getId()).get("Status");
	}

	public static void setCheckTestStatus(boolean checkTestStatus) {
		Map<String,Boolean> tempStatus = checkTestStatusMap.get(Thread.currentThread().getId());
		tempStatus.put("Status", checkTestStatus);
		checkTestStatusMap.put(Thread.currentThread().getId(),tempStatus);
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

		/*
		 * Globals.GBL_REPLACE_EXISTING_HTML_REPORT = Stock
		 * .getConfigParam("Overwrite_Existing_Report");
		 */
		// Globals.GBL_SuiteName = className;

		// Initialize reporter object
		String GC_TEST_REPORT_DIR = System.getProperty("user.dir")+ "\\TestReport" + File.separator;
		String reportFilePath = GC_TEST_REPORT_DIR+ "BDD.html";

		if (!new File(GC_TEST_REPORT_DIR).exists()) {
			new File(GC_TEST_REPORT_DIR).mkdir();
		}

		if (objReport == null) {
			objReport = new ExtentReports();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					reportFilePath);
			// Option for appending on the existing reporter

			/*if (Globals.GBL_REPLACE_EXISTING_HTML_REPORT
					.equalsIgnoreCase("false")) {
				htmlReporter.setAppendExisting(true);
			}*/

			htmlReporter.config().setDocumentTitle(
					"BDD Sample" + "Reports");
			htmlReporter.config().setReportName(
					"BDD Sample"+ "Reports");
			objReport.attachReporter(htmlReporter);

		}
		/*
		 * //Added by Siddartha objReport.setSystemInfo("PLATFORM",
		 * Stock.getConfigParam("PLATFORM"));
		 * objReport.setSystemInfo("Environment",
		 * Stock.getConfigParam("TEST_ENV"));
		 * objReport.setSystemInfo("Selenium Version", "3.0.1");
		 * objReport.setSystemInfo("Type ", Stock.getConfigParam("type"));
		 */

		if(objModuleNameNode==null || !featureName.equalsIgnoreCase(className)){
			objModuleNameNode = objReport.createTest(className).assignCategory(className);
		}
		featureName=className;
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
	public static synchronized void initializeReportForTC(
			int currentIterationNumber, String testCaseName,
			String... description) throws Exception {
		//Globals.GBL_TestCaseName = testCaseName;
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

		if (description.length > 0) {
			objTestCaseNode = objModuleNameNode
					.createNode(
							tmpStr1
							+ (tmpStr2.length() > 0 ? ("<br>     " + tmpStr2)
									: "")
									+ (tmpStr3.length() > 0 ? ("<br>     " + tmpStr3)
											: "") + "<br>Iteration "
											+ currentIterationNumber,
											description[0]);


		} else {
			objTestCaseNode = objModuleNameNode
					.createNode(
							tmpStr1
							+ (tmpStr2.length() > 0 ? ("<br>     " + tmpStr2)
									: "")
									+ (tmpStr3.length() > 0 ? ("<br>     " + tmpStr3)
											: "") + "<br>Iteration "
											+ currentIterationNumber)
											.assignCategory("TestCases");
		}

		//checkTestStatusMap.put(Thread.currentThread().getId(), true);
		setCheckTestStatus(true);
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
	 * @throws IOException 
	 */
	public static synchronized void logEvent(Status logStatus, String Step,
			String Details, boolean attachScreenshot)  {

		//Globals.GC_CAPTURE_SCREENSHOT = getConfigParam("CAPTURESCREENSHOT");

		Details = Details.replaceAll("&", "&amp;").replaceAll(">", "&gt;")
				.replaceAll("<", "&lt;");
		if (Step.trim().length() == 0 && Details.trim().length() == 0
				&& attachScreenshot) {
			captureScreenshot();
		} else if (Step.trim().length() == 0 && attachScreenshot) {
			captureScreenshot();
			try {
				objTestCaseNode
				.addScreenCaptureFromPath(captureScreenshot());
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

			stackTrace = stackTrace.replace("\n", "").replace("\r", "");

			iRandTraceCntr = new Random().nextInt(10000);
			String stackTraceLnk = "<br>"
					+ "<a href=\"#\" onclick=\"var el=getElementById('div"
					+ iRandTraceCntr + "');  alert('" + stackTrace + "');"
					+ "\"><font size='1'>+ Show stack trace</font></a>"
					+ "<div id=\"div" + iRandTraceCntr
					+ "\" style=\"display:none\">" + stackTrace + "</div>";

			Details = "<table style border = \"1px\" >"
					+ "<tr> <th>Step</th><th>Details</th>" + "</tr>"
					+ "<tr> <td  border = \"1px\">" + Step
					+ "</td> <td word-wrap:break:word>" + Details
					+ "</td></tr>" + "</table>";

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
				//checkTestStatusMap.put(Thread.currentThread().getId(), false);
				setCheckTestStatus(false);
				Details += stackTraceLnk;
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

			if (Globals.exception != null) {

				Globals.exception = null;
			}
			if (Globals.error != null) {

				Globals.error = null;
			}
			try {
				if (isScreenshotRequired(tmpLogStatus, attachScreenshot)) {
					objTestCaseNode.log(
							tmpLogStatus, Details);

					// testCaseMap.get(Thread.currentThread().getId()).info("Screenshot below:").addScreenCaptureFromPath(Web.captureScreenshot());

					objTestCaseNode.info(
							"Screenshot Below : ",
							MediaEntityBuilder.createScreenCaptureFromPath(
									captureScreenshot()).build());

				} else {
					objTestCaseNode.log(
							tmpLogStatus, Details);

					if (Globals.exception != null) {
						objTestCaseNode.log(Status.FAIL, Globals.exception);
						objTestCaseNode.fail(Globals.exception);
						Globals.exception = null;
						objTestCaseNode.info(
								"Screenshot Below : ",
								MediaEntityBuilder.createScreenCaptureFromPath(
										captureScreenshot()).build());
					}
					if (Globals.error != null) {
						objTestCaseNode.log(Status.FAIL, Globals.error);
						objTestCaseNode.fail(Globals.error);
						Globals.error = null;

						objTestCaseNode.info(
								"Screenshot Below : ",
								MediaEntityBuilder.createScreenCaptureFromPath(
										captureScreenshot()).build());
					}
				}
			} catch (Exception e) {
				objTestCaseNode.log(Status.FAIL, e);
				e.printStackTrace();
			}
		}
	}

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


	public static String captureScreenshot() {
		String fileName = null;
		try {
			Globals.GBL_strScreenshotsFolderPath = Globals.GC_TEST_REPORT_DIR
					+ Globals.GBL_TestCaseName.replaceAll(" ", "_")
					+ File.separator+"Screenshots";

			// File screenShotDir = new
			// File(Globals.GBL_strScreenshotsFolderPath);
			if (!new File(Globals.GBL_strScreenshotsFolderPath).exists())
				new File(Globals.GBL_strScreenshotsFolderPath).mkdirs();
			File screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			if (!screenShotDir.exists()) {
				// Try any one of these conditions
				System.out.println("SCREENSHOT DIRECTORY IS NOT EXISTS");
				Globals.GBL_strScreenshotsFolderPath = System
						.getProperty("user.dir")
						+ File.separator+"TestReport"+File.separator
						+ Globals.GBL_TestCaseName.replaceAll(" ", "_")
						+ File.separator+"Screenshots";

				screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			}

			int randomInt = screenShotDir.listFiles().length;
			File scrFile = ((TakesScreenshot) Web.getDriver())
					.getScreenshotAs(OutputType.FILE);

			fileName = Globals.GBL_TestCaseName + "_Itr"
					+  "_" + randomInt
					+ ".png";
			FileUtils.copyFile(scrFile, new File(
					Globals.GBL_strScreenshotsFolderPath + File.separator + fileName));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				//				e.printStackTrace();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "./" + Globals.GBL_TestCaseName.replaceAll(" ", "_")
				+ File.separator+"Screenshots" + File.separator + fileName;
	}


	/*private static Properties initPropertiesFile() throws IOException
	{

		if(objRead == null)
		{
			objRead = new ReadObject();
			objProperties = objRead.getObjectRepository();
		}

		return objProperties;

	}
	public static String getConfigParam(String parameter)
	{
		try {
			initPropertiesFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objProperties.getProperty(parameter);
	}*/

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
		if (!checkTestStatusMap.get(Thread.currentThread().getId()).get("Status")) {
			TestListener.setFinalTestStatus(false);
		}else{
			TestListener.setFinalTestStatus(true);
		}
	}
	/**
	 * <pre>
	 * Method to log the event fail in HTML Report after cucumber exception
	 * This method has to be called at the after method of each step definitions file
	 *  @param scenario object:  to check the status for scenario object
	 * </pre>
	 * 
	 * @throws Exception
	 */
	
	public static void logAfterExceptionEvent(cucumber.api.Scenario scenario){
		if (scenario.isFailed()) {
			Reporter.setCheckTestStatus(false);
			if(!checkTestStatusMap.get(Thread.currentThread().getId()).get("hasMinStepsReported"))
				Reporter.logEvent(Status.FAIL, "Unexpected failure occured", "", true);
			checkTestStatusMap.get(Thread.currentThread().getId()).put("hasMinStepsReported",true);
		}
	}


}
