package appUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageobjects.general.LeftNavigationBar;


import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class Common {
	
	/**
	 * <pre>
	 * Method to return the no of plans associated to a user from db
	 * 
	 * @return noOfPlans
	 */
	// For Sponsor
	public static final String GC_DEFAULT_SPONSER = "Empower";
	static List<String> lstDisbursmentReason = new ArrayList<String>();
	static List<String> lstDistributionMethod = new ArrayList<String>();
	static List<String> lstDistributionMethodTimings = new ArrayList<String>();
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	public static WebElement lblUserName;
	@FindBy(name="legacyFeatureIframe") public static WebElement iframeLegacyFeature;
	@FindBy(xpath="//button[text()[normalize-space()='Dismiss']]") public static WebElement lnkDismiss;
	//private static String lnkDismiss= "//button[text()[normalize-space()='Dismiss']]";
	private static String userName = "";
	static String userFromDatasheet = null;
	private static String textField="//*[contains(text(),'webElementText')]";
	private static String errorMessage="//*[text()='webElementText']";
	public static ResultSet getParticipantInfoFromDB(String ssn) {
	
		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				ssn);

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static ResultSet getParticipantInfoFromDataBase(String userName)
			throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery[0] = getParticipantDBName(userName) + "DB_"+checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				userName.substring(0, 9));

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.first();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						com.aventstack.extentreports.Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static String getParticipantDBName(String userName) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;

		ResultSet participantDB = null;

		try {
			sqlQuery = Stock.getTestQuery("getPartcipantDBInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],userName);
		if (DB.getRecordSetCount(participantDB) > 0) {
			try {
				participantDB.first();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		System.out.println("DATA BASE Name"
				+ participantDB.getString("database_instance"));
		return participantDB.getString("database_instance");
	}

	public static String getParticipantID(String ssn) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet participantID = null;

		try {
			sqlQuery = Stock.getTestQuery("getParticipantID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

		if (DB.getRecordSetCount(participantID) > 0) {
			try {
				participantID.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		} else {
			try {
				sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDBISIS");
			} catch (Exception e) {
				e.printStackTrace();
			}

			participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

			if (DB.getRecordSetCount(participantID) > 0) {
				try {
					participantID.last();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			} else {
				try {
					sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDB");
				} catch (Exception e) {
					e.printStackTrace();
				}

				participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

				if (DB.getRecordSetCount(participantID) > 0) {
					try {
						participantID.last();
					} catch (SQLException e) {
						e.printStackTrace();
						Reporter.logEvent(
								Status.WARNING,
								"Query Participant DB:" + sqlQuery[0],
								"The Query did not return any results. Please check participant test data as the appropriate data base.",
								false);
					}
				}
			}
		}
		System.out.println("ID is " + participantID.getString("ID"));
		return participantID.getString("ID");
	}

	public static boolean isCurrentSponser(String sponserName) {
		boolean status = false;

		if (lib.Stock.globalTestdata.containsKey("ACCUCODE")
				&& lib.Stock.GetParameterValue("AccuCode") != null) {
			if (sponserName
					.equalsIgnoreCase("Southwest Airlines Retirement Plans")) {
				sponserName = "SWA";
			}
			if (sponserName.equalsIgnoreCase("Empower Retirement")) {
				sponserName = "Apple";
			}
			if (lib.Stock.GetParameterValue("AccuCode").equalsIgnoreCase(
					sponserName)) {
				status = true;
			} else {
				status = false;
			}
		}

		else {
			if (GC_DEFAULT_SPONSER.equalsIgnoreCase(sponserName))
				status = true;
			else
				status = false;
		}
		return status;
	}

	public static String getSponser() {

		String sponser = null;
		if (Stock.globalTestdata.get(Thread.currentThread().getId()).containsKey("ACCUCODE")) {
			if (lib.Stock.GetParameterValue("AccuCode") != null) {
				sponser = lib.Stock.GetParameterValue("AccuCode");
			} else {
				sponser = GC_DEFAULT_SPONSER;

			}
		} else {
			sponser = GC_DEFAULT_SPONSER;

		}
		return sponser;
	}

	public static String getUserNameFromDB() {

		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = null;
		try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn.substring(
					0, ssn.length() - 3));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userFromDatasheet;
	}

	public static boolean verifyStringIsInNumberFormat(String value) {
		int number;
		boolean lblDisplayed;
		try {

			number = Integer.parseInt(value);
			lblDisplayed = true;
		} catch (NumberFormatException ex) {
			lblDisplayed = false;
		}
		return lblDisplayed;
	}
	public static String checkEnv(String envName) {
		if (envName.contains("PROJ")) {
			return Globals.DB_TYPE.get("PROJ");
		}
		if (envName.contains("QA")) {
			return Globals.DB_TYPE.get("QA");
		}
		if (envName.contains("PROD")) {
			return Globals.DB_TYPE.get("PROD");
		}
		return null;
	}
	public static boolean verifyLoggedInUserIsSame() {
		PageFactory.initElements(lib.Web.getDriver(), Common.class);
		boolean lblDisplayed=false;
		String userLogedIn="";
		ResultSet strUserInfo=null;
	String ssn = Stock.GetParameterValue("userName");
	
	
	//if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
	if(Stock.getConfigParam(Globals.GC_COLNAME_TEST_ENV).contains("PROD"))
	{
		userFromDatasheet=Stock.GetParameterValue("lblUserName");
	}
	else if (!userName.equalsIgnoreCase(ssn.trim())){
	
	try {
		strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	
	try {
		userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
				+ strUserInfo.getString("LAST_NAME");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	if(Web.isWebElementDisplayed(lblUserName)){
	 userLogedIn = lblUserName.getText();
	}
	/*String sponser = this.lblSponser.getAttribute("Alt");
	if (sponser.isEmpty()) {
		sponser = Common.GC_DEFAULT_SPONSER;
	}*/
	if (!userName.equalsIgnoreCase(ssn.trim())){
		 if (userFromDatasheet.equalsIgnoreCase(userLogedIn)){
		lblDisplayed=true;
		 }
		 userName = ssn.trim();
	}else{
		 if (userFromDatasheet.equalsIgnoreCase(userLogedIn)){
		lblDisplayed=true;
		 }
	}
	
	return lblDisplayed;
	}
	

    private static String progressBar ="//*[@class='loader']";
/*
    * @Author :- Siddartha 
     * @Date  :- 17 - Oct -2016
    * @ Method:- 
     */
    
      public static void waitForProgressBar(){
    	  int iTimeInSecond=30;
           try{
                  int iCount = 0;
                  while (FindElement(progressBar).isDisplayed()){
                	  if (Web.isWebElementDisplayed(lnkDismiss)) {
                		  Web.clickOnElement(lnkDismiss);
              		}
                         if(iCount ==iTimeInSecond){
                               break;
                         }   
                         
                         System.out.println("Progress Bar displaying..");
                         Thread.sleep(1000);                       
                         iCount++;
                  }
                  
           }catch(Exception e){
                  
           }
           
        }
        
        public static WebElement FindElement(String sElement){
           return lib.Web.getDriver().findElement(By.xpath(sElement));
        }
        public static boolean isAlerPresent(){
            try{
                   Web.getDriver().switchTo().alert();
                   return true;
            }catch(NoAlertPresentException ex){
                   return false;
            }
            
      }
      public  static void HandlePopAlert(){
            try{
                   Alert alert = Web.getDriver().switchTo().alert();
                   String sPopText = alert.getText();
                   System.out.println(sPopText);
                   alert.accept();
            }catch(Exception e){
            
            }
            
      }

      public  static void handlePageToLoad(String pageName){
          try{
                 LeftNavigationBar lftbar=new LeftNavigationBar();
                 lftbar.clickNavigationLink("Rate Of Return");
                 waitForProgressBar();
                 Web.waitForPageToLoad(Web.getDriver());
                 lftbar.clickNavigationLink(pageName);
                 waitForProgressBar();
                 Web.waitForPageToLoad(Web.getDriver());
          }catch(Exception e){
          
          }
          
    }
      public static boolean switchToLegacyFutureFrame(){
          try{
        	  Web.getDriver().switchTo().defaultContent();
        	  Web.waitForElement(iframeLegacyFeature);
        	  if(Web.isWebElementDisplayed(iframeLegacyFeature)){
        	  Web.getDriver().switchTo().frame(iframeLegacyFeature);
        	         	  }
        	  return true;
          }  
          catch(Exception e){
                 return false;
          }
          
    }
      /*
  	 * Update GDR Status in Database for the Ppts in Service, Sep Service (PWD)
  	 */
  	public static boolean updateGDRStatus(String planId,String dsrsCode,String taxReasoncode,
  			String sdmtCode,String gdmt_seqnum)
  	{
  		boolean updatedGDRStatus=false;
  		int critDetailId=0;		
  		try {		
  			//Updating DB for setting GDR Rules 			
  			String[] sqlQuery = Stock.getTestQuery("getPptCritDetailId");
  			ResultSet getPPtCritDetailIdRs=DB.executeQuery(sqlQuery[0], sqlQuery[1], planId,dsrsCode);		
  			critDetailId=DB.getRecordSetCount(getPPtCritDetailIdRs);
  			if(critDetailId>0)
  			{		
  			while(getPPtCritDetailIdRs.next())
  			{
  				String critId=getPPtCritDetailIdRs.getString("Rule_Crit_Detail_ID");  				
  				String[] updateQuery=Stock.getTestQuery("updateGDRStatus");  				
  				DB.executeUpdate(updateQuery[0], updateQuery[1],taxReasoncode,sdmtCode,gdmt_seqnum,critId);		
  				Reporter.logEvent(Status.PASS, "Verify GDR Rule has been Set up for this Plan",
  						"The GDR Rule has been set up for "+dsrsCode + " and for the Tax Reason Code "+taxReasoncode,false);
  			}  			
  			updatedGDRStatus=true;
  			}
  			else {
  				Reporter.logEvent(Status.FAIL, "Verify GDR Rule has been Set up for this Plan",
  						"The GDR Rule has NOT been set up for "+dsrsCode + " and for the Tax Reason Code "+taxReasoncode,false);
  			throw new Error("GDR Rule has not been Set up for this Plan");
  			}  			
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		catch (Exception e) {		
  			e.printStackTrace();
  		}
  		return updatedGDRStatus;
  		
  	}
  	
  	 /*
  	 * Update GDR Status in Database for the Ppts in Service, Sep Service (PWD)
  	 */
  	public static boolean updateGDRStatus(String planId,String dsrsCode,String taxReasoncode,
  			String sdmtCode,String gdmt_seqnum,String dsmd_Code)
  	{
  		boolean updatedGDRStatus=false;
  		int critDetailId=0;		
  		try {		
  			//Updating DB for setting GDR Rules 			
  			String[] sqlQuery = Stock.getTestQuery("getPptCritDetailId");
  			ResultSet getPPtCritDetailIdRs=DB.executeQuery(sqlQuery[0], sqlQuery[1], planId,dsrsCode);		
  			critDetailId=DB.getRecordSetCount(getPPtCritDetailIdRs);
  			if(critDetailId>0)
  			{		
  			while(getPPtCritDetailIdRs.next())
  			{
  				String critId=getPPtCritDetailIdRs.getString("Rule_Crit_Detail_ID");  				
  				String[] updateQuery=Stock.getTestQuery("updateGDRStatus_SepService");  				
  				DB.executeUpdate(updateQuery[0], updateQuery[1],taxReasoncode,sdmtCode,gdmt_seqnum,dsmd_Code,critId);		
  				Reporter.logEvent(Status.PASS, "Verify GDR Rule has been Set up for this Plan",
  						"The GDR Rule has been set up for "+dsrsCode + " and "+dsmd_Code +" for the Tax Reason Code "+taxReasoncode,false);
  			}  			
  			updatedGDRStatus=true;
  			}
  			else {
  				Reporter.logEvent(Status.FAIL, "Verify GDR Rule has been Set up for this Plan",
  						"The GDR Rule has NOT been set up for "+dsrsCode + " and "+dsmd_Code +" for the Tax Reason Code "+taxReasoncode,false);
  			throw new Error("GDR Rule has not been Set up for this Plan");
  			}  			
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		catch (Exception e) {		
  			e.printStackTrace();
  		}
  		return updatedGDRStatus;
  		
  	}
  	
  	public static boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", true);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
  	/**
	 * Method to verify Label is Displayed
	 * @param labelName
	 * @return
	 */
	public static boolean isLabelDisplayed(String labelName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", labelName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify " + labelName
					+ "   Label is Displayed", "'"+labelName + "' Label is Displayed",
					true);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "Verify " + labelName
					+ "   Label is Displayed", "'"+labelName + "' Label is Not Displayed", true);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
	/**
	 * Method to verify Error Message is Displayed
	 * @param errorMsg
	 * @return
	 */
	public static boolean isErrorMessageDisplayed(String errorMsg) {
		boolean isErrorMessageDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(errorMessage.replace("webElementText", errorMsg)));
	
		 isErrorMessageDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isErrorMessageDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify Error Message" + errorMsg
					+ " is Displayed", "'"+errorMsg + "'Error Message is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL,  "Verify Error Message" + errorMsg
					+ " is Displayed", "'"+errorMsg + "'Error Message is not Displayed",
					 true);
			isErrorMessageDisplayed=false;
		}
	
  return isErrorMessageDisplayed;
	}
	
	public static void updateEDeliveryMthodInDB() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar = Calendar.getInstance();         
		calendar.add(Calendar.DATE, -190);
		String date=dateFormat.format(calendar.getTime());
		System.out.println("DATE"+date);
		
		String ssn=Stock.GetParameterValue("SSN");
		String userName=Stock.GetParameterValue("username");
		//prepareLoginTestData(Stock.GetParameterValue("queryName"), Stock.GetParameterValue("ga_PlanId"));
		String[] sqlQuery = Stock.getTestQuery("UpdateContactVerificationDate");
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,ssn);
		sqlQuery = Stock.getTestQuery("updateTerminationDate");
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		DB.executeUpdate(sqlQuery[0], sqlQuery[1],ssn);
		sqlQuery=Stock.getTestQuery("updateDeliveryMethodForParticipant");
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		DB.executeUpdate(sqlQuery[0], sqlQuery[1],Stock.GetParameterValue("deliveryMethod") ,ssn);
		inserWorkEmailForIndividual(Stock.GetParameterValue("ID"));
	}
	
	/**
	 * Method to add work email for Participant
	 * @param ind_id
	 * @throws Exception
	 */
	public static void inserWorkEmailForIndividual(String ind_id) throws Exception {
	
		String userName=Stock.GetParameterValue("username");
		
		String[] sqlQuery = Stock.getTestQuery("getWorkEmailId");
		sqlQuery[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet rs=DB.executeQuery(sqlQuery[0], sqlQuery[1], ind_id);
		if(!(DB.getRecordSetCount(rs)>0)){
		String[] sqlQuery1 = Stock.getTestQuery("InsertWorkEmailId");
		sqlQuery1[0] = Common.getParticipantDBName(userName) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
		DB.executeUpdate(sqlQuery1[0], sqlQuery1[1], ind_id);
		}
		
	}
	public static String getFutureDate(String format) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();         
		calendar.add(Calendar.DATE, 20);
		String date=dateFormat.format(calendar.getTime());
		System.out.println("DATE"+date);
		return date;
	}
	public static String getFutureDate(String format,int futeureDate) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();         
		calendar.add(Calendar.DATE, futeureDate);
		String date=dateFormat.format(calendar.getTime());
		System.out.println("DATE"+date);
		return date;
	}
	public static String getCurrentDate(String format) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();         
		String date=dateFormat.format(calendar.getTime());
		System.out.println("DATE: "+date);
		return date;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<String> getDisbursmentReason(String ga_Id) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet DisbursmentReason = null;

		try {
			sqlQuery = Stock.getTestQuery("getdisbursmentreason");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DisbursmentReason = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);

		if (DB.getRecordSetCount(DisbursmentReason) > 0) {
			
  			while(DisbursmentReason.next())
  			{try{
  				lstDisbursmentReason.add(DisbursmentReason.getString("custom_description"));
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
  			}
		}
		
			
		return lstDisbursmentReason;
	}
	
	public static List<String> getMethodForDisbursmentReason(String ga_Id,String disbursmentReason) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet DisbursmentMethod = null;

		try {
			sqlQuery = Stock.getTestQuery("getMethodsfordisbursmentreason");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DisbursmentMethod = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id,disbursmentReason);

		if (DB.getRecordSetCount(DisbursmentMethod) > 0) {
			
  			while(DisbursmentMethod.next())
  			{
  				try{
  			
  				lstDistributionMethod.add(DisbursmentMethod.getString("custom_description"));
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
  			}
  			
		}
		
			
		return lstDistributionMethod;
	}

	public static List<String> getTimingForDisbursmentMethod(String ga_Id,String disbursmentMethod) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet DisbursmentTiming = null;

		try {
			sqlQuery = Stock.getTestQuery("gettimingsforMethod");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DisbursmentTiming = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id,disbursmentMethod);

		if (DB.getRecordSetCount(DisbursmentTiming) > 0) {
			
  			while(DisbursmentTiming.next())
  			{
  				try{
  			
  					lstDistributionMethodTimings.add(DisbursmentTiming.getString("custom_description"));
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
  			
		}
		}
		
			
		return lstDistributionMethodTimings;
	}
	
	/**
	 * Method to get the Available Deferrals for Current Enrollment Period
	 * @param ga_Id
	 * @param enrollEndDate
	 * @return lstDeferralTypes
	 * @throws SQLException
	 */
	public static List<String> getAvailableDeferralsforPlan(String ga_Id,String enrollEndDate) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet DeferralType= null;
		List<String> lstDeferralTypes= new ArrayList<String>();

		try {
			sqlQuery = Stock.getTestQuery("getAvailableDeferralTypes");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DeferralType = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);

		if (DB.getRecordSetCount(DeferralType) > 0) {
			
  			while(DeferralType.next())
  			{try{
  				String deferralType=DeferralType.getString("deferral_type_code");
  				if(deferralType.equalsIgnoreCase("EEDEF1")){
  					deferralType="Employee deferral";
  				}
  				lstDeferralTypes.add(deferralType);
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Disbursment Reason:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
  			}
		}
		
			
		return lstDeferralTypes;
	}
	
	/**
	 * Method to get the Plan Name
	 * @param gc_Id 150049
	 * @param id -From Plan Tabel
	 * @return planName
	 * @throws SQLException
	 */
	public static String getPlanName(String gc_Id, String id) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet Plan= null;
		String planName="";

		try {
			sqlQuery = Stock.getTestQuery("getPlanName");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], gc_Id,id);
try{
		if (DB.getRecordSetCount(Plan) > 0) {
			Plan.first();
  			planName= Plan.getString("NAME");
			} 
	}catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query get PlanName:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
  			
		return planName;
	}
	


/**
 * Method to get the Plan Description from
 * @param ga_Id 150049-N1
 ** @return planDescription
 * @throws SQLException
 */
public static String getEnrollmentPlanDescription(String ga_Id) throws SQLException {

	// query to get the no of plans
	String[] sqlQuery = null;
	ResultSet Plan= null;
	String planDescription="";

	try {
		sqlQuery = Stock.getTestQuery("getPlanDescription");
	} catch (Exception e) {
		e.printStackTrace();
	}

	Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
try{
	if (DB.getRecordSetCount(Plan) > 0) {
		Plan.last();
		planDescription= Plan.getString("description");
		} 
}catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query get Plan Description:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}
			
	return planDescription;
}

