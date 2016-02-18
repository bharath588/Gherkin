package appUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Reporter.Status;

public class Common {

	/** <pre> Method to return the no of plans associated to a user from db
	 * 
	 * @return noOfPlans
	 */
	public static ResultSet getParticipantInfoFromDB(String ssn){
		
		//query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],ssn);
		
		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.first();			
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(Status.WARNING, "Query Participant Info from DB:" + sqlQuery[0] , "The Query did not return any results. Please check participant test data as the appropriate data base.", false);
			}
		}		
		return participantInfo;
	}
}
