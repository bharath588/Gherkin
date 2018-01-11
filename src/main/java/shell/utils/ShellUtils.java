package shell.utils;

import java.io.*;

//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.input.ReaderInputStream;


import lib.Stock;

import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;




import core.framework.Globals;

public class ShellUtils {
		
	//Common Declarations               

	public static String userName =  Globals.GC_EMPTY;
	public static String password =  Globals.GC_EMPTY;

	//Declarations for Running Shell Commands
	private static JSch shellJsch = null;
	private static Session shellSession = null;   
	private static Channel shellChannel=null;
	private static boolean isShellConnected;               
	private static File shellFile = new File("C://temp//RunShell.txt");
	private static PrintStream shellCommands;   
	private static BufferedReader shellBufferedReader;
	private static FileWriter shellFileWriter=null;
	private static BufferedWriter shellBufferedWriter=null;

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
			               	FUNCTION:			establishShellConnection()	
			                DESCRIPTION:	    establish the connection for triggering shell commands
			                PARAMETERS: 		None
			                RETURNS:		    boolean connected or not                	
			                REVISION HISTORY: 
			                ------------------------------------------------------------------------------------------------------------------------------------------------------------
			                Author : Janani     Date : 03-11-2015       
			                ------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */ public static boolean establishShellConnection()
	 { 
		 if(isShellConnected)  
			 return isShellConnected;
		 else {                  
			 try {
				 Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
				 userName = Stock.getConfigParam(Globals.GC_COLNAME_USERID);
				 password =  Stock.getConfigParam(Globals.GC_COLNAME_SYSPASSWORD);
				shellJsch=new JSch();
				 shellSession = shellJsch.getSession(userName, Globals.GC_SHELL_HOST_NAME, Globals.GC_SHELL_PORT_NUMBER);
				 shellSession.setPassword(password);                                             
				 shellSession.setUserInfo(new MyUserInfo());
				 shellSession.setConfig("PreferredAuthentications", 
						 "publickey,keyboard-interactive,password");
				 shellSession.connect();                                                                          
				 shellChannel = shellSession.openChannel(Globals.GC_SHELL_CHANNEL_NAME);
				 OutputStream inputForChannel = shellChannel.getOutputStream();
				 shellCommands = new PrintStream(inputForChannel,true);
				 shellBufferedReader = new BufferedReader(new InputStreamReader(shellChannel.getInputStream()));
				 shellChannel.connect();
				 isShellConnected=true; }
			 catch(JSchException e) { System.out.println(e.getMessage()); }                                                           
			 catch(IOException e){ e.printStackTrace(); }
			 catch(Exception e) { e.printStackTrace(); }
			 		 }
		 return isShellConnected; 
	 }

	 /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
       	FUNCTION:			runAndWriteShellCommandsToFile(String command)
        DESCRIPTION:	    Runs the shell Command and write details to a File
        PARAMETERS: 		String shell command
        RETURNS:		    void
        EXAMPLE:	        runAndWriteShellCommandsToFile( shell command)	
        REVISION HISTORY: 
        ------------------------------------------------------------------------------------------------------------------------------------------------------------
        Author : Janani     Date : 04-11-2015       
		----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  */ public static void runAndWriteShellCommandsToFile(String command)
	  {
		  establishShellConnection();
		  String commandLine;
		  if(isShellConnected)
		  {
			  try {                                                                                        
				  shellFileWriter = new FileWriter(shellFile,true);
				  shellBufferedWriter = new BufferedWriter(shellFileWriter);                                               
				  shellCommands.print(command + "\necho '" + Globals.GC_COMMAND_COMPLETE_STATUS + "'\n");                                                    
				  while((commandLine = shellBufferedReader.readLine()).equalsIgnoreCase(Globals.GC_COMMAND_COMPLETE_STATUS) == false)
				  {                                                                              
					  System.out.println(commandLine);
					  shellBufferedWriter.write(commandLine);
					  shellBufferedWriter.newLine();                                                                                         
				  }      
				  shellBufferedWriter.close();
			  } catch(Exception e) {e.printStackTrace(); }                   
		  }                                    
	  }

