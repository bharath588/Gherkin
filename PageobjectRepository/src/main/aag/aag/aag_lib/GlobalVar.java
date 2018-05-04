package aag.aag_lib;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalVar {

	public  static final String houseHoldId = "HouseholdId";
	public  static final String Individualid = "IndividualId";
	public  static final String sponsorId = "SponsorId";
	public  static final String planId = "PlanId";

	public  static final String firstPlanId = "PlanIdOne";
	public  static final String secondPlanId = "PlanIdTwo";
	public  static final String enrollmntStatus = "enrollmentStatus";
	public  static final String dbInstance = "dbInstance";
	public static final String transactionId = "transactionId";
	public static final String rebalBuyFundId = "rebalanceBuyFundId";
	public static final String rebalSellFundId = "rebalanceSellFundId";
	public static final String futureFundallocationOne = "futureFundallocationOne";
	public static final String futureFundallocationTwo = "futureFundallocationTwo";
	public static final String tfbaId = "tfbaId";
	public static final String effectiveDate = "effectiveDate";
	public static final String statusCode = "reBalancestatusCode"; 
	public static final String typeOfFlow = "FLOWTYPE"; 

	public static String sponsorIdExisting = null;
	public static boolean isProspectFileCreated = false;
	
	//Test Cases
	public static final String participantDB ="MemberParticipantDB";
	
	
	//Prospect XML Tags
	public static final String Prospect_batchData ="fei_batch:Data";
	public static Map<String,Map<String,Map<String,String>>> planWiseFundAllocations = new LinkedHashMap<>();
}
