package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.github.lalyos.jfiglet.FigletFont;

/**
 * Class contain all the action command for iOS mobile App
 * 
 * /08 Error :- Run this command in terminal :-
 *  ps -ax|grep -i "8100"|grep -v grep|awk '{print "kill -9 " $1}'|sh

 * @author siddartha 
 *
 */


public class Mobile {
	
	public static int i =0;
//	public static boolean reportStatus = false;
	public static boolean mobilePlatform = false;

	public static AppiumDriver<?> getDriver() {
		return  (AppiumDriver<?>) Web.getDriver() ;
	}

	public static MobileDriver<?> getMobileDriver() {
		return  (MobileDriver<?>) Web.getDriver() ;
	}
	
	

	/**
	 * Method to wait for given time in millsec
	 * @param inmillSec
	 */

	public static void wait(int inmillSec) {
		try {
			Thread.sleep(inmillSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Method to set value in given locator 
 * @param locator  is element 
 * @param sTextValue is input value
 */
	
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
	
	/**
	 * Method to set value in given MobileElement using sendKey command
	 * @param ele
	 * @param sTextValue
	 */
	
	public static void setEdit(MobileElement ele, String sTextValue) {
			
		try{
			if (ele != null) {	
			ele.clear();
			ele.sendKeys(sTextValue);
		
		} else {
			System.out.println("Not able to set Value in Text Field:" );
		}
	}catch(Exception e){
		try{
		ele.sendKeys(sTextValue);
		}
		catch(Exception ex){
				Reporter.logEvent(Status.INFO, "Not able to set value in Text Field", sTextValue, true);	
		}
	}	

	}
	

	/**
	 * Method to set value in given  using setvalue command
	 * @param ele
	 * @param sTextValue
	 */
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

	/**
	 * Method to get Text from particular element
	 * @param locator
	 * @return
	 */
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
	
	/**
	 * Set value in slider in TextField
	 * @param locator
	 * @param sText
	 */
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
	
	/**
	 * Set value in slider in TextField using MobileElement
	 * @param locator
	 * @param sText
	 */	
	
	public static void setSliderValue(MobileElement ele, String sText) {
		try {
		
			if (ele != null) {
			    ele.clear();
				ele.sendKeys(sText);
				Mobile.clickElement("Done");
			
			} 
			
		Reporter.logEvent(Status.INFO," Value set in slider bar.",sText + " %",false);			
		} catch (WebDriverException e) {
			System.out.println(" Not able to slide the Value");
		}

	}
		
	/**
	 *  Method will get element value 
	 * @param locator
	 * @return
	 */
	public static String getElementValue(String locator) {
		String sValue = "";
		IOSElement ele = findElement(locator);

		if (ele != null) {
			sValue= ele.getAttribute("value");
		
		}
		return sValue;

	}
	
	/**
	 *  Method will get element value using MobileElement
	 * @param locator
	 * @return
	 */
	public static String getElementValue(MobileElement ele) {
		String sValue = "";
		

		if (ele != null) {
			sValue= ele.getAttribute("value");
		
		}
		return sValue;

	}
	
	/**
	 *  Method will get element value using By locator
	 * @param locator
	 * @return
	 */
		
	public static String getElementValue(By locator) {
		String sValue = null;
		IOSElement ele = findElementBy(locator);

		if (ele != null) {
			return ele.getAttribute("value");
		
		}
		return sValue;

	}
	
	
	/**
	 *  Method will get element value using By locator
	 * @param locator
	 * @return
	 */
		
	public static String getElementValueByPredicate(String sLabel  ) {
		String sValue = null;
		IOSElement ele =       findElementWithPredicate(sLabel);
	//	IOSElement ele = findElementBy(locator);

		if (ele != null) {
			return ele.getAttribute("value");
		
		}
		return sValue;

	}
	
	/**
	 * Method will click element using String as parameter
	 * @param locator
	 */
	public static void clickElement(String locator) {
		IOSElement ele = findElement(locator);
		if (ele != null) {
			ele.click();			
		}

	}
	/**
	 * Method will click element using MobileElement as parameter
	 * @param locator
	 */

	public static void clickElement(MobileElement locator) {
		if (locator != null) {
		try{
			locator.click();	
		}catch(Exception e){
			
		}
		}

	}
	/**
	 * Method will verify is element is enable using String as param
	 * @param sLocator is String 
	 * @return
	 */

	public static Boolean is_Element_Enable(String sLocator){
		
		IOSElement ele = findElement(sLocator);
		if(ele != null){
			if(ele.isEnabled()){
				return true;
			}
		}else
			return false;
		
		return false;
	}
	
	/**
	 * Method will verify is element is enable using MobileElement as param
	 * @param sLocator is MobileElement 
	 * @return
	 */
	public static Boolean is_Element_Enable(MobileElement ele){
		
		if(ele != null){
			if(ele.isEnabled()){
				return true;
			}
		}else
			return false;
		
		return false;
	}


	/**
	 * Method will verify is element is Display using String as param
	 * @param sLocator is String 
	 * @return
	 */
	
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
	
	/**
	 * Method will verify is element is Display using By locator as param
	 * @param sLocator is By 
	 * @return
	 */
	
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
	
	/**
	 * Method will verify is element is Display using MobileElement as param
	 * @param sLocator is MobileElement 
	 * @return
	 */
	
	public static Boolean is_Element_Displayed(MobileElement ele){
	
		
		if(ele != null){
			try{
			if(ele.isDisplayed()){
				return true;				
				}
			else
			return false;
		
		}catch(Exception e )
			{
			return false;
			}
			}
			return false;
		
		
	
	}
		
	/**
	 * Method will verify Text with Actual and expected value for given element	 
	 * 
	 * @param locator i
	 * @param sExpText
	 * @param sMsg
	 * @param exactMatch
	 */
	
	
	public static void  verifyText(String locator,String sExpText,String sMsg,Boolean exactMatch){		
		IOSElement ele = findElement(locator);
		if (ele != null) {
			String sActText = ele.getText();
			if(exactMatch){
				if(sActText.equals(sExpText))
					Reporter.logEvent(Status.PASS, sMsg,sActText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected was  :" +sMsg +"  : "+sExpText ,"But Actual was :"+sActText, true);
		            }
			else{
				if(sActText.replaceAll("\\n", "").replaceAll(" ","").trim().toLowerCase().contains(sExpText.replaceAll(" ","").trim().toLowerCase()))
					Reporter.logEvent(Status.PASS, sMsg,sActText, false);
				else
					Reporter.logEvent(Status.FAIL, sMsg,"Expected was  to contain  :  "+sExpText +"\n But Actual was :"+sActText, true);
		    
				
			}
		}else{
			Reporter.logEvent(Status.FAIL, sMsg  ,"Expected was   :"+sExpText+" But actual was not found", true);
		}
	}
	
	/**
	 * Method will verify if Text is not displayed
	 * @param locator
	 * @param sExpText
	 * @param sMsg
	 * @param exactMatch
	 */
	
	
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
	
	/**
	 * This method will verify expected text with actual text.
	 * @param locator
	 * @param sExpText
	 * @param exactMatch
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
					Reporter.logEvent(Status.PASS, "Expected Text was displayed  :",sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual was :  "+sActText, true);
		    
				
			}
		}else{
			Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual element was not found", true);
		}
	}
	
/**
 * This method will verify expected text with actual text.
 * @param ele is MobileElement
 * @param sExpText
 * @param exactMatch
 */
	
	public static void  verifyText(MobileElement ele,String sExpText,Boolean exactMatch){		
	//	IOSElement ele = findElement(locator);
		try{	
			if(ele != null){
			String sActText = ele.getText();
			if(exactMatch){
				if(sActText.equals(sExpText))
					Reporter.logEvent(Status.PASS, "Expected Text to be  displayed :",sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual was :  "+sActText, true);
		            }
			else{
				
				if(sActText.replaceAll("\\n", "").replaceAll(" ","").trim().contains(sExpText.replaceAll(" ","").trim()))
					Reporter.logEvent(Status.PASS, "Expected Text was displayed  :",sExpText, false);
				else
					Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual was :  "+sActText, true);
		    
				
			}
		}
		}catch(NoSuchElementException e){
			Reporter.logEvent(Status.FAIL, "Expected text to be :"+sExpText ,"But Actual element was not found", true);
		}
	}
	
	
	/**
	 * Method will verify element is present 
	 * @param sStep
	 * @param sObj is String
	 * @param sMsg
	 */
	
	public  static void verifyElementPresent(String sStep,String sObj,String sMsg){			
			
		if(Mobile.isElementPresent(sObj)){
			Reporter.logEvent(Status.PASS,sStep ,sMsg+ "  is Displayed " ,false);
		}else{			
		     Reporter.logEvent(Status.FAIL,sStep,sMsg+"  is not Displayed",true);
					          
		}
		
	}
	

	
	
	/**
	 *  Method will verify element is present 
	 * @param sStep
	 * @param sObj is MobileElement
	 * @param sMsg
	 */
	
	
	public  static void verifyElementPresent(String sStep,MobileElement sObj,String sMsg){			
		try{
		if(sObj != null){
			if(sObj.isDisplayed()){
			Reporter.logEvent(Status.PASS,sStep ,sMsg+ "  is Displayed " ,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep,sMsg+"  is not Displayed",true);
		          
		}
		}else{
			   Reporter.logEvent(Status.FAIL,sStep,sMsg+"  is not Displayed",true);
		}
		}catch(NoSuchElementException e){
			   Reporter.logEvent(Status.FAIL,sStep,sMsg+"  is not Displayed",true);
		}
		
	}
	
	/**
	 * Method to verify element value 
	 * @param sObj is String 
	 * @param sExpText
	 * @param sMsg
	 */
	
	public  static void verify_Element_Value(String sObj,String sExpText,String sMsg){	
	
		  String sActValue = getElementValue(sObj);
		if(sActValue != null){
		if(sActValue.replaceAll("\\n","").trim().equals(sExpText.trim())){
			Reporter.logEvent(Status.PASS,sMsg, "Actual Value :  "+sActValue,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : "+sExpText +"\n But Actucal was:  "+sActValue,true);
		}
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : "+sExpText +"\nBut Actual it was not present",true);

		}
		}
	
	/**
	 * Method to verify element value
	 * @param Obj as MobileElement
	 * @param sExpText
	 * @param sMsg
	 */

	public  static void verify_Element_Value(MobileElement Obj,String sExpText,String sMsg){	
		String sActValue ="";
		if(Obj != null)	
	 sActValue = Obj.getAttribute("value");
	
	if(sActValue != null){
	if(sActValue.replaceAll("\\n","").trim().equals(sExpText.trim())){
		Reporter.logEvent(Status.PASS,sMsg, "Expected value is displayed :\n  "+sActValue,true);
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : \n "+sExpText +"\n But Actucal was: \n "+sActValue,true);
		}
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : \n "+sExpText +"\nBut Actual it was not present",true);

		}
		
	}



	public  static void verifyElementNotPresent(String sStep,String sObj,String sMsg){			
		if(!isElementPresent(sObj)){
			Reporter.logEvent(Status.PASS,sStep,"Not Displayed :  " +sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep ," Displayed : "+ sMsg,true);
		}
		
	}
	
	
	public  static void verifyElementNotPresent(String sStep,MobileElement sObj,String sMsg){	
		if(sObj != null)
			try{
		if(!sObj.isDisplayed()){
			Reporter.logEvent(Status.PASS,sStep,"Not Displayed :  " +sMsg,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep ," Displayed : "+ sMsg,true);
		}
		}catch(NoSuchElementException ex){
			Reporter.logEvent(Status.PASS,sStep,"Not Displayed :  " +sMsg,false);
		}
		
	}
	

	/**
	 * Method to click on a specified web element.
	 * 
	 * @param pageClassObj
	 *            - Object of the Page class
	 * @param webElementName
	 *            - Name of the web element to be clicked
	 * @return <b>true</b> if successful, <b>false</b> otherwise.
	 * @throws Exception
	 */
	public static boolean clickOnElement(Object pageClassObj,
			String webElementName) {
		boolean success = false;
		if (webElementName != null) {
			WebElement clickableElement = getPageObjectFields(pageClassObj,
					webElementName);
			if (Web.isWebElementDisplayed(clickableElement)) {
				clickableElement.click();
				success = true;
			}
		}
		return success;
	}
	
	/**
	 * <pre>
	 * Method to get declared WebElement from a specified page.
	 * Returns corresponding <b>WebElement</b> from <b>getWebElement</b> method available in PageObject class.
	 * Returns null is case of no element found in the page
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param fieldName
	 *            - Name of the Element listed in getWebElement method
	 * @return <b>WebElement</b> - Corresponding WebElement mapped against the
	 *         fieldName
	 * @throws Exception
	 */
	private static MobileElement getPageObjectFields(Object pageObjectClass,
			String fieldName) {
		Method getWebElementMethod = null;
		MobileElement element = null;
		try {
			getWebElementMethod = pageObjectClass.getClass().getDeclaredMethod(
					"getWebElement", String.class);
		} catch (NoSuchMethodException e) {
			throw new Error("getWebElement() method is not found in "
					+ pageObjectClass.getClass().toString());
		}
		getWebElementMethod.setAccessible(true);
		try {
			element = (MobileElement) getWebElementMethod.invoke(pageObjectClass,
					fieldName);
		} catch (Exception e) {
			throw new Error("Error getting page obejct fields : "
					+ e.getMessage());
		}
		return element;
	}

	public  static void verifyElement_Is_Not_Displayed(String sStep,By sObj,String sMsg){			
		IOSElement ele =  findElementBy(sObj);
		if(ele ==null || !ele.isDisplayed()){
		
			Reporter.logEvent(Status.PASS,sStep,sMsg+" is not  Displayed",false);
		}else{
		     Reporter.logEvent(Status.FAIL,sStep , sMsg +" is  Display ",true);
		}
		
	}
	
	 
 
	/**
	 * Method will verify error message 
	 * @param sObj
	 * @param sExpText
	 * @param bPartial  true will display exact content and false will verify contain 
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
				  Reporter.logEvent(Status.PASS, "Expected Error Message displayed : ",sExpText, false);
			  else
				  Reporter.logEvent(Status.FAIL, "Expected Error Message to be  :"+sExpText  ,"But Actual was :  "+sActText, true);
			}
	    	
	      }
	      
	      /**
	       * Method will verify error message 
	       * @param sObj
	       * @param sExpText
	       *  @param bPartial  true will display exact content and false will verify contain 
	       */
	      
	      
	      public  static void verifyErrorMessage(MobileElement sObj,String sExpText,Boolean bPartial){
	   	    	  
	   	    	  if(sObj != null){
	   	    	 String sActText = sObj.getAttribute("value");
	   	    	 if(bPartial)
	   	    	 {
	   	    		 if(sActText.contains(sExpText))
	   	    			 Reporter.logEvent(Status.PASS, "Expected Error Message displayed",sExpText, false);
	   				  else
	   					  Reporter.logEvent(Status.FAIL, "Expected Error Message to be  :  "+sExpText  ,"But Actual was :  "+sActText, true);
	   	    			 
	   	    	 }
	   	    	 else {
	   	    	 if(sActText.equals(sExpText))
	   				  Reporter.logEvent(Status.PASS, "Expected Error Message displayed : ",sExpText, false);
	   			  else
	   				  Reporter.logEvent(Status.FAIL, "Expected Error Message to be  :"+sExpText  ,"But Actual was :  "+sActText, true);
	   			}
	   	     	  }
	   	    	  else{
	   	    		  Reporter.logEvent(Status.FAIL, "Error message not displayed","", true); 
	   	    	  }
	   	      }
	           
	 
		    /**
		     * Method will verify Warning Message
		     * @param sObj
		     * @param sExpText
		     * @param exactMatch
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
		          
	        
	/**
	 * Verify if element is enable 
	 * @param locator
	 * @param sMsg
	 */
	
	
	public static void  verifyElementEnable(String locator,String sMsg){		
		IOSElement ele = findElement(locator);
		if (ele != null) {
		  if(ele.isEnabled())
			  Reporter.logEvent(Status.PASS, sMsg + " should be enabled.",sMsg +" is enabled", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg+ " should be enabled.",sMsg +" is not enabled", true);
		}  		
  	}
	
	/**
	 * Verify if element is enable 
	 * @param ele
	 * @param sMsg
	 */
	public static void  verifyElementEnable(MobileElement ele,String sMsg){		
	if (ele != null) {
		  if(ele.isEnabled())
			  Reporter.logEvent(Status.PASS, sMsg +" should be enable",sMsg+ " is enable", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg +" should be enable",sMsg+ " is not enable", true);
		}  		
  	}
	
	/**
	 * Verify if element is disable
	 * @param locator
	 * @param sMsg
	 */
	
	public static void  verifyElementISDisable(String locator,String sMsg){	
		IOSElement ele = findElement(locator);
		if (ele != null) {
		  if(!ele.isEnabled())
			  Reporter.logEvent(Status.PASS, sMsg +" should not be enabled.",sMsg+ " is disable", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg +" should not be enabled.",sMsg+ " is  not disable", true);
		}
		  		
  	}
	
	/**
	 * Verify if element is disable
	 * @param locator
	 * @param sMsg
	 */
	
	public static void  verifyElementISDisable(MobileElement ele,String sMsg){	
		
		if (ele != null) {
		  if(!ele.isEnabled())
			  Reporter.logEvent(Status.PASS, sMsg,ele.getText()+ " is Disable", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg,ele.getText()+ "  is Not Disable", true);
		}
			  		
  	}
	
	
	/**
	 *  Method will verify if element is present	
	 * @param locator
	 * @return
	 */

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
	
	/**
	 * Method will verify if element is present	
	 * @param locator
	 * @param timeInSecond
	 * @return
	 */
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
	
	
	/**
	 * Method will find element by By locator
	 * @param link
	 * @return
	 */
	public static IOSElement findElementBy(By link){
		try{
			getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		return (IOSElement) getDriver().findElement(link);
		}catch(NoSuchElementException e){
		return null;
		}
	}
	
	/**
	 * Verify if element is present by ID
	 * @param link
	 * @return
	 */
	
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
	
	/**
	 *Verify if element is Present for page Factor element 
	 * @param ele
	 * @return
	 */
	
	public static boolean assertElementPresentByPageFactor(MobileElement ele){
	//	Boolean flag = false;
		 try{
			 if(ele.isDisplayed())
				 return true;
			 else
				 return false;
		 }catch(NoSuchElementException ex){
			 return false;
		 }
		 
		
	}
	
	/**
	 * Verify if element is present by link 
	 * @param link
	 * @return
	 */
	
	public static boolean assertElementPresent(By link) {
		Boolean flag = false;
		try{
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		IOSElement ele = (IOSElement) getDriver().findElement(link);
		
		if (ele != null) {
			int j =0;
			while (!ele.isDisplayed() && j<3) {	 
          		//scrollTo(ele);	
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
				System.out.println("Not displayed after scrolled in assertElement : "+link );
				flag = false;
			}
		}
		
	}  catch(Exception e){
		System.out.println("Not Display in AssertElement method:" +link);
	}
		return flag;
	}
	
	
	
	
	/**
	 * Verify if element is present 
	 * @param ele in MobileElement
	 * @return
	 */
	
	public static boolean assertElementPresent(MobileElement ele) {
		Boolean flag = false;
		try{		
		if (ele != null) {
			int j =0;
			while (!ele.isDisplayed() && j<3) {	 
          		//scrollTo(ele);	
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
				System.out.println("Not displayed after scrolled in assertElement : ");
				flag = false;
			}
		}
		
	}  catch(Exception e){
		System.out.println("Not Display in AssertElement method:" );
	}
		return flag;
	}
	
	
	/**
	 * Verify if element is present 
	 * @param elements
	 * @return
	 */
	
	
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
	
		

	/**
	 * Wait for page to load	
	 * @param locator
	 */
	

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
	

	/**
	 * Method will find element 
	 * if element is not displayed then help to scroll down/Up till element is visible i the screen. 
	 * @param locator as String
	 * @return IOSelement
	 */
	public static IOSElement findElement(String locator) {
		
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		IOSElement element = null;
		String LocatorType = "AccessibilityID";
		
		if(locator.contains("//")){
			LocatorType = "Xpath";
		}
		
		for (int i = 0; i < 1; i++) {
			try {
				
				if(LocatorType.equals("AccessibilityID")){
					element = (IOSElement) getDriver().findElementByAccessibilityId(
							locator);
				}
				else if(LocatorType.equals("Xpath")){
					element = (IOSElement) getDriver()
							.findElementByXPath(locator);
				}			


			} catch (Exception e) {
				System.out.println(locator + "  Not found at FindElement function");

			}

			if (element != null) {
				int j =0;
 				while (!element.isDisplayed() && j<3) {	
              		Mobile.scroll_Down();
// 				//	scroll_ToVisible(element);
// 					if(!element.isDisplayed()){
// 						scroll_Down();
// 					}         
              		if(j == 2 ){
              			Mobile.scroll_UP();
              			
              		}
                   j++;
				}
//				getDriver().manage().timeouts()
//						.implicitlyWait(10, TimeUnit.SECONDS);
				return element;
			}
		}		
		return element;
	}
	/**
	 * Method will hide keyBoard in screen
	 */

	public static void hideKeyboard() {
		getDriver().getKeyboard().pressKey("\n");	
			wait(1000);
	
	}

	/**
	 * <pre>
	 * Method to return iOS web element
	 * </pre>
	 * 
	 * @param ele  element for which IOSElemet is return
	 * @return  IOS element
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
	
	/**
	 *  Method will Tap on element 
	 * @param iXaxis  in int
	 * @param iYaxix  in int for Y axis
	 * @param element  tap on element
	 */

	public static void onTap(int iXaxis, int iYaxix,MobileElement element) {
		try {
			System.out.println("test");
			// Java
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			Map<String, Object> params = new HashMap<>();
			params.put("element", ((RemoteWebElement) element).getId());
			js.executeScript("mobile: twoFingerTap", params);
	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to TAp");
		}
	}
	/**
	 *  Method will scroll screen to find element 
	 * @param scrollToElement
	 */

	public static void scrollTo(IOSElement scrollToElement) {  
		try{			
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			HashMap<String, String> scrollObject = new HashMap<String, String>();		
			scrollObject.put("element",  ((RemoteWebElement) scrollToElement).getId());
			scrollObject.put("toVisible", "true");
			js.executeScript("mobile: scroll", scrollObject);
					
		}catch(Exception e){
			System.out.println("Not able to swipe");
		}
    
	}


	

	/**
	 * Method will scroll up to find element 
	 */
	public static void scroll_UP(){
		
	JavascriptExecutor js = (JavascriptExecutor) getDriver();
	HashMap<String, String> scrollObject = new HashMap<String, String>();
	scrollObject.put("direction", "up");
	js.executeScript("mobile: scroll", scrollObject);
}

/**
 *  Method will scroll down to find element 
 */
	public static void scroll_Down(){
	
	JavascriptExecutor js = (JavascriptExecutor) getDriver();
	HashMap<String, String> scrollObject = new HashMap<String, String>();
	scrollObject.put("direction", "down");
	js.executeScript("mobile: scroll", scrollObject);
}
	
	/**
	 *  Method will scroll down to find element 
	 */
		public static void scroll_ToVisible(MobileElement ele){
		
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		
		scrollObject.put("element",ele.getId());
		scrollObject.put("toVisible", "true");
	//	scrollObject.put("name", "accessibilityId");		
		js.executeScript("mobile: scroll", scrollObject);
	}
	
	/**
	 *  Method will scroll Right to find element 
	 */
	
	@SuppressWarnings("deprecation")
	public static void swipeRight() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.9);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		//getDriver().swipe(startx, starty, endx, starty, 2000);
	}


	/**
	 *  Method will scroll left to find element 
	 */

	@SuppressWarnings("deprecation")
	public static void swipeLeft() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		//getDriver().swipe(startx, starty, endx, starty, 1000);
	}	


	

	/**
	 * Method will select radio button 
	 * @param sLabel select option by clicking this value
	 */
	
	public static void selectRadioButton(String sLabel) {
	
		try{	
			IOSElement eleRadioOption =	findElementWithPredicate(sLabel);
		if(eleRadioOption != null){
			eleRadioOption.click();
			Reporter.logEvent(Status.INFO,"Selected Radio option for Label",sLabel,false);	
			
		}
		}
		catch(Exception e){
			System.out.println("Not able to select Radio Button");
		}			
	}
	
/**
 * Method will find element with Predicate
 * @param sLabel element for which IOSElemet is return
 * @return  IOS Element
 */
	
	public static IOSElement findElementWithPredicate(String sLabel){
		
		IOSElement ele  = null;
		try{
		 ele = (IOSElement) Mobile.getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS '"+sLabel+"'"));
		//IOSElement ele  =  (IOSElement) Mobile.getDriver().findElement(By.xpath(""));
		if(ele != null){
			if(!ele.isDisplayed()){
				Mobile.scroll_Down();
			}
		}}
		catch(NoSuchElementException e){
			
		}
		return ele;
		
	}
	
	/**
	 * Method will set the flag for Switch Button
	 * @param bValue true will set switch ON and false will switch OFF 
	 */
	public static void switchButton( Boolean bValue) {
		try{
			String sFlag = "0";
			if(bValue){
				sFlag = "1";
			}
		
		IOSElement element =	(IOSElement) Mobile.getDriver().findElement(By.className("XCUIElementTypeSwitch"));	
       if(element != null){
		
		String isSelected = element.getAttribute("value");;
		if (!sFlag.equals(isSelected)) {	
				element.click();		}
       }else{
    	   System.out.println("Not able to Switch Button ");
		}
       wait(1000);
		}catch (NoSuchElementException ex){
			 System.out.println("An element could not be located on the page");	
		}

	}
	
	/**
	 * Method will set the flag for Switch Button
	 * @param bValue true will set switch ON and false will switch OFF 
	 */
	public static void switchButton( MobileElement element ,Boolean bValue) {
		try{
			
			String sValue = "0";
					if(bValue){
						sValue = "1";
					}
				
       if(element != null){
		
		String isSelected = element.getAttribute("value");
		if (!isSelected.equals(sValue)) {	
				element.click();		}
       }else{
    	   System.out.println("Not able to Switch Button");
		}
       wait(1000);
		}catch (NoSuchElementException ex){
			 System.out.println("An element could not be located on the page");	
		}

	}
	
	/**
	 * Find List of element with class
	 * @param element element for which IOSElement is return
	 * @return  list of IOSElement 
	 */
	
	@SuppressWarnings("unchecked")
	public static List<IOSElement> getListOfElements_By_Class(String element){
	
     return (List<IOSElement>) Mobile.getDriver().findElementsByClassName(element);
		
	}
	
	/**
	 * Find element with by class name
	 * @param ele element for which IOSElemet is return
	 * @return IosElement
	 */
	public static IOSElement findElement_By_Class(String ele){
		return Mobile.getIOSElement(By.className(ele));
	}
	
	/**
	 * Method will verify element is  selected
	 * @param sObj 
	 * @param sMsg
	 */
	public static void verify_Element_Is_Selected(String sObj,String sMsg){
		String isSelected = getElementValue(sObj);
		if (Boolean.valueOf(isSelected) == true) 	
			Reporter.logEvent(Status.PASS, "Selected ",sMsg, false);
		  else
			  Reporter.logEvent(Status.FAIL, "Not Selected",sMsg, true);
		
	}
	
	/**
	 * Method will verify if element is not selected
	 * @param sObj
	 * @param sMsg
	 */
	public static void verify_Element_Is_Not_Selected(String sObj,String sMsg){
		String isSelected = getElementValue(sObj);
		if (Boolean.valueOf(isSelected) == false) 	
			Reporter.logEvent(Status.PASS, sMsg,"Element is not  selected", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg,"Element is Selected", true);
		
	}
	
	/**
	 * Method will verify element is not selected
	 * @param sObj
	 * @param sMsg
	 */
	
	public static void verify_Element_Is_Not_Selected(MobileElement sObj,String sMsg){
		String isSelected = "";
		if(sObj != null){
			isSelected =sObj.getAttribute("value");
		}
		if (Boolean.valueOf(isSelected) == false) 	
			Reporter.logEvent(Status.PASS, sMsg,"Element is not  selected", false);
		  else
			  Reporter.logEvent(Status.FAIL, sMsg,"Element is Selected", true);
		
	}
	
	
	/**
	 *  This function will select date from date Picker 
	 * @param sDate
	 * @param sMonth
	 * @param sYear
	 */
	@SuppressWarnings("unchecked")
	public static void setDateValue(String sDate,String sMonth,String sYear){	
		List<IOSElement> wheels =  (List<IOSElement>) Mobile.getDriver().findElements(By.className("XCUIElementTypePickerWheel"));
		  wheels.get(0).setValue(sDate);
	      wheels.get(1).setValue(sMonth);
	      wheels.get(2).setValue(sYear);
	  	  Mobile.clickElement("Next");
	
	}
	
	
	

	/**
	 * This function will select value from Picker in increment order
	 * 
	 * @param sValue
	 * @param sOrder: Either next to select the value next to the current one from the 
	 *                        target picker wheel or previous to select the previous one. Mandatory parameter
	 */
public static void selectPickerValue(String sValue,String sOrder){
	
	try{
		try{
		selectPicker(sValue, sOrder);
	}catch(WebDriverException e){
		if(sOrder.equals("next")){
		selectPicker(sValue, "previous");
		}
		else{
		selectPicker(sValue, "next");
		}
	}
	}catch(Exception ex){
	 	
		  Reporter.logEvent(Status.FAIL, "Select Picker Value was not found",sValue, true);
		  Mobile.clickElement("Done");
		
	}
	
	}


private static void selectPicker(String sValue,String sOrder){
	

		@SuppressWarnings("unchecked")
		List<IOSElement> wheels =  (List<IOSElement>) Mobile.getDriver().findElements(By.className("XCUIElementTypePickerWheel"));
		String  tempActValue = ""; 
		
		if(wheels.get(0) != null){			
		    for(int i =0;i<8;i++){
		    	   String  sActValue = wheels.get(0).getText();	
		    	if(tempActValue.equals(sActValue)){
		    		break;
		    	}else{
		    		tempActValue=sActValue;
		    	}    	
		   		      
		       System.out.println(sActValue);
		    	if(sActValue.startsWith(sValue)){
		    		break;
		    	}	    	
		    	
		    	else{
		    	 JavascriptExecutor js = (JavascriptExecutor) Mobile.getDriver();
		   	      Map<String, Object> params = new HashMap<>();
		   	      params.put("order", sOrder);
		   	      params.put("offset", 0.15);
		   	      params.put("element", ((RemoteWebElement) wheels.get(0)).getId());
		   	      js.executeScript("mobile: selectPickerWheelValue", params);
		    		
		    	}	    	
		    }	 
	      
	  	  Mobile.clickElement("Done");
		}
	
	
	
	
	}
	
/**
 * Method which will print Figlet fort in Console
 * @param text
 */
	
	 public static void figlet(String text) {
	        String asciiArt1 = null;
	        try {
	            asciiArt1 = FigletFont.convertOneLine(text);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println(asciiArt1);
	    }
	 
	 /**
	  * Close Mobile session driver and quite
	  */
	 public static void cleanupSessions() {			 
			System.out.println("After Suite in User base");
			 System.out.println( "**************Closing Session****************");
		 if(Mobile.getDriver() != null && i == 0){				
				 System.out.println( "**************Closing Mobile App ****************");
			    	 Mobile.getDriver().closeApp();
			    	 Mobile.getDriver().quit();					
					i++;
					if (Reader.getConfigParam("APPIUMSEVER").equalsIgnoreCase("PROGRAM")) 
				     new AppiumManager().destroyAppiumNode();
					
					Mobile.figlet("Test Complete");
				
			 }
		 else {
			 System.out.println( "**************Killing Mobile Instance ****************");
				//	Web.getDriver().close();
			 Mobile.getDriver().quit();		
				Mobile.figlet("Test Complete");
				
//				}
		 }
	
	 }
	 
	 /**
		 *  Method will get element value using By locator
		 * @param locator
		 * @return
		 * @Author : Siddartha 
		 * @date : March 2018
		 */
			
		public static String getElementName(MobileElement locator) {
			String sValue = "";
			//IOSElement ele = find(locator);

			if (locator != null) {
				return locator.getAttribute("name");
			
			}
			return sValue;

		}
		
		/**
		 * Method to verify element value
		 * @param Obj as MobileElement
		 * @param sExpText
		 * @param sMsg
		 *   @Author : Siddartha 
		 * @date : March 2018
		 */

		public  static void verify_Element_Label(MobileElement Obj,String sExpText,String sMsg){	
			String sActValue ="";
			if(Obj != null)	
		 sActValue = getElementLabel(Obj);
		
		if(sActValue != null){
		if(sActValue.replaceAll("\\n","").trim().contains(sExpText.trim())){
			Reporter.logEvent(Status.PASS,sMsg, "Actual Value :  "+sActValue,false);
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : "+sExpText +"\n But Actucal was:  "+sActValue,true);
		}
		}else{
		     Reporter.logEvent(Status.FAIL,sMsg," Expected Value : "+sExpText +"\nBut Actual it was not present",true);

		}
		
	}
		/**
		 *  Method will get element value using MobileElement
		 * @param locator
		 * @return
		 */
		public static String getElementLabel(MobileElement ele) {
			String sValue = "";
			

			if (ele != null) {
				sValue= ele.getAttribute("label");
			
			}
			return sValue;

		}

}
