package pageobject.GLOV;

import generallib.General;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import oracleforms.common.OracleForms;

public class GLOV_Process {
	
	
	OracleForms oracle_forms;
	String queryStringPart1;
	private Document doc;
	String tags;
			
		public void executeGLOV() throws Exception{
			
			oracle_forms=new OracleForms();
			queryStringPart1=oracle_forms.setQueryString(Stock.GetParameterValue("inputParams").split(","));
			oracle_forms.runService(Stock.GetParameterValue("ServiceName"), queryStringPart1,Stock.GetParameterValue("queryString2ndPart"));
			tags=oracle_forms.validateOutputCaptureTagValues(Stock.GetParameterValue("responseTags"));
			
			System.out.println("tags: "+tags);
			
		}
		public void validateInDatabase() throws SQLException{
			ResultSet queryResultSet;
			String dbValue= null;
			queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGLOV")[1],Stock.GetParameterValue("RV_COLUMN"));
			if(DB.getRecordSetCount(queryResultSet)>0){
				Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database", false);
	           //int i=0;
				while(queryResultSet.next()){
				
					dbValue = queryResultSet.getString("RV_LOW_VALUE");
					//String formValue = doc.getElementsByTagName("CODES_DESCRIPTION_0").item(0).getTextContent();
					if(dbValue.equalsIgnoreCase(tags))
					{
						Reporter.logEvent(Status.PASS,"Validating if the values from the database matches the form values.",
								"Values from the database matches the form values.Database value:"+dbValue+"\n Value from form:"+tags,false);	
						System.out.println("Form id="+tags+"\n Database id"+dbValue);
							//i++;
						
						}
						else
						{
							Reporter.logEvent(Status.FAIL,"Validating if the values from the database matches the form values",
									"Values from the database does not match the form values.Database value:"+dbValue+"\n Value from form:"+tags,false);
							
						}
						break;
					}
				}
			  
			else
					Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
				}
			
			
			
			
		}




