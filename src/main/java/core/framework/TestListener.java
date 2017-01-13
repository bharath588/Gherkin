package core.framework;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lib.Log;
import lib.Log.Level;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import mobile.Mobile;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.IAnnotationTransformer;
import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonParseException;

import core.framework.ThrowException.TYPE;

public class TestListener implements ITestListener, IConfigurationListener2,
		ISuiteListener, IInvokedMethodListener, IRetryAnalyzer,
		IAnnotationTransformer {

	int currentTCInvocationCount = 0;
	static int suiteInvCount = 0;
	private static Map<Long,Boolean> finalTestStatusMap = new LinkedHashMap<>();
	public static Map<Long, String> browserMap = new LinkedHashMap<>();
	public static Map<Long, String> portMap = new LinkedHashMap<>();
	public Map<Integer, Map<String, String>> failedXmlMap = new LinkedHashMap<>();
	static int i = 0;
	static Map<String, Map<String, String>> gridPropertiesMap;
	
	
	

	private boolean isFinalTestStatus() {
		return finalTestStatusMap.get(Thread.currentThread().getId());
	}

	public static void setFinalTestStatus(boolean testStatus) {
		finalTestStatusMap.put(Thread.currentThread().getId(), testStatus);
	}
	public void onStart(ISuite suite) {
		try {
			Stock.readConfigProperty(Globals.GC_TESTCONFIGLOC
					+ Globals.GC_CONFIGFILEANDSHEETNAME + ".properties");
			if (!Globals.GC_EXECUTION_ENVIRONMENT.isEmpty()) {
				Stock.setConfigParam(Globals.GC_COLNAME_TEST_ENV,
						Globals.GC_EXECUTION_ENVIRONMENT, true);
			}
			if (!Globals.GC_EXECUTION_BROWSER.isEmpty()) {
				Stock.setConfigParam(Globals.GC_COLNAME_BROWSER,
						Globals.GC_EXECUTION_BROWSER, true);
			}
			 if(new File(Globals.GC_TEST_REPORT_DIR).exists()) {

				FileUtils.deleteDirectory(new File(Globals.GC_TEST_REPORT_DIR));

				System.out.println("Deleted report folder from directory : "

				+ new File(Globals.GC_TEST_REPORT_DIR)

				.getAbsolutePath());

				Log.Report(Level.INFO,

				"Test Report folder removed on exist on suite level");

			}
			Globals.GC_MANUAL_TC_NAME_MAP = new HashMap<Long, String>();
			readGridConfigFile("grid.json");
			 if(Stock.getConfigParam("PLATFORM").equalsIgnoreCase("Mobile")){
				 Mobile.mobilePlatform = true; 
			 }
			 
			List<ITestNGMethod> methodsList = new LinkedList<>();
			methodsList = suite.getAllMethods();
			generateTestcaseReferenceMap(suite);
			int counter = 0;
			
			for (ITestNGMethod ite : methodsList) {
				Globals.testNGPropertiesMap.put(ite.getInstance().getClass()
						.getName()
						+ counter, ite.getMethodName());
				counter++;
			}
			Log.Report(Level.INFO,
					"Test Configuration initialized successfully");
			if (new File(Globals.GC_TEST_REPORT_DIR).exists()) {
				FileUtils.deleteDirectory(new File(Globals.GC_TEST_REPORT_DIR));
				System.out.println("Deleted report folder from directory : "
						+ new File(Globals.GC_TEST_REPORT_DIR)
								.getAbsolutePath());
				Log.Report(Level.INFO,
						"Test Report folder removed on exist on suite level");
			}
			counter = 0;
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, e.getMessage());
		}
	}

	private void generateTestcaseReferenceMap(ISuite suite) {
		List<XmlTest> testNames = suite.getXmlSuite().getTests();
		List<XmlClass> classNames;
		List<XmlInclude> methodNames;
		Map<String,String> methodsAndParamsMap;
		for(XmlTest refTest : testNames)
		{
			methodsAndParamsMap = new LinkedHashMap<>();
			classNames = new LinkedList<>();
			classNames = refTest.getXmlClasses();
			methodNames = new LinkedList<>();
			for(XmlClass refClass : classNames)
			{
				methodNames = refClass.getIncludedMethods();
				for(XmlInclude refMethod : methodNames)
				{
					methodsAndParamsMap.put(refMethod.getName(), refMethod.getAllParameters().get("ManualTCName"));
				}
			}
			Globals.manualtoAutoTCMap.put(refTest.getName(), methodsAndParamsMap);
		}
		
		
	}

	public void onStart(ITestContext test) {
		Globals.GC_MANUAL_TC_NAME_MAP.put(Thread.currentThread().getId(), test.getName());
}

	public synchronized String[] getBrowser() {
		String browserName = null;
		String port = null;
		browserName = gridPropertiesMap.get("Config" + i).get("Browser");
		port = gridPropertiesMap.get("Config" + i).get("Port");
		browserMap.put(Thread.currentThread().getId(), browserName);
		portMap.put(Thread.currentThread().getId(), port);
		System.out.println(browserName);
		gridPropertiesMap.remove("Config" + i);
		i = i + 1;
		return new String[] { browserName, port };
	}
	
	public int getNumberOfMachines()
	{
		return gridPropertiesMap.size();
	}

	public void onTestStart(ITestResult result) {
	}

	public void onTestSuccess(ITestResult result) {

	}

	public void onTestFailure(ITestResult result) {

	}

	public void onTestSkipped(ITestResult result) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onFinish(ITestContext context) {
		
		Iterator<ITestResult> failedTestCases = context.getFailedTests()
				.getAllResults().iterator();
		while (failedTestCases.hasNext()) {
			ITestResult failedTestCase = failedTestCases.next();
			ITestNGMethod method = failedTestCase.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTestCases.remove();
			} else {

				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTestCases.remove();
				}
			}
		}
	}

	public void onConfigurationSuccess(ITestResult result) {
	}

	public void onConfigurationFailure(ITestResult result) {

	}

	public void onConfigurationSkip(ITestResult result) {
	}

	public void beforeConfiguration(ITestResult result) {
		
	}

	public void onFinish(ISuite suite) {
		try {		
		
		Reporter.objReport.flush();
	
		if(Stock.getConfigParam("needRetry").equalsIgnoreCase("true"))
		{
		BuildFailedTestNG(failedXmlMap);
		}
			if (Web.getDriver().getWindowHandles().size() >= 0)
				Web.getDriver().quit();
		}
		catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, "Failed to quit Web Driver :"
					+ e.getMessage());

	}}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test
	@SuppressWarnings("unchecked")
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		try {

			if (!Web.getDriver().getWindowHandle().isEmpty()) {
				Log.Report(Level.INFO,
						"Web Driver instance fou      nd to be active for the Test Case :"
								);
			}
		} catch (Exception t) {
			try {
				Log.Report(Level.INFO,
						"Web Driver instance found to be inactive for the Test Case :"
								 + " ,hence re-initiating");

				if (Stock.getConfigParam("type").equalsIgnoreCase("grid")) {
					if (!browserMap.containsKey(Thread.currentThread().getId())
							|| !portMap.containsKey(Thread.currentThread()
									.getId())) {
						getBrowser();
					}
					Web.getRemoteWebDriver(
							browserMap.get(Thread.currentThread().getId()),
							portMap.get(Thread.currentThread().getId()));
				} else {
					Web.getWebDriver(Stock.getConfigParam("BROWSER"));
				}
				Log.Report(Level.INFO,
						"Web Driver instance re-initiated successfully the Test Case :"
								);
			} catch (Exception e) {
				ThrowException
						.Report(TYPE.EXCEPTION,
								"Failed to re-initialize Web Driver :"
										+ e.getMessage());
			}
		}
		if (method.getTestMethod().isTest()) {
			Globals.GC_MANUAL_TC_REPORTER_MAP.put(Thread.currentThread().getId(), method.getTestMethod().findMethodParameters(method.getTestMethod().getXmlTest()).get("ManualTCName"));
			HashMap<String, String> globalTestData = (HashMap<String, String>) testResult
					.getParameters()[1];
			Stock.globalTestdata.put(Thread.currentThread().getId(),
					globalTestData);
			currentTCInvocationCount = testResult.getMethod()
					.getCurrentInvocationCount();
			Web.setLastIteration((Stock.getIterations() == (currentTCInvocationCount + 1)) ? true
					: false);
		}
	}

	@SuppressWarnings({ "serial", "static-access" })
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.getTestMethod().isTest()) {
			if (!isFinalTestStatus()) {
				testResult.setStatus(ITestResult.FAILURE);
			}
			if (suiteInvCount == 0) {
				if (testResult.getStatus() == testResult.FAILURE) {
					Reporter.testCaseMap.get(Thread.currentThread().getId()).log(Status.FAIL,testResult.getThrowable());
					final String className = testResult.getTestClass()
							.getName();
					final String methodName = method.getTestMethod()
							.getMethodName();
					final String testName = testResult.getTestContext()
							.getName();
					failedXmlMap.put(i++, new HashMap<String, String>() {
						{
							put("className", className);
							put("methodName", methodName);
							put("testName", testName);
						}
					});
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Map<String, String>> readGridConfigFile(String file) throws JsonParseException, IOException {
		Map<String, Map<String, String>> configMap = new LinkedHashMap<>();
		Map<String, String> innerMap = null;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray jsonArray = (JSONArray) obj;
			int length = jsonArray.size();
			LinkedList port = new LinkedList();
			LinkedList browser = new LinkedList();
			LinkedList platform = new LinkedList();
			for (int i = 0; i < length; i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Set s = jsonObject.entrySet();
				Iterator iter = s.iterator();
				LinkedList ll = new LinkedList();
				LinkedList lm = new LinkedList();
				while (iter.hasNext()) {
					Map.Entry me = (Map.Entry) iter.next();
					// System.out.println(me.getKey() + "  " + me.getValue());
					ll.add(me.getValue());
					lm.add(me.getKey());
				}
				browser.add(ll.get(2));

				port.add(ll.get(1));

				platform.add(ll.get(0));

			}

			for (int i = 0; i < port.size(); i++) {
				innerMap = new LinkedHashMap<>();
				innerMap.put("Platform", (String) platform.get(i));
				innerMap.put("Browser", (String) browser.get(i));
				innerMap.put("Port", (String) port.get(i));
				configMap.put("Config" + i, innerMap);
			}
			gridPropertiesMap = configMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configMap;
	}

	int retryCount = 0, maxRetryCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			System.out.println("Retrying test " + result.getName()
					+ " with status " + " for the " + (retryCount + 1)
					+ " time(s).");
			retryCount++;
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();

		if (retry == null) {
			annotation.setRetryAnalyzer(RetryAnalyzer.class);
		}
	}

	/*----------------------------------------------------------------------------------------------------------------------
	 * Building testNG xml with Parameters
	 * className
	 * methodName
	 * testcaseName
	 */

	 void BuildFailedTestNG(Map<Integer, Map<String, String>> failedXmlMap)
			throws ParserConfigurationException, TransformerException {

		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = dFactory.newDocumentBuilder();

		Document doc = build.newDocument();
		Element root = doc.getDocumentElement();
		Element suite = doc.createElement("suite");

		suite.setAttribute("name", "Suite");
		doc.appendChild(suite);
		Element listeners = doc.createElement("listeners");
		Element listener = doc.createElement("listener");
		listener.setAttribute("class-name", "core.framework.TestListener");
		listeners.appendChild(listener);
		suite.appendChild(listeners);

		for (Entry<Integer, Map<String, String>> map : failedXmlMap.entrySet()) {
			Element test = doc.createElement("test");
			test.setAttribute("name", map.getValue().get("testName"));
			suite.appendChild(test);

			Element classes = doc.createElement("classes");
			test.appendChild(classes);

			Element clazz = doc.createElement("class");
			clazz.setAttribute("name", map.getValue().get("className"));
			classes.appendChild(clazz);

			Element methods = doc.createElement("methods");
			clazz.appendChild(methods);

			Element include = doc.createElement("include");
			include.setAttribute("name", map.getValue().get("methodName"));
			methods.appendChild(include);

		}

		TransformerFactory tFact = TransformerFactory.newInstance();
		Transformer trans = tFact.newTransformer();

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(new File("failedTestNG"+Thread.currentThread().getId()+".xml"));
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);

	}


}