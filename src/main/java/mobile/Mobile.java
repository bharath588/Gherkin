package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lib.Log;
import lib.Log.Level;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.github.lalyos.jfiglet.FigletFont;

//208 Error :- Run this command in terminal :- ps -ax|grep -i "8100"|grep -v grep|awk '{print "kill -9 " $1}'|sh


public class Mobile {
	
	
	public static boolean reportStatus = false;
	public static boolean mobilePlatform = false;
	//public static AppiumDriver<MobileElement> appiumDriver;

	public static AppiumDriver<?> getDriver() {
		return  (AppiumDriver) Web.getDriver() ;
	}

	public static MobileDriver getMobileDriver() {
		return  (MobileDriver) Web.getDriver() ;
	}
	public static void tapElement(String locator) {
		tapWithPosition(100,300);
	//	TouchAction act = new TouchAction(getDriver());
		IOSElement ele = findElement(locator);
		if (ele != null) {
			  TouchAction tap = new TouchAction(getDriver());
		      tap.press(ele).waitAction(2000).release();
			
		} else {
			System.out.println("Element not Present :" + locator);
		}

	}

	public static void tapWithPosition(int x, int y) {
		try{
			getDriver().tap(1, 200, 400, 2);
//		TouchAction tAction = new TouchAction(getDriver());
//		tAction.longPress(x, y).waitAction().release().perform();
		}catch(Exception e){
			System.out.println("Exception while taping");
		}

	}

