package mobile;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
/**
 * AppiumParallelTest  is sed for parallel execution in mobile devices
 * @author sddprd
 *
 */

public class AppiumParallelTest {
	public static ArrayList<String> devices = new ArrayList<String>();
    public static Properties prop = new Properties();
    public static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
    public static ConcurrentHashMap<String, Boolean> deviceMapping =  new ConcurrentHashMap<String, Boolean>();
    
    static {
        try {
                  if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (IOSDeviceConfiguration.deviceUDIDiOS != null) {
                    System.out.println("Adding iOS devices");
                    devices.addAll(IOSDeviceConfiguration.deviceUDIDiOS);
                }
              
            }
            for (String device : devices) {
                deviceMapping.put(device, true);
            }
            System.out.println(deviceMapping);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize framework");
        }
    }
     
    /**
     * return  available devices for execution
     * @return
     */
    
    public static synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, Boolean> devices = deviceMapping.keySet();
        int i = 0;
        for (String device : devices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            System.out.println("Parallel Thread is::" + t.getName());
            i++;
            if (deviceMapping.get(device)) {
                deviceMapping.put(device, false);
                return device;
            }
        }
        return null;
    }


    public static void freeDevice(String deviceId) {
        deviceMapping.put(deviceId, true);
    }


}
