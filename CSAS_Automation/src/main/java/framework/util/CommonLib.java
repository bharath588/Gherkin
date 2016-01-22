<<<<<<< Upstream, based on origin/master
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
=======
package framework.util;

import lib.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CommonLib {
	  	
	  public static final String MailExistingPINMsg = "Combined verification and pin letter generated: doc_id = "
	  		              + "  If a PIN confirmation letter doc id is displayed then a reminder PIN letter will be"
	  		              + " sent to the participant.  If \"No document to generate.\" displays then a"
	  		              + " reminder PIN letter will not be sent until PIN has been system generated."
	  		              + "  CSR should not attempt to issue through ISIS.";	
	  public static final String OrderTempPINMsgA= "Note: this VRU PIN (or web passcode) is a single use PIN (passcode) "
	  											 + "that must be re-set after first use. Please direct participant accordingly.";
	  public static final String OrderTempPINMsgB= "The temporary PIN number is";

	  public static void mouseHover(WebElement webElement){
          Actions actions;
          actions = new Actions(Web.webdriver);
          actions.moveToElement(webElement);
          actions.build().perform();
	  }
	  	 
}
>>>>>>> 0619d8f Commiting CommonLib initial
