package mobile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class picks the devices connected and distributes across multiple thread.
 * @author Siddartha
 * @since29-Dec -2016
 *
 */


public class ParallelThread {

	protected int deviceCount =0;
	 Map<String, String> devices = new HashMap<String, String>();
	 Map<String, String> iOSdevices = new HashMap<String, String>();
	 private IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
	 private MyTestExecutor myTestExecutor = new MyTestExecutor();
	   
	 
	
	 
	 public void parallelExecution() throws Exception {
		   String operSys = System.getProperty("os.name").toLowerCase();
         File f = new File(System.getProperty("user.dir") + "/target/appiumlogs/");
         
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

         if (operSys.contains("mac")) {
             if (iosDevice.getIOSUDID() != null) {
                 iosDevice.checkExecutePermissionForIOSDebugProxyLauncher();
                 iOSdevices = iosDevice.getIOSUDIDHash();
                 deviceCount += iOSdevices.size();
                 createSnapshotFolderiOS(deviceCount, "iPhone");
             }

         }
         
         if (deviceCount == 0) {
             System.out.println("No Devices Connected");
             System.exit(0);
         }
         
         System.out.println("***************************************************\n");
         System.out.println("Total Number of devices detected::" + deviceCount + "\n");
         System.out.println("***************************************************\n");
         System.out.println("starting running tests in threads");
         
         
         
//         
//
//         if (Reader.getConfigParam("RUNNER").equalsIgnoreCase("distribute")) {
//             myTestExecutor
//                 .runMethodParallelAppium( deviceCount,
//                     "distribute");
//
//         }
//         if (Reader.getConfigParam("RUNNER").equalsIgnoreCase("parallel")) {
//             myTestExecutor
//                 .runMethodParallelAppium( deviceCount,
//                     "parallel");
//         }
//         
         
	 }
	 
	 
	 
	 public void createSnapshotFolderiOS(int deviceCount, String platform) {
	        for (int i = 0; i < iOSdevices.size(); i++) {
	            String deviceSerial = iOSdevices.get("deviceID" + i);
	            createPlatformDirectory(platform);
	            File file = new File(
	                System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
	                    + deviceSerial);
	            if (!file.exists()) {
	                if (file.mkdir()) {
	                    System.out.println("IOS " + deviceSerial + " Directory is created!");
	                } else {
	                    System.out.println("Failed to create directory!");
	                }
	            }
	        }
	    }


	    public void createPlatformDirectory(String platform) {
	        File file2 = new File(System.getProperty("user.dir") + "/target/screenshot");
	        if (!file2.exists()) {
	            file2.mkdir();
	        }

	        File file3 = new File(System.getProperty("user.dir") + "/target/screenshot/" + platform);
	        if (!file3.exists()) {
	            file3.mkdir();
	        }
	    }

	 
}


     
