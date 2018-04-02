package db.filters;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetProvider;

import lib.Stock;



import core.framework.Globals;


public class QueryFilter{
	List<String> filterParamList = new ArrayList<>();
	FilteredRowSet frs = null;
	 String userName = Stock.getConfigParam(Globals.GC_COLNAME_USERID);
	  String passWord = Stock.getConfigParam(Globals.GC_COLNAME_DBPASSWORD);	static String table = null;
	static String columnName = null;
	public static String[] filteredValue;
	
	
	public void executeSubQuery(String subQuery,String dbUrl) throws SQLException
	{
		frs= RowSetProvider.newFactory().createFilteredRowSet();
		frs.setCommand(subQuery);
        frs.setUsername(userName);
        frs.setPassword(passWord);
        frs.setUrl(dbUrl);
	    frs.execute();
	}
	
	
	public void getMetaData() throws SQLException
	{
		ResultSetMetaData rsmd = frs.getMetaData();
		table = frs.getTableName();
		columnName =rsmd.getColumnName(1);
		System.out.println(columnName);
	}
	
	
	public void createFilterArray() throws SQLException
	{
		while(frs.next())
		{
			filterParamList.add(frs.getString(columnName));
		}
		filteredValue = new String[filterParamList.size()];
		filteredValue = filterParamList.toArray(filteredValue);
		
	}	
	public String[] evaluate(String subQuery,String dbName) throws SQLException 
	{
		executeSubQuery(subQuery,Globals.databaseConnectionStrings.get(dbName));
		getMetaData();
		createFilterArray();	
		return filteredValue;
	}
}