package pageobject.ROLL;

import lib.Stock;
import oracleforms.common.OracleForms;

public class ROLL_Process {
	
	OracleForms oracle_forms;
	String queryStringPart1;
			
		public void executeROLL() throws Exception{
			
			oracle_forms=new OracleForms();
			queryStringPart1=oracle_forms.setQueryString(Stock.GetParameterValue("inputParams").split(","));
			oracle_forms.deleteInDatabaseQuery(Stock.getTestQuery("queryForROLLUpdate")[1], Stock.GetParameterValue("dbUpdateParams").split(","));
			oracle_forms.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,Stock.GetParameterValue("queryString2ndPart"));
			oracle_forms.validateOutput();
			oracle_forms.validateInDatabaseWithParams(Stock.getTestQuery("queryForROLL")[1],Stock.GetParameterValue("CONTROL_PC_CODE_0"));
		}
	}