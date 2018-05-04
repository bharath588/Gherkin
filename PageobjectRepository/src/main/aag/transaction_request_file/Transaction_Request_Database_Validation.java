package pageobjects.transaction_request_file;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;

import aag_lib.General;
import aag_lib.GlobalVar;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;

public class Transaction_Request_Database_Validation {

	public static Map<String,String> tempAttrMap;
	public static Map<String,Map<String,String>> tempMap;
	public static List<String> transactionIdrandomList = new LinkedList<String>();
	public static Map<String,String> transactionIdMap;
	public static Map<String,String> rebalanceFundAllocMap;
	public static Map<String,String> futureFundAllocMap;
	public static LinkedHashMap<String, Map<String, String>> multiplePptMap= new LinkedHashMap<String,Map<String,String>>();

	
	/**
	 * This method returns the eligible funds under a paerticipant
	 * @param planId
	 * @param indId
	 * @return
	 */
	public static ResultSet getParticipantEligibleFunds(String planId,String indId)
	{		
		  ResultSet partEligibleFundsResSet =null;
		  try {
			String[] getEligibleFundsQuery=Stock.getTestQuery("getEligibleFunds");
			partEligibleFundsResSet = DB.executeQuery("MemberParticipantDB", getEligibleFundsQuery[1],planId,planId,planId,indId);
			DB.getRecordSetCount(partEligibleFundsResSet);
		  }
		  catch(Exception e) {e.printStackTrace();}
		return partEligibleFundsResSet;
	}
	
	/**
	 * This method provides the existing funds for a participant
	 * @param indId
	 * @return
	 */
	public static ResultSet getParticipantExistingFunds(String indId)
	{
		  ResultSet partExistingFundsResSet =null;
		  try {
			String[] getExistingFundsQuery= Stock.getTestQuery("getExistingFunds");
			partExistingFundsResSet = DB.executeQuery("MemberParticipantDB", getExistingFundsQuery[1],indId);
			DB.getRecordSetCount(partExistingFundsResSet);
		  }
		  catch(Exception e) {e.printStackTrace(); }
		return partExistingFundsResSet;
	}

	/**
	 * This method generated the unique transaction id and adds to a list
	 * @return
	 */
	public static String getTransactionId() {
		String transId = RandomStringUtils.random(18, "0123456789");
	
		if (transactionIdrandomList.contains(transId)) {
			getTransactionId();
		}
		transactionIdrandomList.add(transId);
		return transId;
	}
	
	/**
	 * This method adds the transaction id to the global map
	 */
	public static void addTransIdtoGlobalMap() {
		int iCount = 0;
		tempMap = General.globalMultiPptMap;
		for (Entry<String, Map<String, String>> entry : tempMap.entrySet()) {
			tempAttrMap = entry.getValue();
			String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
			transactionIdMap = tempMap
					.get(houseHoldId);
			transactionIdMap.put(GlobalVar.transactionId,
					transactionIdrandomList.get(iCount));
			Transaction_Request_Database_Validation.multiplePptMap
			.put(houseHoldId, transactionIdMap);
			iCount++;
		}
		General.globalMultiPptMap = Transaction_Request_Database_Validation.multiplePptMap;
		System.out.println(General.globalMultiPptMap);
	}
	
	/**
	 * This method adds the rebalance funds to the global map
	 */
	
	public static void addRebalFundsTOGlobalMap()
	{
		tempMap = General.globalMultiPptMap;
		for (Entry<String, Map<String, String>> entry : tempMap.entrySet()) {
			try{
				tempAttrMap = entry.getValue();
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				rebalanceFundAllocMap = tempMap.get(houseHoldId);
				rebalanceFundAllocMap.put(GlobalVar.rebalBuyFundId,(Generate_Transaction_Request_Input_XML_File.rebalanaceFundAllocationMap.get(houseHoldId)).get(GlobalVar.rebalBuyFundId));
				rebalanceFundAllocMap.put(GlobalVar.rebalSellFundId,(Generate_Transaction_Request_Input_XML_File.rebalanaceFundAllocationMap.get(houseHoldId)).get(GlobalVar.rebalSellFundId));
				Transaction_Request_Database_Validation.multiplePptMap.put(houseHoldId, rebalanceFundAllocMap);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		General.globalMultiPptMap = Transaction_Request_Database_Validation.multiplePptMap;
	}
	
	/**
	 * This method adds the future funds to the global map
	 */
	
	public static void addFutureFundsTOGlobalMap()
	{
		tempMap = General.globalMultiPptMap;
		for (Entry<String, Map<String, String>> entry : tempMap.entrySet()) {
			tempAttrMap = entry.getValue();
			String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
			futureFundAllocMap = tempMap.get(houseHoldId);
			futureFundAllocMap.put(GlobalVar.futureFundallocationOne,(Generate_Transaction_Request_Input_XML_File.futureFundAllocationMap.get(houseHoldId)).get(GlobalVar.futureFundallocationOne));
			futureFundAllocMap.put(GlobalVar.futureFundallocationTwo,(Generate_Transaction_Request_Input_XML_File.futureFundAllocationMap.get(houseHoldId)).get(GlobalVar.futureFundallocationTwo));
			Transaction_Request_Database_Validation.multiplePptMap.put(houseHoldId, futureFundAllocMap);
		}
		General.globalMultiPptMap = Transaction_Request_Database_Validation.multiplePptMap;
	}
	
	
	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			verifyFutureAllocation()	
    DESCRIPTION:	    This method returns results of future allocation funds which are processed
    PARAMETERS: 		None
    RETURNS:		   	None      	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 13-01-2016       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
  */public static void verifyFutureAllocationForTransactionRequest(String participantIndividualId,String participantHouseholdId,String planNumber)
{
	  ResultSet futureAllocationResultSet =null;
	  try {		
		  String[] futureAllocationVerificationQuery =Stock.getTestQuery("futureAllocationVerification");		
		  futureAllocationResultSet = DB.executeQuery("MemberParticipantDB", futureAllocationVerificationQuery[1], participantIndividualId,planNumber);
		  int futureAllocoationCount=	DB.getRecordSetCount(futureAllocationResultSet);			
			while(futureAllocationResultSet.next())
			{
				 String futureFundId= futureAllocationResultSet.getString("SDIO_ID");
				  Date futureFundEffDate=futureAllocationResultSet.getDate("effdate");
				  int futureFundPercentage=futureAllocationResultSet.getInt("percent");
				  Reporter.logEvent(Status.PASS, "Future Allocation: Verify details from database for the participant with \n" +
						  "Household Id: "+participantHouseholdId+ " and \n"+
						  "Individual Id: "+participantIndividualId,
						  "The following fund is opted for furture allocation which is effective from next pay check \n"
						  +"\n"+"Participant Household Id: "+participantHouseholdId
						  +"\n"+"Participant Individual Id: "+participantIndividualId
						  +"\n"+"Participant Pln Id: "+planNumber
						  +"\n"+"Future Fund ID: "+futureFundId 
						  +"\n"+ "Future Fund Eff Date: "+futureFundEffDate
						  +"\n"+"Future Fund Allocation Percentage: "+futureFundPercentage, false);
			}
			if(futureAllocoationCount<=0)
			Reporter.logEvent(Status.FAIL, "Future Allocation: Verify details from database",
					"The future allocation details are not available for this participant", false);
			
	}
		catch(SQLException e) { e.printStackTrace();}	
		catch(Exception e) { e.printStackTrace();}	
}
	
	
	
	
	
}
