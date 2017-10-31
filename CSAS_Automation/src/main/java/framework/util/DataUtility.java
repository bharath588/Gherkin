package framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DataUtility {
	public  static String getTimeForTimeZone(String sFormat, int noDays)
	{
		
		DateFormat df = new SimpleDateFormat(sFormat);
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		cal.add(Calendar.DAY_OF_MONTH, noDays);
		System.out.println(df.format(cal.getTime()));
		return df.format(cal.getTime());
		
	}
	

}
