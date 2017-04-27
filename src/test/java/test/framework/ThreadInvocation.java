package test.framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.testng.TestNG;

import core.framework.DriverScript;




public class ThreadInvocation {

	public static void main(String[] args) {
		try{
			/*//Class c= Class.forName("core.framework.TestListener");
			final TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			String xml=System.getProperty("user.dir")+"\\RunXML\\"+"Liat.xml";
           // suites.add(System.getProperty("user.dir")+"\\RunXML\\BVT.xml");
            suites.add(xml);
            testng.setTestSuites(suites);
			Runnable threadRunnable = new Runnable() {
				
				@Override
				public void run() {
				testng.run();
					
				}
			};

				//Runnable runnable = new DriverScript();
			    Thread[] threads = new Thread[3];
		
			for (int i = 0; i < threads.length; i++) {
			    threads[i] = new Thread(threadRunnable);
			    threads[i].start();
			    Thread.sleep(5000);
			 }
			*/
			Calendar cal = Calendar.getInstance();
			String date=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())+","+" "+
					cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "
					+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+","+" "+
					Integer.toString(cal.get(Calendar.YEAR))+" ,"+" "+
					Integer.toString(cal.get(Calendar.HOUR))+":"+Integer.toString(cal.get(Calendar.MINUTE))+" "+cal.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
			System.out.println(date);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
