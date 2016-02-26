package framework.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonLib {
	


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
	public static boolean compareDB_Date_With_Web_Date(String dbDate,String webDate){

		boolean isDateEqual_Flag = false;
		Date date;
		try {
			date = new Date();
			dbDate = dbDate.substring(0, dbDate.indexOf(" "));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			date = df.parse(dbDate);
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");
			String strDate = sfd.format(date);
			System.out.println(dbDate+"\n  "+strDate+"  \n"+webDate);	
			
			if (strDate.equalsIgnoreCase(webDate))
				isDateEqual_Flag = true;
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return isDateEqual_Flag;
	}

}
