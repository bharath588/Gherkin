package aag.max;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import webservices.util.JsonReadWriteUtils;
import aag.aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class MAX_WebserviceMethods {

	
	
	private final static String remove = "REMOVE";
	private final static String blank = "BLANK";
	ResultSet queryResultset;
	ResultSet queryResultSet = null;
	String customServId;
	String workIbbotsonStatus;
	private static String termdateOnlineAdvice = "";
	private static String termdateMA = "";
	private static String termdateDOLFidu = "";
	private static String termdateNxtGen = "";
	
	public String getMAXAuthURL()
    {
		return Stock.getConfigParam("postgresAuthURL");
    }
	
	public String getConfirmationNumber(String confirmationNumber)
    {
		StringBuilder sb = new StringBuilder(confirmationNumber.split("E")[0].toString());
		sb.deleteCharAt(1);
		while(sb.length()<10)
			sb.append('0');
		return sb.toString();
    }

	/**<pre>
	 * This method is to verify the Advisory Service Profile Response
	 * </pre>
	 * @param jsonResponseString  - Response string
	 * @param service - Service to be verify
	 * @param expected - Expected value
	 * @return
	 * @throws Exception 
	 */
	
	
	public void verifyAdvServProfResponse(String jsonResponseString, String service, String expected) throws Exception {
		// TODO Auto-generated method stub
				
		String actual = JsonReadWriteUtils.getOneNodeValueUsingJpath(jsonResponseString, "$."+service).trim();
		if(actual.equals(expected))
			Reporter.logEvent(Status.PASS, "Verify the Response", "Getting expected Response:- Service Name : "+service+" ; Value : "+actual, false);
		else
			Reporter.logEvent(Status.FAIL, "Verify the Response", "NOT getting Expected Response:- Service Name : "+service+" ; Value : "+actual, false);
	}
	
	/**<pre>
	 * This method is to verify the Advisory Service Profile Response
	 * </pre>
	 * @param jsonResponseString  - Response string
	 * @param service - Service to be verify
	 * @return Boolean
	 * @throws Exception 
	 */
	public void verifyAdvServProfResponseWithNoValue(String jsonResponseString, String service) throws Exception {
		// TODO Auto-generated method stub
		
		try{
			String actual=JsonReadWriteUtils.getOneNodeValueUsingJpath(jsonResponseString, "$."+service).trim();
			Reporter.logEvent(Status.FAIL, "Verify the element 'fundLineupStatus' in the  response", "FundLineupStatus is there in response generated : "+service+" ; Value : "+actual, false);
		}
		catch(Exception e)
		{
			Reporter.logEvent(Status.PASS, "Verify the element 'fundLineupStatus' in the  response", "FundLineupStatus is not be there in response generated", false);
		}
	}
		
	/**<pre>
	 * It returns the request url
	 * @param baseURL of the request url mostly static part of the url.
	 * @param params other parameters (mandatory or optional) of the request url
	 * @return string url
	 */
	public static String formMAXRequestURL(String baseURL, String...params)
	{
		String requestURL = baseURL;
		try
		{
			if(params.length>0)
			{
				for(String param : params)
				{
					if(!(param.contains(remove)||param.contains(blank)||param.contains("?")))
					{
						if(requestURL.endsWith("/")){
							requestURL = requestURL+param;}
						else if(requestURL.endsWith("&")){
							requestURL=requestURL+param;
						}
						else{
							requestURL = requestURL+"/"+param;
						}
					}
					if(param.contains(blank))
					{
						param = param.replace(blank, "");
						requestURL = requestURL+"/"+param;
					}
					if(param.contains("?"))
					{
						if(requestURL.endsWith("/")){
							requestURL = requestURL.substring(0,requestURL.length()-1).trim();
						}
						requestURL = requestURL+param;
					}
				}
				if(requestURL.endsWith("/")){
					requestURL = requestURL.substring(0,requestURL.length()-1).trim();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return requestURL;
	}

	/**<pre>
	 * This method is to check Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkOnlineAdviceInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForOnlineAdvice")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify Online Advice status", "Online Advice status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Online Advice status", "Online Advice status is NOT "+expectedVal, false);
	}

	public void checkStepTypeInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkStepTypeForAdvice")[1]);
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify  step type status", "step type status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify  step type status", "step type status is NOT "+expectedVal, false);
	}
	
	public void checkTransInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkTransForAdvice")[1]);
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify transaction status", "step type status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify  transaction status", "step type status is NOT "+expectedVal, false);
	}
	
	/**<pre>
	 * This method is to check Managed Accounts in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkMAInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForMA")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify Managed Accounts status", "MA status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Managed Accounts status", "MA status is NOT "+expectedVal, false);
	}

	/**<pre>
	 * This method is to check DOL Fiduciary in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkDOLFiduInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForDOLFidu")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify DOL Fiduciary status", "DOL Fiduciary status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify DOL Fiduciary status", "DOL Fiduciary status is NOT "+expectedVal, false);
	}

	/**<pre>
	 * This method is to check Next Gen Advisory Services in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkNxtGenInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForNxtGen")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify Next Gen Advisory Services status", "Next Gen Advisory Services status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Next Gen Advisory Services status", "Next Gen Advisory Services status is NOT "+expectedVal, false);
	}

	/**<pre>
	 * This method is to check Financial Engine in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkFinancialEngineInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForFinEngine")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify Financial Engine status", "Financial Engine status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Financial Engine status", "Financial Engine status is NOT "+expectedVal, false);
	}

	/**<pre>
	 * This method is to checkGuidance in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void checkGuidanceInDB(boolean expectedVal) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForGuidance")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(queryResultSet.next() == expectedVal)
		{
			Reporter.logEvent(Status.PASS, "Verify Guidance status", "Guidance status : "+expectedVal, false);
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify Guidance status", "Guidance status is NOT "+expectedVal, false);
	}
	
	public boolean checkNextgen (String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("CheckNextgen")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	DB.executeUpdate(Globals.dbNameAlias,
			Stock.getTestQuery("updateStatustoID")[1], indId);*/
	
	public void setServiceData(String gaId,boolean adv,boolean dol,boolean ma)
	{
		
			try {
				
				if(adv)
				{
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("adviceON")[1], gaId);
				}
				else
				{
					DB.executeUpdate(Globals.dbNameAlias,
							Stock.getTestQuery("adviceOFF")[1], gaId);
					}
				
				if(dol)
				{
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("DolON")[1], gaId);
				}
				else
				{
					DB.executeUpdate(Globals.dbNameAlias,
							Stock.getTestQuery("DolOFF")[1], gaId);
					}
				
				
				if(ma)
				{
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("MAON")[1], gaId);
				}
				else
				{
					DB.executeUpdate(Globals.dbNameAlias,
							Stock.getTestQuery("MAOFF")[1], gaId);
					}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public boolean checkAdvice (String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("CheckAdvice")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkMa (String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("CheckMa")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkDol (String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("CheckDol")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) >0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setNextgen(String gaId,boolean nextGen)
	{
		
			try {
				
				if(nextGen)
				{
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("NextgenON")[1], gaId);
				}
				else
				{
					DB.executeUpdate(Globals.dbNameAlias,
							Stock.getTestQuery("NextgenOFF")[1], gaId);
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setDBInstance(String sponsorId) throws SQLException

    {

            String dbInstance = null;

            queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")),

                                   Stock.getTestQuery("queryToGetDBInstance")[1],

                                   sponsorId + "%");

                   if (queryResultSet != null) {

                           while (queryResultSet.next()) {

                                   dbInstance = General.checkEnv(Stock.getConfigParam("TEST_ENV")).substring(0, 1)+"_"

                                                   + queryResultSet.getString("database_instance").toUpperCase();

                                   break;

                           }

                   }

                   Stock.setConfigParam(Globals.dbNameAlias, dbInstance, true);

    }
	
	/**<pre>
     * This method is to update Custom service Id for DOL Fiduciary
     * </pre>
     * @throws Exception 
      */
     public void updateCustomServForDOL(String custServId, String gaId) throws Exception {
            // TODO Auto-generated method stub
            
            queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getfundLineupStatusForDOL")[1], gaId);
            if(queryResultSet.next())
            {
                   customServId=queryResultSet.getString("custom_service_id");
                   DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForDOL")[1], custServId, gaId);
            }
            else
                   Reporter.logEvent(Status.FAIL, "Verify Custom service Id for DOL Fiduciary Record", "Custom service Id for DOL Fiduciary is not present", false);
     }

     /**<pre>
     * This method is to Revert the changes Custom service Id for DOL Fiduciary
     * </pre>
     * @throws Exception 
      */
     public void revertCustomServForDOL(String gaId) throws Exception {
            // TODO Auto-generated method stub
                         
            DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForDOL")[1], customServId, gaId);
     }

 	/**<pre>
 	 * This method is to update Custom service Id for M* Advice
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void updateCustomServForAdvice(String custServId, String gaId) throws Exception {
 		// TODO Auto-generated method stub
 		
 		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getfundLineupStatusForAdvice")[1], gaId);
 		if(queryResultSet.next())
 		{
 			customServId=queryResultSet.getString("custom_service_id");
 			DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForAdvice")[1], custServId, gaId);
 		}
 		else
 			Reporter.logEvent(Status.FAIL, "Verify Custom service Id for M* Advice Record", "Custom service Id for M* Advice is not present", false);
 	}

 	/**<pre>
 	 * This method is to Revert the changes Custom service Id for DOL Fiduciary
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void revertCustomServForAdvice(String gaId) throws Exception {
 		// TODO Auto-generated method stub
 				
 		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForAdvice")[1], customServId, gaId);
 	}

 	/**<pre>
 	 * This method is to update Custom service Id for M* Advice
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void updateCustomServForGuidance(String custServId, String gaId) throws Exception {
 		// TODO Auto-generated method stub
 		
 		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getfundLineupStatusForGuidance")[1], gaId);
 		if(queryResultSet.next())
 		{
 			customServId=queryResultSet.getString("custom_service_id");
 			DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForGuidance")[1], custServId, gaId);
 		}
 		else
 			Reporter.logEvent(Status.FAIL, "Verify Custom service Id for M* Advice Record", "Custom service Id for M* Advice is not present", false);
 	}

 	/**<pre>
 	 * This method is to Revert the changes Custom service Id for DOL Fiduciary
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void revertCustomServForGuidance(String gaId) throws Exception {
 		// TODO Auto-generated method stub
 				
 		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatefundLineupStatusForGuidance")[1], customServId, gaId);
 	}

 	/**<pre>
 	 * This method is to update Custom service Id status
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void updateWorkIbbotsonLineupStatus(String custServId, String status) throws Exception {
 		// TODO Auto-generated method stub
 		
 		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getWorkIbbotsonLineupStatus")[1], custServId);
 		if(queryResultSet.next())
 		{
 			workIbbotsonStatus = queryResultSet.getString("status");
 			DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updateWorkIbbotsonLineupStatus")[1], status, custServId);
 		}
 		else
 			Reporter.logEvent(Status.FAIL, "Verify Custom service Id Record", "Custom service Id is not present", false);
 	}

 	/**<pre>
 	 * This method is to Revert the changes Custom service Id status
 	 * </pre>
 	 * @throws Exception 
 	 */
 	public void revertWorkIbbotsonLineupStatus(String custServId) throws Exception {
 		// TODO Auto-generated method stub
 				
 		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updateWorkIbbotsonLineupStatus")[1], workIbbotsonStatus, custServId);
 	}

 	 public String getCustomServForDOL(String gaId) throws Exception {
         // TODO Auto-generated method stub
         
         queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getfundLineupStatusForDOL")[1], gaId);
         if(queryResultSet.next())
         {
                customServId=queryResultSet.getString("custom_service_id");
         }
		return customServId;

 	 }
 	 
 	public String getCustomServForAdvice(String gaId) throws Exception {
        // TODO Auto-generated method stub
        
        queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getfundLineupStatusForAdvice")[1], gaId);
        if(queryResultSet.next())
        {
               customServId=queryResultSet.getString("custom_service_id");
        }
		return customServId;

	 }

	/**<pre>
	 * This method is to update Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void updateOnlineAdviceInDB(String gaId, String dbName, boolean termdateVal) throws Exception {
		// TODO Auto-generated method stub
				
		if(termdateVal)
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForOnlineAdvice")[1], gaId);
			if(queryResultSet.next())
			{
				termdateOnlineAdvice=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForOnlineAdviceNull")[1], gaId);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Next Gen Advisory Record", "Next Gen Advisory Record is not present", false);
		}
		else
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForOnlineAdvice")[1], gaId);
			if(queryResultSet.next())
			{
				termdateOnlineAdvice=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForOnlineAdvice")[1], gaId);
			}
		}
	}

	/**<pre>
	 * This method is to Revert the changes Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void revertOnlineAdviceInDB(String gaId, String dbName) throws Exception {
		// TODO Auto-generated method stub
				
		DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForOnlineAdviceVal")[1], termdateOnlineAdvice, gaId);
	}

	/**<pre>
	 * This method is to update Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void updateMAInDB(String gaId, String dbName, boolean termdateVal) throws Exception {
		// TODO Auto-generated method stub
				
		if(termdateVal)
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForMA")[1], gaId);
			if(queryResultSet.next())
			{
				termdateMA=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForMANull")[1], gaId);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Next Gen Advisory Record", "Next Gen Advisory Record is not present", false);
		}
		else
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForMA")[1], gaId);
			if(queryResultSet.next())
			{
				termdateMA=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForMA")[1], gaId);
			}
		}
	}

	/**<pre>
	 * This method is to Revert the changes Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void revertMAInDB(String gaId, String dbName) throws Exception {
		// TODO Auto-generated method stub
				
		DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForMAVal")[1], termdateMA, gaId);
	}

	/**<pre>
	 * This method is to update Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void updateDOLFiduInDB(String gaId, String dbName, boolean termdateVal) throws Exception {
		// TODO Auto-generated method stub
				
		if(termdateVal)
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForDOLFidu")[1], gaId);
			if(queryResultSet.next())
			{
				termdateDOLFidu=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForDOLFiduNull")[1], gaId);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Next Gen Advisory Record", "Next Gen Advisory Record is not present", false);
		}
		else
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForDOLFidu")[1], gaId);
			if(queryResultSet.next())
			{
				termdateDOLFidu=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForDOLFidu")[1], gaId);
			}
		}
	}

	/**<pre>
	 * This method is to Revert the changes Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void revertDOLFiduInDB(String gaId, String dbName) throws Exception {
		// TODO Auto-generated method stub
				
		DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForDOLFiduVal")[1], termdateDOLFidu, gaId);
	}

	/**<pre>
	 * This method is to update Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void updateNxtGenInDB(String gaId, String dbName, boolean termdateVal) throws Exception {
		// TODO Auto-generated method stub
		
		if(termdateVal)
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForNxtGen")[1], gaId);
			if(queryResultSet.next())
			{
				termdateNxtGen=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForNxtGenNull")[1], gaId);
			}
			else
				Reporter.logEvent(Status.FAIL, "Verify Next Gen Advisory Record", "Next Gen Advisory Record is not present", false);
		}
		else
		{
			queryResultSet= DB.executeQuery(dbName, Stock.getTestQuery("getTermdateForNxtGen")[1], gaId);
			if(queryResultSet.next())
			{
				termdateNxtGen=queryResultSet.getString("termdate");
				DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForNxtGen")[1], gaId);
			}
		}
	}

	/**<pre>
	 * This method is to Revert the changes Online Advice in DB
	 * </pre>
	 * @throws Exception 
	 */
	public void revertNxtGenInDB(String gaId, String dbName) throws Exception {
		// TODO Auto-generated method stub
				
		DB.executeUpdate(dbName, Stock.getTestQuery("setTermdateForNxtGenVal")[1], termdateNxtGen, gaId);
	}

	/**<pre>
	 * This method is to get Custom service Id for MA
	 * Returns the Custom service ID
	 * </pre>
	 * @throws Exception 
	 */
	public String getCustomServiceIdforMA() throws Exception {
		// TODO Auto-generated method stub
		
		String customServId=null;
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getCustomServIdForMA")[1], Stock.GetParameterValue("Sponsor_Id"));
		if(DB.getRecordSetCount(queryResultSet)>=3 && queryResultSet.next())
		{
			customServId=queryResultSet.getString("custom_service_id");
			while(queryResultSet.next())
			{
				if(!customServId.equals(queryResultSet.getString("custom_service_id")))
					Reporter.logEvent(Status.FAIL, "Verify the custom_service_id", "custom_service_id is not same in all the records", false);
			}
		}
		else
			Reporter.logEvent(Status.FAIL, "Verify the record count", "Not getting expected records. Expected count >=3", false);	
		
		return customServId;
	}

	/**<pre>
	 * This method is to check Online Advice record for the ppt
	 * returns the boolean value
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOnlineAdviceForPpt(String ind_id) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForOnlineAdviceForPpt")[1], Stock.GetParameterValue("Sponsor_Id"),ind_id);
		if(queryResultSet.next())
			return true;
		else
			return false;			
	}

	/**<pre>
	 * This method is to check MA record for the ppt
	 * returns the boolean value
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkMAForPpt(String ind_id) throws Exception {
		// TODO Auto-generated method stub
				
		queryResultSet= DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("checkForMAForPpt")[1], Stock.GetParameterValue("Sponsor_Id"),ind_id);
		if(queryResultSet.next())
			return true;
		else
			return false;			
	}
}
