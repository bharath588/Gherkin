package framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CommonLib {

	
	
	/*
	 * Mouse hover on specific web element
	 * 
	 * @PARAMETER = WebElement
	 * 
	 * @Author:Ranjan
	 */
	public static void mouseHover(WebElement webElement){
		Actions actions;
		actions = new Actions(Web.webdriver);
		actions.moveToElement(webElement);
		actions.build().perform();
	}
	
}