/**
 * Method to get the Plan Description from
 * @param ga_Id 150049-N1
 ** @return panelHeading
 * @throws SQLException
 */
public static String getEnrollmentPanelHeading(String ga_Id) throws SQLException {

	// query to get the no of plans
	String[] sqlQuery = null;
	ResultSet Plan= null;
	String panelHeading="";

	try {
		sqlQuery = Stock.getTestQuery("getPlanDescription");
	} catch (Exception e) {
		e.printStackTrace();
	}

	Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], ga_Id);
try{
	if (DB.getRecordSetCount(Plan) > 0) {
		Plan.first();
		panelHeading= Plan.getString("description")+" "+Plan.getString("name");
		} 
}catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query get Plan Description:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}
			
	return panelHeading;
}

/**
 * Method to get the Plan Name
 * @param gc_Id 194044
 * @return planName
 * @throws SQLException
 */
public static String getPlanName(String gc_Id) throws SQLException {

	// query to get the no of plans
	String[] sqlQuery = null;
	ResultSet Plan= null;
	String planName="";

	try {
		sqlQuery = Stock.getTestQuery("getPlanNamewithGCID");
	} catch (Exception e) {
		e.printStackTrace();
	}

	Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], gc_Id);
try{
	if (DB.getRecordSetCount(Plan) > 0) {
		Plan.first();
			planName= Plan.getString("NAME");
		} 
	}catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query get PlanName with GCID:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}
			
	return planName;
}


