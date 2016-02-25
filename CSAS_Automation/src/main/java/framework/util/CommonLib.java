package framework.util;

import java.lang.reflect.Field;

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

}