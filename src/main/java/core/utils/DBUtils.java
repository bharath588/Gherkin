package core.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;

public class DBUtils {

	public static List<ResultSet> masterRecordSet = new ArrayList<ResultSet>();
	public static HashMap<String, Connection> dbConnections = new HashMap<String, Connection>();
	
	public static ResultSet executeQuery(String dbName,String query,String... queryParameterValues){
		
		ResultSet tempRecordSet = null;
		PreparedStatement stmt;

		
		try {
					

			stmt = DBUtils.getPreparedStatement(dbName, query, queryParameterValues);
			
			tempRecordSet = stmt.executeQuery();
			masterRecordSet.add(tempRecordSet);

		} catch (SQLException e) {
			throw new Error("Execution of the Query \n" + query + "\nFailed with message \n" + e.getMessage());
		}
		return tempRecordSet;
	}
	

	
	public static int executeUpdate(String dbName,String query,String... queryParameterValues){
		
		int recordsUpdated = 0;
		PreparedStatement stmt;

		try {
		
			stmt = DBUtils.getPreparedStatement(dbName, query, queryParameterValues);
			recordsUpdated = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Error("Execution of the Query \n" + query + "\nFailed with message \n" + e.getMessage());
		}
		return recordsUpdated;
	}
	
	private static PreparedStatement getPreparedStatement(String dbName,String query,String[] queryParameterValues) {
		PreparedStatement stmt = null;
		int queryParamCnt = 0;
		String dataBaseName = Stock.globalParam.get(dbName.trim());
		
		Connection conn = getDBConnection(dataBaseName.trim());
		
		try {

			if(!conn.isClosed()){
				dbConnections.put(dataBaseName,conn);
				
				for (int i = 0; i < query.length(); i++) {
					if ("?".codePointAt(0) == query.codePointAt(i)) {
						queryParamCnt++;
					}
				}
			
				//queryParamCnt = query.replaceAll("= ? ", "=? ").split("=? ").length;
				
				stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if (queryParamCnt == queryParameterValues.length) {
					for (int i = 1; i <= queryParameterValues.length; i++) {
						stmt.setString(i,queryParameterValues[i-1]);
					
					}
					
				} else {
					throw new Error("Parameters in the query and the values sent missmatched");
				}
			}
		} catch (SQLException e) {
			throw new Error("SQL Exception occurred while preparing query:\n" + query + "\n\nFailed with message:\n" + e.getMessage());
		}
		
		return stmt;
	}
	
	
	private static Connection getDBConnection(String DBName){
		
		OracleDataSource dataSource;
		Connection conn;
		
		
		String jdbcUrl = null;
		String dbuserid = null;
		String dbpassword = null;
		//String actualDBName = ReadProperties.getEnvVariableValue(DBName.trim().toUpperCase()).trim().toUpperCase();
		
		if (dbConnections.containsKey(DBName)) {
			return dbConnections.get(DBName);
		} else {
	
			
			try {
				dataSource = new OracleDataSource();
				jdbcUrl = Stock.globalParam.get(DBName);
				dbuserid = Stock.globalParam.get("DBUSERID");
				dbpassword = Stock.globalParam.get("DBPASSWORD");
				
				if (jdbcUrl.trim().length() == 0) {
					throw new Error("No connection string found for the DB: " + DBName);
				}
				
				dataSource.setURL(jdbcUrl);
				
				conn = dataSource.getConnection(dbuserid,dbpassword);
				
			} catch (SQLException e) {
				throw new Error("Establishing connection to DB " + DBName + " Failed \n " + e.getMessage());
			}
			
			return conn;
		}
		
		
	}
	
	public static int getRecordSetCount(ResultSet resultSet){
		
		int rSize = 0;
		
		try {
			resultSet.last();
			rSize = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (SQLException e) {
			//System.out.println("RecordSet is empty");
			throw new Error("SQL Exception: " + e.getMessage());
		}
		
		return rSize;
		
	}
	

	public static void closeDBConnections(){
		
		Iterator<String> connectionsIterator = dbConnections.keySet().iterator();
		Iterator<ResultSet> recordSetsIterator = masterRecordSet.iterator();
		
		while (recordSetsIterator.hasNext()) {
			
			ResultSet currRecordSet = (ResultSet) recordSetsIterator.next();
			
			try {
				if (!currRecordSet.isClosed()) {
					currRecordSet.close();
					
				}
			} catch (SQLException e) {
			   throw new Error("list<Connections> :- a list containing db connections did not close properly");
			}
			
			
		}
		recordSetsIterator.remove();
		
		
		while (connectionsIterator.hasNext()) {
			String connectionName = (String) connectionsIterator.next();
			
			try {
				if (!dbConnections.get(connectionName).isClosed()) {
						dbConnections.get(connectionName).close();
									
				}
			} catch (SQLException e) {
				
				System.out.println("reporter.report - clean connections for DB"); 
			} 
			
			dbConnections.remove(connectionName);	
		}
		
	}
	
}


