package framework.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import lib.Reporter;
import lib.Stock;
import lib.Web;

public class CommonLib {

	public static long startTime;
	public static long finishTime;
	public static long totalTime;
	public static double totalTimeTaken_Sec;
	public static String prevPpt = null;
	
	static String currentPpt = null;

	/**
	 * Method to calculate Total time taken to launch application
	 * 
	 * @param startTime
	 * @return double
	 */
	public double total_Time_Taken_To_Launch_Page(long startTime) {
		finishTime = System.currentTimeMillis();
		totalTime = finishTime - startTime;
		totalTimeTaken_Sec = totalTime / 1000;

		return totalTimeTaken_Sec;
	}

	/**
	 * <pre>
	 * Compare data base date and web date
	 * </pre>
	 * 
	 * @param
	 * @return :boolean <br>
	 *         is date equal</br>
	 * @author : Ranjan
	 * @throws ParseException
	 */
	public static boolean compareDB_Date_With_Web_Date(String dbDate,
			String webDate) throws ParseException {
		Date date1 = new Date();
		Date date2 = new Date();
		boolean isSameDate = false;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.ENGLISH);
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		date1 = format1.parse(dbDate);
		date2 = format2.parse(webDate);

		if (date1.compareTo(date2) == 0) {
			isSameDate = true;
		} else if (date1.compareTo(date2) > 0) {
			isSameDate = false;
		} else if (date1.compareTo(date2) < 0) {
			isSameDate = false;
		}
		return isSameDate;
	}

	/**
	 * <pre>
	 * Validate string contains money type value with $ currency symbol or not.
	 * </pre>
	 * 
	 * @return boolean:will return <b> true </b> if account balance 'll have $ sign along with numbers else 'll return <b>false</b>
	 */
	public static boolean isAccountBalance_In_ProperFormat(String accountBal) {
		boolean isAccBal = false;
		Number number = null;
		if (accountBal.equalsIgnoreCase("N/A")) {
			return true ;
		}
		try {
			number = NumberFormat.getCurrencyInstance(Locale.US).parse(
					accountBal);
			if (number != null) {
				isAccBal = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isAccBal;
	}
	
	public static boolean checkForPpt()
	{
				
		currentPpt=Stock.GetParameterValue("PPT_ID");
	 	return currentPpt.equalsIgnoreCase(prevPpt);
		
		
	}
	
	/***
	 * Method to check expected webelement present on a page or not.
	 * @param webelment
	 * @param sMsg
	 * @param bFlag
	 */
	public static void verifyIfWebElementPresent(WebElement ele, String sMsg,boolean bFlag){
		// Check if webelement displayed on a page or not.
		Boolean bStatus = Web.isWebElementDisplayed(ele);
		System.out.println(bStatus);
		if(bStatus == bFlag){
			Reporter.logEvent(Status.PASS,	sMsg,"Expected web element present "+ele, false);
		}else{
			Reporter.logEvent(Status.FAIL,	sMsg,"Expected was :"+ele+" \\n Actual was :"+ele, true);
		}
		}
	
	/**
	 * Method to check webelement text is as expected or not.
	 * @param Webelement
	 * @param expectedText
	 */
	public static void verifyIfWebElementTextPresent(WebElement ele, String expectedText,String sMsg){
		// Check if webelement contains expected text or not
		String actual = ele.getText();
		if(actual.equalsIgnoreCase(expectedText)){
			Reporter.logEvent(Status.PASS,"Expected webelement text is :" +expectedText+"Actual webelement text is "+actual ,expectedText,true);
		}else{
			Reporter.logEvent(Status.FAIL,"Expected webelement text is :" +expectedText+"Actual webelement text is "+actual,expectedText, true);
		}
		}
	
	/**
	 * Method to check webelement text is as expected or not.
	 * @param Webelement
	 * @param expectedText
	 */
	public static void verifyFieldValue(WebElement ele, String expectedFieldValue,String sMsg){
		String actualFieldValue =ele.getAttribute("value");
		// Check if webelement contains expected text or not
		if(actualFieldValue.equalsIgnoreCase(expectedFieldValue)){
			Reporter.logEvent(Status.PASS,sMsg ,expectedFieldValue,false);
		}else{
			Reporter.logEvent(Status.FAIL,sMsg ,"Expected was :"+expectedFieldValue + " \\n Actual was :"+ actualFieldValue,true);
		}
		}
	
	/**
	 * Method to check button enabled or not.
	 * @param Webelement
	 * @param boolean
	 */
	public static void verifyIsButtonEnabledOrNot(WebElement ele, boolean b){
		
		boolean isEnabled =ele.isEnabled();
		
		// Check if button enabled or not.
		if(isEnabled==b){
			Reporter.logEvent(Status.PASS,"Check if expected button enabled or not:"+ele+"" ,"Expectecd button enabled.",false);
		}else{
			Reporter.logEvent(Status.FAIL,"Check if expected button enabled or not:"+ele+"" ,"Expectecd button does not enabled.",true);
		}
		}
	/**
	 * Method to check button enabled or not.
	 * @param Webelement
	 * @param boolean
	 *//*
	public static void verifyExpectedAndActualEual(String expected, String actual){
		
		// Check if button enabled or not.
		if(expected.equalsIgnoreCase(actual)){
			Reporter.logEvent(Status.PASS,"Expected value should  match with actual value: " ,expected,false);
		}else{
			Reporter.logEvent(Status.FAIL,"Expected value should  match with actual value " ,"Expected was :"+expected + " \\n Actual was :"+ actual,true);
		}
		}*/
	/**
	 * Method to check button enabled or not.
	 * @param Webelement
	 * @param boolean
	 */
	public static void verifyExpectedAndActualEual(String expected, String actual,String sMsg){
		
		// Check if button enabled or not.
		if(expected.equalsIgnoreCase(actual)){
			Reporter.logEvent(Status.PASS,sMsg ,expected,false);
		}else{
			Reporter.logEvent(Status.FAIL,sMsg ,"Expected was :"+expected + " \\n Actual was :"+ actual,true);
		}
		}
	/**
	 * Method to get random number
	 * @param length
	 * @return
	 */
	public static long getRandomNumber(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}

	
	}
