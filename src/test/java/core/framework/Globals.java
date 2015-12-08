package core.framework;


public class Globals {
       
    // General
          public static final String GC_EMPTY = "";
          public static final boolean GC_FALSE = false;
          public static final boolean GC_TRUE = true;
          public static final String GC_KEYAUT = "AUT";
          public static final String GC_KEYTESTTYPE = "TESTTYPE";
          public static final String GC_DEFAULTTESTTYPE = "DefaultTest";
          public static final String GC_KEY_RUNXML = "RUNXML";
       //  public static final String GC_PROJECT_RESOURCES= "TESTRESOURCES";
          public static final String GC_VAL_RUNALLITR = "ALL";
          public static final String GC_AUT_TYPE = "WEB";
//        public static final String GC_TC_PASSED="PASS";
//        public static final String GC_TC_FAILED="FAIL";
//        public static final String GC_TC_INFO="INFO";
          
       //SpreadSheet
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
          
    //FileLocations
          public static final String GC_TESTCASESLOC = System.getProperty("user.dir")+"\\testcases\\";
          public static final String GC_TESTDATALOC = System.getProperty("user.dir")+"\\testdata\\";
          public static final String GC_TESTNG_XML_PATH = System.getProperty("user.dir")+"\\RunXML";
          public static final String GC_PROJECT_BIN_DIR = System.getProperty("user.dir")+"\\bin";         
          public static final String GC_TESTCASE_RELPATH =  ".testcases.";
          public static final String GC_LISTENERS_CLASSNAME =  "core.utils.TestListener";      
          public static final String GC_APP_RELPATH =  "app.";
          public static final String GC_PROJECT_DIR = System.getProperty("user.dir")+"\\";
          public static final String GC_TESTCONFIGLOC = GC_PROJECT_DIR;
          public static final String GC_TESTNG_TEST_OUTPUT = System.getProperty("user.dir")+"\\test-output";
          public static final String GC_TEST_REPORT_DIR =  GC_PROJECT_DIR+"TestReport\\";
          public static final String GC_TEST_REPORT_RESOURCE = GC_PROJECT_DIR+"src\\devresource\\reports\\";
          public static final String GC_TEST_REPORT_CSS_RESOURCE = GC_TEST_REPORT_RESOURCE+"CSS";
          public static final String GC_TEST_REPORT_JS_RESOURCE = GC_TEST_REPORT_RESOURCE+"JS";
          public static final String GC_TEST_REPORT_FONTS_RESOURCE = GC_TEST_REPORT_RESOURCE+"FONTS";
          public static final String GC_TEST_REPORT_HTML_RESOURCE = GC_TEST_REPORT_RESOURCE+"HTML";
          public static final String GC_TEST_REPORT_IMG_RESOURCE = GC_TEST_REPORT_RESOURCE+"IMG";
          public static String GBL_SuiteName = "";
          
          
          public static String GBL_REPLACE_EXISTING_HTML_REPORT = "true";
          public static String GBL_TestCaseName = "";
          public static int GBL_CurrentIterationNumber = 1;
          public static String GBL_strScreenshotsFolderPath = "";
          public static Exception exception = null;
          public static AssertionError assertionerror = null;
          public  static Error error = null;
          
        //XML  
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
          
        //LOGGER
          public static final String GC_LOG_DEBUG = "debug";
          public static final String GC_LOG_INFO = "info";
          public static final String GC_LOG_WARN = "warn";
          public static final String GC_LOG_ERR = "error";
          public static final String GC_LOG_TRACE = "trace";
          public static final String GC_LOG_INIT_MSG = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ STARTING TEST EXECUTION @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
          public static final String GC_LOG_INITTC_MSG =" ****************************** ";
       
       //EXCEPTION
          public static final String GC_EXPNTYPE_EXCEPTION = "exception";
          public static final String GC_EXPNTYPE_NULLPOINTER = "nullpointerexception";
          public static final String GC_EXPNTYPE_CLASSNOTFOUND = "classnotfoundexception";
          public static final String GC_EXPNTYPE_INTERRUPTED = "interruptedexception";
          public static final String GC_EXPNTYPE_NOSUCHMETHOD = "nosuchmethodexception";
          public static final String GC_EXPNTYPE_NOSUCHFIELD = "nosuchfieldexception";
          public static final String GC_EXPNTYPE_ILLEGALSTATE = "illegalstateexception";
          public static final String GC_EXPNTYPE_IOEXCEPTION = "illegalstateexception";
                 
}
