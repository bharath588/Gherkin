package pageobject.UCSR;

import java.sql.ResultSet;
import java.sql.SQLException;

import generallib.General;

import com.aventstack.extentreports.Status;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import oracleforms.common.OracleForms;

public class UCSR_add_record {
	
	OracleForms oracle_forms;
	String queryStringPart1;

			
		public void executeUCSR() throws Exception{
			
			oracle_forms=new OracleForms();
			queryStringPart1=oracle_forms.setQueryString(Stock.GetParameterValue("inputParams").split(","));
			oracle_forms.deleteInDatabaseQuery(Stock.getTestQuery("queryForUCSRUpdate")[1], Stock.GetParameterValue("dbUpdateParams").split(","));
			oracle_forms.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,Stock.GetParameterValue("queryString2ndPart"));
			oracle_forms.validateOutput();
		}
			public void validateInDatabase() throws SQLException
			{
				ResultSet queryResultSet;
			queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForUCSR")[1],Stock.GetParameterValue("dbParams").split(","));
			if(DB.getRecordSetCount(queryResultSet)>0)
			{
				Reporter.logEvent(Status.PASS,"Validating if records exists in DB.","Records exist in DB.", false);
				if(queryResultSet.next()){			
					Reporter.logEvent(Status.INFO, "Validating Record creation in database", "Records exists in Database", false);
					Reporter.logEvent(Status.INFO, "From DATABASE:\nTable: USER_CLASS_SETUP_RULE","USCS_ID: " +queryResultSet.getString("USCS_ID")+
							"\nBUSSINESS_CODE: " +queryResultSet.getString("BUSINESS_CODE")+
							"\nROLE: " +queryResultSet.getString("ROLE"), false);		
				}
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Validating if records exists in DB.","Records does exist in DB.", false);
			}
			
			
		}
}
