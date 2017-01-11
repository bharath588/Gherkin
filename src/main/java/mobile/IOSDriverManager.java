package mobile;
/* @Author Siddartha 
 * @Date 30/Dec/2016
 * 
 */

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import lib.Log;
import lib.Log.Level;

import org.openqa.selenium.remote.DesiredCapabilities;

import core.framework.Globals;

//If server does not start Run 
// ps -ax|grep -i "iproxy"|grep -v grep|awk '{print "kill -9 " $1}'|sh  in terminal 

public class IOSDriverManager {
	public static AppiumDriver<MobileElement> webdriver = null;
	private static DesiredCapabilities capabilities = new DesiredCapabilities();
	public static String deviceName = null;	
	  public static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
	  public static AppiumManager appiumMan = new AppiumManager();
	    protected static int deviceCount = 0;
	
	  public  AppiumDriver<MobileElement> runner() throws Exception {
	        Mobile.figlet("Mobile App");
	        new ParallelThread().parallelExecution();
	      return  setIOSNativeCapabilities();
	    }
	  
	

	public static AppiumDriver<MobileElement> setIOSNativeCapabilities()
			 {
		AppiumDriver<MobileElement> driver = null;
		Reader.setMobileParam(Globals.GC_TESTCONFIGLOC
				+ "MobileConfig.properties");
		capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION,
				Reader.getConfigParam("APPIUM_VERSION"));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
				AutomationName.IOS_XCUI_TEST);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				5000);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		Mobile.figlet("Mobile App");

		String sURL = "http://" + Reader.getConfigParam("APPIUM_SERVER") + ":"
				+ Reader.getConfigParam("APPIUM_PORT") + "/wd/hub";
		try {

			if (Reader.getConfigParam("MOBILE_PLATFORM")
					.equalsIgnoreCase("iOS")) {

				 ArrayList<String> list =	  IOSDeviceConfiguration.getIOSUDID();
				 
				 if(list == null){
					 Mobile.figlet("No Devices Connected");
			            System.exit(0);
				 }
				 for (String string : list) {
					 deviceName = string;
					 
				 }
				 deviceCount = list.size();
				
			     	System.out.println("***************************************************\n");
			        System.out.println("Total Number of devices detected::" + deviceCount + "\n");
			        System.out.println("***************************************************\n");
			        System.out.println("starting running tests ");

				   System.out.println("****************Device*************" + deviceName);
				   
				 try {
				//	IOSDeviceConfiguration.closeAllIProxy();
					appiumMan.appiumServerForIOS(deviceName,iosDevice.startIOSWebKit(deviceName));
				} catch (Exception e) {					
					System.out.println("Not able to Initialize Appium deriver");
					e.printStackTrace();
					return driver;
				}
				 
				driver = new IOSDriver<>(appiumMan.getAppiumUrl(), iosNative());

			} else if (Reader.getConfigParam("MOBILE_PLATFORM")
					.equalsIgnoreCase("andriod")) {

				driver = new AndroidDriver<>(new URL(sURL), androidNative());

			} else {
				System.out
						.println("Appium Platorm is not selectd in properties file correctly , Should be ios or andriod");
				Log.Report(Level.INFO,
						"Appium Platorm is not selectd in properties file correctly");
			}
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		} catch (MalformedURLException e) {
			System.out.println("Not able to Initialize Appium deriver");
		}

		return driver;
	}	
	
	public static String  getDeviceName()
	{
		return deviceName;
	}

	public static DesiredCapabilities iosNative() {
		System.out.println("Setting iOS Desired Capabilities:");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, " iOS");
		capabilities.setCapability(IOSMobileCapabilityType.INTER_KEY_DELAY,
				Reader.getConfigParam("INTER_KEY_DELAY"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
				"10.0");
		if (Reader.getConfigParam("DEVICE").equalsIgnoreCase("RealDevice")) {
			capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT,
					true);
			
			capabilities.setCapability(MobileCapabilityType.UDID,deviceName);
		

			if (Reader.getConfigParam("APP_TYPE").equalsIgnoreCase("NATIVE")) {

				capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,
						Reader.getConfigParam("NATIVE_BUNDLE_ID"));
				capabilities.setCapability(MobileCapabilityType.APP,
						Reader.getConfigParam("IOS_APP_PATH_FOR_REALDEVICE"));

			} else {
				capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW,
						true);
				capabilities.setCapability(
						MobileCapabilityType.SUPPORTS_JAVASCRIPT, true);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,
						Reader.getConfigParam("MOBILE_BROWSER"));
				capabilities.setCapability(MobileCapabilityType.APP,
						Reader.getConfigParam("SAFARI_APP_PATH"));
				capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,
						"com.byterac.SafariLauncher");
			}

		} else if (Reader.getConfigParam("DEVICE")
				.equalsIgnoreCase("simulator")) {
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,
					Reader.getConfigParam("BUNDLE_ID"));
			capabilities.setCapability(MobileCapabilityType.APP,
					Reader.getConfigParam("IOS_APP_PATH_FOR_Simulator"));
		}

		capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS,
				true);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
				"iPhone 6s");

		return capabilities;
	}

	public static DesiredCapabilities androidNative() {
		System.out.println("Setting Android Desired Capabilities:");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
				" andriod");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
		capabilities.setCapability(MobileCapabilityType.UDID,
				Reader.getConfigParam("UDID"));
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
				Reader.getConfigParam("APP_ACTIVITY"));
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
				Reader.getConfigParam("APP_PACKAGE"));
		capabilities.setCapability(MobileCapabilityType.APP,
				Reader.getConfigParam("ANDROID_APP_PATH"));

		return capabilities;
	}

}