	public static void wait(int inmillSec) {
		try {
			Thread.sleep(inmillSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setEdit(String locator, CharSequence... sTextValue) {
		IOSElement ele = findElement(locator);
		
		try{
			if (ele != null) {	
			ele.clear();
			ele.sendKeys(sTextValue);
		
		} else {
			System.out.println("Element not Present :" + locator);
		}
	}catch(Exception e){
	Reporter.logEvent(Status.INFO, "Not able to set value in Text Field", sTextValue.toString(), true);		
	}	

	}
	

	public static void setValue(String locator, String sTextValue) {
		try {
			IOSElement ele = findElement(locator);
			if (ele != null) {
				ele.setValue(sTextValue);

				Reporter.logEvent(Status.INFO, "Enter Value  : " + sTextValue,
						"", true);
			} else {
				System.out.println("Element not Present :" + locator);
			}
		} catch (WebDriverException e) {
			System.out.println("Not able to set Value");

		}
	}

	public static String getText(String locator) {
		String actText = "";
		IOSElement ele = findElement(locator);
		if (ele != null) {
			actText = ele.getText();

		} else {
			System.out.println("Element not Present :" + locator);
		}
		return actText;

	}

	public static void setSlider(String locator, String sText) {
		try {
			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeSlider";
			IOSElement ele = findElement(sObj);
			if (ele != null) {
			
				ele.sendKeys(sText);
			
			} else {
				System.out.println("Element not Present :" + locator);
			}
            
			
		} catch (WebDriverException e) {
			System.out.println("WebDriverException is thrown" + locator);
		}

	}
	public static void setSliderValue(String locator, String sText) {
		try {
			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeTextField";
			IOSElement ele = findElement(sObj);
			if (ele != null) {
			    ele.clear();
				ele.sendKeys(sText);
			
			} else {
				System.out.println("Element not Present :" + locator);
			}
            
			
		} catch (WebDriverException e) {
			System.out.println("WebDriverException is thrown" + locator);
		}

	}	
	
		
	
	public static String getElementValue(String locator) {
		String sValue = null;
		IOSElement ele = findElement(locator);

		if (ele != null) {
			return ele.getAttribute("value");
		
		}
		return sValue;

	}
	
	public static String getElementValue(By locator) {
		String sValue = null;
		IOSElement ele = findElementBy(locator);

		if (ele != null) {
			return ele.getAttribute("value");
		
		}
		return sValue;

	}
	public static void clickElement(String locator) {
		IOSElement ele = findElement(locator);

		if (ele != null) {
			ele.click();			
		}

	}

	public static Boolean is_Element_Visible(String sLocator){
	
		IOSElement ele = findElement(sLocator);
		if(ele != null){
			if(ele.isDisplayed()){
				return true;
			}
		}else
			return false;
		
		return false;
	}
	
	public static Boolean is_Element_Displayed(By sLocator){
		
		IOSElement ele = findElementBy(sLocator);
		if(ele != null){
			if(ele.isDisplayed()){
				return true;
			}
		}else
			return false;
		
		return false;
	}
	
	
	
	
	/*@Author :Siddartha 
	 * @Date = 18/Jan /2017
	 * This method will verify expected text with actual text.
	 */
	
	public static void  verifyText(String locator,String sExpText,String sMsg,Boolean exactMatch){		
		IOSElement ele = findElement(locator);
		if (ele != null) {
			String sActText = ele.getText();
			if(exactMatch){
				if(sActText.equals(sExpText))
					Reporter.logEvent(Status.PASS, sMsg,sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected was  :" +sMsg +"  : "+sExpText ,"But Actual was :"+sActText, true);
		            }
			else{
				if(sActText.replaceAll("\\n", "").replaceAll(" ","").trim().toLowerCase().contains(sExpText.replaceAll(" ","").trim().toLowerCase()))
					Reporter.logEvent(Status.PASS, sMsg,sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected was  to contain  :  "+sExpText ,"But Actual was :"+sActText, true);
		    
				
			}
		}else{
			Reporter.logEvent(Status.FAIL, " Expected was   :"+sExpText ,"But actual was not found", true);
		}
	}
	
	public static void verify_Text_Is_Not_Displayed(String locator,String sExpText,String sMsg,Boolean exactMatch){		
		
		IOSElement ele = findElement(locator);
		if (ele != null) {
			String sActText = ele.getText();
			if(!sActText.replaceAll("\\n", "").replaceAll(" ","").trim().contains(sExpText.replaceAll(" ","").trim()))
				Reporter.logEvent(Status.PASS, sMsg,sExpText, false);
			else
				Reporter.logEvent(Status.FAIL, "Expected was  :" +sMsg +" "+sExpText ,"But Actual was :"+sActText, true);
		}
	}
	
	
	
	/*@Author :Siddartha 
	 * @Date = 18/Jan /2017
	 * This method will verify expected text with actual text.
	 */
	
	public static void  verifyText(String locator,String sExpText,Boolean exactMatch){		
		IOSElement ele = findElement(locator);
		if (ele != null) {
			String sActText = ele.getText();
			if(exactMatch){
				if(sActText.equals(sExpText))
					Reporter.logEvent(Status.PASS, "Expected Text to be  displayed :",sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual was :  "+sActText, true);
		            }
			else{
				
				if(sActText.replaceAll("\\n", "").replaceAll(" ","").trim().contains(sExpText.replaceAll(" ","").trim()))
					Reporter.logEvent(Status.PASS, "Expected Text was displayed  :",sActText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual was :  "+sActText, true);
		    
				
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual element was not found", true);
		}
	}
	
	
	
	public  static void verifyElementPresent(String sStep,String sObj,String sMsg){			
			
		if(Mobile.isElementPresent(sObj)){
			Reporter.logEvent(Status.PASS,sStep +"  Display ", sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep+"  Not Display  ",sMsg,true);
		          
		}
		
	}
	
	
public  static void verify_Element_Value(String sObj,String sExpText,String sMsg){			
		  String sActValue = getElementValue(sObj);
		if(sActValue != null){
		if(sActValue.replaceAll("\\n","").trim().equals(sExpText.trim())){
			Reporter.logEvent(Status.PASS," Expected  and Actual Value are Same", sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL," Expected Value :"+sExpText ,"But Actucal was:  "+sActValue,true);
		}
		}else{
		     Reporter.logEvent(Status.FAIL," Expected Value :"+sExpText ,"But Actucal was null",true);

		}
		
	}
	public  static void verifyElementNotPresent(String sStep,String sObj,String sMsg){			
		if(!isElementPresent(sObj)){
			Reporter.logEvent(Status.PASS,sStep+" Not Displayed  ",sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep +"Displayed ", sMsg,true);
		}
		
	}
	public  static void verifyElement_Is_Not_Displayed(String sStep,By sObj,String sMsg){			
		IOSElement ele =  findElementBy(sObj);
		if(ele ==null || !ele.isDisplayed()){
		
			Reporter.logEvent(Status.PASS,sStep+" Not Display  ",sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep +"  Display ", sMsg,true);
		}
		
	}
	
	 
    /*
	    * @Author :- Siddartha 
	     * @Date  :- 2 - Feb -2017
	    * @ Method:- This will wait till element is not display
	     */
	    
	      public static void verifyErrorMessage(String sObj,String sExpText,Boolean bPartial){
	    	 String sActText = getElementValue(sObj);
	    	 if(bPartial)
	    	 {
	    		 if(sActText.contains(sExpText))
	    			 Reporter.logEvent(Status.PASS, "Expected Error Message displayed",sExpText, false);
				  else
					  Reporter.logEvent(Status.FAIL, "Expected Error Message to be  :  "+sExpText  ,"But Actual was :  "+sActText, true);
	    			 
	    	 }
	    	 else {
	    	 if(sActText.equals(sExpText))
				  Reporter.logEvent(Status.PASS, "Expected Error Message displayed : ","sExpText", false);
			  else
				  Reporter.logEvent(Status.FAIL, "Expected Error Message to be  :"+sExpText  ,"But Actual was :  "+sActText, true);
			}
	      }
	           
	      /*
		    * @Author :- Siddartha 
		     * @Date  :- 2 - Feb -2017
		    * @ Method:- This will wait till element is not display
		     */
		    
		      public static void verifyWarningMessage(String sObj,String sExpText,Boolean exactMatch){
		    	 String sActText = getElementValue(sObj);
		    	 
		    	 if(!exactMatch)
		    	 {
		    		 if(sActText.contains(sExpText))
		    			 Reporter.logEvent(Status.PASS, "Expected Warning Message : "+sExpText,"", false);
					  else
						  Reporter.logEvent(Status.FAIL, "Expected Warning Message : "+sExpText  ,"But Actual was : "+sActText, true);
		    			 
		    	 }
		    	 else {
		    	 if(sActText.equals(sExpText))
					  Reporter.logEvent(Status.PASS, "Expected Warning Message : "+sExpText,"", false);
				  else
					  Reporter.logEvent(Status.FAIL, "Expected Warning Message : "+sExpText  ,"But Actual was : "+sActText, true);
				}
		      }
		          
	        
	
	
	
	public static void  verifyElementEnable(String locator,String sMsg){		
		IOSElement ele = findElement(locator);
		if (ele != null) {
		  if(ele.isEnabled())
			  Reporter.logEvent(Status.PASS, sMsg,"", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg,"", true);
		}
	
		
  		
  	}
	public static void  verifyElementISDisable(String locator,String sMsg){	
		IOSElement ele = findElement(locator);
		if (ele != null) {
		  if(!ele.isEnabled())
			  Reporter.logEvent(Status.PASS, "Element is Disable",sMsg, false);
		  else
			  Reporter.logEvent(Status.FAIL, "Element is Not Disable",sMsg, true);
		}
	
		
  		
  	}
	// pressed x axis & y axis of seekbar and move seekbar till the end

	public static void slider(String locator, int toPercentage) {
		try {

			TouchAction act = new TouchAction((MobileDriver) getDriver());

			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeSlider";
			IOSElement ele = findElement(sObj);
			System.out.println(ele.getText());

			int xActualPer = Integer.parseInt(ele.getText().replace("%", ""));

			int xAxisStartPoint = ele.getLocation().getX();
			int xAxisEndPoint = xAxisStartPoint + ele.getSize().getWidth();
			int yAxis = ele.getLocation().getY();
			int xAxis = xAxisEndPoint - xAxisStartPoint;
			System.out.println(xAxis);

			int fromX = xAxis * xActualPer / 100 + xAxisStartPoint;

			int toX = xAxis * toPercentage / 100 + xAxisStartPoint;

			System.out.println(fromX);
			System.out.println(toX);

			// TouchAction act=new TouchAction(driver);

			act.press(fromX, yAxis).waitAction(500).moveTo(toX, yAxis)
					.release().perform();
		} catch (Exception e) {
			System.out.println("Not able to slide the slider");
		}

	}

	public static boolean isElementPresent(String locator) {
		IOSElement ele = findElement(locator);
		Boolean flag = false;
		if (ele != null) {
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Display :" + locator);
				flag = false;
			}
		} 
		return flag;		
	}
	
	public static boolean isElementPresent(String locator,int timeInSecond) {
		IOSElement ele = findElement(locator);
		Boolean flag = false;
		
		
			for(int i = 0;i<timeInSecond;i++){
				if (ele == null){
			      ele= findElement(locator);
				}else{
					break;
			}
				
		}
		
		
		if (ele != null) {
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Display :" + locator);
				flag = false;
			}
		} 
		return flag;		
	}
	
	
	
	public static IOSElement findElementBy(By link){
		try{
			getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		return (IOSElement) getDriver().findElement(link);
		}catch(NoSuchElementException e){
		return null;
		}
	}
	
	public static boolean assertElementPresentByAccessibilityId(String link) {
		Boolean flag = false;

		try{
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		IOSElement ele = (IOSElement) getDriver().findElementByAccessibilityId(link);

		if (ele != null) {
			if(!ele.isDisplayed()){
				scrollTo(ele);
			}
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Displaying : "+link );
				flag = false;
			}
		} 
		
	}  catch(Exception e){
		System.out.println("Not Display :" );
	}
		return flag;
	}
	
	
	
	public static boolean assertElementPresent(By link) {
		Boolean flag = false;

		try{
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		IOSElement ele = (IOSElement) getDriver().findElement(link);
		
		if (ele != null) {
			int j =0;
			while (!ele.isDisplayed() && j<3) {	 
          		scrollTo(ele);	
          		if(j == 1 ){
          			Mobile.scroll_Down();
          		}
          		if(j  == 2 ){
          			Mobile.scroll_UP();
          		}
               j++;
			}			
						
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Displaying : "+link );
				flag = false;
			}
		}
		
	}  catch(Exception e){
		System.out.println("Not Display :" );
	}
		return flag;
	}
	
	
	
	
	
	public static boolean assertElementsPresent(String... elements) {
		Boolean flag = false;
		try{
		for (String element : elements) {			  
			IOSElement ele = findElementBy(By.name(element));
			if (ele != null) {
				return true;
			}			
		}	
		
		}catch(Exception e){
			System.out.println("Not Display :" );
		}
			return flag;
		}
	
		

		
	

	public static void waitForPageToLoad(String locator) {
		try {
			IOSElement id = findElement(locator);
			WebDriverWait wait = new WebDriverWait(getDriver(),
					Integer.parseInt(Stock.getConfigParam("objectSyncTimeout")));
			wait.until(ExpectedConditions.elementToBeClickable(id));
		} catch (Exception e) {
			System.out.println("Not able to  load the Page waitForPageToLoad");
		}
	}

	public void waitForElementToDisAppear(String id) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
	}

	public static IOSElement findElement(String locator) {
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		IOSElement element = null;
		String LocatorType = "AccessibilityID";
		
		if(locator.contains("//")){
			LocatorType = "Xpath";
		}
		
		for (int i = 0; i < 3; i++) {
			try {
				
				if(LocatorType.equals("AccessibilityID")){
					element = (IOSElement) getDriver().findElementByAccessibilityId(
							locator);
				}
				else if(LocatorType.equals("Xpath")){
					element = (IOSElement) getDriver()
							.findElementByXPath(locator);
				}
				

//				try {
//					// System.out.println("Name");
//					element = (IOSElement) getDriver().findElementByAccessibilityId(
//							locator);
//				} catch (Exception e1) {
//					try {
//						// System.out.println("Accessibility");
//						element = (IOSElement) getDriver()
//								.findElementByXPath(locator);
//
//					} catch (Exception e2) {
//						element = null;
//
//					}
//						try {
//							// System.out.println("xpath");
//							
////							element = (IOSElement) getDriver()
////									.findElementByName(locator);
//
//						} catch (Exception e3) {
//
//							try {
//								// System.out.println("ID");
////								element = (IOSElement) getDriver()
////										.findElementById(locator);
//
//							} catch (Exception e4) {
//
//								element = null;
//
//							}
	//					}
		//			}
	//			}
			} catch (Exception e) {
				System.out.println(locator + "  Not found at FindElement function");

			}

			if (element != null) {
				int j =0;
				while (!element.isDisplayed() && j<3) {	 
//              		scrollTo(element);	
//              		if(j == 1 ){
              			Mobile.scroll_Down();
              	//	}
              		if(j == 2 ){
              			Mobile.scroll_UP();
              			
              		}
                   j++;
				}
				getDriver().manage().timeouts()
						.implicitlyWait(10, TimeUnit.SECONDS);
				return element;
			}
		}
		

		return element;

	}

	public static void hideKeyboard() {
		getDriver().getKeyboard().pressKey("\n");
	
			wait(2000);
	
	}

	/**
	 * <pre>
	 * Method to return iOS web element
	 * </pre>
	 * 
	 * @param ele
	 * @return
	 */
	public static IOSElement getIOSElement(By by) {
		int waitTime = 5;	

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return (IOSElement) getDriver().findElement(by);
		} catch (org.openqa.selenium.TimeoutException e) {
			return null;
		}

	}

	private static void onTap(WebElement element) {
		try {

			TouchAction tAction = new TouchAction((MobileDriver) getDriver());

			Point loc = element.getLocation();
			int x = loc.getX();
			int y = loc.getY();
			// driver.tap(x, element, y);
			tAction.tap(element).release().perform();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to click");
		}
	}

	public static void scrollTo(IOSElement scrollToElement) {  
		try{
			
						
			
//			Dimension size = getDriver().manage().window().getSize();
//			int starty = (int) (size.height * 0.7);
//			int endy = (int) (size.height * 0.3);
//			int startx = size.width / 2;
//			getDriver().swipe(startx, starty, startx, endy, 2000);

			
//		Dimension dimensions = getDriver().manage().window().getSize();
//		Double screenHeightStart = dimensions.getHeight() * 0.6;
//		int scrollStart = screenHeightStart.intValue();
//		Double screenHeightEnd = dimensions.getHeight() * 0.2;
//		int scrollEnd = screenHeightEnd.intValue();
////		TouchAction tAction = new TouchAction((MobileDriver) getDriver());
//	//	tAction.press(0, scrollStart).waitAction().moveTo(0,scrollEnd).release().perform();
	//	  HashMap<String, String> scrollByObj = new HashMap<String, String>();
//	  scrollByObj.put("element", "//XCUIElementTypeSwitch");
//	  scrollByObj.put("toVisible", "true");
//	  
//		
//		getDriver().execute("mobile: scroll",scrollByObj);
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			HashMap<String, String> scrollObject = new HashMap<String, String>();		
			scrollObject.put("element",  scrollToElement.getId());
			scrollObject.put("toVisible", "true");
			js.executeScript("mobile: scroll", scrollObject);
				
			
		}catch(Exception e){
			System.out.println("Not able to swipe");
		}
    
	}

	public void swipeLeftUntilTextExists(String expected) {
		do {
			swipeLeft();
		} while (!getDriver().getPageSource().contains(expected));
	}

	public static void swipeRightUntilLogOutScreen() {
		do {
			swipeRight();
		} while (!isElementPresent(""));
	}

	
	public static void scrollTillElementIsDisplay(By link){
		
		Dimension size = getDriver().manage().window().getSize();
		int starty = (int) (size.height * 0.7);
		int endy = (int) (size.height * 0.3);
		int startx = size.width / 2;
		
		IOSElement ele = findElementBy(link);
		if(ele != null){
			if(!ele.isDisplayed())
			getDriver().swipe(startx, starty, startx, endy, 2000);
		}
		
		
	}
public static void scroll_UP(){
		
	JavascriptExecutor js = (JavascriptExecutor) getDriver();
	HashMap<String, String> scrollObject = new HashMap<String, String>();
	scrollObject.put("direction", "up");
	js.executeScript("mobile: scroll", scrollObject);
}

public static void scroll_Down(){
	
	JavascriptExecutor js = (JavascriptExecutor) getDriver();
	HashMap<String, String> scrollObject = new HashMap<String, String>();
	scrollObject.put("direction", "down");
	js.executeScript("mobile: scroll", scrollObject);
}
	
	
	public static void swipeRight() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.9);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		getDriver().swipe(startx, starty, endx, starty, 2000);
	}

