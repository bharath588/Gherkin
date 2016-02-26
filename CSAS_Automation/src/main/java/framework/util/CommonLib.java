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
	 * @throws ParseException 
	 */
	public static boolean compareDB_Date_With_Web_Date(String dbDate,String webDate) throws ParseException{
		Date date1 = new Date() ;
		Date date2 = new Date() ;
		boolean isSameDate = false ;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		date1 = format1.parse(dbDate);
		date2 = format2.parse(webDate);
		
		if (date1.compareTo(date2) == 0) {
			isSameDate = true ;
		}else if (date1.compareTo(date2) > 0) {
			isSameDate = false ;
		} else if (date1.compareTo(date2) < 0) {
			isSameDate = false ;
		}
		return isSameDate;
	}
	
	
	
	

}
