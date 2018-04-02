package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import oracle.jdbc.pool.OracleDataSource;
//import com.sun.rowset.FilteredRowSetImpl;
import core.framework.Globals;
/**
 *  DB - This class contains methods to connect to data base and execute querys
 * @author srsksr
 *
 */
public class DB {

	public static List<ResultSet> masterRecordSet = new ArrayList<ResultSet>();
	public static ConcurrentHashMap<String, Connection> dbConnections = new ConcurrentHashMap<String, Connection>();
	//private static FilteredRowSetImpl filterObj;
	static int count = 0;
	private static FilteredRowSet filterObj;
	 static RowSetFactory rsf;
	
	/**
	 *<pre>
	 * Method to execute the query and return ResultSet
	 * </pre>
	 * @param dbName
	 * @param query
	 * @param queryParameterValues
	 * @return <b>ResultSet-tempRecordSet </b>
	 */
	public static ResultSet executeQuery(String dbName,String query,String... queryParameterValues) {
		
		ResultSet tempRecordSet = null;
		PreparedStatement stmt;

		try {				
			stmt = DB.getPreparedStatement(dbName, query, queryParameterValues);
			tempRecordSet = stmt.executeQuery();
			masterRecordSet.add(tempRecordSet);

		} catch (SQLException e) {
			throw new Error ("Execution of the Query \n" + query + "\nFailed with message \n" + e.getMessage());
		}
		return tempRecordSet;
	}
	

	/**
	 *<pre>
	 * Method to update the records in Data Base
	 * </pre>
	 * @param dbName
	 * @param query
	 * @param queryParameterValues
	 * @return integer - recordsUpdated
	 * @throws Exception
	 */
	public static int executeUpdate(String dbName,String query,String... queryParameterValues) throws Exception{
		
		int recordsUpdated = 0;
		PreparedStatement stmt;

		try {
		
			stmt = DB.getPreparedStatement(dbName, query, queryParameterValues);
			recordsUpdated = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception ("Execution of the Query \n" + query + "\nFailed with message \n" + e.getMessage());
		}
		return recordsUpdated;
	}
	/**
	 * <pre>
	 * Method to establish data base connection and prepare Sql statement
	 * </pre>
	 * @param dbName
	 * @param query
	 * @param queryParameterValues
	 * @return PreparedStatement - stmt
	 */

