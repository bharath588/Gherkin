package framework.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonLib {
	public final String MailExistingPINMsg = "Combined verification and pin letter generated: doc_id = "
			+ " If a PIN confirmation letter doc id is displayed then a reminder PIN letter will be"
			+ " sent to the participant.  If \"No document to generate.\" displays then a"
			+ " reminder PIN letter will not be sent until PIN has been system generated."
			+ " CSR should not attempt to issue through ISIS.";
	public final String OrderTempNoteMsg = "Note: this VRU PIN (or web passcode) is a single use PIN (passcode) "
			+ "that must be re-set after first use. Please direct participant accordingly.";
	
	public final String OrderTempPINMsgB = "The temporary PIN number is" ;

	public final String MailExistWebPsCd = "An order to mail the participant's existing web passcode "
			+ "has been issued, confirmation #.";
	public final String MailExistVRUpin = "An order to mail the participant's existing VRU PIN "
			+ "has been issued, confirmation #.";
	public final String OrderTempNote = "Note: this VRU PIN (or web passcode) is a single use PIN (passcode) that must "
			+ "be re-set after first use. "
			+ "Please direct participant accordingly.";
	public final String OrderTempWebPsCd = "The temporary web passcode number is";

	public final String OrderTempVRUPIN = "The temporary VRU PIN number is";

	public Object getVarByName(String fieldName) throws Throwable {
		Field field = this.getClass().getDeclaredField(fieldName);
		return field.get(this);
	}

	public static long startTime;
	public static long finishTime;
	public static long totalTime;
	public static double totalTimeTaken_Sec;

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
	 * <pre>Compare data base date and web date</pre>
	 * @param
	 * @return	:boolean <br> is date equal</br>
	 * @author	: Ranjan
	 */
	public static boolean compareDB_Date_With_Web_Date(String dbDate,
			String webDate) {

		boolean isDateEqual_Flag = false;
		Date date;
		try {
			date = new Date();
			dbDate = dbDate.substring(0, dbDate.indexOf(" "));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			date = df.parse(dbDate);
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");
			String strDate = sfd.format(date);
			if (strDate.equalsIgnoreCase(webDate))
				isDateEqual_Flag = true;
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return isDateEqual_Flag;
	}

}