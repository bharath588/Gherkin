package bdd_core.framework;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import bdd_lib.Stock;
import cucumber.api.DataTable;

public class Globals {

	// General
	public static final String GC_EMPTY = "";
	public static final boolean GC_FALSE = false;
	public static final boolean GC_TRUE = true;
	public static final String GC_KEYAUT = "AUT";
	public static final String GC_KEYTESTTYPE = "TESTTYPE";
	public static final String GC_DEFAULTTESTTYPE = "DefaultTest";
	public static final String GC_KEY_RUNXML = "RUNXML";
	public static final String GC_VAL_RUNALLITR = "ALL";
	public static final String GC_AUT_TYPE = "WEB";
	public static final String GC_COLNAME_BROWSER = "BROWSER";
	public static final Map<String, String> DB_TYPE;
	public static Map<String, String> manualtoAutoTCMap = new LinkedHashMap<>();
	public static final String GC_COLNAME_CAPTURESCREENSHOT = "CAPTURESCREENSHOT";
	static {
		DB_TYPE = new HashMap<String, String>();
		DB_TYPE.put("PROJ", "DEV");
		DB_TYPE.put("QA", "QA");
		DB_TYPE.put("PROD", "PROD");
	}
	// FOR CI
	public static final String GC_COLNAME_TEST_ENV = "TEST_ENV";
	public static final String GC_EXECUTION_ENVIRONMENT = System
			.getProperties().containsKey("env") ? System.getProperty("env")
			.toUpperCase() : Stock.getConfigParam(GC_COLNAME_TEST_ENV);

	public static final String GC_EXECUTION_BROWSER = System.getProperties()
			.containsKey("BROWSER") ? System.getProperty("BROWSER")
			.toUpperCase() : Stock.getConfigParam(GC_COLNAME_BROWSER);

	// SpreadSheet
	public static final String GC_CONFIGFILEANDSHEETNAME = "testexecutionconfig";
	public static final String GC_DATASHEET_ERR = "Sheet not found";
	public static final String GC_TESTCASERUNORDERPREFIX = "runorder_";
	public static final String GC_TESTDATAPREFIX = "testdata_";
	public static final String GC_TESTDATATCCOLNAME = "testcasename";
	public static final String GC_ITRCCOLNAME = "iteration";
	public static final String GC_COLNAME_CONFIG = "config";
	public static final String GC_COLNAME_VALUE = "value";
	public static final String GC_COLNAME_MODULENAME = "modulename";
	public static final String GC_COLNAME_RUNSTATUS = "runstatus";
	public static final String GC_COLNAME_TESTCASES = "testcases";
	public static final String GC_COLNAME_SETPRIORITY = "setpriority";
	public static final String GC_COLNAME_DEPNDTC = "dependenttestcase";
	public static final String GC_RUNSTATUS_YES = "YES";
	public static final String GC_RUNSTATUS_NO = "NO";
	public static final String GC_RUNXML_DEFAULT = "SelectXML";
	public static final String GC_XML_ATTR_VAL_TEST_COL_NM = "ManualTestcases";
	public static final String GC_COLNAME_CLASSPATH = "ClassPath";
	public static String GC_OBJECT_SYNC_TIMEOUT = null;

	// FileLocations
	// public static final String GC_APP_RELPATH = "app.";

	public static final String GC_COMMON_TESTDATALOC = "\\\\its\\\\fss\\\\DEVGHOME\\\\Automation-Selenium\\\\TestData\\\\";
	public static final String GC_TESTCASESLOC = System.getProperty("user.dir")
			+ File.separator + "RunOrderSheets" + File.separator;
	public static final String GC_TESTDATALOC = System.getProperty("user.dir")
			+ File.separator + "TestData" + File.separator;
	public static final String GC_TESTNG_XML_PATH = System
			.getProperty("user.dir") + File.separator + "RunXML";
	public static final String GC_PROJECT_BIN_DIR = System
			.getProperty("user.dir") + File.separator + "bin";
	public static final String GC_TESTCASE_RELPATH = ".testcases.";
	public static final String GC_LISTENERS_CLASSNAME = "core.framework.TestListener";
	public static final String GC_PROJECT_DIR = System.getProperty("user.dir")
			+ File.separator;
	public static final String GC_TESTCONFIGLOC = GC_PROJECT_DIR;
	public static final String GC_TESTNG_TEST_OUTPUT = System
			.getProperty("user.dir") + File.separator + "test-output";
	public static final String GC_TEST_REPORT_DIR = GC_PROJECT_DIR
			+ "TestReport" + File.separator;
	public static final String GC_TEST_REPORT_RESOURCE = GC_PROJECT_DIR + "src"
			+ File.separator + "devresource" + File.separator + "reports"
			+ File.separator;
	public static final String GC_TEST_REPORT_CSS_RESOURCE = GC_TEST_REPORT_RESOURCE
			+ "CSS";
	public static final String GC_TEST_REPORT_JS_RESOURCE = GC_TEST_REPORT_RESOURCE
			+ "JS";
	public static final String GC_TEST_REPORT_FONTS_RESOURCE = GC_TEST_REPORT_RESOURCE
			+ "FONTS";
	public static final String GC_TEST_REPORT_HTML_RESOURCE = GC_TEST_REPORT_RESOURCE
			+ "HTML";
	public static final String GC_TEST_REPORT_IMG_RESOURCE = GC_TEST_REPORT_RESOURCE
			+ "IMG";
	public static String GBL_SuiteName = "";