	private static PreparedStatement getPreparedStatement(String dbName,String query,String[] queryParameterValues)  {
		PreparedStatement stmt = null;
		int queryParamCnt = 0;
		String dataBaseName = Stock.getConfigParam(dbName.trim());
		
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
					throw new Error ("Parameters in the query and the values sent missmatched");
				}
			}
		} catch (SQLException e) {
			throw new Error ("SQL Exception occurred while preparing query:\n" + query + "\n\nFailed with message:\n" + e.getMessage());
		}
		
		return stmt;
	}
	
	/**
	 * <pre>
	 * Method to establish data base connection
	 * @param DBName
	 * @return Connection - conn 
	 */
	private static Connection getDBConnection(String DBName) {
		
		//OracleDataSource dataSource;
		Connection conn;
		
		
		String jdbcUrl = null;
		String dbuserid = null;
		String dbpassword = null;
		//String actualDBName = ReadProperties.getEnvVariableValue(DBName.trim().toUpperCase()).trim().toUpperCase();
		
		if (dbConnections.containsKey(DBName)) {
			return dbConnections.get(DBName);
		} else {
	
			
			try {
				//dataSource = new OracleDataSource();
				//jdbcUrl = Stock.getConfigParam(DBName);
				jdbcUrl = Globals.databaseConnectionStrings.get(DBName);
				dbuserid = Stock.getConfigParam(Globals.GC_COLNAME_USERID);
				dbpassword = Stock.getConfigParam(Globals.GC_COLNAME_DBPASSWORD);
					
				if (jdbcUrl.trim().length() == 0) {
					throw new Error ("No connection string found for the DB: " + DBName);
				}
				
				//dataSource.setURL(jdbcUrl);
				
				conn = DriverManager.getConnection(jdbcUrl, dbuserid, dbpassword);
				
			} catch (SQLException e) {
				throw new Error ("Establishing connection to DB " + DBName + " Failed \n " + e.getMessage());
			}
			
			return conn;
		}
		
		
	}
	/**
	 * Method to get no.of rows for ResultSet
	 * @param resultSet
	 * @return integer - rowSize
	 */
	public static int getRecordSetCount(ResultSet resultSet) {
		
		int rSize = 0;
		
		try {
			resultSet.last();
			rSize = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (SQLException e) {
			//System.out.println("RecordSet is empty");
			throw new Error ("SQL Exception: " + e.getMessage());
		}
		
		return rSize;
		
	}
	
/**
 * Method to close data base connection
 * @throws Exception
 */
	public static void closeDBConnections() throws Exception{
		
		Iterator<String> connectionsIterator = dbConnections.keySet().iterator();
		Iterator<ResultSet> recordSetsIterator = masterRecordSet.iterator();
		
		while (recordSetsIterator.hasNext()) {
			
			ResultSet currRecordSet = (ResultSet) recordSetsIterator.next();
			
			try {
				if (!currRecordSet.isClosed()) {
					currRecordSet.close();
					
				}
			} catch (SQLException e) {
			   throw new Exception ("list<Connections> :- a list containing db connections did not close properly");
			}
			
			
		}
		recordSetsIterator.remove();
		
//		String[] connectionNames = new String[10];
//		int count=0;
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
//			connectionNames[count++]=connectionName;
		}
//		for(int i=0;i<count;i++)	
//			dbConnections.remove(connectionNames[i]);
	}

	/**
	 * Method to compare data base date and web date
	 * 
	 * @PARAMETER = dbDate
	 * 
	 * @PARAMETER = webDate
	 * 
	 * @Author: Ranjan
	 */
	public static boolean compareDB_Date_With_Web_Date(String dbDate,
			String webDate) {

		boolean isDateEqual_Flag = false;
		Date date;
		try {
			date = new Date();
			dbDate = dbDate.substring(0, dbDate.indexOf(" "));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			date = df.parse(dbDate);
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy");
			String strDate = sfd.format(date);
			if (strDate.equalsIgnoreCase(webDate))
				isDateEqual_Flag = true;
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return isDateEqual_Flag;
	}

	
	
	/**
	 * <p>
	 * Returns the filtered rowset object of the existing resultset.User needs to pass the number of filters as parameters and 
	 * the existing result set object on which the filtering is to be applied.
	 * </p>
	 * @param unfilteredRecordSet
	 * @param predicates
	 * @return filteredObj and null if there is no values matching the filter criteria
	 * @throws SQLException
	 */
	public static FilteredRowSet filterRecordSet(ResultSet unfilteredRecordSet,Predicate...predicates) throws SQLException
	{
		rsf= RowSetProvider.newFactory();
		filterObj = rsf.createFilteredRowSet();
		
		List<Predicate> filterList = Arrays.asList(predicates);
		   for(Predicate p : filterList)
	        {
			    filterObj =rsf.createFilteredRowSet();
	        	filterObj.populate(unfilteredRecordSet);
	        	filterObj.setFilter(p);
	        	unfilteredRecordSet = filterObj;
	        	  
	        	 int countRec = DB.getRecordSetCount(filterObj);
		        	if (countRec == 0)
		        	{
	        		return null;
		        	}
	        }
		return filterObj;
	}
	/**
	 * Method to get Filtered RowSet Count 
	 * @param frs
	 * @return Integer - count
	 * @throws SQLException
	 */
	public static int getFilteredRowSetCount(FilteredRowSet frs) throws SQLException {

		count = 0;
	    if (frs == null) {
	      return 0;
	    }

	    CachedRowSet crs = (CachedRowSet)frs;

	    while (crs.next()) {
	      count ++;
	    }
	    frs.beforeFirst();
	    return count;
	  }
	
	/*public static Statement createBatchProcess(String dbName,String query,PreparedStatement stmt,String...queryParameterValues) throws SQLException
	{
		
		int queryParamCnt=0; 
		if (queryParamCnt == queryParameterValues.length) {
			for (int i = 1; i <= queryParameterValues.length; i++) {
				stmt.setString(i,queryParameterValues[i-1]);
			}
			}
		stmt.addBatch(query);
		return stmt;
	}
	
	public static void executeBatch(Statement stmt) throws SQLException 
	{
		stmt.executeBatch();
	}
	
	public PreparedStatement getPreparedStatement(String dbName,String query) throws SQLException
	{
		return (getDBConnection(dbName)).prepareStatement(query);
	}*/
	/**
	 * Method to get string  from record set
	 * @param recordSet
	 * @param paramTobeFetched
	 * @return String - paramValue
	 * @throws SQLException
	 */
	
	public static String getRecordFromRecordSet(FilteredRowSet recordSet,String paramTobeFetched) throws SQLException
	{
		String paramValue = null;
		if(recordSet != null)
		{
			while(recordSet.next())
			{
				paramValue = recordSet.getString(paramTobeFetched);
				break;
			}
		}
		return paramValue;
	}


}
