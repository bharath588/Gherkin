package mobile;



import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;



/**
 *  Appium Manager - this class contains method to start and stops appium server
 *  To execute the tests from eclipse, you need to set PATH as
 *  /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 * 
 * @author sddprd
 * @since30/12/2016
 *
 */

public class AppiumManager {
	
	 private CommandPrompt cp = new CommandPrompt();
	    private AvailablePorts ap = new AvailablePorts();
	    public static AppiumDriverLocalService appiumDriverLocalService;
	    public Properties prop = new Properties();
	    public InputStream input = null;
	    public AppiumServiceBuilder builder = new AppiumServiceBuilder();
	    public static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();

	public static void main(String a){
		
	}
	    
	    /**
	     * start appium with auto generated ports : appium port, chrome port,
	     * bootstrap port and device UDID
	     */
	
	    ServerArgument webKitProxy = new ServerArgument() {
	        @Override public String getArgument() {
	            return "--webkit-debug-proxy-port";
	        }
	    };
	    

	  
	    /**
	     * start appium with auto generated ports : appium port, chrome port,
	     * bootstrap port and device UDID
	     * @param deviceID
	     * @param webKitPort
	     * @return
	     * @throws Exception
	     */

	    public AppiumServiceBuilder appiumServerForIOS(String deviceID, String webKitPort) throws Exception {
	            System.out
	                .println("**********************************************************************\n");
	            System.out.println("Starting Appium Server to handle IOS::" + deviceID + "\n");
	            System.out
	                .println("**********************************************************************\n");
	            File classPathRoot = new File(System.getProperty("user.dir"));
	            createAppliumLogFolder();
	            int port = ap.getPort();
	            AppiumServiceBuilder builder =
	                new AppiumServiceBuilder().withAppiumJS(new File(Reader.getConfigParam("APPIUM_JS_PATH")))
	                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
	                    System.getProperty("user.dir") + "/TestReport/" + deviceID
	                        .replaceAll("\\W", "_") + "__AppiumLog.txt"))
	                    .withArgument(webKitProxy, webKitPort)
	                    .withIPAddress("127.0.0.1")
	                    .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
	                    .withArgument(GeneralServerFlag.TEMP_DIRECTORY,
	                        new File(String.valueOf(classPathRoot)).getAbsolutePath() + "/TestReport/appiumlogs/" + "tmp_"
	                            + port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
	            appiumDriverLocalService = builder.build();
	            appiumDriverLocalService.start();
	            return builder;

	        }

	    public URL getAppiumUrl() {
	        return appiumDriverLocalService.getUrl();
	    }
	    
	    /**
	     * createAppliumLogFolder  create folder for Appium server Log
	     */
	    public void createAppliumLogFolder(){
	    	 File f = new File(System.getProperty("user.dir") + "/TestReport/appiumlogs/");
	    	 if(f.exists()){
	    		 f.delete();
	    	 }
	         if (!f.exists()) {
	             System.out.println("creating directory: " + "Logs");
	             boolean result = false;
	             try {
	                 f.mkdir();
	                 result = true;
	             } catch (SecurityException se) {
	                 se.printStackTrace();
	             }
	         }
	         

	    }
	    /**
	     * kill all iProxy and Webkit proxy once appium server is stop in devices
	     */

	    public void destroyAppiumNode() {
	        try {
	        
	        	if (appiumDriverLocalService.isRunning())
	        appiumDriverLocalService.stop();
	        	
	        if (appiumDriverLocalService.isRunning()) {
	            System.out.println("AppiumServer didn't shut... Trying to quit again....");
	            appiumDriverLocalService.stop();
	        }
	        if (Mobile.getDriver().toString().split(":")[0].trim().equals("IOSDriver")) {
	     
					iosDevice.destroyIOSWebKitProxy();
				} 
	        	}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      
	       new  AppiumParallelTest().freeDevice(new IOSDriverManager().deviceName);
	    }
	   
	    
	    
	    

}
