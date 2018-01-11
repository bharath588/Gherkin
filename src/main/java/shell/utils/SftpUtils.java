package shell.utils;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintStream;
import java.util.Arrays;
//import java.util.Collections;
//import java.util.Vector;
//import org.apache.commons.io.FileUtils;






import lib.Log;
import lib.Log.Level;
import lib.Stock;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import core.framework.Globals;

public class SftpUtils {

	 //Common Declarations               
    public static String userName = Globals.GC_EMPTY;
    public static String password = Globals.GC_EMPTY;
                     
    //Declarations for SFTP  Connections   
    private static JSch sftpJsch = null;
    private static Session sftpSession = null;   
    private static Channel sftpChannel=null;
    private static boolean isSFTPConnected;
    private static ChannelSftp sftp = null;  
    static InetAddress addr;
    static String hostname = null;
 
    /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			establishSFTPConnection()	
    DESCRIPTION:	    establish the connection for SFTP
    PARAMETERS: 		None
    RETURNS:		    boolean Connection True or False                	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 06-11-2015       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/ 
    public static boolean establishSFTPConnection()
    {
    	if(isSFTPConnected)
        {
        return isSFTPConnected;
        }else{    
        try {  
        	Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
        	java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no"); 
            sftpJsch=new JSch();            
            userName = Stock.getConfigParam(Globals.GC_COLNAME_USERID);
			password =  Stock.getConfigParam(Globals.GC_COLNAME_SYSPASSWORD);
            sftpSession = sftpJsch.getSession(userName, Globals.GC_SHELL_HOST_NAME, Globals.GC_SHELL_PORT_NUMBER);
            sftpSession.setConfig(config);
            sftpSession.setPassword(password);
            sftpSession.setConfig("PreferredAuthentications", 
	                  "publickey,keyboard-interactive,password");
            sftpSession.connect();
            sftpChannel = sftpSession.openChannel(Globals.GC_SFTP_CHANNEL_NAME);
            sftpChannel.connect();
            isSFTPConnected=true;                  
        	}                             
        	catch(JSchException e){ e.printStackTrace(); }  
        	catch(Exception e) {e.printStackTrace(); }
        }          
    	return isSFTPConnected;                                               
    }
    
    
    /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			initializeRemoteDirectoryPath()	
    DESCRIPTION:	    This method initialize the directory path for the remote directories
    PARAMETERS: 		None
    RETURNS:		    void               	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 02-03-2016       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    public static void initializeRemoteDirectoryPath()
    {
    	establishSFTPConnection();
    	  String REMOTE_OUTPUT_PATH="\\\\work\\\\batch\\\\output";
          String REMOTE_INPUT_PATH="\\\\work\\\\batch\\\\input\\";    	  
          String REMOTE_ARCHIVE_PATH="\\\\work\\\\batch\\\\input\\\\archive\\";
    	if(isSFTPConnected)
    	{
    		try {
    			sftp = (ChannelSftp) sftpChannel;  
    			Globals.GC_REMOTE_OUTPUT_PATH=(sftp.getHome()+REMOTE_OUTPUT_PATH).replace("/home/d_keys/","\\\\fss-devlfs\\\\");
    			Globals.GC_REMOTE_INPUT_PATH=(sftp.getHome()+REMOTE_INPUT_PATH).replace("/home/d_keys/","\\\\fss-devlfs\\\\");
    			Globals.GC_REMOTE_ARCHIVE_PATH=(sftp.getHome()+REMOTE_ARCHIVE_PATH).replace("/home/d_keys/","\\\\fss-devlfs\\\\");
    			
    		}
    		catch(Exception e) { e.printStackTrace(); }
    		}
    }
    
    
    /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			upload_File(String srcFilePath,String dstFolderPath)	
    DESCRIPTION:	    Upload file from local to remote directory using SFTP
    PARAMETERS: 		String srcFilePath,String dstFolderPath
    RETURNS:		    void               	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 06-11-2015       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
     */  public static void upload_File(String srcFilePath,String dstFolderPath)
     {			
    	 establishSFTPConnection();
    	 if(isSFTPConnected)
    	 {
    		 try {
    			 sftp = (ChannelSftp) sftpChannel;    			
    			 sftp.put(srcFilePath,sftp.getHome()+dstFolderPath);    			     			  			
    		 }
    		 catch(SftpException e) { e.printStackTrace(); }
    	 }
     }
	
     /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
     FUNCTION:			download_File(String srcFolderPath, String remoteFolderPath,String fileNamePattern, String fileType,String dstFolderPath)	
     DESCRIPTION:	    Download Latest file from remote to local directory using SFTP
     PARAMETERS: 		String srcFolderPath,String fileNamePattern, String fileType,String dstFolderPath
     RETURNS:		    void               	
     REVISION HISTORY: 
     ------------------------------------------------------------------------------------------------------------------------------------------------------------
     Author : Janani     Date : 06-11-2015       
     ------------------------------------------------------------------------------------------------------------------------------------------------------------

      */ public static File download_File(String srcFolderPath,String remoteFileFolderPath,String fileNamePattern, String fileType,String dstFolderPath)
      {
    	  establishSFTPConnection();
    	  File downloadFile=null;     	  
    	  if(isSFTPConnected) { 
    		  try{
    			  sftp = (ChannelSftp) sftpChannel;
    			  File remoteFile=new File(remoteFileFolderPath);
    			   			  
    			  sftp.cd(sftp.getHome() + srcFolderPath);
    			  File[] listOfFiles = remoteFile.listFiles(new FileFilter() {          
    				  public boolean accept(File file) {
    					  return file.isFile(); } });
    			  Arrays.sort(listOfFiles); 	
    			  for (File file : listOfFiles) {
    				  if (file.getName().startsWith(fileNamePattern) && file.getName().endsWith(fileType)) 
    					  downloadFile = file;								
    			  } 
    			  System.out.println(downloadFile.getName());
    			  sftp.get(sftp.getHome() + srcFolderPath+downloadFile.getName(), dstFolderPath); 
       		  }
    		  catch(SftpException e)
    		  {
    			  e.printStackTrace();
    		  }
    	  }
		return downloadFile;
      }

      /*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
		FUNCTION:			cleanUpSFTPSession()
		DESCRIPTION:	    Disconnect SFTP Session
		PARAMETERS: 		None
		RETURNS:		    void
		EXAMPLE:	        cleanUpSFTPSession()	
		REVISION HISTORY: 
		------------------------------------------------------------------------------------------------------------------------------------------------------------
		Author : Janani     Date : 06-11-2015       
		------------------------------------------------------------------------------------------------------------------------------------------------------------
       */ public static void cleanUpSFTPSession()
       		{
                    sftpChannel.disconnect();                              
                    sftpSession.disconnect();
       		}
       
       public static String getHostname() 
       {
    	  try{
    	    addr = InetAddress.getLocalHost();
    	    hostname = addr.getHostName();
    	  }catch(Exception e)
    	  {
    		  Globals.exception= e;
    		  Log.Report(Level.ERROR, "Exception occured while getting the hostname");
    	  }
		    return hostname;
    	   
       }
       
       public static String getHostname(String ipAddress)
       {
    	   try{
    	   String hostAddress = (ipAddress.split("[/]")[2]).split(":")[0];
    	   addr = InetAddress.getByName(hostAddress);
    	   hostname = addr.getHostName();
    	   }catch(Exception e)
    	   {
    		  Globals.exception= e;
     		  Log.Report(Level.ERROR, "Exception occured while getting the hostname");
    	   }
    	   return hostname;
       }
}
