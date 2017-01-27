package mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;



public class IOSDeviceConfiguration {

  public static ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
  public HashMap<String, String> deviceMap = new HashMap<String, String>();
  public static CommandPrompt commandPrompt = new CommandPrompt();
	  Map<String, String> devices = new HashMap<>();
	  public final static int IOS_UDID_LENGTH = 40;	
	  public static ConcurrentHashMap<Long, Integer> appiumServerProcess = new ConcurrentHashMap<>();
	    AvailablePorts ap = new AvailablePorts();
	    public InputStream input = null;
	    public Properties prop = new Properties();
	    public Process p1;
	  
	
	    
	  
	  public static void main(String a[]) throws Exception{
		//System.out.println( getDeviceName("bd4f254c81b0ca007f813a4a59f5433dce8a9446"));
//		  ArrayList<String> list =	  getIOSUDID();
//		  for (String string : list) {
//			  if(checkiOSDevice(string))
//				  System.out.println(" Divice present "+string);
//			  
//			  System.out.println("Device Name :"+getDeviceName(string));
//		}
	//		new   IOSDeviceConfiguration().setIOSWebKitProxyPorts("bd4f254c81b0ca007f813a4a59f5433dce8a9446");
//		new   IOSDeviceConfiguration().startIOSWebKit("bd4f254c81b0ca007f813a4a59f5433dce8a9446");
//		new IOSDeviceConfiguration().destroyIOSWebKitProxy();
		  closeAllIProxy();
		  
	  }
	  
	  public HashMap<String, String> setIOSWebKitProxyPorts(String device_udid) throws Exception {
	        try {
	            int webkitproxyport = ap.getPort();
	            deviceMap.put(device_udid, Integer.toString(webkitproxyport));
	        } catch (InterruptedException | IOException e) {
	            e.printStackTrace();
	        }
	        return deviceMap;
	    }
	  
	  public String startIOSWebKit(String udid) throws Exception {
//	        input = new FileInputStream("config.properties");
//	        prop.load(input);
		   setIOSWebKitProxyPorts(udid);
	     //   String serverPath = prop.getProperty(Reader.getConfigParam("APPIUM_JS_PATH"));
	        File file = new File(Reader.getConfigParam("APPIUM_JS_PATH"));
	        File curentPath = new File(file.getParent());
	        System.out.println(curentPath);
	        file = new File(curentPath + "/.." + "/..");
	        String ios_web_lit_proxy_runner =
	                file.getCanonicalPath() + "/bin/ios-webkit-debug-proxy-launcher.js";
	        String webkitRunner =
	                ios_web_lit_proxy_runner + " -c " + udid + ":" + deviceMap.get(udid) + " -d";
	        System.out.println(webkitRunner);
	        p1 = Runtime.getRuntime().exec(webkitRunner);
	        System.out.println(
	                "WebKit Proxy is started on device " + udid + " and with port number " + deviceMap
	                        .get(udid) + " and in thread " + Thread.currentThread().getId());
	        //Add the Process ID to hashMap, which would be needed to kill IOSwebProxywhen required
	        appiumServerProcess.put(Thread.currentThread().getId(), getPid(p1));
	        System.out.println("Process ID's:" + appiumServerProcess);
	        Thread.sleep(1000);
	        return deviceMap.get(udid);
	    }
	  
	  
	    public void destroyIOSWebKitProxy() throws IOException, InterruptedException {
	        Thread.sleep(3000);
	        if (appiumServerProcess.get(Thread.currentThread().getId()) != -1) {
	            String process = "pgrep -P " + appiumServerProcess.get(Thread.currentThread().getId());
	            Process p2 = Runtime.getRuntime().exec(process);
	            BufferedReader r = new BufferedReader(new InputStreamReader(p2.getInputStream()));
	            String command = "kill -9 " + r.readLine();
	            System.out.println("Kills webkit proxy");
	            System.out.println("******************" + command);
	            Runtime.getRuntime().exec(command);
	        }
	    }
	    
	    public static void closeAllIProxy()throws IOException, InterruptedException {	    	 
	    	    String[] args = new String[] {"/bin/bash", "-c", "ps -ax | grep -i '8100'|grep -v grep|awk '{print \"kill -9 \" $1}'|sh"};
	    	    Process proc;
	    	    try{
	        	   String[] arg2 = new String[] {"/bin/bash", "-c", "ps -ax | grep -i '8100'|grep -v grep"};
	        	    proc = new ProcessBuilder(arg2).start();   
	        	    BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	        	    if(r.readLine() != null){
	        	    proc = new ProcessBuilder(args).start();     
	        	    }
	           
	           }catch(Exception e){
	    	    
	    	        
	           }
	        
	    }
	    
	    

