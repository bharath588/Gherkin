package pageobjects.prospect_file;

import java.sql.ResultSet;
import java.sql.SQLException;

import core.framework.Globals;
import aag_lib.General;
import lib.DB;
import lib.Stock;

public class Prospect_Database_Validation {

	public static boolean selectSponsorforRunningProspectJob(boolean... updateStatusToNew)
    {
           boolean rowUpdated=false;
           int noOfSponsorsWithNewStatus=0;
           try {
                  String selectSponsorWithNewStatus[]=Stock.getTestQuery("selectSponsorWithNewStatus");                     
                  ResultSet newStatusSponsorRs =DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), selectSponsorWithNewStatus[1], "NEW");
                  DB.getRecordSetCount(newStatusSponsorRs);
                  while(newStatusSponsorRs.next())
                  {
                  noOfSponsorsWithNewStatus=newStatusSponsorRs.getInt("count");  
                  }
                  System.out.println("New Status Rows "+noOfSponsorsWithNewStatus);
                  if(noOfSponsorsWithNewStatus>0)
                  {
                        String updateSponsorStatusToCompleteQuery[]=Stock.getTestQuery("updateSponsorStatusToComplete");
                        DB.executeUpdate("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), updateSponsorStatusToCompleteQuery[1],"NEW");
                  }
                  if(!(updateStatusToNew.length > 0))
                  {
                  String selectSponsorQuery[]= Stock.getTestQuery("selectSponsor");
                  int noOfRecordsUpdated=DB.executeUpdate("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), selectSponsorQuery[1], 
                		  Stock.getConfigParam(Globals.GC_COLNAME_USERID).toUpperCase(),"COMPLETE", Stock.GetParameterValue("plan_SponsorId"));
                  if(noOfRecordsUpdated >0)
                        rowUpdated=true;
                  }
           }
           catch(SQLException e) { e.printStackTrace(); }  
           catch(Exception e) { e.printStackTrace(); }
           return rowUpdated;
    }
}