	public static void tapAtCenter(){
		try{
//		Dimension size = getDriver().manage().window().getSize();
//		int startx = (int) (size.width * 0.5);
//		int endx = (int) (size.width * 0.5);		
		getDriver().tap(1, 150, 300, 2000);
		getDriver().swipe(150, 300, 180, 300, 2000);
		}
		catch(Exception e){
			
		}
	}
	public static void swipeLeft() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		getDriver().swipe(startx, starty, endx, starty, 1000);
	}

	/**
	 * method to set the context to required view.
	 *
	 * @param context
	 *            view to be set
	 */
	public static void setContext(String context) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> contextNames = getDriver().getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like
												// NATIVE_APP \n WEBVIEW_1
		}
		getDriver().context((String) contextNames.toArray()[1]); // set context
																	// to
																	// WEBVIEW_1

		Log.Report(Level.INFO, "Current context" + getDriver().getContext());
	}

	public static void sendKey(String locator, CharSequence... sTextValue) {
		IOSElement ele = findElement(locator);
		if (ele != null) {

			ele.sendKeys(sTextValue);
		}
		Reporter.logEvent(Status.INFO, "Enter Value  : " + sTextValue, "", true);
	}

	public static void DataPicker(String locator, String sTextValue) {

		String value0 = sTextValue;
		String temp0 = "";
		while (!temp0.equals(value0)) {
			try {
				sendKey(locator, sTextValue);

			} catch (Exception e) {
				// Do Nothing
			} finally {
				temp0 = getText(locator);
				System.out.println("temp0" + temp0);
			}
		}
	}

	
	

	

	public static void clickDeleteImage(String sLabel) {
		String sObj = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeButton";
		clickElement(sObj);

	}

	public static void clickDeleteButton(String sLabel) {
		String sObj = "//*[@name='" + sLabel
				+ "']/following-sibling::XCUIElementTypeButton[@name='DELETE']";
		clickElement(sObj);

	}

	

	public static void AllFund(String sLabel, Boolean bValue) {
		String MONTH_RADIO = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeCell";
		String isSelected = "//*[@name='"
				+ sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage[@name='select-on']";
		try {
			IOSElement eleCheckBoxEle = findElement(MONTH_RADIO);
			IOSElement isSelectObjEle = findElement(isSelected);
			boolean sChecked = false;
			if (isSelectObjEle != null) {
				sChecked = isSelectObjEle.isDisplayed();
			}
			if (bValue == true) {
				if (!sChecked == true) {
					eleCheckBoxEle.click();
				}
			} else {
				if (sChecked == true) {
					eleCheckBoxEle.click();
				}
			}
		} catch (Exception e) {
			System.out.println("Not able to Select ");
		}

	}

	public static void checkItem(String sLabel, Boolean bValue) {
		String MONTH_RADIO = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage";
		String isSelected = "//*[@name='"
				+ sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage[@name='select-on']";
		try {
			IOSElement eleCheckBoxEle = findElement(MONTH_RADIO);
			IOSElement isSelectObjEle = findElement(isSelected);
			boolean sChecked = false;
			if (isSelectObjEle != null) {
				sChecked = isSelectObjEle.isDisplayed();
			}
			if (bValue == true) {
				if (!sChecked == true) {
					eleCheckBoxEle.click();
				}
			} else {
				if (sChecked == true) {
					eleCheckBoxEle.click();
				}
			}
		} catch (Exception e) {
			System.out.println("Not able to Select ");
		}

	}
	
	public static void selectRadioButton(String sLabel) {
		//String sRadioButtonNotSelected = "//*[contains(@name,'"+sLabel+"')]/preceding-sibling::XCUIElementTypeButton";		
		String sRadioButtonNotSelected = "//*[contains(@name,'"+sLabel+"')]";		
		
		IOSElement eleRadioOption = findElement(sRadioButtonNotSelected);
		if(eleRadioOption != null){
			eleRadioOption.click();
		}
		
	}
	
	public static void switchButton(String sObj, Boolean bValue) {

		String isSelected = getElementValue(sObj);
		if (Boolean.valueOf(isSelected) != bValue) {

			try {
				IOSElement eleCheckBoxEle = findElement(sObj);
				if (eleCheckBoxEle != null)
					eleCheckBoxEle.click();
				     wait(2000);

			} catch (Exception e) {
				System.out.println("Not able to Switch Button");
			}
		}

	}
	
	public static void verify_Element_Is_Selected(String sObj,String sMsg){
		String isSelected = getElementValue(sObj);
		if (Boolean.valueOf(isSelected) == true) 	
			Reporter.logEvent(Status.PASS, "Selected ",sMsg, false);
		  else
			  Reporter.logEvent(Status.FAIL, "Not Selected",sMsg, true);
		
	}
	
	public static void verify_Element_Is_Not_Selected(String sObj,String sMsg){
		String isSelected = getElementValue(sObj);
		if (Boolean.valueOf(isSelected) == false) 	
			Reporter.logEvent(Status.PASS, sMsg,"Element should not be selected", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg,"Elemnent is Selected", true);
		
	}
	
	public static void verify_SwitchButton_Selected(String sObj,Boolean bStatus, String sMsg){
		String isSelected = getElementValue(sObj);
		String sVal= "0";
		if(bStatus){
			sVal="1";
		}
		if (sVal.equals(isSelected)) 	
			Reporter.logEvent(Status.PASS, "Switch Option should be  "+bStatus,sMsg, false);
		  else
			  Reporter.logEvent(Status.FAIL, "Switch Option is not Displayed as expected : "+bStatus,"But Actual was :"+isSelected, true);
		
	}	
	
	 public static void figlet(String text) {
	        String asciiArt1 = null;
	        try {
	            asciiArt1 = FigletFont.convertOneLine(text);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println(asciiArt1);
	    }
	
	 
}
