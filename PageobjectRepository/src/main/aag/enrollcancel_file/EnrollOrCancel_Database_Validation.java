package pageobjects.enrollcancel_file;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;






import aag_lib.General;
import aag_lib.GlobalVar;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;
import core.framework.Globals;

public class EnrollOrCancel_Database_Validation {
	static Map<String,String> tempAttrMap=null;
	static Map<String,String> enrollStatusMap= new HashMap<String,String>();
	public static LinkedHashMap<String,Map<String,String>> multiplePptMap= null;
	public static LinkedHashMap<String, Map<String, String>> tempmultiplePptMap= new LinkedHashMap<String,Map<String,String>>();

	/**
	 * <pre> This method retrieves the Individual ID and other details based on  respective participant's Household ID</pre>
	 * @param houseHoldId
	 * @return String array of individual id and db instance
	 */
	
	public static String[] getParticipantsDetailsFromHouseHoldId(String houseHoldId)
	{		  
		String individualID ="";
		String dBInstance = null;
		ResultSet getIndIdRsSet =null;
		try {

			String[] getIndIdQuery =Stock.getTestQuery("getIndividualId");			
			String dbName = "AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV"));			
			getIndIdRsSet = DB.executeQuery(dbName, getIndIdQuery[1],houseHoldId);		
			while(getIndIdRsSet.next())
			{	
				individualID=Integer.toString(getIndIdRsSet.getInt("ind_id"));
				dBInstance=Globals.GC_Database_Prefix+getIndIdRsSet.getString("database_instance").toUpperCase();
			}		
		} 
		catch (SQLException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
		return new String[]{individualID,dBInstance};
	}

	/**
	 *  This method verifies whether the participant is enrolled or cancelled based on the Individual ID and adds to the
	 *  Global Map
	 * @throws Exception
	 */
	
	public static void getEnrollmentStatusMultiplePpt() throws Exception
	{
		List<String> planList = new LinkedList<>();
		boolean isValueExist;
		Generate_EnrollOrCancel_Input_XML_File.initMultiPptMap();
		
		for (Entry<String, Map<String, String>> entry : Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.entrySet())
		{
			
			
			isValueExist = false;
			tempAttrMap = entry.getValue();
			planList.add(tempAttrMap.get(GlobalVar.firstPlanId));
			planList.add(tempAttrMap.get(GlobalVar.secondPlanId));
			Iterator<String> planListIterator = planList.iterator();
			while(planListIterator.hasNext())
			{
				String planNumber =planListIterator.next(); 
				if(planNumber != null)
				{
			String[] enrollCancelQuery =Stock.getTestQuery("participantEnrolledOrCancelled");
			ResultSet enrollcancelRs = DB.executeQuery("MemberParticipantDB", enrollCancelQuery[1], tempAttrMap.get(GlobalVar.Individualid),planNumber);		
			String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
			while(enrollcancelRs.next()){
				String status = enrollcancelRs.getString("status_reason_code");
				if(status.trim().equalsIgnoreCase("Enrolled"))
				{
					isValueExist= true;
					enrollStatusMap = Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.get(houseHoldId);
					enrollStatusMap.put(GlobalVar.enrollmntStatus+" for "+planNumber, status);
				}
				break;
			}
			if(isValueExist)
			{
				Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.put(houseHoldId, enrollStatusMap);
			}
		}
			}
			planList.removeAll(planList);
		}
		General.globalMultiPptMap = Generate_EnrollOrCancel_Input_XML_File.multiplePptMap;
	}

	/**
	 * This method fetches the cancelled participant details from database and logs in the report
	 * 
	 */
	public static void getCancelledParticipants()
	{
		try
		{	  
			String[] cancelQuery =Stock.getTestQuery("getparticipantCancelledQuery");
			multiplePptMap = General.globalMultiPptMap;
			for (Entry<String, Map<String, String>> entry : Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.entrySet())
			{
				tempAttrMap = entry.getValue();
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				ResultSet enrollcancelRs = DB.executeQuery("MemberParticipantDB", cancelQuery[1], tempAttrMap.get(GlobalVar.Individualid));
				int cancelParticipantCount = DB.getRecordSetCount(enrollcancelRs);
				if(enrollcancelRs != null)
				{
					while(enrollcancelRs.next())
					{
						Reporter.logEvent(Status.PASS, "Verify Cancelled Participant Details",
								"The following participants are cancelled: \n"
										+ "Participant Household Id: "+houseHoldId
										+ "\n"+"Participant Individual Id: " +enrollcancelRs.getInt("ind_id")							 
										+ "\n"+"Status: "+enrollcancelRs.getString("status_code")
										+ "\n"+"Status Reason Code: "+enrollcancelRs.getString("status_reason_code")							 
										+ "\n"+"Effective Date: "+enrollcancelRs.getString("EFFDATE"), false);
						break;
					}
					if(cancelParticipantCount <= 0)
					{
						Reporter.logEvent(Status.FAIL, "Part_Service Table: Verify Unenrolled/Cancelled Participant Details",
								"The Unenrolled/Cancelled participant details are not displayed in Part_Service table", false); 
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method updates the status of the participant post cancellation
	 */
	public static void updateStatusPostCancellation()
	{
		multiplePptMap = General.globalMultiPptMap;
		try {
			for (Entry<String, Map<String, String>> entry : multiplePptMap.entrySet())
			{
				tempAttrMap = entry.getValue();
				String[] enrollCancelQuery =Stock.getTestQuery("participantEnrolledOrCancelled");
				ResultSet enrollcancelRs = DB.executeQuery("MemberParticipantDB", enrollCancelQuery[1], tempAttrMap.get(GlobalVar.Individualid));		
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				if(enrollcancelRs != null)
				{
					while(enrollcancelRs.next())
					{
						String status = enrollcancelRs.getString("status_reason_code");
						enrollStatusMap = multiplePptMap.get(houseHoldId);
						enrollStatusMap.put(GlobalVar.enrollmntStatus, status);
						break;
					}
				}
				tempmultiplePptMap.put(houseHoldId, enrollStatusMap);	
			}
			General.globalMultiPptMap = tempmultiplePptMap;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}



}
