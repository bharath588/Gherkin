package lib;


import java.util.Random;
import com.google.common.base.Throwables;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.GridType;
import com.relevantcodes.extentreports.LogStatus;

import core.framework.Globals;

public class Reporter {

	public static String strLogFolderPath;
	
	public static String currIterationStatus;
	public static final String NOT_COMPLETE = "NOT COMPLETE";
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	private static int iRandTraceCntr = 0;
	
	public static ExtentReports objReport;
	public enum Status {
		PASS, FAIL, WARNING, INFO
	}
	
	/**Method to add test case status and execution details to local excel results repository file
	 * 
	 * 
	 * @param beforeStart
	 * <pre>true - Adds a new row with containing details like
	 * 	1) Sub Functionality
	 * 	2) Test Case Name
	 *	3) Iteration Number (Picked from Test data)
	 * 	4) Execution Status (NOT COMPLETE)
	 * 	5) Execution Start Time
	 * 
	 * false - Adds rest of the details at the last row
	 * 	1) Execution Status (Based on execution)
	 * 	2) Execution End Time
	 * 	3) Detailed Report Path</pre>
	 * 
	 * @throws Exception
	 */
//	public static void logTCStatusToLocalResultRepo(boolean beforeStart) throws Exception {
//		String localResultRepoFile = DriveSuite.currRunPath + "\\" + ReadProperties.getEnvVariableValue("localResultRepoFile");
//		DateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//		Date date = new Date();
//		
//		//File xlFile = new File(localResultRepoFile);
//		FileInputStream inStream = new FileInputStream(localResultRepoFile);
//		XSSFWorkbook xlWB = new XSSFWorkbook(inStream);
//		XSSFCell cell;
//		inStream.close();
//		XSSFSheet xlSheet = xlWB.getSheet(ReadProperties.getEnvVariableValue("testSuiteName").trim());
//		
//		int iLastRow = xlSheet.getLastRowNum();
//		XSSFRow xlRow;
//		
//		if (beforeStart){
//			//xlRow = xlSheet.getRow(iLastRow + 1);
//			xlRow = xlSheet.createRow(iLastRow + 1);
//			cell = xlRow.createCell(0); //Sub Functionality
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(ReadProperties.getEnvVariableValue("subFunctionality"));
//			xlSheet.autoSizeColumn(0);
//			
//			cell = xlRow.createCell(1); //Test Case Name
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(ReadProperties.getEnvVariableValue("currTestCaseName"));
//			xlSheet.autoSizeColumn(1);
//			
//			cell = xlRow.createCell(2); //Iteration Number
//			cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
//			cell.setCellValue(TestDataContainer.GetParameterValue("iterationNumber"));
//			xlSheet.autoSizeColumn(2);
//			
//			cell = xlRow.createCell(3); //Execution Status
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(Reporter.currIterationStatus);
//			xlSheet.autoSizeColumn(3);
//			
//			cell = xlRow.createCell(4); //Tester
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(System.getProperty("user.name"));
//			xlSheet.autoSizeColumn(4);
//			
//			cell = xlRow.createCell(5); //Execution Start Time
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(dtFormat.format(date));
//			xlSheet.autoSizeColumn(5);
//		}				
//		else {
//			xlRow = xlSheet.getRow(iLastRow);
//			cell = xlRow.getCell(3); //Execution Status
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			
//			CellStyle cellStyle = xlWB.createCellStyle();
//			Font status_font = xlWB.createFont();
//			status_font.setBold(true);
//			
//			if (Reporter.currIterationStatus == Reporter.PASS)
//				status_font.setColor(HSSFColor.GREEN.index);
//			else if (Reporter.currIterationStatus == Reporter.FAIL)
//				status_font.setColor(HSSFColor.RED.index);
//			else
//				status_font.setColor(HSSFColor.BLACK.index);
//			
//			cell.setCellValue(Reporter.currIterationStatus);
//			cellStyle.setFont(status_font);
//			cell.setCellStyle(cellStyle);
//			
//			xlSheet.autoSizeColumn(3);
//			
//			cell = xlRow.createCell(6); //Execution End Time
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(dtFormat.format(date));
//			xlSheet.autoSizeColumn(6);
//			
//			cell = xlRow.createCell(7); //HTML Report Location
//			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//			/*String fileAddress = "./Reports/" + ReadProperties.getEnvVariableValue("testSuiteName") + "/" 
//					+ ReadProperties.getEnvVariableValue("currTestCaseName") + "/Iteration_" + TestDataContainer.GetParameterValue("iterationNumber") + ".html";*/
//			String fileAddress = "./" + ReadProperties.getEnvVariableValue("testSuiteName") + ".html";
//			cell.setCellValue(fileAddress.replaceAll(" ", "_"));
//			xlSheet.autoSizeColumn(7);
//			
//			CreationHelper createHelper = xlWB.getCreationHelper();
//			Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
//			Font link_Font = xlWB.createFont();
//			link_Font.setUnderline(Font.U_SINGLE);
//			link_Font.setColor(HSSFColor.BLUE.index);
//			cellStyle = xlWB.createCellStyle();
//			cellStyle.setFont(link_Font);
//			
//			link.setAddress(fileAddress.replaceAll(" ", "_"));
//			cell.setHyperlink(link);
//			cell.setCellStyle(cellStyle);
//		}
//		
//		FileOutputStream fOutput = new FileOutputStream(localResultRepoFile);
//		xlWB.write(fOutput);
//		xlWB.close();
//		fOutput.flush();
//		fOutput.close();
//		
//		date = null;
//		dtFormat = null;
//
//	}

	
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
//		String reportFilePath = DriveSuite.currRunPath + "/" 
//					+ ReadProperties.getEnvVariableValue("testSuiteName") + ".html";
		String reportFilePath = Globals.GC_TEST_REPORT_DIR 
				+ Globals.GBL_SuiteName + ".html";
		
