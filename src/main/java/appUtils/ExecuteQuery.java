package appUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;

import lib.DB;
import lib.Log;
import lib.Stock;


public class ExecuteQuery {
	public static Properties conf = null;
	static char dbInital;
	static String dbEnv;
	static String dbName;
	static String dbName1;
	static String SSN;
	static ResultSet resultSet;
	static String getID;
	static String getIDAfterDel;
	static String queryGetID;
	static String queryDelAuth;
	static String queryDelActCodeResp;
	static String queryDelUsrVeriQstn;
	static String queryDelIndReg;
	static String queryDelActCodeChlng;
	static String queryDelUsrNmReg;
	static String queryCommit;
	static String querydeleteEmail;
	static String queryupdatePhoneNo;
	 DB db = new DB();
	
	@SuppressWarnings("static-access")
	public static void UnRegisterParticipant(String SSN) {
		
		initQuery();
				//Access Data Sheet
		Log.Report(Log.Level.INFO,"***************** Initiating execution of delete record query *****************");	
		dbName = "CommonDB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		dbName1 = "ParticipantDataDB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		if(!dbName.isEmpty()){		
			try{
			   /*if(dbName.split("_")[0].length()>1)
			   {throw new Exception("Incorrect DB name provided, refer config.properties");}*/
			  
			  
					   DB db = new DB();
					  // SSN = Stock.GetParameterValue("SSN");		
					   getID = Execute(db,dbName,queryGetID,"ID",SSN);
					  
						try{
							resultSet = db.executeQuery(dbName,queryGetID,SSN);
							if (resultSet != null) {
								while (resultSet.next()) {
									getID = resultSet.getString("ID");
								}
							}
						}catch(Exception e){
							Log.Report(Log.Level.ERROR,e.getMessage());			
						}		
					   if(!getID.isEmpty()){
						   Log.Report(Log.Level.INFO,"Resulting ID : "+getID+" from SSN : "+SSN);					  
						   db.executeUpdate(dbName,queryDelAuth,SSN);
						   db.executeUpdate(dbName,queryDelActCodeResp,getID);
						   db.executeUpdate(dbName,queryDelUsrVeriQstn,getID);
						   db.executeUpdate(dbName,queryDelIndReg,getID);
						   db.executeUpdate(dbName,queryDelActCodeChlng,getID);
						   db.executeUpdate(dbName,queryDelUsrNmReg,getID);
						   db.executeUpdate(dbName1,querydeleteEmail,SSN);
						   db.executeUpdate(dbName1,queryupdatePhoneNo,SSN);
						   db.executeQuery(dbName,"commit");
						   db.executeQuery(dbName,"commit");							
						   getIDAfterDel = Execute(db,dbName,queryGetID,"ID",SSN);
						   if(getIDAfterDel.isEmpty()){
							   Log.Report(Log.Level.INFO,"ID : "+getID+" for SSN : "+SSN+" has been successfully deleted");
						   }else{
							   Log.Report(Log.Level.ERROR,"ID : "+getID+" for SSN : "+SSN+" data delete failed");						   
						   }
					   }else{
						   Log.Report(Log.Level.INFO,"No record found for the SSN : "+SSN);
					   }					   
					   db.closeDBConnections();
			}	
			catch(Exception e){							
				Log.Report(Log.Level.ERROR,e.getLocalizedMessage());
			}
		} else{				   
				   try {
					throw new Exception("Incorrect DB name provided, refer config.properties");
				} catch (Exception e) {
					e.printStackTrace();
				}
			   }  
			
			}
	
	private static void loadPROP(){
		conf =  new Properties();
		try {
			conf.load(new FileInputStream("config.properties"));			
		} catch (FileNotFoundException e) {
			Log.Report(Log.Level.ERROR,"File not found :\n"+e.getMessage());			
			exit();			
		} catch (IOException e) {
			Log.Report(Log.Level.ERROR,"Unable to load Config properties :\n"+e.getMessage());
			exit();	
		}
	}
		
	private static void initQuery(){
		queryGetID = "select ID from USERNAME_REGISTRY  where ssn = ?";
		queryDelAuth = "delete from Authentication_control where ssn = ?";
		queryDelActCodeResp = "delete from ACTIVATION_CODE_RESPONSE where usrreg_id = ?";
		queryDelUsrVeriQstn = "delete from USER_VERIFY_QUESTION where usrreg_id = ?";
		queryDelIndReg = "delete from INDIVIDUAL_REGISTRY where usrreg_id = ?";
		queryDelActCodeChlng ="delete from ACTIVATION_CODE_CHALLENGE  where usrreg_id = ?";
		queryDelUsrNmReg ="delete from USERNAME_REGISTRY where id =?";
		querydeleteEmail = "DELETE FROM ind_email WHERE Ind_ID= (SELECT ID from Individual WHERE SSN=?)";
		queryupdatePhoneNo = "update Individual set mobile_phone_nbr='',mobile_phone_area_code='' where ssn=?";
		queryCommit = "commit";
	}
	
	public static String Execute(DB db,String dbName,String query,String returnData,String... param) throws Exception {
		String ret = "";
		try{
			resultSet = db.executeQuery(dbName,query,param);
			if (resultSet != null) {
				while (resultSet.next()) {
					ret = resultSet.getString(returnData);
				}
			}
		}catch(Exception e){
			Log.Report(Log.Level.ERROR,e.getMessage());			
		}		
		return ret;
	}
	
	private static void exit(){
		System.exit(0);	
	}
}
