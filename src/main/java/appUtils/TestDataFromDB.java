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
import lib.Reporter;
import lib.Stock;
import lib.Reporter.Status;

public class TestDataFromDB {
	private static Map<String, String> tempMap = null;
	
	@SuppressWarnings("null")
	public static HashMap<String, String> getParticipantDetails(
			String queryName, String... queryParameterValues)
			throws SQLException {

		String[] sqlQuery = null;
		HashMap<String, String> mapUsername = new HashMap<String, String>();
		LinkedHashMap<Integer, Map<String, String>> testdataFromDB = new LinkedHashMap<Integer, Map<String, String>>();
		ResultSetMetaData rsMetaData = null;
		int noOfColumns = 0;

		sqlQuery = Stock.getTestQuery(queryName);

		ResultSet participants = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				queryParameterValues);
		if (DB.getRecordSetCount(participants) > 0) {
			participants.last();
			rsMetaData = participants.getMetaData();
			noOfColumns = rsMetaData.getColumnCount();
			for (int i = 1; i <= noOfColumns; i++) {
				if (rsMetaData.getColumnName(i).contains("SUBSTR")) {
					mapUsername
							.put("PASSWORD", participants.getString(rsMetaData
									.getColumnName(i)));
				} else if(rsMetaData.getColumnName(i).equalsIgnoreCase("BIRTH_DATE")) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
					String dateInString =  participants.getString(rsMetaData
							.getColumnName(i));

					try {

						Date date = formatter.parse(dateInString);
						SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
						mapUsername
						.put(rsMetaData.getColumnName(i), formatter1.format(date));

					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				else
				{
					mapUsername
							.put(rsMetaData.getColumnName(i), participants
									.getString(rsMetaData.getColumnName(i)));
				}

			}
			
			
		}
		///System.out.println(mapUsername);

		return mapUsername;
	}

	public static void addUserDetailsToGlobalMap(Map<String, String> paramMap) {

		tempMap = Stock.globalTestdata;
		//System.out.println("TEST DATA FROM EXCEL"+tempMap);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue());

		}
		
		Stock.globalTestdata = tempMap;
		System.out.println("TEST DATA FROM BOTH"+tempMap);

	}

}
