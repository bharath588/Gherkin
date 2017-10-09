package generallib;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Stock;

public class General {

	public static final String dbInstance = "targetDatabase";
	
	
	
	public static void setDatabaseInstance(String dbInst)
	{
		Stock.setConfigParam(General.dbInstance,dbInst.toUpperCase());
	}
	
	public static String getSSNFromPlanNumber(String planNumber) throws SQLException
	{
		String ssn= null;
		ResultSet queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("getSSNFromPlanNumberQuery")[1], planNumber);
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				ssn = queryResultSet.getString("SSN");
				break;
			}
		}
		return ssn;
	}
}