		Reporter.objReport = ExtentReports.get(Reporter.class);
//		Reporter.objReport.init(reportFilePath, 
//				Boolean.parseBoolean(ReadProperties.getEnvVariableValue("replaceExistingReport")), 
//				DisplayOrder.BY_OLDEST_TO_LATEST, 
//				GridType.MASONRY);
		Reporter.objReport.init(reportFilePath, 
				Boolean.parseBoolean(Globals.GBL_REPLACE_EXISTING_HTML_REPORT), 
				DisplayOrder.BY_OLDEST_TO_LATEST, 
				GridType.MASONRY);
		
//		ReadProperties.setEnvVariableValue("replaceExistingReport", "false");
		Globals.GBL_REPLACE_EXISTING_HTML_REPORT = "false";
		
//		Reporter.objReport.config().documentTitle(ReadProperties.getEnvVariableValue("testSuiteName") + ": Summary Report");
		Reporter.objReport.config().documentTitle(Globals.GBL_SuiteName + ": Summary Report");
		Reporter.objReport.config().reportHeadline("Report options");
//		Reporter.objReport.config().reportTitle("Execution summary report for [" + ReadProperties.getEnvVariableValue("testSuiteName") + "]");
		Reporter.objReport.config().reportTitle("Execution summary report for [" + Globals.GBL_SuiteName + "]");
		Reporter.objReport.config().displayCallerClass(false);
		Reporter.objReport.config().useExtentFooter(false);
		Reporter.objReport.config().setImageSize("10%");
		
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
		
//		Reporter.objReport.startTest(tmpStr1 + 
//				(tmpStr2.length() > 0 ? ("<br>     " + tmpStr2) : "") + 
//				(tmpStr3.length() > 0 ? ("<br>     " + tmpStr3) : "") + 
//				"<br>Iteration " + TestDataContainer.GetParameterValue("iterationNumber"));
		
		Reporter.objReport.startTest(tmpStr1 + 
				(tmpStr2.length() > 0 ? ("<br>     " + tmpStr2) : "") + 
				(tmpStr3.length() > 0 ? ("<br>     " + tmpStr3) : "") + 
				"<br>Iteration " + Globals.GBL_CurrentIterationNumber);
		
		Reporter.currIterationStatus = Reporter.NOT_COMPLETE;
//		Reporter.logTCStatusToLocalResultRepo(true);
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
//			Reporter.objReport.attachScreenshot(WebActions.captureScreenshot());
			Reporter.objReport.attachScreenshot(Web.captureScreenshot());
//		} else if (Step.trim().length() == 0 && attachScreenshot) {
//			Reporter.objReport.attachScreenshot(WebActions.captureScreenshot(), Details);
		} else if (Step.trim().length() == 0 && attachScreenshot) {
			Reporter.objReport.attachScreenshot(Web.captureScreenshot(), Details);
		} else {
			LogStatus tmpLogStatus;
			String stackTrace = "";
			
			//Get stack trace from exception if there is an exception if available
			//Otherwise get normally from thread
//			if (DriveSuite.exception != null) {
			if (Globals.exception != null) {
//				stackTrace = Throwables.getStackTraceAsString(DriveSuite.exception);
				stackTrace = Throwables.getStackTraceAsString(Globals.exception);
//				WebDriver.exception = null;
				Globals.exception = null;
			} else if (Globals.error != null) {
				stackTrace = Throwables.getStackTraceAsString(Globals.error);
				Globals.error = null;
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
//				Reporter.objReport.log(tmpLogStatus, Step, Details, WebActions.captureScreenshot());
				Reporter.objReport.log(tmpLogStatus, Step, Details, Web.captureScreenshot());
			} else {
				Reporter.objReport.log(tmpLogStatus, Step, Details);
			}
		}
		
		if (logStatus == Status.FAIL)
			Reporter.currIterationStatus = "FAIL";
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
//		Reporter.logTCStatusToLocalResultRepo(false);
	}
}
