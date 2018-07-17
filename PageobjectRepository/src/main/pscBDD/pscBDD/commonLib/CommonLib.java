/**
 * 
 */
package pscBDD.commonLib;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Stock;
import lib.Web;
import gherkin.formatter.model.Scenario;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*import pageobject.pptweb.LeftNavigationBar;*/






import bdd_core.framework.Globals;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;




import cucumber.api.java.Before;

/**
 * @author rvpndy
 *
 */
public class CommonLib {
	
	
	public static WebDriver webDriver;
	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;
	
	
	
	@FindBy(xpath="//button[text()[normalize-space()='Dismiss']]") public static WebElement lnkDismiss;
	@Before("@Login")
	public void before(cucumber.api.Scenario scenario) {
		featureName=scenario.getId().split(";")[0];
		scenarioName=scenario.getName();
		System.out.println(scenario.getId());
		Reporter.initializeModule(featureName);
		System.setProperty("webdriver.chrome.driver","H:\\common\\Automation-Selenium\\WebDrivers\\chromedriver_2.28.exe");
		if(webDriver==null)
			webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		
	}
	
	
	
	public CommonLib(WebDriver webDriver){
		CommonLib.webDriver = webDriver;
	}
	
	/**
	 * <pre>
	 * Method to check if element exists on the page and is displayed
	 * </pre>
	 * 
	 * @param element
	 *            - WebElement object which is to be check for
	 * @param waitForElement
	 *            - <b>true</b> if user wants to wait for the element before
	 *            checking if it is displayed. <b>false</b> otherwise
	 * @return <b>boolean - true</b> if element is displayed on the page.
	 *         <b>false</b> otherwise
	 */
	public boolean isWebElementDisplayed(WebElement element,
			boolean... waitForElement) {
		boolean blnElementDisplayed = false;
		if(element==null){
			return false;
		}
		try {
			try {
				if (waitForElement.length > 0) {
					if (waitForElement[0] == true) {
						waitForElement(element);
					}
				}

			} catch (Exception e) {
				// Do nothing
			}
			blnElementDisplayed = element.isDisplayed();
		} catch (NoSuchElementException e) {
			blnElementDisplayed = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return blnElementDisplayed;
	}
	
	/**
	 * Method to wait for the specified element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public void waitForElement(WebElement element) {
		try {
			(new WebDriverWait(CommonLib.webDriver, Long.parseLong("30"))).ignoring(
							StaleElementReferenceException.class).until(
									ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			//			e.printStackTrace();
		}
	}
	
	public static void FrameSwitchONandOFF(boolean switchFrame,WebElement... frm){
		if(frm.length>0){
			if(Web.isWebElementDisplayed(frm[0],false) && switchFrame){
				Web.getDriver().switchTo().frame(frm[0]);
			}
		}

		if(!switchFrame){
			Web.getDriver().switchTo().defaultContent();
		}		
	}
	
	/**
	 * <pre>
	 * Method to set text to specified text box element.
	 * Returns value of <b>Value</b> attribute after setting provided text.
	 * Returns empty string if unable to set text.
	 * </pre>
	 * 
	 * @param textBoxField
	 *            - Text box WebElement
	 * @param valueToSet
	 *            - Value/String to be set
	 * @return <b>Value</b> - Text set in <b>value</b> attribute of text box.
	 */
	public String setTextToTextBox(WebElement textBoxField,
			CharSequence... valueToSet) {
		String fieldTextValue = "";
		if (isWebElementDisplayed(textBoxField)) {
			textBoxField.clear();
			textBoxField.sendKeys(valueToSet);
			textBoxField.click();
			fieldTextValue = textBoxField.getAttribute("value");
		}
		return fieldTextValue;
	}
	
	/**
	 * Method to wait for the specified element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public void waitForElementEnablity(WebElement element) {
		try {
			(new WebDriverWait(webDriver, Long.parseLong("30"))).ignoring(
							StaleElementReferenceException.class).until(
									ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			// 			e.printStackTrace();
		}
	}
	
	/**
	 * @author srsksr
	 */
	 private static String progressBar ="//*[@class='loader']";
       
     public static void waitForProgressBar(){
   	  int iTimeInSecond=100;
          try{
                 int iCount = 0;
                 while (FindElement(progressBar).isDisplayed()){
               	  if (Web.isWebElementDisplayed(lnkDismiss)) {
               		  Web.clickOnElement(lnkDismiss);
             		}
                        if(iCount ==iTimeInSecond){
                              break;
                        }   
                        
                        System.out.println("Progress Bar displaying..");
                        Thread.sleep(1000);                       
                        iCount++;
                 }
                 
          }catch(Exception e){
                 
          }
          
       }
     public static WebElement FindElement(String sElement){
         return lib.Web.getDriver().findElement(By.xpath(sElement));
      }
     
     public static boolean isAlerPresent(){
         try{
                Web.getDriver().switchTo().alert();
                return true;
         }catch(NoAlertPresentException ex){
                return false;
         }
         
   }
   public  static void HandlePopAlert(){
         try{
                Alert alert = Web.getDriver().switchTo().alert();
                String sPopText = alert.getText();
                System.out.println(sPopText);
                alert.accept();
         }catch(Exception e){
         
         }
         
   }

 /*  public  static void handlePageToLoad(String pageName){
       try{
            LeftNavigationBar lftbar=new LeftNavigationBar();
              lftbar.clickNavigationLink("Rate Of Return");
              waitForProgressBar();
              Web.waitForPageToLoad(Web.getDriver());
              lftbar.clickNavigationLink(pageName);
              waitForProgressBar();
              Web.waitForPageToLoad(Web.getDriver());
       }catch(Exception e){
       
       }
       
 }*/
   public static String getParticipantDBName(String userName) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;

		ResultSet participantDB = null;

		try {
			sqlQuery = Stock.getTestQuery("getPartcipantDBInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],userName);
		if (DB.getRecordSetCount(participantDB) > 0) {
			try {
				participantDB.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		System.out.println("DATA BASE Name"
				+ participantDB.getString("database_instance"));
		return participantDB.getString("database_instance");
	}
   
   public static String checkEnv(String envName) {
		if (envName.contains("PROJ")) {
			return Globals.DB_TYPE.get("PROJ");
		}
		if (envName.contains("QA")) {
			return Globals.DB_TYPE.get("QA");
		}
		if (envName.contains("PROD")) {
			return Globals.DB_TYPE.get("PROD");
		}
		return null;
	}
   
   /**
    * Searches an array of words for a given value using a recursive binary 
    * search.  Returns the index that contains the value or -1 if the value 
    * is not found.
    * @param words
    * @param value
    * @return
    */    
   public static int binarySearch(String[] words, String value, int min, int max) {
       if (min > max) {
           return -1;
       }
       
       int mid = (max + min) / 2;
       
       if (words[mid].equals(value)) {
           return mid;
       } else if(words[mid].compareTo(value) > 0) {
           return binarySearch(words, value, min, mid - 1);
       } else {
           return binarySearch(words, value, mid + 1, max);
       }
   }
}
