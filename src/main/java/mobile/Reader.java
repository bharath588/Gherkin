package mobile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import lib.Log;
import lib.Log.Level;
import core.framework.Globals;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

/**
 * Class will read Mobile Config file
 * @author siddartha
 *
 */
public class Reader {
	private static Map<String, String> globalMobileParam = new LinkedHashMap<String, String>();
	private static Map<String, String> userNameParam = new LinkedHashMap<String, String>();

	 
    public static String getConfigParam(String parameterName) {
		return globalMobileParam.get(parameterName.trim().toUpperCase());
    }
    
    public static String getUserNameParam(String parameterName) {
  		return userNameParam.get(parameterName.trim().toUpperCase());
      }
    
    public static Map<String, String> getMobileParam() {
		return globalMobileParam;
	}
    
    public static Map<String, String> getUserNameParam() {
		return userNameParam;
	}
    
    public static void setMobileParam(String configPath) {
		readConfigPropertyForMobile(configPath);
	}
    public static void setUserNameParam(String configPath) {
    	readUserConfigPropertyForMobile(configPath);
	}
//	public static void setMobileParam(Map<String, String> mobileParam) {
//	globalMobileParam = mobileParam;
//}
	public static Map<String, String> globalMobileParam() {
		return globalMobileParam;
	}
	
	public static Map<String, String> userNameParam() {
		return userNameParam;
	}
    
	/**
	 * Read mobile config file
	 * @param configPath
	 */
    public static void readConfigPropertyForMobile(String configPath) {
		Properties prop = new Properties();
		InputStream ins = null;
		if (getMobileParam().isEmpty()) {
			try {
				ins = new FileInputStream(configPath);
				Log.Report(Level.INFO, "reading " + configPath + " file");
				prop.load(ins);
				Enumeration<?> e = prop.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String val = prop.getProperty(key);
					getMobileParam().put(
							key.toString().trim().toUpperCase(), val);
					Log.Report(Level.DEBUG, "setting @globalParam with key :"
							+ key + " -- value :" + val);
					getMobileParam().remove(Globals.GC_EMPTY);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				ThrowException.Report(TYPE.EXCEPTION, "Unable to read Config :" +configPath
						+ e.getMessage());
			} finally {
				if (ins != null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
    public static void readUserConfigPropertyForMobile(String configPath) {
		Properties prop = new Properties();
		InputStream ins = null;
		if (getUserNameParam().isEmpty()) {
			try {
				ins = new FileInputStream(configPath);
				Log.Report(Level.INFO, "reading " + configPath + " file");
				prop.load(ins);
				Enumeration<?> e = prop.propertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String val = prop.getProperty(key);
					getUserNameParam().put(
							key.toString().trim().toUpperCase(), val);
					Log.Report(Level.DEBUG, "setting @globalParam with key :"
							+ key + " -- value :" + val);
					getUserNameParam().remove(Globals.GC_EMPTY);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				ThrowException.Report(TYPE.EXCEPTION, "Unable to read Config :" +configPath
						+ e.getMessage());
			} finally {
				if (ins != null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
