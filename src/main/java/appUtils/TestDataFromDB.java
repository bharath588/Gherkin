package appUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lib.DB;
import lib.Stock;

public class TestDataFromDB {
	private static HashMap<String, String> tempMap = null;

	@SuppressWarnings("null")
	public static synchronized HashMap<String, String> getParticipantDetails(
			String queryName, String... queryParameterValues)
			throws SQLException {

		String[] sqlQuery = null;
		LinkedHashMap<String, String> mapUserDetails = new LinkedHashMap<String, String>();
		LinkedHashMap<Long, LinkedHashMap<String, String>> testdataFromDB = new LinkedHashMap<Long, LinkedHashMap<String, String>>();
		ResultSetMetaData rsMetaData = null;
		int noOfColumns = 0;
		int noOfRows = 0;
		sqlQuery = Stock.getTestQuery(queryName);
		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				queryParameterValues);
		if (DB.getRecordSetCount(participants) > 0) {
			noOfRows = DB.getRecordSetCount(participants);
			participants.absolute(noOfRows-2);
			rsMetaData = participants.getMetaData();
			noOfColumns = rsMetaData.getColumnCount();
			System.out.println("no of rows : "+noOfRows+" No of columns : "+noOfColumns);
			
			for(int i=1;i<=noOfRows;i++){
			rsMetaData = participants.getMetaData();
			noOfColumns = rsMetaData.getColumnCount();
			for (int j = 1; j <= noOfColumns; j++) {
				
							
				if (rsMetaData.getColumnName(j).contains("SUBSTR")) {
					if(!checkValueExistsniMap(testdataFromDB, participants.getString(rsMetaData
									.getColumnName(j))))
					mapUserDetails
							.put("PASSWORD", participants.getString(rsMetaData
									.getColumnName(j)));
				} else if (rsMetaData.getColumnName(j).equalsIgnoreCase(
						"BIRTH_DATE")) {
					
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dateInString = participants.getString(rsMetaData
							.getColumnName(j));

					try {

						Date date = formatter.parse(dateInString);
						SimpleDateFormat formatter1 = new SimpleDateFormat(
								"MMddyyyy");
						if(!checkValueExistsniMap(testdataFromDB, participants.getString(rsMetaData
								.getColumnName(j))))
						mapUserDetails.put(rsMetaData.getColumnName(j),
								formatter1.format(date));

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if(rsMetaData.getColumnName(j).contains("FIRST_LINE_MAILING")){
					String address = participants.getString(rsMetaData
							.getColumnName(j));
					if(!checkValueExistsniMap(testdataFromDB, participants.getString(rsMetaData
							.getColumnName(j))))
					mapUserDetails.put(rsMetaData.getColumnName(j),
							address.split(" ")[0]);
				}
				else {
					if(!checkValueExistsniMap(testdataFromDB, participants.getString(rsMetaData
							.getColumnName(j))))
					mapUserDetails
							.put(rsMetaData.getColumnName(j), participants
									.getString(rsMetaData.getColumnName(j)));
				}
			}
			
			
			testdataFromDB.put(Thread.currentThread().getId(),mapUserDetails);
			/*if(fetchNoOfPlans(mapUserDetails.get("SSN")) != 1 || mapUserDetails.get("SSN").equalsIgnoreCase("000231671"))
				participants.next();
			else
				break;*/
			}

		}
		System.out.println("TEST DATA FROM DB:"+mapUserDetails);

		return testdataFromDB.get(Thread.currentThread().getId());
	}

	public static synchronized boolean checkValueExistsniMap(Map<Long,LinkedHashMap<String,String>> dataMap,String value)
	{
		boolean isExist = false;
		if(dataMap.isEmpty() || dataMap.size() == 0)
		{
			return false;
		}
		
		for(Map.Entry<Long, LinkedHashMap<String,String>> refMap : dataMap.entrySet())
		{
			if(refMap.getValue().containsValue(value))
				isExist = true;
		}
	return isExist;	
	}
	
	public static void addUserDetailsToGlobalMap(Map<String, String> paramMap) {

		tempMap = (HashMap<String, String>) Stock.globalTestdata.get(Thread.currentThread().getId());
		// System.out.println("TEST DATA FROM EXCEL"+tempMap);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue());

		}

		Stock.globalTestdata.put(Thread.currentThread().getId(),tempMap);
		System.out.println("TEST DATA FROM BOTH" + tempMap);

	}

	public static void updateTable(String queryName,
			String... queryParameterValues) throws Exception {
		String[] sqlQuery = null;
		sqlQuery = Stock.getTestQuery(queryName);
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], queryParameterValues);
		DB.executeUpdate(sqlQuery[0], "commit");

	}

	public static void fetchRegisteredParticipant(
			String... queryParameterValues) throws SQLException {
		String[] sqlQuery = null;
		HashMap<String, String> mapUserDetails = new HashMap<String, String>();
		ResultSetMetaData rsMetaData = null;
		int noOfColumns = 0;
		int noOfRows = 0;
		sqlQuery = Stock.getTestQuery("fetchRegisteredParticipant");

		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				queryParameterValues);
		if (DB.getRecordSetCount(participants) > 0) {
			noOfRows = DB.getRecordSetCount(participants);
			participants.first();
			rsMetaData = participants.getMetaData();
			noOfColumns = rsMetaData.getColumnCount();
			System.out.println("no of rows : " + noOfRows + " No of columns : "
					+ noOfColumns);

			for (int i = 1; i <= noOfRows; i++) {
				rsMetaData = participants.getMetaData();

				for (int j = 1; j <= noOfColumns; j++) {
					if (rsMetaData
							.getColumnName(j)
							.equalsIgnoreCase(
									"SUBSTR(DECRYPT(U.ENCRYPTED_PASS_CODE),1,10)--,WB.ACCT_BALANCE")) {
						mapUserDetails.put("Password", participants
								.getString(rsMetaData.getColumnName(j)));
					} else
						mapUserDetails.put(rsMetaData.getColumnName(j),
								participants.getString(rsMetaData
										.getColumnName(j)));
				}
				System.out.println(mapUserDetails);
				if (fetchNoOfPlans(mapUserDetails.get("SSN")) != 1 ||!fetchIndID(mapUserDetails.get("SSN")))
					participants.next();
				else
					break;
			}

		}

	}

	public static int fetchNoOfPlans(String ssn) {
		String[] sqlQuery = null;
		int noOfRecords = 0;

		sqlQuery = Stock.getTestQuery("fetchNoOfPlans");
		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
		noOfRecords = DB.getRecordSetCount(participants);
		return noOfRecords;

	}
	
	public static boolean fetchIndID(String ssn) throws SQLException {
		String[] sqlQuery = null;
		boolean IndID = false;

		sqlQuery = Stock.getTestQuery("fetchIdFromUserNameRegistry");
		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
		if (DB.getRecordSetCount(participants) > 0) 
		{
			System.out.println("ID:"+participants.getString("ID"));
			IndID = true;
		}
		return IndID;

	}
}
