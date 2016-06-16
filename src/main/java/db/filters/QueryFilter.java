package db.filters;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.FilteredRowSet;

import com.sun.rowset.FilteredRowSetImpl;

public class QueryFilter{
	List<String> filterParamList = new ArrayList<>();
	FilteredRowSet frs = null;
	String url = "jdbc:oracle:thin:@DINSTDB:1521/dinstmain.isis.gwl.com";
    String userName = "KRSBHR";
    String passWord = "db123";
	static String table = null;
	static String columnName = null;
	public static String[] filteredValue;
	
	
	public void executeSubQuery(String subQuery) throws SQLException
	{
		frs= new FilteredRowSetImpl();
		frs.setCommand(subQuery);
        frs.setUsername(userName);
        frs.setPassword(passWord);
        frs.setUrl(url);
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
	public String[] evaluate(String subQuery) throws SQLException 
	{
		executeSubQuery(subQuery);
		getMetaData();
		createFilterArray();	
		return filteredValue;
	}

	

}
