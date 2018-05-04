package aag.transaction_request_file;

import core.framework.Globals;
import lib.Stock;
import shell.utils.ShellUtils;
import aag.aag_lib.General;

public class Transaction_Request_Core_Batch_Process {

	
	/**
	 * This method use to run upsu process commands
	 * @param dbInstance
	 * @param effectiveDate
	 * @param rebalBuyFundId
	 * @param rebalSellFundId
	 */
	
	//pass the household id and fetch the inner map and run 
	public static void runUPSUProcess(String dbInstance,String effectiveDate,String rebalBuyFundId,String rebalSellFundId)
	{
			//Get Participant Database
			System.out.println("Core Batch Effective Date: "+effectiveDate);
			//ShellUtils.executeShellCommand("setenv ISIS_HOME /opt/isis/stage/qsinst/");
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("cd $ISIS_HOME/bin/");
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("pwd");
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("db "+ dbInstance);	
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("setenv UPSU_DEBUG_LEVEL TRUE");
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("setenv UPSU_RUN_DATE "+ effectiveDate);
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("setenv UPSU_MAIL_LIST "+Stock.getConfigParam(Globals.GC_COLNAME_USERID).toUpperCase());
			General.threadSleepInvocation(5000);
			ShellUtils.executeShellCommand("setenv UPSU_MAIL_LIST2 "+Stock.getConfigParam(Globals.GC_COLNAME_USERID).toUpperCase());
			General.threadSleepInvocation(5000);	
			ShellUtils.executeShellCommand("setenv UPSU_DBTYPE "+dbInstance.toLowerCase());
			General.threadSleepInvocation(5000);
			ShellUtils.runAndWriteShellCommandsToFile("GQ19UPSU "+rebalBuyFundId+" "+effectiveDate);
			General.threadSleepInvocation(5000);
			ShellUtils.runAndWriteShellCommandsToFile("GQ19UPSU "+rebalSellFundId+" "+effectiveDate);
			General.threadSleepInvocation(5000);	
		}
	
	//pass the household id and fetch the inner map and run 
		public static void runUPSUProcessForArray(String dbInstance,String effectiveDate,String[] rebalBuyFundIds)
		{
				//Get Participant Database
				System.out.println("Core Batch Effective Date: "+effectiveDate);
				//ShellUtils.executeShellCommand("setenv ISIS_HOME /opt/isis/stage/qsinst/");
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("cd $ISIS_HOME/bin/");
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("pwd");
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("db "+ dbInstance);	
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("setenv UPSU_DEBUG_LEVEL TRUE");
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("setenv UPSU_RUN_DATE "+ effectiveDate);
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("setenv UPSU_MAIL_LIST "+Stock.getConfigParam(Globals.GC_COLNAME_USERID).toUpperCase());
				General.threadSleepInvocation(5000);
				ShellUtils.executeShellCommand("setenv UPSU_MAIL_LIST2 "+Stock.getConfigParam(Globals.GC_COLNAME_USERID).toUpperCase());
				General.threadSleepInvocation(5000);	
				ShellUtils.executeShellCommand("setenv UPSU_DBTYPE "+dbInstance.toLowerCase());
				General.threadSleepInvocation(5000);
				for(int i=0;i<rebalBuyFundIds.length;i++)
				{
					ShellUtils.runAndWriteShellCommandsToFile("GQ19UPSU "+rebalBuyFundIds[i]+" "+effectiveDate);
					General.threadSleepInvocation(5000);	
				}
			}
	
	
	/**
	 * This method use to run Transfer process commands
	 * @param dbInstance
	 * @param tfbaId
	 */
	public static void runTransferProcess(String dbInstance,String tfbaId)
	{
		System.out.println("TfbaId : " + tfbaId);
		//ShellUtils.executeShellCommand("setenv ISIS_HOME /opt/isis/stage/qsinst/");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand("cd $ISIS_HOME/bin");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand("setenv TRANSFER_DEBUG TRUE");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand("setenv TRANSFER_COMMIT TRUE");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand("db "+ dbInstance);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand("setenv TRFB1_TFBA "+tfbaId);
		General.threadSleepInvocation(5000);	
		ShellUtils.runAndWriteShellCommandsToFile("GQ19TRFB1");
		General.threadSleepInvocation(5000);	
		ShellUtils.runAndWriteShellCommandsToFile("GQ19TRFB");
	}
}