package appUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import core.framework.Globals;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Reporter.Status;

public class Common {

	/** <pre> Method to return the no of plans associated to a user from db
	 * 
	 * @return noOfPlans
	 */
	 //For Sponsor
    public static final String GC_DEFAULT_SPONSER="Empower";
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
				participantInfo.last();			
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(Status.WARNING, "Query Participant Info from DB:" + sqlQuery[0] , "The Query did not return any results. Please check participant test data as the appropriate data base.", false);
			}
		}		
		return participantInfo;
	}
	
	public static ResultSet getParticipantInfoFromDataBase(String ssn)
			throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB Name is"+getParticipantDBName(ssn)+"DB");
		sqlQuery[0] = getParticipantDBName(ssn)+"DB";
		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				ssn);

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static String getParticipantDBName(String ssn) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;

		ResultSet participantDB = null;

		try {
			sqlQuery = Stock.getTestQuery("getParticipantDB");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				getParticipantID(ssn));
		if (DB.getRecordSetCount(participantDB) > 0) {
			try {
				participantDB.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
System.out.println("DATA BASE Name"+participantDB.getString("database_instance"));
		return participantDB.getString("database_instance");
	}

	public static String getParticipantID(String ssn) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet participantID = null;

		try {
			sqlQuery = Stock.getTestQuery("getParticipantID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

		if (DB.getRecordSetCount(participantID) > 0) {
			try {
				participantID.first();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		} else {
			try {
				sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDB");
			} catch (Exception e) {
				e.printStackTrace();
			}

			participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

			if (DB.getRecordSetCount(participantID) > 0) {
				try {
					participantID.last();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			}

		}
		System.out.println("ID is "+participantID.getString("ID"));
		return participantID.getString("ID");
	}

	public static boolean isCurrentSponser(String sponserName) {
		boolean status = false;

		if (lib.Stock.globalTestdata.containsKey("ACCUCODE")
				&& lib.Stock.GetParameterValue("AccuCode") != null) {
			if (sponserName
					.equalsIgnoreCase("Southwest Airlines Retirement Plans")) {
				sponserName = "SWA";
			}
			if (sponserName.equalsIgnoreCase("Empower Retirement")) {
				sponserName = "Apple";
			}
			if (lib.Stock.GetParameterValue("AccuCode").equalsIgnoreCase(
					sponserName)) {
				status = true;
			} else {
				status = false;
			}
		}

		else {
			if (GC_DEFAULT_SPONSER.equalsIgnoreCase(sponserName))
				status = true;
			else
				status = false;
		}
		return status;
	}

	public static String getSponser() {

		String sponser = null;
		if (lib.Stock.globalTestdata.containsKey("ACCUCODE")) {
			if (lib.Stock.GetParameterValue("AccuCode") != null) {
				sponser = lib.Stock.GetParameterValue("AccuCode");
			} else {
				sponser = GC_DEFAULT_SPONSER;

			}
		} else {
			sponser = GC_DEFAULT_SPONSER;

		}
		return sponser;
	}
	
	public static String getUserNameFromDB() {

		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = null;
		try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn.substring(0, ssn.length()-3));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return userFromDatasheet;
	}
}
