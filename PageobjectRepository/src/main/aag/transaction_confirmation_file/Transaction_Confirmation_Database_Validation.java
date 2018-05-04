package pageobjects.transaction_confirmation_file;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import aag_lib.General;
import aag_lib.GlobalVar;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import com.aventstack.extentreports.*;



public class Transaction_Confirmation_Database_Validation {

	public static Map<String,String> tempAttrMap;
	public static LinkedHashMap<String, Map<String, String>> tempmultiplePptMap= new LinkedHashMap<String,Map<String,String>>();
	public static LinkedHashMap<String, Map<String, String>> multiplePptMap= new LinkedHashMap<String,Map<String,String>>();

	
	/**
	 * This method validates the transcation confirmation status in database before and after running transaction confirmation job
	 * @param transactionConfirmationStatus
	 * @param jobStatus
	 */
	
	public static boolean transactionConfirmationValidation(String transactionConfirmationStatus, String jobStatus)
	{
		ResultSet getTransactionConfirmationRs =null;
		int transactionConfirmationCount =0;
		boolean status = true;
		multiplePptMap = General.globalMultiPptMap;
		String[] getTransactionConfirmationQuery =Stock.getTestQuery("getTransactionConfirmationDetails");
		try {	
			for (Entry<String, Map<String, String>> entry : multiplePptMap.entrySet())
			{
				tempAttrMap = entry.getValue();
				String transactionId = tempAttrMap.get(GlobalVar.transactionId);
				String houseHoldId = tempAttrMap.get(GlobalVar.houseHoldId);
				if(tempAttrMap.get(GlobalVar.statusCode).equalsIgnoreCase("COMPLETE"))
				{
					getTransactionConfirmationRs = DB.executeQuery
							("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), getTransactionConfirmationQuery[1],
									transactionId,houseHoldId);		
					transactionConfirmationCount=DB.getRecordSetCount(getTransactionConfirmationRs);
				}
				if(transactionConfirmationCount > 0)
				{			
					while(getTransactionConfirmationRs.next())
					{
						if(transactionConfirmationStatus.equalsIgnoreCase(getTransactionConfirmationRs.getString("status_code")))
							Reporter.logEvent(Status.PASS, "Verify Database "+jobStatus.toUpperCase()+ " Running Transaction Confirmation Job for participant \n"+
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"The following are the Transaction Confirmation Details from Database "+jobStatus.toUpperCase() +" running Job \n"
											+ "\n"+"Participant HouseHold Id: "+getTransactionConfirmationRs.getString("managed_user_id")
											+ "\n"+"Participant Individual Id: "+tempAttrMap.get(GlobalVar.Individualid)				
											+ "\n"+"Sponsor Id: " +getTransactionConfirmationRs.getString("gc_id")
											+ "\n"+"Transaction ID (External ID): "+getTransactionConfirmationRs.getString("external_id")							
											+ "\n"+"Status: "+getTransactionConfirmationRs.getString("status_code") 
											+ "\n"+"Transaction Confirmation Date: "+getTransactionConfirmationRs.getString("confirmation_date"),false);
						else{
							Reporter.logEvent(Status.FAIL, "Verify Database "+jobStatus.toUpperCase()+ " Running Transaction Confirmation Job for participant \n"+
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"The following are the Transaction Confirmation Details from Database "+jobStatus.toUpperCase() +" running Job \n"
											+ "\n"+"Participant HouseHold Id: "+getTransactionConfirmationRs.getString("managed_user_id")
											+ "\n"+"Participant Individual Id: "+tempAttrMap.get(GlobalVar.Individualid)				
											+ "\n"+"Sponsor Id: " +getTransactionConfirmationRs.getString("gc_id")
											+ "\n"+"Transaction ID (External ID): "+getTransactionConfirmationRs.getString("external_id")							
											+ "\n"+"Status: "+getTransactionConfirmationRs.getString("status_code") 
											+ "\n"+"Transaction Confirmation Date: "+getTransactionConfirmationRs.getString("confirmation_date"),false);
							status=false;
						}
					}
				}

				else{
					Reporter.logEvent(Status.FAIL, "Verify Transaction Confirmation Details from Database",
							"Transaction Confirmation details are NOT available for the below participant in the database \n"+
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),false);	
					status=false;
				}
			}
		}catch(SQLException e) { e.printStackTrace();}
		catch(Exception e) { e.printStackTrace();}
		return status;
	}
}
