package pageobjects.member_file;

//import java.sql.ResultSet;
//import java.sql.SQLException;//
//import lib.DB;
//import lib.Stock;

public class Member_Database_Validation {

//	public static int memberHouseHoldID=0;
//	public static int memberIndividualID=0;
//	public static String memberGA_ID=null;	
//	public static String memberdBInstance=null;
//	
//	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
//   	FUNCTION:			getParticipantsDetailsFromMemberHouseHoldId()
//    DESCRIPTION:	    This method returns the resultset of the participants details like individual id from Database
//    PARAMETERS: 		None
//    RETURNS:		    ResultSet
//    REVISION HISTORY: 
//    ------------------------------------------------------------------------------------------------------------------------------------------------------------
//    Author : Janani     Date : 03-12-2015       
//    ------------------------------------------------------------------------------------------------------------------------------------------------------------
//*/ public static ResultSet  getParticipantsDetailsFromMemberHouseHoldId()
//	 {	
//		 ResultSet memberDetailsRs = null;
//	 try {
//		String[] getMemberDetailsQuery =Stock.getTestQuery("getMemberDetails");
//		String dbName = "AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV"));			
//		memberDetailsRs = DB.executeQuery(dbName, getMemberDetailsQuery[0],Member_XML_File_Validation.getMemberHouseHoldID());			
//		DB.getRecordSetCount(memberDetailsRs);	
//		while(memberDetailsRs.next())
//		{	
//			memberHouseHoldID=memberDetailsRs.getInt("managed_user_id");
//			memberIndividualID=memberDetailsRs.getInt("ind_id");
//			memberGA_ID=memberDetailsRs.getString("ga_id");
//			memberdBInstance=memberDetailsRs.getString("database_instance");					
//		}		
//			} catch (SQLException e) { e.printStackTrace(); }
//			 catch (Exception e) { e.printStackTrace(); }
//			return memberDetailsRs;
//	 }
}