/**
 * Method to get Loan Notification Type
 * @param ev_Id 
 * @return notificationType
 * @throws SQLException
 */
public static String getLoanNotificationType(String ev_Id) throws SQLException {

	// query to get the no of plans
	String[] sqlQuery = null;
	ResultSet Plan= null;
	String notificationType="";
	String ev_id1=Integer.toString(Integer.parseInt(ev_Id)+1);

	try {
		sqlQuery = Stock.getTestQuery("getLoanNotificationType");
	} catch (Exception e) {
		e.printStackTrace();
	}

	Plan = DB.executeQuery(sqlQuery[0], sqlQuery[1], ev_Id,ev_id1);
try{
	if (DB.getRecordSetCount(Plan) > 0) {
		Plan.first();
		notificationType= Plan.getString("notification_type");
		} 
	}catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query get Loan Notification Type:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}
			
	return notificationType;
}
public static String getDBNameForUnregisteredUser(String ssn) throws SQLException {


	String[] sqlQuery = null;

	ResultSet participantDB = null;

	try {
		sqlQuery = Stock.getTestQuery("getDBNameForUnRegisteredUser");
	} catch (Exception e) {
		e.printStackTrace();
	}

	participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],ssn);
	if (DB.getRecordSetCount(participantDB) > 0) {
		try {
			participantDB.last();
		} catch (SQLException e) {
			e.printStackTrace();
			Reporter.logEvent(
					Status.WARNING,
					"Query Participant DB:" + sqlQuery[0],
					"The Query did not return any results. Please check participant test data as the appropriate data base.",
					false);
		}

	}
	System.out.println("DATA BASE Name"
			+ participantDB.getString("database_instance"));
	return participantDB.getString("database_instance");
}

public static void updateEmailToNull(String ssn) throws Exception {

	String[] sqlQuery = Stock.getTestQuery("updateemailandMobilNotonull");
	sqlQuery[0] = Common.getDBNameForUnregisteredUser(ssn) + "DB_"+Common.checkEnv(Stock.getConfigParam("TEST_ENV"));
	DB.executeUpdate(sqlQuery[0], sqlQuery[1],ssn);
	
}



}