	// public static String GBL_REPLACE_EXISTING_HTML_REPORT = "true";
	public static String GBL_REPLACE_EXISTING_HTML_REPORT = Stock
			.getConfigParam("Overwrite_Existing_Report");
	public static String GBL_TestCaseName = "";
	public static int GBL_CurrentIterationNumber = 1;
	public static String GBL_strScreenshotsFolderPath = "";
	public static Exception exception = null;
	public static Map<Long, Exception> exceptionMap = new LinkedHashMap<>();
	public static AssertionError assertionerror = null;
	public static Error error = null;

	// Reporter
	// public static final String
	// GC_SCREENSHOT_REQUIRED=System.getProperties().containsKey("ScreenshotCaptureOption")?System.getProperty("ScreenshotCaptureOption").toUpperCase():
	// Stock.getConfigParam("ScreenshotCaptureOption".toUpperCase());
	// public static final String GC_SCREENSHOT_REQUIRED="SCREENSHOTONFAILURE";
	public static String GC_CAPTURE_SCREENSHOT = System.getProperties()
			.containsKey("CAPTURESCREENSHOT") ? System
			.getProperty("CAPTURESCREENSHOT").trim().toUpperCase() : Stock
			.getConfigParam(GC_COLNAME_CAPTURESCREENSHOT);
	public static final String optionUserDefault = "USERDEFAULT";
	public static final String option_Never = "NEVER";
	public static final String option_OnFailure = "ONFAILURE";
	public static final String option_Always = "ALWAYS";
	// XML
	public static final String GC_XML_SUITE = "suite";
	public static final String GC_XML_TEST = "test";
	public static final String GC_XML_LISTENERS = "listeners";
	public static final String GC_XML_LISTENER = "listener";
	public static final String GC_XML_CLASSES = "classes";
	public static final String GC_XML_CLASS = "class";
	public static final String GC_XML_METHODS = "methods";
	public static final String GC_XML_INCLUDE = "include";
	public static final String GC_XML_EXCLUDE = "exclude";
	public static final String GC_XML_ATTR_NAME = "name";
	public static final String GC_XML_ATTR_CLASSNAME = "class-name";
	public static final String GC_XML_ATTR_VERBOSE = "verbose";
	public static final String GC_XML_ATTR_VAL_SUITE = "testsuite";
	public static final String GC_XML_ATTR_VAL_TEST = "testXML";
	public static String GC_MANUAL_TC_NAME;
	public static Map<Long, String> GC_MANUAL_TC_REPORTER_MAP = new LinkedHashMap<>();
	public static Map<Long, String> GC_MANUAL_TC_NAME_MAP = new LinkedHashMap<Long, String>();
	public static final String GC_COL_MANUAL_TC = "ManualTestCaseName";

	// LOGGER
	public static final String GC_LOG_DEBUG = "debug";
	public static final String GC_LOG_INFO = "info";
	public static final String GC_LOG_WARN = "warn";
	public static final String GC_LOG_ERR = "error";
	public static final String GC_LOG_TRACE = "trace";
	public static final String GC_LOG_INIT_MSG = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ STARTING TEST EXECUTION @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
	public static final String GC_LOG_INITTC_MSG = " ****************************** ";

	// EXCEPTION
	public static final String GC_EXPNTYPE_EXCEPTION = "exception";
	public static final String GC_EXPNTYPE_NULLPOINTER = "nullpointerexception";
	public static final String GC_EXPNTYPE_CLASSNOTFOUND = "classnotfoundexception";
	public static final String GC_EXPNTYPE_INTERRUPTED = "interruptedexception";
	public static final String GC_EXPNTYPE_NOSUCHMETHOD = "nosuchmethodexception";
	public static final String GC_EXPNTYPE_NOSUCHFIELD = "nosuchfieldexception";
	public static final String GC_EXPNTYPE_ILLEGALSTATE = "illegalstateexception";
	public static final String GC_EXPNTYPE_IOEXCEPTION = "illegalstateexception";

