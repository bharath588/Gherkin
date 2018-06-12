package bdd_core.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;



public class ThrowException  {

	private static final String EXCEPTION = Globals.GC_EXPNTYPE_EXCEPTION;
	private static final String NULLPOINTER = Globals.GC_EXPNTYPE_NULLPOINTER;
	private static final String CLASSNOTFOUND = Globals.GC_EXPNTYPE_CLASSNOTFOUND;
	private static final String INTERRUPTED = Globals.GC_EXPNTYPE_INTERRUPTED;
	private static final String NOSUCHMETHOD = Globals.GC_EXPNTYPE_NOSUCHMETHOD;
	private static final String NOSUCHFIELD = Globals.GC_EXPNTYPE_NOSUCHFIELD;
	private static final String ILLEGALSTATE = Globals.GC_EXPNTYPE_ILLEGALSTATE;
	private static final String IOEXCEPTION = Globals.GC_EXPNTYPE_IOEXCEPTION;
	
	public enum TYPE {
		EXCEPTION, NULLPOINTER, CLASSNOTFOUND, INTERRUPTED, NOSUCHMETHOD, NOSUCHFIELD, ILLEGALSTATE, IOEXCEPTION
	}
	
	public static void Report(ThrowException.TYPE expType, String errorMessage) {
		try{
			if(expType.toString().equalsIgnoreCase(EXCEPTION)){
				throw new Exception(errorMessage);
			}
			
			if(expType.toString().equalsIgnoreCase(NULLPOINTER)){
				throw new NullPointerException(errorMessage);
			}
			
			if(expType.toString().equalsIgnoreCase(CLASSNOTFOUND)){
				throw new ClassNotFoundException(errorMessage);
			}
			
			if(expType.toString().equalsIgnoreCase(INTERRUPTED)){
				throw new InterruptedException(errorMessage);
			}	
			
			if(expType.toString().equalsIgnoreCase(NOSUCHMETHOD)){
				throw new NoSuchMethodException(errorMessage);
			}
			
			if(expType.toString().equalsIgnoreCase(NOSUCHFIELD)){
				throw new NoSuchFieldException(errorMessage);
			}
			
			if(expType.toString().equalsIgnoreCase(ILLEGALSTATE)){
				throw new IllegalStateException(errorMessage);
			}	
			
			if(expType.toString().equalsIgnoreCase(IOEXCEPTION)){
				throw new IOException(errorMessage);
			}				
			
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);	
				
		}
	}
	
}
