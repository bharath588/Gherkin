package core.framework;



import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Log;
import lib.Log.Level;
import lib.Reporter;
import lib.Stock;
import lib.Web;








import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import core.framework.ThrowException.TYPE;

public class TestListener implements ITestListener, IConfigurationListener2, ISuiteListener, IInvokedMethodListener{

	public static Set<String> webDriverNameList = new LinkedHashSet<>();
	Map<String,String> tempMap;
	int currentTCInvocationCount=0;
	private static boolean finalTestStatus =  true;
	public static Map<Long,String> browserMap = new LinkedHashMap<>();
	public static Map<Long,String> portMap = new LinkedHashMap<>();
	//Iterator iter = webDriverNameList.iterator();
	static int i =0;
	static Map<String,Map<String,String>> gridMap;
	private boolean isFinalTestStatus() {
		return finalTestStatus;
	}

	public static void setFinalTestStatus(boolean testStatus) {
		finalTestStatus = testStatus;
	}
	
	public void onStart(ISuite suite) {
		try{
			Stock.getParam(Globals.GC_TESTCONFIGLOC+
			Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
			Globals.GC_MANUAL_TC_NAME_MAP = new HashMap<Long,String>();
			readJson("grid.json");
			/*if(!Globals.GC_EXECUTION_ENVIRONMENT.isEmpty())
            {
            Stock.setConfigParam(Globals.GC_COLNAME_TEST_ENV, Globals.GC_EXECUTION_ENVIRONMENT, true);
            }*/
			List<ITestNGMethod> methodsList = new LinkedList<>();
			int counter=0;
			methodsList = suite.getAllMethods();
			for(ITestNGMethod ite : methodsList)
			{
				Globals.testNGPropertiesMap.put(ite.getInstance().getClass().getName()+counter,ite.getMethodName());
				counter++;
			}
			
			Log.Report(Level.INFO,"Test Configuration initialized successfully");
			counter = 0;
		}catch(Exception e){
			ThrowException.Report(TYPE.EXCEPTION,e.getMessage());			
		}		
	}

	
	public void onStart(ITestContext test) {	
		Globals.GC_MANUAL_TC_NAME = test.getName();	
		try{	
			
			if(!Web.getDriver().getWindowHandle().isEmpty()){
				Log.Report(Level.INFO,"Web Driver instance found to be active for the Test Case :"+test.getName());				
			}
		}catch(Exception t){				
			try{
				Log.Report(Level.INFO,"Web Driver instance found to be inactive for the Test Case :"+
			               test.getName()+" ,hence re-initiating");
				
				
				Web.getWebDriver("FF");
				
				//Web.getRemoteWebDriver(getBrowser()[0], getBrowser()[1]);
				//Web.getRemoteWebDriver(browserMap.get(Thread.currentThread().getId()).get("Browser"),browserMap.get(Thread.currentThread().getId()).get("Port"));
				
				Log.Report(Level.INFO,"Web Driver instance re-initiated successfully the Test Case :"+
			               test.getName());
			}catch(Exception e){
				ThrowException.Report(TYPE.EXCEPTION,"Failed to re-initialize Web Driver :" +e.getMessage());
			}			
		}						
	}

	public synchronized String[] getBrowser()
	{
		String browserName = null;
		String port = null;
		browserName = gridMap.get("Config"+i).get("Port");
		port = gridMap.get("Config"+i).get("Browser");
		browserMap.put(Thread.currentThread().getId(), browserName);
		portMap.put(Thread.currentThread().getId(), port);
		System.out.println(browserName);
		gridMap.remove("Config"+i);
		i=i+1;
		return new String[]{browserName,port};
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
		//Reporter.objReport.endTest(Reporter.grandMap.get(Thread.currentThread().getId()));
		Reporter.objReport.flush();
		/*try {
			if (Web.webdriver.getWindowHandles().size() >= 0)
				Web.webdriver.quit();
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION,"Failed to quit Web Driver :" +e.getMessage());
		}*/
		
	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test
	@SuppressWarnings("unchecked")
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {		
		if (method.getTestMethod().isTest()) {
			HashMap<String, String> globalTestData = (HashMap<String, String>) testResult.getParameters()[1];
			Stock.globalTestdata.put(Thread.currentThread().getId(), globalTestData);
			currentTCInvocationCount = testResult.getMethod().getCurrentInvocationCount();
			Web.setLastIteration((Stock.getIterations() == (currentTCInvocationCount+1))? true:false);	
		}
	}
	
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.getTestMethod().isTest()){
			if(!isFinalTestStatus()){
				testResult.setStatus(ITestResult.FAILURE);
			}			
		}
	}


	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Map<String,String>> readJson(String file) {
	
		//return propertiesMap;
		 Map<String,Map<String,String>> configMap = new LinkedHashMap<>();
		 Map<String,String> innerMap = null;
		 JSONParser parser = new JSONParser();
		    try {
		        Object obj = parser.parse(new FileReader(file));
		        JSONArray jsonArray = (JSONArray) obj;
		        int length = jsonArray.size();
		   		LinkedList port = new LinkedList();
		        LinkedList browser = new LinkedList();
		        LinkedList platform = new LinkedList();
		        for (int i =0; i< length; i++) {
		            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
		            Set s = jsonObject.entrySet();
		            Iterator iter = s.iterator();
		            LinkedList ll = new LinkedList();
		            LinkedList lm = new LinkedList();
		            while(iter.hasNext()){
		                Map.Entry me = (Map.Entry) iter.next();
		                //System.out.println(me.getKey() + "  " + me.getValue());
		                ll.add(me.getValue());
		                lm.add(me.getKey());
		            }
		            browser.add(ll.get(0));
		            
		            port.add(ll.get(1));   
		            
		            platform.add(ll.get(2));    
		            
		        }
		        
		        for(int i=0;i<port.size();i++)
		        {
		        	innerMap = new LinkedHashMap<>();
		        	innerMap.put("Platform", (String) platform.get(i));
		        	innerMap.put("Browser", (String) port.get(i));
		        	innerMap.put("Port", (String) browser.get(i));
		        	webDriverNameList.add((String) browser.get(i));
		        	configMap.put("Config"+i, innerMap);
		        }
		        gridMap = configMap;
		    }catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
			return configMap;
	}


}