	// AAG Managed Accounts
	public static final String GC_LOG_FILE_VALIDATION_MESSAGE = "Successful-> "
			+ "[1-9].*";
	// Log File Patterns
	public static String GC_PROSPECT_LOG_FILE_PATTERN = "";
	public static String GC_MEMBER_LOG_FILE_PATTERN = "";
	public static String GC_ENROLL_CANCEL_LOG_FILE_PATTERN = "";
	public static String GC_TRANSACTION_LOG_FILE_PATTERN = "";
	public static String GC_TRANSACTION_COINFIRMATION_LOG_FILE_PATTERN = "";
	public static int GC_SELECT_ENVIRONMENT = 0;
	public static String GC_SELECT_FOLDER_FOR_RUNNING_JOB = "";
	public static final String GC_LOG_FILE_TYPE = ".log";
	public static final String GC_FILE_TYPE = ".xml";
	public static String GC_Database_Prefix = "";
	public static String GC_SetEnvCommand = "";

	public static final String GC_TRANSACTION_FUTURE_STATUS = "FUTURE";
	public static final String GC_TRANSACTION_COMPLETE_STATUS = "COMPLETE";
	public static final String GC_BATCH_RUN_DATE_FORMAT = "yyyy-MM-dd";
	public static final String GC_RUN_DATE_TIMESTAMP = "yyyyMMdd_HHmmss";
	public static final String GC_TRANS_CONF_SUCCESS_STATUS = "SUCCESS";
	public static final String GC_TRANS_CONF_REQUESTED_STATUS = "REQUESTED";
	public static final String GC_ENROLL_STATUS = "Enroll";
	public static final String GC_CANCEL_STATUS = "Cancel";
	public static final String GC_TRANS_CONF_BEFORE_JOB_STATUS = "Before";
	public static final String GC_TRANS_CONF_AFTER_JOB_STATUS = "After";
	public static String GC_TEST_REALPATH = "";

	public static Map<String, String> testNGPropertiesMap = new LinkedHashMap<>();

	public static final Map<String, String> databaseConnectionStrings;
	public static final String option_UserDefault = "AS_USER_DEFINED";
	// public static final String GC_COMMON_TESTDATALOC =
	// "H://common//Automation-Selenium//TestData";

	static {
		databaseConnectionStrings = new Hashtable<String, String>();
		// Dev databases
		databaseConnectionStrings.put("D_INST",
				"jdbc:oracle:thin:@DINSTDB:1521/dinstmain.isis.gwl.com");
		databaseConnectionStrings.put("D_PNP",
				"jdbc:oracle:thin:@dpnpdb:1521/dpnpmain.isis.gwl.com");
		databaseConnectionStrings.put("D_ISIS",
				"jdbc:oracle:thin:@disisdb:1521/disisbatch.isis.gwl.com");
		databaseConnectionStrings.put("D_CMNM",
				"jdbc:oracle:thin:@fsxd01dbad02:1521/dcmnmmain.isis.gwl.com");
		databaseConnectionStrings.put("D_MISC",
				"jdbc:oracle:thin:@gp-pdb2:1521/D_MISC.ISIS.GWL.COM");
		databaseConnectionStrings.put("D_AMA",
				"jdbc:oracle:thin:@Damadb:1521/damamain.isis.gwl.com");
		databaseConnectionStrings.put("D_QAMA",
				"jdbc:oracle:thin:@Qamadb:1521/qamamain.isis.gwl.com");

		// to be deleted once the all projects comply
		databaseConnectionStrings.put("D_QASI",
				"jdbc:oracle:thin:@dqasi:1521/dqasimain.isis.gwl.com");
		databaseConnectionStrings.put("D_QASK",
				"jdbc:oracle:thin:@dqaskdb:1521/dqaskmain.isis.gwl.com");
		databaseConnectionStrings.put("D_IN02",
				"jdbc:oracle:thin:@din02db:1521/din02java.isis.gwl.com");
		databaseConnectionStrings.put("D_QAS2",
				"jdbc:oracle:thin:@dqas2:1521/dqas2main.isis.gwl.com");

		// new QA databases
		databaseConnectionStrings.put("Q_CMNM",
				"jdbc:oracle:thin:@fsxq01dbad02:1521/Q_CMNM.ISIS.GWL.COM");
		databaseConnectionStrings.put("Q_INST",
				"jdbc:oracle:thin:@qinstdb:1521/qinstmain.isis.gwl.com");
		databaseConnectionStrings.put("Q_IN02",
				"jdbc:oracle:thin:@qin02db:1521/qin02main.isis.gwl.com");
		databaseConnectionStrings.put("Q_ISIS",
				"jdbc:oracle:thin:@qisisdb:1521/QISISMAIN.ISIS.GWL.COM");
	}

	// BDD
	public static DataTable creds;
	public static Integer currentIteration = null;
	public static String scenarioName;
	public static String planNumber;
	public static String localDateTimeIST;
}