	  /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
       	FUNCTION:			runAndWriteShellCommandsToFile(String integerArgument)
        DESCRIPTION:	    Runs the shell Command and write details to a File
        PARAMETERS: 		Integer command
        RETURNS:		    void
        EXAMPLE:	        runAndWriteShellCommandsToFile(1)	
        REVISION HISTORY: 
        ------------------------------------------------------------------------------------------------------------------------------------------------------------
        Author : Janani     Date : 06-11-2015       
			                ------------------------------------------------------------------------------------------------------------------------------------------------------------
	   */ public static void runAndWriteShellCommandsToFile(int integerArgument)
	   {
		   establishShellConnection();
		   String commandLine;                   
		   if(isShellConnected)
		   {
			   try {                                                                                        
				   shellFileWriter = new FileWriter(shellFile,true);
				   shellBufferedWriter = new BufferedWriter(shellFileWriter);                                               
				   shellCommands.print(integerArgument + ";echo '" + Globals.GC_COMMAND_COMPLETE_STATUS + "'\n");                                                         
				   while((commandLine = shellBufferedReader.readLine()).equalsIgnoreCase(Globals.GC_COMMAND_COMPLETE_STATUS) == false)
				   {                                                                                                      
					   System.out.println(commandLine);
					   shellBufferedWriter.write(commandLine);
					   shellBufferedWriter.newLine();                                                                                                                                  
				   } 
				   shellBufferedWriter.close();
			   } catch(Exception e) {}             
		   }                                    
	   }

	   /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
			           	FUNCTION:			writingConsoleDataToFile()
			            DESCRIPTION:	    write shell command details to a File
			            PARAMETERS: 		None
			            RETURNS:		    void
			            EXAMPLE:	        writingConsoleDataToFile(1)	
			            REVISION HISTORY: 
			            ------------------------------------------------------------------------------------------------------------------------------------------------------------
			            Author : Janani     Date : 06-11-2015       
			            ------------------------------------------------------------------------------------------------------------------------------------------------------------
	    */ public static void writingConsoleDataToFile()
	    {
	    	try {
	    		if (!shellFile.exists())  {           
	    			shellFile.createNewFile();}    
	    		shellFileWriter = new FileWriter(shellFile,true);
	    		shellBufferedWriter = new BufferedWriter(shellFileWriter);
	    		String commandLines = "echo '" + Globals.GC_COMMAND_COMPLETE_STATUS + "'\n";
	    		shellCommands.print(commandLines);
	    		while((commandLines = shellBufferedReader.readLine()).equalsIgnoreCase(Globals.GC_COMMAND_COMPLETE_STATUS) == false)
	    		{                                                                              
	    			System.out.print(commandLines + "\n");
	    			shellBufferedWriter.write(commandLines + "\n");
	    			shellBufferedWriter.newLine();                                                                                                      
	    		}  
	    		shellBufferedWriter.close();
	    	}catch (Exception e) {e.printStackTrace(); }
	    }

	    /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
			       	FUNCTION:			executeShellCommand(String command)
			        DESCRIPTION:	    Runs the shell Commands
			        PARAMETERS: 		String command
			        RETURNS:		    void
			        EXAMPLE:	        executeShellCommand(command)	
			        REVISION HISTORY: 
			        ------------------------------------------------------------------------------------------------------------------------------------------------------------
			        Author : Janani     Date : 06-11-2015       
			        ------------------------------------------------------------------------------------------------------------------------------------------------------------
	     */  public static void executeShellCommand(String command)
	     {
	    	 establishShellConnection();
	    	 if(isShellConnected)                     
	    		 shellCommands.print(command + "\n");                  
	    	 else
	    		 System.out.println("Shell is not connected");                                            
	     }

	     /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
			   	FUNCTION:			executeShellCommand(int command)
			    DESCRIPTION:	    Runs the shell Commands
			    PARAMETERS: 		int command
			    RETURNS:		    void
			    EXAMPLE:	        executeShellCommand(1)	
			    REVISION HISTORY: 
			    ------------------------------------------------------------------------------------------------------------------------------------------------------------
			    Author : Janani     Date : 06-11-2015       
			    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	      */	 public static void executeShellCommand(int command)
	      {
	    	  establishShellConnection();
	    	  if(isShellConnected)
	    		  shellCommands.print(command + "\n");
	    	  else 
	    		  System.out.println("Shell is not connected");                                    
	      }
	      /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
			FUNCTION:			cleanUpShellSession()
			DESCRIPTION:	    Disconnect Shell Session
			PARAMETERS: 		None
			RETURNS:		    void
			EXAMPLE:	        cleanUpShellSession()	
			REVISION HISTORY: 
			------------------------------------------------------------------------------------------------------------------------------------------------------------
			Author : Janani     Date : 06-11-2015       
			------------------------------------------------------------------------------------------------------------------------------------------------------------
	       */ public static void cleanUpShellSession()
	       {
	    	   shellChannel.disconnect();                              
	    	   shellSession.disconnect();
	       }



}
