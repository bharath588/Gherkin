package tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import core.framework.Globals;

public class Validate_ParticipantHomePage {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		Web.webdriver = Web.getWebDriver(Stock.globalParam.get("BROWSER"));
	}
	
	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}
	
	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getName(), testCase.getName());
	}
	
	@BeforeMethod
	public void getTCName(Method tc) {
	       tcName = tc.getName(); 
	       System.out.println("get TCname");
	}
	
	@Test(dataProvider = "setData")
  public void f(int itr, Map<String, String> testdata) {
	  
	  System.out.println("This is a sample test - f()!");
	  
  }
}
