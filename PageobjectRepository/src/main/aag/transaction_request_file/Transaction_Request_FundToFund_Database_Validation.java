package pageobjects.transaction_request_file;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;
import aag_lib.General;
import aag_lib.GlobalVar;


public class Transaction_Request_FundToFund_Database_Validation {

	public static String participantTFBA_ID=null;
	public static String transEffectiveDate=null;
	public static String reBalanceStatus=null;
	public static Map<String, String> tfbaIdMap;
	public static Map<String, String> updatedEffdateMap;
	public static LinkedHashMap<String, Map<String, String>> multiplePptMap= new LinkedHashMap<String,Map<String,String>>();
	public static Map<String,Map<String,String>> tempMap;
	public static Map<String,String> tempAttrMap;
	public static LinkedHashMap<String, Map<String, String>> tempmultiplePptMap= new LinkedHashMap<String,Map<String,String>>();
	public static Map<String, String> tempFundAttrMap ;
	public static Map<String,Map<String, String>> tempPlanWiseFundAttrMap ;
	public static Map<String,Map<String, String>> tempTfbaPropMap ;
	public static Map<String,Map<String,Map<String,String>>> tempPlanWiseFundAllocations = new LinkedHashMap<>();
	//////============ iterate based on number of plans
	/**
	 * This method used to fetch tfba id for the respective individual id
	 * and add the details to the map which is having the tfba id 
	 * @throws Exception
	 */
	public static void runQueryToGetTFBAId() throws Exception
	{
		ResultSet queryResultSet = null;
		
		boolean isValueExist;
		ResultSet tfbaIDResultSet=null;
		multiplePptMap = General.globalMultiPptMap;
		String getTFBAIdQuery[]=Stock.getTestQuery("getTFBAId");
		try
		{
			for (Entry<String, Map<String, String>> entry : multiplePptMap.entrySet())
			{
				
				isValueExist = false;
				tempAttrMap = entry.getValue();
				
				String individualId = tempAttrMap.get(GlobalVar.Individualid);
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				tempTfbaPropMap = new LinkedHashMap<>();
				tempPlanWiseFundAttrMap = GlobalVar.planWiseFundAllocations.get(individualId);
				for(Entry<String,Map<String,String>> objEntryPlan : tempPlanWiseFundAttrMap.entrySet())
				{
					
				String planNumber = objEntryPlan.getKey();
				tempFundAttrMap = objEntryPlan.getValue();
				tfbaIDResultSet = DB.executeQuery("MemberParticipantDB", getTFBAIdQuery[1],planNumber,individualId);
				if(tfbaIDResultSet!= null)
				{
					while(tfbaIDResultSet.next())
					{
						isValueExist = true;
						participantTFBA_ID= tfbaIDResultSet.getString("id");			
						
					}
				}else
					Reporter.logEvent(Status.FAIL, "Verify rebalance allocation and get participant tfbaId", "TFBA ID has not been generated for participant with individual id :" +individualId+"& household Id"+houseHoldId, false);
				
				queryResultSet = DB.executeQuery("MemberParticipantDB", Stock.getTestQuery("getTfbaIdProperties")[1], participantTFBA_ID);
				while(queryResultSet.next())
				{
					transEffectiveDate =queryResultSet.getString("effdate");
					reBalanceStatus = queryResultSet.getString("status_code");
					tfbaIdMap= multiplePptMap.get(houseHoldId);
					tfbaIdMap.put(GlobalVar.effectiveDate, transEffectiveDate);
					tfbaIdMap.put(GlobalVar.tfbaId,participantTFBA_ID);
					tfbaIdMap.put(GlobalVar.statusCode, reBalanceStatus);
					//need to remove this break statement since we are going to get the tfba id as per the plan
					break;
				}
				
				if(isValueExist)
				{
					tempmultiplePptMap.put(houseHoldId, tfbaIdMap);
					tempFundAttrMap.put(GlobalVar.tfbaId, participantTFBA_ID);
				}
				tempTfbaPropMap.put(planNumber, tempFundAttrMap);
			}
			tempPlanWiseFundAllocations.put(individualId, tempTfbaPropMap);	
			}
			General.globalMultiPptMap = tempmultiplePptMap;
			GlobalVar.planWiseFundAllocations = tempPlanWiseFundAllocations;
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	/**
	 * 
	 * @param expectedStatus
	 * @param houseHoldId
	 * @param tfbaId
	 * @param rebalBuyFundId
	 * @param rebalSellFundId
	 */

	public static void getFundDetailsForReBalance(String expectedStatus,String houseHoldId,String tfbaId,String rebalBuyFundId,String rebalSellFundId)
	{
		try {
				String getFundIdQuery[]=Stock.getTestQuery("getFundDetails");
				ResultSet fundIdRs = DB.executeQuery("MemberParticipantDB", getFundIdQuery[1],
						tfbaId,rebalBuyFundId,rebalSellFundId);

				int fundDetailsCount=DB.getRecordSetCount(fundIdRs);	

				while(fundIdRs.next()){		
					if(expectedStatus.equalsIgnoreCase(fundIdRs.getString("status_code")))
						Reporter.logEvent(Status.PASS, "Rebalance Allocation: Verify Fund Details in TRF_Detl Table for participant \n "+
								"Household Id: "+houseHoldId+" and \n"+
								"Individual Id: "+fundIdRs.getString("ind_id"),
								"The following fund is opted for rebalance allocation: \n"
										+ "\n"+"Participant HouseHold Id: "+houseHoldId
										+ "\n"+"Participant Individual Id: "+fundIdRs.getString("ind_id")					
										+ "\n"+"TFBA_ID: " +fundIdRs.getString("TFBA_ID")
										+ "\n"+"Fund ID: "+fundIdRs.getString("SDIO_ID") 
										+ "\n"+"Fund Eff Date: "+fundIdRs.getString("effdate")
										+ "\n"+"Status: "+fundIdRs.getString("status_code") 
										+ "\n"+"Percentage: "+fundIdRs.getString("req_percent")
										+ "\n"+"Fund Id To / From: "+fundIdRs.getString("to_from_ind")
										+ "\n"+"Fund Deferral Type: "+fundIdRs.getString("sdmt_code"), false);
					else
						Reporter.logEvent(Status.FAIL, "After Core Batch, Rebalance Allocation: Verify Fund Details in TRF_Detl Table for participant \n "+
								"Household Id: "+houseHoldId+" and \n"+
								"Individual Id: "+fundIdRs.getString("ind_id"),
								"The following fund is opted for rebalance allocation: \n"
										+ "\n"+"Participant HouseHold Id: "+houseHoldId
										+ "\n"+"Participant Individual Id: "+fundIdRs.getString("ind_id")					
										+ "\n"+"TFBA_ID: " +fundIdRs.getString("TFBA_ID")
										+ "\n"+"Fund ID: "+fundIdRs.getString("SDIO_ID") 
										+ "\n"+"Fund Eff Date: "+fundIdRs.getString("effdate")
										+ "\n"+"Status: "+fundIdRs.getString("status_code") 
										+ "\n"+"Percentage: "+fundIdRs.getString("req_percent")
										+ "\n"+"Fund Id To / From: "+fundIdRs.getString("to_from_ind")
										+ "\n"+"Fund Deferral Type: "+fundIdRs.getString("sdmt_code"), false);
				}
				if(fundDetailsCount<=0)
					Reporter.logEvent(Status.FAIL, "Rebalance Allocation: Verify Fund Details in TRF_Detl Table",
							"The TRF_Detl table does not display the funds details which are opted for rebalance allocation", false);
		}catch(SQLException e) { e.printStackTrace();}
		catch(Exception e) { e.printStackTrace();}
	}

/**
 * This method updates the effdate to run the core batch
 * @param tfbaId
 */
	
	//Run in loop dor diff tfba_id
	public static void updateEffDateToRunCoreBatch(String tfbaId)
	{
		try {
			String updateEffDateQuery[]= Stock.getTestQuery("updateEffectiveDate");
				int noOfRecordsUpdated=DB.executeUpdate("MemberParticipantDB", updateEffDateQuery[1],tfbaId);
//				if(noOfRecordsUpdated>0)
//					Reporter.logEvent(Status.PASS, "Rebalance Allocation: Update Previous Business Day as Effective Date in TRF_Basic table to run Core Batch for particpant with TFBA_ID "+tfbaId, 
//							"Effective date for the participant with TFBA_ID "+tfbaId+" has been updated in the TRF_Basic table", false);
//				else
//					Reporter.logEvent(Status.FAIL, "Rebalance Allocation: Update Previous Business Day as Effective Date in TRF_Basic table to run Core Batch", 
//							"Effective date for the participant with "+tfbaId+" has not been updated in the TRF_Basic table", false);
			}
		catch(Exception e) { e.printStackTrace(); }
	}

/**
 * This method verifies the trfbasic table with the updated effective date
 * @param expectedStatus
 */
	public static void verifyTrfBasicWithUpdatedEffDate(String expectedStatus)
	{
		multiplePptMap = General.globalMultiPptMap;
		try {
			for (Entry<String, Map<String, String>> entry : multiplePptMap.entrySet())
			{
				String getTrfBasicDetailsQuery[] =Stock.getTestQuery("getTrfBasicDetails");	
				tempAttrMap = entry.getValue();
				
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				String individualId = tempAttrMap.get(GlobalVar.Individualid);
				tempTfbaPropMap = new LinkedHashMap<>();
				tempPlanWiseFundAttrMap = GlobalVar.planWiseFundAllocations.get(individualId);
				for(Entry<String,Map<String,String>> objEntryPlan : tempPlanWiseFundAttrMap.entrySet())
				{
					String planNumber = objEntryPlan.getKey();
				tempFundAttrMap = objEntryPlan.getValue();
				String tfbaId = tempFundAttrMap.get(GlobalVar.tfbaId);
				ResultSet trfBasicUpdEffDatResultSet = DB.executeQuery("MemberParticipantDB", getTrfBasicDetailsQuery[1], tfbaId);
				int updatedTrfBasicCount = DB.getRecordSetCount(trfBasicUpdEffDatResultSet);
				if(updatedTrfBasicCount>0)
				{
					trfBasicUpdEffDatResultSet.first();
					transEffectiveDate= trfBasicUpdEffDatResultSet.getString("effdate");
					updatedEffdateMap= multiplePptMap.get(houseHoldId);
					updatedEffdateMap.put(GlobalVar.effectiveDate,transEffectiveDate);
					updatedEffdateMap.put(GlobalVar.statusCode, trfBasicUpdEffDatResultSet.getString("status_code"));
					tempmultiplePptMap.put(houseHoldId, updatedEffdateMap);
					tempFundAttrMap.put(GlobalVar.effectiveDate, transEffectiveDate);
					
					if(expectedStatus.equalsIgnoreCase(trfBasicUpdEffDatResultSet.getString("status_code")))
						Reporter.logEvent(Status.PASS, "Rebalance Allocation Details: TRF_Basic Table for participant with \n"
								+"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)
								+"Individual Id: "+trfBasicUpdEffDatResultSet.getString("ind_id"),
								"The Participant Details with Rebalance Allocation: \n"	
										+"\n"+"Participant Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)
										+"\n"+"Participant Individual Id: "+trfBasicUpdEffDatResultSet.getString("ind_id")						
										+"\n"+"TFBA_ID: "+tfbaId
										+"\n"+"Effective date: "+ trfBasicUpdEffDatResultSet.getString("effdate")
										+"\n"+"Status_Code: "+trfBasicUpdEffDatResultSet.getString("status_code"), false);

					else
						Reporter.logEvent(Status.FAIL, "Rebalance Allocation Details: TRF_Basic Table for participant with \n"
								+"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)
								+"Individual Id: "+trfBasicUpdEffDatResultSet.getString("ind_id"),
								"The Participant Details with Rebalance Allocation: \n"	
										+"\n"+"Participant Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)
										+"\n"+"Participant Individual Id: "+trfBasicUpdEffDatResultSet.getString("ind_id")						
										+"\n"+"TFBA_ID: "+tfbaId
										+"\n"+"Effective date: "+trfBasicUpdEffDatResultSet.getString("effdate")
										+"\n"+"Status_Code: "+trfBasicUpdEffDatResultSet.getString("status_code"), false);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Rebalance Allocation Details: TRF_Basic Table",
							"The TRF Basic table is not updated  with " +expectedStatus+" status for rebalance allocation", false);
				}
				tempTfbaPropMap.put(planNumber, tempFundAttrMap);
			}
				tempPlanWiseFundAllocations.put(individualId, tempTfbaPropMap);	
			}
			GlobalVar.planWiseFundAllocations = tempPlanWiseFundAllocations;
			General.globalMultiPptMap = tempmultiplePptMap;
		}catch (SQLException e) { e.printStackTrace();}
		catch (Exception e) { e.printStackTrace();}

	}
	
	/**
	 * This method verifies the details in the transfer do lists table
	 * @param tfbaId
	 * @return
	 */
	public static boolean verify_Transfer_Do_Lists_Table(String tfbaId)
	{
		boolean verifyIfRowIsPopulated = false;
		try {
		String getTransferDoListsQuery[]=Stock.getTestQuery("getTransferDoLists");
		ResultSet transferDoListsRs = DB.executeQuery("MemberParticipantDB", getTransferDoListsQuery[1],tfbaId);		
		if(DB.getRecordSetCount(transferDoListsRs) >0)
			verifyIfRowIsPopulated=true;
		} 	catch (Exception e) { e.printStackTrace();}
	return verifyIfRowIsPopulated;	
	}

}
