package test.framework;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;




public class ThreadInvocation {

	public static void main(String[] args) {
		try{
			//Class c= Class.forName("core.framework.TestListener");
			final TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			String xml=System.getProperty("user.dir")+"\\RunXML\\"+"Beneficiary.xml";
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
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