	  public int getPid(Process process) {

	        try {
	            Class<?> cProcessImpl = process.getClass();
	            Field fPid = cProcessImpl.getDeclaredField("pid");
	            if (!fPid.isAccessible()) {
	                fPid.setAccessible(true);
	            }
	            return fPid.getInt(process);
	        } catch (Exception e) {
	            return -1;
	        }
	    }


	  public static String getIOSDeviceNameAndVersion(String udid)
	            throws InterruptedException, IOException {
		  String[] cmd = {"idevice_id", "-u","bd4f254c81b0ca007f813a4a59f5433dce8a9446","|","ProductVersion" };	
	        return commandPrompt
	                .runCommand(cmd);
	    }

	  
	  public static ArrayList<String> getIOSUDID() {

	        try {
	            int startPos = 0;
	            int endPos = IOS_UDID_LENGTH - 1;
	            String profile = "system_profiler SPUSBDataType | sed -n -E -e '/(iPhone|iPad|iPod)/,"
	                    + "/Serial/s/ Serial Number: (.+)/\\1/p'";
	            String getIOSDeviceID = commandPrompt.runProcessCommandToGetDeviceID(profile);
	            if (getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID
	                    .isEmpty()) {
	                return null;
	            } else {
	                while (endPos < getIOSDeviceID.length()) {
	                    deviceUDIDiOS.add(getIOSDeviceID.substring(startPos, endPos + 1));
	                    startPos += IOS_UDID_LENGTH;
	                    endPos += IOS_UDID_LENGTH;
	                }
	                return deviceUDIDiOS;
	            }
	        } catch (InterruptedException | IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  
	  public Map<String, String> getIOSUDIDHash() {
	        try {
	        	  String[] cmd = {"idevice_id", "-l"};	
	            String getIOSDeviceID = commandPrompt.runCommand(cmd);
	            if (getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID
	                    .isEmpty()) {
	                return null;
	            } else {
	                String[] lines = getIOSDeviceID.split("\n");
	                for (int i = 0; i < lines.length; i++) {
	                    lines[i] = lines[i].replaceAll("\\s+", "");
	                    devices.put("deviceID" + i, lines[i]);
	                }
	                return devices;
	            }
	        } catch (InterruptedException | IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  public void checkExecutePermissionForIOSDebugProxyLauncher() throws IOException {

	        String serverPath = Reader.getConfigParam("APPIUM_JS_PATH");
	        File file = new File(serverPath);
	        File curentPath = new File(file.getParent());
	        System.out.println(curentPath);
	        file = new File(curentPath + "/.." + "/..");
	        File executePermission =
	                new File(file.getCanonicalPath() + "/bin/ios-webkit-debug-proxy-launcher.js");
	        if (executePermission.exists()) {
	            if (executePermission.canExecute() == false) {
	                executePermission.setExecutable(true);
	                System.out.println("Access Granted for iOSWebKitProxyLauncher");
	            } else {
	                System.out.println("iOSWebKitProxyLauncher File already has access to execute");
	            }
	        }
	    }
	  public static void checkIfiDeviceApiIsInstalled() throws InterruptedException, IOException {
		  String[] cmd = {"brew", "-l"};	
		  	        boolean checkMobileDevice =
	                commandPrompt.runCommand(cmd).contains("ideviceinstaller");
	        if (checkMobileDevice) {
	            System.out.println("iDeviceInstaller already exists");
	        } else {
	            System.out.println("Brewing iDeviceInstaller API....");
	            String[] cmd2 = {"brew", "install ideviceinstaller"};
	            commandPrompt.runCommand(cmd2);
	        }

	    }
	  
	  public static String getDeviceName(String udid) throws InterruptedException, IOException {
		  String[] cmd = {"idevice_id", "-u",udid};	
	        String deviceName =
	                commandPrompt.runCommand(cmd).replace("\\W", "_");
	        return deviceName;
	    }
	  
	  public static  boolean checkiOSDevice(String UDID) throws Exception {
	        try {
	        	String[] cmd = {"idevice_id", "-l"};	
	            String getIOSDeviceID = commandPrompt.runCommand(cmd);
	            boolean checkDeviceExists = getIOSDeviceID.contains(UDID);
	            if (checkDeviceExists) {
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	  
}

