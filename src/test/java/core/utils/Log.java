package core.utils;

import org.apache.log4j.Logger;
import core.framework.Globals;

public class Log {

	private static final String DEBUG = Globals.GC_LOG_DEBUG;
	private static final String INFO = Globals.GC_LOG_INFO;
	private static final String WARN = Globals.GC_LOG_WARN;
	private static final String ERROR = Globals.GC_LOG_ERR;
	private static final String TRACE = Globals.GC_LOG_TRACE;
	
	public enum Level {
		DEBUG, INFO, WARN, ERROR, TRACE
	}
		
	public static void Report(Log.Level level,String description){
		final Logger report = Logger.getLogger(Log.class.getName());
		
		if(level.toString().equalsIgnoreCase(DEBUG)){
			report.debug(description);
		}
		
		if(level.toString().equalsIgnoreCase(INFO)){
			report.info(description);
		}
		
		if(level.toString().equalsIgnoreCase(WARN)){
			report.warn(description);
		}
		
		if(level.toString().equalsIgnoreCase(ERROR)){
			report.error(description);
		}	
		
		if(level.toString().equalsIgnoreCase(TRACE)){
			report.error(description);
		}
	}
}
